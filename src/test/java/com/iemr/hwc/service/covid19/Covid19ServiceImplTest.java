package com.iemr.hwc.service.covid19;

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
import com.iemr.hwc.data.anc.WrapperChildOptionalVaccineDetail;
import com.iemr.hwc.data.anc.WrapperComorbidCondDetails;
import com.iemr.hwc.data.anc.WrapperFemaleObstetricHistory;
import com.iemr.hwc.data.anc.WrapperImmunizationHistory;
import com.iemr.hwc.data.anc.WrapperMedicationHistory;
import com.iemr.hwc.data.covid19.Covid19BenFeedback;
import com.iemr.hwc.data.location.Districts;
import com.iemr.hwc.data.location.States;
import com.iemr.hwc.data.nurse.BenAnthropometryDetail;
import com.iemr.hwc.data.nurse.BenPhysicalVitalDetail;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.provider.ProviderServiceMapping;
import com.iemr.hwc.data.quickConsultation.PrescriptionDetail;
import com.iemr.hwc.repo.location.ZoneDistrictMapping;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.nurse.CDSSRepo;
import com.iemr.hwc.repo.nurse.covid19.Covid19BenFeedbackRepo;
import com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonServiceImpl;
import com.iemr.hwc.service.labtechnician.LabTechnicianServiceImpl;
import com.iemr.hwc.service.tele_consultation.SMSGatewayServiceImpl;
import com.iemr.hwc.service.tele_consultation.TeleConsultationServiceImpl;
import com.iemr.hwc.utils.exception.IEMRException;

@ExtendWith(MockitoExtension.class)
class Covid19ServiceImplTest {
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
    private Covid19BenFeedbackRepo covid19BenFeedbackRepo;

    @InjectMocks
    private Covid19ServiceImpl covid19ServiceImpl;

    @Mock
    private LabTechnicianServiceImpl labTechnicianServiceImpl;

    @Mock
    private PrescriptionDetailRepo prescriptionDetailRepo;

    @Mock
    private SMSGatewayServiceImpl sMSGatewayServiceImpl;

    @Mock
    private TeleConsultationServiceImpl teleConsultationServiceImpl;

    @Test
    void testSaveCovid19NurseData() throws Exception {
        // Arrange, Act and Assert
        assertThrows(Exception.class, () -> covid19ServiceImpl.saveCovid19NurseData(new JsonObject(), "JaneDoe"));
        assertThrows(Exception.class, () -> covid19ServiceImpl.saveCovid19NurseData(null, "JaneDoe"));
    }

    @Test
    void testDeleteVisitDetails() throws Exception {
        // Arrange
        when(benVisitDetailRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1L);
        when(cDSSRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(covid19BenFeedbackRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.add("visitDetails", new JsonArray(3));

        // Act
        covid19ServiceImpl.deleteVisitDetails(requestOBJ);

        // Assert
        verify(benVisitDetailRepo).deleteVisitDetails(isA(Long.class));
        verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
        verify(cDSSRepo).deleteVisitDetails(isA(Long.class));
        verify(covid19BenFeedbackRepo).deleteVisitDetails(isA(Long.class));
    }

    @Test
    void testDeleteVisitDetails2() throws Exception {
        // Arrange
        when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1L);
        when(cDSSRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(covid19BenFeedbackRepo.deleteVisitDetails(Mockito.<Long>any()))
                .thenThrow(new RuntimeException("visitDetails"));

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.add("visitDetails", new JsonArray(3));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> covid19ServiceImpl.deleteVisitDetails(requestOBJ));
        verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
        verify(cDSSRepo).deleteVisitDetails(isA(Long.class));
        verify(covid19BenFeedbackRepo).deleteVisitDetails(isA(Long.class));
    }

    @Test
    void testDeleteVisitDetails3() throws Exception {
        // Arrange
        when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(null);

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.add("visitDetails", new JsonArray(3));

        // Act
        covid19ServiceImpl.deleteVisitDetails(requestOBJ);

        // Assert
        verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
    }

    @Test
    void testDeleteVisitDetails4() throws Exception {
        // Arrange
        when(benVisitDetailRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1L);
        when(cDSSRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(covid19BenFeedbackRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.add("Property", new JsonArray(3));
        requestOBJ.add("visitDetails", new JsonArray(3));

        // Act
        covid19ServiceImpl.deleteVisitDetails(requestOBJ);

        // Assert
        verify(benVisitDetailRepo).deleteVisitDetails(isA(Long.class));
        verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
        verify(cDSSRepo).deleteVisitDetails(isA(Long.class));
        verify(covid19BenFeedbackRepo).deleteVisitDetails(isA(Long.class));
    }

    @Test
    void testDeleteVisitDetails5() throws Exception {
        // Arrange
        when(benVisitDetailRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1L);
        when(cDSSRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(covid19BenFeedbackRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.addProperty("Property", "visitDetails");
        requestOBJ.add("visitDetails", new JsonArray(3));

        // Act
        covid19ServiceImpl.deleteVisitDetails(requestOBJ);

        // Assert
        verify(benVisitDetailRepo).deleteVisitDetails(isA(Long.class));
        verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
        verify(cDSSRepo).deleteVisitDetails(isA(Long.class));
        verify(covid19BenFeedbackRepo).deleteVisitDetails(isA(Long.class));
    }

    @Test
    void testDeleteVisitDetails6() throws Exception {
        // Arrange
        when(benVisitDetailRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1L);
        when(cDSSRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(covid19BenFeedbackRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.addProperty("Property", Integer.valueOf(1));
        requestOBJ.add("visitDetails", new JsonArray(3));

        // Act
        covid19ServiceImpl.deleteVisitDetails(requestOBJ);

        // Assert
        verify(benVisitDetailRepo).deleteVisitDetails(isA(Long.class));
        verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
        verify(cDSSRepo).deleteVisitDetails(isA(Long.class));
        verify(covid19BenFeedbackRepo).deleteVisitDetails(isA(Long.class));
    }

    @Test
    void testDeleteVisitDetails7() throws Exception {
        // Arrange
        when(benVisitDetailRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1L);
        when(cDSSRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(covid19BenFeedbackRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.addProperty("Property", false);
        requestOBJ.add("visitDetails", new JsonArray(3));

        // Act
        covid19ServiceImpl.deleteVisitDetails(requestOBJ);

        // Assert
        verify(benVisitDetailRepo).deleteVisitDetails(isA(Long.class));
        verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
        verify(cDSSRepo).deleteVisitDetails(isA(Long.class));
        verify(covid19BenFeedbackRepo).deleteVisitDetails(isA(Long.class));
    }

    @Test
    void testDeleteVisitDetails8() throws Exception {
        // Arrange
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.add("visitDetails", value);

        // Act
        covid19ServiceImpl.deleteVisitDetails(requestOBJ);

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
        assertThrows(RuntimeException.class, () -> covid19ServiceImpl.deleteVisitDetails(requestOBJ));
        verify(value).isJsonNull();
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
        assertTrue(covid19ServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass).isEmpty());
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
        assertTrue(covid19ServiceImpl.saveBenVisitDetails(null, nurseUtilityClass).isEmpty());
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
        assertTrue(covid19ServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass).isEmpty());
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
        assertTrue(covid19ServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass).isEmpty());
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
        Map<String, Long> actualSaveBenVisitDetailsResult = covid19ServiceImpl.saveBenVisitDetails(visitDetailsOBJ,
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
    void testSaveBenCovid19HistoryDetails() throws Exception {
        // Arrange, Act and Assert
        assertEquals(1L, covid19ServiceImpl.saveBenCovid19HistoryDetails(new JsonObject(), 1L, 1L).longValue());
        assertEquals(1L, covid19ServiceImpl.saveBenCovid19HistoryDetails(null, 1L, 1L).longValue());
    }

    @Test
    void testSaveBenCovid19HistoryDetails2() throws Exception {
        // Arrange
        JsonObject ncdCareHistoryOBJ = new JsonObject();
        ncdCareHistoryOBJ.add("Property", new JsonArray(3));

        // Act and Assert
        assertEquals(1L, covid19ServiceImpl.saveBenCovid19HistoryDetails(ncdCareHistoryOBJ, 1L, 1L).longValue());
    }

    @Test
    void testSaveBenCovid19HistoryDetails3() throws Exception {
        // Arrange
        JsonObject ncdCareHistoryOBJ = new JsonObject();
        ncdCareHistoryOBJ.addProperty("pastHistory", (String) null);

        // Act and Assert
        assertEquals(1L, covid19ServiceImpl.saveBenCovid19HistoryDetails(ncdCareHistoryOBJ, 1L, 1L).longValue());
    }

    @Test
    void testSaveBenCovid19VitalDetails() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(1L);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(1L);

        // Act
        Long actualSaveBenCovid19VitalDetailsResult = covid19ServiceImpl.saveBenCovid19VitalDetails(new JsonObject(), 1L,
                1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1L, actualSaveBenCovid19VitalDetailsResult.longValue());
    }

    @Test
    void testSaveBenCovid19VitalDetails2() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(0L);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(1L);

        // Act
        Long actualSaveBenCovid19VitalDetailsResult = covid19ServiceImpl.saveBenCovid19VitalDetails(new JsonObject(), 1L,
                1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertNull(actualSaveBenCovid19VitalDetailsResult);
    }

    @Test
    void testSaveBenCovid19VitalDetails3() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(null);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(1L);

        // Act
        Long actualSaveBenCovid19VitalDetailsResult = covid19ServiceImpl.saveBenCovid19VitalDetails(new JsonObject(), 1L,
                1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertNull(actualSaveBenCovid19VitalDetailsResult);
    }

    @Test
    void testSaveBenCovid19VitalDetails4() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(1L);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(0L);

        // Act
        Long actualSaveBenCovid19VitalDetailsResult = covid19ServiceImpl.saveBenCovid19VitalDetails(new JsonObject(), 1L,
                1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertNull(actualSaveBenCovid19VitalDetailsResult);
    }

    @Test
    void testSaveBenCovid19VitalDetails5() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(1L);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(null);

        // Act
        Long actualSaveBenCovid19VitalDetailsResult = covid19ServiceImpl.saveBenCovid19VitalDetails(new JsonObject(), 1L,
                1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertNull(actualSaveBenCovid19VitalDetailsResult);
    }

    @Test
    void testSaveBenCovid19VitalDetails6() throws Exception {
        // Arrange, Act and Assert
        assertNull(covid19ServiceImpl.saveBenCovid19VitalDetails(null, 1L, 1L));
    }

    @Test
    void testSaveBenCovid19VitalDetails7() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(1L);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(1L);

        JsonObject vitalDetailsOBJ = new JsonObject();
        vitalDetailsOBJ.add("Property", new JsonArray(3));

        // Act
        Long actualSaveBenCovid19VitalDetailsResult = covid19ServiceImpl.saveBenCovid19VitalDetails(vitalDetailsOBJ, 1L,
                1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1L, actualSaveBenCovid19VitalDetailsResult.longValue());
    }

    @Test
    void testSaveBenCovid19VitalDetails8() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(1L);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(1L);

        JsonObject vitalDetailsOBJ = new JsonObject();
        vitalDetailsOBJ.addProperty("Property", "42");

        // Act
        Long actualSaveBenCovid19VitalDetailsResult = covid19ServiceImpl.saveBenCovid19VitalDetails(vitalDetailsOBJ, 1L,
                1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1L, actualSaveBenCovid19VitalDetailsResult.longValue());
    }

    @Test
    void testSaveBenCovid19VitalDetails9() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(1L);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(1L);

        JsonObject vitalDetailsOBJ = new JsonObject();
        vitalDetailsOBJ.addProperty("Property", Integer.valueOf(1));

        // Act
        Long actualSaveBenCovid19VitalDetailsResult = covid19ServiceImpl.saveBenCovid19VitalDetails(vitalDetailsOBJ, 1L,
                1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1L, actualSaveBenCovid19VitalDetailsResult.longValue());
    }

    @Test
    void testSaveBenCovid19VitalDetails10() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenReturn(1L);
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
                .thenReturn(1L);

        JsonObject vitalDetailsOBJ = new JsonObject();
        vitalDetailsOBJ.addProperty("Property", true);

        // Act
        Long actualSaveBenCovid19VitalDetailsResult = covid19ServiceImpl.saveBenCovid19VitalDetails(vitalDetailsOBJ, 1L,
                1L);

        // Assert
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1L, actualSaveBenCovid19VitalDetailsResult.longValue());
    }

    @Test
    void testSaveBenCovid19VitalDetails11() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
                .thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> covid19ServiceImpl.saveBenCovid19VitalDetails(new JsonObject(), 1L, 1L));
        verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
    }

