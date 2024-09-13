package com.iemr.hwc.service.tele_consultation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Timestamp;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;
import com.ibm.icu.text.SimpleDateFormat;
import com.iemr.hwc.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.tele_consultation.TCRequestModel;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.data.tele_consultation.TeleconsultationStats;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.tc_consultation.TCRequestModelRepo;
import com.iemr.hwc.repo.tc_consultation.TeleconsultationStatsRepo;
import com.iemr.hwc.service.common.transaction.CommonServiceImpl;

public class TestTeleConsultationServiceImpl {

	@Mock
	private TCRequestModelRepo tCRequestModelRepo;
	@Mock
	private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;
	@Mock
	private CommonServiceImpl commonServiceImpl;
	@Mock
	private RestTemplate restTemplate;
	@Mock
	private SMSGatewayServiceImpl sMSGatewayServiceImpl;
	@Mock
	private TeleconsultationStatsRepo teleconsultationStatsRepo;

	@InjectMocks
	private TeleConsultationServiceImpl teleConsultationServiceImpl;
	private JsonObject validRequestJson;
	private JsonObject requestJson;
	private String requestOBJ;
	private String authorization;
	private TeleConsultationServiceImpl teleConsultationService;
	private ArrayList<BeneficiaryFlowStatus> mockBeneficiaryFlowStatusList;
	private Integer providerServiceMapID;
	private Integer userID;
	private String reqDate;

	private JsonObject invalidRequestJson;
	private BeneficiaryFlowStatus beneficiaryFlowStatus;
	private TCRequestModel tCRequestModel;

	private JsonObject tcRequestOBJ;

	private CommonUtilityClass commonUtilityClass;
	private TeleconsultationRequestOBJ teleconsultationRequestOBJ;

	private Long benRegID;
	private Long visitCode;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateTCRequest() {
		// Arrange
		TCRequestModel tCRequestModel = new TCRequestModel();
		TCRequestModel tCRequestModelRS = new TCRequestModel();
		tCRequestModelRS.settMRequestID(1L);

		when(tCRequestModelRepo.save(tCRequestModel)).thenReturn(tCRequestModelRS);

		// Act
		Long result = teleConsultationServiceImpl.createTCRequest(tCRequestModel);

		// Assert
		assertEquals(1L, result);
	}

	@Test
	public void testCreateTCRequest_ReturnsZero() {
		// Arrange
		TCRequestModel tCRequestModel = new TCRequestModel();
		TCRequestModel tCRequestModelRS = new TCRequestModel();
		tCRequestModelRS.settMRequestID(0L);

		when(tCRequestModelRepo.save(tCRequestModel)).thenReturn(tCRequestModelRS);

		// Act
		Long result = teleConsultationServiceImpl.createTCRequest(tCRequestModel);

		// Assert
		assertEquals(0L, result);
	}

	@Test
	public void testUpdateBeneficiaryArrivalStatus_Success() throws Exception {
		when(beneficiaryFlowStatusRepo.updateBeneficiaryArrivalStatus(anyLong(), anyLong(), anyLong(), anyBoolean(),
				anyString(), anyInt())).thenReturn(1);
		when(tCRequestModelRepo.updateBeneficiaryStatus(anyLong(), anyLong(), anyString(), anyString(), anyInt(),
				eq(false))).thenReturn(1);

		int result = teleConsultationServiceImpl.updateBeneficiaryArrivalStatus(validRequestJson.toString());

		assertEquals(1, result);
		verify(beneficiaryFlowStatusRepo, times(1)).updateBeneficiaryArrivalStatus(anyLong(), anyLong(), anyLong(),
				anyBoolean(), anyString(), anyInt());
		verify(tCRequestModelRepo, times(1)).updateBeneficiaryStatus(anyLong(), anyLong(), anyString(), anyString(),
				anyInt(), eq(false));
	}

