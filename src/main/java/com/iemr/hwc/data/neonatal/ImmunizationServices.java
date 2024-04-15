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
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Entity
@Table(name = "t_immunizationservices")
@Data
public class ImmunizationServices {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	@Column(name = "Dateofvisit")
	private Timestamp dateOfVisit;

	@Expose
	@Column(name = "Immunizationservicestype")
	private String immunizationServicesType;

	@Expose
	@Column(name = "Immunizationservicestypeid")
	private Short immunizationServicesTypeID;

	@Expose
	@Column(name = "CurrentImmunizationservice")
	private String currentImmunizationService;

	@Expose
	@Column(name = "CurrentImmunizationserviceid")
	private Integer currentImmunizationServiceID;

	@Expose
	@Column(name = "VaccineName")
	private String vaccineName;

	@Expose
	@Column(name = "Vaccinedose")
	private String vaccineDose;

	@Expose
	@Column(name = "Batchno")
	private String batchNo;

	@Expose
	@Column(name = "Route")
	private String route;

	@Expose
	@Column(name = "Siteofinjection")
	private String siteOfInjection;

	@Expose
	@Column(name = "Siteofinjectionid")
	private Short siteOfInjectionID;

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
	private List<Vaccines> vaccines;

}
