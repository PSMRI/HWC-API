package com.iemr.hwc.service.patientApp.master;

import com.google.gson.Gson;
import com.iemr.hwc.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.hwc.data.covid19.Covid19BenFeedback;
import com.iemr.hwc.data.doctor.ChiefComplaintMaster;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.doctor.ChiefComplaintMasterRepo;
import com.iemr.hwc.repo.masterrepo.covid19.CovidContactHistoryMasterRepo;
import com.iemr.hwc.repo.masterrepo.covid19.CovidRecommnedationMasterRepo;
import com.iemr.hwc.repo.masterrepo.covid19.CovidSymptomsMasterRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.quickBlox.QuickBloxRepo;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonServiceImpl;
import com.iemr.hwc.service.covid19.Covid19ServiceImpl;
import com.iemr.hwc.service.generalOPD.GeneralOPDDoctorServiceImpl;
import com.iemr.hwc.utils.mapper.InputMapper;

import io.lettuce.core.dynamic.annotation.Value;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class TestCommonPatientAppMasterServiceImpl {

	@ExtendWith(MockitoExtension.class)
	public class CommonPatientAppMasterServiceImplTest {

		@Mock
		private ChiefComplaintMasterRepo chiefComplaintMasterRepo;
		@Mock
		private CovidSymptomsMasterRepo covidSymptomsMasterRepo;

		@Mock
		private CovidContactHistoryMasterRepo covidContactHistoryMasterRepo;

		@Mock
		private CovidRecommnedationMasterRepo covidRecommnedationMasterRepo;
		@Mock
		private CommonNurseServiceImpl commonNurseServiceImpl;

		@Mock
		private Covid19ServiceImpl covid19ServiceImpl;

		@Mock
		private BenVisitDetailRepo benVisitDetailRepo;
		@Mock
		private CommonServiceImpl commonServiceImpl;
		@Mock
		private QuickBloxRepo quickBloxRepo;

		@Mock
		private GeneralOPDDoctorServiceImpl generalOPDDoctorServiceImpl;
		@Mock
		private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;

		@InjectMocks
		private CommonPatientAppMasterServiceImpl commonPatientAppMasterService;

		private ArrayList<Object[]> chiefComplaintList;
		private CommonPatientAppMasterServiceImpl commonPatientAppMasterServiceImpl;

		private Gson gson;
		private String requestObj;
		private CommonUtilityClass nurseUtilityClass;
		private BeneficiaryVisitDetail benDetailsOBJ;
		private Covid19BenFeedback covid19BenFeedbackOBJ;
		private BeneficiaryVisitDetail beneficiaryVisitDetail;
		private CommonUtilityClass commonUtilityClass;
		private TeleconsultationRequestOBJ teleconsultationRequestOBJ;
		private String validRequestObj;
		private String invalidRequestObj;
		private CommonUtilityClass validCommonUtilityClass;
		private CommonUtilityClass invalidCommonUtilityClass;

		@Value("${servicePointID}")
		private Integer servicePointID = 1;
		@Value("${parkingPlaceID}")
		private Integer parkingPlaceID = 2;
		@Value("${providerServiceMapID}")
		private Integer providerServiceMapID = 3;
		@Value("${vanID}")
		private Integer vanID = 4;
		@Value("${serviceID}")
		private Integer serviceID = 5;
		@Value("${providerID}")
		private Integer providerID = 6;
		@Value("${appId}")
		private Integer appId = 7;
		@Value("${authKey}")
		private String authKey = "authKey";
		@Value("${authSecret}")
		private String authSecret = "authSecret";
		@Value("${scheduling-slotsize}")
		private Integer schedulingSlotSize = 30;

		@BeforeEach
		public void setUp() {
			chiefComplaintList = new ArrayList<>();
			chiefComplaintList.add(new Object[] { 1, "Headache" });
			chiefComplaintList.add(new Object[] { 2, "Fever" });
		}

		@Test
		public void testGetChiefComplaintsMaster() {
			// Arrange
			when(chiefComplaintMasterRepo.getChiefComplaintMaster()).thenReturn(chiefComplaintList);

			// Act
			String result = commonPatientAppMasterService.getChiefComplaintsMaster(1, 1, "Male");

			// Assert
			Map<String, Object> expectedMap = new HashMap<>();
			expectedMap.put("chiefComplaintMaster", ChiefComplaintMaster.getChiefComplaintMasters(chiefComplaintList));
			String expectedJson = new Gson().toJson(expectedMap);

			assertEquals(expectedJson, result);
		}
	}

	@Test
	public void testGetCovidMaster_Success() {
		when(covidSymptomsMasterRepo.findByDeleted(false)).thenReturn(Collections.singletonList("Symptom1"));
		when(covidContactHistoryMasterRepo.findByDeleted(false))
				.thenReturn(Collections.singletonList("ContactHistory1"));
		when(covidRecommnedationMasterRepo.findByDeleted(false))
				.thenReturn(Collections.singletonList("Recommendation1"));

		String expectedJson = gson.toJson(new CovidMasterResponse(Collections.singletonList("Symptom1"),
				Collections.singletonList("ContactHistory1"), Collections.singletonList("Recommendation1")));

		String actualJson = commonPatientAppMasterServiceImpl.getCovidMaster(1, 1, "M");

		assertEquals(expectedJson, actualJson);
	}

	@Test
	public void testGetCovidMaster_EmptyData() {
		when(covidSymptomsMasterRepo.findByDeleted(false)).thenReturn(Collections.emptyList());
		when(covidContactHistoryMasterRepo.findByDeleted(false)).thenReturn(Collections.emptyList());
		when(covidRecommnedationMasterRepo.findByDeleted(false)).thenReturn(Collections.emptyList());

		String expectedJson = gson.toJson(
				new CovidMasterResponse(Collections.emptyList(), Collections.emptyList(), Collections.emptyList()));

		String actualJson = commonPatientAppMasterServiceImpl.getCovidMaster(1, 1, "M");

		assertEquals(expectedJson, actualJson);
	}

	@Test
	public void testGetCovidMaster_Exception() {
		when(covidSymptomsMasterRepo.findByDeleted(false)).thenThrow(new RuntimeException("Database error"));
		when(covidContactHistoryMasterRepo.findByDeleted(false))
				.thenReturn(Collections.singletonList("ContactHistory1"));
		when(covidRecommnedationMasterRepo.findByDeleted(false))
				.thenReturn(Collections.singletonList("Recommendation1"));

		try {
			commonPatientAppMasterServiceImpl.getCovidMaster(1, 1, "M");
		} catch (RuntimeException e) {
			assertEquals("Database error", e.getMessage());
		}
	}

	private static class CovidMasterResponse {
		private final Object covidSymptomsMaster;
		private final Object covidContactHistoryMaster;
		private final Object covidRecommendationMaster;

		public CovidMasterResponse(Object covidSymptomsMaster, Object covidContactHistoryMaster,
				Object covidRecommendationMaster) {
			this.covidSymptomsMaster = covidSymptomsMaster;
			this.covidContactHistoryMaster = covidContactHistoryMaster;
			this.covidRecommendationMaster = covidRecommendationMaster;
		}
	}

	@Test
	public void testGetMaster() {
		String result = commonPatientAppMasterServiceImpl.getMaster(1);

		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("servicePointID", servicePointID);
		expectedMap.put("parkingPlaceID", parkingPlaceID);
		expectedMap.put("vanID", vanID);
		expectedMap.put("providerServiceMapID", providerServiceMapID);
		expectedMap.put("serviceID", serviceID);
		expectedMap.put("providerID", providerID);
		expectedMap.put("appId", appId);
		expectedMap.put("authKey", authKey);
		expectedMap.put("authSecret", authSecret);
		expectedMap.put("schedulingSlotSize", schedulingSlotSize);

		String expectedJson = new Gson().toJson(expectedMap);

		assertTrue(result.contains(expectedJson));
	}

	@Test
	public void testSaveCovidScreeningData_Success() throws Exception {
		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class))).thenReturn(1L);
		when(commonNurseServiceImpl.generateVisitCode(anyLong(), anyInt(), anyInt())).thenReturn(1L);
		when(covid19ServiceImpl.saveCovidDetails(any(Covid19BenFeedback.class))).thenReturn(1);

		String response = commonPatientAppMasterServiceImpl.saveCovidScreeningData(requestObj);

		assertNotNull(response);
		assertTrue(response.contains("visitCode"));
		verify(commonNurseServiceImpl, times(1)).saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class));
		verify(commonNurseServiceImpl, times(1)).generateVisitCode(anyLong(), anyInt(), anyInt());
		verify(covid19ServiceImpl, times(1)).saveCovidDetails(any(Covid19BenFeedback.class));
	}

	@Test
	public void testSaveCovidScreeningData_MissingBeneficiaryDetails() {
		nurseUtilityClass.setBeneficiaryRegID(null);
		requestObj = new Gson().toJson(nurseUtilityClass);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			commonPatientAppMasterServiceImpl.saveCovidScreeningData(requestObj);
		});

		assertEquals("Error in episode creation. Missing Beneficiary registration details", exception.getMessage());
	}

	@Test
	public void testSaveCovidScreeningData_ErrorInSavingCovidDetails() throws Exception {
		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class))).thenReturn(1L);
		when(commonNurseServiceImpl.generateVisitCode(anyLong(), anyInt(), anyInt())).thenReturn(1L);
		when(covid19ServiceImpl.saveCovidDetails(any(Covid19BenFeedback.class))).thenReturn(null);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			commonPatientAppMasterServiceImpl.saveCovidScreeningData(requestObj);
		});

		assertEquals("error in saving covid screening details", exception.getMessage());
	}

	@Test
	public void testSavechiefComplaintsData() throws Exception {
		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class))).thenReturn(1L);
		when(commonNurseServiceImpl.generateVisitCode(any(Long.class), any(Integer.class), any(Integer.class)))
				.thenReturn(1L);

		String response = commonPatientAppMasterServiceImpl.savechiefComplaintsData(requestObj);

		assertNotNull(response);
		verify(commonNurseServiceImpl, times(1)).saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class));
		verify(commonNurseServiceImpl, times(1)).generateVisitCode(any(Long.class), any(Integer.class),
				any(Integer.class));
	}

	@Test
	public void testSavechiefComplaintsDataWithCovidFlowDone() throws Exception {
		nurseUtilityClass.setIsCovidFlowDone(true);
		nurseUtilityClass.setVisitCode(1L);

		String response = commonPatientAppMasterServiceImpl.savechiefComplaintsData(requestObj);

		assertNotNull(response);
		verify(commonNurseServiceImpl, never()).saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class));
		verify(commonNurseServiceImpl, never()).generateVisitCode(any(Long.class), any(Integer.class),
				any(Integer.class));
	}

	@Test
	public void testSavechiefComplaintsDataWithMissingDetails() {
		nurseUtilityClass.setBeneficiaryRegID(null);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			commonPatientAppMasterServiceImpl.savechiefComplaintsData(requestObj);
		});

		assertEquals(" Error in saving chief complaints", exception.getMessage());
	}

	public void testCreataAndUpdateBeneficairyFlowStatus_Success() throws Exception {
		BeneficiaryFlowStatus beneficiaryFlowStatus = new BeneficiaryFlowStatus();
		beneficiaryFlowStatus.setBenFlowID(1L);

		when(beneficiaryFlowStatusRepo.save(any(BeneficiaryFlowStatus.class))).thenReturn(beneficiaryFlowStatus);

		Integer result = commonPatientAppMasterServiceImpl.creataAndUpdateBeneficairyFlowStatus(
				teleconsultationRequestOBJ, beneficiaryVisitDetail, commonUtilityClass);

		assertEquals(1, result);
		verify(beneficiaryFlowStatusRepo, times(1)).save(any(BeneficiaryFlowStatus.class));
	}

	@Test
	public void testCreataAndUpdateBeneficairyFlowStatus_Failure() throws Exception {
		when(beneficiaryFlowStatusRepo.save(any(BeneficiaryFlowStatus.class))).thenReturn(null);

		Integer result = commonPatientAppMasterServiceImpl.creataAndUpdateBeneficairyFlowStatus(
				teleconsultationRequestOBJ, beneficiaryVisitDetail, commonUtilityClass);

		assertEquals(0, result);
		verify(beneficiaryFlowStatusRepo, times(1)).save(any(BeneficiaryFlowStatus.class));
	}

	@Test
	public void testGetPatientEpisodeData() throws Exception {
		String benVisitDetails = "{\"key\":\"value\"}";
		String chiefComplaints = "[{\"key\":\"value\"}]";

		when(covid19ServiceImpl.getBenVisitDetailsFrmNurseCovid19(1L, 1L)).thenReturn(benVisitDetails);
		when(commonNurseServiceImpl.getBenChiefComplaints(1L, 1L)).thenReturn(chiefComplaints);

		String response = commonPatientAppMasterServiceImpl.getPatientEpisodeData(requestObj);

		JsonObject responseJson = JsonParser.parseString(response).getAsJsonObject();
		assertTrue(responseJson.has("covidDetails"));
		assertTrue(responseJson.has("chiefComplaints"));

		verify(covid19ServiceImpl, times(1)).getBenVisitDetailsFrmNurseCovid19(1L, 1L);
		verify(commonNurseServiceImpl, times(1)).getBenChiefComplaints(1L, 1L);
	}

	@Test
	public void testGetPatientEpisodeData_InvalidRequest() {
		nurseUtilityClass.setVisitCode(null);
		requestObj = InputMapper.gson().toJson(nurseUtilityClass);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			commonPatientAppMasterServiceImpl.getPatientEpisodeData(requestObj);
		});

		String expectedMessage = "Error in getting Beneficiary Details";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void testGetPatientBookedSlots_WithActiveSlots() throws Exception {
		ArrayList<BeneficiaryFlowStatus> objList = new ArrayList<>();
		BeneficiaryFlowStatus status = new BeneficiaryFlowStatus();
		status.setBeneficiaryRegID(1L);
		status.setBenName("John Doe");
		status.setAge("30");
		status.setGenderName("Male");
		status.settCRequestDate(new java.sql.Timestamp(System.currentTimeMillis()));
		objList.add(status);

		when(beneficiaryFlowStatusRepo.getBenSlotDetails("testUser")).thenReturn(objList);
		when(quickBloxRepo.getQuickbloxIds(anyLong())).thenReturn(new Quickblox());

		String response = commonPatientAppMasterServiceImpl.getPatientBookedSlots(requestObj);

		JsonObject responseJson = JsonParser.parseString(response).getAsJsonObject();
		assertTrue(responseJson.get("isAnyActiveSlot").getAsBoolean());
		assertTrue(responseJson.get("isAnyActiveSlotForSameBen").getAsBoolean());
		assertNotNull(responseJson.get("patientDetails"));
	}

	@Test
	public void testGetPatientBookedSlots_WithoutActiveSlots() throws Exception {
		ArrayList<BeneficiaryFlowStatus> objList = new ArrayList<>();

		when(beneficiaryFlowStatusRepo.getBenSlotDetails("testUser")).thenReturn(objList);

		String response = commonPatientAppMasterServiceImpl.getPatientBookedSlots(requestObj);

		JsonObject responseJson = JsonParser.parseString(response).getAsJsonObject();
		assertFalse(responseJson.get("isAnyActiveSlot").getAsBoolean());
	}

	@Test
	public void testGetPatientBookedSlots_InvalidRequest() {
		nurseUtilityClass.setBeneficiaryRegID(null);
		requestObj = new Gson().toJson(nurseUtilityClass);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			commonPatientAppMasterServiceImpl.getPatientBookedSlots(requestObj);
		});

		String expectedMessage = "invalid request. beneficiary details are missing";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void testSaveSpecialistDiagnosisData_Success() throws Exception {
		when(commonNurseServiceImpl.saveBenPrescription(any(PrescriptionDetail.class))).thenReturn(1L);
		when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterSpecialistMobileAPP(anyLong(), anyLong(), anyShort()))
				.thenReturn(1);

		Long response = commonPatientAppMasterServiceImpl.saveSpecialistDiagnosisData(requestObj);

		assertNotNull(response);
		assertEquals(1L, response);
		verify(commonNurseServiceImpl, times(1)).saveBenPrescription(any(PrescriptionDetail.class));
		verify(beneficiaryFlowStatusRepo, times(1)).updateBenFlowStatusAfterSpecialistMobileAPP(anyLong(), anyLong(),
				anyShort());
	}

	@Test
	public void testSaveSpecialistDiagnosisData_MissingBeneficiaryDetails() {
		nurseUtilityClass.setBeneficiaryRegID(null);
		requestObj = new Gson().toJson(nurseUtilityClass);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			commonPatientAppMasterServiceImpl.saveSpecialistDiagnosisData(requestObj);
		});

		assertEquals("invalid request. beneficiary details are missing", exception.getMessage());
	}

	@Test
	public void testSaveSpecialistDiagnosisData_SavePrescriptionError() throws Exception {
		when(commonNurseServiceImpl.saveBenPrescription(any(PrescriptionDetail.class))).thenReturn(null);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			commonPatientAppMasterServiceImpl.saveSpecialistDiagnosisData(requestObj);
		});

		assertEquals("error in saving diagnosis data", exception.getMessage());
	}

	@Test
	public void testSaveSpecialistDiagnosisData_UpdateFlowStatusError() throws Exception {
		when(commonNurseServiceImpl.saveBenPrescription(any(PrescriptionDetail.class))).thenReturn(1L);
		when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterSpecialistMobileAPP(anyLong(), anyLong(), anyShort()))
				.thenReturn(0);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			commonPatientAppMasterServiceImpl.saveSpecialistDiagnosisData(requestObj);
		});

		assertEquals("error in saving diagnosis data - flow status change", exception.getMessage());
	}

	@Test
	public void testGetSpecialistDiagnosisData_ValidInput() throws Exception {
		when(InputMapper.gson().fromJson(validRequestObj, CommonUtilityClass.class))
				.thenReturn(validCommonUtilityClass);
		when(generalOPDDoctorServiceImpl.getGeneralOPDDiagnosisDetails(1L, 1L)).thenReturn("Diagnosis Data");

		String response = commonPatientAppMasterServiceImpl.getSpecialistDiagnosisData(validRequestObj);

		assertEquals("Diagnosis Data", response);
	}

	@Test
	public void testGetSpecialistDiagnosisData_InvalidInput() {
		when(InputMapper.gson().fromJson(invalidRequestObj, CommonUtilityClass.class))
				.thenReturn(invalidCommonUtilityClass);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			commonPatientAppMasterServiceImpl.getSpecialistDiagnosisData(invalidRequestObj);
		});

		assertEquals("invalid request. beneficiary details are missing", exception.getMessage());
	}

	@Test
	public void testGetSpecialistDiagnosisData_NoDiagnosisData() throws Exception {
		when(InputMapper.gson().fromJson(validRequestObj, CommonUtilityClass.class))
				.thenReturn(validCommonUtilityClass);
		when(generalOPDDoctorServiceImpl.getGeneralOPDDiagnosisDetails(1L, 1L)).thenReturn(null);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			commonPatientAppMasterServiceImpl.getSpecialistDiagnosisData(validRequestObj);
		});

		assertEquals("error in getting diagnosis data", exception.getMessage());
	}

	@Test
	public void testGetPatientsLast_3_Episode_ValidRequest() throws Exception {
		ArrayList<BeneficiaryFlowStatus> episodes = new ArrayList<>();
		episodes.add(new BeneficiaryFlowStatus());
		when(beneficiaryFlowStatusRepo.getPatientLat_3_Episode(1L)).thenReturn(episodes);

		String response = commonPatientAppMasterServiceImpl.getPatientsLast_3_Episode(requestObj);

		assertNotNull(response);
		assertTrue(response.contains("BeneficiaryFlowStatus"));
		verify(beneficiaryFlowStatusRepo, times(1)).getPatientLat_3_Episode(1L);
	}

	@Test
	public void testGetPatientsLast_3_Episode_InvalidRequest() {
		nurseUtilityClass.setBeneficiaryRegID(null);
		requestObj = new Gson().toJson(nurseUtilityClass);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			commonPatientAppMasterServiceImpl.getPatientsLast_3_Episode(requestObj);
		});

		assertEquals("invalid request. beneficiary details is/are missing", exception.getMessage());
	}

	@Test
	public void testGetPatientsLast_3_Episode_NoEpisodesFound() throws Exception {
		when(beneficiaryFlowStatusRepo.getPatientLat_3_Episode(1L)).thenReturn(new ArrayList<>());

		Exception exception = assertThrows(RuntimeException.class, () -> {
			commonPatientAppMasterServiceImpl.getPatientsLast_3_Episode(requestObj);
		});

		assertEquals("error in getting patient last 3 episode data data", exception.getMessage());
		verify(beneficiaryFlowStatusRepo, times(1)).getPatientLat_3_Episode(1L);
	}
}
