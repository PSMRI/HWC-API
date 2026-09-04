package com.iemr.hwc.service.common.transaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.google.gson.JsonObject;
import com.iemr.hwc.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.service.tele_consultation.TeleConsultationServiceImpl;
import com.iemr.hwc.testutil.PopulatedMocks;

/**
 * The common service assembles the printable case sheet of a visit and raises the
 * teleconsultation request a nurse books during one. The case sheet is gathered by visit
 * category, each from the service that owns that kind of visit, so what matters is that
 * every category the application records is routed to the right one.
 */
@DisplayName("CommonServiceImpl")
class CommonServiceImplTest {

	private CommonServiceImpl service;
	private TeleConsultationServiceImpl teleConsultationServiceImpl;

	@BeforeEach
	void setUp() {
		service = new CommonServiceImpl();
		teleConsultationServiceImpl = PopulatedMocks.inject(service, "teleConsultationServiceImpl",
				TeleConsultationServiceImpl.class);
		PopulatedMocks.injectCollaborators(service);
	}

	private static BeneficiaryFlowStatus visitOfCategory(String visitCategory) {
		BeneficiaryFlowStatus visit = new BeneficiaryFlowStatus();
		visit.setBeneficiaryRegID(1L);
		visit.setBenVisitID(10L);
		visit.setVisitCode(20L);
		visit.setVisitCategory(visitCategory);
		return visit;
	}

	@Nested
	@DisplayName("gathering the printable case sheet of a visit")
	class GatheringTheCaseSheet {

		@ParameterizedTest
		@ValueSource(strings = { "ANC", "PNC", "General OPD", "NCD care", "General OPD (QC)", "Cancer Screening",
				"COVID-19 Screening", "NCD screening", "FP & Contraceptive Services",
				"Neonatal and Infant Health Care Services", "Childhood & Adolescent Healthcare Services" })
		@DisplayName("gathers the case sheet of every visit category the application records")
		void gathersEveryCategory(String visitCategory) throws Exception {
			assertThat(service.getCaseSheetPrintDataForBeneficiary(visitOfCategory(visitCategory), "Bearer token"))
					.as("case sheet of a %s visit", visitCategory).isNotNull();
		}

		@Test
		@DisplayName("says so for a visit category it does not know")
		void reportsAnUnknownCategory() throws Exception {
			assertThat(service.getCaseSheetPrintDataForBeneficiary(visitOfCategory("Dentistry"), "Bearer token"))
					.isEqualTo("Invalid VisitCategory");
		}

		@ParameterizedTest
		@NullAndEmptySource
		@DisplayName("gathers nothing for a visit whose category was never recorded")
		void gathersNothingWithoutACategory(String visitCategory) throws Exception {
			assertThat(service.getCaseSheetPrintDataForBeneficiary(visitOfCategory(visitCategory), "Bearer token"))
					.isNull();
		}
	}

	@Nested
	@DisplayName("raising a teleconsultation request")
	class RaisingATeleconsultationRequest {

		private JsonObject requestWithTeleconsultation() {
			JsonObject teleconsultation = new JsonObject();
			teleconsultation.addProperty("specializationID", 3);
			teleconsultation.addProperty("userID", 7);
			// The request mapper reads timestamps in the ISO form it is configured with.
			teleconsultation.addProperty("allocationDate", "2024-01-15T10:30:00.000");
			teleconsultation.addProperty("fromTime", "10:30:00");
			teleconsultation.addProperty("toTime", "10:45:00");
			teleconsultation.addProperty("walkIn", false);

			JsonObject request = new JsonObject();
			request.addProperty("beneficiaryRegID", 1L);
			request.addProperty("createdBy", "nurse1");
			request.add("tcRequest", teleconsultation);
			return request;
		}

		private CommonUtilityClass nurse() {
			CommonUtilityClass nurse = new CommonUtilityClass();
			nurse.setBeneficiaryRegID(1L);
			nurse.setBenFlowID(5L);
			nurse.setVanID(1);
			nurse.setCreatedBy("nurse1");
			return nurse;
		}

		@Test
		@DisplayName("raises the request a visit booked and hands the booking back")
		void raisesTheRequest() throws Exception {
			doReturn(1L).when(teleConsultationServiceImpl).createTCRequest(any());

			TeleconsultationRequestOBJ raised = service.createTcRequest(requestWithTeleconsultation(), nurse(),
					"Bearer token");

			assertThat(raised).isNotNull();
			assertThat(raised.getUserID()).isEqualTo(7);
			verify(teleConsultationServiceImpl).createTCRequest(any());
		}

		@Test
		@DisplayName("raises nothing for a visit that booked no teleconsultation")
		void raisesNothingWithoutABooking() throws Exception {
			JsonObject request = new JsonObject();
			request.addProperty("beneficiaryRegID", 1L);

			assertThat(service.createTcRequest(request, nurse(), "Bearer token")).isNull();
		}
	}

	@Nested
	@DisplayName("reading a beneficiary's recorded history")
	class ReadingTheHistory {

		@Test
		@DisplayName("reads each history section of a beneficiary")
		void readsEachHistorySection() {
			assertThat(service.getBenPastHistoryData(1L)).isNotNull();
			assertThat(service.getComorbidHistoryData(1L)).isNotNull();
			assertThat(service.getMedicationHistoryData(1L)).isNotNull();
			assertThat(service.getFamilyHistoryData(1L)).isNotNull();
			assertThat(service.getMenstrualHistoryData(1L)).isNotNull();
		}
	}
}
