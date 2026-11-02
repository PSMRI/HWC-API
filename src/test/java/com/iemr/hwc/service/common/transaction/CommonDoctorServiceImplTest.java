package com.iemr.hwc.service.common.transaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;
import com.iemr.hwc.data.quickConsultation.BenClinicalObservations;
import com.iemr.hwc.data.doctor.BenReferDetails;
import com.iemr.hwc.data.quickConsultation.BenChiefComplaint;
import com.iemr.hwc.repo.quickConsultation.BenClinicalObservationsRepo;
import com.iemr.hwc.repo.doctor.BenReferDetailsRepo;
import com.iemr.hwc.repo.doctor.DocWorkListRepo;
import com.iemr.hwc.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.hwc.repo.quickConsultation.LabTestOrderDetailRepo;
import com.iemr.hwc.repo.quickConsultation.PrescribedDrugDetailRepo;
import com.iemr.hwc.testutil.PopulatedMocks;

/**
 * The common doctor service records what the doctor found, ordered and prescribed at a
 * visit, and reads it back for the case sheet. Each read runs a native query and folds the
 * rows into the document the screen shows, so these tests feed the queries a row of the
 * column types the query returns and assert on the document.
 */
@DisplayName("CommonDoctorServiceImpl")
class CommonDoctorServiceImplTest {

	private static final Timestamp NOW = new Timestamp(System.currentTimeMillis());

	private CommonDoctorServiceImpl service;
	private BenClinicalObservationsRepo benClinicalObservationsRepo;
	private BenChiefComplaintRepo benChiefComplaintRepo;
	private LabTestOrderDetailRepo labTestOrderDetailRepo;
	private PrescribedDrugDetailRepo prescribedDrugDetailRepo;
	private BenReferDetailsRepo benReferDetailsRepo;

	@BeforeEach
	void setUp() {
		service = new CommonDoctorServiceImpl();
		benClinicalObservationsRepo = PopulatedMocks.inject(service, "benClinicalObservationsRepo",
				BenClinicalObservationsRepo.class);
		benChiefComplaintRepo = PopulatedMocks.inject(service, "benChiefComplaintRepo", BenChiefComplaintRepo.class);
		labTestOrderDetailRepo = PopulatedMocks.inject(service, "labTestOrderDetailRepo",
				LabTestOrderDetailRepo.class);
		prescribedDrugDetailRepo = PopulatedMocks.inject(service, "prescribedDrugDetailRepo",
				PrescribedDrugDetailRepo.class);
		benReferDetailsRepo = PopulatedMocks.inject(service, "benReferDetailsRepo", BenReferDetailsRepo.class);
		PopulatedMocks.injectCollaborators(service);
	}

	private static ArrayList<Object[]> rows(Object... row) {
		ArrayList<Object[]> rows = new ArrayList<>();
		rows.add(row);
		return rows;
	}

	/** One recorded chief complaint, in the column order the query returns. */
	private static ArrayList<Object[]> chiefComplaintRows() {
		return rows(1L, 10L, 20L, 1, 7, "Fever", 3, "Day(s)", "intermittent", 99L, "38104006");
	}

	/** One recorded set of findings, in the column order the query returns. */
	private static ArrayList<Object[]> findingsRows() {
		return rows(1L, 10L, 1, "pallor noted", "271594007", "anaemia suspected", Boolean.FALSE, 20L, "271594007",
				"nurse1");
	}

	/** One prescribed drug, in the column order the query returns. */
	private static ArrayList<Object[]> prescriptionRows() {
		return rows(1L, 10L, "Paracetamol", "Tablet", 1, "One Tab", "Once Daily(OD)", "5", "Day(s)", "after food",
				"none", "oral", "generic", "brand", 5, Boolean.FALSE, "doctor1", "N", NOW);
	}

	/** One ordered lab test, in the column order the query returns. */
	private static ArrayList<Object[]> labOrderRows() {
		return rows(1L, 10L, 1, 7, "Haemoglobin", "extra", 20L);
	}

	@Nested
	@DisplayName("reading the consultation back")
	class ReadingTheConsultationBack {

		@Test
		@DisplayName("gathers the findings and the complaints they were recorded against")
		void gathersTheFindings() {
			doReturn(findingsRows()).when(benClinicalObservationsRepo).getFindingsData(anyLong(), anyLong());
			doReturn(chiefComplaintRows()).when(benChiefComplaintRepo).getBenChiefComplaints(anyLong(), anyLong());

			String findings = service.getFindingsDetails(1L, 20L);

			assertThat(findings).contains("Fever").contains("pallor noted");
		}

		@Test
		@DisplayName("gathers an empty findings document when nothing was recorded")
		void gathersEmptyFindings() {
			doReturn(new ArrayList<Object[]>()).when(benClinicalObservationsRepo).getFindingsData(anyLong(),
					anyLong());
			doReturn(new ArrayList<Object[]>()).when(benChiefComplaintRepo).getBenChiefComplaints(anyLong(),
					anyLong());

			assertThat(service.getFindingsDetails(1L, 20L)).isNotNull();
		}

