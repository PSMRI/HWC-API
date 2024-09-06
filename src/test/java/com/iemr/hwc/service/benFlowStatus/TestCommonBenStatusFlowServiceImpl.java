package com.iemr.hwc.service.benFlowStatus;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.System.Logger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ibm.icu.impl.duration.Period;
import com.iemr.hwc.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;

public class TestCommonBenStatusFlowServiceImpl {
	

	

	@ExtendWith(MockitoExtension.class)
	public class CommonBenStatusFlowServiceImplTest {

	    @Mock
	    private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;

	    @Mock
	    private BenVisitDetailRepo benVisitDetailRepo;
	  
	    @Mock
	    private Logger logger;
	    
	    
	    


	    @InjectMocks
	    private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;

	    private String requestOBJ;
	    private Long beneficiaryRegID;
	    private Long beneficiaryID;

	    @BeforeEach
	    public void setUp() {
	        requestOBJ = "{\"key\":\"value\"}";
	        beneficiaryRegID = 1L;
	        beneficiaryID = 1L;
	    }

	    @Test
	    public void testCreateBenFlowRecord_ValidIDs() throws Exception {
	        BeneficiaryFlowStatus beneficiaryFlowStatus = new BeneficiaryFlowStatus();
	        when(beneficiaryFlowStatusRepo.save(any(BeneficiaryFlowStatus.class))).thenReturn(beneficiaryFlowStatus);

	        int result = commonBenStatusFlowServiceImpl.createBenFlowRecord(requestOBJ, beneficiaryRegID, beneficiaryID);

	        assertEquals(1, result);
	        verify(beneficiaryFlowStatusRepo, times(1)).save(any(BeneficiaryFlowStatus.class));
	    }

	    @Test
	    public void testCreateBenFlowRecord_InvalidIDs() throws Exception {
	        Calendar cal = Calendar.getInstance();
	        cal.add(Calendar.DAY_OF_YEAR, -7);
	        long sevenDaysAgo = cal.getTimeInMillis();
	        ArrayList<Long> benFlowIDList = new ArrayList<>();
	        when(beneficiaryFlowStatusRepo.checkBenAlreadyInNurseWorkList(anyLong(), anyLong(), anyInt(), any(Timestamp.class))).thenReturn(benFlowIDList);

	        int result = commonBenStatusFlowServiceImpl.createBenFlowRecord(requestOBJ, null, null);

	        assertEquals(1, result);
	        verify(beneficiaryFlowStatusRepo, times(1)).save(any(BeneficiaryFlowStatus.class));
	    }

	    @Test
	    public void testCreateBenFlowRecord_ExistingBeneficiaryInNurseWorkList() throws Exception {
	        Calendar cal = Calendar.getInstance();
	        cal.add(Calendar.DAY_OF_YEAR, -7);
	        long sevenDaysAgo = cal.getTimeInMillis();
	        ArrayList<Long> benFlowIDList = new ArrayList<>();
	        benFlowIDList.add(1L);
	        when(beneficiaryFlowStatusRepo.checkBenAlreadyInNurseWorkList(anyLong(), anyLong(), anyInt(), any(Timestamp.class))).thenReturn(benFlowIDList);

	        int result = commonBenStatusFlowServiceImpl.createBenFlowRecord(requestOBJ, null, null);

	        assertEquals(3, result);
	        verify(beneficiaryFlowStatusRepo, never()).save(any(BeneficiaryFlowStatus.class));
	    }

	    @Test
	    public void testCreateBenFlowRecord_ExceptionHandling() throws Exception {
	        when(beneficiaryFlowStatusRepo.save(any(BeneficiaryFlowStatus.class))).thenThrow(new RuntimeException("Error"));

	        int result = commonBenStatusFlowServiceImpl.createBenFlowRecord(requestOBJ, beneficiaryRegID, beneficiaryID);

	        assertEquals(0, result);
	        verify(beneficiaryFlowStatusRepo, times(1)).save(any(BeneficiaryFlowStatus.class));
	    }
	}
	 @Test
	    public void testUpdateBenFlowNurseAfterNurseActivity_Success() {
	        // Arrange
	        Long benFlowID = 1L;
	        Long benRegID = 1L;
	        Long benVisitID = 1L;
	        String visitReason = "Reason";
	        String visitCategory = "Category";
	        Short nurseFlag = 1;
	        Short docFlag = 1;
	        Short labIteration = 1;
	        Short radiologistFlag = 1;
	        Short oncologistFlag = 1;
	        Long visitCode = 1L;
	        Integer vanID = 1;
	        Short specialistFlag = 1;
	        Timestamp tcDate = new Timestamp(System.currentTimeMillis());
	        Integer tcSpecialistUserID = 1;

	        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterNurseActivity(anyLong(), anyLong(), anyLong(), anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(), anyInt(), anyShort(), any(Timestamp.class), anyInt())).thenReturn(1);

