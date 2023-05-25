package com.iemr.mmu.data.quickBlox;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_quickblox")
	
public class Quickblox {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "QuickbloxID")
	private Integer quickbloxID;
	@Expose
	@Column(name = "specialistUserID")
	private Integer specialistUserID;
	@Expose
	@Column(name = "specialistQuickbloxID")
	private Long specialistQuickbloxID;
	@Expose
	@Column(name = "specialistBenQuickbloxID")
	private Long specialistBenQuickbloxID;
	@Expose
	@Column(name = "specialistBenQuickBloxPass")
	private String specialistBenQuickBloxPass;
	@Expose
	@Column(name = "specialistQuickbloxpass")
	private String specialistQuickbloxpass;
	
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;
	@Expose
	@Column(name = "CreatedBy")
	private String createdBy;
	@Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp CreatedDate;
	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private String processed;
	@Expose
	@Column(name = "ModifiedBy")
	private String ModifiedBy;
	public Integer getQuickbloxID() {
		return quickbloxID;
	}
	public void setQuickbloxID(Integer quickbloxID) {
		this.quickbloxID = quickbloxID;
	}
	public Integer getSpecialistUserID() {
		return specialistUserID;
	}
	public void setSpecialistUserID(Integer specialistUserID) {
		this.specialistUserID = specialistUserID;
	}
	public Long getSpecialistQuickbloxID() {
		return specialistQuickbloxID;
	}
	public void setSpecialistQuickbloxID(Long specialistQuickbloxID) {
		this.specialistQuickbloxID = specialistQuickbloxID;
	}
	public Long getSpecialistBenQuickbloxID() {
		return specialistBenQuickbloxID;
	}
	public void setSpecialistBenQuickbloxID(Long specialistBenQuickbloxID) {
		this.specialistBenQuickbloxID = specialistBenQuickbloxID;
	}
	public String getSpecialistBenQuickBloxPass() {
		return specialistBenQuickBloxPass;
	}
	public void setSpecialistBenQuickBloxPass(String specialistBenQuickBloxPass) {
		this.specialistBenQuickBloxPass = specialistBenQuickBloxPass;
	}
	public String getSpecialistQuickbloxpass() {
		return specialistQuickbloxpass;
	}
	public void setSpecialistQuickbloxpass(String specialistQuickbloxpass) {
		this.specialistQuickbloxpass = specialistQuickbloxpass;
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
		return CreatedDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		CreatedDate = createdDate;
	}
	public String getProcessed() {
		return processed;
	}
	public void setProcessed(String processed) {
		this.processed = processed;
	}
	public String getModifiedBy() {
		return ModifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		ModifiedBy = modifiedBy;
	}
	public Timestamp getLastModDate() {
		return LastModDate;
	}
	public void setLastModDate(Timestamp lastModDate) {
		LastModDate = lastModDate;
	}
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp LastModDate;

}
