package com.iemr.hwc.controller.foetalmonitor;

import com.iemr.hwc.data.foetalmonitor.FoetalMonitor;
import com.iemr.hwc.service.foetalmonitor.FoetalMonitorService;
import com.iemr.hwc.utils.exception.IEMRException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

import java.io.FileNotFoundException;
import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class FoetalMonitorControllerTest {
    @Mock
    FoetalMonitorService foetalMonitorService;

    @InjectMocks
    FoetalMonitorController foetalMonitorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendANCMotherTestDetailsToFoetalMonitor() throws Exception {
        when(foetalMonitorService.sendFoetalMonitorTestDetails(any(FoetalMonitor.class), anyString())).thenReturn("sendFoetalMonitorTestDetailsResponse");

        ResponseEntity<String> result = foetalMonitorController.sendANCMotherTestDetailsToFoetalMonitor("requestObj", "authorization");
        Assertions.assertEquals(new ResponseEntity<String>("body", null, 0), result);
    }

    @Test
    void testSaveMother() {
        String result = foetalMonitorController.saveMother("requestObj", "authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetFoetalMonitorDetails() throws IEMRException {
        when(foetalMonitorService.getFoetalMonitorDetails(anyLong())).thenReturn("getFoetalMonitorDetailsResponse");

        String result = foetalMonitorController.getFoetalMonitorDetails(Long.valueOf(1));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetFoetalMonitorDetails2() throws FileNotFoundException, IEMRException, IOException {
        when(foetalMonitorService.readPDFANDGetBase64(anyString())).thenReturn("readPDFANDGetBase64Response");

        ResponseEntity<String> result = foetalMonitorController.getFoetalMonitorDetails(new FoetalMonitor(Long.valueOf(1), Long.valueOf(1), Long.valueOf(1), Long.valueOf(1), Long.valueOf(1), Boolean.TRUE));
        Assertions.assertEquals(new ResponseEntity<String>("body", null, 0), result);
    }

    @Test
    void testUpdateFoetalMonitorData() throws IEMRException {
        when(foetalMonitorService.updateFoetalMonitorData(any(FoetalMonitor.class))).thenReturn(0);

        ResponseEntity<String> result = foetalMonitorController.updateFoetalMonitorData("requestObj", "Authorization");
        Assertions.assertEquals(new ResponseEntity<String>("body", null, 0), result);
    }
}

