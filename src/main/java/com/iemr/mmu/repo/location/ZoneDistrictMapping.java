package com.iemr.mmu.repo.location;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.data.location.Districts;

@Entity
@Table(name = "m_zonedistrictmap")
public class ZoneDistrictMapping {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "ZoneDistrictMapID")
	private Integer zoneDistrictMapID;
	@Expose
	@Column(name = "ZoneID")
	private Integer zoneID;
	@Expose
	@Column(name = "DistrictID")
	private Integer districtID;
	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;
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

	@Expose
	@OneToMany(mappedBy = "zoneDistrictMapping", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Districts> districtsSet;

	public ZoneDistrictMapping() {
	}

	public ZoneDistrictMapping(Integer zoneDistrictMapID, Integer zoneID, Integer districtID,
			Integer providerServiceMapID, Boolean deleted, String processed, String createdBy, Timestamp createdDate,
			String modifiedBy, Timestamp lastModDate) {
		super();
		this.zoneDistrictMapID = zoneDistrictMapID;
		this.zoneID = zoneID;
		this.districtID = districtID;
		this.providerServiceMapID = providerServiceMapID;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
	}

	public ZoneDistrictMapping(Integer zoneDistrictMapID, Integer zoneID, Integer districtID,
			Integer providerServiceMapID, Boolean deleted, String processed, String createdBy, Timestamp createdDate,
			String modifiedBy, Timestamp lastModDate, Set<Districts> districtsSet) {
		super();
		this.zoneDistrictMapID = zoneDistrictMapID;
		this.zoneID = zoneID;
		this.districtID = districtID;
		this.providerServiceMapID = providerServiceMapID;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
		this.districtsSet = districtsSet;
	}

	public Set<Districts> getDistrictsSet() {
		return districtsSet;
	}

	public void setDistrictsSet(Set<Districts> districtsSet) {
		this.districtsSet = districtsSet;
	}

	public Integer getZoneDistrictMapID() {
		return zoneDistrictMapID;
	}

	public void setZoneDistrictMapID(Integer zoneDistrictMapID) {
		this.zoneDistrictMapID = zoneDistrictMapID;
	}

	public Integer getZoneID() {
		return zoneID;
	}

	public void setZoneID(Integer zoneID) {
		this.zoneID = zoneID;
	}

	public Integer getDistrictID() {
		return districtID;
	}

	public void setDistrictID(Integer districtID) {
		this.districtID = districtID;
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

}