    @Test
    void testGetBenVisitDetailsFrmNurseCovid19() {
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
        when(commonNurseServiceImpl.getCSVisitDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(beneficiaryVisitDetail);
        when(covid19BenFeedbackRepo.findByBeneficiaryRegIDAndVisitCode(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenThrow(new RuntimeException("covid19NurseVisitDetail"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> covid19ServiceImpl.getBenVisitDetailsFrmNurseCovid19(1L, 1L));
        verify(covid19BenFeedbackRepo).findByBeneficiaryRegIDAndVisitCode(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getCSVisitDetails(isA(Long.class), isA(Long.class));
    }

    @Test
    void testGetBenCovid19HistoryDetails() throws IEMRException {
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
        covid19ServiceImpl.getBenCovid19HistoryDetails(1L, 1L);
    }

    @Test
    void testGetBeneficiaryVitalDetails() {
        // Arrange
        when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Beneficiary Physical Anthropometry Details");
        when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Beneficiary Physical Vital Details");

        // Act
        String actualBeneficiaryVitalDetails = covid19ServiceImpl.getBeneficiaryVitalDetails(1L, 1L);

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
        assertThrows(RuntimeException.class, () -> covid19ServiceImpl.getBeneficiaryVitalDetails(1L, 1L));
        verify(commonNurseServiceImpl).getBeneficiaryPhysicalAnthropometryDetails(isA(Long.class), isA(Long.class));
    }

    @Test
    void testGetBeneficiaryCdssDetails() {
        // Arrange
        when(commonNurseServiceImpl.getBenCdssDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Ben Cdss Details");

        // Act
        String actualBeneficiaryCdssDetails = covid19ServiceImpl.getBeneficiaryCdssDetails(1L, 1L);

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
        assertThrows(RuntimeException.class, () -> covid19ServiceImpl.getBeneficiaryCdssDetails(1L, 1L));
        verify(commonNurseServiceImpl).getBenCdssDetails(isA(Long.class), isA(Long.class));
    }

    @Test
    void testSaveCovidDetails() {
        // Arrange
        Covid19BenFeedback covid19BenFeedback = new Covid19BenFeedback();
        covid19BenFeedback.setBenCallID(1L);
        covid19BenFeedback.setBenMedHistoryID(1L);
        covid19BenFeedback.setBeneficiaryRegID(1L);
        covid19BenFeedback.setContactStatus(new String[]{"Contact Status"});
        covid19BenFeedback.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        covid19BenFeedback.setCreatedDate(mock(Timestamp.class));
        covid19BenFeedback.setDeleted(true);
        covid19BenFeedback.setFromCityInter(1);
        covid19BenFeedback.setFromCountryInter(3);
        covid19BenFeedback.setFromDistrictDom(1);
        covid19BenFeedback.setFromStateDom(1);
        covid19BenFeedback.setFromSubDistrictDom(1);
        covid19BenFeedback.setFromcityInter(1);
        covid19BenFeedback.setLastModDate(mock(Timestamp.class));
        covid19BenFeedback.setMedical_consultation(true);
        covid19BenFeedback.setModeOfTravelDomestic("Mode Of Travel Domestic");
        covid19BenFeedback.setModeOfTravelInter("Mode Of Travel Inter");
        covid19BenFeedback.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        covid19BenFeedback.setProcessed("Processed");
        covid19BenFeedback.setProviderServiceMapID(1);
        covid19BenFeedback.setRecommendation(new ArrayList<>());
        covid19BenFeedback.setRecommendation_db("Recommendation db");
        covid19BenFeedback.setSuspectedStatus(true);
        covid19BenFeedback.setSuspectedStatusUI("Suspected Status UI");
        covid19BenFeedback.setSymptom(new String[]{"Symptom"});
        covid19BenFeedback.setSymptoms(new String[]{"Symptoms"});
        covid19BenFeedback.setSymptoms_db("Symptoms db");
        covid19BenFeedback.setToCityInter(1);
        covid19BenFeedback.setToCountryInter(3);
        covid19BenFeedback.setToDistrictDom(1);
        covid19BenFeedback.setToStateDom(1);
        covid19BenFeedback.setToSubDistrictDom(1);
        covid19BenFeedback.setTocityInter(1);
        covid19BenFeedback.setTravelList(new String[]{"Travel List"});
        covid19BenFeedback.setTravelStatus(true);
        covid19BenFeedback.setTravelType("Travel Type");
        covid19BenFeedback.setVanID(1);
        covid19BenFeedback.setVanSerialNo(1L);
        covid19BenFeedback.setVisitCode(1L);
        covid19BenFeedback.setcOVID19ID(1L);
        covid19BenFeedback.setcOVID19_contact_history("C OVID19 contact history");
        when(covid19BenFeedbackRepo.save(Mockito.<Covid19BenFeedback>any())).thenReturn(covid19BenFeedback);

        Covid19BenFeedback covid19BenFeedbackOBJ = new Covid19BenFeedback();
        covid19BenFeedbackOBJ.setBenCallID(1L);
        covid19BenFeedbackOBJ.setBenMedHistoryID(1L);
        covid19BenFeedbackOBJ.setBeneficiaryRegID(1L);
        covid19BenFeedbackOBJ.setContactStatus(new String[]{"Contact Status"});
        covid19BenFeedbackOBJ.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        covid19BenFeedbackOBJ.setCreatedDate(mock(Timestamp.class));
        covid19BenFeedbackOBJ.setDeleted(true);
        covid19BenFeedbackOBJ.setFromCityInter(1);
        covid19BenFeedbackOBJ.setFromCountryInter(3);
        covid19BenFeedbackOBJ.setFromDistrictDom(1);
        covid19BenFeedbackOBJ.setFromStateDom(1);
        covid19BenFeedbackOBJ.setFromSubDistrictDom(1);
        covid19BenFeedbackOBJ.setFromcityInter(1);
        covid19BenFeedbackOBJ.setLastModDate(mock(Timestamp.class));
        covid19BenFeedbackOBJ.setMedical_consultation(true);
        covid19BenFeedbackOBJ.setModeOfTravelDomestic("Mode Of Travel Domestic");
        covid19BenFeedbackOBJ.setModeOfTravelInter("Mode Of Travel Inter");
        covid19BenFeedbackOBJ.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        covid19BenFeedbackOBJ.setProcessed("Processed");
        covid19BenFeedbackOBJ.setProviderServiceMapID(1);
        covid19BenFeedbackOBJ.setRecommendation(new ArrayList<>());
        covid19BenFeedbackOBJ.setRecommendation_db("Recommendation db");
        covid19BenFeedbackOBJ.setSuspectedStatus(true);
        covid19BenFeedbackOBJ.setSuspectedStatusUI("Suspected Status UI");
        covid19BenFeedbackOBJ.setSymptom(new String[]{"Symptom"});
        covid19BenFeedbackOBJ.setSymptoms(new String[]{"Symptoms"});
        covid19BenFeedbackOBJ.setSymptoms_db("Symptoms db");
        covid19BenFeedbackOBJ.setToCityInter(1);
        covid19BenFeedbackOBJ.setToCountryInter(3);
        covid19BenFeedbackOBJ.setToDistrictDom(1);
        covid19BenFeedbackOBJ.setToStateDom(1);
        covid19BenFeedbackOBJ.setToSubDistrictDom(1);
        covid19BenFeedbackOBJ.setTocityInter(1);
        covid19BenFeedbackOBJ.setTravelList(new String[]{"Travel List"});
        covid19BenFeedbackOBJ.setTravelStatus(true);
        covid19BenFeedbackOBJ.setTravelType("Travel Type");
        covid19BenFeedbackOBJ.setVanID(1);
        covid19BenFeedbackOBJ.setVanSerialNo(1L);
        covid19BenFeedbackOBJ.setVisitCode(1L);
        covid19BenFeedbackOBJ.setcOVID19ID(1L);
        covid19BenFeedbackOBJ.setcOVID19_contact_history("C OVID19 contact history");

        // Act
        Integer actualSaveCovidDetailsResult = covid19ServiceImpl.saveCovidDetails(covid19BenFeedbackOBJ);

        // Assert
        verify(covid19BenFeedbackRepo).save(isA(Covid19BenFeedback.class));
        assertEquals("Symptoms", covid19BenFeedbackOBJ.getSymptoms_db());
        assertEquals("Travel List", covid19BenFeedbackOBJ.getTravelType());
        assertEquals(1, actualSaveCovidDetailsResult.intValue());
    }

    @Test
    void testSaveCovidDetails2() {
        // Arrange
        Covid19BenFeedback covid19BenFeedback = mock(Covid19BenFeedback.class);
        when(covid19BenFeedback.getcOVID19ID()).thenReturn(1L);
        doNothing().when(covid19BenFeedback).setBenCallID(Mockito.<Long>any());
        doNothing().when(covid19BenFeedback).setBenMedHistoryID(Mockito.<Long>any());
        doNothing().when(covid19BenFeedback).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(covid19BenFeedback).setContactStatus(Mockito.<String[]>any());
        doNothing().when(covid19BenFeedback).setCreatedBy(Mockito.<String>any());
        doNothing().when(covid19BenFeedback).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(covid19BenFeedback).setDeleted(Mockito.<Boolean>any());
        doNothing().when(covid19BenFeedback).setFromCityInter(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setFromCountryInter(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setFromDistrictDom(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setFromStateDom(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setFromSubDistrictDom(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setFromcityInter(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(covid19BenFeedback).setMedical_consultation(Mockito.<Boolean>any());
        doNothing().when(covid19BenFeedback).setModeOfTravelDomestic(Mockito.<String>any());
        doNothing().when(covid19BenFeedback).setModeOfTravelInter(Mockito.<String>any());
        doNothing().when(covid19BenFeedback).setModifiedBy(Mockito.<String>any());
        doNothing().when(covid19BenFeedback).setProcessed(Mockito.<String>any());
        doNothing().when(covid19BenFeedback).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setRecommendation(Mockito.<ArrayList<String[]>>any());
        doNothing().when(covid19BenFeedback).setRecommendation_db(Mockito.<String>any());
        doNothing().when(covid19BenFeedback).setSuspectedStatus(Mockito.<Boolean>any());
        doNothing().when(covid19BenFeedback).setSuspectedStatusUI(Mockito.<String>any());
        doNothing().when(covid19BenFeedback).setSymptom(Mockito.<String[]>any());
        doNothing().when(covid19BenFeedback).setSymptoms(Mockito.<String[]>any());
        doNothing().when(covid19BenFeedback).setSymptoms_db(Mockito.<String>any());
        doNothing().when(covid19BenFeedback).setToCityInter(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setToCountryInter(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setToDistrictDom(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setToStateDom(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setToSubDistrictDom(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setTocityInter(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setTravelList(Mockito.<String[]>any());
        doNothing().when(covid19BenFeedback).setTravelStatus(Mockito.<Boolean>any());
        doNothing().when(covid19BenFeedback).setTravelType(Mockito.<String>any());
        doNothing().when(covid19BenFeedback).setVanID(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(covid19BenFeedback).setVisitCode(Mockito.<Long>any());
        doNothing().when(covid19BenFeedback).setcOVID19ID(Mockito.<Long>any());
        doNothing().when(covid19BenFeedback).setcOVID19_contact_history(Mockito.<String>any());
        covid19BenFeedback.setBenCallID(1L);
        covid19BenFeedback.setBenMedHistoryID(1L);
        covid19BenFeedback.setBeneficiaryRegID(1L);
        covid19BenFeedback.setContactStatus(new String[]{"Contact Status"});
        covid19BenFeedback.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        covid19BenFeedback.setCreatedDate(mock(Timestamp.class));
        covid19BenFeedback.setDeleted(true);
        covid19BenFeedback.setFromCityInter(1);
        covid19BenFeedback.setFromCountryInter(3);
        covid19BenFeedback.setFromDistrictDom(1);
        covid19BenFeedback.setFromStateDom(1);
        covid19BenFeedback.setFromSubDistrictDom(1);
        covid19BenFeedback.setFromcityInter(1);
        covid19BenFeedback.setLastModDate(mock(Timestamp.class));
        covid19BenFeedback.setMedical_consultation(true);
        covid19BenFeedback.setModeOfTravelDomestic("Mode Of Travel Domestic");
        covid19BenFeedback.setModeOfTravelInter("Mode Of Travel Inter");
        covid19BenFeedback.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        covid19BenFeedback.setProcessed("Processed");
        covid19BenFeedback.setProviderServiceMapID(1);
        covid19BenFeedback.setRecommendation(new ArrayList<>());
        covid19BenFeedback.setRecommendation_db("Recommendation db");
        covid19BenFeedback.setSuspectedStatus(true);
        covid19BenFeedback.setSuspectedStatusUI("Suspected Status UI");
        covid19BenFeedback.setSymptom(new String[]{"Symptom"});
        covid19BenFeedback.setSymptoms(new String[]{"Symptoms"});
        covid19BenFeedback.setSymptoms_db("Symptoms db");
        covid19BenFeedback.setToCityInter(1);
        covid19BenFeedback.setToCountryInter(3);
        covid19BenFeedback.setToDistrictDom(1);
        covid19BenFeedback.setToStateDom(1);
        covid19BenFeedback.setToSubDistrictDom(1);
        covid19BenFeedback.setTocityInter(1);
        covid19BenFeedback.setTravelList(new String[]{"Travel List"});
        covid19BenFeedback.setTravelStatus(true);
        covid19BenFeedback.setTravelType("Travel Type");
        covid19BenFeedback.setVanID(1);
        covid19BenFeedback.setVanSerialNo(1L);
        covid19BenFeedback.setVisitCode(1L);
        covid19BenFeedback.setcOVID19ID(1L);
        covid19BenFeedback.setcOVID19_contact_history("C OVID19 contact history");
        when(covid19BenFeedbackRepo.save(Mockito.<Covid19BenFeedback>any())).thenReturn(covid19BenFeedback);

        Covid19BenFeedback covid19BenFeedbackOBJ = new Covid19BenFeedback();
        covid19BenFeedbackOBJ.setBenCallID(1L);
        covid19BenFeedbackOBJ.setBenMedHistoryID(1L);
        covid19BenFeedbackOBJ.setBeneficiaryRegID(1L);
        covid19BenFeedbackOBJ.setContactStatus(new String[]{"Contact Status"});
        covid19BenFeedbackOBJ.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        covid19BenFeedbackOBJ.setCreatedDate(mock(Timestamp.class));
        covid19BenFeedbackOBJ.setDeleted(true);
        covid19BenFeedbackOBJ.setFromCityInter(1);
        covid19BenFeedbackOBJ.setFromCountryInter(3);
        covid19BenFeedbackOBJ.setFromDistrictDom(1);
        covid19BenFeedbackOBJ.setFromStateDom(1);
        covid19BenFeedbackOBJ.setFromSubDistrictDom(1);
        covid19BenFeedbackOBJ.setFromcityInter(1);
        covid19BenFeedbackOBJ.setLastModDate(mock(Timestamp.class));
        covid19BenFeedbackOBJ.setMedical_consultation(true);
        covid19BenFeedbackOBJ.setModeOfTravelDomestic("Mode Of Travel Domestic");
        covid19BenFeedbackOBJ.setModeOfTravelInter("Mode Of Travel Inter");
        covid19BenFeedbackOBJ.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        covid19BenFeedbackOBJ.setProcessed("Processed");
        covid19BenFeedbackOBJ.setProviderServiceMapID(1);
        covid19BenFeedbackOBJ.setRecommendation(new ArrayList<>());
        covid19BenFeedbackOBJ.setRecommendation_db("Recommendation db");
        covid19BenFeedbackOBJ.setSuspectedStatus(true);
        covid19BenFeedbackOBJ.setSuspectedStatusUI("Suspected Status UI");
        covid19BenFeedbackOBJ.setSymptom(new String[]{"Symptom"});
        covid19BenFeedbackOBJ.setSymptoms(new String[]{"Symptoms"});
        covid19BenFeedbackOBJ.setSymptoms_db("Symptoms db");
        covid19BenFeedbackOBJ.setToCityInter(1);
        covid19BenFeedbackOBJ.setToCountryInter(3);
        covid19BenFeedbackOBJ.setToDistrictDom(1);
        covid19BenFeedbackOBJ.setToStateDom(1);
        covid19BenFeedbackOBJ.setToSubDistrictDom(1);
        covid19BenFeedbackOBJ.setTocityInter(1);
        covid19BenFeedbackOBJ.setTravelList(new String[]{"Travel List"});
        covid19BenFeedbackOBJ.setTravelStatus(true);
        covid19BenFeedbackOBJ.setTravelType("Travel Type");
        covid19BenFeedbackOBJ.setVanID(1);
        covid19BenFeedbackOBJ.setVanSerialNo(1L);
        covid19BenFeedbackOBJ.setVisitCode(1L);
        covid19BenFeedbackOBJ.setcOVID19ID(1L);
        covid19BenFeedbackOBJ.setcOVID19_contact_history("C OVID19 contact history");

        // Act
        Integer actualSaveCovidDetailsResult = covid19ServiceImpl.saveCovidDetails(covid19BenFeedbackOBJ);

        // Assert
        verify(covid19BenFeedback).getcOVID19ID();
        verify(covid19BenFeedback).setBenCallID(isA(Long.class));
        verify(covid19BenFeedback).setBenMedHistoryID(isA(Long.class));
        verify(covid19BenFeedback).setBeneficiaryRegID(isA(Long.class));
        verify(covid19BenFeedback).setContactStatus(isA(String[].class));
        verify(covid19BenFeedback).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(covid19BenFeedback).setCreatedDate(isA(Timestamp.class));
        verify(covid19BenFeedback).setDeleted(isA(Boolean.class));
        verify(covid19BenFeedback).setFromCityInter(isA(Integer.class));
        verify(covid19BenFeedback).setFromCountryInter(isA(Integer.class));
        verify(covid19BenFeedback).setFromDistrictDom(isA(Integer.class));
        verify(covid19BenFeedback).setFromStateDom(isA(Integer.class));
        verify(covid19BenFeedback).setFromSubDistrictDom(isA(Integer.class));
        verify(covid19BenFeedback).setFromcityInter(isA(Integer.class));
        verify(covid19BenFeedback).setLastModDate(isA(Timestamp.class));
        verify(covid19BenFeedback).setMedical_consultation(isA(Boolean.class));
        verify(covid19BenFeedback).setModeOfTravelDomestic(eq("Mode Of Travel Domestic"));
        verify(covid19BenFeedback).setModeOfTravelInter(eq("Mode Of Travel Inter"));
        verify(covid19BenFeedback).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(covid19BenFeedback).setProcessed(eq("Processed"));
        verify(covid19BenFeedback).setProviderServiceMapID(isA(Integer.class));
        verify(covid19BenFeedback).setRecommendation(isA(ArrayList.class));
        verify(covid19BenFeedback).setRecommendation_db(eq("Recommendation db"));
        verify(covid19BenFeedback).setSuspectedStatus(isA(Boolean.class));
        verify(covid19BenFeedback).setSuspectedStatusUI(eq("Suspected Status UI"));
        verify(covid19BenFeedback).setSymptom(isA(String[].class));
        verify(covid19BenFeedback).setSymptoms(isA(String[].class));
        verify(covid19BenFeedback).setSymptoms_db(eq("Symptoms db"));
        verify(covid19BenFeedback).setToCityInter(isA(Integer.class));
        verify(covid19BenFeedback).setToCountryInter(isA(Integer.class));
        verify(covid19BenFeedback).setToDistrictDom(isA(Integer.class));
        verify(covid19BenFeedback).setToStateDom(isA(Integer.class));
        verify(covid19BenFeedback).setToSubDistrictDom(isA(Integer.class));
        verify(covid19BenFeedback).setTocityInter(isA(Integer.class));
        verify(covid19BenFeedback).setTravelList(isA(String[].class));
        verify(covid19BenFeedback).setTravelStatus(isA(Boolean.class));
        verify(covid19BenFeedback).setTravelType(eq("Travel Type"));
        verify(covid19BenFeedback).setVanID(isA(Integer.class));
        verify(covid19BenFeedback).setVanSerialNo(isA(Long.class));
        verify(covid19BenFeedback).setVisitCode(isA(Long.class));
        verify(covid19BenFeedback).setcOVID19ID(isA(Long.class));
        verify(covid19BenFeedback).setcOVID19_contact_history(eq("C OVID19 contact history"));
        verify(covid19BenFeedbackRepo).save(isA(Covid19BenFeedback.class));
        assertEquals("Symptoms", covid19BenFeedbackOBJ.getSymptoms_db());
        assertEquals("Travel List", covid19BenFeedbackOBJ.getTravelType());
        assertEquals(1, actualSaveCovidDetailsResult.intValue());
    }

    @Test
    void testSaveCovidDetails3() {
        // Arrange
        Covid19BenFeedback covid19BenFeedback = mock(Covid19BenFeedback.class);
        when(covid19BenFeedback.getcOVID19ID()).thenReturn(0L);
        doNothing().when(covid19BenFeedback).setBenCallID(Mockito.<Long>any());
        doNothing().when(covid19BenFeedback).setBenMedHistoryID(Mockito.<Long>any());
        doNothing().when(covid19BenFeedback).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(covid19BenFeedback).setContactStatus(Mockito.<String[]>any());
        doNothing().when(covid19BenFeedback).setCreatedBy(Mockito.<String>any());
        doNothing().when(covid19BenFeedback).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(covid19BenFeedback).setDeleted(Mockito.<Boolean>any());
        doNothing().when(covid19BenFeedback).setFromCityInter(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setFromCountryInter(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setFromDistrictDom(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setFromStateDom(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setFromSubDistrictDom(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setFromcityInter(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(covid19BenFeedback).setMedical_consultation(Mockito.<Boolean>any());
        doNothing().when(covid19BenFeedback).setModeOfTravelDomestic(Mockito.<String>any());
        doNothing().when(covid19BenFeedback).setModeOfTravelInter(Mockito.<String>any());
        doNothing().when(covid19BenFeedback).setModifiedBy(Mockito.<String>any());
        doNothing().when(covid19BenFeedback).setProcessed(Mockito.<String>any());
        doNothing().when(covid19BenFeedback).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setRecommendation(Mockito.<ArrayList<String[]>>any());
        doNothing().when(covid19BenFeedback).setRecommendation_db(Mockito.<String>any());
        doNothing().when(covid19BenFeedback).setSuspectedStatus(Mockito.<Boolean>any());
        doNothing().when(covid19BenFeedback).setSuspectedStatusUI(Mockito.<String>any());
        doNothing().when(covid19BenFeedback).setSymptom(Mockito.<String[]>any());
        doNothing().when(covid19BenFeedback).setSymptoms(Mockito.<String[]>any());
        doNothing().when(covid19BenFeedback).setSymptoms_db(Mockito.<String>any());
        doNothing().when(covid19BenFeedback).setToCityInter(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setToCountryInter(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setToDistrictDom(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setToStateDom(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setToSubDistrictDom(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setTocityInter(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setTravelList(Mockito.<String[]>any());
        doNothing().when(covid19BenFeedback).setTravelStatus(Mockito.<Boolean>any());
        doNothing().when(covid19BenFeedback).setTravelType(Mockito.<String>any());
        doNothing().when(covid19BenFeedback).setVanID(Mockito.<Integer>any());
        doNothing().when(covid19BenFeedback).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(covid19BenFeedback).setVisitCode(Mockito.<Long>any());
        doNothing().when(covid19BenFeedback).setcOVID19ID(Mockito.<Long>any());
        doNothing().when(covid19BenFeedback).setcOVID19_contact_history(Mockito.<String>any());
        covid19BenFeedback.setBenCallID(1L);
        covid19BenFeedback.setBenMedHistoryID(1L);
        covid19BenFeedback.setBeneficiaryRegID(1L);
        covid19BenFeedback.setContactStatus(new String[]{"Contact Status"});
        covid19BenFeedback.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        covid19BenFeedback.setCreatedDate(mock(Timestamp.class));
        covid19BenFeedback.setDeleted(true);
        covid19BenFeedback.setFromCityInter(1);
        covid19BenFeedback.setFromCountryInter(3);
        covid19BenFeedback.setFromDistrictDom(1);
        covid19BenFeedback.setFromStateDom(1);
        covid19BenFeedback.setFromSubDistrictDom(1);
        covid19BenFeedback.setFromcityInter(1);
        covid19BenFeedback.setLastModDate(mock(Timestamp.class));
        covid19BenFeedback.setMedical_consultation(true);
        covid19BenFeedback.setModeOfTravelDomestic("Mode Of Travel Domestic");
        covid19BenFeedback.setModeOfTravelInter("Mode Of Travel Inter");
        covid19BenFeedback.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        covid19BenFeedback.setProcessed("Processed");
        covid19BenFeedback.setProviderServiceMapID(1);
        covid19BenFeedback.setRecommendation(new ArrayList<>());
        covid19BenFeedback.setRecommendation_db("Recommendation db");
        covid19BenFeedback.setSuspectedStatus(true);
        covid19BenFeedback.setSuspectedStatusUI("Suspected Status UI");
        covid19BenFeedback.setSymptom(new String[]{"Symptom"});
        covid19BenFeedback.setSymptoms(new String[]{"Symptoms"});
        covid19BenFeedback.setSymptoms_db("Symptoms db");
        covid19BenFeedback.setToCityInter(1);
        covid19BenFeedback.setToCountryInter(3);
        covid19BenFeedback.setToDistrictDom(1);
        covid19BenFeedback.setToStateDom(1);
        covid19BenFeedback.setToSubDistrictDom(1);
        covid19BenFeedback.setTocityInter(1);
        covid19BenFeedback.setTravelList(new String[]{"Travel List"});
        covid19BenFeedback.setTravelStatus(true);
        covid19BenFeedback.setTravelType("Travel Type");
        covid19BenFeedback.setVanID(1);
        covid19BenFeedback.setVanSerialNo(1L);
        covid19BenFeedback.setVisitCode(1L);
        covid19BenFeedback.setcOVID19ID(1L);
        covid19BenFeedback.setcOVID19_contact_history("C OVID19 contact history");
        when(covid19BenFeedbackRepo.save(Mockito.<Covid19BenFeedback>any())).thenReturn(covid19BenFeedback);

        Covid19BenFeedback covid19BenFeedbackOBJ = new Covid19BenFeedback();
        covid19BenFeedbackOBJ.setBenCallID(1L);
        covid19BenFeedbackOBJ.setBenMedHistoryID(1L);
        covid19BenFeedbackOBJ.setBeneficiaryRegID(1L);
        covid19BenFeedbackOBJ.setContactStatus(new String[]{"Contact Status"});
        covid19BenFeedbackOBJ.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        covid19BenFeedbackOBJ.setCreatedDate(mock(Timestamp.class));
        covid19BenFeedbackOBJ.setDeleted(true);
        covid19BenFeedbackOBJ.setFromCityInter(1);
        covid19BenFeedbackOBJ.setFromCountryInter(3);
        covid19BenFeedbackOBJ.setFromDistrictDom(1);
        covid19BenFeedbackOBJ.setFromStateDom(1);
        covid19BenFeedbackOBJ.setFromSubDistrictDom(1);
        covid19BenFeedbackOBJ.setFromcityInter(1);
        covid19BenFeedbackOBJ.setLastModDate(mock(Timestamp.class));
        covid19BenFeedbackOBJ.setMedical_consultation(true);
        covid19BenFeedbackOBJ.setModeOfTravelDomestic("Mode Of Travel Domestic");
        covid19BenFeedbackOBJ.setModeOfTravelInter("Mode Of Travel Inter");
        covid19BenFeedbackOBJ.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        covid19BenFeedbackOBJ.setProcessed("Processed");
        covid19BenFeedbackOBJ.setProviderServiceMapID(1);
        covid19BenFeedbackOBJ.setRecommendation(new ArrayList<>());
        covid19BenFeedbackOBJ.setRecommendation_db("Recommendation db");
        covid19BenFeedbackOBJ.setSuspectedStatus(true);
        covid19BenFeedbackOBJ.setSuspectedStatusUI("Suspected Status UI");
        covid19BenFeedbackOBJ.setSymptom(new String[]{"Symptom"});
        covid19BenFeedbackOBJ.setSymptoms(new String[]{"Symptoms"});
        covid19BenFeedbackOBJ.setSymptoms_db("Symptoms db");
        covid19BenFeedbackOBJ.setToCityInter(1);
        covid19BenFeedbackOBJ.setToCountryInter(3);
        covid19BenFeedbackOBJ.setToDistrictDom(1);
        covid19BenFeedbackOBJ.setToStateDom(1);
        covid19BenFeedbackOBJ.setToSubDistrictDom(1);
        covid19BenFeedbackOBJ.setTocityInter(1);
        covid19BenFeedbackOBJ.setTravelList(new String[]{"Travel List"});
        covid19BenFeedbackOBJ.setTravelStatus(true);
        covid19BenFeedbackOBJ.setTravelType("Travel Type");
        covid19BenFeedbackOBJ.setVanID(1);
        covid19BenFeedbackOBJ.setVanSerialNo(1L);
        covid19BenFeedbackOBJ.setVisitCode(1L);
        covid19BenFeedbackOBJ.setcOVID19ID(1L);
        covid19BenFeedbackOBJ.setcOVID19_contact_history("C OVID19 contact history");

        // Act
        Integer actualSaveCovidDetailsResult = covid19ServiceImpl.saveCovidDetails(covid19BenFeedbackOBJ);

        // Assert
        verify(covid19BenFeedback).getcOVID19ID();
        verify(covid19BenFeedback).setBenCallID(isA(Long.class));
        verify(covid19BenFeedback).setBenMedHistoryID(isA(Long.class));
        verify(covid19BenFeedback).setBeneficiaryRegID(isA(Long.class));
        verify(covid19BenFeedback).setContactStatus(isA(String[].class));
        verify(covid19BenFeedback).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(covid19BenFeedback).setCreatedDate(isA(Timestamp.class));
        verify(covid19BenFeedback).setDeleted(isA(Boolean.class));
        verify(covid19BenFeedback).setFromCityInter(isA(Integer.class));
        verify(covid19BenFeedback).setFromCountryInter(isA(Integer.class));
        verify(covid19BenFeedback).setFromDistrictDom(isA(Integer.class));
        verify(covid19BenFeedback).setFromStateDom(isA(Integer.class));
        verify(covid19BenFeedback).setFromSubDistrictDom(isA(Integer.class));
        verify(covid19BenFeedback).setFromcityInter(isA(Integer.class));
        verify(covid19BenFeedback).setLastModDate(isA(Timestamp.class));
        verify(covid19BenFeedback).setMedical_consultation(isA(Boolean.class));
        verify(covid19BenFeedback).setModeOfTravelDomestic(eq("Mode Of Travel Domestic"));
        verify(covid19BenFeedback).setModeOfTravelInter(eq("Mode Of Travel Inter"));
        verify(covid19BenFeedback).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(covid19BenFeedback).setProcessed(eq("Processed"));
        verify(covid19BenFeedback).setProviderServiceMapID(isA(Integer.class));
        verify(covid19BenFeedback).setRecommendation(isA(ArrayList.class));
        verify(covid19BenFeedback).setRecommendation_db(eq("Recommendation db"));
        verify(covid19BenFeedback).setSuspectedStatus(isA(Boolean.class));
        verify(covid19BenFeedback).setSuspectedStatusUI(eq("Suspected Status UI"));
        verify(covid19BenFeedback).setSymptom(isA(String[].class));
        verify(covid19BenFeedback).setSymptoms(isA(String[].class));
        verify(covid19BenFeedback).setSymptoms_db(eq("Symptoms db"));
        verify(covid19BenFeedback).setToCityInter(isA(Integer.class));
        verify(covid19BenFeedback).setToCountryInter(isA(Integer.class));
        verify(covid19BenFeedback).setToDistrictDom(isA(Integer.class));
        verify(covid19BenFeedback).setToStateDom(isA(Integer.class));
        verify(covid19BenFeedback).setToSubDistrictDom(isA(Integer.class));
        verify(covid19BenFeedback).setTocityInter(isA(Integer.class));
        verify(covid19BenFeedback).setTravelList(isA(String[].class));
        verify(covid19BenFeedback).setTravelStatus(isA(Boolean.class));
        verify(covid19BenFeedback).setTravelType(eq("Travel Type"));
        verify(covid19BenFeedback).setVanID(isA(Integer.class));
        verify(covid19BenFeedback).setVanSerialNo(isA(Long.class));
        verify(covid19BenFeedback).setVisitCode(isA(Long.class));
        verify(covid19BenFeedback).setcOVID19ID(isA(Long.class));
        verify(covid19BenFeedback).setcOVID19_contact_history(eq("C OVID19 contact history"));
        verify(covid19BenFeedbackRepo).save(isA(Covid19BenFeedback.class));
        assertEquals("Symptoms", covid19BenFeedbackOBJ.getSymptoms_db());
        assertEquals("Travel List", covid19BenFeedbackOBJ.getTravelType());
        assertNull(actualSaveCovidDetailsResult);
    }

    @Test
    void testUpdateBenHistoryDetails() throws Exception {
        // Arrange, Act and Assert
        assertEquals(1, covid19ServiceImpl.updateBenHistoryDetails(new JsonObject()));
        assertEquals(1, covid19ServiceImpl.updateBenHistoryDetails(null));
    }

    @Test
    void testUpdateBenHistoryDetails2() throws Exception {
        // Arrange
        JsonObject historyOBJ = new JsonObject();
        historyOBJ.add("Property", new JsonArray(3));

        // Act and Assert
        assertEquals(1, covid19ServiceImpl.updateBenHistoryDetails(historyOBJ));
    }

    @Test
    void testUpdateBenHistoryDetails3() throws Exception {
        // Arrange
        JsonObject historyOBJ = new JsonObject();
        historyOBJ.addProperty("pastHistory", (String) null);

        // Act and Assert
        assertEquals(1, covid19ServiceImpl.updateBenHistoryDetails(historyOBJ));
    }

    @Test
    void testUpdateBenHistoryDetails4() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateBenPastHistoryDetails(Mockito.<BenMedHistory>any())).thenReturn(1);

        JsonObject historyOBJ = new JsonObject();
        historyOBJ.add("pastHistory", new JsonObject());

        // Act
        int actualUpdateBenHistoryDetailsResult = covid19ServiceImpl.updateBenHistoryDetails(historyOBJ);

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
        int actualUpdateBenHistoryDetailsResult = covid19ServiceImpl.updateBenHistoryDetails(historyOBJ);

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
        int actualUpdateBenVitalDetailsResult = covid19ServiceImpl.updateBenVitalDetails(new JsonObject());

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
        int actualUpdateBenVitalDetailsResult = covid19ServiceImpl.updateBenVitalDetails(new JsonObject());

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
        int actualUpdateBenVitalDetailsResult = covid19ServiceImpl.updateBenVitalDetails(new JsonObject());

        // Assert
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).updateANCPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(0, actualUpdateBenVitalDetailsResult);
    }

    @Test
    void testUpdateBenVitalDetails4() throws Exception {
        // Arrange, Act and Assert
        assertEquals(1, covid19ServiceImpl.updateBenVitalDetails(null));
    }

    @Test
    void testUpdateBenVitalDetails5() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.updateANCAnthropometryDetails(Mockito.<BenAnthropometryDetail>any())).thenReturn(1);
        when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any())).thenReturn(1);

        JsonObject vitalDetailsOBJ = new JsonObject();
        vitalDetailsOBJ.add("Property", new JsonArray(3));

        // Act
        int actualUpdateBenVitalDetailsResult = covid19ServiceImpl.updateBenVitalDetails(vitalDetailsOBJ);

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
        int actualUpdateBenVitalDetailsResult = covid19ServiceImpl.updateBenVitalDetails(vitalDetailsOBJ);

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
        int actualUpdateBenVitalDetailsResult = covid19ServiceImpl.updateBenVitalDetails(vitalDetailsOBJ);

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
        int actualUpdateBenVitalDetailsResult = covid19ServiceImpl.updateBenVitalDetails(vitalDetailsOBJ);

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
        assertThrows(IEMRException.class, () -> covid19ServiceImpl.updateBenVitalDetails(new JsonObject()));
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
        int actualUpdateBenVitalDetailsResult = covid19ServiceImpl.updateBenVitalDetails(vitalDetailsOBJ);

        // Assert
        verify(commonNurseServiceImpl).updateANCAnthropometryDetails(isA(BenAnthropometryDetail.class));
        verify(commonNurseServiceImpl).updateANCPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
        assertEquals(1, actualUpdateBenVitalDetailsResult);
    }

    @Test
    void testGetBenCovidNurseData() throws IEMRException {
        (new Covid19ServiceImpl()).getBenCovidNurseData(1L, 1L);
    }

    @Test
    void testSaveDoctorData() throws Exception {
        // Arrange, Act and Assert
        assertNull(covid19ServiceImpl.saveDoctorData(null, "JaneDoe"));
    }

    @Test
    void testGetBenCaseRecordFromDoctorCovid19() {
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
        when(prescriptionDetailRepo.findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(Mockito.<Long>any(),
                Mockito.<Long>any())).thenReturn(new ArrayList<>());

        // Act
        String actualBenCaseRecordFromDoctorCovid19 = covid19ServiceImpl.getBenCaseRecordFromDoctorCovid19(1L, 1L);

        // Assert
        verify(prescriptionDetailRepo).findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(isA(Long.class),
                isA(Long.class));
        verify(commonDoctorServiceImpl).getFindingsDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getInvestigationDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getPrescribedDrugs(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getReferralDetails(isA(Long.class), isA(Long.class), isA(Boolean.class));
        verify(commonNurseServiceImpl).getGraphicalTrendData(isA(Long.class), eq("ncdCare"));
        verify(labTechnicianServiceImpl).getLabResultDataForBen(isA(Long.class), isA(Long.class));
        verify(labTechnicianServiceImpl).getLast_3_ArchivedTestVisitList(isA(Long.class), isA(Long.class));
        assertEquals("{Refer=Referral Details, prescription=Prescribed Drugs, findings=Findings Details, LabReport=[],"
                + " diagnosis={}, investigation=Investigation Details, ArchivedVisitcodeForLabResult=Last 3 Archived Test"
                + " Visit List, GraphData={}}", actualBenCaseRecordFromDoctorCovid19);
    }

    @Test
    void testGetBenCaseRecordFromDoctorCovid192() {
        // Arrange
        when(commonDoctorServiceImpl.getFindingsDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Findings Details");
        when(prescriptionDetailRepo.findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(Mockito.<Long>any(),
                Mockito.<Long>any())).thenThrow(new RuntimeException("findings"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> covid19ServiceImpl.getBenCaseRecordFromDoctorCovid19(1L, 1L));
        verify(prescriptionDetailRepo).findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(isA(Long.class),
                isA(Long.class));
        verify(commonDoctorServiceImpl).getFindingsDetails(isA(Long.class), isA(Long.class));
    }

    @Test
    void testGetBenCaseRecordFromDoctorCovid193() {
        // Arrange
        when(commonDoctorServiceImpl.getFindingsDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Findings Details");
        when(commonDoctorServiceImpl.getInvestigationDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Investigation Details");
        when(commonDoctorServiceImpl.getPrescribedDrugs(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Prescribed Drugs");
        when(commonDoctorServiceImpl.getReferralDetails(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Boolean>any()))
                .thenReturn("Referral Details");

        HashMap<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("findings", "42");
        when(commonNurseServiceImpl.getGraphicalTrendData(Mockito.<Long>any(), Mockito.<String>any()))
                .thenReturn(stringObjectMap);
        when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Last 3 Archived Test Visit List");
        when(labTechnicianServiceImpl.getLabResultDataForBen(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());
        when(prescriptionDetailRepo.findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(Mockito.<Long>any(),
                Mockito.<Long>any())).thenReturn(new ArrayList<>());

        // Act
        String actualBenCaseRecordFromDoctorCovid19 = covid19ServiceImpl.getBenCaseRecordFromDoctorCovid19(1L, 1L);

        // Assert
        verify(prescriptionDetailRepo).findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(isA(Long.class),
                isA(Long.class));
        verify(commonDoctorServiceImpl).getFindingsDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getInvestigationDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getPrescribedDrugs(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getReferralDetails(isA(Long.class), isA(Long.class), isA(Boolean.class));
        verify(commonNurseServiceImpl).getGraphicalTrendData(isA(Long.class), eq("ncdCare"));
        verify(labTechnicianServiceImpl).getLabResultDataForBen(isA(Long.class), isA(Long.class));
        verify(labTechnicianServiceImpl).getLast_3_ArchivedTestVisitList(isA(Long.class), isA(Long.class));
        assertEquals("{Refer=Referral Details, prescription=Prescribed Drugs, findings=Findings Details, LabReport=[],"
                + " diagnosis={}, investigation=Investigation Details, ArchivedVisitcodeForLabResult=Last 3 Archived Test"
                + " Visit List, GraphData={\"findings\":\"42\"}}", actualBenCaseRecordFromDoctorCovid19);
    }

    @Test
    void testGetBenCaseRecordFromDoctorCovid194() {
        // Arrange
        when(commonDoctorServiceImpl.getFindingsDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Findings Details");
        when(commonDoctorServiceImpl.getInvestigationDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Investigation Details");
        when(commonDoctorServiceImpl.getPrescribedDrugs(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Prescribed Drugs");
        when(commonDoctorServiceImpl.getReferralDetails(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Boolean>any()))
                .thenReturn("Referral Details");

        HashMap<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("doctorDiagnonsis", "42");
        stringObjectMap.put("findings", "42");
        when(commonNurseServiceImpl.getGraphicalTrendData(Mockito.<Long>any(), Mockito.<String>any()))
                .thenReturn(stringObjectMap);
        when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Last 3 Archived Test Visit List");
        when(labTechnicianServiceImpl.getLabResultDataForBen(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());
        when(prescriptionDetailRepo.findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(Mockito.<Long>any(),
                Mockito.<Long>any())).thenReturn(new ArrayList<>());

        // Act
        String actualBenCaseRecordFromDoctorCovid19 = covid19ServiceImpl.getBenCaseRecordFromDoctorCovid19(1L, 1L);

        // Assert
        verify(prescriptionDetailRepo).findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(isA(Long.class),
                isA(Long.class));
        verify(commonDoctorServiceImpl).getFindingsDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getInvestigationDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getPrescribedDrugs(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getReferralDetails(isA(Long.class), isA(Long.class), isA(Boolean.class));
        verify(commonNurseServiceImpl).getGraphicalTrendData(isA(Long.class), eq("ncdCare"));
        verify(labTechnicianServiceImpl).getLabResultDataForBen(isA(Long.class), isA(Long.class));
        verify(labTechnicianServiceImpl).getLast_3_ArchivedTestVisitList(isA(Long.class), isA(Long.class));
        assertEquals(
                "{Refer=Referral Details, prescription=Prescribed Drugs, findings=Findings Details, LabReport=[],"
                        + " diagnosis={}, investigation=Investigation Details, ArchivedVisitcodeForLabResult=Last 3 Archived Test"
                        + " Visit List, GraphData={\"doctorDiagnonsis\":\"42\",\"findings\":\"42\"}}",
                actualBenCaseRecordFromDoctorCovid19);
    }

    @Test
    void testGetBenCaseRecordFromDoctorCovid195() {
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

        PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
        prescriptionDetail.setBenVisitID(1L);
        prescriptionDetail.setBeneficiaryRegID(1L);
        prescriptionDetail.setBreastCancerConfirmed(true);
        prescriptionDetail.setCervicalCancerConfirmed(true);
        prescriptionDetail.setConfirmatoryDiagnosis("findings");
        prescriptionDetail.setCounsellingProvided("findings");
        prescriptionDetail.setCounsellingProvidedList(new String[]{"findings"});
        prescriptionDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        prescriptionDetail.setCreatedDate(mock(Timestamp.class));
        prescriptionDetail.setDeleted(true);
        prescriptionDetail.setDiabetesScreeningConfirmed(true);
        prescriptionDetail.setDiagnosisProvided("findings");
        prescriptionDetail.setDiagnosisProvided_SCTCode("findings");
        prescriptionDetail.setDiagnosisProvided_SCTTerm("findings");
        prescriptionDetail.setExternalInvestigation("findings");
        prescriptionDetail.setHypertensionScreeningConfirmed(true);
        prescriptionDetail.setInstruction("findings");
        prescriptionDetail.setLastModDate(mock(Timestamp.class));
        prescriptionDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        prescriptionDetail.setOralCancerConfirmed(true);
        prescriptionDetail.setParkingPlaceID(1);
        prescriptionDetail.setPrescribedDrugs(new ArrayList<>());
        prescriptionDetail.setPrescriptionID(1L);
        prescriptionDetail.setProcessed("findings");
        prescriptionDetail.setProviderServiceMapID(1);
        prescriptionDetail.setProvisionalDiagnosisList(new ArrayList<>());
        prescriptionDetail.setRemarks("findings");
        prescriptionDetail.setReservedForChange("findings");
        prescriptionDetail.setSyncedBy("findings");
        prescriptionDetail.setSyncedDate(mock(Timestamp.class));
        prescriptionDetail.setTreatmentsOnSideEffects("findings");
        prescriptionDetail.setVanID(1);
        prescriptionDetail.setVanSerialNo(1L);
        prescriptionDetail.setVehicalNo("findings");
        prescriptionDetail.setVisitCode(1L);

        ArrayList<PrescriptionDetail> prescriptionDetailList = new ArrayList<>();
        prescriptionDetailList.add(prescriptionDetail);
        when(prescriptionDetailRepo.findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(Mockito.<Long>any(),
                Mockito.<Long>any())).thenReturn(prescriptionDetailList);

        // Act
        String actualBenCaseRecordFromDoctorCovid19 = covid19ServiceImpl.getBenCaseRecordFromDoctorCovid19(1L, 1L);

        // Assert
        verify(prescriptionDetailRepo).findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(isA(Long.class),
                isA(Long.class));
        verify(commonDoctorServiceImpl).getFindingsDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getInvestigationDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getPrescribedDrugs(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getReferralDetails(isA(Long.class), isA(Long.class), isA(Boolean.class));
        verify(commonNurseServiceImpl).getGraphicalTrendData(isA(Long.class), eq("ncdCare"));
        verify(labTechnicianServiceImpl).getLabResultDataForBen(isA(Long.class), isA(Long.class));
        verify(labTechnicianServiceImpl).getLast_3_ArchivedTestVisitList(isA(Long.class), isA(Long.class));
        assertEquals("{Refer=Referral Details, prescription=Prescribed Drugs, findings=Findings Details, LabReport=[],"
                + " diagnosis={\"prescriptionID\":1,\"vanID\":1,\"beneficiaryRegID\":1,\"specialistDiagnosis\":\"findings\",\"createdBy"
                + "\":\"Jan 1, 2020 8:00am GMT+0100\",\"doctorDiagnonsis\":\"findings\",\"parkingPlaceID\":1,\"visitCode\":1,"
                + "\"providerServiceMapID\":1,\"externalInvestigation\":\"findings\",\"counsellingProvided\":\"findings\"},"
                + " investigation=Investigation Details, ArchivedVisitcodeForLabResult=Last 3 Archived Test Visit List,"
                + " GraphData={}, counsellingProvidedList=\"findings\"}", actualBenCaseRecordFromDoctorCovid19);
    }

    @Test
    void testGetBenCaseRecordFromDoctorCovid196() {
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

        PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
        prescriptionDetail.setBenVisitID(1L);
        prescriptionDetail.setBeneficiaryRegID(1L);
        prescriptionDetail.setBreastCancerConfirmed(true);
        prescriptionDetail.setCervicalCancerConfirmed(true);
        prescriptionDetail.setConfirmatoryDiagnosis("findings");
        prescriptionDetail.setCounsellingProvided("findings");
        prescriptionDetail.setCounsellingProvidedList(new String[]{"findings"});
        prescriptionDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        prescriptionDetail.setCreatedDate(mock(Timestamp.class));
        prescriptionDetail.setDeleted(true);
        prescriptionDetail.setDiabetesScreeningConfirmed(true);
        prescriptionDetail.setDiagnosisProvided("findings");
        prescriptionDetail.setDiagnosisProvided_SCTCode("findings");
        prescriptionDetail.setDiagnosisProvided_SCTTerm("findings");
        prescriptionDetail.setExternalInvestigation("findings");
        prescriptionDetail.setHypertensionScreeningConfirmed(true);
        prescriptionDetail.setInstruction("findings");
        prescriptionDetail.setLastModDate(mock(Timestamp.class));
        prescriptionDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        prescriptionDetail.setOralCancerConfirmed(true);
        prescriptionDetail.setParkingPlaceID(1);
        prescriptionDetail.setPrescribedDrugs(new ArrayList<>());
        prescriptionDetail.setPrescriptionID(1L);
        prescriptionDetail.setProcessed("findings");
        prescriptionDetail.setProviderServiceMapID(1);
        prescriptionDetail.setProvisionalDiagnosisList(new ArrayList<>());
        prescriptionDetail.setRemarks("findings");
        prescriptionDetail.setReservedForChange("findings");
        prescriptionDetail.setSyncedBy("findings");
        prescriptionDetail.setSyncedDate(mock(Timestamp.class));
        prescriptionDetail.setTreatmentsOnSideEffects("findings");
        prescriptionDetail.setVanID(1);
        prescriptionDetail.setVanSerialNo(1L);
        prescriptionDetail.setVehicalNo("findings");
        prescriptionDetail.setVisitCode(1L);

        PrescriptionDetail prescriptionDetail2 = new PrescriptionDetail();
        prescriptionDetail2.setBenVisitID(2L);
        prescriptionDetail2.setBeneficiaryRegID(2L);
        prescriptionDetail2.setBreastCancerConfirmed(false);
        prescriptionDetail2.setCervicalCancerConfirmed(false);
        prescriptionDetail2.setConfirmatoryDiagnosis("doctorDiagnonsis");
        prescriptionDetail2.setCounsellingProvided("doctorDiagnonsis");
        prescriptionDetail2.setCounsellingProvidedList(new String[]{"findings"});
        prescriptionDetail2.setCreatedBy("findings");
        prescriptionDetail2.setCreatedDate(mock(Timestamp.class));
        prescriptionDetail2.setDeleted(false);
        prescriptionDetail2.setDiabetesScreeningConfirmed(false);
        prescriptionDetail2.setDiagnosisProvided("doctorDiagnonsis");
        prescriptionDetail2.setDiagnosisProvided_SCTCode("doctorDiagnonsis");
        prescriptionDetail2.setDiagnosisProvided_SCTTerm("doctorDiagnonsis");
        prescriptionDetail2.setExternalInvestigation("doctorDiagnonsis");
        prescriptionDetail2.setHypertensionScreeningConfirmed(false);
        prescriptionDetail2.setInstruction("doctorDiagnonsis");
        prescriptionDetail2.setLastModDate(mock(Timestamp.class));
        prescriptionDetail2.setModifiedBy("findings");
        prescriptionDetail2.setOralCancerConfirmed(false);
        prescriptionDetail2.setParkingPlaceID(2);
        prescriptionDetail2.setPrescribedDrugs(new ArrayList<>());
        prescriptionDetail2.setPrescriptionID(2L);
        prescriptionDetail2.setProcessed("doctorDiagnonsis");
        prescriptionDetail2.setProviderServiceMapID(2);
        prescriptionDetail2.setProvisionalDiagnosisList(new ArrayList<>());
        prescriptionDetail2.setRemarks("doctorDiagnonsis");
        prescriptionDetail2.setReservedForChange("doctorDiagnonsis");
        prescriptionDetail2.setSyncedBy("doctorDiagnonsis");
        prescriptionDetail2.setSyncedDate(mock(Timestamp.class));
        prescriptionDetail2.setTreatmentsOnSideEffects("doctorDiagnonsis");
        prescriptionDetail2.setVanID(2);
        prescriptionDetail2.setVanSerialNo(0L);
        prescriptionDetail2.setVehicalNo("doctorDiagnonsis");
        prescriptionDetail2.setVisitCode(0L);

        ArrayList<PrescriptionDetail> prescriptionDetailList = new ArrayList<>();
        prescriptionDetailList.add(prescriptionDetail2);
        prescriptionDetailList.add(prescriptionDetail);
        when(prescriptionDetailRepo.findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(Mockito.<Long>any(),
                Mockito.<Long>any())).thenReturn(prescriptionDetailList);

        // Act
        String actualBenCaseRecordFromDoctorCovid19 = covid19ServiceImpl.getBenCaseRecordFromDoctorCovid19(1L, 1L);

        // Assert
        verify(prescriptionDetailRepo).findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(isA(Long.class),
                isA(Long.class));
        verify(commonDoctorServiceImpl).getFindingsDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getInvestigationDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getPrescribedDrugs(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getReferralDetails(isA(Long.class), isA(Long.class), isA(Boolean.class));
        verify(commonNurseServiceImpl).getGraphicalTrendData(isA(Long.class), eq("ncdCare"));
        verify(labTechnicianServiceImpl).getLabResultDataForBen(isA(Long.class), isA(Long.class));
        verify(labTechnicianServiceImpl).getLast_3_ArchivedTestVisitList(isA(Long.class), isA(Long.class));
        assertEquals("{Refer=Referral Details, prescription=Prescribed Drugs, findings=Findings Details, LabReport=[],"
                + " diagnosis={\"prescriptionID\":2,\"vanID\":2,\"beneficiaryRegID\":2,\"specialistDiagnosis\":\"doctorDiagnonsis"
                + "\",\"createdBy\":\"findings\",\"doctorDiagnonsis\":\"doctorDiagnonsis\",\"parkingPlaceID\":2,\"visitCode\":0,"
                + "\"providerServiceMapID\":2,\"externalInvestigation\":\"doctorDiagnonsis\",\"counsellingProvided\":\"doctorDiagnonsis"
                + "\"}, investigation=Investigation Details, ArchivedVisitcodeForLabResult=Last 3 Archived Test Visit List,"
                + " GraphData={}, counsellingProvidedList=\"doctorDiagnonsis\"}", actualBenCaseRecordFromDoctorCovid19);
    }

    @Test
    void testUpdateCovid19DoctorData() throws Exception {
        // Arrange, Act and Assert
        assertNull(covid19ServiceImpl.updateCovid19DoctorData(null, "JaneDoe"));
    }
}
