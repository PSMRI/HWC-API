package com.iemr.hwc.controller.masterVillage;

import com.iemr.hwc.data.login.UserMasterVillageDTO;
import com.iemr.hwc.data.login.UsersMasterVillage;
import com.iemr.hwc.service.masterVillage.MasterVillageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class MasterVillageControllerTest {

    @Mock
    MasterVillageService masterVillageService;
    @InjectMocks
    MasterVillageController masterVillageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSetMasterVillage() {
        when(masterVillageService.setMasterVillage(anyLong(), anyInt())).thenReturn("setMasterVillageResponse");

        String result = masterVillageController.setMasterVillage(new UserMasterVillageDTO());
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetMasterVillage() {
        when(masterVillageService.getMasterVillage(anyLong())).thenReturn(new UsersMasterVillage());

        String result = masterVillageController.getMasterVillage(Long.valueOf(1));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme