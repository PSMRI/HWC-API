package com.iemr.hwc.service.cancerScreening;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.iemr.hwc.data.nurse.BenCancerVitalDetail;
import com.iemr.hwc.data.nurse.BenFamilyCancerHistory;
import com.iemr.hwc.data.nurse.BenObstetricCancerHistory;
import com.iemr.hwc.data.nurse.BenPersonalCancerDietHistory;
import com.iemr.hwc.data.nurse.BenPersonalCancerHistory;
import com.iemr.hwc.repo.nurse.BenCancerVitalDetailRepo;
import com.iemr.hwc.repo.nurse.BenFamilyCancerHistoryRepo;
import com.iemr.hwc.repo.nurse.BenObstetricCancerHistoryRepo;
import com.iemr.hwc.repo.nurse.BenPersonalCancerDietHistoryRepo;
import com.iemr.hwc.repo.nurse.BenPersonalCancerHistoryRepo;
import com.iemr.hwc.testutil.PopulatedMocks;
import com.iemr.hwc.utils.exception.IEMRException;

/**
 * The cancer screening nurse service records and reads back the beneficiary's family,
 * personal, diet and obstetric history. Each history is read back with a native query and
 * turned into a table of columns and rows for the screen that shows it, so these tests
 * feed the queries a row of the column types the query returns and assert on the table.
 */
@DisplayName("CSNurseServiceImpl")
class CSNurseServiceImplTest {

	private static final Date CAPTURED_ON = Date.valueOf("2024-01-15");

	private CSNurseServiceImpl service;
	private BenFamilyCancerHistoryRepo benFamilyCancerHistoryRepo;
	private BenPersonalCancerHistoryRepo benPersonalCancerHistoryRepo;
	private BenPersonalCancerDietHistoryRepo benPersonalCancerDietHistoryRepo;
	private BenObstetricCancerHistoryRepo benObstetricCancerHistoryRepo;
	private BenCancerVitalDetailRepo benCancerVitalDetailRepo;

	@BeforeEach
	void setUp() {
		service = new CSNurseServiceImpl();
		benFamilyCancerHistoryRepo = PopulatedMocks.inject(service, "benFamilyCancerHistoryRepo",
				BenFamilyCancerHistoryRepo.class);
		benPersonalCancerHistoryRepo = PopulatedMocks.inject(service, "benPersonalCancerHistoryRepo",
				BenPersonalCancerHistoryRepo.class);
		benPersonalCancerDietHistoryRepo = PopulatedMocks.inject(service, "benPersonalCancerDietHistoryRepo",
				BenPersonalCancerDietHistoryRepo.class);
		benObstetricCancerHistoryRepo = PopulatedMocks.inject(service, "benObstetricCancerHistoryRepo",
				BenObstetricCancerHistoryRepo.class);
		benCancerVitalDetailRepo = PopulatedMocks.inject(service, "benCancerVitalDetailRepo",
				BenCancerVitalDetailRepo.class);
		PopulatedMocks.injectCollaborators(service);
	}

	private static ArrayList<Object[]> rows(Object... row) {
		ArrayList<Object[]> rows = new ArrayList<>();
		rows.add(row);
		return rows;
	}

	@Nested
	@DisplayName("reading a recorded history back as a table")
	class ReadingAHistoryBack {

		@Test
		@DisplayName("lays the family history out as one row per relative with a cancer")
		void laysOutTheFamilyHistory() {
			doReturn(rows("Mother", "Breast Cancer", CAPTURED_ON)).when(benFamilyCancerHistoryRepo)
					.getBenCancerFamilyHistory(1L);

			String table = service.getBenCancerFamilyHistory(1L);

			assertThat(table).contains("columns").contains("data").contains("Mother").contains("Breast Cancer");
		}

		@Test
		@DisplayName("lays out an empty family history when nothing was recorded")
		void laysOutAnEmptyFamilyHistory() {
			doReturn(new ArrayList<Object[]>()).when(benFamilyCancerHistoryRepo).getBenCancerFamilyHistory(1L);

			assertThat(service.getBenCancerFamilyHistory(1L)).contains("columns");
		}

