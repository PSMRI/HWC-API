package com.iemr.mmu.data.registrar;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import com.iemr.mmu.data.login.MasterServicePoint;
import com.iemr.mmu.data.provider.ProviderServiceMapping;

@Entity
@Table(name = "I_bendemographics")
public class BeneficiaryDemographicData {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "BenDemographicsID")
	private Long benDemographicsID;
	@Expose
	@Column(name = "BeneficiaryRegID")
	private Long beneficiaryRegID;
	@Expose
	@Column(name = "EducationID")
	private Short educationID;
	@Expose
	@Column(name = "OccupationID")
	private Short occupationID;
	@Expose
	@Column(name = "HealthCareWorkerID")
	private Short healthCareWorkerID;
	@Expose
	@Column(name = "IncomeStatusID")
	private Short incomeStatusID;
	@Expose
	@Column(name = "CommunityID")
	private Short communityID;
	@Expose
	@Column(name = "PreferredLangID")
	private Integer preferredLangID;
	@Expose
	@Column(name = "ReligionID")
	private Short religionID;
	@Expose
	@Column(name = "isPhoto")
	private Boolean isPhoto;
	@Expose
	@Column(name = "PhotoFilePath")
	private String photoFilePath;
	@Expose
	@Column(name = "IsBiometric")
	private Boolean isBiometric;
	@Expose
	@Column(name = "BiometricFilePath")
	private String biometricFilePath;
	@Expose
	@Column(name = "AddressLine1")
	private String addressLine1;
	@Expose
	@Column(name = "AddressLine2")
	private String addressLine2;
	@Expose
	@Column(name = "AddressLine3")
	private String addressLine3;
	@Expose
	@Column(name = "AddressLine4")
	private String addressLine4;
	@Expose
	@Column(name = "AddressLine5")
	private String addressLine5;
	@Expose
	@Column(name = "CountryID")
	private Integer countryID;
	@Expose
	@Column(name = "StateID")
	private Integer stateID;
	@Expose
	@Column(name = "DistrictID")
	private Integer districtID;
	@Expose
	@Column(name = "BlockID")
	private Integer blockID;
	@Expose
	@Column(name = "DistrictBranchID")
	private Integer districtBranchID;
	@Expose
	@Column(name = "CityID")
	private Integer cityID;
	@Expose
	@Column(name = "PinCode")
	private String pinCode;
	@Expose
	@Column(name = "IsAddPresent")
	private Boolean isAddPresent;
	@Expose
	@Column(name = "IsAddPermanent")
	private Boolean isAddPermanent;
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;
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
	/*
	 * @Expose
	 * 
	 * @Column(name = "SubDistrictID") private int subDistrictID;
	 * 
	 * @Expose
	 * 
	 * @Column(name = "VillageID") private int villageID;
	 */
	@Expose
	@Column(name = "ZoneID")
	private Integer zoneID;
	@Expose
	@Column(name = "AreaID")
	private Integer areaID;
	@Expose
	@Column(name = "ServicePointID")
	private Integer servicePointID;
	@Expose
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false, insertable = false, name = "servicePointID")
	private MasterServicePoint servicePoint;
	
	@Expose
	@Transient
	private String servicePointName;
	

	@OneToOne(mappedBy = "benDemoData")
	private BeneficiaryData benData;

	public BeneficiaryDemographicData() {
	}

	public BeneficiaryDemographicData(Long beneficiaryRegID, int servicePointID, String servicePointName){
		this.beneficiaryRegID = beneficiaryRegID;
		this.servicePointID = servicePointID;
		this.servicePointName = servicePointName;
	}
	public BeneficiaryDemographicData(Long benDemographicsID, Long beneficiaryRegID, Short educationID,
			Short occupationID, Short healthCareWorkerID, Short incomeStatusID, Short communityID,
			Integer preferredLangID, Short religionID, Boolean isPhoto, String photoFilePath, Boolean isBiometric,
			String biometricFilePath, String addressLine1, String addressLine2, String addressLine3,
			String addressLine4, String addressLine5, Integer countryID, Integer stateID, Integer districtID,
			Integer blockID, Integer districtBranchID, Integer cityID, String pinCode, Boolean isAddPresent,
			Boolean isAddPermanent, Boolean deleted, String createdBy, Timestamp createdDate, String modifiedBy,
			Timestamp lastModDate, int subDistrictID, int villageID, Integer zoneID, Integer areaID, Integer servicePointID,
			BeneficiaryData benData) {
		super();
		this.benDemographicsID = benDemographicsID;
		this.beneficiaryRegID = beneficiaryRegID;
		this.educationID = educationID;
		this.occupationID = occupationID;
		this.healthCareWorkerID = healthCareWorkerID;
		this.incomeStatusID = incomeStatusID;
		this.communityID = communityID;
		this.preferredLangID = preferredLangID;
		this.religionID = religionID;
		this.isPhoto = isPhoto;
		this.photoFilePath = photoFilePath;
		this.isBiometric = isBiometric;
		this.biometricFilePath = biometricFilePath;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.addressLine3 = addressLine3;
		this.addressLine4 = addressLine4;
		this.addressLine5 = addressLine5;
		this.countryID = countryID;
		this.stateID = stateID;
		this.districtID = districtID;
		this.blockID = blockID;
		this.districtBranchID = districtBranchID;
		this.cityID = cityID;
		this.pinCode = pinCode;
		this.isAddPresent = isAddPresent;
		this.isAddPermanent = isAddPermanent;
		this.deleted = deleted;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
		/*
		 * this.subDistrictID = subDistrictID; this.villageID = villageID;
		 */
		this.zoneID = zoneID;
		this.areaID = areaID;
		this.servicePointID = servicePointID;
		this.benData = benData;
	}

	public Long getBenDemographicsID() {
		return benDemographicsID;
	}

	public void setBenDemographicsID(Long benDemographicsID) {
		this.benDemographicsID = benDemographicsID;
	}

	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	public Short getEducationID() {
		return educationID;
	}

	public void setEducationID(Short educationID) {
		this.educationID = educationID;
	}

	public Short getOccupationID() {
		return occupationID;
	}

	public void setOccupationID(Short occupationID) {
		this.occupationID = occupationID;
	}

	public Short getHealthCareWorkerID() {
		return healthCareWorkerID;
	}

	public void setHealthCareWorkerID(Short healthCareWorkerID) {
		this.healthCareWorkerID = healthCareWorkerID;
	}

	public Short getIncomeStatusID() {
		return incomeStatusID;
	}

	public void setIncomeStatusID(Short incomeStatusID) {
		this.incomeStatusID = incomeStatusID;
	}

	public Short getCommunityID() {
		return communityID;
	}

	public void setCommunityID(Short communityID) {
		this.communityID = communityID;
	}

	public Integer getPreferredLangID() {
		return preferredLangID;
	}

	public void setPreferredLangID(Integer preferredLangID) {
		this.preferredLangID = preferredLangID;
	}

	public Short getReligionID() {
		return religionID;
	}

	public void setReligionID(Short religionID) {
		this.religionID = religionID;
	}

	public Boolean getIsPhoto() {
		return isPhoto;
	}

	public void setIsPhoto(Boolean isPhoto) {
		this.isPhoto = isPhoto;
	}

	public String getPhotoFilePath() {
		return photoFilePath;
	}

	public void setPhotoFilePath(String photoFilePath) {
		this.photoFilePath = photoFilePath;
	}

	public Boolean getIsBiometric() {
		return isBiometric;
	}

	public void setIsBiometric(Boolean isBiometric) {
		this.isBiometric = isBiometric;
	}

	public String getBiometricFilePath() {
		return biometricFilePath;
	}

	public void setBiometricFilePath(String biometricFilePath) {
		this.biometricFilePath = biometricFilePath;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public String getAddressLine4() {
		return addressLine4;
	}

	public void setAddressLine4(String addressLine4) {
		this.addressLine4 = addressLine4;
	}

	public String getAddressLine5() {
		return addressLine5;
	}

	public void setAddressLine5(String addressLine5) {
		this.addressLine5 = addressLine5;
	}

	public Integer getCountryID() {
		return countryID;
	}

	public void setCountryID(Integer countryID) {
		this.countryID = countryID;
	}

	public Integer getStateID() {
		return stateID;
	}

	public void setStateID(Integer stateID) {
		this.stateID = stateID;
	}

	public Integer getDistrictID() {
		return districtID;
	}

	public void setDistrictID(Integer districtID) {
		this.districtID = districtID;
	}

	public Integer getBlockID() {
		return blockID;
	}

	public void setBlockID(Integer blockID) {
		this.blockID = blockID;
	}

	public Integer getDistrictBranchID() {
		return districtBranchID;
	}

	public void setDistrictBranchID(Integer districtBranchID) {
		this.districtBranchID = districtBranchID;
	}

	public Integer getCityID() {
		return cityID;
	}

	public void setCityID(Integer cityID) {
		this.cityID = cityID;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public Boolean getIsAddPresent() {
		return isAddPresent;
	}

	public void setIsAddPresent(Boolean isAddPresent) {
		this.isAddPresent = isAddPresent;
	}

	public Boolean getIsAddPermanent() {
		return isAddPermanent;
	}

	public void setIsAddPermanent(Boolean isAddPermanent) {
		this.isAddPermanent = isAddPermanent;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
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

	/*
	 * public int getSubDistrictID() { return subDistrictID; }
	 * 
	 * public void setSubDistrictID(int subDistrictID) { this.subDistrictID =
	 * subDistrictID; }
	 * 
	 * public int getVillageID() { return villageID; }
	 * 
	 * public void setVillageID(int villageID) { this.villageID = villageID; }
	 */
	public Integer getZoneID() {
		return zoneID;
	}

	public void setZoneID(Integer zoneID) {
		this.zoneID = zoneID;
	}

	public Integer getAreaID() {
		return areaID;
	}

	public void setAreaID(Integer areaID) {
		this.areaID = areaID;
	}

	public Integer getServicePointID() {
		return servicePointID;
	}

	public void setServicePointID(Integer servicePointID) {
		this.servicePointID = servicePointID;
	}

	public BeneficiaryData getBenData() {
		return benData;
	}

	public void setBenData(BeneficiaryData benData) {
		this.benData = benData;
	}

}
