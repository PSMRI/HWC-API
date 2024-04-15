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
package com.iemr.hwc.data.masterdata.anc;

import java.sql.Timestamp;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.google.gson.annotations.Expose;
import com.iemr.hwc.data.doctor.CancerDiagnosis;

@Entity
@Table(name = "m_allergicreactiontype")
public class AllergicReactionTypes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	@Column(name = "AllergicReactionTypeID")
	private Short allergicReactionTypeID;
	
	@Expose
	@Column(name = "Name")
	private String name;
	
	@Expose
	@Column(name = "AllergicReactionTypeDesc")
	private String allergicReactionTypeDesc;

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

	public AllergicReactionTypes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AllergicReactionTypes(Short allergicReactionTypeID, String name, String allergicReactionTypeDesc) {
		super();
		this.allergicReactionTypeID = allergicReactionTypeID;
		this.name = name;
		this.allergicReactionTypeDesc = allergicReactionTypeDesc;
	}
	
	public static ArrayList<AllergicReactionTypes> getAllergicReactionTypes(ArrayList<Object[]> resList) {
		ArrayList<AllergicReactionTypes> resArray = new ArrayList<AllergicReactionTypes>();
		for (Object[] obj : resList) {
			AllergicReactionTypes cOBJ = new AllergicReactionTypes((Short)obj[0], (String)obj[1], (String)obj[2]);
			resArray.add(cOBJ);
		}
		return resArray;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAllergicReactionTypeDesc() {
		return allergicReactionTypeDesc;
	}

	public void setAllergicReactionTypeDesc(String allergicReactionTypeDesc) {
		this.allergicReactionTypeDesc = allergicReactionTypeDesc;
	}

	public Boolean getDeleted() {
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

	public Short getAllergicReactionTypeID() {
		return allergicReactionTypeID;
	}
	
	
}
