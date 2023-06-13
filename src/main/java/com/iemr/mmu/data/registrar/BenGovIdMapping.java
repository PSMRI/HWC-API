/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.registrar;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

@Entity
@Table(name = "i_bengovidmap")
public class BenGovIdMapping {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "ID")
	private Long ID;
	@Column(name = "BeneficiaryRegID")
	private Long beneficiaryRegID;
	@Column(name = "GovtIdentityTypeID")
	private Short govtIdentityTypeID;
	@Column(name = "GovtIdentityNo")
	private String govtIdentityNo;
	@Column(name = "Deleted")
	private Boolean deleted;
	@Column(name = "Processed", insertable = false, updatable = false)
	private Character processed;

	@Column(name = "CreatedBy")
	private String createdBy;
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	public BenGovIdMapping() {
	}

	public BenGovIdMapping(Long iD, Long beneficiaryRegID, Short govtIdentityTypeID, String govtIdentityNo,
			Boolean deleted, Character processed, String createdBy, Timestamp createdDate, String modifiedBy,
			Timestamp lastModDate) {
		super();
		ID = iD;
		this.beneficiaryRegID = beneficiaryRegID;
		this.govtIdentityTypeID = govtIdentityTypeID;
		this.govtIdentityNo = govtIdentityNo;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
	}

	public static ArrayList<BenGovIdMapping> getBenGovIdMappingOBJList(JsonObject benD, Long benRegID) {
		ArrayList<BenGovIdMapping> resArray = new ArrayList<>();
		BenGovIdMapping benGovIdMapOBJ = null;
		//System.out.println("ello");
		for (JsonElement obj : benD.getAsJsonArray("govID")) {
			benGovIdMapOBJ = new BenGovIdMapping();
			benGovIdMapOBJ.beneficiaryRegID = benRegID;
			if (benD.has("createdBy") && !benD.get("createdBy").isJsonNull())
				benGovIdMapOBJ.createdBy = benD.get("createdBy").getAsString();
			// if (!benD.get("createdBy").isJsonNull())
			JsonObject oo = obj.getAsJsonObject();
			if (oo.has("type") && oo.has("value")) {
				benGovIdMapOBJ.govtIdentityTypeID = oo.get("type").getAsShort();
				benGovIdMapOBJ.govtIdentityNo = oo.get("value").getAsString();
			}
			// ID will get only when to delete the GovID data
			if (oo.has("benGovMapID") && !oo.get("benGovMapID").isJsonNull())
				benGovIdMapOBJ.ID = oo.get("benGovMapID").getAsLong();
		//	System.out.println("ello");
			resArray.add(benGovIdMapOBJ);
		}
		//System.out.println("ello");

		return resArray;

	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	public Short getGovtIdentityTypeID() {
		return govtIdentityTypeID;
	}

	public void setGovtIdentityTypeID(Short govtIdentityTypeID) {
		this.govtIdentityTypeID = govtIdentityTypeID;
	}

	public String getGovtIdentityNo() {
		return govtIdentityNo;
	}

	public void setGovtIdentityNo(String govtIdentityNo) {
		this.govtIdentityNo = govtIdentityNo;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Character getProcessed() {
		return processed;
	}

	public void setProcessed(Character processed) {
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
