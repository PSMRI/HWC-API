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
package com.iemr.mmu.data.masterdata.doctor;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.utils.mapper.OutputMapper;

@Entity
@Table(name="m_pharmacologicalcategory")
public class M_Pharmacologicalcategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "PharmCategoryID")
	private Integer pharmacologyCategoryID; 
	@Expose
	@Column(name = "PharmCategoryName")
	private String pharmCategoryName;
	@Expose
	@Column(name = "PharmCategoryDesc")
	private String pharmCategoryDesc;
	@Expose
	@Column(name = "PharmCategoryCode")
	private String pharmCategoryCode; 
	@Expose
	@Column(name = "Status")
	private String status;
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
	
	
	public M_Pharmacologicalcategory() {
		// TODO Auto-generated constructor stub
	}


	








	public String getPharmCategoryName() {
		return pharmCategoryName;
	}





	public Integer getPharmacologyCategoryID() {
		return pharmacologyCategoryID;
	}











	public void setPharmacologyCategoryID(Integer pharmacologyCategoryID) {
		this.pharmacologyCategoryID = pharmacologyCategoryID;
	}











//	public OutputMapper getOutputMapper() {
//		return outputMapper;
//	}











//	public void setOutputMapper(OutputMapper outputMapper) {
//		this.outputMapper = outputMapper;
//	}











	public void setPharmCategoryName(String pharmCategoryName) {
		this.pharmCategoryName = pharmCategoryName;
	}





	public String getPharmCategoryDesc() {
		return pharmCategoryDesc;
	}





	public void setPharmCategoryDesc(String pharmCategoryDesc) {
		this.pharmCategoryDesc = pharmCategoryDesc;
	}





	public String getPharmCategoryCode() {
		return pharmCategoryCode;
	}





	public void setPharmCategoryCode(String pharmCategoryCode) {
		this.pharmCategoryCode = pharmCategoryCode;
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
	
	public Integer getPharmCategoryID(){
		return pharmacologyCategoryID;
		
	}

}

