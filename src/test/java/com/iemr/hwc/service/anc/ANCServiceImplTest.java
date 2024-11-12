package com.iemr.hwc.service.anc;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.Disabled;
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
import com.iemr.hwc.data.anc.ANCCareDetails;
import com.iemr.hwc.data.anc.BenFamilyHistory;
import com.iemr.hwc.data.anc.BenMedHistory;
import com.iemr.hwc.data.anc.BenMenstrualDetails;
import com.iemr.hwc.data.anc.BenPersonalHabit;
import com.iemr.hwc.data.anc.HrpStatusAndReasonsRequestModel;
import com.iemr.hwc.data.anc.PhyGeneralExamination;
import com.iemr.hwc.data.anc.PhyHeadToToeExamination;
import com.iemr.hwc.data.anc.SysCardiovascularExamination;
import com.iemr.hwc.data.anc.SysCentralNervousExamination;
import com.iemr.hwc.data.anc.SysGenitourinarySystemExamination;
import com.iemr.hwc.data.anc.SysMusculoskeletalSystemExamination;
import com.iemr.hwc.data.anc.SysObstetricExamination;
import com.iemr.hwc.data.anc.SysRespiratoryExamination;
import com.iemr.hwc.data.anc.WrapperChildOptionalVaccineDetail;
import com.iemr.hwc.data.anc.WrapperComorbidCondDetails;
import com.iemr.hwc.data.anc.WrapperFemaleObstetricHistory;
import com.iemr.hwc.data.anc.WrapperImmunizationHistory;
import com.iemr.hwc.data.anc.WrapperMedicationHistory;
import com.iemr.hwc.data.location.Districts;
import com.iemr.hwc.data.location.States;
import com.iemr.hwc.data.nurse.BenAnthropometryDetail;
import com.iemr.hwc.data.nurse.BenPhysicalVitalDetail;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.provider.ProviderServiceMapping;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.foetalmonitor.FoetalMonitorRepo;
import com.iemr.hwc.repo.location.ZoneDistrictMapping;
import com.iemr.hwc.repo.nurse.BenAnthropometryRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.nurse.CDSSRepo;
import com.iemr.hwc.repo.nurse.anc.ANCCareRepo;
import com.iemr.hwc.repo.nurse.anc.ANCDiagnosisRepo;
import com.iemr.hwc.repo.nurse.anc.BenAdherenceRepo;
import com.iemr.hwc.repo.nurse.anc.BenMedHistoryRepo;
import com.iemr.hwc.repo.nurse.anc.BenMenstrualDetailsRepo;
import com.iemr.hwc.repo.nurse.anc.BencomrbidityCondRepo;
import com.iemr.hwc.repo.nurse.anc.FemaleObstetricHistoryRepo;
import com.iemr.hwc.repo.nurse.anc.SysObstetricExaminationRepo;
import com.iemr.hwc.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonServiceImpl;
import com.iemr.hwc.service.labtechnician.LabTechnicianServiceImpl;
import com.iemr.hwc.service.tele_consultation.SMSGatewayServiceImpl;
import com.iemr.hwc.service.tele_consultation.TeleConsultationServiceImpl;
import com.iemr.hwc.utils.exception.IEMRException;

@ExtendWith(MockitoExtension.class)
class ANCServiceImplTest {
    @Autowired
    private HrpStatusAndReasonsRequestModel hrpStatusAndReasonsRequestModel;

    @Mock
    private ANCCareRepo aNCCareRepo;

    @Mock
    private ANCDiagnosisRepo aNCDiagnosisRepo;

    @Mock
    private ANCDoctorServiceImpl aNCDoctorServiceImpl;

    @Mock
    private ANCNurseServiceImpl aNCNurseServiceImpl;

    @InjectMocks
    private ANCServiceImpl aNCServiceImpl;

    @Mock
    private BenAdherenceRepo benAdherenceRepo;

    @Mock
    private BenAnthropometryRepo benAnthropometryRepo;

    @Mock
    private BenChiefComplaintRepo benChiefComplaintRepo;

    @Mock
    private BenMedHistoryRepo benMedHistoryRepo;

    @Mock
    private BenMenstrualDetailsRepo benMenstrualDetailsRepo;

    @Mock
    private BenVisitDetailRepo benVisitDetailRepo;

    @Mock
    private BencomrbidityCondRepo bencomrbidityCondRepo;

    @Mock
    private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;

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
    private FemaleObstetricHistoryRepo femaleObstetricHistoryRepo;

    @Mock
    private FoetalMonitorRepo foetalMonitorRepo;

    @Mock
    private LabTechnicianServiceImpl labTechnicianServiceImpl;

    @Mock
    private SMSGatewayServiceImpl sMSGatewayServiceImpl;

    @Mock
    private SysObstetricExaminationRepo sysObstetricExaminationRepo;

    @Mock
    private TeleConsultationServiceImpl teleConsultationServiceImpl;

    @Test
    void testSaveANCNurseData() throws Exception {
        // Arrange, Act and Assert
        assertThrows(Exception.class, () -> aNCServiceImpl.saveANCNurseData(new JsonObject(), "JaneDoe"));
        assertThrows(Exception.class, () -> aNCServiceImpl.saveANCNurseData(null, "JaneDoe"));
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
        aNCServiceImpl.deleteVisitDetails(requestOBJ);

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
        assertThrows(RuntimeException.class, () -> aNCServiceImpl.deleteVisitDetails(requestOBJ));
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
        aNCServiceImpl.deleteVisitDetails(requestOBJ);

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
        aNCServiceImpl.deleteVisitDetails(requestOBJ);

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
        aNCServiceImpl.deleteVisitDetails(requestOBJ);

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
        aNCServiceImpl.deleteVisitDetails(requestOBJ);

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
        aNCServiceImpl.deleteVisitDetails(requestOBJ);

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
        aNCServiceImpl.deleteVisitDetails(requestOBJ);

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
        assertThrows(RuntimeException.class, () -> aNCServiceImpl.deleteVisitDetails(requestOBJ));
        verify(value).isJsonNull();
    }

    @Test
    void testSaveANCDoctorData() throws Exception {
        // Arrange, Act and Assert
        assertNull(aNCServiceImpl.saveANCDoctorData(null, "JaneDoe"));
    }

