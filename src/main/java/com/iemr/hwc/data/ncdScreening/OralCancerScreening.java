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
package com.iemr.hwc.data.ncdScreening;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "t_oralcancerscreening")
public class OralCancerScreening {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id", insertable = false)
	private Long id;

	@Column(name = "BeneficiaryRegId")
	private Long beneficiaryRegId;

	@Column(name = "Visitcode")
	private Long visitcode;

	public Long getVisitcode() {
		return visitcode;
	}

	public void setVisitcode(Long visitcode) {
		this.visitcode = visitcode;
	}

	@Column(name = "OralCavityFindingId")
	private Integer oralCavityFindingId;

	@Column(name = "OralCavityFinding")
	private String oralCavityFinding;

	@Column(name = "MouthOpeningId")
	private Integer mouthOpeningId;

	@Column(name = "MouthOpening")
	private String mouthOpening;

	@Column(name = "PalpationofOralCavityId")
	private Integer palpationofOralCavityId;

	@Column(name = "PalpationofOralCavity")
	private String palpationofOralCavity;

	@Column(name = "TemporomandibularJointRightId")
	private Integer temporomandibularJointRightId;

	@Column(name = "TemporomandibularJointRight")
	private String temporomandibularJointRight;

	@Column(name = "TemporomandibularJointLeftId")
	private Integer temporomandibularJointLeftId;

	@Column(name = "TemporomandibularJointLeft")
	private String temporomandibularJointLeft;

	@Column(name = "CervicalLymphnodesId")
	private Integer cervicalLymphnodesId;

	@Column(name = "CervicalLymphnodes")
	private String cervicalLymphnodes;

	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

	@Column(name = "Processed", insertable = false, updatable = true)
	private Character processed;

	@Column(name = "CreatedBy", insertable = true, updatable = false)
	private String createdBy;

	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;

	@Column(name = "ModifiedBy", insertable = false, updatable = true)
	private String modifiedBy;

	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	@Expose
	@Column(name = "VanSerialNo")
	private Long vanSerialNo;

	@Expose
	@Column(name = "VehicalNo")
	private String vehicalNo;

	@Expose
	@Column(name = "vanID", updatable = false)
	private Integer vanID;

	@Expose
	@Column(name = "ParkingPlaceID", updatable = false)
	private Integer parkingPlaceID;

	@Expose
	@Column(name = "suspected")
	private Boolean suspected;
	@Expose
	@Column(name = "Confirmatory")
	private Boolean confirmed;

	public Boolean getSuspected() {
		return suspected;
	}

	public void setSuspected(Boolean suspected) {
		this.suspected = suspected;
	}

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBeneficiaryRegId() {
		return beneficiaryRegId;
	}

	public void setBeneficiaryRegId(Long beneficiaryRegId) {
		this.beneficiaryRegId = beneficiaryRegId;
	}

	public Integer getOralCavityFindingId() {
		return oralCavityFindingId;
	}

	public void setOralCavityFindingId(Integer oralCavityFindingId) {
		this.oralCavityFindingId = oralCavityFindingId;
	}

	public String getOralCavityFinding() {
		return oralCavityFinding;
	}

	public void setOralCavityFinding(String oralCavityFinding) {
		this.oralCavityFinding = oralCavityFinding;
	}

	public Integer getMouthOpeningId() {
		return mouthOpeningId;
	}

	public void setMouthOpeningId(Integer mouthOpeningId) {
		this.mouthOpeningId = mouthOpeningId;
	}

	public String getMouthOpening() {
		return mouthOpening;
	}

	public void setMouthOpening(String mouthOpening) {
		this.mouthOpening = mouthOpening;
	}

	public Integer getPalpationofOralCavityId() {
		return palpationofOralCavityId;
	}

	public void setPalpationofOralCavityId(Integer palpationofOralCavityId) {
		this.palpationofOralCavityId = palpationofOralCavityId;
	}

	public String getPalpationofOralCavity() {
		return palpationofOralCavity;
	}

	public void setPalpationofOralCavity(String palpationofOralCavity) {
		this.palpationofOralCavity = palpationofOralCavity;
	}

	public Integer getTemporomandibularJointRightId() {
		return temporomandibularJointRightId;
	}

	public void setTemporomandibularJointRightId(Integer temporomandibularJointRightId) {
		this.temporomandibularJointRightId = temporomandibularJointRightId;
	}

	public String getTemporomandibularJointRight() {
		return temporomandibularJointRight;
	}

	public void setTemporomandibularJointRight(String temporomandibularJointRight) {
		this.temporomandibularJointRight = temporomandibularJointRight;
	}

	public Integer getTemporomandibularJointLeftId() {
		return temporomandibularJointLeftId;
	}

	public void setTemporomandibularJointLeftId(Integer temporomandibularJointLeftId) {
		this.temporomandibularJointLeftId = temporomandibularJointLeftId;
	}

	public String getTemporomandibularJointLeft() {
		return temporomandibularJointLeft;
	}

	public void setTemporomandibularJointLeft(String temporomandibularJointLeft) {
		this.temporomandibularJointLeft = temporomandibularJointLeft;
	}

	public Integer getCervicalLymphnodesId() {
		return cervicalLymphnodesId;
	}

	public void setCervicalLymphnodesId(Integer cervicalLymphnodesId) {
		this.cervicalLymphnodesId = cervicalLymphnodesId;
	}

	public String getCervicalLymphnodes() {
		return cervicalLymphnodes;
	}

	public void setCervicalLymphnodes(String cervicalLymphnodes) {
		this.cervicalLymphnodes = cervicalLymphnodes;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Character getProcessed() {
		return processed;
	}

	public void setProcessed(Character processed) {
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

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public Integer getParkingPlaceID() {
		return parkingPlaceID;
	}

	public void setParkingPlaceID(Integer parkingPlaceID) {
		this.parkingPlaceID = parkingPlaceID;
	}

}
