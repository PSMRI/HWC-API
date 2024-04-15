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


@Entity
@Table(name="m_uom")
public class M_Uom {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	@Column(name="UOMID")
	private Integer uOMID;
	@Expose
	@Column(name="UOMName")
	private String uOMName;
	@Expose
	@Column(name="UOMDesc")
	private String uOMDesc; 
	@Expose
	@Column(name="UOMCode")
	private String uOMCode;
	@Expose
	@Column(name="Status")
	private String status;
	@Expose
	@Column(name="ProviderServiceMapID")
	private Integer providerServiceMapID;
	@Expose
	@Column(name="Deleted",insertable = false, updatable = true)
	private Boolean deleted;
	@Expose
	@Column(name="Processed",insertable = false, updatable = true)
	private Character processed;
	@Expose
	@Column(name="CreatedBy")
	private String createdBy;
	@Expose
	@Column(name="CreatedDate",insertable = false, updatable = false)
	private Date createdDate;
	@Expose
	@Column(name="ModifiedBy")
	private String modifiedBy;
	@Expose
	@Column(name="LastModDate",insertable = false, updatable = false)
	private Date lastModDate;
	
	
	public M_Uom() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the uOMID
	 */
	public Integer getuOMID() {
		return uOMID;
	}


	/**
	 * @param uOMID the uOMID to set
	 */
	public void setuOMID(Integer uOMID) {
		this.uOMID = uOMID;
	}


	/**
	 * @return the uOMName
	 */
	public String getuOMName() {
		return uOMName;
	}


	/**
	 * @param uOMName the uOMName to set
	 */
	public void setuOMName(String uOMName) {
		this.uOMName = uOMName;
	}


	/**
	 * @return the uOMDesc
	 */
	public String getuOMDesc() {
		return uOMDesc;
	}


	/**
	 * @param uOMDesc the uOMDesc to set
	 */
	public void setuOMDesc(String uOMDesc) {
		this.uOMDesc = uOMDesc;
	}


	/**
	 * @return the uOMCode
	 */
	public String getuOMCode() {
		return uOMCode;
	}


	/**
	 * @param uOMCode the uOMCode to set
	 */
	public void setuOMCode(String uOMCode) {
		this.uOMCode = uOMCode;
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
	 * @return the processed
	 */
	public Character getProcessed() {
		return processed;
	}


	/**
	 * @param processed the processed to set
	 */
	public void setProcessed(Character processed) {
		this.processed = processed;
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
//	
	
	

}