    @Test
    void testSaveBenVisitDetails() throws Exception {
        // Arrange
        JsonObject visitDetailsOBJ = new JsonObject();

        CommonUtilityClass nurseUtilityClass = new CommonUtilityClass();
        nurseUtilityClass.setAge(1);
        nurseUtilityClass.setAgeUnits("Age Units");
        nurseUtilityClass.setAuthorization("JaneDoe");
        nurseUtilityClass.setBenFlowID(1L);
        nurseUtilityClass.setBenRegID(1L);
        nurseUtilityClass.setBenVisitID(1L);
        nurseUtilityClass.setBeneficiaryID(1L);
        nurseUtilityClass.setBeneficiaryRegID(1L);
        nurseUtilityClass.setBeneficiaryRegId(1L);
        nurseUtilityClass.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        nurseUtilityClass.setDistrictID(1);
        nurseUtilityClass.setDistrictName("District Name");
        nurseUtilityClass.setFacilityID(1);
        nurseUtilityClass.setFirstName("Jane");
        nurseUtilityClass.setGenderID((short) 1);
        nurseUtilityClass.setGenderName("Gender Name");
        nurseUtilityClass.setIsCovidFlowDone(true);
        nurseUtilityClass.setIsMobile(true);
        nurseUtilityClass.setIsSpecialist(true);
        nurseUtilityClass.setLastName("Doe");
        nurseUtilityClass.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        nurseUtilityClass.setParkingPlaceID(1);
        nurseUtilityClass.setParkingPlaceId(1);
        nurseUtilityClass.setPrescriptionID(1L);
        nurseUtilityClass.setProviderServiceMapID(1);
        nurseUtilityClass.setServiceID((short) 1);
        nurseUtilityClass.setSessionID(1);
        nurseUtilityClass.setSpecialistDiagnosis("Specialist Diagnosis");
        nurseUtilityClass.setSubVisitCategory("Sub Visit Category");
        nurseUtilityClass.setTreatmentsOnSideEffects("Treatments On Side Effects");
        nurseUtilityClass.setVanID(1);
        nurseUtilityClass.setVanId(1);
        nurseUtilityClass.setVillageId(1);
        nurseUtilityClass.setVillageName("Village Name");
        nurseUtilityClass.setVisitCategoryID(1);
        nurseUtilityClass.setVisitCode(1L);

        // Act and Assert
        assertTrue(aNCServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass).isEmpty());
    }

    @Test
    void testSaveBenVisitDetails2() throws Exception {
        // Arrange
        CommonUtilityClass nurseUtilityClass = new CommonUtilityClass();
        nurseUtilityClass.setAge(1);
        nurseUtilityClass.setAgeUnits("Age Units");
        nurseUtilityClass.setAuthorization("JaneDoe");
        nurseUtilityClass.setBenFlowID(1L);
        nurseUtilityClass.setBenRegID(1L);
        nurseUtilityClass.setBenVisitID(1L);
        nurseUtilityClass.setBeneficiaryID(1L);
        nurseUtilityClass.setBeneficiaryRegID(1L);
        nurseUtilityClass.setBeneficiaryRegId(1L);
        nurseUtilityClass.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        nurseUtilityClass.setDistrictID(1);
        nurseUtilityClass.setDistrictName("District Name");
        nurseUtilityClass.setFacilityID(1);
        nurseUtilityClass.setFirstName("Jane");
        nurseUtilityClass.setGenderID((short) 1);
        nurseUtilityClass.setGenderName("Gender Name");
        nurseUtilityClass.setIsCovidFlowDone(true);
        nurseUtilityClass.setIsMobile(true);
        nurseUtilityClass.setIsSpecialist(true);
        nurseUtilityClass.setLastName("Doe");
        nurseUtilityClass.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        nurseUtilityClass.setParkingPlaceID(1);
        nurseUtilityClass.setParkingPlaceId(1);
        nurseUtilityClass.setPrescriptionID(1L);
        nurseUtilityClass.setProviderServiceMapID(1);
        nurseUtilityClass.setServiceID((short) 1);
        nurseUtilityClass.setSessionID(1);
        nurseUtilityClass.setSpecialistDiagnosis("Specialist Diagnosis");
        nurseUtilityClass.setSubVisitCategory("Sub Visit Category");
        nurseUtilityClass.setTreatmentsOnSideEffects("Treatments On Side Effects");
        nurseUtilityClass.setVanID(1);
        nurseUtilityClass.setVanId(1);
        nurseUtilityClass.setVillageId(1);
        nurseUtilityClass.setVillageName("Village Name");
        nurseUtilityClass.setVisitCategoryID(1);
        nurseUtilityClass.setVisitCode(1L);

        // Act and Assert
        assertTrue(aNCServiceImpl.saveBenVisitDetails(null, nurseUtilityClass).isEmpty());
    }

    @Test
    void testSaveBenVisitDetails3() throws Exception {
        // Arrange
        JsonObject visitDetailsOBJ = new JsonObject();
        visitDetailsOBJ.add("Property", new JsonArray(3));

        CommonUtilityClass nurseUtilityClass = new CommonUtilityClass();
        nurseUtilityClass.setAge(1);
        nurseUtilityClass.setAgeUnits("Age Units");
        nurseUtilityClass.setAuthorization("JaneDoe");
        nurseUtilityClass.setBenFlowID(1L);
        nurseUtilityClass.setBenRegID(1L);
        nurseUtilityClass.setBenVisitID(1L);
        nurseUtilityClass.setBeneficiaryID(1L);
        nurseUtilityClass.setBeneficiaryRegID(1L);
        nurseUtilityClass.setBeneficiaryRegId(1L);
        nurseUtilityClass.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        nurseUtilityClass.setDistrictID(1);
        nurseUtilityClass.setDistrictName("District Name");
        nurseUtilityClass.setFacilityID(1);
        nurseUtilityClass.setFirstName("Jane");
        nurseUtilityClass.setGenderID((short) 1);
        nurseUtilityClass.setGenderName("Gender Name");
        nurseUtilityClass.setIsCovidFlowDone(true);
        nurseUtilityClass.setIsMobile(true);
        nurseUtilityClass.setIsSpecialist(true);
        nurseUtilityClass.setLastName("Doe");
        nurseUtilityClass.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        nurseUtilityClass.setParkingPlaceID(1);
        nurseUtilityClass.setParkingPlaceId(1);
        nurseUtilityClass.setPrescriptionID(1L);
        nurseUtilityClass.setProviderServiceMapID(1);
        nurseUtilityClass.setServiceID((short) 1);
        nurseUtilityClass.setSessionID(1);
        nurseUtilityClass.setSpecialistDiagnosis("Specialist Diagnosis");
        nurseUtilityClass.setSubVisitCategory("Sub Visit Category");
        nurseUtilityClass.setTreatmentsOnSideEffects("Treatments On Side Effects");
        nurseUtilityClass.setVanID(1);
        nurseUtilityClass.setVanId(1);
        nurseUtilityClass.setVillageId(1);
        nurseUtilityClass.setVillageName("Village Name");
        nurseUtilityClass.setVisitCategoryID(1);
        nurseUtilityClass.setVisitCode(1L);

        // Act and Assert
        assertTrue(aNCServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass).isEmpty());
    }

    @Test
    void testSaveBenVisitDetails4() throws Exception {
        // Arrange
        JsonObject visitDetailsOBJ = new JsonObject();
        visitDetailsOBJ.addProperty("visitDetails", (String) null);

        CommonUtilityClass nurseUtilityClass = new CommonUtilityClass();
        nurseUtilityClass.setAge(1);
        nurseUtilityClass.setAgeUnits("Age Units");
        nurseUtilityClass.setAuthorization("JaneDoe");
        nurseUtilityClass.setBenFlowID(1L);
        nurseUtilityClass.setBenRegID(1L);
        nurseUtilityClass.setBenVisitID(1L);
        nurseUtilityClass.setBeneficiaryID(1L);
        nurseUtilityClass.setBeneficiaryRegID(1L);
        nurseUtilityClass.setBeneficiaryRegId(1L);
        nurseUtilityClass.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        nurseUtilityClass.setDistrictID(1);
        nurseUtilityClass.setDistrictName("District Name");
        nurseUtilityClass.setFacilityID(1);
        nurseUtilityClass.setFirstName("Jane");
        nurseUtilityClass.setGenderID((short) 1);
        nurseUtilityClass.setGenderName("Gender Name");
        nurseUtilityClass.setIsCovidFlowDone(true);
        nurseUtilityClass.setIsMobile(true);
        nurseUtilityClass.setIsSpecialist(true);
        nurseUtilityClass.setLastName("Doe");
        nurseUtilityClass.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        nurseUtilityClass.setParkingPlaceID(1);
        nurseUtilityClass.setParkingPlaceId(1);
        nurseUtilityClass.setPrescriptionID(1L);
        nurseUtilityClass.setProviderServiceMapID(1);
        nurseUtilityClass.setServiceID((short) 1);
        nurseUtilityClass.setSessionID(1);
        nurseUtilityClass.setSpecialistDiagnosis("Specialist Diagnosis");
        nurseUtilityClass.setSubVisitCategory("Sub Visit Category");
        nurseUtilityClass.setTreatmentsOnSideEffects("Treatments On Side Effects");
        nurseUtilityClass.setVanID(1);
        nurseUtilityClass.setVanId(1);
        nurseUtilityClass.setVillageId(1);
        nurseUtilityClass.setVillageName("Village Name");
        nurseUtilityClass.setVisitCategoryID(1);
        nurseUtilityClass.setVisitCode(1L);

        // Act and Assert
        assertTrue(aNCServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass).isEmpty());
    }

    @Test
    void testSaveBenANCDetails() throws Exception {
        // Arrange, Act and Assert
        assertNull(aNCServiceImpl.saveBenANCDetails(new JsonObject(), 1L, 1L));
        assertNull(aNCServiceImpl.saveBenANCDetails(null, 1L, 1L));
    }

    @Test
    void testSaveBenANCDetails2() throws Exception {
        // Arrange
        JsonObject ancDetailsOBJ = new JsonObject();
        ancDetailsOBJ.add("Property", new JsonArray(3));

        // Act and Assert
        assertNull(aNCServiceImpl.saveBenANCDetails(ancDetailsOBJ, 1L, 1L));
    }

    @Test
    void testSaveBenANCDetails3() throws Exception {
        // Arrange
        JsonObject ancDetailsOBJ = new JsonObject();
        ancDetailsOBJ.addProperty("ancObstetricDetails", (String) null);

        // Act and Assert
        assertNull(aNCServiceImpl.saveBenANCDetails(ancDetailsOBJ, 1L, 1L));
    }

    @Test
    void testSaveBenANCHistoryDetails() throws Exception {
        // Arrange, Act and Assert
        assertNull(aNCServiceImpl.saveBenANCHistoryDetails(new JsonObject(), 1L, 1L));
        assertNull(aNCServiceImpl.saveBenANCHistoryDetails(null, 1L, 1L));
    }

    @Test
    void testSaveBenANCHistoryDetails2() throws Exception {
        // Arrange
        JsonObject ancHistoryOBJ = new JsonObject();
        ancHistoryOBJ.add("Property", new JsonArray(3));

        // Act and Assert
        assertNull(aNCServiceImpl.saveBenANCHistoryDetails(ancHistoryOBJ, 1L, 1L));
    }

    @Test
    void testSaveBenANCHistoryDetails3() throws Exception {
        // Arrange
        JsonObject ancHistoryOBJ = new JsonObject();
        ancHistoryOBJ.addProperty("pastHistory", (String) null);

        // Act and Assert
        assertNull(aNCServiceImpl.saveBenANCHistoryDetails(ancHistoryOBJ, 1L, 1L));
    }

    @Test
    void testSaveBenANCVitalDetails() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(1L);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(1L);

        // Act
        Long actualSaveBenANCVitalDetailsResult = aNCServiceImpl.saveBenANCVitalDetails(new JsonObject(), 1L, 1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1L, actualSaveBenANCVitalDetailsResult.longValue());
    }

    @Test
    void testSaveBenANCVitalDetails2() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(0L);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(1L);

        // Act
        Long actualSaveBenANCVitalDetailsResult = aNCServiceImpl.saveBenANCVitalDetails(new JsonObject(), 1L, 1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertNull(actualSaveBenANCVitalDetailsResult);
    }

    @Test
    void testSaveBenANCVitalDetails3() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(null);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(1L);

        // Act
        Long actualSaveBenANCVitalDetailsResult = aNCServiceImpl.saveBenANCVitalDetails(new JsonObject(), 1L, 1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertNull(actualSaveBenANCVitalDetailsResult);
    }

    @Test
    void testSaveBenANCVitalDetails4() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(1L);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(0L);

        // Act
        Long actualSaveBenANCVitalDetailsResult = aNCServiceImpl.saveBenANCVitalDetails(new JsonObject(), 1L, 1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertNull(actualSaveBenANCVitalDetailsResult);
    }

    @Test
    void testSaveBenANCVitalDetails5() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(1L);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(null);

        // Act
        Long actualSaveBenANCVitalDetailsResult = aNCServiceImpl.saveBenANCVitalDetails(new JsonObject(), 1L, 1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertNull(actualSaveBenANCVitalDetailsResult);
    }

    @Test
    void testSaveBenANCVitalDetails6() throws Exception {
        // Arrange, Act and Assert
        assertNull(aNCServiceImpl.saveBenANCVitalDetails(null, 1L, 1L));
    }

    @Test
    void testSaveBenANCVitalDetails7() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(1L);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(1L);

        JsonObject vitalDetailsOBJ = new JsonObject();
        vitalDetailsOBJ.add("Property", new JsonArray(3));

        // Act
        Long actualSaveBenANCVitalDetailsResult = aNCServiceImpl.saveBenANCVitalDetails(vitalDetailsOBJ, 1L, 1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1L, actualSaveBenANCVitalDetailsResult.longValue());
    }

    @Test
    void testSaveBenANCVitalDetails8() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(1L);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(1L);

        JsonObject vitalDetailsOBJ = new JsonObject();
        vitalDetailsOBJ.addProperty("Property", "42");

        // Act
        Long actualSaveBenANCVitalDetailsResult = aNCServiceImpl.saveBenANCVitalDetails(vitalDetailsOBJ, 1L, 1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1L, actualSaveBenANCVitalDetailsResult.longValue());
    }

    @Test
    void testSaveBenANCVitalDetails9() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(1L);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(1L);

        JsonObject vitalDetailsOBJ = new JsonObject();
        vitalDetailsOBJ.addProperty("Property", Integer.valueOf(1));

        // Act
        Long actualSaveBenANCVitalDetailsResult = aNCServiceImpl.saveBenANCVitalDetails(vitalDetailsOBJ, 1L, 1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1L, actualSaveBenANCVitalDetailsResult.longValue());
    }

    @Test
    void testSaveBenANCVitalDetails10() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(1L);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(1L);

        JsonObject vitalDetailsOBJ = new JsonObject();
        vitalDetailsOBJ.addProperty("Property", true);

        // Act
        Long actualSaveBenANCVitalDetailsResult = aNCServiceImpl.saveBenANCVitalDetails(vitalDetailsOBJ, 1L, 1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1L, actualSaveBenANCVitalDetailsResult.longValue());
    }

    @Test
    void testSaveBenANCVitalDetails11() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> aNCServiceImpl.saveBenANCVitalDetails(new JsonObject(), 1L, 1L));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
    }

    @Test
    void testSaveBenANCExaminationDetails() throws Exception {
        // Arrange, Act and Assert
        assertNull(aNCServiceImpl.saveBenANCExaminationDetails(new JsonObject(), 1L, 1L));
        assertNull(aNCServiceImpl.saveBenANCExaminationDetails(null, 1L, 1L));
    }

    @Test
    void testSaveBenANCExaminationDetails2() throws Exception {
        // Arrange
        JsonObject examinationDetailsOBJ = new JsonObject();
        examinationDetailsOBJ.add("Property", new JsonArray(3));

        // Act and Assert
        assertNull(aNCServiceImpl.saveBenANCExaminationDetails(examinationDetailsOBJ, 1L, 1L));
    }

    @Test
    void testSaveBenANCExaminationDetails3() throws Exception {
        // Arrange
        JsonObject examinationDetailsOBJ = new JsonObject();
        examinationDetailsOBJ.addProperty("generalExamination", (String) null);

        // Act and Assert
        assertNull(aNCServiceImpl.saveBenANCExaminationDetails(examinationDetailsOBJ, 1L, 1L));
    }

    @Test
    void testGetBenVisitDetailsFrmNurseANC() {
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
        when(commonNurseServiceImpl.getBenAdherence(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn("Ben Adherence");
        when(commonNurseServiceImpl.getBenCdss(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn("Ben Cdss");
        when(commonNurseServiceImpl.getBenChiefComplaints(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Ben Chief Complaints");
        when(commonNurseServiceImpl.getLabTestOrders(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Lab Test Orders");

        // Act
        aNCServiceImpl.getBenVisitDetailsFrmNurseANC(1L, 1L);

        // Assert
        verify(commonNurseServiceImpl).getBenAdherence(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getBenCdss(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getBenChiefComplaints(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getCSVisitDetails(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getLabTestOrders(isA(Long.class), isA(Long.class));
        verify(createdDate).getTime();
        verify(lastModDate).getTime();
        verify(syncedDate).getTime();
        verify(visitDateTime).getTime();
    }

    @Test
    void testGetBenVisitDetailsFrmNurseANC2() {
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
        when(visitDateTime.getTime()).thenThrow(new RuntimeException("ANCNurseVisitDetail"));

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
        assertThrows(RuntimeException.class, () -> aNCServiceImpl.getBenVisitDetailsFrmNurseANC(1L, 1L));
        verify(commonNurseServiceImpl).getCSVisitDetails(isA(Long.class), isA(Long.class));
        verify(visitDateTime).getTime();
    }

    @Test
    void testGetBenVisitDetailsFrmNurseANC3() {
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
        when(commonNurseServiceImpl.getBenAdherence(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn("Ben Adherence");
        when(commonNurseServiceImpl.getBenCdss(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn("Ben Cdss");
        when(commonNurseServiceImpl.getBenChiefComplaints(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Ben Chief Complaints");
        when(commonNurseServiceImpl.getLabTestOrders(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Lab Test Orders");

        // Act
        String actualBenVisitDetailsFrmNurseANC = aNCServiceImpl.getBenVisitDetailsFrmNurseANC(1L, 1L);

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
        verify(commonNurseServiceImpl).getBenAdherence(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getBenCdss(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getBenChiefComplaints(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getCSVisitDetails(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getLabTestOrders(isA(Long.class), isA(Long.class));
        assertEquals("{BenAdherence=Ben Adherence, BenChiefComplaints=Ben Chief Complaints, Investigation=Lab Test Orders,"
                + " ANCNurseVisitDetail={}, Cdss=Ben Cdss}", actualBenVisitDetailsFrmNurseANC);
    }

    @Test
    void testGetBenANCDetailsFrmNurseANC() throws IEMRException {
        // Arrange
        when(aNCNurseServiceImpl.getANCCareDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Anc Care Details");
        when(aNCNurseServiceImpl.getANCWomenVaccineDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Anc Women Vaccine Details");

        // Act
        String actualBenANCDetailsFrmNurseANC = aNCServiceImpl.getBenANCDetailsFrmNurseANC(1L, 1L);

        // Assert
        verify(aNCNurseServiceImpl).getANCCareDetails(isA(Long.class), isA(Long.class));
        verify(aNCNurseServiceImpl).getANCWomenVaccineDetails(isA(Long.class), isA(Long.class));
        assertEquals("{ANCWomenVaccineDetails=Anc Women Vaccine Details, ANCCareDetail=Anc Care Details}",
                actualBenANCDetailsFrmNurseANC);
    }

    @Test
    void testGetBenANCDetailsFrmNurseANC2() throws IEMRException {
        // Arrange
        when(beneficiaryFlowStatusRepo.getLatestVisitCode(Mockito.<Long>any(), Mockito.<String>any())).thenReturn(1L);
        when(aNCNurseServiceImpl.getANCCareDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Anc Care Details");
        when(aNCNurseServiceImpl.getANCWomenVaccineDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Anc Women Vaccine Details");

        // Act
        String actualBenANCDetailsFrmNurseANC = aNCServiceImpl.getBenANCDetailsFrmNurseANC(1L, null);

        // Assert
        verify(beneficiaryFlowStatusRepo).getLatestVisitCode(isA(Long.class), eq("ANC"));
        verify(aNCNurseServiceImpl).getANCCareDetails(isA(Long.class), isA(Long.class));
        verify(aNCNurseServiceImpl).getANCWomenVaccineDetails(isA(Long.class), isA(Long.class));
        assertEquals("{ANCWomenVaccineDetails=Anc Women Vaccine Details, ANCCareDetail=Anc Care Details}",
                actualBenANCDetailsFrmNurseANC);
    }

    @Test
    void testGetBenANCDetailsFrmNurseANC3() throws IEMRException {
        // Arrange
        when(beneficiaryFlowStatusRepo.getLatestVisitCode(Mockito.<Long>any(), Mockito.<String>any())).thenReturn(null);

        // Act
        String actualBenANCDetailsFrmNurseANC = aNCServiceImpl.getBenANCDetailsFrmNurseANC(1L, null);

        // Assert
        verify(beneficiaryFlowStatusRepo).getLatestVisitCode(isA(Long.class), eq("ANC"));
        assertEquals("{}", actualBenANCDetailsFrmNurseANC);
    }

    @Test
    void testGetBenANCDetailsFrmNurseANC4() throws IEMRException {
        // Arrange
        when(aNCNurseServiceImpl.getANCCareDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Anc Care Details");
        when(aNCNurseServiceImpl.getANCWomenVaccineDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Anc Women Vaccine Details");

        // Act
        String actualBenANCDetailsFrmNurseANC = aNCServiceImpl.getBenANCDetailsFrmNurseANC(null, null);

        // Assert
        verify(aNCNurseServiceImpl).getANCCareDetails(isNull(), isNull());
        verify(aNCNurseServiceImpl).getANCWomenVaccineDetails(isNull(), isNull());
        assertEquals("{ANCWomenVaccineDetails=Anc Women Vaccine Details, ANCCareDetail=Anc Care Details}",
                actualBenANCDetailsFrmNurseANC);
    }

    @Test
    void testGetBenANCDetailsFrmNurseANC5() throws IEMRException {
        // Arrange
        when(beneficiaryFlowStatusRepo.getLatestVisitCode(Mockito.<Long>any(), Mockito.<String>any()))
                .thenThrow(new RuntimeException("ANC"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> aNCServiceImpl.getBenANCDetailsFrmNurseANC(1L, null));
        verify(beneficiaryFlowStatusRepo).getLatestVisitCode(isA(Long.class), eq("ANC"));
    }

    @Test
    void testGetBenANCHistoryDetails() {
        BenFamilyHistory benFamilyHistory = new BenFamilyHistory();
        benFamilyHistory.setBenVisitID(1L);
        benFamilyHistory.setBeneficiaryRegID(1L);
        benFamilyHistory.setCaptureDate(mock(Date.class));
        benFamilyHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benFamilyHistory.setCreatedDate(mock(Timestamp.class));
        benFamilyHistory.setDeleted(true);
        benFamilyHistory.setDiseaseType("Disease Type");
        benFamilyHistory.setDiseaseTypeID((short) 1);
        benFamilyHistory.setFamilyDiseaseList(new ArrayList<>());
        benFamilyHistory.setFamilyMember("Family Member");
        benFamilyHistory.setGeneticDisorder("Genetic Disorder");
        benFamilyHistory.setID(1L);
        benFamilyHistory.setIsConsanguineousMarrige("Is Consanguineous Marrige");
        benFamilyHistory.setIsGeneticDisorder("Is Genetic Disorder");
        benFamilyHistory.setLastModDate(mock(Timestamp.class));
        benFamilyHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benFamilyHistory.setOtherDiseaseType("Other Disease Type");
        benFamilyHistory.setParkingPlaceID(1);
        benFamilyHistory.setProcessed("Processed");
        benFamilyHistory.setProviderServiceMapID(1);
        benFamilyHistory.setReservedForChange("Reserved For Change");
        benFamilyHistory.setSnomedCode("Snomed Code");
        benFamilyHistory.setSnomedTerm("Snomed Term");
        benFamilyHistory.setSyncedBy("Synced By");
        benFamilyHistory.setSyncedDate(mock(Timestamp.class));
        benFamilyHistory.setVanID(1);
        benFamilyHistory.setVanSerialNo(1L);
        benFamilyHistory.setVehicalNo("Vehical No");
        benFamilyHistory.setVisitCode(1L);

        BenMedHistory benMedHistory = new BenMedHistory();
        benMedHistory.setBenMedHistoryID(1L);
        benMedHistory.setBenVisitID(1L);
        benMedHistory.setBeneficiaryRegID(1L);
        benMedHistory.setCaptureDate(mock(Date.class));
        benMedHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benMedHistory.setCreatedDate(mock(Timestamp.class));
        benMedHistory.setDeleted(true);
        benMedHistory.setDrugComplianceID((short) 1);
        benMedHistory.setIllnessType("Illness Type");
        benMedHistory.setIllnessTypeID(1);
        benMedHistory.setIllness_Type("Illness Type");
        benMedHistory.setLastModDate(mock(Timestamp.class));
        benMedHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benMedHistory.setOtherIllnessType("Other Illness Type");
        benMedHistory.setOtherSurgeryType("Other Surgery Type");
        benMedHistory.setOther_Illness_Type("Other Illness Type");
        benMedHistory.setOther_Surgery_Type("Other Surgery Type");
        benMedHistory.setParkingPlaceID(1);
        benMedHistory.setPastIllness(new ArrayList<>());
        benMedHistory.setPastSurgery(new ArrayList<>());
        benMedHistory.setProcessed("Processed");
        benMedHistory.setProviderServiceMapID(1);
        benMedHistory.setReservedForChange("Reserved For Change");
        benMedHistory.setSurgeryID(1);
        benMedHistory.setSurgeryType("Surgery Type");
        benMedHistory.setSurgery_Type("Surgery Type");
        benMedHistory.setSyncedBy("Synced By");
        benMedHistory.setSyncedDate(mock(Timestamp.class));
        benMedHistory.setVanID(1);
        benMedHistory.setVanSerialNo(1L);
        benMedHistory.setVehicalNo("Vehical No");
        benMedHistory.setVisitCode(1L);
        benMedHistory.setYear_Of_Illness(mock(Date.class));
        benMedHistory.setYear_Of_Surgery(mock(Date.class));
        benMedHistory.setYearofIllness(mock(Timestamp.class));
        benMedHistory.setYearofSurgery(mock(Timestamp.class));

        BenMenstrualDetails benMenstrualDetails = new BenMenstrualDetails();
        benMenstrualDetails.setBenMenstrualID(1);
        benMenstrualDetails.setBenVisitID(1L);
        benMenstrualDetails.setBeneficiaryRegID(1L);
        benMenstrualDetails.setBloodFlowDuration("Blood Flow Duration");
        benMenstrualDetails.setCaptureDate(mock(Date.class));
        benMenstrualDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benMenstrualDetails.setCreatedDate(mock(Timestamp.class));
        benMenstrualDetails.setCycleLength("Cycle Length");
        benMenstrualDetails.setDeleted(true);
        benMenstrualDetails.setLastModDate(mock(Timestamp.class));
        benMenstrualDetails.setLmp_date(mock(Date.class));
        benMenstrualDetails.setMenstrualCycleStatus("Menstrual Cycle Status");
        benMenstrualDetails.setMenstrualCycleStatusID((short) 1);
        benMenstrualDetails.setMenstrualCyclelengthID((short) 1);
        benMenstrualDetails.setMenstrualFlowDurationID((short) 1);
        benMenstrualDetails.setMenstrualProblemID("Menstrual Problem ID");
        benMenstrualDetails.setMenstrualProblemList(new ArrayList<>());
        benMenstrualDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benMenstrualDetails.setParkingPlaceID(1);
        benMenstrualDetails.setProblemName("Problem Name");
        benMenstrualDetails.setProcessed("Processed");
        benMenstrualDetails.setProviderServiceMapID(1);
        benMenstrualDetails.setRegularity("Regularity");
        benMenstrualDetails.setReservedForChange("Reserved For Change");
        benMenstrualDetails.setSyncedBy("Synced By");
        benMenstrualDetails.setSyncedDate(mock(Timestamp.class));
        benMenstrualDetails.setVanID(1);
        benMenstrualDetails.setVanSerialNo(1L);
        benMenstrualDetails.setVehicalNo("Vehical No");
        benMenstrualDetails.setVisitCode(1L);
        benMenstrualDetails.setlMPDate(mock(Timestamp.class));

        BenPersonalHabit benPersonalHabit = new BenPersonalHabit();
        benPersonalHabit.setAlcoholDuration(mock(Timestamp.class));
        benPersonalHabit.setAlcoholIntakeFrequency("Alcohol Intake Frequency");
        benPersonalHabit.setAlcoholIntakeStatus("Alcohol Intake Status");
        benPersonalHabit.setAlcoholList(new ArrayList<>());
        benPersonalHabit.setAlcoholType("Alcohol Type");
        benPersonalHabit.setAlcoholTypeID("Alcohol Type ID");
        benPersonalHabit.setAlcohol_use_duration(mock(Date.class));
        benPersonalHabit.setAllergicList(new ArrayList<>());
        benPersonalHabit.setAllergyStatus("Allergy Status");
        benPersonalHabit.setAvgAlcoholConsumption("Avg Alcohol Consumption");
        benPersonalHabit.setBenPersonalHabitID(1);
        benPersonalHabit.setBenVisitID(1L);
        benPersonalHabit.setBeneficiaryRegID(1L);
        benPersonalHabit.setCaptureDate(mock(Date.class));
        benPersonalHabit.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benPersonalHabit.setCreatedDate(mock(Timestamp.class));
        benPersonalHabit.setDeleted(true);
        benPersonalHabit.setDietaryType("Dietary Type");
        benPersonalHabit.setLastModDate(mock(Timestamp.class));
        benPersonalHabit.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benPersonalHabit.setNumberperDay((short) 1);
        benPersonalHabit.setNumberperWeek((short) 1);
        benPersonalHabit.setOtherAlcoholType("Other Alcohol Type");
        benPersonalHabit.setOtherTobaccoUseType("Other Tobacco Use Type");
        benPersonalHabit.setParkingPlaceID(1);
        benPersonalHabit.setPhysicalActivityType("Physical Activity Type");
        benPersonalHabit.setProcessed("Processed");
        benPersonalHabit.setProviderServiceMapID(1);
        benPersonalHabit.setReservedForChange("Reserved For Change");
        benPersonalHabit.setRiskySexualPracticeStatus("Risky Sexual Practice Status");
        benPersonalHabit.setRiskySexualPracticesStatus('A');
        benPersonalHabit.setSyncedBy("Synced By");
        benPersonalHabit.setSyncedDate(mock(Timestamp.class));
        benPersonalHabit.setTobaccoList(new ArrayList<>());
        benPersonalHabit.setTobaccoUseDuration(mock(Timestamp.class));
        benPersonalHabit.setTobaccoUseStatus("Tobacco Use Status");
        benPersonalHabit.setTobaccoUseType("Tobacco Use Type");
        benPersonalHabit.setTobaccoUseTypeID("Tobacco Use Type ID");
        benPersonalHabit.setTobacco_use_duration(mock(Date.class));
        benPersonalHabit.setVanID(1);
        benPersonalHabit.setVanSerialNo(1L);
        benPersonalHabit.setVehicalNo("Vehical No");
        benPersonalHabit.setVisitCode(1L);

        WrapperChildOptionalVaccineDetail wrapperChildOptionalVaccineDetail = new WrapperChildOptionalVaccineDetail();
        wrapperChildOptionalVaccineDetail.setBenVisitID(1L);
        wrapperChildOptionalVaccineDetail.setBeneficiaryRegID(1L);
        wrapperChildOptionalVaccineDetail.setChildOptionalVaccineList(new ArrayList<>());
        wrapperChildOptionalVaccineDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        wrapperChildOptionalVaccineDetail.setParkingPlaceID(1);
        wrapperChildOptionalVaccineDetail.setProviderServiceMapID(1);
        wrapperChildOptionalVaccineDetail.setVanID(1);
        wrapperChildOptionalVaccineDetail.setVisitCode(1L);

        WrapperComorbidCondDetails wrapperComorbidCondDetails = new WrapperComorbidCondDetails();
        wrapperComorbidCondDetails.setBenVisitID(1L);
        wrapperComorbidCondDetails.setBeneficiaryRegID(1L);
        wrapperComorbidCondDetails.setComorbidityConcurrentConditionsList(new ArrayList<>());
        wrapperComorbidCondDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        wrapperComorbidCondDetails.setParkingPlaceID(1);
        wrapperComorbidCondDetails.setProviderServiceMapID(1);
        wrapperComorbidCondDetails.setVanID(1);
        wrapperComorbidCondDetails.setVisitCode(1L);

        WrapperFemaleObstetricHistory wrapperFemaleObstetricHistory = new WrapperFemaleObstetricHistory();
        wrapperFemaleObstetricHistory.setBenVisitID(1L);
        wrapperFemaleObstetricHistory.setBeneficiaryRegID(1L);
        wrapperFemaleObstetricHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        wrapperFemaleObstetricHistory.setFemaleObstetricHistoryList(new ArrayList<>());
        wrapperFemaleObstetricHistory.setParkingPlaceID(1);
        wrapperFemaleObstetricHistory.setProviderServiceMapID(1);
        wrapperFemaleObstetricHistory.setTotalNoOfPreg((short) 1);
        wrapperFemaleObstetricHistory.setVanID(1);
        wrapperFemaleObstetricHistory.setVisitCode(1L);

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

        WrapperMedicationHistory wrapperMedicationHistory = new WrapperMedicationHistory();
        wrapperMedicationHistory.setBenVisitID(1L);
        wrapperMedicationHistory.setBeneficiaryRegID(1L);
        wrapperMedicationHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        wrapperMedicationHistory.setMedicationHistoryList(new ArrayList<>());
        wrapperMedicationHistory.setParkingPlaceID(1);
        wrapperMedicationHistory.setProviderServiceMapID(1);
        wrapperMedicationHistory.setVanID(1);
        wrapperMedicationHistory.setVisitCode(1L);
        when(commonNurseServiceImpl.getFamilyHistory(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(benFamilyHistory);
        when(commonNurseServiceImpl.getPastHistoryData(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(benMedHistory);
        when(commonNurseServiceImpl.getMenstrualHistory(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(benMenstrualDetails);
        when(commonNurseServiceImpl.getPersonalHistory(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(benPersonalHabit);
        when(commonNurseServiceImpl.getChildOptionalVaccineHistory(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(wrapperChildOptionalVaccineDetail);
        when(commonNurseServiceImpl.getComorbidityConditionsHistory(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(wrapperComorbidCondDetails);
        when(commonNurseServiceImpl.getFemaleObstetricHistory(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(wrapperFemaleObstetricHistory);
        when(commonNurseServiceImpl.getImmunizationHistory(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(wrapperImmunizationHistory);
        when(commonNurseServiceImpl.getMedicationHistory(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(wrapperMedicationHistory);

        // Act
        aNCServiceImpl.getBenANCHistoryDetails(1L, 1L);
    }

    @Test
    void testGetBeneficiaryVitalDetails() {
        // Arrange
        when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Beneficiary Physical Anthropometry Details");
        when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Beneficiary Physical Vital Details");

        // Act
        String actualBeneficiaryVitalDetails = aNCServiceImpl.getBeneficiaryVitalDetails(1L, 1L);

        // Assert
        verify(commonNurseServiceImpl).getBeneficiaryPhysicalAnthropometryDetails(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getBeneficiaryPhysicalVitalDetails(isA(Long.class), isA(Long.class));
        assertEquals(
                "{benAnthropometryDetail=Beneficiary Physical Anthropometry Details, benPhysicalVitalDetail=Beneficiary"
                        + " Physical Vital Details}",
                actualBeneficiaryVitalDetails);
    }

    @Test
    void testGetBeneficiaryVitalDetails2() {
        // Arrange
        when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenThrow(new RuntimeException("benAnthropometryDetail"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> aNCServiceImpl.getBeneficiaryVitalDetails(1L, 1L));
        verify(commonNurseServiceImpl).getBeneficiaryPhysicalAnthropometryDetails(isA(Long.class), isA(Long.class));
    }

    @Test
    void testGetBeneficiaryCdssDetails() {
        // Arrange
        when(commonNurseServiceImpl.getBenCdssDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Ben Cdss Details");

        // Act
        String actualBeneficiaryCdssDetails = aNCServiceImpl.getBeneficiaryCdssDetails(1L, 1L);

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
        assertThrows(RuntimeException.class, () -> aNCServiceImpl.getBeneficiaryCdssDetails(1L, 1L));
        verify(commonNurseServiceImpl).getBenCdssDetails(isA(Long.class), isA(Long.class));
    }

    @Test
    void testGetANCExaminationDetailsData() {
        SysObstetricExamination sysObstetricExamination = new SysObstetricExamination();
        sysObstetricExamination.setAbdominalScars("Abdominal Scars");
        sysObstetricExamination.setBenVisitID(1L);
        sysObstetricExamination.setBeneficiaryRegID(1L);
        sysObstetricExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        sysObstetricExamination.setCreatedDate(mock(Timestamp.class));
        sysObstetricExamination.setDeleted(true);
        sysObstetricExamination.setFetalHeartRate_BeatsPerMinute("Fetal Heart Rate Beats Per Minute");
        sysObstetricExamination.setFetalHeartSounds("Fetal Heart Sounds");
        sysObstetricExamination.setFetalMovements("Fetal Movements");
        sysObstetricExamination.setFetalPositionOrLie("Fetal Position Or Lie");
        sysObstetricExamination.setFetalPresentation("Fetal Presentation");
        sysObstetricExamination.setFundalHeight("Fundal Height");
        sysObstetricExamination.setID(1L);
        sysObstetricExamination.setIsHRP(true);
        sysObstetricExamination.setLastModDate(mock(Timestamp.class));
        sysObstetricExamination.setLowLyingPlacenta(true);
        sysObstetricExamination.setMalPresentation(true);
        sysObstetricExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        sysObstetricExamination.setParkingPlaceID(1);
        sysObstetricExamination.setProcessed("Processed");
        sysObstetricExamination.setProviderServiceMapID(1);
        sysObstetricExamination.setReasonsForHRP(new String[]{"Just cause"});
        sysObstetricExamination.setReasonsForHRPDB("Just cause");
        sysObstetricExamination.setReservedForChange("Reserved For Change");
        sysObstetricExamination.setSfh(10.0d);
        sysObstetricExamination.setSyncedBy("Synced By");
        sysObstetricExamination.setSyncedDate(mock(Timestamp.class));
        sysObstetricExamination.setVanID(1);
        sysObstetricExamination.setVanSerialNo(1L);
        sysObstetricExamination.setVehicalNo("Vehical No");
        sysObstetricExamination.setVertebralDeformity(true);
        sysObstetricExamination.setVisitCode(1L);
        sysObstetricExamination.setfHAndPOA_Interpretation("F HAnd POA Interpretation");
        sysObstetricExamination.setfHAndPOA_Status("F HAnd POA Status");
        when(aNCNurseServiceImpl.getSysObstetricExamination(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(sysObstetricExamination);

        PhyGeneralExamination phyGeneralExamination = new PhyGeneralExamination();
        phyGeneralExamination.setBenVisitID(1L);
        phyGeneralExamination.setBeneficiaryRegID(1L);
        phyGeneralExamination.setBuiltAndAppearance("Built And Appearance");
        phyGeneralExamination.setClubbing("Clubbing");
        phyGeneralExamination.setCoherence("Coherence");
        phyGeneralExamination.setComfortness("Comfortness");
        phyGeneralExamination.setConsciousness("Consciousness");
        phyGeneralExamination.setCooperation("Cooperation");
        phyGeneralExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        phyGeneralExamination.setCreatedDate(mock(Timestamp.class));
        phyGeneralExamination.setCyanosis("Cyanosis");
        phyGeneralExamination.setDangerSigns("Danger Signs");
        phyGeneralExamination.setDeleted(true);
        phyGeneralExamination.setEdema("Edema");
        phyGeneralExamination.setEdemaType("Edema Type");
        phyGeneralExamination.setExtentOfEdema("Extent Of Edema");
        phyGeneralExamination.setFoetalMovements("Foetal Movements");
        phyGeneralExamination.setGait("Gait");
        phyGeneralExamination.setID(1L);
        phyGeneralExamination.setJaundice("Jaundice");
        phyGeneralExamination.setLastModDate(mock(Timestamp.class));
        phyGeneralExamination.setLymphadenopathy("Lymphadenopathy");
        phyGeneralExamination.setLymphnodesInvolved("Lymphnodes Involved");
        phyGeneralExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        phyGeneralExamination.setPallor("Pallor");
        phyGeneralExamination.setParkingPlaceID(1);
        phyGeneralExamination.setProcessed("Processed");
        phyGeneralExamination.setProviderServiceMapID(1);
        phyGeneralExamination.setQuickening("Quickening");
        phyGeneralExamination.setReservedForChange("Reserved For Change");
        phyGeneralExamination.setSyncedBy("Synced By");
        phyGeneralExamination.setSyncedDate(mock(Timestamp.class));
        phyGeneralExamination.setTypeOfDangerSign("Type Of Danger Sign");
        phyGeneralExamination.setTypeOfDangerSigns(new ArrayList<>());
        phyGeneralExamination.setTypeOfLymphadenopathy("Type Of Lymphadenopathy");
        phyGeneralExamination.setVanSerialNo(1L);
        phyGeneralExamination.setVehicalNo("Vehical No");
        phyGeneralExamination.setVisitCode(1L);

        PhyHeadToToeExamination phyHeadToToeExamination = new PhyHeadToToeExamination();
        phyHeadToToeExamination.setBenVisitID(1L);
        phyHeadToToeExamination.setBeneficiaryRegID(1L);
        phyHeadToToeExamination.setBreastAndNipples("Breast And Nipples");
        phyHeadToToeExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        phyHeadToToeExamination.setCreatedDate(mock(Timestamp.class));
        phyHeadToToeExamination.setDeleted(true);
        phyHeadToToeExamination.setEars("Ears");
        phyHeadToToeExamination.setEyes("Eyes");
        phyHeadToToeExamination.setHair("Hair");
        phyHeadToToeExamination.setHead("Head");
        phyHeadToToeExamination.setID(1L);
        phyHeadToToeExamination.setLastModDate(mock(Timestamp.class));
        phyHeadToToeExamination.setLowerLimbs("Lower Limbs");
        phyHeadToToeExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        phyHeadToToeExamination.setNails("Nails");
        phyHeadToToeExamination.setNipples("Nipples");
        phyHeadToToeExamination.setNose("Nose");
        phyHeadToToeExamination.setOralCavity("Oral Cavity");
        phyHeadToToeExamination.setParkingPlaceID(1);
        phyHeadToToeExamination.setProcessed("Processed");
        phyHeadToToeExamination.setProviderServiceMapID(1);
        phyHeadToToeExamination.setReservedForChange("Reserved For Change");
        phyHeadToToeExamination.setSkin("Skin");
        phyHeadToToeExamination.setSyncedBy("Synced By");
        phyHeadToToeExamination.setSyncedDate(mock(Timestamp.class));
        phyHeadToToeExamination.setThroat("Throat");
        phyHeadToToeExamination.setTrunk("Trunk");
        phyHeadToToeExamination.setUpperLimbs("Upper Limbs");
        phyHeadToToeExamination.setVanID(1);
        phyHeadToToeExamination.setVanSerialNo(1L);
        phyHeadToToeExamination.setVehicalNo("Vehical No");
        phyHeadToToeExamination.setVisitCode(1L);

        SysCardiovascularExamination sysCardiovascularExamination = new SysCardiovascularExamination();
        sysCardiovascularExamination.setAdditionalHeartSounds("Additional Heart Sounds");
        sysCardiovascularExamination.setApexbeatLocation("Apexbeat Location");
        sysCardiovascularExamination.setApexbeatType("Apexbeat Type");
        sysCardiovascularExamination.setBenVisitID(1L);
        sysCardiovascularExamination.setBeneficiaryRegID(1L);
        sysCardiovascularExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        sysCardiovascularExamination.setCreatedDate(mock(Timestamp.class));
        sysCardiovascularExamination.setDeleted(true);
        sysCardiovascularExamination.setFirstHeartSound_S1("First Heart Sound S1");
        sysCardiovascularExamination.setID(1L);
        sysCardiovascularExamination.setJugularVenousPulse_JVP("Jugular Venous Pulse JVP");
        sysCardiovascularExamination.setLastModDate(mock(Timestamp.class));
        sysCardiovascularExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        sysCardiovascularExamination.setMurmurs("Murmurs");
        sysCardiovascularExamination.setParkingPlaceID(1);
        sysCardiovascularExamination.setPericardialRub("Pericardial Rub");
        sysCardiovascularExamination.setProcessed("Processed");
        sysCardiovascularExamination.setProviderServiceMapID(1);
        sysCardiovascularExamination.setReservedForChange("Reserved For Change");
        sysCardiovascularExamination.setSecondHeartSound_S2("Second Heart Sound S2");
        sysCardiovascularExamination.setSyncedBy("Synced By");
        sysCardiovascularExamination.setSyncedDate(mock(Timestamp.class));
        sysCardiovascularExamination.setVanSerialNo(1L);
        sysCardiovascularExamination.setVehicalNo("Vehical No");
        sysCardiovascularExamination.setVisitCode(1L);

        SysCentralNervousExamination sysCentralNervousExamination = new SysCentralNervousExamination();
        sysCentralNervousExamination.setAutonomicSystem("Autonomic System");
        sysCentralNervousExamination.setBenVisitID(1L);
        sysCentralNervousExamination.setBeneficiaryRegID(1L);
        sysCentralNervousExamination.setCerebellarSigns("Cerebellar Signs");
        sysCentralNervousExamination.setCranialNervesExamination("Cranial Nerves Examination");
        sysCentralNervousExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        sysCentralNervousExamination.setCreatedDate(mock(Timestamp.class));
        sysCentralNervousExamination.setDeleted(true);
        sysCentralNervousExamination.setHandedness("Handedness");
        sysCentralNervousExamination.setID(1L);
        sysCentralNervousExamination.setLastModDate(mock(Timestamp.class));
        sysCentralNervousExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        sysCentralNervousExamination.setMotorSystem("Motor System");
        sysCentralNervousExamination.setParkingPlaceID(1);
        sysCentralNervousExamination.setProcessed("Processed");
        sysCentralNervousExamination.setProviderServiceMapID(1);
        sysCentralNervousExamination.setReservedForChange("Reserved For Change");
        sysCentralNervousExamination.setSensorySystem("Sensory System");
        sysCentralNervousExamination.setSignsOfMeningealIrritation("Signs Of Meningeal Irritation");
        sysCentralNervousExamination.setSkull("Skull");
        sysCentralNervousExamination.setSyncedBy("Synced By");
        sysCentralNervousExamination.setSyncedDate(mock(Timestamp.class));
        sysCentralNervousExamination.setVanSerialNo(1L);
        sysCentralNervousExamination.setVehicalNo("Vehical No");
        sysCentralNervousExamination.setVisitCode(1L);

        SysGenitourinarySystemExamination sysGenitourinarySystemExamination = new SysGenitourinarySystemExamination();
        sysGenitourinarySystemExamination.setBenVisitID(1L);
        sysGenitourinarySystemExamination.setBeneficiaryRegID(1L);
        sysGenitourinarySystemExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        sysGenitourinarySystemExamination.setCreatedDate(mock(Timestamp.class));
        sysGenitourinarySystemExamination.setDeleted(true);
        sysGenitourinarySystemExamination.setExternalGenitalia("External Genitalia");
        sysGenitourinarySystemExamination.setID(1L);
        sysGenitourinarySystemExamination.setLastModDate(mock(Timestamp.class));
        sysGenitourinarySystemExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        sysGenitourinarySystemExamination.setParkingPlaceID(1);
        sysGenitourinarySystemExamination.setProcessed("Processed");
        sysGenitourinarySystemExamination.setProviderServiceMapID(1);
        sysGenitourinarySystemExamination.setRenalAngle("Renal Angle");
        sysGenitourinarySystemExamination.setReservedForChange("Reserved For Change");
        sysGenitourinarySystemExamination.setSuprapubicRegion("us-east-2");
        sysGenitourinarySystemExamination.setSyncedBy("Synced By");
        sysGenitourinarySystemExamination.setSyncedDate(mock(Timestamp.class));
        sysGenitourinarySystemExamination.setVanSerialNo(1L);
        sysGenitourinarySystemExamination.setVehicalNo("Vehical No");
        sysGenitourinarySystemExamination.setVisitCode(1L);

        SysMusculoskeletalSystemExamination sysMusculoskeletalSystemExamination = new SysMusculoskeletalSystemExamination();
        sysMusculoskeletalSystemExamination.setBenVisitID(1L);
        sysMusculoskeletalSystemExamination.setBeneficiaryRegID(1L);
        sysMusculoskeletalSystemExamination.setChestWall("Chest Wall");
        sysMusculoskeletalSystemExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        sysMusculoskeletalSystemExamination.setCreatedDate(mock(Timestamp.class));
        sysMusculoskeletalSystemExamination.setDeleted(true);
        sysMusculoskeletalSystemExamination.setID(1L);
        sysMusculoskeletalSystemExamination.setJoint_Abnormality("Joint Abnormality");
        sysMusculoskeletalSystemExamination.setJoint_Laterality("Joint Laterality");
        sysMusculoskeletalSystemExamination.setJoint_TypeOfJoint("Joint Type Of Joint");
        sysMusculoskeletalSystemExamination.setLastModDate(mock(Timestamp.class));
        sysMusculoskeletalSystemExamination.setLowerLimb_Abnormality("Lower Limb Abnormality");
        sysMusculoskeletalSystemExamination.setLowerLimb_Laterality("Lower Limb Laterality");
        sysMusculoskeletalSystemExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        sysMusculoskeletalSystemExamination.setParkingPlaceID(1);
        sysMusculoskeletalSystemExamination.setProcessed("Processed");
        sysMusculoskeletalSystemExamination.setProviderServiceMapID(1);
        sysMusculoskeletalSystemExamination.setReservedForChange("Reserved For Change");
        sysMusculoskeletalSystemExamination.setSpine("Spine");
        sysMusculoskeletalSystemExamination.setSyncedBy("Synced By");
        sysMusculoskeletalSystemExamination.setSyncedDate(mock(Timestamp.class));
        sysMusculoskeletalSystemExamination.setUpperLimb_Abnormality("Upper Limb Abnormality");
        sysMusculoskeletalSystemExamination.setUpperLimb_Laterality("Upper Limb Laterality");
        sysMusculoskeletalSystemExamination.setVanSerialNo(1L);
        sysMusculoskeletalSystemExamination.setVehicalNo("Vehical No");
        sysMusculoskeletalSystemExamination.setVisitCode(1L);

        SysRespiratoryExamination sysRespiratoryExamination = new SysRespiratoryExamination();
        sysRespiratoryExamination.setAuscultation("Auscultation");
        sysRespiratoryExamination.setAuscultation_BreathSounds("Auscultation Breath Sounds");
        sysRespiratoryExamination.setAuscultation_ConductedSounds("Auscultation Conducted Sounds");
        sysRespiratoryExamination.setAuscultation_Crepitations("Auscultation Crepitations");
        sysRespiratoryExamination.setAuscultation_PleuralRub("Auscultation Pleural Rub");
        sysRespiratoryExamination.setAuscultation_Stridor("Auscultation Stridor");
        sysRespiratoryExamination.setAuscultation_Wheezing("Auscultation Wheezing");
        sysRespiratoryExamination.setBenVisitID(1L);
        sysRespiratoryExamination.setBeneficiaryRegID(1L);
        sysRespiratoryExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        sysRespiratoryExamination.setCreatedDate(mock(Timestamp.class));
        sysRespiratoryExamination.setDeleted(true);
        sysRespiratoryExamination.setID(1L);
        sysRespiratoryExamination.setInspection("Inspection");
        sysRespiratoryExamination.setLastModDate(mock(Timestamp.class));
        sysRespiratoryExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        sysRespiratoryExamination.setPalpation("Palpation");
        sysRespiratoryExamination.setParkingPlaceID(1);
        sysRespiratoryExamination.setPercussion("Percussion");
        sysRespiratoryExamination.setProcessed("Processed");
        sysRespiratoryExamination.setProviderServiceMapID(1);
        sysRespiratoryExamination.setReservedForChange("Reserved For Change");
        sysRespiratoryExamination.setSignsOfRespiratoryDistress("Signs Of Respiratory Distress");
        sysRespiratoryExamination.setSyncedBy("Synced By");
        sysRespiratoryExamination.setSyncedDate(mock(Timestamp.class));
        sysRespiratoryExamination.setTrachea("Trachea");
        sysRespiratoryExamination.setVanSerialNo(1L);
        sysRespiratoryExamination.setVehicalNo("Vehical No");
        sysRespiratoryExamination.setVisitCode(1L);
        when(commonNurseServiceImpl.getGeneralExaminationData(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(phyGeneralExamination);
        when(commonNurseServiceImpl.getHeadToToeExaminationData(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(phyHeadToToeExamination);
        when(commonNurseServiceImpl.getCardiovascularExamination(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(sysCardiovascularExamination);
        when(commonNurseServiceImpl.getSysCentralNervousExamination(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(sysCentralNervousExamination);
        when(commonNurseServiceImpl.getGenitourinaryExamination(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(sysGenitourinarySystemExamination);
        when(commonNurseServiceImpl.getMusculoskeletalExamination(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(sysMusculoskeletalSystemExamination);
        when(commonNurseServiceImpl.getRespiratoryExamination(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(sysRespiratoryExamination);

        // Act
        aNCServiceImpl.getANCExaminationDetailsData(1L, 1L);
    }

    @Test
    void testUpdateBenANCDetails() throws Exception {
        // Arrange, Act and Assert
        assertEquals(0, aNCServiceImpl.updateBenANCDetails(new JsonObject()));
        assertEquals(0, aNCServiceImpl.updateBenANCDetails(null));
    }

    @Test
    void testUpdateBenANCDetails2() throws Exception {
        // Arrange
        JsonObject ancDetailsOBJ = new JsonObject();
        ancDetailsOBJ.add("Property", new JsonArray(3));

        // Act and Assert
        assertEquals(0, aNCServiceImpl.updateBenANCDetails(ancDetailsOBJ));
    }

    @Test
    void testUpdateBenANCDetails3() throws Exception {
        // Arrange
        JsonObject ancDetailsOBJ = new JsonObject();
        ancDetailsOBJ.addProperty("ancObstetricDetails", (String) null);

        // Act and Assert
        assertEquals(0, aNCServiceImpl.updateBenANCDetails(ancDetailsOBJ));
    }

    @Test
    void testUpdateBenANCDetails4() throws Exception {
        // Arrange
        when(aNCNurseServiceImpl.updateBenAncCareDetails(Mockito.<ANCCareDetails>any())).thenReturn(1);

        JsonObject ancDetailsOBJ = new JsonObject();
        ancDetailsOBJ.add("ancObstetricDetails", new JsonObject());

        // Act
        int actualUpdateBenANCDetailsResult = aNCServiceImpl.updateBenANCDetails(ancDetailsOBJ);

        // Assert
        verify(aNCNurseServiceImpl).updateBenAncCareDetails(isA(ANCCareDetails.class));
        assertEquals(0, actualUpdateBenANCDetailsResult);
    }

    @Test
    void testUpdateBenANCHistoryDetails() throws Exception {
        // Arrange, Act and Assert
        assertEquals(1, aNCServiceImpl.updateBenANCHistoryDetails(new JsonObject()));
        assertEquals(1, aNCServiceImpl.updateBenANCHistoryDetails(null));
    }

    @Test
    void testUpdateBenANCHistoryDetails2() throws Exception {
        // Arrange
        JsonObject ancHistoryOBJ = new JsonObject();
        ancHistoryOBJ.add("Property", new JsonArray(3));

        // Act and Assert
        assertEquals(1, aNCServiceImpl.updateBenANCHistoryDetails(ancHistoryOBJ));
    }

    @Test
    void testUpdateBenANCHistoryDetails3() throws Exception {
        // Arrange
        JsonObject ancHistoryOBJ = new JsonObject();
        ancHistoryOBJ.addProperty("pastHistory", (String) null);

        // Act and Assert
        assertEquals(1, aNCServiceImpl.updateBenANCHistoryDetails(ancHistoryOBJ));
    }

    @Test
    void testUpdateBenANCHistoryDetails4() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateBenPastHistoryDetails(Mockito.<BenMedHistory>any())).thenReturn(1);

        JsonObject ancHistoryOBJ = new JsonObject();
        ancHistoryOBJ.add("pastHistory", new JsonObject());

        // Act
        int actualUpdateBenANCHistoryDetailsResult = aNCServiceImpl.updateBenANCHistoryDetails(ancHistoryOBJ);

        // Assert
        verify(commonNurseServiceImpl).updateBenPastHistoryDetails(isA(BenMedHistory.class));
        assertEquals(1, actualUpdateBenANCHistoryDetailsResult);
    }

    @Test
    void testUpdateBenANCHistoryDetails5() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateBenPastHistoryDetails(Mockito.<BenMedHistory>any())).thenReturn(0);

        JsonObject ancHistoryOBJ = new JsonObject();
        ancHistoryOBJ.add("pastHistory", new JsonObject());

        // Act
        int actualUpdateBenANCHistoryDetailsResult = aNCServiceImpl.updateBenANCHistoryDetails(ancHistoryOBJ);

        // Assert
        verify(commonNurseServiceImpl).updateBenPastHistoryDetails(isA(BenMedHistory.class));
        assertEquals(0, actualUpdateBenANCHistoryDetailsResult);
    }

    @Test
    void testUpdateBenANCVitalDetails() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateANCAnthropometryDetails(Mockito.<BenAnthropometryDetail>any())).thenReturn(1);
        when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any())).thenReturn(1);

        // Act
        int actualUpdateBenANCVitalDetailsResult = aNCServiceImpl.updateBenANCVitalDetails(new JsonObject());

        // Assert
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).updateANCPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1, actualUpdateBenANCVitalDetailsResult);
    }

    @Test
    void testUpdateBenANCVitalDetails2() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateANCAnthropometryDetails(Mockito.<BenAnthropometryDetail>any())).thenReturn(0);
        when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any())).thenReturn(1);

        // Act
        int actualUpdateBenANCVitalDetailsResult = aNCServiceImpl.updateBenANCVitalDetails(new JsonObject());

        // Assert
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).updateANCPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(0, actualUpdateBenANCVitalDetailsResult);
    }

    @Test
    void testUpdateBenANCVitalDetails3() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateANCAnthropometryDetails(Mockito.<BenAnthropometryDetail>any())).thenReturn(1);
        when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any())).thenReturn(0);

        // Act
        int actualUpdateBenANCVitalDetailsResult = aNCServiceImpl.updateBenANCVitalDetails(new JsonObject());

        // Assert
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).updateANCPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(0, actualUpdateBenANCVitalDetailsResult);
    }

    @Test
    void testUpdateBenANCVitalDetails4() throws Exception {
        // Arrange, Act and Assert
        assertEquals(0, aNCServiceImpl.updateBenANCVitalDetails(null));
    }

    @Test
    void testUpdateBenANCVitalDetails5() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateANCAnthropometryDetails(Mockito.<BenAnthropometryDetail>any())).thenReturn(1);
        when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any())).thenReturn(1);

        JsonObject vitalDetailsOBJ = new JsonObject();
        vitalDetailsOBJ.add("Property", new JsonArray(3));

        // Act
        int actualUpdateBenANCVitalDetailsResult = aNCServiceImpl.updateBenANCVitalDetails(vitalDetailsOBJ);

        // Assert
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).updateANCPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1, actualUpdateBenANCVitalDetailsResult);
    }

    @Test
    void testUpdateBenANCVitalDetails6() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateANCAnthropometryDetails(Mockito.<BenAnthropometryDetail>any())).thenReturn(1);
        when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any())).thenReturn(1);

        JsonObject vitalDetailsOBJ = new JsonObject();
        vitalDetailsOBJ.addProperty("Property", "42");

        // Act
        int actualUpdateBenANCVitalDetailsResult = aNCServiceImpl.updateBenANCVitalDetails(vitalDetailsOBJ);

        // Assert
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).updateANCPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1, actualUpdateBenANCVitalDetailsResult);
    }

    @Test
    void testUpdateBenANCVitalDetails7() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateANCAnthropometryDetails(Mockito.<BenAnthropometryDetail>any())).thenReturn(1);
        when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any())).thenReturn(1);

        JsonObject vitalDetailsOBJ = new JsonObject();
        Integer value = Integer.valueOf(1);
        vitalDetailsOBJ.addProperty("Property", value);

        // Act
        int actualUpdateBenANCVitalDetailsResult = aNCServiceImpl.updateBenANCVitalDetails(vitalDetailsOBJ);

        // Assert
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).updateANCPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1, actualUpdateBenANCVitalDetailsResult);
        assertSame(value, actualUpdateBenANCVitalDetailsResult);
    }

    @Test
    void testUpdateBenANCVitalDetails8() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateANCAnthropometryDetails(Mockito.<BenAnthropometryDetail>any())).thenReturn(1);
        when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any())).thenReturn(1);

        JsonObject vitalDetailsOBJ = new JsonObject();
        vitalDetailsOBJ.addProperty("Property", true);

        // Act
        int actualUpdateBenANCVitalDetailsResult = aNCServiceImpl.updateBenANCVitalDetails(vitalDetailsOBJ);

        // Assert
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).updateANCPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1, actualUpdateBenANCVitalDetailsResult);
    }

    @Test
    void testUpdateBenANCVitalDetails9() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateANCAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenThrow(new IEMRException("An error occurred"));

        // Act and Assert
        assertThrows(IEMRException.class, () -> aNCServiceImpl.updateBenANCVitalDetails(new JsonObject()));
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
    }

    @Test
    void testUpdateBenANCVitalDetails10() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateANCAnthropometryDetails(Mockito.<BenAnthropometryDetail>any())).thenReturn(1);
        when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any())).thenReturn(1);

        JsonObject vitalDetailsOBJ = new JsonObject();
        vitalDetailsOBJ.add("Property", null);

        // Act
        int actualUpdateBenANCVitalDetailsResult = aNCServiceImpl.updateBenANCVitalDetails(vitalDetailsOBJ);

        // Assert
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).updateANCPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1, actualUpdateBenANCVitalDetailsResult);
    }

    @Test
    void testUpdateBenANCExaminationDetails() throws Exception {
        // Arrange, Act and Assert
        assertEquals(1, aNCServiceImpl.updateBenANCExaminationDetails(new JsonObject()));
        assertEquals(1, aNCServiceImpl.updateBenANCExaminationDetails(null));
    }

    @Test
    void testUpdateBenANCExaminationDetails2() throws Exception {
        // Arrange
        JsonObject examinationDetailsOBJ = new JsonObject();
        examinationDetailsOBJ.add("Property", new JsonArray(3));

        // Act and Assert
        assertEquals(1, aNCServiceImpl.updateBenANCExaminationDetails(examinationDetailsOBJ));
    }

    @Test
    void testUpdateBenANCExaminationDetails3() throws Exception {
        // Arrange
        JsonObject examinationDetailsOBJ = new JsonObject();
        examinationDetailsOBJ.addProperty("generalExamination", (String) null);

        // Act and Assert
        assertEquals(1, aNCServiceImpl.updateBenANCExaminationDetails(examinationDetailsOBJ));
    }

    @Test
    void testUpdateBenANCExaminationDetails4() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updatePhyGeneralExamination(Mockito.<PhyGeneralExamination>any())).thenReturn(1);

        JsonObject examinationDetailsOBJ = new JsonObject();
        examinationDetailsOBJ.add("generalExamination", new JsonObject());

        // Act
        int actualUpdateBenANCExaminationDetailsResult = aNCServiceImpl
                .updateBenANCExaminationDetails(examinationDetailsOBJ);

        // Assert
        verify(commonNurseServiceImpl).updatePhyGeneralExamination(isA(PhyGeneralExamination.class));
        assertEquals(1, actualUpdateBenANCExaminationDetailsResult);
    }

    @Test
    void testGetBenANCNurseData() throws IEMRException {
        SysObstetricExamination sysObstetricExamination = new SysObstetricExamination();
        sysObstetricExamination.setAbdominalScars("Abdominal Scars");
        sysObstetricExamination.setBenVisitID(1L);
        sysObstetricExamination.setBeneficiaryRegID(1L);
        sysObstetricExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        sysObstetricExamination.setCreatedDate(mock(Timestamp.class));
        sysObstetricExamination.setDeleted(true);
        sysObstetricExamination.setFetalHeartRate_BeatsPerMinute("Fetal Heart Rate Beats Per Minute");
        sysObstetricExamination.setFetalHeartSounds("Fetal Heart Sounds");
        sysObstetricExamination.setFetalMovements("Fetal Movements");
        sysObstetricExamination.setFetalPositionOrLie("Fetal Position Or Lie");
        sysObstetricExamination.setFetalPresentation("Fetal Presentation");
        sysObstetricExamination.setFundalHeight("Fundal Height");
        sysObstetricExamination.setID(1L);
        sysObstetricExamination.setIsHRP(true);
        sysObstetricExamination.setLastModDate(mock(Timestamp.class));
        sysObstetricExamination.setLowLyingPlacenta(true);
        sysObstetricExamination.setMalPresentation(true);
        sysObstetricExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        sysObstetricExamination.setParkingPlaceID(1);
        sysObstetricExamination.setProcessed("Processed");
        sysObstetricExamination.setProviderServiceMapID(1);
        sysObstetricExamination.setReasonsForHRP(new String[]{"Just cause"});
        sysObstetricExamination.setReasonsForHRPDB("Just cause");
        sysObstetricExamination.setReservedForChange("Reserved For Change");
        sysObstetricExamination.setSfh(10.0d);
        sysObstetricExamination.setSyncedBy("Synced By");
        sysObstetricExamination.setSyncedDate(mock(Timestamp.class));
        sysObstetricExamination.setVanID(1);
        sysObstetricExamination.setVanSerialNo(1L);
        sysObstetricExamination.setVehicalNo("Vehical No");
        sysObstetricExamination.setVertebralDeformity(true);
        sysObstetricExamination.setVisitCode(1L);
        sysObstetricExamination.setfHAndPOA_Interpretation("F HAnd POA Interpretation");
        sysObstetricExamination.setfHAndPOA_Status("F HAnd POA Status");
        when(aNCNurseServiceImpl.getSysObstetricExamination(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(sysObstetricExamination);
        when(aNCNurseServiceImpl.getANCCareDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Anc Care Details");
        when(aNCNurseServiceImpl.getANCWomenVaccineDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Anc Women Vaccine Details");

        BenFamilyHistory benFamilyHistory = new BenFamilyHistory();
        benFamilyHistory.setBenVisitID(1L);
        benFamilyHistory.setBeneficiaryRegID(1L);
        benFamilyHistory.setCaptureDate(mock(Date.class));
        benFamilyHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benFamilyHistory.setCreatedDate(mock(Timestamp.class));
        benFamilyHistory.setDeleted(true);
        benFamilyHistory.setDiseaseType("Disease Type");
        benFamilyHistory.setDiseaseTypeID((short) 1);
        benFamilyHistory.setFamilyDiseaseList(new ArrayList<>());
        benFamilyHistory.setFamilyMember("Family Member");
        benFamilyHistory.setGeneticDisorder("Genetic Disorder");
        benFamilyHistory.setID(1L);
        benFamilyHistory.setIsConsanguineousMarrige("Is Consanguineous Marrige");
        benFamilyHistory.setIsGeneticDisorder("Is Genetic Disorder");
        benFamilyHistory.setLastModDate(mock(Timestamp.class));
        benFamilyHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benFamilyHistory.setOtherDiseaseType("Other Disease Type");
        benFamilyHistory.setParkingPlaceID(1);
        benFamilyHistory.setProcessed("Processed");
        benFamilyHistory.setProviderServiceMapID(1);
        benFamilyHistory.setReservedForChange("Reserved For Change");
        benFamilyHistory.setSnomedCode("Snomed Code");
        benFamilyHistory.setSnomedTerm("Snomed Term");
        benFamilyHistory.setSyncedBy("Synced By");
        benFamilyHistory.setSyncedDate(mock(Timestamp.class));
        benFamilyHistory.setVanID(1);
        benFamilyHistory.setVanSerialNo(1L);
        benFamilyHistory.setVehicalNo("Vehical No");
        benFamilyHistory.setVisitCode(1L);

        BenMedHistory benMedHistory = new BenMedHistory();
        benMedHistory.setBenMedHistoryID(1L);
        benMedHistory.setBenVisitID(1L);
        benMedHistory.setBeneficiaryRegID(1L);
        benMedHistory.setCaptureDate(mock(Date.class));
        benMedHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benMedHistory.setCreatedDate(mock(Timestamp.class));
        benMedHistory.setDeleted(true);
        benMedHistory.setDrugComplianceID((short) 1);
        benMedHistory.setIllnessType("Illness Type");
        benMedHistory.setIllnessTypeID(1);
        benMedHistory.setIllness_Type("Illness Type");
        benMedHistory.setLastModDate(mock(Timestamp.class));
        benMedHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benMedHistory.setOtherIllnessType("Other Illness Type");
        benMedHistory.setOtherSurgeryType("Other Surgery Type");
        benMedHistory.setOther_Illness_Type("Other Illness Type");
        benMedHistory.setOther_Surgery_Type("Other Surgery Type");
        benMedHistory.setParkingPlaceID(1);
        benMedHistory.setPastIllness(new ArrayList<>());
        benMedHistory.setPastSurgery(new ArrayList<>());
        benMedHistory.setProcessed("Processed");
        benMedHistory.setProviderServiceMapID(1);
        benMedHistory.setReservedForChange("Reserved For Change");
        benMedHistory.setSurgeryID(1);
        benMedHistory.setSurgeryType("Surgery Type");
        benMedHistory.setSurgery_Type("Surgery Type");
        benMedHistory.setSyncedBy("Synced By");
        benMedHistory.setSyncedDate(mock(Timestamp.class));
        benMedHistory.setVanID(1);
        benMedHistory.setVanSerialNo(1L);
        benMedHistory.setVehicalNo("Vehical No");
        benMedHistory.setVisitCode(1L);
        benMedHistory.setYear_Of_Illness(mock(Date.class));
        benMedHistory.setYear_Of_Surgery(mock(Date.class));
        benMedHistory.setYearofIllness(mock(Timestamp.class));
        benMedHistory.setYearofSurgery(mock(Timestamp.class));

        BenMenstrualDetails benMenstrualDetails = new BenMenstrualDetails();
        benMenstrualDetails.setBenMenstrualID(1);
        benMenstrualDetails.setBenVisitID(1L);
        benMenstrualDetails.setBeneficiaryRegID(1L);
        benMenstrualDetails.setBloodFlowDuration("Blood Flow Duration");
        benMenstrualDetails.setCaptureDate(mock(Date.class));
        benMenstrualDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benMenstrualDetails.setCreatedDate(mock(Timestamp.class));
        benMenstrualDetails.setCycleLength("Cycle Length");
        benMenstrualDetails.setDeleted(true);
        benMenstrualDetails.setLastModDate(mock(Timestamp.class));
        benMenstrualDetails.setLmp_date(mock(Date.class));
        benMenstrualDetails.setMenstrualCycleStatus("Menstrual Cycle Status");
        benMenstrualDetails.setMenstrualCycleStatusID((short) 1);
        benMenstrualDetails.setMenstrualCyclelengthID((short) 1);
        benMenstrualDetails.setMenstrualFlowDurationID((short) 1);
        benMenstrualDetails.setMenstrualProblemID("Menstrual Problem ID");
        benMenstrualDetails.setMenstrualProblemList(new ArrayList<>());
        benMenstrualDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benMenstrualDetails.setParkingPlaceID(1);
        benMenstrualDetails.setProblemName("Problem Name");
        benMenstrualDetails.setProcessed("Processed");
        benMenstrualDetails.setProviderServiceMapID(1);
        benMenstrualDetails.setRegularity("Regularity");
        benMenstrualDetails.setReservedForChange("Reserved For Change");
        benMenstrualDetails.setSyncedBy("Synced By");
        benMenstrualDetails.setSyncedDate(mock(Timestamp.class));
        benMenstrualDetails.setVanID(1);
        benMenstrualDetails.setVanSerialNo(1L);
        benMenstrualDetails.setVehicalNo("Vehical No");
        benMenstrualDetails.setVisitCode(1L);
        benMenstrualDetails.setlMPDate(mock(Timestamp.class));

        BenPersonalHabit benPersonalHabit = new BenPersonalHabit();
        benPersonalHabit.setAlcoholDuration(mock(Timestamp.class));
        benPersonalHabit.setAlcoholIntakeFrequency("Alcohol Intake Frequency");
        benPersonalHabit.setAlcoholIntakeStatus("Alcohol Intake Status");
        benPersonalHabit.setAlcoholList(new ArrayList<>());
        benPersonalHabit.setAlcoholType("Alcohol Type");
        benPersonalHabit.setAlcoholTypeID("Alcohol Type ID");
        benPersonalHabit.setAlcohol_use_duration(mock(Date.class));
        benPersonalHabit.setAllergicList(new ArrayList<>());
        benPersonalHabit.setAllergyStatus("Allergy Status");
        benPersonalHabit.setAvgAlcoholConsumption("Avg Alcohol Consumption");
        benPersonalHabit.setBenPersonalHabitID(1);
        benPersonalHabit.setBenVisitID(1L);
        benPersonalHabit.setBeneficiaryRegID(1L);
        benPersonalHabit.setCaptureDate(mock(Date.class));
        benPersonalHabit.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benPersonalHabit.setCreatedDate(mock(Timestamp.class));
        benPersonalHabit.setDeleted(true);
        benPersonalHabit.setDietaryType("Dietary Type");
        benPersonalHabit.setLastModDate(mock(Timestamp.class));
        benPersonalHabit.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benPersonalHabit.setNumberperDay((short) 1);
        benPersonalHabit.setNumberperWeek((short) 1);
        benPersonalHabit.setOtherAlcoholType("Other Alcohol Type");
        benPersonalHabit.setOtherTobaccoUseType("Other Tobacco Use Type");
        benPersonalHabit.setParkingPlaceID(1);
        benPersonalHabit.setPhysicalActivityType("Physical Activity Type");
        benPersonalHabit.setProcessed("Processed");
        benPersonalHabit.setProviderServiceMapID(1);
        benPersonalHabit.setReservedForChange("Reserved For Change");
        benPersonalHabit.setRiskySexualPracticeStatus("Risky Sexual Practice Status");
        benPersonalHabit.setRiskySexualPracticesStatus('A');
        benPersonalHabit.setSyncedBy("Synced By");
        benPersonalHabit.setSyncedDate(mock(Timestamp.class));
        benPersonalHabit.setTobaccoList(new ArrayList<>());
        benPersonalHabit.setTobaccoUseDuration(mock(Timestamp.class));
        benPersonalHabit.setTobaccoUseStatus("Tobacco Use Status");
        benPersonalHabit.setTobaccoUseType("Tobacco Use Type");
        benPersonalHabit.setTobaccoUseTypeID("Tobacco Use Type ID");
        benPersonalHabit.setTobacco_use_duration(mock(Date.class));
        benPersonalHabit.setVanID(1);
        benPersonalHabit.setVanSerialNo(1L);
        benPersonalHabit.setVehicalNo("Vehical No");
        benPersonalHabit.setVisitCode(1L);

        PhyGeneralExamination phyGeneralExamination = new PhyGeneralExamination();
        phyGeneralExamination.setBenVisitID(1L);
        phyGeneralExamination.setBeneficiaryRegID(1L);
        phyGeneralExamination.setBuiltAndAppearance("Built And Appearance");
        phyGeneralExamination.setClubbing("Clubbing");
        phyGeneralExamination.setCoherence("Coherence");
        phyGeneralExamination.setComfortness("Comfortness");
        phyGeneralExamination.setConsciousness("Consciousness");
        phyGeneralExamination.setCooperation("Cooperation");
        phyGeneralExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        phyGeneralExamination.setCreatedDate(mock(Timestamp.class));
        phyGeneralExamination.setCyanosis("Cyanosis");
        phyGeneralExamination.setDangerSigns("Danger Signs");
        phyGeneralExamination.setDeleted(true);
        phyGeneralExamination.setEdema("Edema");
        phyGeneralExamination.setEdemaType("Edema Type");
        phyGeneralExamination.setExtentOfEdema("Extent Of Edema");
        phyGeneralExamination.setFoetalMovements("Foetal Movements");
        phyGeneralExamination.setGait("Gait");
        phyGeneralExamination.setID(1L);
        phyGeneralExamination.setJaundice("Jaundice");
        phyGeneralExamination.setLastModDate(mock(Timestamp.class));
        phyGeneralExamination.setLymphadenopathy("Lymphadenopathy");
        phyGeneralExamination.setLymphnodesInvolved("Lymphnodes Involved");
        phyGeneralExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        phyGeneralExamination.setPallor("Pallor");
        phyGeneralExamination.setParkingPlaceID(1);
        phyGeneralExamination.setProcessed("Processed");
        phyGeneralExamination.setProviderServiceMapID(1);
        phyGeneralExamination.setQuickening("Quickening");
        phyGeneralExamination.setReservedForChange("Reserved For Change");
        phyGeneralExamination.setSyncedBy("Synced By");
        phyGeneralExamination.setSyncedDate(mock(Timestamp.class));
        phyGeneralExamination.setTypeOfDangerSign("Type Of Danger Sign");
        phyGeneralExamination.setTypeOfDangerSigns(new ArrayList<>());
        phyGeneralExamination.setTypeOfLymphadenopathy("Type Of Lymphadenopathy");
        phyGeneralExamination.setVanSerialNo(1L);
        phyGeneralExamination.setVehicalNo("Vehical No");
        phyGeneralExamination.setVisitCode(1L);

        PhyHeadToToeExamination phyHeadToToeExamination = new PhyHeadToToeExamination();
        phyHeadToToeExamination.setBenVisitID(1L);
        phyHeadToToeExamination.setBeneficiaryRegID(1L);
        phyHeadToToeExamination.setBreastAndNipples("Breast And Nipples");
        phyHeadToToeExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        phyHeadToToeExamination.setCreatedDate(mock(Timestamp.class));
        phyHeadToToeExamination.setDeleted(true);
        phyHeadToToeExamination.setEars("Ears");
        phyHeadToToeExamination.setEyes("Eyes");
        phyHeadToToeExamination.setHair("Hair");
        phyHeadToToeExamination.setHead("Head");
        phyHeadToToeExamination.setID(1L);
        phyHeadToToeExamination.setLastModDate(mock(Timestamp.class));
        phyHeadToToeExamination.setLowerLimbs("Lower Limbs");
        phyHeadToToeExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        phyHeadToToeExamination.setNails("Nails");
        phyHeadToToeExamination.setNipples("Nipples");
        phyHeadToToeExamination.setNose("Nose");
        phyHeadToToeExamination.setOralCavity("Oral Cavity");
        phyHeadToToeExamination.setParkingPlaceID(1);
        phyHeadToToeExamination.setProcessed("Processed");
        phyHeadToToeExamination.setProviderServiceMapID(1);
        phyHeadToToeExamination.setReservedForChange("Reserved For Change");
        phyHeadToToeExamination.setSkin("Skin");
        phyHeadToToeExamination.setSyncedBy("Synced By");
        phyHeadToToeExamination.setSyncedDate(mock(Timestamp.class));
        phyHeadToToeExamination.setThroat("Throat");
        phyHeadToToeExamination.setTrunk("Trunk");
        phyHeadToToeExamination.setUpperLimbs("Upper Limbs");
        phyHeadToToeExamination.setVanID(1);
        phyHeadToToeExamination.setVanSerialNo(1L);
        phyHeadToToeExamination.setVehicalNo("Vehical No");
        phyHeadToToeExamination.setVisitCode(1L);

        SysCardiovascularExamination sysCardiovascularExamination = new SysCardiovascularExamination();
        sysCardiovascularExamination.setAdditionalHeartSounds("Additional Heart Sounds");
        sysCardiovascularExamination.setApexbeatLocation("Apexbeat Location");
        sysCardiovascularExamination.setApexbeatType("Apexbeat Type");
        sysCardiovascularExamination.setBenVisitID(1L);
        sysCardiovascularExamination.setBeneficiaryRegID(1L);
        sysCardiovascularExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        sysCardiovascularExamination.setCreatedDate(mock(Timestamp.class));
        sysCardiovascularExamination.setDeleted(true);
        sysCardiovascularExamination.setFirstHeartSound_S1("First Heart Sound S1");
        sysCardiovascularExamination.setID(1L);
        sysCardiovascularExamination.setJugularVenousPulse_JVP("Jugular Venous Pulse JVP");
        sysCardiovascularExamination.setLastModDate(mock(Timestamp.class));
        sysCardiovascularExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        sysCardiovascularExamination.setMurmurs("Murmurs");
        sysCardiovascularExamination.setParkingPlaceID(1);
        sysCardiovascularExamination.setPericardialRub("Pericardial Rub");
        sysCardiovascularExamination.setProcessed("Processed");
        sysCardiovascularExamination.setProviderServiceMapID(1);
        sysCardiovascularExamination.setReservedForChange("Reserved For Change");
        sysCardiovascularExamination.setSecondHeartSound_S2("Second Heart Sound S2");
        sysCardiovascularExamination.setSyncedBy("Synced By");
        sysCardiovascularExamination.setSyncedDate(mock(Timestamp.class));
        sysCardiovascularExamination.setVanSerialNo(1L);
        sysCardiovascularExamination.setVehicalNo("Vehical No");
        sysCardiovascularExamination.setVisitCode(1L);

        SysCentralNervousExamination sysCentralNervousExamination = new SysCentralNervousExamination();
        sysCentralNervousExamination.setAutonomicSystem("Autonomic System");
        sysCentralNervousExamination.setBenVisitID(1L);
        sysCentralNervousExamination.setBeneficiaryRegID(1L);
        sysCentralNervousExamination.setCerebellarSigns("Cerebellar Signs");
        sysCentralNervousExamination.setCranialNervesExamination("Cranial Nerves Examination");
        sysCentralNervousExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        sysCentralNervousExamination.setCreatedDate(mock(Timestamp.class));
        sysCentralNervousExamination.setDeleted(true);
        sysCentralNervousExamination.setHandedness("Handedness");
        sysCentralNervousExamination.setID(1L);
        sysCentralNervousExamination.setLastModDate(mock(Timestamp.class));
        sysCentralNervousExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        sysCentralNervousExamination.setMotorSystem("Motor System");
        sysCentralNervousExamination.setParkingPlaceID(1);
        sysCentralNervousExamination.setProcessed("Processed");
        sysCentralNervousExamination.setProviderServiceMapID(1);
        sysCentralNervousExamination.setReservedForChange("Reserved For Change");
        sysCentralNervousExamination.setSensorySystem("Sensory System");
        sysCentralNervousExamination.setSignsOfMeningealIrritation("Signs Of Meningeal Irritation");
        sysCentralNervousExamination.setSkull("Skull");
        sysCentralNervousExamination.setSyncedBy("Synced By");
        sysCentralNervousExamination.setSyncedDate(mock(Timestamp.class));
        sysCentralNervousExamination.setVanSerialNo(1L);
        sysCentralNervousExamination.setVehicalNo("Vehical No");
        sysCentralNervousExamination.setVisitCode(1L);

        SysGenitourinarySystemExamination sysGenitourinarySystemExamination = new SysGenitourinarySystemExamination();
        sysGenitourinarySystemExamination.setBenVisitID(1L);
        sysGenitourinarySystemExamination.setBeneficiaryRegID(1L);
        sysGenitourinarySystemExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        sysGenitourinarySystemExamination.setCreatedDate(mock(Timestamp.class));
        sysGenitourinarySystemExamination.setDeleted(true);
        sysGenitourinarySystemExamination.setExternalGenitalia("External Genitalia");
        sysGenitourinarySystemExamination.setID(1L);
        sysGenitourinarySystemExamination.setLastModDate(mock(Timestamp.class));
        sysGenitourinarySystemExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        sysGenitourinarySystemExamination.setParkingPlaceID(1);
        sysGenitourinarySystemExamination.setProcessed("Processed");
        sysGenitourinarySystemExamination.setProviderServiceMapID(1);
        sysGenitourinarySystemExamination.setRenalAngle("Renal Angle");
        sysGenitourinarySystemExamination.setReservedForChange("Reserved For Change");
        sysGenitourinarySystemExamination.setSuprapubicRegion("us-east-2");
        sysGenitourinarySystemExamination.setSyncedBy("Synced By");
        sysGenitourinarySystemExamination.setSyncedDate(mock(Timestamp.class));
        sysGenitourinarySystemExamination.setVanSerialNo(1L);
        sysGenitourinarySystemExamination.setVehicalNo("Vehical No");
        sysGenitourinarySystemExamination.setVisitCode(1L);

        SysMusculoskeletalSystemExamination sysMusculoskeletalSystemExamination = new SysMusculoskeletalSystemExamination();
        sysMusculoskeletalSystemExamination.setBenVisitID(1L);
        sysMusculoskeletalSystemExamination.setBeneficiaryRegID(1L);
        sysMusculoskeletalSystemExamination.setChestWall("Chest Wall");
        sysMusculoskeletalSystemExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        sysMusculoskeletalSystemExamination.setCreatedDate(mock(Timestamp.class));
        sysMusculoskeletalSystemExamination.setDeleted(true);
        sysMusculoskeletalSystemExamination.setID(1L);
        sysMusculoskeletalSystemExamination.setJoint_Abnormality("Joint Abnormality");
        sysMusculoskeletalSystemExamination.setJoint_Laterality("Joint Laterality");
        sysMusculoskeletalSystemExamination.setJoint_TypeOfJoint("Joint Type Of Joint");
        sysMusculoskeletalSystemExamination.setLastModDate(mock(Timestamp.class));
        sysMusculoskeletalSystemExamination.setLowerLimb_Abnormality("Lower Limb Abnormality");
        sysMusculoskeletalSystemExamination.setLowerLimb_Laterality("Lower Limb Laterality");
        sysMusculoskeletalSystemExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        sysMusculoskeletalSystemExamination.setParkingPlaceID(1);
        sysMusculoskeletalSystemExamination.setProcessed("Processed");
        sysMusculoskeletalSystemExamination.setProviderServiceMapID(1);
        sysMusculoskeletalSystemExamination.setReservedForChange("Reserved For Change");
        sysMusculoskeletalSystemExamination.setSpine("Spine");
        sysMusculoskeletalSystemExamination.setSyncedBy("Synced By");
        sysMusculoskeletalSystemExamination.setSyncedDate(mock(Timestamp.class));
        sysMusculoskeletalSystemExamination.setUpperLimb_Abnormality("Upper Limb Abnormality");
        sysMusculoskeletalSystemExamination.setUpperLimb_Laterality("Upper Limb Laterality");
        sysMusculoskeletalSystemExamination.setVanSerialNo(1L);
        sysMusculoskeletalSystemExamination.setVehicalNo("Vehical No");
        sysMusculoskeletalSystemExamination.setVisitCode(1L);

        SysRespiratoryExamination sysRespiratoryExamination = new SysRespiratoryExamination();
        sysRespiratoryExamination.setAuscultation("Auscultation");
        sysRespiratoryExamination.setAuscultation_BreathSounds("Auscultation Breath Sounds");
        sysRespiratoryExamination.setAuscultation_ConductedSounds("Auscultation Conducted Sounds");
        sysRespiratoryExamination.setAuscultation_Crepitations("Auscultation Crepitations");
        sysRespiratoryExamination.setAuscultation_PleuralRub("Auscultation Pleural Rub");
        sysRespiratoryExamination.setAuscultation_Stridor("Auscultation Stridor");
        sysRespiratoryExamination.setAuscultation_Wheezing("Auscultation Wheezing");
        sysRespiratoryExamination.setBenVisitID(1L);
        sysRespiratoryExamination.setBeneficiaryRegID(1L);
        sysRespiratoryExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        sysRespiratoryExamination.setCreatedDate(mock(Timestamp.class));
        sysRespiratoryExamination.setDeleted(true);
        sysRespiratoryExamination.setID(1L);
        sysRespiratoryExamination.setInspection("Inspection");
        sysRespiratoryExamination.setLastModDate(mock(Timestamp.class));
        sysRespiratoryExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        sysRespiratoryExamination.setPalpation("Palpation");
        sysRespiratoryExamination.setParkingPlaceID(1);
        sysRespiratoryExamination.setPercussion("Percussion");
        sysRespiratoryExamination.setProcessed("Processed");
        sysRespiratoryExamination.setProviderServiceMapID(1);
        sysRespiratoryExamination.setReservedForChange("Reserved For Change");
        sysRespiratoryExamination.setSignsOfRespiratoryDistress("Signs Of Respiratory Distress");
        sysRespiratoryExamination.setSyncedBy("Synced By");
        sysRespiratoryExamination.setSyncedDate(mock(Timestamp.class));
        sysRespiratoryExamination.setTrachea("Trachea");
        sysRespiratoryExamination.setVanSerialNo(1L);
        sysRespiratoryExamination.setVehicalNo("Vehical No");
        sysRespiratoryExamination.setVisitCode(1L);

        WrapperChildOptionalVaccineDetail wrapperChildOptionalVaccineDetail = new WrapperChildOptionalVaccineDetail();
        wrapperChildOptionalVaccineDetail.setBenVisitID(1L);
        wrapperChildOptionalVaccineDetail.setBeneficiaryRegID(1L);
        wrapperChildOptionalVaccineDetail.setChildOptionalVaccineList(new ArrayList<>());
        wrapperChildOptionalVaccineDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        wrapperChildOptionalVaccineDetail.setParkingPlaceID(1);
        wrapperChildOptionalVaccineDetail.setProviderServiceMapID(1);
        wrapperChildOptionalVaccineDetail.setVanID(1);
        wrapperChildOptionalVaccineDetail.setVisitCode(1L);

        WrapperComorbidCondDetails wrapperComorbidCondDetails = new WrapperComorbidCondDetails();
        wrapperComorbidCondDetails.setBenVisitID(1L);
        wrapperComorbidCondDetails.setBeneficiaryRegID(1L);
        wrapperComorbidCondDetails.setComorbidityConcurrentConditionsList(new ArrayList<>());
        wrapperComorbidCondDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        wrapperComorbidCondDetails.setParkingPlaceID(1);
        wrapperComorbidCondDetails.setProviderServiceMapID(1);
        wrapperComorbidCondDetails.setVanID(1);
        wrapperComorbidCondDetails.setVisitCode(1L);

        WrapperFemaleObstetricHistory wrapperFemaleObstetricHistory = new WrapperFemaleObstetricHistory();
        wrapperFemaleObstetricHistory.setBenVisitID(1L);
        wrapperFemaleObstetricHistory.setBeneficiaryRegID(1L);
        wrapperFemaleObstetricHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        wrapperFemaleObstetricHistory.setFemaleObstetricHistoryList(new ArrayList<>());
        wrapperFemaleObstetricHistory.setParkingPlaceID(1);
        wrapperFemaleObstetricHistory.setProviderServiceMapID(1);
        wrapperFemaleObstetricHistory.setTotalNoOfPreg((short) 1);
        wrapperFemaleObstetricHistory.setVanID(1);
        wrapperFemaleObstetricHistory.setVisitCode(1L);

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

        WrapperMedicationHistory wrapperMedicationHistory = new WrapperMedicationHistory();
        wrapperMedicationHistory.setBenVisitID(1L);
        wrapperMedicationHistory.setBeneficiaryRegID(1L);
        wrapperMedicationHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        wrapperMedicationHistory.setMedicationHistoryList(new ArrayList<>());
        wrapperMedicationHistory.setParkingPlaceID(1);
        wrapperMedicationHistory.setProviderServiceMapID(1);
        wrapperMedicationHistory.setVanID(1);
        wrapperMedicationHistory.setVisitCode(1L);
        when(commonNurseServiceImpl.getFamilyHistory(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(benFamilyHistory);
        when(commonNurseServiceImpl.getPastHistoryData(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(benMedHistory);
        when(commonNurseServiceImpl.getMenstrualHistory(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(benMenstrualDetails);
        when(commonNurseServiceImpl.getPersonalHistory(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(benPersonalHabit);
        when(commonNurseServiceImpl.getGeneralExaminationData(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(phyGeneralExamination);
        when(commonNurseServiceImpl.getHeadToToeExaminationData(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(phyHeadToToeExamination);
        when(commonNurseServiceImpl.getCardiovascularExamination(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(sysCardiovascularExamination);
        when(commonNurseServiceImpl.getSysCentralNervousExamination(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(sysCentralNervousExamination);
        when(commonNurseServiceImpl.getGenitourinaryExamination(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(sysGenitourinarySystemExamination);
        when(commonNurseServiceImpl.getMusculoskeletalExamination(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(sysMusculoskeletalSystemExamination);
        when(commonNurseServiceImpl.getRespiratoryExamination(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(sysRespiratoryExamination);
        when(commonNurseServiceImpl.getChildOptionalVaccineHistory(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(wrapperChildOptionalVaccineDetail);
        when(commonNurseServiceImpl.getComorbidityConditionsHistory(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(wrapperComorbidCondDetails);
        when(commonNurseServiceImpl.getFemaleObstetricHistory(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(wrapperFemaleObstetricHistory);
        when(commonNurseServiceImpl.getImmunizationHistory(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(wrapperImmunizationHistory);
        when(commonNurseServiceImpl.getMedicationHistory(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(wrapperMedicationHistory);
        when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Beneficiary Physical Anthropometry Details");
        when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Beneficiary Physical Vital Details");

        // Act
        aNCServiceImpl.getBenANCNurseData(1L, 1L);
    }

    @Test
    void testGetBenCaseRecordFromDoctorANC() {
        // Arrange
        when(commonDoctorServiceImpl.getFindingsDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenThrow(new RuntimeException("findings"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> aNCServiceImpl.getBenCaseRecordFromDoctorANC(1L, 1L));
        verify(commonDoctorServiceImpl).getFindingsDetails(isA(Long.class), isA(Long.class));
    }

    @Test
    void testGetBenCaseRecordFromDoctorANC2() {
        // Arrange
        when(aNCDoctorServiceImpl.getANCDiagnosisDetails(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(null);
        when(commonDoctorServiceImpl.getFoetalMonitorData(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Foetal Monitor Data");
        when(commonDoctorServiceImpl.getInvestigationDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Investigation Details");
        when(commonDoctorServiceImpl.getPrescribedDrugs(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Prescribed Drugs");
        when(commonDoctorServiceImpl.getReferralDetails(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Boolean>any()))
                .thenReturn("Referral Details");
        when(commonDoctorServiceImpl.getFindingsDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Findings Details");
        when(commonNurseServiceImpl.getGraphicalTrendData(Mockito.<Long>any(), Mockito.<String>any()))
                .thenReturn(new HashMap<>());
        when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Last 3 Archived Test Visit List");
        when(labTechnicianServiceImpl.getLabResultDataForBen(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        // Act
        String actualBenCaseRecordFromDoctorANC = aNCServiceImpl.getBenCaseRecordFromDoctorANC(1L, 1L);

        // Assert
        verify(aNCDoctorServiceImpl).getANCDiagnosisDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getFindingsDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getFoetalMonitorData(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getInvestigationDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getPrescribedDrugs(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getReferralDetails(isA(Long.class), isA(Long.class), isA(Boolean.class));
        verify(commonNurseServiceImpl).getGraphicalTrendData(isA(Long.class), eq("anc"));
        verify(labTechnicianServiceImpl).getLabResultDataForBen(isA(Long.class), isA(Long.class));
        verify(labTechnicianServiceImpl).getLast_3_ArchivedTestVisitList(isA(Long.class), isA(Long.class));
        assertEquals("{Refer=Referral Details, prescription=Prescribed Drugs, findings=Findings Details, LabReport=[],"
                + " diagnosis=null, investigation=Investigation Details, ArchivedVisitcodeForLabResult=Last 3 Archived"
                + " Test Visit List, fetosenseData=Foetal Monitor Data, GraphData={}}", actualBenCaseRecordFromDoctorANC);
    }

    @Test
    void testGetBenCaseRecordFromDoctorANC3() {
        // Arrange
        when(aNCDoctorServiceImpl.getANCDiagnosisDetails(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(null);
        when(commonDoctorServiceImpl.getFoetalMonitorData(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Foetal Monitor Data");
        when(commonDoctorServiceImpl.getInvestigationDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Investigation Details");
        when(commonDoctorServiceImpl.getPrescribedDrugs(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Prescribed Drugs");
        when(commonDoctorServiceImpl.getReferralDetails(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Boolean>any()))
                .thenReturn("Referral Details");
        when(commonDoctorServiceImpl.getFindingsDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Findings Details");
        when(labTechnicianServiceImpl.getLabResultDataForBen(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenThrow(new RuntimeException("findings"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> aNCServiceImpl.getBenCaseRecordFromDoctorANC(1L, 1L));
        verify(aNCDoctorServiceImpl).getANCDiagnosisDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getFindingsDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getFoetalMonitorData(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getInvestigationDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getPrescribedDrugs(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getReferralDetails(isA(Long.class), isA(Long.class), isA(Boolean.class));
        verify(labTechnicianServiceImpl).getLabResultDataForBen(isA(Long.class), isA(Long.class));
    }

    @Test
    void testGetBenCaseRecordFromDoctorANC4() {
        // Arrange
        when(aNCDoctorServiceImpl.getANCDiagnosisDetails(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn("");
        when(commonDoctorServiceImpl.getFoetalMonitorData(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Foetal Monitor Data");
        when(commonDoctorServiceImpl.getInvestigationDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Investigation Details");
        when(commonDoctorServiceImpl.getPrescribedDrugs(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Prescribed Drugs");
        when(commonDoctorServiceImpl.getReferralDetails(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Boolean>any()))
                .thenReturn("Referral Details");
        when(commonDoctorServiceImpl.getFindingsDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Findings Details");
        when(commonNurseServiceImpl.getGraphicalTrendData(Mockito.<Long>any(), Mockito.<String>any()))
                .thenReturn(new HashMap<>());
        when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Last 3 Archived Test Visit List");
        when(labTechnicianServiceImpl.getLabResultDataForBen(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        // Act
        String actualBenCaseRecordFromDoctorANC = aNCServiceImpl.getBenCaseRecordFromDoctorANC(1L, 1L);

        // Assert
        verify(aNCDoctorServiceImpl).getANCDiagnosisDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getFindingsDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getFoetalMonitorData(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getInvestigationDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getPrescribedDrugs(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getReferralDetails(isA(Long.class), isA(Long.class), isA(Boolean.class));
        verify(commonNurseServiceImpl).getGraphicalTrendData(isA(Long.class), eq("anc"));
        verify(labTechnicianServiceImpl).getLabResultDataForBen(isA(Long.class), isA(Long.class));
        verify(labTechnicianServiceImpl).getLast_3_ArchivedTestVisitList(isA(Long.class), isA(Long.class));
        assertEquals("{Refer=Referral Details, prescription=Prescribed Drugs, findings=Findings Details, LabReport=[],"
                + " diagnosis=, investigation=Investigation Details, ArchivedVisitcodeForLabResult=Last 3 Archived Test"
                + " Visit List, fetosenseData=Foetal Monitor Data, GraphData={}}", actualBenCaseRecordFromDoctorANC);
    }

    @Test
    void testGetBenCaseRecordFromDoctorANC5() {
        // Arrange
        when(aNCDoctorServiceImpl.getANCDiagnosisDetails(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(null);
        when(commonDoctorServiceImpl.getFoetalMonitorData(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Foetal Monitor Data");
        when(commonDoctorServiceImpl.getInvestigationDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Investigation Details");
        when(commonDoctorServiceImpl.getPrescribedDrugs(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Prescribed Drugs");
        when(commonDoctorServiceImpl.getReferralDetails(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Boolean>any()))
                .thenReturn("Referral Details");
        when(commonDoctorServiceImpl.getFindingsDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Findings Details");

        HashMap<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("findings", "42");
        when(commonNurseServiceImpl.getGraphicalTrendData(Mockito.<Long>any(), Mockito.<String>any()))
                .thenReturn(stringObjectMap);
        when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Last 3 Archived Test Visit List");
        when(labTechnicianServiceImpl.getLabResultDataForBen(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        // Act
        String actualBenCaseRecordFromDoctorANC = aNCServiceImpl.getBenCaseRecordFromDoctorANC(1L, 1L);

        // Assert
        verify(aNCDoctorServiceImpl).getANCDiagnosisDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getFindingsDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getFoetalMonitorData(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getInvestigationDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getPrescribedDrugs(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getReferralDetails(isA(Long.class), isA(Long.class), isA(Boolean.class));
        verify(commonNurseServiceImpl).getGraphicalTrendData(isA(Long.class), eq("anc"));
        verify(labTechnicianServiceImpl).getLabResultDataForBen(isA(Long.class), isA(Long.class));
        verify(labTechnicianServiceImpl).getLast_3_ArchivedTestVisitList(isA(Long.class), isA(Long.class));
        assertEquals(
                "{Refer=Referral Details, prescription=Prescribed Drugs, findings=Findings Details, LabReport=[],"
                        + " diagnosis=null, investigation=Investigation Details, ArchivedVisitcodeForLabResult=Last 3 Archived"
                        + " Test Visit List, fetosenseData=Foetal Monitor Data, GraphData={\"findings\":\"42\"}}",
                actualBenCaseRecordFromDoctorANC);
    }

    @Test
    void testGetBenCaseRecordFromDoctorANC6() {
        // Arrange
        when(aNCDoctorServiceImpl.getANCDiagnosisDetails(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(null);
        when(commonDoctorServiceImpl.getFoetalMonitorData(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Foetal Monitor Data");
        when(commonDoctorServiceImpl.getInvestigationDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Investigation Details");
        when(commonDoctorServiceImpl.getPrescribedDrugs(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Prescribed Drugs");
        when(commonDoctorServiceImpl.getReferralDetails(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Boolean>any()))
                .thenReturn("Referral Details");
        when(commonDoctorServiceImpl.getFindingsDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Findings Details");

        HashMap<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("diagnosis", "42");
        stringObjectMap.put("findings", "42");
        when(commonNurseServiceImpl.getGraphicalTrendData(Mockito.<Long>any(), Mockito.<String>any()))
                .thenReturn(stringObjectMap);
        when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Last 3 Archived Test Visit List");
        when(labTechnicianServiceImpl.getLabResultDataForBen(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        // Act
        String actualBenCaseRecordFromDoctorANC = aNCServiceImpl.getBenCaseRecordFromDoctorANC(1L, 1L);

        // Assert
        verify(aNCDoctorServiceImpl).getANCDiagnosisDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getFindingsDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getFoetalMonitorData(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getInvestigationDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getPrescribedDrugs(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getReferralDetails(isA(Long.class), isA(Long.class), isA(Boolean.class));
        verify(commonNurseServiceImpl).getGraphicalTrendData(isA(Long.class), eq("anc"));
        verify(labTechnicianServiceImpl).getLabResultDataForBen(isA(Long.class), isA(Long.class));
        verify(labTechnicianServiceImpl).getLast_3_ArchivedTestVisitList(isA(Long.class), isA(Long.class));
        assertEquals("{Refer=Referral Details, prescription=Prescribed Drugs, findings=Findings Details, LabReport=[],"
                        + " diagnosis=null, investigation=Investigation Details, ArchivedVisitcodeForLabResult=Last 3 Archived"
                        + " Test Visit List, fetosenseData=Foetal Monitor Data, GraphData={\"findings\":\"42\",\"diagnosis\":\"42\"}}",
                actualBenCaseRecordFromDoctorANC);
    }

    @Test
    void testUpdateANCDoctorData() throws Exception {
        // Arrange
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
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(1);

        // Act
        Long actualUpdateANCDoctorDataResult = aNCServiceImpl.updateANCDoctorData(new JsonObject(), "JaneDoe");

        // Assert
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataUpdate(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
        assertEquals(1L, actualUpdateANCDoctorDataResult.longValue());
    }

    @Test
    void testUpdateANCDoctorData2() throws Exception {
        // Arrange
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
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any()))
                .thenThrow(new Exception("investigation"));

        // Act and Assert
        assertThrows(Exception.class, () -> aNCServiceImpl.updateANCDoctorData(new JsonObject(), "JaneDoe"));
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataUpdate(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
    }

    @Test
    void testUpdateANCDoctorData3() throws Exception {
        // Arrange
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
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(1);

        // Act
        Long actualUpdateANCDoctorDataResult = aNCServiceImpl.updateANCDoctorData(new JsonObject(), "JaneDoe");

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
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
        verify(sMSGatewayServiceImpl).smsSenderGateway(eq("schedule"), isNull(), isA(Integer.class), isA(Long.class),
                isNull(), isNull(), eq("Mock for Timestamp, hashCode: 67323228"), isNull(), eq("JaneDoe"));
        assertEquals(1L, actualUpdateANCDoctorDataResult.longValue());
    }

    @Test
    void testUpdateANCDoctorData4() throws Exception {
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
        when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
                Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(0);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> aNCServiceImpl.updateANCDoctorData(new JsonObject(), "JaneDoe"));
        verify(teleconsultationRequestOBJ).setAllocationDate(isA(Timestamp.class));
        verify(teleconsultationRequestOBJ).setFromTime(eq("jane.doe@example.org"));
        verify(teleconsultationRequestOBJ).setSpecializationID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setTmRequestID(isA(Long.class));
        verify(teleconsultationRequestOBJ).setToTime(eq("To Time"));
        verify(teleconsultationRequestOBJ).setUserID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setWalkIn(isA(Boolean.class));
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataUpdate(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
    }

    @Test
    void testUpdateANCDoctorData5() throws Exception {
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
        Long actualUpdateANCDoctorDataResult = aNCServiceImpl.updateANCDoctorData(null, "JaneDoe");

        // Assert
        verify(teleconsultationRequestOBJ).setAllocationDate(isA(Timestamp.class));
        verify(teleconsultationRequestOBJ).setFromTime(eq("jane.doe@example.org"));
        verify(teleconsultationRequestOBJ).setSpecializationID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setTmRequestID(isA(Long.class));
        verify(teleconsultationRequestOBJ).setToTime(eq("To Time"));
        verify(teleconsultationRequestOBJ).setUserID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setWalkIn(isA(Boolean.class));
        assertNull(actualUpdateANCDoctorDataResult);
    }

    @Test
    void testGetHRPStatus() throws Exception {
        // Arrange, Act and Assert
        assertEquals("{\"isHRP\":false,\"reasonForHrp\":[]}", aNCServiceImpl.getHRPStatus(hrpStatusAndReasonsRequestModel));
    }

    @Test
    void testGetHRPInformation() throws IEMRException {
        // Arrange
        SysObstetricExamination sysObstetricExamination = new SysObstetricExamination();
        sysObstetricExamination.setAbdominalScars("Abdominal Scars");
        sysObstetricExamination.setBenVisitID(1L);
        sysObstetricExamination.setBeneficiaryRegID(1L);
        sysObstetricExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        sysObstetricExamination.setCreatedDate(mock(Timestamp.class));
        sysObstetricExamination.setDeleted(true);
        sysObstetricExamination.setFetalHeartRate_BeatsPerMinute("Fetal Heart Rate Beats Per Minute");
        sysObstetricExamination.setFetalHeartSounds("Fetal Heart Sounds");
        sysObstetricExamination.setFetalMovements("Fetal Movements");
        sysObstetricExamination.setFetalPositionOrLie("Fetal Position Or Lie");
        sysObstetricExamination.setFetalPresentation("Fetal Presentation");
        sysObstetricExamination.setFundalHeight("Fundal Height");
        sysObstetricExamination.setID(1L);
        sysObstetricExamination.setIsHRP(true);
        sysObstetricExamination.setLastModDate(mock(Timestamp.class));
        sysObstetricExamination.setLowLyingPlacenta(true);
        sysObstetricExamination.setMalPresentation(true);
        sysObstetricExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        sysObstetricExamination.setParkingPlaceID(1);
        sysObstetricExamination.setProcessed("Processed");
        sysObstetricExamination.setProviderServiceMapID(1);
        sysObstetricExamination.setReasonsForHRP(new String[]{"Just cause"});
        sysObstetricExamination.setReasonsForHRPDB("Just cause");
        sysObstetricExamination.setReservedForChange("Reserved For Change");
        sysObstetricExamination.setSfh(10.0d);
        sysObstetricExamination.setSyncedBy("Synced By");
        sysObstetricExamination.setSyncedDate(mock(Timestamp.class));
        sysObstetricExamination.setVanID(1);
        sysObstetricExamination.setVanSerialNo(1L);
        sysObstetricExamination.setVehicalNo("Vehical No");
        sysObstetricExamination.setVertebralDeformity(true);
        sysObstetricExamination.setVisitCode(1L);
        sysObstetricExamination.setfHAndPOA_Interpretation("F HAnd POA Interpretation");
        sysObstetricExamination.setfHAndPOA_Status("F HAnd POA Status");
        when(sysObstetricExaminationRepo.findFirstByBeneficiaryRegIDOrderByIDDesc(Mockito.<Long>any()))
                .thenReturn(sysObstetricExamination);

        // Act
        SysObstetricExamination actualHRPInformation = aNCServiceImpl.getHRPInformation(1L);

        // Assert
        verify(sysObstetricExaminationRepo).findFirstByBeneficiaryRegIDOrderByIDDesc(isA(Long.class));
        assertSame(sysObstetricExamination, actualHRPInformation);
        assertArrayEquals(new String[]{"Just cause"}, actualHRPInformation.getReasonsForHRP());
    }

    @Test
    void testGetHRPInformation2() throws IEMRException {
        // Arrange
        SysObstetricExamination sysObstetricExamination = mock(SysObstetricExamination.class);
        when(sysObstetricExamination.getReasonsForHRPDB()).thenReturn("Just cause");
        doNothing().when(sysObstetricExamination).setAbdominalScars(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setBenVisitID(Mockito.<Long>any());
        doNothing().when(sysObstetricExamination).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(sysObstetricExamination).setCreatedBy(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(sysObstetricExamination).setDeleted(Mockito.<Boolean>any());
        doNothing().when(sysObstetricExamination).setFetalHeartRate_BeatsPerMinute(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setFetalHeartSounds(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setFetalMovements(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setFetalPositionOrLie(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setFetalPresentation(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setFundalHeight(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setID(Mockito.<Long>any());
        doNothing().when(sysObstetricExamination).setIsHRP(Mockito.<Boolean>any());
        doNothing().when(sysObstetricExamination).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(sysObstetricExamination).setLowLyingPlacenta(Mockito.<Boolean>any());
        doNothing().when(sysObstetricExamination).setMalPresentation(Mockito.<Boolean>any());
        doNothing().when(sysObstetricExamination).setModifiedBy(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(sysObstetricExamination).setProcessed(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(sysObstetricExamination).setReasonsForHRP(Mockito.<String[]>any());
        doNothing().when(sysObstetricExamination).setReasonsForHRPDB(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setReservedForChange(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setSfh(Mockito.<Double>any());
        doNothing().when(sysObstetricExamination).setSyncedBy(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(sysObstetricExamination).setVanID(Mockito.<Integer>any());
        doNothing().when(sysObstetricExamination).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(sysObstetricExamination).setVehicalNo(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setVertebralDeformity(Mockito.<Boolean>any());
        doNothing().when(sysObstetricExamination).setVisitCode(Mockito.<Long>any());
        doNothing().when(sysObstetricExamination).setfHAndPOA_Interpretation(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setfHAndPOA_Status(Mockito.<String>any());
        sysObstetricExamination.setAbdominalScars("Abdominal Scars");
        sysObstetricExamination.setBenVisitID(1L);
        sysObstetricExamination.setBeneficiaryRegID(1L);
        sysObstetricExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        sysObstetricExamination.setCreatedDate(mock(Timestamp.class));
        sysObstetricExamination.setDeleted(true);
        sysObstetricExamination.setFetalHeartRate_BeatsPerMinute("Fetal Heart Rate Beats Per Minute");
        sysObstetricExamination.setFetalHeartSounds("Fetal Heart Sounds");
        sysObstetricExamination.setFetalMovements("Fetal Movements");
        sysObstetricExamination.setFetalPositionOrLie("Fetal Position Or Lie");
        sysObstetricExamination.setFetalPresentation("Fetal Presentation");
        sysObstetricExamination.setFundalHeight("Fundal Height");
        sysObstetricExamination.setID(1L);
        sysObstetricExamination.setIsHRP(true);
        sysObstetricExamination.setLastModDate(mock(Timestamp.class));
        sysObstetricExamination.setLowLyingPlacenta(true);
        sysObstetricExamination.setMalPresentation(true);
        sysObstetricExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        sysObstetricExamination.setParkingPlaceID(1);
        sysObstetricExamination.setProcessed("Processed");
        sysObstetricExamination.setProviderServiceMapID(1);
        sysObstetricExamination.setReasonsForHRP(new String[]{"Just cause"});
        sysObstetricExamination.setReasonsForHRPDB("Just cause");
        sysObstetricExamination.setReservedForChange("Reserved For Change");
        sysObstetricExamination.setSfh(10.0d);
        sysObstetricExamination.setSyncedBy("Synced By");
        sysObstetricExamination.setSyncedDate(mock(Timestamp.class));
        sysObstetricExamination.setVanID(1);
        sysObstetricExamination.setVanSerialNo(1L);
        sysObstetricExamination.setVehicalNo("Vehical No");
        sysObstetricExamination.setVertebralDeformity(true);
        sysObstetricExamination.setVisitCode(1L);
        sysObstetricExamination.setfHAndPOA_Interpretation("F HAnd POA Interpretation");
        sysObstetricExamination.setfHAndPOA_Status("F HAnd POA Status");
        when(sysObstetricExaminationRepo.findFirstByBeneficiaryRegIDOrderByIDDesc(Mockito.<Long>any()))
                .thenReturn(sysObstetricExamination);

        // Act
        aNCServiceImpl.getHRPInformation(1L);

        // Assert
        verify(sysObstetricExamination, atLeast(1)).getReasonsForHRPDB();
        verify(sysObstetricExamination).setAbdominalScars(eq("Abdominal Scars"));
        verify(sysObstetricExamination).setBenVisitID(isA(Long.class));
        verify(sysObstetricExamination).setBeneficiaryRegID(isA(Long.class));
        verify(sysObstetricExamination).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(sysObstetricExamination).setCreatedDate(isA(Timestamp.class));
        verify(sysObstetricExamination).setDeleted(isA(Boolean.class));
        verify(sysObstetricExamination).setFetalHeartRate_BeatsPerMinute(eq("Fetal Heart Rate Beats Per Minute"));
        verify(sysObstetricExamination).setFetalHeartSounds(eq("Fetal Heart Sounds"));
        verify(sysObstetricExamination).setFetalMovements(eq("Fetal Movements"));
        verify(sysObstetricExamination).setFetalPositionOrLie(eq("Fetal Position Or Lie"));
        verify(sysObstetricExamination).setFetalPresentation(eq("Fetal Presentation"));
        verify(sysObstetricExamination).setFundalHeight(eq("Fundal Height"));
        verify(sysObstetricExamination).setID(isA(Long.class));
        verify(sysObstetricExamination).setIsHRP(isA(Boolean.class));
        verify(sysObstetricExamination).setLastModDate(isA(Timestamp.class));
        verify(sysObstetricExamination).setLowLyingPlacenta(isA(Boolean.class));
        verify(sysObstetricExamination).setMalPresentation(isA(Boolean.class));
        verify(sysObstetricExamination).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(sysObstetricExamination).setParkingPlaceID(isA(Integer.class));
        verify(sysObstetricExamination).setProcessed(eq("Processed"));
        verify(sysObstetricExamination).setProviderServiceMapID(isA(Integer.class));
        verify(sysObstetricExamination, atLeast(1)).setReasonsForHRP(Mockito.<String[]>any());
        verify(sysObstetricExamination).setReasonsForHRPDB(eq("Just cause"));
        verify(sysObstetricExamination).setReservedForChange(eq("Reserved For Change"));
        verify(sysObstetricExamination).setSfh(isA(Double.class));
        verify(sysObstetricExamination).setSyncedBy(eq("Synced By"));
        verify(sysObstetricExamination).setSyncedDate(isA(Timestamp.class));
        verify(sysObstetricExamination).setVanID(isA(Integer.class));
        verify(sysObstetricExamination).setVanSerialNo(isA(Long.class));
        verify(sysObstetricExamination).setVehicalNo(eq("Vehical No"));
        verify(sysObstetricExamination).setVertebralDeformity(isA(Boolean.class));
        verify(sysObstetricExamination).setVisitCode(isA(Long.class));
        verify(sysObstetricExamination).setfHAndPOA_Interpretation(eq("F HAnd POA Interpretation"));
        verify(sysObstetricExamination).setfHAndPOA_Status(eq("F HAnd POA Status"));
        verify(sysObstetricExaminationRepo).findFirstByBeneficiaryRegIDOrderByIDDesc(isA(Long.class));
    }

    @Test
    void testGetHRPInformation3() throws IEMRException {
        // Arrange
        SysObstetricExamination sysObstetricExamination = mock(SysObstetricExamination.class);
        when(sysObstetricExamination.getReasonsForHRPDB()).thenReturn(null);
        doNothing().when(sysObstetricExamination).setAbdominalScars(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setBenVisitID(Mockito.<Long>any());
        doNothing().when(sysObstetricExamination).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(sysObstetricExamination).setCreatedBy(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(sysObstetricExamination).setDeleted(Mockito.<Boolean>any());
        doNothing().when(sysObstetricExamination).setFetalHeartRate_BeatsPerMinute(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setFetalHeartSounds(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setFetalMovements(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setFetalPositionOrLie(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setFetalPresentation(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setFundalHeight(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setID(Mockito.<Long>any());
        doNothing().when(sysObstetricExamination).setIsHRP(Mockito.<Boolean>any());
        doNothing().when(sysObstetricExamination).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(sysObstetricExamination).setLowLyingPlacenta(Mockito.<Boolean>any());
        doNothing().when(sysObstetricExamination).setMalPresentation(Mockito.<Boolean>any());
        doNothing().when(sysObstetricExamination).setModifiedBy(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(sysObstetricExamination).setProcessed(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(sysObstetricExamination).setReasonsForHRP(Mockito.<String[]>any());
        doNothing().when(sysObstetricExamination).setReasonsForHRPDB(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setReservedForChange(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setSfh(Mockito.<Double>any());
        doNothing().when(sysObstetricExamination).setSyncedBy(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(sysObstetricExamination).setVanID(Mockito.<Integer>any());
        doNothing().when(sysObstetricExamination).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(sysObstetricExamination).setVehicalNo(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setVertebralDeformity(Mockito.<Boolean>any());
        doNothing().when(sysObstetricExamination).setVisitCode(Mockito.<Long>any());
        doNothing().when(sysObstetricExamination).setfHAndPOA_Interpretation(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setfHAndPOA_Status(Mockito.<String>any());
        sysObstetricExamination.setAbdominalScars("Abdominal Scars");
        sysObstetricExamination.setBenVisitID(1L);
        sysObstetricExamination.setBeneficiaryRegID(1L);
        sysObstetricExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        sysObstetricExamination.setCreatedDate(mock(Timestamp.class));
        sysObstetricExamination.setDeleted(true);
        sysObstetricExamination.setFetalHeartRate_BeatsPerMinute("Fetal Heart Rate Beats Per Minute");
        sysObstetricExamination.setFetalHeartSounds("Fetal Heart Sounds");
        sysObstetricExamination.setFetalMovements("Fetal Movements");
        sysObstetricExamination.setFetalPositionOrLie("Fetal Position Or Lie");
        sysObstetricExamination.setFetalPresentation("Fetal Presentation");
        sysObstetricExamination.setFundalHeight("Fundal Height");
        sysObstetricExamination.setID(1L);
        sysObstetricExamination.setIsHRP(true);
        sysObstetricExamination.setLastModDate(mock(Timestamp.class));
        sysObstetricExamination.setLowLyingPlacenta(true);
        sysObstetricExamination.setMalPresentation(true);
        sysObstetricExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        sysObstetricExamination.setParkingPlaceID(1);
        sysObstetricExamination.setProcessed("Processed");
        sysObstetricExamination.setProviderServiceMapID(1);
        sysObstetricExamination.setReasonsForHRP(new String[]{"Just cause"});
        sysObstetricExamination.setReasonsForHRPDB("Just cause");
        sysObstetricExamination.setReservedForChange("Reserved For Change");
        sysObstetricExamination.setSfh(10.0d);
        sysObstetricExamination.setSyncedBy("Synced By");
        sysObstetricExamination.setSyncedDate(mock(Timestamp.class));
        sysObstetricExamination.setVanID(1);
        sysObstetricExamination.setVanSerialNo(1L);
        sysObstetricExamination.setVehicalNo("Vehical No");
        sysObstetricExamination.setVertebralDeformity(true);
        sysObstetricExamination.setVisitCode(1L);
        sysObstetricExamination.setfHAndPOA_Interpretation("F HAnd POA Interpretation");
        sysObstetricExamination.setfHAndPOA_Status("F HAnd POA Status");
        when(sysObstetricExaminationRepo.findFirstByBeneficiaryRegIDOrderByIDDesc(Mockito.<Long>any()))
                .thenReturn(sysObstetricExamination);

        // Act
        aNCServiceImpl.getHRPInformation(1L);

        // Assert
        verify(sysObstetricExamination).getReasonsForHRPDB();
        verify(sysObstetricExamination).setAbdominalScars(eq("Abdominal Scars"));
        verify(sysObstetricExamination).setBenVisitID(isA(Long.class));
        verify(sysObstetricExamination).setBeneficiaryRegID(isA(Long.class));
        verify(sysObstetricExamination).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(sysObstetricExamination).setCreatedDate(isA(Timestamp.class));
        verify(sysObstetricExamination).setDeleted(isA(Boolean.class));
        verify(sysObstetricExamination).setFetalHeartRate_BeatsPerMinute(eq("Fetal Heart Rate Beats Per Minute"));
        verify(sysObstetricExamination).setFetalHeartSounds(eq("Fetal Heart Sounds"));
        verify(sysObstetricExamination).setFetalMovements(eq("Fetal Movements"));
        verify(sysObstetricExamination).setFetalPositionOrLie(eq("Fetal Position Or Lie"));
        verify(sysObstetricExamination).setFetalPresentation(eq("Fetal Presentation"));
        verify(sysObstetricExamination).setFundalHeight(eq("Fundal Height"));
        verify(sysObstetricExamination).setID(isA(Long.class));
        verify(sysObstetricExamination).setIsHRP(isA(Boolean.class));
        verify(sysObstetricExamination).setLastModDate(isA(Timestamp.class));
        verify(sysObstetricExamination).setLowLyingPlacenta(isA(Boolean.class));
        verify(sysObstetricExamination).setMalPresentation(isA(Boolean.class));
        verify(sysObstetricExamination).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(sysObstetricExamination).setParkingPlaceID(isA(Integer.class));
        verify(sysObstetricExamination).setProcessed(eq("Processed"));
        verify(sysObstetricExamination).setProviderServiceMapID(isA(Integer.class));
        verify(sysObstetricExamination).setReasonsForHRP(isA(String[].class));
        verify(sysObstetricExamination).setReasonsForHRPDB(eq("Just cause"));
        verify(sysObstetricExamination).setReservedForChange(eq("Reserved For Change"));
        verify(sysObstetricExamination).setSfh(isA(Double.class));
        verify(sysObstetricExamination).setSyncedBy(eq("Synced By"));
        verify(sysObstetricExamination).setSyncedDate(isA(Timestamp.class));
        verify(sysObstetricExamination).setVanID(isA(Integer.class));
        verify(sysObstetricExamination).setVanSerialNo(isA(Long.class));
        verify(sysObstetricExamination).setVehicalNo(eq("Vehical No"));
        verify(sysObstetricExamination).setVertebralDeformity(isA(Boolean.class));
        verify(sysObstetricExamination).setVisitCode(isA(Long.class));
        verify(sysObstetricExamination).setfHAndPOA_Interpretation(eq("F HAnd POA Interpretation"));
        verify(sysObstetricExamination).setfHAndPOA_Status(eq("F HAnd POA Status"));
        verify(sysObstetricExaminationRepo).findFirstByBeneficiaryRegIDOrderByIDDesc(isA(Long.class));
    }

    @Test
    void testGettersAndSetters() {
        ANCServiceImpl ancServiceImpl = new ANCServiceImpl();

        // Act
        ancServiceImpl.setANCDoctorServiceImpl(new ANCDoctorServiceImpl());
        ancServiceImpl.setAncNurseServiceImpl(new ANCNurseServiceImpl());
        ancServiceImpl.setCommonBenStatusFlowServiceImpl(new CommonBenStatusFlowServiceImpl());
        ancServiceImpl.setCommonDoctorServiceImpl(new CommonDoctorServiceImpl());
        ancServiceImpl.setCommonNurseServiceImpl(new CommonNurseServiceImpl());
        ancServiceImpl.setLabTechnicianServiceImpl(new LabTechnicianServiceImpl());
    }
}
