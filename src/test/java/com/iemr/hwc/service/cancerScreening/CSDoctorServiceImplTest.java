package com.iemr.hwc.service.cancerScreening;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.iemr.hwc.data.doctor.CancerDiagnosis;
import com.iemr.hwc.data.institution.Institute;
import com.iemr.hwc.repo.doctor.CancerDiagnosisRepo;

@ExtendWith(MockitoExtension.class)
class CSDoctorServiceImplTest {
    @InjectMocks
    private CSDoctorServiceImpl cSDoctorServiceImpl;

    @Mock
    private CancerDiagnosisRepo cancerDiagnosisRepo;

    @Test
    void testSetCancerDiagnosisRepo() {
        (new CSDoctorServiceImpl()).setCancerDiagnosisRepo(mock(CancerDiagnosisRepo.class));
    }

    @Test
    void testSaveCancerDiagnosisData() {
        // Arrange
        Institute institute = new Institute();
        institute.setBlockID(1);
        institute.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        institute.setCreatedDate(mock(Timestamp.class));
        institute.setDeleted(true);
        institute.setDistrictBranchMappingID(1);
        institute.setDistrictID(1);
        institute.setInstitutionID(1);
        institute.setInstitutionName("Institution Name");
        institute.setLastModDate(mock(Timestamp.class));
        institute.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        institute.setProcessed("Processed");
        institute.setProviderServiceMapID(1);
        institute.setStateID(1);

        CancerDiagnosis cancerDiagnosis = new CancerDiagnosis();
        cancerDiagnosis.setBenVisitID(1L);
        cancerDiagnosis.setBeneficiaryRegID(1L);
        cancerDiagnosis.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        cancerDiagnosis.setCreatedDate(mock(Timestamp.class));
        cancerDiagnosis.setDeleted(true);
        cancerDiagnosis.setID(1L);
        cancerDiagnosis.setInstitute(institute);
        cancerDiagnosis.setLastModDate(mock(Timestamp.class));
        cancerDiagnosis.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        cancerDiagnosis.setParkingPlaceID(1);
        cancerDiagnosis.setProcessed("Processed");
        cancerDiagnosis.setProviderServiceMapID(1);
        cancerDiagnosis.setProvisionalDiagnosisOncologist("Provisional Diagnosis Oncologist");
        cancerDiagnosis.setProvisionalDiagnosisPrimaryDoctor("Provisional Diagnosis Primary Doctor");
        cancerDiagnosis.setReferralReason("Just cause");
        cancerDiagnosis.setReferredToInstituteID(1);
        cancerDiagnosis.setReferredToInstituteName("Referred To Institute Name");
        cancerDiagnosis.setRefrredToAdditionalService("Refrred To Additional Service");
        cancerDiagnosis.setRefrredToAdditionalServiceList(new ArrayList<>());
        cancerDiagnosis.setRemarks("Remarks");
        cancerDiagnosis.setReservedForChange("Reserved For Change");
        cancerDiagnosis.setRevisitDate(mock(Timestamp.class));
        cancerDiagnosis.setSyncedBy("Synced By");
        cancerDiagnosis.setSyncedDate(mock(Timestamp.class));
        cancerDiagnosis.setVanID(1);
        cancerDiagnosis.setVanSerialNo(1L);
        cancerDiagnosis.setVehicalNo("Vehical No");
        cancerDiagnosis.setVisitCode(1L);
        when(cancerDiagnosisRepo.save(Mockito.<CancerDiagnosis>any())).thenReturn(cancerDiagnosis);

        Institute institute2 = new Institute();
        institute2.setBlockID(1);
        institute2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        institute2.setCreatedDate(mock(Timestamp.class));
        institute2.setDeleted(true);
        institute2.setDistrictBranchMappingID(1);
        institute2.setDistrictID(1);
        institute2.setInstitutionID(1);
        institute2.setInstitutionName("Institution Name");
        institute2.setLastModDate(mock(Timestamp.class));
        institute2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        institute2.setProcessed("Processed");
        institute2.setProviderServiceMapID(1);
        institute2.setStateID(1);

        CancerDiagnosis cancerDiagnosis2 = new CancerDiagnosis();
        cancerDiagnosis2.setBenVisitID(1L);
        cancerDiagnosis2.setBeneficiaryRegID(1L);
        cancerDiagnosis2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        cancerDiagnosis2.setCreatedDate(mock(Timestamp.class));
        cancerDiagnosis2.setDeleted(true);
        cancerDiagnosis2.setID(1L);
        cancerDiagnosis2.setInstitute(institute2);
        cancerDiagnosis2.setLastModDate(mock(Timestamp.class));
        cancerDiagnosis2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        cancerDiagnosis2.setParkingPlaceID(1);
        cancerDiagnosis2.setProcessed("Processed");
        cancerDiagnosis2.setProviderServiceMapID(1);
        cancerDiagnosis2.setProvisionalDiagnosisOncologist("Provisional Diagnosis Oncologist");
        cancerDiagnosis2.setProvisionalDiagnosisPrimaryDoctor("Provisional Diagnosis Primary Doctor");
        cancerDiagnosis2.setReferralReason("Just cause");
        cancerDiagnosis2.setReferredToInstituteID(1);
        cancerDiagnosis2.setReferredToInstituteName("Referred To Institute Name");
        cancerDiagnosis2.setRefrredToAdditionalService("Refrred To Additional Service");
        cancerDiagnosis2.setRefrredToAdditionalServiceList(new ArrayList<>());
        cancerDiagnosis2.setRemarks("Remarks");
        cancerDiagnosis2.setReservedForChange("Reserved For Change");
        cancerDiagnosis2.setRevisitDate(mock(Timestamp.class));
        cancerDiagnosis2.setSyncedBy("Synced By");
        cancerDiagnosis2.setSyncedDate(mock(Timestamp.class));
        cancerDiagnosis2.setVanID(1);
        cancerDiagnosis2.setVanSerialNo(1L);
        cancerDiagnosis2.setVehicalNo("Vehical No");
        cancerDiagnosis2.setVisitCode(1L);

        // Act
        Long actualSaveCancerDiagnosisDataResult = cSDoctorServiceImpl.saveCancerDiagnosisData(cancerDiagnosis2);

        // Assert
        verify(cancerDiagnosisRepo).save(isA(CancerDiagnosis.class));
        assertEquals("", cancerDiagnosis2.getRefrredToAdditionalService());
        assertEquals(1L, actualSaveCancerDiagnosisDataResult.longValue());
    }

