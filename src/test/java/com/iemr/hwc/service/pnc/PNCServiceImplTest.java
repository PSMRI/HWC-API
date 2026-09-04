package com.iemr.hwc.service.pnc;

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
 * The postnatal service splits one nurse request into the visit, PNC, history, vital and
 * examination sections and moves the mother on to the next desk only when all of them
 * saved. Its collaborators are populated mocks, which report a saved record for each
 * section, so a test asks for the failure path by stubbing the section it cares about.
 */
@DisplayName("PNCServiceImpl")
class PNCServiceImplTest {

	private static final String[] HISTORY_SECTIONS = { "pastHistory", "comorbidConditions", "medicationHistory",
			"personalHistory", "familyHistory", "menstrualHistory", "femaleObstetricHistory", "immunizationHistory",
			"childVaccineDetails" };

	private static final String[] EXAMINATION_SECTIONS = { "generalExamination", "headToToeExamination",
			"gastroIntestinalExamination", "cardioVascularExamination", "respiratorySystemExamination",
			"centralNervousSystemExamination", "musculoskeletalSystemExamination",
			"genitoUrinarySystemExamination" };

	private PNCServiceImpl service;
	private CommonNurseServiceImpl commonNurseServiceImpl;
	private PNCNurseServiceImpl pncNurseServiceImpl;
	private PNCDoctorServiceImpl pncDoctorServiceImpl;
	private CommonDoctorServiceImpl commonDoctorServiceImpl;
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;

	@BeforeEach
	void setUp() throws Exception {
		service = new PNCServiceImpl();
		commonNurseServiceImpl = PopulatedMocks.inject(service, "commonNurseServiceImpl",
				CommonNurseServiceImpl.class);
		pncNurseServiceImpl = PopulatedMocks.inject(service, "pncNurseServiceImpl", PNCNurseServiceImpl.class);
		pncDoctorServiceImpl = PopulatedMocks.inject(service, "pncDoctorServiceImpl", PNCDoctorServiceImpl.class);
		commonDoctorServiceImpl = PopulatedMocks.inject(service, "commonDoctorServiceImpl",
				CommonDoctorServiceImpl.class);
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
		return NurseVisitRequest.forCategory("PNC").ordering(investigation)
				.with("pNCDeatils", new JsonObject())
				.with("historyDetails", HISTORY_SECTIONS)
				.with("vitalDetails", new JsonObject())
				.with("examinationDetails", EXAMINATION_SECTIONS)
				.build();
	}

	@Nested
	@DisplayName("saving a nurse visit")
	class SavingANurseVisit {

