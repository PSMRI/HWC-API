package com.iemr.hwc.service.choApp;

import static org.mockito.Mockito.*;

import java.lang.System.Logger;

import org.apache.commons.collections.map.MultiValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.*;
import org.springframework.web.client.*;

import com.iemr.hwc.data.choApp.OutreachActivity;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.choApp.OutreachActivityRepo;
import com.iemr.hwc.repo.choApp.UserActivityLogsRepo;
import com.iemr.hwc.repo.doctor.PrescriptionTemplatesRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.generalOPD.GeneralOPDServiceImpl;
import com.iemr.hwc.utils.request.SyncSearchRequest;

public class TestCHOAppSyncServiceImpl {

	@RunWith(MockitoJUnitRunner.class)
	@Mock
	private RestTemplate restTemplate;

	@Mock
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;

	@Mock
	private Logger logger;
	@Mock
	private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;

	@Mock
	private UserActivityLogsRepo userActivityLogsRepo;

	@Mock
	private GeneralOPDServiceImpl generalOPDServiceImpl;

	@Mock
	private BenVisitDetailRepo benVisitDetailRepo;
	@Mock
	private PrescriptionTemplatesRepo prescriptionTemplatesRepo;
	@Mock
	private OutreachActivityRepo outreachActivityRepo;

	@InjectMocks
	private CHOAppSyncServiceImpl choAppSyncServiceImpl;

	private String registrationUrl = "http://example.com/register";
	private SyncSearchRequest syncSearchRequest;
	private String authorization;
	private SyncSearchRequest validRequest;
	private SyncSearchRequest invalidRequest;
	private String requestObj;
	private OutreachActivity activity;

	@Before
	public void setUp() {
		// Initialize any required setup here
	}

	@Test
	public void testRegisterCHOAPPBeneficiary_Success() throws Exception {
		String comingRequest = "{\"someKey\":\"someValue\"}";
		String authorization = "Bearer someToken";

		String responseBody = "{\"statusCode\":200,\"data\":{\"beneficiaryRegID\":12345,\"beneficiaryID\":67890}}";
		ResponseEntity<String> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);

		when(restTemplate.exchange(eq(registrationUrl), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
				.thenReturn(responseEntity);
		when(commonBenStatusFlowServiceImpl.createBenFlowRecord(anyString(), anyLong(), anyLong())).thenReturn(1);

		ResponseEntity<String> response = choAppSyncServiceImpl.registerCHOAPPBeneficiary(comingRequest, authorization);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().contains("\"beneficiaryID\":67890"));
		assertTrue(response.getBody().contains("\"beneficiaryRegID\":12345"));
	}

