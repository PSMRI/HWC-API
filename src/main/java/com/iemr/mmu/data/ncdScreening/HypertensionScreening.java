/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.ncdScreening;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

/**
 * 
 * @author SH20094090 17-08-2022
 */

@Entity
@Table(name = "t_hypertensionscreening")
public class HypertensionScreening {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id", insertable = false)
	private Long id;

	@Column(name = "BeneficiaryRegId")
	private Long beneficiaryRegId;

	@Column(name = "Visitcode")
	private Long visitcode;

	public Long getVisitcode() {
		return visitcode;
	}

	public void setVisitcode(Long visitcode) {
		this.visitcode = visitcode;
	}

	@Column(name = "systolicBP_1stReading")
	private Integer systolicBP_1stReading;

	@Column(name = "diastolicBP_1stReading")
	private Integer diastolicBP_1stReading;

	@Column(name = "systolicBP_2ndReading")
	private Integer systolicBP_2ndReading;

	@Column(name = "diastolicBP_2ndReading")
	private Integer diastolicBP_2ndReading;

	@Column(name = "systolicBP_3rdReading")
	private Integer systolicBP_3rdReading;

	@Column(name = "diastolicBP_3rdReading")
	private Integer diastolicBP_3rdReading;

	@Column(name = "averageSystolicBP")
	private Integer averageSystolicBP;

	@Column(name = "averageDiastolicBP")
	private Integer averageDiastolicBP;

	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

	@Column(name = "Processed", insertable = false, updatable = true)
	private String processed;

	@Column(name = "CreatedBy", insertable = true, updatable = false)
	private String createdBy;

	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;

	@Column(name = "ModifiedBy", insertable = false, updatable = true)
	private String modifiedBy;

	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	@Expose
	@Column(name = "VanSerialNo")
	private Long vanSerialNo;

	@Expose
	@Column(name = "VehicalNo")
	private String vehicalNo;

	@Expose
	@Column(name = "vanID", updatable = false)
	private Integer vanID;

	@Expose
	@Column(name = "ParkingPlaceID", updatable = false)
	private Integer parkingPlaceID;

	@Expose
	@Column(name = "suspected")
	private Boolean suspected;

	@Expose
	@Column(name = "Confirmatory")
	private Boolean confirmed;
	
	
	

	public Boolean getSuspected() {
		return suspected;
	}

	public void setSuspected(Boolean suspected) {
		this.suspected = suspected;
	}

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBeneficiaryRegId() {
		return beneficiaryRegId;
	}

	public void setBeneficiaryRegId(Long beneficiaryRegId) {
		this.beneficiaryRegId = beneficiaryRegId;
	}

	public Integer getSystolicBP_1stReading() {
		return systolicBP_1stReading;
	}

	public void setSystolicBP_1stReading(Integer systolicBP_1stReading) {
		this.systolicBP_1stReading = systolicBP_1stReading;
	}

	public Integer getDiastolicBP_1stReading() {
		return diastolicBP_1stReading;
	}

	public void setDiastolicBP_1stReading(Integer diastolicBP_1stReading) {
		this.diastolicBP_1stReading = diastolicBP_1stReading;
	}

	public Integer getSystolicBP_2ndReading() {
		return systolicBP_2ndReading;
	}

	public void setSystolicBP_2ndReading(Integer systolicBP_2ndReading) {
		this.systolicBP_2ndReading = systolicBP_2ndReading;
	}

	public Integer getDiastolicBP_2ndReading() {
		return diastolicBP_2ndReading;
	}

	public void setDiastolicBP_2ndReading(Integer diastolicBP_2ndReading) {
		this.diastolicBP_2ndReading = diastolicBP_2ndReading;
	}

	public Integer getSystolicBP_3rdReading() {
		return systolicBP_3rdReading;
	}

	public void setSystolicBP_3rdReading(Integer systolicBP_3rdReading) {
		this.systolicBP_3rdReading = systolicBP_3rdReading;
	}

	public Integer getDiastolicBP_3rdReading() {
		return diastolicBP_3rdReading;
	}

	public void setDiastolicBP_3rdReading(Integer diastolicBP_3rdReading) {
		this.diastolicBP_3rdReading = diastolicBP_3rdReading;
	}

	public Integer getAverageSystolicBP() {
		return averageSystolicBP;
	}

	public void setAverageSystolicBP(Integer averageSystolicBP) {
		this.averageSystolicBP = averageSystolicBP;
	}

	public Integer getAverageDiastolicBP() {
		return averageDiastolicBP;
	}

	public void setAverageDiastolicBP(Integer averageDiastolicBP) {
		this.averageDiastolicBP = averageDiastolicBP;
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

	public Long getVanSerialNo() {
		return vanSerialNo;
	}

	public void setVanSerialNo(Long vanSerialNo) {
		this.vanSerialNo = vanSerialNo;
	}

	public String getVehicalNo() {
		return vehicalNo;
	}

	public void setVehicalNo(String vehicalNo) {
		this.vehicalNo = vehicalNo;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public Integer getParkingPlaceID() {
		return parkingPlaceID;
	}

	public void setParkingPlaceID(Integer parkingPlaceID) {
		this.parkingPlaceID = parkingPlaceID;
	}
}