	@Test
	public void testUpdateBeneficiaryArrivalStatus_Failure_NoActiveSession() throws Exception {
		when(beneficiaryFlowStatusRepo.updateBeneficiaryArrivalStatus(anyLong(), anyLong(), anyLong(), anyBoolean(),
				anyString(), anyInt())).thenReturn(1);
		when(tCRequestModelRepo.updateBeneficiaryStatus(anyLong(), anyLong(), anyString(), anyString(), anyInt(),
				eq(false))).thenReturn(0);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			teleConsultationServiceImpl.updateBeneficiaryArrivalStatus(validRequestJson.toString());
		});

		assertEquals("Beneficiary arrival status update failed.There is no active Tele-consultation session",
				exception.getMessage());
		verify(beneficiaryFlowStatusRepo, times(1)).updateBeneficiaryArrivalStatus(anyLong(), anyLong(), anyLong(),
				anyBoolean(), anyString(), anyInt());
		verify(tCRequestModelRepo, times(1)).updateBeneficiaryStatus(anyLong(), anyLong(), anyString(), anyString(),
				anyInt(), eq(false));
	}

	@Test
	public void testUpdateBeneficiaryArrivalStatus_Failure_InvalidRequest() {
		JsonObject invalidRequestJson = new JsonObject();
		invalidRequestJson.addProperty("benflowID", 1L);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			teleConsultationServiceImpl.updateBeneficiaryArrivalStatus(invalidRequestJson.toString());
		});

		assertEquals("Invalid request", exception.getMessage());
		verify(beneficiaryFlowStatusRepo, times(0)).updateBeneficiaryArrivalStatus(anyLong(), anyLong(), anyLong(),
				anyBoolean(), anyString(), anyInt());
		verify(tCRequestModelRepo, times(0)).updateBeneficiaryStatus(anyLong(), anyLong(), anyString(), anyString(),
				anyInt(), eq(false));
	}

	@Test
	public void testUpdateBeneficiaryStatusToCancelTCRequest_Success() throws Exception {
		TCRequestModel tCRequestModel = new TCRequestModel();
		tCRequestModel.setRequestDate(new Timestamp(System.currentTimeMillis()));
		tCRequestModel.setSpecializationID(1);

		when(tCRequestModelRepo.getSpecializationID(anyLong(), anyLong(), anyInt())).thenReturn(tCRequestModel);
		when(beneficiaryFlowStatusRepo.updateBeneficiaryStatusToCancelRequest(anyLong(), anyLong(), anyLong(),
				anyString(), anyInt())).thenReturn(1);
		when(tCRequestModelRepo.updateBeneficiaryStatusCancel(anyLong(), anyLong(), anyString(), anyString(), anyInt(),
				anyBoolean())).thenReturn(1);
		when(restTemplate.exchange(anyString(), any(), any(), eq(String.class)))
				.thenReturn(ResponseEntity.ok("{\"statusCode\": 200}"));
		when(sMSGatewayServiceImpl.smsSenderGateway(anyString(), anyLong(), anyInt(), any(), any(), anyString(), any(),
				anyString())).thenReturn(1);

		int result = teleConsultationServiceImpl.updateBeneficiaryStatusToCancelTCRequest(requestOBJ, authorization);

		assertEquals(1, result);
		verify(tCRequestModelRepo, times(1)).getSpecializationID(anyLong(), anyLong(), anyInt());
		verify(beneficiaryFlowStatusRepo, times(1)).updateBeneficiaryStatusToCancelRequest(anyLong(), anyLong(),
				anyLong(), anyString(), anyInt());
		verify(tCRequestModelRepo, times(1)).updateBeneficiaryStatusCancel(anyLong(), anyLong(), anyString(),
				anyString(), anyInt(), anyBoolean());
	}

	@Test
	public void testUpdateBeneficiaryStatusToCancelTCRequest_InvalidRequest() {
		requestJson.remove("benflowID");
		requestOBJ = requestJson.toString();

		Exception exception = assertThrows(RuntimeException.class, () -> {
			teleConsultationServiceImpl.updateBeneficiaryStatusToCancelTCRequest(requestOBJ, authorization);
		});

		assertEquals("Invalid request", exception.getMessage());
	}

	@Test
	public void testUpdateBeneficiaryStatusToCancelTCRequest_NoActiveSession() {
		when(tCRequestModelRepo.getSpecializationID(anyLong(), anyLong(), anyInt())).thenReturn(null);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			teleConsultationServiceImpl.updateBeneficiaryStatusToCancelTCRequest(requestOBJ, authorization);
		});

		assertEquals(
				"Teleconsultation cancel request failed.There is no active Tele-consultation session for this visit",
				exception.getMessage());
	}

	@Test
	public void testCancelSlotForTCCancel_EmptyResultSet() throws Exception {
		// Arrange
		int userID = 1;
		long benRegID = 1L;
		long visitCode = 1L;
		String authorization = "auth-token";
		Set<String> statusSet = new HashSet<>();
		statusSet.add("N");
		statusSet.add("A");
		statusSet.add("O");

		when(tCRequestModelRepo.getTcDetailsList(benRegID, visitCode, userID, statusSet)).thenReturn(new ArrayList<>());

		// Act
		int result = teleConsultationServiceImpl.cancelSlotForTCCancel(userID, benRegID, visitCode, authorization);

		// Assert
		assertEquals(1, result);
	}

	@Test
	public void testCancelSlotForTCCancel_NonEmptyResultSet_SuccessResponse() throws Exception {
		// Arrange
		int userID = 1;
		long benRegID = 1L;
		long visitCode = 1L;
		String authorization = "auth-token";
		Set<String> statusSet = new HashSet<>();
		statusSet.add("N");
		statusSet.add("A");
		statusSet.add("O");

		TCRequestModel tcRequestModel = new TCRequestModel();
		tcRequestModel.setRequestDate(LocalDateTime.now());
		tcRequestModel.setDuration_minute(30L);
		ArrayList<TCRequestModel> resultSetList = new ArrayList<>();
		resultSetList.add(tcRequestModel);

		when(tCRequestModelRepo.getTcDetailsList(benRegID, visitCode, userID, statusSet)).thenReturn(resultSetList);

		ResponseEntity<String> responseEntity = mock(ResponseEntity.class);
		when(responseEntity.getStatusCodeValue()).thenReturn(200);
		when(responseEntity.hasBody()).thenReturn(true);
		when(responseEntity.getBody()).thenReturn("{\"statusCode\": 200}");

		when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
				.thenReturn(responseEntity);

		// Act
		int result = teleConsultationServiceImpl.cancelSlotForTCCancel(userID, benRegID, visitCode, authorization);

		// Assert
		assertEquals(1, result);
	}

	@Test
	public void testCancelSlotForTCCancel_NonEmptyResultSet_FailureResponse() throws Exception {
		// Arrange
		int userID = 1;
		long benRegID = 1L;
		long visitCode = 1L;
		String authorization = "auth-token";
		Set<String> statusSet = new HashSet<>();
		statusSet.add("N");
		statusSet.add("A");
		statusSet.add("O");

		TCRequestModel tcRequestModel = new TCRequestModel();
		tcRequestModel.setRequestDate(LocalDateTime.now());
		tcRequestModel.setDuration_minute(30L);
		ArrayList<TCRequestModel> resultSetList = new ArrayList<>();
		resultSetList.add(tcRequestModel);

		when(tCRequestModelRepo.getTcDetailsList(benRegID, visitCode, userID, statusSet)).thenReturn(resultSetList);

		ResponseEntity<String> responseEntity = mock(ResponseEntity.class);
		when(responseEntity.getStatusCodeValue()).thenReturn(500);

		when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
				.thenReturn(responseEntity);

		// Act
		int result = teleConsultationServiceImpl.cancelSlotForTCCancel(userID, benRegID, visitCode, authorization);

		// Assert
		assertEquals(0, result);
	}

	@Test
	public void testCheckBeneficiaryStatusForSpecialistTransaction_ValidRequest_BeneficiaryArrived_ActiveSession()
			throws Exception {
		ArrayList<BeneficiaryFlowStatus> beneficiaryFlowStatusList = new ArrayList<>();
		beneficiaryFlowStatusList.add(beneficiaryFlowStatus);

		ArrayList<TCRequestModel> tCRequestModelList = new ArrayList<>();
		tCRequestModelList.add(tCRequestModel);

		when(beneficiaryFlowStatusRepo.checkBeneficiaryArrivalStatus(1L, 1L, 1L, 1))
				.thenReturn(beneficiaryFlowStatusList);
		when(tCRequestModelRepo.checkBenTcStatus(1L, 1L, 1)).thenReturn(tCRequestModelList);

		int result = teleConsultationServiceImpl
				.checkBeneficiaryStatusForSpecialistTransaction(validRequestJson.toString());

		assertEquals(1, result);
	}

	@Test
	public void testCheckBeneficiaryStatusForSpecialistTransaction_ValidRequest_BeneficiaryNotArrived() {
		beneficiaryFlowStatus.setBenArrivedFlag(false);
		ArrayList<BeneficiaryFlowStatus> beneficiaryFlowStatusList = new ArrayList<>();
		beneficiaryFlowStatusList.add(beneficiaryFlowStatus);

		when(beneficiaryFlowStatusRepo.checkBeneficiaryArrivalStatus(1L, 1L, 1L, 1))
				.thenReturn(beneficiaryFlowStatusList);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			teleConsultationServiceImpl.checkBeneficiaryStatusForSpecialistTransaction(validRequestJson.toString());
		});

		assertEquals("Sorry, Beneficiary has not arrived at TM spoke/center", exception.getMessage());
	}

	@Test
	public void testCheckBeneficiaryStatusForSpecialistTransaction_ValidRequest_NoActiveSession() {
		ArrayList<BeneficiaryFlowStatus> beneficiaryFlowStatusList = new ArrayList<>();
		beneficiaryFlowStatusList.add(beneficiaryFlowStatus);

		when(beneficiaryFlowStatusRepo.checkBeneficiaryArrivalStatus(1L, 1L, 1L, 1))
				.thenReturn(beneficiaryFlowStatusList);
		when(tCRequestModelRepo.checkBenTcStatus(1L, 1L, 1)).thenReturn(new ArrayList<>());

		Exception exception = assertThrows(RuntimeException.class, () -> {
			teleConsultationServiceImpl.checkBeneficiaryStatusForSpecialistTransaction(validRequestJson.toString());
		});

		assertEquals("Sorry, Beneficiary has not any active Teleconsultation session.", exception.getMessage());
	}

	@Test
	public void testCheckBeneficiaryStatusForSpecialistTransaction_InvalidRequest() {
		Exception exception = assertThrows(RuntimeException.class, () -> {
			teleConsultationServiceImpl.checkBeneficiaryStatusForSpecialistTransaction(invalidRequestJson.toString());
		});

		assertEquals("Invalid request.", exception.getMessage());
	}

	@Test
	public void testCreateTCRequestFromWorkList_Success() throws Exception {
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
				.thenReturn(teleconsultationRequestOBJ);
		when(beneficiaryFlowStatusRepo.updateFlagAfterTcRequestCreatedFromWorklist(anyLong(), anyLong(), anyLong(),
				anyInt(), any())).thenReturn(1);
		when(sMSGatewayServiceImpl.smsSenderGateway(anyString(), anyLong(), anyInt(), any(), any(), anyString(), any(),
				any(), eq(authorization))).thenReturn(1);

		int result = teleConsultationService.createTCRequestFromWorkList(tcRequestOBJ, authorization);

		assertEquals(1, result);
		verify(commonServiceImpl, times(1)).createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class),
				eq(authorization));
		verify(beneficiaryFlowStatusRepo, times(1)).updateFlagAfterTcRequestCreatedFromWorklist(anyLong(), anyLong(),
				anyLong(), anyInt(), any());
		verify(sMSGatewayServiceImpl, times(1)).smsSenderGateway(anyString(), anyLong(), anyInt(), any(), any(),
				anyString(), any(), any(), eq(authorization));
	}

	@Test
	public void testCreateTCRequestFromWorkList_InvalidRequest() {
		JsonObject invalidRequestOBJ = new JsonObject();

		Exception exception = assertThrows(RuntimeException.class, () -> {
			teleConsultationService.createTCRequestFromWorkList(invalidRequestOBJ, authorization);
		});

		assertEquals("Error while creating TC request. Kindly contact your administrator", exception.getMessage());
	}

	@Test
	public void testCreateTCRequestFromWorkList_Exception() throws Exception {
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
				.thenThrow(new RuntimeException("Service exception"));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			teleConsultationService.createTCRequestFromWorkList(tcRequestOBJ, authorization);
		});

		assertEquals("Error while creating TC request. Kindly contact your administrator", exception.getMessage());
	}

	@Test
	public void testGetTCRequestListBySpecialistIdAndDate() throws Exception {
		LocalDate datePart = LocalDate.parse(reqDate.split("T")[0]);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Date d = new SimpleDateFormat("yyyy-MM-dd").parse(datePart.format(format));
		Timestamp t = new Timestamp(d.getTime());

		when(beneficiaryFlowStatusRepo.getTCRequestList(providerServiceMapID, userID, t))
				.thenReturn(mockBeneficiaryFlowStatusList);

		String result = teleConsultationServiceImpl.getTCRequestListBySpecialistIdAndDate(providerServiceMapID, userID,
				reqDate);

		assertNotNull(result);
		assertTrue(result.contains("\"beneficiaryRegID\":1"));
		verify(beneficiaryFlowStatusRepo, times(1)).getTCRequestList(providerServiceMapID, userID, t);
	}

	@Test
	public void testGetTCRequestListBySpecialistIdAndDate_EmptyResult() throws Exception {
		LocalDate datePart = LocalDate.parse(reqDate.split("T")[0]);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Date d = new SimpleDateFormat("yyyy-MM-dd").parse(datePart.format(format));
		Timestamp t = new Timestamp(d.getTime());

		when(beneficiaryFlowStatusRepo.getTCRequestList(providerServiceMapID, userID, t)).thenReturn(new ArrayList<>());

		String result = teleConsultationServiceImpl.getTCRequestListBySpecialistIdAndDate(providerServiceMapID, userID,
				reqDate);

		assertNotNull(result);
		assertEquals("[]", result);
		verify(beneficiaryFlowStatusRepo, times(1)).getTCRequestList(providerServiceMapID, userID, t);
	}

	@Test
	public void testGetTCRequestListBySpecialistIdAndDate_InvalidDate() {
		String invalidDate = "invalid-date";

		Exception exception = assertThrows(Exception.class, () -> {
			teleConsultationServiceImpl.getTCRequestListBySpecialistIdAndDate(providerServiceMapID, userID,
					invalidDate);
		});

		String expectedMessage = "Text 'invalid-date' could not be parsed";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void testStartConsultation() {
		TeleconsultationStats teleconsultationStats = new TeleconsultationStats();
		teleconsultationStats.setBeneficiaryRegID(benRegID);
		teleconsultationStats.setVisitCode(visitCode);
		teleconsultationStats.setStartTime(new Timestamp(System.currentTimeMillis()));
		teleconsultationStats.setCreatedBy("Report");

		when(tCRequestModelRepo.updateStartConsultationTime(benRegID, visitCode)).thenReturn(1);

		Integer result = teleConsultationServiceImpl.startconsultation(benRegID, visitCode);

		assertEquals(1, result);
		verify(teleconsultationStatsRepo, times(1)).save(any(TeleconsultationStats.class));
		verify(tCRequestModelRepo, times(1)).updateStartConsultationTime(benRegID, visitCode);
	}
}
