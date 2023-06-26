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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_optionalvaccination")
public class OptionalVaccinations {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "OptionalVaccinationID")
	@Expose
	private Short vaccineID;
	
	@Column(name = "OptionalVaccinationTime")
	@Expose
	private String vaccinationTime;
	
	@Column(name = "OptionalVaccineName")
	@Expose
	private String vaccineName;
	
	@Column(name = "OptionalVaccineDesc")
	@Expose
	private String vaccineDesc;

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
	
	@Expose
	@Column(name = "Sctcode")
	private String sctCode;
	
	@Expose
	@Column(name = "SctTerm")
	private String sctTerm;

	public Short getVaccineID() {
		return vaccineID;
	}

	public void setVaccineID(Short vaccineID) {
		this.vaccineID = vaccineID;
	}

	public String getVaccinationTime() {
		return vaccinationTime;
	}

	public void setVaccinationTime(String vaccinationTime) {
		this.vaccinationTime = vaccinationTime;
	}

	public String getVaccineName() {
		return vaccineName;
	}

	public void setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
	}

	public String getVaccineDesc() {
		return vaccineDesc;
	}

	public void setVaccineDesc(String vaccineDesc) {
		this.vaccineDesc = vaccineDesc;
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
	
	public OptionalVaccinations(Short vaccineID, String vaccineName,String sctCode,String sctTerm) {
		super();
		this.vaccineID = vaccineID;
		this.vaccineName = vaccineName;
		this.sctCode = sctCode;
		this.sctTerm = sctTerm;
	}

	public static ArrayList<OptionalVaccinations> getOptionalVaccinations(ArrayList<Object[]> resList) {
		ArrayList<OptionalVaccinations> resArray = new ArrayList<OptionalVaccinations>();
		for (Object[] obj : resList) {
			OptionalVaccinations cOBJ = new OptionalVaccinations((Short)obj[0], (String)obj[1],(String)obj[2],(String)obj[3]);
			resArray.add(cOBJ);
		}
		return resArray;
	}

	public String getSctCode() {
		return sctCode;
	}

	public void setSctCode(String sctCode) {
		this.sctCode = sctCode;
	}

	public String getSctTerm() {
		return sctTerm;
	}

	public void setSctTerm(String sctTerm) {
		this.sctTerm = sctTerm;
	}
	
}
