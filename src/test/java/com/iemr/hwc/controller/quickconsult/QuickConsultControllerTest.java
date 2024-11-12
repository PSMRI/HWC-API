package com.iemr.hwc.controller.quickconsult;

import com.google.gson.JsonObject;
import com.iemr.hwc.service.quickConsultation.QuickConsultationServiceImpl;
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
class QuickConsultControllerTest {

    @Mock
    QuickConsultationServiceImpl quickConsultationServiceImpl;
    @InjectMocks
    QuickConsultController quickConsultController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBenQuickConsultDataNurse() throws Exception {
        when(quickConsultationServiceImpl.quickConsultNurseDataInsert(any(JsonObject.class), anyString())).thenReturn("quickConsultNurseDataInsertResponse");

        String result = quickConsultController.saveBenQuickConsultDataNurse("requestObj", "Authorization");
        verify(quickConsultationServiceImpl).deleteVisitDetails(any(JsonObject.class));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testSaveQuickConsultationDetail() throws Exception {
        when(quickConsultationServiceImpl.quickConsultDoctorDataInsert(any(JsonObject.class), anyString())).thenReturn(Integer.valueOf(0));

        String result = quickConsultController.saveQuickConsultationDetail("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenDataFrmNurseScrnToDocScrnVisitDetails() {
        when(quickConsultationServiceImpl.getBenDataFrmNurseToDocVisitDetailsScreen(anyLong(), anyLong())).thenReturn("getBenDataFrmNurseToDocVisitDetailsScreenResponse");

        String result = quickConsultController.getBenDataFrmNurseScrnToDocScrnVisitDetails("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenVitalDetailsFrmNurse() {
        when(quickConsultationServiceImpl.getBeneficiaryVitalDetails(anyLong(), anyLong())).thenReturn("getBeneficiaryVitalDetailsResponse");

        String result = quickConsultController.getBenVitalDetailsFrmNurse("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenCaseRecordFromDoctorQuickConsult() {
        when(quickConsultationServiceImpl.getBenCaseRecordFromDoctorQuickConsult(anyLong(), anyLong())).thenReturn("getBenCaseRecordFromDoctorQuickConsultResponse");

        String result = quickConsultController.getBenCaseRecordFromDoctorQuickConsult("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testUpdateGeneralOPDQCDoctorData() throws Exception {
        when(quickConsultationServiceImpl.updateGeneralOPDQCDoctorData(any(JsonObject.class), anyString())).thenReturn(Long.valueOf(1));

        String result = quickConsultController.updateGeneralOPDQCDoctorData("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

