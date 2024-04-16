package com.iemr.hwc.service.tele_consultation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.iemr.hwc.data.quickConsultation.PrescribedDrugDetail;
import com.iemr.hwc.repo.tc_consultation.TCRequestModelRepo;

@ExtendWith(MockitoExtension.class)
class SMSGatewayServiceImplTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SMSGatewayServiceImpl sMSGatewayServiceImpl;

    @Mock
    private TCRequestModelRepo tCRequestModelRepo;

    @Test
    void testSmsSenderGateway() {
        // Arrange, Act and Assert
        assertEquals(0, sMSGatewayServiceImpl.smsSenderGateway("Sms Type", 1L, 1, 1L, 1L, "Jan 1, 2020 8:00am GMT+0100",
                "2020-03-01", "2020-03-01", "JaneDoe"));
        assertEquals(0, sMSGatewayServiceImpl.smsSenderGateway(null, 1L, 1, 1L, 1L, "Jan 1, 2020 8:00am GMT+0100",
                "2020-03-01", "2020-03-01", "JaneDoe"));
    }

    @Test
    void testSmsSenderGateway2() {
        // Arrange
        when(tCRequestModelRepo.getSMSTemplateID(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(tCRequestModelRepo.getSMSTypeID(Mockito.<String>any())).thenReturn(1);
        int actualSmsSenderGatewayResult = sMSGatewayServiceImpl.smsSenderGateway("cancel", 1L, 1, 1L, 1L,
                "Jan 1, 2020 8:00am GMT+0100", "2020-03-01", "2020-03-01", "JaneDoe");
        verify(tCRequestModelRepo).getSMSTemplateID(isA(Integer.class));
        verify(tCRequestModelRepo).getSMSTypeID(eq("${cancel}"));
        assertEquals(0, actualSmsSenderGatewayResult);
    }

    @Test
    void testSmsSenderGateway3() throws RestClientException {
        // Arrange
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), isA(Object[].class))).thenReturn(null);

        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        when(tCRequestModelRepo.getSMSTemplateID(Mockito.<Integer>any())).thenReturn(integerList);
        when(tCRequestModelRepo.getSMSTypeID(Mockito.<String>any())).thenReturn(1);

        // Act
        int actualSmsSenderGatewayResult = sMSGatewayServiceImpl.smsSenderGateway("cancel", 1L, 1, 1L, 1L,
                "Jan 1, 2020 8:00am GMT+0100", "2020-03-01", "2020-03-01", "JaneDoe");

        verify(tCRequestModelRepo).getSMSTemplateID(isA(Integer.class));
        verify(tCRequestModelRepo).getSMSTypeID(eq("${cancel}"));
        verify(restTemplate).exchange(eq("${sendSMSUrl}"), isA(HttpMethod.class), isA(HttpEntity.class), isA(Class.class),
                isA(Object[].class));
        assertEquals(0, actualSmsSenderGatewayResult);
    }

   
    @Test
    void testSmsSenderGateway4() throws RestClientException {
        // Arrange
        ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn("Body");
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), isA(Object[].class))).thenReturn(responseEntity);

        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        when(tCRequestModelRepo.getSMSTemplateID(Mockito.<Integer>any())).thenReturn(integerList);
        when(tCRequestModelRepo.getSMSTypeID(Mockito.<String>any())).thenReturn(1);

        // Act
        int actualSmsSenderGatewayResult = sMSGatewayServiceImpl.smsSenderGateway("cancel", 1L, 1, 1L, 1L,
                "Jan 1, 2020 8:00am GMT+0100", "2020-03-01", "2020-03-01", "JaneDoe");

        // Assert
        verify(tCRequestModelRepo).getSMSTemplateID(isA(Integer.class));
        verify(tCRequestModelRepo).getSMSTypeID(eq("${cancel}"));
        verify(responseEntity).getBody();
        verify(restTemplate).exchange(eq("${sendSMSUrl}"), isA(HttpMethod.class), isA(HttpEntity.class), isA(Class.class),
                isA(Object[].class));
        assertEquals(0, actualSmsSenderGatewayResult);
    }

    @Test
    void testSmsSenderGateway5() throws RestClientException {
        // Arrange
        ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn("https://example.org/example");
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), isA(Object[].class))).thenReturn(responseEntity);

        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        when(tCRequestModelRepo.getSMSTemplateID(Mockito.<Integer>any())).thenReturn(integerList);
        when(tCRequestModelRepo.getSMSTypeID(Mockito.<String>any())).thenReturn(1);

        // Act
        int actualSmsSenderGatewayResult = sMSGatewayServiceImpl.smsSenderGateway("cancel", 1L, 1, 1L, 1L,
                "Jan 1, 2020 8:00am GMT+0100", "2020-03-01", "2020-03-01", "JaneDoe");

        // Assert
        verify(tCRequestModelRepo).getSMSTemplateID(isA(Integer.class));
        verify(tCRequestModelRepo).getSMSTypeID(eq("${cancel}"));
        verify(responseEntity).getBody();
        verify(restTemplate).exchange(eq("${sendSMSUrl}"), isA(HttpMethod.class), isA(HttpEntity.class), isA(Class.class),
                isA(Object[].class));
        assertEquals(0, actualSmsSenderGatewayResult);
    }

    @Test
    void testSmsSenderGateway6() throws RestClientException {
        // Arrange
        ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(-1367724422);
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), isA(Object[].class))).thenReturn(responseEntity);

        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        when(tCRequestModelRepo.getSMSTemplateID(Mockito.<Integer>any())).thenReturn(integerList);
        when(tCRequestModelRepo.getSMSTypeID(Mockito.<String>any())).thenReturn(1);

        // Act
        int actualSmsSenderGatewayResult = sMSGatewayServiceImpl.smsSenderGateway("cancel", 1L, 1, 1L, 1L,
                "Jan 1, 2020 8:00am GMT+0100", "2020-03-01", "2020-03-01", "JaneDoe");

        // Assert
        verify(tCRequestModelRepo).getSMSTemplateID(isA(Integer.class));
        verify(tCRequestModelRepo).getSMSTypeID(eq("${cancel}"));
        verify(responseEntity).getBody();
        verify(restTemplate).exchange(eq("${sendSMSUrl}"), isA(HttpMethod.class), isA(HttpEntity.class), isA(Class.class),
                isA(Object[].class));
        assertEquals(0, actualSmsSenderGatewayResult);
    }

    @Test
    void testSmsSenderGateway7() throws RestClientException {
        // Arrange
        ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn("Exception during sending ");
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), isA(Object[].class))).thenReturn(responseEntity);

        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        when(tCRequestModelRepo.getSMSTemplateID(Mockito.<Integer>any())).thenReturn(integerList);
        when(tCRequestModelRepo.getSMSTypeID(Mockito.<String>any())).thenReturn(1);

        // Act
        int actualSmsSenderGatewayResult = sMSGatewayServiceImpl.smsSenderGateway("cancel", 1L, 1, 1L, 1L,
                "Jan 1, 2020 8:00am GMT+0100", "2020-03-01", "2020-03-01", "JaneDoe");

        // Assert
        verify(tCRequestModelRepo).getSMSTemplateID(isA(Integer.class));
        verify(tCRequestModelRepo).getSMSTypeID(eq("${cancel}"));
        verify(responseEntity).getBody();
        verify(restTemplate).exchange(eq("${sendSMSUrl}"), isA(HttpMethod.class), isA(HttpEntity.class), isA(Class.class),
                isA(Object[].class));
        assertEquals(0, actualSmsSenderGatewayResult);
    }

    @Test
    void testSmsSenderGateway8() throws RestClientException {
        // Arrange
        ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn("foo");
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), isA(Object[].class))).thenReturn(responseEntity);

        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        when(tCRequestModelRepo.getSMSTemplateID(Mockito.<Integer>any())).thenReturn(integerList);
        when(tCRequestModelRepo.getSMSTypeID(Mockito.<String>any())).thenReturn(1);

        // Act
        int actualSmsSenderGatewayResult = sMSGatewayServiceImpl.smsSenderGateway("cancel", 1L, 1, 1L, 1L,
                "Jan 1, 2020 8:00am GMT+0100", "2020-03-01", "2020-03-01", "JaneDoe");

        // Assert
        verify(tCRequestModelRepo).getSMSTemplateID(isA(Integer.class));
        verify(tCRequestModelRepo).getSMSTypeID(eq("${cancel}"));
        verify(responseEntity).getBody();
        verify(restTemplate).exchange(eq("${sendSMSUrl}"), isA(HttpMethod.class), isA(HttpEntity.class), isA(Class.class),
                isA(Object[].class));
        assertEquals(0, actualSmsSenderGatewayResult);
    }

    @Test
    void testSmsSenderGateway9() throws RestClientException {
        // Arrange
        ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(null);
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), isA(Object[].class))).thenReturn(responseEntity);

        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        when(tCRequestModelRepo.getSMSTemplateID(Mockito.<Integer>any())).thenReturn(integerList);
        when(tCRequestModelRepo.getSMSTypeID(Mockito.<String>any())).thenReturn(1);

        // Act
        int actualSmsSenderGatewayResult = sMSGatewayServiceImpl.smsSenderGateway("cancel", 1L, 1, 1L, 1L,
                "Jan 1, 2020 8:00am GMT+0100", "2020-03-01", "2020-03-01", "JaneDoe");

        // Assert
        verify(tCRequestModelRepo).getSMSTemplateID(isA(Integer.class));
        verify(tCRequestModelRepo).getSMSTypeID(eq("${cancel}"));
        verify(responseEntity).getBody();
        verify(restTemplate).exchange(eq("${sendSMSUrl}"), isA(HttpMethod.class), isA(HttpEntity.class), isA(Class.class),
                isA(Object[].class));
        assertEquals(0, actualSmsSenderGatewayResult);
    }

    @Test
    void testSmsSenderGateway10() throws RestClientException {
        // Arrange
        ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn("42");
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), isA(Object[].class))).thenReturn(responseEntity);

        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        when(tCRequestModelRepo.getSMSTemplateID(Mockito.<Integer>any())).thenReturn(integerList);
        when(tCRequestModelRepo.getSMSTypeID(Mockito.<String>any())).thenReturn(1);

        // Act
        int actualSmsSenderGatewayResult = sMSGatewayServiceImpl.smsSenderGateway("cancel", 1L, 1, 1L, 1L,
                "Jan 1, 2020 8:00am GMT+0100", "2020-03-01", "2020-03-01", "JaneDoe");

        // Assert
        verify(tCRequestModelRepo).getSMSTemplateID(isA(Integer.class));
        verify(tCRequestModelRepo).getSMSTypeID(eq("${cancel}"));
        verify(responseEntity).getBody();
        verify(restTemplate).exchange(eq("${sendSMSUrl}"), isA(HttpMethod.class), isA(HttpEntity.class), isA(Class.class),
                isA(Object[].class));
        assertEquals(0, actualSmsSenderGatewayResult);
    }

    @Test
    void testSmsSenderGateway11() throws RestClientException {
        // Arrange
        ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn("");
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), isA(Object[].class))).thenReturn(responseEntity);

        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        when(tCRequestModelRepo.getSMSTemplateID(Mockito.<Integer>any())).thenReturn(integerList);
        when(tCRequestModelRepo.getSMSTypeID(Mockito.<String>any())).thenReturn(1);

        // Act
        int actualSmsSenderGatewayResult = sMSGatewayServiceImpl.smsSenderGateway("cancel", 1L, 1, 1L, 1L,
                "Jan 1, 2020 8:00am GMT+0100", "2020-03-01", "2020-03-01", "JaneDoe");

        // Assert
        verify(tCRequestModelRepo).getSMSTemplateID(isA(Integer.class));
        verify(tCRequestModelRepo).getSMSTypeID(eq("${cancel}"));
        verify(responseEntity).getBody();
        verify(restTemplate).exchange(eq("${sendSMSUrl}"), isA(HttpMethod.class), isA(HttpEntity.class), isA(Class.class),
                isA(Object[].class));
        assertEquals(0, actualSmsSenderGatewayResult);
    }

    @Test
    void testSmsSenderGateway12() {
        // Arrange
        when(tCRequestModelRepo.getSMSTypeID(Mockito.<String>any())).thenReturn(null);

        // Act
        int actualSmsSenderGatewayResult = sMSGatewayServiceImpl.smsSenderGateway("cancel", 1L, 1, 1L, 1L,
                "Jan 1, 2020 8:00am GMT+0100", "2020-03-01", "2020-03-01", "JaneDoe");

        // Assert
        verify(tCRequestModelRepo).getSMSTypeID(eq("${cancel}"));
        assertEquals(0, actualSmsSenderGatewayResult);
    }

    @Test
    void testSmsSenderGateway13() throws RestClientException {
        // Arrange
        ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn("Body");
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), isA(Object[].class))).thenReturn(responseEntity);

        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        when(tCRequestModelRepo.getSMSTemplateID(Mockito.<Integer>any())).thenReturn(integerList);
        when(tCRequestModelRepo.getSMSTypeID(Mockito.<String>any())).thenReturn(1);

        // Act
        int actualSmsSenderGatewayResult = sMSGatewayServiceImpl.smsSenderGateway("cancel", 1L, 1, 1L, 1L, null,
                "2020-03-01", "2020-03-01", "JaneDoe");

        // Assert
        verify(tCRequestModelRepo).getSMSTemplateID(isA(Integer.class));
        verify(tCRequestModelRepo).getSMSTypeID(eq("${cancel}"));
        verify(responseEntity).getBody();
        verify(restTemplate).exchange(eq("${sendSMSUrl}"), isA(HttpMethod.class), isA(HttpEntity.class), isA(Class.class),
                isA(Object[].class));
        assertEquals(0, actualSmsSenderGatewayResult);
    }

    @Test
    void testSmsSenderGateway14() throws RestClientException {
        // Arrange
        ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn("Body");
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), isA(Object[].class))).thenReturn(responseEntity);

        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        when(tCRequestModelRepo.getSMSTemplateID(Mockito.<Integer>any())).thenReturn(integerList);
        when(tCRequestModelRepo.getSMSTypeID(Mockito.<String>any())).thenReturn(1);

        // Act
        int actualSmsSenderGatewayResult = sMSGatewayServiceImpl.smsSenderGateway("cancel", 1L, 1, 1L, 1L, "", "2020-03-01",
                "2020-03-01", "JaneDoe");

        // Assert
        verify(tCRequestModelRepo).getSMSTemplateID(isA(Integer.class));
        verify(tCRequestModelRepo).getSMSTypeID(eq("${cancel}"));
        verify(responseEntity).getBody();
        verify(restTemplate).exchange(eq("${sendSMSUrl}"), isA(HttpMethod.class), isA(HttpEntity.class), isA(Class.class),
                isA(Object[].class));
        assertEquals(0, actualSmsSenderGatewayResult);
    }

    @Test
    void testSmsSenderGateway15() throws RestClientException {
        // Arrange
        ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn("Body");
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), isA(Object[].class))).thenReturn(responseEntity);

        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        when(tCRequestModelRepo.getSMSTemplateID(Mockito.<Integer>any())).thenReturn(integerList);
        when(tCRequestModelRepo.getSMSTypeID(Mockito.<String>any())).thenReturn(1);

        // Act
        int actualSmsSenderGatewayResult = sMSGatewayServiceImpl.smsSenderGateway("reSchedule", 1L, 1, 1L, 1L,
                "Jan 1, 2020 8:00am GMT+0100", "2020-03-01", "2020-03-01", "JaneDoe");

        // Assert
        verify(tCRequestModelRepo).getSMSTemplateID(isA(Integer.class));
        verify(tCRequestModelRepo).getSMSTypeID(eq("${reSchedule}"));
        verify(responseEntity).getBody();
        verify(restTemplate).exchange(eq("${sendSMSUrl}"), isA(HttpMethod.class), isA(HttpEntity.class), isA(Class.class),
                isA(Object[].class));
        assertEquals(0, actualSmsSenderGatewayResult);
    }

    @Test
    void testSmsSenderGateway16() throws RestClientException {
        // Arrange
        ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn("Body");
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), isA(Object[].class))).thenReturn(responseEntity);

        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        when(tCRequestModelRepo.getSMSTemplateID(Mockito.<Integer>any())).thenReturn(integerList);
        when(tCRequestModelRepo.getSMSTypeID(Mockito.<String>any())).thenReturn(1);

        // Act
        int actualSmsSenderGatewayResult = sMSGatewayServiceImpl.smsSenderGateway("schedule", 1L, 1, 1L, 1L,
                "Jan 1, 2020 8:00am GMT+0100", "2020-03-01", "2020-03-01", "JaneDoe");

        // Assert
        verify(tCRequestModelRepo).getSMSTemplateID(isA(Integer.class));
        verify(tCRequestModelRepo).getSMSTypeID(eq("${schedule}"));
        verify(responseEntity).getBody();
        verify(restTemplate).exchange(eq("${sendSMSUrl}"), isA(HttpMethod.class), isA(HttpEntity.class), isA(Class.class),
                isA(Object[].class));
        assertEquals(0, actualSmsSenderGatewayResult);
    }

    @Test
    void testSmsSenderGateway22() {
        // Arrange
        ArrayList<PrescribedDrugDetail> object = new ArrayList<>();

        // Act and Assert
        assertEquals(0, sMSGatewayServiceImpl.smsSenderGateway2("Sms Type", object, "JaneDoe", 1L,
                "Jan 1, 2020 8:00am GMT+0100", new ArrayList<>()));
    }

    @Test
    void testSmsSenderGateway23() {
        // Arrange
        when(tCRequestModelRepo.getSMSTemplateID(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(tCRequestModelRepo.getSMSTypeID(Mockito.<String>any())).thenReturn(1);
        ArrayList<PrescribedDrugDetail> object = new ArrayList<>();

        // Act
        int actualSmsSenderGateway2Result = sMSGatewayServiceImpl.smsSenderGateway2("prescription", object, "JaneDoe", 1L,
                "Jan 1, 2020 8:00am GMT+0100", new ArrayList<>());

        // Assert
        verify(tCRequestModelRepo).getSMSTemplateID(isA(Integer.class));
        verify(tCRequestModelRepo).getSMSTypeID(eq("${prescription}"));
        assertEquals(0, actualSmsSenderGateway2Result);
    }

    @Test
    void testSmsSenderGateway24() throws RestClientException {
        // Arrange
        ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(null);
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), isA(Object[].class))).thenReturn(responseEntity);

        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        when(tCRequestModelRepo.getSMSTemplateID(Mockito.<Integer>any())).thenReturn(integerList);
        when(tCRequestModelRepo.getSMSTypeID(Mockito.<String>any())).thenReturn(1);
        ArrayList<PrescribedDrugDetail> object = new ArrayList<>();

        // Act
        int actualSmsSenderGateway2Result = sMSGatewayServiceImpl.smsSenderGateway2("prescription", object, "JaneDoe", 1L,
                "Jan 1, 2020 8:00am GMT+0100", new ArrayList<>());

        // Assert
        verify(tCRequestModelRepo).getSMSTemplateID(isA(Integer.class));
        verify(tCRequestModelRepo).getSMSTypeID(eq("${prescription}"));
        verify(responseEntity).getBody();
        verify(restTemplate).exchange(eq("${sendSMSUrl}"), isA(HttpMethod.class), isA(HttpEntity.class), isA(Class.class),
                isA(Object[].class));
        assertEquals(0, actualSmsSenderGateway2Result);
    }

    @Test
    void testSmsSenderGateway25() {
        // Arrange
        when(tCRequestModelRepo.getSMSTypeID(Mockito.<String>any())).thenReturn(null);
        ArrayList<PrescribedDrugDetail> object = new ArrayList<>();

        // Act
        int actualSmsSenderGateway2Result = sMSGatewayServiceImpl.smsSenderGateway2("prescription", object, "JaneDoe", 1L,
                "Jan 1, 2020 8:00am GMT+0100", new ArrayList<>());

        // Assert
        verify(tCRequestModelRepo).getSMSTypeID(eq("${prescription}"));
        assertEquals(0, actualSmsSenderGateway2Result);
    }

    @Test
    void testCreateSMSRequest() {
        // Arrange, Act and Assert
        assertNull(sMSGatewayServiceImpl.createSMSRequest("Sms Type", 1L, 1, 1L, 1L, "Jan 1, 2020 8:00am GMT+0100",
                "2020-03-01", "2020-03-01"));
    }

    @Test
    void testCreateSMSRequest2() {
        // Arrange
        when(tCRequestModelRepo.getSMSTemplateID(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(tCRequestModelRepo.getSMSTypeID(Mockito.<String>any())).thenReturn(1);

        // Act
        String actualCreateSMSRequestResult = sMSGatewayServiceImpl.createSMSRequest("cancel", 1L, 1, 1L, 1L,
                "Jan 1, 2020 8:00am GMT+0100", "2020-03-01", "2020-03-01");

        // Assert
        verify(tCRequestModelRepo).getSMSTemplateID(isA(Integer.class));
        verify(tCRequestModelRepo).getSMSTypeID(eq("${cancel}"));
        assertNull(actualCreateSMSRequestResult);
    }

    @Test
    void testCreateSMSRequest3() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        when(tCRequestModelRepo.getSMSTemplateID(Mockito.<Integer>any())).thenReturn(integerList);
        when(tCRequestModelRepo.getSMSTypeID(Mockito.<String>any())).thenReturn(1);

        // Act
        String actualCreateSMSRequestResult = sMSGatewayServiceImpl.createSMSRequest("cancel", 1L, 1, 1L, 1L,
                "Jan 1, 2020 8:00am GMT+0100", "2020-03-01", "2020-03-01");

        // Assert
        verify(tCRequestModelRepo).getSMSTemplateID(isA(Integer.class));
        verify(tCRequestModelRepo).getSMSTypeID(eq("${cancel}"));
        assertEquals(
                "[{\"beneficiaryRegID\":1,\"smsTemplateID\":2,\"specializationID\":1,\"smsTypeTM\":\"cancel\",\"createdBy\":\"Jan"
                        + " 1, 2020 8:00am GMT+0100\",\"tcDate\":\"2020-03-01\",\"tcPreviousDate\":\"2020-03-01\"}]",
                actualCreateSMSRequestResult);
    }

    @Test
    void testCreateSMSRequest4() {
        // Arrange
        when(tCRequestModelRepo.getSMSTemplateID(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(tCRequestModelRepo.getSMSTypeID(Mockito.<String>any())).thenReturn(1);

        // Act
        String actualCreateSMSRequestResult = sMSGatewayServiceImpl.createSMSRequest("reSchedule", 1L, 1, 1L, 1L,
                "Jan 1, 2020 8:00am GMT+0100", "2020-03-01", "2020-03-01");

        // Assert
        verify(tCRequestModelRepo).getSMSTemplateID(isA(Integer.class));
        verify(tCRequestModelRepo).getSMSTypeID(eq("${reSchedule}"));
        assertNull(actualCreateSMSRequestResult);
    }

    @Test
    void testCreateSMSRequest5() {
        // Arrange
        when(tCRequestModelRepo.getSMSTemplateID(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(tCRequestModelRepo.getSMSTypeID(Mockito.<String>any())).thenReturn(1);

        // Act
        String actualCreateSMSRequestResult = sMSGatewayServiceImpl.createSMSRequest("schedule", 1L, 1, 1L, 1L,
                "Jan 1, 2020 8:00am GMT+0100", "2020-03-01", "2020-03-01");

        // Assert
        verify(tCRequestModelRepo).getSMSTemplateID(isA(Integer.class));
        verify(tCRequestModelRepo).getSMSTypeID(eq("${schedule}"));
        assertNull(actualCreateSMSRequestResult);
    }

    @Test
    void testCreateSMSRequest6() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.addAll(new ArrayList<>());
        integerList.add(2);
        when(tCRequestModelRepo.getSMSTemplateID(Mockito.<Integer>any())).thenReturn(integerList);
        when(tCRequestModelRepo.getSMSTypeID(Mockito.<String>any())).thenReturn(1);

        // Act
        String actualCreateSMSRequestResult = sMSGatewayServiceImpl.createSMSRequest("cancel", 1L, 1, 1L, 1L,
                "Jan 1, 2020 8:00am GMT+0100", "2020-03-01", "2020-03-01");

        // Assert
        verify(tCRequestModelRepo).getSMSTemplateID(isA(Integer.class));
        verify(tCRequestModelRepo).getSMSTypeID(eq("${cancel}"));
        assertEquals(
                "[{\"beneficiaryRegID\":1,\"smsTemplateID\":2,\"specializationID\":1,\"smsTypeTM\":\"cancel\",\"createdBy\":\"Jan"
                        + " 1, 2020 8:00am GMT+0100\",\"tcDate\":\"2020-03-01\",\"tcPreviousDate\":\"2020-03-01\"}]",
                actualCreateSMSRequestResult);
    }

    @Test
    void testCreateSMSRequest7() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        when(tCRequestModelRepo.getSMSTemplateID(Mockito.<Integer>any())).thenReturn(integerList);
        when(tCRequestModelRepo.getSMSTypeID(Mockito.<String>any())).thenReturn(1);

        // Act
        String actualCreateSMSRequestResult = sMSGatewayServiceImpl.createSMSRequest("cancel", 1L, 1, 1L, 1L, null,
                "2020-03-01", "2020-03-01");

        // Assert
        verify(tCRequestModelRepo).getSMSTemplateID(isA(Integer.class));
        verify(tCRequestModelRepo).getSMSTypeID(eq("${cancel}"));
        assertEquals(
                "[{\"beneficiaryRegID\":1,\"smsTemplateID\":2,\"specializationID\":1,\"smsTypeTM\":\"cancel\",\"tcDate\":\"2020-03"
                        + "-01\",\"tcPreviousDate\":\"2020-03-01\"}]",
                actualCreateSMSRequestResult);
    }

    @Test
    void testCreateSMSRequest8() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        when(tCRequestModelRepo.getSMSTemplateID(Mockito.<Integer>any())).thenReturn(integerList);
        when(tCRequestModelRepo.getSMSTypeID(Mockito.<String>any())).thenReturn(1);

        // Act
        String actualCreateSMSRequestResult = sMSGatewayServiceImpl.createSMSRequest("cancel", 1L, 1, 1L, 1L, "",
                "2020-03-01", "2020-03-01");

        // Assert
        verify(tCRequestModelRepo).getSMSTemplateID(isA(Integer.class));
        verify(tCRequestModelRepo).getSMSTypeID(eq("${cancel}"));
        assertEquals(
                "[{\"beneficiaryRegID\":1,\"smsTemplateID\":2,\"specializationID\":1,\"smsTypeTM\":\"cancel\",\"createdBy\":\"\","
                        + "\"tcDate\":\"2020-03-01\",\"tcPreviousDate\":\"2020-03-01\"}]",
                actualCreateSMSRequestResult);
    }

    @Test
    void testSendSMS() throws RestClientException {
        // Arrange
        ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn("Body");
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), isA(Object[].class))).thenReturn(responseEntity);

        // Act
        String actualSendSMSResult = sMSGatewayServiceImpl.sendSMS("Request", "JaneDoe");

        // Assert
        verify(responseEntity).getBody();
        verify(restTemplate).exchange(eq("${sendSMSUrl}"), isA(HttpMethod.class), isA(HttpEntity.class), isA(Class.class),
                isA(Object[].class));
        assertEquals("Body", actualSendSMSResult);
    }
}
