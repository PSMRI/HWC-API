package com.iemr.hwc.service.neonatal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.iemr.hwc.data.anc.ChildVaccineDetail1;
import com.iemr.hwc.data.anc.WrapperImmunizationHistory;
import com.iemr.hwc.data.location.Districts;
import com.iemr.hwc.data.location.States;
import com.iemr.hwc.data.neonatal.ImmunizationServices;
import com.iemr.hwc.data.neonatal.InfantBirthDetails;
import com.iemr.hwc.data.neonatal.Vaccines;
import com.iemr.hwc.data.nurse.BenAnthropometryDetail;
import com.iemr.hwc.data.nurse.BenPhysicalVitalDetail;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.provider.ProviderServiceMapping;
import com.iemr.hwc.data.quickConsultation.PrescriptionDetail;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.repo.location.ZoneDistrictMapping;
import com.iemr.hwc.repo.neonatal.FollowUpForImmunizationRepo;
import com.iemr.hwc.repo.neonatal.ImmunizationServicesRepo;
import com.iemr.hwc.repo.neonatal.InfantBirthDetailsRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.nurse.CDSSRepo;
import com.iemr.hwc.repo.nurse.anc.BenAdherenceRepo;
import com.iemr.hwc.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonServiceImpl;
import com.iemr.hwc.service.generalOPD.GeneralOPDDoctorServiceImpl;
import com.iemr.hwc.service.labtechnician.LabTechnicianServiceImpl;
import com.iemr.hwc.service.tele_consultation.SMSGatewayServiceImpl;
import com.iemr.hwc.utils.exception.IEMRException;

@ExtendWith(MockitoExtension.class)
class NeonatalServiceImplTest {
    @Mock
    private BenAdherenceRepo benAdherenceRepo;

    @Mock
    private BenChiefComplaintRepo benChiefComplaintRepo;

    @Mock
    private BenVisitDetailRepo benVisitDetailRepo;

    @Mock
    private CDSSRepo cDSSRepo;

    @Mock
    private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;

    @Mock
    private CommonDoctorServiceImpl commonDoctorServiceImpl;

    @Mock
    private CommonNurseServiceImpl commonNurseServiceImpl;

    @Mock
    private CommonServiceImpl commonServiceImpl;

    @Mock
    private FollowUpForImmunizationRepo followUpForImmunizationRepo;

    @Mock
    private GeneralOPDDoctorServiceImpl generalOPDDoctorServiceImpl;

    @Mock
    private ImmunizationServicesRepo immunizationServicesRepo;

    @Mock
    private InfantBirthDetailsRepo infantBirthDetailsRepo;

    @Mock
    private LabTechnicianServiceImpl labTechnicianServiceImpl;

    @InjectMocks
    private NeonatalServiceImpl neonatalServiceImpl;

    @Mock
    private SMSGatewayServiceImpl sMSGatewayServiceImpl;

    @Test
    void testSaveNurseData() throws Exception {
        // Arrange, Act and Assert
        assertThrows(RuntimeException.class, () -> neonatalServiceImpl.saveNurseData(new JsonObject(), "JaneDoe"));
        assertThrows(RuntimeException.class, () -> neonatalServiceImpl.saveNurseData(null, "JaneDoe"));
    }

    @Test
    void testDeleteVisitDetails() throws Exception {
        // Arrange
        when(benAdherenceRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benChiefComplaintRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1L);
        when(cDSSRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.add("visitDetails", new JsonArray(3));

        // Act
        neonatalServiceImpl.deleteVisitDetails(requestOBJ);

        // Assert
        verify(benVisitDetailRepo).deleteVisitDetails(isA(Long.class));
        verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
        verify(cDSSRepo).deleteVisitDetails(isA(Long.class));
        verify(benAdherenceRepo).deleteVisitDetails(isA(Long.class));
        verify(benChiefComplaintRepo).deleteVisitDetails(isA(Long.class));
    }

    @Test
    void testDeleteVisitDetails2() throws Exception {
        // Arrange
        when(benAdherenceRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benChiefComplaintRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1L);
        when(cDSSRepo.deleteVisitDetails(Mockito.<Long>any())).thenThrow(new RuntimeException("visitDetails"));

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.add("visitDetails", new JsonArray(3));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> neonatalServiceImpl.deleteVisitDetails(requestOBJ));
        verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
        verify(cDSSRepo).deleteVisitDetails(isA(Long.class));
        verify(benAdherenceRepo).deleteVisitDetails(isA(Long.class));
        verify(benChiefComplaintRepo).deleteVisitDetails(isA(Long.class));
    }

    @Test
    void testDeleteVisitDetails3() throws Exception {
        // Arrange
        when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(null);

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.add("visitDetails", new JsonArray(3));

        // Act
        neonatalServiceImpl.deleteVisitDetails(requestOBJ);

        // Assert
        verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
    }

