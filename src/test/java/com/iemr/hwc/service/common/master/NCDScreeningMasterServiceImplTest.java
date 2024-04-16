package com.iemr.hwc.service.common.master;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.iemr.hwc.data.masterdata.anc.AllergicReactionTypes;
import com.iemr.hwc.data.masterdata.ncdscreening.CBAC;
import com.iemr.hwc.data.masterdata.ncdscreening.CBACResponse;
import com.iemr.hwc.repo.doctor.ChiefComplaintMasterRepo;
import com.iemr.hwc.repo.doctor.LabTestMasterRepo;
import com.iemr.hwc.repo.labModule.ProcedureRepo;
import com.iemr.hwc.repo.masterrepo.anc.AllergicReactionTypesRepo;
import com.iemr.hwc.repo.masterrepo.anc.DiseaseTypeRepo;
import com.iemr.hwc.repo.masterrepo.anc.PersonalHabitTypeRepo;
import com.iemr.hwc.repo.masterrepo.ncdScreening.BPAndDiabeticStatusRepo;
import com.iemr.hwc.repo.masterrepo.ncdScreening.BloodGlucoseMasterRepo;
import com.iemr.hwc.repo.masterrepo.ncdScreening.CBACRepo;
import com.iemr.hwc.repo.masterrepo.ncdScreening.CervicalLympRepo;
import com.iemr.hwc.repo.masterrepo.ncdScreening.IDRS_ScreenQuestionsRepo;
import com.iemr.hwc.repo.masterrepo.ncdScreening.InpectionBreastsRepo;
import com.iemr.hwc.repo.masterrepo.ncdScreening.MouthOpeningRepo;
import com.iemr.hwc.repo.masterrepo.ncdScreening.NCDScreeningConditionRepo;
import com.iemr.hwc.repo.masterrepo.ncdScreening.NCDScreeningReasonRepo;
import com.iemr.hwc.repo.masterrepo.ncdScreening.OralCavityFindingRepo;
import com.iemr.hwc.repo.masterrepo.ncdScreening.PalpationBreastsRepo;
import com.iemr.hwc.repo.masterrepo.ncdScreening.PalpationOfLymphRepo;
import com.iemr.hwc.repo.masterrepo.ncdScreening.PalpationOralCavityRepo;
import com.iemr.hwc.repo.masterrepo.ncdScreening.PhysicalActivityRepo;
import com.iemr.hwc.repo.masterrepo.ncdScreening.ScreeningDiseaseMasterRepo;
import com.iemr.hwc.repo.masterrepo.ncdScreening.TemperomandibularRepo;
import com.iemr.hwc.repo.masterrepo.ncdScreening.VisualExamRepo;
import com.iemr.hwc.repo.masterrepo.nurse.FamilyMemberMasterRepo;

@ExtendWith(MockitoExtension.class)
class NCDScreeningMasterServiceImplTest {
    @Mock
    private AllergicReactionTypesRepo allergicReactionTypesRepo;

    @Mock
    private BPAndDiabeticStatusRepo bPAndDiabeticStatusRepo;

    @Mock
    private BloodGlucoseMasterRepo bloodGlucoseMasterRepo;

    @Mock
    private CBACRepo cBACRepo;

    @Mock
    private CervicalLympRepo cervicalLympRepo;

    @Mock
    private ChiefComplaintMasterRepo chiefComplaintMasterRepo;

    @Mock
    private DiseaseTypeRepo diseaseTypeRepo;

    @Mock
    private FamilyMemberMasterRepo familyMemberMasterRepo;

    @Mock
    private IDRS_ScreenQuestionsRepo iDRS_ScreenQuestionsRepo;

    @Mock
    private InpectionBreastsRepo inpectionBreastsRepo;

    @Mock
    private LabTestMasterRepo labTestMasterRepo;

    @Mock
    private MouthOpeningRepo mouthOpeningRepo;

    @Mock
    private NCDScreeningConditionRepo nCDScreeningConditionRepo;

    @InjectMocks
    private NCDScreeningMasterServiceImpl nCDScreeningMasterServiceImpl;

    @Mock
    private NCDScreeningReasonRepo nCDScreeningReasonRepo;

    @Mock
    private OralCavityFindingRepo oralCavityFindingRepo;

    @Mock
    private PalpationBreastsRepo palpationBreastsRepo;

    @Mock
    private PalpationOfLymphRepo palpationOfLymphRepo;

    @Mock
    private PalpationOralCavityRepo palpationOralCavityRepo;

    @Mock
    private PersonalHabitTypeRepo personalHabitTypeRepo;

    @Mock
    private PhysicalActivityRepo physicalActivityRepo;

    @Mock
    private ProcedureRepo procedureRepo;

    @Mock
    private ScreeningDiseaseMasterRepo screeningDiseaseMasterRepo;

    @Mock
    private TemperomandibularRepo temperomandibularRepo;

    @Mock
    private VisualExamRepo visualExamRepo;

    @Test
    void testGetNCDScreeningConditions() {
        // Arrange
        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        when(nCDScreeningConditionRepo.getNCDScreeningConditions()).thenReturn(objectArrayList);

        // Act
        List<Object[]> actualNCDScreeningConditions = nCDScreeningMasterServiceImpl.getNCDScreeningConditions();

        // Assert
        verify(nCDScreeningConditionRepo).getNCDScreeningConditions();
        assertTrue(actualNCDScreeningConditions.isEmpty());
        assertSame(objectArrayList, actualNCDScreeningConditions);
    }

    @Test
    void testGetNCDScreeningReasons() {
        // Arrange
        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        when(nCDScreeningReasonRepo.getNCDScreeningReasons()).thenReturn(objectArrayList);

        // Act
        List<Object[]> actualNCDScreeningReasons = nCDScreeningMasterServiceImpl.getNCDScreeningReasons();

        // Assert
        verify(nCDScreeningReasonRepo).getNCDScreeningReasons();
        assertTrue(actualNCDScreeningReasons.isEmpty());
        assertSame(objectArrayList, actualNCDScreeningReasons);
    }

    @Test
    void testGetBPAndDiabeticStatus() {
        // Arrange
        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        when(bPAndDiabeticStatusRepo.getBPAndDiabeticStatus(Mockito.<Boolean>any())).thenReturn(objectArrayList);

        // Act
        List<Object[]> actualBPAndDiabeticStatus = nCDScreeningMasterServiceImpl.getBPAndDiabeticStatus(true);

        // Assert
        verify(bPAndDiabeticStatusRepo).getBPAndDiabeticStatus(isA(Boolean.class));
        assertTrue(actualBPAndDiabeticStatus.isEmpty());
        assertSame(objectArrayList, actualBPAndDiabeticStatus);
    }

