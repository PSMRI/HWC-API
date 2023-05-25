package com.iemr.mmu.data.nurse;

import com.iemr.mmu.annotation.sqlInjectionSafe.SQLInjectionSafe;

public class CommonUtilityClass {
	private Long benFlowID;
	private Long beneficiaryID;
	private Integer sessionID;
	private Integer parkingPlaceID;

	private Integer vanID;

	private Long beneficiaryRegID;

	private Integer providerServiceMapID;
	private Long benVisitID;
	private Long visitCode;
	private Integer facilityID;
	private Boolean isSpecialist;
	private Short serviceID;

	private @SQLInjectionSafe String createdBy;

	private Boolean isMobile;
	private Boolean isCovidFlowDone;

	private String firstName;
	private String lastName;
	private Short genderID;
	private String genderName;
	private Integer age;
	private String ageUnits;
	private Integer villageId;
	private String villageName;
	private Integer districtID;
	private String districtName;
	private String specialistDiagnosis;
	private Long prescriptionID;
	private Integer visitCategoryID;
	private Long beneficiaryRegId;
	private Integer vanId;
	private Integer parkingPlaceId;

	private String modifiedBy;

	private Long benRegID;

	private String treatmentsOnSideEffects;
	
	private String subVisitCategory;
	
	

	public String getSubVisitCategory() {
		return subVisitCategory;
	}

	public void setSubVisitCategory(String subVisitCategory) {
		this.subVisitCategory = subVisitCategory;
	}

	public String getTreatmentsOnSideEffects() {
		return treatmentsOnSideEffects;
	}

	public void setTreatmentsOnSideEffects(String treatmentsOnSideEffects) {
		this.treatmentsOnSideEffects = treatmentsOnSideEffects;
	}

	public Long getBenRegID() {
		return benRegID;
	}

	public void setBenRegID(Long benRegID) {
		this.benRegID = benRegID;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	private String authorization;

	public Long getPrescriptionID() {
		return prescriptionID;
	}

	public void setPrescriptionID(Long prescriptionID) {
		this.prescriptionID = prescriptionID;
	}

	public Integer getVisitCategoryID() {
		return visitCategoryID;
	}

	public void setVisitCategoryID(Integer visitCategoryID) {
		this.visitCategoryID = visitCategoryID;
	}

	public String getSpecialistDiagnosis() {
		return specialistDiagnosis;
	}

	public void setSpecialistDiagnosis(String specialistDiagnosis) {
		this.specialistDiagnosis = specialistDiagnosis;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Short getGenderID() {
		return genderID;
	}

	public void setGenderID(Short genderID) {
		this.genderID = genderID;
	}

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAgeUnits() {
		return ageUnits;
	}

	public void setAgeUnits(String ageUnits) {
		this.ageUnits = ageUnits;
	}

	public Integer getVillageId() {
		return villageId;
	}

	public void setVillageId(Integer villageId) {
		this.villageId = villageId;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public Integer getDistrictID() {
		return districtID;
	}

	public void setDistrictID(Integer districtID) {
		this.districtID = districtID;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public Boolean getIsCovidFlowDone() {
		return isCovidFlowDone;
	}

	public void setIsCovidFlowDone(Boolean isCovidFlowDone) {
		this.isCovidFlowDone = isCovidFlowDone;
	}

	public Boolean getIsMobile() {
		return isMobile;
	}

	public void setIsMobile(Boolean isMobile) {
		this.isMobile = isMobile;
	}

	public Long getBenFlowID() {
		return benFlowID;
	}

	public void setBenFlowID(Long benFlowID) {
		this.benFlowID = benFlowID;
	}

	public Long getBeneficiaryID() {
		return beneficiaryID;
	}

	public void setBeneficiaryID(Long beneficiaryID) {
		this.beneficiaryID = beneficiaryID;
	}

	public Integer getSessionID() {
		return sessionID;
	}

	public void setSessionID(Integer sessionID) {
		this.sessionID = sessionID;
	}

	public Integer getParkingPlaceID() {
		return parkingPlaceID;
	}

	public void setParkingPlaceID(Integer parkingPlaceID) {
		this.parkingPlaceID = parkingPlaceID;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
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

	public Integer getFacilityID() {
		return facilityID;
	}

	public void setFacilityID(Integer facilityID) {
		this.facilityID = facilityID;
	}

	public Short getServiceID() {
		return serviceID;
	}

	public void setServiceID(Short serviceID) {
		this.serviceID = serviceID;
	}

	public Boolean getIsSpecialist() {
		return isSpecialist;
	}

	public void setIsSpecialist(Boolean isSpecialist) {
		this.isSpecialist = isSpecialist;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Long getBeneficiaryRegId() {
		return beneficiaryRegId;
	}

	public void setBeneficiaryRegId(Long beneficiaryRegId) {
		this.beneficiaryRegId = beneficiaryRegId;
	}

	public Integer getVanId() {
		return vanId;
	}

	public void setVanId(Integer vanId) {
		this.vanId = vanId;
	}

	public Integer getParkingPlaceId() {
		return parkingPlaceId;
	}

	public void setParkingPlaceId(Integer parkingPlaceId) {
		this.parkingPlaceId = parkingPlaceId;
	}

}
