package com.iemr.hwc.service.generalOPD;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
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

import com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo;

@ExtendWith(MockitoExtension.class)
class GeneralOPDDoctorServiceImplTest {
    @InjectMocks
    private GeneralOPDDoctorServiceImpl generalOPDDoctorServiceImpl;

    @Mock
    private PrescriptionDetailRepo prescriptionDetailRepo;

    @Test
    void testSetPrescriptionDetailRepo() {
        (new GeneralOPDDoctorServiceImpl()).setPrescriptionDetailRepo(mock(PrescriptionDetailRepo.class));
    }

    @Test
    void testGetGeneralOPDDiagnosisDetails() {
        // Arrange
        when(prescriptionDetailRepo.findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(Mockito.<Long>any(),
                Mockito.<Long>any())).thenReturn(new ArrayList<>());

        // Act
        String actualGeneralOPDDiagnosisDetails = generalOPDDoctorServiceImpl.getGeneralOPDDiagnosisDetails(1L, 1L);

        // Assert
        verify(prescriptionDetailRepo).findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(isA(Long.class),
                isA(Long.class));
        assertEquals("{}", actualGeneralOPDDiagnosisDetails);
    }
}
