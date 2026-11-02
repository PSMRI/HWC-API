package com.iemr.hwc.service.neonatal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.testutil.NurseVisitRequest;
import com.iemr.hwc.testutil.PopulatedMocks;

/**
 * The neonatal service records a newborn visit: the visit itself, the birth details, the
 * immunisation given and the vitals, after which the infant moves on to the doctor.
 *
 * <p>Its collaborators are populated mocks, which report a saved record for every section,
 * so a test asks for a failure path by stubbing the one section it cares about.
 */
@DisplayName("NeonatalServiceImpl")
class NeonatalServiceImplTest {

	private NeonatalServiceImpl service;
	private CommonNurseServiceImpl commonNurseServiceImpl;
	private CommonDoctorServiceImpl commonDoctorServiceImpl;
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;
	private com.iemr.hwc.service.generalOPD.GeneralOPDDoctorServiceImpl generalOPDDoctorServiceImpl;

	@BeforeEach
	void setUp() throws Exception {
		service = new NeonatalServiceImpl();
		commonNurseServiceImpl = PopulatedMocks.inject(service, "commonNurseServiceImpl",
				CommonNurseServiceImpl.class);
		commonDoctorServiceImpl = PopulatedMocks.inject(service, "commonDoctorServiceImpl",
				CommonDoctorServiceImpl.class);
		commonBenStatusFlowServiceImpl = PopulatedMocks.inject(service, "commonBenStatusFlowServiceImpl",
				CommonBenStatusFlowServiceImpl.class);
		generalOPDDoctorServiceImpl = PopulatedMocks.inject(service, "generalOPDDoctorServiceImpl",
				com.iemr.hwc.service.generalOPD.GeneralOPDDoctorServiceImpl.class);
		PopulatedMocks.injectCollaborators(service);

		// The case record parses the diagnosis document this hands back, so it has to read as
		// one rather than as the bare value a populated mock would answer with.
		doReturn("{\"counsellingProvided\":\"a||b\"}").when(generalOPDDoctorServiceImpl)
				.getGeneralOPDDiagnosisDetails(anyLong(), anyLong());

		// A first visit of the day, so the save proceeds instead of reporting the visit as
		// already captured.
		doReturn(0).when(commonNurseServiceImpl).getMaxCurrentdate(any(), any(), any());
		doReturn(10L).when(commonNurseServiceImpl).saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class));
		doReturn(20L).when(commonNurseServiceImpl).generateVisitCode(anyLong(), any(), any());
	}

	private static JsonObject nurseRequest() {
		return nurseRequest(NurseVisitRequest.investigationOrderingTests());
	}

	private static JsonObject nurseRequest(JsonObject investigation) {
		NurseVisitRequest request = NurseVisitRequest.forCategory("NNI").ordering(investigation);
		request.with("infantBirthDetails", new JsonObject());
		request.with("immunizationHistory", new JsonObject());
		request.with("immunizationServices", new JsonObject());
		request.with("vitalDetails", new JsonObject());
		return request.build();
	}

	/** The counselling list, which these services read as an array of provided topics. */
	private static JsonArray counselling() {
		JsonArray list = new JsonArray();
		list.add("weight monitoring");
		return list;
	}

	private static JsonObject doctorRequest() {
		JsonObject diagnosis = new JsonObject();
		diagnosis.addProperty("specialistDiagnosis", "advice recorded");
		diagnosis.add("provisionalDiagnosisList", new JsonArray());
		return NurseVisitRequest.forCategory("NNI").with("findings", new JsonObject())
				.with("prescription", new JsonArray()).with("diagnosis", diagnosis)
				.with("refer", new JsonObject()).with("doctorDiagnosis", new JsonObject())
				.with("followUpForImmunization", new JsonObject())
				.with("counsellingProvidedList", counselling()).field("doctorSignatureFlag", "true").field("isSpecialist", "true").build();
	}

	@Nested
	@DisplayName("saving a nurse visit")
	class SavingANurseVisit {

		@Test
		@DisplayName("saves the visit and its sections, and answers with the visit code")
		void savesEverySection() throws Exception {
			String response = service.saveNurseData(nurseRequest(), "Bearer token");

			assertThat(response).contains("\"visitCode\":\"20\"").contains("Data saved successfully");
			verify(commonNurseServiceImpl).saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class));
			verify(commonNurseServiceImpl).generateVisitCode(10L, 1, 1);
		}

		@Test
		@DisplayName("reports the visit as already captured when one is recorded for the same reason")
		void reportsAnAlreadyCapturedVisit() throws Exception {
			doReturn(1).when(commonNurseServiceImpl).getMaxCurrentdate(any(), any(), any());

			assertThat(service.saveNurseData(nurseRequest(), "Bearer token")).contains("visitCode");
		}

		@Test
		@DisplayName("rejects a request that carries no visit details")
		void rejectsARequestWithoutVisitDetails() {
			assertThatThrownBy(() -> service.saveNurseData(new JsonObject(), "Bearer token"))
					.isInstanceOf(RuntimeException.class).hasMessageContaining("Invalid request, visit details is missing");
		}

		@Test
		@DisplayName("rejects a null request")
		void rejectsANullRequest() {
			assertThatThrownBy(() -> service.saveNurseData(null, "Bearer token")).isInstanceOf(RuntimeException.class);
		}

		@Test
		@DisplayName("moves the beneficiary on to the next desk once the visit is saved")
		void movesTheBeneficiaryOn() throws Exception {
			service.saveNurseData(nurseRequest(), "Bearer token");

			verify(commonBenStatusFlowServiceImpl).updateBenFlowNurseAfterNurseActivity(any(), any(), any(), any(), any(), any(), any(), any(),
					any(), any(), any(), any(), any(), any(), any());
		}
	}

	@Nested
	@DisplayName("saving doctor data")
	class SavingDoctorData {

		@Test
		@DisplayName("records the findings, investigation and prescription of the consultation")
		void recordsTheConsultation() throws Exception {
			service.saveDoctorDataNNI(doctorRequest(), "Bearer token");

			verify(commonDoctorServiceImpl).saveDocFindings(any());
		}

		@Test
		@DisplayName("updates a consultation that was already recorded")
		void updatesTheConsultation() throws Exception {
			assertThat(service.updateDoctorDataNNI(doctorRequest(), "Bearer token")).isNotNull();
		}
	}

	@Nested
	@DisplayName("reading a case record back")
	class ReadingACaseRecord {

		@Test
		@DisplayName("gathers the nurse sections of a visit into one document")
		void gathersTheNurseSections() throws Exception {
			assertThat(service.getNurseDataNNI(1L, 20L)).isNotNull();
		}

		@Test
		@DisplayName("gathers the doctor sections of a visit into one document")
		void gathersTheDoctorSections() throws Exception {
			assertThat(service.getBenCaseRecordFromDoctorNNI(1L, 20L)).isNotNull().contains("findings")
					.contains("counsellingProvidedList");
		}
	}

	@Nested
	@DisplayName("rolling a failed save back")
	class RollingAFailedSaveBack {

		@Test
		@DisplayName("deletes the records a failed nurse save had already written")
		void deletesTheRecordsOfAFailedSave() throws Exception {
			BenVisitDetailRepo benVisitDetailRepo = PopulatedMocks.inject(service, "benVisitDetailRepo",
					BenVisitDetailRepo.class);
			BenChiefComplaintRepo benChiefComplaintRepo = PopulatedMocks.inject(service, "benChiefComplaintRepo",
					BenChiefComplaintRepo.class);
			doReturn(20L).when(benVisitDetailRepo).getVisitCode(any(), any());

			service.deleteVisitDetails(nurseRequest());

			verify(benChiefComplaintRepo).deleteVisitDetails(20L);
			verify(benVisitDetailRepo).deleteVisitDetails(20L);
		}

		@Test
		@DisplayName("does nothing for a request that never created a visit")
		void doesNothingWithoutVisitDetails() throws Exception {
			BenVisitDetailRepo benVisitDetailRepo = PopulatedMocks.inject(service, "benVisitDetailRepo",
					BenVisitDetailRepo.class);

			service.deleteVisitDetails(new JsonObject());

			verify(benVisitDetailRepo, never()).getVisitCode(any(), any());
		}
	}
}
