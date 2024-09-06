package com.iemr.hwc.service.cancerScreening;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iemr.hwc.data.doctor.CancerDiagnosis;
import com.iemr.hwc.repo.doctor.CancerDiagnosisRepo;
public class TestCSDoctorServiceImpl {
	

	@ExtendWith(MockitoExtension.class)
	
	    @Mock
	    private CancerDiagnosisRepo cancerDiagnosisRepo;
	

	    @InjectMocks
	    private CSDoctorServiceImpl csDoctorServiceImpl;

	    private CancerDiagnosis cancerDiagnosis;
	    private Long benRegID;
	    private Long visitCode;


	    @BeforeEach
	    void setUp() {
	        cancerDiagnosis = new CancerDiagnosis();
	        cancerDiagnosis.setID(1L);
	    }

	    @Test
	    void testSaveCancerDiagnosisData_Positive() {
	        // Arrange
	        when(cancerDiagnosisRepo.save(cancerDiagnosis)).thenReturn(cancerDiagnosis);

	        // Act
	        Long result = csDoctorServiceImpl.saveCancerDiagnosisData(cancerDiagnosis);

	        // Assert
	        assertEquals(1L, result);
	    }

	    @Test
	    void testSaveCancerDiagnosisData_Negative() {
	        // Arrange
	        when(cancerDiagnosisRepo.save(cancerDiagnosis)).thenReturn(null);

	        // Act
	        Long result = csDoctorServiceImpl.saveCancerDiagnosisData(cancerDiagnosis);

	        // Assert
	        assertNull(result);
	    }
	    @Test
	    public void testGetCancerDiagnosisObj_withAdditionalServices() {
	        List<String> additionalServices = Arrays.asList("Service1", "Service2", "Service3");
	        cancerDiagnosis.setRefrredToAdditionalServiceList(additionalServices);

	        CancerDiagnosis result = csDoctorServiceImpl.getCancerDiagnosisObj(cancerDiagnosis);

	        assertNotNull(result);
	        assertEquals("Service1,Service2,Service3", result.getRefrredToAdditionalService());
	    }

	    @Test
	    public void testGetCancerDiagnosisObj_withEmptyAdditionalServices() {
	        List<String> additionalServices = Arrays.asList();
	        cancerDiagnosis.setRefrredToAdditionalServiceList(additionalServices);

	        CancerDiagnosis result = csDoctorServiceImpl.getCancerDiagnosisObj(cancerDiagnosis);

	        assertNotNull(result);
	        assertEquals("", result.getRefrredToAdditionalService());
	    }

	    @Test
	    public void testGetCancerDiagnosisObj_withNullAdditionalServices() {
	        cancerDiagnosis.setRefrredToAdditionalServiceList(null);

	        CancerDiagnosis result = csDoctorServiceImpl.getCancerDiagnosisObj(cancerDiagnosis);

	        assertNotNull(result);
	        assertNull(result.getRefrredToAdditionalService());
	    }
	    @Test
	    public void testGetBenDoctorEnteredDataForCaseSheet1() {
	        when(cancerDiagnosisRepo.getBenCancerDiagnosisDetails(benRegID, visitCode)).thenReturn(cancerDiagnosis);

	        Map<String, Object> result = csDoctorServiceImpl.getBenDoctorEnteredDataForCaseSheet(benRegID, visitCode);

	        assertNotNull(result);
	        assertTrue(result.containsKey("diagnosis"));
	        assertEquals(cancerDiagnosis, result.get("diagnosis"));

	        verify(cancerDiagnosisRepo, times(1)).getBenCancerDiagnosisDetails(benRegID, visitCode);
	    }

