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
package com.iemr.hwc.repo.location;

import java.sql.Timestamp;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.google.gson.annotations.Expose;
import com.iemr.hwc.data.location.Districts;

@Entity
@Table(name = "m_zonedistrictmap")
public class ZoneDistrictMapping {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	@Column(name = "ZoneDistrictMapID")
	private Integer zoneDistrictMapID;
	@Expose
	@Column(name = "ZoneID")
	private Integer zoneID;
	@Expose
	@Column(name = "DistrictID")
	private Integer districtID;
	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;
	@Expose
	@Column(name = "Processed")
	private String processed;
	@Expose
	@Column(name = "CreatedBy")
	private String createdBy;
	@Expose
	@Column(name = "CreatedDate")
	private Timestamp createdDate;
	@Expose
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	@Expose
	@Column(name = "LastModDate")
	private Timestamp lastModDate;

	@Expose
	@OneToMany(mappedBy = "zoneDistrictMapping", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Districts> districtsSet;

	public ZoneDistrictMapping() {
	}

	public ZoneDistrictMapping(Integer zoneDistrictMapID, Integer zoneID, Integer districtID,
			Integer providerServiceMapID, Boolean deleted, String processed, String createdBy, Timestamp createdDate,
			String modifiedBy, Timestamp lastModDate) {
		super();
		this.zoneDistrictMapID = zoneDistrictMapID;
		this.zoneID = zoneID;
		this.districtID = districtID;
		this.providerServiceMapID = providerServiceMapID;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
	}

	public ZoneDistrictMapping(Integer zoneDistrictMapID, Integer zoneID, Integer districtID,
			Integer providerServiceMapID, Boolean deleted, String processed, String createdBy, Timestamp createdDate,
			String modifiedBy, Timestamp lastModDate, Set<Districts> districtsSet) {
		super();
		this.zoneDistrictMapID = zoneDistrictMapID;
		this.zoneID = zoneID;
		this.districtID = districtID;
		this.providerServiceMapID = providerServiceMapID;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
		this.districtsSet = districtsSet;
	}

	public Set<Districts> getDistrictsSet() {
		return districtsSet;
	}

	public void setDistrictsSet(Set<Districts> districtsSet) {
		this.districtsSet = districtsSet;
	}

	public Integer getZoneDistrictMapID() {
		return zoneDistrictMapID;
	}

	public void setZoneDistrictMapID(Integer zoneDistrictMapID) {
		this.zoneDistrictMapID = zoneDistrictMapID;
	}

	public Integer getZoneID() {
		return zoneID;
	}

	public void setZoneID(Integer zoneID) {
		this.zoneID = zoneID;
	}

	public Integer getDistrictID() {
		return districtID;
	}

	public void setDistrictID(Integer districtID) {
		this.districtID = districtID;
	}

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getProcessed() {
		return processed;
	}

	public void setProcessed(String processed) {
		this.processed = processed;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getLastModDate() {
		return lastModDate;
	}

	public void setLastModDate(Timestamp lastModDate) {
		this.lastModDate = lastModDate;
	}

}
