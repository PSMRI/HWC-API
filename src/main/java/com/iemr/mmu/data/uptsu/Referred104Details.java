package com.iemr.mmu.data.uptsu;

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
