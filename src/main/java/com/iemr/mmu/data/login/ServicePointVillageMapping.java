/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.login;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.data.location.DistrictBranchMapping;

@Entity
@Table(name = "m_servicepointvillagemap")
public class ServicePointVillageMapping {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "ServicePointVillageMapID")
	private Integer servicePointVillageMapID;

	@Expose
	@Column(name = "ServicePointID")
	private Integer servicePointID;

	@Expose
	@Column(name = "DistrictBranchID")
	private Integer districtBranchID;
	@Expose
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false, insertable = false, name = "districtBranchID")
	private DistrictBranchMapping districtBranchMapping;
	@Expose
	@Transient
	String villageName;

	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;
	// @Expose
	// @OneToOne(fetch = FetchType.LAZY)
	// @JoinColumn(updatable = false, insertable = false, name =
	// "ProviderServiceMapID")
	// private M_ProviderServiceMapping m_providerServiceMapping;

	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;
	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private String processed;
	@Expose
	@Column(name = "CreatedBy")
	private String createdBy;
	@Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Date createdDate;
	@Expose
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	@Expose
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Date lastModDate;

	public ServicePointVillageMapping() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ServicePointVillageMapping(Integer districtBranchID, String villageName) {
		super();
		this.districtBranchID = districtBranchID;
		this.villageName = villageName;
	}

	public Integer getServicePointID() {
		return servicePointID;
	}

	public void setServicePointID(Integer servicePointID) {
		this.servicePointID = servicePointID;
	}

	public Integer getDistrictBranchID() {
		return districtBranchID;
	}

	public void setDistrictBranchID(Integer districtBranchID) {
		this.districtBranchID = districtBranchID;
	}

	public DistrictBranchMapping getDistrictBranchMapping() {
		return districtBranchMapping;
	}

	public void setDistrictBranchMapping(DistrictBranchMapping districtBranchMapping) {
		this.districtBranchMapping = districtBranchMapping;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
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

	public Integer getServicePointVillageMapID() {
		return servicePointVillageMapID;
	}

}