    @Test
    void testGetNCDTest() {
        // Arrange
        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        when(labTestMasterRepo.getTestsBYVisitCategory(Mockito.<String>any())).thenReturn(objectArrayList);

        // Act
        ArrayList<Object[]> actualNCDTest = nCDScreeningMasterServiceImpl.getNCDTest();

        // Assert
        verify(labTestMasterRepo).getTestsBYVisitCategory(eq("NCD Screening"));
        assertTrue(actualNCDTest.isEmpty());
        assertSame(objectArrayList, actualNCDTest);
    }

    @Test
    void testGetChiefComplaintMaster() {
        // Arrange
        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        when(chiefComplaintMasterRepo.getChiefComplaintMaster()).thenReturn(objectArrayList);

        // Act
        List<Object[]> actualChiefComplaintMaster = nCDScreeningMasterServiceImpl.getChiefComplaintMaster();

        // Assert
        verify(chiefComplaintMasterRepo).getChiefComplaintMaster();
        assertTrue(actualChiefComplaintMaster.isEmpty());
        assertSame(objectArrayList, actualChiefComplaintMaster);
    }

    @Test
    void testGetNCDScreeningMasterData() {
        // Arrange
        when(allergicReactionTypesRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(bloodGlucoseMasterRepo.getBloodGlucoseType()).thenReturn(new ArrayList<>());
        when(cBACRepo.getCBAC(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(cervicalLympRepo.getCervicalScreening()).thenReturn(new ArrayList<>());
        when(diseaseTypeRepo.getDiseaseTypes()).thenReturn(new ArrayList<>());
        when(familyMemberMasterRepo.getFamilyMemberTypeMaster()).thenReturn(new ArrayList<>());
        when(iDRS_ScreenQuestionsRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(inpectionBreastsRepo.getBreastInspectionMasters()).thenReturn(new ArrayList<>());
        when(mouthOpeningRepo.getMouthOpeningMasters()).thenReturn(new ArrayList<>());
        when(oralCavityFindingRepo.getOralCavityMasters()).thenReturn(new ArrayList<>());
        when(palpationBreastsRepo.getPalpationBreastsMasters()).thenReturn(new ArrayList<>());
        when(palpationOfLymphRepo.getPalpationLymphMasters()).thenReturn(new ArrayList<>());
        when(palpationOralCavityRepo.getPalpationOralCavityMasters()).thenReturn(new ArrayList<>());
        when(personalHabitTypeRepo.getPersonalHabitTypeMaster(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(physicalActivityRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(screeningDiseaseMasterRepo.getScreeningDiseases()).thenReturn(new ArrayList<>());
        when(temperomandibularRepo.getTemperomandibularJoinMasters()).thenReturn(new ArrayList<>());
        when(visualExamRepo.getVisualExaminationMasters()).thenReturn(new ArrayList<>());
        when(chiefComplaintMasterRepo.getChiefComplaintMaster()).thenReturn(new ArrayList<>());
        when(procedureRepo.getProcedureMasterData(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());

        // Act
        String actualNCDScreeningMasterData = nCDScreeningMasterServiceImpl.getNCDScreeningMasterData(1, 1, "Gender");

        // Assert
        verify(chiefComplaintMasterRepo).getChiefComplaintMaster();
        verify(procedureRepo).getProcedureMasterData(isA(Integer.class), eq("Gender"));
        verify(allergicReactionTypesRepo).findByDeleted(isA(Boolean.class));
        verify(diseaseTypeRepo).getDiseaseTypes();
        verify(personalHabitTypeRepo, atLeast(1)).getPersonalHabitTypeMaster(Mockito.<String>any());
        verify(bloodGlucoseMasterRepo).getBloodGlucoseType();
        verify(cBACRepo).getCBAC(eq("gender"));
        verify(cervicalLympRepo).getCervicalScreening();
        verify(iDRS_ScreenQuestionsRepo).findByDeleted(isA(Boolean.class));
        verify(inpectionBreastsRepo).getBreastInspectionMasters();
        verify(mouthOpeningRepo).getMouthOpeningMasters();
        verify(oralCavityFindingRepo).getOralCavityMasters();
        verify(palpationBreastsRepo).getPalpationBreastsMasters();
        verify(palpationOfLymphRepo).getPalpationLymphMasters();
        verify(palpationOralCavityRepo).getPalpationOralCavityMasters();
        verify(physicalActivityRepo).findByDeleted(isA(Boolean.class));
        verify(screeningDiseaseMasterRepo).getScreeningDiseases();
        verify(temperomandibularRepo).getTemperomandibularJoinMasters();
        verify(visualExamRepo).getVisualExaminationMasters();
        verify(familyMemberMasterRepo).getFamilyMemberTypeMaster();
        assertEquals("{\n" + "  \"IDRSQuestions\": [],\n" + "  \"tobaccoUseStatus\": [],\n"
                + "  \"alcoholUseStatus\": [],\n" + "  \"quantityOfAlcoholIntake\": [],\n" + "  \"oralCancer\": {\n"
                + "    \"oralCavity\": [],\n" + "    \"palpationOralCavity\": [],\n" + "    \"mouthOpening\": [],\n"
                + "    \"cervicalLymphNode\": [],\n" + "    \"temporomandibularJoin\": []\n" + "  },\n"
                + "  \"physicalActivity\": [],\n" + "  \"chiefComplaintMaster\": [],\n" + "  \"cervicalCancer\": {\n"
                + "    \"visualExamination\": []\n" + "  },\n" + "  \"typeOfAlcoholProducts\": [],\n"
                + "  \"frequencyOfAlcoholIntake\": [],\n" + "  \"screeningCondition\": [],\n" + "  \"breastCancer\": {\n"
                + "    \"palpationOfBreasts\": [],\n" + "    \"palpationLymphNodes\": [],\n"
                + "    \"inspectionOfBreasts\": []\n" + "  },\n" + "  \"typeOfTobaccoProducts\": [],\n"
                + "  \"DiseaseTypes\": [],\n" + "  \"procedures\": [],\n" + "  \"bloodGlucoseType\": [],\n"
                + "  \"AllergicReactionTypes\": [],\n" + "  \"familyMemberTypes\": []\n" + "}", actualNCDScreeningMasterData);
    }

    @Test
    void testGetNCDScreeningMasterData2() {
        // Arrange
        Timestamp createdDate = mock(Timestamp.class);
        when(createdDate.getTime()).thenReturn(10L);
        Timestamp lastModDate = mock(Timestamp.class);
        when(lastModDate.getTime()).thenReturn(10L);

        AllergicReactionTypes allergicReactionTypes = new AllergicReactionTypes();
        allergicReactionTypes.setAllergicReactionTypeDesc("chiefComplaintMaster");
        allergicReactionTypes.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        allergicReactionTypes.setCreatedDate(createdDate);
        allergicReactionTypes.setDeleted(true);
        allergicReactionTypes.setLastModDate(lastModDate);
        allergicReactionTypes.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        allergicReactionTypes.setName("chiefComplaintMaster");
        allergicReactionTypes.setProcessed("chiefComplaintMaster");

        ArrayList<AllergicReactionTypes> allergicReactionTypesList = new ArrayList<>();
        allergicReactionTypesList.add(allergicReactionTypes);
        when(allergicReactionTypesRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(allergicReactionTypesList);
        when(bloodGlucoseMasterRepo.getBloodGlucoseType()).thenReturn(new ArrayList<>());
        when(cBACRepo.getCBAC(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(cervicalLympRepo.getCervicalScreening()).thenReturn(new ArrayList<>());
        when(diseaseTypeRepo.getDiseaseTypes()).thenReturn(new ArrayList<>());
        when(familyMemberMasterRepo.getFamilyMemberTypeMaster()).thenReturn(new ArrayList<>());
        when(iDRS_ScreenQuestionsRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(inpectionBreastsRepo.getBreastInspectionMasters()).thenReturn(new ArrayList<>());
        when(mouthOpeningRepo.getMouthOpeningMasters()).thenReturn(new ArrayList<>());
        when(oralCavityFindingRepo.getOralCavityMasters()).thenReturn(new ArrayList<>());
        when(palpationBreastsRepo.getPalpationBreastsMasters()).thenReturn(new ArrayList<>());
        when(palpationOfLymphRepo.getPalpationLymphMasters()).thenReturn(new ArrayList<>());
        when(palpationOralCavityRepo.getPalpationOralCavityMasters()).thenReturn(new ArrayList<>());
        when(personalHabitTypeRepo.getPersonalHabitTypeMaster(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(physicalActivityRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(screeningDiseaseMasterRepo.getScreeningDiseases()).thenReturn(new ArrayList<>());
        when(temperomandibularRepo.getTemperomandibularJoinMasters()).thenReturn(new ArrayList<>());
        when(visualExamRepo.getVisualExaminationMasters()).thenReturn(new ArrayList<>());
        when(chiefComplaintMasterRepo.getChiefComplaintMaster()).thenReturn(new ArrayList<>());
        when(procedureRepo.getProcedureMasterData(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());

        // Act
        nCDScreeningMasterServiceImpl.getNCDScreeningMasterData(1, 1, "Gender");

        // Assert
        verify(chiefComplaintMasterRepo).getChiefComplaintMaster();
        verify(procedureRepo).getProcedureMasterData(isA(Integer.class), eq("Gender"));
        verify(allergicReactionTypesRepo).findByDeleted(isA(Boolean.class));
        verify(diseaseTypeRepo).getDiseaseTypes();
        verify(personalHabitTypeRepo, atLeast(1)).getPersonalHabitTypeMaster(Mockito.<String>any());
        verify(bloodGlucoseMasterRepo).getBloodGlucoseType();
        verify(cBACRepo).getCBAC(eq("gender"));
        verify(cervicalLympRepo).getCervicalScreening();
        verify(iDRS_ScreenQuestionsRepo).findByDeleted(isA(Boolean.class));
        verify(inpectionBreastsRepo).getBreastInspectionMasters();
        verify(mouthOpeningRepo).getMouthOpeningMasters();
        verify(oralCavityFindingRepo).getOralCavityMasters();
        verify(palpationBreastsRepo).getPalpationBreastsMasters();
        verify(palpationOfLymphRepo).getPalpationLymphMasters();
        verify(palpationOralCavityRepo).getPalpationOralCavityMasters();
        verify(physicalActivityRepo).findByDeleted(isA(Boolean.class));
        verify(screeningDiseaseMasterRepo).getScreeningDiseases();
        verify(temperomandibularRepo).getTemperomandibularJoinMasters();
        verify(visualExamRepo).getVisualExaminationMasters();
        verify(familyMemberMasterRepo).getFamilyMemberTypeMaster();
        verify(createdDate).getTime();
        verify(lastModDate).getTime();
    }

    @Test
    void testGetNCDScreeningMasterData3() {
        // Arrange
        Timestamp createdDate = mock(Timestamp.class);
        when(createdDate.getTime()).thenReturn(10L);
        Timestamp lastModDate = mock(Timestamp.class);
        when(lastModDate.getTime()).thenReturn(10L);

        AllergicReactionTypes allergicReactionTypes = new AllergicReactionTypes();
        allergicReactionTypes.setAllergicReactionTypeDesc("chiefComplaintMaster");
        allergicReactionTypes.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        allergicReactionTypes.setCreatedDate(createdDate);
        allergicReactionTypes.setDeleted(true);
        allergicReactionTypes.setLastModDate(lastModDate);
        allergicReactionTypes.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        allergicReactionTypes.setName("chiefComplaintMaster");
        allergicReactionTypes.setProcessed("chiefComplaintMaster");
        Timestamp createdDate2 = mock(Timestamp.class);
        when(createdDate2.getTime()).thenReturn(10L);
        Timestamp lastModDate2 = mock(Timestamp.class);
        when(lastModDate2.getTime()).thenReturn(10L);

        AllergicReactionTypes allergicReactionTypes2 = new AllergicReactionTypes();
        allergicReactionTypes2.setAllergicReactionTypeDesc("DiseaseTypes");
        allergicReactionTypes2.setCreatedBy("chiefComplaintMaster");
        allergicReactionTypes2.setCreatedDate(createdDate2);
        allergicReactionTypes2.setDeleted(false);
        allergicReactionTypes2.setLastModDate(lastModDate2);
        allergicReactionTypes2.setModifiedBy("chiefComplaintMaster");
        allergicReactionTypes2.setName("DiseaseTypes");
        allergicReactionTypes2.setProcessed("DiseaseTypes");

        ArrayList<AllergicReactionTypes> allergicReactionTypesList = new ArrayList<>();
        allergicReactionTypesList.add(allergicReactionTypes2);
        allergicReactionTypesList.add(allergicReactionTypes);
        when(allergicReactionTypesRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(allergicReactionTypesList);
        when(bloodGlucoseMasterRepo.getBloodGlucoseType()).thenReturn(new ArrayList<>());
        when(cBACRepo.getCBAC(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(cervicalLympRepo.getCervicalScreening()).thenReturn(new ArrayList<>());
        when(diseaseTypeRepo.getDiseaseTypes()).thenReturn(new ArrayList<>());
        when(familyMemberMasterRepo.getFamilyMemberTypeMaster()).thenReturn(new ArrayList<>());
        when(iDRS_ScreenQuestionsRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(inpectionBreastsRepo.getBreastInspectionMasters()).thenReturn(new ArrayList<>());
        when(mouthOpeningRepo.getMouthOpeningMasters()).thenReturn(new ArrayList<>());
        when(oralCavityFindingRepo.getOralCavityMasters()).thenReturn(new ArrayList<>());
        when(palpationBreastsRepo.getPalpationBreastsMasters()).thenReturn(new ArrayList<>());
        when(palpationOfLymphRepo.getPalpationLymphMasters()).thenReturn(new ArrayList<>());
        when(palpationOralCavityRepo.getPalpationOralCavityMasters()).thenReturn(new ArrayList<>());
        when(personalHabitTypeRepo.getPersonalHabitTypeMaster(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(physicalActivityRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(screeningDiseaseMasterRepo.getScreeningDiseases()).thenReturn(new ArrayList<>());
        when(temperomandibularRepo.getTemperomandibularJoinMasters()).thenReturn(new ArrayList<>());
        when(visualExamRepo.getVisualExaminationMasters()).thenReturn(new ArrayList<>());
        when(chiefComplaintMasterRepo.getChiefComplaintMaster()).thenReturn(new ArrayList<>());
        when(procedureRepo.getProcedureMasterData(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());

        // Act
        nCDScreeningMasterServiceImpl.getNCDScreeningMasterData(1, 1, "Gender");

        // Assert
        verify(chiefComplaintMasterRepo).getChiefComplaintMaster();
        verify(procedureRepo).getProcedureMasterData(isA(Integer.class), eq("Gender"));
        verify(allergicReactionTypesRepo).findByDeleted(isA(Boolean.class));
        verify(diseaseTypeRepo).getDiseaseTypes();
        verify(personalHabitTypeRepo, atLeast(1)).getPersonalHabitTypeMaster(Mockito.<String>any());
        verify(bloodGlucoseMasterRepo).getBloodGlucoseType();
        verify(cBACRepo).getCBAC(eq("gender"));
        verify(cervicalLympRepo).getCervicalScreening();
        verify(iDRS_ScreenQuestionsRepo).findByDeleted(isA(Boolean.class));
        verify(inpectionBreastsRepo).getBreastInspectionMasters();
        verify(mouthOpeningRepo).getMouthOpeningMasters();
        verify(oralCavityFindingRepo).getOralCavityMasters();
        verify(palpationBreastsRepo).getPalpationBreastsMasters();
        verify(palpationOfLymphRepo).getPalpationLymphMasters();
        verify(palpationOralCavityRepo).getPalpationOralCavityMasters();
        verify(physicalActivityRepo).findByDeleted(isA(Boolean.class));
        verify(screeningDiseaseMasterRepo).getScreeningDiseases();
        verify(temperomandibularRepo).getTemperomandibularJoinMasters();
        verify(visualExamRepo).getVisualExaminationMasters();
        verify(familyMemberMasterRepo).getFamilyMemberTypeMaster();
        verify(createdDate2).getTime();
        verify(lastModDate2).getTime();
        verify(createdDate).getTime();
        verify(lastModDate).getTime();
    }

    @Test
    void testGetNCDScreeningMasterData4() {
        // Arrange
        Timestamp createdDate = mock(Timestamp.class);
        when(createdDate.getTime()).thenReturn(10L);
        Timestamp lastModDate = mock(Timestamp.class);
        when(lastModDate.getTime()).thenReturn(10L);

        AllergicReactionTypes allergicReactionTypes = new AllergicReactionTypes();
        allergicReactionTypes.setAllergicReactionTypeDesc("chiefComplaintMaster");
        allergicReactionTypes.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        allergicReactionTypes.setCreatedDate(createdDate);
        allergicReactionTypes.setDeleted(true);
        allergicReactionTypes.setLastModDate(lastModDate);
        allergicReactionTypes.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        allergicReactionTypes.setName("chiefComplaintMaster");
        allergicReactionTypes.setProcessed("chiefComplaintMaster");
        Timestamp createdDate2 = mock(Timestamp.class);
        when(createdDate2.getTime()).thenReturn(10L);
        Timestamp lastModDate2 = mock(Timestamp.class);
        when(lastModDate2.getTime()).thenReturn(10L);

        AllergicReactionTypes allergicReactionTypes2 = new AllergicReactionTypes((short) 1, "chiefComplaintMaster",
                "chiefComplaintMaster");
        allergicReactionTypes2.setAllergicReactionTypeDesc("DiseaseTypes");
        allergicReactionTypes2.setCreatedBy("chiefComplaintMaster");
        allergicReactionTypes2.setCreatedDate(createdDate2);
        allergicReactionTypes2.setDeleted(false);
        allergicReactionTypes2.setLastModDate(lastModDate2);
        allergicReactionTypes2.setModifiedBy("chiefComplaintMaster");
        allergicReactionTypes2.setName("DiseaseTypes");
        allergicReactionTypes2.setProcessed("DiseaseTypes");

        ArrayList<AllergicReactionTypes> allergicReactionTypesList = new ArrayList<>();
        allergicReactionTypesList.add(allergicReactionTypes2);
        allergicReactionTypesList.add(allergicReactionTypes);
        when(allergicReactionTypesRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(allergicReactionTypesList);
        when(bloodGlucoseMasterRepo.getBloodGlucoseType()).thenReturn(new ArrayList<>());
        when(cBACRepo.getCBAC(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(cervicalLympRepo.getCervicalScreening()).thenReturn(new ArrayList<>());
        when(diseaseTypeRepo.getDiseaseTypes()).thenReturn(new ArrayList<>());
        when(familyMemberMasterRepo.getFamilyMemberTypeMaster()).thenReturn(new ArrayList<>());
        when(iDRS_ScreenQuestionsRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(inpectionBreastsRepo.getBreastInspectionMasters()).thenReturn(new ArrayList<>());
        when(mouthOpeningRepo.getMouthOpeningMasters()).thenReturn(new ArrayList<>());
        when(oralCavityFindingRepo.getOralCavityMasters()).thenReturn(new ArrayList<>());
        when(palpationBreastsRepo.getPalpationBreastsMasters()).thenReturn(new ArrayList<>());
        when(palpationOfLymphRepo.getPalpationLymphMasters()).thenReturn(new ArrayList<>());
        when(palpationOralCavityRepo.getPalpationOralCavityMasters()).thenReturn(new ArrayList<>());
        when(personalHabitTypeRepo.getPersonalHabitTypeMaster(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(physicalActivityRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(screeningDiseaseMasterRepo.getScreeningDiseases()).thenReturn(new ArrayList<>());
        when(temperomandibularRepo.getTemperomandibularJoinMasters()).thenReturn(new ArrayList<>());
        when(visualExamRepo.getVisualExaminationMasters()).thenReturn(new ArrayList<>());
        when(chiefComplaintMasterRepo.getChiefComplaintMaster()).thenReturn(new ArrayList<>());
        when(procedureRepo.getProcedureMasterData(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());

        // Act
        nCDScreeningMasterServiceImpl.getNCDScreeningMasterData(1, 1, "Gender");

        // Assert
        verify(chiefComplaintMasterRepo).getChiefComplaintMaster();
        verify(procedureRepo).getProcedureMasterData(isA(Integer.class), eq("Gender"));
        verify(allergicReactionTypesRepo).findByDeleted(isA(Boolean.class));
        verify(diseaseTypeRepo).getDiseaseTypes();
        verify(personalHabitTypeRepo, atLeast(1)).getPersonalHabitTypeMaster(Mockito.<String>any());
        verify(bloodGlucoseMasterRepo).getBloodGlucoseType();
        verify(cBACRepo).getCBAC(eq("gender"));
        verify(cervicalLympRepo).getCervicalScreening();
        verify(iDRS_ScreenQuestionsRepo).findByDeleted(isA(Boolean.class));
        verify(inpectionBreastsRepo).getBreastInspectionMasters();
        verify(mouthOpeningRepo).getMouthOpeningMasters();
        verify(oralCavityFindingRepo).getOralCavityMasters();
        verify(palpationBreastsRepo).getPalpationBreastsMasters();
        verify(palpationOfLymphRepo).getPalpationLymphMasters();
        verify(palpationOralCavityRepo).getPalpationOralCavityMasters();
        verify(physicalActivityRepo).findByDeleted(isA(Boolean.class));
        verify(screeningDiseaseMasterRepo).getScreeningDiseases();
        verify(temperomandibularRepo).getTemperomandibularJoinMasters();
        verify(visualExamRepo).getVisualExaminationMasters();
        verify(familyMemberMasterRepo).getFamilyMemberTypeMaster();
        verify(createdDate2).getTime();
        verify(lastModDate2).getTime();
        verify(createdDate).getTime();
        verify(lastModDate).getTime();
    }

    @Test
    void testGetNCDScreeningMasterData5() {
        // Arrange
        Timestamp createdDate = mock(Timestamp.class);
        when(createdDate.getTime()).thenReturn(10L);
        Timestamp lastModDate = mock(Timestamp.class);
        when(lastModDate.getTime()).thenReturn(10L);

        AllergicReactionTypes allergicReactionTypes = new AllergicReactionTypes();
        allergicReactionTypes.setAllergicReactionTypeDesc("chiefComplaintMaster");
        allergicReactionTypes.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        allergicReactionTypes.setCreatedDate(createdDate);
        allergicReactionTypes.setDeleted(true);
        allergicReactionTypes.setLastModDate(lastModDate);
        allergicReactionTypes.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        allergicReactionTypes.setName("chiefComplaintMaster");
        allergicReactionTypes.setProcessed("chiefComplaintMaster");
        Timestamp createdDate2 = mock(Timestamp.class);
        when(createdDate2.getTime()).thenReturn(10L);
        Timestamp lastModDate2 = mock(Timestamp.class);
        when(lastModDate2.getTime()).thenReturn(10L);
        AllergicReactionTypes allergicReactionTypes2 = mock(AllergicReactionTypes.class);
        doNothing().when(allergicReactionTypes2).setAllergicReactionTypeDesc(Mockito.<String>any());
        doNothing().when(allergicReactionTypes2).setCreatedBy(Mockito.<String>any());
        doNothing().when(allergicReactionTypes2).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(allergicReactionTypes2).setDeleted(Mockito.<Boolean>any());
        doNothing().when(allergicReactionTypes2).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(allergicReactionTypes2).setModifiedBy(Mockito.<String>any());
        doNothing().when(allergicReactionTypes2).setName(Mockito.<String>any());
        doNothing().when(allergicReactionTypes2).setProcessed(Mockito.<String>any());
        allergicReactionTypes2.setAllergicReactionTypeDesc("DiseaseTypes");
        allergicReactionTypes2.setCreatedBy("chiefComplaintMaster");
        allergicReactionTypes2.setCreatedDate(createdDate2);
        allergicReactionTypes2.setDeleted(false);
        allergicReactionTypes2.setLastModDate(lastModDate2);
        allergicReactionTypes2.setModifiedBy("chiefComplaintMaster");
        allergicReactionTypes2.setName("DiseaseTypes");
        allergicReactionTypes2.setProcessed("DiseaseTypes");

        ArrayList<AllergicReactionTypes> allergicReactionTypesList = new ArrayList<>();
        allergicReactionTypesList.add(allergicReactionTypes2);
        allergicReactionTypesList.add(allergicReactionTypes);
        when(allergicReactionTypesRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(allergicReactionTypesList);
        when(bloodGlucoseMasterRepo.getBloodGlucoseType()).thenReturn(new ArrayList<>());
        when(cBACRepo.getCBAC(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(cervicalLympRepo.getCervicalScreening()).thenReturn(new ArrayList<>());
        when(diseaseTypeRepo.getDiseaseTypes()).thenReturn(new ArrayList<>());
        when(familyMemberMasterRepo.getFamilyMemberTypeMaster()).thenReturn(new ArrayList<>());
        when(iDRS_ScreenQuestionsRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(inpectionBreastsRepo.getBreastInspectionMasters()).thenReturn(new ArrayList<>());
        when(mouthOpeningRepo.getMouthOpeningMasters()).thenReturn(new ArrayList<>());
        when(oralCavityFindingRepo.getOralCavityMasters()).thenReturn(new ArrayList<>());
        when(palpationBreastsRepo.getPalpationBreastsMasters()).thenReturn(new ArrayList<>());
        when(palpationOfLymphRepo.getPalpationLymphMasters()).thenReturn(new ArrayList<>());
        when(palpationOralCavityRepo.getPalpationOralCavityMasters()).thenReturn(new ArrayList<>());
        when(personalHabitTypeRepo.getPersonalHabitTypeMaster(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(physicalActivityRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(screeningDiseaseMasterRepo.getScreeningDiseases()).thenReturn(new ArrayList<>());
        when(temperomandibularRepo.getTemperomandibularJoinMasters()).thenReturn(new ArrayList<>());
        when(visualExamRepo.getVisualExaminationMasters()).thenReturn(new ArrayList<>());
        when(chiefComplaintMasterRepo.getChiefComplaintMaster()).thenReturn(new ArrayList<>());
        when(procedureRepo.getProcedureMasterData(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());

        // Act
        nCDScreeningMasterServiceImpl.getNCDScreeningMasterData(1, 1, "Gender");

        // Assert
        verify(allergicReactionTypes2).setAllergicReactionTypeDesc(eq("DiseaseTypes"));
        verify(allergicReactionTypes2).setCreatedBy(eq("chiefComplaintMaster"));
        verify(allergicReactionTypes2).setCreatedDate(isA(Timestamp.class));
        verify(allergicReactionTypes2).setDeleted(isA(Boolean.class));
        verify(allergicReactionTypes2).setLastModDate(isA(Timestamp.class));
        verify(allergicReactionTypes2).setModifiedBy(eq("chiefComplaintMaster"));
        verify(allergicReactionTypes2).setName(eq("DiseaseTypes"));
        verify(allergicReactionTypes2).setProcessed(eq("DiseaseTypes"));
        verify(chiefComplaintMasterRepo).getChiefComplaintMaster();
        verify(procedureRepo).getProcedureMasterData(isA(Integer.class), eq("Gender"));
        verify(allergicReactionTypesRepo).findByDeleted(isA(Boolean.class));
        verify(diseaseTypeRepo).getDiseaseTypes();
        verify(personalHabitTypeRepo, atLeast(1)).getPersonalHabitTypeMaster(Mockito.<String>any());
        verify(bloodGlucoseMasterRepo).getBloodGlucoseType();
        verify(cBACRepo).getCBAC(eq("gender"));
        verify(cervicalLympRepo).getCervicalScreening();
        verify(iDRS_ScreenQuestionsRepo).findByDeleted(isA(Boolean.class));
        verify(inpectionBreastsRepo).getBreastInspectionMasters();
        verify(mouthOpeningRepo).getMouthOpeningMasters();
        verify(oralCavityFindingRepo).getOralCavityMasters();
        verify(palpationBreastsRepo).getPalpationBreastsMasters();
        verify(palpationOfLymphRepo).getPalpationLymphMasters();
        verify(palpationOralCavityRepo).getPalpationOralCavityMasters();
        verify(physicalActivityRepo).findByDeleted(isA(Boolean.class));
        verify(screeningDiseaseMasterRepo).getScreeningDiseases();
        verify(temperomandibularRepo).getTemperomandibularJoinMasters();
        verify(visualExamRepo).getVisualExaminationMasters();
        verify(familyMemberMasterRepo).getFamilyMemberTypeMaster();
        verify(createdDate).getTime();
        verify(lastModDate).getTime();
    }

    @Test
    void testGetCBACDataOrdered() {
        // Arrange, Act and Assert
        assertTrue(nCDScreeningMasterServiceImpl.getCBACDataOrdered(new ArrayList<>()).isEmpty());
    }

    @Test
    void testGetCBACDataOrdered2() {
        // Arrange
        CBAC cbac = new CBAC();
        cbac.setCbac_Score(3);
        cbac.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        cbac.setCreatedDate(mock(Timestamp.class));
        cbac.setDeleted(true);
        cbac.setFemale_Male("Female Male");
        cbac.setGender("Gender");
        cbac.setId(1);
        cbac.setLastModDate(mock(Timestamp.class));
        cbac.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        cbac.setName("Name");
        cbac.setOption(new ArrayList<>());
        cbac.setProcessed('A');
        cbac.setQuestionId(1);
        cbac.setQuestionType("Question Type");
        cbac.setQuestionid(1);
        cbac.setQuestiontype("Questiontype");
        cbac.setRange("Range");
        cbac.setSection("Section");

        ArrayList<CBAC> cbacList = new ArrayList<>();
        cbacList.add(cbac);

        // Act
        List<CBACResponse> actualCBACDataOrdered = nCDScreeningMasterServiceImpl.getCBACDataOrdered(cbacList);

        // Assert
        assertEquals(1, actualCBACDataOrdered.size());
        CBACResponse getResult = actualCBACDataOrdered.get(0);
        assertEquals("Name", getResult.getQuestion());
        assertEquals("Questiontype", getResult.getQuestionType());
        assertEquals("Section", getResult.getSection());
        assertEquals(1, getResult.getQuestionId().intValue());
        List<HashMap<String, String>> options = getResult.getOptions();
        assertEquals(1, options.size());
        assertEquals(2, options.get(0).size());
    }

    @Test
    void testGetCBACDataOrdered3() {
        // Arrange
        CBAC cbac = new CBAC();
        cbac.setCbac_Score(3);
        cbac.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        cbac.setCreatedDate(mock(Timestamp.class));
        cbac.setDeleted(true);
        cbac.setFemale_Male("Female Male");
        cbac.setGender("Gender");
        cbac.setId(1);
        cbac.setLastModDate(mock(Timestamp.class));
        cbac.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        cbac.setName("Name");
        cbac.setOption(new ArrayList<>());
        cbac.setProcessed('A');
        cbac.setQuestionId(1);
        cbac.setQuestionType("Question Type");
        cbac.setQuestionid(1);
        cbac.setQuestiontype("Questiontype");
        cbac.setRange("Range");
        cbac.setSection("Section");

        CBAC cbac2 = new CBAC();
        cbac2.setCbac_Score(1);
        cbac2.setCreatedBy("option");
        cbac2.setCreatedDate(mock(Timestamp.class));
        cbac2.setDeleted(false);
        cbac2.setFemale_Male("score");
        cbac2.setGender("score");
        cbac2.setId(2);
        cbac2.setLastModDate(mock(Timestamp.class));
        cbac2.setModifiedBy("option");
        cbac2.setName("score");
        cbac2.setOption(new ArrayList<>());
        cbac2.setProcessed('A');
        cbac2.setQuestionId(2);
        cbac2.setQuestionType("score");
        cbac2.setQuestionid(2);
        cbac2.setQuestiontype("score");
        cbac2.setRange("score");
        cbac2.setSection("score");

        ArrayList<CBAC> cbacList = new ArrayList<>();
        cbacList.add(cbac2);
        cbacList.add(cbac);

        // Act
        List<CBACResponse> actualCBACDataOrdered = nCDScreeningMasterServiceImpl.getCBACDataOrdered(cbacList);

        // Assert
        assertEquals(2, actualCBACDataOrdered.size());
        CBACResponse getResult = actualCBACDataOrdered.get(1);
        assertEquals("Name", getResult.getQuestion());
        assertEquals("Questiontype", getResult.getQuestionType());
        assertEquals("Section", getResult.getSection());
        CBACResponse getResult2 = actualCBACDataOrdered.get(0);
        assertEquals("score", getResult2.getQuestion());
        assertEquals("score", getResult2.getQuestionType());
        assertEquals("score", getResult2.getSection());
        assertEquals(1, getResult.getQuestionId().intValue());
        List<HashMap<String, String>> options = getResult2.getOptions();
        assertEquals(1, options.size());
        List<HashMap<String, String>> options2 = getResult.getOptions();
        assertEquals(1, options2.size());
        assertEquals(2, getResult2.getQuestionId().intValue());
        assertEquals(2, options.get(0).size());
        assertEquals(2, options2.get(0).size());
    }

    @Test
    void testGetCBACDataOrdered4() {
        // Arrange
        CBAC cbac = new CBAC();
        cbac.setCbac_Score(null);
        cbac.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        cbac.setCreatedDate(mock(Timestamp.class));
        cbac.setDeleted(true);
        cbac.setFemale_Male("Female Male");
        cbac.setGender("Gender");
        cbac.setId(1);
        cbac.setLastModDate(mock(Timestamp.class));
        cbac.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        cbac.setName("Name");
        ArrayList<Map<String, String>> option = new ArrayList<>();
        cbac.setOption(option);
        cbac.setProcessed('A');
        cbac.setQuestionId(1);
        cbac.setQuestionType("Question Type");
        cbac.setQuestionid(1);
        cbac.setQuestiontype("Questiontype");
        cbac.setRange("Range");
        cbac.setSection("Section");

        ArrayList<CBAC> cbacList = new ArrayList<>();
        cbacList.add(cbac);

        // Act
        List<CBACResponse> actualCBACDataOrdered = nCDScreeningMasterServiceImpl.getCBACDataOrdered(cbacList);

        // Assert
        assertEquals(1, actualCBACDataOrdered.size());
        CBACResponse getResult = actualCBACDataOrdered.get(0);
        assertEquals("Name", getResult.getQuestion());
        assertEquals("Questiontype", getResult.getQuestionType());
        assertEquals("Section", getResult.getSection());
        assertEquals(1, getResult.getQuestionId().intValue());
        assertEquals(option, getResult.getOptions());
    }

    @Test
    void testGetCBACDataOrdered5() {
        // Arrange
        CBAC cbac = new CBAC();
        cbac.setCbac_Score(3);
        cbac.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        cbac.setCreatedDate(mock(Timestamp.class));
        cbac.setDeleted(true);
        cbac.setFemale_Male("Female Male");
        cbac.setGender("Gender");
        cbac.setId(1);
        cbac.setLastModDate(mock(Timestamp.class));
        cbac.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        cbac.setName("Name");
        ArrayList<Map<String, String>> option = new ArrayList<>();
        cbac.setOption(option);
        cbac.setProcessed('A');
        cbac.setQuestionId(1);
        cbac.setQuestionType("Question Type");
        cbac.setQuestionid(1);
        cbac.setQuestiontype("Questiontype");
        cbac.setRange(null);
        cbac.setSection("Section");

        ArrayList<CBAC> cbacList = new ArrayList<>();
        cbacList.add(cbac);

        // Act
        List<CBACResponse> actualCBACDataOrdered = nCDScreeningMasterServiceImpl.getCBACDataOrdered(cbacList);

        // Assert
        assertEquals(1, actualCBACDataOrdered.size());
        CBACResponse getResult = actualCBACDataOrdered.get(0);
        assertEquals("Name", getResult.getQuestion());
        assertEquals("Questiontype", getResult.getQuestionType());
        assertEquals("Section", getResult.getSection());
        assertEquals(1, getResult.getQuestionId().intValue());
        assertEquals(option, getResult.getOptions());
    }

    @Test
    void testGetCBACDataOrdered6() {
        // Arrange
        CBAC cbac = new CBAC();
        cbac.setCbac_Score(3);
        cbac.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        cbac.setCreatedDate(mock(Timestamp.class));
        cbac.setDeleted(true);
        cbac.setFemale_Male("Female Male");
        cbac.setGender("Gender");
        cbac.setId(1);
        cbac.setLastModDate(mock(Timestamp.class));
        cbac.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        cbac.setName("Name");
        cbac.setOption(new ArrayList<>());
        cbac.setProcessed('A');
        cbac.setQuestionId(1);
        cbac.setQuestionType("Question Type");
        cbac.setQuestionid(1);
        cbac.setQuestiontype("Questiontype");
        cbac.setRange("Range");
        cbac.setSection("Section");

        CBAC cbac2 = new CBAC();
        cbac2.setCbac_Score(1);
        cbac2.setCreatedBy("option");
        cbac2.setCreatedDate(mock(Timestamp.class));
        cbac2.setDeleted(false);
        cbac2.setFemale_Male("score");
        cbac2.setGender("score");
        cbac2.setId(2);
        cbac2.setLastModDate(mock(Timestamp.class));
        cbac2.setModifiedBy("option");
        cbac2.setName("score");
        cbac2.setOption(new ArrayList<>());
        cbac2.setProcessed('A');
        cbac2.setQuestionId(2);
        cbac2.setQuestionType("score");
        cbac2.setQuestionid(2);
        cbac2.setQuestiontype("score");
        cbac2.setRange("score");
        cbac2.setSection("score");

        CBAC cbac3 = new CBAC();
        cbac3.setCbac_Score(2);
        cbac3.setCreatedBy("score");
        cbac3.setCreatedDate(mock(Timestamp.class));
        cbac3.setDeleted(true);
        cbac3.setFemale_Male("Female Male");
        cbac3.setGender("Gender");
        cbac3.setId(3);
        cbac3.setLastModDate(mock(Timestamp.class));
        cbac3.setModifiedBy("score");
        cbac3.setName("Name");
        cbac3.setOption(new ArrayList<>());
        cbac3.setProcessed('A');
        cbac3.setQuestionId(3);
        cbac3.setQuestionType("Question Type");
        cbac3.setQuestionid(3);
        cbac3.setQuestiontype("Questiontype");
        cbac3.setRange("Range");
        cbac3.setSection("Section");

        ArrayList<CBAC> cbacList = new ArrayList<>();
        cbacList.add(cbac3);
        cbacList.add(cbac2);
        cbacList.add(cbac);

        // Act
        List<CBACResponse> actualCBACDataOrdered = nCDScreeningMasterServiceImpl.getCBACDataOrdered(cbacList);

        // Assert
        assertEquals(3, actualCBACDataOrdered.size());
        CBACResponse getResult = actualCBACDataOrdered.get(0);
        assertEquals("Name", getResult.getQuestion());
        CBACResponse getResult2 = actualCBACDataOrdered.get(2);
        assertEquals("Name", getResult2.getQuestion());
        assertEquals("Questiontype", getResult.getQuestionType());
        assertEquals("Questiontype", getResult2.getQuestionType());
        assertEquals("Section", getResult.getSection());
        assertEquals("Section", getResult2.getSection());
        CBACResponse getResult3 = actualCBACDataOrdered.get(1);
        assertEquals("score", getResult3.getQuestion());
        assertEquals("score", getResult3.getQuestionType());
        assertEquals("score", getResult3.getSection());
        assertEquals(1, getResult2.getQuestionId().intValue());
        List<HashMap<String, String>> options = getResult.getOptions();
        assertEquals(1, options.size());
        List<HashMap<String, String>> options2 = getResult3.getOptions();
        assertEquals(1, options2.size());
        List<HashMap<String, String>> options3 = getResult2.getOptions();
        assertEquals(1, options3.size());
        assertEquals(2, getResult3.getQuestionId().intValue());
        assertEquals(2, options.get(0).size());
        assertEquals(2, options2.get(0).size());
        assertEquals(2, options3.get(0).size());
        assertEquals(3, getResult.getQuestionId().intValue());
    }

    @Test
    void testGetCBACDataOrdered7() {
        // Arrange
        CBAC cbac = new CBAC();
        cbac.setCbac_Score(3);
        cbac.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        cbac.setCreatedDate(mock(Timestamp.class));
        cbac.setDeleted(true);
        cbac.setFemale_Male("Female Male");
        cbac.setGender("Gender");
        cbac.setId(1);
        cbac.setLastModDate(mock(Timestamp.class));
        cbac.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        cbac.setName("Name");
        cbac.setOption(new ArrayList<>());
        cbac.setProcessed('A');
        cbac.setQuestionId(1);
        cbac.setQuestionType("Question Type");
        cbac.setQuestionid(1);
        cbac.setQuestiontype("Questiontype");
        cbac.setRange("Range");
        cbac.setSection("Section");

        CBAC cbac2 = new CBAC();
        cbac2.setCbac_Score(1);
        cbac2.setCreatedBy("option");
        cbac2.setCreatedDate(mock(Timestamp.class));
        cbac2.setDeleted(false);
        cbac2.setFemale_Male("score");
        cbac2.setGender("score");
        cbac2.setId(2);
        cbac2.setLastModDate(mock(Timestamp.class));
        cbac2.setModifiedBy("option");
        cbac2.setName("score");
        cbac2.setOption(new ArrayList<>());
        cbac2.setProcessed('A');
        cbac2.setQuestionId(2);
        cbac2.setQuestionType("score");
        cbac2.setQuestionid(1);
        cbac2.setQuestiontype("score");
        cbac2.setRange("score");
        cbac2.setSection("score");

        ArrayList<CBAC> cbacList = new ArrayList<>();
        cbacList.add(cbac2);
        cbacList.add(cbac);

        // Act
        List<CBACResponse> actualCBACDataOrdered = nCDScreeningMasterServiceImpl.getCBACDataOrdered(cbacList);

        // Assert
        assertEquals(1, actualCBACDataOrdered.size());
        CBACResponse getResult = actualCBACDataOrdered.get(0);
        assertEquals("Name", getResult.getQuestion());
        assertEquals("Questiontype", getResult.getQuestionType());
        assertEquals("Section", getResult.getSection());
        assertEquals(1, getResult.getQuestionId().intValue());
        List<HashMap<String, String>> options = getResult.getOptions();
        assertEquals(2, options.size());
        assertEquals(2, options.get(0).size());
        assertEquals(2, options.get(1).size());
    }

    @Test
    void testGettersAndSetters() {
        NCDScreeningMasterServiceImpl ncdScreeningMasterServiceImpl = new NCDScreeningMasterServiceImpl();

        // Act
        ncdScreeningMasterServiceImpl.setBpAndDiabeticStatusRepo(mock(BPAndDiabeticStatusRepo.class));
        ncdScreeningMasterServiceImpl.setChiefComplaintMasterRepo(mock(ChiefComplaintMasterRepo.class));
        ncdScreeningMasterServiceImpl.setLabTestMasterRepo(mock(LabTestMasterRepo.class));
        ncdScreeningMasterServiceImpl.setNcdScreeningConditionRepo(mock(NCDScreeningConditionRepo.class));
        ncdScreeningMasterServiceImpl.setNcdScreeningReasonRepo(mock(NCDScreeningReasonRepo.class));
        ncdScreeningMasterServiceImpl.setProcedureRepo(mock(ProcedureRepo.class));
    }
}
