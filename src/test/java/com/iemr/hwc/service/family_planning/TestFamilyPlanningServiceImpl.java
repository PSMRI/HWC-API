package com.iemr.hwc.service.family_planning;

import static org.mockito.Mockito.*;

import java.util.HashMap;

import org.hibernate.mapping.Map;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.data.family_planning.FPDispenseDetails;
import com.iemr.hwc.data.family_planning.FPIecCounsellingDetails;
import com.iemr.hwc.data.family_planning.FamilyPlanningReproductive;
import com.iemr.hwc.data.nurse.BenAnthropometryDetail;
import com.iemr.hwc.data.nurse.BenPhysicalVitalDetail;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.nurse.CDSS;
import com.iemr.hwc.repo.family_planning.FPDispenseDetailsRepo;
import com.iemr.hwc.repo.family_planning.FPIecCounsellingDetailsRepo;
import com.iemr.hwc.repo.family_planning.FamilyPlanningReproductiveRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.nurse.anc.BenAdherenceRepo;
import com.iemr.hwc.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonServiceImpl;
import com.iemr.hwc.service.generalOPD.GeneralOPDDoctorServiceImpl;
import com.iemr.hwc.service.labtechnician.LabTechnicianServiceImpl;
import com.iemr.hwc.service.tele_consultation.SMSGatewayServiceImpl;
import com.iemr.hwc.utils.exception.IEMRException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

public class TestFamilyPlanningServiceImpl {

	@RunWith(MockitoJUnitRunner.class)

	@Mock
	private CommonServiceImpl commonServiceImpl;

	@Mock
	private FamilyPlanningReproductiveRepo familyPlanningReproductiveRepo;

	@Mock
	private FPIecCounsellingDetailsRepo fPIecCounsellingDetailsRepo;

	@Mock
	private FPDispenseDetailsRepo fPDispenseDetailsRepo;

	@Mock
	private SMSGatewayServiceImpl sMSGatewayServiceImpl;

	@Mock
	private BenVisitDetailRepo benVisitDetailRepo;

	@Mock
	private BenChiefComplaintRepo benChiefComplaintRepo;

	@Mock
	private BenAdherenceRepo benAdherenceRepo;

	@Mock
	private CdssRepo cdssRepo;
	@Mock
	private CommonNurseServiceImpl commonNurseServiceImpl;

	@Mock
	private CommonDoctorServiceImpl commonDoctorServiceImpl;

	@Mock
	private LabTechnicianServiceImpl labTechnicianServiceImpl;

	@Mock
	private GeneralOPDDoctorServiceImpl generalOPDDoctorServiceImpl;

	@Mock
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;

	@InjectMocks
	private FamilyPlanningServiceImpl familyPlanningService;
	private FamilyPlanningServiceImpl familyPlanningServiceImpl;

	private Long benRegID;
	private Long visitCode;
	private BeneficiaryVisitDetail visitDetail;
	private Map<String, Object> resMap;
	private Long beneficiaryRegID;

	private BenAnthropometryDetail benAnthropometryDetail;
	private BenPhysicalVitalDetail benPhysicalVitalDetail;

	private Long benVisitID;
	private CDSS cdss;
	private FamilyPlanningReproductive mockFpr;
	private FPIecCounsellingDetails mockFpc;
	private FPDispenseDetails mockFpd;
	private JsonObject requestObj;
	private JsonObject vitalDetailsOBJ;
	private String authorization;
	private FamilyPlanningReproductive familyPlanningReproductive;
	private FPIecCounsellingDetails fPIecCounsellingDetails;
	private FPDispenseDetails fPDispenseDetails;

	@Before
	public void setUp() {
		// Initialize mocks and other setup tasks
	}

