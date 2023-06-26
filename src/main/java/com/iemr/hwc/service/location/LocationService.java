/*
* AMRIT – Accessible Medical Records via Integrated Technology 
* Integrated EHR (Electronic Health Records) Solution 
*
* Copyright (C) "Piramal Swasthya Management and Research Institute" 
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.iemr.hwc.service.location;

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
