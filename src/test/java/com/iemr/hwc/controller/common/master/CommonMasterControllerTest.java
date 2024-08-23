package com.iemr.hwc.controller.common.master;

import com.iemr.hwc.service.common.master.CommonMasterServiceImpl;
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
class CommonMasterControllerTest {

    @Mock
    CommonMasterServiceImpl commonMasterServiceImpl;
    @InjectMocks
    CommonMasterController commonMasterController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetVisitReasonAndCategories() {
        when(commonMasterServiceImpl.getVisitReasonAndCategories()).thenReturn("getVisitReasonAndCategoriesResponse");

        String result = commonMasterController.getVisitReasonAndCategories();
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testNurseMasterData() throws IEMRException {
        when(commonMasterServiceImpl.getMasterDataForNurse(anyInt(), anyInt(), anyString())).thenReturn("getMasterDataForNurseResponse");

        String result = commonMasterController.NurseMasterData(Integer.valueOf(0), Integer.valueOf(0), "gender");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testDoctorMasterData() {
        when(commonMasterServiceImpl.getMasterDataForDoctor(anyInt(), anyInt(), anyString(), anyInt(), anyInt())).thenReturn("getMasterDataForDoctorResponse");

        String result = commonMasterController.DoctorMasterData(Integer.valueOf(0), Integer.valueOf(0), "gender", Integer.valueOf(0), Integer.valueOf(0));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetVaccineDetailsForCISID() throws IEMRException {
        when(commonMasterServiceImpl.getVaccineDetailsForCISID(anyInt(), anyInt())).thenReturn("getVaccineDetailsForCISIDResponse");

        String result = commonMasterController.getVaccineDetailsForCISID(Integer.valueOf(0), Integer.valueOf(0));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

