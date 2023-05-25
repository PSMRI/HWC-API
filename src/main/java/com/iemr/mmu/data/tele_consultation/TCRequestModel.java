package com.iemr.mmu.data.tele_consultation;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.annotation.sqlInjectionSafe.SQLInjectionSafe;

@Entity
@Table(name = "t_tmrequest")
public class TCRequestModel {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "TMRequestID")
	private Long tMRequestID;
	
	@Expose
	@Column(name = "BeneficiaryRegID")
	private Long beneficiaryRegID;
	@Expose
	@Column(name = "BenVisitID")
	private Long benVisitID;
	@Expose
	@Column(name = "VisitCode")
	private Long visitCode;
	@Expose
	@Column(name = "UserID")
	private Integer userID;
	@Expose
	@Column(name = "SpecializationID")
	private Integer specializationID;
	@Expose
	@Column(name = "RequestDate")
	private Timestamp requestDate;
	@Expose
	@Column(name = "Duration_minute")
	private Long duration_minute;
	@Expose
	@Column(name = "Status", insertable = false)
	private @SQLInjectionSafe String status;
	@Expose
	@Column(name = "ConsultationStats")
	private @SQLInjectionSafe String consultationStats;
	@Expose
	@Column(name = "BeneficiaryArrivalTime")
	private Timestamp beneficiaryArrivalTime;
	@Expose
	@Column(name = "Consultation_FirstStart")
	private Timestamp consultation_FirstStart;
	@Expose
	@Column(name = "Consultation_LastEnd")
	private Timestamp consultation_LastEnd;
	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;
	@Expose
	@Column(name = "VanID")
	private Integer vanID;
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
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;
	@Expose
	@Column(name = "IsReferredByDoctor")
	private Boolean isReferredByDoctor;

	public Long gettMRequestID() {
		return tMRequestID;
	}

	public void settMRequestID(Long tMRequestID) {
		this.tMRequestID = tMRequestID;
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

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Timestamp getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Timestamp requestDate) {
		this.requestDate = requestDate;
	}

	public Long getDuration_minute() {
		return duration_minute;
	}

	public void setDuration_minute(Long duration_minute) {
		this.duration_minute = duration_minute;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getConsultationStats() {
		return consultationStats;
	}

	public void setConsultationStats(String consultationStats) {
		this.consultationStats = consultationStats;
	}

	public Timestamp getBeneficiaryArrivalTime() {
		return beneficiaryArrivalTime;
	}

	public void setBeneficiaryArrivalTime(Timestamp beneficiaryArrivalTime) {
		this.beneficiaryArrivalTime = beneficiaryArrivalTime;
	}

	public Timestamp getConsultation_FirstStart() {
		return consultation_FirstStart;
	}

	public void setConsultation_FirstStart(Timestamp consultation_FirstStart) {
		this.consultation_FirstStart = consultation_FirstStart;
	}

	public Timestamp getConsultation_LastEnd() {
		return consultation_LastEnd;
	}

	public void setConsultation_LastEnd(Timestamp consultation_LastEnd) {
		this.consultation_LastEnd = consultation_LastEnd;
	}

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
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

	public Integer getSpecializationID() {
		return specializationID;
	}

	public void setSpecializationID(Integer specializationID) {
		this.specializationID = specializationID;
	}

	public Boolean getIsReferredByDoctor() {
		return isReferredByDoctor;
	}

	public void setIsReferredByDoctor(Boolean isReferredByDoctor) {
		this.isReferredByDoctor = isReferredByDoctor;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

}
