package com.iemr.mmu.data.tele_consultation;

import java.util.List;

import com.iemr.mmu.data.quickConsultation.PrescribedDrugDetail;

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
