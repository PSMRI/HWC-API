package com.iemr.hwc.service.cancerScreening;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.data.nurse.BenCancerVitalDetail;
import com.iemr.hwc.data.nurse.BenFamilyCancerHistory;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.nurse.BenFamilyCancerHistoryRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonServiceImpl;
import com.iemr.hwc.service.tele_consultation.SMSGatewayServiceImpl;
import com.iemr.hwc.service.tele_consultation.TeleConsultationServiceImpl;
import com.iemr.hwc.utils.mapper.InputMapper;

import net.minidev.json.JSONObject;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestCSServiceImpl {

	public class CSServiceImplTest {

		@Mock
		private CommonServiceImpl commonServiceImpl;

		@Mock
		private SMSGatewayServiceImpl sMSGatewayServiceImpl;
		@Mock
		private CommonNurseServiceImpl commonNurseServiceImpl;
		@Mock
		private CSNurseServiceImpl cSNurseServiceImpl;
		@Mock
		private BenFamilyCancerHistoryRepo benFamilyCancerHistoryRepo;
		@Mock
		private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;
		@Mock
		private CommonDoctorServiceImpl commonDoctorServiceImpl;

		@Mock
		private TeleConsultationServiceImpl teleConsultationServiceImpl;

		@Mock
		private InputMapper inputMapper;

		private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;

		@Mock
		private CSCarestreamServiceImpl cSCarestreamServiceImpl;

		@Mock
		private CSDoctorServiceImpl cSDoctorServiceImpl;
		@Mock
		private CSOncologistServiceImpl csOncologistServiceImpl;

		@InjectMocks
		private CSServiceImpl csServiceImpl;
		private BeneficiaryVisitDetail benVisitDetailsOBJ;
		private CommonUtilityClass nurseUtilityClass;
		private JsonObject requestOBJ;
		private Long benVisitID;
		private Long benVisitCode;
		private JsonObject validJsonObject;
		private JsonObject validSignsDetails;
		private JsonObject validOralDetails;
		private JsonObject validBreastDetails;
		private JsonObject validAbdominalDetails;
		private JsonObject validGynecologicalDetails;
		private JsonObject validImageCoordinates;
		private Long benRegID;
		private Long visitCode;
		private CSServiceImpl cSServiceImpl;

		private JsonObject validRequestObj;
		private JsonObject invalidRequestObj;
		private String authorization;
		private Long benFlowID;
		private JSONObject requestObj;
		private Long beneficiaryRegID;
		private JsonObject validRequest;
		private JsonObject invalidRequest;
		private JsonObject requestWithoutDiagnosis;

		@BeforeEach
		public void setUp() {
			MockitoAnnotations.openMocks(this);
		}

		@Test
		public void testSaveCancerScreeningNurseData_ValidInput() throws Exception {
			// Arrange
			String jsonString = "{ \"visitDetails\": { \"someKey\": \"someValue\" }, \"examinationDetails\": { \"breastDetails\": { \"referredToMammogram\": true } }, \"sendToDoctorWorklist\": true }";
			JsonObject requestOBJ = JsonParser.parseString(jsonString).getAsJsonObject();
			String authorization = "validAuthorizationToken";

			// Mock dependencies
			when(commonServiceImpl.createTcRequest(any(), any(), anyString()))
					.thenReturn(new TeleconsultationRequestOBJ());
			when(csServiceImpl.saveBenVisitDetails(any(), any())).thenReturn(Map.of("visitID", 1L, "visitCode", 1L));
			when(csServiceImpl.saveBenHistoryDetails(any(), anyLong(), anyLong())).thenReturn(1L);
			when(csServiceImpl.saveBenExaminationDetails(any(), anyLong(), anyString(), anyLong(), anyLong()))
					.thenReturn(1L);
			when(csServiceImpl.saveBenVitalsDetails(any(), anyLong(), anyLong())).thenReturn(1L);
			when(csServiceImpl.updateBenStatusFlagAfterNurseSaveSuccess(any(), anyLong(), anyLong(), anyBoolean(),
					anyBoolean(), anyLong(), anyLong(), any())).thenReturn(1);

			// Act
			String result = csServiceImpl.saveCancerScreeningNurseData(requestOBJ, authorization);

			// Assert
			assertNotNull(result);
			assertTrue(result.contains("Data saved successfully"));
		}

		@Test
		public void testSaveCancerScreeningNurseData_NullInput() {
			// Arrange
			JsonObject requestOBJ = null;
			String authorization = "validAuthorizationToken";

			// Act & Assert
			Exception exception = assertThrows(Exception.class, () -> {
				csServiceImpl.saveCancerScreeningNurseData(requestOBJ, authorization);
			});

			assertEquals("Invalid input", exception.getMessage());
		}

		@Test
		public void testSaveCancerScreeningNurseData_InvalidInput() {
			// Arrange
			String jsonString = "{ \"invalidKey\": \"invalidValue\" }";
			JsonObject requestOBJ = JsonParser.parseString(jsonString).getAsJsonObject();
			String authorization = "validAuthorizationToken";

			// Act & Assert
			Exception exception = assertThrows(Exception.class, () -> {
				csServiceImpl.saveCancerScreeningNurseData(requestOBJ, authorization);
			});

			assertEquals("Invalid input", exception.getMessage());
		}

		@Test
		public void testSaveCancerScreeningNurseData_DataAlreadySaved() throws Exception {
			// Arrange
			String jsonString = "{ \"visitDetails\": { \"someKey\": \"someValue\" } }";
			JsonObject requestOBJ = JsonParser.parseString(jsonString).getAsJsonObject();
			String authorization = "validAuthorizationToken";

			// Mock dependencies
			when(csServiceImpl.saveBenVisitDetails(any(), any())).thenReturn(Map.of());

			// Act
			String result = csServiceImpl.saveCancerScreeningNurseData(requestOBJ, authorization);

			// Assert
			assertNotNull(result);
			assertTrue(result.contains("Data already saved"));
		}
	}

	@Test
	public void testUpdateBenStatusFlagAfterNurseSaveSuccess_ReferedToMammogram() {
		isReferedToMammogram = true;

		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(), anyLong(),
				anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(),
				anyInt(), anyShort(), any(Timestamp.class), anyInt())).thenReturn(1);

		int result = csServiceImpl.updateBenStatusFlagAfterNurseSaveSuccess(benVisitDetailsOBJ, benVisitID, benFlowID,
				isReferedToMammogram, docVisitReq, benVisitCode, vanID, tcRequestOBJ);

		assertEquals(1, result);
		verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(),
				anyLong(), anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(),
				anyLong(), anyInt(), anyShort(), any(Timestamp.class), anyInt());
	}

	@Test
	public void testUpdateBenStatusFlagAfterNurseSaveSuccess_DocVisitReq() {
		docVisitReq = true;

		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(), anyLong(),
				anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(),
				anyInt(), anyShort(), any(Timestamp.class), anyInt())).thenReturn(1);

		int result = csServiceImpl.updateBenStatusFlagAfterNurseSaveSuccess(benVisitDetailsOBJ, benVisitID, benFlowID,
				isReferedToMammogram, docVisitReq, benVisitCode, vanID, tcRequestOBJ);

		assertEquals(1, result);
		verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(),
				anyLong(), anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(),
				anyLong(), anyInt(), anyShort(), any(Timestamp.class), anyInt());
	}

	@Test
	public void testUpdateBenStatusFlagAfterNurseSaveSuccess_NoReferAndNoDocVisit() {
		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(), anyLong(),
				anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(),
				anyInt(), anyShort(), any(Timestamp.class), anyInt())).thenReturn(1);

		int result = csServiceImpl.updateBenStatusFlagAfterNurseSaveSuccess(benVisitDetailsOBJ, benVisitID, benFlowID,
				isReferedToMammogram, docVisitReq, benVisitCode, vanID, tcRequestOBJ);

		assertEquals(1, result);
		verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(),
				anyLong(), anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(),
				anyLong(), anyInt(), anyShort(), any(Timestamp.class), anyInt());
	}

	@Test
	public void testUpdateBenStatusFlagAfterNurseSaveSuccess_WithTcRequest() {
		tcRequestOBJ.setUserID(1);
		tcRequestOBJ.setAllocationDate(new Timestamp(System.currentTimeMillis()));

		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(), anyLong(),
				anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(),
				anyInt(), anyShort(), any(Timestamp.class), anyInt())).thenReturn(1);

		int result = csServiceImpl.updateBenStatusFlagAfterNurseSaveSuccess(benVisitDetailsOBJ, benVisitID, benFlowID,
				isReferedToMammogram, docVisitReq, benVisitCode, vanID, tcRequestOBJ);

		assertEquals(1, result);
		verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(),
				anyLong(), anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(),
				anyLong(), anyInt(), anyShort(), any(Timestamp.class), anyInt());
	}

	@Test
	public void testSaveBenVisitDetails_Success() throws Exception {
		when(commonNurseServiceImpl.getMaxCurrentdate(anyLong(), anyString(), anyString())).thenReturn(0);
		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class))).thenReturn(1L);
		when(commonNurseServiceImpl.generateVisitCode(anyLong(), anyInt(), anyInt())).thenReturn(100L);

		Map<String, Long> result = csServiceImpl.saveBenVisitDetails(benVisitDetailsOBJ, nurseUtilityClass);

		assertNotNull(result);
		assertEquals(1L, result.get("visitID"));
		assertEquals(100L, result.get("visitCode"));

		verify(commonNurseServiceImpl, times(1)).getMaxCurrentdate(anyLong(), anyString(), anyString());
		verify(commonNurseServiceImpl, times(1)).saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class));
		verify(commonNurseServiceImpl, times(1)).generateVisitCode(anyLong(), anyInt(), anyInt());
	}

	@Test
	public void testSaveBenVisitDetails_AlreadySaved() throws Exception {
		when(commonNurseServiceImpl.getMaxCurrentdate(anyLong(), anyString(), anyString())).thenReturn(1);

		Map<String, Long> result = csServiceImpl.saveBenVisitDetails(benVisitDetailsOBJ, nurseUtilityClass);

		assertTrue(result.isEmpty());

		verify(commonNurseServiceImpl, times(1)).getMaxCurrentdate(anyLong(), anyString(), anyString());
		verify(commonNurseServiceImpl, never()).saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class));
		verify(commonNurseServiceImpl, never()).generateVisitCode(anyLong(), anyInt(), anyInt());
	}

	@Test
	public void testSaveBenHistoryDetails_ValidInput() throws Exception {
		String jsonString = "{ \"historyDetails\": { \"familyHistory\": { \"diseases\": [{ \"disease\": \"Cancer\" }] }, \"personalHistory\": { \"history\": \"Personal History\" }, \"pastObstetricHistory\": { \"history\": \"Obstetric History\" } } }";
		requestOBJ = JsonParser.parseString(jsonString).getAsJsonObject();

		when(cSNurseServiceImpl.saveBenFamilyCancerHistory(anyList())).thenReturn(1);
		when(cSNurseServiceImpl.saveBenPersonalCancerHistory(any(BenPersonalCancerHistory.class))).thenReturn(1L);
		when(cSNurseServiceImpl.saveBenPersonalCancerDietHistory(any(BenPersonalCancerDietHistory.class)))
				.thenReturn(1L);
		when(cSNurseServiceImpl.saveBenObstetricCancerHistory(any(BenObstetricCancerHistory.class))).thenReturn(1L);

		Long result = csServiceImpl.saveBenHistoryDetails(requestOBJ, benVisitID, benVisitCode);

		assertNotNull(result);
		assertEquals(1L, result);

		verify(cSNurseServiceImpl, times(1)).saveBenFamilyCancerHistory(anyList());
		verify(cSNurseServiceImpl, times(1)).saveBenPersonalCancerHistory(any(BenPersonalCancerHistory.class));
		verify(cSNurseServiceImpl, times(1)).saveBenPersonalCancerDietHistory(any(BenPersonalCancerDietHistory.class));
		verify(cSNurseServiceImpl, times(1)).saveBenObstetricCancerHistory(any(BenObstetricCancerHistory.class));
	}

	@Test
	public void testSaveBenHistoryDetails_MissingHistoryDetails() throws Exception {
		String jsonString = "{ }";
		requestOBJ = JsonParser.parseString(jsonString).getAsJsonObject();

		Long result = csServiceImpl.saveBenHistoryDetails(requestOBJ, benVisitID, benVisitCode);

		assertNotNull(result);
		assertEquals(1L, result);

		verify(cSNurseServiceImpl, never()).saveBenFamilyCancerHistory(anyList());
		verify(cSNurseServiceImpl, never()).saveBenPersonalCancerHistory(any(BenPersonalCancerHistory.class));
		verify(cSNurseServiceImpl, never()).saveBenPersonalCancerDietHistory(any(BenPersonalCancerDietHistory.class));
		verify(cSNurseServiceImpl, never()).saveBenObstetricCancerHistory(any(BenObstetricCancerHistory.class));
	}

	@Test
	public void testSaveBenHistoryDetails_EmptyHistoryDetails() throws Exception {
		String jsonString = "{ \"historyDetails\": { } }";
		requestOBJ = JsonParser.parseString(jsonString).getAsJsonObject();

		Long result = csServiceImpl.saveBenHistoryDetails(requestOBJ, benVisitID, benVisitCode);

		assertNotNull(result);
		assertEquals(1L, result);

		verify(cSNurseServiceImpl, never()).saveBenFamilyCancerHistory(anyList());
		verify(cSNurseServiceImpl, never()).saveBenPersonalCancerHistory(any(BenPersonalCancerHistory.class));
		verify(cSNurseServiceImpl, never()).saveBenPersonalCancerDietHistory(any(BenPersonalCancerDietHistory.class));
		verify(cSNurseServiceImpl, never()).saveBenObstetricCancerHistory(any(BenObstetricCancerHistory.class));
	}

	@Test
	public void testSaveBenFamilyHistoryDetails_Success() throws Exception {
		BenFamilyCancerHistory benFamilyCancerHistory = new BenFamilyCancerHistory();
		benFamilyCancerHistory.setBenVisitID(1L);
		benFamilyCancerHistory.setVisitCode(1L);
		List<BenFamilyCancerHistory> benFamilyCancerHistoryList = Arrays.asList(benFamilyCancerHistory);

		when(cSNurseServiceImpl.saveBenFamilyCancerHistory(benFamilyCancerHistoryList)).thenReturn(1);

		Long result = csServiceImpl.saveBenFamilyHistoryDetails();

		assertNotNull(result);
		assertEquals(1, result);
		verify(cSNurseServiceImpl, times(1)).saveBenFamilyCancerHistory(benFamilyCancerHistoryList);
	}

	@Test
	public void testSaveBenFamilyHistoryDetails_Failure() throws Exception {
		BenFamilyCancerHistory benFamilyCancerHistory = new BenFamilyCancerHistory();
		benFamilyCancerHistory.setBenVisitID(1L);
		benFamilyCancerHistory.setVisitCode(1L);
		List<BenFamilyCancerHistory> benFamilyCancerHistoryList = Arrays.asList(benFamilyCancerHistory);

		when(cSNurseServiceImpl.saveBenFamilyCancerHistory(benFamilyCancerHistoryList)).thenReturn(0);

		Long result = csServiceImpl.saveBenFamilyHistoryDetails();

		assertNull(result);
		verify(cSNurseServiceImpl, times(1)).saveBenFamilyCancerHistory(benFamilyCancerHistoryList);
	}

	@Test
	public void testSaveBenFamilyHistoryDetails_NullInput() throws Exception {
		requestOBJ = null;

		Long result = csServiceImpl.saveBenFamilyHistoryDetails();

		assertNull(result);
		verify(cSNurseServiceImpl, times(0)).saveBenFamilyCancerHistory(anyList());
	}

	@Test
	public void testSaveBenVitalsDetails_NullRequestObj() throws Exception {
		Long result = csServiceImpl.saveBenVitalsDetails(null, 1L, 1L);
		assertEquals(1L, result);
	}

	@Test
	public void testSaveBenVitalsDetails_NoVitalsDetails() throws Exception {
		JsonObject requestObj = new JsonObject();
		Long result = csServiceImpl.saveBenVitalsDetails(requestObj, 1L, 1L);
		assertEquals(1L, result);
	}

	@Test
	public void testSaveBenVitalsDetails_NullVitalsDetails() throws Exception {
		JsonObject requestObj = new JsonObject();
		requestObj.add("vitalsDetails", null);
		Long result = csServiceImpl.saveBenVitalsDetails(requestObj, 1L, 1L);
		assertEquals(1L, result);
	}

	@Test
	public void testSaveBenVitalsDetails_ValidVitalsDetails_Success() throws Exception {
		JsonObject requestObj = new JsonObject();
		JsonObject vitalsDetails = new JsonObject();
		requestObj.add("vitalsDetails", vitalsDetails);

		BenCancerVitalDetail benCancerVitalDetail = new BenCancerVitalDetail();
		when(InputMapper.gson().fromJson(vitalsDetails, BenCancerVitalDetail.class)).thenReturn(benCancerVitalDetail);
		when(cSNurseServiceImpl.saveBenVitalDetail(benCancerVitalDetail)).thenReturn(1L);

		Long result = csServiceImpl.saveBenVitalsDetails(requestObj, 1L, 1L);
		assertEquals(1L, result);
		verify(cSNurseServiceImpl, times(1)).saveBenVitalDetail(benCancerVitalDetail);
	}

	@Test
	public void testSaveBenVitalsDetails_ValidVitalsDetails_Failure() throws Exception {
		JsonObject requestObj = new JsonObject();
		JsonObject vitalsDetails = new JsonObject();
		requestObj.add("vitalsDetails", vitalsDetails);

		BenCancerVitalDetail benCancerVitalDetail = new BenCancerVitalDetail();
		when(InputMapper.gson().fromJson(vitalsDetails, BenCancerVitalDetail.class)).thenReturn(benCancerVitalDetail);
		when(cSNurseServiceImpl.saveBenVitalDetail(benCancerVitalDetail)).thenReturn(-1L);

		Long result = csServiceImpl.saveBenVitalsDetails(requestObj, 1L, 1L);
		assertEquals(-1L, result);
		verify(cSNurseServiceImpl, times(1)).saveBenVitalDetail(benCancerVitalDetail);
	}

	@Test
	public void testUpdateCSHistoryNurseData_AllDetailsProvided() throws Exception {
		when(cSNurseServiceImpl.updateBeneficiaryFamilyCancerHistory(anyList())).thenReturn(1);
		when(cSNurseServiceImpl.updateBenObstetricCancerHistory(any(BenObstetricCancerHistory.class))).thenReturn(1);
		when(cSNurseServiceImpl.updateBenPersonalCancerHistory(any(BenPersonalCancerHistory.class))).thenReturn(1);
		when(cSNurseServiceImpl.updateBenPersonalCancerDietHistory(any(BenPersonalCancerDietHistory.class)))
				.thenReturn(1);

		int result = csServiceImpl.UpdateCSHistoryNurseData(validJsonObject);

		assertEquals(1, result);
		verify(cSNurseServiceImpl, times(1)).updateBeneficiaryFamilyCancerHistory(anyList());
		verify(cSNurseServiceImpl, times(1)).updateBenObstetricCancerHistory(any(BenObstetricCancerHistory.class));
		verify(cSNurseServiceImpl, times(1)).updateBenPersonalCancerHistory(any(BenPersonalCancerHistory.class));
		verify(cSNurseServiceImpl, times(1))
				.updateBenPersonalCancerDietHistory(any(BenPersonalCancerDietHistory.class));
	}

	@Test
	public void testUpdateCSHistoryNurseData_FamilyHistoryMissing() throws Exception {
		validJsonObject.remove("familyHistory");

		when(cSNurseServiceImpl.updateBenObstetricCancerHistory(any(BenObstetricCancerHistory.class))).thenReturn(1);
		when(cSNurseServiceImpl.updateBenPersonalCancerHistory(any(BenPersonalCancerHistory.class))).thenReturn(1);
		when(cSNurseServiceImpl.updateBenPersonalCancerDietHistory(any(BenPersonalCancerDietHistory.class)))
				.thenReturn(1);

		int result = csServiceImpl.UpdateCSHistoryNurseData(validJsonObject);

		assertEquals(1, result);
		verify(cSNurseServiceImpl, never()).updateBeneficiaryFamilyCancerHistory(anyList());
		verify(cSNurseServiceImpl, times(1)).updateBenObstetricCancerHistory(any(BenObstetricCancerHistory.class));
		verify(cSNurseServiceImpl, times(1)).updateBenPersonalCancerHistory(any(BenPersonalCancerHistory.class));
		verify(cSNurseServiceImpl, times(1))
				.updateBenPersonalCancerDietHistory(any(BenPersonalCancerDietHistory.class));
	}

	@Test
	public void testUpdateCSHistoryNurseData_PastObstetricHistoryMissing() throws Exception {
		validJsonObject.remove("pastObstetricHistory");

		when(cSNurseServiceImpl.updateBeneficiaryFamilyCancerHistory(anyList())).thenReturn(1);
		when(cSNurseServiceImpl.updateBenPersonalCancerHistory(any(BenPersonalCancerHistory.class))).thenReturn(1);
		when(cSNurseServiceImpl.updateBenPersonalCancerDietHistory(any(BenPersonalCancerDietHistory.class)))
				.thenReturn(1);

		int result = csServiceImpl.UpdateCSHistoryNurseData(validJsonObject);

		assertEquals(1, result);
		verify(cSNurseServiceImpl, times(1)).updateBeneficiaryFamilyCancerHistory(anyList());
		verify(cSNurseServiceImpl, never()).updateBenObstetricCancerHistory(any(BenObstetricCancerHistory.class));
		verify(cSNurseServiceImpl, times(1)).updateBenPersonalCancerHistory(any(BenPersonalCancerHistory.class));
		verify(cSNurseServiceImpl, times(1))
				.updateBenPersonalCancerDietHistory(any(BenPersonalCancerDietHistory.class));
	}

	@Test
	public void testUpdateCSHistoryNurseData_PersonalHistoryMissing() throws Exception {
		validJsonObject.remove("personalHistory");

		when(cSNurseServiceImpl.updateBeneficiaryFamilyCancerHistory(anyList())).thenReturn(1);
		when(cSNurseServiceImpl.updateBenObstetricCancerHistory(any(BenObstetricCancerHistory.class))).thenReturn(1);

		int result = csServiceImpl.UpdateCSHistoryNurseData(validJsonObject);

		assertEquals(1, result);
		verify(cSNurseServiceImpl, times(1)).updateBeneficiaryFamilyCancerHistory(anyList());
		verify(cSNurseServiceImpl, times(1)).updateBenObstetricCancerHistory(any(BenObstetricCancerHistory.class));
		verify(cSNurseServiceImpl, never()).updateBenPersonalCancerHistory(any(BenPersonalCancerHistory.class));
		verify(cSNurseServiceImpl, never()).updateBenPersonalCancerDietHistory(any(BenPersonalCancerDietHistory.class));
	}

	@Test
	public void testUpdateCSHistoryNurseData_AllDetailsMissing() throws Exception {
		JsonObject emptyJsonObject = new JsonObject();

		int result = csServiceImpl.UpdateCSHistoryNurseData(emptyJsonObject);

		assertEquals(1, result);
		verify(cSNurseServiceImpl, never()).updateBeneficiaryFamilyCancerHistory(anyList());
		verify(cSNurseServiceImpl, never()).updateBenObstetricCancerHistory(any(BenObstetricCancerHistory.class));
		verify(cSNurseServiceImpl, never()).updateBenPersonalCancerHistory(any(BenPersonalCancerHistory.class));
		verify(cSNurseServiceImpl, never()).updateBenPersonalCancerDietHistory(any(BenPersonalCancerDietHistory.class));
	}

	@Test
	public void testUpdateBenExaminationDetail_withValidSignsDetails() throws Exception {
		when(cSNurseServiceImpl.updateSignAndSymptomsExaminationDetails(any())).thenReturn(1);
		when(cSNurseServiceImpl.updateLymphNodeExaminationDetails(any())).thenReturn(1);

		int result = csServiceImpl.updateBenExaminationDetail(validSignsDetails);

		assertEquals(1, result);
		verify(cSNurseServiceImpl, times(1)).updateSignAndSymptomsExaminationDetails(any());
		verify(cSNurseServiceImpl, times(1)).updateLymphNodeExaminationDetails(any());
	}

	@Test
	public void testUpdateBenExaminationDetail_withValidOralDetails() throws Exception {
		when(cSNurseServiceImpl.updateCancerOralDetails(any())).thenReturn(1);

		int result = csServiceImpl.updateBenExaminationDetail(validOralDetails);

		assertEquals(1, result);
		verify(cSNurseServiceImpl, times(1)).updateCancerOralDetails(any());
	}

	@Test
	public void testUpdateBenExaminationDetail_withValidBreastDetails() throws Exception {
		when(cSNurseServiceImpl.updateCancerBreastDetails(any())).thenReturn(1);

		int result = csServiceImpl.updateBenExaminationDetail(validBreastDetails);

		assertEquals(1, result);
		verify(cSNurseServiceImpl, times(1)).updateCancerBreastDetails(any());
	}

	@Test
	public void testUpdateBenExaminationDetail_withValidAbdominalDetails() throws Exception {
		when(cSNurseServiceImpl.updateCancerAbdominalExaminationDetails(any())).thenReturn(1);

		int result = csServiceImpl.updateBenExaminationDetail(validAbdominalDetails);

		assertEquals(1, result);
		verify(cSNurseServiceImpl, times(1)).updateCancerAbdominalExaminationDetails(any());
	}

	@Test
	public void testUpdateBenExaminationDetail_withValidGynecologicalDetails() throws Exception {
		when(cSNurseServiceImpl.updateCancerGynecologicalExaminationDetails(any())).thenReturn(1);

		int result = csServiceImpl.updateBenExaminationDetail(validGynecologicalDetails);

		assertEquals(1, result);
		verify(cSNurseServiceImpl, times(1)).updateCancerGynecologicalExaminationDetails(any());
	}

	@Test
	public void testUpdateBenExaminationDetail_withValidImageCoordinates() throws Exception {
		when(cSNurseServiceImpl.updateCancerExamImgAnotasnDetails(any())).thenReturn(1);

		int result = csServiceImpl.updateBenExaminationDetail(validImageCoordinates);

		assertEquals(1, result);
		verify(cSNurseServiceImpl, times(1)).updateCancerExamImgAnotasnDetails(any());
	}

	@Test
	public void testUpdateBenExaminationDetail_withValidSignsDetails() throws Exception {
		when(cSNurseServiceImpl.updateSignAndSymptomsExaminationDetails(any())).thenReturn(1);
		when(cSNurseServiceImpl.updateLymphNodeExaminationDetails(any())).thenReturn(1);

		int result = csServiceImpl.updateBenExaminationDetail(validSignsDetails);

		assertEquals(1, result);
		verify(cSNurseServiceImpl, times(1)).updateSignAndSymptomsExaminationDetails(any());
		verify(cSNurseServiceImpl, times(1)).updateLymphNodeExaminationDetails(any());
	}

	@Test
	public void testUpdateBenExaminationDetail_withValidOralDetails() throws Exception {
		when(cSNurseServiceImpl.updateCancerOralDetails(any())).thenReturn(1);

		int result = csServiceImpl.updateBenExaminationDetail(validOralDetails);

		assertEquals(1, result);
		verify(cSNurseServiceImpl, times(1)).updateCancerOralDetails(any());
	}

	@Test
	public void testUpdateBenExaminationDetail_withValidBreastDetails() throws Exception {
		when(cSNurseServiceImpl.updateCancerBreastDetails(any())).thenReturn(1);

		int result = csServiceImpl.updateBenExaminationDetail(validBreastDetails);

		assertEquals(1, result);
		verify(cSNurseServiceImpl, times(1)).updateCancerBreastDetails(any());
	}

	@Test
	public void testUpdateBenExaminationDetail_withValidAbdominalDetails() throws Exception {
		when(cSNurseServiceImpl.updateCancerAbdominalExaminationDetails(any())).thenReturn(1);

		int result = csServiceImpl.updateBenExaminationDetail(validAbdominalDetails);

		assertEquals(1, result);
		verify(cSNurseServiceImpl, times(1)).updateCancerAbdominalExaminationDetails(any());
	}

	@Test
	public void testUpdateBenExaminationDetail_withValidGynecologicalDetails() throws Exception {
		when(cSNurseServiceImpl.updateCancerGynecologicalExaminationDetails(any())).thenReturn(1);

		int result = csServiceImpl.updateBenExaminationDetail(validGynecologicalDetails);

		assertEquals(1, result);
		verify(cSNurseServiceImpl, times(1)).updateCancerGynecologicalExaminationDetails(any());
	}

	@Test
	public void testUpdateBenExaminationDetail_withValidImageCoordinates() throws Exception {
		when(cSNurseServiceImpl.updateCancerExamImgAnotasnDetails(any())).thenReturn(1);

		int result = csServiceImpl.updateBenExaminationDetail(validImageCoordinates);

		assertEquals(1, result);
		verify(cSNurseServiceImpl, times(1)).updateCancerExamImgAnotasnDetails(any());
	}

	@Test
	public void testGetBenDataFrmNurseToDocVisitDetailsScreen_ValidInput() {
		when(commonNurseServiceImpl.getCSVisitDetails(benRegID, visitCode)).thenReturn(benVisitDetailsOBJ);

		String response = csServiceImpl.getBenDataFrmNurseToDocVisitDetailsScreen(benRegID, visitCode);

		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("benVisitDetails", benVisitDetailsOBJ);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String expectedResponse = gson.toJson(expectedMap);

		assertEquals(expectedResponse, response);
		verify(commonNurseServiceImpl, times(1)).getCSVisitDetails(benRegID, visitCode);
	}

	@Test
	public void testGetBenDataFrmNurseToDocVisitDetailsScreen_InvalidInput() {
		when(commonNurseServiceImpl.getCSVisitDetails(benRegID, visitCode)).thenReturn(null);

		String response = csServiceImpl.getBenDataFrmNurseToDocVisitDetailsScreen(benRegID, visitCode);

		Map<String, Object> expectedMap = new HashMap<>();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String expectedResponse = gson.toJson(expectedMap);

		assertEquals(expectedResponse, response);
		verify(commonNurseServiceImpl, times(1)).getCSVisitDetails(benRegID, visitCode);
	}

	@Test
	public void testGetBenDataFrmNurseToDocHistoryScreen() {
		// Call the method under test
		String result = cSServiceImpl.getBenDataFrmNurseToDocHistoryScreen(benRegID, visitCode);

		// Prepare expected JSON result
		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("benFamilyHistory", "familyHistoryData");
		expectedMap.put("benObstetricHistory", "obstetricHistoryData");
		expectedMap.put("benPersonalHistory", "personalHistoryData");
		expectedMap.put("benPersonalDietHistory", "dietHistoryData");

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.serializeNulls();
		Gson gson = gsonBuilder.create();
		String expectedJson = gson.toJson(expectedMap);

		// Assert the result
		assertEquals(expectedJson, result);
	}

	@Test
	public void testGetBenDataFrmNurseToDocVitalScreen() {
		Long benRegID = 1L;
		Long visitCode = 1L;

		BenCancerVitalDetail mockVitalDetail = new BenCancerVitalDetail();
		Map<String, Object> mockGraphData = new HashMap<>();
		mockGraphData.put("someKey", "someValue");

		when(cSNurseServiceImpl.getBenCancerVitalDetailData(benRegID, visitCode)).thenReturn(mockVitalDetail);
		when(commonNurseServiceImpl.getGraphicalTrendData(benRegID, "cancer screening")).thenReturn(mockGraphData);

		String response = csServiceImpl.getBenDataFrmNurseToDocVitalScreen(benRegID, visitCode);

		Gson gson = new Gson();
		Map<String, Object> responseMap = gson.fromJson(response, Map.class);

		assertTrue(responseMap.containsKey("benVitalDetails"));
		assertTrue(responseMap.containsKey("GraphData"));
		assertEquals(mockVitalDetail, responseMap.get("benVitalDetails"));
		assertEquals(mockGraphData, responseMap.get("GraphData"));

		verify(cSNurseServiceImpl, times(1)).getBenCancerVitalDetailData(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getGraphicalTrendData(benRegID, "cancer screening");
	}

	@Test
	public void testGetBenDataFrmNurseToDocExaminationScreen() {
		// Mocking the return values of the dependencies
		when(cSNurseServiceImpl.getBenCancerAbdominalExaminationData(benRegID, visitCode))
				.thenReturn(new CancerAbdominalExamination());
		when(cSNurseServiceImpl.getBenCancerBreastExaminationData(benRegID, visitCode))
				.thenReturn(new CancerBreastExamination());
		when(cSNurseServiceImpl.getBenCancerGynecologicalExaminationData(benRegID, visitCode))
				.thenReturn(new CancerGynecologicalExamination());
		when(cSNurseServiceImpl.getBenCancerSignAndSymptomsData(benRegID, visitCode))
				.thenReturn(new CancerSignAndSymptoms());
		when(cSNurseServiceImpl.getBenCancerLymphNodeDetailsData(benRegID, visitCode))
				.thenReturn(new CancerLymphNodeDetails());
		when(cSNurseServiceImpl.getBenCancerOralExaminationData(benRegID, visitCode))
				.thenReturn(new CancerOralExamination());
		when(cSNurseServiceImpl.getCancerExaminationImageAnnotationCasesheet(benRegID, visitCode))
				.thenReturn(new CancerExamImgAnotasn());

		// Call the method under test
		String response = csServiceImpl.getBenDataFrmNurseToDocExaminationScreen(benRegID, visitCode);

		// Deserialize the response
		Gson gson = new Gson();
		Map<String, Object> responseMap = gson.fromJson(response, HashMap.class);

		// Assertions
		assertNotNull(responseMap);
		assertTrue(responseMap.containsKey("abdominalExamination"));
		assertTrue(responseMap.containsKey("breastExamination"));
		assertTrue(responseMap.containsKey("gynecologicalExamination"));
		assertTrue(responseMap.containsKey("signsAndSymptoms"));
		assertTrue(responseMap.containsKey("BenCancerLymphNodeDetails"));
		assertTrue(responseMap.containsKey("oralExamination"));
		assertTrue(responseMap.containsKey("imageCoordinates"));

		// Verify the interactions with the mocked dependencies
		verify(cSNurseServiceImpl, times(1)).getBenCancerAbdominalExaminationData(benRegID, visitCode);
		verify(cSNurseServiceImpl, times(1)).getBenCancerBreastExaminationData(benRegID, visitCode);
		verify(cSNurseServiceImpl, times(1)).getBenCancerGynecologicalExaminationData(benRegID, visitCode);
		verify(cSNurseServiceImpl, times(1)).getBenCancerSignAndSymptomsData(benRegID, visitCode);
		verify(cSNurseServiceImpl, times(1)).getBenCancerLymphNodeDetailsData(benRegID, visitCode);
		verify(cSNurseServiceImpl, times(1)).getBenCancerOralExaminationData(benRegID, visitCode);
		verify(cSNurseServiceImpl, times(1)).getCancerExaminationImageAnnotationCasesheet(benRegID, visitCode);
	}

	@Test
	public void testSaveCancerScreeningDoctorData_Success() throws Exception {
		when(commonDoctorServiceImpl.callTmForSpecialistSlotBook(any(), eq(authorization))).thenReturn(1);
		when(teleConsultationServiceImpl.createTCRequest(any())).thenReturn(1L);
		when(commonDoctorServiceImpl.updateBenFlowAfterDocData(anyLong(), anyLong(), anyLong(), anyLong(), anyShort(),
				anyShort(), anyShort(), anyShort(), anyInt(), any(), anyShort())).thenReturn(1);

		Long result = csServiceImpl.saveCancerScreeningDoctorData(validRequestObj, authorization);

		assertNotNull(result);
		assertTrue(result > 0);
		verify(commonDoctorServiceImpl, times(1)).callTmForSpecialistSlotBook(any(), eq(authorization));
		verify(teleConsultationServiceImpl, times(1)).createTCRequest(any());
		verify(commonDoctorServiceImpl, times(1)).updateBenFlowAfterDocData(anyLong(), anyLong(), anyLong(), anyLong(),
				anyShort(), anyShort(), anyShort(), anyShort(), anyInt(), any(), anyShort());
	}

	@Test
	public void testSaveCancerScreeningDoctorData_InvalidInput() {
		Exception exception = assertThrows(Exception.class, () -> {
			csServiceImpl.saveCancerScreeningDoctorData(invalidRequestObj, authorization);
		});

		assertEquals("Invalid input", exception.getMessage());
	}

	@Test
	public void testSaveCancerScreeningDoctorData_SlotBookingFailure() throws Exception {
		when(commonDoctorServiceImpl.callTmForSpecialistSlotBook(any(), eq(authorization))).thenReturn(0);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			csServiceImpl.saveCancerScreeningDoctorData(validRequestObj, authorization);
		});

		assertEquals("Error while booking slot.", exception.getMessage());
		verify(commonDoctorServiceImpl, times(1)).callTmForSpecialistSlotBook(any(), eq(authorization));
		verify(teleConsultationServiceImpl, times(0)).createTCRequest(any());
	}

	@Test
	public void testSaveCancerScreeningDoctorData_BeneficiaryStatusUpdateFailure() throws Exception {
		when(commonDoctorServiceImpl.callTmForSpecialistSlotBook(any(), eq(authorization))).thenReturn(1);
		when(teleConsultationServiceImpl.createTCRequest(any())).thenReturn(1L);
		when(commonDoctorServiceImpl.updateBenFlowAfterDocData(anyLong(), anyLong(), anyLong(), anyLong(), anyShort(),
				anyShort(), anyShort(), anyShort(), anyInt(), any(), anyShort())).thenReturn(0);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			csServiceImpl.saveCancerScreeningDoctorData(validRequestObj, authorization);
		});

		assertEquals("Error occurred while saving data. Beneficiary status update failed", exception.getMessage());
		verify(commonDoctorServiceImpl, times(1)).callTmForSpecialistSlotBook(any(), eq(authorization));
		verify(teleConsultationServiceImpl, times(1)).createTCRequest(any());
		verify(commonDoctorServiceImpl, times(1)).updateBenFlowAfterDocData(anyLong(), anyLong(), anyLong(), anyLong(),
				anyShort(), anyShort(), anyShort(), anyShort(), anyInt(), any(), anyShort());
	}

	@Test
	public void testSaveBenExaminationDetails_Success() throws Exception {
		// Setup requestOBJ with all required fields
		JsonObject examinationDetails = new JsonObject();
		examinationDetails.add("signsDetails", new JsonObject());
		examinationDetails.add("oralDetails", new JsonObject());
		examinationDetails.add("breastDetails", new JsonObject());
		examinationDetails.add("abdominalDetails", new JsonObject());
		examinationDetails.add("gynecologicalDetails", new JsonObject());
		examinationDetails.add("imageCoordinates", new JsonObject());
		requestOBJ.add("examinationDetails", examinationDetails);

		// Mocking the service methods
		when(cSNurseServiceImpl.saveCancerSignAndSymptomsData(any(), anyLong(), anyLong())).thenReturn(1L);
		when(cSNurseServiceImpl.saveLymphNodeDetails(any(), anyLong(), anyLong())).thenReturn(1L);
		when(cSNurseServiceImpl.saveCancerOralExaminationData(any())).thenReturn(1L);
		when(cSNurseServiceImpl.saveCancerBreastExaminationData(any())).thenReturn(1L);
		when(cSNurseServiceImpl.saveCancerAbdominalExaminationData(any())).thenReturn(1L);
		when(cSNurseServiceImpl.saveCancerGynecologicalExaminationData(any())).thenReturn(1L);
		when(cSNurseServiceImpl.saveDocExaminationImageAnnotation(any(), anyLong(), anyLong())).thenReturn(1L);

		Long result = csServiceImpl.saveBenExaminationDetails(requestOBJ, benVisitID, authorization, benVisitCode,
				benFlowID);

		assertNotNull(result);
		assertEquals(1L, result);
	}

	@Test
	public void testSaveBenExaminationDetails_MissingFields() throws Exception {
		// Setup requestOBJ with missing fields
		JsonObject examinationDetails = new JsonObject();
		examinationDetails.add("signsDetails", new JsonObject());
		requestOBJ.add("examinationDetails", examinationDetails);

		// Mocking the service methods
		when(cSNurseServiceImpl.saveCancerSignAndSymptomsData(any(), anyLong(), anyLong())).thenReturn(1L);
		when(cSNurseServiceImpl.saveLymphNodeDetails(any(), anyLong(), anyLong())).thenReturn(1L);

		Long result = csServiceImpl.saveBenExaminationDetails(requestOBJ, benVisitID, authorization, benVisitCode,
				benFlowID);

		assertNotNull(result);
		assertEquals(1L, result);
	}

	@Test
	public void testSaveBenExaminationDetails_NullValues() throws Exception {
		// Setup requestOBJ with null values
		JsonObject examinationDetails = new JsonObject();
		examinationDetails.add("signsDetails", null);
		requestOBJ.add("examinationDetails", examinationDetails);

		// Mocking the service methods
		when(cSNurseServiceImpl.saveCancerSignAndSymptomsData(any(), anyLong(), anyLong())).thenReturn(1L);
		when(cSNurseServiceImpl.saveLymphNodeDetails(any(), anyLong(), anyLong())).thenReturn(1L);

		Long result = csServiceImpl.saveBenExaminationDetails(requestOBJ, benVisitID, authorization, benVisitCode,
				benFlowID);

		assertNotNull(result);
		assertEquals(1L, result);
	}

	@Test
	public void testSaveBenExaminationDetails_Exception() throws Exception {
		// Setup requestOBJ with all required fields
		JsonObject examinationDetails = new JsonObject();
		examinationDetails.add("signsDetails", new JsonObject());
		requestOBJ.add("examinationDetails", examinationDetails);

		// Mocking the service methods to throw exception
		when(cSNurseServiceImpl.saveCancerSignAndSymptomsData(any(), anyLong(), anyLong()))
				.thenThrow(new RuntimeException("Error"));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			csServiceImpl.saveBenExaminationDetails(requestOBJ, benVisitID, authorization, benVisitCode, benFlowID);
		});

		assertEquals("Error", exception.getMessage());
	}

	@Test
	public void testSaveBenDiagnosisDetails_NullRequest() throws Exception {
		Long result = cSServiceImpl.saveBenDiagnosisDetails(null);
		assertEquals(Long.valueOf(1), result);
	}

	@Test
	public void testSaveBenDiagnosisDetails_NoDiagnosis() throws Exception {
		JsonObject requestOBJ = new JsonObject();
		Long result = cSServiceImpl.saveBenDiagnosisDetails(requestOBJ);
		assertEquals(Long.valueOf(1), result);
	}

	@Test
	public void testSaveBenDiagnosisDetails_SaveFails() throws Exception {
		JsonObject requestOBJ = new JsonObject();
		JsonObject diagnosis = new JsonObject();
		requestOBJ.add("diagnosis", diagnosis);

		when(cSDoctorServiceImpl.saveCancerDiagnosisData(any(CancerDiagnosis.class))).thenReturn(null);

		Long result = cSServiceImpl.saveBenDiagnosisDetails(requestOBJ);
		assertEquals(Long.valueOf(1), result);
		verify(cSDoctorServiceImpl, times(1)).saveCancerDiagnosisData(any(CancerDiagnosis.class));
	}

	@Test
	public void testSaveBenDiagnosisDetails_SaveSucceeds() throws Exception {
		JsonObject requestOBJ = new JsonObject();
		JsonObject diagnosis = new JsonObject();
		requestOBJ.add("diagnosis", diagnosis);

		when(cSDoctorServiceImpl.saveCancerDiagnosisData(any(CancerDiagnosis.class))).thenReturn(100L);

		Long result = cSServiceImpl.saveBenDiagnosisDetails(requestOBJ);
		assertEquals(Long.valueOf(100), result);
		verify(cSDoctorServiceImpl, times(1)).saveCancerDiagnosisData(any(CancerDiagnosis.class));
	}

	@Test
	public void testGetCancerCasesheetData() throws Exception {
		Map<String, Object> nurseData = new HashMap<>();
		nurseData.put("key", "nurseData");
		Map<String, Object> doctorData = new HashMap<>();
		doctorData.put("key", "doctorData");
		Map<String, Object> benDetails = new HashMap<>();
		benDetails.put("key", "benDetails");
		Map<String, Object> imageAnnotatedData = new HashMap<>();
		imageAnnotatedData.put("key", "imageAnnotatedData");

		when(cSNurseServiceImpl.getBenNurseDataForCaseSheet(anyLong(), anyLong())).thenReturn(nurseData);
		when(cSDoctorServiceImpl.getBenDoctorEnteredDataForCaseSheet(anyLong(), anyLong())).thenReturn(doctorData);
		when(beneficiaryFlowStatusRepo.getBenDetailsForLeftSidePanel(anyLong()))
				.thenReturn(new BeneficiaryFlowStatus());
		when(cSNurseServiceImpl.getCancerExaminationImageAnnotationCasesheet(anyLong(), anyLong()))
				.thenReturn(imageAnnotatedData);

		String response = csServiceImpl.getCancerCasesheetData(requestObj, authorization);

		assertNotNull(response);
		assertTrue(response.contains("nurseData"));
		assertTrue(response.contains("doctorData"));
		assertTrue(response.contains("benDetails"));
		assertTrue(response.contains("imageAnnotatedData"));

		verify(cSNurseServiceImpl, times(1)).getBenNurseDataForCaseSheet(anyLong(), anyLong());
		verify(cSDoctorServiceImpl, times(1)).getBenDoctorEnteredDataForCaseSheet(anyLong(), anyLong());
		verify(beneficiaryFlowStatusRepo, times(1)).getBenDetailsForLeftSidePanel(anyLong());
		verify(cSNurseServiceImpl, times(1)).getCancerExaminationImageAnnotationCasesheet(anyLong(), anyLong());
	}

	@Test
	public void testGetCancerCasesheetDataWithEmptyRequest() throws Exception {
		JSONObject emptyRequest = new JSONObject();

		String response = csServiceImpl.getCancerCasesheetData(emptyRequest, authorization);

		assertNull(response);
	}

	@Test
	public void testGetCancerCasesheetDataWithJSONException() throws Exception {
		JSONObject invalidRequest = new JSONObject();
		invalidRequest.put("invalidKey", "invalidValue");

		assertThrows(JSONException.class, () -> {
			csServiceImpl.getCancerCasesheetData(invalidRequest, authorization);
		});
	}

	@Test
	public void testGetBenDataForCaseSheet_ValidInput() throws Exception {
		Map<String, Object> nurseData = new HashMap<>();
		nurseData.put("key1", "value1");

		Map<String, Object> doctorData = new HashMap<>();
		doctorData.put("key2", "value2");

		BeneficiaryFlowStatus beneficiaryFlowStatus = new BeneficiaryFlowStatus();
		beneficiaryFlowStatus.setBenFlowID(benFlowID);

		when(commonNurseServiceImpl.getBenNurseDataForCaseSheet(benRegID, visitCode)).thenReturn(nurseData);
		when(cSDoctorServiceImpl.getBenDoctorEnteredDataForCaseSheet(benRegID, visitCode)).thenReturn(doctorData);
		when(beneficiaryFlowStatusRepo.getBenDetailsForLeftSidePanel(benFlowID)).thenReturn(beneficiaryFlowStatus);

		String result = cSServiceImpl.getBenDataForCaseSheet(benFlowID, benRegID, visitCode, authorization);

		Gson gson = new Gson();
		Map<String, Object> resultMap = gson.fromJson(result, Map.class);

		assertTrue(resultMap.containsKey("key1"));
		assertTrue(resultMap.containsKey("key2"));
		assertTrue(resultMap.containsKey("BeneficiaryData"));
	}

	@Test
	public void testGetBenDataForCaseSheet_InvalidInput() throws Exception {
		String result = cSServiceImpl.getBenDataForCaseSheet(null, null, null, null);

		Gson gson = new Gson();
		Map<String, Object> resultMap = gson.fromJson(result, Map.class);

		assertNull(resultMap);
	}

	@Test
	public void testGetBenDataForCaseSheet_ExceptionHandling() throws Exception {
		when(commonNurseServiceImpl.getBenNurseDataForCaseSheet(benRegID, visitCode))
				.thenThrow(new RuntimeException("Exception"));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			cSServiceImpl.getBenDataForCaseSheet(benFlowID, benRegID, visitCode, authorization);
		});

		assertEquals("Exception", exception.getMessage());
	}

	@Test
	public void testGetBenNurseDataForCaseSheet_AllDataAvailable() {
		// Mocking the return values
		when(cSNurseServiceImpl.getBeneficiaryVisitDetails(benRegID, visitCode))
				.thenReturn(new BeneficiaryVisitDetail());
		when(cSNurseServiceImpl.getBenFamilyHisData(benRegID, visitCode)).thenReturn(new BenFamilyHistory());
		when(cSNurseServiceImpl.getBenObstetricDetailsData(benRegID, visitCode)).thenReturn(new BenObstetricHistory());
		when(cSNurseServiceImpl.getBenPersonalCancerHistoryData(benRegID, visitCode))
				.thenReturn(new BenPersonalHistory());
		when(cSNurseServiceImpl.getBenPersonalCancerDietHistoryData(benRegID, visitCode))
				.thenReturn(new BenPersonalDietHistory());
		when(cSNurseServiceImpl.getBenCancerVitalDetailData(benRegID, visitCode)).thenReturn(new BenVitalDetail());
		when(cSNurseServiceImpl.getBenCancerAbdominalExaminationData(benRegID, visitCode))
				.thenReturn(new CancerAbdominalExamination());
		when(cSNurseServiceImpl.getBenCancerBreastExaminationData(benRegID, visitCode))
				.thenReturn(new CancerBreastExamination());
		when(cSNurseServiceImpl.getBenCancerGynecologicalExaminationData(benRegID, visitCode))
				.thenReturn(new CancerGynecologicalExamination());
		when(cSNurseServiceImpl.getBenCancerSignAndSymptomsData(benRegID, visitCode))
				.thenReturn(new CancerSignAndSymptoms());
		when(cSNurseServiceImpl.getBenCancerLymphNodeDetailsData(benRegID, visitCode))
				.thenReturn(new CancerLymphNodeDetails());
		when(cSNurseServiceImpl.getBenCancerOralExaminationData(benRegID, visitCode))
				.thenReturn(new CancerOralExamination());

		// Expected JSON
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("benVisitDetail", new BeneficiaryVisitDetail());
		resMap.put("familyDiseaseHistory", new BenFamilyHistory());
		resMap.put("patientObstetricHistory", new BenObstetricHistory());
		resMap.put("patientPersonalHistory", new BenPersonalHistory());
		resMap.put("benPersonalDietHistory", new BenPersonalDietHistory());
		resMap.put("currentVitals", new BenVitalDetail());
		resMap.put("abdominalExamination", new CancerAbdominalExamination());
		resMap.put("breastExamination", new CancerBreastExamination());
		resMap.put("gynecologicalExamination", new CancerGynecologicalExamination());
		resMap.put("signsAndSymptoms", new CancerSignAndSymptoms());
		resMap.put("BenCancerLymphNodeDetails", new CancerLymphNodeDetails());
		resMap.put("oralExamination", new CancerOralExamination());

		Gson gson = new Gson();
		String expectedJson = gson.toJson(resMap);

		// Call the method
		String actualJson = csServiceImpl.getBenNurseDataForCaseSheet(benRegID, visitCode);

		// Assert the result
		assertEquals(expectedJson, actualJson);
	}

	@Test
	public void testGetBenNurseDataForCaseSheet_NullData() {
		// Mocking the return values to return null
		when(cSNurseServiceImpl.getBeneficiaryVisitDetails(benRegID, visitCode)).thenReturn(null);
		when(cSNurseServiceImpl.getBenFamilyHisData(benRegID, visitCode)).thenReturn(null);
		when(cSNurseServiceImpl.getBenObstetricDetailsData(benRegID, visitCode)).thenReturn(null);
		when(cSNurseServiceImpl.getBenPersonalCancerHistoryData(benRegID, visitCode)).thenReturn(null);
		when(cSNurseServiceImpl.getBenPersonalCancerDietHistoryData(benRegID, visitCode)).thenReturn(null);
		when(cSNurseServiceImpl.getBenCancerVitalDetailData(benRegID, visitCode)).thenReturn(null);
		when(cSNurseServiceImpl.getBenCancerAbdominalExaminationData(benRegID, visitCode)).thenReturn(null);
		when(cSNurseServiceImpl.getBenCancerBreastExaminationData(benRegID, visitCode)).thenReturn(null);
		when(cSNurseServiceImpl.getBenCancerGynecologicalExaminationData(benRegID, visitCode)).thenReturn(null);
		when(cSNurseServiceImpl.getBenCancerSignAndSymptomsData(benRegID, visitCode)).thenReturn(null);
		when(cSNurseServiceImpl.getBenCancerLymphNodeDetailsData(benRegID, visitCode)).thenReturn(null);
		when(cSNurseServiceImpl.getBenCancerOralExaminationData(benRegID, visitCode)).thenReturn(null);

		// Expected JSON
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("benVisitDetail", null);
		resMap.put("familyDiseaseHistory", null);
		resMap.put("patientObstetricHistory", null);
		resMap.put("patientPersonalHistory", null);
		resMap.put("benPersonalDietHistory", null);
		resMap.put("currentVitals", null);
		resMap.put("abdominalExamination", null);
		resMap.put("breastExamination", null);
		resMap.put("gynecologicalExamination", null);
		resMap.put("signsAndSymptoms", null);
		resMap.put("BenCancerLymphNodeDetails", null);
		resMap.put("oralExamination", null);

		Gson gson = new Gson();
		String expectedJson = gson.toJson(resMap);

		// Call the method
		String actualJson = csServiceImpl.getBenNurseDataForCaseSheet(benRegID, visitCode);

		// Assert the result
		assertEquals(expectedJson, actualJson);
	}

	@Test
	public void testGetBenNurseDataForCaseSheet_VerifyMethodCalls() {
		// Call the method
		csServiceImpl.getBenNurseDataForCaseSheet(benRegID, visitCode);

		// Verify that the methods in cSNurseServiceImpl are called with the correct
		// parameters
		verify(cSNurseServiceImpl, times(1)).getBeneficiaryVisitDetails(benRegID, visitCode);
		verify(cSNurseServiceImpl, times(1)).getBenFamilyHisData(benRegID, visitCode);
		verify(cSNurseServiceImpl, times(1)).getBenObstetricDetailsData(benRegID, visitCode);
		verify(cSNurseServiceImpl, times(1)).getBenPersonalCancerHistoryData(benRegID, visitCode);
		verify(cSNurseServiceImpl, times(1)).getBenPersonalCancerDietHistoryData(benRegID, visitCode);
		verify(cSNurseServiceImpl, times(1)).getBenCancerVitalDetailData(benRegID, visitCode);
		verify(cSNurseServiceImpl, times(1)).getBenCancerAbdominalExaminationData(benRegID, visitCode);
		verify(cSNurseServiceImpl, times(1)).getBenCancerBreastExaminationData(benRegID, visitCode);
		verify(cSNurseServiceImpl, times(1)).getBenCancerGynecologicalExaminationData(benRegID, visitCode);
		verify(cSNurseServiceImpl, times(1)).getBenCancerSignAndSymptomsData(benRegID, visitCode);
		verify(cSNurseServiceImpl, times(1)).getBenCancerLymphNodeDetailsData(benRegID, visitCode);
		verify(cSNurseServiceImpl, times(1)).getBenCancerOralExaminationData(benRegID, visitCode);
	}

	@Test
	public void testGetBenFamilyHistoryData() {
		Long beneficiaryRegID = 1L;
		String expectedResponse = "FamilyHistoryData";

		when(cSNurseServiceImpl.getBenCancerFamilyHistory(beneficiaryRegID)).thenReturn(expectedResponse);

		String actualResponse = csServiceImpl.getBenFamilyHistoryData(beneficiaryRegID);

		assertEquals(expectedResponse, actualResponse);
		verify(cSNurseServiceImpl, times(1)).getBenCancerFamilyHistory(beneficiaryRegID);
	}

	@Test
	public void testGetBenPersonalHistoryData_Success() {
		String expectedResponse = "Personal History Data";
		when(cSNurseServiceImpl.getBenCancerPersonalHistory(beneficiaryRegID)).thenReturn(expectedResponse);

		String actualResponse = cSServiceImpl.getBenPersonalHistoryData(beneficiaryRegID);

		assertEquals(expectedResponse, actualResponse);
		verify(cSNurseServiceImpl, times(1)).getBenCancerPersonalHistory(beneficiaryRegID);
	}

	@Test
	public void testGetBenPersonalHistoryData_NoData() {
		when(cSNurseServiceImpl.getBenCancerPersonalHistory(beneficiaryRegID)).thenReturn(null);

		String actualResponse = cSServiceImpl.getBenPersonalHistoryData(beneficiaryRegID);

		assertNull(actualResponse);
		verify(cSNurseServiceImpl, times(1)).getBenCancerPersonalHistory(beneficiaryRegID);
	}

	@Test
	public void testGetBenPersonalHistoryData_Exception() {
		when(cSNurseServiceImpl.getBenCancerPersonalHistory(beneficiaryRegID))
				.thenThrow(new RuntimeException("Exception occurred"));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			cSServiceImpl.getBenPersonalHistoryData(beneficiaryRegID);
		});

		assertEquals("Exception occurred", exception.getMessage());
		verify(cSNurseServiceImpl, times(1)).getBenCancerPersonalHistory(beneficiaryRegID);
	}

	@Test
	public void testGetBenPersonalDietHistoryData_Success() {
		String expectedResponse = "Personal Diet History Data";
		when(cSNurseServiceImpl.getBenCancerPersonalDietHistory(beneficiaryRegID)).thenReturn(expectedResponse);

		String actualResponse = cSServiceImpl.getBenPersonalDietHistoryData(beneficiaryRegID);

		assertEquals(expectedResponse, actualResponse);
		verify(cSNurseServiceImpl, times(1)).getBenCancerPersonalDietHistory(beneficiaryRegID);
	}

	@Test
	public void testGetBenPersonalDietHistoryData_NoData() {
		when(cSNurseServiceImpl.getBenCancerPersonalDietHistory(beneficiaryRegID)).thenReturn(null);

		String actualResponse = cSServiceImpl.getBenPersonalDietHistoryData(beneficiaryRegID);

		assertNull(actualResponse);
		verify(cSNurseServiceImpl, times(1)).getBenCancerPersonalDietHistory(beneficiaryRegID);
	}

	@Test
	public void testGetBenPersonalDietHistoryData_Exception() {
		when(cSNurseServiceImpl.getBenCancerPersonalDietHistory(beneficiaryRegID))
				.thenThrow(new RuntimeException("Exception occurred"));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			cSServiceImpl.getBenPersonalDietHistoryData(beneficiaryRegID);
		});

		assertEquals("Exception occurred", exception.getMessage());
		verify(cSNurseServiceImpl, times(1)).getBenCancerPersonalDietHistory(beneficiaryRegID);
	}

	@Test
	public void testGetBenObstetricHistoryData_Success() {
		String expectedResponse = "Obstetric History Data";
		when(cSNurseServiceImpl.getBenCancerObstetricHistory(beneficiaryRegID)).thenReturn(expectedResponse);

		String actualResponse = cSServiceImpl.getBenObstetricHistoryData(beneficiaryRegID);

		assertEquals(expectedResponse, actualResponse);
		verify(cSNurseServiceImpl, times(1)).getBenCancerObstetricHistory(beneficiaryRegID);
	}

	@Test
	public void testGetBenObstetricHistoryData_NoData() {
		when(cSNurseServiceImpl.getBenCancerObstetricHistory(beneficiaryRegID)).thenReturn(null);

		String actualResponse = cSServiceImpl.getBenObstetricHistoryData(beneficiaryRegID);

		assertNull(actualResponse);
		verify(cSNurseServiceImpl, times(1)).getBenCancerObstetricHistory(beneficiaryRegID);
	}

	@Test
	public void testGetBenObstetricHistoryData_Exception() {
		when(cSNurseServiceImpl.getBenCancerObstetricHistory(beneficiaryRegID))
				.thenThrow(new RuntimeException("Exception occurred"));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			cSServiceImpl.getBenObstetricHistoryData(beneficiaryRegID);
		});

		assertEquals("Exception occurred", exception.getMessage());
		verify(cSNurseServiceImpl, times(1)).getBenCancerObstetricHistory(beneficiaryRegID);
	}

	@Test
	public void testUpdateCancerDiagnosisDetailsByOncologist_Success() {
		when(csOncologistServiceImpl.updateCancerDiagnosisDetailsByOncologist(cancerDiagnosis)).thenReturn(1);

		int result = csServiceImpl.updateCancerDiagnosisDetailsByOncologist(cancerDiagnosis);

		assertEquals(1, result);
		verify(csOncologistServiceImpl, times(1)).updateCancerDiagnosisDetailsByOncologist(cancerDiagnosis);
	}

	@Test
	public void testUpdateCancerDiagnosisDetailsByOncologist_Failure() {
		when(csOncologistServiceImpl.updateCancerDiagnosisDetailsByOncologist(cancerDiagnosis)).thenReturn(0);

		int result = csServiceImpl.updateCancerDiagnosisDetailsByOncologist(cancerDiagnosis);

		assertEquals(0, result);
		verify(csOncologistServiceImpl, times(1)).updateCancerDiagnosisDetailsByOncologist(cancerDiagnosis);
	}

	@Test
	public void testCreateCareStreamOrder_Success() {
		ArrayList<Object[]> benDataForCareStream = new ArrayList<>();
		when(beneficiaryFlowStatusRepo.getBenDataForCareStream(benFlowID)).thenReturn(benDataForCareStream);
		when(cSCarestreamServiceImpl.createMamographyRequest(benDataForCareStream, benRegID, benVisitID, authorization))
				.thenReturn(1);

		int result = csServiceImpl.createCareStreamOrder(benRegID, benVisitID, authorization, benFlowID);

		assertEquals(1, result);
		verify(beneficiaryFlowStatusRepo, times(1)).getBenDataForCareStream(benFlowID);
		verify(cSCarestreamServiceImpl, times(1)).createMamographyRequest(benDataForCareStream, benRegID, benVisitID,
				authorization);
	}

	@Test
	public void testCreateCareStreamOrder_Failure() {
		ArrayList<Object[]> benDataForCareStream = new ArrayList<>();
		when(beneficiaryFlowStatusRepo.getBenDataForCareStream(benFlowID)).thenReturn(benDataForCareStream);
		when(cSCarestreamServiceImpl.createMamographyRequest(benDataForCareStream, benRegID, benVisitID, authorization))
				.thenReturn(0);

		int result = csServiceImpl.createCareStreamOrder(benRegID, benVisitID, authorization, benFlowID);

		assertEquals(0, result);
		verify(beneficiaryFlowStatusRepo, times(1)).getBenDataForCareStream(benFlowID);
		verify(cSCarestreamServiceImpl, times(1)).createMamographyRequest(benDataForCareStream, benRegID, benVisitID,
				authorization);
	}

	@Test
	public void testGetBenDoctorDiagnosisData_Success() {
		Map<String, Object> expectedDiagnosisData = new HashMap<>();
		expectedDiagnosisData.put("diagnosisKey", "diagnosisValue");

		when(cSDoctorServiceImpl.getBenCancerDiagnosisData(benRegID, visitCode)).thenReturn(expectedDiagnosisData);

		String actualResponse = cSServiceImpl.getBenDoctorDiagnosisData(benRegID, visitCode);

		Map<String, Object> expectedResponseMap = new HashMap<>();
		expectedResponseMap.put("benDiagnosisDetails", expectedDiagnosisData);
		String expectedResponse = new Gson().toJson(expectedResponseMap);

		assertEquals(expectedResponse, actualResponse);
		verify(cSDoctorServiceImpl, times(1)).getBenCancerDiagnosisData(benRegID, visitCode);
	}

	@Test
	public void testGetBenDoctorDiagnosisData_NoData() {
		when(cSDoctorServiceImpl.getBenCancerDiagnosisData(benRegID, visitCode)).thenReturn(null);

		String actualResponse = cSServiceImpl.getBenDoctorDiagnosisData(benRegID, visitCode);

		Map<String, Object> expectedResponseMap = new HashMap<>();
		expectedResponseMap.put("benDiagnosisDetails", null);
		String expectedResponse = new Gson().toJson(expectedResponseMap);

		assertEquals(expectedResponse, actualResponse);
		verify(cSDoctorServiceImpl, times(1)).getBenCancerDiagnosisData(benRegID, visitCode);
	}

	@Test
	public void testGetBenDoctorDiagnosisData_Exception() {
		when(cSDoctorServiceImpl.getBenCancerDiagnosisData(benRegID, visitCode))
				.thenThrow(new RuntimeException("Exception occurred"));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			cSServiceImpl.getBenDoctorDiagnosisData(benRegID, visitCode);
		});

		assertEquals("Exception occurred", exception.getMessage());
		verify(cSDoctorServiceImpl, times(1)).getBenCancerDiagnosisData(benRegID, visitCode);
	}

	@Test
	public void testGetBenCaseRecordFromDoctorCS_Success() {
		Map<String, Object> expectedDiagnosisData = new HashMap<>();
		expectedDiagnosisData.put("diagnosisKey", "diagnosisValue");

		when(cSDoctorServiceImpl.getBenCancerDiagnosisData(benRegID, visitCode)).thenReturn(expectedDiagnosisData);

		String actualResponse = cSServiceImpl.getBenCaseRecordFromDoctorCS(benRegID, visitCode);

		Map<String, Object> expectedResponseMap = new HashMap<>();
		expectedResponseMap.put("diagnosis", expectedDiagnosisData);
		String expectedResponse = new Gson().toJson(expectedResponseMap);

		assertEquals(expectedResponse, actualResponse);
		verify(cSDoctorServiceImpl, times(1)).getBenCancerDiagnosisData(benRegID, visitCode);
	}

	@Test
	public void testGetBenCaseRecordFromDoctorCS_NoData() {
		when(cSDoctorServiceImpl.getBenCancerDiagnosisData(benRegID, visitCode)).thenReturn(null);

		String actualResponse = cSServiceImpl.getBenCaseRecordFromDoctorCS(benRegID, visitCode);

		Map<String, Object> expectedResponseMap = new HashMap<>();
		expectedResponseMap.put("diagnosis", null);
		String expectedResponse = new Gson().toJson(expectedResponseMap);

		assertEquals(expectedResponse, actualResponse);
		verify(cSDoctorServiceImpl, times(1)).getBenCancerDiagnosisData(benRegID, visitCode);
	}

	@Test
	public void testGetBenCaseRecordFromDoctorCS_Exception() {
		when(cSDoctorServiceImpl.getBenCancerDiagnosisData(benRegID, visitCode))
				.thenThrow(new RuntimeException("Exception occurred"));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			cSServiceImpl.getBenCaseRecordFromDoctorCS(benRegID, visitCode);
		});

		assertEquals("Exception occurred", exception.getMessage());
		verify(cSDoctorServiceImpl, times(1)).getBenCancerDiagnosisData(benRegID, visitCode);
	}

	@Test
	public void testUpdateCancerScreeningDoctorData_ValidRequest() throws Exception {
		when(cSDoctorServiceImpl.updateCancerDiagnosisDetailsByDoctor(any(CancerDiagnosis.class))).thenReturn(1);
		when(beneficiaryFlowStatusRepo.updateBenFlowAfterTCSpcialistDoneForCanceScreening(anyLong(), anyLong(),
				anyLong())).thenReturn(1);
		when(tCRequestModelRepo.updateStatusIfConsultationCompleted(anyLong(), anyLong(), anyString())).thenReturn(1);

		int result = csServiceImpl.updateCancerScreeningDoctorData(validRequest);

		assertEquals(1, result);
		verify(cSDoctorServiceImpl, times(1)).updateCancerDiagnosisDetailsByDoctor(any(CancerDiagnosis.class));
		verify(beneficiaryFlowStatusRepo, times(1)).updateBenFlowAfterTCSpcialistDoneForCanceScreening(anyLong(),
				anyLong(), anyLong());
		verify(tCRequestModelRepo, times(1)).updateStatusIfConsultationCompleted(anyLong(), anyLong(), anyString());
	}

	@Test
	public void testUpdateCancerScreeningDoctorData_InvalidRequest() {
		assertThrows(RuntimeException.class, () -> {
			csServiceImpl.updateCancerScreeningDoctorData(invalidRequest);
		});
	}

	@Test
	public void testUpdateCancerScreeningDoctorData_RequestWithoutDiagnosis() {
		assertThrows(RuntimeException.class, () -> {
			csServiceImpl.updateCancerScreeningDoctorData(requestWithoutDiagnosis);
		});
	}

	@Test
	public void testUpdateCancerScreeningDoctorData_DiagnosisUpdateFailure() throws Exception {
		when(cSDoctorServiceImpl.updateCancerDiagnosisDetailsByDoctor(any(CancerDiagnosis.class))).thenReturn(0);

		assertThrows(RuntimeException.class, () -> {
			csServiceImpl.updateCancerScreeningDoctorData(validRequest);
		});
	}

	@Test
	public void testUpdateCancerScreeningDoctorData_BeneficiaryFlowStatusUpdateFailure() throws Exception {
		when(cSDoctorServiceImpl.updateCancerDiagnosisDetailsByDoctor(any(CancerDiagnosis.class))).thenReturn(1);
		when(beneficiaryFlowStatusRepo.updateBenFlowAfterTCSpcialistDoneForCanceScreening(anyLong(), anyLong(),
				anyLong())).thenReturn(0);

		assertThrows(RuntimeException.class, () -> {
			csServiceImpl.updateCancerScreeningDoctorData(validRequest);
		});
	}
}
