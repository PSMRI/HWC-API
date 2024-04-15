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

import java.sql.Date;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "t_CancerObstetricHistory")
public class BenObstetricCancerHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	@Column(name = "PregnancyStatus")
	private String pregnancyStatus;
	@Expose
	@Column(name = "IsUrinePregTest")
	private Boolean isUrinePregTest;

	@Transient
	private String IsUrinePregTest;

	@Expose
	@Column(name = "Pregnant_No")
	private String pregnant_No;
	@Expose
	@Column(name = "NoOfLivingChild")
	private Integer noOfLivingChild;
	@Expose
	@Column(name = "IsAbortion")
	private Boolean isAbortion;

	@Transient
	private String IsAbortion;

	@Expose
	@Column(name = "IsOralContraceptiveUsed")
	private Boolean isOralContraceptiveUsed;

	@Transient
	private String IsOralContraceptiveUsed;

	@Expose
	@Column(name = "IsHormoneReplacementTherapy")
	private Boolean isHormoneReplacementTherapy;

	@Transient
	private String IsHormoneReplacementTherapy;

	@Expose
	@Column(name = "Menarche_Age")
	private Integer menarche_Age;
	@Expose
	@Column(name = "IsMenstrualCycleRegular")
	private Boolean isMenstrualCycleRegular;

	@Transient
	private String IsMenstrualCycleRegular;

	@Expose
	@Column(name = "MenstrualCycleLength")
	private Integer menstrualCycleLength;
	@Expose
	@Column(name = "MenstrualFlowDuration")
	private Integer menstrualFlowDuration;
	@Expose
	@Column(name = "MenstrualFlowType")
	private String menstrualFlowType;
	@Expose
	@Column(name = "IsDysmenorrhea")
	private Boolean isDysmenorrhea;

	@Transient
	private String IsDysmenorrhea;

	@Expose
	@Column(name = "IsInterMenstrualBleeding")
	private Boolean isInterMenstrualBleeding;

	@Transient
	private String IsInterMenstrualBleeding;

	@Expose
	@Column(name = "MenopauseAge")
	private Integer menopauseAge;
	@Expose
	@Column(name = "IsPostMenopauseBleeding")
	private Boolean isPostMenopauseBleeding;

	@Transient
	private String IsPostMenopauseBleeding;

	@Expose
	@Column(name = "isFoulSmellingDischarge")
	private Boolean isFoulSmellingDischarge;

	@Transient
	private String IsFoulSmellingDischarge;

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
	private Date captureDate;

	public BenObstetricCancerHistory() {
	}

	public BenObstetricCancerHistory(String pregnancyStatus, Boolean isUrinePregTest, String pregnant_No,
			Integer noOfLivingChild, Boolean isAbortion, Boolean isOralContraceptiveUsed,
			Boolean isHormoneReplacementTherapy, Integer menarche_Age, Boolean isMenstrualCycleRegular,
			Integer menstrualCycleLength, Integer menstrualFlowDuration, String menstrualFlowType,
			Boolean isDysmenorrhea, Boolean isInterMenstrualBleeding, Integer menopauseAge,
			Boolean isPostMenopauseBleeding, Boolean isFoulSmellingDischarge, Date captureDate) {

		this.pregnancyStatus = pregnancyStatus;

		if (null != isUrinePregTest && isUrinePregTest)
			this.IsUrinePregTest = "Yes";
		else
			this.IsUrinePregTest = "No";

		this.pregnant_No = pregnant_No;
		this.noOfLivingChild = noOfLivingChild;

		if (null != isAbortion && isAbortion)
			this.IsAbortion = "Yes";
		else
			this.IsAbortion = "No";

		if (null != isOralContraceptiveUsed && isOralContraceptiveUsed)
			this.IsOralContraceptiveUsed = "Yes";
		else
			this.IsOralContraceptiveUsed = "No";

		if (null != isHormoneReplacementTherapy && isHormoneReplacementTherapy)
			this.IsHormoneReplacementTherapy = "Yes";
		else
			this.IsHormoneReplacementTherapy = "No";

		this.menarche_Age = menarche_Age;

		if (null != isMenstrualCycleRegular && isMenstrualCycleRegular)
			this.IsMenstrualCycleRegular = "Yes";
		else
			this.IsMenstrualCycleRegular = "No";

		this.menstrualCycleLength = menstrualCycleLength;
		this.menstrualFlowDuration = menstrualFlowDuration;
		this.menstrualFlowType = menstrualFlowType;

		if (null != isDysmenorrhea && isDysmenorrhea)
			this.IsDysmenorrhea = "Yes";
		else
			this.IsDysmenorrhea = "No";

		if (null != isInterMenstrualBleeding && isInterMenstrualBleeding)
			this.IsInterMenstrualBleeding = "Yes";
		else
			this.IsInterMenstrualBleeding = "No";

		this.menopauseAge = menopauseAge;

		if (null != isPostMenopauseBleeding && isPostMenopauseBleeding)
			this.IsPostMenopauseBleeding = "Yes";
		else
			this.IsPostMenopauseBleeding = "No";

		if (null != isFoulSmellingDischarge && isFoulSmellingDischarge)
			this.IsFoulSmellingDischarge = "Yes";
		else
			this.IsFoulSmellingDischarge = "No";
		this.captureDate = captureDate;

	}

	public BenObstetricCancerHistory(Long iD, Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID,
			String pregnancyStatus, Boolean isUrinePregTest, String pregnant_No, Integer noOfLivingChild,
			Boolean isAbortion, Boolean isOralContraceptiveUsed, Boolean isHormoneReplacementTherapy,
			Integer menarche_Age, Boolean isMenstrualCycleRegular, Integer menstrualCycleLength,
			Integer menstrualFlowDuration, String menstrualFlowType, Boolean isDysmenorrhea,
			Boolean isInterMenstrualBleeding, Integer menopauseAge, Boolean isPostMenopauseBleeding,
			Boolean isFoulSmellingDischarge, Boolean deleted, String processed, String createdBy, Timestamp createdDate,
			String modifiedBy, Timestamp lastModDate) {
		super();
		this.ID = iD;
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.pregnancyStatus = pregnancyStatus;
		this.isUrinePregTest = isUrinePregTest;
		this.pregnant_No = pregnant_No;
		this.noOfLivingChild = noOfLivingChild;
		this.isAbortion = isAbortion;
		this.isOralContraceptiveUsed = isOralContraceptiveUsed;
		this.isHormoneReplacementTherapy = isHormoneReplacementTherapy;
		this.menarche_Age = menarche_Age;
		this.isMenstrualCycleRegular = isMenstrualCycleRegular;
		this.menstrualCycleLength = menstrualCycleLength;
		this.menstrualFlowDuration = menstrualFlowDuration;
		this.menstrualFlowType = menstrualFlowType;
		this.isDysmenorrhea = isDysmenorrhea;
		this.isInterMenstrualBleeding = isInterMenstrualBleeding;
		this.menopauseAge = menopauseAge;
		this.isPostMenopauseBleeding = isPostMenopauseBleeding;
		this.isFoulSmellingDischarge = isFoulSmellingDischarge;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public Date getCaptureDate() {
		return captureDate;
	}

	public void setCaptureDate(Date captureDate) {
		this.captureDate = captureDate;
	}

	public void setIsUrinePregTest(String isUrinePregTest) {
		IsUrinePregTest = isUrinePregTest;
	}

	public void setIsAbortion(String isAbortion) {
		IsAbortion = isAbortion;
	}

	public void setIsOralContraceptiveUsed(String isOralContraceptiveUsed) {
		IsOralContraceptiveUsed = isOralContraceptiveUsed;
	}

	public void setIsHormoneReplacementTherapy(String isHormoneReplacementTherapy) {
		IsHormoneReplacementTherapy = isHormoneReplacementTherapy;
	}

	public void setIsMenstrualCycleRegular(String isMenstrualCycleRegular) {
		IsMenstrualCycleRegular = isMenstrualCycleRegular;
	}

	public void setIsDysmenorrhea(String isDysmenorrhea) {
		IsDysmenorrhea = isDysmenorrhea;
	}

	public void setIsInterMenstrualBleeding(String isInterMenstrualBleeding) {
		IsInterMenstrualBleeding = isInterMenstrualBleeding;
	}

	public void setIsPostMenopauseBleeding(String isPostMenopauseBleeding) {
		IsPostMenopauseBleeding = isPostMenopauseBleeding;
	}

	public void setIsFoulSmellingDischarge(String isFoulSmellingDischarge) {
		IsFoulSmellingDischarge = isFoulSmellingDischarge;
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

	public String getPregnancyStatus() {
		return pregnancyStatus;
	}

	public void setPregnancyStatus(String pregnancyStatus) {
		this.pregnancyStatus = pregnancyStatus;
	}

	public Boolean getIsUrinePregTest() {
		return isUrinePregTest;
	}

	public void setIsUrinePregTest(Boolean isUrinePregTest) {
		this.isUrinePregTest = isUrinePregTest;
	}

	public String getPregnant_No() {
		return pregnant_No;
	}

	public void setPregnant_No(String pregnant_No) {
		this.pregnant_No = pregnant_No;
	}

	public Integer getNoOfLivingChild() {
		return noOfLivingChild;
	}

	public void setNoOfLivingChild(Integer noOfLivingChild) {
		this.noOfLivingChild = noOfLivingChild;
	}

	public Boolean getIsAbortion() {
		return isAbortion;
	}

	public void setIsAbortion(Boolean isAbortion) {
		this.isAbortion = isAbortion;
	}

	public Boolean getIsOralContraceptiveUsed() {
		return isOralContraceptiveUsed;
	}

	public void setIsOralContraceptiveUsed(Boolean isOralContraceptiveUsed) {
		this.isOralContraceptiveUsed = isOralContraceptiveUsed;
	}

	public Boolean getIsHormoneReplacementTherapy() {
		return isHormoneReplacementTherapy;
	}

	public void setIsHormoneReplacementTherapy(Boolean isHormoneReplacementTherapy) {
		this.isHormoneReplacementTherapy = isHormoneReplacementTherapy;
	}

	public Integer getMenarche_Age() {
		return menarche_Age;
	}

	public void setMenarche_Age(Integer menarche_Age) {
		this.menarche_Age = menarche_Age;
	}

	public Boolean getIsMenstrualCycleRegular() {
		return isMenstrualCycleRegular;
	}

	public void setIsMenstrualCycleRegular(Boolean isMenstrualCycleRegular) {
		this.isMenstrualCycleRegular = isMenstrualCycleRegular;
	}

	public Integer getMenstrualCycleLength() {
		return menstrualCycleLength;
	}

	public void setMenstrualCycleLength(Integer menstrualCycleLength) {
		this.menstrualCycleLength = menstrualCycleLength;
	}

	public Integer getMenstrualFlowDuration() {
		return menstrualFlowDuration;
	}

	public void setMenstrualFlowDuration(Integer menstrualFlowDuration) {
		this.menstrualFlowDuration = menstrualFlowDuration;
	}

	public String getMenstrualFlowType() {
		return menstrualFlowType;
	}

	public void setMenstrualFlowType(String menstrualFlowType) {
		this.menstrualFlowType = menstrualFlowType;
	}

	public Boolean getIsDysmenorrhea() {
		return isDysmenorrhea;
	}

	public void setIsDysmenorrhea(Boolean isDysmenorrhea) {
		this.isDysmenorrhea = isDysmenorrhea;
	}

	public Boolean getIsInterMenstrualBleeding() {
		return isInterMenstrualBleeding;
	}

	public void setIsInterMenstrualBleeding(Boolean isInterMenstrualBleeding) {
		this.isInterMenstrualBleeding = isInterMenstrualBleeding;
	}

	public Integer getMenopauseAge() {
		return menopauseAge;
	}

	public void setMenopauseAge(Integer menopauseAge) {
		this.menopauseAge = menopauseAge;
	}

	public Boolean getIsPostMenopauseBleeding() {
		return isPostMenopauseBleeding;
	}

	public void setIsPostMenopauseBleeding(Boolean isPostMenopauseBleeding) {
		this.isPostMenopauseBleeding = isPostMenopauseBleeding;
	}

	public Boolean getIsFoulSmellingDischarge() {
		return isFoulSmellingDischarge;
	}

	public void setIsFoulSmellingDischarge(Boolean isFoulSmellingDischarge) {
		this.isFoulSmellingDischarge = isFoulSmellingDischarge;
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

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

}
