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
package com.iemr.hwc.data.covid19;

import java.sql.Timestamp;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.hwc.annotation.sqlInjectionSafe.SQLInjectionSafe;

@Entity
@Table(name = "t_covid19")
public class Covid19BenFeedback {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	@Column(name = "COVID19ID", insertable = false, updatable = false)
	private Long cOVID19ID;
	@Expose
	@Column(name = "BenCallID")
	private Long benCallID = 0L;
	
	@Expose
	@Column(name = "BeneficiaryRegID")
	private Long beneficiaryRegID;
	@Expose
	@Column(name = "BenMedHistoryID")
	private Long benMedHistoryID = 0L;
	@Expose
	@Column(name = "travel_14days")
	private Boolean travelStatus;
	@Expose
	@Column(name = "travel_type")
	private String travelType;
	@Expose
	@Column(name = "domestic_mode")
	private String modeOfTravelDomestic;
	@Expose
	@Column(name = "domStateID_from")
	private Integer fromStateDom;
	@Expose
	@Column(name = "domDistrictID_from")
	private Integer fromDistrictDom;
	@Expose
	@Column(name = "domTalukID_from")
	private Integer fromSubDistrictDom;
	@Expose
	@Column(name = "domStateID_to")
	private Integer toStateDom;
	@Expose
	@Column(name = "domDistrictID_to")
	private Integer toDistrictDom;
	@Expose
	@Column(name = "domTalukID_to")
	private Integer toSubDistrictDom;
	@Expose
	@Column(name = "international_mode")
	private String modeOfTravelInter;
	@Expose
	@Column(name = "interCountryID_from")
	private Integer fromCountryInter;
	@Expose
	@Column(name = "interCityID_from")
	private Integer fromCityInter;
	@Expose
	@Column(name = "interCountryID_to")
	private Integer toCountryInter;
	@Expose
	@Column(name = "interCityID_to")
	private Integer toCityInter;
	@Expose
	@Column(name = "symptoms")
	private String symptoms_db;
	@Expose
	@Column(name = "COVID19_contact_history")
	private String cOVID19_contact_history;
	@Expose
	@Column(name = "medical_consultation")
	private Boolean medical_consultation;
	@Expose
	@Column(name = "Suspected_COVID19")
	private Boolean suspectedStatus;

	@Expose
	@Transient
	private String suspectedStatusUI;

	@Expose
	@Column(name = "recommendation")
	private String recommendation_db;
	@Expose
	@Column(name = "Deleted", insertable = false, updatable = false)
	private Boolean deleted;
	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private String processed;
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
	@Column(name = "LastModDate")
	private Timestamp lastModDate;
	@Expose
	@Column(name = "VisitCode")
	private Long visitCode;
	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;
	@Expose
	@Column(name = "vanid")
	private Integer vanID;
	@Expose
	@Column(name = "VanSerialNo")
	private Long vanSerialNo;

	@Expose
	@Transient
	private String[] symptom;

	@Expose
	@Transient
	private String[] contactStatus;

	@Expose
	@Transient
	private String[] travelList;

	@Expose
	@Transient
	private ArrayList<String[]> recommendation;

	public Long getcOVID19ID() {
		return cOVID19ID;
	}

	public void setcOVID19ID(Long cOVID19ID) {
		this.cOVID19ID = cOVID19ID;
	}

	public Long getBenCallID() {
		return benCallID;
	}

	public void setBenCallID(Long benCallID) {
		this.benCallID = benCallID;
	}

	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	public Long getBenMedHistoryID() {
		return benMedHistoryID;
	}

	public void setBenMedHistoryID(Long benMedHistoryID) {
		this.benMedHistoryID = benMedHistoryID;
	}

	public Boolean getTravelStatus() {
		return travelStatus;
	}

	public void setTravelStatus(Boolean travelStatus) {
		this.travelStatus = travelStatus;
	}

	public String getTravelType() {
		return travelType;
	}

	public void setTravelType(String travelType) {
		this.travelType = travelType;
	}

	public String getModeOfTravelDomestic() {
		return modeOfTravelDomestic;
	}

	public void setModeOfTravelDomestic(String modeOfTravelDomestic) {
		this.modeOfTravelDomestic = modeOfTravelDomestic;
	}

	public Integer getFromStateDom() {
		return fromStateDom;
	}

	public void setFromStateDom(Integer fromStateDom) {
		this.fromStateDom = fromStateDom;
	}

	public Integer getFromDistrictDom() {
		return fromDistrictDom;
	}

