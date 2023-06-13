/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.masterdata.registrar;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_beneficiaryincomestatus")
public class IncomeStatusMaster {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "IncomeStatusID")
	private Short incomeStatusID;
	@Expose
	@Column(name = "IncomeStatus")
	private String incomeStatus;
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;

	public IncomeStatusMaster() {
	}

	public IncomeStatusMaster(Short incomeStatusID, String incomeStatus) {
		this.incomeStatusID = incomeStatusID;
		this.incomeStatus = incomeStatus;
	}

	public static ArrayList<IncomeStatusMaster> getIncomeStatusMasterData(ArrayList<Object[]> resList) {
		ArrayList<IncomeStatusMaster> resArray = new ArrayList<>();
		for (Object[] obj : resList) {
			IncomeStatusMaster cOBJ = new IncomeStatusMaster((Short) obj[0], (String) obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
}
