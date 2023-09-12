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
package com.iemr.hwc.data.neonatal;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Entity
@Table(name = "t_infantbirthdetails")
@Data
public class InfantBirthDetails {

	@Id
	@GeneratedValue
	@Expose
	@Column(name = "Id", insertable = false)
	private Long id;

	@Expose
	@Column(name = "Beneficiaryregid")
	private Long beneficiaryRegID;

	@Expose
	@Column(name = "Benvisitid")
	private Long benVisitID;

	@Expose
	@Column(name = "Visitcode")
	private Long visitCode;

	@Expose
	@Column(name = "Deliveryplaceid")
	private Short deliveryPlaceID;

	@Expose
	@Column(name = "Deliveryplace")
	private String deliveryPlace;

	@Expose
	@Column(name = "Otherdeliveryplace")
	private String otherDeliveryPlace;

	@Expose
	@Column(name = "Deliverytype")
	private String deliveryType;

	@Expose
	@Column(name = "Deliverytypeid")
	private Integer deliveryTypeID;

	@Expose
	@Column(name = "Birthcomplication")
	private String birthComplication;

	@Expose
	@Column(name = "Birthcomplicationid")
	private Integer birthComplicationID;

	@Expose
	@Column(name = "OtherDeliveryComplication")
	private String otherDeliveryComplication;

	@Expose
	@Column(name = "Gestation")
	private String gestation;

	@Expose
	@Column(name = "Gestationid")
	private Short gestationID;

	@Expose
	@Column(name = "Birthweightofnewborn")
	private Integer birthWeightOfNewborn;

	@Expose
	@Column(name = "Deliveryconductedby")
	private String deliveryConductedBy;

	@Expose
	@Column(name = "Deliveryconductedbyid")
	private Short deliveryConductedByID;

	@Expose
	@Column(name = "Congenitalanomalieslist")
	private String congenitalAnomalies;

	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;

	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private String processed;

	@Expose
	@Column(name = "CreatedBy", updatable = false)
	private String createdBy;

	@Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;

	@Expose
	@Column(name = "ModifiedBy", insertable = false, updatable = true)
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

	@Transient
	private String[] congenitalAnomaliesList;

	// adolescent
	@Expose
	@Column(name = "Dateofbirth")
	private Timestamp dateOfBirth;

	@Expose
	@Column(name = "Timeofbirth")
	private String timeOfBirth;

	@Expose
	@Column(name = "DateOfupdatingbirthdetails")
	private Timestamp dateOfUpdatingBirthDetails;

}
