package com.iemr.hwc.service.ncdCare;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.security.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.iemr.hwc.data.nurse.BenAnthropometryDetail;
import com.iemr.hwc.data.nurse.BenPhysicalVitalDetail;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.nurse.CDSSRepo;
import com.iemr.hwc.repo.nurse.anc.BenAdherenceRepo;
import com.iemr.hwc.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.hwc.repo.quickConsultation.LabTestOrderDetailRepo;
import com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.common.CommonServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.labtechnician.LabTechnicianServiceImpl;
import com.iemr.hwc.service.sms.SMSGatewayServiceImpl;
import com.iemr.hwc.service.tele_consultation.TeleConsultationServiceImpl;
import com.iemr.hwc.utils.InputMapper;
import com.iemr.hwc.utils.exception.IEMRException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

public class TestNCDCareServiceImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private CommonServiceImpl commonServiceImpl;

	@Mock
	private SMSGatewayServiceImpl sMSGatewayServiceImpl;
	@Mock
	private CommonNurseServiceImpl commonNurseServiceImpl;

	@Mock
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;

	@Mock
	private TeleConsultationServiceImpl teleConsultationServiceImpl;

	@Mock
	private CommonDoctorServiceImpl commonDoctorServiceImpl;
	@Mock
	private NCDCareDoctorServiceImpl ncdCareDoctorServiceImpl;

	@Mock
	private LabTechnicianServiceImpl labTechnicianServiceImpl;

	@Mock
	private BenVisitDetailRepo benVisitDetailRepo;
	@Mock
	private BenChiefComplaintRepo benChiefComplaintRepo;
	@Mock
	private BenAdherenceRepo benAdherenceRepo;
	@Mock
	private CDSSRepo cdssRepo;
	@Mock
	private PrescriptionDetailRepo prescriptionDetailRepo;
	@Mock
	private LabTestOrderDetailRepo labTestOrderDetailRepo;

	@InjectMocks
	private NCDCareServiceImpl ncdCareServiceImpl;

	private JsonObject requestOBJ;
	private String Authorization;
	private JsonObject investigationDataCheck;
	private JsonObject tmpOBJ;
	private Long benVisitID;
	private Long benFlowID;
	private Long benVisitCode;
	private Integer vanID;
	private TeleconsultationRequestOBJ tcRequestOBJ;
	private JsonObject visitDetailsOBJ;
	private CommonUtilityClass nurseUtilityClass;
	private JsonObject ncdCareHistoryOBJ;
	private JsonObject validVitalDetails;
	private Long benRegID;
	private Long visitCode;

	private Gson gson;
	private Long beneficiaryRegID;

	private BenAnthropometryDetail benAnthropometryDetail;
	private BenPhysicalVitalDetail benPhysicalVitalDetail;
	private String authorization;
	private JsonObject historyOBJ;
	private JsonObject vitalDetailsOBJ;
	private CommonUtilityClass commonUtilityClass;

	@BeforeEach
	public void setUp() {
		requestOBJ = new JsonObject();
		Authorization = "Bearer token";
	}

	@Test
	public void testSaveNCDCareNurseData_InvalidInput() {
		Exception exception = assertThrows(Exception.class, () -> {
			ncdCareServiceImpl.saveNCDCareNurseData(null, Authorization);
		});

		assertEquals("Invalid input", exception.getMessage());
	}

	@Test
	public void testSaveNCDCareNurseData_VisitDetailsNull() {
		requestOBJ.add("visitDetails", null);

		Exception exception = assertThrows(Exception.class, () -> {
			ncdCareServiceImpl.saveNCDCareNurseData(requestOBJ, Authorization);
		});

		assertEquals("Invalid input", exception.getMessage());
	}

	@Test
	public void testSaveNCDCareNurseData_VisitIdAndCodeMapValid() throws Exception {
		JsonObject visitDetails = new JsonObject();
		requestOBJ.add("visitDetails", visitDetails);

		Map<String, Long> visitIdAndCodeMap = new HashMap<>();
		visitIdAndCodeMap.put("visitID", 1L);
		visitIdAndCodeMap.put("visitCode", 1L);

		when(commonServiceImpl.createTcRequest(any(), any(), anyString())).thenReturn(null);
		when(ncdCareServiceImpl.saveBenVisitDetails(any(), any())).thenReturn(visitIdAndCodeMap);
		when(ncdCareServiceImpl.saveBenNCDCareHistoryDetails(any(), anyLong(), anyLong())).thenReturn(1L);
		when(ncdCareServiceImpl.saveBenNCDCareVitalDetails(any(), anyLong(), anyLong())).thenReturn(1L);
		when(ncdCareServiceImpl.updateBenFlowNurseAfterNurseActivityANC(any(), any(), anyLong(), anyLong(), anyLong(),
				any(), any())).thenReturn(1);

		String response = ncdCareServiceImpl.saveNCDCareNurseData(requestOBJ, Authorization);

		Map<String, String> expectedResponse = new HashMap<>();
		expectedResponse.put("visitCode", "1");
		expectedResponse.put("response", "Data saved successfully");

		assertEquals(new Gson().toJson(expectedResponse), response);
	}

	@Test
	public void testSaveNCDCareNurseData_VisitIdAndCodeMapInvalid() throws Exception {
		JsonObject visitDetails = new JsonObject();
		requestOBJ.add("visitDetails", visitDetails);

		Map<String, Long> visitIdAndCodeMap = new HashMap<>();

		when(ncdCareServiceImpl.saveBenVisitDetails(any(), any())).thenReturn(visitIdAndCodeMap);

		String response = ncdCareServiceImpl.saveNCDCareNurseData(requestOBJ, Authorization);

		Map<String, String> expectedResponse = new HashMap<>();
		expectedResponse.put("response", "Data already saved");

		assertEquals(new Gson().toJson(expectedResponse), response);
	}

	@Test
	public void testSaveNCDCareNurseData_HistoryAndVitalSaveSuccess() throws Exception {
		JsonObject visitDetails = new JsonObject();
		requestOBJ.add("visitDetails", visitDetails);

		Map<String, Long> visitIdAndCodeMap = new HashMap<>();
		visitIdAndCodeMap.put("visitID", 1L);
		visitIdAndCodeMap.put("visitCode", 1L);

		when(commonServiceImpl.createTcRequest(any(), any(), anyString())).thenReturn(null);
		when(ncdCareServiceImpl.saveBenVisitDetails(any(), any())).thenReturn(visitIdAndCodeMap);
		when(ncdCareServiceImpl.saveBenNCDCareHistoryDetails(any(), anyLong(), anyLong())).thenReturn(1L);
		when(ncdCareServiceImpl.saveBenNCDCareVitalDetails(any(), anyLong(), anyLong())).thenReturn(1L);
		when(ncdCareServiceImpl.updateBenFlowNurseAfterNurseActivityANC(any(), any(), anyLong(), anyLong(), anyLong(),
				any(), any())).thenReturn(1);

		String response = ncdCareServiceImpl.saveNCDCareNurseData(requestOBJ, Authorization);

		Map<String, String> expectedResponse = new HashMap<>();
		expectedResponse.put("visitCode", "1");
		expectedResponse.put("response", "Data saved successfully");

		assertEquals(new Gson().toJson(expectedResponse), response);
	}

	@Test
	public void testSaveNCDCareNurseData_HistoryOrVitalSaveFail() throws Exception {
		JsonObject visitDetails = new JsonObject();
		requestOBJ.add("visitDetails", visitDetails);

		Map<String, Long> visitIdAndCodeMap = new HashMap<>();
		visitIdAndCodeMap.put("visitID", 1L);
		visitIdAndCodeMap.put("visitCode", 1L);

		when(commonServiceImpl.createTcRequest(any(), any(), anyString())).thenReturn(null);
		when(ncdCareServiceImpl.saveBenVisitDetails(any(), any())).thenReturn(visitIdAndCodeMap);
		when(ncdCareServiceImpl.saveBenNCDCareHistoryDetails(any(), anyLong(), anyLong())).thenReturn(null);
		when(ncdCareServiceImpl.saveBenNCDCareVitalDetails(any(), anyLong(), anyLong())).thenReturn(1L);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			ncdCareServiceImpl.saveNCDCareNurseData(requestOBJ, Authorization);
		});

		assertEquals("Error occurred while saving data", exception.getMessage());
	}

	@Test
	public void testUpdateBenFlowNurseAfterNurseActivityANC_LabAndDocTransfer() {
		investigationDataCheck.add("laboratoryList", new JsonObject());

		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(), anyLong(),
				anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(),
				anyInt(), anyShort(), any(Timestamp.class), anyInt())).thenReturn(1);

		int result = ncdCareServiceImpl.updateBenFlowNurseAfterNurseActivityANC(investigationDataCheck, tmpOBJ,
				benVisitID, benFlowID, benVisitCode, vanID, tcRequestOBJ);

		assertEquals(1, result);
		verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(),
				anyLong(), anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(),
				anyLong(), anyInt(), anyShort(), any(Timestamp.class), anyInt());
	}

	@Test
	public void testUpdateBenFlowNurseAfterNurseActivityANC_DocOnlyTransfer() {
		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(), anyLong(),
				anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(),
				anyInt(), anyShort(), any(Timestamp.class), anyInt())).thenReturn(1);

		int result = ncdCareServiceImpl.updateBenFlowNurseAfterNurseActivityANC(investigationDataCheck, tmpOBJ,
				benVisitID, benFlowID, benVisitCode, vanID, tcRequestOBJ);

		assertEquals(1, result);
		verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(),
				anyLong(), anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(),
				anyLong(), anyInt(), anyShort(), any(Timestamp.class), anyInt());
	}

	@Test
	public void testUpdateBenFlowNurseAfterNurseActivityANC_WithTeleconsultation() {
		tcRequestOBJ.setUserID(1);
		tcRequestOBJ.setAllocationDate(new Timestamp(System.currentTimeMillis()));

		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(), anyLong(),
				anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(),
				anyInt(), anyShort(), any(Timestamp.class), anyInt())).thenReturn(1);

		int result = ncdCareServiceImpl.updateBenFlowNurseAfterNurseActivityANC(investigationDataCheck, tmpOBJ,
				benVisitID, benFlowID, benVisitCode, vanID, tcRequestOBJ);

		assertEquals(1, result);
		verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(),
				anyLong(), anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(),
				anyLong(), anyInt(), anyShort(), any(Timestamp.class), anyInt());
	}

	@Test
	public void testSaveBenVisitDetails_Success() throws Exception {
		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class))).thenReturn(1L);
		when(commonNurseServiceImpl.generateVisitCode(anyLong(), anyInt(), anyInt())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenAdherenceDetails(any(BenAdherence.class))).thenReturn(1);
		when(commonNurseServiceImpl.saveBenInvestigationDetails(any(WrapperBenInvestigationANC.class))).thenReturn(1);

		Map<String, Long> result = ncdCareServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass);

		assertNotNull(result);
		assertEquals(1L, result.get("visitID"));
		assertEquals(1L, result.get("visitCode"));
	}

	@Test
	public void testSaveBenVisitDetails_AlreadySaved() throws Exception {
		when(commonNurseServiceImpl.getMaxCurrentdate(anyLong(), anyString(), anyString())).thenReturn(1);

		Map<String, Long> result = ncdCareServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass);

		assertTrue(result.isEmpty());
	}

	@Test
	public void testSaveBenVisitDetails_NullInput() throws Exception {
		Map<String, Long> result = ncdCareServiceImpl.saveBenVisitDetails(null, nurseUtilityClass);

		assertTrue(result.isEmpty());
	}

	@Test
	public void testSaveBenNCDCareHistoryDetails_AllFields() throws Exception {
		// Setup the JSON object with all fields
		ncdCareHistoryOBJ = JsonParser.parseString(
				"{ \"pastHistory\": {}, \"comorbidConditions\": {}, \"medicationHistory\": {}, \"femaleObstetricHistory\": {}, \"menstrualHistory\": {}, \"familyHistory\": {}, \"personalHistory\": {}, \"childVaccineDetails\": {}, \"immunizationHistory\": {}, \"developmentHistory\": {}, \"feedingHistory\": {}, \"perinatalHistroy\": {} }")
				.getAsJsonObject();

		// Mock the behavior of commonNurseServiceImpl
		when(commonNurseServiceImpl.saveBenPastHistory(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenComorbidConditions(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenMedicationHistory(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveFemaleObstetricHistory(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenMenstrualHistory(any())).thenReturn(1);
		when(commonNurseServiceImpl.saveBenFamilyHistory(any())).thenReturn(1L);
		when(commonNurseServiceImpl.savePersonalHistory(any())).thenReturn(1);
		when(commonNurseServiceImpl.saveAllergyHistory(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveChildOptionalVaccineDetail(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveImmunizationHistory(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveChildDevelopmentHistory(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveChildFeedingHistory(any())).thenReturn(1L);
		when(commonNurseServiceImpl.savePerinatalHistory(any())).thenReturn(1L);

		Long result = ncdCareServiceImpl.saveBenNCDCareHistoryDetails(ncdCareHistoryOBJ, benVisitID, benVisitCode);

		assertNotNull(result);
		assertEquals(1L, result);
	}

	@Test
	public void testSaveBenNCDCareHistoryDetails_MissingFields() throws Exception {
		// Setup the JSON object with some fields missing
		ncdCareHistoryOBJ = JsonParser
				.parseString("{ \"pastHistory\": {}, \"comorbidConditions\": {}, \"medicationHistory\": {} }")
				.getAsJsonObject();

		// Mock the behavior of commonNurseServiceImpl
		when(commonNurseServiceImpl.saveBenPastHistory(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenComorbidConditions(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenMedicationHistory(any())).thenReturn(1L);

		Long result = ncdCareServiceImpl.saveBenNCDCareHistoryDetails(ncdCareHistoryOBJ, benVisitID, benVisitCode);

		assertNotNull(result);
		assertEquals(1L, result);
	}

	@Test
	public void testSaveBenNCDCareHistoryDetails_NullObject() {
		Exception exception = assertThrows(Exception.class, () -> {
			ncdCareServiceImpl.saveBenNCDCareHistoryDetails(null, benVisitID, benVisitCode);
		});

		String expectedMessage = "Invalid input";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void testSaveBenNCDCareHistoryDetails_EmptyObject() throws Exception {
		// Setup the JSON object as empty
		ncdCareHistoryOBJ = new JsonObject();

		Long result = ncdCareServiceImpl.saveBenNCDCareHistoryDetails(ncdCareHistoryOBJ, benVisitID, benVisitCode);

		assertNull(result);
	}

	@Test
	public void testSaveBenNCDCareVitalDetails_Success() throws Exception {
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(any(BenAnthropometryDetail.class)))
				.thenReturn(1L);
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(any(BenPhysicalVitalDetail.class)))
				.thenReturn(1L);

		Long result = ncdCareServiceImpl.saveBenNCDCareVitalDetails(validVitalDetails, benVisitID, benVisitCode);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(commonNurseServiceImpl, times(1))
				.saveBeneficiaryPhysicalAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(1)).saveBeneficiaryPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testSaveBenNCDCareVitalDetails_FailureAnthropometry() throws Exception {
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(any(BenAnthropometryDetail.class)))
				.thenReturn(0L);
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(any(BenPhysicalVitalDetail.class)))
				.thenReturn(1L);

		Long result = ncdCareServiceImpl.saveBenNCDCareVitalDetails(validVitalDetails, benVisitID, benVisitCode);

		assertNull(result);
		verify(commonNurseServiceImpl, times(1))
				.saveBeneficiaryPhysicalAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(1)).saveBeneficiaryPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testSaveBenNCDCareVitalDetails_FailureVital() throws Exception {
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(any(BenAnthropometryDetail.class)))
				.thenReturn(1L);
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(any(BenPhysicalVitalDetail.class)))
				.thenReturn(0L);

		Long result = ncdCareServiceImpl.saveBenNCDCareVitalDetails(validVitalDetails, benVisitID, benVisitCode);

		assertNull(result);
		verify(commonNurseServiceImpl, times(1))
				.saveBeneficiaryPhysicalAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(1)).saveBeneficiaryPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testSaveBenNCDCareVitalDetails_NullInput() throws Exception {
		Long result = ncdCareServiceImpl.saveBenNCDCareVitalDetails(null, benVisitID, benVisitCode);

		assertNull(result);
		verify(commonNurseServiceImpl, times(0))
				.saveBeneficiaryPhysicalAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(0)).saveBeneficiaryPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testGetBenVisitDetailsFrmNurseNCDCare() {
		when(commonNurseServiceImpl.getCSVisitDetails(benRegID, visitCode)).thenReturn(visitDetail);
		when(commonNurseServiceImpl.getBenAdherence(benRegID, visitCode)).thenReturn("Adherence");
		when(commonNurseServiceImpl.getLabTestOrders(benRegID, visitCode)).thenReturn("Investigation");
		when(commonNurseServiceImpl.getBenCdss(benRegID, visitCode)).thenReturn("Cdss");

		String response = ncdCareServiceImpl.getBenVisitDetailsFrmNurseNCDCare(benRegID, visitCode);

		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("NCDCareNurseVisitDetail", gson.toJson(visitDetail));
		expectedMap.put("BenAdherence", "Adherence");
		expectedMap.put("Investigation", "Investigation");
		expectedMap.put("Cdss", "Cdss");

		assertEquals(expectedMap.toString(), response);
		verify(commonNurseServiceImpl, times(1)).getCSVisitDetails(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenAdherence(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getLabTestOrders(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenCdss(benRegID, visitCode);
	}

	@Test
	public void testGetBenNCDCareHistoryDetails() throws IEMRException {
		Map<String, Object> historyDetailsMap = new HashMap<>();
		historyDetailsMap.put("PastHistory", "PastHistoryData");
		historyDetailsMap.put("ComorbidityConditions", "ComorbidityConditionsData");
		historyDetailsMap.put("MedicationHistory", "MedicationHistoryData");
		historyDetailsMap.put("PersonalHistory", "PersonalHistoryData");
		historyDetailsMap.put("FamilyHistory", "FamilyHistoryData");
		historyDetailsMap.put("MenstrualHistory", "MenstrualHistoryData");
		historyDetailsMap.put("FemaleObstetricHistory", "FemaleObstetricHistoryData");
		historyDetailsMap.put("ImmunizationHistory", "ImmunizationHistoryData");
		historyDetailsMap.put("childOptionalVaccineHistory", "childOptionalVaccineHistoryData");
		historyDetailsMap.put("DevelopmentHistory", "DevelopmentHistoryData");
		historyDetailsMap.put("PerinatalHistory", "PerinatalHistoryData");
		historyDetailsMap.put("FeedingHistory", "FeedingHistoryData");

		when(commonNurseServiceImpl.getPastHistoryData(benRegID, visitCode)).thenReturn("PastHistoryData");
		when(commonNurseServiceImpl.getComorbidityConditionsHistory(benRegID, visitCode))
				.thenReturn("ComorbidityConditionsData");
		when(commonNurseServiceImpl.getMedicationHistory(benRegID, visitCode)).thenReturn("MedicationHistoryData");
		when(commonNurseServiceImpl.getPersonalHistory(benRegID, visitCode)).thenReturn("PersonalHistoryData");
		when(commonNurseServiceImpl.getFamilyHistory(benRegID, visitCode)).thenReturn("FamilyHistoryData");
		when(commonNurseServiceImpl.getMenstrualHistory(benRegID, visitCode)).thenReturn("MenstrualHistoryData");
		when(commonNurseServiceImpl.getFemaleObstetricHistory(benRegID, visitCode))
				.thenReturn("FemaleObstetricHistoryData");
		when(commonNurseServiceImpl.getImmunizationHistory(benRegID, visitCode)).thenReturn("ImmunizationHistoryData");
		when(commonNurseServiceImpl.getChildOptionalVaccineHistory(benRegID, visitCode))
				.thenReturn("childOptionalVaccineHistoryData");
		when(commonNurseServiceImpl.getDevelopmentHistory(benRegID, visitCode)).thenReturn("DevelopmentHistoryData");
		when(commonNurseServiceImpl.getPerinatalHistory(benRegID, visitCode)).thenReturn("PerinatalHistoryData");
		when(commonNurseServiceImpl.getFeedingHistory(benRegID, visitCode)).thenReturn("FeedingHistoryData");

		String response = ncdCareServiceImpl.getBenNCDCareHistoryDetails(benRegID, visitCode);

		assertNotNull(response);
		assertTrue(response.contains("PastHistoryData"));
		assertTrue(response.contains("ComorbidityConditionsData"));
		assertTrue(response.contains("MedicationHistoryData"));
		assertTrue(response.contains("PersonalHistoryData"));
		assertTrue(response.contains("FamilyHistoryData"));
		assertTrue(response.contains("MenstrualHistoryData"));
		assertTrue(response.contains("FemaleObstetricHistoryData"));
		assertTrue(response.contains("ImmunizationHistoryData"));
		assertTrue(response.contains("childOptionalVaccineHistoryData"));
		assertTrue(response.contains("DevelopmentHistoryData"));
		assertTrue(response.contains("PerinatalHistoryData"));
		assertTrue(response.contains("FeedingHistoryData"));

		verify(commonNurseServiceImpl, times(1)).getPastHistoryData(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getComorbidityConditionsHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getMedicationHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getPersonalHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getFamilyHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getMenstrualHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getFemaleObstetricHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getImmunizationHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getChildOptionalVaccineHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getDevelopmentHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getPerinatalHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getFeedingHistory(benRegID, visitCode);
	}

	@Test
	public void testGetBeneficiaryVitalDetails() {
		String result = ncdCareServiceImpl.getBeneficiaryVitalDetails(beneficiaryRegID, visitCode);

		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("benAnthropometryDetail", benAnthropometryDetail);
		expectedMap.put("benPhysicalVitalDetail", benPhysicalVitalDetail);

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String expectedJson = gson.toJson(expectedMap);

		assertEquals(expectedJson, result);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID,
				visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetBeneficiaryCdssDetails() {
		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("presentChiefComplaint", "Chief Complaint Details");
		expectedMap.put("diseaseSummary", "Disease Summary Details");

		when(commonNurseServiceImpl.getBenCdssDetails(beneficiaryRegID, benVisitID))
				.thenReturn("Chief Complaint Details", "Disease Summary Details");

		String response = ncdCareServiceImpl.getBeneficiaryCdssDetails(beneficiaryRegID, benVisitID);

		assertTrue(response.contains("Chief Complaint Details"));
		assertTrue(response.contains("Disease Summary Details"));
		verify(commonNurseServiceImpl, times(2)).getBenCdssDetails(beneficiaryRegID, benVisitID);
	}

	@Test
	public void testSaveDoctorData_Success() throws Exception {
		// Setup mock responses
		when(commonDoctorServiceImpl.saveDocFindings(any(WrapperAncFindings.class))).thenReturn(1);
		when(commonNurseServiceImpl.savePrescriptionDetailsAndGetPrescriptionID(anyLong(), anyLong(), anyLong(),
				anyString(), any(), anyLong(), anyLong(), anyLong(), anyString(), anyString(), anyList()))
				.thenReturn(1L);
		when(commonNurseServiceImpl.saveBenInvestigation(any(WrapperBenInvestigationANC.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenPrescribedDrugsList(anyList())).thenReturn(1);
		when(commonDoctorServiceImpl.saveBenReferDetails(any(JsonObject.class), eq(false))).thenReturn(1L);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataSave(any(CommonUtilityClass.class), anyBoolean(),
				anyBoolean(), any(TeleconsultationRequestOBJ.class))).thenReturn(1);

		// Call the method under test
		Long result = ncdCareServiceImpl.saveDoctorData(requestOBJ, authorization);

		// Verify the result
		assertNotNull(result);
		assertEquals(1L, result);

		// Verify interactions with mocks
		verify(commonDoctorServiceImpl, times(1)).saveDocFindings(any(WrapperAncFindings.class));
		verify(commonNurseServiceImpl, times(1)).savePrescriptionDetailsAndGetPrescriptionID(anyLong(), anyLong(),
				anyLong(), anyString(), any(), anyLong(), anyLong(), anyLong(), anyString(), anyString(), anyList());
		verify(commonNurseServiceImpl, times(1)).saveBenInvestigation(any(WrapperBenInvestigationANC.class));
		verify(commonNurseServiceImpl, times(1)).saveBenPrescribedDrugsList(anyList());
		verify(commonDoctorServiceImpl, times(1)).saveBenReferDetails(any(JsonObject.class), eq(false));
		verify(commonDoctorServiceImpl, times(1)).updateBenFlowtableAfterDocDataSave(any(CommonUtilityClass.class),
				anyBoolean(), anyBoolean(), any(TeleconsultationRequestOBJ.class));
	}

	@Test
	public void testSaveDoctorData_NullRequestOBJ() throws Exception {
		// Call the method under test with null requestOBJ
		Long result = ncdCareServiceImpl.saveDoctorData(null, authorization);

		// Verify the result
		assertNull(result);

		// Verify no interactions with mocks
		verifyNoInteractions(commonDoctorServiceImpl);
		verifyNoInteractions(commonNurseServiceImpl);
	}

	@Test
	public void testSaveDoctorData_ExceptionThrown() throws Exception {
		// Setup mock responses to throw an exception
		when(commonDoctorServiceImpl.saveDocFindings(any(WrapperAncFindings.class)))
				.thenThrow(new RuntimeException("Test Exception"));

		// Call the method under test and expect an exception
		Exception exception = assertThrows(RuntimeException.class, () -> {
			ncdCareServiceImpl.saveDoctorData(requestOBJ, authorization);
		});

		// Verify the exception message
		assertEquals("Test Exception", exception.getMessage());

		// Verify interactions with mocks
		verify(commonDoctorServiceImpl, times(1)).saveDocFindings(any(WrapperAncFindings.class));
		verifyNoMoreInteractions(commonDoctorServiceImpl);
		verifyNoInteractions(commonNurseServiceImpl);
	}

	@Test
	public void testSaveDoctorData_MissingRequiredFields() throws Exception {
		// Setup requestOBJ with missing required fields
		requestOBJ.add("missingField", new JsonArray());

		// Call the method under test
		Long result = ncdCareServiceImpl.saveDoctorData(requestOBJ, authorization);

		// Verify the result
		assertNull(result);

		// Verify no interactions with mocks
		verifyNoInteractions(commonDoctorServiceImpl);
		verifyNoInteractions(commonNurseServiceImpl);
	}

	@Test
	public void testUpdateBenHistoryDetails_Success() throws Exception {
		// Setup mock responses
		when(commonNurseServiceImpl.updateBenPastHistoryDetails(any(BenMedHistory.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateBenComorbidConditions(any(WrapperComorbidCondDetails.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateBenMedicationHistory(any(WrapperMedicationHistory.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateBenPersonalHistory(any(BenPersonalHabit.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateBenAllergicHistory(any(BenAllergyHistory.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateBenFamilyHistory(any(BenFamilyHistory.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateMenstrualHistory(any(BenMenstrualDetails.class))).thenReturn(1);
		when(commonNurseServiceImpl.updatePastObstetricHistory(any(WrapperFemaleObstetricHistory.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateChildImmunizationDetail(any(WrapperImmunizationHistory.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateChildOptionalVaccineDetail(any(WrapperChildOptionalVaccineDetail.class)))
				.thenReturn(1);
		when(commonNurseServiceImpl.updateChildFeedingHistory(any(ChildFeedingDetails.class))).thenReturn(1);
		when(commonNurseServiceImpl.updatePerinatalHistory(any(PerinatalHistory.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateChildDevelopmentHistory(any(BenChildDevelopmentHistory.class))).thenReturn(1);

		// Add necessary fields to historyOBJ
		historyOBJ.add("pastHistory", new JsonObject());
		historyOBJ.add("comorbidConditions", new JsonObject());
		historyOBJ.add("medicationHistory", new JsonObject());
		historyOBJ.add("personalHistory", new JsonObject());
		historyOBJ.add("familyHistory", new JsonObject());
		historyOBJ.add("menstrualHistory", new JsonObject());
		historyOBJ.add("femaleObstetricHistory", new JsonObject());
		historyOBJ.add("immunizationHistory", new JsonObject());
		historyOBJ.add("childVaccineDetails", new JsonObject());
		historyOBJ.add("feedingHistory", new JsonObject());
		historyOBJ.add("perinatalHistroy", new JsonObject());
		historyOBJ.add("developmentHistory", new JsonObject());

		int result = ncdCareServiceImpl.updateBenHistoryDetails(historyOBJ);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).updateBenPastHistoryDetails(any(BenMedHistory.class));
		verify(commonNurseServiceImpl, times(1)).updateBenComorbidConditions(any(WrapperComorbidCondDetails.class));
		verify(commonNurseServiceImpl, times(1)).updateBenMedicationHistory(any(WrapperMedicationHistory.class));
		verify(commonNurseServiceImpl, times(1)).updateBenPersonalHistory(any(BenPersonalHabit.class));
		verify(commonNurseServiceImpl, times(1)).updateBenAllergicHistory(any(BenAllergyHistory.class));
		verify(commonNurseServiceImpl, times(1)).updateBenFamilyHistory(any(BenFamilyHistory.class));
		verify(commonNurseServiceImpl, times(1)).updateMenstrualHistory(any(BenMenstrualDetails.class));
		verify(commonNurseServiceImpl, times(1)).updatePastObstetricHistory(any(WrapperFemaleObstetricHistory.class));
		verify(commonNurseServiceImpl, times(1)).updateChildImmunizationDetail(any(WrapperImmunizationHistory.class));
		verify(commonNurseServiceImpl, times(1))
				.updateChildOptionalVaccineDetail(any(WrapperChildOptionalVaccineDetail.class));
		verify(commonNurseServiceImpl, times(1)).updateChildFeedingHistory(any(ChildFeedingDetails.class));
		verify(commonNurseServiceImpl, times(1)).updatePerinatalHistory(any(PerinatalHistory.class));
		verify(commonNurseServiceImpl, times(1)).updateChildDevelopmentHistory(any(BenChildDevelopmentHistory.class));
	}

	@Test
	public void testUpdateBenHistoryDetails_NullHistoryOBJ() throws Exception {
		int result = ncdCareServiceImpl.updateBenHistoryDetails(null);
		assertEquals(0, result);
	}

	@Test
	public void testUpdateBenHistoryDetails_PartialSuccess() throws Exception {
		// Setup mock responses
		when(commonNurseServiceImpl.updateBenPastHistoryDetails(any(BenMedHistory.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateBenComorbidConditions(any(WrapperComorbidCondDetails.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateBenMedicationHistory(any(WrapperMedicationHistory.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateBenPersonalHistory(any(BenPersonalHabit.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateBenAllergicHistory(any(BenAllergyHistory.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateBenFamilyHistory(any(BenFamilyHistory.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateMenstrualHistory(any(BenMenstrualDetails.class))).thenReturn(1);
		when(commonNurseServiceImpl.updatePastObstetricHistory(any(WrapperFemaleObstetricHistory.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateChildImmunizationDetail(any(WrapperImmunizationHistory.class))).thenReturn(0);
		when(commonNurseServiceImpl.updateChildOptionalVaccineDetail(any(WrapperChildOptionalVaccineDetail.class)))
				.thenReturn(1);
		when(commonNurseServiceImpl.updateChildFeedingHistory(any(ChildFeedingDetails.class))).thenReturn(1);
		when(commonNurseServiceImpl.updatePerinatalHistory(any(PerinatalHistory.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateChildDevelopmentHistory(any(BenChildDevelopmentHistory.class))).thenReturn(1);

		// Add necessary fields to historyOBJ
		historyOBJ.add("pastHistory", new JsonObject());
		historyOBJ.add("comorbidConditions", new JsonObject());
		historyOBJ.add("medicationHistory", new JsonObject());
		historyOBJ.add("personalHistory", new JsonObject());
		historyOBJ.add("familyHistory", new JsonObject());
		historyOBJ.add("menstrualHistory", new JsonObject());
		historyOBJ.add("femaleObstetricHistory", new JsonObject());
		historyOBJ.add("immunizationHistory", new JsonObject());
		historyOBJ.add("childVaccineDetails", new JsonObject());
		historyOBJ.add("feedingHistory", new JsonObject());
		historyOBJ.add("perinatalHistroy", new JsonObject());
		historyOBJ.add("developmentHistory", new JsonObject());

		int result = ncdCareServiceImpl.updateBenHistoryDetails(historyOBJ);

		assertEquals(0, result);
		verify(commonNurseServiceImpl, times(1)).updateBenPastHistoryDetails(any(BenMedHistory.class));
		verify(commonNurseServiceImpl, times(1)).updateBenComorbidConditions(any(WrapperComorbidCondDetails.class));
		verify(commonNurseServiceImpl, times(1)).updateBenMedicationHistory(any(WrapperMedicationHistory.class));
		verify(commonNurseServiceImpl, times(1)).updateBenPersonalHistory(any(BenPersonalHabit.class));
		verify(commonNurseServiceImpl, times(1)).updateBenAllergicHistory(any(BenAllergyHistory.class));
		verify(commonNurseServiceImpl, times(1)).updateBenFamilyHistory(any(BenFamilyHistory.class));
		verify(commonNurseServiceImpl, times(1)).updateMenstrualHistory(any(BenMenstrualDetails.class));
		verify(commonNurseServiceImpl, times(1)).updatePastObstetricHistory(any(WrapperFemaleObstetricHistory.class));
		verify(commonNurseServiceImpl, times(1)).updateChildImmunizationDetail(any(WrapperImmunizationHistory.class));
		verify(commonNurseServiceImpl, times(1))
				.updateChildOptionalVaccineDetail(any(WrapperChildOptionalVaccineDetail.class));
		verify(commonNurseServiceImpl, times(1)).updateChildFeedingHistory(any(ChildFeedingDetails.class));
		verify(commonNurseServiceImpl, times(1)).updatePerinatalHistory(any(PerinatalHistory.class));
		verify(commonNurseServiceImpl, times(1)).updateChildDevelopmentHistory(any(BenChildDevelopmentHistory.class));
	}

	@Test
	public void testUpdateBenVitalDetails_Success() throws Exception {
		vitalDetailsOBJ.add("benAnthropometryDetail", new JsonObject());
		vitalDetailsOBJ.add("benPhysicalVitalDetail", new JsonObject());

		int result = ncdCareServiceImpl.updateBenVitalDetails(vitalDetailsOBJ);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(1)).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testUpdateBenVitalDetails_NullVitalDetailsOBJ() throws Exception {
		int result = ncdCareServiceImpl.updateBenVitalDetails(null);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, never()).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, never()).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testUpdateBenVitalDetails_NullFields() throws Exception {
		vitalDetailsOBJ.add("benAnthropometryDetail", null);
		vitalDetailsOBJ.add("benPhysicalVitalDetail", null);

		int result = ncdCareServiceImpl.updateBenVitalDetails(vitalDetailsOBJ);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, never()).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, never()).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testGetBenNCDCareNurseData() throws IEMRException {
		// Mocking the responses
		String mockVitals = "{\"benAnthropometryDetail\":\"details\",\"benPhysicalVitalDetail\":\"details\"}";
		String mockHistory = "{\"PastHistory\":\"details\",\"ComorbidityConditions\":\"details\"}";
		String mockCdss = "{\"presentChiefComplaint\":\"details\",\"diseaseSummary\":\"details\"}";

		when(commonNurseServiceImpl.getBeneficiaryVitalDetails(benRegID, visitCode)).thenReturn(mockVitals);
		when(commonNurseServiceImpl.getPastHistoryData(benRegID, visitCode)).thenReturn("details");
		when(commonNurseServiceImpl.getComorbidityConditionsHistory(benRegID, visitCode)).thenReturn("details");
		when(commonNurseServiceImpl.getMedicationHistory(benRegID, visitCode)).thenReturn("details");
		when(commonNurseServiceImpl.getPersonalHistory(benRegID, visitCode)).thenReturn("details");
		when(commonNurseServiceImpl.getFamilyHistory(benRegID, visitCode)).thenReturn("details");
		when(commonNurseServiceImpl.getMenstrualHistory(benRegID, visitCode)).thenReturn("details");
		when(commonNurseServiceImpl.getFemaleObstetricHistory(benRegID, visitCode)).thenReturn("details");
		when(commonNurseServiceImpl.getImmunizationHistory(benRegID, visitCode)).thenReturn("details");
		when(commonNurseServiceImpl.getChildOptionalVaccineHistory(benRegID, visitCode)).thenReturn("details");
		when(commonNurseServiceImpl.getDevelopmentHistory(benRegID, visitCode)).thenReturn("details");
		when(commonNurseServiceImpl.getPerinatalHistory(benRegID, visitCode)).thenReturn("details");
		when(commonNurseServiceImpl.getFeedingHistory(benRegID, visitCode)).thenReturn("details");
		when(commonNurseServiceImpl.getBenCdssDetails(benRegID, visitCode)).thenReturn("details");

		// Call the method
		String response = ncdCareServiceImpl.getBenNCDCareNurseData(benRegID, visitCode);

		// Assertions
		assertNotNull(response);
		assertTrue(response.contains("vitals"));
		assertTrue(response.contains("history"));
		assertTrue(response.contains("cdss"));

		// Verify the interactions
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryVitalDetails(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getPastHistoryData(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getComorbidityConditionsHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getMedicationHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getPersonalHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getFamilyHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getMenstrualHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getFemaleObstetricHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getImmunizationHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getChildOptionalVaccineHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getDevelopmentHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getPerinatalHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getFeedingHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenCdssDetails(benRegID, visitCode);
	}

	@Test
	public void testGetBenCaseRecordFromDoctorNCDCare() {
		// Mocking the behavior of dependencies
		when(commonDoctorServiceImpl.getFindingsDetails(benRegID, visitCode)).thenReturn("Findings");
		when(ncdCareDoctorServiceImpl.getNCDCareDiagnosisDetails(benRegID, visitCode))
				.thenReturn("{\"counsellingProvided\":\"Counselling\"}");
		when(commonDoctorServiceImpl.getInvestigationDetails(benRegID, visitCode)).thenReturn("Investigation");
		when(commonDoctorServiceImpl.getPrescribedDrugs(benRegID, visitCode)).thenReturn("Prescription");
		when(commonDoctorServiceImpl.getReferralDetails(benRegID, visitCode, false)).thenReturn("Refer");
		when(labTechnicianServiceImpl.getLabResultDataForBen(benRegID, visitCode)).thenReturn("LabReport");
		when(commonNurseServiceImpl.getGraphicalTrendData(benRegID, "ncdCare")).thenReturn("GraphData");
		when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(benRegID, visitCode))
				.thenReturn("ArchivedVisitcodeForLabResult");

		// Call the method under test
		String response = ncdCareServiceImpl.getBenCaseRecordFromDoctorNCDCare(benRegID, visitCode);

		// Parse the response
		Gson gson = new Gson();
		Map<String, Object> responseMap = gson.fromJson(response, HashMap.class);

		// Assertions
		assertEquals("Findings", responseMap.get("findings"));
		assertEquals("{\"counsellingProvided\":\"Counselling\"}", responseMap.get("diagnosis"));
		assertEquals("Investigation", responseMap.get("investigation"));
		assertEquals("Prescription", responseMap.get("prescription"));
		assertEquals("Refer", responseMap.get("Refer"));
		assertEquals("LabReport", responseMap.get("LabReport"));
		assertEquals("GraphData", responseMap.get("GraphData"));
		assertEquals("ArchivedVisitcodeForLabResult", responseMap.get("ArchivedVisitcodeForLabResult"));

		// Verify interactions with mocks
		verify(commonDoctorServiceImpl, times(1)).getFindingsDetails(benRegID, visitCode);
		verify(ncdCareDoctorServiceImpl, times(1)).getNCDCareDiagnosisDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getInvestigationDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getPrescribedDrugs(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getReferralDetails(benRegID, visitCode, false);
		verify(labTechnicianServiceImpl, times(1)).getLabResultDataForBen(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getGraphicalTrendData(benRegID, "ncdCare");
		verify(labTechnicianServiceImpl, times(1)).getLast_3_ArchivedTestVisitList(benRegID, visitCode);
	}

	@Test
	public void testUpdateNCDCareDoctorData_Success() throws Exception {
		// Setup
		JsonArray investigationArray = new JsonArray();
		requestOBJ.add("investigation", investigationArray);
		JsonArray prescriptionArray = new JsonArray();
		requestOBJ.add("prescription", prescriptionArray);
		requestOBJ.add("findings", new JsonObject());
		requestOBJ.add("diagnosis", new JsonObject());
		requestOBJ.add("refer", new JsonObject());

		when(commonDoctorServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class),
				eq(authorization))).thenReturn(new TeleconsultationRequestOBJ());
		when(commonDoctorServiceImpl.updateDocFindings(any(WrapperAncFindings.class))).thenReturn(1);
		when(commonNurseServiceImpl.updatePrescription(any(PrescriptionDetail.class))).thenReturn(1);
		when(commonNurseServiceImpl.saveBenInvestigation(any(WrapperBenInvestigationANC.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenPrescribedDrugsList(anyList())).thenReturn(1);
		when(commonDoctorServiceImpl.updateBenReferDetails(any(JsonObject.class), eq(false))).thenReturn(1L);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(any(CommonUtilityClass.class), eq(true),
				eq(true), any(TeleconsultationRequestOBJ.class))).thenReturn(1);

		// Execute
		Long result = ncdCareServiceImpl.updateNCDCareDoctorData(requestOBJ, authorization);

		// Verify
		assertNotNull(result);
		verify(commonDoctorServiceImpl, times(1)).createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class),
				eq(authorization));
		verify(commonDoctorServiceImpl, times(1)).updateDocFindings(any(WrapperAncFindings.class));
		verify(commonNurseServiceImpl, times(1)).updatePrescription(any(PrescriptionDetail.class));
		verify(commonNurseServiceImpl, times(1)).saveBenInvestigation(any(WrapperBenInvestigationANC.class));
		verify(commonNurseServiceImpl, times(1)).saveBenPrescribedDrugsList(anyList());
		verify(commonDoctorServiceImpl, times(1)).updateBenReferDetails(any(JsonObject.class), eq(false));
		verify(commonDoctorServiceImpl, times(1)).updateBenFlowtableAfterDocDataUpdate(any(CommonUtilityClass.class),
				eq(true), eq(true), any(TeleconsultationRequestOBJ.class));
	}

	@Test
	public void testUpdateNCDCareDoctorData_NullRequest() throws Exception {
		// Execute
		Long result = ncdCareServiceImpl.updateNCDCareDoctorData(null, authorization);

		// Verify
		assertNull(result);
	}

	@Test
	public void testUpdateNCDCareDoctorData_Exception() throws Exception {
		// Setup
		JsonArray investigationArray = new JsonArray();
		requestOBJ.add("investigation", investigationArray);
		JsonArray prescriptionArray = new JsonArray();
		requestOBJ.add("prescription", prescriptionArray);
		requestOBJ.add("findings", new JsonObject());
		requestOBJ.add("diagnosis", new JsonObject());
		requestOBJ.add("refer", new JsonObject());

		when(commonDoctorServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class),
				eq(authorization))).thenReturn(new TeleconsultationRequestOBJ());
		when(commonDoctorServiceImpl.updateDocFindings(any(WrapperAncFindings.class))).thenReturn(1);
		when(commonNurseServiceImpl.updatePrescription(any(PrescriptionDetail.class))).thenReturn(1);
		when(commonNurseServiceImpl.saveBenInvestigation(any(WrapperBenInvestigationANC.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenPrescribedDrugsList(anyList())).thenReturn(1);
		when(commonDoctorServiceImpl.updateBenReferDetails(any(JsonObject.class), eq(false))).thenReturn(1L);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(any(CommonUtilityClass.class), eq(true),
				eq(true), any(TeleconsultationRequestOBJ.class)))
				.thenThrow(new RuntimeException("Error occurred while saving data. Beneficiary status update failed"));

		// Execute & Verify
		assertThrows(RuntimeException.class, () -> {
			ncdCareServiceImpl.updateNCDCareDoctorData(requestOBJ, authorization);
		});
	}
}
