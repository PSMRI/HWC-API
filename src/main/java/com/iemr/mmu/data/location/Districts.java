/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.location;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import com.iemr.mmu.annotation.sqlInjectionSafe.SQLInjectionSafe;
import com.iemr.mmu.repo.location.ZoneDistrictMapping;

@Entity
@Table(name = "m_district")
public class Districts {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DistrictID")
	@Expose
	private Integer districtID;
	@Column(name = "StateID")
	
	@Expose
	private Integer stateID;
	@Column(name = "DistrictName")
	@Expose
	private String districtName;
	// @Column(name = "Zone")
	// @Expose
	// private String zone;
	@Column(name = "Deleted", insertable = false, updatable = true)
	@Expose
	private Boolean deleted;
	@Column(name = "CreatedBy")
	@Expose
	private String createdBy;
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(unique = true, insertable = false, name = "StateID", updatable = false)
	@JsonIgnore
	private States states;

	@Expose
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DistrictID", insertable = false, updatable = false)
	private ZoneDistrictMapping zoneDistrictMapping;

	public Districts() {
	}

	public Integer getStateID() {
		return stateID;
	}

	public void setStateID(Integer stateID) {
		this.stateID = stateID;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public ZoneDistrictMapping getZoneDistrictMapping() {
		return zoneDistrictMapping;
	}

	public void setZoneDistrictMapping(ZoneDistrictMapping zoneDistrictMapping) {
		this.zoneDistrictMapping = zoneDistrictMapping;
	}

	public void setDistrictID(Integer districtID) {
		this.districtID = districtID;
	}

	public Districts(Integer districtID, Integer stateID, String districtName, Boolean deleted, String createdBy,
			Timestamp createdDate, String modifiedBy, Timestamp lastModDate, States states,
			ZoneDistrictMapping zoneDistrictMapping) {
		super();
		this.districtID = districtID;
		this.stateID = stateID;
		this.districtName = districtName;
		this.deleted = deleted;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
		this.states = states;
		this.zoneDistrictMapping = zoneDistrictMapping;
	}

	public Districts(Integer DistrictID, String DistrictName) {
		this.districtID = DistrictID;
		this.districtName = DistrictName;
	}

	public Districts(Integer DistrictID, String DistrictName, Integer stateId, String stateName) {
		this.states = new States(stateId.intValue(), stateName);
		this.districtID = DistrictID;
		this.districtName = DistrictName;
	}

	public int getDistrictID() {
		return this.districtID.intValue();
	}

	public void setDistrictID(int districtID) {
		this.districtID = Integer.valueOf(districtID);
	}

	public String getDistrictName() {
		return this.districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	// public String getZone() {
	// return this.zone;
	// }
	//
	// public void setZone(String zone) {
	// this.zone = zone;
	// }

	public boolean isDeleted() {
		return this.deleted.booleanValue();
	}

	public void setDeleted(boolean deleted) {
		this.deleted = Boolean.valueOf(deleted);
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getLastModDate() {
		return this.lastModDate;
	}

	public void setLastModDate(Timestamp lastModDate) {
		this.lastModDate = lastModDate;
	}

	public States getStates() {
		return this.states;
	}

	public void setStates(States states) {
		this.states = states;
	}

}
