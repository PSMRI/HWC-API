package com.iemr.mmu.data.doctor;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

@Entity
@Table(name = "t_cancergynecologicalexamination")
public class CancerGynecologicalExamination {
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
	@Column(name = "AppearanceOfCervix")
	private String appearanceOfCervix;

	@Expose
	@Column(name = "TypeOfLesion")
	private String typeOfLesion;

	@JsonIgnore
	@Transient
	private List<String> typeOfLesionList;

	@Expose
	@Column(name = "VulvalInvolvement")
	private Boolean vulvalInvolvement;

	@Expose
	@Column(name = "VaginalInvolvement")
	private Boolean vaginalInvolvement;

	@Expose
	@Column(name = "Uterus_Normal")
	private Boolean uterus_Normal;

	@Expose
	@Column(name = "SufferedFromRTIOrSTI")
	private Boolean sufferedFromRTIOrSTI;

	@Expose
	@Column(name = "RTIOrSTIDetail")
	private String rTIOrSTIDetail;

	@Expose
	@Column(name = "FilePath")
	private String filePath;

	@Expose
	@Column(name = "ExperiencedPostCoitalBleeding")
	private Boolean experiencedPostCoitalBleeding;

	@Expose
	@Column(name = "Observation")
	private String observation;

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

	public CancerGynecologicalExamination() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CancerGynecologicalExamination(Long iD, Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID,
			String appearanceOfCervix, String typeOfLesion, List<String> typeOfLesionList, Boolean vulvalInvolvement,
			Boolean vaginalInvolvement, Boolean uterus_Normal, Boolean sufferedFromRTIOrSTI, String rTIOrSTIDetail,
			String filePath, Boolean experiencedPostCoitalBleeding, String observation, Boolean deleted,
			String processed, String createdBy, Timestamp createdDate, String modifiedBy, Timestamp lastModDate) {
		super();
		ID = iD;
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.appearanceOfCervix = appearanceOfCervix;
		this.typeOfLesion = typeOfLesion;
		this.typeOfLesionList = typeOfLesionList;
		this.vulvalInvolvement = vulvalInvolvement;
		this.vaginalInvolvement = vaginalInvolvement;
		this.uterus_Normal = uterus_Normal;
		this.sufferedFromRTIOrSTI = sufferedFromRTIOrSTI;
		this.rTIOrSTIDetail = rTIOrSTIDetail;
		this.filePath = filePath;
		this.experiencedPostCoitalBleeding = experiencedPostCoitalBleeding;
		this.observation = observation;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
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

	public String getAppearanceOfCervix() {
		return appearanceOfCervix;
	}

	public void setAppearanceOfCervix(String appearanceOfCervix) {
		this.appearanceOfCervix = appearanceOfCervix;
	}

	public String getTypeOfLesion() {
		return typeOfLesion;
	}

	public void setTypeOfLesion(String typeOfLesion) {
		this.typeOfLesion = typeOfLesion;
	}

	public List<String> getTypeOfLesionList() {
		return typeOfLesionList;
	}

	public void setTypeOfLesionList(List<String> typeOfLesionList) {
		this.typeOfLesionList = typeOfLesionList;
	}

	public Boolean getVulvalInvolvement() {
		return vulvalInvolvement;
	}

	public void setVulvalInvolvement(Boolean vulvalInvolvement) {
		this.vulvalInvolvement = vulvalInvolvement;
	}

	public Boolean getVaginalInvolvement() {
		return vaginalInvolvement;
	}

	public void setVaginalInvolvement(Boolean vaginalInvolvement) {
		this.vaginalInvolvement = vaginalInvolvement;
	}

	public Boolean getUterus_Normal() {
		return uterus_Normal;
	}

	public void setUterus_Normal(Boolean uterus_Normal) {
		this.uterus_Normal = uterus_Normal;
	}

	public Boolean getSufferedFromRTIOrSTI() {
		return sufferedFromRTIOrSTI;
	}

	public void setSufferedFromRTIOrSTI(Boolean sufferedFromRTIOrSTI) {
		this.sufferedFromRTIOrSTI = sufferedFromRTIOrSTI;
	}

	public String getrTIOrSTIDetail() {
		return rTIOrSTIDetail;
	}

	public void setrTIOrSTIDetail(String rTIOrSTIDetail) {
		this.rTIOrSTIDetail = rTIOrSTIDetail;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Boolean getExperiencedPostCoitalBleeding() {
		return experiencedPostCoitalBleeding;
	}

	public void setExperiencedPostCoitalBleeding(Boolean experiencedPostCoitalBleeding) {
		this.experiencedPostCoitalBleeding = experiencedPostCoitalBleeding;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
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

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

}
