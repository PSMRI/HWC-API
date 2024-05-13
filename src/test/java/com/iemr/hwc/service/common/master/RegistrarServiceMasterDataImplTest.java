package com.iemr.hwc.service.common.master;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.iemr.hwc.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.hwc.data.benFlowStatus.I_bendemographics;
import com.iemr.hwc.data.login.MasterVan;
import com.iemr.hwc.data.masterdata.registrar.AgeUnit;
import com.iemr.hwc.data.masterdata.registrar.GenderMaster;
import com.iemr.hwc.data.masterdata.registrar.RelationshipMaster;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.masterrepo.AgeUnitRepo;
import com.iemr.hwc.repo.masterrepo.CommunityMasterRepo;
import com.iemr.hwc.repo.masterrepo.GenderMasterRepo;
import com.iemr.hwc.repo.masterrepo.GovIdEntityTypeRepo;
import com.iemr.hwc.repo.masterrepo.IncomeStatusMasterRepo;
import com.iemr.hwc.repo.masterrepo.LiteracyStatusRepo;
import com.iemr.hwc.repo.masterrepo.MaritalStatusMasterRepo;
import com.iemr.hwc.repo.masterrepo.OccupationMasterRepo;
import com.iemr.hwc.repo.masterrepo.QualificationMasterRepo;
import com.iemr.hwc.repo.masterrepo.RelationshipMasterRepo;
import com.iemr.hwc.repo.masterrepo.ReligionMasterRepo;
import com.iemr.hwc.repo.nurse.anc.ANCCareRepo;
import com.iemr.hwc.repo.registrar.BeneficiaryImageRepo;
import com.iemr.hwc.repo.registrar.ReistrarRepoBenSearch;

@ExtendWith(MockitoExtension.class)
class RegistrarServiceMasterDataImplTest {
    @Mock
    private ANCCareRepo aNCCareRepo;

    @Mock
    private AgeUnitRepo ageUnitRepo;

    @Mock
    private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;

    @Mock
    private BeneficiaryImageRepo beneficiaryImageRepo;

    @Mock
    private CommunityMasterRepo communityMasterRepo;

    @Mock
    private GenderMasterRepo genderMasterRepo;

    @Mock
    private GovIdEntityTypeRepo govIdEntityTypeRepo;

    @Mock
    private IncomeStatusMasterRepo incomeStatusMasterRepo;

    @Mock
    private LiteracyStatusRepo literacyStatusRepo;

    @Mock
    private MaritalStatusMasterRepo maritalStatusMasterRepo;

    @Mock
    private OccupationMasterRepo occupationMasterRepo;

    @Mock
    private QualificationMasterRepo qualificationMasterRepo;

    @InjectMocks
    private RegistrarServiceMasterDataImpl registrarServiceMasterDataImpl;

    @Mock
    private ReistrarRepoBenSearch reistrarRepoBenSearch;

    @Mock
    private RelationshipMasterRepo relationshipMasterRepo;

    @Mock
    private ReligionMasterRepo religionMasterRepo;

