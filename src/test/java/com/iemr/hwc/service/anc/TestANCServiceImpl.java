
package com.iemr.hwc.service.anc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iemr.hwc.controller.anc.AntenatalCareController;
import com.iemr.hwc.data.anc.ANCCareDetails;
import com.iemr.hwc.data.anc.HrpStatusAndReasonsRequestModel;
import com.iemr.hwc.data.anc.SysObstetricExamination;
import com.iemr.hwc.data.anc.WrapperAncImmunization;
import com.iemr.hwc.data.foetalmonitor.FoetalMonitor;
import com.iemr.hwc.data.nurse.BenAnthropometryDetail;
import com.iemr.hwc.data.nurse.BenPhysicalVitalDetail;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.foetalmonitor.FoetalMonitorRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.nurse.CDSSRepo;
import com.iemr.hwc.repo.nurse.anc.BenAdherenceRepo;
import com.iemr.hwc.repo.nurse.anc.BenMenstrualDetailsRepo;
import com.iemr.hwc.repo.nurse.anc.SysObstetricExaminationRepo;
import com.iemr.hwc.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonServiceImpl;
import com.iemr.hwc.service.labtechnician.LabTechnicianServiceImpl;
import com.iemr.hwc.service.tele_consultation.SMSGatewayServiceImpl;
import com.iemr.hwc.utils.exception.IEMRException;

