package com.iemr.hwc.service.pnc;

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
import java.util.Map;

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
import com.iemr.hwc.data.anc.BenChildDevelopmentHistory;
import com.iemr.hwc.data.anc.BenFamilyHistory;
import com.iemr.hwc.data.anc.BenMedHistory;
import com.iemr.hwc.data.anc.BenMenstrualDetails;
import com.iemr.hwc.data.anc.BenPersonalHabit;
import com.iemr.hwc.data.anc.ChildFeedingDetails;
import com.iemr.hwc.data.anc.PerinatalHistory;
import com.iemr.hwc.data.anc.PhyGeneralExamination;
import com.iemr.hwc.data.anc.PhyHeadToToeExamination;
import com.iemr.hwc.data.anc.SysCardiovascularExamination;
import com.iemr.hwc.data.anc.SysCentralNervousExamination;
import com.iemr.hwc.data.anc.SysGastrointestinalExamination;
import com.iemr.hwc.data.anc.SysGenitourinarySystemExamination;
import com.iemr.hwc.data.anc.SysMusculoskeletalSystemExamination;
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
import com.iemr.hwc.data.pnc.PNCCare;
import com.iemr.hwc.data.provider.ProviderServiceMapping;
import com.iemr.hwc.data.quickConsultation.PrescriptionDetail;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.location.ZoneDistrictMapping;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.nurse.CDSSRepo;
import com.iemr.hwc.repo.nurse.anc.BenAdherenceRepo;
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
class PNCServiceImplTest {
    @Mock
    private BenAdherenceRepo benAdherenceRepo;

    @Mock
    private BenChiefComplaintRepo benChiefComplaintRepo;

    @Mock
    private BenVisitDetailRepo benVisitDetailRepo;

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
    private LabTechnicianServiceImpl labTechnicianServiceImpl;

    @Mock
    private PNCDoctorServiceImpl pNCDoctorServiceImpl;

    @Mock
    private PNCNurseServiceImpl pNCNurseServiceImpl;

    @InjectMocks
    private PNCServiceImpl pNCServiceImpl;

    @Mock
    private SMSGatewayServiceImpl sMSGatewayServiceImpl;

    @Mock
    private TeleConsultationServiceImpl teleConsultationServiceImpl;

    @Test
    void testSavePNCNurseData() throws Exception {
        // Arrange, Act and Assert
        assertThrows(Exception.class, () -> pNCServiceImpl.savePNCNurseData(new JsonObject(), "JaneDoe"));
        assertThrows(Exception.class, () -> pNCServiceImpl.savePNCNurseData(null, "JaneDoe"));
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
        pNCServiceImpl.deleteVisitDetails(requestOBJ);

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
        assertThrows(RuntimeException.class, () -> pNCServiceImpl.deleteVisitDetails(requestOBJ));
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
        pNCServiceImpl.deleteVisitDetails(requestOBJ);

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
        pNCServiceImpl.deleteVisitDetails(requestOBJ);

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
        pNCServiceImpl.deleteVisitDetails(requestOBJ);

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
        pNCServiceImpl.deleteVisitDetails(requestOBJ);

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
        pNCServiceImpl.deleteVisitDetails(requestOBJ);

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
        pNCServiceImpl.deleteVisitDetails(requestOBJ);

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
        assertThrows(RuntimeException.class, () -> pNCServiceImpl.deleteVisitDetails(requestOBJ));
        verify(value).isJsonNull();
    }

