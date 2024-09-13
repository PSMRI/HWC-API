package com.iemr.hwc.service.common.transaction;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ibm.icu.util.Calendar;
import com.iemr.hwc.data.anc.BenClinicalObservations;
import com.iemr.hwc.data.anc.WrapperAncFindings;
import com.iemr.hwc.data.anc.WrapperBenInvestigationANC;
import com.iemr.hwc.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.quickConsultation.BenChiefComplaint;
import com.iemr.hwc.data.tele_consultation.TcSpecialistSlotBookingRequestOBJ;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.doctor.BenReferDetailsRepo;
import com.iemr.hwc.repo.doctor.DocWorkListRepo;
import com.iemr.hwc.repo.foetalmonitor.FoetalMonitorRepo;
import com.iemr.hwc.repo.nurse.pnc.PNCDiagnosisRepo;
import com.iemr.hwc.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.hwc.repo.quickConsultation.LabTestOrderDetailRepo;
import com.iemr.hwc.repo.quickConsultation.PrescribedDrugDetailRepo;
import com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.hwc.repo.tc_consultation.TCRequestModelRepo;
import com.iemr.hwc.repo.tc_consultation.TeleconsultationStatsRepo;
import com.iemr.hwc.repository.anc.BenClinicalObservationsRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.tele_consultation.SMSGatewayServiceImpl;
import com.iemr.hwc.utils.InputMapper;

import net.minidev.json.JSONObject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

public class TestCommonDoctorServiceImpl {
	
	
	@ExtendWith(MockitoExtension.class)
	
	    @Mock
	    private BenClinicalObservationsRepo benClinicalObservationsRepo;

	    @Mock
	    private InputMapper inputMapper;

	    
	    @Mock
	    private BenChiefComplaintRepo benChiefComplaintRepo;

	    @Mock
	    private SnomedServiceImpl snomedServiceImpl;

	    @Mock
	    private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;

	    @Mock
	    private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;

	    @Mock
	    private TCRequestModelRepo tCRequestModelRepo;

	    @Mock
	    private PNCDiagnosisRepo pNCDiagnosisRepo;

	    @Mock
	    private PrescriptionDetailRepo prescriptionDetailRepo;

	    @Mock
	    private NCDCareDiagnosisRepo NCDCareDiagnosisRepo;

	    @Mock
	    private SMSGatewayServiceImpl sMSGatewayServiceImpl;

	    @Mock
	    private FoetalMonitorRepo foetalMonitorRepo;

	    @Mock
	    private TeleconsultationStatsRepo teleconsultationStatsRepo;
	    @Mock
	    private DocWorkListRepo docWorkListRepo;
	    @Mock
	    private BenReferDetailsRepo benReferDetailsRepo;
	    @Mock
	    private LabTestOrderDetailRepo labTestOrderDetailRepo;
	    @Mock
	    private PrescribedDrugDetailRepo prescribedDrugDetailRepo;
	    @Mock
	    private RestTemplate restTemplate;

	   

	   

	   
	   

	   
	   
	   

	  
	

	   
	    

	  

	 

	   

	   

	    @InjectMocks
	    private CommonDoctorServiceImpl commonDoctorServiceImpl;

	    private JsonObject jsonObject;
	    private WrapperAncFindings wrapperAncFindings;
	    private BenClinicalObservations benClinicalObservations;
	    private List<BenChiefComplaint> benChiefComplaints;
	    private Gson gson;
	 
	    private CommonDoctorServiceImpl commonDoctorService;
	    private ArrayList<BeneficiaryFlowStatus> mockBeneficiaryFlowStatusList;
	    private Integer providerServiceMapId;
	    private Integer userID;
	    private Integer serviceID;
	    private Integer vanID;
	    private ArrayList<BeneficiaryFlowStatus> tcSpecialistWorkList;
	    private Calendar cal;
	    private long sevenDaysAgo;
	    private List<BeneficiaryFlowStatus> tcSpecialistWorkListFutureScheduled;
	    private Long beneficiaryRegID;
	    private Long visitCode;
	    private ArrayList<Object[]> labTestOrders;
	    private Boolean rrList;
	    private List<BenChiefComplaint> benChiefComplaintList;
	    private JsonObject referObj;
	    private CommonUtilityClass commonUtilityClass;
	    private TeleconsultationRequestOBJ tcRequestOBJ;

	    private JSONObject validJsonObject;
	    private JSONObject invalidJsonObject;
	    private TcSpecialistSlotBookingRequestOBJ tcSpecialistSlotBookingRequestOBJ;
	    private String authorization;

		   
	    
	    


	    @BeforeEach
	    public void setUp() {
	        jsonObject = new JsonObject();
	        jsonObject.addProperty("key", "value");
	    }

	    @Test
	    public void testSaveFindings_Success() throws Exception {
	        BenClinicalObservations benClinicalObservations = new BenClinicalObservations();
	        when(InputMapper.gson().fromJson(jsonObject, BenClinicalObservations.class)).thenReturn(benClinicalObservations);
	        when(benClinicalObservationsRepo.save(benClinicalObservations)).thenReturn(benClinicalObservations);

	        Integer result = commonDoctorServiceImpl.saveFindings(jsonObject);

	        assertEquals(1, result);
	        verify(benClinicalObservationsRepo, times(1)).save(benClinicalObservations);
	    }

	    @Test
	    public void testSaveFindings_Failure() throws Exception {
	        BenClinicalObservations benClinicalObservations = new BenClinicalObservations();
	        when(InputMapper.gson().fromJson(jsonObject, BenClinicalObservations.class)).thenReturn(benClinicalObservations);
	        when(benClinicalObservationsRepo.save(benClinicalObservations)).thenReturn(null);

	        Integer result = commonDoctorServiceImpl.saveFindings(jsonObject);

	        assertEquals(0, result);
	        verify(benClinicalObservationsRepo, times(1)).save(benClinicalObservations);
	    }

	    @Test
	    public void testSaveFindings_Exception() {
	        when(InputMapper.gson().fromJson(jsonObject, BenClinicalObservations.class)).thenThrow(new RuntimeException("Exception"));

	        assertThrows(Exception.class, () -> {
	            commonDoctorServiceImpl.saveFindings(jsonObject);
	        });
	    }

	    @Test
	    public void testSaveDocFindings_ClinicalObservationsSaved() {
	        when(benClinicalObservationsRepo.save(any(BenClinicalObservations.class))).thenReturn(benClinicalObservations);

	        Integer result = commonDoctorServiceImpl.saveDocFindings(wrapperAncFindings);

	        assertEquals(1, result);
	        verify(benClinicalObservationsRepo, times(1)).save(any(BenClinicalObservations.class));
	    }

	    @Test
	    public void testSaveDocFindings_ChiefComplaintsSaved() {
	        BenChiefComplaint chiefComplaint = new BenChiefComplaint();
	        chiefComplaint.setChiefComplaint("Headache");
	        benChiefComplaints.add(chiefComplaint);

	        when(benClinicalObservationsRepo.save(any(BenClinicalObservations.class))).thenReturn(benClinicalObservations);
	        when(benChiefComplaintRepo.saveAll(anyList())).thenReturn(benChiefComplaints);

	        Integer result = commonDoctorServiceImpl.saveDocFindings(wrapperAncFindings);

	        assertEquals(1, result);
	        verify(benChiefComplaintRepo, times(1)).saveAll(anyList());
	    }