	        // Act
	        int result = commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(benFlowID, benRegID, benVisitID, visitReason, visitCategory, nurseFlag, docFlag, labIteration, radiologistFlag, oncologistFlag, visitCode, vanID, specialistFlag, tcDate, tcSpecialistUserID);

	        // Assert
	        assertEquals(1, result);
	        verify(beneficiaryFlowStatusRepo, times(1)).updateBenFlowStatusAfterNurseActivity(benFlowID, benRegID, benVisitID, visitReason, visitCategory, nurseFlag, docFlag, labIteration, radiologistFlag, oncologistFlag, visitCode, vanID, specialistFlag, tcDate, tcSpecialistUserID);
	    }

	    @Test
	    public void testUpdateBenFlowNurseAfterNurseActivity_Exception() {
	        // Arrange
	        Long benFlowID = 1L;
	        Long benRegID = 1L;
	        Long benVisitID = 1L;
	        String visitReason = "Reason";
	        String visitCategory = "Category";
	        Short nurseFlag = 1;
	        Short docFlag = 1;
	        Short labIteration = 1;
	        Short radiologistFlag = 1;
	        Short oncologistFlag = 1;
	        Long visitCode = 1L;
	        Integer vanID = 1;
	        Short specialistFlag = 1;
	        Timestamp tcDate = new Timestamp(System.currentTimeMillis());
	        Integer tcSpecialistUserID = 1;

	        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterNurseActivity(anyLong(), anyLong(), anyLong(), anyString(), anyString(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyLong(), anyInt(), anyShort(), any(Timestamp.class), anyInt())).thenThrow(new RuntimeException("Exception"));

	        // Act
	        int result = commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(benFlowID, benRegID, benVisitID, visitReason, visitCategory, nurseFlag, docFlag, labIteration, radiologistFlag, oncologistFlag, visitCode, vanID, specialistFlag, tcDate, tcSpecialistUserID);

	        // Assert
	        assertEquals(0, result);
	        verify(beneficiaryFlowStatusRepo, times(1)).updateBenFlowStatusAfterNurseActivity(benFlowID, benRegID, benVisitID, visitReason, visitCategory, nurseFlag, docFlag, labIteration, radiologistFlag, oncologistFlag, visitCode, vanID, specialistFlag, tcDate, tcSpecialistUserID);
	        verify(logger, times(1)).error(anyString(), any(RuntimeException.class));
	    }
	    @Test
	    public void testUpdateBenFlowNurseAfterNurseActivityANC_Success() {
	        Long benFlowID = 1L;
	        Long benRegID = 1L;
	        Long benVisitID = 1L;
	        String visitReason = "Routine Checkup";
	        String visitCategory = "ANC";
	        Short nurseFlag = 1;
	        Short docFlag = 0;
	        Short labIteration = 1;
	        Short radiologistFlag = 0;
	        Short oncologistFlag = 0;
	        Long visitCode = 1L;
	        Integer vanID = 1;
	        Short specialistFlag = 0;
	        Timestamp tcDate = new Timestamp(System.currentTimeMillis());
	        Integer tcSpecialistUserID = 1;
	        Short labTechnician = 1;

	        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterNurseActivityANC(
	            benFlowID, benRegID, benVisitID, visitReason, visitCategory, nurseFlag, docFlag, labIteration, 
	            radiologistFlag, oncologistFlag, visitCode, vanID, specialistFlag, tcDate, tcSpecialistUserID, labTechnician))
	            .thenReturn(1);

	        int result = commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivityANC(
	            benFlowID, benRegID, benVisitID, visitReason, visitCategory, nurseFlag, docFlag, labIteration, 
	            radiologistFlag, oncologistFlag, visitCode, vanID, specialistFlag, tcDate, tcSpecialistUserID, labTechnician);

	        assertEquals(1, result);
	        verify(beneficiaryFlowStatusRepo, times(1)).updateBenFlowStatusAfterNurseActivityANC(
	            benFlowID, benRegID, benVisitID, visitReason, visitCategory, nurseFlag, docFlag, labIteration, 
	            radiologistFlag, oncologistFlag, visitCode, vanID, specialistFlag, tcDate, tcSpecialistUserID, labTechnician);
	    }

	    @Test
	    public void testUpdateBenFlowNurseAfterNurseActivityANC_Failure() {
	        Long benFlowID = 1L;
	        Long benRegID = 1L;
	        Long benVisitID = 1L;
	        String visitReason = "Routine Checkup";
	        String visitCategory = "ANC";
	        Short nurseFlag = 1;
	        Short docFlag = 0;
	        Short labIteration = 1;
	        Short radiologistFlag = 0;
	        Short oncologistFlag = 0;
	        Long visitCode = 1L;
	        Integer vanID = 1;
	        Short specialistFlag = 0;
	        Timestamp tcDate = new Timestamp(System.currentTimeMillis());
	        Integer tcSpecialistUserID = 1;
	        Short labTechnician = 1;

	        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterNurseActivityANC(
	            benFlowID, benRegID, benVisitID, visitReason, visitCategory, nurseFlag, docFlag, labIteration, 
	            radiologistFlag, oncologistFlag, visitCode, vanID, specialistFlag, tcDate, tcSpecialistUserID, labTechnician))
	            .thenReturn(0);

	        int result = commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivityANC(
	            benFlowID, benRegID, benVisitID, visitReason, visitCategory, nurseFlag, docFlag, labIteration, 
	            radiologistFlag, oncologistFlag, visitCode, vanID, specialistFlag, tcDate, tcSpecialistUserID, labTechnician);

	        assertEquals(0, result);
	        verify(beneficiaryFlowStatusRepo, times(1)).updateBenFlowStatusAfterNurseActivityANC(
	            benFlowID, benRegID, benVisitID, visitReason, visitCategory, nurseFlag, docFlag, labIteration, 
	            radiologistFlag, oncologistFlag, visitCode, vanID, specialistFlag, tcDate, tcSpecialistUserID, labTechnician);
	    }

	    @Test
	    public void testUpdateBenFlowNurseAfterNurseActivityANC_Exception() {
	        Long benFlowID = 1L;
	        Long benRegID = 1L;
	        Long benVisitID = 1L;
	        String visitReason = "Routine Checkup";
	        String visitCategory = "ANC";
	        Short nurseFlag = 1;
	        Short docFlag = 0;
	        Short labIteration = 1;
	        Short radiologistFlag = 0;
	        Short oncologistFlag = 0;
	        Long visitCode = 1L;
	        Integer vanID = 1;
	        Short specialistFlag = 0;
	        Timestamp tcDate = new Timestamp(System.currentTimeMillis());
	        Integer tcSpecialistUserID = 1;
	        Short labTechnician = 1;

	        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterNurseActivityANC(
	            benFlowID, benRegID, benVisitID, visitReason, visitCategory, nurseFlag, docFlag, labIteration, 
	            radiologistFlag, oncologistFlag, visitCode, vanID, specialistFlag, tcDate, tcSpecialistUserID, labTechnician))
	            .thenThrow(new RuntimeException("Database error"));

	        int result = commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivityANC(
	            benFlowID, benRegID, benVisitID, visitReason, visitCategory, nurseFlag, docFlag, labIteration, 
	            radiologistFlag, oncologistFlag, visitCode, vanID, specialistFlag, tcDate, tcSpecialistUserID, labTechnician);

	        assertEquals(0, result);
	        verify(beneficiaryFlowStatusRepo, times(1)).updateBenFlowStatusAfterNurseActivityANC(
	            benFlowID, benRegID, benVisitID, visitReason, visitCategory, nurseFlag, docFlag, labIteration, 
	            radiologistFlag, oncologistFlag, visitCode, vanID, specialistFlag, tcDate, tcSpecialistUserID, labTechnician);
	    }
	    @Test
	    public void testUpdateBenFlowNurseAfterNurseUpdateNCD_Screening_Success() {
	        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterNurseDataUpdateNCD_Screening(benFlowID, benRegID, nurseFlag)).thenReturn(1);

	        int result = commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseUpdateNCD_Screening(benFlowID, benRegID, nurseFlag);

	        assertEquals(1, result);
	        verify(beneficiaryFlowStatusRepo, times(1)).updateBenFlowStatusAfterNurseDataUpdateNCD_Screening(benFlowID, benRegID, nurseFlag);
	    }

	    @Test
	    public void testUpdateBenFlowNurseAfterNurseUpdateNCD_Screening_Exception() {
	        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterNurseDataUpdateNCD_Screening(benFlowID, benRegID, nurseFlag)).thenThrow(new RuntimeException("Database error"));

	        int result = commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseUpdateNCD_Screening(benFlowID, benRegID, nurseFlag);

	        assertEquals(0, result);
	        verify(beneficiaryFlowStatusRepo, times(1)).updateBenFlowStatusAfterNurseDataUpdateNCD_Screening(benFlowID, benRegID, nurseFlag);
	    }
	    @Test
	    public void testGetBenFlowRecordObj_ValidInput() throws Exception {
	        BeneficiaryFlowStatus result = commonBenStatusFlowServiceImpl.getBenFlowRecordObj(requestOBJ, beneficiaryRegID, beneficiaryID);

	        assertNotNull(result);
	        assertEquals("John Doe", result.getBenName());
	        assertEquals("1234567890", result.getPreferredPhoneNum());
	        assertEquals(1, result.getDistrictID());
	        assertEquals("District", result.getDistrictName());
	        assertEquals(1, result.getVillageID());
	        assertEquals("Branch", result.getVillageName());
	        assertEquals(1, result.getServicePointID());
	        assertEquals("Service Point", result.getServicePointName());
	        assertEquals(1, result.getGenderID());
	        assertEquals("Male", result.getGenderName());
	        assertEquals(1, result.getBeneficiaryRegID());
	        assertEquals(1, result.getBeneficiaryID());

	        LocalDate birthdate = LocalDate.of(1990, 1, 1);
	        Period p = Period.between(birthdate, LocalDate.now());
	        String expectedAgeDetails = p.getYears() + " years - " + p.getMonths() + " months";
	        assertEquals(expectedAgeDetails, result.getAge());
	        assertEquals(p.getYears(), result.getBen_age_val());
	    }

	    @Test
	    public void testGetBenFlowRecordObj_NullOptionalFields() throws Exception {
	        String requestOBJ = "{ \"firstName\": \"John\", \"dOB\": \"1990-01-01\" }";
	        BeneficiaryFlowStatus result = commonBenStatusFlowServiceImpl.getBenFlowRecordObj(requestOBJ, beneficiaryRegID, beneficiaryID);

	        assertNotNull(result);
	        assertEquals("John", result.getBenName());
	        assertNull(result.getPreferredPhoneNum());
	        assertNull(result.getDistrictID());
	        assertNull(result.getDistrictName());
	        assertNull(result.getVillageID());
	        assertNull(result.getVillageName());
	        assertNull(result.getServicePointID());
	        assertNull(result.getServicePointName());
	        assertNull(result.getGenderID());
	        assertNull(result.getGenderName());
	        assertEquals(1, result.getBeneficiaryRegID());
	        assertEquals(1, result.getBeneficiaryID());

	        LocalDate birthdate = LocalDate.of(1990, 1, 1);
	        Period p = Period.between(birthdate, LocalDate.now());
	        String expectedAgeDetails = p.getYears() + " years - " + p.getMonths() + " months";
	        assertEquals(expectedAgeDetails, result.getAge());
	        assertEquals(p.getYears(), result.getBen_age_val());
	    }

	    @Test
	    public void testGetBenFlowRecordObj_EdgeCaseDateCalculation() throws Exception {
	        String requestOBJ = "{ \"firstName\": \"John\", \"dOB\": \"2023-01-01\" }";
	        BeneficiaryFlowStatus result = commonBenStatusFlowServiceImpl.getBenFlowRecordObj(requestOBJ, beneficiaryRegID, beneficiaryID);

	        assertNotNull(result);
	        assertEquals("John", result.getBenName());

	        LocalDate birthdate = LocalDate.of(2023, 1, 1);
	        Period p = Period.between(birthdate, LocalDate.now());
	        String expectedAgeDetails;
	        if (p.getYears() > 0) {
	            expectedAgeDetails = p.getYears() + " years - " + p.getMonths() + " months";
	        } else if (p.getMonths() > 0) {
	            expectedAgeDetails = p.getMonths() + " months - " + p.getDays() + " days";
	        } else {
	            expectedAgeDetails = p.getDays() + " days";
	        }
	        assertEquals(expectedAgeDetails, result.getAge());
	        assertEquals(p.getYears(), result.getBen_age_val());
	    }
	    @Test
	    public void testUpdateBenFlowAfterDocData_Success() {
	        Long benFlowID = 1L;
	        Long benRegID = 1L;
	        Long benID = 1L;
	        Long benVisitID = 1L;
	        short docFlag = 1;
	        short pharmaFlag = 1;
	        short oncologistFlag = 1;
	        short tcSpecialistFlag = 1;
	        int tcUserID = 1;
	        Timestamp tcDate = new Timestamp(System.currentTimeMillis());
	        short labTechnicianFlag = 1;

	        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterDoctorActivity(benFlowID, benRegID, benID, docFlag, pharmaFlag, oncologistFlag, tcSpecialistFlag, tcUserID, tcDate, labTechnicianFlag)).thenReturn(1);

	        int result = commonBenStatusFlowServiceImpl.updateBenFlowAfterDocData(benFlowID, benRegID, benID, benVisitID, docFlag, pharmaFlag, oncologistFlag, tcSpecialistFlag, tcUserID, tcDate, labTechnicianFlag);

	        assertEquals(1, result);
	        verify(beneficiaryFlowStatusRepo, times(1)).updateBenFlowStatusAfterDoctorActivity(benFlowID, benRegID, benID, docFlag, pharmaFlag, oncologistFlag, tcSpecialistFlag, tcUserID, tcDate, labTechnicianFlag);
	    }

	    @Test
	    public void testUpdateBenFlowAfterDocData_Exception() {
	        Long benFlowID = 1L;
	        Long benRegID = 1L;
	        Long benID = 1L;
	        Long benVisitID = 1L;
	        short docFlag = 1;
	        short pharmaFlag = 1;
	        short oncologistFlag = 1;
	        short tcSpecialistFlag = 1;
	        int tcUserID = 1;
	        Timestamp tcDate = new Timestamp(System.currentTimeMillis());
	        short labTechnicianFlag = 1;

	        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterDoctorActivity(benFlowID, benRegID, benID, docFlag, pharmaFlag, oncologistFlag, tcSpecialistFlag, tcUserID, tcDate, labTechnicianFlag)).thenThrow(new RuntimeException("Database error"));

	        int result = commonBenStatusFlowServiceImpl.updateBenFlowAfterDocData(benFlowID, benRegID, benID, benVisitID, docFlag, pharmaFlag, oncologistFlag, tcSpecialistFlag, tcUserID, tcDate, labTechnicianFlag);

	        assertEquals(0, result);
	        verify(logger, times(1)).error(anyString(), any(RuntimeException.class));
	    }
	    @Test
	    public void testUpdateBenFlowAfterDocDataFromSpecialistANC_Success() {
	        Long benFlowID = 1L;
	        Long benRegID = 1L;
	        Long benID = 1L;
	        Long benVisitID = 1L;
	        short docFlag = 1;
	        short pharmaFlag = 1;
	        short oncologistFlag = 1;
	        short tcSpecialistFlag = 1;
	        short labTechnicianFlag = 1;

	        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterDoctorActivitySpecialistANC(
	                benFlowID, benRegID, benID, docFlag, pharmaFlag, oncologistFlag, tcSpecialistFlag, labTechnicianFlag))
	                .thenReturn(1);

	        int result = commonBenStatusFlowServiceImpl.updateBenFlowAfterDocDataFromSpecialistANC(
	                benFlowID, benRegID, benID, benVisitID, docFlag, pharmaFlag, oncologistFlag, tcSpecialistFlag, labTechnicianFlag);

	        assertEquals(1, result);
	        verify(beneficiaryFlowStatusRepo, times(1)).updateBenFlowStatusAfterDoctorActivitySpecialistANC(
	                benFlowID, benRegID, benID, docFlag, pharmaFlag, oncologistFlag, tcSpecialistFlag, labTechnicianFlag);
	    }

	    @Test
	    public void testUpdateBenFlowAfterDocDataFromSpecialistANC_Exception() {
	        Long benFlowID = 1L;
	        Long benRegID = 1L;
	        Long benID = 1L;
	        Long benVisitID = 1L;
	        short docFlag = 1;
	        short pharmaFlag = 1;
	        short oncologistFlag = 1;
	        short tcSpecialistFlag = 1;
	        short labTechnicianFlag = 1;

	        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterDoctorActivitySpecialistANC(
	                benFlowID, benRegID, benID, docFlag, pharmaFlag, oncologistFlag, tcSpecialistFlag, labTechnicianFlag))
	                .thenThrow(new RuntimeException("Test Exception"));

	        int result = commonBenStatusFlowServiceImpl.updateBenFlowAfterDocDataFromSpecialistANC(
	                benFlowID, benRegID, benID, benVisitID, docFlag, pharmaFlag, oncologistFlag, tcSpecialistFlag, labTechnicianFlag);

	        assertEquals(0, result);
	        verify(logger, times(1)).error(anyString(), any(RuntimeException.class));
	    }
	    @Test
	    public void testUpdateBenFlowAfterDocDataUpdate_Success() throws Exception {
	        when(beneficiaryFlowStatusRepo.getPharmaFlag(benFlowID)).thenReturn((short) 1);
	        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterDoctorActivity(anyLong(), anyLong(), anyLong(), anyShort(), anyShort(), anyShort(), anyShort(), anyInt(), any(Timestamp.class), anyShort())).thenReturn(1);

	        int result = commonBenStatusFlowServiceImpl.updateBenFlowAfterDocDataUpdate(benFlowID, benRegID, benID, benVisitID, docFlag, pharmaFlag, oncologistFlag, tcSpecialistFlag, tcUserID, tcDate, labTechnicianFlag);

	        assertEquals(1, result);
	        verify(beneficiaryFlowStatusRepo, times(1)).getPharmaFlag(benFlowID);
	        verify(beneficiaryFlowStatusRepo, times(1)).updateBenFlowStatusAfterDoctorActivity(benFlowID, benRegID, benID, docFlag, pharmaFlag, oncologistFlag, tcSpecialistFlag, tcUserID, tcDate, labTechnicianFlag);
	    }

	    @Test
	    public void testUpdateBenFlowAfterDocDataUpdate_Exception() {
	        when(beneficiaryFlowStatusRepo.getPharmaFlag(benFlowID)).thenThrow(new RuntimeException("Database error"));

	        Exception exception = assertThrows(Exception.class, () -> {
	            commonBenStatusFlowServiceImpl.updateBenFlowAfterDocDataUpdate(benFlowID, benRegID, benID, benVisitID, docFlag, pharmaFlag, oncologistFlag, tcSpecialistFlag, tcUserID, tcDate, labTechnicianFlag);
	        });

	        assertTrue(exception.getMessage().contains("Database error"));
	        verify(beneficiaryFlowStatusRepo, times(1)).getPharmaFlag(benFlowID);
	        verify(beneficiaryFlowStatusRepo, times(0)).updateBenFlowStatusAfterDoctorActivity(anyLong(), anyLong(), anyLong(), anyShort(), anyShort(), anyShort(), anyShort(), anyInt(), any(Timestamp.class), anyShort());
	    }
	    @Test
	    public void testUpdateBenFlowAfterDocDataUpdateTCSpecialist_PharmaFlagNotSet() throws Exception {
	        when(beneficiaryFlowStatusRepo.getPharmaFlag(benFlowID)).thenReturn(null);

	        int result = commonBenStatusFlowServiceImpl.updateBenFlowAfterDocDataUpdateTCSpecialist(
	                benFlowID, benRegID, benID, benVisitID, docFlag, pharmaFlag, oncologistFlag, tcSpecialistFlag, tcUserID, tcDate, labTechnicianFlag);

	        assertEquals(0, result);
	        verify(beneficiaryFlowStatusRepo, times(1)).updateBenFlowStatusAfterDoctorActivityTCSpecialist(
	                benFlowID, benRegID, benID, pharmaFlag, oncologistFlag, tcSpecialistFlag, labTechnicianFlag);
	    }

	    @Test
	    public void testUpdateBenFlowAfterDocDataUpdateTCSpecialist_PharmaFlagSet() throws Exception {
	        when(beneficiaryFlowStatusRepo.getPharmaFlag(benFlowID)).thenReturn((short) 1);

	        int result = commonBenStatusFlowServiceImpl.updateBenFlowAfterDocDataUpdateTCSpecialist(
	                benFlowID, benRegID, benID, benVisitID, docFlag, pharmaFlag, oncologistFlag, tcSpecialistFlag, tcUserID, tcDate, labTechnicianFlag);

	        assertEquals(0, result);
	        verify(beneficiaryFlowStatusRepo, times(1)).updateBenFlowStatusAfterDoctorActivityTCSpecialist(
	                benFlowID, benRegID, benID, (short) 1, oncologistFlag, tcSpecialistFlag, labTechnicianFlag);
	    }

	    @Test
	    public void testUpdateBenFlowAfterDocDataUpdateTCSpecialist_Exception() {
	        when(beneficiaryFlowStatusRepo.getPharmaFlag(benFlowID)).thenThrow(new RuntimeException("Database error"));

	        Exception exception = assertThrows(Exception.class, () -> {
	            commonBenStatusFlowServiceImpl.updateBenFlowAfterDocDataUpdateTCSpecialist(
	                    benFlowID, benRegID, benID, benVisitID, docFlag, pharmaFlag, oncologistFlag, tcSpecialistFlag, tcUserID, tcDate, labTechnicianFlag);
	        });

	        assertTrue(exception.getMessage().contains("Database error"));
	        verify(beneficiaryFlowStatusRepo, times(1)).getPharmaFlag(benFlowID);
	    }
	    @Test
	    public void testUpdateFlowAfterLabResultEntry() {
	        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterLabResultEntry(benFlowID, benRegID, nurseFlag, doctorFlag)).thenReturn(1);

	        int result = commonBenStatusFlowServiceImpl.updateFlowAfterLabResultEntry(benFlowID, benRegID, benVisitID, nurseFlag, doctorFlag, labFlag);

	        assertEquals(1, result);
	        verify(beneficiaryFlowStatusRepo, times(1)).updateBenFlowStatusAfterLabResultEntry(benFlowID, benRegID, nurseFlag, doctorFlag);
	    }

	    @Test
	    public void testUpdateFlowAfterLabResultEntryForTCSpecialist() {
	        Short specialistFlag = 1;
	        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterLabResultEntryForSpecialist(benFlowID, benRegID, specialistFlag)).thenReturn(1);

	        int result = commonBenStatusFlowServiceImpl.updateFlowAfterLabResultEntryForTCSpecialist(benFlowID, benRegID, specialistFlag);

	        assertEquals(1, result);
	        verify(beneficiaryFlowStatusRepo, times(1)).updateBenFlowStatusAfterLabResultEntryForSpecialist(benFlowID, benRegID, specialistFlag);
	    }

	    @Test
	    public void testUpdateFlowAfterLabResultEntryForTCSpecialist_Success() {
	        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterLabResultEntryForSpecialist(benFlowID, benRegID, specialistFlag)).thenReturn(1);

	        int result = commonBenStatusFlowServiceImpl.updateFlowAfterLabResultEntryForTCSpecialist(benFlowID, benRegID, specialistFlag);

	        assertEquals(1, result);
	        verify(beneficiaryFlowStatusRepo, times(1)).updateBenFlowStatusAfterLabResultEntryForSpecialist(benFlowID, benRegID, specialistFlag);
	    }

	    @Test
	    public void testUpdateFlowAfterLabResultEntryForTCSpecialist_Exception() {
	        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterLabResultEntryForSpecialist(benFlowID, benRegID, specialistFlag)).thenThrow(new RuntimeException("Database error"));

	        Exception exception = assertThrows(RuntimeException.class, () -> {
	            commonBenStatusFlowServiceImpl.updateFlowAfterLabResultEntryForTCSpecialist(benFlowID, benRegID, specialistFlag);
	        });

	        assertEquals("Database error", exception.getMessage());
	        verify(beneficiaryFlowStatusRepo, times(1)).updateBenFlowStatusAfterLabResultEntryForSpecialist(benFlowID, benRegID, specialistFlag);
	    }
	}
	

