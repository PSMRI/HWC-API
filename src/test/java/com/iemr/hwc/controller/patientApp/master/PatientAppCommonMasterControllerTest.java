package com.iemr.hwc.controller.patientApp.master;

import com.iemr.hwc.service.patientApp.master.CommonPatientAppMasterService;
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
class PatientAppCommonMasterControllerTest {

    @Mock
    CommonPatientAppMasterService commonPatientAppMasterService;
    @InjectMocks
    PatientAppCommonMasterController patientAppCommonMasterController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPatientAppChiefComplaintsMasterData() {
        when(commonPatientAppMasterService.getChiefComplaintsMaster(anyInt(), anyInt(), anyString())).thenReturn("getChiefComplaintsMasterResponse");

        String result = patientAppCommonMasterController.patientAppChiefComplaintsMasterData(Integer.valueOf(0), Integer.valueOf(0), "gender");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testPatientAppCovidMasterData() {
        when(commonPatientAppMasterService.getCovidMaster(anyInt(), anyInt(), anyString())).thenReturn("getCovidMasterResponse");

        String result = patientAppCommonMasterController.patientAppCovidMasterData(Integer.valueOf(0), Integer.valueOf(0), "gender");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testSaveBenCovidDoctorDataPatientApp() throws Exception {
        when(commonPatientAppMasterService.saveCovidScreeningData(anyString())).thenReturn("saveCovidScreeningDataResponse");

        String result = patientAppCommonMasterController.saveBenCovidDoctorDataPatientApp("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testSaveBenChiefComplaintsDataPatientApp() throws Exception {
        when(commonPatientAppMasterService.savechiefComplaintsData(anyString())).thenReturn("savechiefComplaintsDataResponse");

        String result = patientAppCommonMasterController.saveBenChiefComplaintsDataPatientApp("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testSaveTCSlotDataPatientApp() throws Exception {
        when(commonPatientAppMasterService.bookTCSlotData(anyString(), anyString())).thenReturn(Integer.valueOf(0));

        String result = patientAppCommonMasterController.saveTCSlotDataPatientApp("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testPatientAppMasterData() {
        when(commonPatientAppMasterService.getMaster(anyInt())).thenReturn("getMasterResponse");

        String result = patientAppCommonMasterController.patientAppMasterData(Integer.valueOf(0));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetPatientEpisodeDataMobileApp() throws Exception {
        when(commonPatientAppMasterService.getPatientEpisodeData(anyString())).thenReturn("getPatientEpisodeDataResponse");

        String result = patientAppCommonMasterController.getPatientEpisodeDataMobileApp("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetPatientBookedSlotDetails() throws Exception {
        when(commonPatientAppMasterService.getPatientBookedSlots(anyString())).thenReturn("getPatientBookedSlotsResponse");

        String result = patientAppCommonMasterController.getPatientBookedSlotDetails("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testSaveSpecialistDiagnosisData() throws Exception {
        when(commonPatientAppMasterService.saveSpecialistDiagnosisData(anyString())).thenReturn(Long.valueOf(1));

        String result = patientAppCommonMasterController.saveSpecialistDiagnosisData("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetSpecialistDiagnosisData() throws Exception {
        when(commonPatientAppMasterService.getSpecialistDiagnosisData(anyString())).thenReturn("getSpecialistDiagnosisDataResponse");

        String result = patientAppCommonMasterController.getSpecialistDiagnosisData("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetPatientsLast_3_Episode() throws Exception {
        when(commonPatientAppMasterService.getPatientsLast_3_Episode(anyString())).thenReturn("getPatientsLast_3_EpisodeResponse");

        String result = patientAppCommonMasterController.getPatientsLast_3_Episode("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