	    @Test
	    public void testSaveDocFindings_EmptyChiefComplaints() {
	        when(benClinicalObservationsRepo.save(any(BenClinicalObservations.class))).thenReturn(benClinicalObservations);

	        Integer result = commonDoctorServiceImpl.saveDocFindings(wrapperAncFindings);

	        assertEquals(1, result);
	        verify(benChiefComplaintRepo, never()).saveAll(anyList());
	    }

	    @Test
	    public void testSaveDocFindings_SuccessfulSave() {
	        BenChiefComplaint chiefComplaint = new BenChiefComplaint();
	        chiefComplaint.setChiefComplaint("Headache");
	        benChiefComplaints.add(chiefComplaint);

	        when(benClinicalObservationsRepo.save(any(BenClinicalObservations.class))).thenReturn(benClinicalObservations);
	        when(benChiefComplaintRepo.saveAll(anyList())).thenReturn(benChiefComplaints);

	        Integer result = commonDoctorServiceImpl.saveDocFindings(wrapperAncFindings);

	        assertEquals(1, result);
	        verify(benClinicalObservationsRepo, times(1)).save(any(BenClinicalObservations.class));
	        verify(benChiefComplaintRepo, times(1)).saveAll(anyList());
	    }
	    @Test
	    public void testGetBenClinicalObservations() {
	        when(snomedServiceImpl.findSnomedCTRecordFromTerm("fever")).thenReturn(new SCTDescription("111", "Fever"));
	        when(snomedServiceImpl.findSnomedCTRecordFromTerm("cough")).thenReturn(new SCTDescription("222", "Cough"));

	        BenClinicalObservations result = commonDoctorServiceImpl.getBenClinicalObservations(wrapperAncFindings);

	        assertNotNull(result);
	        assertEquals(1L, result.getBeneficiaryRegID());
	        assertEquals(1L, result.getBenVisitID());
	        assertEquals(1L, result.getVisitCode());
	        assertEquals(1L, result.getProviderServiceMapID());
	        assertEquals(1L, result.getVanID());
	        assertEquals(1L, result.getParkingPlaceID());
	        assertEquals("testUser", result.getCreatedBy());
	        assertEquals("fever,cough", result.getOtherSymptoms());
	        assertEquals("111,222", result.getOtherSymptomsSCTCode());
	        assertEquals("Fever,Cough", result.getOtherSymptomsSCTTerm());
	        assertEquals("Observation1", result.getClinicalObservation());
	        assertEquals("12345", result.getClinicalObservationSctcode());
	        assertEquals("Finding1", result.getSignificantFindings());
	        assertEquals("67890", result.getSignificantFindingsSctcode());
	    }

	    @Test
	    public void testGetBenClinicalObservations_NoSymptoms() {
	        wrapperAncFindings.setOtherSymptoms(null);

	        BenClinicalObservations result = commonDoctorServiceImpl.getBenClinicalObservations(wrapperAncFindings);

	        assertNotNull(result);
	        assertNull(result.getOtherSymptomsSCTCode());
	        assertNull(result.getOtherSymptomsSCTTerm());
	    }

	    @Test
	    public void testGetBenClinicalObservations_EmptySymptoms() {
	        wrapperAncFindings.setOtherSymptoms("");

	        BenClinicalObservations result = commonDoctorServiceImpl.getBenClinicalObservations(wrapperAncFindings);

	        assertNotNull(result);
	        assertEquals("", result.getOtherSymptomsSCTCode());
	        assertEquals("", result.getOtherSymptomsSCTTerm());
	    }

	    @Test
	    public void testGetBenClinicalObservations_SnomedServiceReturnsNull() {
	        when(snomedServiceImpl.findSnomedCTRecordFromTerm("fever")).thenReturn(null);
	        when(snomedServiceImpl.findSnomedCTRecordFromTerm("cough")).thenReturn(null);

	        BenClinicalObservations result = commonDoctorServiceImpl.getBenClinicalObservations(wrapperAncFindings);

	        assertNotNull(result);
	        assertEquals("N/A,N/A", result.getOtherSymptomsSCTCode());
	        assertEquals("N/A,N/A", result.getOtherSymptomsSCTTerm());
	    }
	    @Test
	    public void testGetDocWorkList_withData() {
	        // Arrange
	        List<Object[]> mockData = new ArrayList<>();
	        mockData.add(new Object[]{"data1", "data2"});
	        when(docWorkListRepo.getDocWorkList()).thenReturn(mockData);

	        // Act
	        String result = commonDoctorServiceImpl.getDocWorkList();

	        // Assert
	        String expectedJson = "[{\"0\":\"data1\",\"1\":\"data2\"}]";
	        assertEquals(expectedJson, result);
	        verify(docWorkListRepo, times(1)).getDocWorkList();
	    }

	    @Test
	    public void testGetDocWorkList_withNoData() {
	        // Arrange
	        List<Object[]> mockData = new ArrayList<>();
	        when(docWorkListRepo.getDocWorkList()).thenReturn(mockData);

	        // Act
	        String result = commonDoctorServiceImpl.getDocWorkList();

	        // Assert
	        String expectedJson = "[]";
	        assertEquals(expectedJson, result);
	        verify(docWorkListRepo, times(1)).getDocWorkList();
	    }
	    @Test
	    public void testGetDocWorkListNew_MMU() {
	        Integer providerServiceMapId = 1;
	        Integer serviceID = 2; // MMU service ID
	        Integer vanID = 1;

	        ArrayList<BeneficiaryFlowStatus> mockList = new ArrayList<>();
	        mockList.add(new BeneficiaryFlowStatus());
	        when(beneficiaryFlowStatusRepo.getDocWorkListNew(providerServiceMapId)).thenReturn(mockList);

	        String result = commonDoctorService.getDocWorkListNew(providerServiceMapId, serviceID, vanID);

	        assertNotNull(result);
	        assertTrue(result.contains("BeneficiaryFlowStatus"));
	        verify(beneficiaryFlowStatusRepo, times(1)).getDocWorkListNew(providerServiceMapId);
	    }

	    @Test
	    public void testGetDocWorkListNew_TC() {
	        Integer providerServiceMapId = 1;
	        Integer serviceID = 9; // TC service ID
	        Integer vanID = 1;

	        Calendar cal = Calendar.getInstance();
	        cal.add(Calendar.DAY_OF_YEAR, -7);
	        long sevenDaysAgo = cal.getTimeInMillis();

	        ArrayList<BeneficiaryFlowStatus> mockList = new ArrayList<>();
	        mockList.add(new BeneficiaryFlowStatus());
	        when(beneficiaryFlowStatusRepo.getDocWorkListNewTC(providerServiceMapId, new Timestamp(sevenDaysAgo), vanID)).thenReturn(mockList);

	        String result = commonDoctorService.getDocWorkListNew(providerServiceMapId, serviceID, vanID);

	        assertNotNull(result);
	        assertTrue(result.contains("BeneficiaryFlowStatus"));
	        verify(beneficiaryFlowStatusRepo, times(1)).getDocWorkListNewTC(providerServiceMapId, new Timestamp(sevenDaysAgo), vanID);
	    }