	@Test
	public void testRegisterCHOAPPBeneficiary_ResourceAccessException() {
		String comingRequest = "{\"someKey\":\"someValue\"}";
		String authorization = "Bearer someToken";

		when(restTemplate.exchange(eq(registrationUrl), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
				.thenThrow(new ResourceAccessException("Connection error"));

		ResponseEntity<String> response = choAppSyncServiceImpl.registerCHOAPPBeneficiary(comingRequest, authorization);

		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
		assertTrue(response.getBody().contains("Error establishing connection with Common-API service."));
	}

	@Test
	public void testRegisterCHOAPPBeneficiary_JSONException() {
		String comingRequest = "{\"someKey\":\"someValue\"}";
		String authorization = "Bearer someToken";

		String responseBody = "Invalid JSON";
		ResponseEntity<String> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);

		when(restTemplate.exchange(eq(registrationUrl), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
				.thenReturn(responseEntity);

		ResponseEntity<String> response = choAppSyncServiceImpl.registerCHOAPPBeneficiary(comingRequest, authorization);

		assertEquals(HttpStatus.BAD_GATEWAY, response.getStatusCode());
		assertTrue(response.getBody().contains("Encountered JSON exception"));
	}

	@Test
	public void testRegisterCHOAPPBeneficiary_RestClientResponseException() {
		String comingRequest = "{\"someKey\":\"someValue\"}";
		String authorization = "Bearer someToken";

		when(restTemplate.exchange(eq(registrationUrl), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
				.thenThrow(new RestClientResponseException("Error", 500, "Internal Server Error", null, null, null));

		ResponseEntity<String> response = choAppSyncServiceImpl.registerCHOAPPBeneficiary(comingRequest, authorization);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertTrue(
				response.getBody().contains("Error encountered in Common-API service while registering beneficiary."));
	}

	@Test
	public void testGetBeneficiaryByVillageIDAndLastModifiedDate_Success() throws Exception {
		String responseBody = "{\"response\":{\"data\":\"[]\"}}";
		ResponseEntity<String> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);

		when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
				.thenReturn(responseEntity);

		ResponseEntity<String> response = choAppSyncServiceImpl
				.getBeneficiaryByVillageIDAndLastModifiedDate(syncSearchRequest, authorization);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().contains("[]"));
	}

	@Test
	public void testGetBeneficiaryByVillageIDAndLastModifiedDate_MissingVillageID() {
		syncSearchRequest.setVillageID(null);

		ResponseEntity<String> response = choAppSyncServiceImpl
				.getBeneficiaryByVillageIDAndLastModifiedDate(syncSearchRequest, authorization);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains("Incomplete request body"));
	}

	@Test
	public void testGetBeneficiaryByVillageIDAndLastModifiedDate_MissingLastSyncDate() {
		syncSearchRequest.setLastSyncDate(null);

		ResponseEntity<String> response = choAppSyncServiceImpl
				.getBeneficiaryByVillageIDAndLastModifiedDate(syncSearchRequest, authorization);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains("Incomplete request body"));
	}

	@Test
	public void testGetBeneficiaryByVillageIDAndLastModifiedDate_ResourceAccessException() {
		when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
				.thenThrow(new ResourceAccessException("Connection error"));

		ResponseEntity<String> response = choAppSyncServiceImpl
				.getBeneficiaryByVillageIDAndLastModifiedDate(syncSearchRequest, authorization);

		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
		assertTrue(response.getBody().contains("Error establishing connection"));
	}

	@Test
	public void testGetBeneficiaryByVillageIDAndLastModifiedDate_RestClientResponseException() {
		when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
				.thenThrow(new RestClientResponseException("Error", 500, "Internal Server Error", null, null, null));

		ResponseEntity<String> response = choAppSyncServiceImpl
				.getBeneficiaryByVillageIDAndLastModifiedDate(syncSearchRequest, authorization);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertTrue(response.getBody().contains("Error encountered in Identity service"));
	}

	@Test
	public void testGetBeneficiaryByVillageIDAndLastModifiedDate_JSONException() {
		String responseBody = "{\"response\":{\"data\":\"invalid_json\"}}";
		ResponseEntity<String> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);

		when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
				.thenReturn(responseEntity);

		ResponseEntity<String> response = choAppSyncServiceImpl
				.getBeneficiaryByVillageIDAndLastModifiedDate(syncSearchRequest, authorization);

		assertEquals(HttpStatus.BAD_GATEWAY, response.getStatusCode());
		assertTrue(response.getBody().contains("Encountered JSON exception"));
	}

	@Test
	public void testGetBeneficiaryByVillageIDAndLastModifiedDate_GeneralException() {
		when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
				.thenThrow(new RuntimeException("General error"));

		ResponseEntity<String> response = choAppSyncServiceImpl
				.getBeneficiaryByVillageIDAndLastModifiedDate(syncSearchRequest, authorization);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertTrue(response.getBody().contains("Error searching beneficiaries to sync"));
	}

