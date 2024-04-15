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
package com.iemr.hwc.data.doctor;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "t_cancerbreastexamination")
public class CancerBreastExamination {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	@Column(name = "EverBreastFed")
	private Boolean everBreastFed;

	@Expose
	@Column(name = "BreastFeedingDuration_gtr_eql_6months")
	private Boolean breastFeedingDurationGTE6months;

	@Expose
	@Column(name = "BreastsAppear_Normal")
	private Boolean breastsAppear_Normal;

	@Expose
	@Column(name = "RashOnBreast")
	private Boolean rashOnBreast;

	@Expose
	@Column(name = "DimplingSkinOnBreast")
	private Boolean dimplingSkinOnBreast;

	@Expose
	@Column(name = "DischargeFromNipple")
	private Boolean dischargeFromNipple;

	@Expose
	@Column(name = "PeaudOrange")
	private Boolean peaudOrange;

	@Expose
	@Column(name = "LumpInBreast")
	private Boolean lumpInBreast;

	@Expose
	@Column(name = "LumpSize")
	private String lumpSize;

	@Expose
	@Column(name = "LumpShape")
	private String lumpShape;

	@Expose
	@Column(name = "LumpTexture")
	private String lumpTexture;

	@Expose
	@Column(name = "ReferredToMammogram")
	private Boolean referredToMammogram;

	@Expose
	@Column(name = "MamogramReport")
	private String mamogramReport;

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

	public CancerBreastExamination() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CancerBreastExamination(Long iD, Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID,
			Boolean everBreastFed, Boolean breastFeedingDurationGTE6months, Boolean breastsAppear_Normal,
			Boolean rashOnBreast, Boolean dimplingSkinOnBreast, Boolean dischargeFromNipple, Boolean peaudOrange,
			Boolean lumpInBreast, String lumpSize, String lumpShape, String lumpTexture, Boolean referredToMammogram,
			String mamogramReport, Boolean deleted, String processed, String createdBy, Timestamp createdDate,
			String modifiedBy, Timestamp lastModDate) {
		super();
		ID = iD;
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.everBreastFed = everBreastFed;
		this.breastFeedingDurationGTE6months = breastFeedingDurationGTE6months;
		this.breastsAppear_Normal = breastsAppear_Normal;
		this.rashOnBreast = rashOnBreast;
		this.dimplingSkinOnBreast = dimplingSkinOnBreast;
		this.dischargeFromNipple = dischargeFromNipple;
		this.peaudOrange = peaudOrange;
		this.lumpInBreast = lumpInBreast;
		this.lumpSize = lumpSize;
		this.lumpShape = lumpShape;
		this.lumpTexture = lumpTexture;
		this.referredToMammogram = referredToMammogram;
		this.mamogramReport = mamogramReport;
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

	public Boolean getEverBreastFed() {
		return everBreastFed;
	}

	public void setEverBreastFed(Boolean everBreastFed) {
		this.everBreastFed = everBreastFed;
	}

	public Boolean getBreastFeedingDurationGTE6months() {
		return breastFeedingDurationGTE6months;
	}

	public void setBreastFeedingDurationGTE6months(Boolean breastFeedingDurationGTE6months) {
		this.breastFeedingDurationGTE6months = breastFeedingDurationGTE6months;
	}

	public Boolean getBreastsAppear_Normal() {
		return breastsAppear_Normal;
	}

	public void setBreastsAppear_Normal(Boolean breastsAppear_Normal) {
		this.breastsAppear_Normal = breastsAppear_Normal;
	}

	public Boolean getRashOnBreast() {
		return rashOnBreast;
	}

	public void setRashOnBreast(Boolean rashOnBreast) {
		this.rashOnBreast = rashOnBreast;
	}

	public Boolean getDimplingSkinOnBreast() {
		return dimplingSkinOnBreast;
	}

	public void setDimplingSkinOnBreast(Boolean dimplingSkinOnBreast) {
		this.dimplingSkinOnBreast = dimplingSkinOnBreast;
	}

	public Boolean getDischargeFromNipple() {
		return dischargeFromNipple;
	}

	public void setDischargeFromNipple(Boolean dischargeFromNipple) {
		this.dischargeFromNipple = dischargeFromNipple;
	}

	public Boolean getPeaudOrange() {
		return peaudOrange;
	}

	public void setPeaudOrange(Boolean peaudOrange) {
		this.peaudOrange = peaudOrange;
	}

	public Boolean getLumpInBreast() {
		return lumpInBreast;
	}

	public void setLumpInBreast(Boolean lumpInBreast) {
		this.lumpInBreast = lumpInBreast;
	}

	public String getLumpSize() {
		return lumpSize;
	}

	public void setLumpSize(String lumpSize) {
		this.lumpSize = lumpSize;
	}

	public String getLumpShape() {
		return lumpShape;
	}

	public void setLumpShape(String lumpShape) {
		this.lumpShape = lumpShape;
	}

	public String getLumpTexture() {
		return lumpTexture;
	}

	public void setLumpTexture(String lumpTexture) {
		this.lumpTexture = lumpTexture;
	}

	public Boolean getReferredToMammogram() {
		return referredToMammogram;
	}

	public void setReferredToMammogram(Boolean referredToMammogram) {
		this.referredToMammogram = referredToMammogram;
	}

	public String getMamogramReport() {
		return mamogramReport;
	}

	public void setMamogramReport(String mamogramReport) {
		this.mamogramReport = mamogramReport;
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
