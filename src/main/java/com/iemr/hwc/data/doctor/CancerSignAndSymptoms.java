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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "t_cancersignandsymptoms")
public class CancerSignAndSymptoms {
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
	@Column(name = "ShortnessOfBreath")
	private Boolean shortnessOfBreath;

	@Expose
	@Column(name = "Cough_gtr_eq_2Weeks")
	private Boolean coughgt2Weeks;

	@Expose
	@Column(name = "BloodInSputum")
	private Boolean bloodInSputum;

	@Expose
	@Column(name = "DifficultyInOpeningMouth")
	private Boolean difficultyInOpeningMouth;

	@Expose
	@Column(name = "NonHealingUlcerOrPatchOrGrowth")
	private Boolean nonHealingUlcerOrPatchOrGrowth;

	@Expose
	@Column(name = "ChangeInTheToneOfVoice")
	private Boolean changeInTheToneOfVoice;

	@Expose
	@Column(name = "LumpInTheBreast")
	private Boolean lumpInTheBreast;

	@Expose
	@Column(name = "BloodStainedDischargeFromNipple")
	private Boolean bloodStainedDischargeFromNipple;

	@Expose
	@Column(name = "ChangeInShapeAndSizeOfBreasts")
	private Boolean changeInShapeAndSizeOfBreasts;

	@Expose
	@Column(name = "VaginalBleedingBetweenPeriods")
	private Boolean vaginalBleedingBetweenPeriods;

	@Expose
	@Column(name = "VaginalBleedingAfterMenopause")
	private Boolean vaginalBleedingAfterMenopause;

	@Expose
	@Column(name = "VaginalBleedingAfterIntercourse")
	private Boolean vaginalBleedingAfterIntercourse;

	@Expose
	@Column(name = "FoulSmellingVaginalDischarge")
	private Boolean foulSmellingVaginalDischarge;

	@Expose
	@Column(name = "BreastEnlargement")
	private Boolean breastEnlargement;

	@Expose
	@Column(name = "LymphNode_Enlarged")
	private Boolean lymphNode_Enlarged;

	@Expose
	@Column(name = "BriefHistory")
	private String observation;

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

	public CancerSignAndSymptoms() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CancerSignAndSymptoms(Long iD, Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID,
			Boolean shortnessOfBreath, Boolean coughGTE2Weeks, Boolean bloodInSputum, Boolean difficultyInOpeningMouth,
			Boolean nonHealingUlcerOrPatchOrGrowth, Boolean changeInTheToneOfVoice, Boolean lumpInTheBreast,
			Boolean bloodStainedDischargeFromNipple, Boolean changeInShapeAndSizeOfBreasts,
			Boolean vaginalBleedingBetweenPeriods, Boolean vaginalBleedingAfterMenopause,
			Boolean vaginalBleedingAfterIntercourse, Boolean foulSmellingVaginalDischarge, Boolean breastEnlargement,
			Boolean lymphNode_Enlarged, String observation, Boolean deleted, String processed, String createdBy,
			Timestamp createdDate, String modifiedBy, Timestamp lastModDate) {
		super();
		ID = iD;
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.shortnessOfBreath = shortnessOfBreath;
		this.coughgt2Weeks = coughGTE2Weeks;
		this.bloodInSputum = bloodInSputum;
		this.difficultyInOpeningMouth = difficultyInOpeningMouth;
		this.nonHealingUlcerOrPatchOrGrowth = nonHealingUlcerOrPatchOrGrowth;
		this.changeInTheToneOfVoice = changeInTheToneOfVoice;
		this.lumpInTheBreast = lumpInTheBreast;
		this.bloodStainedDischargeFromNipple = bloodStainedDischargeFromNipple;
		this.changeInShapeAndSizeOfBreasts = changeInShapeAndSizeOfBreasts;
		this.vaginalBleedingBetweenPeriods = vaginalBleedingBetweenPeriods;
		this.vaginalBleedingAfterMenopause = vaginalBleedingAfterMenopause;
		this.vaginalBleedingAfterIntercourse = vaginalBleedingAfterIntercourse;
		this.foulSmellingVaginalDischarge = foulSmellingVaginalDischarge;
		this.breastEnlargement = breastEnlargement;
		this.lymphNode_Enlarged = lymphNode_Enlarged;
		this.observation = observation;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
	}

	public Boolean getCoughgt2Weeks() {
		return coughgt2Weeks;
	}

