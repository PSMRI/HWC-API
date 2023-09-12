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
package com.iemr.hwc.data.location;

import lombok.Data;

@Data
public class LocationDetails {

	private Integer villageID;

	private String villageName;
	
	private Integer blockID;

	private String blockName;

	private Integer districtID;

	private String districtName;

	private Integer stateID;

	private String stateName;
	
	private Integer countryID;

	private String countryName;
	
	public LocationDetails( Integer villageID,String villageName,Integer blockID, String blockName, Integer districtID, String districtName, Integer stateID,  String stateName, Integer countryID, String countryName) {
		super();
		this.villageID = villageID;
		this.villageName = villageName;
		this.blockID = blockID;
		this.blockName = blockName;
		this.districtID = districtID;
		this.districtName = districtName;
		this.stateID = stateID;
		this.stateName = stateName;
		this.countryID = countryID;
		this.countryName = countryName;
		
	}

	public LocationDetails() {
		// TODO Auto-generated constructor stub
	}

}
