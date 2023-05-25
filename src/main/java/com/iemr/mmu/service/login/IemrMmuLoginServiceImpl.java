package com.iemr.mmu.service.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.iemr.mmu.data.login.MasterVan;
import com.iemr.mmu.data.login.ServicePointVillageMapping;
import com.iemr.mmu.data.login.UserVanSpDetails_View;
import com.iemr.mmu.repo.login.MasterVanRepo;
import com.iemr.mmu.repo.login.ServicePointVillageMappingRepo;
import com.iemr.mmu.repo.login.UserParkingplaceMappingRepo;
import com.iemr.mmu.repo.login.UserVanSpDetails_View_Repo;
import com.iemr.mmu.repo.login.VanServicepointMappingRepo;
import com.iemr.mmu.utils.response.OutputResponse;

@Service
public class IemrMmuLoginServiceImpl implements IemrMmuLoginService {

	private UserParkingplaceMappingRepo userParkingplaceMappingRepo;
	private MasterVanRepo masterVanRepo;
	private VanServicepointMappingRepo vanServicepointMappingRepo;
	private ServicePointVillageMappingRepo servicePointVillageMappingRepo;
	private UserVanSpDetails_View_Repo userVanSpDetails_View_Repo;

	@Autowired
	public void setUserVanSpDetails_View_Repo(UserVanSpDetails_View_Repo userVanSpDetails_View_Repo) {
		this.userVanSpDetails_View_Repo = userVanSpDetails_View_Repo;
	}

	@Autowired
	public void setUserParkingplaceMappingRepo(UserParkingplaceMappingRepo userParkingplaceMappingRepo) {
		this.userParkingplaceMappingRepo = userParkingplaceMappingRepo;
	}

	@Autowired
	public void setMasterVanRepo(MasterVanRepo masterVanRepo) {
		this.masterVanRepo = masterVanRepo;
	}

	@Autowired
	public void setVanServicepointMappingRepo(VanServicepointMappingRepo vanServicepointMappingRepo) {
		this.vanServicepointMappingRepo = vanServicepointMappingRepo;
	}

	@Autowired
	public void setServicePointVillageMappingRepo(ServicePointVillageMappingRepo servicePointVillageMappingRepo) {
		this.servicePointVillageMappingRepo = servicePointVillageMappingRepo;
	}

	@Override
	public String getUserServicePointVanDetails(Integer userID) {
		// System.out.println("hello");
		Map<String, ArrayList<Map<String, Object>>> responseMap = new HashMap<>();
		List<Object[]> parkingPlaceList = userParkingplaceMappingRepo.getUserParkingPlce(userID);
		Set<Integer> ppS = new HashSet<>();
		ArrayList<Map<String, Object>> parkingPlaceLocationList = new ArrayList<>();
		if (parkingPlaceList.size() > 0) {
			Map<String, Object> parkingPlaceLocationMap;
			for (Object[] obj : parkingPlaceList) {
				ppS.add((Integer) obj[0]);
				parkingPlaceLocationMap = new HashMap<String, Object>();
				parkingPlaceLocationMap.put("stateID", obj[1]);
				parkingPlaceLocationMap.put("stateName", obj[2]);
				parkingPlaceLocationMap.put("districtID", obj[3]);
				parkingPlaceLocationMap.put("districtName", obj[4]);
				parkingPlaceLocationMap.put("blockID", obj[5]);
				parkingPlaceLocationMap.put("blockName", obj[6]);
				parkingPlaceLocationList.add(parkingPlaceLocationMap);
			}
			// System.out.println("hello");
			List<Object[]> vanList = masterVanRepo.getUserVanDatails(ppS);
			Map<String, Object> vMap;
			ArrayList<Map<String, Object>> vanListResponse = new ArrayList<>();
			if (vanList.size() > 0) {
				for (Object[] obj : vanList) {
					vMap = new HashMap<String, Object>();
					vMap.put("vanID", obj[0]);
					vMap.put("vanNO", obj[1]);
					vanListResponse.add(vMap);
				}
			} else {
				vMap = new HashMap<String, Object>();
				vanListResponse.add(vMap);
			}
			// System.out.println("hello");

			List<Object[]> servicePointList = vanServicepointMappingRepo.getuserSpSessionDetails(ppS);
			Map<String, Object> spMap;
			ArrayList<Map<String, Object>> servicePointListResponse = new ArrayList<>();
			if (servicePointList.size() > 0) {
				for (Object[] obj : servicePointList) {
					spMap = new HashMap<String, Object>();
					spMap.put("servicePointID", obj[0]);
					spMap.put("servicePointName", obj[1]);
					spMap.put("sessionType", obj[2]);
					servicePointListResponse.add(spMap);
				}
			} else {
				spMap = new HashMap<String, Object>();
				servicePointListResponse.add(spMap);
			}

			responseMap.put("userVanDetails", vanListResponse);
			responseMap.put("userSpDetails", servicePointListResponse);
			responseMap.put("parkingPlaceLocationList", parkingPlaceLocationList);

			// System.out.println("hello");

		}
		return new Gson().toJson(responseMap);
	}

