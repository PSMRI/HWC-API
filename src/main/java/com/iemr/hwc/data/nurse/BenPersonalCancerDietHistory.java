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
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

@Entity
@Table(name = "t_cancerdiethistory")
public class BenPersonalCancerDietHistory {
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
	@Column(name = "DietType")
	private String dietType;
	@Expose
	@Column(name = "FruitConsumptionDays")
	private Integer fruitConsumptionDays;
	@Expose
	@Column(name = "FruitQuantityPerDay")
	private Integer fruitQuantityPerDay;
	@Expose
	@Column(name = "VegetableConsumptionDays")
	private Integer vegetableConsumptionDays;
	@Expose
	@Column(name = "VegetableQuantityPerDay")
	private Integer vegetableQuantityPerDay;
	@Expose
	@Column(name = "IntakeOfOutsidePreparedMeal")
	private Integer intakeOfOutsidePreparedMeal;
	@Expose
	@Column(name = "TypeOfOilConsumed")
	private String typeOfOilConsumed;
	@Expose
	@Column(name = "PhysicalActivityType")
	private String physicalActivityType;
	@Expose
	@Column(name = "IsRadiationExposure")
	private Boolean ssRadiationExposure;

	@Transient
	private String IsRadiationExposure;

	@Transient
	private String IsThyroidDisorder;

	@Expose
	@Column(name = "IsThyroidDisorder")
	private Boolean isThyroidDisorder;
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
	private Timestamp CreatedDate;
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
	@JsonIgnore
	private List<String> typeOfOilConsumedList;

	public BenPersonalCancerDietHistory() {
	}

	public BenPersonalCancerDietHistory(Long iD, Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID,
			String dietType, Integer fruitConsumptionDays, Integer fruitQuantityPerDay,
			Integer vegetableConsumptionDays, Integer vegetableQuantityPerDay, Integer intakeOfOutsidePreparedMeal,
			String typeOfOilConsumed, String physicalActivityType, Boolean ssRadiationExposure,
			Boolean isThyroidDisorder, Boolean deleted, String processed, String createdBy, Timestamp createdDate,
			String modifiedBy, Timestamp lastModDate, List<String> typeOfOilConsumedList) {
		super();
		ID = iD;
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.dietType = dietType;
		this.fruitConsumptionDays = fruitConsumptionDays;
		this.fruitQuantityPerDay = fruitQuantityPerDay;
		this.vegetableConsumptionDays = vegetableConsumptionDays;
		this.vegetableQuantityPerDay = vegetableQuantityPerDay;
		this.intakeOfOutsidePreparedMeal = intakeOfOutsidePreparedMeal;
		this.typeOfOilConsumed = typeOfOilConsumed;
		this.physicalActivityType = physicalActivityType;
		this.ssRadiationExposure = ssRadiationExposure;
		this.isThyroidDisorder = isThyroidDisorder;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		CreatedDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
		this.typeOfOilConsumedList = typeOfOilConsumedList;
	}

	@Transient
	private Date captureDate;

	public BenPersonalCancerDietHistory(String dietType, Integer fruitConsumptionDays, Integer fruitQuantityPerDay,
			Integer vegetableConsumptionDays, Integer vegetableQuantityPerDay, Integer intakeOfOutsidePreparedMeal,
			String typeOfOilConsumed, String physicalActivityType, Boolean ssRadiationExposure,
			Boolean isThyroidDisorder, Date captureDate) {
		super();
		this.dietType = dietType;
		this.fruitConsumptionDays = fruitConsumptionDays;
		this.fruitQuantityPerDay = fruitQuantityPerDay;
		this.vegetableConsumptionDays = vegetableConsumptionDays;
		this.vegetableQuantityPerDay = vegetableQuantityPerDay;
		this.intakeOfOutsidePreparedMeal = intakeOfOutsidePreparedMeal;
		this.typeOfOilConsumed = typeOfOilConsumed;
		this.physicalActivityType = physicalActivityType;
		if (null != ssRadiationExposure && ssRadiationExposure)
			this.IsRadiationExposure = "Yes";
		else
			this.IsRadiationExposure = "No";
		if (null != isThyroidDisorder && isThyroidDisorder)
			this.IsThyroidDisorder = "Yes";
		else
			this.IsThyroidDisorder = "No";

		this.captureDate = captureDate;
	}

	public String getIsRadiationExposure() {
		return IsRadiationExposure;
	}

	public void setIsRadiationExposure(String isRadiationExposure) {
		IsRadiationExposure = isRadiationExposure;
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

	public void setIsThyroidDisorder(String isThyroidDisorder) {
		IsThyroidDisorder = isThyroidDisorder;
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

	public String getDietType() {
		return dietType;
	}

	public void setDietType(String dietType) {
		this.dietType = dietType;
	}

	public Integer getFruitConsumptionDays() {
		return fruitConsumptionDays;
	}

	public void setFruitConsumptionDays(Integer fruitConsumptionDays) {
		this.fruitConsumptionDays = fruitConsumptionDays;
	}

	public Integer getFruitQuantityPerDay() {
		return fruitQuantityPerDay;
	}

	public void setFruitQuantityPerDay(Integer fruitQuantityPerDay) {
		this.fruitQuantityPerDay = fruitQuantityPerDay;
	}

	public Integer getVegetableConsumptionDays() {
		return vegetableConsumptionDays;
	}

	public void setVegetableConsumptionDays(Integer vegetableConsumptionDays) {
		this.vegetableConsumptionDays = vegetableConsumptionDays;
	}

	public Integer getVegetableQuantityPerDay() {
		return vegetableQuantityPerDay;
	}

	public void setVegetableQuantityPerDay(Integer vegetableQuantityPerDay) {
		this.vegetableQuantityPerDay = vegetableQuantityPerDay;
	}

	public Integer getIntakeOfOutsidePreparedMeal() {
		return intakeOfOutsidePreparedMeal;
	}

	public void setIntakeOfOutsidePreparedMeal(Integer intakeOfOutsidePreparedMeal) {
		this.intakeOfOutsidePreparedMeal = intakeOfOutsidePreparedMeal;
	}

	public String getTypeOfOilConsumed() {
		return typeOfOilConsumed;
	}

	public void setTypeOfOilConsumed(String typeOfOilConsumed) {
		this.typeOfOilConsumed = typeOfOilConsumed;
	}

	public String getPhysicalActivityType() {
		return physicalActivityType;
	}

	public void setPhysicalActivityType(String physicalActivityType) {
		this.physicalActivityType = physicalActivityType;
	}

	public Boolean getSsRadiationExposure() {
		return ssRadiationExposure;
	}

	public void setSsRadiationExposure(Boolean ssRadiationExposure) {
		this.ssRadiationExposure = ssRadiationExposure;
	}

	public Boolean getIsThyroidDisorder() {
		return isThyroidDisorder;
	}

	public void setIsThyroidDisorder(Boolean isThyroidDisorder) {
		this.isThyroidDisorder = isThyroidDisorder;
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
		return CreatedDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		CreatedDate = createdDate;
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

	public List<String> getTypeOfOilConsumedList() {
		return typeOfOilConsumedList;
	}

	public void setTypeOfOilConsumedList(List<String> typeOfOilConsumedList) {
		this.typeOfOilConsumedList = typeOfOilConsumedList;
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
