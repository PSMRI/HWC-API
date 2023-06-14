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
package com.iemr.mmu.data.videoconsultation;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.utils.mapper.OutputMapper;


@Entity
@Table(name = "M_User")
public class M_UserTemp {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "UserID")
	private Long userID;
	private Integer TitleID;
	@Expose
	private String FirstName;
	@Expose
	private String MiddleName;
	@Expose
	private String LastName;
	private Integer GenderID;
	private Integer MaritalStatusID;
	private String AadhaarNo;
	private String PAN;
	private Timestamp DOB;
	private Timestamp DOJ;
	private Integer QualificationID;
	@Expose
	@Column(name = "UserName")
	private String UserName;
	@Expose
	private String EmailID;
	private Integer StatusID;
	private String EmergencyContactPerson;
	private String EmergencyContactNo;
	private Boolean IsSupervisor;
	@Expose
	@Column(name = "DesignationID")
	private Integer designationID;
	@Expose
	@Column(name = "ServiceProviderID")
	private Integer ServiceProviderID;
	@Expose
	@Column(name = "Deleted", insertable = false, updatable = false)
	private Boolean Deleted;
	private String CreatedBy;
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp CreatedDate;
	private String ModifiedBy;
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp LastModDate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UserID", insertable = false, updatable = false)
	@Expose
	private VideoConsultationUser userSwymed;

	@Transient
	private OutputMapper outputMapper = new OutputMapper();

	@Override
	public String toString() {
		return outputMapper.gson().toJson(this);
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public Integer getTitleID() {
		return TitleID;
	}

	public void setTitleID(Integer titleID) {
		TitleID = titleID;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getMiddleName() {
		return MiddleName;
	}

	public void setMiddleName(String middleName) {
		MiddleName = middleName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public Integer getGenderID() {
		return GenderID;
	}

	public void setGenderID(Integer genderID) {
		GenderID = genderID;
	}

	public Integer getMaritalStatusID() {
		return MaritalStatusID;
	}

	public void setMaritalStatusID(Integer maritalStatusID) {
		MaritalStatusID = maritalStatusID;
	}

	public String getAadhaarNo() {
		return AadhaarNo;
	}

	public void setAadhaarNo(String aadhaarNo) {
		AadhaarNo = aadhaarNo;
	}

	public String getPAN() {
		return PAN;
	}

	public void setPAN(String pAN) {
		PAN = pAN;
	}

	public Timestamp getDOB() {
		return DOB;
	}

	public void setDOB(Timestamp dOB) {
		DOB = dOB;
	}

	public Timestamp getDOJ() {
		return DOJ;
	}

	public void setDOJ(Timestamp dOJ) {
		DOJ = dOJ;
	}

	public Integer getQualificationID() {
		return QualificationID;
	}

	public void setQualificationID(Integer qualificationID) {
		QualificationID = qualificationID;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getEmailID() {
		return EmailID;
	}

	public void setEmailID(String emailID) {
		EmailID = emailID;
	}

	public Integer getStatusID() {
		return StatusID;
	}

	public void setStatusID(Integer statusID) {
		StatusID = statusID;
	}

	public String getEmergencyContactPerson() {
		return EmergencyContactPerson;
	}

	public void setEmergencyContactPerson(String emergencyContactPerson) {
		EmergencyContactPerson = emergencyContactPerson;
	}

	public String getEmergencyContactNo() {
		return EmergencyContactNo;
	}

	public void setEmergencyContactNo(String emergencyContactNo) {
		EmergencyContactNo = emergencyContactNo;
	}

	public Boolean getIsSupervisor() {
		return IsSupervisor;
	}

	public void setIsSupervisor(Boolean isSupervisor) {
		IsSupervisor = isSupervisor;
	}

	public Integer getDesignationID() {
		return designationID;
	}

	public void setDesignationID(Integer designationID) {
		this.designationID = designationID;
	}

	public Integer getServiceProviderID() {
		return ServiceProviderID;
	}

	public void setServiceProviderID(Integer serviceProviderID) {
		ServiceProviderID = serviceProviderID;
	}

	public Boolean getDeleted() {
		return Deleted;
	}

	public void setDeleted(Boolean deleted) {
		Deleted = deleted;
	}

	public String getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		CreatedDate = createdDate;
	}

	public String getModifiedBy() {
		return ModifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		ModifiedBy = modifiedBy;
	}

	public Timestamp getLastModDate() {
		return LastModDate;
	}

	public void setLastModDate(Timestamp lastModDate) {
		LastModDate = lastModDate;
	}

	public VideoConsultationUser getUserSwymed() {
		return userSwymed;
	}

	public void setUserSwymed(VideoConsultationUser userSwymed) {
		this.userSwymed = userSwymed;
	}

	public OutputMapper getOutputMapper() {
		return outputMapper;
	}

	public void setOutputMapper(OutputMapper outputMapper) {
		this.outputMapper = outputMapper;
	}
	
	

}
