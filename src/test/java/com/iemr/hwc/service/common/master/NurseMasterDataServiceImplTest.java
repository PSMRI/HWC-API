package com.iemr.hwc.service.common.master;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeast;
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

import com.iemr.hwc.repo.masterrepo.nurse.CancerDiseaseMasterRepo;
import com.iemr.hwc.repo.masterrepo.nurse.CancerPersonalHabitMasterRepo;
import com.iemr.hwc.repo.masterrepo.nurse.FamilyMemberMasterRepo;
import com.iemr.hwc.repo.masterrepo.nurse.VisitCategoryMasterRepo;
import com.iemr.hwc.repo.masterrepo.nurse.VisitReasonMasterRepo;

@ExtendWith(MockitoExtension.class)
class NurseMasterDataServiceImplTest {
    @Mock
    private CancerDiseaseMasterRepo cancerDiseaseMasterRepo;

    @Mock
    private CancerPersonalHabitMasterRepo cancerPersonalHabitMasterRepo;

    @Mock
    private FamilyMemberMasterRepo familyMemberMasterRepo;

    @InjectMocks
    private NurseMasterDataServiceImpl nurseMasterDataServiceImpl;

    @Mock
    private VisitCategoryMasterRepo visitCategoryMasterRepo;

    @Mock
    private VisitReasonMasterRepo visitReasonMasterRepo;

    @Test
    void testGetVisitReasonAndCategories() {
        // Arrange
        when(visitCategoryMasterRepo.getVisitCategoryMaster()).thenReturn(new ArrayList<>());
        when(visitReasonMasterRepo.getVisitReasonMaster()).thenReturn(new ArrayList<>());

        // Act
        String actualGetVisitReasonAndCategoriesResult = nurseMasterDataServiceImpl.GetVisitReasonAndCategories();

        // Assert
        verify(visitCategoryMasterRepo).getVisitCategoryMaster();
        verify(visitReasonMasterRepo).getVisitReasonMaster();
        assertEquals("{\"visitReasons\":[],\"visitCategories\":[]}", actualGetVisitReasonAndCategoriesResult);
    }

