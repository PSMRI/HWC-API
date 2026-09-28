package com.iemr.hwc.service.common.transaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.google.gson.Gson;
import com.iemr.hwc.data.anc.WrapperFemaleObstetricHistory;
import com.iemr.hwc.data.bmi.BmiCalculation;
import com.iemr.hwc.data.quickConsultation.PrescribedDrugDetail;
import com.iemr.hwc.repo.bmiCalculation.BMICalculationRepo;
import com.iemr.hwc.repo.quickConsultation.LabTestOrderDetailRepo;
import com.iemr.hwc.repo.quickConsultation.PrescribedDrugDetailRepo;
import com.iemr.hwc.repo.nurse.anc.FemaleObstetricHistoryRepo;
import com.iemr.hwc.testutil.PopulatedMocks;
import com.iemr.hwc.testutil.ReflectiveFiller;
import com.iemr.hwc.utils.exception.IEMRException;

/**
 * The common nurse service is where the shared parts of a visit are recorded, and it also
 * carries two pieces of arithmetic the rest of the application depends on: how many
 * tablets a prescription adds up to, and which malnutrition band a child's BMI falls in.
 * Both are asserted here against the doses and bands the forms offer.
 */
@DisplayName("CommonNurseServiceImpl")
class CommonNurseServiceImplTest {

	private CommonNurseServiceImpl service;
	private PrescribedDrugDetailRepo prescribedDrugDetailRepo;
	private BMICalculationRepo bmiCalculationRepo;

	@BeforeEach
	void setUp() {
		service = new CommonNurseServiceImpl();
		prescribedDrugDetailRepo = PopulatedMocks.inject(service, "prescribedDrugDetailRepo",
				PrescribedDrugDetailRepo.class);
		bmiCalculationRepo = PopulatedMocks.inject(service, "bmiCalculationRepo", BMICalculationRepo.class);
		PopulatedMocks.injectCollaborators(service);

		// The repository hands the drugs back as saved, which is what the service counts.
		doReturn(null).when(prescribedDrugDetailRepo).saveAll(any());
	}

	private static PrescribedDrugDetail drug(String form, String dose, String frequency, String duration,
			String unit) {
		PrescribedDrugDetail detail = new PrescribedDrugDetail();
		detail.setFormName(form);
		detail.setDose(dose);
		detail.setFrequency(frequency);
		detail.setDuration(duration);
		detail.setUnit(unit);
		return detail;
	}

	@Nested
	@DisplayName("working out how much of a drug to dispense")
	class WorkingOutTheQuantity {

		/** Saves one drug and reads back the quantity the service worked out for it. */
		private Integer quantityFor(PrescribedDrugDetail detail) {
			List<PrescribedDrugDetail> drugs = List.of(detail);
			doReturn(drugs).when(prescribedDrugDetailRepo).saveAll(any());

			service.saveBenPrescribedDrugsList(drugs);

			return detail.getQtyPrescribed();
		}

