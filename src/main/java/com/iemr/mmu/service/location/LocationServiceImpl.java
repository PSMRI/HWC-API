/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.service.location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.iemr.mmu.data.location.Country;
import com.iemr.mmu.data.location.CountryCityMaster;
import com.iemr.mmu.data.location.DistrictBlock;
import com.iemr.mmu.data.location.DistrictBranchMapping;
import com.iemr.mmu.data.location.Districts;
import com.iemr.mmu.data.location.LocationDetails;
import com.iemr.mmu.data.location.States;
import com.iemr.mmu.data.location.V_GetLocDetailsFromSPidAndPSMid;
import com.iemr.mmu.data.location.ZoneMaster;
import com.iemr.mmu.data.login.MasterServicePoint;
import com.iemr.mmu.data.login.ParkingPlace;
import com.iemr.mmu.data.login.ServicePointVillageMapping;
import com.iemr.mmu.repo.location.CountryCityMasterRepo;
import com.iemr.mmu.repo.location.CountryMasterRepo;
import com.iemr.mmu.repo.location.DistrictBlockMasterRepo;
import com.iemr.mmu.repo.location.DistrictBranchMasterRepo;
import com.iemr.mmu.repo.location.DistrictMasterRepo;
import com.iemr.mmu.repo.location.LocationMasterRepo;
import com.iemr.mmu.repo.location.ParkingPlaceMasterRepo;
import com.iemr.mmu.repo.location.ServicePointMasterRepo;
import com.iemr.mmu.repo.location.StateMasterRepo;
import com.iemr.mmu.repo.location.V_GetLocDetailsFromSPidAndPSMidRepo;
import com.iemr.mmu.repo.location.V_getVanLocDetailsRepo;
import com.iemr.mmu.repo.location.V_get_prkngplc_dist_zone_state_from_spidRepo;
import com.iemr.mmu.repo.location.ZoneMasterRepo;
import com.iemr.mmu.repo.login.ServicePointVillageMappingRepo;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private CountryMasterRepo countryMasterRepo;
	@Autowired
	private CountryCityMasterRepo countryCityMasterRepo;
	
	@Autowired
	private LocationMasterRepo locationMasterRepo;

	private StateMasterRepo stateMasterRepo;
	private ZoneMasterRepo zoneMasterRepo;
	private DistrictMasterRepo districtMasterRepo;
	private DistrictBlockMasterRepo districtBlockMasterRepo;
	private ParkingPlaceMasterRepo parkingPlaceMasterRepo;
	private ServicePointMasterRepo servicePointMasterRepo;
	private V_GetLocDetailsFromSPidAndPSMidRepo v_GetLocDetailsFromSPidAndPSMidRepo;
	private ServicePointVillageMappingRepo servicePointVillageMappingRepo;
	private DistrictBranchMasterRepo districtBranchMasterRepo;
	private V_get_prkngplc_dist_zone_state_from_spidRepo v_get_prkngplc_dist_zone_state_from_spidRepo;
	@Autowired
	private V_getVanLocDetailsRepo v_getVanLocDetailsRepo;

	@Autowired
	public void setV_get_prkngplc_dist_zone_state_from_spidRepo(
			V_get_prkngplc_dist_zone_state_from_spidRepo v_get_prkngplc_dist_zone_state_from_spidRepo) {
		this.v_get_prkngplc_dist_zone_state_from_spidRepo = v_get_prkngplc_dist_zone_state_from_spidRepo;
	}

	@Autowired
	public void setDistrictBranchMasterRepo(DistrictBranchMasterRepo districtBranchMasterRepo) {
		this.districtBranchMasterRepo = districtBranchMasterRepo;
	}

	@Autowired
	public void setServicePointVillageMappingRepo(ServicePointVillageMappingRepo servicePointVillageMappingRepo) {
		this.servicePointVillageMappingRepo = servicePointVillageMappingRepo;
	}

	@Autowired
	public void setV_GetLocDetailsFromSPidAndPSMidRepo(
			V_GetLocDetailsFromSPidAndPSMidRepo v_GetLocDetailsFromSPidAndPSMidRepo) {
		this.v_GetLocDetailsFromSPidAndPSMidRepo = v_GetLocDetailsFromSPidAndPSMidRepo;
	}

	@Autowired
	public void setServicePointMasterRepo(ServicePointMasterRepo servicePointMasterRepo) {
		this.servicePointMasterRepo = servicePointMasterRepo;
	}

	@Autowired
	public void setParkingPlaceMasterRepo(ParkingPlaceMasterRepo parkingPlaceMasterRepo) {
		this.parkingPlaceMasterRepo = parkingPlaceMasterRepo;
	}

	@Autowired
	public void setDistrictBlockMasterRepo(DistrictBlockMasterRepo districtBlockMasterRepo) {
		this.districtBlockMasterRepo = districtBlockMasterRepo;
	}

	@Autowired
	public void setDistrictMasterRepo(DistrictMasterRepo districtMasterRepo) {
		this.districtMasterRepo = districtMasterRepo;
	}

	@Autowired
	public void setZoneMasterRepo(ZoneMasterRepo zoneMasterRepo) {
		this.zoneMasterRepo = zoneMasterRepo;
	}

	@Autowired
	public void setStateMasterRepo(StateMasterRepo stateMasterRepo) {
		this.stateMasterRepo = stateMasterRepo;
	}
	

	@Override
	public String getCountryList() {

		ArrayList<Country> stateMasterList = countryMasterRepo.findAllCountries();

		return new Gson().toJson(stateMasterList);
	}

	@Override
	public String getCountryCityList(Integer countryID) {

		ArrayList<CountryCityMaster> countryCityList = countryCityMasterRepo.findByCountryIDAndDeleted(countryID,
				false);

		return new Gson().toJson(countryCityList);
	}

	@Override
	public String getStateList() {
		ArrayList<States> stateList = new ArrayList<>();
		ArrayList<Object[]> stateMasterList = stateMasterRepo.getStateMaster();
		if (stateMasterList != null && stateMasterList.size() > 0) {
			for (Object[] objArr : stateMasterList) {
				States states = new States((Integer) objArr[0], (String) objArr[1]);
				stateList.add(states);
			}
		}
		return new Gson().toJson(stateList);
	}

	@Override
	public String getZoneList(Integer providerServiceMapID) {
		ArrayList<Object> zoneList = new ArrayList<>();
		ArrayList<Object[]> zoneMasterList = zoneMasterRepo.getZoneMaster(providerServiceMapID);
		if (zoneMasterList != null && zoneMasterList.size() > 0) {
			for (Object[] objArr : zoneMasterList) {
				ZoneMaster zoneMaster = new ZoneMaster((Integer) objArr[0], (String) objArr[1]);
				zoneList.add(zoneMaster);
			}
		}
		return new Gson().toJson(zoneList);
	}

	@Override
	public String getDistrictList(Integer stateID) {
		ArrayList<Object> districtList = new ArrayList<>();
		ArrayList<Object[]> districtMasterList = districtMasterRepo.getDistrictMaster(stateID);
		if (districtMasterList != null && districtMasterList.size() > 0) {
			for (Object[] objArr : districtMasterList) {
				Districts districtMaster = new Districts((Integer) objArr[0], (String) objArr[1]);
				districtList.add(districtMaster);
			}
		}
		return new Gson().toJson(districtList);
	}
	
	@Override
	public String getLocationMasterData(Integer villageID) {
		LocationDetails locDetail = new LocationDetails();
		List<Objects[]> locationMasterList = locationMasterRepo.getLocationMaster(villageID);
		if (locationMasterList != null && locationMasterList.size() > 0) {
			Object[] objArr = locationMasterList.get(0);
			locDetail = new LocationDetails((Integer) objArr[0], (String) objArr[1], (Integer) objArr[2], (String) objArr[3], (Integer) objArr[4], (String) objArr[5], (Integer) objArr[6], (String) objArr[7], (Integer) objArr[8], (String) objArr[9]);
			
		}
		return new Gson().toJson(locDetail);
		
	}


	@Override
	public String getDistrictBlockList(Integer districtID) {
		ArrayList<Object> districtBlockList = new ArrayList<>();
		ArrayList<Object[]> districtBlockMasterList = districtBlockMasterRepo.getDistrictBlockMaster(districtID);
		if (districtBlockMasterList != null && districtBlockMasterList.size() > 0) {
			for (Object[] objArr : districtBlockMasterList) {
				DistrictBlock districtBLockMaster = new DistrictBlock((Integer) objArr[0], (String) objArr[1]);
				districtBlockList.add(districtBLockMaster);
			}
		}
		return new Gson().toJson(districtBlockList);
	}

	@Override
	public String getParkingPlaceList(Integer providerServiceMapID) {
		ArrayList<Object> parkingPlaceList = new ArrayList<>();
		ArrayList<Object[]> parkingPlaceMasterList = parkingPlaceMasterRepo.getParkingPlaceMaster(providerServiceMapID);
		if (parkingPlaceMasterList != null && parkingPlaceMasterList.size() > 0) {
			for (Object[] objArr : parkingPlaceMasterList) {
				ParkingPlace parkingPlace = new ParkingPlace((Integer) objArr[0], (String) objArr[1]);
				parkingPlaceList.add(parkingPlace);
			}
		}
		return new Gson().toJson(parkingPlaceList);
	}

	public String getServicePointPlaceList(Integer parkingPlaceID) {
		ArrayList<Object> servicePointList = new ArrayList<>();
		ArrayList<Object[]> servicePointMasterList = servicePointMasterRepo.getServicePointMaster(parkingPlaceID);
		if (servicePointMasterList != null && servicePointMasterList.size() > 0) {
			for (Object[] objArr : servicePointMasterList) {
				MasterServicePoint masterServicePoint = new MasterServicePoint((Integer) objArr[0], (String) objArr[1]);
				servicePointList.add(masterServicePoint);
			}
		}
		return new Gson().toJson(servicePointList);
	}

	public String getVillageMasterFromBlockID(Integer distBlockID) {
		ArrayList<Object[]> resList = districtBranchMasterRepo.findByBlockID(distBlockID);
		return DistrictBranchMapping.getVillageList(resList);
	}

	// old, 11-10-2018
	@Deprecated
	public String getLocDetails(Integer spID, Integer spPSMID) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		// other location details
		ArrayList<Object[]> objList = v_GetLocDetailsFromSPidAndPSMidRepo
				.findByServicepointidAndSpproviderservicemapidAndPpproviderservicemapidAndZdmproviderservicemapid(spID,
						spPSMID, spPSMID, spPSMID);

		V_GetLocDetailsFromSPidAndPSMid locOBJ = V_GetLocDetailsFromSPidAndPSMid.getOtherLocDetails(objList);

		// state master
		ArrayList<States> stateList = new ArrayList<>();
		ArrayList<Object[]> stateMasterList = stateMasterRepo.getStateMaster();
		if (stateMasterList != null && stateMasterList.size() > 0) {
			for (Object[] objArr : stateMasterList) {
				States states = new States((Integer) objArr[0], (String) objArr[1]);
				stateList.add(states);
			}
		}
		// village masters from service point
		List<Object[]> servicePointVillageList = servicePointVillageMappingRepo.getServicePointVillages(spID);

		ArrayList<ServicePointVillageMapping> villageList = new ArrayList<ServicePointVillageMapping>();
		if (servicePointVillageList.size() > 0) {
			ServicePointVillageMapping VillageMap;
			for (Object[] obj : servicePointVillageList) {
				VillageMap = new ServicePointVillageMapping((Integer) obj[0], (String) obj[1]);
				villageList.add(VillageMap);
			}
		}

		resMap.put("otherLoc", locOBJ);
		resMap.put("stateMaster", stateList);
		resMap.put("villageMaster", villageList);

		return new Gson().toJson(resMap);
	}

	// new, 11-10-2018
	public String getLocDetailsNew(Integer vanID, Integer spPSMID) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		// other location details
		// ArrayList<Object[]> objList =
		// v_get_prkngplc_dist_zone_state_from_spidRepo.getDefaultLocDetails(spID,
		// spPSMID);

		// other location details, changed for TM
		ArrayList<Object[]> resultSet = v_getVanLocDetailsRepo.getVanDetails(vanID);

		// state master
		ArrayList<States> stateList = new ArrayList<>();
		ArrayList<Object[]> stateMasterList = stateMasterRepo.getStateMaster();
		if (stateMasterList != null && stateMasterList.size() > 0) {
			for (Object[] objArr : stateMasterList) {
				States states = new States((Integer) objArr[0], (String) objArr[1]);
				stateList.add(states);
			}
		}

		resMap.put("otherLoc", getDefaultLocDetails(resultSet));
		resMap.put("stateMaster", stateList);

		return new Gson().toJson(resMap);
	}

	private Map<String, Object> getDefaultLocDetails(ArrayList<Object[]> objList) {
		Map<String, Object> returnObj = new HashMap<>();
		Map<String, Object> distMap = new HashMap<>();
		ArrayList<Map<String, Object>> distLit = new ArrayList<>();
		if (objList != null && objList.size() > 0) {
			returnObj.put("stateID", objList.get(0)[0]);
			// returnObj.put("stateName", objList.get(0)[7]);
			// returnObj.put("zoneID", objList.get(0)[4]);
			// returnObj.put("zoneName", objList.get(0)[5]);
			returnObj.put("parkingPlaceID", objList.get(0)[1]);
			// returnObj.put("parkingPlaceName", objList.get(0)[1]);
			for (Object[] objArr : objList) {
				distMap = new HashMap<>();
				distMap.put("districtID", objArr[2]);
				distMap.put("districtName", objArr[3]);

				distLit.add(distMap);
			}

			returnObj.put("districtList", distLit);
		}
		return returnObj;

	}
	// private Map<String, Object> getDefaultLocDetails(ArrayList<Object[]> objList)
	// {
	// Map<String, Object> returnObj = new HashMap<>();
	// Map<String, Object> distMap = new HashMap<>();
	// ArrayList<Map<String, Object>> distLit = new ArrayList<>();
	// if (objList != null && objList.size() > 0) {
	// returnObj.put("stateID", objList.get(0)[6]);
	// returnObj.put("stateName", objList.get(0)[7]);
	// returnObj.put("zoneID", objList.get(0)[4]);
	// returnObj.put("zoneName", objList.get(0)[5]);
	// returnObj.put("parkingPlaceID", objList.get(0)[0]);
	// returnObj.put("parkingPlaceName", objList.get(0)[1]);
	// for (Object[] objArr : objList) {
	// distMap = new HashMap<>();
	// distMap.put("districtID", objArr[2]);
	// distMap.put("districtName", objArr[3]);
	//
	// distLit.add(distMap);
	// }
	//
	// returnObj.put("districtList", distLit);
	// }
	// return returnObj;
	//
	// }
}