	@Test
	public void testSaveNurseDataFP() throws Exception {
		// Prepare test data
		String jsonString = "{ \"visitDetails\": { \"visitDetails\": {} }, \"vitalDetails\": {}, \"familyPlanningReproductiveDetails\": {}, \"iecAndCounsellingDetails\": {}, \"dispensationDetails\": {} }";
		JsonObject requestOBJ = JsonParser.parseString(jsonString).getAsJsonObject();
		String authorization = "Bearer token";

		// Mock the behavior of dependencies
		when(commonServiceImpl.createTcRequest(any(), any(), anyString())).thenReturn(new TeleconsultationRequestOBJ());
		when(familyPlanningReproductiveRepo.save(any())).thenReturn(null);
		when(fPIecCounsellingDetailsRepo.save(any())).thenReturn(null);
		when(fPDispenseDetailsRepo.save(any())).thenReturn(null);
		when(sMSGatewayServiceImpl.smsSenderGateway(anyString(), anyLong(), anyLong(), anyLong(), any(), anyString(),
				anyString(), any(), anyString())).thenReturn(1);

		// Call the method to test
		String response = familyPlanningService.saveNurseDataFP(requestOBJ, authorization);

		// Verify the results
		assertNotNull(response);
		assertTrue(response.contains("Data saved successfully"));
	}

	@Test
	public void testDeleteVisitDetails() throws Exception {
		// Prepare test data
		String jsonString = "{ \"visitDetails\": {} }";
		JsonObject requestOBJ = JsonParser.parseString(jsonString).getAsJsonObject();

		// Mock the behavior of dependencies
		Long visitCode = 12345L;
		when(benVisitDetailRepo.getVisitCode(anyLong(), anyLong())).thenReturn(visitCode);

		// Call the method to test
		familyPlanningService.deleteVisitDetails(requestOBJ);

		// Verify the interactions with the mocks
		verify(benVisitDetailRepo, times(1)).getVisitCode(anyLong(), anyLong());
		verify(benChiefComplaintRepo, times(1)).deleteVisitDetails(visitCode);
		verify(benAdherenceRepo, times(1)).deleteVisitDetails(visitCode);
		verify(cdssRepo, times(1)).deleteVisitDetails(visitCode);
		verify(benVisitDetailRepo, times(1)).deleteVisitDetails(visitCode);
	}

	@Test
	public void testDeleteVisitDetails_NullRequest() throws Exception {
		// Call the method to test with null request
		familyPlanningService.deleteVisitDetails(null);

		// Verify no interactions with the mocks
		verifyNoInteractions(benVisitDetailRepo, benChiefComplaintRepo, benAdherenceRepo, cdssRepo);
	}

	@Test
	public void testDeleteVisitDetails_NoVisitDetails() throws Exception {
		// Prepare test data
		String jsonString = "{}";
		JsonObject requestOBJ = JsonParser.parseString(jsonString).getAsJsonObject();

		// Call the method to test
		familyPlanningService.deleteVisitDetails(requestOBJ);

		// Verify no interactions with the mocks
		verifyNoInteractions(benVisitDetailRepo, benChiefComplaintRepo, benAdherenceRepo, cdssRepo);
	}

	@Test
	public void testGetBenVisitDetailsFrmNurseFP_ValidData() throws Exception {
		when(commonNurseServiceImpl.getCSVisitDetails(benRegID, visitCode)).thenReturn(visitDetail);
		when(commonNurseServiceImpl.getBenAdherence(benRegID, visitCode)).thenReturn("Adherence Data");
		when(commonNurseServiceImpl.getBenChiefComplaints(benRegID, visitCode)).thenReturn("Chief Complaints Data");
		when(commonNurseServiceImpl.getBenCdss(benRegID, visitCode)).thenReturn("Cdss Data");

		String response = familyPlanningServiceImpl.getBenVisitDetailsFrmNurseFP(benRegID, visitCode);

		assertNotNull(response);
		assertTrue(response.contains("FP_NurseVisitDetail"));
		assertTrue(response.contains("Adherence Data"));
		assertTrue(response.contains("Chief Complaints Data"));
		assertTrue(response.contains("Cdss Data"));

		verify(commonNurseServiceImpl, times(1)).getCSVisitDetails(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenAdherence(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenChiefComplaints(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenCdss(benRegID, visitCode);
	}

	@Test
	public void testGetBenVisitDetailsFrmNurseFP_InvalidData() throws Exception {
		when(commonNurseServiceImpl.getCSVisitDetails(benRegID, visitCode)).thenReturn(null);

		String response = familyPlanningServiceImpl.getBenVisitDetailsFrmNurseFP(benRegID, visitCode);

		assertNotNull(response);
		assertFalse(response.contains("FP_NurseVisitDetail"));

		verify(commonNurseServiceImpl, times(1)).getCSVisitDetails(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(0)).getBenAdherence(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(0)).getBenChiefComplaints(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(0)).getBenCdss(benRegID, visitCode);
	}

	@Test
	public void testGetBenVisitDetailsFrmNurseFP_NullData() throws Exception {
		String response = familyPlanningServiceImpl.getBenVisitDetailsFrmNurseFP(null, null);

		assertNotNull(response);
		assertFalse(response.contains("FP_NurseVisitDetail"));

		verify(commonNurseServiceImpl, times(0)).getCSVisitDetails(null, null);
		verify(commonNurseServiceImpl, times(0)).getBenAdherence(null, null);
		verify(commonNurseServiceImpl, times(0)).getBenChiefComplaints(null, null);
		verify(commonNurseServiceImpl, times(0)).getBenCdss(null, null);
	}

	@Test
	public void testGetBeneficiaryVitalDetailsFP() throws Exception {
		when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, visitCode))
				.thenReturn(benAnthropometryDetail);
		when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode))
				.thenReturn(benPhysicalVitalDetail);

