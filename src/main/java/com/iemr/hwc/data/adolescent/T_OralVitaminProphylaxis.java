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
package com.iemr.hwc.data.adolescent;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
@Entity
@Table(name = "t_oralvitaminprophylaxis")
public class T_OralVitaminProphylaxis {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	@Column(name = "Id", insertable = false)
	private Long id;

	@Expose
	@Column(name = "Beneficiaryregid")
	private Long beneficiaryRegID;

	@Expose
	@Column(name = "Visitcode")
	private Long visitCode;

	@Expose
	@Column(name = "Dateofvisit")
	private Timestamp dateOfVisit;
	@Expose
	@Column(name = "Vaccinename")
	private String vaccineName;
	@Expose
	@Column(name = "Oralvitaminstatus")
	private String oralVitaminAStatus;
	@Expose
	@Column(name = "Oralvitaminnoofdoseid")
	private Integer noOfOralVitaminADoseID;
	@Expose
	@Column(name = "Oralvitaminnoofdose")
	private String noOfOralVitaminADose;
	@Expose
	@Column(name = "Dose")
	private String dose;
	@Expose
	@Column(name = "Batchno")
	private String batchNo;
	@Expose
	@Column(name = "Route")
	private String route;

	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;

	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

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
	@Column(name = "vanID")
	private Integer vanID;

	@Expose
	@Column(name = "ParkingPlaceID")
	private Integer parkingPlaceID;

}
