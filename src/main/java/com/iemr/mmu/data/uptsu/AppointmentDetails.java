package com.iemr.mmu.data.uptsu;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import lombok.Data;
@Data
@Entity
@Table(name = "t_104appointmentdetails")
public class AppointmentDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "Id")
	private int id;
	
	@Column(name = "BlockName")
	private String blockName;
	
	@Column(name = "FacilityName")
	private String facilityName;
	
	@Column(name = "FacilityCode")
	private String facilityCode;
	
	@Column(name = "ChoName")
	private String choName;
	
	@Column(name = "Employee_Code")
	private String employeeCode;
	
	@Column(name = "HFRID")
	private String hfrId;
	
	@Column(name = "FacilityPhoneNo")
	private String facilityPhoneNo ;
	
	@Column(name = "AppointmentDate")
	private Timestamp appointmentDate;
	
	@Column(name = "BenRegId")
	private String benRegId;
	
	@Column(name = "BenCallID")
	private String benCallId;
	
	@Column(name = "AlternateMobileNo")
	private String alternateMobNo;
	
	@Column(name = "RefferedFlag")
	private boolean refferedFlag;
	
	@Column(name = "ProviderServiceMapID")
	private int providerServiceMapID;
	
	@Column(name = "Deleted")
	private boolean deleted;
	
	@Column(name = "Processed")
	private char processed;
	
	@Column(name = "CreatedDate")
	private Timestamp createdDate;
	
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	
	@Column(name = "LastModDate")
	private Timestamp lastModDate;
	
	@Column(name = "CreatedBy")
	private String createdBy;

}
