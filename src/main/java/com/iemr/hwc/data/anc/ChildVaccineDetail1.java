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
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.hwc.annotation.sqlInjectionSafe.SQLInjectionSafe;

@Entity
@Table(name = "t_ChildVaccineDetail1")
public class ChildVaccineDetail1 {

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
	@Column(name = "DefaultReceivingAge")
	private @SQLInjectionSafe String defaultReceivingAge;

	@Expose
	@Column(name = "VaccineName")
	private @SQLInjectionSafe String vaccineName;

	@Expose
	@Column(name = "ActualReceivingAge")
	private @SQLInjectionSafe String actualReceivingAge;

	@Expose
	@Column(name = "Status")
	private Boolean status;

	@Transient
	@Expose
	private @SQLInjectionSafe String Status;

	@Transient
	@Expose
	private List vaccines;

	@Expose
	@Column(name = "ReceivedDate")
	private Timestamp receivedDate;

	@Expose
	@Column(name = "ReceivedFacilityName")
	private @SQLInjectionSafe String receivedFacilityName;

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

	@Transient
	@Expose
	private List<String> vaccineNameList;

	@Transient
	private Date captureDate;

	@Expose
	@Column(name = "Sctcode")
	private @SQLInjectionSafe String sctCode;

	@Expose
	@Column(name = "SctTerm")
	private @SQLInjectionSafe String sctTerm;

	@Expose
	@Column(name = "Vaccinationreceivedat")
	private @SQLInjectionSafe String vaccinationReceivedAt;

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

	public void setStatus(String status) {
		Status = status;
	}

	public List<String> getVaccineNameList() {
		return vaccineNameList;
	}

	public void setVaccineNameList(List<String> vaccineNameList) {
		this.vaccineNameList = vaccineNameList;
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

	public String getDefaultReceivingAge() {
		return defaultReceivingAge;
	}

	public void setDefaultReceivingAge(String defaultReceivingAge) {
		this.defaultReceivingAge = defaultReceivingAge;
	}

	public String getVaccineName() {
		return vaccineName;
	}

	public void setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
	}

	public String getActualReceivingAge() {
		return actualReceivingAge;
	}

	public void setActualReceivingAge(String actualReceivingAge) {
		this.actualReceivingAge = actualReceivingAge;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Timestamp getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Timestamp receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getReceivedFacilityName() {
		return receivedFacilityName;
	}

	public void setReceivedFacilityName(String receivedFacilityName) {
		this.receivedFacilityName = receivedFacilityName;
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

	public List getVaccines() {
		return vaccines;
	}

	public void setVaccines(List vaccines) {
		this.vaccines = vaccines;
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

	public ChildVaccineDetail1() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChildVaccineDetail1(String defaultReceivingAge, String vaccineName, Boolean status, String sctCode,
			String sctTerm) {
		super();
		this.defaultReceivingAge = defaultReceivingAge;
		this.vaccineName = vaccineName;
		this.status = status;
		this.sctCode = sctCode;
		this.sctTerm = sctTerm;
	}

	public ChildVaccineDetail1(Date createdDate, String defaultReceivingAge, String vaccineName, Boolean status) {
		super();
		this.captureDate = createdDate;
		this.defaultReceivingAge = defaultReceivingAge;
		this.vaccineName = vaccineName;
		if (null != status && status)
			this.Status = "Yes";
		else
			this.Status = "No";
	}

	public ChildVaccineDetail1(String defaultReceivingAge) {
		super();
		this.defaultReceivingAge = defaultReceivingAge;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getSctCode() {
		return sctCode;
	}

	public void setSctCode(String sctCode) {
		this.sctCode = sctCode;
	}

	public String getSctTerm() {
		return sctTerm;
	}

	public void setSctTerm(String sctTerm) {
		this.sctTerm = sctTerm;
	}

	public String getVaccinationReceivedAt() {
		return vaccinationReceivedAt;
	}

	public void setVaccinationReceivedAt(String vaccinationReceivedAt) {
		this.vaccinationReceivedAt = vaccinationReceivedAt;
	}

}
