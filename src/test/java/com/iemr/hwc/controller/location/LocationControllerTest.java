package com.iemr.hwc.controller.location;

import com.iemr.hwc.service.location.LocationServiceImpl;
import org.json.JSONObject;
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
class LocationControllerTest {


    @Mock
    LocationServiceImpl locationServiceImpl;
    @InjectMocks
    LocationController locationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCountryMaster() {
        when(locationServiceImpl.getCountryList()).thenReturn("getCountryListResponse");

        String result = locationController.getCountryMaster();
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetCountryCityMaster() {
        when(locationServiceImpl.getCountryCityList(anyInt())).thenReturn("getCountryCityListResponse");

        String result = locationController.getCountryCityMaster(Integer.valueOf(0));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetStateMaster() {
        when(locationServiceImpl.getStateList()).thenReturn("getStateListResponse");

        String result = locationController.getStateMaster();
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetDistrictMaster() {
        when(locationServiceImpl.getDistrictList(anyInt())).thenReturn("getDistrictListResponse");

        String result = locationController.getDistrictMaster(Integer.valueOf(0));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetLocationMaster() {
        when(locationServiceImpl.getLocationMasterData(anyInt())).thenReturn("getLocationMasterDataResponse");

        String result = locationController.getLocationMaster(Integer.valueOf(0));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetDistrictBlockMaster() {
        when(locationServiceImpl.getDistrictBlockList(anyInt())).thenReturn("getDistrictBlockListResponse");

        String result = locationController.getDistrictBlockMaster(Integer.valueOf(0));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetVillageMaster() {
        when(locationServiceImpl.getVillageMasterFromBlockID(anyInt())).thenReturn("getVillageMasterFromBlockIDResponse");

        String result = locationController.getVillageMaster(Integer.valueOf(0));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetVillageByDistrictID() {
        when(locationServiceImpl.getVillageListByDistrictID(anyInt())).thenReturn("getVillageListByDistrictIDResponse");

        String result = locationController.getVillageByDistrictID(Integer.valueOf(0));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetLocDetailsBasedOnSpIDAndPsmIDNew() {
        when(locationServiceImpl.getLocDetailsNew(anyInt(), anyInt(), any(JSONObject.class))).thenReturn("getLocDetailsNewResponse");

        String result = locationController.getLocDetailsBasedOnSpIDAndPsmIDNew("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

}