	    @Test
	    public void testGetDocWorkListNew_InvalidServiceID() {
	        Integer providerServiceMapId = 1;
	        Integer serviceID = 99; // Invalid service ID
	        Integer vanID = 1;

	        String result = commonDoctorService.getDocWorkListNew(providerServiceMapId, serviceID, vanID);

	        assertNotNull(result);
	        assertEquals("[]", result);
	        verify(beneficiaryFlowStatusRepo, times(0)).getDocWorkListNew(anyInt());
	        verify(beneficiaryFlowStatusRepo, times(0)).getDocWorkListNewTC(anyInt(), any(Timestamp.class), anyInt());
	    }
	    @Test
	    public void testGetDocWorkListNewFutureScheduledForTM_ServiceID9_NonEmptyList() {
	        when(beneficiaryFlowStatusRepo.getDocWorkListNewFutureScheduledTC(1, 1)).thenReturn(mockBeneficiaryFlowStatusList);

	        String response = commonDoctorServiceImpl.getDocWorkListNewFutureScheduledForTM(1, 9, 1);

	        assertEquals(new Gson().toJson(mockBeneficiaryFlowStatusList), response);
	        verify(beneficiaryFlowStatusRepo, times(1)).getDocWorkListNewFutureScheduledTC(1, 1);
	    }

	    @Test
	    public void testGetDocWorkListNewFutureScheduledForTM_ServiceID9_EmptyList() {
	        when(beneficiaryFlowStatusRepo.getDocWorkListNewFutureScheduledTC(1, 1)).thenReturn(new ArrayList<>());

	        String response = commonDoctorServiceImpl.getDocWorkListNewFutureScheduledForTM(1, 9, 1);

	        assertEquals(new Gson().toJson(new ArrayList<>()), response);
	        verify(beneficiaryFlowStatusRepo, times(1)).getDocWorkListNewFutureScheduledTC(1, 1);
	    }

	    @Test
	    public void testGetDocWorkListNewFutureScheduledForTM_ServiceIDNot9() {
	        String response = commonDoctorServiceImpl.getDocWorkListNewFutureScheduledForTM(1, 8, 1);

	        assertEquals(new Gson().toJson(new ArrayList<>()), response);
	        verify(beneficiaryFlowStatusRepo, never()).getDocWorkListNewFutureScheduledTC(anyInt(), anyInt());
	    }
	    @Test
	    public void testGetTCSpecialistWorkListNewForTMPatientApp_ValidInputs() {
	        Calendar cal = Calendar.getInstance();
	        cal.add(Calendar.DAY_OF_YEAR, -7);
	        long sevenDaysAgo = cal.getTimeInMillis();

	        when(beneficiaryFlowStatusRepo.getTCSpecialistWorkListNewPatientApp(providerServiceMapId, userID, new Timestamp(sevenDaysAgo), vanID))
	                .thenReturn(tcSpecialistWorkList);

	        String response = commonDoctorServiceImpl.getTCSpecialistWorkListNewForTMPatientApp(providerServiceMapId, userID, serviceID, vanID);

	        assertEquals(gson.toJson(tcSpecialistWorkList), response);
	        verify(beneficiaryFlowStatusRepo, times(1)).getTCSpecialistWorkListNewPatientApp(providerServiceMapId, userID, new Timestamp(sevenDaysAgo), vanID);
	    }

	    @Test
	    public void testGetTCSpecialistWorkListNewForTMPatientApp_NoResults() {
	        Calendar cal = Calendar.getInstance();
	        cal.add(Calendar.DAY_OF_YEAR, -7);
	        long sevenDaysAgo = cal.getTimeInMillis();

	        when(beneficiaryFlowStatusRepo.getTCSpecialistWorkListNewPatientApp(providerServiceMapId, userID, new Timestamp(sevenDaysAgo), vanID))
	                .thenReturn(new ArrayList<>());

	        String response = commonDoctorServiceImpl.getTCSpecialistWorkListNewForTMPatientApp(providerServiceMapId, userID, serviceID, vanID);

	        assertEquals(gson.toJson(new ArrayList<>()), response);
	        verify(beneficiaryFlowStatusRepo, times(1)).getTCSpecialistWorkListNewPatientApp(providerServiceMapId, userID, new Timestamp(sevenDaysAgo), vanID);
	    }

	    @Test
	    public void testGetTCSpecialistWorkListNewForTMPatientApp_InvalidServiceID() {
	        serviceID = 1; // Invalid service ID

	        String response = commonDoctorServiceImpl.getTCSpecialistWorkListNewForTMPatientApp(providerServiceMapId, userID, serviceID, vanID);

	        assertEquals(gson.toJson(new ArrayList<>()), response);
	        verify(beneficiaryFlowStatusRepo, never()).getTCSpecialistWorkListNewPatientApp(anyInt(), anyInt(), any(Timestamp.class), anyInt());
	    }
	    @Test
	    public void testGetTCSpecialistWorkListNewForTM_ServiceID9() {
	        when(beneficiaryFlowStatusRepo.getTCSpecialistWorkListNew(providerServiceMapId, userID, new Timestamp(sevenDaysAgo)))
	                .thenReturn(tcSpecialistWorkList);

	        String response = commonDoctorServiceImpl.getTCSpecialistWorkListNewForTM(providerServiceMapId, userID, serviceID);

	        assertNotNull(response);
	        assertTrue(response.contains("BeneficiaryFlowStatus"));
	        verify(beneficiaryFlowStatusRepo, times(1)).getTCSpecialistWorkListNew(providerServiceMapId, userID, new Timestamp(sevenDaysAgo));
	    }

	    @Test
	    public void testGetTCSpecialistWorkListNewForTM_ServiceIDNot9() {
	        serviceID = 1;

	        String response = commonDoctorServiceImpl.getTCSpecialistWorkListNewForTM(providerServiceMapId, userID, serviceID);

	        assertNotNull(response);
	        assertEquals("[]", response);
	        verify(beneficiaryFlowStatusRepo, never()).getTCSpecialistWorkListNew(anyInt(), anyInt(), any(Timestamp.class));
	    }

	    @Test
	    public void testGetTCSpecialistWorkListNewForTM_EmptyList() {
	        when(beneficiaryFlowStatusRepo.getTCSpecialistWorkListNew(providerServiceMapId, userID, new Timestamp(sevenDaysAgo)))
	                .thenReturn(new ArrayList<>());

	        String response = commonDoctorServiceImpl.getTCSpecialistWorkListNewForTM(providerServiceMapId, userID, serviceID);

	        assertNotNull(response);
	        assertEquals("[]", response);
	        verify(beneficiaryFlowStatusRepo, times(1)).getTCSpecialistWorkListNew(providerServiceMapId, userID, new Timestamp(sevenDaysAgo));
	    }
	    @Test
	    public void testGetTCSpecialistWorkListNewFutureScheduledForTM() {
	        Integer providerServiceMapId = 1;
	        Integer userID = 1;
	        Integer serviceID = 9;

	        when(beneficiaryFlowStatusRepo.getTCSpecialistWorkListNewFutureScheduled(providerServiceMapId, userID))
	                .thenReturn((ArrayList<BeneficiaryFlowStatus>) tcSpecialistWorkListFutureScheduled);

	        String response = commonDoctorServiceImpl.getTCSpecialistWorkListNewFutureScheduledForTM(providerServiceMapId, userID, serviceID);

	        assertNotNull(response);
	        assertTrue(response.contains("\"beneficiaryRegID\":1"));
	        assertTrue(response.contains("\"visitCode\":1"));

	        verify(beneficiaryFlowStatusRepo, times(1)).getTCSpecialistWorkListNewFutureScheduled(providerServiceMapId, userID);
	    }

