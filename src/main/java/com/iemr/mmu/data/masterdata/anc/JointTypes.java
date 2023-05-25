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
@Table(name = "m_jointtype")
public class JointTypes {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "JointID")
	private Short jointID;
	
	@Expose
	@Column(name = "JointType")
	private String jointType;
	
	@Expose
	@Column(name = "JointTypeDesc")
	private String jointTypeDesc;
	
	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private String processed;

	@Expose
	@Column(name = "CreatedBy")
	private String createdBy;

	@Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;

	@Expose
	@Column(name = "ModifiedBy")
	private String modifiedBy;

	@Expose
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	public JointTypes(Short jointID, String jointType, String jointTypeDesc) {
		super();
		this.jointID = jointID;
		this.jointType = jointType;
		this.jointTypeDesc = jointTypeDesc;
	}
	
	public static ArrayList<JointTypes> getJointTypes(ArrayList<Object[]> resList) {
		ArrayList<JointTypes> resArray = new ArrayList<JointTypes>();
		for (Object[] obj : resList) {
			JointTypes cOBJ = new JointTypes((Short)obj[0], (String)obj[1], (String)obj[2]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
	
}
