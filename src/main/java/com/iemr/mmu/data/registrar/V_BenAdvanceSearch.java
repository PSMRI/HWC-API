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
package com.iemr.mmu.data.registrar;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

@Entity
@Table(name = "v_benadvancesearch")
public class V_BenAdvanceSearch {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	private Short genderID;
	@Expose
	@Column(name = "GenderName")
	private String genderName;
	@Expose
	@Column(name = "dob")
	private Date dob;
	@Expose
	@Transient
	private String age;
	@Expose
	@Column(name = "FatherName")
	private String fatherName;
	@Expose
	@Column(name = "AadharNo")
	private String aadharNo;

	@Expose
	@Column(name = "StateID")
	private Integer stateID;

	@Expose
	@Column(name = "DistrictID")
	private Integer districtID;
	@Expose
	@Column(name = "districtName")
	private String districtName;
	@Expose
	@Column(name = "DistrictBranchID")
	private Integer districtBranchID;
	@Expose
	@Column(name = "villageName")
	private String villageName;
	@Expose
	@Column(name = "PhoneNo")
	private String phoneNo;
	@Expose
	@Column(name = "GovtIdentityNo")
	private String govtIdentityNo;
	@Expose
	@Column(name = "FlowStatusFlag")
	private Character flowStatusFlag;

	@Expose
	@Column(name = "RegCreatedDate")
	private Timestamp regCreatedDate;
	@Expose
	@Column(name = "RegLastModDate")
	private Timestamp regLastModDate;

	public V_BenAdvanceSearch() {
	}

	public V_BenAdvanceSearch(Long id, Long beneficiaryRegID, String beneficiaryID, String firstName, String lastName,
			Short genderID, String genderName, Date dob, String fatherName, String aadharNo, Integer stateID,Integer districtID,
			String districtName, Integer districtBranchID, String villageName, String phoneNo, String govtIdentityNo,
			Character flowStatusFlag, Timestamp regCreatedDate, Timestamp regLastModDate) {
		super();
		this.beneficiaryRegID = beneficiaryRegID;
		this.beneficiaryID = beneficiaryID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.genderID = genderID;
		this.genderName = genderName;
		this.dob = dob;
		this.fatherName = fatherName;
		this.aadharNo = aadharNo;
		this.stateID = stateID;
		this.districtID = districtID;
		this.districtName = districtName;
		this.districtBranchID = districtBranchID;
		this.villageName = villageName;
		this.phoneNo = phoneNo;
		this.govtIdentityNo = govtIdentityNo;
		this.flowStatusFlag = flowStatusFlag;
		this.regCreatedDate = regCreatedDate;
		this.regLastModDate = regLastModDate;
	}
	
	public static String getSearchData(List<Object[]> resList) {
		ArrayList<V_BenAdvanceSearch> resArray = new ArrayList<V_BenAdvanceSearch>();
		if (resList.size() > 0) {
			for (Object[] obj : resList) {
				
				V_BenAdvanceSearch searchResult = new V_BenAdvanceSearch();
				searchResult.beneficiaryRegID = (Long) obj[0];
				searchResult.beneficiaryID = (String) obj[1];
				searchResult.benName = (String) obj[2];
				searchResult.genderID = (Short) obj[3];
				searchResult.genderName = (String) obj[4];
				searchResult.dob = (Date) obj[5];
				if (obj[5] != null) {
					Date date = (Date) obj[5];
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

					if (y > 0) {
						searchResult.age = y + " years - " + m + " months";
					} else {
						if (m > 0) {
							searchResult.age = m + " months - " + d + " days";
						} else {
							searchResult.age = d + " days";
						}
					}

				}
				searchResult.fatherName = (String) obj[6];
				searchResult.aadharNo = (String) obj[7];
				searchResult.districtID = (Integer) obj[8];
				searchResult.districtName = (String) obj[9];
				searchResult.districtBranchID = (Integer) obj[10];
				searchResult.villageName = (String) obj[11];
				searchResult.phoneNo = (String) obj[12];
				searchResult.govtIdentityNo = (String) obj[13];
				
				resArray.add(searchResult);
			}
		}
		return new Gson().toJson(resArray);
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

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
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

	public Integer getDistrictBranchID() {
		return districtBranchID;
	}

	public void setDistrictBranchID(Integer districtBranchID) {
		this.districtBranchID = districtBranchID;
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

	public String getGovtIdentityNo() {
		return govtIdentityNo;
	}

	public void setGovtIdentityNo(String govtIdentityNo) {
		this.govtIdentityNo = govtIdentityNo;
	}

	public Character getFlowStatusFlag() {
		return flowStatusFlag;
	}

	public void setFlowStatusFlag(Character flowStatusFlag) {
		this.flowStatusFlag = flowStatusFlag;
	}

	public Timestamp getRegCreatedDate() {
		return regCreatedDate;
	}

	public void setRegCreatedDate(Timestamp regCreatedDate) {
		this.regCreatedDate = regCreatedDate;
	}

	public Timestamp getRegLastModDate() {
		return regLastModDate;
	}

	public void setRegLastModDate(Timestamp regLastModDate) {
		this.regLastModDate = regLastModDate;
	}
	
	public Integer getStateID() {
		return stateID;
	}

	public void setStateID(Integer stateID) {
		this.stateID = stateID;
	}

}
