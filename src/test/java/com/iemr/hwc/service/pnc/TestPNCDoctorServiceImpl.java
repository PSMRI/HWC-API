package com.iemr.hwc.service.pnc;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iemr.hwc.data.pnc.PNCDiagnosis;
import com.iemr.hwc.data.snomedct.SCTDescription;
import com.iemr.hwc.repo.nurse.pnc.PNCDiagnosisRepo;
import com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.hwc.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.mapper.InputMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

public class TestPNCDoctorServiceImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private PNCDiagnosisRepo pncDiagnosisRepo;

	@Mock
	private PrescriptionDetailRepo prescriptionDetailRepo;

	@Mock
	private CommonDoctorServiceImpl commonDoctorServiceImpl;

	@InjectMocks
	private PNCDoctorServiceImpl pncDoctorService;

	private Long beneficiaryRegID;
	private Long visitCode;
	private PNCDiagnosis pncDiagnosis;

	@InjectMocks
	private PNCDoctorServiceImpl pncDoctorServiceImpl;

	private JsonObject jsonObject;
	private Long prescriptionID;

	@BeforeEach
	public void setUp() {
		jsonObject = new JsonObject();
		jsonObject.addProperty("key", "value");
		prescriptionID = 1L;
	}

	@Test
	public void testSaveBenPNCDiagnosis_Success() throws IEMRException {
		PNCDiagnosis pncDiagnosis = new PNCDiagnosis();
		pncDiagnosis.setID(1L);
		when(pncDiagnosisRepo.save(any(PNCDiagnosis.class))).thenReturn(pncDiagnosis);

		Long result = pncDoctorServiceImpl.saveBenPNCDiagnosis(jsonObject, prescriptionID);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(pncDiagnosisRepo, times(1)).save(any(PNCDiagnosis.class));
	}

	@Test
	public void testSaveBenPNCDiagnosis_NullPNCDiagnosis() throws IEMRException {
		when(pncDiagnosisRepo.save(any(PNCDiagnosis.class))).thenReturn(null);

		Long result = pncDoctorServiceImpl.saveBenPNCDiagnosis(jsonObject, prescriptionID);

		assertNull(result);
		verify(pncDiagnosisRepo, times(1)).save(any(PNCDiagnosis.class));
	}

	@Test
	public void testSaveBenPNCDiagnosis_EmptyProvisionalAndConfirmatoryDiagnosisList() throws IEMRException {
		PNCDiagnosis pncDiagnosis = new PNCDiagnosis();
		pncDiagnosis.setProvisionalDiagnosisList(new ArrayList<>());
		pncDiagnosis.setConfirmatoryDiagnosisList(new ArrayList<>());
		pncDiagnosis.setID(1L);
		when(pncDiagnosisRepo.save(any(PNCDiagnosis.class))).thenReturn(pncDiagnosis);

		Long result = pncDoctorServiceImpl.saveBenPNCDiagnosis(jsonObject, prescriptionID);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(pncDiagnosisRepo, times(1)).save(any(PNCDiagnosis.class));
	}

	@Test
	public void testGetPNCDiagnosisDetails_WithPrescriptionData() {
		ArrayList<Object[]> prescriptionData = new ArrayList<>();
		prescriptionData.add(new Object[] { "Investigation", "Instruction", "Counselling" });

		when(prescriptionDetailRepo.getExternalinvestigationForVisitCode(beneficiaryRegID, visitCode))
				.thenReturn(prescriptionData);

		PNCDiagnosis pncDiagnosis = new PNCDiagnosis();
		pncDiagnosis.setProvisionalDiagnosis("Term1  ||  Term2");
		pncDiagnosis.setProvisionalDiagnosisSCTCode("Code1  ||  Code2");
		pncDiagnosis.setConfirmatoryDiagnosis("Term3  ||  Term4");
		pncDiagnosis.setConfirmatoryDiagnosisSCTCode("Code3  ||  Code4");

		when(pncDiagnosisRepo.findByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode))
				.thenReturn(new ArrayList<>(Arrays.asList(pncDiagnosis)));

		String result = pncDoctorService.getPNCDiagnosisDetails(beneficiaryRegID, visitCode);

		PNCDiagnosis resultDiagnosis = new Gson().fromJson(result, PNCDiagnosis.class);

		assertEquals("Investigation", resultDiagnosis.getExternalInvestigation());
		assertEquals("Instruction", resultDiagnosis.getSpecialistDiagnosis());
		assertEquals("Counselling", resultDiagnosis.getCounsellingProvided());
		assertNotNull(resultDiagnosis.getProvisionalDiagnosisList());
		assertNotNull(resultDiagnosis.getConfirmatoryDiagnosisList());
	}

	@Test
	public void testGetPNCDiagnosisDetails_WithoutPrescriptionData() {
		when(prescriptionDetailRepo.getExternalinvestigationForVisitCode(beneficiaryRegID, visitCode)).thenReturn(null);

		PNCDiagnosis pncDiagnosis = new PNCDiagnosis();
		pncDiagnosis.setProvisionalDiagnosis("Term1  ||  Term2");
		pncDiagnosis.setProvisionalDiagnosisSCTCode("Code1  ||  Code2");
		pncDiagnosis.setConfirmatoryDiagnosis("Term3  ||  Term4");
		pncDiagnosis.setConfirmatoryDiagnosisSCTCode("Code3  ||  Code4");

		when(pncDiagnosisRepo.findByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode))
				.thenReturn(new ArrayList<>(Arrays.asList(pncDiagnosis)));

		String result = pncDoctorService.getPNCDiagnosisDetails(beneficiaryRegID, visitCode);

		PNCDiagnosis resultDiagnosis = new Gson().fromJson(result, PNCDiagnosis.class);

		assertNull(resultDiagnosis.getExternalInvestigation());
		assertNull(resultDiagnosis.getSpecialistDiagnosis());
		assertNull(resultDiagnosis.getCounsellingProvided());
		assertNotNull(resultDiagnosis.getProvisionalDiagnosisList());
		assertNotNull(resultDiagnosis.getConfirmatoryDiagnosisList());
	}

	@Test
	public void testGetPNCDiagnosisDetails_WithEmptyPNCDiagnosisList() {
		when(prescriptionDetailRepo.getExternalinvestigationForVisitCode(beneficiaryRegID, visitCode)).thenReturn(null);

		when(pncDiagnosisRepo.findByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode))
				.thenReturn(new ArrayList<>());

		String result = pncDoctorService.getPNCDiagnosisDetails(beneficiaryRegID, visitCode);

		PNCDiagnosis resultDiagnosis = new Gson().fromJson(result, PNCDiagnosis.class);

		assertNull(resultDiagnosis.getExternalInvestigation());
		assertNull(resultDiagnosis.getSpecialistDiagnosis());
		assertNull(resultDiagnosis.getCounsellingProvided());
		assertNull(resultDiagnosis.getProvisionalDiagnosisList());
		assertNull(resultDiagnosis.getConfirmatoryDiagnosisList());
	}

	@Test
	public void testUpdateBenPNCDiagnosis_SuccessfulUpdate() throws IEMRException {
		when(pncDiagnosisRepo.getPNCDiagnosisStatus(anyLong(), anyLong(), anyLong())).thenReturn("Y");
		when(pncDiagnosisRepo.updatePNCDiagnosis(anyString(), anyString(), anyBoolean(), anyString(), any(),
				anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString(), anyString(), anyString(),
				anyString(), anyLong())).thenReturn(1);

		int result = pncDoctorServiceImpl.updateBenPNCDiagnosis(pncDiagnosis);

		assertEquals(1, result);
		verify(pncDiagnosisRepo, times(1)).updatePNCDiagnosis(anyString(), anyString(), anyBoolean(), anyString(),
				any(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString(), anyString(),
				anyString(), anyString(), anyLong());
	}

	@Test
	public void testUpdateBenPNCDiagnosis_SaveNewDiagnosis() throws IEMRException {
		when(pncDiagnosisRepo.getPNCDiagnosisStatus(anyLong(), anyLong(), anyLong())).thenReturn(null);
		when(pncDiagnosisRepo.save(any(PNCDiagnosis.class))).thenReturn(pncDiagnosis);

		int result = pncDoctorServiceImpl.updateBenPNCDiagnosis(pncDiagnosis);

		assertEquals(1, result);
		verify(pncDiagnosisRepo, times(1)).save(any(PNCDiagnosis.class));
	}

	@Test
	public void testUpdateBenPNCDiagnosis_NullDiagnosis() throws IEMRException {
		int result = pncDoctorServiceImpl.updateBenPNCDiagnosis(null);

		assertEquals(0, result);
		verify(pncDiagnosisRepo, times(0)).updatePNCDiagnosis(anyString(), anyString(), anyBoolean(), anyString(),
				any(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString(), anyString(),
				anyString(), anyString(), anyLong());
		verify(pncDiagnosisRepo, times(0)).save(any(PNCDiagnosis.class));
	}

	@Test
	public void testUpdateBenPNCDiagnosis_InvalidDiagnosis() throws IEMRException {
		pncDiagnosis.setBeneficiaryRegID(null);

		int result = pncDoctorServiceImpl.updateBenPNCDiagnosis(pncDiagnosis);

		assertEquals(0, result);
		verify(pncDiagnosisRepo, times(0)).updatePNCDiagnosis(anyString(), anyString(), anyBoolean(), anyString(),
				any(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString(), anyString(),
				anyString(), anyString(), anyLong());
		verify(pncDiagnosisRepo, times(0)).save(any(PNCDiagnosis.class));
	}
}
