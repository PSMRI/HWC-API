package com.iemr.hwc.service.common.master;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.iemr.hwc.repo.doctor.LabTestMasterRepo;
import com.iemr.hwc.repo.masterrepo.ncdScreening.BPAndDiabeticStatusRepo;
import com.iemr.hwc.repo.masterrepo.ncdScreening.NCDScreeningConditionRepo;
import com.iemr.hwc.repo.masterrepo.ncdScreening.NCDScreeningReasonRepo;

public class TestNCDScreeningMasterServiceImpl {
	
	

	    @Mock
	    private NCDScreeningConditionRepo ncdScreeningConditionRepo;
	    @Mock
	    private NCDScreeningReasonRepo ncdScreeningReasonRepo;
	    @Mock
	    private BPAndDiabeticStatusRepo bpAndDiabeticStatusRepo;
	    @Mock
	    private LabTestMasterRepo labTestMasterRepo;

	    

	   

	   
	   
	   
	    @InjectMocks
	    private NCDScreeningMasterServiceImpl ncdScreeningMasterServiceImpl;
	    private NCDScreeningMasterServiceImpl ncdScreeningMasterService;
	    private List<Object[]> mockData;
	    private ArrayList<Object[]> mockLabTests;


	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    public void testGetNCDScreeningConditions_Positive() {
	        // Arrange
	        List<Object[]> expectedConditions = Arrays.asList(new Object[]{1, "Condition1"}, new Object[]{2, "Condition2"});
	        when(ncdScreeningConditionRepo.getNCDScreeningConditions()).thenReturn(expectedConditions);

	        // Act
	        List<Object[]> actualConditions = ncdScreeningMasterServiceImpl.getNCDScreeningConditions();

	        // Assert
	        assertNotNull(actualConditions);
	        assertEquals(expectedConditions, actualConditions);
	    }

	    @Test
	    public void testGetNCDScreeningConditions_Negative() {
	        // Arrange
	        when(ncdScreeningConditionRepo.getNCDScreeningConditions()).thenThrow(new RuntimeException("Database error"));

	        // Act
	        List<Object[]> actualConditions = ncdScreeningMasterServiceImpl.getNCDScreeningConditions();

	        // Assert
	        assertNull(actualConditions);
	    }
	    @Test
	    public void testGetNCDScreeningReasons_Positive() {
	        // Arrange
	        List<Object[]> expectedReasons = Arrays.asList(new Object[]{1, "Reason1"}, new Object[]{2, "Reason2"});
	        when(ncdScreeningReasonRepo.getNCDScreeningReasons()).thenReturn(expectedReasons);

	        // Act
	        List<Object[]> actualReasons = ncdScreeningMasterServiceImpl.getNCDScreeningReasons();

	        // Assert
	        assertNotNull(actualReasons);
	        assertEquals(expectedReasons, actualReasons);
	    }

	    @Test
	    public void testGetNCDScreeningReasons_Negative() {
	        // Arrange
	        when(ncdScreeningReasonRepo.getNCDScreeningReasons()).thenThrow(new RuntimeException("Database error"));

	        // Act
	        List<Object[]> actualReasons = ncdScreeningMasterServiceImpl.getNCDScreeningReasons();

	        // Assert
	        assertNull(actualReasons);
	    }
	    @Test
	    public void testGetBPAndDiabeticStatusTrue() {
	        when(bpAndDiabeticStatusRepo.getBPAndDiabeticStatus(true)).thenReturn(mockData);

	        List<Object[]> result = ncdScreeningMasterService.getBPAndDiabeticStatus(true);

	        assertNotNull(result);
	        assertEquals(2, result.size());
	        assertEquals("Mock Data 1", result.get(0)[0]);
	        assertEquals("Mock Data 2", result.get(1)[0]);
	        verify(bpAndDiabeticStatusRepo, times(1)).getBPAndDiabeticStatus(true);
	    }

	    @Test
	    public void testGetBPAndDiabeticStatusFalse() {
	        when(bpAndDiabeticStatusRepo.getBPAndDiabeticStatus(false)).thenReturn(mockData);

	        List<Object[]> result = ncdScreeningMasterService.getBPAndDiabeticStatus(false);

	        assertNotNull(result);
	        assertEquals(2, result.size());
	        assertEquals("Mock Data 1", result.get(0)[0]);
	        assertEquals("Mock Data 2", result.get(1)[0]);
	        verify(bpAndDiabeticStatusRepo, times(1)).getBPAndDiabeticStatus(false);
	    }
	    @Test
	    public void testGetNCDTest() {
	        when(labTestMasterRepo.getTestsBYVisitCategory("NCD Screening")).thenReturn(mockLabTests);

	        ArrayList<Object[]> result = ncdScreeningMasterService.getNCDTest();

	        assertNotNull(result);
	        assertEquals(2, result.size());
	        assertEquals("Test1", result.get(0)[0]);
	        assertEquals("Description1", result.get(0)[1]);
	        assertEquals("Test2", result.get(1)[0]);
	        assertEquals("Description2", result.get(1)[1]);

	        verify(labTestMasterRepo, times(1)).getTestsBYVisitCategory("NCD Screening");
	    }
	}

