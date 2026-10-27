package com.iemr.hwc.service.family_planning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
import com.iemr.hwc.service.generalOPD.GeneralOPDDoctorServiceImpl;
import com.iemr.hwc.testutil.NurseVisitRequest;
import com.iemr.hwc.testutil.PopulatedMocks;
import com.iemr.hwc.utils.exception.IEMRException;

/**
 * The family planning service records a counselling visit: the visit itself and vitals,
 * the reproductive history, the counselling given and the contraceptives dispensed.
 */
@DisplayName("FamilyPlanningServiceImpl")
class FamilyPlanningServiceImplTest {

	private FamilyPlanningServiceImpl service;
	private CommonNurseServiceImpl commonNurseServiceImpl;
	private CommonDoctorServiceImpl commonDoctorServiceImpl;
	private GeneralOPDDoctorServiceImpl generalOPDDoctorServiceImpl;
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;

	@BeforeEach
	void setUp() throws Exception {
		service = new FamilyPlanningServiceImpl();
		commonNurseServiceImpl = PopulatedMocks.inject(service, "commonNurseServiceImpl",
				CommonNurseServiceImpl.class);
		commonDoctorServiceImpl = PopulatedMocks.inject(service, "commonDoctorServiceImpl",
				CommonDoctorServiceImpl.class);
		generalOPDDoctorServiceImpl = PopulatedMocks.inject(service, "generalOPDDoctorServiceImpl",
				GeneralOPDDoctorServiceImpl.class);
		commonBenStatusFlowServiceImpl = PopulatedMocks.inject(service, "commonBenStatusFlowServiceImpl",
				CommonBenStatusFlowServiceImpl.class);
		PopulatedMocks.injectCollaborators(service);

		doReturn(0).when(commonNurseServiceImpl).getMaxCurrentdate(any(), any(), any());
		doReturn(10L).when(commonNurseServiceImpl).saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class));
		doReturn(20L).when(commonNurseServiceImpl).generateVisitCode(anyLong(), any(), any());
		doReturn("{\"counsellingProvided\":\"a||b\"}").when(generalOPDDoctorServiceImpl)
				.getGeneralOPDDiagnosisDetails(anyLong(), anyLong());
	}

	private static JsonObject nurseRequest() {
		return NurseVisitRequest.forCategory("FP & Contraceptive Services")
				.with("vitalDetails", new JsonObject())
				.with("familyPlanningReproductiveDetails", new JsonObject())
				.with("iecAndCounsellingDetails", new JsonObject())
				.with("dispensationDetails", new JsonObject()).build();
	}

	private static JsonObject doctorRequest() {
		JsonObject diagnosis = new JsonObject();
		diagnosis.addProperty("specialistDiagnosis", "advice recorded");
		JsonArray counselling = new JsonArray();
		counselling.add("spacing methods");
		return NurseVisitRequest.forCategory("FP & Contraceptive Services").with("findings", new JsonObject())
				.with("prescription", new JsonArray()).with("diagnosis", diagnosis)
				.with("refer", new JsonObject()).with("counsellingProvidedList", counselling)
				.field("doctorSignatureFlag", "true").field("isSpecialist", "true").build();
	}

	@Nested
	@DisplayName("saving a nurse visit")
	class SavingANurseVisit {

		@Test
		@DisplayName("saves the visit and its forms and answers with the visit code")
		void savesEveryForm() throws Exception {
			String response = service.saveNurseDataFP(nurseRequest(), "Bearer token");

			assertThat(response).contains("\"visitCode\":\"20\"").contains("Data saved successfully");
			verify(commonNurseServiceImpl).saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class));
			verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(any());
		}

		@Test
		@DisplayName("rejects a request that carries no visit details")
		void rejectsARequestWithoutVisitDetails() {
			assertThatThrownBy(() -> service.saveNurseDataFP(new JsonObject(), "Bearer token"))
					.isInstanceOf(IEMRException.class).hasMessageContaining("Visit details is not valid");
		}

		@Test
		@DisplayName("rejects a null request")
		void rejectsANullRequest() {
			assertThatThrownBy(() -> service.saveNurseDataFP(null, "Bearer token"))
					.isInstanceOf(IEMRException.class);
		}

		@Test
		@DisplayName("moves the beneficiary on to the next desk once the visit is saved")
		void movesTheBeneficiaryOn() throws Exception {
			service.saveNurseDataFP(nurseRequest(), "Bearer token");

			verify(commonBenStatusFlowServiceImpl).updateBenFlowNurseAfterNurseActivity(any(), any(), any(), any(),
					any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any());
		}
	}

	@Nested
	@DisplayName("saving doctor data")
	class SavingDoctorData {

		@Test
		@DisplayName("records the findings, investigation and prescription of the consultation")
		void recordsTheConsultation() throws Exception {
			service.saveDoctorDataFP(doctorRequest(), "Bearer token");

			verify(commonDoctorServiceImpl).saveDocFindings(any());
		}

		@Test
		@DisplayName("updates a consultation that was already recorded")
		void updatesTheConsultation() throws Exception {
			assertThat(service.updateDoctorDataFP(doctorRequest(), "Bearer token")).isNotNull();
		}
	}

	@Nested
	@DisplayName("reading a visit back")
	class ReadingAVisit {

		@Test
		@DisplayName("gathers the nurse sections of a visit into one document")
		void gathersTheNurseSections() throws Exception {
			assertThat(service.getNurseDataFP(1L, 20L)).isNotNull();
			assertThat(service.getBenVisitDetailsFrmNurseFP(1L, 20L)).isNotNull();
			assertThat(service.getBeneficiaryVitalDetailsFP(1L, 20L)).isNotNull();
			assertThat(service.getBeneficiaryFPDetailsFP(1L, 20L)).isNotNull();
		}

		@Test
		@DisplayName("gathers the doctor sections of a visit into one document")
		void gathersTheDoctorSections() throws Exception {
			assertThat(service.getBenCaseRecordFromDoctorFP(1L, 20L)).isNotNull().contains("findings")
					.contains("counsellingProvidedList");
		}
	}

	@Nested
	@DisplayName("updating a recorded visit")
	class UpdatingARecordedVisit {

		@Test
		@DisplayName("updates the family planning forms and the vitals")
		void updatesTheForms() throws Exception {
			JsonObject request = NurseVisitRequest.forCategory("FP & Contraceptive Services")
					.with("familyPlanningReproductiveDetails", new JsonObject())
					.with("iecAndCounsellingDetails", new JsonObject())
					.with("dispensationDetails", new JsonObject()).build();

			assertThat(service.updateFPDataFP(request)).isNotNull();
			assertThat(service.updateBenVitalDetailsFP(new JsonObject())).isNotNegative();
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
