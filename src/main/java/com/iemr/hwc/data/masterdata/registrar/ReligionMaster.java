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
package com.iemr.hwc.data.masterdata.registrar;

import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_religion")
public class ReligionMaster {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "ReligionID")
	private Short religionID;
	@Expose
	@Column(name = "ReligionType")
	private String religionType;
	@Expose
	@Column(name = "ReligionDesc")
	private String religionDesc;
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;

	public ReligionMaster() {
	}

	public ReligionMaster(Short religionID, String religionType) {
		this.religionID = religionID;
		this.religionType = religionType;
	}

	public static ArrayList<ReligionMaster> getReligionMasterData(ArrayList<Object[]> resList) {
		ArrayList<ReligionMaster> resArray = new ArrayList<>();
		for (Object[] obj : resList) {
			ReligionMaster cOBJ = new ReligionMaster((Short) obj[0], (String) obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
}