    @Test
    void testGetCancerDiagnosisObj() {
        // Arrange
        Institute institute = new Institute();
        institute.setBlockID(1);
        institute.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        institute.setCreatedDate(mock(Timestamp.class));
        institute.setDeleted(true);
        institute.setDistrictBranchMappingID(1);
        institute.setDistrictID(1);
        institute.setInstitutionID(1);
        institute.setInstitutionName("Institution Name");
        institute.setLastModDate(mock(Timestamp.class));
        institute.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        institute.setProcessed("Processed");
        institute.setProviderServiceMapID(1);
        institute.setStateID(1);

        CancerDiagnosis cancerDiagnosis = new CancerDiagnosis();
        cancerDiagnosis.setBenVisitID(1L);
        cancerDiagnosis.setBeneficiaryRegID(1L);
        cancerDiagnosis.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        cancerDiagnosis.setCreatedDate(mock(Timestamp.class));
        cancerDiagnosis.setDeleted(true);
        cancerDiagnosis.setID(1L);
        cancerDiagnosis.setInstitute(institute);
        cancerDiagnosis.setLastModDate(mock(Timestamp.class));
        cancerDiagnosis.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        cancerDiagnosis.setParkingPlaceID(1);
        cancerDiagnosis.setProcessed("Processed");
        cancerDiagnosis.setProviderServiceMapID(1);
        cancerDiagnosis.setProvisionalDiagnosisOncologist("Provisional Diagnosis Oncologist");
        cancerDiagnosis.setProvisionalDiagnosisPrimaryDoctor("Provisional Diagnosis Primary Doctor");
        cancerDiagnosis.setReferralReason("Just cause");
        cancerDiagnosis.setReferredToInstituteID(1);
        cancerDiagnosis.setReferredToInstituteName("Referred To Institute Name");
        cancerDiagnosis.setRefrredToAdditionalService("Refrred To Additional Service");
        cancerDiagnosis.setRefrredToAdditionalServiceList(new ArrayList<>());
        cancerDiagnosis.setRemarks("Remarks");
        cancerDiagnosis.setReservedForChange("Reserved For Change");
        cancerDiagnosis.setRevisitDate(mock(Timestamp.class));
        cancerDiagnosis.setSyncedBy("Synced By");
        cancerDiagnosis.setSyncedDate(mock(Timestamp.class));
        cancerDiagnosis.setVanID(1);
        cancerDiagnosis.setVanSerialNo(1L);
        cancerDiagnosis.setVehicalNo("Vehical No");
        cancerDiagnosis.setVisitCode(1L);

        // Act
        CancerDiagnosis actualCancerDiagnosisObj = cSDoctorServiceImpl.getCancerDiagnosisObj(cancerDiagnosis);

        // Assert
        assertEquals("", actualCancerDiagnosisObj.getRefrredToAdditionalService());
        assertSame(cancerDiagnosis, actualCancerDiagnosisObj);
    }

