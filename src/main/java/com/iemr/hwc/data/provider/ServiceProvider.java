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
package com.iemr.hwc.data.provider;

import java.sql.Date;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.JoIntegerable;
import jakarta.persistence.Table;

import com.google.gson.annotations.Expose;
import com.iemr.hwc.utils.mapper.OutputMapper;

@Entity
@Table(name = "m_ServiceProvider")
public class ServiceProvider {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "ServiceProviderId")
	private Integer serviceProviderId;
	@Expose
	@Column(name = "ServiceProviderName")
	private String serviceProviderName;
	@Expose
	@Column(name = "JoiningDate")
	private Date joiningDate;
	@Expose
	@Column(name = "StateId")
	private Integer stateId;
	@Expose
	@Column(name = "LogoFileName")
	private String logoFileName;
	@Expose
	@Column(name = "LogoFilePath")
	private String logoFilePath;
	@Expose
	@Column(name = "PrimaryContactName")
	private String primaryContactName;
	@Expose
	@Column(name = "PrimaryContactNo")
	private String primaryContactNo;
	@Expose
	@Column(name = "PrimaryContactEmailID")
	private String primaryContactEmailID;
	@Expose
	@Column(name = "PrimaryContactAddress")
	private String primaryContactAddress;
	@Expose
	@Column(name = "PrimaryContactValidityTillDate")
	private String primaryContactValidityTillDate;
	@Expose
	@Column(name = "SecondaryContactName")
	private String secondaryContactName;
	@Expose
	@Column(name = "SecondaryContactNo")
	private String secondaryContactNo;
	@Expose
	@Column(name = "SecondaryContactEmailID")
	private String secondaryContactEmailID;
	@Expose
	@Column(name = "SecondaryContactAddress")
	private String secondaryContactAddress;
	@Expose
	@Column(name = "SecondaryContactValidityTillDate")
	private Date secondaryContactValidityTillDate;
	@Expose
	@Column(name = "StatusID")
	private Integer statusID;
	@Expose

	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;
	@Expose
	@Column(name = "CreatedBy")
	private String createdBy;
	@Expose
	@Column(name = "ValidFrom")
	private Date validFrom;
	@Expose
	@Column(name = "ValidTill")
	private Date validTill;

	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp CreatedDate;
	private String ModifiedBy;
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp LastModDate;

	public ServiceProvider() {

	}

	public ServiceProvider(Integer serviceProviderId, String serviceProviderName, Date joiningDate, Integer stateId,
			String logoFileName, String logoFilePath, String primaryContactName, String primaryContactNo,
			String primaryContactEmailID, String primaryContactAddress, String primaryContactValidityTillDate,
			String secondaryContactName, String secondaryContactNo, String secondaryContactEmailID,
			String secondaryContactAddress, Date secondaryContactValidityTillDate, Boolean deleted, String createdBy,
			Date validFrom, Date validTill, Timestamp createdDate, String modifiedBy, Timestamp lastModDate) {

		this.serviceProviderId = serviceProviderId;
		this.serviceProviderName = serviceProviderName;
		this.joiningDate = joiningDate;
		this.stateId = stateId;
		this.logoFileName = logoFileName;
		this.logoFilePath = logoFilePath;
		this.primaryContactName = primaryContactName;
		this.primaryContactNo = primaryContactNo;
		this.primaryContactEmailID = primaryContactEmailID;
		this.primaryContactAddress = primaryContactAddress;
		this.primaryContactValidityTillDate = primaryContactValidityTillDate;
		this.secondaryContactName = secondaryContactName;
		this.secondaryContactNo = secondaryContactNo;
		this.secondaryContactEmailID = secondaryContactEmailID;
		this.secondaryContactAddress = secondaryContactAddress;
		this.secondaryContactValidityTillDate = secondaryContactValidityTillDate;
		this.deleted = deleted;
		this.createdBy = createdBy;
		this.validFrom = validFrom;
		this.validTill = validTill;
		CreatedDate = createdDate;
		ModifiedBy = modifiedBy;
		LastModDate = lastModDate;
	}

	public Integer getServiceProviderId() {
		return serviceProviderId;
	}

	public void setServiceProviderId(Integer serviceProviderId) {
		this.serviceProviderId = serviceProviderId;
	}

	public String getServiceProviderName() {
		return serviceProviderName;
	}

	public void setServiceProviderName(String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
	}

	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public String getLogoFileName() {
		return logoFileName;
	}

	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}

	public String getLogoFilePath() {
		return logoFilePath;
	}

	public void setLogoFilePath(String logoFilePath) {
		this.logoFilePath = logoFilePath;
	}

	public String getPrimaryContactName() {
		return primaryContactName;
	}

	public void setPrimaryContactName(String primaryContactName) {
		this.primaryContactName = primaryContactName;
	}

	public String getPrimaryContactNo() {
		return primaryContactNo;
	}

	public void setPrimaryContactNo(String primaryContactNo) {
		this.primaryContactNo = primaryContactNo;
	}

	public String getPrimaryContactEmailID() {
		return primaryContactEmailID;
	}

	public void setPrimaryContactEmailID(String primaryContactEmailID) {
		this.primaryContactEmailID = primaryContactEmailID;
	}

	public String getPrimaryContactAddress() {
		return primaryContactAddress;
	}

	public void setPrimaryContactAddress(String primaryContactAddress) {
		this.primaryContactAddress = primaryContactAddress;
	}

	public String getPrimaryContactValidityTillDate() {
		return primaryContactValidityTillDate;
	}

	public void setPrimaryContactValidityTillDate(String primaryContactValidityTillDate) {
		this.primaryContactValidityTillDate = primaryContactValidityTillDate;
	}

	public String getSecondaryContactName() {
		return secondaryContactName;
	}

	public void setSecondaryContactName(String secondaryContactName) {
		this.secondaryContactName = secondaryContactName;
	}

	public String getSecondaryContactNo() {
		return secondaryContactNo;
	}

	public void setSecondaryContactNo(String secondaryContactNo) {
		this.secondaryContactNo = secondaryContactNo;
	}

	public String getSecondaryContactEmailID() {
		return secondaryContactEmailID;
	}

	public void setSecondaryContactEmailID(String secondaryContactEmailID) {
		this.secondaryContactEmailID = secondaryContactEmailID;
	}

	public String getSecondaryContactAddress() {
		return secondaryContactAddress;
	}

	public void setSecondaryContactAddress(String secondaryContactAddress) {
		this.secondaryContactAddress = secondaryContactAddress;
	}

	public Date getSecondaryContactValidityTillDate() {
		return secondaryContactValidityTillDate;
	}

	public void setSecondaryContactValidityTillDate(Date secondaryContactValidityTillDate) {
		this.secondaryContactValidityTillDate = secondaryContactValidityTillDate;
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

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTill() {
		return validTill;
	}

	public void setValidTill(Date validTill) {
		this.validTill = validTill;
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

	public Integer getStatusID() {
		return statusID;
	}

	public void setStatusID(Integer statusID) {
		this.statusID = statusID;
	}

	private static OutputMapper outputMapper = new OutputMapper();

	@Override
	public String toString() {
		return outputMapper.gson().toJson(this);
	}

}