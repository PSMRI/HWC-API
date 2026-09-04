package com.iemr.hwc.service.cancerScreening;

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
import com.iemr.hwc.testutil.NurseVisitRequest;
import com.iemr.hwc.testutil.PopulatedMocks;

/**
 * The cancer screening service records a screening visit: the visit itself, the family,
 * personal and obstetric history, the vitals and the examinations - oral, breast,
 * abdominal and gynaecological. Unlike the other visit services it reads every section out
 * of the top level of the request rather than out of a per-form section.
 */
@DisplayName("CSServiceImpl")
class CSServiceImplTest {

	private CSServiceImpl service;
	private CommonNurseServiceImpl commonNurseServiceImpl;
	private CSNurseServiceImpl cSNurseServiceImpl;
	private CSDoctorServiceImpl cSDoctorServiceImpl;
	private CommonDoctorServiceImpl commonDoctorServiceImpl;
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;

	@BeforeEach
	void setUp() throws Exception {
		service = new CSServiceImpl();
		commonNurseServiceImpl = PopulatedMocks.inject(service, "commonNurseServiceImpl",
				CommonNurseServiceImpl.class);
		cSNurseServiceImpl = PopulatedMocks.inject(service, "cSNurseServiceImpl", CSNurseServiceImpl.class);
		cSDoctorServiceImpl = PopulatedMocks.inject(service, "cSDoctorServiceImpl", CSDoctorServiceImpl.class);
		commonDoctorServiceImpl = PopulatedMocks.inject(service, "commonDoctorServiceImpl",
				CommonDoctorServiceImpl.class);
		commonBenStatusFlowServiceImpl = PopulatedMocks.inject(service, "commonBenStatusFlowServiceImpl",
				CommonBenStatusFlowServiceImpl.class);
		PopulatedMocks.injectCollaborators(service);

		doReturn(0).when(commonNurseServiceImpl).getMaxCurrentdate(any(), any(), any());
		doReturn(10L).when(commonNurseServiceImpl).saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class));
		doReturn(20L).when(commonNurseServiceImpl).generateVisitCode(anyLong(), any(), any());
	}

	/** A screening request carrying every form the save reads, all at the top level. */
	private static JsonObject nurseRequest() {
		return NurseVisitRequest.forCategory("Cancer Screening").with("historyDetails", historyForms())
				.with("vitalsDetails", new JsonObject()).with("examinationDetails", examinationForms()).build();
	}

	/**
	 * The history forms; the family history holds its diseases as a list, and the list has to
	 * carry a disease because the save only records a flag for a list that is not empty.
	 */
	private static JsonObject historyForms() {
		JsonObject history = NurseVisitRequest.sections("personalHistory", "pastObstetricHistory");
		JsonObject familyHistory = new JsonObject();
		JsonArray diseases = new JsonArray();
		diseases.add(new JsonObject());
		familyHistory.add("diseases", diseases);
		history.add("familyHistory", familyHistory);
		return history;
	}

	/** The examination forms; the image annotations are a list of marked coordinates. */
	private static JsonObject examinationForms() {
		JsonObject examination = NurseVisitRequest.sections("signsDetails", "oralDetails", "breastDetails",
				"abdominalDetails", "gynecologicalDetails");
		JsonArray imageCoordinates = new JsonArray();
		imageCoordinates.add(new JsonObject());
		examination.add("imageCoordinates", imageCoordinates);
		return examination;
	}

	@Nested
	@DisplayName("saving a nurse visit")
	class SavingANurseVisit {

		@Test
		@DisplayName("saves every screening form and answers with the visit code")
		void savesEveryForm() throws Exception {
			String response = service.saveCancerScreeningNurseData(nurseRequest(), "Bearer token");

			assertThat(response).contains("\"visitCode\":\"20\"");
			verify(commonNurseServiceImpl).saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class));
			verify(cSNurseServiceImpl).saveBenVitalDetail(any());
		}

		@Test
		@DisplayName("reports the visit as already captured when one is recorded for the same reason")
		void reportsAnAlreadyCapturedVisit() throws Exception {
			doReturn(1).when(commonNurseServiceImpl).getMaxCurrentdate(any(), any(), any());

			assertThat(service.saveCancerScreeningNurseData(nurseRequest(), "Bearer token"))
					.contains("Data already saved");
		}

		@Test
		@DisplayName("rejects a request that carries no visit details")
		void rejectsARequestWithoutVisitDetails() {
			assertThatThrownBy(() -> service.saveCancerScreeningNurseData(new JsonObject(), "Bearer token"))
					.isInstanceOf(Exception.class).hasMessage("Invalid input");
		}

		@Test
		@DisplayName("treats a visit with no history or examination as saved, since there was nothing to save")
		void savesAVisitWithNoForms() throws Exception {
			JsonObject request = NurseVisitRequest.forCategory("Cancer Screening").build();

			assertThat(service.saveCancerScreeningNurseData(request, "Bearer token")).contains("visitCode");
		}
	}

	@Nested
	@DisplayName("saving doctor data")
	class SavingDoctorData {

		private JsonObject doctorRequest() {
			JsonObject diagnosis = new JsonObject();
			diagnosis.addProperty("specialistDiagnosis", "advice recorded");
			return NurseVisitRequest.forCategory("Cancer Screening").with("findings", new JsonObject())
					.with("prescription", new JsonArray()).with("diagnosis", diagnosis)
					.with("refer", new JsonObject()).with("examinationDetails", new JsonObject())
					.field("doctorSignatureFlag", "true").field("isSpecialist", "true").build();
		}

		@Test
		@DisplayName("records the consultation of the screening visit")
		void recordsTheConsultation() throws Exception {
			assertThat(service.saveCancerScreeningDoctorData(doctorRequest(), "Bearer token")).isNotNull();
		}

		@Test
		@DisplayName("records the diagnosis of the screening visit")
		void recordsTheDiagnosis() throws Exception {
			JsonObject diagnosis = NurseVisitRequest.forCategory("Cancer Screening")
					.with("diagnosis", new JsonObject()).build();

			assertThat(service.saveBenDiagnosisDetails(diagnosis)).isNotNull();
		}
	}

	@Nested
	@DisplayName("reading a screening record back")
	class ReadingAScreeningRecord {

		@Test
		@DisplayName("gathers each nurse screen of a visit")
		void gathersEachNurseScreen() {
			assertThat(service.getBenDataFrmNurseToDocVisitDetailsScreen(1L, 20L)).isNotNull();
			assertThat(service.getBenDataFrmNurseToDocHistoryScreen(1L, 20L)).isNotNull();
			assertThat(service.getBenDataFrmNurseToDocVitalScreen(1L, 20L)).isNotNull();
			assertThat(service.getBenDataFrmNurseToDocExaminationScreen(1L, 20L)).isNotNull();
		}
	}

	@Nested
	@DisplayName("updating a recorded visit")
	class UpdatingARecordedVisit {

		@Test
		@DisplayName("updates the examination forms of a recorded visit")
		void updatesTheExaminationForms() throws Exception {
			JsonObject request = NurseVisitRequest.forCategory("Cancer Screening")
					.with("examinationDetails", examinationForms()).build();

			assertThat(service.updateBenExaminationDetail(request)).isNotNegative();
		}

		@Test
		@DisplayName("updates the history forms of a recorded visit")
		void updatesTheHistoryForms() throws Exception {
			JsonObject request = NurseVisitRequest.forCategory("Cancer Screening")
					.with("historyDetails", historyForms()).build();

			assertThat(service.UpdateCSHistoryNurseData(request)).isNotNull();
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
