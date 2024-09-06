package com.iemr.hwc.service.cancerScreening;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.lang.System.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gson.Gson;
import com.iemr.hwc.data.doctor.CancerAbdominalExamination;
import com.iemr.hwc.data.doctor.CancerBreastExamination;
import com.iemr.hwc.data.doctor.CancerGynecologicalExamination;
import com.iemr.hwc.data.doctor.CancerLymphNodeDetails;
import com.iemr.hwc.data.doctor.CancerOralExamination;
import com.iemr.hwc.data.doctor.CancerSignAndSymptoms;
import com.iemr.hwc.data.doctor.WrapperCancerExamImgAnotasn;
import com.iemr.hwc.data.doctor.WrapperCancerSymptoms;
import com.iemr.hwc.data.nurse.BenCancerVitalDetail;
import com.iemr.hwc.data.nurse.BenFamilyCancerHistory;
import com.iemr.hwc.data.nurse.BenObstetricCancerHistory;
import com.iemr.hwc.data.nurse.BenPersonalCancerDietHistory;
import com.iemr.hwc.data.nurse.BenPersonalCancerHistory;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.repo.doctor.CancerAbdominalExaminationRepo;
import com.iemr.hwc.repo.doctor.CancerBreastExaminationRepo;
import com.iemr.hwc.repo.doctor.CancerExaminationImageAnnotationRepo;
import com.iemr.hwc.repo.doctor.CancerGynecologicalExaminationRepo;
import com.iemr.hwc.repo.doctor.CancerLymphNodeExaminationRepo;
import com.iemr.hwc.repo.doctor.CancerOralExaminationRepo;
import com.iemr.hwc.repo.doctor.CancerSignAndSymptomsRepo;
import com.iemr.hwc.repo.nurse.BenCancerVitalDetailRepo;
import com.iemr.hwc.repo.nurse.BenFamilyCancerHistoryRepo;
import com.iemr.hwc.repo.nurse.BenObstetricCancerHistoryRepo;
import com.iemr.hwc.repo.nurse.BenPersonalCancerDietHistoryRepo;
import com.iemr.hwc.repo.nurse.BenPersonalCancerHistoryRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;

public class TestCSNurseServiceImpl {
	
	
	    @Mock
	    private BenFamilyCancerHistoryRepo benFamilyCancerHistoryRepo;
	    @Mock
	    private BenPersonalCancerHistoryRepo benPersonalCancerHistoryRepo;
	    @Mock
	    private BenPersonalCancerDietHistoryRepo benPersonalCancerDietHistoryRepo;
	    @Mock
	    private BenObstetricCancerHistoryRepo benObstetricCancerHistoryRepo;
	    @Mock
	    private BenCancerVitalDetailRepo benCancerVitalDetailRepo;
	    @Mock
	    private BenVisitDetailRepo benVisitDetailRepo;
	    @Mock
	    private CancerAbdominalExaminationRepo cancerAbdominalExaminationRepo;
	    @Mock
	    private CancerBreastExaminationRepo cancerBreastExaminationRepo;
	    @Mock
	    private CancerGynecologicalExaminationRepo cancerGynecologicalExaminationRepo;
	    @Mock
	    private CancerSignAndSymptomsRepo cancerSignAndSymptomsRepo;
	    @Mock
	    private CancerLymphNodeExaminationRepo cancerLymphNodeExaminationRepo;
	    @Mock
	    private CancerOralExaminationRepo cancerOralExaminationRepo;
	    @Mock
	    private CancerExaminationImageAnnotationRepo cancerExaminationImageAnnotationRepo;
	    private BenPersonalCancerDietHistory benPersonalCancerDietHistory;
	    @Mock
	    private Logger logger;
	    
	    @InjectMocks
	   
	    private CancerSignAndSymptoms cancerSignAndSymptoms;
	    @InjectMocks
	    private CSNurseServiceImpl csNurseServiceImpl;
	    private CSNurseServiceImpl csNurseService;
	    private List<BenFamilyCancerHistory> benFamilyCancerHistoryList;
	   
	    private BenObstetricCancerHistory benObstetricCancerHistory;
	    private BenPersonalCancerHistory benPersonalCancerHistory;
	    private BenCancerVitalDetail benCancerVitalDetail;
	    private Long benRegID;
	    private Long visitCode;
	    private List<CancerLymphNodeDetails> cancerLymphNodeDetailsList;
	    private CancerOralExamination cancerOralExamination;
	    private List<Object[]> benCancerFamilyHistoryDataArray;
	    private Long beneficiaryRegID;
	    private List<Object[]> benObstetricCancerHistoryList;
	    private CancerBreastExamination cancerBreastExamination;
	    private CancerAbdominalExamination cancerAbdominalExamination;
	    private List<WrapperCancerExamImgAnotasn> wrapperCancerExamImgAnotasnList;
	    private WrapperCancerSymptoms wrapperCancerSymptoms;
	    private List<CancerLymphNodeDetails> lymphNodeDetails;
	    private CancerGynecologicalExamination cancerGynecologicalExamination;



	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    public void testSaveBenFamilyCancerHistory_Success() {
	        // Arrange
	        BenFamilyCancerHistory history = new BenFamilyCancerHistory();
	        history.setFamilyMemberList(Arrays.asList("Father", "Mother"));
	        List<BenFamilyCancerHistory> historyList = Arrays.asList(history);

	        when(benFamilyCancerHistoryRepo.saveAll(anyList())).thenReturn(historyList);

	        // Act
	        int result = csNurseServiceImpl.saveBenFamilyCancerHistory(historyList);

	        // Assert
	        assertEquals(1, result);
	    }

	    @Test
	    public void testSaveBenFamilyCancerHistory_Failure() {
	        // Arrange
	        BenFamilyCancerHistory history = new BenFamilyCancerHistory();
	        history.setFamilyMemberList(Arrays.asList("Father", "Mother"));
	        List<BenFamilyCancerHistory> historyList = Arrays.asList(history);

	        when(benFamilyCancerHistoryRepo.saveAll(anyList())).thenReturn(new ArrayList<>());

	        // Act
	        int result = csNurseServiceImpl.saveBenFamilyCancerHistory(historyList);

	        // Assert
	        assertEquals(0, result);
	    }
	    @Test
	    public void testSaveBenPersonalCancerHistory_Success() {
	        // Arrange
	        BenPersonalCancerHistory benPersonalCancerHistory = new BenPersonalCancerHistory();
	        benPersonalCancerHistory.setID(1L);
	        when(benPersonalCancerHistoryRepo.save(any(BenPersonalCancerHistory.class))).thenReturn(benPersonalCancerHistory);

	        // Act
	        Long result = csNurseServiceImpl.saveBenPersonalCancerHistory(benPersonalCancerHistory);

	        // Assert
	        assertEquals(1L, result);
	    }

	    @Test
	    public void testSaveBenPersonalCancerHistory_Failure() {
	        // Arrange
	        BenPersonalCancerHistory benPersonalCancerHistory = new BenPersonalCancerHistory();
	        when(benPersonalCancerHistoryRepo.save(any(BenPersonalCancerHistory.class))).thenReturn(null);

	        // Act
	        Long result = csNurseServiceImpl.saveBenPersonalCancerHistory(benPersonalCancerHistory);

	        // Assert
	        assertNull(result);
	    }
	    @Test
	    public void testGetBenPersonalHistoryOBJ_Success() {
	        // Arrange
	        BenPersonalCancerHistory benPersonalCancerHistory = new BenPersonalCancerHistory();
	        benPersonalCancerHistory.setTypeOfTobaccoProductList(Arrays.asList("Cigarette", "Cigar"));

	        // Act
	        BenPersonalCancerHistory result = csNurseServiceImpl.getBenPersonalHistoryOBJ(benPersonalCancerHistory);

	        // Assert
	        assertEquals("Cigarette,Cigar,", result.getTypeOfTobaccoProduct());
	    }

	    @Test
	    public void testGetBenPersonalHistoryOBJ_EmptyList() {
	        // Arrange
	        BenPersonalCancerHistory benPersonalCancerHistory = new BenPersonalCancerHistory();
	        benPersonalCancerHistory.setTypeOfTobaccoProductList(Collections.emptyList());

	        // Act
	        BenPersonalCancerHistory result = csNurseServiceImpl.getBenPersonalHistoryOBJ(benPersonalCancerHistory);

	        // Assert
	        assertEquals("", result.getTypeOfTobaccoProduct());
	    }
	    @Test
	    public void testSaveBenPersonalCancerDietHistory_Success() {
	        benPersonalCancerDietHistory.setID(1L);
	        when(benPersonalCancerDietHistoryRepo.save(any(BenPersonalCancerDietHistory.class)))
	                .thenReturn(benPersonalCancerDietHistory);

	        Long result = csNurseService.saveBenPersonalCancerDietHistory(benPersonalCancerDietHistory);

	        assertNotNull(result);
	        assertEquals(1L, result);
	        verify(benPersonalCancerDietHistoryRepo, times(1)).save(any(BenPersonalCancerDietHistory.class));
	    }

	    @Test
	    public void testSaveBenPersonalCancerDietHistory_NullResponse() {
	        when(benPersonalCancerDietHistoryRepo.save(any(BenPersonalCancerDietHistory.class)))
	                .thenReturn(null);

	        Long result = csNurseService.saveBenPersonalCancerDietHistory(benPersonalCancerDietHistory);

	        assertNull(result);
	        verify(benPersonalCancerDietHistoryRepo, times(1)).save(any(BenPersonalCancerDietHistory.class));
	    }

	    @Test
	    public void testSaveBenPersonalCancerDietHistory_ProcessInput() {
	        when(benPersonalCancerDietHistoryRepo.save(any(BenPersonalCancerDietHistory.class)))
	                .thenReturn(benPersonalCancerDietHistory);

	        csNurseService.saveBenPersonalCancerDietHistory(benPersonalCancerDietHistory);

	        assertEquals("Olive Oil,Coconut Oil,", benPersonalCancerDietHistory.getTypeOfOilConsumed());
	        verify(benPersonalCancerDietHistoryRepo, times(1)).save(any(BenPersonalCancerDietHistory.class));
	    }  
	    @Test
	    public void testGetBenPersonalCancerDietOBJ_WithNonEmptyOilList() {
	        List<String> oilList = new ArrayList<>();
	        oilList.add("Olive Oil");
	        oilList.add("Coconut Oil");
	        benPersonalCancerDietHistory.setTypeOfOilConsumedList(oilList);

	        BenPersonalCancerDietHistory result = csNurseService.getBenPersonalCancerDietOBJ(benPersonalCancerDietHistory);

	        assertEquals("Olive Oil,Coconut Oil,", result.getTypeOfOilConsumed());
	    }

	    @Test
	    public void testGetBenPersonalCancerDietOBJ_WithEmptyOilList() {
	        List<String> oilList = new ArrayList<>();
	        benPersonalCancerDietHistory.setTypeOfOilConsumedList(oilList);

	        BenPersonalCancerDietHistory result = csNurseService.getBenPersonalCancerDietOBJ(benPersonalCancerDietHistory);

	        assertEquals("", result.getTypeOfOilConsumed());
	    }

	    @Test
	    public void testGetBenPersonalCancerDietOBJ_WithNullOilList() {
	        benPersonalCancerDietHistory.setTypeOfOilConsumedList(null);

	        BenPersonalCancerDietHistory result = csNurseService.getBenPersonalCancerDietOBJ(benPersonalCancerDietHistory);

	        assertNull(result.getTypeOfOilConsumed());
	    }
	    @Test
	    public void testSaveBenObstetricCancerHistory_Success() {
	        BenObstetricCancerHistory benObstetricCancerHistory = new BenObstetricCancerHistory();
	        benObstetricCancerHistory.setID(1L);

	        when(benObstetricCancerHistoryRepo.save(any(BenObstetricCancerHistory.class))).thenReturn(benObstetricCancerHistory);

	        Long result = csNurseService.saveBenObstetricCancerHistory(benObstetricCancerHistory);

	        assertNotNull(result);
	        assertEquals(1L, result);
	        verify(benObstetricCancerHistoryRepo, times(1)).save(benObstetricCancerHistory);
	    }

	    @Test
	    public void testSaveBenObstetricCancerHistory_Failure() {
	        BenObstetricCancerHistory benObstetricCancerHistory = new BenObstetricCancerHistory();

	        when(benObstetricCancerHistoryRepo.save(any(BenObstetricCancerHistory.class))).thenReturn(null);

	        Long result = csNurseService.saveBenObstetricCancerHistory(benObstetricCancerHistory);

	        assertNull(result);
	        verify(benObstetricCancerHistoryRepo, times(1)).save(benObstetricCancerHistory);
	    }
	    @Test
	    public void testSaveBenVitalDetail_NewRecord() {
	        when(benCancerVitalDetailRepo.getCancerVitalStatus(anyLong(), anyLong())).thenReturn(null);
	        when(benCancerVitalDetailRepo.save(any(BenCancerVitalDetail.class))).thenReturn(benCancerVitalDetail);

	        Long result = csNurseServiceImpl.saveBenVitalDetail(benCancerVitalDetail);

	        assertNotNull(result);
	        assertEquals(benCancerVitalDetail.getBeneficiaryRegID(), result);
	        verify(benCancerVitalDetailRepo, times(1)).save(any(BenCancerVitalDetail.class));
	    }

