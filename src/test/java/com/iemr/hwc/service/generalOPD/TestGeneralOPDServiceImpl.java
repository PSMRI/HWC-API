package com.iemr.hwc.service.generalOPD;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.data.nurse.BenAnthropometryDetail;
import com.iemr.hwc.data.nurse.BenPhysicalVitalDetail;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.service.nurse.CommonNurseServiceImpl;
import com.iemr.hwc.service.doctor.CommonDoctorServiceImpl;
import com.iemr.hwc.service.benstatusflow.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.lab.LabTechnicianServiceImpl;
import com.iemr.hwc.service.common.CommonServiceImpl;
import com.iemr.hwc.service.sms.SMSGatewayServiceImpl;
import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.service.familyplanning.FamilyPlanningService;
import com.iemr.hwc.repo.BenVisitDetailRepo;
import com.iemr.hwc.repo.BenChiefComplaintRepo;
import com.iemr.hwc.repo.BenAdherenceRepo;
import com.iemr.hwc.repo.CDSSRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestGeneralOPDServiceImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private CommonNurseServiceImpl commonNurseServiceImpl;
	@Mock
	private CommonDoctorServiceImpl commonDoctorServiceImpl;
	@Mock
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;
	@Mock
	private LabTechnicianServiceImpl labTechnicianServiceImpl;
	@Mock
	private CommonServiceImpl commonServiceImpl;
	@Mock
	private SMSGatewayServiceImpl sMSGatewayServiceImpl;
	@Mock
	private FamilyPlanningService familyPlanningService;
	@Mock
	private BenVisitDetailRepo benVisitDetailRepo;
	@Mock
	private BenChiefComplaintRepo benChiefComplaintRepo;
	@Mock
	private BenAdherenceRepo benAdherenceRepo;
	@Mock
	private CDSSRepo cdssRepo;

	@Mock
	private GeneralOPDDoctorServiceImpl generalOPDDoctorServiceImpl;

	@InjectMocks
	private GeneralOPDServiceImpl generalOPDServiceImpl;

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
	private JsonObject generalOPDHistoryOBJ;
	private JsonObject vitalDetailsOBJ;
	private JsonObject examinationDetailsOBJ;
	private JsonObject requestOBJ;
	private Long benRegID;
	private Long visitCode;
	private BeneficiaryVisitDetail visitDetail;
	private Gson gson;
	private Map<String, Object> historyDetailsMap;
	private Long beneficiaryRegID;

	private BenAnthropometryDetail benAnthropometryDetail;
	private BenPhysicalVitalDetail benPhysicalVitalDetail;
	private JsonObject validJsonWithChiefComplaints;
	private JsonObject validJsonWithoutChiefComplaints;
	private JsonObject nullJson;
	private JsonObject jsonWithoutVisitDetails;
	private JsonObject historyOBJ;
	private Map<String, Object> expectedHistory;
	private Map<String, Object> expectedExamination;
	private Map<String, Object> expectedVitals;
	private Map<String, Object> expectedCdss;

	@BeforeEach
	public void setUp() {
		requestObj = new JsonObject();
		JsonObject visitDetails = new JsonObject();
		visitDetails.addProperty("visitDetails", "someDetails");
		requestObj.add("visitDetails", visitDetails);
		authorization = "Bearer token";
	}

	@Test
	public void testSaveNurseData_ValidInput() throws Exception {
		// Arrange
		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any())).thenReturn(1L);
		when(commonNurseServiceImpl.generateVisitCode(anyLong(), anyInt(), anyInt())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenChiefComplaints(anyList())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenAdherenceDetails(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveCdssDetails(any())).thenReturn(1L);
		when(commonServiceImpl.createTcRequest(any(), any(), eq(authorization))).thenReturn(null);
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
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(any())).thenReturn(1L);
		when(commonNurseServiceImpl.savePhyGeneralExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.savePhyHeadToToeExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysGastrointestinalExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysCardiovascularExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysRespiratoryExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysCentralNervousExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysMusculoskeletalSystemExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysGenitourinarySystemExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveOralExamination(any())).thenReturn(1L);
		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(), anyLong(),
				anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(),
				anyInt(), anyShort(), any(), any())).thenReturn(1);

		// Act
		String response = generalOPDServiceImpl.saveNurseData(requestObj, authorization);

		// Assert
		assertTrue(response.contains("Data saved successfully"));
		verify(commonNurseServiceImpl, times(1)).saveBeneficiaryVisitDetails(any());
		verify(commonNurseServiceImpl, times(1)).generateVisitCode(anyLong(), anyInt(), anyInt());
	}

	@Test
	public void testSaveNurseData_InvalidInput() {
		// Arrange
		JsonObject invalidRequestObj = new JsonObject();

		// Act & Assert
		Exception exception = assertThrows(Exception.class, () -> {
			generalOPDServiceImpl.saveNurseData(invalidRequestObj, authorization);
		});

		assertEquals("Invalid input", exception.getMessage());
	}

	@Test
	public void testSaveNurseData_NullVisitDetails() {
		// Arrange
		requestObj.remove("visitDetails");

		// Act & Assert
		Exception exception = assertThrows(Exception.class, () -> {
			generalOPDServiceImpl.saveNurseData(requestObj, authorization);
		});

		assertEquals("Invalid input", exception.getMessage());
	}

	@Test
	public void testSaveNurseData_NullHistoryDetails() throws Exception {
		// Arrange
		requestObj.remove("historyDetails");

		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any())).thenReturn(1L);
		when(commonNurseServiceImpl.generateVisitCode(anyLong(), anyInt(), anyInt())).thenReturn(1L);
		when(commonServiceImpl.createTcRequest(any(), any(), eq(authorization))).thenReturn(null);
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(any())).thenReturn(1L);
		when(commonNurseServiceImpl.savePhyGeneralExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.savePhyHeadToToeExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysGastrointestinalExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysCardiovascularExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysRespiratoryExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysCentralNervousExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysMusculoskeletalSystemExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysGenitourinarySystemExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveOralExamination(any())).thenReturn(1L);
		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(), anyLong(),
				anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(),
				anyInt(), anyShort(), any(), any())).thenReturn(1);

		// Act
		String response = generalOPDServiceImpl.saveNurseData(requestObj, authorization);

		// Assert
		assertTrue(response.contains("Data saved successfully"));
	}

	@Test
	public void testSaveNurseData_NullVitalDetails() throws Exception {
		// Arrange
		requestObj.remove("vitalDetails");

		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any())).thenReturn(1L);
		when(commonNurseServiceImpl.generateVisitCode(anyLong(), anyInt(), anyInt())).thenReturn(1L);
		when(commonServiceImpl.createTcRequest(any(), any(), eq(authorization))).thenReturn(null);
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
		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(), anyLong(),
				anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(),
				anyInt(), anyShort(), any(), any())).thenReturn(1);

		// Act
		String response = generalOPDServiceImpl.saveNurseData(requestObj, authorization);

		// Assert
		assertTrue(response.contains("Data saved successfully"));
	}

	@Test
	public void testSaveNurseData_NullExaminationDetails() throws Exception {
		// Arrange
		requestObj.remove("examinationDetails");

		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any())).thenReturn(1L);
		when(commonNurseServiceImpl.generateVisitCode(anyLong(), anyInt(), anyInt())).thenReturn(1L);
		when(commonServiceImpl.createTcRequest(any(), any(), eq(authorization))).thenReturn(null);
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
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(any())).thenReturn(1L);
		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(), anyLong(),
				anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(),
				anyInt(), anyShort(), any(), any())).thenReturn(1);

		// Act
		String response = generalOPDServiceImpl.saveNurseData(requestObj, authorization);

		// Assert
		assertTrue(response.contains("Data saved successfully"));
	}

	@Test
	public void testUpdateBenStatusFlagAfterNurseSaveSuccess_WithTeleconsultation() {
		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(), anyLong(),
				anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(),
				anyInt(), anyShort(), any(Timestamp.class), anyInt())).thenReturn(1);

		int result = generalOPDServiceImpl.updateBenStatusFlagAfterNurseSaveSuccess(tmpOBJ, benVisitID, benFlowID,
				benVisitCode, vanID, tcRequestOBJ);

		assertEquals(1, result);
		verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseActivity(eq(benFlowID), eq(1L),
				eq(benVisitID), eq("Routine Checkup"), eq("General"), eq((short) 9), eq((short) 1), eq((short) 0),
				eq((short) 0), eq((short) 0), eq(benVisitCode), eq(vanID), eq((short) 1),
				eq(tcRequestOBJ.getAllocationDate()), eq(tcRequestOBJ.getUserID()));
	}

	@Test
	public void testUpdateBenStatusFlagAfterNurseSaveSuccess_WithoutTeleconsultation() {
		tcRequestOBJ = null;

		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(), anyLong(),
				anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(),
				anyInt(), anyShort(), any(Timestamp.class), anyInt())).thenReturn(1);

		int result = generalOPDServiceImpl.updateBenStatusFlagAfterNurseSaveSuccess(tmpOBJ, benVisitID, benFlowID,
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

		int result = generalOPDServiceImpl.updateBenStatusFlagAfterNurseSaveSuccess(tmpOBJ, benVisitID, benFlowID,
				benVisitCode, vanID, tcRequestOBJ);

		assertEquals(0, result);
		verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseActivity(eq(benFlowID), eq(1L),
				eq(benVisitID), eq("Routine Checkup"), eq("General"), eq((short) 9), eq((short) 1), eq((short) 0),
				eq((short) 0), eq((short) 0), eq(benVisitCode), eq(vanID), eq((short) 1),
				eq(tcRequestOBJ.getAllocationDate()), eq(tcRequestOBJ.getUserID()));
	}

	@Test
	public void testSaveBenVisitDetails_Success() throws Exception {
		BeneficiaryVisitDetail benVisitDetailsOBJ = new BeneficiaryVisitDetail();
		benVisitDetailsOBJ.setBeneficiaryRegID(1L);
		benVisitDetailsOBJ.setVisitReason("Routine");
		benVisitDetailsOBJ.setVisitCategory("General");

		when(commonNurseServiceImpl.getMaxCurrentdate(anyLong(), anyString(), anyString())).thenReturn(0);
		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class))).thenReturn(1L);
		when(commonNurseServiceImpl.generateVisitCode(anyLong(), anyInt(), anyInt())).thenReturn(1L);

		Map<String, Long> result = generalOPDServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass);

		assertNotNull(result);
		assertEquals(1L, result.get("visitID"));
		assertEquals(1L, result.get("visitCode"));

		verify(commonNurseServiceImpl, times(1)).getMaxCurrentdate(anyLong(), anyString(), anyString());
		verify(commonNurseServiceImpl, times(1)).saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class));
		verify(commonNurseServiceImpl, times(1)).generateVisitCode(anyLong(), anyInt(), anyInt());
		verify(commonNurseServiceImpl, times(1)).saveBenChiefComplaints(anyList());
		verify(commonNurseServiceImpl, times(1)).saveBenAdherenceDetails(any(BenAdherence.class));
		verify(commonNurseServiceImpl, times(1)).saveCdssDetails(any(CDSS.class));
	}

	@Test
	public void testSaveBenVisitDetails_NullVisitDetails() throws Exception {
		visitDetailsOBJ.remove("visitDetails");

		Map<String, Long> result = generalOPDServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass);

		assertTrue(result.isEmpty());
		verify(commonNurseServiceImpl, never()).getMaxCurrentdate(anyLong(), anyString(), anyString());
		verify(commonNurseServiceImpl, never()).saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class));
	}

	@Test
	public void testSaveBenVisitDetails_ExistingVisit() throws Exception {
		when(commonNurseServiceImpl.getMaxCurrentdate(anyLong(), anyString(), anyString())).thenReturn(1);

		Map<String, Long> result = generalOPDServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass);

		assertTrue(result.isEmpty());
		verify(commonNurseServiceImpl, times(1)).getMaxCurrentdate(anyLong(), anyString(), anyString());
		verify(commonNurseServiceImpl, never()).saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class));
	}

	@Test
	public void testSaveBenVisitDetails_Exception() throws Exception {
		when(commonNurseServiceImpl.getMaxCurrentdate(anyLong(), anyString(), anyString()))
				.thenThrow(new IEMRException("Error"));

		Exception exception = assertThrows(Exception.class, () -> {
			generalOPDServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass);
		});

		assertEquals("Error", exception.getMessage());
		verify(commonNurseServiceImpl, times(1)).getMaxCurrentdate(anyLong(), anyString(), anyString());
		verify(commonNurseServiceImpl, never()).saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class));
	}

	@Test
	public void testSaveBenGeneralOPDHistoryDetails_Success() throws Exception {
		// Setup mock behavior
		when(commonNurseServiceImpl.saveBenPastHistory(any(BenMedHistory.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenComorbidConditions(any(WrapperComorbidCondDetails.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenMedicationHistory(any(WrapperMedicationHistory.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveFemaleObstetricHistory(any(WrapperFemaleObstetricHistory.class)))
				.thenReturn(1L);
		when(commonNurseServiceImpl.saveBenMenstrualHistory(any(BenMenstrualDetails.class))).thenReturn(1);
		when(commonNurseServiceImpl.saveBenFamilyHistory(any(BenFamilyHistory.class))).thenReturn(1L);
		when(commonNurseServiceImpl.savePersonalHistory(any(BenPersonalHabit.class))).thenReturn(1);
		when(commonNurseServiceImpl.saveAllergyHistory(any(BenAllergyHistory.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveChildOptionalVaccineDetail(any(WrapperChildOptionalVaccineDetail.class)))
				.thenReturn(1L);
		when(commonNurseServiceImpl.saveImmunizationHistory(any(WrapperImmunizationHistory.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveChildDevelopmentHistory(any(BenChildDevelopmentHistory.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveChildFeedingHistory(any(ChildFeedingDetails.class))).thenReturn(1L);
		when(commonNurseServiceImpl.savePerinatalHistory(any(PerinatalHistory.class))).thenReturn(1L);

		// Add necessary fields to generalOPDHistoryOBJ
		generalOPDHistoryOBJ.add("pastHistory", new JsonObject());
		generalOPDHistoryOBJ.add("comorbidConditions", new JsonObject());
		generalOPDHistoryOBJ.add("medicationHistory", new JsonObject());
		generalOPDHistoryOBJ.add("femaleObstetricHistory", new JsonObject());
		generalOPDHistoryOBJ.add("menstrualHistory", new JsonObject());
		generalOPDHistoryOBJ.add("familyHistory", new JsonObject());
		generalOPDHistoryOBJ.add("personalHistory", new JsonObject());
		generalOPDHistoryOBJ.add("childVaccineDetails", new JsonObject());
		generalOPDHistoryOBJ.add("immunizationHistory", new JsonObject());
		generalOPDHistoryOBJ.add("developmentHistory", new JsonObject());
		generalOPDHistoryOBJ.add("feedingHistory", new JsonObject());
		generalOPDHistoryOBJ.add("perinatalHistroy", new JsonObject());

		Long result = generalOPDServiceImpl.saveBenGeneralOPDHistoryDetails(generalOPDHistoryOBJ, benVisitID,
				benVisitCode);

		assertNotNull(result);
		assertEquals(1L, result);
	}

	@Test
	public void testSaveBenGeneralOPDHistoryDetails_InvalidInput() throws Exception {
		generalOPDHistoryOBJ = null;

		Exception exception = assertThrows(Exception.class, () -> {
			generalOPDServiceImpl.saveBenGeneralOPDHistoryDetails(generalOPDHistoryOBJ, benVisitID, benVisitCode);
		});

		assertEquals("Invalid input", exception.getMessage());
	}

	@Test
	public void testSaveBenGeneralOPDHistoryDetails_Exception() throws Exception {
		when(commonNurseServiceImpl.saveBenPastHistory(any(BenMedHistory.class)))
				.thenThrow(new RuntimeException("Error"));

		generalOPDHistoryOBJ.add("pastHistory", new JsonObject());

		Exception exception = assertThrows(RuntimeException.class, () -> {
			generalOPDServiceImpl.saveBenGeneralOPDHistoryDetails(generalOPDHistoryOBJ, benVisitID, benVisitCode);
		});

		assertEquals("Error", exception.getMessage());
	}

	@Test
	public void testSaveBenVitalDetails_Success() throws Exception {
		BenAnthropometryDetail benAnthropometryDetail = new BenAnthropometryDetail();
		BenPhysicalVitalDetail benPhysicalVitalDetail = new BenPhysicalVitalDetail();

		when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(any(BenAnthropometryDetail.class)))
				.thenReturn(1L);
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(any(BenPhysicalVitalDetail.class)))
				.thenReturn(1L);

		Long result = generalOPDServiceImpl.saveBenVitalDetails(vitalDetailsOBJ, benVisitID, benVisitCode);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(commonNurseServiceImpl, times(1))
				.saveBeneficiaryPhysicalAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(1)).saveBeneficiaryPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testSaveBenVitalDetails_NullInput() throws Exception {
		Long result = generalOPDServiceImpl.saveBenVitalDetails(null, benVisitID, benVisitCode);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(commonNurseServiceImpl, times(0))
				.saveBeneficiaryPhysicalAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(0)).saveBeneficiaryPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testSaveBenVitalDetails_PartialData() throws Exception {
		JsonObject partialVitalDetailsOBJ = new JsonObject();
		partialVitalDetailsOBJ.addProperty("height", 170);

		when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(any(BenAnthropometryDetail.class)))
				.thenReturn(1L);
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(any(BenPhysicalVitalDetail.class)))
				.thenReturn(1L);

		Long result = generalOPDServiceImpl.saveBenVitalDetails(partialVitalDetailsOBJ, benVisitID, benVisitCode);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(commonNurseServiceImpl, times(1))
				.saveBeneficiaryPhysicalAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(1)).saveBeneficiaryPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testSaveBenExaminationDetails_GeneralExamination() throws Exception {
		JsonObject generalExamination = new JsonObject();
		generalExamination.addProperty("someProperty", "someValue");
		examinationDetailsOBJ.add("generalExamination", generalExamination);

		Long result = generalOPDServiceImpl.saveBenExaminationDetails(examinationDetailsOBJ, benVisitID, benVisitCode);

		assertEquals(1L, result);
		verify(commonNurseServiceImpl, times(1)).savePhyGeneralExamination(any(PhyGeneralExamination.class));
	}

	@Test
	public void testSaveBenExaminationDetails_HeadToToeExamination() throws Exception {
		JsonObject headToToeExamination = new JsonObject();
		headToToeExamination.addProperty("someProperty", "someValue");
		examinationDetailsOBJ.add("headToToeExamination", headToToeExamination);

		Long result = generalOPDServiceImpl.saveBenExaminationDetails(examinationDetailsOBJ, benVisitID, benVisitCode);

		assertEquals(1L, result);
		verify(commonNurseServiceImpl, times(1)).savePhyHeadToToeExamination(any(PhyHeadToToeExamination.class));
	}

	@Test
	public void testSaveBenExaminationDetails_GastrointestinalExamination() throws Exception {
		JsonObject gastrointestinalExamination = new JsonObject();
		gastrointestinalExamination.addProperty("someProperty", "someValue");
		examinationDetailsOBJ.add("gastroIntestinalExamination", gastrointestinalExamination);

		Long result = generalOPDServiceImpl.saveBenExaminationDetails(examinationDetailsOBJ, benVisitID, benVisitCode);

		assertEquals(1L, result);
		verify(commonNurseServiceImpl, times(1))
				.saveSysGastrointestinalExamination(any(SysGastrointestinalExamination.class));
	}

	@Test
	public void testSaveBenExaminationDetails_CardiovascularExamination() throws Exception {
		JsonObject cardiovascularExamination = new JsonObject();
		cardiovascularExamination.addProperty("someProperty", "someValue");
		examinationDetailsOBJ.add("cardioVascularExamination", cardiovascularExamination);

		Long result = generalOPDServiceImpl.saveBenExaminationDetails(examinationDetailsOBJ, benVisitID, benVisitCode);

		assertEquals(1L, result);
		verify(commonNurseServiceImpl, times(1))
				.saveSysCardiovascularExamination(any(SysCardiovascularExamination.class));
	}

	@Test
	public void testSaveBenExaminationDetails_RespiratoryExamination() throws Exception {
		JsonObject respiratoryExamination = new JsonObject();
		respiratoryExamination.addProperty("someProperty", "someValue");
		examinationDetailsOBJ.add("respiratorySystemExamination", respiratoryExamination);

		Long result = generalOPDServiceImpl.saveBenExaminationDetails(examinationDetailsOBJ, benVisitID, benVisitCode);

		assertEquals(1L, result);
		verify(commonNurseServiceImpl, times(1)).saveSysRespiratoryExamination(any(SysRespiratoryExamination.class));
	}

	@Test
	public void testSaveBenExaminationDetails_CentralNervousSystemExamination() throws Exception {
		JsonObject centralNervousSystemExamination = new JsonObject();
		centralNervousSystemExamination.addProperty("someProperty", "someValue");
		examinationDetailsOBJ.add("centralNervousSystemExamination", centralNervousSystemExamination);

		Long result = generalOPDServiceImpl.saveBenExaminationDetails(examinationDetailsOBJ, benVisitID, benVisitCode);

		assertEquals(1L, result);
		verify(commonNurseServiceImpl, times(1))
				.saveSysCentralNervousExamination(any(SysCentralNervousExamination.class));
	}

	@Test
	public void testSaveBenExaminationDetails_MusculoskeletalSystemExamination() throws Exception {
		JsonObject musculoskeletalSystemExamination = new JsonObject();
		musculoskeletalSystemExamination.addProperty("someProperty", "someValue");
		examinationDetailsOBJ.add("musculoskeletalSystemExamination", musculoskeletalSystemExamination);

		Long result = generalOPDServiceImpl.saveBenExaminationDetails(examinationDetailsOBJ, benVisitID, benVisitCode);

		assertEquals(1L, result);
		verify(commonNurseServiceImpl, times(1))
				.saveSysMusculoskeletalSystemExamination(any(SysMusculoskeletalSystemExamination.class));
	}

	@Test
	public void testSaveBenExaminationDetails_GenitourinarySystemExamination() throws Exception {
		JsonObject genitourinarySystemExamination = new JsonObject();
		genitourinarySystemExamination.addProperty("someProperty", "someValue");
		examinationDetailsOBJ.add("genitoUrinarySystemExamination", genitourinarySystemExamination);

		Long result = generalOPDServiceImpl.saveBenExaminationDetails(examinationDetailsOBJ, benVisitID, benVisitCode);

		assertEquals(1L, result);
		verify(commonNurseServiceImpl, times(1))
				.saveSysGenitourinarySystemExamination(any(SysGenitourinarySystemExamination.class));
	}

	@Test
	public void testSaveBenExaminationDetails_OralExamination() throws Exception {
		JsonObject oralDetails = new JsonObject();
		oralDetails.addProperty("someProperty", "someValue");
		examinationDetailsOBJ.add("oralDetails", oralDetails);

		Long result = generalOPDServiceImpl.saveBenExaminationDetails(examinationDetailsOBJ, benVisitID, benVisitCode);

		assertEquals(1L, result);
		verify(commonNurseServiceImpl, times(1)).saveOralExamination(any(OralExamination.class));
	}

	@Test
	public void testSaveDoctorData_Success() throws Exception {
		// Setup
		JsonObject findings = new JsonObject();
		findings.addProperty("key", "value");
		requestOBJ.add("findings", findings);

		JsonObject diagnosis = new JsonObject();
		diagnosis.addProperty("key", "value");
		requestOBJ.add("diagnosis", diagnosis);

		JsonObject investigation = new JsonObject();
		JsonArray laboratoryList = new JsonArray();
		investigation.add("laboratoryList", laboratoryList);
		requestOBJ.add("investigation", investigation);

		JsonArray prescription = new JsonArray();
		requestOBJ.add("prescription", prescription);

		JsonObject refer = new JsonObject();
		refer.addProperty("key", "value");
		requestOBJ.add("refer", refer);

		CommonUtilityClass commonUtilityClass = new CommonUtilityClass();
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
				.thenReturn(null);
		when(commonNurseServiceImpl.saveBenPrescription(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenInvestigation(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenPrescribedDrugsList(any())).thenReturn(1);
		when(commonDoctorServiceImpl.saveBenReferDetails(any(), eq(false))).thenReturn(1L);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataSave(any(), eq(false), eq(false), eq(null)))
				.thenReturn(1);

		// Execute
		Long result = generalOPDServiceImpl.saveDoctorData(requestOBJ, authorization);

		// Verify
		assertNotNull(result);
		assertEquals(1L, result);
		verify(commonNurseServiceImpl, times(1)).saveBenPrescription(any());
		verify(commonNurseServiceImpl, times(1)).saveBenInvestigation(any());
		verify(commonNurseServiceImpl, times(1)).saveBenPrescribedDrugsList(any());
		verify(commonDoctorServiceImpl, times(1)).saveBenReferDetails(any(), eq(false));
		verify(commonDoctorServiceImpl, times(1)).updateBenFlowtableAfterDocDataSave(any(), eq(false), eq(false),
				eq(null));
	}

	@Test
	public void testSaveDoctorData_NullRequest() throws Exception {
		// Execute
		Long result = generalOPDServiceImpl.saveDoctorData(null, authorization);

		// Verify
		assertNull(result);
	}

	@Test
	public void testSaveDoctorData_Exception() throws Exception {
		// Setup
		JsonObject findings = new JsonObject();
		findings.addProperty("key", "value");
		requestOBJ.add("findings", findings);

		when(commonNurseServiceImpl.saveBenPrescription(any())).thenThrow(new RuntimeException("Error"));

		// Execute & Verify
		assertThrows(RuntimeException.class, () -> {
			generalOPDServiceImpl.saveDoctorData(requestOBJ, authorization);
		});
	}

	@Test
	public void testGetBenVisitDetailsFrmNurseGOPD() {
		// Mocking the return values
		when(commonNurseServiceImpl.getCSVisitDetails(benRegID, visitCode)).thenReturn(visitDetail);
		when(commonNurseServiceImpl.getBenAdherence(benRegID, visitCode)).thenReturn("Adherence");
		when(commonNurseServiceImpl.getBenChiefComplaints(benRegID, visitCode)).thenReturn("ChiefComplaints");
		when(commonNurseServiceImpl.getBenCdss(benRegID, visitCode)).thenReturn("Cdss");

		// Expected response
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("GOPDNurseVisitDetail", gson.toJson(visitDetail));
		resMap.put("BenAdherence", "Adherence");
		resMap.put("BenChiefComplaints", "ChiefComplaints");
		resMap.put("Cdss", "Cdss");
		String expectedResponse = resMap.toString();

		// Actual response
		String actualResponse = generalOPDServiceImpl.getBenVisitDetailsFrmNurseGOPD(benRegID, visitCode);

		// Assertion
		assertEquals(expectedResponse, actualResponse);
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

		String expectedResponse = new Gson().toJson(historyDetailsMap);
		String actualResponse = generalOPDServiceImpl.getBenHistoryDetails(benRegID, visitCode);

		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void testGetBeneficiaryVitalDetails() {
		when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, benVisitID))
				.thenReturn(benAnthropometryDetail);
		when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, benVisitID))
				.thenReturn(benPhysicalVitalDetail);

		String response = generalOPDServiceImpl.getBeneficiaryVitalDetails(beneficiaryRegID, benVisitID);

		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("benAnthropometryDetail", benAnthropometryDetail);
		expectedMap.put("benPhysicalVitalDetail", benPhysicalVitalDetail);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String expectedResponse = gson.toJson(expectedMap);

		assertEquals(expectedResponse, response);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID,
				benVisitID);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, benVisitID);
	}

	@Test
	public void testGetBeneficiaryVitalDetails_NoData() {
		when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, benVisitID))
				.thenReturn(null);
		when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, benVisitID)).thenReturn(null);

		String response = generalOPDServiceImpl.getBeneficiaryVitalDetails(beneficiaryRegID, benVisitID);

		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("benAnthropometryDetail", null);
		expectedMap.put("benPhysicalVitalDetail", null);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String expectedResponse = gson.toJson(expectedMap);

		assertEquals(expectedResponse, response);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID,
				benVisitID);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, benVisitID);
	}

	@Test
	public void testGetBeneficiaryCdssDetails() {
		// Arrange
		Map<String, Object> cdssDetails = new HashMap<>();
		cdssDetails.put("presentChiefComplaint", "Chief Complaint Data");
		cdssDetails.put("diseaseSummary", "Disease Summary Data");

		when(commonNurseServiceImpl.getBenCdssDetails(beneficiaryRegID, benVisitID)).thenReturn(cdssDetails);

		// Act
		String response = generalOPDServiceImpl.getBeneficiaryCdssDetails(beneficiaryRegID, benVisitID);

		// Assert
		assertNotNull(response);
		assertTrue(response.contains("presentChiefComplaint"));
		assertTrue(response.contains("diseaseSummary"));
		verify(commonNurseServiceImpl, times(2)).getBenCdssDetails(beneficiaryRegID, benVisitID);
	}

	@Test
	public void testGetExaminationDetailsData() throws IEMRException {
		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("generalExamination", "General Examination Data");
		expectedMap.put("headToToeExamination", "Head to Toe Examination Data");
		expectedMap.put("gastrointestinalExamination", "Gastrointestinal Examination Data");
		expectedMap.put("cardiovascularExamination", "Cardiovascular Examination Data");
		expectedMap.put("respiratoryExamination", "Respiratory Examination Data");
		expectedMap.put("centralNervousExamination", "Central Nervous Examination Data");
		expectedMap.put("musculoskeletalExamination", "Musculoskeletal Examination Data");
		expectedMap.put("genitourinaryExamination", "Genitourinary Examination Data");
		expectedMap.put("oralDetails", "Oral Examination Data");

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
		when(commonNurseServiceImpl.getOralExamination(benRegID, visitCode)).thenReturn("Oral Examination Data");

		String response = generalOPDServiceImpl.getExaminationDetailsData(benRegID, visitCode);
		Map<String, Object> actualMap = gson.fromJson(response, Map.class);

		assertEquals(expectedMap, actualMap);

		verify(commonNurseServiceImpl, times(1)).getGeneralExaminationData(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getHeadToToeExaminationData(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getSysGastrointestinalExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getCardiovascularExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getRespiratoryExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getSysCentralNervousExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getMusculoskeletalExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getGenitourinaryExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getOralExamination(benRegID, visitCode);
	}

	@Test
	public void testUpdateVisitDetails_ValidJsonWithChiefComplaints() throws Exception {
		BenChiefComplaint[] benChiefComplaintArray = InputMapper.gson()
				.fromJson(validJsonWithChiefComplaints.get("chiefComplaints"), BenChiefComplaint[].class);
		List<BenChiefComplaint> benChiefComplaintList = Arrays.asList(benChiefComplaintArray);

		when(commonNurseServiceImpl.updateBenChiefComplaints(benChiefComplaintList)).thenReturn(1);

		int result = generalOPDServiceImpl.UpdateVisitDetails(validJsonWithChiefComplaints);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).updateBenChiefComplaints(benChiefComplaintList);
	}

	@Test
	public void testUpdateVisitDetails_ValidJsonWithoutChiefComplaints() throws Exception {
		int result = generalOPDServiceImpl.UpdateVisitDetails(validJsonWithoutChiefComplaints);

		assertEquals(0, result);
		verify(commonNurseServiceImpl, times(0)).updateBenChiefComplaints(anyList());
	}

	@Test
	public void testUpdateVisitDetails_NullJson() throws Exception {
		int result = generalOPDServiceImpl.UpdateVisitDetails(nullJson);

		assertEquals(0, result);
		verify(commonNurseServiceImpl, times(0)).updateBenChiefComplaints(anyList());
	}

	@Test
	public void testUpdateVisitDetails_JsonWithoutVisitDetails() throws Exception {
		int result = generalOPDServiceImpl.UpdateVisitDetails(jsonWithoutVisitDetails);

		assertEquals(0, result);
		verify(commonNurseServiceImpl, times(0)).updateBenChiefComplaints(anyList());
	}

	@Test
	public void testUpdateBenHistoryDetails_AllFields() throws Exception {
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

		int result = generalOPDServiceImpl.updateBenHistoryDetails(historyOBJ);

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
	public void testUpdateBenHistoryDetails_SomeFieldsMissing() throws Exception {
		historyOBJ.remove("pastHistory");
		historyOBJ.remove("comorbidConditions");

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

		int result = generalOPDServiceImpl.updateBenHistoryDetails(historyOBJ);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, never()).updateBenPastHistoryDetails(any());
		verify(commonNurseServiceImpl, never()).updateBenComorbidConditions(any());
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
	public void testUpdateBenHistoryDetails_NullInput() throws Exception {
		int result = generalOPDServiceImpl.updateBenHistoryDetails(null);

		assertEquals(0, result);
		verify(commonNurseServiceImpl, never()).updateBenPastHistoryDetails(any());
		verify(commonNurseServiceImpl, never()).updateBenComorbidConditions(any());
		verify(commonNurseServiceImpl, never()).updateBenMedicationHistory(any());
		verify(commonNurseServiceImpl, never()).updateBenPersonalHistory(any());
		verify(commonNurseServiceImpl, never()).updateBenAllergicHistory(any());
		verify(commonNurseServiceImpl, never()).updateBenFamilyHistory(any());
		verify(commonNurseServiceImpl, never()).updateMenstrualHistory(any());
		verify(commonNurseServiceImpl, never()).updatePastObstetricHistory(any());
		verify(commonNurseServiceImpl, never()).updateChildImmunizationDetail(any());
		verify(commonNurseServiceImpl, never()).updateChildOptionalVaccineDetail(any());
		verify(commonNurseServiceImpl, never()).updateChildFeedingHistory(any());
		verify(commonNurseServiceImpl, never()).updatePerinatalHistory(any());
		verify(commonNurseServiceImpl, never()).updateChildDevelopmentHistory(any());
	}

	@Test
	public void testUpdateBenVitalDetails_Success() throws Exception {
		BenAnthropometryDetail benAnthropometryDetail = new BenAnthropometryDetail();
		BenPhysicalVitalDetail benPhysicalVitalDetail = new BenPhysicalVitalDetail();

		when(commonNurseServiceImpl.updateANCAnthropometryDetails(any(BenAnthropometryDetail.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class))).thenReturn(1);

		int result = generalOPDServiceImpl.updateBenVitalDetails(vitalDetailsOBJ);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(1)).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testUpdateBenVitalDetails_NullVitalDetailsOBJ() throws Exception {
		int result = generalOPDServiceImpl.updateBenVitalDetails(null);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, never()).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, never()).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testUpdateBenVitalDetails_FailureInAnthropometryDetails() throws Exception {
		BenAnthropometryDetail benAnthropometryDetail = new BenAnthropometryDetail();
		BenPhysicalVitalDetail benPhysicalVitalDetail = new BenPhysicalVitalDetail();

		when(commonNurseServiceImpl.updateANCAnthropometryDetails(any(BenAnthropometryDetail.class))).thenReturn(0);
		when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class))).thenReturn(1);

		int result = generalOPDServiceImpl.updateBenVitalDetails(vitalDetailsOBJ);

		assertEquals(0, result);
		verify(commonNurseServiceImpl, times(1)).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(1)).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testUpdateBenVitalDetails_FailureInPhysicalVitalDetails() throws Exception {
		BenAnthropometryDetail benAnthropometryDetail = new BenAnthropometryDetail();
		BenPhysicalVitalDetail benPhysicalVitalDetail = new BenPhysicalVitalDetail();

		when(commonNurseServiceImpl.updateANCAnthropometryDetails(any(BenAnthropometryDetail.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class))).thenReturn(0);

		int result = generalOPDServiceImpl.updateBenVitalDetails(vitalDetailsOBJ);

		assertEquals(0, result);
		verify(commonNurseServiceImpl, times(1)).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(1)).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testUpdateBenExaminationDetails() throws Exception {
		int result = generalOPDServiceImpl.updateBenExaminationDetails(examinationDetailsOBJ);

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
		verify(commonNurseServiceImpl, times(1)).updateOralExamination(any(OralExamination.class));
	}

	@Test
	public void testGetBenGeneralOPDNurseData() throws Exception {
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

		when(commonNurseServiceImpl.getGeneralExaminationData(benRegID, visitCode))
				.thenReturn("generalExaminationData");
		when(commonNurseServiceImpl.getHeadToToeExaminationData(benRegID, visitCode))
				.thenReturn("headToToeExaminationData");
		when(commonNurseServiceImpl.getSysGastrointestinalExamination(benRegID, visitCode))
				.thenReturn("gastrointestinalExaminationData");
		when(commonNurseServiceImpl.getCardiovascularExamination(benRegID, visitCode))
				.thenReturn("cardiovascularExaminationData");
		when(commonNurseServiceImpl.getRespiratoryExamination(benRegID, visitCode))
				.thenReturn("respiratoryExaminationData");
		when(commonNurseServiceImpl.getSysCentralNervousExamination(benRegID, visitCode))
				.thenReturn("centralNervousExaminationData");
		when(commonNurseServiceImpl.getMusculoskeletalExamination(benRegID, visitCode))
				.thenReturn("musculoskeletalExaminationData");
		when(commonNurseServiceImpl.getGenitourinaryExamination(benRegID, visitCode))
				.thenReturn("genitourinaryExaminationData");
		when(commonNurseServiceImpl.getOralExamination(benRegID, visitCode)).thenReturn("oralDetailsData");

		when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(benRegID, visitCode))
				.thenReturn("benAnthropometryDetailData");
		when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(benRegID, visitCode))
				.thenReturn("benPhysicalVitalDetailData");

		when(commonNurseServiceImpl.getBenCdssDetails(benRegID, visitCode)).thenReturn("presentChiefComplaintData");

		String response = generalOPDServiceImpl.getBenGeneralOPDNurseData(benRegID, visitCode);

		assertTrue(response.contains(new Gson().toJson(expectedHistory)));
		assertTrue(response.contains(new Gson().toJson(expectedExamination)));
		assertTrue(response.contains(new Gson().toJson(expectedVitals)));
		assertTrue(response.contains(new Gson().toJson(expectedCdss)));

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

		verify(commonNurseServiceImpl, times(1)).getGeneralExaminationData(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getHeadToToeExaminationData(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getSysGastrointestinalExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getCardiovascularExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getRespiratoryExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getSysCentralNervousExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getMusculoskeletalExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getGenitourinaryExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getOralExamination(benRegID, visitCode);

		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalVitalDetails(benRegID, visitCode);

		verify(commonNurseServiceImpl, times(1)).getBenCdssDetails(benRegID, visitCode);
	}

	@Test
	public void testGetBenCaseRecordFromDoctorGeneralOPD() throws IEMRException {
		// Mocking the responses
		when(commonDoctorServiceImpl.getFindingsDetails(benRegID, visitCode)).thenReturn("Findings");
		when(generalOPDDoctorServiceImpl.getGeneralOPDDiagnosisDetails(benRegID, visitCode)).thenReturn("Diagnosis");
		when(commonDoctorServiceImpl.getInvestigationDetails(benRegID, visitCode)).thenReturn("Investigation");
		when(commonDoctorServiceImpl.getPrescribedDrugs(benRegID, visitCode)).thenReturn("Prescription");
		when(commonDoctorServiceImpl.getReferralDetails(benRegID, visitCode, false)).thenReturn("Refer");
		when(labTechnicianServiceImpl.getLabResultDataForBen(benRegID, visitCode)).thenReturn(new HashMap<>());
		when(commonNurseServiceImpl.getGraphicalTrendData(benRegID, "General OPD")).thenReturn(new HashMap<>());
		when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(benRegID, visitCode)).thenReturn(new HashMap<>());

		// Call the method to test
		String response = generalOPDServiceImpl.getBenCaseRecordFromDoctorGeneralOPD(benRegID, visitCode);

		// Verify the interactions
		verify(commonDoctorServiceImpl, times(1)).getFindingsDetails(benRegID, visitCode);
		verify(generalOPDDoctorServiceImpl, times(1)).getGeneralOPDDiagnosisDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getInvestigationDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getPrescribedDrugs(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getReferralDetails(benRegID, visitCode, false);
		verify(labTechnicianServiceImpl, times(1)).getLabResultDataForBen(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getGraphicalTrendData(benRegID, "General OPD");
		verify(labTechnicianServiceImpl, times(1)).getLast_3_ArchivedTestVisitList(benRegID, visitCode);

		// Parse the response
		Gson gson = new Gson();
		Map<String, Object> responseMap = gson.fromJson(response, Map.class);

		// Assertions
		assertNotNull(responseMap);
		assertEquals("Findings", responseMap.get("findings"));
		assertEquals("Diagnosis", responseMap.get("diagnosis"));
		assertEquals("Investigation", responseMap.get("investigation"));
		assertEquals("Prescription", responseMap.get("prescription"));
		assertEquals("Refer", responseMap.get("Refer"));
		assertNotNull(responseMap.get("LabReport"));
		assertNotNull(responseMap.get("GraphData"));
		assertNotNull(responseMap.get("ArchivedVisitcodeForLabResult"));
	}

	@Test
	public void testUpdateGeneralOPDDoctorData_Success() throws Exception {
		// Setup mock behavior
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
				.thenReturn(null);
		when(commonDoctorServiceImpl.updateDocFindings(any())).thenReturn(1);
		when(commonNurseServiceImpl.updatePrescription(any())).thenReturn(1);
		when(commonNurseServiceImpl.saveBenInvestigation(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenPrescribedDrugsList(any())).thenReturn(1);
		when(commonDoctorServiceImpl.updateBenReferDetails(any(JsonObject.class), eq(false))).thenReturn(1L);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(any(CommonUtilityClass.class), anyBoolean(),
				anyBoolean(), any())).thenReturn(1);

		// Call the method under test
		Long result = generalOPDServiceImpl.updateGeneralOPDDoctorData(requestOBJ, authorization);

		// Verify the result
		assertNotNull(result);
		assertEquals(1L, result);

		// Verify interactions with mocks
		verify(commonServiceImpl, times(1)).createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class),
				eq(authorization));
		verify(commonDoctorServiceImpl, times(1)).updateDocFindings(any());
		verify(commonNurseServiceImpl, times(1)).updatePrescription(any());
		verify(commonNurseServiceImpl, times(1)).saveBenInvestigation(any());
		verify(commonNurseServiceImpl, times(1)).saveBenPrescribedDrugsList(any());
		verify(commonDoctorServiceImpl, times(1)).updateBenReferDetails(any(JsonObject.class), eq(false));
		verify(commonDoctorServiceImpl, times(1)).updateBenFlowtableAfterDocDataUpdate(any(CommonUtilityClass.class),
				anyBoolean(), anyBoolean(), any());
	}

	@Test
	public void testUpdateGeneralOPDDoctorData_NullRequest() throws Exception {
		// Call the method under test with null requestOBJ
		Long result = generalOPDServiceImpl.updateGeneralOPDDoctorData(null, authorization);

		// Verify the result
		assertNull(result);

		// Verify no interactions with mocks
		verifyNoInteractions(commonServiceImpl, commonDoctorServiceImpl, commonNurseServiceImpl);
	}

	@Test
	public void testUpdateGeneralOPDDoctorData_Exception() throws Exception {
		// Setup mock behavior to throw an exception
		when(commonDoctorServiceImpl.updateDocFindings(any())).thenThrow(new RuntimeException("Test Exception"));

		// Call the method under test and verify exception
		assertThrows(RuntimeException.class, () -> {
			generalOPDServiceImpl.updateGeneralOPDDoctorData(requestOBJ, authorization);
		});

		// Verify interactions with mocks
		verify(commonDoctorServiceImpl, times(1)).updateDocFindings(any());
	}
}
