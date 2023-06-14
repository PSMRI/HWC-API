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
package com.iemr.mmu.data.quickConsultation;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "t_prescribeddrug")
public class PrescribedDrugDetail {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "PrescribedDrugID", insertable = false, updatable = false)
	private Long id;

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
	@Column(name = "PrescriptionID")
	private Long prescriptionID;

	@Expose
	@Column(name = "DrugForm")
	private String formName;

	@Expose
	@Column(name = "DrugTradeOrBrandName")
	private String drugTradeOrBrandName;

	@Expose
	@Column(name = "DrugID")
	private Integer drugID;

	@Expose
	@Column(name = "GenericDrugName")
	private String drugName;

	@Expose
	@Column(name = "DrugStrength")
	private String drugStrength;

	@Expose
	@Column(name = "Dose")
	private String dose;

	@Expose
	@Column(name = "Route")
	private String route;

	@Expose
	@Column(name = "Frequency")
	private String frequency;

	@Expose
	@Column(name = "Duration")
	private String duration;

	@Expose
	@Column(name = "DuartionUnit")
	private String unit;

	@Expose
	@Column(name = "RelationToFood")
	private String relationToFood;

	@Expose
	@Column(name = "SpecialInstruction")
	private String instructions;

	@Expose
	@Column(name = "QtyPrescribed")
	private Integer qtyPrescribed;

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
	@Column(name = "isEDL")
	private Boolean isEDL;
	
	@Expose
	@Column(name = "Sctcode")
	private String sctCode;
	
	@Expose
	@Column(name = "SctTerm")
	private String sctTerm;
	
	public PrescribedDrugDetail() {
	}

	public PrescribedDrugDetail(Long prescribedDrugID, Long prescriptionID, String drugForm,
			String drugTradeOrBrandName, Integer drugID, String genericDrugName, String drugStrength, String dose,
			String route, String frequency, String drugDuration, String drugDurationUnit, String relationToFood,
			String specialInstruction, Integer qtyPrescribed , Boolean isEDL, String sctCode, String sctTerm, Timestamp createdDate) {
		super();
		this.id = prescribedDrugID;
		this.prescriptionID = prescriptionID;
		this.formName = drugForm;
		this.drugTradeOrBrandName = drugTradeOrBrandName;
		this.drugID = drugID;
		this.drugName = genericDrugName;
		this.drugStrength = drugStrength;
		this.dose = dose;
		this.route = route;
		this.frequency = frequency;
		this.duration = drugDuration;
		this.unit = drugDurationUnit;
		this.relationToFood = relationToFood;
		this.instructions = specialInstruction;
		this.qtyPrescribed = qtyPrescribed;
		this.isEDL = isEDL;
		this.sctCode = sctCode;
		this.sctTerm = sctTerm;
		this.createdDate = createdDate;
	}

	public static ArrayList<PrescribedDrugDetail> getprescribedDrugs(ArrayList<Object[]> resList) {
		ArrayList<PrescribedDrugDetail> resArray = new ArrayList<PrescribedDrugDetail>();
		PrescribedDrugDetail cOBJ = null;
		if (resList != null && resList.size() > 0) {
			for (Object[] obj : resList) {
				cOBJ = new PrescribedDrugDetail((Long) obj[0], (Long) obj[1], (String) obj[2], (String) obj[3],
						(Integer) obj[4], (String) obj[5], (String) obj[6], (String) obj[7], (String) obj[8],
						(String) obj[9], (String) obj[10], (String) obj[11], (String) obj[12], (String) obj[13],
						(Integer) obj[14], (Boolean)obj[15], (String) obj[16], (String) obj[17], (Timestamp) obj[18]);
				resArray.add(cOBJ);
			}
		}
		return resArray;
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

	public Long getPrescriptionID() {
		return prescriptionID;
	}

	public void setPrescriptionID(Long prescriptionID) {
		this.prescriptionID = prescriptionID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getDrugTradeOrBrandName() {
		return drugTradeOrBrandName;
	}

	public void setDrugTradeOrBrandName(String drugTradeOrBrandName) {
		this.drugTradeOrBrandName = drugTradeOrBrandName;
	}

	public Integer getDrugID() {
		return drugID;
	}

	public void setDrugID(Integer drugID) {
		this.drugID = drugID;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getDrugStrength() {
		return drugStrength;
	}

	public void setDrugStrength(String drugStrength) {
		this.drugStrength = drugStrength;
	}

	public String getDose() {
		return dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getRelationToFood() {
		return relationToFood;
	}

	public void setRelationToFood(String relationToFood) {
		this.relationToFood = relationToFood;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
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

	public Integer getQtyPrescribed() {
		return qtyPrescribed;
	}

	public void setQtyPrescribed(Integer qtyPrescribed) {
		this.qtyPrescribed = qtyPrescribed;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
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

}
