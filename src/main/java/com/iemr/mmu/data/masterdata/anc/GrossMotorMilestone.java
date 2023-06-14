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
package com.iemr.mmu.data.masterdata.anc;

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
@Table(name = "m_grossmotormilestone")
public class GrossMotorMilestone {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "GMMilestoneID")
	private Short gMMilestoneID;
	
	@Expose
	@Column(name = "GMMilestone")
	private String gMMilestone;
	
	@Expose
	@Column(name = "GMMilestoneDesc")
	private String gMMilestoneDesc;
	
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

	public GrossMotorMilestone(Short gMMilestoneID, String gMMilestone, String gMMilestoneDesc) {
		super();
		this.gMMilestoneID = gMMilestoneID;
		this.gMMilestone = gMMilestone;
		this.gMMilestoneDesc = gMMilestoneDesc;
	}
	
	public static ArrayList<GrossMotorMilestone> getGrossMotorMilestone(ArrayList<Object[]> resList) {
		ArrayList<GrossMotorMilestone> resArray = new ArrayList<GrossMotorMilestone>();
		for (Object[] obj : resList) {
			GrossMotorMilestone cOBJ = new GrossMotorMilestone((Short)obj[0], (String)obj[1], (String)obj[2]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
	
}
