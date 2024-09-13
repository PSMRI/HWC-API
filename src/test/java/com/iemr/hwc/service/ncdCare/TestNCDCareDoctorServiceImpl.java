package com.iemr.hwc.service.ncdCare;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.iemr.hwc.data.ncdcare.NCDCareDiagnosis;
import com.iemr.hwc.repo.nurse.ncdcare.NCDCareDiagnosisRepo;
import com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

public class TestNCDCareDoctorServiceImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private NCDCareDiagnosisRepo ncdCareDiagnosisRepo;

	@Mock
	private PrescriptionDetailRepo prescriptionDetailRepo;

	@InjectMocks
	private NCDCareDoctorServiceImpl ncdCareDoctorServiceImpl;

	private Long beneficiaryRegID;
	private Long visitCode;
	private ArrayList<Object[]> prescriptionData;
	private ArrayList<Object[]> resList;

	@InjectMocks
	private NCDCareDoctorServiceImpl ncdCareDoctorService;

	private NCDCareDiagnosis ncdCareDiagnosis;

	@BeforeEach
	public void setUp() {
		ncdCareDiagnosis = new NCDCareDiagnosis();
		ncdCareDiagnosis.setNcdScreeningConditionArray(new String[] { "Condition1", "Condition2" });
	}

	@Test
	public void testSaveNCDDiagnosisData_Success() throws Exception {
		NCDCareDiagnosis savedDiagnosis = new NCDCareDiagnosis();
		savedDiagnosis.setID(1L);
		when(ncdCareDiagnosisRepo.save(any(NCDCareDiagnosis.class))).thenReturn(savedDiagnosis);

		long result = ncdCareDoctorService.saveNCDDiagnosisData(ncdCareDiagnosis);

		assertEquals(1L, result);
		verify(ncdCareDiagnosisRepo, times(1)).save(any(NCDCareDiagnosis.class));
	}

	@Test
	public void testSaveNCDDiagnosisData_NullScreeningConditionArray() throws Exception {
		ncdCareDiagnosis.setNcdScreeningConditionArray(null);
		NCDCareDiagnosis savedDiagnosis = new NCDCareDiagnosis();
		savedDiagnosis.setID(1L);
		when(ncdCareDiagnosisRepo.save(any(NCDCareDiagnosis.class))).thenReturn(savedDiagnosis);

		long result = ncdCareDoctorService.saveNCDDiagnosisData(ncdCareDiagnosis);

		assertEquals(1L, result);
		assertNull(ncdCareDiagnosis.getNcdScreeningCondition());
		verify(ncdCareDiagnosisRepo, times(1)).save(any(NCDCareDiagnosis.class));
	}

	@Test
	public void testSaveNCDDiagnosisData_EmptyScreeningConditionArray() throws Exception {
		ncdCareDiagnosis.setNcdScreeningConditionArray(new String[] {});
		NCDCareDiagnosis savedDiagnosis = new NCDCareDiagnosis();
		savedDiagnosis.setID(1L);
		when(ncdCareDiagnosisRepo.save(any(NCDCareDiagnosis.class))).thenReturn(savedDiagnosis);

		long result = ncdCareDoctorService.saveNCDDiagnosisData(ncdCareDiagnosis);

		assertEquals(1L, result);
		assertNull(ncdCareDiagnosis.getNcdScreeningCondition());
		verify(ncdCareDiagnosisRepo, times(1)).save(any(NCDCareDiagnosis.class));
	}

	@Test
	public void testSaveNCDDiagnosisData_Exception() {
		when(ncdCareDiagnosisRepo.save(any(NCDCareDiagnosis.class))).thenThrow(new RuntimeException("Database error"));

		Exception exception = assertThrows(Exception.class, () -> {
			ncdCareDoctorService.saveNCDDiagnosisData(ncdCareDiagnosis);
		});

		assertEquals("Database error", exception.getMessage());
		verify(ncdCareDiagnosisRepo, times(1)).save(any(NCDCareDiagnosis.class));
	}

	@Test
	public void testGetNCDCareDiagnosisDetails_withPrescriptionData() {
		when(prescriptionDetailRepo.getExternalinvestigationForVisitCode(beneficiaryRegID, visitCode))
				.thenReturn(prescriptionData);
		when(ncdCareDiagnosisRepo.getNCDCareDiagnosisDetails(beneficiaryRegID, visitCode)).thenReturn(resList);

		String result = ncdCareDoctorServiceImpl.getNCDCareDiagnosisDetails(beneficiaryRegID, visitCode);

		NCDCareDiagnosis expectedDiagnosis = new NCDCareDiagnosis();
		expectedDiagnosis.setExternalInvestigation("Investigation");
		expectedDiagnosis.setSpecialistDiagnosis("Instruction");
		expectedDiagnosis.setCounsellingProvided("Counselling");
		expectedDiagnosis.setNcdScreeningConditionArray(new String[] { "Condition" });
		expectedDiagnosis.setDiagnosisProvided(null);
		expectedDiagnosis.setDiagnosisProvided_SCTCode(null);
		expectedDiagnosis.setProvisionalDiagnosisList(new ArrayList<>());

		assertEquals(new Gson().toJson(expectedDiagnosis), result);
		verify(prescriptionDetailRepo, times(1)).getExternalinvestigationForVisitCode(beneficiaryRegID, visitCode);
		verify(ncdCareDiagnosisRepo, times(1)).getNCDCareDiagnosisDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetNCDCareDiagnosisDetails_withoutPrescriptionData() {
		when(prescriptionDetailRepo.getExternalinvestigationForVisitCode(beneficiaryRegID, visitCode)).thenReturn(null);
		when(ncdCareDiagnosisRepo.getNCDCareDiagnosisDetails(beneficiaryRegID, visitCode)).thenReturn(resList);

		String result = ncdCareDoctorServiceImpl.getNCDCareDiagnosisDetails(beneficiaryRegID, visitCode);

		NCDCareDiagnosis expectedDiagnosis = new NCDCareDiagnosis();
		expectedDiagnosis.setExternalInvestigation(null);
		expectedDiagnosis.setSpecialistDiagnosis(null);
		expectedDiagnosis.setCounsellingProvided(null);
		expectedDiagnosis.setNcdScreeningConditionArray(new String[] { "Condition" });
		expectedDiagnosis.setDiagnosisProvided(null);
		expectedDiagnosis.setDiagnosisProvided_SCTCode(null);
		expectedDiagnosis.setProvisionalDiagnosisList(new ArrayList<>());

		assertEquals(new Gson().toJson(expectedDiagnosis), result);
		verify(prescriptionDetailRepo, times(1)).getExternalinvestigationForVisitCode(beneficiaryRegID, visitCode);
		verify(ncdCareDiagnosisRepo, times(1)).getNCDCareDiagnosisDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetNCDCareDiagnosisDetails_withEmptyResList() {
		when(prescriptionDetailRepo.getExternalinvestigationForVisitCode(beneficiaryRegID, visitCode))
				.thenReturn(prescriptionData);
		when(ncdCareDiagnosisRepo.getNCDCareDiagnosisDetails(beneficiaryRegID, visitCode))
				.thenReturn(new ArrayList<>());

		String result = ncdCareDoctorServiceImpl.getNCDCareDiagnosisDetails(beneficiaryRegID, visitCode);

		assertNull(result);
		verify(prescriptionDetailRepo, times(1)).getExternalinvestigationForVisitCode(beneficiaryRegID, visitCode);
		verify(ncdCareDiagnosisRepo, times(1)).getNCDCareDiagnosisDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testUpdateBenNCDCareDiagnosis_WhenProcessedIsNotNullAndNotN() throws IEMRException {
		when(ncdCareDiagnosisRepo.getNCDCareDiagnosisStatus(anyLong(), anyLong(), anyLong())).thenReturn("Y");
		when(ncdCareDiagnosisRepo.updateNCDCareDiagnosis(any(), any(), any(), any(), any(), anyLong(), anyLong(),
				anyLong(), any())).thenReturn(1);

		int result = ncdCareDoctorService.updateBenNCDCareDiagnosis(ncdCareDiagnosis);

		assertEquals(1, result);
		verify(ncdCareDiagnosisRepo, times(1)).updateNCDCareDiagnosis(any(), any(), any(), any(), any(), anyLong(),
				anyLong(), anyLong(), any());
	}

	@Test
	public void testUpdateBenNCDCareDiagnosis_WhenProcessedIsNull() throws IEMRException {
		when(ncdCareDiagnosisRepo.getNCDCareDiagnosisStatus(anyLong(), anyLong(), anyLong())).thenReturn(null);
		when(ncdCareDiagnosisRepo.save(any(NCDCareDiagnosis.class))).thenReturn(ncdCareDiagnosis);

		int result = ncdCareDoctorService.updateBenNCDCareDiagnosis(ncdCareDiagnosis);

		assertEquals(1, result);
		verify(ncdCareDiagnosisRepo, times(1)).save(any(NCDCareDiagnosis.class));
	}

	@Test
	public void testUpdateBenNCDCareDiagnosis_WhenNcdScreeningConditionArrayIsNotNull() throws IEMRException {
		ncdCareDiagnosis.setNcdScreeningConditionArray(new String[] { "condition1", "condition2" });
		when(ncdCareDiagnosisRepo.getNCDCareDiagnosisStatus(anyLong(), anyLong(), anyLong())).thenReturn("Y");
		when(ncdCareDiagnosisRepo.updateNCDCareDiagnosis(any(), any(), any(), any(), any(), anyLong(), anyLong(),
				anyLong(), any())).thenReturn(1);

		int result = ncdCareDoctorService.updateBenNCDCareDiagnosis(ncdCareDiagnosis);

		assertEquals(1, result);
		assertNotNull(ncdCareDiagnosis.getNcdScreeningCondition());
		verify(ncdCareDiagnosisRepo, times(1)).updateNCDCareDiagnosis(any(), any(), any(), any(), any(), anyLong(),
				anyLong(), anyLong(), any());
	}

	@Test
	public void testUpdateBenNCDCareDiagnosis_WhenNcdScreeningConditionArrayIsNull() throws IEMRException {
		ncdCareDiagnosis.setNcdScreeningConditionArray(null);
		when(ncdCareDiagnosisRepo.getNCDCareDiagnosisStatus(anyLong(), anyLong(), anyLong())).thenReturn("Y");
		when(ncdCareDiagnosisRepo.updateNCDCareDiagnosis(any(), any(), any(), any(), any(), anyLong(), anyLong(),
				anyLong(), any())).thenReturn(1);

		int result = ncdCareDoctorService.updateBenNCDCareDiagnosis(ncdCareDiagnosis);

		assertEquals(1, result);
		assertNull(ncdCareDiagnosis.getNcdScreeningCondition());
		verify(ncdCareDiagnosisRepo, times(1)).updateNCDCareDiagnosis(any(), any(), any(), any(), any(), anyLong(),
				anyLong(), anyLong(), any());
	}
}
