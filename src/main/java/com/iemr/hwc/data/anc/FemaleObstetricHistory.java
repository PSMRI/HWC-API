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
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.hwc.annotation.sqlInjectionSafe.SQLInjectionSafe;

@Entity
@Table(name = "t_FemaleObstetricHistory")
public class FemaleObstetricHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	@Column(name = "ObstetricHistoryID")
	private Long obstetricHistoryID;
	
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
	@Column(name = "PregOrder")
	private Short pregOrder;

	@Expose
	@Column(name = "totalNoOfPreg")
	private Short totalNoOfPreg;

	@Expose
	@Column(name = "PregComplicationID")
	private @SQLInjectionSafe String pregComplicationID;

	@Expose
	@Column(name = "PregComplicationType")
	private @SQLInjectionSafe String pregComplicationType;

	// CR, 30-10-2018, multiple selection
	@Expose
	@Transient
	private ArrayList<Map<String, Object>> pregComplicationList;

	@Expose
	@Column(name = "OtherPregComplication")
	private @SQLInjectionSafe String otherPregComplication;

	@Expose
	@Column(name = "PregDurationID")
	private Short pregDurationID;

	@Expose
	@Column(name = "DurationType")
	private @SQLInjectionSafe String durationType;

	@Expose
	@Column(name = "DeliveryTypeID")
	private Short deliveryTypeID;

	@Expose
	@Column(name = "DeliveryType")
	private @SQLInjectionSafe String deliveryType;

	@Expose
	@Column(name = "DeliveryPlaceID")
	private Short deliveryPlaceID;

	@Expose
	@Column(name = "DeliveryPlace")
	private @SQLInjectionSafe String deliveryPlace;

	@Expose
	@Column(name = "OtherDeliveryPlace")
	private @SQLInjectionSafe String otherDeliveryPlace;

	@Expose
	@Column(name = "DeliveryComplicationID")
	private @SQLInjectionSafe String deliveryComplicationID;

	@Expose
	@Column(name = "DeliveryComplicationType")
	private @SQLInjectionSafe String deliveryComplicationType;

	// CR, 30-10-2018, multiple selection
	@Expose
	@Transient
	private ArrayList<Map<String, Object>> deliveryComplicationList;

	@Expose
	@Column(name = "OtherDeliveryComplication")
	private @SQLInjectionSafe String otherDeliveryComplication;

	@Expose
	@Column(name = "PregOutcomeID")
	private Short pregOutcomeID;

	@Expose
	@Column(name = "PregOutcome")
	private @SQLInjectionSafe String pregOutcome;

	@Expose
	@Column(name = "PostpartumComplicationID")
	private @SQLInjectionSafe String postpartumComplicationID;

	@Expose
	@Column(name = "PostpartumComplicationType")
	private @SQLInjectionSafe String postpartumComplicationType;

	// CR, 30-10-2018, multiple selection
	@Expose
	@Transient
	private ArrayList<Map<String, Object>> postpartumComplicationList;

	@Expose
	@Column(name = "OtherPostpartumCompType")
	private @SQLInjectionSafe String otherPostpartumCompType;

	@Expose
	@Column(name = "PostNatalComplicationID")
	private Short postNatalComplicationID;

	@Expose
	@Column(name = "PostNatalComplication")
	private @SQLInjectionSafe String postNatalComplication;

	@Expose
	@Column(name = "OtherPostNatalComplication")
	private @SQLInjectionSafe String otherPostNatalComplication;

	@Expose
	@Column(name = "CongenitalAnomalies")
	private @SQLInjectionSafe String congenitalAnomalies;

	@Expose
	@Column(name = "NewBornComplicationID")
	private Short newBornComplicationID;

	@Expose
	@Column(name = "NewBornComplication")
	private @SQLInjectionSafe String newBornComplication;

	@Expose
	@Column(name = "OtherNewBornComplication")
	private @SQLInjectionSafe String otherNewBornComplication;

	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private @SQLInjectionSafe String processed;

	@Expose
	@Column(name = "CreatedBy")
	private @SQLInjectionSafe String createdBy;

	@Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;

	@Expose
	@Column(name = "ModifiedBy")
	private @SQLInjectionSafe String modifiedBy;

	@Expose
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	@Expose
	@Column(name = "VanSerialNo")
	private Long vanSerialNo;

	@Expose
	@Column(name = "VehicalNo")
	private @SQLInjectionSafe String vehicalNo;

	@Expose
	@Column(name = "vanID")
	private Integer vanID;

	@Expose
	@Column(name = "ParkingPlaceID")
	private Integer parkingPlaceID;

	@Expose
	@Column(name = "SyncedBy")
	private @SQLInjectionSafe String syncedBy;

	@Expose
	@Column(name = "SyncedDate")
	private Timestamp syncedDate;

	@Expose
	@Column(name = "ReservedForChange")
	private @SQLInjectionSafe String reservedForChange;

	@Transient
	private Date captureDate;

	@Expose
	@Column(name = "typeOfAbortionid")
	private Integer abortionTypeID;

	@Expose
	@Column(name = "typeOfAbortionValue")
	private @SQLInjectionSafe String typeOfAbortionValue;

	@Expose
	@Column(name = "PostAbortionComplications")
	private @SQLInjectionSafe String postAbortionComplication_db;

	@Expose
	@Column(name = "PostAbortionComplicationsValues")
	private @SQLInjectionSafe String postAbortionComplicationsValues;

	@Expose
	@Column(name = "CompletedWeeksofPregnancy")
	private Integer pregDuration;

	@Expose
	@Column(name = "ServiceFacilityID")
	private Integer typeofFacilityID;

	@Expose
	@Column(name = "ServiceFacilityValue")
	private @SQLInjectionSafe String serviceFacilityValue;

	@Expose
	@Transient
	private ArrayList<Map<String, Object>> postAbortionComplication;

	@Expose
	@Transient
	private Map<String, Object> abortionType;

	@Expose
	@Transient
	private Map<String, Object> typeofFacility;

	public String getTypeOfAbortionValue() {
		return typeOfAbortionValue;
	}

	public void setTypeOfAbortionValue(String typeOfAbortionValue) {
		this.typeOfAbortionValue = typeOfAbortionValue;
	}

	public String getServiceFacilityValue() {
		return serviceFacilityValue;
	}

	public void setServiceFacilityValue(String serviceFacilityValue) {
		this.serviceFacilityValue = serviceFacilityValue;
	}

	public Map<String, Object> getAbortionType() {
		return abortionType;
	}

	public void setAbortionType(Map<String, Object> abortionType) {
		this.abortionType = abortionType;
	}

	public Map<String, Object> getTypeofFacility() {
		return typeofFacility;
	}

	public void setTypeofFacility(Map<String, Object> typeofFacility) {
		this.typeofFacility = typeofFacility;
	}

	public String getPostAbortionComplicationsValues() {
		return postAbortionComplicationsValues;
	}

	public void setPostAbortionComplicationsValues(String postAbortionComplicationsValues) {
		this.postAbortionComplicationsValues = postAbortionComplicationsValues;
	}

	public Integer getAbortionTypeID() {
		return abortionTypeID;
	}

	public void setAbortionTypeID(Integer abortionTypeID) {
		this.abortionTypeID = abortionTypeID;
	}

	public String getPostAbortionComplication_db() {
		return postAbortionComplication_db;
	}

	public void setPostAbortionComplication_db(String postAbortionComplication_db) {
		this.postAbortionComplication_db = postAbortionComplication_db;
	}

	public Integer getTypeofFacilityID() {
		return typeofFacilityID;
	}

	public void setTypeofFacilityID(Integer typeofFacilityID) {
		this.typeofFacilityID = typeofFacilityID;
	}

	public ArrayList<Map<String, Object>> getPostAbortionComplication() {
		return postAbortionComplication;
	}

	public void setPostAbortionComplication(ArrayList<Map<String, Object>> postAbortionComplication) {
		this.postAbortionComplication = postAbortionComplication;
	}

	public Integer getPregDuration() {
		return pregDuration;
	}

	public void setPregDuration(Integer pregDuration) {
		this.pregDuration = pregDuration;
	}

	public Integer getServiceFacilityID() {
		return typeofFacilityID;
	}

	public void setServiceFacilityID(Integer serviceFacilityID) {
		this.typeofFacilityID = serviceFacilityID;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public Date getCaptureDate() {
		return captureDate;
	}

	public void setCaptureDate(Date captureDate) {
		this.captureDate = captureDate;
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

	public Short getPregOrder() {
		return pregOrder;
	}

	public void setPregOrder(Short pregOrder) {
		this.pregOrder = pregOrder;
	}

	public String getPregComplicationID() {
		return pregComplicationID;
	}

	public void setPregComplicationID(String pregComplicationID) {
		this.pregComplicationID = pregComplicationID;
	}

	public String getPregComplicationType() {
		return pregComplicationType;
	}

	public void setPregComplicationType(String pregComplicationType) {
		this.pregComplicationType = pregComplicationType;
	}

	public String getOtherPregComplication() {
		return otherPregComplication;
	}

	public void setOtherPregComplication(String otherPregComplication) {
		this.otherPregComplication = otherPregComplication;
	}

	public Short getPregDurationID() {
		return pregDurationID;
	}

	public void setPregDurationID(Short pregDurationID) {
		this.pregDurationID = pregDurationID;
	}

	public String getDurationType() {
		return durationType;
	}

	public void setDurationType(String durationType) {
		this.durationType = durationType;
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

	public String getDeliveryComplicationID() {
		return deliveryComplicationID;
	}

	public void setDeliveryComplicationID(String deliveryComplicationID) {
		this.deliveryComplicationID = deliveryComplicationID;
	}

	public String getDeliveryComplicationType() {
		return deliveryComplicationType;
	}

	public void setDeliveryComplicationType(String deliveryComplicationType) {
		this.deliveryComplicationType = deliveryComplicationType;
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

	public String getPostpartumComplicationID() {
		return postpartumComplicationID;
	}

	public void setPostpartumComplicationID(String postpartumComplicationID) {
		this.postpartumComplicationID = postpartumComplicationID;
	}

	public String getPostpartumComplicationType() {
		return postpartumComplicationType;
	}

	public void setPostpartumComplicationType(String postpartumComplicationType) {
		this.postpartumComplicationType = postpartumComplicationType;
	}

	public String getOtherPostpartumCompType() {
		return otherPostpartumCompType;
	}

	public void setOtherPostpartumCompType(String otherPostpartumCompType) {
		this.otherPostpartumCompType = otherPostpartumCompType;
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

	public String getCongenitalAnomalies() {
		return congenitalAnomalies;
	}

	public void setCongenitalAnomalies(String congenitalAnomalies) {
		this.congenitalAnomalies = congenitalAnomalies;
	}

	public Short getNewBornComplicationID() {
		return newBornComplicationID;
	}

	public void setNewBornComplicationID(Short newBornComplicationID) {
		this.newBornComplicationID = newBornComplicationID;
	}

	public String getNewBornComplication() {
		return newBornComplication;
	}

	public void setNewBornComplication(String newBornComplication) {
		this.newBornComplication = newBornComplication;
	}

	public String getOtherNewBornComplication() {
		return otherNewBornComplication;
	}

	public void setOtherNewBornComplication(String otherNewBornComplication) {
		this.otherNewBornComplication = otherNewBornComplication;
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

	public Long getObstetricHistoryID() {
		return obstetricHistoryID;
	}

	public Short getTotalNoOfPreg() {
		return totalNoOfPreg;
	}

	public void setTotalNoOfPreg(Short totalNoOfPreg) {
		this.totalNoOfPreg = totalNoOfPreg;
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

	public FemaleObstetricHistory() {
	}

	public FemaleObstetricHistory(Date createdDate, Short pregOrder, String pregComplicationType,
			String otherPregComplication, String durationType, String deliveryType, String deliveryPlace,
			String otherDeliveryPlace, String deliveryComplicationType, String otherDeliveryComplication,
			String pregOutcome, String postpartumComplicationType, String otherPostpartumCompType,
			String postNatalComplication, String otherPostNatalComplication, String congenitalAnomalies,
			String newBornComplication, String otherNewBornComplication) {
		super();
		this.captureDate = createdDate;
		this.pregOrder = pregOrder;
		this.pregComplicationType = pregComplicationType;
		this.otherPregComplication = otherPregComplication;
		this.durationType = durationType;
		this.deliveryType = deliveryType;
		this.deliveryPlace = deliveryPlace;
		this.otherDeliveryPlace = otherDeliveryPlace;
		this.deliveryComplicationType = deliveryComplicationType;
		this.otherDeliveryComplication = otherDeliveryComplication;
		this.pregOutcome = pregOutcome;
		this.postpartumComplicationType = postpartumComplicationType;
		this.otherPostpartumCompType = otherPostpartumCompType;
		this.postNatalComplication = postNatalComplication;
		this.otherPostNatalComplication = otherPostNatalComplication;
		this.congenitalAnomalies = congenitalAnomalies;
		this.newBornComplication = newBornComplication;
		this.otherNewBornComplication = otherNewBornComplication;

	}

	public FemaleObstetricHistory(Short pregOrder, String pregComplicationID, String pregComplicationType,
			String otherPregComplication, Short pregDurationID, String durationType, Short deliveryTypeID,
			String deliveryType, Short deliveryPlaceID, String deliveryPlace, String otherDeliveryPlace,
			String deliveryComplicationID, String deliveryComplicationType, String otherDeliveryComplication,
			Short pregOutcomeID, String pregOutcome, String postpartumComplicationID, String postpartumComplicationType,
			String otherPostpartumCompType, Short postNatalComplicationID, String postNatalComplication,
			String otherPostNatalComplication, String congenitalAnomalies, Short newBornComplicationID,
			String newBornComplication, String otherNewBornComplication, Long visitCode, Integer abortionTypeID,
			String postAbortionComplication_db, Integer pregDuration, Integer typeofFacilityID,
			String PostAbortionComplicationsValues, String typeOfAbortionVal, String serviceFacilityVal) {
		super();
		this.pregOrder = pregOrder;
		this.pregComplicationID = pregComplicationID;
		this.pregComplicationType = pregComplicationType;
		this.otherPregComplication = otherPregComplication;
		this.pregDurationID = pregDurationID;
		this.durationType = durationType;
		this.deliveryTypeID = deliveryTypeID;
		this.deliveryType = deliveryType;
		this.deliveryPlaceID = deliveryPlaceID;
		this.deliveryPlace = deliveryPlace;
		this.otherDeliveryPlace = otherDeliveryPlace;
		this.deliveryComplicationID = deliveryComplicationID;
		this.deliveryComplicationType = deliveryComplicationType;
		this.otherDeliveryComplication = otherDeliveryComplication;
		this.pregOutcomeID = pregOutcomeID;
		this.pregOutcome = pregOutcome;
		this.postpartumComplicationID = postpartumComplicationID;
		this.postpartumComplicationType = postpartumComplicationType;
		this.otherPostpartumCompType = otherPostpartumCompType;
		this.postNatalComplicationID = postNatalComplicationID;
		this.postNatalComplication = postNatalComplication;
		this.otherPostNatalComplication = otherPostNatalComplication;
		this.congenitalAnomalies = congenitalAnomalies;
		this.newBornComplicationID = newBornComplicationID;
		this.newBornComplication = newBornComplication;
		this.otherNewBornComplication = otherNewBornComplication;
		this.visitCode = visitCode;

		this.abortionTypeID = abortionTypeID;
		this.postAbortionComplication_db = postAbortionComplication_db;
		this.pregDuration = pregDuration;
		this.typeofFacilityID = typeofFacilityID;
		this.postAbortionComplicationsValues = PostAbortionComplicationsValues;
		this.typeOfAbortionValue = typeOfAbortionVal;
		this.serviceFacilityValue = serviceFacilityVal;

	}

	public void setObstetricHistoryID(Long obstetricHistoryID) {
		this.obstetricHistoryID = obstetricHistoryID;
	}

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

	public ArrayList<Map<String, Object>> getDeliveryComplicationList() {
		return deliveryComplicationList;
	}

	public void setDeliveryComplicationList(ArrayList<Map<String, Object>> deliveryComplicationList) {
		this.deliveryComplicationList = deliveryComplicationList;
	}

	public ArrayList<Map<String, Object>> getPregComplicationList() {
		return pregComplicationList;
	}

	public void setPregComplicationList(ArrayList<Map<String, Object>> pregComplicationList) {
		this.pregComplicationList = pregComplicationList;
	}

	public ArrayList<Map<String, Object>> getPostpartumComplicationList() {
		return postpartumComplicationList;
	}

	public void setPostpartumComplicationList(ArrayList<Map<String, Object>> postpartumComplicationList) {
		this.postpartumComplicationList = postpartumComplicationList;
	}

}