	    @Test
	    public void testGetBenDoctorEnteredDataForCaseSheet_NoDiagnosis1() {
	        when(cancerDiagnosisRepo.getBenCancerDiagnosisDetails(benRegID, visitCode)).thenReturn(null);

	        Map<String, Object> result = csDoctorServiceImpl.getBenDoctorEnteredDataForCaseSheet(benRegID, visitCode);

	        assertNotNull(result);
	        assertTrue(result.containsKey("diagnosis"));
	        assertNull(result.get("diagnosis"));

	        verify(cancerDiagnosisRepo, times(1)).getBenCancerDiagnosisDetails(benRegID, visitCode);
	    }
	    @Test
	    public void testGetBenDoctorEnteredDataForCaseSheet() {
	        when(cancerDiagnosisRepo.getBenCancerDiagnosisDetails(benRegID, visitCode)).thenReturn(cancerDiagnosis);

	        Map<String, Object> result = csDoctorServiceImpl.getBenDoctorEnteredDataForCaseSheet(benRegID, visitCode);

	        assertNotNull(result);
	        assertTrue(result.containsKey("diagnosis"));
	        assertEquals(cancerDiagnosis, result.get("diagnosis"));

	        verify(cancerDiagnosisRepo, times(1)).getBenCancerDiagnosisDetails(benRegID, visitCode);
	    }

	    @Test
	    public void testGetBenDoctorEnteredDataForCaseSheet_NoDiagnosis() {
	        when(cancerDiagnosisRepo.getBenCancerDiagnosisDetails(benRegID, visitCode)).thenReturn(null);

	        Map<String, Object> result = csDoctorServiceImpl.getBenDoctorEnteredDataForCaseSheet(benRegID, visitCode);

	        assertNotNull(result);
	        assertTrue(result.containsKey("diagnosis"));
	        assertNull(result.get("diagnosis"));

	        verify(cancerDiagnosisRepo, times(1)).getBenCancerDiagnosisDetails(benRegID, visitCode);
	    }
	    @Test
	    public void testUpdateCancerDiagnosisDetailsByDoctor_StatusNotN() {
	        when(cancerDiagnosisRepo.getCancerDiagnosisStatuses(anyLong(), anyLong())).thenReturn("Y");
	        when(cancerDiagnosisRepo.updateCancerDiagnosisDetailsByDoctor(any(), any(), any(), any(), any(), any(), anyLong(), anyLong(), any(), any())).thenReturn(1);

	        int response = csDoctorServiceImpl.updateCancerDiagnosisDetailsByDoctor(cancerDiagnosis);

	        assertEquals(1, response);
	        verify(cancerDiagnosisRepo, times(1)).updateCancerDiagnosisDetailsByDoctor(any(), any(), any(), any(), any(),"U", any(), any(), any(), any());
	    }

	    @Test
	    public void testUpdateCancerDiagnosisDetailsByDoctor_StatusN() {
	        when(cancerDiagnosisRepo.getCancerDiagnosisStatuses(anyLong(), anyLong())).thenReturn("N");
	        when(cancerDiagnosisRepo.updateCancerDiagnosisDetailsByDoctor(any(), any(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(1);

	        int response = csDoctorServiceImpl.updateCancerDiagnosisDetailsByDoctor(cancerDiagnosis);

	        assertEquals(1, response);
	        verify(cancerDiagnosisRepo, times(1)).updateCancerDiagnosisDetailsByDoctor(any(), any(), any(), any(), any(), "N", any(), any(), any(), any());
	    }

	    @Test
	    public void testUpdateCancerDiagnosisDetailsByDoctor_StatusNull_SaveSuccess() {
	        when(cancerDiagnosisRepo.getCancerDiagnosisStatuses(anyLong(), anyLong())).thenReturn(null);
	        when(cancerDiagnosisRepo.save(any(CancerDiagnosis.class))).thenReturn(cancerDiagnosis);

	        int response = csDoctorServiceImpl.updateCancerDiagnosisDetailsByDoctor(cancerDiagnosis);

	        assertEquals(1, response);
	        verify(cancerDiagnosisRepo, times(1)).save(any(CancerDiagnosis.class));
	    }

	    @Test
	    public void testUpdateCancerDiagnosisDetailsByDoctor_StatusNull_SaveFail() {
	        when(cancerDiagnosisRepo.getCancerDiagnosisStatuses(anyLong(), anyLong())).thenReturn(null);
	        when(cancerDiagnosisRepo.save(any(CancerDiagnosis.class))).thenReturn(null);

	        int response = csDoctorServiceImpl.updateCancerDiagnosisDetailsByDoctor(cancerDiagnosis);

	        assertEquals(0, response);
	        verify(cancerDiagnosisRepo, times(1)).save(any(CancerDiagnosis.class));
	    }
	}
	
	