    @Test
    void testGetCancerDiagnosisObj2() {
        // Arrange
        Institute institute = new Institute();
        institute.setBlockID(1);
        institute.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        institute.setCreatedDate(mock(Timestamp.class));
        institute.setDeleted(true);
        institute.setDistrictBranchMappingID(1);
        institute.setDistrictID(1);
        institute.setInstitutionID(1);
        institute.setInstitutionName("Institution Name");
        institute.setLastModDate(mock(Timestamp.class));
        institute.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        institute.setProcessed("Processed");
        institute.setProviderServiceMapID(1);
        institute.setStateID(1);

        ArrayList<String> refrredToAdditionalServiceList = new ArrayList<>();
        refrredToAdditionalServiceList.add("foo");

        CancerDiagnosis cancerDiagnosis = new CancerDiagnosis();
        cancerDiagnosis.setBenVisitID(1L);
        cancerDiagnosis.setBeneficiaryRegID(1L);
        cancerDiagnosis.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        cancerDiagnosis.setCreatedDate(mock(Timestamp.class));
        cancerDiagnosis.setDeleted(true);
        cancerDiagnosis.setID(1L);
        cancerDiagnosis.setInstitute(institute);
        cancerDiagnosis.setLastModDate(mock(Timestamp.class));
        cancerDiagnosis.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        cancerDiagnosis.setParkingPlaceID(1);
        cancerDiagnosis.setProcessed("Processed");
        cancerDiagnosis.setProviderServiceMapID(1);
        cancerDiagnosis.setProvisionalDiagnosisOncologist("Provisional Diagnosis Oncologist");
        cancerDiagnosis.setProvisionalDiagnosisPrimaryDoctor("Provisional Diagnosis Primary Doctor");
        cancerDiagnosis.setReferralReason("Just cause");
        cancerDiagnosis.setReferredToInstituteID(1);
        cancerDiagnosis.setReferredToInstituteName("Referred To Institute Name");
        cancerDiagnosis.setRefrredToAdditionalService("Refrred To Additional Service");
        cancerDiagnosis.setRefrredToAdditionalServiceList(refrredToAdditionalServiceList);
        cancerDiagnosis.setRemarks("Remarks");
        cancerDiagnosis.setReservedForChange("Reserved For Change");
        cancerDiagnosis.setRevisitDate(mock(Timestamp.class));
        cancerDiagnosis.setSyncedBy("Synced By");
        cancerDiagnosis.setSyncedDate(mock(Timestamp.class));
        cancerDiagnosis.setVanID(1);
        cancerDiagnosis.setVanSerialNo(1L);
        cancerDiagnosis.setVehicalNo("Vehical No");
        cancerDiagnosis.setVisitCode(1L);

        // Act
        CancerDiagnosis actualCancerDiagnosisObj = cSDoctorServiceImpl.getCancerDiagnosisObj(cancerDiagnosis);

        // Assert
        assertEquals("foo", actualCancerDiagnosisObj.getRefrredToAdditionalService());
        assertSame(cancerDiagnosis, actualCancerDiagnosisObj);
    }

