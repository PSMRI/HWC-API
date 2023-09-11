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
package com.iemr.hwc.data.login;

import java.sql.Timestamp;

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
@Table(name = "m_user")
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "UserID")
	private Long userID;
	@Expose
	@Column(name = "TitleID")	private Short titleID;
	@Expose
	@Column(name = "FirstName")
	private String firstName;
	@Expose
	@Column(name = "MiddleName")
	private String middleName;
	@Expose
	@Column(name = "lastName")
	private String lastName;
	@Expose
	@Column(name = "GenderID")
	private Short genderID;
	@Expose
	@Column(name = "MaritalStatusID")
	private Short maritalStatusID;
	@Expose
	@Column(name = "AadhaarNo")
	private String aadhaarNo;
	@Expose
	@Column(name = "PAN")
	private String pan;
	@Expose
	@Column(name = "DOB")
	private Timestamp dob;
	@Expose
	@Column(name = "DOJ")
	private Timestamp doj;
	@Expose
	@Column(name = "QualificationID")
	private Integer qualificationID;
	@Expose
	@Column(name = "UserName")
	private String userName;
	@Expose
	@Column(name = "Password")
	private String password;
	@Expose
	@Column(name = "AgentID")
	private String agentID;
	@Expose
	@Column(name = "AgentPassword")
	private String agentPassword;
	@Expose
	@Column(name = "EmailID")
	private String emailID;
	@Expose
	@Column(name = "StatusID")
	private Short statusID;
	@Expose
	@Column(name = "EmergencyContactPerson")
	private String emergencyContactPerson;
	@Expose
	@Column(name = "EmergencyContactNo")
	private String emergencyContactNo;
	@Expose
	@Column(name = "IsSupervisor")
	private Boolean isSupervisor;
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;
	@Expose
	@Column(name = "CreatedBy")
	private String createdBy;
	@Expose
	@Column(name = "CreatedDate")
	private Timestamp createdDate;
	@Expose
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	@Expose
	@Column(name = "LastModDate")
	private Timestamp lastModDate;

}
