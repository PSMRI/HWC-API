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
package com.iemr.hwc.data.anc;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.iemr.hwc.annotation.sqlInjectionSafe.SQLInjectionSafe;

@Entity
@Table(name = "t_ancwomenvaccinedetail")
public class ANCWomenVaccineDetail {
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
	@Column(name = "VaccineName")
	private String vaccineName;

	@Expose
	@Column(name = "Status")
	private String status;

	@Expose
	@Column(name = "ReceivedDate")
	private Date receivedDate;

	@Expose
	@Column(name = "ReceivedFacilityName")
	private String receivedFacilityName;

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
	@Column(name = "vanID")
	private Integer vanID;

	@Expose
	@Column(name = "VehicalNo")
	private String vehicalNo;

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

	public ANCWomenVaccineDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ANCWomenVaccineDetail(Long iD, Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID,
			String vaccineName, String status, Date receivedDate, String receivedFacilityName) {
		super();
		ID = iD;
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.vaccineName = vaccineName;
		this.status = status;
		this.receivedDate = receivedDate;
		this.receivedFacilityName = receivedFacilityName;
	}

	public static WrapperAncImmunization getANCWomenVaccineDetails(ArrayList<Object[]> resList) {
		ArrayList<ANCWomenVaccineDetail> resArray = new ArrayList<ANCWomenVaccineDetail>();
		WrapperAncImmunization wrapperAncImmunization = new WrapperAncImmunization();

		for (Object[] obj : resList) {
			Date dt = (Date) obj[6];
			if (resList.indexOf(obj) == 0) {
				wrapperAncImmunization.setBeneficiaryRegID((Long) obj[1]);
				wrapperAncImmunization.setBenVisitID((Long) obj[2]);
				wrapperAncImmunization.setProviderServiceMapID((Integer) obj[3]);
				wrapperAncImmunization.setVisitCode((Long) obj[8]);
				wrapperAncImmunization.settT1ID((Long) obj[0]);
				wrapperAncImmunization.settT_1Status((String) obj[5]);
				if (null != dt)
					wrapperAncImmunization.setDateReceivedForTT_1(dt.toString());
				wrapperAncImmunization.setFacilityNameOfTT_1((String) obj[7]);
			} else if (resList.indexOf(obj) == 1) {
				wrapperAncImmunization.settT2ID((Long) obj[0]);
				wrapperAncImmunization.settT_2Status((String) obj[5]);
				if (null != dt)
					wrapperAncImmunization.setDateReceivedForTT_2(dt.toString());
				wrapperAncImmunization.setFacilityNameOfTT_2((String) obj[7]);
			} else if (resList.indexOf(obj) == 2) {
				wrapperAncImmunization.settT3ID((Long) obj[0]);
				wrapperAncImmunization.settT_3Status((String) obj[5]);
				if (null != dt)
					wrapperAncImmunization.setDateReceivedForTT_3(dt.toString());
				wrapperAncImmunization.setFacilityNameOfTT_3((String) obj[7]);
			}

		}
		return wrapperAncImmunization;
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

	public String getVaccineName() {
		return vaccineName;
	}

	public void setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getReceivedFacilityName() {
		return receivedFacilityName;
	}

	public void setReceivedFacilityName(String receivedFacilityName) {
		this.receivedFacilityName = receivedFacilityName;
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

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

}
