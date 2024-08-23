package com.iemr.hwc.controller.ncdscreening;

import com.google.gson.JsonObject;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.service.ncdscreening.NCDSCreeningDoctorService;
import com.iemr.hwc.service.ncdscreening.NCDScreeningService;
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
class NCDScreeningControllerTest {

    @Mock
    NCDScreeningService ncdScreeningService;
    @Mock
    NCDSCreeningDoctorService ncdSCreeningDoctorService;
    @InjectMocks
    NCDScreeningController nCDScreeningController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBeneficiaryNCDScreeningDetails() throws Exception {
        when(ncdScreeningService.saveNCDScreeningNurseData(any(JsonObject.class), anyString())).thenReturn("saveNCDScreeningNurseDataResponse");

        String result = nCDScreeningController.saveBeneficiaryNCDScreeningDetails("requestObj", "Authorization");
        verify(ncdScreeningService).deleteVisitDetails(any(JsonObject.class));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testSaveBenNCDScreeningDoctorData() throws Exception {
        when(ncdScreeningService.saveDoctorData(any(JsonObject.class), anyString())).thenReturn(Long.valueOf(1));

        String result = nCDScreeningController.saveBenNCDScreeningDoctorData("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetNCDScreenigDetails() {
        when(ncdScreeningService.getNCDScreeningDetails(anyLong(), anyLong())).thenReturn("getNCDScreeningDetailsResponse");

        String result = nCDScreeningController.getNCDScreenigDetails("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetNcdScreeningVisitCount() {
        when(ncdScreeningService.getNcdScreeningVisitCnt(anyLong())).thenReturn("getNcdScreeningVisitCntResponse");

        String result = nCDScreeningController.getNcdScreeningVisitCount(Long.valueOf(1));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenCaseRecordFromDoctorNCDCare() {
        when(ncdScreeningService.getBenCaseRecordFromDoctorNCDScreening(anyLong(), anyLong())).thenReturn("getBenCaseRecordFromDoctorNCDScreeningResponse");

        String result = nCDScreeningController.getBenCaseRecordFromDoctorNCDCare("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenVisitDetailsFrmNurseGOPD() {
        when(ncdScreeningService.getBenVisitDetailsFrmNurseNCDScreening(anyLong(), anyLong())).thenReturn("getBenVisitDetailsFrmNurseNCDScreeningResponse");

        String result = nCDScreeningController.getBenVisitDetailsFrmNurseGOPD("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenHistoryDetails() {
        when(ncdScreeningService.getBenHistoryDetails(anyLong(), anyLong())).thenReturn("getBenHistoryDetailsResponse");

        String result = nCDScreeningController.getBenHistoryDetails("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenVitalDetailsFrmNurse() {
        when(ncdScreeningService.getBeneficiaryVitalDetails(anyLong(), anyLong())).thenReturn("getBeneficiaryVitalDetailsResponse");

        String result = nCDScreeningController.getBenVitalDetailsFrmNurse("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenIdrsDetailsFrmNurse() {
        when(ncdScreeningService.getBenIdrsDetailsFrmNurse(anyLong(), anyLong())).thenReturn("getBenIdrsDetailsFrmNurseResponse");

        String result = nCDScreeningController.getBenIdrsDetailsFrmNurse("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testFetchConfirmedScreeningDisease() throws IEMRException {
        when(ncdScreeningService.fetchConfirmedScreeningDisease(anyLong())).thenReturn("fetchConfirmedScreeningDiseaseResponse");

        String result = nCDScreeningController.fetchConfirmedScreeningDisease(new CommonUtilityClass());
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetNCDScreeningData() throws IEMRException {
        when(ncdScreeningService.getNCDScreeningData(anyLong(), anyLong())).thenReturn("getNCDScreeningDataResponse");

        String result = nCDScreeningController.getNCDScreeningData(new CommonUtilityClass());
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetCbacDetails() throws IEMRException {
        when(ncdScreeningService.getCbacData(anyLong(), anyLong())).thenReturn("getCbacDataResponse");

        String result = nCDScreeningController.getCbacDetails(new CommonUtilityClass());
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testUpdateBeneficiaryNCDScreeningDetails() throws Exception {
        when(ncdScreeningService.updateNurseNCDScreeningDetails(any(JsonObject.class))).thenReturn(Integer.valueOf(0));

        String result = nCDScreeningController.updateBeneficiaryNCDScreeningDetails("requestObj");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testUpdateHistoryNurse() throws Exception {
        when(ncdScreeningService.UpdateNCDScreeningHistory(any(JsonObject.class))).thenReturn(Integer.valueOf(0));

        String result = nCDScreeningController.updateHistoryNurse("requestObj");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testUpdateVitalNurse() throws Exception {
        when(ncdScreeningService.updateBenVitalDetails(any(JsonObject.class))).thenReturn(0);

        String result = nCDScreeningController.updateVitalNurse("requestObj");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testUpdateIDRSScreen() throws Exception {
        when(ncdScreeningService.UpdateIDRSScreen(any(JsonObject.class))).thenReturn(Long.valueOf(1));

        String result = nCDScreeningController.updateIDRSScreen("requestObj");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testUpdateDoctorData() throws Exception {
        when(ncdSCreeningDoctorService.updateDoctorData(any(JsonObject.class), anyString())).thenReturn(0);

        String result = nCDScreeningController.updateDoctorData("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testUpdateNCDScreeningData() throws IEMRException {
        when(ncdScreeningService.updateNCDScreeningData(any(JsonObject.class))).thenReturn("updateNCDScreeningDataResponse");

        String result = nCDScreeningController.updateNCDScreeningData("requestObj");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

