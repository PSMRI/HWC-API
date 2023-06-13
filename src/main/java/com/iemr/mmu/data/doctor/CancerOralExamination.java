/* LicenseInfo : Copyright © 2023 Piramal */ 
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
@Table(name = "t_canceroralexamination")
public class CancerOralExamination {
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
	@Column(name = "LimitedMouthOpening")
	private String limitedMouthOpening;

	@Expose
	@Column(name = "PremalignantLesions")
	private Boolean premalignantLesions;

	@Expose
	@Column(name = "PreMalignantLesionType")
	private String preMalignantLesionType;

	@JsonIgnore
	@Transient
	private List<String> preMalignantLesionTypeList;

	@Expose
	@Column(name = "ProlongedIrritation")
	private Boolean prolongedIrritation;

	@Expose
	@Column(name = "ChronicBurningSensation")
	private Boolean chronicBurningSensation;

	@Expose
	@Column(name = "Observation")
	private String observation;

	/*
	 * @JsonIgnore
	 * 
	 * @Column(name = "Image") private Blob image;
	 */

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

	public CancerOralExamination() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CancerOralExamination(Long iD, Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID,
			String limitedMouthOpening, Boolean premalignantLesions, String preMalignantLesionType,
			List<String> preMalignantLesionTypeList, Boolean prolongedIrritation, Boolean chronicBurningSensation,
			String observation, Boolean deleted, String processed, String createdBy, Timestamp createdDate,
			String modifiedBy, Timestamp lastModDate) {
		super();
		ID = iD;
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.limitedMouthOpening = limitedMouthOpening;
		this.premalignantLesions = premalignantLesions;
		this.preMalignantLesionType = preMalignantLesionType;
		this.preMalignantLesionTypeList = preMalignantLesionTypeList;
		this.prolongedIrritation = prolongedIrritation;
		this.chronicBurningSensation = chronicBurningSensation;
		this.observation = observation;
		// this.image = image;
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

	public String getLimitedMouthOpening() {
		return limitedMouthOpening;
	}

	public void setLimitedMouthOpening(String limitedMouthOpening) {
		this.limitedMouthOpening = limitedMouthOpening;
	}

	public Boolean getPremalignantLesions() {
		return premalignantLesions;
	}

	public void setPremalignantLesions(Boolean premalignantLesions) {
		this.premalignantLesions = premalignantLesions;
	}

	public String getPreMalignantLesionType() {
		return preMalignantLesionType;
	}

	public void setPreMalignantLesionType(String preMalignantLesionType) {
		this.preMalignantLesionType = preMalignantLesionType;
	}

	public List<String> getPreMalignantLesionTypeList() {
		return preMalignantLesionTypeList;
	}

	public void setPreMalignantLesionTypeList(List<String> preMalignantLesionTypeList) {
		this.preMalignantLesionTypeList = preMalignantLesionTypeList;
	}

	public Boolean getProlongedIrritation() {
		return prolongedIrritation;
	}

	public void setProlongedIrritation(Boolean prolongedIrritation) {
		this.prolongedIrritation = prolongedIrritation;
	}

	public Boolean getChronicBurningSensation() {
		return chronicBurningSensation;
	}

	public void setChronicBurningSensation(Boolean chronicBurningSensation) {
		this.chronicBurningSensation = chronicBurningSensation;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	/*
	 * public Blob getImage() { return image; }
	 * 
	 * public void setImage(Blob image) { this.image = image; }
	 */

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
