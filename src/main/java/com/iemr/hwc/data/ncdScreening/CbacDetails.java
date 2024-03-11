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
package com.iemr.hwc.data.ncdScreening;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "t_cbacdetails")
public class CbacDetails {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "Id", insertable = false)
	private Long id;
	@Expose
	@Column(name = "BeneficiaryRegId")
	private Long beneficiaryRegId;

	@Expose
	@Column(name = "Visitcode")
	private Long visitCode;
	@Expose
	@Column(name = "Cbac_Age")
	private String cbacAge;
	@Expose
	@Column(name = "Cbac_Age_Score")
	private Integer cbacAgeScore;
	@Expose
	@Column(name = "Cbac_ConusmeGutka")
	private String cbacConsumeGutka;
	@Expose
	@Column(name = "Cbac_ConusmeGutka_Score")
	private Integer cbacConsumeGutkaScore;
	@Expose
	@Column(name = "cbac_alcohol")
	private String cbacAlcohol;
	@Expose
	@Column(name = "cbac_alcohol_Score")
	private Integer cbacAlcoholScore;
	@Expose
	@Column(name = "Cbac_waist_Male")
	private String cbacWaistMale;
	@Expose
	@Column(name = "Cbac_waist_Male_Score")
	private Integer cbacWaistMaleScore;
	@Expose
	@Column(name = "Cbac_Waist_Female")
	private String cbacWaistFemale;
	@Expose
	@Column(name = "Cbac_Waist_Female_Score")
	private Integer cbacWaistFemaleScore;
	@Expose
	@Column(name = "Cbac_PhysicalActivity")
	private String cbacPhysicalActivity;
	@Expose
	@Column(name = "Cbac_PhysicalActivity_Score")
	private Integer cbacPhysicalActivityScore;
	@Expose
	@Column(name = "Cbac_FamilyHistory_bpdiabetes")
	private String cbacFamilyHistoryBpdiabetes;
	@Expose
	@Column(name = "Cbac_FamilyHistory_bpdiabetes_Score")
	private Integer cbacFamilyHistoryBpdiabetesScore;
	@Expose
	@Column(name = "Cbac_ShortnessBreath")
	private String cbacShortnessBreath;
	@Expose
	@Column(name = "Cbac_Cough2weeks")
	private String cbacCough2weeks;
	@Expose
	@Column(name = "Cbac_Bloodsputum")
	private String cbacBloodsputum;
	@Expose
	@Column(name = "Cbac_fever2weeks")
	private String cbacFever2weeks;
	@Expose
	@Column(name = "Cbac_WeightLoss")
	private String cbacWeightLoss;
	@Expose
	@Column(name = "Cbac_NightSweats")
	private String cbacNightSweats;
	@Expose
	@Column(name = "Cbac_AntiTBDrugs")
	private String cbacAntiTBDrugs;
	@Expose
	@Column(name = "Cabc_TB")
	private String cbacTb;
	@Expose
	@Column(name = "Cbac_TBHistory")
	private String cbacTBHistory;
	@Expose
	@Column(name = "Cbac_Ulceration")
	private String cbacUlceration;
	@Expose
	@Column(name = "Cbac_RecurrentTingling")
	private String cbacRecurrentTingling;
	@Expose
	@Column(name = "Cbac_FitsHistory")
	private String cbacFitsHistory;
	@Expose
	@Column(name = "Cbac_MouthopeningDifficulty")
	private String cbacMouthopeningDifficulty;
	@Expose
	@Column(name = "Cbac_MouthUlcers")
	private String cbacMouthUlcers;
	@Expose
	@Column(name = "Cbac_MouthUlcersGrowth")
	private String cbacMouthUlcersGrowth;
	@Expose
	@Column(name = "Cbac_Mouthredpatch")
	private String cbacMouthredpatch;
	@Expose
	@Column(name = "Cbac_Painchewing")
	private String cbacPainchewing;
	@Expose
	@Column(name = "Cbac_Tonechange")
	private String cbacTonechange;
	@Expose
	@Column(name = "Cbac_Hypopigmentedpatches")
	private String cbacHypopigmentedpatches;
	@Expose
	@Column(name = "Cbac_Thickenedskin")
	private String cbacThickenedskin;
	@Expose
	@Column(name = "Cbac_Nodulesonskin")
	private String cbacNodulesonskin;
	@Expose
	@Column(name = "Cbac_RecurrentNumbness")
	private String cbacRecurrentNumbness;
	@Expose
	@Column(name = "Cbac_BlurredVision")
	private String cbacBlurredVision;
	@Expose
	@Column(name = "Cbac_Difficultyreading")
	private String cbacDifficultyreading;
	@Expose
	@Column(name = "Cbac_Painineyes")
	private String cbacPainineyes;
	@Expose
	@Column(name = "Cbac_RednessPain")
	private String cbacRednessPain;
	@Expose
	@Column(name = "Cbac_DifficultyHearing")
	private String cbacDifficultyHearing;
	@Expose
	@Column(name = "Cbac_Clawingfingers")
	private String cbacClawingfingers;
	@Expose
	@Column(name = "Cbac_HandTingling")
	private String cbacHandTingling;
	@Expose
	@Column(name = "Cbac_InabilityCloseeyelid")
	private String cbacInabilityCloseeyelid;
	@Expose
	@Column(name = "Cbac_DifficultHoldingObjects")
	private String cbacDifficultHoldingObjects;
	@Expose
	@Column(name = "Cbac_Feetweakness")
	private String cbacFeetweakness;
	@Expose
	@Column(name = "Cbac_LumpBreast")
	private String cbacLumpBreast;
	@Expose
	@Column(name = "Cbac_BloodnippleDischarge")
	private String cbacBloodnippleDischarge;
	@Expose
	@Column(name = "Cbac_Breastsizechange")
	private String cbacBreastsizechange;
	@Expose
	@Column(name = "Cbac_BleedingPeriods")
	private String cbacBleedingPeriods;
	@Expose
	@Column(name = "Cbac_BleedingMenopause")
	private String cbacBleedingMenopause;
	@Expose
	@Column(name = "Cbac_BleedingIntercourse")
	private String cbacBleedingIntercourse;
	@Expose
	@Column(name = "Cbac_VaginalDischarge")
	private String cbacVaginalDischarge;
	@Expose
	@Column(name = "Cbac_FeelingUnsteady")
	private String cbacFeelingUnsteady;
	@Expose
	@Column(name = "Cbac_PhysicalDisabilitySuffering")
	private String cbacPhysicalDisabilitySuffering;
	@Expose
	@Column(name = "Cbac_NeedhelpEverydayActivities")
	private String cbacNeedhelpEverydayActivities;
	@Expose
	@Column(name = "Cbac_Forgetnearones")
	private String cbacForgetnearones;
	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapId;

	@Expose
	@Column(name = "Total_Score")
	private Integer totalScore;
	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
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

	@Expose
	@Column(name = "VanSerialNo")
	private Integer vanSerialNo;
	@Expose
	@Column(name = "VanID")
	private Integer vanId;
	@Expose
	@Column(name = "VehicalNo")
	private String vehicalNo;
	@Expose
	@Column(name = "ParkingPlaceID")
	private Integer parkingPlaceId;
	@Expose
	@Column(name = "SyncedBy")
	private String syncedBy;
	@Expose
	@Column(name = "SyncedDate")
	private Timestamp syncedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBeneficiaryRegId() {
		return beneficiaryRegId;
	}

	public void setBeneficiaryRegId(Long beneficiaryRegId) {
		this.beneficiaryRegId = beneficiaryRegId;
	}

	public Long getVisitcode() {
		return visitCode;
	}

	public void setVisitcode(Long visitcode) {
		this.visitCode = visitcode;
	}

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

	public String getCbacAge() {
		return cbacAge;
	}

	public void setCbacAge(String cbacAge) {
		this.cbacAge = cbacAge;
	}

	public Integer getCbacAgeScore() {
		return cbacAgeScore;
	}

	public void setCbacAgeScore(Integer cbacAgeScore) {
		this.cbacAgeScore = cbacAgeScore;
	}

	public String getCbacConsumeGutka() {
		return cbacConsumeGutka;
	}

	public void setCbacConsumeGutka(String cbacConsumeGutka) {
		this.cbacConsumeGutka = cbacConsumeGutka;
	}

	public String getCbacTb() {
		return cbacTb;
	}

	public void setCbacTb(String cbacTb) {
		this.cbacTb = cbacTb;
	}

	public Integer getCbacConsumeGutkaScore() {
		return cbacConsumeGutkaScore;
	}

	public void setCbacConsumeGutkaScore(Integer cbacConsumeGutkaScore) {
		this.cbacConsumeGutkaScore = cbacConsumeGutkaScore;
	}

	public String getCbacAlcohol() {
		return cbacAlcohol;
	}

	public void setCbacAlcohol(String cbacAlcohol) {
		this.cbacAlcohol = cbacAlcohol;
	}

	public Integer getCbacAlcoholScore() {
		return cbacAlcoholScore;
	}

	public void setCbacAlcoholScore(Integer cbacAlcoholScore) {
		this.cbacAlcoholScore = cbacAlcoholScore;
	}

	public String getCbacWaistMale() {
		return cbacWaistMale;
	}

	public void setCbacWaistMale(String cbacWaistMale) {
		this.cbacWaistMale = cbacWaistMale;
	}

	public Integer getCbacWaistMaleScore() {
		return cbacWaistMaleScore;
	}

	public void setCbacWaistMaleScore(Integer cbacWaistMaleScore) {
		this.cbacWaistMaleScore = cbacWaistMaleScore;
	}

	public String getCbacWaistFemale() {
		return cbacWaistFemale;
	}

	public void setCbacWaistFemale(String cbacWaistFemale) {
		this.cbacWaistFemale = cbacWaistFemale;
	}

	public Integer getCbacWaistFemaleScore() {
		return cbacWaistFemaleScore;
	}

	public void setCbacWaistFemaleScore(Integer cbacWaistFemaleScore) {
		this.cbacWaistFemaleScore = cbacWaistFemaleScore;
	}

	public String getCbacPhysicalActivity() {
		return cbacPhysicalActivity;
	}

	public void setCbacPhysicalActivity(String cbacPhysicalActivity) {
		this.cbacPhysicalActivity = cbacPhysicalActivity;
	}

	public Integer getCbacPhysicalActivityScore() {
		return cbacPhysicalActivityScore;
	}

	public void setCbacPhysicalActivityScore(Integer cbacPhysicalActivityScore) {
		this.cbacPhysicalActivityScore = cbacPhysicalActivityScore;
	}

	public String getCbacFamilyHistoryBpdiabetes() {
		return cbacFamilyHistoryBpdiabetes;
	}

	public void setCbacFamilyHistoryBpdiabetes(String cbacFamilyHistoryBpdiabetes) {
		this.cbacFamilyHistoryBpdiabetes = cbacFamilyHistoryBpdiabetes;
	}

	public Integer getCbacFamilyHistoryBpdiabetesScore() {
		return cbacFamilyHistoryBpdiabetesScore;
	}

	public void setCbacFamilyHistoryBpdiabetesScore(Integer cbacFamilyHistoryBpdiabetesScore) {
		this.cbacFamilyHistoryBpdiabetesScore = cbacFamilyHistoryBpdiabetesScore;
	}

	public String getCbacShortnessBreath() {
		return cbacShortnessBreath;
	}

	public void setCbacShortnessBreath(String cbacShortnessBreath) {
		this.cbacShortnessBreath = cbacShortnessBreath;
	}

	public String getCbacCough2weeks() {
		return cbacCough2weeks;
	}

	public void setCbacCough2weeks(String cbacCough2weeks) {
		this.cbacCough2weeks = cbacCough2weeks;
	}

	public String getCbacBloodsputum() {
		return cbacBloodsputum;
	}

	public void setCbacBloodsputum(String cbacBloodsputum) {
		this.cbacBloodsputum = cbacBloodsputum;
	}

	public String getCbacFever2weeks() {
		return cbacFever2weeks;
	}

	public void setCbacFever2weeks(String cbacFever2weeks) {
		this.cbacFever2weeks = cbacFever2weeks;
	}

	public String getCbacWeightLoss() {
		return cbacWeightLoss;
	}

	public void setCbacWeightLoss(String cbacWeightLoss) {
		this.cbacWeightLoss = cbacWeightLoss;
	}

	public String getCbacNightSweats() {
		return cbacNightSweats;
	}

	public void setCbacNightSweats(String cbacNightSweats) {
		this.cbacNightSweats = cbacNightSweats;
	}

	public String getCbacAntiTBDrugs() {
		return cbacAntiTBDrugs;
	}

	public void setCbacAntiTBDrugs(String cbacAntiTBDrugs) {
		this.cbacAntiTBDrugs = cbacAntiTBDrugs;
	}

	public String getCabcTB() {
		return cbacTb;
	}

	public void setCabcTB(String cabcTB) {
		this.cbacTb = cabcTB;
	}

	public String getCbacTBHistory() {
		return cbacTBHistory;
	}

	public void setCbacTBHistory(String cbacTBHistory) {
		this.cbacTBHistory = cbacTBHistory;
	}

	public String getCbacUlceration() {
		return cbacUlceration;
	}

	public void setCbacUlceration(String cbacUlceration) {
		this.cbacUlceration = cbacUlceration;
	}

	public String getCbacRecurrentTingling() {
		return cbacRecurrentTingling;
	}

	public void setCbacRecurrentTingling(String cbacRecurrentTingling) {
		this.cbacRecurrentTingling = cbacRecurrentTingling;
	}

	public String getCbacFitsHistory() {
		return cbacFitsHistory;
	}

	public void setCbacFitsHistory(String cbacFitsHistory) {
		this.cbacFitsHistory = cbacFitsHistory;
	}

	public String getCbacMouthopeningDifficulty() {
		return cbacMouthopeningDifficulty;
	}

	public void setCbacMouthopeningDifficulty(String cbacMouthopeningDifficulty) {
		this.cbacMouthopeningDifficulty = cbacMouthopeningDifficulty;
	}

	public String getCbacMouthUlcers() {
		return cbacMouthUlcers;
	}

	public void setCbacMouthUlcers(String cbacMouthUlcers) {
		this.cbacMouthUlcers = cbacMouthUlcers;
	}

	public String getCbacMouthUlcersGrowth() {
		return cbacMouthUlcersGrowth;
	}

	public void setCbacMouthUlcersGrowth(String cbacMouthUlcersGrowth) {
		this.cbacMouthUlcersGrowth = cbacMouthUlcersGrowth;
	}

	public String getCbacMouthredpatch() {
		return cbacMouthredpatch;
	}

	public void setCbacMouthredpatch(String cbacMouthredpatch) {
		this.cbacMouthredpatch = cbacMouthredpatch;
	}

	public String getCbacPainchewing() {
		return cbacPainchewing;
	}

	public void setCbacPainchewing(String cbacPainchewing) {
		this.cbacPainchewing = cbacPainchewing;
	}

	public String getCbacTonechange() {
		return cbacTonechange;
	}

	public void setCbacTonechange(String cbacTonechange) {
		this.cbacTonechange = cbacTonechange;
	}

	public String getCbacHypopigmentedpatches() {
		return cbacHypopigmentedpatches;
	}

	public void setCbacHypopigmentedpatches(String cbacHypopigmentedpatches) {
		this.cbacHypopigmentedpatches = cbacHypopigmentedpatches;
	}

	public String getCbacThickenedskin() {
		return cbacThickenedskin;
	}

	public void setCbacThickenedskin(String cbacThickenedskin) {
		this.cbacThickenedskin = cbacThickenedskin;
	}

	public String getCbacNodulesonskin() {
		return cbacNodulesonskin;
	}

	public void setCbacNodulesonskin(String cbacNodulesonskin) {
		this.cbacNodulesonskin = cbacNodulesonskin;
	}

	public String getCbacRecurrentNumbness() {
		return cbacRecurrentNumbness;
	}

	public void setCbacRecurrentNumbness(String cbacRecurrentNumbness) {
		this.cbacRecurrentNumbness = cbacRecurrentNumbness;
	}

	public String getCbacBlurredVision() {
		return cbacBlurredVision;
	}

	public void setCbacBlurredVision(String cbacBlurredVision) {
		this.cbacBlurredVision = cbacBlurredVision;
	}

	public String getCbacDifficultyreading() {
		return cbacDifficultyreading;
	}

	public void setCbacDifficultyreading(String cbacDifficultyreading) {
		this.cbacDifficultyreading = cbacDifficultyreading;
	}

	public String getCbacPainineyes() {
		return cbacPainineyes;
	}

	public void setCbacPainineyes(String cbacPainineyes) {
		this.cbacPainineyes = cbacPainineyes;
	}

	public String getCbacRednessPain() {
		return cbacRednessPain;
	}

	public void setCbacRednessPain(String cbacRednessPain) {
		this.cbacRednessPain = cbacRednessPain;
	}

	public String getCbacDifficultyHearing() {
		return cbacDifficultyHearing;
	}

	public void setCbacDifficultyHearing(String cbacDifficultyHearing) {
		this.cbacDifficultyHearing = cbacDifficultyHearing;
	}

	public String getCbacClawingfingers() {
		return cbacClawingfingers;
	}

	public void setCbacClawingfingers(String cbacClawingfingers) {
		this.cbacClawingfingers = cbacClawingfingers;
	}

	public String getCbacHandTingling() {
		return cbacHandTingling;
	}

	public void setCbacHandTingling(String cbacHandTingling) {
		this.cbacHandTingling = cbacHandTingling;
	}

	public String getCbacInabilityCloseeyelid() {
		return cbacInabilityCloseeyelid;
	}

	public void setCbacInabilityCloseeyelid(String cbacInabilityCloseeyelid) {
		this.cbacInabilityCloseeyelid = cbacInabilityCloseeyelid;
	}

	public String getCbacDifficultHoldingObjects() {
		return cbacDifficultHoldingObjects;
	}

	public void setCbacDifficultHoldingObjects(String cbacDifficultHoldingObjects) {
		this.cbacDifficultHoldingObjects = cbacDifficultHoldingObjects;
	}

	public String getCbacFeetweakness() {
		return cbacFeetweakness;
	}

	public void setCbacFeetweakness(String cbacFeetweakness) {
		this.cbacFeetweakness = cbacFeetweakness;
	}

	public String getCbacLumpBreast() {
		return cbacLumpBreast;
	}

	public void setCbacLumpBreast(String cbacLumpBreast) {
		this.cbacLumpBreast = cbacLumpBreast;
	}

	public String getCbacBloodnippleDischarge() {
		return cbacBloodnippleDischarge;
	}

	public void setCbacBloodnippleDischarge(String cbacBloodnippleDischarge) {
		this.cbacBloodnippleDischarge = cbacBloodnippleDischarge;
	}

	public String getCbacBreastsizechange() {
		return cbacBreastsizechange;
	}

	public void setCbacBreastsizechange(String cbacBreastsizechange) {
		this.cbacBreastsizechange = cbacBreastsizechange;
	}

	public String getCbacBleedingPeriods() {
		return cbacBleedingPeriods;
	}

	public void setCbacBleedingPeriods(String cbacBleedingPeriods) {
		this.cbacBleedingPeriods = cbacBleedingPeriods;
	}

	public String getCbacBleedingMenopause() {
		return cbacBleedingMenopause;
	}

	public void setCbacBleedingMenopause(String cbacBleedingMenopause) {
		this.cbacBleedingMenopause = cbacBleedingMenopause;
	}

	public String getCbacBleedingIntercourse() {
		return cbacBleedingIntercourse;
	}

	public void setCbacBleedingIntercourse(String cbacBleedingIntercourse) {
		this.cbacBleedingIntercourse = cbacBleedingIntercourse;
	}

	public String getCbacVaginalDischarge() {
		return cbacVaginalDischarge;
	}

	public void setCbacVaginalDischarge(String cbacVaginalDischarge) {
		this.cbacVaginalDischarge = cbacVaginalDischarge;
	}

	public String getCbacFeelingUnsteady() {
		return cbacFeelingUnsteady;
	}

	public void setCbacFeelingUnsteady(String cbacFeelingUnsteady) {
		this.cbacFeelingUnsteady = cbacFeelingUnsteady;
	}

	public String getCbacPhysicalDisabilitySuffering() {
		return cbacPhysicalDisabilitySuffering;
	}

	public void setCbacPhysicalDisabilitySuffering(String cbacPhysicalDisabilitySuffering) {
		this.cbacPhysicalDisabilitySuffering = cbacPhysicalDisabilitySuffering;
	}

	public String getCbacNeedhelpEverydayActivities() {
		return cbacNeedhelpEverydayActivities;
	}

	public void setCbacNeedhelpEverydayActivities(String cbacNeedhelpEverydayActivities) {
		this.cbacNeedhelpEverydayActivities = cbacNeedhelpEverydayActivities;
	}

	public String getCbacForgetnearones() {
		return cbacForgetnearones;
	}

	public void setCbacForgetnearones(String cbacForgetnearones) {
		this.cbacForgetnearones = cbacForgetnearones;
	}

	public Integer getProviderServiceMapId() {
		return providerServiceMapId;
	}

	public void setProviderServiceMapId(Integer providerServiceMapId) {
		this.providerServiceMapId = providerServiceMapId;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
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

	public Integer getVanSerialNo() {
		return vanSerialNo;
	}

	public void setVanSerialNo(Integer vanSerialNo) {
		this.vanSerialNo = vanSerialNo;
	}

	public Integer getVanId() {
		return vanId;
	}

	public void setVanId(Integer vanId) {
		this.vanId = vanId;
	}

	public String getVehicalNo() {
		return vehicalNo;
	}

	public void setVehicalNo(String vehicalNo) {
		this.vehicalNo = vehicalNo;
	}

	public Integer getParkingPlaceId() {
		return parkingPlaceId;
	}

	public void setParkingPlaceId(Integer parkingPlaceId) {
		this.parkingPlaceId = parkingPlaceId;
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

}