	    @Test
	    public void testGetTCSpecialistWorkListNewFutureScheduledForTM_EmptyList() {
	        Integer providerServiceMapId = 1;
	        Integer userID = 1;
	        Integer serviceID = 9;

	        when(beneficiaryFlowStatusRepo.getTCSpecialistWorkListNewFutureScheduled(providerServiceMapId, userID))
	                .thenReturn(new ArrayList<>());

	        String response = commonDoctorServiceImpl.getTCSpecialistWorkListNewFutureScheduledForTM(providerServiceMapId, userID, serviceID);

	        assertNotNull(response);
	        assertEquals("[]", response);

	        verify(beneficiaryFlowStatusRepo, times(1)).getTCSpecialistWorkListNewFutureScheduled(providerServiceMapId, userID);
	    }

	    @Test
	    public void testGetTCSpecialistWorkListNewFutureScheduledForTM_NullServiceID() {
	        Integer providerServiceMapId = 1;
	        Integer userID = 1;
	        Integer serviceID = null;

	        String response = commonDoctorServiceImpl.getTCSpecialistWorkListNewFutureScheduledForTM(providerServiceMapId, userID, serviceID);

	        assertNotNull(response);
	        assertEquals("[]", response);

	        verify(beneficiaryFlowStatusRepo, never()).getTCSpecialistWorkListNewFutureScheduled(anyInt(), anyInt());
	    }
	    @Test
	    public void testFetchBenPreviousSignificantFindings() {
	        Long beneficiaryRegID = 1L;
	        ArrayList<Object[]> previousSignificantFindings = new ArrayList<>();
	        previousSignificantFindings.add(new Object[]{new Date(), "Finding1"});
	        previousSignificantFindings.add(new Object[]{new Date(), "Finding2"});

	        when(benClinicalObservationsRepo.getPreviousSignificantFindings(beneficiaryRegID)).thenReturn(previousSignificantFindings);

	        String response = commonDoctorServiceImpl.fetchBenPreviousSignificantFindings(beneficiaryRegID);

	        Map<String, Object> expectedResponse = new HashMap<>();
	        ArrayList<BenClinicalObservations> clinicalObservations = new ArrayList<>();
	        for (Object[] obj : previousSignificantFindings) {
	            BenClinicalObservations clinicalObservation = new BenClinicalObservations((String) obj[1], (Date) obj[0]);
	            clinicalObservations.add(clinicalObservation);
	        }
	        expectedResponse.put("findings", clinicalObservations);

	        assertEquals(gson.toJson(expectedResponse), response);
	        verify(benClinicalObservationsRepo, times(1)).getPreviousSignificantFindings(beneficiaryRegID);
	    }
	    @Test
	    public void testSaveBenReferDetailsWithReferralReasonList() throws IEMRException {
	        JsonObject jsonObject = new JsonObject();
	        jsonObject.addProperty("referralReasonList", "reason1||reason2");
	        jsonObject.addProperty("refrredToAdditionalServiceList", "service1||service2");

	        BenReferDetails benReferDetails = new BenReferDetails();
	        benReferDetails.setReferralReasonList(new String[]{"reason1", "reason2"});
	        benReferDetails.setRefrredToAdditionalServiceList(new String[]{"service1", "service2"});

	        when(InputMapper.gson().fromJson(jsonObject, BenReferDetails.class)).thenReturn(benReferDetails);

	        Long result = commonDoctorServiceImpl.saveBenReferDetails(jsonObject, true);

	        assertEquals(1L, result);
	        verify(benReferDetailsRepo, times(1)).save(benReferDetails);
	    }

	    @Test
	    public void testSaveBenReferDetailsWithoutReferralReasonList() throws IEMRException {
	        JsonObject jsonObject = new JsonObject();
	        jsonObject.addProperty("refrredToAdditionalServiceList", "service1||service2");

	        BenReferDetails benReferDetails = new BenReferDetails();
	        benReferDetails.setRefrredToAdditionalServiceList(new String[]{"service1", "service2"});

	        when(InputMapper.gson().fromJson(jsonObject, BenReferDetails.class)).thenReturn(benReferDetails);

	        Long result = commonDoctorServiceImpl.saveBenReferDetails(jsonObject, false);

	        assertEquals(1L, result);
	        verify(benReferDetailsRepo, times(1)).save(benReferDetails);
	    }

	    @Test
	    public void testSaveBenReferDetailsWithEmptyReferralReasonList() throws IEMRException {
	        JsonObject jsonObject = new JsonObject();
	        jsonObject.addProperty("referralReasonList", "");

	        BenReferDetails benReferDetails = new BenReferDetails();
	        benReferDetails.setReferralReasonList(new String[]{});

	        when(InputMapper.gson().fromJson(jsonObject, BenReferDetails.class)).thenReturn(benReferDetails);

	        Long result = commonDoctorServiceImpl.saveBenReferDetails(jsonObject, true);

	        assertEquals(1L, result);
	        verify(benReferDetailsRepo, times(1)).save(benReferDetails);
	    }

	    @Test
	    public void testSaveBenReferDetailsWithAdditionalServiceList() throws IEMRException {
	        JsonObject jsonObject = new JsonObject();
	        jsonObject.addProperty("refrredToAdditionalServiceList", "service1||service2");

	        BenReferDetails benReferDetails = new BenReferDetails();
	        benReferDetails.setRefrredToAdditionalServiceList(new String[]{"service1", "service2"});

	        when(InputMapper.gson().fromJson(jsonObject, BenReferDetails.class)).thenReturn(benReferDetails);

	        Long result = commonDoctorServiceImpl.saveBenReferDetails(jsonObject, true);

	        assertEquals(1L, result);
	        verify(benReferDetailsRepo, times(1)).save(benReferDetails);
	    }

	    @Test
	    public void testSaveBenReferDetailsWithEmptyAdditionalServiceList() throws IEMRException {
	        JsonObject jsonObject = new JsonObject();
	        jsonObject.addProperty("refrredToAdditionalServiceList", "");

	        BenReferDetails benReferDetails = new BenReferDetails();
	        benReferDetails.setRefrredToAdditionalServiceList(new String[]{});

	        when(InputMapper.gson().fromJson(jsonObject, BenReferDetails.class)).thenReturn(benReferDetails);

	        Long result = commonDoctorServiceImpl.saveBenReferDetails(jsonObject, true);

	        assertEquals(1L, result);
	        verify(benReferDetailsRepo, times(1)).save(benReferDetails);
	    } 

	    @Test
	    public void testGetFindingsDetails() {
	        // Mocking the repository methods
	        ArrayList<Object[]> clinicalObservationsList = new ArrayList<>();
	        clinicalObservationsList.add(new Object[]{"2023-01-01", "Observation1"});
	        clinicalObservationsList.add(new Object[]{"2023-01-02", "Observation2"});

	        ArrayList<Object[]> chiefComplaintsList = new ArrayList<>();
	        chiefComplaintsList.add(new Object[]{"2023-01-01", "Complaint1"});
	        chiefComplaintsList.add(new Object[]{"2023-01-02", "Complaint2"});

	        when(benClinicalObservationsRepo.getFindingsData(beneficiaryRegID, visitCode)).thenReturn(clinicalObservationsList);
	        when(benChiefComplaintRepo.getBenChiefComplaints(beneficiaryRegID, visitCode)).thenReturn(chiefComplaintsList);

	        // Call the method under test
	        String response = commonDoctorServiceImpl.getFindingsDetails(beneficiaryRegID, visitCode);

	        // Deserialize the response
	        WrapperAncFindings findings = new Gson().fromJson(response, WrapperAncFindings.class);

	        // Assertions
	        assertNotNull(findings);
	        assertEquals(2, findings.getClinicalObservationsList().size());
	        assertEquals(2, findings.getChiefComplaintsList().size());
	        assertEquals("Observation1", findings.getClinicalObservationsList().get(0).getClinicalObservation());
	        assertEquals("Complaint1", findings.getChiefComplaintsList().get(0).getChiefComplaint());
	    }
	    @Test
	    public void testGetInvestigationDetails() {
	        when(labTestOrderDetailRepo.getLabTestOrderDetails(beneficiaryRegID, visitCode)).thenReturn(labTestOrders);

	        String response = commonDoctorServiceImpl.getInvestigationDetails(beneficiaryRegID, visitCode);

	        WrapperBenInvestigationANC expectedWrapper = new WrapperBenInvestigationANC();
	        expectedWrapper.setLabTestOrderDetails(Arrays.asList(
	                new WrapperBenInvestigationANC.LabTestOrderDetail("Test1", "Result1"),
	                new WrapperBenInvestigationANC.LabTestOrderDetail("Test2", "Result2")
	        ));
	        String expectedResponse = new Gson().toJson(expectedWrapper);

	        assertEquals(expectedResponse, response);
	        verify(labTestOrderDetailRepo, times(1)).getLabTestOrderDetails(beneficiaryRegID, visitCode);
	    }
	   
