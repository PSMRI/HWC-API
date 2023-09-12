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
package com.iemr.hwc.data.registrar;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "i_beneficiary")
public class BeneficiaryData {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "BeneficiaryRegID")
	private Long beneficiaryRegID;
	@Expose
	@Column(name = "BeneficiaryID")
	private String beneficiaryID;
	@Expose
	@Column(name = "TitleId")
	private Short titleID;
	@Expose
	@Column(name = "FirstName")
	private String firstName;
	@Expose
	@Column(name = "MiddleName")
	private String middleName;
	@Expose
	@Column(name = "LastName")
	private String lastName;
	@Expose
	@Transient
	private String beneficiaryName;

	@Expose
	@Column(name = "StatusID")
	private Short statusID;
	@Expose
	@Column(name = "GenderID")
	private Short genderID;
	@Expose
	@Transient
	private String genderName;

	@Expose
	@Column(name = "MaritalStatusID")
	private Short maritalStatusID;
	@Expose
	@Column(name = "DOB")
	private Timestamp dob;
	@Transient
	@Expose
	private String age;
	@Transient
	@Expose
	private Integer ageVal;
	@Expose
	@Column(name = "FatherName")
	private String fatherName;
	@Expose
	@Column(name = "SpouseName")
	private String spouseName;
	@Expose
	@Column(name = "AadharNo")
	private String aadharNo;
	@Expose
	@Column(name = "GovtIdentityNo")
	private String govIdentityNo;
	@Expose
	@Column(name = "GovtIdentityTypeID")
	private Short govIdentityTypeID;
	@Expose
	@Column(name = "RegisteredServiceID")
	private Short registeredServiceID;
	@Expose
	@Column(name = "SexualOrientationID")
	private Short sexuaOrientationID;
	@Expose
	@Column(name = "PlaceOfWork")
	private String placeOfWork;
	@Expose
	@Column(name = "SourseOfInformation")
	private String sourseOfInformation;
	@Expose
	@Column(name = "EmergencyRegistration")
	private String emergencyRegistration;
	@Expose
	@Column(name = "IsHIVPos")
	private Boolean isHivPos;
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;
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
	@Column(name = "LastModDate", insertable = false)
	private Timestamp lastModDate;

	@Expose
	@Column(name = "FlowStatusFlag", insertable = false)
	private Character flowStatusFlag;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "beneficiaryRegID", referencedColumnName = "beneficiaryRegID", insertable = false, updatable = false)
	@Expose
	private BeneficiaryDemographicData benDemoData;

	@OneToMany(mappedBy = "benData", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Expose
	private Set<BeneficiaryPhoneMapping> benPhoneMap;
	
	@Transient
	@Expose
	private String  benImage; 
	
	@Transient
	@Expose
	private String villageName;
	
	@Transient
	@Expose
	private String districtName;
	
	@Transient
	@Expose 
	private String servicePointName;

	public String getImage() {
		return benImage;
	}

	public void setImage(String benImage) {
		this.benImage = benImage;
	}

	public BeneficiaryData() {
	}

	public BeneficiaryData(Long beneficiaryRegID, String beneficiaryID, Short titleID, String firstName,
			String middleName, String lastName, String beneficiaryName, Short statusID, Short genderID,
			String genderName, Short maritalStatusID, Timestamp dob, String age, String fatherName, String spouseName,
			String aadharNo, String govIdentityNo, Short govIdentityTypeID, Short registeredServiceID,
			Short sexuaOrientationID, String placeOfWork, String sourseOfInformation, String emergencyRegistration,
			Boolean isHivPos, Boolean deleted, String createdBy, Timestamp createdDate, String modifiedBy,
			Timestamp lastModDate, Character flowStatusFlag, BeneficiaryDemographicData benDemoData,
			Set<BeneficiaryPhoneMapping> benPhoneMap) {
		super();
		this.beneficiaryRegID = beneficiaryRegID;
		this.beneficiaryID = beneficiaryID;
		this.titleID = titleID;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.beneficiaryName = beneficiaryName;
		this.statusID = statusID;
		this.genderID = genderID;
		this.genderName = genderName;
		this.maritalStatusID = maritalStatusID;
		this.dob = dob;
		this.age = age;
		this.fatherName = fatherName;
		this.spouseName = spouseName;
		this.aadharNo = aadharNo;
		this.govIdentityNo = govIdentityNo;
		this.govIdentityTypeID = govIdentityTypeID;
		this.registeredServiceID = registeredServiceID;
		this.sexuaOrientationID = sexuaOrientationID;
		this.placeOfWork = placeOfWork;
		this.sourseOfInformation = sourseOfInformation;
		this.emergencyRegistration = emergencyRegistration;
		this.isHivPos = isHivPos;
		this.deleted = deleted;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
		this.flowStatusFlag = flowStatusFlag;
		this.benDemoData = benDemoData;
		this.benPhoneMap = benPhoneMap;
	}

	public BeneficiaryData(Long beneficiaryRegID, String beneficiaryID, String beneficiaryName, Date dob,
			Short genderID, Timestamp createdDate, String districtName, String villageName) {
		this.beneficiaryRegID = beneficiaryRegID;
		this.beneficiaryID = beneficiaryID;
		this.beneficiaryName = beneficiaryName;
		this.genderID = genderID;
		// this.dob = dob;
		if (dob != null) {
			Date date = (Date) dob;
			Calendar cal = Calendar.getInstance();

			cal.setTime(date);

			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DAY_OF_MONTH);

			java.time.LocalDate todayDate = java.time.LocalDate.now();
			java.time.LocalDate birthdate = java.time.LocalDate.of(year, month, day);
			Period p = Period.between(birthdate, todayDate);

			int d = p.getDays();
			int m = p.getMonths();
			int y = p.getYears();
			//System.out.println("helloo...");

			if (y > 0) {
				this.age = y + " years - " + m + " months";
				this.ageVal = y;
			} else {
				if (m > 0) {
					this.age = m + " months - " + d + " days";
					this.ageVal = 0;
				} else {
					this.age = d + " days";
					this.ageVal = 0;
				}
			}

		}
		this.createdDate = createdDate;
		this.villageName = villageName;
		this.districtName = districtName;
	}

	public static ArrayList<BeneficiaryData> getBeneficiaryData(List<Object[]> resList) {
		ArrayList<BeneficiaryData> resArray = new ArrayList<BeneficiaryData>();
		for (Object[] obj : resList) {
			BeneficiaryData cOBJ = new BeneficiaryData((Long) obj[0], (String) obj[1], (String) obj[2], (Date) obj[3],
					(Short) obj[4], (Timestamp) obj[5], (String)obj[6], (String)obj[7]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
	
	public static ArrayList<BeneficiaryData> getBeneficiaryPersonalData(List<Object[]> resList) {
		ArrayList<BeneficiaryData> resArray = new ArrayList<BeneficiaryData>();
		for (Object[] obj : resList) {
			BeneficiaryData cOBJ = new BeneficiaryData((Long) obj[0], (String) obj[1], (String) obj[2], (Date) obj[3],
					(Short) obj[4], (Timestamp) obj[5], null, null);
			resArray.add(cOBJ);
		}
		return resArray;
	}
	

	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	public String getBeneficiaryID() {
		return beneficiaryID;
	}

	public void setBeneficiaryID(String beneficiaryID) {
		this.beneficiaryID = beneficiaryID;
	}

	public Short getTitleID() {
		return titleID;
	}

	public void setTitleID(Short titleID) {
		this.titleID = titleID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public Short getStatusID() {
		return statusID;
	}

	public void setStatusID(Short statusID) {
		this.statusID = statusID;
	}

	public Short getGenderID() {
		return genderID;
	}

	public void setGenderID(Short genderID) {
		this.genderID = genderID;
	}

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public Short getMaritalStatusID() {
		return maritalStatusID;
	}

	public void setMaritalStatusID(Short maritalStatusID) {
		this.maritalStatusID = maritalStatusID;
	}

	public Timestamp getDob() {
		return dob;
	}

	public void setDob(Timestamp dob) {
		this.dob = dob;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getSpouseName() {
		return spouseName;
	}

	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}

	public String getAadharNo() {
		return aadharNo;
	}

	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}

	public String getGovIdentityNo() {
		return govIdentityNo;
	}

	public void setGovIdentityNo(String govIdentityNo) {
		this.govIdentityNo = govIdentityNo;
	}

	public Short getGovIdentityTypeID() {
		return govIdentityTypeID;
	}

	public void setGovIdentityTypeID(Short govIdentityTypeID) {
		this.govIdentityTypeID = govIdentityTypeID;
	}

	public Short getRegisteredServiceID() {
		return registeredServiceID;
	}

	public void setRegisteredServiceID(Short registeredServiceID) {
		this.registeredServiceID = registeredServiceID;
	}

	public Short getSexuaOrientationID() {
		return sexuaOrientationID;
	}

	public void setSexuaOrientationID(Short sexuaOrientationID) {
		this.sexuaOrientationID = sexuaOrientationID;
	}

	public String getPlaceOfWork() {
		return placeOfWork;
	}

	public void setPlaceOfWork(String placeOfWork) {
		this.placeOfWork = placeOfWork;
	}

	public String getSourseOfInformation() {
		return sourseOfInformation;
	}

	public void setSourseOfInformation(String sourseOfInformation) {
		this.sourseOfInformation = sourseOfInformation;
	}

	public String getEmergencyRegistration() {
		return emergencyRegistration;
	}

	public void setEmergencyRegistration(String emergencyRegistration) {
		this.emergencyRegistration = emergencyRegistration;
	}

	public Boolean getIsHivPos() {
		return isHivPos;
	}

	public void setIsHivPos(Boolean isHivPos) {
		this.isHivPos = isHivPos;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
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

	public Character getFlowStatusFlag() {
		return flowStatusFlag;
	}

	public void setFlowStatusFlag(Character flowStatusFlag) {
		this.flowStatusFlag = flowStatusFlag;
	}

	public BeneficiaryDemographicData getBenDemoData() {
		return benDemoData;
	}

	public void setBenDemoData(BeneficiaryDemographicData benDemoData) {
		this.benDemoData = benDemoData;
	}

	public Set<BeneficiaryPhoneMapping> getBenPhoneMap() {
		return benPhoneMap;
	}

	public void setBenPhoneMap(Set<BeneficiaryPhoneMapping> benPhoneMap) {
		this.benPhoneMap = benPhoneMap;
	}

	public String getServicePointName() {
		return servicePointName;
	}

	public void setServicePointName(String servicePointName) {
		this.servicePointName = servicePointName;
	}

}
