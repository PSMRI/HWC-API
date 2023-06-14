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
package com.iemr.mmu.data.ncdScreening;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "t_ncdscreening")
public class NCDScreening {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	@Column(name = "NCDScreeningVisitNo")
	private Short ncdScreeningVisitNo;

	@Expose
	@Column(name = "NCDScreeningConditionID")
	private String ncdScreeningConditionID;

	@Expose
	@Column(name = "ScreeningCondition")
	private String screeningCondition;

	@Expose
	@Transient
	private ArrayList<Map<String, Object>> ncdScreeningConditionList;

	@Expose
	@Column(name = "NCDScreeningReasonID")
	private Integer ncdScreeningReasonID;

	@Expose
	@Column(name = "ReasonForScreening")
	private String reasonForScreening;

	@Expose
	@Column(name = "NextScreeningDate")
	private Timestamp nextScreeningDateDB;

	@Transient
	private String nextScreeningDate;

	@Expose
	@Column(name = "ActionForScreenPositive")
	private String actionForScreenPositive;

	@Expose
	@Column(name = "IsScreeningComplete")
	private Boolean isScreeningComplete;

	@Expose
	@Column(name = "IsBPPrescribed")
	private Boolean isBPPrescribed;

	@Expose
	@Column(name = "IsBloodGlucosePrescribed")
	private Boolean isBloodGlucosePrescribed;

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

	@Expose
	@Column(name = "VanSerialNo")
	private Long vanSerialNo;

	@Expose
	@Column(name = "VehicalNo")
	private String vehicalNo;

	@Expose
	@Column(name = "vanID")
	private Integer vanID;

	@Expose
	@Column(name = "ParkingPlaceID")
	private Integer parkingPlaceID;

	@Expose
	@Column(name = "SyncedBy")
	private String syncedBy;

	@Expose
	@Column(name = "SyncedDate")
	private Timestamp syncedDate;

	@Expose
	@Column(name = "ReservedForChange")
	private String reservedForChange;

	@Transient
	private Long benFlowID;

	@Expose
	@Transient
	private Integer[] fileIDs;

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

	public Short getNcdScreeningVisitNo() {
		return ncdScreeningVisitNo;
	}

	public void setNcdScreeningVisitNo(Short ncdScreeningVisitNo) {
		this.ncdScreeningVisitNo = ncdScreeningVisitNo;
	}

	public String getNcdScreeningConditionID() {
		return ncdScreeningConditionID;
	}

	public void setNcdScreeningConditionID(String ncdScreeningConditionID) {
		this.ncdScreeningConditionID = ncdScreeningConditionID;
	}

	public String getScreeningCondition() {
		return screeningCondition;
	}

	public void setScreeningCondition(String screeningCondition) {
		this.screeningCondition = screeningCondition;
	}

	public Integer getNcdScreeningReasonID() {
		return ncdScreeningReasonID;
	}

	public void setNcdScreeningReasonID(Integer ncdScreeningReasonID) {
		this.ncdScreeningReasonID = ncdScreeningReasonID;
	}

	public String getReasonForScreening() {
		return reasonForScreening;
	}

	public void setReasonForScreening(String reasonForScreening) {
		this.reasonForScreening = reasonForScreening;
	}

	public Timestamp getNextScreeningDateDB() {
		return nextScreeningDateDB;
	}

	public void setNextScreeningDateDB(Timestamp nextScreeningDateDB) {
		this.nextScreeningDateDB = nextScreeningDateDB;
	}

	public String getNextScreeningDate() {
		return nextScreeningDate;
	}

	public void setNextScreeningDate(String nextScreeningDate) {
		this.nextScreeningDate = nextScreeningDate;
	}

	public String getActionForScreenPositive() {
		return actionForScreenPositive;
	}

	public void setActionForScreenPositive(String actionForScreenPositive) {
		this.actionForScreenPositive = actionForScreenPositive;
	}

	public Boolean getIsScreeningComplete() {
		return isScreeningComplete;
	}

	public void setIsScreeningComplete(Boolean isScreeningComplete) {
		this.isScreeningComplete = isScreeningComplete;
	}

	public Boolean getIsBPPrescribed() {
		return isBPPrescribed;
	}

	public void setIsBPPrescribed(Boolean isBPPrescribed) {
		this.isBPPrescribed = isBPPrescribed;
	}

	public Boolean getIsBloodGlucosePrescribed() {
		return isBloodGlucosePrescribed;
	}

	public void setIsBloodGlucosePrescribed(Boolean isBloodGlucosePrescribed) {
		this.isBloodGlucosePrescribed = isBloodGlucosePrescribed;
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

	public Long getBenFlowID() {
		return benFlowID;
	}

	public void setBenFlowID(Long benFlowID) {
		this.benFlowID = benFlowID;
	}

	public ArrayList<Map<String, Object>> getNcdScreeningConditionList() {
		return ncdScreeningConditionList;
	}

	public void setNcdScreeningConditionList(ArrayList<Map<String, Object>> ncdScreeningConditionList) {
		this.ncdScreeningConditionList = ncdScreeningConditionList;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public Integer[] getFileIDs() {
		return fileIDs;
	}

	public void setFileIDs(Integer[] fileIDs) {
		this.fileIDs = fileIDs;
	}

}
