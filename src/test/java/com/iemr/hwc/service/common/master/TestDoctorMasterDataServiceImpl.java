package com.iemr.hwc.service.common.master;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.google.gson.Gson;
import com.iemr.hwc.repo.masterrepo.anc.ServiceMasterRepo;
import com.iemr.hwc.repo.masterrepo.doctor.InstituteRepo;
import com.iemr.hwc.repo.masterrepo.doctor.PreMalignantLesionMasterRepo;

import java.util.*;
public class TestDoctorMasterDataServiceImpl {
	

	@RunWith(MockitoJUnitRunner.class)
	

	    @Mock
	    private PreMalignantLesionMasterRepo preMalignantLesionMasterRepo;

	    @Mock
	    private InstituteRepo instituteRepo;

	    @Mock
	    private ServiceMasterRepo serviceMasterRepo;

	    @InjectMocks
	    private DoctorMasterDataServiceImpl doctorMasterDataServiceImpl;

	    @Before
	    public void setUp() {
	        // Setup mock data
	        when(preMalignantLesionMasterRepo.getPreMalignantLesionMaster()).thenReturn(new ArrayList<>());
	        when(instituteRepo.getInstituteDetails(anyInt())).thenReturn(new ArrayList<>());
	        when(serviceMasterRepo.getAdditionalServices()).thenReturn(new ArrayList<>());
	    }

	    @Test
	    public void testGetCancerScreeningMasterDataForDoctor() {
	        int psmID = 1;

	        String result = doctorMasterDataServiceImpl.getCancerScreeningMasterDataForDoctor(psmID);

	        // Convert result to Map for easier assertion
	        Map<String, Object> resultMap = new Gson().fromJson(result, Map.class);

	        assertNotNull(resultMap);
	        assertTrue(resultMap.containsKey("preMalignantLesionTypes"));
	        assertTrue(resultMap.containsKey("higherHealthCare"));
	        assertTrue(resultMap.containsKey("additionalServices"));
	    }
	}

