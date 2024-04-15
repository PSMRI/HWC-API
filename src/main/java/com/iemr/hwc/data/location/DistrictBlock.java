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
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.hwc.utils.mapper.OutputMapper;

@Entity
@Table(name = "m_districtblock")
public class DistrictBlock {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BlockID")
	@Expose
	private Integer blockID;

	@Column(name = "GovDistrictID")
	@Expose
	private Integer govtLGDDistrictID;

	@Column(name = "GovSubDistrictID")
	@Expose
	private Integer govLGDSubDistrictID;
	
	@Column(name = "DistrictID")
	@Expose
	private Integer districtID;
	@Column(name = "BlockName")
	@Expose
	private String blockName;
	@Column(name = "StateID")
	@Expose
	private Integer stateID;
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

	@Transient
	private OutputMapper outputMapper = new OutputMapper();

	

	/*
	 * public DistrictBlock(Integer BlockID, String BlockName) { this.blockID =
	 * BlockID; this.blockName = BlockName; }
	 */
	
	public Integer getBlockID() {
		return this.blockID;
	}

	public void setBlockID(Integer blockID) {
		this.blockID = blockID;
	}

	public Integer getDistrictID() {
		return this.districtID;
	}

	public void setDistrictID(Integer districtID) {
		this.districtID = districtID;
	}

	public String getBlockName() {
		return this.blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public Integer getStateID() {
		return this.stateID;
	}

	public void setStateID(Integer stateID) {
		this.stateID = stateID;
	}

	public Boolean isDeleted() {
		return this.deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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
	
	
	

	public DistrictBlock(Integer blockID, String blockName, Integer govtLGDDistrictID, Integer govLGDSubDistrictID) {
		this.blockID = blockID;
		this.blockName = blockName;
		this.govtLGDDistrictID = govtLGDDistrictID;
		this.govLGDSubDistrictID = govLGDSubDistrictID;
		
	}

	
	@Override
	public String toString() {
		return outputMapper.gson().toJson(this);
	}
}
