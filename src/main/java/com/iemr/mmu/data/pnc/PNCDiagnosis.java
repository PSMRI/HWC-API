package com.iemr.mmu.data.pnc;

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
import com.iemr.mmu.annotation.sqlInjectionSafe.SQLInjectionSafe;
import com.iemr.mmu.data.snomedct.SCTDescription;

@Entity
@Table(name = "t_pncdiagnosis")
public class PNCDiagnosis {
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
	@Column(name = "PrescriptionID")
	private Long prescriptionID;

	@Expose
	@Column(name = "ProvisionalDiagnosis")
	private String provisionalDiagnosis;

	@Expose
	@Column(name = "ProvisionalDiagnosis_SCTCode")
	private String provisionalDiagnosisSCTCode;

	@Expose
	@Column(name = "ProvisionalDiagnosis_SCTTerm")
	private String provisionalDiagnosisSCTTerm;

	@Expose
	@Column(name = "ConfirmatoryDiagnosis")
	private String confirmatoryDiagnosis;

	@Expose
	@Column(name = "ConfirmatoryDiagnosis_SCTCode")
	private String confirmatoryDiagnosisSCTCode;

	@Expose
	@Column(name = "ConfirmatoryDiagnosis_SCTTerm")
	private String confirmatoryDiagnosisSCTTerm;

	@Expose
	@Column(name = "IsMaternalDeath")
	private Boolean isMaternalDeath;

	@Expose
	@Column(name = "PlaceOfDeath")
	private String placeOfDeath;

	@Expose
	@Column(name = "DateOfDeath")
	private Date dateOfDeath;

	@Expose
	@Column(name = "CauseOfDeath")
	private String causeOfDeath;

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
	@Expose
	private String externalInvestigation;

	@Transient
	@Expose
	private String specialistDiagnosis;

	@Transient
	@Expose
	private String counsellingProvided;
	
	@Expose
	@Transient
	private ArrayList<SCTDescription> provisionalDiagnosisList;

	@Expose
	@Transient
	private ArrayList<SCTDescription> confirmatoryDiagnosisList;

	public String getProvisionalDiagnosisSCTCode() {
		return provisionalDiagnosisSCTCode;
	}

	public void setProvisionalDiagnosisSCTCode(String provisionalDiagnosisSCTCode) {
		this.provisionalDiagnosisSCTCode = provisionalDiagnosisSCTCode;
	}

	public String getProvisionalDiagnosisSCTTerm() {
		return provisionalDiagnosisSCTTerm;
	}

	public void setProvisionalDiagnosisSCTTerm(String provisionalDiagnosisSCTTerm) {
		this.provisionalDiagnosisSCTTerm = provisionalDiagnosisSCTTerm;
	}

	public String getConfirmatoryDiagnosisSCTCode() {
		return confirmatoryDiagnosisSCTCode;
	}

	public void setConfirmatoryDiagnosisSCTCode(String confirmatoryDiagnosisSCTCode) {
		this.confirmatoryDiagnosisSCTCode = confirmatoryDiagnosisSCTCode;
	}

	public String getConfirmatoryDiagnosisSCTTerm() {
		return confirmatoryDiagnosisSCTTerm;
	}

	public void setConfirmatoryDiagnosisSCTTerm(String confirmatoryDiagnosisSCTTerm) {
		this.confirmatoryDiagnosisSCTTerm = confirmatoryDiagnosisSCTTerm;
	}

	public String getExternalInvestigation() {
		return externalInvestigation;
	}

	public void setExternalInvestigation(String externalInvestigation) {
		this.externalInvestigation = externalInvestigation;
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

	public Long getPrescriptionID() {
		return prescriptionID;
	}

	public void setPrescriptionID(Long prescriptionID) {
		this.prescriptionID = prescriptionID;
	}

	public String getProvisionalDiagnosis() {
		return provisionalDiagnosis;
	}

	public void setProvisionalDiagnosis(String provisionalDiagnosis) {
		this.provisionalDiagnosis = provisionalDiagnosis;
	}

	public String getConfirmatoryDiagnosis() {
		return confirmatoryDiagnosis;
	}

	public void setConfirmatoryDiagnosis(String confirmatoryDiagnosis) {
		this.confirmatoryDiagnosis = confirmatoryDiagnosis;
	}

	public Boolean getIsMaternalDeath() {
		return isMaternalDeath;
	}

	public void setIsMaternalDeath(Boolean isMaternalDeath) {
		this.isMaternalDeath = isMaternalDeath;
	}

	public String getPlaceOfDeath() {
		return placeOfDeath;
	}

	public void setPlaceOfDeath(String placeOfDeath) {
		this.placeOfDeath = placeOfDeath;
	}

	public Date getDateOfDeath() {
		return dateOfDeath;
	}

	public void setDateOfDeath(Date dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}

	public String getCauseOfDeath() {
		return causeOfDeath;
	}

	public void setCauseOfDeath(String causeOfDeath) {
		this.causeOfDeath = causeOfDeath;
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

	public PNCDiagnosis() {
	}

	public PNCDiagnosis(Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID, Long prescriptionID,
			String provisionalDiagnosis, String confirmatoryDiagnosis, Boolean isMaternalDeath, String placeOfDeath,
			Date dateOfDeath, String causeOfDeath, Long visitCode, String externalInvestigation) {
		super();
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.prescriptionID = prescriptionID;
		this.provisionalDiagnosis = provisionalDiagnosis;
		this.confirmatoryDiagnosis = confirmatoryDiagnosis;
		this.isMaternalDeath = isMaternalDeath;
		this.placeOfDeath = placeOfDeath;
		this.dateOfDeath = dateOfDeath;
		this.causeOfDeath = causeOfDeath;
		this.visitCode = visitCode;
		this.externalInvestigation = externalInvestigation;
	}

	public static PNCDiagnosis getPNCDiagnosisDetails(ArrayList<Object[]> resList) {
		PNCDiagnosis cOBJ = null;
		if (null != resList && resList.size() > 0) {
			Object[] obj = resList.get(0);
			cOBJ = new PNCDiagnosis((Long) obj[0], (Long) obj[1], (Integer) obj[2], (Long) obj[3], (String) obj[4],
					(String) obj[5], (Boolean) obj[6], (String) obj[7], (Date) obj[8], (String) obj[9], (Long) obj[10],
					null);

		}
		return cOBJ;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public ArrayList<SCTDescription> getProvisionalDiagnosisList() {
		return provisionalDiagnosisList;
	}

	public void setProvisionalDiagnosisList(ArrayList<SCTDescription> provisionalDiagnosisList) {
		this.provisionalDiagnosisList = provisionalDiagnosisList;
	}

	public ArrayList<SCTDescription> getConfirmatoryDiagnosisList() {
		return confirmatoryDiagnosisList;
	}

	public void setConfirmatoryDiagnosisList(ArrayList<SCTDescription> confirmatoryDiagnosisList) {
		this.confirmatoryDiagnosisList = confirmatoryDiagnosisList;
	}

	public String getSpecialistDiagnosis() {
		return specialistDiagnosis;
	}

	public void setSpecialistDiagnosis(String specialistDiagnosis) {
		this.specialistDiagnosis = specialistDiagnosis;
	}

	public String getCounsellingProvided() {
		return counsellingProvided;
	}

	public void setCounsellingProvided(String counsellingProvided) {
		this.counsellingProvided = counsellingProvided;
	}

}
