package com.iemr.mmu.data.doctor;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "temp_masterdrug")
public class TempMasterDrug {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "DrugID")
	private Integer drugID;

	@Expose
	@Column(name = "DrugName")
	private String drugName;

	@Expose
	@Column(name = "DrugDisplayName")
	private String drugDisplayName;

	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;

	public TempMasterDrug() {
	}

	public TempMasterDrug(Integer drugID, String drugDisplayName) {
		super();
		this.drugID = drugID;
		this.drugDisplayName = drugDisplayName;
	}

	public static ArrayList<TempMasterDrug> getTempDrugMasterList(ArrayList<TempMasterDrug> tempMasterDrugList) {
		ArrayList<TempMasterDrug> tempMasterDrugListOBJ = new ArrayList<>();
		for (TempMasterDrug drugMaster : tempMasterDrugList) {
			TempMasterDrug obj = new TempMasterDrug(drugMaster.getDrugID(), drugMaster.getDrugDisplayName());
			tempMasterDrugListOBJ.add(obj);
		}
		return tempMasterDrugListOBJ;
	}

	public TempMasterDrug(Integer drugID, String drugName, String drugDisplayName, Boolean deleted) {
		super();
		this.drugID = drugID;
		this.drugName = drugName;
		this.drugDisplayName = drugDisplayName;
		this.deleted = deleted;
	}

	public Integer getDrugID() {
		return drugID;
	}

	public void setDrugID(Integer drugID) {
		this.drugID = drugID;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getDrugDisplayName() {
		return drugDisplayName;
	}

	public void setDrugDisplayName(String drugDisplayName) {
		this.drugDisplayName = drugDisplayName;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

}
