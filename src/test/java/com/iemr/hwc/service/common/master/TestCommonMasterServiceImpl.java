package com.iemr.hwc.service.common.master;


	import static org.junit.jupiter.api.Assertions.assertEquals;
	import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
	import org.junit.jupiter.api.Test;
	import org.mockito.InjectMocks;
	import org.mockito.Mock;
	import org.mockito.MockitoAnnotations;

import com.iemr.hwc.data.neonatal.ImmunizationServiceVaccinationMaster;
import com.iemr.hwc.repo.neonatal.ImmunizationServiceDoseMasterRepo;
import com.iemr.hwc.repo.neonatal.ImmunizationServiceRouteMasterRepo;
import com.iemr.hwc.repo.neonatal.ImmunizationServiceVaccinationMasterRepo;
import com.iemr.hwc.repo.neonatal.SiteOfInjectionMasterRepo;
import com.iemr.hwc.service.family_planning.FamilyPlanningMasterService;
import com.iemr.hwc.service.neonatal.NeonatalMasterService;
import com.iemr.hwc.utils.exception.IEMRException;
	public class TestCommonMasterServiceImpl {


	    @Mock
	    private NurseMasterDataServiceImpl nurseMasterDataServiceImpl;
	    @Mock
	    private ANCMasterDataServiceImpl ancMasterDataServiceImpl;

	    

	    @Mock
	    private DoctorMasterDataServiceImpl doctorMasterDataServiceImpl;

	    @Mock
	    private NCDScreeningMasterServiceImpl ncdScreeningServiceImpl;

	    @Mock
	    private FamilyPlanningMasterService familyPlanningMasterService;

	    @Mock
	    private NeonatalMasterService neonatalMasterService;
	    @Mock
	    private ImmunizationServiceVaccinationMasterRepo immunizationServiceVaccinationMasterRepo;

	    @Mock
	    private ImmunizationServiceDoseMasterRepo immunizationServiceDoseMasterRepo;

	    @Mock
	    private ImmunizationServiceRouteMasterRepo immunizationServiceRouteMasterRepo;

	    @Mock
	    private SiteOfInjectionMasterRepo siteOfInjectionMasterRepo;

	    
	    

	   
	    @InjectMocks
	    private CommonMasterServiceImpl commonMasterServiceImpl;
	    private Integer visitCategoryID;
	    private Integer providerServiceMapID;
	    private String gender;
	    private Integer facilityID;
	    private Integer vanID;

	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    public void testGetVisitReasonAndCategories_Positive() {
	        // Arrange
	        String expected = "Expected Visit Reason and Categories";
	        when(nurseMasterDataServiceImpl.GetVisitReasonAndCategories()).thenReturn(expected);

	        // Act
	        String actual = commonMasterServiceImpl.getVisitReasonAndCategories();

	        // Assert
	        assertEquals(expected, actual);
	    }

	    @Test
	    public void testGetVisitReasonAndCategories_Negative() {
	        // Arrange
	        when(nurseMasterDataServiceImpl.GetVisitReasonAndCategories()).thenThrow(new RuntimeException("Exception occurred"));

	        // Act & Assert
	        assertThrows(RuntimeException.class, () -> {
	            commonMasterServiceImpl.getVisitReasonAndCategories();
	        });
	    }
	    @Test
	    public void testGetMasterDataForNurse_CancerScreening() throws IEMRException {
	        when(nurseMasterDataServiceImpl.getCancerScreeningMasterDataForNurse()).thenReturn("CancerScreeningData");

	        String result = commonMasterServiceImpl.getMasterDataForNurse(1, 1, "M");

	        assertEquals("CancerScreeningData", result);
	        verify(nurseMasterDataServiceImpl, times(1)).getCancerScreeningMasterDataForNurse();
	    }

	    @Test
	    public void testGetMasterDataForNurse_NCDScreening() throws IEMRException {
	        when(ncdScreeningServiceImpl.getNCDScreeningMasterData(2, 1, "M")).thenReturn("NCDScreeningData");

	        String result = commonMasterServiceImpl.getMasterDataForNurse(2, 1, "M");

	        assertEquals("NCDScreeningData", result);
	        verify(ncdScreeningServiceImpl, times(1)).getNCDScreeningMasterData(2, 1, "M");
	    }

	    @Test
	    public void testGetMasterDataForNurse_ANC() throws IEMRException {
	        when(ancMasterDataServiceImpl.getCommonNurseMasterDataForGenopdAncNcdcarePnc(4, 1, "M")).thenReturn("ANCData");

	        String result = commonMasterServiceImpl.getMasterDataForNurse(4, 1, "M");

	        assertEquals("ANCData", result);
	        verify(ancMasterDataServiceImpl, times(1)).getCommonNurseMasterDataForGenopdAncNcdcarePnc(4, 1, "M");
	    }

	    @Test
	    public void testGetMasterDataForNurse_InvalidVisitCategoryID() throws IEMRException {
	        String result = commonMasterServiceImpl.getMasterDataForNurse(99, 1, "M");

	        assertEquals("Invalid VisitCategoryID", result);
	    }

	    @Test
	    public void testGetMasterDataForNurse_NullVisitCategoryID() throws IEMRException {
	        String result = commonMasterServiceImpl.getMasterDataForNurse(null, 1, "M");

	        assertEquals("Invalid VisitCategoryID", result);
	    }
	    @Test
	    public void testGetMasterDataForDoctor_CancerScreening() {
	        visitCategoryID = 1;
	        when(doctorMasterDataServiceImpl.getCancerScreeningMasterDataForDoctor(providerServiceMapID))
	                .thenReturn("CancerScreeningData");

	        String result = commonMasterServiceImpl.getMasterDataForDoctor(visitCategoryID, providerServiceMapID, gender, facilityID, vanID);

	        assertEquals("CancerScreeningData", result);
	        verify(doctorMasterDataServiceImpl, times(1)).getCancerScreeningMasterDataForDoctor(providerServiceMapID);
	    }

	    @Test
	    public void testGetMasterDataForDoctor_NCDScreening() {
	        visitCategoryID = 2;
	        when(ancMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(visitCategoryID, providerServiceMapID, gender, facilityID, vanID))
	                .thenReturn("NCDScreeningData");

	        String result = commonMasterServiceImpl.getMasterDataForDoctor(visitCategoryID, providerServiceMapID, gender, facilityID, vanID);

	        assertEquals("NCDScreeningData", result);
	        verify(ancMasterDataServiceImpl, times(1)).getCommonDoctorMasterDataForGenopdAncNcdcarePnc(visitCategoryID, providerServiceMapID, gender, facilityID, vanID);
	    }

	    @Test
	    public void testGetMasterDataForDoctor_InvalidVisitCategoryID() {
	        visitCategoryID = 99;

	        String result = commonMasterServiceImpl.getMasterDataForDoctor(visitCategoryID, providerServiceMapID, gender, facilityID, vanID);

	        assertEquals("Invalid VisitCategoryID", result);
	    }

	    @Test
	    public void testGetMasterDataForDoctor_NullVisitCategoryID() {
	        visitCategoryID = null;

	        String result = commonMasterServiceImpl.getMasterDataForDoctor(visitCategoryID, providerServiceMapID, gender, facilityID, vanID);

	        assertEquals("Invalid VisitCategoryID", result);
	    }
	    @Test
	    public void testGetVaccineDetailsForCISID_ValidCISIDAndVisitCategoryID_NonEmptyList() throws IEMRException {
	        Integer CISID = 1;
	        Integer visitCategoryID = 1;

	        ImmunizationServiceVaccinationMaster vaccine = new ImmunizationServiceVaccinationMaster();
	        vaccine.setVaccinationID(1);
	        vaccine.setVaccineName("Vaccine1");

	        List<ImmunizationServiceVaccinationMaster> vaccineList = Arrays.asList(vaccine);

	        when(immunizationServiceVaccinationMasterRepo.findByCurrentImmunizationServiceIdAndDeletedAndVisitCategoryID(CISID, false, visitCategoryID))
	            .thenReturn(vaccineList);
	        when(immunizationServiceDoseMasterRepo.findByVaccinationIDAndDeletedOrderByDose(1, false))
	            .thenReturn(new ArrayList<>());
	        when(immunizationServiceRouteMasterRepo.findByVaccinationIDAndDeletedOrderByRoute(1, false))
	            .thenReturn(new ArrayList<>());
	        when(siteOfInjectionMasterRepo.findByVaccinationIDAndDeletedOrderBySiteofinjection(1, false))
	            .thenReturn(new ArrayList<>());

	        String response = commonMasterServiceImpl.getVaccineDetailsForCISID(CISID, visitCategoryID);

	        Map<String, List<Map<String, Object>>> expectedResponse = new HashMap<>();
	        List<Map<String, Object>> responseList = new ArrayList<>();
	        Map<String, Object> vaccineMap = new HashMap<>();
	        vaccineMap.put("vaccineId", 1);
	        vaccineMap.put("vaccine", "Vaccine1");
	        vaccineMap.put("dose", new ArrayList<>());
	        vaccineMap.put("route", new ArrayList<>());
	        vaccineMap.put("siteOfInjection", new ArrayList<>());
	        responseList.add(vaccineMap);
	        expectedResponse.put("vaccineList", responseList);

	        assertEquals(gson.toJson(expectedResponse), response);
	        verify(immunizationServiceVaccinationMasterRepo, times(1)).findByCurrentImmunizationServiceIdAndDeletedAndVisitCategoryID(CISID, false, visitCategoryID);
	    }

	    @Test
	    public void testGetVaccineDetailsForCISID_ValidCISIDAndVisitCategoryID_EmptyList() throws IEMRException {
	        Integer CISID = 1;
	        Integer visitCategoryID = 1;

	        when(immunizationServiceVaccinationMasterRepo.findByCurrentImmunizationServiceIdAndDeletedAndVisitCategoryID(CISID, false, visitCategoryID))
	            .thenReturn(new ArrayList<>());

	        String response = commonMasterServiceImpl.getVaccineDetailsForCISID(CISID, visitCategoryID);

	        Map<String, List<Map<String, Object>>> expectedResponse = new HashMap<>();
	        expectedResponse.put("vaccineList", new ArrayList<>());

	        assertEquals(gson.toJson(expectedResponse), response);
	        verify(immunizationServiceVaccinationMasterRepo, times(1)).findByCurrentImmunizationServiceIdAndDeletedAndVisitCategoryID(CISID, false, visitCategoryID);
	    }

	    @Test
	    public void testGetVaccineDetailsForCISID_NullCISIDOrVisitCategoryID() throws IEMRException {
	        Integer CISID = null;
	        Integer visitCategoryID = 1;

	        String response = commonMasterServiceImpl.getVaccineDetailsForCISID(CISID, visitCategoryID);

	        Map<String, List<Map<String, Object>>> expectedResponse = new HashMap<>();
	        expectedResponse.put("vaccineList", new ArrayList<>());

	        assertEquals(gson.toJson(expectedResponse), response);
	        verify(immunizationServiceVaccinationMasterRepo, never()).findByCurrentImmunizationServiceIdAndDeletedAndVisitCategoryID(any(), anyBoolean(), any());
	    }
	}
	

