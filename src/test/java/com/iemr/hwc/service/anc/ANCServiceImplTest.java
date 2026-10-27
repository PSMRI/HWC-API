package com.iemr.hwc.service.anc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.iemr.hwc.data.anc.WrapperAncFindings;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonServiceImpl;
import com.iemr.hwc.testutil.PopulatedMocks;

/**
 * The ANC service is the nurse and doctor entry point for an antenatal visit: it takes one
 * request document, splits it into the visit, ANC, history, vital and examination sections,
 * hands each to the service that owns it, and moves the beneficiary on to the next desk
 * only when every section saved.
 *
 * <p>The collaborators are populated mocks, which report a saved record for every section,
 * so a test that wants the "nothing saved" path says so by stubbing that one section.
 */
@DisplayName("ANCServiceImpl")
class ANCServiceImplTest {

	private ANCServiceImpl service;
	private CommonNurseServiceImpl commonNurseServiceImpl;
	private ANCNurseServiceImpl ancNurseServiceImpl;
	private ANCDoctorServiceImpl ancDoctorServiceImpl;
	private CommonDoctorServiceImpl commonDoctorServiceImpl;
	private CommonServiceImpl commonServiceImpl;
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;

	@BeforeEach
	void setUp() throws Exception {
		service = new ANCServiceImpl();
		commonNurseServiceImpl = PopulatedMocks.of(CommonNurseServiceImpl.class);
		ancNurseServiceImpl = PopulatedMocks.of(ANCNurseServiceImpl.class);
		ancDoctorServiceImpl = PopulatedMocks.of(ANCDoctorServiceImpl.class);
		commonDoctorServiceImpl = PopulatedMocks.of(CommonDoctorServiceImpl.class);
		commonServiceImpl = PopulatedMocks.of(CommonServiceImpl.class);
		commonBenStatusFlowServiceImpl = PopulatedMocks.of(CommonBenStatusFlowServiceImpl.class);

		service.setCommonNurseServiceImpl(commonNurseServiceImpl);
		service.setAncNurseServiceImpl(ancNurseServiceImpl);
		service.setANCDoctorServiceImpl(ancDoctorServiceImpl);
		service.setCommonDoctorServiceImpl(commonDoctorServiceImpl);
		service.setCommonBenStatusFlowServiceImpl(commonBenStatusFlowServiceImpl);
		ReflectionTestUtils.setField(service, "commonServiceImpl", commonServiceImpl);
		PopulatedMocks.injectCollaborators(service);

		// A first visit of the day: no visit of this reason and category is recorded yet, so
		// the save proceeds instead of reporting the visit as already captured.
		doReturn(0).when(commonNurseServiceImpl).getMaxCurrentdate(any(), any(), any());
		doReturn(10L).when(commonNurseServiceImpl).saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class));
		doReturn(20L).when(commonNurseServiceImpl).generateVisitCode(anyLong(), any(), any());
	}

	private static JsonObject withKeys(String... keys) {
		JsonObject json = new JsonObject();
		for (String key : keys) {
			json.add(key, new JsonObject());
		}
		return json;
	}

	private static JsonObject visitRecord() {
		JsonObject visit = new JsonObject();
		visit.addProperty("beneficiaryRegID", 1L);
		visit.addProperty("visitReason", "New Chief Complaint");
		visit.addProperty("visitCategory", "ANC");
		visit.addProperty("createdBy", "nurse1");
		visit.addProperty("vanID", 1);
		visit.addProperty("parkingPlaceID", 1);
		return visit;
	}

	private static JsonObject investigationWithTests() {
		JsonObject investigation = new JsonObject();
		JsonArray laboratoryList = new JsonArray();
		laboratoryList.add(new JsonObject());
		investigation.add("laboratoryList", laboratoryList);
		return investigation;
	}

	/** A nurse request carrying every section the ANC save reads. */
	private static JsonObject nurseRequest(JsonObject investigation) {
		JsonObject visitDetails = new JsonObject();
		visitDetails.add("visitDetails", visitRecord());
		visitDetails.add("chiefComplaints", new JsonArray());
		visitDetails.add("adherence", new JsonObject());
		visitDetails.add("investigation", investigation);

		JsonObject request = new JsonObject();
		request.addProperty("beneficiaryRegID", 1L);
		request.addProperty("benFlowID", 5L);
		request.addProperty("vanID", 1);
		request.addProperty("sessionID", 1);
		request.addProperty("parkingPlaceID", 1);
		request.addProperty("createdBy", "nurse1");
		request.add("visitDetails", visitDetails);
		request.add("ancDetails", withKeys("ancObstetricDetails", "ancImmunization"));
		request.add("historyDetails", withKeys("pastHistory", "comorbidConditions", "medicationHistory",
				"personalHistory", "familyHistory", "menstrualHistory", "femaleObstetricHistory",
				"immunizationHistory", "childVaccineDetails"));
		request.add("vitalDetails", new JsonObject());
		request.add("examinationDetails", withKeys("generalExamination", "headToToeExamination",
				"cardioVascularExamination", "respiratorySystemExamination", "centralNervousSystemExamination",
				"musculoskeletalSystemExamination", "genitoUrinarySystemExamination", "obstetricExamination"));
		return request;
	}

	@Nested
	@DisplayName("saving a nurse visit")
	class SavingANurseVisit {

		@Test
		@DisplayName("saves every section and answers with the visit code")
		void savesEverySection() throws Exception {
			String response = service.saveANCNurseData(nurseRequest(investigationWithTests()), "Bearer token");

			assertThat(response).contains("\"visitCode\":\"20\"").contains("Data saved successfully");
			verify(commonNurseServiceImpl).saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class));
			verify(ancNurseServiceImpl).saveBenAncCareDetails(any());
			verify(commonNurseServiceImpl).saveBenPastHistory(any());
			verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(any());
			verify(commonNurseServiceImpl).savePhyGeneralExamination(any());
			verify(ancNurseServiceImpl).saveSysObstetricExamination(any());
		}

		@Test
		@DisplayName("stamps the visit it created onto the visit code of the request")
		void stampsTheVisitCode() throws Exception {
			service.saveANCNurseData(nurseRequest(investigationWithTests()), "Bearer token");

			verify(commonNurseServiceImpl).generateVisitCode(10L, 1, 1);
		}

		@Test
		@DisplayName("reports the visit as already captured when one is recorded for the same reason")
		void reportsAnAlreadyCapturedVisit() throws Exception {
			doReturn(1).when(commonNurseServiceImpl).getMaxCurrentdate(any(), any(), any());

			String response = service.saveANCNurseData(nurseRequest(investigationWithTests()), "Bearer token");

			assertThat(response).contains("Data already saved");
		}

		@Test
		@DisplayName("rejects a request that carries no visit details")
		void rejectsARequestWithoutVisitDetails() {
			assertThatThrownBy(() -> service.saveANCNurseData(new JsonObject(), "Bearer token"))
					.isInstanceOf(Exception.class).hasMessage("Invalid input");
		}

		@Test
		@DisplayName("rejects a null request")
		void rejectsANullRequest() {
			assertThatThrownBy(() -> service.saveANCNurseData(null, "Bearer token"))
					.isInstanceOf(Exception.class).hasMessage("Invalid input");
		}

		@Test
		@DisplayName("fails the save when a section reports nothing saved, rather than moving the beneficiary on")
		void failsWhenASectionDoesNotSave() throws Exception {
			doReturn(null).when(ancNurseServiceImpl).saveBenAncCareDetails(any());

			assertThatThrownBy(() -> service.saveANCNurseData(nurseRequest(investigationWithTests()), "Bearer token"))
					.isInstanceOf(RuntimeException.class).hasMessageContaining("Error occurred while saving data");
		}

		@Test
		@DisplayName("fails the save when the beneficiary status cannot be moved on")
		void failsWhenTheStatusUpdateFails() throws Exception {
			doReturn(0).when(commonBenStatusFlowServiceImpl).updateBenFlowNurseAfterNurseActivityANC(any(), any(),
					any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any());

			assertThatThrownBy(() -> service.saveANCNurseData(nurseRequest(investigationWithTests()), "Bearer token"))
					.isInstanceOf(RuntimeException.class)
					.hasMessageContaining("Beneficiary status update failed");
		}

		@Test
		@DisplayName("sends the beneficiary to the lab as well as the doctor when tests were ordered")
		void routesToTheLabWhenTestsWereOrdered() throws Exception {
			service.saveANCNurseData(nurseRequest(investigationWithTests()), "Bearer token");

			verify(commonBenStatusFlowServiceImpl).updateBenFlowNurseAfterNurseActivityANC(any(), any(), any(), any(),
					any(), org.mockito.ArgumentMatchers.eq((short) 2), org.mockito.ArgumentMatchers.eq((short) 0),
					org.mockito.ArgumentMatchers.eq((short) 1), any(), any(), any(), any(), any(), any(), any(),
					any());
		}

		@Test
		@DisplayName("sends the beneficiary straight to the doctor when no tests were ordered")
		void routesToTheDoctorWhenNoTestsWereOrdered() throws Exception {
			JsonObject noTests = new JsonObject();
			noTests.add("laboratoryList", new JsonArray());

			service.saveANCNurseData(nurseRequest(noTests), "Bearer token");

			verify(commonBenStatusFlowServiceImpl).updateBenFlowNurseAfterNurseActivityANC(any(), any(), any(), any(),
					any(), org.mockito.ArgumentMatchers.eq((short) 9), org.mockito.ArgumentMatchers.eq((short) 1),
					org.mockito.ArgumentMatchers.eq((short) 0), any(), any(), any(), any(), any(), any(), any(),
					any());
		}

		@Test
		@DisplayName("schedules the teleconsultation SMS for a request that is not a walk-in")
		void schedulesTheTeleconsultationSms() throws Exception {
			TeleconsultationRequestOBJ tcRequest = mock(TeleconsultationRequestOBJ.class);
			doReturn(false).when(tcRequest).getWalkIn();
			doReturn(7).when(tcRequest).getUserID();
			doReturn(new java.sql.Timestamp(System.currentTimeMillis())).when(tcRequest).getAllocationDate();
			doReturn(tcRequest).when(commonServiceImpl).createTcRequest(any(), any(), anyString());

			service.saveANCNurseData(nurseRequest(investigationWithTests()), "Bearer token");

			verify(commonServiceImpl).createTcRequest(any(), any(), anyString());
		}
	}

	@Nested
	@DisplayName("saving doctor data")
	class SavingDoctorData {

		private JsonObject doctorRequest() {
			JsonObject request = new JsonObject();
			request.addProperty("beneficiaryRegID", 1L);
			request.addProperty("benVisitID", 10L);
			request.addProperty("visitCode", 20L);
			request.addProperty("createdBy", "doctor1");
			request.addProperty("doctorSignatureFlag", true);
			request.add("findings", new JsonObject());
			request.add("investigation", investigationWithTests());
			request.add("prescription", new JsonArray());
			JsonObject diagnosis = new JsonObject();
			diagnosis.addProperty("specialistDiagnosis", "advice recorded");
			request.add("diagnosis", diagnosis);
			return request;
		}

		@Test
		@DisplayName("records the findings, investigation and prescription of the consultation")
		void recordsTheConsultation() throws Exception {
			Long saved = service.saveANCDoctorData(doctorRequest(), "Bearer token");

			assertThat(saved).isNotNull();
			verify(commonDoctorServiceImpl).saveDocFindings(any(WrapperAncFindings.class));
		}

		@Test
		@DisplayName("requires the investigation section, which it reads without a null check")
		void requiresTheInvestigationSection() {
			JsonObject request = new JsonObject();
			request.addProperty("beneficiaryRegID", 1L);
			request.addProperty("benVisitID", 10L);
			request.addProperty("visitCode", 20L);
			request.addProperty("createdBy", "doctor1");

			// The section is mapped and then dereferenced unguarded, so a request without it
			// fails rather than saving the rest of the consultation. Asserted so the
			// requirement is explicit for a caller building the request.
			assertThatThrownBy(() -> service.saveANCDoctorData(request, "Bearer token"))
					.isInstanceOf(NullPointerException.class);
		}

		@Test
		@DisplayName("records a consultation carrying only the investigation section")
		void recordsAMinimalConsultation() throws Exception {
			JsonObject request = new JsonObject();
			request.addProperty("beneficiaryRegID", 1L);
			request.addProperty("benVisitID", 10L);
			request.addProperty("visitCode", 20L);
			request.addProperty("createdBy", "doctor1");
			request.add("investigation", investigationWithTests());

			assertThat(service.saveANCDoctorData(request, "Bearer token")).isNotNull();
		}
	}

	@Nested
	@DisplayName("reading a case record back")
	class ReadingACaseRecord {

		@Test
		@DisplayName("gathers the nurse sections of a visit into one document")
		void gathersTheNurseSections() throws Exception {
			String record = service.getBenANCNurseData(1L, 20L);

			assertThat(record).isNotNull().contains("anc").contains("examination").contains("history");
		}

		@Test
		@DisplayName("gathers the doctor sections of a visit into one document")
		void gathersTheDoctorSections() throws Exception {
			doReturn("{\"counsellingProvided\":\"a||b\"}").when(ancDoctorServiceImpl).getANCDiagnosisDetails(anyLong(),
					anyLong());

			String record = service.getBenCaseRecordFromDoctorANC(1L, 20L);

			assertThat(record).isNotNull().contains("findings").contains("diagnosis")
					.contains("counsellingProvidedList");
		}

		@Test
		@DisplayName("leaves the counselling list out when the diagnosis records none")
		void leavesTheCounsellingListOut() throws Exception {
			doReturn("{}").when(ancDoctorServiceImpl).getANCDiagnosisDetails(anyLong(), anyLong());

			assertThat(service.getBenCaseRecordFromDoctorANC(1L, 20L)).doesNotContain("counsellingProvidedList");
		}
	}

	@Nested
	@DisplayName("rolling a failed save back")
	class RollingAFailedSaveBack {

		@Test
		@DisplayName("deletes the records a failed nurse save had already written")
		void deletesTheRecordsOfAFailedSave() throws Exception {
			BenVisitDetailRepo benVisitDetailRepo = PopulatedMocks.of(BenVisitDetailRepo.class);
			BenChiefComplaintRepo benChiefComplaintRepo = PopulatedMocks.of(BenChiefComplaintRepo.class);
			doReturn(20L).when(benVisitDetailRepo).getVisitCode(any(), any());
			ReflectionTestUtils.setField(service, "benVisitDetailRepo", benVisitDetailRepo);
			ReflectionTestUtils.setField(service, "benChiefComplaintRepo", benChiefComplaintRepo);

			service.deleteVisitDetails(nurseRequest(investigationWithTests()));

			verify(benChiefComplaintRepo).deleteVisitDetails(20L);
			verify(benVisitDetailRepo).deleteVisitDetails(20L);
		}

		@Test
		@DisplayName("deletes nothing when no visit was recorded for the beneficiary")
		void deletesNothingWithoutAVisitCode() throws Exception {
			BenVisitDetailRepo benVisitDetailRepo = PopulatedMocks.of(BenVisitDetailRepo.class);
			doReturn(null).when(benVisitDetailRepo).getVisitCode(any(), any());
			ReflectionTestUtils.setField(service, "benVisitDetailRepo", benVisitDetailRepo);

			service.deleteVisitDetails(nurseRequest(investigationWithTests()));

			verify(benVisitDetailRepo, org.mockito.Mockito.never()).deleteVisitDetails(anyLong());
		}

		@Test
		@DisplayName("does nothing for a request that never created a visit")
		void doesNothingWithoutVisitDetails() throws Exception {
			BenVisitDetailRepo benVisitDetailRepo = PopulatedMocks.of(BenVisitDetailRepo.class);
			ReflectionTestUtils.setField(service, "benVisitDetailRepo", benVisitDetailRepo);

			service.deleteVisitDetails(new JsonObject());

			verify(benVisitDetailRepo, org.mockito.Mockito.never()).getVisitCode(any(), any());
		}
	}
}
