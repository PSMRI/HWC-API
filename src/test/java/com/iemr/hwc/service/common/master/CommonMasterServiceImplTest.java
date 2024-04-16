package com.iemr.hwc.service.common.master;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.iemr.hwc.data.neonatal.ImmunizationServiceDoseMaster;
import com.iemr.hwc.data.neonatal.ImmunizationServiceVaccinationMaster;
import com.iemr.hwc.repo.neonatal.ImmunizationServiceDoseMasterRepo;
import com.iemr.hwc.repo.neonatal.ImmunizationServiceRouteMasterRepo;
import com.iemr.hwc.repo.neonatal.ImmunizationServiceVaccinationMasterRepo;
import com.iemr.hwc.repo.neonatal.SiteOfInjectionMasterRepo;
import com.iemr.hwc.service.family_planning.FamilyPlanningMasterService;
import com.iemr.hwc.service.neonatal.NeonatalMasterService;
import com.iemr.hwc.utils.exception.IEMRException;

@ExtendWith(MockitoExtension.class)
class CommonMasterServiceImplTest {
    @Mock
    private ANCMasterDataServiceImpl aNCMasterDataServiceImpl;

    @InjectMocks
    private CommonMasterServiceImpl commonMasterServiceImpl;

    @Mock
    private DoctorMasterDataServiceImpl doctorMasterDataServiceImpl;

    @Mock
    private FamilyPlanningMasterService familyPlanningMasterService;

    @Mock
    private ImmunizationServiceDoseMasterRepo immunizationServiceDoseMasterRepo;

    @Mock
    private ImmunizationServiceRouteMasterRepo immunizationServiceRouteMasterRepo;

    @Mock
    private ImmunizationServiceVaccinationMasterRepo immunizationServiceVaccinationMasterRepo;

    @Mock
    private NCDScreeningMasterServiceImpl nCDScreeningMasterServiceImpl;

    @Mock
    private NeonatalMasterService neonatalMasterService;

    @Mock
    private NurseMasterDataServiceImpl nurseMasterDataServiceImpl;

    @Mock
    private SiteOfInjectionMasterRepo siteOfInjectionMasterRepo;

    @Test
    void testGetVisitReasonAndCategories() {
        // Arrange
        when(nurseMasterDataServiceImpl.GetVisitReasonAndCategories()).thenReturn("Just cause");

        // Act
        String actualVisitReasonAndCategories = commonMasterServiceImpl.getVisitReasonAndCategories();

        // Assert
        verify(nurseMasterDataServiceImpl).GetVisitReasonAndCategories();
        assertEquals("Just cause", actualVisitReasonAndCategories);
    }

    @Test
    void testGetMasterDataForNurse() throws IEMRException {
        // Arrange
        when(nurseMasterDataServiceImpl.getCancerScreeningMasterDataForNurse())
                .thenReturn("Cancer Screening Master Data For Nurse");

        // Act
        String actualMasterDataForNurse = commonMasterServiceImpl.getMasterDataForNurse(1, 1, "Gender");

        // Assert
        verify(nurseMasterDataServiceImpl).getCancerScreeningMasterDataForNurse();
        assertEquals("Cancer Screening Master Data For Nurse", actualMasterDataForNurse);
    }

    @Test
    void testGetMasterDataForNurse2() throws IEMRException {
        // Arrange
        when(nCDScreeningMasterServiceImpl.getNCDScreeningMasterData(Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<String>any())).thenReturn("Ncd Screening Master Data");

        // Act
        String actualMasterDataForNurse = commonMasterServiceImpl.getMasterDataForNurse(2, 1, "Gender");

        // Assert
        verify(nCDScreeningMasterServiceImpl).getNCDScreeningMasterData(isA(Integer.class), isA(Integer.class),
                eq("Gender"));
        assertEquals("Ncd Screening Master Data", actualMasterDataForNurse);
    }

