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
package com.iemr.mmu.data.masterdata.registrar;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_beneficiaryoccupation")
public class OccupationMaster {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "OccupationID")
	private Short occupationID;
	@Expose
	@Column(name = "OccupationType")
	private String occupationType;
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;

	public OccupationMaster() {
	}

	public OccupationMaster(Short occupationID, String occupationType) {
		this.occupationID = occupationID;
		this.occupationType = occupationType;
	}

	public static ArrayList<OccupationMaster> getOccupationMasterData(ArrayList<Object[]> resList) {
		ArrayList<OccupationMaster> resArray = new ArrayList<>();
		for (Object[] obj : resList) {
			OccupationMaster cOBJ = new OccupationMaster((Short) obj[0], (String) obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
}