    @Test
    void testGetCancerDiagnosisObj3() {
        // Arrange
        Institute institute = new Institute();
        institute.setBlockID(1);
        institute.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        institute.setCreatedDate(mock(Timestamp.class));
        institute.setDeleted(true);
        institute.setDistrictBranchMappingID(1);
        institute.setDistrictID(1);
        institute.setInstitutionID(1);
        institute.setInstitutionName("Institution Name");
        institute.setLastModDate(mock(Timestamp.class));
        institute.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        institute.setProcessed("Processed");
        institute.setProviderServiceMapID(1);
        institute.setStateID(1);

        ArrayList<String> refrredToAdditionalServiceList = new ArrayList<>();
        refrredToAdditionalServiceList.add("");
        refrredToAdditionalServiceList.add("foo");

        CancerDiagnosis cancerDiagnosis = new CancerDiagnosis();
        cancerDiagnosis.setBenVisitID(1L);
        cancerDiagnosis.setBeneficiaryRegID(1L);
        cancerDiagnosis.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        cancerDiagnosis.setCreatedDate(mock(Timestamp.class));
        cancerDiagnosis.setDeleted(true);
        cancerDiagnosis.setID(1L);
        cancerDiagnosis.setInstitute(institute);
        cancerDiagnosis.setLastModDate(mock(Timestamp.class));
        cancerDiagnosis.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        cancerDiagnosis.setParkingPlaceID(1);
        cancerDiagnosis.setProcessed("Processed");
        cancerDiagnosis.setProviderServiceMapID(1);
        cancerDiagnosis.setProvisionalDiagnosisOncologist("Provisional Diagnosis Oncologist");
        cancerDiagnosis.setProvisionalDiagnosisPrimaryDoctor("Provisional Diagnosis Primary Doctor");
        cancerDiagnosis.setReferralReason("Just cause");
        cancerDiagnosis.setReferredToInstituteID(1);
        cancerDiagnosis.setReferredToInstituteName("Referred To Institute Name");
        cancerDiagnosis.setRefrredToAdditionalService("Refrred To Additional Service");
        cancerDiagnosis.setRefrredToAdditionalServiceList(refrredToAdditionalServiceList);
        cancerDiagnosis.setRemarks("Remarks");
        cancerDiagnosis.setReservedForChange("Reserved For Change");
        cancerDiagnosis.setRevisitDate(mock(Timestamp.class));
        cancerDiagnosis.setSyncedBy("Synced By");
        cancerDiagnosis.setSyncedDate(mock(Timestamp.class));
        cancerDiagnosis.setVanID(1);
        cancerDiagnosis.setVanSerialNo(1L);
        cancerDiagnosis.setVehicalNo("Vehical No");
        cancerDiagnosis.setVisitCode(1L);

        // Act
        CancerDiagnosis actualCancerDiagnosisObj = cSDoctorServiceImpl.getCancerDiagnosisObj(cancerDiagnosis);

        // Assert
        assertEquals(",foo", actualCancerDiagnosisObj.getRefrredToAdditionalService());
        assertSame(cancerDiagnosis, actualCancerDiagnosisObj);
    }

