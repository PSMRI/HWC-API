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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import lombok.Data;

/**
 * 
 * @author SH20094090 17-08-2022
 */

@Entity
@Data
@Table(name = "t_diabetesscreening")
public class DiabetesScreening {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id", insertable = false)
	private Long id;

	@Column(name = "BeneficiaryRegId")
	private Long beneficiaryRegId;

	@Column(name = "Visitcode")
	private Long visitcode;

	@Column(name = "bloodGlucoseTypeId")
	private Integer bloodGlucoseTypeId;

	@Column(name = "bloodGlucoseType")
	private String bloodGlucoseType;

	@Column(name = "bloodGlucose")
	private Integer bloodGlucose;
	
	@Expose
	@Column(name = "BloodGlucoseRemarks")
	private String bloodGlucoseRemarks;


//	@Column(name = "facilityId")
//	Integer facilityId;

	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

	@Column(name = "Processed", insertable = false, updatable = true)
	private String processed;

	@Column(name = "CreatedBy", insertable = true, updatable = false)
	private String createdBy;

	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;

	@Column(name = "ModifiedBy", insertable = false, updatable = true)
	private String modifiedBy;

	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	@Expose
	@Column(name = "VanSerialNo")
	private Long vanSerialNo;

	@Expose
	@Column(name = "VehicalNo")
	private String vehicalNo;

	@Expose
	@Column(name = "vanID", updatable = false)
	private Integer vanID;

	@Expose
	@Column(name = "ParkingPlaceID", updatable = false)
	private Integer parkingPlaceID;

	@Expose
	@Column(name = "suspected")
	private Boolean suspected;
	@Expose
	@Column(name = "Confirmatory")
	private Boolean confirmed;

	public Boolean getSuspected() {
		return suspected;
	}

	public void setSuspected(Boolean suspected) {
		this.suspected = suspected;
	}

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

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

	public Integer getBloodGlucoseTypeId() {
		return bloodGlucoseTypeId;
	}

	public void setBloodGlucoseTypeId(Integer bloodGlucoseTypeId) {
		this.bloodGlucoseTypeId = bloodGlucoseTypeId;
	}

	public String getBloodGlucoseType() {
		return bloodGlucoseType;
	}

	public void setBloodGlucoseType(String bloodGlucoseType) {
		this.bloodGlucoseType = bloodGlucoseType;
	}

	public Integer getBloodGlucose() {
		return bloodGlucose;
	}

	public void setBloodGlucose(Integer bloodGlucose) {
		this.bloodGlucose = bloodGlucose;
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

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public Integer getParkingPlaceID() {
		return parkingPlaceID;
	}

	public void setParkingPlaceID(Integer parkingPlaceID) {
		this.parkingPlaceID = parkingPlaceID;
	}

	public Long getVisitcode() {
		return visitcode;
	}

	public void setVisitcode(Long visitcode) {
		this.visitcode = visitcode;
	}

}
