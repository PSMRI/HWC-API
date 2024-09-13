package com.iemr.hwc.service.covid19;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iemr.hwc.data.covid.Covid19BenFeedback;
import com.iemr.hwc.data.nurse.BenAnthropometryDetail;
import com.iemr.hwc.data.nurse.BenPhysicalVitalDetail;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.quickConsultation.PrescriptionDetail;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.nurse.CDSSRepo;
import com.iemr.hwc.repo.nurse.covid19.Covid19BenFeedbackRepo;
import com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonServiceImpl;
import com.iemr.hwc.service.labtechnician.LabTechnicianServiceImpl;
import com.iemr.hwc.service.tele_consultation.TeleConsultationServiceImpl;
import com.iemr.hwc.service.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.mapper.InputMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

public class TestCovid19ServiceImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private CommonServiceImpl commonServiceImpl;

	@Mock
	private SMSSenderGatewayServiceImpl sMSGatewayServiceImpl;
	@Mock
	private CommonNurseServiceImpl commonNurseServiceImpl;
	@Mock
	private BenVisitDetailRepo benVisitDetailRepo;
	@Mock
	private CDSSRepo cdssRepo;
	@Mock
	private Covid19BenFeedbackRepo covid19BenFeedbackRepo;

	@Mock
	private CommonDoctorServiceImpl commonDoctorServiceImpl;
	@Mock
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;
	@Mock
	private LabTechnicianServiceImpl labTechnicianServiceImpl;

	@Mock
	private TeleConsultationServiceImpl teleConsultationServiceImpl;

	@Mock
	private PrescriptionDetailRepo prescriptionDetailRepo;

	@InjectMocks
	private Covid19ServiceImpl covid19ServiceImpl;

	private JsonObject requestOBJ;
	private String authorization;
	private JsonObject validRequestObj;
	private JsonObject invalidRequestObj;
	private JsonObject visitDetailsOBJ;
	private CommonUtilityClass nurseUtilityClass;
	private JsonObject validHistoryDetails;
	private Long benVisitID;
	private Long benVisitCode;
	private JsonObject vitalDetailsOBJ;
	private Long benRegID;
	private Long visitCode;
	private Covid19BenFeedback covid19BenFeedback;
	private Map<String, Object> historyDetailsMap;
	private Long beneficiaryRegID;

	private BenAnthropometryDetail benAnthropometryDetail;
	private BenPhysicalVitalDetail benPhysicalVitalDetail;

	private JsonObject validVitalDetailsOBJ;
	private JsonObject invalidVitalDetailsOBJ;

	private JsonObject historyOBJ;
	private Map<String, Object> expectedResponseMap;
	private PrescriptionDetail prescriptionDetail;

	@BeforeEach
	public void setUp() {
		requestOBJ = new JsonObject();
		JsonObject visitDetails = new JsonObject();
		visitDetails.addProperty("visitID", 1L);
		visitDetails.addProperty("visitCode", 1L);
		requestOBJ.add("visitDetails", visitDetails);

		authorization = "Bearer token";
	}

	@Test
	public void testSaveCovid19NurseData_Success() throws Exception {
		CommonUtilityClass nurseUtilityClass = new CommonUtilityClass();
		nurseUtilityClass.setVisitCode(1L);
		nurseUtilityClass.setBenVisitID(1L);
		nurseUtilityClass.setBenFlowID(1L);

		Map<String, Long> visitIdAndCodeMap = new HashMap<>();
		visitIdAndCodeMap.put("visitID", 1L);
		visitIdAndCodeMap.put("visitCode", 1L);

		when(commonServiceImpl.saveBenVisitDetails(any(JsonObject.class), any(CommonUtilityClass.class)))
				.thenReturn(visitIdAndCodeMap);

		Covid19BenFeedback covid19BenFeedback = new Covid19BenFeedback();
		covid19BenFeedback.setVisitCode(1L);
		when(commonServiceImpl.saveCovidDetails(any(Covid19BenFeedback.class))).thenReturn(1);

		TeleconsultationRequestOBJ tcRequestOBJ = new TeleconsultationRequestOBJ();
		tcRequestOBJ.setWalkIn(false);
		tcRequestOBJ.setSpecializationID(1L);
		tcRequestOBJ.setTmRequestID(1L);
		tcRequestOBJ.setAllocationDate("2023-10-10");
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
				.thenReturn(tcRequestOBJ);

		when(commonServiceImpl.saveBenCovid19HistoryDetails(any(JsonObject.class), eq(1L), eq(1L))).thenReturn(1L);
		when(commonServiceImpl.saveBenCovid19VitalDetails(any(JsonObject.class), eq(1L), eq(1L))).thenReturn(1L);
		when(commonServiceImpl.updateBenFlowNurseAfterNurseActivityANC(any(JsonObject.class), eq(1L), eq(1L), eq(1L),
				eq(1L), any(TeleconsultationRequestOBJ.class))).thenReturn(1);

		String response = covid19ServiceImpl.saveCovid19NurseData(requestOBJ, authorization);

		assertNotNull(response);
		assertTrue(response.contains("Data saved successfully"));
		verify(commonServiceImpl, times(1)).saveBenVisitDetails(any(JsonObject.class), any(CommonUtilityClass.class));
		verify(commonServiceImpl, times(1)).saveCovidDetails(any(Covid19BenFeedback.class));
		verify(commonServiceImpl, times(1)).createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class),
				eq(authorization));
		verify(commonServiceImpl, times(1)).saveBenCovid19HistoryDetails(any(JsonObject.class), eq(1L), eq(1L));
		verify(commonServiceImpl, times(1)).saveBenCovid19VitalDetails(any(JsonObject.class), eq(1L), eq(1L));
		verify(commonServiceImpl, times(1)).updateBenFlowNurseAfterNurseActivityANC(any(JsonObject.class), eq(1L),
				eq(1L), eq(1L), eq(1L), any(TeleconsultationRequestOBJ.class));
		verify(sMSGatewayServiceImpl, times(1)).smsSenderGateway(eq("schedule"), eq(1L), eq(1L), eq(1L), isNull(),
				eq(1L), eq("2023-10-10"), isNull(), eq(authorization));
	}

	@Test
	public void testSaveCovid19NurseData_InvalidInput() {
		JsonObject invalidRequestOBJ = new JsonObject();

		Exception exception = assertThrows(Exception.class, () -> {
			covid19ServiceImpl.saveCovid19NurseData(invalidRequestOBJ, authorization);
		});

		assertEquals("Invalid input", exception.getMessage());
	}

	@Test
	public void testSaveCovid19NurseData_ErrorSavingData() throws Exception {
		CommonUtilityClass nurseUtilityClass = new CommonUtilityClass();
		nurseUtilityClass.setVisitCode(1L);
		nurseUtilityClass.setBenVisitID(1L);
		nurseUtilityClass.setBenFlowID(1L);

		Map<String, Long> visitIdAndCodeMap = new HashMap<>();
		visitIdAndCodeMap.put("visitID", 1L);
		visitIdAndCodeMap.put("visitCode", 1L);

		when(commonServiceImpl.saveBenVisitDetails(any(JsonObject.class), any(CommonUtilityClass.class)))
				.thenReturn(visitIdAndCodeMap);

		when(commonServiceImpl.saveCovidDetails(any(Covid19BenFeedback.class))).thenReturn(1);

		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
				.thenReturn(null);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			covid19ServiceImpl.saveCovid19NurseData(requestOBJ, authorization);
		});

		assertEquals("Error occurred while saving data", exception.getMessage());
	}

	@Test
	public void testDeleteVisitDetails_Success() throws Exception {
		CommonUtilityClass nurseUtilityClass = new CommonUtilityClass();
		nurseUtilityClass.setBeneficiaryRegID(1L);
		nurseUtilityClass.setProviderServiceMapID(1L);

		when(benVisitDetailRepo.getVisitCode(anyLong(), anyLong())).thenReturn(1L);

		covid19ServiceImpl.deleteVisitDetails(validRequestObj);

		verify(cdssRepo, times(1)).deleteVisitDetails(1L);
		verify(covid19BenFeedbackRepo, times(1)).deleteVisitDetails(1L);
		verify(benVisitDetailRepo, times(1)).deleteVisitDetails(1L);
	}

	@Test
	public void testDeleteVisitDetails_NoVisitCode() throws Exception {
		CommonUtilityClass nurseUtilityClass = new CommonUtilityClass();
		nurseUtilityClass.setBeneficiaryRegID(1L);
		nurseUtilityClass.setProviderServiceMapID(1L);

		when(benVisitDetailRepo.getVisitCode(anyLong(), anyLong())).thenReturn(null);

		covid19ServiceImpl.deleteVisitDetails(validRequestObj);

		verify(cdssRepo, times(0)).deleteVisitDetails(anyLong());
		verify(covid19BenFeedbackRepo, times(0)).deleteVisitDetails(anyLong());
		verify(benVisitDetailRepo, times(0)).deleteVisitDetails(anyLong());
	}

	@Test
	public void testDeleteVisitDetails_InvalidRequest() throws Exception {
		covid19ServiceImpl.deleteVisitDetails(invalidRequestObj);

		verify(cdssRepo, times(0)).deleteVisitDetails(anyLong());
		verify(covid19BenFeedbackRepo, times(0)).deleteVisitDetails(anyLong());
		verify(benVisitDetailRepo, times(0)).deleteVisitDetails(anyLong());
	}

	@Test
	public void testSaveBenVisitDetails_NullVisitDetails() throws Exception {
		Map<String, Long> result = covid19ServiceImpl.saveBenVisitDetails(null, nurseUtilityClass);
		assertTrue(result.isEmpty());
	}

	@Test
	public void testSaveBenVisitDetails_NoVisitDetails() throws Exception {
		visitDetailsOBJ.add("visitDetails", null);
		Map<String, Long> result = covid19ServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass);
		assertTrue(result.isEmpty());
	}

	@Test
	public void testSaveBenVisitDetails_ValidVisitDetails() throws Exception {
		JsonObject visitDetails = new JsonObject();
		visitDetails.addProperty("beneficiaryRegID", 1L);
		visitDetails.addProperty("visitReason", "Reason");
		visitDetails.addProperty("visitCategory", "Category");
		visitDetailsOBJ.add("visitDetails", visitDetails);

		BeneficiaryVisitDetail benVisitDetailsOBJ = new BeneficiaryVisitDetail();
		benVisitDetailsOBJ.setBeneficiaryRegID(1L);
		benVisitDetailsOBJ.setVisitReason("Reason");
		benVisitDetailsOBJ.setVisitCategory("Category");

		when(commonNurseServiceImpl.getMaxCurrentdate(anyLong(), anyString(), anyString())).thenReturn(0);
		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class))).thenReturn(1L);
		when(commonNurseServiceImpl.generateVisitCode(anyLong(), anyInt(), anyInt())).thenReturn(1L);

		Map<String, Long> result = covid19ServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass);

		assertNotNull(result);
		assertEquals(1L, result.get("visitID"));
		assertEquals(1L, result.get("visitCode"));
	}

	@Test
	public void testSaveBenVisitDetails_VisitDetailsAlreadySaved() throws Exception {
		JsonObject visitDetails = new JsonObject();
		visitDetails.addProperty("beneficiaryRegID", 1L);
		visitDetails.addProperty("visitReason", "Reason");
		visitDetails.addProperty("visitCategory", "Category");
		visitDetailsOBJ.add("visitDetails", visitDetails);

		when(commonNurseServiceImpl.getMaxCurrentdate(anyLong(), anyString(), anyString())).thenReturn(1);

		Map<String, Long> result = covid19ServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass);

		assertTrue(result.isEmpty());
	}

	@Test
	public void testSaveBenCovid19HistoryDetails_withValidData() throws Exception {
		when(commonNurseServiceImpl.saveBenPastHistory(any(BenMedHistory.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenComorbidConditions(any(WrapperComorbidCondDetails.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenMedicationHistory(any(WrapperMedicationHistory.class))).thenReturn(1L);

		Long result = covid19ServiceImpl.saveBenCovid19HistoryDetails(validHistoryDetails, benVisitID, benVisitCode);

		assertNotNull(result);
		assertEquals(1L, result);
	}

	@Test
	public void testSaveBenCovid19HistoryDetails_withMissingPastHistory() throws Exception {
		validHistoryDetails.remove("pastHistory");

		when(commonNurseServiceImpl.saveBenPastHistory(any(BenMedHistory.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenComorbidConditions(any(WrapperComorbidCondDetails.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenMedicationHistory(any(WrapperMedicationHistory.class))).thenReturn(1L);

		Long result = covid19ServiceImpl.saveBenCovid19HistoryDetails(validHistoryDetails, benVisitID, benVisitCode);

		assertNotNull(result);
		assertEquals(1L, result);
	}

	@Test
	public void testSaveBenCovid19HistoryDetails_withMissingComorbidConditions() throws Exception {
		validHistoryDetails.remove("comorbidConditions");

		when(commonNurseServiceImpl.saveBenPastHistory(any(BenMedHistory.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenComorbidConditions(any(WrapperComorbidCondDetails.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenMedicationHistory(any(WrapperMedicationHistory.class))).thenReturn(1L);

		Long result = covid19ServiceImpl.saveBenCovid19HistoryDetails(validHistoryDetails, benVisitID, benVisitCode);

		assertNotNull(result);
		assertEquals(1L, result);
	}

	@Test
	public void testSaveBenCovid19HistoryDetails_withMissingMedicationHistory() throws Exception {
		validHistoryDetails.remove("medicationHistory");

		when(commonNurseServiceImpl.saveBenPastHistory(any(BenMedHistory.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenComorbidConditions(any(WrapperComorbidCondDetails.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenMedicationHistory(any(WrapperMedicationHistory.class))).thenReturn(1L);

		Long result = covid19ServiceImpl.saveBenCovid19HistoryDetails(validHistoryDetails, benVisitID, benVisitCode);

		assertNotNull(result);
		assertEquals(1L, result);
	}

	@Test
	public void testSaveBenCovid19HistoryDetails_withAllNullInputs() throws Exception {
		JsonObject nullHistoryDetails = new JsonObject();

		Long result = covid19ServiceImpl.saveBenCovid19HistoryDetails(nullHistoryDetails, benVisitID, benVisitCode);

		assertNull(result);
	}

	@Test
	public void testSaveBenCovid19VitalDetails_NullVitalDetailsOBJ() throws Exception {
		Long result = covid19ServiceImpl.saveBenCovid19VitalDetails(null, benVisitID, benVisitCode);
		assertNull(result);
	}

	@Test
	public void testSaveBenCovid19VitalDetails_NullBenAnthropometryDetailAndBenPhysicalVitalDetail() throws Exception {
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(any(BenAnthropometryDetail.class)))
				.thenReturn(null);
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(any(BenPhysicalVitalDetail.class)))
				.thenReturn(null);

		Long result = covid19ServiceImpl.saveBenCovid19VitalDetails(vitalDetailsOBJ, benVisitID, benVisitCode);
		assertNull(result);
	}

	@Test
	public void testSaveBenCovid19VitalDetails_Success() throws Exception {
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(any(BenAnthropometryDetail.class)))
				.thenReturn(1L);
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(any(BenPhysicalVitalDetail.class)))
				.thenReturn(1L);

		Long result = covid19ServiceImpl.saveBenCovid19VitalDetails(vitalDetailsOBJ, benVisitID, benVisitCode);
		assertNotNull(result);
		assertEquals(1L, result);
	}

	@Test
	public void testSaveBenCovid19VitalDetails_AnthropometrySuccess_VitalFail() throws Exception {
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(any(BenAnthropometryDetail.class)))
				.thenReturn(1L);
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(any(BenPhysicalVitalDetail.class)))
				.thenReturn(null);

		Long result = covid19ServiceImpl.saveBenCovid19VitalDetails(vitalDetailsOBJ, benVisitID, benVisitCode);
		assertNull(result);
	}

	@Test
	public void testSaveBenCovid19VitalDetails_VitalSuccess_AnthropometryFail() throws Exception {
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(any(BenAnthropometryDetail.class)))
				.thenReturn(null);
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(any(BenPhysicalVitalDetail.class)))
				.thenReturn(1L);

		Long result = covid19ServiceImpl.saveBenCovid19VitalDetails(vitalDetailsOBJ, benVisitID, benVisitCode);
		assertNull(result);
	}

	@Test
	public void testGetCovidDetailsFound() {
		when(covid19BenFeedbackRepo.findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode))
				.thenReturn(covid19BenFeedback);

		Covid19BenFeedback result = covid19ServiceImpl.getCovidDetails(benRegID, visitCode);

		assertNotNull(result);
		assertEquals(benRegID, result.getBeneficiaryRegID());
		assertEquals(visitCode, result.getVisitCode());
		assertArrayEquals(new String[] { "fever", "cough" }, result.getSymptoms());
		assertArrayEquals(new String[] { "domestic", "international" }, result.getTravelList());
		assertArrayEquals(new String[] { "contact1", "contact2" }, result.getContactStatus());
		assertEquals("YES", result.getSuspectedStatusUI());
		assertNotNull(result.getRecommendation());
		assertArrayEquals(new String[] { "recommendation1", "recommendation2" }, result.getRecommendation().get(0));
	}

	@Test
	public void testGetCovidDetailsNotFound() {
		when(covid19BenFeedbackRepo.findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode)).thenReturn(null);

		Covid19BenFeedback result = covid19ServiceImpl.getCovidDetails(benRegID, visitCode);

		assertNull(result);
	}

	@Test
	public void testGetCovidDetailsWithEmptyFields() {
		covid19BenFeedback.setSymptoms_db(null);
		covid19BenFeedback.setTravelType(null);
		covid19BenFeedback.setcOVID19_contact_history(null);
		covid19BenFeedback.setRecommendation_db(null);
		covid19BenFeedback.setSuspectedStatus(null);

		when(covid19BenFeedbackRepo.findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode))
				.thenReturn(covid19BenFeedback);

		Covid19BenFeedback result = covid19ServiceImpl.getCovidDetails(benRegID, visitCode);

		assertNotNull(result);
		assertNull(result.getSymptoms());
		assertNull(result.getTravelList());
		assertNull(result.getContactStatus());
		assertNull(result.getRecommendation());
		assertNull(result.getSuspectedStatusUI());
	}

	@Test
	public void testGetBenCovid19HistoryDetails_Success() throws IEMRException {
		when(commonNurseServiceImpl.getPastHistoryData(benRegID, visitCode)).thenReturn("Past history data");
		when(commonNurseServiceImpl.getComorbidityConditionsHistory(benRegID, visitCode))
				.thenReturn("Comorbidity conditions data");
		when(commonNurseServiceImpl.getMedicationHistory(benRegID, visitCode)).thenReturn("Medication history data");
		when(commonNurseServiceImpl.getPersonalHistory(benRegID, visitCode)).thenReturn("Personal history data");
		when(commonNurseServiceImpl.getFamilyHistory(benRegID, visitCode)).thenReturn("Family history data");
		when(commonNurseServiceImpl.getMenstrualHistory(benRegID, visitCode)).thenReturn("Menstrual history data");
		when(commonNurseServiceImpl.getFemaleObstetricHistory(benRegID, visitCode))
				.thenReturn("Female obstetric history data");
		when(commonNurseServiceImpl.getImmunizationHistory(benRegID, visitCode))
				.thenReturn("Immunization history data");
		when(commonNurseServiceImpl.getChildOptionalVaccineHistory(benRegID, visitCode))
				.thenReturn("Child optional vaccine history data");
		when(commonNurseServiceImpl.getDevelopmentHistory(benRegID, visitCode)).thenReturn("Development history data");
		when(commonNurseServiceImpl.getPerinatalHistory(benRegID, visitCode)).thenReturn("Perinatal history data");
		when(commonNurseServiceImpl.getFeedingHistory(benRegID, visitCode)).thenReturn("Feeding history data");

		String response = covid19ServiceImpl.getBenCovid19HistoryDetails(benRegID, visitCode);
		String expectedResponse = new Gson().toJson(historyDetailsMap);

		assertEquals(expectedResponse, response);
	}

	@Test
	public void testGetBenCovid19HistoryDetails_PartialData() throws IEMRException {
		when(commonNurseServiceImpl.getPastHistoryData(benRegID, visitCode)).thenReturn("Past history data");
		when(commonNurseServiceImpl.getComorbidityConditionsHistory(benRegID, visitCode)).thenReturn(null);
		when(commonNurseServiceImpl.getMedicationHistory(benRegID, visitCode)).thenReturn("Medication history data");
		when(commonNurseServiceImpl.getPersonalHistory(benRegID, visitCode)).thenReturn(null);
		when(commonNurseServiceImpl.getFamilyHistory(benRegID, visitCode)).thenReturn("Family history data");
		when(commonNurseServiceImpl.getMenstrualHistory(benRegID, visitCode)).thenReturn(null);
		when(commonNurseServiceImpl.getFemaleObstetricHistory(benRegID, visitCode))
				.thenReturn("Female obstetric history data");
		when(commonNurseServiceImpl.getImmunizationHistory(benRegID, visitCode)).thenReturn(null);
		when(commonNurseServiceImpl.getChildOptionalVaccineHistory(benRegID, visitCode))
				.thenReturn("Child optional vaccine history data");
		when(commonNurseServiceImpl.getDevelopmentHistory(benRegID, visitCode)).thenReturn(null);
		when(commonNurseServiceImpl.getPerinatalHistory(benRegID, visitCode)).thenReturn("Perinatal history data");
		when(commonNurseServiceImpl.getFeedingHistory(benRegID, visitCode)).thenReturn(null);

		historyDetailsMap.put("ComorbidityConditions", null);
		historyDetailsMap.put("PersonalHistory", null);
		historyDetailsMap.put("MenstrualHistory", null);
		historyDetailsMap.put("ImmunizationHistory", null);
		historyDetailsMap.put("DevelopmentHistory", null);
		historyDetailsMap.put("FeedingHistory", null);

		String response = covid19ServiceImpl.getBenCovid19HistoryDetails(benRegID, visitCode);
		String expectedResponse = new Gson().toJson(historyDetailsMap);

		assertEquals(expectedResponse, response);
	}

	@Test
	public void testGetBenCovid19HistoryDetails_Exception() throws IEMRException {
		when(commonNurseServiceImpl.getPastHistoryData(benRegID, visitCode))
				.thenThrow(new IEMRException("Exception occurred"));

		Exception exception = assertThrows(IEMRException.class, () -> {
			covid19ServiceImpl.getBenCovid19HistoryDetails(benRegID, visitCode);
		});

		assertEquals("Exception occurred", exception.getMessage());
	}

	@Test
	public void testGetBeneficiaryVitalDetails() {
		// Arrange
		when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, visitCode))
				.thenReturn(benAnthropometryDetail);
		when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode))
				.thenReturn(benPhysicalVitalDetail);

		// Act
		String result = covid19ServiceImpl.getBeneficiaryVitalDetails(beneficiaryRegID, visitCode);

		// Assert
		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("benAnthropometryDetail", benAnthropometryDetail);
		expectedMap.put("benPhysicalVitalDetail", benPhysicalVitalDetail);
		String expectedJson = new Gson().toJson(expectedMap);

		assertEquals(expectedJson, result);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID,
				visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetBeneficiaryVitalDetails_NoAnthropometryDetail() {
		// Arrange
		when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, visitCode))
				.thenReturn(null);
		when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode))
				.thenReturn(benPhysicalVitalDetail);

		// Act
		String result = covid19ServiceImpl.getBeneficiaryVitalDetails(beneficiaryRegID, visitCode);

		// Assert
		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("benAnthropometryDetail", null);
		expectedMap.put("benPhysicalVitalDetail", benPhysicalVitalDetail);
		String expectedJson = new Gson().toJson(expectedMap);

		assertEquals(expectedJson, result);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID,
				visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetBeneficiaryVitalDetails_NoVitalDetail() {
		// Arrange
		when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, visitCode))
				.thenReturn(benAnthropometryDetail);
		when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode)).thenReturn(null);

		// Act
		String result = covid19ServiceImpl.getBeneficiaryVitalDetails(beneficiaryRegID, visitCode);

		// Assert
		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("benAnthropometryDetail", benAnthropometryDetail);
		expectedMap.put("benPhysicalVitalDetail", null);
		String expectedJson = new Gson().toJson(expectedMap);

		assertEquals(expectedJson, result);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID,
				visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetBeneficiaryVitalDetails_NoDetails() {
		// Arrange
		when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, visitCode))
				.thenReturn(null);
		when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode)).thenReturn(null);

		// Act
		String result = covid19ServiceImpl.getBeneficiaryVitalDetails(beneficiaryRegID, visitCode);

		// Assert
		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("benAnthropometryDetail", null);
		expectedMap.put("benPhysicalVitalDetail", null);
		String expectedJson = new Gson().toJson(expectedMap);

		assertEquals(expectedJson, result);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID,
				visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetBeneficiaryCdssDetails() {
		// Arrange
		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("presentChiefComplaint", "Chief Complaint Data");
		expectedMap.put("diseaseSummary", "Disease Summary Data");

		when(commonNurseServiceImpl.getBenCdssDetails(benRegID, benVisitID)).thenReturn("Chief Complaint Data");
		when(commonNurseServiceImpl.getBenCdssDetails(benRegID, benVisitID)).thenReturn("Disease Summary Data");

		// Act
		String result = covid19ServiceImpl.getBeneficiaryCdssDetails(benRegID, benVisitID);

		// Assert
		assertNotNull(result);
		assertTrue(result.contains("presentChiefComplaint"));
		assertTrue(result.contains("diseaseSummary"));
		verify(commonNurseServiceImpl, times(2)).getBenCdssDetails(benRegID, benVisitID);
	}

	@Test
	public void testSaveCovidDetails_Success() {
		when(covid19BenFeedbackRepo.save(any(Covid19BenFeedback.class))).thenReturn(covid19BenFeedback);

		Integer result = covid19ServiceImpl.saveCovidDetails(covid19BenFeedback);

		assertNotNull(result);
		assertEquals(1, result);
		verify(covid19BenFeedbackRepo, times(1)).save(any(Covid19BenFeedback.class));
	}

	@Test
	public void testSaveCovidDetails_EmptySymptoms() {
		covid19BenFeedback.setSymptoms(new String[] {});

		when(covid19BenFeedbackRepo.save(any(Covid19BenFeedback.class))).thenReturn(covid19BenFeedback);

		Integer result = covid19ServiceImpl.saveCovidDetails(covid19BenFeedback);

		assertNotNull(result);
		assertEquals(1, result);
		verify(covid19BenFeedbackRepo, times(1)).save(any(Covid19BenFeedback.class));
	}

	@Test
	public void testSaveCovidDetails_EmptyContactStatus() {
		covid19BenFeedback.setContactStatus(new String[] {});

		when(covid19BenFeedbackRepo.save(any(Covid19BenFeedback.class))).thenReturn(covid19BenFeedback);

		Integer result = covid19ServiceImpl.saveCovidDetails(covid19BenFeedback);

		assertNotNull(result);
		assertEquals(1, result);
		verify(covid19BenFeedbackRepo, times(1)).save(any(Covid19BenFeedback.class));
	}

	@Test
	public void testSaveCovidDetails_EmptyTravelList() {
		covid19BenFeedback.setTravelList(new String[] {});

		when(covid19BenFeedbackRepo.save(any(Covid19BenFeedback.class))).thenReturn(covid19BenFeedback);

		Integer result = covid19ServiceImpl.saveCovidDetails(covid19BenFeedback);

		assertNotNull(result);
		assertEquals(1, result);
		verify(covid19BenFeedbackRepo, times(1)).save(any(Covid19BenFeedback.class));
	}

	@Test
	public void testSaveCovidDetails_EmptyRecommendation() {
		covid19BenFeedback.setRecommendation(new ArrayList<>());

		when(covid19BenFeedbackRepo.save(any(Covid19BenFeedback.class))).thenReturn(covid19BenFeedback);

		Integer result = covid19ServiceImpl.saveCovidDetails(covid19BenFeedback);

		assertNotNull(result);
		assertEquals(1, result);
		verify(covid19BenFeedbackRepo, times(1)).save(any(Covid19BenFeedback.class));
	}

	@Test
	public void testSaveCovidDetails_SuspectedStatusYes() {
		covid19BenFeedback.setSuspectedStatusUI("YES");

		when(covid19BenFeedbackRepo.save(any(Covid19BenFeedback.class))).thenReturn(covid19BenFeedback);

		Integer result = covid19ServiceImpl.saveCovidDetails(covid19BenFeedback);

		assertNotNull(result);
		assertEquals(1, result);
		verify(covid19BenFeedbackRepo, times(1)).save(any(Covid19BenFeedback.class));
	}

	@Test
	public void testSaveCovidDetails_SuspectedStatusNo() {
		covid19BenFeedback.setSuspectedStatusUI("NO");

		when(covid19BenFeedbackRepo.save(any(Covid19BenFeedback.class))).thenReturn(covid19BenFeedback);

		Integer result = covid19ServiceImpl.saveCovidDetails(covid19BenFeedback);

		assertNotNull(result);
		assertEquals(1, result);
		verify(covid19BenFeedbackRepo, times(1)).save(any(Covid19BenFeedback.class));
	}

	@Test
	public void testUpdateBenHistoryDetails_Success() throws Exception {
		when(commonNurseServiceImpl.updateBenPastHistoryDetails(any())).thenReturn(1);
		when(commonNurseServiceImpl.updateBenComorbidConditions(any())).thenReturn(1);
		when(commonNurseServiceImpl.updateBenMedicationHistory(any())).thenReturn(1);
		when(commonNurseServiceImpl.updateBenPersonalHistory(any())).thenReturn(1);
		when(commonNurseServiceImpl.updateBenAllergicHistory(any())).thenReturn(1);
		when(commonNurseServiceImpl.updateBenFamilyHistory(any())).thenReturn(1);
		when(commonNurseServiceImpl.updateMenstrualHistory(any())).thenReturn(1);
		when(commonNurseServiceImpl.updatePastObstetricHistory(any())).thenReturn(1);
		when(commonNurseServiceImpl.updateChildImmunizationDetail(any())).thenReturn(1);
		when(commonNurseServiceImpl.updateChildOptionalVaccineDetail(any())).thenReturn(1);
		when(commonNurseServiceImpl.updateChildFeedingHistory(any())).thenReturn(1);
		when(commonNurseServiceImpl.updatePerinatalHistory(any())).thenReturn(1);
		when(commonNurseServiceImpl.updateChildDevelopmentHistory(any())).thenReturn(1);

		int result = covid19ServiceImpl.updateBenHistoryDetails(historyOBJ);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).updateBenPastHistoryDetails(any());
		verify(commonNurseServiceImpl, times(1)).updateBenComorbidConditions(any());
		verify(commonNurseServiceImpl, times(1)).updateBenMedicationHistory(any());
		verify(commonNurseServiceImpl, times(1)).updateBenPersonalHistory(any());
		verify(commonNurseServiceImpl, times(1)).updateBenAllergicHistory(any());
		verify(commonNurseServiceImpl, times(1)).updateBenFamilyHistory(any());
		verify(commonNurseServiceImpl, times(1)).updateMenstrualHistory(any());
		verify(commonNurseServiceImpl, times(1)).updatePastObstetricHistory(any());
		verify(commonNurseServiceImpl, times(1)).updateChildImmunizationDetail(any());
		verify(commonNurseServiceImpl, times(1)).updateChildOptionalVaccineDetail(any());
		verify(commonNurseServiceImpl, times(1)).updateChildFeedingHistory(any());
		verify(commonNurseServiceImpl, times(1)).updatePerinatalHistory(any());
		verify(commonNurseServiceImpl, times(1)).updateChildDevelopmentHistory(any());
	}

	@Test
	public void testUpdateBenHistoryDetails_Failure() throws Exception {
		when(commonNurseServiceImpl.updateBenPastHistoryDetails(any())).thenReturn(0);
		when(commonNurseServiceImpl.updateBenComorbidConditions(any())).thenReturn(0);
		when(commonNurseServiceImpl.updateBenMedicationHistory(any())).thenReturn(0);
		when(commonNurseServiceImpl.updateBenPersonalHistory(any())).thenReturn(0);
		when(commonNurseServiceImpl.updateBenAllergicHistory(any())).thenReturn(0);
		when(commonNurseServiceImpl.updateBenFamilyHistory(any())).thenReturn(0);
		when(commonNurseServiceImpl.updateMenstrualHistory(any())).thenReturn(0);
		when(commonNurseServiceImpl.updatePastObstetricHistory(any())).thenReturn(0);
		when(commonNurseServiceImpl.updateChildImmunizationDetail(any())).thenReturn(0);
		when(commonNurseServiceImpl.updateChildOptionalVaccineDetail(any())).thenReturn(0);
		when(commonNurseServiceImpl.updateChildFeedingHistory(any())).thenReturn(0);
		when(commonNurseServiceImpl.updatePerinatalHistory(any())).thenReturn(0);
		when(commonNurseServiceImpl.updateChildDevelopmentHistory(any())).thenReturn(0);

		int result = covid19ServiceImpl.updateBenHistoryDetails(historyOBJ);

		assertEquals(0, result);
		verify(commonNurseServiceImpl, times(1)).updateBenPastHistoryDetails(any());
		verify(commonNurseServiceImpl, times(1)).updateBenComorbidConditions(any());
		verify(commonNurseServiceImpl, times(1)).updateBenMedicationHistory(any());
		verify(commonNurseServiceImpl, times(1)).updateBenPersonalHistory(any());
		verify(commonNurseServiceImpl, times(1)).updateBenAllergicHistory(any());
		verify(commonNurseServiceImpl, times(1)).updateBenFamilyHistory(any());
		verify(commonNurseServiceImpl, times(1)).updateMenstrualHistory(any());
		verify(commonNurseServiceImpl, times(1)).updatePastObstetricHistory(any());
		verify(commonNurseServiceImpl, times(1)).updateChildImmunizationDetail(any());
		verify(commonNurseServiceImpl, times(1)).updateChildOptionalVaccineDetail(any());
		verify(commonNurseServiceImpl, times(1)).updateChildFeedingHistory(any());
		verify(commonNurseServiceImpl, times(1)).updatePerinatalHistory(any());
		verify(commonNurseServiceImpl, times(1)).updateChildDevelopmentHistory(any());
	}

	@Test
	public void testUpdateBenVitalDetails_NullVitalDetailsOBJ() throws Exception {
		int result = covid19ServiceImpl.updateBenVitalDetails(null);
		assertEquals(1, result);
	}

	@Test
	public void testUpdateBenVitalDetails_ValidVitalDetailsOBJ() throws Exception {
		BenAnthropometryDetail benAnthropometryDetail = new BenAnthropometryDetail();
		BenPhysicalVitalDetail benPhysicalVitalDetail = new BenPhysicalVitalDetail();

		when(commonNurseServiceImpl.updateANCAnthropometryDetails(any(BenAnthropometryDetail.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class))).thenReturn(1);

		int result = covid19ServiceImpl.updateBenVitalDetails(validVitalDetailsOBJ);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(1)).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testUpdateBenVitalDetails_InvalidVitalDetailsOBJ() throws Exception {
		BenAnthropometryDetail benAnthropometryDetail = new BenAnthropometryDetail();
		BenPhysicalVitalDetail benPhysicalVitalDetail = new BenPhysicalVitalDetail();

		when(commonNurseServiceImpl.updateANCAnthropometryDetails(any(BenAnthropometryDetail.class))).thenReturn(0);
		when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class))).thenReturn(0);

		int result = covid19ServiceImpl.updateBenVitalDetails(invalidVitalDetailsOBJ);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(1)).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testGetBenCovidNurseData() throws IEMRException {
		String expectedResponse = new Gson().toJson(expectedResponseMap);

		String actualResponse = covid19ServiceImpl.getBenCovidNurseData(benRegID, visitCode);

		assertEquals(expectedResponse, actualResponse);

		verify(commonNurseServiceImpl, times(1)).getBeneficiaryVitalDetails(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenCdssDetails(benRegID, visitCode);
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
	public void testSaveDoctorData_Success() throws Exception {
		// Setup
		CommonUtilityClass commonUtilityClass = new CommonUtilityClass();
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
				.thenReturn(null);
		when(commonDoctorServiceImpl.saveDocFindings(any())).thenReturn(1);
		when(commonNurseServiceImpl.savePrescriptionCovid(anyLong(), anyLong(), anyLong(), anyString(), any(),
				anyLong(), anyInt(), anyInt(), anyString(), anyString(), anyString())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenInvestigation(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenPrescribedDrugsList(anyList())).thenReturn(1);
		when(commonDoctorServiceImpl.saveBenReferDetails(any(JsonObject.class), eq(false))).thenReturn(1L);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataSave(any(CommonUtilityClass.class), anyBoolean(),
				anyBoolean(), any())).thenReturn(1);

		// Execute
		Long result = covid19ServiceImpl.saveDoctorData(requestOBJ, authorization);

		// Verify
		assertNotNull(result);
		assertEquals(1L, result);
		verify(commonDoctorServiceImpl, times(1)).saveDocFindings(any());
		verify(commonNurseServiceImpl, times(1)).savePrescriptionCovid(anyLong(), anyLong(), anyLong(), anyString(),
				any(), anyLong(), anyInt(), anyInt(), anyString(), anyString(), anyString());
		verify(commonNurseServiceImpl, times(1)).saveBenInvestigation(any());
		verify(commonNurseServiceImpl, times(1)).saveBenPrescribedDrugsList(anyList());
		verify(commonDoctorServiceImpl, times(1)).saveBenReferDetails(any(JsonObject.class), eq(false));
		verify(commonDoctorServiceImpl, times(1)).updateBenFlowtableAfterDocDataSave(any(CommonUtilityClass.class),
				anyBoolean(), anyBoolean(), any());
	}

	@Test
	public void testSaveDoctorData_NullRequest() throws Exception {
		// Execute
		Long result = covid19ServiceImpl.saveDoctorData(null, authorization);

		// Verify
		assertNull(result);
		verify(commonDoctorServiceImpl, times(0)).saveDocFindings(any());
		verify(commonNurseServiceImpl, times(0)).savePrescriptionCovid(anyLong(), anyLong(), anyLong(), anyString(),
				any(), anyLong(), anyInt(), anyInt(), anyString(), anyString(), anyString());
		verify(commonNurseServiceImpl, times(0)).saveBenInvestigation(any());
		verify(commonNurseServiceImpl, times(0)).saveBenPrescribedDrugsList(anyList());
		verify(commonDoctorServiceImpl, times(0)).saveBenReferDetails(any(JsonObject.class), eq(false));
		verify(commonDoctorServiceImpl, times(0)).updateBenFlowtableAfterDocDataSave(any(CommonUtilityClass.class),
				anyBoolean(), anyBoolean(), any());
	}

	@Test
	public void testSaveDoctorData_Exception() throws Exception {
		// Setup
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
				.thenThrow(new RuntimeException("Error"));

		// Execute & Verify
		assertThrows(RuntimeException.class, () -> {
			covid19ServiceImpl.saveDoctorData(requestOBJ, authorization);
		});

		verify(commonDoctorServiceImpl, times(0)).saveDocFindings(any());
		verify(commonNurseServiceImpl, times(0)).savePrescriptionCovid(anyLong(), anyLong(), anyLong(), anyString(),
				any(), anyLong(), anyInt(), anyInt(), anyString(), anyString(), anyString());
		verify(commonNurseServiceImpl, times(0)).saveBenInvestigation(any());
		verify(commonNurseServiceImpl, times(0)).saveBenPrescribedDrugsList(anyList());
		verify(commonDoctorServiceImpl, times(0)).saveBenReferDetails(any(JsonObject.class), eq(false));
		verify(commonDoctorServiceImpl, times(0)).updateBenFlowtableAfterDocDataSave(any(CommonUtilityClass.class),
				anyBoolean(), anyBoolean(), any());
	}

	@Test
	public void testGetCovidDiagnosisData_ValidData() {
		ArrayList<PrescriptionDetail> prescriptionDetails = new ArrayList<>();
		prescriptionDetails.add(prescriptionDetail);

		when(prescriptionDetailRepo.findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(benRegID, visitCode))
				.thenReturn(prescriptionDetails);

		String result = covid19ServiceImpl.getCovidDiagnosisData(benRegID, visitCode);

		assertNotNull(result);
		assertTrue(result.contains("Diagnosis"));
		assertTrue(result.contains("Instruction"));
		assertTrue(result.contains("External Investigation"));
		assertTrue(result.contains("Counselling Provided"));
	}

	@Test
	public void testGetCovidDiagnosisData_NullData() {
		when(prescriptionDetailRepo.findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(benRegID, visitCode))
				.thenReturn(null);

		String result = covid19ServiceImpl.getCovidDiagnosisData(benRegID, visitCode);

		assertNotNull(result);
		assertTrue(result.contains("\"doctorDiagnonsis\":null"));
		assertTrue(result.contains("\"specialistDiagnosis\":null"));
		assertTrue(result.contains("\"externalInvestigation\":null"));
		assertTrue(result.contains("\"prescriptionID\":null"));
	}

	@Test
	public void testGetCovidDiagnosisData_EmptyData() {
		when(prescriptionDetailRepo.findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(benRegID, visitCode))
				.thenReturn(new ArrayList<>());

		String result = covid19ServiceImpl.getCovidDiagnosisData(benRegID, visitCode);

		assertNotNull(result);
		assertTrue(result.contains("\"doctorDiagnonsis\":null"));
		assertTrue(result.contains("\"specialistDiagnosis\":null"));
		assertTrue(result.contains("\"externalInvestigation\":null"));
		assertTrue(result.contains("\"prescriptionID\":null"));
	}

	@Test
	public void testUpdateCovid19DoctorData_Success() throws Exception {
		// Set up the request object with necessary fields
		requestOBJ.addProperty("someField", "someValue");
		JsonArray investigationArray = new JsonArray();
		requestOBJ.add("investigation", investigationArray);
		JsonArray prescriptionArray = new JsonArray();
		requestOBJ.add("prescription", prescriptionArray);

		CommonUtilityClass commonUtilityClass = new CommonUtilityClass();
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
				.thenReturn(null);
		when(commonNurseServiceImpl.saveBenInvestigation(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenPrescribedDrugsList(any())).thenReturn(1);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(any(), anyBoolean(), anyBoolean(), any()))
				.thenReturn(1);

		Long result = covid19ServiceImpl.updateCovid19DoctorData(requestOBJ, authorization);

		assertNotNull(result);
		verify(commonNurseServiceImpl, times(1)).saveBenInvestigation(any());
		verify(commonNurseServiceImpl, times(1)).saveBenPrescribedDrugsList(any());
		verify(commonDoctorServiceImpl, times(1)).updateBenFlowtableAfterDocDataUpdate(any(), anyBoolean(),
				anyBoolean(), any());
	}

	@Test
	public void testUpdateCovid19DoctorData_NullRequest() throws Exception {
		Long result = covid19ServiceImpl.updateCovid19DoctorData(null, authorization);
		assertNull(result);
	}

	@Test
	public void testUpdateCovid19DoctorData_MissingFields() throws Exception {
		// Set up the request object with missing fields
		requestOBJ.addProperty("someField", "someValue");

		CommonUtilityClass commonUtilityClass = new CommonUtilityClass();
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
				.thenReturn(null);
		when(commonNurseServiceImpl.saveBenInvestigation(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenPrescribedDrugsList(any())).thenReturn(1);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(any(), anyBoolean(), anyBoolean(), any()))
				.thenReturn(1);

		Long result = covid19ServiceImpl.updateCovid19DoctorData(requestOBJ, authorization);

		assertNotNull(result);
		verify(commonNurseServiceImpl, times(1)).saveBenInvestigation(any());
		verify(commonNurseServiceImpl, times(1)).saveBenPrescribedDrugsList(any());
		verify(commonDoctorServiceImpl, times(1)).updateBenFlowtableAfterDocDataUpdate(any(), anyBoolean(),
				anyBoolean(), any());
	}
}
