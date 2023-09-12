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
package com.iemr.hwc.data.quickConsultation;

public class WrapperNurseDataVitals {
	private Long beneficiaryRegID;
	private Long benVisitID;
	private Integer providerServiceMapID;
	private Double weight_Kg;
	private Double height_cm;
	private Double waistCircumference_cm;
	private Double hipCircumference_cm;
	private Double bMI;
	private Double waistHipRatio;
	private Double temperature;
	private Short pulseRate;
	private Short systolicBP_1stReading;
	private Short diastolicBP_1stReading;
	private Short bloodGlucose_Fasting;
	private Short bloodGlucose_Random;
	private Short bloodGlucose_2hr_PP;
	private Short respiratoryRate;
	private String createdBy;

	public WrapperNurseDataVitals() {
	}

	public WrapperNurseDataVitals(Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID,
			Double weight_Kg, Double height_cm, Double waistCircumference_cm, Double hipCircumference_cm, Double bMI,
			Double waistHipRatio, Double temperature, Short pulseRate, Short systolicBP_1stReading,
			Short diastolicBP_1stReading, Short bloodGlucose_Fasting, Short bloodGlucose_Random,
			Short bloodGlucose_2hr_PP, Short respiratoryRate, String createdBy) {
		super();
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.weight_Kg = weight_Kg;
		this.height_cm = height_cm;
		this.waistCircumference_cm = waistCircumference_cm;
		this.hipCircumference_cm = hipCircumference_cm;
		this.bMI = bMI;
		this.waistHipRatio = waistHipRatio;
		this.temperature = temperature;
		this.pulseRate = pulseRate;
		this.systolicBP_1stReading = systolicBP_1stReading;
		this.diastolicBP_1stReading = diastolicBP_1stReading;
		this.bloodGlucose_Fasting = bloodGlucose_Fasting;
		this.bloodGlucose_Random = bloodGlucose_Random;
		this.bloodGlucose_2hr_PP = bloodGlucose_2hr_PP;
		this.respiratoryRate = respiratoryRate;
		this.createdBy = createdBy;
	}

	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	public Long getBenVisitID() {
		return benVisitID;
	}

	public void setBenVisitID(Long benVisitID) {
		this.benVisitID = benVisitID;
	}

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}

	public Double getWeight_Kg() {
		return weight_Kg;
	}

	public void setWeight_Kg(Double weight_Kg) {
		this.weight_Kg = weight_Kg;
	}

	public Double getHeight_cm() {
		return height_cm;
	}

	public void setHeight_cm(Double height_cm) {
		this.height_cm = height_cm;
	}

	public Double getWaistCircumference_cm() {
		return waistCircumference_cm;
	}

	public void setWaistCircumference_cm(Double waistCircumference_cm) {
		this.waistCircumference_cm = waistCircumference_cm;
	}

	public Double getHipCircumference_cm() {
		return hipCircumference_cm;
	}

	public void setHipCircumference_cm(Double hipCircumference_cm) {
		this.hipCircumference_cm = hipCircumference_cm;
	}

	public Double getbMI() {
		return bMI;
	}

	public void setbMI(Double bMI) {
		this.bMI = bMI;
	}

	public Double getWaistHipRatio() {
		return waistHipRatio;
	}

	public void setWaistHipRatio(Double waistHipRatio) {
		this.waistHipRatio = waistHipRatio;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Short getPulseRate() {
		return pulseRate;
	}

	public void setPulseRate(Short pulseRate) {
		this.pulseRate = pulseRate;
	}

	public Short getSystolicBP_1stReading() {
		return systolicBP_1stReading;
	}

	public void setSystolicBP_1stReading(Short systolicBP_1stReading) {
		this.systolicBP_1stReading = systolicBP_1stReading;
	}

	public Short getDiastolicBP_1stReading() {
		return diastolicBP_1stReading;
	}

	public void setDiastolicBP_1stReading(Short diastolicBP_1stReading) {
		this.diastolicBP_1stReading = diastolicBP_1stReading;
	}

	public Short getBloodGlucose_Fasting() {
		return bloodGlucose_Fasting;
	}

	public void setBloodGlucose_Fasting(Short bloodGlucose_Fasting) {
		this.bloodGlucose_Fasting = bloodGlucose_Fasting;
	}

	public Short getBloodGlucose_Random() {
		return bloodGlucose_Random;
	}

	public void setBloodGlucose_Random(Short bloodGlucose_Random) {
		this.bloodGlucose_Random = bloodGlucose_Random;
	}

	public Short getBloodGlucose_2hr_PP() {
		return bloodGlucose_2hr_PP;
	}

	public void setBloodGlucose_2hr_PP(Short bloodGlucose_2hr_PP) {
		this.bloodGlucose_2hr_PP = bloodGlucose_2hr_PP;
	}

	public Short getRespiratoryRate() {
		return respiratoryRate;
	}

	public void setRespiratoryRate(Short respiratoryRate) {
		this.respiratoryRate = respiratoryRate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}
