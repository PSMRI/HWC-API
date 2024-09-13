package com.iemr.hwc.service.family_planning;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iemr.hwc.data.labModule.ProcedureData;
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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestFamilyPlanningMasterServiceImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private FPMethodFollowupRepo fPMethodFollowupRepo;
	@Mock
	private FPSideEffectsRepo fPSideEffectsRepo;
	@Mock
	private FertilityStatusRepo fertilityStatusRepo;
	@Mock
	private TypeofIUCDinsertedRepo typeofIUCDinsertedRepo;
	@Mock
	private IUCDinsertiondonebyRepo iUCDinsertiondonebyRepo;
	@Mock
	private FPCounselledOnRepo fPCounselledOnRepo;
	@Mock
	private ChiefComplaintMasterRepo chiefComplaintMasterRepo;
	@Mock
	private AgeUnitRepo ageUnitRepo;
	@Mock
	private GenderMasterRepo genderMasterRepo;
	@Mock
	private ProcedureRepo procedureRepo;

	@InjectMocks
	private FamilyPlanningMasterServiceImpl familyPlanningMasterService;

	private Gson gson;

	@BeforeEach
	public void setUp() {
		gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	}

	@Test
	public void testGetMasterDataFP_Female() throws IEMRException {
		when(fPSideEffectsRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(fertilityStatusRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(typeofIUCDinsertedRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(iUCDinsertiondonebyRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(fPCounselledOnRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(ageUnitRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(genderMasterRepo.findByDeletedOrderByGenderName(false)).thenReturn(new ArrayList<>());
		when(procedureRepo.getProcedureMasterData(anyInt(), anyString())).thenReturn(new ArrayList<>());
		when(chiefComplaintMasterRepo.findByDeletedOrderByChiefComplaint(false)).thenReturn(new ArrayList<>());
		when(fPMethodFollowupRepo.findAllNameByGender("female")).thenReturn(new ArrayList<>());

		String result = familyPlanningMasterService.getMasterDataFP(1, 1, "female");

		Map<String, Object> resMap = gson.fromJson(result, HashMap.class);
		assertNotNull(resMap);
		verify(fPMethodFollowupRepo, times(1)).findAllNameByGender("female");
	}

	@Test
	public void testGetMasterDataFP_Male() throws IEMRException {
		when(fPSideEffectsRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(fertilityStatusRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(typeofIUCDinsertedRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(iUCDinsertiondonebyRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(fPCounselledOnRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(ageUnitRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(genderMasterRepo.findByDeletedOrderByGenderName(false)).thenReturn(new ArrayList<>());
		when(procedureRepo.getProcedureMasterData(anyInt(), anyString())).thenReturn(new ArrayList<>());
		when(chiefComplaintMasterRepo.findByDeletedOrderByChiefComplaint(false)).thenReturn(new ArrayList<>());
		when(fPMethodFollowupRepo.findAllNameByGender("male")).thenReturn(new ArrayList<>());

		String result = familyPlanningMasterService.getMasterDataFP(1, 1, "male");

		Map<String, Object> resMap = gson.fromJson(result, HashMap.class);
		assertNotNull(resMap);
		verify(fPMethodFollowupRepo, times(1)).findAllNameByGender("male");
	}

	@Test
	public void testGetMasterDataFP_NullGender() throws IEMRException {
		when(fPSideEffectsRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(fertilityStatusRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(typeofIUCDinsertedRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(iUCDinsertiondonebyRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(fPCounselledOnRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(ageUnitRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(genderMasterRepo.findByDeletedOrderByGenderName(false)).thenReturn(new ArrayList<>());
		when(procedureRepo.getProcedureMasterData(anyInt(), anyString())).thenReturn(new ArrayList<>());
		when(chiefComplaintMasterRepo.findByDeletedOrderByChiefComplaint(false)).thenReturn(new ArrayList<>());
		when(fPMethodFollowupRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());

		String result = familyPlanningMasterService.getMasterDataFP(1, 1, null);

		Map<String, Object> resMap = gson.fromJson(result, HashMap.class);
		assertNotNull(resMap);
		verify(fPMethodFollowupRepo, times(1)).findByDeletedOrderByName(false);
	}

	@Test
	public void testGetMasterDataFP_InvalidGender() throws IEMRException {
		when(fPSideEffectsRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(fertilityStatusRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(typeofIUCDinsertedRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(iUCDinsertiondonebyRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(fPCounselledOnRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(ageUnitRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(genderMasterRepo.findByDeletedOrderByGenderName(false)).thenReturn(new ArrayList<>());
		when(procedureRepo.getProcedureMasterData(anyInt(), anyString())).thenReturn(new ArrayList<>());
		when(chiefComplaintMasterRepo.findByDeletedOrderByChiefComplaint(false)).thenReturn(new ArrayList<>());
		when(fPMethodFollowupRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());

		String result = familyPlanningMasterService.getMasterDataFP(1, 1, "invalid");

		Map<String, Object> resMap = gson.fromJson(result, HashMap.class);
		assertNotNull(resMap);
		verify(fPMethodFollowupRepo, times(1)).findByDeletedOrderByName(false);
	}
}