		String response = familyPlanningServiceImpl.getBeneficiaryVitalDetailsFP(beneficiaryRegID, visitCode);

		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("benAnthropometryDetail", benAnthropometryDetail);
		expectedMap.put("benPhysicalVitalDetail", benPhysicalVitalDetail);

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String expectedResponse = gson.toJson(expectedMap);

		assertEquals(expectedResponse, response);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID,
				visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetBeneficiaryVitalDetailsFP_NoDetails() throws Exception {
		when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, visitCode))
				.thenReturn(null);
		when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode)).thenReturn(null);

		String response = familyPlanningServiceImpl.getBeneficiaryVitalDetailsFP(beneficiaryRegID, visitCode);

		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("benAnthropometryDetail", null);
		expectedMap.put("benPhysicalVitalDetail", null);

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String expectedResponse = gson.toJson(expectedMap);

		assertEquals(expectedResponse, response);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID,
				visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetBeneficiaryCdssDetails() {
		String response = familyPlanningServiceImpl.getBeneficiaryCdssDetails(beneficiaryRegID, benVisitID);

		assertNotNull(response);
		assertTrue(response.contains("Headache"));
		assertTrue(response.contains("Migraine"));

		verify(commonNurseServiceImpl, times(2)).getBenCdssDetails(beneficiaryRegID, benVisitID);
	}

	@Test
	public void testGetBeneficiaryCdssDetails_NoData() {
		when(commonNurseServiceImpl.getBenCdssDetails(beneficiaryRegID, benVisitID)).thenReturn(null);

		String response = familyPlanningServiceImpl.getBeneficiaryCdssDetails(beneficiaryRegID, benVisitID);

		assertNotNull(response);
		assertTrue(response.contains("presentChiefComplaint"));
		assertTrue(response.contains("diseaseSummary"));

		verify(commonNurseServiceImpl, times(2)).getBenCdssDetails(beneficiaryRegID, benVisitID);
	}

	@Test
	public void testGetBeneficiaryFPDetailsFP_WithVisitCode() throws Exception {
		Long beneficiaryRegID = 1L;
		Long visitCode = 1L;

		when(familyPlanningReproductiveRepo.findOneByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode))
				.thenReturn(mockFpr);
		when(fPIecCounsellingDetailsRepo.findOneByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode))
				.thenReturn(mockFpc);
		when(fPDispenseDetailsRepo.findOneByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode))
				.thenReturn(mockFpd);

		String response = familyPlanningServiceImpl.getBeneficiaryFPDetailsFP(beneficiaryRegID, visitCode);

		assertTrue(response.contains("method1"));
		assertTrue(response.contains("counsel1"));
		assertTrue(response.contains("opted1"));
		assertTrue(response.contains("prescribed1"));

		verify(familyPlanningReproductiveRepo, times(1)).findOneByBeneficiaryRegIDAndVisitCode(beneficiaryRegID,
				visitCode);
		verify(fPIecCounsellingDetailsRepo, times(1)).findOneByBeneficiaryRegIDAndVisitCode(beneficiaryRegID,
				visitCode);
		verify(fPDispenseDetailsRepo, times(1)).findOneByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetBeneficiaryFPDetailsFP_WithoutVisitCode() throws Exception {
		Long beneficiaryRegID = 1L;
		Long visitCode = null;

		when(familyPlanningReproductiveRepo.findByBeneficiaryRegIDOrderByIdDesc(beneficiaryRegID))
				.thenReturn(Collections.singletonList(mockFpr));
		when(fPIecCounsellingDetailsRepo.findByBeneficiaryRegIDOrderByIdDesc(beneficiaryRegID))
				.thenReturn(Collections.singletonList(mockFpc));
		when(fPDispenseDetailsRepo.findByBeneficiaryRegIDOrderByIdDesc(beneficiaryRegID))
				.thenReturn(Collections.singletonList(mockFpd));

		String response = familyPlanningServiceImpl.getBeneficiaryFPDetailsFP(beneficiaryRegID, visitCode);

		assertTrue(response.contains("method1"));
		assertTrue(response.contains("counsel1"));
		assertTrue(response.contains("opted1"));
		assertTrue(response.contains("prescribed1"));

		verify(familyPlanningReproductiveRepo, times(1)).findByBeneficiaryRegIDOrderByIdDesc(beneficiaryRegID);
		verify(fPIecCounsellingDetailsRepo, times(1)).findByBeneficiaryRegIDOrderByIdDesc(beneficiaryRegID);
		verify(fPDispenseDetailsRepo, times(1)).findByBeneficiaryRegIDOrderByIdDesc(beneficiaryRegID);
	}

	@Test
	public void testGetBeneficiaryFPDetailsFP_NoDataFound() throws Exception {
		Long beneficiaryRegID = 1L;
		Long visitCode = 1L;

		when(familyPlanningReproductiveRepo.findOneByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode))
				.thenReturn(null);
		when(fPIecCounsellingDetailsRepo.findOneByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode))
				.thenReturn(null);
		when(fPDispenseDetailsRepo.findOneByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode)).thenReturn(null);

		String response = familyPlanningServiceImpl.getBeneficiaryFPDetailsFP(beneficiaryRegID, visitCode);

		assertFalse(response.contains("method1"));
		assertFalse(response.contains("counsel1"));
		assertFalse(response.contains("opted1"));
		assertFalse(response.contains("prescribed1"));

		verify(familyPlanningReproductiveRepo, times(1)).findOneByBeneficiaryRegIDAndVisitCode(beneficiaryRegID,
				visitCode);
		verify(fPIecCounsellingDetailsRepo, times(1)).findOneByBeneficiaryRegIDAndVisitCode(beneficiaryRegID,
				visitCode);
		verify(fPDispenseDetailsRepo, times(1)).findOneByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode);
	}

	@Test
	public void testUpdateFPDataFP_Success() throws IEMRException {
		// Setup
		JsonObject familyPlanningReproductiveDetails = new JsonObject();
		familyPlanningReproductiveDetails.addProperty("currentlyUsingFpMethod", "method1||method2");
		requestObj.add("familyPlanningReproductiveDetails", familyPlanningReproductiveDetails);

		JsonObject iecAndCounsellingDetails = new JsonObject();
		iecAndCounsellingDetails.addProperty("counselledOn", "counsel1||counsel2");
		requestObj.add("iecAndCounsellingDetails", iecAndCounsellingDetails);

		JsonObject dispensationDetails = new JsonObject();
		dispensationDetails.addProperty("typeOfContraceptivePrescribed", "contraceptive1||contraceptive2");
		requestObj.add("dispensationDetails", dispensationDetails);

		FamilyPlanningReproductive familyPlanningReproductive = new FamilyPlanningReproductive();
		FPIecCounsellingDetails fPIecCounsellingDetails = new FPIecCounsellingDetails();
		FPDispenseDetails fPDispenseDetails = new FPDispenseDetails();

		when(familyPlanningReproductiveRepo.save(any(FamilyPlanningReproductive.class)))
				.thenReturn(familyPlanningReproductive);
		when(fPIecCounsellingDetailsRepo.save(any(FPIecCounsellingDetails.class))).thenReturn(fPIecCounsellingDetails);
		when(fPDispenseDetailsRepo.save(any(FPDispenseDetails.class))).thenReturn(fPDispenseDetails);

		// Execute
		String response = familyPlanningServiceImpl.updateFPDataFP(requestObj);

		// Verify
		assertEquals("Data updated successfully", response);
		verify(familyPlanningReproductiveRepo, times(1)).save(any(FamilyPlanningReproductive.class));
		verify(fPIecCounsellingDetailsRepo, times(1)).save(any(FPIecCounsellingDetails.class));
		verify(fPDispenseDetailsRepo, times(1)).save(any(FPDispenseDetails.class));
	}

	@Test
	public void testUpdateFPDataFP_InvalidRequest() {
		// Setup
		JsonObject invalidRequestObj = null;

		// Execute & Verify
		IEMRException exception = assertThrows(IEMRException.class, () -> {
			familyPlanningServiceImpl.updateFPDataFP(invalidRequestObj);
		});

		assertEquals("Invalid request - NULL", exception.getMessage());
	}

	@Test
	public void testUpdateFPDataFP_NoDetails() throws IEMRException {
		// Setup
		requestObj = new JsonObject();

		// Execute
		String response = familyPlanningServiceImpl.updateFPDataFP(requestObj);

		// Verify
		assertEquals("Data updated successfully", response);
		verify(familyPlanningReproductiveRepo, never()).save(any(FamilyPlanningReproductive.class));
		verify(fPIecCounsellingDetailsRepo, never()).save(any(FPIecCounsellingDetails.class));
		verify(fPDispenseDetailsRepo, never()).save(any(FPDispenseDetails.class));
	}

	@Test
	public void testUpdateBenVitalDetailsFP_Success() throws Exception {
		BenAnthropometryDetail benAnthropometryDetail = new BenAnthropometryDetail();
		BenPhysicalVitalDetail benPhysicalVitalDetail = new BenPhysicalVitalDetail();

		when(commonNurseServiceImpl.updateANCAnthropometryDetails(any(BenAnthropometryDetail.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class))).thenReturn(1);

		int result = familyPlanningServiceImpl.updateBenVitalDetailsFP(vitalDetailsOBJ);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(1)).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testUpdateBenVitalDetailsFP_Failure() throws Exception {
		when(commonNurseServiceImpl.updateANCAnthropometryDetails(any(BenAnthropometryDetail.class)))
				.thenThrow(new IEMRException("Error"));
		when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class))).thenReturn(1);

		Exception exception = assertThrows(IEMRException.class, () -> {
			familyPlanningServiceImpl.updateBenVitalDetailsFP(vitalDetailsOBJ);
		});

		assertEquals("Error", exception.getMessage());
		verify(commonNurseServiceImpl, times(1)).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(0)).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testUpdateDoctorDataFPSuccess() throws Exception {
		// Arrange
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
				.thenReturn(null);
		when(commonNurseServiceImpl.updatePrescription(any())).thenReturn(1);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(any(), anyBoolean(), anyBoolean(), any()))
				.thenReturn(1);

		// Act
		Long result = familyPlanningServiceImpl.updateDoctorDataFP(requestObj, authorization);

		// Assert
		assertEquals(1L, result);
		verify(commonServiceImpl, times(1)).createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class),
				eq(authorization));
		verify(commonNurseServiceImpl, times(1)).updatePrescription(any());
		verify(commonDoctorServiceImpl, times(1)).updateBenFlowtableAfterDocDataUpdate(any(), anyBoolean(),
				anyBoolean(), any());
	}

	@Test
	public void testUpdateDoctorDataFPNullRequest() {
		// Arrange
		JsonObject nullRequestObj = null;

		// Act & Assert
		assertThrows(IEMRException.class, () -> {
			familyPlanningServiceImpl.updateDoctorDataFP(nullRequestObj, authorization);
		});
	}

	@Test
	public void testUpdateDoctorDataFPException() throws Exception {
		// Arrange
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
				.thenThrow(new RuntimeException("Error occurred"));

		// Act & Assert
		assertThrows(RuntimeException.class, () -> {
			familyPlanningServiceImpl.updateDoctorDataFP(requestObj, authorization);
		});
	}

	@Test
	public void testSaveDoctorDataFP_Success() throws Exception {
		// Setup
		JsonObject findings = new JsonObject();
		findings.addProperty("key", "value");
		requestObj.add("findings", findings);

		JsonObject diagnosis = new JsonObject();
		diagnosis.addProperty("key", "value");
		requestObj.add("diagnosis", diagnosis);

		JsonObject investigation = new JsonObject();
		JsonArray laboratoryList = new JsonArray();
		investigation.add("laboratoryList", laboratoryList);
		requestObj.add("investigation", investigation);

		JsonArray prescription = new JsonArray();
		requestObj.add("prescription", prescription);

		JsonObject refer = new JsonObject();
		refer.addProperty("key", "value");
		requestObj.add("refer", refer);

		CommonUtilityClass commonUtilityClass = new CommonUtilityClass();
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
				.thenReturn(null);
		when(commonDoctorServiceImpl.saveDocFindings(any())).thenReturn(1);
		when(commonNurseServiceImpl.saveBenPrescription(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenInvestigation(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenPrescribedDrugsList(any())).thenReturn(1);
		when(commonDoctorServiceImpl.saveBenReferDetails(any(), eq(true))).thenReturn(1L);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataSave(any(), anyBoolean(), anyBoolean(), any()))
				.thenReturn(1);

		// Execute
		int result = familyPlanningServiceImpl.saveDoctorDataFP(requestObj, authorization);

		// Verify
		assertEquals(1, result);
		verify(commonDoctorServiceImpl, times(1)).saveDocFindings(any());
		verify(commonNurseServiceImpl, times(1)).saveBenPrescription(any());
		verify(commonNurseServiceImpl, times(1)).saveBenInvestigation(any());
		verify(commonNurseServiceImpl, times(1)).saveBenPrescribedDrugsList(any());
		verify(commonDoctorServiceImpl, times(1)).saveBenReferDetails(any(), eq(true));
		verify(commonDoctorServiceImpl, times(1)).updateBenFlowtableAfterDocDataSave(any(), anyBoolean(), anyBoolean(),
				any());
	}

	@Test
	public void testSaveDoctorDataFP_NullRequest() {
		// Execute & Verify
		assertThrows(IEMRException.class, () -> {
			familyPlanningServiceImpl.saveDoctorDataFP(null, authorization);
		});
	}

	@Test
	public void testSaveDoctorDataFP_NoTestsOrMedicines() throws Exception {
		// Setup
		JsonObject findings = new JsonObject();
		findings.addProperty("key", "value");
		requestObj.add("findings", findings);

		JsonObject diagnosis = new JsonObject();
		diagnosis.addProperty("key", "value");
		requestObj.add("diagnosis", diagnosis);

		CommonUtilityClass commonUtilityClass = new CommonUtilityClass();
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
				.thenReturn(null);
		when(commonDoctorServiceImpl.saveDocFindings(any())).thenReturn(1);
		when(commonNurseServiceImpl.saveBenPrescription(any())).thenReturn(1L);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataSave(any(), anyBoolean(), anyBoolean(), any()))
				.thenReturn(1);

		// Execute
		int result = familyPlanningServiceImpl.saveDoctorDataFP(requestObj, authorization);

		// Verify
		assertEquals(1, result);
		verify(commonDoctorServiceImpl, times(1)).saveDocFindings(any());
		verify(commonNurseServiceImpl, times(1)).saveBenPrescription(any());
		verify(commonDoctorServiceImpl, times(1)).updateBenFlowtableAfterDocDataSave(any(), anyBoolean(), anyBoolean(),
				any());
	}

	@Test
	public void testSaveDoctorDataFP_UpdateBeneficiaryFlowTable() throws Exception {
		// Setup
		JsonObject findings = new JsonObject();
		findings.addProperty("key", "value");
		requestObj.add("findings", findings);

		JsonObject diagnosis = new JsonObject();
		diagnosis.addProperty("key", "value");
		requestObj.add("diagnosis", diagnosis);

		JsonObject investigation = new JsonObject();
		JsonArray laboratoryList = new JsonArray();
		investigation.add("laboratoryList", laboratoryList);
		requestObj.add("investigation", investigation);

		JsonArray prescription = new JsonArray();
		requestObj.add("prescription", prescription);

		JsonObject refer = new JsonObject();
		refer.addProperty("key", "value");
		requestObj.add("refer", refer);

		CommonUtilityClass commonUtilityClass = new CommonUtilityClass();
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
				.thenReturn(null);
		when(commonDoctorServiceImpl.saveDocFindings(any())).thenReturn(1);
		when(commonNurseServiceImpl.saveBenPrescription(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenInvestigation(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenPrescribedDrugsList(any())).thenReturn(1);
		when(commonDoctorServiceImpl.saveBenReferDetails(any(), eq(true))).thenReturn(1L);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataSave(any(), anyBoolean(), anyBoolean(), any()))
				.thenReturn(1);

		// Execute
		int result = familyPlanningServiceImpl.saveDoctorDataFP(requestObj, authorization);

		// Verify
		assertEquals(1, result);
		verify(commonDoctorServiceImpl, times(1)).updateBenFlowtableAfterDocDataSave(any(), anyBoolean(), anyBoolean(),
				any());
	}

	@Test
	public void testGetBenCaseRecordFromDoctorFP() throws IEMRException {
		// Mocking the return values
		when(commonDoctorServiceImpl.getFindingsDetails(benRegID, visitCode)).thenReturn("findingsDetails");
		when(generalOPDDoctorServiceImpl.getGeneralOPDDiagnosisDetails(benRegID, visitCode))
				.thenReturn("diagnosisDetails");
		when(commonDoctorServiceImpl.getInvestigationDetails(benRegID, visitCode)).thenReturn("investigationDetails");
		when(commonDoctorServiceImpl.getPrescribedDrugs(benRegID, visitCode)).thenReturn("prescribedDrugs");
		when(commonDoctorServiceImpl.getReferralDetails(benRegID, visitCode, true)).thenReturn("referralDetails");
		when(labTechnicianServiceImpl.getLabResultDataForBen(benRegID, visitCode)).thenReturn("labResults");
		when(commonNurseServiceImpl.getGraphicalTrendData(benRegID, "genOPD")).thenReturn("graphData");
		when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(benRegID, visitCode))
				.thenReturn("archivedVisitCodes");

		// Execute the method
		String response = familyPlanningServiceImpl.getBenCaseRecordFromDoctorFP(benRegID, visitCode);

		// Verify interactions
		verify(commonDoctorServiceImpl, times(1)).getFindingsDetails(benRegID, visitCode);
		verify(generalOPDDoctorServiceImpl, times(1)).getGeneralOPDDiagnosisDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getInvestigationDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getPrescribedDrugs(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getReferralDetails(benRegID, visitCode, true);
		verify(labTechnicianServiceImpl, times(1)).getLabResultDataForBen(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getGraphicalTrendData(benRegID, "genOPD");
		verify(labTechnicianServiceImpl, times(1)).getLast_3_ArchivedTestVisitList(benRegID, visitCode);

		// Assert the response
		assertNotNull(response);
		assertTrue(response.contains("findingsDetails"));
		assertTrue(response.contains("diagnosisDetails"));
		assertTrue(response.contains("investigationDetails"));
		assertTrue(response.contains("prescribedDrugs"));
		assertTrue(response.contains("referralDetails"));
		assertTrue(response.contains("labResults"));
		assertTrue(response.contains("graphData"));
		assertTrue(response.contains("archivedVisitCodes"));
	}

	@Test
	public void testGetNurseDataFP() throws Exception {
		String expectedVisitDetailJson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
				.toJson(visitDetail);
		String expectedFPDetailsJson = new Gson().toJson(familyPlanningReproductive);
		String expectedVitalsJson = new Gson().toJson(fPIecCounsellingDetails);
		String expectedCdssJson = new Gson().toJson(fPDispenseDetails);

		String result = familyPlanningServiceImpl.getNurseDataFP(benRegID, visitCode);

		assertNotNull(result);
		assertTrue(result.contains(expectedVisitDetailJson));
		assertTrue(result.contains(expectedFPDetailsJson));
		assertTrue(result.contains(expectedVitalsJson));
		assertTrue(result.contains(expectedCdssJson));

		verify(commonNurseServiceImpl, times(1)).getCSVisitDetails(benRegID, visitCode);
		verify(familyPlanningReproductiveRepo, times(1)).findOneByBeneficiaryRegIDAndVisitCode(benRegID, visitCode);
		verify(fPIecCounsellingDetailsRepo, times(1)).findOneByBeneficiaryRegIDAndVisitCode(benRegID, visitCode);
		verify(fPDispenseDetailsRepo, times(1)).findOneByBeneficiaryRegIDAndVisitCode(benRegID, visitCode);
	}
}
