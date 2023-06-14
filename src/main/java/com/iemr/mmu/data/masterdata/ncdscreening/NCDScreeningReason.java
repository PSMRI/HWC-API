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
package com.iemr.mmu.data.masterdata.ncdscreening;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_ncdscreeningreason")
public class NCDScreeningReason {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "ncdScreeningReasonID")
	private Integer ncdScreeningReasonID; 
	
	@Expose
	@Column(name = "ncdScreeningReason")
	private String ncdScreeningReason; 
	
	@Expose
	@Column(name = "ncdScreeningReasonDesc")
	private String ncdScreeningReasonDesc; 
	
	@Expose
	@Column(name = "ncdScreeningConditionID")
	private Integer ncdScreeningConditionID; 
	
	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;
	
	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private String processed;
	
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

	
	public NCDScreeningReason(Integer ncdScreeningReasonID, String ncdScreeningReason) {
		super();
		this.ncdScreeningReasonID = ncdScreeningReasonID;
		this.ncdScreeningReason = ncdScreeningReason;
	}

	public static ArrayList<NCDScreeningReason> getNCDScreeningReason(ArrayList<Object[]> resList) {
		ArrayList<NCDScreeningReason> resArray = new ArrayList<NCDScreeningReason>();
		for (Object[] obj : resList) {
			NCDScreeningReason cOBJ = new NCDScreeningReason((Integer)obj[0], (String)obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
	
	public Integer getNcdScreeningReasonID() {
		return ncdScreeningReasonID;
	}

	public void setNcdScreeningReasonID(Integer ncdScreeningReasonID) {
		this.ncdScreeningReasonID = ncdScreeningReasonID;
	}

	public String getNcdScreeningReason() {
		return ncdScreeningReason;
	}

	public void setNcdScreeningReason(String ncdScreeningReason) {
		this.ncdScreeningReason = ncdScreeningReason;
	}

	public String getNcdScreeningReasonDesc() {
		return ncdScreeningReasonDesc;
	}

	public void setNcdScreeningReasonDesc(String ncdScreeningReasonDesc) {
		this.ncdScreeningReasonDesc = ncdScreeningReasonDesc;
	}

	public Integer getNcdScreeningConditionID() {
		return ncdScreeningConditionID;
	}

	public void setNcdScreeningConditionID(Integer ncdScreeningConditionID) {
		this.ncdScreeningConditionID = ncdScreeningConditionID;
	}

	public Boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getProcessed() {
		return processed;
	}

	public void setProcessed(String processed) {
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