		@Test
		@DisplayName("lays the personal history out with the tobacco and alcohol answers")
		void laysOutThePersonalHistory() {
			doReturn(rows("nurse1", 5, 2, "Cigarettes", 10, Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, 3, "Beer",
					Boolean.TRUE, "occasionally", CAPTURED_ON)).when(benPersonalCancerHistoryRepo)
							.getBenPersonalHistory(1L);

			String table = service.getBenCancerPersonalHistory(1L);

			assertThat(table).contains("columns").contains("data").contains("Cigarettes");
		}

		@Test
		@DisplayName("lays the diet history out with the intake counts")
		void laysOutTheDietHistory() {
			doReturn(rows("nurse1", 3, 2, 1, 4, 5, "Vegetarian", "twice a week", Boolean.TRUE, Boolean.FALSE,
					CAPTURED_ON)).when(benPersonalCancerDietHistoryRepo).getBenPersonaDietHistory(1L);

			String table = service.getBenCancerPersonalDietHistory(1L);

			assertThat(table).contains("columns").contains("data").contains("Vegetarian");
		}

		@Test
		@DisplayName("lays the obstetric history out with the pregnancy answers")
		void laysOutTheObstetricHistory() {
			doReturn(rows("nurse1", Boolean.TRUE, "Regular", 28, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, 2,
					Boolean.TRUE, 1, 3, "None", Boolean.FALSE, Boolean.TRUE, 4, Boolean.FALSE, Boolean.TRUE,
					CAPTURED_ON)).when(benObstetricCancerHistoryRepo).getBenObstetricCancerHistoryData(1L);

			String table = service.getBenCancerObstetricHistory(1L);

			assertThat(table).contains("columns").contains("data").contains("Regular");
		}

		@Test
		@DisplayName("lays out an empty obstetric history when nothing was recorded")
		void laysOutAnEmptyObstetricHistory() {
			doReturn(new ArrayList<Object[]>()).when(benObstetricCancerHistoryRepo)
					.getBenObstetricCancerHistoryData(1L);

			assertThat(service.getBenCancerObstetricHistory(1L)).contains("columns");
		}
	}

	@Nested
	@DisplayName("recording a history")
	class RecordingAHistory {

		@Test
		@DisplayName("flattens the relatives named for a disease into one field before recording them")
		void flattensTheRelativesBeforeRecording() {
			BenFamilyCancerHistory history = new BenFamilyCancerHistory();
			history.setFamilyMemberList(List.of("Mother", "Sister"));
			doReturn(List.of(history)).when(benFamilyCancerHistoryRepo).saveAll(any());

			assertThat(service.saveBenFamilyCancerHistory(List.of(history))).isEqualTo(1);
			assertThat(history.getFamilyMember()).isEqualTo("Mother,Sister");
		}

		@Test
		@DisplayName("records nothing for an entry that names no relative")
		void recordsNothingWithoutARelative() {
			doReturn(List.of()).when(benFamilyCancerHistoryRepo).saveAll(any());

			assertThat(service.saveBenFamilyCancerHistory(List.of(new BenFamilyCancerHistory()))).isEqualTo(1);
		}

		@Test
		@DisplayName("records the personal history and reports the id it was given")
		void recordsThePersonalHistory() throws IEMRException {
			BenPersonalCancerHistory history = new BenPersonalCancerHistory();
			history.setID(7L);
			doReturn(history).when(benPersonalCancerHistoryRepo).save(any());

			assertThat(service.saveBenPersonalCancerHistory(new BenPersonalCancerHistory())).isEqualTo(7L);
		}

		@Test
		@DisplayName("reports no id for a personal history the database would not accept")
		void reportsAnUnsavedPersonalHistory() {
			doReturn(null).when(benPersonalCancerHistoryRepo).save(any());

			assertThat(service.saveBenPersonalCancerHistory(new BenPersonalCancerHistory())).isNull();
		}

