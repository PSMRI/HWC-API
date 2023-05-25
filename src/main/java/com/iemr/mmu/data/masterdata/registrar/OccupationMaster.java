package com.iemr.mmu.data.masterdata.registrar;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_beneficiaryoccupation")
public class OccupationMaster {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "OccupationID")
	private Short occupationID;
	@Expose
	@Column(name = "OccupationType")
	private String occupationType;
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;

	public OccupationMaster() {
	}

	public OccupationMaster(Short occupationID, String occupationType) {
		this.occupationID = occupationID;
		this.occupationType = occupationType;
	}

	public static ArrayList<OccupationMaster> getOccupationMasterData(ArrayList<Object[]> resList) {
		ArrayList<OccupationMaster> resArray = new ArrayList<>();
		for (Object[] obj : resList) {
			OccupationMaster cOBJ = new OccupationMaster((Short) obj[0], (String) obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
}