		@Test
		@DisplayName("gathers the tests ordered at the visit")
		void gathersTheInvestigation() {
			doReturn(labOrderRows()).when(labTestOrderDetailRepo).getLabTestOrderDetails(anyLong(), anyLong());

			assertThat(service.getInvestigationDetails(1L, 20L)).contains("Haemoglobin");
			assertThat(service.getInvestigationDetailsWrapper(1L, 20L).getLaboratoryList()).isNotEmpty();
		}

		@Test
		@DisplayName("gathers the drugs prescribed at the visit")
		void gathersThePrescription() {
			doReturn(prescriptionRows()).when(prescribedDrugDetailRepo).getBenPrescribedDrugDetails(anyLong(),
					anyLong());

			assertThat(service.getPrescribedDrugs(1L, 20L)).contains("Paracetamol");
		}

		@Test
		@DisplayName("gathers the referral made at the visit")
		void gathersTheReferral() {
			ArrayList<BenReferDetails> referrals = new ArrayList<>();
			referrals.add(new BenReferDetails());
			doReturn(referrals).when(benReferDetailsRepo).findByBeneficiaryRegIDAndVisitCode(anyLong(), anyLong());

			assertThat(service.getReferralDetails(1L, 20L, false)).isNotNull();
		}

		@Test
		@DisplayName("gathers the findings recorded at the visits before this one")
		void gathersThePreviousFindings() {
			doReturn(rows(Date.valueOf("2024-01-15"), "pallor noted")).when(benClinicalObservationsRepo)
					.getPreviousSignificantFindings(anyLong());

			assertThat(service.fetchBenPreviousSignificantFindings(1L)).contains("pallor noted");
		}

		@Test
		@DisplayName("gathers the referrals a doctor made")
		void gathersTheReferralsOfADoctor() {
			ArrayList<BenReferDetails> referrals = new ArrayList<>();
			referrals.add(new BenReferDetails());
			doReturn(referrals).when(benReferDetailsRepo).getBenReferDetailsByCreatedBy(anyString());

			assertThat(service.getBenReferDetailsByCreatedBy("doctor1")).isNotNull();
		}
	}

	@Nested
	@DisplayName("recording the consultation")
	class RecordingTheConsultation {

		@Test
		@DisplayName("records the findings of the consultation")
		void recordsTheFindings() throws Exception {
			doReturn(new BenClinicalObservations()).when(benClinicalObservationsRepo).save(any());
			JsonObject request = new JsonObject();
			request.addProperty("beneficiaryRegID", 1L);
			request.addProperty("benVisitID", 10L);
			request.addProperty("visitCode", 20L);

			assertThat(service.saveFindings(request)).isEqualTo(1);
			verify(benClinicalObservationsRepo).save(any());
		}

		@Test
		@DisplayName("records the referral of the consultation")
		void recordsTheReferral() throws Exception {
			BenReferDetails referral = new BenReferDetails();
			referral.setBenReferID(8L);
			doReturn(referral).when(benReferDetailsRepo).save(any());
			JsonObject request = new JsonObject();
			request.addProperty("beneficiaryRegID", 1L);
			request.addProperty("visitCode", 20L);

			assertThat(service.saveBenReferDetails(request, false)).isNotNull();
		}

		@Test
		@DisplayName("updates the complaints recorded against a visit")
		void updatesTheComplaints() {
			List<BenChiefComplaint> complaints = List.of(new BenChiefComplaint());
			doReturn(complaints).when(benChiefComplaintRepo).saveAll(any());

			assertThat(service.updateDoctorBenChiefComplaints(complaints)).isNotNegative();
		}

		@Test
		@DisplayName("updates the observations recorded against a visit")
		void updatesTheObservations() {
			assertThat(service.updateBenClinicalObservations(new BenClinicalObservations())).isNotNegative();
		}
	}

	@Nested
	@DisplayName("reading a doctor's worklist")
	class ReadingAWorklist {

		@Test
		@DisplayName("answers with the beneficiaries waiting at the doctor's desk")
		void answersWithTheWorklist() {
			DocWorkListRepo docWorkListRepo = PopulatedMocks.inject(service, "docWorkListRepo",
					DocWorkListRepo.class);
			doReturn(java.util.Collections.singletonList(new Object[] { 1L, "B-1", "Ramesh Kumar", Date.valueOf("1990-05-02"), (short) 1, "Male",
					10L, (short) 1, "New Chief Complaint", "General OPD", "self", "village-2", "9990000000", "N",
					"nurse1" })).when(docWorkListRepo).getDocWorkList();

			assertThat(service.getDocWorkList()).contains("Ramesh Kumar");
		}
	}
}
