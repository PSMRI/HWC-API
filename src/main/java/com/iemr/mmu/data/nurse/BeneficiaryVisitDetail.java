/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.nurse;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.annotation.sqlInjectionSafe.SQLInjectionSafe;
import com.iemr.mmu.data.provider.ProviderServiceMapping;

@Entity
@Table(name = "t_benvisitdetail")
public class BeneficiaryVisitDetail {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "BenVisitID")
	private Long benVisitID;

	@Expose
	@Column(name = "BeneficiaryRegID")
	private Long beneficiaryRegID;

	@Expose
	@Column(name = "VisitCode")
	private Long visitCode;
	// @Expose
	// @OneToOne(fetch = FetchType.LAZY)
	// @JoinColumn(updatable = false, insertable = false, name = "BeneficiaryRegID")
	// private BeneficiaryData beneficiaryData;

	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;

	//@Expose
	@Transient
	private @SQLInjectionSafe String serviceProviderName;

	//@Expose
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false, insertable = false, name = "providerServiceMapID")
	private ProviderServiceMapping providerServiceMapping;

	@Expose
	@Column(name = "VisitDateTime")
	private Timestamp visitDateTime;

	@Expose
	@Column(name = "VisitNo")
	private Short visitNo;

	@Expose
	@Column(name = "VisitReasonID")
	private Short visitReasonID;

	@Expose
	@Column(name = "VisitReason")
	private @SQLInjectionSafe String visitReason;

	@Expose
	@Column(name = "VisitCategoryID")
	private Integer visitCategoryID;

	@Expose
	@Column(name = "VisitCategory")
	private @SQLInjectionSafe String visitCategory;

	@Expose
	@Column(name = "PregnancyStatus")
	private @SQLInjectionSafe String pregnancyStatus;

	@Expose
	@Column(name = "RCHID")
	private String rCHID;

	@Expose
	@Column(name = "HealthFacilityType")
	private @SQLInjectionSafe String healthFacilityType;

	@Expose
	@Column(name = "HealthFacilityLocation")
	private @SQLInjectionSafe String healthFacilityLocation;

	@Expose
	@Column(name = "ReportFilePath")
	private @SQLInjectionSafe String reportFilePath;

	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;
	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private @SQLInjectionSafe String processed;
	@Expose
	@Column(name = "CreatedBy")
	private @SQLInjectionSafe String createdBy;
	@Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;
	@Expose
	@Column(name = "ModifiedBy")
	private @SQLInjectionSafe String modifiedBy;
	@Expose
	@Column(name = "LastModDate", insertable = false)
	private Timestamp lastModDate;

	@Expose
	@Column(name = "VisitFlowStatusFlag", insertable = false)
	private @SQLInjectionSafe String visitFlowStatusFlag;

	@Expose
	@Column(name = "VanSerialNo")
	private Long vanSerialNo;

	@Expose
	@Column(name = "VehicalNo")
	private @SQLInjectionSafe String vehicalNo;

	@Expose
	@Column(name = "VanID")
	private Integer vanID;

	@Expose
	@Column(name = "ParkingPlaceID")
	private Integer parkingPlaceID;

	@Expose
	@Column(name = "SyncedBy")
	private @SQLInjectionSafe String syncedBy;

	@Expose
	@Column(name = "SyncedDate")
	private Timestamp syncedDate;

	@Expose
	@Column(name = "ReservedForChange")
	private @SQLInjectionSafe String reservedForChange;

	@Expose
	@Transient
	private Integer[] fileIDs;

	@Expose
	@Column(name = "FP_Sideeffects")
	private String fpSideeffects;
	@Expose
	@Column(name = "FP_Sideeffects_Other")
	private String otherSideEffects;

	@Expose
	@Column(name = "FPMethod_Followup")
	private String fpMethodFollowup;
	@Expose
	@Column(name = "FPMethod_Followup_Other")
	private String otherFollowUpForFpMethod;
	
	@Expose
	@Column(name = "SubVisitCategory")
	private String subVisitCategory;

	@Expose
	@Transient
	private String[] followUpForFpMethod;
	@Expose
	@Transient
	private String[] sideEffects;
	

	public BeneficiaryVisitDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BeneficiaryVisitDetail(Long benVisitID, Long beneficiaryRegID, Integer providerServiceMapID,
			Timestamp visitDateTime, Short visitNo, Short visitReasonID, String visitReason, Integer visitCategoryID,
			String visitCategory, String pregnancyStatus, String rCHID, String healthFacilityType,
			String healthFacilityLocation, String reportFilePath, Boolean deleted, String processed, String createdBy,
			Timestamp createdDate, String modifiedBy, Timestamp lastModDate, String visitFlowStatusFlag) {
		super();
		this.benVisitID = benVisitID;
		this.beneficiaryRegID = beneficiaryRegID;
		// this.beneficiaryData = beneficiaryData;
		this.providerServiceMapID = providerServiceMapID;
		this.visitDateTime = visitDateTime;
		this.visitNo = visitNo;
		this.visitReasonID = visitReasonID;
		this.visitReason = visitReason;
		this.visitCategoryID = visitCategoryID;
		this.visitCategory = visitCategory;
		this.pregnancyStatus = pregnancyStatus;
		this.rCHID = rCHID;
		this.healthFacilityType = healthFacilityType;
		this.healthFacilityLocation = healthFacilityLocation;
		this.reportFilePath = reportFilePath;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
		this.visitFlowStatusFlag = visitFlowStatusFlag;
	}

	public BeneficiaryVisitDetail(Long benVisitID, Long beneficiaryRegID, Long visitCode, Integer providerServiceMapID,
			Timestamp visitDateTime, Short visitNo, Short visitReasonID, String visitReason, Integer visitCategoryID,
			String visitCategory, String pregnancyStatus, String rCHID, String healthFacilityType,
			String healthFacilityLocation, String reportFilePath, Boolean deleted, String processed, String createdBy,
			Timestamp createdDate, String modifiedBy, Timestamp lastModDate) {
		super();
		this.benVisitID = benVisitID;
		this.beneficiaryRegID = beneficiaryRegID;
		this.visitCode = visitCode;
		this.providerServiceMapID = providerServiceMapID;
		this.visitDateTime = visitDateTime;
		this.visitNo = visitNo;
		this.visitReasonID = visitReasonID;
		this.visitReason = visitReason;
		this.visitCategoryID = visitCategoryID;
		this.visitCategory = visitCategory;
		this.pregnancyStatus = pregnancyStatus;
		this.rCHID = rCHID;
		this.healthFacilityType = healthFacilityType;
		this.healthFacilityLocation = healthFacilityLocation;
		this.reportFilePath = reportFilePath;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
	}

	public BeneficiaryVisitDetail(Long benVisitID, Long beneficiaryRegID, Integer providerServiceMapID,
			Timestamp visitDateTime, Short visitNo, Short visitReasonID, String visitReason, Integer visitCategoryID,
			String visitCategory, String pregnancyStatus, String rCHID, String healthFacilityType,
			String healthFacilityLocation, String reportFilePath, String serviceProviderName) {
		super();
		this.benVisitID = benVisitID;
		this.beneficiaryRegID = beneficiaryRegID;
		this.providerServiceMapID = providerServiceMapID;
		this.visitDateTime = visitDateTime;
		this.visitNo = visitNo;
		this.visitReasonID = visitReasonID;
		this.visitReason = visitReason;
		this.visitCategoryID = visitCategoryID;
		this.visitCategory = visitCategory;
		this.pregnancyStatus = pregnancyStatus;
		this.rCHID = rCHID;
		this.healthFacilityType = healthFacilityType;
		this.healthFacilityLocation = healthFacilityLocation;
		this.reportFilePath = reportFilePath;
		this.serviceProviderName = serviceProviderName;
	}

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

	public String getServiceProviderName() {
		return serviceProviderName;
	}

	public void setServiceProviderName(String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
	}

	public Long getBenVisitID() {
		return benVisitID;
	}

	public void setBenVisitID(Long benVisitID) {
		this.benVisitID = benVisitID;
	}

	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	// public BeneficiaryData getBeneficiaryData() {
	// return beneficiaryData;
	// }
	//
	// public void setBeneficiaryData(BeneficiaryData beneficiaryData) {
	// this.beneficiaryData = beneficiaryData;
	// }

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}

	public Timestamp getVisitDateTime() {
		return visitDateTime;
	}

	public void setVisitDateTime(Timestamp visitDateTime) {
		this.visitDateTime = visitDateTime;
	}

	public Short getVisitNo() {
		return visitNo;
	}

	public void setVisitNo(Short visitNo) {
		this.visitNo = visitNo;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public Short getVisitReasonID() {
		return visitReasonID;
	}

	public void setVisitReasonID(Short visitReasonID) {
		this.visitReasonID = visitReasonID;
	}

	public String getVisitReason() {
		return visitReason;
	}

	public void setVisitReason(String visitReason) {
		this.visitReason = visitReason;
	}

	public Integer getVisitCategoryID() {
		return visitCategoryID;
	}

	public void setVisitCategoryID(Integer visitCategoryID) {
		this.visitCategoryID = visitCategoryID;
	}

	public String getVisitCategory() {
		return visitCategory;
	}

	public void setVisitCategory(String visitCategory) {
		this.visitCategory = visitCategory;
	}

	public String getPregnancyStatus() {
		return pregnancyStatus;
	}

	public void setPregnancyStatus(String pregnancyStatus) {
		this.pregnancyStatus = pregnancyStatus;
	}

	public String getrCHID() {
		return rCHID;
	}

	public void setrCHID(String rCHID) {
		this.rCHID = rCHID;
	}

	public String getHealthFacilityType() {
		return healthFacilityType;
	}

	public void setHealthFacilityType(String healthFacilityType) {
		this.healthFacilityType = healthFacilityType;
	}

	public String getHealthFacilityLocation() {
		return healthFacilityLocation;
	}

	public void setHealthFacilityLocation(String healthFacilityLocation) {
		this.healthFacilityLocation = healthFacilityLocation;
	}

	public String getReportFilePath() {
		return reportFilePath;
	}

	public void setReportFilePath(String reportFilePath) {
		this.reportFilePath = reportFilePath;
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

	public String getVisitFlowStatusFlag() {
		return visitFlowStatusFlag;
	}

	public void setVisitFlowStatusFlag(String visitFlowStatusFlag) {
		this.visitFlowStatusFlag = visitFlowStatusFlag;
	}

	public ProviderServiceMapping getProviderServiceMapping() {
		return providerServiceMapping;
	}

	public void setProviderServiceMapping(ProviderServiceMapping providerServiceMapping) {
		this.providerServiceMapping = providerServiceMapping;
	}

	public Long getVanSerialNo() {
		return vanSerialNo;
	}

	public void setVanSerialNo(Long vanSerialNo) {
		this.vanSerialNo = vanSerialNo;
	}

	public String getVehicalNo() {
		return vehicalNo;
	}

	public void setVehicalNo(String vehicalNo) {
		this.vehicalNo = vehicalNo;
	}

	public Integer getParkingPlaceID() {
		return parkingPlaceID;
	}

	public void setParkingPlaceID(Integer parkingPlaceID) {
		this.parkingPlaceID = parkingPlaceID;
	}

	public String getSyncedBy() {
		return syncedBy;
	}

	public void setSyncedBy(String syncedBy) {
		this.syncedBy = syncedBy;
	}

	public Timestamp getSyncedDate() {
		return syncedDate;
	}

	public void setSyncedDate(Timestamp syncedDate) {
		this.syncedDate = syncedDate;
	}

	public String getReservedForChange() {
		return reservedForChange;
	}

	public void setReservedForChange(String reservedForChange) {
		this.reservedForChange = reservedForChange;
	}

	public Integer[] getFileIDs() {
		return fileIDs;
	}

	public void setFileIDs(Integer[] fileIDs) {
		this.fileIDs = fileIDs;
	}

	public String getFpSideeffects() {
		return fpSideeffects;
	}

	public void setFpSideeffects(String fpSideeffects) {
		this.fpSideeffects = fpSideeffects;
	}

	public String getFpMethodFollowup() {
		return fpMethodFollowup;
	}

	public void setFpMethodFollowup(String fpMethodFollowup) {
		this.fpMethodFollowup = fpMethodFollowup;
	}

	public String[] getFollowUpForFpMethod() {
		return followUpForFpMethod;
	}

	public void setFollowUpForFpMethod(String[] followUpForFpMethod) {
		this.followUpForFpMethod = followUpForFpMethod;
	}

	public String[] getSideEffects() {
		return sideEffects;
	}

	public void setSideEffects(String[] sideEffects) {
		this.sideEffects = sideEffects;
	}

	public String getOtherSideEffects() {
		return otherSideEffects;
	}

	public void setOtherSideEffects(String otherSideEffects) {
		this.otherSideEffects = otherSideEffects;
	}

	public String getOtherFollowUpForFpMethod() {
		return otherFollowUpForFpMethod;
	}

	public void setOtherFollowUpForFpMethod(String otherFollowUpForFpMethod) {
		this.otherFollowUpForFpMethod = otherFollowUpForFpMethod;
	}

	public String getSubVisitCategory() {
		return subVisitCategory;
	}

	public void setSubVisitCategory(String subVisitCategory) {
		this.subVisitCategory = subVisitCategory;
	}

	
	


}
