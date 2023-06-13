/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.quickConsultation;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.data.snomedct.SCTDescription;

@Entity
@Table(name = "t_benclinicalobservation")
public class BenClinicalObservations {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "ClinicalObservationID")
	private Long clinicalObservationID;

	@Expose
	@Column(name = "BeneficiaryRegID")
	private Long beneficiaryRegID;

	@Expose
	@Column(name = "BenVisitID")
	private Long benVisitID;
	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;

	@Expose
	@Column(name = "VisitCode")
	private Long visitCode;

	@Expose
	@Column(name = "ClinicalObservation")
	private String clinicalObservation;
	
	@Expose
	@Column(name = "ClinicalObservation_Sctcode")
	private String clinicalObservationSctcode;

	@Expose
	@Column(name = "OtherSymptoms")
	private String otherSymptoms;

	// newly added field on 07-08-2018
	@Expose
	@Column(name = "OtherSymptoms_SCTCode")
	private String otherSymptomsSCTCode;

	@Expose
	@Column(name = "OtherSymptoms_SCTTerm")
	private String otherSymptomsSCTTerm;

	@Expose
	@Column(name = "SignificantFindings")
	private String significantFindings;
	
	@Expose
	@Column(name = "SignificantFindings_Sctcode")
	private String significantFindingsSctcode;

	@Expose
	@Column(name = "IsForHistory")
	private Boolean isForHistory;

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
	@Column(name = "VanSerialNo")
	private Long vanSerialNo;

	@Expose
	@Column(name = "VehicalNo")
	private String vehicalNo;

	@Expose
	@Column(name = "vanID")
	private Integer vanID;

	@Expose
	@Column(name = "ParkingPlaceID")
	private Integer parkingPlaceID;

	@Expose
	@Column(name = "SyncedBy")
	private String syncedBy;

	@Expose
	@Column(name = "SyncedDate")
	private Timestamp syncedDate;

	@Expose
	@Column(name = "ReservedForChange")
	private String reservedForChange;

	@Transient
	private Date captureDate;
	

	public BenClinicalObservations() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	public Long getBenVisitID() {
		return benVisitID;
	}

	public void setBenVisitID(Long benVisitID) {
		this.benVisitID = benVisitID;
	}

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}

	public String getClinicalObservation() {
		return clinicalObservation;
	}

	public void setClinicalObservation(String clinicalObservation) {
		this.clinicalObservation = clinicalObservation;
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

	public Long getClinicalObservationID() {
		return clinicalObservationID;
	}

	public String getOtherSymptoms() {
		return otherSymptoms;
	}

	public void setOtherSymptoms(String otherSymptoms) {
		this.otherSymptoms = otherSymptoms;
	}

	public void setClinicalObservationID(Long clinicalObservationID) {
		this.clinicalObservationID = clinicalObservationID;
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

	public Integer getParkingPlaceID() {
		return parkingPlaceID;
	}

	public void setParkingPlaceID(Integer parkingPlaceID) {
		this.parkingPlaceID = parkingPlaceID;
	}

	public String getSyncedBy() {
		return syncedBy;
	}

	public void setSyncedBy(String syncedBy) {
		this.syncedBy = syncedBy;
	}

	public Timestamp getSyncedDate() {
		return syncedDate;
	}

	public void setSyncedDate(Timestamp syncedDate) {
		this.syncedDate = syncedDate;
	}

	public String getReservedForChange() {
		return reservedForChange;
	}

	public void setReservedForChange(String reservedForChange) {
		this.reservedForChange = reservedForChange;
	}

	public String getSignificantFindings() {
		return significantFindings;
	}

	public void setSignificantFindings(String significantFindings) {
		this.significantFindings = significantFindings;
	}

	public Boolean getIsForHistory() {
		return isForHistory;
	}

	public void setIsForHistory(Boolean isForHistory) {
		this.isForHistory = isForHistory;
	}

	public BenClinicalObservations(String significantFindings, Date createdDate) {
		super();
		this.significantFindings = significantFindings;
		this.captureDate = createdDate;
	}

	public BenClinicalObservations(Long clinicalObservationID, Long beneficiaryRegID, Long benVisitID,
			Integer providerServiceMapID, String clinicalObservation, String otherSymptoms, String significantFindings,
			Boolean isForHistory) {
		super();
		this.clinicalObservationID = clinicalObservationID;
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.clinicalObservation = clinicalObservation;
		this.otherSymptoms = otherSymptoms;
		this.significantFindings = significantFindings;
		this.isForHistory = isForHistory;
	}

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

	public String getOtherSymptomsSCTCode() {
		return otherSymptomsSCTCode;
	}

	public void setOtherSymptomsSCTCode(String otherSymptomsSCTCode) {
		this.otherSymptomsSCTCode = otherSymptomsSCTCode;
	}

	public String getOtherSymptomsSCTTerm() {
		return otherSymptomsSCTTerm;
	}

	public void setOtherSymptomsSCTTerm(String otherSymptomsSCTTerm) {
		this.otherSymptomsSCTTerm = otherSymptomsSCTTerm;
	}

	public Date getCaptureDate() {
		return captureDate;
	}

	public void setCaptureDate(Date captureDate) {
		this.captureDate = captureDate;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public String getClinicalObservationSctcode() {
		return clinicalObservationSctcode;
	}

	public void setClinicalObservationSctcode(String clinicalObservationSctcode) {
		this.clinicalObservationSctcode = clinicalObservationSctcode;
	}

	public String getSignificantFindingsSctcode() {
		return significantFindingsSctcode;
	}

	public void setSignificantFindingsSctcode(String significantFindingsSctcode) {
		this.significantFindingsSctcode = significantFindingsSctcode;
	}
	
	
	
	

	// public static BenClinicalObservations
	// getBenClinicalObservationsList(JsonObject emrgCasesheet) {
	// ArrayList<BenClinicalObservations> resArray = new ArrayList<>();
	// BenClinicalObservations benClinicalObservations = null;
	//
	// benClinicalObservations = new BenClinicalObservations();
	//
	// if (emrgCasesheet.has("benVisitID") &&
	// !emrgCasesheet.get("benVisitID").isJsonNull())
	// benChiefComplaint.setBenVisitID(new
	// Long(emrgCasesheet.get("benVisitID").toString()));
	//
	// if (emrgCasesheet.has("beneficiaryRegID") &&
	// !emrgCasesheet.get("beneficiaryRegID").isJsonNull())
	// benChiefComplaint.setBeneficiaryRegID(new
	// Long(emrgCasesheet.get("beneficiaryRegID").toString()));
	//
	// if (emrgCasesheet.has("providerServiceMapID") &&
	// !emrgCasesheet.get("providerServiceMapID").isJsonNull())
	// benChiefComplaint.setProviderServiceMapID(new
	// Integer(emrgCasesheet.get("providerServiceMapID").toString()));
	//
	// JsonObject obj = csobj.getAsJsonObject();
	//
	// if (obj.has("chiefComplaintID") &&
	// !obj.get("chiefComplaintID").isJsonNull())
	// benChiefComplaint.setChiefComplaintID(new
	// Integer(obj.get("chiefComplaintID").toString()));
	//
	// if (obj.has("chiefComplaint") && !obj.get("chiefComplaint").isJsonNull())
	// benChiefComplaint.setChiefComplaint(obj.get("chiefComplaint").toString());
	//
	// if (obj.has("duration") && !obj.get("duration").isJsonNull())
	// benChiefComplaint.setDuration(new
	// Integer(obj.get("providerServiceMapID").toString()));
	//
	// if (obj.has("unitOfDuration") && !obj.get("unitOfDuration").isJsonNull())
	// benChiefComplaint.setUnitOfDuration(obj.get("unitOfDuration").toString());
	//
	// if (obj.has("description") && !obj.get("description").isJsonNull())
	// benChiefComplaint.setDescription(obj.get("description").toString());
	//
	// if (emrgCasesheet.has("createdBy") &&
	// !emrgCasesheet.get("createdBy").isJsonNull())
	// benChiefComplaint.setCreatedBy(emrgCasesheet.get("createdBy").toString());
	//
	// resArray.add(benChiefComplaint);
	// }
	//
	// return resArray;
	// }

}