    @Test
    void testGetCancerScreeningMasterDataForNurse() {
        // Arrange
        when(cancerDiseaseMasterRepo.getCancerDiseaseMaster()).thenReturn(new ArrayList<>());
        when(cancerPersonalHabitMasterRepo.getCancerPersonalHabitTypeMaster(Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(familyMemberMasterRepo.getFamilyMemberTypeMaster()).thenReturn(new ArrayList<>());
        when(visitCategoryMasterRepo.getVisitCategoryMaster()).thenReturn(new ArrayList<>());
        when(visitReasonMasterRepo.getVisitReasonMaster()).thenReturn(new ArrayList<>());

        // Act
        String actualCancerScreeningMasterDataForNurse = nurseMasterDataServiceImpl.getCancerScreeningMasterDataForNurse();

        // Assert
        verify(cancerDiseaseMasterRepo).getCancerDiseaseMaster();
        verify(cancerPersonalHabitMasterRepo, atLeast(1)).getCancerPersonalHabitTypeMaster(Mockito.<String>any());
        verify(familyMemberMasterRepo).getFamilyMemberTypeMaster();
        verify(visitCategoryMasterRepo).getVisitCategoryMaster();
        verify(visitReasonMasterRepo).getVisitReasonMaster();
        assertEquals(
                "{\"frequencyOfAlcoholIntake\":[],\"oilConsumed\":[],\"dietTypes\":[],\"typeOfTobaccoProducts\":[],\"physicalA"
                        + "ctivityType\":[],\"tobaccoUseStatus\":[],\"alcoholUseStatus\":[],\"visitReasons\":[],\"visitCategories\":[],"
                        + "\"CancerDiseaseType\":[],\"familyMemberTypes\":[]}",
                actualCancerScreeningMasterDataForNurse);
    }

    @Test
    void testGetCancerScreeningMasterDataForNurse2() {
        // Arrange
        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        objectArrayList.add(new Object[]{"42"});
        when(cancerDiseaseMasterRepo.getCancerDiseaseMaster()).thenReturn(objectArrayList);
        when(cancerPersonalHabitMasterRepo.getCancerPersonalHabitTypeMaster(Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(familyMemberMasterRepo.getFamilyMemberTypeMaster()).thenReturn(new ArrayList<>());
        when(visitCategoryMasterRepo.getVisitCategoryMaster()).thenReturn(new ArrayList<>());
        when(visitReasonMasterRepo.getVisitReasonMaster()).thenReturn(new ArrayList<>());

        // Act
        String actualCancerScreeningMasterDataForNurse = nurseMasterDataServiceImpl.getCancerScreeningMasterDataForNurse();

        // Assert
        verify(cancerDiseaseMasterRepo).getCancerDiseaseMaster();
        verify(cancerPersonalHabitMasterRepo, atLeast(1)).getCancerPersonalHabitTypeMaster(Mockito.<String>any());
        verify(familyMemberMasterRepo).getFamilyMemberTypeMaster();
        verify(visitCategoryMasterRepo).getVisitCategoryMaster();
        verify(visitReasonMasterRepo).getVisitReasonMaster();
        assertEquals("{}", actualCancerScreeningMasterDataForNurse);
    }

    @Test
    void testGetCancerScreeningMasterDataForNurse3() {
        // Arrange
        when(cancerDiseaseMasterRepo.getCancerDiseaseMaster()).thenReturn(new ArrayList<>());

        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        objectArrayList.add(new Object[]{"42"});
        when(cancerPersonalHabitMasterRepo.getCancerPersonalHabitTypeMaster(Mockito.<String>any()))
                .thenReturn(objectArrayList);
        when(familyMemberMasterRepo.getFamilyMemberTypeMaster()).thenReturn(new ArrayList<>());
        when(visitCategoryMasterRepo.getVisitCategoryMaster()).thenReturn(new ArrayList<>());
        when(visitReasonMasterRepo.getVisitReasonMaster()).thenReturn(new ArrayList<>());

        // Act
        String actualCancerScreeningMasterDataForNurse = nurseMasterDataServiceImpl.getCancerScreeningMasterDataForNurse();

        // Assert
        verify(cancerDiseaseMasterRepo).getCancerDiseaseMaster();
        verify(cancerPersonalHabitMasterRepo, atLeast(1)).getCancerPersonalHabitTypeMaster(Mockito.<String>any());
        verify(familyMemberMasterRepo).getFamilyMemberTypeMaster();
        verify(visitCategoryMasterRepo).getVisitCategoryMaster();
        verify(visitReasonMasterRepo).getVisitReasonMaster();
        assertEquals("{\"CancerDiseaseType\":[]}", actualCancerScreeningMasterDataForNurse);
    }

    @Test
    void testGetCancerScreeningMasterDataForNurse4() {
        // Arrange
        when(cancerDiseaseMasterRepo.getCancerDiseaseMaster()).thenReturn(new ArrayList<>());
        when(cancerPersonalHabitMasterRepo.getCancerPersonalHabitTypeMaster(Mockito.<String>any()))
                .thenReturn(new ArrayList<>());

        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        objectArrayList.add(new Object[]{"42"});
        when(familyMemberMasterRepo.getFamilyMemberTypeMaster()).thenReturn(objectArrayList);
        when(visitCategoryMasterRepo.getVisitCategoryMaster()).thenReturn(new ArrayList<>());
        when(visitReasonMasterRepo.getVisitReasonMaster()).thenReturn(new ArrayList<>());

        // Act
        String actualCancerScreeningMasterDataForNurse = nurseMasterDataServiceImpl.getCancerScreeningMasterDataForNurse();

        // Assert
        verify(cancerDiseaseMasterRepo).getCancerDiseaseMaster();
        verify(cancerPersonalHabitMasterRepo, atLeast(1)).getCancerPersonalHabitTypeMaster(Mockito.<String>any());
        verify(familyMemberMasterRepo).getFamilyMemberTypeMaster();
        verify(visitCategoryMasterRepo).getVisitCategoryMaster();
        verify(visitReasonMasterRepo).getVisitReasonMaster();
        assertEquals(
                "{\"frequencyOfAlcoholIntake\":[],\"oilConsumed\":[],\"dietTypes\":[],\"typeOfTobaccoProducts\":[],\"physicalA"
                        + "ctivityType\":[],\"tobaccoUseStatus\":[],\"alcoholUseStatus\":[],\"CancerDiseaseType\":[]}",
                actualCancerScreeningMasterDataForNurse);
    }

    @Test
    void testGetCancerScreeningMasterDataForNurse5() {
        // Arrange
        when(cancerDiseaseMasterRepo.getCancerDiseaseMaster()).thenReturn(new ArrayList<>());
        when(cancerPersonalHabitMasterRepo.getCancerPersonalHabitTypeMaster(Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(familyMemberMasterRepo.getFamilyMemberTypeMaster()).thenReturn(new ArrayList<>());

        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        objectArrayList.add(new Object[]{"42"});
        when(visitCategoryMasterRepo.getVisitCategoryMaster()).thenReturn(objectArrayList);
        when(visitReasonMasterRepo.getVisitReasonMaster()).thenReturn(new ArrayList<>());

        // Act
        String actualCancerScreeningMasterDataForNurse = nurseMasterDataServiceImpl.getCancerScreeningMasterDataForNurse();

        // Assert
        verify(cancerDiseaseMasterRepo).getCancerDiseaseMaster();
        verify(cancerPersonalHabitMasterRepo, atLeast(1)).getCancerPersonalHabitTypeMaster(Mockito.<String>any());
        verify(familyMemberMasterRepo).getFamilyMemberTypeMaster();
        verify(visitCategoryMasterRepo).getVisitCategoryMaster();
        verify(visitReasonMasterRepo).getVisitReasonMaster();
        assertEquals("{\"frequencyOfAlcoholIntake\":[],\"oilConsumed\":[],\"dietTypes\":[],\"typeOfTobaccoProducts\":[],"
                + "\"physicalActivityType\":[],\"tobaccoUseStatus\":[],\"alcoholUseStatus\":[],\"CancerDiseaseType\":[],"
                + "\"familyMemberTypes\":[]}", actualCancerScreeningMasterDataForNurse);
    }

    @Test
    void testGetCancerScreeningMasterDataForNurse6() {
        // Arrange
        when(cancerDiseaseMasterRepo.getCancerDiseaseMaster()).thenReturn(new ArrayList<>());
        when(cancerPersonalHabitMasterRepo.getCancerPersonalHabitTypeMaster(Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(familyMemberMasterRepo.getFamilyMemberTypeMaster()).thenReturn(new ArrayList<>());
        when(visitCategoryMasterRepo.getVisitCategoryMaster()).thenReturn(new ArrayList<>());

        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        objectArrayList.add(new Object[]{"42"});
        when(visitReasonMasterRepo.getVisitReasonMaster()).thenReturn(objectArrayList);

        // Act
        String actualCancerScreeningMasterDataForNurse = nurseMasterDataServiceImpl.getCancerScreeningMasterDataForNurse();

        // Assert
        verify(cancerDiseaseMasterRepo).getCancerDiseaseMaster();
        verify(cancerPersonalHabitMasterRepo, atLeast(1)).getCancerPersonalHabitTypeMaster(Mockito.<String>any());
        verify(familyMemberMasterRepo).getFamilyMemberTypeMaster();
        verify(visitCategoryMasterRepo).getVisitCategoryMaster();
        verify(visitReasonMasterRepo).getVisitReasonMaster();
        assertEquals(
                "{\"frequencyOfAlcoholIntake\":[],\"oilConsumed\":[],\"dietTypes\":[],\"typeOfTobaccoProducts\":[],\"physicalA"
                        + "ctivityType\":[],\"tobaccoUseStatus\":[],\"alcoholUseStatus\":[],\"visitCategories\":[],\"CancerDiseaseType\""
                        + ":[],\"familyMemberTypes\":[]}",
                actualCancerScreeningMasterDataForNurse);
    }

    @Test
    void testGetCancerScreeningMasterDataForNurse7() {
        // Arrange
        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        objectArrayList.add(new Object[]{});
        when(cancerDiseaseMasterRepo.getCancerDiseaseMaster()).thenReturn(objectArrayList);
        when(cancerPersonalHabitMasterRepo.getCancerPersonalHabitTypeMaster(Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(familyMemberMasterRepo.getFamilyMemberTypeMaster()).thenReturn(new ArrayList<>());
        when(visitCategoryMasterRepo.getVisitCategoryMaster()).thenReturn(new ArrayList<>());
        when(visitReasonMasterRepo.getVisitReasonMaster()).thenReturn(new ArrayList<>());

        // Act
        String actualCancerScreeningMasterDataForNurse = nurseMasterDataServiceImpl.getCancerScreeningMasterDataForNurse();

        // Assert
        verify(cancerDiseaseMasterRepo).getCancerDiseaseMaster();
        verify(cancerPersonalHabitMasterRepo, atLeast(1)).getCancerPersonalHabitTypeMaster(Mockito.<String>any());
        verify(familyMemberMasterRepo).getFamilyMemberTypeMaster();
        verify(visitCategoryMasterRepo).getVisitCategoryMaster();
        verify(visitReasonMasterRepo).getVisitReasonMaster();
        assertEquals("{}", actualCancerScreeningMasterDataForNurse);
    }
}
