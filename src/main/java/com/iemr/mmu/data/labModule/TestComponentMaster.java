/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.labModule;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
