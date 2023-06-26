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
package com.iemr.hwc.data.nurse;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.iemr.hwc.annotation.sqlInjectionSafe.SQLInjectionSafe;

@Entity
@Table(name = "t_phy_vitals")
public class BenPhysicalVitalDetail {

	@Id
	@GeneratedValue
	@Expose
	@Column(name = "ID")
	private Long ID;
	
	@Expose
	@Column(name = "BeneficiaryRegID")
	private Long beneficiaryRegID;

	@Expose
	@Column(name = "BenVisitID")
	private Long benVisitID;

	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;

	@Expose
	@Column(name = "VisitCode")
	private Long visitCode;

	@Expose
	@Column(name = "Temperature")
	private Double temperature;

	@Expose
	@Column(name = "PulseRate")
	private Short pulseRate;

	@Expose
	@Column(name = "RespiratoryRate")
	private Short respiratoryRate;

	@Expose
	@Column(name = "SystolicBP_1stReading")
	private Short systolicBP_1stReading;

	@Expose
	@Column(name = "DiastolicBP_1stReading")
	private Short diastolicBP_1stReading;

	@Expose
	@Column(name = "SystolicBP_2ndReading")
	private Short systolicBP_2ndReading;

	@Expose
	@Column(name = "DiastolicBP_2ndReading")
	private Short diastolicBP_2ndReading;

	@Expose
	@Column(name = "SystolicBP_3rdReading")
	private Short systolicBP_3rdReading;

	@Expose
	@Column(name = "DiastolicBP_3rdReading")
	private Short diastolicBP_3rdReading;

	@Expose
	@Column(name = "BloodPressureStatusID")
	private Short bloodPressureStatusID;

	@Expose
	@Column(name = "BloodPressureStatus")
	private @SQLInjectionSafe String bloodPressureStatus;

	@Expose
	@Column(name = "BloodGlucose_Fasting")
	private Short bloodGlucose_Fasting;

	@Expose
	@Column(name = "BloodGlucose_Random")
	private Short bloodGlucose_Random;

	@Expose
	@Column(name = "BloodGlucose_2hr_PP")
	private Short bloodGlucose_2hr_PP;

	@Expose
	@Column(name = "BloodGlucose_NotSpecified")
	private Short bloodGlucose_NotSpecified;

	@Expose
	@Column(name = "DiabeticStatusID")
	private Short diabeticStatusID;

	@Expose
	@Column(name = "DiabeticStatus")
	private @SQLInjectionSafe String diabeticStatus;

	@Expose
	@Column(name = "AverageSystolicBP")
	private Short averageSystolicBP;

	@Expose
	@Column(name = "AverageDiastolicBP")
	private Short averageDiastolicBP;

	@Expose
	@Column(name = "CapillaryRefillTime")
	private @SQLInjectionSafe String capillaryRefillTime;
	

	@Expose
	@Column(name = "rbs")
	private String rbsTestResult;

	@Expose
	@Column(name = "rbs_remarks")
	private String rbsTestRemarks;

	@Expose
	@Column(name = "spo2")
	private String sPO2;
	
	@Expose
	@Column(name = "Hemoglobin")
	private Double hemoglobin;

	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private @SQLInjectionSafe String processed;

	@Expose
	@Column(name = "CreatedBy")
	private @SQLInjectionSafe String createdBy;

	@Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;

	@Expose
	@Column(name = "ModifiedBy")
	private @SQLInjectionSafe String modifiedBy;

	@Expose
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	@Expose
	@Column(name = "VanSerialNo")
	private Long vanSerialNo;

	@Expose
	@Column(name = "VehicalNo")
	private @SQLInjectionSafe String vehicalNo;

	@Expose
	@Column(name = "vanID")
	private Integer vanID;

	@Expose
	@Column(name = "ParkingPlaceID")
	private Integer parkingPlaceID;

	@Expose
	@Column(name = "SyncedBy")
	private @SQLInjectionSafe String syncedBy;

	@Expose
	@Column(name = "SyncedDate")
	private Timestamp syncedDate;

	@Expose
	@Column(name = "ReservedForChange")
	private @SQLInjectionSafe String reservedForChange;

