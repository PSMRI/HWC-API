/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.fetosense;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.transaction.Transactional;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_vanfetosensedeviceidmapping")
public class FetosenseDeviceID {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "VfdID")
	private Long VfdID;

	@Expose
	@Column(name = "devicename")
	private String deviceName;

	@Expose
	@Column(name = "deviceID")
	private String deviceID;

	@Expose
	@Column(name = "vanID")
	private Integer vanID;	

	@Expose
	@Column(name = "parkingplaceID")
	private Integer parkingPlaceID;
	
	@Expose
	@Column(name = "vanTypeID")
	private Integer vanTypeID;
	
	@Expose
	@Column(name = "vanName")
	private String vanName;

	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;	
	
	@Expose
	@Column(name = "deactivated")
	private Boolean deactivated;	
	
	
	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

	@Expose
	@Column(name = "Processed", insertable = false, updatable = false)
	private String processed;

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
	

	public Long getVfdID() {
		return VfdID;
	}

	public void setVfdID(Long vfdID) {
		VfdID = vfdID;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}

	public Boolean getDeactivated() {
		return deactivated;
	}

	public void setDeactivated(Boolean deactivated) {
		this.deactivated = deactivated;
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

	public Integer getParkingPlaceID() {
		return parkingPlaceID;
	}

	public void setParkingPlaceID(Integer parkingPlaceID) {
		this.parkingPlaceID = parkingPlaceID;
	}

	public Integer getVanTypeID() {
		return vanTypeID;
	}

	public void setVanTypeID(Integer vanTypeID) {
		this.vanTypeID = vanTypeID;
	}

	public String getVanName() {
		return vanName;
	}

	public void setVanName(String vanName) {
		this.vanName = vanName;
	}

	
}
