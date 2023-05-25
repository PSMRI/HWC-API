package com.iemr.mmu.data.doctor;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_drugduration")
public class DrugDurationUnitMaster {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "DrugDurationID")
	private Integer drugDurationID;
	@Expose
	@Column(name = "DrugDuration")
	private String drugDuration;
	@Expose
	@Column(name = "DrugDurationDesc")
	private String drugDurationDesc;
	@Expose
	@Column(name = "Deleted", insertable = false, updatable = false)
	private Boolean deleted;
	@Expose
	@Column(name = "Processed", insertable = false, updatable = false)
	private Character processed;
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

	public DrugDurationUnitMaster() {
	}

	public DrugDurationUnitMaster(Integer drugDurationID, String drugDuration, String drugDurationDesc, Boolean deleted,
			Character processed, String createdBy, Timestamp createdDate, String modifiedBy, Timestamp lastModDate) {
		super();
		this.drugDurationID = drugDurationID;
		this.drugDuration = drugDuration;
		this.drugDurationDesc = drugDurationDesc;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
	}
	
	public DrugDurationUnitMaster(Integer drugDurationID, String drugDuration) {
		super();
		this.drugDurationID = drugDurationID;
		this.drugDuration = drugDuration;
	}
	
	public static ArrayList<DrugDurationUnitMaster> getDrugDurationUnitMaster(ArrayList<Object[]> resList) {
		ArrayList<DrugDurationUnitMaster> resArray = new ArrayList<DrugDurationUnitMaster>();
		for (Object[] obj : resList) {
			DrugDurationUnitMaster cOBJ = new DrugDurationUnitMaster((Integer)obj[0], (String)obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}

	public Integer getDrugDurationID() {
		return drugDurationID;
	}

	public void setDrugDurationID(Integer drugDurationID) {
		this.drugDurationID = drugDurationID;
	}

	public String getDrugDuration() {
		return drugDuration;
	}

	public void setDrugDuration(String drugDuration) {
		this.drugDuration = drugDuration;
	}

	public String getDrugDurationDesc() {
		return drugDurationDesc;
	}

	public void setDrugDurationDesc(String drugDurationDesc) {
		this.drugDurationDesc = drugDurationDesc;
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
