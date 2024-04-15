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
package com.iemr.hwc.data.doctor;

import java.sql.Timestamp;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_drugduration")
public class DrugDurationUnitMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	@Column(name = "DrugDurationID")
	private Integer drugDurationID;
	@Expose
	@Column(name = "DrugDuration")
	private String drugDuration;
	@Expose
	@Column(name = "DrugDurationDesc")
	private String drugDurationDesc;
	@Expose
	@Column(name = "Deleted", insertable = false, updatable = false)
	private Boolean deleted;
	@Expose
	@Column(name = "Processed", insertable = false, updatable = false)
	private Character processed;
	@Expose
	@Column(name = "CreatedBy")
	private String createdBy;
	@Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;
	@Expose
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	@Expose
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	public DrugDurationUnitMaster() {
	}

	public DrugDurationUnitMaster(Integer drugDurationID, String drugDuration, String drugDurationDesc, Boolean deleted,
			Character processed, String createdBy, Timestamp createdDate, String modifiedBy, Timestamp lastModDate) {
		super();
		this.drugDurationID = drugDurationID;
		this.drugDuration = drugDuration;
		this.drugDurationDesc = drugDurationDesc;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
	}
	
	public DrugDurationUnitMaster(Integer drugDurationID, String drugDuration) {
		super();
		this.drugDurationID = drugDurationID;
		this.drugDuration = drugDuration;
	}
	
	public static ArrayList<DrugDurationUnitMaster> getDrugDurationUnitMaster(ArrayList<Object[]> resList) {
		ArrayList<DrugDurationUnitMaster> resArray = new ArrayList<DrugDurationUnitMaster>();
		for (Object[] obj : resList) {
			DrugDurationUnitMaster cOBJ = new DrugDurationUnitMaster((Integer)obj[0], (String)obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}

	public Integer getDrugDurationID() {
		return drugDurationID;
	}

	public void setDrugDurationID(Integer drugDurationID) {
		this.drugDurationID = drugDurationID;
	}

	public String getDrugDuration() {
		return drugDuration;
	}

	public void setDrugDuration(String drugDuration) {
		this.drugDuration = drugDuration;
	}

	public String getDrugDurationDesc() {
		return drugDurationDesc;
	}

	public void setDrugDurationDesc(String drugDurationDesc) {
		this.drugDurationDesc = drugDurationDesc;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Character getProcessed() {
		return processed;
	}

	public void setProcessed(Character processed) {
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
