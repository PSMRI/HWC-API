package com.iemr.hwc.service.quickConsultation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.google.gson.JsonObject;
import com.iemr.hwc.data.nurse.BenAnthropometryDetail;
import com.iemr.hwc.data.nurse.BenPhysicalVitalDetail;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.nurse.CDSS;
import com.iemr.hwc.data.quickConsultation.BenChiefComplaint;
import com.iemr.hwc.data.quickConsultation.BenClinicalObservations;
import com.iemr.hwc.data.quickConsultation.ExternalLabTestOrder;
import com.iemr.hwc.data.quickConsultation.PrescriptionDetail;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.repo.nurse.BenPhysicalVitalRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.nurse.CDSSRepo;
import com.iemr.hwc.repo.nurse.anc.BenAdherenceRepo;
import com.iemr.hwc.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.hwc.repo.quickConsultation.BenClinicalObservationsRepo;
import com.iemr.hwc.repo.quickConsultation.ExternalTestOrderRepo;
import com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonServiceImpl;
import com.iemr.hwc.service.generalOPD.GeneralOPDDoctorServiceImpl;
import com.iemr.hwc.service.labtechnician.LabTechnicianServiceImpl;
import com.iemr.hwc.service.tele_consultation.SMSGatewayServiceImpl;
import com.iemr.hwc.service.tele_consultation.TeleConsultationServiceImpl;
import com.iemr.hwc.utils.mapper.InputMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

public class TestQuickConsultationServiceImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private BenChiefComplaintRepo benChiefComplaintRepo;
	@Mock
	private BenClinicalObservationsRepo benClinicalObservationsRepo;

	@Mock
	private CommonDoctorServiceImpl commonDoctorServiceImpl;

	@Mock
	private InputMapper inputMapper;
	@Mock
	private PrescriptionDetailRepo prescriptionDetailRepo;
	@Mock
	private ExternalTestOrderRepo externalTestOrderRepo;

	@Mock
	private CommonNurseServiceImpl commonNurseServiceImpl;
	@Mock
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;
	@Mock
	private LabTechnicianServiceImpl labTechnicianServiceImpl;

	@Mock
	private GeneralOPDDoctorServiceImpl generalOPDDoctorServiceImpl;
	@Mock
	private CommonServiceImpl commonServiceImpl;
	@Mock
	private TeleConsultationServiceImpl teleConsultationServiceImpl;
	@Mock
	private SMSGatewayServiceImpl sMSGatewayServiceImpl;
	@Mock
	private BenPhysicalVitalRepo benPhysicalVitalRepo;
	@Mock
	private BenVisitDetailRepo benVisitDetailRepo;
	@Mock
	private BenAdherenceRepo benAdherenceRepo;

	@Mock
	private CDSSRepo cdssRepo;

	@InjectMocks
	private QuickConsultationServiceImpl quickConsultationServiceImpl;

	private JsonObject caseSheet;
	private BenClinicalObservations benClinicalObservations;
	private PrescriptionDetail prescriptionDetail;
	private JsonObject validCaseSheet;
	private ExternalLabTestOrder externalLabTestOrder;
	private JsonObject validJsonObject;
	private JsonObject invalidJsonObject;
	private String authorization;
	private JsonObject validRequestObj;
	private JsonObject invalidRequestObj;

	private BeneficiaryVisitDetail benVisitDetailsOBJ;
	private Long benVisitID;
	private Long benFlowID;
	private Long benVisitCode;
	private Integer vanID;
	private TeleconsultationRequestOBJ tcRequestOBJ;
	private JsonObject quickConsultDoctorOBJ;
	private BeneficiaryVisitDetail beneficiaryVisitDetail;
	private CDSS cdss;
	private Long beneficiaryRegID;
	private Long visitCode;
	private Long benRegID;

	@BeforeEach
	public void setUp() {
		caseSheet = new JsonObject();
		caseSheet.addProperty("key", "value");
	}

	@Test
	public void testSaveBeneficiaryChiefComplaint_WithNonEmptyList() {
		ArrayList<BenChiefComplaint> benChiefComplaints = new ArrayList<>();
		benChiefComplaints.add(new BenChiefComplaint());
		benChiefComplaints.add(new BenChiefComplaint());

		when(benChiefComplaintRepo.saveAll(anyList())).thenReturn(benChiefComplaints);

		Long result = quickConsultationServiceImpl.saveBeneficiaryChiefComplaint(caseSheet);

		assertEquals(1L, result);
		verify(benChiefComplaintRepo, times(1)).saveAll(anyList());
	}

	@Test
	public void testSaveBeneficiaryChiefComplaint_WithNullList() {
		ArrayList<BenChiefComplaint> benChiefComplaints = null;

		Long result = quickConsultationServiceImpl.saveBeneficiaryChiefComplaint(caseSheet);

		assertEquals(1L, result);
		verify(benChiefComplaintRepo, never()).saveAll(anyList());
	}

	@Test
	public void testSaveBeneficiaryChiefComplaint_WithEmptyList() {
		ArrayList<BenChiefComplaint> benChiefComplaints = new ArrayList<>();

		Long result = quickConsultationServiceImpl.saveBeneficiaryChiefComplaint(caseSheet);

		assertEquals(1L, result);
		verify(benChiefComplaintRepo, never()).saveAll(anyList());
	}

	@Test
	public void testSaveBeneficiaryClinicalObservations_Success() throws Exception {
		String[] snomedCTcodeArr = { "code", "term" };
		when(commonDoctorServiceImpl.getSnomedCTcode("symptoms")).thenReturn(snomedCTcodeArr);
		when(benClinicalObservationsRepo.save(any(BenClinicalObservations.class))).thenReturn(benClinicalObservations);

		Long result = quickConsultationServiceImpl.saveBeneficiaryClinicalObservations(caseSheet);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(benClinicalObservationsRepo, times(1)).save(any(BenClinicalObservations.class));
	}

	@Test
	public void testSaveBeneficiaryClinicalObservations_NullInput() throws Exception {
		Long result = quickConsultationServiceImpl.saveBeneficiaryClinicalObservations(null);

		assertNull(result);
		verify(benClinicalObservationsRepo, never()).save(any(BenClinicalObservations.class));
	}

	@Test
	public void testSaveBeneficiaryClinicalObservations_Exception() throws Exception {
		when(inputMapper.gson().fromJson(caseSheet, BenClinicalObservations.class))
				.thenThrow(new RuntimeException("Error"));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			quickConsultationServiceImpl.saveBeneficiaryClinicalObservations(caseSheet);
		});

		assertEquals("Error", exception.getMessage());
		verify(benClinicalObservationsRepo, never()).save(any(BenClinicalObservations.class));
	}

	@Test
	public void testSaveBenPrescriptionForANCSuccess() {
		when(prescriptionDetailRepo.save(any(PrescriptionDetail.class))).thenReturn(prescriptionDetail);

		Long result = quickConsultationServiceImpl.saveBenPrescriptionForANC(prescriptionDetail);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(prescriptionDetailRepo, times(1)).save(any(PrescriptionDetail.class));
	}

	@Test
	public void testSaveBenPrescriptionForANCFailure() {
		when(prescriptionDetailRepo.save(any(PrescriptionDetail.class))).thenReturn(null);

		Long result = quickConsultationServiceImpl.saveBenPrescriptionForANC(prescriptionDetail);

		assertNull(result);
		verify(prescriptionDetailRepo, times(1)).save(any(PrescriptionDetail.class));
	}

	@Test
	public void testSaveBeneficiaryExternalLabTestOrderDetails_Success() {
		when(externalTestOrderRepo.save(any(ExternalLabTestOrder.class))).thenReturn(externalLabTestOrder);

		Long result = quickConsultationServiceImpl.saveBeneficiaryExternalLabTestOrderDetails(validCaseSheet);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(externalTestOrderRepo, times(1)).save(any(ExternalLabTestOrder.class));
	}

	@Test
	public void testSaveBeneficiaryExternalLabTestOrderDetails_NullInput() {
		Long result = quickConsultationServiceImpl.saveBeneficiaryExternalLabTestOrderDetails(null);

		assertNull(result);
		verify(externalTestOrderRepo, times(0)).save(any(ExternalLabTestOrder.class));
	}

	@Test
	public void testSaveBeneficiaryExternalLabTestOrderDetails_InvalidInput() {
		JsonObject invalidCaseSheet = new JsonObject();

		when(externalTestOrderRepo.save(any(ExternalLabTestOrder.class))).thenReturn(null);

		Long result = quickConsultationServiceImpl.saveBeneficiaryExternalLabTestOrderDetails(invalidCaseSheet);

		assertNull(result);
		verify(externalTestOrderRepo, times(1)).save(any(ExternalLabTestOrder.class));
	}

	@Test
	public void testQuickConsultNurseDataInsert_Success() throws Exception {
		CommonUtilityClass nurseUtilityClass = new CommonUtilityClass();
		nurseUtilityClass.setSessionID(1L);
		nurseUtilityClass.setVanID(1L);
		nurseUtilityClass.setBenFlowID(1L);

		BeneficiaryVisitDetail benVisitDetailsOBJ = new BeneficiaryVisitDetail();
		benVisitDetailsOBJ.setBeneficiaryRegID(1L);
		benVisitDetailsOBJ.setVisitReason("reason");
		benVisitDetailsOBJ.setVisitCategory("category");

		when(InputMapper.gson().fromJson(any(JsonObject.class), eq(CommonUtilityClass.class)))
				.thenReturn(nurseUtilityClass);
		when(InputMapper.gson().fromJson(any(JsonObject.class), eq(BeneficiaryVisitDetail.class)))
				.thenReturn(benVisitDetailsOBJ);
		when(commonNurseServiceImpl.getMaxCurrentdate(anyLong(), anyString(), anyString())).thenReturn(0);
		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any(BeneficiaryVisitDetail.class), anyLong()))
				.thenReturn(1L);
		when(commonNurseServiceImpl.generateVisitCode(anyLong(), anyLong(), anyLong())).thenReturn(1L);
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), anyString()))
				.thenReturn(new TeleconsultationRequestOBJ());
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(any(BenAnthropometryDetail.class)))
				.thenReturn(1L);
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(any(BenPhysicalVitalDetail.class)))
				.thenReturn(1L);
		when(sMSGatewayServiceImpl.smsSenderGateway(anyString(), anyLong(), anyLong(), anyLong(), any(), anyLong(),
				anyString(), any(), anyString())).thenReturn(1);

		String response = quickConsultationServiceImpl.quickConsultNurseDataInsert(jsnOBJ, authorization);

		Map<String, String> expectedResponse = new HashMap<>();
		expectedResponse.put("visitCode", "1");
		expectedResponse.put("response", "Data saved successfully");

		assertEquals(new Gson().toJson(expectedResponse), response);
	}

	@Test
	public void testQuickConsultNurseDataInsert_InvalidInput() {
		Exception exception = assertThrows(Exception.class, () -> {
			quickConsultationServiceImpl.quickConsultNurseDataInsert(null, authorization);
		});

		assertEquals("Invalid input", exception.getMessage());
	}

	@Test
	public void testQuickConsultNurseDataInsert_AlreadySavedData() throws Exception {
		CommonUtilityClass nurseUtilityClass = new CommonUtilityClass();
		nurseUtilityClass.setSessionID(1L);
		nurseUtilityClass.setVanID(1L);
		nurseUtilityClass.setBenFlowID(1L);

		BeneficiaryVisitDetail benVisitDetailsOBJ = new BeneficiaryVisitDetail();
		benVisitDetailsOBJ.setBeneficiaryRegID(1L);
		benVisitDetailsOBJ.setVisitReason("reason");
		benVisitDetailsOBJ.setVisitCategory("category");

		when(InputMapper.gson().fromJson(any(JsonObject.class), eq(CommonUtilityClass.class)))
				.thenReturn(nurseUtilityClass);
		when(InputMapper.gson().fromJson(any(JsonObject.class), eq(BeneficiaryVisitDetail.class)))
				.thenReturn(benVisitDetailsOBJ);
		when(commonNurseServiceImpl.getMaxCurrentdate(anyLong(), anyString(), anyString())).thenReturn(1);

		String response = quickConsultationServiceImpl.quickConsultNurseDataInsert(jsnOBJ, authorization);

		Map<String, String> expectedResponse = new HashMap<>();
		expectedResponse.put("response", "Data already saved");

		assertEquals(new Gson().toJson(expectedResponse), response);
	}

	@Test
	public void testDeleteVisitDetails_ValidInput() throws Exception {
		CommonUtilityClass nurseUtilityClass = InputMapper.gson().fromJson(validRequestObj, CommonUtilityClass.class);
		when(benVisitDetailRepo.getVisitCode(nurseUtilityClass.getBeneficiaryRegID(),
				nurseUtilityClass.getProviderServiceMapID())).thenReturn(1L);

		quickConsultationServiceImpl.deleteVisitDetails(validRequestObj);

		verify(benChiefComplaintRepo, times(1)).deleteVisitDetails(1L);
		verify(benAdherenceRepo, times(1)).deleteVisitDetails(1L);
		verify(cdssRepo, times(1)).deleteVisitDetails(1L);
		verify(benVisitDetailRepo, times(1)).deleteVisitDetails(1L);
	}

	@Test
	public void testDeleteVisitDetails_InvalidInput() {
		Exception exception = assertThrows(Exception.class, () -> {
			quickConsultationServiceImpl.deleteVisitDetails(invalidRequestObj);
		});

		String expectedMessage = "Invalid input";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void testDeleteVisitDetails_VisitDetailsNotFound() throws Exception {
		CommonUtilityClass nurseUtilityClass = InputMapper.gson().fromJson(validRequestObj, CommonUtilityClass.class);
		when(benVisitDetailRepo.getVisitCode(nurseUtilityClass.getBeneficiaryRegID(),
				nurseUtilityClass.getProviderServiceMapID())).thenReturn(null);

		quickConsultationServiceImpl.deleteVisitDetails(validRequestObj);

		verify(benChiefComplaintRepo, times(0)).deleteVisitDetails(anyLong());
		verify(benAdherenceRepo, times(0)).deleteVisitDetails(anyLong());
		verify(cdssRepo, times(0)).deleteVisitDetails(anyLong());
		verify(benVisitDetailRepo, times(0)).deleteVisitDetails(anyLong());
	}

	@Test
	public void testUpdateBenStatusFlagAfterNurseSaveSuccess_NoSpecialist() {
		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(benFlowID,
				benVisitDetailsOBJ.getBeneficiaryRegID(), benVisitID, benVisitDetailsOBJ.getVisitReason(),
				benVisitDetailsOBJ.getVisitCategory(), (short) 9, (short) 1, (short) 0, (short) 0, (short) 0,
				benVisitCode, vanID, (short) 0, null, null)).thenReturn(1);

		int result = quickConsultationServiceImpl.updateBenStatusFlagAfterNurseSaveSuccess(benVisitDetailsOBJ,
				benVisitID, benFlowID, benVisitCode, vanID, tcRequestOBJ);

		assertEquals(1, result);
		verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseActivity(benFlowID,
				benVisitDetailsOBJ.getBeneficiaryRegID(), benVisitID, benVisitDetailsOBJ.getVisitReason(),
				benVisitDetailsOBJ.getVisitCategory(), (short) 9, (short) 1, (short) 0, (short) 0, (short) 0,
				benVisitCode, vanID, (short) 0, null, null);
	}

	@Test
	public void testUpdateBenStatusFlagAfterNurseSaveSuccess_WithSpecialist() {
		tcRequestOBJ.setUserID(1);
		tcRequestOBJ.setAllocationDate(new Timestamp(System.currentTimeMillis()));

		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(benFlowID,
				benVisitDetailsOBJ.getBeneficiaryRegID(), benVisitID, benVisitDetailsOBJ.getVisitReason(),
				benVisitDetailsOBJ.getVisitCategory(), (short) 9, (short) 1, (short) 0, (short) 0, (short) 0,
				benVisitCode, vanID, (short) 1, tcRequestOBJ.getAllocationDate(), tcRequestOBJ.getUserID()))
				.thenReturn(1);

		int result = quickConsultationServiceImpl.updateBenStatusFlagAfterNurseSaveSuccess(benVisitDetailsOBJ,
				benVisitID, benFlowID, benVisitCode, vanID, tcRequestOBJ);

		assertEquals(1, result);
		verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseActivity(benFlowID,
				benVisitDetailsOBJ.getBeneficiaryRegID(), benVisitID, benVisitDetailsOBJ.getVisitReason(),
				benVisitDetailsOBJ.getVisitCategory(), (short) 9, (short) 1, (short) 0, (short) 0, (short) 0,
				benVisitCode, vanID, (short) 1, tcRequestOBJ.getAllocationDate(), tcRequestOBJ.getUserID());
	}

	@Test
	public void testUpdateBenStatusFlagAfterNurseSaveSuccess_Failure() {
		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(benFlowID,
				benVisitDetailsOBJ.getBeneficiaryRegID(), benVisitID, benVisitDetailsOBJ.getVisitReason(),
				benVisitDetailsOBJ.getVisitCategory(), (short) 9, (short) 1, (short) 0, (short) 0, (short) 0,
				benVisitCode, vanID, (short) 0, null, null)).thenReturn(0);

		int result = quickConsultationServiceImpl.updateBenStatusFlagAfterNurseSaveSuccess(benVisitDetailsOBJ,
				benVisitID, benFlowID, benVisitCode, vanID, tcRequestOBJ);

		assertEquals(0, result);
		verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseActivity(benFlowID,
				benVisitDetailsOBJ.getBeneficiaryRegID(), benVisitID, benVisitDetailsOBJ.getVisitReason(),
				benVisitDetailsOBJ.getVisitCategory(), (short) 9, (short) 1, (short) 0, (short) 0, (short) 0,
				benVisitCode, vanID, (short) 0, null, null);
	}

	@Test
	public void testQuickConsultDoctorDataInsert_Success() throws Exception {
		// Mocking the input JSON object
		quickConsultDoctorOBJ.add("visitDetails", new JsonObject());
		quickConsultDoctorOBJ.add("vitalsDetails", new JsonObject());
		quickConsultDoctorOBJ.add("prescription", new JsonArray());
		quickConsultDoctorOBJ.add("labTestOrders", new JsonArray());

		// Mocking the behavior of dependencies
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(), eq(authorization))).thenReturn(null);
		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any(), any())).thenReturn(1L);
		when(commonNurseServiceImpl.generateVisitCode(anyLong(), anyInt(), anyInt())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(any())).thenReturn(1L);
		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(), anyLong(), any(),
				any(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(), anyInt(), anyShort(),
				any(), any())).thenReturn(1);
		when(sMSGatewayServiceImpl.smsSenderGateway(anyString(), anyLong(), anyInt(), anyLong(), any(), any(),
				anyString(), any(), eq(authorization))).thenReturn(1);

		// Calling the method under test
		String response = quickConsultationServiceImpl.quickConsultDoctorDataInsert(quickConsultDoctorOBJ,
				authorization);

		// Asserting the response
		assertNotNull(response);
		assertTrue(response.contains("Data saved successfully"));

		// Verifying interactions with mocks
		verify(commonNurseServiceImpl, times(1)).saveBeneficiaryVisitDetails(any(), any());
		verify(commonNurseServiceImpl, times(1)).generateVisitCode(anyLong(), anyInt(), anyInt());
		verify(commonNurseServiceImpl, times(1)).saveBeneficiaryPhysicalAnthropometryDetails(any());
		verify(commonNurseServiceImpl, times(1)).saveBeneficiaryPhysicalVitalDetails(any());
		verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(),
				anyLong(), any(), any(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(),
				anyInt(), anyShort(), any(), any());
		verify(sMSGatewayServiceImpl, times(1)).smsSenderGateway(anyString(), anyLong(), anyInt(), anyLong(), any(),
				any(), anyString(), any(), eq(authorization));
	}

	@Test
	public void testQuickConsultDoctorDataInsert_InvalidInput() {
		// Calling the method under test with invalid input
		Exception exception = assertThrows(Exception.class, () -> {
			quickConsultationServiceImpl.quickConsultDoctorDataInsert(null, authorization);
		});

		// Asserting the exception message
		assertEquals("Invalid input", exception.getMessage());
	}

	@Test
	public void testQuickConsultDoctorDataInsert_DataAlreadySaved() throws Exception {
		// Mocking the input JSON object
		quickConsultDoctorOBJ.add("visitDetails", new JsonObject());
		quickConsultDoctorOBJ.add("vitalsDetails", new JsonObject());
		quickConsultDoctorOBJ.add("prescription", new JsonArray());
		quickConsultDoctorOBJ.add("labTestOrders", new JsonArray());

		// Mocking the behavior of dependencies
		when(commonNurseServiceImpl.getMaxCurrentdate(anyLong(), any(), any())).thenReturn(1);

		// Calling the method under test
		String response = quickConsultationServiceImpl.quickConsultDoctorDataInsert(quickConsultDoctorOBJ,
				authorization);

		// Asserting the response
		assertNotNull(response);
		assertTrue(response.contains("Data already saved"));

		// Verifying interactions with mocks
		verify(commonNurseServiceImpl, times(1)).getMaxCurrentdate(anyLong(), any(), any());
	}

	@Test
	public void testQuickConsultDoctorDataInsert_SaveError() throws Exception {
		// Mocking the input JSON object
		quickConsultDoctorOBJ.add("visitDetails", new JsonObject());
		quickConsultDoctorOBJ.add("vitalsDetails", new JsonObject());
		quickConsultDoctorOBJ.add("prescription", new JsonArray());
		quickConsultDoctorOBJ.add("labTestOrders", new JsonArray());

		// Mocking the behavior of dependencies
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(), eq(authorization))).thenReturn(null);
		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any(), any())).thenReturn(1L);
		when(commonNurseServiceImpl.generateVisitCode(anyLong(), anyInt(), anyInt())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(any())).thenReturn(1L);
		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(), anyLong(), any(),
				any(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(), anyInt(), anyShort(),
				any(), any())).thenReturn(0);

		// Calling the method under test
		Exception exception = assertThrows(RuntimeException.class, () -> {
			quickConsultationServiceImpl.quickConsultDoctorDataInsert(quickConsultDoctorOBJ, authorization);
		});

		// Asserting the exception message
		assertEquals("Error occurred while saving data. Beneficiary status update failed", exception.getMessage());

		// Verifying interactions with mocks
		verify(commonNurseServiceImpl, times(1)).saveBeneficiaryVisitDetails(any(), any());
		verify(commonNurseServiceImpl, times(1)).generateVisitCode(anyLong(), anyInt(), anyInt());
		verify(commonNurseServiceImpl, times(1)).saveBeneficiaryPhysicalAnthropometryDetails(any());
		verify(commonNurseServiceImpl, times(1)).saveBeneficiaryPhysicalVitalDetails(any());
		verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseActivity(anyLong(), anyLong(),
				anyLong(), any(), any(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(),
				anyInt(), anyShort(), any(), any());
	}

	@Test
	public void testGetBenDataFrmNurseToDocVisitDetailsScreen() {
		when(commonNurseServiceImpl.getCSVisitDetails(1L, 1L)).thenReturn(beneficiaryVisitDetail);
		when(commonNurseServiceImpl.getCdssDetails(1L, 1L)).thenReturn(cdss);

		String result = quickConsultationServiceImpl.getBenDataFrmNurseToDocVisitDetailsScreen(1L, 1L);

		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("benVisitDetails", beneficiaryVisitDetail);
		expectedMap.put("cdss", cdss);

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String expectedJson = gson.toJson(expectedMap);

		assertEquals(expectedJson, result);

		verify(commonNurseServiceImpl, times(1)).getCSVisitDetails(1L, 1L);
		verify(commonNurseServiceImpl, times(1)).getCdssDetails(1L, 1L);
	}

	@Test
	public void testGetBeneficiaryVitalDetails() {
		BenAnthropometryDetail benAnthropometryDetail = new BenAnthropometryDetail();
		BenPhysicalVitalDetail benPhysicalVitalDetail = new BenPhysicalVitalDetail();

		when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, visitCode))
				.thenReturn(benAnthropometryDetail);
		when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode))
				.thenReturn(benPhysicalVitalDetail);

		String result = quickConsultationServiceImpl.getBeneficiaryVitalDetails(beneficiaryRegID, visitCode);

		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("benAnthropometryDetail", benAnthropometryDetail);
		expectedMap.put("benPhysicalVitalDetail", benPhysicalVitalDetail);

		assertEquals(expectedMap.toString(), result);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID,
				visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetBeneficiaryCdssDetails() {
		Map<String, Object> expectedCdssDetails = new HashMap<>();
		expectedCdssDetails.put("presentChiefComplaint", "Chief Complaint Details");
		expectedCdssDetails.put("diseaseSummary", "Disease Summary Details");

		when(commonNurseServiceImpl.getBenCdssDetails(beneficiaryRegID, benVisitID))
				.thenReturn("Chief Complaint Details", "Disease Summary Details");

		String response = quickConsultationServiceImpl.getBeneficiaryCdssDetails(beneficiaryRegID, benVisitID);

		assertTrue(response.contains("presentChiefComplaint"));
		assertTrue(response.contains("Chief Complaint Details"));
		assertTrue(response.contains("diseaseSummary"));
		assertTrue(response.contains("Disease Summary Details"));

		verify(commonNurseServiceImpl, times(2)).getBenCdssDetails(beneficiaryRegID, benVisitID);
	}

	@Test
	public void testGetBenQuickConsultNurseData() {
		// Mocking the dependencies
		BenAnthropometryDetail benAnthropometryDetail = new BenAnthropometryDetail();
		BenPhysicalVitalDetail benPhysicalVitalDetail = new BenPhysicalVitalDetail();
		CDSS cdss = new CDSS();

		when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(benRegID, visitCode))
				.thenReturn(benAnthropometryDetail);
		when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(benRegID, visitCode))
				.thenReturn(benPhysicalVitalDetail);
		when(commonNurseServiceImpl.getBenCdssDetails(benRegID, visitCode)).thenReturn(cdss);

		// Call the method under test
		String result = quickConsultationServiceImpl.getBenQuickConsultNurseData(benRegID, visitCode);

		// Verify the interactions
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalVitalDetails(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenCdssDetails(benRegID, visitCode);

		// Assert the expected results
		assertNotNull(result);
		assertTrue(result.contains("vitals"));
		assertTrue(result.contains("cdss"));
	}

	@Test
	public void testGetBenCaseRecordFromDoctorQuickConsult() {
		// Mocking the responses
		when(commonDoctorServiceImpl.getFindingsDetails(benRegID, visitCode)).thenReturn("Findings");
		when(generalOPDDoctorServiceImpl.getGeneralOPDDiagnosisDetails(benRegID, visitCode)).thenReturn("Diagnosis");
		when(commonDoctorServiceImpl.getInvestigationDetails(benRegID, visitCode)).thenReturn("Investigation");
		when(commonDoctorServiceImpl.getPrescribedDrugs(benRegID, visitCode)).thenReturn("Prescription");
		when(commonDoctorServiceImpl.getReferralDetails(benRegID, visitCode, false)).thenReturn("Refer");
		when(labTechnicianServiceImpl.getLabResultDataForBen(benRegID, visitCode)).thenReturn("LabReport");
		when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(benRegID, visitCode))
				.thenReturn("ArchivedVisitcodeForLabResult");

		// Call the method
		String response = quickConsultationServiceImpl.getBenCaseRecordFromDoctorQuickConsult(benRegID, visitCode);

		// Verify the interactions
		verify(commonDoctorServiceImpl, times(1)).getFindingsDetails(benRegID, visitCode);
		verify(generalOPDDoctorServiceImpl, times(1)).getGeneralOPDDiagnosisDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getInvestigationDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getPrescribedDrugs(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getReferralDetails(benRegID, visitCode, false);
		verify(labTechnicianServiceImpl, times(1)).getLabResultDataForBen(benRegID, visitCode);
		verify(labTechnicianServiceImpl, times(1)).getLast_3_ArchivedTestVisitList(benRegID, visitCode);

		// Expected response map
		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("findings", "Findings");
		expectedMap.put("diagnosis", "Diagnosis");
		expectedMap.put("investigation", "Investigation");
		expectedMap.put("prescription", "Prescription");
		expectedMap.put("Refer", "Refer");
		expectedMap.put("LabReport", new Gson().toJson("LabReport"));
		expectedMap.put("ArchivedVisitcodeForLabResult", "ArchivedVisitcodeForLabResult");

		// Assert the response
		assertEquals(expectedMap.toString(), response);
	}

	@Test
	public void testUpdateGeneralOPDQCDoctorData_Success() throws Exception {
		// Setup mock responses
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(), eq(authorization))).thenReturn(null);
		when(commonNurseServiceImpl.saveBeneficiaryPrescription(any(JsonObject.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenPrescribedDrugsList(anyList())).thenReturn(1);
		when(commonNurseServiceImpl.saveBeneficiaryLabTestOrderDetails(any(JsonObject.class), anyLong()))
				.thenReturn(1L);
		when(benPhysicalVitalRepo.updatePhysicalVitalDetailsQCDoctor(anyString(), anyString(), anyLong(), anyLong()))
				.thenReturn(1);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(any(), anyBoolean(), anyBoolean(), any()))
				.thenReturn(1);

		// Call the method
		Long result = quickConsultationServiceImpl.updateGeneralOPDQCDoctorData(quickConsultDoctorOBJ, authorization);

		// Verify the result
		assertNotNull(result);
		assertEquals(1L, result);
	}

	@Test
	public void testUpdateGeneralOPDQCDoctorData_BenChiefComplaintIDNull() throws Exception {
		// Setup mock responses
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(), eq(authorization))).thenReturn(null);
		when(commonNurseServiceImpl.saveBeneficiaryPrescription(any(JsonObject.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenPrescribedDrugsList(anyList())).thenReturn(1);
		when(commonNurseServiceImpl.saveBeneficiaryLabTestOrderDetails(any(JsonObject.class), anyLong()))
				.thenReturn(1L);
		when(benPhysicalVitalRepo.updatePhysicalVitalDetailsQCDoctor(anyString(), anyString(), anyLong(), anyLong()))
				.thenReturn(1);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(any(), anyBoolean(), anyBoolean(), any()))
				.thenReturn(1);

		// Call the method
		Long result = quickConsultationServiceImpl.updateGeneralOPDQCDoctorData(quickConsultDoctorOBJ, authorization);

		// Verify the result
		assertNull(result);
	}

	@Test
	public void testUpdateGeneralOPDQCDoctorData_ClinicalObservationIDNull() throws Exception {
		// Setup mock responses
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(), eq(authorization))).thenReturn(null);
		when(commonNurseServiceImpl.saveBeneficiaryPrescription(any(JsonObject.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenPrescribedDrugsList(anyList())).thenReturn(1);
		when(commonNurseServiceImpl.saveBeneficiaryLabTestOrderDetails(any(JsonObject.class), anyLong()))
				.thenReturn(1L);
		when(benPhysicalVitalRepo.updatePhysicalVitalDetailsQCDoctor(anyString(), anyString(), anyLong(), anyLong()))
				.thenReturn(1);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(any(), anyBoolean(), anyBoolean(), any()))
				.thenReturn(1);

		// Call the method
		Long result = quickConsultationServiceImpl.updateGeneralOPDQCDoctorData(quickConsultDoctorOBJ, authorization);

		// Verify the result
		assertNull(result);
	}

	@Test
	public void testUpdateGeneralOPDQCDoctorData_PrescribedDrugSuccessFlagNull() throws Exception {
		// Setup mock responses
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(), eq(authorization))).thenReturn(null);
		when(commonNurseServiceImpl.saveBeneficiaryPrescription(any(JsonObject.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenPrescribedDrugsList(anyList())).thenReturn(1);
		when(commonNurseServiceImpl.saveBeneficiaryLabTestOrderDetails(any(JsonObject.class), anyLong()))
				.thenReturn(1L);
		when(benPhysicalVitalRepo.updatePhysicalVitalDetailsQCDoctor(anyString(), anyString(), anyLong(), anyLong()))
				.thenReturn(1);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(any(), anyBoolean(), anyBoolean(), any()))
				.thenReturn(1);

		// Call the method
		Long result = quickConsultationServiceImpl.updateGeneralOPDQCDoctorData(quickConsultDoctorOBJ, authorization);

		// Verify the result
		assertNull(result);
	}

	@Test
	public void testUpdateGeneralOPDQCDoctorData_LabTestOrderSuccessFlagNull() throws Exception {
		// Setup mock responses
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(), eq(authorization))).thenReturn(null);
		when(commonNurseServiceImpl.saveBeneficiaryPrescription(any(JsonObject.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenPrescribedDrugsList(anyList())).thenReturn(1);
		when(commonNurseServiceImpl.saveBeneficiaryLabTestOrderDetails(any(JsonObject.class), anyLong()))
				.thenReturn(1L);
		when(benPhysicalVitalRepo.updatePhysicalVitalDetailsQCDoctor(anyString(), anyString(), anyLong(), anyLong()))
				.thenReturn(1);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(any(), anyBoolean(), anyBoolean(), any()))
				.thenReturn(1);

		// Call the method
		Long result = quickConsultationServiceImpl.updateGeneralOPDQCDoctorData(quickConsultDoctorOBJ, authorization);

		// Verify the result
		assertNull(result);
	}

	@Test
	public void testUpdateGeneralOPDQCDoctorData_VitalsRBSTestFlagNull() throws Exception {
		// Setup mock responses
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(), eq(authorization))).thenReturn(null);
		when(commonNurseServiceImpl.saveBeneficiaryPrescription(any(JsonObject.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenPrescribedDrugsList(anyList())).thenReturn(1);
		when(commonNurseServiceImpl.saveBeneficiaryLabTestOrderDetails(any(JsonObject.class), anyLong()))
				.thenReturn(1L);
		when(benPhysicalVitalRepo.updatePhysicalVitalDetailsQCDoctor(anyString(), anyString(), anyLong(), anyLong()))
				.thenReturn(1);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(any(), anyBoolean(), anyBoolean(), any()))
				.thenReturn(1);

		// Call the method
		Long result = quickConsultationServiceImpl.updateGeneralOPDQCDoctorData(quickConsultDoctorOBJ, authorization);

		// Verify the result
		assertNull(result);
	}

	@Test
	public void testUpdateGeneralOPDQCDoctorData_ReferupdateSuccessFlagNull() throws Exception {
		// Setup mock responses
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(), eq(authorization))).thenReturn(null);
		when(commonNurseServiceImpl.saveBeneficiaryPrescription(any(JsonObject.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenPrescribedDrugsList(anyList())).thenReturn(1);
		when(commonNurseServiceImpl.saveBeneficiaryLabTestOrderDetails(any(JsonObject.class), anyLong()))
				.thenReturn(1L);
		when(benPhysicalVitalRepo.updatePhysicalVitalDetailsQCDoctor(anyString(), anyString(), anyLong(), anyLong()))
				.thenReturn(1);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(any(), anyBoolean(), anyBoolean(), any()))
				.thenReturn(1);

		// Call the method
		Long result = quickConsultationServiceImpl.updateGeneralOPDQCDoctorData(quickConsultDoctorOBJ, authorization);

		// Verify the result
		assertNull(result);
	}

	@Test
	public void testUpdateBeneficiaryClinicalObservations_Success() throws Exception {
		when(commonDoctorServiceImpl.getSnomedCTcode("headache")).thenReturn(new String[] { "12345", "Headache" });
		when(commonDoctorServiceImpl.updateBenClinicalObservations(any(BenClinicalObservations.class))).thenReturn(1);

		int result = quickConsultationServiceImpl.updateBeneficiaryClinicalObservations(caseSheet);

		assertEquals(1, result);
		verify(commonDoctorServiceImpl, times(1)).getSnomedCTcode("headache");
		verify(commonDoctorServiceImpl, times(1)).updateBenClinicalObservations(any(BenClinicalObservations.class));
	}

	@Test
	public void testUpdateBeneficiaryClinicalObservations_NullClinicalObservations() throws Exception {
		caseSheet = new JsonObject(); // Empty caseSheet

		int result = quickConsultationServiceImpl.updateBeneficiaryClinicalObservations(caseSheet);

		assertEquals(0, result);
		verify(commonDoctorServiceImpl, never()).getSnomedCTcode(anyString());
		verify(commonDoctorServiceImpl, never()).updateBenClinicalObservations(any(BenClinicalObservations.class));
	}

	@Test
	public void testUpdateBeneficiaryClinicalObservations_InvalidInput() throws Exception {
		caseSheet.addProperty("otherSymptoms", "invalid");

		when(commonDoctorServiceImpl.getSnomedCTcode("invalid")).thenReturn(null);

		int result = quickConsultationServiceImpl.updateBeneficiaryClinicalObservations(caseSheet);

		assertEquals(0, result);
		verify(commonDoctorServiceImpl, times(1)).getSnomedCTcode("invalid");
		verify(commonDoctorServiceImpl, never()).updateBenClinicalObservations(any(BenClinicalObservations.class));
	}
}
