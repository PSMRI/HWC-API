package com.iemr.hwc.service.anc;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.google.gson.Gson;
import com.ibm.icu.text.SimpleDateFormat;
import com.iemr.hwc.data.anc.ANCCareDetails;
import com.iemr.hwc.data.anc.ANCWomenVaccineDetail;
import com.iemr.hwc.data.anc.SysObstetricExamination;
import com.iemr.hwc.data.anc.WrapperAncImmunization;
import com.iemr.hwc.data.quickConsultation.LabTestOrderDetail;
import com.iemr.hwc.repo.nurse.anc.ANCCareRepo;
import com.iemr.hwc.repo.nurse.anc.ANCWomenVaccineRepo;
import com.iemr.hwc.repo.nurse.anc.BenAdherenceRepo;
import com.iemr.hwc.repo.nurse.anc.SysObstetricExaminationRepo;
import com.iemr.hwc.repo.quickConsultation.LabTestOrderDetailRepo;

import jakarta.mail.internet.ParseException;
public class TestANCNurseServiceImpl {
	
	

	@ExtendWith(MockitoExtension.class)
	
	    @Mock
	    private ANCCareRepo ancCareRepo;
	  @Mock
	    private ANCWomenVaccineRepo ancWomenVaccineRepo;
	  @Mock
	    private LabTestOrderDetailRepo labTestOrderDetailRepo;

	    

	   @Mock
	    private BenAdherenceRepo benAdherenceRepo;

	    @Mock
	    private SysObstetricExaminationRepo sysObstetricExaminationRepo;

	  

	   
	  
	  

	    @InjectMocks
	    private ANCNurseServiceImpl ancNurseService;

	    private ANCCareDetails ancCareDetails;

	    private WrapperAncImmunization wrapperAncImmunization;
	    private ANCNurseServiceImpl ancNurseServiceImpl;

	    private SysObstetricExamination sysObstetricExamination;
	  

