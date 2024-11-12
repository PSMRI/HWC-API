package com.iemr.hwc.service.ncdscreening;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.quickConsultation.PrescriptionDetail;
import com.iemr.hwc.repo.nurse.ncdscreening.BreastCancerScreeningRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.CervicalCancerScreeningRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.DiabetesScreeningRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.HypertensionScreeningRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.OralCancerScreeningRepo;
import com.iemr.hwc.utils.exception.IEMRException;

@ExtendWith(MockitoExtension.class)
class CommonNcdScreeningServiceTest {
    @Mock
    private BreastCancerScreeningRepo breastCancerScreeningRepo;

    @Mock
    private CervicalCancerScreeningRepo cervicalCancerScreeningRepo;

    @InjectMocks
    private CommonNcdScreeningService commonNcdScreeningService;

    @Mock
    private DiabetesScreeningRepo diabetesScreeningRepo;

    @Mock
    private HypertensionScreeningRepo hypertensionScreeningRepo;

    @Mock
    private OralCancerScreeningRepo oralCancerScreeningRepo;

    @Test
    void testUpdateScreeningConfirmedStatusByDocSpecialist() throws IEMRException {
        // Arrange
        when(breastCancerScreeningRepo.updateBreastScreeningConfirmedStatus(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(1);
        when(cervicalCancerScreeningRepo.updateCervicalScreeningConfirmedStatus(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(1);
        when(diabetesScreeningRepo.updateDiabetesScreeningConfirmedStatus(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(1);
        when(hypertensionScreeningRepo.updateHypertensionScreeningConfirmedStatus(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(1);
        when(oralCancerScreeningRepo.updateOralScreeningConfirmedStatus(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(1);

        PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
        prescriptionDetail.setBenVisitID(1L);
        prescriptionDetail.setBeneficiaryRegID(1L);
        prescriptionDetail.setBreastCancerConfirmed(true);
        prescriptionDetail.setCervicalCancerConfirmed(true);
        prescriptionDetail.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
        prescriptionDetail.setCounsellingProvided("Counselling Provided");
        prescriptionDetail.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
        prescriptionDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        prescriptionDetail.setCreatedDate(mock(Timestamp.class));
        prescriptionDetail.setDeleted(true);
        prescriptionDetail.setDiabetesScreeningConfirmed(true);
        prescriptionDetail.setDiagnosisProvided("Diagnosis Provided");
        prescriptionDetail.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
        prescriptionDetail.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
        prescriptionDetail.setExternalInvestigation("External Investigation");
        prescriptionDetail.setHypertensionScreeningConfirmed(true);
        prescriptionDetail.setInstruction("Instruction");
        prescriptionDetail.setLastModDate(mock(Timestamp.class));
        prescriptionDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        prescriptionDetail.setOralCancerConfirmed(true);
        prescriptionDetail.setParkingPlaceID(1);
        prescriptionDetail.setPrescribedDrugs(new ArrayList<>());
        prescriptionDetail.setPrescriptionID(1L);
        prescriptionDetail.setProcessed("Processed");
        prescriptionDetail.setProviderServiceMapID(1);
        prescriptionDetail.setProvisionalDiagnosisList(new ArrayList<>());
        prescriptionDetail.setRemarks("Remarks");
        prescriptionDetail.setReservedForChange("Reserved For Change");
        prescriptionDetail.setSyncedBy("Synced By");
        prescriptionDetail.setSyncedDate(mock(Timestamp.class));
        prescriptionDetail.setTreatmentsOnSideEffects("Treatments On Side Effects");
        prescriptionDetail.setVanID(1);
        prescriptionDetail.setVanSerialNo(1L);
        prescriptionDetail.setVehicalNo("Vehical No");
        prescriptionDetail.setVisitCode(1L);

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

        // Act
        int actualUpdateScreeningConfirmedStatusByDocSpecialistResult = commonNcdScreeningService
                .updateScreeningConfirmedStatusByDocSpecialist(prescriptionDetail, commonUtilityClass);

        // Assert
        verify(breastCancerScreeningRepo).updateBreastScreeningConfirmedStatus(isA(Long.class), isA(Long.class),
                isA(Boolean.class));
        verify(cervicalCancerScreeningRepo).updateCervicalScreeningConfirmedStatus(isA(Long.class), isA(Long.class),
                isA(Boolean.class));
        verify(diabetesScreeningRepo).updateDiabetesScreeningConfirmedStatus(isA(Long.class), isA(Long.class),
                isA(Boolean.class));
        verify(hypertensionScreeningRepo).updateHypertensionScreeningConfirmedStatus(isA(Long.class), isA(Long.class),
                isA(Boolean.class));
        verify(oralCancerScreeningRepo).updateOralScreeningConfirmedStatus(isA(Long.class), isA(Long.class),
                isA(Boolean.class));
        assertEquals(1, actualUpdateScreeningConfirmedStatusByDocSpecialistResult);
    }

    @Test
    void testUpdateScreeningConfirmedStatusByDocSpecialist2() throws IEMRException {
        // Arrange
        when(cervicalCancerScreeningRepo.updateCervicalScreeningConfirmedStatus(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(1);
        when(diabetesScreeningRepo.updateDiabetesScreeningConfirmedStatus(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(1);
        when(hypertensionScreeningRepo.updateHypertensionScreeningConfirmedStatus(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(1);
        when(oralCancerScreeningRepo.updateOralScreeningConfirmedStatus(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(1);

        PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
        prescriptionDetail.setBenVisitID(1L);
        prescriptionDetail.setBeneficiaryRegID(1L);
        prescriptionDetail.setBreastCancerConfirmed(null);
        prescriptionDetail.setCervicalCancerConfirmed(true);
        prescriptionDetail.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
        prescriptionDetail.setCounsellingProvided("Counselling Provided");
        prescriptionDetail.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
        prescriptionDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        prescriptionDetail.setCreatedDate(mock(Timestamp.class));
        prescriptionDetail.setDeleted(true);
        prescriptionDetail.setDiabetesScreeningConfirmed(true);
        prescriptionDetail.setDiagnosisProvided("Diagnosis Provided");
        prescriptionDetail.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
        prescriptionDetail.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
        prescriptionDetail.setExternalInvestigation("External Investigation");
        prescriptionDetail.setHypertensionScreeningConfirmed(true);
        prescriptionDetail.setInstruction("Instruction");
        prescriptionDetail.setLastModDate(mock(Timestamp.class));
        prescriptionDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        prescriptionDetail.setOralCancerConfirmed(true);
        prescriptionDetail.setParkingPlaceID(1);
        prescriptionDetail.setPrescribedDrugs(new ArrayList<>());
        prescriptionDetail.setPrescriptionID(1L);
        prescriptionDetail.setProcessed("Processed");
        prescriptionDetail.setProviderServiceMapID(1);
        prescriptionDetail.setProvisionalDiagnosisList(new ArrayList<>());
        prescriptionDetail.setRemarks("Remarks");
        prescriptionDetail.setReservedForChange("Reserved For Change");
        prescriptionDetail.setSyncedBy("Synced By");
        prescriptionDetail.setSyncedDate(mock(Timestamp.class));
        prescriptionDetail.setTreatmentsOnSideEffects("Treatments On Side Effects");
        prescriptionDetail.setVanID(1);
        prescriptionDetail.setVanSerialNo(1L);
        prescriptionDetail.setVehicalNo("Vehical No");
        prescriptionDetail.setVisitCode(1L);

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

        // Act
        int actualUpdateScreeningConfirmedStatusByDocSpecialistResult = commonNcdScreeningService
                .updateScreeningConfirmedStatusByDocSpecialist(prescriptionDetail, commonUtilityClass);

        // Assert
        verify(cervicalCancerScreeningRepo).updateCervicalScreeningConfirmedStatus(isA(Long.class), isA(Long.class),
                isA(Boolean.class));
        verify(diabetesScreeningRepo).updateDiabetesScreeningConfirmedStatus(isA(Long.class), isA(Long.class),
                isA(Boolean.class));
        verify(hypertensionScreeningRepo).updateHypertensionScreeningConfirmedStatus(isA(Long.class), isA(Long.class),
                isA(Boolean.class));
        verify(oralCancerScreeningRepo).updateOralScreeningConfirmedStatus(isA(Long.class), isA(Long.class),
                isA(Boolean.class));
        assertEquals(1, actualUpdateScreeningConfirmedStatusByDocSpecialistResult);
    }

    @Test
    void testUpdateScreeningConfirmedStatusByDocSpecialist3() throws IEMRException {
        // Arrange
        when(breastCancerScreeningRepo.updateBreastScreeningConfirmedStatus(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(1);
        when(diabetesScreeningRepo.updateDiabetesScreeningConfirmedStatus(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(1);
        when(hypertensionScreeningRepo.updateHypertensionScreeningConfirmedStatus(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(1);
        when(oralCancerScreeningRepo.updateOralScreeningConfirmedStatus(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(1);

        PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
        prescriptionDetail.setBenVisitID(1L);
        prescriptionDetail.setBeneficiaryRegID(1L);
        prescriptionDetail.setBreastCancerConfirmed(true);
        prescriptionDetail.setCervicalCancerConfirmed(null);
        prescriptionDetail.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
        prescriptionDetail.setCounsellingProvided("Counselling Provided");
        prescriptionDetail.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
        prescriptionDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        prescriptionDetail.setCreatedDate(mock(Timestamp.class));
        prescriptionDetail.setDeleted(true);
        prescriptionDetail.setDiabetesScreeningConfirmed(true);
        prescriptionDetail.setDiagnosisProvided("Diagnosis Provided");
        prescriptionDetail.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
        prescriptionDetail.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
        prescriptionDetail.setExternalInvestigation("External Investigation");
        prescriptionDetail.setHypertensionScreeningConfirmed(true);
        prescriptionDetail.setInstruction("Instruction");
        prescriptionDetail.setLastModDate(mock(Timestamp.class));
        prescriptionDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        prescriptionDetail.setOralCancerConfirmed(true);
        prescriptionDetail.setParkingPlaceID(1);
        prescriptionDetail.setPrescribedDrugs(new ArrayList<>());
        prescriptionDetail.setPrescriptionID(1L);
        prescriptionDetail.setProcessed("Processed");
        prescriptionDetail.setProviderServiceMapID(1);
        prescriptionDetail.setProvisionalDiagnosisList(new ArrayList<>());
        prescriptionDetail.setRemarks("Remarks");
        prescriptionDetail.setReservedForChange("Reserved For Change");
        prescriptionDetail.setSyncedBy("Synced By");
        prescriptionDetail.setSyncedDate(mock(Timestamp.class));
        prescriptionDetail.setTreatmentsOnSideEffects("Treatments On Side Effects");
        prescriptionDetail.setVanID(1);
        prescriptionDetail.setVanSerialNo(1L);
        prescriptionDetail.setVehicalNo("Vehical No");
        prescriptionDetail.setVisitCode(1L);

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

        // Act
        int actualUpdateScreeningConfirmedStatusByDocSpecialistResult = commonNcdScreeningService
                .updateScreeningConfirmedStatusByDocSpecialist(prescriptionDetail, commonUtilityClass);

        // Assert
        verify(breastCancerScreeningRepo).updateBreastScreeningConfirmedStatus(isA(Long.class), isA(Long.class),
                isA(Boolean.class));
        verify(diabetesScreeningRepo).updateDiabetesScreeningConfirmedStatus(isA(Long.class), isA(Long.class),
                isA(Boolean.class));
        verify(hypertensionScreeningRepo).updateHypertensionScreeningConfirmedStatus(isA(Long.class), isA(Long.class),
                isA(Boolean.class));
        verify(oralCancerScreeningRepo).updateOralScreeningConfirmedStatus(isA(Long.class), isA(Long.class),
                isA(Boolean.class));
        assertEquals(1, actualUpdateScreeningConfirmedStatusByDocSpecialistResult);
    }

    @Test
    void testUpdateScreeningConfirmedStatusByDocSpecialist4() throws IEMRException {
        // Arrange
        when(breastCancerScreeningRepo.updateBreastScreeningConfirmedStatus(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(1);
        when(cervicalCancerScreeningRepo.updateCervicalScreeningConfirmedStatus(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(1);
        when(hypertensionScreeningRepo.updateHypertensionScreeningConfirmedStatus(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(1);
        when(oralCancerScreeningRepo.updateOralScreeningConfirmedStatus(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(1);

        PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
        prescriptionDetail.setBenVisitID(1L);
        prescriptionDetail.setBeneficiaryRegID(1L);
        prescriptionDetail.setBreastCancerConfirmed(true);
        prescriptionDetail.setCervicalCancerConfirmed(true);
        prescriptionDetail.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
        prescriptionDetail.setCounsellingProvided("Counselling Provided");
        prescriptionDetail.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
        prescriptionDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        prescriptionDetail.setCreatedDate(mock(Timestamp.class));
        prescriptionDetail.setDeleted(true);
        prescriptionDetail.setDiabetesScreeningConfirmed(null);
        prescriptionDetail.setDiagnosisProvided("Diagnosis Provided");
        prescriptionDetail.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
        prescriptionDetail.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
        prescriptionDetail.setExternalInvestigation("External Investigation");
        prescriptionDetail.setHypertensionScreeningConfirmed(true);
        prescriptionDetail.setInstruction("Instruction");
        prescriptionDetail.setLastModDate(mock(Timestamp.class));
        prescriptionDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        prescriptionDetail.setOralCancerConfirmed(true);
        prescriptionDetail.setParkingPlaceID(1);
        prescriptionDetail.setPrescribedDrugs(new ArrayList<>());
        prescriptionDetail.setPrescriptionID(1L);
        prescriptionDetail.setProcessed("Processed");
        prescriptionDetail.setProviderServiceMapID(1);
        prescriptionDetail.setProvisionalDiagnosisList(new ArrayList<>());
        prescriptionDetail.setRemarks("Remarks");
        prescriptionDetail.setReservedForChange("Reserved For Change");
        prescriptionDetail.setSyncedBy("Synced By");
        prescriptionDetail.setSyncedDate(mock(Timestamp.class));
        prescriptionDetail.setTreatmentsOnSideEffects("Treatments On Side Effects");
        prescriptionDetail.setVanID(1);
        prescriptionDetail.setVanSerialNo(1L);
        prescriptionDetail.setVehicalNo("Vehical No");
        prescriptionDetail.setVisitCode(1L);

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

        // Act
        int actualUpdateScreeningConfirmedStatusByDocSpecialistResult = commonNcdScreeningService
                .updateScreeningConfirmedStatusByDocSpecialist(prescriptionDetail, commonUtilityClass);

        // Assert
        verify(breastCancerScreeningRepo).updateBreastScreeningConfirmedStatus(isA(Long.class), isA(Long.class),
                isA(Boolean.class));
        verify(cervicalCancerScreeningRepo).updateCervicalScreeningConfirmedStatus(isA(Long.class), isA(Long.class),
                isA(Boolean.class));
        verify(hypertensionScreeningRepo).updateHypertensionScreeningConfirmedStatus(isA(Long.class), isA(Long.class),
                isA(Boolean.class));
        verify(oralCancerScreeningRepo).updateOralScreeningConfirmedStatus(isA(Long.class), isA(Long.class),
                isA(Boolean.class));
        assertEquals(1, actualUpdateScreeningConfirmedStatusByDocSpecialistResult);
    }

    @Test
    void testUpdateScreeningConfirmedStatusByDocSpecialist5() throws IEMRException {
        // Arrange
        when(breastCancerScreeningRepo.updateBreastScreeningConfirmedStatus(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(1);
        when(cervicalCancerScreeningRepo.updateCervicalScreeningConfirmedStatus(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(1);
        when(diabetesScreeningRepo.updateDiabetesScreeningConfirmedStatus(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(1);
        when(oralCancerScreeningRepo.updateOralScreeningConfirmedStatus(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(1);

        PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
        prescriptionDetail.setBenVisitID(1L);
        prescriptionDetail.setBeneficiaryRegID(1L);
        prescriptionDetail.setBreastCancerConfirmed(true);
        prescriptionDetail.setCervicalCancerConfirmed(true);
        prescriptionDetail.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
        prescriptionDetail.setCounsellingProvided("Counselling Provided");
        prescriptionDetail.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
        prescriptionDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        prescriptionDetail.setCreatedDate(mock(Timestamp.class));
        prescriptionDetail.setDeleted(true);
        prescriptionDetail.setDiabetesScreeningConfirmed(true);
        prescriptionDetail.setDiagnosisProvided("Diagnosis Provided");
        prescriptionDetail.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
        prescriptionDetail.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
        prescriptionDetail.setExternalInvestigation("External Investigation");
        prescriptionDetail.setHypertensionScreeningConfirmed(null);
        prescriptionDetail.setInstruction("Instruction");
        prescriptionDetail.setLastModDate(mock(Timestamp.class));
        prescriptionDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        prescriptionDetail.setOralCancerConfirmed(true);
        prescriptionDetail.setParkingPlaceID(1);
        prescriptionDetail.setPrescribedDrugs(new ArrayList<>());
        prescriptionDetail.setPrescriptionID(1L);
        prescriptionDetail.setProcessed("Processed");
        prescriptionDetail.setProviderServiceMapID(1);
        prescriptionDetail.setProvisionalDiagnosisList(new ArrayList<>());
        prescriptionDetail.setRemarks("Remarks");
        prescriptionDetail.setReservedForChange("Reserved For Change");
        prescriptionDetail.setSyncedBy("Synced By");
        prescriptionDetail.setSyncedDate(mock(Timestamp.class));
        prescriptionDetail.setTreatmentsOnSideEffects("Treatments On Side Effects");
        prescriptionDetail.setVanID(1);
        prescriptionDetail.setVanSerialNo(1L);
        prescriptionDetail.setVehicalNo("Vehical No");
        prescriptionDetail.setVisitCode(1L);

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

        // Act
        int actualUpdateScreeningConfirmedStatusByDocSpecialistResult = commonNcdScreeningService
                .updateScreeningConfirmedStatusByDocSpecialist(prescriptionDetail, commonUtilityClass);

        // Assert
        verify(breastCancerScreeningRepo).updateBreastScreeningConfirmedStatus(isA(Long.class), isA(Long.class),
                isA(Boolean.class));
        verify(cervicalCancerScreeningRepo).updateCervicalScreeningConfirmedStatus(isA(Long.class), isA(Long.class),
                isA(Boolean.class));
        verify(diabetesScreeningRepo).updateDiabetesScreeningConfirmedStatus(isA(Long.class), isA(Long.class),
                isA(Boolean.class));
        verify(oralCancerScreeningRepo).updateOralScreeningConfirmedStatus(isA(Long.class), isA(Long.class),
                isA(Boolean.class));
        assertEquals(1, actualUpdateScreeningConfirmedStatusByDocSpecialistResult);
    }

    @Test
    void testUpdateScreeningConfirmedStatusByDocSpecialist6() throws IEMRException {
        // Arrange
        when(breastCancerScreeningRepo.updateBreastScreeningConfirmedStatus(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(1);
        when(cervicalCancerScreeningRepo.updateCervicalScreeningConfirmedStatus(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(1);
        when(diabetesScreeningRepo.updateDiabetesScreeningConfirmedStatus(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(1);
        when(hypertensionScreeningRepo.updateHypertensionScreeningConfirmedStatus(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(1);

        PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
        prescriptionDetail.setBenVisitID(1L);
        prescriptionDetail.setBeneficiaryRegID(1L);
        prescriptionDetail.setBreastCancerConfirmed(true);
        prescriptionDetail.setCervicalCancerConfirmed(true);
        prescriptionDetail.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
        prescriptionDetail.setCounsellingProvided("Counselling Provided");
        prescriptionDetail.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
        prescriptionDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        prescriptionDetail.setCreatedDate(mock(Timestamp.class));
        prescriptionDetail.setDeleted(true);
        prescriptionDetail.setDiabetesScreeningConfirmed(true);
        prescriptionDetail.setDiagnosisProvided("Diagnosis Provided");
        prescriptionDetail.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
        prescriptionDetail.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
        prescriptionDetail.setExternalInvestigation("External Investigation");
        prescriptionDetail.setHypertensionScreeningConfirmed(true);
        prescriptionDetail.setInstruction("Instruction");
        prescriptionDetail.setLastModDate(mock(Timestamp.class));
        prescriptionDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        prescriptionDetail.setOralCancerConfirmed(null);
        prescriptionDetail.setParkingPlaceID(1);
        prescriptionDetail.setPrescribedDrugs(new ArrayList<>());
        prescriptionDetail.setPrescriptionID(1L);
        prescriptionDetail.setProcessed("Processed");
        prescriptionDetail.setProviderServiceMapID(1);
        prescriptionDetail.setProvisionalDiagnosisList(new ArrayList<>());
        prescriptionDetail.setRemarks("Remarks");
        prescriptionDetail.setReservedForChange("Reserved For Change");
        prescriptionDetail.setSyncedBy("Synced By");
        prescriptionDetail.setSyncedDate(mock(Timestamp.class));
        prescriptionDetail.setTreatmentsOnSideEffects("Treatments On Side Effects");
        prescriptionDetail.setVanID(1);
        prescriptionDetail.setVanSerialNo(1L);
        prescriptionDetail.setVehicalNo("Vehical No");
        prescriptionDetail.setVisitCode(1L);

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

        // Act
        int actualUpdateScreeningConfirmedStatusByDocSpecialistResult = commonNcdScreeningService
                .updateScreeningConfirmedStatusByDocSpecialist(prescriptionDetail, commonUtilityClass);

        // Assert
        verify(breastCancerScreeningRepo).updateBreastScreeningConfirmedStatus(isA(Long.class), isA(Long.class),
                isA(Boolean.class));
        verify(cervicalCancerScreeningRepo).updateCervicalScreeningConfirmedStatus(isA(Long.class), isA(Long.class),
                isA(Boolean.class));
        verify(diabetesScreeningRepo).updateDiabetesScreeningConfirmedStatus(isA(Long.class), isA(Long.class),
                isA(Boolean.class));
        verify(hypertensionScreeningRepo).updateHypertensionScreeningConfirmedStatus(isA(Long.class), isA(Long.class),
                isA(Boolean.class));
        assertEquals(1, actualUpdateScreeningConfirmedStatusByDocSpecialistResult);
    }
}
