package com.iemr.hwc.service.tele_consultation;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.data.quickConsultation.PrescribedDrugDetail;
import com.iemr.hwc.data.tele_consultation.SmsRequestOBJ;
import com.iemr.hwc.repo.tc_consultation.TCRequestModelRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestSMSGatewayServiceImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private TCRequestModelRepo tCRequestModelRepo;
	@Mock
	private RestTemplate restTemplate;
	@InjectMocks
	private SMSGatewayServiceImpl smsGatewayServiceImpl;
	private String sendSMSUrl = "http://example.com/sendSMS";
	private String authorization = "Bearer token";
	private String smsType;
	private List<PrescribedDrugDetail> object;
	private String Authorization;
	private Long benregID;
	private String createdBy;
	private List<Object> diagnosis;
	private Long benRegID;
	private Integer specializationID;
	private Long tMRequestID;
	private Long tMRequestCancelID;
	private String tcDate;
	private String tcPreviousDate;

	@BeforeEach
	public void setUp() {
		smsGatewayServiceImpl.sendSMSUrl = sendSMSUrl;
	}

	@Test
	public void testSmsSenderGateway_Success() {
		String smsType = "schedule";
		Long benRegID = 1L;
		Integer specializationID = 1;
		Long tMRequestID = 1L;
		Long tMRequestCancelID = 1L;
		String createdBy = "user";
		String tcDate = "2023-10-10";
		String tcPreviousDate = "2023-10-09";
		when(tCRequestModelRepo.getSMSTypeID(anyString())).thenReturn(1);
		when(tCRequestModelRepo.getSMSTemplateID(anyInt())).thenReturn(Arrays.asList(1));
		when(restTemplate.exchange(eq(sendSMSUrl), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
				.thenReturn(new org.springframework.http.ResponseEntity<>("{\"statusCode\": 200}",
						org.springframework.http.HttpStatus.OK));

		int result = smsGatewayServiceImpl.smsSenderGateway(smsType, benRegID, specializationID, tMRequestID,
				tMRequestCancelID, createdBy, tcDate, tcPreviousDate, authorization);

		assertEquals(1, result);
	}

	@Test
	public void testSmsSenderGateway_Exception() {
		String smsType = "schedule";
		Long benRegID = 1L;
		Integer specializationID = 1;
		Long tMRequestID = 1L;
		Long tMRequestCancelID = 1L;
		String createdBy = "user";
		String tcDate = "2023-10-10";
		String tcPreviousDate = "2023-10-09";

		when(tCRequestModelRepo.getSMSTypeID(anyString())).thenReturn(1);
		when(tCRequestModelRepo.getSMSTemplateID(anyInt())).thenReturn(Arrays.asList(1));
		when(restTemplate.exchange(eq(sendSMSUrl), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
				.thenThrow(new RuntimeException("Exception during sending SMS"));

		int result = smsGatewayServiceImpl.smsSenderGateway(smsType, benRegID, specializationID, tMRequestID,
				tMRequestCancelID, createdBy, tcDate, tcPreviousDate, authorization);

		assertEquals(0, result);
	}

	@Test
	public void testSmsSenderGateway_NullRequestObject() {
		String smsType = "invalidType";
		Long benRegID = 1L;
		Integer specializationID = 1;
		Long tMRequestID = 1L;
		Long tMRequestCancelID = 1L;
		String createdBy = "user";
		String tcDate = "2023-10-10";
		String tcPreviousDate = "2023-10-09";

		when(tCRequestModelRepo.getSMSTypeID(anyString())).thenReturn(0);

		int result = smsGatewayServiceImpl.smsSenderGateway(smsType, benRegID, specializationID, tMRequestID,
				tMRequestCancelID, createdBy, tcDate, tcPreviousDate, authorization);

		assertEquals(0, result);
	}

	@Test
	public void testSmsSenderGateway_InvalidStatusCode() {
		String smsType = "schedule";
		Long benRegID = 1L;
		Integer specializationID = 1;
		Long tMRequestID = 1L;
		Long tMRequestCancelID = 1L;
		String createdBy = "user";
		String tcDate = "2023-10-10";
		String tcPreviousDate = "2023-10-09";

		when(tCRequestModelRepo.getSMSTypeID(anyString())).thenReturn(1);
		when(tCRequestModelRepo.getSMSTemplateID(anyInt())).thenReturn(Arrays.asList(1));
		when(restTemplate.exchange(eq(sendSMSUrl), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
				.thenReturn(new org.springframework.http.ResponseEntity<>("{\"statusCode\": 500}",
						org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR));

		int result = smsGatewayServiceImpl.smsSenderGateway(smsType, benRegID, specializationID, tMRequestID,
				tMRequestCancelID, createdBy, tcDate, tcPreviousDate, authorization);

		assertEquals(0, result);
	}

	@Test
	public void testSmsSenderGateway2_Success() {
		when(tCRequestModelRepo.getSMSTypeID(smsType)).thenReturn(1);
		when(tCRequestModelRepo.getSMSTemplateID(1)).thenReturn(new ArrayList<>(List.of(1)));
		when(restTemplate.exchange(anyString(), any(), any(), eq(String.class)))
				.thenReturn(new org.springframework.http.ResponseEntity<>("{\"statusCode\": 200}",
						org.springframework.http.HttpStatus.OK));

		int result = smsGatewayServiceImpl.smsSenderGateway2(smsType, object, Authorization, benregID, createdBy,
				diagnosis);

		assertEquals(1, result);
		verify(tCRequestModelRepo, times(1)).getSMSTypeID(smsType);
		verify(tCRequestModelRepo, times(1)).getSMSTemplateID(1);
		verify(restTemplate, times(1)).exchange(anyString(), any(), any(), eq(String.class));
	}

	@Test
	public void testSmsSenderGateway2_Failure() {
		when(tCRequestModelRepo.getSMSTypeID(smsType)).thenReturn(1);
		when(tCRequestModelRepo.getSMSTemplateID(1)).thenReturn(new ArrayList<>(List.of(1)));
		when(restTemplate.exchange(anyString(), any(), any(), eq(String.class)))
				.thenReturn(new org.springframework.http.ResponseEntity<>("{\"statusCode\": 500}",
						org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR));

		int result = smsGatewayServiceImpl.smsSenderGateway2(smsType, object, Authorization, benregID, createdBy,
				diagnosis);

		assertEquals(0, result);
		verify(tCRequestModelRepo, times(1)).getSMSTypeID(smsType);
		verify(tCRequestModelRepo, times(1)).getSMSTemplateID(1);
		verify(restTemplate, times(1)).exchange(anyString(), any(), any(), eq(String.class));
	}

	@Test
	public void testSmsSenderGateway2_NonPrescription() {
		smsType = "nonPrescription";
		int result = smsGatewayServiceImpl.smsSenderGateway2(smsType, object, Authorization, benregID, createdBy,
				diagnosis);

		assertEquals(0, result);
		verify(tCRequestModelRepo, never()).getSMSTypeID(anyString());
		verify(tCRequestModelRepo, never()).getSMSTemplateID(anyInt());
		verify(restTemplate, never()).exchange(anyString(), any(), any(), eq(String.class));
	}

	@Test
	public void testSmsSenderGateway2_MultipleTemplates() {
		when(tCRequestModelRepo.getSMSTypeID(smsType)).thenReturn(1);
		when(tCRequestModelRepo.getSMSTemplateID(1)).thenReturn(new ArrayList<>(List.of(1, 2)));

		int result = smsGatewayServiceImpl.smsSenderGateway2(smsType, object, Authorization, benregID, createdBy,
				diagnosis);

		assertEquals(0, result);
		verify(tCRequestModelRepo, times(1)).getSMSTypeID(smsType);
		verify(tCRequestModelRepo, times(1)).getSMSTemplateID(1);
		verify(restTemplate, never()).exchange(anyString(), any(), any(), eq(String.class));
	}

	@Test
	public void testCreateSMSRequest_Schedule() {
		when(tCRequestModelRepo.getSMSTypeID("schedule")).thenReturn(1);
		when(tCRequestModelRepo.getSMSTemplateID(1)).thenReturn(new ArrayList<>(Arrays.asList(1)));

		String result = smsGatewayServiceImpl.createSMSRequest("schedule", benRegID, specializationID, tMRequestID,
				tMRequestCancelID, createdBy, tcDate, tcPreviousDate);

		assertNotNull(result);
		SmsRequestOBJ[] objArray = new Gson().fromJson(result, SmsRequestOBJ[].class);
		assertEquals(1, objArray.length);
		assertEquals(1, objArray[0].getSmsTemplateID());
		assertEquals(benRegID, objArray[0].getBeneficiaryRegID());
		assertEquals(specializationID, objArray[0].getSpecializationID());
		assertEquals("schedule", objArray[0].getSmsType());
		assertEquals(createdBy, objArray[0].getCreatedBy());
		assertEquals(tcDate, objArray[0].getTcDate());
		assertEquals(tcPreviousDate, objArray[0].getTcPreviousDate());
	}

	@Test
	public void testCreateSMSRequest_Cancel() {
		when(tCRequestModelRepo.getSMSTypeID("cancel")).thenReturn(2);
		when(tCRequestModelRepo.getSMSTemplateID(2)).thenReturn(new ArrayList<>(Arrays.asList(2)));

		String result = smsGatewayServiceImpl.createSMSRequest("cancel", benRegID, specializationID, tMRequestID,
				tMRequestCancelID, createdBy, tcDate, tcPreviousDate);

		assertNotNull(result);
		SmsRequestOBJ[] objArray = new Gson().fromJson(result, SmsRequestOBJ[].class);
		assertEquals(1, objArray.length);
		assertEquals(2, objArray[0].getSmsTemplateID());
		assertEquals(benRegID, objArray[0].getBeneficiaryRegID());
		assertEquals(specializationID, objArray[0].getSpecializationID());
		assertEquals("cancel", objArray[0].getSmsType());
		assertEquals(createdBy, objArray[0].getCreatedBy());
		assertEquals(tcDate, objArray[0].getTcDate());
		assertEquals(tcPreviousDate, objArray[0].getTcPreviousDate());
	}

	@Test
	public void testCreateSMSRequest_ReSchedule() {
		when(tCRequestModelRepo.getSMSTypeID("reSchedule")).thenReturn(3);
		when(tCRequestModelRepo.getSMSTemplateID(3)).thenReturn(new ArrayList<>(Arrays.asList(3)));

		String result = smsGatewayServiceImpl.createSMSRequest("reSchedule", benRegID, specializationID, tMRequestID,
				tMRequestCancelID, createdBy, tcDate, tcPreviousDate);

		assertNotNull(result);
		SmsRequestOBJ[] objArray = new Gson().fromJson(result, SmsRequestOBJ[].class);
		assertEquals(1, objArray.length);
		assertEquals(3, objArray[0].getSmsTemplateID());
		assertEquals(benRegID, objArray[0].getBeneficiaryRegID());
		assertEquals(specializationID, objArray[0].getSpecializationID());
		assertEquals("reSchedule", objArray[0].getSmsType());
		assertEquals(createdBy, objArray[0].getCreatedBy());
		assertEquals(tcDate, objArray[0].getTcDate());
		assertEquals(tcPreviousDate, objArray[0].getTcPreviousDate());
	}

	@Test
	public void testCreateSMSRequest_InvalidType() {
		String result = smsGatewayServiceImpl.createSMSRequest("invalidType", benRegID, specializationID, tMRequestID,
				tMRequestCancelID, createdBy, tcDate, tcPreviousDate);

		assertNull(result);
	}

	@Test
	public void testCreateSMSRequest_MultipleTemplates() {
		when(tCRequestModelRepo.getSMSTypeID("schedule")).thenReturn(1);
		when(tCRequestModelRepo.getSMSTemplateID(1)).thenReturn(new ArrayList<>(Arrays.asList(1, 2)));

		String result = smsGatewayServiceImpl.createSMSRequest("schedule", benRegID, specializationID, tMRequestID,
				tMRequestCancelID, createdBy, tcDate, tcPreviousDate);

		assertNull(result);
	}
}
