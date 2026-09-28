package com.iemr.hwc.fhir.provider.patient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.List;

import org.hl7.fhir.r4.model.Encounter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.iemr.hwc.fhir.dto.beneficiary.identityDTO.BeneficiariesDTOSearch;
import com.iemr.hwc.fhir.dto.visitDetailsMain.visitDetails.BenVisitsDTO;
import com.iemr.hwc.fhir.model.encounter.EncounterExt;
import com.iemr.hwc.fhir.model.patient.PatientExt;
import com.iemr.hwc.fhir.service.patient.PatientService;
import com.iemr.hwc.service.nurse.NurseServiceImpl;
import com.iemr.hwc.service.registrar.RegistrarServiceImpl;
import com.iemr.hwc.testutil.PopulatedMocks;
import com.iemr.hwc.testutil.ReflectiveFiller;

import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.DateParam;
import ca.uhn.fhir.rest.param.StringParam;
import jakarta.servlet.http.HttpServletRequest;

/**
 * The FHIR patient endpoint is how the app reads beneficiaries and their visits out of
 * AMRIT as FHIR resources. The provider's work is the translation: it takes the search
 * response the registrar service returns and maps every field onto the FHIR extensions
 * that carry it, so these tests assert on the resources that come out the other side.
 */
@DisplayName("PatientExtProvider")
class PatientExtProviderTest {

	private PatientExtProvider provider;
	private PatientService patientService;
	private RegistrarServiceImpl registrarServiceImpl;
	private NurseServiceImpl nurseServiceImpl;
	private HttpServletRequest request;

	@BeforeEach
	void setUp() {
		provider = new PatientExtProvider();
		patientService = PopulatedMocks.inject(provider, "patientService", PatientService.class);
		registrarServiceImpl = PopulatedMocks.inject(provider, "registrarServiceImpl", RegistrarServiceImpl.class);
		nurseServiceImpl = PopulatedMocks.inject(provider, "nurseServiceImpl", NurseServiceImpl.class);
		request = mock(HttpServletRequest.class);
		when(request.getHeader("Authorization")).thenReturn("Bearer token");
	}

	/** The search response the registrar service returns, carrying {@code beneficiaries}. */
	private static String searchResponse(List<BeneficiariesDTOSearch> beneficiaries) {
		Gson gson = new Gson();
		return "{\"response\":{\"data\":" + gson.toJson(gson.toJson(beneficiaries)) + "}}";
	}

	@Test
	@DisplayName("names the resource it serves")
	void namesItsResource() {
		assertThat(provider.getResourceType()).isEqualTo(PatientExt.class);
	}

	@Nested
	@DisplayName("creating a beneficiary")
	class CreatingABeneficiary {

		@Test
		@DisplayName("hands the beneficiary to the patient service and reports it as created")
		void reportsTheBeneficiaryAsCreated() {
			PatientExt created = new PatientExt();
			doReturn(created).when(patientService).createNewPatient(any(), any());

			MethodOutcome outcome = provider.createPatient(request, new PatientExt());

			assertThat(outcome.getCreated()).isTrue();
			assertThat(outcome.getResource()).isSameAs(created);
			assertThat(outcome.getOperationOutcome()).isNotNull();
			verify(patientService).createNewPatient(any(), any());
		}
	}

	@Nested
	@DisplayName("searching for beneficiaries changed in a block")
	class SearchingForBeneficiaries {

		private List<PatientExt> search(String response) {
			doReturn(response).when(registrarServiceImpl).getBeneficiaryByBlockIDAndLastModDate(anyString(), any(),
					anyString());

			return provider.findPatientsByDistrictAndLastModifDate(request, new StringParam("7"),
					new DateParam("2024-01-15"));
		}