		@Test
		@DisplayName("saves every section and answers with the visit code")
		void savesEverySection() throws Exception {
			String response = service.savePNCNurseData(nurseRequest(), "Bearer token");

			assertThat(response).contains("\"visitCode\":\"20\"").contains("Data saved successfully");
			verify(commonNurseServiceImpl).saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class));
			verify(commonNurseServiceImpl).saveBenPastHistory(any());
			verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(any());
			verify(commonNurseServiceImpl).savePhyGeneralExamination(any());
			verify(pncNurseServiceImpl).saveBenPncCareDetails(any());
		}

		@Test
		@DisplayName("reports the visit as already captured when one is recorded for the same reason")
		void reportsAnAlreadyCapturedVisit() throws Exception {
			doReturn(1).when(commonNurseServiceImpl).getMaxCurrentdate(any(), any(), any());

			assertThat(service.savePNCNurseData(nurseRequest(), "Bearer token")).contains("Data already saved");
		}

		@Test
		@DisplayName("rejects a request that carries no visit details")
		void rejectsARequestWithoutVisitDetails() {
			assertThatThrownBy(() -> service.savePNCNurseData(new JsonObject(), "Bearer token"))
					.isInstanceOf(Exception.class).hasMessage("Invalid input");
		}

		@Test
		@DisplayName("fails the save when a section reports nothing saved")
		void failsWhenASectionDoesNotSave() throws Exception {
			doReturn(null).when(commonNurseServiceImpl).saveBenPastHistory(any());

			assertThatThrownBy(() -> service.savePNCNurseData(nurseRequest(), "Bearer token"))
					.isInstanceOf(RuntimeException.class).hasMessageContaining("Error occurred while saving data");
		}

		@Test
		@DisplayName("sends the mother on to the doctor, which is the only desk after a PNC visit")
		void routesToTheDoctor() throws Exception {
			service.savePNCNurseData(nurseRequest(NurseVisitRequest.investigationOrderingTests()), "Bearer token");

			verify(commonBenStatusFlowServiceImpl).updateBenFlowNurseAfterNurseActivity(any(), any(), any(), any(),
					any(), eq((short) 9), eq((short) 1), eq((short) 0), any(), any(), any(), any(), any(), any(),
					any());
		}

		@Test
		@DisplayName("leaves the specialist flag clear when no teleconsultation was raised")
		void leavesTheSpecialistFlagClear() throws Exception {
			doReturn(null).when(
					(com.iemr.hwc.service.common.transaction.CommonServiceImpl) org.springframework.test.util.ReflectionTestUtils
							.getField(service, "commonServiceImpl"))
					.createTcRequest(any(), any(), org.mockito.ArgumentMatchers.anyString());

			service.savePNCNurseData(nurseRequest(), "Bearer token");

			verify(commonBenStatusFlowServiceImpl).updateBenFlowNurseAfterNurseActivity(any(), any(), any(), any(),
					any(), any(), any(), any(), any(), any(), any(), any(), eq((short) 0), any(), any());
		}

		@Test
		@DisplayName("marks the visit for a specialist when the teleconsultation is already allocated")
		void marksTheVisitForASpecialist() throws Exception {
			com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ tcRequest = org.mockito.Mockito
					.mock(com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ.class);
			doReturn(7).when(tcRequest).getUserID();
			doReturn(new java.sql.Timestamp(System.currentTimeMillis())).when(tcRequest).getAllocationDate();
			doReturn(true).when(tcRequest).getWalkIn();
			doReturn(tcRequest).when(
					(com.iemr.hwc.service.common.transaction.CommonServiceImpl) org.springframework.test.util.ReflectionTestUtils
							.getField(service, "commonServiceImpl"))
					.createTcRequest(any(), any(), org.mockito.ArgumentMatchers.anyString());

			service.savePNCNurseData(nurseRequest(), "Bearer token");

			verify(commonBenStatusFlowServiceImpl).updateBenFlowNurseAfterNurseActivity(any(), any(), any(), any(),
					any(), any(), any(), any(), any(), any(), any(), any(), eq((short) 1), any(), any());
		}
	}

	@Nested
	@DisplayName("saving doctor data")
	class SavingDoctorData {

		private JsonObject doctorRequest() {
			JsonObject diagnosis = new JsonObject();
			diagnosis.addProperty("specialistDiagnosis", "advice recorded");
			return NurseVisitRequest.forCategory("PNC").with("findings", new JsonObject())
					.with("prescription", new JsonArray()).with("diagnosis", diagnosis)
					.with("refer", new JsonObject()).field("doctorSignatureFlag", "true").build();
		}

		@Test
		@DisplayName("records the findings, investigation and prescription of the consultation")
		void recordsTheConsultation() throws Exception {
			assertThat(service.savePNCDoctorData(doctorRequest(), "Bearer token")).isNotNull();

			verify(commonDoctorServiceImpl).saveDocFindings(any());
		}

		@Test
		@DisplayName("updates a consultation that was already recorded")
		void updatesTheConsultation() throws Exception {
			assertThat(service.updatePNCDoctorData(doctorRequest(), "Bearer token")).isNotNull();
		}
	}

	@Nested
	@DisplayName("reading a case record back")
	class ReadingACaseRecord {

		@Test
		@DisplayName("gathers the nurse sections of a visit into one document")
		void gathersTheNurseSections() throws Exception {
			assertThat(service.getBenPNCNurseData(1L, 20L)).isNotNull().contains("history");
		}

		@Test
		@DisplayName("gathers the doctor sections of a visit into one document")
		void gathersTheDoctorSections() throws Exception {
			doReturn("{\"counsellingProvided\":\"a||b\"}").when(pncDoctorServiceImpl).getPNCDiagnosisDetails(anyLong(),
					anyLong());

			assertThat(service.getBenCaseRecordFromDoctorPNC(1L, 20L)).isNotNull().contains("diagnosis")
					.contains("counsellingProvidedList");
		}
	}

	@Nested
	@DisplayName("updating a recorded visit")
	class UpdatingARecordedVisit {

		@Test
		@DisplayName("updates the history, vital, examination and PNC sections")
		void updatesTheSections() throws Exception {
			assertThat(service.updateBenHistoryDetails(NurseVisitRequest.sections(HISTORY_SECTIONS)))
					.isNotNegative();
			assertThat(service.updateBenVitalDetails(new JsonObject())).isNotNegative();
			assertThat(service.updateBenExaminationDetails(NurseVisitRequest.sections(EXAMINATION_SECTIONS)))
					.isNotNegative();
			assertThat(service.updateBenPNCDetails(NurseVisitRequest.sections("PNCDetails"))).isNotNegative();
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
