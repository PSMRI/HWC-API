/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.masterdata.doctor;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.utils.mapper.OutputMapper;

@Entity
@Table(name = "m_itemcategory")
public class M_ItemCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name="ItemCategoryID")
	private Integer itemCategoryID;
	
	@Expose
	@Column(name="AlertBeforeDays")
	private Integer alertBeforeDays;
	
	@Expose
	@Column(name="IssueType")
	private String issueType;
	
	@Expose
	@Column(name="ItemCategoryName")
	private String itemCategoryName;
	
	@Expose
	@Column(name="ItemCategoryDesc")
	private String itemCategoryDesc;
	
	@Expose
	@Column(name="ItemCategoryCode")
	private String itemCategoryCode;
	
	@Expose
	@Column(name="Status")
	private String status;
	
	@Expose
	@Column(name="ProviderServiceMapID")
	private Integer providerServiceMapID;
	
	@Expose
	@Column(name="Deleted",insertable = false, updatable = false)
	private Boolean deleted;
	
	@Expose
	@Column(name="Processed",insertable = false, updatable = false)
	private Character processed;
	
	@Expose
	@Column(name="CreatedBy")
	private String createdBy;
	
	@Expose
	@Column(name="CreatedDate",insertable = false, updatable = false)
	private Date createdDate;
	
	@Expose
	@Column(name="ModifiedBy")
	private String modifiedBy;
	
	@Expose
	@Column(name="LastModDate",insertable = false, updatable = false)
	private Date lastModDate;
	
//	@Transient
//	private OutputMapper outputMapper = new OutputMapper();
//
//	@Override
//	public String toString() {
//		return outputMapper.gson().toJson(this);
//	}

	public Integer getItemCategoryID() {
		return itemCategoryID;
	}

	public void setItemCategoryID(Integer itemCategoryID) {
		this.itemCategoryID = itemCategoryID;
	}

	public Integer getAlertBeforeDays() {
		return alertBeforeDays;
	}

	public void setAlertBeforeDays(Integer alertBeforeDays) {
		this.alertBeforeDays = alertBeforeDays;
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public String getItemCategoryName() {
		return itemCategoryName;
	}

	public void setItemCategoryName(String itemCategoryName) {
		this.itemCategoryName = itemCategoryName;
	}

	public String getItemCategoryDesc() {
		return itemCategoryDesc;
	}

	public void setItemCategoryDesc(String itemCategoryDesc) {
		this.itemCategoryDesc = itemCategoryDesc;
	}

	public String getItemCategoryCode() {
		return itemCategoryCode;
	}

	public void setItemCategoryCode(String itemCategoryCode) {
		this.itemCategoryCode = itemCategoryCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModDate() {
		return lastModDate;
	}

	public void setLastModDate(Date lastModDate) {
		this.lastModDate = lastModDate;
	}

//	public OutputMapper getOutputMapper() {
//		return outputMapper;
//	}
//
//	public void setOutputMapper(OutputMapper outputMapper) {
//		this.outputMapper = outputMapper;
//	}
}

