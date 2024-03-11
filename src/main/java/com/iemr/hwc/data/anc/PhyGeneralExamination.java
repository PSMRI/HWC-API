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
@Table(name = "t_Phy_GeneralExam")
public class PhyGeneralExamination {
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
	@Column(name = "Consciousness")
	private String consciousness;

	@Expose
	@Column(name = "Coherence")
	private String coherence;

	@Expose
	@Column(name = "Cooperation")
	private String cooperation;

	@Expose
	@Column(name = "Comfortness")
	private String comfortness;

	@Expose
	@Column(name = "BuiltAndAppearance")
	private String builtAndAppearance;

	@Expose
	@Column(name = "Gait")
	private String gait;

	@Expose
	@Column(name = "DangerSigns")
	private String dangerSigns;

	@Expose
	@Column(name = "TypeOfDangerSign")
	private String typeOfDangerSign;
	
	@Transient
	List<String> typeOfDangerSigns;

	@Expose
	@Column(name = "Pallor")
	private String pallor;

	@Expose
	@Column(name = "Jaundice")
	private String jaundice;

	@Expose
	@Column(name = "Cyanosis")
	private String cyanosis;

	@Expose
	@Column(name = "Clubbing")
	private String clubbing;

	@Expose
	@Column(name = "Lymphadenopathy")
	private String lymphadenopathy;

	@Expose
	@Column(name = "LymphnodesInvolved")
	private String lymphnodesInvolved;

	@Expose
	@Column(name = "TypeOfLymphadenopathy")
	private String typeOfLymphadenopathy;

	@Expose
	@Column(name = "Edema")
	private String edema;

	@Expose
	@Column(name = "ExtentOfEdema")
	private String extentOfEdema;

	@Expose
	@Column(name = "EdemaType")
	private String edemaType;
	
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
	
	@Expose
	@Column(name = "Quickening")
	private String quickening;
	
	@Expose
	@Column(name = "Foetal_movements")
	private String foetalMovements;
	
	

	public String getQuickening() {
		return quickening;
	}

	public void setQuickening(String quickening) {
		this.quickening = quickening;
	}

	public String getFoetalMovements() {
		return foetalMovements;
	}

	public void setFoetalMovements(String foetalMovements) {
		this.foetalMovements = foetalMovements;
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

	public String getConsciousness() {
		return consciousness;
	}

	public void setConsciousness(String consciousness) {
		this.consciousness = consciousness;
	}

	public String getCoherence() {
		return coherence;
	}

	public void setCoherence(String coherence) {
		this.coherence = coherence;
	}

	public String getCooperation() {
		return cooperation;
	}

	public void setCooperation(String cooperation) {
		this.cooperation = cooperation;
	}

	public String getComfortness() {
		return comfortness;
	}

	public void setComfortness(String comfortness) {
		this.comfortness = comfortness;
	}

	public String getBuiltAndAppearance() {
		return builtAndAppearance;
	}

	public void setBuiltAndAppearance(String builtAndAppearance) {
		this.builtAndAppearance = builtAndAppearance;
	}

	public String getGait() {
		return gait;
	}

	public void setGait(String gait) {
		this.gait = gait;
	}

	public String getDangerSigns() {
		return dangerSigns;
	}

	public void setDangerSigns(String dangerSigns) {
		this.dangerSigns = dangerSigns;
	}

	public String getTypeOfDangerSign() {
		return typeOfDangerSign;
	}

	public void setTypeOfDangerSign(String typeOfDangerSign) {
		this.typeOfDangerSign = typeOfDangerSign;
	}

	public List<String> getTypeOfDangerSigns() {
		return typeOfDangerSigns;
	}

	public void setTypeOfDangerSigns(List<String> typeOfDangerSigns) {
		this.typeOfDangerSigns = typeOfDangerSigns;
	}

	public String getPallor() {
		return pallor;
	}

	public void setPallor(String pallor) {
		this.pallor = pallor;
	}

	public String getJaundice() {
		return jaundice;
	}

	public void setJaundice(String jaundice) {
		this.jaundice = jaundice;
	}

	public String getCyanosis() {
		return cyanosis;
	}

	public void setCyanosis(String cyanosis) {
		this.cyanosis = cyanosis;
	}

	public String getClubbing() {
		return clubbing;
	}

	public void setClubbing(String clubbing) {
		this.clubbing = clubbing;
	}

	public String getLymphadenopathy() {
		return lymphadenopathy;
	}

	public void setLymphadenopathy(String lymphadenopathy) {
		this.lymphadenopathy = lymphadenopathy;
	}

	public String getLymphnodesInvolved() {
		return lymphnodesInvolved;
	}

	public void setLymphnodesInvolved(String lymphnodesInvolved) {
		this.lymphnodesInvolved = lymphnodesInvolved;
	}

	public String getTypeOfLymphadenopathy() {
		return typeOfLymphadenopathy;
	}

	public void setTypeOfLymphadenopathy(String typeOfLymphadenopathy) {
		this.typeOfLymphadenopathy = typeOfLymphadenopathy;
	}

	public String getEdema() {
		return edema;
	}

	public void setEdema(String edema) {
		this.edema = edema;
	}

	public String getExtentOfEdema() {
		return extentOfEdema;
	}

	public void setExtentOfEdema(String extentOfEdema) {
		this.extentOfEdema = extentOfEdema;
	}

	public String getEdemaType() {
		return edemaType;
	}

	public void setEdemaType(String edemaType) {
		this.edemaType = edemaType;
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

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
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
