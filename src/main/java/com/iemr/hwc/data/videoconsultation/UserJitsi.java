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
package com.iemr.hwc.data.videoconsultation;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_userjitsimapping")
public class UserJitsi {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	@Column(name = "JitsiID")
	private Long jitsiID;

	@Expose
	@Column(name = "UserID")
	private Long userID;
	@Expose
	@Column(name = "JitsiUserName")
	private String jitsiUserName;
	@Expose
	@Column(name = "JitsiPassword")
	private String jitsiPassword;	
	
	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;

	@Expose
	@Column(name = "Vanid")
	private Integer vanID;	
	
	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean Deleted;
	
	@Expose
	@Column(name = "CreatedBy")
	private String createdBy;	
	
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp CreatedDate;
	
	@Expose
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	
	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private String processed;
	
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp LastModDate;

	
	public UserJitsi() {
		
	}
	
	public UserJitsi(String jitsiUserName,String jitsiPassword) {
		this.jitsiUserName = jitsiUserName;
		this.jitsiPassword = jitsiPassword;
	}
	
	public Long getJitsiID() {
		return jitsiID;
	}

	public void setJitsiID(Long jitsiID) {
		this.jitsiID = jitsiID;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public String getJitsiUserName() {
		return jitsiUserName;
	}

	public void setJitsiUserName(String jitsiUserName) {
		this.jitsiUserName = jitsiUserName;
	}

	public String getJitsiPassword() {
		return jitsiPassword;
	}

	public void setJitsiPassword(String jitsiPassword) {
		this.jitsiPassword = jitsiPassword;
	}

	
	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}

	public Integer getVanid() {
		return vanID;
	}

	public void setVanid(Integer vanID) {
		this.vanID = vanID;
	}

	public Boolean getDeleted() {
		return Deleted;
	}

	public void setDeleted(Boolean deleted) {
		Deleted = deleted;
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

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getProcessed() {
		return processed;
	}

	public void setProcessed(String processed) {
		this.processed = processed;
	}

	public Timestamp getLastModDate() {
		return LastModDate;
	}

	public void setLastModDate(Timestamp lastModDate) {
		LastModDate = lastModDate;
	}
	
	
}