	    @Test
	    public void testSaveBenVitalDetail_UpdateRecord() {
	        when(benCancerVitalDetailRepo.getCancerVitalStatus(anyLong(), anyLong())).thenReturn("U");
	        when(benCancerVitalDetailRepo.updateBenCancerVitalDetail(anyLong(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyString(), anyLong(), anyLong(), anyString(), anyDouble(), anyString())).thenReturn(1);

	        Long result = csNurseServiceImpl.saveBenVitalDetail(benCancerVitalDetail);

	        assertNotNull(result);
	        assertEquals(benCancerVitalDetail.getBeneficiaryRegID(), result);
	        verify(benCancerVitalDetailRepo, times(1)).updateBenCancerVitalDetail(anyLong(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyString(), anyLong(), anyLong(), anyString(), anyDouble(), anyString());
	    }

	    @Test
	    public void testSaveBenVitalDetail_SaveFails() {
	        when(benCancerVitalDetailRepo.getCancerVitalStatus(anyLong(), anyLong())).thenReturn(null);
	        when(benCancerVitalDetailRepo.save(any(BenCancerVitalDetail.class))).thenReturn(null);

	        Long result = csNurseServiceImpl.saveBenVitalDetail(benCancerVitalDetail);

	        assertNull(result);
	        verify(benCancerVitalDetailRepo, times(1)).save(any(BenCancerVitalDetail.class));
	    }
	  
	    
	    @Test
	    public void testUpdateBeneficiaryFamilyCancerHistory_RecordsAvailable() {
	        // Mocking repository methods
	        when(benFamilyCancerHistoryRepo.getFamilyCancerHistoryStatus(anyLong(), anyLong()))
	                .thenReturn(new ArrayList<>());
	        when(benFamilyCancerHistoryRepo.deleteExistingFamilyRecord(anyLong(), anyString()))
	                .thenReturn(1);
	        when(benFamilyCancerHistoryRepo.saveAll(anyList()))
	                .thenReturn(benFamilyCancerHistoryList);

	        // Call the method
	        int response = csNurseService.updateBeneficiaryFamilyCancerHistory(benFamilyCancerHistoryList);

	        // Verify interactions and assert response
	        verify(benFamilyCancerHistoryRepo, times(1)).getFamilyCancerHistoryStatus(anyLong(), anyLong());
	        verify(benFamilyCancerHistoryRepo, times(1)).deleteExistingFamilyRecord(anyLong(), anyString());
	        verify(benFamilyCancerHistoryRepo, times(1)).saveAll(anyList());
	        assertEquals(1, response);
	    }

	    @Test
	    public void testUpdateBeneficiaryFamilyCancerHistory_NoRecordsAvailable() {
	        // Mocking repository methods
	        when(benFamilyCancerHistoryRepo.getFamilyCancerHistoryStatus(anyLong(), anyLong()))
	                .thenReturn(null);
	        when(benFamilyCancerHistoryRepo.saveAll(anyList()))
	                .thenReturn(benFamilyCancerHistoryList);

	        // Call the method
	        int response = csNurseService.updateBeneficiaryFamilyCancerHistory(benFamilyCancerHistoryList);

	        // Verify interactions and assert response
	        verify(benFamilyCancerHistoryRepo, times(1)).getFamilyCancerHistoryStatus(anyLong(), anyLong());
	        verify(benFamilyCancerHistoryRepo, times(0)).deleteExistingFamilyRecord(anyLong(), anyString());
	        verify(benFamilyCancerHistoryRepo, times(1)).saveAll(anyList());
	        assertEquals(1, response);
	    }

	    @Test
	    public void testUpdateBeneficiaryFamilyCancerHistory_Exception() {
	        // Mocking repository methods to throw an exception
	        when(benFamilyCancerHistoryRepo.getFamilyCancerHistoryStatus(anyLong(), anyLong()))
	                .thenThrow(new RuntimeException("Database error"));

	        // Call the method and assert exception
	        Exception exception = assertThrows(RuntimeException.class, () -> {
	            csNurseService.updateBeneficiaryFamilyCancerHistory(benFamilyCancerHistoryList);
	        });

	        // Verify interactions and assert exception message
	        verify(benFamilyCancerHistoryRepo, times(1)).getFamilyCancerHistoryStatus(anyLong(), anyLong());
	        assertEquals("Database error", exception.getMessage());
	    }
	    @Test
	    public void testUpdateBenObstetricCancerHistory_ProcessedNotNullAndNotN() {
	        when(benObstetricCancerHistory.getBeneficiaryRegID()).thenReturn("someRegID");
	        when(benObstetricCancerHistory.getVisitCode()).thenReturn("someVisitCode");
	        when(benObstetricCancerHistoryRepo.getObstetricCancerHistoryStatus("someRegID", "someVisitCode")).thenReturn("Y");

	        int result = csNurseServiceImpl.updateBenObstetricCancerHistory(benObstetricCancerHistory);

	        verify(benObstetricCancerHistory).setProcessed("U");
	        assertEquals(1, result);
	    }

	    @Test
	    public void testUpdateBenObstetricCancerHistory_ProcessedNullOrN() {
	        when(benObstetricCancerHistory.getBeneficiaryRegID()).thenReturn("someRegID");
	        when(benObstetricCancerHistory.getVisitCode()).thenReturn("someVisitCode");
	        when(benObstetricCancerHistoryRepo.getObstetricCancerHistoryStatus("someRegID", "someVisitCode")).thenReturn(null);

	        int result = csNurseServiceImpl.updateBenObstetricCancerHistory(benObstetricCancerHistory);

	        verify(benObstetricCancerHistory).setProcessed("N");
	        assertEquals(1, result);
	    }

	    @Test
	    public void testUpdateBenObstetricCancerHistory_ExceptionHandling() {
	        when(benObstetricCancerHistory.getBeneficiaryRegID()).thenReturn("someRegID");
	        when(benObstetricCancerHistory.getVisitCode()).thenReturn("someVisitCode");
	        when(benObstetricCancerHistoryRepo.getObstetricCancerHistoryStatus("someRegID", "someVisitCode")).thenThrow(new RuntimeException("Exception"));

	        int result = csNurseServiceImpl.updateBenObstetricCancerHistory(benObstetricCancerHistory);

	        assertEquals(0, result);
	    }
	    @Test
	    public void testUpdateBenObstetricHistory_Success() {
	        when(benObstetricCancerHistoryRepo.updateBenObstetricCancerHistory(
	                anyLong(), anyString(), anyBoolean(), anyInt(), anyInt(), anyBoolean(), anyBoolean(), anyBoolean(),
	                anyInt(), anyBoolean(), anyInt(), anyInt(), anyString(), anyBoolean(), anyBoolean(), anyInt(),
	                anyBoolean(), anyBoolean(), anyString(), anyLong(), anyLong(), anyString()))
	                .thenReturn(1);

	        int response = csNurseService.updateBenObstetricHistory(benObstetricCancerHistory);

	        assertEquals(1, response);
	        verify(benObstetricCancerHistoryRepo, times(1)).updateBenObstetricCancerHistory(
	                anyLong(), anyString(), anyBoolean(), anyInt(), anyInt(), anyBoolean(), anyBoolean(), anyBoolean(),
	                anyInt(), anyBoolean(), anyInt(), anyInt(), anyString(), anyBoolean(), anyBoolean(), anyInt(),
	                anyBoolean(), anyBoolean(), anyString(), anyLong(), anyLong(), anyString());
	    }

	    @Test
	    public void testUpdateBenObstetricHistory_Exception() {
	        when(benObstetricCancerHistoryRepo.updateBenObstetricCancerHistory(
	                anyLong(), anyString(), anyBoolean(), anyInt(), anyInt(), anyBoolean(), anyBoolean(), anyBoolean(),
	                anyInt(), anyBoolean(), anyInt(), anyInt(), anyString(), anyBoolean(), anyBoolean(), anyInt(),
	                anyBoolean(), anyBoolean(), anyString(), anyLong(), anyLong(), anyString()))
	                .thenThrow(new RuntimeException("Database error"));

	        Exception exception = assertThrows(RuntimeException.class, () -> {
	            csNurseService.updateBenObstetricHistory(benObstetricCancerHistory);
	        });

	        assertEquals("Database error", exception.getMessage());
	        verify(benObstetricCancerHistoryRepo, times(1)).updateBenObstetricCancerHistory(
	                anyLong(), anyString(), anyBoolean(), anyInt(), anyInt(), anyBoolean(), anyBoolean(), anyBoolean(),
	                anyInt(), anyBoolean(), anyInt(), anyInt(), anyString(), anyBoolean(), anyBoolean(), anyInt(),
	                anyBoolean(), anyBoolean(), anyString(), anyLong(), anyLong(), anyString());
	    }
	  
	    @Test
	    public void testUpdateBenPersonalCancerHistory_UpdateExistingRecord() {
	        when(benPersonalCancerHistoryRepo.getPersonalCancerHistoryStatus(1L, 1L)).thenReturn("Y");
	        when(benPersonalCancerHistoryRepo.updateBenPersonalCancerHistory(anyLong(), anyString(), anyInt(), anyInt(), anyString(), anyInt(), anyString(), anyString(), anyString(), anyInt(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(1);

	        int response = csNurseServiceImpl.updateBenPersonalCancerHistory(benPersonalCancerHistory);

	        assertEquals(1, response);
	        verify(benPersonalCancerHistoryRepo, times(1)).updateBenPersonalCancerHistory(anyLong(), anyString(), anyInt(), anyInt(), anyString(), anyInt(), anyString(), anyString(), anyString(), anyInt(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
	    }

	    @Test
	    public void testUpdateBenPersonalCancerHistory_SaveNewRecord() {
	        when(benPersonalCancerHistoryRepo.getPersonalCancerHistoryStatus(1L, 1L)).thenReturn(null);
	        when(benPersonalCancerHistoryRepo.save(any(BenPersonalCancerHistory.class))).thenReturn(benPersonalCancerHistory);

	        int response = csNurseServiceImpl.updateBenPersonalCancerHistory(benPersonalCancerHistory);

	        assertEquals(1, response);
	        verify(benPersonalCancerHistoryRepo, times(1)).save(any(BenPersonalCancerHistory.class));
	    }

	    @Test
	    public void testUpdateBenPersonalCancerHistory_ExceptionHandling() {
	        when(benPersonalCancerHistoryRepo.getPersonalCancerHistoryStatus(1L, 1L)).thenThrow(new RuntimeException("Database error"));

	        int response = csNurseServiceImpl.updateBenPersonalCancerHistory(benPersonalCancerHistory);

	        assertEquals(0, response);
	        verify(benPersonalCancerHistoryRepo, times(1)).getPersonalCancerHistoryStatus(1L, 1L);
	    }
	    @Test
	    public void testUpdateBenPersonalHistory_NullValues() {
	        BenPersonalCancerHistory benPersonalCancerHistory = new BenPersonalCancerHistory();
	        benPersonalCancerHistory.setProviderServiceMapID(null);
	        benPersonalCancerHistory.setTobaccoUse(null);
	        benPersonalCancerHistory.setStartAge_year(0);
	        benPersonalCancerHistory.setEndAge_year(0);
	        benPersonalCancerHistory.setTypeOfTobaccoProduct(null);
	        benPersonalCancerHistory.setQuantityPerDay(0);
	        benPersonalCancerHistory.setIsFilteredCigaerette(false);
	        benPersonalCancerHistory.setIsCigaretteExposure(false);
	        benPersonalCancerHistory.setIsBetelNutChewing(false);
	        benPersonalCancerHistory.setDurationOfBetelQuid(0);
	        benPersonalCancerHistory.setAlcoholUse(null);
	        benPersonalCancerHistory.setSsAlcoholUsed(null);
	        benPersonalCancerHistory.setFrequencyOfAlcoholUsed(null);
	        benPersonalCancerHistory.setModifiedBy(null);
	        benPersonalCancerHistory.setBeneficiaryRegID(null);
	        benPersonalCancerHistory.setVisitCode(null);
	        benPersonalCancerHistory.setProcessed(null);

	        when(benPersonalCancerHistoryRepo.updateBenPersonalCancerHistory(
	                anyLong(), anyString(), anyInt(), anyInt(), anyString(), anyInt(), anyBoolean(), anyBoolean(),
	                anyBoolean(), anyInt(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString()
	        )).thenReturn(0);

	        int response = csNurseService.updateBenPersonalHistory(benPersonalCancerHistory);

	        assertEquals(0, response);
	        verify(benPersonalCancerHistoryRepo, times(1)).updateBenPersonalCancerHistory(
	                anyLong(), anyString(), anyInt(), anyInt(), anyString(), anyInt(), anyBoolean(), anyBoolean(),
	                anyBoolean(), anyInt(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString()
	        );
	    }

	    @Test
	    public void testUpdateBenPersonalHistory_EmptyLists() {
	        BenPersonalCancerHistory benPersonalCancerHistory = new BenPersonalCancerHistory();
	        benPersonalCancerHistory.setTypeOfTobaccoProductList(new ArrayList<>());

	        when(benPersonalCancerHistoryRepo.updateBenPersonalCancerHistory(
	                anyLong(), anyString(), anyInt(), anyInt(), anyString(), anyInt(), anyBoolean(), anyBoolean(),
	                anyBoolean(), anyInt(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString()
	        )).thenReturn(0);

	        int response = csNurseService.updateBenPersonalHistory(benPersonalCancerHistory);

	        assertEquals(0, response);
	        verify(benPersonalCancerHistoryRepo, times(1)).updateBenPersonalCancerHistory(
	                anyLong(), anyString(), anyInt(), anyInt(), anyString(), anyInt(), anyBoolean(), anyBoolean(),
	                anyBoolean(), anyInt(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString()
	        );
	    }

	    @Test
	    public void testUpdateBenPersonalHistory_Exception() {
	        BenPersonalCancerHistory benPersonalCancerHistory = new BenPersonalCancerHistory();
	        benPersonalCancerHistory.setProviderServiceMapID(1L);
	        benPersonalCancerHistory.setTobaccoUse("Yes");
	        benPersonalCancerHistory.setStartAge_year(20);
	        benPersonalCancerHistory.setEndAge_year(30);
	        benPersonalCancerHistory.setTypeOfTobaccoProduct("Cigarette");
	        benPersonalCancerHistory.setQuantityPerDay(5);
	        benPersonalCancerHistory.setIsFilteredCigaerette(true);
	        benPersonalCancerHistory.setIsCigaretteExposure(false);
	        benPersonalCancerHistory.setIsBetelNutChewing(false);
	        benPersonalCancerHistory.setDurationOfBetelQuid(0);
	        benPersonalCancerHistory.setAlcoholUse("Occasionally");
	        benPersonalCancerHistory.setSsAlcoholUsed("Beer");
	        benPersonalCancerHistory.setFrequencyOfAlcoholUsed("Weekly");
	        benPersonalCancerHistory.setModifiedBy("User");
	        benPersonalCancerHistory.setBeneficiaryRegID(1L);
	        benPersonalCancerHistory.setVisitCode(1L);
	        benPersonalCancerHistory.setProcessed("N");

	        when(benPersonalCancerHistoryRepo.updateBenPersonalCancerHistory(
	                anyLong(), anyString(), anyInt(), anyInt(), anyString(), anyInt(), anyBoolean(), anyBoolean(),
	                anyBoolean(), anyInt(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString()
	        )).thenThrow(new RuntimeException("Database error"));

	        Exception exception = assertThrows(RuntimeException.class, () -> {
	            csNurseService.updateBenPersonalHistory(benPersonalCancerHistory);
	        });

	        assertEquals("Database error", exception.getMessage());
	        verify(benPersonalCancerHistoryRepo, times(1)).updateBenPersonalCancerHistory(
	                anyLong(), anyString(), anyInt(), anyInt(), anyString(), anyInt(), anyBoolean(), anyBoolean(),
	                anyBoolean(), anyInt(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString()
	        );
	    }
	    @Test
	    public void testUpdateBenPersonalCancerDietHistory_Positive() {
	        when(benPersonalCancerDietHistoryRepo.getPersonalCancerDietHistoryStatus(anyString(), anyString()))
	                .thenReturn("Y");

	        int response = csNurseServiceImpl.updateBenPersonalCancerDietHistory(benPersonalCancerDietHistory);

	        assertEquals(1, response);
	        verify(benPersonalCancerDietHistoryRepo, times(1)).getPersonalCancerDietHistoryStatus(anyString(), anyString());
	    }

	    @Test
	    public void testUpdateBenPersonalCancerDietHistory_Negative() {
	        when(benPersonalCancerDietHistoryRepo.getPersonalCancerDietHistoryStatus(anyString(), anyString()))
	                .thenThrow(new RuntimeException("Database error"));

	        int response = csNurseServiceImpl.updateBenPersonalCancerDietHistory(benPersonalCancerDietHistory);

	        assertEquals(0, response);
	        verify(benPersonalCancerDietHistoryRepo, times(1)).getPersonalCancerDietHistoryStatus(anyString(), anyString());
	    }
	    @Test
	    public void testUpdateBenPersonalDietHistory_Positive() {
	        when(benPersonalCancerDietHistoryRepo.updateBenPersonalCancerDietHistory(
	                anyInt(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyString(), anyString(), anyString(), anyBoolean(), anyString(), anyInt(), anyInt(), anyBoolean()))
	                .thenReturn(1);

	        int response = csNurseServiceImpl.updateBenPersonalDietHistory(validBenPersonalCancerDietHistory);

	        assertEquals(1, response);
	        verify(benPersonalCancerDietHistoryRepo, times(1)).updateBenPersonalCancerDietHistory(
	                anyInt(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyString(), anyString(), anyString(), anyBoolean(), anyString(), anyInt(), anyInt(), anyBoolean());
	    }

	    @Test
	    public void testUpdateBenPersonalDietHistory_Negative() {
	        when(benPersonalCancerDietHistoryRepo.updateBenPersonalCancerDietHistory(
	                anyInt(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyString(), anyString(), anyString(), anyBoolean(), anyString(), anyInt(), anyInt(), anyBoolean()))
	                .thenThrow(new RuntimeException("Database error"));

	        try {
	            csNurseServiceImpl.updateBenPersonalDietHistory(invalidBenPersonalCancerDietHistory);
	        } catch (RuntimeException e) {
	            assertEquals("Database error", e.getMessage());
	        }

	        verify(benPersonalCancerDietHistoryRepo, times(1)).updateBenPersonalCancerDietHistory(
	                anyInt(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyString(), anyString(), anyString(), anyBoolean(), anyString(), anyInt(), anyInt(), anyBoolean());
	    }
	    @Test
	    public void testUpdateBenVitalDetail_UpdateExistingRecord() {
	        when(benCancerVitalDetailRepo.getCancerVitalStatus(1L, 1L)).thenReturn("U");
	        when(benCancerVitalDetailRepo.updateBenCancerVitalDetail(anyLong(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyString(), anyLong(), anyLong(), anyString(), anyDouble(), anyString())).thenReturn(1);

	        int response = csNurseService.updateBenVitalDetail(benCancerVitalDetail);

	        assertEquals(1, response);
	        verify(benCancerVitalDetailRepo, times(1)).updateBenCancerVitalDetail(anyLong(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyString(), anyLong(), anyLong(), anyString(), anyDouble(), anyString());
	    }

	    @Test
	    public void testUpdateBenVitalDetail_UpdateExistingRecordWithNStatus() {
	        when(benCancerVitalDetailRepo.getCancerVitalStatus(1L, 1L)).thenReturn("N");
	        when(benCancerVitalDetailRepo.updateBenCancerVitalDetail(anyLong(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyString(), anyLong(), anyLong(), anyString(), anyDouble(), anyString())).thenReturn(1);

	        int response = csNurseService.updateBenVitalDetail(benCancerVitalDetail);

	        assertEquals(1, response);
	        verify(benCancerVitalDetailRepo, times(1)).updateBenCancerVitalDetail(anyLong(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyString(), anyLong(), anyLong(), anyString(), anyDouble(), anyString());
	    }

	    @Test
	    public void testUpdateBenVitalDetail_SaveNewRecord() {
	        when(benCancerVitalDetailRepo.getCancerVitalStatus(1L, 1L)).thenReturn(null);
	        when(benCancerVitalDetailRepo.save(any(BenCancerVitalDetail.class))).thenReturn(benCancerVitalDetail);

	        int response = csNurseService.updateBenVitalDetail(benCancerVitalDetail);

	        assertEquals(1, response);
	        verify(benCancerVitalDetailRepo, times(1)).save(any(BenCancerVitalDetail.class));
	    }

	    @Test
	    public void testUpdateBenVitalDetail_ExceptionHandling() {
	        when(benCancerVitalDetailRepo.getCancerVitalStatus(1L, 1L)).thenThrow(new RuntimeException("Database error"));

	        int response = csNurseService.updateBenVitalDetail(benCancerVitalDetail);

	        assertEquals(0, response);
	        verify(benCancerVitalDetailRepo, times(1)).getCancerVitalStatus(1L, 1L);
	    }
	    @Test
	    public void testGetBenFamilyHisData_ReturnsData() {
	        when(benFamilyCancerHistoryRepo.getBenFamilyHistory(1L, 1L)).thenReturn(benFamilyCancerHistoryList);

	        List<BenFamilyCancerHistory> result = csNurseService.getBenFamilyHisData(1L, 1L);

	        assertNotNull(result);
	        assertEquals(2, result.size());
	        assertEquals("Father,Mother", result.get(0).getFamilyMember());
	        assertEquals("Brother,Sister", result.get(1).getFamilyMember());
	        verify(benFamilyCancerHistoryRepo, times(1)).getBenFamilyHistory(1L, 1L);
	    }

	    @Test
	    public void testGetBenFamilyHisData_ReturnsEmptyList() {
	        when(benFamilyCancerHistoryRepo.getBenFamilyHistory(1L, 1L)).thenReturn(new ArrayList<>());

	        List<BenFamilyCancerHistory> result = csNurseService.getBenFamilyHisData(1L, 1L);

	        assertNotNull(result);
	        assertTrue(result.isEmpty());
	        verify(benFamilyCancerHistoryRepo, times(1)).getBenFamilyHistory(1L, 1L);
	    }

	    @Test
	    public void testGetBenFamilyHisData_PopulatesFamilyMemberList() {
	        when(benFamilyCancerHistoryRepo.getBenFamilyHistory(1L, 1L)).thenReturn(benFamilyCancerHistoryList);

	        List<BenFamilyCancerHistory> result = csNurseService.getBenFamilyHisData(1L, 1L);

	        assertNotNull(result);
	        assertEquals(2, result.size());
	        assertEquals(2, result.get(0).getFamilyMemberList().size());
	        assertEquals("Father", result.get(0).getFamilyMemberList().get(0));
	        assertEquals("Mother", result.get(0).getFamilyMemberList().get(1));
	        assertEquals(2, result.get(1).getFamilyMemberList().size());
	        assertEquals("Brother", result.get(1).getFamilyMemberList().get(0));
	        assertEquals("Sister", result.get(1).getFamilyMemberList().get(1));
	        verify(benFamilyCancerHistoryRepo, times(1)).getBenFamilyHistory(1L, 1L);
	    }
	    @Test
	    public void testGetBenObstetricDetailsData_ReturnsValidData() {
	        Long benRegID = 1L;
	        Long visitCode = 1L;
	        BenObstetricCancerHistory expectedHistory = new BenObstetricCancerHistory();
	        expectedHistory.setBeneficiaryRegID(benRegID);
	        expectedHistory.setVisitCode(visitCode);

	        when(benObstetricCancerHistoryRepo.getBenObstetricCancerHistory(benRegID, visitCode)).thenReturn(expectedHistory);

	        BenObstetricCancerHistory result = csNurseServiceImpl.getBenObstetricDetailsData(benRegID, visitCode);

	        assertNotNull(result);
	        assertEquals(expectedHistory.getBeneficiaryRegID(), result.getBeneficiaryRegID());
	        assertEquals(expectedHistory.getVisitCode(), result.getVisitCode());
	    }

	    @Test
	    public void testGetBenObstetricDetailsData_ReturnsNull() {
	        Long benRegID = 1L;
	        Long visitCode = 1L;

	        when(benObstetricCancerHistoryRepo.getBenObstetricCancerHistory(benRegID, visitCode)).thenReturn(null);

	        BenObstetricCancerHistory result = csNurseServiceImpl.getBenObstetricDetailsData(benRegID, visitCode);

	        assertNull(result);
	    }
	    @Test
	    public void testGetBenPersonalCancerHistoryData_ValidData() {
	        when(benPersonalCancerHistoryRepo.getBenPersonalHistory(benRegID, visitCode)).thenReturn(benPersonalCancerHistory);

	        BenPersonalCancerHistory result = csNurseService.getBenPersonalCancerHistoryData(benRegID, visitCode);

	        assertNotNull(result);
	        assertEquals(2, result.getTypeOfTobaccoProductList().size());
	        assertEquals("Cigarette", result.getTypeOfTobaccoProductList().get(0));
	        assertEquals("Chewing Tobacco", result.getTypeOfTobaccoProductList().get(1));
	    }

	    @Test
	    public void testGetBenPersonalCancerHistoryData_NullData() {
	        when(benPersonalCancerHistoryRepo.getBenPersonalHistory(benRegID, visitCode)).thenReturn(null);

	        BenPersonalCancerHistory result = csNurseService.getBenPersonalCancerHistoryData(benRegID, visitCode);

	        assertNull(result);
	    }

	    @Test
	    public void testGetBenPersonalCancerHistoryData_NullTypeOfTobaccoProduct() {
	        benPersonalCancerHistory.setTypeOfTobaccoProduct(null);
	        when(benPersonalCancerHistoryRepo.getBenPersonalHistory(benRegID, visitCode)).thenReturn(benPersonalCancerHistory);

	        BenPersonalCancerHistory result = csNurseService.getBenPersonalCancerHistoryData(benRegID, visitCode);

	        assertNotNull(result);
	        assertNull(result.getTypeOfTobaccoProductList());
	    }

	    @Test
	    public void testGetBenPersonalCancerHistoryData_SingleTypeOfTobaccoProduct() {
	        benPersonalCancerHistory.setTypeOfTobaccoProduct("Cigarette");
	        when(benPersonalCancerHistoryRepo.getBenPersonalHistory(benRegID, visitCode)).thenReturn(benPersonalCancerHistory);

	        BenPersonalCancerHistory result = csNurseService.getBenPersonalCancerHistoryData(benRegID, visitCode);

	        assertNotNull(result);
	        assertEquals(1, result.getTypeOfTobaccoProductList().size());
	        assertEquals("Cigarette", result.getTypeOfTobaccoProductList().get(0));
	    }
	    @Test
	    public void testGetBenPersonalCancerDietHistoryData_Null() {
	        when(benPersonalCancerDietHistoryRepo.getBenPersonaDietHistory(1L, 1L)).thenReturn(null);

	        BenPersonalCancerDietHistory result = csNurseService.getBenPersonalCancerDietHistoryData(1L, 1L);

	        assertNull(result);
	    }

	    @Test
	    public void testGetBenPersonalCancerDietHistoryData_EmptyOilConsumed() {
	        benPersonalCancerDietHistory.setTypeOfOilConsumed("");
	        when(benPersonalCancerDietHistoryRepo.getBenPersonaDietHistory(1L, 1L)).thenReturn(benPersonalCancerDietHistory);

	        BenPersonalCancerDietHistory result = csNurseService.getBenPersonalCancerDietHistoryData(1L, 1L);

	        assertNotNull(result);
	        assertEquals(1L, result.getBeneficiaryRegID());
	        assertEquals(1L, result.getVisitCode());
	        assertEquals("", result.getTypeOfOilConsumed());
	        assertTrue(result.getTypeOfOilConsumedList().isEmpty());
	    }
	    @Test
	    public void testGetBenCancerVitalDetailData_Success() {
	        when(benCancerVitalDetailRepo.getBenCancerVitalDetail(1L, 1L)).thenReturn(benCancerVitalDetail);

	        BenCancerVitalDetail result = csNurseServiceImpl.getBenCancerVitalDetailData(1L, 1L);

	        assertNotNull(result);
	        assertEquals(1L, result.getBeneficiaryRegID());
	        assertEquals(1L, result.getVisitCode());
	        assertEquals(70.0, result.getWeight_Kg());
	        assertEquals(170.0, result.getHeight_cm());
	        // Add more assertions as needed

	        verify(benCancerVitalDetailRepo, times(1)).getBenCancerVitalDetail(1L, 1L);
	    }

	    @Test
	    public void testGetBenCancerVitalDetailData_NotFound() {
	        when(benCancerVitalDetailRepo.getBenCancerVitalDetail(1L, 1L)).thenReturn(null);

	        BenCancerVitalDetail result = csNurseServiceImpl.getBenCancerVitalDetailData(1L, 1L);

	        assertNull(result);

	        verify(benCancerVitalDetailRepo, times(1)).getBenCancerVitalDetail(1L, 1L);
	    }
	   
	    @Test
	    public void testGetBenCancerAbdominalExaminationData_ReturnsValidData() {
	        CancerAbdominalExamination expectedExamination = new CancerAbdominalExamination();
	        expectedExamination.setBenRegID(benRegID);
	        expectedExamination.setVisitCode(visitCode);

	        when(cancerAbdominalExaminationRepo.getBenCancerAbdominalExaminationDetails(benRegID, visitCode))
	                .thenReturn(expectedExamination);

	        CancerAbdominalExamination actualExamination = csNurseServiceImpl.getBenCancerAbdominalExaminationData(benRegID, visitCode);

	        assertNotNull(actualExamination);
	        assertEquals(expectedExamination, actualExamination);
	        verify(cancerAbdominalExaminationRepo, times(1)).getBenCancerAbdominalExaminationDetails(benRegID, visitCode);
	    }

	    @Test
	    public void testGetBenCancerAbdominalExaminationData_ReturnsNull() {
	        when(cancerAbdominalExaminationRepo.getBenCancerAbdominalExaminationDetails(benRegID, visitCode))
	                .thenReturn(null);

	        CancerAbdominalExamination actualExamination = csNurseServiceImpl.getBenCancerAbdominalExaminationData(benRegID, visitCode);

	        assertNull(actualExamination);
	        verify(cancerAbdominalExaminationRepo, times(1)).getBenCancerAbdominalExaminationDetails(benRegID, visitCode);
	    }
	    @Test
	    public void testGetBenCancerBreastExaminationData() {
	        when(cancerBreastExaminationRepo.getBenCancerBreastExaminationDetails(benRegID, visitCode))
	                .thenReturn(cancerBreastExamination);

	        CancerBreastExamination result = csNurseServiceImpl.getBenCancerBreastExaminationData(benRegID, visitCode);

	        assertNotNull(result);
	        assertEquals(benRegID, result.getBenRegID());
	        assertEquals(visitCode, result.getVisitCode());
	        verify(cancerBreastExaminationRepo, times(1)).getBenCancerBreastExaminationDetails(benRegID, visitCode);
	    }

	    @Test
	    public void testGetBenCancerBreastExaminationData_NoData() {
	        when(cancerBreastExaminationRepo.getBenCancerBreastExaminationDetails(benRegID, visitCode))
	                .thenReturn(null);

	        CancerBreastExamination result = csNurseServiceImpl.getBenCancerBreastExaminationData(benRegID, visitCode);

	        assertNull(result);
	        verify(cancerBreastExaminationRepo, times(1)).getBenCancerBreastExaminationDetails(benRegID, visitCode);
	    }
	    @Test
	    public void testGetBenCancerGynecologicalExaminationData_ReturnsValidData() {
	        CancerGynecologicalExamination expectedExamination = new CancerGynecologicalExamination();
	        when(cancerGynecologicalExaminationRepo.getBenCancerGynecologicalExaminationDetails(benRegID, visitCode))
	                .thenReturn(expectedExamination);

	        CancerGynecologicalExamination actualExamination = csNurseServiceImpl.getBenCancerGynecologicalExaminationData(benRegID, visitCode);

	        assertNotNull(actualExamination);
	        assertEquals(expectedExamination, actualExamination);
	        verify(cancerGynecologicalExaminationRepo, times(1)).getBenCancerGynecologicalExaminationDetails(benRegID, visitCode);
	    }

	    @Test
	    public void testGetBenCancerGynecologicalExaminationData_ReturnsNull() {
	        when(cancerGynecologicalExaminationRepo.getBenCancerGynecologicalExaminationDetails(benRegID, visitCode))
	                .thenReturn(null);

	        CancerGynecologicalExamination actualExamination = csNurseServiceImpl.getBenCancerGynecologicalExaminationData(benRegID, visitCode);

	        assertNull(actualExamination);
	        verify(cancerGynecologicalExaminationRepo, times(1)).getBenCancerGynecologicalExaminationDetails(benRegID, visitCode);
	    }
	    @Test
	    public void testGetBenCancerSignAndSymptomsData_Success() {
	        when(cancerSignAndSymptomsRepo.getBenCancerSignAndSymptomsDetails(benRegID, visitCode))
	                .thenReturn(cancerSignAndSymptoms);

	        CancerSignAndSymptoms result = csNurseServiceImpl.getBenCancerSignAndSymptomsData(benRegID, visitCode);

	        assertNotNull(result);
	        assertEquals(benRegID, result.getBenRegID());
	        assertEquals(visitCode, result.getVisitCode());
	        assertEquals("Sample Symptoms", result.getSymptoms());
	        verify(cancerSignAndSymptomsRepo, times(1)).getBenCancerSignAndSymptomsDetails(benRegID, visitCode);
	    }

	    @Test
	    public void testGetBenCancerSignAndSymptomsData_NoData() {
	        when(cancerSignAndSymptomsRepo.getBenCancerSignAndSymptomsDetails(benRegID, visitCode))
	                .thenReturn(null);

	        CancerSignAndSymptoms result = csNurseServiceImpl.getBenCancerSignAndSymptomsData(benRegID, visitCode);

	        assertNull(result);
	        verify(cancerSignAndSymptomsRepo, times(1)).getBenCancerSignAndSymptomsDetails(benRegID, visitCode);
	    }

	    @Test
	    public void testGetBenCancerSignAndSymptomsData_Exception() {
	        when(cancerSignAndSymptomsRepo.getBenCancerSignAndSymptomsDetails(benRegID, visitCode))
	                .thenThrow(new RuntimeException("Database error"));

	        Exception exception = assertThrows(RuntimeException.class, () -> {
	            csNurseServiceImpl.getBenCancerSignAndSymptomsData(benRegID, visitCode);
	        });

	        assertEquals("Database error", exception.getMessage());
	        verify(cancerSignAndSymptomsRepo, times(1)).getBenCancerSignAndSymptomsDetails(benRegID, visitCode);
	    }
	  
	    @Test
	    public void testGetBenCancerLymphNodeDetailsData() {
	        when(cancerLymphNodeExaminationRepo.getBenCancerLymphNodeDetails(benRegID, visitCode))
	                .thenReturn(cancerLymphNodeDetailsList);

	        List<CancerLymphNodeDetails> result = csNurseServiceImpl.getBenCancerLymphNodeDetailsData(benRegID, visitCode);

	        assertNotNull(result);
	        assertEquals(1, result.size());
	        assertEquals("Lymph Node 1", result.get(0).getLymphNodeName());
	        verify(cancerLymphNodeExaminationRepo, times(1)).getBenCancerLymphNodeDetails(benRegID, visitCode);
	    }

	    @Test
	    public void testGetBenCancerLymphNodeDetailsData_EmptyList() {
	        when(cancerLymphNodeExaminationRepo.getBenCancerLymphNodeDetails(benRegID, visitCode))
	                .thenReturn(new ArrayList<>());

	        List<CancerLymphNodeDetails> result = csNurseServiceImpl.getBenCancerLymphNodeDetailsData(benRegID, visitCode);

	        assertNotNull(result);
	        assertTrue(result.isEmpty());
	        verify(cancerLymphNodeExaminationRepo, times(1)).getBenCancerLymphNodeDetails(benRegID, visitCode);
	    }
	    @Test
	    public void testGetBenCancerOralExaminationData_Success() {
	        when(cancerOralExaminationRepo.getBenCancerOralExaminationDetails(benRegID, visitCode))
	                .thenReturn(cancerOralExamination);

	        CancerOralExamination result = csNurseServiceImpl.getBenCancerOralExaminationData(benRegID, visitCode);

	        assertNotNull(result);
	        assertEquals(benRegID, result.getBenRegID());
	        assertEquals(visitCode, result.getVisitCode());
	        verify(cancerOralExaminationRepo, times(1)).getBenCancerOralExaminationDetails(benRegID, visitCode);
	    }

	    @Test
	    public void testGetBenCancerOralExaminationData_NotFound() {
	        when(cancerOralExaminationRepo.getBenCancerOralExaminationDetails(benRegID, visitCode))
	                .thenReturn(null);

	        CancerOralExamination result = csNurseServiceImpl.getBenCancerOralExaminationData(benRegID, visitCode);

	        assertNull(result);
	        verify(cancerOralExaminationRepo, times(1)).getBenCancerOralExaminationDetails(benRegID, visitCode);
	    }
	    @Test
	    public void testGetCancerExaminationImageAnnotationCasesheet_EmptyList() {
	        when(cancerExaminationImageAnnotationRepo.getCancerExaminationImageAnnotationList(benRegID, visitCode))
	                .thenReturn(Collections.emptyList());

	        ArrayList<WrapperCancerExamImgAnotasn> result = csNurseServiceImpl.getCancerExaminationImageAnnotationCasesheet(benRegID, visitCode);

	        assertTrue(result.isEmpty());
	        verify(cancerExaminationImageAnnotationRepo, times(1)).getCancerExaminationImageAnnotationList(benRegID, visitCode);
	    }

	    @Test
	    public void testGetCancerExaminationImageAnnotationCasesheet_SingleImageMultipleAnnotations() {
	        when(cancerExaminationImageAnnotationRepo.getCancerExaminationImageAnnotationList(benRegID, visitCode))
	                .thenReturn(cancerExaminationImageAnnotationList.subList(0, 2));

	        ArrayList<WrapperCancerExamImgAnotasn> result = csNurseServiceImpl.getCancerExaminationImageAnnotationCasesheet(benRegID, visitCode);

	        assertEquals(1, result.size());
	        assertEquals(2, result.get(0).getMarkers().size());
	        verify(cancerExaminationImageAnnotationRepo, times(1)).getCancerExaminationImageAnnotationList(benRegID, visitCode);
	    }

	    @Test
	    public void testGetCancerExaminationImageAnnotationCasesheet_MultipleImages() {
	        when(cancerExaminationImageAnnotationRepo.getCancerExaminationImageAnnotationList(benRegID, visitCode))
	                .thenReturn(cancerExaminationImageAnnotationList);

	        ArrayList<WrapperCancerExamImgAnotasn> result = csNurseServiceImpl.getCancerExaminationImageAnnotationCasesheet(benRegID, visitCode);

	        assertEquals(2, result.size());
	        assertEquals(2, result.get(0).getMarkers().size());
	        assertEquals(1, result.get(1).getMarkers().size());
	        verify(cancerExaminationImageAnnotationRepo, times(1)).getCancerExaminationImageAnnotationList(benRegID, visitCode);
	    }
	    @Test
	    public void testGetBenNurseDataForCaseSheet() {
	        // Prepare test data
	        BeneficiaryVisitDetail visitDetail = new BeneficiaryVisitDetail(benRegID, visitCode, 1, new Timestamp(System.currentTimeMillis()), (short) 1, (short) 1, "test", 1, "test", "test", "test", "test", "test", "test", "test");
	        List<BenFamilyCancerHistory> familyHistoryList = Collections.singletonList(new BenFamilyCancerHistory());
	        BenObstetricCancerHistory obstetricHistory = new BenObstetricCancerHistory();
	        BenPersonalCancerHistory personalHistory = new BenPersonalCancerHistory();
	        BenPersonalCancerDietHistory dietHistory = new BenPersonalCancerDietHistory();
	        BenCancerVitalDetail vitalDetail = new BenCancerVitalDetail();
	        CancerAbdominalExamination abdominalExamination = new CancerAbdominalExamination();
	        CancerBreastExamination breastExamination = new CancerBreastExamination();
	        CancerGynecologicalExamination gynecologicalExamination = new CancerGynecologicalExamination();
	        CancerSignAndSymptoms signAndSymptoms = new CancerSignAndSymptoms();
	        List<CancerLymphNodeDetails> lymphNodeDetails = Collections.singletonList(new CancerLymphNodeDetails());
	        CancerOralExamination oralExamination = new CancerOralExamination();

	        // Mock repository methods
	        when(benVisitDetailRepo.getBeneficiaryVisitDetails(benRegID, visitCode)).thenReturn(Collections.singletonList(new Object[]{benRegID, visitCode, 1, new Timestamp(System.currentTimeMillis()), (short) 1, (short) 1, "test", 1, "test", "test", "test", "test", "test", "test", "test"}));
	        when(benFamilyCancerHistoryRepo.getBenFamilyHistory(benRegID, visitCode)).thenReturn(familyHistoryList);
	        when(benObstetricCancerHistoryRepo.getBenObstetricCancerHistory(benRegID, visitCode)).thenReturn(obstetricHistory);
	        when(benPersonalCancerHistoryRepo.getBenPersonalHistory(benRegID, visitCode)).thenReturn(personalHistory);
	        when(benPersonalCancerDietHistoryRepo.getBenPersonaDietHistory(benRegID, visitCode)).thenReturn(dietHistory);
	        when(benCancerVitalDetailRepo.getBenCancerVitalDetail(benRegID, visitCode)).thenReturn(vitalDetail);
	        when(cancerAbdominalExaminationRepo.getBenCancerAbdominalExaminationDetails(benRegID, visitCode)).thenReturn(abdominalExamination);
	        when(cancerBreastExaminationRepo.getBenCancerBreastExaminationDetails(benRegID, visitCode)).thenReturn(breastExamination);
	        when(cancerGynecologicalExaminationRepo.getBenCancerGynecologicalExaminationDetails(benRegID, visitCode)).thenReturn(gynecologicalExamination);
	        when(cancerSignAndSymptomsRepo.getBenCancerSignAndSymptomsDetails(benRegID, visitCode)).thenReturn(signAndSymptoms);
	        when(cancerLymphNodeExaminationRepo.getBenCancerLymphNodeDetails(benRegID, visitCode)).thenReturn(lymphNodeDetails);
	        when(cancerOralExaminationRepo.getBenCancerOralExaminationDetails(benRegID, visitCode)).thenReturn(oralExamination);

	        // Call the method under test
	        Map<String, Object> result = csNurseService.getBenNurseDataForCaseSheet(benRegID, visitCode);

	        // Verify the results
	        assertNotNull(result);
	        assertEquals(visitDetail, result.get("benVisitDetail"));
	        assertEquals(familyHistoryList, result.get("familyDiseaseHistory"));
	        assertEquals(obstetricHistory, result.get("patientObstetricHistory"));
	        assertEquals(personalHistory, result.get("patientPersonalHistory"));
	        assertEquals(dietHistory, result.get("benPersonalDietHistory"));
	        assertEquals(vitalDetail, result.get("currentVitals"));
	        assertEquals(abdominalExamination, result.get("abdominalExamination"));
	        assertEquals(breastExamination, result.get("breastExamination"));
	        assertEquals(gynecologicalExamination, result.get("gynecologicalExamination"));
	        assertEquals(signAndSymptoms, result.get("signsAndSymptoms"));
	        assertEquals(lymphNodeDetails, result.get("BenCancerLymphNodeDetails"));
	        assertEquals(oralExamination, result.get("oralExamination"));

	        // Verify repository method calls
	        verify(benVisitDetailRepo, times(1)).getBeneficiaryVisitDetails(benRegID, visitCode);
	        verify(benFamilyCancerHistoryRepo, times(1)).getBenFamilyHistory(benRegID, visitCode);
	        verify(benObstetricCancerHistoryRepo, times(1)).getBenObstetricCancerHistory(benRegID, visitCode);
	        verify(benPersonalCancerHistoryRepo, times(1)).getBenPersonalHistory(benRegID, visitCode);
	        verify(benPersonalCancerDietHistoryRepo, times(1)).getBenPersonaDietHistory(benRegID, visitCode);
	        verify(benCancerVitalDetailRepo, times(1)).getBenCancerVitalDetail(benRegID, visitCode);
	        verify(cancerAbdominalExaminationRepo, times(1)).getBenCancerAbdominalExaminationDetails(benRegID, visitCode);
	        verify(cancerBreastExaminationRepo, times(1)).getBenCancerBreastExaminationDetails(benRegID, visitCode);
	        verify(cancerGynecologicalExaminationRepo, times(1)).getBenCancerGynecologicalExaminationDetails(benRegID, visitCode);
	        verify(cancerSignAndSymptomsRepo, times(1)).getBenCancerSignAndSymptomsDetails(benRegID, visitCode);
	        verify(cancerLymphNodeExaminationRepo, times(1)).getBenCancerLymphNodeDetails(benRegID, visitCode);
	        verify(cancerOralExaminationRepo, times(1)).getBenCancerOralExaminationDetails(benRegID, visitCode);
	    }
	    @Test
	    public void testGetBeneficiaryVisitDetails_WhenDataExists() {
	        when(benVisitDetailRepo.getBeneficiaryVisitDetails(benRegID, visitCode)).thenReturn(beneficiaryVisitDetail);

	        BeneficiaryVisitDetail result = csNurseService.getBeneficiaryVisitDetails(benRegID, visitCode);

	        assertNotNull(result);
	        assertEquals(1L, result.getBeneficiaryRegID());
	        assertEquals(1L, result.getVisitCode());
	        assertEquals(1, result.getProviderServiceMapID());
	        assertEquals("test", result.getVisitStatus());
	        verify(benVisitDetailRepo, times(1)).getBeneficiaryVisitDetails(benRegID, visitCode);
	    }

	    @Test
	    public void testGetBeneficiaryVisitDetails_WhenDataDoesNotExist() {
	        when(benVisitDetailRepo.getBeneficiaryVisitDetails(benRegID, visitCode)).thenReturn(null);

	        BeneficiaryVisitDetail result = csNurseService.getBeneficiaryVisitDetails(benRegID, visitCode);

	        assertNull(result);
	        verify(benVisitDetailRepo, times(1)).getBeneficiaryVisitDetails(benRegID, visitCode);
	    }

	    @Test
	    public void testGetBenCancerFamilyHistory_WithData() {
	        when(benFamilyCancerHistoryRepo.getBenCancerFamilyHistory(anyLong())).thenReturn(benCancerFamilyHistoryDataArray);

	        String result = csNurseServiceImpl.getBenCancerFamilyHistory(1L);

	        assertNotNull(result);
	        assertTrue(result.contains("Type1"));
	        assertTrue(result.contains("Member1"));
	        assertTrue(result.contains("Type2"));
	        assertTrue(result.contains("Member2"));

	        verify(benFamilyCancerHistoryRepo, times(1)).getBenCancerFamilyHistory(anyLong());
	    }

	    @Test
	    public void testGetBenCancerFamilyHistory_EmptyData() {
	        when(benFamilyCancerHistoryRepo.getBenCancerFamilyHistory(anyLong())).thenReturn(new ArrayList<>());

	        String result = csNurseServiceImpl.getBenCancerFamilyHistory(1L);

	        assertNotNull(result);
	        assertTrue(result.contains("\"data\":[]"));

	        verify(benFamilyCancerHistoryRepo, times(1)).getBenCancerFamilyHistory(anyLong());
	    }
	    @Test
	    public void testGetBenCancerPersonalHistory_WithRecords() {
	        List<Object[]> mockData = new ArrayList<>();
	        mockData.add(new Object[]{"Tobacco Use", 10, 20, "Cigarette", 5, true, true, true, 30, "Filtered", true, "Exposure", new Date()});
	        when(benPersonalCancerHistoryRepo.getBenPersonalHistory(beneficiaryRegID)).thenReturn(mockData);

	        String response = csNurseServiceImpl.getBenCancerPersonalHistory(beneficiaryRegID);

	        Map<String, Object> responseMap = new Gson().fromJson(response, Map.class);
	        assertNotNull(responseMap);
	        assertTrue(responseMap.containsKey("columns"));
	        assertTrue(responseMap.containsKey("data"));

	        List<Map<String, String>> columns = (List<Map<String, String>>) responseMap.get("columns");
	        assertEquals(13, columns.size());

	        List<Map<String, Object>> data = (List<Map<String, Object>>) responseMap.get("data");
	        assertEquals(1, data.size());
	        assertEquals("Tobacco Use", data.get(0).get("tobaccoUse"));

	        verify(benPersonalCancerHistoryRepo, times(1)).getBenPersonalHistory(beneficiaryRegID);
	    }

	    @Test
	    public void testGetBenCancerPersonalHistory_NoRecords() {
	        when(benPersonalCancerHistoryRepo.getBenPersonalHistory(beneficiaryRegID)).thenReturn(new ArrayList<>());

	        String response = csNurseServiceImpl.getBenCancerPersonalHistory(beneficiaryRegID);

	        Map<String, Object> responseMap = new Gson().fromJson(response, Map.class);
	        assertNotNull(responseMap);
	        assertTrue(responseMap.containsKey("columns"));
	        assertTrue(responseMap.containsKey("data"));

	        List<Map<String, String>> columns = (List<Map<String, String>>) responseMap.get("columns");
	        assertEquals(13, columns.size());

	        List<Map<String, Object>> data = (List<Map<String, Object>>) responseMap.get("data");
	        assertEquals(0, data.size());

	        verify(benPersonalCancerHistoryRepo, times(1)).getBenPersonalHistory(beneficiaryRegID);
	    }
	   
	    @Test
	    public void testGetBenCancerPersonalDietHistory_withValidData() {
	        List<Object[]> mockData = new ArrayList<>();
	        mockData.add(new Object[]{"DietType", 3, 2, 1, 4, 5, "OilType", "PhysicalActivity", true, false, new Date()});

	        when(benPersonalCancerDietHistoryRepo.getBenPersonaDietHistory(anyLong())).thenReturn(mockData);

	        String response = csNurseServiceImpl.getBenCancerPersonalDietHistory(1L);

	        assertNotNull(response);
	        assertTrue(response.contains("DietType"));
	        assertTrue(response.contains("OilType"));
	        verify(benPersonalCancerDietHistoryRepo, times(1)).getBenPersonaDietHistory(anyLong());
	    }

	    @Test
	    public void testGetBenCancerPersonalDietHistory_withNoData() {
	        List<Object[]> mockData = new ArrayList<>();

	        when(benPersonalCancerDietHistoryRepo.getBenPersonaDietHistory(anyLong())).thenReturn(mockData);

	        String response = csNurseServiceImpl.getBenCancerPersonalDietHistory(1L);

	        assertNotNull(response);
	        assertTrue(response.contains("\"data\":[]"));
	        verify(benPersonalCancerDietHistoryRepo, times(1)).getBenPersonaDietHistory(anyLong());
	    }

	    @Test
	    public void testGetBenCancerPersonalDietHistory_withNullData() {
	        when(benPersonalCancerDietHistoryRepo.getBenPersonaDietHistory(anyLong())).thenReturn(null);

	        String response = csNurseServiceImpl.getBenCancerPersonalDietHistory(1L);

	        assertNotNull(response);
	        assertTrue(response.contains("\"data\":[]"));
	        verify(benPersonalCancerDietHistoryRepo, times(1)).getBenPersonaDietHistory(anyLong());
	    }
	    @Test
	    public void testSaveLymphNodeDetails_SetsVisitIDAndVisitCode() {
	        List<CancerLymphNodeDetails> lymphNodeDetailsList = new ArrayList<>();
	        CancerLymphNodeDetails detail1 = new CancerLymphNodeDetails();
	        CancerLymphNodeDetails detail2 = new CancerLymphNodeDetails();
	        lymphNodeDetailsList.add(detail1);
	        lymphNodeDetailsList.add(detail2);

	        Long benVisitID = 1L;
	        Long visitCode = 2L;

	        csNurseServiceImpl.saveLymphNodeDetails(lymphNodeDetailsList, benVisitID, visitCode);

	        for (CancerLymphNodeDetails detail : lymphNodeDetailsList) {
	            assertEquals(benVisitID, detail.getBenVisitID());
	            assertEquals(visitCode, detail.getVisitCode());
	        }
	    }

	    @Test
	    public void testSaveLymphNodeDetails_CallsSaveAll() {
	        List<CancerLymphNodeDetails> lymphNodeDetailsList = new ArrayList<>();
	        CancerLymphNodeDetails detail1 = new CancerLymphNodeDetails();
	        CancerLymphNodeDetails detail2 = new CancerLymphNodeDetails();
	        lymphNodeDetailsList.add(detail1);
	        lymphNodeDetailsList.add(detail2);

	        when(cancerLymphNodeExaminationRepo.saveAll(anyList())).thenReturn(lymphNodeDetailsList);

	        csNurseServiceImpl.saveLymphNodeDetails(lymphNodeDetailsList, 1L, 2L);

	        verify(cancerLymphNodeExaminationRepo, times(1)).saveAll(lymphNodeDetailsList);
	    }

	    @Test
	    public void testSaveLymphNodeDetails_ReturnsCorrectID() {
	        List<CancerLymphNodeDetails> lymphNodeDetailsList = new ArrayList<>();
	        CancerLymphNodeDetails detail1 = new CancerLymphNodeDetails();
	        CancerLymphNodeDetails detail2 = new CancerLymphNodeDetails();
	        detail2.setID(10L);
	        lymphNodeDetailsList.add(detail1);
	        lymphNodeDetailsList.add(detail2);

	        when(cancerLymphNodeExaminationRepo.saveAll(anyList())).thenReturn(lymphNodeDetailsList);

	        Long result = csNurseServiceImpl.saveLymphNodeDetails(lymphNodeDetailsList, 1L, 2L);

	        assertEquals(10L, result);
	    }
	    @Test
	    public void testSaveCancerSignAndSymptomsData_Success() {
	        CancerSignAndSymptoms savedCancerSignAndSymptoms = new CancerSignAndSymptoms();
	        savedCancerSignAndSymptoms.setID(1L);

	        when(cancerSignAndSymptomsRepo.save(any(CancerSignAndSymptoms.class))).thenReturn(savedCancerSignAndSymptoms);

	        Long response = csNurseService.saveCancerSignAndSymptomsData(cancerSignAndSymptoms, 1L, 1L);

	        assertNotNull(response);
	        assertEquals(1L, response);
	        verify(cancerSignAndSymptomsRepo, times(1)).save(any(CancerSignAndSymptoms.class));
	    }

	    @Test
	    public void testSaveCancerSignAndSymptomsData_Failure() {
	        when(cancerSignAndSymptomsRepo.save(any(CancerSignAndSymptoms.class))).thenReturn(null);

	        Long response = csNurseService.saveCancerSignAndSymptomsData(cancerSignAndSymptoms, 1L, 1L);

	        assertNull(response);
	        verify(cancerSignAndSymptomsRepo, times(1)).save(any(CancerSignAndSymptoms.class));
	    }
	    @Test
	    public void testSaveCancerSignAndSymptomsData_Success() {
	        when(cancerSignAndSymptomsRepo.save(any(CancerSignAndSymptoms.class))).thenReturn(cancerSignAndSymptoms);

	        Long response = csNurseServiceImpl.saveCancerSignAndSymptomsData(cancerSignAndSymptoms, 100L, 200L);

	        assertNotNull(response);
	        assertEquals(1L, response);
	        verify(cancerSignAndSymptomsRepo, times(1)).save(any(CancerSignAndSymptoms.class));
	    }

	    @Test
	    public void testSaveCancerSignAndSymptomsData_ReturnsID() {
	        when(cancerSignAndSymptomsRepo.save(any(CancerSignAndSymptoms.class))).thenReturn(cancerSignAndSymptoms);

	        Long response = csNurseServiceImpl.saveCancerSignAndSymptomsData(cancerSignAndSymptoms);

	        assertNotNull(response);
	        assertEquals(1L, response);
	    }

	    @Test
	    public void testSaveCancerSignAndSymptomsData_ReturnsNull() {
	        when(cancerSignAndSymptomsRepo.save(any(CancerSignAndSymptoms.class))).thenReturn(null);

	        Long response = csNurseServiceImpl.saveCancerSignAndSymptomsData(cancerSignAndSymptoms);

	        assertNull(response);
	    }
	    @Test
	    public void testSaveCancerOralExaminationData_Success() {
	        CancerOralExamination cancerOralExamination = new CancerOralExamination();
	        CancerOralExamination savedCancerOralExamination = new CancerOralExamination();
	        savedCancerOralExamination.setID(1L);

	        when(cancerOralExaminationRepo.save(cancerOralExamination)).thenReturn(savedCancerOralExamination);

	        Long result = csNurseServiceImpl.saveCancerOralExaminationData(cancerOralExamination);

	        assertEquals(1L, result);
	    }

	    @Test
	    public void testSaveCancerOralExaminationData_Failure() {
	        CancerOralExamination cancerOralExamination = new CancerOralExamination();

	        when(cancerOralExaminationRepo.save(cancerOralExamination)).thenReturn(null);

	        Long result = csNurseServiceImpl.saveCancerOralExaminationData(cancerOralExamination);

	        assertNull(result);
	    }
	    @Test
	    public void testGetCancerOralExaminationDetails_WithNonEmptyList() {
	        // Arrange
	        List<String> preMalignantLesionTypeList = Arrays.asList("Lesion1", "Lesion2");
	        CancerOralExamination cancerOralExamination = new CancerOralExamination();
	        cancerOralExamination.setPreMalignantLesionTypeList(preMalignantLesionTypeList);

	        // Act
	        CancerOralExamination result = csNurseService.getCancerOralExaminationDetails(cancerOralExamination);

	        // Assert
	        assertNotNull(result);
	        assertEquals("Lesion1,Lesion2,", result.getPreMalignantLesionType());
	    }

	    @Test
	    public void testGetCancerOralExaminationDetails_WithEmptyList() {
	        // Arrange
	        List<String> preMalignantLesionTypeList = Collections.emptyList();
	        CancerOralExamination cancerOralExamination = new CancerOralExamination();
	        cancerOralExamination.setPreMalignantLesionTypeList(preMalignantLesionTypeList);

	        // Act
	        CancerOralExamination result = csNurseService.getCancerOralExaminationDetails(cancerOralExamination);

	        // Assert
	        assertNotNull(result);
	        assertEquals("", result.getPreMalignantLesionType());
	    }
	   
	    @Test
	    public void testSaveCancerBreastExaminationData_Success() {
	        when(cancerBreastExaminationRepo.save(any(CancerBreastExamination.class))).thenReturn(cancerBreastExamination);

	        Long response = csNurseServiceImpl.saveCancerBreastExaminationData(cancerBreastExamination);

	        assertNotNull(response);
	        assertEquals(1L, response);
	        verify(cancerBreastExaminationRepo, times(1)).save(cancerBreastExamination);
	    }

	    @Test
	    public void testSaveCancerBreastExaminationData_Failure() {
	        when(cancerBreastExaminationRepo.save(any(CancerBreastExamination.class))).thenReturn(null);

	        Long response = csNurseServiceImpl.saveCancerBreastExaminationData(cancerBreastExamination);

	        assertNull(response);
	        verify(cancerBreastExaminationRepo, times(1)).save(cancerBreastExamination);
	    }
	   
	    @Test
	    public void testSaveCancerAbdominalExaminationData_Success() {
	        when(cancerAbdominalExaminationRepo.save(any(CancerAbdominalExamination.class)))
	                .thenReturn(cancerAbdominalExamination);

	        Long response = csNurseServiceImpl.saveCancerAbdominalExaminationData(cancerAbdominalExamination);

	        assertNotNull(response);
	        assertEquals(1L, response);
	        verify(cancerAbdominalExaminationRepo, times(1)).save(any(CancerAbdominalExamination.class));
	    }

	    @Test
	    public void testSaveCancerAbdominalExaminationData_NullResponse() {
	        when(cancerAbdominalExaminationRepo.save(any(CancerAbdominalExamination.class)))
	                .thenReturn(null);

	        Long response = csNurseServiceImpl.saveCancerAbdominalExaminationData(cancerAbdominalExamination);

	        assertNull(response);
	        verify(cancerAbdominalExaminationRepo, times(1)).save(any(CancerAbdominalExamination.class));
	    }

	    @Test
	    public void testSaveCancerAbdominalExaminationData_Exception() {
	        when(cancerAbdominalExaminationRepo.save(any(CancerAbdominalExamination.class)))
	                .thenThrow(new RuntimeException("Database error"));

	        assertThrows(RuntimeException.class, () -> {
	            csNurseServiceImpl.saveCancerAbdominalExaminationData(cancerAbdominalExamination);
	        });

	        verify(cancerAbdominalExaminationRepo, times(1)).save(any(CancerAbdominalExamination.class));
	    }
	    @Test
	    public void testGetBenCancerGynecologicalExaminationData_ValidData() {
	        Long benRegID = 1L;
	        Long visitCode = 1L;
	        CancerGynecologicalExamination expectedExamination = new CancerGynecologicalExamination();
	        expectedExamination.setTypeOfLesion("LesionType");

	        when(cancerGynecologicalExaminationRepo.getBenCancerGynecologicalExaminationDetails(benRegID, visitCode))
	                .thenReturn(expectedExamination);

	        CancerGynecologicalExamination actualExamination = csNurseService.getBenCancerGynecologicalExaminationData(benRegID, visitCode);

	        assertNotNull(actualExamination);
	        assertEquals(expectedExamination.getTypeOfLesion(), actualExamination.getTypeOfLesion());
	        verify(cancerGynecologicalExaminationRepo, times(1)).getBenCancerGynecologicalExaminationDetails(benRegID, visitCode);
	    }

	    @Test
	    public void testGetBenCancerGynecologicalExaminationData_EmptyData() {
	        Long benRegID = 1L;
	        Long visitCode = 1L;

	        when(cancerGynecologicalExaminationRepo.getBenCancerGynecologicalExaminationDetails(benRegID, visitCode))
	                .thenReturn(null);

	        CancerGynecologicalExamination actualExamination = csNurseService.getBenCancerGynecologicalExaminationData(benRegID, visitCode);

	        assertNull(actualExamination);
	        verify(cancerGynecologicalExaminationRepo, times(1)).getBenCancerGynecologicalExaminationDetails(benRegID, visitCode);
	    }

	    @Test
	    public void testGetBenCancerGynecologicalExaminationData_NullData() {
	        Long benRegID = 1L;
	        Long visitCode = 1L;

	        when(cancerGynecologicalExaminationRepo.getBenCancerGynecologicalExaminationDetails(benRegID, visitCode))
	                .thenReturn(null);

	        CancerGynecologicalExamination actualExamination = csNurseService.getBenCancerGynecologicalExaminationData(benRegID, visitCode);

	        assertNull(actualExamination);
	        verify(cancerGynecologicalExaminationRepo, times(1)).getBenCancerGynecologicalExaminationDetails(benRegID, visitCode);
	    }
	    @Test
	    public void testSaveDocExaminationImageAnnotation_ValidInput() {
	        List<WrapperCancerExamImgAnotasn> wrapperList = new ArrayList<>();
	        WrapperCancerExamImgAnotasn wrapper = new WrapperCancerExamImgAnotasn();
	        wrapperList.add(wrapper);

	        List<CancerExaminationImageAnnotation> annotationList = new ArrayList<>();
	        annotationList.add(new CancerExaminationImageAnnotation());
	        when(cancerExaminationImageAnnotationRepo.saveAll(anyList())).thenReturn(annotationList);

	        Long result = csNurseServiceImpl.saveDocExaminationImageAnnotation(wrapperList, 1L, 1L);

	        assertEquals(1, result);
	        verify(cancerExaminationImageAnnotationRepo, times(1)).saveAll(anyList());
	    }

	    @Test
	    public void testSaveDocExaminationImageAnnotation_EmptyList() {
	        List<WrapperCancerExamImgAnotasn> wrapperList = new ArrayList<>();

	        Long result = csNurseServiceImpl.saveDocExaminationImageAnnotation(wrapperList, 1L, 1L);

	        assertEquals(0, result);
	        verify(cancerExaminationImageAnnotationRepo, never()).saveAll(anyList());
	    }

	    @Test
	    public void testSaveDocExaminationImageAnnotation_NullInput() {
	        Long result = csNurseServiceImpl.saveDocExaminationImageAnnotation(null, 1L, 1L);

	        assertNull(result);
	        verify(cancerExaminationImageAnnotationRepo, never()).saveAll(anyList());
	    }
	    @Test
	    public void testGetCancerExaminationImageAnnotationList_EmptyList() {
	        List<CancerExaminationImageAnnotation> result = csNurseServiceImpl.getCancerExaminationImageAnnotationList(wrapperCancerExamImgAnotasnList, 1L);
	        assertTrue(result.isEmpty());
	    }

	    @Test
	    public void testGetCancerExaminationImageAnnotationList_SingleWrapperWithMarkers() {
	        WrapperCancerExamImgAnotasn wrapper = new WrapperCancerExamImgAnotasn();
	        wrapper.setBeneficiaryRegID(1L);
	        wrapper.setVisitID(1L);
	        wrapper.setProviderServiceMapID(1L);
	        wrapper.setVanID(1L);
	        wrapper.setParkingPlaceID(1L);
	        wrapper.setCreatedBy("testUser");
	        wrapper.setImageID(1L);

	        List<Map<String, Object>> markers = new ArrayList<>();
	        markers.add(Map.of("xCord", 10.0, "yCord", 20.0, "point", 1.0, "description", "Test Marker"));
	        wrapper.setMarkers(markers);

	        wrapperCancerExamImgAnotasnList.add(wrapper);

	        List<CancerExaminationImageAnnotation> result = csNurseServiceImpl.getCancerExaminationImageAnnotationList(wrapperCancerExamImgAnotasnList, 1L);

	        assertEquals(1, result.size());
	        CancerExaminationImageAnnotation annotation = result.get(0);
	        assertEquals(1L, annotation.getBeneficiaryRegID());
	        assertEquals(1L, annotation.getVisitID());
	        assertEquals(10, annotation.getxCoordinate());
	        assertEquals(20, annotation.getyCoordinate());
	        assertEquals(1, annotation.getPoint());
	        assertEquals("Test Marker", annotation.getPointDesc());
	    }

	    @Test
	    public void testGetCancerExaminationImageAnnotationList_MultipleWrappersWithMarkers() {
	        WrapperCancerExamImgAnotasn wrapper1 = new WrapperCancerExamImgAnotasn();
	        wrapper1.setBeneficiaryRegID(1L);
	        wrapper1.setVisitID(1L);
	        wrapper1.setProviderServiceMapID(1L);
	        wrapper1.setVanID(1L);
	        wrapper1.setParkingPlaceID(1L);
	        wrapper1.setCreatedBy("testUser1");
	        wrapper1.setImageID(1L);

	        List<Map<String, Object>> markers1 = new ArrayList<>();
	        markers1.add(Map.of("xCord", 10.0, "yCord", 20.0, "point", 1.0, "description", "Test Marker 1"));
	        wrapper1.setMarkers(markers1);

	        WrapperCancerExamImgAnotasn wrapper2 = new WrapperCancerExamImgAnotasn();
	        wrapper2.setBeneficiaryRegID(2L);
	        wrapper2.setVisitID(2L);
	        wrapper2.setProviderServiceMapID(2L);
	        wrapper2.setVanID(2L);
	        wrapper2.setParkingPlaceID(2L);
	        wrapper2.setCreatedBy("testUser2");
	        wrapper2.setImageID(2L);

	        List<Map<String, Object>> markers2 = new ArrayList<>();
	        markers2.add(Map.of("xCord", 30.0, "yCord", 40.0, "point", 2.0, "description", "Test Marker 2"));
	        wrapper2.setMarkers(markers2);

	        wrapperCancerExamImgAnotasnList.add(wrapper1);
	        wrapperCancerExamImgAnotasnList.add(wrapper2);

	        List<CancerExaminationImageAnnotation> result = csNurseServiceImpl.getCancerExaminationImageAnnotationList(wrapperCancerExamImgAnotasnList, 1L);

	        assertEquals(2, result.size());

	        CancerExaminationImageAnnotation annotation1 = result.get(0);
	        assertEquals(1L, annotation1.getBeneficiaryRegID());
	        assertEquals(1L, annotation1.getVisitID());
	        assertEquals(10, annotation1.getxCoordinate());
	        assertEquals(20, annotation1.getyCoordinate());
	        assertEquals(1, annotation1.getPoint());
	        assertEquals("Test Marker 1", annotation1.getPointDesc());

	        CancerExaminationImageAnnotation annotation2 = result.get(1);
	        assertEquals(2L, annotation2.getBeneficiaryRegID());
	        assertEquals(2L, annotation2.getVisitID());
	        assertEquals(30, annotation2.getxCoordinate());
	        assertEquals(40, annotation2.getyCoordinate());
	        assertEquals(2, annotation2.getPoint());
	        assertEquals("Test Marker 2", annotation2.getPointDesc());
	    }
	    @Test
	    public void testUpdateSignAndSymptomsExaminationDetails_RecordsAvailable() {
	        CancerSignAndSymptoms cancerSignAndSymptoms = new CancerSignAndSymptoms();
	        cancerSignAndSymptoms.setBeneficiaryRegID(1L);
	        cancerSignAndSymptoms.setVisitCode(1L);
	        cancerSignAndSymptoms.setModifiedBy("testUser");

	        when(cancerSignAndSymptomsRepo.getCancerSignAndSymptomsStatus(1L, 1L)).thenReturn("Y");
	        when(cancerSignAndSymptomsRepo.updateCancerSignAndSymptoms(anyLong(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyString(), anyString(), anyLong(), anyLong(), anyString())).thenReturn(1);

	        int response = csNurseServiceImpl.updateSignAndSymptomsExaminationDetails(cancerSignAndSymptoms);

	        assertEquals(1, response);
	        verify(cancerSignAndSymptomsRepo, times(1)).updateCancerSignAndSymptoms(anyLong(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyString(), anyString(), anyLong(), anyLong(), anyString());
	    }

	    @Test
	    public void testUpdateSignAndSymptomsExaminationDetails_NoRecordsAvailable() {
	        CancerSignAndSymptoms cancerSignAndSymptoms = new CancerSignAndSymptoms();
	        cancerSignAndSymptoms.setBeneficiaryRegID(1L);
	        cancerSignAndSymptoms.setVisitCode(1L);
	        cancerSignAndSymptoms.setModifiedBy("testUser");

	        when(cancerSignAndSymptomsRepo.getCancerSignAndSymptomsStatus(1L, 1L)).thenReturn(null);
	        when(cancerSignAndSymptomsRepo.save(any(CancerSignAndSymptoms.class))).thenReturn(cancerSignAndSymptoms);

	        int response = csNurseServiceImpl.updateSignAndSymptomsExaminationDetails(cancerSignAndSymptoms);

	        assertEquals(1, response);
	        verify(cancerSignAndSymptomsRepo, times(1)).save(any(CancerSignAndSymptoms.class));
	    }

	    @Test
	    public void testUpdateSignAndSymptomsExaminationDetails_Exception() {
	        CancerSignAndSymptoms cancerSignAndSymptoms = new CancerSignAndSymptoms();
	        cancerSignAndSymptoms.setBeneficiaryRegID(1L);
	        cancerSignAndSymptoms.setVisitCode(1L);
	        cancerSignAndSymptoms.setModifiedBy("testUser");

	        when(cancerSignAndSymptomsRepo.getCancerSignAndSymptomsStatus(1L, 1L)).thenThrow(new RuntimeException("Database error"));

	        int response = csNurseServiceImpl.updateSignAndSymptomsExaminationDetails(cancerSignAndSymptoms);

	        assertEquals(0, response);
	        verify(logger, times(1)).error(anyString());
	    }
	    @Test
	    public void testUpdateSignAndSymptomsExamination() {
	        when(cancerSignAndSymptomsRepo.updateCancerSignAndSymptoms(
	                anyInt(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(),
	                anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(),
	                anyBoolean(), anyBoolean(), anyString(), anyString(), anyInt(), anyString(), anyBoolean()))
	                .thenReturn(1);

	        int response = csNurseServiceImpl.updateSignAndSymptomsExamination(cancerSignAndSymptoms);

	        assertEquals(1, response);
	        verify(cancerSignAndSymptomsRepo, times(1)).updateCancerSignAndSymptoms(
	                cancerSignAndSymptoms.getProviderServiceMapID(), cancerSignAndSymptoms.getShortnessOfBreath(),
	                cancerSignAndSymptoms.getCoughGTE2Weeks(), cancerSignAndSymptoms.getBloodInSputum(),
	                cancerSignAndSymptoms.getDifficultyInOpeningMouth(),
	                cancerSignAndSymptoms.getNonHealingUlcerOrPatchOrGrowth(),
	                cancerSignAndSymptoms.getChangeInTheToneOfVoice(), cancerSignAndSymptoms.getLumpInTheBreast(),
	                cancerSignAndSymptoms.getBloodStainedDischargeFromNipple(),
	                cancerSignAndSymptoms.getChangeInShapeAndSizeOfBreasts(),
	                cancerSignAndSymptoms.getVaginalBleedingBetweenPeriods(),
	                cancerSignAndSymptoms.getVaginalBleedingAfterMenopause(),
	                cancerSignAndSymptoms.getVaginalBleedingAfterIntercourse(),
	                cancerSignAndSymptoms.getFoulSmellingVaginalDischarge(), cancerSignAndSymptoms.getBreastEnlargement(),
	                cancerSignAndSymptoms.getLymphNode_Enlarged(), cancerSignAndSymptoms.getBriefHistory(),
	                cancerSignAndSymptoms.getModifiedBy(), cancerSignAndSymptoms.getBeneficiaryRegID(),
	                cancerSignAndSymptoms.getVisitCode(), cancerSignAndSymptoms.getProcessed());
	    }
	   
	    @Test
	    public void testUpdateLymphNodeExaminationDetails_ValidDetails() {
	        when(cancerLymphNodeExaminationRepo.saveAll(anyList())).thenReturn(lymphNodeDetails);

	        int response = csNurseService.updateLymphNodeExaminationDetails(wrapperCancerSymptoms);

	        assertEquals(2, response);
	        verify(cancerLymphNodeExaminationRepo, times(1)).saveAll(anyList());
	    }

	    @Test
	    public void testUpdateLymphNodeExaminationDetails_EmptyDetails() {
	        wrapperCancerSymptoms.setCancerLymphNodeDetails(new ArrayList<>());

	        int response = csNurseService.updateLymphNodeExaminationDetails(wrapperCancerSymptoms);

	        assertEquals(1, response);
	        verify(cancerLymphNodeExaminationRepo, never()).saveAll(anyList());
	    }

	    @Test
	    public void testUpdateLymphNodeExaminationDetails_NullDetails() {
	        wrapperCancerSymptoms.setCancerLymphNodeDetails(null);

	        int response = csNurseService.updateLymphNodeExaminationDetails(wrapperCancerSymptoms);

	        assertEquals(0, response);
	        verify(cancerLymphNodeExaminationRepo, never()).saveAll(anyList());
	    }

	    @Test
	    public void testUpdateLymphNodeExaminationDetails_NullFields() {
	        CancerLymphNodeDetails detail = new CancerLymphNodeDetails();
	        lymphNodeDetails.add(detail);

	        when(cancerLymphNodeExaminationRepo.saveAll(anyList())).thenReturn(lymphNodeDetails);

	        int response = csNurseService.updateLymphNodeExaminationDetails(wrapperCancerSymptoms);

	        assertEquals(3, response);
	        verify(cancerLymphNodeExaminationRepo, times(1)).saveAll(anyList());
	    }

	    @Test
	    public void testUpdateLymphNodeExaminationDetails_DuplicateNames() {
	        CancerLymphNodeDetails detail = new CancerLymphNodeDetails();
	        detail.setLymphNodeName("Node1");
	        lymphNodeDetails.add(detail);

	        when(cancerLymphNodeExaminationRepo.saveAll(anyList())).thenReturn(lymphNodeDetails);

	        int response = csNurseService.updateLymphNodeExaminationDetails(wrapperCancerSymptoms);

	        assertEquals(3, response);
	        verify(cancerLymphNodeExaminationRepo, times(1)).saveAll(anyList());
	    }
	    @Test
	    public void testUpdateCancerOralDetails_RecordsAvailable() {
	        when(cancerOralExaminationRepo.getCancerOralExaminationStatus(1L, 1L)).thenReturn("Y");
	        when(cancerOralExaminationRepo.updateCancerOralExaminationDetails(anyLong(), anyBoolean(), anyBoolean(), anyString(), anyBoolean(), anyBoolean(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString())).thenReturn(1);

	        int response = csNurseServiceImpl.updateCancerOralDetails(cancerOralExamination);

	        assertEquals(1, response);
	        verify(cancerOralExaminationRepo, times(1)).updateCancerOralExaminationDetails(anyLong(), anyBoolean(), anyBoolean(), anyString(), anyBoolean(), anyBoolean(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString());
	    }

	    @Test
	    public void testUpdateCancerOralDetails_NoRecordsAvailable() {
	        when(cancerOralExaminationRepo.getCancerOralExaminationStatus(1L, 1L)).thenReturn(null);
	        when(cancerOralExaminationRepo.save(any(CancerOralExamination.class))).thenReturn(cancerOralExamination);

	        int response = csNurseServiceImpl.updateCancerOralDetails(cancerOralExamination);

	        assertEquals(1, response);
	        verify(cancerOralExaminationRepo, times(1)).save(any(CancerOralExamination.class));
	    }

	    @Test
	    public void testUpdateCancerOralDetails_Exception() {
	        when(cancerOralExaminationRepo.getCancerOralExaminationStatus(1L, 1L)).thenThrow(new RuntimeException("Database error"));

	        int response = csNurseServiceImpl.updateCancerOralDetails(cancerOralExamination);

	        assertEquals(0, response);
	        verify(cancerOralExaminationRepo, times(1)).getCancerOralExaminationStatus(1L, 1L);
	    }
	    @Test
	    public void testUpdateCancerOralExamination_RecordsAvailable() {
	        CancerOralExamination cancerOralExamination = new CancerOralExamination();
	        cancerOralExamination.setBeneficiaryRegID(1L);
	        cancerOralExamination.setVisitCode(1L);
	        cancerOralExamination.setModifiedBy("testUser");

	        when(cancerOralExaminationRepo.getCancerOralExaminationStatus(1L, 1L)).thenReturn("Y");
	        when(cancerOralExaminationRepo.updateCancerOralExaminationDetails(anyLong(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString())).thenReturn(1);

	        int response = csNurseService.updateCancerOralExamination(cancerOralExamination);

	        assertEquals(1, response);
	        verify(cancerOralExaminationRepo, times(1)).updateCancerOralExaminationDetails(anyLong(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString());
	    }

	    @Test
	    public void testUpdateCancerOralExamination_NoRecordsAvailable() {
	        CancerOralExamination cancerOralExamination = new CancerOralExamination();
	        cancerOralExamination.setBeneficiaryRegID(1L);
	        cancerOralExamination.setVisitCode(1L);
	        cancerOralExamination.setModifiedBy("testUser");

	        when(cancerOralExaminationRepo.getCancerOralExaminationStatus(1L, 1L)).thenReturn(null);
	        when(cancerOralExaminationRepo.save(any(CancerOralExamination.class))).thenReturn(cancerOralExamination);

	        int response = csNurseService.updateCancerOralExamination(cancerOralExamination);

	        assertEquals(1, response);
	        verify(cancerOralExaminationRepo, times(1)).save(any(CancerOralExamination.class));
	    }

	    @Test
	    public void testUpdateCancerOralExamination_Exception() {
	        CancerOralExamination cancerOralExamination = new CancerOralExamination();
	        cancerOralExamination.setBeneficiaryRegID(1L);
	        cancerOralExamination.setVisitCode(1L);
	        cancerOralExamination.setModifiedBy("testUser");

	        when(cancerOralExaminationRepo.getCancerOralExaminationStatus(1L, 1L)).thenThrow(new RuntimeException("Database error"));

	        assertThrows(RuntimeException.class, () -> {
	            csNurseService.updateCancerOralExamination(cancerOralExamination);
	        });
	    }
	    @Test
	    public void testUpdateCancerBreastDetails_whenProcessedIsNull() {
	        CancerBreastExamination examination = new CancerBreastExamination();
	        when(cancerBreastExaminationRepo.getCancerBreastExaminationStatus(anyString(), anyString())).thenReturn(null);

	        int response = csNurseServiceImpl.updateCancerBreastDetails(examination);

	        assertEquals(1, response);
	        verify(cancerBreastExaminationRepo, times(1)).getCancerBreastExaminationStatus(anyString(), anyString());
	    }

	    @Test
	    public void testUpdateCancerBreastDetails_whenProcessedIsN() {
	        CancerBreastExamination examination = new CancerBreastExamination();
	        when(cancerBreastExaminationRepo.getCancerBreastExaminationStatus(anyString(), anyString())).thenReturn("N");

	        int response = csNurseServiceImpl.updateCancerBreastDetails(examination);

	        assertEquals(1, response);
	        verify(cancerBreastExaminationRepo, times(1)).getCancerBreastExaminationStatus(anyString(), anyString());
	    }

	    @Test
	    public void testUpdateCancerBreastDetails_whenProcessedIsNotN() {
	        CancerBreastExamination examination = new CancerBreastExamination();
	        when(cancerBreastExaminationRepo.getCancerBreastExaminationStatus(anyString(), anyString())).thenReturn("Y");

	        int response = csNurseServiceImpl.updateCancerBreastDetails(examination);

	        assertEquals(1, response);
	        verify(cancerBreastExaminationRepo, times(1)).getCancerBreastExaminationStatus(anyString(), anyString());
	    }

	    @Test
	    public void testUpdateCancerBreastDetails_whenExceptionThrown() {
	        CancerBreastExamination examination = new CancerBreastExamination();
	        when(cancerBreastExaminationRepo.getCancerBreastExaminationStatus(anyString(), anyString())).thenThrow(new RuntimeException("Exception"));

	        int response = csNurseServiceImpl.updateCancerBreastDetails(examination);

	        assertEquals(0, response);
	        verify(cancerBreastExaminationRepo, times(1)).getCancerBreastExaminationStatus(anyString(), anyString());
	    }
	    @Test
	    public void testUpdateCancerBreastExamination_success() {
	        CancerBreastExamination examination = new CancerBreastExamination();
	        // Set up the examination object with test data
	        examination.setProviderServiceMapID("testProviderServiceMapID");
	        examination.setEverBreastFed("Yes");
	        examination.setBreastFeedingDurationGTE6months("Yes");
	        examination.setBreastsAppear_Normal("Normal");
	        examination.setRashOnBreast("No");
	        examination.setDimplingSkinOnBreast("No");
	        examination.setDischargeFromNipple("No");
	        examination.setPeaudOrange("No");
	        examination.setLumpInBreast("No");
	        examination.setLumpSize("0");
	        examination.setLumpShape("None");
	        examination.setLumpTexture("None");
	        examination.setReferredToMammogram("No");
	        examination.setMamogramReport("None");
	        examination.setModifiedBy("testUser");
	        examination.setBeneficiaryRegID("testBeneficiaryRegID");
	        examination.setVisitCode("testVisitCode");
	        examination.setProcessed("N");

	        when(cancerBreastExaminationRepo.updateCancerBreastExaminatio(
	                anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(),
	                anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(),
	                anyString(), anyString())).thenReturn(1);

	        int response = csNurseServiceImpl.updateCancerBreastExamination(examination);

	        assertEquals(1, response);
	        verify(cancerBreastExaminationRepo, times(1)).updateCancerBreastExaminatio(
	                anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(),
	                anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(),
	                anyString(), anyString());
	    }

	    @Test
	    public void testUpdateCancerBreastExamination_exception() {
	        CancerBreastExamination examination = new CancerBreastExamination();
	        // Set up the examination object with test data
	        examination.setProviderServiceMapID("testProviderServiceMapID");
	        examination.setEverBreastFed("Yes");
	        examination.setBreastFeedingDurationGTE6months("Yes");
	        examination.setBreastsAppear_Normal("Normal");
	        examination.setRashOnBreast("No");
	        examination.setDimplingSkinOnBreast("No");
	        examination.setDischargeFromNipple("No");
	        examination.setPeaudOrange("No");
	        examination.setLumpInBreast("No");
	        examination.setLumpSize("0");
	        examination.setLumpShape("None");
	        examination.setLumpTexture("None");
	        examination.setReferredToMammogram("No");
	        examination.setMamogramReport("None");
	        examination.setModifiedBy("testUser");
	        examination.setBeneficiaryRegID("testBeneficiaryRegID");
	        examination.setVisitCode("testVisitCode");
	        examination.setProcessed("N");

	        when(cancerBreastExaminationRepo.updateCancerBreastExaminatio(
	                anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(),
	                anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(),
	                anyString(), anyString())).thenThrow(new RuntimeException("Exception"));

	        try {
	            csNurseServiceImpl.updateCancerBreastExamination(examination);
	            fail("Expected RuntimeException to be thrown");
	        } catch (RuntimeException e) {
	            assertEquals("Exception", e.getMessage());
	        }

	        verify(cancerBreastExaminationRepo, times(1)).updateCancerBreastExaminatio(
	                anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(),
	                anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(),
	                anyString(), anyString());
	    }
	    @Test
	    public void testUpdateCancerAbdominalExaminationDetails_UpdateExistingRecord() {
	        when(cancerAbdominalExaminationRepo.getCancerAbdominalExaminationStatus(1L, 1L)).thenReturn("U");

	        int response = csNurseService.updateCancerAbdominalExaminationDetails(cancerAbdominalExamination);

	        assertEquals(1, response);
	        verify(cancerAbdominalExaminationRepo, times(1)).updateCancerAbdominalExamination(
	                anyLong(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyString(), anyString(), anyLong(), anyLong(), anyString());
	    }

	    @Test
	    public void testUpdateCancerAbdominalExaminationDetails_SaveNewRecord() {
	        when(cancerAbdominalExaminationRepo.getCancerAbdominalExaminationStatus(1L, 1L)).thenReturn(null);
	        when(cancerAbdominalExaminationRepo.save(any(CancerAbdominalExamination.class))).thenReturn(cancerAbdominalExamination);

	        int response = csNurseService.updateCancerAbdominalExaminationDetails(cancerAbdominalExamination);

	        assertEquals(1, response);
	        verify(cancerAbdominalExaminationRepo, times(1)).save(any(CancerAbdominalExamination.class));
	    }

	    @Test
	    public void testUpdateCancerAbdominalExaminationDetails_ExceptionHandling() {
	        when(cancerAbdominalExaminationRepo.getCancerAbdominalExaminationStatus(1L, 1L)).thenThrow(new RuntimeException("Database error"));

	        int response = csNurseService.updateCancerAbdominalExaminationDetails(cancerAbdominalExamination);

	        assertEquals(0, response);
	        verify(cancerAbdominalExaminationRepo, times(1)).getCancerAbdominalExaminationStatus(1L, 1L);
	    }
	    @Test
	    public void testUpdateCancerAbdominalExamination_RecordsAvailable() {
	        when(cancerAbdominalExaminationRepo.getCancerAbdominalExaminationStatus(1L, 1L)).thenReturn("U");
	        when(cancerAbdominalExaminationRepo.updateCancerAbdominalExamination(
	                anyLong(), anyBoolean(), anyString(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyString(), anyString(), anyLong(), anyLong(), anyString()))
	                .thenReturn(1);

	        int response = csNurseServiceImpl.updateCancerAbdominalExamination(cancerAbdominalExamination);

	        assertEquals(1, response);
	        verify(cancerAbdominalExaminationRepo, times(1)).updateCancerAbdominalExamination(
	                anyLong(), anyBoolean(), anyString(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyString(), anyString(), anyLong(), anyLong(), anyString());
	    }

	    @Test
	    public void testUpdateCancerAbdominalExamination_NoRecordsAvailable() {
	        when(cancerAbdominalExaminationRepo.getCancerAbdominalExaminationStatus(1L, 1L)).thenReturn(null);
	        when(cancerAbdominalExaminationRepo.save(any(CancerAbdominalExamination.class))).thenReturn(cancerAbdominalExamination);

	        int response = csNurseServiceImpl.updateCancerAbdominalExamination(cancerAbdominalExamination);

	        assertEquals(1, response);
	        verify(cancerAbdominalExaminationRepo, times(1)).save(any(CancerAbdominalExamination.class));
	    }

	    @Test
	    public void testUpdateCancerAbdominalExamination_Exception() {
	        when(cancerAbdominalExaminationRepo.getCancerAbdominalExaminationStatus(1L, 1L)).thenThrow(new RuntimeException("Database error"));

	        int response = csNurseServiceImpl.updateCancerAbdominalExamination(cancerAbdominalExamination);

	        assertEquals(0, response);
	        verify(cancerAbdominalExaminationRepo, times(1)).getCancerAbdominalExaminationStatus(1L, 1L);
	    }
	    @Test
	    public void testUpdateCancerGynecologicalExaminationDetails_RecordsAvailable() {
	        when(cancerGynecologicalExaminationRepo.getCancerGynecologicalExaminationStatus(1L, 1L)).thenReturn("U");
	        when(csNurseServiceImpl.updateCancerGynecologicalExamination(any(CancerGynecologicalExamination.class))).thenReturn(1);

	        int response = csNurseServiceImpl.updateCancerGynecologicalExaminationDetails(cancerGynecologicalExamination);

	        assertEquals(1, response);
	        verify(cancerGynecologicalExaminationRepo, times(1)).getCancerGynecologicalExaminationStatus(1L, 1L);
	        verify(csNurseServiceImpl, times(1)).updateCancerGynecologicalExamination(any(CancerGynecologicalExamination.class));
	    }

	    @Test
	    public void testUpdateCancerGynecologicalExaminationDetails_NoRecordsAvailable() {
	        when(cancerGynecologicalExaminationRepo.getCancerGynecologicalExaminationStatus(1L, 1L)).thenReturn(null);
	        when(csNurseServiceImpl.saveCancerGynecologicalExaminationData(any(CancerGynecologicalExamination.class))).thenReturn(1L);

	        int response = csNurseServiceImpl.updateCancerGynecologicalExaminationDetails(cancerGynecologicalExamination);

	        assertEquals(1, response);
	        verify(cancerGynecologicalExaminationRepo, times(1)).getCancerGynecologicalExaminationStatus(1L, 1L);
	        verify(csNurseServiceImpl, times(1)).saveCancerGynecologicalExaminationData(any(CancerGynecologicalExamination.class));
	    }

	    @Test
	    public void testUpdateCancerGynecologicalExaminationDetails_Exception() {
	        when(cancerGynecologicalExaminationRepo.getCancerGynecologicalExaminationStatus(1L, 1L)).thenThrow(new RuntimeException("Database error"));

	        int response = csNurseServiceImpl.updateCancerGynecologicalExaminationDetails(cancerGynecologicalExamination);

	        assertEquals(0, response);
	        verify(cancerGynecologicalExaminationRepo, times(1)).getCancerGynecologicalExaminationStatus(1L, 1L);
	    }

	    @Test
	    public void testUpdateCancerGynecologicalExamination_Success() {
	        when(cancerGynecologicalExaminationRepo.updateCancerGynecologicalExamination(
	                anyLong(), anyString(), anyString(), anyString(), anyString(), anyBoolean(), anyBoolean(), anyString(),
	                anyString(), anyBoolean(), anyString(), anyString(), anyLong(), anyLong(), anyString())).thenReturn(1);

	        int response = csNurseServiceImpl.updateCancerGynecologicalExamination(cancerGynecologicalExamination);

	        assertEquals(1, response);
	        verify(cancerGynecologicalExaminationRepo, times(1)).updateCancerGynecologicalExamination(
	                anyLong(), anyString(), anyString(), anyString(), anyString(), anyBoolean(), anyBoolean(), anyString(),
	                anyString(), anyBoolean(), anyString(), anyString(), anyLong(), anyLong(), anyString());
	    }

	    @Test
	    public void testUpdateCancerGynecologicalExamination_Exception() {
	        when(cancerGynecologicalExaminationRepo.updateCancerGynecologicalExamination(
	                anyLong(), anyString(), anyString(), anyString(), anyString(), anyBoolean(), anyBoolean(), anyString(),
	                anyString(), anyBoolean(), anyString(), anyString(), anyLong(), anyLong(), anyString()))
	                .thenThrow(new RuntimeException("Database error"));

	        try {
	            csNurseServiceImpl.updateCancerGynecologicalExamination(cancerGynecologicalExamination);
	            fail("Expected RuntimeException to be thrown");
	        } catch (RuntimeException e) {
	            assertEquals("Database error", e.getMessage());
	        }

	        verify(cancerGynecologicalExaminationRepo, times(1)).updateCancerGynecologicalExamination(
	                anyLong(), anyString(), anyString(), anyString(), anyString(), anyBoolean(), anyBoolean(), anyString(),
	                anyString(), anyBoolean(), anyString(), anyString(), anyLong(), anyLong(), anyString());
	    }
	    
	    @Test
	    public void testUpdateCancerExamImgAnotasnDetails_RecordsAvailable() {
	        List<Object[]> statuses = new ArrayList<>();
	        statuses.add(new Object[]{1L, "U"});
	        when(cancerExaminationImageAnnotationRepo.getCancerExaminationImageAnnotationDetailsStatus(anyLong(), anyLong(), anyList()))
	                .thenReturn(statuses);
	        when(cancerExaminationImageAnnotationRepo.deleteExistingImageAnnotationDetails(anyLong(), anyString()))
	                .thenReturn(1);
	        when(cancerExaminationImageAnnotationRepo.saveAll(anyList()))
	                .thenReturn(cancerExaminationImageAnnotationList);

	        int response = csNurseServiceImpl.updateCancerExamImgAnotasnDetails(cancerExaminationImageAnnotationList);

	        assertEquals(1, response);
	        verify(cancerExaminationImageAnnotationRepo, times(1)).getCancerExaminationImageAnnotationDetailsStatus(anyLong(), anyLong(), anyList());
	        verify(cancerExaminationImageAnnotationRepo, times(1)).deleteExistingImageAnnotationDetails(anyLong(), anyString());
	        verify(cancerExaminationImageAnnotationRepo, times(1)).saveAll(anyList());
	    }

	    @Test
	    public void testUpdateCancerExamImgAnotasnDetails_NoRecordsAvailable() {
	        when(cancerExaminationImageAnnotationRepo.getCancerExaminationImageAnnotationDetailsStatus(anyLong(), anyLong(), anyList()))
	                .thenReturn(null);
	        when(cancerExaminationImageAnnotationRepo.saveAll(anyList()))
	                .thenReturn(cancerExaminationImageAnnotationList);

	        int response = csNurseServiceImpl.updateCancerExamImgAnotasnDetails(cancerExaminationImageAnnotationList);

	        assertEquals(1, response);
	        verify(cancerExaminationImageAnnotationRepo, times(1)).getCancerExaminationImageAnnotationDetailsStatus(anyLong(), anyLong(), anyList());
	        verify(cancerExaminationImageAnnotationRepo, times(1)).saveAll(anyList());
	    }

	    @Test
	    public void testUpdateCancerExamImgAnotasnDetails_Exception() {
	        when(cancerExaminationImageAnnotationRepo.getCancerExaminationImageAnnotationDetailsStatus(anyLong(), anyLong(), anyList()))
	                .thenThrow(new RuntimeException("Database error"));

	        int response = csNurseServiceImpl.updateCancerExamImgAnotasnDetails(cancerExaminationImageAnnotationList);

	        assertEquals(0, response);
	        verify(cancerExaminationImageAnnotationRepo, times(1)).getCancerExaminationImageAnnotationDetailsStatus(anyLong(), anyLong(), anyList());
	    }
	    
	}
	

	
	   
	
	
	
	

