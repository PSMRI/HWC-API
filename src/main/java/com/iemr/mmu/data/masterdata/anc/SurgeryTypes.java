/* LicenseInfo : Copyright © 2023 Piramal */ 
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
@Table(name = "m_surgery")
public class SurgeryTypes {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "SurgeryID")
	private Integer surgeryID;

	@Expose
	@Column(name = "SurgeryType")
	private String surgeryType;

	@Expose
	@Column(name = "Gender")
	private String gender;

	@Expose
	@Column(name = "Age")
	private String age;

	@Expose
	@Column(name = "VisitCategoryID")
	private Integer visitCategoryID;

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

	public SurgeryTypes(Integer surgeryID, String surgeryType) {
		super();
		this.surgeryID = surgeryID;
		this.surgeryType = surgeryType;
	}

	public static ArrayList<SurgeryTypes> getSurgeryTypes(ArrayList<Object[]> resList) {
		ArrayList<SurgeryTypes> resArray = new ArrayList<SurgeryTypes>();
		for (Object[] obj : resList) {
			SurgeryTypes cOBJ = new SurgeryTypes((Integer) obj[0], (String) obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}

}
