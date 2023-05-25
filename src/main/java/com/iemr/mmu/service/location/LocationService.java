package com.iemr.mmu.service.location;

public interface LocationService {

	public String getCountryList();

	public String getCountryCityList(Integer countryID);

	public String getStateList();

	public String getZoneList(Integer stateID);

	public String getDistrictList(Integer stateID);

	public String getDistrictBlockList(Integer districtID);

	public String getParkingPlaceList(Integer districtBlockID);

	public String getServicePointPlaceList(Integer parkingPlaceID);

	public String getLocationMasterData(Integer villageid);
}
