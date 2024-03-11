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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "v_get_prkngplc_dist_zone_state_from_spid")
public class V_get_prkngplc_dist_zone_state_from_spid {
	@Id
	@GeneratedValue
	private Long ID;

	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;
	@Expose
	@Column(name = "ServicePointID")
	private Integer servicePointID;
	@Expose
	@Column(name = "ParkingPlaceID")
	private Integer parkingPlaceID;
	@Expose
	@Column(name = "ParkingPlaceName")
	private String parkingPlaceName;
	@Expose
	@Column(name = "DistrictID")
	private Integer districtID;
	@Expose
	@Column(name = "DistrictName")
	private String districtName;
	@Expose
	@Column(name = "ZoneID")
	private Integer zoneID;
	@Expose
	@Column(name = "ZoneName")
	private String zoneName;
	@Expose
	@Column(name = "StateID")
	private Integer stateID;
	@Expose
	@Column(name = "StateName")
	private String stateName;

	public V_get_prkngplc_dist_zone_state_from_spid() {
	}

	public V_get_prkngplc_dist_zone_state_from_spid(Integer parkingPlaceID, String parkingPlaceName, Integer districtID,
			String districtName, Integer zoneID, String zoneName, Integer stateID, String stateName) {
		super();

		this.parkingPlaceID = parkingPlaceID;
		this.parkingPlaceName = parkingPlaceName;
		this.districtID = districtID;
		this.districtName = districtName;
		this.zoneID = zoneID;
		this.zoneName = zoneName;
		this.stateID = stateID;
		this.stateName = stateName;
	}

}
