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
package com.iemr.mmu.data.ncdcare;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.data.snomedct.SCTDescription;

@Entity
@Table(name = "t_ncddiagnosis")
public class NCDCareDiagnosis {
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
	@Column(name = "PrescriptionID")
	private Long prescriptionID;

	@Expose
	@Column(name = "NCD_Condition")
	private String ncdScreeningCondition;

	// 07-09-2021
	@Expose
	@Transient
	private String[] ncdScreeningConditionArray;

	@Expose
	@Column(name = "NCDConditionOther")
	private String ncdScreeningConditionOther;

	// End

	@Expose
	@Column(name = "NCD_Complication")
	private String ncdComplication;

	@Expose
	@Column(name = "NCDCareType")
	private String ncdCareType;

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

	@Transient
	@Expose
	private String externalInvestigation;

	@Transient
	@Expose
	private String specialistDiagnosis;
	
	@Transient
	@Expose
	private String counsellingProvided;
	
	@Expose
	@Transient
	private ArrayList<SCTDescription> provisionalDiagnosisList;
	
	@Expose
	@Transient
	private String diagnosisProvided;
	
	@Expose
	@Transient
	private String diagnosisProvided_SCTCode;
	
	

	public String getDiagnosisProvided() {
		return diagnosisProvided;
	}

	public void setDiagnosisProvided(String diagnosisProvided) {
		this.diagnosisProvided = diagnosisProvided;
	}

	public String getDiagnosisProvided_SCTCode() {
		return diagnosisProvided_SCTCode;
	}

	public void setDiagnosisProvided_SCTCode(String diagnosisProvided_SCTCode) {
		this.diagnosisProvided_SCTCode = diagnosisProvided_SCTCode;
	}

	public ArrayList<SCTDescription> getProvisionalDiagnosisList() {
		return provisionalDiagnosisList;
	}

	public void setProvisionalDiagnosisList(ArrayList<SCTDescription> provisionalDiagnosisList) {
		this.provisionalDiagnosisList = provisionalDiagnosisList;
	}

	public String getNcdScreeningCondition() {
		return ncdScreeningCondition;
	}

	public void setNcdScreeningCondition(String ncdScreeningCondition) {
		this.ncdScreeningCondition = ncdScreeningCondition;
	}

	public String getExternalInvestigation() {
		return externalInvestigation;
	}

	public void setExternalInvestigation(String externalInvestigation) {
		this.externalInvestigation = externalInvestigation;
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

	public String getNcdCareCondition() {
		return ncdScreeningCondition;
	}

	public void setNcdCareCondition(String ncdCareCondition) {
		this.ncdScreeningCondition = ncdCareCondition;
	}

	public String getNcdComplication() {
		return ncdComplication;
	}

	public void setNcdComplication(String ncdComplication) {
		this.ncdComplication = ncdComplication;
	}

	public String getNcdCareType() {
		return ncdCareType;
	}

	public void setNcdCareType(String ncdCareType) {
		this.ncdCareType = ncdCareType;
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

	public Long getPrescriptionID() {
		return prescriptionID;
	}

	public void setPrescriptionID(Long prescriptionID) {
		this.prescriptionID = prescriptionID;
	}

	public String[] getNcdScreeningConditionArray() {
		return ncdScreeningConditionArray;
	}

	public void setNcdScreeningConditionArray(String[] ncdScreeningConditionArray) {
		this.ncdScreeningConditionArray = ncdScreeningConditionArray;
	}

	public String getNcdScreeningConditionOther() {
		return ncdScreeningConditionOther;
	}

	public void setNcdScreeningConditionOther(String ncdScreeningConditionOther) {
		this.ncdScreeningConditionOther = ncdScreeningConditionOther;
	}

	public NCDCareDiagnosis(Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID, Long prescriptionID,
			String ncdCareCondition, String ncdComplication, String ncdCareType, Long visitCode,
			String externalInvestigation, String ncdCareConditionOther) {
		super();
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.prescriptionID = prescriptionID;
		this.ncdScreeningCondition = ncdCareCondition;
		this.ncdComplication = ncdComplication;
		this.ncdCareType = ncdCareType;
		this.visitCode = visitCode;
		this.externalInvestigation = externalInvestigation;
		this.ncdScreeningConditionOther = ncdCareConditionOther;
	}

	public static NCDCareDiagnosis getNCDCareDiagnosisDetails(ArrayList<Object[]> resList) {
		NCDCareDiagnosis cOBJ = null;
		if (null != resList && resList.size() > 0) {
			Object[] obj = resList.get(0);
			cOBJ = new NCDCareDiagnosis((Long) obj[0], (Long) obj[1], (Integer) obj[2], (Long) obj[3], (String) obj[4],
					(String) obj[5], (String) obj[6], (Long) obj[7], null, (String) obj[8]);

		}
		return cOBJ;
	}

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public String getSpecialistDiagnosis() {
		return specialistDiagnosis;
	}

	public void setSpecialistDiagnosis(String specialistDiagnosis) {
		this.specialistDiagnosis = specialistDiagnosis;
	}

	public String getCounsellingProvided() {
		return counsellingProvided;
	}

	public void setCounsellingProvided(String counsellingProvided) {
		this.counsellingProvided = counsellingProvided;
	}

}
