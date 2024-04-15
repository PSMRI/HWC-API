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
package com.iemr.hwc.data.anc;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.hwc.annotation.sqlInjectionSafe.SQLInjectionSafe;
import com.iemr.hwc.service.anc.Utility;

@Entity
@Table(name = "t_BenPersonalHabit")
public class BenPersonalHabit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	@Column(name = "BenPersonalHabitID")
	private Integer benPersonalHabitID;

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
	@Column(name = "DietaryType")
	private @SQLInjectionSafe String dietaryType;

	@Expose
	@Column(name = "PhysicalActivityType")
	private String physicalActivityType;

	@Expose
	@Column(name = "TobaccoUseStatus")
	private @SQLInjectionSafe String tobaccoUseStatus;

	@Expose
	@Column(name = "TobaccoUseTypeID")
	private @SQLInjectionSafe String tobaccoUseTypeID;

	@Expose
	@Column(name = "TobaccoUseType")
	private @SQLInjectionSafe String tobaccoUseType;

	@Expose
	@Column(name = "OtherTobaccoUseType")
	private @SQLInjectionSafe String otherTobaccoUseType;

	@Expose
	@Column(name = "NumberperDay")
	private Short numberperDay;

	@Expose
	@Column(name = "NumberperWeek")
	private Short numberperWeek;

	@Expose
	@Column(name = "TobaccoUseDuration")
	private Timestamp tobaccoUseDuration;

	@Expose
	@Column(name = "AlcoholIntakeStatus")
	private @SQLInjectionSafe String alcoholIntakeStatus;

	@Expose
	@Column(name = "AlcoholTypeID")
	private @SQLInjectionSafe String alcoholTypeID;

	@Expose
	@Column(name = "AlcoholType")
	private @SQLInjectionSafe String alcoholType;

	@Expose
	@Column(name = "OtherAlcoholType")
	private @SQLInjectionSafe String otherAlcoholType;

	@Expose
	@Column(name = "AlcoholIntakeFrequency")
	private @SQLInjectionSafe String alcoholIntakeFrequency;

	@Expose
	@Column(name = "AvgAlcoholConsumption")
	private @SQLInjectionSafe String avgAlcoholConsumption;

	@Expose
	@Column(name = "AlcoholDuration")
	private Timestamp alcoholDuration;

	@Expose
	@Column(name = "RiskySexualPracticesStatus")
	private Character riskySexualPracticesStatus;

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

	@Transient
	@Expose
	private List<Map<String, String>> tobaccoList;

	@Transient
	@Expose
	private List<Map<String, String>> alcoholList;

	@Transient
	@Expose
	private List<BenAllergyHistory> allergicList;

	@Transient
	@Expose
	private @SQLInjectionSafe String allergyStatus;

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
	private @SQLInjectionSafe String syncedBy;

	@Expose
	@Column(name = "SyncedDate")
	private Timestamp syncedDate;

	@Expose
	@Column(name = "ReservedForChange")
	private @SQLInjectionSafe String reservedForChange;

	@Transient
	private Date captureDate;

	@Transient
	private Date tobacco_use_duration;

	@Transient
	private Date alcohol_use_duration;

	@Transient
	private @SQLInjectionSafe String riskySexualPracticeStatus;

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

	public Date getTobacco_use_duration() {
		return tobacco_use_duration;
	}

	public void setTobacco_use_duration(Date tobacco_use_duration) {
		this.tobacco_use_duration = tobacco_use_duration;
	}

	public Date getAlcohol_use_duration() {
		return alcohol_use_duration;
	}

	public void setAlcohol_use_duration(Date alcohol_use_duration) {
		this.alcohol_use_duration = alcohol_use_duration;
	}

	public String getRiskySexualPracticeStatus() {
		return riskySexualPracticeStatus;
	}

	public void setRiskySexualPracticeStatus(String riskySexualPracticeStatus) {
		this.riskySexualPracticeStatus = riskySexualPracticeStatus;
	}

	public String getAllergyStatus() {
		return allergyStatus;
	}

	public void setAllergyStatus(String allergyStatus) {
		this.allergyStatus = allergyStatus;
	}

	public List<BenAllergyHistory> getAllergicList() {
		return allergicList;
	}

	public void setAllergicList(List<BenAllergyHistory> allergicList) {
		this.allergicList = allergicList;
	}

	public Integer getBenPersonalHabitID() {
		return benPersonalHabitID;
	}

	public void setBenPersonalHabitID(Integer benPersonalHabitID) {
		this.benPersonalHabitID = benPersonalHabitID;
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

	public String getDietaryType() {
		return dietaryType;
	}

	public void setDietaryType(String dietaryType) {
		this.dietaryType = dietaryType;
	}

	public String getPhysicalActivityType() {
		return physicalActivityType;
	}

	public void setPhysicalActivityType(String physicalActivityType) {
		this.physicalActivityType = physicalActivityType;
	}

	public String getTobaccoUseStatus() {
		return tobaccoUseStatus;
	}

	public void setTobaccoUseStatus(String tobaccoUseStatus) {
		this.tobaccoUseStatus = tobaccoUseStatus;
	}

	public String getTobaccoUseType() {
		return tobaccoUseType;
	}

	public void setTobaccoUseType(String tobaccoUseType) {
		this.tobaccoUseType = tobaccoUseType;
	}

	public Short getNumberperDay() {
		return numberperDay;
	}

	public Short getNumberperWeek() {
		return numberperWeek;
	}

	public void setNumberperWeek(Short numberperWeek) {
		this.numberperWeek = numberperWeek;
	}

	public void setNumberperDay(Short numberperDay) {
		this.numberperDay = numberperDay;
	}

	public Timestamp getTobaccoUseDuration() {
		return tobaccoUseDuration;
	}

	public void setTobaccoUseDuration(Timestamp tobaccoUseDuration) {
		this.tobaccoUseDuration = tobaccoUseDuration;
	}

	public String getAlcoholIntakeStatus() {
		return alcoholIntakeStatus;
	}

	public void setAlcoholIntakeStatus(String alcoholIntakeStatus) {
		this.alcoholIntakeStatus = alcoholIntakeStatus;
	}

	public String getAlcoholType() {
		return alcoholType;
	}

	public void setAlcoholType(String alcoholType) {
		this.alcoholType = alcoholType;
	}

	public String getAlcoholIntakeFrequency() {
		return alcoholIntakeFrequency;
	}

	public void setAlcoholIntakeFrequency(String alcoholIntakeFrequency) {
		this.alcoholIntakeFrequency = alcoholIntakeFrequency;
	}

	public String getAvgAlcoholConsumption() {
		return avgAlcoholConsumption;
	}

	public void setAvgAlcoholConsumption(String avgAlcoholConsumption) {
		this.avgAlcoholConsumption = avgAlcoholConsumption;
	}

	public Timestamp getAlcoholDuration() {
		return alcoholDuration;
	}

	public void setAlcoholDuration(Timestamp alcoholDuration) {
		this.alcoholDuration = alcoholDuration;
	}

	public Character getRiskySexualPracticesStatus() {
		return riskySexualPracticesStatus;
	}

	public void setRiskySexualPracticesStatus(Character riskySexualPracticesStatus) {
		this.riskySexualPracticesStatus = riskySexualPracticesStatus;
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

	public List<Map<String, String>> getTobaccoList() {
		return tobaccoList;
	}

	public void setTobaccoList(List<Map<String, String>> tobaccoList) {
		this.tobaccoList = tobaccoList;
	}

	public List<Map<String, String>> getAlcoholList() {
		return alcoholList;
	}

	public void setAlcoholList(List<Map<String, String>> alcoholList) {
		this.alcoholList = alcoholList;
	}

	public String getTobaccoUseTypeID() {
		return tobaccoUseTypeID;
	}

	public void setTobaccoUseTypeID(String tobaccoUseTypeID) {
		this.tobaccoUseTypeID = tobaccoUseTypeID;
	}

	public String getOtherTobaccoUseType() {
		return otherTobaccoUseType;
	}

	public void setOtherTobaccoUseType(String otherTobaccoUseType) {
		this.otherTobaccoUseType = otherTobaccoUseType;
	}

	public String getAlcoholTypeID() {
		return alcoholTypeID;
	}

	public void setAlcoholTypeID(String alcoholTypeID) {
		this.alcoholTypeID = alcoholTypeID;
	}

	public String getOtherAlcoholType() {
		return otherAlcoholType;
	}

	public void setOtherAlcoholType(String otherAlcoholType) {
		this.otherAlcoholType = otherAlcoholType;
	}

	public BenPersonalHabit() {
		super();
		// TODO Auto-generated constructor stub
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

	public ArrayList<BenPersonalHabit> getPersonalHistory() {
		int maxPersonalHistorySize = 0;
		if (tobaccoList.size() > alcoholList.size()) {
			maxPersonalHistorySize = tobaccoList.size();
		} else {
			maxPersonalHistorySize = alcoholList.size();
		}
		ArrayList<BenPersonalHabit> personalHabitList = new ArrayList<BenPersonalHabit>();
		if (maxPersonalHistorySize > 0) {
			for (int i = 0; i < maxPersonalHistorySize; i++) {
				BenPersonalHabit benPersonalHabit = new BenPersonalHabit();

				benPersonalHabit.setBeneficiaryRegID(beneficiaryRegID);
				benPersonalHabit.setBenVisitID(benVisitID);
				benPersonalHabit.setVisitCode(visitCode);
				benPersonalHabit.setProviderServiceMapID(providerServiceMapID);
				benPersonalHabit.setVanID(vanID);
				benPersonalHabit.setParkingPlaceID(parkingPlaceID);
				benPersonalHabit.setCreatedBy(createdBy);
				benPersonalHabit.setDietaryType(dietaryType);
				benPersonalHabit.setPhysicalActivityType(physicalActivityType);
				benPersonalHabit.setRiskySexualPracticesStatus(riskySexualPracticesStatus);
				benPersonalHabit.setTobaccoUseStatus(tobaccoUseStatus);
				benPersonalHabit.setAlcoholIntakeStatus(alcoholIntakeStatus);

				String timePeriodUnit = "";
				Integer timePeriodAgo = 0;

				if (null != tobaccoList && tobaccoList.size() > i) {
					Map<String, String> tobaccoInfo = (Map<String, String>) tobaccoList.get(i);
					benPersonalHabit.setTobaccoUseTypeID(tobaccoInfo.get("tobaccoUseTypeID"));
					benPersonalHabit.setTobaccoUseType(tobaccoInfo.get("tobaccoUseType"));
					benPersonalHabit.setOtherTobaccoUseType(tobaccoInfo.get("otherTobaccoUseType"));

					if (null != tobaccoInfo.get("numberperDay")) {
						benPersonalHabit.setNumberperDay(new Short(tobaccoInfo.get("numberperDay")));
					}
					timePeriodUnit = (String) tobaccoInfo.get("durationUnit");

					if (null != tobaccoInfo.get("numberperWeek")) {
						benPersonalHabit.setNumberperWeek(new Short(tobaccoInfo.get("numberperWeek")));
					}
					timePeriodUnit = (String) tobaccoInfo.get("durationUnit");

					if (null != tobaccoInfo.get("duration")) {
						timePeriodAgo = Integer.parseInt(tobaccoInfo.get("duration").toString());
					}
					benPersonalHabit.setTobaccoUseDuration(Utility.convertToDateFormat(timePeriodUnit, timePeriodAgo));
				}

				if (null != alcoholList && alcoholList.size() > i) {
					Map<String, String> alcoholInfo = (Map<String, String>) alcoholList.get(i);

					benPersonalHabit.setAlcoholTypeID(alcoholInfo.get("alcoholTypeID"));
					benPersonalHabit.setAlcoholType(alcoholInfo.get("typeOfAlcohol"));
					benPersonalHabit.setOtherAlcoholType(alcoholInfo.get("otherAlcoholType"));
					benPersonalHabit.setAlcoholIntakeFrequency(alcoholInfo.get("alcoholIntakeFrequency"));
					benPersonalHabit.setAvgAlcoholConsumption(alcoholInfo.get("avgAlcoholConsumption"));

					String durationUnit = (String) alcoholInfo.get("durationUnit");
					Integer duration = 0;
					if (null != alcoholInfo.get("duration")) {
						duration = Integer.parseInt(alcoholInfo.get("duration").toString());
					}
					benPersonalHabit.setAlcoholDuration(Utility.convertToDateFormat(durationUnit, duration));
				}

				// if (benPersonalHabit.getTobaccoUseTypeID() != null ||
				// benPersonalHabit.getAlcoholTypeID() != null)
				personalHabitList.add(benPersonalHabit);
			}
		} else {
			if (dietaryType != null || physicalActivityType != null || riskySexualPracticesStatus != null
					|| tobaccoUseStatus != null || alcoholIntakeStatus != null) {
				BenPersonalHabit benPersonalHabit = new BenPersonalHabit();

				benPersonalHabit.setBeneficiaryRegID(beneficiaryRegID);
				benPersonalHabit.setBenVisitID(benVisitID);
				benPersonalHabit.setVisitCode(visitCode);
				benPersonalHabit.setProviderServiceMapID(providerServiceMapID);
				benPersonalHabit.setVanID(vanID);
				benPersonalHabit.setParkingPlaceID(parkingPlaceID);
				benPersonalHabit.setCreatedBy(createdBy);

				benPersonalHabit.setDietaryType(dietaryType);
				benPersonalHabit.setPhysicalActivityType(physicalActivityType);
				benPersonalHabit.setRiskySexualPracticesStatus(riskySexualPracticesStatus);
				benPersonalHabit.setTobaccoUseStatus(tobaccoUseStatus);
				benPersonalHabit.setAlcoholIntakeStatus(alcoholIntakeStatus);

				personalHabitList.add(benPersonalHabit);
			}
		}
		return personalHabitList;
	}

	public BenPersonalHabit(Date createdDate, String dietaryType, String physicalActivityType, String tobaccoUseStatus,
			String tobaccoUseType, String otherTobaccoUseType, Short numberperDay, Date tobaccoUseDuration,
			Character riskySexualPracticesStatus, Short numberperWeek) {
		super();
		this.captureDate = createdDate;
		this.dietaryType = dietaryType;
		this.physicalActivityType = physicalActivityType;
		this.tobaccoUseStatus = tobaccoUseStatus;
		this.tobaccoUseType = tobaccoUseType;
		this.otherTobaccoUseType = otherTobaccoUseType;
		this.numberperDay = numberperDay;
		this.numberperWeek = numberperWeek;
		this.tobacco_use_duration = tobaccoUseDuration;
		if (null != riskySexualPracticesStatus && riskySexualPracticesStatus == '0') {
			this.riskySexualPracticeStatus = "No";
		} else if (riskySexualPracticesStatus != null && riskySexualPracticesStatus == '1') {
			this.riskySexualPracticeStatus = "Yes";
		}
	}

	public BenPersonalHabit(Date createdDate, String dietaryType, String physicalActivityType,
			String alcoholIntakeStatus, String alcoholType, String otherAlcoholType, String alcoholIntakeFrequency,
			String avgAlcoholConsumption, Date alcoholDuration, Character riskySexualPracticesStatus) {
		super();
		this.captureDate = createdDate;
		this.dietaryType = dietaryType;
		this.physicalActivityType = physicalActivityType;
		this.alcoholIntakeStatus = alcoholIntakeStatus;
		this.alcoholType = alcoholType;
		this.otherAlcoholType = otherAlcoholType;
		this.alcoholIntakeFrequency = alcoholIntakeFrequency;
		this.avgAlcoholConsumption = avgAlcoholConsumption;
		this.alcohol_use_duration = alcoholDuration;
		if (null != riskySexualPracticesStatus && riskySexualPracticesStatus == '0') {
			this.riskySexualPracticeStatus = "No";
		} else {
			this.riskySexualPracticeStatus = "Yes";
		}
	}

	public BenPersonalHabit(Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID, String dietaryType,
			String physicalActivityType, String tobaccoUseStatus, String alcoholIntakeStatus,
			Character riskySexualPracticesStatus) {
		super();
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.dietaryType = dietaryType;
		this.physicalActivityType = physicalActivityType;
		this.tobaccoUseStatus = tobaccoUseStatus;
		this.alcoholIntakeStatus = alcoholIntakeStatus;
		this.riskySexualPracticesStatus = riskySexualPracticesStatus;
	}

	public BenPersonalHabit(String tobaccoUseTypeID, String tobaccoUseType, String otherTobaccoUseType,
			Short numberperDay, Timestamp tobaccoUseDuration, String alcoholTypeID, String alcoholType,
			String otherAlcoholType, String alcoholIntakeFrequency, String avgAlcoholConsumption,
			Timestamp alcoholDuration, Timestamp createdDate, Long visitCode, Short numberperWeek) {
		super();
		this.tobaccoUseTypeID = tobaccoUseTypeID;
		this.tobaccoUseType = tobaccoUseType;
		this.otherTobaccoUseType = otherTobaccoUseType;
		this.numberperDay = numberperDay;

		this.tobaccoUseDuration = tobaccoUseDuration;
		this.alcoholTypeID = alcoholTypeID;
		this.alcoholType = alcoholType;
		this.otherAlcoholType = otherAlcoholType;
		this.alcoholIntakeFrequency = alcoholIntakeFrequency;
		this.avgAlcoholConsumption = avgAlcoholConsumption;
		this.alcoholDuration = alcoholDuration;
		this.createdDate = createdDate;
		this.visitCode = visitCode;
		this.numberperWeek = numberperWeek;
	}

	public static BenPersonalHabit getPersonalDetails(ArrayList<Object[]> personalHistoryDetails) {
		BenPersonalHabit personalDetails = null;
		if (null != personalHistoryDetails && personalHistoryDetails.size() > 0) {
			Object[] obj1 = personalHistoryDetails.get(0);

			personalDetails = new BenPersonalHabit((Long) obj1[0], (Long) obj1[1], (Integer) obj1[2], (String) obj1[3],
					(String) obj1[4], (String) obj1[5], (String) obj1[11], (Character) obj1[18]);

			ArrayList<Map<String, String>> tobaccoList = new ArrayList<Map<String, String>>();
			ArrayList<Map<String, String>> alcoholList = new ArrayList<Map<String, String>>();
			for (Object[] obj : personalHistoryDetails) {
				BenPersonalHabit personalHabits = new BenPersonalHabit((String) obj[6], (String) obj[7],
						(String) obj[8], (Short) obj[9], (Timestamp) obj[10], (String) obj[12], (String) obj[13],
						(String) obj[14], (String) obj[15], (String) obj[16], (Timestamp) obj[17], (Timestamp) obj[19],
						(Long) obj[20], (Short) obj[21]);

				Map<String, Object> timePeriod = null;
				// Integer timePeriodAgo = null;
				if (null != personalHabits.getTobaccoUseTypeID()) {
					Map<String, String> tobaccoInfo = new HashMap<String, String>();
					tobaccoInfo.put("tobaccoUseTypeID", personalHabits.getTobaccoUseTypeID());
					tobaccoInfo.put("tobaccoUseType", personalHabits.getTobaccoUseType());
					tobaccoInfo.put("otherTobaccoUseType", personalHabits.getOtherTobaccoUseType());
					if (null != personalHabits.getNumberperDay()) {
						tobaccoInfo.put("numberperDay", personalHabits.getNumberperDay().toString());
					}

					timePeriod = Utility.convertTimeToWords(personalHabits.getTobaccoUseDuration(),
							personalHabits.getCreatedDate());

					if (null != personalHabits.getNumberperWeek()) {
						tobaccoInfo.put("numberperWeek", personalHabits.getNumberperWeek().toString());
					}

					timePeriod = Utility.convertTimeToWords(personalHabits.getTobaccoUseDuration(),
							personalHabits.getCreatedDate());

					if (timePeriod != null) {
						if (timePeriod.get("timePeriodAgo") != null)
							tobaccoInfo.put("duration", timePeriod.get("timePeriodAgo").toString());
						else
							tobaccoInfo.put("duration", null);

						if (timePeriod.get("timePeriodUnit") != null)
							tobaccoInfo.put("durationUnit", timePeriod.get("timePeriodUnit").toString());
						else
							tobaccoInfo.put("durationUnit", null);
					}
					tobaccoList.add(tobaccoInfo);
				}
				if (null != personalHabits.getAlcoholTypeID()) {
					Map<String, String> alcoholInfo = new HashMap<String, String>();
					alcoholInfo.put("alcoholTypeID", personalHabits.getAlcoholTypeID());
					alcoholInfo.put("alcoholType", personalHabits.getAlcoholType());
					alcoholInfo.put("otherAlcoholType", personalHabits.getOtherAlcoholType());
					alcoholInfo.put("alcoholIntakeFrequency", personalHabits.getAlcoholIntakeFrequency());
					alcoholInfo.put("avgAlcoholConsumption", personalHabits.getAvgAlcoholConsumption());

					timePeriod = Utility.convertTimeToWords(personalHabits.getAlcoholDuration(),
							personalHabits.getCreatedDate());
					// timePeriodAgo = Integer.parseInt(timePeriod.get("timePeriodAgo").toString());

					if (timePeriod != null && timePeriod.get("timePeriodAgo") != null)
						alcoholInfo.put("duration", timePeriod.get("timePeriodAgo").toString());
					else
						alcoholInfo.put("duration", null);

					// alcoholInfo.put("duration", timePeriodAgo.toString());
					if (timePeriod != null && timePeriod.containsKey("timePeriodUnit")
							&& timePeriod.get("timePeriodUnit") != null)
						alcoholInfo.put("durationUnit", timePeriod.get("timePeriodUnit").toString());

					alcoholList.add(alcoholInfo);
				}
			}
			personalDetails.setTobaccoList(tobaccoList);
			personalDetails.setAlcoholList(alcoholList);

		}
		return personalDetails;
	}

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

}
