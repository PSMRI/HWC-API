package com.iemr.hwc.service.labtechnician;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iemr.hwc.data.lab.LabResultEntry;
import com.iemr.hwc.data.lab.V_benLabTestOrderedDetails;
import com.iemr.hwc.data.labModule.WrapperLabResultEntry;
import com.iemr.hwc.repo.labModule.LabResultEntryRepo;
import com.iemr.hwc.repository.lab.V_benLabTestOrderedDetailsRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestLabTechnicianServiceImpl {

	@ExtendWith(MockitoExtension.class)
	public class LabTechnicianServiceImplTest {

		@Mock
		private V_benLabTestOrderedDetailsRepo v_benLabTestOrderedDetailsRepo;

		@Mock
		private LabResultEntryRepo labResultEntryRepo;

		@Mock
		private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;

		@InjectMocks
		private LabTechnicianServiceImpl labTechnicianServiceImpl;

		private Long benRegID;
		private Long visitCode;
		private ArrayList<LabResultEntry> procedureResults;
		private ArrayList<V_benLabTestOrderedDetails> orderedLabTestListLab;
		private ArrayList<V_benLabTestOrderedDetails> orderedLabTestListRadio;
		private LabTechnicianServiceImpl labTechnicianService;

		private ArrayList<V_benLabTestOrderedDetails> orderedLabTestList;
		private JsonObject requestObj;
		private JsonObject validRequestObj;
		private JsonObject invalidRequestObj;
		private WrapperLabResultEntry wrapperLabResultEntry;

		@BeforeEach
		public void setUp() {
			benRegID = 1L;
			visitCode = 1L;

			procedureResults = new ArrayList<>();
			LabResultEntry labResultEntry = new LabResultEntry();
			labResultEntry.setProcedureID(1);
			procedureResults.add(labResultEntry);

			orderedLabTestListLab = new ArrayList<>();
			V_benLabTestOrderedDetails labTestOrderedDetailsLab = new V_benLabTestOrderedDetails();
			labTestOrderedDetailsLab.setProcedureID(2);
			orderedLabTestListLab.add(labTestOrderedDetailsLab);

			orderedLabTestListRadio = new ArrayList<>();
			V_benLabTestOrderedDetails labTestOrderedDetailsRadio = new V_benLabTestOrderedDetails();
			labTestOrderedDetailsRadio.setProcedureID(3);
			orderedLabTestListRadio.add(labTestOrderedDetailsRadio);
		}

		@Test
		public void testGetBenePrescribedProcedureDetails() {
			when(labTechnicianServiceImpl.getLabResultDataForBen(benRegID, visitCode)).thenReturn(procedureResults);
			when(v_benLabTestOrderedDetailsRepo
					.findDistinctByBeneficiaryRegIDAndVisitCodeAndProcedureTypeAndProcedureIDNotInOrderByProcedureIDAscTestComponentIDAscResultValueAsc(
							benRegID, visitCode, "Laboratory", new ArrayList<>(procedureResults)))
					.thenReturn(orderedLabTestListLab);
			when(v_benLabTestOrderedDetailsRepo
					.findDistinctByBeneficiaryRegIDAndVisitCodeAndProcedureTypeAndProcedureIDNotInOrderByProcedureIDAscTestComponentIDAscResultValueAsc(
							benRegID, visitCode, "Radiology", new ArrayList<>(procedureResults)))
					.thenReturn(orderedLabTestListRadio);

			String response = labTechnicianServiceImpl.getBenePrescribedProcedureDetails(benRegID, visitCode);

			Map<String, Object> responseMap = new Gson().fromJson(response, Map.class);

			assertNotNull(responseMap);
			assertTrue(responseMap.containsKey("radiologyList"));
			assertTrue(responseMap.containsKey("laboratoryList"));
			assertTrue(responseMap.containsKey("archive"));

			verify(labTechnicianServiceImpl, times(1)).getLabResultDataForBen(benRegID, visitCode);
			verify(v_benLabTestOrderedDetailsRepo, times(1))
					.findDistinctByBeneficiaryRegIDAndVisitCodeAndProcedureTypeAndProcedureIDNotInOrderByProcedureIDAscTestComponentIDAscResultValueAsc(
							benRegID, visitCode, "Laboratory", new ArrayList<>(procedureResults));
			verify(v_benLabTestOrderedDetailsRepo, times(1))
					.findDistinctByBeneficiaryRegIDAndVisitCodeAndProcedureTypeAndProcedureIDNotInOrderByProcedureIDAscTestComponentIDAscResultValueAsc(
							benRegID, visitCode, "Radiology", new ArrayList<>(procedureResults));
		}
	}

	@Test
	public void testGetPrescribedLabTestInJsonFormatRadiology() {
		ArrayList<Object> result = labTechnicianService.getPrescribedLabTestInJsonFormatRadiology(orderedLabTestList);

		assertNotNull(result);
		assertEquals(1, result.size());

		Map<String, Object> procedureCompDetails = (Map<String, Object>) result.get(0);
		assertEquals(1, procedureCompDetails.get("procedureID"));
		assertEquals("X-Ray", procedureCompDetails.get("procedureName"));
		assertEquals("Chest X-Ray", procedureCompDetails.get("procedureDesc"));
		assertEquals("Radiology", procedureCompDetails.get("procedureType"));
		assertEquals(101, procedureCompDetails.get("prescriptionID"));

		Map<String, Object> compDetails = (Map<String, Object>) procedureCompDetails.get("compDetails");
		assertEquals(1001, compDetails.get("testComponentID"));
		assertEquals("X-Ray Image", compDetails.get("testComponentName"));
		assertEquals("Image of the chest", compDetails.get("testComponentDesc"));
		assertEquals("File", compDetails.get("inputType"));
	}

	@Test
	public void testGetPrescribedLabTestInJsonFormatRadiology_EmptyList() {
		ArrayList<Object> result = labTechnicianService.getPrescribedLabTestInJsonFormatRadiology(new ArrayList<>());

		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test
	public void testGetPrescribedLabTestInJsonFormatRadiology_MultipleEntries() {
		V_benLabTestOrderedDetails detail2 = new V_benLabTestOrderedDetails();
		detail2.setProcedureID(2);
		detail2.setProcedureName("MRI");
		detail2.setProcedureDesc("Brain MRI");
		detail2.setProcedureType("Radiology");
		detail2.setPrescriptionID(102);
		detail2.setTestComponentID(1002);
		detail2.setTestComponentName("MRI Image");
		detail2.setTestComponentDesc("Image of the brain");
		detail2.setInputType("File");

		orderedLabTestList.add(detail2);

		ArrayList<Object> result = labTechnicianService.getPrescribedLabTestInJsonFormatRadiology(orderedLabTestList);

		assertNotNull(result);
		assertEquals(2, result.size());

		Map<String, Object> procedureCompDetails1 = (Map<String, Object>) result.get(0);
		assertEquals(1, procedureCompDetails1.get("procedureID"));
		assertEquals("X-Ray", procedureCompDetails1.get("procedureName"));
		assertEquals("Chest X-Ray", procedureCompDetails1.get("procedureDesc"));
		assertEquals("Radiology", procedureCompDetails1.get("procedureType"));
		assertEquals(101, procedureCompDetails1.get("prescriptionID"));

		Map<String, Object> compDetails1 = (Map<String, Object>) procedureCompDetails1.get("compDetails");
		assertEquals(1001, compDetails1.get("testComponentID"));
		assertEquals("X-Ray Image", compDetails1.get("testComponentName"));
		assertEquals("Image of the chest", compDetails1.get("testComponentDesc"));
		assertEquals("File", compDetails1.get("inputType"));

		Map<String, Object> procedureCompDetails2 = (Map<String, Object>) result.get(1);
		assertEquals(2, procedureCompDetails2.get("procedureID"));
		assertEquals("MRI", procedureCompDetails2.get("procedureName"));
		assertEquals("Brain MRI", procedureCompDetails2.get("procedureDesc"));
		assertEquals("Radiology", procedureCompDetails2.get("procedureType"));
		assertEquals(102, procedureCompDetails2.get("prescriptionID"));

		Map<String, Object> compDetails2 = (Map<String, Object>) procedureCompDetails2.get("compDetails");
		assertEquals(1002, compDetails2.get("testComponentID"));
		assertEquals("MRI Image", compDetails2.get("testComponentName"));
		assertEquals("Image of the brain", compDetails2.get("testComponentDesc"));
		assertEquals("File", compDetails2.get("inputType"));
	}

	@Test
	public void testGetBenePrescribedProcedureDetails() {
		Long benRegID = 1L;
		Long visitCode = 1L;

		List<V_benLabTestOrderedDetails> labTestList = new ArrayList<>();
		List<LabResultEntry> labResultEntries = new ArrayList<>();

		when(v_benLabTestOrderedDetailsRepo
				.findDistinctByBeneficiaryRegIDAndVisitCodeAndProcedureTypeAndProcedureIDNotInOrderByProcedureIDAscTestComponentIDAscResultValueAsc(
						benRegID, visitCode, "Laboratory", new ArrayList<>()))
				.thenReturn(labTestList);
		when(v_benLabTestOrderedDetailsRepo
				.findDistinctByBeneficiaryRegIDAndVisitCodeAndProcedureTypeAndProcedureIDNotInOrderByProcedureIDAscTestComponentIDAscResultValueAsc(
						benRegID, visitCode, "Radiology", new ArrayList<>()))
				.thenReturn(labTestList);
		when(labResultEntryRepo.findByBeneficiaryRegIDAndVisitCodeOrderByProcedureIDAsc(benRegID, visitCode))
				.thenReturn(labResultEntries);

		String response = labTechnicianServiceImpl.getBenePrescribedProcedureDetails(benRegID, visitCode);

		assertNotNull(response);
		verify(v_benLabTestOrderedDetailsRepo, times(1))
				.findDistinctByBeneficiaryRegIDAndVisitCodeAndProcedureTypeAndProcedureIDNotInOrderByProcedureIDAscTestComponentIDAscResultValueAsc(
						benRegID, visitCode, "Laboratory", new ArrayList<>());
		verify(v_benLabTestOrderedDetailsRepo, times(1))
				.findDistinctByBeneficiaryRegIDAndVisitCodeAndProcedureTypeAndProcedureIDNotInOrderByProcedureIDAscTestComponentIDAscResultValueAsc(
						benRegID, visitCode, "Radiology", new ArrayList<>());
		verify(labResultEntryRepo, times(1)).findByBeneficiaryRegIDAndVisitCodeOrderByProcedureIDAsc(benRegID,
				visitCode);
	}

	@Test
	public void testSaveLabTestResult() throws Exception {
		JsonObject requestObj = new JsonObject();
		requestObj.add("labTestResults", new JsonObject());

		WrapperLabResultEntry wrapperLabResultEntry = InputMapper.gson().fromJson(requestObj,
				WrapperLabResultEntry.class);

		when(labResultEntryRepo.saveAll(anyList())).thenReturn(new ArrayList<>());

		Integer result = labTechnicianServiceImpl.saveLabTestResult(requestObj);

		assertEquals(1, result);
		verify(labResultEntryRepo, times(1)).saveAll(anyList());
	}

	@Test
	public void testGetLast_3_ArchivedTestVisitList() {
		Long benRegID = 1L;
		Long visitCode = 1L;

		List<Object[]> visitCodeList = new ArrayList<>();
		when(labResultEntryRepo.getLast_3_visitForLabTestDone(benRegID, visitCode)).thenReturn(visitCodeList);

		String response = labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(benRegID, visitCode);

		assertNotNull(response);
		verify(labResultEntryRepo, times(1)).getLast_3_visitForLabTestDone(benRegID, visitCode);
	}

	@Test
	public void testGetLabResultForVisitcode() {
		Long benRegID = 1L;
		Long visitCode = 1L;

		List<LabResultEntry> labResultList = new ArrayList<>();
		when(labResultEntryRepo.findByBeneficiaryRegIDAndVisitCodeOrderByProcedureIDAsc(benRegID, visitCode))
				.thenReturn(labResultList);

		String response = labTechnicianServiceImpl.getLabResultForVisitcode(benRegID, visitCode);

		assertNotNull(response);
		verify(labResultEntryRepo, times(1)).findByBeneficiaryRegIDAndVisitCodeOrderByProcedureIDAsc(benRegID,
				visitCode);
	}

	@Test
	public void testSaveLabTestResultWithValidRequest() throws Exception {
		when(InputMapper.gson().fromJson(validRequestObj, WrapperLabResultEntry.class))
				.thenReturn(wrapperLabResultEntry);
		when(labResultEntryRepo.saveAll(anyList())).thenReturn(wrapperLabResultEntry.getLabTestResults());

		Integer result = labTechnicianServiceImpl.saveLabTestResult(validRequestObj);

		assertEquals(1, result);
		verify(labResultEntryRepo, times(1)).saveAll(anyList());
	}

	@Test
	public void testSaveLabTestResultWithInvalidRequest() throws Exception {
		Integer result = labTechnicianServiceImpl.saveLabTestResult(invalidRequestObj);

		assertEquals(1, result);
		verify(labResultEntryRepo, times(0)).saveAll(anyList());
	}

	@Test
	public void testSaveLabTestResultWithEmptyLabTestResults() throws Exception {
		JsonObject emptyLabTestResultsObj = new JsonObject();
		emptyLabTestResultsObj.addProperty("labTestResults", "[]");

		Integer result = labTechnicianServiceImpl.saveLabTestResult(emptyLabTestResultsObj);

		assertEquals(1, result);
		verify(labResultEntryRepo, times(0)).saveAll(anyList());
	}

	@Test
	public void testSaveLabTestResultWithValidRadiologyTestResults() throws Exception {
		wrapperLabResultEntry.setRadiologyTestResults(wrapperLabResultEntry.getLabTestResults());
		when(InputMapper.gson().fromJson(validRequestObj, WrapperLabResultEntry.class))
				.thenReturn(wrapperLabResultEntry);
		when(labResultEntryRepo.saveAll(anyList())).thenReturn(wrapperLabResultEntry.getLabTestResults());

		Integer result = labTechnicianServiceImpl.saveLabTestResult(validRequestObj);

		assertEquals(1, result);
		verify(labResultEntryRepo, times(1)).saveAll(anyList());
	}
}
