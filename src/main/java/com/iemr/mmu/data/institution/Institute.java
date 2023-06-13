/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.institution;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_institution")
public class Institute {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "InstitutionID")
	@Expose
	private Integer institutionID;

	@Column(name = "InstitutionName")
	@Expose
	private String institutionName;
	@Column(name = "StateID")
	@Expose
	private Integer stateID;

	@Column(name = "DistrictID")
	@Expose
	private Integer districtID;

	@Column(name = "BlockID")
	@Expose
	private Integer blockID;

	@Column(name = "DistrictBranchMappingID")
	@Expose
	private Integer districtBranchMappingID;

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
	private Timestamp createdDate;

	@Expose
	@Column(name = "ModifiedBy")
	private String modifiedBy;

	@Expose
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;

	public Institute() {
	}

	public Institute(Integer institutionID, String institutionName) {
		this.institutionID = institutionID;
		this.institutionName = institutionName;
	}

	public static ArrayList<Institute> getinstituteDetails(ArrayList<Object[]> objList) {
		ArrayList<Institute> instituteList = new ArrayList<>();
		if (objList != null && objList.size() > 0) {
			for (Object[] obj : objList) {
				Institute institute = new Institute((Integer) obj[0], (String) obj[1]);
				instituteList.add(institute);
			}
		}
		return instituteList;
	}

	public Institute(Integer institutionID, String institutionName, Integer stateID, Integer districtID,
			Integer blockID, Integer districtBranchMappingID, Boolean deleted, String processed, String createdBy,
			Timestamp createdDate, String modifiedBy, Timestamp lastModDate, Integer providerServiceMapID) {
		super();
		this.institutionID = institutionID;
		this.institutionName = institutionName;
		this.stateID = stateID;
		this.districtID = districtID;
		this.blockID = blockID;
		this.districtBranchMappingID = districtBranchMappingID;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
		this.providerServiceMapID = providerServiceMapID;
	}

	public Integer getInstitutionID() {
		return institutionID;
	}

	public void setInstitutionID(Integer institutionID) {
		this.institutionID = institutionID;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
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

	public Integer getBlockID() {
		return blockID;
	}

	public void setBlockID(Integer blockID) {
		this.blockID = blockID;
	}

	public Integer getDistrictBranchMappingID() {
		return districtBranchMappingID;
	}

	public void setDistrictBranchMappingID(Integer districtBranchMappingID) {
		this.districtBranchMappingID = districtBranchMappingID;
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

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}

}
