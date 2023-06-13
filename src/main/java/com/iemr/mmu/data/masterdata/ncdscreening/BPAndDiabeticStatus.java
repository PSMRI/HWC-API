/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.masterdata.ncdscreening;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.data.doctor.LabTestMaster;

@Entity
@Table(name = "m_bpanddiabeticstatus")
public class BPAndDiabeticStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "BPAndDiabeticStatusID")
	private Short bpAndDiabeticStatusID; 
	
	@Expose
	@Column(name = "BPAndDiabeticStatus")
	private String bpAndDiabeticStatus;
	
	@Expose
	@Column(name = "BPAndDiabeticStatusDesc")
	private String bpAndDiabeticStatusDesc;
	
	@Expose
	@Column(name = "IsBPStatus")
	private Boolean isBPStatus;
	
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

	public BPAndDiabeticStatus() {
		super();
	}
	
	
	public BPAndDiabeticStatus(Short bpAndDiabeticStatusID, String bpAndDiabeticStatus) {
		super();
		this.bpAndDiabeticStatusID = bpAndDiabeticStatusID;
		this.bpAndDiabeticStatus = bpAndDiabeticStatus;
	}


	public static ArrayList<BPAndDiabeticStatus> getBPAndDiabeticStatus(ArrayList<Object[]> resList) {
		ArrayList<BPAndDiabeticStatus> resArray = new ArrayList<BPAndDiabeticStatus>();
		for (Object[] obj : resList) {
			BPAndDiabeticStatus cOBJ = new BPAndDiabeticStatus((Short)obj[0], (String)obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}

	public Short getBpAndDiabeticStatusID() {
		return bpAndDiabeticStatusID;
	}

	public void setBpAndDiabeticStatusID(Short bpAndDiabeticStatusID) {
		this.bpAndDiabeticStatusID = bpAndDiabeticStatusID;
	}

	public String getBpAndDiabeticStatus() {
		return bpAndDiabeticStatus;
	}

	public void setBpAndDiabeticStatus(String bpAndDiabeticStatus) {
		this.bpAndDiabeticStatus = bpAndDiabeticStatus;
	}

	public String getBpAndDiabeticStatusDesc() {
		return bpAndDiabeticStatusDesc;
	}

	public void setBpAndDiabeticStatusDesc(String bpAndDiabeticStatusDesc) {
		this.bpAndDiabeticStatusDesc = bpAndDiabeticStatusDesc;
	}

	public Boolean getIsBPStatus() {
		return isBPStatus;
	}

	public void setIsBPStatus(Boolean isBPStatus) {
		this.isBPStatus = isBPStatus;
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

	
	
	
}
