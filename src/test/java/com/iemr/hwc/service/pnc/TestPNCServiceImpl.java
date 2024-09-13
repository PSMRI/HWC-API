package com.iemr.hwc.service.pnc;

import static org.mockito.Mockito.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.data.nurse.BenAnthropometryDetail;
import com.iemr.hwc.data.nurse.BenPhysicalVitalDetail;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.pnc.PNCCare;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.service.nurse.CommonNurseServiceImpl;
import com.iemr.hwc.service.doctor.CommonDoctorServiceImpl;
import com.iemr.hwc.service.labtechnician.LabTechnicianServiceImpl;
import com.iemr.hwc.service.teleconsultation.TeleConsultationServiceImpl;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.common.CommonServiceImpl;
import com.iemr.hwc.service.sms.SMSGatewayServiceImpl;
import com.iemr.hwc.repository.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repository.BenVisitDetailRepo;
import com.iemr.hwc.repository.BenChiefComplaintRepo;
import com.iemr.hwc.repository.BenAdherenceRepo;
import com.iemr.hwc.repository.CDSSRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

public class TestPNCServiceImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private CommonNurseServiceImpl commonNurseServiceImpl;
	@Mock
	private CommonDoctorServiceImpl commonDoctorServiceImpl;
	@Mock
	private PNCNurseServiceImpl pncNurseServiceImpl;
	@Mock
	private PNCDoctorServiceImpl pncDoctorServiceImpl;
	@Mock
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;
	@Mock
	private LabTechnicianServiceImpl labTechnicianServiceImpl;
	@Mock
	private TeleConsultationServiceImpl teleConsultationServiceImpl;
	@Mock
	private CommonServiceImpl commonServiceImpl;
	@Mock
	private SMSGatewayServiceImpl sMSGatewayServiceImpl;
	@Mock
	private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;
	@Mock
	private BenVisitDetailRepo benVisitDetailRepo;
	@Mock
	private BenChiefComplaintRepo benChiefComplaintRepo;
	@Mock
	private BenAdherenceRepo benAdherenceRepo;
	@Mock
	private CDSSRepo cdssRepo;

	@InjectMocks
	private PNCServiceImpl pncServiceImpl;

	private JsonObject requestObj;
	private String authorization;
	private JsonObject tmpOBJ;
	private Long benVisitID;
	private Long benFlowID;
	private Long benVisitCode;
	private Integer vanID;
	private TeleconsultationRequestOBJ tcRequestOBJ;
	private JsonObject visitDetailsOBJ;
	private CommonUtilityClass nurseUtilityClass;
	private JsonObject pncHistoryOBJ;
	private JsonObject validPncDetailsObj;
	private JsonObject vitalDetailsOBJ;
	private JsonObject validExaminationDetails;
	private JsonObject partialExaminationDetails;
	private JsonObject nullExaminationDetails;
	private Long benRegID;
	private Long visitCode;
	private BeneficiaryVisitDetail visitDetail;
	private Gson gson;
	private Map<String, Object> historyDetailsMap;
	private Long beneficiaryRegID;

	private Map<String, Object> expectedResponse;
	private Map<String, Object> cdssDetails;
	private JsonObject historyOBJ;
	private JsonObject examinationDetailsOBJ;
	private JsonObject pncDetailsOBJ;

	@BeforeEach
	public void setUp() {
		requestObj = new JsonObject();
		requestObj.addProperty("key", "value");
		authorization = "Bearer token";
	}

	@Test
	public void testSavePNCNurseData_Success() throws Exception {
		// Setup mock responses
		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any())).thenReturn(1L);
		when(commonNurseServiceImpl.generateVisitCode(anyLong(), anyInt(), anyInt())).thenReturn(1L);
		when(pncNurseServiceImpl.saveBenPncCareDetails(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenChiefComplaints(any())).thenReturn(1);
		when(commonNurseServiceImpl.saveBenAdherenceDetails(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveCdssDetails(any())).thenReturn(1L);
		when(commonServiceImpl.createTcRequest(any(), any(), anyString())).thenReturn(null);
		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(), anyLong(),
				anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(),
				anyInt(), anyShort(), any(), any())).thenReturn(1);

		String response = pncServiceImpl.savePNCNurseData(requestObj, authorization);

		assertTrue(response.contains("Data saved successfully"));
		verify(commonNurseServiceImpl, times(1)).saveBeneficiaryVisitDetails(any());
		verify(pncNurseServiceImpl, times(1)).saveBenPncCareDetails(any());
	}

	@Test
	public void testSavePNCNurseData_InvalidInput() {
		Exception exception = assertThrows(Exception.class, () -> {
			pncServiceImpl.savePNCNurseData(null, authorization);
		});

		assertEquals("Invalid input", exception.getMessage());
	}

	@Test
	public void testSavePNCNurseData_DataAlreadySaved() throws Exception {
		// Setup mock responses
		when(commonNurseServiceImpl.getMaxCurrentdate(anyLong(), anyString(), anyString())).thenReturn(1);

		String response = pncServiceImpl.savePNCNurseData(requestObj, authorization);

		assertTrue(response.contains("Data already saved"));
	}

	@Test
	public void testSavePNCNurseData_SaveFailure() throws Exception {
		// Setup mock responses
		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any())).thenReturn(1L);
		when(commonNurseServiceImpl.generateVisitCode(anyLong(), anyInt(), anyInt())).thenReturn(1L);
		when(pncNurseServiceImpl.saveBenPncCareDetails(any())).thenReturn(0L);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			pncServiceImpl.savePNCNurseData(requestObj, authorization);
		});

		assertEquals("Error occurred while saving data", exception.getMessage());
	}

	@Test
	public void testUpdateBenStatusFlagAfterNurseSaveSuccess_WithTcRequest() {
		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(), anyLong(),
				anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(),
				anyInt(), anyShort(), any(Timestamp.class), anyInt())).thenReturn(1);

		int result = pncServiceImpl.updateBenStatusFlagAfterNurseSaveSuccess(tmpOBJ, benVisitID, benFlowID,
				benVisitCode, vanID, tcRequestOBJ);

		assertEquals(1, result);
		verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseActivity(eq(benFlowID), eq(1L),
				eq(benVisitID), eq("Routine Checkup"), eq("General"), eq((short) 9), eq((short) 1), eq((short) 0),
				eq((short) 0), eq((short) 0), eq(benVisitCode), eq(vanID), eq((short) 1),
				eq(tcRequestOBJ.getAllocationDate()), eq(tcRequestOBJ.getUserID()));
	}

	@Test
	public void testUpdateBenStatusFlagAfterNurseSaveSuccess_WithoutTcRequest() {
		tcRequestOBJ = null;

		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(), anyLong(),
				anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(),
				anyInt(), anyShort(), any(Timestamp.class), anyInt())).thenReturn(1);

		int result = pncServiceImpl.updateBenStatusFlagAfterNurseSaveSuccess(tmpOBJ, benVisitID, benFlowID,
				benVisitCode, vanID, tcRequestOBJ);

		assertEquals(1, result);
		verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseActivity(eq(benFlowID), eq(1L),
				eq(benVisitID), eq("Routine Checkup"), eq("General"), eq((short) 9), eq((short) 1), eq((short) 0),
				eq((short) 0), eq((short) 0), eq(benVisitCode), eq(vanID), eq((short) 0), isNull(), isNull());
	}

	@Test
	public void testUpdateBenStatusFlagAfterNurseSaveSuccess_Failure() {
		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(), anyLong(),
				anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(),
				anyInt(), anyShort(), any(Timestamp.class), anyInt())).thenReturn(0);

		int result = pncServiceImpl.updateBenStatusFlagAfterNurseSaveSuccess(tmpOBJ, benVisitID, benFlowID,
				benVisitCode, vanID, tcRequestOBJ);

		assertEquals(0, result);
		verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseActivity(eq(benFlowID), eq(1L),
				eq(benVisitID), eq("Routine Checkup"), eq("General"), eq((short) 9), eq((short) 1), eq((short) 0),
				eq((short) 0), eq((short) 0), eq(benVisitCode), eq(vanID), eq((short) 1),
				eq(tcRequestOBJ.getAllocationDate()), eq(tcRequestOBJ.getUserID()));
	}

	@Test
	public void testSavePNCDoctorData_Success() throws Exception {
		// Mocking dependencies
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
				.thenReturn(new TeleconsultationRequestOBJ());
		when(commonNurseServiceImpl.savePrescriptionDetailsAndGetPrescriptionID(anyLong(), anyLong(), anyLong(),
				anyString(), any(), anyLong(), anyInt(), anyInt(), anyString(), anyString(), any())).thenReturn(1L);
		when(commonDoctorServiceImpl.saveDocFindings(any())).thenReturn(1);
		when(pncDoctorServiceImpl.saveBenPNCDiagnosis(any(JsonObject.class), anyLong())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenInvestigation(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenPrescribedDrugsList(anyList())).thenReturn(1);
		when(commonDoctorServiceImpl.saveBenReferDetails(any(JsonObject.class), anyBoolean())).thenReturn(1L);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataSave(any(CommonUtilityClass.class), anyBoolean(),
				anyBoolean(), any())).thenReturn(1);

		Long result = pncServiceImpl.savePNCDoctorData(requestObj, authorization);

		assertNotNull(result);
		assertEquals(1L, result);
	}

	@Test
	public void testSavePNCDoctorData_NullRequest() throws Exception {
		Long result = pncServiceImpl.savePNCDoctorData(null, authorization);
		assertNull(result);
	}

	@Test
	public void testSavePNCDoctorData_Exception() {
		assertThrows(RuntimeException.class, () -> {
			pncServiceImpl.savePNCDoctorData(requestObj, authorization);
		});
	}

	@Test
	public void testSaveBenVisitDetails_NullVisitDetails() throws Exception {
		Map<String, Long> result = pncServiceImpl.saveBenVisitDetails(null, nurseUtilityClass);
		assertTrue(result.isEmpty());
	}

	@Test
	public void testSaveBenVisitDetails_NoVisitDetails() throws Exception {
		visitDetailsOBJ.add("visitDetails", null);
		Map<String, Long> result = pncServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass);
		assertTrue(result.isEmpty());
	}

	@Test
	public void testSaveBenVisitDetails_ValidVisitDetails() throws Exception {
		JsonObject visitDetails = new JsonObject();
		visitDetails.addProperty("beneficiaryRegID", 1L);
		visitDetails.addProperty("visitReason", "Reason");
		visitDetails.addProperty("visitCategory", "Category");
		visitDetailsOBJ.add("visitDetails", visitDetails);

		when(commonNurseServiceImpl.getMaxCurrentdate(anyLong(), anyString(), anyString())).thenReturn(0);
		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any())).thenReturn(1L);
		when(commonNurseServiceImpl.generateVisitCode(anyLong(), anyInt(), anyInt())).thenReturn(1L);

		Map<String, Long> result = pncServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass);

		assertNotNull(result);
		assertEquals(1L, result.get("visitID"));
		assertEquals(1L, result.get("visitCode"));
	}

	@Test
	public void testSaveBenVisitDetails_WithChiefComplaints() throws Exception {
		JsonObject visitDetails = new JsonObject();
		visitDetails.addProperty("beneficiaryRegID", 1L);
		visitDetails.addProperty("visitReason", "Reason");
		visitDetails.addProperty("visitCategory", "Category");
		visitDetailsOBJ.add("visitDetails", visitDetails);

		JsonObject chiefComplaints = new JsonObject();
		visitDetailsOBJ.add("chiefComplaints", chiefComplaints);

		when(commonNurseServiceImpl.getMaxCurrentdate(anyLong(), anyString(), anyString())).thenReturn(0);
		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any())).thenReturn(1L);
		when(commonNurseServiceImpl.generateVisitCode(anyLong(), anyInt(), anyInt())).thenReturn(1L);

		Map<String, Long> result = pncServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass);

		assertNotNull(result);
		assertEquals(1L, result.get("visitID"));
		assertEquals(1L, result.get("visitCode"));
		verify(commonNurseServiceImpl, times(1)).saveBenChiefComplaints(anyList());
	}

	@Test
	public void testSaveBenVisitDetails_WithAdherence() throws Exception {
		JsonObject visitDetails = new JsonObject();
		visitDetails.addProperty("beneficiaryRegID", 1L);
		visitDetails.addProperty("visitReason", "Reason");
		visitDetails.addProperty("visitCategory", "Category");
		visitDetailsOBJ.add("visitDetails", visitDetails);

		JsonObject adherence = new JsonObject();
		visitDetailsOBJ.add("adherence", adherence);

		when(commonNurseServiceImpl.getMaxCurrentdate(anyLong(), anyString(), anyString())).thenReturn(0);
		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any())).thenReturn(1L);
		when(commonNurseServiceImpl.generateVisitCode(anyLong(), anyInt(), anyInt())).thenReturn(1L);

		Map<String, Long> result = pncServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass);

		assertNotNull(result);
		assertEquals(1L, result.get("visitID"));
		assertEquals(1L, result.get("visitCode"));
		verify(commonNurseServiceImpl, times(1)).saveBenAdherenceDetails(any());
	}

	@Test
	public void testSaveBenVisitDetails_WithCdss() throws Exception {
		JsonObject visitDetails = new JsonObject();
		visitDetails.addProperty("beneficiaryRegID", 1L);
		visitDetails.addProperty("visitReason", "Reason");
		visitDetails.addProperty("visitCategory", "Category");
		visitDetailsOBJ.add("visitDetails", visitDetails);

		JsonObject cdss = new JsonObject();
		visitDetailsOBJ.add("cdss", cdss);

		when(commonNurseServiceImpl.getMaxCurrentdate(anyLong(), anyString(), anyString())).thenReturn(0);
		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any())).thenReturn(1L);
		when(commonNurseServiceImpl.generateVisitCode(anyLong(), anyInt(), anyInt())).thenReturn(1L);

		Map<String, Long> result = pncServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass);

		assertNotNull(result);
		assertEquals(1L, result.get("visitID"));
		assertEquals(1L, result.get("visitCode"));
		verify(commonNurseServiceImpl, times(1)).saveCdssDetails(any());
	}

	@Test
	public void testSaveBenPNCHistoryDetails_success() throws Exception {
		// Mocking the behavior of commonNurseServiceImpl methods
		when(commonNurseServiceImpl.saveBenPastHistory(any(BenMedHistory.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenComorbidConditions(any(WrapperComorbidCondDetails.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenMedicationHistory(any(WrapperMedicationHistory.class))).thenReturn(1L);
		when(commonNurseServiceImpl.savePersonalHistory(any(BenPersonalHabit.class))).thenReturn(1);
		when(commonNurseServiceImpl.saveAllergyHistory(any(BenAllergyHistory.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenFamilyHistory(any(BenFamilyHistory.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenMenstrualHistory(any(BenMenstrualDetails.class))).thenReturn(1);
		when(commonNurseServiceImpl.saveFemaleObstetricHistory(any(WrapperFemaleObstetricHistory.class)))
				.thenReturn(1L);
		when(commonNurseServiceImpl.saveImmunizationHistory(any(WrapperImmunizationHistory.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveChildOptionalVaccineDetail(any(WrapperChildOptionalVaccineDetail.class)))
				.thenReturn(1L);

		// Call the method to test
		Long result = pncServiceImpl.saveBenPNCHistoryDetails(pncHistoryOBJ, benVisitID, benVisitCode);

		// Assertions
		assertNotNull(result);
		assertEquals(1L, result);

		// Verify interactions with mocks
		verify(commonNurseServiceImpl, times(1)).saveBenPastHistory(any(BenMedHistory.class));
		verify(commonNurseServiceImpl, times(1)).saveBenComorbidConditions(any(WrapperComorbidCondDetails.class));
		verify(commonNurseServiceImpl, times(1)).saveBenMedicationHistory(any(WrapperMedicationHistory.class));
		verify(commonNurseServiceImpl, times(1)).savePersonalHistory(any(BenPersonalHabit.class));
		verify(commonNurseServiceImpl, times(1)).saveAllergyHistory(any(BenAllergyHistory.class));
		verify(commonNurseServiceImpl, times(1)).saveBenFamilyHistory(any(BenFamilyHistory.class));
		verify(commonNurseServiceImpl, times(1)).saveBenMenstrualHistory(any(BenMenstrualDetails.class));
		verify(commonNurseServiceImpl, times(1)).saveFemaleObstetricHistory(any(WrapperFemaleObstetricHistory.class));
		verify(commonNurseServiceImpl, times(1)).saveImmunizationHistory(any(WrapperImmunizationHistory.class));
		verify(commonNurseServiceImpl, times(1))
				.saveChildOptionalVaccineDetail(any(WrapperChildOptionalVaccineDetail.class));
	}

	@Test
	public void testSaveBenPNCHistoryDetails_failure() throws Exception {
		// Mocking the behavior of commonNurseServiceImpl methods to return failure
		// flags
		when(commonNurseServiceImpl.saveBenPastHistory(any(BenMedHistory.class))).thenReturn(0L);
		when(commonNurseServiceImpl.saveBenComorbidConditions(any(WrapperComorbidCondDetails.class))).thenReturn(0L);
		when(commonNurseServiceImpl.saveBenMedicationHistory(any(WrapperMedicationHistory.class))).thenReturn(0L);
		when(commonNurseServiceImpl.savePersonalHistory(any(BenPersonalHabit.class))).thenReturn(0);
		when(commonNurseServiceImpl.saveAllergyHistory(any(BenAllergyHistory.class))).thenReturn(0L);
		when(commonNurseServiceImpl.saveBenFamilyHistory(any(BenFamilyHistory.class))).thenReturn(0L);
		when(commonNurseServiceImpl.saveBenMenstrualHistory(any(BenMenstrualDetails.class))).thenReturn(0);
		when(commonNurseServiceImpl.saveFemaleObstetricHistory(any(WrapperFemaleObstetricHistory.class)))
				.thenReturn(0L);
		when(commonNurseServiceImpl.saveImmunizationHistory(any(WrapperImmunizationHistory.class))).thenReturn(0L);
		when(commonNurseServiceImpl.saveChildOptionalVaccineDetail(any(WrapperChildOptionalVaccineDetail.class)))
				.thenReturn(0L);

		// Call the method to test
		Long result = pncServiceImpl.saveBenPNCHistoryDetails(pncHistoryOBJ, benVisitID, benVisitCode);

		// Assertions
		assertNull(result);

		// Verify interactions with mocks
		verify(commonNurseServiceImpl, times(1)).saveBenPastHistory(any(BenMedHistory.class));
		verify(commonNurseServiceImpl, times(1)).saveBenComorbidConditions(any(WrapperComorbidCondDetails.class));
		verify(commonNurseServiceImpl, times(1)).saveBenMedicationHistory(any(WrapperMedicationHistory.class));
		verify(commonNurseServiceImpl, times(1)).savePersonalHistory(any(BenPersonalHabit.class));
		verify(commonNurseServiceImpl, times(1)).saveAllergyHistory(any(BenAllergyHistory.class));
		verify(commonNurseServiceImpl, times(1)).saveBenFamilyHistory(any(BenFamilyHistory.class));
		verify(commonNurseServiceImpl, times(1)).saveBenMenstrualHistory(any(BenMenstrualDetails.class));
		verify(commonNurseServiceImpl, times(1)).saveFemaleObstetricHistory(any(WrapperFemaleObstetricHistory.class));
		verify(commonNurseServiceImpl, times(1)).saveImmunizationHistory(any(WrapperImmunizationHistory.class));
		verify(commonNurseServiceImpl, times(1))
				.saveChildOptionalVaccineDetail(any(WrapperChildOptionalVaccineDetail.class));
	}

	@Test
	public void testSaveBenPNCDetails_ValidInput() throws Exception {
		PNCCare pncCare = new PNCCare();
		when(pncNurseServiceImpl.saveBenPncCareDetails(any(PNCCare.class))).thenReturn(1L);

		Long result = pncServiceImpl.saveBenPNCDetails(validPncDetailsObj, benVisitID, benVisitCode);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(pncNurseServiceImpl, times(1)).saveBenPncCareDetails(any(PNCCare.class));
	}

	@Test
	public void testSaveBenPNCDetails_MissingPNCDetails() throws Exception {
		JsonObject missingPncDetailsObj = JsonParser.parseString("{}").getAsJsonObject();

		Long result = pncServiceImpl.saveBenPNCDetails(missingPncDetailsObj, benVisitID, benVisitCode);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(pncNurseServiceImpl, never()).saveBenPncCareDetails(any(PNCCare.class));
	}

	@Test
	public void testSaveBenPNCDetails_NullPNCDetails() throws Exception {
		JsonObject nullPncDetailsObj = JsonParser.parseString("{\"pNCDeatils\": null}").getAsJsonObject();

		Long result = pncServiceImpl.saveBenPNCDetails(nullPncDetailsObj, benVisitID, benVisitCode);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(pncNurseServiceImpl, never()).saveBenPncCareDetails(any(PNCCare.class));
	}

	@Test
	public void testSaveBenPNCVitalDetails_NullVitalDetailsOBJ() throws Exception {
		Long result = pncServiceImpl.saveBenPNCVitalDetails(null, benVisitID, benVisitCode);
		assertNull(result);
	}

	@Test
	public void testSaveBenPNCVitalDetails_Success() throws Exception {
		BenAnthropometryDetail benAnthropometryDetail = new BenAnthropometryDetail();
		BenPhysicalVitalDetail benPhysicalVitalDetail = new BenPhysicalVitalDetail();

		when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(any(BenAnthropometryDetail.class)))
				.thenReturn(1L);
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(any(BenPhysicalVitalDetail.class)))
				.thenReturn(1L);

		Long result = pncServiceImpl.saveBenPNCVitalDetails(vitalDetailsOBJ, benVisitID, benVisitCode);

		assertNotNull(result);
		assertEquals(1L, result);
	}

	@Test
	public void testSaveBenPNCVitalDetails_Failure() throws Exception {
		BenAnthropometryDetail benAnthropometryDetail = new BenAnthropometryDetail();
		BenPhysicalVitalDetail benPhysicalVitalDetail = new BenPhysicalVitalDetail();

		when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(any(BenAnthropometryDetail.class)))
				.thenReturn(0L);
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(any(BenPhysicalVitalDetail.class)))
				.thenReturn(0L);

		Long result = pncServiceImpl.saveBenPNCVitalDetails(vitalDetailsOBJ, benVisitID, benVisitCode);

		assertNull(result);
	}

	@Test
	public void testSaveBenExaminationDetails_withValidDetails() throws Exception {
		when(commonNurseServiceImpl.savePhyGeneralExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.savePhyHeadToToeExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysGastrointestinalExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysCardiovascularExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysRespiratoryExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysCentralNervousExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysMusculoskeletalSystemExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysGenitourinarySystemExamination(any())).thenReturn(1L);

		Long result = pncServiceImpl.saveBenExaminationDetails(validExaminationDetails, 1L, 1L);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(commonNurseServiceImpl, times(1)).savePhyGeneralExamination(any());
		verify(commonNurseServiceImpl, times(1)).savePhyHeadToToeExamination(any());
		verify(commonNurseServiceImpl, times(1)).saveSysGastrointestinalExamination(any());
		verify(commonNurseServiceImpl, times(1)).saveSysCardiovascularExamination(any());
		verify(commonNurseServiceImpl, times(1)).saveSysRespiratoryExamination(any());
		verify(commonNurseServiceImpl, times(1)).saveSysCentralNervousExamination(any());
		verify(commonNurseServiceImpl, times(1)).saveSysMusculoskeletalSystemExamination(any());
		verify(commonNurseServiceImpl, times(1)).saveSysGenitourinarySystemExamination(any());
	}

	@Test
	public void testSaveBenExaminationDetails_withPartialDetails() throws Exception {
		when(commonNurseServiceImpl.savePhyGeneralExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.savePhyHeadToToeExamination(any())).thenReturn(1L);

		Long result = pncServiceImpl.saveBenExaminationDetails(partialExaminationDetails, 1L, 1L);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(commonNurseServiceImpl, times(1)).savePhyGeneralExamination(any());
		verify(commonNurseServiceImpl, times(1)).savePhyHeadToToeExamination(any());
		verify(commonNurseServiceImpl, times(0)).saveSysGastrointestinalExamination(any());
		verify(commonNurseServiceImpl, times(0)).saveSysCardiovascularExamination(any());
		verify(commonNurseServiceImpl, times(0)).saveSysRespiratoryExamination(any());
		verify(commonNurseServiceImpl, times(0)).saveSysCentralNervousExamination(any());
		verify(commonNurseServiceImpl, times(0)).saveSysMusculoskeletalSystemExamination(any());
		verify(commonNurseServiceImpl, times(0)).saveSysGenitourinarySystemExamination(any());
	}

	@Test
	public void testSaveBenExaminationDetails_withNullDetails() throws Exception {
		Long result = pncServiceImpl.saveBenExaminationDetails(nullExaminationDetails, 1L, 1L);

		assertNull(result);
		verify(commonNurseServiceImpl, times(0)).savePhyGeneralExamination(any());
		verify(commonNurseServiceImpl, times(0)).savePhyHeadToToeExamination(any());
		verify(commonNurseServiceImpl, times(0)).saveSysGastrointestinalExamination(any());
		verify(commonNurseServiceImpl, times(0)).saveSysCardiovascularExamination(any());
		verify(commonNurseServiceImpl, times(0)).saveSysRespiratoryExamination(any());
		verify(commonNurseServiceImpl, times(0)).saveSysCentralNervousExamination(any());
		verify(commonNurseServiceImpl, times(0)).saveSysMusculoskeletalSystemExamination(any());
		verify(commonNurseServiceImpl, times(0)).saveSysGenitourinarySystemExamination(any());
	}

	@Test
	public void testGetBenVisitDetailsFrmNursePNC() {
		// Arrange
		when(commonNurseServiceImpl.getCSVisitDetails(benRegID, visitCode)).thenReturn(visitDetail);
		when(commonNurseServiceImpl.getBenAdherence(benRegID, visitCode)).thenReturn("Adherence");
		when(commonNurseServiceImpl.getBenChiefComplaints(benRegID, visitCode)).thenReturn("ChiefComplaints");
		when(commonNurseServiceImpl.getBenCdss(benRegID, visitCode)).thenReturn("Cdss");

		// Act
		String response = pncServiceImpl.getBenVisitDetailsFrmNursePNC(benRegID, visitCode);

		// Assert
		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("PNCNurseVisitDetail", gson.toJson(visitDetail));
		expectedMap.put("BenAdherence", "Adherence");
		expectedMap.put("BenChiefComplaints", "ChiefComplaints");
		expectedMap.put("Cdss", "Cdss");

		assertEquals(expectedMap.toString(), response);
		verify(commonNurseServiceImpl, times(1)).getCSVisitDetails(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenAdherence(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenChiefComplaints(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenCdss(benRegID, visitCode);
	}

	@Test
	public void testGetBenPNCDetailsFrmNursePNC_WithVisitCode() {
		Map<String, Object> mockResponse = new HashMap<>();
		mockResponse.put("PNCCareDetail", "Mocked PNCCareDetail");

		when(pncNurseServiceImpl.getPNCCareDetails(benRegID, visitCode)).thenReturn("Mocked PNCCareDetail");

		String response = pncServiceImpl.getBenPNCDetailsFrmNursePNC(benRegID, visitCode);

		assertTrue(response.contains("Mocked PNCCareDetail"));
		verify(pncNurseServiceImpl, times(1)).getPNCCareDetails(benRegID, visitCode);
	}

	@Test
	public void testGetBenPNCDetailsFrmNursePNC_WithoutVisitCode() {
		when(beneficiaryFlowStatusRepo.getLatestVisitCode(benRegID, "PNC")).thenReturn(visitCode);
		when(pncNurseServiceImpl.getPNCCareDetails(benRegID, visitCode)).thenReturn("Mocked PNCCareDetail");

		String response = pncServiceImpl.getBenPNCDetailsFrmNursePNC(benRegID, null);

		assertTrue(response.contains("Mocked PNCCareDetail"));
		verify(beneficiaryFlowStatusRepo, times(1)).getLatestVisitCode(benRegID, "PNC");
		verify(pncNurseServiceImpl, times(1)).getPNCCareDetails(benRegID, visitCode);
	}

	@Test
	public void testGetBenPNCDetailsFrmNursePNC_WithoutVisitCode_NoLatestVisitCode() {
		when(beneficiaryFlowStatusRepo.getLatestVisitCode(benRegID, "PNC")).thenReturn(null);

		String response = pncServiceImpl.getBenPNCDetailsFrmNursePNC(benRegID, null);

		assertEquals("{}", response);
		verify(beneficiaryFlowStatusRepo, times(1)).getLatestVisitCode(benRegID, "PNC");
		verify(pncNurseServiceImpl, times(0)).getPNCCareDetails(anyLong(), anyLong());
	}

	@Test
	public void testGetBenHistoryDetails() throws IEMRException {
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

		String response = pncServiceImpl.getBenHistoryDetails(benRegID, visitCode);

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
		when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, visitCode))
				.thenReturn("AnthropometryDetails");
		when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode))
				.thenReturn("PhysicalVitalDetails");

		String response = pncServiceImpl.getBeneficiaryVitalDetails(beneficiaryRegID, visitCode);

		assertTrue(response.contains("AnthropometryDetails"));
		assertTrue(response.contains("PhysicalVitalDetails"));
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID,
				visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetBeneficiaryVitalDetails_EmptyResponse() {
		when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, visitCode))
				.thenReturn(null);
		when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode)).thenReturn(null);

		String response = pncServiceImpl.getBeneficiaryVitalDetails(beneficiaryRegID, visitCode);

		assertFalse(response.contains("AnthropometryDetails"));
		assertFalse(response.contains("PhysicalVitalDetails"));
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID,
				visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetBeneficiaryCdssDetails() {
		when(commonNurseServiceImpl.getBenCdssDetails(beneficiaryRegID, benVisitID)).thenReturn(cdssDetails);

		String response = pncServiceImpl.getBeneficiaryCdssDetails(beneficiaryRegID, benVisitID);

		Gson gson = new GsonBuilder().create();
		Map<String, Object> expectedResponse = new HashMap<>();
		expectedResponse.put("presentChiefComplaint", cdssDetails);
		expectedResponse.put("diseaseSummary", cdssDetails);

		assertEquals(gson.toJson(expectedResponse), response);
		verify(commonNurseServiceImpl, times(2)).getBenCdssDetails(beneficiaryRegID, benVisitID);
	}

	@Test
	public void testGetPNCExaminationDetailsData() {
		Map<String, Object> examinationDetailsMap = new HashMap<>();
		examinationDetailsMap.put("generalExamination", "General Examination Data");
		examinationDetailsMap.put("headToToeExamination", "Head to Toe Examination Data");
		examinationDetailsMap.put("gastrointestinalExamination", "Gastrointestinal Examination Data");
		examinationDetailsMap.put("cardiovascularExamination", "Cardiovascular Examination Data");
		examinationDetailsMap.put("respiratoryExamination", "Respiratory Examination Data");
		examinationDetailsMap.put("centralNervousExamination", "Central Nervous Examination Data");
		examinationDetailsMap.put("musculoskeletalExamination", "Musculoskeletal Examination Data");
		examinationDetailsMap.put("genitourinaryExamination", "Genitourinary Examination Data");

		when(commonNurseServiceImpl.getGeneralExaminationData(benRegID, visitCode))
				.thenReturn("General Examination Data");
		when(commonNurseServiceImpl.getHeadToToeExaminationData(benRegID, visitCode))
				.thenReturn("Head to Toe Examination Data");
		when(commonNurseServiceImpl.getSysGastrointestinalExamination(benRegID, visitCode))
				.thenReturn("Gastrointestinal Examination Data");
		when(commonNurseServiceImpl.getCardiovascularExamination(benRegID, visitCode))
				.thenReturn("Cardiovascular Examination Data");
		when(commonNurseServiceImpl.getRespiratoryExamination(benRegID, visitCode))
				.thenReturn("Respiratory Examination Data");
		when(commonNurseServiceImpl.getSysCentralNervousExamination(benRegID, visitCode))
				.thenReturn("Central Nervous Examination Data");
		when(commonNurseServiceImpl.getMusculoskeletalExamination(benRegID, visitCode))
				.thenReturn("Musculoskeletal Examination Data");
		when(commonNurseServiceImpl.getGenitourinaryExamination(benRegID, visitCode))
				.thenReturn("Genitourinary Examination Data");

		String response = pncServiceImpl.getPNCExaminationDetailsData(benRegID, visitCode);

		assertTrue(response.contains("General Examination Data"));
		assertTrue(response.contains("Head to Toe Examination Data"));
		assertTrue(response.contains("Gastrointestinal Examination Data"));
		assertTrue(response.contains("Cardiovascular Examination Data"));
		assertTrue(response.contains("Respiratory Examination Data"));
		assertTrue(response.contains("Central Nervous Examination Data"));
		assertTrue(response.contains("Musculoskeletal Examination Data"));
		assertTrue(response.contains("Genitourinary Examination Data"));

		verify(commonNurseServiceImpl, times(1)).getGeneralExaminationData(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getHeadToToeExaminationData(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getSysGastrointestinalExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getCardiovascularExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getRespiratoryExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getSysCentralNervousExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getMusculoskeletalExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getGenitourinaryExamination(benRegID, visitCode);
	}

	@Test
	public void testUpdateBenHistoryDetails_success() throws Exception {
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

		int result = pncServiceImpl.updateBenHistoryDetails(historyOBJ);

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
	public void testUpdateBenHistoryDetails_failure() throws Exception {
		when(commonNurseServiceImpl.updateBenPastHistoryDetails(any(BenMedHistory.class))).thenReturn(0);

		int result = pncServiceImpl.updateBenHistoryDetails(historyOBJ);

		assertEquals(0, result);
		verify(commonNurseServiceImpl, times(1)).updateBenPastHistoryDetails(any(BenMedHistory.class));
	}

	@Test
	public void testUpdateBenVitalDetails_Success() throws Exception {
		BenAnthropometryDetail benAnthropometryDetail = new BenAnthropometryDetail();
		BenPhysicalVitalDetail benPhysicalVitalDetail = new BenPhysicalVitalDetail();

		when(commonNurseServiceImpl.updateANCAnthropometryDetails(any(BenAnthropometryDetail.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class))).thenReturn(1);

		int result = pncServiceImpl.updateBenVitalDetails(vitalDetailsOBJ);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(1)).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testUpdateBenVitalDetails_NullInput() throws Exception {
		int result = pncServiceImpl.updateBenVitalDetails(null);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, never()).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, never()).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testUpdateBenVitalDetails_MissingFields() throws Exception {
		JsonObject incompleteVitalDetailsOBJ = new JsonObject();
		incompleteVitalDetailsOBJ.addProperty("benVisitID", 1L);

		int result = pncServiceImpl.updateBenVitalDetails(incompleteVitalDetailsOBJ);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, never()).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, never()).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testUpdateBenExaminationDetails_Success() throws Exception {
		when(commonNurseServiceImpl.updatePhyGeneralExamination(any(PhyGeneralExamination.class))).thenReturn(1);
		when(commonNurseServiceImpl.updatePhyHeadToToeExamination(any(PhyHeadToToeExamination.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateSysGastrointestinalExamination(any(SysGastrointestinalExamination.class)))
				.thenReturn(1);
		when(commonNurseServiceImpl.updateSysCardiovascularExamination(any(SysCardiovascularExamination.class)))
				.thenReturn(1);
		when(commonNurseServiceImpl.updateSysRespiratoryExamination(any(SysRespiratoryExamination.class)))
				.thenReturn(1);
		when(commonNurseServiceImpl.updateSysCentralNervousExamination(any(SysCentralNervousExamination.class)))
				.thenReturn(1);
		when(commonNurseServiceImpl
				.updateSysMusculoskeletalSystemExamination(any(SysMusculoskeletalSystemExamination.class)))
				.thenReturn(1);
		when(commonNurseServiceImpl
				.updateSysGenitourinarySystemExamination(any(SysGenitourinarySystemExamination.class))).thenReturn(1);

		int result = pncServiceImpl.updateBenExaminationDetails(examinationDetailsOBJ);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).updatePhyGeneralExamination(any(PhyGeneralExamination.class));
		verify(commonNurseServiceImpl, times(1)).updatePhyHeadToToeExamination(any(PhyHeadToToeExamination.class));
		verify(commonNurseServiceImpl, times(1))
				.updateSysGastrointestinalExamination(any(SysGastrointestinalExamination.class));
		verify(commonNurseServiceImpl, times(1))
				.updateSysCardiovascularExamination(any(SysCardiovascularExamination.class));
		verify(commonNurseServiceImpl, times(1)).updateSysRespiratoryExamination(any(SysRespiratoryExamination.class));
		verify(commonNurseServiceImpl, times(1))
				.updateSysCentralNervousExamination(any(SysCentralNervousExamination.class));
		verify(commonNurseServiceImpl, times(1))
				.updateSysMusculoskeletalSystemExamination(any(SysMusculoskeletalSystemExamination.class));
		verify(commonNurseServiceImpl, times(1))
				.updateSysGenitourinarySystemExamination(any(SysGenitourinarySystemExamination.class));
	}

	@Test
	public void testUpdateBenExaminationDetails_NullExaminationDetailsOBJ() throws Exception {
		int result = pncServiceImpl.updateBenExaminationDetails(null);

		assertEquals(0, result);
		verify(commonNurseServiceImpl, never()).updatePhyGeneralExamination(any(PhyGeneralExamination.class));
	}

	@Test
	public void testUpdateBenExaminationDetails_PartialUpdate() throws Exception {
		examinationDetailsOBJ.remove("headToToeExamination");

		when(commonNurseServiceImpl.updatePhyGeneralExamination(any(PhyGeneralExamination.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateSysGastrointestinalExamination(any(SysGastrointestinalExamination.class)))
				.thenReturn(1);
		when(commonNurseServiceImpl.updateSysCardiovascularExamination(any(SysCardiovascularExamination.class)))
				.thenReturn(1);
		when(commonNurseServiceImpl.updateSysRespiratoryExamination(any(SysRespiratoryExamination.class)))
				.thenReturn(1);
		when(commonNurseServiceImpl.updateSysCentralNervousExamination(any(SysCentralNervousExamination.class)))
				.thenReturn(1);
		when(commonNurseServiceImpl
				.updateSysMusculoskeletalSystemExamination(any(SysMusculoskeletalSystemExamination.class)))
				.thenReturn(1);
		when(commonNurseServiceImpl
				.updateSysGenitourinarySystemExamination(any(SysGenitourinarySystemExamination.class))).thenReturn(1);

		int result = pncServiceImpl.updateBenExaminationDetails(examinationDetailsOBJ);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).updatePhyGeneralExamination(any(PhyGeneralExamination.class));
		verify(commonNurseServiceImpl, never()).updatePhyHeadToToeExamination(any(PhyHeadToToeExamination.class));
		verify(commonNurseServiceImpl, times(1))
				.updateSysGastrointestinalExamination(any(SysGastrointestinalExamination.class));
		verify(commonNurseServiceImpl, times(1))
				.updateSysCardiovascularExamination(any(SysCardiovascularExamination.class));
		verify(commonNurseServiceImpl, times(1)).updateSysRespiratoryExamination(any(SysRespiratoryExamination.class));
		verify(commonNurseServiceImpl, times(1))
				.updateSysCentralNervousExamination(any(SysCentralNervousExamination.class));
		verify(commonNurseServiceImpl, times(1))
				.updateSysMusculoskeletalSystemExamination(any(SysMusculoskeletalSystemExamination.class));
		verify(commonNurseServiceImpl, times(1))
				.updateSysGenitourinarySystemExamination(any(SysGenitourinarySystemExamination.class));
	}

	@Test
	public void testUpdateBenPNCDetails_Success() throws Exception {
		PNCCare pncCareDetails = new PNCCare();
		when(pncNurseServiceImpl.updateBenPNCCareDetails(any(PNCCare.class))).thenReturn(1);

		int result = pncServiceImpl.updateBenPNCDetails(pncDetailsOBJ);

		assertEquals(1, result);
		verify(pncNurseServiceImpl, times(1)).updateBenPNCCareDetails(any(PNCCare.class));
	}

	@Test
	public void testUpdateBenPNCDetails_NullDetails() throws Exception {
		JsonObject emptyDetailsOBJ = new JsonObject();
		int result = pncServiceImpl.updateBenPNCDetails(emptyDetailsOBJ);

		assertEquals(1, result);
		verify(pncNurseServiceImpl, never()).updateBenPNCCareDetails(any(PNCCare.class));
	}

	@Test
	public void testUpdateBenPNCDetails_Exception() throws Exception {
		when(pncNurseServiceImpl.updateBenPNCCareDetails(any(PNCCare.class)))
				.thenThrow(new RuntimeException("Exception"));

		Exception exception = assertThrows(Exception.class, () -> {
			pncServiceImpl.updateBenPNCDetails(pncDetailsOBJ);
		});

		assertEquals("Exception", exception.getMessage());
		verify(pncNurseServiceImpl, times(1)).updateBenPNCCareDetails(any(PNCCare.class));
	}

	@Test
	public void testGetBenPNCNurseData() throws IEMRException {
		// Mocking the dependencies
		when(commonNurseServiceImpl.getCSVisitDetails(benRegID, visitCode)).thenReturn(new CommonUtilityClass());
		when(commonNurseServiceImpl.getBenAdherence(benRegID, visitCode)).thenReturn("Adherence");
		when(commonNurseServiceImpl.getBenChiefComplaints(benRegID, visitCode)).thenReturn("ChiefComplaints");
		when(commonNurseServiceImpl.getBenCdss(benRegID, visitCode)).thenReturn("Cdss");
		when(commonNurseServiceImpl.getPNCCareDetails(benRegID, visitCode)).thenReturn("PNCCareDetail");
		when(commonNurseServiceImpl.getPastHistoryData(benRegID, visitCode)).thenReturn("PastHistory");
		when(commonNurseServiceImpl.getComorbidityConditionsHistory(benRegID, visitCode))
				.thenReturn("ComorbidityConditions");
		when(commonNurseServiceImpl.getMedicationHistory(benRegID, visitCode)).thenReturn("MedicationHistory");
		when(commonNurseServiceImpl.getPersonalHistory(benRegID, visitCode)).thenReturn("PersonalHistory");
		when(commonNurseServiceImpl.getFamilyHistory(benRegID, visitCode)).thenReturn("FamilyHistory");
		when(commonNurseServiceImpl.getMenstrualHistory(benRegID, visitCode)).thenReturn("MenstrualHistory");
		when(commonNurseServiceImpl.getFemaleObstetricHistory(benRegID, visitCode))
				.thenReturn("FemaleObstetricHistory");
		when(commonNurseServiceImpl.getImmunizationHistory(benRegID, visitCode)).thenReturn("ImmunizationHistory");
		when(commonNurseServiceImpl.getChildOptionalVaccineHistory(benRegID, visitCode))
				.thenReturn("ChildOptionalVaccineHistory");
		when(commonNurseServiceImpl.getDevelopmentHistory(benRegID, visitCode)).thenReturn("DevelopmentHistory");
		when(commonNurseServiceImpl.getPerinatalHistory(benRegID, visitCode)).thenReturn("PerinatalHistory");
		when(commonNurseServiceImpl.getFeedingHistory(benRegID, visitCode)).thenReturn("FeedingHistory");
		when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(benRegID, visitCode))
				.thenReturn("PhysicalAnthropometryDetails");
		when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(benRegID, visitCode))
				.thenReturn("PhysicalVitalDetails");
		when(commonNurseServiceImpl.getGeneralExaminationData(benRegID, visitCode))
				.thenReturn("GeneralExaminationData");
		when(commonNurseServiceImpl.getHeadToToeExaminationData(benRegID, visitCode))
				.thenReturn("HeadToToeExaminationData");
		when(commonNurseServiceImpl.getSysGastrointestinalExamination(benRegID, visitCode))
				.thenReturn("GastrointestinalExamination");
		when(commonNurseServiceImpl.getCardiovascularExamination(benRegID, visitCode))
				.thenReturn("CardiovascularExamination");
		when(commonNurseServiceImpl.getRespiratoryExamination(benRegID, visitCode))
				.thenReturn("RespiratoryExamination");
		when(commonNurseServiceImpl.getSysCentralNervousExamination(benRegID, visitCode))
				.thenReturn("CentralNervousExamination");
		when(commonNurseServiceImpl.getMusculoskeletalExamination(benRegID, visitCode))
				.thenReturn("MusculoskeletalExamination");
		when(commonNurseServiceImpl.getGenitourinaryExamination(benRegID, visitCode))
				.thenReturn("GenitourinaryExamination");
		when(commonNurseServiceImpl.getGraphicalTrendData(benRegID, "pnc")).thenReturn("GraphicalTrendData");
		when(commonDoctorServiceImpl.getFindingsDetails(benRegID, visitCode)).thenReturn("FindingsDetails");
		when(commonDoctorServiceImpl.getInvestigationDetails(benRegID, visitCode)).thenReturn("InvestigationDetails");
		when(commonDoctorServiceImpl.getPrescribedDrugs(benRegID, visitCode)).thenReturn("PrescribedDrugs");
		when(commonDoctorServiceImpl.getReferralDetails(benRegID, visitCode, false)).thenReturn("ReferralDetails");
		when(labTechnicianServiceImpl.getLabResultDataForBen(benRegID, visitCode)).thenReturn("LabResultData");
		when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(benRegID, visitCode))
				.thenReturn("ArchivedVisitcodeForLabResult");

		// Call the method under test
		String response = pncServiceImpl.getBenPNCNurseData(benRegID, visitCode);

		// Assert the expected results
		assertNotNull(response);
		assertTrue(response.contains("pnc"));
		assertTrue(response.contains("history"));
		assertTrue(response.contains("vitals"));
		assertTrue(response.contains("examination"));
		assertTrue(response.contains("cdss"));

		// Verify interactions with mocks
		verify(commonNurseServiceImpl, times(1)).getCSVisitDetails(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenAdherence(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenChiefComplaints(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenCdss(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getPNCCareDetails(benRegID, visitCode);
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
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalVitalDetails(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getGeneralExaminationData(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getHeadToToeExaminationData(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getSysGastrointestinalExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getCardiovascularExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getRespiratoryExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getSysCentralNervousExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getMusculoskeletalExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getGenitourinaryExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getGraphicalTrendData(benRegID, "pnc");
		verify(commonDoctorServiceImpl, times(1)).getFindingsDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getInvestigationDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getPrescribedDrugs(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getReferralDetails(benRegID, visitCode, false);
		verify(labTechnicianServiceImpl, times(1)).getLabResultDataForBen(benRegID, visitCode);
		verify(labTechnicianServiceImpl, times(1)).getLast_3_ArchivedTestVisitList(benRegID, visitCode);
	}

	@Test
	public void testGetBenCaseRecordFromDoctorPNC() {
		// Mocking the return values
		when(commonDoctorServiceImpl.getFindingsDetails(benRegID, visitCode)).thenReturn("Findings");
		when(pncDoctorServiceImpl.getPNCDiagnosisDetails(benRegID, visitCode)).thenReturn("Diagnosis");
		when(commonDoctorServiceImpl.getInvestigationDetails(benRegID, visitCode)).thenReturn("Investigation");
		when(commonDoctorServiceImpl.getPrescribedDrugs(benRegID, visitCode)).thenReturn("Prescription");
		when(commonDoctorServiceImpl.getReferralDetails(benRegID, visitCode, false)).thenReturn("Refer");
		when(labTechnicianServiceImpl.getLabResultDataForBen(benRegID, visitCode)).thenReturn(new HashMap<>());
		when(commonNurseServiceImpl.getGraphicalTrendData(benRegID, "pnc")).thenReturn(new HashMap<>());
		when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(benRegID, visitCode)).thenReturn(new HashMap<>());

		// Call the method
		String response = pncServiceImpl.getBenCaseRecordFromDoctorPNC(benRegID, visitCode);

		// Expected response
		Map<String, Object> expectedResponse = new HashMap<>();
		expectedResponse.put("findings", "Findings");
		expectedResponse.put("diagnosis", "Diagnosis");
		expectedResponse.put("investigation", "Investigation");
		expectedResponse.put("prescription", "Prescription");
		expectedResponse.put("Refer", "Refer");
		expectedResponse.put("LabReport", "{}");
		expectedResponse.put("GraphData", "{}");
		expectedResponse.put("ArchivedVisitcodeForLabResult", "{}");

		// Assert the response
		assertEquals(new Gson().toJson(expectedResponse), response);

		// Verify the interactions
		verify(commonDoctorServiceImpl, times(1)).getFindingsDetails(benRegID, visitCode);
		verify(pncDoctorServiceImpl, times(1)).getPNCDiagnosisDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getInvestigationDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getPrescribedDrugs(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getReferralDetails(benRegID, visitCode, false);
		verify(labTechnicianServiceImpl, times(1)).getLabResultDataForBen(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getGraphicalTrendData(benRegID, "pnc");
		verify(labTechnicianServiceImpl, times(1)).getLast_3_ArchivedTestVisitList(benRegID, visitCode);
	}

	@Test
	public void testUpdatePNCDoctorData_Success() throws Exception {
		// Setup mock behavior
		when(commonDoctorServiceImpl.updateDocFindings(any())).thenReturn(1);
		when(pncDoctorServiceImpl.updateBenPNCDiagnosis(any())).thenReturn(1);
		when(commonNurseServiceImpl.saveBenInvestigation(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenPrescribedDrugsList(any())).thenReturn(1);
		when(commonDoctorServiceImpl.updateBenReferDetails(any(), eq(false))).thenReturn(1L);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(any(), anyBoolean(), anyBoolean(), any()))
				.thenReturn(1);

		// Call the method under test
		Long result = pncServiceImpl.updatePNCDoctorData(requestObj, authorization);

		// Verify the result
		assertNotNull(result);
		assertEquals(1L, result);

		// Verify interactions
		verify(commonDoctorServiceImpl, times(1)).updateDocFindings(any());
		verify(pncDoctorServiceImpl, times(1)).updateBenPNCDiagnosis(any());
		verify(commonNurseServiceImpl, times(1)).saveBenInvestigation(any());
		verify(commonNurseServiceImpl, times(1)).saveBenPrescribedDrugsList(any());
		verify(commonDoctorServiceImpl, times(1)).updateBenReferDetails(any(), eq(false));
		verify(commonDoctorServiceImpl, times(1)).updateBenFlowtableAfterDocDataUpdate(any(), anyBoolean(),
				anyBoolean(), any());
	}

	@Test
	public void testUpdatePNCDoctorData_NullRequestObj() throws Exception {
		// Call the method under test
		Long result = pncServiceImpl.updatePNCDoctorData(null, authorization);

		// Verify the result
		assertNull(result);

		// Verify no interactions
		verifyNoInteractions(commonDoctorServiceImpl);
		verifyNoInteractions(pncDoctorServiceImpl);
		verifyNoInteractions(commonNurseServiceImpl);
	}

	@Test
	public void testUpdatePNCDoctorData_MissingFields() throws Exception {
		// Setup mock behavior
		when(commonDoctorServiceImpl.updateDocFindings(any())).thenReturn(1);
		when(pncDoctorServiceImpl.updateBenPNCDiagnosis(any())).thenReturn(1);
		when(commonNurseServiceImpl.saveBenInvestigation(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenPrescribedDrugsList(any())).thenReturn(1);
		when(commonDoctorServiceImpl.updateBenReferDetails(any(), eq(false))).thenReturn(1L);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(any(), anyBoolean(), anyBoolean(), any()))
				.thenReturn(1);

		// Call the method under test
		Long result = pncServiceImpl.updatePNCDoctorData(requestObj, authorization);

		// Verify the result
		assertNotNull(result);
		assertEquals(1L, result);

		// Verify interactions
		verify(commonDoctorServiceImpl, times(1)).updateDocFindings(any());
		verify(pncDoctorServiceImpl, times(1)).updateBenPNCDiagnosis(any());
		verify(commonNurseServiceImpl, times(1)).saveBenInvestigation(any());
		verify(commonNurseServiceImpl, times(1)).saveBenPrescribedDrugsList(any());
		verify(commonDoctorServiceImpl, times(1)).updateBenReferDetails(any(), eq(false));
		verify(commonDoctorServiceImpl, times(1)).updateBenFlowtableAfterDocDataUpdate(any(), anyBoolean(),
				anyBoolean(), any());
	}

	@Test
	public void testUpdatePNCDoctorData_ExceptionThrown() throws Exception {
		// Setup mock behavior
		when(commonDoctorServiceImpl.updateDocFindings(any())).thenThrow(new RuntimeException("Test Exception"));

		// Call the method under test and verify exception
		assertThrows(RuntimeException.class, () -> {
			pncServiceImpl.updatePNCDoctorData(requestObj, authorization);
		});

		// Verify interactions
		verify(commonDoctorServiceImpl, times(1)).updateDocFindings(any());
		verifyNoMoreInteractions(commonDoctorServiceImpl);
		verifyNoInteractions(pncDoctorServiceImpl);
		verifyNoInteractions(commonNurseServiceImpl);
	}
}
