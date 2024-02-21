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
package com.iemr.hwc.data.foetalmonitor;

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
@Table(name = "m_fetosensetests")
public class FoetalMonitorTestMaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "fetosensetestid")
	private Integer foetalMonitorTestId;
	
	@Expose
	@Column(name = "TestName") 
	private String testName;
	
	@Expose
    @Column(name = "TestDesc") 
    private String testDesc;
	
	
	
	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;
	
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

	

	
	public FoetalMonitorTestMaster(Integer foetalMonitorTestId, String testName) {
		super();
		this.foetalMonitorTestId = foetalMonitorTestId;
		this.testName = testName;
	}

	
	public static ArrayList<FoetalMonitorTestMaster> getFoetalMonitorMasters(ArrayList<Object[]> resList) {
		ArrayList<FoetalMonitorTestMaster> resArray = new ArrayList<FoetalMonitorTestMaster>();
		for (Object[] obj : resList) {
			FoetalMonitorTestMaster foetalMonitorOBJ = new FoetalMonitorTestMaster((Integer)obj[0], (String)obj[1]);
			resArray.add(foetalMonitorOBJ);
		}
		return resArray;
	}
	
	

}
