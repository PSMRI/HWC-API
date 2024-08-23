package com.iemr.hwc.controller.registrar.main;

import com.google.gson.JsonObject;
import com.iemr.hwc.data.registrar.BeneficiaryData;
import com.iemr.hwc.data.registrar.V_BenAdvanceSearch;
import com.iemr.hwc.service.common.master.RegistrarServiceMasterDataImpl;
import com.iemr.hwc.service.nurse.NurseServiceImpl;
import com.iemr.hwc.service.registrar.RegistrarServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import org.json.JSONException;

@ExtendWith(MockitoExtension.class)
class RegistrarControllerTest {


    @Mock
    RegistrarServiceImpl registrarServiceImpl;
    @Mock
    RegistrarServiceMasterDataImpl registrarServiceMasterDataImpl;
    @Mock
    NurseServiceImpl nurseServiceImpl;

    @InjectMocks
    RegistrarController registrarController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRegistrarWorkList() throws Exception {
        when(registrarServiceImpl.getRegWorkList(anyInt())).thenReturn("getRegWorkListResponse");

        String result = registrarController.getRegistrarWorkList("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testQuickSearchBeneficiary() {
        when(registrarServiceImpl.getQuickSearchBenData(anyString())).thenReturn("getQuickSearchBenDataResponse");

        String result = registrarController.quickSearchBeneficiary("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testAdvanceSearch() {
        when(registrarServiceImpl.getAdvanceSearchBenData(any(V_BenAdvanceSearch.class))).thenReturn("getAdvanceSearchBenDataResponse");

        String result = registrarController.advanceSearch("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenDetailsByRegID() {
        when(registrarServiceMasterDataImpl.getBenDetailsByRegID(anyLong())).thenReturn("getBenDetailsByRegIDResponse");

        String result = registrarController.getBenDetailsByRegID("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBeneficiaryDetails() {
        when(registrarServiceImpl.getBeneficiaryDetails(anyLong())).thenReturn("getBeneficiaryDetailsResponse");

        String result = registrarController.getBeneficiaryDetails("requestObj");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBeneficiaryImage() {
        when(registrarServiceImpl.getBenImage(anyLong())).thenReturn("getBenImageResponse");

        String result = registrarController.getBeneficiaryImage("requestObj");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testQuickSearchNew() {
        when(registrarServiceImpl.beneficiaryQuickSearch(anyString(), anyString())).thenReturn("beneficiaryQuickSearchResponse");

        String result = registrarController.quickSearchNew("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testAdvanceSearchNew() {
        when(registrarServiceImpl.beneficiaryAdvanceSearch(anyString(), anyString())).thenReturn("beneficiaryAdvanceSearchResponse");

        String result = registrarController.advanceSearchNew("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testBenAdvanceSearchNew() {
        when(registrarServiceImpl.beneficiaryAdvanceSearch(anyString(), anyString())).thenReturn("beneficiaryAdvanceSearchResponse");

        String result = registrarController.benAdvanceSearchNew("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenDetailsForLeftSidePanelByRegID() {
        when(registrarServiceMasterDataImpl.getBenDetailsForLeftSideByRegIDNew(anyLong(), anyLong(), anyString(), anyString())).thenReturn("getBenDetailsForLeftSideByRegIDNewResponse");

        String result = registrarController.getBenDetailsForLeftSidePanelByRegID("comingRequest", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenImage() throws Exception {
        when(registrarServiceMasterDataImpl.getBenImageFromIdentityAPI(anyString(), anyString())).thenReturn("getBenImageFromIdentityAPIResponse");

        String result = registrarController.getBenImage("requestObj", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testCreateBeneficiary() {
        when(registrarServiceImpl.createBeneficiary(any(JsonObject.class))).thenReturn(new BeneficiaryData(Long.valueOf(1), "beneficiaryID", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
        when(registrarServiceImpl.createBeneficiaryDemographic(any(JsonObject.class), anyLong())).thenReturn(Long.valueOf(1));
        when(registrarServiceImpl.createBeneficiaryDemographicAdditional(any(JsonObject.class), anyLong())).thenReturn(Long.valueOf(1));
        when(registrarServiceImpl.createBeneficiaryImage(any(JsonObject.class), anyLong())).thenReturn(Long.valueOf(1));
        when(registrarServiceImpl.createBeneficiaryPhoneMapping(any(JsonObject.class), anyLong())).thenReturn(Long.valueOf(1));
        when(registrarServiceImpl.createBenGovIdMapping(any(JsonObject.class), anyLong())).thenReturn(0);

        String result = registrarController.createBeneficiary("comingRequest", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testRegistrarBeneficaryRegistrationNew() throws Exception {
        when(registrarServiceImpl.registerBeneficiary(anyString(), anyString())).thenReturn("registerBeneficiaryResponse");

        String result = registrarController.registrarBeneficaryRegistrationNew("comingReq", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testUpdateBeneficiary() {
        when(registrarServiceImpl.updateBeneficiary(any(JsonObject.class))).thenReturn(0);
        when(registrarServiceImpl.updateBeneficiaryDemographic(any(JsonObject.class), anyLong())).thenReturn(0);
        when(registrarServiceImpl.updateBeneficiaryPhoneMapping(any(JsonObject.class), anyLong())).thenReturn(0);
        when(registrarServiceImpl.updateBenGovIdMapping(any(JsonObject.class), anyLong())).thenReturn(0);
        when(registrarServiceImpl.updateBeneficiaryDemographicAdditional(any(JsonObject.class), anyLong())).thenReturn(0);
        when(registrarServiceImpl.updateBeneficiaryImage(any(JsonObject.class), anyLong())).thenReturn(0);

        String result = registrarController.updateBeneficiary("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testCreateReVisitForBenToNurse() throws Exception {
        when(registrarServiceImpl.searchAndSubmitBeneficiaryToNurse(anyString())).thenReturn(0);

        String result = registrarController.createReVisitForBenToNurse("requestOBJ");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testBeneficiaryUpdate() throws Exception {
        when(registrarServiceImpl.updateBeneficiary(anyString(), anyString())).thenReturn(Integer.valueOf(0));

        String result = registrarController.beneficiaryUpdate("requestOBJ", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

