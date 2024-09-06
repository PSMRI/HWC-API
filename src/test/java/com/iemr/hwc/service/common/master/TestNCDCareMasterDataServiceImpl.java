package com.iemr.hwc.service.common.master;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.google.gson.Gson;
import com.iemr.hwc.data.masterdata.ncdcare.NCDCareType;
import com.iemr.hwc.data.masterdata.ncdscreening.NCDScreeningCondition;
import com.iemr.hwc.repo.masterrepo.ncdCare.NCDCareTypeRepo;

import java.util.*;
public class TestNCDCareMasterDataServiceImpl {
	

	@RunWith(MockitoJUnitRunner.class)
	

	    @Mock
	    private NCDScreeningMasterServiceImpl ncdScreeningMasterServiceImpl;

	    @Mock
	    private NCDCareTypeRepo ncdCareTypeRepo;

	    @InjectMocks
	    private NCDCareMasterDataServiceImpl ncdCareMasterDataServiceImpl;

	    @Before
	    public void setUp() {
	        // Setup mock behavior
	        when(ncdScreeningMasterServiceImpl.getNCDScreeningConditions()).thenReturn(new ArrayList<Object[]>());
	        when(ncdCareTypeRepo.getNCDCareTypes()).thenReturn(new ArrayList<Object[]>());
	    }

	    @Test
	    public void testGetNCDCareMasterData() {
	        // Expected result
	        Map<String, Object> expectedMap = new HashMap<>();
	        expectedMap.put("ncdCareConditions", NCDScreeningCondition.getNCDScreeningCondition(new ArrayList<Object[]>()));
	        expectedMap.put("ncdCareTypes", NCDCareType.getNCDCareTypes(new ArrayList<Object[]>()));
	        String expectedJson = new Gson().toJson(expectedMap);

	        // Actual result
	        String actualJson = ncdCareMasterDataServiceImpl.getNCDCareMasterData();

	        // Assert
	        assertEquals(expectedJson, actualJson);
	    }
	}

