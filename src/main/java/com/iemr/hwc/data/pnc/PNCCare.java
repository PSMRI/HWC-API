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
package com.iemr.hwc.data.pnc;

import java.sql.Date;
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

@Entity
@Table(name = "t_pnccare")
public class PNCCare {
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
	@Column(name = "VisitNo")
	private Short visitNo;

	@Expose
	@Column(name = "DeliveryTypeID")
	private Short deliveryTypeID;

	@Expose
	@Column(name = "DeliveryType")
	private String deliveryType;

	@Expose
	@Column(name = "DeliveryPlaceID")
	private Short deliveryPlaceID;

	@Expose
	@Column(name = "DeliveryPlace")
	private String deliveryPlace;

	@Expose
	@Column(name = "OtherDeliveryPlace")
	private String otherDeliveryPlace;

	@Expose
	@Column(name = "DateOfDelivery")
	private Date dateOfDelivery;

	@Expose
	@Column(name = "DeliveryComplicationID")
	private Short deliveryComplicationID;

	@Expose
	@Column(name = "DeliveryComplication")
	private String deliveryComplication;

	@Expose
	@Column(name = "OtherDeliveryComplication")
	private String otherDeliveryComplication;

	@Expose
	@Column(name = "PregOutcomeID")
	private Short pregOutcomeID;

	@Expose
	@Column(name = "PregOutcome")
	private String pregOutcome;

	@Expose
	@Column(name = "PostNatalComplicationID")
	private Short postNatalComplicationID;

	@Expose
	@Column(name = "PostNatalComplication")
	private String postNatalComplication;

	@Expose
	@Column(name = "OtherPostNatalComplication")
	private String otherPostNatalComplication;

	@Expose
	@Column(name = "GestationID")
	private Short gestationID;

	@Expose
	@Column(name = "GestationName")
	private String gestationName;

	@Expose
	@Column(name = "BirthWeightOfNewborn")
	private Double birthWeightOfNewborn;

	@Expose
	@Column(name = "NewBornHealthStatusID")
	private Integer newBornHealthStatusID;

	@Expose
	@Column(name = "NewBornHealthStatus")
	private String newBornHealthStatus;

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
	private String dDate;

	@Expose
	@Column(name = "DeliveryConductedById")
	private Integer deliveryConductedByID;

	@Expose
	@Column(name = "deliveryconductedby")
	private String deliveryConductedBy;

	public PNCCare() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public Integer getDeliveryConductedByID() {
		return deliveryConductedByID;
	}

	public void setDeliveryConductedByID(Integer deliveryConductedByID) {
		this.deliveryConductedByID = deliveryConductedByID;
	}

	public String getDeliveryConductedBy() {
		return deliveryConductedBy;
	}

	public void setDeliveryConductedBy(String deliveryConductedBy) {
		this.deliveryConductedBy = deliveryConductedBy;
	}

	public String getdDate() {
		return dDate;
	}

