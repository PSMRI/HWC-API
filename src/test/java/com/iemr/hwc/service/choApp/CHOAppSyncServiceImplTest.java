package com.iemr.hwc.service.choApp;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;

import com.iemr.hwc.data.choApp.OutreachActivity;
import com.iemr.hwc.data.choApp.UserActivityLogs;
import com.iemr.hwc.data.doctor.PrescriptionTemplates;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.choApp.OutreachActivityRepo;
import com.iemr.hwc.repo.choApp.UserActivityLogsRepo;
import com.iemr.hwc.repo.doctor.PrescriptionTemplatesRepo;
import com.iemr.hwc.repo.nurse.BenAnthropometryRepo;
import com.iemr.hwc.repo.nurse.BenPhysicalVitalRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.generalOPD.GeneralOPDServiceImpl;
import com.iemr.hwc.utils.request.SyncSearchRequest;

@ExtendWith(MockitoExtension.class)
class CHOAppSyncServiceImplTest {
    @Mock
    private BenAnthropometryRepo benAnthropometryRepo;

    @Mock
    private BenChiefComplaintRepo benChiefComplaintRepo;

    @Mock
    private BenPhysicalVitalRepo benPhysicalVitalRepo;

    @Mock
    private BenVisitDetailRepo benVisitDetailRepo;

    @Mock
    private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;

    @InjectMocks
    private CHOAppSyncServiceImpl cHOAppSyncServiceImpl;

    @Mock
    private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;

    @Mock
    private CommonNurseServiceImpl commonNurseServiceImpl;

    @Mock
    private GeneralOPDServiceImpl generalOPDServiceImpl;

    @Mock
    private OutreachActivityRepo outreachActivityRepo;

    @Mock
    private PrescriptionTemplatesRepo prescriptionTemplatesRepo;

    @Mock
    private UserActivityLogsRepo userActivityLogsRepo;

    @Test
    void testRegisterCHOAPPBeneficiary() {
        // Arrange and Act
        ResponseEntity<String> actualRegisterCHOAPPBeneficiaryResult = cHOAppSyncServiceImpl
                .registerCHOAPPBeneficiary("Coming Request", "JaneDoe");

        // Assert
        assertEquals(
                "{\"statusCode\":500,\"errorMessage\":\"Error registering the beneficiary.Encountered exception com.diffblue"
                        + ".cover.sandbox.execution.ForbiddenByPolicyException: Sandboxing policy violation. Reason: to access"
                        + " the network\",\"status\":\"Error registering the beneficiary.Encountered exception com.diffblue.cover"
                        + ".sandbox.execution.ForbiddenByPolicyException: Sandboxing policy violation. Reason: to access the"
                        + " network\"}",
                actualRegisterCHOAPPBeneficiaryResult.getBody());
        assertEquals(1, actualRegisterCHOAPPBeneficiaryResult.getHeaders().size());
        assertEquals(500, actualRegisterCHOAPPBeneficiaryResult.getStatusCodeValue());
    }

    @Test
    void testGetBeneficiaryByVillageIDAndLastModifiedDate() {
        // Arrange
        SyncSearchRequest villageIDAndLastSyncDate = new SyncSearchRequest();
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualBeneficiaryByVillageIDAndLastModifiedDate = cHOAppSyncServiceImpl
                .getBeneficiaryByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        assertEquals(
                "{\"data\":null,\"statusCode\":400,\"errorMessage\":\"Bad request. Incomplete request body - Either villageIDs"
                        + " or lastSyncDate missing.\",\"status\":\"Bad request. Incomplete request body - Either villageIDs or"
                        + " lastSyncDate missing.\"}",
                actualBeneficiaryByVillageIDAndLastModifiedDate.getBody());
        assertEquals(1, actualBeneficiaryByVillageIDAndLastModifiedDate.getHeaders().size());
        assertEquals(400, actualBeneficiaryByVillageIDAndLastModifiedDate.getStatusCodeValue());
    }

    @Test
    void testGetBeneficiaryByVillageIDAndLastModifiedDate2() {
        // Arrange
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(new ArrayList<>());
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualBeneficiaryByVillageIDAndLastModifiedDate = cHOAppSyncServiceImpl
                .getBeneficiaryByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals(
                "{\"data\":null,\"statusCode\":400,\"errorMessage\":\"Bad request. Incomplete request body - Either villageIDs"
                        + " or lastSyncDate missing.\",\"status\":\"Bad request. Incomplete request body - Either villageIDs or"
                        + " lastSyncDate missing.\"}",
                actualBeneficiaryByVillageIDAndLastModifiedDate.getBody());
        assertEquals(1, actualBeneficiaryByVillageIDAndLastModifiedDate.getHeaders().size());
        assertEquals(400, actualBeneficiaryByVillageIDAndLastModifiedDate.getStatusCodeValue());
    }

