package com.iemr.mmu.data.anc;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.annotation.sqlInjectionSafe.SQLInjectionSafe;

@Entity
@Table(name = "t_childoptionalvaccinedetail")
public class ChildOptionalVaccineDetail {

	@Id
	@GeneratedValue
	@Expose
	@Column(name = "ID")
	private Long ID;

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
	@Column(name = "DefaultReceivingAge")
	private @SQLInjectionSafe String defaultReceivingAge;

	@Expose
	@Column(name = "VaccineName")
	private @SQLInjectionSafe String vaccineName;

	@Expose
	@Column(name = "otherVaccineName")
	private @SQLInjectionSafe String otherVaccineName;

	@Expose
	@Column(name = "Status")
	private @SQLInjectionSafe String status;

	@Expose
	@Column(name = "ReceivedDate")
	private Timestamp receivedDate;

	@Expose
	@Column(name = "ActualReceivingAge")
	private @SQLInjectionSafe String actualReceivingAge;

	@Expose
	@Column(name = "ReceivedFacilityName")
	private @SQLInjectionSafe String receivedFacilityName;

	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private @SQLInjectionSafe String processed;

	@Expose
	@Column(name = "CreatedBy")
	private @SQLInjectionSafe String createdBy;

	@Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;

	@Expose
	@Column(name = "ModifiedBy")
	private @SQLInjectionSafe String modifiedBy;

	@Expose
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	@Expose
	@Column(name = "VanSerialNo")
	private Long vanSerialNo;

	@Expose
	@Column(name = "VehicalNo")
	private @SQLInjectionSafe String vehicalNo;

	@Expose
	@Column(name = "vanID")
	private Integer vanID;

	@Expose
	@Column(name = "ParkingPlaceID")
	private Integer parkingPlaceID;

	@Expose
	@Column(name = "SyncedBy")
	private @SQLInjectionSafe String syncedBy;

	@Expose
	@Column(name = "SyncedDate")
	private Timestamp syncedDate;

	@Expose
	@Column(name = "ReservedForChange")
	private @SQLInjectionSafe String reservedForChange;

	@Transient
	private Date captureDate;

	@Expose
	@Column(name = "Sctcode")
	private @SQLInjectionSafe String sctCode;

	@Expose
	@Column(name = "SctTerm")
	private @SQLInjectionSafe String sctTerm;

	@Expose
	@Column(name = "ageunitid")
	private Integer ageUnitID;

	@Expose
	@Column(name = "ageunit")
	private String ageUnit;

	public String getOtherVaccineName() {
		return otherVaccineName;
	}

	public void setOtherVaccineName(String otherVaccineName) {
		this.otherVaccineName = otherVaccineName;
	}

	public Integer getAgeUnitID() {
		return ageUnitID;
	}

	public void setAgeUnitID(Integer ageUnitID) {
		this.ageUnitID = ageUnitID;
	}

	public String getAgeUnit() {
		return ageUnit;
	}

	public void setAgeUnit(String ageUnit) {
		this.ageUnit = ageUnit;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public Date getCaptureDate() {
		return captureDate;
	}

	public void setCaptureDate(Date captureDate) {
		this.captureDate = captureDate;
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

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

	public String getDefaultReceivingAge() {
		return defaultReceivingAge;
	}

	public void setDefaultReceivingAge(String defaultReceivingAge) {
		this.defaultReceivingAge = defaultReceivingAge;
	}

	public String getVaccineName() {
		return vaccineName;
	}

	public void setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Timestamp receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getActualReceivingAge() {
		return actualReceivingAge;
	}

	public void setActualReceivingAge(String actualReceivingAge) {
		this.actualReceivingAge = actualReceivingAge;
	}

	public String getReceivedFacilityName() {
		return receivedFacilityName;
	}

	public void setReceivedFacilityName(String receivedFacilityName) {
		this.receivedFacilityName = receivedFacilityName;
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

	public Long getID() {
		return ID;
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

	public ChildOptionalVaccineDetail(String defaultReceivingAge, String vaccineName, String otherVaccineName,
			String status, Timestamp receivedDate, String actualReceivingAge, String receivedFacilityName,
			String sctCode, String sctTerm, Integer ageUnitId, String ageUnit) {
		super();
		this.defaultReceivingAge = defaultReceivingAge;
		this.vaccineName = vaccineName;
		this.otherVaccineName = otherVaccineName;
		this.status = status;
		this.receivedDate = receivedDate;
		this.actualReceivingAge = actualReceivingAge;
		this.receivedFacilityName = receivedFacilityName;
		this.sctCode = sctCode;
		this.sctTerm = sctTerm;
		this.ageUnitID = ageUnitId;
		this.ageUnit = ageUnit;
	}

	public ChildOptionalVaccineDetail(Date createdDate, String defaultReceivingAge, String vaccineName,
			String otherVaccineName, String status, Timestamp receivedDate, String actualReceivingAge,
			String receivedFacilityName, String ageUnit) {
		super();
		this.captureDate = createdDate;
		this.defaultReceivingAge = defaultReceivingAge;
		this.vaccineName = vaccineName;
		this.otherVaccineName = otherVaccineName;
		this.status = status;
		this.receivedDate = receivedDate;
		this.actualReceivingAge = actualReceivingAge;
		this.receivedFacilityName = receivedFacilityName;
		this.ageUnit = ageUnit;

	}

	public ChildOptionalVaccineDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getSctCode() {
		return sctCode;
	}

	public void setSctCode(String sctCode) {
		this.sctCode = sctCode;
	}

	public String getSctTerm() {
		return sctTerm;
	}

	public void setSctTerm(String sctTerm) {
		this.sctTerm = sctTerm;
	}

}
