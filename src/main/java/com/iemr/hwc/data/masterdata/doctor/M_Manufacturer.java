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
package com.iemr.hwc.data.masterdata.doctor;
import java.sql.Date;

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
@Table(name="m_manufacturer")

public class M_Manufacturer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "ManufacturerID")
	private Integer manufacturerID;
	@Expose
	@Column(name = "ManufacturerName")
	private String manufacturerName;
	@Expose
	@Column(name = "ManufacturerDesc")
	private String manufacturerDesc;
	@Expose
	@Column(name = "ManufacturerCode")
	private String manufacturerCode;
	@Expose
	@Column(name = "Status")
	private String status;
	@Expose
	@Column(name = "ContactPerson")
	private String contactPerson; 
	@Expose
	@Column(name = "CST_GST_No")
	private String cST_GST_No;
	@Expose
	@Column(name="AddressLine1")
	private String addressLine1;
	@Expose
	@Column(name="AddressLine2")
	private String addressLine2;
	@Expose
	@Column(name="DistrictID")
	private Integer districtID;
	@Expose
	@Column(name="StateID")
	private Integer stateID;
	@Expose
	@Column(name="CountryID")
	private Integer countryID;
	@Expose
	@Column(name="PinCode")
	private String pinCode;
	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID; 
	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;
	@Expose
	@Column(name = "CreatedBy")
	private String createdBy;
	@Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Date createdDate;
	@Expose
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	@Expose
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Date lastModDate;
	
	
	public M_Manufacturer() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the manufacturerID
	 */
	public Integer getManufacturerID() {
		return manufacturerID;
	}


	/**
	 * @param manufacturerID the manufacturerID to set
	 */
	public void setManufacturerID(Integer manufacturerID) {
		this.manufacturerID = manufacturerID;
	}


	/**
	 * @return the manufacturerName
	 */
	public String getManufacturerName() {
		return manufacturerName;
	}


	/**
	 * @param manufacturerName the manufacturerName to set
	 */
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}


	/**
	 * @return the manufacturerDesc
	 */
	public String getManufacturerDesc() {
		return manufacturerDesc;
	}


	/**
	 * @param manufacturerDesc the manufacturerDesc to set
	 */
	public void setManufacturerDesc(String manufacturerDesc) {
		this.manufacturerDesc = manufacturerDesc;
	}


	/**
	 * @return the manufacturerCode
	 */
	public String getManufacturerCode() {
		return manufacturerCode;
	}


	/**
	 * @param manufacturerCode the manufacturerCode to set
	 */
	public void setManufacturerCode(String manufacturerCode) {
		this.manufacturerCode = manufacturerCode;
	}


	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * @return the contactPerson
	 */
	public String getContactPerson() {
		return contactPerson;
	}


	/**
	 * @param contactPerson the contactPerson to set
	 */
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}


	/**
	 * @return the cST_GST_No
	 */
	public String getcST_GST_No() {
		return cST_GST_No;
	}


	/**
	 * @param cST_GST_No the cST_GST_No to set
	 */
	public void setcST_GST_No(String cST_GST_No) {
		this.cST_GST_No = cST_GST_No;
	}


	/**
	 * @return the providerServiceMapID
	 */
	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}


	/**
	 * @param providerServiceMapID the providerServiceMapID to set
	 */
	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}


	/**
	 * @return the deleted
	 */
	public Boolean getDeleted() {
		return deleted;
	}


	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}


	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}


	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}


	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}


	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	/**
	 * @return the lastModDate
	 */
	public Date getLastModDate() {
		return lastModDate;
	}


	/**
	 * @param lastModDate the lastModDate to set
	 */
	public void setLastModDate(Date lastModDate) {
		this.lastModDate = lastModDate;
	}
	
//	@Transient
//	private OutputMapper outputMapper = new OutputMapper();
//
//	@Override
//	public String toString() {
//		return outputMapper.gson().toJson(this);
//	}

}

