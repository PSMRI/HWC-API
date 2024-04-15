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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "v_benDetailsFetching")
public class FetchBeneficiaryDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Expose
	@Column(name = "BeneficiaryRegID")
	private Long beneficiaryRegID;

	@Expose
	@Column(name = "BeneficiaryID")
	private String beneficiaryID;

	@Expose
	@Column(name = "FirstName")
	private String firstName;

	@Expose
	@Column(name = "LastName")
	private String lastName;
	@Expose
	@Transient
	private String benName;
	@Expose
	@Column(name = "GenderID")
	private Short gender;

	@Expose
	@Column(name = "dob")
	private Date dob;
	@Expose
	@Column(name = "MaritalStatusID")
	private Short maritalStatus;
	@Expose
	@Column(name = "SpouseName")
	private String husbandName;
	@Expose
	@Column(name = "IncomeStatusID")
	private Short income;
	@Expose
	@Column(name = "EducationID")
	private Short educationQualification;
	@Expose
	@Column(name = "OccupationID")
	private Short occupation;
	@Expose
	@Column(name = "BlockID")
	private Integer blockID;
	@Expose
	@Column(name = "BlockName")
	private String blockName;
	@Expose
	@Column(name = "StateID")
	private Integer stateID;
	@Expose
	@Column(name = "StateName")
	private String stateName;
	@Expose
	@Column(name = "CommunityID")
	private Integer community;
	@Expose
	@Column(name = "ReligionID")
	private Short religion;
	@Expose
	@Column(name = "FatherName")
	private String fatherName;
	@Expose
	@Column(name = "AadharNo")
	private String aadharNo;
	@Expose
	@Column(name = "DistrictID")
	private Integer districtID;
	@Expose
	@Column(name = "districtName")
	private String districtName;

	@Expose
	@Column(name = "DistrictBranchID")
	private Integer villageID;
	@Expose
	@Column(name = "villageName")
	private String villageName;

	@Expose
	@Column(name = "PhoneNo")
	private String phoneNo;
	@Expose
	@Column(name = "GovtIdentityTypeID")
	private Short govtIdentityTypeID;
	@Expose
	@Column(name = "GovtIdentityNo")
	private String govtIdentityNo;
	@Expose
	@Column(name = "IsGovtID")
	private Boolean isGovtID;
	@Expose
	@Column(name = "MarrigeDate")
	private Date marrigeDate;
	@Expose
	@Column(name = "LiteracyStatus")
	private String literacyStatus;
	@Expose
	@Column(name = "MotherName")
	private String motherName;
	@Expose
	@Column(name = "EmailID")
	private String emailID;
	@Expose
	@Column(name = "BankName")
	private String bankName;
	@Expose
	@Column(name = "BranchName")
	private String branchName;
	@Expose
	@Column(name = "IFSCCode")
	private String IFSCCode;
	@Expose
	@Column(name = "AccountNo")
	private String accountNumber;

	@Expose
	@Column(name = "BenGovMapID")
	private String benGovMapID;

	@Transient
	@Expose
	private ArrayList<Map<String, Object>> govID;

	@Transient
	@Expose
	private ArrayList<Map<String, Object>> otherGovID;

	@Transient
	@Expose
	private String image;

	@Transient
	@Expose
	private int age;

	@Transient
	@Expose
	private Integer ageAtMarriage;

	public FetchBeneficiaryDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FetchBeneficiaryDetails(Long beneficiaryRegID, String beneficiaryID, String firstName, String lastName,
			Short genderID, Date dob, Short maritalStatusID, String spouseName, Short incomeStatusID,
			Short educationID, Short occupationID, Integer blockID, String blockName, Integer stateID, String stateName,
			Integer communityID, Short religionID, String fatherName, String aadharNo, Integer districtID,
			String districtName, Integer districtBranchID, String villageName, String phoneNo, Short govtIdentityTypeID,
			String govtIdentityNo, Boolean isGovtID, Date marrigeDate, String literacyStatus, String motherName,
			String emailID, String bankName, String branchName, String iFSCCode, String accountNo,
			ArrayList<Map<String, Object>> govIDArray, String s, ArrayList<Map<String, Object>> otherGovIDArray,
			 Integer ageAtMarriage) {
		super();
		this.beneficiaryRegID = beneficiaryRegID;
		this.beneficiaryID = beneficiaryID;

		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = genderID;
		this.dob = dob;
		this.maritalStatus = maritalStatusID;
		this.husbandName = spouseName;
		this.income = incomeStatusID;
		this.educationQualification = educationID;
		this.occupation = occupationID;
		this.blockID = blockID;
		this.blockName = blockName;
		this.stateID = stateID;
		this.stateName = stateName;
		this.community = communityID;
		this.religion = religionID;
		this.fatherName = fatherName;
		this.aadharNo = aadharNo;
		this.districtID = districtID;
		this.districtName = districtName;
		this.villageID = districtBranchID;
		this.villageName = villageName;
		this.phoneNo = phoneNo;
		this.govtIdentityTypeID = govtIdentityTypeID;
		this.govtIdentityNo = govtIdentityNo;
		this.isGovtID = isGovtID;
		this.marrigeDate = marrigeDate;
		this.literacyStatus = literacyStatus;
		this.motherName = motherName;
		this.emailID = emailID;
		this.bankName = bankName;
		this.branchName = branchName;
		this.IFSCCode = iFSCCode;
		this.accountNumber = accountNo;
		this.govID = govIDArray;
		this.image = s;
		this.otherGovID = otherGovIDArray;
		this.ageAtMarriage = ageAtMarriage;
		//this.age = age;
	}

	public static FetchBeneficiaryDetails getFetchBeneficiaryDetailsObj(Object[] arrayOBJ,
			ArrayList<Map<String, Object>> govIDArray) {
		// FetchBeneficiaryDetails
		return null;
	}

	public static FetchBeneficiaryDetails getBeneficiaryDetails(Object[] obj, ArrayList<Map<String, Object>> govIDArray,
			String s, ArrayList<Map<String, Object>> otherGovIDArray) {
		Integer ageWhenMarried;
		int age;
		if (obj[27] != null && obj[5] != null) {
			Date date = (Date) obj[27];
			Calendar cal = Calendar.getInstance();

			cal.setTime(date);

			int year = cal.get(Calendar.YEAR);
			// int month = cal.get(Calendar.MONTH) + 1;
			// int day = cal.get(Calendar.DAY_OF_MONTH);

			Date date1 = (Date) obj[5];
			Calendar cal1 = Calendar.getInstance();

			cal1.setTime(date1);

			int year1 = cal1.get(Calendar.YEAR);
			// int month1 = cal.get(Calendar.MONTH) + 1;
			// int day1 = cal.get(Calendar.DAY_OF_MONTH);

			// java.time.LocalDate todayDate = java.time.LocalDate.now();
			// java.time.LocalDate marriageDate = java.time.LocalDate.of(year,
			// month, day);
			// Period p = Period.between(marriageDate, todayDate);

			ageWhenMarried = year - year1;
			//System.out.println("helloo...");

		} else {
			ageWhenMarried = null;

		}
		FetchBeneficiaryDetails cOBJ = new FetchBeneficiaryDetails((Long) obj[0], (String) obj[1], (String) obj[2],
				(String) obj[3], (Short) obj[4], (Date) obj[5], (Short) obj[6], (String) obj[7], (Short) obj[8],
				(Short) obj[9], (Short) obj[10], (Integer) obj[11], (String) obj[12], (Integer) obj[13],
				(String) obj[14], (Integer) obj[15], (Short) obj[16], (String) obj[17], (String) obj[18],
				(Integer) obj[19], (String) obj[20], (Integer) obj[21], (String) obj[22], (String) obj[23],
				(Short) obj[24], (String) obj[25], (Boolean) obj[26], (Date) obj[27], (String) obj[28],
				(String) obj[29], (String) obj[30], (String) obj[31], (String) obj[32], (String) obj[33],
				(String) obj[34], govIDArray, s, otherGovIDArray, ageWhenMarried);

		return cOBJ;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBenName() {
		return benName;
	}

	public void setBenName(String benName) {
		this.benName = benName;
	}

	public Short getGender() {
		return gender;
	}

	public void setGender(Short gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Short getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(Short maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getHusbandName() {
		return husbandName;
	}

	public void setHusbandName(String husbandName) {
		this.husbandName = husbandName;
	}

	public Short getIncome() {
		return income;
	}

	public void setIncome(Short income) {
		this.income = income;
	}

	public Short getEducationQualification() {
		return educationQualification;
	}

	public void setEducationQualification(Short educationQualification) {
		this.educationQualification = educationQualification;
	}

	public Short getOccupation() {
		return occupation;
	}

	public void setOccupation(Short occupation) {
		this.occupation = occupation;
	}

	public Integer getBlockID() {
		return blockID;
	}

	public void setBlockID(Integer blockID) {
		this.blockID = blockID;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public Integer getStateID() {
		return stateID;
	}

	public void setStateID(Integer stateID) {
		this.stateID = stateID;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Integer getCommunity() {
		return community;
	}

	public void setCommunity(Integer community) {
		this.community = community;
	}

	public Short getReligion() {
		return religion;
	}

	public void setReligion(Short religion) {
		this.religion = religion;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getAadharNo() {
		return aadharNo;
	}

	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}

	public Integer getDistrictID() {
		return districtID;
	}

	public void setDistrictID(Integer districtID) {
		this.districtID = districtID;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public Integer getVillageID() {
		return villageID;
	}

	public void setVillageID(Integer villageID) {
		this.villageID = villageID;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Short getGovtIdentityTypeID() {
		return govtIdentityTypeID;
	}

	public void setGovtIdentityTypeID(Short govtIdentityTypeID) {
		this.govtIdentityTypeID = govtIdentityTypeID;
	}

	public String getGovtIdentityNo() {
		return govtIdentityNo;
	}

	public void setGovtIdentityNo(String govtIdentityNo) {
		this.govtIdentityNo = govtIdentityNo;
	}

	public Boolean getIsGovtID() {
		return isGovtID;
	}

	public void setIsGovtID(Boolean isGovtID) {
		this.isGovtID = isGovtID;
	}

	public String getLiteracyStatus() {
		return literacyStatus;
	}

	public void setLiteracyStatus(String literacyStatus) {
		this.literacyStatus = literacyStatus;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getIFSCCode() {
		return IFSCCode;
	}

	public void setIFSCCode(String iFSCCode) {
		IFSCCode = iFSCCode;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

}
