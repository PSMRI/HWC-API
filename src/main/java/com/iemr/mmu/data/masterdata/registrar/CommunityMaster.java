package com.iemr.mmu.data.masterdata.registrar;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_community")
public class CommunityMaster {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "CommunityID")
	private Short communityID;
	@Expose
	@Column(name = "CommunityType")
	private String communityType;
	@Expose
	@Column(name = "CommunityDesc")
	private String communityDesc;
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;

	public CommunityMaster() {
	}

	public CommunityMaster(Short communityID, String communityType) {
		this.communityID = communityID;
		this.communityType = communityType;
	}

	public static ArrayList<CommunityMaster> getCommunityMasterData(ArrayList<Object[]> resList) {
		ArrayList<CommunityMaster> resArray = new ArrayList<>();
		for (Object[] obj : resList) {
			CommunityMaster cOBJ = new CommunityMaster((Short) obj[0], (String) obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}

}
