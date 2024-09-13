package com.iemr.hwc.service.neonatal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;
import com.iemr.hwc.data.neonatal.*;
import com.iemr.hwc.data.nurse.BenAnthropometryDetail;
import com.iemr.hwc.data.nurse.BenPhysicalVitalDetail;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.nurse.CDSS;
import com.iemr.hwc.data.quickConsultation.BenChiefComplaint;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.repo.neonatal.FollowUpForImmunizationRepo;
import com.iemr.hwc.repo.neonatal.ImmunizationServicesRepo;
import com.iemr.hwc.repo.neonatal.InfantBirthDetailsRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.nurse.CDSSRepo;
import com.iemr.hwc.repo.nurse.anc.BenAdherenceRepo;
import com.iemr.hwc.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.hwc.repository.neonatal.*;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonServiceImpl;
import com.iemr.hwc.service.generalOPD.GeneralOPDDoctorServiceImpl;
import com.iemr.hwc.service.labtechnician.LabTechnicianServiceImpl;
import com.iemr.hwc.service.tele_consultation.SMSGatewayServiceImpl;
import com.iemr.hwc.utils.exception.IEMRException;
import com.mysql.cj.xdevapi.JsonArray;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.*;

public class TestNeonatalServiceImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private InfantBirthDetailsRepo infantBirthDetailsRepo;

	@Mock
	private ImmunizationServicesRepo immunizationServicesRepo;

	@Mock
	private CommonServiceImpl commonServiceImpl;

	@Mock
	private SMSGatewayServiceImpl sMSGatewayServiceImpl;
	@Mock
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;

	@Mock
	private CommonNurseServiceImpl commonNurseServiceImpl;

	@Mock
	private BenVisitDetailRepo benVisitDetailRepo;
	@Mock
	private CommonDoctorServiceImpl commonDoctorServiceImpl;
	@Mock
	private FollowUpForImmunizationRepo followUpForImmunizationRepo;
	@Mock
	private GeneralOPDDoctorServiceImpl generalOPDDoctorServiceImpl;
	@Mock
	private LabTechnicianServiceImpl labTechnicianServiceImpl;
	@Mock
	private BenChiefComplaintRepo benChiefComplaintRepo;
	@Mock
	private BenAdherenceRepo benAdherenceRepo;
	@Mock
	private CDSSRepo cdssRepo;

	@InjectMocks
	private NeonatalServiceImpl neonatalServiceImpl;

	private JsonObject requestOBJ;
	private String authorization;
	private JsonObject requestObj;
	private Long benRegID;
	private Long visitCode;

	private Gson gson;
	private Long beneficiaryRegID;

	private BenAnthropometryDetail benAnthropometryDetail;
	private BenPhysicalVitalDetail benPhysicalVitalDetail;
	private Long benVisitID;
	private CDSS cdss;
	private InfantBirthDetails infantBirthDetails;
	private List<ImmunizationServices> immunizationServicesList;
	private ImmunizationServices immunizationService;
	private Vaccines vaccine;
	private BeneficiaryVisitDetail visitDetail;
	private BenChiefComplaint benChiefComplaint;
	private ImmunizationHistory immunizationHistory;

	private ImmunizationServices immunizationServices;
	private JsonObject vitalDetailsOBJ;
	private JsonObject historyOBJ;
	private JsonObject immunServiceOBJ;

	@BeforeEach
	public void setUp() {
		requestOBJ = new JsonObject();
		authorization = "Bearer token";
	}

	@Test
	public void testSaveNurseData_Success() throws SQLException, Exception {
		JsonObject visitDetails = new JsonObject();
		visitDetails.addProperty("visitID", 1L);
		visitDetails.addProperty("visitCode", 1L);
		requestOBJ.add("visitDetails", visitDetails);

		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
				.thenReturn(new TeleconsultationRequestOBJ());

		String result = neonatalServiceImpl.saveNurseData(requestOBJ, authorization);

		assertNotNull(result);
		assertTrue(result.contains("Data saved successfully"));
		verify(infantBirthDetailsRepo, times(1)).save(any(InfantBirthDetails.class));
		verify(immunizationServicesRepo, times(1)).saveAll(anyList());
	}

	@Test
	public void testSaveNurseData_InvalidRequest() {
		Exception exception = assertThrows(RuntimeException.class, () -> {
			neonatalServiceImpl.saveNurseData(new JsonObject(), authorization);
		});

		assertEquals("Invalid request, visit details is missing", exception.getMessage());
	}

	@Test
	public void testSaveNurseData_Exception() throws SQLException, Exception {
		JsonObject visitDetails = new JsonObject();
		visitDetails.addProperty("visitID", 1L);
		visitDetails.addProperty("visitCode", 1L);
		requestOBJ.add("visitDetails", visitDetails);

		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
				.thenThrow(new RuntimeException("Database error"));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			neonatalServiceImpl.saveNurseData(requestOBJ, authorization);
		});

		assertEquals("Database error", exception.getMessage());
	}

	@Test
	public void testDeleteVisitDetails_Success() throws Exception {
		CommonUtilityClass nurseUtilityClass = new CommonUtilityClass();
		nurseUtilityClass.setBeneficiaryRegID(1L);
		nurseUtilityClass.setProviderServiceMapID(1);

		when(benVisitDetailRepo.getVisitCode(1L, 1)).thenReturn(1L);

		neonatalServiceImpl.deleteVisitDetails(requestObj);

		verify(benChiefComplaintRepo, times(1)).deleteVisitDetails(1L);
		verify(benAdherenceRepo, times(1)).deleteVisitDetails(1L);
		verify(cdssRepo, times(1)).deleteVisitDetails(1L);
		verify(benVisitDetailRepo, times(1)).deleteVisitDetails(1L);
	}

	@Test
	public void testDeleteVisitDetails_NoVisitDetails() throws Exception {
		JsonObject invalidRequestObj = new JsonObject();

		neonatalServiceImpl.deleteVisitDetails(invalidRequestObj);

		verify(benChiefComplaintRepo, never()).deleteVisitDetails(anyLong());
		verify(benAdherenceRepo, never()).deleteVisitDetails(anyLong());
		verify(cdssRepo, never()).deleteVisitDetails(anyLong());
		verify(benVisitDetailRepo, never()).deleteVisitDetails(anyLong());
	}

	@Test
	public void testSaveNurseData() throws Exception {
		JsonObject visitDetails = new JsonObject();
		visitDetails.addProperty("visitDetails", "details");
		requestObj.add("visitDetails", visitDetails);

		when(commonServiceImpl.createTcRequest(any(), any(), eq(authorization)))
				.thenReturn(new TeleconsultationRequestOBJ());
		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any())).thenReturn(1L);
		when(commonNurseServiceImpl.generateVisitCode(anyLong(), anyInt(), anyInt())).thenReturn(1L);

		String response = neonatalServiceImpl.saveNurseData(requestObj, authorization);

		assertTrue(response.contains("Data saved successfully"));
		verify(commonNurseServiceImpl, times(1)).saveBeneficiaryVisitDetails(any());
	}

	@Test
	public void testDeleteVisitDetails() throws Exception {
		JsonObject visitDetails = new JsonObject();
		visitDetails.addProperty("visitDetails", "details");
		requestObj.add("visitDetails", visitDetails);

		when(benVisitDetailRepo.getVisitCode(anyLong(), anyInt())).thenReturn(1L);

		neonatalServiceImpl.deleteVisitDetails(requestObj);

		verify(benChiefComplaintRepo, times(1)).deleteVisitDetails(anyLong());
		verify(benAdherenceRepo, times(1)).deleteVisitDetails(anyLong());
		verify(cdssRepo, times(1)).deleteVisitDetails(anyLong());
		verify(benVisitDetailRepo, times(1)).deleteVisitDetails(anyLong());
	}

	@Test
	public void testSaveDoctorDataNNI() throws Exception {
		JsonObject investigation = new JsonObject();
		investigation.add("laboratoryList", new JsonArray());
		requestObj.add("investigation", investigation);

		when(commonServiceImpl.createTcRequest(any(), any(), eq(authorization)))
				.thenReturn(new TeleconsultationRequestOBJ());
		when(commonNurseServiceImpl.saveBenPrescription(any())).thenReturn(1L);

		int response = neonatalServiceImpl.saveDoctorDataNNI(requestObj, authorization);

		assertEquals(1, response);
		verify(commonNurseServiceImpl, times(1)).saveBenPrescription(any());
	}

	@Test
	public void testUpdateBenVitalDetailsNNI() throws Exception {
		JsonObject vitalDetails = new JsonObject();
		requestObj.add("vitalDetails", vitalDetails);

		int response = neonatalServiceImpl.updateBenVitalDetailsNNI(vitalDetails);

		assertEquals(1, response);
		verify(commonNurseServiceImpl, times(1)).updateANCAnthropometryDetails(any());
		verify(commonNurseServiceImpl, times(1)).updateANCPhysicalVitalDetails(any());
	}

	@Test
	public void testUpdateBenHistoryDetails() throws Exception {
		JsonObject historyDetails = new JsonObject();
		requestObj.add("immunizationHistory", historyDetails);

		int response = neonatalServiceImpl.updateBenHistoryDetails(requestObj);

		assertEquals(1, response);
		verify(commonNurseServiceImpl, times(1)).updateChildImmunizationDetail(any());
	}

	@Test
	public void testUpdateBenImmunServiceDetailsNNI() throws Exception {
		JsonObject immunServiceDetails = new JsonObject();
		requestObj.add("immunizationServices", immunServiceDetails);

		int response = neonatalServiceImpl.updateBenImmunServiceDetailsNNI(requestObj);

		assertEquals(1, response);
		verify(immunizationServicesRepo, times(1)).updateDeletedFlag(anyBoolean(), anyLong(), anyLong());
		verify(immunizationServicesRepo, times(1)).saveAll(anyList());
	}

	@Test
	public void testUpdateDoctorDataNNI() throws Exception {
		JsonObject investigation = new JsonObject();
		investigation.add("laboratoryList", new JsonArray());
		requestObj.add("investigation", investigation);

		when(commonServiceImpl.createTcRequest(any(), any(), eq(authorization)))
				.thenReturn(new TeleconsultationRequestOBJ());

		Long response = neonatalServiceImpl.updateDoctorDataNNI(requestObj, authorization);

		assertEquals(1L, response);
		verify(commonNurseServiceImpl, times(1)).updatePrescription(any());
	}

	@Test
	public void testGetBenVisitDetailsFrmNurseNNI() throws Exception {
		when(commonNurseServiceImpl.getCSVisitDetails(benRegID, visitCode)).thenReturn(visitDetail);
		when(commonNurseServiceImpl.getBenChiefComplaints(benRegID, visitCode)).thenReturn("ChiefComplaints");
		when(commonNurseServiceImpl.getBenCdss(benRegID, visitCode)).thenReturn("Cdss");

		String expectedResponse = new HashMap<String, Object>() {
			{
				put("neonatalNurseVisitDetail", gson.toJson(visitDetail));
				put("BenChiefComplaints", "ChiefComplaints");
				put("Cdss", "Cdss");
			}
		}.toString();

		String actualResponse = neonatalServiceImpl.getBenVisitDetailsFrmNurseNNI(benRegID, visitCode);

		assertEquals(expectedResponse, actualResponse);
		verify(commonNurseServiceImpl, times(1)).getCSVisitDetails(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenChiefComplaints(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenCdss(benRegID, visitCode);
	}

	@Test
	public void testGetBeneficiaryVitalDetails() throws Exception {
		when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, visitCode))
				.thenReturn(benAnthropometryDetail);
		when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode))
				.thenReturn(benPhysicalVitalDetail);

		String response = neonatalServiceImpl.getBeneficiaryVitalDetails(beneficiaryRegID, visitCode);

		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("benAnthropometryDetail", benAnthropometryDetail);
		expectedMap.put("benPhysicalVitalDetail", benPhysicalVitalDetail);

		assertEquals(new Gson().toJson(expectedMap), response);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID,
				visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetBeneficiaryVitalDetails_Exception() throws Exception {
		when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, visitCode))
				.thenThrow(new IEMRException("Error fetching anthropometry details"));
		when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode))
				.thenThrow(new IEMRException("Error fetching vital details"));

		Exception exception = assertThrows(IEMRException.class, () -> {
			neonatalServiceImpl.getBeneficiaryVitalDetails(beneficiaryRegID, visitCode);
		});

		assertTrue(exception.getMessage().contains("Error fetching anthropometry details"));
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID,
				visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetBeneficiaryCdssDetails() {
		when(commonNurseServiceImpl.getBenCdssDetails(beneficiaryRegID, benVisitID)).thenReturn(cdss);

		String response = neonatalServiceImpl.getBeneficiaryCdssDetails(beneficiaryRegID, benVisitID);

		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("presentChiefComplaint", cdss);
		expectedMap.put("diseaseSummary", cdss);

		Gson gson = new Gson();
		String expectedResponse = gson.toJson(expectedMap);

		assertEquals(expectedResponse, response);
		verify(commonNurseServiceImpl, times(2)).getBenCdssDetails(beneficiaryRegID, benVisitID);
	}

	@Test
	public void testGetBirthAndImmuniHistory_Success() throws IEMRException {
		when(benVisitDetailRepo.getLatestVisitCodeForGivenVC(benRegID, "Neonatal and infant health care services"))
				.thenReturn(visitCode);
		when(commonNurseServiceImpl.getImmunizationHistory(benRegID, visitCode)).thenReturn(new HashMap<>());
		when(infantBirthDetailsRepo.findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode))
				.thenReturn(infantBirthDetails);

		String response = neonatalServiceImpl.getBirthAndImmuniHistory(benRegID, visitCode);

		assertNotNull(response);
		assertTrue(response.contains("immunizationHistory"));
		assertTrue(response.contains("infantBirthDetails"));

		verify(benVisitDetailRepo, times(1)).getLatestVisitCodeForGivenVC(benRegID,
				"Neonatal and infant health care services");
		verify(commonNurseServiceImpl, times(1)).getImmunizationHistory(benRegID, visitCode);
		verify(infantBirthDetailsRepo, times(1)).findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode);
	}

	@Test
	public void testGetBirthAndImmuniHistory_NoPreviousInfo() {
		when(benVisitDetailRepo.getLatestVisitCodeForGivenVC(benRegID, "Neonatal and infant health care services"))
				.thenReturn(null);

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			neonatalServiceImpl.getBirthAndImmuniHistory(benRegID, null);
		});

		assertEquals("no previous information found. please capture latest information", exception.getMessage());

		verify(benVisitDetailRepo, times(1)).getLatestVisitCodeForGivenVC(benRegID,
				"Neonatal and infant health care services");
		verify(commonNurseServiceImpl, times(0)).getImmunizationHistory(anyLong(), anyLong());
		verify(infantBirthDetailsRepo, times(0)).findByBeneficiaryRegIDAndVisitCode(anyLong(), anyLong());
	}

	@Test
	public void testGetBirthAndImmuniHistory_CongenitalAnomalies() throws IEMRException {
		when(benVisitDetailRepo.getLatestVisitCodeForGivenVC(benRegID, "Neonatal and infant health care services"))
				.thenReturn(visitCode);
		when(commonNurseServiceImpl.getImmunizationHistory(benRegID, visitCode)).thenReturn(new HashMap<>());
		when(infantBirthDetailsRepo.findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode))
				.thenReturn(infantBirthDetails);

		String response = neonatalServiceImpl.getBirthAndImmuniHistory(benRegID, visitCode);

		assertNotNull(response);
		assertTrue(response.contains("Anomaly1"));
		assertTrue(response.contains("Anomaly2"));

		verify(benVisitDetailRepo, times(1)).getLatestVisitCodeForGivenVC(benRegID,
				"Neonatal and infant health care services");
		verify(commonNurseServiceImpl, times(1)).getImmunizationHistory(benRegID, visitCode);
		verify(infantBirthDetailsRepo, times(1)).findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode);
	}

	@Test
	public void testGetBeneficiaryImmunizationServiceDetails() throws Exception {
		when(immunizationServicesRepo.findByBeneficiaryRegIDAndVisitCodeAndDeleted(beneficiaryRegID, visitCode, false))
				.thenReturn(immunizationServicesList);

		String result = neonatalServiceImpl.getBeneficiaryImmunizationServiceDetails(beneficiaryRegID, visitCode);

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("immunizationServices", gson.toJson(immunizationService));

		assertEquals(expectedMap.toString(), result);
		verify(immunizationServicesRepo, times(1)).findByBeneficiaryRegIDAndVisitCodeAndDeleted(beneficiaryRegID,
				visitCode, false);
	}

	@Test
	public void testGetBeneficiaryImmunizationServiceDetails_NoData() throws Exception {
		when(immunizationServicesRepo.findByBeneficiaryRegIDAndVisitCodeAndDeleted(beneficiaryRegID, visitCode, false))
				.thenReturn(Collections.emptyList());

		String result = neonatalServiceImpl.getBeneficiaryImmunizationServiceDetails(beneficiaryRegID, visitCode);

		assertEquals("{}", result);
		verify(immunizationServicesRepo, times(1)).findByBeneficiaryRegIDAndVisitCodeAndDeleted(beneficiaryRegID,
				visitCode, false);
	}

	@Test
	public void testGetBenCaseRecordFromDoctorNNI_Success() throws IEMRException {
		// Mocking the dependencies
		when(commonDoctorServiceImpl.getFindingsDetails(benRegID, visitCode)).thenReturn("findings");
		when(generalOPDDoctorServiceImpl.getGeneralOPDDiagnosisDetails(benRegID, visitCode)).thenReturn("diagnosis");
		when(commonDoctorServiceImpl.getInvestigationDetails(benRegID, visitCode)).thenReturn("investigation");
		when(commonDoctorServiceImpl.getPrescribedDrugs(benRegID, visitCode)).thenReturn("prescription");
		when(labTechnicianServiceImpl.getLabResultDataForBen(benRegID, visitCode)).thenReturn("labReport");
		when(commonNurseServiceImpl.getGraphicalTrendData(benRegID, "genOPD")).thenReturn("graphData");
		when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(benRegID, visitCode))
				.thenReturn("archivedVisitcodeForLabResult");
		FollowUpForImmunization followUpForImmunization = new FollowUpForImmunization();
		when(followUpForImmunizationRepo.findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode))
				.thenReturn(followUpForImmunization);

		// Call the method under test
		String response = neonatalServiceImpl.getBenCaseRecordFromDoctorNNI(benRegID, visitCode);

		// Verify the results
		assertNotNull(response);
		assertTrue(response.contains("findings"));
		assertTrue(response.contains("diagnosis"));
		assertTrue(response.contains("investigation"));
		assertTrue(response.contains("prescription"));
		assertTrue(response.contains("labReport"));
		assertTrue(response.contains("graphData"));
		assertTrue(response.contains("archivedVisitcodeForLabResult"));
		assertTrue(response.contains("followUpForImmunization"));

		// Verify the interactions
		verify(commonDoctorServiceImpl, times(1)).getFindingsDetails(benRegID, visitCode);
		verify(generalOPDDoctorServiceImpl, times(1)).getGeneralOPDDiagnosisDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getInvestigationDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getPrescribedDrugs(benRegID, visitCode);
		verify(labTechnicianServiceImpl, times(1)).getLabResultDataForBen(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getGraphicalTrendData(benRegID, "genOPD");
		verify(labTechnicianServiceImpl, times(1)).getLast_3_ArchivedTestVisitList(benRegID, visitCode);
		verify(followUpForImmunizationRepo, times(1)).findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode);
	}

	@Test
	public void testGetBenCaseRecordFromDoctorNNI_NoFollowUpForImmunization() throws IEMRException {
		// Mocking the dependencies
		when(commonDoctorServiceImpl.getFindingsDetails(benRegID, visitCode)).thenReturn("findings");
		when(generalOPDDoctorServiceImpl.getGeneralOPDDiagnosisDetails(benRegID, visitCode)).thenReturn("diagnosis");
		when(commonDoctorServiceImpl.getInvestigationDetails(benRegID, visitCode)).thenReturn("investigation");
		when(commonDoctorServiceImpl.getPrescribedDrugs(benRegID, visitCode)).thenReturn("prescription");
		when(labTechnicianServiceImpl.getLabResultDataForBen(benRegID, visitCode)).thenReturn("labReport");
		when(commonNurseServiceImpl.getGraphicalTrendData(benRegID, "genOPD")).thenReturn("graphData");
		when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(benRegID, visitCode))
				.thenReturn("archivedVisitcodeForLabResult");
		when(followUpForImmunizationRepo.findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode)).thenReturn(null);

		// Call the method under test
		String response = neonatalServiceImpl.getBenCaseRecordFromDoctorNNI(benRegID, visitCode);

		// Verify the results
		assertNotNull(response);
		assertTrue(response.contains("findings"));
		assertTrue(response.contains("diagnosis"));
		assertTrue(response.contains("investigation"));
		assertTrue(response.contains("prescription"));
		assertTrue(response.contains("labReport"));
		assertTrue(response.contains("graphData"));
		assertTrue(response.contains("archivedVisitcodeForLabResult"));
		assertFalse(response.contains("followUpForImmunization"));

		// Verify the interactions
		verify(commonDoctorServiceImpl, times(1)).getFindingsDetails(benRegID, visitCode);
		verify(generalOPDDoctorServiceImpl, times(1)).getGeneralOPDDiagnosisDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getInvestigationDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getPrescribedDrugs(benRegID, visitCode);
		verify(labTechnicianServiceImpl, times(1)).getLabResultDataForBen(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getGraphicalTrendData(benRegID, "genOPD");
		verify(labTechnicianServiceImpl, times(1)).getLast_3_ArchivedTestVisitList(benRegID, visitCode);
		verify(followUpForImmunizationRepo, times(1)).findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode);
	}

	@Test
	public void testGetBenCaseRecordFromDoctorNNI_Exception() throws IEMRException {
		// Mocking the dependencies to throw an exception
		when(commonDoctorServiceImpl.getFindingsDetails(benRegID, visitCode)).thenThrow(new IEMRException("Exception"));

		// Call the method under test and verify the exception
		assertThrows(IEMRException.class, () -> {
			neonatalServiceImpl.getBenCaseRecordFromDoctorNNI(benRegID, visitCode);
		});

		// Verify the interactions
		verify(commonDoctorServiceImpl, times(1)).getFindingsDetails(benRegID, visitCode);
		verify(generalOPDDoctorServiceImpl, times(0)).getGeneralOPDDiagnosisDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(0)).getInvestigationDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(0)).getPrescribedDrugs(benRegID, visitCode);
		verify(labTechnicianServiceImpl, times(0)).getLabResultDataForBen(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(0)).getGraphicalTrendData(benRegID, "genOPD");
		verify(labTechnicianServiceImpl, times(0)).getLast_3_ArchivedTestVisitList(benRegID, visitCode);
		verify(followUpForImmunizationRepo, times(0)).findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode);
	}

	@Test
	public void testGetNurseDataNNI_Success() throws Exception {
		String result = neonatalServiceImpl.getNurseDataNNI(benRegID, visitCode);

		assertNotNull(result);
		assertTrue(result.contains("neonatalNurseVisitDetail"));
		assertTrue(result.contains("BenChiefComplaints"));
		assertTrue(result.contains("history"));
		assertTrue(result.contains("vitals"));
		assertTrue(result.contains("immunizationServices"));
		assertTrue(result.contains("cdss"));

		verify(commonNurseServiceImpl, times(1)).getCSVisitDetails(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenChiefComplaints(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getImmunizationHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalVitalDetails(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenCdss(benRegID, visitCode);
	}

	@Test
	public void testGetNurseDataNNI_NoVisitCode() throws Exception {
		when(benVisitDetailRepo.getLatestVisitCodeForGivenVC(benRegID, "Neonatal and infant health care services"))
				.thenReturn(visitCode);

		String result = neonatalServiceImpl.getNurseDataNNI(benRegID, null);

		assertNotNull(result);
		assertTrue(result.contains("neonatalNurseVisitDetail"));
		assertTrue(result.contains("BenChiefComplaints"));
		assertTrue(result.contains("history"));
		assertTrue(result.contains("vitals"));
		assertTrue(result.contains("immunizationServices"));
		assertTrue(result.contains("cdss"));

		verify(benVisitDetailRepo, times(1)).getLatestVisitCodeForGivenVC(benRegID,
				"Neonatal and infant health care services");
		verify(commonNurseServiceImpl, times(1)).getCSVisitDetails(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenChiefComplaints(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getImmunizationHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalVitalDetails(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenCdss(benRegID, visitCode);
	}

	@Test
	public void testUpdateBenVitalDetailsNNI_Success() throws Exception {
		BenAnthropometryDetail benAnthropometryDetail = new BenAnthropometryDetail();
		BenPhysicalVitalDetail benPhysicalVitalDetail = new BenPhysicalVitalDetail();

		doNothing().when(commonNurseServiceImpl).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		doNothing().when(commonNurseServiceImpl).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));

		int result = neonatalServiceImpl.updateBenVitalDetailsNNI(vitalDetailsOBJ);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(1)).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testUpdateBenVitalDetailsNNI_Failure() throws Exception {
		doThrow(new IEMRException("Update failed")).when(commonNurseServiceImpl)
				.updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		doThrow(new IEMRException("Update failed")).when(commonNurseServiceImpl)
				.updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));

		Exception exception = assertThrows(IEMRException.class, () -> {
			neonatalServiceImpl.updateBenVitalDetailsNNI(vitalDetailsOBJ);
		});

		assertEquals("Update failed", exception.getMessage());
		verify(commonNurseServiceImpl, times(1)).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(1)).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testUpdateBenHistoryDetails_Success() throws Exception {
		WrapperImmunizationHistory wrapperImmunizationHistory = new WrapperImmunizationHistory();
		when(commonNurseServiceImpl.updateChildImmunizationDetail(any(WrapperImmunizationHistory.class))).thenReturn(1);

		InfantBirthDetails infantBirthDetails = new InfantBirthDetails();
		when(infantBirthDetailsRepo.save(any(InfantBirthDetails.class))).thenReturn(infantBirthDetails);

		int result = neonatalServiceImpl.updateBenHistoryDetails(historyOBJ);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).updateChildImmunizationDetail(any(WrapperImmunizationHistory.class));
		verify(infantBirthDetailsRepo, times(1)).save(any(InfantBirthDetails.class));
	}

	@Test
	public void testUpdateBenHistoryDetails_InvalidImmunizationHistory() throws Exception {
		historyOBJ.remove("immunizationHistory");

		int result = neonatalServiceImpl.updateBenHistoryDetails(historyOBJ);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, never()).updateChildImmunizationDetail(any(WrapperImmunizationHistory.class));
	}

	@Test
	public void testUpdateBenHistoryDetails_InvalidInfantBirthDetails() throws Exception {
		historyOBJ.remove("infantBirthDetails");

		int result = neonatalServiceImpl.updateBenHistoryDetails(historyOBJ);

		assertEquals(1, result);
		verify(infantBirthDetailsRepo, never()).save(any(InfantBirthDetails.class));
	}

	@Test
	public void testUpdateBenHistoryDetails_Exception() {
		assertThrows(IEMRException.class, () -> {
			neonatalServiceImpl.updateBenHistoryDetails(null);
		});
	}

	@Test
	public void testUpdateBenImmunServiceDetailsNNI_Success() throws Exception {
		ImmunizationServices immunizationServices = InputMapper.gson()
				.fromJson(immunServiceOBJ.get("immunizationServices"), ImmunizationServices.class);

		doNothing().when(immunizationServicesRepo).updateDeletedFlag(true, immunizationServices.getBeneficiaryRegID(),
				immunizationServices.getVisitCode());
		when(immunizationServicesRepo.saveAll(anyList())).thenReturn(new ArrayList<>());

		int result = neonatalServiceImpl.updateBenImmunServiceDetailsNNI(immunServiceOBJ);

		assertEquals(1, result);
		verify(immunizationServicesRepo, times(1)).updateDeletedFlag(true, immunizationServices.getBeneficiaryRegID(),
				immunizationServices.getVisitCode());
		verify(immunizationServicesRepo, times(1)).saveAll(anyList());
	}

	@Test
	public void testUpdateBenImmunServiceDetailsNNI_NoImmunizationServices() throws Exception {
		JsonObject emptyImmunServiceOBJ = new JsonObject();

		int result = neonatalServiceImpl.updateBenImmunServiceDetailsNNI(emptyImmunServiceOBJ);

		assertEquals(1, result);
		verify(immunizationServicesRepo, never()).updateDeletedFlag(anyBoolean(), anyLong(), anyLong());
		verify(immunizationServicesRepo, never()).saveAll(anyList());
	}

	@Test
	public void testUpdateBenImmunServiceDetailsNNI_Exception() throws Exception {
		ImmunizationServices immunizationServices = InputMapper.gson()
				.fromJson(immunServiceOBJ.get("immunizationServices"), ImmunizationServices.class);

		doThrow(new IEMRException("Update failed")).when(immunizationServicesRepo).updateDeletedFlag(true,
				immunizationServices.getBeneficiaryRegID(), immunizationServices.getVisitCode());

		Exception exception = assertThrows(IEMRException.class, () -> {
			neonatalServiceImpl.updateBenImmunServiceDetailsNNI(immunServiceOBJ);
		});

		assertEquals("Update failed", exception.getMessage());
		verify(immunizationServicesRepo, times(1)).updateDeletedFlag(true, immunizationServices.getBeneficiaryRegID(),
				immunizationServices.getVisitCode());
		verify(immunizationServicesRepo, never()).saveAll(anyList());
	}

	@Test
	public void testUpdateDoctorDataNNI_Success() throws Exception {
		// Mocking dependencies
		CommonUtilityClass commonUtilityClass = new CommonUtilityClass();
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
				.thenReturn(new TeleconsultationRequestOBJ());

		when(commonNurseServiceImpl.updatePrescription(any(PrescriptionDetail.class))).thenReturn(1);

		// Call the method under test
		Long result = neonatalServiceImpl.updateDoctorDataNNI(requestOBJ, authorization);

		// Assertions
		assertNotNull(result);
		assertEquals(1L, result);

		// Verify interactions
		verify(commonServiceImpl, times(1)).createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class),
				eq(authorization));
		verify(commonNurseServiceImpl, times(1)).updatePrescription(any(PrescriptionDetail.class));
	}

	@Test
	public void testUpdateDoctorDataNNI_Failure() throws Exception {
		// Mocking dependencies
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
				.thenReturn(new TeleconsultationRequestOBJ());

		when(commonNurseServiceImpl.updatePrescription(any(PrescriptionDetail.class))).thenReturn(0);

		// Call the method under test
		Exception exception = assertThrows(RuntimeException.class, () -> {
			neonatalServiceImpl.updateDoctorDataNNI(requestOBJ, authorization);
		});

		// Assertions
		assertEquals("Error occurred while saving data. Beneficiary status update failed", exception.getMessage());

		// Verify interactions
		verify(commonServiceImpl, times(1)).createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class),
				eq(authorization));
		verify(commonNurseServiceImpl, times(1)).updatePrescription(any(PrescriptionDetail.class));
	}

	@Test
	public void testUpdateDoctorDataNNI_NullRequest() throws Exception {
		// Call the method under test
		Long result = neonatalServiceImpl.updateDoctorDataNNI(null, authorization);

		// Assertions
		assertNull(result);

		// Verify no interactions
		verifyNoInteractions(commonServiceImpl, commonNurseServiceImpl, commonDoctorServiceImpl, sMSGatewayServiceImpl);
	}
}
