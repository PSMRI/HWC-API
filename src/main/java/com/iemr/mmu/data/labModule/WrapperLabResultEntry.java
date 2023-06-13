/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.labModule;

import java.util.List;

public class WrapperLabResultEntry {

	private Long beneficiaryRegID;
	private Long visitID;
	private Long visitCode;
	private Integer providerServiceMapID;
	private String createdBy;
	private List<LabResultEntry> labTestResults;
	private List<LabResultEntry> radiologyTestResults;
	private Boolean labCompleted;
	private Long benFlowID;

	private Short nurseFlag;
	private Short doctorFlag;

	private Integer vanID;
	private Integer parkingPlaceID;

	private Short specialist_flag;

	public Short getSpecialist_flag() {
		return specialist_flag;
	}

	public void setSpecialist_flag(Short specialist_flag) {
		this.specialist_flag = specialist_flag;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public Integer getParkingPlaceID() {
		return parkingPlaceID;
	}

	public void setParkingPlaceID(Integer parkingPlaceID) {
		this.parkingPlaceID = parkingPlaceID;
	}

	public Short getNurseFlag() {
		return nurseFlag;
	}

	public void setNurseFlag(Short nurseFlag) {
		this.nurseFlag = nurseFlag;
	}

	public Short getDoctorFlag() {
		return doctorFlag;
	}

	public void setDoctorFlag(Short doctorFlag) {
		this.doctorFlag = doctorFlag;
	}

	public Long getBenFlowID() {
		return benFlowID;
	}

	public void setBenFlowID(Long benFlowID) {
		this.benFlowID = benFlowID;
	}

	public Boolean getLabCompleted() {
		return labCompleted;
	}

	public void setLabCompleted(Boolean labCompleted) {
		this.labCompleted = labCompleted;
	}

	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	public Long getVisitID() {
		return visitID;
	}

	public void setVisitID(Long visitID) {
		this.visitID = visitID;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public List<LabResultEntry> getLabTestResults() {
		return labTestResults;
	}

	public void setLabTestResults(List<LabResultEntry> labTestResults) {
		this.labTestResults = labTestResults;
	}

	public List<LabResultEntry> getRadiologyTestResults() {
		return radiologyTestResults;
	}

	public void setRadiologyTestResults(List<LabResultEntry> radiologyTestResults) {
		this.radiologyTestResults = radiologyTestResults;
	}

}
