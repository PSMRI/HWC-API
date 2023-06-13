/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.labModule;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_procedure")
public class ProcedureData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ProcedureID")
	@Expose
	private Integer procedureID;

	@Column(name = "ProcedureName")
	@Expose
	private String procedureName;

	@Column(name = "ProcedureDesc")
	@Expose
	private String procedureDesc;

	@Column(name = "ProcedureType")
	@Expose
	private String procedureType;

	@Column(name = "Gender")
	@Expose
	private String gender;

	@Column(name = "ProviderServiceMapID")
	@Expose
	private Integer providerServiceMapID;

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

	@Expose
	@OneToMany(mappedBy = "procedureData", cascade = CascadeType.ALL)
	private Set<LabResultEntry> labResultEntry;

	public Integer getProcedureID() {
		return procedureID;
	}

	public void setProcedureID(Integer procedureID) {
		this.procedureID = procedureID;
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	public String getProcedureDesc() {
		return procedureDesc;
	}

	public void setProcedureDesc(String procedureDesc) {
		this.procedureDesc = procedureDesc;
	}

	public String getProcedureType() {
		return procedureType;
	}

	public void setProcedureType(String procedureType) {
		this.procedureType = procedureType;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
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

	public Set<LabResultEntry> getLabResultEntry() {
		return labResultEntry;
	}

	public void setLabResultEntry(Set<LabResultEntry> labResultEntry) {
		this.labResultEntry = labResultEntry;
	}

	public ProcedureData() {
	}

	public ProcedureData(Integer procedureID, String procedureName, String procedureDesc, String procedureType,
			String gender, Integer providerServiceMapID) {
		super();
		this.procedureID = procedureID;
		this.procedureName = procedureName;
		this.procedureDesc = procedureDesc;
		this.procedureType = procedureType;
		this.gender = gender;
		this.providerServiceMapID = providerServiceMapID;
	}

	public static ArrayList<ProcedureData> getProcedures(ArrayList<Object[]> resList) {
		ArrayList<ProcedureData> resArray = new ArrayList<ProcedureData>();
		for (Object[] obj : resList) {
			ProcedureData cOBJ = new ProcedureData((Integer) obj[0], (String) obj[1], (String) obj[2], (String) obj[3],
					(String) obj[4], (Integer) obj[5]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
}
