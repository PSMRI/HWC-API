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

import java.sql.Timestamp;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import com.iemr.hwc.annotation.sqlInjectionSafe.SQLInjectionSafe;
import com.iemr.hwc.repo.location.ZoneDistrictMapping;

import lombok.Data;

@Data
@Entity
@Table(name = "m_district")
public class Districts {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DistrictID")
	@Expose
	private Integer districtID;
	@Column(name = "StateID")
	
	@Expose
	private Integer stateID;
	
	
	@Column(name = "GovtStateID")
	@Expose
	private Integer govtLGDStateID;
	
	@Column(name = "GovtDistrictID")
	@Expose
	private Integer govtLGDDistrictID;


	@Column(name = "DistrictName")
	@Expose
	private String districtName;
	// @Column(name = "Zone")
	// @Expose
	// private String zone;
	@Column(name = "Deleted", insertable = false, updatable = true)
	@Expose
	private Boolean deleted;
	@Column(name = "CreatedBy")
	@Expose
	private String createdBy;
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(unique = true, insertable = false, name = "StateID", updatable = false)
	@JsonIgnore
	private States states;

	@Expose
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DistrictID", insertable = false, updatable = false)
	private ZoneDistrictMapping zoneDistrictMapping;

	public Districts() {
	}

	public Integer getStateID() {
		return stateID;
	}

	public void setStateID(Integer stateID) {
		this.stateID = stateID;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public ZoneDistrictMapping getZoneDistrictMapping() {
		return zoneDistrictMapping;
	}

	public void setZoneDistrictMapping(ZoneDistrictMapping zoneDistrictMapping) {
		this.zoneDistrictMapping = zoneDistrictMapping;
	}

	public void setDistrictID(Integer districtID) {
		this.districtID = districtID;
	}

	/*
	 * public Districts(Integer districtID, Integer stateID, String districtName,
	 * Boolean deleted, String createdBy, Timestamp createdDate, String modifiedBy,
	 * Timestamp lastModDate, States states, ZoneDistrictMapping
	 * zoneDistrictMapping) { super(); this.districtID = districtID; this.stateID =
	 * stateID; this.districtName = districtName; this.deleted = deleted;
	 * this.createdBy = createdBy; this.createdDate = createdDate; this.modifiedBy =
	 * modifiedBy; this.lastModDate = lastModDate; this.states = states;
	 * this.zoneDistrictMapping = zoneDistrictMapping; }
	 */

	
	/*
	 * public Districts(Integer DistrictID, String DistrictName) { this.districtID =
	 * DistrictID; this.districtName = DistrictName; }
	 */

	public Districts(Integer DistrictID, String DistrictName, Integer stateId, String stateName,Integer govtLGDStateID,Integer govtLGDDistrictID) {
		this.states = new States(stateId.intValue(), stateName, govtLGDStateID);
		this.districtID = DistrictID;
		this.districtName = DistrictName;
		this.govtLGDStateID = govtLGDStateID;
		this.govtLGDDistrictID = govtLGDDistrictID;
	}
	
	public Districts(Integer districtID, String districtName, Integer govtLGDStateID, Integer govtLGDDistrictID) {
		this.districtID = districtID;
		this.districtName = districtName;
		this.govtLGDStateID = govtLGDStateID;
		this.govtLGDDistrictID = govtLGDDistrictID;
		}
	
	
	
	

	public Districts(Integer districtID, Integer stateID, Integer govtLGDStateID, Integer govtLGDDistrictID,
			String districtName, Boolean deleted, String createdBy, Timestamp createdDate, String modifiedBy,
			Timestamp lastModDate, States states, ZoneDistrictMapping zoneDistrictMapping) {
		super();
		this.districtID = districtID;
		this.stateID = stateID;
		this.govtLGDStateID = govtLGDStateID;
		this.govtLGDDistrictID = govtLGDDistrictID;
		this.districtName = districtName;
		this.deleted = deleted;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
		this.states = states;
		this.zoneDistrictMapping = zoneDistrictMapping;
	}

	public Integer getGovtLGDStateID() {
		return govtLGDStateID;
	}

	public void setGovtLGDStateID(Integer govtLGDStateID) {
		this.govtLGDStateID = govtLGDStateID;
	}

	public Integer getGovtLGDDistrictID() {
		return govtLGDDistrictID;
	}

	public void setGovtLGDDistrictID(Integer govtLGDDistrictID) {
		this.govtLGDDistrictID = govtLGDDistrictID;
	}

	public int getDistrictID() {
		return this.districtID.intValue();
	}

	public void setDistrictID(int districtID) {
		this.districtID = Integer.valueOf(districtID);
	}

	public String getDistrictName() {
		return this.districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	// public String getZone() {
	// return this.zone;
	// }
	//
	// public void setZone(String zone) {
	// this.zone = zone;
	// }

	public boolean isDeleted() {
		return this.deleted.booleanValue();
	}

	public void setDeleted(boolean deleted) {
		this.deleted = Boolean.valueOf(deleted);
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getLastModDate() {
		return this.lastModDate;
	}

	public void setLastModDate(Timestamp lastModDate) {
		this.lastModDate = lastModDate;
	}

	public States getStates() {
		return this.states;
	}

	public void setStates(States states) {
		this.states = states;
	}

}
