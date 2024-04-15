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
@Table(name = "m_chiefcomplaint")
public class ChiefComplaintMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	@Column(name = "ChiefComplaintID")
	private Integer chiefComplaintID;
	@Expose
	@Column(name = "ChiefComplaint")
	private String chiefComplaint;
	@Expose
	@Column(name = "ChiefComplaintDesc")
	private String chiefComplaintDesc;

	@Column(name = "Deleted", insertable = false, updatable = false)
	private Boolean deleted;

	@Column(name = "Processed", insertable = false, updatable = false)
	private Character processed;

	@Column(name = "CreatedBy")
	private String createdBy;

	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;

	@Column(name = "ModifiedBy")
	private String modifiedBy;

	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	public ChiefComplaintMaster() {
	}

	public ChiefComplaintMaster(Integer chiefComplaintID, String chiefComplaint, String chiefComplaintDesc,
			Boolean deleted, Character processed, String createdBy, Timestamp createdDate, String modifiedBy,
			Timestamp lastModDate) {
		super();
		this.chiefComplaintID = chiefComplaintID;
		this.chiefComplaint = chiefComplaint;
		this.chiefComplaintDesc = chiefComplaintDesc;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
	}

	public ChiefComplaintMaster(Integer chiefComplaintID, String chiefComplaint, String chiefComplaintDesc) {
		super();
		this.chiefComplaintID = chiefComplaintID;
		this.chiefComplaint = chiefComplaint;
		this.chiefComplaintDesc = chiefComplaintDesc;
	}

	public static ArrayList<ChiefComplaintMaster> getChiefComplaintMasters(ArrayList<Object[]> resList) {
		ArrayList<ChiefComplaintMaster> resArray = new ArrayList<ChiefComplaintMaster>();
		for (Object[] obj : resList) {
			ChiefComplaintMaster cOBJ = new ChiefComplaintMaster((Integer) obj[0], (String) obj[1], (String) obj[2]);
			resArray.add(cOBJ);
		}
		return resArray;
	}

	public Integer getChiefComplaintID() {
		return chiefComplaintID;
	}

	public void setChiefComplaintID(Integer chiefComplaintID) {
		this.chiefComplaintID = chiefComplaintID;
	}

	public String getChiefComplaint() {
		return chiefComplaint;
	}

	public void setChiefComplaint(String chiefComplaint) {
		this.chiefComplaint = chiefComplaint;
	}

	public String getChiefComplaintDesc() {
		return chiefComplaintDesc;
	}

	public void setChiefComplaintDesc(String chiefComplaintDesc) {
		this.chiefComplaintDesc = chiefComplaintDesc;
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
