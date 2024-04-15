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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.google.gson.annotations.Expose;
import com.iemr.hwc.annotation.sqlInjectionSafe.SQLInjectionSafe;

@Entity
@Table(name = "t_phy_anthropometry")
public class BenAnthropometryDetail {
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
	@Column(name = "Weight_Kg")
	private Double weight_Kg;
	
	@Expose
	@Column(name = "Height_cm")
	private Double height_cm;
	@Expose
	@Column(name = "BMI")
	private Double bMI;
	@Expose
	@Column(name = "HeadCircumference_cm")
	private Double headCircumference_cm;
	@Expose
	@Column(name = "MidUpperArmCircumference_MUAC_cm")
	private Double midUpperArmCircumference_MUAC_cm;
	@Expose
	@Column(name = "HipCircumference_cm")
	private Double hipCircumference_cm;
	@Expose
	@Column(name = "WaistCircumference_cm")
	private Double waistCircumference_cm;
	@Expose
	@Column(name = "WaistHipRatio")
	private Double waistHipRatio;
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

	public BenAnthropometryDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
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

	public Double getWeight_Kg() {
		return weight_Kg;
	}

	public void setWeight_Kg(Double weight_Kg) {
		this.weight_Kg = weight_Kg;
	}

	public Double getHeight_cm() {
		return height_cm;
	}

	public void setHeight_cm(Double height_cm) {
		this.height_cm = height_cm;
	}

	public Double getbMI() {
		return bMI;
	}

	public void setbMI(Double bMI) {
		this.bMI = bMI;
	}

	public Double getHeadCircumference_cm() {
		return headCircumference_cm;
	}

	public void setHeadCircumference_cm(Double headCircumference_cm) {
		this.headCircumference_cm = headCircumference_cm;
	}

	public Double getMidUpperArmCircumference_MUAC_cm() {
		return midUpperArmCircumference_MUAC_cm;
	}

	public void setMidUpperArmCircumference_MUAC_cm(Double midUpperArmCircumference_MUAC_cm) {
		this.midUpperArmCircumference_MUAC_cm = midUpperArmCircumference_MUAC_cm;
	}

	public Double getHipCircumference_cm() {
		return hipCircumference_cm;
	}

	public void setHipCircumference_cm(Double hipCircumference_cm) {
		this.hipCircumference_cm = hipCircumference_cm;
	}

	public Double getWaistCircumference_cm() {
		return waistCircumference_cm;
	}

	public void setWaistCircumference_cm(Double waistCircumference_cm) {
		this.waistCircumference_cm = waistCircumference_cm;
	}

	public Double getWaistHipRatio() {
		return waistHipRatio;
	}

	public void setWaistHipRatio(Double waistHipRatio) {
		this.waistHipRatio = waistHipRatio;
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

	public Long getID() {
		return ID;
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

	public void setID(Long iD) {
		ID = iD;
	}

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

}