    @Test
    void testDeleteVisitDetails4() throws Exception {
        // Arrange
        when(benAdherenceRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benChiefComplaintRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1L);
        when(cDSSRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.add("Property", new JsonArray(3));
        requestOBJ.add("visitDetails", new JsonArray(3));

        // Act
        neonatalServiceImpl.deleteVisitDetails(requestOBJ);

        // Assert
        verify(benVisitDetailRepo).deleteVisitDetails(isA(Long.class));
        verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
        verify(cDSSRepo).deleteVisitDetails(isA(Long.class));
        verify(benAdherenceRepo).deleteVisitDetails(isA(Long.class));
        verify(benChiefComplaintRepo).deleteVisitDetails(isA(Long.class));
    }

    @Test
    void testDeleteVisitDetails5() throws Exception {
        // Arrange
        when(benAdherenceRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benChiefComplaintRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1L);
        when(cDSSRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.addProperty("Property", "visitDetails");
        requestOBJ.add("visitDetails", new JsonArray(3));

        // Act
        neonatalServiceImpl.deleteVisitDetails(requestOBJ);

        // Assert
        verify(benVisitDetailRepo).deleteVisitDetails(isA(Long.class));
        verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
        verify(cDSSRepo).deleteVisitDetails(isA(Long.class));
        verify(benAdherenceRepo).deleteVisitDetails(isA(Long.class));
        verify(benChiefComplaintRepo).deleteVisitDetails(isA(Long.class));
    }

    @Test
    void testDeleteVisitDetails6() throws Exception {
        // Arrange
        when(benAdherenceRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benChiefComplaintRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1L);
        when(cDSSRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.addProperty("Property", Integer.valueOf(1));
        requestOBJ.add("visitDetails", new JsonArray(3));

        // Act
        neonatalServiceImpl.deleteVisitDetails(requestOBJ);

        // Assert
        verify(benVisitDetailRepo).deleteVisitDetails(isA(Long.class));
        verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
        verify(cDSSRepo).deleteVisitDetails(isA(Long.class));
        verify(benAdherenceRepo).deleteVisitDetails(isA(Long.class));
        verify(benChiefComplaintRepo).deleteVisitDetails(isA(Long.class));
    }

    @Test
    void testDeleteVisitDetails7() throws Exception {
        // Arrange
        when(benAdherenceRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benChiefComplaintRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1L);
        when(cDSSRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.addProperty("Property", false);
        requestOBJ.add("visitDetails", new JsonArray(3));

        // Act
        neonatalServiceImpl.deleteVisitDetails(requestOBJ);

        // Assert
        verify(benVisitDetailRepo).deleteVisitDetails(isA(Long.class));
        verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
        verify(cDSSRepo).deleteVisitDetails(isA(Long.class));
        verify(benAdherenceRepo).deleteVisitDetails(isA(Long.class));
        verify(benChiefComplaintRepo).deleteVisitDetails(isA(Long.class));
    }

    @Test
    void testDeleteVisitDetails8() throws Exception {
        // Arrange
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.add("visitDetails", value);

        // Act
        neonatalServiceImpl.deleteVisitDetails(requestOBJ);

        // Assert that nothing has changed
        verify(value).isJsonNull();
    }

    @Test
    void testDeleteVisitDetails9() throws Exception {
        // Arrange
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenThrow(new RuntimeException("visitDetails"));

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.add("visitDetails", value);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> neonatalServiceImpl.deleteVisitDetails(requestOBJ));
        verify(value).isJsonNull();
    }

    @Test
    void testSaveDoctorDataNNI() throws Exception {
        // Arrange, Act and Assert
        assertEquals(1, neonatalServiceImpl.saveDoctorDataNNI(null, "JaneDoe"));
    }

    @Test
    void testGetBenVisitDetailsFrmNurseNNI() throws Exception {
        // Arrange
        Timestamp createdDate = mock(Timestamp.class);
        when(createdDate.getTime()).thenReturn(10L);
        Timestamp lastModDate = mock(Timestamp.class);
        when(lastModDate.getTime()).thenReturn(10L);

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
        Timestamp syncedDate = mock(Timestamp.class);
        when(syncedDate.getTime()).thenReturn(10L);
        Timestamp visitDateTime = mock(Timestamp.class);
        when(visitDateTime.getTime()).thenReturn(10L);

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
        beneficiaryVisitDetail.setCreatedDate(createdDate);
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
        beneficiaryVisitDetail.setLastModDate(lastModDate);
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
        beneficiaryVisitDetail.setSyncedDate(syncedDate);
        beneficiaryVisitDetail.setVanID(1);
        beneficiaryVisitDetail.setVanSerialNo(1L);
        beneficiaryVisitDetail.setVehicalNo("Vehical No");
        beneficiaryVisitDetail.setVisitCategory("Visit Category");
        beneficiaryVisitDetail.setVisitCategoryID(1);
        beneficiaryVisitDetail.setVisitCode(1L);
        beneficiaryVisitDetail.setVisitDateTime(visitDateTime);
        beneficiaryVisitDetail.setVisitFlowStatusFlag("Visit Flow Status Flag");
        beneficiaryVisitDetail.setVisitNo((short) 1);
        beneficiaryVisitDetail.setVisitReason("Just cause");
        beneficiaryVisitDetail.setVisitReasonID((short) 1);
        beneficiaryVisitDetail.setrCHID("R CHID");
        when(commonNurseServiceImpl.getCSVisitDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(beneficiaryVisitDetail);
        when(commonNurseServiceImpl.getBenCdss(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn("Ben Cdss");
        when(commonNurseServiceImpl.getBenChiefComplaints(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Ben Chief Complaints");

        // Act
        neonatalServiceImpl.getBenVisitDetailsFrmNurseNNI(1L, 1L);

        // Assert
        verify(commonNurseServiceImpl).getBenCdss(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getBenChiefComplaints(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getCSVisitDetails(isA(Long.class), isA(Long.class));
        verify(createdDate).getTime();
        verify(lastModDate).getTime();
        verify(syncedDate).getTime();
        verify(visitDateTime).getTime();
    }

    @Test
    void testGetBenVisitDetailsFrmNurseNNI2() throws Exception {
        // Arrange
        Timestamp createdDate = mock(Timestamp.class);
        when(createdDate.getTime()).thenReturn(10L);
        Timestamp lastModDate = mock(Timestamp.class);
        when(lastModDate.getTime()).thenReturn(10L);

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
        Timestamp syncedDate = mock(Timestamp.class);
        when(syncedDate.getTime()).thenReturn(10L);
        Timestamp visitDateTime = mock(Timestamp.class);
        when(visitDateTime.getTime()).thenThrow(new RuntimeException("neonatalNurseVisitDetail"));

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
        beneficiaryVisitDetail.setCreatedDate(createdDate);
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
        beneficiaryVisitDetail.setLastModDate(lastModDate);
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
        beneficiaryVisitDetail.setSyncedDate(syncedDate);
        beneficiaryVisitDetail.setVanID(1);
        beneficiaryVisitDetail.setVanSerialNo(1L);
        beneficiaryVisitDetail.setVehicalNo("Vehical No");
        beneficiaryVisitDetail.setVisitCategory("Visit Category");
        beneficiaryVisitDetail.setVisitCategoryID(1);
        beneficiaryVisitDetail.setVisitCode(1L);
        beneficiaryVisitDetail.setVisitDateTime(visitDateTime);
        beneficiaryVisitDetail.setVisitFlowStatusFlag("Visit Flow Status Flag");
        beneficiaryVisitDetail.setVisitNo((short) 1);
        beneficiaryVisitDetail.setVisitReason("Just cause");
        beneficiaryVisitDetail.setVisitReasonID((short) 1);
        beneficiaryVisitDetail.setrCHID("R CHID");
        when(commonNurseServiceImpl.getCSVisitDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(beneficiaryVisitDetail);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> neonatalServiceImpl.getBenVisitDetailsFrmNurseNNI(1L, 1L));
        verify(commonNurseServiceImpl).getCSVisitDetails(isA(Long.class), isA(Long.class));
        verify(visitDateTime).getTime();
    }

    @Test
    void testGetBenVisitDetailsFrmNurseNNI3() throws Exception {
        // Arrange
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
        BeneficiaryVisitDetail beneficiaryVisitDetail = mock(BeneficiaryVisitDetail.class);
        doNothing().when(beneficiaryVisitDetail).setAction(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setActionId(Mockito.<Integer>any());
        doNothing().when(beneficiaryVisitDetail).setActionIdPc(Mockito.<Integer>any());
        doNothing().when(beneficiaryVisitDetail).setActionPc(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setAlgorithm(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setAlgorithmPc(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setBenVisitID(Mockito.<Long>any());
        doNothing().when(beneficiaryVisitDetail).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(beneficiaryVisitDetail).setCreatedBy(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryVisitDetail).setDeleted(Mockito.<Boolean>any());
        doNothing().when(beneficiaryVisitDetail).setDiseaseSummary(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setDiseaseSummaryID(Mockito.<Integer>any());
        doNothing().when(beneficiaryVisitDetail).setFileIDs(Mockito.<Integer[]>any());
        doNothing().when(beneficiaryVisitDetail).setFollowUpForFpMethod(Mockito.<String[]>any());
        doNothing().when(beneficiaryVisitDetail).setFpMethodFollowup(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setFpSideeffects(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setHealthFacilityLocation(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setHealthFacilityType(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setInformationGiven(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryVisitDetail).setModifiedBy(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setOtherFollowUpForFpMethod(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setOtherSideEffects(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(beneficiaryVisitDetail).setPregnancyStatus(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setPresentChiefComplaint(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setPresentChiefComplaintID(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setProcessed(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(beneficiaryVisitDetail).setProviderServiceMapping(Mockito.<ProviderServiceMapping>any());
        doNothing().when(beneficiaryVisitDetail).setRecommendedAction(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setRecommendedActionPc(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setRemarks(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setRemarksPc(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setReportFilePath(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setReservedForChange(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setSelectedDiagnosis(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setSelectedDiagnosisID(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setServiceProviderName(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setSideEffects(Mockito.<String[]>any());
        doNothing().when(beneficiaryVisitDetail).setSubVisitCategory(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setSyncedBy(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryVisitDetail).setVanID(Mockito.<Integer>any());
        doNothing().when(beneficiaryVisitDetail).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(beneficiaryVisitDetail).setVehicalNo(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setVisitCategory(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setVisitCategoryID(Mockito.<Integer>any());
        doNothing().when(beneficiaryVisitDetail).setVisitCode(Mockito.<Long>any());
        doNothing().when(beneficiaryVisitDetail).setVisitDateTime(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryVisitDetail).setVisitFlowStatusFlag(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setVisitNo(Mockito.<Short>any());
        doNothing().when(beneficiaryVisitDetail).setVisitReason(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setVisitReasonID(Mockito.<Short>any());
        doNothing().when(beneficiaryVisitDetail).setrCHID(Mockito.<String>any());
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
        when(commonNurseServiceImpl.getCSVisitDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(beneficiaryVisitDetail);
        when(commonNurseServiceImpl.getBenCdss(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn("Ben Cdss");
        when(commonNurseServiceImpl.getBenChiefComplaints(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Ben Chief Complaints");

        // Act
        String actualBenVisitDetailsFrmNurseNNI = neonatalServiceImpl.getBenVisitDetailsFrmNurseNNI(1L, 1L);

        // Assert
        verify(beneficiaryVisitDetail).setAction(eq("Action"));
        verify(beneficiaryVisitDetail).setActionId(isA(Integer.class));
        verify(beneficiaryVisitDetail).setActionIdPc(isA(Integer.class));
        verify(beneficiaryVisitDetail).setActionPc(eq("Action Pc"));
        verify(beneficiaryVisitDetail).setAlgorithm(eq("Algorithm"));
        verify(beneficiaryVisitDetail).setAlgorithmPc(eq("Algorithm Pc"));
        verify(beneficiaryVisitDetail).setBenVisitID(isA(Long.class));
        verify(beneficiaryVisitDetail).setBeneficiaryRegID(isA(Long.class));
        verify(beneficiaryVisitDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(beneficiaryVisitDetail).setCreatedDate(isA(Timestamp.class));
        verify(beneficiaryVisitDetail).setDeleted(isA(Boolean.class));
        verify(beneficiaryVisitDetail).setDiseaseSummary(eq("Disease Summary"));
        verify(beneficiaryVisitDetail).setDiseaseSummaryID(isA(Integer.class));
        verify(beneficiaryVisitDetail).setFileIDs(isA(Integer[].class));
        verify(beneficiaryVisitDetail).setFollowUpForFpMethod(isA(String[].class));
        verify(beneficiaryVisitDetail).setFpMethodFollowup(eq("Fp Method Followup"));
        verify(beneficiaryVisitDetail).setFpSideeffects(eq("Fp Sideeffects"));
        verify(beneficiaryVisitDetail).setHealthFacilityLocation(eq("Health Facility Location"));
        verify(beneficiaryVisitDetail).setHealthFacilityType(eq("Health Facility Type"));
        verify(beneficiaryVisitDetail).setInformationGiven(eq("Information Given"));
        verify(beneficiaryVisitDetail).setLastModDate(isA(Timestamp.class));
        verify(beneficiaryVisitDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(beneficiaryVisitDetail).setOtherFollowUpForFpMethod(eq("Other Follow Up For Fp Method"));
        verify(beneficiaryVisitDetail).setOtherSideEffects(eq("Other Side Effects"));
        verify(beneficiaryVisitDetail).setParkingPlaceID(isA(Integer.class));
        verify(beneficiaryVisitDetail).setPregnancyStatus(eq("Pregnancy Status"));
        verify(beneficiaryVisitDetail).setPresentChiefComplaint(eq("Present Chief Complaint"));
        verify(beneficiaryVisitDetail).setPresentChiefComplaintID(eq("Present Chief Complaint ID"));
        verify(beneficiaryVisitDetail).setProcessed(eq("Processed"));
        verify(beneficiaryVisitDetail).setProviderServiceMapID(isA(Integer.class));
        verify(beneficiaryVisitDetail).setProviderServiceMapping(isA(ProviderServiceMapping.class));
        verify(beneficiaryVisitDetail).setRecommendedAction(eq("Recommended Action"));
        verify(beneficiaryVisitDetail).setRecommendedActionPc(eq("Recommended Action Pc"));
        verify(beneficiaryVisitDetail).setRemarks(eq("Remarks"));
        verify(beneficiaryVisitDetail).setRemarksPc(eq("Remarks Pc"));
        verify(beneficiaryVisitDetail).setReportFilePath(eq("/directory/foo.txt"));
        verify(beneficiaryVisitDetail).setReservedForChange(eq("Reserved For Change"));
        verify(beneficiaryVisitDetail).setSelectedDiagnosis(eq("Selected Diagnosis"));
        verify(beneficiaryVisitDetail).setSelectedDiagnosisID(eq("Selected Diagnosis ID"));
        verify(beneficiaryVisitDetail).setServiceProviderName(eq("Service Provider Name"));
        verify(beneficiaryVisitDetail).setSideEffects(isA(String[].class));
        verify(beneficiaryVisitDetail).setSubVisitCategory(eq("Sub Visit Category"));
        verify(beneficiaryVisitDetail).setSyncedBy(eq("Synced By"));
        verify(beneficiaryVisitDetail).setSyncedDate(isA(Timestamp.class));
        verify(beneficiaryVisitDetail).setVanID(isA(Integer.class));
        verify(beneficiaryVisitDetail).setVanSerialNo(isA(Long.class));
        verify(beneficiaryVisitDetail).setVehicalNo(eq("Vehical No"));
        verify(beneficiaryVisitDetail).setVisitCategory(eq("Visit Category"));
        verify(beneficiaryVisitDetail).setVisitCategoryID(isA(Integer.class));
        verify(beneficiaryVisitDetail).setVisitCode(isA(Long.class));
        verify(beneficiaryVisitDetail).setVisitDateTime(isA(Timestamp.class));
        verify(beneficiaryVisitDetail).setVisitFlowStatusFlag(eq("Visit Flow Status Flag"));
        verify(beneficiaryVisitDetail).setVisitNo(isA(Short.class));
        verify(beneficiaryVisitDetail).setVisitReason(eq("Just cause"));
        verify(beneficiaryVisitDetail).setVisitReasonID(isA(Short.class));
        verify(beneficiaryVisitDetail).setrCHID(eq("R CHID"));
        verify(commonNurseServiceImpl).getBenCdss(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getBenChiefComplaints(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getCSVisitDetails(isA(Long.class), isA(Long.class));
        assertEquals("{neonatalNurseVisitDetail={}, BenChiefComplaints=Ben Chief Complaints, Cdss=Ben Cdss}",
                actualBenVisitDetailsFrmNurseNNI);
    }

    @Test
    void testGetBeneficiaryVitalDetails() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Beneficiary Physical Anthropometry Details");
        when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Beneficiary Physical Vital Details");

        // Act
        String actualBeneficiaryVitalDetails = neonatalServiceImpl.getBeneficiaryVitalDetails(1L, 1L);

        // Assert
        verify(commonNurseServiceImpl).getBeneficiaryPhysicalAnthropometryDetails(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getBeneficiaryPhysicalVitalDetails(isA(Long.class), isA(Long.class));
        assertEquals(
                "{benAnthropometryDetail=Beneficiary Physical Anthropometry Details, benPhysicalVitalDetail=Beneficiary"
                        + " Physical Vital Details}",
                actualBeneficiaryVitalDetails);
    }

    @Test
    void testGetBeneficiaryVitalDetails2() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenThrow(new RuntimeException("benAnthropometryDetail"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> neonatalServiceImpl.getBeneficiaryVitalDetails(1L, 1L));
        verify(commonNurseServiceImpl).getBeneficiaryPhysicalAnthropometryDetails(isA(Long.class), isA(Long.class));
    }

    @Test
    void testGetBeneficiaryCdssDetails() {
        // Arrange
        when(commonNurseServiceImpl.getBenCdssDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Ben Cdss Details");

        // Act
        String actualBeneficiaryCdssDetails = neonatalServiceImpl.getBeneficiaryCdssDetails(1L, 1L);

        // Assert
        verify(commonNurseServiceImpl, atLeast(1)).getBenCdssDetails(isA(Long.class), isA(Long.class));
        assertEquals("{diseaseSummary=Ben Cdss Details, presentChiefComplaint=Ben Cdss Details}",
                actualBeneficiaryCdssDetails);
    }

    @Test
    void testGetBeneficiaryCdssDetails2() {
        // Arrange
        when(commonNurseServiceImpl.getBenCdssDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenThrow(new RuntimeException("presentChiefComplaint"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> neonatalServiceImpl.getBeneficiaryCdssDetails(1L, 1L));
        verify(commonNurseServiceImpl).getBenCdssDetails(isA(Long.class), isA(Long.class));
    }

    @Test
    void testGetBirthAndImmuniHistory() throws IEMRException {
        // Arrange
        WrapperImmunizationHistory wrapperImmunizationHistory = new WrapperImmunizationHistory();
        wrapperImmunizationHistory.setBenVisitID(1L);
        wrapperImmunizationHistory.setBeneficiaryRegID(1L);
        wrapperImmunizationHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        wrapperImmunizationHistory.setImmunizationList(new ArrayList<>());
        wrapperImmunizationHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        wrapperImmunizationHistory.setParkingPlaceID(1);
        wrapperImmunizationHistory.setProviderServiceMapID(1);
        wrapperImmunizationHistory.setVaccinationReceivedAt("Vaccination Received At");
        wrapperImmunizationHistory.setVanID(1);
        wrapperImmunizationHistory.setVisitCode(1L);
        when(commonNurseServiceImpl.getImmunizationHistory(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(wrapperImmunizationHistory);
        when(infantBirthDetailsRepo.findByBeneficiaryRegIDAndVisitCode(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenThrow(new RuntimeException("immunizationHistory"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> neonatalServiceImpl.getBirthAndImmuniHistory(1L, 1L));
        verify(infantBirthDetailsRepo).findByBeneficiaryRegIDAndVisitCode(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getImmunizationHistory(isA(Long.class), isA(Long.class));
    }

    @Test
    void testGetBirthAndImmuniHistory2() throws IEMRException {
        // Arrange
        WrapperImmunizationHistory wrapperImmunizationHistory = mock(WrapperImmunizationHistory.class);
        doNothing().when(wrapperImmunizationHistory).setBenVisitID(Mockito.<Long>any());
        doNothing().when(wrapperImmunizationHistory).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(wrapperImmunizationHistory).setCreatedBy(Mockito.<String>any());
        doNothing().when(wrapperImmunizationHistory).setImmunizationList(Mockito.<ArrayList<ChildVaccineDetail1>>any());
        doNothing().when(wrapperImmunizationHistory).setModifiedBy(Mockito.<String>any());
        doNothing().when(wrapperImmunizationHistory).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(wrapperImmunizationHistory).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(wrapperImmunizationHistory).setVaccinationReceivedAt(Mockito.<String>any());
        doNothing().when(wrapperImmunizationHistory).setVanID(Mockito.<Integer>any());
        doNothing().when(wrapperImmunizationHistory).setVisitCode(Mockito.<Long>any());
        wrapperImmunizationHistory.setBenVisitID(1L);
        wrapperImmunizationHistory.setBeneficiaryRegID(1L);
        wrapperImmunizationHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        wrapperImmunizationHistory.setImmunizationList(new ArrayList<>());
        wrapperImmunizationHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        wrapperImmunizationHistory.setParkingPlaceID(1);
        wrapperImmunizationHistory.setProviderServiceMapID(1);
        wrapperImmunizationHistory.setVaccinationReceivedAt("Vaccination Received At");
        wrapperImmunizationHistory.setVanID(1);
        wrapperImmunizationHistory.setVisitCode(1L);
        when(commonNurseServiceImpl.getImmunizationHistory(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(wrapperImmunizationHistory);
        InfantBirthDetails infantBirthDetails = mock(InfantBirthDetails.class);
        when(infantBirthDetails.getCongenitalAnomalies()).thenThrow(new RuntimeException("immunizationHistory"));
        when(infantBirthDetails.getId()).thenReturn(1L);
        doNothing().when(infantBirthDetails).setBenVisitID(Mockito.<Long>any());
        doNothing().when(infantBirthDetails).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(infantBirthDetails).setBirthComplication(Mockito.<String>any());
        doNothing().when(infantBirthDetails).setBirthComplicationID(Mockito.<Integer>any());
        doNothing().when(infantBirthDetails).setBirthWeightOfNewborn(Mockito.<Integer>any());
        doNothing().when(infantBirthDetails).setCongenitalAnomalies(Mockito.<String>any());
        doNothing().when(infantBirthDetails).setCongenitalAnomaliesList(Mockito.<String[]>any());
        doNothing().when(infantBirthDetails).setCreatedBy(Mockito.<String>any());
        doNothing().when(infantBirthDetails).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(infantBirthDetails).setDateOfBirth(Mockito.<Timestamp>any());
        doNothing().when(infantBirthDetails).setDateOfUpdatingBirthDetails(Mockito.<Timestamp>any());
        doNothing().when(infantBirthDetails).setDeleted(Mockito.<Boolean>any());
        doNothing().when(infantBirthDetails).setDeliveryConductedBy(Mockito.<String>any());
        doNothing().when(infantBirthDetails).setDeliveryConductedByID(Mockito.<Short>any());
        doNothing().when(infantBirthDetails).setDeliveryPlace(Mockito.<String>any());
        doNothing().when(infantBirthDetails).setDeliveryPlaceID(Mockito.<Short>any());
        doNothing().when(infantBirthDetails).setDeliveryType(Mockito.<String>any());
        doNothing().when(infantBirthDetails).setDeliveryTypeID(Mockito.<Integer>any());
        doNothing().when(infantBirthDetails).setGestation(Mockito.<String>any());
        doNothing().when(infantBirthDetails).setGestationID(Mockito.<Short>any());
        doNothing().when(infantBirthDetails).setId(Mockito.<Long>any());
        doNothing().when(infantBirthDetails).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(infantBirthDetails).setModifiedBy(Mockito.<String>any());
        doNothing().when(infantBirthDetails).setOtherDeliveryComplication(Mockito.<String>any());
        doNothing().when(infantBirthDetails).setOtherDeliveryPlace(Mockito.<String>any());
        doNothing().when(infantBirthDetails).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(infantBirthDetails).setProcessed(Mockito.<String>any());
        doNothing().when(infantBirthDetails).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(infantBirthDetails).setSyncedBy(Mockito.<String>any());
        doNothing().when(infantBirthDetails).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(infantBirthDetails).setTimeOfBirth(Mockito.<String>any());
        doNothing().when(infantBirthDetails).setVanID(Mockito.<Integer>any());
        doNothing().when(infantBirthDetails).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(infantBirthDetails).setVehicalNo(Mockito.<String>any());
        doNothing().when(infantBirthDetails).setVisitCode(Mockito.<Long>any());
        infantBirthDetails.setBenVisitID(1L);
        infantBirthDetails.setBeneficiaryRegID(1L);
        infantBirthDetails.setBirthComplication("Birth Complication");
        infantBirthDetails.setBirthComplicationID(1);
        infantBirthDetails.setBirthWeightOfNewborn(3);
        infantBirthDetails.setCongenitalAnomalies("Congenital Anomalies");
        infantBirthDetails.setCongenitalAnomaliesList(new String[]{"Congenital Anomalies List"});
        infantBirthDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        infantBirthDetails.setCreatedDate(mock(Timestamp.class));
        infantBirthDetails.setDateOfBirth(mock(Timestamp.class));
        infantBirthDetails.setDateOfUpdatingBirthDetails(mock(Timestamp.class));
        infantBirthDetails.setDeleted(true);
        infantBirthDetails.setDeliveryConductedBy("Delivery Conducted By");
        infantBirthDetails.setDeliveryConductedByID((short) 1);
        infantBirthDetails.setDeliveryPlace("Delivery Place");
        infantBirthDetails.setDeliveryPlaceID((short) 1);
        infantBirthDetails.setDeliveryType("Delivery Type");
        infantBirthDetails.setDeliveryTypeID(1);
        infantBirthDetails.setGestation("Gestation");
        infantBirthDetails.setGestationID((short) 1);
        infantBirthDetails.setId(1L);
        infantBirthDetails.setLastModDate(mock(Timestamp.class));
        infantBirthDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        infantBirthDetails.setOtherDeliveryComplication("Other Delivery Complication");
        infantBirthDetails.setOtherDeliveryPlace("Other Delivery Place");
        infantBirthDetails.setParkingPlaceID(1);
        infantBirthDetails.setProcessed("Processed");
        infantBirthDetails.setProviderServiceMapID(1);
        infantBirthDetails.setSyncedBy("Synced By");
        infantBirthDetails.setSyncedDate(mock(Timestamp.class));
        infantBirthDetails.setTimeOfBirth("Time Of Birth");
        infantBirthDetails.setVanID(1);
        infantBirthDetails.setVanSerialNo(1L);
        infantBirthDetails.setVehicalNo("Vehical No");
        infantBirthDetails.setVisitCode(1L);
        when(infantBirthDetailsRepo.findByBeneficiaryRegIDAndVisitCode(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(infantBirthDetails);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> neonatalServiceImpl.getBirthAndImmuniHistory(1L, 1L));
        verify(wrapperImmunizationHistory).setBenVisitID(isA(Long.class));
        verify(wrapperImmunizationHistory).setBeneficiaryRegID(isA(Long.class));
        verify(wrapperImmunizationHistory).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(wrapperImmunizationHistory).setImmunizationList(isA(ArrayList.class));
        verify(wrapperImmunizationHistory).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(wrapperImmunizationHistory).setParkingPlaceID(isA(Integer.class));
        verify(wrapperImmunizationHistory).setProviderServiceMapID(isA(Integer.class));
        verify(wrapperImmunizationHistory).setVaccinationReceivedAt(eq("Vaccination Received At"));
        verify(wrapperImmunizationHistory).setVanID(isA(Integer.class));
        verify(wrapperImmunizationHistory).setVisitCode(isA(Long.class));
        verify(infantBirthDetails).getCongenitalAnomalies();
        verify(infantBirthDetails, atLeast(1)).getId();
        verify(infantBirthDetails).setBenVisitID(isA(Long.class));
        verify(infantBirthDetails).setBeneficiaryRegID(isA(Long.class));
        verify(infantBirthDetails).setBirthComplication(eq("Birth Complication"));
        verify(infantBirthDetails).setBirthComplicationID(isA(Integer.class));
        verify(infantBirthDetails).setBirthWeightOfNewborn(isA(Integer.class));
        verify(infantBirthDetails).setCongenitalAnomalies(eq("Congenital Anomalies"));
        verify(infantBirthDetails).setCongenitalAnomaliesList(isA(String[].class));
        verify(infantBirthDetails).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(infantBirthDetails).setCreatedDate(isA(Timestamp.class));
        verify(infantBirthDetails).setDateOfBirth(isA(Timestamp.class));
        verify(infantBirthDetails).setDateOfUpdatingBirthDetails(isA(Timestamp.class));
        verify(infantBirthDetails).setDeleted(isA(Boolean.class));
        verify(infantBirthDetails).setDeliveryConductedBy(eq("Delivery Conducted By"));
        verify(infantBirthDetails).setDeliveryConductedByID(isA(Short.class));
        verify(infantBirthDetails).setDeliveryPlace(eq("Delivery Place"));
        verify(infantBirthDetails).setDeliveryPlaceID(isA(Short.class));
        verify(infantBirthDetails).setDeliveryType(eq("Delivery Type"));
        verify(infantBirthDetails).setDeliveryTypeID(isA(Integer.class));
        verify(infantBirthDetails).setGestation(eq("Gestation"));
        verify(infantBirthDetails).setGestationID(isA(Short.class));
        verify(infantBirthDetails).setId(isA(Long.class));
        verify(infantBirthDetails).setLastModDate(isA(Timestamp.class));
        verify(infantBirthDetails).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(infantBirthDetails).setOtherDeliveryComplication(eq("Other Delivery Complication"));
        verify(infantBirthDetails).setOtherDeliveryPlace(eq("Other Delivery Place"));
        verify(infantBirthDetails).setParkingPlaceID(isA(Integer.class));
        verify(infantBirthDetails).setProcessed(eq("Processed"));
        verify(infantBirthDetails).setProviderServiceMapID(isA(Integer.class));
        verify(infantBirthDetails).setSyncedBy(eq("Synced By"));
        verify(infantBirthDetails).setSyncedDate(isA(Timestamp.class));
        verify(infantBirthDetails).setTimeOfBirth(eq("Time Of Birth"));
        verify(infantBirthDetails).setVanID(isA(Integer.class));
        verify(infantBirthDetails).setVanSerialNo(isA(Long.class));
        verify(infantBirthDetails).setVehicalNo(eq("Vehical No"));
        verify(infantBirthDetails).setVisitCode(isA(Long.class));
        verify(infantBirthDetailsRepo).findByBeneficiaryRegIDAndVisitCode(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getImmunizationHistory(isA(Long.class), isA(Long.class));
    }

    @Test
    void testGetBeneficiaryImmunizationServiceDetails() throws Exception {
        // Arrange
        when(immunizationServicesRepo.findByBeneficiaryRegIDAndVisitCodeAndDeleted(anyLong(), anyLong(), anyBoolean()))
                .thenReturn(new ArrayList<>());

        // Act
        String actualBeneficiaryImmunizationServiceDetails = neonatalServiceImpl
                .getBeneficiaryImmunizationServiceDetails(1L, 1L);

        // Assert
        verify(immunizationServicesRepo).findByBeneficiaryRegIDAndVisitCodeAndDeleted(eq(1L), eq(1L), eq(false));
        assertEquals("{}", actualBeneficiaryImmunizationServiceDetails);
    }

    @Test
    void testGetBeneficiaryImmunizationServiceDetails2() throws Exception {
        // Arrange
        when(immunizationServicesRepo.findByBeneficiaryRegIDAndVisitCodeAndDeleted(anyLong(), anyLong(), anyBoolean()))
                .thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> neonatalServiceImpl.getBeneficiaryImmunizationServiceDetails(1L, 1L));
        verify(immunizationServicesRepo).findByBeneficiaryRegIDAndVisitCodeAndDeleted(eq(1L), eq(1L), eq(false));
    }

    @Test
    void testGetBeneficiaryImmunizationServiceDetails3() throws Exception {
        // Arrange
        ImmunizationServices immunizationServices = mock(ImmunizationServices.class);
        when(immunizationServices.getVaccineDose()).thenThrow(new RuntimeException("immunizationServices"));
        when(immunizationServices.getVaccineName()).thenReturn("Vaccine Name");
        doNothing().when(immunizationServices).setBatchNo(Mockito.<String>any());
        doNothing().when(immunizationServices).setBenVisitID(Mockito.<Long>any());
        doNothing().when(immunizationServices).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(immunizationServices).setCreatedBy(Mockito.<String>any());
        doNothing().when(immunizationServices).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(immunizationServices).setCurrentImmunizationService(Mockito.<String>any());
        doNothing().when(immunizationServices).setCurrentImmunizationServiceID(Mockito.<Integer>any());
        doNothing().when(immunizationServices).setDateOfVisit(Mockito.<Timestamp>any());
        doNothing().when(immunizationServices).setDeleted(Mockito.<Boolean>any());
        doNothing().when(immunizationServices).setId(Mockito.<Long>any());
        doNothing().when(immunizationServices).setImmunizationServicesType(Mockito.<String>any());
        doNothing().when(immunizationServices).setImmunizationServicesTypeID(Mockito.<Short>any());
        doNothing().when(immunizationServices).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(immunizationServices).setModifiedBy(Mockito.<String>any());
        doNothing().when(immunizationServices).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(immunizationServices).setProcessed(Mockito.<String>any());
        doNothing().when(immunizationServices).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(immunizationServices).setRoute(Mockito.<String>any());
        doNothing().when(immunizationServices).setSiteOfInjection(Mockito.<String>any());
        doNothing().when(immunizationServices).setSiteOfInjectionID(Mockito.<Short>any());
        doNothing().when(immunizationServices).setSyncedBy(Mockito.<String>any());
        doNothing().when(immunizationServices).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(immunizationServices).setVaccineDose(Mockito.<String>any());
        doNothing().when(immunizationServices).setVaccineName(Mockito.<String>any());
        doNothing().when(immunizationServices).setVaccines(Mockito.<List<Vaccines>>any());
        doNothing().when(immunizationServices).setVanID(Mockito.<Integer>any());
        doNothing().when(immunizationServices).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(immunizationServices).setVehicalNo(Mockito.<String>any());
        doNothing().when(immunizationServices).setVisitCode(Mockito.<Long>any());
        immunizationServices.setBatchNo("Batch No");
        immunizationServices.setBenVisitID(1L);
        immunizationServices.setBeneficiaryRegID(1L);
        immunizationServices.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        immunizationServices.setCreatedDate(mock(Timestamp.class));
        immunizationServices.setCurrentImmunizationService("Current Immunization Service");
        immunizationServices.setCurrentImmunizationServiceID(1);
        immunizationServices.setDateOfVisit(mock(Timestamp.class));
        immunizationServices.setDeleted(true);
        immunizationServices.setId(1L);
        immunizationServices.setImmunizationServicesType("Immunization Services Type");
        immunizationServices.setImmunizationServicesTypeID((short) 1);
        immunizationServices.setLastModDate(mock(Timestamp.class));
        immunizationServices.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        immunizationServices.setParkingPlaceID(1);
        immunizationServices.setProcessed("Processed");
        immunizationServices.setProviderServiceMapID(1);
        immunizationServices.setRoute("Route");
        immunizationServices.setSiteOfInjection("Site Of Injection");
        immunizationServices.setSiteOfInjectionID((short) 1);
        immunizationServices.setSyncedBy("Synced By");
        immunizationServices.setSyncedDate(mock(Timestamp.class));
        immunizationServices.setVaccineDose("Vaccine Dose");
        immunizationServices.setVaccineName("Vaccine Name");
        immunizationServices.setVaccines(new ArrayList<>());
        immunizationServices.setVanID(1);
        immunizationServices.setVanSerialNo(1L);
        immunizationServices.setVehicalNo("Vehical No");
        immunizationServices.setVisitCode(1L);

        ArrayList<ImmunizationServices> immunizationServicesList = new ArrayList<>();
        immunizationServicesList.add(immunizationServices);
        when(immunizationServicesRepo.findByBeneficiaryRegIDAndVisitCodeAndDeleted(anyLong(), anyLong(), anyBoolean()))
                .thenReturn(immunizationServicesList);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> neonatalServiceImpl.getBeneficiaryImmunizationServiceDetails(1L, 1L));
        verify(immunizationServices).getVaccineDose();
        verify(immunizationServices, atLeast(1)).getVaccineName();
        verify(immunizationServices).setBatchNo(eq("Batch No"));
        verify(immunizationServices).setBenVisitID(isA(Long.class));
        verify(immunizationServices).setBeneficiaryRegID(isA(Long.class));
        verify(immunizationServices).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(immunizationServices).setCreatedDate(isA(Timestamp.class));
        verify(immunizationServices).setCurrentImmunizationService(eq("Current Immunization Service"));
        verify(immunizationServices).setCurrentImmunizationServiceID(isA(Integer.class));
        verify(immunizationServices).setDateOfVisit(isA(Timestamp.class));
        verify(immunizationServices).setDeleted(isA(Boolean.class));
        verify(immunizationServices).setId(isA(Long.class));
        verify(immunizationServices).setImmunizationServicesType(eq("Immunization Services Type"));
        verify(immunizationServices).setImmunizationServicesTypeID(isA(Short.class));
        verify(immunizationServices).setLastModDate(isA(Timestamp.class));
        verify(immunizationServices).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(immunizationServices).setParkingPlaceID(isA(Integer.class));
        verify(immunizationServices).setProcessed(eq("Processed"));
        verify(immunizationServices).setProviderServiceMapID(isA(Integer.class));
        verify(immunizationServices).setRoute(eq("Route"));
        verify(immunizationServices).setSiteOfInjection(eq("Site Of Injection"));
        verify(immunizationServices).setSiteOfInjectionID(isA(Short.class));
        verify(immunizationServices).setSyncedBy(eq("Synced By"));
        verify(immunizationServices).setSyncedDate(isA(Timestamp.class));
        verify(immunizationServices).setVaccineDose(eq("Vaccine Dose"));
        verify(immunizationServices).setVaccineName(eq("Vaccine Name"));
        verify(immunizationServices).setVaccines(isA(List.class));
        verify(immunizationServices).setVanID(isA(Integer.class));
        verify(immunizationServices).setVanSerialNo(isA(Long.class));
        verify(immunizationServices).setVehicalNo(eq("Vehical No"));
        verify(immunizationServices).setVisitCode(isA(Long.class));
        verify(immunizationServicesRepo).findByBeneficiaryRegIDAndVisitCodeAndDeleted(eq(1L), eq(1L), eq(false));
    }

    @Test
    void testGetBenCaseRecordFromDoctorNNI() throws IEMRException {
        // Arrange
        when(commonDoctorServiceImpl.getFindingsDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Findings Details");
        when(generalOPDDoctorServiceImpl.getGeneralOPDDiagnosisDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenThrow(new RuntimeException("findings"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> neonatalServiceImpl.getBenCaseRecordFromDoctorNNI(1L, 1L));
        verify(commonDoctorServiceImpl).getFindingsDetails(isA(Long.class), isA(Long.class));
        verify(generalOPDDoctorServiceImpl).getGeneralOPDDiagnosisDetails(isA(Long.class), isA(Long.class));
    }

    @Test
    void testGetNurseDataNNI() throws Exception {
        // Arrange
        WrapperImmunizationHistory wrapperImmunizationHistory = new WrapperImmunizationHistory();
        wrapperImmunizationHistory.setBenVisitID(1L);
        wrapperImmunizationHistory.setBeneficiaryRegID(1L);
        wrapperImmunizationHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        wrapperImmunizationHistory.setImmunizationList(new ArrayList<>());
        wrapperImmunizationHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        wrapperImmunizationHistory.setParkingPlaceID(1);
        wrapperImmunizationHistory.setProviderServiceMapID(1);
        wrapperImmunizationHistory.setVaccinationReceivedAt("Vaccination Received At");
        wrapperImmunizationHistory.setVanID(1);
        wrapperImmunizationHistory.setVisitCode(1L);
        Timestamp createdDate = mock(Timestamp.class);
        when(createdDate.getTime()).thenReturn(10L);
        Timestamp lastModDate = mock(Timestamp.class);
        when(lastModDate.getTime()).thenReturn(10L);

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
        Timestamp syncedDate = mock(Timestamp.class);
        when(syncedDate.getTime()).thenReturn(10L);
        Timestamp visitDateTime = mock(Timestamp.class);
        when(visitDateTime.getTime()).thenReturn(10L);

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
        beneficiaryVisitDetail.setCreatedDate(createdDate);
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
        beneficiaryVisitDetail.setLastModDate(lastModDate);
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
        beneficiaryVisitDetail.setSyncedDate(syncedDate);
        beneficiaryVisitDetail.setVanID(1);
        beneficiaryVisitDetail.setVanSerialNo(1L);
        beneficiaryVisitDetail.setVehicalNo("Vehical No");
        beneficiaryVisitDetail.setVisitCategory("Visit Category");
        beneficiaryVisitDetail.setVisitCategoryID(1);
        beneficiaryVisitDetail.setVisitCode(1L);
        beneficiaryVisitDetail.setVisitDateTime(visitDateTime);
        beneficiaryVisitDetail.setVisitFlowStatusFlag("Visit Flow Status Flag");
        beneficiaryVisitDetail.setVisitNo((short) 1);
        beneficiaryVisitDetail.setVisitReason("Just cause");
        beneficiaryVisitDetail.setVisitReasonID((short) 1);
        beneficiaryVisitDetail.setrCHID("R CHID");
        when(commonNurseServiceImpl.getImmunizationHistory(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(wrapperImmunizationHistory);
        when(commonNurseServiceImpl.getCSVisitDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(beneficiaryVisitDetail);
        when(commonNurseServiceImpl.getBenChiefComplaints(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Ben Chief Complaints");
        when(infantBirthDetailsRepo.findByBeneficiaryRegIDAndVisitCode(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenThrow(new RuntimeException("neonatalNurseVisitDetail"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> neonatalServiceImpl.getNurseDataNNI(1L, 1L));
        verify(infantBirthDetailsRepo).findByBeneficiaryRegIDAndVisitCode(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getBenChiefComplaints(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getCSVisitDetails(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getImmunizationHistory(isA(Long.class), isA(Long.class));
        verify(createdDate).getTime();
        verify(lastModDate).getTime();
        verify(syncedDate).getTime();
        verify(visitDateTime).getTime();
    }

    @Test
    void testUpdateBenVitalDetailsNNI() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateANCAnthropometryDetails(Mockito.<BenAnthropometryDetail>any())).thenReturn(1);
        when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any())).thenReturn(1);

        // Act
        int actualUpdateBenVitalDetailsNNIResult = neonatalServiceImpl.updateBenVitalDetailsNNI(new JsonObject());

        // Assert
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).updateANCPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1, actualUpdateBenVitalDetailsNNIResult);
    }

    @Test
    void testUpdateBenVitalDetailsNNI2() throws Exception {
        // Arrange, Act and Assert
        assertEquals(1, neonatalServiceImpl.updateBenVitalDetailsNNI(null));
    }

    @Test
    void testUpdateBenVitalDetailsNNI3() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateANCAnthropometryDetails(Mockito.<BenAnthropometryDetail>any())).thenReturn(1);
        when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any())).thenReturn(1);

        JsonObject vitalDetailsOBJ = new JsonObject();
        vitalDetailsOBJ.add("Property", new JsonArray(3));

        // Act
        int actualUpdateBenVitalDetailsNNIResult = neonatalServiceImpl.updateBenVitalDetailsNNI(vitalDetailsOBJ);

        // Assert
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).updateANCPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1, actualUpdateBenVitalDetailsNNIResult);
    }

    @Test
    void testUpdateBenVitalDetailsNNI4() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateANCAnthropometryDetails(Mockito.<BenAnthropometryDetail>any())).thenReturn(1);
        when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any())).thenReturn(1);

        JsonObject vitalDetailsOBJ = new JsonObject();
        vitalDetailsOBJ.addProperty("Property", "42");

        // Act
        int actualUpdateBenVitalDetailsNNIResult = neonatalServiceImpl.updateBenVitalDetailsNNI(vitalDetailsOBJ);

        // Assert
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).updateANCPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1, actualUpdateBenVitalDetailsNNIResult);
    }

    @Test
    void testUpdateBenVitalDetailsNNI5() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateANCAnthropometryDetails(Mockito.<BenAnthropometryDetail>any())).thenReturn(1);
        when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any())).thenReturn(1);

        JsonObject vitalDetailsOBJ = new JsonObject();
        Integer value = Integer.valueOf(1);
        vitalDetailsOBJ.addProperty("Property", value);

        // Act
        int actualUpdateBenVitalDetailsNNIResult = neonatalServiceImpl.updateBenVitalDetailsNNI(vitalDetailsOBJ);

        // Assert
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).updateANCPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1, actualUpdateBenVitalDetailsNNIResult);
        assertSame(value, actualUpdateBenVitalDetailsNNIResult);
    }

    @Test
    void testUpdateBenVitalDetailsNNI6() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateANCAnthropometryDetails(Mockito.<BenAnthropometryDetail>any())).thenReturn(1);
        when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any())).thenReturn(1);

        JsonObject vitalDetailsOBJ = new JsonObject();
        vitalDetailsOBJ.addProperty("Property", true);

        // Act
        int actualUpdateBenVitalDetailsNNIResult = neonatalServiceImpl.updateBenVitalDetailsNNI(vitalDetailsOBJ);

        // Assert
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).updateANCPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1, actualUpdateBenVitalDetailsNNIResult);
    }

    @Test
    void testUpdateBenVitalDetailsNNI7() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateANCAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenThrow(new IEMRException("An error occurred"));

        // Act and Assert
        assertThrows(IEMRException.class, () -> neonatalServiceImpl.updateBenVitalDetailsNNI(new JsonObject()));
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
    }

    @Test
    void testUpdateBenVitalDetailsNNI8() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateANCAnthropometryDetails(Mockito.<BenAnthropometryDetail>any())).thenReturn(1);
        when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any())).thenReturn(1);

        JsonObject vitalDetailsOBJ = new JsonObject();
        vitalDetailsOBJ.add("Property", null);

        // Act
        int actualUpdateBenVitalDetailsNNIResult = neonatalServiceImpl.updateBenVitalDetailsNNI(vitalDetailsOBJ);

        // Assert
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).updateANCPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1, actualUpdateBenVitalDetailsNNIResult);
    }

    @Test
    void testUpdateBenHistoryDetails() throws IEMRException, ParseException {
        // Arrange, Act and Assert
        assertEquals(1, neonatalServiceImpl.updateBenHistoryDetails(new JsonObject()));
    }

    @Test
    void testUpdateBenImmunServiceDetailsNNI() throws Exception {
        // Arrange, Act and Assert
        assertEquals(1, neonatalServiceImpl.updateBenImmunServiceDetailsNNI(new JsonObject()));
    }

    @Test
    void testUpdateBenImmunServiceDetailsNNI2() throws Exception {
        // Arrange
        JsonObject immunServiceOBJ = new JsonObject();
        immunServiceOBJ.add("Property", new JsonArray(3));

        // Act and Assert
        assertEquals(1, neonatalServiceImpl.updateBenImmunServiceDetailsNNI(immunServiceOBJ));
    }

    @Test
    void testUpdateBenImmunServiceDetailsNNI3() throws Exception {
        // Arrange
        JsonObject immunServiceOBJ = new JsonObject();
        immunServiceOBJ.add("immunizationServices", null);

        // Act and Assert
        assertEquals(1, neonatalServiceImpl.updateBenImmunServiceDetailsNNI(immunServiceOBJ));
    }

    @Test
    void testUpdateBenImmunServiceDetailsNNI4() throws Exception {
        // Arrange
        JsonObject immunServiceOBJ = new JsonObject();
        immunServiceOBJ.add("immunizationServices", new JsonObject());

        // Act and Assert
        assertEquals(1, neonatalServiceImpl.updateBenImmunServiceDetailsNNI(immunServiceOBJ));
    }

    @Test
    void testUpdateDoctorDataNNI() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(1);
        when(commonNurseServiceImpl.updatePrescription(Mockito.<PrescriptionDetail>any())).thenReturn(1);

        TeleconsultationRequestOBJ teleconsultationRequestOBJ = new TeleconsultationRequestOBJ();
        teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
        teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
        teleconsultationRequestOBJ.setSpecializationID(1);
        teleconsultationRequestOBJ.setTmRequestID(1L);
        teleconsultationRequestOBJ.setToTime("To Time");
        teleconsultationRequestOBJ.setUserID(1);
        teleconsultationRequestOBJ.setWalkIn(true);
        when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
                Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);

        // Act
        Long actualUpdateDoctorDataNNIResult = neonatalServiceImpl.updateDoctorDataNNI(new JsonObject(), "JaneDoe");

        // Assert
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataUpdate(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonNurseServiceImpl).updatePrescription(isNull());
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
        assertEquals(1L, actualUpdateDoctorDataNNIResult.longValue());
    }

    @Test
    void testUpdateDoctorDataNNI2() throws Exception {
        // Arrange
        when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
                Mockito.<String>any())).thenThrow(new SQLException());

        // Act and Assert
        assertThrows(SQLException.class, () -> neonatalServiceImpl.updateDoctorDataNNI(new JsonObject(), "JaneDoe"));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
    }

    @Test
    void testUpdateDoctorDataNNI3() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(0);
        when(commonNurseServiceImpl.updatePrescription(Mockito.<PrescriptionDetail>any())).thenReturn(1);

        TeleconsultationRequestOBJ teleconsultationRequestOBJ = new TeleconsultationRequestOBJ();
        teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
        teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
        teleconsultationRequestOBJ.setSpecializationID(1);
        teleconsultationRequestOBJ.setTmRequestID(1L);
        teleconsultationRequestOBJ.setToTime("To Time");
        teleconsultationRequestOBJ.setUserID(1);
        teleconsultationRequestOBJ.setWalkIn(true);
        when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
                Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> neonatalServiceImpl.updateDoctorDataNNI(new JsonObject(), "JaneDoe"));
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataUpdate(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonNurseServiceImpl).updatePrescription(isNull());
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
    }

    @Test
    void testUpdateDoctorDataNNI4() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(1);
        when(commonNurseServiceImpl.updatePrescription(Mockito.<PrescriptionDetail>any())).thenReturn(1);
        TeleconsultationRequestOBJ teleconsultationRequestOBJ = mock(TeleconsultationRequestOBJ.class);
        when(teleconsultationRequestOBJ.getWalkIn()).thenReturn(false);
        when(teleconsultationRequestOBJ.getSpecializationID()).thenReturn(1);
        when(teleconsultationRequestOBJ.getTmRequestID()).thenReturn(1L);
        when(teleconsultationRequestOBJ.getAllocationDate()).thenReturn(mock(Timestamp.class));
        doNothing().when(teleconsultationRequestOBJ).setAllocationDate(Mockito.<Timestamp>any());
        doNothing().when(teleconsultationRequestOBJ).setFromTime(Mockito.<String>any());
        doNothing().when(teleconsultationRequestOBJ).setSpecializationID(Mockito.<Integer>any());
        doNothing().when(teleconsultationRequestOBJ).setTmRequestID(Mockito.<Long>any());
        doNothing().when(teleconsultationRequestOBJ).setToTime(Mockito.<String>any());
        doNothing().when(teleconsultationRequestOBJ).setUserID(Mockito.<Integer>any());
        doNothing().when(teleconsultationRequestOBJ).setWalkIn(Mockito.<Boolean>any());
        teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
        teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
        teleconsultationRequestOBJ.setSpecializationID(1);
        teleconsultationRequestOBJ.setTmRequestID(1L);
        teleconsultationRequestOBJ.setToTime("To Time");
        teleconsultationRequestOBJ.setUserID(1);
        teleconsultationRequestOBJ.setWalkIn(true);
        when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
                Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);
        when(sMSGatewayServiceImpl.smsSenderGateway(Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Integer>any(),
                Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(3);

        // Act
        Long actualUpdateDoctorDataNNIResult = neonatalServiceImpl.updateDoctorDataNNI(new JsonObject(), "JaneDoe");

        // Assert
        verify(teleconsultationRequestOBJ, atLeast(1)).getAllocationDate();
        verify(teleconsultationRequestOBJ).getSpecializationID();
        verify(teleconsultationRequestOBJ).getTmRequestID();
        verify(teleconsultationRequestOBJ).getWalkIn();
        verify(teleconsultationRequestOBJ).setAllocationDate(isA(Timestamp.class));
        verify(teleconsultationRequestOBJ).setFromTime(eq("jane.doe@example.org"));
        verify(teleconsultationRequestOBJ).setSpecializationID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setTmRequestID(isA(Long.class));
        verify(teleconsultationRequestOBJ).setToTime(eq("To Time"));
        verify(teleconsultationRequestOBJ).setUserID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setWalkIn(isA(Boolean.class));
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataUpdate(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonNurseServiceImpl).updatePrescription(isNull());
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
        verify(sMSGatewayServiceImpl).smsSenderGateway(eq("schedule"), isNull(), isA(Integer.class), isA(Long.class),
                isNull(), isNull(), eq("Mock for Timestamp, hashCode: 142174028"), isNull(), eq("JaneDoe"));
        assertEquals(1L, actualUpdateDoctorDataNNIResult.longValue());
    }

    @Test
    void testUpdateDoctorDataNNI5() throws Exception {
        // Arrange
        TeleconsultationRequestOBJ teleconsultationRequestOBJ = mock(TeleconsultationRequestOBJ.class);
        doNothing().when(teleconsultationRequestOBJ).setAllocationDate(Mockito.<Timestamp>any());
        doNothing().when(teleconsultationRequestOBJ).setFromTime(Mockito.<String>any());
        doNothing().when(teleconsultationRequestOBJ).setSpecializationID(Mockito.<Integer>any());
        doNothing().when(teleconsultationRequestOBJ).setTmRequestID(Mockito.<Long>any());
        doNothing().when(teleconsultationRequestOBJ).setToTime(Mockito.<String>any());
        doNothing().when(teleconsultationRequestOBJ).setUserID(Mockito.<Integer>any());
        doNothing().when(teleconsultationRequestOBJ).setWalkIn(Mockito.<Boolean>any());
        teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
        teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
        teleconsultationRequestOBJ.setSpecializationID(1);
        teleconsultationRequestOBJ.setTmRequestID(1L);
        teleconsultationRequestOBJ.setToTime("To Time");
        teleconsultationRequestOBJ.setUserID(1);
        teleconsultationRequestOBJ.setWalkIn(true);

        // Act
        Long actualUpdateDoctorDataNNIResult = neonatalServiceImpl.updateDoctorDataNNI(null, "JaneDoe");

        // Assert
        verify(teleconsultationRequestOBJ).setAllocationDate(isA(Timestamp.class));
        verify(teleconsultationRequestOBJ).setFromTime(eq("jane.doe@example.org"));
        verify(teleconsultationRequestOBJ).setSpecializationID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setTmRequestID(isA(Long.class));
        verify(teleconsultationRequestOBJ).setToTime(eq("To Time"));
        verify(teleconsultationRequestOBJ).setUserID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setWalkIn(isA(Boolean.class));
        assertNull(actualUpdateDoctorDataNNIResult);
    }
}
