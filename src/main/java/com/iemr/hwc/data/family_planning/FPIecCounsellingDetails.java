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
package com.iemr.hwc.data.family_planning;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.hwc.annotation.sqlInjectionSafe.SQLInjectionSafe;

@Entity
@Table(name = "t_FPIecCounsellingDetails")
public class FPIecCounsellingDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	@Column(name = "Id", insertable = false)
	private Long id;

	@Expose
	@Column(name = "BeneficiaryRegID")
	private Long beneficiaryRegID;

	@Expose
	@Column(name = "Benvisitid")
	private Long benVisitID;

	@Expose
	@Column(name = "Visitcode")
	private Long visitCode;

	@Expose
	@Column(name = "FPCounselledOnId")
	private Integer fPCounselledOnId;

	@Expose
	@Column(name = "CounselledOn")
	private String counselledOnDB;

	@Expose
	@Column(name = "Other_CounselledOn")
	private String otherCounselledOn;

	@Expose
	@Column(name = "TypeOfContraceptiveOpted")
	private String typeOfContraceptiveOptedDB;

	@Expose
	@Column(name = "Other_TypeOfContraceptiveOpted")
	private String otherTypeOfContraceptiveOpted;

	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;

	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private @SQLInjectionSafe String processed;

	@Expose
	@Column(name = "CreatedBy", updatable = false)
	private @SQLInjectionSafe String createdBy;

	@Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;

	@Expose
	@Column(name = "ModifiedBy", insertable = false, updatable = true)
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

	@Transient
	private String[] counselledOn;

	@Transient
	private String[] typeOfContraceptiveOpted;

	public Long getID() {
		return id;
	}

	public void setID(Long iD) {
		id = iD;
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

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

	public Integer getfPCounselledOnId() {
		return fPCounselledOnId;
	}

	public void setfPCounselledOnId(Integer fPCounselledOnId) {
		this.fPCounselledOnId = fPCounselledOnId;
	}

	public String getCounselledOnDB() {
		return counselledOnDB;
	}

	public void setCounselledOnDB(String counselledOnDB) {
		this.counselledOnDB = counselledOnDB;
	}

	public String getOtherCounselledOn() {
		return otherCounselledOn;
	}

	public void setOtherCounselledOn(String otherCounselledOn) {
		this.otherCounselledOn = otherCounselledOn;
	}

	public String getTypeOfContraceptiveOptedDB() {
		return typeOfContraceptiveOptedDB;
	}

	public void setTypeOfContraceptiveOptedDB(String typeOfContraceptiveOptedDB) {
		this.typeOfContraceptiveOptedDB = typeOfContraceptiveOptedDB;
	}

	public String getOtherTypeOfContraceptiveOpted() {
		return otherTypeOfContraceptiveOpted;
	}

	public void setOtherTypeOfContraceptiveOpted(String otherTypeOfContraceptiveOpted) {
		this.otherTypeOfContraceptiveOpted = otherTypeOfContraceptiveOpted;
	}

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
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

	public String[] getCounselledOn() {
		return counselledOn;
	}

	public void setCounselledOn(String[] counselledOn) {
		this.counselledOn = counselledOn;
	}

	public String[] getTypeOfContraceptiveOpted() {
		return typeOfContraceptiveOpted;
	}

	public void setTypeOfContraceptiveOpted(String[] typeOfContraceptiveOpted) {
		this.typeOfContraceptiveOpted = typeOfContraceptiveOpted;
	}

}