		@ParameterizedTest(name = "{0} {1} {2} for {3} {4} is {5}")
		@CsvSource({
				// one tablet a day, in each of the ways the form spells that
				"Tablet, One Tab, Once Daily(OD), 5, Day(s), 5",
				"Tablet, One Tab, Once Daily(OD) Before Food, 5, Day(s), 5",
				"Tablet, One Tab, Once Daily(OD) After Food, 5, Day(s), 5",
				"Tablet, One Tab, Once Daily(OD) At Bedtime, 5, Day(s), 5",
				// the four tablet doses, once a day
				"Tablet, Half Tab, Once Daily(OD), 5, Day(s), 3",
				"Tablet, One & Half Tab, Once Daily(OD), 5, Day(s), 8",
				"Tablet, Two Tabs, Once Daily(OD), 5, Day(s), 10",
				// twice, three and four times a day
				"Tablet, One Tab, Twice Daily(BD), 5, Day(s), 10",
				"Tablet, Half Tab, Twice Daily(BD), 5, Day(s), 5",
				"Tablet, Two Tabs, Twice Daily(BD) After Food, 5, Day(s), 20",
				"Tablet, One Tab, Thrice Daily (TID), 5, Day(s), 15",
				"Tablet, Half Tab, Thrice Daily (TID) Before Food, 5, Day(s), 8",
				"Tablet, One & Half Tab, Thrice Daily (TID), 5, Day(s), 23",
				"Tablet, One Tab, Four Times in a Day (QID), 5, Day(s), 20",
				"Tablet, Two Tabs, Four Times in a Day AF, 5, Day(s), 40",
				"Tablet, Half Tab, Four Times in a Day BF, 5, Day(s), 10",
				// capsules come one per intake regardless of the dose recorded
				"Capsule, One Tab, Once Daily(OD), 5, Day(s), 5",
				"Capsule, One Tab, Twice Daily(BD), 5, Day(s), 10",
				"Capsule, One Tab, Thrice Daily (TID), 5, Day(s), 15",
				"Capsule, One Tab, Four Times in a Day (QID), 5, Day(s), 20",
				// weekly and as-needed
				"Tablet, One Tab, Once in a Week, 28, Day(s), 4",
				"Capsule, One Tab, Once in a Week, 28, Day(s), 5",
				"Tablet, One Tab, SOS, 5, Day(s), 5",
				"Capsule, One Tab, SOS, 5, Day(s), 5",
				// a course measured in weeks or months
				"Tablet, One Tab, Once Daily(OD), 2, Week(s), 14",
				"Tablet, One Tab, Twice Daily(BD), 2, Week(s), 28",
				"Tablet, One Tab, Once Daily(OD), 1, Month(s), 30",
				"Tablet, Half Tab, Once Daily(OD), 1, Month(s), 15" })
		@DisplayName("multiplies the dose by the frequency and the length of the course")
		void multipliesTheDoseByTheCourse(String form, String dose, String frequency, String duration, String unit,
				int expected) {
			assertThat(quantityFor(drug(form, dose, frequency, duration, unit))).isEqualTo(expected);
		}

		@ParameterizedTest(name = "{0} {1} {2} is {3}")
		@CsvSource({ "Tablet, Half Tab, Single Dose, 1", "Tablet, One Tab, Single Dose, 1",
				"Tablet, One & Half Tab, Single Dose, 2", "Tablet, Two Tabs, Single Dose, 2",
				"Capsule, One Tab, Single Dose, 1", "Tablet, One Tab, Stat Dose, 1" })
		@DisplayName("dispenses a single dose once, whatever course length the form carries")
		void dispensesASingleDoseOnce(String form, String dose, String frequency, int expected) {
			assertThat(quantityFor(drug(form, dose, frequency, "5", "Day(s)"))).isEqualTo(expected);
		}

		@Test
		@DisplayName("treats a single dose worded as before or after food as a daily course, not as one dose")
		void wordingASingleDoseAroundFoodChangesTheCourse() {
			// The quantity for one day comes from the single-dose table, but the course is
			// still multiplied out, because only the bare "Single Dose" and "Stat Dose"
			// wordings are recognised as a one-off.
			assertThat(quantityFor(drug("Tablet", "One Tab", "Single Dose After Food", "5", "Day(s)"))).isEqualTo(5);
		}

		@ParameterizedTest
		@ValueSource(strings = { "Syrup", "Injection", "Drops" })
		@DisplayName("leaves the quantity alone for a form that is not counted in tablets")
		void leavesOtherFormsAlone(String form) {
			assertThat(quantityFor(drug(form, "One Tab", "Once Daily(OD)", "5", "Day(s)"))).isNull();
		}

		@Test
		@DisplayName("works out no quantity when the prescription is missing a field")
		void worksOutNothingFromAnIncompletePrescription() {
			assertThat(quantityFor(drug("Tablet", null, "Once Daily(OD)", "5", "Day(s)"))).isZero();
			assertThat(quantityFor(drug("Tablet", "One Tab", null, "5", "Day(s)"))).isZero();
			assertThat(quantityFor(drug("Tablet", "One Tab", "Once Daily(OD)", null, "Day(s)"))).isZero();
			assertThat(quantityFor(drug("Tablet", "One Tab", "Once Daily(OD)", "5", null))).isZero();
		}

