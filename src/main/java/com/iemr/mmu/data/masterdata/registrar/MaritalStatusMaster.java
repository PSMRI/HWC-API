package com.iemr.mmu.data.masterdata.registrar;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_maritalstatus")
public class MaritalStatusMaster {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "MaritalStatusID")
	private Short maritalStatusID;
	@Expose
	@Column(name = "Status")
	private String status;
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;

	public MaritalStatusMaster() {
	}

	public MaritalStatusMaster(Short maritalStatusID, String status) {
		this.maritalStatusID = maritalStatusID;
		this.status = status;
	}

	public static ArrayList<MaritalStatusMaster> getMaritalStatusMasterData(ArrayList<Object[]> resList) {
		ArrayList<MaritalStatusMaster> resArray = new ArrayList<>();
		for (Object[] obj : resList) {
			MaritalStatusMaster cOBJ = new MaritalStatusMaster((Short) obj[0], (String) obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
}
