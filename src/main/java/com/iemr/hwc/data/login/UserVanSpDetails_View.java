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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "v_userprkngplacvanspstatedisblok")
public class UserVanSpDetails_View {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long ID;

	@Expose
	@Column(name = "userid")
	private Integer userID;

	@Expose
	@Column(name = "VanID")
	private Integer vanID;

	@Expose
	@Column(name = "vanNoAndType")
	private String vanNoAndType;

	@Expose
	@Column(name = "VanSession")
	private Short vanSession;

	@Expose
	@Column(name = "ServicePointID")
	private Integer servicePointID;

	@Expose
	@Column(name = "ServicePointName")
	private String servicePointName;

	@Expose
	@Column(name = "parkingplaceid")
	private Integer parkingPlaceID;

	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;
	
	@Expose
	@Column(name = "IsFacility")
	private Boolean isFacility;
	
	@Expose
	@Column(name = "FacilityID")
	private Integer facilityID;
	
	

	public UserVanSpDetails_View() {
	}

	public UserVanSpDetails_View(Integer userID, Integer vanID, String vanNoAndType, Short vanSession,
			Integer servicePointID, String servicePointName, Integer parkingPlaceID, Integer facilityID, int i) {
		super();
		this.userID = userID;
		this.vanID = vanID;
		this.vanNoAndType = vanNoAndType;
		this.vanSession = vanSession;
		this.servicePointID = servicePointID;
		this.servicePointName = servicePointName;
		this.parkingPlaceID = parkingPlaceID;
		this.facilityID = facilityID;
	}

	public UserVanSpDetails_View(Integer userID, Integer vanID, String vanNoAndType, Short vanSession,
			Integer servicePointID, String servicePointName, Integer parkingPlaceID, Integer providerServiceMapID) {
		super();
		this.userID = userID;
		this.vanID = vanID;
		this.vanNoAndType = vanNoAndType;
		this.vanSession = vanSession;
		this.servicePointID = servicePointID;
		this.servicePointName = servicePointName;
		this.parkingPlaceID = parkingPlaceID;
		this.providerServiceMapID = providerServiceMapID;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public String getVanNoAndType() {
		return vanNoAndType;
	}

	public void setVanNoAndType(String vanNoAndType) {
		this.vanNoAndType = vanNoAndType;
	}

	public Short getVanSession() {
		return vanSession;
	}

	public void setVanSession(Short vanSession) {
		this.vanSession = vanSession;
	}

	public Integer getServicePointID() {
		return servicePointID;
	}

	public void setServicePointID(Integer servicePointID) {
		this.servicePointID = servicePointID;
	}

	public String getServicePointName() {
		return servicePointName;
	}

	public void setServicePointName(String servicePointName) {
		this.servicePointName = servicePointName;
	}

	public Integer getParkingPlaceID() {
		return parkingPlaceID;
	}

	public void setParkingPlaceID(Integer parkingPlaceID) {
		this.parkingPlaceID = parkingPlaceID;
	}

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public Boolean getIsFacility() {
		return isFacility;
	}

	public void setIsFacility(Boolean isFacility) {
		this.isFacility = isFacility;
	}

	public Integer getFacilityID() {
		return facilityID;
	}

	public void setFacilityID(Integer facilityID) {
		this.facilityID = facilityID;
	}

	
}
