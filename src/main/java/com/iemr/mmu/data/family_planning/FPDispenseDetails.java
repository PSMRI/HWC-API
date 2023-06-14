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
package com.iemr.mmu.data.family_planning;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.annotation.sqlInjectionSafe.SQLInjectionSafe;

@Entity
@Table(name = "t_FPDispenseDetails")
public class FPDispenseDetails {

	@Id
	@GeneratedValue
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
	@Column(name = "TypeOfContraceptivePrescribed")
	private String typeOfContraceptivePrescribedDB;

	@Expose
	@Column(name = "Other_TypeOfContraceptivePrescribed")
	private String otherTypeOfContraceptivePrescribed;

	@Expose
	@Column(name = "Doses_Taken")
	private String dosesTaken;

	@Expose
	@Column(name = "Date_LastDosetaken")
	private Timestamp dateOfLastDoseTaken;

	@Expose
	@Column(name = "QtyPrescribed")
	private Integer qtyPrescribed;

	@Expose
	@Column(name = "NextVisitForRefill")
	private Timestamp nextVisitForRefill;

	@Expose
	@Column(name = "TypeofIUCDinsertedId")
	private Integer typeOfIUCDInsertedId;

	@Expose
	@Column(name = "TypeofIUCDinserted")
	private String typeOfIUCDInserted;

	@Expose
	@Column(name = "DateofIUCDinserted")
	private Timestamp dateOfIUCDInsertion;

	@Expose
	@Column(name = "IUCDinsertiondoneby")
	private String iucdInsertionDoneBy;

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
	private String[] typeOfContraceptivePrescribed;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getTypeOfContraceptivePrescribedDB() {
		return typeOfContraceptivePrescribedDB;
	}

	public void setTypeOfContraceptivePrescribedDB(String typeOfContraceptivePrescribedDB) {
		this.typeOfContraceptivePrescribedDB = typeOfContraceptivePrescribedDB;
	}

	public String getOtherTypeOfContraceptivePrescribed() {
		return otherTypeOfContraceptivePrescribed;
	}

	public void setOtherTypeOfContraceptivePrescribed(String otherTypeOfContraceptivePrescribed) {
		this.otherTypeOfContraceptivePrescribed = otherTypeOfContraceptivePrescribed;
	}

	public String getDosesTaken() {
		return dosesTaken;
	}

	public void setDosesTaken(String dosesTaken) {
		this.dosesTaken = dosesTaken;
	}

	public Timestamp getDateOfLastDoseTaken() {
		return dateOfLastDoseTaken;
	}

	public void setDateOfLastDoseTaken(Timestamp dateOfLastDoseTaken) {
		this.dateOfLastDoseTaken = dateOfLastDoseTaken;
	}

	public Integer getQtyPrescribed() {
		return qtyPrescribed;
	}

	public void setQtyPrescribed(Integer qtyPrescribed) {
		this.qtyPrescribed = qtyPrescribed;
	}

	public Timestamp getNextVisitForRefill() {
		return nextVisitForRefill;
	}

	public void setNextVisitForRefill(Timestamp nextVisitForRefill) {
		this.nextVisitForRefill = nextVisitForRefill;
	}

	public Integer getTypeOfIUCDInsertedId() {
		return typeOfIUCDInsertedId;
	}

	public void setTypeOfIUCDInsertedId(Integer typeOfIUCDInsertedId) {
		this.typeOfIUCDInsertedId = typeOfIUCDInsertedId;
	}

	public String getTypeOfIUCDInserted() {
		return typeOfIUCDInserted;
	}

	public void setTypeOfIUCDInserted(String typeOfIUCDInserted) {
		this.typeOfIUCDInserted = typeOfIUCDInserted;
	}

	public Timestamp getDateOfIUCDInsertion() {
		return dateOfIUCDInsertion;
	}

	public void setDateOfIUCDInsertion(Timestamp dateOfIUCDInsertion) {
		this.dateOfIUCDInsertion = dateOfIUCDInsertion;
	}

	public String getIucdInsertionDoneBy() {
		return iucdInsertionDoneBy;
	}

	public void setIucdInsertionDoneBy(String iucdInsertionDoneBy) {
		this.iucdInsertionDoneBy = iucdInsertionDoneBy;
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

	public String[] getTypeOfContraceptivePrescribed() {
		return typeOfContraceptivePrescribed;
	}

	public void setTypeOfContraceptivePrescribed(String[] typeOfContraceptivePrescribed) {
		this.typeOfContraceptivePrescribed = typeOfContraceptivePrescribed;
	}

}
