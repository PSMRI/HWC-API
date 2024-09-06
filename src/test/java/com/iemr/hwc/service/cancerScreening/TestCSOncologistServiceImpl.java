package com.iemr.hwc.service.cancerScreening;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.iemr.hwc.data.doctor.CancerDiagnosis;
import com.iemr.hwc.repo.doctor.CancerDiagnosisRepo;
public class TestCSOncologistServiceImpl {
	
	    @Mock
	    private CancerDiagnosisRepo cancerDiagnosisRepo;

	    @InjectMocks
	    private CSOncologistServiceImpl cSOncologistServiceImpl;

	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    public void testUpdateCancerDiagnosisDetailsByOncologist_ProcessedNotNullAndNotN() {
	        CancerDiagnosis cancerDiagnosis = new CancerDiagnosis();
	        cancerDiagnosis.setBeneficiaryRegID("123");
	        cancerDiagnosis.setVisitCode("456");
	        cancerDiagnosis.setProvisionalDiagnosisOncologist("Diagnosis");
	        cancerDiagnosis.setModifiedBy("Doctor");

	        when(cancerDiagnosisRepo.getCancerDiagnosisStatuses(anyString(), anyString())).thenReturn("Y");
	        when(cancerDiagnosisRepo.updateDetailsByOncologist(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(1);

	        int response = cSOncologistServiceImpl.updateCancerDiagnosisDetailsByOncologist(cancerDiagnosis);

	        assertEquals(1, response);
	        verify(cancerDiagnosisRepo, times(1)).updateDetailsByOncologist(anyString(), anyString(), anyString(), anyString(), eq("U"));
	    }

	    @Test
	    public void testUpdateCancerDiagnosisDetailsByOncologist_ProcessedNull() {
	        CancerDiagnosis cancerDiagnosis = new CancerDiagnosis();
	        cancerDiagnosis.setBeneficiaryRegID("123");
	        cancerDiagnosis.setVisitCode("456");
	        cancerDiagnosis.setProvisionalDiagnosisOncologist("Diagnosis");
	        cancerDiagnosis.setModifiedBy("Doctor");

	        when(cancerDiagnosisRepo.getCancerDiagnosisStatuses(anyString(), anyString())).thenReturn(null);
	        when(cancerDiagnosisRepo.save(any(CancerDiagnosis.class))).thenReturn(cancerDiagnosis);

	        int response = cSOncologistServiceImpl.updateCancerDiagnosisDetailsByOncologist(cancerDiagnosis);

	        assertEquals(1, response);
	        verify(cancerDiagnosisRepo, times(1)).save(any(CancerDiagnosis.class));
	    }

	    @Test
	    public void testUpdateCancerDiagnosisDetailsByOncologist_ProcessedN() {
	        CancerDiagnosis cancerDiagnosis = new CancerDiagnosis();
	        cancerDiagnosis.setBeneficiaryRegID("123");
	        cancerDiagnosis.setVisitCode("456");
	        cancerDiagnosis.setProvisionalDiagnosisOncologist("Diagnosis");
	        cancerDiagnosis.setModifiedBy("Doctor");

	        when(cancerDiagnosisRepo.getCancerDiagnosisStatuses(anyString(), anyString())).thenReturn("N");
	        when(cancerDiagnosisRepo.save(any(CancerDiagnosis.class))).thenReturn(cancerDiagnosis);

	        int response = cSOncologistServiceImpl.updateCancerDiagnosisDetailsByOncologist(cancerDiagnosis);

	        assertEquals(1, response);
	        verify(cancerDiagnosisRepo, times(1)).save(any(CancerDiagnosis.class));
	    }
	}

