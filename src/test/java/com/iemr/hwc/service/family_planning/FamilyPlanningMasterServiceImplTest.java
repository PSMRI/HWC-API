package com.iemr.hwc.service.family_planning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.iemr.hwc.data.doctor.ChiefComplaintMaster;
import com.iemr.hwc.data.masterdata.registrar.AgeUnit;
import com.iemr.hwc.data.masterdata.registrar.GenderMaster;
import com.iemr.hwc.repo.doctor.ChiefComplaintMasterRepo;
import com.iemr.hwc.repo.labModule.ProcedureRepo;
import com.iemr.hwc.repo.masterrepo.AgeUnitRepo;
import com.iemr.hwc.repo.masterrepo.GenderMasterRepo;
import com.iemr.hwc.repo.masterrepo.nurse.FPCounselledOnRepo;
import com.iemr.hwc.repo.masterrepo.nurse.FPMethodFollowupRepo;
import com.iemr.hwc.repo.masterrepo.nurse.FPSideEffectsRepo;
import com.iemr.hwc.repo.masterrepo.nurse.FertilityStatusRepo;
import com.iemr.hwc.repo.masterrepo.nurse.IUCDinsertiondonebyRepo;
import com.iemr.hwc.repo.masterrepo.nurse.TypeofIUCDinsertedRepo;
import com.iemr.hwc.utils.exception.IEMRException;

@ExtendWith(MockitoExtension.class)
class FamilyPlanningMasterServiceImplTest {
    @Mock
    private AgeUnitRepo ageUnitRepo;

    @Mock
    private ChiefComplaintMasterRepo chiefComplaintMasterRepo;

    @Mock
    private FPCounselledOnRepo fPCounselledOnRepo;

    @Mock
    private FPMethodFollowupRepo fPMethodFollowupRepo;

    @Mock
    private FPSideEffectsRepo fPSideEffectsRepo;

    @InjectMocks
    private FamilyPlanningMasterServiceImpl familyPlanningMasterServiceImpl;

    @Mock
    private FertilityStatusRepo fertilityStatusRepo;

    @Mock
    private GenderMasterRepo genderMasterRepo;

    @Mock
    private IUCDinsertiondonebyRepo iUCDinsertiondonebyRepo;

    @Mock
    private ProcedureRepo procedureRepo;

    @Mock
    private TypeofIUCDinsertedRepo typeofIUCDinsertedRepo;

