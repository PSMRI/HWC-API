package com.iemr.mmu.data.masterdata.anc;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_bloodgroup")
public class BloodGroups {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "BloodGroupID")
	private Integer bloodGroupID;

	@Expose
	@Column(name = "BloodGroup")
	private String bloodGroup;

	// @Expose
	@Column(name = "BloodGroupDesc")
	private String bloodGroupDesc;

	// @Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

	// @Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private String processed;

	// @Expose
	@Column(name = "CreatedBy")
	private String createdBy;

	// @Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;

	// @Expose
	@Column(name = "ModifiedBy")
	private String modifiedBy;

	// @Expose
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	public BloodGroups() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BloodGroups(Integer bloodGroupID, String bloodGroup, String bloodGroupDesc) {
		super();
		this.bloodGroupID = bloodGroupID;
		this.bloodGroup = bloodGroup;
		this.bloodGroupDesc = bloodGroupDesc;
	}

	public static ArrayList<BloodGroups> getBloodGroups(ArrayList<Object[]> resList) {
		ArrayList<BloodGroups> resArray = new ArrayList<BloodGroups>();
		for (Object[] obj : resList) {
			BloodGroups cOBJ = new BloodGroups((Integer) obj[0], (String) obj[1], (String) obj[2]);
			resArray.add(cOBJ);
		}
		return resArray;
	}

}
