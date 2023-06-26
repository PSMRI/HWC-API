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
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.hwc.annotation.sqlInjectionSafe.SQLInjectionSafe;

@Entity
@Table(name = "t_perinatalhistory")
public class PerinatalHistory {
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
	@Column(name = "DeliveryPlaceID")
	private Short deliveryPlaceID;

	@Expose
	@Column(name = "PlaceOfDelivery")
	private @SQLInjectionSafe String placeOfDelivery;

	@Expose
	@Column(name = "OtherPlaceOfDelivery")
	private @SQLInjectionSafe String otherPlaceOfDelivery;

	@Expose
	@Column(name = "DeliveryTypeID")
	private Short deliveryTypeID;

	@Expose
	@Column(name = "TypeOfDelivery")
	private @SQLInjectionSafe String typeOfDelivery;

	@Expose
	@Column(name = "ComplicationAtBirthID")
	private Short complicationAtBirthID;

	@Expose
	@Column(name = "ComplicationAtBirth")
	private @SQLInjectionSafe String complicationAtBirth;

	@Expose
	@Column(name = "OtherComplicationAtBirth")
	private @SQLInjectionSafe String otherComplicationAtBirth;

	@Expose
	@Column(name = "Gestation")
	private @SQLInjectionSafe String gestation;

//	@Expose
//	@Column(name = "BirthWeight_kg")
//	private Double birthWeight_kg;
	
	@Expose
	@Column(name = "BirthWeight_gm")
	private Double birthWeightG;

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

	public String getPlaceOfDelivery() {
		return placeOfDelivery;
	}

	public void setPlaceOfDelivery(String placeOfDelivery) {
		this.placeOfDelivery = placeOfDelivery;
	}

	public String getTypeOfDelivery() {
		return typeOfDelivery;
	}

	public void setTypeOfDelivery(String typeOfDelivery) {
		this.typeOfDelivery = typeOfDelivery;
	}

	public String getComplicationAtBirth() {
		return complicationAtBirth;
	}

	public void setComplicationAtBirth(String complicationAtBirth) {
		this.complicationAtBirth = complicationAtBirth;
	}

	public String getGestation() {
		return gestation;
	}

	public void setGestation(String gestation) {
		this.gestation = gestation;
	}

//	public Double getBirthWeight_kg() {
//		return birthWeight_kg;
//	}
//
//	public void setBirthWeight_kg(Double birthWeight_kg) {
//		this.birthWeight_kg = birthWeight_kg;
//	}
	
	

	public Double getBirthWeightG() {
		return birthWeightG;
	}

	public void setBirthWeightG(Double birthWeightG) {
		this.birthWeightG = birthWeightG;
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

	public Short getDeliveryPlaceID() {
		return deliveryPlaceID;
	}

	public void setDeliveryPlaceID(Short deliveryPlaceID) {
		this.deliveryPlaceID = deliveryPlaceID;
	}

	public String getOtherPlaceOfDelivery() {
		return otherPlaceOfDelivery;
	}

	public void setOtherPlaceOfDelivery(String otherPlaceOfDelivery) {
		this.otherPlaceOfDelivery = otherPlaceOfDelivery;
	}

	public Short getDeliveryTypeID() {
		return deliveryTypeID;
	}

	public void setDeliveryTypeID(Short deliveryTypeID) {
		this.deliveryTypeID = deliveryTypeID;
	}

	public Short getComplicationAtBirthID() {
		return complicationAtBirthID;
	}

	public void setComplicationAtBirthID(Short complicationAtBirthID) {
		this.complicationAtBirthID = complicationAtBirthID;
	}

	public String getOtherComplicationAtBirth() {
		return otherComplicationAtBirth;
	}

	public void setOtherComplicationAtBirth(String otherComplicationAtBirth) {
		this.otherComplicationAtBirth = otherComplicationAtBirth;
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

	public PerinatalHistory(Date createdDate, String placeOfDelivery, String otherPlaceOfDelivery,
			String typeOfDelivery, String complicationAtBirth, String otherComplicationAtBirth, String gestation, Double birthWeightG) {
		super();
		this.captureDate = createdDate;
		this.placeOfDelivery = placeOfDelivery;
		this.otherPlaceOfDelivery = otherPlaceOfDelivery;
		this.typeOfDelivery = typeOfDelivery;
		this.complicationAtBirth = complicationAtBirth;
		this.otherComplicationAtBirth = otherComplicationAtBirth;
		this.gestation = gestation;
		this.birthWeightG = birthWeightG;
//		this.birthWeight_kg = birthWeight_kg;
	}

	public PerinatalHistory(Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID, Short deliveryPlaceID,
			String placeOfDelivery, String otherPlaceOfDelivery, Short deliveryTypeID, String typeOfDelivery,
			Short complicationAtBirthID, String complicationAtBirth, String otherComplicationAtBirth, String gestation,
			Long visitCode, Double birthWeightG) {
		super();
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.deliveryPlaceID = deliveryPlaceID;
		this.placeOfDelivery = placeOfDelivery;
		this.otherPlaceOfDelivery = otherPlaceOfDelivery;
		this.deliveryTypeID = deliveryTypeID;
		this.typeOfDelivery = typeOfDelivery;
		this.complicationAtBirthID = complicationAtBirthID;
		this.complicationAtBirth = complicationAtBirth;
		this.otherComplicationAtBirth = otherComplicationAtBirth;
		this.gestation = gestation;
//		this.birthWeight_kg = birthWeight_kg;
		this.visitCode = visitCode;
		this.birthWeightG = birthWeightG;
	}

	public static PerinatalHistory getBenPerinatalDetails(ArrayList<Object[]> perinatalHistoryDetails) {
		PerinatalHistory perinatalDetails = null;
		if (null != perinatalHistoryDetails && perinatalHistoryDetails.size() > 0) {
			for (Object[] obj : perinatalHistoryDetails) {
				perinatalDetails = new PerinatalHistory((Long) obj[0], (Long) obj[1], (Integer) obj[2], (Short) obj[3],
						(String) obj[4], (String) obj[5], (Short) obj[6], (String) obj[7], (Short) obj[8],
						(String) obj[9], (String) obj[10], (String) obj[11],  (Long) obj[12], (Double) obj[13]);
			}
		}
		return perinatalDetails;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public PerinatalHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

}
