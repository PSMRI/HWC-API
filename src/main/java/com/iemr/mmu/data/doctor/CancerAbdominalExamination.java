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
package com.iemr.mmu.data.doctor;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "t_cancerabdominalexamination")
public class CancerAbdominalExamination {
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
	@Column(name = "AbdominalInspection_Normal")
	private Boolean abdominalInspection_Normal;

	@Expose
	@Column(name = "Liver")
	private String liver;

	@Expose
	@Column(name = "Ascites_Present")
	private Boolean ascites_Present;

	@Expose
	@Column(name = "AnyOtherMass_Present")
	private Boolean anyOtherMass_Present;

	@Expose
	@Column(name = "LymphNodes_Enlarged")
	private Boolean lymphNodes_Enlarged;

	@Expose
	@Column(name = "LymphNode_Inguinal_Left")
	private Boolean lymphNode_Inguinal_Left;

	@Expose
	@Column(name = "LymphNode_Inguinal_Right")
	private Boolean lymphNode_Inguinal_Right;

	@Expose
	@Column(name = "LymphNode_ExternalIliac_Left")
	private Boolean lymphNode_ExternalIliac_Left;

	@Expose
	@Column(name = "LymphNode_ExternalIliac_Right")
	private Boolean lymphNode_ExternalIliac_Right;

	@Expose
	@Column(name = "LymphNode_ParaAortic_Left")
	private Boolean lymphNode_ParaAortic_Left;

	@Expose
	@Column(name = "LymphNode_ParaAortic_Right")
	private Boolean lymphNode_ParaAortic_Right;

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

	public CancerAbdominalExamination() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CancerAbdominalExamination(Long iD, Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID,
			Boolean abdominalInspection_Normal, String liver, Boolean ascites_Present, Boolean anyOtherMass_Present,
			Boolean lymphNodes_Enlarged, Boolean lymphNode_Inguinal_Left, Boolean lymphNode_Inguinal_Right,
			Boolean lymphNode_ExternalIliac_Left, Boolean lymphNode_ExternalIliac_Right,
			Boolean lymphNode_ParaAortic_Left, Boolean lymphNode_ParaAortic_Right, String observation, Boolean deleted,
			String processed, String createdBy, Timestamp createdDate, String modifiedBy, Timestamp lastModDate) {
		super();
		ID = iD;
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.abdominalInspection_Normal = abdominalInspection_Normal;
		this.liver = liver;
		this.ascites_Present = ascites_Present;
		this.anyOtherMass_Present = anyOtherMass_Present;
		this.lymphNodes_Enlarged = lymphNodes_Enlarged;
		this.lymphNode_Inguinal_Left = lymphNode_Inguinal_Left;
		this.lymphNode_Inguinal_Right = lymphNode_Inguinal_Right;
		this.lymphNode_ExternalIliac_Left = lymphNode_ExternalIliac_Left;
		this.lymphNode_ExternalIliac_Right = lymphNode_ExternalIliac_Right;
		this.lymphNode_ParaAortic_Left = lymphNode_ParaAortic_Left;
		this.lymphNode_ParaAortic_Right = lymphNode_ParaAortic_Right;
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

	public Boolean getAbdominalInspection_Normal() {
		return abdominalInspection_Normal;
	}

	public void setAbdominalInspection_Normal(Boolean abdominalInspection_Normal) {
		this.abdominalInspection_Normal = abdominalInspection_Normal;
	}

	public String getLiver() {
		return liver;
	}

	public void setLiver(String liver) {
		this.liver = liver;
	}

	public Boolean getAscites_Present() {
		return ascites_Present;
	}

	public void setAscites_Present(Boolean ascites_Present) {
		this.ascites_Present = ascites_Present;
	}

	public Boolean getAnyOtherMass_Present() {
		return anyOtherMass_Present;
	}

	public void setAnyOtherMass_Present(Boolean anyOtherMass_Present) {
		this.anyOtherMass_Present = anyOtherMass_Present;
	}

	public Boolean getLymphNodes_Enlarged() {
		return lymphNodes_Enlarged;
	}

	public void setLymphNodes_Enlarged(Boolean lymphNodes_Enlarged) {
		this.lymphNodes_Enlarged = lymphNodes_Enlarged;
	}

	public Boolean getLymphNode_Inguinal_Left() {
		return lymphNode_Inguinal_Left;
	}

	public void setLymphNode_Inguinal_Left(Boolean lymphNode_Inguinal_Left) {
		this.lymphNode_Inguinal_Left = lymphNode_Inguinal_Left;
	}

	public Boolean getLymphNode_Inguinal_Right() {
		return lymphNode_Inguinal_Right;
	}

	public void setLymphNode_Inguinal_Right(Boolean lymphNode_Inguinal_Right) {
		this.lymphNode_Inguinal_Right = lymphNode_Inguinal_Right;
	}

	public Boolean getLymphNode_ExternalIliac_Left() {
		return lymphNode_ExternalIliac_Left;
	}

	public void setLymphNode_ExternalIliac_Left(Boolean lymphNode_ExternalIliac_Left) {
		this.lymphNode_ExternalIliac_Left = lymphNode_ExternalIliac_Left;
	}

	public Boolean getLymphNode_ExternalIliac_Right() {
		return lymphNode_ExternalIliac_Right;
	}

	public void setLymphNode_ExternalIliac_Right(Boolean lymphNode_ExternalIliac_Right) {
		this.lymphNode_ExternalIliac_Right = lymphNode_ExternalIliac_Right;
	}

	public Boolean getLymphNode_ParaAortic_Left() {
		return lymphNode_ParaAortic_Left;
	}

	public void setLymphNode_ParaAortic_Left(Boolean lymphNode_ParaAortic_Left) {
		this.lymphNode_ParaAortic_Left = lymphNode_ParaAortic_Left;
	}

	public Boolean getLymphNode_ParaAortic_Right() {
		return lymphNode_ParaAortic_Right;
	}

	public void setLymphNode_ParaAortic_Right(Boolean lymphNode_ParaAortic_Right) {
		this.lymphNode_ParaAortic_Right = lymphNode_ParaAortic_Right;
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
