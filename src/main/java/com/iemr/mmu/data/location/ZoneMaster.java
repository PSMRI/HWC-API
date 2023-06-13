/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.location;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_zone")
public class ZoneMaster {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "ZoneID")
	private Integer zoneID;
	@Expose
	@Column(name = "ZoneName")
	private String zoneName;
	@Expose
	@Column(name = "ZoneDesc")
	private String zoneDesc;
	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;

	@Expose
	@Column(name = "CountryID")
	private Integer countryID;
	@Expose
	@Column(name = "StateID")
	private Integer stateID;
	@Expose
	@Column(name = "DistrictID")
	private Integer districtID;
	@Expose
	@Column(name = "DistrictBlockID")
	private Integer districtBlockID;

	@Expose
	@Column(name = "DistrictBranchID")
	private Integer districtBranchID;
	@Expose
	@Column(name = "ZoneHQAddress")
	private String zoneHQAddress;
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;
	@Expose
	@Column(name = "Processed")
	private String processed;

	@Expose
	@Column(name = "CreatedBy")
	private String createdBy;
	@Expose
	@Column(name = "CreatedDate")
	private Timestamp createdDate;
	@Expose
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	@Expose
	@Column(name = "LastModDate")
	private Timestamp lastModDate;

	public ZoneMaster() {
	}

	public ZoneMaster(Integer zoneID, String zoneName) {
		this.zoneID = zoneID;
		this.zoneName = zoneName;
	}

	public ZoneMaster(Integer zoneID, String zoneName, String zoneDesc, Integer providerServiceMapID, Integer countryID,
			Integer stateID, Integer districtID, Integer districtBlockID, Integer districtBranchID,
			String zoneHQAddress, Boolean deleted, String processed, String createdBy, Timestamp createdDate,
			String modifiedBy, Timestamp lastModDate) {
		super();
		this.zoneID = zoneID;
		this.zoneName = zoneName;
		this.zoneDesc = zoneDesc;
		this.providerServiceMapID = providerServiceMapID;
		this.countryID = countryID;
		this.stateID = stateID;
		this.districtID = districtID;
		this.districtBlockID = districtBlockID;
		this.districtBranchID = districtBranchID;
		this.zoneHQAddress = zoneHQAddress;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
	}

	public Integer getZoneID() {
		return zoneID;
	}

	public void setZoneID(Integer zoneID) {
		this.zoneID = zoneID;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getZoneDesc() {
		return zoneDesc;
	}

	public void setZoneDesc(String zoneDesc) {
		this.zoneDesc = zoneDesc;
	}

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}

	public Integer getCountryID() {
		return countryID;
	}

	public void setCountryID(Integer countryID) {
		this.countryID = countryID;
	}

	public Integer getStateID() {
		return stateID;
	}

	public void setStateID(Integer stateID) {
		this.stateID = stateID;
	}

	public Integer getDistrictID() {
		return districtID;
	}

	public void setDistrictID(Integer districtID) {
		this.districtID = districtID;
	}

	public Integer getDistrictBlockID() {
		return districtBlockID;
	}

	public void setDistrictBlockID(Integer districtBlockID) {
		this.districtBlockID = districtBlockID;
	}

	public Integer getDistrictBranchID() {
		return districtBranchID;
	}

	public void setDistrictBranchID(Integer districtBranchID) {
		this.districtBranchID = districtBranchID;
	}

	public String getZoneHQAddress() {
		return zoneHQAddress;
	}

	public void setZoneHQAddress(String zoneHQAddress) {
		this.zoneHQAddress = zoneHQAddress;
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

}
