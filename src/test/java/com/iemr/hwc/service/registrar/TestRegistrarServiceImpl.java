package com.iemr.hwc.service.registrar;

import static org.mockito.Mockito.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import com.google.gson.JsonObject;
import com.iemr.hwc.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.hwc.data.login.Users;
import com.iemr.hwc.data.registrar.BenGovIdMapping;
import com.iemr.hwc.data.registrar.BeneficiaryData;
import com.iemr.hwc.data.registrar.BeneficiaryDemographicAdditional;
import com.iemr.hwc.data.registrar.BeneficiaryDemographicData;
import com.iemr.hwc.data.registrar.BeneficiaryPhoneMapping;
import com.iemr.hwc.data.registrar.FingerPrintDTO;
import com.iemr.hwc.data.registrar.UserBiometricsMapping;
import com.iemr.hwc.data.registrar.V_BenAdvanceSearch;
import com.iemr.hwc.data.registrar.WrapperRegWorklist;
import com.iemr.hwc.repo.login.UserLoginRepo;
import com.iemr.hwc.repo.registrar.BeneficiaryDemographicAdditionalRepo;
import com.iemr.hwc.repo.registrar.BeneficiaryImageRepo;
import com.iemr.hwc.repo.registrar.RegistrarRepoBenData;
import com.iemr.hwc.repo.registrar.RegistrarRepoBenDemoData;
import com.iemr.hwc.repo.registrar.RegistrarRepoBenGovIdMapping;
import com.iemr.hwc.repo.registrar.RegistrarRepoBenPhoneMapData;
import com.iemr.hwc.repo.registrar.RegistrarRepoBeneficiaryDetails;
import com.iemr.hwc.repo.registrar.ReistrarRepoBenSearch;
import com.iemr.hwc.repo.registrar.UserBiometricsRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import io.swagger.v3.oas.models.PathItem.HttpMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
public class TestRegistrarServiceImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private RegistrarRepoBenData registrarRepoBenData;
	@Mock
	private RegistrarRepoBenDemoData registrarRepoBenDemoData;
	@Mock
	private BeneficiaryDemographicAdditionalRepo beneficiaryDemographicAdditionalRepo;
	@Mock
	private RegistrarRepoBenGovIdMapping registrarRepoBenGovIdMapping;
	@Mock
	private ReistrarRepoBenSearch reistrarRepoBenSearch;
	@Mock
	private RegistrarRepoBenPhoneMapData registrarRepoBenPhoneMapData;
	@Mock
	private RegistrarRepoBeneficiaryDetails registrarRepoBeneficiaryDetails;
	@Mock
	private BeneficiaryImageRepo beneficiaryImageRepo;
	@Mock
	private RestTemplate restTemplate;
	@Mock
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;
	@Mock
	private UserLoginRepo userRepo;
	@Mock
	private UserBiometricsRepo userBiometricsRepo;
	@InjectMocks
	private RegistrarServiceImpl registrarServiceImpl;
	private JsonObject benD;
	private BeneficiaryData beneficiaryData;
	private Long benRegID;
	private List<Object[]> mockResultList;
	private RegistrarServiceImpl registrarService;
	private JsonObject jsonObject;
	private List<Object[]> sampleBeneficiaryDetails;
	private String sampleImage;
	private V_BenAdvanceSearch v_BenAdvanceSearch;
	private List<Object[]> resList;
	private Long beneficiaryRegID;
	private String expectedImage;
	private String registrationUrl = "http://example.com/registration";
	private String authorization = "Bearer token";
	private String comingRequest;
	private BeneficiaryFlowStatus beneficiaryFlowStatus;
	private String villageID;
	private Date lastModifDate;
	private String syncSearchByLocation;
	private String beneficiaryEditUrl = "http://example.com/api/beneficiary/edit";
	private String registrarQuickSearchByIdUrl = "http://example.com/api/beneficiary/searchById";
	private String registrarQuickSearchByPhoneNoUrl = "http://example.com/api/beneficiary/searchByPhoneNo";
	private String requestObj;
	private String registrarAdvanceSearchUrl;
	private String requestOBJ;
	private FingerPrintDTO fingerPrintDTO;
	private Users user;
	private UserBiometricsMapping userBiometricsMapping;

	@BeforeEach
	public void setUp() {
		benD = new JsonObject();
		benD.addProperty("name", "John Doe");
		benD.addProperty("age", 30);

		beneficiaryData = new BeneficiaryData();
		beneficiaryData.setName("John Doe");
		beneficiaryData.setAge(30);
	}

	@Test
	public void testCreateBeneficiary_Success() {
		when(registrarRepoBenData.save(any(BeneficiaryData.class))).thenReturn(beneficiaryData);

		BeneficiaryData result = registrarServiceImpl.createBeneficiary(benD);

		assertNotNull(result);
		assertEquals("John Doe", result.getName());
		assertEquals(30, result.getAge());
		verify(registrarRepoBenData, times(1)).save(any(BeneficiaryData.class));
	}

	@Test
	public void testCreateBeneficiary_Exception() {
		when(registrarRepoBenData.save(any(BeneficiaryData.class))).thenThrow(new RuntimeException("Database error"));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			registrarServiceImpl.createBeneficiary(benD);
		});

		assertEquals("Database error", exception.getMessage());
		verify(registrarRepoBenData, times(1)).save(any(BeneficiaryData.class));
	}

	@Test
	public void testCreateBeneficiaryDemographic() {
		BeneficiaryDemographicData mockData = new BeneficiaryDemographicData();
		mockData.setBenDemographicsID(1L);
		when(registrarRepoBenDemoData.save(any(BeneficiaryDemographicData.class))).thenReturn(mockData);

		Long result = registrarServiceImpl.createBeneficiaryDemographic(benD, benRegID);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(registrarRepoBenDemoData, times(1)).save(any(BeneficiaryDemographicData.class));
	}

	@Test
	public void testCreateBeneficiaryDemographic_NullResponse() {
		when(registrarRepoBenDemoData.save(any(BeneficiaryDemographicData.class))).thenReturn(null);

		Long result = registrarServiceImpl.createBeneficiaryDemographic(benD, benRegID);

		assertNull(result);
		verify(registrarRepoBenDemoData, times(1)).save(any(BeneficiaryDemographicData.class));
	}

	@Test
	public void testCreateBeneficiaryDemographicAdditional_Success() {
		BeneficiaryDemographicAdditional mockResponse = new BeneficiaryDemographicAdditional();
		mockResponse.setBenDemoAdditionalID(1L);

		when(beneficiaryDemographicAdditionalRepo.save(any(BeneficiaryDemographicAdditional.class)))
				.thenReturn(mockResponse);

		Long result = registrarServiceImpl.createBeneficiaryDemographicAdditional(benD, benRegID);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(beneficiaryDemographicAdditionalRepo, times(1)).save(any(BeneficiaryDemographicAdditional.class));
	}

	@Test
	public void testCreateBeneficiaryDemographicAdditional_NullInput() {
		Long result = registrarServiceImpl.createBeneficiaryDemographicAdditional(null, benRegID);

		assertNull(result);
		verify(beneficiaryDemographicAdditionalRepo, times(0)).save(any(BeneficiaryDemographicAdditional.class));
	}

	@Test
	public void testCreateBeneficiaryDemographicAdditional_ExceptionHandling() {
		when(beneficiaryDemographicAdditionalRepo.save(any(BeneficiaryDemographicAdditional.class)))
				.thenThrow(new RuntimeException("Database error"));

		assertThrows(RuntimeException.class, () -> {
			registrarServiceImpl.createBeneficiaryDemographicAdditional(benD, benRegID);
		});

		verify(beneficiaryDemographicAdditionalRepo, times(1)).save(any(BeneficiaryDemographicAdditional.class));
	}

	@Test
	public void testGetBeneficiaryDemographicAdditional() {
		BeneficiaryDemographicAdditional expected = new BeneficiaryDemographicAdditional();
		expected.setBeneficiaryRegID(benRegID);
		expected.setLiteracyStatus("Literate");
		expected.setMotherName("Jane Doe");
		expected.setEmailID("jane.doe@example.com");
		expected.setBankName("Bank of Test");
		expected.setBranchName("Test Branch");
		expected.setiFSCCode("TEST0001234");
		expected.setAccountNo("1234567890");
		expected.setCreatedBy("testUser");

		when(beneficiaryDemographicAdditionalRepo.save(any(BeneficiaryDemographicAdditional.class)))
				.thenReturn(expected);

		Long result = registrarServiceImpl.createBeneficiaryDemographicAdditional(benD, benRegID);

		assertNotNull(result);
		assertEquals(expected.getBenDemoAdditionalID(), result);
		verify(beneficiaryDemographicAdditionalRepo, times(1)).save(any(BeneficiaryDemographicAdditional.class));
	}

	@Test
	public void testCreateBeneficiaryPhoneMapping_Success() {
		BeneficiaryPhoneMapping mockPhoneMapping = new BeneficiaryPhoneMapping();
		mockPhoneMapping.setBenPhMapID(1L);
		when(registrarRepoBenPhoneMapData.save(any(BeneficiaryPhoneMapping.class))).thenReturn(mockPhoneMapping);

		Long result = registrarServiceImpl.createBeneficiaryPhoneMapping(benD, benRegID);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(registrarRepoBenPhoneMapData, times(1)).save(any(BeneficiaryPhoneMapping.class));
	}

	@Test
	public void testCreateBeneficiaryPhoneMapping_Failure() {
		when(registrarRepoBenPhoneMapData.save(any(BeneficiaryPhoneMapping.class))).thenReturn(null);

		Long result = registrarServiceImpl.createBeneficiaryPhoneMapping(benD, benRegID);

		assertNull(result);
		verify(registrarRepoBenPhoneMapData, times(1)).save(any(BeneficiaryPhoneMapping.class));
	}

	@Test
	public void testCreateBenGovIdMapping_Success() {
		List<BenGovIdMapping> mockList = new ArrayList<>();
		mockList.add(new BenGovIdMapping());

		when(registrarRepoBenGovIdMapping.saveAll(anyList())).thenReturn(mockList);

		int result = registrarServiceImpl.createBenGovIdMapping(benD, benRegID);

		assertEquals(1, result);
		verify(registrarRepoBenGovIdMapping, times(1)).saveAll(anyList());
	}

	@Test
	public void testCreateBenGovIdMapping_EmptyInput() {
		JsonObject emptyBenD = new JsonObject();

		int result = registrarServiceImpl.createBenGovIdMapping(emptyBenD, benRegID);

		assertEquals(0, result);
		verify(registrarRepoBenGovIdMapping, times(1)).saveAll(anyList());
	}

	@Test
	public void testCreateBenGovIdMapping_Exception() {
		when(registrarRepoBenGovIdMapping.saveAll(anyList())).thenThrow(new RuntimeException("Database error"));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			registrarServiceImpl.createBenGovIdMapping(benD, benRegID);
		});

		assertEquals("Database error", exception.getMessage());
		verify(registrarRepoBenGovIdMapping, times(1)).saveAll(anyList());
	}

	@Test
	public void testGetRegWorkList() {
		// Arrange
		int input = 1;
		List<Object[]> mockResultList = new ArrayList<>();
		Object[] mockResult = new Object[] { "data1", "data2" };
		mockResultList.add(mockResult);

		when(registrarRepoBenData.getRegistrarWorkList(input)).thenReturn(mockResultList);
		String expectedResponse = WrapperRegWorklist.getRegistrarWorkList(mockResultList);

		// Act
		String actualResponse = registrarServiceImpl.getRegWorkList(input);

		// Assert
		assertEquals(expectedResponse, actualResponse);
		verify(registrarRepoBenData, times(1)).getRegistrarWorkList(input);
	}

	@Test
	public void testGetQuickSearchBenData_ValidID() {
		String benID = "1";
		when(reistrarRepoBenSearch.getQuickSearch(benID)).thenReturn(mockResultList);

		String result = registrarServiceImpl.getQuickSearchBenData(benID);

		assertNotNull(result);
		assertTrue(result.contains("John"));
		verify(reistrarRepoBenData, never()).getQuickSearch(anyString());
		verify(reistrarRepoBenSearch, times(1)).getQuickSearch(benID);
	}

	@Test
	public void testGetQuickSearchBenData_InvalidID() {
		String benID = "999";
		when(reistrarRepoBenSearch.getQuickSearch(benID)).thenReturn(Collections.emptyList());

		String result = registrarServiceImpl.getQuickSearchBenData(benID);

		assertNotNull(result);
		assertTrue(result.isEmpty());
		verify(reistrarRepoBenData, never()).getQuickSearch(anyString());
		verify(reistrarRepoBenSearch, times(1)).getQuickSearch(benID);
	}

	@Test
	public void testGetQuickSearchBenData_EmptyResult() {
		String benID = "2";
		when(reistrarRepoBenSearch.getQuickSearch(benID)).thenReturn(Collections.emptyList());

		String result = registrarServiceImpl.getQuickSearchBenData(benID);

		assertNotNull(result);
		assertTrue(result.isEmpty());
		verify(reistrarRepoBenData, never()).getQuickSearch(anyString());
		verify(reistrarRepoBenSearch, times(1)).getQuickSearch(benID);
	}

	@Test
	public void testGetAdvanceSearchBenData() {
		when(reistrarRepoBenSearch.getAdvanceBenSearchList(anyString(), anyString(), anyString(), anyString(),
				anyString(), anyString(), anyString(), anyString())).thenReturn(resList);

		String result = registrarServiceImpl.getAdvanceSearchBenData(v_BenAdvanceSearch);

		assertNotNull(result);
		assertTrue(result.contains("John"));
		verify(reistrarRepoBenSearch, times(1)).getAdvanceBenSearchList(anyString(), anyString(), anyString(),
				anyString(), anyString(), anyString(), anyString(), anyString());
	}

	@Test
	public void testGetAdvanceSearchBenData_NoResults() {
		when(reistrarRepoBenSearch.getAdvanceBenSearchList(anyString(), anyString(), anyString(), anyString(),
				anyString(), anyString(), anyString(), anyString())).thenReturn(new ArrayList<>());

		String result = registrarServiceImpl.getAdvanceSearchBenData(v_BenAdvanceSearch);

		assertNotNull(result);
		assertTrue(result.isEmpty());
		verify(reistrarRepoBenSearch, times(1)).getAdvanceBenSearchList(anyString(), anyString(), anyString(),
				anyString(), anyString(), anyString(), anyString(), anyString());
	}

	@Test
	public void testGetAdvanceSearchBenData_Exception() {
		when(reistrarRepoBenSearch.getAdvanceBenSearchList(anyString(), anyString(), anyString(), anyString(),
				anyString(), anyString(), anyString(), anyString())).thenThrow(new RuntimeException("Database error"));

		String result = registrarServiceImpl.getAdvanceSearchBenData(v_BenAdvanceSearch);

		assertNotNull(result);
		assertTrue(result.isEmpty());
		verify(reistrarRepoBenSearch, times(1)).getAdvanceBenSearchList(anyString(), anyString(), anyString(),
				anyString(), anyString(), anyString(), anyString(), anyString());
	}

	@Test
	public void testGetBenOBJ_AllFields() {
		JsonObject benD = new JsonObject();
		benD.addProperty("firstName", "John");
		benD.addProperty("lastName", "Doe");
		benD.addProperty("gender", (short) 1);
		benD.addProperty("dob", "2000-01-01T00:00:00.000Z");
		benD.addProperty("maritalStatus", (short) 1);
		benD.addProperty("createdBy", "admin");
		benD.addProperty("fatherName", "Father Doe");
		benD.addProperty("husbandName", "Husband Doe");
		benD.addProperty("aadharNo", "123456789012");
		benD.addProperty("beneficiaryRegID", 1L);
		benD.addProperty("modifiedBy", "admin");

		BeneficiaryData benData = registrarService.getBenOBJ(benD);

		assertEquals("John", benData.getFirstName());
		assertEquals("Doe", benData.getLastName());
		assertEquals((short) 1, benData.getGenderID());
		assertNotNull(benData.getDob());
		assertEquals((short) 1, benData.getMaritalStatusID());
		assertEquals("admin", benData.getCreatedBy());
		assertEquals("Father Doe", benData.getFatherName());
		assertEquals("Husband Doe", benData.getSpouseName());
		assertEquals("123456789012", benData.getAadharNo());
		assertEquals(1L, benData.getBeneficiaryRegID());
		assertEquals("admin", benData.getModifiedBy());
	}

	@Test
	public void testGetBenOBJ_MissingOptionalFields() {
		JsonObject benD = new JsonObject();
		benD.addProperty("firstName", "John");
		benD.addProperty("lastName", "Doe");
		benD.addProperty("gender", (short) 1);
		benD.addProperty("dob", "2000-01-01T00:00:00.000Z");
		benD.addProperty("maritalStatus", (short) 1);
		benD.addProperty("createdBy", "admin");

		BeneficiaryData benData = registrarService.getBenOBJ(benD);

		assertEquals("John", benData.getFirstName());
		assertEquals("Doe", benData.getLastName());
		assertEquals((short) 1, benData.getGenderID());
		assertNotNull(benData.getDob());
		assertEquals((short) 1, benData.getMaritalStatusID());
		assertEquals("admin", benData.getCreatedBy());
		assertNull(benData.getFatherName());
		assertNull(benData.getSpouseName());
		assertNull(benData.getAadharNo());
		assertNull(benData.getBeneficiaryRegID());
		assertNull(benData.getModifiedBy());
	}

	@Test
	public void testGetBenOBJ_EmptyJsonObject() {
		JsonObject benD = new JsonObject();

		BeneficiaryData benData = registrarService.getBenOBJ(benD);

		assertNull(benData.getFirstName());
		assertNull(benData.getLastName());
		assertNull(benData.getGenderID());
		assertNull(benData.getDob());
		assertNull(benData.getMaritalStatusID());
		assertNull(benData.getCreatedBy());
		assertNull(benData.getFatherName());
		assertNull(benData.getSpouseName());
		assertNull(benData.getAadharNo());
		assertNull(benData.getBeneficiaryRegID());
		assertNull(benData.getModifiedBy());
	}

	@Test
	public void testGetBenDemoOBJ_AllFieldsPresent() {
		Long benRegID = 100L;
		BeneficiaryDemographicData result = registrarServiceImpl.getBenDemoOBJ(jsonObject, benRegID);

		assertNotNull(result);
		assertEquals(benRegID, result.getBeneficiaryRegID());
		assertEquals(1, result.getCountryID());
		assertEquals(2, result.getStateID());
		assertEquals(3, result.getDistrictID());
		assertEquals(4, result.getBlockID());
		assertEquals(5, result.getServicePointID());
		assertEquals(6, result.getDistrictBranchID());
		assertEquals("testUser", result.getCreatedBy());
		assertEquals(7, result.getCommunityID());
		assertEquals(8, result.getReligionID());
		assertEquals(9, result.getOccupationID());
		assertEquals(10, result.getEducationID());
		assertEquals(11, result.getIncomeStatusID());
		assertEquals(12L, result.getBenDemographicsID());
		assertEquals("testUserModified", result.getModifiedBy());
	}

	@Test
	public void testGetBenDemoOBJ_SomeFieldsMissing() {
		jsonObject.remove("stateID");
		jsonObject.remove("blockID");
		Long benRegID = 100L;
		BeneficiaryDemographicData result = registrarServiceImpl.getBenDemoOBJ(jsonObject, benRegID);

		assertNotNull(result);
		assertEquals(benRegID, result.getBeneficiaryRegID());
		assertEquals(1, result.getCountryID());
		assertNull(result.getStateID());
		assertEquals(3, result.getDistrictID());
		assertNull(result.getBlockID());
		assertEquals(5, result.getServicePointID());
		assertEquals(6, result.getDistrictBranchID());
		assertEquals("testUser", result.getCreatedBy());
		assertEquals(7, result.getCommunityID());
		assertEquals(8, result.getReligionID());
		assertEquals(9, result.getOccupationID());
		assertEquals(10, result.getEducationID());
		assertEquals(11, result.getIncomeStatusID());
		assertEquals(12L, result.getBenDemographicsID());
		assertEquals("testUserModified", result.getModifiedBy());
	}

	@Test
	public void testGetBenDemoOBJ_AllFieldsMissing() {
		jsonObject = new JsonObject();
		Long benRegID = 100L;
		BeneficiaryDemographicData result = registrarServiceImpl.getBenDemoOBJ(jsonObject, benRegID);

		assertNotNull(result);
		assertEquals(benRegID, result.getBeneficiaryRegID());
		assertNull(result.getCountryID());
		assertNull(result.getStateID());
		assertNull(result.getDistrictID());
		assertNull(result.getBlockID());
		assertNull(result.getServicePointID());
		assertNull(result.getDistrictBranchID());
		assertNull(result.getCreatedBy());
		assertNull(result.getCommunityID());
		assertNull(result.getReligionID());
		assertNull(result.getOccupationID());
		assertNull(result.getEducationID());
		assertNull(result.getIncomeStatusID());
		assertNull(result.getBenDemographicsID());
		assertNull(result.getModifiedBy());
	}

	@Test
	public void testGetBenPhoneOBJ_AllFieldsPresent() {
		BeneficiaryPhoneMapping result = registrarServiceImpl.getBenPhoneOBJ(jsonObject, benRegID);

		assertNotNull(result);
		assertEquals(benRegID, result.getBenificiaryRegID());
		assertEquals("1234567890", result.getPhoneNo());
		assertEquals("testUser", result.getCreatedBy());
		assertEquals(1L, result.getBenPhMapID());
		assertEquals("testUserModified", result.getModifiedBy());
	}

	@Test
	public void testGetBenPhoneOBJ_MissingOptionalFields() {
		jsonObject.remove("benPhMapID");
		jsonObject.remove("modifiedBy");

		BeneficiaryPhoneMapping result = registrarServiceImpl.getBenPhoneOBJ(jsonObject, benRegID);

		assertNotNull(result);
		assertEquals(benRegID, result.getBenificiaryRegID());
		assertEquals("1234567890", result.getPhoneNo());
		assertEquals("testUser", result.getCreatedBy());
		assertNull(result.getBenPhMapID());
		assertNull(result.getModifiedBy());
	}

	@Test
	public void testGetBenPhoneOBJ_NullValues() {
		jsonObject.add("benPhMapID", null);
		jsonObject.add("modifiedBy", null);

		BeneficiaryPhoneMapping result = registrarServiceImpl.getBenPhoneOBJ(jsonObject, benRegID);

		assertNotNull(result);
		assertEquals(benRegID, result.getBenificiaryRegID());
		assertEquals("1234567890", result.getPhoneNo());
		assertEquals("testUser", result.getCreatedBy());
		assertNull(result.getBenPhMapID());
		assertNull(result.getModifiedBy());
	}

	@Test
	public void testGetBeneficiaryDetails_ValidDetails() {
		Long beneficiaryRegID = 1L;

		when(registrarRepoBeneficiaryDetails.getBeneficiaryDetails(beneficiaryRegID))
				.thenReturn(sampleBeneficiaryDetails);
		when(beneficiaryImageRepo.getBenImage(beneficiaryRegID)).thenReturn(sampleImage);

		String result = registrarServiceImpl.getBeneficiaryDetails(beneficiaryRegID);

		assertNotNull(result);
		assertTrue(result.contains("John"));
		assertTrue(result.contains("Doe"));
		assertTrue(result.contains("sampleImageData"));

		verify(registrarRepoBeneficiaryDetails, times(1)).getBeneficiaryDetails(beneficiaryRegID);
		verify(beneficiaryImageRepo, times(1)).getBenImage(beneficiaryRegID);
	}

	@Test
	public void testGetBeneficiaryDetails_NoDetails() {
		Long beneficiaryRegID = 1L;

		when(registrarRepoBeneficiaryDetails.getBeneficiaryDetails(beneficiaryRegID))
				.thenReturn(Collections.emptyList());

		String result = registrarServiceImpl.getBeneficiaryDetails(beneficiaryRegID);

		assertNull(result);

		verify(registrarRepoBeneficiaryDetails, times(1)).getBeneficiaryDetails(beneficiaryRegID);
		verify(beneficiaryImageRepo, times(0)).getBenImage(beneficiaryRegID);
	}

	@Test
	public void testGetBenImage_ValidImage() {
		when(beneficiaryImageRepo.getBenImage(beneficiaryRegID)).thenReturn(expectedImage);

		String result = registrarServiceImpl.getBenImage(beneficiaryRegID);

		Map<String, String> expectedMap = new HashMap<>();
		expectedMap.put("benImage", expectedImage);
		String expectedJson = new Gson().toJson(expectedMap);

		assertEquals(expectedJson, result);
		verify(beneficiaryImageRepo, times(1)).getBenImage(beneficiaryRegID);
	}

	@Test
	public void testGetBenImage_NullImage() {
		when(beneficiaryImageRepo.getBenImage(beneficiaryRegID)).thenReturn(null);

		String result = registrarServiceImpl.getBenImage(beneficiaryRegID);

		Map<String, String> expectedMap = new HashMap<>();
		String expectedJson = new Gson().toJson(expectedMap);

		assertEquals(expectedJson, result);
		verify(beneficiaryImageRepo, times(1)).getBenImage(beneficiaryRegID);
	}

	@Test
	public void testUpdateBeneficiary_Success() {
		BeneficiaryData beneficiaryData = registrarServiceImpl.getBenOBJ(benD);

		when(registrarRepoBenData.updateBeneficiaryData(beneficiaryData.getFirstName(), beneficiaryData.getLastName(),
				beneficiaryData.getGenderID(), beneficiaryData.getDob(), beneficiaryData.getMaritalStatusID(),
				beneficiaryData.getFatherName(), beneficiaryData.getSpouseName(), beneficiaryData.getAadharNo(),
				beneficiaryData.getModifiedBy(), beneficiaryData.getBeneficiaryRegID())).thenReturn(1);

		int response = registrarServiceImpl.updateBeneficiary(benD);

		assertEquals(1, response);
		verify(registrarRepoBenData, times(1)).updateBeneficiaryData(beneficiaryData.getFirstName(),
				beneficiaryData.getLastName(), beneficiaryData.getGenderID(), beneficiaryData.getDob(),
				beneficiaryData.getMaritalStatusID(), beneficiaryData.getFatherName(), beneficiaryData.getSpouseName(),
				beneficiaryData.getAadharNo(), beneficiaryData.getModifiedBy(), beneficiaryData.getBeneficiaryRegID());
	}

	@Test
	public void testUpdateBeneficiary_MissingFields() {
		benD.remove("lastName");
		BeneficiaryData beneficiaryData = registrarServiceImpl.getBenOBJ(benD);

		when(registrarRepoBenData.updateBeneficiaryData(beneficiaryData.getFirstName(), beneficiaryData.getLastName(),
				beneficiaryData.getGenderID(), beneficiaryData.getDob(), beneficiaryData.getMaritalStatusID(),
				beneficiaryData.getFatherName(), beneficiaryData.getSpouseName(), beneficiaryData.getAadharNo(),
				beneficiaryData.getModifiedBy(), beneficiaryData.getBeneficiaryRegID())).thenReturn(0);

		int response = registrarServiceImpl.updateBeneficiary(benD);

		assertEquals(0, response);
		verify(registrarRepoBenData, times(1)).updateBeneficiaryData(beneficiaryData.getFirstName(),
				beneficiaryData.getLastName(), beneficiaryData.getGenderID(), beneficiaryData.getDob(),
				beneficiaryData.getMaritalStatusID(), beneficiaryData.getFatherName(), beneficiaryData.getSpouseName(),
				beneficiaryData.getAadharNo(), beneficiaryData.getModifiedBy(), beneficiaryData.getBeneficiaryRegID());
	}

	@Test
	public void testUpdateBeneficiaryDemographic() {
		when(registrarRepoBenDemoData.updateBendemographicData(anyInt(), anyInt(), anyInt(), anyInt(), anyInt(),
				anyInt(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyString(), anyLong()))
				.thenReturn(1);

		int result = registrarServiceImpl.updateBeneficiaryDemographic(benD, benRegID);

		assertEquals(1, result);
		verify(registrarRepoBenDemoData, times(1)).updateBendemographicData(eq(1), eq(2), eq(3), eq(4), eq(5), eq(6),
				eq((short) 7), eq((short) 8), eq((short) 9), eq((short) 10), eq((short) 11), eq("testUser"),
				eq(benRegID));
	}

	@Test
	public void testUpdateBeneficiaryDemographic_NoUpdate() {
		when(registrarRepoBenDemoData.updateBendemographicData(anyInt(), anyInt(), anyInt(), anyInt(), anyInt(),
				anyInt(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyString(), anyLong()))
				.thenReturn(0);

		int result = registrarServiceImpl.updateBeneficiaryDemographic(benD, benRegID);

		assertEquals(0, result);
		verify(registrarRepoBenDemoData, times(1)).updateBendemographicData(eq(1), eq(2), eq(3), eq(4), eq(5), eq(6),
				eq((short) 7), eq((short) 8), eq((short) 9), eq((short) 10), eq((short) 11), eq("testUser"),
				eq(benRegID));
	}

	@Test
	public void testUpdateBeneficiaryPhoneMapping_Success() {
		when(registrarRepoBenPhoneMapData.updateBenPhoneMap(any(String.class), any(String.class), any(Long.class)))
				.thenReturn(1);

		int result = registrarServiceImpl.updateBeneficiaryPhoneMapping(benD, benRegID);

		assertEquals(1, result);
		verify(registrarRepoBenPhoneMapData, times(1)).updateBenPhoneMap(any(String.class), any(String.class),
				any(Long.class));
	}

	@Test
	public void testUpdateBeneficiaryPhoneMapping_MissingFields() {
		benD.remove("phoneNo");

		int result = registrarServiceImpl.updateBeneficiaryPhoneMapping(benD, benRegID);

		assertEquals(0, result);
		verify(registrarRepoBenPhoneMapData, times(0)).updateBenPhoneMap(any(String.class), any(String.class),
				any(Long.class));
	}

	@Test
	public void testUpdateBeneficiaryPhoneMapping_InvalidData() {
		benD.addProperty("phoneNo", "");

		int result = registrarServiceImpl.updateBeneficiaryPhoneMapping(benD, benRegID);

		assertEquals(0, result);
		verify(registrarRepoBenPhoneMapData, times(0)).updateBenPhoneMap(any(String.class), any(String.class),
				any(Long.class));
	}

	@Test
	public void testUpdateBenGovIdMapping() {
		// Arrange
		when(registrarRepoBenGovIdMapping.deletePreviousGovMapID(benRegID)).thenReturn(1);
		when(registrarRepoBenGovIdMapping.saveAll(any())).thenReturn(new ArrayList<>());

		// Act
		int result = registrarServiceImpl.updateBenGovIdMapping(benD, benRegID);

		// Assert
		verify(registrarRepoBenGovIdMapping, times(1)).deletePreviousGovMapID(benRegID);
		verify(registrarRepoBenGovIdMapping, times(1)).saveAll(any());
		assertEquals(1, result);
	}

	@Test
	public void testUpdateBenGovIdMappingWithEmptyMappings() {
		// Arrange
		JsonObject emptyBenD = new JsonObject();
		when(registrarRepoBenGovIdMapping.deletePreviousGovMapID(benRegID)).thenReturn(1);
		when(registrarRepoBenGovIdMapping.saveAll(any())).thenReturn(Collections.emptyList());

		// Act
		int result = registrarServiceImpl.updateBenGovIdMapping(emptyBenD, benRegID);

		// Assert
		verify(registrarRepoBenGovIdMapping, times(1)).deletePreviousGovMapID(benRegID);
		verify(registrarRepoBenGovIdMapping, times(1)).saveAll(any());
		assertEquals(0, result);
	}

	@Test
	public void testUpdateBeneficiaryDemographicAdditional_Success() {
		BeneficiaryDemographicAdditional existingData = new BeneficiaryDemographicAdditional();
		existingData.setBenDemoAdditionalID(1L);

		when(beneficiaryDemographicAdditionalRepo.getBeneficiaryDemographicAdditional(benRegID))
				.thenReturn(existingData);
		when(beneficiaryDemographicAdditionalRepo.updateBeneficiaryDemographicAdditional(anyString(), anyString(),
				anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyLong())).thenReturn(1);

		int result = registrarServiceImpl.updateBeneficiaryDemographicAdditional(benD, benRegID);

		assertEquals(1, result);
		verify(beneficiaryDemographicAdditionalRepo, times(1)).updateBeneficiaryDemographicAdditional(anyString(),
				anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyLong());
	}

	@Test
	public void testUpdateBeneficiaryDemographicAdditional_NoExistingData() {
		when(beneficiaryDemographicAdditionalRepo.getBeneficiaryDemographicAdditional(benRegID)).thenReturn(null);
		when(beneficiaryDemographicAdditionalRepo.save(any(BeneficiaryDemographicAdditional.class)))
				.thenReturn(new BeneficiaryDemographicAdditional());

		int result = registrarServiceImpl.updateBeneficiaryDemographicAdditional(benD, benRegID);

		assertEquals(1, result);
		verify(beneficiaryDemographicAdditionalRepo, times(1)).save(any(BeneficiaryDemographicAdditional.class));
	}

	@Test
	public void testUpdateBeneficiaryDemographicAdditional_Exception() {
		when(beneficiaryDemographicAdditionalRepo.getBeneficiaryDemographicAdditional(benRegID))
				.thenThrow(new RuntimeException("Database error"));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			registrarServiceImpl.updateBeneficiaryDemographicAdditional(benD, benRegID);
		});

		assertEquals("Database error", exception.getMessage());
	}

	@Test
	public void testUpdateBeneficiaryImage_ImageProvided_ExistsInRepo() {
		when(beneficiaryImageRepo.findBenImage(benRegID)).thenReturn(benRegID);
		when(beneficiaryImageRepo.updateBeneficiaryImage(anyString(), anyString(), eq(benRegID))).thenReturn(1);

		int response = registrarServiceImpl.updateBeneficiaryImage(benD, benRegID);

		assertEquals(1, response);
		verify(beneficiaryImageRepo, times(1)).updateBeneficiaryImage(anyString(), anyString(), eq(benRegID));
	}

	@Test
	public void testUpdateBeneficiaryImage_ImageProvided_NotExistsInRepo() {
		when(beneficiaryImageRepo.findBenImage(benRegID)).thenReturn(null);
		when(beneficiaryImageRepo.save(any(BeneficiaryImage.class))).thenReturn(new BeneficiaryImage());

		int response = registrarServiceImpl.updateBeneficiaryImage(benD, benRegID);

		assertEquals(1, response);
		verify(beneficiaryImageRepo, times(1)).save(any(BeneficiaryImage.class));
	}

	@Test
	public void testUpdateBeneficiaryImage_NoImageProvided() {
		benD.remove("image");

		int response = registrarServiceImpl.updateBeneficiaryImage(benD, benRegID);

		assertEquals(1, response);
		verify(beneficiaryImageRepo, never()).updateBeneficiaryImage(anyString(), anyString(), eq(benRegID));
		verify(beneficiaryImageRepo, never()).save(any(BeneficiaryImage.class));
	}

	@Test
	public void testGetBeneficiaryPersonalDetails_Found() {
		Long benRegID = 1L;

		List<Object[]> benDetailsList = new ArrayList<>();
		benDetailsList.add(new Object[] { 1L, "John", "Doe", 1, new Date(), 1, "Father Name", "Spouse Name",
				"123456789012", "CreatedBy", "ModifiedBy" });

		List<Object[]> beneficiaryDemographicData = new ArrayList<>();
		beneficiaryDemographicData.add(new Object[] { 1L, 1L, "Service Point Name" });

		when(registrarRepoBenData.getBenDetailsByRegID(benRegID)).thenReturn(benDetailsList);
		when(registrarRepoBenDemoData.getBeneficiaryDemographicData(benRegID)).thenReturn(beneficiaryDemographicData);

		BeneficiaryData result = registrarServiceImpl.getBeneficiaryPersonalDetails(benRegID);

		assertNotNull(result);
		assertEquals("John", result.getFirstName());
		assertEquals("Doe", result.getLastName());
		assertEquals("Male", result.getGenderName());
		assertEquals("Service Point Name", result.getServicePointName());
	}

	@Test
	public void testGetBeneficiaryPersonalDetails_NotFound() {
		Long benRegID = 1L;

		when(registrarRepoBenData.getBenDetailsByRegID(benRegID)).thenReturn(Collections.emptyList());

		BeneficiaryData result = registrarServiceImpl.getBeneficiaryPersonalDetails(benRegID);

		assertNull(result);
	}

	@Test
	public void testRegisterBeneficiary_Success() throws Exception {
		// Mocking RestTemplate response
		JSONObject responseJson = new JSONObject();
		responseJson.put("data", new JSONObject().put("beneficiaryRegID", 1).put("beneficiaryID", 1));
		ResponseEntity<String> responseEntity = ResponseEntity.ok(responseJson.toString());

		when(restTemplate.exchange(eq(registrationUrl), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
				.thenReturn(responseEntity);

		// Mocking InputMapper
		when(InputMapper.gson().fromJson(comingRequest, BeneficiaryFlowStatus.class)).thenReturn(beneficiaryFlowStatus);

		// Call the method
		String response = registrarServiceImpl.registerBeneficiary(comingRequest, authorization);

		// Verify the response
		assertTrue(response.contains("Beneficiary successfully registered. Beneficiary ID is : 1"));

		// Verify interactions
		verify(restTemplate, times(1)).exchange(eq(registrationUrl), eq(HttpMethod.POST), any(HttpEntity.class),
				eq(String.class));
		verify(commonBenStatusFlowServiceImpl, never()).createBenFlowRecord(anyString(), anyLong(), anyLong());
	}

	@Test
	public void testRegisterBeneficiary_Failure() throws Exception {
		// Mocking RestTemplate response
		ResponseEntity<String> responseEntity = ResponseEntity.status(500).body("");

		when(restTemplate.exchange(eq(registrationUrl), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
				.thenReturn(responseEntity);

		// Call the method
		String response = registrarServiceImpl.registerBeneficiary(comingRequest, authorization);

		// Verify the response
		assertNull(response);

		// Verify interactions
		verify(restTemplate, times(1)).exchange(eq(registrationUrl), eq(HttpMethod.POST), any(HttpEntity.class),
				eq(String.class));
		verify(commonBenStatusFlowServiceImpl, never()).createBenFlowRecord(anyString(), anyLong(), anyLong());
	}

	public void testGetBeneficiaryByBlockIDAndLastModDate_Success() {
		// Arrange
		JSONObject obj = new JSONObject();
		obj.put("villageID", Integer.parseInt(villageID));
		obj.put("lastModifiedDate", lastModifDate.getTime());

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", "application/json");
		HttpEntity<String> request = new HttpEntity<>(obj.toString(), headers);

		String expectedResponse = "{\"data\":\"someData\"}";
		ResponseEntity<String> responseEntity = ResponseEntity.ok(expectedResponse);

		when(restTemplate.exchange(eq(syncSearchByLocation), eq(HttpMethod.POST), eq(request), eq(String.class)))
				.thenReturn(responseEntity);

		// Act
		String result = registrarServiceImpl.getBeneficiaryByBlockIDAndLastModDate(villageID, lastModifDate,
				authorization);

		// Assert
		assertEquals(expectedResponse, result);
		verify(restTemplate, times(1)).exchange(eq(syncSearchByLocation), eq(HttpMethod.POST), eq(request),
				eq(String.class));
	}

	@Test
	public void testGetBeneficiaryByBlockIDAndLastModDate_Exception() {
		// Arrange
		JSONObject obj = new JSONObject();
		obj.put("villageID", Integer.parseInt(villageID));
		obj.put("lastModifiedDate", lastModifDate.getTime());

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", "application/json");
		HttpEntity<String> request = new HttpEntity<>(obj.toString(), headers);

		when(restTemplate.exchange(eq(syncSearchByLocation), eq(HttpMethod.POST), eq(request), eq(String.class)))
				.thenThrow(new RuntimeException("Exception occurred"));

		// Act
		String result = registrarServiceImpl.getBeneficiaryByBlockIDAndLastModDate(villageID, lastModifDate,
				authorization);

		// Assert
		assertNull(result);
		verify(restTemplate, times(1)).exchange(eq(syncSearchByLocation), eq(HttpMethod.POST), eq(request),
				eq(String.class));
	}

	@Test
	public void testUpdateBeneficiary_PassToNurseTrue() throws Exception {
		String comingRequest = "{\"passToNurse\": true}";
		String authorization = "Bearer token";
		BeneficiaryFlowStatus beneficiaryFlowStatus = new BeneficiaryFlowStatus();
		beneficiaryFlowStatus.setPassToNurse(true);

		ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.OK);
		when(restTemplate.exchange(eq(beneficiaryEditUrl), eq(HttpMethod.POST), any(HttpEntity.class),
				eq(String.class))).thenReturn(responseEntity);

		Integer result = registrarService.updateBeneficiary(comingRequest, authorization);

		assertEquals(Integer.valueOf(1), result);
	}

	@Test
	public void testUpdateBeneficiary_PassToNurseFalse() throws Exception {
		String comingRequest = "{\"passToNurse\": false}";
		String authorization = "Bearer token";
		BeneficiaryFlowStatus beneficiaryFlowStatus = new BeneficiaryFlowStatus();
		beneficiaryFlowStatus.setPassToNurse(false);

		ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.OK);
		when(restTemplate.exchange(eq(beneficiaryEditUrl), eq(HttpMethod.POST), any(HttpEntity.class),
				eq(String.class))).thenReturn(responseEntity);

		Integer result = registrarService.updateBeneficiary(comingRequest, authorization);

		assertEquals(Integer.valueOf(1), result);
	}

	@Test
	public void testUpdateBeneficiary_UnsuccessfulResponse() throws Exception {
		String comingRequest = "{\"passToNurse\": true}";
		String authorization = "Bearer token";

		ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		when(restTemplate.exchange(eq(beneficiaryEditUrl), eq(HttpMethod.POST), any(HttpEntity.class),
				eq(String.class))).thenReturn(responseEntity);

		Integer result = registrarService.updateBeneficiary(comingRequest, authorization);

		assertNull(result);
	}

	@Test
	public void testBeneficiaryQuickSearch_ByBeneficiaryID() throws Exception {
		String requestObj = "{\"beneficiaryID\": \"12345\"}";
		String authorization = "Bearer token";
		String expectedResponse = "{\"result\": \"success\"}";

		ResponseEntity<String> responseEntity = new ResponseEntity<>(expectedResponse, HttpStatus.OK);
		when(restTemplate.exchange(eq(registrarQuickSearchByIdUrl), eq(HttpMethod.POST), any(HttpEntity.class),
				eq(String.class))).thenReturn(responseEntity);

		String result = registrarService.beneficiaryQuickSearch(requestObj, authorization);

		assertEquals(expectedResponse, result);
	}

	@Test
	public void testBeneficiaryQuickSearch_ByPhoneNo() throws Exception {
		String requestObj = "{\"phoneNo\": \"9876543210\"}";
		String authorization = "Bearer token";
		String expectedResponse = "{\"result\": \"success\"}";

		ResponseEntity<String> responseEntity = new ResponseEntity<>(expectedResponse, HttpStatus.OK);
		when(restTemplate.exchange(eq(registrarQuickSearchByPhoneNoUrl), eq(HttpMethod.POST), any(HttpEntity.class),
				eq(String.class))).thenReturn(responseEntity);

		String result = registrarService.beneficiaryQuickSearch(requestObj, authorization);

		assertEquals(expectedResponse, result);
	}

	@Test
	public void testBeneficiaryQuickSearch_NoValidCriteria() throws Exception {
		String requestObj = "{}";
		String authorization = "Bearer token";

		String result = registrarService.beneficiaryQuickSearch(requestObj, authorization);

		assertNull(result);
	}

	@Test
	public void testBeneficiaryAdvanceSearch_Success() {
		String expectedResponse = "{\"data\":\"some data\"}";
		ResponseEntity<String> responseEntity = ResponseEntity.ok(expectedResponse);

		when(restTemplate.exchange(eq(registrarAdvanceSearchUrl), eq(HttpMethod.POST), any(HttpEntity.class),
				eq(String.class))).thenReturn(responseEntity);

		String actualResponse = registrarService.beneficiaryAdvanceSearch(requestObj, authorization);

		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void testBeneficiaryAdvanceSearch_NoResponseBody() {
		ResponseEntity<String> responseEntity = ResponseEntity.ok().build();

		when(restTemplate.exchange(eq(registrarAdvanceSearchUrl), eq(HttpMethod.POST), any(HttpEntity.class),
				eq(String.class))).thenReturn(responseEntity);

		String actualResponse = registrarService.beneficiaryAdvanceSearch(requestObj, authorization);

		assertNull(actualResponse);
	}

	@Test
	public void testBeneficiaryAdvanceSearch_Exception() {
		when(restTemplate.exchange(eq(registrarAdvanceSearchUrl), eq(HttpMethod.POST), any(HttpEntity.class),
				eq(String.class))).thenThrow(new RuntimeException("Exception occurred"));

		assertThrows(RuntimeException.class, () -> {
			registrarService.beneficiaryAdvanceSearch(requestObj, authorization);
		});
	}

	@Test
	public void testSearchAndSubmitBeneficiaryToNurse_Success() throws Exception {
		when(commonBenStatusFlowServiceImpl.createBenFlowRecord(anyString(), isNull(), isNull())).thenReturn(1);

		int result = registrarServiceImpl.searchAndSubmitBeneficiaryToNurse(requestOBJ);

		assertEquals(1, result);
		verify(commonBenStatusFlowServiceImpl, times(1)).createBenFlowRecord(anyString(), isNull(), isNull());
	}

	@Test
	public void testSearchAndSubmitBeneficiaryToNurse_Exception() throws Exception {
		when(commonBenStatusFlowServiceImpl.createBenFlowRecord(anyString(), isNull(), isNull()))
				.thenThrow(new Exception("Error"));

		Exception exception = assertThrows(Exception.class, () -> {
			registrarServiceImpl.searchAndSubmitBeneficiaryToNurse(requestOBJ);
		});

		assertEquals("Error", exception.getMessage());
		verify(commonBenStatusFlowServiceImpl, times(1)).createBenFlowRecord(anyString(), isNull(), isNull());
	}

	@Test
	public void testSaveFingerprints_UserFound_SaveSuccessful() {
		when(userRepo.getUserByUsername("testUser")).thenReturn(user);
		when(userBiometricsRepo.save(any(UserBiometricsMapping.class))).thenReturn(userBiometricsMapping);

		String response = registrarService.saveFingerprints(fingerPrintDTO);

		assertEquals("ok", response);
		verify(userRepo, times(1)).getUserByUsername("testUser");
		verify(userBiometricsRepo, times(1)).save(any(UserBiometricsMapping.class));
	}

	@Test
	public void testSaveFingerprints_UserFound_SaveFailed() {
		when(userRepo.getUserByUsername("testUser")).thenReturn(user);
		when(userBiometricsRepo.save(any(UserBiometricsMapping.class))).thenReturn(null);

		String response = registrarService.saveFingerprints(fingerPrintDTO);

		assertEquals("ko", response);
		verify(userRepo, times(1)).getUserByUsername("testUser");
		verify(userBiometricsRepo, times(1)).save(any(UserBiometricsMapping.class));
	}

	@Test
	public void testSaveFingerprints_UserNotFound() {
		when(userRepo.getUserByUsername("testUser")).thenReturn(null);

		String response = registrarService.saveFingerprints(fingerPrintDTO);

		assertEquals("wrong username", response);
		verify(userRepo, times(1)).getUserByUsername("testUser");
		verify(userBiometricsRepo, times(0)).save(any(UserBiometricsMapping.class));
	}

	@Test
	public void testGetFingerprintsByUserID() {
		Long userID = 1L;
		when(userBiometricsRepo.getFingerprintsByUserID(userID)).thenReturn(userBiometricsMapping);

		UserBiometricsMapping result = registrarService.getFingerprintsByUserID(userID);

		assertNotNull(result);
		assertEquals(userBiometricsMapping.getUserID(), result.getUserID());
		assertEquals(userBiometricsMapping.getFirstName(), result.getFirstName());
		assertEquals(userBiometricsMapping.getLastName(), result.getLastName());
		assertEquals(userBiometricsMapping.getUserName(), result.getUserName());
		assertEquals(userBiometricsMapping.getRightThumb(), result.getRightThumb());
		assertEquals(userBiometricsMapping.getRightIndexFinger(), result.getRightIndexFinger());
		assertEquals(userBiometricsMapping.getLeftThumb(), result.getLeftThumb());
		assertEquals(userBiometricsMapping.getLeftIndexFinger(), result.getLeftIndexFinger());

		verify(userBiometricsRepo, times(1)).getFingerprintsByUserID(userID);
	}

	@Test
	public void testGetFingerprintsByUserID_UserNotFound() {
		Long userID = 2L;
		when(userBiometricsRepo.getFingerprintsByUserID(userID)).thenReturn(null);

		UserBiometricsMapping result = registrarService.getFingerprintsByUserID(userID);

		assertNull(result);
		verify(userBiometricsRepo, times(1)).getFingerprintsByUserID(userID);
	}
}
