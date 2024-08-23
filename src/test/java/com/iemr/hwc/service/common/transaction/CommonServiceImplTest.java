package com.iemr.hwc.service.common.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.neonatal.ImmunizationServicesRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.provider.ProviderServiceMappingRepo;
import com.iemr.hwc.service.adolescent.AdolescentAndChildCareService;
import com.iemr.hwc.service.anc.ANCServiceImpl;
import com.iemr.hwc.service.cancerScreening.CSNurseServiceImpl;
import com.iemr.hwc.service.cancerScreening.CSServiceImpl;
import com.iemr.hwc.service.covid19.Covid19ServiceImpl;
import com.iemr.hwc.service.family_planning.FamilyPlanningService;
import com.iemr.hwc.service.generalOPD.GeneralOPDServiceImpl;
import com.iemr.hwc.service.ncdCare.NCDCareServiceImpl;
import com.iemr.hwc.service.ncdscreening.NCDScreeningServiceImpl;
import com.iemr.hwc.service.neonatal.NeonatalService;
import com.iemr.hwc.service.pnc.PNCServiceImpl;
import com.iemr.hwc.service.quickConsultation.QuickConsultationServiceImpl;
import com.iemr.hwc.service.tele_consultation.TeleConsultationServiceImpl;
import com.iemr.hwc.utils.exception.IEMRException;

@ExtendWith(MockitoExtension.class)
class CommonServiceImplTest {
    @Mock
    private ANCServiceImpl aNCServiceImpl;

    @Mock
    private AdolescentAndChildCareService adolescentAndChildCareService;

    @Mock
    private BenVisitDetailRepo benVisitDetailRepo;

    @Mock
    private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;

    @Mock
    private CSNurseServiceImpl cSNurseServiceImpl;

    @Mock
    private CSServiceImpl cSServiceImpl;

    @Mock
    private CommonDoctorServiceImpl commonDoctorServiceImpl;

    @Mock
    private CommonNurseServiceImpl commonNurseServiceImpl;

    @InjectMocks
    private CommonServiceImpl commonServiceImpl;

    @Mock
    private Covid19ServiceImpl covid19ServiceImpl;

    @Mock
    private FamilyPlanningService familyPlanningService;

    @Mock
    private GeneralOPDServiceImpl generalOPDServiceImpl;

    @Mock
    private ImmunizationServicesRepo immunizationServicesRepo;

    @Mock
    private NCDCareServiceImpl nCDCareServiceImpl;

    @Mock
    private NCDScreeningServiceImpl nCDScreeningServiceImpl;

    @Mock
    private NeonatalService neonatalService;

    @Mock
    private PNCServiceImpl pNCServiceImpl;

    @Mock
    private ProviderServiceMappingRepo providerServiceMappingRepo;

    @Mock
    private QuickConsultationServiceImpl quickConsultationServiceImpl;

    @Mock
    private TeleConsultationServiceImpl teleConsultationServiceImpl;

    @Test
    void testGetBenPastHistoryData() {
        // Arrange
        when(commonNurseServiceImpl.fetchBenPastMedicalHistory(Mockito.<Long>any()))
                .thenReturn("Fetch Ben Past Medical History");

        // Act
        String actualBenPastHistoryData = commonServiceImpl.getBenPastHistoryData(1L);

        // Assert
        verify(commonNurseServiceImpl).fetchBenPastMedicalHistory(isA(Long.class));
        assertEquals("Fetch Ben Past Medical History", actualBenPastHistoryData);
    }

