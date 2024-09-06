package com.iemr.hwc.service.anc;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.iemr.hwc.data.anc.ANCDiagnosis;
import com.iemr.hwc.repo.nurse.anc.ANCDiagnosisRepo;
import com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.hwc.utils.exception.IEMRException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
public class TestANCDoctorServiceImpl {
	

	public class ANCDoctorServiceImplTest {

	    
	    @Mock
	    private PrescriptionDetailRepo prescriptionDetailRepo;

	    @Mock
	    private ANCDiagnosisRepo ancDiagnosisRepo;
	    

	   
	    @InjectMocks
	    private ANCDoctorServiceImpl ancDoctorService;

	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    public void testSaveBenANCDiagnosis_Positive() throws IEMRException {
	        // Arrange
	        JsonObject obj = new JsonObject();
	        obj.addProperty("someProperty", "someValue");
	        Long prescriptionID = 1L;

	        ANCDiagnosis ancDiagnosis = new ANCDiagnosis();
	        ancDiagnosis.setID(1L);

	        when(ancDiagnosisRepo.save(any(ANCDiagnosis.class))).thenReturn(ancDiagnosis);

	        // Act
	        Long result = ancDoctorService.saveBenANCDiagnosis(obj, prescriptionID);

	        // Assert
	        assertNotNull(result);
	        assertEquals(1L, result);
	    }

	    @Test
	    public void testSaveBenANCDiagnosis_Negative() throws IEMRException {
	        // Arrange
	        JsonObject obj = new JsonObject();
	        obj.addProperty("someProperty", "someValue");
	        Long prescriptionID = 1L;

	        when(ancDiagnosisRepo.save(any(ANCDiagnosis.class))).thenReturn(null);

	        // Act
	        Long result = ancDoctorService.saveBenANCDiagnosis(obj, prescriptionID);

	        // Assert
	        assertNull(result);
	    }
	}
	 @Test
	    public void testGetANCDiagnosisDetails_Positive() {
	        Long beneficiaryRegID = 1L;
	        Long visitCode = 1L;

	        // Mocking prescriptionDetailRepo
	        ArrayList<Object[]> prescriptionData = new ArrayList<>();
	        prescriptionData.add(new Object[]{"Investigation", "Instruction", "Counselling"});
	        when(prescriptionDetailRepo.getExternalinvestigationForVisitCode(beneficiaryRegID, visitCode)).thenReturn(prescriptionData);

	        // Mocking ancDiagnosisRepo
	        ANCDiagnosis ancDiagnosis = new ANCDiagnosis();
	        ancDiagnosis.setComplicationOfCurrentPregnancy("Complication1 , Complication2");
	        ancDiagnosis.setOtherCurrPregComplication("OtherComplication");
	        ArrayList<ANCDiagnosis> ancDiagnosisList = new ArrayList<>();
	        ancDiagnosisList.add(ancDiagnosis);
	        when(ancDiagnosisRepo.findByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode)).thenReturn(ancDiagnosisList);

	        // Call the method
	        String result = ancDoctorService.getANCDiagnosisDetails(beneficiaryRegID, visitCode);

	        // Assert the result
	        assertNotNull(result);
	        assertTrue(result.contains("Investigation"));
	        assertTrue(result.contains("Instruction"));
	        assertTrue(result.contains("Counselling"));
	        assertTrue(result.contains("Complication1"));
	        assertTrue(result.contains("OtherComplication"));
	    }

	    @Test
	    public void testGetANCDiagnosisDetails_Negative() {
	        Long beneficiaryRegID = 1L;
	        Long visitCode = 1L;

	        // Mocking prescriptionDetailRepo to return null
	        when(prescriptionDetailRepo.getExternalinvestigationForVisitCode(beneficiaryRegID, visitCode)).thenReturn(null);

	        // Mocking ancDiagnosisRepo to return empty list
	        when(ancDiagnosisRepo.findByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode)).thenReturn(new ArrayList<>());

	        // Call the method
	        String result = ancDoctorService.getANCDiagnosisDetails(beneficiaryRegID, visitCode);

	        // Assert the result
	        assertNotNull(result);
	        ANCDiagnosis ancDiagnosisDetails = new Gson().fromJson(result, ANCDiagnosis.class);
	        assertNull(ancDiagnosisDetails.getExternalInvestigation());
	        assertNull(ancDiagnosisDetails.getSpecialistDiagnosis());
	        assertNull(ancDiagnosisDetails.getCounsellingProvided());
	        assertNull(ancDiagnosisDetails.getComplicationOfCurrentPregnancyList());
	    }
	    @Test
	    public void testUpdateBenANCDiagnosis_UpdateExistingDiagnosis() throws IEMRException {
	        when(ancDiagnosisRepo.getANCDiagnosisStatus(anyLong(), anyLong(), anyLong())).thenReturn("N");
	        when(ancDiagnosisRepo.updateANCDiagnosis(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString())).thenReturn(1);

	        int result = ancDoctorService.updateBenANCDiagnosis(ancDiagnosis);

	        assertEquals(1, result);
	        verify(ancDiagnosisRepo, times(1)).updateANCDiagnosis(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString());
	    }

	    @Test
	    public void testUpdateBenANCDiagnosis_SaveNewDiagnosis() throws IEMRException {
	        when(ancDiagnosisRepo.getANCDiagnosisStatus(anyLong(), anyLong(), anyLong())).thenReturn(null);
	        when(ancDiagnosisRepo.save(any(ANCDiagnosis.class))).thenReturn(ancDiagnosis);

	        int result = ancDoctorService.updateBenANCDiagnosis(ancDiagnosis);

	        assertEquals(1, result);
	        verify(ancDiagnosisRepo, times(1)).save(any(ANCDiagnosis.class));
	    }

	    @Test
	    public void testUpdateBenANCDiagnosis_HandleComplications() throws IEMRException {
	        when(ancDiagnosisRepo.getANCDiagnosisStatus(anyLong(), anyLong(), anyLong())).thenReturn("N");
	        when(ancDiagnosisRepo.updateANCDiagnosis(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString())).thenReturn(1);

	        ancDiagnosis.setComplicationOfCurrentPregnancyList(new ArrayList<>());
	        int result = ancDoctorService.updateBenANCDiagnosis(ancDiagnosis);

	        assertEquals(1, result);
	        verify(ancDiagnosisRepo, times(1)).updateANCDiagnosis(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString());
	    }

	    @Test
	    public void testUpdateBenANCDiagnosis_ReturnCorrectResultOnUpdate() throws IEMRException {
	        when(ancDiagnosisRepo.getANCDiagnosisStatus(anyLong(), anyLong(), anyLong())).thenReturn("N");
	        when(ancDiagnosisRepo.updateANCDiagnosis(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString())).thenReturn(1);

	        int result = ancDoctorService.updateBenANCDiagnosis(ancDiagnosis);

	        assertEquals(1, result);
	    }

	    @Test
	    public void testUpdateBenANCDiagnosis_ReturnCorrectResultOnSave() throws IEMRException {
	        when(ancDiagnosisRepo.getANCDiagnosisStatus(anyLong(), anyLong(), anyLong())).thenReturn(null);
	        when(ancDiagnosisRepo.save(any(ANCDiagnosis.class))).thenReturn(ancDiagnosis);

	        int result = ancDoctorService.updateBenANCDiagnosis(ancDiagnosis);

	        assertEquals(1, result);
	    }
}