    @Test
    void testGetMasterDataFP() throws IEMRException {
        // Arrange
        when(ageUnitRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(chiefComplaintMasterRepo.findByDeletedOrderByChiefComplaint(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(fPCounselledOnRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(fPMethodFollowupRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(fPSideEffectsRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(fertilityStatusRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(genderMasterRepo.findByDeletedOrderByGenderName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(iUCDinsertiondonebyRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(procedureRepo.getProcedureMasterData(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(typeofIUCDinsertedRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        // Act
        String actualMasterDataFP = familyPlanningMasterServiceImpl.getMasterDataFP(1, 1, "Gender");

        // Assert
        verify(chiefComplaintMasterRepo).findByDeletedOrderByChiefComplaint(isA(Boolean.class));
        verify(procedureRepo).getProcedureMasterData(isA(Integer.class), eq("Gender"));
        verify(ageUnitRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(genderMasterRepo).findByDeletedOrderByGenderName(isA(Boolean.class));
        verify(fPCounselledOnRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(fPMethodFollowupRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(fPSideEffectsRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(fertilityStatusRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(iUCDinsertiondonebyRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(typeofIUCDinsertedRepo).findByDeletedOrderByName(isA(Boolean.class));
        assertEquals(
                "{\"m_IUCDinsertiondoneby\":[],\"m_gender\":[],\"m_fpmethodfollowup\":[],\"procedures\":[],\"m_FertilityStatus"
                        + "\":[],\"m_FPCounselledOn\":[],\"m_TypeofIUCDinserted\":[],\"m_FPSideEffects\":[],\"m_ageunits\":[],\"chiefComp"
                        + "laintMaster\":[]}",
                actualMasterDataFP);
    }

    @Test
    void testGetMasterDataFP2() throws IEMRException {
        // Arrange
        ArrayList<AgeUnit> ageUnitList = new ArrayList<>();
        ageUnitList.add(new AgeUnit());
        when(ageUnitRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(ageUnitList);
        when(chiefComplaintMasterRepo.findByDeletedOrderByChiefComplaint(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(fPCounselledOnRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(fPMethodFollowupRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(fPSideEffectsRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(fertilityStatusRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(genderMasterRepo.findByDeletedOrderByGenderName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(iUCDinsertiondonebyRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(procedureRepo.getProcedureMasterData(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(typeofIUCDinsertedRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        // Act
        String actualMasterDataFP = familyPlanningMasterServiceImpl.getMasterDataFP(1, 1, "Gender");

        // Assert
        verify(chiefComplaintMasterRepo).findByDeletedOrderByChiefComplaint(isA(Boolean.class));
        verify(procedureRepo).getProcedureMasterData(isA(Integer.class), eq("Gender"));
        verify(ageUnitRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(genderMasterRepo).findByDeletedOrderByGenderName(isA(Boolean.class));
        verify(fPCounselledOnRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(fPMethodFollowupRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(fPSideEffectsRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(fertilityStatusRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(iUCDinsertiondonebyRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(typeofIUCDinsertedRepo).findByDeletedOrderByName(isA(Boolean.class));
        assertEquals(
                "{\"m_IUCDinsertiondoneby\":[],\"m_gender\":[],\"m_fpmethodfollowup\":[],\"procedures\":[],\"m_FertilityStatus"
                        + "\":[],\"m_FPCounselledOn\":[],\"m_TypeofIUCDinserted\":[],\"m_FPSideEffects\":[],\"m_ageunits\":[{}],"
                        + "\"chiefComplaintMaster\":[]}",
                actualMasterDataFP);
    }

    @Test
    void testGetMasterDataFP3() throws IEMRException {
        // Arrange
        ArrayList<AgeUnit> ageUnitList = new ArrayList<>();
        ageUnitList.add(new AgeUnit());
        ageUnitList.add(new AgeUnit());
        when(ageUnitRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(ageUnitList);
        when(chiefComplaintMasterRepo.findByDeletedOrderByChiefComplaint(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(fPCounselledOnRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(fPMethodFollowupRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(fPSideEffectsRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(fertilityStatusRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(genderMasterRepo.findByDeletedOrderByGenderName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(iUCDinsertiondonebyRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(procedureRepo.getProcedureMasterData(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(typeofIUCDinsertedRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        // Act
        String actualMasterDataFP = familyPlanningMasterServiceImpl.getMasterDataFP(1, 1, "Gender");

        // Assert
        verify(chiefComplaintMasterRepo).findByDeletedOrderByChiefComplaint(isA(Boolean.class));
        verify(procedureRepo).getProcedureMasterData(isA(Integer.class), eq("Gender"));
        verify(ageUnitRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(genderMasterRepo).findByDeletedOrderByGenderName(isA(Boolean.class));
        verify(fPCounselledOnRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(fPMethodFollowupRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(fPSideEffectsRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(fertilityStatusRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(iUCDinsertiondonebyRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(typeofIUCDinsertedRepo).findByDeletedOrderByName(isA(Boolean.class));
        assertEquals(
                "{\"m_IUCDinsertiondoneby\":[],\"m_gender\":[],\"m_fpmethodfollowup\":[],\"procedures\":[],\"m_FertilityStatus"
                        + "\":[],\"m_FPCounselledOn\":[],\"m_TypeofIUCDinserted\":[],\"m_FPSideEffects\":[],\"m_ageunits\":[{},{}],"
                        + "\"chiefComplaintMaster\":[]}",
                actualMasterDataFP);
    }

    @Test
    void testGetMasterDataFP4() throws IEMRException {
        // Arrange
        when(ageUnitRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        ChiefComplaintMaster chiefComplaintMaster = new ChiefComplaintMaster();
        chiefComplaintMaster.setChiefComplaint("m_FPSideEffects");
        chiefComplaintMaster.setChiefComplaintDesc("m_FPSideEffects");
        chiefComplaintMaster.setChiefComplaintID(1);
        chiefComplaintMaster.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        chiefComplaintMaster.setCreatedDate(mock(Timestamp.class));
        chiefComplaintMaster.setDeleted(true);
        chiefComplaintMaster.setLastModDate(mock(Timestamp.class));
        chiefComplaintMaster.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        chiefComplaintMaster.setProcessed('A');

        ArrayList<ChiefComplaintMaster> chiefComplaintMasterList = new ArrayList<>();
        chiefComplaintMasterList.add(chiefComplaintMaster);
        when(chiefComplaintMasterRepo.findByDeletedOrderByChiefComplaint(Mockito.<Boolean>any()))
                .thenReturn(chiefComplaintMasterList);
        when(fPCounselledOnRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(fPMethodFollowupRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(fPSideEffectsRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(fertilityStatusRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(genderMasterRepo.findByDeletedOrderByGenderName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(iUCDinsertiondonebyRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(procedureRepo.getProcedureMasterData(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(typeofIUCDinsertedRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        // Act
        String actualMasterDataFP = familyPlanningMasterServiceImpl.getMasterDataFP(1, 1, "Gender");

        // Assert
        verify(chiefComplaintMasterRepo).findByDeletedOrderByChiefComplaint(isA(Boolean.class));
        verify(procedureRepo).getProcedureMasterData(isA(Integer.class), eq("Gender"));
        verify(ageUnitRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(genderMasterRepo).findByDeletedOrderByGenderName(isA(Boolean.class));
        verify(fPCounselledOnRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(fPMethodFollowupRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(fPSideEffectsRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(fertilityStatusRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(iUCDinsertiondonebyRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(typeofIUCDinsertedRepo).findByDeletedOrderByName(isA(Boolean.class));
        assertEquals(
                "{\"m_IUCDinsertiondoneby\":[],\"m_gender\":[],\"m_fpmethodfollowup\":[],\"procedures\":[],\"m_FertilityStatus"
                        + "\":[],\"m_FPCounselledOn\":[],\"m_TypeofIUCDinserted\":[],\"m_FPSideEffects\":[],\"m_ageunits\":[],\"chiefComp"
                        + "laintMaster\":[{\"chiefComplaintID\":1,\"chiefComplaint\":\"m_FPSideEffects\",\"chiefComplaintDesc\":\"m"
                        + "_FPSideEffects\"}]}",
                actualMasterDataFP);
    }

    @Test
    void testGetMasterDataFP5() throws IEMRException {
        // Arrange
        when(ageUnitRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(chiefComplaintMasterRepo.findByDeletedOrderByChiefComplaint(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(fPCounselledOnRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(fPMethodFollowupRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(fPSideEffectsRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(fertilityStatusRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        GenderMaster genderMaster = new GenderMaster();
        genderMaster.setDeleted(true);
        genderMaster.setGenderID((short) 1);
        genderMaster.setGenderName("m_FPSideEffects");

        ArrayList<GenderMaster> genderMasterList = new ArrayList<>();
        genderMasterList.add(genderMaster);
        when(genderMasterRepo.findByDeletedOrderByGenderName(Mockito.<Boolean>any())).thenReturn(genderMasterList);
        when(iUCDinsertiondonebyRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(procedureRepo.getProcedureMasterData(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(typeofIUCDinsertedRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        // Act
        String actualMasterDataFP = familyPlanningMasterServiceImpl.getMasterDataFP(1, 1, "Gender");

        // Assert
        verify(chiefComplaintMasterRepo).findByDeletedOrderByChiefComplaint(isA(Boolean.class));
        verify(procedureRepo).getProcedureMasterData(isA(Integer.class), eq("Gender"));
        verify(ageUnitRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(genderMasterRepo).findByDeletedOrderByGenderName(isA(Boolean.class));
        verify(fPCounselledOnRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(fPMethodFollowupRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(fPSideEffectsRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(fertilityStatusRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(iUCDinsertiondonebyRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(typeofIUCDinsertedRepo).findByDeletedOrderByName(isA(Boolean.class));
        assertEquals(
                "{\"m_IUCDinsertiondoneby\":[],\"m_gender\":[{\"genderID\":1,\"genderName\":\"m_FPSideEffects\"}],\"m_fpmethodfollowup"
                        + "\":[],\"procedures\":[],\"m_FertilityStatus\":[],\"m_FPCounselledOn\":[],\"m_TypeofIUCDinserted\":[],\"m"
                        + "_FPSideEffects\":[],\"m_ageunits\":[],\"chiefComplaintMaster\":[]}",
                actualMasterDataFP);
    }

    @Test
    void testGetMasterDataFP6() throws IEMRException {
        // Arrange
        when(ageUnitRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(chiefComplaintMasterRepo.findByDeletedOrderByChiefComplaint(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(fPCounselledOnRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(fPSideEffectsRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(fertilityStatusRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(genderMasterRepo.findByDeletedOrderByGenderName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(iUCDinsertiondonebyRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(procedureRepo.getProcedureMasterData(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(typeofIUCDinsertedRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        // Act
        String actualMasterDataFP = familyPlanningMasterServiceImpl.getMasterDataFP(1, 1, null);

        // Assert
        verify(chiefComplaintMasterRepo).findByDeletedOrderByChiefComplaint(isA(Boolean.class));
        verify(procedureRepo).getProcedureMasterData(isA(Integer.class), isNull());
        verify(ageUnitRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(genderMasterRepo).findByDeletedOrderByGenderName(isA(Boolean.class));
        verify(fPCounselledOnRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(fPSideEffectsRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(fertilityStatusRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(iUCDinsertiondonebyRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(typeofIUCDinsertedRepo).findByDeletedOrderByName(isA(Boolean.class));
        assertEquals(
                "{\"m_IUCDinsertiondoneby\":[],\"m_gender\":[],\"procedures\":[],\"m_FertilityStatus\":[],\"m_FPCounselledOn\":"
                        + "[],\"m_TypeofIUCDinserted\":[],\"m_FPSideEffects\":[],\"m_ageunits\":[],\"chiefComplaintMaster\":[]}",
                actualMasterDataFP);
    }

    @Test
    void testGetMasterDataFP7() throws IEMRException {
        // Arrange
        when(ageUnitRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(chiefComplaintMasterRepo.findByDeletedOrderByChiefComplaint(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(fPCounselledOnRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(fPMethodFollowupRepo.findAllNameByGender(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(fPSideEffectsRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(fertilityStatusRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(genderMasterRepo.findByDeletedOrderByGenderName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(iUCDinsertiondonebyRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(procedureRepo.getProcedureMasterData(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(typeofIUCDinsertedRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        // Act
        String actualMasterDataFP = familyPlanningMasterServiceImpl.getMasterDataFP(1, 1, "female");

        // Assert
        verify(chiefComplaintMasterRepo).findByDeletedOrderByChiefComplaint(isA(Boolean.class));
        verify(procedureRepo).getProcedureMasterData(isA(Integer.class), eq("female"));
        verify(ageUnitRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(genderMasterRepo).findByDeletedOrderByGenderName(isA(Boolean.class));
        verify(fPCounselledOnRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(fPMethodFollowupRepo).findAllNameByGender(eq("female"));
        verify(fPSideEffectsRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(fertilityStatusRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(iUCDinsertiondonebyRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(typeofIUCDinsertedRepo).findByDeletedOrderByName(isA(Boolean.class));
        assertEquals(
                "{\"m_IUCDinsertiondoneby\":[],\"m_gender\":[],\"m_fpmethodfollowup\":[],\"procedures\":[],\"m_FertilityStatus"
                        + "\":[],\"m_FPCounselledOn\":[],\"m_TypeofIUCDinserted\":[],\"m_FPSideEffects\":[],\"m_ageunits\":[],\"chiefComp"
                        + "laintMaster\":[]}",
                actualMasterDataFP);
    }

    @Test
    void testGetMasterDataFP8() throws IEMRException {
        // Arrange
        when(ageUnitRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(chiefComplaintMasterRepo.findByDeletedOrderByChiefComplaint(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(fPCounselledOnRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(fPMethodFollowupRepo.findAllNameByGender(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(fPSideEffectsRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(fertilityStatusRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(genderMasterRepo.findByDeletedOrderByGenderName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(iUCDinsertiondonebyRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(procedureRepo.getProcedureMasterData(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(typeofIUCDinsertedRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        // Act
        String actualMasterDataFP = familyPlanningMasterServiceImpl.getMasterDataFP(1, 1, "male");

        // Assert
        verify(chiefComplaintMasterRepo).findByDeletedOrderByChiefComplaint(isA(Boolean.class));
        verify(procedureRepo).getProcedureMasterData(isA(Integer.class), eq("male"));
        verify(ageUnitRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(genderMasterRepo).findByDeletedOrderByGenderName(isA(Boolean.class));
        verify(fPCounselledOnRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(fPMethodFollowupRepo).findAllNameByGender(eq("male"));
        verify(fPSideEffectsRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(fertilityStatusRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(iUCDinsertiondonebyRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(typeofIUCDinsertedRepo).findByDeletedOrderByName(isA(Boolean.class));
        assertEquals(
                "{\"m_IUCDinsertiondoneby\":[],\"m_gender\":[],\"m_fpmethodfollowup\":[],\"procedures\":[],\"m_FertilityStatus"
                        + "\":[],\"m_FPCounselledOn\":[],\"m_TypeofIUCDinserted\":[],\"m_FPSideEffects\":[],\"m_ageunits\":[],\"chiefComp"
                        + "laintMaster\":[]}",
                actualMasterDataFP);
    }
}
