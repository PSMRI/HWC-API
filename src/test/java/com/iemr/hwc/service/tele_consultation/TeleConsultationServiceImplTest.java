package com.iemr.hwc.service.tele_consultation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.tele_consultation.TCRequestModel;
import com.iemr.hwc.data.tele_consultation.TcSpecialistSlotBookingRequestOBJ;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.data.tele_consultation.TeleconsultationStats;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.tc_consultation.TCRequestModelRepo;
import com.iemr.hwc.repo.tc_consultation.TeleconsultationStatsRepo;
import com.iemr.hwc.service.common.transaction.CommonServiceImpl;
import com.iemr.hwc.utils.mapper.OutputMapper;

@ExtendWith(MockitoExtension.class)
class TeleConsultationServiceImplTest {
	@Mock
	private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;

	@Mock
	private CommonServiceImpl commonServiceImpl;

	@Mock
	private SMSGatewayServiceImpl sMSGatewayServiceImpl;

	@Mock
	private TCRequestModelRepo tCRequestModelRepo;

	@InjectMocks
	private TeleConsultationServiceImpl teleConsultationServiceImpl;

	@Mock
	private TeleconsultationStatsRepo teleconsultationStatsRepo;

	@Test
	void testCreateTCRequest() {
		// Arrange
		TCRequestModel tcRequestModel = new TCRequestModel();
		tcRequestModel.setBenVisitID(1L);
		tcRequestModel.setBeneficiaryArrivalTime(mock(Timestamp.class));
		tcRequestModel.setBeneficiaryRegID(1L);
		tcRequestModel.setConsultationStats("Consultation Stats");
		tcRequestModel.setConsultation_FirstStart(mock(Timestamp.class));
		tcRequestModel.setConsultation_LastEnd(mock(Timestamp.class));
		tcRequestModel.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		tcRequestModel.setCreatedDate(mock(Timestamp.class));
		tcRequestModel.setDeleted(true);
		tcRequestModel.setDuration_minute(1L);
		tcRequestModel.setIsReferredByDoctor(true);
		tcRequestModel.setLastModDate(mock(Timestamp.class));
		tcRequestModel.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		tcRequestModel.setProcessed("Processed");
		tcRequestModel.setProviderServiceMapID(1);
		tcRequestModel.setRequestDate(mock(Timestamp.class));
		tcRequestModel.setSpecializationID(1);
		tcRequestModel.setStatus("Status");
		tcRequestModel.setUserID(1);
		tcRequestModel.setVanID(1);
		tcRequestModel.setVisitCode(1L);
		tcRequestModel.settMRequestID(1L);
		when(tCRequestModelRepo.save(Mockito.<TCRequestModel>any())).thenReturn(tcRequestModel);

		TCRequestModel tCRequestModel = new TCRequestModel();
		tCRequestModel.setBenVisitID(1L);
		tCRequestModel.setBeneficiaryArrivalTime(mock(Timestamp.class));
		tCRequestModel.setBeneficiaryRegID(1L);
		tCRequestModel.setConsultationStats("Consultation Stats");
		tCRequestModel.setConsultation_FirstStart(mock(Timestamp.class));
		tCRequestModel.setConsultation_LastEnd(mock(Timestamp.class));
		tCRequestModel.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		tCRequestModel.setCreatedDate(mock(Timestamp.class));
		tCRequestModel.setDeleted(true);
		tCRequestModel.setDuration_minute(1L);
		tCRequestModel.setIsReferredByDoctor(true);
		tCRequestModel.setLastModDate(mock(Timestamp.class));
		tCRequestModel.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		tCRequestModel.setProcessed("Processed");
		tCRequestModel.setProviderServiceMapID(1);
		tCRequestModel.setRequestDate(mock(Timestamp.class));
		tCRequestModel.setSpecializationID(1);
		tCRequestModel.setStatus("Status");
		tCRequestModel.setUserID(1);
		tCRequestModel.setVanID(1);
		tCRequestModel.setVisitCode(1L);
		tCRequestModel.settMRequestID(1L);
		Long actualCreateTCRequestResult = teleConsultationServiceImpl.createTCRequest(tCRequestModel);
		verify(tCRequestModelRepo).save(isA(TCRequestModel.class));
		assertEquals(1L, actualCreateTCRequestResult.longValue());
	}

