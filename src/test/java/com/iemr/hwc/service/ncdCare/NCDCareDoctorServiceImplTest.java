package com.iemr.hwc.service.ncdCare;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
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

import com.iemr.hwc.data.ncdcare.NCDCareDiagnosis;
import com.iemr.hwc.repo.nurse.ncdcare.NCDCareDiagnosisRepo;
import com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.hwc.utils.exception.IEMRException;

@ExtendWith(MockitoExtension.class)
class NCDCareDoctorServiceImplTest {
    @Mock
    private NCDCareDiagnosisRepo nCDCareDiagnosisRepo;

    @InjectMocks
    private NCDCareDoctorServiceImpl nCDCareDoctorServiceImpl;

    @Mock
    private PrescriptionDetailRepo prescriptionDetailRepo;

    @Test
    void testSaveNCDDiagnosisData() throws Exception {
        // Arrange
        NCDCareDiagnosis ncdCareDiagnosis = new NCDCareDiagnosis(1L, 1L, 1, 1L, "Ncd Care Condition", "Ncd Complication",
                "Ncd Care Type", 1L, "External Investigation", "Ncd Care Condition Other");
        ncdCareDiagnosis.setID(1L);
        when(nCDCareDiagnosisRepo.save(Mockito.<NCDCareDiagnosis>any())).thenReturn(ncdCareDiagnosis);
        NCDCareDiagnosis ncdDiagnosis = new NCDCareDiagnosis(1L, 1L, 1, 1L, "Ncd Care Condition", "Ncd Complication",
                "Ncd Care Type", 1L, "External Investigation", "Ncd Care Condition Other");

        // Act
        long actualSaveNCDDiagnosisDataResult = nCDCareDoctorServiceImpl.saveNCDDiagnosisData(ncdDiagnosis);

        // Assert
        verify(nCDCareDiagnosisRepo).save(isA(NCDCareDiagnosis.class));
        assertNull(ncdDiagnosis.getNcdCareCondition());
        assertEquals(1L, actualSaveNCDDiagnosisDataResult);
    }

    @Test
    void testSaveNCDDiagnosisData2() throws Exception {
        // Arrange
        when(nCDCareDiagnosisRepo.save(Mockito.<NCDCareDiagnosis>any())).thenReturn(null);
        NCDCareDiagnosis ncdDiagnosis = new NCDCareDiagnosis(1L, 1L, 1, 1L, "Ncd Care Condition", "Ncd Complication",
                "Ncd Care Type", 1L, "External Investigation", "Ncd Care Condition Other");

        // Act
        long actualSaveNCDDiagnosisDataResult = nCDCareDoctorServiceImpl.saveNCDDiagnosisData(ncdDiagnosis);

        // Assert
        verify(nCDCareDiagnosisRepo).save(isA(NCDCareDiagnosis.class));
        assertNull(ncdDiagnosis.getNcdCareCondition());
        assertEquals(0L, actualSaveNCDDiagnosisDataResult);
    }

    @Test
    void testSaveNCDDiagnosisData3() throws Exception {
        // Arrange
        NCDCareDiagnosis ncdCareDiagnosis = mock(NCDCareDiagnosis.class);
        when(ncdCareDiagnosis.getID()).thenReturn(1L);
        when(nCDCareDiagnosisRepo.save(Mockito.<NCDCareDiagnosis>any())).thenReturn(ncdCareDiagnosis);
        NCDCareDiagnosis ncdDiagnosis = new NCDCareDiagnosis(1L, 1L, 1, 1L, "Ncd Care Condition", "Ncd Complication",
                "Ncd Care Type", 1L, "External Investigation", "Ncd Care Condition Other");

        // Act
        long actualSaveNCDDiagnosisDataResult = nCDCareDoctorServiceImpl.saveNCDDiagnosisData(ncdDiagnosis);

        // Assert
        verify(ncdCareDiagnosis).getID();
        verify(nCDCareDiagnosisRepo).save(isA(NCDCareDiagnosis.class));
        assertNull(ncdDiagnosis.getNcdCareCondition());
        assertEquals(1L, actualSaveNCDDiagnosisDataResult);
    }

