package com.iemr.hwc.service.login;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.google.gson.Gson;
import com.iemr.hwc.data.login.MasterVan;
import com.iemr.hwc.repo.login.*;

public class TestIemrMmuLoginServiceImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private UserParkingplaceMappingRepo userParkingplaceMappingRepo;

	@Mock
	private MasterVanRepo masterVanRepo;

	@Mock
	private VanServicepointMappingRepo vanServicepointMappingRepo;

	@Mock
	private ServicePointVillageMappingRepo servicePointVillageMappingRepo;

	@InjectMocks
	private IemrMmuLoginServiceImpl iemrMmuLoginServiceImpl;
	private IemrMmuLoginServiceImpl iemrMmuLoginService;

	private List<Object[]> servicePointVillageList;
	private Integer userID;
	private Integer providerServiceMapID;
	private Integer psmId;
	private List<Object[]> vanMasterList;

	@BeforeEach
	public void setUp() {
		// No additional setup required as @InjectMocks handles the injection
	}

	@Test
	public void testGetUserServicePointVanDetails_withParkingPlaces() {
		Integer userID = 1;

		List<Object[]> parkingPlaceList = new ArrayList<>();
		parkingPlaceList.add(new Object[] { 1, 1, "State1", 1, "District1", 1, "Block1" });
		when(userParkingplaceMappingRepo.getUserParkingPlce(userID)).thenReturn(parkingPlaceList);

		List<Object[]> vanList = new ArrayList<>();
		vanList.add(new Object[] { 1, "Van1" });
		when(masterVanRepo.getUserVanDatails(anySet())).thenReturn(vanList);

		List<Object[]> servicePointList = new ArrayList<>();
		servicePointList.add(new Object[] { 1, "ServicePoint1", "SessionType1" });
		when(vanServicepointMappingRepo.getuserSpSessionDetails(anySet())).thenReturn(servicePointList);

		String response = iemrMmuLoginServiceImpl.getUserServicePointVanDetails(userID);

		Map<String, ArrayList<Map<String, Object>>> responseMap = new Gson().fromJson(response, Map.class);

		assertNotNull(responseMap);
		assertTrue(responseMap.containsKey("userVanDetails"));
		assertTrue(responseMap.containsKey("userSpDetails"));
		assertTrue(responseMap.containsKey("parkingPlaceLocationList"));

		verify(userParkingplaceMappingRepo, times(1)).getUserParkingPlce(userID);
		verify(masterVanRepo, times(1)).getUserVanDatails(anySet());
		verify(vanServicepointMappingRepo, times(1)).getuserSpSessionDetails(anySet());
	}

	@Test
	public void testGetUserServicePointVanDetails_withoutParkingPlaces() {
		Integer userID = 1;

		List<Object[]> parkingPlaceList = new ArrayList<>();
		when(userParkingplaceMappingRepo.getUserParkingPlce(userID)).thenReturn(parkingPlaceList);

		String response = iemrMmuLoginServiceImpl.getUserServicePointVanDetails(userID);

		Map<String, ArrayList<Map<String, Object>>> responseMap = new Gson().fromJson(response, Map.class);

		assertNotNull(responseMap);
		assertFalse(responseMap.containsKey("userVanDetails"));
		assertFalse(responseMap.containsKey("userSpDetails"));
		assertFalse(responseMap.containsKey("parkingPlaceLocationList"));

		verify(userParkingplaceMappingRepo, times(1)).getUserParkingPlce(userID);
		verify(masterVanRepo, times(0)).getUserVanDatails(anySet());
		verify(vanServicepointMappingRepo, times(0)).getuserSpSessionDetails(anySet());
	}

	@Test
	public void testGetServicepointVillages_WithVillages() {
		when(servicePointVillageMappingRepo.getServicePointVillages(1)).thenReturn(servicePointVillageList);

		String response = iemrMmuLoginService.getServicepointVillages(1);

		assertTrue(response.contains("Village1"));
		assertTrue(response.contains("Village2"));
		verify(servicePointVillageMappingRepo, times(1)).getServicePointVillages(1);
	}

	@Test
	public void testGetServicepointVillages_NoVillages() {
		when(servicePointVillageMappingRepo.getServicePointVillages(2)).thenReturn(new ArrayList<>());

		String response = iemrMmuLoginService.getServicepointVillages(2);

		assertFalse(response.contains("Village1"));
		assertFalse(response.contains("Village2"));
		verify(servicePointVillageMappingRepo, times(1)).getServicePointVillages(2);
	}

	@Test
	public void testGetUserVanSpDetails() {
		// Mocking the behavior of userVanSpDetails_View_Repo
		List<Object[]> userVanSpDetailsList = new ArrayList<>();
		userVanSpDetailsList.add(new Object[] { 1, 1, "Van1", (short) 1, 1, "ServicePoint1", 1, 1 });
		when(userVanSpDetails_View_Repo.getUserVanSpDetails_View(userID, providerServiceMapID))
				.thenReturn(userVanSpDetailsList);

		// Mocking the behavior of userParkingplaceMappingRepo
		List<Object[]> parkingPlaceList = new ArrayList<>();
		parkingPlaceList.add(new Object[] { 1, 1, "State1", 1, "District1", 1, "Block1" });
		when(userParkingplaceMappingRepo.getUserParkingPlce(userID)).thenReturn(parkingPlaceList);

		// Calling the method to test
		String response = iemrMmuLoginServiceImpl.getUserVanSpDetails(userID, providerServiceMapID);

		// Expected response
		Map<String, Object> resMap = new HashMap<>();
		List<UserVanSpDetails_View> userVanSpDetails_ViewList = new ArrayList<>();
		userVanSpDetails_ViewList.add(new UserVanSpDetails_View(1, 1, "Van1", (short) 1, 1, "ServicePoint1", 1, 1, 0));
		resMap.put("UserVanSpDetails", userVanSpDetails_ViewList);

		Map<String, Object> parkingPlaceLocationMap = new HashMap<>();
		parkingPlaceLocationMap.put("parkingPlaceID", 1);
		parkingPlaceLocationMap.put("stateID", 1);
		parkingPlaceLocationMap.put("stateName", "State1");
		parkingPlaceLocationMap.put("districtID", 1);
		parkingPlaceLocationMap.put("districtName", "District1");
		parkingPlaceLocationMap.put("blockID", 1);
		parkingPlaceLocationMap.put("blockName", "Block1");
		resMap.put("UserLocDetails", parkingPlaceLocationMap);

		String expectedResponse = new Gson().toJson(resMap);

		// Asserting the response
		assertEquals(expectedResponse, response);

		// Verifying the interactions
		verify(userVanSpDetails_View_Repo, times(1)).getUserVanSpDetails_View(userID, providerServiceMapID);
		verify(userParkingplaceMappingRepo, times(1)).getUserParkingPlce(userID);
	}

	@Test
	public void testGetUserSpokeDetails_WithNonEmptyList() {
		when(masterVanRepo.getVanMaster(psmId)).thenReturn(vanMasterList);

		String response = iemrMmuLoginService.getUserSpokeDetails(psmId);

		List<MasterVan> expectedList = new ArrayList<>();
		expectedList.add(new MasterVan(0, "All"));
		expectedList.add(new MasterVan(1, "Van1"));
		expectedList.add(new MasterVan(2, "Van2"));

		assertEquals(new Gson().toJson(expectedList), response);
		verify(masterVanRepo, times(1)).getVanMaster(psmId);
	}

	@Test
	public void testGetUserSpokeDetails_WithEmptyList() {
		when(masterVanRepo.getVanMaster(psmId)).thenReturn(new ArrayList<>());

		String response = iemrMmuLoginService.getUserSpokeDetails(psmId);

		List<MasterVan> expectedList = new ArrayList<>();
		expectedList.add(new MasterVan(0, "All"));

		assertEquals(new Gson().toJson(expectedList), response);
		verify(masterVanRepo, times(1)).getVanMaster(psmId);
	}

}
