/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.anc;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.annotation.sqlInjectionSafe.SQLInjectionSafe;

@Entity
@Table(name = "t_ancdiagnosis")
public class ANCDiagnosis {

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
	@Column(name = "HighRiskStatus")
	private String highRiskStatus;

	@Expose
	@Column(name = "HighRiskCondition")
	private String highRiskCondition;

	@Expose
	@Column(name = "ComplicationOfCurrentPregnancy")
	private String complicationOfCurrentPregnancy;

	@Expose
	@Column(name = "IsMaternalDeath")
	private Boolean isMaternalDeath;

	@Expose
	@Column(name = "PlaceOfDeath")
	private String placeOfDeath;

	@Expose
	@Column(name = "DateOfDeath")
	private Date dateOfDeath;

	@Expose
	@Column(name = "CauseOfDeath")
	private String causeOfDeath;

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

	@Transient
	@Expose
	private ArrayList<Map<String, String>> complicationOfCurrentPregnancyList;

	@Expose
	@Column(name = "OtherCompCurPreg ")
	private String otherCurrPregComplication;

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

	public String getHighRiskStatus() {
		return highRiskStatus;
	}

	public void setHighRiskStatus(String highRiskStatus) {
		this.highRiskStatus = highRiskStatus;
	}

	public String getHighRiskCondition() {
		return highRiskCondition;
	}

	public void setHighRiskCondition(String highRiskCondition) {
		this.highRiskCondition = highRiskCondition;
	}

	public String getComplicationOfCurrentPregnancy() {
		return complicationOfCurrentPregnancy;
	}

	public void setComplicationOfCurrentPregnancy(String complicationOfCurrentPregnancy) {
		this.complicationOfCurrentPregnancy = complicationOfCurrentPregnancy;
	}

	public Boolean getIsMaternalDeath() {
		return isMaternalDeath;
	}

	public void setIsMaternalDeath(Boolean isMaternalDeath) {
		this.isMaternalDeath = isMaternalDeath;
	}

	public String getPlaceOfDeath() {
		return placeOfDeath;
	}

	public void setPlaceOfDeath(String placeOfDeath) {
		this.placeOfDeath = placeOfDeath;
	}

	public Date getDateOfDeath() {
		return dateOfDeath;
	}

	public void setDateOfDeath(Date dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}

	public String getCauseOfDeath() {
		return causeOfDeath;
	}

	public void setCauseOfDeath(String causeOfDeath) {
		this.causeOfDeath = causeOfDeath;
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

	public ANCDiagnosis() {
	}

	public ANCDiagnosis(Long iD, Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID,
			Long prescriptionID, String highRiskStatus, String highRiskCondition, String complicationOfCurrentPregnancy,
			Boolean isMaternalDeath, String placeOfDeath, Date dateOfDeath, String causeOfDeath, Long visitCode,
			String externalInvestigation) {
		super();
		ID = iD;
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.prescriptionID = prescriptionID;
		this.highRiskStatus = highRiskStatus;
		this.highRiskCondition = highRiskCondition;
		this.complicationOfCurrentPregnancy = complicationOfCurrentPregnancy;
		this.isMaternalDeath = isMaternalDeath;
		this.placeOfDeath = placeOfDeath;
		this.dateOfDeath = dateOfDeath;
		this.causeOfDeath = causeOfDeath;
		this.visitCode = visitCode;
		this.externalInvestigation = externalInvestigation;
	}

	public static ANCDiagnosis getANCDiagnosisDetails(ArrayList<Object[]> resList) {
		ANCDiagnosis cOBJ = null;
		if (null != resList && resList.size() > 0) {
			Object[] obj = resList.get(0);
			cOBJ = new ANCDiagnosis((Long) obj[0], (Long) obj[1], (Long) obj[2], (Integer) obj[3], (Long) obj[4],
					(String) obj[5], (String) obj[6], (String) obj[7], (Boolean) obj[8], (String) obj[9],
					(Date) obj[10], (String) obj[11], (Long) obj[12], null);

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

	public ArrayList<Map<String, String>> getComplicationOfCurrentPregnancyList() {
		return complicationOfCurrentPregnancyList;
	}

	public void setComplicationOfCurrentPregnancyList(
			ArrayList<Map<String, String>> complicationOfCurrentPregnancyList) {
		this.complicationOfCurrentPregnancyList = complicationOfCurrentPregnancyList;
	}

	public String getOtherCurrPregComplication() {
		return otherCurrPregComplication;
	}

	public void setOtherCurrPregComplication(String otherCurrPregComplication) {
		this.otherCurrPregComplication = otherCurrPregComplication;
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
