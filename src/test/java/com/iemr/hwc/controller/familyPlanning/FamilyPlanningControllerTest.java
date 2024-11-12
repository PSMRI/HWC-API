package com.iemr.hwc.controller.familyPlanning;

import com.google.gson.JsonObject;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.service.family_planning.FamilyPlanningService;
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
class FamilyPlanningControllerTest {

    @Mock
    FamilyPlanningService familyPlanningService;
    @InjectMocks
    FamilyPlanningController familyPlanningController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveFamilyPlanningNuseData() throws Exception {
        when(familyPlanningService.saveNurseDataFP(any(JsonObject.class), anyString())).thenReturn("saveNurseDataFPResponse");

        String result = familyPlanningController.saveFamilyPlanningNuseData("requestOBJ", "Authorization");
        verify(familyPlanningService).deleteVisitDetails(any(JsonObject.class));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testSaveFamilyPlanningDoctorData() throws Exception {
        when(familyPlanningService.saveDoctorDataFP(any(JsonObject.class), anyString())).thenReturn(0);

        String result = familyPlanningController.saveFamilyPlanningDoctorData("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenVisitDetailsFrmNurseFamilyPlanning() throws Exception {
        when(familyPlanningService.getBenVisitDetailsFrmNurseFP(anyLong(), anyLong())).thenReturn("getBenVisitDetailsFrmNurseFPResponse");

        String result = familyPlanningController.getBenVisitDetailsFrmNurseFamilyPlanning(new CommonUtilityClass());
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenVitalDetailsFrmNurseFamilyPlanning() throws Exception {
        when(familyPlanningService.getBeneficiaryVitalDetailsFP(anyLong(), anyLong())).thenReturn("getBeneficiaryVitalDetailsFPResponse");

        String result = familyPlanningController.getBenVitalDetailsFrmNurseFamilyPlanning(new CommonUtilityClass());
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenFPDetailsFrmNurseFamilyPlanning() throws Exception {
        when(familyPlanningService.getBeneficiaryFPDetailsFP(anyLong(), anyLong())).thenReturn("getBeneficiaryFPDetailsFPResponse");

        String result = familyPlanningController.getBenFPDetailsFrmNurseFamilyPlanning(new CommonUtilityClass());
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenCaseRecordFromDoctor() throws IEMRException {
        when(familyPlanningService.getBenCaseRecordFromDoctorFP(anyLong(), anyLong())).thenReturn("getBenCaseRecordFromDoctorFPResponse");

        String result = familyPlanningController.getBenCaseRecordFromDoctor("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testUpdateFamilyPlanningNurse() throws IEMRException {
        when(familyPlanningService.updateFPDataFP(any(JsonObject.class))).thenReturn("updateFPDataFPResponse");

        String result = familyPlanningController.updateFamilyPlanningNurse("requestObj");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testUpdateVitalNurseFamilyPlanning() throws Exception {
        when(familyPlanningService.updateBenVitalDetailsFP(any(JsonObject.class))).thenReturn(0);

        String result = familyPlanningController.updateVitalNurseFamilyPlanning("requestObj");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testUpdateFamilyPlanningDoctorData() throws Exception {
        when(familyPlanningService.updateDoctorDataFP(any(JsonObject.class), anyString())).thenReturn(Long.valueOf(1));

        String result = familyPlanningController.updateFamilyPlanningDoctorData("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

