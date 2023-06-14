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
package com.iemr.mmu.data.masterdata.nurse;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_visitreason")
public class VisitReason {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "VisitReasonID")
	private Short visitReasonID;
	
	@Expose
	@Column(name = "VisitReason")
	private String visitReason;
	
	@Expose
	@Column(name = "VisitReasonDesc")
	private String visitReasonDesc;
	
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;

	public VisitReason(Short visitReasonID, String visitReason) {
		super();
		this.visitReasonID = visitReasonID;
		this.visitReason = visitReason;
	}

	public static ArrayList<VisitReason> getVisitReasonMasterData(ArrayList<Object[]> resList) {
		ArrayList<VisitReason> resArray = new ArrayList<>();
		for (Object[] obj : resList) {
			VisitReason cOBJ = new VisitReason((Short) obj[0], (String) obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}

}
