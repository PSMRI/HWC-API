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
package com.iemr.hwc.data.labModule;

import java.sql.Timestamp;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_testcomponent")
public class TestComponentMaster {

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TestComponentID")
	private Integer testComponentID;
	@Expose
	@Column(name = "TestComponentName")
	private String testComponentName;
	@Expose
	@Column(name = "TestComponentDesc")
	private String testComponentDesc;
	@Expose
	@Column(name = "InputType")
	private String inputType;
	@Expose
	@Column(name = "MeasurementUnit")
	private String measurementUnit;
	@Expose
	@Column(name = "Range_min")
	private Integer range_min;
	@Expose
	@Column(name = "Range_normal_min")
	private Integer range_normal_min;
	@Expose
	@Column(name = "Range_normal_max")
	private Integer range_normal_max;
	@Expose
	@Column(name = "Range_max")
	private Integer range_max;
	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;
	@Expose
	@Column(name = "Deleted", insertable = false)
	private Boolean deleted;
	@Expose
	@Column(name = "Processed", insertable = false)
	private String processed;
	@Expose
	@Column(name = "CreatedBy")
	private String createdBy;
	@Expose
	@Column(name = "CreatedDate", insertable = false)
	private Timestamp createdDate;
	@Expose
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	@Expose
	@Column(name = "LastModDate", insertable = false)
	private Timestamp lastModDate;
	@Expose
	@Column(name = "Loinc_Num")
	private String lionicNum;
	@Expose
	@Column(name = "loinc_Component")
	private String lionicTerm;
	public String getLionicTerm() {
		return lionicTerm;
	}

	public void setLionicTerm(String lionicTerm) {
		this.lionicTerm = lionicTerm;
	}

	@Expose
	@OneToMany(mappedBy = "testComponentMaster", cascade = CascadeType.ALL)
	private Set<LabResultEntry> labResultEntry;
//	@Expose
//	@ManyToOne
//	@JoinColumn(name = "lionicNum", insertable = false)
//	private Loinc loinc;
//	public Integer getTestComponentID() {
//		return testComponentID;
//	}
//
//	public Loinc getLoinc() {
//		return loinc;
//	}
//
//	public void setLoinc(Loinc loinc) {
//		this.loinc = loinc;
//	}

	public void setTestComponentID(Integer testComponentID) {
		this.testComponentID = testComponentID;
	}

	public String getTestComponentName() {
		return testComponentName;
	}

	public void setTestComponentName(String testComponentName) {
		this.testComponentName = testComponentName;
	}

	public String getTestComponentDesc() {
		return testComponentDesc;
	}

	public void setTestComponentDesc(String testComponentDesc) {
		this.testComponentDesc = testComponentDesc;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getMeasurementUnit() {
		return measurementUnit;
	}

	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}

	public Integer getRange_min() {
		return range_min;
	}

	public void setRange_min(Integer range_min) {
		this.range_min = range_min;
	}

	public Integer getRange_normal_min() {
		return range_normal_min;
	}

	public void setRange_normal_min(Integer range_normal_min) {
		this.range_normal_min = range_normal_min;
	}

	public Integer getRange_normal_max() {
		return range_normal_max;
	}

	public void setRange_normal_max(Integer range_normal_max) {
		this.range_normal_max = range_normal_max;
	}

	public Integer getRange_max() {
		return range_max;
	}

	public void setRange_max(Integer range_max) {
		this.range_max = range_max;
	}

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getProcessed() {
		return processed;
	}

	public void setProcessed(String processed) {
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

	public String getLionicNum() {
		return lionicNum;
	}

	public void setLionicNum(String lionicNum) {
		this.lionicNum = lionicNum;
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

	public Set<LabResultEntry> getLabResultEntry() {
		return labResultEntry;
	}

	public void setLabResultEntry(Set<LabResultEntry> labResultEntry) {
		this.labResultEntry = labResultEntry;
	}

}
