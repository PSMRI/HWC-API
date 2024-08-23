package com.iemr.hwc.controller.labtechnician;

import com.google.gson.JsonObject;
import com.iemr.hwc.service.labtechnician.LabTechnicianServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LabTechnicianControllerTest {

    @Mock
    LabTechnicianServiceImpl labTechnicianServiceImpl;
    @InjectMocks
    LabTechnicianController labTechnicianController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveLabTestResult() throws Exception {
        when(labTechnicianServiceImpl.saveLabTestResult(any(JsonObject.class))).thenReturn(Integer.valueOf(0));

        String result = labTechnicianController.saveLabTestResult("requestObj");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBeneficiaryPrescribedProcedure() {
        when(labTechnicianServiceImpl.getBenePrescribedProcedureDetails(anyLong(), anyLong())).thenReturn("getBenePrescribedProcedureDetailsResponse");

        String result = labTechnicianController.getBeneficiaryPrescribedProcedure("requestOBJ");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetLabResultForVisitCode() {
        when(labTechnicianServiceImpl.getLabResultForVisitcode(anyLong(), anyLong())).thenReturn("getLabResultForVisitcodeResponse");

        String result = labTechnicianController.getLabResultForVisitCode("requestOBJ");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

