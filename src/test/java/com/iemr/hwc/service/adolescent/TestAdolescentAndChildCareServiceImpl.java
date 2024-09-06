package com.iemr.hwc.service.adolescent;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.data.adolescent.T_OralVitaminProphylaxis;
import com.iemr.hwc.data.anc.WrapperBenInvestigationANC;
import com.iemr.hwc.data.anc.WrapperImmunizationHistory;
import com.iemr.hwc.data.labModule.LabResultEntry;
import com.iemr.hwc.data.neonatal.FollowUpForImmunization;
import com.iemr.hwc.data.neonatal.ImmunizationServices;
import com.iemr.hwc.data.neonatal.InfantBirthDetails;
import com.iemr.hwc.data.nurse.BenAnthropometryDetail;
import com.iemr.hwc.data.nurse.BenPhysicalVitalDetail;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.quickConsultation.PrescribedDrugDetail;
import com.iemr.hwc.data.quickConsultation.PrescriptionDetail;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.repo.adolescent.T_OralVitaminProphylaxisRepo;
import com.iemr.hwc.repo.neonatal.FollowUpForImmunizationRepo;
import com.iemr.hwc.repo.neonatal.ImmunizationServicesRepo;
import com.iemr.hwc.repo.neonatal.InfantBirthDetailsRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.nurse.CDSSRepo;
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
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Any;

import jakarta.mail.internet.ParseException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ExtendWith(MockitoExtension.class)
public class TestAdolescentAndChildCareServiceImpl {
	@Mock
	private CommonServiceImpl commonServiceImpl;
	@Mock
	private ImmunizationServicesRepo immunizationServicesRepo;
	@Mock
	private SMSGatewayServiceImpl sMSGatewayServiceImpl;
	@Mock
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;
	@Mock
	private CommonNurseServiceImpl commonNurseServiceImpl;
	@Mock
	private InfantBirthDetailsRepo infantBirthDetailsRepo;
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
	private T_OralVitaminProphylaxisRepo oralVitaminProphylaxisRepo;
	@Mock
	private BenChiefComplaintRepo benChiefComplaintRepo;
	@Mock
	private BenAdherenceRepo benAdherenceRepo;

	@InjectMocks
	private AdolescentAndChildCareServiceImpl service;
	private JsonObject validRequestObj;
	private JsonObject invalidRequestObj;
	private JsonObject validRequestOBJ;
	private String validAuthorization;
	private JsonObject validVisitDetailsOBJ;
	private JsonObject invalidVisitDetailsOBJ;
	private CommonUtilityClass nurseUtilityClass;
	private CDSSRepo cdssRepo;
	private JsonObject validGeneralOPDHistoryOBJ;
	private JsonObject invalidGeneralOPDHistoryOBJ;
	private AdolescentAndChildCareServiceImpl adolescentAndChildCareServiceImpl;
	private JsonObject vitalDetailsOBJ;
	private Long benVisitID;
	private Long benVisitCode;
	private JsonObject requestOBJ;
	private String authorization;
	private JsonObject tmpOBJ;
	private Long benFlowID;
	private Integer vanID;
	private TeleconsultationRequestOBJ tcRequestOBJ;
	private Long benRegID;
	private Long visitCode;
	private BeneficiaryVisitDetail visitDetail;
	private Map<String, Object> expectedResponse;
	private InfantBirthDetails infantBirthDetails;
	private String immunizationHistory;
	private Long beneficiaryRegID;
	private Map<String, Object> resMap;
	 private JsonObject validVitalDetailsOBJ;
	   private JsonObject validHistoryOBJ;
	    private JsonObject invalidHistoryOBJ;
	    private JsonObject immunServiceOBJ;

	@BeforeEach
	public void setUp() {
		validRequestOBJ = JsonParser.parseString("{\"visitDetails\": {\"visitID\": 1, \"visitCode\": 12345}}")
				.getAsJsonObject();
		validAuthorization = "validAuthToken";
	}

	@Test
	public void testSaveNurseData_ValidInput() throws Exception {
		// Mock dependencies and their behaviors
		when(commonServiceImpl.createTcRequest(any(), any(), anyString())).thenReturn(new TeleconsultationRequestOBJ());

		// Call the method
		String response = service.saveNurseData(validRequestOBJ, validAuthorization);

		// Verify the response
		assertNotNull(response);
		assertTrue(response.contains("Data saved successfully"));
	}

	@Test
	public void testSaveNurseData_NullInput() {
		Exception exception = assertThrows(RuntimeException.class, () -> {
			service.saveNurseData(null, validAuthorization);
		});

		assertEquals("Invalid request, visit details is missing", exception.getMessage());
	}

	@Test
	public void testSaveNurseData_MissingVisitDetails() {
		JsonObject invalidRequestOBJ = new JsonObject(); // No visitDetails field

		Exception exception = assertThrows(RuntimeException.class, () -> {
			service.saveNurseData(invalidRequestOBJ, validAuthorization);
		});

		assertEquals("Invalid request, visit details is missing", exception.getMessage());
	}