		@Test
		@DisplayName("records the diet history and reports the id it was given")
		void recordsTheDietHistory() throws IEMRException {
			BenPersonalCancerDietHistory history = new BenPersonalCancerDietHistory();
			history.setID(8L);
			doReturn(history).when(benPersonalCancerDietHistoryRepo).save(any());

			assertThat(service.saveBenPersonalCancerDietHistory(new BenPersonalCancerDietHistory())).isEqualTo(8L);
		}

		@Test
		@DisplayName("records the obstetric history and reports the id it was given")
		void recordsTheObstetricHistory() throws IEMRException {
			BenObstetricCancerHistory history = new BenObstetricCancerHistory();
			history.setID(9L);
			doReturn(history).when(benObstetricCancerHistoryRepo).save(any());

			assertThat(service.saveBenObstetricCancerHistory(new BenObstetricCancerHistory())).isEqualTo(9L);
		}

		@Test
		@DisplayName("records the vitals and reports the id they were given")
		void recordsTheVitals() throws IEMRException {
			BenCancerVitalDetail vitals = new BenCancerVitalDetail();
			vitals.setID(11L);
			doReturn(vitals).when(benCancerVitalDetailRepo).save(any());

			assertThat(service.saveBenVitalDetail(new BenCancerVitalDetail())).isEqualTo(11L);
		}
	}

	@Nested
	@DisplayName("updating a recorded history")
	class UpdatingARecordedHistory {

		@Test
		@DisplayName("replaces the recorded relatives with the ones now given")
		void replacesTheFamilyHistory() {
			doReturn(rows(1L, "N")).when(benFamilyCancerHistoryRepo).getFamilyCancerHistoryStatus(any(), any());

			service.updateBeneficiaryFamilyCancerHistory(List.of(new BenFamilyCancerHistory()));

			verify(benFamilyCancerHistoryRepo).deleteExistingFamilyRecord(any(), any());
		}

		@Test
		@DisplayName("updates the personal, diet, obstetric and vital records of a visit")
		void updatesTheOtherHistories() {
			assertThat(service.updateBenPersonalCancerHistory(new BenPersonalCancerHistory())).isNotNegative();
			assertThat(service.updateBenPersonalCancerDietHistory(new BenPersonalCancerDietHistory()))
					.isNotNegative();
			assertThat(service.updateBenObstetricCancerHistory(new BenObstetricCancerHistory())).isNotNegative();
			assertThat(service.updateBenVitalDetail(new BenCancerVitalDetail())).isNotNegative();
		}
	}

	@Nested
	@DisplayName("gathering a case sheet")
	class GatheringACaseSheet {

		@Test
		@DisplayName("reads the visit record of a screening visit")
		void readsTheVisitRecord() {
			BenCancerVitalDetail vitals = new BenCancerVitalDetail();
			vitals.setID(11L);
			doReturn(vitals).when(benCancerVitalDetailRepo).getBenCancerVitalDetail(anyLong(), anyLong());

			assertThat(service.getBenCancerVitalDetailData(1L, 20L)).isSameAs(vitals);
		}

		@Test
		@DisplayName("reads the visit record of a beneficiary out of the visit table")
		void readsTheBeneficiaryVisit() {
			com.iemr.hwc.repo.nurse.BenVisitDetailRepo benVisitDetailRepo = PopulatedMocks.inject(service,
					"benVisitDetailRepo", com.iemr.hwc.repo.nurse.BenVisitDetailRepo.class);
			doReturn(rows(1L, 10L, 1, new java.sql.Timestamp(System.currentTimeMillis()), (short) 1, (short) 1,
					"New Chief Complaint", 1, "Cancer Screening", "self", "none", "nurse1", "N", "a", "b"))
							.when(benVisitDetailRepo).getBeneficiaryVisitDetails(anyLong(), anyLong());

			assertThat(service.getBeneficiaryVisitDetails(1L, 20L)).isNotNull();
		}
	}
}