	@Test
	void testCreateTCRequest2() {
		// Arrange
		TCRequestModel tcRequestModel = mock(TCRequestModel.class);
		when(tcRequestModel.gettMRequestID()).thenReturn(1L);
		doNothing().when(tcRequestModel).setBenVisitID(Mockito.<Long>any());
		doNothing().when(tcRequestModel).setBeneficiaryArrivalTime(Mockito.<Timestamp>any());
		doNothing().when(tcRequestModel).setBeneficiaryRegID(Mockito.<Long>any());
		doNothing().when(tcRequestModel).setConsultationStats(Mockito.<String>any());
		doNothing().when(tcRequestModel).setConsultation_FirstStart(Mockito.<Timestamp>any());
		doNothing().when(tcRequestModel).setConsultation_LastEnd(Mockito.<Timestamp>any());
		doNothing().when(tcRequestModel).setCreatedBy(Mockito.<String>any());
		doNothing().when(tcRequestModel).setCreatedDate(Mockito.<Timestamp>any());
		doNothing().when(tcRequestModel).setDeleted(Mockito.<Boolean>any());
		doNothing().when(tcRequestModel).setDuration_minute(Mockito.<Long>any());
		doNothing().when(tcRequestModel).setIsReferredByDoctor(Mockito.<Boolean>any());
		doNothing().when(tcRequestModel).setLastModDate(Mockito.<Timestamp>any());
		doNothing().when(tcRequestModel).setModifiedBy(Mockito.<String>any());
		doNothing().when(tcRequestModel).setProcessed(Mockito.<String>any());
		doNothing().when(tcRequestModel).setProviderServiceMapID(Mockito.<Integer>any());
		doNothing().when(tcRequestModel).setRequestDate(Mockito.<Timestamp>any());
		doNothing().when(tcRequestModel).setSpecializationID(Mockito.<Integer>any());
		doNothing().when(tcRequestModel).setStatus(Mockito.<String>any());
		doNothing().when(tcRequestModel).setUserID(Mockito.<Integer>any());
		doNothing().when(tcRequestModel).setVanID(Mockito.<Integer>any());
		doNothing().when(tcRequestModel).setVisitCode(Mockito.<Long>any());
		doNothing().when(tcRequestModel).settMRequestID(Mockito.<Long>any());
		tcRequestModel.setBenVisitID(1L);
		tcRequestModel.setBeneficiaryArrivalTime(mock(Timestamp.class));
		tcRequestModel.setBeneficiaryRegID(1L);
		tcRequestModel.setConsultationStats("Consultation Stats");
		tcRequestModel.setConsultation_FirstStart(mock(Timestamp.class));
		tcRequestModel.setConsultation_LastEnd(mock(Timestamp.class));
		tcRequestModel.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		tcRequestModel.setCreatedDate(mock(Timestamp.class));
		tcRequestModel.setDeleted(true);
		tcRequestModel.setDuration_minute(1L);
		tcRequestModel.setIsReferredByDoctor(true);
		tcRequestModel.setLastModDate(mock(Timestamp.class));
		tcRequestModel.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		tcRequestModel.setProcessed("Processed");
		tcRequestModel.setProviderServiceMapID(1);
		tcRequestModel.setRequestDate(mock(Timestamp.class));
		tcRequestModel.setSpecializationID(1);
		tcRequestModel.setStatus("Status");
		tcRequestModel.setUserID(1);
		tcRequestModel.setVanID(1);
		tcRequestModel.setVisitCode(1L);
		tcRequestModel.settMRequestID(1L);
		when(tCRequestModelRepo.save(Mockito.<TCRequestModel>any())).thenReturn(tcRequestModel);

		TCRequestModel tCRequestModel = new TCRequestModel();
		tCRequestModel.setBenVisitID(1L);
		tCRequestModel.setBeneficiaryArrivalTime(mock(Timestamp.class));
		tCRequestModel.setBeneficiaryRegID(1L);
		tCRequestModel.setConsultationStats("Consultation Stats");
		tCRequestModel.setConsultation_FirstStart(mock(Timestamp.class));
		tCRequestModel.setConsultation_LastEnd(mock(Timestamp.class));
		tCRequestModel.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		tCRequestModel.setCreatedDate(mock(Timestamp.class));
		tCRequestModel.setDeleted(true);
		tCRequestModel.setDuration_minute(1L);
		tCRequestModel.setIsReferredByDoctor(true);
		tCRequestModel.setLastModDate(mock(Timestamp.class));
		tCRequestModel.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		tCRequestModel.setProcessed("Processed");
		tCRequestModel.setProviderServiceMapID(1);
		tCRequestModel.setRequestDate(mock(Timestamp.class));
		tCRequestModel.setSpecializationID(1);
		tCRequestModel.setStatus("Status");
		tCRequestModel.setUserID(1);
		tCRequestModel.setVanID(1);
		tCRequestModel.setVisitCode(1L);
		tCRequestModel.settMRequestID(1L);

		// Act
		Long actualCreateTCRequestResult = teleConsultationServiceImpl.createTCRequest(tCRequestModel);

		// Assert
		verify(tcRequestModel, atLeast(1)).gettMRequestID();
		verify(tcRequestModel).setBenVisitID(isA(Long.class));
		verify(tcRequestModel).setBeneficiaryArrivalTime(isA(Timestamp.class));
		verify(tcRequestModel).setBeneficiaryRegID(isA(Long.class));
		verify(tcRequestModel).setConsultationStats(eq("Consultation Stats"));
		verify(tcRequestModel).setConsultation_FirstStart(isA(Timestamp.class));
		verify(tcRequestModel).setConsultation_LastEnd(isA(Timestamp.class));
		verify(tcRequestModel).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
		verify(tcRequestModel).setCreatedDate(isA(Timestamp.class));
		verify(tcRequestModel).setDeleted(isA(Boolean.class));
		verify(tcRequestModel).setDuration_minute(isA(Long.class));
		verify(tcRequestModel).setIsReferredByDoctor(isA(Boolean.class));
		verify(tcRequestModel).setLastModDate(isA(Timestamp.class));
		verify(tcRequestModel).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
		verify(tcRequestModel).setProcessed(eq("Processed"));
		verify(tcRequestModel).setProviderServiceMapID(isA(Integer.class));
		verify(tcRequestModel).setRequestDate(isA(Timestamp.class));
		verify(tcRequestModel).setSpecializationID(isA(Integer.class));
		verify(tcRequestModel).setStatus(eq("Status"));
		verify(tcRequestModel).setUserID(isA(Integer.class));
		verify(tcRequestModel).setVanID(isA(Integer.class));
		verify(tcRequestModel).setVisitCode(isA(Long.class));
		verify(tcRequestModel).settMRequestID(isA(Long.class));
		verify(tCRequestModelRepo).save(isA(TCRequestModel.class));
		assertEquals(1L, actualCreateTCRequestResult.longValue());
	}

	@Test
	void testCreateTCRequest3() {
		// Arrange
		TCRequestModel tcRequestModel = mock(TCRequestModel.class);
		when(tcRequestModel.gettMRequestID()).thenReturn(0L);
		doNothing().when(tcRequestModel).setBenVisitID(Mockito.<Long>any());
		doNothing().when(tcRequestModel).setBeneficiaryArrivalTime(Mockito.<Timestamp>any());
		doNothing().when(tcRequestModel).setBeneficiaryRegID(Mockito.<Long>any());
		doNothing().when(tcRequestModel).setConsultationStats(Mockito.<String>any());
		doNothing().when(tcRequestModel).setConsultation_FirstStart(Mockito.<Timestamp>any());
		doNothing().when(tcRequestModel).setConsultation_LastEnd(Mockito.<Timestamp>any());
		doNothing().when(tcRequestModel).setCreatedBy(Mockito.<String>any());
		doNothing().when(tcRequestModel).setCreatedDate(Mockito.<Timestamp>any());
		doNothing().when(tcRequestModel).setDeleted(Mockito.<Boolean>any());
		doNothing().when(tcRequestModel).setDuration_minute(Mockito.<Long>any());
		doNothing().when(tcRequestModel).setIsReferredByDoctor(Mockito.<Boolean>any());
		doNothing().when(tcRequestModel).setLastModDate(Mockito.<Timestamp>any());
		doNothing().when(tcRequestModel).setModifiedBy(Mockito.<String>any());
		doNothing().when(tcRequestModel).setProcessed(Mockito.<String>any());
		doNothing().when(tcRequestModel).setProviderServiceMapID(Mockito.<Integer>any());
		doNothing().when(tcRequestModel).setRequestDate(Mockito.<Timestamp>any());
		doNothing().when(tcRequestModel).setSpecializationID(Mockito.<Integer>any());
		doNothing().when(tcRequestModel).setStatus(Mockito.<String>any());
		doNothing().when(tcRequestModel).setUserID(Mockito.<Integer>any());
		doNothing().when(tcRequestModel).setVanID(Mockito.<Integer>any());
		doNothing().when(tcRequestModel).setVisitCode(Mockito.<Long>any());
		doNothing().when(tcRequestModel).settMRequestID(Mockito.<Long>any());
		tcRequestModel.setBenVisitID(1L);
		tcRequestModel.setBeneficiaryArrivalTime(mock(Timestamp.class));
		tcRequestModel.setBeneficiaryRegID(1L);
		tcRequestModel.setConsultationStats("Consultation Stats");
		tcRequestModel.setConsultation_FirstStart(mock(Timestamp.class));
		tcRequestModel.setConsultation_LastEnd(mock(Timestamp.class));
		tcRequestModel.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		tcRequestModel.setCreatedDate(mock(Timestamp.class));
		tcRequestModel.setDeleted(true);
		tcRequestModel.setDuration_minute(1L);
		tcRequestModel.setIsReferredByDoctor(true);
		tcRequestModel.setLastModDate(mock(Timestamp.class));
		tcRequestModel.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		tcRequestModel.setProcessed("Processed");
		tcRequestModel.setProviderServiceMapID(1);
		tcRequestModel.setRequestDate(mock(Timestamp.class));
		tcRequestModel.setSpecializationID(1);
		tcRequestModel.setStatus("Status");
		tcRequestModel.setUserID(1);
		tcRequestModel.setVanID(1);
		tcRequestModel.setVisitCode(1L);
		tcRequestModel.settMRequestID(1L);
		when(tCRequestModelRepo.save(Mockito.<TCRequestModel>any())).thenReturn(tcRequestModel);

		TCRequestModel tCRequestModel = new TCRequestModel();
		tCRequestModel.setBenVisitID(1L);
		tCRequestModel.setBeneficiaryArrivalTime(mock(Timestamp.class));
		tCRequestModel.setBeneficiaryRegID(1L);
		tCRequestModel.setConsultationStats("Consultation Stats");
		tCRequestModel.setConsultation_FirstStart(mock(Timestamp.class));
		tCRequestModel.setConsultation_LastEnd(mock(Timestamp.class));
		tCRequestModel.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		tCRequestModel.setCreatedDate(mock(Timestamp.class));
		tCRequestModel.setDeleted(true);
		tCRequestModel.setDuration_minute(1L);
		tCRequestModel.setIsReferredByDoctor(true);
		tCRequestModel.setLastModDate(mock(Timestamp.class));
		tCRequestModel.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		tCRequestModel.setProcessed("Processed");
		tCRequestModel.setProviderServiceMapID(1);
		tCRequestModel.setRequestDate(mock(Timestamp.class));
		tCRequestModel.setSpecializationID(1);
		tCRequestModel.setStatus("Status");
		tCRequestModel.setUserID(1);
		tCRequestModel.setVanID(1);
		tCRequestModel.setVisitCode(1L);
		tCRequestModel.settMRequestID(1L);
		Long actualCreateTCRequestResult = teleConsultationServiceImpl.createTCRequest(tCRequestModel);
		verify(tcRequestModel).gettMRequestID();
		verify(tcRequestModel).setBenVisitID(isA(Long.class));
		verify(tcRequestModel).setBeneficiaryArrivalTime(isA(Timestamp.class));
		verify(tcRequestModel).setBeneficiaryRegID(isA(Long.class));
		verify(tcRequestModel).setConsultationStats(eq("Consultation Stats"));
		verify(tcRequestModel).setConsultation_FirstStart(isA(Timestamp.class));
		verify(tcRequestModel).setConsultation_LastEnd(isA(Timestamp.class));
		verify(tcRequestModel).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
		verify(tcRequestModel).setCreatedDate(isA(Timestamp.class));
		verify(tcRequestModel).setDeleted(isA(Boolean.class));
		verify(tcRequestModel).setDuration_minute(isA(Long.class));
		verify(tcRequestModel).setIsReferredByDoctor(isA(Boolean.class));
		verify(tcRequestModel).setLastModDate(isA(Timestamp.class));
		verify(tcRequestModel).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
		verify(tcRequestModel).setProcessed(eq("Processed"));
		verify(tcRequestModel).setProviderServiceMapID(isA(Integer.class));
		verify(tcRequestModel).setRequestDate(isA(Timestamp.class));
		verify(tcRequestModel).setSpecializationID(isA(Integer.class));
		verify(tcRequestModel).setStatus(eq("Status"));
		verify(tcRequestModel).setUserID(isA(Integer.class));
		verify(tcRequestModel).setVanID(isA(Integer.class));
		verify(tcRequestModel).setVisitCode(isA(Long.class));
		verify(tcRequestModel).settMRequestID(isA(Long.class));
		verify(tCRequestModelRepo).save(isA(TCRequestModel.class));
		assertEquals(0L, actualCreateTCRequestResult.longValue());
	}

