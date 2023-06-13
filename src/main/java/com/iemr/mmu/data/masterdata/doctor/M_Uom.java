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


@Entity
@Table(name="m_uom")
public class M_Uom {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name="UOMID")
	private Integer uOMID;
	@Expose
	@Column(name="UOMName")
	private String uOMName;
	@Expose
	@Column(name="UOMDesc")
	private String uOMDesc; 
	@Expose
	@Column(name="UOMCode")
	private String uOMCode;
	@Expose
	@Column(name="Status")
	private String status;
	@Expose
	@Column(name="ProviderServiceMapID")
	private Integer providerServiceMapID;
	@Expose
	@Column(name="Deleted",insertable = false, updatable = true)
	private Boolean deleted;
	@Expose
	@Column(name="Processed",insertable = false, updatable = true)
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
	
	
	public M_Uom() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the uOMID
	 */
	public Integer getuOMID() {
		return uOMID;
	}


	/**
	 * @param uOMID the uOMID to set
	 */
	public void setuOMID(Integer uOMID) {
		this.uOMID = uOMID;
	}


	/**
	 * @return the uOMName
	 */
	public String getuOMName() {
		return uOMName;
	}


	/**
	 * @param uOMName the uOMName to set
	 */
	public void setuOMName(String uOMName) {
		this.uOMName = uOMName;
	}


	/**
	 * @return the uOMDesc
	 */
	public String getuOMDesc() {
		return uOMDesc;
	}


	/**
	 * @param uOMDesc the uOMDesc to set
	 */
	public void setuOMDesc(String uOMDesc) {
		this.uOMDesc = uOMDesc;
	}


	/**
	 * @return the uOMCode
	 */
	public String getuOMCode() {
		return uOMCode;
	}


	/**
	 * @param uOMCode the uOMCode to set
	 */
	public void setuOMCode(String uOMCode) {
		this.uOMCode = uOMCode;
	}


	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * @return the providerServiceMapID
	 */
	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}


	/**
	 * @param providerServiceMapID the providerServiceMapID to set
	 */
	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}


	/**
	 * @return the deleted
	 */
	public Boolean getDeleted() {
		return deleted;
	}


	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}


	/**
	 * @return the processed
	 */
	public Character getProcessed() {
		return processed;
	}


	/**
	 * @param processed the processed to set
	 */
	public void setProcessed(Character processed) {
		this.processed = processed;
	}


	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}


	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}


	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}


	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	/**
	 * @return the lastModDate
	 */
	public Date getLastModDate() {
		return lastModDate;
	}


	/**
	 * @param lastModDate the lastModDate to set
	 */
	public void setLastModDate(Date lastModDate) {
		this.lastModDate = lastModDate;
	}   
	     
	
	       
	

//	@Transient
//	private OutputMapper outputMapper = new OutputMapper();
//
//	@Override
//	public String toString() {
//		return outputMapper.gson().toJson(this);
//	}
//	
	
	

}

