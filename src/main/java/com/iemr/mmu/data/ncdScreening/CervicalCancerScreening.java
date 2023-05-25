package com.iemr.mmu.data.ncdScreening;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "t_cervicalcancerscreening")
public class CervicalCancerScreening {

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

	@Column(name = "VisualExaminationId")
	private Integer visualExaminationId;

	@Column(name = "VisualExamination_VIA")
	private String visualExaminationVIA;

	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

	@Column(name = "Processed", insertable = false, updatable = true)
	private Character processed;

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

	public Integer getVisualExaminationId() {
		return visualExaminationId;
	}

	public void setVisualExaminationId(Integer visualExaminationId) {
		this.visualExaminationId = visualExaminationId;
	}

	public String getVisualExaminationVIA() {
		return visualExaminationVIA;
	}

	public void setVisualExaminationVIA(String visualExaminationVIA) {
		this.visualExaminationVIA = visualExaminationVIA;
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