	@Override
	public String getServicepointVillages(Integer servicePointID) {
		List<Object[]> servicePointVillageList = servicePointVillageMappingRepo.getServicePointVillages(servicePointID);

		ArrayList<ServicePointVillageMapping> villageList = new ArrayList<ServicePointVillageMapping>();
		if (servicePointVillageList.size() > 0) {
			ServicePointVillageMapping VillageMap;
			for (Object[] obj : servicePointVillageList) {
				VillageMap = new ServicePointVillageMapping((Integer) obj[0], (String) obj[1]);
				villageList.add(VillageMap);
			}
		}

		return new Gson().toJson(villageList);
	}

	@Override
	public String getUserVanSpDetails(Integer userID, Integer providerServiceMapID) {
		Map<String, Object> resMap = new HashMap<>();
		ArrayList<Object[]> objList = userVanSpDetails_View_Repo.getUserVanSpDetails_View(userID, providerServiceMapID);
		ArrayList<UserVanSpDetails_View> userVanSpDetails_ViewList = new ArrayList<>();
		if (objList.size() > 0) {
			for (Object[] objArray : objList) {
				UserVanSpDetails_View userVanSpDetails_ViewOBJ = new UserVanSpDetails_View((Integer) objArray[0],
						(Integer) objArray[1], (String) objArray[2], (Short) objArray[3], (Integer) objArray[4],
						(String) objArray[5], (Integer) objArray[6], (Integer) objArray[7], 0);
				userVanSpDetails_ViewList.add(userVanSpDetails_ViewOBJ);
			}
		}
		resMap.put("UserVanSpDetails", userVanSpDetails_ViewList);
		// System.out.println("helloo bhai---" + new Gson().toJson(resMap));
		// Later will remove below part till 1.1 new api is getting called on
		// continue button
		List<Object[]> parkingPlaceList = userParkingplaceMappingRepo.getUserParkingPlce(userID);
		Map<String, Object> parkingPlaceLocationMap = new HashMap<>();
		if (parkingPlaceList.size() > 0) {
			Object[] obj1 = parkingPlaceList.get(0);
			parkingPlaceLocationMap.put("parkingPlaceID", obj1[0]);
			parkingPlaceLocationMap.put("stateID", obj1[1]);
			parkingPlaceLocationMap.put("stateName", obj1[2]);
			parkingPlaceLocationMap.put("districtID", obj1[3]);
			parkingPlaceLocationMap.put("districtName", obj1[4]);
			parkingPlaceLocationMap.put("blockID", obj1[5]);
			parkingPlaceLocationMap.put("blockName", obj1[6]);
		}
		resMap.put("UserLocDetails", parkingPlaceLocationMap);
		// 1.1
		return new Gson().toJson(resMap);
	}
	
	/* created by = DU20091017 */
	
	@Override
	public String getUserSpokeDetails (Integer psmId)  {
		MasterVan mVan;
		ArrayList<MasterVan> vanMasterList = new ArrayList<>();
		mVan = new MasterVan(0, "All");
		vanMasterList.add(mVan);
		ArrayList<Object[]> vanMaster = masterVanRepo.getVanMaster(psmId);

		if (vanMaster != null && vanMaster.size() > 0) {
			for (Object[] arr : vanMaster) {
				mVan = new MasterVan((Integer) arr[0], (String) arr[1]);
				vanMasterList.add(mVan);
			}
		}
		return new Gson().toJson(vanMasterList);
	}
}
