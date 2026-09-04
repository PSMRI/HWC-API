package com.iemr.hwc.service.ncdscreening;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
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
import com.iemr.hwc.repo.nurse.ncdscreening.BreastCancerScreeningRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.CbacDetailsRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.CervicalCancerScreeningRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.DiabetesScreeningRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.HypertensionScreeningRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.OralCancerScreeningRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.testutil.NurseVisitRequest;
import com.iemr.hwc.testutil.PopulatedMocks;

/**
 * The NCD screening service records a screening visit: the visit itself, the history and
 * vitals, the CBAC and IDRS questionnaires, the physical activity history and the four
 * screening forms - diabetes, hypertension, oral, breast and cervical.
 */
@DisplayName("NCDScreeningServiceImpl")
class NCDScreeningServiceImplTest {

	private NCDScreeningServiceImpl service;
	private CommonNurseServiceImpl commonNurseServiceImpl;
	private CommonDoctorServiceImpl commonDoctorServiceImpl;
	private NCDScreeningNurseServiceImpl ncdScreeningNurseServiceImpl;
	private NCDSCreeningDoctorServiceImpl ncdSCreeningDoctorServiceImpl;
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;

	@BeforeEach
	void setUp() throws Exception {
		service = new NCDScreeningServiceImpl();
		commonNurseServiceImpl = PopulatedMocks.inject(service, "commonNurseServiceImpl",
				CommonNurseServiceImpl.class);
		commonDoctorServiceImpl = PopulatedMocks.inject(service, "commonDoctorServiceImpl",
				CommonDoctorServiceImpl.class);
		ncdScreeningNurseServiceImpl = PopulatedMocks.inject(service, "ncdScreeningNurseServiceImpl",
				NCDScreeningNurseServiceImpl.class);
		ncdSCreeningDoctorServiceImpl = PopulatedMocks.inject(service, "ncdSCreeningDoctorServiceImpl",
				NCDSCreeningDoctorServiceImpl.class);
		commonBenStatusFlowServiceImpl = PopulatedMocks.inject(service, "commonBenStatusFlowServiceImpl",
				CommonBenStatusFlowServiceImpl.class);
		PopulatedMocks.injectCollaborators(service);

		// Each screening form is saved through its own repository and the service then reads
		// the generated id back to decide whether the save worked, so the saved records have
		// to come back with one.
		stampIdsOnSavedScreeningForms();

		doReturn(0).when(commonNurseServiceImpl).getMaxCurrentdate(any(), any(), any());
		doReturn(10L).when(commonNurseServiceImpl).saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class));
		doReturn(20L).when(commonNurseServiceImpl).generateVisitCode(anyLong(), any(), any());
	}

	/**
	 * Makes every screening repository hand back a record carrying an id, the way a real save
	 * does. The populated mocks echo the record they were given, which has no id yet.
	 */
	private void stampIdsOnSavedScreeningForms() {
		CbacDetailsRepo cbacDetailsRepo = PopulatedMocks.inject(service, "cbacDetailsRepo", CbacDetailsRepo.class);
		doAnswer(saveWithId(1L)).when(cbacDetailsRepo).save(any());
		BreastCancerScreeningRepo breastRepo = PopulatedMocks.inject(service, "breastCancerScreeningRepo",
				BreastCancerScreeningRepo.class);
		doAnswer(saveWithId(1L)).when(breastRepo).save(any());
		CervicalCancerScreeningRepo cervicalRepo = PopulatedMocks.inject(service, "cervicalCancerScreeningRepo",
				CervicalCancerScreeningRepo.class);
		doAnswer(saveWithId(1L)).when(cervicalRepo).save(any());
		DiabetesScreeningRepo diabetesRepo = PopulatedMocks.inject(service, "diabetesScreeningRepo",
				DiabetesScreeningRepo.class);
		doAnswer(saveWithId(1L)).when(diabetesRepo).save(any());
		HypertensionScreeningRepo hypertensionRepo = PopulatedMocks.inject(service, "hypertensionScreeningRepo",
				HypertensionScreeningRepo.class);
		doAnswer(saveWithId(1L)).when(hypertensionRepo).save(any());
		OralCancerScreeningRepo oralRepo = PopulatedMocks.inject(service, "oralCancerScreeningRepo",
				OralCancerScreeningRepo.class);
		doAnswer(saveWithId(1L)).when(oralRepo).save(any());
	}

	/** Echoes the saved record back with {@code id} set, as the database would. */
	private static org.mockito.stubbing.Answer<Object> saveWithId(Long id) {
		return invocation -> {
			Object saved = invocation.getArgument(0);
			org.springframework.test.util.ReflectionTestUtils.setField(saved, "id", id);
			return saved;
		};
	}

	/** A screening request carrying every form the save reads. */
	private static JsonObject nurseRequest() {
		// The physical activity form is filled in as part of the history, so the service reads
		// it from inside that section rather than from the top of the request.
		JsonObject historyDetails = NurseVisitRequest.sections(NurseVisitRequest.HISTORY_SECTIONS);
		historyDetails.add("physicalActivityHistory", new JsonObject());

		return NurseVisitRequest.forCategory("NCD screening").with("historyDetails", historyDetails)
				.with("vitalDetails", new JsonObject()).with("idrsDetails", new JsonObject())
				.with("cbac", new JsonObject())
				.with("diabetes", new JsonObject()).with("hypertension", new JsonObject())
				.with("oral", new JsonObject()).with("breast", new JsonObject())
				.with("cervical", new JsonObject()).build();
	}

	private static JsonObject doctorRequest() {
		JsonObject diagnosis = new JsonObject();
		diagnosis.addProperty("specialistDiagnosis", "advice recorded");
		diagnosis.add("provisionalDiagnosisList", new JsonArray());
		return NurseVisitRequest.forCategory("NCD screening").with("findings", new JsonObject())
				.with("prescription", new JsonArray()).with("diagnosis", diagnosis)
				.with("refer", new JsonObject()).field("doctorSignatureFlag", "true")
				.field("isSpecialist", "true").build();
	}

	@Nested
	@DisplayName("saving a nurse visit")
	class SavingANurseVisit {

		@Test
		@DisplayName("saves every screening form and answers with the visit code and visit id")
		void savesEveryScreeningForm() throws Exception {
			String response = service.saveNCDScreeningNurseData(nurseRequest(), "Bearer token");

			assertThat(response).contains("\"visitCode\":\"20\"").contains("\"benVisitID\":\"10\"")
					.contains("Data saved successfully");
			verify(commonNurseServiceImpl).saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class));
			verify(commonNurseServiceImpl).savePhysicalActivity(any());
			verify(commonNurseServiceImpl).saveIDRS(any());
		}

		@Test
		@DisplayName("reports the visit as already captured when one is recorded for the same reason")
		void reportsAnAlreadyCapturedVisit() throws Exception {
			doReturn(1).when(commonNurseServiceImpl).getMaxCurrentdate(any(), any(), any());

			assertThat(service.saveNCDScreeningNurseData(nurseRequest(), "Bearer token"))
					.contains("Data already saved");
		}

		@Test
		@DisplayName("rejects a request that carries no visit details")
		void rejectsARequestWithoutVisitDetails() {
			assertThatThrownBy(() -> service.saveNCDScreeningNurseData(new JsonObject(), "Bearer token"))
					.isInstanceOf(Exception.class).hasMessage("Invalid input");
		}

		@Test
		@DisplayName("saves a visit whose screening forms were left blank")
		void savesAVisitWithoutScreeningForms() throws Exception {
			JsonObject request = NurseVisitRequest.forCategory("NCD screening")
					.with("historyDetails", NurseVisitRequest.HISTORY_SECTIONS)
					.with("vitalDetails", new JsonObject()).build();

			assertThat(service.saveNCDScreeningNurseData(request, "Bearer token")).contains("visitCode");
		}

		@Test
		@DisplayName("moves the beneficiary on to the next desk once the visit is saved")
		void movesTheBeneficiaryOn() throws Exception {
			service.saveNCDScreeningNurseData(nurseRequest(), "Bearer token");

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
			assertThat(service.saveDoctorData(doctorRequest(), "Bearer token")).isNotNull();

			verify(commonDoctorServiceImpl).saveDocFindings(any());
		}
	}

	@Nested
	@DisplayName("reading a screening record back")
	class ReadingAScreeningRecord {

		@Test
		@DisplayName("gathers the nurse sections of a visit into one document")
		void gathersTheNurseSections() {
			assertThat(service.getBenVisitDetailsFrmNurseNCDScreening(1L, 20L)).isNotNull();
			assertThat(service.getBenHistoryDetails(1L, 20L)).isNotNull();
			assertThat(service.getNCDScreeningDetails(1L, 20L)).isNotNull();
		}

		@Test
		@DisplayName("gathers the doctor sections of a visit into one document")
		void gathersTheDoctorSections() {
			doReturn("{\"counsellingProvided\":\"a||b\"}").when(ncdSCreeningDoctorServiceImpl)
					.getNCDDiagnosisData(anyLong(), anyLong());

			assertThat(service.getBenCaseRecordFromDoctorNCDScreening(1L, 20L)).isNotNull().contains("findings");
		}

		@Test
		@DisplayName("counts the screening visits recorded for a beneficiary")
		void countsTheScreeningVisits() {
			assertThat(service.getNcdScreeningVisitCnt(1L)).isNotNull();
		}
	}

	@Nested
	@DisplayName("updating a recorded visit")
	class UpdatingARecordedVisit {

		@Test
		@DisplayName("updates the vitals of a recorded visit")
		void updatesTheVitals() throws Exception {
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
