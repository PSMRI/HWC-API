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
package com.iemr.mmu.data.videoconsultation;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.utils.mapper.OutputMapper;

@Entity
@Table(name = "m_userswymedmapping")
public class VideoConsultationUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "UserSwymedMapID")
	private Long userSwymedMapID;

	@Expose
	@Column(name = "userID")
	private Long userID;
	@Expose
	@Column(name = "SwymedID")
	private Long swymedID;
	@Expose
	@Column(name = "SwymedPassword")
	private String swymedPassword;

	@Expose
	@Column(name = "SwymedEmailID")
	private String swymedEmailID;

	@Expose
	@Column(name = "SwymedDomain")
	private String swymedDomain;

	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean Deleted;
	private String CreatedBy;
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp CreatedDate;
	private String ModifiedBy;
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp LastModDate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UserID", insertable = false, updatable = false)
	@Expose
	private M_UserTemp user;

	@Transient
	private OutputMapper outputMapper = new OutputMapper();

	@Transient
	private String username;

	@Override
	public String toString() {
		return outputMapper.gson().toJson(this);
	}

	public VideoConsultationUser() {

	}

	public VideoConsultationUser(VideoConsultationUser sw, String username) {
		this.swymedDomain = sw.getSwymedDomain();
		this.swymedPassword = sw.getSwymedPassword();
		this.swymedEmailID = sw.getSwymedEmailID();
		this.username = username;

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getUserSwymedMapID() {
		return userSwymedMapID;
	}

	public void setUserSwymedMapID(Long userSwymedMapID) {
		this.userSwymedMapID = userSwymedMapID;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public Long getSwymedID() {
		return swymedID;
	}

	public void setSwymedID(Long swymedID) {
		this.swymedID = swymedID;
	}

	public String getSwymedPassword() {
		return swymedPassword;
	}

	public void setSwymedPassword(String swymedPassword) {
		this.swymedPassword = swymedPassword;
	}

	public String getSwymedEmailID() {
		return swymedEmailID;
	}

	public void setSwymedEmailID(String swymedEmailID) {
		this.swymedEmailID = swymedEmailID;
	}

	public String getSwymedDomain() {
		return swymedDomain;
	}

	public void setSwymedDomain(String swymedDomain) {
		this.swymedDomain = swymedDomain;
	}

	public Boolean getDeleted() {
		return Deleted;
	}

	public void setDeleted(Boolean deleted) {
		Deleted = deleted;
	}

	public String getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		CreatedDate = createdDate;
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

	public M_UserTemp getUser() {
		return user;
	}

	public void setUser(M_UserTemp user) {
		this.user = user;
	}

	public OutputMapper getOutputMapper() {
		return outputMapper;
	}

	public void setOutputMapper(OutputMapper outputMapper) {
		this.outputMapper = outputMapper;
	}

}