	public BenPhysicalVitalDetail() {
		super();
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
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

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Short getPulseRate() {
		return pulseRate;
	}

	public void setPulseRate(Short pulseRate) {
		this.pulseRate = pulseRate;
	}

	public Short getRespiratoryRate() {
		return respiratoryRate;
	}

	public void setRespiratoryRate(Short respiratoryRate) {
		this.respiratoryRate = respiratoryRate;
	}

	public Short getSystolicBP_1stReading() {
		return systolicBP_1stReading;
	}

	public void setSystolicBP_1stReading(Short systolicBP_1stReading) {
		this.systolicBP_1stReading = systolicBP_1stReading;
	}

	public Short getDiastolicBP_1stReading() {
		return diastolicBP_1stReading;
	}

	public void setDiastolicBP_1stReading(Short diastolicBP_1stReading) {
		this.diastolicBP_1stReading = diastolicBP_1stReading;
	}

	public Short getSystolicBP_2ndReading() {
		return systolicBP_2ndReading;
	}

	public void setSystolicBP_2ndReading(Short systolicBP_2ndReading) {
		this.systolicBP_2ndReading = systolicBP_2ndReading;
	}

	public Short getDiastolicBP_2ndReading() {
		return diastolicBP_2ndReading;
	}

	public void setDiastolicBP_2ndReading(Short diastolicBP_2ndReading) {
		this.diastolicBP_2ndReading = diastolicBP_2ndReading;
	}

	public Short getSystolicBP_3rdReading() {
		return systolicBP_3rdReading;
	}

	public void setSystolicBP_3rdReading(Short systolicBP_3rdReading) {
		this.systolicBP_3rdReading = systolicBP_3rdReading;
	}

	public Short getDiastolicBP_3rdReading() {
		return diastolicBP_3rdReading;
	}

	public void setDiastolicBP_3rdReading(Short diastolicBP_3rdReading) {
		this.diastolicBP_3rdReading = diastolicBP_3rdReading;
	}

	public Short getBloodPressureStatusID() {
		return bloodPressureStatusID;
	}

	public void setBloodPressureStatusID(Short bloodPressureStatusID) {
		this.bloodPressureStatusID = bloodPressureStatusID;
	}

	public String getBloodPressureStatus() {
		return bloodPressureStatus;
	}

	public void setBloodPressureStatus(String bloodPressureStatus) {
		this.bloodPressureStatus = bloodPressureStatus;
	}

	public Short getBloodGlucose_Fasting() {
		return bloodGlucose_Fasting;
	}

	public void setBloodGlucose_Fasting(Short bloodGlucose_Fasting) {
		this.bloodGlucose_Fasting = bloodGlucose_Fasting;
	}

	public Short getBloodGlucose_Random() {
		return bloodGlucose_Random;
	}

	public void setBloodGlucose_Random(Short bloodGlucose_Random) {
		this.bloodGlucose_Random = bloodGlucose_Random;
	}

	public Short getBloodGlucose_2hr_PP() {
		return bloodGlucose_2hr_PP;
	}

	public void setBloodGlucose_2hr_PP(Short bloodGlucose_2hr_PP) {
		this.bloodGlucose_2hr_PP = bloodGlucose_2hr_PP;
	}

	public Short getBloodGlucose_NotSpecified() {
		return bloodGlucose_NotSpecified;
	}

	public void setBloodGlucose_NotSpecified(Short bloodGlucose_NotSpecified) {
		this.bloodGlucose_NotSpecified = bloodGlucose_NotSpecified;
	}

	public Short getDiabeticStatusID() {
		return diabeticStatusID;
	}

	public void setDiabeticStatusID(Short diabeticStatusID) {
		this.diabeticStatusID = diabeticStatusID;
	}

	public String getDiabeticStatus() {
		return diabeticStatus;
	}

	public void setDiabeticStatus(String diabeticStatus) {
		this.diabeticStatus = diabeticStatus;
	}

	public Short getAverageSystolicBP() {
		return averageSystolicBP;
	}

	public void setAverageSystolicBP(Short averageSystolicBP) {
		this.averageSystolicBP = averageSystolicBP;
	}

	public Short getAverageDiastolicBP() {
		return averageDiastolicBP;
	}

	public void setAverageDiastolicBP(Short averageDiastolicBP) {
		this.averageDiastolicBP = averageDiastolicBP;
	}

	public String getCapillaryRefillTime() {
		return capillaryRefillTime;
	}

	public void setCapillaryRefillTime(String capillaryRefillTime) {
		this.capillaryRefillTime = capillaryRefillTime;
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

	public Long getVanSerialNo() {
		return vanSerialNo;
	}

	public void setVanSerialNo(Long vanSerialNo) {
		this.vanSerialNo = vanSerialNo;
	}

	public String getVehicalNo() {
		return vehicalNo;
	}

	public void setVehicalNo(String vehicalNo) {
		this.vehicalNo = vehicalNo;
	}

	public Integer getParkingPlaceID() {
		return parkingPlaceID;
	}

	public void setParkingPlaceID(Integer parkingPlaceID) {
		this.parkingPlaceID = parkingPlaceID;
	}

	public String getSyncedBy() {
		return syncedBy;
	}

	public void setSyncedBy(String syncedBy) {
		this.syncedBy = syncedBy;
	}

	public Timestamp getSyncedDate() {
		return syncedDate;
	}

	public void setSyncedDate(Timestamp syncedDate) {
		this.syncedDate = syncedDate;
	}

	public String getReservedForChange() {
		return reservedForChange;
	}

	public void setReservedForChange(String reservedForChange) {
		this.reservedForChange = reservedForChange;
	}

	public String getRbsTestResult() {
		return rbsTestResult;
	}

	public void setRbsTestResult(String rbsTestResult) {
		this.rbsTestResult = rbsTestResult;
	}

	public String getRbsTestRemarks() {
		return rbsTestRemarks;
	}

	public void setRbsTestRemarks(String rbsTestRemarks) {
		this.rbsTestRemarks = rbsTestRemarks;
	}

	public String getsPO2() {
		return sPO2;
	}

	public void setsPO2(String sPO2) {
		this.sPO2 = sPO2;
	}

	public Double getHemoglobin() {
		return hemoglobin;
	}

	public void setHemoglobin(Double hemoglobin) {
		this.hemoglobin = hemoglobin;
	}

	
	

}