    @Test
    void testGetBenPastHistoryData2() {
        // Arrange
        when(commonNurseServiceImpl.fetchBenPastMedicalHistory(Mockito.<Long>any())).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> commonServiceImpl.getBenPastHistoryData(1L));
        verify(commonNurseServiceImpl).fetchBenPastMedicalHistory(isA(Long.class));
    }

    @Test
    void testGetBenPhysicalHistory() {
        // Arrange
        when(commonNurseServiceImpl.fetchBenPhysicalHistory(Mockito.<Long>any())).thenReturn("Fetch Ben Physical History");

        // Act
        String actualBenPhysicalHistory = commonServiceImpl.getBenPhysicalHistory(1L);

        // Assert
        verify(commonNurseServiceImpl).fetchBenPhysicalHistory(isA(Long.class));
        assertEquals("Fetch Ben Physical History", actualBenPhysicalHistory);
    }

    @Test
    void testGetBenPhysicalHistory2() {
        // Arrange
        when(commonNurseServiceImpl.fetchBenPhysicalHistory(Mockito.<Long>any())).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> commonServiceImpl.getBenPhysicalHistory(1L));
        verify(commonNurseServiceImpl).fetchBenPhysicalHistory(isA(Long.class));
    }

    @Test
    void testGetBenPerinatalHistoryData() {
        // Arrange
        when(commonNurseServiceImpl.fetchBenPerinatalHistory(Mockito.<Long>any()))
                .thenReturn("Fetch Ben Perinatal History");

        // Act
        String actualBenPerinatalHistoryData = commonServiceImpl.getBenPerinatalHistoryData(1L);

        // Assert
        verify(commonNurseServiceImpl).fetchBenPerinatalHistory(isA(Long.class));
        assertEquals("Fetch Ben Perinatal History", actualBenPerinatalHistoryData);
    }

    @Test
    void testGetBenPerinatalHistoryData2() {
        // Arrange
        when(commonNurseServiceImpl.fetchBenPerinatalHistory(Mockito.<Long>any())).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> commonServiceImpl.getBenPerinatalHistoryData(1L));
        verify(commonNurseServiceImpl).fetchBenPerinatalHistory(isA(Long.class));
    }

    @Test
    void testGetBenFeedingHistoryData() {
        // Arrange
        when(commonNurseServiceImpl.fetchBenFeedingHistory(Mockito.<Long>any())).thenReturn("Fetch Ben Feeding History");

        // Act
        String actualBenFeedingHistoryData = commonServiceImpl.getBenFeedingHistoryData(1L);

        // Assert
        verify(commonNurseServiceImpl).fetchBenFeedingHistory(isA(Long.class));
        assertEquals("Fetch Ben Feeding History", actualBenFeedingHistoryData);
    }

    @Test
    void testGetBenFeedingHistoryData2() {
        // Arrange
        when(commonNurseServiceImpl.fetchBenFeedingHistory(Mockito.<Long>any())).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> commonServiceImpl.getBenFeedingHistoryData(1L));
        verify(commonNurseServiceImpl).fetchBenFeedingHistory(isA(Long.class));
    }

    @Test
    void testGetBenDevelopmentHistoryData() {
        // Arrange
        when(commonNurseServiceImpl.fetchBenDevelopmentHistory(Mockito.<Long>any()))
                .thenReturn("Fetch Ben Development History");

        // Act
        String actualBenDevelopmentHistoryData = commonServiceImpl.getBenDevelopmentHistoryData(1L);

        // Assert
        verify(commonNurseServiceImpl).fetchBenDevelopmentHistory(isA(Long.class));
        assertEquals("Fetch Ben Development History", actualBenDevelopmentHistoryData);
    }

    @Test
    void testGetBenDevelopmentHistoryData2() {
        // Arrange
        when(commonNurseServiceImpl.fetchBenDevelopmentHistory(Mockito.<Long>any())).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> commonServiceImpl.getBenDevelopmentHistoryData(1L));
        verify(commonNurseServiceImpl).fetchBenDevelopmentHistory(isA(Long.class));
    }

    @Test
    void testGetBenPreviousVisitDataForCaseRecord() throws IEMRException {
        // Arrange
        when(providerServiceMappingRepo.getProviderServiceMapIdForServiceID(Mockito.<Short>any()))
                .thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> commonServiceImpl.getBenPreviousVisitDataForCaseRecord(""));
        verify(providerServiceMappingRepo).getProviderServiceMapIdForServiceID(isA(Short.class));
    }

    @Test
    void testCreateTcRequest() throws Exception {
        // Arrange
        JsonObject requestOBJ = new JsonObject();

        CommonUtilityClass commonUtilityClass = new CommonUtilityClass();
        commonUtilityClass.setAge(1);
        commonUtilityClass.setAgeUnits("Age Units");
        commonUtilityClass.setAuthorization("JaneDoe");
        commonUtilityClass.setBenFlowID(1L);
        commonUtilityClass.setBenRegID(1L);
        commonUtilityClass.setBenVisitID(1L);
        commonUtilityClass.setBeneficiaryID(1L);
        commonUtilityClass.setBeneficiaryRegID(1L);
        commonUtilityClass.setBeneficiaryRegId(1L);
        commonUtilityClass.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        commonUtilityClass.setDistrictID(1);
        commonUtilityClass.setDistrictName("District Name");
        commonUtilityClass.setFacilityID(1);
        commonUtilityClass.setFirstName("Jane");
        commonUtilityClass.setGenderID((short) 1);
        commonUtilityClass.setGenderName("Gender Name");
        commonUtilityClass.setIsCovidFlowDone(true);
        commonUtilityClass.setIsMobile(true);
        commonUtilityClass.setIsSpecialist(true);
        commonUtilityClass.setLastName("Doe");
        commonUtilityClass.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        commonUtilityClass.setParkingPlaceID(1);
        commonUtilityClass.setParkingPlaceId(1);
        commonUtilityClass.setPrescriptionID(1L);
        commonUtilityClass.setProviderServiceMapID(1);
        commonUtilityClass.setServiceID((short) 1);
        commonUtilityClass.setSessionID(1);
        commonUtilityClass.setSpecialistDiagnosis("Specialist Diagnosis");
        commonUtilityClass.setSubVisitCategory("Sub Visit Category");
        commonUtilityClass.setTreatmentsOnSideEffects("Treatments On Side Effects");
        commonUtilityClass.setVanID(1);
        commonUtilityClass.setVanId(1);
        commonUtilityClass.setVillageId(1);
        commonUtilityClass.setVillageName("Village Name");
        commonUtilityClass.setVisitCategoryID(1);
        commonUtilityClass.setVisitCode(1L);

        // Act and Assert
        assertNull(commonServiceImpl.createTcRequest(requestOBJ, commonUtilityClass, "JaneDoe"));
    }

    @Test
    void testCreateTcRequest2() throws Exception {
        // Arrange
        CommonUtilityClass commonUtilityClass = new CommonUtilityClass();
        commonUtilityClass.setAge(1);
        commonUtilityClass.setAgeUnits("Age Units");
        commonUtilityClass.setAuthorization("JaneDoe");
        commonUtilityClass.setBenFlowID(1L);
        commonUtilityClass.setBenRegID(1L);
        commonUtilityClass.setBenVisitID(1L);
        commonUtilityClass.setBeneficiaryID(1L);
        commonUtilityClass.setBeneficiaryRegID(1L);
        commonUtilityClass.setBeneficiaryRegId(1L);
        commonUtilityClass.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        commonUtilityClass.setDistrictID(1);
        commonUtilityClass.setDistrictName("District Name");
        commonUtilityClass.setFacilityID(1);
        commonUtilityClass.setFirstName("Jane");
        commonUtilityClass.setGenderID((short) 1);
        commonUtilityClass.setGenderName("Gender Name");
        commonUtilityClass.setIsCovidFlowDone(true);
        commonUtilityClass.setIsMobile(true);
        commonUtilityClass.setIsSpecialist(true);
        commonUtilityClass.setLastName("Doe");
        commonUtilityClass.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        commonUtilityClass.setParkingPlaceID(1);
        commonUtilityClass.setParkingPlaceId(1);
        commonUtilityClass.setPrescriptionID(1L);
        commonUtilityClass.setProviderServiceMapID(1);
        commonUtilityClass.setServiceID((short) 1);
        commonUtilityClass.setSessionID(1);
        commonUtilityClass.setSpecialistDiagnosis("Specialist Diagnosis");
        commonUtilityClass.setSubVisitCategory("Sub Visit Category");
        commonUtilityClass.setTreatmentsOnSideEffects("Treatments On Side Effects");
        commonUtilityClass.setVanID(1);
        commonUtilityClass.setVanId(1);
        commonUtilityClass.setVillageId(1);
        commonUtilityClass.setVillageName("Village Name");
        commonUtilityClass.setVisitCategoryID(1);
        commonUtilityClass.setVisitCode(1L);

        // Act and Assert
        assertNull(commonServiceImpl.createTcRequest(null, commonUtilityClass, "JaneDoe"));
    }

    @Test
    void testCreateTcRequest3() throws Exception {
        // Arrange
        JsonObject requestOBJ = new JsonObject();
        requestOBJ.add("yyyy-MM-dd'T'HH:mm:ss.SSS", new JsonArray(3));

        CommonUtilityClass commonUtilityClass = new CommonUtilityClass();
        commonUtilityClass.setAge(1);
        commonUtilityClass.setAgeUnits("Age Units");
        commonUtilityClass.setAuthorization("JaneDoe");
        commonUtilityClass.setBenFlowID(1L);
        commonUtilityClass.setBenRegID(1L);
        commonUtilityClass.setBenVisitID(1L);
        commonUtilityClass.setBeneficiaryID(1L);
        commonUtilityClass.setBeneficiaryRegID(1L);
        commonUtilityClass.setBeneficiaryRegId(1L);
        commonUtilityClass.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        commonUtilityClass.setDistrictID(1);
        commonUtilityClass.setDistrictName("District Name");
        commonUtilityClass.setFacilityID(1);
        commonUtilityClass.setFirstName("Jane");
        commonUtilityClass.setGenderID((short) 1);
        commonUtilityClass.setGenderName("Gender Name");
        commonUtilityClass.setIsCovidFlowDone(true);
        commonUtilityClass.setIsMobile(true);
        commonUtilityClass.setIsSpecialist(true);
        commonUtilityClass.setLastName("Doe");
        commonUtilityClass.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        commonUtilityClass.setParkingPlaceID(1);
        commonUtilityClass.setParkingPlaceId(1);
        commonUtilityClass.setPrescriptionID(1L);
        commonUtilityClass.setProviderServiceMapID(1);
        commonUtilityClass.setServiceID((short) 1);
        commonUtilityClass.setSessionID(1);
        commonUtilityClass.setSpecialistDiagnosis("Specialist Diagnosis");
        commonUtilityClass.setSubVisitCategory("Sub Visit Category");
        commonUtilityClass.setTreatmentsOnSideEffects("Treatments On Side Effects");
        commonUtilityClass.setVanID(1);
        commonUtilityClass.setVanId(1);
        commonUtilityClass.setVillageId(1);
        commonUtilityClass.setVillageName("Village Name");
        commonUtilityClass.setVisitCategoryID(1);
        commonUtilityClass.setVisitCode(1L);

        // Act and Assert
        assertNull(commonServiceImpl.createTcRequest(requestOBJ, commonUtilityClass, "JaneDoe"));
    }

    @Test
    void testCreateTcRequest4() throws Exception {
        // Arrange
        JsonObject requestOBJ = new JsonObject();
        requestOBJ.add("Property", new JsonArray(3));

        CommonUtilityClass commonUtilityClass = new CommonUtilityClass();
        commonUtilityClass.setAge(1);
        commonUtilityClass.setAgeUnits("Age Units");
        commonUtilityClass.setAuthorization("JaneDoe");
        commonUtilityClass.setBenFlowID(1L);
        commonUtilityClass.setBenRegID(1L);
        commonUtilityClass.setBenVisitID(1L);
        commonUtilityClass.setBeneficiaryID(1L);
        commonUtilityClass.setBeneficiaryRegID(1L);
        commonUtilityClass.setBeneficiaryRegId(1L);
        commonUtilityClass.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        commonUtilityClass.setDistrictID(1);
        commonUtilityClass.setDistrictName("District Name");
        commonUtilityClass.setFacilityID(1);
        commonUtilityClass.setFirstName("Jane");
        commonUtilityClass.setGenderID((short) 1);
        commonUtilityClass.setGenderName("Gender Name");
        commonUtilityClass.setIsCovidFlowDone(true);
        commonUtilityClass.setIsMobile(true);
        commonUtilityClass.setIsSpecialist(true);
        commonUtilityClass.setLastName("Doe");
        commonUtilityClass.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        commonUtilityClass.setParkingPlaceID(1);
        commonUtilityClass.setParkingPlaceId(1);
        commonUtilityClass.setPrescriptionID(1L);
        commonUtilityClass.setProviderServiceMapID(1);
        commonUtilityClass.setServiceID((short) 1);
        commonUtilityClass.setSessionID(1);
        commonUtilityClass.setSpecialistDiagnosis("Specialist Diagnosis");
        commonUtilityClass.setSubVisitCategory("Sub Visit Category");
        commonUtilityClass.setTreatmentsOnSideEffects("Treatments On Side Effects");
        commonUtilityClass.setVanID(1);
        commonUtilityClass.setVanId(1);
        commonUtilityClass.setVillageId(1);
        commonUtilityClass.setVillageName("Village Name");
        commonUtilityClass.setVisitCategoryID(1);
        commonUtilityClass.setVisitCode(1L);

        // Act and Assert
        assertNull(commonServiceImpl.createTcRequest(requestOBJ, commonUtilityClass, "JaneDoe"));
    }

    @Test
    void testCreateTcRequest5() throws Exception {
        // Arrange
        JsonObject requestOBJ = new JsonObject();
        requestOBJ.addProperty("tcRequest", (String) null);

        CommonUtilityClass commonUtilityClass = new CommonUtilityClass();
        commonUtilityClass.setAge(1);
        commonUtilityClass.setAgeUnits("Age Units");
        commonUtilityClass.setAuthorization("JaneDoe");
        commonUtilityClass.setBenFlowID(1L);
        commonUtilityClass.setBenRegID(1L);
        commonUtilityClass.setBenVisitID(1L);
        commonUtilityClass.setBeneficiaryID(1L);
        commonUtilityClass.setBeneficiaryRegID(1L);
        commonUtilityClass.setBeneficiaryRegId(1L);
        commonUtilityClass.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        commonUtilityClass.setDistrictID(1);
        commonUtilityClass.setDistrictName("District Name");
        commonUtilityClass.setFacilityID(1);
        commonUtilityClass.setFirstName("Jane");
        commonUtilityClass.setGenderID((short) 1);
        commonUtilityClass.setGenderName("Gender Name");
        commonUtilityClass.setIsCovidFlowDone(true);
        commonUtilityClass.setIsMobile(true);
        commonUtilityClass.setIsSpecialist(true);
        commonUtilityClass.setLastName("Doe");
        commonUtilityClass.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        commonUtilityClass.setParkingPlaceID(1);
        commonUtilityClass.setParkingPlaceId(1);
        commonUtilityClass.setPrescriptionID(1L);
        commonUtilityClass.setProviderServiceMapID(1);
        commonUtilityClass.setServiceID((short) 1);
        commonUtilityClass.setSessionID(1);
        commonUtilityClass.setSpecialistDiagnosis("Specialist Diagnosis");
        commonUtilityClass.setSubVisitCategory("Sub Visit Category");
        commonUtilityClass.setTreatmentsOnSideEffects("Treatments On Side Effects");
        commonUtilityClass.setVanID(1);
        commonUtilityClass.setVanId(1);
        commonUtilityClass.setVillageId(1);
        commonUtilityClass.setVillageName("Village Name");
        commonUtilityClass.setVisitCategoryID(1);
        commonUtilityClass.setVisitCode(1L);

        // Act and Assert
        assertNull(commonServiceImpl.createTcRequest(requestOBJ, commonUtilityClass, "JaneDoe"));
    }

    @Test
    void testCreateTcRequest6() throws Exception {
        // Arrange
        JsonObject requestOBJ = new JsonObject();
        CommonUtilityClass commonUtilityClass = mock(CommonUtilityClass.class);
        doNothing().when(commonUtilityClass).setAge(Mockito.<Integer>any());
        doNothing().when(commonUtilityClass).setAgeUnits(Mockito.<String>any());
        doNothing().when(commonUtilityClass).setAuthorization(Mockito.<String>any());
        doNothing().when(commonUtilityClass).setBenFlowID(Mockito.<Long>any());
        doNothing().when(commonUtilityClass).setBenRegID(Mockito.<Long>any());
        doNothing().when(commonUtilityClass).setBenVisitID(Mockito.<Long>any());
        doNothing().when(commonUtilityClass).setBeneficiaryID(Mockito.<Long>any());
        doNothing().when(commonUtilityClass).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(commonUtilityClass).setBeneficiaryRegId(Mockito.<Long>any());
        doNothing().when(commonUtilityClass).setCreatedBy(Mockito.<String>any());
        doNothing().when(commonUtilityClass).setDistrictID(Mockito.<Integer>any());
        doNothing().when(commonUtilityClass).setDistrictName(Mockito.<String>any());
        doNothing().when(commonUtilityClass).setFacilityID(Mockito.<Integer>any());
        doNothing().when(commonUtilityClass).setFirstName(Mockito.<String>any());
        doNothing().when(commonUtilityClass).setGenderID(Mockito.<Short>any());
        doNothing().when(commonUtilityClass).setGenderName(Mockito.<String>any());
        doNothing().when(commonUtilityClass).setIsCovidFlowDone(Mockito.<Boolean>any());
        doNothing().when(commonUtilityClass).setIsMobile(Mockito.<Boolean>any());
        doNothing().when(commonUtilityClass).setIsSpecialist(Mockito.<Boolean>any());
        doNothing().when(commonUtilityClass).setLastName(Mockito.<String>any());
        doNothing().when(commonUtilityClass).setModifiedBy(Mockito.<String>any());
        doNothing().when(commonUtilityClass).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(commonUtilityClass).setParkingPlaceId(Mockito.<Integer>any());
        doNothing().when(commonUtilityClass).setPrescriptionID(Mockito.<Long>any());
        doNothing().when(commonUtilityClass).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(commonUtilityClass).setServiceID(Mockito.<Short>any());
        doNothing().when(commonUtilityClass).setSessionID(Mockito.<Integer>any());
        doNothing().when(commonUtilityClass).setSpecialistDiagnosis(Mockito.<String>any());
        doNothing().when(commonUtilityClass).setSubVisitCategory(Mockito.<String>any());
        doNothing().when(commonUtilityClass).setTreatmentsOnSideEffects(Mockito.<String>any());
        doNothing().when(commonUtilityClass).setVanID(Mockito.<Integer>any());
        doNothing().when(commonUtilityClass).setVanId(Mockito.<Integer>any());
        doNothing().when(commonUtilityClass).setVillageId(Mockito.<Integer>any());
        doNothing().when(commonUtilityClass).setVillageName(Mockito.<String>any());
        doNothing().when(commonUtilityClass).setVisitCategoryID(Mockito.<Integer>any());
        doNothing().when(commonUtilityClass).setVisitCode(Mockito.<Long>any());
        commonUtilityClass.setAge(1);
        commonUtilityClass.setAgeUnits("Age Units");
        commonUtilityClass.setAuthorization("JaneDoe");
        commonUtilityClass.setBenFlowID(1L);
        commonUtilityClass.setBenRegID(1L);
        commonUtilityClass.setBenVisitID(1L);
        commonUtilityClass.setBeneficiaryID(1L);
        commonUtilityClass.setBeneficiaryRegID(1L);
        commonUtilityClass.setBeneficiaryRegId(1L);
        commonUtilityClass.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        commonUtilityClass.setDistrictID(1);
        commonUtilityClass.setDistrictName("District Name");
        commonUtilityClass.setFacilityID(1);
        commonUtilityClass.setFirstName("Jane");
        commonUtilityClass.setGenderID((short) 1);
        commonUtilityClass.setGenderName("Gender Name");
        commonUtilityClass.setIsCovidFlowDone(true);
        commonUtilityClass.setIsMobile(true);
        commonUtilityClass.setIsSpecialist(true);
        commonUtilityClass.setLastName("Doe");
        commonUtilityClass.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        commonUtilityClass.setParkingPlaceID(1);
        commonUtilityClass.setParkingPlaceId(1);
        commonUtilityClass.setPrescriptionID(1L);
        commonUtilityClass.setProviderServiceMapID(1);
        commonUtilityClass.setServiceID((short) 1);
        commonUtilityClass.setSessionID(1);
        commonUtilityClass.setSpecialistDiagnosis("Specialist Diagnosis");
        commonUtilityClass.setSubVisitCategory("Sub Visit Category");
        commonUtilityClass.setTreatmentsOnSideEffects("Treatments On Side Effects");
        commonUtilityClass.setVanID(1);
        commonUtilityClass.setVanId(1);
        commonUtilityClass.setVillageId(1);
        commonUtilityClass.setVillageName("Village Name");
        commonUtilityClass.setVisitCategoryID(1);
        commonUtilityClass.setVisitCode(1L);

        // Act
        TeleconsultationRequestOBJ actualCreateTcRequestResult = commonServiceImpl.createTcRequest(requestOBJ,
                commonUtilityClass, "JaneDoe");

        // Assert
        verify(commonUtilityClass).setAge(isA(Integer.class));
        verify(commonUtilityClass).setAgeUnits(eq("Age Units"));
        verify(commonUtilityClass).setAuthorization(eq("JaneDoe"));
        verify(commonUtilityClass).setBenFlowID(isA(Long.class));
        verify(commonUtilityClass).setBenRegID(isA(Long.class));
        verify(commonUtilityClass).setBenVisitID(isA(Long.class));
        verify(commonUtilityClass).setBeneficiaryID(isA(Long.class));
        verify(commonUtilityClass).setBeneficiaryRegID(isA(Long.class));
        verify(commonUtilityClass).setBeneficiaryRegId(isA(Long.class));
        verify(commonUtilityClass).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(commonUtilityClass).setDistrictID(isA(Integer.class));
        verify(commonUtilityClass).setDistrictName(eq("District Name"));
        verify(commonUtilityClass).setFacilityID(isA(Integer.class));
        verify(commonUtilityClass).setFirstName(eq("Jane"));
        verify(commonUtilityClass).setGenderID(isA(Short.class));
        verify(commonUtilityClass).setGenderName(eq("Gender Name"));
        verify(commonUtilityClass).setIsCovidFlowDone(isA(Boolean.class));
        verify(commonUtilityClass).setIsMobile(isA(Boolean.class));
        verify(commonUtilityClass).setIsSpecialist(isA(Boolean.class));
        verify(commonUtilityClass).setLastName(eq("Doe"));
        verify(commonUtilityClass).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(commonUtilityClass).setParkingPlaceID(isA(Integer.class));
        verify(commonUtilityClass).setParkingPlaceId(isA(Integer.class));
        verify(commonUtilityClass).setPrescriptionID(isA(Long.class));
        verify(commonUtilityClass).setProviderServiceMapID(isA(Integer.class));
        verify(commonUtilityClass).setServiceID(isA(Short.class));
        verify(commonUtilityClass).setSessionID(isA(Integer.class));
        verify(commonUtilityClass).setSpecialistDiagnosis(eq("Specialist Diagnosis"));
        verify(commonUtilityClass).setSubVisitCategory(eq("Sub Visit Category"));
        verify(commonUtilityClass).setTreatmentsOnSideEffects(eq("Treatments On Side Effects"));
        verify(commonUtilityClass).setVanID(isA(Integer.class));
        verify(commonUtilityClass).setVanId(isA(Integer.class));
        verify(commonUtilityClass).setVillageId(isA(Integer.class));
        verify(commonUtilityClass).setVillageName(eq("Village Name"));
        verify(commonUtilityClass).setVisitCategoryID(isA(Integer.class));
        verify(commonUtilityClass).setVisitCode(isA(Long.class));
        assertNull(actualCreateTcRequestResult);
    }

    @Test
    void testGetBenSymptomaticQuestionnaireDetailsData() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.getBenSymptomaticData(Mockito.<Long>any())).thenReturn("Ben Symptomatic Data");

        // Act
        String actualBenSymptomaticQuestionnaireDetailsData = commonServiceImpl
                .getBenSymptomaticQuestionnaireDetailsData(1L);

        // Assert
        verify(commonNurseServiceImpl).getBenSymptomaticData(isA(Long.class));
        assertEquals("Ben Symptomatic Data", actualBenSymptomaticQuestionnaireDetailsData);
    }

    @Test
    void testGetBenSymptomaticQuestionnaireDetailsData2() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.getBenSymptomaticData(Mockito.<Long>any())).thenThrow(new Exception("foo"));

        // Act and Assert
        assertThrows(Exception.class, () -> commonServiceImpl.getBenSymptomaticQuestionnaireDetailsData(1L));
        verify(commonNurseServiceImpl).getBenSymptomaticData(isA(Long.class));
    }

    @Test
    void testGetBenPreviousDiabetesData() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.getBenPreviousDiabetesData(Mockito.<Long>any()))
                .thenReturn("Ben Previous Diabetes Data");

        // Act
        String actualBenPreviousDiabetesData = commonServiceImpl.getBenPreviousDiabetesData(1L);

        // Assert
        verify(commonNurseServiceImpl).getBenPreviousDiabetesData(isA(Long.class));
        assertEquals("Ben Previous Diabetes Data", actualBenPreviousDiabetesData);
    }

    @Test
    void testGetBenPreviousDiabetesData2() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.getBenPreviousDiabetesData(Mockito.<Long>any())).thenThrow(new Exception("foo"));

        // Act and Assert
        assertThrows(Exception.class, () -> commonServiceImpl.getBenPreviousDiabetesData(1L));
        verify(commonNurseServiceImpl).getBenPreviousDiabetesData(isA(Long.class));
    }

    @Test
    void testGetBenPreviousReferralData() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.getBenPreviousReferralData(Mockito.<Long>any()))
                .thenReturn("Ben Previous Referral Data");

        // Act
        String actualBenPreviousReferralData = commonServiceImpl.getBenPreviousReferralData(1L);

        // Assert
        verify(commonNurseServiceImpl).getBenPreviousReferralData(isA(Long.class));
        assertEquals("Ben Previous Referral Data", actualBenPreviousReferralData);
    }

    @Test
    void testGetBenPreviousReferralData2() throws Exception {
        // Arrange
        when(commonNurseServiceImpl.getBenPreviousReferralData(Mockito.<Long>any())).thenThrow(new Exception("foo"));

        // Act and Assert
        assertThrows(Exception.class, () -> commonServiceImpl.getBenPreviousReferralData(1L));
        verify(commonNurseServiceImpl).getBenPreviousReferralData(isA(Long.class));
    }

    @Test
    void testGetBenImmunizationServiceHistory() throws Exception {
        // Arrange
        when(immunizationServicesRepo.findByBeneficiaryRegIDAndDeleted(anyLong(), anyBoolean()))
                .thenReturn(new ArrayList<>());

        // Act
        String actualBenImmunizationServiceHistory = commonServiceImpl.getBenImmunizationServiceHistory(1L);

        // Assert
        verify(immunizationServicesRepo).findByBeneficiaryRegIDAndDeleted(eq(1L), eq(false));
        assertEquals("[]", actualBenImmunizationServiceHistory);
    }

    @Test
    void testGetBenImmunizationServiceHistory2() throws Exception {
        // Arrange
        when(immunizationServicesRepo.findByBeneficiaryRegIDAndDeleted(anyLong(), anyBoolean()))
                .thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> commonServiceImpl.getBenImmunizationServiceHistory(1L));
        verify(immunizationServicesRepo).findByBeneficiaryRegIDAndDeleted(eq(1L), eq(false));
    }

    @Test
    void testGettersAndSetters() {
        CommonServiceImpl commonServiceImpl = new CommonServiceImpl();

        // Act
        commonServiceImpl.setAncServiceImpl(new ANCServiceImpl());
        commonServiceImpl.setBeneficiaryFlowStatusRepo(mock(BeneficiaryFlowStatusRepo.class));
        commonServiceImpl.setCommonNurseServiceImpl(new CommonNurseServiceImpl());
        commonServiceImpl.setCsServiceImpl(new CSServiceImpl());
        commonServiceImpl.setGeneralOPDServiceImpl(new GeneralOPDServiceImpl());
        commonServiceImpl.setNcdCareServiceImpl(new NCDCareServiceImpl());
        commonServiceImpl.setNcdScreeningServiceImpl(new NCDScreeningServiceImpl());
        commonServiceImpl.setPncServiceImpl(new PNCServiceImpl());
        commonServiceImpl.setQuickConsultationServiceImpl(new QuickConsultationServiceImpl());
        commonServiceImpl.setcSNurseServiceImpl(new CSNurseServiceImpl());
    }
}
