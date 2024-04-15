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
package com.iemr.hwc.data.quickConsultation;

import java.sql.Timestamp;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.hwc.data.snomedct.SCTDescription;

@Entity
@Table(name = "t_prescription")
public class PrescriptionDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	@Column(name = "PrescriptionID", insertable = false, updatable = false)
	private Long prescriptionID;

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
	@Column(name = "DiagnosisProvided")
	private String diagnosisProvided;

	@Expose
	@Column(name = "DiagnosisProvided_SCTCode")
	private String diagnosisProvided_SCTCode;

	@Expose
	@Column(name = "DiagnosisProvided_SCTTerm")
	private String diagnosisProvided_SCTTerm;

	@Expose
	@Column(name = "Instruction")
	private String instruction;

	@Expose
	@Column(name = "ConfirmatoryDiagnosis")
	private String confirmatoryDiagnosis;

	@Expose
	@Column(name = "ExternalInvestigation")
	private String externalInvestigation;

	@Expose
	@Column(name = "Remarks")
	private String remarks;

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
	@Column(name = "VanID")
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
	private ArrayList<PrescribedDrugDetail> prescribedDrugs;

	@Expose
	@Transient
	private ArrayList<SCTDescription> provisionalDiagnosisList;

	@Transient
	private Boolean diabetesScreeningConfirmed;
	@Transient
	private Boolean hypertensionScreeningConfirmed;
	@Transient
	private Boolean oralCancerConfirmed;
	@Transient
	private Boolean breastCancerConfirmed;
	@Transient
	private Boolean cervicalCancerConfirmed;

	@Expose
	@Column(name = "treatments_SideEffects")
	private String treatmentsOnSideEffects;

	@Expose
	@Column(name = "Counsellingprovided")
	private String counsellingProvided;
	
	@Transient
	private String[] counsellingProvidedList;


	public String getTreatmentsOnSideEffects() {
		return treatmentsOnSideEffects;
	}

	public void setTreatmentsOnSideEffects(String treatmentsOnSideEffects) {
		this.treatmentsOnSideEffects = treatmentsOnSideEffects;
	}

	public Boolean getDiabetesScreeningConfirmed() {
		return diabetesScreeningConfirmed;
	}

	public void setDiabetesScreeningConfirmed(Boolean diabetesScreeningConfirmed) {
		this.diabetesScreeningConfirmed = diabetesScreeningConfirmed;
	}

	public Boolean getHypertensionScreeningConfirmed() {
		return hypertensionScreeningConfirmed;
	}

	public void setHypertensionScreeningConfirmed(Boolean hypertensionScreeningConfirmed) {
		this.hypertensionScreeningConfirmed = hypertensionScreeningConfirmed;
	}

	public Boolean getOralCancerConfirmed() {
		return oralCancerConfirmed;
	}

	public void setOralCancerConfirmed(Boolean oralCancerConfirmed) {
		this.oralCancerConfirmed = oralCancerConfirmed;
	}

	public Boolean getBreastCancerConfirmed() {
		return breastCancerConfirmed;
	}

	public void setBreastCancerConfirmed(Boolean breastCancerConfirmed) {
		this.breastCancerConfirmed = breastCancerConfirmed;
	}

	public Boolean getCervicalCancerConfirmed() {
		return cervicalCancerConfirmed;
	}

	public void setCervicalCancerConfirmed(Boolean cervicalCancerConfirmed) {
		this.cervicalCancerConfirmed = cervicalCancerConfirmed;
	}

	public String getDiagnosisProvided_SCTCode() {
		return diagnosisProvided_SCTCode;
	}

	public void setDiagnosisProvided_SCTCode(String diagnosisProvided_SCTCode) {
		this.diagnosisProvided_SCTCode = diagnosisProvided_SCTCode;
	}

	public String getDiagnosisProvided_SCTTerm() {
		return diagnosisProvided_SCTTerm;
	}

	public void setDiagnosisProvided_SCTTerm(String diagnosisProvided_SCTTerm) {
		this.diagnosisProvided_SCTTerm = diagnosisProvided_SCTTerm;
	}

	public ArrayList<PrescribedDrugDetail> getPrescribedDrugs() {
		return prescribedDrugs;
	}

	public void setPrescribedDrugs(ArrayList<PrescribedDrugDetail> prescribedDrugs) {
		this.prescribedDrugs = prescribedDrugs;
	}

	public PrescriptionDetail() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getDiagnosisProvided() {
		return diagnosisProvided;
	}

	public void setDiagnosisProvided(String diagnosisProvided) {
		this.diagnosisProvided = diagnosisProvided;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public Long getPrescriptionID() {
		return prescriptionID;
	}

	public String getConfirmatoryDiagnosis() {
		return confirmatoryDiagnosis;
	}

	public void setConfirmatoryDiagnosis(String confirmatoryDiagnosis) {
		this.confirmatoryDiagnosis = confirmatoryDiagnosis;
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

	public void setPrescriptionID(Long prescriptionID) {
		this.prescriptionID = prescriptionID;
	}

	public String getExternalInvestigation() {
		return externalInvestigation;
	}

	public void setExternalInvestigation(String externalInvestigation) {
		this.externalInvestigation = externalInvestigation;
	}

	public PrescriptionDetail(Long prescriptionID, Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID,
			String diagnosisProvided, String instruction, String externalInvestigation, Long visitCode) {
		super();
		this.prescriptionID = prescriptionID;
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.diagnosisProvided = diagnosisProvided;
		this.instruction = instruction;
		this.externalInvestigation = externalInvestigation;
		this.visitCode = visitCode;
	}

	public PrescriptionDetail(Long prescriptionID, Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID,
			String diagnosisProvided, String instruction, Long visitCode) {
		super();
		this.prescriptionID = prescriptionID;
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.diagnosisProvided = diagnosisProvided;
		this.instruction = instruction;
		this.visitCode = visitCode;
	}

	public static PrescriptionDetail getPrescriptions(ArrayList<Object[]> resList) {
		PrescriptionDetail cOBJ = null;
		if (resList != null && resList.size() > 0) {
			Object[] obj = resList.get(0);
			cOBJ = new PrescriptionDetail((Long) obj[0], (Long) obj[1], (Long) obj[2], (Integer) obj[3],
					(String) obj[4], (String) obj[5], (String) obj[6], (Long) obj[7]);
		}
		return cOBJ;
	}

	public static PrescriptionDetail getGeneralOPDDiagnosis(ArrayList<Object[]> resList) {
		PrescriptionDetail cOBJ = null;
		if (resList != null && resList.size() > 0) {
			Object[] obj = resList.get(0);
			cOBJ = new PrescriptionDetail((Long) obj[0], (Long) obj[1], (Long) obj[2], (Integer) obj[3],
					(String) obj[4], (String) obj[5], (Long) obj[6]);

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

	public ArrayList<SCTDescription> getProvisionalDiagnosisList() {
		return provisionalDiagnosisList;
	}

	public void setProvisionalDiagnosisList(ArrayList<SCTDescription> provisionalDiagnosisList) {
		this.provisionalDiagnosisList = provisionalDiagnosisList;
	}

	public String getCounsellingProvided() {
		return counsellingProvided;
	}

	public void setCounsellingProvided(String counsellingProvided) {
		this.counsellingProvided = counsellingProvided;
	}

	public String[] getCounsellingProvidedList() {
		return counsellingProvidedList;
	}

	public void setCounsellingProvidedList(String[] counsellingProvidedList) {
		this.counsellingProvidedList = counsellingProvidedList;
	}
	
	

}
