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

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_benrelationshiptype")
public class FamilyMemberType {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "BenRelationshipID")
	private Short benRelationshipID;
	
	@Expose
	@Column(name = "BenRelationshipType")
	private String benRelationshipType;
	
	@Expose
	@Column(name = "Gender")
	private String gender;
	
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;

	public FamilyMemberType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FamilyMemberType(Short benRelationshipID, String benRelationshipType, String gender) {
		super();
		this.benRelationshipID = benRelationshipID;
		this.benRelationshipType = benRelationshipType;
		this.gender = gender;
	}
	
	public static ArrayList<FamilyMemberType> getFamilyMemberTypeMasterData(ArrayList<Object[]> resList) {
		ArrayList<FamilyMemberType> resArray = new ArrayList<>();
		for (Object[] obj : resList) {
			FamilyMemberType cOBJ = new FamilyMemberType((Short) obj[0], (String) obj[1], (String) obj[2]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
}