	@Test
	public void testCountBeneficiaryByVillageIDAndLastModifiedDate_Success() {
		String jsonResponse = "{\"response\":{\"data\":\"10\"}}";
		ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

		when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
				.thenReturn(responseEntity);

		ResponseEntity<String> response = choAppSyncServiceImpl
				.countBeneficiaryByVillageIDAndLastModifiedDate(validRequest, "Bearer token");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().contains("10"));
	}

	@Test
	public void testCountBeneficiaryByVillageIDAndLastModifiedDate_BadRequest() {
		ResponseEntity<String> response = choAppSyncServiceImpl
				.countBeneficiaryByVillageIDAndLastModifiedDate(invalidRequest, "Bearer token");

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains("Bad request"));
	}

	@Test
	public void testCountBeneficiaryByVillageIDAndLastModifiedDate_ResourceAccessException() {
		when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
				.thenThrow(new ResourceAccessException("Connection error"));

		ResponseEntity<String> response = choAppSyncServiceImpl
				.countBeneficiaryByVillageIDAndLastModifiedDate(validRequest, "Bearer token");

		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
		assertTrue(response.getBody().contains("Error establishing connection"));
	}

	@Test
	public void testCountBeneficiaryByVillageIDAndLastModifiedDate_RestClientResponseException() {
		when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
				.thenThrow(new RestClientResponseException("Service error", 500, "Internal Server Error", null, null,
						null));

		ResponseEntity<String> response = choAppSyncServiceImpl
				.countBeneficiaryByVillageIDAndLastModifiedDate(validRequest, "Bearer token");

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertTrue(response.getBody().contains("Error encountered in Identity service"));
	}

	@Test
	public void testCountFlowRecordsByVillageIDAndLastModifiedDate_ValidInput() {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
		DateTime dt = formatter.parseDateTime(validRequest.getLastSyncDate());
		Timestamp timestamp = new Timestamp(dt.toDate().getTime());

		when(beneficiaryFlowStatusRepo.getFlowRecordsCount(validRequest.getVillageID(), timestamp)).thenReturn(10L);

		ResponseEntity<String> response = choAppSyncServiceImpl
				.countFlowRecordsByVillageIDAndLastModifiedDate(validRequest, authorization);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().contains("10"));
		verify(beneficiaryFlowStatusRepo, times(1)).getFlowRecordsCount(validRequest.getVillageID(), timestamp);
	}

	@Test
	public void testCountFlowRecordsByVillageIDAndLastModifiedDate_InvalidInput() {
		ResponseEntity<String> response = choAppSyncServiceImpl
				.countFlowRecordsByVillageIDAndLastModifiedDate(invalidRequest, authorization);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains("Bad request"));
		verify(beneficiaryFlowStatusRepo, times(0)).getFlowRecordsCount(anyString(), any(Timestamp.class));
	}

	@Test
	public void testCountFlowRecordsByVillageIDAndLastModifiedDate_ExceptionHandling() {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
		DateTime dt = formatter.parseDateTime(validRequest.getLastSyncDate());
		Timestamp timestamp = new Timestamp(dt.toDate().getTime());

		when(beneficiaryFlowStatusRepo.getFlowRecordsCount(validRequest.getVillageID(), timestamp))
				.thenThrow(new RuntimeException("Database error"));

		ResponseEntity<String> response = choAppSyncServiceImpl
				.countFlowRecordsByVillageIDAndLastModifiedDate(validRequest, authorization);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertTrue(response.getBody().contains("Error fetching ben flow status records to sync"));
		verify(beneficiaryFlowStatusRepo, times(1)).getFlowRecordsCount(validRequest.getVillageID(), timestamp);
	}

	@Test
	public void testGetFlowRecordsByVillageIDAndLastModifiedDate_ValidInput() {
		ArrayList<BeneficiaryFlowStatus> benFlowList = new ArrayList<>();
		when(beneficiaryFlowStatusRepo.getFlowRecordsToSync(anyString(), any(Timestamp.class))).thenReturn(benFlowList);

		ResponseEntity<String> response = choAppSyncServiceImpl
				.getFlowRecordsByVillageIDAndLastModifiedDate(validRequest, "Authorization");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(beneficiaryFlowStatusRepo, times(1)).getFlowRecordsToSync(anyString(), any(Timestamp.class));
	}

	@Test
	public void testGetFlowRecordsByVillageIDAndLastModifiedDate_MissingVillageIDOrLastSyncDate() {
		ResponseEntity<String> response = choAppSyncServiceImpl
				.getFlowRecordsByVillageIDAndLastModifiedDate(invalidRequest, "Authorization");

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		verify(beneficiaryFlowStatusRepo, times(0)).getFlowRecordsToSync(anyString(), any(Timestamp.class));
	}

	@Test
	public void testGetFlowRecordsByVillageIDAndLastModifiedDate_IllegalArgumentException() {
		SyncSearchRequest request = new SyncSearchRequest();
		request.setVillageID("123");
		request.setLastSyncDate("invalid-date");

		ResponseEntity<String> response = choAppSyncServiceImpl.getFlowRecordsByVillageIDAndLastModifiedDate(request,
				"Authorization");

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		verify(beneficiaryFlowStatusRepo, times(0)).getFlowRecordsToSync(anyString(), any(Timestamp.class));
	}

	@Test
	public void testGetFlowRecordsByVillageIDAndLastModifiedDate_Exception() {
		when(beneficiaryFlowStatusRepo.getFlowRecordsToSync(anyString(), any(Timestamp.class)))
				.thenThrow(new RuntimeException("Database error"));

		ResponseEntity<String> response = choAppSyncServiceImpl
				.getFlowRecordsByVillageIDAndLastModifiedDate(validRequest, "Authorization");

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		verify(beneficiaryFlowStatusRepo, times(1)).getFlowRecordsToSync(anyString(), any(Timestamp.class));
	}

	@Test
	public void testSaveUserActivityLogs_Success() {
		when(userActivityLogsRepo.saveAll(anyList())).thenReturn(logsList);

		ResponseEntity<String> response = choAppSyncServiceImpl.saveUserActivityLogs(logsList, authorization);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().contains("Data saved successfully"));
		verify(userActivityLogsRepo, times(1)).saveAll(anyList());
	}

	@Test
	public void testSaveUserActivityLogs_IEMRException() {
		doThrow(new IEMRException("Error")).when(userActivityLogsRepo).saveAll(anyList());

		ResponseEntity<String> response = choAppSyncServiceImpl.saveUserActivityLogs(logsList, authorization);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains("Encountered exception EITHER due to incorrect payload syntax OR"));
		verify(userActivityLogsRepo, times(1)).saveAll(anyList());
	}

	@Test
	public void testSaveUserActivityLogs_DataIntegrityViolationException() {
		doThrow(new DataIntegrityViolationException("Error")).when(userActivityLogsRepo).saveAll(anyList());

		ResponseEntity<String> response = choAppSyncServiceImpl.saveUserActivityLogs(logsList, authorization);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains("Encountered exception EITHER due to incorrect payload syntax OR"));
		verify(userActivityLogsRepo, times(1)).saveAll(anyList());
	}

	@Test
	public void testSaveUserActivityLogs_JsonSyntaxException() {
		doThrow(new JsonSyntaxException("Error")).when(userActivityLogsRepo).saveAll(anyList());

		ResponseEntity<String> response = choAppSyncServiceImpl.saveUserActivityLogs(logsList, authorization);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains("Encountered exception EITHER due to incorrect payload syntax OR"));
		verify(userActivityLogsRepo, times(1)).saveAll(anyList());
	}

	@Test
	public void testSaveUserActivityLogs_NumberFormatException() {
		doThrow(new NumberFormatException("Error")).when(userActivityLogsRepo).saveAll(anyList());

		ResponseEntity<String> response = choAppSyncServiceImpl.saveUserActivityLogs(logsList, authorization);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains("Encountered exception EITHER due to incorrect payload syntax OR"));
		verify(userActivityLogsRepo, times(1)).saveAll(anyList());
	}

	@Test
	public void testSaveUserActivityLogs_GenericException() {
		doThrow(new RuntimeException("Error")).when(userActivityLogsRepo).saveAll(anyList());

		ResponseEntity<String> response = choAppSyncServiceImpl.saveUserActivityLogs(logsList, authorization);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertTrue(response.getBody().contains("Encountered exception while saving UserActivityLogs"));
		verify(userActivityLogsRepo, times(1)).saveAll(anyList());
	}

	@Test
	public void testGetBeneficiaryNurseFormDataGeneralOPDSuccess() throws Exception {
		// Mocking the dependencies
		when(commonNurseServiceImpl.getCSVisitDetails(anyLong(), anyLong())).thenReturn(new Object());
		when(benChiefComplaintRepo.getBenChiefComplaints(anyLong(), anyLong())).thenReturn(new ArrayList<>());
		when(benAnthropometryRepo.getBenAnthropometryDetail(anyLong(), anyLong())).thenReturn(new Object());
		when(benPhysicalVitalRepo.getBenPhysicalVitalDetail(anyLong(), anyLong())).thenReturn(new Object());

		ResponseEntity<String> response = choAppSyncServiceImpl.getBeneficiaryNurseFormDataGeneralOPD(validRequest,
				authorization);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().contains("GOPDNurseVisitDetail"));
		assertTrue(response.getBody().contains("BenChiefComplaints"));
		assertTrue(response.getBody().contains("benAnthropometryDetail"));
		assertTrue(response.getBody().contains("benPhysicalVitalDetail"));
	}

	@Test
	public void testGetBeneficiaryNurseFormDataGeneralOPDInvalidRequest() throws Exception {
		ResponseEntity<String> response = choAppSyncServiceImpl.getBeneficiaryNurseFormDataGeneralOPD(invalidRequest,
				authorization);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains(
				"Encountered exception EITHER due to incorrect payload syntax OR because of missing benRegID/visitCode"));
	}

	@Test
	public void testGetBeneficiaryNurseFormDataGeneralOPDNoDataFound() throws Exception {
		// Mocking the dependencies to return null
		when(commonNurseServiceImpl.getCSVisitDetails(anyLong(), anyLong())).thenReturn(null);
		when(benChiefComplaintRepo.getBenChiefComplaints(anyLong(), anyLong())).thenReturn(new ArrayList<>());
		when(benAnthropometryRepo.getBenAnthropometryDetail(anyLong(), anyLong())).thenReturn(null);
		when(benPhysicalVitalRepo.getBenPhysicalVitalDetail(anyLong(), anyLong())).thenReturn(null);

		ResponseEntity<String> response = choAppSyncServiceImpl.getBeneficiaryNurseFormDataGeneralOPD(validRequest,
				authorization);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().contains("GOPDNurseVisitDetail"));
		assertTrue(response.getBody().contains("BenChiefComplaints"));
		assertTrue(response.getBody().contains("benAnthropometryDetail"));
		assertTrue(response.getBody().contains("benPhysicalVitalDetail"));
	}

	@Test
	public void testGetBeneficiaryNurseFormDataGeneralOPDExceptionHandling() throws Exception {
		// Mocking the dependencies to throw an exception
		when(commonNurseServiceImpl.getCSVisitDetails(anyLong(), anyLong()))
				.thenThrow(new RuntimeException("Test Exception"));

		ResponseEntity<String> response = choAppSyncServiceImpl.getBeneficiaryNurseFormDataGeneralOPD(validRequest,
				authorization);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertTrue(response.getBody().contains("Encountered exception while fetching nurse data"));
	}

	@Test
	public void testSaveBeneficiaryNurseFormDataGeneralOPD_Success() throws Exception {
		JsonObject responseObject = new JsonObject();
		responseObject.addProperty("visitCode", 12345L);
		when(generalOPDServiceImpl.saveNurseData(any(JsonObject.class), eq(authorization)))
				.thenReturn(responseObject.toString());

		BeneficiaryVisitDetail visitDetail = new BeneficiaryVisitDetail();
		visitDetail.setBenVisitID(1L);
		when(benVisitDetailRepo.findByVisitCode(12345L)).thenReturn(visitDetail);

		ResponseEntity<String> response = choAppSyncServiceImpl.saveBeneficiaryNurseFormDataGeneralOPD(requestObj,
				authorization);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().contains("visitID"));
		verify(generalOPDServiceImpl, times(1)).saveNurseData(any(JsonObject.class), eq(authorization));
		verify(benVisitDetailRepo, times(1)).findByVisitCode(12345L);
	}

	@Test
	public void testSaveBeneficiaryNurseFormDataGeneralOPD_InvalidRequestObject() {
		String invalidRequestObj = "invalid json";

		ResponseEntity<String> response = choAppSyncServiceImpl
				.saveBeneficiaryNurseFormDataGeneralOPD(invalidRequestObj, authorization);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains("Bad Request. Invalid request payload"));
	}

	@Test
	public void testSaveBeneficiaryNurseFormDataGeneralOPD_Exception() throws Exception {
		when(generalOPDServiceImpl.saveNurseData(any(JsonObject.class), eq(authorization)))
				.thenThrow(new RuntimeException("Exception"));

		ResponseEntity<String> response = choAppSyncServiceImpl.saveBeneficiaryNurseFormDataGeneralOPD(requestObj,
				authorization);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertTrue(response.getBody().contains("Unable to save data"));
		verify(generalOPDServiceImpl, times(1)).saveNurseData(any(JsonObject.class), eq(authorization));
	}

	@Test
	public void testSavePrescriptionTemplatesToServer_Success() {
		List<PrescriptionTemplates> templateList = Arrays.asList(new PrescriptionTemplates(),
				new PrescriptionTemplates());
		when(prescriptionTemplatesRepo.saveAll(templateList)).thenReturn(templateList);

		ResponseEntity<String> response = choAppSyncServiceImpl.savePrescriptionTemplatesToServer(templateList,
				"Bearer token");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().contains("Data saved successfully"));
		verify(prescriptionTemplatesRepo, times(1)).saveAll(templateList);
	}

	@Test
	public void testSavePrescriptionTemplatesToServer_Exception() {
		List<PrescriptionTemplates> templateList = Arrays.asList(new PrescriptionTemplates(),
				new PrescriptionTemplates());
		when(prescriptionTemplatesRepo.saveAll(templateList)).thenThrow(new RuntimeException("Database error"));

		ResponseEntity<String> response = choAppSyncServiceImpl.savePrescriptionTemplatesToServer(templateList,
				"Bearer token");

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertTrue(response.getBody().contains("Encountered exception while saving Prescription templates"));
		verify(prescriptionTemplatesRepo, times(1)).saveAll(templateList);
	}

	@Test
	public void testSavePrescriptionTemplatesToApp_Success() {
		Integer userID = 1;
		List<PrescriptionTemplates> templateList = new ArrayList<>();
		PrescriptionTemplates template = new PrescriptionTemplates();
		templateList.add(template);

		when(prescriptionTemplatesRepo.getPrescriptionTemplatesByUserID(userID)).thenReturn(templateList);

		ResponseEntity<String> response = choAppSyncServiceImpl.savePrescriptionTemplatesToApp(userID, "Bearer token");

		String expectedResponse = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create()
				.toJson(templateList);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedResponse, response.getBody());

		verify(prescriptionTemplatesRepo, times(1)).getPrescriptionTemplatesByUserID(userID);
	}

	@Test
	public void testSavePrescriptionTemplatesToApp_Exception() {
		Integer userID = 1;

		when(prescriptionTemplatesRepo.getPrescriptionTemplatesByUserID(userID))
				.thenThrow(new RuntimeException("Database error"));

		ResponseEntity<String> response = choAppSyncServiceImpl.savePrescriptionTemplatesToApp(userID, "Bearer token");

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals(
				"{\"error\":{\"code\":500,\"message\":\"Encountered exception while saving Prescription templates. java.lang.RuntimeException: Database error\"}}",
				response.getBody());

		verify(prescriptionTemplatesRepo, times(1)).getPrescriptionTemplatesByUserID(userID);
	}

	@Test
	public void testDeletePrescriptionTemplates_Success() {
		Integer userID = 1;
		Integer tempID = 1;

		// Act
		ResponseEntity<String> response = choAppSyncServiceImpl.deletePrescriptionTemplates(userID, tempID);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().contains("Successfully deleted"));
		verify(prescriptionTemplatesRepo, times(1)).deletePrescriptionTemplatesByUserIDAndTempID(userID, tempID);
	}

	@Test
	public void testDeletePrescriptionTemplates_Exception() {
		Integer userID = 1;
		Integer tempID = 1;

		// Arrange
		doThrow(new RuntimeException("Database error")).when(prescriptionTemplatesRepo)
				.deletePrescriptionTemplatesByUserIDAndTempID(userID, tempID);

		// Act
		ResponseEntity<String> response = choAppSyncServiceImpl.deletePrescriptionTemplates(userID, tempID);

		// Assert
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertTrue(response.getBody().contains("Encountered exception while deleting Prescription templates"));
		verify(prescriptionTemplatesRepo, times(1)).deletePrescriptionTemplatesByUserIDAndTempID(userID, tempID);
	}

	@Test
	public void testCreateNewOutreachActivity_Success() {
		ResponseEntity<String> response = choAppSyncServiceImpl.createNewOutreachActivity(activity, authorization);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().contains("Data saved successfully"));
		verify(outreachActivityRepo, times(1)).save(activity);
	}

	@Test
	public void testCreateNewOutreachActivity_DataIntegrityViolationException() {
		doThrow(new DataIntegrityViolationException("Data integrity violation")).when(outreachActivityRepo)
				.save(any(OutreachActivity.class));

		ResponseEntity<String> response = choAppSyncServiceImpl.createNewOutreachActivity(activity, authorization);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains("Encountered exception EITHER due to incorrect payload syntax OR"));
		verify(outreachActivityRepo, times(1)).save(activity);
	}

	@Test
	public void testCreateNewOutreachActivity_JsonSyntaxException() {
		doThrow(new com.google.gson.JsonSyntaxException("JSON syntax error")).when(outreachActivityRepo)
				.save(any(OutreachActivity.class));

		ResponseEntity<String> response = choAppSyncServiceImpl.createNewOutreachActivity(activity, authorization);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains("Encountered exception EITHER due to incorrect payload syntax OR"));
		verify(outreachActivityRepo, times(1)).save(activity);
	}

	@Test
	public void testCreateNewOutreachActivity_NumberFormatException() {
		doThrow(new NumberFormatException("Number format exception")).when(outreachActivityRepo)
				.save(any(OutreachActivity.class));

		ResponseEntity<String> response = choAppSyncServiceImpl.createNewOutreachActivity(activity, authorization);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains("Encountered exception EITHER due to incorrect payload syntax OR"));
		verify(outreachActivityRepo, times(1)).save(activity);
	}

	@Test
	public void testCreateNewOutreachActivity_GenericException() {
		doThrow(new RuntimeException("Generic exception")).when(outreachActivityRepo).save(any(OutreachActivity.class));

		ResponseEntity<String> response = choAppSyncServiceImpl.createNewOutreachActivity(activity, authorization);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertTrue(response.getBody().contains("Encountered exception while saving outreach activity"));
		verify(outreachActivityRepo, times(1)).save(activity);
	}

	@Test
	public void testGetActivitiesByUser_Success() {
		Integer userId = 1;
		String authorization = "Bearer token";

		ArrayList<Object[]> activitiesObj = new ArrayList<>();
		activitiesObj.add(new Object[] { 1, "Activity 1" });
		activitiesObj.add(new Object[] { 2, "Activity 2" });

		when(outreachActivityRepo.getActivitiesByUserID(userId)).thenReturn(activitiesObj);

		ResponseEntity<String> response = choAppSyncServiceImpl.getActivitiesByUser(userId, authorization);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().contains("Activity 1"));
		assertTrue(response.getBody().contains("Activity 2"));
	}

	@Test
	public void testGetActivitiesByUser_NoActivities() {
		Integer userId = 1;
		String authorization = "Bearer token";

		ArrayList<Object[]> activitiesObj = new ArrayList<>();

		when(outreachActivityRepo.getActivitiesByUserID(userId)).thenReturn(activitiesObj);

		ResponseEntity<String> response = choAppSyncServiceImpl.getActivitiesByUser(userId, authorization);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().contains("[]"));
	}

	@Test
	public void testGetActivitiesByUser_Exception() {
		Integer userId = 1;
		String authorization = "Bearer token";

		when(outreachActivityRepo.getActivitiesByUserID(userId)).thenThrow(new RuntimeException("Database error"));

		ResponseEntity<String> response = choAppSyncServiceImpl.getActivitiesByUser(userId, authorization);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertTrue(response.getBody().contains("Error fetching activities"));
	}

	@Test
	public void testGetActivityById_ValidActivityWithImages() {
		OutreachActivity activity = new OutreachActivity();
		activity.setImg1Data("image1".getBytes());
		activity.setImg2Data("image2".getBytes());
		when(outreachActivityRepo.findById(1)).thenReturn(Optional.of(activity));

		ResponseEntity<String> response = choAppSyncServiceImpl.getActivityById(1, "Bearer token");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().contains(Base64.getEncoder().encodeToString("image1".getBytes())));
		assertTrue(response.getBody().contains(Base64.getEncoder().encodeToString("image2".getBytes())));
	}

	@Test
	public void testGetActivityById_ValidActivityWithoutImages() {
		OutreachActivity activity = new OutreachActivity();
		when(outreachActivityRepo.findById(1)).thenReturn(Optional.of(activity));

		ResponseEntity<String> response = choAppSyncServiceImpl.getActivityById(1, "Bearer token");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertFalse(response.getBody().contains("img1"));
		assertFalse(response.getBody().contains("img2"));
	}

	@Test
	public void testGetActivityById_ActivityNotFound() {
		when(outreachActivityRepo.findById(1)).thenReturn(Optional.empty());

		ResponseEntity<String> response = choAppSyncServiceImpl.getActivityById(1, "Bearer token");

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testGetActivityById_Exception() {
		when(outreachActivityRepo.findById(1)).thenThrow(new RuntimeException("Database error"));

		ResponseEntity<String> response = choAppSyncServiceImpl.getActivityById(1, "Bearer token");

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
}
