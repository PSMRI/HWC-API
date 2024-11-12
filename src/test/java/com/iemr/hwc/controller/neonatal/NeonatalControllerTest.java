package com.iemr.hwc.controller.neonatal;

import com.google.gson.JsonObject;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.service.neonatal.NeonatalService;
import com.iemr.hwc.utils.exception.IEMRException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.text.ParseException;

@ExtendWith(MockitoExtension.class)
class NeonatalControllerTest {

    @Mock
    NeonatalService neonatalService;
    @InjectMocks
    NeonatalController neonatalController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBenNeoNatalAndInfantNurseData() throws SQLException, Exception {
        when(neonatalService.saveNurseData(any(JsonObject.class), anyString())).thenReturn("saveNurseDataResponse");

        String result = neonatalController.saveBenNeoNatalAndInfantNurseData("requestObj", "Authorization");
        verify(neonatalService).deleteVisitDetails(any(JsonObject.class));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testSaveNNIDoctorData() throws Exception {
        when(neonatalService.saveDoctorDataNNI(any(JsonObject.class), anyString())).thenReturn(0);

        String result = neonatalController.saveNNIDoctorData("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenVisitDetailsFrmNurseNNI() throws Exception {
        when(neonatalService.getBenVisitDetailsFrmNurseNNI(anyLong(), anyLong())).thenReturn("getBenVisitDetailsFrmNurseNNIResponse");

        String result = neonatalController.getBenVisitDetailsFrmNurseNNI("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenVitalDetailsFrmNurse() throws Exception {
        when(neonatalService.getBeneficiaryVitalDetails(anyLong(), anyLong())).thenReturn("getBeneficiaryVitalDetailsResponse");

        String result = neonatalController.getBenVitalDetailsFrmNurse("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenHistoryDetails() throws Exception {
        when(neonatalService.getBirthAndImmuniHistory(anyLong(), anyLong())).thenReturn("getBirthAndImmuniHistoryResponse");

        String result = neonatalController.getBenHistoryDetails(new CommonUtilityClass());
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenImmunizationServiceDetails() throws Exception {
        when(neonatalService.getBeneficiaryImmunizationServiceDetails(anyLong(), anyLong())).thenReturn("getBeneficiaryImmunizationServiceDetailsResponse");

        String result = neonatalController.getBenImmunizationServiceDetails(new CommonUtilityClass());
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenCaseRecordFromDoctor() throws Exception {
        when(neonatalService.getBenCaseRecordFromDoctorNNI(anyLong(), anyLong())).thenReturn("getBenCaseRecordFromDoctorNNIResponse");

        String result = neonatalController.getBenCaseRecordFromDoctor("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testUpdateVitalNurseNNI() throws Exception {
        when(neonatalService.updateBenVitalDetailsNNI(any(JsonObject.class))).thenReturn(0);

        String result = neonatalController.updateVitalNurseNNI("requestObj");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testUpdateBirthAndImmunizationHistoryNurse() throws IEMRException, ParseException {
        when(neonatalService.updateBenHistoryDetails(any(JsonObject.class))).thenReturn(0);

        String result = neonatalController.updateBirthAndImmunizationHistoryNurse("requestObj");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testUpdateImmunizationServicesNurse() throws Exception {
        when(neonatalService.updateBenImmunServiceDetailsNNI(any(JsonObject.class))).thenReturn(0);

        String result = neonatalController.updateImmunizationServicesNurse("requestObj");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testUpdateNNIDoctorData() throws Exception {
        when(neonatalService.updateDoctorDataNNI(any(JsonObject.class), anyString())).thenReturn(Long.valueOf(1));

        String result = neonatalController.updateNNIDoctorData("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