		@Test
		@DisplayName("works out no quantity for a dose or frequency the tables do not know")
		void worksOutNothingFromAnUnknownDose() {
			assertThat(quantityFor(drug("Tablet", "Three Tabs", "Once Daily(OD)", "5", "Day(s)"))).isZero();
			assertThat(quantityFor(drug("Tablet", "One Tab", "Every Other Day", "5", "Day(s)"))).isZero();
		}

		@Test
		@DisplayName("works out no quantity for a course unit the tables do not know")
		void worksOutNothingFromAnUnknownCourseUnit() {
			assertThat(quantityFor(drug("Tablet", "One Tab", "Once Daily(OD)", "5", "Year(s)"))).isZero();
		}

		@Test
		@DisplayName("reports the drugs it saved, with their ids")
		void reportsTheDrugsItSaved() {
			List<PrescribedDrugDetail> drugs = List.of(drug("Tablet", "One Tab", "Once Daily(OD)", "5", "Day(s)"));
			doReturn(drugs).when(prescribedDrugDetailRepo).saveAll(any());

			assertThat(service.saveBenPrescribedDrugsList(drugs)).containsKey("prescribedDrugIDs");
		}

		@Test
		@DisplayName("reports nothing saved for an empty prescription")
		void reportsNothingSavedForAnEmptyPrescription() {
			assertThat(service.saveBenPrescribedDrugsList(List.of())).isNotNull();
		}
	}

	@Nested
	@DisplayName("placing a child's BMI in a malnutrition band")
	class PlacingABmiInABand {

		/** The bands a child of this age and sex is measured against. */
		private BmiCalculation bands() {
			BmiCalculation bands = new BmiCalculation();
			bands.setN3SD(11.0d);
			bands.setN2SD(13.0d);
			bands.setN1SD(15.0d);
			bands.setP1SD(17.0d);
			bands.setP2SD(19.0d);
			bands.setP3SD(21.0d);
			return bands;
		}

		private String bandFor(double bmi) throws IEMRException {
			doReturn(bands()).when(bmiCalculationRepo).getBMIDetails(anyInt(), anyString());
			return service.calculateBMIStatus(new Gson().toJson(measurement(bmi)));
		}

		/**
		 * A measurement of a three-and-a-half year old girl.
		 *
		 * <p>The age is written as four space-separated tokens because the service reads the
		 * years off the first and the months off the fourth.
		 */
		private BmiCalculation measurement(double bmi) {
			BmiCalculation request = new BmiCalculation();
			request.setYearMonth("3 years and 6");
			request.setGender("Female");
			request.setBmi(bmi);
			return request;
		}

		static Stream<Arguments> bandsAndBmis() {
			return Stream.of(Arguments.of(16.0d, "Normal"), Arguments.of(15.0d, "Normal"),
					Arguments.of(14.0d, "Mild malnourished"), Arguments.of(13.0d, "Mild malnourished"),
					Arguments.of(12.0d, "Moderately Malnourished"), Arguments.of(10.0d, "Severely Malnourished"),
					Arguments.of(18.0d, "Overweight"), Arguments.of(20.0d, "Obese"),
					Arguments.of(22.0d, "Severely Obese"));
		}

		@ParameterizedTest(name = "a BMI of {0} is {1}")
		@MethodSource("bandsAndBmis")
		@DisplayName("names the band the measurement falls in")
		void namesTheBand(double bmi, String expected) throws IEMRException {
			assertThat(bandFor(bmi)).contains(expected);
		}

		@Test
		@DisplayName("reports that a child of this age and sex has no bands recorded")
		void reportsAMissingBand() {
			doReturn(null).when(bmiCalculationRepo).getBMIDetails(anyInt(), anyString());

			assertThatThrownBy(() -> service.calculateBMIStatus(new Gson().toJson(measurement(16.0d))))
					.isInstanceOf(IEMRException.class).hasMessageContaining("No data found for this category");
		}

