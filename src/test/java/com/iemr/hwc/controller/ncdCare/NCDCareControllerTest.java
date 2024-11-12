package com.iemr.hwc.controller.ncdCare;

import com.google.gson.JsonObject;
import com.iemr.hwc.service.ncdCare.NCDCareServiceImpl;
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

@ExtendWith(MockitoExtension.class)
class NCDCareControllerTest {

    @Mock
    NCDCareServiceImpl ncdCareServiceImpl;
    @InjectMocks
    NCDCareController nCDCareController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBenNCDCareNurseData() throws Exception {
        when(ncdCareServiceImpl.saveNCDCareNurseData(any(JsonObject.class), anyString())).thenReturn("saveNCDCareNurseDataResponse");

        String result = nCDCareController.saveBenNCDCareNurseData("requestObj", "Authorization");
        verify(ncdCareServiceImpl).deleteVisitDetails(any(JsonObject.class));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testSaveBenNCDCareDoctorData() throws Exception {
        when(ncdCareServiceImpl.saveDoctorData(any(JsonObject.class), anyString())).thenReturn(Long.valueOf(1));

        String result = nCDCareController.saveBenNCDCareDoctorData("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenVisitDetailsFrmNurseNCDCare() {
        when(ncdCareServiceImpl.getBenVisitDetailsFrmNurseNCDCare(anyLong(), anyLong())).thenReturn("getBenVisitDetailsFrmNurseNCDCareResponse");

        String result = nCDCareController.getBenVisitDetailsFrmNurseNCDCare("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenNCDCareHistoryDetails() throws IEMRException {
        when(ncdCareServiceImpl.getBenNCDCareHistoryDetails(anyLong(), anyLong())).thenReturn("getBenNCDCareHistoryDetailsResponse");

        String result = nCDCareController.getBenNCDCareHistoryDetails("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenVitalDetailsFrmNurseNCDCare() {
        when(ncdCareServiceImpl.getBeneficiaryVitalDetails(anyLong(), anyLong())).thenReturn("getBeneficiaryVitalDetailsResponse");

        String result = nCDCareController.getBenVitalDetailsFrmNurseNCDCare("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenCaseRecordFromDoctorNCDCare() {
        when(ncdCareServiceImpl.getBenCaseRecordFromDoctorNCDCare(anyLong(), anyLong())).thenReturn("getBenCaseRecordFromDoctorNCDCareResponse");

        String result = nCDCareController.getBenCaseRecordFromDoctorNCDCare("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testUpdateHistoryNurse() throws Exception {
        when(ncdCareServiceImpl.updateBenHistoryDetails(any(JsonObject.class))).thenReturn(0);

        String result = nCDCareController.updateHistoryNurse("requestObj");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testUpdateVitalNurse() throws Exception {
        when(ncdCareServiceImpl.updateBenVitalDetails(any(JsonObject.class))).thenReturn(0);

        String result = nCDCareController.updateVitalNurse("requestObj");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testUpdateNCDCareDoctorData() throws Exception {
        when(ncdCareServiceImpl.updateNCDCareDoctorData(any(JsonObject.class), anyString())).thenReturn(Long.valueOf(1));

        String result = nCDCareController.updateNCDCareDoctorData("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