    @Test
    void testSaveNCDDiagnosisData4() throws Exception {
        // Arrange
        NCDCareDiagnosis ncdCareDiagnosis = mock(NCDCareDiagnosis.class);
        when(ncdCareDiagnosis.getID()).thenReturn(1L);
        when(nCDCareDiagnosisRepo.save(Mockito.<NCDCareDiagnosis>any())).thenReturn(ncdCareDiagnosis);
        NCDCareDiagnosis ncdDiagnosis = mock(NCDCareDiagnosis.class);
        when(ncdDiagnosis.getNcdScreeningConditionArray()).thenReturn(new String[]{"Ncd Screening Condition Array"});
        doNothing().when(ncdDiagnosis).setNcdScreeningCondition(Mockito.<String>any());

        // Act
        long actualSaveNCDDiagnosisDataResult = nCDCareDoctorServiceImpl.saveNCDDiagnosisData(ncdDiagnosis);

        // Assert
        verify(ncdCareDiagnosis).getID();
        verify(ncdDiagnosis, atLeast(1)).getNcdScreeningConditionArray();
        verify(ncdDiagnosis).setNcdScreeningCondition(eq("Ncd Screening Condition Array"));
        verify(nCDCareDiagnosisRepo).save(isA(NCDCareDiagnosis.class));
        assertEquals(1L, actualSaveNCDDiagnosisDataResult);
    }

    @Test
    void testSaveNCDDiagnosisData5() throws Exception {
        // Arrange
        NCDCareDiagnosis ncdCareDiagnosis = mock(NCDCareDiagnosis.class);
        when(ncdCareDiagnosis.getID()).thenReturn(1L);
        when(nCDCareDiagnosisRepo.save(Mockito.<NCDCareDiagnosis>any())).thenReturn(ncdCareDiagnosis);
        NCDCareDiagnosis ncdDiagnosis = mock(NCDCareDiagnosis.class);
        when(ncdDiagnosis.getNcdScreeningConditionArray()).thenReturn(null);
        doNothing().when(ncdDiagnosis).setNcdScreeningCondition(Mockito.<String>any());

        // Act
        long actualSaveNCDDiagnosisDataResult = nCDCareDoctorServiceImpl.saveNCDDiagnosisData(ncdDiagnosis);

        // Assert
        verify(ncdCareDiagnosis).getID();
        verify(ncdDiagnosis).getNcdScreeningConditionArray();
        verify(ncdDiagnosis).setNcdScreeningCondition(isNull());
        verify(nCDCareDiagnosisRepo).save(isA(NCDCareDiagnosis.class));
        assertEquals(1L, actualSaveNCDDiagnosisDataResult);
    }

    @Test
    void testSaveNCDDiagnosisData6() throws Exception {
        // Arrange
        NCDCareDiagnosis ncdCareDiagnosis = mock(NCDCareDiagnosis.class);
        when(ncdCareDiagnosis.getID()).thenReturn(1L);
        when(nCDCareDiagnosisRepo.save(Mockito.<NCDCareDiagnosis>any())).thenReturn(ncdCareDiagnosis);
        NCDCareDiagnosis ncdDiagnosis = mock(NCDCareDiagnosis.class);
        when(ncdDiagnosis.getNcdScreeningConditionArray()).thenReturn(new String[]{});
        doNothing().when(ncdDiagnosis).setNcdScreeningCondition(Mockito.<String>any());

        // Act
        long actualSaveNCDDiagnosisDataResult = nCDCareDoctorServiceImpl.saveNCDDiagnosisData(ncdDiagnosis);

        // Assert
        verify(ncdCareDiagnosis).getID();
        verify(ncdDiagnosis, atLeast(1)).getNcdScreeningConditionArray();
        verify(ncdDiagnosis).setNcdScreeningCondition(isNull());
        verify(nCDCareDiagnosisRepo).save(isA(NCDCareDiagnosis.class));
        assertEquals(1L, actualSaveNCDDiagnosisDataResult);
    }

