package com.iemr.mmu.data.family_planning;

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
@Table(name = "t_FamilyPlanningreproductive")
public class FamilyPlanningReproductive {

	@Id
	@GeneratedValue
	@Expose
	@Column(name = "Id", insertable = false)
	private Long id;

	@Expose
	@Column(name = "BeneficiaryRegID")
	private Long beneficiaryRegID;

	@Expose
	@Column(name = "Benvisitid")
	private Long benVisitID;

	@Expose
	@Column(name = "Visitcode")
	private Long visitCode;

	@Expose
	@Column(name = "FertilityStatusId")
	private Short fertilityStatusID;

	@Expose
	@Column(name = "FertilityStatus")
	private String fertilityStatus;

	@Expose
	@Column(name = "Parity")
	private Integer parity;

	@Expose
	@Column(name = "Total_Children_born")
	private Integer totalNoOfChildrenBorn;

	@Expose
	@Column(name = "Total_FemaleChildren_born")
	private Integer totalNoOfChildrenBornFemale;

	@Expose
	@Column(name = "Total_MaleChildren_born")
	private Integer totalNoOfChildrenBornMale;

	@Expose
	@Column(name = "Total_LiveChildren_born")
	private Integer numberOfLiveChildren;

	@Expose
	@Column(name = "Total_LiveFemaleChildren_born")
	private Integer numberOfLiveChildrenFemale;

	@Expose
	@Column(name = "Total_LiveMaleChildren_born")
	private Integer numberOfLiveChildrenMale;

	@Expose
	@Column(name = "Avg_YoungestChildage")
	private Integer ageOfYoungestChild;

	@Expose
	@Column(name = "Ageunit")
	private String unitOfAge;

	@Expose
	@Column(name = "YoungesChild_Gender")
	private String youngestChildGender;

	@Expose
	@Column(name = "OtherYoungesChild_Gender")
	private String otherYoungestChildGender;

	@Expose
	@Column(name = "FP_Method")
	private String currentlyUsingFpMethodDB;

	@Expose
	@Column(name = "OtherFP_Method")
	private String otherCurrentlyUsingFpMethod;

	@Expose
	@Column(name = "Sterilization_date")
	private Timestamp dateOfSterilization;

	@Expose
	@Column(name = "Sterilization_place")
	private String placeOfSterilization;

	@Expose
	@Column(name = "Doses_Taken")
	private Integer dosesTaken;

	@Expose
	@Column(name = "Date_LastDosetaken")
	private Timestamp dateOfLastDoseTaken;

	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;

	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private @SQLInjectionSafe String processed;

	@Expose
	@Column(name = "CreatedBy", updatable = false)
	private @SQLInjectionSafe String createdBy;

	@Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;

	@Expose
	@Column(name = "ModifiedBy", insertable = false, updatable = true)
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

	@Transient
	private String[] currentlyUsingFpMethod;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

	public Short getFertilityStatusID() {
		return fertilityStatusID;
	}

	public void setFertilityStatusID(Short fertilityStatusID) {
		this.fertilityStatusID = fertilityStatusID;
	}

	public String getFertilityStatus() {
		return fertilityStatus;
	}

	public void setFertilityStatus(String fertilityStatus) {
		this.fertilityStatus = fertilityStatus;
	}

	public Integer getParity() {
		return parity;
	}

	public void setParity(Integer parity) {
		this.parity = parity;
	}

	public Integer getTotalNoOfChildrenBorn() {
		return totalNoOfChildrenBorn;
	}

	public void setTotalNoOfChildrenBorn(Integer totalNoOfChildrenBorn) {
		this.totalNoOfChildrenBorn = totalNoOfChildrenBorn;
	}

	public Integer getTotalNoOfChildrenBornFemale() {
		return totalNoOfChildrenBornFemale;
	}

	public void setTotalNoOfChildrenBornFemale(Integer totalNoOfChildrenBornFemale) {
		this.totalNoOfChildrenBornFemale = totalNoOfChildrenBornFemale;
	}