    @Test
    void testSavePNCDoctorData() throws Exception {
        // Arrange, Act and Assert
        assertNull(pNCServiceImpl.savePNCDoctorData(null, "JaneDoe"));
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
        assertTrue(pNCServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass).isEmpty());
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
        assertTrue(pNCServiceImpl.saveBenVisitDetails(null, nurseUtilityClass).isEmpty());
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
        assertTrue(pNCServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass).isEmpty());
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
        assertTrue(pNCServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass).isEmpty());
    }

    @Test
    void testSaveBenVisitDetails5() throws Exception {
        // Arrange
        JsonObject visitDetailsOBJ = new JsonObject();
        CommonUtilityClass nurseUtilityClass = mock(CommonUtilityClass.class);
        doNothing().when(nurseUtilityClass).setAge(Mockito.<Integer>any());
        doNothing().when(nurseUtilityClass).setAgeUnits(Mockito.<String>any());
        doNothing().when(nurseUtilityClass).setAuthorization(Mockito.<String>any());
        doNothing().when(nurseUtilityClass).setBenFlowID(Mockito.<Long>any());
        doNothing().when(nurseUtilityClass).setBenRegID(Mockito.<Long>any());
        doNothing().when(nurseUtilityClass).setBenVisitID(Mockito.<Long>any());
        doNothing().when(nurseUtilityClass).setBeneficiaryID(Mockito.<Long>any());
        doNothing().when(nurseUtilityClass).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(nurseUtilityClass).setBeneficiaryRegId(Mockito.<Long>any());
        doNothing().when(nurseUtilityClass).setCreatedBy(Mockito.<String>any());
        doNothing().when(nurseUtilityClass).setDistrictID(Mockito.<Integer>any());
        doNothing().when(nurseUtilityClass).setDistrictName(Mockito.<String>any());
        doNothing().when(nurseUtilityClass).setFacilityID(Mockito.<Integer>any());
        doNothing().when(nurseUtilityClass).setFirstName(Mockito.<String>any());
        doNothing().when(nurseUtilityClass).setGenderID(Mockito.<Short>any());
        doNothing().when(nurseUtilityClass).setGenderName(Mockito.<String>any());
        doNothing().when(nurseUtilityClass).setIsCovidFlowDone(Mockito.<Boolean>any());
        doNothing().when(nurseUtilityClass).setIsMobile(Mockito.<Boolean>any());
        doNothing().when(nurseUtilityClass).setIsSpecialist(Mockito.<Boolean>any());
        doNothing().when(nurseUtilityClass).setLastName(Mockito.<String>any());
        doNothing().when(nurseUtilityClass).setModifiedBy(Mockito.<String>any());
        doNothing().when(nurseUtilityClass).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(nurseUtilityClass).setParkingPlaceId(Mockito.<Integer>any());
        doNothing().when(nurseUtilityClass).setPrescriptionID(Mockito.<Long>any());
        doNothing().when(nurseUtilityClass).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(nurseUtilityClass).setServiceID(Mockito.<Short>any());
        doNothing().when(nurseUtilityClass).setSessionID(Mockito.<Integer>any());
        doNothing().when(nurseUtilityClass).setSpecialistDiagnosis(Mockito.<String>any());
        doNothing().when(nurseUtilityClass).setSubVisitCategory(Mockito.<String>any());
        doNothing().when(nurseUtilityClass).setTreatmentsOnSideEffects(Mockito.<String>any());
        doNothing().when(nurseUtilityClass).setVanID(Mockito.<Integer>any());
        doNothing().when(nurseUtilityClass).setVanId(Mockito.<Integer>any());
        doNothing().when(nurseUtilityClass).setVillageId(Mockito.<Integer>any());
        doNothing().when(nurseUtilityClass).setVillageName(Mockito.<String>any());
        doNothing().when(nurseUtilityClass).setVisitCategoryID(Mockito.<Integer>any());
        doNothing().when(nurseUtilityClass).setVisitCode(Mockito.<Long>any());
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

        // Act
        Map<String, Long> actualSaveBenVisitDetailsResult = pNCServiceImpl.saveBenVisitDetails(visitDetailsOBJ,
                nurseUtilityClass);

        // Assert
        verify(nurseUtilityClass).setAge(isA(Integer.class));
        verify(nurseUtilityClass).setAgeUnits(eq("Age Units"));
        verify(nurseUtilityClass).setAuthorization(eq("JaneDoe"));
        verify(nurseUtilityClass).setBenFlowID(isA(Long.class));
        verify(nurseUtilityClass).setBenRegID(isA(Long.class));
        verify(nurseUtilityClass).setBenVisitID(isA(Long.class));
        verify(nurseUtilityClass).setBeneficiaryID(isA(Long.class));
        verify(nurseUtilityClass).setBeneficiaryRegID(isA(Long.class));
        verify(nurseUtilityClass).setBeneficiaryRegId(isA(Long.class));
        verify(nurseUtilityClass).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(nurseUtilityClass).setDistrictID(isA(Integer.class));
        verify(nurseUtilityClass).setDistrictName(eq("District Name"));
        verify(nurseUtilityClass).setFacilityID(isA(Integer.class));
        verify(nurseUtilityClass).setFirstName(eq("Jane"));
        verify(nurseUtilityClass).setGenderID(isA(Short.class));
        verify(nurseUtilityClass).setGenderName(eq("Gender Name"));
        verify(nurseUtilityClass).setIsCovidFlowDone(isA(Boolean.class));
        verify(nurseUtilityClass).setIsMobile(isA(Boolean.class));
        verify(nurseUtilityClass).setIsSpecialist(isA(Boolean.class));
        verify(nurseUtilityClass).setLastName(eq("Doe"));
        verify(nurseUtilityClass).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(nurseUtilityClass).setParkingPlaceID(isA(Integer.class));
        verify(nurseUtilityClass).setParkingPlaceId(isA(Integer.class));
        verify(nurseUtilityClass).setPrescriptionID(isA(Long.class));
        verify(nurseUtilityClass).setProviderServiceMapID(isA(Integer.class));
        verify(nurseUtilityClass).setServiceID(isA(Short.class));
        verify(nurseUtilityClass).setSessionID(isA(Integer.class));
        verify(nurseUtilityClass).setSpecialistDiagnosis(eq("Specialist Diagnosis"));
        verify(nurseUtilityClass).setSubVisitCategory(eq("Sub Visit Category"));
        verify(nurseUtilityClass).setTreatmentsOnSideEffects(eq("Treatments On Side Effects"));
        verify(nurseUtilityClass).setVanID(isA(Integer.class));
        verify(nurseUtilityClass).setVanId(isA(Integer.class));
        verify(nurseUtilityClass).setVillageId(isA(Integer.class));
        verify(nurseUtilityClass).setVillageName(eq("Village Name"));
        verify(nurseUtilityClass).setVisitCategoryID(isA(Integer.class));
        verify(nurseUtilityClass).setVisitCode(isA(Long.class));
        assertTrue(actualSaveBenVisitDetailsResult.isEmpty());
    }

    @Test
    void testSaveBenPNCHistoryDetails() throws Exception {
        // Arrange, Act and Assert
        assertEquals(1L, pNCServiceImpl.saveBenPNCHistoryDetails(new JsonObject(), 1L, 1L).longValue());
        assertEquals(1L, pNCServiceImpl.saveBenPNCHistoryDetails(null, 1L, 1L).longValue());
    }

    @Test
    void testSaveBenPNCHistoryDetails2() throws Exception {
        // Arrange
        JsonObject pncHistoryOBJ = new JsonObject();
        pncHistoryOBJ.add("Property", new JsonArray(3));

        // Act and Assert
        assertEquals(1L, pNCServiceImpl.saveBenPNCHistoryDetails(pncHistoryOBJ, 1L, 1L).longValue());
    }

    @Test
    void testSaveBenPNCHistoryDetails3() throws Exception {
        // Arrange
        JsonObject pncHistoryOBJ = new JsonObject();
        pncHistoryOBJ.addProperty("pastHistory", (String) null);

        // Act and Assert
        assertEquals(1L, pNCServiceImpl.saveBenPNCHistoryDetails(pncHistoryOBJ, 1L, 1L).longValue());
    }

    @Test
    void testSaveBenPNCDetails() throws Exception {
        // Arrange, Act and Assert
        assertEquals(1L, pNCServiceImpl.saveBenPNCDetails(new JsonObject(), 1L, 1L).longValue());
        assertEquals(1L, pNCServiceImpl.saveBenPNCDetails(null, 1L, 1L).longValue());
    }

    @Test
    void testSaveBenPNCDetails2() throws Exception {
        // Arrange
        JsonObject pncDetailsOBJ = new JsonObject();
        pncDetailsOBJ.add("Property", new JsonArray(3));

        // Act and Assert
        assertEquals(1L, pNCServiceImpl.saveBenPNCDetails(pncDetailsOBJ, 1L, 1L).longValue());
    }

    @Test
    void testSaveBenPNCDetails3() throws Exception {
        // Arrange
        JsonObject pncDetailsOBJ = new JsonObject();
        pncDetailsOBJ.addProperty("pNCDeatils", (String) null);

        // Act and Assert
        assertEquals(1L, pNCServiceImpl.saveBenPNCDetails(pncDetailsOBJ, 1L, 1L).longValue());
    }

    @Test
    void testSaveBenPNCVitalDetails() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(1L);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(1L);

        // Act
        Long actualSaveBenPNCVitalDetailsResult = pNCServiceImpl.saveBenPNCVitalDetails(new JsonObject(), 1L, 1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1L, actualSaveBenPNCVitalDetailsResult.longValue());
    }

    @Test
    void testSaveBenPNCVitalDetails2() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(0L);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(1L);

        // Act
        Long actualSaveBenPNCVitalDetailsResult = pNCServiceImpl.saveBenPNCVitalDetails(new JsonObject(), 1L, 1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertNull(actualSaveBenPNCVitalDetailsResult);
    }

    @Test
    void testSaveBenPNCVitalDetails3() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(null);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(1L);

        // Act
        Long actualSaveBenPNCVitalDetailsResult = pNCServiceImpl.saveBenPNCVitalDetails(new JsonObject(), 1L, 1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertNull(actualSaveBenPNCVitalDetailsResult);
    }

    @Test
    void testSaveBenPNCVitalDetails4() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(1L);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(0L);

        // Act
        Long actualSaveBenPNCVitalDetailsResult = pNCServiceImpl.saveBenPNCVitalDetails(new JsonObject(), 1L, 1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertNull(actualSaveBenPNCVitalDetailsResult);
    }

    @Test
    void testSaveBenPNCVitalDetails5() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(1L);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(null);

        // Act
        Long actualSaveBenPNCVitalDetailsResult = pNCServiceImpl.saveBenPNCVitalDetails(new JsonObject(), 1L, 1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertNull(actualSaveBenPNCVitalDetailsResult);
    }

    @Test
    void testSaveBenPNCVitalDetails6() throws Exception {
        // Arrange, Act and Assert
        assertNull(pNCServiceImpl.saveBenPNCVitalDetails(null, 1L, 1L));
    }

    @Test
    void testSaveBenPNCVitalDetails7() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(1L);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(1L);

        JsonObject vitalDetailsOBJ = new JsonObject();
        vitalDetailsOBJ.add("Property", new JsonArray(3));

        // Act
        Long actualSaveBenPNCVitalDetailsResult = pNCServiceImpl.saveBenPNCVitalDetails(vitalDetailsOBJ, 1L, 1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1L, actualSaveBenPNCVitalDetailsResult.longValue());
    }

    @Test
    void testSaveBenPNCVitalDetails8() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(1L);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(1L);

        JsonObject vitalDetailsOBJ = new JsonObject();
        vitalDetailsOBJ.addProperty("Property", "42");

        // Act
        Long actualSaveBenPNCVitalDetailsResult = pNCServiceImpl.saveBenPNCVitalDetails(vitalDetailsOBJ, 1L, 1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1L, actualSaveBenPNCVitalDetailsResult.longValue());
    }

    @Test
    void testSaveBenPNCVitalDetails9() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(1L);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(1L);

        JsonObject vitalDetailsOBJ = new JsonObject();
        vitalDetailsOBJ.addProperty("Property", Integer.valueOf(1));

        // Act
        Long actualSaveBenPNCVitalDetailsResult = pNCServiceImpl.saveBenPNCVitalDetails(vitalDetailsOBJ, 1L, 1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1L, actualSaveBenPNCVitalDetailsResult.longValue());
    }

    @Test
    void testSaveBenPNCVitalDetails10() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(1L);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(1L);

        JsonObject vitalDetailsOBJ = new JsonObject();
        vitalDetailsOBJ.addProperty("Property", true);

        // Act
        Long actualSaveBenPNCVitalDetailsResult = pNCServiceImpl.saveBenPNCVitalDetails(vitalDetailsOBJ, 1L, 1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1L, actualSaveBenPNCVitalDetailsResult.longValue());
    }

    @Test
    void testSaveBenPNCVitalDetails11() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> pNCServiceImpl.saveBenPNCVitalDetails(new JsonObject(), 1L, 1L));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
    }

    @Test
    void testSaveBenExaminationDetails() throws Exception {
        // Arrange, Act and Assert
        assertEquals(1L, pNCServiceImpl.saveBenExaminationDetails(new JsonObject(), 1L, 1L).longValue());
        assertEquals(1L, pNCServiceImpl.saveBenExaminationDetails(null, 1L, 1L).longValue());
    }

    @Test
    void testSaveBenExaminationDetails2() throws Exception {
        // Arrange
        JsonObject examinationDetailsOBJ = new JsonObject();
        examinationDetailsOBJ.add("Property", new JsonArray(3));

        // Act and Assert
        assertEquals(1L, pNCServiceImpl.saveBenExaminationDetails(examinationDetailsOBJ, 1L, 1L).longValue());
    }

    @Test
    void testSaveBenExaminationDetails3() throws Exception {
        // Arrange
        JsonObject examinationDetailsOBJ = new JsonObject();
        examinationDetailsOBJ.addProperty("generalExamination", (String) null);

        // Act and Assert
        assertEquals(1L, pNCServiceImpl.saveBenExaminationDetails(examinationDetailsOBJ, 1L, 1L).longValue());
    }

    @Test
    void testGetBenVisitDetailsFrmNursePNC() {
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

        // Act
        pNCServiceImpl.getBenVisitDetailsFrmNursePNC(1L, 1L);

        // Assert
        verify(commonNurseServiceImpl).getBenAdherence(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getBenCdss(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getBenChiefComplaints(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getCSVisitDetails(isA(Long.class), isA(Long.class));
        verify(createdDate).getTime();
        verify(lastModDate).getTime();
        verify(syncedDate).getTime();
        verify(visitDateTime).getTime();
    }

    @Test
    void testGetBenVisitDetailsFrmNursePNC2() {
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
        when(visitDateTime.getTime()).thenThrow(new RuntimeException("PNCNurseVisitDetail"));

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
        assertThrows(RuntimeException.class, () -> pNCServiceImpl.getBenVisitDetailsFrmNursePNC(1L, 1L));
        verify(commonNurseServiceImpl).getCSVisitDetails(isA(Long.class), isA(Long.class));
        verify(visitDateTime).getTime();
    }

    @Test
    void testGetBenVisitDetailsFrmNursePNC3() {
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

        // Act
        String actualBenVisitDetailsFrmNursePNC = pNCServiceImpl.getBenVisitDetailsFrmNursePNC(1L, 1L);

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
        assertEquals(
                "{PNCNurseVisitDetail={}, BenAdherence=Ben Adherence, BenChiefComplaints=Ben Chief Complaints, Cdss=Ben"
                        + " Cdss}",
                actualBenVisitDetailsFrmNursePNC);
    }

    @Test
    void testGetBenPNCDetailsFrmNursePNC() {
        // Arrange
        when(pNCNurseServiceImpl.getPNCCareDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Pnc Care Details");

        // Act
        String actualBenPNCDetailsFrmNursePNC = pNCServiceImpl.getBenPNCDetailsFrmNursePNC(1L, 1L);

        // Assert
        verify(pNCNurseServiceImpl).getPNCCareDetails(isA(Long.class), isA(Long.class));
        assertEquals("{PNCCareDetail=Pnc Care Details}", actualBenPNCDetailsFrmNursePNC);
    }

    @Test
    void testGetBenPNCDetailsFrmNursePNC2() {
        // Arrange
        when(beneficiaryFlowStatusRepo.getLatestVisitCode(Mockito.<Long>any(), Mockito.<String>any())).thenReturn(1L);
        when(pNCNurseServiceImpl.getPNCCareDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Pnc Care Details");

        // Act
        String actualBenPNCDetailsFrmNursePNC = pNCServiceImpl.getBenPNCDetailsFrmNursePNC(1L, null);

        // Assert
        verify(beneficiaryFlowStatusRepo).getLatestVisitCode(isA(Long.class), eq("PNC"));
        verify(pNCNurseServiceImpl).getPNCCareDetails(isA(Long.class), isA(Long.class));
        assertEquals("{PNCCareDetail=Pnc Care Details}", actualBenPNCDetailsFrmNursePNC);
    }

    @Test
    void testGetBenPNCDetailsFrmNursePNC3() {
        // Arrange
        when(beneficiaryFlowStatusRepo.getLatestVisitCode(Mockito.<Long>any(), Mockito.<String>any())).thenReturn(null);

        // Act
        String actualBenPNCDetailsFrmNursePNC = pNCServiceImpl.getBenPNCDetailsFrmNursePNC(1L, null);

        // Assert
        verify(beneficiaryFlowStatusRepo).getLatestVisitCode(isA(Long.class), eq("PNC"));
        assertEquals("{}", actualBenPNCDetailsFrmNursePNC);
    }

    @Test
    void testGetBenPNCDetailsFrmNursePNC4() {
        // Arrange
        when(pNCNurseServiceImpl.getPNCCareDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Pnc Care Details");

        // Act
        String actualBenPNCDetailsFrmNursePNC = pNCServiceImpl.getBenPNCDetailsFrmNursePNC(null, null);

        // Assert
        verify(pNCNurseServiceImpl).getPNCCareDetails(isNull(), isNull());
        assertEquals("{PNCCareDetail=Pnc Care Details}", actualBenPNCDetailsFrmNursePNC);
    }

    @Test
    void testGetBenPNCDetailsFrmNursePNC5() {
        // Arrange
        when(beneficiaryFlowStatusRepo.getLatestVisitCode(Mockito.<Long>any(), Mockito.<String>any()))
                .thenThrow(new RuntimeException("PNC"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> pNCServiceImpl.getBenPNCDetailsFrmNursePNC(1L, null));
        verify(beneficiaryFlowStatusRepo).getLatestVisitCode(isA(Long.class), eq("PNC"));
    }

    @Test
    void testGetBenHistoryDetails() throws IEMRException {
        BenChildDevelopmentHistory benChildDevelopmentHistory = new BenChildDevelopmentHistory();
        benChildDevelopmentHistory.setBenVisitID(1L);
        benChildDevelopmentHistory.setBeneficiaryRegID(1L);
        benChildDevelopmentHistory.setCaptureDate(mock(Date.class));
        benChildDevelopmentHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benChildDevelopmentHistory.setCreatedDate(mock(Timestamp.class));
        benChildDevelopmentHistory.setDeleted(true);
        benChildDevelopmentHistory.setDevelopmentProblem("Development Problem");
        benChildDevelopmentHistory.setDevelopmentProblems(new ArrayList<>());
        benChildDevelopmentHistory.setFineMotorMilestone("Fine Motor Milestone");
        benChildDevelopmentHistory.setFineMotorMilestones(new ArrayList<>());
        benChildDevelopmentHistory.setGrossMotorMilestone("Gross Motor Milestone");
        benChildDevelopmentHistory.setGrossMotorMilestones(new ArrayList<>());
        benChildDevelopmentHistory.setID(1L);
        benChildDevelopmentHistory.setIsFineMotorMilestones(true);
        benChildDevelopmentHistory.setIsGrossMotorMilestones(true);
        benChildDevelopmentHistory.setIsLanguageMilestones(true);
        benChildDevelopmentHistory.setIsSocialMilestones(true);
        benChildDevelopmentHistory.setLanguageMilestone("en");
        benChildDevelopmentHistory.setLanguageMilestones(new ArrayList<>());
        benChildDevelopmentHistory.setLastModDate(mock(Timestamp.class));
        benChildDevelopmentHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benChildDevelopmentHistory.setParkingPlaceID(1);
        benChildDevelopmentHistory.setProcessed("Processed");
        benChildDevelopmentHistory.setProviderServiceMapID(1);
        benChildDevelopmentHistory.setReservedForChange("Reserved For Change");
        benChildDevelopmentHistory.setSocialMilestone("Social Milestone");
        benChildDevelopmentHistory.setSocialMilestones(new ArrayList<>());
        benChildDevelopmentHistory.setSyncedBy("Synced By");
        benChildDevelopmentHistory.setSyncedDate(mock(Timestamp.class));
        benChildDevelopmentHistory.setVanID(1);
        benChildDevelopmentHistory.setVanSerialNo(1L);
        benChildDevelopmentHistory.setVehicalNo("Vehical No");
        benChildDevelopmentHistory.setVisitCode(1L);

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

        ChildFeedingDetails childFeedingDetails = new ChildFeedingDetails();
        childFeedingDetails.setBenMotherID(1L);
        childFeedingDetails.setBenVisitID(1L);
        childFeedingDetails.setBeneficiaryRegID(1L);
        childFeedingDetails.setCaptureDate(mock(Date.class));
        childFeedingDetails.setChildID(1L);
        childFeedingDetails.setCompFeedStartAge("Comp Feed Start Age");
        childFeedingDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        childFeedingDetails.setCreatedDate(mock(Timestamp.class));
        childFeedingDetails.setDeleted(true);
        childFeedingDetails.setFoodIntoleranceStatus("Food Intolerance Status");
        childFeedingDetails.setID(1L);
        childFeedingDetails.setLastModDate(mock(Timestamp.class));
        childFeedingDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        childFeedingDetails.setNoOfCompFeedPerDay("No Of Comp Feed Per Day");
        childFeedingDetails.setOtherFoodIntolerance("Other Food Intolerance");
        childFeedingDetails.setParkingPlaceID(1);
        childFeedingDetails.setProcessed("Processed");
        childFeedingDetails.setProviderServiceMapID(1);
        childFeedingDetails.setReservedForChange("Reserved For Change");
        childFeedingDetails.setSyncedBy("Synced By");
        childFeedingDetails.setSyncedDate(mock(Timestamp.class));
        childFeedingDetails.setTypeOfFeed("Type Of Feed");
        childFeedingDetails.setTypeOfFoodIntolerances(new String[]{"Type Of Food Intolerances"});
        childFeedingDetails.setTypeofFoodIntolerance("Typeof Food Intolerance");
        childFeedingDetails.setTypeofFoodIntoleranceID(1L);
        childFeedingDetails.setVanID(1);
        childFeedingDetails.setVanSerialNo(1L);
        childFeedingDetails.setVehicalNo("Vehical No");
        childFeedingDetails.setVisitCode(1L);

        PerinatalHistory perinatalHistory = new PerinatalHistory();
        perinatalHistory.setBenVisitID(1L);
        perinatalHistory.setBeneficiaryRegID(1L);
        perinatalHistory.setBirthWeightG(10.0d);
        perinatalHistory.setComplicationAtBirth("Complication At Birth");
        perinatalHistory.setComplicationAtBirthID((short) 1);
        perinatalHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        perinatalHistory.setCreatedDate(mock(Timestamp.class));
        perinatalHistory.setDeleted(true);
        perinatalHistory.setDeliveryPlaceID((short) 1);
        perinatalHistory.setDeliveryTypeID((short) 1);
        perinatalHistory.setGestation("Gestation");
        perinatalHistory.setID(1L);
        perinatalHistory.setLastModDate(mock(Timestamp.class));
        perinatalHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        perinatalHistory.setOtherComplicationAtBirth("Other Complication At Birth");
        perinatalHistory.setOtherPlaceOfDelivery("Other Place Of Delivery");
        perinatalHistory.setParkingPlaceID(1);
        perinatalHistory.setPlaceOfDelivery("Place Of Delivery");
        perinatalHistory.setProcessed("Processed");
        perinatalHistory.setProviderServiceMapID(1);
        perinatalHistory.setReservedForChange("Reserved For Change");
        perinatalHistory.setSyncedBy("Synced By");
        perinatalHistory.setSyncedDate(mock(Timestamp.class));
        perinatalHistory.setTypeOfDelivery("Type Of Delivery");
        perinatalHistory.setVanSerialNo(1L);
        perinatalHistory.setVehicalNo("Vehical No");
        perinatalHistory.setVisitCode(1L);

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
        when(commonNurseServiceImpl.getDevelopmentHistory(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(benChildDevelopmentHistory);
        when(commonNurseServiceImpl.getFamilyHistory(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(benFamilyHistory);
        when(commonNurseServiceImpl.getPastHistoryData(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(benMedHistory);
        when(commonNurseServiceImpl.getMenstrualHistory(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(benMenstrualDetails);
        when(commonNurseServiceImpl.getPersonalHistory(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(benPersonalHabit);
        when(commonNurseServiceImpl.getFeedingHistory(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(childFeedingDetails);
        when(commonNurseServiceImpl.getPerinatalHistory(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(perinatalHistory);
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
        pNCServiceImpl.getBenHistoryDetails(1L, 1L);
    }

    @Test
    void testGetBeneficiaryVitalDetails() {
        // Arrange
        when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Beneficiary Physical Anthropometry Details");
        when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Beneficiary Physical Vital Details");

        // Act
        String actualBeneficiaryVitalDetails = pNCServiceImpl.getBeneficiaryVitalDetails(1L, 1L);

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
        assertThrows(RuntimeException.class, () -> pNCServiceImpl.getBeneficiaryVitalDetails(1L, 1L));
        verify(commonNurseServiceImpl).getBeneficiaryPhysicalAnthropometryDetails(isA(Long.class), isA(Long.class));
    }

    @Test
    void testGetBeneficiaryCdssDetails() {
        // Arrange
        when(commonNurseServiceImpl.getBenCdssDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Ben Cdss Details");

        // Act
        String actualBeneficiaryCdssDetails = pNCServiceImpl.getBeneficiaryCdssDetails(1L, 1L);

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
        assertThrows(RuntimeException.class, () -> pNCServiceImpl.getBeneficiaryCdssDetails(1L, 1L));
        verify(commonNurseServiceImpl).getBenCdssDetails(isA(Long.class), isA(Long.class));
    }

    @Test
    void testGetPNCExaminationDetailsData() {
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

        SysGastrointestinalExamination sysGastrointestinalExamination = new SysGastrointestinalExamination();
        sysGastrointestinalExamination.setAnalRegion("us-east-2");
        sysGastrointestinalExamination.setAuscultation("Auscultation");
        sysGastrointestinalExamination.setBenVisitID(1L);
        sysGastrointestinalExamination.setBeneficiaryRegID(1L);
        sysGastrointestinalExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        sysGastrointestinalExamination.setCreatedDate(mock(Timestamp.class));
        sysGastrointestinalExamination.setDeleted(true);
        sysGastrointestinalExamination.setID(1L);
        sysGastrointestinalExamination.setInspection("Inspection");
        sysGastrointestinalExamination.setLastModDate(mock(Timestamp.class));
        sysGastrointestinalExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        sysGastrointestinalExamination.setPalpation("Palpation");
        sysGastrointestinalExamination.setPalpation_AbdomenTexture("Palpation Abdomen Texture");
        sysGastrointestinalExamination.setPalpation_Liver("Palpation Liver");
        sysGastrointestinalExamination.setPalpation_LocationOfTenderness("Palpation Location Of Tenderness");
        sysGastrointestinalExamination.setPalpation_Spleen("Palpation Spleen");
        sysGastrointestinalExamination.setPalpation_Tenderness("Palpation Tenderness");
        sysGastrointestinalExamination.setParkingPlaceID(1);
        sysGastrointestinalExamination.setPercussion("Percussion");
        sysGastrointestinalExamination.setProcessed("Processed");
        sysGastrointestinalExamination.setProviderServiceMapID(1);
        sysGastrointestinalExamination.setReservedForChange("Reserved For Change");
        sysGastrointestinalExamination.setSyncedBy("Synced By");
        sysGastrointestinalExamination.setSyncedDate(mock(Timestamp.class));
        sysGastrointestinalExamination.setVanSerialNo(1L);
        sysGastrointestinalExamination.setVehicalNo("Vehical No");
        sysGastrointestinalExamination.setVisitCode(1L);

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
        when(commonNurseServiceImpl.getSysGastrointestinalExamination(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(sysGastrointestinalExamination);
        when(commonNurseServiceImpl.getGenitourinaryExamination(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(sysGenitourinarySystemExamination);
        when(commonNurseServiceImpl.getMusculoskeletalExamination(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(sysMusculoskeletalSystemExamination);
        when(commonNurseServiceImpl.getRespiratoryExamination(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(sysRespiratoryExamination);

        // Act
        pNCServiceImpl.getPNCExaminationDetailsData(1L, 1L);
    }

    @Test
    void testUpdateBenHistoryDetails() throws Exception {
        // Arrange, Act and Assert
        assertEquals(1, pNCServiceImpl.updateBenHistoryDetails(new JsonObject()));
        assertEquals(1, pNCServiceImpl.updateBenHistoryDetails(null));
    }

    @Test
    void testUpdateBenHistoryDetails2() throws Exception {
        // Arrange
        JsonObject historyOBJ = new JsonObject();
        historyOBJ.add("Property", new JsonArray(3));

        // Act and Assert
        assertEquals(1, pNCServiceImpl.updateBenHistoryDetails(historyOBJ));
    }

    @Test
    void testUpdateBenHistoryDetails3() throws Exception {
        // Arrange
        JsonObject historyOBJ = new JsonObject();
        historyOBJ.addProperty("pastHistory", (String) null);

        // Act and Assert
        assertEquals(1, pNCServiceImpl.updateBenHistoryDetails(historyOBJ));
    }

    @Test
    void testUpdateBenHistoryDetails4() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateBenPastHistoryDetails(Mockito.<BenMedHistory>any())).thenReturn(1);

        JsonObject historyOBJ = new JsonObject();
        historyOBJ.add("pastHistory", new JsonObject());

        // Act
        int actualUpdateBenHistoryDetailsResult = pNCServiceImpl.updateBenHistoryDetails(historyOBJ);

        // Assert
        verify(commonNurseServiceImpl).updateBenPastHistoryDetails(isA(BenMedHistory.class));
        assertEquals(1, actualUpdateBenHistoryDetailsResult);
    }

    @Test
    void testUpdateBenHistoryDetails5() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateBenPastHistoryDetails(Mockito.<BenMedHistory>any())).thenReturn(0);

        JsonObject historyOBJ = new JsonObject();
        historyOBJ.add("pastHistory", new JsonObject());

        // Act
        int actualUpdateBenHistoryDetailsResult = pNCServiceImpl.updateBenHistoryDetails(historyOBJ);

        // Assert
        verify(commonNurseServiceImpl).updateBenPastHistoryDetails(isA(BenMedHistory.class));
        assertEquals(0, actualUpdateBenHistoryDetailsResult);
    }

    @Test
    void testUpdateBenVitalDetails() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateANCAnthropometryDetails(Mockito.<BenAnthropometryDetail>any())).thenReturn(1);
        when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any())).thenReturn(1);

        // Act
        int actualUpdateBenVitalDetailsResult = pNCServiceImpl.updateBenVitalDetails(new JsonObject());

        // Assert
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).updateANCPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1, actualUpdateBenVitalDetailsResult);
    }

    @Test
    void testUpdateBenVitalDetails2() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateANCAnthropometryDetails(Mockito.<BenAnthropometryDetail>any())).thenReturn(0);
        when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any())).thenReturn(1);

        // Act
        int actualUpdateBenVitalDetailsResult = pNCServiceImpl.updateBenVitalDetails(new JsonObject());

        // Assert
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).updateANCPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(0, actualUpdateBenVitalDetailsResult);
    }

    @Test
    void testUpdateBenVitalDetails3() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateANCAnthropometryDetails(Mockito.<BenAnthropometryDetail>any())).thenReturn(1);
        when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any())).thenReturn(0);

        // Act
        int actualUpdateBenVitalDetailsResult = pNCServiceImpl.updateBenVitalDetails(new JsonObject());

        // Assert
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).updateANCPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(0, actualUpdateBenVitalDetailsResult);
    }

    @Test
    void testUpdateBenVitalDetails4() throws Exception {
        // Arrange, Act and Assert
        assertEquals(1, pNCServiceImpl.updateBenVitalDetails(null));
    }

    @Test
    void testUpdateBenVitalDetails5() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateANCAnthropometryDetails(Mockito.<BenAnthropometryDetail>any())).thenReturn(1);
        when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any())).thenReturn(1);

        JsonObject vitalDetailsOBJ = new JsonObject();
        vitalDetailsOBJ.add("Property", new JsonArray(3));

        // Act
        int actualUpdateBenVitalDetailsResult = pNCServiceImpl.updateBenVitalDetails(vitalDetailsOBJ);

        // Assert
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).updateANCPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1, actualUpdateBenVitalDetailsResult);
    }

    @Test
    void testUpdateBenVitalDetails6() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateANCAnthropometryDetails(Mockito.<BenAnthropometryDetail>any())).thenReturn(1);
        when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any())).thenReturn(1);

        JsonObject vitalDetailsOBJ = new JsonObject();
        vitalDetailsOBJ.addProperty("Property", "42");

        // Act
        int actualUpdateBenVitalDetailsResult = pNCServiceImpl.updateBenVitalDetails(vitalDetailsOBJ);

        // Assert
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).updateANCPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1, actualUpdateBenVitalDetailsResult);
    }

    @Test
    void testUpdateBenVitalDetails7() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateANCAnthropometryDetails(Mockito.<BenAnthropometryDetail>any())).thenReturn(1);
        when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any())).thenReturn(1);

        JsonObject vitalDetailsOBJ = new JsonObject();
        Integer value = Integer.valueOf(1);
        vitalDetailsOBJ.addProperty("Property", value);

        // Act
        int actualUpdateBenVitalDetailsResult = pNCServiceImpl.updateBenVitalDetails(vitalDetailsOBJ);

        // Assert
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).updateANCPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1, actualUpdateBenVitalDetailsResult);
        assertSame(value, actualUpdateBenVitalDetailsResult);
    }

    @Test
    void testUpdateBenVitalDetails8() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateANCAnthropometryDetails(Mockito.<BenAnthropometryDetail>any())).thenReturn(1);
        when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any())).thenReturn(1);

        JsonObject vitalDetailsOBJ = new JsonObject();
        vitalDetailsOBJ.addProperty("Property", true);

        // Act
        int actualUpdateBenVitalDetailsResult = pNCServiceImpl.updateBenVitalDetails(vitalDetailsOBJ);

        // Assert
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).updateANCPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1, actualUpdateBenVitalDetailsResult);
    }

    @Test
    void testUpdateBenVitalDetails9() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateANCAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenThrow(new IEMRException("An error occurred"));

        // Act and Assert
        assertThrows(IEMRException.class, () -> pNCServiceImpl.updateBenVitalDetails(new JsonObject()));
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
    }

    @Test
    void testUpdateBenVitalDetails10() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateANCAnthropometryDetails(Mockito.<BenAnthropometryDetail>any())).thenReturn(1);
        when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any())).thenReturn(1);

        JsonObject vitalDetailsOBJ = new JsonObject();
        vitalDetailsOBJ.add("Property", null);

        // Act
        int actualUpdateBenVitalDetailsResult = pNCServiceImpl.updateBenVitalDetails(vitalDetailsOBJ);

        // Assert
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).updateANCPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1, actualUpdateBenVitalDetailsResult);
    }

    @Test
    void testUpdateBenExaminationDetails() throws Exception {
        // Arrange, Act and Assert
        assertEquals(1, pNCServiceImpl.updateBenExaminationDetails(new JsonObject()));
        assertEquals(1, pNCServiceImpl.updateBenExaminationDetails(null));
    }

    @Test
    void testUpdateBenExaminationDetails2() throws Exception {
        // Arrange
        JsonObject examinationDetailsOBJ = new JsonObject();
        examinationDetailsOBJ.add("Property", new JsonArray(3));

        // Act and Assert
        assertEquals(1, pNCServiceImpl.updateBenExaminationDetails(examinationDetailsOBJ));
    }

    @Test
    void testUpdateBenExaminationDetails3() throws Exception {
        // Arrange
        JsonObject examinationDetailsOBJ = new JsonObject();
        examinationDetailsOBJ.addProperty("generalExamination", (String) null);

        // Act and Assert
        assertEquals(1, pNCServiceImpl.updateBenExaminationDetails(examinationDetailsOBJ));
    }

    @Test
    void testUpdateBenExaminationDetails4() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updatePhyGeneralExamination(Mockito.<PhyGeneralExamination>any())).thenReturn(1);

        JsonObject examinationDetailsOBJ = new JsonObject();
        examinationDetailsOBJ.add("generalExamination", new JsonObject());

        // Act
        int actualUpdateBenExaminationDetailsResult = pNCServiceImpl.updateBenExaminationDetails(examinationDetailsOBJ);

        // Assert
        verify(commonNurseServiceImpl).updatePhyGeneralExamination(isA(PhyGeneralExamination.class));
        assertEquals(1, actualUpdateBenExaminationDetailsResult);
    }

    @Test
    void testUpdateBenExaminationDetails5() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updatePhyGeneralExamination(Mockito.<PhyGeneralExamination>any())).thenReturn(0);

        JsonObject examinationDetailsOBJ = new JsonObject();
        examinationDetailsOBJ.add("generalExamination", new JsonObject());

        // Act
        int actualUpdateBenExaminationDetailsResult = pNCServiceImpl.updateBenExaminationDetails(examinationDetailsOBJ);

        // Assert
        verify(commonNurseServiceImpl).updatePhyGeneralExamination(isA(PhyGeneralExamination.class));
        assertEquals(0, actualUpdateBenExaminationDetailsResult);
    }

    @Test
    void testUpdateBenPNCDetails() throws Exception {
        // Arrange, Act and Assert
        assertEquals(1, pNCServiceImpl.updateBenPNCDetails(new JsonObject()));
        assertEquals(1, pNCServiceImpl.updateBenPNCDetails(null));
    }

    @Test
    void testUpdateBenPNCDetails2() throws Exception {
        // Arrange
        JsonObject pncDetailsOBJ = new JsonObject();
        pncDetailsOBJ.add("Property", new JsonArray(3));

        // Act and Assert
        assertEquals(1, pNCServiceImpl.updateBenPNCDetails(pncDetailsOBJ));
    }

    @Test
    void testUpdateBenPNCDetails3() throws Exception {
        // Arrange
        JsonObject pncDetailsOBJ = new JsonObject();
        pncDetailsOBJ.add("42", new JsonArray(3));

        // Act and Assert
        assertEquals(1, pNCServiceImpl.updateBenPNCDetails(pncDetailsOBJ));
    }

    @Test
    void testUpdateBenPNCDetails4() throws Exception {
        // Arrange
        JsonObject pncDetailsOBJ = new JsonObject();
        pncDetailsOBJ.addProperty("PNCDetails", (String) null);

        // Act and Assert
        assertEquals(1, pNCServiceImpl.updateBenPNCDetails(pncDetailsOBJ));
    }

    @Test
    void testUpdateBenPNCDetails5() throws Exception {
        // Arrange
        when(pNCNurseServiceImpl.updateBenPNCCareDetails(Mockito.<PNCCare>any())).thenReturn(1);

        JsonObject pncDetailsOBJ = new JsonObject();
        pncDetailsOBJ.add("PNCDetails", new JsonObject());

        // Act
        int actualUpdateBenPNCDetailsResult = pNCServiceImpl.updateBenPNCDetails(pncDetailsOBJ);

        // Assert
        verify(pNCNurseServiceImpl).updateBenPNCCareDetails(isA(PNCCare.class));
        assertEquals(1, actualUpdateBenPNCDetailsResult);
    }

    @Test
    void testGetBenPNCNurseData() throws IEMRException {
        (new PNCServiceImpl()).getBenPNCNurseData(1L, 1L);
    }

    @Test
    void testGetBenCaseRecordFromDoctorPNC() {
        // Arrange
        when(commonDoctorServiceImpl.getFindingsDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Findings Details");
        when(pNCDoctorServiceImpl.getPNCDiagnosisDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenThrow(new RuntimeException("findings"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> pNCServiceImpl.getBenCaseRecordFromDoctorPNC(1L, 1L));
        verify(commonDoctorServiceImpl).getFindingsDetails(isA(Long.class), isA(Long.class));
        verify(pNCDoctorServiceImpl).getPNCDiagnosisDetails(isA(Long.class), isA(Long.class));
    }

    @Test
    void testGetBenCaseRecordFromDoctorPNC2() {
        // Arrange
        when(commonDoctorServiceImpl.getFindingsDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Findings Details");
        when(commonDoctorServiceImpl.getInvestigationDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Investigation Details");
        when(commonDoctorServiceImpl.getPrescribedDrugs(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Prescribed Drugs");
        when(commonDoctorServiceImpl.getReferralDetails(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Boolean>any()))
                .thenReturn("Referral Details");
        when(commonNurseServiceImpl.getGraphicalTrendData(Mockito.<Long>any(), Mockito.<String>any()))
                .thenReturn(new HashMap<>());
        when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Last 3 Archived Test Visit List");
        when(labTechnicianServiceImpl.getLabResultDataForBen(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());
        when(pNCDoctorServiceImpl.getPNCDiagnosisDetails(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(null);

        // Act
        String actualBenCaseRecordFromDoctorPNC = pNCServiceImpl.getBenCaseRecordFromDoctorPNC(1L, 1L);

        // Assert
        verify(commonDoctorServiceImpl).getFindingsDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getInvestigationDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getPrescribedDrugs(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getReferralDetails(isA(Long.class), isA(Long.class), isA(Boolean.class));
        verify(commonNurseServiceImpl).getGraphicalTrendData(isA(Long.class), eq("pnc"));
        verify(labTechnicianServiceImpl).getLabResultDataForBen(isA(Long.class), isA(Long.class));
        verify(labTechnicianServiceImpl).getLast_3_ArchivedTestVisitList(isA(Long.class), isA(Long.class));
        verify(pNCDoctorServiceImpl).getPNCDiagnosisDetails(isA(Long.class), isA(Long.class));
        assertEquals("{Refer=Referral Details, prescription=Prescribed Drugs, findings=Findings Details, LabReport=[],"
                + " diagnosis=null, investigation=Investigation Details, ArchivedVisitcodeForLabResult=Last 3 Archived"
                + " Test Visit List, GraphData={}}", actualBenCaseRecordFromDoctorPNC);
    }

    @Test
    void testGetBenCaseRecordFromDoctorPNC3() {
        // Arrange
        when(commonDoctorServiceImpl.getFindingsDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Findings Details");
        when(commonDoctorServiceImpl.getInvestigationDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Investigation Details");
        when(commonDoctorServiceImpl.getPrescribedDrugs(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Prescribed Drugs");
        when(commonDoctorServiceImpl.getReferralDetails(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Boolean>any()))
                .thenReturn("Referral Details");
        when(commonNurseServiceImpl.getGraphicalTrendData(Mockito.<Long>any(), Mockito.<String>any()))
                .thenReturn(new HashMap<>());
        when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Last 3 Archived Test Visit List");
        when(labTechnicianServiceImpl.getLabResultDataForBen(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());
        when(pNCDoctorServiceImpl.getPNCDiagnosisDetails(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn("");

        // Act
        String actualBenCaseRecordFromDoctorPNC = pNCServiceImpl.getBenCaseRecordFromDoctorPNC(1L, 1L);

        // Assert
        verify(commonDoctorServiceImpl).getFindingsDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getInvestigationDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getPrescribedDrugs(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getReferralDetails(isA(Long.class), isA(Long.class), isA(Boolean.class));
        verify(commonNurseServiceImpl).getGraphicalTrendData(isA(Long.class), eq("pnc"));
        verify(labTechnicianServiceImpl).getLabResultDataForBen(isA(Long.class), isA(Long.class));
        verify(labTechnicianServiceImpl).getLast_3_ArchivedTestVisitList(isA(Long.class), isA(Long.class));
        verify(pNCDoctorServiceImpl).getPNCDiagnosisDetails(isA(Long.class), isA(Long.class));
        assertEquals("{Refer=Referral Details, prescription=Prescribed Drugs, findings=Findings Details, LabReport=[],"
                + " diagnosis=, investigation=Investigation Details, ArchivedVisitcodeForLabResult=Last 3 Archived Test"
                + " Visit List, GraphData={}}", actualBenCaseRecordFromDoctorPNC);
    }

    @Test
    void testUpdatePNCDoctorData() throws Exception {
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
        when(commonNurseServiceImpl.updatePrescription(Mockito.<PrescriptionDetail>any())).thenReturn(1);

        // Act
        Long actualUpdatePNCDoctorDataResult = pNCServiceImpl.updatePNCDoctorData(new JsonObject(), "JaneDoe");

        // Assert
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataUpdate(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonNurseServiceImpl).updatePrescription(isNull());
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
        assertEquals(1L, actualUpdatePNCDoctorDataResult.longValue());
    }

    @Test
    void testUpdatePNCDoctorData2() throws Exception {
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
        when(commonNurseServiceImpl.updatePrescription(Mockito.<PrescriptionDetail>any()))
                .thenThrow(new RuntimeException("investigation"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> pNCServiceImpl.updatePNCDoctorData(new JsonObject(), "JaneDoe"));
        verify(commonNurseServiceImpl).updatePrescription(isNull());
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
    }

    @Test
    void testUpdatePNCDoctorData3() throws Exception {
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
        when(commonNurseServiceImpl.updatePrescription(Mockito.<PrescriptionDetail>any())).thenReturn(1);

        // Act
        Long actualUpdatePNCDoctorDataResult = pNCServiceImpl.updatePNCDoctorData(new JsonObject(), "JaneDoe");

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
                isNull(), isNull(), eq("Mock for Timestamp, hashCode: 2003939295"), isNull(), eq("JaneDoe"));
        assertEquals(1L, actualUpdatePNCDoctorDataResult.longValue());
    }

    @Test
    void testUpdatePNCDoctorData4() throws Exception {
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
        when(commonNurseServiceImpl.updatePrescription(Mockito.<PrescriptionDetail>any())).thenReturn(1);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> pNCServiceImpl.updatePNCDoctorData(new JsonObject(), "JaneDoe"));
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
    }

    @Test
    void testUpdatePNCDoctorData5() throws Exception {
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
        Long actualUpdatePNCDoctorDataResult = pNCServiceImpl.updatePNCDoctorData(null, "JaneDoe");

        // Assert
        verify(teleconsultationRequestOBJ).setAllocationDate(isA(Timestamp.class));
        verify(teleconsultationRequestOBJ).setFromTime(eq("jane.doe@example.org"));
        verify(teleconsultationRequestOBJ).setSpecializationID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setTmRequestID(isA(Long.class));
        verify(teleconsultationRequestOBJ).setToTime(eq("To Time"));
        verify(teleconsultationRequestOBJ).setUserID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setWalkIn(isA(Boolean.class));
        assertNull(actualUpdatePNCDoctorDataResult);
    }

    @Test
    void testGettersAndSetters() {
        PNCServiceImpl pncServiceImpl = new PNCServiceImpl();

        // Act
        pncServiceImpl.setCommonBenStatusFlowServiceImpl(new CommonBenStatusFlowServiceImpl());
        pncServiceImpl.setCommonDoctorServiceImpl(new CommonDoctorServiceImpl());
        pncServiceImpl.setCommonNurseServiceImpl(new CommonNurseServiceImpl());
        pncServiceImpl.setLabTechnicianServiceImpl(new LabTechnicianServiceImpl());
        pncServiceImpl.setPncDoctorServiceImpl(new PNCDoctorServiceImpl());
        pncServiceImpl.setPncNurseServiceImpl(new PNCNurseServiceImpl());
    }
}
