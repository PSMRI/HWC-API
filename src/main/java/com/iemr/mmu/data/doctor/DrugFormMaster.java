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
@Table(name = "m_drugform")
public class DrugFormMaster {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "DrugFormID")
	private Integer drugFormID;
	@Expose
	@Column(name = "DrugForm")
	private String drugForm;
	@Expose
	@Column(name = "DrugFormDecs")
	private String drugFormDecs;
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

	public DrugFormMaster() {
	}

	public DrugFormMaster(Integer drugFormID, String drugForm, String drugFormDecs, Boolean deleted,
			Character processed, String createdBy, Timestamp createdDate, String modifiedBy, Timestamp lastModDate) {
		super();
		this.drugFormID = drugFormID;
		this.drugForm = drugForm;
		this.drugFormDecs = drugFormDecs;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
	}
	
	public DrugFormMaster(Integer drugFormID, String drugForm) {
		super();
		this.drugFormID = drugFormID;
		this.drugForm = drugForm;
	}
	
	public static ArrayList<DrugFormMaster> getDrugFormMaster(ArrayList<Object[]> resList) {
		ArrayList<DrugFormMaster> resArray = new ArrayList<DrugFormMaster>();
		for (Object[] obj : resList) {
			DrugFormMaster cOBJ = new DrugFormMaster((Integer)obj[0], (String)obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}

	public Integer getDrugFormID() {
		return drugFormID;
	}

	public void setDrugFormID(Integer drugFormID) {
		this.drugFormID = drugFormID;
	}

	public String getDrugForm() {
		return drugForm;
	}

	public void setDrugForm(String drugForm) {
		this.drugForm = drugForm;
	}

	public String getDrugFormDecs() {
		return drugFormDecs;
	}

	public void setDrugFormDecs(String drugFormDecs) {
		this.drugFormDecs = drugFormDecs;
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
