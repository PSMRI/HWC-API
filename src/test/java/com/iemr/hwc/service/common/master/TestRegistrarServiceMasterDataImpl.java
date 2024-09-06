package com.iemr.hwc.service.common.master;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.masterrepo.AgeUnitRepo;
import com.iemr.hwc.repo.masterrepo.CommunityMasterRepo;
import com.iemr.hwc.repo.masterrepo.GenderMasterRepo;
import com.iemr.hwc.repo.masterrepo.GovIdEntityTypeRepo;
import com.iemr.hwc.repo.masterrepo.IncomeStatusMasterRepo;
import com.iemr.hwc.repo.masterrepo.LiteracyStatusRepo;
import com.iemr.hwc.repo.masterrepo.MaritalStatusMasterRepo;
import com.iemr.hwc.repo.masterrepo.OccupationMasterRepo;
import com.iemr.hwc.repo.masterrepo.QualificationMasterRepo;
import com.iemr.hwc.repo.masterrepo.RelationshipMasterRepo;
import com.iemr.hwc.repo.masterrepo.ReligionMasterRepo;
import com.iemr.hwc.repo.nurse.anc.ANCCareRepo;
import com.iemr.hwc.repo.registrar.BeneficiaryImageRepo;
import com.iemr.hwc.repo.registrar.ReistrarRepoBenSearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestRegistrarServiceMasterDataImpl {

	@RunWith(MockitoJUnitRunner.class)

	@Mock
	private CommunityMasterRepo communityMasterRepo;
	@Mock
	private GenderMasterRepo genderMasterRepo;
	@Mock
	private GovIdEntityTypeRepo govIdEntityTypeRepo;
	@Mock
	private IncomeStatusMasterRepo incomeStatusMasterRepo;
	@Mock
	private MaritalStatusMasterRepo maritalStatusMasterRepo;
	@Mock
	private OccupationMasterRepo occupationMasterRepo;
	@Mock
	private QualificationMasterRepo qualificationMasterRepo;
	@Mock
	private ReligionMasterRepo religionMasterRepo;
	@Mock
	private AgeUnitRepo ageUnitRepo;
	@Mock
	private LiteracyStatusRepo literacyStatusRepo;
	@Mock
	private RelationshipMasterRepo relationshipMasterRepo;
	@Mock
	private ReistrarRepoBenSearch reistrarRepoBenSearch;

	@Mock
	private BeneficiaryImageRepo beneficiaryImageRepo;
	@Mock
	private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;

	@Mock
	private ANCCareRepo aNCCareRepo;

	@InjectMocks
	private RegistrarServiceMasterDataImpl registrarServiceMasterDataImpl;

	private Gson gson;
	private RegistrarServiceMasterDataImpl registrarServiceMasterData;

	@Before
	public void setUp() {
		gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
	}

	@Test
	public void testGetMasterData_Positive() {
		// Mocking repository methods to return valid data
		when(communityMasterRepo.getCommunityMaster()).thenReturn(new ArrayList<>());
		when(genderMasterRepo.getGenderMaster()).thenReturn(new ArrayList<>());
		when(govIdEntityTypeRepo.getGovIdEntityMaster()).thenReturn(new ArrayList<>());
		when(govIdEntityTypeRepo.getOtherGovIdEntityMaster()).thenReturn(new ArrayList<>());
		when(incomeStatusMasterRepo.getIncomeStatusMaster()).thenReturn(new ArrayList<>());
		when(maritalStatusMasterRepo.getMaritalStatusMaster()).thenReturn(new ArrayList<>());
		when(occupationMasterRepo.getOccupationMaster()).thenReturn(new ArrayList<>());
		when(qualificationMasterRepo.getQualificationMaster()).thenReturn(new ArrayList<>());
		when(religionMasterRepo.getReligionMaster()).thenReturn(new ArrayList<>());
		when(ageUnitRepo.getAgeUnit()).thenReturn(new ArrayList<>());
		when(literacyStatusRepo.getLiteracyStatus()).thenReturn(new ArrayList<>());
		when(relationshipMasterRepo.getRelationshipMaster()).thenReturn(new ArrayList<>());

		// Call the method
		String result = registrarServiceMasterDataImpl.getMasterData();

		// Assert the JSON output
		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("communityMaster", new ArrayList<>());
		expectedMap.put("genderMaster", new ArrayList<>());
		expectedMap.put("govIdEntityMaster", new ArrayList<>());
		expectedMap.put("otherGovIdEntityMaster", new ArrayList<>());
		expectedMap.put("incomeMaster", new ArrayList<>());
		expectedMap.put("maritalStatusMaster", new ArrayList<>());
		expectedMap.put("occupationMaster", new ArrayList<>());
		expectedMap.put("qualificationMaster", new ArrayList<>());
		expectedMap.put("religionMaster", new ArrayList<>());
		expectedMap.put("ageUnit", new ArrayList<>());
		expectedMap.put("literacyStatus", new ArrayList<>());
		expectedMap.put("relationshipMaster", new ArrayList<>());

		String expectedJson = gson.toJson(expectedMap);
		assertEquals(expectedJson, result);
	}

	@Test
	public void testGetMasterData_Negative() {
		// Mocking repository methods to throw exceptions
		when(communityMasterRepo.getCommunityMaster()).thenThrow(new RuntimeException("Database error"));

		// Call the method and assert that the exception is handled
		try {
			registrarServiceMasterDataImpl.getMasterData();
			fail("Expected an exception to be thrown");
		} catch (Exception e) {
			assertEquals("Database error", e.getMessage());
		}
	}

	@Test
	public void testGetBenDetailsByRegID() {
		when(reistrarRepoBenSearch.getBenDetails(beneficiaryRegID)).thenReturn(benDetailsList);
		when(beneficiaryImageRepo.getBenImage(beneficiaryRegID)).thenReturn("imageData");

		String result = registrarServiceMasterData.getBenDetailsByRegID(beneficiaryRegID);

		assertNotNull(result);
		assertTrue(result.contains("John Doe"));
		assertTrue(result.contains("Male"));
		verify(reistrarRepoBenSearch, times(1)).getBenDetails(beneficiaryRegID);
		verify(beneficiaryImageRepo, times(1)).getBenImage(beneficiaryRegID);
	}

	@Test
	public void testGetBenDetailsByRegID_NullValues() {
		Object[] benDetails = new Object[] { 1L, null, null, null };
		benDetailsList.clear();
		benDetailsList.add(benDetails);

		when(reistrarRepoBenSearch.getBenDetails(beneficiaryRegID)).thenReturn(benDetailsList);
		when(beneficiaryImageRepo.getBenImage(beneficiaryRegID)).thenReturn(null);

		String result = registrarServiceMasterData.getBenDetailsByRegID(beneficiaryRegID);

		assertNotNull(result);
		assertTrue(result.contains("\"beneficiaryName\":null"));
		assertTrue(result.contains("\"genderName\":null"));
		verify(reistrarRepoBenSearch, times(1)).getBenDetails(beneficiaryRegID);
		verify(beneficiaryImageRepo, times(1)).getBenImage(beneficiaryRegID);
	}

	@Test
	public void testGetBenDetailsByRegID_GenderName() {
		Object[] benDetails = new Object[] { 1L, "Jane Doe", 2, "imageData" };
		benDetailsList.clear();
		benDetailsList.add(benDetails);

		when(reistrarRepoBenSearch.getBenDetails(beneficiaryRegID)).thenReturn(benDetailsList);
		when(beneficiaryImageRepo.getBenImage(beneficiaryRegID)).thenReturn("imageData");

		String result = registrarServiceMasterData.getBenDetailsByRegID(beneficiaryRegID);

		assertNotNull(result);
		assertTrue(result.contains("Jane Doe"));
		assertTrue(result.contains("Female"));
		verify(reistrarRepoBenSearch, times(1)).getBenDetails(beneficiaryRegID);
		verify(beneficiaryImageRepo, times(1)).getBenImage(beneficiaryRegID);
	}

	@Test
	public void testGetBenDetailsForLeftSideByRegIDNew() {
		BeneficiaryFlowStatus benFlowOBJ = new BeneficiaryFlowStatus();
		benFlowOBJ.setBenName("John Doe");
		benFlowOBJ.setBen_age_val("30");
		benFlowOBJ.setBenVisitDate("2023-10-01");
		benFlowOBJ.setRegistrationDate("2023-01-01");

		when(beneficiaryFlowStatusRepo.getBenDetailsForLeftSidePanel(benFlowID)).thenReturn(benFlowOBJ);
		when(aNCCareRepo.getBenANCCareDetailsStatus(beneficiaryRegID)).thenReturn("O+");

		String response = registrarServiceMasterData.getBenDetailsForLeftSideByRegIDNew(beneficiaryRegID, benFlowID,
				authorization, comingRequest);

		assertNotNull(response);
		assertTrue(response.contains("John Doe"));
		assertTrue(response.contains("30"));
		assertTrue(response.contains("2023-10-01"));
		assertTrue(response.contains("2023-01-01"));
		assertTrue(response.contains("O+"));

		verify(beneficiaryFlowStatusRepo, times(1)).getBenDetailsForLeftSidePanel(benFlowID);
		verify(aNCCareRepo, times(1)).getBenANCCareDetailsStatus(beneficiaryRegID);
	}
}
