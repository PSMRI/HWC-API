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
package com.iemr.hwc.data.tele_consultation;

import java.util.List;

import com.iemr.hwc.data.quickConsultation.PrescribedDrugDetail;

public class SmsRequestOBJ {
	private Long beneficiaryRegID;
	private Integer smsTemplateID;
	private Integer specializationID;
	private String smsTypeTM;
	private String createdBy;
	private String tcDate;
	private String tcPreviousDate;
    private List<PrescribedDrugDetail> presObj;
	private List<Object> diagnosis;

	public List<PrescribedDrugDetail> getPresObj() {
		return presObj;
	}

	public void setPresObj(List<PrescribedDrugDetail> presObj) {
		this.presObj = presObj;
	}

	public List<Object> getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(List<Object> diagnosis) {
		this.diagnosis = diagnosis;
	}

	public List<PrescribedDrugDetail> getObj() {
		return presObj;
	}

	public void setObj(List<PrescribedDrugDetail> obj) {
		this.presObj = obj;
	}

	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	public Integer getSmsTemplateID() {
		return smsTemplateID;
	}

	public void setSmsTemplateID(Integer smsTemplateID) {
		this.smsTemplateID = smsTemplateID;
	}

	public Integer getSpecializationID() {
		return specializationID;
	}

	public void setSpecializationID(Integer specializationID) {
		this.specializationID = specializationID;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getTcDate() {
		return tcDate;
	}

	public void setTcDate(String tcDate) {
		this.tcDate = tcDate;
	}

	public String getTcPreviousDate() {
		return tcPreviousDate;
	}

	public void setTcPreviousDate(String tcPreviousDate) {
		this.tcPreviousDate = tcPreviousDate;
	}

	public String getSmsType() {
		return smsTypeTM;
	}

	public void setSmsType(String smsType) {
		this.smsTypeTM = smsType;
	}

}
