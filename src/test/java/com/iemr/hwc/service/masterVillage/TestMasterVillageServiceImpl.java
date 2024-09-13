package com.iemr.hwc.service.masterVillage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;

import com.google.gson.Gson;
import com.iemr.hwc.data.location.DistrictBranchMapping;
import com.iemr.hwc.data.login.Users;
import com.iemr.hwc.data.login.UsersMasterVillage;
import com.iemr.hwc.repo.location.DistrictBranchMasterRepo;
import com.iemr.hwc.repo.login.UserLoginRepo;
import com.iemr.hwc.repo.login.UserMasterVillageRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

public class TestMasterVillageServiceImpl {

	@ExtendWith(MockitoExtension.class)
	public class MasterVillageServiceImplTest {

		@Mock
		private UserMasterVillageRepo userMasterVillageRepo;

		@Mock
		private DistrictBranchMasterRepo districtBranchMasterRepo;

		@Mock
		private UserLoginRepo userLoginRepo;

		@InjectMocks
		private MasterVillageServiceImpl masterVillageService;

		private UsersMasterVillage usersMasterVillage;
		private DistrictBranchMapping districtBranchMapping;
		private Users user;

		@BeforeEach
		public void setUp() {
			usersMasterVillage = new UsersMasterVillage();
			districtBranchMapping = new DistrictBranchMapping();
			user = new Users();
		}

		@Test
		public void testSetMasterVillage_NewEntry() {
			Long userID = 1L;
			Integer villageID = 1;

			when(userMasterVillageRepo.getByUserID(userID)).thenReturn(null);
			when(districtBranchMasterRepo.findByDistrictBranchID(villageID)).thenReturn(districtBranchMapping);
			when(userLoginRepo.getUserByUserID(userID)).thenReturn(user);
			when(userMasterVillageRepo.save(any(UsersMasterVillage.class))).thenReturn(usersMasterVillage);

			String response = masterVillageService.setMasterVillage(userID, villageID);

			assertEquals(new Gson().toJson(districtBranchMapping), response);
			verify(userMasterVillageRepo, times(1)).getByUserID(userID);
			verify(districtBranchMasterRepo, times(1)).findByDistrictBranchID(villageID);
			verify(userLoginRepo, times(1)).getUserByUserID(userID);
			verify(userMasterVillageRepo, times(1)).save(any(UsersMasterVillage.class));
		}

		@Test
		public void testSetMasterVillage_ExistingEntry() {
			Long userID = 1L;
			Integer villageID = 1;

			when(userMasterVillageRepo.getByUserID(userID)).thenReturn(usersMasterVillage);

			String response = masterVillageService.setMasterVillage(userID, villageID);

			assertEquals(new Gson().toJson(usersMasterVillage.getMasterVillage()), response);
			verify(userMasterVillageRepo, times(1)).getByUserID(userID);
			verify(districtBranchMasterRepo, never()).findByDistrictBranchID(anyInt());
			verify(userLoginRepo, never()).getUserByUserID(anyLong());
			verify(userMasterVillageRepo, never()).save(any(UsersMasterVillage.class));
		}

		@Test
		public void testSetMasterVillage_DistrictBranchMappingNotExist() {
			Long userID = 1L;
			Integer villageID = 1;

			when(userMasterVillageRepo.getByUserID(userID)).thenReturn(null);
			when(districtBranchMasterRepo.findByDistrictBranchID(villageID)).thenReturn(null);
			when(userLoginRepo.getUserByUserID(userID)).thenReturn(user);

			String response = masterVillageService.setMasterVillage(userID, villageID);

			assertEquals("villageID_not_exist", response);
			verify(userMasterVillageRepo, times(1)).getByUserID(userID);
			verify(districtBranchMasterRepo, times(1)).findByDistrictBranchID(villageID);
			verify(userLoginRepo, times(1)).getUserByUserID(userID);
			verify(userMasterVillageRepo, never()).save(any(UsersMasterVillage.class));
		}

		@Test
		public void testSetMasterVillage_UserNotExist() {
			Long userID = 1L;
			Integer villageID = 1;

			when(userMasterVillageRepo.getByUserID(userID)).thenReturn(null);
			when(districtBranchMasterRepo.findByDistrictBranchID(villageID)).thenReturn(districtBranchMapping);
			when(userLoginRepo.getUserByUserID(userID)).thenReturn(null);

			String response = masterVillageService.setMasterVillage(userID, villageID);

			assertEquals("userID_not_exist", response);
			verify(userMasterVillageRepo, times(1)).getByUserID(userID);
			verify(districtBranchMasterRepo, times(1)).findByDistrictBranchID(villageID);
			verify(userLoginRepo, times(1)).getUserByUserID(userID);
			verify(userMasterVillageRepo, never()).save(any(UsersMasterVillage.class));
		}

		@Test
		public void testGetMasterVillage_UserExists() {
			Long userID = 1L;

			when(userMasterVillageRepo.getByUserID(userID)).thenReturn(usersMasterVillage);

			UsersMasterVillage response = masterVillageService.getMasterVillage(userID);

			assertEquals(usersMasterVillage, response);
			verify(userMasterVillageRepo, times(1)).getByUserID(userID);
		}

		@Test
		public void testGetMasterVillage_UserNotExists() {
			Long userID = 1L;

			when(userMasterVillageRepo.getByUserID(userID)).thenReturn(null);

			UsersMasterVillage response = masterVillageService.getMasterVillage(userID);

			assertNull(response);
			verify(userMasterVillageRepo, times(1)).getByUserID(userID);
		}
	}
}