		@Test
		@DisplayName("maps each beneficiary in the response onto a FHIR patient")
		void mapsEachBeneficiary() {
			BeneficiariesDTOSearch beneficiary = ReflectiveFiller.fill(BeneficiariesDTOSearch.class);
			assertThat(beneficiary).as("a populated beneficiary to map").isNotNull();

			List<PatientExt> patients = search(searchResponse(List.of(beneficiary)));

			assertThat(patients).hasSize(1);
			PatientExt patient = patients.get(0);
			assertThat(patient.getName()).isNotEmpty();
			assertThat(patient.getTelecom()).isNotEmpty();
			assertThat(patient.getState()).isNotNull();
			assertThat(patient.getDistrict()).isNotNull();
			assertThat(patient.getBlock()).isNotNull();
			assertThat(patient.getReligion()).isNotNull();
			assertThat(patient.getCommunity()).isNotNull();
			assertThat(patient.getMaritalStatus()).isNotNull();
		}

		@Test
		@DisplayName("carries the ABHA identifiers of a beneficiary who has one")
		void carriesTheAbhaIdentifiers() {
			BeneficiariesDTOSearch beneficiary = ReflectiveFiller.fill(BeneficiariesDTOSearch.class);

			List<PatientExt> patients = search(searchResponse(List.of(beneficiary)));

			assertThat(patients).hasSize(1);
			assertThat(patients.get(0).getAbhaGenerationMode()).isNotNull();
			assertThat(patients.get(0).getGovtIdentityType()).isNotNull();
		}

		@Test
		@DisplayName("answers with no beneficiaries when the response carries none")
		void answersWithNoBeneficiaries() {
			assertThat(search(searchResponse(List.of()))).isEmpty();
		}

		@Test
		@DisplayName("answers with no beneficiaries rather than failing when the response cannot be read")
		void answersWithNoBeneficiariesForAnUnreadableResponse() {
			assertThat(search("not a response")).isEmpty();
		}
	}

	@Nested
	@DisplayName("searching for visits changed in a village")
	class SearchingForVisits {

		private List<EncounterExt> search(List<BenVisitsDTO> visits) {
			doReturn(visits).when(nurseServiceImpl).getVisitByLocationAndLastModifDate(anyInt(),
					any(Timestamp.class));

			return provider.findVisitsByVillageAndLastModifDate(new StringParam("ANC"), new StringParam("7"),
					new DateParam("2024-01-15"));
		}

		@Test
		@DisplayName("maps each visit in the response onto a FHIR encounter")
		void mapsEachVisit() {
			BenVisitsDTO visit = ReflectiveFiller.fill(BenVisitsDTO.class);
			assertThat(visit).as("a populated visit to map").isNotNull();

			List<EncounterExt> encounters = search(List.of(visit));

			assertThat(encounters).hasSize(1);
			EncounterExt encounter = encounters.get(0);
			assertThat(encounter.getType()).isNotEmpty();
			assertThat(encounter.getServiceType()).isNotNull();
			assertThat(encounter.getClass_().getCode()).isEqualTo("AMB");
			assertThat(encounter.getReasonCode()).isNotEmpty();
		}

		@Test
		@DisplayName("marks a visit still on the nurse's desk as having no encounter status yet")
		void marksAnUnfinishedVisit() {
			BenVisitsDTO visit = ReflectiveFiller.fill(BenVisitsDTO.class);
			visit.getBenVisitDetails().setVisitFlowStatusFlag("N");

			List<EncounterExt> encounters = search(List.of(visit));

			assertThat(encounters).hasSize(1);
			assertThat(encounters.get(0).getStatus()).isEqualTo(Encounter.EncounterStatus.NULL);
		}

		@Test
		@DisplayName("answers with no encounters when no visit changed")
		void answersWithNoEncounters() {
			assertThat(search(List.of())).isEmpty();
		}

		@Test
		@DisplayName("answers with no encounters rather than failing when a visit cannot be mapped")
		void answersWithNoEncountersForAnUnmappableVisit() {
			assertThat(search(java.util.Collections.singletonList(new BenVisitsDTO()))).isEmpty();
		}
	}

}
