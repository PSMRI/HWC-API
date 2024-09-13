package com.iemr.hwc.service.generalOPD;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.google.gson.Gson;
import com.iemr.hwc.data.quickConsultation.PrescriptionDetail;
import com.iemr.hwc.data.snomedct.SCTDescription;
import com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo;

public class TestGeneralOPDDoctorServiceImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private PrescriptionDetailRepo prescriptionDetailRepo;

	@InjectMocks
	private GeneralOPDDoctorServiceImpl generalOPDDoctorServiceImpl;
	private GeneralOPDDoctorServiceImpl generalOPDDoctorService;

	private Long beneficiaryRegID;
	private Long visitCode;

	@BeforeEach
	public void setUp() {
		generalOPDDoctorServiceImpl.setPrescriptionDetailRepo(prescriptionDetailRepo);
	}

	@Test
	public void testSetPrescriptionDetailRepo() {
		// Verify that the prescriptionDetailRepo is set correctly
		assertNotNull(generalOPDDoctorServiceImpl);
		assertSame(prescriptionDetailRepo, generalOPDDoctorServiceImpl.prescriptionDetailRepo);
	}

	@Test
	public void testGetGeneralOPDDiagnosisDetails_ValidData() {
		PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
		prescriptionDetail.setDiagnosisProvided_SCTCode("12345  ||  67890");
		prescriptionDetail.setDiagnosisProvided("Diagnosis1  ||  Diagnosis2");

		when(prescriptionDetailRepo.findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(beneficiaryRegID,
				visitCode)).thenReturn(new ArrayList<>(Collections.singletonList(prescriptionDetail)));

		String response = generalOPDDoctorService.getGeneralOPDDiagnosisDetails(beneficiaryRegID, visitCode);

		PrescriptionDetail expectedDetail = new PrescriptionDetail();
		expectedDetail.setDiagnosisProvided_SCTCode("12345  ||  67890");
		expectedDetail.setDiagnosisProvided("Diagnosis1  ||  Diagnosis2");

		ArrayList<SCTDescription> sctDescriptions = new ArrayList<>();
		SCTDescription sct1 = new SCTDescription();
		sct1.setConceptID("12345");
		sct1.setTerm("Diagnosis1");
		sctDescriptions.add(sct1);

		SCTDescription sct2 = new SCTDescription();
		sct2.setConceptID("67890");
		sct2.setTerm("Diagnosis2");
		sctDescriptions.add(sct2);

		expectedDetail.setProvisionalDiagnosisList(sctDescriptions);

		assertEquals(new Gson().toJson(expectedDetail), response);
	}

	@Test
	public void testGetGeneralOPDDiagnosisDetails_EmptyData() {
		when(prescriptionDetailRepo.findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(beneficiaryRegID,
				visitCode)).thenReturn(new ArrayList<>());

		String response = generalOPDDoctorService.getGeneralOPDDiagnosisDetails(beneficiaryRegID, visitCode);

		PrescriptionDetail expectedDetail = new PrescriptionDetail();
		assertEquals(new Gson().toJson(expectedDetail), response);
	}

	@Test
	public void testGetGeneralOPDDiagnosisDetails_NullDiagnosisData() {
		PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
		prescriptionDetail.setDiagnosisProvided_SCTCode(null);
		prescriptionDetail.setDiagnosisProvided(null);

		when(prescriptionDetailRepo.findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(beneficiaryRegID,
				visitCode)).thenReturn(new ArrayList<>(Collections.singletonList(prescriptionDetail)));

		String response = generalOPDDoctorService.getGeneralOPDDiagnosisDetails(beneficiaryRegID, visitCode);

		PrescriptionDetail expectedDetail = new PrescriptionDetail();
		expectedDetail.setDiagnosisProvided_SCTCode(null);
		expectedDetail.setDiagnosisProvided(null);

		assertEquals(new Gson().toJson(expectedDetail), response);
	}
}