	public void setCoughgt2Weeks(Boolean coughgt2Weeks) {
		this.coughgt2Weeks = coughgt2Weeks;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
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

	public Boolean getShortnessOfBreath() {
		return shortnessOfBreath;
	}

	public void setShortnessOfBreath(Boolean shortnessOfBreath) {
		this.shortnessOfBreath = shortnessOfBreath;
	}

	public Boolean getCoughGTE2Weeks() {
		return coughgt2Weeks;
	}

	public void setCoughGTE2Weeks(Boolean coughGTE2Weeks) {
		this.coughgt2Weeks = coughGTE2Weeks;
	}

	public Boolean getBloodInSputum() {
		return bloodInSputum;
	}

	public void setBloodInSputum(Boolean bloodInSputum) {
		this.bloodInSputum = bloodInSputum;
	}

	public Boolean getDifficultyInOpeningMouth() {
		return difficultyInOpeningMouth;
	}

	public void setDifficultyInOpeningMouth(Boolean difficultyInOpeningMouth) {
		this.difficultyInOpeningMouth = difficultyInOpeningMouth;
	}

	public Boolean getNonHealingUlcerOrPatchOrGrowth() {
		return nonHealingUlcerOrPatchOrGrowth;
	}

	public void setNonHealingUlcerOrPatchOrGrowth(Boolean nonHealingUlcerOrPatchOrGrowth) {
		this.nonHealingUlcerOrPatchOrGrowth = nonHealingUlcerOrPatchOrGrowth;
	}

	public Boolean getChangeInTheToneOfVoice() {
		return changeInTheToneOfVoice;
	}

	public void setChangeInTheToneOfVoice(Boolean changeInTheToneOfVoice) {
		this.changeInTheToneOfVoice = changeInTheToneOfVoice;
	}

	public Boolean getLumpInTheBreast() {
		return lumpInTheBreast;
	}

	public void setLumpInTheBreast(Boolean lumpInTheBreast) {
		this.lumpInTheBreast = lumpInTheBreast;
	}

	public Boolean getBloodStainedDischargeFromNipple() {
		return bloodStainedDischargeFromNipple;
	}

	public void setBloodStainedDischargeFromNipple(Boolean bloodStainedDischargeFromNipple) {
		this.bloodStainedDischargeFromNipple = bloodStainedDischargeFromNipple;
	}

	public Boolean getChangeInShapeAndSizeOfBreasts() {
		return changeInShapeAndSizeOfBreasts;
	}

	public void setChangeInShapeAndSizeOfBreasts(Boolean changeInShapeAndSizeOfBreasts) {
		this.changeInShapeAndSizeOfBreasts = changeInShapeAndSizeOfBreasts;
	}

	public Boolean getVaginalBleedingBetweenPeriods() {
		return vaginalBleedingBetweenPeriods;
	}

	public void setVaginalBleedingBetweenPeriods(Boolean vaginalBleedingBetweenPeriods) {
		this.vaginalBleedingBetweenPeriods = vaginalBleedingBetweenPeriods;
	}

	public Boolean getVaginalBleedingAfterMenopause() {
		return vaginalBleedingAfterMenopause;
	}

	public void setVaginalBleedingAfterMenopause(Boolean vaginalBleedingAfterMenopause) {
		this.vaginalBleedingAfterMenopause = vaginalBleedingAfterMenopause;
	}

	public Boolean getVaginalBleedingAfterIntercourse() {
		return vaginalBleedingAfterIntercourse;
	}

	public void setVaginalBleedingAfterIntercourse(Boolean vaginalBleedingAfterIntercourse) {
		this.vaginalBleedingAfterIntercourse = vaginalBleedingAfterIntercourse;
	}

	public Boolean getFoulSmellingVaginalDischarge() {
		return foulSmellingVaginalDischarge;
	}

	public void setFoulSmellingVaginalDischarge(Boolean foulSmellingVaginalDischarge) {
		this.foulSmellingVaginalDischarge = foulSmellingVaginalDischarge;
	}

	public Boolean getBreastEnlargement() {
		return breastEnlargement;
	}

	public void setBreastEnlargement(Boolean breastEnlargement) {
		this.breastEnlargement = breastEnlargement;
	}

	public Boolean getLymphNode_Enlarged() {
		return lymphNode_Enlarged;
	}

	public void setLymphNode_Enlarged(Boolean lymphNode_Enlarged) {
		this.lymphNode_Enlarged = lymphNode_Enlarged;
	}

	public String getBriefHistory() {
		return observation;
	}

	public void setBriefHistory(String observation) {
		this.observation = observation;
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