    @Test
    void testGetBenDoctorEnteredDataForCaseSheet() {
        // Arrange
        Institute institute = new Institute();
        institute.setBlockID(1);
        institute.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        institute.setCreatedDate(mock(Timestamp.class));
        institute.setDeleted(true);
        institute.setDistrictBranchMappingID(1);
        institute.setDistrictID(1);
        institute.setInstitutionID(1);
        institute.setInstitutionName("Institution Name");
        institute.setLastModDate(mock(Timestamp.class));
        institute.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        institute.setProcessed("Processed");
        institute.setProviderServiceMapID(1);
        institute.setStateID(1);

        CancerDiagnosis cancerDiagnosis = new CancerDiagnosis();
        cancerDiagnosis.setBenVisitID(1L);
        cancerDiagnosis.setBeneficiaryRegID(1L);
        cancerDiagnosis.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        cancerDiagnosis.setCreatedDate(mock(Timestamp.class));
        cancerDiagnosis.setDeleted(true);
        cancerDiagnosis.setID(1L);
        cancerDiagnosis.setInstitute(institute);
        cancerDiagnosis.setLastModDate(mock(Timestamp.class));
        cancerDiagnosis.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        cancerDiagnosis.setParkingPlaceID(1);
        cancerDiagnosis.setProcessed("Processed");
        cancerDiagnosis.setProviderServiceMapID(1);
        cancerDiagnosis.setProvisionalDiagnosisOncologist("Provisional Diagnosis Oncologist");
        cancerDiagnosis.setProvisionalDiagnosisPrimaryDoctor("Provisional Diagnosis Primary Doctor");
        cancerDiagnosis.setReferralReason("Just cause");
        cancerDiagnosis.setReferredToInstituteID(1);
        cancerDiagnosis.setReferredToInstituteName("Referred To Institute Name");
        cancerDiagnosis.setRefrredToAdditionalService("Refrred To Additional Service");
        cancerDiagnosis.setRefrredToAdditionalServiceList(new ArrayList<>());
        cancerDiagnosis.setRemarks("Remarks");
        cancerDiagnosis.setReservedForChange("Reserved For Change");
        cancerDiagnosis.setRevisitDate(mock(Timestamp.class));
        cancerDiagnosis.setSyncedBy("Synced By");
        cancerDiagnosis.setSyncedDate(mock(Timestamp.class));
        cancerDiagnosis.setVanID(1);
        cancerDiagnosis.setVanSerialNo(1L);
        cancerDiagnosis.setVehicalNo("Vehical No");
        cancerDiagnosis.setVisitCode(1L);
        when(cancerDiagnosisRepo.getBenCancerDiagnosisDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(cancerDiagnosis);

        // Act
        Map<String, Object> actualBenDoctorEnteredDataForCaseSheet = cSDoctorServiceImpl
                .getBenDoctorEnteredDataForCaseSheet(1L, 1L);

        // Assert
        verify(cancerDiagnosisRepo).getBenCancerDiagnosisDetails(isA(Long.class), isA(Long.class));
        assertEquals(1, actualBenDoctorEnteredDataForCaseSheet.size());
        assertEquals("Institution Name",
                ((CancerDiagnosis) actualBenDoctorEnteredDataForCaseSheet.get("diagnosis")).getReferredToInstituteName());
        assertEquals(1,
                ((CancerDiagnosis) actualBenDoctorEnteredDataForCaseSheet.get("diagnosis")).getRefrredToAdditionalServiceList()
                        .size());
    }

    @Test
    void testGetBenCancerDiagnosisData() {
        // Arrange
        Institute institute = new Institute();
        institute.setBlockID(1);
        institute.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        institute.setCreatedDate(mock(Timestamp.class));
        institute.setDeleted(true);
        institute.setDistrictBranchMappingID(1);
        institute.setDistrictID(1);
        institute.setInstitutionID(1);
        institute.setInstitutionName("Institution Name");
        institute.setLastModDate(mock(Timestamp.class));
        institute.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        institute.setProcessed("Processed");
        institute.setProviderServiceMapID(1);
        institute.setStateID(1);

        CancerDiagnosis cancerDiagnosis = new CancerDiagnosis();
        cancerDiagnosis.setBenVisitID(1L);
        cancerDiagnosis.setBeneficiaryRegID(1L);
        cancerDiagnosis.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        cancerDiagnosis.setCreatedDate(mock(Timestamp.class));
        cancerDiagnosis.setDeleted(true);
        cancerDiagnosis.setID(1L);
        cancerDiagnosis.setInstitute(institute);
        cancerDiagnosis.setLastModDate(mock(Timestamp.class));
        cancerDiagnosis.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        cancerDiagnosis.setParkingPlaceID(1);
        cancerDiagnosis.setProcessed("Processed");
        cancerDiagnosis.setProviderServiceMapID(1);
        cancerDiagnosis.setProvisionalDiagnosisOncologist("Provisional Diagnosis Oncologist");
        cancerDiagnosis.setProvisionalDiagnosisPrimaryDoctor("Provisional Diagnosis Primary Doctor");
        cancerDiagnosis.setReferralReason("Just cause");
        cancerDiagnosis.setReferredToInstituteID(1);
        cancerDiagnosis.setReferredToInstituteName("Referred To Institute Name");
        cancerDiagnosis.setRefrredToAdditionalService("Refrred To Additional Service");
        cancerDiagnosis.setRefrredToAdditionalServiceList(new ArrayList<>());
        cancerDiagnosis.setRemarks("Remarks");
        cancerDiagnosis.setReservedForChange("Reserved For Change");
        cancerDiagnosis.setRevisitDate(mock(Timestamp.class));
        cancerDiagnosis.setSyncedBy("Synced By");
        cancerDiagnosis.setSyncedDate(mock(Timestamp.class));
        cancerDiagnosis.setVanID(1);
        cancerDiagnosis.setVanSerialNo(1L);
        cancerDiagnosis.setVehicalNo("Vehical No");
        cancerDiagnosis.setVisitCode(1L);
        when(cancerDiagnosisRepo.getBenCancerDiagnosisDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(cancerDiagnosis);

        // Act
        CancerDiagnosis actualBenCancerDiagnosisData = cSDoctorServiceImpl.getBenCancerDiagnosisData(1L, 1L);

        // Assert
        verify(cancerDiagnosisRepo).getBenCancerDiagnosisDetails(isA(Long.class), isA(Long.class));
        assertEquals("Institution Name", actualBenCancerDiagnosisData.getReferredToInstituteName());
        assertEquals(1, actualBenCancerDiagnosisData.getRefrredToAdditionalServiceList().size());
        assertSame(cancerDiagnosis, actualBenCancerDiagnosisData);
    }

    @Test
    void testUpdateCancerDiagnosisDetailsByDoctor() {
        // Arrange
        when(cancerDiagnosisRepo.updateCancerDiagnosisDetailsByDoctor(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Integer>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Timestamp>any(), Mockito.<String>any())).thenReturn(1);
        when(cancerDiagnosisRepo.getCancerDiagnosisStatuses(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Cancer Diagnosis Statuses");

        Institute institute = new Institute();
        institute.setBlockID(1);
        institute.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        institute.setCreatedDate(mock(Timestamp.class));
        institute.setDeleted(true);
        institute.setDistrictBranchMappingID(1);
        institute.setDistrictID(1);
        institute.setInstitutionID(1);
        institute.setInstitutionName("Institution Name");
        institute.setLastModDate(mock(Timestamp.class));
        institute.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        institute.setProcessed("Processed");
        institute.setProviderServiceMapID(1);
        institute.setStateID(1);

        CancerDiagnosis cancerDiagnosis = new CancerDiagnosis();
        cancerDiagnosis.setBenVisitID(1L);
        cancerDiagnosis.setBeneficiaryRegID(1L);
        cancerDiagnosis.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        cancerDiagnosis.setCreatedDate(mock(Timestamp.class));
        cancerDiagnosis.setDeleted(true);
        cancerDiagnosis.setID(1L);
        cancerDiagnosis.setInstitute(institute);
        cancerDiagnosis.setLastModDate(mock(Timestamp.class));
        cancerDiagnosis.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        cancerDiagnosis.setParkingPlaceID(1);
        cancerDiagnosis.setProcessed("Processed");
        cancerDiagnosis.setProviderServiceMapID(1);
        cancerDiagnosis.setProvisionalDiagnosisOncologist("Provisional Diagnosis Oncologist");
        cancerDiagnosis.setProvisionalDiagnosisPrimaryDoctor("Provisional Diagnosis Primary Doctor");
        cancerDiagnosis.setReferralReason("Just cause");
        cancerDiagnosis.setReferredToInstituteID(1);
        cancerDiagnosis.setReferredToInstituteName("Referred To Institute Name");
        cancerDiagnosis.setRefrredToAdditionalService("Refrred To Additional Service");
        cancerDiagnosis.setRefrredToAdditionalServiceList(new ArrayList<>());
        cancerDiagnosis.setRemarks("Remarks");
        cancerDiagnosis.setReservedForChange("Reserved For Change");
        cancerDiagnosis.setRevisitDate(mock(Timestamp.class));
        cancerDiagnosis.setSyncedBy("Synced By");
        cancerDiagnosis.setSyncedDate(mock(Timestamp.class));
        cancerDiagnosis.setVanID(1);
        cancerDiagnosis.setVanSerialNo(1L);
        cancerDiagnosis.setVehicalNo("Vehical No");
        cancerDiagnosis.setVisitCode(1L);

        // Act
        int actualUpdateCancerDiagnosisDetailsByDoctorResult = cSDoctorServiceImpl
                .updateCancerDiagnosisDetailsByDoctor(cancerDiagnosis);

        // Assert
        verify(cancerDiagnosisRepo).getCancerDiagnosisStatuses(isA(Long.class), isA(Long.class));
        verify(cancerDiagnosisRepo).updateCancerDiagnosisDetailsByDoctor(eq("Provisional Diagnosis Primary Doctor"),
                eq("Remarks"), isA(Integer.class), eq(""), eq("Jan 1, 2020 9:00am GMT+0100"), eq("U"), isA(Long.class),
                isA(Long.class), isA(Timestamp.class), eq("Just cause"));
        assertEquals("", cancerDiagnosis.getRefrredToAdditionalService());
        assertEquals(1, actualUpdateCancerDiagnosisDetailsByDoctorResult);
    }
}
