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
package com.iemr.hwc.data.uptsu;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
@Entity
@Table(name = "t_104referredcdssdetails")
public class Referred104Details {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "id")
	private int id;
	
	@Column(name="beneficiaryRegID")
	private long beneficiaryRegID;
	
	@Column(name = "PatientName")
	private String patientName;
	
	@Column(name = "PatientAge")
	private String patientAge;
	
	@Column(name = "PatientGenderID")
	private int patientGenderID;
	
	@Column(name = "SelecteDiagnosisID")
	private String selecteDiagnosisID;
	
	@Column(name = "SelecteDiagnosis")
	private String selecteDiagnosis;
	
	@Column(name = "PresentChiefComplaint")
	private String presentChiefComplaint;
	
	@Column(name = "PresentChiefComplaintID")
	private String presentChiefComplaintID;
	
	@Column(name = "RecommendedAction")
	private String recommendedAction;
	
	@Column(name = "Remarks")
	private String remarks;
	
	@Column(name = "Algorithm")
	private String algorithm;
	
	@Column(name = "BeneficiaryID")
	private long beneficiaryID;
	
	@Column(name = "SessionID")
	private int sessionID;
	
	@Column(name = "ServiceID")
	private int serviceID;
	
	@Column(name = "PoviderServiceMapID")
	private int providerServiceMapID;
	
	@Column(name = "CreatedBy")
	private String createdBy;
	
	@Column(name = "VanID")
	private int vanID;
	
	@Column(name = "ParkingPlaceID")
	private int parkingPlaceID;
	
	@Column(name = "BenCallID")
	private String benCallID;
	
	@Column(name = "ActionId")
	private int actionId;
	
	@Column(name = "Action")
	private String action;

}