	public void setdDate(String dDate) {
		this.dDate = dDate;
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

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

	public Short getVisitNo() {
		return visitNo;
	}

	public void setVisitNo(Short visitNo) {
		this.visitNo = visitNo;
	}

	public Short getDeliveryTypeID() {
		return deliveryTypeID;
	}

	public void setDeliveryTypeID(Short deliveryTypeID) {
		this.deliveryTypeID = deliveryTypeID;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public Short getDeliveryPlaceID() {
		return deliveryPlaceID;
	}

	public void setDeliveryPlaceID(Short deliveryPlaceID) {
		this.deliveryPlaceID = deliveryPlaceID;
	}

	public String getDeliveryPlace() {
		return deliveryPlace;
	}

	public void setDeliveryPlace(String deliveryPlace) {
		this.deliveryPlace = deliveryPlace;
	}

	public String getOtherDeliveryPlace() {
		return otherDeliveryPlace;
	}

	public void setOtherDeliveryPlace(String otherDeliveryPlace) {
		this.otherDeliveryPlace = otherDeliveryPlace;
	}

	public Date getDateOfDelivery() {
		return dateOfDelivery;
	}

	public void setDateOfDelivery(Date dateOfDelivery) {
		this.dateOfDelivery = dateOfDelivery;
	}

	public Short getDeliveryComplicationID() {
		return deliveryComplicationID;
	}

	public void setDeliveryComplicationID(Short deliveryComplicationID) {
		this.deliveryComplicationID = deliveryComplicationID;
	}

	public String getDeliveryComplication() {
		return deliveryComplication;
	}

	public void setDeliveryComplication(String deliveryComplication) {
		this.deliveryComplication = deliveryComplication;
	}

	public String getOtherDeliveryComplication() {
		return otherDeliveryComplication;
	}

	public void setOtherDeliveryComplication(String otherDeliveryComplication) {
		this.otherDeliveryComplication = otherDeliveryComplication;
	}

	public Short getPregOutcomeID() {
		return pregOutcomeID;
	}

	public void setPregOutcomeID(Short pregOutcomeID) {
		this.pregOutcomeID = pregOutcomeID;
	}

	public String getPregOutcome() {
		return pregOutcome;
	}

	public void setPregOutcome(String pregOutcome) {
		this.pregOutcome = pregOutcome;
	}

	public Short getPostNatalComplicationID() {
		return postNatalComplicationID;
	}

	public void setPostNatalComplicationID(Short postNatalComplicationID) {
		this.postNatalComplicationID = postNatalComplicationID;
	}

	public String getPostNatalComplication() {
		return postNatalComplication;
	}

	public void setPostNatalComplication(String postNatalComplication) {
		this.postNatalComplication = postNatalComplication;
	}

	public String getOtherPostNatalComplication() {
		return otherPostNatalComplication;
	}

	public void setOtherPostNatalComplication(String otherPostNatalComplication) {
		this.otherPostNatalComplication = otherPostNatalComplication;
	}

	public Short getGestationID() {
		return gestationID;
	}

	public void setGestationID(Short gestationID) {
		this.gestationID = gestationID;
	}

	public String getGestationName() {
		return gestationName;
	}

	public void setGestationName(String gestationName) {
		this.gestationName = gestationName;
	}

	public Double getBirthWeightOfNewborn() {
		return birthWeightOfNewborn;
	}

	public void setBirthWeightOfNewborn(Double birthWeightOfNewborn) {
		this.birthWeightOfNewborn = birthWeightOfNewborn;
	}

	public Integer getNewBornHealthStatusID() {
		return newBornHealthStatusID;
	}

	public void setNewBornHealthStatusID(Integer newBornHealthStatusID) {
		this.newBornHealthStatusID = newBornHealthStatusID;
	}

	public String getNewBornHealthStatus() {
		return newBornHealthStatus;
	}

	public void setNewBornHealthStatus(String newBornHealthStatus) {
		this.newBornHealthStatus = newBornHealthStatus;
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

	public PNCCare(Long iD, Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID, Short visitNo,
			Short deliveryTypeID, String deliveryType, Short deliveryPlaceID, String deliveryPlace,
			String otherDeliveryPlace, Date dateOfDelivery, Short deliveryComplicationID, String deliveryComplication,
			String otherDeliveryComplication, Short pregOutcomeID, String pregOutcome, Short postNatalComplicationID,
			String postNatalComplication, String otherPostNatalComplication, Short gestationID, String gestationName,
			Double birthWeightOfNewborn, Integer newBornHealthStatusID, String newBornHealthStatus, Long visitCode) {
		super();
		ID = iD;
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.visitNo = visitNo;
		this.deliveryTypeID = deliveryTypeID;
		this.deliveryType = deliveryType;
		this.deliveryPlaceID = deliveryPlaceID;
		this.deliveryPlace = deliveryPlace;
		this.otherDeliveryPlace = otherDeliveryPlace;
		this.dateOfDelivery = dateOfDelivery;
		this.deliveryComplicationID = deliveryComplicationID;
		this.deliveryComplication = deliveryComplication;
		this.otherDeliveryComplication = otherDeliveryComplication;
		this.pregOutcomeID = pregOutcomeID;
		this.pregOutcome = pregOutcome;
		this.postNatalComplicationID = postNatalComplicationID;
		this.postNatalComplication = postNatalComplication;
		this.otherPostNatalComplication = otherPostNatalComplication;
		this.gestationID = gestationID;
		this.gestationName = gestationName;
		this.birthWeightOfNewborn = birthWeightOfNewborn;
		this.newBornHealthStatusID = newBornHealthStatusID;
		this.newBornHealthStatus = newBornHealthStatus;
		this.visitCode = visitCode;
	}

	public static PNCCare getPNCCareDetails(ArrayList<Object[]> resList) {
		ArrayList<PNCCare> resArray = new ArrayList<PNCCare>();
		PNCCare cOBJ = null;
		for (Object[] obj : resList) {

			cOBJ = new PNCCare((Long) obj[0], (Long) obj[1], (Long) obj[2], (Integer) obj[3], (Short) obj[4],
					(Short) obj[5], (String) obj[6], (Short) obj[7], (String) obj[8], (String) obj[9], (Date) obj[10],
					(Short) obj[11], (String) obj[12], (String) obj[13], (Short) obj[14], (String) obj[15],
					(Short) obj[16], (String) obj[17], (String) obj[18], (Short) obj[19], (String) obj[20],
					(Double) obj[21], (Integer) obj[22], (String) obj[23], (Long) obj[24]);

		}
		return cOBJ;
	}

}