	@Test
	public void testSaveNurseData_InvalidAuthorization() throws Exception {
		// Mock dependencies and their behaviors
		when(commonServiceImpl.createTcRequest(any(), any(), anyString()))
				.thenThrow(new RuntimeException("Invalid Authorization"));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			service.saveNurseData(validRequestOBJ, "invalidAuthToken");
		});

		assertEquals("Invalid Authorization", exception.getMessage());
	}

	@Test
	public void testDeleteVisitDetails_Success() throws Exception {
		CommonUtilityClass nurseUtilityClass = new CommonUtilityClass();
		nurseUtilityClass.setBeneficiaryRegID(1L);
		nurseUtilityClass.setProviderServiceMapID(1);

		when(benVisitDetailRepo.getVisitCode(1L, 1)).thenReturn(1L);

		doNothing().when(benChiefComplaintRepo).deleteVisitDetails(1L);
		doNothing().when(benAdherenceRepo).deleteVisitDetails(1L);
		doNothing().when(cdssRepo).deleteVisitDetails(1L);
		doNothing().when(benVisitDetailRepo).deleteVisitDetails(1L);

		adolescentAndChildCareServiceImpl.deleteVisitDetails(validRequestObj);

		verify(benChiefComplaintRepo, times(1)).deleteVisitDetails(1L);
		verify(benAdherenceRepo, times(1)).deleteVisitDetails(1L);
		verify(cdssRepo, times(1)).deleteVisitDetails(1L);
		verify(benVisitDetailRepo, times(1)).deleteVisitDetails(1L);
	}

	@Test
	public void testDeleteVisitDetails_InvalidRequest() {
		Exception exception = assertThrows(RuntimeException.class, () -> {
			adolescentAndChildCareServiceImpl.deleteVisitDetails(invalidRequestObj);
		});

		String expectedMessage = "Invalid request, visit details is missing";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	
	
	
	
	

	
	
	

	@Test
	public void testSaveDoctorDataCAC_Success() throws Exception {
		// Setup mock behavior
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
				.thenReturn(new TeleconsultationRequestOBJ());
		when(commonNurseServiceImpl.saveBenPrescription(any(PrescriptionDetail.class))).thenReturn(1L);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataSave(any(CommonUtilityClass.class), anyBoolean(),
				anyBoolean(), any(TeleconsultationRequestOBJ.class))).thenReturn(1);

		// Call the method
		int result = adolescentAndChildCareServiceImpl.saveDoctorDataCAC(requestOBJ, authorization);

		// Verify the result
		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).saveBenPrescription(any(PrescriptionDetail.class));
		verify(commonDoctorServiceImpl, times(1)).updateBenFlowtableAfterDocDataSave(any(CommonUtilityClass.class),
				anyBoolean(), anyBoolean(), any(TeleconsultationRequestOBJ.class));
	}

	@Test
	public void testSaveDoctorDataCAC_NullRequestOBJ() throws Exception {
		// Call the method with null requestOBJ
		int result = adolescentAndChildCareServiceImpl.saveDoctorDataCAC(null, authorization);

		// Verify the result
		assertEquals(1, result);
	}

	@Test
	public void testSaveDoctorDataCAC_PrescriptionSaveFailure() throws Exception {
		// Setup mock behavior
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
				.thenReturn(new TeleconsultationRequestOBJ());
		when(commonNurseServiceImpl.saveBenPrescription(any(PrescriptionDetail.class))).thenReturn(null);

		// Call the method and expect an exception
		assertThrows(RuntimeException.class, () -> {
			adolescentAndChildCareServiceImpl.saveDoctorDataCAC(requestOBJ, authorization);
		});

		// Verify the interactions
		verify(commonNurseServiceImpl, times(1)).saveBenPrescription(any(PrescriptionDetail.class));
	}

	@Test
	public void testSaveDoctorDataCAC_UpdateFlowTableFailure() throws Exception {
		// Setup mock behavior
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
				.thenReturn(new TeleconsultationRequestOBJ());
		when(commonNurseServiceImpl.saveBenPrescription(any(PrescriptionDetail.class))).thenReturn(1L);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataSave(any(CommonUtilityClass.class), anyBoolean(),
				anyBoolean(), any(TeleconsultationRequestOBJ.class))).thenReturn(0);

		// Call the method and expect an exception
		assertThrows(RuntimeException.class, () -> {
			adolescentAndChildCareServiceImpl.saveDoctorDataCAC(requestOBJ, authorization);
		});

		// Verify the interactions
		verify(commonNurseServiceImpl, times(1)).saveBenPrescription(any(PrescriptionDetail.class));
		verify(commonDoctorServiceImpl, times(1)).updateBenFlowtableAfterDocDataSave(any(CommonUtilityClass.class),
				anyBoolean(), anyBoolean(), any(TeleconsultationRequestOBJ.class));
	}

	

	

	

	@Test
	public void testGetBenVisitDetailsFrmNurseCAC() throws Exception {
		when(commonNurseServiceImpl.getCSVisitDetails(benRegID, visitCode)).thenReturn(visitDetail);
		when(commonNurseServiceImpl.getBenChiefComplaints(benRegID, visitCode)).thenReturn("ChiefComplaints");
		when(commonNurseServiceImpl.getBenCdss(benRegID, visitCode)).thenReturn("CdssDetails");

		String response = adolescentAndChildCareServiceImpl.getBenVisitDetailsFrmNurseCAC(benRegID, visitCode);

		assertTrue(response.contains("cacNurseVisitDetail"));
		assertTrue(response.contains("ChiefComplaints"));
		assertTrue(response.contains("CdssDetails"));

		verify(commonNurseServiceImpl, times(1)).getCSVisitDetails(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenChiefComplaints(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenCdss(benRegID, visitCode);
	}

	@Test
	public void testGetBirthAndImmuniHistory_Success() throws Exception {
		WrapperImmunizationHistory wrapperimmunizationHistory= new WrapperImmunizationHistory();
		when(commonNurseServiceImpl.getImmunizationHistory(benRegID, visitCode)).thenReturn(wrapperimmunizationHistory);
		when(infantBirthDetailsRepo.findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode))
				.thenReturn(infantBirthDetails);

		String response = adolescentAndChildCareServiceImpl.getBirthAndImmuniHistory(benRegID, visitCode);

		Map<String, Object> expectedResponse = new HashMap<>();
		expectedResponse.put("immunizationHistory", immunizationHistory);
		expectedResponse.put("infantBirthDetails", infantBirthDetails);

		assertEquals(new Gson().toJson(expectedResponse), response);

		verify(commonNurseServiceImpl, times(1)).getImmunizationHistory(benRegID, visitCode);
		verify(infantBirthDetailsRepo, times(1)).findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode);
	}

	@Test
	public void testGetBirthAndImmuniHistory_NullVisitCode() throws Exception {
		when(benVisitDetailRepo.getLatestVisitCodeForGivenVC(benRegID, "Childhood & Adolescent Healthcare Services"))
				.thenReturn(visitCode);
		WrapperImmunizationHistory wrapperimmunizationHistory = new WrapperImmunizationHistory();
		when(commonNurseServiceImpl.getImmunizationHistory(benRegID, visitCode)).thenReturn(wrapperimmunizationHistory );
		when(infantBirthDetailsRepo.findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode))
				.thenReturn(infantBirthDetails);

		String response = adolescentAndChildCareServiceImpl.getBirthAndImmuniHistory(benRegID, null);

		Map<String, Object> expectedResponse = new HashMap<>();
		expectedResponse.put("immunizationHistory", immunizationHistory);
		expectedResponse.put("infantBirthDetails", infantBirthDetails);

		assertEquals(new Gson().toJson(expectedResponse), response);

		verify(benVisitDetailRepo, times(1)).getLatestVisitCodeForGivenVC(benRegID,
				"Childhood & Adolescent Healthcare Services");
		verify(commonNurseServiceImpl, times(1)).getImmunizationHistory(benRegID, visitCode);
		verify(infantBirthDetailsRepo, times(1)).findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode);
	}

	@Test
	public void testGetBirthAndImmuniHistory_NoPreviousInformation() {
		when(benVisitDetailRepo.getLatestVisitCodeForGivenVC(benRegID, "Childhood & Adolescent Healthcare Services"))
				.thenReturn(null);

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			adolescentAndChildCareServiceImpl.getBirthAndImmuniHistory(benRegID, null);
		});

		assertEquals("no previous information found. please capture latest information", exception.getMessage());

		verify(benVisitDetailRepo, times(1)).getLatestVisitCodeForGivenVC(benRegID,
				"Childhood & Adolescent Healthcare Services");
		verify(commonNurseServiceImpl, times(0)).getImmunizationHistory(anyLong(), anyLong());
		verify(infantBirthDetailsRepo, times(0)).findByBeneficiaryRegIDAndVisitCode(anyLong(), anyLong());
	}

	@Test
	public void testGetBeneficiaryVitalDetails_Success() throws Exception {
		// Mocking the behavior of commonNurseServiceImpl
		when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, visitCode))
				.thenReturn("AnthropometryDetails");
		when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode))
				.thenReturn("VitalDetails");

		// Calling the actual method
		String response = adolescentAndChildCareServiceImpl.getBeneficiaryVitalDetails(beneficiaryRegID, visitCode);

		// Verifying the response
		assertTrue(response.contains("AnthropometryDetails"));
		assertTrue(response.contains("VitalDetails"));

		// Verifying the interactions with mocks
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID,
				visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetBeneficiaryVitalDetails_Exception() throws Exception {
		// Mocking the behavior to throw an exception
		when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, visitCode))
				.thenThrow(new Exception("Exception occurred"));

		// Asserting that the exception is thrown
		Exception exception = assertThrows(Exception.class, () -> {
			adolescentAndChildCareServiceImpl.getBeneficiaryVitalDetails(beneficiaryRegID, visitCode);
		});

		// Verifying the exception message
		assertEquals("Exception occurred", exception.getMessage());

		// Verifying the interactions with mocks
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID,
				visitCode);
		verify(commonNurseServiceImpl, times(0)).getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode);
	}
	  
	    @Test
	    public void testGetBeneficiaryCdssDetails_ReturnsValidData() {
	        Map<String, Object> expectedData = new HashMap<>();
	        expectedData.put("presentChiefComplaint", "Chief Complaint Data");
	        expectedData.put("diseaseSummary", "Disease Summary Data");

	        when(commonNurseServiceImpl.getBenCdssDetails(beneficiaryRegID, benVisitID)).thenReturn("Chief Complaint Data");

	        String response = adolescentAndChildCareServiceImpl.getBeneficiaryCdssDetails(beneficiaryRegID, benVisitID);

	        assertTrue(response.contains("Chief Complaint Data"));
	        assertTrue(response.contains("Disease Summary Data"));
	        verify(commonNurseServiceImpl, times(2)).getBenCdssDetails(beneficiaryRegID, benVisitID);
	    }

	    @Test
	    public void testGetBeneficiaryCdssDetails_ReturnsEmptyData() {
	        when(commonNurseServiceImpl.getBenCdssDetails(beneficiaryRegID, benVisitID)).thenReturn("");

	        String response = adolescentAndChildCareServiceImpl.getBeneficiaryCdssDetails(beneficiaryRegID, benVisitID);

	        assertTrue(response.contains("presentChiefComplaint"));
	        assertTrue(response.contains("diseaseSummary"));
	        verify(commonNurseServiceImpl, times(2)).getBenCdssDetails(beneficiaryRegID, benVisitID);
	    }

	    @Test
	    public void testGetBeneficiaryCdssDetails_ThrowsException() {
	        when(commonNurseServiceImpl.getBenCdssDetails(beneficiaryRegID, benVisitID)).thenThrow(new RuntimeException("Exception occurred"));

	        Exception exception = assertThrows(RuntimeException.class, () -> {
	            adolescentAndChildCareServiceImpl.getBeneficiaryCdssDetails(beneficiaryRegID, benVisitID);
	        });

	        assertEquals("Exception occurred", exception.getMessage());
	        verify(commonNurseServiceImpl, times(1)).getBenCdssDetails(beneficiaryRegID, benVisitID);
	    }
	   

	    @Test
	    public void testGetBeneficiaryImmunizationServiceDetails_WithImmunizationData() throws Exception {
	        List<ImmunizationServices> immunizationServicesList = new ArrayList<>();
	        ImmunizationServices immunizationService = new ImmunizationServices();
	        immunizationService.setVaccineName("Vaccine1");
	        immunizationServicesList.add(immunizationService);

	        when(immunizationServicesRepo.findByBeneficiaryRegIDAndVisitCodeAndDeleted(beneficiaryRegID, visitCode, false))
	                .thenReturn(immunizationServicesList);

	        String response = adolescentAndChildCareServiceImpl.getBeneficiaryImmunizationServiceDetails(beneficiaryRegID, visitCode);

	        assertTrue(response.contains("Vaccine1"));
	        verify(immunizationServicesRepo, times(1)).findByBeneficiaryRegIDAndVisitCodeAndDeleted(beneficiaryRegID, visitCode, false);
	    }

	    @Test
	    public void testGetBeneficiaryImmunizationServiceDetails_WithoutImmunizationData() throws Exception {
	        when(immunizationServicesRepo.findByBeneficiaryRegIDAndVisitCodeAndDeleted(beneficiaryRegID, visitCode, false))
	                .thenReturn(new ArrayList<>());

	        String response = adolescentAndChildCareServiceImpl.getBeneficiaryImmunizationServiceDetails(beneficiaryRegID, visitCode);

	        assertTrue(response.contains("immunizationServices"));
	        verify(immunizationServicesRepo, times(1)).findByBeneficiaryRegIDAndVisitCodeAndDeleted(beneficiaryRegID, visitCode, false);
	    }

	    @Test
	    public void testGetBeneficiaryImmunizationServiceDetails_WithOralVitaminData() throws Exception {
	        List<T_OralVitaminProphylaxis> oralVitaminProphylaxisList = new ArrayList<>();
	        T_OralVitaminProphylaxis oralVitaminProphylaxis = new T_OralVitaminProphylaxis();
	        oralVitaminProphylaxis.setVaccineName("VitaminA");
	        oralVitaminProphylaxisList.add(oralVitaminProphylaxis);

	        when(oralVitaminProphylaxisRepo.findByBeneficiaryRegIDAndVisitCodeAndDeleted(beneficiaryRegID, visitCode, false))
	                .thenReturn(oralVitaminProphylaxisList);

	        String response = adolescentAndChildCareServiceImpl.getBeneficiaryImmunizationServiceDetails(beneficiaryRegID, visitCode);

	        assertTrue(response.contains("VitaminA"));
	        verify(oralVitaminProphylaxisRepo, times(1)).findByBeneficiaryRegIDAndVisitCodeAndDeleted(beneficiaryRegID, visitCode, false);
	    }

	    @Test
	    public void testGetBeneficiaryImmunizationServiceDetails_WithoutOralVitaminData() throws Exception {
	        when(oralVitaminProphylaxisRepo.findByBeneficiaryRegIDAndVisitCodeAndDeleted(beneficiaryRegID, visitCode, false))
	                .thenReturn(new ArrayList<>());

	        String response = adolescentAndChildCareServiceImpl.getBeneficiaryImmunizationServiceDetails(beneficiaryRegID, visitCode);

	        assertTrue(response.contains("oralVitaminAProphylaxis"));
	        verify(oralVitaminProphylaxisRepo, times(1)).findByBeneficiaryRegIDAndVisitCodeAndDeleted(beneficiaryRegID, visitCode, false);
	    }

	    @Test
	    public void testGetBeneficiaryImmunizationServiceDetails_WithBothData() throws Exception {
	        List<ImmunizationServices> immunizationServicesList = new ArrayList<>();
	        ImmunizationServices immunizationService = new ImmunizationServices();
	        immunizationService.setVaccineName("Vaccine1");
	        immunizationServicesList.add(immunizationService);

	        List<T_OralVitaminProphylaxis> oralVitaminProphylaxisList = new ArrayList<>();
	        T_OralVitaminProphylaxis oralVitaminProphylaxis = new T_OralVitaminProphylaxis();
	        oralVitaminProphylaxis.setVaccineName("VitaminA");
	        oralVitaminProphylaxisList.add(oralVitaminProphylaxis);

	        when(immunizationServicesRepo.findByBeneficiaryRegIDAndVisitCodeAndDeleted(beneficiaryRegID, visitCode, false))
	                .thenReturn(immunizationServicesList);
	        when(oralVitaminProphylaxisRepo.findByBeneficiaryRegIDAndVisitCodeAndDeleted(beneficiaryRegID, visitCode, false))
	                .thenReturn(oralVitaminProphylaxisList);

	        String response = adolescentAndChildCareServiceImpl.getBeneficiaryImmunizationServiceDetails(beneficiaryRegID, visitCode);

	        assertTrue(response.contains("Vaccine1"));
	        assertTrue(response.contains("VitaminA"));
	        verify(immunizationServicesRepo, times(1)).findByBeneficiaryRegIDAndVisitCodeAndDeleted(beneficiaryRegID, visitCode, false);
	        verify(oralVitaminProphylaxisRepo, times(1)).findByBeneficiaryRegIDAndVisitCodeAndDeleted(beneficiaryRegID, visitCode, false);
	    }

	    @Test
	    public void testGetBeneficiaryImmunizationServiceDetails_WithoutBothData() throws Exception {
	        when(immunizationServicesRepo.findByBeneficiaryRegIDAndVisitCodeAndDeleted(beneficiaryRegID, visitCode, false))
	                .thenReturn(new ArrayList<>());
	        when(oralVitaminProphylaxisRepo.findByBeneficiaryRegIDAndVisitCodeAndDeleted(beneficiaryRegID, visitCode, false))
	                .thenReturn(new ArrayList<>());

	        String response = adolescentAndChildCareServiceImpl.getBeneficiaryImmunizationServiceDetails(beneficiaryRegID, visitCode);

	        assertTrue(response.contains("immunizationServices"));
	        assertTrue(response.contains("oralVitaminAProphylaxis"));
	        verify(immunizationServicesRepo, times(1)).findByBeneficiaryRegIDAndVisitCodeAndDeleted(beneficiaryRegID, visitCode, false);
	        verify(oralVitaminProphylaxisRepo, times(1)).findByBeneficiaryRegIDAndVisitCodeAndDeleted(beneficiaryRegID, visitCode, false);
	    }
	   
	    @Test
	    public void testGetBenCaseRecordFromDoctorCAC() throws IEMRException {
	        // Mocking dependencies
	        when(commonDoctorServiceImpl.getFindingsDetails(benRegID, visitCode)).thenReturn("findings");
	        when(generalOPDDoctorServiceImpl.getGeneralOPDDiagnosisDetails(benRegID, visitCode)).thenReturn("{\"counsellingProvided\":\"counsel1||counsel2\"}");
	        when(commonDoctorServiceImpl.getInvestigationDetails(benRegID, visitCode)).thenReturn("investigation");
	        when(commonDoctorServiceImpl.getPrescribedDrugs(benRegID, visitCode)).thenReturn("prescription");
	        ArrayList<LabResultEntry> listoflabentry = new ArrayList<LabResultEntry> ();
			when(labTechnicianServiceImpl.getLabResultDataForBen(benRegID, visitCode)).thenReturn(listoflabentry);
			Map<String, Object> returnOBJ = new HashMap<>();

	        when(commonNurseServiceImpl.getGraphicalTrendData(benRegID, "genOPD")).thenReturn(returnOBJ);
	        when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(benRegID, visitCode)).thenReturn("archivedVisitcodeForLabResult");
	        FollowUpForImmunization followUpForImmunization = new FollowUpForImmunization();
	        when(followUpForImmunizationRepo.findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode)).thenReturn(followUpForImmunization);

	        // Call the method
	        String response = adolescentAndChildCareServiceImpl.getBenCaseRecordFromDoctorCAC(benRegID, visitCode);

	        // Verify the response
	        assertTrue(response.contains("findings"));
	        assertTrue(response.contains("diagnosis"));
	        assertTrue(response.contains("investigation"));
	        assertTrue(response.contains("prescription"));
	        assertTrue(response.contains("labReport"));
	        assertTrue(response.contains("graphData"));
	        assertTrue(response.contains("archivedVisitcodeForLabResult"));
	        assertTrue(response.contains("followUpForImmunization"));

	        // Verify interactions
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
	    public void testGetNurseDataCAC() throws Exception {
	        // Mocking dependencies
	        BeneficiaryVisitDetail visitDetail = new BeneficiaryVisitDetail();
	        when(commonNurseServiceImpl.getCSVisitDetails(benRegID, visitCode)).thenReturn(visitDetail);
	        when(commonNurseServiceImpl.getBenChiefComplaints(benRegID, visitCode)).thenReturn("ChiefComplaints");
	        when(adolescentAndChildCareServiceImpl.getBirthAndImmuniHistory(benRegID, visitCode)).thenReturn("History");
	        when(adolescentAndChildCareServiceImpl.getBeneficiaryVitalDetails(benRegID, visitCode)).thenReturn("Vitals");
	        when(adolescentAndChildCareServiceImpl.getBeneficiaryImmunizationServiceDetails(benRegID, visitCode)).thenReturn("ImmunizationServices");
	        when(adolescentAndChildCareServiceImpl.getBeneficiaryCdssDetails(benRegID, visitCode)).thenReturn("Cdss");

	        // Expected response
	        HashMap<String, Object> expectedResMap = new HashMap<>();
	        expectedResMap.put("adolescentNurseVisitDetail", new Gson().toJson(visitDetail));
	        expectedResMap.put("BenChiefComplaints", "ChiefComplaints");
	        expectedResMap.put("history", "History");
	        expectedResMap.put("vitals", "Vitals");
	        expectedResMap.put("immunizationServices", "ImmunizationServices");
	        expectedResMap.put("cdss", "Cdss");

	        // Call the method
	        String response = adolescentAndChildCareServiceImpl.getNurseDataCAC(benRegID, visitCode);

	        // Verify the response
	        assertEquals(expectedResMap.toString(), response);

	        // Verify interactions
	        verify(commonNurseServiceImpl, times(1)).getCSVisitDetails(benRegID, visitCode);
	        verify(commonNurseServiceImpl, times(1)).getBenChiefComplaints(benRegID, visitCode);
	        verify(adolescentAndChildCareServiceImpl, times(1)).getBirthAndImmuniHistory(benRegID, visitCode);
	        verify(adolescentAndChildCareServiceImpl, times(1)).getBeneficiaryVitalDetails(benRegID, visitCode);
	        verify(adolescentAndChildCareServiceImpl, times(1)).getBeneficiaryImmunizationServiceDetails(benRegID, visitCode);
	        verify(adolescentAndChildCareServiceImpl, times(1)).getBeneficiaryCdssDetails(benRegID, visitCode);
	    }
	   
	  

	   

	    @Test
	    public void testUpdateBenVitalDetailsCAC_Success() throws Exception {
	        BenAnthropometryDetail benAnthropometryDetail = new BenAnthropometryDetail();
	        BenPhysicalVitalDetail benPhysicalVitalDetail = new BenPhysicalVitalDetail();

	        when(commonNurseServiceImpl.updateANCAnthropometryDetails(any(BenAnthropometryDetail.class))).thenReturn(1);
	        when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class))).thenReturn(1);

	        int result = adolescentAndChildCareServiceImpl.updateBenVitalDetailsCAC(validVitalDetailsOBJ);

	        assertEquals(1, result);
	        verify(commonNurseServiceImpl, times(1)).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
	        verify(commonNurseServiceImpl, times(1)).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	    }

	    @Test
	    public void testUpdateBenVitalDetailsCAC_NullInput() throws Exception {
	        int result = adolescentAndChildCareServiceImpl.updateBenVitalDetailsCAC(null);

	        assertEquals(1, result);
	        verify(commonNurseServiceImpl, times(0)).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
	        verify(commonNurseServiceImpl, times(0)).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	    }

	    @Test
	    public void testUpdateBenVitalDetailsCAC_Exception() throws Exception {
	        doThrow(new RuntimeException()).when(commonNurseServiceImpl).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));

	        assertThrows(RuntimeException.class, () -> {
	            adolescentAndChildCareServiceImpl.updateBenVitalDetailsCAC(validVitalDetailsOBJ);
	        });

	        verify(commonNurseServiceImpl, times(1)).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
	        verify(commonNurseServiceImpl, times(0)).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	    }
	   

	 


	    @Test
	    public void testUpdateBenHistoryDetails_Success() throws IEMRException, ParseException, java.text.ParseException {
	        WrapperImmunizationHistory wrapperImmunizationHistory = new WrapperImmunizationHistory();
	        InfantBirthDetails infantBirthDetails = new InfantBirthDetails();

	        when(commonNurseServiceImpl.updateChildImmunizationDetail(any(WrapperImmunizationHistory.class))).thenReturn(1);
	        when(infantBirthDetailsRepo.save(any(InfantBirthDetails.class))).thenReturn(infantBirthDetails);

	        int result = adolescentAndChildCareServiceImpl.updateBenHistoryDetails(validHistoryOBJ);

	        assertEquals(1, result);
	        verify(commonNurseServiceImpl, times(1)).updateChildImmunizationDetail(any(WrapperImmunizationHistory.class));
	        verify(infantBirthDetailsRepo, times(1)).save(any(InfantBirthDetails.class));
	    }

	    @Test
	    public void testUpdateBenHistoryDetails_NullInput() throws IEMRException, ParseException, java.text.ParseException {
	        int result = adolescentAndChildCareServiceImpl.updateBenHistoryDetails(null);

	        assertEquals(1, result);
	        verify(commonNurseServiceImpl, times(0)).updateChildImmunizationDetail(any(WrapperImmunizationHistory.class));
	        verify(infantBirthDetailsRepo, times(0)).save(any(InfantBirthDetails.class));
	    }

	    @Test
	    public void testUpdateBenHistoryDetails_InvalidInput() throws IEMRException, ParseException, java.text.ParseException {
	        int result = adolescentAndChildCareServiceImpl.updateBenHistoryDetails(invalidHistoryOBJ);

	        assertEquals(1, result);
	        verify(commonNurseServiceImpl, times(0)).updateChildImmunizationDetail(any(WrapperImmunizationHistory.class));
	        verify(infantBirthDetailsRepo, times(0)).save(any(InfantBirthDetails.class));
	    }

	    @Test
	    public void testUpdateBenHistoryDetails_Exception() throws IEMRException, ParseException {
	        doThrow(new RuntimeException()).when(commonNurseServiceImpl).updateChildImmunizationDetail(any(WrapperImmunizationHistory.class));

	        assertThrows(RuntimeException.class, () -> {
	            adolescentAndChildCareServiceImpl.updateBenHistoryDetails(validHistoryOBJ);
	        });

	        verify(commonNurseServiceImpl, times(1)).updateChildImmunizationDetail(any(WrapperImmunizationHistory.class));
	        verify(infantBirthDetailsRepo, times(0)).save(any(InfantBirthDetails.class));
	    }
	   

	    

	   

	   

	    @Test
	    public void testUpdateBenImmunServiceDetailsCAC_Success() throws Exception {
	        when(immunizationServicesRepo.updateDeletedFlag(true, 1L, 1L)).thenReturn(1);
	        when(oralVitaminProphylaxisRepo.save(any(T_OralVitaminProphylaxis.class))).thenReturn(new T_OralVitaminProphylaxis());

	        int result = adolescentAndChildCareServiceImpl.updateBenImmunServiceDetailsCAC(immunServiceOBJ);

	        assertEquals(1, result);
	        verify(immunizationServicesRepo, times(1)).updateDeletedFlag(true, 1L, 1L);
	        verify(oralVitaminProphylaxisRepo, times(1)).save(any(T_OralVitaminProphylaxis.class));
	    }

	    @Test
	    public void testUpdateBenImmunServiceDetailsCAC_NullImmunServiceOBJ() throws Exception {
	        int result = adolescentAndChildCareServiceImpl.updateBenImmunServiceDetailsCAC(new JsonObject());

	        assertEquals(1, result);
	        verify(immunizationServicesRepo, never()).updateDeletedFlag(anyBoolean(), anyLong(), anyLong());
	        verify(oralVitaminProphylaxisRepo, never()).save(any(T_OralVitaminProphylaxis.class));
	    }

	    @Test
	    public void testUpdateBenImmunServiceDetailsCAC_MissingFields() throws Exception {
	        JsonObject incompleteImmunServiceOBJ = new JsonObject();
	        incompleteImmunServiceOBJ.add("immunizationServices", new JsonObject());

	        int result = adolescentAndChildCareServiceImpl.updateBenImmunServiceDetailsCAC(incompleteImmunServiceOBJ);

	        assertEquals(1, result);
	        verify(immunizationServicesRepo, never()).updateDeletedFlag(anyBoolean(), anyLong(), anyLong());
	        verify(oralVitaminProphylaxisRepo, never()).save(any(T_OralVitaminProphylaxis.class));
	    }

	    
	   

	    @Test
	    public void testUpdateDoctorDataCAC_Success() throws Exception {
	        CommonUtilityClass commonUtilityClass = new CommonUtilityClass();
	        TeleconsultationRequestOBJ tcRequestOBJ = new TeleconsultationRequestOBJ();
	        PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
	        WrapperBenInvestigationANC wrapperBenInvestigationANC = new WrapperBenInvestigationANC();
	        FollowUpForImmunization followUpForImmunization = new FollowUpForImmunization();
	        PrescribedDrugDetail[] prescribedDrugDetail = new PrescribedDrugDetail[0];

	        when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), anyString())).thenReturn(tcRequestOBJ);
	        when(commonNurseServiceImpl.updatePrescription(any(PrescriptionDetail.class))).thenReturn(1);
	        when(followUpForImmunizationRepo.save(any(FollowUpForImmunization.class))).thenReturn(followUpForImmunization);
	        when(commonNurseServiceImpl.saveBenInvestigation(any(WrapperBenInvestigationANC.class))).thenReturn(1L);
	        when(commonNurseServiceImpl.saveBenPrescribedDrugsList(anyList())).thenReturn(1);
	        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(any(CommonUtilityClass.class), anyBoolean(), anyBoolean(), any(TeleconsultationRequestOBJ.class))).thenReturn(1);
	        when(sMSGatewayServiceImpl.smsSenderGateway(any(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(vanID);
	        Long result = adolescentAndChildCareServiceImpl.updateDoctorDataCAC(requestOBJ, "Authorization");

	        assertEquals(1L, result);
	        verify(commonServiceImpl, times(1)).createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), anyString());
	        verify(commonNurseServiceImpl, times(1)).updatePrescription(any(PrescriptionDetail.class));
	        verify(followUpForImmunizationRepo, times(1)).save(any(FollowUpForImmunization.class));
	        verify(commonNurseServiceImpl, times(1)).saveBenInvestigation(any(WrapperBenInvestigationANC.class));
	        verify(commonNurseServiceImpl, times(1)).saveBenPrescribedDrugsList(anyList());
	        verify(commonDoctorServiceImpl, times(1)).updateBenFlowtableAfterDocDataUpdate(any(CommonUtilityClass.class), anyBoolean(), anyBoolean(), any(TeleconsultationRequestOBJ.class));
	        verify(sMSGatewayServiceImpl, times(1)).smsSenderGateway(any(), any(), any(), anyLong(), any(), anyString(), anyString(), any(), anyString());
	    }

	    @Test
	    public void testUpdateDoctorDataCAC_NullInput() throws Exception {
	        Long result = adolescentAndChildCareServiceImpl.updateDoctorDataCAC(null, "Authorization");

	        assertNull(result);
	        verify(commonServiceImpl, never()).createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), anyString());
	        verify(commonNurseServiceImpl, never()).updatePrescription(any(PrescriptionDetail.class));
	        verify(followUpForImmunizationRepo, never()).save(any(FollowUpForImmunization.class));
	        verify(commonNurseServiceImpl, never()).saveBenInvestigation(any(WrapperBenInvestigationANC.class));
	        verify(commonNurseServiceImpl, never()).saveBenPrescribedDrugsList(anyList());
	        verify(commonDoctorServiceImpl, never()).updateBenFlowtableAfterDocDataUpdate(any(CommonUtilityClass.class), anyBoolean(), anyBoolean(), any(TeleconsultationRequestOBJ.class));
	        verify(sMSGatewayServiceImpl, never()).smsSenderGateway(any(), any(), any(), anyLong(), any(), anyString(), anyString(), any(), anyString());
	    }

	    @Test
	    public void testUpdateDoctorDataCAC_Exception() throws Exception {
	        when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), anyString())).thenThrow(new RuntimeException());

	        assertThrows(RuntimeException.class, () -> {
	            adolescentAndChildCareServiceImpl.updateDoctorDataCAC(requestOBJ, "Authorization");
	        });

	        verify(commonServiceImpl, times(1)).createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), anyString());
	        verify(commonNurseServiceImpl, never()).updatePrescription(any(PrescriptionDetail.class));
	        verify(followUpForImmunizationRepo, never()).save(any(FollowUpForImmunization.class));
	        verify(commonNurseServiceImpl, never()).saveBenInvestigation(any(WrapperBenInvestigationANC.class));
	        verify(commonNurseServiceImpl, never()).saveBenPrescribedDrugsList(anyList());
	        verify(commonDoctorServiceImpl, never()).updateBenFlowtableAfterDocDataUpdate(any(CommonUtilityClass.class), anyBoolean(), anyBoolean(), any(TeleconsultationRequestOBJ.class));
	        verify(sMSGatewayServiceImpl, never()).smsSenderGateway(any(), any(), any(), anyLong(), any(), anyString(), anyString(), any(), anyString());
	    }
	}
	
