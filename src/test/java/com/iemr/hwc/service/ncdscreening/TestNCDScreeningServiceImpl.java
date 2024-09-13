package com.iemr.hwc.service.ncdscreening;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.iemr.hwc.data.ncdScreening.BreastCancerScreening;
import com.iemr.hwc.data.ncdScreening.CbacDetails;
import com.iemr.hwc.data.ncdScreening.CervicalCancerScreening;
import com.iemr.hwc.data.ncdScreening.DiabetesScreening;
import com.iemr.hwc.data.ncdScreening.HypertensionScreening;
import com.iemr.hwc.data.ncdScreening.NCDScreening;
import com.iemr.hwc.data.ncdScreening.OralCancerScreening;
import com.iemr.hwc.data.nurse.BenAnthropometryDetail;
import com.iemr.hwc.data.nurse.BenPhysicalVitalDetail;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.nurse.CDSSRepo;
import com.iemr.hwc.repo.nurse.anc.BenAdherenceRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.BreastCancerScreeningRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.CbacDetailsRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.CervicalCancerScreeningRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.DiabetesScreeningRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.HypertensionScreeningRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.IDRSDataRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.OralCancerScreeningRepo;
import com.iemr.hwc.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonServiceImpl;
import com.iemr.hwc.service.labtechnician.LabTechnicianServiceImpl;
import com.iemr.hwc.service.tele_consultation.SMSGatewayServiceImpl;
import com.iemr.hwc.service.tele_consultation.TeleConsultationServiceImpl;
import com.iemr.hwc.utils.exception.IEMRException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class TestNCDScreeningServiceImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private NCDScreeningNurseServiceImpl ncdScreeningNurseServiceImpl;
	@Mock
	private CommonNurseServiceImpl commonNurseServiceImpl;
	@Mock
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;
	@Mock
	private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;
	@Mock
	private LabTechnicianServiceImpl labTechnicianServiceImpl;
	@Mock
	private CommonDoctorServiceImpl commonDoctorServiceImpl;
	@Mock
	private CbacDetailsRepo cbacDetailsRepo;
	@Mock
	private CommonServiceImpl commonServiceImpl;
	@Mock
	private TeleConsultationServiceImpl teleConsultationServiceImpl;
	@Mock
	private BenVisitDetailRepo benVisitDetailRepo;
	@Mock
	private SMSGatewayServiceImpl sMSGatewayServiceImpl;
	@Mock
	private PrescriptionDetailRepo prescriptionDetailRepo;
	@Mock
	private NCDSCreeningDoctorServiceImpl ncdSCreeningDoctorServiceImpl;
	@Mock
	private IDRSDataRepo iDrsDataRepo;
	@Mock
	private BreastCancerScreeningRepo breastCancerScreeningRepo;
	@Mock
	private CervicalCancerScreeningRepo cervicalCancerScreeningRepo;
	@Mock
	private DiabetesScreeningRepo diabetesScreeningRepo;
	@Mock
	private HypertensionScreeningRepo hypertensionScreeningRepo;
	@Mock
	private OralCancerScreeningRepo oralCancerScreeningRepo;
	@Mock
	private CommonNcdScreeningService commonNcdScreeningService;
	@Mock
	private BenChiefComplaintRepo benChiefComplaintRepo;
	@Mock
	private BenAdherenceRepo benAdherenceRepo;
	@Mock
	private CDSSRepo cdssRepo;

	@InjectMocks
	private NCDScreeningServiceImpl ncdScreeningServiceImpl;

	private JsonObject requestObj;
	private String authorization;
	private JsonObject tmpOBJ;
	private Long benVisitID;
	private Long benFlowID;
	private Long benVisitCode;
	private Integer vanID;
	private TeleconsultationRequestOBJ tcRequestOBJ;
	private boolean isDocVisitRequired;
	private JsonObject visitDetailsOBJ;
	private CommonUtilityClass nurseUtilityClass;
	private JsonObject jsonObject;
	private JsonObject validInput;
	private JsonObject missingOptionalFieldsInput;
	private JsonObject nullInput;

	private JsonObject validRequestWithQuestionArray;
	private JsonObject validRequestWithoutQuestionArray;
	private JsonObject nullRequest;
	private JsonObject validPhysicalActivityDetailsOBJ;
	private JsonObject missingFieldsInput;
	private NCDScreening ncdScreening;
	private BenAnthropometryDetail anthropometryDetail;
	private BenPhysicalVitalDetail physicalVitalDetail;
	private JsonObject validJsonObject;
	private JsonObject invalidJsonObject;
	private Gson gson;
	private Long beneficiaryRegID;
	private Long visitCode;
	private Long benRegID;
	private Map<String, Object> expectedResponse;
	private Map<String, Object> mockResponse;
	private BenAnthropometryDetail benAnthropometryDetail;
	private BenPhysicalVitalDetail benPhysicalVitalDetail;
	private JsonObject historyOBJ;
	private BeneficiaryVisitDetail visitDetail;
	private Map<String, Object> resMap;
	private DiabetesScreening diabetesScreening;
	private HypertensionScreening hypertensionScreening;
	private BreastCancerScreening breastCancerScreening;
	private CervicalCancerScreening cervicalCancerScreening;
	private OralCancerScreening oralCancerScreening;
	private CbacDetails cbacDetails;

	private NCDScreeningServiceImpl ncdScreeningService;

	private Long beneficiaryRegId;
	private JsonObject requestOBJ;

	@BeforeEach
	public void setUp() {
		requestObj = new JsonObject();
		JsonObject visitDetails = new JsonObject();
		visitDetails.addProperty("visitDetails", "details");
		requestObj.add("visitDetails", visitDetails);
		authorization = "Bearer token";
	}

	@Test
	public void testSaveNCDScreeningNurseData_Success() throws Exception {
		// Mocking dependencies
		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any())).thenReturn(1L);
		when(commonNurseServiceImpl.generateVisitCode(anyLong(), anyInt(), anyInt())).thenReturn(1L);
		when(commonServiceImpl.createTcRequest(any(), any(), eq(authorization))).thenReturn(null);
		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(), anyLong(),
				anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(),
				anyInt(), anyShort(), any(), any())).thenReturn(1);

		// Call the method under test
		String response = ncdScreeningServiceImpl.saveNCDScreeningNurseData(requestObj, authorization);

		// Assertions
		assertTrue(response.contains("Data saved successfully"));
		verify(commonNurseServiceImpl, times(1)).saveBeneficiaryVisitDetails(any());
		verify(commonNurseServiceImpl, times(1)).generateVisitCode(anyLong(), anyInt(), anyInt());
		verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(),
				anyLong(), anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(),
				anyLong(), anyInt(), anyShort(), any(), any());
	}

	@Test
	public void testSaveNCDScreeningNurseData_InvalidInput() {
		// Call the method under test with invalid input
		Exception exception = assertThrows(Exception.class, () -> {
			ncdScreeningServiceImpl.saveNCDScreeningNurseData(null, authorization);
		});

		// Assertions
		assertEquals("Invalid input", exception.getMessage());
	}

	@Test
	public void testSaveNCDScreeningNurseData_DataAlreadySaved() throws Exception {
		// Mocking dependencies
		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any())).thenReturn(null);

		// Call the method under test
		String response = ncdScreeningServiceImpl.saveNCDScreeningNurseData(requestObj, authorization);

		// Assertions
		assertTrue(response.contains("Data already saved"));
		verify(commonNurseServiceImpl, times(1)).saveBeneficiaryVisitDetails(any());
	}

	@Test
	public void testSaveNCDScreeningNurseData_ErrorWhileSavingData() throws Exception {
		// Mocking dependencies
		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any())).thenReturn(1L);
		when(commonNurseServiceImpl.generateVisitCode(anyLong(), anyInt(), anyInt())).thenReturn(1L);
		when(commonServiceImpl.createTcRequest(any(), any(), eq(authorization))).thenReturn(null);
		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(), anyLong(),
				anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(),
				anyInt(), anyShort(), any(), any())).thenReturn(0);

		// Call the method under test
		Exception exception = assertThrows(RuntimeException.class, () -> {
			ncdScreeningServiceImpl.saveNCDScreeningNurseData(requestObj, authorization);
		});

		// Assertions
		assertEquals("Error occurred while saving data. Beneficiary status update failed", exception.getMessage());
	}

	@Test
	public void testUpdateBenFlowNurseAfterNurseActivityANC_DocVisitNotRequired() {
		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(eq(benFlowID), eq(1L), eq(benVisitID),
				eq("Routine Checkup"), eq("General"), eq((short) 9), eq((short) 9), eq((short) 0), eq((short) 0),
				eq((short) 0), eq(benVisitCode), eq(vanID), eq((short) 0), isNull(), isNull())).thenReturn(1);

		int result = ncdScreeningServiceImpl.updateBenFlowNurseAfterNurseActivityANC(tmpOBJ, benVisitID, benFlowID,
				benVisitCode, vanID, tcRequestOBJ, isDocVisitRequired);

		assertEquals(1, result);
		verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseActivity(eq(benFlowID), eq(1L),
				eq(benVisitID), eq("Routine Checkup"), eq("General"), eq((short) 9), eq((short) 9), eq((short) 0),
				eq((short) 0), eq((short) 0), eq(benVisitCode), eq(vanID), eq((short) 0), isNull(), isNull());
	}

	@Test
	public void testUpdateBenFlowNurseAfterNurseActivityANC_DocVisitRequired() {
		isDocVisitRequired = true;

		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(eq(benFlowID), eq(1L), eq(benVisitID),
				eq("Routine Checkup"), eq("General"), eq((short) 9), eq((short) 1), eq((short) 0), eq((short) 0),
				eq((short) 0), eq(benVisitCode), eq(vanID), eq((short) 0), isNull(), isNull())).thenReturn(1);

		int result = ncdScreeningServiceImpl.updateBenFlowNurseAfterNurseActivityANC(tmpOBJ, benVisitID, benFlowID,
				benVisitCode, vanID, tcRequestOBJ, isDocVisitRequired);

		assertEquals(1, result);
		verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseActivity(eq(benFlowID), eq(1L),
				eq(benVisitID), eq("Routine Checkup"), eq("General"), eq((short) 9), eq((short) 1), eq((short) 0),
				eq((short) 0), eq((short) 0), eq(benVisitCode), eq(vanID), eq((short) 0), isNull(), isNull());
	}

	@Test
	public void testUpdateBenFlowNurseAfterNurseActivityANC_WithTeleconsultation() {
		tcRequestOBJ = new TeleconsultationRequestOBJ();
		tcRequestOBJ.setUserID(1);
		tcRequestOBJ.setAllocationDate(new Timestamp(System.currentTimeMillis()));

		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(eq(benFlowID), eq(1L), eq(benVisitID),
				eq("Routine Checkup"), eq("General"), eq((short) 9), eq((short) 9), eq((short) 0), eq((short) 0),
				eq((short) 0), eq(benVisitCode), eq(vanID), eq((short) 1), any(Timestamp.class), eq(1))).thenReturn(1);

		int result = ncdScreeningServiceImpl.updateBenFlowNurseAfterNurseActivityANC(tmpOBJ, benVisitID, benFlowID,
				benVisitCode, vanID, tcRequestOBJ, isDocVisitRequired);

		assertEquals(1, result);
		verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseActivity(eq(benFlowID), eq(1L),
				eq(benVisitID), eq("Routine Checkup"), eq("General"), eq((short) 9), eq((short) 9), eq((short) 0),
				eq((short) 0), eq((short) 0), eq(benVisitCode), eq(vanID), eq((short) 1), any(Timestamp.class), eq(1));
	}

	@Test
	public void testSaveBenVisitDetails_Success() throws Exception {
		BeneficiaryVisitDetail benVisitDetailsOBJ = InputMapper.gson().fromJson(visitDetailsOBJ.get("visitDetails"),
				BeneficiaryVisitDetail.class);
		when(commonNurseServiceImpl.getMaxCurrentdate(anyLong(), anyString(), anyString())).thenReturn(0);
		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class))).thenReturn(1L);
		when(commonNurseServiceImpl.generateVisitCode(anyLong(), anyInt(), anyInt())).thenReturn(1L);

		Map<String, Long> result = ncdScreeningServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass);

		assertNotNull(result);
		assertTrue(result.containsKey("visitID"));
		assertTrue(result.containsKey("visitCode"));
		assertEquals(1L, result.get("visitID"));
		assertEquals(1L, result.get("visitCode"));
	}

	@Test
	public void testSaveBenVisitDetails_DataAlreadySaved() throws Exception {
		when(commonNurseServiceImpl.getMaxCurrentdate(anyLong(), anyString(), anyString())).thenReturn(1);

		Map<String, Long> result = ncdScreeningServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass);

		assertTrue(result.isEmpty());
	}

	@Test
	public void testSaveBenVisitDetails_NullInput() throws Exception {
		Map<String, Long> result = ncdScreeningServiceImpl.saveBenVisitDetails(null, nurseUtilityClass);

		assertTrue(result.isEmpty());
	}

	@Test
	public void testSaveBenVisitDetails_InvalidInput() throws Exception {
		JsonObject invalidVisitDetailsOBJ = new JsonObject();
		invalidVisitDetailsOBJ.addProperty("invalidKey", "invalidValue");

		Map<String, Long> result = ncdScreeningServiceImpl.saveBenVisitDetails(invalidVisitDetailsOBJ,
				nurseUtilityClass);

		assertTrue(result.isEmpty());
	}

	@Test
	public void testSaveNCDScreeningVitalDetails_Success() throws Exception {
		BenAnthropometryDetail anthropometryDetail = new BenAnthropometryDetail();
		anthropometryDetail.setBenVisitID(benVisitID);
		anthropometryDetail.setVisitCode(benVisitCode);

		BenPhysicalVitalDetail physicalVitalDetail = new BenPhysicalVitalDetail();
		physicalVitalDetail.setBenVisitID(benVisitID);
		physicalVitalDetail.setVisitCode(benVisitCode);

		when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(any(BenAnthropometryDetail.class)))
				.thenReturn(1L);
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(any(BenPhysicalVitalDetail.class)))
				.thenReturn(1L);

		Long result = ncdScreeningServiceImpl.saveNCDScreeningVitalDetails(jsonObject, benVisitID, benVisitCode);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(commonNurseServiceImpl, times(1))
				.saveBeneficiaryPhysicalAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(1)).saveBeneficiaryPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testSaveNCDScreeningVitalDetails_Failure() throws Exception {
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(any(BenAnthropometryDetail.class)))
				.thenReturn(null);
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(any(BenPhysicalVitalDetail.class)))
				.thenReturn(null);

		Long result = ncdScreeningServiceImpl.saveNCDScreeningVitalDetails(jsonObject, benVisitID, benVisitCode);

		assertNull(result);
		verify(commonNurseServiceImpl, times(1))
				.saveBeneficiaryPhysicalAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(1)).saveBeneficiaryPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testSaveBenNCDCareHistoryDetails_validInput() throws Exception {
		when(commonNurseServiceImpl.saveBenPastHistory(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenComorbidConditions(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenMedicationHistory(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveFemaleObstetricHistory(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenMenstrualHistory(any())).thenReturn(1);
		when(commonNurseServiceImpl.saveBenFamilyHistoryNCDScreening(any())).thenReturn(1L);
		when(commonNurseServiceImpl.savePersonalHistory(any())).thenReturn(1);
		when(commonNurseServiceImpl.saveAllergyHistory(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveChildOptionalVaccineDetail(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveImmunizationHistory(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveChildDevelopmentHistory(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveChildFeedingHistory(any())).thenReturn(1L);
		when(commonNurseServiceImpl.savePerinatalHistory(any())).thenReturn(1L);

		Long result = ncdScreeningServiceImpl.saveBenNCDCareHistoryDetails(validInput, 1L, 1L);

		assertNotNull(result);
		assertEquals(1L, result);
	}

	@Test
	public void testSaveBenNCDCareHistoryDetails_missingOptionalFields() throws Exception {
		when(commonNurseServiceImpl.saveBenPastHistory(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenComorbidConditions(any())).thenReturn(1L);

		Long result = ncdScreeningServiceImpl.saveBenNCDCareHistoryDetails(missingOptionalFieldsInput, 1L, 1L);

		assertNotNull(result);
		assertEquals(1L, result);
	}

	@Test
	public void testSaveBenNCDCareHistoryDetails_nullInput() {
		assertThrows(Exception.class, () -> {
			ncdScreeningServiceImpl.saveBenNCDCareHistoryDetails(nullInput, 1L, 1L);
		});
	}

	@Test
	public void testSaveidrsDetailsWithQuestionArray() throws Exception {
		when(commonNurseServiceImpl.saveIDRS(any(IDRSData.class))).thenReturn(1L);

		Long result = ncdScreeningServiceImpl.saveidrsDetails(validRequestWithQuestionArray, 1L, 1L);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(commonNurseServiceImpl, times(1)).saveIDRS(any(IDRSData.class));
	}

	@Test
	public void testSaveidrsDetailsWithoutQuestionArray() throws Exception {
		when(commonNurseServiceImpl.saveIDRS(any(IDRSData.class))).thenReturn(1L);

		Long result = ncdScreeningServiceImpl.saveidrsDetails(validRequestWithoutQuestionArray, 1L, 1L);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(commonNurseServiceImpl, times(1)).saveIDRS(any(IDRSData.class));
	}

	@Test
	public void testSaveidrsDetailsWithNullRequest() throws Exception {
		Long result = ncdScreeningServiceImpl.saveidrsDetails(nullRequest, 1L, 1L);

		assertNull(result);
		verify(commonNurseServiceImpl, times(0)).saveIDRS(any(IDRSData.class));
	}

	@Test
	public void testSavePhysicalActivityDetails_NullInput() throws Exception {
		Long result = ncdScreeningServiceImpl.savePhysicalActivityDetails(null, benVisitID, benVisitCode);
		assertNull(result);
	}

	@Test
	public void testSavePhysicalActivityDetails_ValidInput() throws Exception {
		PhysicalActivityType physicalActivityDetail = new PhysicalActivityType();
		physicalActivityDetail.setBenVisitID(benVisitID);
		physicalActivityDetail.setVisitCode(benVisitCode);
		physicalActivityDetail.setActivityType("Walking");
		physicalActivityDetail.setDuration(30);

		when(commonNurseServiceImpl.savePhysicalActivity(any(PhysicalActivityType.class))).thenReturn(1L);

		Long result = ncdScreeningServiceImpl.savePhysicalActivityDetails(validPhysicalActivityDetailsOBJ, benVisitID,
				benVisitCode);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(commonNurseServiceImpl, times(1)).savePhysicalActivity(any(PhysicalActivityType.class));
	}

	@Test
	public void testUpdateNurseNCDScreeningDetails_Success() throws Exception {
		when(ncdScreeningNurseServiceImpl.updateNCDScreeningDetails(any(NCDScreening.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateANCAnthropometryDetails(any(BenAnthropometryDetail.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class))).thenReturn(1);
		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseUpdateNCD_Screening(anyLong(), anyLong(),
				anyShort())).thenReturn(1);

		Integer result = ncdScreeningServiceImpl.updateNurseNCDScreeningDetails(jsonObject);

		assertNotNull(result);
		assertEquals(1, result);
		verify(ncdScreeningNurseServiceImpl, times(1)).updateNCDScreeningDetails(any(NCDScreening.class));
		verify(commonNurseServiceImpl, times(1)).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(1)).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
		verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseUpdateNCD_Screening(anyLong(),
				anyLong(), anyShort());
	}

	@Test
	public void testUpdateNurseNCDScreeningDetails_Failure_UpdateNCDScreeningDetails() throws Exception {
		when(ncdScreeningNurseServiceImpl.updateNCDScreeningDetails(any(NCDScreening.class))).thenReturn(null);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			ncdScreeningServiceImpl.updateNurseNCDScreeningDetails(jsonObject);
		});

		assertEquals("Error occured while updating record in between...", exception.getMessage());
		verify(ncdScreeningNurseServiceImpl, times(1)).updateNCDScreeningDetails(any(NCDScreening.class));
		verify(commonNurseServiceImpl, times(0)).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(0)).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
		verify(commonBenStatusFlowServiceImpl, times(0)).updateBenFlowNurseAfterNurseUpdateNCD_Screening(anyLong(),
				anyLong(), anyShort());
	}

	@Test
	public void testUpdateNurseNCDScreeningDetails_Failure_UpdateANCAnthropometryDetails() throws Exception {
		when(ncdScreeningNurseServiceImpl.updateNCDScreeningDetails(any(NCDScreening.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateANCAnthropometryDetails(any(BenAnthropometryDetail.class))).thenReturn(null);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			ncdScreeningServiceImpl.updateNurseNCDScreeningDetails(jsonObject);
		});

		assertEquals("Error occured while updating record in between...", exception.getMessage());
		verify(ncdScreeningNurseServiceImpl, times(1)).updateNCDScreeningDetails(any(NCDScreening.class));
		verify(commonNurseServiceImpl, times(1)).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(0)).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
		verify(commonBenStatusFlowServiceImpl, times(0)).updateBenFlowNurseAfterNurseUpdateNCD_Screening(anyLong(),
				anyLong(), anyShort());
	}

	@Test
	public void testUpdateNurseNCDScreeningDetails_Failure_UpdateANCPhysicalVitalDetails() throws Exception {
		when(ncdScreeningNurseServiceImpl.updateNCDScreeningDetails(any(NCDScreening.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateANCAnthropometryDetails(any(BenAnthropometryDetail.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class))).thenReturn(null);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			ncdScreeningServiceImpl.updateNurseNCDScreeningDetails(jsonObject);
		});

		assertEquals("Error occured while updating record in between...", exception.getMessage());
		verify(ncdScreeningNurseServiceImpl, times(1)).updateNCDScreeningDetails(any(NCDScreening.class));
		verify(commonNurseServiceImpl, times(1)).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(1)).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
		verify(commonBenStatusFlowServiceImpl, times(0)).updateBenFlowNurseAfterNurseUpdateNCD_Screening(anyLong(),
				anyLong(), anyShort());
	}

	@Test
	public void testUpdateNurseNCDScreeningDetails_Failure_UpdateBenFlowNurseAfterNurseUpdateNCD_Screening()
			throws Exception {
		when(ncdScreeningNurseServiceImpl.updateNCDScreeningDetails(any(NCDScreening.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateANCAnthropometryDetails(any(BenAnthropometryDetail.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class))).thenReturn(1);
		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseUpdateNCD_Screening(anyLong(), anyLong(),
				anyShort())).thenReturn(0);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			ncdScreeningServiceImpl.updateNurseNCDScreeningDetails(jsonObject);
		});

		assertEquals("Error occured while updating record in between...", exception.getMessage());
		verify(ncdScreeningNurseServiceImpl, times(1)).updateNCDScreeningDetails(any(NCDScreening.class));
		verify(commonNurseServiceImpl, times(1)).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(1)).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
		verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseUpdateNCD_Screening(anyLong(),
				anyLong(), anyShort());
	}

	@Test
	public void testUpdateBenVitalDetails_Success() throws Exception {
		BenAnthropometryDetail benAnthropometryDetail = new BenAnthropometryDetail();
		BenPhysicalVitalDetail benPhysicalVitalDetail = new BenPhysicalVitalDetail();

		when(commonNurseServiceImpl.updateANCAnthropometryDetails(any(BenAnthropometryDetail.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class))).thenReturn(1);

		int result = ncdScreeningServiceImpl.updateBenVitalDetails(validJsonObject);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(1)).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testUpdateBenVitalDetails_Failure() throws Exception {
		BenAnthropometryDetail benAnthropometryDetail = new BenAnthropometryDetail();
		BenPhysicalVitalDetail benPhysicalVitalDetail = new BenPhysicalVitalDetail();

		when(commonNurseServiceImpl.updateANCAnthropometryDetails(any(BenAnthropometryDetail.class))).thenReturn(0);
		when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class))).thenReturn(0);

		int result = ncdScreeningServiceImpl.updateBenVitalDetails(validJsonObject);

		assertEquals(0, result);
		verify(commonNurseServiceImpl, times(1)).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(1)).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testUpdateBenVitalDetails_NullInput() throws Exception {
		int result = ncdScreeningServiceImpl.updateBenVitalDetails(invalidJsonObject);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(0)).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(0)).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testGetNCDScreeningDetails_Success() {
		String ncdScreeningDetails = "{\"screening\":\"details\"}";
		String anthropometryDetails = "{\"anthropometry\":\"details\"}";
		String vitalDetails = "{\"vital\":\"details\"}";

		when(ncdScreeningNurseServiceImpl.getNCDScreeningDetails(beneficiaryRegID, visitCode))
				.thenReturn(ncdScreeningDetails);
		when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, visitCode))
				.thenReturn(anthropometryDetails);
		when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode))
				.thenReturn(vitalDetails);

		String result = ncdScreeningServiceImpl.getNCDScreeningDetails(beneficiaryRegID, visitCode);

		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("ncdScreeningDetails", ncdScreeningDetails);
		expectedMap.put("anthropometryDetails", anthropometryDetails);
		expectedMap.put("vitalDetails", vitalDetails);

		assertEquals(expectedMap.toString(), result);
		verify(ncdScreeningNurseServiceImpl, times(1)).getNCDScreeningDetails(beneficiaryRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID,
				visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetNCDScreeningDetails_Failure() {
		String ncdScreeningDetails = null;
		String anthropometryDetails = "{\"anthropometry\":\"details\"}";
		String vitalDetails = "{\"vital\":\"details\"}";

		when(ncdScreeningNurseServiceImpl.getNCDScreeningDetails(beneficiaryRegID, visitCode))
				.thenReturn(ncdScreeningDetails);
		when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, visitCode))
				.thenReturn(anthropometryDetails);
		when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode))
				.thenReturn(vitalDetails);

		String result = ncdScreeningServiceImpl.getNCDScreeningDetails(beneficiaryRegID, visitCode);

		assertNotEquals(
				"{\"ncdScreeningDetails\":\"details\",\"anthropometryDetails\":\"details\",\"vitalDetails\":\"details\"}",
				result);
		verify(ncdScreeningNurseServiceImpl, times(1)).getNCDScreeningDetails(beneficiaryRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID,
				visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetNcdScreeningVisitCnt() {
		Long beneficiaryRegID = 1L;
		Long visitCount = 5L;

		when(beneficiaryFlowStatusRepo.getNcdScreeningVisitCount(beneficiaryRegID)).thenReturn(visitCount);

		String expectedJson = gson.toJson(Map.of("ncdScreeningVisitCount", visitCount + 1));
		String actualJson = ncdScreeningServiceImpl.getNcdScreeningVisitCnt(beneficiaryRegID);

		assertEquals(expectedJson, actualJson);
	}

	@Test
	public void testSaveDoctorData_Success() throws Exception {
		// Setup
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
				.thenReturn(null);
		when(commonNurseServiceImpl.saveBenPrescription(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenInvestigation(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenPrescribedDrugsList(any())).thenReturn(1);
		when(commonDoctorServiceImpl.saveBenReferDetails(any(), eq(true))).thenReturn(1L);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataSave(any(), anyBoolean(), anyBoolean(), any()))
				.thenReturn(1);

		// Execute
		Long result = ncdScreeningServiceImpl.saveDoctorData(requestObj, authorization);

		// Verify
		assertNotNull(result);
		assertEquals(1L, result);
		verify(commonServiceImpl, times(1)).createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class),
				eq(authorization));
		verify(commonNurseServiceImpl, times(1)).saveBenPrescription(any());
		verify(commonNurseServiceImpl, times(1)).saveBenInvestigation(any());
		verify(commonNurseServiceImpl, times(1)).saveBenPrescribedDrugsList(any());
		verify(commonDoctorServiceImpl, times(1)).saveBenReferDetails(any(), eq(true));
		verify(commonDoctorServiceImpl, times(1)).updateBenFlowtableAfterDocDataSave(any(), anyBoolean(), anyBoolean(),
				any());
	}

	@Test
	public void testSaveDoctorData_NullInput() throws Exception {
		// Execute
		Long result = ncdScreeningServiceImpl.saveDoctorData(null, authorization);

		// Verify
		assertNull(result);
		verifyNoInteractions(commonServiceImpl, commonNurseServiceImpl, commonDoctorServiceImpl, sMSGatewayServiceImpl);
	}

	@Test
	public void testSaveDoctorData_MissingFields() throws Exception {
		// Setup
		JsonObject incompleteRequestObj = new JsonObject();
		incompleteRequestObj.addProperty("incompleteKey", "value");

		// Execute
		Exception exception = assertThrows(RuntimeException.class, () -> {
			ncdScreeningServiceImpl.saveDoctorData(incompleteRequestObj, authorization);
		});

		// Verify
		assertNotNull(exception);
		verifyNoInteractions(commonServiceImpl, commonNurseServiceImpl, commonDoctorServiceImpl, sMSGatewayServiceImpl);
	}

	@Test
	public void testGetBenCaseRecordFromDoctorNCDScreening() {
		when(commonDoctorServiceImpl.getFindingsDetails(benRegID, visitCode)).thenReturn("findingsData");
		when(commonDoctorServiceImpl.getInvestigationDetails(benRegID, visitCode)).thenReturn("investigationData");
		when(commonDoctorServiceImpl.getPrescribedDrugs(benRegID, visitCode)).thenReturn("prescriptionData");
		when(commonDoctorServiceImpl.getReferralDetails(benRegID, visitCode, true)).thenReturn("referData");
		when(labTechnicianServiceImpl.getLabResultDataForBen(benRegID, visitCode)).thenReturn("labReportData");
		when(commonNurseServiceImpl.getGraphicalTrendData(benRegID, "ncdCare")).thenReturn("graphData");
		when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(benRegID, visitCode))
				.thenReturn("archivedVisitCodeData");
		when(ncdScreeningServiceImpl.getNCDDiagnosisData(benRegID, visitCode)).thenReturn("diagnosisData");

		String result = ncdScreeningServiceImpl.getBenCaseRecordFromDoctorNCDScreening(benRegID, visitCode);

		assertEquals(new Gson().toJson(expectedResponse), result);

		verify(commonDoctorServiceImpl, times(1)).getFindingsDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getInvestigationDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getPrescribedDrugs(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getReferralDetails(benRegID, visitCode, true);
		verify(labTechnicianServiceImpl, times(1)).getLabResultDataForBen(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getGraphicalTrendData(benRegID, "ncdCare");
		verify(labTechnicianServiceImpl, times(1)).getLast_3_ArchivedTestVisitList(benRegID, visitCode);
		verify(ncdScreeningServiceImpl, times(1)).getNCDDiagnosisData(benRegID, visitCode);
	}

	@Test
	public void testGetBenVisitDetailsFrmNurseNCDScreening() {
		String response = ncdScreeningServiceImpl.getBenVisitDetailsFrmNurseNCDScreening(benRegID, visitCode);

		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("NCDScreeningNurseVisitDetail", gson.toJson(visitDetail));
		expectedMap.put("BenChiefComplaints", "ChiefComplaints");
		expectedMap.put("Cdss", "Cdss");

		assertEquals(new Gson().toJson(expectedMap), response);
		verify(commonNurseServiceImpl, times(1)).getCSVisitDetails(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenChiefComplaints(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenCdss(benRegID, visitCode);
	}

	@Test
	public void testGetBenHistoryDetails_Success() {
		// Arrange
		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("FamilyHistory", "FamilyHistoryData");
		expectedMap.put("PhysicalActivityHistory", "PhysicalActivityData");
		expectedMap.put("PersonalHistory", "PersonalHistoryData");

		when(commonNurseServiceImpl.getFamilyHistoryDetail(benRegID, visitCode)).thenReturn("FamilyHistoryData");
		when(commonNurseServiceImpl.getPhysicalActivityType(benRegID, visitCode)).thenReturn("PhysicalActivityData");
		when(commonNurseServiceImpl.getPersonalHistory(benRegID, visitCode)).thenReturn("PersonalHistoryData");

		// Act
		String result = ncdScreeningServiceImpl.getBenHistoryDetails(benRegID, visitCode);

		// Assert
		assertEquals(new Gson().toJson(expectedMap), result);
		verify(commonNurseServiceImpl, times(1)).getFamilyHistoryDetail(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getPhysicalActivityType(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getPersonalHistory(benRegID, visitCode);
	}

	@Test
	public void testGetBenHistoryDetails_NoData() {
		// Arrange
		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("FamilyHistory", null);
		expectedMap.put("PhysicalActivityHistory", null);
		expectedMap.put("PersonalHistory", null);

		when(commonNurseServiceImpl.getFamilyHistoryDetail(benRegID, visitCode)).thenReturn(null);
		when(commonNurseServiceImpl.getPhysicalActivityType(benRegID, visitCode)).thenReturn(null);
		when(commonNurseServiceImpl.getPersonalHistory(benRegID, visitCode)).thenReturn(null);

		// Act
		String result = ncdScreeningServiceImpl.getBenHistoryDetails(benRegID, visitCode);

		// Assert
		assertEquals(new Gson().toJson(expectedMap), result);
		verify(commonNurseServiceImpl, times(1)).getFamilyHistoryDetail(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getPhysicalActivityType(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getPersonalHistory(benRegID, visitCode);
	}

	@Test
	public void testGetBenHistoryDetails_Exception() {
		// Arrange
		when(commonNurseServiceImpl.getFamilyHistoryDetail(benRegID, visitCode))
				.thenThrow(new RuntimeException("Error"));

		// Act & Assert
		assertThrows(RuntimeException.class, () -> {
			ncdScreeningServiceImpl.getBenHistoryDetails(benRegID, visitCode);
		});
		verify(commonNurseServiceImpl, times(1)).getFamilyHistoryDetail(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(0)).getPhysicalActivityType(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(0)).getPersonalHistory(benRegID, visitCode);
	}

	@Test
	public void testGetBenIdrsDetailsFrmNurse() {
		when(commonNurseServiceImpl.getBeneficiaryIdrsDetails(beneficiaryRegID, benVisitID))
				.thenReturn("Mock IDRS Details");

		String response = ncdScreeningServiceImpl.getBenIdrsDetailsFrmNurse(beneficiaryRegID, benVisitID);

		assertNotNull(response);
		assertTrue(response.contains("IDRSDetail"));
		assertTrue(response.contains("Mock IDRS Details"));

		verify(commonNurseServiceImpl, times(1)).getBeneficiaryIdrsDetails(beneficiaryRegID, benVisitID);
	}

	@Test
	public void testGetBenIdrsDetailsFrmNurse_NoDetails() {
		when(commonNurseServiceImpl.getBeneficiaryIdrsDetails(beneficiaryRegID, benVisitID)).thenReturn(null);

		String response = ncdScreeningServiceImpl.getBenIdrsDetailsFrmNurse(beneficiaryRegID, benVisitID);

		assertNotNull(response);
		assertTrue(response.contains("IDRSDetail"));
		assertTrue(response.contains("null"));

		verify(commonNurseServiceImpl, times(1)).getBeneficiaryIdrsDetails(beneficiaryRegID, benVisitID);
	}

	@Test
	public void testGetBeneficiaryVitalDetails() {
		when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, benVisitID))
				.thenReturn(benAnthropometryDetail);
		when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, benVisitID))
				.thenReturn(benPhysicalVitalDetail);

		String response = ncdScreeningServiceImpl.getBeneficiaryVitalDetails(beneficiaryRegID, benVisitID);

		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("benAnthropometryDetail", benAnthropometryDetail);
		expectedMap.put("benPhysicalVitalDetail", benPhysicalVitalDetail);
		String expectedResponse = new Gson().toJson(expectedMap);

		assertEquals(expectedResponse, response);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID,
				benVisitID);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, benVisitID);
	}

	@Test
	public void testGetBeneficiaryCdssDetails_Success() throws IEMRException {
		Map<String, Object> expectedCdssDetails = new HashMap<>();
		expectedCdssDetails.put("presentChiefComplaint", "Chief Complaint Data");
		expectedCdssDetails.put("diseaseSummary", "Disease Summary Data");

		when(commonNurseServiceImpl.getBenCdssDetails(beneficiaryRegID, benVisitID)).thenReturn("Chief Complaint Data",
				"Disease Summary Data");

		String response = ncdScreeningServiceImpl.getBeneficiaryCdssDetails(beneficiaryRegID, benVisitID);

		assertTrue(response.contains("Chief Complaint Data"));
		assertTrue(response.contains("Disease Summary Data"));
		verify(commonNurseServiceImpl, times(2)).getBenCdssDetails(beneficiaryRegID, benVisitID);
	}

	@Test
	public void testGetBeneficiaryCdssDetails_Failure() throws IEMRException {
		when(commonNurseServiceImpl.getBenCdssDetails(beneficiaryRegID, benVisitID))
				.thenThrow(new IEMRException("Error fetching CDSS details"));

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			ncdScreeningServiceImpl.getBeneficiaryCdssDetails(beneficiaryRegID, benVisitID);
		});

		assertEquals("Error fetching CDSS details", exception.getMessage());
		verify(commonNurseServiceImpl, times(1)).getBenCdssDetails(beneficiaryRegID, benVisitID);
	}

	@Test
	public void testUpdateNCDScreeningHistory_Success() throws Exception {
		// Setup
		JsonObject familyHistory = JsonParser.parseString("{\"someKey\":\"someValue\"}").getAsJsonObject();
		JsonObject physicalActivityHistory = JsonParser.parseString("{\"someKey\":\"someValue\"}").getAsJsonObject();
		historyOBJ.add("familyHistory", familyHistory);
		historyOBJ.add("physicalActivityHistory", physicalActivityHistory);

		BenFamilyHistory benFamilyHistory = new BenFamilyHistory();
		PhysicalActivityType physicalActivityType = new PhysicalActivityType();

		when(commonNurseServiceImpl.updateBenFamilyHistoryNCDScreening(any(BenFamilyHistory.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateBenPhysicalActivityHistoryNCDScreening(any(PhysicalActivityType.class)))
				.thenReturn(1);

		// Execute
		int result = ncdScreeningServiceImpl.UpdateNCDScreeningHistory(historyOBJ);

		// Verify
		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).updateBenFamilyHistoryNCDScreening(any(BenFamilyHistory.class));
		verify(commonNurseServiceImpl, times(1))
				.updateBenPhysicalActivityHistoryNCDScreening(any(PhysicalActivityType.class));
	}

	@Test
	public void testUpdateNCDScreeningHistory_FamilyHistoryMissing() throws Exception {
		// Setup
		JsonObject physicalActivityHistory = JsonParser.parseString("{\"someKey\":\"someValue\"}").getAsJsonObject();
		historyOBJ.add("physicalActivityHistory", physicalActivityHistory);

		PhysicalActivityType physicalActivityType = new PhysicalActivityType();

		when(commonNurseServiceImpl.updateBenPhysicalActivityHistoryNCDScreening(any(PhysicalActivityType.class)))
				.thenReturn(1);

		// Execute
		int result = ncdScreeningServiceImpl.UpdateNCDScreeningHistory(historyOBJ);

		// Verify
		assertEquals(0, result);
		verify(commonNurseServiceImpl, times(0)).updateBenFamilyHistoryNCDScreening(any(BenFamilyHistory.class));
		verify(commonNurseServiceImpl, times(1))
				.updateBenPhysicalActivityHistoryNCDScreening(any(PhysicalActivityType.class));
	}

	@Test
	public void testUpdateNCDScreeningHistory_PhysicalActivityHistoryMissing() throws Exception {
		// Setup
		JsonObject familyHistory = JsonParser.parseString("{\"someKey\":\"someValue\"}").getAsJsonObject();
		historyOBJ.add("familyHistory", familyHistory);

		BenFamilyHistory benFamilyHistory = new BenFamilyHistory();

		when(commonNurseServiceImpl.updateBenFamilyHistoryNCDScreening(any(BenFamilyHistory.class))).thenReturn(1);

		// Execute
		int result = ncdScreeningServiceImpl.UpdateNCDScreeningHistory(historyOBJ);

		// Verify
		assertEquals(0, result);
		verify(commonNurseServiceImpl, times(1)).updateBenFamilyHistoryNCDScreening(any(BenFamilyHistory.class));
		verify(commonNurseServiceImpl, times(0))
				.updateBenPhysicalActivityHistoryNCDScreening(any(PhysicalActivityType.class));
	}

	@Test
	public void testUpdateNCDScreeningHistory_BothHistoriesMissing() throws Exception {
		// Execute
		int result = ncdScreeningServiceImpl.UpdateNCDScreeningHistory(historyOBJ);

		// Verify
		assertEquals(0, result);
		verify(commonNurseServiceImpl, times(0)).updateBenFamilyHistoryNCDScreening(any(BenFamilyHistory.class));
		verify(commonNurseServiceImpl, times(0))
				.updateBenPhysicalActivityHistoryNCDScreening(any(PhysicalActivityType.class));
	}

	@Test
	public void testGetBenNCDScreeningNurseData() throws IEMRException {
		when(commonNurseServiceImpl.getCSVisitDetails(benRegID, visitCode)).thenReturn(visitDetail);
		when(commonNurseServiceImpl.getBenChiefComplaints(benRegID, visitCode)).thenReturn("ChiefComplaints");
		when(commonNurseServiceImpl.getBenCdss(benRegID, visitCode)).thenReturn("Cdss");
		when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(benRegID, visitCode))
				.thenReturn("AnthropometryDetails");
		when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(benRegID, visitCode)).thenReturn("VitalDetails");
		when(commonNurseServiceImpl.getFamilyHistoryDetail(benRegID, visitCode)).thenReturn("FamilyHistory");
		when(commonNurseServiceImpl.getPhysicalActivityType(benRegID, visitCode)).thenReturn("PhysicalActivityHistory");
		when(commonNurseServiceImpl.getPersonalHistory(benRegID, visitCode)).thenReturn("PersonalHistory");
		when(commonNurseServiceImpl.getBeneficiaryIdrsDetails(benRegID, visitCode)).thenReturn("IDRSDetail");
		when(commonNurseServiceImpl.getBenCdssDetails(benRegID, visitCode)).thenReturn("CdssDetails");

		String result = ncdScreeningServiceImpl.getBenNCDScreeningNurseData(benRegID, visitCode);

		resMap.put("vitals",
				"{\"benAnthropometryDetail\":\"AnthropometryDetails\",\"benPhysicalVitalDetail\":\"VitalDetails\"}");
		resMap.put("history",
				"{\"FamilyHistory\":\"FamilyHistory\",\"PhysicalActivityHistory\":\"PhysicalActivityHistory\",\"PersonalHistory\":\"PersonalHistory\"}");
		resMap.put("idrs", "{\"IDRSDetail\":\"IDRSDetail\"}");
		resMap.put("cbac", "null");
		resMap.put("cdss", "{\"presentChiefComplaint\":\"CdssDetails\",\"diseaseSummary\":\"CdssDetails\"}");

		assertEquals(new Gson().toJson(resMap), result);

		verify(commonNurseServiceImpl, times(1)).getCSVisitDetails(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenChiefComplaints(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenCdss(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalVitalDetails(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getFamilyHistoryDetail(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getPhysicalActivityType(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getPersonalHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryIdrsDetails(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenCdssDetails(benRegID, visitCode);
	}

	@Test
	public void testSaveDiabetesDetailsSuccess() throws IEMRException {
		when(diabetesScreeningRepo.save(any(DiabetesScreening.class))).thenReturn(diabetesScreening);

		Long result = ncdScreeningServiceImpl.saveDiabetesDetails(diabetesScreening);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(diabetesScreeningRepo, times(1)).save(any(DiabetesScreening.class));
	}

	@Test
	public void testSaveDiabetesDetailsFailure() {
		when(diabetesScreeningRepo.save(any(DiabetesScreening.class))).thenReturn(null);

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			ncdScreeningServiceImpl.saveDiabetesDetails(diabetesScreening);
		});

		assertEquals("Error while saving diabetes screening data", exception.getMessage());
		verify(diabetesScreeningRepo, times(1)).save(any(DiabetesScreening.class));
	}

	@Test
	public void testSaveHypertensionDetails_Success() throws IEMRException {
		when(hypertensionScreeningRepo.save(any(HypertensionScreening.class))).thenReturn(hypertensionScreening);

		Long result = ncdScreeningServiceImpl.saveHypertensionDetails(hypertensionScreening);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(hypertensionScreeningRepo, times(1)).save(hypertensionScreening);
	}

	@Test
	public void testSaveHypertensionDetails_Failure() {
		when(hypertensionScreeningRepo.save(any(HypertensionScreening.class))).thenReturn(null);

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			ncdScreeningServiceImpl.saveHypertensionDetails(hypertensionScreening);
		});

		assertEquals("Error while saving hypertension screening data", exception.getMessage());
		verify(hypertensionScreeningRepo, times(1)).save(hypertensionScreening);
	}

	@Test
	public void testSaveBreastCancerDetails_Success() throws IEMRException {
		when(breastCancerScreeningRepo.save(any(BreastCancerScreening.class))).thenReturn(breastCancerScreening);

		Long result = ncdScreeningServiceImpl.saveBreastCancerDetails(breastCancerScreening);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(breastCancerScreeningRepo, times(1)).save(any(BreastCancerScreening.class));
	}

	@Test
	public void testSaveBreastCancerDetails_Failure() {
		when(breastCancerScreeningRepo.save(any(BreastCancerScreening.class))).thenReturn(null);

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			ncdScreeningServiceImpl.saveBreastCancerDetails(breastCancerScreening);
		});

		assertEquals("Error while saving breast cancer screening", exception.getMessage());
		verify(breastCancerScreeningRepo, times(1)).save(any(BreastCancerScreening.class));
	}

	@Test
	public void testSaveCervicalDetails_Success() throws IEMRException {
		when(cervicalCancerScreeningRepo.save(any(CervicalCancerScreening.class))).thenReturn(cervicalCancerScreening);

		Long result = ncdScreeningServiceImpl.saveCervicalDetails(cervicalCancerScreening);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(cervicalCancerScreeningRepo, times(1)).save(cervicalCancerScreening);
	}

	@Test
	public void testSaveCervicalDetails_Failure() {
		when(cervicalCancerScreeningRepo.save(any(CervicalCancerScreening.class))).thenReturn(null);

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			ncdScreeningServiceImpl.saveCervicalDetails(cervicalCancerScreening);
		});

		assertEquals("Error while saving cervical screening", exception.getMessage());
		verify(cervicalCancerScreeningRepo, times(1)).save(cervicalCancerScreening);
	}

	@Test
	public void testSaveOralCancerDetails_Success() throws IEMRException {
		when(oralCancerScreeningRepo.save(any(OralCancerScreening.class))).thenReturn(oralCancerScreening);

		Long result = ncdScreeningServiceImpl.saveOralCancerDetails(oralCancerScreening);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(oralCancerScreeningRepo, times(1)).save(any(OralCancerScreening.class));
	}

	@Test
	public void testSaveOralCancerDetails_Failure() {
		when(oralCancerScreeningRepo.save(any(OralCancerScreening.class))).thenReturn(null);

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			ncdScreeningServiceImpl.saveOralCancerDetails(oralCancerScreening);
		});

		assertEquals("Error while saving oral screening", exception.getMessage());
		verify(oralCancerScreeningRepo, times(1)).save(any(OralCancerScreening.class));
	}

	@Test
	public void testSaveCbacDetails_Success() throws IEMRException {
		when(cbacDetailsRepo.save(any(CbacDetails.class))).thenReturn(cbacDetails);

		Long result = ncdScreeningServiceImpl.saveCbacDetails(cbacDetails);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(cbacDetailsRepo, times(1)).save(cbacDetails);
	}

	@Test
	public void testSaveCbacDetails_Failure_Null() {
		when(cbacDetailsRepo.save(any(CbacDetails.class))).thenReturn(null);

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			ncdScreeningServiceImpl.saveCbacDetails(cbacDetails);
		});

		assertEquals("Error while saving Cbac details", exception.getMessage());
		verify(cbacDetailsRepo, times(1)).save(cbacDetails);
	}

	@Test
	public void testSaveCbacDetails_Failure_Exception() {
		when(cbacDetailsRepo.save(any(CbacDetails.class))).thenThrow(new RuntimeException("Database error"));

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			ncdScreeningServiceImpl.saveCbacDetails(cbacDetails);
		});

		assertEquals("Error while saving Cbac details", exception.getMessage());
		verify(cbacDetailsRepo, times(1)).save(cbacDetails);
	}

	@Test
	public void testFetchConfirmedScreeningDisease_Success() throws IEMRException {
		List<Object[]> resultSet = new ArrayList<>();
		resultSet.add(new Object[] { BigInteger.valueOf(1), true, false, true, false, true });

		when(hypertensionScreeningRepo.fetchConfirmedScreening(beneficiaryRegId)).thenReturn(resultSet);

		String response = ncdScreeningService.fetchConfirmedScreeningDisease(beneficiaryRegId);

		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("beneficiaryRegId", 1L);
		expectedMap.put("confirmedDiseases", Arrays.asList("Diabetes Mellitus", "Oral cancer", "Cervical cancer"));

		assertEquals(new Gson().toJson(expectedMap), response);
	}

	@Test
	public void testFetchConfirmedScreeningDisease_NoRecords() throws IEMRException {
		when(hypertensionScreeningRepo.fetchConfirmedScreening(beneficiaryRegId)).thenReturn(Collections.emptyList());

		String response = ncdScreeningService.fetchConfirmedScreeningDisease(beneficiaryRegId);

		assertEquals("No records found for the beneficiary", response);
	}

	@Test
	public void testFetchConfirmedScreeningDisease_Exception() {
		when(hypertensionScreeningRepo.fetchConfirmedScreening(beneficiaryRegId))
				.thenThrow(new RuntimeException("Database error"));

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			ncdScreeningService.fetchConfirmedScreeningDisease(beneficiaryRegId);
		});

		assertEquals("Error while fetching confirmed screening disease :Database error", exception.getMessage());
	}

	@Test
	public void testGetNCDScreeningData() throws IEMRException {
		when(diabetesScreeningRepo.findByBeneficiaryRegIdAndVisitcode(beneficiaryRegId, visitCode))
				.thenReturn(diabetesScreening);
		when(hypertensionScreeningRepo.findByBeneficiaryRegIdAndVisitcode(beneficiaryRegId, visitCode))
				.thenReturn(hypertensionScreening);
		when(oralCancerScreeningRepo.findByBeneficiaryRegIdAndVisitcode(beneficiaryRegId, visitCode))
				.thenReturn(oralCancerScreening);
		when(breastCancerScreeningRepo.findByBeneficiaryRegIdAndVisitcode(beneficiaryRegId, visitCode))
				.thenReturn(breastCancerScreening);
		when(cervicalCancerScreeningRepo.findByBeneficiaryRegIdAndVisitcode(beneficiaryRegId, visitCode))
				.thenReturn(cervicalCancerScreening);

		String result = ncdScreeningService.getNCDScreeningData(beneficiaryRegId, visitCode);

		Map<String, Object> expectedResponse = new HashMap<>();
		expectedResponse.put("diabetes", diabetesScreening);
		expectedResponse.put("hypertension", hypertensionScreening);
		expectedResponse.put("oral", oralCancerScreening);
		expectedResponse.put("breast", breastCancerScreening);
		expectedResponse.put("cervical", cervicalCancerScreening);

		Gson gson = new GsonBuilder().serializeNulls().create();
		String expectedJson = gson.toJson(expectedResponse);

		assertEquals(expectedJson, result);

		verify(diabetesScreeningRepo, times(1)).findByBeneficiaryRegIdAndVisitcode(beneficiaryRegId, visitCode);
		verify(hypertensionScreeningRepo, times(1)).findByBeneficiaryRegIdAndVisitcode(beneficiaryRegId, visitCode);
		verify(oralCancerScreeningRepo, times(1)).findByBeneficiaryRegIdAndVisitcode(beneficiaryRegId, visitCode);
		verify(breastCancerScreeningRepo, times(1)).findByBeneficiaryRegIdAndVisitcode(beneficiaryRegId, visitCode);
		verify(cervicalCancerScreeningRepo, times(1)).findByBeneficiaryRegIdAndVisitcode(beneficiaryRegId, visitCode);
	}

	@Test
	public void testGetNCDScreeningData_NoDataFound() throws IEMRException {
		when(diabetesScreeningRepo.findByBeneficiaryRegIdAndVisitcode(beneficiaryRegId, visitCode)).thenReturn(null);
		when(hypertensionScreeningRepo.findByBeneficiaryRegIdAndVisitcode(beneficiaryRegId, visitCode))
				.thenReturn(null);
		when(oralCancerScreeningRepo.findByBeneficiaryRegIdAndVisitcode(beneficiaryRegId, visitCode)).thenReturn(null);
		when(breastCancerScreeningRepo.findByBeneficiaryRegIdAndVisitcode(beneficiaryRegId, visitCode))
				.thenReturn(null);
		when(cervicalCancerScreeningRepo.findByBeneficiaryRegIdAndVisitcode(beneficiaryRegId, visitCode))
				.thenReturn(null);

		String result = ncdScreeningService.getNCDScreeningData(beneficiaryRegId, visitCode);

		Map<String, Object> expectedResponse = new HashMap<>();

		Gson gson = new GsonBuilder().serializeNulls().create();
		String expectedJson = gson.toJson(expectedResponse);

		assertEquals(expectedJson, result);

		verify(diabetesScreeningRepo, times(1)).findByBeneficiaryRegIdAndVisitcode(beneficiaryRegId, visitCode);
		verify(hypertensionScreeningRepo, times(1)).findByBeneficiaryRegIdAndVisitcode(beneficiaryRegId, visitCode);
		verify(oralCancerScreeningRepo, times(1)).findByBeneficiaryRegIdAndVisitcode(beneficiaryRegId, visitCode);
		verify(breastCancerScreeningRepo, times(1)).findByBeneficiaryRegIdAndVisitcode(beneficiaryRegId, visitCode);
		verify(cervicalCancerScreeningRepo, times(1)).findByBeneficiaryRegIdAndVisitcode(beneficiaryRegId, visitCode);
	}

	@Test
	public void testUpdateNCDScreeningData_withValidDiabetesData() throws IEMRException {
		JsonObject diabetesObj = new JsonObject();
		diabetesObj.addProperty("confirmed", true);
		requestOBJ.add("diabetes", diabetesObj);

		when(diabetesScreeningRepo.save(any(DiabetesScreening.class))).thenReturn(new DiabetesScreening());

		String response = ncdScreeningServiceImpl.updateNCDScreeningData(requestOBJ);

		assertEquals("Screening data updated successfully", response);
		verify(diabetesScreeningRepo, times(1)).save(any(DiabetesScreening.class));
	}

	@Test
	public void testUpdateNCDScreeningData_withValidHypertensionData() throws IEMRException {
		JsonObject hypertensionObj = new JsonObject();
		hypertensionObj.addProperty("confirmed", true);
		requestOBJ.add("hypertension", hypertensionObj);

		when(hypertensionScreeningRepo.save(any(HypertensionScreening.class))).thenReturn(new HypertensionScreening());

		String response = ncdScreeningServiceImpl.updateNCDScreeningData(requestOBJ);

		assertEquals("Screening data updated successfully", response);
		verify(hypertensionScreeningRepo, times(1)).save(any(HypertensionScreening.class));
	}

	@Test
	public void testUpdateNCDScreeningData_withValidBreastCancerData() throws IEMRException {
		JsonObject breastObj = new JsonObject();
		breastObj.addProperty("confirmed", true);
		requestOBJ.add("breast", breastObj);

		when(breastCancerScreeningRepo.save(any(BreastCancerScreening.class))).thenReturn(new BreastCancerScreening());

		String response = ncdScreeningServiceImpl.updateNCDScreeningData(requestOBJ);

		assertEquals("Screening data updated successfully", response);
		verify(breastCancerScreeningRepo, times(1)).save(any(BreastCancerScreening.class));
	}

	@Test
	public void testUpdateNCDScreeningData_withValidCervicalCancerData() throws IEMRException {
		JsonObject cervicalObj = new JsonObject();
		cervicalObj.addProperty("confirmed", true);
		requestOBJ.add("cervical", cervicalObj);

		when(cervicalCancerScreeningRepo.save(any(CervicalCancerScreening.class)))
				.thenReturn(new CervicalCancerScreening());

		String response = ncdScreeningServiceImpl.updateNCDScreeningData(requestOBJ);

		assertEquals("Screening data updated successfully", response);
		verify(cervicalCancerScreeningRepo, times(1)).save(any(CervicalCancerScreening.class));
	}

	@Test
	public void testUpdateNCDScreeningData_withValidOralCancerData() throws IEMRException {
		JsonObject oralObj = new JsonObject();
		oralObj.addProperty("confirmed", true);
		requestOBJ.add("oral", oralObj);

		when(oralCancerScreeningRepo.save(any(OralCancerScreening.class))).thenReturn(new OralCancerScreening());

		String response = ncdScreeningServiceImpl.updateNCDScreeningData(requestOBJ);

		assertEquals("Screening data updated successfully", response);
		verify(oralCancerScreeningRepo, times(1)).save(any(OralCancerScreening.class));
	}

	@Test
	public void testUpdateNCDScreeningData_withInvalidData() {
		Exception exception = assertThrows(IEMRException.class, () -> {
			ncdScreeningServiceImpl.updateNCDScreeningData(null);
		});

		assertEquals("invalid screening data to update", exception.getMessage());
	}

	@Test
	public void testGetCbacData_Success() throws IEMRException {
		when(cbacDetailsRepo.findByBeneficiaryRegIdAndVisitCode(beneficiaryRegId, visitCode)).thenReturn(cbacDetails);

		String result = ncdScreeningServiceImpl.getCbacData(beneficiaryRegId, visitCode);

		assertNotNull(result);
		assertTrue(result.contains("\"id\":1"));
		verify(cbacDetailsRepo, times(1)).findByBeneficiaryRegIdAndVisitCode(beneficiaryRegId, visitCode);
	}

	@Test
	public void testGetCbacData_NoDataFound() throws IEMRException {
		when(cbacDetailsRepo.findByBeneficiaryRegIdAndVisitCode(beneficiaryRegId, visitCode)).thenReturn(null);

		String result = ncdScreeningServiceImpl.getCbacData(beneficiaryRegId, visitCode);

		assertNull(result);
		verify(cbacDetailsRepo, times(1)).findByBeneficiaryRegIdAndVisitCode(beneficiaryRegId, visitCode);
	}

	@Test
	public void testGetCbacData_Exception() {
		when(cbacDetailsRepo.findByBeneficiaryRegIdAndVisitCode(beneficiaryRegId, visitCode))
				.thenThrow(new RuntimeException("Database error"));

		Exception exception = assertThrows(IEMRException.class, () -> {
			ncdScreeningServiceImpl.getCbacData(beneficiaryRegId, visitCode);
		});

		assertEquals("Database error", exception.getMessage());
		verify(cbacDetailsRepo, times(1)).findByBeneficiaryRegIdAndVisitCode(beneficiaryRegId, visitCode);
	}
}
