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

@Entity
@Table(name = "m_menstrualcyclestatus")
public class MenstrualCycleStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "MenstrualCycleStatusID")
	private Short menstrualCycleStatusID;

	@Expose
	@Column(name = "Name")
	private String name;

	@Expose
	@Column(name = "MenstrualCycleStatusDesc")
	private String menstrualCycleStatusDesc;

	@Expose
	@Column(name = "VisitCategoryID")
	private Integer visitCategoryID;

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

	public MenstrualCycleStatus(Short menstrualCycleStatusID, String name, String menstrualCycleStatusDesc) {
		super();
		this.menstrualCycleStatusID = menstrualCycleStatusID;
		this.name = name;
		this.menstrualCycleStatusDesc = menstrualCycleStatusDesc;
	}

	public static ArrayList<MenstrualCycleStatus> getMenstrualCycleStatuses(ArrayList<Object[]> resList) {
		ArrayList<MenstrualCycleStatus> resArray = new ArrayList<MenstrualCycleStatus>();
		for (Object[] obj : resList) {
			MenstrualCycleStatus cOBJ = new MenstrualCycleStatus((Short) obj[0], (String) obj[1], (String) obj[2]);
			resArray.add(cOBJ);
		}
		return resArray;
	}

}
