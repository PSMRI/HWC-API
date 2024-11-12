package com.iemr.hwc.controller.quickBlox;

import com.iemr.hwc.service.quickBlox.QuickbloxService;
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
class QuickbloxControllerTest {

    @Mock
    QuickbloxService quickbloxService;
    @InjectMocks
    QuickbloxController quickbloxController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetquickbloxIds() throws Exception {
        when(quickbloxService.getQuickbloxIds(anyString())).thenReturn("getQuickbloxIdsResponse");

        String result = quickbloxController.getquickbloxIds("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

