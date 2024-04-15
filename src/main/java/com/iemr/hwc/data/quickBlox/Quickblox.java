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
package com.iemr.hwc.data.quickBlox;

import java.sql.Date;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_quickblox")
	
public class Quickblox {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	@Column(name = "QuickbloxID")
	private Integer quickbloxID;
	@Expose
	@Column(name = "specialistUserID")
	private Integer specialistUserID;
	@Expose
	@Column(name = "specialistQuickbloxID")
	private Long specialistQuickbloxID;
	@Expose
	@Column(name = "specialistBenQuickbloxID")
	private Long specialistBenQuickbloxID;
	@Expose
	@Column(name = "specialistBenQuickBloxPass")
	private String specialistBenQuickBloxPass;
	@Expose
	@Column(name = "specialistQuickbloxpass")
	private String specialistQuickbloxpass;
	
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;
	@Expose
	@Column(name = "CreatedBy")
	private String createdBy;
	@Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp CreatedDate;
	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private String processed;
	@Expose
	@Column(name = "ModifiedBy")
	private String ModifiedBy;
	public Integer getQuickbloxID() {
		return quickbloxID;
	}
	public void setQuickbloxID(Integer quickbloxID) {
		this.quickbloxID = quickbloxID;
	}
	public Integer getSpecialistUserID() {
		return specialistUserID;
	}
	public void setSpecialistUserID(Integer specialistUserID) {
		this.specialistUserID = specialistUserID;
	}
	public Long getSpecialistQuickbloxID() {
		return specialistQuickbloxID;
	}
	public void setSpecialistQuickbloxID(Long specialistQuickbloxID) {
		this.specialistQuickbloxID = specialistQuickbloxID;
	}
	public Long getSpecialistBenQuickbloxID() {
		return specialistBenQuickbloxID;
	}
	public void setSpecialistBenQuickbloxID(Long specialistBenQuickbloxID) {
		this.specialistBenQuickbloxID = specialistBenQuickbloxID;
	}
	public String getSpecialistBenQuickBloxPass() {
		return specialistBenQuickBloxPass;
	}
	public void setSpecialistBenQuickBloxPass(String specialistBenQuickBloxPass) {
		this.specialistBenQuickBloxPass = specialistBenQuickBloxPass;
	}
	public String getSpecialistQuickbloxpass() {
		return specialistQuickbloxpass;
	}
	public void setSpecialistQuickbloxpass(String specialistQuickbloxpass) {
		this.specialistQuickbloxpass = specialistQuickbloxpass;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedDate() {
		return CreatedDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		CreatedDate = createdDate;
	}
	public String getProcessed() {
		return processed;
	}
	public void setProcessed(String processed) {
		this.processed = processed;
	}
	public String getModifiedBy() {
		return ModifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		ModifiedBy = modifiedBy;
	}
	public Timestamp getLastModDate() {
		return LastModDate;
	}
	public void setLastModDate(Timestamp lastModDate) {
		LastModDate = lastModDate;
	}
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp LastModDate;

}