		@Test
		@DisplayName("rejects an age written the way it reads, because the month count is taken from the fourth word")
		void rejectsAPlainlyWrittenAge() {
			BmiCalculation request = new BmiCalculation();
			request.setYearMonth("3 years 6 months");
			request.setGender("Female");
			request.setBmi(16.0d);

			// "3 years 6 months" splits into four words and the fourth is "months", which is
			// not a number, so the whole calculation is rejected. Asserted so the format the
			// endpoint really requires is on record.
			assertThatThrownBy(() -> service.calculateBMIStatus(new Gson().toJson(request)))
					.isInstanceOf(IEMRException.class).hasMessageContaining("For input string");
		}

		@Test
		@DisplayName("answers with an empty band when the request carries no measurement")
		void answersEmptyWithoutAMeasurement() throws IEMRException {
			BmiCalculation request = new BmiCalculation();
			request.setYearMonth("3 years and 6");
			request.setGender("Female");

			assertThat(service.calculateBMIStatus(new Gson().toJson(request))).contains("\"bmiStatus\":\"\"");
		}

		@Test
		@DisplayName("reports a request whose age is not written as years and months")
		void reportsAMalformedAge() {
			BmiCalculation request = new BmiCalculation();
			request.setYearMonth("three-and-a-half");
			request.setGender("Female");
			request.setBmi(16.0d);

			assertThatThrownBy(() -> service.calculateBMIStatus(new Gson().toJson(request)))
					.isInstanceOf(IEMRException.class).hasMessageContaining("Error while calculating BMI status");
		}
	}

	@Nested
	@DisplayName("fetching the data another facility asked for")
	class FetchingProviderSpecificData {

		private String requestFor(String what) {
			return "{\"fetchMMUDataFor\":\"" + what + "\",\"beneficiaryRegID\":1,\"visitCode\":20}";
		}

		@Test
		@DisplayName("publishes the drugs prescribed at a visit")
		void publishesThePrescription() throws IEMRException {
			doReturn(prescriptionRows()).when(prescribedDrugDetailRepo).getBenPrescribedDrugDetails(any(), any());

			String published = service.fetchProviderSpecificdata(requestFor("prescription"));

			assertThat(published).contains("data").contains("columns").contains("Paracetamol");
		}

		@Test
		@DisplayName("publishes the tests ordered at a visit")
		void publishesTheInvestigation() throws IEMRException {
			LabTestOrderDetailRepo labTestOrderDetailRepo = PopulatedMocks.inject(service, "labTestOrderDetailRepo",
					LabTestOrderDetailRepo.class);
			doReturn(labOrderRows()).when(labTestOrderDetailRepo).getLabTestOrderDetails(any(), any());

			assertThat(service.fetchProviderSpecificdata(requestFor("investigation"))).contains("data");
		}

		@ParameterizedTest
		@ValueSource(strings = { "referral", "REFERRAL" })
		@DisplayName("publishes the referral of a visit, whatever case the request asks in")
		void publishesTheReferral(String what) throws IEMRException {
			assertThat(service.fetchProviderSpecificdata(requestFor(what))).isNotNull();
		}

		/** One prescribed drug, in the column order the query returns. */
		private java.util.ArrayList<Object[]> prescriptionRows() {
			java.util.ArrayList<Object[]> rows = new java.util.ArrayList<>();
			rows.add(new Object[] { 1L, 10L, "Paracetamol", "Tablet", 1, "One Tab", "Once Daily(OD)", "5", "Day(s)",
					"after food", "none", "oral", "generic", "brand", 5, Boolean.FALSE, "nurse1", "N",
					new java.sql.Timestamp(System.currentTimeMillis()) });
			return rows;
		}

		/** One ordered lab test, in the column order the query returns. */
		private java.util.ArrayList<Object[]> labOrderRows() {
			java.util.ArrayList<Object[]> rows = new java.util.ArrayList<>();
			rows.add(new Object[] { 1L, 10L, 1, 7, "Haemoglobin", "extra", 20L });
			return rows;
		}

