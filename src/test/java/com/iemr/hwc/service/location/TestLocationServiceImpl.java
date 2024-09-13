package com.iemr.hwc.service.location;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iemr.hwc.data.choApp.Outreach;
import com.iemr.hwc.data.location.Country;
import com.iemr.hwc.data.location.CountryCityMaster;
import com.iemr.hwc.data.location.DistrictBlock;
import com.iemr.hwc.data.location.DistrictBranchMapping;
import com.iemr.hwc.data.location.Districts;
import com.iemr.hwc.data.location.LocationDetails;
import com.iemr.hwc.data.location.States;
import com.iemr.hwc.data.location.V_GetLocDetailsFromSPidAndPSMid;
import com.iemr.hwc.data.location.ZoneMaster;
import com.iemr.hwc.data.login.MasterServicePoint;
import com.iemr.hwc.data.login.ParkingPlace;
import com.iemr.hwc.data.login.ServicePointVillageMapping;
import com.iemr.hwc.repo.choApp.OutreachRepo;
import com.iemr.hwc.repo.location.CountryCityMasterRepo;
import com.iemr.hwc.repo.location.CountryMasterRepo;
import com.iemr.hwc.repo.location.DistrictBlockMasterRepo;
import com.iemr.hwc.repo.location.DistrictBranchMasterRepo;
import com.iemr.hwc.repo.location.DistrictMasterRepo;
import com.iemr.hwc.repo.location.LocationMasterRepo;
import com.iemr.hwc.repo.location.ParkingPlaceMasterRepo;
import com.iemr.hwc.repo.location.ServicePointMasterRepo;
import com.iemr.hwc.repo.location.StateMasterRepo;
import com.iemr.hwc.repo.location.V_GetLocDetailsFromSPidAndPSMidRepo;
import com.iemr.hwc.repo.location.ZoneMasterRepo;
import com.iemr.hwc.repo.login.ServicePointVillageMappingRepo;