	@Test
	public void updateBeneficiaryArrivalStatus_Success() throws Exception {
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ObjectMapper mapper = new ObjectMapper();
		JsonNode requestJson = factory.objectNode();
		((ObjectNode) requestJson).put("benflowID", 1L);
		((ObjectNode) requestJson).put("benRegID", 2L);
		((ObjectNode) requestJson).put("visitCode", 3L);
		((ObjectNode) requestJson).put("modifiedBy", "user");
		((ObjectNode) requestJson).put("status", true);
		((ObjectNode) requestJson).put("userID", 1);
		when(beneficiaryFlowStatusRepo.updateBeneficiaryArrivalStatus(1L, 2L, 3L, true, "user", 1)).thenReturn(1);
		when(tCRequestModelRepo.updateBeneficiaryStatus(2L, 3L, "A", "user", 1, false)).thenReturn(1);
		int resultFlag = teleConsultationServiceImpl
				.updateBeneficiaryArrivalStatus(mapper.writeValueAsString(requestJson));
		Assertions.assertEquals(1, resultFlag);
	}

	@Test
	public void updateBeneficiaryArrivalStatus_TeleConsultationSessionFailure() throws Exception {
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ObjectMapper mapper = new ObjectMapper();
		JsonNode requestJson = factory.objectNode();
		((ObjectNode) requestJson).put("benflowID", 1L);
		((ObjectNode) requestJson).put("benRegID", 2L);
		((ObjectNode) requestJson).put("visitCode", 3L);
		((ObjectNode) requestJson).put("modifiedBy", "user");
		((ObjectNode) requestJson).put("status", true);
		((ObjectNode) requestJson).put("userID", 1);
		when(beneficiaryFlowStatusRepo.updateBeneficiaryArrivalStatus(1L, 2L, 3L, true, "user", 1)).thenReturn(1);
		when(tCRequestModelRepo.updateBeneficiaryStatus(2L, 3L, "A", "user", 1, false)).thenReturn(0);
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> teleConsultationServiceImpl
				.updateBeneficiaryArrivalStatus(mapper.writeValueAsString(requestJson)));
		Assertions.assertEquals("Beneficiary arrival status update failed.There is no active Tele-consultation session",
				exception.getMessage());
	}

	@Test
	public void updateBeneficiaryArrivalStatus_NoActiveTeleConsultationSessionForVisit() throws Exception {
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ObjectMapper mapper = new ObjectMapper();
		JsonNode requestJson = factory.objectNode();
		((ObjectNode) requestJson).put("benflowID", 1L);
		((ObjectNode) requestJson).put("benRegID", 2L);
		((ObjectNode) requestJson).put("visitCode", 3L);
		((ObjectNode) requestJson).put("modifiedBy", "user");
		((ObjectNode) requestJson).put("status", true);
		((ObjectNode) requestJson).put("userID", 1);
		when(beneficiaryFlowStatusRepo.updateBeneficiaryArrivalStatus(1L, 2L, 3L, true, "user", 1)).thenReturn(0);
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> teleConsultationServiceImpl
				.updateBeneficiaryArrivalStatus(mapper.writeValueAsString(requestJson)));
		Assertions.assertEquals(
				"Beneficiary arrival status update failed.There is no active Tele-consultation session for this visit",
				exception.getMessage());
	}

	@Test
	public void updateBeneficiaryArrivalStatus_InvalidRequest() throws Exception {
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ObjectMapper mapper = new ObjectMapper();
		JsonNode requestJson = factory.objectNode();
		((ObjectNode) requestJson).put("benRegID", 2L);
		((ObjectNode) requestJson).put("visitCode", 3L);
		((ObjectNode) requestJson).put("modifiedBy", "user");
		((ObjectNode) requestJson).put("status", true);
		((ObjectNode) requestJson).put("userID", 1);
		Exception exception = Assertions.assertThrows(RuntimeException.class, () -> teleConsultationServiceImpl
				.updateBeneficiaryArrivalStatus(mapper.writeValueAsString(requestJson)));
		Assertions.assertEquals("Invalid request", exception.getMessage());
	}

	@Test
	public void updateBeneficiaryStatusToCancelTCRequest_Success() throws Exception {
		String requestOBJ = "{\"benflowID\": 1, \"benRegID\": 2, \"visitCode\": 3, \"modifiedBy\": \"user\", \"status\": true, \"userID\": 1}";
		TCRequestModel tCRequestModelRS = new TCRequestModel();
		when(tCRequestModelRepo.getSpecializationID(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt()))
				.thenReturn(tCRequestModelRS);
		when(beneficiaryFlowStatusRepo.updateBeneficiaryStatusToCancelRequest(Mockito.anyLong(), Mockito.anyLong(),
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyInt())).thenReturn(1);
		when(tCRequestModelRepo.updateBeneficiaryStatusCancel(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(1);
		int resultFlag = teleConsultationServiceImpl.updateBeneficiaryStatusToCancelTCRequest(requestOBJ, null);
		assertThat(resultFlag).isEqualTo(1);
	}

	@Test
	public void updateBeneficiaryStatusToCancelTCRequest_InvalidRequest() throws Exception {
		String requestOBJ = "{\"benflowID\": 1, \"benRegID\": 2, \"visitCode\": 3, \"modifiedBy\": \"user\"}";
		assertThatExceptionOfType(RuntimeException.class)
				.isThrownBy(
						() -> teleConsultationServiceImpl.updateBeneficiaryStatusToCancelTCRequest(requestOBJ, null))
				.withMessage("Invalid request");
	}

	@Test
	public void updateBeneficiaryStatusToCancelTCRequest_TeleconsultationCancelRequestFailed() throws Exception {
		String requestOBJ = "{\"benflowID\": 1, \"benRegID\": 2, \"visitCode\": 3, \"modifiedBy\": \"user\", \"status\": true}";
		assertThatExceptionOfType(RuntimeException.class)
				.isThrownBy(
						() -> teleConsultationServiceImpl.updateBeneficiaryStatusToCancelTCRequest(requestOBJ, null))
				.withMessage("Invalid request");
	}

	@Test
	public void updateBeneficiaryStatusToCancelTCRequest_TeleconsultationCancelRequestFailed_NoActiveSession()
			throws Exception {
		String requestOBJ = "{\"benflowID\": 1, \"benRegID\": 2, \"visitCode\": 3, \"modifiedBy\": \"user\", \"status\": true, \"userID\": 1}";
		TCRequestModel tCRequestModelRS = new TCRequestModel();
		when(tCRequestModelRepo.getSpecializationID(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt()))
				.thenReturn(tCRequestModelRS);
		when(beneficiaryFlowStatusRepo.updateBeneficiaryStatusToCancelRequest(Mockito.anyLong(), Mockito.anyLong(),
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyInt())).thenReturn(-1);
		when(tCRequestModelRepo.updateBeneficiaryStatusCancel(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(-1);
		assertThatExceptionOfType(RuntimeException.class)
				.isThrownBy(
						() -> teleConsultationServiceImpl.updateBeneficiaryStatusToCancelTCRequest(requestOBJ, null))
				.withMessage(
						"Teleconsultation cancel request failed.There is no active Tele-consultation session for this visit");
	}

	@Test
	  public void testCancelSlotForTCCancel_Success() throws Exception {
	    int userID = 1;
	    long benRegID = 1;
	    long visitCode = 1;
	    String authorization = "YOUR_AUTH_TOKEN";
	    TCRequestModel tcRequestModel = new TCRequestModel();
	    tcRequestModel.setRequestDate(new Timestamp(System.currentTimeMillis()));
	    when(tCRequestModelRepo.getTcDetailsList(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt(), Mockito.anySet()))
	        .thenReturn(new ArrayList<>(Arrays.asList(tcRequestModel)));
	    HttpHeaders headers = new HttpHeaders();
	    RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("AUTHORIZATION", authorization);
	    TcSpecialistSlotBookingRequestOBJ obj = new TcSpecialistSlotBookingRequestOBJ();
	    obj.setUserID(userID);
	    obj.setDate(tcRequestModel.getRequestDate());
	    String fromTime = tcRequestModel.getRequestDate().toString().split(" ")[1];
	    obj.setFromTime(fromTime);
	    Gson gson = new GsonBuilder().create();
	    String requestOBJ = gson.toJson(obj);
	    HttpEntity<Object> requestEntity = new HttpEntity<>(requestOBJ, headers);
	    ResponseEntity<String> responseEntity = new ResponseEntity<>("{ \"statusCode\": 200 }", HttpStatus.OK);
	    when(restTemplate.exchange(Mockito.anyString(), eq(HttpMethod.POST), eq(requestEntity), eq(String.class)))
	        .thenReturn(responseEntity);

	    int result = teleConsultationServiceImpl.cancelSlotForTCCancel(userID, benRegID, visitCode, authorization);
	    assertEquals(1, result);
	  }

	@Test
	void testCancelSlotForTCCancel() throws Exception {
		// Arrange
		when(tCRequestModelRepo.getTcDetailsList(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Integer>any(),
				Mockito.<Set<String>>any())).thenReturn(new ArrayList<>());
		int actualCancelSlotForTCCancelResult = teleConsultationServiceImpl.cancelSlotForTCCancel(1, 1L, 1L, "JaneDoe");
		verify(tCRequestModelRepo).getTcDetailsList(isA(Long.class), isA(Long.class), isA(Integer.class),
				isA(Set.class));
		assertEquals(1, actualCancelSlotForTCCancelResult);
	}

	@Test
	void testCancelSlotForTCCancel2() throws Exception {
		when(tCRequestModelRepo.getTcDetailsList(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Integer>any(),
				Mockito.<Set<String>>any())).thenThrow(new RuntimeException("N"));
		assertThrows(RuntimeException.class,
				() -> teleConsultationServiceImpl.cancelSlotForTCCancel(1, 1L, 1L, "JaneDoe"));
		verify(tCRequestModelRepo).getTcDetailsList(isA(Long.class), isA(Long.class), isA(Integer.class),
				isA(Set.class));
	}

	@Test
	public void testAllConditionsMet_BeneficiaryArrived_TCSessionActive() throws Exception {
		String validRequestJson = "{\"benflowID\": 1, \"benRegID\": 1, \"visitCode\": 1, \"userID\": 1}";
		BeneficiaryFlowStatus benFlowStatus = new BeneficiaryFlowStatus();
		benFlowStatus.setBenArrivedFlag(true);
		when(beneficiaryFlowStatusRepo.checkBeneficiaryArrivalStatus(Mockito.anyLong(), Mockito.anyLong(),
				Mockito.anyLong(), Mockito.anyInt())).thenReturn(new ArrayList<>(Arrays.asList(benFlowStatus)));
		when(tCRequestModelRepo.checkBenTcStatus(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt()))
				.thenReturn(new ArrayList<>(Arrays.asList(new TCRequestModel())));
		int result = teleConsultationServiceImpl.checkBeneficiaryStatusForSpecialistTransaction(validRequestJson);
		assertEquals(1, result);
	}

	@Test
	public void checkBeneficiaryStatusForSpecialistTransaction_NoRecordFound() throws Exception {
		String requestOBJ = "{\"benflowID\": 1, \"benRegID\": 2, \"visitCode\": 3, \"userID\": 1}";
		when(beneficiaryFlowStatusRepo.checkBeneficiaryArrivalStatus(Mockito.anyLong(), Mockito.anyLong(),
				Mockito.anyLong(), Mockito.anyInt())).thenReturn(new ArrayList<>());
		assertThatExceptionOfType(RuntimeException.class)
				.isThrownBy(
						() -> teleConsultationServiceImpl.checkBeneficiaryStatusForSpecialistTransaction(requestOBJ))
				.withMessage("No record or multiple record found in DB. Kindly contact the administrator");
	}

	@Test
	public void checkBeneficiaryStatusForSpecialistTransaction_InvalidRequest() throws Exception {
		String requestOBJ = "{\"benflowID\": 1, \"benRegID\": 2, \"visitCode\": 3}";
		assertThatExceptionOfType(RuntimeException.class)
				.isThrownBy(
						() -> teleConsultationServiceImpl.checkBeneficiaryStatusForSpecialistTransaction(requestOBJ))
				.withMessage("Invalid request.");
	}

	@Test
	void testCreateTCRequestFromWorkList() throws Exception {
		when(beneficiaryFlowStatusRepo.updateFlagAfterTcRequestCreatedFromWorklist(Mockito.<Long>any(),
				Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Integer>any(), Mockito.<Timestamp>any()))
						.thenReturn(1);

		TeleconsultationRequestOBJ teleconsultationRequestOBJ = new TeleconsultationRequestOBJ();
		teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
		teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
		teleconsultationRequestOBJ.setSpecializationID(1);
		teleconsultationRequestOBJ.setTmRequestID(1L);
		teleconsultationRequestOBJ.setToTime("To Time");
		teleconsultationRequestOBJ.setUserID(1);
		teleconsultationRequestOBJ.setWalkIn(true);
		when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
				Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);
		int actualCreateTCRequestFromWorkListResult = teleConsultationServiceImpl
				.createTCRequestFromWorkList(new JsonObject(), "JaneDoe");
		verify(beneficiaryFlowStatusRepo).updateFlagAfterTcRequestCreatedFromWorklist(isNull(), isNull(), isNull(),
				isA(Integer.class), isA(Timestamp.class));
		verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
		assertEquals(1, actualCreateTCRequestFromWorkListResult);
	}

	@Test
	void testCreateTCRequestFromWorkList2() throws Exception {
		// Arrange
		when(beneficiaryFlowStatusRepo.updateFlagAfterTcRequestCreatedFromWorklist(Mockito.<Long>any(),
				Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Integer>any(), Mockito.<Timestamp>any()))
						.thenThrow(new RuntimeException(
								"Error while updating beneficiary flow status. Kindly contact your administrator"));

		TeleconsultationRequestOBJ teleconsultationRequestOBJ = new TeleconsultationRequestOBJ();
		teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
		teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
		teleconsultationRequestOBJ.setSpecializationID(1);
		teleconsultationRequestOBJ.setTmRequestID(1L);
		teleconsultationRequestOBJ.setToTime("To Time");
		teleconsultationRequestOBJ.setUserID(1);
		teleconsultationRequestOBJ.setWalkIn(true);
		when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
				Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);
		assertThrows(RuntimeException.class,
				() -> teleConsultationServiceImpl.createTCRequestFromWorkList(new JsonObject(), "JaneDoe"));
		verify(beneficiaryFlowStatusRepo).updateFlagAfterTcRequestCreatedFromWorklist(isNull(), isNull(), isNull(),
				isA(Integer.class), isA(Timestamp.class));
		verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
	}

	@Test
	void testCreateTCRequestFromWorkList3() throws Exception {
		// Arrange
		when(beneficiaryFlowStatusRepo.updateFlagAfterTcRequestCreatedFromWorklist(Mockito.<Long>any(),
				Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Integer>any(), Mockito.<Timestamp>any()))
						.thenReturn(0);

		TeleconsultationRequestOBJ teleconsultationRequestOBJ = new TeleconsultationRequestOBJ();
		teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
		teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
		teleconsultationRequestOBJ.setSpecializationID(1);
		teleconsultationRequestOBJ.setTmRequestID(1L);
		teleconsultationRequestOBJ.setToTime("To Time");
		teleconsultationRequestOBJ.setUserID(1);
		teleconsultationRequestOBJ.setWalkIn(true);
		when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
				Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);
		assertThrows(RuntimeException.class,
				() -> teleConsultationServiceImpl.createTCRequestFromWorkList(new JsonObject(), "JaneDoe"));
		verify(beneficiaryFlowStatusRepo).updateFlagAfterTcRequestCreatedFromWorklist(isNull(), isNull(), isNull(),
				isA(Integer.class), isA(Timestamp.class));
		verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
	}

	@Test
	void testCreateTCRequestFromWorkList4() throws Exception {
		// Arrange
		when(beneficiaryFlowStatusRepo.updateFlagAfterTcRequestCreatedFromWorklist(Mockito.<Long>any(),
				Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Integer>any(), Mockito.<Timestamp>any()))
						.thenReturn(1);
		TeleconsultationRequestOBJ teleconsultationRequestOBJ = mock(TeleconsultationRequestOBJ.class);
		when(teleconsultationRequestOBJ.getWalkIn()).thenThrow(new RuntimeException("schedule"));
		when(teleconsultationRequestOBJ.getUserID()).thenReturn(1);
		when(teleconsultationRequestOBJ.getAllocationDate()).thenReturn(mock(Timestamp.class));
		doNothing().when(teleconsultationRequestOBJ).setAllocationDate(Mockito.<Timestamp>any());
		doNothing().when(teleconsultationRequestOBJ).setFromTime(Mockito.<String>any());
		doNothing().when(teleconsultationRequestOBJ).setSpecializationID(Mockito.<Integer>any());
		doNothing().when(teleconsultationRequestOBJ).setTmRequestID(Mockito.<Long>any());
		doNothing().when(teleconsultationRequestOBJ).setToTime(Mockito.<String>any());
		doNothing().when(teleconsultationRequestOBJ).setUserID(Mockito.<Integer>any());
		doNothing().when(teleconsultationRequestOBJ).setWalkIn(Mockito.<Boolean>any());
		teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
		teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
		teleconsultationRequestOBJ.setSpecializationID(1);
		teleconsultationRequestOBJ.setTmRequestID(1L);
		teleconsultationRequestOBJ.setToTime("To Time");
		teleconsultationRequestOBJ.setUserID(1);
		teleconsultationRequestOBJ.setWalkIn(true);
		when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
				Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);
		assertThrows(RuntimeException.class,
				() -> teleConsultationServiceImpl.createTCRequestFromWorkList(new JsonObject(), "JaneDoe"));
		verify(teleconsultationRequestOBJ, atLeast(1)).getAllocationDate();
		verify(teleconsultationRequestOBJ, atLeast(1)).getUserID();
		verify(teleconsultationRequestOBJ).getWalkIn();
		verify(teleconsultationRequestOBJ).setAllocationDate(isA(Timestamp.class));
		verify(teleconsultationRequestOBJ).setFromTime(eq("jane.doe@example.org"));
		verify(teleconsultationRequestOBJ).setSpecializationID(isA(Integer.class));
		verify(teleconsultationRequestOBJ).setTmRequestID(isA(Long.class));
		verify(teleconsultationRequestOBJ).setToTime(eq("To Time"));
		verify(teleconsultationRequestOBJ).setUserID(isA(Integer.class));
		verify(teleconsultationRequestOBJ).setWalkIn(isA(Boolean.class));
		verify(beneficiaryFlowStatusRepo).updateFlagAfterTcRequestCreatedFromWorklist(isNull(), isNull(), isNull(),
				isA(Integer.class), isA(Timestamp.class));
		verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
	}

	@Test
	void testCreateTCRequestFromWorkList5() throws Exception {
		// Arrange
		when(beneficiaryFlowStatusRepo.updateFlagAfterTcRequestCreatedFromWorklist(Mockito.<Long>any(),
				Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Integer>any(), Mockito.<Timestamp>any()))
						.thenReturn(1);
		TeleconsultationRequestOBJ teleconsultationRequestOBJ = mock(TeleconsultationRequestOBJ.class);
		when(teleconsultationRequestOBJ.getWalkIn()).thenReturn(false);
		when(teleconsultationRequestOBJ.getSpecializationID()).thenReturn(1);
		when(teleconsultationRequestOBJ.getUserID()).thenReturn(1);
		when(teleconsultationRequestOBJ.getAllocationDate()).thenReturn(mock(Timestamp.class));
		doNothing().when(teleconsultationRequestOBJ).setAllocationDate(Mockito.<Timestamp>any());
		doNothing().when(teleconsultationRequestOBJ).setFromTime(Mockito.<String>any());
		doNothing().when(teleconsultationRequestOBJ).setSpecializationID(Mockito.<Integer>any());
		doNothing().when(teleconsultationRequestOBJ).setTmRequestID(Mockito.<Long>any());
		doNothing().when(teleconsultationRequestOBJ).setToTime(Mockito.<String>any());
		doNothing().when(teleconsultationRequestOBJ).setUserID(Mockito.<Integer>any());
		doNothing().when(teleconsultationRequestOBJ).setWalkIn(Mockito.<Boolean>any());
		teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
		teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
		teleconsultationRequestOBJ.setSpecializationID(1);
		teleconsultationRequestOBJ.setTmRequestID(1L);
		teleconsultationRequestOBJ.setToTime("To Time");
		teleconsultationRequestOBJ.setUserID(1);
		teleconsultationRequestOBJ.setWalkIn(true);
		when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
				Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);
		when(sMSGatewayServiceImpl.smsSenderGateway(Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Integer>any(),
				Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any(),
				Mockito.<String>any(), Mockito.<String>any())).thenReturn(3);

		// Act
		int actualCreateTCRequestFromWorkListResult = teleConsultationServiceImpl
				.createTCRequestFromWorkList(new JsonObject(), "JaneDoe");
		verify(teleconsultationRequestOBJ, atLeast(1)).getAllocationDate();
		verify(teleconsultationRequestOBJ).getSpecializationID();
		verify(teleconsultationRequestOBJ, atLeast(1)).getUserID();
		verify(teleconsultationRequestOBJ).getWalkIn();
		verify(teleconsultationRequestOBJ).setAllocationDate(isA(Timestamp.class));
		verify(teleconsultationRequestOBJ).setFromTime(eq("jane.doe@example.org"));
		verify(teleconsultationRequestOBJ).setSpecializationID(isA(Integer.class));
		verify(teleconsultationRequestOBJ).setTmRequestID(isA(Long.class));
		verify(teleconsultationRequestOBJ).setToTime(eq("To Time"));
		verify(teleconsultationRequestOBJ).setUserID(isA(Integer.class));
		verify(teleconsultationRequestOBJ).setWalkIn(isA(Boolean.class));
		verify(beneficiaryFlowStatusRepo).updateFlagAfterTcRequestCreatedFromWorklist(isNull(), isNull(), isNull(),
				isA(Integer.class), isA(Timestamp.class));
		verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
		verify(sMSGatewayServiceImpl).smsSenderGateway(eq("schedule"), isNull(), isA(Integer.class), isNull(), isNull(),
				isNull(), eq("Mock for Timestamp, hashCode: 1537977022"), isNull(), eq("JaneDoe"));
		assertEquals(1, actualCreateTCRequestFromWorkListResult);
	}

	@Test
	void testCreateTCRequestFromWorkList6() throws Exception {
		// Arrange
		when(beneficiaryFlowStatusRepo.updateFlagAfterTcRequestCreatedFromWorklist(Mockito.<Long>any(),
				Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Integer>any(), Mockito.<Timestamp>any()))
						.thenReturn(1);
		TeleconsultationRequestOBJ teleconsultationRequestOBJ = mock(TeleconsultationRequestOBJ.class);
		when(teleconsultationRequestOBJ.getWalkIn()).thenReturn(true);
		when(teleconsultationRequestOBJ.getUserID()).thenReturn(1);
		when(teleconsultationRequestOBJ.getAllocationDate()).thenReturn(mock(Timestamp.class));
		doNothing().when(teleconsultationRequestOBJ).setAllocationDate(Mockito.<Timestamp>any());
		doNothing().when(teleconsultationRequestOBJ).setFromTime(Mockito.<String>any());
		doNothing().when(teleconsultationRequestOBJ).setSpecializationID(Mockito.<Integer>any());
		doNothing().when(teleconsultationRequestOBJ).setTmRequestID(Mockito.<Long>any());
		doNothing().when(teleconsultationRequestOBJ).setToTime(Mockito.<String>any());
		doNothing().when(teleconsultationRequestOBJ).setUserID(Mockito.<Integer>any());
		doNothing().when(teleconsultationRequestOBJ).setWalkIn(Mockito.<Boolean>any());
		teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
		teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
		teleconsultationRequestOBJ.setSpecializationID(1);
		teleconsultationRequestOBJ.setTmRequestID(1L);
		teleconsultationRequestOBJ.setToTime("To Time");
		teleconsultationRequestOBJ.setUserID(1);
		teleconsultationRequestOBJ.setWalkIn(true);
		when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
				Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);

		JsonObject tcRequestOBJ = new JsonObject();
		tcRequestOBJ.add("Property", new JsonArray(3));
		int actualCreateTCRequestFromWorkListResult = teleConsultationServiceImpl
				.createTCRequestFromWorkList(tcRequestOBJ, "JaneDoe");
		verify(teleconsultationRequestOBJ, atLeast(1)).getAllocationDate();
		verify(teleconsultationRequestOBJ, atLeast(1)).getUserID();
		verify(teleconsultationRequestOBJ).getWalkIn();
		verify(teleconsultationRequestOBJ).setAllocationDate(isA(Timestamp.class));
		verify(teleconsultationRequestOBJ).setFromTime(eq("jane.doe@example.org"));
		verify(teleconsultationRequestOBJ).setSpecializationID(isA(Integer.class));
		verify(teleconsultationRequestOBJ).setTmRequestID(isA(Long.class));
		verify(teleconsultationRequestOBJ).setToTime(eq("To Time"));
		verify(teleconsultationRequestOBJ).setUserID(isA(Integer.class));
		verify(teleconsultationRequestOBJ).setWalkIn(isA(Boolean.class));
		verify(beneficiaryFlowStatusRepo).updateFlagAfterTcRequestCreatedFromWorklist(isNull(), isNull(), isNull(),
				isA(Integer.class), isA(Timestamp.class));
		verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
		assertEquals(1, actualCreateTCRequestFromWorkListResult);
	}

	@Test
	void testCreateTCRequestFromWorkList7() throws Exception {
		// Arrange
		when(beneficiaryFlowStatusRepo.updateFlagAfterTcRequestCreatedFromWorklist(Mockito.<Long>any(),
				Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Integer>any(), Mockito.<Timestamp>any()))
						.thenReturn(1);
		TeleconsultationRequestOBJ teleconsultationRequestOBJ = mock(TeleconsultationRequestOBJ.class);
		when(teleconsultationRequestOBJ.getWalkIn()).thenReturn(true);
		when(teleconsultationRequestOBJ.getUserID()).thenReturn(1);
		when(teleconsultationRequestOBJ.getAllocationDate()).thenReturn(mock(Timestamp.class));
		doNothing().when(teleconsultationRequestOBJ).setAllocationDate(Mockito.<Timestamp>any());
		doNothing().when(teleconsultationRequestOBJ).setFromTime(Mockito.<String>any());
		doNothing().when(teleconsultationRequestOBJ).setSpecializationID(Mockito.<Integer>any());
		doNothing().when(teleconsultationRequestOBJ).setTmRequestID(Mockito.<Long>any());
		doNothing().when(teleconsultationRequestOBJ).setToTime(Mockito.<String>any());
		doNothing().when(teleconsultationRequestOBJ).setUserID(Mockito.<Integer>any());
		doNothing().when(teleconsultationRequestOBJ).setWalkIn(Mockito.<Boolean>any());
		teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
		teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
		teleconsultationRequestOBJ.setSpecializationID(1);
		teleconsultationRequestOBJ.setTmRequestID(1L);
		teleconsultationRequestOBJ.setToTime("To Time");
		teleconsultationRequestOBJ.setUserID(1);
		teleconsultationRequestOBJ.setWalkIn(true);
		when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
				Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);

		JsonObject tcRequestOBJ = new JsonObject();
		tcRequestOBJ.addProperty("Property", "42");
		int actualCreateTCRequestFromWorkListResult = teleConsultationServiceImpl
				.createTCRequestFromWorkList(tcRequestOBJ, "JaneDoe");
		verify(teleconsultationRequestOBJ, atLeast(1)).getAllocationDate();
		verify(teleconsultationRequestOBJ, atLeast(1)).getUserID();
		verify(teleconsultationRequestOBJ).getWalkIn();
		verify(teleconsultationRequestOBJ).setAllocationDate(isA(Timestamp.class));
		verify(teleconsultationRequestOBJ).setFromTime(eq("jane.doe@example.org"));
		verify(teleconsultationRequestOBJ).setSpecializationID(isA(Integer.class));
		verify(teleconsultationRequestOBJ).setTmRequestID(isA(Long.class));
		verify(teleconsultationRequestOBJ).setToTime(eq("To Time"));
		verify(teleconsultationRequestOBJ).setUserID(isA(Integer.class));
		verify(teleconsultationRequestOBJ).setWalkIn(isA(Boolean.class));
		verify(beneficiaryFlowStatusRepo).updateFlagAfterTcRequestCreatedFromWorklist(isNull(), isNull(), isNull(),
				isA(Integer.class), isA(Timestamp.class));
		verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
		assertEquals(1, actualCreateTCRequestFromWorkListResult);
	}

	@Test
	void testCreateTCRequestFromWorkList8() throws Exception {
		// Arrange
		when(beneficiaryFlowStatusRepo.updateFlagAfterTcRequestCreatedFromWorklist(Mockito.<Long>any(),
				Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Integer>any(), Mockito.<Timestamp>any()))
						.thenReturn(1);
		TeleconsultationRequestOBJ teleconsultationRequestOBJ = mock(TeleconsultationRequestOBJ.class);
		when(teleconsultationRequestOBJ.getWalkIn()).thenReturn(true);
		when(teleconsultationRequestOBJ.getUserID()).thenReturn(1);
		when(teleconsultationRequestOBJ.getAllocationDate()).thenReturn(mock(Timestamp.class));
		doNothing().when(teleconsultationRequestOBJ).setAllocationDate(Mockito.<Timestamp>any());
		doNothing().when(teleconsultationRequestOBJ).setFromTime(Mockito.<String>any());
		doNothing().when(teleconsultationRequestOBJ).setSpecializationID(Mockito.<Integer>any());
		doNothing().when(teleconsultationRequestOBJ).setTmRequestID(Mockito.<Long>any());
		doNothing().when(teleconsultationRequestOBJ).setToTime(Mockito.<String>any());
		doNothing().when(teleconsultationRequestOBJ).setUserID(Mockito.<Integer>any());
		doNothing().when(teleconsultationRequestOBJ).setWalkIn(Mockito.<Boolean>any());
		teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
		teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
		teleconsultationRequestOBJ.setSpecializationID(1);
		teleconsultationRequestOBJ.setTmRequestID(1L);
		teleconsultationRequestOBJ.setToTime("To Time");
		teleconsultationRequestOBJ.setUserID(1);
		teleconsultationRequestOBJ.setWalkIn(true);
		when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
				Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);

		JsonObject tcRequestOBJ = new JsonObject();
		Integer value = Integer.valueOf(1);
		tcRequestOBJ.addProperty("Property", value);
		int actualCreateTCRequestFromWorkListResult = teleConsultationServiceImpl
				.createTCRequestFromWorkList(tcRequestOBJ, "JaneDoe");
		verify(teleconsultationRequestOBJ, atLeast(1)).getAllocationDate();
		verify(teleconsultationRequestOBJ, atLeast(1)).getUserID();
		verify(teleconsultationRequestOBJ).getWalkIn();
		verify(teleconsultationRequestOBJ).setAllocationDate(isA(Timestamp.class));
		verify(teleconsultationRequestOBJ).setFromTime(eq("jane.doe@example.org"));
		verify(teleconsultationRequestOBJ).setSpecializationID(isA(Integer.class));
		verify(teleconsultationRequestOBJ).setTmRequestID(isA(Long.class));
		verify(teleconsultationRequestOBJ).setToTime(eq("To Time"));
		verify(teleconsultationRequestOBJ).setUserID(isA(Integer.class));
		verify(teleconsultationRequestOBJ).setWalkIn(isA(Boolean.class));
		verify(beneficiaryFlowStatusRepo).updateFlagAfterTcRequestCreatedFromWorklist(isNull(), isNull(), isNull(),
				isA(Integer.class), isA(Timestamp.class));
		verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
		assertEquals(1, actualCreateTCRequestFromWorkListResult);
		assertSame(value, actualCreateTCRequestFromWorkListResult);
	}

	@Test
	void testCreateTCRequestFromWorkList9() throws Exception {
		// Arrange
		when(beneficiaryFlowStatusRepo.updateFlagAfterTcRequestCreatedFromWorklist(Mockito.<Long>any(),
				Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Integer>any(), Mockito.<Timestamp>any()))
						.thenReturn(1);
		TeleconsultationRequestOBJ teleconsultationRequestOBJ = mock(TeleconsultationRequestOBJ.class);
		when(teleconsultationRequestOBJ.getWalkIn()).thenReturn(true);
		when(teleconsultationRequestOBJ.getUserID()).thenReturn(1);
		when(teleconsultationRequestOBJ.getAllocationDate()).thenReturn(mock(Timestamp.class));
		doNothing().when(teleconsultationRequestOBJ).setAllocationDate(Mockito.<Timestamp>any());
		doNothing().when(teleconsultationRequestOBJ).setFromTime(Mockito.<String>any());
		doNothing().when(teleconsultationRequestOBJ).setSpecializationID(Mockito.<Integer>any());
		doNothing().when(teleconsultationRequestOBJ).setTmRequestID(Mockito.<Long>any());
		doNothing().when(teleconsultationRequestOBJ).setToTime(Mockito.<String>any());
		doNothing().when(teleconsultationRequestOBJ).setUserID(Mockito.<Integer>any());
		doNothing().when(teleconsultationRequestOBJ).setWalkIn(Mockito.<Boolean>any());
		teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
		teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
		teleconsultationRequestOBJ.setSpecializationID(1);
		teleconsultationRequestOBJ.setTmRequestID(1L);
		teleconsultationRequestOBJ.setToTime("To Time");
		teleconsultationRequestOBJ.setUserID(1);
		teleconsultationRequestOBJ.setWalkIn(true);
		when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
				Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);

		JsonObject tcRequestOBJ = new JsonObject();
		tcRequestOBJ.addProperty("Property", true);
		int actualCreateTCRequestFromWorkListResult = teleConsultationServiceImpl
				.createTCRequestFromWorkList(tcRequestOBJ, "JaneDoe");
		verify(teleconsultationRequestOBJ, atLeast(1)).getAllocationDate();
		verify(teleconsultationRequestOBJ, atLeast(1)).getUserID();
		verify(teleconsultationRequestOBJ).getWalkIn();
		verify(teleconsultationRequestOBJ).setAllocationDate(isA(Timestamp.class));
		verify(teleconsultationRequestOBJ).setFromTime(eq("jane.doe@example.org"));
		verify(teleconsultationRequestOBJ).setSpecializationID(isA(Integer.class));
		verify(teleconsultationRequestOBJ).setTmRequestID(isA(Long.class));
		verify(teleconsultationRequestOBJ).setToTime(eq("To Time"));
		verify(teleconsultationRequestOBJ).setUserID(isA(Integer.class));
		verify(teleconsultationRequestOBJ).setWalkIn(isA(Boolean.class));
		verify(beneficiaryFlowStatusRepo).updateFlagAfterTcRequestCreatedFromWorklist(isNull(), isNull(), isNull(),
				isA(Integer.class), isA(Timestamp.class));
		verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
		assertEquals(1, actualCreateTCRequestFromWorkListResult);
	}

	@Test
	void testGetTCRequestListBySpecialistIdAndDate() throws Exception {
		when(beneficiaryFlowStatusRepo.getTCRequestList(Mockito.<Integer>any(), Mockito.<Integer>any(),
				Mockito.<Timestamp>any())).thenReturn(new ArrayList<>());
		String actualTCRequestListBySpecialistIdAndDate = teleConsultationServiceImpl
				.getTCRequestListBySpecialistIdAndDate(1, 1, "2020-03-01");
		verify(beneficiaryFlowStatusRepo).getTCRequestList(isA(Integer.class), isA(Integer.class),
				isA(Timestamp.class));
		assertEquals("[]", actualTCRequestListBySpecialistIdAndDate);
	}

	@Test
	void testGetTCRequestListBySpecialistIdAndDate2() throws Exception {
		when(beneficiaryFlowStatusRepo.getTCRequestList(Mockito.<Integer>any(), Mockito.<Integer>any(),
				Mockito.<Timestamp>any())).thenThrow(new RuntimeException("T"));
		assertThrows(RuntimeException.class,
				() -> teleConsultationServiceImpl.getTCRequestListBySpecialistIdAndDate(1, 1, "2020-03-01"));
		verify(beneficiaryFlowStatusRepo).getTCRequestList(isA(Integer.class), isA(Integer.class),
				isA(Timestamp.class));
	}

	@Test
	void testStartconsultation() {
		// Arrange
		when(tCRequestModelRepo.updateStartConsultationTime(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(3);

		TeleconsultationStats teleconsultationStats = new TeleconsultationStats();
		teleconsultationStats.setBenVisitID(1L);
		teleconsultationStats.setBeneficiaryRegID(1L);
		teleconsultationStats.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		teleconsultationStats.setCreatedDate(mock(Timestamp.class));
		teleconsultationStats.setDeleted(true);
		teleconsultationStats.setEndTime(mock(Timestamp.class));
		teleconsultationStats.setLastModDate(mock(Timestamp.class));
		teleconsultationStats.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		teleconsultationStats.setProcessed("Processed");
		teleconsultationStats.setProviderServiceMapID(1);
		teleconsultationStats.setStartTime(mock(Timestamp.class));
		teleconsultationStats.setVanID(1);
		teleconsultationStats.setVisitCode(5L);
		teleconsultationStats.settMStatsID(1L);
		when(teleconsultationStatsRepo.save(Mockito.<TeleconsultationStats>any())).thenReturn(teleconsultationStats);
		Integer actualStartconsultationResult = teleConsultationServiceImpl.startconsultation(1L, 5L);
		verify(tCRequestModelRepo).updateStartConsultationTime(isA(Long.class), isA(Long.class));
		verify(teleconsultationStatsRepo).save(isA(TeleconsultationStats.class));
		assertEquals(3, actualStartconsultationResult.intValue());
	}

	@Test
	void testStartconsultation2() {
		when(teleconsultationStatsRepo.save(Mockito.<TeleconsultationStats>any()))
				.thenThrow(new RuntimeException("Report"));
		assertThrows(RuntimeException.class, () -> teleConsultationServiceImpl.startconsultation(1L, 5L));
		verify(teleconsultationStatsRepo).save(isA(TeleconsultationStats.class));
	}

	@Test
	void testStartconsultation3() {
		// Arrange
		when(tCRequestModelRepo.updateStartConsultationTime(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(3);

		TeleconsultationStats teleconsultationStats = new TeleconsultationStats();
		teleconsultationStats.setBenVisitID(1L);
		teleconsultationStats.setBeneficiaryRegID(1L);
		teleconsultationStats.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		teleconsultationStats.setCreatedDate(mock(Timestamp.class));
		teleconsultationStats.setDeleted(true);
		teleconsultationStats.setEndTime(mock(Timestamp.class));
		teleconsultationStats.setLastModDate(mock(Timestamp.class));
		teleconsultationStats.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		teleconsultationStats.setProcessed("Processed");
		teleconsultationStats.setProviderServiceMapID(1);
		teleconsultationStats.setStartTime(mock(Timestamp.class));
		teleconsultationStats.setVanID(1);
		teleconsultationStats.setVisitCode(5L);
		teleconsultationStats.settMStatsID(1L);
		when(teleconsultationStatsRepo.save(Mockito.<TeleconsultationStats>any())).thenReturn(teleconsultationStats);
		Integer actualStartconsultationResult = teleConsultationServiceImpl.startconsultation(2L, 5L);
		verify(tCRequestModelRepo).updateStartConsultationTime(isA(Long.class), isA(Long.class));
		verify(teleconsultationStatsRepo).save(isA(TeleconsultationStats.class));
		assertEquals(3, actualStartconsultationResult.intValue());
	}

	@Test
	void testStartconsultation4() {
		// Arrange
		when(tCRequestModelRepo.updateStartConsultationTime(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(3);

		TeleconsultationStats teleconsultationStats = new TeleconsultationStats();
		teleconsultationStats.setBenVisitID(1L);
		teleconsultationStats.setBeneficiaryRegID(1L);
		teleconsultationStats.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		teleconsultationStats.setCreatedDate(mock(Timestamp.class));
		teleconsultationStats.setDeleted(true);
		teleconsultationStats.setEndTime(mock(Timestamp.class));
		teleconsultationStats.setLastModDate(mock(Timestamp.class));
		teleconsultationStats.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		teleconsultationStats.setProcessed("Processed");
		teleconsultationStats.setProviderServiceMapID(1);
		teleconsultationStats.setStartTime(mock(Timestamp.class));
		teleconsultationStats.setVanID(1);
		teleconsultationStats.setVisitCode(5L);
		teleconsultationStats.settMStatsID(1L);
		when(teleconsultationStatsRepo.save(Mockito.<TeleconsultationStats>any())).thenReturn(teleconsultationStats);
		Integer actualStartconsultationResult = teleConsultationServiceImpl.startconsultation(3L, 5L);
		verify(tCRequestModelRepo).updateStartConsultationTime(isA(Long.class), isA(Long.class));
		verify(teleconsultationStatsRepo).save(isA(TeleconsultationStats.class));
		assertEquals(3, actualStartconsultationResult.intValue());
	}

	@Test
	void testStartconsultation5() {
		// Arrange
		when(tCRequestModelRepo.updateStartConsultationTime(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(3);

		TeleconsultationStats teleconsultationStats = new TeleconsultationStats();
		teleconsultationStats.setBenVisitID(1L);
		teleconsultationStats.setBeneficiaryRegID(1L);
		teleconsultationStats.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		teleconsultationStats.setCreatedDate(mock(Timestamp.class));
		teleconsultationStats.setDeleted(true);
		teleconsultationStats.setEndTime(mock(Timestamp.class));
		teleconsultationStats.setLastModDate(mock(Timestamp.class));
		teleconsultationStats.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		teleconsultationStats.setProcessed("Processed");
		teleconsultationStats.setProviderServiceMapID(1);
		teleconsultationStats.setStartTime(mock(Timestamp.class));
		teleconsultationStats.setVanID(1);
		teleconsultationStats.setVisitCode(5L);
		teleconsultationStats.settMStatsID(1L);
		when(teleconsultationStatsRepo.save(Mockito.<TeleconsultationStats>any())).thenReturn(teleconsultationStats);
		Integer actualStartconsultationResult = teleConsultationServiceImpl.startconsultation(4L, 5L);
		verify(tCRequestModelRepo).updateStartConsultationTime(isA(Long.class), isA(Long.class));
		verify(teleconsultationStatsRepo).save(isA(TeleconsultationStats.class));
		assertEquals(3, actualStartconsultationResult.intValue());
	}
}
