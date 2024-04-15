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
package com.iemr.hwc.data.login;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_userparkingplacemap")
public class UserParkingplaceMapping {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	@Column(name = "UserParkingPlaceMapID")
	private Integer userParkingPlaceMapID;
	@Expose
	@Column(name = "UserID")
	private Integer userID;
	@Expose
	@Column(name = "ParkingPlaceID")
	private Integer parkingPlaceID;
	@Expose
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false, insertable = false, name = "parkingPlaceID")
	private ParkingPlace m_parkingplace;

	@Expose
	@Column(name = "StateID")
	private Integer stateID;
	@Expose
	@Column(name = "DistrictID")
	private Integer districtID;
	@Expose
	@Column(name = "ProviderServiceMapId")
	private Integer providerServiceMapId;
	@Expose
	@Column(name = "Deleted")
	private Integer deleted;

//	@OneToMany(mappedBy = "userParkingplaceMapping", cascade = CascadeType.ALL)
//	private Set<MasterVan> masterVanSet;

	// 23-01-2019, report download issue, since mapping is not available in mastervan.
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "parkingPlaceID", insertable = false, updatable = false)
//	private MasterVan masterVanSet;

	public UserParkingplaceMapping() {
	}

//	public UserParkingplaceMapping(Integer userParkingPlaceMapID, Integer userID, Integer parkingPlaceID,
//			Integer stateID, Integer districtID, Integer providerServiceMapId, Integer deleted,
//			Set<MasterVan> masterVanSet) {
//		super();
//		this.userParkingPlaceMapID = userParkingPlaceMapID;
//		this.userID = userID;
//		this.parkingPlaceID = parkingPlaceID;
//		this.stateID = stateID;
//		this.districtID = districtID;
//		this.providerServiceMapId = providerServiceMapId;
//		this.deleted = deleted;
//		this.masterVanSet = masterVanSet;
//	}

	public Integer getUserParkingPlaceMapID() {
		return userParkingPlaceMapID;
	}

	public void setUserParkingPlaceMapID(Integer userParkingPlaceMapID) {
		this.userParkingPlaceMapID = userParkingPlaceMapID;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getParkingPlaceID() {
		return parkingPlaceID;
	}

	public void setParkingPlaceID(Integer parkingPlaceID) {
		this.parkingPlaceID = parkingPlaceID;
	}

	public Integer getStateID() {
		return stateID;
	}

	public void setStateID(Integer stateID) {
		this.stateID = stateID;
	}

	public Integer getDistrictID() {
		return districtID;
	}

	public void setDistrictID(Integer districtID) {
		this.districtID = districtID;
	}

	public Integer getProviderServiceMapId() {
		return providerServiceMapId;
	}

	public void setProviderServiceMapId(Integer providerServiceMapId) {
		this.providerServiceMapId = providerServiceMapId;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

//	public Set<MasterVan> getMasterVanSet() {
//		return masterVanSet;
//	}
//
//	public void setMasterVanSet(Set<MasterVan> masterVanSet) {
//		this.masterVanSet = masterVanSet;
//	}

}