    @Test
    void testGetMasterDataForNurse3() throws IEMRException {
        // Arrange
        when(aNCMasterDataServiceImpl.getCommonNurseMasterDataForGenopdAncNcdcarePnc(Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn("Common Nurse Master Data For Genopd Anc Ncdcare Pnc");

        // Act
        String actualMasterDataForNurse = commonMasterServiceImpl.getMasterDataForNurse(3, 1, "Gender");

        // Assert
        verify(aNCMasterDataServiceImpl).getCommonNurseMasterDataForGenopdAncNcdcarePnc(isA(Integer.class),
                isA(Integer.class), eq("Gender"));
        assertEquals("Common Nurse Master Data For Genopd Anc Ncdcare Pnc", actualMasterDataForNurse);
    }

    @Test
    void testGetMasterDataForNurse4() throws IEMRException {
        // Arrange
        when(aNCMasterDataServiceImpl.getCommonNurseMasterDataForGenopdAncNcdcarePnc(Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn("Common Nurse Master Data For Genopd Anc Ncdcare Pnc");

        // Act
        String actualMasterDataForNurse = commonMasterServiceImpl.getMasterDataForNurse(4, 1, "Gender");

        // Assert
        verify(aNCMasterDataServiceImpl).getCommonNurseMasterDataForGenopdAncNcdcarePnc(isA(Integer.class),
                isA(Integer.class), eq("Gender"));
        assertEquals("Common Nurse Master Data For Genopd Anc Ncdcare Pnc", actualMasterDataForNurse);
    }

    @Test
    void testGetMasterDataForNurse5() throws IEMRException {
        // Arrange
        when(aNCMasterDataServiceImpl.getCommonNurseMasterDataForGenopdAncNcdcarePnc(Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn("Common Nurse Master Data For Genopd Anc Ncdcare Pnc");

        // Act
        String actualMasterDataForNurse = commonMasterServiceImpl.getMasterDataForNurse(5, 1, "Gender");

        // Assert
        verify(aNCMasterDataServiceImpl).getCommonNurseMasterDataForGenopdAncNcdcarePnc(isA(Integer.class),
                isA(Integer.class), eq("Gender"));
        assertEquals("Common Nurse Master Data For Genopd Anc Ncdcare Pnc", actualMasterDataForNurse);
    }

    @Test
    void testGetMasterDataForNurse6() throws IEMRException {
        // Arrange
        when(aNCMasterDataServiceImpl.getCommonNurseMasterDataForGenopdAncNcdcarePnc(Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn("Common Nurse Master Data For Genopd Anc Ncdcare Pnc");

        // Act
        String actualMasterDataForNurse = commonMasterServiceImpl.getMasterDataForNurse(6, 1, "Gender");

        // Assert
        verify(aNCMasterDataServiceImpl).getCommonNurseMasterDataForGenopdAncNcdcarePnc(isA(Integer.class),
                isA(Integer.class), eq("Gender"));
        assertEquals("Common Nurse Master Data For Genopd Anc Ncdcare Pnc", actualMasterDataForNurse);
    }

    @Test
    void testGetMasterDataForNurse7() throws IEMRException {
        // Arrange, Act and Assert
        assertEquals("No Master Data found for QuickConsultation",
                commonMasterServiceImpl.getMasterDataForNurse(7, 1, "Gender"));
    }

    @Test
    void testGetMasterDataForNurse8() throws IEMRException {
        // Arrange
        when(aNCMasterDataServiceImpl.getCommonNurseMasterDataForGenopdAncNcdcarePnc(Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn("Common Nurse Master Data For Genopd Anc Ncdcare Pnc");

        // Act
        String actualMasterDataForNurse = commonMasterServiceImpl.getMasterDataForNurse(8, 1, "Gender");

        // Assert
        verify(aNCMasterDataServiceImpl).getCommonNurseMasterDataForGenopdAncNcdcarePnc(isA(Integer.class),
                isA(Integer.class), eq("Gender"));
        assertEquals("Common Nurse Master Data For Genopd Anc Ncdcare Pnc", actualMasterDataForNurse);
    }

    @Test
    void testGetMasterDataForNurse9() throws IEMRException {
        // Arrange
        when(familyPlanningMasterService.getMasterDataFP(Mockito.<Integer>any(), anyInt(), Mockito.<String>any()))
                .thenReturn("Master Data FP");

        // Act
        String actualMasterDataForNurse = commonMasterServiceImpl.getMasterDataForNurse(9, 1, "Gender");

        // Assert
        verify(familyPlanningMasterService).getMasterDataFP(isA(Integer.class), eq(1), eq("Gender"));
        assertEquals("Master Data FP", actualMasterDataForNurse);
    }

    @Test
    void testGetMasterDataForNurse10() throws IEMRException {
        // Arrange
        when(neonatalMasterService.getNurseMasterDataNeonatal(Mockito.<Integer>any(), anyInt(), Mockito.<String>any()))
                .thenReturn("Nurse Master Data Neonatal");

        // Act
        String actualMasterDataForNurse = commonMasterServiceImpl.getMasterDataForNurse(10, 1, "Gender");

        // Assert
        verify(neonatalMasterService).getNurseMasterDataNeonatal(isA(Integer.class), eq(1), eq("Gender"));
        assertEquals("Nurse Master Data Neonatal", actualMasterDataForNurse);
    }

    @Test
    void testGetMasterDataForNurse11() throws IEMRException {
        // Arrange, Act and Assert
        assertEquals("Invalid VisitCategoryID", commonMasterServiceImpl.getMasterDataForNurse(0, 1, "Gender"));
    }

    @Test
    void testGetMasterDataForNurse12() throws IEMRException {
        // Arrange, Act and Assert
        assertEquals("Invalid VisitCategoryID", commonMasterServiceImpl.getMasterDataForNurse(null, 1, "Gender"));
    }

    @Test
    void testGetMasterDataForNurse13() throws IEMRException {
        // Arrange
        when(neonatalMasterService.getNurseMasterDataNeonatal(Mockito.<Integer>any(), anyInt(), Mockito.<String>any()))
                .thenThrow(new IEMRException("An error occurred"));

        // Act and Assert
        assertThrows(IEMRException.class, () -> commonMasterServiceImpl.getMasterDataForNurse(10, 1, "Gender"));
        verify(neonatalMasterService).getNurseMasterDataNeonatal(isA(Integer.class), eq(1), eq("Gender"));
    }

    @Test
    void testGetMasterDataForNurse14() throws IEMRException {
        // Arrange
        when(neonatalMasterService.getNurseMasterDataNeonatal(Mockito.<Integer>any(), anyInt(), Mockito.<String>any()))
                .thenReturn("Nurse Master Data Neonatal");

        // Act
        String actualMasterDataForNurse = commonMasterServiceImpl.getMasterDataForNurse(11, 1, "Gender");

        // Assert
        verify(neonatalMasterService).getNurseMasterDataNeonatal(isA(Integer.class), eq(1), eq("Gender"));
        assertEquals("Nurse Master Data Neonatal", actualMasterDataForNurse);
    }

    @Test
    void testGetMasterDataForNurse15() throws IEMRException {
        // Arrange
        when(neonatalMasterService.getNurseMasterDataNeonatal(Mockito.<Integer>any(), anyInt(), Mockito.<String>any()))
                .thenThrow(new IEMRException("An error occurred"));

        // Act and Assert
        assertThrows(IEMRException.class, () -> commonMasterServiceImpl.getMasterDataForNurse(11, 1, "Gender"));
        verify(neonatalMasterService).getNurseMasterDataNeonatal(isA(Integer.class), eq(1), eq("Gender"));
    }

    @Test
    void testGetMasterDataForDoctor() {
        // Arrange
        when(doctorMasterDataServiceImpl.getCancerScreeningMasterDataForDoctor(anyInt()))
                .thenReturn("Cancer Screening Master Data For Doctor");

        // Act
        String actualMasterDataForDoctor = commonMasterServiceImpl.getMasterDataForDoctor(1, 1, "Gender", 1, 1);

        // Assert
        verify(doctorMasterDataServiceImpl).getCancerScreeningMasterDataForDoctor(eq(1));
        assertEquals("Cancer Screening Master Data For Doctor", actualMasterDataForDoctor);
    }

    @Test
    void testGetMasterDataForDoctor2() {
        // Arrange
        when(aNCMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(Mockito.<Integer>any(), anyInt(),
                Mockito.<String>any(), Mockito.<Integer>any(), Mockito.<Integer>any()))
                .thenReturn("Common Doctor Master Data For Genopd Anc Ncdcare Pnc");

        // Act
        String actualMasterDataForDoctor = commonMasterServiceImpl.getMasterDataForDoctor(2, 1, "Gender", 1, 1);

        // Assert
        verify(aNCMasterDataServiceImpl).getCommonDoctorMasterDataForGenopdAncNcdcarePnc(isA(Integer.class), eq(1),
                eq("Gender"), isA(Integer.class), isA(Integer.class));
        assertEquals("Common Doctor Master Data For Genopd Anc Ncdcare Pnc", actualMasterDataForDoctor);
    }

    @Test
    void testGetMasterDataForDoctor3() {
        // Arrange
        when(aNCMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(Mockito.<Integer>any(), anyInt(),
                Mockito.<String>any(), Mockito.<Integer>any(), Mockito.<Integer>any()))
                .thenReturn("Common Doctor Master Data For Genopd Anc Ncdcare Pnc");

        // Act
        String actualMasterDataForDoctor = commonMasterServiceImpl.getMasterDataForDoctor(3, 1, "Gender", 1, 1);

        // Assert
        verify(aNCMasterDataServiceImpl).getCommonDoctorMasterDataForGenopdAncNcdcarePnc(isA(Integer.class), eq(1),
                eq("Gender"), isA(Integer.class), isA(Integer.class));
        assertEquals("Common Doctor Master Data For Genopd Anc Ncdcare Pnc", actualMasterDataForDoctor);
    }

    @Test
    void testGetMasterDataForDoctor4() {
        // Arrange
        when(aNCMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(Mockito.<Integer>any(), anyInt(),
                Mockito.<String>any(), Mockito.<Integer>any(), Mockito.<Integer>any()))
                .thenReturn("Common Doctor Master Data For Genopd Anc Ncdcare Pnc");

        // Act
        String actualMasterDataForDoctor = commonMasterServiceImpl.getMasterDataForDoctor(4, 1, "Gender", 1, 1);

        // Assert
        verify(aNCMasterDataServiceImpl).getCommonDoctorMasterDataForGenopdAncNcdcarePnc(isA(Integer.class), eq(1),
                eq("Gender"), isA(Integer.class), isA(Integer.class));
        assertEquals("Common Doctor Master Data For Genopd Anc Ncdcare Pnc", actualMasterDataForDoctor);
    }

    @Test
    void testGetMasterDataForDoctor5() {
        // Arrange
        when(aNCMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(Mockito.<Integer>any(), anyInt(),
                Mockito.<String>any(), Mockito.<Integer>any(), Mockito.<Integer>any()))
                .thenReturn("Common Doctor Master Data For Genopd Anc Ncdcare Pnc");

        // Act
        String actualMasterDataForDoctor = commonMasterServiceImpl.getMasterDataForDoctor(5, 1, "Gender", 1, 1);

        // Assert
        verify(aNCMasterDataServiceImpl).getCommonDoctorMasterDataForGenopdAncNcdcarePnc(isA(Integer.class), eq(1),
                eq("Gender"), isA(Integer.class), isA(Integer.class));
        assertEquals("Common Doctor Master Data For Genopd Anc Ncdcare Pnc", actualMasterDataForDoctor);
    }

    @Test
    void testGetMasterDataForDoctor6() {
        // Arrange
        when(aNCMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(Mockito.<Integer>any(), anyInt(),
                Mockito.<String>any(), Mockito.<Integer>any(), Mockito.<Integer>any()))
                .thenReturn("Common Doctor Master Data For Genopd Anc Ncdcare Pnc");

        // Act
        String actualMasterDataForDoctor = commonMasterServiceImpl.getMasterDataForDoctor(6, 1, "Gender", 1, 1);

        // Assert
        verify(aNCMasterDataServiceImpl).getCommonDoctorMasterDataForGenopdAncNcdcarePnc(isA(Integer.class), eq(1),
                eq("Gender"), isA(Integer.class), isA(Integer.class));
        assertEquals("Common Doctor Master Data For Genopd Anc Ncdcare Pnc", actualMasterDataForDoctor);
    }

    @Test
    void testGetMasterDataForDoctor7() {
        // Arrange
        when(aNCMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(Mockito.<Integer>any(), anyInt(),
                Mockito.<String>any(), Mockito.<Integer>any(), Mockito.<Integer>any()))
                .thenReturn("Common Doctor Master Data For Genopd Anc Ncdcare Pnc");

        // Act
        String actualMasterDataForDoctor = commonMasterServiceImpl.getMasterDataForDoctor(7, 1, "Gender", 1, 1);

        // Assert
        verify(aNCMasterDataServiceImpl).getCommonDoctorMasterDataForGenopdAncNcdcarePnc(isA(Integer.class), eq(1),
                eq("Gender"), isA(Integer.class), isA(Integer.class));
        assertEquals("Common Doctor Master Data For Genopd Anc Ncdcare Pnc", actualMasterDataForDoctor);
    }

    @Test
    void testGetMasterDataForDoctor8() {
        // Arrange
        when(aNCMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(Mockito.<Integer>any(), anyInt(),
                Mockito.<String>any(), Mockito.<Integer>any(), Mockito.<Integer>any()))
                .thenReturn("Common Doctor Master Data For Genopd Anc Ncdcare Pnc");

        // Act
        String actualMasterDataForDoctor = commonMasterServiceImpl.getMasterDataForDoctor(8, 1, "Gender", 1, 1);

        // Assert
        verify(aNCMasterDataServiceImpl).getCommonDoctorMasterDataForGenopdAncNcdcarePnc(isA(Integer.class), eq(1),
                eq("Gender"), isA(Integer.class), isA(Integer.class));
        assertEquals("Common Doctor Master Data For Genopd Anc Ncdcare Pnc", actualMasterDataForDoctor);
    }

    @Test
    void testGetMasterDataForDoctor9() {
        // Arrange
        when(aNCMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(Mockito.<Integer>any(), anyInt(),
                Mockito.<String>any(), Mockito.<Integer>any(), Mockito.<Integer>any()))
                .thenReturn("Common Doctor Master Data For Genopd Anc Ncdcare Pnc");

        // Act
        String actualMasterDataForDoctor = commonMasterServiceImpl.getMasterDataForDoctor(9, 1, "Gender", 1, 1);

        // Assert
        verify(aNCMasterDataServiceImpl).getCommonDoctorMasterDataForGenopdAncNcdcarePnc(isA(Integer.class), eq(1),
                eq("Gender"), isA(Integer.class), isA(Integer.class));
        assertEquals("Common Doctor Master Data For Genopd Anc Ncdcare Pnc", actualMasterDataForDoctor);
    }

    @Test
    void testGetMasterDataForDoctor10() {
        // Arrange
        when(aNCMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(Mockito.<Integer>any(), anyInt(),
                Mockito.<String>any(), Mockito.<Integer>any(), Mockito.<Integer>any()))
                .thenReturn("Common Doctor Master Data For Genopd Anc Ncdcare Pnc");

        // Act
        String actualMasterDataForDoctor = commonMasterServiceImpl.getMasterDataForDoctor(10, 1, "Gender", 1, 1);

        // Assert
        verify(aNCMasterDataServiceImpl).getCommonDoctorMasterDataForGenopdAncNcdcarePnc(isA(Integer.class), eq(1),
                eq("Gender"), isA(Integer.class), isA(Integer.class));
        assertEquals("Common Doctor Master Data For Genopd Anc Ncdcare Pnc", actualMasterDataForDoctor);
    }

    @Test
    void testGetMasterDataForDoctor11() {
        // Arrange, Act and Assert
        assertEquals("Invalid VisitCategoryID", commonMasterServiceImpl.getMasterDataForDoctor(0, 1, "Gender", 1, 1));
    }

    @Test
    void testGetMasterDataForDoctor12() {
        // Arrange, Act and Assert
        assertEquals("Invalid VisitCategoryID", commonMasterServiceImpl.getMasterDataForDoctor(null, 1, "Gender", 1, 1));
    }

    @Test
    void testGetMasterDataForDoctor13() {
        // Arrange
        when(aNCMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(Mockito.<Integer>any(), anyInt(),
                Mockito.<String>any(), Mockito.<Integer>any(), Mockito.<Integer>any()))
                .thenReturn("Common Doctor Master Data For Genopd Anc Ncdcare Pnc");

        // Act
        String actualMasterDataForDoctor = commonMasterServiceImpl.getMasterDataForDoctor(11, 1, "Gender", 1, 1);

        // Assert
        verify(aNCMasterDataServiceImpl).getCommonDoctorMasterDataForGenopdAncNcdcarePnc(isA(Integer.class), eq(1),
                eq("Gender"), isA(Integer.class), isA(Integer.class));
        assertEquals("Common Doctor Master Data For Genopd Anc Ncdcare Pnc", actualMasterDataForDoctor);
    }

    @Test
    void testGetVaccineDetailsForCISID() throws IEMRException {
        // Arrange
        when(immunizationServiceVaccinationMasterRepo.findByCurrentImmunizationServiceIdAndDeletedAndVisitCategoryID(
                Mockito.<Integer>any(), Mockito.<Boolean>any(), Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        String actualVaccineDetailsForCISID = commonMasterServiceImpl.getVaccineDetailsForCISID(1, 1);

        // Assert
        verify(immunizationServiceVaccinationMasterRepo).findByCurrentImmunizationServiceIdAndDeletedAndVisitCategoryID(
                isA(Integer.class), isA(Boolean.class), isA(Integer.class));
        assertEquals("{\"vaccineList\":[]}", actualVaccineDetailsForCISID);
    }

    @Test
    void testGetVaccineDetailsForCISID2() throws IEMRException {
        // Arrange
        when(immunizationServiceDoseMasterRepo.findByVaccinationIDAndDeletedOrderByDose(Mockito.<Integer>any(),
                Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(immunizationServiceRouteMasterRepo.findByVaccinationIDAndDeletedOrderByRoute(Mockito.<Integer>any(),
                Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        ImmunizationServiceVaccinationMaster immunizationServiceVaccinationMaster = new ImmunizationServiceVaccinationMaster();
        immunizationServiceVaccinationMaster.setCurrentImmunizationServiceId(1);
        immunizationServiceVaccinationMaster.setDeleted(true);
        immunizationServiceVaccinationMaster.setVaccinationID(1);
        immunizationServiceVaccinationMaster.setVaccineName("vaccineList");
        immunizationServiceVaccinationMaster.setVisitCategoryID(1);

        ArrayList<ImmunizationServiceVaccinationMaster> immunizationServiceVaccinationMasterList = new ArrayList<>();
        immunizationServiceVaccinationMasterList.add(immunizationServiceVaccinationMaster);
        when(immunizationServiceVaccinationMasterRepo.findByCurrentImmunizationServiceIdAndDeletedAndVisitCategoryID(
                Mockito.<Integer>any(), Mockito.<Boolean>any(), Mockito.<Integer>any()))
                .thenReturn(immunizationServiceVaccinationMasterList);
        when(siteOfInjectionMasterRepo.findByVaccinationIDAndDeletedOrderBySiteofinjection(Mockito.<Integer>any(),
                Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        // Act
        String actualVaccineDetailsForCISID = commonMasterServiceImpl.getVaccineDetailsForCISID(1, 1);

        // Assert
        verify(immunizationServiceDoseMasterRepo).findByVaccinationIDAndDeletedOrderByDose(isA(Integer.class),
                isA(Boolean.class));
        verify(immunizationServiceRouteMasterRepo).findByVaccinationIDAndDeletedOrderByRoute(isA(Integer.class),
                isA(Boolean.class));
        verify(immunizationServiceVaccinationMasterRepo).findByCurrentImmunizationServiceIdAndDeletedAndVisitCategoryID(
                isA(Integer.class), isA(Boolean.class), isA(Integer.class));
        verify(siteOfInjectionMasterRepo).findByVaccinationIDAndDeletedOrderBySiteofinjection(isA(Integer.class),
                isA(Boolean.class));
        assertEquals(
                "{\"vaccineList\":[{\"vaccineId\":1,\"vaccine\":\"vaccineList\",\"dose\":[],\"route\":[],\"siteOfInjection\":[]}]}",
                actualVaccineDetailsForCISID);
    }

    @Test
    void testGetVaccineDetailsForCISID3() throws IEMRException {
        // Arrange
        ArrayList<ImmunizationServiceDoseMaster> immunizationServiceDoseMasterList = new ArrayList<>();
        immunizationServiceDoseMasterList.add(new ImmunizationServiceDoseMaster());
        when(immunizationServiceDoseMasterRepo.findByVaccinationIDAndDeletedOrderByDose(Mockito.<Integer>any(),
                Mockito.<Boolean>any())).thenReturn(immunizationServiceDoseMasterList);
        when(immunizationServiceRouteMasterRepo.findByVaccinationIDAndDeletedOrderByRoute(Mockito.<Integer>any(),
                Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        ImmunizationServiceVaccinationMaster immunizationServiceVaccinationMaster = new ImmunizationServiceVaccinationMaster();
        immunizationServiceVaccinationMaster.setCurrentImmunizationServiceId(1);
        immunizationServiceVaccinationMaster.setDeleted(true);
        immunizationServiceVaccinationMaster.setVaccinationID(1);
        immunizationServiceVaccinationMaster.setVaccineName("vaccineList");
        immunizationServiceVaccinationMaster.setVisitCategoryID(1);

        ArrayList<ImmunizationServiceVaccinationMaster> immunizationServiceVaccinationMasterList = new ArrayList<>();
        immunizationServiceVaccinationMasterList.add(immunizationServiceVaccinationMaster);
        when(immunizationServiceVaccinationMasterRepo.findByCurrentImmunizationServiceIdAndDeletedAndVisitCategoryID(
                Mockito.<Integer>any(), Mockito.<Boolean>any(), Mockito.<Integer>any()))
                .thenReturn(immunizationServiceVaccinationMasterList);
        when(siteOfInjectionMasterRepo.findByVaccinationIDAndDeletedOrderBySiteofinjection(Mockito.<Integer>any(),
                Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        // Act
        String actualVaccineDetailsForCISID = commonMasterServiceImpl.getVaccineDetailsForCISID(1, 1);

        // Assert
        verify(immunizationServiceDoseMasterRepo).findByVaccinationIDAndDeletedOrderByDose(isA(Integer.class),
                isA(Boolean.class));
        verify(immunizationServiceRouteMasterRepo).findByVaccinationIDAndDeletedOrderByRoute(isA(Integer.class),
                isA(Boolean.class));
        verify(immunizationServiceVaccinationMasterRepo).findByCurrentImmunizationServiceIdAndDeletedAndVisitCategoryID(
                isA(Integer.class), isA(Boolean.class), isA(Integer.class));
        verify(siteOfInjectionMasterRepo).findByVaccinationIDAndDeletedOrderBySiteofinjection(isA(Integer.class),
                isA(Boolean.class));
        assertEquals(
                "{\"vaccineList\":[{\"vaccineId\":1,\"vaccine\":\"vaccineList\",\"dose\":[{}],\"route\":[],\"siteOfInjection"
                        + "\":[]}]}",
                actualVaccineDetailsForCISID);
    }

    @Test
    void testGetVaccineDetailsForCISID4() throws IEMRException {
        // Arrange
        ArrayList<ImmunizationServiceDoseMaster> immunizationServiceDoseMasterList = new ArrayList<>();
        immunizationServiceDoseMasterList.add(new ImmunizationServiceDoseMaster());
        immunizationServiceDoseMasterList.add(new ImmunizationServiceDoseMaster());
        when(immunizationServiceDoseMasterRepo.findByVaccinationIDAndDeletedOrderByDose(Mockito.<Integer>any(),
                Mockito.<Boolean>any())).thenReturn(immunizationServiceDoseMasterList);
        when(immunizationServiceRouteMasterRepo.findByVaccinationIDAndDeletedOrderByRoute(Mockito.<Integer>any(),
                Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        ImmunizationServiceVaccinationMaster immunizationServiceVaccinationMaster = new ImmunizationServiceVaccinationMaster();
        immunizationServiceVaccinationMaster.setCurrentImmunizationServiceId(1);
        immunizationServiceVaccinationMaster.setDeleted(true);
        immunizationServiceVaccinationMaster.setVaccinationID(1);
        immunizationServiceVaccinationMaster.setVaccineName("vaccineList");
        immunizationServiceVaccinationMaster.setVisitCategoryID(1);

        ArrayList<ImmunizationServiceVaccinationMaster> immunizationServiceVaccinationMasterList = new ArrayList<>();
        immunizationServiceVaccinationMasterList.add(immunizationServiceVaccinationMaster);
        when(immunizationServiceVaccinationMasterRepo.findByCurrentImmunizationServiceIdAndDeletedAndVisitCategoryID(
                Mockito.<Integer>any(), Mockito.<Boolean>any(), Mockito.<Integer>any()))
                .thenReturn(immunizationServiceVaccinationMasterList);
        when(siteOfInjectionMasterRepo.findByVaccinationIDAndDeletedOrderBySiteofinjection(Mockito.<Integer>any(),
                Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        // Act
        String actualVaccineDetailsForCISID = commonMasterServiceImpl.getVaccineDetailsForCISID(1, 1);

        // Assert
        verify(immunizationServiceDoseMasterRepo).findByVaccinationIDAndDeletedOrderByDose(isA(Integer.class),
                isA(Boolean.class));
        verify(immunizationServiceRouteMasterRepo).findByVaccinationIDAndDeletedOrderByRoute(isA(Integer.class),
                isA(Boolean.class));
        verify(immunizationServiceVaccinationMasterRepo).findByCurrentImmunizationServiceIdAndDeletedAndVisitCategoryID(
                isA(Integer.class), isA(Boolean.class), isA(Integer.class));
        verify(siteOfInjectionMasterRepo).findByVaccinationIDAndDeletedOrderBySiteofinjection(isA(Integer.class),
                isA(Boolean.class));
        assertEquals(
                "{\"vaccineList\":[{\"vaccineId\":1,\"vaccine\":\"vaccineList\",\"dose\":[{},{}],\"route\":[],\"siteOfInjection"
                        + "\":[]}]}",
                actualVaccineDetailsForCISID);
    }

    @Test
    void testGetVaccineDetailsForCISID5() throws IEMRException {
        // Arrange
        when(immunizationServiceDoseMasterRepo.findByVaccinationIDAndDeletedOrderByDose(Mockito.<Integer>any(),
                Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(immunizationServiceRouteMasterRepo.findByVaccinationIDAndDeletedOrderByRoute(Mockito.<Integer>any(),
                Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        ImmunizationServiceVaccinationMaster immunizationServiceVaccinationMaster = mock(
                ImmunizationServiceVaccinationMaster.class);
        when(immunizationServiceVaccinationMaster.getVaccinationID()).thenReturn(1);
        when(immunizationServiceVaccinationMaster.getVaccineName())
                .thenReturn("ImmunizationServiceVaccinationMaster(vaccinationID=");
        doNothing().when(immunizationServiceVaccinationMaster).setCurrentImmunizationServiceId(Mockito.<Integer>any());
        doNothing().when(immunizationServiceVaccinationMaster).setDeleted(Mockito.<Boolean>any());
        doNothing().when(immunizationServiceVaccinationMaster).setVaccinationID(Mockito.<Integer>any());
        doNothing().when(immunizationServiceVaccinationMaster).setVaccineName(Mockito.<String>any());
        doNothing().when(immunizationServiceVaccinationMaster).setVisitCategoryID(Mockito.<Integer>any());
        immunizationServiceVaccinationMaster.setCurrentImmunizationServiceId(1);
        immunizationServiceVaccinationMaster.setDeleted(true);
        immunizationServiceVaccinationMaster.setVaccinationID(1);
        immunizationServiceVaccinationMaster.setVaccineName("vaccineList");
        immunizationServiceVaccinationMaster.setVisitCategoryID(1);

        ArrayList<ImmunizationServiceVaccinationMaster> immunizationServiceVaccinationMasterList = new ArrayList<>();
        immunizationServiceVaccinationMasterList.add(immunizationServiceVaccinationMaster);
        when(immunizationServiceVaccinationMasterRepo.findByCurrentImmunizationServiceIdAndDeletedAndVisitCategoryID(
                Mockito.<Integer>any(), Mockito.<Boolean>any(), Mockito.<Integer>any()))
                .thenReturn(immunizationServiceVaccinationMasterList);
        when(siteOfInjectionMasterRepo.findByVaccinationIDAndDeletedOrderBySiteofinjection(Mockito.<Integer>any(),
                Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        // Act
        String actualVaccineDetailsForCISID = commonMasterServiceImpl.getVaccineDetailsForCISID(1, 1);

        // Assert
        verify(immunizationServiceVaccinationMaster, atLeast(1)).getVaccinationID();
        verify(immunizationServiceVaccinationMaster).getVaccineName();
        verify(immunizationServiceVaccinationMaster).setCurrentImmunizationServiceId(isA(Integer.class));
        verify(immunizationServiceVaccinationMaster).setDeleted(isA(Boolean.class));
        verify(immunizationServiceVaccinationMaster).setVaccinationID(isA(Integer.class));
        verify(immunizationServiceVaccinationMaster).setVaccineName(eq("vaccineList"));
        verify(immunizationServiceVaccinationMaster).setVisitCategoryID(isA(Integer.class));
        verify(immunizationServiceDoseMasterRepo).findByVaccinationIDAndDeletedOrderByDose(isA(Integer.class),
                isA(Boolean.class));
        verify(immunizationServiceRouteMasterRepo).findByVaccinationIDAndDeletedOrderByRoute(isA(Integer.class),
                isA(Boolean.class));
        verify(immunizationServiceVaccinationMasterRepo).findByCurrentImmunizationServiceIdAndDeletedAndVisitCategoryID(
                isA(Integer.class), isA(Boolean.class), isA(Integer.class));
        verify(siteOfInjectionMasterRepo).findByVaccinationIDAndDeletedOrderBySiteofinjection(isA(Integer.class),
                isA(Boolean.class));
        assertEquals(
                "{\"vaccineList\":[{\"vaccineId\":1,\"vaccine\":\"ImmunizationServiceVaccinationMaster(vaccinationID\\u003d\","
                        + "\"dose\":[],\"route\":[],\"siteOfInjection\":[]}]}",
                actualVaccineDetailsForCISID);
    }

    @Test
    void testGetVaccineDetailsForCISID6() throws IEMRException {
        // Arrange
        when(immunizationServiceDoseMasterRepo.findByVaccinationIDAndDeletedOrderByDose(Mockito.<Integer>any(),
                Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(immunizationServiceRouteMasterRepo.findByVaccinationIDAndDeletedOrderByRoute(Mockito.<Integer>any(),
                Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        ImmunizationServiceVaccinationMaster immunizationServiceVaccinationMaster = mock(
                ImmunizationServiceVaccinationMaster.class);
        when(immunizationServiceVaccinationMaster.getVaccinationID()).thenReturn(null);
        when(immunizationServiceVaccinationMaster.getVaccineName())
                .thenReturn("ImmunizationServiceVaccinationMaster(vaccinationID=");
        doNothing().when(immunizationServiceVaccinationMaster).setCurrentImmunizationServiceId(Mockito.<Integer>any());
        doNothing().when(immunizationServiceVaccinationMaster).setDeleted(Mockito.<Boolean>any());
        doNothing().when(immunizationServiceVaccinationMaster).setVaccinationID(Mockito.<Integer>any());
        doNothing().when(immunizationServiceVaccinationMaster).setVaccineName(Mockito.<String>any());
        doNothing().when(immunizationServiceVaccinationMaster).setVisitCategoryID(Mockito.<Integer>any());
        immunizationServiceVaccinationMaster.setCurrentImmunizationServiceId(1);
        immunizationServiceVaccinationMaster.setDeleted(true);
        immunizationServiceVaccinationMaster.setVaccinationID(1);
        immunizationServiceVaccinationMaster.setVaccineName("vaccineList");
        immunizationServiceVaccinationMaster.setVisitCategoryID(1);

        ArrayList<ImmunizationServiceVaccinationMaster> immunizationServiceVaccinationMasterList = new ArrayList<>();
        immunizationServiceVaccinationMasterList.add(immunizationServiceVaccinationMaster);
        when(immunizationServiceVaccinationMasterRepo.findByCurrentImmunizationServiceIdAndDeletedAndVisitCategoryID(
                Mockito.<Integer>any(), Mockito.<Boolean>any(), Mockito.<Integer>any()))
                .thenReturn(immunizationServiceVaccinationMasterList);
        when(siteOfInjectionMasterRepo.findByVaccinationIDAndDeletedOrderBySiteofinjection(Mockito.<Integer>any(),
                Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        // Act
        String actualVaccineDetailsForCISID = commonMasterServiceImpl.getVaccineDetailsForCISID(1, 1);

        // Assert
        verify(immunizationServiceVaccinationMaster, atLeast(1)).getVaccinationID();
        verify(immunizationServiceVaccinationMaster).getVaccineName();
        verify(immunizationServiceVaccinationMaster).setCurrentImmunizationServiceId(isA(Integer.class));
        verify(immunizationServiceVaccinationMaster).setDeleted(isA(Boolean.class));
        verify(immunizationServiceVaccinationMaster).setVaccinationID(isA(Integer.class));
        verify(immunizationServiceVaccinationMaster).setVaccineName(eq("vaccineList"));
        verify(immunizationServiceVaccinationMaster).setVisitCategoryID(isA(Integer.class));
        verify(immunizationServiceDoseMasterRepo).findByVaccinationIDAndDeletedOrderByDose(isNull(), isA(Boolean.class));
        verify(immunizationServiceRouteMasterRepo).findByVaccinationIDAndDeletedOrderByRoute(isNull(), isA(Boolean.class));
        verify(immunizationServiceVaccinationMasterRepo).findByCurrentImmunizationServiceIdAndDeletedAndVisitCategoryID(
                isA(Integer.class), isA(Boolean.class), isA(Integer.class));
        verify(siteOfInjectionMasterRepo).findByVaccinationIDAndDeletedOrderBySiteofinjection(isNull(), isA(Boolean.class));
        assertEquals(
                "{\"vaccineList\":[{\"vaccine\":\"ImmunizationServiceVaccinationMaster(vaccinationID\\u003d\",\"dose\":[],\"route"
                        + "\":[],\"siteOfInjection\":[]}]}",
                actualVaccineDetailsForCISID);
    }

    @Test
    void testGetVaccineDetailsForCISID7() throws IEMRException {
        // Arrange
        ImmunizationServiceVaccinationMaster immunizationServiceVaccinationMaster = mock(
                ImmunizationServiceVaccinationMaster.class);
        doNothing().when(immunizationServiceVaccinationMaster).setCurrentImmunizationServiceId(Mockito.<Integer>any());
        doNothing().when(immunizationServiceVaccinationMaster).setDeleted(Mockito.<Boolean>any());
        doNothing().when(immunizationServiceVaccinationMaster).setVaccinationID(Mockito.<Integer>any());
        doNothing().when(immunizationServiceVaccinationMaster).setVaccineName(Mockito.<String>any());
        doNothing().when(immunizationServiceVaccinationMaster).setVisitCategoryID(Mockito.<Integer>any());
        immunizationServiceVaccinationMaster.setCurrentImmunizationServiceId(1);
        immunizationServiceVaccinationMaster.setDeleted(true);
        immunizationServiceVaccinationMaster.setVaccinationID(1);
        immunizationServiceVaccinationMaster.setVaccineName("vaccineList");
        immunizationServiceVaccinationMaster.setVisitCategoryID(1);

        ArrayList<ImmunizationServiceVaccinationMaster> immunizationServiceVaccinationMasterList = new ArrayList<>();
        immunizationServiceVaccinationMasterList.add(immunizationServiceVaccinationMaster);

        // Act
        String actualVaccineDetailsForCISID = commonMasterServiceImpl.getVaccineDetailsForCISID(null, 1);

        // Assert
        verify(immunizationServiceVaccinationMaster).setCurrentImmunizationServiceId(isA(Integer.class));
        verify(immunizationServiceVaccinationMaster).setDeleted(isA(Boolean.class));
        verify(immunizationServiceVaccinationMaster).setVaccinationID(isA(Integer.class));
        verify(immunizationServiceVaccinationMaster).setVaccineName(eq("vaccineList"));
        verify(immunizationServiceVaccinationMaster).setVisitCategoryID(isA(Integer.class));
        assertEquals("{\"vaccineList\":[]}", actualVaccineDetailsForCISID);
    }

    @Test
    void testGetVaccineDetailsForCISID8() throws IEMRException {
        // Arrange
        ImmunizationServiceVaccinationMaster immunizationServiceVaccinationMaster = mock(
                ImmunizationServiceVaccinationMaster.class);
        doNothing().when(immunizationServiceVaccinationMaster).setCurrentImmunizationServiceId(Mockito.<Integer>any());
        doNothing().when(immunizationServiceVaccinationMaster).setDeleted(Mockito.<Boolean>any());
        doNothing().when(immunizationServiceVaccinationMaster).setVaccinationID(Mockito.<Integer>any());
        doNothing().when(immunizationServiceVaccinationMaster).setVaccineName(Mockito.<String>any());
        doNothing().when(immunizationServiceVaccinationMaster).setVisitCategoryID(Mockito.<Integer>any());
        immunizationServiceVaccinationMaster.setCurrentImmunizationServiceId(1);
        immunizationServiceVaccinationMaster.setDeleted(true);
        immunizationServiceVaccinationMaster.setVaccinationID(1);
        immunizationServiceVaccinationMaster.setVaccineName("vaccineList");
        immunizationServiceVaccinationMaster.setVisitCategoryID(1);

        ArrayList<ImmunizationServiceVaccinationMaster> immunizationServiceVaccinationMasterList = new ArrayList<>();
        immunizationServiceVaccinationMasterList.add(immunizationServiceVaccinationMaster);

        // Act
        String actualVaccineDetailsForCISID = commonMasterServiceImpl.getVaccineDetailsForCISID(1, null);

        // Assert
        verify(immunizationServiceVaccinationMaster).setCurrentImmunizationServiceId(isA(Integer.class));
        verify(immunizationServiceVaccinationMaster).setDeleted(isA(Boolean.class));
        verify(immunizationServiceVaccinationMaster).setVaccinationID(isA(Integer.class));
        verify(immunizationServiceVaccinationMaster).setVaccineName(eq("vaccineList"));
        verify(immunizationServiceVaccinationMaster).setVisitCategoryID(isA(Integer.class));
        assertEquals("{\"vaccineList\":[]}", actualVaccineDetailsForCISID);
    }

    @Test
    void testGettersAndSetters() {
        CommonMasterServiceImpl commonMasterServiceImpl = new CommonMasterServiceImpl();

        // Act
        commonMasterServiceImpl.setAncMasterDataServiceImpl(new ANCMasterDataServiceImpl());
        commonMasterServiceImpl.setDoctorMasterDataServiceImpl(new DoctorMasterDataServiceImpl());
        commonMasterServiceImpl.setNcdScreeningServiceImpl(new NCDScreeningMasterServiceImpl());
        commonMasterServiceImpl.setNurseMasterDataServiceImpl(new NurseMasterDataServiceImpl());
    }
}