import ca.uhn.fhir.parser.JsonParser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestANCServiceImpl {

	@Mock
	private CommonServiceImpl commonServiceImpl;

	@Mock
	private SMSGatewayServiceImpl sMSGatewayServiceImpl;
	@Mock
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;

	@Mock
	private FoetalMonitorRepo foetalMonitorRepo;
	@Mock
	private CommonNurseServiceImpl commonNurseServiceImpl;
	@Mock
	private ANCDoctorServiceImpl ancDoctorServiceImpl;

	@Mock
	private BenVisitDetailRepo benVisitDetailRepo;

	@Mock
	private BenChiefComplaintRepo benChiefComplaintRepo;

	@Mock
	private BenAdherenceRepo benAdherenceRepo;

	@Mock
	private CDSSRepo cdssRepo;
	@Mock
	private ANCNurseServiceImpl ancNurseServiceImpl;
	@Mock
	private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;
	@Mock
	private BenMenstrualDetailsRepo benMenstrualDetailsRepo;
	@Mock
	ANCService ancService;
	@Mock
	private CommonDoctorServiceImpl commonDoctorServiceImpl;

	@Mock
	private LabTechnicianServiceImpl labTechnicianServiceImpl;
	@Mock
	private SysObstetricExaminationRepo sysObstetricExaminationRepo;

	@InjectMocks
	private ANCServiceImpl ancServiceImpl;
	private JsonObject investigationDataCheck;
	private JsonObject tmpOBJ;
	private Long benVisitID;
	private Long benFlowID;
	private Long visitCode;
	private Integer vanID;
	private TeleconsultationRequestOBJ tcRequestOBJ;
	private JsonObject requestObj;
	private String authorization;
	private JsonObject visitDetailsOBJ;
	private CommonUtilityClass nurseUtilityClass;
	private JsonObject ancHistoryOBJ;
	private Long benVisitCode;
	private JsonObject validVitalDetailsOBJ;
	private JsonObject examinationDetailsOBJ;
	private Gson gson;
	private Long benRegID;
	private Long beneficiaryRegID;
	private JsonObject ancDetailsOBJ;
	private JsonObject ancHistoryJson;
	private JsonObject validVitalDetails;
	private JsonObject partialAnthropometryDetails;
	private JsonObject partialPhysicalVitalDetails;
	private AntenatalCareController antenatalCareController;
	private Long beneficiaryRegId;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testSaveANCNurseData_Positive() throws Exception {
		// Arrange
		JsonObject requestOBJ = new JsonObject();
		JsonObject visitDetails = new JsonObject();
		visitDetails.addProperty("visitID", 1L);
		visitDetails.addProperty("visitCode", 123L);
		requestOBJ.add("visitDetails", visitDetails);

		when(commonServiceImpl.createTcRequest(any(), any(), anyString())).thenReturn(new TeleconsultationRequestOBJ());
		when(ancServiceImpl.saveBenVisitDetails(any(), any())).thenReturn(Map.of("visitID", 1L, "visitCode", 123L));
		when(ancServiceImpl.saveBenANCDetails(any(), anyLong(), anyLong())).thenReturn(1L);
		when(ancServiceImpl.saveBenANCHistoryDetails(any(), anyLong(), anyLong())).thenReturn(1L);
		when(ancServiceImpl.saveBenANCVitalDetails(any(), anyLong(), anyLong())).thenReturn(1L);
		when(ancServiceImpl.saveBenANCExaminationDetails(any(), anyLong(), anyLong())).thenReturn(1L);
		when(ancServiceImpl.updateBenFlowNurseAfterNurseActivityANC(any(), any(), anyLong(), anyLong(), anyLong(),
				any(), any())).thenReturn(1);

		// Act
		String result = ancServiceImpl.saveANCNurseData(requestOBJ, "Authorization");

		// Assert
		Map<String, String> expectedResponse = new HashMap<>();
		expectedResponse.put("visitCode", "123");
		expectedResponse.put("response", "Data saved successfully");
		assertEquals(new Gson().toJson(expectedResponse), result);
	}

	@Test
	public void testSaveANCNurseData_Negative_InvalidInput() {
		// Arrange
		JsonObject requestOBJ = new JsonObject();

		// Act & Assert
		Exception exception = assertThrows(Exception.class, () -> {
			ancServiceImpl.saveANCNurseData(requestOBJ, "Authorization");
		});

		assertEquals("Invalid input", exception.getMessage());
	}

	@Test
	public void testSaveANCNurseData_Negative_SaveFailure() throws Exception {
		// Arrange
		JsonObject requestOBJ = new JsonObject();
		JsonObject visitDetails = new JsonObject();
		visitDetails.addProperty("visitID", 1L);
		visitDetails.addProperty("visitCode", 123L);
		requestOBJ.add("visitDetails", visitDetails);

		when(commonServiceImpl.createTcRequest(any(), any(), anyString())).thenReturn(new TeleconsultationRequestOBJ());
		when(ancServiceImpl.saveBenVisitDetails(any(), any())).thenReturn(Map.of("visitID", 1L, "visitCode", 123L));
		when(ancServiceImpl.saveBenANCDetails(any(), anyLong(), anyLong())).thenReturn(0L);

		// Act & Assert
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			ancServiceImpl.saveANCNurseData(requestOBJ, "Authorization");
		});

		assertEquals("Error occurred while saving data", exception.getMessage());
	}

	@Test
	public void testUpdateBenFlowNurseAfterNurseActivityANC_NoInvestigations() {
		tmpOBJ.addProperty("beneficiaryRegID", 1L);
		tmpOBJ.addProperty("visitReason", "reason");
		tmpOBJ.addProperty("visitCategory", "category");

		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivityANC(anyLong(), anyLong(), anyLong(),
				anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(),
				anyInt(), anyShort(), any(Timestamp.class), anyInt(), anyShort())).thenReturn(1);

		int result = ancServiceImpl.updateBenFlowNurseAfterNurseActivityANC(investigationDataCheck, tmpOBJ, benVisitID,
				benFlowID, visitCode, vanID, tcRequestOBJ);

		assertEquals(1, result);
		verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseActivityANC(anyLong(), anyLong(),
				anyLong(), anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(),
				anyLong(), anyInt(), anyShort(), any(Timestamp.class), anyInt(), anyShort());
	}

	@Test
	public void testUpdateBenFlowNurseAfterNurseActivityANC_WithInvestigations() {
		JsonObject laboratoryList = new JsonObject();
		investigationDataCheck.add("laboratoryList", laboratoryList);
		tmpOBJ.addProperty("beneficiaryRegID", 1L);
		tmpOBJ.addProperty("visitReason", "reason");
		tmpOBJ.addProperty("visitCategory", "category");

		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivityANC(anyLong(), anyLong(), anyLong(),
				anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(),
				anyInt(), anyShort(), any(Timestamp.class), anyInt(), anyShort())).thenReturn(1);

		int result = ancServiceImpl.updateBenFlowNurseAfterNurseActivityANC(investigationDataCheck, tmpOBJ, benVisitID,
				benFlowID, visitCode, vanID, tcRequestOBJ);

		assertEquals(1, result);
		verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseActivityANC(anyLong(), anyLong(),
				anyLong(), anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(),
				anyLong(), anyInt(), anyShort(), any(Timestamp.class), anyInt(), anyShort());
	}

	@Test
	public void testUpdateBenFlowNurseAfterNurseActivityANC_WithTeleconsultation() {
		tmpOBJ.addProperty("beneficiaryRegID", 1L);
		tmpOBJ.addProperty("visitReason", "reason");
		tmpOBJ.addProperty("visitCategory", "category");
		tcRequestOBJ.setUserID(1);
		tcRequestOBJ.setAllocationDate(new Timestamp(System.currentTimeMillis()));

		when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivityANC(anyLong(), anyLong(), anyLong(),
				anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(),
				anyInt(), anyShort(), any(Timestamp.class), anyInt(), anyShort())).thenReturn(1);

		int result = ancServiceImpl.updateBenFlowNurseAfterNurseActivityANC(investigationDataCheck, tmpOBJ, benVisitID,
				benFlowID, visitCode, vanID, tcRequestOBJ);

		assertEquals(1, result);
		verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseActivityANC(anyLong(), anyLong(),
				anyLong(), anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(),
				anyLong(), anyInt(), anyShort(), any(Timestamp.class), anyInt(), anyShort());
	}

	@Test
    public void testUpdateBenFlowNurseAfterNurseActivityANC_WithFoetalMonitorData() {
        tmpOBJ.addProperty("beneficiaryRegID", 1L);
        tmpOBJ.addProperty("visitReason", "reason");
        tmpOBJ.addProperty("visitCategory", "category");

        ArrayList<FoetalMonitor> foetalMonitorData = new ArrayList<>();
        FoetalMonitor foetalMonitor = new FoetalMonitor();
        foetalMonitor.setResultState(false);
        foetalMonitorData.add(foetalMonitor);

        when(foetalMonitorRepo.getFoetalMonitorDetailsByFlowId(benFlowID)).thenReturn(foetalMonitorData);
        when(commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivityANC(anyLong(), anyLong(), anyLong(), anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(), anyInt(), anyShort(), any(Timestamp.class), anyInt(), anyShort())).thenReturn(1);

        int result = ancServiceImpl.updateBenFlowNurseAfterNurseActivityANC(investigationDataCheck, tmpOBJ, benVisitID, benFlowID, visitCode, vanID, tcRequestOBJ);

        assertEquals(1, result);
        verify(foetalMonitorRepo, times(1)).getFoetalMonitorDetailsByFlowId(benFlowID);
        verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowNurseAfterNurseActivityANC(anyLong(), anyLong(), anyLong(), anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(), anyInt(), anyShort(), any(Timestamp.class), anyInt(), anyShort());

        @Test
        public void testSaveANCDoctorData_Success() throws Exception {
            // Mocking dependencies
            when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
                .thenReturn(new TeleconsultationRequestOBJ());
            when(commonNurseServiceImpl.savePrescriptionDetailsAndGetPrescriptionID(anyLong(), anyLong(), anyLong(), anyString(), any(), anyLong(), anyInt(), anyInt(), anyString(), anyString(), anyString()))
                .thenReturn(1L);
            when(commonNurseServiceImpl.saveBenInvestigation(any())).thenReturn(1L);
            when(commonNurseServiceImpl.saveBenPrescribedDrugsList(any())).thenReturn(1);
            when(ancDoctorServiceImpl.saveBenANCDiagnosis(any(JsonObject.class), anyLong())).thenReturn(1L);
            when(commonDoctorServiceImpl.saveBenReferDetails(any(JsonObject.class), eq(false))).thenReturn(1L);
            when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataSave(any(CommonUtilityClass.class), eq(true), eq(true), any(TeleconsultationRequestOBJ.class)))
                .thenReturn(1);

            // Call the method
            Long result = ancServiceImpl.saveANCDoctorData(requestObj, authorization);

            // Assertions
            assertNotNull(result);
            assertEquals(1L, result);
            verify(commonServiceImpl, times(1)).createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization));
            verify(commonNurseServiceImpl, times(1)).savePrescriptionDetailsAndGetPrescriptionID(anyLong(), anyLong(), anyLong(), anyString(), any(), anyLong(), anyInt(), anyInt(), anyString(), anyString(), anyString());
            verify(commonNurseServiceImpl, times(1)).saveBenInvestigation(any());
            verify(commonNurseServiceImpl, times(1)).saveBenPrescribedDrugsList(any());
            verify(ancDoctorServiceImpl, times(1)).saveBenANCDiagnosis(any(JsonObject.class), anyLong());
            verify(commonDoctorServiceImpl, times(1)).saveBenReferDetails(any(JsonObject.class), eq(false));
            verify(commonDoctorServiceImpl, times(1)).updateBenFlowtableAfterDocDataSave(any(CommonUtilityClass.class), eq(true), eq(true), any(TeleconsultationRequestOBJ.class));
        }

        @Test
        public void testSaveANCDoctorData_NullRequestObj() throws Exception {
            // Call the method with null requestObj
            Long result = ancServiceImpl.saveANCDoctorData(null, authorization);

            // Assertions
            assertNull(result);
        }

        @Test
        public void testSaveANCDoctorData_NoFindings() throws Exception {
            // Modify requestObj to not include findings
            requestObj.remove("findings");

            // Mocking dependencies
            when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
                .thenReturn(new TeleconsultationRequestOBJ());
            when(commonNurseServiceImpl.savePrescriptionDetailsAndGetPrescriptionID(anyLong(), anyLong(), anyLong(), anyString(), any(), anyLong(), anyInt(), anyInt(), anyString(), anyString(), anyString()))
                .thenReturn(1L);
            when(commonNurseServiceImpl.saveBenInvestigation(any())).thenReturn(1L);
            when(commonNurseServiceImpl.saveBenPrescribedDrugsList(any())).thenReturn(1);
            when(ancDoctorServiceImpl.saveBenANCDiagnosis(any(JsonObject.class), anyLong())).thenReturn(1L);
            when(commonDoctorServiceImpl.saveBenReferDetails(any(JsonObject.class), eq(false))).thenReturn(1L);
            when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataSave(any(CommonUtilityClass.class), eq(true), eq(true), any(TeleconsultationRequestOBJ.class)))
                .thenReturn(1);

            // Call the method
            Long result = ancServiceImpl.saveANCDoctorData(requestObj, authorization);

            // Assertions
            assertNotNull(result);
            assertEquals(1L, result);
            verify(commonServiceImpl, times(1)).createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization));
            verify(commonNurseServiceImpl, times(1)).savePrescriptionDetailsAndGetPrescriptionID(anyLong(), anyLong(), anyLong(), anyString(), any(), anyLong(), anyInt(), anyInt(), anyString(), anyString(), anyString());
            verify(commonNurseServiceImpl, times(1)).saveBenInvestigation(any());
            verify(commonNurseServiceImpl, times(1)).saveBenPrescribedDrugsList(any());
            verify(ancDoctorServiceImpl, times(1)).saveBenANCDiagnosis(any(JsonObject.class), anyLong());
            verify(commonDoctorServiceImpl, times(1)).saveBenReferDetails(any(JsonObject.class), eq(false));
            verify(commonDoctorServiceImpl, times(1)).updateBenFlowtableAfterDocDataSave(any(CommonUtilityClass.class), eq(true), eq(true), any(TeleconsultationRequestOBJ.class));
        }

       
    }

	@Test
	public void testSaveBenVisitDetails_NullVisitDetailsOBJ() throws Exception {
		Map<String, Long> result = ancServiceImpl.saveBenVisitDetails(null, nurseUtilityClass);
		assertTrue(result.isEmpty());
	}

	@Test
	public void testSaveBenVisitDetails_NoVisitDetails() throws Exception {
		visitDetailsOBJ.add("someOtherField", new JsonObject());
		Map<String, Long> result = ancServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass);
		assertTrue(result.isEmpty());
	}

	@Test
	public void testSaveBenVisitDetails_ValidVisitDetails() throws Exception {
		JsonObject visitDetails = new JsonObject();
		visitDetails.addProperty("visitReason", "reason");
		visitDetails.addProperty("visitCategory", "category");
		visitDetailsOBJ.add("visitDetails", visitDetails);

		when(commonNurseServiceImpl.getMaxCurrentdate(anyLong(), anyString(), anyString())).thenReturn(0);
		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any())).thenReturn(1L);
		when(commonNurseServiceImpl.generateVisitCode(anyLong(), anyInt(), anyInt())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenChiefComplaints(anyList())).thenReturn(1);
		when(commonNurseServiceImpl.saveBenAdherenceDetails(any())).thenReturn(1);
		when(commonNurseServiceImpl.saveBenInvestigationDetails(any())).thenReturn(1);

		Map<String, Long> result = ancServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass);

		assertNotNull(result);
		assertEquals(1L, result.get("visitID"));
		assertEquals(1L, result.get("visitCode"));

		verify(commonNurseServiceImpl, times(1)).saveBeneficiaryVisitDetails(any());
		verify(commonNurseServiceImpl, times(1)).generateVisitCode(anyLong(), anyInt(), anyInt());
		verify(commonNurseServiceImpl, times(1)).saveBenChiefComplaints(anyList());
		verify(commonNurseServiceImpl, times(1)).saveBenAdherenceDetails(any());
		verify(commonNurseServiceImpl, times(1)).saveBenInvestigationDetails(any());
	}

	@Test
	public void testSaveBenVisitDetails_InvalidNestedObjects() throws Exception {
		JsonObject visitDetails = new JsonObject();
		visitDetails.addProperty("visitReason", "reason");
		visitDetails.addProperty("visitCategory", "category");
		visitDetailsOBJ.add("visitDetails", visitDetails);

		when(commonNurseServiceImpl.getMaxCurrentdate(anyLong(), anyString(), anyString())).thenReturn(0);
		when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(any())).thenReturn(1L);
		when(commonNurseServiceImpl.generateVisitCode(anyLong(), anyInt(), anyInt())).thenReturn(1L);

		Map<String, Long> result = ancServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass);

		assertNotNull(result);
		assertEquals(1L, result.get("visitID"));
		assertEquals(1L, result.get("visitCode"));

		verify(commonNurseServiceImpl, times(1)).saveBeneficiaryVisitDetails(any());
		verify(commonNurseServiceImpl, times(1)).generateVisitCode(anyLong(), anyInt(), anyInt());
		verify(commonNurseServiceImpl, never()).saveBenChiefComplaints(anyList());
		verify(commonNurseServiceImpl, never()).saveBenAdherenceDetails(any());
		verify(commonNurseServiceImpl, never()).saveBenInvestigationDetails(any());
	}

	@Test
	public void testSaveBenANCDetails_withValidDetails() throws Exception {
		JsonObject ancDetailsOBJ = JsonParser.parseString(
				"{\"ancObstetricDetails\": {\"someKey\": \"someValue\"}, \"ancImmunization\": {\"anotherKey\": \"anotherValue\"}}")
				.getAsJsonObject();
		Long benVisitID = 1L;
		Long benVisitCode = 1L;

		when(ancNurseServiceImpl.saveBenAncCareDetails(any(ANCCareDetails.class))).thenReturn(1L);
		when(ancNurseServiceImpl.saveAncImmunizationDetails(any(WrapperAncImmunization.class))).thenReturn(1L);

		Long result = ancServiceImpl.saveBenANCDetails(ancDetailsOBJ, benVisitID, benVisitCode);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(ancNurseServiceImpl, times(1)).saveBenAncCareDetails(any(ANCCareDetails.class));
		verify(ancNurseServiceImpl, times(1)).saveAncImmunizationDetails(any(WrapperAncImmunization.class));
	}

	@Test
	public void testSaveBenANCDetails_withOnlyObstetricDetails() throws Exception {
		JsonObject ancDetailsOBJ = JsonParser.parseString("{\"ancObstetricDetails\": {\"someKey\": \"someValue\"}}")
				.getAsJsonObject();
		Long benVisitID = 1L;
		Long benVisitCode = 1L;

		when(ancNurseServiceImpl.saveBenAncCareDetails(any(ANCCareDetails.class))).thenReturn(1L);

		Long result = ancServiceImpl.saveBenANCDetails(ancDetailsOBJ, benVisitID, benVisitCode);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(ancNurseServiceImpl, times(1)).saveBenAncCareDetails(any(ANCCareDetails.class));
		verify(ancNurseServiceImpl, never()).saveAncImmunizationDetails(any(WrapperAncImmunization.class));
	}

	@Test
	public void testSaveBenANCDetails_withOnlyImmunizationDetails() throws Exception {
		JsonObject ancDetailsOBJ = JsonParser.parseString("{\"ancImmunization\": {\"anotherKey\": \"anotherValue\"}}")
				.getAsJsonObject();
		Long benVisitID = 1L;
		Long benVisitCode = 1L;

		when(ancNurseServiceImpl.saveAncImmunizationDetails(any(WrapperAncImmunization.class))).thenReturn(1L);

		Long result = ancServiceImpl.saveBenANCDetails(ancDetailsOBJ, benVisitID, benVisitCode);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(ancNurseServiceImpl, never()).saveBenAncCareDetails(any(ANCCareDetails.class));
		verify(ancNurseServiceImpl, times(1)).saveAncImmunizationDetails(any(WrapperAncImmunization.class));
	}

	@Test
	public void testSaveBenANCDetails_withNoDetails() throws Exception {
		JsonObject ancDetailsOBJ = new JsonObject();
		Long benVisitID = 1L;
		Long benVisitCode = 1L;

		Long result = ancServiceImpl.saveBenANCDetails(ancDetailsOBJ, benVisitID, benVisitCode);

		assertNull(result);
		verify(ancNurseServiceImpl, never()).saveBenAncCareDetails(any(ANCCareDetails.class));
		verify(ancNurseServiceImpl, never()).saveAncImmunizationDetails(any(WrapperAncImmunization.class));
	}

	@Test
	public void testSaveBenANCHistoryDetails_Success() throws Exception {
		// Setup
		ancHistoryOBJ.add("pastHistory", new JsonObject());
		ancHistoryOBJ.add("comorbidConditions", new JsonObject());
		ancHistoryOBJ.add("medicationHistory", new JsonObject());
		ancHistoryOBJ.add("personalHistory", new JsonObject());
		ancHistoryOBJ.add("familyHistory", new JsonObject());
		ancHistoryOBJ.add("menstrualHistory", new JsonObject());
		ancHistoryOBJ.add("femaleObstetricHistory", new JsonObject());
		ancHistoryOBJ.add("immunizationHistory", new JsonObject());
		ancHistoryOBJ.add("childVaccineDetails", new JsonObject());

		when(commonNurseServiceImpl.saveBenPastHistory(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenComorbidConditions(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenMedicationHistory(any())).thenReturn(1L);
		when(commonNurseServiceImpl.savePersonalHistory(any())).thenReturn(1);
		when(commonNurseServiceImpl.saveAllergyHistory(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenFamilyHistory(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenMenstrualHistory(any())).thenReturn(1);
		when(commonNurseServiceImpl.saveFemaleObstetricHistory(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveImmunizationHistory(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveChildOptionalVaccineDetail(any())).thenReturn(1L);

		// Execute
		Long result = ancServiceImpl.saveBenANCHistoryDetails(ancHistoryOBJ, benVisitID, benVisitCode);

		// Verify
		assertNotNull(result);
		assertEquals(1L, result);
		verify(commonNurseServiceImpl, times(1)).saveBenPastHistory(any());
		verify(commonNurseServiceImpl, times(1)).saveBenComorbidConditions(any());
		verify(commonNurseServiceImpl, times(1)).saveBenMedicationHistory(any());
		verify(commonNurseServiceImpl, times(1)).savePersonalHistory(any());
		verify(commonNurseServiceImpl, times(1)).saveAllergyHistory(any());
		verify(commonNurseServiceImpl, times(1)).saveBenFamilyHistory(any());
		verify(commonNurseServiceImpl, times(1)).saveBenMenstrualHistory(any());
		verify(commonNurseServiceImpl, times(1)).saveFemaleObstetricHistory(any());
		verify(commonNurseServiceImpl, times(1)).saveImmunizationHistory(any());
		verify(commonNurseServiceImpl, times(1)).saveChildOptionalVaccineDetail(any());
	}

	@Test
	public void testSaveBenANCHistoryDetails_NullInput() throws Exception {
		// Execute
		Long result = ancServiceImpl.saveBenANCHistoryDetails(null, benVisitID, benVisitCode);

		// Verify
		assertNull(result);
	}

	@Test
	public void testSaveBenANCHistoryDetails_MissingFields() throws Exception {
		// Setup
		ancHistoryOBJ.add("pastHistory", new JsonObject());

		when(commonNurseServiceImpl.saveBenPastHistory(any())).thenReturn(1L);

		// Execute
		Long result = ancServiceImpl.saveBenANCHistoryDetails(ancHistoryOBJ, benVisitID, benVisitCode);

		// Verify
		assertNotNull(result);
		assertEquals(1L, result);
		verify(commonNurseServiceImpl, times(1)).saveBenPastHistory(any());
		verify(commonNurseServiceImpl, times(0)).saveBenComorbidConditions(any());
		verify(commonNurseServiceImpl, times(0)).saveBenMedicationHistory(any());
		verify(commonNurseServiceImpl, times(0)).savePersonalHistory(any());
		verify(commonNurseServiceImpl, times(0)).saveAllergyHistory(any());
		verify(commonNurseServiceImpl, times(0)).saveBenFamilyHistory(any());
		verify(commonNurseServiceImpl, times(0)).saveBenMenstrualHistory(any());
		verify(commonNurseServiceImpl, times(0)).saveFemaleObstetricHistory(any());
		verify(commonNurseServiceImpl, times(0)).saveImmunizationHistory(any());
		verify(commonNurseServiceImpl, times(0)).saveChildOptionalVaccineDetail(any());
	}

	@Test
	public void testSaveBenANCVitalDetails_withValidData() throws Exception {
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(any())).thenReturn(1L);

		Long result = ancServiceImpl.saveBenANCVitalDetails(validVitalDetailsOBJ, benVisitID, benVisitCode);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(commonNurseServiceImpl, times(1)).saveBeneficiaryPhysicalAnthropometryDetails(any());
		verify(commonNurseServiceImpl, times(1)).saveBeneficiaryPhysicalVitalDetails(any());
	}

	@Test
	public void testSaveBenANCVitalDetails_withNullData() throws Exception {
		Long result = ancServiceImpl.saveBenANCVitalDetails(null, benVisitID, benVisitCode);

		assertNull(result);
		verify(commonNurseServiceImpl, times(0)).saveBeneficiaryPhysicalAnthropometryDetails(any());
		verify(commonNurseServiceImpl, times(0)).saveBeneficiaryPhysicalVitalDetails(any());
	}

	@Test
	public void testSaveBenANCVitalDetails_withMissingFields() throws Exception {
		JsonObject incompleteVitalDetailsOBJ = new JsonObject();
		incompleteVitalDetailsOBJ.addProperty("benVisitID", 1L);

		Long result = ancServiceImpl.saveBenANCVitalDetails(incompleteVitalDetailsOBJ, benVisitID, benVisitCode);

		assertNull(result);
		verify(commonNurseServiceImpl, times(0)).saveBeneficiaryPhysicalAnthropometryDetails(any());
		verify(commonNurseServiceImpl, times(0)).saveBeneficiaryPhysicalVitalDetails(any());
	}

	@Test
	public void testSaveBenANCExaminationDetails_AllDetailsProvided() throws Exception {
		// Setup mock responses
		when(commonNurseServiceImpl.savePhyGeneralExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.savePhyHeadToToeExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysCardiovascularExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysRespiratoryExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysCentralNervousExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysMusculoskeletalSystemExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysGenitourinarySystemExamination(any())).thenReturn(1L);
		when(ancNurseServiceImpl.saveSysObstetricExamination(any())).thenReturn(1L);

		// Add examination details to the JsonObject
		examinationDetailsOBJ.add("generalExamination", new JsonObject());
		examinationDetailsOBJ.add("headToToeExamination", new JsonObject());
		examinationDetailsOBJ.add("cardioVascularExamination", new JsonObject());
		examinationDetailsOBJ.add("respiratorySystemExamination", new JsonObject());
		examinationDetailsOBJ.add("centralNervousSystemExamination", new JsonObject());
		examinationDetailsOBJ.add("musculoskeletalSystemExamination", new JsonObject());
		examinationDetailsOBJ.add("genitoUrinarySystemExamination", new JsonObject());
		examinationDetailsOBJ.add("obstetricExamination", new JsonObject());

		Long result = ancServiceImpl.saveBenANCExaminationDetails(examinationDetailsOBJ, benVisitID, benVisitCode);

		assertNotNull(result);
		assertEquals(1L, result);

		verify(commonNurseServiceImpl, times(1)).savePhyGeneralExamination(any());
		verify(commonNurseServiceImpl, times(1)).savePhyHeadToToeExamination(any());
		verify(commonNurseServiceImpl, times(1)).saveSysCardiovascularExamination(any());
		verify(commonNurseServiceImpl, times(1)).saveSysRespiratoryExamination(any());
		verify(commonNurseServiceImpl, times(1)).saveSysCentralNervousExamination(any());
		verify(commonNurseServiceImpl, times(1)).saveSysMusculoskeletalSystemExamination(any());
		verify(commonNurseServiceImpl, times(1)).saveSysGenitourinarySystemExamination(any());
		verify(ancNurseServiceImpl, times(1)).saveSysObstetricExamination(any());
	}

	@Test
	public void testSaveBenANCExaminationDetails_SomeDetailsMissing() throws Exception {
		// Setup mock responses
		when(commonNurseServiceImpl.savePhyGeneralExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.savePhyHeadToToeExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysCardiovascularExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysRespiratoryExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysCentralNervousExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysMusculoskeletalSystemExamination(any())).thenReturn(1L);
		when(commonNurseServiceImpl.saveSysGenitourinarySystemExamination(any())).thenReturn(1L);

		// Add some examination details to the JsonObject
		examinationDetailsOBJ.add("generalExamination", new JsonObject());
		examinationDetailsOBJ.add("headToToeExamination", new JsonObject());
		examinationDetailsOBJ.add("cardioVascularExamination", new JsonObject());

		Long result = ancServiceImpl.saveBenANCExaminationDetails(examinationDetailsOBJ, benVisitID, benVisitCode);

		assertNull(result);

		verify(commonNurseServiceImpl, times(1)).savePhyGeneralExamination(any());
		verify(commonNurseServiceImpl, times(1)).savePhyHeadToToeExamination(any());
		verify(commonNurseServiceImpl, times(1)).saveSysCardiovascularExamination(any());
		verify(commonNurseServiceImpl, times(0)).saveSysRespiratoryExamination(any());
		verify(commonNurseServiceImpl, times(0)).saveSysCentralNervousExamination(any());
		verify(commonNurseServiceImpl, times(0)).saveSysMusculoskeletalSystemExamination(any());
		verify(commonNurseServiceImpl, times(0)).saveSysGenitourinarySystemExamination(any());
		verify(ancNurseServiceImpl, times(0)).saveSysObstetricExamination(any());
	}

	@Test
	public void testSaveBenANCExaminationDetails_NoDetailsProvided() throws Exception {
		Long result = ancServiceImpl.saveBenANCExaminationDetails(examinationDetailsOBJ, benVisitID, benVisitCode);

		assertNull(result);

		verify(commonNurseServiceImpl, times(0)).savePhyGeneralExamination(any());
		verify(commonNurseServiceImpl, times(0)).savePhyHeadToToeExamination(any());
		verify(commonNurseServiceImpl, times(0)).saveSysCardiovascularExamination(any());
		verify(commonNurseServiceImpl, times(0)).saveSysRespiratoryExamination(any());
		verify(commonNurseServiceImpl, times(0)).saveSysCentralNervousExamination(any());
		verify(commonNurseServiceImpl, times(0)).saveSysMusculoskeletalSystemExamination(any());
		verify(commonNurseServiceImpl, times(0)).saveSysGenitourinarySystemExamination(any());
		verify(ancNurseServiceImpl, times(0)).saveSysObstetricExamination(any());
	}

	@Test
	public void testGetBenVisitDetailsFrmNurseANC_ValidInputs() {
		Long benRegID = 1L;
		Long visitCode = 1L;

		// Mocking the return values of the methods called on commonNurseServiceImpl
		when(commonNurseServiceImpl.getCSVisitDetails(benRegID, visitCode)).thenReturn(new BeneficiaryVisitDetail());
		when(commonNurseServiceImpl.getBenAdherence(benRegID, visitCode)).thenReturn("Adherence");
		when(commonNurseServiceImpl.getBenChiefComplaints(benRegID, visitCode)).thenReturn("ChiefComplaints");
		when(commonNurseServiceImpl.getLabTestOrders(benRegID, visitCode)).thenReturn("Investigation");
		when(commonNurseServiceImpl.getBenCdss(benRegID, visitCode)).thenReturn("Cdss");

		String response = ancServiceImpl.getBenVisitDetailsFrmNurseANC(benRegID, visitCode);

		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("ANCNurseVisitDetail", gson.toJson(new BeneficiaryVisitDetail()));
		expectedMap.put("BenAdherence", "Adherence");
		expectedMap.put("BenChiefComplaints", "ChiefComplaints");
		expectedMap.put("Investigation", "Investigation");
		expectedMap.put("Cdss", "Cdss");

		assertEquals(expectedMap.toString(), response);
	}

	@Test
	public void testGetBenVisitDetailsFrmNurseANC_NullInputs() {
		Long benRegID = null;
		Long visitCode = null;

		String response = ancServiceImpl.getBenVisitDetailsFrmNurseANC(benRegID, visitCode);

		assertEquals("{}", response);
	}

	@Test
	public void testGetBenVisitDetailsFrmNurseANC_VerifyMethodCalls() {
		Long benRegID = 1L;
		Long visitCode = 1L;

		ancServiceImpl.getBenVisitDetailsFrmNurseANC(benRegID, visitCode);

		verify(commonNurseServiceImpl, times(1)).getCSVisitDetails(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenAdherence(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenChiefComplaints(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getLabTestOrders(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getBenCdss(benRegID, visitCode);
	}

	@Test
	public void testGetBenANCDetailsFrmNurseANC_WhenVisitCodeIsNullAndLatestVisitCodeExists() throws IEMRException {
		when(beneficiaryFlowStatusRepo.getLatestVisitCode(benRegID, "ANC")).thenReturn(visitCode);
		when(ancNurseServiceImpl.getANCCareDetails(benRegID, visitCode)).thenReturn("ANCCareDetails");
		when(ancNurseServiceImpl.getANCWomenVaccineDetails(benRegID, visitCode)).thenReturn("ANCWomenVaccineDetails");

		String response = ancServiceImpl.getBenANCDetailsFrmNurseANC(benRegID, null);

		assertTrue(response.contains("ANCCareDetails"));
		assertTrue(response.contains("ANCWomenVaccineDetails"));
		verify(beneficiaryFlowStatusRepo, times(1)).getLatestVisitCode(benRegID, "ANC");
		verify(ancNurseServiceImpl, times(1)).getANCCareDetails(benRegID, visitCode);
		verify(ancNurseServiceImpl, times(1)).getANCWomenVaccineDetails(benRegID, visitCode);
	}

	@Test
	public void testGetBenANCDetailsFrmNurseANC_WhenVisitCodeIsNotNull() throws IEMRException {
		when(ancNurseServiceImpl.getANCCareDetails(benRegID, visitCode)).thenReturn("ANCCareDetails");
		when(ancNurseServiceImpl.getANCWomenVaccineDetails(benRegID, visitCode)).thenReturn("ANCWomenVaccineDetails");

		String response = ancServiceImpl.getBenANCDetailsFrmNurseANC(benRegID, visitCode);

		assertTrue(response.contains("ANCCareDetails"));
		assertTrue(response.contains("ANCWomenVaccineDetails"));
		verify(beneficiaryFlowStatusRepo, never()).getLatestVisitCode(anyLong(), anyString());
		verify(ancNurseServiceImpl, times(1)).getANCCareDetails(benRegID, visitCode);
		verify(ancNurseServiceImpl, times(1)).getANCWomenVaccineDetails(benRegID, visitCode);
	}

	@Test
	public void testGetBenANCDetailsFrmNurseANC_WhenVisitCodeIsNullAndNoLatestVisitCode() throws IEMRException {
		when(beneficiaryFlowStatusRepo.getLatestVisitCode(benRegID, "ANC")).thenReturn(null);

		String response = ancServiceImpl.getBenANCDetailsFrmNurseANC(benRegID, null);

		assertEquals("{}", response);
		verify(beneficiaryFlowStatusRepo, times(1)).getLatestVisitCode(benRegID, "ANC");
		verify(ancNurseServiceImpl, never()).getANCCareDetails(anyLong(), anyLong());
		verify(ancNurseServiceImpl, never()).getANCWomenVaccineDetails(anyLong(), anyLong());
	}

	@Test
	public void testGetBenANCHistoryDetails_ValidInput() throws IEMRException {
		// Mocking the return values of commonNurseServiceImpl methods
		when(commonNurseServiceImpl.getPastHistoryData(benRegID, visitCode)).thenReturn("PastHistoryData");
		when(commonNurseServiceImpl.getComorbidityConditionsHistory(benRegID, visitCode)).thenReturn("ComorbidityData");
		when(commonNurseServiceImpl.getMedicationHistory(benRegID, visitCode)).thenReturn("MedicationData");
		when(commonNurseServiceImpl.getPersonalHistory(benRegID, visitCode)).thenReturn("PersonalHistoryData");
		when(commonNurseServiceImpl.getFamilyHistory(benRegID, visitCode)).thenReturn("FamilyHistoryData");
		when(commonNurseServiceImpl.getMenstrualHistory(benRegID, visitCode)).thenReturn("MenstrualHistoryData");
		when(commonNurseServiceImpl.getFemaleObstetricHistory(benRegID, visitCode)).thenReturn("ObstetricHistoryData");
		when(commonNurseServiceImpl.getImmunizationHistory(benRegID, visitCode)).thenReturn("ImmunizationHistoryData");
		when(commonNurseServiceImpl.getChildOptionalVaccineHistory(benRegID, visitCode)).thenReturn("ChildVaccineData");

		// Call the method
		String response = ancServiceImpl.getBenANCHistoryDetails(benRegID, visitCode);

		// Assert the response contains the expected keys and values
		assertTrue(response.contains("PastHistoryData"));
		assertTrue(response.contains("ComorbidityData"));
		assertTrue(response.contains("MedicationData"));
		assertTrue(response.contains("PersonalHistoryData"));
		assertTrue(response.contains("FamilyHistoryData"));
		assertTrue(response.contains("MenstrualHistoryData"));
		assertTrue(response.contains("ObstetricHistoryData"));
		assertTrue(response.contains("ImmunizationHistoryData"));
		assertTrue(response.contains("ChildVaccineData"));

		// Verify the mocked methods are called with the correct parameters
		verify(commonNurseServiceImpl, times(1)).getPastHistoryData(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getComorbidityConditionsHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getMedicationHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getPersonalHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getFamilyHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getMenstrualHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getFemaleObstetricHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getImmunizationHistory(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getChildOptionalVaccineHistory(benRegID, visitCode);
	}

	@Test
	public void testGetBenANCHistoryDetails_NullInput() throws IEMRException {
		// Call the method with null inputs
		String response = ancServiceImpl.getBenANCHistoryDetails(null, null);

		// Assert the response is empty
		assertEquals("{}", response);

		// Verify the mocked methods are not called
		verify(commonNurseServiceImpl, times(0)).getPastHistoryData(any(), any());
		verify(commonNurseServiceImpl, times(0)).getComorbidityConditionsHistory(any(), any());
		verify(commonNurseServiceImpl, times(0)).getMedicationHistory(any(), any());
		verify(commonNurseServiceImpl, times(0)).getPersonalHistory(any(), any());
		verify(commonNurseServiceImpl, times(0)).getFamilyHistory(any(), any());
		verify(commonNurseServiceImpl, times(0)).getMenstrualHistory(any(), any());
		verify(commonNurseServiceImpl, times(0)).getFemaleObstetricHistory(any(), any());
		verify(commonNurseServiceImpl, times(0)).getImmunizationHistory(any(), any());
		verify(commonNurseServiceImpl, times(0)).getChildOptionalVaccineHistory(any(), any());
	}

	@Test
	public void testGetBeneficiaryVitalDetails_ValidData() {
		BenAnthropometryDetail anthropometryDetail = new BenAnthropometryDetail();
		BenPhysicalVitalDetail physicalVitalDetail = new BenPhysicalVitalDetail();

		when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, visitCode))
				.thenReturn(anthropometryDetail);
		when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode))
				.thenReturn(physicalVitalDetail);

		String response = ancServiceImpl.getBeneficiaryVitalDetails(beneficiaryRegID, visitCode);

		assertTrue(response.contains("benAnthropometryDetail"));
		assertTrue(response.contains("benPhysicalVitalDetail"));
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID,
				visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetBeneficiaryVitalDetails_EmptyData() {
		when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, visitCode))
				.thenReturn(null);
		when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode)).thenReturn(null);

		String response = ancServiceImpl.getBeneficiaryVitalDetails(beneficiaryRegID, visitCode);

		assertTrue(response.contains("benAnthropometryDetail"));
		assertTrue(response.contains("benPhysicalVitalDetail"));
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID,
				visitCode);
		verify(commonNurseServiceImpl, times(1)).getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetANCExaminationDetailsData_AllDetailsPresent() {
		Long benRegID = 1L;
		Long visitCode = 1L;

		Map<String, Object> examinationDetailsMap = new HashMap<>();
		examinationDetailsMap.put("generalExamination", "General Examination Data");
		examinationDetailsMap.put("headToToeExamination", "Head to Toe Examination Data");
		examinationDetailsMap.put("cardiovascularExamination", "Cardiovascular Examination Data");
		examinationDetailsMap.put("respiratoryExamination", "Respiratory Examination Data");
		examinationDetailsMap.put("centralNervousExamination", "Central Nervous Examination Data");
		examinationDetailsMap.put("musculoskeletalExamination", "Musculoskeletal Examination Data");
		examinationDetailsMap.put("genitourinaryExamination", "Genitourinary Examination Data");
		examinationDetailsMap.put("obstetricExamination", "Obstetric Examination Data");

		when(commonNurseServiceImpl.getGeneralExaminationData(benRegID, visitCode))
				.thenReturn("General Examination Data");
		when(commonNurseServiceImpl.getHeadToToeExaminationData(benRegID, visitCode))
				.thenReturn("Head to Toe Examination Data");
		when(commonNurseServiceImpl.getCardiovascularExamination(benRegID, visitCode))
				.thenReturn("Cardiovascular Examination Data");
		when(commonNurseServiceImpl.getRespiratoryExamination(benRegID, visitCode))
				.thenReturn("Respiratory Examination Data");
		when(commonNurseServiceImpl.getSysCentralNervousExamination(benRegID, visitCode))
				.thenReturn("Central Nervous Examination Data");
		when(commonNurseServiceImpl.getMusculoskeletalExamination(benRegID, visitCode))
				.thenReturn("Musculoskeletal Examination Data");
		when(commonNurseServiceImpl.getGenitourinaryExamination(benRegID, visitCode))
				.thenReturn("Genitourinary Examination Data");
		when(commonNurseServiceImpl.getSysObstetricExamination(benRegID, visitCode))
				.thenReturn("Obstetric Examination Data");

		String response = ancServiceImpl.getANCExaminationDetailsData(benRegID, visitCode);

		Gson gson = new Gson();
		Map<String, Object> responseMap = gson.fromJson(response, Map.class);

		assertEquals(examinationDetailsMap, responseMap);
		verify(commonNurseServiceImpl, times(1)).getGeneralExaminationData(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getHeadToToeExaminationData(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getCardiovascularExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getRespiratoryExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getSysCentralNervousExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getMusculoskeletalExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getGenitourinaryExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getSysObstetricExamination(benRegID, visitCode);
	}

	@Test
	public void testGetANCExaminationDetailsData_SomeDetailsMissing() {
		Long benRegID = 1L;
		Long visitCode = 1L;

		Map<String, Object> examinationDetailsMap = new HashMap<>();
		examinationDetailsMap.put("generalExamination", "General Examination Data");
		examinationDetailsMap.put("headToToeExamination", "Head to Toe Examination Data");
		examinationDetailsMap.put("cardiovascularExamination", "Cardiovascular Examination Data");
		examinationDetailsMap.put("respiratoryExamination", "Respiratory Examination Data");
		examinationDetailsMap.put("centralNervousExamination", "Central Nervous Examination Data");
		examinationDetailsMap.put("musculoskeletalExamination", "Musculoskeletal Examination Data");
		examinationDetailsMap.put("genitourinaryExamination", null);
		examinationDetailsMap.put("obstetricExamination", "Obstetric Examination Data");

		when(commonNurseServiceImpl.getGeneralExaminationData(benRegID, visitCode))
				.thenReturn("General Examination Data");
		when(commonNurseServiceImpl.getHeadToToeExaminationData(benRegID, visitCode))
				.thenReturn("Head to Toe Examination Data");
		when(commonNurseServiceImpl.getCardiovascularExamination(benRegID, visitCode))
				.thenReturn("Cardiovascular Examination Data");
		when(commonNurseServiceImpl.getRespiratoryExamination(benRegID, visitCode))
				.thenReturn("Respiratory Examination Data");
		when(commonNurseServiceImpl.getSysCentralNervousExamination(benRegID, visitCode))
				.thenReturn("Central Nervous Examination Data");
		when(commonNurseServiceImpl.getMusculoskeletalExamination(benRegID, visitCode))
				.thenReturn("Musculoskeletal Examination Data");
		when(commonNurseServiceImpl.getGenitourinaryExamination(benRegID, visitCode)).thenReturn(null);
		when(commonNurseServiceImpl.getSysObstetricExamination(benRegID, visitCode))
				.thenReturn("Obstetric Examination Data");

		String response = ancServiceImpl.getANCExaminationDetailsData(benRegID, visitCode);

		Gson gson = new Gson();
		Map<String, Object> responseMap = gson.fromJson(response, Map.class);

		assertEquals(examinationDetailsMap, responseMap);
		verify(commonNurseServiceImpl, times(1)).getGeneralExaminationData(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getHeadToToeExaminationData(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getCardiovascularExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getRespiratoryExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getSysCentralNervousExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getMusculoskeletalExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getGenitourinaryExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getSysObstetricExamination(benRegID, visitCode);
	}

	@Test
	public void testGetANCExaminationDetailsData_NoDetailsPresent() {
		Long benRegID = 1L;
		Long visitCode = 1L;

		Map<String, Object> examinationDetailsMap = new HashMap<>();
		examinationDetailsMap.put("generalExamination", null);
		examinationDetailsMap.put("headToToeExamination", null);
		examinationDetailsMap.put("cardiovascularExamination", null);
		examinationDetailsMap.put("respiratoryExamination", null);
		examinationDetailsMap.put("centralNervousExamination", null);
		examinationDetailsMap.put("musculoskeletalExamination", null);
		examinationDetailsMap.put("genitourinaryExamination", null);
		examinationDetailsMap.put("obstetricExamination", null);

		when(commonNurseServiceImpl.getGeneralExaminationData(benRegID, visitCode)).thenReturn(null);
		when(commonNurseServiceImpl.getHeadToToeExaminationData(benRegID, visitCode)).thenReturn(null);
		when(commonNurseServiceImpl.getCardiovascularExamination(benRegID, visitCode)).thenReturn(null);
		when(commonNurseServiceImpl.getRespiratoryExamination(benRegID, visitCode)).thenReturn(null);
		when(commonNurseServiceImpl.getSysCentralNervousExamination(benRegID, visitCode)).thenReturn(null);
		when(commonNurseServiceImpl.getMusculoskeletalExamination(benRegID, visitCode)).thenReturn(null);
		when(commonNurseServiceImpl.getGenitourinaryExamination(benRegID, visitCode)).thenReturn(null);
		when(commonNurseServiceImpl.getSysObstetricExamination(benRegID, visitCode)).thenReturn(null);

		String response = ancServiceImpl.getANCExaminationDetailsData(benRegID, visitCode);

		Gson gson = new Gson();
		Map<String, Object> responseMap = gson.fromJson(response, Map.class);

		assertEquals(examinationDetailsMap, responseMap);
		verify(commonNurseServiceImpl, times(1)).getGeneralExaminationData(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getHeadToToeExaminationData(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getCardiovascularExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getRespiratoryExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getSysCentralNervousExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getMusculoskeletalExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getGenitourinaryExamination(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getSysObstetricExamination(benRegID, visitCode);
	}

	@Test
	public void testUpdateBenANCDetails_Success() throws Exception {
		ANCCareDetails ancCareDetails = InputMapper.gson().fromJson(ancDetailsOBJ.get("ancObstetricDetails"),
				ANCCareDetails.class);
		WrapperAncImmunization wrapperAncImmunization = InputMapper.gson()
				.fromJson(ancDetailsOBJ.get("ancImmunization"), WrapperAncImmunization.class);

		when(ancNurseServiceImpl.updateBenAncCareDetails(any(ANCCareDetails.class))).thenReturn(1);
		when(ancNurseServiceImpl.updateBenAncImmunizationDetails(any(WrapperAncImmunization.class))).thenReturn(1);

		int result = ancServiceImpl.updateBenANCDetails(ancDetailsOBJ);

		assertEquals(1, result);
		verify(ancNurseServiceImpl, times(1)).updateBenAncCareDetails(any(ANCCareDetails.class));
		verify(ancNurseServiceImpl, times(1)).updateBenAncImmunizationDetails(any(WrapperAncImmunization.class));
	}

	@Test
	public void testUpdateBenANCDetails_NullOrEmptyFields() throws Exception {
		ancDetailsOBJ.remove("ancObstetricDetails");
		ancDetailsOBJ.remove("ancImmunization");

		int result = ancServiceImpl.updateBenANCDetails(ancDetailsOBJ);

		assertEquals(0, result);
		verify(ancNurseServiceImpl, times(0)).updateBenAncCareDetails(any(ANCCareDetails.class));
		verify(ancNurseServiceImpl, times(0)).updateBenAncImmunizationDetails(any(WrapperAncImmunization.class));
	}

	@Test
	public void testUpdateBenANCDetails_Exception() throws Exception {
		when(ancNurseServiceImpl.updateBenAncCareDetails(any(ANCCareDetails.class)))
				.thenThrow(new RuntimeException("Exception"));

		assertThrows(RuntimeException.class, () -> {
			ancServiceImpl.updateBenANCDetails(ancDetailsOBJ);
		});

		verify(ancNurseServiceImpl, times(1)).updateBenAncCareDetails(any(ANCCareDetails.class));
	}

	@Test
	public void testUpdateBenANCHistoryDetails_Success() throws Exception {
		when(commonNurseServiceImpl.updateBenPastHistoryDetails(any(BenMedHistory.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateBenComorbidConditions(any(WrapperComorbidCondDetails.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateBenMedicationHistory(any(WrapperMedicationHistory.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateBenPersonalHistory(any(BenPersonalHabit.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateBenAllergicHistory(any(BenAllergyHistory.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateBenFamilyHistory(any(BenFamilyHistory.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateMenstrualHistory(any(BenMenstrualDetails.class))).thenReturn(1);
		when(commonNurseServiceImpl.updatePastObstetricHistory(any(WrapperFemaleObstetricHistory.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateChildImmunizationDetail(any(WrapperImmunizationHistory.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateChildOptionalVaccineDetail(any(WrapperChildOptionalVaccineDetail.class)))
				.thenReturn(1);

		int result = ancServiceImpl.updateBenANCHistoryDetails(ancHistoryJson);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).updateBenPastHistoryDetails(any(BenMedHistory.class));
		verify(commonNurseServiceImpl, times(1)).updateBenComorbidConditions(any(WrapperComorbidCondDetails.class));
		verify(commonNurseServiceImpl, times(1)).updateBenMedicationHistory(any(WrapperMedicationHistory.class));
		verify(commonNurseServiceImpl, times(1)).updateBenPersonalHistory(any(BenPersonalHabit.class));
		verify(commonNurseServiceImpl, times(1)).updateBenAllergicHistory(any(BenAllergyHistory.class));
		verify(commonNurseServiceImpl, times(1)).updateBenFamilyHistory(any(BenFamilyHistory.class));
		verify(commonNurseServiceImpl, times(1)).updateMenstrualHistory(any(BenMenstrualDetails.class));
		verify(commonNurseServiceImpl, times(1)).updatePastObstetricHistory(any(WrapperFemaleObstetricHistory.class));
		verify(commonNurseServiceImpl, times(1)).updateChildImmunizationDetail(any(WrapperImmunizationHistory.class));
		verify(commonNurseServiceImpl, times(1))
				.updateChildOptionalVaccineDetail(any(WrapperChildOptionalVaccineDetail.class));
	}

	@Test
	public void testUpdateBenANCHistoryDetails_PartialUpdate() throws Exception {
		ancHistoryJson.remove("pastHistory");

		when(commonNurseServiceImpl.updateBenComorbidConditions(any(WrapperComorbidCondDetails.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateBenMedicationHistory(any(WrapperMedicationHistory.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateBenPersonalHistory(any(BenPersonalHabit.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateBenAllergicHistory(any(BenAllergyHistory.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateBenFamilyHistory(any(BenFamilyHistory.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateMenstrualHistory(any(BenMenstrualDetails.class))).thenReturn(1);
		when(commonNurseServiceImpl.updatePastObstetricHistory(any(WrapperFemaleObstetricHistory.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateChildImmunizationDetail(any(WrapperImmunizationHistory.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateChildOptionalVaccineDetail(any(WrapperChildOptionalVaccineDetail.class)))
				.thenReturn(1);

		int result = ancServiceImpl.updateBenANCHistoryDetails(ancHistoryJson);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, never()).updateBenPastHistoryDetails(any(BenMedHistory.class));
		verify(commonNurseServiceImpl, times(1)).updateBenComorbidConditions(any(WrapperComorbidCondDetails.class));
		verify(commonNurseServiceImpl, times(1)).updateBenMedicationHistory(any(WrapperMedicationHistory.class));
		verify(commonNurseServiceImpl, times(1)).updateBenPersonalHistory(any(BenPersonalHabit.class));
		verify(commonNurseServiceImpl, times(1)).updateBenAllergicHistory(any(BenAllergyHistory.class));
		verify(commonNurseServiceImpl, times(1)).updateBenFamilyHistory(any(BenFamilyHistory.class));
		verify(commonNurseServiceImpl, times(1)).updateMenstrualHistory(any(BenMenstrualDetails.class));
		verify(commonNurseServiceImpl, times(1)).updatePastObstetricHistory(any(WrapperFemaleObstetricHistory.class));
		verify(commonNurseServiceImpl, times(1)).updateChildImmunizationDetail(any(WrapperImmunizationHistory.class));
		verify(commonNurseServiceImpl, times(1))
				.updateChildOptionalVaccineDetail(any(WrapperChildOptionalVaccineDetail.class));
	}

	@Test
	public void testUpdateBenANCHistoryDetails_NullJson() throws Exception {
		int result = ancServiceImpl.updateBenANCHistoryDetails(null);

		assertEquals(0, result);
		verify(commonNurseServiceImpl, never()).updateBenPastHistoryDetails(any(BenMedHistory.class));
		verify(commonNurseServiceImpl, never()).updateBenComorbidConditions(any(WrapperComorbidCondDetails.class));
		verify(commonNurseServiceImpl, never()).updateBenMedicationHistory(any(WrapperMedicationHistory.class));
		verify(commonNurseServiceImpl, never()).updateBenPersonalHistory(any(BenPersonalHabit.class));
		verify(commonNurseServiceImpl, never()).updateBenAllergicHistory(any(BenAllergyHistory.class));
		verify(commonNurseServiceImpl, never()).updateBenFamilyHistory(any(BenFamilyHistory.class));
		verify(commonNurseServiceImpl, never()).updateMenstrualHistory(any(BenMenstrualDetails.class));
		verify(commonNurseServiceImpl, never()).updatePastObstetricHistory(any(WrapperFemaleObstetricHistory.class));
		verify(commonNurseServiceImpl, never()).updateChildImmunizationDetail(any(WrapperImmunizationHistory.class));
		verify(commonNurseServiceImpl, never())
				.updateChildOptionalVaccineDetail(any(WrapperChildOptionalVaccineDetail.class));
	}

	@Test
	public void testUpdateBenANCVitalDetails_Success() throws Exception {
		when(commonNurseServiceImpl.updateANCAnthropometryDetails(any(BenAnthropometryDetail.class))).thenReturn(1);
		when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class))).thenReturn(1);

		int result = ancServiceImpl.updateBenANCVitalDetails(validVitalDetails);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(1)).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testUpdateBenANCVitalDetails_NullInput() throws Exception {
		int result = ancServiceImpl.updateBenANCVitalDetails(null);

		assertEquals(0, result);
		verify(commonNurseServiceImpl, never()).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, never()).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testUpdateBenANCVitalDetails_PartialAnthropometry() throws Exception {
		when(commonNurseServiceImpl.updateANCAnthropometryDetails(any(BenAnthropometryDetail.class))).thenReturn(1);

		int result = ancServiceImpl.updateBenANCVitalDetails(partialAnthropometryDetails);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, never()).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testUpdateBenANCVitalDetails_PartialPhysicalVital() throws Exception {
		when(commonNurseServiceImpl.updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class))).thenReturn(1);

		int result = ancServiceImpl.updateBenANCVitalDetails(partialPhysicalVitalDetails);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, never()).updateANCAnthropometryDetails(any(BenAnthropometryDetail.class));
		verify(commonNurseServiceImpl, times(1)).updateANCPhysicalVitalDetails(any(BenPhysicalVitalDetail.class));
	}

	@Test
	public void testUpdateBenANCExaminationDetails_GeneralExamination() throws Exception {
		JsonObject generalExamination = new JsonObject();
		examinationDetailsOBJ.add("generalExamination", generalExamination);

		when(commonNurseServiceImpl.updatePhyGeneralExamination(any())).thenReturn(1);

		int result = ancServiceImpl.updateBenANCExaminationDetails(examinationDetailsOBJ);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).updatePhyGeneralExamination(any());
	}

	@Test
	public void testUpdateBenANCExaminationDetails_HeadToToeExamination() throws Exception {
		JsonObject headToToeExamination = new JsonObject();
		examinationDetailsOBJ.add("headToToeExamination", headToToeExamination);

		when(commonNurseServiceImpl.updatePhyHeadToToeExamination(any())).thenReturn(1);

		int result = ancServiceImpl.updateBenANCExaminationDetails(examinationDetailsOBJ);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).updatePhyHeadToToeExamination(any());
	}

	@Test
	public void testUpdateBenANCExaminationDetails_CardiovascularExamination() throws Exception {
		JsonObject cardiovascularExamination = new JsonObject();
		examinationDetailsOBJ.add("cardioVascularExamination", cardiovascularExamination);

		when(commonNurseServiceImpl.updateSysCardiovascularExamination(any())).thenReturn(1);

		int result = ancServiceImpl.updateBenANCExaminationDetails(examinationDetailsOBJ);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).updateSysCardiovascularExamination(any());
	}

	@Test
	public void testUpdateBenANCExaminationDetails_RespiratoryExamination() throws Exception {
		JsonObject respiratoryExamination = new JsonObject();
		examinationDetailsOBJ.add("respiratorySystemExamination", respiratoryExamination);

		when(commonNurseServiceImpl.updateSysRespiratoryExamination(any())).thenReturn(1);

		int result = ancServiceImpl.updateBenANCExaminationDetails(examinationDetailsOBJ);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).updateSysRespiratoryExamination(any());
	}

	@Test
	public void testUpdateBenANCExaminationDetails_CentralNervousSystemExamination() throws Exception {
		JsonObject centralNervousSystemExamination = new JsonObject();
		examinationDetailsOBJ.add("centralNervousSystemExamination", centralNervousSystemExamination);

		when(commonNurseServiceImpl.updateSysCentralNervousExamination(any())).thenReturn(1);

		int result = ancServiceImpl.updateBenANCExaminationDetails(examinationDetailsOBJ);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).updateSysCentralNervousExamination(any());
	}

	@Test
	public void testUpdateBenANCExaminationDetails_MusculoskeletalSystemExamination() throws Exception {
		JsonObject musculoskeletalSystemExamination = new JsonObject();
		examinationDetailsOBJ.add("musculoskeletalSystemExamination", musculoskeletalSystemExamination);

		when(commonNurseServiceImpl.updateSysMusculoskeletalSystemExamination(any())).thenReturn(1);

		int result = ancServiceImpl.updateBenANCExaminationDetails(examinationDetailsOBJ);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).updateSysMusculoskeletalSystemExamination(any());
	}

	@Test
	public void testUpdateBenANCExaminationDetails_GenitourinarySystemExamination() throws Exception {
		JsonObject genitourinarySystemExamination = new JsonObject();
		examinationDetailsOBJ.add("genitoUrinarySystemExamination", genitourinarySystemExamination);

		when(commonNurseServiceImpl.updateSysGenitourinarySystemExamination(any())).thenReturn(1);

		int result = ancServiceImpl.updateBenANCExaminationDetails(examinationDetailsOBJ);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).updateSysGenitourinarySystemExamination(any());
	}

	@Test
	public void testUpdateBenANCExaminationDetails_ObstetricExamination() throws Exception {
		JsonObject obstetricExamination = new JsonObject();
		examinationDetailsOBJ.add("obstetricExamination", obstetricExamination);

		when(ancNurseServiceImpl.updateSysObstetricExamination(any())).thenReturn(1);

		int result = ancServiceImpl.updateBenANCExaminationDetails(examinationDetailsOBJ);

		assertEquals(1, result);
		verify(ancNurseServiceImpl, times(1)).updateSysObstetricExamination(any());
	}

	@Test
	public void testUpdateBenANCExaminationDetails_AllUpdatesSuccessful() throws Exception {
		JsonObject generalExamination = new JsonObject();
		JsonObject headToToeExamination = new JsonObject();
		JsonObject cardiovascularExamination = new JsonObject();
		JsonObject respiratoryExamination = new JsonObject();
		JsonObject centralNervousSystemExamination = new JsonObject();
		JsonObject musculoskeletalSystemExamination = new JsonObject();
		JsonObject genitourinarySystemExamination = new JsonObject();
		JsonObject obstetricExamination = new JsonObject();

		examinationDetailsOBJ.add("generalExamination", generalExamination);
		examinationDetailsOBJ.add("headToToeExamination", headToToeExamination);
		examinationDetailsOBJ.add("cardioVascularExamination", cardiovascularExamination);
		examinationDetailsOBJ.add("respiratorySystemExamination", respiratoryExamination);
		examinationDetailsOBJ.add("centralNervousSystemExamination", centralNervousSystemExamination);
		examinationDetailsOBJ.add("musculoskeletalSystemExamination", musculoskeletalSystemExamination);
		examinationDetailsOBJ.add("genitoUrinarySystemExamination", genitourinarySystemExamination);
		examinationDetailsOBJ.add("obstetricExamination", obstetricExamination);

		when(commonNurseServiceImpl.updatePhyGeneralExamination(any())).thenReturn(1);
		when(commonNurseServiceImpl.updatePhyHeadToToeExamination(any())).thenReturn(1);
		when(commonNurseServiceImpl.updateSysCardiovascularExamination(any())).thenReturn(1);
		when(commonNurseServiceImpl.updateSysRespiratoryExamination(any())).thenReturn(1);
		when(commonNurseServiceImpl.updateSysCentralNervousExamination(any())).thenReturn(1);
		when(commonNurseServiceImpl.updateSysMusculoskeletalSystemExamination(any())).thenReturn(1);
		when(commonNurseServiceImpl.updateSysGenitourinarySystemExamination(any())).thenReturn(1);
		when(ancNurseServiceImpl.updateSysObstetricExamination(any())).thenReturn(1);

		int result = ancServiceImpl.updateBenANCExaminationDetails(examinationDetailsOBJ);

		assertEquals(1, result);
		verify(commonNurseServiceImpl, times(1)).updatePhyGeneralExamination(any());
		verify(commonNurseServiceImpl, times(1)).updatePhyHeadToToeExamination(any());
		verify(commonNurseServiceImpl, times(1)).updateSysCardiovascularExamination(any());
		verify(commonNurseServiceImpl, times(1)).updateSysRespiratoryExamination(any());
		verify(commonNurseServiceImpl, times(1)).updateSysCentralNervousExamination(any());
		verify(commonNurseServiceImpl, times(1)).updateSysMusculoskeletalSystemExamination(any());
		verify(commonNurseServiceImpl, times(1)).updateSysGenitourinarySystemExamination(any());
		verify(ancNurseServiceImpl, times(1)).updateSysObstetricExamination(any());
	}

	@Test
	public void testGetBenANCNurseData() throws IEMRException {
		// Mocking the service methods
		when(ancService.getBenANCDetailsFrmNurseANC(benRegID, visitCode)).thenReturn("ANC Details");
		when(ancService.getBenANCHistoryDetails(benRegID, visitCode)).thenReturn("History Details");
		when(ancService.getBeneficiaryVitalDetails(benRegID, visitCode)).thenReturn("Vital Details");
		when(ancService.getANCExaminationDetailsData(benRegID, visitCode)).thenReturn("Examination Details");
		when(ancService.getBeneficiaryCdssDetails(benRegID, visitCode)).thenReturn("CDSS Details");

		// Calling the method under test
		String response = antenatalCareController.getBenANCNurseData(benRegID, visitCode);

		// Verifying the response
		assertTrue(response.contains("ANC Details"));
		assertTrue(response.contains("History Details"));
		assertTrue(response.contains("Vital Details"));
		assertTrue(response.contains("Examination Details"));
		assertTrue(response.contains("CDSS Details"));

		// Verifying the service method calls
		verify(ancService, times(1)).getBenANCDetailsFrmNurseANC(benRegID, visitCode);
		verify(ancService, times(1)).getBenANCHistoryDetails(benRegID, visitCode);
		verify(ancService, times(1)).getBeneficiaryVitalDetails(benRegID, visitCode);
		verify(ancService, times(1)).getANCExaminationDetailsData(benRegID, visitCode);
		verify(ancService, times(1)).getBeneficiaryCdssDetails(benRegID, visitCode);
	}

	@Test
	public void testGetBenANCNurseData_Exception() throws IEMRException {
		// Mocking the service methods to throw exceptions
		when(ancService.getBenANCDetailsFrmNurseANC(benRegID, visitCode))
				.thenThrow(new IEMRException("Error fetching ANC details"));
		when(ancService.getBenANCHistoryDetails(benRegID, visitCode))
				.thenThrow(new IEMRException("Error fetching history details"));
		when(ancService.getBeneficiaryVitalDetails(benRegID, visitCode))
				.thenThrow(new IEMRException("Error fetching vital details"));
		when(ancService.getANCExaminationDetailsData(benRegID, visitCode))
				.thenThrow(new IEMRException("Error fetching examination details"));
		when(ancService.getBeneficiaryCdssDetails(benRegID, visitCode))
				.thenThrow(new IEMRException("Error fetching CDSS details"));

		// Calling the method under test
		Exception exception = assertThrows(IEMRException.class, () -> {
			antenatalCareController.getBenANCNurseData(benRegID, visitCode);
		});

		// Verifying the exception message
		assertTrue(exception.getMessage().contains("Error fetching ANC details"));

		// Verifying the service method calls
		verify(ancService, times(1)).getBenANCDetailsFrmNurseANC(benRegID, visitCode);
		verify(ancService, times(1)).getBenANCHistoryDetails(benRegID, visitCode);
		verify(ancService, times(1)).getBeneficiaryVitalDetails(benRegID, visitCode);
		verify(ancService, times(1)).getANCExaminationDetailsData(benRegID, visitCode);
		verify(ancService, times(1)).getBeneficiaryCdssDetails(benRegID, visitCode);
	}

	@Test
	public void testGetBenCaseRecordFromDoctorANC_Success() throws Exception {
		when(commonDoctorServiceImpl.getFindingsDetails(benRegID, visitCode)).thenReturn("Findings");
		when(ancDoctorServiceImpl.getANCDiagnosisDetails(benRegID, visitCode)).thenReturn("Diagnosis");
		when(commonDoctorServiceImpl.getInvestigationDetails(benRegID, visitCode)).thenReturn("Investigation");
		when(commonDoctorServiceImpl.getPrescribedDrugs(benRegID, visitCode)).thenReturn("Prescription");
		when(commonDoctorServiceImpl.getReferralDetails(benRegID, visitCode, false)).thenReturn("Refer");
		when(commonDoctorServiceImpl.getFoetalMonitorData(benRegID, visitCode)).thenReturn("FetosenseData");
		when(labTechnicianServiceImpl.getLabResultDataForBen(benRegID, visitCode)).thenReturn("LabReport");
		when(commonNurseServiceImpl.getGraphicalTrendData(benRegID, "anc")).thenReturn("GraphData");
		when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(benRegID, visitCode))
				.thenReturn("ArchivedVisitcodeForLabResult");

		String response = ancServiceImpl.getBenCaseRecordFromDoctorANC(benRegID, visitCode);

		assertTrue(response.contains("Findings"));
		assertTrue(response.contains("Diagnosis"));
		assertTrue(response.contains("Investigation"));
		assertTrue(response.contains("Prescription"));
		assertTrue(response.contains("Refer"));
		assertTrue(response.contains("FetosenseData"));
		assertTrue(response.contains("LabReport"));
		assertTrue(response.contains("GraphData"));
		assertTrue(response.contains("ArchivedVisitcodeForLabResult"));

		verify(commonDoctorServiceImpl, times(1)).getFindingsDetails(benRegID, visitCode);
		verify(ancDoctorServiceImpl, times(1)).getANCDiagnosisDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getInvestigationDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getPrescribedDrugs(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getReferralDetails(benRegID, visitCode, false);
		verify(commonDoctorServiceImpl, times(1)).getFoetalMonitorData(benRegID, visitCode);
		verify(labTechnicianServiceImpl, times(1)).getLabResultDataForBen(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getGraphicalTrendData(benRegID, "anc");
		verify(labTechnicianServiceImpl, times(1)).getLast_3_ArchivedTestVisitList(benRegID, visitCode);
	}

	@Test
	public void testGetBenCaseRecordFromDoctorANC_NullValues() throws Exception {
		when(commonDoctorServiceImpl.getFindingsDetails(benRegID, visitCode)).thenReturn(null);
		when(ancDoctorServiceImpl.getANCDiagnosisDetails(benRegID, visitCode)).thenReturn(null);
		when(commonDoctorServiceImpl.getInvestigationDetails(benRegID, visitCode)).thenReturn(null);
		when(commonDoctorServiceImpl.getPrescribedDrugs(benRegID, visitCode)).thenReturn(null);
		when(commonDoctorServiceImpl.getReferralDetails(benRegID, visitCode, false)).thenReturn(null);
		when(commonDoctorServiceImpl.getFoetalMonitorData(benRegID, visitCode)).thenReturn(null);
		when(labTechnicianServiceImpl.getLabResultDataForBen(benRegID, visitCode)).thenReturn(null);
		when(commonNurseServiceImpl.getGraphicalTrendData(benRegID, "anc")).thenReturn(null);
		when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(benRegID, visitCode)).thenReturn(null);

		String response = ancServiceImpl.getBenCaseRecordFromDoctorANC(benRegID, visitCode);

		assertTrue(response.contains("findings"));
		assertTrue(response.contains("diagnosis"));
		assertTrue(response.contains("investigation"));
		assertTrue(response.contains("prescription"));
		assertTrue(response.contains("Refer"));
		assertTrue(response.contains("fetosenseData"));
		assertTrue(response.contains("LabReport"));
		assertTrue(response.contains("GraphData"));
		assertTrue(response.contains("ArchivedVisitcodeForLabResult"));

		verify(commonDoctorServiceImpl, times(1)).getFindingsDetails(benRegID, visitCode);
		verify(ancDoctorServiceImpl, times(1)).getANCDiagnosisDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getInvestigationDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getPrescribedDrugs(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getReferralDetails(benRegID, visitCode, false);
		verify(commonDoctorServiceImpl, times(1)).getFoetalMonitorData(benRegID, visitCode);
		verify(labTechnicianServiceImpl, times(1)).getLabResultDataForBen(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getGraphicalTrendData(benRegID, "anc");
		verify(labTechnicianServiceImpl, times(1)).getLast_3_ArchivedTestVisitList(benRegID, visitCode);
	}

	@Test
	public void testGetBenCaseRecordFromDoctorANC_EmptyValues() throws Exception {
		when(commonDoctorServiceImpl.getFindingsDetails(benRegID, visitCode)).thenReturn("");
		when(ancDoctorServiceImpl.getANCDiagnosisDetails(benRegID, visitCode)).thenReturn("");
		when(commonDoctorServiceImpl.getInvestigationDetails(benRegID, visitCode)).thenReturn("");
		when(commonDoctorServiceImpl.getPrescribedDrugs(benRegID, visitCode)).thenReturn("");
		when(commonDoctorServiceImpl.getReferralDetails(benRegID, visitCode, false)).thenReturn("");
		when(commonDoctorServiceImpl.getFoetalMonitorData(benRegID, visitCode)).thenReturn("");
		when(labTechnicianServiceImpl.getLabResultDataForBen(benRegID, visitCode)).thenReturn("");
		when(commonNurseServiceImpl.getGraphicalTrendData(benRegID, "anc")).thenReturn("");
		when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(benRegID, visitCode)).thenReturn("");

		String response = ancServiceImpl.getBenCaseRecordFromDoctorANC(benRegID, visitCode);

		assertTrue(response.contains("findings"));
		assertTrue(response.contains("diagnosis"));
		assertTrue(response.contains("investigation"));
		assertTrue(response.contains("prescription"));
		assertTrue(response.contains("Refer"));
		assertTrue(response.contains("fetosenseData"));
		assertTrue(response.contains("LabReport"));
		assertTrue(response.contains("GraphData"));
		assertTrue(response.contains("ArchivedVisitcodeForLabResult"));

		verify(commonDoctorServiceImpl, times(1)).getFindingsDetails(benRegID, visitCode);
		verify(ancDoctorServiceImpl, times(1)).getANCDiagnosisDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getInvestigationDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getPrescribedDrugs(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getReferralDetails(benRegID, visitCode, false);
		verify(commonDoctorServiceImpl, times(1)).getFoetalMonitorData(benRegID, visitCode);
		verify(labTechnicianServiceImpl, times(1)).getLabResultDataForBen(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getGraphicalTrendData(benRegID, "anc");
		verify(labTechnicianServiceImpl, times(1)).getLast_3_ArchivedTestVisitList(benRegID, visitCode);
	}

	@Test
	public void testGetBenCaseRecordFromDoctorANC_Exception() throws Exception {
		when(commonDoctorServiceImpl.getFindingsDetails(benRegID, visitCode)).thenThrow(new IEMRException("Exception"));
		when(ancDoctorServiceImpl.getANCDiagnosisDetails(benRegID, visitCode))
				.thenThrow(new IEMRException("Exception"));
		when(commonDoctorServiceImpl.getInvestigationDetails(benRegID, visitCode))
				.thenThrow(new IEMRException("Exception"));
		when(commonDoctorServiceImpl.getPrescribedDrugs(benRegID, visitCode)).thenThrow(new IEMRException("Exception"));
		when(commonDoctorServiceImpl.getReferralDetails(benRegID, visitCode, false))
				.thenThrow(new IEMRException("Exception"));
		when(commonDoctorServiceImpl.getFoetalMonitorData(benRegID, visitCode))
				.thenThrow(new IEMRException("Exception"));
		when(labTechnicianServiceImpl.getLabResultDataForBen(benRegID, visitCode))
				.thenThrow(new IEMRException("Exception"));
		when(commonNurseServiceImpl.getGraphicalTrendData(benRegID, "anc")).thenThrow(new IEMRException("Exception"));
		when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(benRegID, visitCode))
				.thenThrow(new IEMRException("Exception"));

		assertThrows(IEMRException.class, () -> {
			ancServiceImpl.getBenCaseRecordFromDoctorANC(benRegID, visitCode);
		});

		verify(commonDoctorServiceImpl, times(1)).getFindingsDetails(benRegID, visitCode);
		verify(ancDoctorServiceImpl, times(1)).getANCDiagnosisDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getInvestigationDetails(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getPrescribedDrugs(benRegID, visitCode);
		verify(commonDoctorServiceImpl, times(1)).getReferralDetails(benRegID, visitCode, false);
		verify(commonDoctorServiceImpl, times(1)).getFoetalMonitorData(benRegID, visitCode);
		verify(labTechnicianServiceImpl, times(1)).getLabResultDataForBen(benRegID, visitCode);
		verify(commonNurseServiceImpl, times(1)).getGraphicalTrendData(benRegID, "anc");
		verify(labTechnicianServiceImpl, times(1)).getLast_3_ArchivedTestVisitList(benRegID, visitCode);
	}

	@Test
	public void testUpdateANCDoctorData_FindingsUpdate() throws Exception {
		// Setup
		JsonObject findings = new JsonObject();
		findings.addProperty("findingsKey", "findingsValue");
		requestObj.add("findings", findings);

		when(commonDoctorServiceImpl.updateDocFindings(any())).thenReturn(1);

		// Execute
		Long result = ancServiceImpl.updateANCDoctorData(requestObj, authorization);

		// Verify
		assertNotNull(result);
		verify(commonDoctorServiceImpl, times(1)).updateDocFindings(any());
	}

	@Test
	public void testUpdateANCDoctorData_DiagnosisUpdate() throws Exception {
		// Setup
		JsonObject diagnosis = new JsonObject();
		diagnosis.addProperty("diagnosisKey", "diagnosisValue");
		requestObj.add("diagnosis", diagnosis);

		when(ancDoctorServiceImpl.updateBenANCDiagnosis(any())).thenReturn(1);

		// Execute
		Long result = ancServiceImpl.updateANCDoctorData(requestObj, authorization);

		// Verify
		assertNotNull(result);
		verify(ancDoctorServiceImpl, times(1)).updateBenANCDiagnosis(any());
	}

	@Test
	public void testUpdateANCDoctorData_PrescribedLabTestsUpdate() throws Exception {
		// Setup
		JsonObject investigation = new JsonObject();
		JsonArray labList = new JsonArray();
		investigation.add("laboratoryList", labList);
		requestObj.add("investigation", investigation);

		when(commonNurseServiceImpl.saveBenInvestigation(any())).thenReturn(1L);

		// Execute
		Long result = ancServiceImpl.updateANCDoctorData(requestObj, authorization);

		// Verify
		assertNotNull(result);
		verify(commonNurseServiceImpl, times(1)).saveBenInvestigation(any());
	}

	@Test
	public void testUpdateANCDoctorData_PrescribedMedicinesUpdate() throws Exception {
		// Setup
		JsonArray prescription = new JsonArray();
		requestObj.add("prescription", prescription);

		when(commonNurseServiceImpl.saveBenPrescribedDrugsList(any())).thenReturn(1);

		// Execute
		Long result = ancServiceImpl.updateANCDoctorData(requestObj, authorization);

		// Verify
		assertNotNull(result);
		verify(commonNurseServiceImpl, times(1)).saveBenPrescribedDrugsList(any());
	}

	@Test
	public void testUpdateANCDoctorData_ReferralUpdate() throws Exception {
		// Setup
		JsonObject refer = new JsonObject();
		refer.addProperty("referKey", "referValue");
		requestObj.add("refer", refer);

		when(commonDoctorServiceImpl.updateBenReferDetails(any(), eq(false))).thenReturn(1L);

		// Execute
		Long result = ancServiceImpl.updateANCDoctorData(requestObj, authorization);

		// Verify
		assertNotNull(result);
		verify(commonDoctorServiceImpl, times(1)).updateBenReferDetails(any(), eq(false));
	}

	@Test
	public void testUpdateANCDoctorData_NullRequestObject() throws Exception {
		// Execute
		Long result = ancServiceImpl.updateANCDoctorData(null, authorization);

		// Verify
		assertNull(result);
	}

	@Test
	public void testUpdateANCDoctorData_ExceptionHandling() {
		// Setup
		JsonObject findings = new JsonObject();
		findings.addProperty("findingsKey", "findingsValue");
		requestObj.add("findings", findings);

		when(commonDoctorServiceImpl.updateDocFindings(any())).thenThrow(new RuntimeException("Error"));

		// Execute & Verify
		assertThrows(RuntimeException.class, () -> {
			ancServiceImpl.updateANCDoctorData(requestObj, authorization);
		});
	}

	@Test
	public void testGetHRPStatus_NoHRPConditions() throws Exception {
		HrpStatusAndReasonsRequestModel model = new HrpStatusAndReasonsRequestModel();
		model.setComorbidConditions(new String[] {});
		model.setPastIllness(new String[] {});
		model.setBenificiaryAge(25);
		model.setBloodGroupType("O+");
		model.setBeneficiaryHeight(160);
		model.setHemoglobin(12.0);
		model.setMalPresentation(false);
		model.setLowLyingPlacenta(false);
		model.setVertebralDeformity(false);
		model.setPastObstetric(new HashSet<>());

		String response = ancService.getHRPStatus(model);
		assertFalse(response.contains("\"isHRP\":true"));
	}

	@Test
	public void testGetHRPStatus_ComorbidConditions() throws Exception {
		HrpStatusAndReasonsRequestModel model = new HrpStatusAndReasonsRequestModel();
		model.setComorbidConditions(new String[] { "Pregnancy induced hypertension" });
		model.setPastIllness(new String[] {});
		model.setBenificiaryAge(25);
		model.setBloodGroupType("O+");
		model.setBeneficiaryHeight(160);
		model.setHemoglobin(12.0);
		model.setMalPresentation(false);
		model.setLowLyingPlacenta(false);
		model.setVertebralDeformity(false);
		model.setPastObstetric(new HashSet<>());

		String response = ancService.getHRPStatus(model);
		assertTrue(response.contains("\"isHRP\":true"));
		assertTrue(response.contains("Pregnancy induced hypertension"));
	}

	@Test
	public void testGetHRPStatus_PastIllness() throws Exception {
		HrpStatusAndReasonsRequestModel model = new HrpStatusAndReasonsRequestModel();
		model.setComorbidConditions(new String[] {});
		model.setPastIllness(new String[] { "Diabetes" });
		model.setBenificiaryAge(25);
		model.setBloodGroupType("O+");
		model.setBeneficiaryHeight(160);
		model.setHemoglobin(12.0);
		model.setMalPresentation(false);
		model.setLowLyingPlacenta(false);
		model.setVertebralDeformity(false);
		model.setPastObstetric(new HashSet<>());

		String response = ancService.getHRPStatus(model);
		assertTrue(response.contains("\"isHRP\":true"));
		assertTrue(response.contains("Diabetes"));
	}

	@Test
	public void testGetHRPStatus_AgeConditions() throws Exception {
		HrpStatusAndReasonsRequestModel model = new HrpStatusAndReasonsRequestModel();
		model.setComorbidConditions(new String[] {});
		model.setPastIllness(new String[] {});
		model.setBenificiaryAge(19);
		model.setBloodGroupType("O+");
		model.setBeneficiaryHeight(160);
		model.setHemoglobin(12.0);
		model.setMalPresentation(false);
		model.setLowLyingPlacenta(false);
		model.setVertebralDeformity(false);
		model.setPastObstetric(new HashSet<>());

		String response = ancService.getHRPStatus(model);
		assertTrue(response.contains("\"isHRP\":true"));
		assertTrue(response.contains("Young Primi"));
	}

	@Test
	public void testGetHRPStatus_BloodGroupConditions() throws Exception {
		HrpStatusAndReasonsRequestModel model = new HrpStatusAndReasonsRequestModel();
		model.setComorbidConditions(new String[] {});
		model.setPastIllness(new String[] {});
		model.setBenificiaryAge(25);
		model.setBloodGroupType("O-");
		model.setBeneficiaryHeight(160);
		model.setHemoglobin(12.0);
		model.setMalPresentation(false);
		model.setLowLyingPlacenta(false);
		model.setVertebralDeformity(false);
		model.setPastObstetric(new HashSet<>());

		String response = ancService.getHRPStatus(model);
		assertTrue(response.contains("\"isHRP\":true"));
		assertTrue(response.contains("Rh negative"));
	}

	@Test
	public void testGetHRPStatus_AnthropometryConditions() throws Exception {
		HrpStatusAndReasonsRequestModel model = new HrpStatusAndReasonsRequestModel();
		model.setComorbidConditions(new String[] {});
		model.setPastIllness(new String[] {});
		model.setBenificiaryAge(25);
		model.setBloodGroupType("O+");
		model.setBeneficiaryHeight(135);
		model.setHemoglobin(12.0);
		model.setMalPresentation(false);
		model.setLowLyingPlacenta(false);
		model.setVertebralDeformity(false);
		model.setPastObstetric(new HashSet<>());

		String response = ancService.getHRPStatus(model);
		assertTrue(response.contains("\"isHRP\":true"));
		assertTrue(response.contains("Short height woman"));
	}

	@Test
	public void testGetHRPStatus_VitalsConditions() throws Exception {
		HrpStatusAndReasonsRequestModel model = new HrpStatusAndReasonsRequestModel();
		model.setComorbidConditions(new String[] {});
		model.setPastIllness(new String[] {});
		model.setBenificiaryAge(25);
		model.setBloodGroupType("O+");
		model.setBeneficiaryHeight(160);
		model.setHemoglobin(6.0);
		model.setMalPresentation(false);
		model.setLowLyingPlacenta(false);
		model.setVertebralDeformity(false);
		model.setPastObstetric(new HashSet<>());

		String response = ancService.getHRPStatus(model);
		assertTrue(response.contains("\"isHRP\":true"));
		assertTrue(response.contains("Hemoglobin is less than 7"));
	}

	@Test
	public void testGetHRPStatus_ObstetricExaminationConditions() throws Exception {
		HrpStatusAndReasonsRequestModel model = new HrpStatusAndReasonsRequestModel();
		model.setComorbidConditions(new String[] {});
		model.setPastIllness(new String[] {});
		model.setBenificiaryAge(25);
		model.setBloodGroupType("O+");
		model.setBeneficiaryHeight(160);
		model.setHemoglobin(12.0);
		model.setMalPresentation(true);
		model.setLowLyingPlacenta(false);
		model.setVertebralDeformity(false);
		model.setPastObstetric(new HashSet<>());

		String response = ancService.getHRPStatus(model);
		assertTrue(response.contains("\"isHRP\":true"));
		assertTrue(response.contains("Mal Presentation"));
	}

	@Test
	public void testGetHRPStatus_PastObstetricHistoryConditions() throws Exception {
		HrpStatusAndReasonsRequestModel model = new HrpStatusAndReasonsRequestModel();
		model.setComorbidConditions(new String[] {});
		model.setPastIllness(new String[] {});
		model.setBenificiaryAge(25);
		model.setBloodGroupType("O+");
		model.setBeneficiaryHeight(160);
		model.setHemoglobin(12.0);
		model.setMalPresentation(false);
		model.setLowLyingPlacenta(false);
		model.setVertebralDeformity(false);

		Set<PastObstetricHostoryHRP> pastObstetric = new HashSet<>();
		PastObstetricHostoryHRP pastObstetricHostoryHRP = new PastObstetricHostoryHRP();
		pastObstetricHostoryHRP.setComplicationDuringPregnancy(Set.of(new ComplicationDuringPregnancyHRP("Twins")));
		pastObstetric.add(pastObstetricHostoryHRP);
		model.setPastObstetric(pastObstetric);

		String response = ancService.getHRPStatus(model);
		assertTrue(response.contains("\"isHRP\":true"));
		assertTrue(response.contains("Twins"));
	}

	@Test
	public void testGetHRPInformation_WithReasonsForHRPDB() throws IEMRException {
		SysObstetricExamination sysObstetricExamination = new SysObstetricExamination();
		sysObstetricExamination.setReasonsForHRPDB("Reason1||Reason2");

		when(sysObstetricExaminationRepo.findFirstByBeneficiaryRegIDOrderByIDDesc(beneficiaryRegId))
				.thenReturn(sysObstetricExamination);

		SysObstetricExamination result = ancServiceImpl.getHRPInformation(beneficiaryRegId);

		assertNotNull(result);
		assertArrayEquals(new String[] { "Reason1", "Reason2" }, result.getReasonsForHRP());
		verify(sysObstetricExaminationRepo, times(1)).findFirstByBeneficiaryRegIDOrderByIDDesc(beneficiaryRegId);
	}

	@Test
	public void testGetHRPInformation_WithoutReasonsForHRPDB() throws IEMRException {
		SysObstetricExamination sysObstetricExamination = new SysObstetricExamination();

		when(sysObstetricExaminationRepo.findFirstByBeneficiaryRegIDOrderByIDDesc(beneficiaryRegId))
				.thenReturn(sysObstetricExamination);

		SysObstetricExamination result = ancServiceImpl.getHRPInformation(beneficiaryRegId);

		assertNotNull(result);
		assertNull(result.getReasonsForHRP());
		verify(sysObstetricExaminationRepo, times(1)).findFirstByBeneficiaryRegIDOrderByIDDesc(beneficiaryRegId);
	}

	@Test
	public void testGetHRPInformation_NullResult() throws IEMRException {
		when(sysObstetricExaminationRepo.findFirstByBeneficiaryRegIDOrderByIDDesc(beneficiaryRegId)).thenReturn(null);

		SysObstetricExamination result = ancServiceImpl.getHRPInformation(beneficiaryRegId);

		assertNull(result);
		verify(sysObstetricExaminationRepo, times(1)).findFirstByBeneficiaryRegIDOrderByIDDesc(beneficiaryRegId);
	}
}