    @Test
    void testGetBeneficiaryByVillageIDAndLastModifiedDate3() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getLastSyncDate()).thenReturn("2020-03-01");
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(integerList);
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualBeneficiaryByVillageIDAndLastModifiedDate = cHOAppSyncServiceImpl
                .getBeneficiaryByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate, atLeast(1)).getLastSyncDate();
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals("{\"data\":null,\"statusCode\":500,\"errorMessage\":\"Error searching beneficiaries to sync. Exception"
                        + " java.lang.IllegalArgumentException: Invalid format: \\\"2020-03-01\\\" is malformed at \\\"20-03-01\\\"\","
                        + "\"status\":\"Error searching beneficiaries to sync. Exception java.lang.IllegalArgumentException: Invalid"
                        + " format: \\\"2020-03-01\\\" is malformed at \\\"20-03-01\\\"\"}",
                actualBeneficiaryByVillageIDAndLastModifiedDate.getBody());
        assertEquals(1, actualBeneficiaryByVillageIDAndLastModifiedDate.getHeaders().size());
        assertEquals(500, actualBeneficiaryByVillageIDAndLastModifiedDate.getStatusCodeValue());
    }

    @Test
    void testGetBeneficiaryByVillageIDAndLastModifiedDate4() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getLastSyncDate()).thenReturn("Failed with generic error");
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(integerList);
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualBeneficiaryByVillageIDAndLastModifiedDate = cHOAppSyncServiceImpl
                .getBeneficiaryByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate, atLeast(1)).getLastSyncDate();
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals("{\"data\":null,\"statusCode\":500,\"errorMessage\":\"Error searching beneficiaries to sync. Exception"
                + " java.lang.IllegalArgumentException: Invalid format: \\\"Failed with generic error\\\"\",\"status\":\"Error"
                + " searching beneficiaries to sync. Exception java.lang.IllegalArgumentException: Invalid format: \\\"Failed"
                + " with generic error\\\"\"}", actualBeneficiaryByVillageIDAndLastModifiedDate.getBody());
        assertEquals(1, actualBeneficiaryByVillageIDAndLastModifiedDate.getHeaders().size());
        assertEquals(500, actualBeneficiaryByVillageIDAndLastModifiedDate.getStatusCodeValue());
    }

    @Test
    void testGetBeneficiaryByVillageIDAndLastModifiedDate5() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getLastSyncDate()).thenReturn(null);
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(integerList);
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualBeneficiaryByVillageIDAndLastModifiedDate = cHOAppSyncServiceImpl
                .getBeneficiaryByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate).getLastSyncDate();
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals(
                "{\"data\":null,\"statusCode\":400,\"errorMessage\":\"Bad request. Incomplete request body - Either villageIDs"
                        + " or lastSyncDate missing.\",\"status\":\"Bad request. Incomplete request body - Either villageIDs or"
                        + " lastSyncDate missing.\"}",
                actualBeneficiaryByVillageIDAndLastModifiedDate.getBody());
        assertEquals(1, actualBeneficiaryByVillageIDAndLastModifiedDate.getHeaders().size());
        assertEquals(400, actualBeneficiaryByVillageIDAndLastModifiedDate.getStatusCodeValue());
    }

    @Test
    void testGetBeneficiaryByVillageIDAndLastModifiedDate6() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getLastSyncDate()).thenReturn("SyncSearchRequest(lastSyncDate=");
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(integerList);
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualBeneficiaryByVillageIDAndLastModifiedDate = cHOAppSyncServiceImpl
                .getBeneficiaryByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate, atLeast(1)).getLastSyncDate();
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals("{\"data\":null,\"statusCode\":500,\"errorMessage\":\"Error searching beneficiaries to sync. Exception"
                        + " java.lang.IllegalArgumentException: Invalid format: \\\"SyncSearchRequest(lastSyncDate\\u003d\\\"\",\"status"
                        + "\":\"Error searching beneficiaries to sync. Exception java.lang.IllegalArgumentException: Invalid format:"
                        + " \\\"SyncSearchRequest(lastSyncDate\\u003d\\\"\"}",
                actualBeneficiaryByVillageIDAndLastModifiedDate.getBody());
        assertEquals(1, actualBeneficiaryByVillageIDAndLastModifiedDate.getHeaders().size());
        assertEquals(500, actualBeneficiaryByVillageIDAndLastModifiedDate.getStatusCodeValue());
    }

    @Test
    void testGetBeneficiaryByVillageIDAndLastModifiedDate7() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getLastSyncDate())
                .thenThrow(new ResourceAccessException("Failed with generic error"));
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(integerList);
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualBeneficiaryByVillageIDAndLastModifiedDate = cHOAppSyncServiceImpl
                .getBeneficiaryByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate).getLastSyncDate();
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals(
                "{\"data\":null,\"statusCode\":503,\"errorMessage\":\"Error establishing connection with Identity service."
                        + " \",\"status\":\"Error establishing connection with Identity service. \"}",
                actualBeneficiaryByVillageIDAndLastModifiedDate.getBody());
        assertEquals(1, actualBeneficiaryByVillageIDAndLastModifiedDate.getHeaders().size());
        assertEquals(503, actualBeneficiaryByVillageIDAndLastModifiedDate.getStatusCodeValue());
    }

    @Test
    void testGetBeneficiaryByVillageIDAndLastModifiedDate8() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getLastSyncDate())
                .thenThrow(new IllegalArgumentException("Failed with generic error"));
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(integerList);
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualBeneficiaryByVillageIDAndLastModifiedDate = cHOAppSyncServiceImpl
                .getBeneficiaryByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate).getLastSyncDate();
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals("{\"data\":null,\"statusCode\":500,\"errorMessage\":\"Error searching beneficiaries to sync. Exception"
                        + " java.lang.IllegalArgumentException: Failed with generic error\",\"status\":\"Error searching beneficiaries"
                        + " to sync. Exception java.lang.IllegalArgumentException: Failed with generic error\"}",
                actualBeneficiaryByVillageIDAndLastModifiedDate.getBody());
        assertEquals(1, actualBeneficiaryByVillageIDAndLastModifiedDate.getHeaders().size());
        assertEquals(500, actualBeneficiaryByVillageIDAndLastModifiedDate.getStatusCodeValue());
    }

    @Test
    void testCountBeneficiaryByVillageIDAndLastModifiedDate() {
        // Arrange
        SyncSearchRequest villageIDAndLastSyncDate = new SyncSearchRequest();
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualCountBeneficiaryByVillageIDAndLastModifiedDateResult = cHOAppSyncServiceImpl
                .countBeneficiaryByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        assertEquals(
                "{\"data\":null,\"statusCode\":400,\"errorMessage\":\"Bad request. Incomplete request body - Either villageIDs"
                        + " or lastSyncDate missing.\",\"status\":\"Bad request. Incomplete request body - Either villageIDs or"
                        + " lastSyncDate missing.\"}",
                actualCountBeneficiaryByVillageIDAndLastModifiedDateResult.getBody());
        assertEquals(1, actualCountBeneficiaryByVillageIDAndLastModifiedDateResult.getHeaders().size());
        assertEquals(400, actualCountBeneficiaryByVillageIDAndLastModifiedDateResult.getStatusCodeValue());
    }

    @Test
    void testCountBeneficiaryByVillageIDAndLastModifiedDate2() {
        // Arrange
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(new ArrayList<>());
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualCountBeneficiaryByVillageIDAndLastModifiedDateResult = cHOAppSyncServiceImpl
                .countBeneficiaryByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals(
                "{\"data\":null,\"statusCode\":400,\"errorMessage\":\"Bad request. Incomplete request body - Either villageIDs"
                        + " or lastSyncDate missing.\",\"status\":\"Bad request. Incomplete request body - Either villageIDs or"
                        + " lastSyncDate missing.\"}",
                actualCountBeneficiaryByVillageIDAndLastModifiedDateResult.getBody());
        assertEquals(1, actualCountBeneficiaryByVillageIDAndLastModifiedDateResult.getHeaders().size());
        assertEquals(400, actualCountBeneficiaryByVillageIDAndLastModifiedDateResult.getStatusCodeValue());
    }

    @Test
    void testCountBeneficiaryByVillageIDAndLastModifiedDate3() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getLastSyncDate()).thenReturn("2020-03-01");
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(integerList);
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualCountBeneficiaryByVillageIDAndLastModifiedDateResult = cHOAppSyncServiceImpl
                .countBeneficiaryByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate, atLeast(1)).getLastSyncDate();
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals(
                "{\"data\":null,\"statusCode\":500,\"errorMessage\":\"Error getting count of beneficiaries to sync. Exception"
                        + " java.lang.IllegalArgumentException: Invalid format: \\\"2020-03-01\\\" is malformed at \\\"20-03-01\\\"\","
                        + "\"status\":\"Error getting count of beneficiaries to sync. Exception java.lang.IllegalArgumentException:"
                        + " Invalid format: \\\"2020-03-01\\\" is malformed at \\\"20-03-01\\\"\"}",
                actualCountBeneficiaryByVillageIDAndLastModifiedDateResult.getBody());
        assertEquals(1, actualCountBeneficiaryByVillageIDAndLastModifiedDateResult.getHeaders().size());
        assertEquals(500, actualCountBeneficiaryByVillageIDAndLastModifiedDateResult.getStatusCodeValue());
    }

    @Test
    void testCountBeneficiaryByVillageIDAndLastModifiedDate4() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getLastSyncDate()).thenReturn("Failed with generic error");
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(integerList);
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualCountBeneficiaryByVillageIDAndLastModifiedDateResult = cHOAppSyncServiceImpl
                .countBeneficiaryByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate, atLeast(1)).getLastSyncDate();
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals(
                "{\"data\":null,\"statusCode\":500,\"errorMessage\":\"Error getting count of beneficiaries to sync. Exception"
                        + " java.lang.IllegalArgumentException: Invalid format: \\\"Failed with generic error\\\"\",\"status\":\"Error"
                        + " getting count of beneficiaries to sync. Exception java.lang.IllegalArgumentException: Invalid format:"
                        + " \\\"Failed with generic error\\\"\"}",
                actualCountBeneficiaryByVillageIDAndLastModifiedDateResult.getBody());
        assertEquals(1, actualCountBeneficiaryByVillageIDAndLastModifiedDateResult.getHeaders().size());
        assertEquals(500, actualCountBeneficiaryByVillageIDAndLastModifiedDateResult.getStatusCodeValue());
    }

    @Test
    void testCountBeneficiaryByVillageIDAndLastModifiedDate5() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getLastSyncDate()).thenReturn(null);
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(integerList);
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualCountBeneficiaryByVillageIDAndLastModifiedDateResult = cHOAppSyncServiceImpl
                .countBeneficiaryByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate).getLastSyncDate();
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals(
                "{\"data\":null,\"statusCode\":400,\"errorMessage\":\"Bad request. Incomplete request body - Either villageIDs"
                        + " or lastSyncDate missing.\",\"status\":\"Bad request. Incomplete request body - Either villageIDs or"
                        + " lastSyncDate missing.\"}",
                actualCountBeneficiaryByVillageIDAndLastModifiedDateResult.getBody());
        assertEquals(1, actualCountBeneficiaryByVillageIDAndLastModifiedDateResult.getHeaders().size());
        assertEquals(400, actualCountBeneficiaryByVillageIDAndLastModifiedDateResult.getStatusCodeValue());
    }

    @Test
    void testCountBeneficiaryByVillageIDAndLastModifiedDate6() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getLastSyncDate()).thenReturn("SyncSearchRequest(lastSyncDate=");
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(integerList);
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualCountBeneficiaryByVillageIDAndLastModifiedDateResult = cHOAppSyncServiceImpl
                .countBeneficiaryByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate, atLeast(1)).getLastSyncDate();
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals(
                "{\"data\":null,\"statusCode\":500,\"errorMessage\":\"Error getting count of beneficiaries to sync. Exception"
                        + " java.lang.IllegalArgumentException: Invalid format: \\\"SyncSearchRequest(lastSyncDate\\u003d\\\"\",\"status"
                        + "\":\"Error getting count of beneficiaries to sync. Exception java.lang.IllegalArgumentException: Invalid"
                        + " format: \\\"SyncSearchRequest(lastSyncDate\\u003d\\\"\"}",
                actualCountBeneficiaryByVillageIDAndLastModifiedDateResult.getBody());
        assertEquals(1, actualCountBeneficiaryByVillageIDAndLastModifiedDateResult.getHeaders().size());
        assertEquals(500, actualCountBeneficiaryByVillageIDAndLastModifiedDateResult.getStatusCodeValue());
    }

    @Test
    void testCountBeneficiaryByVillageIDAndLastModifiedDate7() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getLastSyncDate())
                .thenThrow(new ResourceAccessException("Failed with generic error"));
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(integerList);
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualCountBeneficiaryByVillageIDAndLastModifiedDateResult = cHOAppSyncServiceImpl
                .countBeneficiaryByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate).getLastSyncDate();
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals(
                "{\"data\":null,\"statusCode\":503,\"errorMessage\":\"Error establishing connection with Identity service."
                        + " \",\"status\":\"Error establishing connection with Identity service. \"}",
                actualCountBeneficiaryByVillageIDAndLastModifiedDateResult.getBody());
        assertEquals(1, actualCountBeneficiaryByVillageIDAndLastModifiedDateResult.getHeaders().size());
        assertEquals(503, actualCountBeneficiaryByVillageIDAndLastModifiedDateResult.getStatusCodeValue());
    }

    @Test
    void testCountBeneficiaryByVillageIDAndLastModifiedDate8() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getLastSyncDate())
                .thenThrow(new IllegalArgumentException("Failed with generic error"));
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(integerList);
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualCountBeneficiaryByVillageIDAndLastModifiedDateResult = cHOAppSyncServiceImpl
                .countBeneficiaryByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate).getLastSyncDate();
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals(
                "{\"data\":null,\"statusCode\":500,\"errorMessage\":\"Error getting count of beneficiaries to sync. Exception"
                        + " java.lang.IllegalArgumentException: Failed with generic error\",\"status\":\"Error getting count of"
                        + " beneficiaries to sync. Exception java.lang.IllegalArgumentException: Failed with generic error\"}",
                actualCountBeneficiaryByVillageIDAndLastModifiedDateResult.getBody());
        assertEquals(1, actualCountBeneficiaryByVillageIDAndLastModifiedDateResult.getHeaders().size());
        assertEquals(500, actualCountBeneficiaryByVillageIDAndLastModifiedDateResult.getStatusCodeValue());
    }

    @Test
    void testCountFlowRecordsByVillageIDAndLastModifiedDate() {
        // Arrange
        SyncSearchRequest villageIDAndLastSyncDate = new SyncSearchRequest();
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualCountFlowRecordsByVillageIDAndLastModifiedDateResult = cHOAppSyncServiceImpl
                .countFlowRecordsByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        assertEquals(
                "{\"data\":null,\"statusCode\":400,\"errorMessage\":\"Bad request. Incomplete request body - Either villageIDs"
                        + " or lastSyncDate missing.\",\"status\":\"Bad request. Incomplete request body - Either villageIDs or"
                        + " lastSyncDate missing.\"}",
                actualCountFlowRecordsByVillageIDAndLastModifiedDateResult.getBody());
        assertEquals(1, actualCountFlowRecordsByVillageIDAndLastModifiedDateResult.getHeaders().size());
        assertEquals(400, actualCountFlowRecordsByVillageIDAndLastModifiedDateResult.getStatusCodeValue());
    }

    @Test
    void testCountFlowRecordsByVillageIDAndLastModifiedDate2() {
        // Arrange
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(new ArrayList<>());
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualCountFlowRecordsByVillageIDAndLastModifiedDateResult = cHOAppSyncServiceImpl
                .countFlowRecordsByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals(
                "{\"data\":null,\"statusCode\":400,\"errorMessage\":\"Bad request. Incomplete request body - Either villageIDs"
                        + " or lastSyncDate missing.\",\"status\":\"Bad request. Incomplete request body - Either villageIDs or"
                        + " lastSyncDate missing.\"}",
                actualCountFlowRecordsByVillageIDAndLastModifiedDateResult.getBody());
        assertEquals(1, actualCountFlowRecordsByVillageIDAndLastModifiedDateResult.getHeaders().size());
        assertEquals(400, actualCountFlowRecordsByVillageIDAndLastModifiedDateResult.getStatusCodeValue());
    }

    @Test
    void testCountFlowRecordsByVillageIDAndLastModifiedDate3() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getLastSyncDate()).thenReturn("2020-03-01");
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(integerList);
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualCountFlowRecordsByVillageIDAndLastModifiedDateResult = cHOAppSyncServiceImpl
                .countFlowRecordsByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate, atLeast(1)).getLastSyncDate();
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals(
                "{\"data\":null,\"statusCode\":400,\"errorMessage\":\"Encountered exception. Exception java.lang.IllegalArgu"
                        + "mentException: Invalid format: \\\"2020-03-01\\\" is malformed at \\\"20-03-01\\\"\",\"status\":\"Encountered"
                        + " exception. Exception java.lang.IllegalArgumentException: Invalid format: \\\"2020-03-01\\\" is malformed"
                        + " at \\\"20-03-01\\\"\"}",
                actualCountFlowRecordsByVillageIDAndLastModifiedDateResult.getBody());
        assertEquals(1, actualCountFlowRecordsByVillageIDAndLastModifiedDateResult.getHeaders().size());
        assertEquals(400, actualCountFlowRecordsByVillageIDAndLastModifiedDateResult.getStatusCodeValue());
    }

    @Test
    void testCountFlowRecordsByVillageIDAndLastModifiedDate4() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getLastSyncDate()).thenReturn("Failed with generic error");
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(integerList);
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualCountFlowRecordsByVillageIDAndLastModifiedDateResult = cHOAppSyncServiceImpl
                .countFlowRecordsByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate, atLeast(1)).getLastSyncDate();
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals(
                "{\"data\":null,\"statusCode\":400,\"errorMessage\":\"Encountered exception. Exception java.lang.IllegalArgu"
                        + "mentException: Invalid format: \\\"Failed with generic error\\\"\",\"status\":\"Encountered exception. Exception"
                        + " java.lang.IllegalArgumentException: Invalid format: \\\"Failed with generic error\\\"\"}",
                actualCountFlowRecordsByVillageIDAndLastModifiedDateResult.getBody());
        assertEquals(1, actualCountFlowRecordsByVillageIDAndLastModifiedDateResult.getHeaders().size());
        assertEquals(400, actualCountFlowRecordsByVillageIDAndLastModifiedDateResult.getStatusCodeValue());
    }

    @Test
    void testCountFlowRecordsByVillageIDAndLastModifiedDate5() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getLastSyncDate()).thenReturn(null);
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(integerList);
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualCountFlowRecordsByVillageIDAndLastModifiedDateResult = cHOAppSyncServiceImpl
                .countFlowRecordsByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate).getLastSyncDate();
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals(
                "{\"data\":null,\"statusCode\":400,\"errorMessage\":\"Bad request. Incomplete request body - Either villageIDs"
                        + " or lastSyncDate missing.\",\"status\":\"Bad request. Incomplete request body - Either villageIDs or"
                        + " lastSyncDate missing.\"}",
                actualCountFlowRecordsByVillageIDAndLastModifiedDateResult.getBody());
        assertEquals(1, actualCountFlowRecordsByVillageIDAndLastModifiedDateResult.getHeaders().size());
        assertEquals(400, actualCountFlowRecordsByVillageIDAndLastModifiedDateResult.getStatusCodeValue());
    }

    @Test
    void testCountFlowRecordsByVillageIDAndLastModifiedDate6() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getLastSyncDate()).thenReturn("SyncSearchRequest(lastSyncDate=");
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(integerList);
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualCountFlowRecordsByVillageIDAndLastModifiedDateResult = cHOAppSyncServiceImpl
                .countFlowRecordsByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate, atLeast(1)).getLastSyncDate();
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals(
                "{\"data\":null,\"statusCode\":400,\"errorMessage\":\"Encountered exception. Exception java.lang.IllegalArgu"
                        + "mentException: Invalid format: \\\"SyncSearchRequest(lastSyncDate\\u003d\\\"\",\"status\":\"Encountered exception."
                        + " Exception java.lang.IllegalArgumentException: Invalid format: \\\"SyncSearchRequest(lastSyncDate\\u003d"
                        + "\\\"\"}",
                actualCountFlowRecordsByVillageIDAndLastModifiedDateResult.getBody());
        assertEquals(1, actualCountFlowRecordsByVillageIDAndLastModifiedDateResult.getHeaders().size());
        assertEquals(400, actualCountFlowRecordsByVillageIDAndLastModifiedDateResult.getStatusCodeValue());
    }

    @Test
    void testCountFlowRecordsByVillageIDAndLastModifiedDate7() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getLastSyncDate())
                .thenThrow(new ResourceAccessException("Failed with generic error"));
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(integerList);
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualCountFlowRecordsByVillageIDAndLastModifiedDateResult = cHOAppSyncServiceImpl
                .countFlowRecordsByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate).getLastSyncDate();
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals(
                "{\"data\":null,\"statusCode\":500,\"errorMessage\":\"Error fetching ben flow status records to sync . Exception"
                        + " org.springframework.web.client.ResourceAccessException: Failed with generic error\",\"status\":\"Error"
                        + " fetching ben flow status records to sync . Exception org.springframework.web.client.ResourceAccessException:"
                        + " Failed with generic error\"}",
                actualCountFlowRecordsByVillageIDAndLastModifiedDateResult.getBody());
        assertEquals(1, actualCountFlowRecordsByVillageIDAndLastModifiedDateResult.getHeaders().size());
        assertEquals(500, actualCountFlowRecordsByVillageIDAndLastModifiedDateResult.getStatusCodeValue());
    }

    @Test
    void testCountFlowRecordsByVillageIDAndLastModifiedDate8() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getLastSyncDate())
                .thenThrow(new IllegalArgumentException("Failed with generic error"));
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(integerList);
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualCountFlowRecordsByVillageIDAndLastModifiedDateResult = cHOAppSyncServiceImpl
                .countFlowRecordsByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate).getLastSyncDate();
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals(
                "{\"data\":null,\"statusCode\":400,\"errorMessage\":\"Encountered exception. Exception java.lang.IllegalArgu"
                        + "mentException: Failed with generic error\",\"status\":\"Encountered exception. Exception java.lang"
                        + ".IllegalArgumentException: Failed with generic error\"}",
                actualCountFlowRecordsByVillageIDAndLastModifiedDateResult.getBody());
        assertEquals(1, actualCountFlowRecordsByVillageIDAndLastModifiedDateResult.getHeaders().size());
        assertEquals(400, actualCountFlowRecordsByVillageIDAndLastModifiedDateResult.getStatusCodeValue());
    }

    @Test
    void testGetFlowRecordsByVillageIDAndLastModifiedDate() {
        // Arrange
        SyncSearchRequest villageIDAndLastSyncDate = new SyncSearchRequest();
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualFlowRecordsByVillageIDAndLastModifiedDate = cHOAppSyncServiceImpl
                .getFlowRecordsByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        assertEquals(
                "{\"data\":null,\"statusCode\":400,\"errorMessage\":\"Bad request. Incomplete request body - Either villageIDs"
                        + " or lastSyncDate missing.\",\"status\":\"Bad request. Incomplete request body - Either villageIDs or"
                        + " lastSyncDate missing.\"}",
                actualFlowRecordsByVillageIDAndLastModifiedDate.getBody());
        assertEquals(1, actualFlowRecordsByVillageIDAndLastModifiedDate.getHeaders().size());
        assertEquals(400, actualFlowRecordsByVillageIDAndLastModifiedDate.getStatusCodeValue());
    }

    @Test
    void testGetFlowRecordsByVillageIDAndLastModifiedDate2() {
        // Arrange
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(new ArrayList<>());
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualFlowRecordsByVillageIDAndLastModifiedDate = cHOAppSyncServiceImpl
                .getFlowRecordsByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals(
                "{\"data\":null,\"statusCode\":400,\"errorMessage\":\"Bad request. Incomplete request body - Either villageIDs"
                        + " or lastSyncDate missing.\",\"status\":\"Bad request. Incomplete request body - Either villageIDs or"
                        + " lastSyncDate missing.\"}",
                actualFlowRecordsByVillageIDAndLastModifiedDate.getBody());
        assertEquals(1, actualFlowRecordsByVillageIDAndLastModifiedDate.getHeaders().size());
        assertEquals(400, actualFlowRecordsByVillageIDAndLastModifiedDate.getStatusCodeValue());
    }

    @Test
    void testGetFlowRecordsByVillageIDAndLastModifiedDate3() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getLastSyncDate()).thenReturn("2020-03-01");
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(integerList);
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualFlowRecordsByVillageIDAndLastModifiedDate = cHOAppSyncServiceImpl
                .getFlowRecordsByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate, atLeast(1)).getLastSyncDate();
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals(
                "{\"data\":null,\"statusCode\":400,\"errorMessage\":\"Encountered exception. Exception java.lang.IllegalArgu"
                        + "mentException: Invalid format: \\\"2020-03-01\\\" is malformed at \\\"20-03-01\\\"\",\"status\":\"Encountered"
                        + " exception. Exception java.lang.IllegalArgumentException: Invalid format: \\\"2020-03-01\\\" is malformed"
                        + " at \\\"20-03-01\\\"\"}",
                actualFlowRecordsByVillageIDAndLastModifiedDate.getBody());
        assertEquals(1, actualFlowRecordsByVillageIDAndLastModifiedDate.getHeaders().size());
        assertEquals(400, actualFlowRecordsByVillageIDAndLastModifiedDate.getStatusCodeValue());
    }

    @Test
    void testGetFlowRecordsByVillageIDAndLastModifiedDate4() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getLastSyncDate()).thenReturn("Failed with generic error");
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(integerList);
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualFlowRecordsByVillageIDAndLastModifiedDate = cHOAppSyncServiceImpl
                .getFlowRecordsByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate, atLeast(1)).getLastSyncDate();
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals(
                "{\"data\":null,\"statusCode\":400,\"errorMessage\":\"Encountered exception. Exception java.lang.IllegalArgu"
                        + "mentException: Invalid format: \\\"Failed with generic error\\\"\",\"status\":\"Encountered exception. Exception"
                        + " java.lang.IllegalArgumentException: Invalid format: \\\"Failed with generic error\\\"\"}",
                actualFlowRecordsByVillageIDAndLastModifiedDate.getBody());
        assertEquals(1, actualFlowRecordsByVillageIDAndLastModifiedDate.getHeaders().size());
        assertEquals(400, actualFlowRecordsByVillageIDAndLastModifiedDate.getStatusCodeValue());
    }

    @Test
    void testGetFlowRecordsByVillageIDAndLastModifiedDate5() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getLastSyncDate()).thenReturn(null);
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(integerList);
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualFlowRecordsByVillageIDAndLastModifiedDate = cHOAppSyncServiceImpl
                .getFlowRecordsByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate).getLastSyncDate();
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals(
                "{\"data\":null,\"statusCode\":400,\"errorMessage\":\"Bad request. Incomplete request body - Either villageIDs"
                        + " or lastSyncDate missing.\",\"status\":\"Bad request. Incomplete request body - Either villageIDs or"
                        + " lastSyncDate missing.\"}",
                actualFlowRecordsByVillageIDAndLastModifiedDate.getBody());
        assertEquals(1, actualFlowRecordsByVillageIDAndLastModifiedDate.getHeaders().size());
        assertEquals(400, actualFlowRecordsByVillageIDAndLastModifiedDate.getStatusCodeValue());
    }

    @Test
    void testGetFlowRecordsByVillageIDAndLastModifiedDate6() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getLastSyncDate()).thenReturn("SyncSearchRequest(lastSyncDate=");
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(integerList);
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualFlowRecordsByVillageIDAndLastModifiedDate = cHOAppSyncServiceImpl
                .getFlowRecordsByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate, atLeast(1)).getLastSyncDate();
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals(
                "{\"data\":null,\"statusCode\":400,\"errorMessage\":\"Encountered exception. Exception java.lang.IllegalArgu"
                        + "mentException: Invalid format: \\\"SyncSearchRequest(lastSyncDate\\u003d\\\"\",\"status\":\"Encountered exception."
                        + " Exception java.lang.IllegalArgumentException: Invalid format: \\\"SyncSearchRequest(lastSyncDate\\u003d"
                        + "\\\"\"}",
                actualFlowRecordsByVillageIDAndLastModifiedDate.getBody());
        assertEquals(1, actualFlowRecordsByVillageIDAndLastModifiedDate.getHeaders().size());
        assertEquals(400, actualFlowRecordsByVillageIDAndLastModifiedDate.getStatusCodeValue());
    }

    @Test
    void testGetFlowRecordsByVillageIDAndLastModifiedDate7() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getLastSyncDate())
                .thenThrow(new ResourceAccessException("Failed with generic error"));
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(integerList);
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualFlowRecordsByVillageIDAndLastModifiedDate = cHOAppSyncServiceImpl
                .getFlowRecordsByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate).getLastSyncDate();
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals(
                "{\"data\":null,\"statusCode\":500,\"errorMessage\":\"Error fetching ben flow status records to sync . Exception"
                        + " org.springframework.web.client.ResourceAccessException: Failed with generic error\",\"status\":\"Error"
                        + " fetching ben flow status records to sync . Exception org.springframework.web.client.ResourceAccessException:"
                        + " Failed with generic error\"}",
                actualFlowRecordsByVillageIDAndLastModifiedDate.getBody());
        assertEquals(1, actualFlowRecordsByVillageIDAndLastModifiedDate.getHeaders().size());
        assertEquals(500, actualFlowRecordsByVillageIDAndLastModifiedDate.getStatusCodeValue());
    }

    @Test
    void testGetFlowRecordsByVillageIDAndLastModifiedDate8() {
        // Arrange
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        SyncSearchRequest villageIDAndLastSyncDate = mock(SyncSearchRequest.class);
        when(villageIDAndLastSyncDate.getLastSyncDate())
                .thenThrow(new IllegalArgumentException("Failed with generic error"));
        when(villageIDAndLastSyncDate.getVillageID()).thenReturn(integerList);
        doNothing().when(villageIDAndLastSyncDate).setLastModifiedDate(Mockito.<Long>any());
        doNothing().when(villageIDAndLastSyncDate).setLastSyncDate(Mockito.<String>any());
        doNothing().when(villageIDAndLastSyncDate).setVillageID(Mockito.<List<Integer>>any());
        villageIDAndLastSyncDate.setLastModifiedDate(1L);
        villageIDAndLastSyncDate.setLastSyncDate("2020-03-01");
        villageIDAndLastSyncDate.setVillageID(new ArrayList<>());

        // Act
        ResponseEntity<String> actualFlowRecordsByVillageIDAndLastModifiedDate = cHOAppSyncServiceImpl
                .getFlowRecordsByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, "JaneDoe");

        // Assert
        verify(villageIDAndLastSyncDate).getLastSyncDate();
        verify(villageIDAndLastSyncDate, atLeast(1)).getVillageID();
        verify(villageIDAndLastSyncDate).setLastModifiedDate(isA(Long.class));
        verify(villageIDAndLastSyncDate).setLastSyncDate(eq("2020-03-01"));
        verify(villageIDAndLastSyncDate).setVillageID(isA(List.class));
        assertEquals(
                "{\"data\":null,\"statusCode\":400,\"errorMessage\":\"Encountered exception. Exception java.lang.IllegalArgu"
                        + "mentException: Failed with generic error\",\"status\":\"Encountered exception. Exception java.lang"
                        + ".IllegalArgumentException: Failed with generic error\"}",
                actualFlowRecordsByVillageIDAndLastModifiedDate.getBody());
        assertEquals(1, actualFlowRecordsByVillageIDAndLastModifiedDate.getHeaders().size());
        assertEquals(400, actualFlowRecordsByVillageIDAndLastModifiedDate.getStatusCodeValue());
    }

    @Test
    void testSaveUserActivityLogs() {
        // Arrange
        when(userActivityLogsRepo.saveAll(Mockito.<Iterable<UserActivityLogs>>any())).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<String> actualSaveUserActivityLogsResult = cHOAppSyncServiceImpl
                .saveUserActivityLogs(new ArrayList<>(), "JaneDoe");

        // Assert
        verify(userActivityLogsRepo).saveAll(isA(Iterable.class));
        assertEquals(
                "{\"data\":{\"response\":\"Data saved successfully\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":"
                        + "\"Success\"}",
                actualSaveUserActivityLogsResult.getBody());
        assertEquals(1, actualSaveUserActivityLogsResult.getHeaders().size());
        assertEquals(200, actualSaveUserActivityLogsResult.getStatusCodeValue());
    }

    @Test
    void testSaveUserActivityLogs2() {
        // Arrange
        UserActivityLogs userActivityLogs = new UserActivityLogs();
        userActivityLogs.setImageData(new byte[]{'A', -120, 'A', -120, 'A', -120, 'A', -120});
        userActivityLogs.setIsOutOfReach(true);
        userActivityLogs.setLatitude(10.0d);
        userActivityLogs.setLogId(1);
        userActivityLogs.setLoginTimeStamp(mock(Timestamp.class));
        userActivityLogs.setLoginType("Failed with generic error");
        userActivityLogs.setLogoutTimeStamp(mock(Timestamp.class));
        userActivityLogs.setLogoutType("Failed with generic error");
        userActivityLogs.setLongitude(10.0d);
        userActivityLogs.setOption("Failed with generic error");
        userActivityLogs.setUserId("42");
        userActivityLogs.setUserImage("Failed with generic error");
        userActivityLogs.setUserName("janedoe");

        ArrayList<UserActivityLogs> userActivityLogsList = new ArrayList<>();
        userActivityLogsList.add(userActivityLogs);
        when(userActivityLogsRepo.saveAll(Mockito.<Iterable<UserActivityLogs>>any())).thenReturn(userActivityLogsList);

        // Act
        ResponseEntity<String> actualSaveUserActivityLogsResult = cHOAppSyncServiceImpl
                .saveUserActivityLogs(new ArrayList<>(), "JaneDoe");

        // Assert
        verify(userActivityLogsRepo).saveAll(isA(Iterable.class));
        assertEquals(
                "{\"statusCode\":500,\"errorMessage\":\"Encountered exception while saving UserActivityLogs. java.lang"
                        + ".Exception\",\"status\":\"Encountered exception while saving UserActivityLogs. java.lang.Exception\"}",
                actualSaveUserActivityLogsResult.getBody());
        assertEquals(1, actualSaveUserActivityLogsResult.getHeaders().size());
        assertEquals(500, actualSaveUserActivityLogsResult.getStatusCodeValue());
    }

    @Test
    void testSaveUserActivityLogs3() {
        // Arrange
        UserActivityLogs userActivityLogs = new UserActivityLogs();
        userActivityLogs.setImageData(new byte[]{'A', -120, 'A', -120, 'A', -120, 'A', -120});
        userActivityLogs.setIsOutOfReach(true);
        userActivityLogs.setLatitude(10.0d);
        userActivityLogs.setLogId(1);
        userActivityLogs.setLoginTimeStamp(mock(Timestamp.class));
        userActivityLogs.setLoginType("Failed with generic error");
        userActivityLogs.setLogoutTimeStamp(mock(Timestamp.class));
        userActivityLogs.setLogoutType("Failed with generic error");
        userActivityLogs.setLongitude(10.0d);
        userActivityLogs.setOption("Failed with generic error");
        userActivityLogs.setUserId("42");
        userActivityLogs.setUserImage("Failed with generic error");
        userActivityLogs.setUserName("janedoe");

        ArrayList<UserActivityLogs> logsList = new ArrayList<>();
        logsList.add(userActivityLogs);

        // Act
        ResponseEntity<String> actualSaveUserActivityLogsResult = cHOAppSyncServiceImpl.saveUserActivityLogs(logsList,
                "JaneDoe");

        // Assert
        assertEquals(
                "{\"statusCode\":500,\"errorMessage\":\"Encountered exception while saving UserActivityLogs. java.lang"
                        + ".IllegalArgumentException: Illegal base64 character 20\",\"status\":\"Encountered exception while saving"
                        + " UserActivityLogs. java.lang.IllegalArgumentException: Illegal base64 character 20\"}",
                actualSaveUserActivityLogsResult.getBody());
        assertEquals(1, actualSaveUserActivityLogsResult.getHeaders().size());
        assertEquals(500, actualSaveUserActivityLogsResult.getStatusCodeValue());
    }

    @Test
    void testSaveUserActivityLogs4() {
        // Arrange
        UserActivityLogs userActivityLogs = new UserActivityLogs();
        userActivityLogs.setImageData(new byte[]{'A', -120, 'A', -120, 'A', -120, 'A', -120});
        userActivityLogs.setIsOutOfReach(true);
        userActivityLogs.setLatitude(10.0d);
        userActivityLogs.setLogId(1);
        userActivityLogs.setLoginTimeStamp(mock(Timestamp.class));
        userActivityLogs.setLoginType("Failed with generic error");
        userActivityLogs.setLogoutTimeStamp(mock(Timestamp.class));
        userActivityLogs.setLogoutType("Failed with generic error");
        userActivityLogs.setLongitude(10.0d);
        userActivityLogs.setOption("Failed with generic error");
        userActivityLogs.setUserId("42");
        userActivityLogs.setUserImage("Failed with generic error");
        userActivityLogs.setUserName("janedoe");

        UserActivityLogs userActivityLogs2 = new UserActivityLogs();
        userActivityLogs2.setImageData(new byte[]{'A', -120, 'A', -120, 'A', -120, 'A', -120});
        userActivityLogs2.setIsOutOfReach(false);
        userActivityLogs2.setLatitude(0.5d);
        userActivityLogs2.setLogId(2);
        userActivityLogs2.setLoginTimeStamp(mock(Timestamp.class));
        userActivityLogs2.setLoginType("FAILURE");
        userActivityLogs2.setLogoutTimeStamp(mock(Timestamp.class));
        userActivityLogs2.setLogoutType("FAILURE");
        userActivityLogs2.setLongitude(0.5d);
        userActivityLogs2.setOption("FAILURE");
        userActivityLogs2.setUserId("Failed with generic error");
        userActivityLogs2.setUserImage("FAILURE");
        userActivityLogs2.setUserName("Failed with generic error");

        ArrayList<UserActivityLogs> logsList = new ArrayList<>();
        logsList.add(userActivityLogs2);
        logsList.add(userActivityLogs);

        // Act
        ResponseEntity<String> actualSaveUserActivityLogsResult = cHOAppSyncServiceImpl.saveUserActivityLogs(logsList,
                "JaneDoe");

        // Assert
        assertEquals(
                "{\"statusCode\":500,\"errorMessage\":\"Encountered exception while saving UserActivityLogs. java.lang"
                        + ".IllegalArgumentException: Illegal base64 character 20\",\"status\":\"Encountered exception while saving"
                        + " UserActivityLogs. java.lang.IllegalArgumentException: Illegal base64 character 20\"}",
                actualSaveUserActivityLogsResult.getBody());
        assertEquals(1, actualSaveUserActivityLogsResult.getHeaders().size());
        assertEquals(500, actualSaveUserActivityLogsResult.getStatusCodeValue());
    }

    @Test
    void testSaveUserActivityLogs5() {
        // Arrange
        when(userActivityLogsRepo.saveAll(Mockito.<Iterable<UserActivityLogs>>any()))
                .thenThrow(new ResourceAccessException("Failed with generic error"));

        // Act
        ResponseEntity<String> actualSaveUserActivityLogsResult = cHOAppSyncServiceImpl
                .saveUserActivityLogs(new ArrayList<>(), "JaneDoe");

        // Assert
        verify(userActivityLogsRepo).saveAll(isA(Iterable.class));
        assertEquals(
                "{\"statusCode\":500,\"errorMessage\":\"Encountered exception while saving UserActivityLogs. org.springframework"
                        + ".web.client.ResourceAccessException: Failed with generic error\",\"status\":\"Encountered exception while"
                        + " saving UserActivityLogs. org.springframework.web.client.ResourceAccessException: Failed with generic"
                        + " error\"}",
                actualSaveUserActivityLogsResult.getBody());
        assertEquals(1, actualSaveUserActivityLogsResult.getHeaders().size());
        assertEquals(500, actualSaveUserActivityLogsResult.getStatusCodeValue());
    }

    @Test
    void testSaveUserActivityLogs6() {
        // Arrange
        when(userActivityLogsRepo.saveAll(Mockito.<Iterable<UserActivityLogs>>any()))
                .thenThrow(new DataIntegrityViolationException("Failed with generic error"));

        // Act
        ResponseEntity<String> actualSaveUserActivityLogsResult = cHOAppSyncServiceImpl
                .saveUserActivityLogs(new ArrayList<>(), "JaneDoe");

        // Assert
        verify(userActivityLogsRepo).saveAll(isA(Iterable.class));
        assertEquals(
                "{\"statusCode\":400,\"errorMessage\":\"Encountered exception EITHER due to incorrect payload syntax OR"
                        + " because of missing userId. Please check the payload. org.springframework.dao.DataIntegrityViolationException"
                        + ": Failed with generic error\",\"status\":\"Encountered exception EITHER due to incorrect payload syntax"
                        + " OR because of missing userId. Please check the payload. org.springframework.dao.DataIntegrityViolat"
                        + "ionException: Failed with generic error\"}",
                actualSaveUserActivityLogsResult.getBody());
        assertEquals(1, actualSaveUserActivityLogsResult.getHeaders().size());
        assertEquals(400, actualSaveUserActivityLogsResult.getStatusCodeValue());
    }

    @Test
    void testGetBeneficiaryNurseFormDataGeneralOPD() {
        // Arrange and Act
        ResponseEntity<String> actualBeneficiaryNurseFormDataGeneralOPD = cHOAppSyncServiceImpl
                .getBeneficiaryNurseFormDataGeneralOPD("Coming Request", "JaneDoe");

        // Assert
        assertEquals(
                "{\"data\":null,\"statusCode\":400,\"errorMessage\":\"Encountered exception EITHER due to incorrect payload"
                        + " syntax OR because of missing benRegID/visitCode. Please check the payload. org.json.JSONException:"
                        + " Value Coming of type java.lang.String cannot be converted to JSONObject\",\"status\":\"Encountered"
                        + " exception EITHER due to incorrect payload syntax OR because of missing benRegID/visitCode. Please"
                        + " check the payload. org.json.JSONException: Value Coming of type java.lang.String cannot be converted"
                        + " to JSONObject\"}",
                actualBeneficiaryNurseFormDataGeneralOPD.getBody());
        assertEquals(1, actualBeneficiaryNurseFormDataGeneralOPD.getHeaders().size());
        assertEquals(400, actualBeneficiaryNurseFormDataGeneralOPD.getStatusCodeValue());
    }

    @Test
    void testGetBeneficiaryNurseFormDataGeneralOPD2() {
        // Arrange and Act
        ResponseEntity<String> actualBeneficiaryNurseFormDataGeneralOPD = cHOAppSyncServiceImpl
                .getBeneficiaryNurseFormDataGeneralOPD("Failed with generic error", "JaneDoe");

        // Assert
        assertEquals(
                "{\"data\":null,\"statusCode\":400,\"errorMessage\":\"Encountered exception EITHER due to incorrect payload"
                        + " syntax OR because of missing benRegID/visitCode. Please check the payload. org.json.JSONException:"
                        + " Value Failed of type java.lang.String cannot be converted to JSONObject\",\"status\":\"Encountered"
                        + " exception EITHER due to incorrect payload syntax OR because of missing benRegID/visitCode. Please"
                        + " check the payload. org.json.JSONException: Value Failed of type java.lang.String cannot be converted"
                        + " to JSONObject\"}",
                actualBeneficiaryNurseFormDataGeneralOPD.getBody());
        assertEquals(1, actualBeneficiaryNurseFormDataGeneralOPD.getHeaders().size());
        assertEquals(400, actualBeneficiaryNurseFormDataGeneralOPD.getStatusCodeValue());
    }

    @Test
    void testGetBeneficiaryNurseFormDataGeneralOPD3() {
        // Arrange and Act
        ResponseEntity<String> actualBeneficiaryNurseFormDataGeneralOPD = cHOAppSyncServiceImpl
                .getBeneficiaryNurseFormDataGeneralOPD("FAILURE", "JaneDoe");

        // Assert
        assertEquals(
                "{\"data\":null,\"statusCode\":400,\"errorMessage\":\"Encountered exception EITHER due to incorrect payload"
                        + " syntax OR because of missing benRegID/visitCode. Please check the payload. org.json.JSONException:"
                        + " Value FAILURE of type java.lang.String cannot be converted to JSONObject\",\"status\":\"Encountered"
                        + " exception EITHER due to incorrect payload syntax OR because of missing benRegID/visitCode. Please"
                        + " check the payload. org.json.JSONException: Value FAILURE of type java.lang.String cannot be converted"
                        + " to JSONObject\"}",
                actualBeneficiaryNurseFormDataGeneralOPD.getBody());
        assertEquals(1, actualBeneficiaryNurseFormDataGeneralOPD.getHeaders().size());
        assertEquals(400, actualBeneficiaryNurseFormDataGeneralOPD.getStatusCodeValue());
    }

    @Test
    void testGetBeneficiaryNurseFormDataGeneralOPD4() {
        // Arrange and Act
        ResponseEntity<String> actualBeneficiaryNurseFormDataGeneralOPD = cHOAppSyncServiceImpl
                .getBeneficiaryNurseFormDataGeneralOPD("42", "JaneDoe");

        // Assert
        assertEquals(
                "{\"data\":null,\"statusCode\":400,\"errorMessage\":\"Encountered exception EITHER due to incorrect payload"
                        + " syntax OR because of missing benRegID/visitCode. Please check the payload. org.json.JSONException:"
                        + " Value 42 of type java.lang.Integer cannot be converted to JSONObject\",\"status\":\"Encountered exception"
                        + " EITHER due to incorrect payload syntax OR because of missing benRegID/visitCode. Please check the"
                        + " payload. org.json.JSONException: Value 42 of type java.lang.Integer cannot be converted to"
                        + " JSONObject\"}",
                actualBeneficiaryNurseFormDataGeneralOPD.getBody());
        assertEquals(1, actualBeneficiaryNurseFormDataGeneralOPD.getHeaders().size());
        assertEquals(400, actualBeneficiaryNurseFormDataGeneralOPD.getStatusCodeValue());
    }

    @Test
    void testGetBeneficiaryNurseFormDataGeneralOPD5() {
        // Arrange and Act
        ResponseEntity<String> actualBeneficiaryNurseFormDataGeneralOPD = cHOAppSyncServiceImpl
                .getBeneficiaryNurseFormDataGeneralOPD("", "JaneDoe");

        // Assert
        assertEquals(
                "{\"data\":null,\"statusCode\":400,\"errorMessage\":\"Encountered exception EITHER due to incorrect payload"
                        + " syntax OR because of missing benRegID/visitCode. Please check the payload. org.json.JSONException:"
                        + " End of input at character 0 of \",\"status\":\"Encountered exception EITHER due to incorrect payload"
                        + " syntax OR because of missing benRegID/visitCode. Please check the payload. org.json.JSONException:"
                        + " End of input at character 0 of \"}",
                actualBeneficiaryNurseFormDataGeneralOPD.getBody());
        assertEquals(1, actualBeneficiaryNurseFormDataGeneralOPD.getHeaders().size());
        assertEquals(400, actualBeneficiaryNurseFormDataGeneralOPD.getStatusCodeValue());
    }

    @Test
    void testGetBeneficiaryNurseFormDataGeneralOPD6() {
        // Arrange and Act
        ResponseEntity<String> actualBeneficiaryNurseFormDataGeneralOPD = cHOAppSyncServiceImpl
                .getBeneficiaryNurseFormDataGeneralOPD("java.lang.String", "JaneDoe");

        // Assert
        assertEquals(
                "{\"data\":null,\"statusCode\":400,\"errorMessage\":\"Encountered exception EITHER due to incorrect payload"
                        + " syntax OR because of missing benRegID/visitCode. Please check the payload. org.json.JSONException:"
                        + " Value java.lang.String of type java.lang.String cannot be converted to JSONObject\",\"status\":\"Encountered"
                        + " exception EITHER due to incorrect payload syntax OR because of missing benRegID/visitCode. Please"
                        + " check the payload. org.json.JSONException: Value java.lang.String of type java.lang.String cannot be"
                        + " converted to JSONObject\"}",
                actualBeneficiaryNurseFormDataGeneralOPD.getBody());
        assertEquals(1, actualBeneficiaryNurseFormDataGeneralOPD.getHeaders().size());
        assertEquals(400, actualBeneficiaryNurseFormDataGeneralOPD.getStatusCodeValue());
    }

    @Test
    void testSaveBeneficiaryNurseFormDataGeneralOPD() {
        // Arrange and Act
        ResponseEntity<String> actualSaveBeneficiaryNurseFormDataGeneralOPDResult = cHOAppSyncServiceImpl
                .saveBeneficiaryNurseFormDataGeneralOPD("Request Obj", "JaneDoe");

        // Assert
        assertEquals(
                "{\"statusCode\":500,\"errorMessage\":\"Unable to save data com.google.gson.JsonSyntaxException: com.google"
                        + ".gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line"
                        + " 1 column 10 path $\",\"status\":\"Unable to save data com.google.gson.JsonSyntaxException: com.google"
                        + ".gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line"
                        + " 1 column 10 path $\"}",
                actualSaveBeneficiaryNurseFormDataGeneralOPDResult.getBody());
        assertEquals(1, actualSaveBeneficiaryNurseFormDataGeneralOPDResult.getHeaders().size());
        assertEquals(500, actualSaveBeneficiaryNurseFormDataGeneralOPDResult.getStatusCodeValue());
    }

    @Test
    void testSaveBeneficiaryNurseFormDataGeneralOPD2() {
        // Arrange and Act
        ResponseEntity<String> actualSaveBeneficiaryNurseFormDataGeneralOPDResult = cHOAppSyncServiceImpl
                .saveBeneficiaryNurseFormDataGeneralOPD("Failed with generic error", "JaneDoe");

        // Assert
        assertEquals(
                "{\"statusCode\":500,\"errorMessage\":\"Unable to save data com.google.gson.JsonSyntaxException: com.google"
                        + ".gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line"
                        + " 1 column 9 path $\",\"status\":\"Unable to save data com.google.gson.JsonSyntaxException: com.google.gson"
                        + ".stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1"
                        + " column 9 path $\"}",
                actualSaveBeneficiaryNurseFormDataGeneralOPDResult.getBody());
        assertEquals(1, actualSaveBeneficiaryNurseFormDataGeneralOPDResult.getHeaders().size());
        assertEquals(500, actualSaveBeneficiaryNurseFormDataGeneralOPDResult.getStatusCodeValue());
    }

    @Test
    void testSaveBeneficiaryNurseFormDataGeneralOPD3() {
        // Arrange and Act
        ResponseEntity<String> actualSaveBeneficiaryNurseFormDataGeneralOPDResult = cHOAppSyncServiceImpl
                .saveBeneficiaryNurseFormDataGeneralOPD("FAILURE", "JaneDoe");

        // Assert
        assertEquals(
                "{\"statusCode\":500,\"errorMessage\":\"Unable to save data java.lang.IllegalStateException: Not a JSON"
                        + " Object: \\\"FAILURE\\\"\",\"status\":\"Unable to save data java.lang.IllegalStateException: Not a JSON Object:"
                        + " \\\"FAILURE\\\"\"}",
                actualSaveBeneficiaryNurseFormDataGeneralOPDResult.getBody());
        assertEquals(1, actualSaveBeneficiaryNurseFormDataGeneralOPDResult.getHeaders().size());
        assertEquals(500, actualSaveBeneficiaryNurseFormDataGeneralOPDResult.getStatusCodeValue());
    }

    @Test
    void testSaveBeneficiaryNurseFormDataGeneralOPD4() {
        // Arrange and Act
        ResponseEntity<String> actualSaveBeneficiaryNurseFormDataGeneralOPDResult = cHOAppSyncServiceImpl
                .saveBeneficiaryNurseFormDataGeneralOPD("application/json", "JaneDoe");

        // Assert
        assertEquals(
                "{\"statusCode\":500,\"errorMessage\":\"Unable to save data com.google.gson.JsonSyntaxException: com.google"
                        + ".gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line"
                        + " 1 column 13 path $\",\"status\":\"Unable to save data com.google.gson.JsonSyntaxException: com.google"
                        + ".gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line"
                        + " 1 column 13 path $\"}",
                actualSaveBeneficiaryNurseFormDataGeneralOPDResult.getBody());
        assertEquals(1, actualSaveBeneficiaryNurseFormDataGeneralOPDResult.getHeaders().size());
        assertEquals(500, actualSaveBeneficiaryNurseFormDataGeneralOPDResult.getStatusCodeValue());
    }

    @Test
    void testSaveBeneficiaryNurseFormDataGeneralOPD5() {
        // Arrange and Act
        ResponseEntity<String> actualSaveBeneficiaryNurseFormDataGeneralOPDResult = cHOAppSyncServiceImpl
                .saveBeneficiaryNurseFormDataGeneralOPD("Error in nurse data saving :", "JaneDoe");

        // Assert
        assertEquals(
                "{\"statusCode\":500,\"errorMessage\":\"Unable to save data com.google.gson.JsonSyntaxException: com.google"
                        + ".gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line"
                        + " 1 column 8 path $\",\"status\":\"Unable to save data com.google.gson.JsonSyntaxException: com.google.gson"
                        + ".stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1"
                        + " column 8 path $\"}",
                actualSaveBeneficiaryNurseFormDataGeneralOPDResult.getBody());
        assertEquals(1, actualSaveBeneficiaryNurseFormDataGeneralOPDResult.getHeaders().size());
        assertEquals(500, actualSaveBeneficiaryNurseFormDataGeneralOPDResult.getStatusCodeValue());
    }

    @Test
    void testSaveBeneficiaryNurseFormDataGeneralOPD6() {
        // Arrange and Act
        ResponseEntity<String> actualSaveBeneficiaryNurseFormDataGeneralOPDResult = cHOAppSyncServiceImpl
                .saveBeneficiaryNurseFormDataGeneralOPD("42", "JaneDoe");

        // Assert
        assertEquals(
                "{\"statusCode\":500,\"errorMessage\":\"Unable to save data java.lang.IllegalStateException: Not a JSON"
                        + " Object: 42\",\"status\":\"Unable to save data java.lang.IllegalStateException: Not a JSON Object: 42\"}",
                actualSaveBeneficiaryNurseFormDataGeneralOPDResult.getBody());
        assertEquals(1, actualSaveBeneficiaryNurseFormDataGeneralOPDResult.getHeaders().size());
        assertEquals(500, actualSaveBeneficiaryNurseFormDataGeneralOPDResult.getStatusCodeValue());
    }

    @Test
    void testSaveBeneficiaryNurseFormDataGeneralOPD7() {
        // Arrange and Act
        ResponseEntity<String> actualSaveBeneficiaryNurseFormDataGeneralOPDResult = cHOAppSyncServiceImpl
                .saveBeneficiaryNurseFormDataGeneralOPD("", "JaneDoe");

        // Assert
        assertEquals(
                "{\"statusCode\":500,\"errorMessage\":\"Unable to save data java.lang.IllegalStateException: Not a JSON"
                        + " Object: null\",\"status\":\"Unable to save data java.lang.IllegalStateException: Not a JSON Object:"
                        + " null\"}",
                actualSaveBeneficiaryNurseFormDataGeneralOPDResult.getBody());
        assertEquals(1, actualSaveBeneficiaryNurseFormDataGeneralOPDResult.getHeaders().size());
        assertEquals(500, actualSaveBeneficiaryNurseFormDataGeneralOPDResult.getStatusCodeValue());
    }

    @Test
    void testSavePrescriptionTemplatesToServer() {
        // Arrange
        when(prescriptionTemplatesRepo.saveAll(Mockito.<Iterable<PrescriptionTemplates>>any()))
                .thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<String> actualSavePrescriptionTemplatesToServerResult = cHOAppSyncServiceImpl
                .savePrescriptionTemplatesToServer(new ArrayList<>(), "JaneDoe");

        // Assert
        verify(prescriptionTemplatesRepo).saveAll(isA(Iterable.class));
        assertEquals(
                "{\"data\":{\"response\":\"Data saved successfully\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":"
                        + "\"Success\"}",
                actualSavePrescriptionTemplatesToServerResult.getBody());
        assertEquals(1, actualSavePrescriptionTemplatesToServerResult.getHeaders().size());
        assertEquals(200, actualSavePrescriptionTemplatesToServerResult.getStatusCodeValue());
    }

    @Test
    void testSavePrescriptionTemplatesToServer2() {
        // Arrange
        PrescriptionTemplates prescriptionTemplates = new PrescriptionTemplates();
        prescriptionTemplates.setCreatedDate(mock(Timestamp.class));
        prescriptionTemplates.setDrugId(1);
        prescriptionTemplates.setDrugName("Failed with generic error");
        prescriptionTemplates.setDuration("Failed with generic error");
        prescriptionTemplates.setFrequency("Failed with generic error");
        prescriptionTemplates.setId(1L);
        prescriptionTemplates.setInstruction("Failed with generic error");
        prescriptionTemplates.setModifiedDte(mock(Timestamp.class));
        prescriptionTemplates.setTempID(1);
        prescriptionTemplates.setTemplateName("Failed with generic error");
        prescriptionTemplates.setUnit("Failed with generic error");
        prescriptionTemplates.setUserID(1);

        ArrayList<PrescriptionTemplates> prescriptionTemplatesList = new ArrayList<>();
        prescriptionTemplatesList.add(prescriptionTemplates);
        when(prescriptionTemplatesRepo.saveAll(Mockito.<Iterable<PrescriptionTemplates>>any()))
                .thenReturn(prescriptionTemplatesList);

        // Act
        ResponseEntity<String> actualSavePrescriptionTemplatesToServerResult = cHOAppSyncServiceImpl
                .savePrescriptionTemplatesToServer(new ArrayList<>(), "JaneDoe");

        // Assert
        verify(prescriptionTemplatesRepo).saveAll(isA(Iterable.class));
        assertEquals("{\"statusCode\":500,\"errorMessage\":\"Encountered exception while saving Prescription templates."
                + " java.lang.Exception\",\"status\":\"Encountered exception while saving Prescription templates."
                + " java.lang.Exception\"}", actualSavePrescriptionTemplatesToServerResult.getBody());
        assertEquals(1, actualSavePrescriptionTemplatesToServerResult.getHeaders().size());
        assertEquals(500, actualSavePrescriptionTemplatesToServerResult.getStatusCodeValue());
    }

    @Test
    void testSavePrescriptionTemplatesToServer3() {
        // Arrange
        when(prescriptionTemplatesRepo.saveAll(Mockito.<Iterable<PrescriptionTemplates>>any()))
                .thenReturn(new ArrayList<>());

        PrescriptionTemplates prescriptionTemplates = new PrescriptionTemplates();
        prescriptionTemplates.setCreatedDate(mock(Timestamp.class));
        prescriptionTemplates.setDrugId(1);
        prescriptionTemplates.setDrugName("Failed with generic error");
        prescriptionTemplates.setDuration("Failed with generic error");
        prescriptionTemplates.setFrequency("Failed with generic error");
        prescriptionTemplates.setId(1L);
        prescriptionTemplates.setInstruction("Failed with generic error");
        prescriptionTemplates.setModifiedDte(mock(Timestamp.class));
        prescriptionTemplates.setTempID(1);
        prescriptionTemplates.setTemplateName("Failed with generic error");
        prescriptionTemplates.setUnit("Failed with generic error");
        prescriptionTemplates.setUserID(1);

        ArrayList<PrescriptionTemplates> templateList = new ArrayList<>();
        templateList.add(prescriptionTemplates);

        // Act
        ResponseEntity<String> actualSavePrescriptionTemplatesToServerResult = cHOAppSyncServiceImpl
                .savePrescriptionTemplatesToServer(templateList, "JaneDoe");

        // Assert
        verify(prescriptionTemplatesRepo).saveAll(isA(Iterable.class));
        assertEquals("{\"statusCode\":500,\"errorMessage\":\"Encountered exception while saving Prescription templates."
                + " java.lang.Exception\",\"status\":\"Encountered exception while saving Prescription templates."
                + " java.lang.Exception\"}", actualSavePrescriptionTemplatesToServerResult.getBody());
        assertEquals(1, actualSavePrescriptionTemplatesToServerResult.getHeaders().size());
        assertEquals(500, actualSavePrescriptionTemplatesToServerResult.getStatusCodeValue());
    }

    @Test
    void testSavePrescriptionTemplatesToServer4() {
        // Arrange
        when(prescriptionTemplatesRepo.saveAll(Mockito.<Iterable<PrescriptionTemplates>>any()))
                .thenReturn(new ArrayList<>());

        PrescriptionTemplates prescriptionTemplates = new PrescriptionTemplates();
        prescriptionTemplates.setCreatedDate(mock(Timestamp.class));
        prescriptionTemplates.setDrugId(1);
        prescriptionTemplates.setDrugName("Failed with generic error");
        prescriptionTemplates.setDuration("Failed with generic error");
        prescriptionTemplates.setFrequency("Failed with generic error");
        prescriptionTemplates.setId(1L);
        prescriptionTemplates.setInstruction("Failed with generic error");
        prescriptionTemplates.setModifiedDte(mock(Timestamp.class));
        prescriptionTemplates.setTempID(1);
        prescriptionTemplates.setTemplateName("Failed with generic error");
        prescriptionTemplates.setUnit("Failed with generic error");
        prescriptionTemplates.setUserID(1);

        PrescriptionTemplates prescriptionTemplates2 = new PrescriptionTemplates();
        prescriptionTemplates2.setCreatedDate(mock(Timestamp.class));
        prescriptionTemplates2.setDrugId(2);
        prescriptionTemplates2.setDrugName("FAILURE");
        prescriptionTemplates2.setDuration("FAILURE");
        prescriptionTemplates2.setFrequency("FAILURE");
        prescriptionTemplates2.setId(2L);
        prescriptionTemplates2.setInstruction("FAILURE");
        prescriptionTemplates2.setModifiedDte(mock(Timestamp.class));
        prescriptionTemplates2.setTempID(2);
        prescriptionTemplates2.setTemplateName("FAILURE");
        prescriptionTemplates2.setUnit("FAILURE");
        prescriptionTemplates2.setUserID(2);

        ArrayList<PrescriptionTemplates> templateList = new ArrayList<>();
        templateList.add(prescriptionTemplates2);
        templateList.add(prescriptionTemplates);

        // Act
        ResponseEntity<String> actualSavePrescriptionTemplatesToServerResult = cHOAppSyncServiceImpl
                .savePrescriptionTemplatesToServer(templateList, "JaneDoe");

        // Assert
        verify(prescriptionTemplatesRepo).saveAll(isA(Iterable.class));
        assertEquals("{\"statusCode\":500,\"errorMessage\":\"Encountered exception while saving Prescription templates."
                + " java.lang.Exception\",\"status\":\"Encountered exception while saving Prescription templates."
                + " java.lang.Exception\"}", actualSavePrescriptionTemplatesToServerResult.getBody());
        assertEquals(1, actualSavePrescriptionTemplatesToServerResult.getHeaders().size());
        assertEquals(500, actualSavePrescriptionTemplatesToServerResult.getStatusCodeValue());
    }

    @Test
    void testSavePrescriptionTemplatesToServer5() {
        // Arrange
        when(prescriptionTemplatesRepo.saveAll(Mockito.<Iterable<PrescriptionTemplates>>any()))
                .thenThrow(new ResourceAccessException("Failed with generic error"));

        // Act
        ResponseEntity<String> actualSavePrescriptionTemplatesToServerResult = cHOAppSyncServiceImpl
                .savePrescriptionTemplatesToServer(new ArrayList<>(), "JaneDoe");

        // Assert
        verify(prescriptionTemplatesRepo).saveAll(isA(Iterable.class));
        assertEquals("{\"statusCode\":500,\"errorMessage\":\"Encountered exception while saving Prescription templates."
                + " org.springframework.web.client.ResourceAccessException: Failed with generic error\",\"status\":\"Encountered"
                + " exception while saving Prescription templates. org.springframework.web.client.ResourceAccessException:"
                + " Failed with generic error\"}", actualSavePrescriptionTemplatesToServerResult.getBody());
        assertEquals(1, actualSavePrescriptionTemplatesToServerResult.getHeaders().size());
        assertEquals(500, actualSavePrescriptionTemplatesToServerResult.getStatusCodeValue());
    }

    @Test
    void testSavePrescriptionTemplatesToServer6() {
        // Arrange
        when(prescriptionTemplatesRepo.saveAll(Mockito.<Iterable<PrescriptionTemplates>>any()))
                .thenThrow(new DataIntegrityViolationException("Failed with generic error"));

        // Act
        ResponseEntity<String> actualSavePrescriptionTemplatesToServerResult = cHOAppSyncServiceImpl
                .savePrescriptionTemplatesToServer(new ArrayList<>(), "JaneDoe");

        // Assert
        verify(prescriptionTemplatesRepo).saveAll(isA(Iterable.class));
        assertEquals(
                "{\"statusCode\":400,\"errorMessage\":\"Encountered exception EITHER due to incorrect payload syntax OR"
                        + " because of missing userId. Please check the payload. org.springframework.dao.DataIntegrityViolationException"
                        + ": Failed with generic error\",\"status\":\"Encountered exception EITHER due to incorrect payload syntax"
                        + " OR because of missing userId. Please check the payload. org.springframework.dao.DataIntegrityViolat"
                        + "ionException: Failed with generic error\"}",
                actualSavePrescriptionTemplatesToServerResult.getBody());
        assertEquals(1, actualSavePrescriptionTemplatesToServerResult.getHeaders().size());
        assertEquals(400, actualSavePrescriptionTemplatesToServerResult.getStatusCodeValue());
    }

    @Test
    void testSavePrescriptionTemplatesToApp() {
        // Arrange
        when(prescriptionTemplatesRepo.getPrescriptionTemplatesByUserID(Mockito.<Integer>any()))
                .thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<String> actualSavePrescriptionTemplatesToAppResult = cHOAppSyncServiceImpl
                .savePrescriptionTemplatesToApp(1, "JaneDoe");

        // Assert
        verify(prescriptionTemplatesRepo).getPrescriptionTemplatesByUserID(isA(Integer.class));
        assertEquals("{\"data\":[],\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}",
                actualSavePrescriptionTemplatesToAppResult.getBody());
        assertEquals(1, actualSavePrescriptionTemplatesToAppResult.getHeaders().size());
        assertEquals(200, actualSavePrescriptionTemplatesToAppResult.getStatusCodeValue());
    }

    @Test
    void testSavePrescriptionTemplatesToApp2() {
        // Arrange
        PrescriptionTemplates prescriptionTemplates = new PrescriptionTemplates();
        prescriptionTemplates.setCreatedDate(mock(Timestamp.class));
        prescriptionTemplates.setDrugId(1);
        prescriptionTemplates.setDrugName("Failed with generic error");
        prescriptionTemplates.setDuration("Failed with generic error");
        prescriptionTemplates.setFrequency("Failed with generic error");
        prescriptionTemplates.setId(1L);
        prescriptionTemplates.setInstruction("Failed with generic error");
        prescriptionTemplates.setModifiedDte(mock(Timestamp.class));
        prescriptionTemplates.setTempID(1);
        prescriptionTemplates.setTemplateName("Failed with generic error");
        prescriptionTemplates.setUnit("Failed with generic error");
        prescriptionTemplates.setUserID(1);

        ArrayList<PrescriptionTemplates> prescriptionTemplatesList = new ArrayList<>();
        prescriptionTemplatesList.add(prescriptionTemplates);
        when(prescriptionTemplatesRepo.getPrescriptionTemplatesByUserID(Mockito.<Integer>any()))
                .thenReturn(prescriptionTemplatesList);

        // Act
        ResponseEntity<String> actualSavePrescriptionTemplatesToAppResult = cHOAppSyncServiceImpl
                .savePrescriptionTemplatesToApp(1, "JaneDoe");

        // Assert
        verify(prescriptionTemplatesRepo).getPrescriptionTemplatesByUserID(isA(Integer.class));
        assertEquals(
                "{\"data\":[{\"userID\":1,\"tempID\":1,\"templateName\":\"Failed with generic error\",\"drugId\":1,\"drugName\":\"Failed"
                        + " with generic error\",\"frequency\":\"Failed with generic error\",\"unit\":\"Failed with generic error\","
                        + "\"duration\":\"Failed with generic error\",\"instruction\":\"Failed with generic error\"}],\"statusCode\":200,"
                        + "\"errorMessage\":\"Success\",\"status\":\"Success\"}",
                actualSavePrescriptionTemplatesToAppResult.getBody());
        assertEquals(1, actualSavePrescriptionTemplatesToAppResult.getHeaders().size());
        assertEquals(200, actualSavePrescriptionTemplatesToAppResult.getStatusCodeValue());
    }

    @Test
    void testSavePrescriptionTemplatesToApp3() {
        // Arrange
        PrescriptionTemplates prescriptionTemplates = new PrescriptionTemplates();
        prescriptionTemplates.setCreatedDate(mock(Timestamp.class));
        prescriptionTemplates.setDrugId(1);
        prescriptionTemplates.setDrugName("Failed with generic error");
        prescriptionTemplates.setDuration("Failed with generic error");
        prescriptionTemplates.setFrequency("Failed with generic error");
        prescriptionTemplates.setId(1L);
        prescriptionTemplates.setInstruction("Failed with generic error");
        prescriptionTemplates.setModifiedDte(mock(Timestamp.class));
        prescriptionTemplates.setTempID(1);
        prescriptionTemplates.setTemplateName("Failed with generic error");
        prescriptionTemplates.setUnit("Failed with generic error");
        prescriptionTemplates.setUserID(1);

        PrescriptionTemplates prescriptionTemplates2 = new PrescriptionTemplates();
        prescriptionTemplates2.setCreatedDate(mock(Timestamp.class));
        prescriptionTemplates2.setDrugId(2);
        prescriptionTemplates2.setDrugName("Failed with generic error");
        prescriptionTemplates2.setDuration("Failed with generic error");
        prescriptionTemplates2.setFrequency("Failed with generic error");
        prescriptionTemplates2.setId(2L);
        prescriptionTemplates2.setInstruction("Failed with generic error");
        prescriptionTemplates2.setModifiedDte(mock(Timestamp.class));
        prescriptionTemplates2.setTempID(2);
        prescriptionTemplates2.setTemplateName("Failed with generic error");
        prescriptionTemplates2.setUnit("Failed with generic error");
        prescriptionTemplates2.setUserID(2);

        ArrayList<PrescriptionTemplates> prescriptionTemplatesList = new ArrayList<>();
        prescriptionTemplatesList.add(prescriptionTemplates2);
        prescriptionTemplatesList.add(prescriptionTemplates);
        when(prescriptionTemplatesRepo.getPrescriptionTemplatesByUserID(Mockito.<Integer>any()))
                .thenReturn(prescriptionTemplatesList);

        // Act
        ResponseEntity<String> actualSavePrescriptionTemplatesToAppResult = cHOAppSyncServiceImpl
                .savePrescriptionTemplatesToApp(1, "JaneDoe");

        // Assert
        verify(prescriptionTemplatesRepo).getPrescriptionTemplatesByUserID(isA(Integer.class));
        assertEquals(
                "{\"data\":[{\"userID\":2,\"tempID\":2,\"templateName\":\"Failed with generic error\",\"drugId\":2,\"drugName\":\"Failed"
                        + " with generic error\",\"frequency\":\"Failed with generic error\",\"unit\":\"Failed with generic error\","
                        + "\"duration\":\"Failed with generic error\",\"instruction\":\"Failed with generic error\"},{\"userID\":1,\"tempID"
                        + "\":1,\"templateName\":\"Failed with generic error\",\"drugId\":1,\"drugName\":\"Failed with generic error\","
                        + "\"frequency\":\"Failed with generic error\",\"unit\":\"Failed with generic error\",\"duration\":\"Failed with"
                        + " generic error\",\"instruction\":\"Failed with generic error\"}],\"statusCode\":200,\"errorMessage\":\"Success"
                        + "\",\"status\":\"Success\"}",
                actualSavePrescriptionTemplatesToAppResult.getBody());
        assertEquals(1, actualSavePrescriptionTemplatesToAppResult.getHeaders().size());
        assertEquals(200, actualSavePrescriptionTemplatesToAppResult.getStatusCodeValue());
    }

    @Test
    void testSavePrescriptionTemplatesToApp4() {
        // Arrange
        when(prescriptionTemplatesRepo.getPrescriptionTemplatesByUserID(Mockito.<Integer>any()))
                .thenThrow(new ResourceAccessException("Failed with generic error"));

        // Act and Assert
        assertThrows(ResourceAccessException.class,
                () -> cHOAppSyncServiceImpl.savePrescriptionTemplatesToApp(1, "JaneDoe"));
        verify(prescriptionTemplatesRepo).getPrescriptionTemplatesByUserID(isA(Integer.class));
    }

    @Test
    void testDeletePrescriptionTemplates() {
        // Arrange
        doNothing().when(prescriptionTemplatesRepo)
                .deletePrescriptionTemplatesByUserIDAndTempID(Mockito.<Integer>any(), Mockito.<Integer>any());

        // Act
        ResponseEntity<String> actualDeletePrescriptionTemplatesResult = cHOAppSyncServiceImpl
                .deletePrescriptionTemplates(1, 1);

        // Assert
        verify(prescriptionTemplatesRepo).deletePrescriptionTemplatesByUserIDAndTempID(isA(Integer.class),
                isA(Integer.class));
        assertEquals(
                "{\"data\":{\"response\":\"Successfully deleted\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":"
                        + "\"Success\"}",
                actualDeletePrescriptionTemplatesResult.getBody());
        assertEquals(1, actualDeletePrescriptionTemplatesResult.getHeaders().size());
        assertEquals(200, actualDeletePrescriptionTemplatesResult.getStatusCodeValue());
    }

    @Test
    void testDeletePrescriptionTemplates2() {
        // Arrange
        doThrow(new ResourceAccessException("Failed with generic error")).when(prescriptionTemplatesRepo)
                .deletePrescriptionTemplatesByUserIDAndTempID(Mockito.<Integer>any(), Mockito.<Integer>any());

        // Act and Assert
        assertThrows(ResourceAccessException.class, () -> cHOAppSyncServiceImpl.deletePrescriptionTemplates(1, 1));
        verify(prescriptionTemplatesRepo).deletePrescriptionTemplatesByUserIDAndTempID(isA(Integer.class),
                isA(Integer.class));
    }

    @Test
    void testCreateNewOutreachActivity() throws UnsupportedEncodingException {
        // Arrange
        OutreachActivity outreachActivity = new OutreachActivity();
        outreachActivity.setActivityDate(mock(Timestamp.class));
        outreachActivity.setActivityId(1);
        outreachActivity.setActivityName("Activity Name");
        outreachActivity.setDeleted(true);
        outreachActivity.setEventDescription("Event Description");
        outreachActivity.setImg1("Img1");
        outreachActivity.setImg1Address("42 Main St");
        outreachActivity.setImg1Data("AXAXAXAX".getBytes("UTF-8"));
        outreachActivity.setImg1TimeStamp(mock(Timestamp.class));
        outreachActivity.setImg1latitude(10.0d);
        outreachActivity.setImg1longitude(10.0d);
        outreachActivity.setImg2("Img2");
        outreachActivity.setImg2Address("42 Main St");
        outreachActivity.setImg2Data("AXAXAXAX".getBytes("UTF-8"));
        outreachActivity.setImg2TimeStamp(mock(Timestamp.class));
        outreachActivity.setImg2latitude(10.0d);
        outreachActivity.setImg2longitude(10.0d);
        outreachActivity.setNoOfParticipants(1);
        outreachActivity.setUserId(1);
        outreachActivity.setUserName("janedoe");
        when(outreachActivityRepo.save(Mockito.<OutreachActivity>any())).thenReturn(outreachActivity);

        OutreachActivity activity = new OutreachActivity();
        activity.setActivityDate(mock(Timestamp.class));
        activity.setActivityId(1);
        activity.setActivityName("Activity Name");
        activity.setDeleted(true);
        activity.setEventDescription("Event Description");
        activity.setImg1("Img1");
        activity.setImg1Address("42 Main St");
        activity.setImg1Data("AXAXAXAX".getBytes("UTF-8"));
        activity.setImg1TimeStamp(mock(Timestamp.class));
        activity.setImg1latitude(10.0d);
        activity.setImg1longitude(10.0d);
        activity.setImg2("Img2");
        activity.setImg2Address("42 Main St");
        activity.setImg2Data("AXAXAXAX".getBytes("UTF-8"));
        activity.setImg2TimeStamp(mock(Timestamp.class));
        activity.setImg2latitude(10.0d);
        activity.setImg2longitude(10.0d);
        activity.setNoOfParticipants(1);
        activity.setUserId(1);
        activity.setUserName("janedoe");

        // Act
        ResponseEntity<String> actualCreateNewOutreachActivityResult = cHOAppSyncServiceImpl
                .createNewOutreachActivity(activity, "JaneDoe");

        // Assert
        verify(outreachActivityRepo).save(isA(OutreachActivity.class));
        assertEquals(
                "{\"data\":{\"response\":\"Data saved successfully\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":"
                        + "\"Success\"}",
                actualCreateNewOutreachActivityResult.getBody());
        assertEquals(1, actualCreateNewOutreachActivityResult.getHeaders().size());
        assertEquals(200, actualCreateNewOutreachActivityResult.getStatusCodeValue());
        byte[] expectedImg1Data = "\"h5".getBytes("UTF-8");
        assertArrayEquals(expectedImg1Data, activity.getImg1Data());
        byte[] expectedImg2Data = "\"h6".getBytes("UTF-8");
        assertArrayEquals(expectedImg2Data, activity.getImg2Data());
    }

    @Test
    void testGetActivitiesByUser() {
        // Arrange
        when(outreachActivityRepo.getActivitiesByUserID(Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<String> actualActivitiesByUser = cHOAppSyncServiceImpl.getActivitiesByUser(1, "JaneDoe");

        // Assert
        verify(outreachActivityRepo).getActivitiesByUserID(isA(Integer.class));
        assertEquals("{\"data\":[],\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}",
                actualActivitiesByUser.getBody());
        assertEquals(1, actualActivitiesByUser.getHeaders().size());
        assertEquals(200, actualActivitiesByUser.getStatusCodeValue());
    }

    @Test
    void testGetActivityById() throws UnsupportedEncodingException {
        // Arrange
        Timestamp activityDate = mock(Timestamp.class);
        when(activityDate.getTime()).thenReturn(10L);
        Timestamp img1TimeStamp = mock(Timestamp.class);
        when(img1TimeStamp.getTime()).thenReturn(10L);
        Timestamp img2TimeStamp = mock(Timestamp.class);
        when(img2TimeStamp.getTime()).thenReturn(10L);

        OutreachActivity outreachActivity = new OutreachActivity();
        outreachActivity.setActivityDate(activityDate);
        outreachActivity.setActivityId(1);
        outreachActivity.setActivityName("Activity Name");
        outreachActivity.setDeleted(true);
        outreachActivity.setEventDescription("Event Description");
        outreachActivity.setImg1("Img1");
        outreachActivity.setImg1Address("42 Main St");
        outreachActivity.setImg1Data("AXAXAXAX".getBytes("UTF-8"));
        outreachActivity.setImg1TimeStamp(img1TimeStamp);
        outreachActivity.setImg1latitude(10.0d);
        outreachActivity.setImg1longitude(10.0d);
        outreachActivity.setImg2("Img2");
        outreachActivity.setImg2Address("42 Main St");
        outreachActivity.setImg2Data("AXAXAXAX".getBytes("UTF-8"));
        outreachActivity.setImg2TimeStamp(img2TimeStamp);
        outreachActivity.setImg2latitude(10.0d);
        outreachActivity.setImg2longitude(10.0d);
        outreachActivity.setNoOfParticipants(1);
        outreachActivity.setUserId(1);
        outreachActivity.setUserName("janedoe");
        Optional<OutreachActivity> ofResult = Optional.of(outreachActivity);
        when(outreachActivityRepo.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        ResponseEntity<String> actualActivityById = cHOAppSyncServiceImpl.getActivityById(1, "JaneDoe");

        // Assert
        verify(activityDate).getTime();
        verify(img1TimeStamp).getTime();
        verify(img2TimeStamp).getTime();
        verify(outreachActivityRepo).findById(isA(Integer.class));
        assertEquals(1, actualActivityById.getHeaders().size());
        assertEquals(200, actualActivityById.getStatusCodeValue());
        assertTrue(actualActivityById.hasBody());
    }

    @Test
    void testGetActivityById2() throws UnsupportedEncodingException {
        // Arrange
        Timestamp activityDate = mock(Timestamp.class);
        when(activityDate.getTime()).thenReturn(10L);
        Timestamp img1TimeStamp = mock(Timestamp.class);
        when(img1TimeStamp.getTime()).thenReturn(10L);
        Timestamp img2TimeStamp = mock(Timestamp.class);
        when(img2TimeStamp.getTime()).thenThrow(new ResourceAccessException("0.9E9"));

        OutreachActivity outreachActivity = new OutreachActivity();
        outreachActivity.setActivityDate(activityDate);
        outreachActivity.setActivityId(1);
        outreachActivity.setActivityName("Activity Name");
        outreachActivity.setDeleted(true);
        outreachActivity.setEventDescription("Event Description");
        outreachActivity.setImg1("Img1");
        outreachActivity.setImg1Address("42 Main St");
        outreachActivity.setImg1Data("AXAXAXAX".getBytes("UTF-8"));
        outreachActivity.setImg1TimeStamp(img1TimeStamp);
        outreachActivity.setImg1latitude(10.0d);
        outreachActivity.setImg1longitude(10.0d);
        outreachActivity.setImg2("Img2");
        outreachActivity.setImg2Address("42 Main St");
        outreachActivity.setImg2Data("AXAXAXAX".getBytes("UTF-8"));
        outreachActivity.setImg2TimeStamp(img2TimeStamp);
        outreachActivity.setImg2latitude(10.0d);
        outreachActivity.setImg2longitude(10.0d);
        outreachActivity.setNoOfParticipants(1);
        outreachActivity.setUserId(1);
        outreachActivity.setUserName("janedoe");
        Optional<OutreachActivity> ofResult = Optional.of(outreachActivity);
        when(outreachActivityRepo.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(ResourceAccessException.class, () -> cHOAppSyncServiceImpl.getActivityById(1, "JaneDoe"));
        verify(activityDate).getTime();
        verify(img1TimeStamp).getTime();
        verify(img2TimeStamp).getTime();
        verify(outreachActivityRepo).findById(isA(Integer.class));
    }

    @Test
    void testGetActivityById3() throws UnsupportedEncodingException {
        // Arrange
        OutreachActivity outreachActivity = mock(OutreachActivity.class);
        when(outreachActivity.getImg1Data()).thenReturn("AXAXAXAX".getBytes("UTF-8"));
        when(outreachActivity.getImg2Data()).thenReturn("AXAXAXAX".getBytes("UTF-8"));
        doNothing().when(outreachActivity).setActivityDate(Mockito.<Timestamp>any());
        doNothing().when(outreachActivity).setActivityId(anyInt());
        doNothing().when(outreachActivity).setActivityName(Mockito.<String>any());
        doNothing().when(outreachActivity).setDeleted(Mockito.<Boolean>any());
        doNothing().when(outreachActivity).setEventDescription(Mockito.<String>any());
        doNothing().when(outreachActivity).setImg1(Mockito.<String>any());
        doNothing().when(outreachActivity).setImg1Address(Mockito.<String>any());
        doNothing().when(outreachActivity).setImg1Data(Mockito.<byte[]>any());
        doNothing().when(outreachActivity).setImg1TimeStamp(Mockito.<Timestamp>any());
        doNothing().when(outreachActivity).setImg1latitude(Mockito.<Double>any());
        doNothing().when(outreachActivity).setImg1longitude(Mockito.<Double>any());
        doNothing().when(outreachActivity).setImg2(Mockito.<String>any());
        doNothing().when(outreachActivity).setImg2Address(Mockito.<String>any());
        doNothing().when(outreachActivity).setImg2Data(Mockito.<byte[]>any());
        doNothing().when(outreachActivity).setImg2TimeStamp(Mockito.<Timestamp>any());
        doNothing().when(outreachActivity).setImg2latitude(Mockito.<Double>any());
        doNothing().when(outreachActivity).setImg2longitude(Mockito.<Double>any());
        doNothing().when(outreachActivity).setNoOfParticipants(Mockito.<Integer>any());
        doNothing().when(outreachActivity).setUserId(Mockito.<Integer>any());
        doNothing().when(outreachActivity).setUserName(Mockito.<String>any());
        outreachActivity.setActivityDate(mock(Timestamp.class));
        outreachActivity.setActivityId(1);
        outreachActivity.setActivityName("Activity Name");
        outreachActivity.setDeleted(true);
        outreachActivity.setEventDescription("Event Description");
        outreachActivity.setImg1("Img1");
        outreachActivity.setImg1Address("42 Main St");
        outreachActivity.setImg1Data("AXAXAXAX".getBytes("UTF-8"));
        outreachActivity.setImg1TimeStamp(mock(Timestamp.class));
        outreachActivity.setImg1latitude(10.0d);
        outreachActivity.setImg1longitude(10.0d);
        outreachActivity.setImg2("Img2");
        outreachActivity.setImg2Address("42 Main St");
        outreachActivity.setImg2Data("AXAXAXAX".getBytes("UTF-8"));
        outreachActivity.setImg2TimeStamp(mock(Timestamp.class));
        outreachActivity.setImg2latitude(10.0d);
        outreachActivity.setImg2longitude(10.0d);
        outreachActivity.setNoOfParticipants(1);
        outreachActivity.setUserId(1);
        outreachActivity.setUserName("janedoe");
        Optional<OutreachActivity> ofResult = Optional.of(outreachActivity);
        when(outreachActivityRepo.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        ResponseEntity<String> actualActivityById = cHOAppSyncServiceImpl.getActivityById(1, "JaneDoe");

        // Assert
        verify(outreachActivity, atLeast(1)).getImg1Data();
        verify(outreachActivity, atLeast(1)).getImg2Data();
        verify(outreachActivity).setActivityDate(isA(Timestamp.class));
        verify(outreachActivity).setActivityId(eq(1));
        verify(outreachActivity).setActivityName(eq("Activity Name"));
        verify(outreachActivity).setDeleted(isA(Boolean.class));
        verify(outreachActivity).setEventDescription(eq("Event Description"));
        verify(outreachActivity, atLeast(1)).setImg1(Mockito.<String>any());
        verify(outreachActivity).setImg1Address(eq("42 Main St"));
        verify(outreachActivity).setImg1Data(isA(byte[].class));
        verify(outreachActivity).setImg1TimeStamp(isA(Timestamp.class));
        verify(outreachActivity).setImg1latitude(isA(Double.class));
        verify(outreachActivity).setImg1longitude(isA(Double.class));
        verify(outreachActivity, atLeast(1)).setImg2(Mockito.<String>any());
        verify(outreachActivity).setImg2Address(eq("42 Main St"));
        verify(outreachActivity).setImg2Data(isA(byte[].class));
        verify(outreachActivity).setImg2TimeStamp(isA(Timestamp.class));
        verify(outreachActivity).setImg2latitude(isA(Double.class));
        verify(outreachActivity).setImg2longitude(isA(Double.class));
        verify(outreachActivity).setNoOfParticipants(isA(Integer.class));
        verify(outreachActivity).setUserId(isA(Integer.class));
        verify(outreachActivity).setUserName(eq("janedoe"));
        verify(outreachActivityRepo).findById(isA(Integer.class));
        assertEquals(
                "{\"data\":{\"activityId\":0,\"userId\":null,\"userName\":null,\"activityName\":null,\"eventDescription\":null,"
                        + "\"noOfParticipants\":null,\"activityDate\":null,\"img1TimeStamp\":null,\"img2TimeStamp\":null,\"img1\":null,"
                        + "\"img2\":null,\"img1latitude\":null,\"img1longitude\":null,\"img1Address\":null,\"img2Address\":null,\"img2latitude"
                        + "\":null,\"img2longitude\":null},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}",
                actualActivityById.getBody());
        assertEquals(1, actualActivityById.getHeaders().size());
        assertEquals(200, actualActivityById.getStatusCodeValue());
    }

    @Test
    void testGetActivityById4() throws UnsupportedEncodingException {
        // Arrange
        OutreachActivity outreachActivity = mock(OutreachActivity.class);
        when(outreachActivity.getImg1Data()).thenReturn(null);
        when(outreachActivity.getImg2Data()).thenReturn("AXAXAXAX".getBytes("UTF-8"));
        doNothing().when(outreachActivity).setActivityDate(Mockito.<Timestamp>any());
        doNothing().when(outreachActivity).setActivityId(anyInt());
        doNothing().when(outreachActivity).setActivityName(Mockito.<String>any());
        doNothing().when(outreachActivity).setDeleted(Mockito.<Boolean>any());
        doNothing().when(outreachActivity).setEventDescription(Mockito.<String>any());
        doNothing().when(outreachActivity).setImg1(Mockito.<String>any());
        doNothing().when(outreachActivity).setImg1Address(Mockito.<String>any());
        doNothing().when(outreachActivity).setImg1Data(Mockito.<byte[]>any());
        doNothing().when(outreachActivity).setImg1TimeStamp(Mockito.<Timestamp>any());
        doNothing().when(outreachActivity).setImg1latitude(Mockito.<Double>any());
        doNothing().when(outreachActivity).setImg1longitude(Mockito.<Double>any());
        doNothing().when(outreachActivity).setImg2(Mockito.<String>any());
        doNothing().when(outreachActivity).setImg2Address(Mockito.<String>any());
        doNothing().when(outreachActivity).setImg2Data(Mockito.<byte[]>any());
        doNothing().when(outreachActivity).setImg2TimeStamp(Mockito.<Timestamp>any());
        doNothing().when(outreachActivity).setImg2latitude(Mockito.<Double>any());
        doNothing().when(outreachActivity).setImg2longitude(Mockito.<Double>any());
        doNothing().when(outreachActivity).setNoOfParticipants(Mockito.<Integer>any());
        doNothing().when(outreachActivity).setUserId(Mockito.<Integer>any());
        doNothing().when(outreachActivity).setUserName(Mockito.<String>any());
        outreachActivity.setActivityDate(mock(Timestamp.class));
        outreachActivity.setActivityId(1);
        outreachActivity.setActivityName("Activity Name");
        outreachActivity.setDeleted(true);
        outreachActivity.setEventDescription("Event Description");
        outreachActivity.setImg1("Img1");
        outreachActivity.setImg1Address("42 Main St");
        outreachActivity.setImg1Data("AXAXAXAX".getBytes("UTF-8"));
        outreachActivity.setImg1TimeStamp(mock(Timestamp.class));
        outreachActivity.setImg1latitude(10.0d);
        outreachActivity.setImg1longitude(10.0d);
        outreachActivity.setImg2("Img2");
        outreachActivity.setImg2Address("42 Main St");
        outreachActivity.setImg2Data("AXAXAXAX".getBytes("UTF-8"));
        outreachActivity.setImg2TimeStamp(mock(Timestamp.class));
        outreachActivity.setImg2latitude(10.0d);
        outreachActivity.setImg2longitude(10.0d);
        outreachActivity.setNoOfParticipants(1);
        outreachActivity.setUserId(1);
        outreachActivity.setUserName("janedoe");
        Optional<OutreachActivity> ofResult = Optional.of(outreachActivity);
        when(outreachActivityRepo.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        ResponseEntity<String> actualActivityById = cHOAppSyncServiceImpl.getActivityById(1, "JaneDoe");

        // Assert
        verify(outreachActivity).getImg1Data();
        verify(outreachActivity, atLeast(1)).getImg2Data();
        verify(outreachActivity).setActivityDate(isA(Timestamp.class));
        verify(outreachActivity).setActivityId(eq(1));
        verify(outreachActivity).setActivityName(eq("Activity Name"));
        verify(outreachActivity).setDeleted(isA(Boolean.class));
        verify(outreachActivity).setEventDescription(eq("Event Description"));
        verify(outreachActivity).setImg1(eq("Img1"));
        verify(outreachActivity).setImg1Address(eq("42 Main St"));
        verify(outreachActivity).setImg1Data(isA(byte[].class));
        verify(outreachActivity).setImg1TimeStamp(isA(Timestamp.class));
        verify(outreachActivity).setImg1latitude(isA(Double.class));
        verify(outreachActivity).setImg1longitude(isA(Double.class));
        verify(outreachActivity, atLeast(1)).setImg2(Mockito.<String>any());
        verify(outreachActivity).setImg2Address(eq("42 Main St"));
        verify(outreachActivity).setImg2Data(isA(byte[].class));
        verify(outreachActivity).setImg2TimeStamp(isA(Timestamp.class));
        verify(outreachActivity).setImg2latitude(isA(Double.class));
        verify(outreachActivity).setImg2longitude(isA(Double.class));
        verify(outreachActivity).setNoOfParticipants(isA(Integer.class));
        verify(outreachActivity).setUserId(isA(Integer.class));
        verify(outreachActivity).setUserName(eq("janedoe"));
        verify(outreachActivityRepo).findById(isA(Integer.class));
        assertEquals(
                "{\"data\":{\"activityId\":0,\"userId\":null,\"userName\":null,\"activityName\":null,\"eventDescription\":null,"
                        + "\"noOfParticipants\":null,\"activityDate\":null,\"img1TimeStamp\":null,\"img2TimeStamp\":null,\"img1\":null,"
                        + "\"img2\":null,\"img1latitude\":null,\"img1longitude\":null,\"img1Address\":null,\"img2Address\":null,\"img2latitude"
                        + "\":null,\"img2longitude\":null},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}",
                actualActivityById.getBody());
        assertEquals(1, actualActivityById.getHeaders().size());
        assertEquals(200, actualActivityById.getStatusCodeValue());
    }

    @Test
    void testGetActivityById5() throws UnsupportedEncodingException {
        // Arrange
        OutreachActivity outreachActivity = mock(OutreachActivity.class);
        when(outreachActivity.getImg1Data()).thenReturn("AXAXAXAX".getBytes("UTF-8"));
        when(outreachActivity.getImg2Data()).thenReturn(null);
        doNothing().when(outreachActivity).setActivityDate(Mockito.<Timestamp>any());
        doNothing().when(outreachActivity).setActivityId(anyInt());
        doNothing().when(outreachActivity).setActivityName(Mockito.<String>any());
        doNothing().when(outreachActivity).setDeleted(Mockito.<Boolean>any());
        doNothing().when(outreachActivity).setEventDescription(Mockito.<String>any());
        doNothing().when(outreachActivity).setImg1(Mockito.<String>any());
        doNothing().when(outreachActivity).setImg1Address(Mockito.<String>any());
        doNothing().when(outreachActivity).setImg1Data(Mockito.<byte[]>any());
        doNothing().when(outreachActivity).setImg1TimeStamp(Mockito.<Timestamp>any());
        doNothing().when(outreachActivity).setImg1latitude(Mockito.<Double>any());
        doNothing().when(outreachActivity).setImg1longitude(Mockito.<Double>any());
        doNothing().when(outreachActivity).setImg2(Mockito.<String>any());
        doNothing().when(outreachActivity).setImg2Address(Mockito.<String>any());
        doNothing().when(outreachActivity).setImg2Data(Mockito.<byte[]>any());
        doNothing().when(outreachActivity).setImg2TimeStamp(Mockito.<Timestamp>any());
        doNothing().when(outreachActivity).setImg2latitude(Mockito.<Double>any());
        doNothing().when(outreachActivity).setImg2longitude(Mockito.<Double>any());
        doNothing().when(outreachActivity).setNoOfParticipants(Mockito.<Integer>any());
        doNothing().when(outreachActivity).setUserId(Mockito.<Integer>any());
        doNothing().when(outreachActivity).setUserName(Mockito.<String>any());
        outreachActivity.setActivityDate(mock(Timestamp.class));
        outreachActivity.setActivityId(1);
        outreachActivity.setActivityName("Activity Name");
        outreachActivity.setDeleted(true);
        outreachActivity.setEventDescription("Event Description");
        outreachActivity.setImg1("Img1");
        outreachActivity.setImg1Address("42 Main St");
        outreachActivity.setImg1Data("AXAXAXAX".getBytes("UTF-8"));
        outreachActivity.setImg1TimeStamp(mock(Timestamp.class));
        outreachActivity.setImg1latitude(10.0d);
        outreachActivity.setImg1longitude(10.0d);
        outreachActivity.setImg2("Img2");
        outreachActivity.setImg2Address("42 Main St");
        outreachActivity.setImg2Data("AXAXAXAX".getBytes("UTF-8"));
        outreachActivity.setImg2TimeStamp(mock(Timestamp.class));
        outreachActivity.setImg2latitude(10.0d);
        outreachActivity.setImg2longitude(10.0d);
        outreachActivity.setNoOfParticipants(1);
        outreachActivity.setUserId(1);
        outreachActivity.setUserName("janedoe");
        Optional<OutreachActivity> ofResult = Optional.of(outreachActivity);
        when(outreachActivityRepo.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        ResponseEntity<String> actualActivityById = cHOAppSyncServiceImpl.getActivityById(1, "JaneDoe");

        // Assert
        verify(outreachActivity, atLeast(1)).getImg1Data();
        verify(outreachActivity).getImg2Data();
        verify(outreachActivity).setActivityDate(isA(Timestamp.class));
        verify(outreachActivity).setActivityId(eq(1));
        verify(outreachActivity).setActivityName(eq("Activity Name"));
        verify(outreachActivity).setDeleted(isA(Boolean.class));
        verify(outreachActivity).setEventDescription(eq("Event Description"));
        verify(outreachActivity, atLeast(1)).setImg1(Mockito.<String>any());
        verify(outreachActivity).setImg1Address(eq("42 Main St"));
        verify(outreachActivity).setImg1Data(isA(byte[].class));
        verify(outreachActivity).setImg1TimeStamp(isA(Timestamp.class));
        verify(outreachActivity).setImg1latitude(isA(Double.class));
        verify(outreachActivity).setImg1longitude(isA(Double.class));
        verify(outreachActivity).setImg2(eq("Img2"));
        verify(outreachActivity).setImg2Address(eq("42 Main St"));
        verify(outreachActivity).setImg2Data(isA(byte[].class));
        verify(outreachActivity).setImg2TimeStamp(isA(Timestamp.class));
        verify(outreachActivity).setImg2latitude(isA(Double.class));
        verify(outreachActivity).setImg2longitude(isA(Double.class));
        verify(outreachActivity).setNoOfParticipants(isA(Integer.class));
        verify(outreachActivity).setUserId(isA(Integer.class));
        verify(outreachActivity).setUserName(eq("janedoe"));
        verify(outreachActivityRepo).findById(isA(Integer.class));
        assertEquals(
                "{\"data\":{\"activityId\":0,\"userId\":null,\"userName\":null,\"activityName\":null,\"eventDescription\":null,"
                        + "\"noOfParticipants\":null,\"activityDate\":null,\"img1TimeStamp\":null,\"img2TimeStamp\":null,\"img1\":null,"
                        + "\"img2\":null,\"img1latitude\":null,\"img1longitude\":null,\"img1Address\":null,\"img2Address\":null,\"img2latitude"
                        + "\":null,\"img2longitude\":null},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}",
                actualActivityById.getBody());
        assertEquals(1, actualActivityById.getHeaders().size());
        assertEquals(200, actualActivityById.getStatusCodeValue());
    }

    @Test
    void testGettersAndSetters() {
        CHOAppSyncServiceImpl choAppSyncServiceImpl = new CHOAppSyncServiceImpl();

        // Act
        choAppSyncServiceImpl.setBenAnthropometryRepo(mock(BenAnthropometryRepo.class));
        choAppSyncServiceImpl.setBenChiefComplaintRepo(mock(BenChiefComplaintRepo.class));
        choAppSyncServiceImpl.setBenPhysicalVitalRepo(mock(BenPhysicalVitalRepo.class));
        choAppSyncServiceImpl.setBenVisitDetailRepo(mock(BenVisitDetailRepo.class));
        choAppSyncServiceImpl.setBeneficiaryFlowStatusRepo(mock(BeneficiaryFlowStatusRepo.class));
        choAppSyncServiceImpl.setCommonBenStatusFlowServiceImpl(new CommonBenStatusFlowServiceImpl());
        choAppSyncServiceImpl.setCommonNurseServiceImpl(new CommonNurseServiceImpl());
        choAppSyncServiceImpl.setGeneralOPDServiceImpl(new GeneralOPDServiceImpl());
        choAppSyncServiceImpl.setOutreachActivityRepo(mock(OutreachActivityRepo.class));
        choAppSyncServiceImpl.setPrescriptionTemplatesRepo(mock(PrescriptionTemplatesRepo.class));
        choAppSyncServiceImpl.setUserActivityLogsRepo(mock(UserActivityLogsRepo.class));
    }
}
