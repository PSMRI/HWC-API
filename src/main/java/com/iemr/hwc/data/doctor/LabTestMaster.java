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
@Table(name = "m_labtests")
public class LabTestMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	@Column(name = "TestID")
	private Integer testID;
	
	@Expose
	@Column(name = "TestName")
	private String testName;
	
	@Expose
	@Column(name = "TestDesc")
	private String testDesc;
	
	@Expose
	@Column(name = "TestFor")
	private String testFor;
	
	
	@Expose
	@Column(name = "IsRadiologyImaging")
	private Boolean isRadiologyImaging;
	
	@Expose
	@Column(name = "Deleted", insertable = false, updatable = false)
	private Boolean deleted;
	
	@Expose
	@Column(name = "Processed", insertable = false, updatable = false)
	private Character processed;
	
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

	public LabTestMaster() {
	}
	
	public LabTestMaster(Integer testID, String testName, String testDesc, String testFor, Boolean isRadiologyImaging,
			Boolean deleted, Character processed, String createdBy, Timestamp createdDate, String modifiedBy,
			Timestamp lastModDate) {
		super();
		this.testID = testID;
		this.testName = testName;
		this.testDesc = testDesc;
		this.testFor = testFor;
		this.isRadiologyImaging = isRadiologyImaging;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
	}

	public LabTestMaster(Integer testID, String testName, Boolean isRadiologyImaging) {
		super();
		this.testID = testID;
		this.testName = testName;
		this.isRadiologyImaging = isRadiologyImaging;
	}
	
	public LabTestMaster(Integer testID, String testName) {
		super();
		this.testID = testID;
		this.testName = testName;
	}

	public static ArrayList<LabTestMaster> getLabTestMasters(ArrayList<Object[]> resList) {
		ArrayList<LabTestMaster> resArray = new ArrayList<LabTestMaster>();
		for (Object[] obj : resList) {
			LabTestMaster cOBJ = new LabTestMaster((Integer)obj[0], (String)obj[1], (Boolean)obj[2]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
	
	public static ArrayList<LabTestMaster> getNCDScreeningTests(ArrayList<Object[]> resList) {
		ArrayList<LabTestMaster> resArray = new ArrayList<LabTestMaster>();
		for (Object[] obj : resList) {
			LabTestMaster cOBJ = new LabTestMaster((Integer)obj[0], (String)obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
	
	public Integer getTestID() {
		return testID;
	}

	public void setTestID(Integer testID) {
		this.testID = testID;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestDesc() {
		return testDesc;
	}

	public void setTestDesc(String testDesc) {
		this.testDesc = testDesc;
	}

	public Boolean getIsRadiologyImaging() {
		return isRadiologyImaging;
	}

	public void setIsRadiologyImaging(Boolean isRadiologyImaging) {
		this.isRadiologyImaging = isRadiologyImaging;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Character getProcessed() {
		return processed;
	}

	public void setProcessed(Character processed) {
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

	public String getTestFor() {
		return testFor;
	}

	public void setTestFor(String testFor) {
		this.testFor = testFor;
	}
	
	

}
