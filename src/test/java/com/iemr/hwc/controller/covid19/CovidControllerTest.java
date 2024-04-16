package com.iemr.hwc.controller.covid19;

import com.google.gson.JsonObject;
import com.iemr.hwc.service.covid19.Covid19ServiceImpl;
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
class CovidControllerTest {

    @Mock
    Covid19ServiceImpl covid19ServiceImpl;
    @InjectMocks
    CovidController covidController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBenNCDCareNurseData() throws Exception {
        when(covid19ServiceImpl.saveCovid19NurseData(any(JsonObject.class), anyString())).thenReturn("saveCovid19NurseDataResponse");

        String result = covidController.saveBenNCDCareNurseData("requestObj", "Authorization");
        verify(covid19ServiceImpl).deleteVisitDetails(any(JsonObject.class));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testSaveBenCovidDoctorData() throws Exception {
        when(covid19ServiceImpl.saveDoctorData(any(JsonObject.class), anyString())).thenReturn(Long.valueOf(1));

        String result = covidController.saveBenCovidDoctorData("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenVisitDetailsFrmNurseCovid19() {
        when(covid19ServiceImpl.getBenVisitDetailsFrmNurseCovid19(anyLong(), anyLong())).thenReturn("getBenVisitDetailsFrmNurseCovid19Response");

        String result = covidController.getBenVisitDetailsFrmNurseCovid19("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenCovid19HistoryDetails() throws IEMRException {
        when(covid19ServiceImpl.getBenCovid19HistoryDetails(anyLong(), anyLong())).thenReturn("getBenCovid19HistoryDetailsResponse");

        String result = covidController.getBenCovid19HistoryDetails("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenVitalDetailsFrmNurseNCDCare() {
        when(covid19ServiceImpl.getBeneficiaryVitalDetails(anyLong(), anyLong())).thenReturn("getBeneficiaryVitalDetailsResponse");

        String result = covidController.getBenVitalDetailsFrmNurseNCDCare("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenCaseRecordFromDoctorCovid19() {
        when(covid19ServiceImpl.getBenCaseRecordFromDoctorCovid19(anyLong(), anyLong())).thenReturn("getBenCaseRecordFromDoctorCovid19Response");

        String result = covidController.getBenCaseRecordFromDoctorCovid19("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testUpdateHistoryNurse() throws Exception {
        when(covid19ServiceImpl.updateBenHistoryDetails(any(JsonObject.class))).thenReturn(0);

        String result = covidController.updateHistoryNurse("requestObj");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testUpdateVitalNurse() throws Exception {
        when(covid19ServiceImpl.updateBenVitalDetails(any(JsonObject.class))).thenReturn(0);

        String result = covidController.updateVitalNurse("requestObj");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testUpdateCovid19DoctorData() throws Exception {
        when(covid19ServiceImpl.updateCovid19DoctorData(any(JsonObject.class), anyString())).thenReturn(Long.valueOf(1));

        String result = covidController.updateCovid19DoctorData("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

