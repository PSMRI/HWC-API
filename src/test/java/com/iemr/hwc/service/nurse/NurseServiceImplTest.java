package com.iemr.hwc.service.nurse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.iemr.hwc.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.hwc.data.benFlowStatus.I_bendemographics;
import com.iemr.hwc.data.location.Districts;
import com.iemr.hwc.data.location.States;
import com.iemr.hwc.data.login.MasterVan;
import com.iemr.hwc.data.masterdata.registrar.GenderMaster;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.provider.ProviderServiceMapping;
import com.iemr.hwc.fhir.dto.visitDetailsMain.visitDetails.BenVisitsDTO;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.location.ZoneDistrictMapping;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;

@ExtendWith(MockitoExtension.class)
class NurseServiceImplTest {
    @Mock
    private BenVisitDetailRepo benVisitDetailRepo;

    @Mock
    private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;

    @InjectMocks
    private NurseServiceImpl nurseServiceImpl;

    @Test
    void testSetBenVisitDetailRepo() {
        (new NurseServiceImpl()).setBenVisitDetailRepo(mock(BenVisitDetailRepo.class));
    }

    @Test
    void testGetBeneficiaryVisitHistory() {
        // Arrange
        when(benVisitDetailRepo.getBeneficiaryVisitHistory(Mockito.<Long>any())).thenReturn(new ArrayList<>());

        // Act
        String actualBeneficiaryVisitHistory = nurseServiceImpl.getBeneficiaryVisitHistory(1L);

        // Assert
        verify(benVisitDetailRepo).getBeneficiaryVisitHistory(isA(Long.class));
        assertEquals("{\"benVisitDetails\":[]}", actualBeneficiaryVisitHistory);
    }

    @Test
    void testGetVisitByLocationAndLastModifDate() {
        // Arrange
        when(beneficiaryFlowStatusRepo.getVisitByLocationAndLastModifDate(Mockito.<Integer>any(), Mockito.<Timestamp>any()))
                .thenReturn(new ArrayList<>());

        // Act
        List<BenVisitsDTO> actualVisitByLocationAndLastModifDate = nurseServiceImpl.getVisitByLocationAndLastModifDate(1,
                mock(Timestamp.class));

        // Assert
        verify(beneficiaryFlowStatusRepo).getVisitByLocationAndLastModifDate(isA(Integer.class), isA(Timestamp.class));
        assertTrue(actualVisitByLocationAndLastModifDate.isEmpty());
    }

