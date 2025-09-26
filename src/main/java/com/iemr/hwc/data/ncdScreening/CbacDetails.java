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

import jakarta.persistence.*;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Entity
@Table(name = "t_cbacdetails")
@Data
public class CbacDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@Transient
	@Expose
	private Long beneficiaryId;

	@Expose
	@Column(name = "Cbac_OccupationalExposure")
	private  String CbacOccupationalExposure;

	@Expose
	@Column(name = "Cbac_BotheredProblem_last2weeks")
	private  String CbacBotheredProblemLast2weeks;

	@Expose
	@Column(name = "Cbac_LittleInterest_Pleasure")
	private  String CbacLittleInterestPleasure;

	@Expose
	@Column(name = "Cbac_Depressed_hopeless")
	private  String CbacDepressedhopeless;

	@Expose
	@Column(name = "Cbac_DiscolorationSkin")
	private  String CbacDiscolorationSkin;

	@Expose
	@Column(name = "Cbac_Cooking_Oil")
	private String CbacCookingOil;



}
