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
package com.iemr.hwc.data.doctor;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "temp_masterdrug")
public class TempMasterDrug {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "DrugID")
	private Integer drugID;

	@Expose
	@Column(name = "DrugName")
	private String drugName;

	@Expose
	@Column(name = "DrugDisplayName")
	private String drugDisplayName;

	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;

	public TempMasterDrug() {
	}

	public TempMasterDrug(Integer drugID, String drugDisplayName) {
		super();
		this.drugID = drugID;
		this.drugDisplayName = drugDisplayName;
	}

	public static ArrayList<TempMasterDrug> getTempDrugMasterList(ArrayList<TempMasterDrug> tempMasterDrugList) {
		ArrayList<TempMasterDrug> tempMasterDrugListOBJ = new ArrayList<>();
		for (TempMasterDrug drugMaster : tempMasterDrugList) {
			TempMasterDrug obj = new TempMasterDrug(drugMaster.getDrugID(), drugMaster.getDrugDisplayName());
			tempMasterDrugListOBJ.add(obj);
		}
		return tempMasterDrugListOBJ;
	}

	public TempMasterDrug(Integer drugID, String drugName, String drugDisplayName, Boolean deleted) {
		super();
		this.drugID = drugID;
		this.drugName = drugName;
		this.drugDisplayName = drugDisplayName;
		this.deleted = deleted;
	}

	public Integer getDrugID() {
		return drugID;
	}

	public void setDrugID(Integer drugID) {
		this.drugID = drugID;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getDrugDisplayName() {
		return drugDisplayName;
	}

	public void setDrugDisplayName(String drugDisplayName) {
		this.drugDisplayName = drugDisplayName;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

}