    @Test
    void testGetVisitByLocationAndLastModifDate2() {
        // Arrange
        I_bendemographics i_bendemographics = new I_bendemographics();
        i_bendemographics.setDistrictBranchID(1);
        i_bendemographics.setDistrictBranchName("janedoe/featurebranch");
        i_bendemographics.setDistrictID(1);
        i_bendemographics.setDistrictName("District Name");
        i_bendemographics.setServicePointID(1);
        i_bendemographics.setServicePointName("Service Point Name");

        GenderMaster m_gender = new GenderMaster();
        m_gender.setDeleted(true);
        m_gender.setGenderID((short) 1);
        m_gender.setGenderName("Gender Name");

        MasterVan masterVan = new MasterVan();
        masterVan.setDeleted(true);
        masterVan.setFacilityID(1);
        masterVan.setIsFacility(true);
        masterVan.setParkingPlaceID(1);
        masterVan.setProviderServiceMapID(1);
        masterVan.setSwymedEmailID("jane.doe@example.org");
        masterVan.setVanID(1);
        masterVan.setVanName("Van Name");
        masterVan.setVehicalNo("Vehical No");

        BeneficiaryFlowStatus beneficiaryFlowStatus = new BeneficiaryFlowStatus();
        beneficiaryFlowStatus.setAge("Age");
        beneficiaryFlowStatus.setAgeVal(42);
        beneficiaryFlowStatus.setAgentId("42");
        beneficiaryFlowStatus.setBenArrivedFlag(true);
        beneficiaryFlowStatus.setBenFlowID(1L);
        beneficiaryFlowStatus.setBenImage("Ben Image");
        beneficiaryFlowStatus.setBenName("Ben Name");
        beneficiaryFlowStatus.setBenPhoneMaps(new ArrayList<>());
        beneficiaryFlowStatus.setBenVisitCode(1L);
        beneficiaryFlowStatus.setBenVisitDate(mock(Timestamp.class));
        beneficiaryFlowStatus.setBenVisitID(1L);
        beneficiaryFlowStatus.setBenVisitNo((short) 1);
        beneficiaryFlowStatus.setBen_age_val(42);
        beneficiaryFlowStatus.setBeneficiaryID(1L);
        beneficiaryFlowStatus.setBeneficiaryName("Beneficiary Name");
        beneficiaryFlowStatus.setBeneficiaryRegID(1L);
        beneficiaryFlowStatus.setBloodGroup("Blood Group");
        beneficiaryFlowStatus.setConsultantID(1);
        beneficiaryFlowStatus.setConsultantName("Consultant Name");
        beneficiaryFlowStatus.setConsultationDate(mock(Timestamp.class));
        beneficiaryFlowStatus.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        beneficiaryFlowStatus.setCreatedDate(mock(Timestamp.class));
        beneficiaryFlowStatus.setDeleted(true);
        beneficiaryFlowStatus.setDistrictID(1);
        beneficiaryFlowStatus.setDistrictName("District Name");
        beneficiaryFlowStatus.setDoctorFlag((short) 1);
        beneficiaryFlowStatus.setFatherName("Father Name");
        beneficiaryFlowStatus.setFirstName("Jane");
        beneficiaryFlowStatus.setGenderID((short) 1);
        beneficiaryFlowStatus.setGenderName("Gender Name");
        beneficiaryFlowStatus.setI_bendemographics(i_bendemographics);
        beneficiaryFlowStatus.setIsMobile(true);
        beneficiaryFlowStatus.setLabIteration((short) 1);
        beneficiaryFlowStatus.setLab_technician_flag((short) 1);
        beneficiaryFlowStatus.setLastName("Doe");
        beneficiaryFlowStatus.setM_gender(m_gender);
        beneficiaryFlowStatus.setMasterVan(masterVan);
        beneficiaryFlowStatus.setModified_by("Jan 1, 2020 9:00am GMT+0100");
        beneficiaryFlowStatus.setModified_date(mock(Timestamp.class));
        beneficiaryFlowStatus.setNurseFlag((short) 1);
        beneficiaryFlowStatus.setOncologist_flag((short) 1);
        beneficiaryFlowStatus.setParkingPlaceID(1);
        beneficiaryFlowStatus.setPassToNurse(true);
        beneficiaryFlowStatus.setPharmacist_flag((short) 1);
        beneficiaryFlowStatus.setPreferredPhoneNum("6625550144");
        beneficiaryFlowStatus.setProcessed("Processed");
        beneficiaryFlowStatus.setProviderServiceMapID(1);
        beneficiaryFlowStatus.setProviderServiceMapId(1);
        beneficiaryFlowStatus.setRadiologist_flag((short) 1);
        beneficiaryFlowStatus.setReferredVisitCode(1L);
        beneficiaryFlowStatus.setReferred_visit_id(1L);
        beneficiaryFlowStatus.setRegistrationDate(mock(Timestamp.class));
        beneficiaryFlowStatus.setReservedForChange("Reserved For Change");
        beneficiaryFlowStatus.setServiceDate(mock(Timestamp.class));
        beneficiaryFlowStatus.setServicePointID(1);
        beneficiaryFlowStatus.setServicePointName("Service Point Name");
        beneficiaryFlowStatus.setSpecialist_flag((short) 1);
        beneficiaryFlowStatus.setSpouseName("Spouse Name");
        beneficiaryFlowStatus.setSubVisitCategory("Sub Visit Category");
        beneficiaryFlowStatus.setSyncedBy("Synced By");
        beneficiaryFlowStatus.setSyncedDate(mock(Timestamp.class));
        beneficiaryFlowStatus.setVanID(1);
        beneficiaryFlowStatus.setVanNo("Van No");
        beneficiaryFlowStatus.setVanSerialNo(1L);
        beneficiaryFlowStatus.setVehicalNo("Vehical No");
        beneficiaryFlowStatus.setVillageID(1);
        beneficiaryFlowStatus.setVillageName("Village Name");
        beneficiaryFlowStatus.setVisitCategory("Visit Category");
        beneficiaryFlowStatus.setVisitCode(1L);
        beneficiaryFlowStatus.setVisitDate(mock(Timestamp.class));
        beneficiaryFlowStatus.setVisitReason("Just cause");
        beneficiaryFlowStatus.setVisitSession(1);
        beneficiaryFlowStatus.setdOB(mock(Timestamp.class));
        beneficiaryFlowStatus.settCRequestDate(mock(Timestamp.class));
        beneficiaryFlowStatus.settCSpecialistUserID(1);
        beneficiaryFlowStatus.settC_SpecialistLabFlag((short) 1);

        ArrayList<BeneficiaryFlowStatus> beneficiaryFlowStatusList = new ArrayList<>();
        beneficiaryFlowStatusList.add(beneficiaryFlowStatus);
        when(beneficiaryFlowStatusRepo.getVisitByLocationAndLastModifDate(Mockito.<Integer>any(), Mockito.<Timestamp>any()))
                .thenReturn(beneficiaryFlowStatusList);

        States states = new States();
        states.setCountryID(1);
        states.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        states.setCreatedDate(mock(Timestamp.class));
        states.setDeleted(true);
        states.setLastModDate(mock(Timestamp.class));
        states.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        states.setStateCode("MD");
        states.setStateID(1);
        states.setStateIName("MD");
        states.setStateName("MD");

        ZoneDistrictMapping zoneDistrictMapping = new ZoneDistrictMapping();
        zoneDistrictMapping.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        zoneDistrictMapping.setCreatedDate(mock(Timestamp.class));
        zoneDistrictMapping.setDeleted(true);
        zoneDistrictMapping.setDistrictID(1);
        zoneDistrictMapping.setDistrictsSet(new HashSet<>());
        zoneDistrictMapping.setLastModDate(mock(Timestamp.class));
        zoneDistrictMapping.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        zoneDistrictMapping.setProcessed("Processed");
        zoneDistrictMapping.setProviderServiceMapID(1);
        zoneDistrictMapping.setZoneDistrictMapID(1);
        zoneDistrictMapping.setZoneID(1);

        Districts m_district = new Districts();
        m_district.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        m_district.setCreatedDate(mock(Timestamp.class));
        m_district.setDeleted(true);
        m_district.setDistrictID(1);
        m_district.setDistrictName("District Name");
        m_district.setGovtLGDDistrictID(1);
        m_district.setGovtLGDStateID(1);
        m_district.setLastModDate(mock(Timestamp.class));
        m_district.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        m_district.setStateID(1);
        m_district.setStates(states);
        m_district.setZoneDistrictMapping(zoneDistrictMapping);

        States state = new States();
        state.setCountryID(1);
        state.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        state.setCreatedDate(mock(Timestamp.class));
        state.setDeleted(true);
        state.setLastModDate(mock(Timestamp.class));
        state.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        state.setStateCode("MD");
        state.setStateID(1);
        state.setStateIName("MD");
        state.setStateName("MD");

        ProviderServiceMapping providerServiceMapping = new ProviderServiceMapping();
        providerServiceMapping.setAddress("42 Main St");
        providerServiceMapping.setCityID(1);
        providerServiceMapping.setCountryID(1);
        providerServiceMapping.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        providerServiceMapping.setCreatedDate(mock(Timestamp.class));
        providerServiceMapping.setDeleted(true);
        providerServiceMapping.setDistrictBlockID(1);
        providerServiceMapping.setDistrictID(1);
        providerServiceMapping.setLastModDate(mock(Timestamp.class));
        providerServiceMapping.setM_district(m_district);
        providerServiceMapping.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        providerServiceMapping.setProviderServiceMapID(1);
        providerServiceMapping.setServiceID((short) 1);
        providerServiceMapping.setServiceProviderID(1);
        providerServiceMapping.setState(state);
        providerServiceMapping.setStateID(1);
        providerServiceMapping.setStatusID(1);

        BeneficiaryVisitDetail beneficiaryVisitDetail = new BeneficiaryVisitDetail();
        beneficiaryVisitDetail.setAction("Action");
        beneficiaryVisitDetail.setActionId(1);
        beneficiaryVisitDetail.setActionIdPc(1);
        beneficiaryVisitDetail.setActionPc("Action Pc");
        beneficiaryVisitDetail.setAlgorithm("Algorithm");
        beneficiaryVisitDetail.setAlgorithmPc("Algorithm Pc");
        beneficiaryVisitDetail.setBenVisitID(1L);
        beneficiaryVisitDetail.setBeneficiaryRegID(1L);
        beneficiaryVisitDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        beneficiaryVisitDetail.setCreatedDate(mock(Timestamp.class));
        beneficiaryVisitDetail.setDeleted(true);
        beneficiaryVisitDetail.setDiseaseSummary("Disease Summary");
        beneficiaryVisitDetail.setDiseaseSummaryID(1);
        beneficiaryVisitDetail.setFileIDs(new Integer[]{1});
        beneficiaryVisitDetail.setFollowUpForFpMethod(new String[]{"Follow Up For Fp Method"});
        beneficiaryVisitDetail.setFpMethodFollowup("Fp Method Followup");
        beneficiaryVisitDetail.setFpSideeffects("Fp Sideeffects");
        beneficiaryVisitDetail.setHealthFacilityLocation("Health Facility Location");
        beneficiaryVisitDetail.setHealthFacilityType("Health Facility Type");
        beneficiaryVisitDetail.setInformationGiven("Information Given");
        beneficiaryVisitDetail.setLastModDate(mock(Timestamp.class));
        beneficiaryVisitDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        beneficiaryVisitDetail.setOtherFollowUpForFpMethod("Other Follow Up For Fp Method");
        beneficiaryVisitDetail.setOtherSideEffects("Other Side Effects");
        beneficiaryVisitDetail.setParkingPlaceID(1);
        beneficiaryVisitDetail.setPregnancyStatus("Pregnancy Status");
        beneficiaryVisitDetail.setPresentChiefComplaint("Present Chief Complaint");
        beneficiaryVisitDetail.setPresentChiefComplaintID("Present Chief Complaint ID");
        beneficiaryVisitDetail.setProcessed("Processed");
        beneficiaryVisitDetail.setProviderServiceMapID(1);
        beneficiaryVisitDetail.setProviderServiceMapping(providerServiceMapping);
        beneficiaryVisitDetail.setRecommendedAction("Recommended Action");
        beneficiaryVisitDetail.setRecommendedActionPc("Recommended Action Pc");
        beneficiaryVisitDetail.setRemarks("Remarks");
        beneficiaryVisitDetail.setRemarksPc("Remarks Pc");
        beneficiaryVisitDetail.setReportFilePath("/directory/foo.txt");
        beneficiaryVisitDetail.setReservedForChange("Reserved For Change");
        beneficiaryVisitDetail.setSelectedDiagnosis("Selected Diagnosis");
        beneficiaryVisitDetail.setSelectedDiagnosisID("Selected Diagnosis ID");
        beneficiaryVisitDetail.setServiceProviderName("Service Provider Name");
        beneficiaryVisitDetail.setSideEffects(new String[]{"Side Effects"});
        beneficiaryVisitDetail.setSubVisitCategory("Sub Visit Category");
        beneficiaryVisitDetail.setSyncedBy("Synced By");
        beneficiaryVisitDetail.setSyncedDate(mock(Timestamp.class));
        beneficiaryVisitDetail.setVanID(1);
        beneficiaryVisitDetail.setVanSerialNo(1L);
        beneficiaryVisitDetail.setVehicalNo("Vehical No");
        beneficiaryVisitDetail.setVisitCategory("Visit Category");
        beneficiaryVisitDetail.setVisitCategoryID(1);
        beneficiaryVisitDetail.setVisitCode(1L);
        beneficiaryVisitDetail.setVisitDateTime(mock(Timestamp.class));
        beneficiaryVisitDetail.setVisitFlowStatusFlag("Visit Flow Status Flag");
        beneficiaryVisitDetail.setVisitNo((short) 1);
        beneficiaryVisitDetail.setVisitReason("Just cause");
        beneficiaryVisitDetail.setVisitReasonID((short) 1);
        beneficiaryVisitDetail.setrCHID("R CHID");
        when(benVisitDetailRepo.getVisitDetailsByVisitIDAndLastModifDate(Mockito.<Long>any(), Mockito.<Timestamp>any()))
                .thenReturn(beneficiaryVisitDetail);

        // Act
        List<BenVisitsDTO> actualVisitByLocationAndLastModifDate = nurseServiceImpl.getVisitByLocationAndLastModifDate(1,
                mock(Timestamp.class));

        // Assert
        verify(beneficiaryFlowStatusRepo).getVisitByLocationAndLastModifDate(isA(Integer.class), isA(Timestamp.class));
        verify(benVisitDetailRepo).getVisitDetailsByVisitIDAndLastModifDate(isA(Long.class), isA(Timestamp.class));
        assertEquals(1, actualVisitByLocationAndLastModifDate.size());
        BenVisitsDTO getResult = actualVisitByLocationAndLastModifDate.get(0);
        assertEquals("Action", getResult.getBenVisitDetails().getAction());
        assertEquals(1, getResult.getSessionID().intValue());
        assertEquals(1L, getResult.getBenFlowID().longValue());
        assertEquals(1L, getResult.getBeneficiaryID().longValue());
    }
}
