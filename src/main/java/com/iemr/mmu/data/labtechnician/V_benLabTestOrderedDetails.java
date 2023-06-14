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
package com.iemr.mmu.data.labtechnician;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "v_benprocedurecomponentdetails")
public class V_benLabTestOrderedDetails {
	@Id
	private String id;
	@Expose
	@Column(name = "BeneficiaryRegID")
	private Long beneficiaryRegID;
	@Expose
	@Column(name = "BenVisitID")
	private Long benVisitID;
	@Expose
	@Column(name = "VisitCode")
	private Long visitCode;
	@Expose
	@Column(name = "PrescriptionID")
	private Long prescriptionID;
	@Expose
	@Column(name = "ProcedureID")
	private Integer procedureID;
	@Expose
	@Column(name = "CalibrationStartAPI")
	private String calibrationStartAPI;
	@Expose
	@Column(name = "CalibrationStatusAPI")
	private String calibrationStatusAPI;
	@Expose
	@Column(name = "CalibrationEndAPI")
	private String calibrationEndAPI;
	@Expose
	@Column(name = "ProcedureName")
	private String procedureName;
	@Expose
	@Column(name = "ProcedureDesc")
	private String procedureDesc;
	@Expose
	@Column(name = "ProcedureType")
	private String procedureType;
	@Expose
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
	@Column(name = "Range_max")
	private Integer range_max;
	@Expose
	@Column(name = "Range_normal_min")
	private Integer range_normal_min;
	@Expose
	@Column(name = "Range_normal_max")
	private Integer range_normal_max;
	@Expose
	@Column(name = "ResultValue")
	private String resultValue;

	@Expose
	@Column(name = "isDecimal")
	private Boolean isDecimal;

	@Expose
	@Column(name = "IOTProcedureName")
	private String iotProcedureName;

	@Expose
	@Column(name = "ProcedureCode")
	private String procedureCode;

	@Expose
	@Column(name = "ProcedureStartAPI")
	private String ProcedureStartAPI;

	@Expose
	@Column(name = "ProcedureEndAPI")
	private String ProcedureEndAPI;
	
	@Expose
	@Column(name = "ProcedureStatusAPI")
	private String ProcedureStatusAPI;
	
	@Expose
	@Column(name = "isLabProcedure")
	private Boolean isLabProcedure;
	
	@Expose
	@Column(name = "DiscoveryCode")
	private String DiscoveryCode;
	
	@Expose
	@Column(name = "IOTComponentName")
	private String IOTComponentName;
	
	@Expose
	@Column(name = "ComponentCode")
	private String ComponentCode;
	
	@Expose
	@Column(name = "IOTProcedureID")
	private String IOTProcedureID;
	
	@Expose
	@Column(name = "ComponentUnit")
	private String ComponentUnit;

	@Expose
	@Column(name = "isMandatory")
	private Boolean isMandatory;
	
	public Boolean getIsMandatory() {
		return isMandatory;
	}

	public String getCalibrationStartAPI() {
		return calibrationStartAPI;
	}

	public void setCalibrationStartAPI(String calibrationStartAPI) {
		this.calibrationStartAPI = calibrationStartAPI;
	}

	public String getCalibrationStatusAPI() {
		return calibrationStatusAPI;
	}

	public void setCalibrationStatusAPI(String calibrationStatusAPI) {
		this.calibrationStatusAPI = calibrationStatusAPI;
	}

	public String getCalibrationEndAPI() {
		return calibrationEndAPI;
	}

	public void setCalibrationEndAPI(String calibrationEndAPI) {
		this.calibrationEndAPI = calibrationEndAPI;
	}

	public void setIsMandatory(Boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getIsDecimal() {
		return isDecimal;
	}

	public void setIsDecimal(Boolean isDecimal) {
		this.isDecimal = isDecimal;
	}

	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	public Long getBenVisitID() {
		return benVisitID;
	}

	public void setBenVisitID(Long benVisitID) {
		this.benVisitID = benVisitID;
	}

	public Long getPrescriptionID() {
		return prescriptionID;
	}

	public void setPrescriptionID(Long prescriptionID) {
		this.prescriptionID = prescriptionID;
	}

	public Integer getProcedureID() {
		return procedureID;
	}

	public void setProcedureID(Integer procedureID) {
		this.procedureID = procedureID;
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	public String getProcedureDesc() {
		return procedureDesc;
	}

	public void setProcedureDesc(String procedureDesc) {
		this.procedureDesc = procedureDesc;
	}

	public String getProcedureType() {
		return procedureType;
	}

	public void setProcedureType(String procedureType) {
		this.procedureType = procedureType;
	}

	public Integer getTestComponentID() {
		return testComponentID;
	}

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

	public Integer getRange_max() {
		return range_max;
	}

	public void setRange_max(Integer range_max) {
		this.range_max = range_max;
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

	public String getResultValue() {
		return resultValue;
	}

	public void setResultValue(String resultValue) {
		this.resultValue = resultValue;
	}

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

	public String getIotProcedureName() {
		return iotProcedureName;
	}

	public void setIotProcedureName(String iotProcedureName) {
		this.iotProcedureName = iotProcedureName;
	}

	public String getProcedureCode() {
		return procedureCode;
	}

	public void setProcedureCode(String procedureCode) {
		this.procedureCode = procedureCode;
	}

	public String getProcedureStartAPI() {
		return ProcedureStartAPI;
	}

	public void setProcedureStartAPI(String procedureStartAPI) {
		ProcedureStartAPI = procedureStartAPI;
	}

	public String getProcedureEndAPI() {
		return ProcedureEndAPI;
	}

	public void setProcedureEndAPI(String procedureEndAPI) {
		ProcedureEndAPI = procedureEndAPI;
	}

	public String getProcedureStatusAPI() {
		return ProcedureStatusAPI;
	}

	public void setProcedureStatusAPI(String procedureStatusAPI) {
		ProcedureStatusAPI = procedureStatusAPI;
	}

	public Boolean getIsLabProcedure() {
		return isLabProcedure;
	}

	public void setIsLabProcedure(Boolean isLabProcedure) {
		this.isLabProcedure = isLabProcedure;
	}

	public String getDiscoveryCode() {
		return DiscoveryCode;
	}

	public void setDiscoveryCode(String discoveryCode) {
		DiscoveryCode = discoveryCode;
	}

	public String getIOTComponentName() {
		return IOTComponentName;
	}

	public void setIOTComponentName(String iOTComponentName) {
		IOTComponentName = iOTComponentName;
	}

	public String getComponentCode() {
		return ComponentCode;
	}

	public void setComponentCode(String componentCode) {
		ComponentCode = componentCode;
	}

	public String getIOTProcedureID() {
		return IOTProcedureID;
	}

	public void setIOTProcedureID(String iOTProcedureID) {
		IOTProcedureID = iOTProcedureID;
	}

	public String getComponentUnit() {
		return ComponentUnit;
	}

	public void setComponentUnit(String componentUnit) {
		ComponentUnit = componentUnit;
	}

	
}
