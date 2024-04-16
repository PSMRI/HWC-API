package com.iemr.hwc.controller.registrar.master;

import com.iemr.hwc.service.common.master.RegistrarServiceMasterDataImpl;
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
class RegistrarMasterControllerTest {

    @Mock
    RegistrarServiceMasterDataImpl registrarServiceMasterDataImpl;
    @InjectMocks
    RegistrarMasterController registrarMasterController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMasterDataForRegistration() {
        when(registrarServiceMasterDataImpl.getRegMasterData()).thenReturn("getRegMasterDataResponse");

        String result = registrarMasterController.masterDataForRegistration("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