    @Test
    void testGetNCDCareDiagnosisDetails() {
        // Arrange
        when(nCDCareDiagnosisRepo.getNCDCareDiagnosisDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());
        when(prescriptionDetailRepo.findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(Mockito.<Long>any(),
                Mockito.<Long>any())).thenReturn(new ArrayList<>());
        when(prescriptionDetailRepo.getExternalinvestigationForVisitCode(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        // Act
        String actualNCDCareDiagnosisDetails = nCDCareDoctorServiceImpl.getNCDCareDiagnosisDetails(1L, 1L);

        // Assert
        verify(nCDCareDiagnosisRepo).getNCDCareDiagnosisDetails(isA(Long.class), isA(Long.class));
        verify(prescriptionDetailRepo).findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(isA(Long.class),
                isA(Long.class));
        verify(prescriptionDetailRepo).getExternalinvestigationForVisitCode(isA(Long.class), isA(Long.class));
        assertEquals("null", actualNCDCareDiagnosisDetails);
    }

    @Test
    void testUpdateBenNCDCareDiagnosis() throws IEMRException {
        // Arrange
        when(nCDCareDiagnosisRepo.updateNCDCareDiagnosis(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<String>any())).thenReturn(1);
        when(nCDCareDiagnosisRepo.getNCDCareDiagnosisStatus(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Ncd Care Diagnosis Status");
        NCDCareDiagnosis ncdCareDiagnosis = new NCDCareDiagnosis(1L, 1L, 1, 1L, "Ncd Care Condition", "Ncd Complication",
                "Ncd Care Type", 1L, "External Investigation", "Ncd Care Condition Other");

        // Act
        int actualUpdateBenNCDCareDiagnosisResult = nCDCareDoctorServiceImpl.updateBenNCDCareDiagnosis(ncdCareDiagnosis);

        // Assert
        verify(nCDCareDiagnosisRepo).getNCDCareDiagnosisStatus(isA(Long.class), isA(Long.class), isA(Long.class));
        verify(nCDCareDiagnosisRepo).updateNCDCareDiagnosis(isNull(), eq("Ncd Complication"), eq("Ncd Care Type"), isNull(),
                eq("U"), isA(Long.class), isA(Long.class), isA(Long.class), eq("Ncd Care Condition Other"));
        assertEquals("U", ncdCareDiagnosis.getProcessed());
        assertNull(ncdCareDiagnosis.getNcdCareCondition());
        assertEquals(1, actualUpdateBenNCDCareDiagnosisResult);
    }

    @Test
    void testUpdateBenNCDCareDiagnosis2() throws IEMRException {
        // Arrange
        when(nCDCareDiagnosisRepo.updateNCDCareDiagnosis(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<String>any())).thenReturn(1);
        when(nCDCareDiagnosisRepo.getNCDCareDiagnosisStatus(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("N");
        NCDCareDiagnosis ncdCareDiagnosis = new NCDCareDiagnosis(1L, 1L, 1, 1L, "Ncd Care Condition", "Ncd Complication",
                "Ncd Care Type", 1L, "External Investigation", "Ncd Care Condition Other");

        // Act
        int actualUpdateBenNCDCareDiagnosisResult = nCDCareDoctorServiceImpl.updateBenNCDCareDiagnosis(ncdCareDiagnosis);

        // Assert
        verify(nCDCareDiagnosisRepo).getNCDCareDiagnosisStatus(isA(Long.class), isA(Long.class), isA(Long.class));
        verify(nCDCareDiagnosisRepo).updateNCDCareDiagnosis(isNull(), eq("Ncd Complication"), eq("Ncd Care Type"), isNull(),
                eq("N"), isA(Long.class), isA(Long.class), isA(Long.class), eq("Ncd Care Condition Other"));
        assertEquals("N", ncdCareDiagnosis.getProcessed());
        assertNull(ncdCareDiagnosis.getNcdCareCondition());
        assertEquals(1, actualUpdateBenNCDCareDiagnosisResult);
    }

    @Test
    void testUpdateBenNCDCareDiagnosis3() throws IEMRException {
        // Arrange
        when(nCDCareDiagnosisRepo.updateNCDCareDiagnosis(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<String>any())).thenReturn(1);
        when(nCDCareDiagnosisRepo.getNCDCareDiagnosisStatus(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Ncd Care Diagnosis Status");
        NCDCareDiagnosis ncdCareDiagnosis = mock(NCDCareDiagnosis.class);
        when(ncdCareDiagnosis.getBeneficiaryRegID()).thenReturn(1L);
        when(ncdCareDiagnosis.getPrescriptionID()).thenReturn(1L);
        when(ncdCareDiagnosis.getVisitCode()).thenReturn(1L);
        when(ncdCareDiagnosis.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(ncdCareDiagnosis.getNcdCareCondition()).thenReturn("Ncd Care Condition");
        when(ncdCareDiagnosis.getNcdCareType()).thenReturn("Ncd Care Type");
        when(ncdCareDiagnosis.getNcdComplication()).thenReturn("Ncd Complication");
        when(ncdCareDiagnosis.getNcdScreeningConditionOther()).thenReturn("Ncd Screening Condition Other");
        when(ncdCareDiagnosis.getProcessed()).thenReturn("Processed");
        when(ncdCareDiagnosis.getNcdScreeningConditionArray()).thenReturn(new String[]{"Ncd Screening Condition Array"});
        doNothing().when(ncdCareDiagnosis).setNcdScreeningCondition(Mockito.<String>any());
        doNothing().when(ncdCareDiagnosis).setProcessed(Mockito.<String>any());

        // Act
        int actualUpdateBenNCDCareDiagnosisResult = nCDCareDoctorServiceImpl.updateBenNCDCareDiagnosis(ncdCareDiagnosis);

        // Assert
        verify(ncdCareDiagnosis, atLeast(1)).getBeneficiaryRegID();
        verify(ncdCareDiagnosis).getCreatedBy();
        verify(ncdCareDiagnosis).getNcdCareCondition();
        verify(ncdCareDiagnosis).getNcdCareType();
        verify(ncdCareDiagnosis).getNcdComplication();
        verify(ncdCareDiagnosis, atLeast(1)).getNcdScreeningConditionArray();
        verify(ncdCareDiagnosis).getNcdScreeningConditionOther();
        verify(ncdCareDiagnosis, atLeast(1)).getPrescriptionID();
        verify(ncdCareDiagnosis).getProcessed();
        verify(ncdCareDiagnosis, atLeast(1)).getVisitCode();
        verify(ncdCareDiagnosis).setNcdScreeningCondition(eq("Ncd Screening Condition Array"));
        verify(ncdCareDiagnosis).setProcessed(eq("U"));
        verify(nCDCareDiagnosisRepo).getNCDCareDiagnosisStatus(isA(Long.class), isA(Long.class), isA(Long.class));
        verify(nCDCareDiagnosisRepo).updateNCDCareDiagnosis(eq("Ncd Care Condition"), eq("Ncd Complication"),
                eq("Ncd Care Type"), eq("Jan 1, 2020 8:00am GMT+0100"), eq("Processed"), isA(Long.class), isA(Long.class),
                isA(Long.class), eq("Ncd Screening Condition Other"));
        assertEquals(1, actualUpdateBenNCDCareDiagnosisResult);
    }

    @Test
    void testGettersAndSetters() {
        NCDCareDoctorServiceImpl ncdCareDoctorServiceImpl = new NCDCareDoctorServiceImpl();

        // Act
        ncdCareDoctorServiceImpl.setNcdCareDiagnosisRepo(mock(NCDCareDiagnosisRepo.class));
        ncdCareDoctorServiceImpl.setPrescriptionDetailRepo(mock(PrescriptionDetailRepo.class));
    }
}
