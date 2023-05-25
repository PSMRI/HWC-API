package com.iemr.mmu.data.masterdata.registrar;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_govtidentitytype")
public class GovIdEntityType {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "GovtIdentityTypeID")
	private Short govtIdentityTypeID;
	@Expose
	@Column(name = "IdentityType")
	private String identityType;
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;
	@Expose
	@Column(name = "IsGovtID")
	private Boolean isGovtID;

	public GovIdEntityType() {
	}

	public GovIdEntityType(Short govtIdentityTypeID, String identityType) {
		this.govtIdentityTypeID = govtIdentityTypeID;
		this.identityType = identityType;
	}

	public static ArrayList<GovIdEntityType> getGovIdEntityTypeData(ArrayList<Object[]> resList) {
		ArrayList<GovIdEntityType> resArray = new ArrayList<>();
		for (Object[] obj : resList) {
			GovIdEntityType cOBJ = new GovIdEntityType((Short) obj[0], (String) obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
}
