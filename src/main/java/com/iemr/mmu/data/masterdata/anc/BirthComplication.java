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
@Table(name = "m_birthcomplication")
public class BirthComplication {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "BirthComplicationID")
	private Short birthComplicationID;
	
	@Expose
	@Column(name = "Name")
	private String name;
	
	@Expose
	@Column(name = "BirthComplicationDesc")
	private String birthComplicationDesc;
	
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

	public BirthComplication() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BirthComplication(Short birthComplicationID, String name, String birthComplicationDesc) {
		super();
		this.birthComplicationID = birthComplicationID;
		this.name = name;
		this.birthComplicationDesc = birthComplicationDesc;
	}
	
	public static ArrayList<BirthComplication> getBirthComplicationTypes(ArrayList<Object[]> resList) {
		ArrayList<BirthComplication> resArray = new ArrayList<BirthComplication>();
		for (Object[] obj : resList) {
			BirthComplication cOBJ = new BirthComplication((Short)obj[0], (String)obj[1], (String)obj[2]);
			resArray.add(cOBJ);
		}
		return resArray;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthComplicationDesc() {
		return birthComplicationDesc;
	}

	public void setBirthComplicationDesc(String birthComplicationDesc) {
		this.birthComplicationDesc = birthComplicationDesc;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getProcessed() {
		return processed;
	}

	public void setProcessed(String processed) {
		this.processed = processed;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getLastModDate() {
		return lastModDate;
	}

	public void setLastModDate(Timestamp lastModDate) {
		this.lastModDate = lastModDate;
	}

	public Short getBirthComplicationID() {
		return birthComplicationID;
	}
	
	
}
