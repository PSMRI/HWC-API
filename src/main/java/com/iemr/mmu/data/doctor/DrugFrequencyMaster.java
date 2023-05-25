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
@Table(name = "m_drugfrequency")
public class DrugFrequencyMaster {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "DrugFrequencyID")
	private Integer drugFrequencyID;
	@Expose
	@Column(name = "Frequency")
	private String frequency;
	@Expose
	@Column(name = "FrequencyDesc")
	private String frequencyDesc;
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

	public DrugFrequencyMaster() {
	}

	public DrugFrequencyMaster(Integer drugFrequencyID, String frequency, String frequencyDesc, Boolean deleted,
			Character processed, String createdBy, Timestamp createdDate, String modifiedBy, Timestamp lastModDate) {
		super();
		this.drugFrequencyID = drugFrequencyID;
		this.frequency = frequency;
		this.frequencyDesc = frequencyDesc;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
	}
	
	public DrugFrequencyMaster(Integer drugFrequencyID, String frequency) {
		super();
		this.drugFrequencyID = drugFrequencyID;
		this.frequency = frequency;
	}
	
	public static ArrayList<DrugFrequencyMaster> getDrugFrequencyMaster(ArrayList<Object[]> resList) {
		ArrayList<DrugFrequencyMaster> resArray = new ArrayList<DrugFrequencyMaster>();
		for (Object[] obj : resList) {
			DrugFrequencyMaster cOBJ = new DrugFrequencyMaster((Integer)obj[0], (String)obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}

	public Integer getDrugFrequencyID() {
		return drugFrequencyID;
	}

	public void setDrugFrequencyID(Integer drugFrequencyID) {
		this.drugFrequencyID = drugFrequencyID;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getFrequencyDesc() {
		return frequencyDesc;
	}

	public void setFrequencyDesc(String frequencyDesc) {
		this.frequencyDesc = frequencyDesc;
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
