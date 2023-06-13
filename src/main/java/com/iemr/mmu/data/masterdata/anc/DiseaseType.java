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
@Table(name = "m_diseasetype")
public class DiseaseType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "DiseaseTypeID")
	private Short diseaseTypeID;

	@Expose
	@Column(name = "DiseaseType")
	private String diseaseType;

	@Expose
	@Column(name = "DiseaseTypeDesc")
	private String diseaseTypeDesc;

	@Expose
	@Column(name = "Gender")
	private String gender;

	@Expose
	@Column(name = "Sctcode")
	private String snomedCode;

	@Expose
	@Column(name = "SctTerm")
	private String snomedTerm;

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

	public DiseaseType(Short diseaseTypeID, String diseaseType, String snomedCode, String snomedTerm) {
		super();
		this.diseaseTypeID = diseaseTypeID;
		this.diseaseType = diseaseType;
		this.snomedCode = snomedCode;
		this.snomedTerm = snomedTerm;
	}

	public static ArrayList<DiseaseType> getDiseaseTypes(ArrayList<Object[]> resList) {
		ArrayList<DiseaseType> resArray = new ArrayList<DiseaseType>();
		for (Object[] obj : resList) {
			DiseaseType cOBJ = new DiseaseType((Short) obj[0], (String) obj[1], (String) obj[2], (String) obj[3]);
			resArray.add(cOBJ);
		}
		return resArray;
	}

}
