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
@Table(name = "m_religion")
public class ReligionMaster {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "ReligionID")
	private Short religionID;
	@Expose
	@Column(name = "ReligionType")
	private String religionType;
	@Expose
	@Column(name = "ReligionDesc")
	private String religionDesc;
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;

	public ReligionMaster() {
	}

	public ReligionMaster(Short religionID, String religionType) {
		this.religionID = religionID;
		this.religionType = religionType;
	}

	public static ArrayList<ReligionMaster> getReligionMasterData(ArrayList<Object[]> resList) {
		ArrayList<ReligionMaster> resArray = new ArrayList<>();
		for (Object[] obj : resList) {
			ReligionMaster cOBJ = new ReligionMaster((Short) obj[0], (String) obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
}
