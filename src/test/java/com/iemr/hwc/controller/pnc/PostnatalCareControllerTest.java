package com.iemr.hwc.controller.pnc;

import com.google.gson.JsonObject;
import com.iemr.hwc.service.pnc.PNCServiceImpl;
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
class PostnatalCareControllerTest {

    @Mock
    PNCServiceImpl pncServiceImpl;
    @InjectMocks
    PostnatalCareController postnatalCareController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBenPNCNurseData() throws Exception {
        when(pncServiceImpl.savePNCNurseData(any(JsonObject.class), anyString())).thenReturn("savePNCNurseDataResponse");

        String result = postnatalCareController.saveBenPNCNurseData("requestObj", "Authorization");
        verify(pncServiceImpl).deleteVisitDetails(any(JsonObject.class));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testSaveBenPNCDoctorData() throws Exception {
        when(pncServiceImpl.savePNCDoctorData(any(JsonObject.class), anyString())).thenReturn(Long.valueOf(1));

        String result = postnatalCareController.saveBenPNCDoctorData("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenVisitDetailsFrmNursePNC() {
        when(pncServiceImpl.getBenVisitDetailsFrmNursePNC(anyLong(), anyLong())).thenReturn("getBenVisitDetailsFrmNursePNCResponse");

        String result = postnatalCareController.getBenVisitDetailsFrmNursePNC("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenPNCDetailsFrmNursePNC() {
        when(pncServiceImpl.getBenPNCDetailsFrmNursePNC(anyLong(), anyLong())).thenReturn("getBenPNCDetailsFrmNursePNCResponse");

        String result = postnatalCareController.getBenPNCDetailsFrmNursePNC("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenHistoryDetails() throws Exception {
        when(pncServiceImpl.getBenHistoryDetails(anyLong(), anyLong())).thenReturn("getBenHistoryDetailsResponse");

        String result = postnatalCareController.getBenHistoryDetails("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenVitalDetailsFrmNurse() {
        when(pncServiceImpl.getBeneficiaryVitalDetails(anyLong(), anyLong())).thenReturn("getBeneficiaryVitalDetailsResponse");

        String result = postnatalCareController.getBenVitalDetailsFrmNurse("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenExaminationDetailsPNC() {
        when(pncServiceImpl.getPNCExaminationDetailsData(anyLong(), anyLong())).thenReturn("getPNCExaminationDetailsDataResponse");

        String result = postnatalCareController.getBenExaminationDetailsPNC("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenCaseRecordFromDoctorPNC() {
        when(pncServiceImpl.getBenCaseRecordFromDoctorPNC(anyLong(), anyLong())).thenReturn("getBenCaseRecordFromDoctorPNCResponse");

        String result = postnatalCareController.getBenCaseRecordFromDoctorPNC("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testUpdatePNCCareNurse() throws Exception {
        when(pncServiceImpl.updateBenPNCDetails(any(JsonObject.class))).thenReturn(0);

        String result = postnatalCareController.updatePNCCareNurse("requestObj");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testUpdateHistoryNurse() throws Exception {
        when(pncServiceImpl.updateBenHistoryDetails(any(JsonObject.class))).thenReturn(0);

        String result = postnatalCareController.updateHistoryNurse("requestObj");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testUpdateVitalNurse() throws Exception {
        when(pncServiceImpl.updateBenVitalDetails(any(JsonObject.class))).thenReturn(0);

        String result = postnatalCareController.updateVitalNurse("requestObj");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testUpdateGeneralOPDExaminationNurse() throws Exception {
        when(pncServiceImpl.updateBenExaminationDetails(any(JsonObject.class))).thenReturn(0);

        String result = postnatalCareController.updateGeneralOPDExaminationNurse("requestObj");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testUpdatePNCDoctorData() throws Exception {
        when(pncServiceImpl.updatePNCDoctorData(any(JsonObject.class), anyString())).thenReturn(Long.valueOf(1));

        String result = postnatalCareController.updatePNCDoctorData("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