	    @Test
	    public void testGetPrescribedDrugs() {
	        // Arrange
	        List<Object[]> mockData = new ArrayList<>();
	        mockData.add(new Object[]{"Drug1", "Dosage1", "Frequency1"});
	        mockData.add(new Object[]{"Drug2", "Dosage2", "Frequency2"});

	        when(prescribedDrugDetailRepo.getBenPrescribedDrugDetails(beneficiaryRegID, visitCode)).thenReturn(mockData);

	        // Act
	        String response = commonDoctorServiceImpl.getPrescribedDrugs(beneficiaryRegID, visitCode);

	        // Assert
	        assertNotNull(response);
	        assertTrue(response.contains("Drug1"));
	        assertTrue(response.contains("Drug2"));

	        verify(prescribedDrugDetailRepo, times(1)).getBenPrescribedDrugDetails(beneficiaryRegID, visitCode);
	    }
	   
	    @Test
	    public void testGetReferralDetails_NonEmptyList() {
	        when(benReferDetailsRepo.findByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode))
	                .thenReturn(benReferDetailsList);

	        String response = commonDoctorServiceImpl.getReferralDetails(beneficiaryRegID, visitCode, rrList);

	        assertNotNull(response);
	        assertTrue(response.contains("Reason1"));
	        assertTrue(response.contains("Service1"));
	        verify(benReferDetailsRepo, times(1)).findByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode);
	    }

	    @Test
	    public void testGetReferralDetails_EmptyList() {
	        when(benReferDetailsRepo.findByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode))
	                .thenReturn(new ArrayList<>());

	        String response = commonDoctorServiceImpl.getReferralDetails(beneficiaryRegID, visitCode, rrList);

	        assertNotNull(response);
	        assertTrue(response.contains("{}"));
	        verify(benReferDetailsRepo, times(1)).findByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode);
	    }

	    @Test
	    public void testGetReferralDetails_rrListTrue() {
	        when(benReferDetailsRepo.findByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode))
	                .thenReturn(benReferDetailsList);

	        String response = commonDoctorServiceImpl.getReferralDetails(beneficiaryRegID, visitCode, true);

	        assertNotNull(response);
	        assertTrue(response.contains("Reason1"));
	        assertTrue(response.contains("Service1"));
	        verify(benReferDetailsRepo, times(1)).findByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode);
	    }

	    @Test
	    public void testGetReferralDetails_rrListFalse() {
	        when(benReferDetailsRepo.findByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode))
	                .thenReturn(benReferDetailsList);

	        String response = commonDoctorServiceImpl.getReferralDetails(beneficiaryRegID, visitCode, false);

	        assertNotNull(response);
	        assertFalse(response.contains("Reason1"));
	        assertTrue(response.contains("Service1"));
	        verify(benReferDetailsRepo, times(1)).findByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode);
	    }
	   
	    @Test
	    public void testUpdateDocFindings_Success() {
	        when(benClinicalObservationsRepo.save(any(BenClinicalObservations.class))).thenReturn(benClinicalObservations);
	        when(benChiefComplaintRepo.saveAll(anyList())).thenReturn(benChiefComplaintList);

	        int result = commonDoctorServiceImpl.updateDocFindings(wrapperAncFindings);

	        assertEquals(1, result);
	        verify(benClinicalObservationsRepo, times(1)).save(any(BenClinicalObservations.class));
	        verify(benChiefComplaintRepo, times(1)).saveAll(anyList());
	    }

	    @Test
	    public void testUpdateDocFindings_ClinicalObservationFail() {
	        when(benClinicalObservationsRepo.save(any(BenClinicalObservations.class))).thenReturn(null);

	        int result = commonDoctorServiceImpl.updateDocFindings(wrapperAncFindings);

	        assertEquals(0, result);
	        verify(benClinicalObservationsRepo, times(1)).save(any(BenClinicalObservations.class));
	        verify(benChiefComplaintRepo, never()).saveAll(anyList());
	    }

	    @Test
	    public void testUpdateDocFindings_ChiefComplaintFail() {
	        when(benClinicalObservationsRepo.save(any(BenClinicalObservations.class))).thenReturn(benClinicalObservations);
	        when(benChiefComplaintRepo.saveAll(anyList())).thenReturn(new ArrayList<>());

	        int result = commonDoctorServiceImpl.updateDocFindings(wrapperAncFindings);

	        assertEquals(0, result);
	        verify(benClinicalObservationsRepo, times(1)).save(any(BenClinicalObservations.class));
	        verify(benChiefComplaintRepo, times(1)).saveAll(anyList());
	    }
	    @Test
	    public void testUpdateDoctorBenChiefComplaints_Success() {
	        when(benChiefComplaintRepo.saveAll(benChiefComplaintList)).thenReturn(benChiefComplaintList);

	        int result = commonDoctorServiceImpl.updateDoctorBenChiefComplaints(benChiefComplaintList);

	        assertEquals(benChiefComplaintList.size(), result);
	        verify(benChiefComplaintRepo, times(1)).saveAll(benChiefComplaintList);
	    }

	    @Test
	    public void testUpdateDoctorBenChiefComplaints_EmptyList() {
	        List<BenChiefComplaint> emptyList = new ArrayList<>();

	        int result = commonDoctorServiceImpl.updateDoctorBenChiefComplaints(emptyList);

	        assertEquals(1, result);
	        verify(benChiefComplaintRepo, never()).saveAll(emptyList);
	    }

	    @Test
	    public void testUpdateDoctorBenChiefComplaints_SaveFails() {
	        when(benChiefComplaintRepo.saveAll(benChiefComplaintList)).thenReturn(new ArrayList<>());

	        int result = commonDoctorServiceImpl.updateDoctorBenChiefComplaints(benChiefComplaintList);

	        assertEquals(0, result);
	        verify(benChiefComplaintRepo, times(1)).saveAll(benChiefComplaintList);
	    }
	    @Test
	    public void testUpdateBenClinicalObservations_NullObservations() {
	        Integer result = commonDoctorServiceImpl.updateBenClinicalObservations(null);
	        assertEquals(0, result);
	    }

	    @Test
	    public void testUpdateBenClinicalObservations_ProcessedNull() {
	        when(benClinicalObservationsRepo.getBenClinicalObservationStatus(anyLong(), anyLong())).thenReturn(null);

	        Integer result = commonDoctorServiceImpl.updateBenClinicalObservations(benClinicalObservations);

	        assertEquals(1, result);
	        verify(benClinicalObservationsRepo, times(1)).save(any(BenClinicalObservations.class));
	    }

	    @Test
	    public void testUpdateBenClinicalObservations_ProcessedNotNullAndEqualsN() {
	        when(benClinicalObservationsRepo.getBenClinicalObservationStatus(anyLong(), anyLong())).thenReturn("N");

	        Integer result = commonDoctorServiceImpl.updateBenClinicalObservations(benClinicalObservations);

	        assertEquals(1, result);
	        verify(benClinicalObservationsRepo, times(1)).save(any(BenClinicalObservations.class));
	    }

	    @Test
	    public void testUpdateBenClinicalObservations_ProcessedNotNullAndNotEqualsN() {
	        when(benClinicalObservationsRepo.getBenClinicalObservationStatus(anyLong(), anyLong())).thenReturn("Y");

	        Integer result = commonDoctorServiceImpl.updateBenClinicalObservations(benClinicalObservations);

	        assertEquals(1, result);
	        verify(benClinicalObservationsRepo, times(1)).save(any(BenClinicalObservations.class));
	    }

	    @Test
	    public void testUpdateBenClinicalObservations_RecordsAvailableGreaterThanZero() {
	        when(benClinicalObservationsRepo.getBenClinicalObservationStatus(anyLong(), anyLong())).thenReturn("Y");
	        when(benClinicalObservationsRepo.updateBenClinicalObservations(anyString(), anyString(), anyString(), anyString(), anyString(), anyBoolean(), anyString(), anyString(), anyLong(), anyLong(), anyString(), anyString())).thenReturn(1);

	        Integer result = commonDoctorServiceImpl.updateBenClinicalObservations(benClinicalObservations);

	        assertEquals(1, result);
	        verify(benClinicalObservationsRepo, times(1)).updateBenClinicalObservations(anyString(), anyString(), anyString(), anyString(), anyString(), anyBoolean(), anyString(), anyString(), anyLong(), anyLong(), anyString(), anyString());
	    }

	    @Test
	    public void testUpdateBenClinicalObservations_RecordsAvailableZero() {
	        when(benClinicalObservationsRepo.getBenClinicalObservationStatus(anyLong(), anyLong())).thenReturn(null);
	        when(benClinicalObservationsRepo.save(any(BenClinicalObservations.class))).thenReturn(benClinicalObservations);

	        Integer result = commonDoctorServiceImpl.updateBenClinicalObservations(benClinicalObservations);

	        assertEquals(1, result);
	        verify(benClinicalObservationsRepo, times(1)).save(any(BenClinicalObservations.class));
	    }
	   
	    @Test
	    public void testUpdateBenReferDetails_NullReferDetails() throws IEMRException {
	        when(inputMapper.gson().fromJson(any(JsonObject.class), eq(BenReferDetails.class))).thenReturn(null);

	        Long result = commonDoctorServiceImpl.updateBenReferDetails(referObj, true);

	        assertEquals(1L, result);
	        verify(benReferDetailsRepo, never()).save(any(BenReferDetails.class));
	    }

	    @Test
	    public void testUpdateBenReferDetails_WithRRList() throws IEMRException {
	        BenReferDetails referDetails = new BenReferDetails();
	        referDetails.setReferralReasonList(new String[]{"reason1", "reason2"});
	        when(inputMapper.gson().fromJson(any(JsonObject.class), eq(BenReferDetails.class))).thenReturn(referDetails);

	        Long result = commonDoctorServiceImpl.updateBenReferDetails(referObj, true);

	        assertEquals(1L, result);
	        verify(benReferDetailsRepo, times(1)).save(any(BenReferDetails.class));
	    }

	    @Test
	    public void testUpdateBenReferDetails_WithoutRRList() throws IEMRException {
	        BenReferDetails referDetails = new BenReferDetails();
	        referDetails.setReferralReasonList(new String[]{"reason1", "reason2"});
	        when(inputMapper.gson().fromJson(any(JsonObject.class), eq(BenReferDetails.class))).thenReturn(referDetails);

	        Long result = commonDoctorServiceImpl.updateBenReferDetails(referObj, false);

	        assertEquals(1L, result);
	        verify(benReferDetailsRepo, times(1)).save(any(BenReferDetails.class));
	    }
	   
	    @Test
	    public void testUpdateBenFlowtableAfterDocDataSave_TestAndMedicinePrescribed() throws IEMRException {
	        when(foetalMonitorRepo.getFoetalMonitorDetailsByFlowId(anyLong())).thenReturn(new ArrayList<>());

	        int result = commonDoctorServiceImpl.updateBenFlowtableAfterDocDataSave(commonUtilityClass, true, true, tcRequestOBJ);

	        assertEquals(1, result);
	        verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowAfterDocDataFromSpecialist(anyLong(), anyLong(), anyLong(), anyLong(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort());
	    }

	    @Test
	    public void testUpdateBenFlowtableAfterDocDataSave_NoTestButMedicinePrescribed() throws IEMRException {
	        when(foetalMonitorRepo.getFoetalMonitorDetailsByFlowId(anyLong())).thenReturn(new ArrayList<>());

	        int result = commonDoctorServiceImpl.updateBenFlowtableAfterDocDataSave(commonUtilityClass, false, true, tcRequestOBJ);

	        assertEquals(1, result);
	        verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowAfterDocDataFromSpecialist(anyLong(), anyLong(), anyLong(), anyLong(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort());
	    }

	    @Test
	    public void testUpdateBenFlowtableAfterDocDataSave_TestButNoMedicinePrescribed() throws IEMRException {
	        when(foetalMonitorRepo.getFoetalMonitorDetailsByFlowId(anyLong())).thenReturn(new ArrayList<>());

	        int result = commonDoctorServiceImpl.updateBenFlowtableAfterDocDataSave(commonUtilityClass, true, false, tcRequestOBJ);

	        assertEquals(1, result);
	        verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowAfterDocDataFromSpecialist(anyLong(), anyLong(), anyLong(), anyLong(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort());
	    }

	    @Test
	    public void testUpdateBenFlowtableAfterDocDataSave_NoTestNoMedicinePrescribed() throws IEMRException {
	        when(foetalMonitorRepo.getFoetalMonitorDetailsByFlowId(anyLong())).thenReturn(new ArrayList<>());

	        int result = commonDoctorServiceImpl.updateBenFlowtableAfterDocDataSave(commonUtilityClass, false, false, tcRequestOBJ);

	        assertEquals(1, result);
	        verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowAfterDocDataFromSpecialist(anyLong(), anyLong(), anyLong(), anyLong(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort());
	    }

	    @Test
	    public void testUpdateBenFlowtableAfterDocDataSave_IsSpecialistTrue() throws IEMRException {
	        commonUtilityClass.setIsSpecialist(true);
	        when(foetalMonitorRepo.getFoetalMonitorDetailsByFlowId(anyLong())).thenReturn(new ArrayList<>());

	        int result = commonDoctorServiceImpl.updateBenFlowtableAfterDocDataSave(commonUtilityClass, true, true, tcRequestOBJ);

	        assertEquals(1, result);
	        verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowAfterDocDataFromSpecialist(anyLong(), anyLong(), anyLong(), anyLong(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort());
	    }

	    @Test
	    public void testUpdateBenFlowtableAfterDocDataSave_TcRequestOBJNotNull() throws IEMRException {
	        when(foetalMonitorRepo.getFoetalMonitorDetailsByFlowId(anyLong())).thenReturn(new ArrayList<>());

	        int result = commonDoctorServiceImpl.updateBenFlowtableAfterDocDataSave(commonUtilityClass, true, true, tcRequestOBJ);

	        assertEquals(1, result);
	        verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowAfterDocDataFromSpecialist(anyLong(), anyLong(), anyLong(), anyLong(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort());
	    }
	    @Test
	    public void testUpdateBenFlowtableAfterDocDataUpdate_TestAndMedicinePrescribed() throws Exception {
	        when(foetalMonitorRepo.getFoetalMonitorDetailsByFlowId(anyLong())).thenReturn(new ArrayList<>());

	        int result = commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(commonUtilityClass, true, true, tcRequestOBJ);

	        assertEquals(1, result);
	        verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowAfterDocDataUpdateTCSpecialist(anyLong(), anyLong(), anyLong(), anyLong(), anyShort(), anyShort(), anyShort(), anyShort(), anyInt(), any(Timestamp.class), anyShort());
	    }

	    @Test
	    public void testUpdateBenFlowtableAfterDocDataUpdate_NoTestButMedicinePrescribed() throws Exception {
	        when(foetalMonitorRepo.getFoetalMonitorDetailsByFlowId(anyLong())).thenReturn(new ArrayList<>());

	        int result = commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(commonUtilityClass, false, true, tcRequestOBJ);

	        assertEquals(1, result);
	        verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowAfterDocDataUpdateTCSpecialist(anyLong(), anyLong(), anyLong(), anyLong(), anyShort(), anyShort(), anyShort(), anyShort(), anyInt(), any(Timestamp.class), anyShort());
	    }

	    @Test
	    public void testUpdateBenFlowtableAfterDocDataUpdate_TestButNoMedicinePrescribed() throws Exception {
	        when(foetalMonitorRepo.getFoetalMonitorDetailsByFlowId(anyLong())).thenReturn(new ArrayList<>());

	        int result = commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(commonUtilityClass, true, false, tcRequestOBJ);

	        assertEquals(1, result);
	        verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowAfterDocDataUpdateTCSpecialist(anyLong(), anyLong(), anyLong(), anyLong(), anyShort(), anyShort(), anyShort(), anyShort(), anyInt(), any(Timestamp.class), anyShort());
	    }

	    @Test
	    public void testUpdateBenFlowtableAfterDocDataUpdate_NoTestNoMedicinePrescribed() throws Exception {
	        when(foetalMonitorRepo.getFoetalMonitorDetailsByFlowId(anyLong())).thenReturn(new ArrayList<>());

	        int result = commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(commonUtilityClass, false, false, tcRequestOBJ);

	        assertEquals(1, result);
	        verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowAfterDocDataUpdateTCSpecialist(anyLong(), anyLong(), anyLong(), anyLong(), anyShort(), anyShort(), anyShort(), anyShort(), anyInt(), any(Timestamp.class), anyShort());
	    }

	    @Test
	    public void testUpdateBenFlowtableAfterDocDataUpdate_IsSpecialistTrue() throws Exception {
	        commonUtilityClass.setIsSpecialist(true);
	        when(foetalMonitorRepo.getFoetalMonitorDetailsByFlowId(anyLong())).thenReturn(new ArrayList<>());

	        int result = commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(commonUtilityClass, true, true, tcRequestOBJ);

	        assertEquals(1, result);
	        verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowAfterDocDataUpdateTCSpecialist(anyLong(), anyLong(), anyLong(), anyLong(), anyShort(), anyShort(), anyShort(), anyShort(), anyInt(), any(Timestamp.class), anyShort());
	    }

	    @Test
	    public void testUpdateBenFlowtableAfterDocDataUpdate_TcRequestOBJNotNull() throws Exception {
	        when(foetalMonitorRepo.getFoetalMonitorDetailsByFlowId(anyLong())).thenReturn(new ArrayList<>());

	        int result = commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(commonUtilityClass, true, true, tcRequestOBJ);

	        assertEquals(1, result);
	        verify(commonBenStatusFlowServiceImpl, times(1)).updateBenFlowAfterDocDataUpdateTCSpecialist(anyLong(), anyLong(), anyLong(), anyLong(), anyShort(), anyShort(), anyShort(), anyShort(), anyInt(), any(Timestamp.class), anyShort());
	    }

	   
	    @Test
	    public void testDeletePrescribedMedicine_NullJsonObject() {
	        String result = commonDoctorServiceImpl.deletePrescribedMedicine(null);
	        assertNull(result);
	        verify(prescribedDrugDetailRepo, never()).deletePrescribedmedicine(anyLong());
	    }

	    @Test
	    public void testDeletePrescribedMedicine_NoIdKey() {
	        String result = commonDoctorServiceImpl.deletePrescribedMedicine(invalidJsonObject);
	        assertNull(result);
	        verify(prescribedDrugDetailRepo, never()).deletePrescribedmedicine(anyLong());
	    }

	    @Test
	    public void testDeletePrescribedMedicine_SuccessfulDeletion() {
	        when(prescribedDrugDetailRepo.deletePrescribedmedicine(anyLong())).thenReturn(1);

	        String result = commonDoctorServiceImpl.deletePrescribedMedicine(validJsonObject);

	        assertEquals("record deleted successfully", result);
	        verify(prescribedDrugDetailRepo, times(1)).deletePrescribedmedicine(anyLong());
	    }

	    @Test
	    public void testDeletePrescribedMedicine_UnsuccessfulDeletion() {
	        when(prescribedDrugDetailRepo.deletePrescribedmedicine(anyLong())).thenReturn(0);

	        String result = commonDoctorServiceImpl.deletePrescribedMedicine(validJsonObject);

	        assertNull(result);
	        verify(prescribedDrugDetailRepo, times(1)).deletePrescribedmedicine(anyLong());
	    }
	   
	    @Test
	    public void testCallTmForSpecialistSlotBook_Success() {
	        JsonObject jsonResponse = new JsonObject();
	        jsonResponse.addProperty("statusCode", 200);
	        ResponseEntity<String> responseEntity = ResponseEntity.ok(jsonResponse.toString());

	        when(restTemplate.exchange(any(String.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
	                .thenReturn(responseEntity);

	        int result = commonDoctorServiceImpl.callTmForSpecialistSlotBook(tcSpecialistSlotBookingRequestOBJ, authorization);

	        assertEquals(1, result);
	    }

	    @Test
	    public void testCallTmForSpecialistSlotBook_Failure() {
	        JsonObject jsonResponse = new JsonObject();
	        jsonResponse.addProperty("statusCode", 400);
	        ResponseEntity<String> responseEntity = ResponseEntity.ok(jsonResponse.toString());

	        when(restTemplate.exchange(any(String.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
	                .thenReturn(responseEntity);

	        int result = commonDoctorServiceImpl.callTmForSpecialistSlotBook(tcSpecialistSlotBookingRequestOBJ, authorization);

	        assertEquals(0, result);
	    }

	    @Test
	    public void testCallTmForSpecialistSlotBook_Exception() {
	        when(restTemplate.exchange(any(String.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
	                .thenThrow(new RuntimeException("Exception occurred"));

	        int result = commonDoctorServiceImpl.callTmForSpecialistSlotBook(tcSpecialistSlotBookingRequestOBJ, authorization);

	        assertEquals(0, result);
	    }
	    @Test
	    public void testCreateTMPrescriptionSms_VisitCategory678() throws IEMRException {
	        List<PrescribedDrugDetail> prescribedDrugs = new ArrayList<>();
	        prescribedDrugs.add(new PrescribedDrugDetail());
	        when(prescribedDrugDetailRepo.getPrescriptionDetails(1L)).thenReturn(prescribedDrugs);
	        when(prescriptionDetailRepo.getProvisionalDiagnosis(anyLong(), anyLong())).thenReturn(new ArrayList<>());
	        when(sMSGatewayServiceImpl.smsSenderGateway2(anyString(), anyList(), anyString(), anyLong(), anyString(), anyList())).thenReturn(1);

	        commonDoctorServiceImpl.createTMPrescriptionSms(commonUtilityClass);

	        verify(prescribedDrugDetailRepo, times(1)).getPrescriptionDetails(1L);
	        verify(prescriptionDetailRepo, times(1)).getProvisionalDiagnosis(anyLong(), anyLong());
	        verify(sMSGatewayServiceImpl, times(1)).smsSenderGateway2(anyString(), anyList(), anyString(), anyLong(), anyString(), anyList());
	    }

	    @Test
	    public void testCreateTMPrescriptionSms_VisitCategory3() throws IEMRException {
	        commonUtilityClass.setVisitCategoryID(3);
	        List<PrescribedDrugDetail> prescribedDrugs = new ArrayList<>();
	        prescribedDrugs.add(new PrescribedDrugDetail());
	        when(prescribedDrugDetailRepo.getPrescriptionDetails(1L)).thenReturn(prescribedDrugs);
	        when(NCDCareDiagnosisRepo.getNCDcondition(anyLong(), anyLong())).thenReturn(new ArrayList<>());
	        when(sMSGatewayServiceImpl.smsSenderGateway2(anyString(), anyList(), anyString(), anyLong(), anyString(), anyList())).thenReturn(1);

	        commonDoctorServiceImpl.createTMPrescriptionSms(commonUtilityClass);

	        verify(prescribedDrugDetailRepo, times(1)).getPrescriptionDetails(1L);
	        verify(NCDCareDiagnosisRepo, times(1)).getNCDcondition(anyLong(), anyLong());
	        verify(sMSGatewayServiceImpl, times(1)).smsSenderGateway2(anyString(), anyList(), anyString(), anyLong(), anyString(), anyList());
	    }

	    @Test
	    public void testCreateTMPrescriptionSms_VisitCategory5() throws IEMRException {
	        commonUtilityClass.setVisitCategoryID(5);
	        List<PrescribedDrugDetail> prescribedDrugs = new ArrayList<>();
	        prescribedDrugs.add(new PrescribedDrugDetail());
	        when(prescribedDrugDetailRepo.getPrescriptionDetails(1L)).thenReturn(prescribedDrugs);
	        when(pNCDiagnosisRepo.getProvisionalDiagnosis(anyLong(), anyLong())).thenReturn(new ArrayList<>());
	        when(sMSGatewayServiceImpl.smsSenderGateway2(anyString(), anyList(), anyString(), anyLong(), anyString(), anyList())).thenReturn(1);

	        commonDoctorServiceImpl.createTMPrescriptionSms(commonUtilityClass);

	        verify(prescribedDrugDetailRepo, times(1)).getPrescriptionDetails(1L);
	        verify(pNCDiagnosisRepo, times(1)).getProvisionalDiagnosis(anyLong(), anyLong());
	        verify(sMSGatewayServiceImpl, times(1)).smsSenderGateway2(anyString(), anyList(), anyString(), anyLong(), anyString(), anyList());
	    }

	    @Test
	    public void testCreateTMPrescriptionSms_ExceptionDuringFetching() throws IEMRException {
	        when(prescribedDrugDetailRepo.getPrescriptionDetails(1L)).thenThrow(new RuntimeException("Exception during fetching"));

	        assertDoesNotThrow(() -> commonDoctorServiceImpl.createTMPrescriptionSms(commonUtilityClass));

	        verify(prescribedDrugDetailRepo, times(1)).getPrescriptionDetails(1L);
	        verify(sMSGatewayServiceImpl, times(0)).smsSenderGateway2(anyString(), anyList(), anyString(), anyLong(), anyString(), anyList());
	    }

	    @Test
	    public void testCreateTMPrescriptionSms_ExceptionDuringSendingSMS() throws IEMRException {
	        List<PrescribedDrugDetail> prescribedDrugs = new ArrayList<>();
	        prescribedDrugs.add(new PrescribedDrugDetail());
	        when(prescribedDrugDetailRepo.getPrescriptionDetails(1L)).thenReturn(prescribedDrugs);
	        when(prescriptionDetailRepo.getProvisionalDiagnosis(anyLong(), anyLong())).thenReturn(new ArrayList<>());
	        when(sMSGatewayServiceImpl.smsSenderGateway2(anyString(), anyList(), anyString(), anyLong(), anyString(), anyList())).thenThrow(new RuntimeException("Exception during sending SMS"));

	        assertDoesNotThrow(() -> commonDoctorServiceImpl.createTMPrescriptionSms(commonUtilityClass));

	        verify(prescribedDrugDetailRepo, times(1)).getPrescriptionDetails(1L);
	        verify(prescriptionDetailRepo, times(1)).getProvisionalDiagnosis(anyLong(), anyLong());
	        verify(sMSGatewayServiceImpl, times(1)).smsSenderGateway2(anyString(), anyList(), anyString(), anyLong(), anyString(), anyList());
	    }

	    @Test
	    public void testGetFoetalMonitorData_WithValidData() {
	        List<FoetalMonitor> foetalMonitorList = new ArrayList<>();
	        FoetalMonitor foetalMonitor = new FoetalMonitor();
	        foetalMonitorList.add(foetalMonitor);

	        when(foetalMonitorRepo.getFoetalMonitorDetailsForCaseRecord(beneficiaryRegID, visitCode)).thenReturn(foetalMonitorList);

	        String response = commonDoctorServiceImpl.getFoetalMonitorData(beneficiaryRegID, visitCode);

	        assertNotNull(response);
	        assertTrue(response.contains("[]")); // Adjust this based on the actual JSON structure
	        verify(foetalMonitorRepo, times(1)).getFoetalMonitorDetailsForCaseRecord(beneficiaryRegID, visitCode);
	    }

	    @Test
	    public void testGetFoetalMonitorData_WithEmptyData() {
	        List<FoetalMonitor> foetalMonitorList = new ArrayList<>();

	        when(foetalMonitorRepo.getFoetalMonitorDetailsForCaseRecord(beneficiaryRegID, visitCode)).thenReturn(foetalMonitorList);

	        String response = commonDoctorServiceImpl.getFoetalMonitorData(beneficiaryRegID, visitCode);

	        assertNotNull(response);
	        assertEquals("[]", response);
	        verify(foetalMonitorRepo, times(1)).getFoetalMonitorDetailsForCaseRecord(beneficiaryRegID, visitCode);
	    }

	    @Test
	    public void testGetFoetalMonitorData_WithException() {
	        when(foetalMonitorRepo.getFoetalMonitorDetailsForCaseRecord(beneficiaryRegID, visitCode)).thenThrow(new RuntimeException("Database error"));

	        assertThrows(RuntimeException.class, () -> {
	            commonDoctorServiceImpl.getFoetalMonitorData(beneficiaryRegID, visitCode);
	        });

	        verify(foetalMonitorRepo, times(1)).getFoetalMonitorDetailsForCaseRecord(beneficiaryRegID, visitCode);
	    }
	    
	  
	  
	}

