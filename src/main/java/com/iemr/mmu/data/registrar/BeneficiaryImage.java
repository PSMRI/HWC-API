/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.registrar;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "i_benimage")
public class BeneficiaryImage {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "BenImageID")
	private Long benImageID;
	@Expose
	@Column(name = "BeneficiaryRegID")
	private Long beneficiaryRegID;
	@Expose
	@Column(name = "benImage")
	private String benImage;
	@Expose
	@Column(name = "Deleted", insertable = false)
	private Boolean deleted;
	@Expose
	@Column(name = "Processed", insertable = false)
	private Boolean processed;
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

	public BeneficiaryImage() {
	}

	public BeneficiaryImage(Long benImageID, Long beneficiaryRegID, String benImage, Boolean deleted, Boolean processed,
			String createdBy, Timestamp createdDate, String modifiedBy, Timestamp lastModDate) {
		super();
		this.benImageID = benImageID;
		this.beneficiaryRegID = beneficiaryRegID;
		this.benImage = benImage;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
	}

	public Long getBenImageID() {
		return benImageID;
	}

	public void setBenImageID(Long benImageID) {
		this.benImageID = benImageID;
	}

	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	public String getBenImage() {
		return benImage;
	}

	public void setBenImage(String benImage) {
		this.benImage = benImage;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
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
