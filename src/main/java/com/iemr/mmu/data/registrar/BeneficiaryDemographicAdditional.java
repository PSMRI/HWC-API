/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.registrar;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "i_bendemoadditional")
public class BeneficiaryDemographicAdditional {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "BenDemoAdditionalID")
	private Long benDemoAdditionalID;
	@Expose
	@Column(name = "BeneficiaryRegID")
	private Long beneficiaryRegID;
	@Expose
	@Column(name = "LiteracyStatus")
	private String literacyStatus;
	@Expose
	@Column(name = "MarrigeDate")
	private Timestamp marrigeDate;
	@Expose
	@Column(name = "MotherName")
	private String motherName;
	@Expose
	@Column(name = "EmailID")
	private String emailID;
	@Expose
	@Column(name = "BankName")
	private String bankName;
	@Expose
	@Column(name = "BranchName")
	private String branchName;
	@Expose
	@Column(name = "IFSCCode")
	private String iFSCCode;
	@Expose
	@Column(name = "AccountNo")
	private String accountNo;
	@Expose
	@Column(name = "Deleted", insertable = false)
	private Boolean deleted;
	@Expose
	@Column(name = "Processed", insertable = false)
	private Character processed;
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
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	public BeneficiaryDemographicAdditional() {
	}

	public BeneficiaryDemographicAdditional(Long benDemoAdditionalID, Long beneficiaryRegID, String literacyStatus,
			Timestamp marrigeDate, String motherName, String emailID, String bankName, String branchName,
			String iFSCCode, String accountNo, Boolean deleted, Character processed, String createdBy,
			Timestamp createdDate, String modifiedBy, Timestamp lastModDate) {
		super();
		this.benDemoAdditionalID = benDemoAdditionalID;
		this.beneficiaryRegID = beneficiaryRegID;
		this.literacyStatus = literacyStatus;
		this.marrigeDate = marrigeDate;
		this.motherName = motherName;
		this.emailID = emailID;
		this.bankName = bankName;
		this.branchName = branchName;
		this.iFSCCode = iFSCCode;
		this.accountNo = accountNo;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
	}

	public Long getBenDemoAdditionalID() {
		return benDemoAdditionalID;
	}

	public void setBenDemoAdditionalID(Long benDemoAdditionalID) {
		this.benDemoAdditionalID = benDemoAdditionalID;
	}

	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	public String getLiteracyStatus() {
		return literacyStatus;
	}

	public void setLiteracyStatus(String literacyStatus) {
		this.literacyStatus = literacyStatus;
	}

	public Timestamp getMarrigeDate() {
		return marrigeDate;
	}

	public void setMarrigeDate(Timestamp marrigeDate) {
		this.marrigeDate = marrigeDate;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getiFSCCode() {
		return iFSCCode;
	}

	public void setiFSCCode(String iFSCCode) {
		this.iFSCCode = iFSCCode;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Character getProcessed() {
		return processed;
	}

	public void setProcessed(Character processed) {
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

}