	public void setFromDistrictDom(Integer fromDistrictDom) {
		this.fromDistrictDom = fromDistrictDom;
	}

	public Integer getFromSubDistrictDom() {
		return fromSubDistrictDom;
	}

	public void setFromSubDistrictDom(Integer fromSubDistrictDom) {
		this.fromSubDistrictDom = fromSubDistrictDom;
	}

	public Integer getToStateDom() {
		return toStateDom;
	}

	public void setToStateDom(Integer toStateDom) {
		this.toStateDom = toStateDom;
	}

	public Integer getToDistrictDom() {
		return toDistrictDom;
	}

	public void setToDistrictDom(Integer toDistrictDom) {
		this.toDistrictDom = toDistrictDom;
	}

	public Integer getToSubDistrictDom() {
		return toSubDistrictDom;
	}

	public void setToSubDistrictDom(Integer toSubDistrictDom) {
		this.toSubDistrictDom = toSubDistrictDom;
	}

	public String getModeOfTravelInter() {
		return modeOfTravelInter;
	}

	public void setModeOfTravelInter(String modeOfTravelInter) {
		this.modeOfTravelInter = modeOfTravelInter;
	}

	public Integer getFromCountryInter() {
		return fromCountryInter;
	}

	public void setFromCountryInter(Integer fromCountryInter) {
		this.fromCountryInter = fromCountryInter;
	}

	public Integer getFromcityInter() {
		return fromCityInter;
	}

	public void setFromcityInter(Integer fromCityInter) {
		this.fromCityInter = fromCityInter;
	}

	public Integer getToCountryInter() {
		return toCountryInter;
	}

	public void setToCountryInter(Integer toCountryInter) {
		this.toCountryInter = toCountryInter;
	}

	public Integer getTocityInter() {
		return toCityInter;
	}

	public void setTocityInter(Integer toCityInter) {
		this.toCityInter = toCityInter;
	}

	public String getSymptoms_db() {
		return symptoms_db;
	}

	public void setSymptoms_db(String symptoms_db) {
		this.symptoms_db = symptoms_db;
	}

	public String getcOVID19_contact_history() {
		return cOVID19_contact_history;
	}

	public void setcOVID19_contact_history(String cOVID19_contact_history) {
		this.cOVID19_contact_history = cOVID19_contact_history;
	}

	public Boolean getMedical_consultation() {
		return medical_consultation;
	}

	public void setMedical_consultation(Boolean medical_consultation) {
		this.medical_consultation = medical_consultation;
	}

	public Boolean getSuspectedStatus() {
		return suspectedStatus;
	}

	public void setSuspectedStatus(Boolean suspectedStatus) {
		this.suspectedStatus = suspectedStatus;
	}

	public String getRecommendation_db() {
		return recommendation_db;
	}

	public void setRecommendation_db(String recommendation_db) {
		this.recommendation_db = recommendation_db;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getProcessed() {
		return processed;
	}

	public void setProcessed(String processed) {
		this.processed = processed;
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

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public Long getVanSerialNo() {
		return vanSerialNo;
	}

	public void setVanSerialNo(Long vanSerialNo) {
		this.vanSerialNo = vanSerialNo;
	}

	public String[] getSymptoms() {
		return symptom;
	}

	public void setSymptoms(String[] symptoms) {
		this.symptom = symptoms;
	}

	public String[] getContactStatus() {
		return contactStatus;
	}

	public void setContactStatus(String[] contactStatus) {
		this.contactStatus = contactStatus;
	}

	public String[] getTravelList() {
		return travelList;
	}

	public void setTravelList(String[] travelList) {
		this.travelList = travelList;
	}

	public Integer getFromCityInter() {
		return fromCityInter;
	}

	public void setFromCityInter(Integer fromCityInter) {
		this.fromCityInter = fromCityInter;
	}

	public Integer getToCityInter() {
		return toCityInter;
	}

	public void setToCityInter(Integer toCityInter) {
		this.toCityInter = toCityInter;
	}

	public String[] getSymptom() {
		return symptom;
	}

	public void setSymptom(String[] symptom) {
		this.symptom = symptom;
	}

	public ArrayList<String[]> getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(ArrayList<String[]> recommendation) {
		this.recommendation = recommendation;
	}

	public String getSuspectedStatusUI() {
		return suspectedStatusUI;
	}

	public void setSuspectedStatusUI(String suspectedStatusUI) {
		this.suspectedStatusUI = suspectedStatusUI;
	}

}
