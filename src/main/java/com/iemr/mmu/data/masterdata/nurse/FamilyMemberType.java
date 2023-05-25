package com.iemr.mmu.data.masterdata.nurse;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_benrelationshiptype")
public class FamilyMemberType {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "BenRelationshipID")
	private Short benRelationshipID;
	
	@Expose
	@Column(name = "BenRelationshipType")
	private String benRelationshipType;
	
	@Expose
	@Column(name = "Gender")
	private String gender;
	
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;

	public FamilyMemberType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FamilyMemberType(Short benRelationshipID, String benRelationshipType, String gender) {
		super();
		this.benRelationshipID = benRelationshipID;
		this.benRelationshipType = benRelationshipType;
		this.gender = gender;
	}
	
	public static ArrayList<FamilyMemberType> getFamilyMemberTypeMasterData(ArrayList<Object[]> resList) {
		ArrayList<FamilyMemberType> resArray = new ArrayList<>();
		for (Object[] obj : resList) {
			FamilyMemberType cOBJ = new FamilyMemberType((Short) obj[0], (String) obj[1], (String) obj[2]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
}