import org.hibernate.mapping.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TestLocationServiceImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private CountryMasterRepo countryMasterRepo;
	@Mock
	private CountryCityMasterRepo countryCityMasterRepo;
	@Mock
	private StateMasterRepo stateMasterRepo;
	@Mock
	private ZoneMasterRepo zoneMasterRepo;
	@Mock
	private DistrictMasterRepo districtMasterRepo;
	@Mock
	private LocationMasterRepo locationMasterRepo;
	@Mock
	private DistrictBlockMasterRepo districtBlockMasterRepo;

	@Mock
	private DistrictBranchMasterRepo districtBranchMasterRepo;
	@Mock
	private ParkingPlaceMasterRepo parkingPlaceMasterRepo;
	@Mock
	private ServicePointMasterRepo servicePointMasterRepo;

	@Mock
	private V_GetLocDetailsFromSPidAndPSMidRepo v_GetLocDetailsFromSPidAndPSMidRepo;

	@Mock
	private ServicePointVillageMappingRepo servicePointVillageMappingRepo;
	@Mock
	private OutreachRepo outreachRepo;

	@InjectMocks
	private LocationServiceImpl locationServiceImpl;

	private ArrayList<CountryCityMaster> countryCityList;
	private LocationServiceImpl locationService;

	private ArrayList<Country> countryList;
	private ArrayList<Object[]> stateMasterList;
	private ArrayList<Object[]> zoneMasterList;

	private ArrayList<Object[]> districtMasterList;
	private Gson gson;
	private List<Object[]> sampleLocationMasterList;
	private ArrayList<Object[]> mockDistrictBlockMasterList;
	private List<Object[]> districtBlockMasterList;
	private List<Object[]> villageList;
	private ArrayList<Object[]> parkingPlaceMasterList;
	private ArrayList<Object[]> servicePointMasterList;
	private List<Object[]> objList;
	private List<Object[]> servicePointVillageList;

	private DistrictBranchMapping inactiveDistrictBranchMapping;
	private DistrictBranchMapping activeDistrictBranchMapping;
	private List<Outreach> outreachList;

	@BeforeEach
	public void setUp() {
		countryList = new ArrayList<>();
		countryList.add(new Country(1, "Country1"));
		countryList.add(new Country(2, "Country2"));
	}

	@Test
	public void testGetCountryList() {
		when(countryMasterRepo.findAllCountries()).thenReturn(countryList);

		String result = locationService.getCountryList();

		verify(countryMasterRepo, times(1)).findAllCountries();
		assertEquals(new Gson().toJson(countryList), result);
	}

	@Test
	public void testGetCountryCityList() {
		Integer countryID = 1;
		when(countryCityMasterRepo.findByCountryIDAndDeleted(countryID, false)).thenReturn(countryCityList);

		String expectedJson = new Gson().toJson(countryCityList);
		String actualJson = locationServiceImpl.getCountryCityList(countryID);

		assertEquals(expectedJson, actualJson);
		verify(countryCityMasterRepo, times(1)).findByCountryIDAndDeleted(countryID, false);
	}

	@Test
	public void testGetStateList() {
		String expectedJson = new Gson()
				.toJson(Arrays.asList(new States(1, "State1", 101), new States(2, "State2", 102)));

		String actualJson = locationService.getStateList();

		assertEquals(expectedJson, actualJson);
		verify(stateMasterRepo, times(1)).getStateMaster();
	}

	@Test
	public void testGetStateList_Empty() {
		when(stateMasterRepo.getStateMaster()).thenReturn(new ArrayList<>());

		String actualJson = locationService.getStateList();

		assertEquals("[]", actualJson);
		verify(stateMasterRepo, times(1)).getStateMaster();
	}

	@Test
	public void testGetStateList_Null() {
		when(stateMasterRepo.getStateMaster()).thenReturn(null);

		String actualJson = locationService.getStateList();

		assertEquals("[]", actualJson);
		verify(stateMasterRepo, times(1)).getStateMaster();
	}

	@Test
	public void testGetZoneList_ValidProviderServiceMapID() {
		when(zoneMasterRepo.getZoneMaster(1)).thenReturn(zoneMasterList);

		String response = locationService.getZoneList(1);

		ArrayList<ZoneMaster> expectedList = new ArrayList<>(
				Arrays.asList(new ZoneMaster(1, "Zone 1"), new ZoneMaster(2, "Zone 2")));

		assertEquals(new Gson().toJson(expectedList), response);
		verify(zoneMasterRepo, times(1)).getZoneMaster(1);
	}

	@Test
	public void testGetZoneList_EmptyResult() {
		when(zoneMasterRepo.getZoneMaster(1)).thenReturn(new ArrayList<>());

		String response = locationService.getZoneList(1);

		assertEquals(new Gson().toJson(new ArrayList<>()), response);
		verify(zoneMasterRepo, times(1)).getZoneMaster(1);
	}

	@Test
	public void testGetDistrictList_Success() {
		when(districtMasterRepo.getDistrictMaster(1)).thenReturn(districtMasterList);

		String response = locationServiceImpl.getDistrictList(1);

		ArrayList<Districts> expectedDistricts = new ArrayList<>(
				Arrays.asList(new Districts(1, "District1", 1, 1), new Districts(2, "District2", 1, 1)));

		assertEquals(gson.toJson(expectedDistricts), response);
		verify(districtMasterRepo, times(1)).getDistrictMaster(1);
	}

	@Test
	public void testGetDistrictList_Empty() {
		when(districtMasterRepo.getDistrictMaster(1)).thenReturn(new ArrayList<>());

		String response = locationServiceImpl.getDistrictList(1);

		assertEquals(gson.toJson(new ArrayList<>()), response);
		verify(districtMasterRepo, times(1)).getDistrictMaster(1);
	}

	@Test
	public void testGetLocationMasterData() {
		Integer villageID = 1;
		when(locationMasterRepo.getLocationMaster(villageID)).thenReturn(sampleLocationMasterList);

		String expectedJson = new Gson()
				.toJson(new LocationDetails(1, "Location1", 2, "City1", 3, "State1", 4, "Country1", 5, "Region1"));
		String actualJson = locationServiceImpl.getLocationMasterData(villageID);

		assertEquals(expectedJson, actualJson);
		verify(locationMasterRepo, times(1)).getLocationMaster(villageID);
	}

	@Test
	public void testGetDistrictBlockList() {
		when(districtBlockMasterRepo.getDistrictBlockMaster(1)).thenReturn(mockDistrictBlockMasterList);

		String response = locationServiceImpl.getDistrictBlockList(1);

		ArrayList<DistrictBlock> expectedDistrictBlockList = new ArrayList<>(
				Arrays.asList(new DistrictBlock(1, "Block1", 1, 1), new DistrictBlock(2, "Block2", 1, 1)));

		String expectedResponse = new Gson().toJson(expectedDistrictBlockList);

		assertEquals(expectedResponse, response);
		verify(districtBlockMasterRepo, times(1)).getDistrictBlockMaster(1);
	}

	@Test
	public void testGetVillageListByDistrictID_ValidDistrictID() {
		when(districtBlockMasterRepo.getDistrictBlockMaster(1))
				.thenReturn((ArrayList<Object[]>) districtBlockMasterList);
		when(districtBranchMasterRepo.findByBlockID(1)).thenReturn((ArrayList<Object[]>) villageList);
		when(districtBranchMasterRepo.findByBlockID(2)).thenReturn(new ArrayList<>());

		String result = locationServiceImpl.getVillageListByDistrictID(1);

		assertNotNull(result);
		assertTrue(result.contains("Village1"));
		assertTrue(result.contains("Village2"));
		verify(districtBlockMasterRepo, times(1)).getDistrictBlockMaster(1);
		verify(districtBranchMasterRepo, times(1)).findByBlockID(1);
		verify(districtBranchMasterRepo, times(1)).findByBlockID(2);
	}

	@Test
	public void testGetVillageListByDistrictID_NoBlocks() {
		when(districtBlockMasterRepo.getDistrictBlockMaster(2)).thenReturn(new ArrayList<>());

		String result = locationServiceImpl.getVillageListByDistrictID(2);

		assertNotNull(result);
		assertEquals("[]", result);
		verify(districtBlockMasterRepo, times(1)).getDistrictBlockMaster(2);
		verify(districtBranchMasterRepo, times(0)).findByBlockID(anyInt());
	}

	@Test
	public void testGetVillageListByDistrictID_BlocksNoVillages() {
		when(districtBlockMasterRepo.getDistrictBlockMaster(3))
				.thenReturn((ArrayList<Object[]>) districtBlockMasterList);
		when(districtBranchMasterRepo.findByBlockID(anyInt())).thenReturn(new ArrayList<>());

		String result = locationServiceImpl.getVillageListByDistrictID(3);

		assertNotNull(result);
		assertEquals("[]", result);
		verify(districtBlockMasterRepo, times(1)).getDistrictBlockMaster(3);
		verify(districtBranchMasterRepo, times(2)).findByBlockID(anyInt());
	}

	@Test
	public void testGetParkingPlaceList() {
		when(parkingPlaceMasterRepo.getParkingPlaceMaster(anyInt())).thenReturn(parkingPlaceMasterList);

		String response = locationServiceImpl.getParkingPlaceList(1);

		ArrayList<ParkingPlace> expectedList = new ArrayList<>(
				Arrays.asList(new ParkingPlace(1, "Parking Place 1"), new ParkingPlace(2, "Parking Place 2")));
		String expectedJson = new Gson().toJson(expectedList);

		assertEquals(expectedJson, response);
		verify(parkingPlaceMasterRepo, times(1)).getParkingPlaceMaster(1);
	}

	@Test
	public void testGetParkingPlaceList_Empty() {
		when(parkingPlaceMasterRepo.getParkingPlaceMaster(anyInt())).thenReturn(new ArrayList<>());

		String response = locationServiceImpl.getParkingPlaceList(1);

		String expectedJson = new Gson().toJson(new ArrayList<>());

		assertEquals(expectedJson, response);
		verify(parkingPlaceMasterRepo, times(1)).getParkingPlaceMaster(1);
	}

	@Test
	public void testGetServicePointPlaceList() {
		when(servicePointMasterRepo.getServicePointMaster(1)).thenReturn(servicePointMasterList);

		String response = locationServiceImpl.getServicePointPlaceList(1);

		ArrayList<MasterServicePoint> expectedList = new ArrayList<>();
		expectedList.add(new MasterServicePoint(1, "Service Point 1"));
		expectedList.add(new MasterServicePoint(2, "Service Point 2"));

		assertEquals(new Gson().toJson(expectedList), response);
		verify(servicePointMasterRepo, times(1)).getServicePointMaster(1);
	}

	@Test
	public void testGetServicePointPlaceList_Empty() {
		when(servicePointMasterRepo.getServicePointMaster(1)).thenReturn(new ArrayList<>());

		String response = locationServiceImpl.getServicePointPlaceList(1);

		assertEquals("[]", response);
		verify(servicePointMasterRepo, times(1)).getServicePointMaster(1);
	}

	@Test
	public void testGetVillageMasterFromBlockID() {
		Integer distBlockID = 1;

		when(districtBranchMasterRepo.findByBlockID(distBlockID)).thenReturn(mockVillageList);

		String expectedJson = "[{\"districtBranchID\":1,\"villageName\":\"Village1\"},{\"districtBranchID\":2,\"villageName\":\"Village2\"}]";
		String actualJson = locationServiceImpl.getVillageMasterFromBlockID(distBlockID);

		assertEquals(expectedJson, actualJson);
	}

	@Test
	public void testGetLocDetails() {
		when(stateMasterRepo.getStateMaster()).thenReturn(stateMasterList);
		when(v_GetLocDetailsFromSPidAndPSMidRepo
				.findByServicepointidAndSpproviderservicemapidAndPpproviderservicemapidAndZdmproviderservicemapid(
						anyInt(), anyInt(), anyInt(), anyInt()))
				.thenReturn(objList);
		when(servicePointVillageMappingRepo.getServicePointVillages(anyInt())).thenReturn(servicePointVillageList);

		String result = locationServiceImpl.getLocDetails(1, 1);

		Map<String, Object> expectedMap = new HashMap<>();
		V_GetLocDetailsFromSPidAndPSMid locOBJ = V_GetLocDetailsFromSPidAndPSMid.getOtherLocDetails(objList);
		List<States> stateList = new ArrayList<>();
		for (Object[] objArr : stateMasterList) {
			States states = new States((Integer) objArr[0], (String) objArr[1], (Integer) objArr[2]);
			stateList.add(states);
		}
		List<ServicePointVillageMapping> villageList = new ArrayList<>();
		for (Object[] obj : servicePointVillageList) {
			ServicePointVillageMapping villageMap = new ServicePointVillageMapping((Integer) obj[0], (String) obj[1]);
			villageList.add(villageMap);
		}
		expectedMap.put("otherLoc", locOBJ);
		expectedMap.put("stateMaster", stateList);
		expectedMap.put("villageMaster", villageList);

		String expectedJson = new Gson().toJson(expectedMap);

		assertEquals(expectedJson, result);

		verify(stateMasterRepo, times(1)).getStateMaster();
		verify(v_GetLocDetailsFromSPidAndPSMidRepo, times(1))
				.findByServicepointidAndSpproviderservicemapidAndPpproviderservicemapidAndZdmproviderservicemapid(
						anyInt(), anyInt(), anyInt(), anyInt());
		verify(servicePointVillageMappingRepo, times(1)).getServicePointVillages(anyInt());
	}

	@Test
	public void testGetDefaultLocDetails_EmptyList() {
		Map<String, Object> result = locationServiceImpl.getDefaultLocDetails(objList);
		assertTrue(result.isEmpty());
	}

	@Test
	public void testGetDefaultLocDetails_SingleDistrictSingleVillage() {
		Object[] data = { 1, 2, 3, "DistrictName", 4, "BlockName", "5", "VillageName" };
		objList.add(data);

		Map<String, Object> result = locationServiceImpl.getDefaultLocDetails(objList);

		assertEquals(1, result.get("stateID"));
		assertEquals(2, result.get("parkingPlaceID"));

		ArrayList<Map<String, Object>> districtList = (ArrayList<Map<String, Object>>) result.get("districtList");
		assertEquals(1, districtList.size());

		Map<String, Object> district = districtList.get(0);
		assertEquals(3, district.get("districtID"));
		assertEquals("DistrictName", district.get("districtName"));
		assertEquals(4, district.get("blockId"));
		assertEquals("BlockName", district.get("blockName"));

		ArrayList<Map<String, Object>> villageList = (ArrayList<Map<String, Object>>) district.get("villageList");
		assertEquals(1, villageList.size());

		Map<String, Object> village = villageList.get(0);
		assertEquals("5", village.get("districtBranchID"));
		assertEquals("VillageName", village.get("villageName"));
	}

	@Test
	public void testGetDefaultLocDetails_MultipleDistrictsAndVillages() {
		Object[] data1 = { 1, 2, 3, "DistrictName1", 4, "BlockName1", "5,6", "VillageName1,VillageName2" };
		Object[] data2 = { 1, 2, 7, "DistrictName2", 8, "BlockName2", "9", "VillageName3" };
		objList.add(data1);
		objList.add(data2);

		Map<String, Object> result = locationServiceImpl.getDefaultLocDetails(objList);

		assertEquals(1, result.get("stateID"));
		assertEquals(2, result.get("parkingPlaceID"));

		ArrayList<Map<String, Object>> districtList = (ArrayList<Map<String, Object>>) result.get("districtList");
		assertEquals(2, districtList.size());

		Map<String, Object> district1 = districtList.get(0);
		assertEquals(3, district1.get("districtID"));
		assertEquals("DistrictName1", district1.get("districtName"));
		assertEquals(4, district1.get("blockId"));
		assertEquals("BlockName1", district1.get("blockName"));

		ArrayList<Map<String, Object>> villageList1 = (ArrayList<Map<String, Object>>) district1.get("villageList");
		assertEquals(2, villageList1.size());

		Map<String, Object> village1 = villageList1.get(0);
		assertEquals("5", village1.get("districtBranchID"));
		assertEquals("VillageName1", village1.get("villageName"));

		Map<String, Object> village2 = villageList1.get(1);
		assertEquals("6", village2.get("districtBranchID"));
		assertEquals("VillageName2", village2.get("villageName"));

		Map<String, Object> district2 = districtList.get(1);
		assertEquals(7, district2.get("districtID"));
		assertEquals("DistrictName2", district2.get("districtName"));
		assertEquals(8, district2.get("blockId"));
		assertEquals("BlockName2", district2.get("blockName"));

		ArrayList<Map<String, Object>> villageList2 = (ArrayList<Map<String, Object>>) district2.get("villageList");
		assertEquals(1, villageList2.size());

		Map<String, Object> village3 = villageList2.get(0);
		assertEquals("9", village3.get("districtBranchID"));
		assertEquals("VillageName3", village3.get("villageName"));
	}

	@Test
	public void testUpdateGeolocationByDistrictBranchID_DistrictBranchMappingIsNull() {
		when(districtBranchMasterRepo.findAllByDistrictBranchID(anyInt())).thenReturn(null);

		int result = locationServiceImpl.updateGeolocationByDistrictBranchID(12.34, 56.78, 1, "Test Address");

		assertEquals(0, result);
		verify(districtBranchMasterRepo, times(1)).findAllByDistrictBranchID(anyInt());
		verify(districtBranchMasterRepo, never()).updateGeolocationByDistrictBranchID(anyDouble(), anyDouble(),
				anyBoolean(), anyString(), anyInt());
	}

	@Test
	public void testUpdateGeolocationByDistrictBranchID_DistrictBranchMappingIsInactive() {
		when(districtBranchMasterRepo.findAllByDistrictBranchID(anyInt())).thenReturn(inactiveDistrictBranchMapping);
		when(districtBranchMasterRepo.updateGeolocationByDistrictBranchID(anyDouble(), anyDouble(), anyBoolean(),
				anyString(), anyInt())).thenReturn(1);

		int result = locationServiceImpl.updateGeolocationByDistrictBranchID(12.34, 56.78, 1, "Test Address");

		assertEquals(1, result);
		verify(districtBranchMasterRepo, times(1)).findAllByDistrictBranchID(anyInt());
		verify(districtBranchMasterRepo, times(1)).updateGeolocationByDistrictBranchID(anyDouble(), anyDouble(),
				eq(true), anyString(), anyInt());
	}

	@Test
	public void testUpdateGeolocationByDistrictBranchID_DistrictBranchMappingIsActive() {
		when(districtBranchMasterRepo.findAllByDistrictBranchID(anyInt())).thenReturn(activeDistrictBranchMapping);

		int result = locationServiceImpl.updateGeolocationByDistrictBranchID(12.34, 56.78, 1, "Test Address");

		assertEquals(101, result);
		verify(districtBranchMasterRepo, times(1)).findAllByDistrictBranchID(anyInt());
		verify(districtBranchMasterRepo, never()).updateGeolocationByDistrictBranchID(anyDouble(), anyDouble(),
				anyBoolean(), anyString(), anyInt());
	}

	@Test
	public void testGetOutreachProgramsList() {
		when(outreachRepo.getOutreachListByStateID(1)).thenReturn(outreachList);

		String expectedJson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create()
				.toJson(outreachList);
		String actualJson = locationService.getOutreachProgramsList(1);

		assertEquals(expectedJson, actualJson);
		verify(outreachRepo, times(1)).getOutreachListByStateID(1);
	}

	@Test
	public void testGetOutreachProgramsList_Empty() {
		when(outreachRepo.getOutreachListByStateID(1)).thenReturn(Arrays.asList());

		String expectedJson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create()
				.toJson(Arrays.asList());
		String actualJson = locationService.getOutreachProgramsList(1);

		assertEquals(expectedJson, actualJson);
		verify(outreachRepo, times(1)).getOutreachListByStateID(1);
	}
}