    @Test
    void testGetRegMasterData() {
        // Arrange
        when(ageUnitRepo.getAgeUnit()).thenReturn(new ArrayList<>());
        when(communityMasterRepo.getCommunityMaster()).thenReturn(new ArrayList<>());
        when(genderMasterRepo.getGenderMaster()).thenReturn(new ArrayList<>());
        when(govIdEntityTypeRepo.getGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(govIdEntityTypeRepo.getOtherGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(incomeStatusMasterRepo.getIncomeStatusMaster()).thenReturn(new ArrayList<>());
        when(literacyStatusRepo.getLiteracyStatus()).thenReturn(new ArrayList<>());
        when(maritalStatusMasterRepo.getMaritalStatusMaster()).thenReturn(new ArrayList<>());
        when(occupationMasterRepo.getOccupationMaster()).thenReturn(new ArrayList<>());
        when(qualificationMasterRepo.getQualificationMaster()).thenReturn(new ArrayList<>());
        when(relationshipMasterRepo.getRelationshipMaster()).thenReturn(new ArrayList<>());
        when(religionMasterRepo.getReligionMaster()).thenReturn(new ArrayList<>());

        // Act
        String actualRegMasterData = registrarServiceMasterDataImpl.getRegMasterData();

        // Assert
        verify(ageUnitRepo).getAgeUnit();
        verify(communityMasterRepo).getCommunityMaster();
        verify(genderMasterRepo).getGenderMaster();
        verify(govIdEntityTypeRepo).getGovIdEntityMaster();
        verify(govIdEntityTypeRepo).getOtherGovIdEntityMaster();
        verify(incomeStatusMasterRepo).getIncomeStatusMaster();
        verify(literacyStatusRepo).getLiteracyStatus();
        verify(maritalStatusMasterRepo).getMaritalStatusMaster();
        verify(occupationMasterRepo).getOccupationMaster();
        verify(qualificationMasterRepo).getQualificationMaster();
        verify(relationshipMasterRepo).getRelationshipMaster();
        verify(religionMasterRepo).getReligionMaster();
        assertEquals("{\n" + "  \"ageUnit\": [],\n" + "  \"occupationMaster\": [],\n" + "  \"literacyStatus\": [],\n"
                        + "  \"incomeMaster\": [],\n" + "  \"maritalStatusMaster\": [],\n" + "  \"religionMaster\": [],\n"
                        + "  \"communityMaster\": [],\n" + "  \"qualificationMaster\": [],\n" + "  \"relationshipMaster\": [],\n"
                        + "  \"govIdEntityMaster\": [],\n" + "  \"genderMaster\": [],\n" + "  \"otherGovIdEntityMaster\": []\n" + "}",
                actualRegMasterData);
    }

    @Test
    void testGetRegMasterData2() {
        // Arrange
        ArrayList<AgeUnit> ageUnitList = new ArrayList<>();
        ageUnitList.add(new AgeUnit());
        when(ageUnitRepo.getAgeUnit()).thenReturn(ageUnitList);
        when(communityMasterRepo.getCommunityMaster()).thenReturn(new ArrayList<>());
        when(genderMasterRepo.getGenderMaster()).thenReturn(new ArrayList<>());
        when(govIdEntityTypeRepo.getGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(govIdEntityTypeRepo.getOtherGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(incomeStatusMasterRepo.getIncomeStatusMaster()).thenReturn(new ArrayList<>());
        when(literacyStatusRepo.getLiteracyStatus()).thenReturn(new ArrayList<>());
        when(maritalStatusMasterRepo.getMaritalStatusMaster()).thenReturn(new ArrayList<>());
        when(occupationMasterRepo.getOccupationMaster()).thenReturn(new ArrayList<>());
        when(qualificationMasterRepo.getQualificationMaster()).thenReturn(new ArrayList<>());
        when(relationshipMasterRepo.getRelationshipMaster()).thenReturn(new ArrayList<>());
        when(religionMasterRepo.getReligionMaster()).thenReturn(new ArrayList<>());

        // Act
        String actualRegMasterData = registrarServiceMasterDataImpl.getRegMasterData();

        // Assert
        verify(ageUnitRepo).getAgeUnit();
        verify(communityMasterRepo).getCommunityMaster();
        verify(genderMasterRepo).getGenderMaster();
        verify(govIdEntityTypeRepo).getGovIdEntityMaster();
        verify(govIdEntityTypeRepo).getOtherGovIdEntityMaster();
        verify(incomeStatusMasterRepo).getIncomeStatusMaster();
        verify(literacyStatusRepo).getLiteracyStatus();
        verify(maritalStatusMasterRepo).getMaritalStatusMaster();
        verify(occupationMasterRepo).getOccupationMaster();
        verify(qualificationMasterRepo).getQualificationMaster();
        verify(relationshipMasterRepo).getRelationshipMaster();
        verify(religionMasterRepo).getReligionMaster();
        assertEquals("{\n" + "  \"ageUnit\": [\n" + "    {}\n" + "  ],\n" + "  \"occupationMaster\": [],\n"
                + "  \"literacyStatus\": [],\n" + "  \"incomeMaster\": [],\n" + "  \"maritalStatusMaster\": [],\n"
                + "  \"religionMaster\": [],\n" + "  \"communityMaster\": [],\n" + "  \"qualificationMaster\": [],\n"
                + "  \"relationshipMaster\": [],\n" + "  \"govIdEntityMaster\": [],\n" + "  \"genderMaster\": [],\n"
                + "  \"otherGovIdEntityMaster\": []\n" + "}", actualRegMasterData);
    }

    @Test
    void testGetRegMasterData3() {
        // Arrange
        ArrayList<AgeUnit> ageUnitList = new ArrayList<>();
        ageUnitList.add(new AgeUnit());
        ageUnitList.add(new AgeUnit());
        when(ageUnitRepo.getAgeUnit()).thenReturn(ageUnitList);
        when(communityMasterRepo.getCommunityMaster()).thenReturn(new ArrayList<>());
        when(genderMasterRepo.getGenderMaster()).thenReturn(new ArrayList<>());
        when(govIdEntityTypeRepo.getGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(govIdEntityTypeRepo.getOtherGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(incomeStatusMasterRepo.getIncomeStatusMaster()).thenReturn(new ArrayList<>());
        when(literacyStatusRepo.getLiteracyStatus()).thenReturn(new ArrayList<>());
        when(maritalStatusMasterRepo.getMaritalStatusMaster()).thenReturn(new ArrayList<>());
        when(occupationMasterRepo.getOccupationMaster()).thenReturn(new ArrayList<>());
        when(qualificationMasterRepo.getQualificationMaster()).thenReturn(new ArrayList<>());
        when(relationshipMasterRepo.getRelationshipMaster()).thenReturn(new ArrayList<>());
        when(religionMasterRepo.getReligionMaster()).thenReturn(new ArrayList<>());

        // Act
        String actualRegMasterData = registrarServiceMasterDataImpl.getRegMasterData();

        // Assert
        verify(ageUnitRepo).getAgeUnit();
        verify(communityMasterRepo).getCommunityMaster();
        verify(genderMasterRepo).getGenderMaster();
        verify(govIdEntityTypeRepo).getGovIdEntityMaster();
        verify(govIdEntityTypeRepo).getOtherGovIdEntityMaster();
        verify(incomeStatusMasterRepo).getIncomeStatusMaster();
        verify(literacyStatusRepo).getLiteracyStatus();
        verify(maritalStatusMasterRepo).getMaritalStatusMaster();
        verify(occupationMasterRepo).getOccupationMaster();
        verify(qualificationMasterRepo).getQualificationMaster();
        verify(relationshipMasterRepo).getRelationshipMaster();
        verify(religionMasterRepo).getReligionMaster();
        assertEquals("{\n" + "  \"ageUnit\": [\n" + "    {},\n" + "    {}\n" + "  ],\n" + "  \"occupationMaster\": [],\n"
                + "  \"literacyStatus\": [],\n" + "  \"incomeMaster\": [],\n" + "  \"maritalStatusMaster\": [],\n"
                + "  \"religionMaster\": [],\n" + "  \"communityMaster\": [],\n" + "  \"qualificationMaster\": [],\n"
                + "  \"relationshipMaster\": [],\n" + "  \"govIdEntityMaster\": [],\n" + "  \"genderMaster\": [],\n"
                + "  \"otherGovIdEntityMaster\": []\n" + "}", actualRegMasterData);
    }

    @Test
    void testGetRegMasterData4() {
        // Arrange
        when(ageUnitRepo.getAgeUnit()).thenReturn(new ArrayList<>());

        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        objectArrayList.add(new Object[]{"42"});
        when(communityMasterRepo.getCommunityMaster()).thenReturn(objectArrayList);
        when(genderMasterRepo.getGenderMaster()).thenReturn(new ArrayList<>());
        when(govIdEntityTypeRepo.getGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(govIdEntityTypeRepo.getOtherGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(incomeStatusMasterRepo.getIncomeStatusMaster()).thenReturn(new ArrayList<>());
        when(literacyStatusRepo.getLiteracyStatus()).thenReturn(new ArrayList<>());
        when(maritalStatusMasterRepo.getMaritalStatusMaster()).thenReturn(new ArrayList<>());
        when(occupationMasterRepo.getOccupationMaster()).thenReturn(new ArrayList<>());
        when(qualificationMasterRepo.getQualificationMaster()).thenReturn(new ArrayList<>());
        when(relationshipMasterRepo.getRelationshipMaster()).thenReturn(new ArrayList<>());
        when(religionMasterRepo.getReligionMaster()).thenReturn(new ArrayList<>());

        // Act
        String actualRegMasterData = registrarServiceMasterDataImpl.getRegMasterData();

        // Assert
        verify(ageUnitRepo).getAgeUnit();
        verify(communityMasterRepo).getCommunityMaster();
        verify(genderMasterRepo).getGenderMaster();
        verify(govIdEntityTypeRepo).getGovIdEntityMaster();
        verify(govIdEntityTypeRepo).getOtherGovIdEntityMaster();
        verify(incomeStatusMasterRepo).getIncomeStatusMaster();
        verify(literacyStatusRepo).getLiteracyStatus();
        verify(maritalStatusMasterRepo).getMaritalStatusMaster();
        verify(occupationMasterRepo).getOccupationMaster();
        verify(qualificationMasterRepo).getQualificationMaster();
        verify(relationshipMasterRepo).getRelationshipMaster();
        verify(religionMasterRepo).getReligionMaster();
        assertEquals("{}", actualRegMasterData);
    }

    @Test
    void testGetRegMasterData5() {
        // Arrange
        when(ageUnitRepo.getAgeUnit()).thenReturn(new ArrayList<>());
        when(communityMasterRepo.getCommunityMaster()).thenReturn(new ArrayList<>());

        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        objectArrayList.add(new Object[]{"42"});
        when(genderMasterRepo.getGenderMaster()).thenReturn(objectArrayList);
        when(govIdEntityTypeRepo.getGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(govIdEntityTypeRepo.getOtherGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(incomeStatusMasterRepo.getIncomeStatusMaster()).thenReturn(new ArrayList<>());
        when(literacyStatusRepo.getLiteracyStatus()).thenReturn(new ArrayList<>());
        when(maritalStatusMasterRepo.getMaritalStatusMaster()).thenReturn(new ArrayList<>());
        when(occupationMasterRepo.getOccupationMaster()).thenReturn(new ArrayList<>());
        when(qualificationMasterRepo.getQualificationMaster()).thenReturn(new ArrayList<>());
        when(relationshipMasterRepo.getRelationshipMaster()).thenReturn(new ArrayList<>());
        when(religionMasterRepo.getReligionMaster()).thenReturn(new ArrayList<>());

        // Act
        String actualRegMasterData = registrarServiceMasterDataImpl.getRegMasterData();

        // Assert
        verify(ageUnitRepo).getAgeUnit();
        verify(communityMasterRepo).getCommunityMaster();
        verify(genderMasterRepo).getGenderMaster();
        verify(govIdEntityTypeRepo).getGovIdEntityMaster();
        verify(govIdEntityTypeRepo).getOtherGovIdEntityMaster();
        verify(incomeStatusMasterRepo).getIncomeStatusMaster();
        verify(literacyStatusRepo).getLiteracyStatus();
        verify(maritalStatusMasterRepo).getMaritalStatusMaster();
        verify(occupationMasterRepo).getOccupationMaster();
        verify(qualificationMasterRepo).getQualificationMaster();
        verify(relationshipMasterRepo).getRelationshipMaster();
        verify(religionMasterRepo).getReligionMaster();
        assertEquals("{\n  \"communityMaster\": []\n}", actualRegMasterData);
    }

    @Test
    void testGetRegMasterData6() {
        // Arrange
        when(ageUnitRepo.getAgeUnit()).thenReturn(new ArrayList<>());
        when(communityMasterRepo.getCommunityMaster()).thenReturn(new ArrayList<>());
        when(genderMasterRepo.getGenderMaster()).thenReturn(new ArrayList<>());

        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        objectArrayList.add(new Object[]{"42"});
        when(govIdEntityTypeRepo.getGovIdEntityMaster()).thenReturn(objectArrayList);
        when(govIdEntityTypeRepo.getOtherGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(incomeStatusMasterRepo.getIncomeStatusMaster()).thenReturn(new ArrayList<>());
        when(literacyStatusRepo.getLiteracyStatus()).thenReturn(new ArrayList<>());
        when(maritalStatusMasterRepo.getMaritalStatusMaster()).thenReturn(new ArrayList<>());
        when(occupationMasterRepo.getOccupationMaster()).thenReturn(new ArrayList<>());
        when(qualificationMasterRepo.getQualificationMaster()).thenReturn(new ArrayList<>());
        when(relationshipMasterRepo.getRelationshipMaster()).thenReturn(new ArrayList<>());
        when(religionMasterRepo.getReligionMaster()).thenReturn(new ArrayList<>());

        // Act
        String actualRegMasterData = registrarServiceMasterDataImpl.getRegMasterData();

        // Assert
        verify(ageUnitRepo).getAgeUnit();
        verify(communityMasterRepo).getCommunityMaster();
        verify(genderMasterRepo).getGenderMaster();
        verify(govIdEntityTypeRepo).getGovIdEntityMaster();
        verify(govIdEntityTypeRepo).getOtherGovIdEntityMaster();
        verify(incomeStatusMasterRepo).getIncomeStatusMaster();
        verify(literacyStatusRepo).getLiteracyStatus();
        verify(maritalStatusMasterRepo).getMaritalStatusMaster();
        verify(occupationMasterRepo).getOccupationMaster();
        verify(qualificationMasterRepo).getQualificationMaster();
        verify(relationshipMasterRepo).getRelationshipMaster();
        verify(religionMasterRepo).getReligionMaster();
        assertEquals("{\n  \"communityMaster\": [],\n  \"genderMaster\": []\n}", actualRegMasterData);
    }

    @Test
    void testGetRegMasterData7() {
        // Arrange
        when(ageUnitRepo.getAgeUnit()).thenReturn(new ArrayList<>());
        when(communityMasterRepo.getCommunityMaster()).thenReturn(new ArrayList<>());
        when(genderMasterRepo.getGenderMaster()).thenReturn(new ArrayList<>());

        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        objectArrayList.add(new Object[]{"42"});
        when(govIdEntityTypeRepo.getGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(govIdEntityTypeRepo.getOtherGovIdEntityMaster()).thenReturn(objectArrayList);
        when(incomeStatusMasterRepo.getIncomeStatusMaster()).thenReturn(new ArrayList<>());
        when(literacyStatusRepo.getLiteracyStatus()).thenReturn(new ArrayList<>());
        when(maritalStatusMasterRepo.getMaritalStatusMaster()).thenReturn(new ArrayList<>());
        when(occupationMasterRepo.getOccupationMaster()).thenReturn(new ArrayList<>());
        when(qualificationMasterRepo.getQualificationMaster()).thenReturn(new ArrayList<>());
        when(relationshipMasterRepo.getRelationshipMaster()).thenReturn(new ArrayList<>());
        when(religionMasterRepo.getReligionMaster()).thenReturn(new ArrayList<>());

        // Act
        String actualRegMasterData = registrarServiceMasterDataImpl.getRegMasterData();

        // Assert
        verify(ageUnitRepo).getAgeUnit();
        verify(communityMasterRepo).getCommunityMaster();
        verify(genderMasterRepo).getGenderMaster();
        verify(govIdEntityTypeRepo).getGovIdEntityMaster();
        verify(govIdEntityTypeRepo).getOtherGovIdEntityMaster();
        verify(incomeStatusMasterRepo).getIncomeStatusMaster();
        verify(literacyStatusRepo).getLiteracyStatus();
        verify(maritalStatusMasterRepo).getMaritalStatusMaster();
        verify(occupationMasterRepo).getOccupationMaster();
        verify(qualificationMasterRepo).getQualificationMaster();
        verify(relationshipMasterRepo).getRelationshipMaster();
        verify(religionMasterRepo).getReligionMaster();
        assertEquals("{\n  \"communityMaster\": [],\n  \"govIdEntityMaster\": [],\n  \"genderMaster\": []\n}",
                actualRegMasterData);
    }

    @Test
    void testGetRegMasterData8() {
        // Arrange
        when(ageUnitRepo.getAgeUnit()).thenReturn(new ArrayList<>());
        when(communityMasterRepo.getCommunityMaster()).thenReturn(new ArrayList<>());
        when(genderMasterRepo.getGenderMaster()).thenReturn(new ArrayList<>());
        when(govIdEntityTypeRepo.getGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(govIdEntityTypeRepo.getOtherGovIdEntityMaster()).thenReturn(new ArrayList<>());

        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        objectArrayList.add(new Object[]{"42"});
        when(incomeStatusMasterRepo.getIncomeStatusMaster()).thenReturn(objectArrayList);
        when(literacyStatusRepo.getLiteracyStatus()).thenReturn(new ArrayList<>());
        when(maritalStatusMasterRepo.getMaritalStatusMaster()).thenReturn(new ArrayList<>());
        when(occupationMasterRepo.getOccupationMaster()).thenReturn(new ArrayList<>());
        when(qualificationMasterRepo.getQualificationMaster()).thenReturn(new ArrayList<>());
        when(relationshipMasterRepo.getRelationshipMaster()).thenReturn(new ArrayList<>());
        when(religionMasterRepo.getReligionMaster()).thenReturn(new ArrayList<>());

        // Act
        String actualRegMasterData = registrarServiceMasterDataImpl.getRegMasterData();

        // Assert
        verify(ageUnitRepo).getAgeUnit();
        verify(communityMasterRepo).getCommunityMaster();
        verify(genderMasterRepo).getGenderMaster();
        verify(govIdEntityTypeRepo).getGovIdEntityMaster();
        verify(govIdEntityTypeRepo).getOtherGovIdEntityMaster();
        verify(incomeStatusMasterRepo).getIncomeStatusMaster();
        verify(literacyStatusRepo).getLiteracyStatus();
        verify(maritalStatusMasterRepo).getMaritalStatusMaster();
        verify(occupationMasterRepo).getOccupationMaster();
        verify(qualificationMasterRepo).getQualificationMaster();
        verify(relationshipMasterRepo).getRelationshipMaster();
        verify(religionMasterRepo).getReligionMaster();
        assertEquals("{\n" + "  \"communityMaster\": [],\n" + "  \"govIdEntityMaster\": [],\n" + "  \"genderMaster\": [],\n"
                + "  \"otherGovIdEntityMaster\": []\n" + "}", actualRegMasterData);
    }

    @Test
    void testGetRegMasterData9() {
        // Arrange
        when(ageUnitRepo.getAgeUnit()).thenReturn(new ArrayList<>());
        when(communityMasterRepo.getCommunityMaster()).thenReturn(new ArrayList<>());
        when(genderMasterRepo.getGenderMaster()).thenReturn(new ArrayList<>());
        when(govIdEntityTypeRepo.getGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(govIdEntityTypeRepo.getOtherGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(incomeStatusMasterRepo.getIncomeStatusMaster()).thenReturn(new ArrayList<>());
        when(literacyStatusRepo.getLiteracyStatus()).thenReturn(new ArrayList<>());

        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        objectArrayList.add(new Object[]{"42"});
        when(maritalStatusMasterRepo.getMaritalStatusMaster()).thenReturn(objectArrayList);
        when(occupationMasterRepo.getOccupationMaster()).thenReturn(new ArrayList<>());
        when(qualificationMasterRepo.getQualificationMaster()).thenReturn(new ArrayList<>());
        when(relationshipMasterRepo.getRelationshipMaster()).thenReturn(new ArrayList<>());
        when(religionMasterRepo.getReligionMaster()).thenReturn(new ArrayList<>());

        // Act
        String actualRegMasterData = registrarServiceMasterDataImpl.getRegMasterData();

        // Assert
        verify(ageUnitRepo).getAgeUnit();
        verify(communityMasterRepo).getCommunityMaster();
        verify(genderMasterRepo).getGenderMaster();
        verify(govIdEntityTypeRepo).getGovIdEntityMaster();
        verify(govIdEntityTypeRepo).getOtherGovIdEntityMaster();
        verify(incomeStatusMasterRepo).getIncomeStatusMaster();
        verify(literacyStatusRepo).getLiteracyStatus();
        verify(maritalStatusMasterRepo).getMaritalStatusMaster();
        verify(occupationMasterRepo).getOccupationMaster();
        verify(qualificationMasterRepo).getQualificationMaster();
        verify(relationshipMasterRepo).getRelationshipMaster();
        verify(religionMasterRepo).getReligionMaster();
        assertEquals("{\n" + "  \"incomeMaster\": [],\n" + "  \"communityMaster\": [],\n" + "  \"govIdEntityMaster\": [],\n"
                + "  \"genderMaster\": [],\n" + "  \"otherGovIdEntityMaster\": []\n" + "}", actualRegMasterData);
    }

    @Test
    void testGetRegMasterData10() {
        // Arrange
        when(ageUnitRepo.getAgeUnit()).thenReturn(new ArrayList<>());
        when(communityMasterRepo.getCommunityMaster()).thenReturn(new ArrayList<>());
        when(genderMasterRepo.getGenderMaster()).thenReturn(new ArrayList<>());
        when(govIdEntityTypeRepo.getGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(govIdEntityTypeRepo.getOtherGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(incomeStatusMasterRepo.getIncomeStatusMaster()).thenReturn(new ArrayList<>());
        when(literacyStatusRepo.getLiteracyStatus()).thenReturn(new ArrayList<>());
        when(maritalStatusMasterRepo.getMaritalStatusMaster()).thenReturn(new ArrayList<>());

        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        objectArrayList.add(new Object[]{"42"});
        when(occupationMasterRepo.getOccupationMaster()).thenReturn(objectArrayList);
        when(qualificationMasterRepo.getQualificationMaster()).thenReturn(new ArrayList<>());
        when(relationshipMasterRepo.getRelationshipMaster()).thenReturn(new ArrayList<>());
        when(religionMasterRepo.getReligionMaster()).thenReturn(new ArrayList<>());

        // Act
        String actualRegMasterData = registrarServiceMasterDataImpl.getRegMasterData();

        // Assert
        verify(ageUnitRepo).getAgeUnit();
        verify(communityMasterRepo).getCommunityMaster();
        verify(genderMasterRepo).getGenderMaster();
        verify(govIdEntityTypeRepo).getGovIdEntityMaster();
        verify(govIdEntityTypeRepo).getOtherGovIdEntityMaster();
        verify(incomeStatusMasterRepo).getIncomeStatusMaster();
        verify(literacyStatusRepo).getLiteracyStatus();
        verify(maritalStatusMasterRepo).getMaritalStatusMaster();
        verify(occupationMasterRepo).getOccupationMaster();
        verify(qualificationMasterRepo).getQualificationMaster();
        verify(relationshipMasterRepo).getRelationshipMaster();
        verify(religionMasterRepo).getReligionMaster();
        assertEquals("{\n" + "  \"incomeMaster\": [],\n" + "  \"maritalStatusMaster\": [],\n"
                + "  \"communityMaster\": [],\n" + "  \"govIdEntityMaster\": [],\n" + "  \"genderMaster\": [],\n"
                + "  \"otherGovIdEntityMaster\": []\n" + "}", actualRegMasterData);
    }

    @Test
    void testGetRegMasterData11() {
        // Arrange
        when(ageUnitRepo.getAgeUnit()).thenReturn(new ArrayList<>());
        when(communityMasterRepo.getCommunityMaster()).thenReturn(new ArrayList<>());
        when(genderMasterRepo.getGenderMaster()).thenReturn(new ArrayList<>());
        when(govIdEntityTypeRepo.getGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(govIdEntityTypeRepo.getOtherGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(incomeStatusMasterRepo.getIncomeStatusMaster()).thenReturn(new ArrayList<>());
        when(literacyStatusRepo.getLiteracyStatus()).thenReturn(new ArrayList<>());
        when(maritalStatusMasterRepo.getMaritalStatusMaster()).thenReturn(new ArrayList<>());
        when(occupationMasterRepo.getOccupationMaster()).thenReturn(new ArrayList<>());

        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        objectArrayList.add(new Object[]{"42"});
        when(qualificationMasterRepo.getQualificationMaster()).thenReturn(objectArrayList);
        when(relationshipMasterRepo.getRelationshipMaster()).thenReturn(new ArrayList<>());
        when(religionMasterRepo.getReligionMaster()).thenReturn(new ArrayList<>());

        // Act
        String actualRegMasterData = registrarServiceMasterDataImpl.getRegMasterData();

        // Assert
        verify(ageUnitRepo).getAgeUnit();
        verify(communityMasterRepo).getCommunityMaster();
        verify(genderMasterRepo).getGenderMaster();
        verify(govIdEntityTypeRepo).getGovIdEntityMaster();
        verify(govIdEntityTypeRepo).getOtherGovIdEntityMaster();
        verify(incomeStatusMasterRepo).getIncomeStatusMaster();
        verify(literacyStatusRepo).getLiteracyStatus();
        verify(maritalStatusMasterRepo).getMaritalStatusMaster();
        verify(occupationMasterRepo).getOccupationMaster();
        verify(qualificationMasterRepo).getQualificationMaster();
        verify(relationshipMasterRepo).getRelationshipMaster();
        verify(religionMasterRepo).getReligionMaster();
        assertEquals("{\n" + "  \"occupationMaster\": [],\n" + "  \"incomeMaster\": [],\n"
                + "  \"maritalStatusMaster\": [],\n" + "  \"communityMaster\": [],\n" + "  \"govIdEntityMaster\": [],\n"
                + "  \"genderMaster\": [],\n" + "  \"otherGovIdEntityMaster\": []\n" + "}", actualRegMasterData);
    }

    @Test
    void testGetRegMasterData12() {
        // Arrange
        when(ageUnitRepo.getAgeUnit()).thenReturn(new ArrayList<>());
        when(communityMasterRepo.getCommunityMaster()).thenReturn(new ArrayList<>());
        when(genderMasterRepo.getGenderMaster()).thenReturn(new ArrayList<>());
        when(govIdEntityTypeRepo.getGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(govIdEntityTypeRepo.getOtherGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(incomeStatusMasterRepo.getIncomeStatusMaster()).thenReturn(new ArrayList<>());
        when(literacyStatusRepo.getLiteracyStatus()).thenReturn(new ArrayList<>());
        when(maritalStatusMasterRepo.getMaritalStatusMaster()).thenReturn(new ArrayList<>());
        when(occupationMasterRepo.getOccupationMaster()).thenReturn(new ArrayList<>());
        when(qualificationMasterRepo.getQualificationMaster()).thenReturn(new ArrayList<>());

        ArrayList<RelationshipMaster> relationshipMasterList = new ArrayList<>();
        relationshipMasterList.add(new RelationshipMaster());
        when(relationshipMasterRepo.getRelationshipMaster()).thenReturn(relationshipMasterList);
        when(religionMasterRepo.getReligionMaster()).thenReturn(new ArrayList<>());

        // Act
        String actualRegMasterData = registrarServiceMasterDataImpl.getRegMasterData();

        // Assert
        verify(ageUnitRepo).getAgeUnit();
        verify(communityMasterRepo).getCommunityMaster();
        verify(genderMasterRepo).getGenderMaster();
        verify(govIdEntityTypeRepo).getGovIdEntityMaster();
        verify(govIdEntityTypeRepo).getOtherGovIdEntityMaster();
        verify(incomeStatusMasterRepo).getIncomeStatusMaster();
        verify(literacyStatusRepo).getLiteracyStatus();
        verify(maritalStatusMasterRepo).getMaritalStatusMaster();
        verify(occupationMasterRepo).getOccupationMaster();
        verify(qualificationMasterRepo).getQualificationMaster();
        verify(relationshipMasterRepo).getRelationshipMaster();
        verify(religionMasterRepo).getReligionMaster();
        assertEquals("{\n" + "  \"ageUnit\": [],\n" + "  \"occupationMaster\": [],\n" + "  \"literacyStatus\": [],\n"
                + "  \"incomeMaster\": [],\n" + "  \"maritalStatusMaster\": [],\n" + "  \"religionMaster\": [],\n"
                + "  \"communityMaster\": [],\n" + "  \"qualificationMaster\": [],\n" + "  \"relationshipMaster\": [\n"
                + "    {}\n" + "  ],\n" + "  \"govIdEntityMaster\": [],\n" + "  \"genderMaster\": [],\n"
                + "  \"otherGovIdEntityMaster\": []\n" + "}", actualRegMasterData);
    }

    @Test
    void testGetRegMasterData13() {
        // Arrange
        when(ageUnitRepo.getAgeUnit()).thenReturn(new ArrayList<>());
        when(communityMasterRepo.getCommunityMaster()).thenReturn(new ArrayList<>());
        when(genderMasterRepo.getGenderMaster()).thenReturn(new ArrayList<>());
        when(govIdEntityTypeRepo.getGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(govIdEntityTypeRepo.getOtherGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(incomeStatusMasterRepo.getIncomeStatusMaster()).thenReturn(new ArrayList<>());
        when(literacyStatusRepo.getLiteracyStatus()).thenReturn(new ArrayList<>());
        when(maritalStatusMasterRepo.getMaritalStatusMaster()).thenReturn(new ArrayList<>());
        when(occupationMasterRepo.getOccupationMaster()).thenReturn(new ArrayList<>());
        when(qualificationMasterRepo.getQualificationMaster()).thenReturn(new ArrayList<>());
        when(relationshipMasterRepo.getRelationshipMaster()).thenReturn(new ArrayList<>());

        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        objectArrayList.add(new Object[]{"42"});
        when(religionMasterRepo.getReligionMaster()).thenReturn(objectArrayList);

        // Act
        String actualRegMasterData = registrarServiceMasterDataImpl.getRegMasterData();

        // Assert
        verify(ageUnitRepo).getAgeUnit();
        verify(communityMasterRepo).getCommunityMaster();
        verify(genderMasterRepo).getGenderMaster();
        verify(govIdEntityTypeRepo).getGovIdEntityMaster();
        verify(govIdEntityTypeRepo).getOtherGovIdEntityMaster();
        verify(incomeStatusMasterRepo).getIncomeStatusMaster();
        verify(literacyStatusRepo).getLiteracyStatus();
        verify(maritalStatusMasterRepo).getMaritalStatusMaster();
        verify(occupationMasterRepo).getOccupationMaster();
        verify(qualificationMasterRepo).getQualificationMaster();
        verify(relationshipMasterRepo).getRelationshipMaster();
        verify(religionMasterRepo).getReligionMaster();
        assertEquals("{\n" + "  \"occupationMaster\": [],\n" + "  \"incomeMaster\": [],\n"
                        + "  \"maritalStatusMaster\": [],\n" + "  \"communityMaster\": [],\n" + "  \"qualificationMaster\": [],\n"
                        + "  \"govIdEntityMaster\": [],\n" + "  \"genderMaster\": [],\n" + "  \"otherGovIdEntityMaster\": []\n" + "}",
                actualRegMasterData);
    }

    @Test
    void testGetRegMasterData14() {
        // Arrange
        ArrayList<AgeUnit> ageUnitList = new ArrayList<>();
        ageUnitList.add(mock(AgeUnit.class));
        when(ageUnitRepo.getAgeUnit()).thenReturn(ageUnitList);
        when(communityMasterRepo.getCommunityMaster()).thenReturn(new ArrayList<>());
        when(genderMasterRepo.getGenderMaster()).thenReturn(new ArrayList<>());
        when(govIdEntityTypeRepo.getGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(govIdEntityTypeRepo.getOtherGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(incomeStatusMasterRepo.getIncomeStatusMaster()).thenReturn(new ArrayList<>());
        when(literacyStatusRepo.getLiteracyStatus()).thenReturn(new ArrayList<>());
        when(maritalStatusMasterRepo.getMaritalStatusMaster()).thenReturn(new ArrayList<>());
        when(occupationMasterRepo.getOccupationMaster()).thenReturn(new ArrayList<>());
        when(qualificationMasterRepo.getQualificationMaster()).thenReturn(new ArrayList<>());
        when(relationshipMasterRepo.getRelationshipMaster()).thenReturn(new ArrayList<>());
        when(religionMasterRepo.getReligionMaster()).thenReturn(new ArrayList<>());

        // Act
        String actualRegMasterData = registrarServiceMasterDataImpl.getRegMasterData();

        // Assert
        verify(ageUnitRepo).getAgeUnit();
        verify(communityMasterRepo).getCommunityMaster();
        verify(genderMasterRepo).getGenderMaster();
        verify(govIdEntityTypeRepo).getGovIdEntityMaster();
        verify(govIdEntityTypeRepo).getOtherGovIdEntityMaster();
        verify(incomeStatusMasterRepo).getIncomeStatusMaster();
        verify(literacyStatusRepo).getLiteracyStatus();
        verify(maritalStatusMasterRepo).getMaritalStatusMaster();
        verify(occupationMasterRepo).getOccupationMaster();
        verify(qualificationMasterRepo).getQualificationMaster();
        verify(relationshipMasterRepo).getRelationshipMaster();
        verify(religionMasterRepo).getReligionMaster();
        assertEquals("{\n" + "  \"ageUnit\": [\n" + "    {}\n" + "  ],\n" + "  \"occupationMaster\": [],\n"
                + "  \"literacyStatus\": [],\n" + "  \"incomeMaster\": [],\n" + "  \"maritalStatusMaster\": [],\n"
                + "  \"religionMaster\": [],\n" + "  \"communityMaster\": [],\n" + "  \"qualificationMaster\": [],\n"
                + "  \"relationshipMaster\": [],\n" + "  \"govIdEntityMaster\": [],\n" + "  \"genderMaster\": [],\n"
                + "  \"otherGovIdEntityMaster\": []\n" + "}", actualRegMasterData);
    }

    @Test
    void testGetRegMasterData15() {
        // Arrange
        when(ageUnitRepo.getAgeUnit()).thenReturn(new ArrayList<>());

        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        objectArrayList.add(new Object[]{});
        when(communityMasterRepo.getCommunityMaster()).thenReturn(objectArrayList);
        when(genderMasterRepo.getGenderMaster()).thenReturn(new ArrayList<>());
        when(govIdEntityTypeRepo.getGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(govIdEntityTypeRepo.getOtherGovIdEntityMaster()).thenReturn(new ArrayList<>());
        when(incomeStatusMasterRepo.getIncomeStatusMaster()).thenReturn(new ArrayList<>());
        when(literacyStatusRepo.getLiteracyStatus()).thenReturn(new ArrayList<>());
        when(maritalStatusMasterRepo.getMaritalStatusMaster()).thenReturn(new ArrayList<>());
        when(occupationMasterRepo.getOccupationMaster()).thenReturn(new ArrayList<>());
        when(qualificationMasterRepo.getQualificationMaster()).thenReturn(new ArrayList<>());
        when(relationshipMasterRepo.getRelationshipMaster()).thenReturn(new ArrayList<>());
        when(religionMasterRepo.getReligionMaster()).thenReturn(new ArrayList<>());

        // Act
        String actualRegMasterData = registrarServiceMasterDataImpl.getRegMasterData();

        // Assert
        verify(ageUnitRepo).getAgeUnit();
        verify(communityMasterRepo).getCommunityMaster();
        verify(genderMasterRepo).getGenderMaster();
        verify(govIdEntityTypeRepo).getGovIdEntityMaster();
        verify(govIdEntityTypeRepo).getOtherGovIdEntityMaster();
        verify(incomeStatusMasterRepo).getIncomeStatusMaster();
        verify(literacyStatusRepo).getLiteracyStatus();
        verify(maritalStatusMasterRepo).getMaritalStatusMaster();
        verify(occupationMasterRepo).getOccupationMaster();
        verify(qualificationMasterRepo).getQualificationMaster();
        verify(relationshipMasterRepo).getRelationshipMaster();
        verify(religionMasterRepo).getReligionMaster();
        assertEquals("{}", actualRegMasterData);
    }

    @Test
    void testGetBenDetailsByRegID() {
        when(reistrarRepoBenSearch.getBenDetails(Mockito.<Long>any())).thenReturn(new ArrayList<>());

        // Act
        registrarServiceMasterDataImpl.getBenDetailsByRegID(1L);
    }

    @Test
    void testGetBenDetailsForLeftSideByRegIDNew() {
        I_bendemographics i_bendemographics = new I_bendemographics();
        i_bendemographics.setDistrictBranchID(1);
        i_bendemographics.setDistrictBranchName("janedoe/featurebranch");
        i_bendemographics.setDistrictID(1);
        i_bendemographics.setDistrictName("District Name");
        i_bendemographics.setServicePointID(1);
        i_bendemographics.setServicePointName("Service Point Name");

        GenderMaster m_gender = new GenderMaster();
        m_gender.setDeleted(true);
        m_gender.setGenderID((short) 1);
        m_gender.setGenderName("Gender Name");

        MasterVan masterVan = new MasterVan();
        masterVan.setDeleted(true);
        masterVan.setFacilityID(1);
        masterVan.setIsFacility(true);
        masterVan.setParkingPlaceID(1);
        masterVan.setProviderServiceMapID(1);
        masterVan.setSwymedEmailID("jane.doe@example.org");
        masterVan.setVanID(1);
        masterVan.setVanName("Van Name");
        masterVan.setVehicalNo("Vehical No");

        BeneficiaryFlowStatus beneficiaryFlowStatus = new BeneficiaryFlowStatus();
        beneficiaryFlowStatus.setAge("Age");
        beneficiaryFlowStatus.setAgeVal(42);
        beneficiaryFlowStatus.setAgentId("42");
        beneficiaryFlowStatus.setBenArrivedFlag(true);
        beneficiaryFlowStatus.setBenFlowID(1L);
        beneficiaryFlowStatus.setBenImage("Ben Image");
        beneficiaryFlowStatus.setBenName("Ben Name");
        beneficiaryFlowStatus.setBenPhoneMaps(new ArrayList<>());
        beneficiaryFlowStatus.setBenVisitCode(1L);
        beneficiaryFlowStatus.setBenVisitDate(mock(Timestamp.class));
        beneficiaryFlowStatus.setBenVisitID(1L);
        beneficiaryFlowStatus.setBenVisitNo((short) 1);
        beneficiaryFlowStatus.setBen_age_val(42);
        beneficiaryFlowStatus.setBeneficiaryID(1L);
        beneficiaryFlowStatus.setBeneficiaryName("Beneficiary Name");
        beneficiaryFlowStatus.setBeneficiaryRegID(1L);
        beneficiaryFlowStatus.setBloodGroup("Blood Group");
        beneficiaryFlowStatus.setConsultantID(1);
        beneficiaryFlowStatus.setConsultantName("Consultant Name");
        beneficiaryFlowStatus.setConsultationDate(mock(Timestamp.class));
        beneficiaryFlowStatus.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        beneficiaryFlowStatus.setCreatedDate(mock(Timestamp.class));
        beneficiaryFlowStatus.setDeleted(true);
        beneficiaryFlowStatus.setDistrictID(1);
        beneficiaryFlowStatus.setDistrictName("District Name");
        beneficiaryFlowStatus.setDoctorFlag((short) 1);
        beneficiaryFlowStatus.setFatherName("Father Name");
        beneficiaryFlowStatus.setFirstName("Jane");
        beneficiaryFlowStatus.setGenderID((short) 1);
        beneficiaryFlowStatus.setGenderName("Gender Name");
        beneficiaryFlowStatus.setI_bendemographics(i_bendemographics);
        beneficiaryFlowStatus.setIsMobile(true);
        beneficiaryFlowStatus.setLabIteration((short) 1);
        beneficiaryFlowStatus.setLab_technician_flag((short) 1);
        beneficiaryFlowStatus.setLastName("Doe");
        beneficiaryFlowStatus.setM_gender(m_gender);
        beneficiaryFlowStatus.setMasterVan(masterVan);
        beneficiaryFlowStatus.setModified_by("Jan 1, 2020 9:00am GMT+0100");
        beneficiaryFlowStatus.setModified_date(mock(Timestamp.class));
        beneficiaryFlowStatus.setNurseFlag((short) 1);
        beneficiaryFlowStatus.setOncologist_flag((short) 1);
        beneficiaryFlowStatus.setParkingPlaceID(1);
        beneficiaryFlowStatus.setPassToNurse(true);
        beneficiaryFlowStatus.setPharmacist_flag((short) 1);
        beneficiaryFlowStatus.setPreferredPhoneNum("6625550144");
        beneficiaryFlowStatus.setProcessed("Processed");
        beneficiaryFlowStatus.setProviderServiceMapID(1);
        beneficiaryFlowStatus.setProviderServiceMapId(1);
        beneficiaryFlowStatus.setRadiologist_flag((short) 1);
        beneficiaryFlowStatus.setReferredVisitCode(1L);
        beneficiaryFlowStatus.setReferred_visit_id(1L);
        beneficiaryFlowStatus.setRegistrationDate(mock(Timestamp.class));
        beneficiaryFlowStatus.setReservedForChange("Reserved For Change");
        beneficiaryFlowStatus.setServiceDate(mock(Timestamp.class));
        beneficiaryFlowStatus.setServicePointID(1);
        beneficiaryFlowStatus.setServicePointName("Service Point Name");
        beneficiaryFlowStatus.setSpecialist_flag((short) 1);
        beneficiaryFlowStatus.setSpouseName("Spouse Name");
        beneficiaryFlowStatus.setSubVisitCategory("Sub Visit Category");
        beneficiaryFlowStatus.setSyncedBy("Synced By");
        beneficiaryFlowStatus.setSyncedDate(mock(Timestamp.class));
        beneficiaryFlowStatus.setVanID(1);
        beneficiaryFlowStatus.setVanNo("Van No");
        beneficiaryFlowStatus.setVanSerialNo(1L);
        beneficiaryFlowStatus.setVehicalNo("Vehical No");
        beneficiaryFlowStatus.setVillageID(1);
        beneficiaryFlowStatus.setVillageName("Village Name");
        beneficiaryFlowStatus.setVisitCategory("Visit Category");
        beneficiaryFlowStatus.setVisitCode(1L);
        beneficiaryFlowStatus.setVisitDate(mock(Timestamp.class));
        beneficiaryFlowStatus.setVisitReason("Just cause");
        beneficiaryFlowStatus.setVisitSession(1);
        beneficiaryFlowStatus.setdOB(mock(Timestamp.class));
        beneficiaryFlowStatus.settCRequestDate(mock(Timestamp.class));
        beneficiaryFlowStatus.settCSpecialistUserID(1);
        beneficiaryFlowStatus.settC_SpecialistLabFlag((short) 1);
        when(beneficiaryFlowStatusRepo.getBenDetailsForLeftSidePanel(Mockito.<Long>any()))
                .thenReturn(beneficiaryFlowStatus);
        when(aNCCareRepo.getBenANCCareDetailsStatus(Mockito.<Long>any())).thenReturn("Ben ANCCare Details Status");

        // Act
        registrarServiceMasterDataImpl.getBenDetailsForLeftSideByRegIDNew(1L, 1L, "JaneDoe", "Coming Request");
    }

    @Test
    void testGetBenImageFromIdentityAPI() throws Exception {
        registrarServiceMasterDataImpl.getBenImageFromIdentityAPI("JaneDoe", "Coming Request");
    }

    @Test
    void testGettersAndSetters() {
        RegistrarServiceMasterDataImpl registrarServiceMasterDataImpl = new RegistrarServiceMasterDataImpl();

        // Act
        registrarServiceMasterDataImpl.setAgeUnitRepo(mock(AgeUnitRepo.class));
        registrarServiceMasterDataImpl.setBeneficiaryFlowStatusRepo(mock(BeneficiaryFlowStatusRepo.class));
        registrarServiceMasterDataImpl.setBeneficiaryImageRepo(mock(BeneficiaryImageRepo.class));
        registrarServiceMasterDataImpl.setCommunityMasterRepo(mock(CommunityMasterRepo.class));
        registrarServiceMasterDataImpl.setGenderMasterRepo(mock(GenderMasterRepo.class));
        registrarServiceMasterDataImpl.setGovIdEntityTypeRepo(mock(GovIdEntityTypeRepo.class));
        registrarServiceMasterDataImpl.setIncomeStatusMasterRepo(mock(IncomeStatusMasterRepo.class));
        registrarServiceMasterDataImpl.setLiteracyStatusRepo(mock(LiteracyStatusRepo.class));
        registrarServiceMasterDataImpl.setMaritalStatusMasterRepo(mock(MaritalStatusMasterRepo.class));
        registrarServiceMasterDataImpl.setOccupationMasterRepo(mock(OccupationMasterRepo.class));
        registrarServiceMasterDataImpl.setQualificationMasterRepo(mock(QualificationMasterRepo.class));
        registrarServiceMasterDataImpl.setReistrarRepoAdvanceBenSearch(mock(ReistrarRepoBenSearch.class));
        registrarServiceMasterDataImpl.setRelationshipMasterRepo(mock(RelationshipMasterRepo.class));
        registrarServiceMasterDataImpl.setReligionMasterRepo(mock(ReligionMasterRepo.class));
        registrarServiceMasterDataImpl.setaNCCareRepo(mock(ANCCareRepo.class));
    }
}
