package com.iemr.mmu.data.videoconsultation;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_userjitsimapping")
public class UserJitsi {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "JitsiID")
	private Long jitsiID;

	@Expose
	@Column(name = "UserID")
	private Long userID;
	@Expose
	@Column(name = "JitsiUserName")
	private String jitsiUserName;
	@Expose
	@Column(name = "JitsiPassword")
	private String jitsiPassword;	
	
	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;

	@Expose
	@Column(name = "Vanid")
	private Integer vanID;	
	
	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean Deleted;
	
	@Expose
	@Column(name = "CreatedBy")
	private String createdBy;	
	
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp CreatedDate;
	
	@Expose
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	
	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private String processed;
	
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp LastModDate;

	
	public UserJitsi() {
		
	}
	
	public UserJitsi(String jitsiUserName,String jitsiPassword) {
		this.jitsiUserName = jitsiUserName;
		this.jitsiPassword = jitsiPassword;
	}
	
	public Long getJitsiID() {
		return jitsiID;
	}

	public void setJitsiID(Long jitsiID) {
		this.jitsiID = jitsiID;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public String getJitsiUserName() {
		return jitsiUserName;
	}

	public void setJitsiUserName(String jitsiUserName) {
		this.jitsiUserName = jitsiUserName;
	}

	public String getJitsiPassword() {
		return jitsiPassword;
	}

	public void setJitsiPassword(String jitsiPassword) {
		this.jitsiPassword = jitsiPassword;
	}

	
	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}

	public Integer getVanid() {
		return vanID;
	}

	public void setVanid(Integer vanID) {
		this.vanID = vanID;
	}

	public Boolean getDeleted() {
		return Deleted;
	}

	public void setDeleted(Boolean deleted) {
		Deleted = deleted;
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

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getProcessed() {
		return processed;
	}

	public void setProcessed(String processed) {
		this.processed = processed;
	}

	public Timestamp getLastModDate() {
		return LastModDate;
	}

	public void setLastModDate(Timestamp lastModDate) {
		LastModDate = lastModDate;
	}
	
	
}