	public Integer getTotalNoOfChildrenBornMale() {
		return totalNoOfChildrenBornMale;
	}

	public void setTotalNoOfChildrenBornMale(Integer totalNoOfChildrenBornMale) {
		this.totalNoOfChildrenBornMale = totalNoOfChildrenBornMale;
	}

	public Integer getNumberOfLiveChildren() {
		return numberOfLiveChildren;
	}

	public void setNumberOfLiveChildren(Integer numberOfLiveChildren) {
		this.numberOfLiveChildren = numberOfLiveChildren;
	}

	public Integer getNumberOfLiveChildrenFemale() {
		return numberOfLiveChildrenFemale;
	}

	public void setNumberOfLiveChildrenFemale(Integer numberOfLiveChildrenFemale) {
		this.numberOfLiveChildrenFemale = numberOfLiveChildrenFemale;
	}

	public Integer getNumberOfLiveChildrenMale() {
		return numberOfLiveChildrenMale;
	}

	public void setNumberOfLiveChildrenMale(Integer numberOfLiveChildrenMale) {
		this.numberOfLiveChildrenMale = numberOfLiveChildrenMale;
	}

	public Integer getAgeOfYoungestChild() {
		return ageOfYoungestChild;
	}

	public void setAgeOfYoungestChild(Integer ageOfYoungestChild) {
		this.ageOfYoungestChild = ageOfYoungestChild;
	}

	public String getUnitOfAge() {
		return unitOfAge;
	}

	public void setUnitOfAge(String unitOfAge) {
		this.unitOfAge = unitOfAge;
	}

	public String getYoungestChildGender() {
		return youngestChildGender;
	}

	public void setYoungestChildGender(String youngestChildGender) {
		this.youngestChildGender = youngestChildGender;
	}

	public String getOtherYoungestChildGender() {
		return otherYoungestChildGender;
	}

	public void setOtherYoungestChildGender(String otherYoungestChildGender) {
		this.otherYoungestChildGender = otherYoungestChildGender;
	}

	public String getCurrentlyUsingFpMethodDB() {
		return currentlyUsingFpMethodDB;
	}

	public void setCurrentlyUsingFpMethodDB(String currentlyUsingFpMethodDB) {
		this.currentlyUsingFpMethodDB = currentlyUsingFpMethodDB;
	}

	public String getOtherCurrentlyUsingFpMethod() {
		return otherCurrentlyUsingFpMethod;
	}

	public void setOtherCurrentlyUsingFpMethod(String otherCurrentlyUsingFpMethod) {
		this.otherCurrentlyUsingFpMethod = otherCurrentlyUsingFpMethod;
	}

	public Timestamp getDateOfSterilization() {
		return dateOfSterilization;
	}

	public void setDateOfSterilization(Timestamp dateOfSterilization) {
		this.dateOfSterilization = dateOfSterilization;
	}

	public String getPlaceOfSterilization() {
		return placeOfSterilization;
	}

	public void setPlaceOfSterilization(String placeOfSterilization) {
		this.placeOfSterilization = placeOfSterilization;
	}

	public Integer getDosesTaken() {
		return dosesTaken;
	}

	public void setDosesTaken(Integer dosesTaken) {
		this.dosesTaken = dosesTaken;
	}

	public Timestamp getDateOfLastDoseTaken() {
		return dateOfLastDoseTaken;
	}

	public void setDateOfLastDoseTaken(Timestamp dateOfLastDoseTaken) {
		this.dateOfLastDoseTaken = dateOfLastDoseTaken;
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

	public String[] getCurrentlyUsingFpMethod() {
		return currentlyUsingFpMethod;
	}

	public void setCurrentlyUsingFpMethod(String[] currentlyUsingFpMethod) {
		this.currentlyUsingFpMethod = currentlyUsingFpMethod;
	}

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}

}