		@Test
		@DisplayName("says so when asked for a kind of record it does not publish")
		void reportsAnUnknownKind() throws IEMRException {
			assertThat(service.fetchProviderSpecificdata(requestFor("something else")))
					.isEqualTo("Invalid master param to fetch data");
		}

		@Test
		@DisplayName("reports a request that does not say what to fetch")
		void reportsARequestWithoutAKind() {
			assertThatThrownBy(() -> service.fetchProviderSpecificdata("{}")).isInstanceOf(IEMRException.class);
		}
	}

	@Nested
	@DisplayName("reading a woman's obstetric history back")
	class ReadingTheObstetricHistory {

		/**
		 * One recorded pregnancy, in the column order the query returns.
		 *
		 * <p>The complication columns hold pipe-separated lists, which the service splits into
		 * the id-and-name pairs the screen shows.
		 */
		private java.util.ArrayList<Object[]> pregnancyRows() {
			Object[] row = new Object[38];
			row[0] = 1L;
			row[1] = 10L;
			row[2] = 1;
			row[3] = (short) 1;
			row[4] = (short) 2;
			for (int i = 5; i < row.length; i++) {
				row[i] = "1||2";
			}
			row[8] = (short) 1;
			row[10] = (short) 1;
			row[12] = (short) 1;
			row[18] = (short) 1;
			row[23] = (short) 1;
			row[27] = (short) 1;
			row[30] = 99L;
			row[31] = 1;
			row[33] = 1;
			row[34] = 1;
			java.util.ArrayList<Object[]> rows = new java.util.ArrayList<>();
			rows.add(row);
			return rows;
		}

		@Test
		@DisplayName("splits the complications of each pregnancy into the pairs the screen shows")
		void splitsTheComplications() {
			FemaleObstetricHistoryRepo femaleObstetricHistoryRepo = PopulatedMocks.inject(service,
					"femaleObstetricHistoryRepo", FemaleObstetricHistoryRepo.class);
			doReturn(pregnancyRows()).when(femaleObstetricHistoryRepo)
					.getBenFemaleObstetricHistoryDetail(anyLong(), anyLong());

			WrapperFemaleObstetricHistory history = service.getFemaleObstetricHistory(1L, 20L);

			assertThat(history).isNotNull();
			assertThat(history.getFemaleObstetricHistoryList()).isNotEmpty();
			assertThat(history.getTotalNoOfPreg()).isEqualTo((short) 2);
		}

		@Test
		@DisplayName("reads back an empty history when no pregnancy was recorded")
		void readsBackAnEmptyHistory() {
			FemaleObstetricHistoryRepo femaleObstetricHistoryRepo = PopulatedMocks.inject(service,
					"femaleObstetricHistoryRepo", FemaleObstetricHistoryRepo.class);
			doReturn(new java.util.ArrayList<Object[]>()).when(femaleObstetricHistoryRepo)
					.getBenFemaleObstetricHistoryDetail(anyLong(), anyLong());

			assertThat(service.getFemaleObstetricHistory(1L, 20L).getFemaleObstetricHistoryList()).isEmpty();
		}

	}

	@Nested
	@DisplayName("updating a recorded visit")
	class UpdatingARecordedVisit {

		@Test
		@DisplayName("updates the menstrual history of a visit")
		void updatesTheMenstrualHistory() throws IEMRException {
			assertThat(service.updateMenstrualHistory(new com.iemr.hwc.data.anc.BenMenstrualDetails()))
					.isNotNegative();
		}

		@Test
		@DisplayName("updates the prescription of a visit")
		void updatesThePrescription() {
			com.iemr.hwc.data.quickConsultation.PrescriptionDetail saved = new com.iemr.hwc.data.quickConsultation.PrescriptionDetail();
			saved.setPrescriptionID(7L);
			com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo prescriptionDetailRepo = PopulatedMocks
					.inject(service, "prescriptionDetailRepo",
							com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo.class);
			doReturn(saved).when(prescriptionDetailRepo).save(any());

			assertThat(service.updatePrescription(new com.iemr.hwc.data.quickConsultation.PrescriptionDetail()))
					.isNotNegative();
		}

	}
}
