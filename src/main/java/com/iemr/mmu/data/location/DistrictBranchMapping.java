package com.iemr.mmu.data.location;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.iemr.mmu.utils.mapper.OutputMapper;

@Entity
@Table(name = "m_DistrictBranchMapping")
public class DistrictBranchMapping {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DistrictBranchID")
	@Expose
	private Integer districtBranchID;
	// @OneToMany(fetch = FetchType.LAZY, mappedBy = "m_districtbranchmapping")
	// @Transient
	// private Set<I_BenDemographics> I_bendemographics;
	@Column(name = "BlockID")
	@Expose
	private Integer blockID;
	@Column(name = "PanchayatName")
	@Expose
	private String panchayatName;
	@Column(name = "VillageName")
	@Expose
	private String villageName;
	@Column(name = "Habitat")
	@Expose
	private String habitat;
	@Column(name = "PinCode")
	@Expose
	private String pinCode;
	@Column(name = "Deleted", insertable = false, updatable = true)
	@Expose
	private Boolean deleted;
	@Column(name = "CreatedBy")
	@Expose
	private String createdBy;
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	@Transient
	private OutputMapper outputMapper = new OutputMapper();

	public DistrictBranchMapping() {
	}

	public DistrictBranchMapping(Integer DistrictBranchID, String VillageName) {
		this.districtBranchID = DistrictBranchID;
		this.villageName = VillageName;
	}

	public static String getVillageList(ArrayList<Object[]> resList) {
		DistrictBranchMapping villOBJ = null;
		ArrayList<DistrictBranchMapping> villList = new ArrayList<>();
		for (Object[] obj : resList) {
			villOBJ = new DistrictBranchMapping((Integer) obj[0], (String) obj[1]);
			villList.add(villOBJ);
		}
		return new Gson().toJson(villList);

	}

	public DistrictBranchMapping(Integer DistrictBranchID, String VillageName, String PanchayatName, String Habitat,
			String PinCode) {
		this.districtBranchID = DistrictBranchID;
		this.villageName = VillageName;
		this.panchayatName = PanchayatName;
		this.habitat = Habitat;
		this.pinCode = PinCode;
	}

	public Integer getDistrictBranchID() {
		return this.districtBranchID;
	}

	public void setDistrictBranchID(int districtBranchID) {
		this.districtBranchID = Integer.valueOf(districtBranchID);
	}

	public Integer getBlockID() {
		return this.blockID;
	}

	public void setBlockID(int blockID) {
		this.blockID = Integer.valueOf(blockID);
	}

	public String getPanchayatName() {
		return this.panchayatName;
	}

	public void setPanchayatName(String panchayatName) {
		this.panchayatName = panchayatName;
	}

	public String getVillageName() {
		return this.villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public String getHabitat() {
		return this.habitat;
	}

	public void setHabitat(String habitat) {
		this.habitat = habitat;
	}

	public String getPinCode() {
		return this.pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public Boolean isDeleted() {
		return this.deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = Boolean.valueOf(deleted);
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getLastModDate() {
		return this.lastModDate;
	}

	public void setLastModDate(Timestamp lastModDate) {
		this.lastModDate = lastModDate;
	}

	@Override
	public String toString() {
		return outputMapper.gson().toJson(this);
	}
}
