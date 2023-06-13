/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.anc;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.annotation.sqlInjectionSafe.SQLInjectionSafe;

@Entity
@Table(name = "t_Sys_Gastrointestinal")
public class SysGastrointestinalExamination {

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
	@Column(name = "Inspection")
	private String inspection;

	@Expose
	@Column(name = "Palpation")
	private String palpation;

	@Expose
	@Column(name = "Palpation_AbdomenTexture")
	private String palpation_AbdomenTexture;

	@Expose
	@Column(name = "Palpation_Liver")
	private String palpation_Liver;

	@Expose
	@Column(name = "Palpation_Spleen")
	private String palpation_Spleen;

	@Expose
	@Column(name = "Palpation_Tenderness")
	private String palpation_Tenderness;

	@Expose
	@Column(name = "Palpation_LocationOfTenderness")
	private String palpation_LocationOfTenderness;

	@Expose
	@Column(name = "Percussion")
	private String percussion;

	@Expose
	@Column(name = "Auscultation")
	private String auscultation;

	@Expose
	@Column(name = "AnalRegion")
	private String analRegion;

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

	public String getInspection() {
		return inspection;
	}

	public void setInspection(String inspection) {
		this.inspection = inspection;
	}

	public String getPalpation() {
		return palpation;
	}

	public void setPalpation(String palpation) {
		this.palpation = palpation;
	}

	public String getPalpation_AbdomenTexture() {
		return palpation_AbdomenTexture;
	}

	public void setPalpation_AbdomenTexture(String palpation_AbdomenTexture) {
		this.palpation_AbdomenTexture = palpation_AbdomenTexture;
	}

	public String getPalpation_Liver() {
		return palpation_Liver;
	}

	public void setPalpation_Liver(String palpation_Liver) {
		this.palpation_Liver = palpation_Liver;
	}

	public String getPalpation_Spleen() {
		return palpation_Spleen;
	}

	public void setPalpation_Spleen(String palpation_Spleen) {
		this.palpation_Spleen = palpation_Spleen;
	}

	public String getPalpation_Tenderness() {
		return palpation_Tenderness;
	}

	public void setPalpation_Tenderness(String palpation_Tenderness) {
		this.palpation_Tenderness = palpation_Tenderness;
	}

	public String getPalpation_LocationOfTenderness() {
		return palpation_LocationOfTenderness;
	}

	public void setPalpation_LocationOfTenderness(String palpation_LocationOfTenderness) {
		this.palpation_LocationOfTenderness = palpation_LocationOfTenderness;
	}

	public String getPercussion() {
		return percussion;
	}

	public void setPercussion(String percussion) {
		this.percussion = percussion;
	}

	public String getAuscultation() {
		return auscultation;
	}

	public void setAuscultation(String auscultation) {
		this.auscultation = auscultation;
	}

	public String getAnalRegion() {
		return analRegion;
	}

	public void setAnalRegion(String analRegion) {
		this.analRegion = analRegion;
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
