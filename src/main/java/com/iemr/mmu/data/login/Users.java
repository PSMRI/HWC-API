package com.iemr.mmu.data.login;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

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
