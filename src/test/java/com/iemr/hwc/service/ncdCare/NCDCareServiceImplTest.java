package com.iemr.hwc.service.ncdCare;

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
 * The NCD care service handles a follow-up visit for a beneficiary already on treatment:
 * one request carrying the visit, the history and the vitals, after which the beneficiary
 * goes to the lab and the doctor, or to the doctor alone when no test was ordered.
 */
@DisplayName("NCDCareServiceImpl")
class NCDCareServiceImplTest {

	private NCDCareServiceImpl service;
	private CommonNurseServiceImpl commonNurseServiceImpl;
	private CommonDoctorServiceImpl commonDoctorServiceImpl;
	private NCDCareDoctorServiceImpl ncdCareDoctorServiceImpl;
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;

	@BeforeEach
	void setUp() throws Exception {
		service = new NCDCareServiceImpl();
		commonNurseServiceImpl = PopulatedMocks.inject(service, "commonNurseServiceImpl",
				CommonNurseServiceImpl.class);
		commonDoctorServiceImpl = PopulatedMocks.inject(service, "commonDoctorServiceImpl",
				CommonDoctorServiceImpl.class);
		ncdCareDoctorServiceImpl = PopulatedMocks.inject(service, "ncdCareDoctorServiceImpl",
				NCDCareDoctorServiceImpl.class);
		commonBenStatusFlowServiceImpl = PopulatedMocks.inject(service, "commonBenStatusFlowServiceImpl",
				CommonBenStatusFlowServiceImpl.class);
		PopulatedMocks.injectCollaborators(service);

		doReturn(0).when(commonNurseServiceImpl).getMaxCurrentdate(any(), any(), any());
		doReturn(10L).when(commonNurseServiceImpl).saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class));
		doReturn(20L).when(commonNurseServiceImpl).generateVisitCode(anyLong(), any(), any());
	}

	private static JsonObject nurseRequest() {
		return nurseRequest(NurseVisitRequest.investigationOrderingTests());
	}

	private static JsonObject nurseRequest(JsonObject investigation) {
		return NurseVisitRequest.forCategory("NCD care").ordering(investigation)
				.with("historyDetails", NurseVisitRequest.HISTORY_SECTIONS)
				.with("vitalDetails", new JsonObject()).build();
	}

	@Nested
	@DisplayName("saving a nurse visit")
	class SavingANurseVisit {

		@Test
		@DisplayName("saves the visit, history and vitals and answers with the visit code")
		void savesEverySection() throws Exception {
			String response = service.saveNCDCareNurseData(nurseRequest(), "Bearer token");

			assertThat(response).contains("\"visitCode\":\"20\"").contains("Data saved successfully");
			verify(commonNurseServiceImpl).saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class));
			verify(commonNurseServiceImpl).saveBenPastHistory(any());
			verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(any());
		}

		@Test
		@DisplayName("reports the visit as already captured when one is recorded for the same reason")
		void reportsAnAlreadyCapturedVisit() throws Exception {
			doReturn(1).when(commonNurseServiceImpl).getMaxCurrentdate(any(), any(), any());

			assertThat(service.saveNCDCareNurseData(nurseRequest(), "Bearer token")).contains("Data already saved");
		}

		@Test
		@DisplayName("rejects a request that carries no visit details")
		void rejectsARequestWithoutVisitDetails() {
			assertThatThrownBy(() -> service.saveNCDCareNurseData(new JsonObject(), "Bearer token"))
					.isInstanceOf(Exception.class).hasMessage("Invalid input");
		}

		@Test
		@DisplayName("fails the save when the history reports nothing saved")
		void failsWhenASectionDoesNotSave() throws Exception {
			doReturn(null).when(commonNurseServiceImpl).saveBenPastHistory(any());

			assertThatThrownBy(() -> service.saveNCDCareNurseData(nurseRequest(), "Bearer token"))
					.isInstanceOf(RuntimeException.class).hasMessageContaining("Error occurred while saving data");
		}

		@Test
		@DisplayName("sends the beneficiary to the lab as well as the doctor when tests were ordered")
		void routesToTheLabWhenTestsWereOrdered() throws Exception {
			service.saveNCDCareNurseData(nurseRequest(NurseVisitRequest.investigationOrderingTests()),
					"Bearer token");

			verify(commonBenStatusFlowServiceImpl).updateBenFlowNurseAfterNurseActivity(any(), any(), any(),
					any(), any(), eq((short) 2), eq((short) 0), eq((short) 1), any(), any(), any(), any(), any(),
					any(), any());
		}

		@Test
		@DisplayName("sends the beneficiary straight to the doctor when no tests were ordered")
		void routesToTheDoctorWhenNoTestsWereOrdered() throws Exception {
			service.saveNCDCareNurseData(nurseRequest(NurseVisitRequest.investigationOrderingNothing()),
					"Bearer token");

			verify(commonBenStatusFlowServiceImpl).updateBenFlowNurseAfterNurseActivity(any(), any(), any(),
					any(), any(), eq((short) 9), eq((short) 1), eq((short) 0), any(), any(), any(), any(), any(),
					any(), any());
		}
	}

	@Nested
	@DisplayName("saving doctor data")
	class SavingDoctorData {

		private JsonObject doctorRequest() {
			JsonObject diagnosis = new JsonObject();
			diagnosis.addProperty("specialistDiagnosis", "advice recorded");
			diagnosis.add("provisionalDiagnosisList", new JsonArray());
			return NurseVisitRequest.forCategory("NCD care").with("findings", new JsonObject())
					.with("prescription", new JsonArray()).with("diagnosis", diagnosis)
					.with("refer", new JsonObject()).field("doctorSignatureFlag", "true").build();
		}

		@Test
		@DisplayName("records the findings, investigation and prescription of the consultation")
		void recordsTheConsultation() throws Exception {
			assertThat(service.saveDoctorData(doctorRequest(), "Bearer token")).isNotNull();

			verify(commonDoctorServiceImpl).saveDocFindings(any());
		}

		@Test
		@DisplayName("updates a consultation that was already recorded")
		void updatesTheConsultation() throws Exception {
			assertThat(service.updateNCDCareDoctorData(doctorRequest(), "Bearer token")).isNotNull();
		}
	}

	@Nested
	@DisplayName("reading a case record back")
	class ReadingACaseRecord {

		@Test
		@DisplayName("gathers the nurse sections of a visit into one document")
		void gathersTheNurseSections() throws Exception {
			assertThat(service.getBenNCDCareNurseData(1L, 20L)).isNotNull().contains("history");
		}

		@Test
		@DisplayName("gathers the doctor sections of a visit into one document")
		void gathersTheDoctorSections() throws Exception {
			doReturn("{\"counsellingProvided\":\"a||b\"}").when(ncdCareDoctorServiceImpl)
					.getNCDCareDiagnosisDetails(anyLong(), anyLong());

			assertThat(service.getBenCaseRecordFromDoctorNCDCare(1L, 20L)).isNotNull().contains("diagnosis")
					.contains("counsellingProvidedList");
		}
	}

	@Nested
	@DisplayName("updating a recorded visit")
	class UpdatingARecordedVisit {

		@Test
		@DisplayName("updates the history and the vitals")
		void updatesTheSections() throws Exception {
			assertThat(service.updateBenHistoryDetails(
					NurseVisitRequest.sections(NurseVisitRequest.HISTORY_SECTIONS))).isNotNegative();
			assertThat(service.updateBenVitalDetails(new JsonObject())).isNotNegative();
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
