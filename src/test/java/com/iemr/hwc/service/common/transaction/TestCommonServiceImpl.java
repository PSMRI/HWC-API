package com.iemr.hwc.service.common.transaction;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iemr.hwc.data.BeneficiaryFlowStatus;
import com.iemr.hwc.data.neonatal.ImmunizationServices;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.repo.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.BeneficiaryVisitDetailRepo;
import com.iemr.hwc.repo.neonatal.ImmunizationServicesRepo;
import com.iemr.hwc.repo.provider.ProviderServiceMappingRepo;
import com.iemr.hwc.service.tele_consultation.TeleConsultationServiceImpl;
import com.iemr.hwc.utils.exception.IEMRException;

import io.swagger.v3.oas.models.PathItem.HttpMethod;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

public class TestCommonServiceImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;

	@Mock
	private BeneficiaryVisitDetailRepo benVisitDetailRepo;

	@Mock
	private ProviderServiceMappingRepo providerServiceMappingRepo;
	@Mock
	private CommonDoctorServiceImpl commonDoctorServiceImpl;

	@Mock
	private TeleConsultationServiceImpl teleConsultationServiceImpl;
	@Mock
	private RestTemplate restTemplate;

	@Mock
	private ImmunizationServicesRepo immunizationServicesRepo;

	@InjectMocks
	private CommonServiceImpl commonServiceImpl;

	private BeneficiaryFlowStatus benFlowOBJ;

	private CommonNurseServiceImpl commonNurseServiceImpl;
	private Long beneficiaryRegID;
	private String sampleRequest;
	private List<ImmunizationServices> immunizationServicesList;
	private CommonUtilityClass commonUtilityClass;
	private String comingRequest;
	private JsonObject requestOBJ;

	private String authorization;
	private String openkmDocUrl;

	@BeforeEach
	public void setUp() {
		benFlowOBJ = new BeneficiaryFlowStatus();
		benFlowOBJ.setVisitCategory("ANC");
		benFlowOBJ.setBeneficiaryRegID(1L);
		benFlowOBJ.setBenVisitCode(1L);
		benFlowOBJ.setBenFlowID(1L);
	}

	@Test
	public void testGetCaseSheetPrintDataForBeneficiary_ANC() throws Exception {
		when(beneficiaryFlowStatusRepo.getBenDetailsForLeftSidePanel(anyLong())).thenReturn(benFlowOBJ);
		when(benVisitDetailRepo.getSubVisitCategory(anyLong(), anyLong())).thenReturn(null);

		String result = commonNurseServiceImpl.getCaseSheetPrintDataForBeneficiary(benFlowOBJ, "Authorization");

		assertNotNull(result);
		assertTrue(result.contains("nurseData"));
		assertTrue(result.contains("doctorData"));
		assertTrue(result.contains("BeneficiaryData"));
		assertTrue(result.contains("serviceID"));
	}

	@Test
	public void testGetCaseSheetPrintDataForBeneficiary_InvalidVisitCategory() throws Exception {
		benFlowOBJ.setVisitCategory("Invalid");

		String result = commonNurseServiceImpl.getCaseSheetPrintDataForBeneficiary(benFlowOBJ, "Authorization");

		assertEquals("Invalid VisitCategory", result);
	}

	@Test
	public void testGetCaseSheetPrintDataForBeneficiary_NullVisitCategory() throws Exception {
		benFlowOBJ.setVisitCategory(null);

		String result = commonNurseServiceImpl.getCaseSheetPrintDataForBeneficiary(benFlowOBJ, "Authorization");

		assertNull(result);
	}

	@Test
	public void testGetCaseSheetPrintDataForBeneficiary_GeneralOPD() throws Exception {
		benFlowOBJ.setVisitCategory("General OPD");

		when(beneficiaryFlowStatusRepo.getBenDetailsForLeftSidePanel(anyLong())).thenReturn(benFlowOBJ);
		when(benVisitDetailRepo.getSubVisitCategory(anyLong(), anyLong())).thenReturn(null);

		String result = commonNurseServiceImpl.getCaseSheetPrintDataForBeneficiary(benFlowOBJ, "Authorization");

		assertNotNull(result);
		assertTrue(result.contains("nurseData"));
		assertTrue(result.contains("doctorData"));
		assertTrue(result.contains("BeneficiaryData"));
		assertTrue(result.contains("serviceID"));
	}

	@Test
	public void testGetBenPastHistoryData() {
		Long beneficiaryRegID = 1L;
		String expectedHistoryData = "Past Medical History Data";

		when(commonNurseServiceImpl.fetchBenPastMedicalHistory(beneficiaryRegID)).thenReturn(expectedHistoryData);

		String actualHistoryData = commonServiceImpl.getBenPastHistoryData(beneficiaryRegID);

		assertEquals(expectedHistoryData, actualHistoryData);
		verify(commonNurseServiceImpl, times(1)).fetchBenPastMedicalHistory(beneficiaryRegID);
	}

	@Test
	public void testGetComorbidHistoryData() {
		String expectedResponse = "Comorbid History Data";
		when(commonNurseServiceImpl.fetchBenComorbidityHistory(beneficiaryRegID)).thenReturn(expectedResponse);

		String actualResponse = commonServiceImpl.getComorbidHistoryData(beneficiaryRegID);

		assertEquals(expectedResponse, actualResponse);
		verify(commonNurseServiceImpl, times(1)).fetchBenComorbidityHistory(beneficiaryRegID);
	}

	@Test
	public void testGetComorbidHistoryData_NoData() {
		when(commonNurseServiceImpl.fetchBenComorbidityHistory(beneficiaryRegID)).thenReturn(null);

		String actualResponse = commonServiceImpl.getComorbidHistoryData(beneficiaryRegID);

		assertNull(actualResponse);
		verify(commonNurseServiceImpl, times(1)).fetchBenComorbidityHistory(beneficiaryRegID);
	}

	@Test
	public void testGetComorbidHistoryData_Exception() {
		when(commonNurseServiceImpl.fetchBenComorbidityHistory(beneficiaryRegID))
				.thenThrow(new RuntimeException("Exception occurred"));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			commonServiceImpl.getComorbidHistoryData(beneficiaryRegID);
		});

		assertEquals("Exception occurred", exception.getMessage());
		verify(commonNurseServiceImpl, times(1)).fetchBenComorbidityHistory(beneficiaryRegID);
	}

	@Test
	public void testGetMedicationHistoryData() {
		Long beneficiaryRegID = 1L;
		String expectedHistoryData = "Medication History Data";

		when(commonNurseServiceImpl.fetchBenPersonalMedicationHistory(beneficiaryRegID))
				.thenReturn(expectedHistoryData);

		String actualHistoryData = commonServiceImpl.getMedicationHistoryData(beneficiaryRegID);

		assertEquals(expectedHistoryData, actualHistoryData);
	}

	@Test
	public void testGetPersonalTobaccoHistoryData() {
		String expectedResponse = "Tobacco History Data";
		when(commonNurseServiceImpl.fetchBenPersonalTobaccoHistory(beneficiaryRegID)).thenReturn(expectedResponse);

		String actualResponse = commonServiceImpl.getPersonalTobaccoHistoryData(beneficiaryRegID);

		assertEquals(expectedResponse, actualResponse);
		verify(commonNurseServiceImpl, times(1)).fetchBenPersonalTobaccoHistory(beneficiaryRegID);
	}

	@Test
	public void testGetPersonalAlcoholHistoryData() {
		Long beneficiaryRegID = 1L;
		String expectedHistoryData = "Alcohol history data";

		when(commonNurseServiceImpl.fetchBenPersonalAlcoholHistory(beneficiaryRegID)).thenReturn(expectedHistoryData);

		String actualHistoryData = commonServiceImpl.getPersonalAlcoholHistoryData(beneficiaryRegID);

		assertEquals(expectedHistoryData, actualHistoryData);
		verify(commonNurseServiceImpl, times(1)).fetchBenPersonalAlcoholHistory(beneficiaryRegID);
	}

	@Test
	public void testGetPersonalAllergyHistoryData() {
		Long beneficiaryRegID = 1L;
		String expectedResponse = "AllergyHistoryData";

		when(commonNurseServiceImpl.fetchBenPersonalAllergyHistory(beneficiaryRegID)).thenReturn(expectedResponse);

		String actualResponse = commonServiceImpl.getPersonalAllergyHistoryData(beneficiaryRegID);

		assertEquals(expectedResponse, actualResponse);
		verify(commonNurseServiceImpl, times(1)).fetchBenPersonalAllergyHistory(beneficiaryRegID);
	}

	@Test
	public void testGetFamilyHistoryData() {
		String expectedResponse = "familyHistoryData";
		when(commonNurseServiceImpl.fetchBenPersonalFamilyHistory(beneficiaryRegID)).thenReturn(expectedResponse);

		String actualResponse = commonServiceImpl.getFamilyHistoryData(beneficiaryRegID);

		assertEquals(expectedResponse, actualResponse);
		verify(commonNurseServiceImpl, times(1)).fetchBenPersonalFamilyHistory(beneficiaryRegID);
	}

	@Test
	public void testGetProviderSpecificData() throws IEMRException {
		String expectedResponse = "{\"response\":\"data\"}";
		when(commonNurseServiceImpl.fetchProviderSpecificdata(sampleRequest)).thenReturn(expectedResponse);

		String actualResponse = commonServiceImpl.getProviderSpecificData(sampleRequest);

		assertEquals(expectedResponse, actualResponse);
		verify(commonNurseServiceImpl, times(1)).fetchProviderSpecificdata(sampleRequest);
	}

	@Test
	public void testGetProviderSpecificData_Exception() throws IEMRException {
		when(commonNurseServiceImpl.fetchProviderSpecificdata(sampleRequest)).thenThrow(new IEMRException("Error"));

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			commonServiceImpl.getProviderSpecificData(sampleRequest);
		});

		assertEquals("Error", exception.getMessage());
		verify(commonNurseServiceImpl, times(1)).fetchProviderSpecificdata(sampleRequest);
	}

	@Test
	public void testGetBenPhysicalHistory() {
		String expectedResponse = "Physical History Data";
		when(commonNurseServiceImpl.fetchBenPhysicalHistory(beneficiaryRegID)).thenReturn(expectedResponse);

		String actualResponse = commonServiceImpl.getBenPhysicalHistory(beneficiaryRegID);

		assertEquals(expectedResponse, actualResponse);
		verify(commonNurseServiceImpl, times(1)).fetchBenPhysicalHistory(beneficiaryRegID);
	}

	@Test
	public void testGetMenstrualHistoryData() throws Exception {
		Long beneficiaryRegID = 1L;
		String expectedResponse = "Menstrual History Data";

		when(commonNurseServiceImpl.fetchBenMenstrualHistory(beneficiaryRegID)).thenReturn(expectedResponse);

		String actualResponse = commonServiceImpl.getMenstrualHistoryData(beneficiaryRegID);

		assertEquals(expectedResponse, actualResponse);
		verify(commonNurseServiceImpl, times(1)).fetchBenMenstrualHistory(beneficiaryRegID);
	}

	@Test
	public void testGetObstetricHistoryData_Success() throws IEMRException {
		String expectedResponse = "Obstetric History Data";
		when(commonNurseServiceImpl.fetchBenPastObstetricHistory(beneficiaryRegID)).thenReturn(expectedResponse);

		String actualResponse = commonServiceImpl.getObstetricHistoryData(beneficiaryRegID);

		assertEquals(expectedResponse, actualResponse);
		verify(commonNurseServiceImpl, times(1)).fetchBenPastObstetricHistory(beneficiaryRegID);
	}

	@Test
	public void testGetObstetricHistoryData_Exception() throws IEMRException {
		when(commonNurseServiceImpl.fetchBenPastObstetricHistory(beneficiaryRegID))
				.thenThrow(new IEMRException("Error"));

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			commonServiceImpl.getObstetricHistoryData(beneficiaryRegID);
		});

		assertEquals("Error", exception.getMessage());
		verify(commonNurseServiceImpl, times(1)).fetchBenPastObstetricHistory(beneficiaryRegID);
	}

	@Test
	public void testGetImmunizationHistoryData() throws Exception {
		Long beneficiaryRegID = 1L;
		String expectedResponse = "Immunization History Data";

		when(commonNurseServiceImpl.fetchBenImmunizationHistory(beneficiaryRegID)).thenReturn(expectedResponse);

		String actualResponse = commonServiceImpl.getImmunizationHistoryData(beneficiaryRegID);

		assertEquals(expectedResponse, actualResponse);
		verify(commonNurseServiceImpl, times(1)).fetchBenImmunizationHistory(beneficiaryRegID);
	}

	@Test
	public void testGetChildVaccineHistoryData() throws Exception {
		when(immunizationServicesRepo.findByBeneficiaryRegIDAndDeleted(beneficiaryRegID, false))
				.thenReturn(immunizationServicesList);

		String result = commonServiceImpl.getChildVaccineHistoryData(beneficiaryRegID);

		assertNotNull(result);
		assertTrue(result.contains("\"beneficiaryRegID\":1"));
		verify(immunizationServicesRepo, times(1)).findByBeneficiaryRegIDAndDeleted(beneficiaryRegID, false);
	}

	@Test
	public void testGetBenPerinatalHistoryData() {
		String expectedResponse = "Perinatal History Data";
		when(commonNurseServiceImpl.fetchBenPerinatalHistory(beneficiaryRegID)).thenReturn(expectedResponse);

		String actualResponse = commonServiceImpl.getBenPerinatalHistoryData(beneficiaryRegID);

		assertEquals(expectedResponse, actualResponse);
		verify(commonNurseServiceImpl, times(1)).fetchBenPerinatalHistory(beneficiaryRegID);
	}

	@Test
	public void testGetBenPerinatalHistoryData_NoData() {
		when(commonNurseServiceImpl.fetchBenPerinatalHistory(beneficiaryRegID)).thenReturn(null);

		String actualResponse = commonServiceImpl.getBenPerinatalHistoryData(beneficiaryRegID);

		assertNull(actualResponse);
		verify(commonNurseServiceImpl, times(1)).fetchBenPerinatalHistory(beneficiaryRegID);
	}

	@Test
	public void testGetBenPerinatalHistoryData_Exception() {
		when(commonNurseServiceImpl.fetchBenPerinatalHistory(beneficiaryRegID))
				.thenThrow(new RuntimeException("Exception occurred"));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			commonServiceImpl.getBenPerinatalHistoryData(beneficiaryRegID);
		});

		assertEquals("Exception occurred", exception.getMessage());
		verify(commonNurseServiceImpl, times(1)).fetchBenPerinatalHistory(beneficiaryRegID);
	}

	@Test
	public void testGetBenFeedingHistoryData() {
		String expectedResponse = "FeedingHistoryData";
		when(commonNurseServiceImpl.fetchBenFeedingHistory(beneficiaryRegID)).thenReturn(expectedResponse);

		String actualResponse = commonServiceImpl.getBenFeedingHistoryData(beneficiaryRegID);

		assertEquals(expectedResponse, actualResponse);
		verify(commonNurseServiceImpl, times(1)).fetchBenFeedingHistory(beneficiaryRegID);
	}

	@Test
	public void testGetBenDevelopmentHistoryData() throws Exception {
		String expectedResponse = "DevelopmentHistoryData";
		when(commonNurseServiceImpl.fetchBenDevelopmentHistory(beneficiaryRegID)).thenReturn(expectedResponse);

		String actualResponse = commonServiceImpl.getBenDevelopmentHistoryData(beneficiaryRegID);

		assertEquals(expectedResponse, actualResponse);
		verify(commonNurseServiceImpl, times(1)).fetchBenDevelopmentHistory(beneficiaryRegID);
	}

	@Test
	public void testGetBenPreviousVisitDataForCaseRecord() throws IEMRException {
		List<Integer> psmIDList = new ArrayList<>();
		psmIDList.add(1);
		psmIDList.add(0);

		List<Object[]> resultList = new ArrayList<>();
		Object[] result = new Object[] { 1L, "testData" };
		resultList.add(result);

		when(providerServiceMappingRepo.getProviderServiceMapIdForServiceID((short) 4)).thenReturn(psmIDList);
		when(beneficiaryFlowStatusRepo.getBenPreviousHistory(any(Long.class), any(List.class))).thenReturn(resultList);

		String response = commonServiceImpl.getBenPreviousVisitDataForCaseRecord(comingRequest);

		assertNotNull(response);
		assertTrue(response.contains("testData"));
	}

	@Test
	public void testGetBenPreviousVisitDataForCaseRecord_EmptyResult() throws IEMRException {
		List<Integer> psmIDList = new ArrayList<>();
		psmIDList.add(1);
		psmIDList.add(0);

		List<Object[]> resultList = new ArrayList<>();

		when(providerServiceMappingRepo.getProviderServiceMapIdForServiceID((short) 4)).thenReturn(psmIDList);
		when(beneficiaryFlowStatusRepo.getBenPreviousHistory(any(Long.class), any(List.class))).thenReturn(resultList);

		String response = commonServiceImpl.getBenPreviousVisitDataForCaseRecord(comingRequest);

		assertNotNull(response);
		assertTrue(response.isEmpty());
	}

	@Test
	public void testGetBenPreviousVisitDataForCaseRecord_Exception() throws IEMRException {
		when(providerServiceMappingRepo.getProviderServiceMapIdForServiceID((short) 4))
				.thenThrow(new RuntimeException("Exception"));

		Exception exception = assertThrows(IEMRException.class, () -> {
			commonServiceImpl.getBenPreviousVisitDataForCaseRecord(comingRequest);
		});

		assertEquals("Exception", exception.getMessage());
	}

	@Test
	public void testCreateTcRequest_Success() throws Exception {
		TCRequestModel tRequestModel = new TCRequestModel();
		tRequestModel.setBeneficiaryRegID(1L);
		tRequestModel.setProviderServiceMapID(1);
		tRequestModel.setVisitCode(1L);
		tRequestModel.setBenVisitID(1L);
		tRequestModel.setUserID(1L);
		tRequestModel.setSpecializationID(1L);
		tRequestModel.setRequestDate(Utility.combineDateAndTimeToDateTime("2023-10-10", "10:00"));
		tRequestModel.setDuration_minute(60);
		tRequestModel.setVanID(1L);

		TcSpecialistSlotBookingRequestOBJ tcSpecialistSlotBookingRequestOBJ = new TcSpecialistSlotBookingRequestOBJ();
		tcSpecialistSlotBookingRequestOBJ.setUserID(1L);
		tcSpecialistSlotBookingRequestOBJ.setDate(Utility.combineDateAndTimeToDateTime("2023-10-10", "10:00"));
		tcSpecialistSlotBookingRequestOBJ.setFromTime("10:00");
		tcSpecialistSlotBookingRequestOBJ.setToTime("11:00");
		tcSpecialistSlotBookingRequestOBJ.setCreatedBy(1L);
		tcSpecialistSlotBookingRequestOBJ.setModifiedBy(1L);

		when(commonDoctorServiceImpl.callTmForSpecialistSlotBook(any(TcSpecialistSlotBookingRequestOBJ.class),
				eq(authorization))).thenReturn(1);
		when(teleConsultationServiceImpl.createTCRequest(any(TCRequestModel.class))).thenReturn(1L);

		TeleconsultationRequestOBJ result = commonServiceImpl.createTcRequest(requestOBJ, commonUtilityClass,
				authorization);

		assertNotNull(result);
		assertEquals(1L, result.getTmRequestID());
		verify(commonDoctorServiceImpl, times(1))
				.callTmForSpecialistSlotBook(any(TcSpecialistSlotBookingRequestOBJ.class), eq(authorization));
		verify(teleConsultationServiceImpl, times(1)).createTCRequest(any(TCRequestModel.class));
	}

	@Test
	public void testCreateTcRequest_AlreadyBookedSlot() throws Exception {
		when(commonDoctorServiceImpl.callTmForSpecialistSlotBook(any(TcSpecialistSlotBookingRequestOBJ.class),
				eq(authorization))).thenReturn(0);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			commonServiceImpl.createTcRequest(requestOBJ, commonUtilityClass, authorization);
		});

		assertEquals("Already Booked Slot, Please choose another slot", exception.getMessage());
		verify(commonDoctorServiceImpl, times(1))
				.callTmForSpecialistSlotBook(any(TcSpecialistSlotBookingRequestOBJ.class), eq(authorization));
		verify(teleConsultationServiceImpl, times(0)).createTCRequest(any(TCRequestModel.class));
	}

	@Test
	public void testCreateTcRequest_ErrorCreatingRequest() throws Exception {
		when(commonDoctorServiceImpl.callTmForSpecialistSlotBook(any(TcSpecialistSlotBookingRequestOBJ.class),
				eq(authorization))).thenReturn(1);
		when(teleConsultationServiceImpl.createTCRequest(any(TCRequestModel.class))).thenReturn(null);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			commonServiceImpl.createTcRequest(requestOBJ, commonUtilityClass, authorization);
		});

		assertEquals("Error while creating TC request.", exception.getMessage());
		verify(commonDoctorServiceImpl, times(1))
				.callTmForSpecialistSlotBook(any(TcSpecialistSlotBookingRequestOBJ.class), eq(authorization));
		verify(teleConsultationServiceImpl, times(1)).createTCRequest(any(TCRequestModel.class));
	}

	@Test
	public void testGetOpenKMDocURL_Success() {
		String fileUUID = "1234-5678-91011";
		String expectedResponse = "Document URL";

		when(benVisitDetailRepo.getFileUUID(1)).thenReturn(fileUUID);

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("fileUID", fileUUID);

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", "application/json");
		headers.add("AUTHORIZATION", authorization);

		HttpEntity<Object> request = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> responseEntity = new ResponseEntity<>(expectedResponse, HttpStatus.OK);

		when(restTemplate.exchange(openkmDocUrl, HttpMethod.POST, request, String.class)).thenReturn(responseEntity);

		String actualResponse = commonServiceImpl.getOpenKMDocURL(requestOBJ, authorization);

		assertEquals(expectedResponse, actualResponse);
		verify(benVisitDetailRepo, times(1)).getFileUUID(1);
		verify(restTemplate, times(1)).exchange(openkmDocUrl, HttpMethod.POST, request, String.class);
	}

	@Test
	public void testGetOpenKMDocURL_FileUUIDNotFound() {
		when(benVisitDetailRepo.getFileUUID(1)).thenReturn(null);

		String actualResponse = commonServiceImpl.getOpenKMDocURL(requestOBJ, authorization);

		assertNull(actualResponse);
		verify(benVisitDetailRepo, times(1)).getFileUUID(1);
		verify(restTemplate, times(0)).exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class),
				eq(String.class));
	}

	@Test
	public void testGetOpenKMDocURL_FileIDNotPresent() {
		String requestWithoutFileID = "{}";

		String actualResponse = commonServiceImpl.getOpenKMDocURL(requestWithoutFileID, authorization);

		assertNull(actualResponse);
		verify(benVisitDetailRepo, times(0)).getFileUUID(anyInt());
		verify(restTemplate, times(0)).exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class),
				eq(String.class));
	}

	@Test
	public void testGetBenSymptomaticQuestionnaireDetailsData() throws Exception {
		String expectedResponse = "Symptomatic Data";
		when(commonNurseServiceImpl.getBenSymptomaticData(beneficiaryRegID)).thenReturn(expectedResponse);

		String actualResponse = commonServiceImpl.getBenSymptomaticQuestionnaireDetailsData(beneficiaryRegID);

		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void testGetBenPreviousDiabetesData() throws Exception {
		String expectedData = "Previous Diabetes Data";
		when(commonNurseServiceImpl.getBenPreviousDiabetesData(beneficiaryRegID)).thenReturn(expectedData);

		String actualData = commonServiceImpl.getBenPreviousDiabetesData(beneficiaryRegID);

		assertEquals(expectedData, actualData);
		verify(commonNurseServiceImpl, times(1)).getBenPreviousDiabetesData(beneficiaryRegID);
	}

	@Test
	public void testGetBenPreviousReferralData() throws Exception {
		String expectedResponse = "Previous Referral Data";
		when(commonNurseServiceImpl.getBenPreviousReferralData(beneficiaryRegID)).thenReturn(expectedResponse);

		String actualResponse = commonServiceImpl.getBenPreviousReferralData(beneficiaryRegID);

		assertEquals(expectedResponse, actualResponse);
		verify(commonNurseServiceImpl, times(1)).getBenPreviousReferralData(beneficiaryRegID);
	}

	@Test
	public void testGetBenImmunizationServiceHistory() throws Exception {
		Long beneficiaryRegID = 1L;
		when(immunizationServicesRepo.findByBeneficiaryRegIDAndDeleted(beneficiaryRegID, false))
				.thenReturn(immunizationServicesList);

		String expectedJson = new Gson().toJson(immunizationServicesList);
		String actualJson = commonServiceImpl.getBenImmunizationServiceHistory(beneficiaryRegID);

		assertEquals(expectedJson, actualJson);
	}

	@Test
	public void testGetBenImmunizationServiceHistory_EmptyList() throws Exception {
		Long beneficiaryRegID = 2L;
		when(immunizationServicesRepo.findByBeneficiaryRegIDAndDeleted(beneficiaryRegID, false))
				.thenReturn(new ArrayList<>());

		String expectedJson = new Gson().toJson(new ArrayList<>());
		String actualJson = commonServiceImpl.getBenImmunizationServiceHistory(beneficiaryRegID);

		assertEquals(expectedJson, actualJson);
	}
}
