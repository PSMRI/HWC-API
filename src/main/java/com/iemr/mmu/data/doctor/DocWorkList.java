/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.doctor;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "v_DocWorkist")
public class DocWorkList {
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
	@Column(name = "GenderID")
	private Short genderID;
	@Expose
	@Column(name = "GenderName")
	private String genderName;
	@Expose
	@Column(name = "dob")
	private Timestamp dob;
	@Expose
	@Column(name = "RegCreatedDate")
	private Timestamp regCreatedDate;
	@Expose
	@Column(name = "RegLastModDate")
	private Timestamp regLastModDate;
	@Expose
	@Column(name = "FlowStatusFlag")
	private Character flowStatusFlag;
	@Expose
	@Column(name = "BenVisitID")
	private Long benVisitID;
	@Expose
	@Column(name = "visitNo")
	private Short visitNo;
	@Expose
	@Column(name = "VisitFlowStatusFlag")
	private String visitFlowStatusFlag;
	@Expose
	@Column(name = "VisitCategory")
	private String visitCategory;
	@Expose
	@Column(name = "VisitCreatedDate")
	private Timestamp visitCreatedDate;

	@Expose
	@Column(name = "FatherName")
	private String fatherName;

	@Expose
	@Column(name = "districtName")
	private String districtName;

	@Expose
	@Column(name = "villageName")
	private String villageName;

	@Expose
	@Column(name = "PhoneNo")
	private String phoneNo;

	public DocWorkList() {
	}

	public DocWorkList(Long id, Long beneficiaryRegID, String beneficiaryID, String firstName, String lastName,
			Short genderID, String genderName, Timestamp dob, Timestamp regCreatedDate, Timestamp regLastModDate,
			Character flowStatusFlag, Long benVisitID, Short visitNo, String visitFlowStatusFlag, String visitCategory,
			Timestamp visitCreatedDate, String fatherName, String districtName, String villageName, String phoneNo) {
		super();
		this.id = id;
		this.beneficiaryRegID = beneficiaryRegID;
		this.beneficiaryID = beneficiaryID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.genderID = genderID;
		this.genderName = genderName;
		this.dob = dob;
		this.regCreatedDate = regCreatedDate;
		this.regLastModDate = regLastModDate;
		this.flowStatusFlag = flowStatusFlag;
		this.benVisitID = benVisitID;
		this.visitNo = visitNo;
		this.visitFlowStatusFlag = visitFlowStatusFlag;
		this.visitCategory = visitCategory;
		this.visitCreatedDate = visitCreatedDate;
		this.fatherName = fatherName;
		this.districtName = districtName;
		this.villageName = villageName;
		this.phoneNo = phoneNo;
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

	public Timestamp getDob() {
		return dob;
	}

	public void setDob(Timestamp dob) {
		this.dob = dob;
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

	public Character getFlowStatusFlag() {
		return flowStatusFlag;
	}

	public void setFlowStatusFlag(Character flowStatusFlag) {
		this.flowStatusFlag = flowStatusFlag;
	}

	public Long getBenVisitID() {
		return benVisitID;
	}

	public void setBenVisitID(Long benVisitID) {
		this.benVisitID = benVisitID;
	}

	public Short getVisitNo() {
		return visitNo;
	}

	public void setVisitNo(Short visitNo) {
		this.visitNo = visitNo;
	}

	public String getVisitFlowStatusFlag() {
		return visitFlowStatusFlag;
	}

	public void setVisitFlowStatusFlag(String visitFlowStatusFlag) {
		this.visitFlowStatusFlag = visitFlowStatusFlag;
	}

	public String getVisitCategory() {
		return visitCategory;
	}

	public void setVisitCategory(String visitCategory) {
		this.visitCategory = visitCategory;
	}

	public Timestamp getVisitCreatedDate() {
		return visitCreatedDate;
	}

	public void setVisitCreatedDate(Timestamp visitCreatedDate) {
		this.visitCreatedDate = visitCreatedDate;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
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

}
