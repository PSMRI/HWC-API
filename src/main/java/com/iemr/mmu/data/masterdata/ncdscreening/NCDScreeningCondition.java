package com.iemr.mmu.data.masterdata.ncdscreening;

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
@Table(name = "m_ncdscreeningcondition")
public class NCDScreeningCondition {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "ncdScreeningConditionID")
	private Integer ncdScreeningConditionID;

	@Expose
	@Column(name = "ncdScreeningCondition")
	private String screeningCondition;

	@Expose
	@Column(name = "ncdScreeningConditionDesc")
	private String ncdScreeningConditionDesc;

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

	public NCDScreeningCondition(Integer ncdScreeningConditionID, String ncdScreeningCondition) {
		super();
		this.ncdScreeningConditionID = ncdScreeningConditionID;
		this.screeningCondition = ncdScreeningCondition;
	}

	public static ArrayList<NCDScreeningCondition> getNCDScreeningCondition(ArrayList<Object[]> resList) {
		ArrayList<NCDScreeningCondition> resArray = new ArrayList<NCDScreeningCondition>();
		for (Object[] obj : resList) {
			NCDScreeningCondition cOBJ = new NCDScreeningCondition((Integer) obj[0], (String) obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}

	public Integer getNcdScreeningConditionID() {
		return ncdScreeningConditionID;
	}

	public void setNcdScreeningConditionID(Integer ncdScreeningConditionID) {
		this.ncdScreeningConditionID = ncdScreeningConditionID;
	}

	public String getNcdScreeningCondition() {
		return screeningCondition;
	}

	public void setNcdScreeningCondition(String ncdScreeningCondition) {
		this.screeningCondition = ncdScreeningCondition;
	}

	public String getNcdScreeningConditionDesc() {
		return ncdScreeningConditionDesc;
	}

	public void setNcdScreeningConditionDesc(String ncdScreeningConditionDesc) {
		this.ncdScreeningConditionDesc = ncdScreeningConditionDesc;
	}

	public Boolean isDeleted() {
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

}
