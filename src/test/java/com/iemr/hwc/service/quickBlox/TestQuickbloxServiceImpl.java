package com.iemr.hwc.service.quickBlox;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.google.gson.Gson;
import com.iemr.hwc.data.quickBlox.Quickblox;
import com.iemr.hwc.repo.quickBlox.QuickBloxRepo;
import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.mapper.InputMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
public class TestQuickbloxServiceImpl {

	

	@ExtendWith(MockitoExtension.class)
	
	    @Mock
	    private QuickBloxRepo quickBloxRepo;

	    @InjectMocks
	    private QuickbloxServiceImpl quickbloxService;

	    private Quickblox quickblox;
	    private String requestObj;
	    private Gson gson;

	    @BeforeEach
	    public void setUp() {
	        gson = new Gson();
	        quickblox = new Quickblox();
	        quickblox.setSpecialistUserID(1L);
	        quickblox.setSpecialistQuickbloxID(123L);
	        quickblox.setSpecialistBenQuickbloxID(456L);
	        requestObj = gson.toJson(quickblox);
	    }

	    @Test
	    public void testGetQuickbloxIds_Success() throws IEMRException {
	        when(quickBloxRepo.getQuickbloxIds(1L)).thenReturn(quickblox);

	        String response = quickbloxService.getQuickbloxIds(requestObj);

	        Map<String, Object> expectedResponse = new HashMap<>();
	        expectedResponse.put("quickbloxIds", quickblox);
	        String expectedJson = gson.toJson(expectedResponse);

	        assertEquals(expectedJson, response);
	        verify(quickBloxRepo, times(1)).getQuickbloxIds(1L);
	    }

	    @Test
	    public void testGetQuickbloxIds_NotFound() {
	        Quickblox emptyQuickblox = new Quickblox();
	        emptyQuickblox.setSpecialistUserID(1L);
	        when(quickBloxRepo.getQuickbloxIds(1L)).thenReturn(emptyQuickblox);

	        IEMRException exception = assertThrows(IEMRException.class, () -> {
	            quickbloxService.getQuickbloxIds(requestObj);
	        });

	        assertEquals("quickblox user id not found for specialist", exception.getMessage());
	        verify(quickBloxRepo, times(1)).getQuickbloxIds(1L);
	    }
	}

