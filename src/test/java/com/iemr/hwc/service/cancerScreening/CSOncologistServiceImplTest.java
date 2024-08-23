package com.iemr.hwc.service.cancerScreening;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
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

import com.iemr.hwc.data.doctor.CancerDiagnosis;
import com.iemr.hwc.data.institution.Institute;
import com.iemr.hwc.repo.doctor.CancerDiagnosisRepo;

@ExtendWith(MockitoExtension.class)
class CSOncologistServiceImplTest {
    @InjectMocks
    private CSOncologistServiceImpl cSOncologistServiceImpl;

    @Mock
    private CancerDiagnosisRepo cancerDiagnosisRepo;

    @Test
    void testSetCancerDiagnosisRepo() {
        (new CSOncologistServiceImpl()).setCancerDiagnosisRepo(mock(CancerDiagnosisRepo.class));
    }

    @Test
    void testUpdateCancerDiagnosisDetailsByOncologist() {
        // Arrange
        when(cancerDiagnosisRepo.updateDetailsByOncologist(Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any())).thenReturn(1);
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
        int actualUpdateCancerDiagnosisDetailsByOncologistResult = cSOncologistServiceImpl
                .updateCancerDiagnosisDetailsByOncologist(cancerDiagnosis);

        // Assert
        verify(cancerDiagnosisRepo).getCancerDiagnosisStatuses(isA(Long.class), isA(Long.class));
        verify(cancerDiagnosisRepo).updateDetailsByOncologist(eq("Provisional Diagnosis Oncologist"), isA(Long.class),
                isA(Long.class), eq("Jan 1, 2020 9:00am GMT+0100"), eq("U"));
        assertEquals(1, actualUpdateCancerDiagnosisDetailsByOncologistResult);
    }
}