	    @BeforeEach
	    public void setUp() {
	        ancCareDetails = new ANCCareDetails();
	        ancCareDetails.setID(1L);
	        wrapperAncImmunization = new WrapperAncImmunization();
	        wrapperAncImmunization.setBeneficiaryRegID(1L);
	        wrapperAncImmunization.setBenVisitID(1L);
	        wrapperAncImmunization.setVisitCode(1L);
	        wrapperAncImmunization.setProviderServiceMapID(1L);
	        wrapperAncImmunization.setVanID(1L);
	        wrapperAncImmunization.setParkingPlaceID(1L);
	        wrapperAncImmunization.setCreatedBy("testUser");
	        wrapperAncImmunization.settT1ID(1L);
	        wrapperAncImmunization.settT2ID(2L);
	        wrapperAncImmunization.settT3ID(3L);
	        wrapperAncImmunization.settT_1Status("Completed");
	        wrapperAncImmunization.settT_2Status("Completed");
	        wrapperAncImmunization.settT_3Status("Completed");
	        wrapperAncImmunization.setDateReceivedForTT_1("2023-01-01T00:00:00");
	        wrapperAncImmunization.setDateReceivedForTT_2("2023-02-01T00:00:00");
	        wrapperAncImmunization.setDateReceivedForTT_3("2023-03-01T00:00:00");
	        wrapperAncImmunization.setFacilityNameOfTT_1("Facility1");
	        wrapperAncImmunization.setFacilityNameOfTT_2("Facility2");
	        wrapperAncImmunization.setFacilityNameOfTT_3("Facility3");
	        wrapperAncImmunization.setModifiedBy("testUser");
	    }

	    }

	    @Test
	    public void testSaveBeneficiaryANCDetails_Positive() {
	        when(ancCareRepo.save(any(ANCCareDetails.class))).thenReturn(ancCareDetails);

	        Long result = ancNurseService.saveBeneficiaryANCDetails(ancCareDetails);

	        assertNotNull(result);
	        assertEquals(1L, result);
	        verify(ancCareRepo, times(1)).save(any(ANCCareDetails.class));
	    }

	    @Test
	    public void testSaveBeneficiaryANCDetails_Negative() {
	        when(ancCareRepo.save(any(ANCCareDetails.class))).thenReturn(null);

	        Long result = ancNurseService.saveBeneficiaryANCDetails(ancCareDetails);

	        assertNull(result);
	        verify(ancCareRepo, times(1)).save(any(ANCCareDetails.class));
	    }

	    @Test
	    public void testSaveBeneficiaryANCDetails_InvalidID() {
	        ANCCareDetails invalidAncCareDetails = new ANCCareDetails();
	        invalidAncCareDetails.setID(-1L);
	        when(ancCareRepo.save(any(ANCCareDetails.class))).thenReturn(invalidAncCareDetails);

	        Long result = ancNurseService.saveBeneficiaryANCDetails(ancCareDetails);

	        assertNull(result);
	        verify(ancCareRepo, times(1)).save(any(ANCCareDetails.class));
	    }
	    @Test
	    public void testSaveANCWomenVaccineDetails_Success() {
	        List<ANCWomenVaccineDetail> vaccineDetails = new ArrayList<>();
	        ANCWomenVaccineDetail detail1 = new ANCWomenVaccineDetail();
	        detail1.setID(1L);
	        ANCWomenVaccineDetail detail2 = new ANCWomenVaccineDetail();
	        detail2.setID(2L);
	        vaccineDetails.add(detail1);
	        vaccineDetails.add(detail2);

	        when(ancWomenVaccineRepo.saveAll(vaccineDetails)).thenReturn(vaccineDetails);

	        Long result = ancNurseServiceImpl.saveANCWomenVaccineDetails(vaccineDetails);

	        assertNotNull(result);
	        assertEquals(2L, result);
	        verify(ancWomenVaccineRepo, times(1)).saveAll(vaccineDetails);
	    }

	    @Test
	    public void testSaveANCWomenVaccineDetails_EmptyList() {
	        List<ANCWomenVaccineDetail> vaccineDetails = new ArrayList<>();

	        when(ancWomenVaccineRepo.saveAll(vaccineDetails)).thenReturn(vaccineDetails);

	        Long result = ancNurseServiceImpl.saveANCWomenVaccineDetails(vaccineDetails);

	        assertNull(result);
	        verify(ancWomenVaccineRepo, times(1)).saveAll(vaccineDetails);
	    }

	    @Test
	    public void testSaveANCWomenVaccineDetails_RepoReturnsEmptyList() {
	        List<ANCWomenVaccineDetail> vaccineDetails = new ArrayList<>();
	        ANCWomenVaccineDetail detail1 = new ANCWomenVaccineDetail();
	        detail1.setID(1L);
	        vaccineDetails.add(detail1);

	        when(ancWomenVaccineRepo.saveAll(vaccineDetails)).thenReturn(new ArrayList<>());

	        Long result = ancNurseServiceImpl.saveANCWomenVaccineDetails(vaccineDetails);

	        assertNull(result);
	        verify(ancWomenVaccineRepo, times(1)).saveAll(vaccineDetails);
	    }
	    @Test
	    public void testSaveBenInvestigationFromDoc_NullInvestigationList() {
	        wrapperBenInvestigationANC.setLaboratoryList(null);

	        int result = ancNurseServiceImpl.saveBenInvestigationFromDoc(wrapperBenInvestigationANC);

	        assertEquals(1, result);
	        verify(labTestOrderDetailRepo, never()).saveAll(any());
	    }

	    @Test
	    public void testSaveBenInvestigationFromDoc_EmptyInvestigationList() {
	        wrapperBenInvestigationANC.setLaboratoryList(new ArrayList<>());

	        int result = ancNurseServiceImpl.saveBenInvestigationFromDoc(wrapperBenInvestigationANC);

	        assertEquals(1, result);
	        verify(labTestOrderDetailRepo, never()).saveAll(any());
	    }

	    @Test
	    public void testSaveBenInvestigationFromDoc_ValidInvestigationList() {
	        List<LabTestOrderDetail> labTestOrderDetails = new ArrayList<>();
	        LabTestOrderDetail labTestOrderDetail = new LabTestOrderDetail();
	        labTestOrderDetails.add(labTestOrderDetail);
	        wrapperBenInvestigationANC.setLaboratoryList(labTestOrderDetails);

	        when(labTestOrderDetailRepo.saveAll(any())).thenReturn(labTestOrderDetails);

	        int result = ancNurseServiceImpl.saveBenInvestigationFromDoc(wrapperBenInvestigationANC);

	        assertEquals(1, result);
	        verify(labTestOrderDetailRepo, times(1)).saveAll(any());
	    }
	    @Test
	    public void testSaveBenAncCareDetails_withValidDates() throws ParseException, java.text.ParseException {
	        ANCCareDetails ancCareDetails = new ANCCareDetails();
	        ancCareDetails.setLmpDate("2023-01-01T00:00:00");
	        ancCareDetails.setExpDelDt("2023-10-01T00:00:00");

	        ANCCareDetails savedDetails = new ANCCareDetails();
	        savedDetails.setID(1L);
	        when(ancCareRepo.save(any(ANCCareDetails.class))).thenReturn(savedDetails);

	        Long result = ancNurseService.saveBenAncCareDetails(ancCareDetails);

	        assertNotNull(result);
	        assertEquals(1L, result);
	        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01"), ancCareDetails.getLastMenstrualPeriod_LMP());
	        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2023-10-01"), ancCareDetails.getExpectedDateofDelivery());
	    }

	    @Test
	    public void testSaveBenAncCareDetails_withoutDates() throws ParseException, java.text.ParseException {
	        ANCCareDetails ancCareDetails = new ANCCareDetails();

	        ANCCareDetails savedDetails = new ANCCareDetails();
	        savedDetails.setID(1L);
	        when(ancCareRepo.save(any(ANCCareDetails.class))).thenReturn(savedDetails);

	        Long result = ancNurseService.saveBenAncCareDetails(ancCareDetails);

	        assertNotNull(result);
	        assertEquals(1L, result);
	    }

	    @Test
	    public void testSaveBenAncCareDetails_saveFails() throws ParseException, java.text.ParseException {
	        ANCCareDetails ancCareDetails = new ANCCareDetails();

	        when(ancCareRepo.save(any(ANCCareDetails.class))).thenReturn(null);

	        Long result = ancNurseService.saveBenAncCareDetails(ancCareDetails);

	        assertNull(result);
	    }
	    @Test
	    public void testGetANCWomenVaccineDetail_ValidInput() throws ParseException {
	        List<ANCWomenVaccineDetail> result = ancNurseServiceImpl.getANCWomenVaccineDetail(wrapperAncImmunization);
	        assertNotNull(result);
	        assertEquals(3, result.size());

	        ANCWomenVaccineDetail tt1 = result.get(0);
	        assertEquals("TT-1", tt1.getVaccineName());
	        assertEquals("Completed", tt1.getStatus());
	        assertEquals("Facility1", tt1.getReceivedFacilityName());

	        ANCWomenVaccineDetail tt2 = result.get(1);
	        assertEquals("TT-2", tt2.getVaccineName());
	        assertEquals("Completed", tt2.getStatus());
	        assertEquals("Facility2", tt2.getReceivedFacilityName());

	        ANCWomenVaccineDetail tt3 = result.get(2);
	        assertEquals("TT-Booster", tt3.getVaccineName());
	        assertEquals("Completed", tt3.getStatus());
	        assertEquals("Facility3", tt3.getReceivedFacilityName());
	    }

	    @Test
	    public void testGetANCWomenVaccineDetail_NullInput() throws ParseException {
	        List<ANCWomenVaccineDetail> result = ancNurseServiceImpl.getANCWomenVaccineDetail(null);
	        assertNotNull(result);
	        assertTrue(result.isEmpty());
	    }

	    @Test
	    public void testGetANCWomenVaccineDetail_MissingFields() throws ParseException {
	        wrapperAncImmunization.setDateReceivedForTT_1(null);
	        wrapperAncImmunization.setFacilityNameOfTT_1(null);

	        List<ANCWomenVaccineDetail> result = ancNurseServiceImpl.getANCWomenVaccineDetail(wrapperAncImmunization);
	        assertNotNull(result);
	        assertEquals(3, result.size());

	        ANCWomenVaccineDetail tt1 = result.get(0);
	        assertEquals("TT-1", tt1.getVaccineName());
	        assertEquals("Completed", tt1.getStatus());
	        assertNull(tt1.getReceivedDate());
	        assertNull(tt1.getReceivedFacilityName());
	    }

	    @Test
	    public void testSaveSysObstetricExamination() {
	        when(sysObstetricExaminationRepo.save(any(SysObstetricExamination.class))).thenReturn(sysObstetricExamination);

	        Long result = ancNurseServiceImpl.saveSysObstetricExamination(sysObstetricExamination);

	        assertNotNull(result);
	        assertEquals(1L, result);
	        verify(sysObstetricExaminationRepo, times(1)).save(any(SysObstetricExamination.class));
	    }
	    @Test
	    public void testGetSysObstetricExamination() {
	        Long benRegID = 1L;
	        Long visitCode = 1L;
	        SysObstetricExamination expectedExamination = new SysObstetricExamination();
	        expectedExamination.setID(1L);
	        expectedExamination.setBeneficiaryRegID(benRegID);
	        expectedExamination.setVisitCode(visitCode);
	        expectedExamination.setReasonsForHRPDB("Reason1||Reason2");

	        when(sysObstetricExaminationRepo.getSysObstetricExaminationData(benRegID, visitCode))
	                .thenReturn(expectedExamination);

	        SysObstetricExamination result = ancNurseServiceImpl.getSysObstetricExamination(benRegID, visitCode);

	        assertNotNull(result);
	        assertEquals(expectedExamination.getID(), result.getID());
	        assertEquals(expectedExamination.getBeneficiaryRegID(), result.getBeneficiaryRegID());
	        assertEquals(expectedExamination.getVisitCode(), result.getVisitCode());
	        assertArrayEquals(new String[]{"Reason1", "Reason2"}, result.getReasonsForHRP());

	        verify(sysObstetricExaminationRepo, times(1)).getSysObstetricExaminationData(benRegID, visitCode);
	    }

	    @Test
	    public void testGetSysObstetricExamination_NoReasonsForHRPDB() {
	        Long benRegID = 1L;
	        Long visitCode = 1L;
	        SysObstetricExamination expectedExamination = new SysObstetricExamination();
	        expectedExamination.setID(1L);
	        expectedExamination.setBeneficiaryRegID(benRegID);
	        expectedExamination.setVisitCode(visitCode);
	        expectedExamination.setReasonsForHRPDB(null);

	        when(sysObstetricExaminationRepo.getSysObstetricExaminationData(benRegID, visitCode))
	                .thenReturn(expectedExamination);

	        SysObstetricExamination result = ancNurseServiceImpl.getSysObstetricExamination(benRegID, visitCode);

	        assertNotNull(result);
	        assertEquals(expectedExamination.getID(), result.getID());
	        assertEquals(expectedExamination.getBeneficiaryRegID(), result.getBeneficiaryRegID());
	        assertEquals(expectedExamination.getVisitCode(), result.getVisitCode());
	        assertNull(result.getReasonsForHRP());

	        verify(sysObstetricExaminationRepo, times(1)).getSysObstetricExaminationData(benRegID, visitCode);
	    }
	    @Test
	    public void testGetANCCareDetails_ValidInput() {
	        Long beneficiaryRegID = 1L;
	        Long visitCode = 1L;

	        ArrayList<Object[]> resList = new ArrayList<>();
	        Object[] obj = new Object[1];
	        obj[0] = new ANCCareDetails();
	        resList.add(obj);

	        when(ancCareRepo.getANCCareDetails(beneficiaryRegID, visitCode)).thenReturn(resList);

	        String result = ancNurseServiceImpl.getANCCareDetails(beneficiaryRegID, visitCode);

	        ANCCareDetails expectedDetails = ANCCareDetails.getANCCareDetails(resList, 0);
	        String expectedJson = new Gson().toJson(expectedDetails);

	        assertEquals(expectedJson, result);
	        verify(ancCareRepo, times(1)).getANCCareDetails(beneficiaryRegID, visitCode);
	    }

	    @Test
	    public void testGetANCCareDetails_EmptyResult() {
	        Long beneficiaryRegID = 1L;
	        Long visitCode = 1L;

	        ArrayList<Object[]> resList = new ArrayList<>();

	        when(ancCareRepo.getANCCareDetails(beneficiaryRegID, visitCode)).thenReturn(resList);

	        String result = ancNurseServiceImpl.getANCCareDetails(beneficiaryRegID, visitCode);

	        ANCCareDetails expectedDetails = ANCCareDetails.getANCCareDetails(resList, 0);
	        String expectedJson = new Gson().toJson(expectedDetails);

	        assertEquals(expectedJson, result);
	        verify(ancCareRepo, times(1)).getANCCareDetails(beneficiaryRegID, visitCode);
	    }
	    @Test
	    public void testGetANCWomenVaccineDetails_ValidData() {
	        Long beneficiaryRegID = 1L;
	        Long visitCode = 1L;

	        List<Object[]> mockResultList = new ArrayList<>();
	        Object[] mockResult = new Object[] { /* mock data */ };
	        mockResultList.add(mockResult);

	        when(ancWomenVaccineRepo.getANCWomenVaccineDetails(beneficiaryRegID, visitCode)).thenReturn(mockResultList);

	        String result = ancNurseServiceImpl.getANCWomenVaccineDetails(beneficiaryRegID, visitCode);

	        WrapperAncImmunization expectedWrapper = ANCWomenVaccineDetail.getANCWomenVaccineDetails(mockResultList);
	        String expectedJson = new Gson().toJson(expectedWrapper);

	        assertEquals(expectedJson, result);
	        verify(ancWomenVaccineRepo, times(1)).getANCWomenVaccineDetails(beneficiaryRegID, visitCode);
	    }

	    @Test
	    public void testGetANCWomenVaccineDetails_EmptyData() {
	        Long beneficiaryRegID = 1L;
	        Long visitCode = 1L;

	        List<Object[]> mockResultList = new ArrayList<>();

	        when(ancWomenVaccineRepo.getANCWomenVaccineDetails(beneficiaryRegID, visitCode)).thenReturn(mockResultList);

	        String result = ancNurseServiceImpl.getANCWomenVaccineDetails(beneficiaryRegID, visitCode);

	        WrapperAncImmunization expectedWrapper = ANCWomenVaccineDetail.getANCWomenVaccineDetails(mockResultList);
	        String expectedJson = new Gson().toJson(expectedWrapper);

	        assertEquals(expectedJson, result);
	        verify(ancWomenVaccineRepo, times(1)).getANCWomenVaccineDetails(beneficiaryRegID, visitCode);
	    }

	    @Test
	    public void testGetANCWomenVaccineDetails_NullData() {
	        Long beneficiaryRegID = 1L;
	        Long visitCode = 1L;

	        when(ancWomenVaccineRepo.getANCWomenVaccineDetails(beneficiaryRegID, visitCode)).thenReturn(null);

	        String result = ancNurseServiceImpl.getANCWomenVaccineDetails(beneficiaryRegID, visitCode);

	        assertNull(result);
	        verify(ancWomenVaccineRepo, times(1)).getANCWomenVaccineDetails(beneficiaryRegID, visitCode);
	    }
	    @Test
	    public void testUpdateBenAdherenceDetails_StatusNotN() {
	        when(benAdherenceRepo.getBenAdherenceDetailsStatus(1L, 1L, 1L)).thenReturn("Y");
	        when(benAdherenceRepo.updateBenAdherence(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyLong())).thenReturn(1);

	        int result = ancNurseServiceImpl.updateBenAdherenceDetails(benAdherence);

	        assertEquals(1, result);
	        verify(benAdherenceRepo, times(1)).updateBenAdherence(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyLong());
	    }

	    @Test
	    public void testUpdateBenAdherenceDetails_StatusN() {
	        when(benAdherenceRepo.getBenAdherenceDetailsStatus(1L, 1L, 1L)).thenReturn("N");
	        when(benAdherenceRepo.updateBenAdherence(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyLong())).thenReturn(1);

	        int result = ancNurseServiceImpl.updateBenAdherenceDetails(benAdherence);

	        assertEquals(1, result);
	        verify(benAdherenceRepo, times(1)).updateBenAdherence(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyLong());
	    }

	    @Test
	    public void testUpdateBenAdherenceDetails_CorrectParameters() {
	        when(benAdherenceRepo.getBenAdherenceDetailsStatus(1L, 1L, 1L)).thenReturn("Y");
	        when(benAdherenceRepo.updateBenAdherence(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyLong())).thenReturn(1);

	        ancNurseServiceImpl.updateBenAdherenceDetails(benAdherence);

	        verify(benAdherenceRepo, times(1)).updateBenAdherence(eq("Drugs"), eq("Reason"), eq("Referral"), eq("ReferralReason"), eq("Progress"), eq("User"), eq("U"), eq(1L), eq(1L), eq(1L));
	    }
	    @Test
	    public void testUpdateBenAncCareDetails_Success() throws ParseException {
	        when(ancCareRepo.getBenANCCareDetailsStatus(anyLong(), anyLong())).thenReturn("U");
	        when(ancCareRepo.updateANCCareDetails(anyString(), anyString(), anyString(), any(Date.class), anyString(), anyString(), any(Date.class), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString(), anyString())).thenReturn(1);

	        int result = ancNurseServiceImpl.updateBenAncCareDetails(ancCareDetails);

	        assertEquals(1, result);
	        verify(ancCareRepo, times(1)).updateANCCareDetails(anyString(), anyString(), anyString(), any(Date.class), anyString(), anyString(), any(Date.class), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString(), anyString());
	    }

	    @Test
	    public void testUpdateBenAncCareDetails_NullValues() throws ParseException {
	        ancCareDetails.setLmpDate(null);
	        ancCareDetails.setExpDelDt(null);

	        when(ancCareRepo.getBenANCCareDetailsStatus(anyLong(), anyLong())).thenReturn("N");
	        when(ancCareRepo.updateANCCareDetails(anyString(), anyString(), anyString(), isNull(), anyString(), anyString(), isNull(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString(), anyString())).thenReturn(1);

	        int result = ancNurseServiceImpl.updateBenAncCareDetails(ancCareDetails);

	        assertEquals(1, result);
	        verify(ancCareRepo, times(1)).updateANCCareDetails(anyString(), anyString(), anyString(), isNull(), anyString(), anyString(), isNull(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString(), anyString());
	    }

	    @Test
	    public void testUpdateBenAncCareDetails_DateParsing() throws ParseException {
	        String lmpDate = "2023-01-01";
	        String expDelDt = "2023-10-01";
	        ancCareDetails.setLmpDate(lmpDate + "T00:00:00");
	        ancCareDetails.setExpDelDt(expDelDt + "T00:00:00");

	        Date expectedLmpDate = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(lmpDate).getTime());
	        Date expectedExpDelDt = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(expDelDt).getTime());

	        when(ancCareRepo.getBenANCCareDetailsStatus(anyLong(), anyLong())).thenReturn("N");
	        when(ancCareRepo.updateANCCareDetails(anyString(), anyString(), anyString(), eq(expectedLmpDate), anyString(), anyString(), eq(expectedExpDelDt), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString(), anyString())).thenReturn(1);

	        int result = ancNurseServiceImpl.updateBenAncCareDetails(ancCareDetails);

	        assertEquals(1, result);
	        verify(ancCareRepo, times(1)).updateANCCareDetails(anyString(), anyString(), anyString(), eq(expectedLmpDate), anyString(), anyString(), eq(expectedExpDelDt), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString(), anyString());
	    }

	    @Test
	    public void testUpdateBenAncCareDetails_StatusCode() throws ParseException {
	        when(ancCareRepo.getBenANCCareDetailsStatus(anyLong(), anyLong())).thenReturn("N");
	        when(ancCareRepo.updateANCCareDetails(anyString(), anyString(), anyString(), any(Date.class), anyString(), anyString(), any(Date.class), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString(), anyString())).thenReturn(0);

	        int result = ancNurseServiceImpl.updateBenAncCareDetails(ancCareDetails);

	        assertEquals(0, result);
	        verify(ancCareRepo, times(1)).updateANCCareDetails(anyString(), anyString(), anyString(), any(Date.class), anyString(), anyString(), any(Date.class), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString(), anyString());
	    }
	    @Test
	    public void testUpdateBenAncImmunizationDetails_Success() throws ParseException {
	        List<ANCWomenVaccineDetail> ancWomenVaccineDetailList = new ArrayList<>();
	        ANCWomenVaccineDetail detail = new ANCWomenVaccineDetail();
	        detail.setBeneficiaryRegID(1L);
	        detail.setVisitCode(1L);
	        detail.setVaccineName("TT-1");
	        ancWomenVaccineDetailList.add(detail);

	        when(ancWomenVaccineRepo.getBenANCWomenVaccineStatus(anyLong(), anyLong())).thenReturn(new ArrayList<>());
	        when(ancWomenVaccineRepo.updateANCImmunizationDetails(anyString(), any(Date.class), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString())).thenReturn(1);

	        int result = ancNurseServiceImpl.updateBenAncImmunizationDetails(wrapperAncImmunization);

	        assertEquals(1, result);
	        verify(ancWomenVaccineRepo, times(3)).updateANCImmunizationDetails(anyString(), any(Date.class), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString());
	    }

	    @Test
	    public void testUpdateBenAncImmunizationDetails_EmptyList() throws ParseException {
	        wrapperAncImmunization = new WrapperAncImmunization(); // Empty object

	        int result = ancNurseServiceImpl.updateBenAncImmunizationDetails(wrapperAncImmunization);

	        assertEquals(0, result);
	        verify(ancWomenVaccineRepo, never()).updateANCImmunizationDetails(anyString(), any(Date.class), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString());
	    }

	    @Test
	    public void testUpdateBenAncImmunizationDetails_NullList() throws ParseException {
	        wrapperAncImmunization = null; // Null object

	        int result = ancNurseServiceImpl.updateBenAncImmunizationDetails(wrapperAncImmunization);

	        assertEquals(0, result);
	        verify(ancWomenVaccineRepo, never()).updateANCImmunizationDetails(anyString(), any(Date.class), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString());
	    }

	    @Test
	    public void testUpdateBenAncImmunizationDetails_ProcessedStatus() throws ParseException {
	        List<ANCWomenVaccineDetail> ancWomenVaccineDetailList = new ArrayList<>();
	        ANCWomenVaccineDetail detail = new ANCWomenVaccineDetail();
	        detail.setBeneficiaryRegID(1L);
	        detail.setVisitCode(1L);
	        detail.setVaccineName("TT-1");
	        ancWomenVaccineDetailList.add(detail);

	        List<Object[]> statuses = new ArrayList<>();
	        statuses.add(new Object[]{"TT-1", "Completed"});
	        when(ancWomenVaccineRepo.getBenANCWomenVaccineStatus(anyLong(), anyLong())).thenReturn(statuses);
	        when(ancWomenVaccineRepo.updateANCImmunizationDetails(anyString(), any(Date.class), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString())).thenReturn(1);

	        int result = ancNurseServiceImpl.updateBenAncImmunizationDetails(wrapperAncImmunization);

	        assertEquals(1, result);
	        verify(ancWomenVaccineRepo, times(3)).updateANCImmunizationDetails(anyString(), any(Date.class), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString());
	    }
	    @Test
	    public void testUpdateSysObstetricExamination_StatusNotN() {
	        when(sysObstetricExaminationRepo.getBenObstetricExaminationStatus(1L, 1L)).thenReturn("U");
	        when(sysObstetricExaminationRepo.updateSysObstetricExamination(
	                anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(),
	                anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString()
	        )).thenReturn(1);

	        int result = ancNurseServiceImpl.updateSysObstetricExamination(obstetricExamination);

	        assertEquals(1, result);
	        verify(sysObstetricExaminationRepo, times(1)).updateSysObstetricExamination(
	                eq("30 cm"), eq("Normal"), eq("Good"), eq("Active"), eq("Present"), eq("140 bpm"), eq("Cephalic"),
	                eq("Vertex"), eq("None"), eq("30 cm"), eq("testUser"), eq("U"), eq(1L), eq(1L), eq("None"), eq("No"),
	                eq("No"), eq("No"), eq("None")
	        );
	    }

	    @Test
	    public void testUpdateSysObstetricExamination_StatusN() {
	        when(sysObstetricExaminationRepo.getBenObstetricExaminationStatus(1L, 1L)).thenReturn("N");
	        when(sysObstetricExaminationRepo.updateSysObstetricExamination(
	                anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(),
	                anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString()
	        )).thenReturn(1);

	        int result = ancNurseServiceImpl.updateSysObstetricExamination(obstetricExamination);

	        assertEquals(1, result);
	        verify(sysObstetricExaminationRepo, times(1)).updateSysObstetricExamination(
	                eq("30 cm"), eq("Normal"), eq("Good"), eq("Active"), eq("Present"), eq("140 bpm"), eq("Cephalic"),
	                eq("Vertex"), eq("None"), eq("30 cm"), eq("testUser"), eq("N"), eq(1L), eq(1L), eq("None"), eq("No"),
	                eq("No"), eq("No"), eq("None")
	        );
	    }

	    @Test
	    public void testUpdateSysObstetricExamination_NullExamination() {
	        int result = ancNurseServiceImpl.updateSysObstetricExamination(null);

	        assertEquals(0, result);
	        verify(sysObstetricExaminationRepo, times(0)).updateSysObstetricExamination(
	                anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(),
	                anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString()
	        );
	    }
	}
	
	
	
	
	
	
	
	
	

