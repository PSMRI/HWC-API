package com.iemr.mmu.data.registrar;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iemr.mmu.annotation.sqlInjectionSafe.SQLInjectionSafe;

@Entity
@Table(name = "i_benphonemap")
public class BeneficiaryPhoneMapping {
	@Id
	@GeneratedValue
	@Column(name = "BenPhMapID")
	private Long benPhMapID;
	@Column(name = "BenificiaryRegID")
	private Long benificiaryRegID;
	@Column(name = "ParentBenRegID")
	private Long parentBenRegID;
	@Column(name = "BenRelationshipID")
	private Short benRelationshipID;
	@SQLInjectionSafe
	@Column(name = "PhoneNo")
	private String phoneNo;
	@Column(name = "PhoneTypeID")
	private Short phoneTypeID;
	@Column(name = "NuisanceCallCount")
	private Short nuisanceCallCount;
	@Column(name = "Deleted")
	private Boolean deleted;
	@Column(name = "CreatedBy")
	private String createdBy;
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "benificiaryRegID", referencedColumnName = "beneficiaryRegID", insertable = false, updatable = false)
	private BeneficiaryData benData;

	public BeneficiaryPhoneMapping() {
	}

	public BeneficiaryPhoneMapping(Long benPhMapID, Long benificiaryRegID, Long parentBenRegID, Short benRelationshipID,
			String phoneNo, Short phoneTypeID, Short nuisanceCallCount, Boolean deleted, String createdBy,
			Timestamp createdDate, String modifiedBy, Timestamp lastModDate, BeneficiaryData benData) {
		super();
		this.benPhMapID = benPhMapID;
		this.benificiaryRegID = benificiaryRegID;
		this.parentBenRegID = parentBenRegID;
		this.benRelationshipID = benRelationshipID;
		this.phoneNo = phoneNo;
		this.phoneTypeID = phoneTypeID;
		this.nuisanceCallCount = nuisanceCallCount;
		this.deleted = deleted;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
		this.benData = benData;
	}

	public Long getBenPhMapID() {
		return benPhMapID;
	}

	public void setBenPhMapID(Long benPhMapID) {
		this.benPhMapID = benPhMapID;
	}

	public Long getBenificiaryRegID() {
		return benificiaryRegID;
	}

	public void setBenificiaryRegID(Long benificiaryRegID) {
		this.benificiaryRegID = benificiaryRegID;
	}

	public Long getParentBenRegID() {
		return parentBenRegID;
	}

	public void setParentBenRegID(Long parentBenRegID) {
		this.parentBenRegID = parentBenRegID;
	}

	public Short getBenRelationshipID() {
		return benRelationshipID;
	}

	public void setBenRelationshipID(Short benRelationshipID) {
		this.benRelationshipID = benRelationshipID;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Short getPhoneTypeID() {
		return phoneTypeID;
	}

	public void setPhoneTypeID(Short phoneTypeID) {
		this.phoneTypeID = phoneTypeID;
	}

	public Short getNuisanceCallCount() {
		return nuisanceCallCount;
	}

	public void setNuisanceCallCount(Short nuisanceCallCount) {
		this.nuisanceCallCount = nuisanceCallCount;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
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

	public BeneficiaryData getBenData() {
		return benData;
	}

	public void setBenData(BeneficiaryData benData) {
		this.benData = benData;
	}

}
