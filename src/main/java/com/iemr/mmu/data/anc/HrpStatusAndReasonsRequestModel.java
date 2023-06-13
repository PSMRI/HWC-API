/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.anc;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class HrpStatusAndReasonsRequestModel {

	private Long beneficiaryRegID;
	private Integer benificiaryAge;
	private Float beneficiaryHeight;
	private String[] comorbidConditions;
	private Boolean malPresentation;
	private Boolean lowLyingPlacenta;
	private Boolean vertebralDeformity;
	private Float hemoglobin;
	private String bloodGroupType;
	private String[] pastIllness;
	private ArrayList<PastObstetricHostoryHRP> pastObstetric;

	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	public Integer getBenificiaryAge() {
		return benificiaryAge;
	}

	public void setBenificiaryAge(Integer benificiaryAge) {
		this.benificiaryAge = benificiaryAge;
	}

	public Float getBeneficiaryHeight() {
		return beneficiaryHeight;
	}

	public void setBeneficiaryHeight(Float beneficiaryHeight) {
		this.beneficiaryHeight = beneficiaryHeight;
	}

	public String[] getComorbidConditions() {
		return comorbidConditions;
	}

	public void setComorbidConditions(String[] comorbidConditions) {
		this.comorbidConditions = comorbidConditions;
	}

	public Boolean getMalPresentation() {
		return malPresentation;
	}

	public void setMalPresentation(Boolean malPresentation) {
		this.malPresentation = malPresentation;
	}

	public Boolean getLowLyingPlacenta() {
		return lowLyingPlacenta;
	}

	public void setLowLyingPlacenta(Boolean lowLyingPlacenta) {
		this.lowLyingPlacenta = lowLyingPlacenta;
	}

	public Boolean getVertebralDeformity() {
		return vertebralDeformity;
	}

	public void setVertebralDeformity(Boolean vertebralDeformity) {
		this.vertebralDeformity = vertebralDeformity;
	}

	public Float getHemoglobin() {
		return hemoglobin;
	}

	public void setHemoglobin(Float hemoglobin) {
		this.hemoglobin = hemoglobin;
	}

	public String getBloodGroupType() {
		return bloodGroupType;
	}

	public void setBloodGroupType(String bloodGroupType) {
		this.bloodGroupType = bloodGroupType;
	}

	public String[] getPastIllness() {
		return pastIllness;
	}

	public void setPastIllness(String[] pastIllness) {
		this.pastIllness = pastIllness;
	}

	public ArrayList<PastObstetricHostoryHRP> getPastObstetric() {
		return pastObstetric;
	}

	public void setPastObstetric(ArrayList<PastObstetricHostoryHRP> pastObstetric) {
		this.pastObstetric = pastObstetric;
	}

}
