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
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.Expose;

@Entity
@Table(name = "t_fetosensedata")
public class FoetalMonitor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "fetosenseID")
	private Long partnerFoetalMonitorId;

	@Expose
	@Column(name = "BeneficiaryRegID")
	private Long beneficiaryRegID;

	@Expose
	@Column(name = "BeneficiaryID")
	private Long beneficiaryID;

	@Expose
	@Column(name = "benFlowID")
	private Long benFlowID;

	@Expose
	@Column(name = "visitcode")
	private Long visitCode;

	@Expose
	@Column(name = "testtime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Timestamp testTime;

	@Expose
	@Column(name = "motherLMPDate")
	private Timestamp motherLMPDate;

	@Expose
	@Column(name = "MotherName")
	private String motherName;

	@Expose
	@Column(name = "partnerName")
	private String partnerName;

	@Expose
	@Column(name = "fetosenseMotherID")
	private String cmMotherId;

	@Expose
	@Column(name = "fetosensePartnerID")
	private String partnerId;

	@Expose
	@Column(name = "fetosensetestid")
	private Long foetalMonitorTestId;

	@Expose
	@Column(name = "resultState", insertable = false, updatable = true)
	private Boolean resultState;

	@Expose
	@Column(name = "testId")
	private String testId;

	@Expose
	@Column(name = "testName")
	private String testName;

	@Expose
	@Column(name = "deviceId")
	private String deviceId;

	@Expose
	@Column(name = "testDoneAt")
	private String testDoneAt;

	@Expose
	@Column(name = "lengthOfTest")
	private Integer lengthOfTest;

	@Expose
	@Column(name = "basalHeartRate")
	private Integer basalHeartRate;

	@Expose
	@Column(name = "accelerationsList")
	private String accelerationsListDB;

	@Expose
	@Column(name = "decelerationsList")
	private String decelerationsListDB;

	@Expose
	@Column(name = "shortTermVariationBpm")
	private String shortTermVariationBpm;

	@Expose
	@Column(name = "shortTermVariationMilli")
	private Integer shortTermVariationMilli;

	@Expose
	@Column(name = "longTermVariation")
	private Integer longTermVariation;

	@Expose
	@Column(name = "movementEntries")
	private String movementEntriesDB;

	@Expose
	@Column(name = "autoFetalMovement")
	private String autoFetalMovementDB;

	@Expose
	@Column(name = "reportPath")
	private String reportPath;

	@Expose
	@Column(name = "Deleted", insertable = false)
	private Boolean deleted;

	@Expose
	@Column(name = "Processed", insertable = false, updatable = false)
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

	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer ProviderServiceMapID;
	@Expose
	@Column(name = "vanID")
	private Integer vanID;

	@Expose
	@Transient
	private ArrayList<Object> accelerationsList;

	@Expose
	@Transient
	private ArrayList<Object> decelerationsList;

	@Expose
	@Transient
	private ArrayList<Integer> movementEntries;

	@Expose
	@Transient
	private ArrayList<Integer> autoFetalMovement;

	@Transient
	private Map<String, String> mother;

	@Expose
	@Column(name = "AMRITFilePath")
	private String aMRITFilePath;

	public FoetalMonitor() {

	}

	public FoetalMonitor(Long foetalMonitorID, Long beneficiaryRegID, Long benFlowID, Long visitCode, Long foetalMonitorTestId,
			Boolean resultState) {
		this.partnerFoetalMonitorId = foetalMonitorID;
		this.beneficiaryRegID = beneficiaryRegID;
		this.benFlowID = benFlowID;
		this.visitCode = visitCode;
		this.foetalMonitorTestId = foetalMonitorTestId;
		this.resultState = resultState;

	}

	public Long getFoetalMonitorID() {
		return partnerFoetalMonitorId;
	}

	public void setFoetalMonitorID(Long foetalMonitorID) {
		this.partnerFoetalMonitorId = foetalMonitorID;
	}

	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

	public Timestamp getTestTime() {
		return testTime;
	}

	public void setTestTime(Timestamp testTime) {
		this.testTime = testTime;
	}

	public Timestamp getMotherLMPDate() {
		return motherLMPDate;
	}

	public void setMotherLMPDate(Timestamp motherLMPDate) {
		this.motherLMPDate = motherLMPDate;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getFoetalMonitorMotherID() {
		return cmMotherId;
	}

	public void setFoetalMonitorMotherID(String foetalMonitorMotherID) {
		this.cmMotherId = foetalMonitorMotherID;
	}

	public String getFoetalMonitorPartnerID() {
		return partnerId;
	}

	public void setFoetalMonitorPartnerID(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getTestDoneAt() {
		return testDoneAt;
	}

	public void setTestDoneAt(String testDoneAt) {
		this.testDoneAt = testDoneAt;
	}

	public Integer getLengthOfTest() {
		return lengthOfTest;
	}

	public void setLengthOfTest(Integer lengthOfTest) {
		this.lengthOfTest = lengthOfTest;
	}

	public Integer getBasalHeartRate() {
		return basalHeartRate;
	}

	public void setBasalHeartRate(Integer basalHeartRate) {
		this.basalHeartRate = basalHeartRate;
	}

	public String getAccelerationsListDB() {
		return accelerationsListDB;
	}

	public void setAccelerationsListDB(String accelerationsListDB) {
		this.accelerationsListDB = accelerationsListDB;
	}

	public String getDecelerationsListDB() {
		return decelerationsListDB;
	}

	public void setDecelerationsListDB(String decelerationsListDB) {
		this.decelerationsListDB = decelerationsListDB;
	}

	public String getShortTermVariationBpm() {
		return shortTermVariationBpm;
	}

	public void setShortTermVariationBpm(String shortTermVariationBpm) {
		this.shortTermVariationBpm = shortTermVariationBpm;
	}

	public Integer getShortTermVariationMilli() {
		return shortTermVariationMilli;
	}

	public void setShortTermVariationMilli(Integer shortTermVariationMilli) {
		this.shortTermVariationMilli = shortTermVariationMilli;
	}

	public Integer getLongTermVariation() {
		return longTermVariation;
	}

	public void setLongTermVariation(Integer longTermVariation) {
		this.longTermVariation = longTermVariation;
	}

	public String getMovementEntriesDB() {
		return movementEntriesDB;
	}

	public void setMovementEntriesDB(String movementEntriesDB) {
		this.movementEntriesDB = movementEntriesDB;
	}

	public String getAutoFetalMovementDB() {
		return autoFetalMovementDB;
	}

	public void setAutoFetalMovementDB(String autoFetalMovementDB) {
		this.autoFetalMovementDB = autoFetalMovementDB;
	}

	public String getReportPath() {
		return reportPath;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
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

	public ArrayList<Object> getAccelerationsList() {
		return accelerationsList;
	}

	public void setAccelerationsList(ArrayList<Object> accelerationsList) {
		this.accelerationsList = accelerationsList;
	}

	public ArrayList<Object> getDecelerationsList() {
		return decelerationsList;
	}

	public void setDecelerationsList(ArrayList<Object> decelerationsList) {
		this.decelerationsList = decelerationsList;
	}

	public ArrayList<Integer> getMovementEntries() {
		return movementEntries;
	}

	public void setMovementEntries(ArrayList<Integer> movementEntries) {
		this.movementEntries = movementEntries;
	}

	public ArrayList<Integer> getAutoFetalMovement() {
		return autoFetalMovement;
	}

	public void setAutoFetalMovement(ArrayList<Integer> autoFetalMovement) {
		this.autoFetalMovement = autoFetalMovement;
	}

	public Map<String, String> getMother() {
		return mother;
	}

	public void setMother(Map<String, String> mother) {
		this.mother = mother;
	}

	public Integer getProviderServiceMapID() {
		return ProviderServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		ProviderServiceMapID = providerServiceMapID;
	}

	public Long getFoetalMonitorTestId() {
		return foetalMonitorTestId;
	}

	public void setFoetalMonitorTestId(Long foetalMonitorTestId) {
		this.foetalMonitorTestId = foetalMonitorTestId;
	}

	public Long getBenFlowID() {
		return benFlowID;
	}

	public void setBenFlowID(Long benFlowID) {
		this.benFlowID = benFlowID;
	}

	public Boolean getResultState() {
		return resultState;
	}

	public void setResultState(Boolean resultState) {
		this.resultState = resultState;
	}

	public String getCmMotherId() {
		return cmMotherId;
	}

	public void setCmMotherId(String cmMotherId) {
		this.cmMotherId = cmMotherId;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public Long getPartnerFoetalMonitorId() {
		return partnerFoetalMonitorId;
	}

	public void setPartnerFoetalMonitorId(Long partnerFoetalMonitorId) {
		this.partnerFoetalMonitorId = partnerFoetalMonitorId;
	}

	public String getaMRITFilePath() {
		return aMRITFilePath;
	}

	public void setaMRITFilePath(String aMRITFilePath) {
		this.aMRITFilePath = aMRITFilePath;
	}

	public Long getBeneficiaryID() {
		return beneficiaryID;
	}

	public void setBeneficiaryID(Long beneficiaryID) {
		this.beneficiaryID = beneficiaryID;
	}

}
