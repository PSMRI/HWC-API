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
package com.iemr.hwc.data.masterdata.nurse;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_cancerdiseasetype")
public class CancerDiseaseType {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "CancerDiseaseTypeID")
	private Short cancerDiseaseTypeID;
	
	@Expose
	@Column(name = "CancerDiseaseType")
	private String cancerDiseaseType;
	
	@Expose
	@Column(name = "CancerDiseaseTypeDesc")
	private String cancerDiseaseTypeDesc;
	
	@Expose
	@Column(name = "Gender")
	private String gender;
	
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;

	@Expose
	@Column(name = "Sctcode")
	private String snomedCode;
	
	@Expose
	@Column(name = "SctTerm")
	private String snomedTerm;
	
	public String getSnomedCode() {
		return snomedCode;
	}

	public void setSnomedCode(String snomedCode) {
		this.snomedCode = snomedCode;
	}

	public String getSnomedTerm() {
		return snomedTerm;
	}

	public void setSnomedTerm(String snomedTerm) {
		this.snomedTerm = snomedTerm;
	}

	public CancerDiseaseType(Short cancerDiseaseTypeID, String cancerDiseaseType, String gender, String snomedCode, String snomedTerm) {
		super();
		this.cancerDiseaseTypeID = cancerDiseaseTypeID;
		this.cancerDiseaseType = cancerDiseaseType;
		this.gender = gender;
		this.snomedCode = snomedCode;
		this.snomedTerm = snomedTerm;
	}
	
	public static ArrayList<CancerDiseaseType> getCancerDiseaseTypeMasterData(ArrayList<Object[]> resList) {
		ArrayList<CancerDiseaseType> resArray = new ArrayList<>();
		for (Object[] obj : resList) {
			CancerDiseaseType cOBJ = new CancerDiseaseType((Short) obj[0], (String) obj[1],  (String) obj[2], (String) obj[3], (String) obj[4]);
			resArray.add(cOBJ);
		}
		return resArray;
	}

	
}
