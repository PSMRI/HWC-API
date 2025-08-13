/*
* AMRIT – Accessible Medical Records via Integrated Technology 
* Integrated EHR (Electronic Health Records) Solution 
*
* Copyright (C) "Piramal Swasthya Management and Research Institute" 
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.iemr.hwc.data.benFlowStatus;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.iemr.hwc.annotation.sqlInjectionSafe.SQLInjectionSafe;
import com.iemr.hwc.data.login.MasterVan;
import com.iemr.hwc.data.masterdata.registrar.GenderMaster;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/***
 * 
 * @author NE298657
 *
 */
@Entity
@Table(name = "i_ben_flow_outreach")
public class BeneficiaryFlowStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	@Column(name = "ben_flow_id")
	private Long benFlowID;
   
	@Expose
	@Column(name = "beneficiary_reg_id")
	private Long beneficiaryRegID;

	@Expose
	@Column(name = "beneficiary_visit_id")
	private Long benVisitID;

	@Expose
	@Column(name = "beneficiary_visit_code")
	private Long visitCode;

	@Expose
	@Column(name = "visit_reason")
	private String VisitReason;

	@Expose
	@Column(name = "visit_category")
	private String VisitCategory;

	@Expose
	@Column(name = "visit_no")
	private Short benVisitNo;

	@Expose
	@Column(name = "nurse_flag")
	private Short nurseFlag;

	@Expose
	@Column(name = "doctor_flag")
	private Short doctorFlag;

	@Expose
	@Column(name = "pharmacist_flag")
	private Short pharmacist_flag;

	@Expose
	@Column(name = "lab_technician_flag")
	private Short lab_technician_flag;

	@Expose
	@Column(name = "radiologist_flag")
	private Short radiologist_flag;

	@Expose
	@Column(name = "oncologist_flag")
	private Short oncologist_flag;

	@Expose
	@Column(name = "specialist_flag")
	private Short specialist_flag;

	@Expose
	@Column(name = "TC_SpecialistLabFlag")
	private Short tC_SpecialistLabFlag;

	@Expose
	@Column(name = "created_by")
	private String agentId;

	@Expose
	@Column(name = "created_date", insertable = false, updatable = false)
	private Timestamp visitDate;

	@Expose
	@Column(name = "modified_by")
	private String modified_by;

	@Expose
	@Column(name = "modified_date", insertable = false)
	private Timestamp modified_date;

	@Expose
	@Column(name = "ben_name")
	private String benName;

	@Expose
	@Column(name = "deleted", insertable = false)
	private Boolean deleted;

	@Transient
	private String firstName;
	@Transient
	private String lastName;

	@Expose
	@Column(name = "ben_age")
	private String age;

	@Expose
	@Column(name = "ben_age_val")
	private Integer ben_age_val;

	@Expose
	@Column(name = "ben_dob")
	private Timestamp dOB;

	@Expose
	@Column(name = "ben_gender_val")
	private Short genderID;
	@SQLInjectionSafe
	@Expose
	@Column(name = "ben_gender")
	private String genderName;
	@SQLInjectionSafe
	@Expose
	@Column(name = "ben_phone_no")
	private String preferredPhoneNum;
	@SQLInjectionSafe
	@Expose
	@Column(name = "father_name")
	private String fatherName;
//	@Expose
//	@Column(name = "benQuickbloxID")
//	private Long benQuickbloxID;
	@Expose
	@Column(name = "spouse_name")
	private String spouseName;

//	public Long getBenQuickbloxID() {
//		return benQuickbloxID;
//	}
//
//	public void setBenQuickbloxID(Long benQuickbloxID) {
//		this.benQuickbloxID = benQuickbloxID;
//	}
	@SQLInjectionSafe
	@Expose
	@Column(name = "district")
	private String districtName;
	@SQLInjectionSafe
	@Expose
	@Column(name = "servicePoint")
	private String servicePointName;

	@Expose
	@Column(name = "registrationDate")
	private Timestamp registrationDate;

	@Expose
	@Column(name = "visitDate")
	private Timestamp benVisitDate;

	@Expose
	@Column(name = "consultationDate")
	private Timestamp consultationDate;

	@Expose
	@Column(name = "consultantID")
	private Integer consultantID;

	@Expose
	@Column(name = "consultantName")
	private String consultantName;

	@Expose
	@Column(name = "visitSession")
	private Integer visitSession;

	@Expose
	@Column(name = "servicePointID")
	private Integer servicePointID;

	@Expose
	@Column(name = "districtID")
	private Integer districtID;

	@Expose
	@Column(name = "villageID")
	private Integer villageID;

	@Expose
	@Column(name = "vanID")
	private Integer vanID;

	@Expose
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "vanID", referencedColumnName = "vanID", insertable = false, updatable = false)
	private MasterVan masterVan;

	@Expose
	@Column(name = "vanNo")
	private String vanNo;

	@Expose
	@Column(name = "providerServiceMapID")
	private Integer providerServiceMapId;

	@Expose
	@Column(name = "village")
	private String villageName;
	@Expose
	@Column(name = "beneficiary_id")
	private Long beneficiaryID;

	@Expose
	@Column(name = "lab_iteration_cnt")
	private Short labIteration;

	@Expose
	@Column(name = "VanSerialNo")
	private Long vanSerialNo;
	@Expose
	@Column(name = "VehicalNo")
	private String vehicalNo;
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
	@Expose
	@Column(name = "Processed", insertable = false)
	private String processed;

	@Expose
	@Column(name = "BenArrivedFlag", insertable = false)
	private Boolean benArrivedFlag;

	@Expose
	@Column(name = "TCSpecialistUserID")
	private Integer tCSpecialistUserID;
	@Expose
	@Column(name = "TCRequestDate")
	private Timestamp tCRequestDate;

	@Expose
	@Column(name = "referred_visitcode")
	private Long referredVisitCode;
	
	@Expose
	@Column(name = "referred_visit_id")
	private Long referred_visit_id;
	
	
	@Transient
	Boolean is_high_risk;
	 
	
	public Boolean isIs_high_risk() {
		return is_high_risk;
	}

	public void setIs_high_risk(boolean is_high_risk) {
		this.is_high_risk = is_high_risk;
	}
	 

	@Transient
	private I_bendemographics i_bendemographics;
	@Transient
	private List<BenPhoneMaps> benPhoneMaps;
	@Transient
	private String benImage;
	@Transient
	private Integer ageVal;
	@Transient
	private Timestamp serviceDate;
	@Transient
	private String beneficiaryName;
	@Transient
	private Timestamp createdDate;
	@Transient
	private String createdBy;
	@Transient
	private GenderMaster m_gender;
	@Transient
	private Boolean passToNurse;
	@Transient
	private String bloodGroup;
	@Transient
	private String subVisitCategory;
	

	// new variable added for patient app
	@Transient
	private Boolean isMobile;

	public BeneficiaryFlowStatus() {
	}

	public BeneficiaryFlowStatus(Long benFlowID, Long benRegID, Timestamp visitDate, String benName, String age,
			Integer ageVal, Short genderID, String genderName, String villageName, String districtName,
			Long beneficiaryID, String servicePoint, String VisitReason, String VisitCategory, Long benVisitID,
			Timestamp regDate, Timestamp benVisitDate, Long visitCode, Timestamp consultationDate) {
		this.benFlowID = benFlowID;
		this.beneficiaryRegID = benRegID;
		this.serviceDate = benVisitDate;
		this.beneficiaryName = benName;
		this.age = age;
		this.ageVal = ageVal;
		this.genderID = genderID;
		this.genderName = genderName;
		this.villageName = villageName;
		this.districtName = districtName;
		this.createdDate = regDate;
		this.beneficiaryID = beneficiaryID;
		this.servicePointName = servicePoint;
		this.VisitReason = VisitReason;
		this.VisitCategory = VisitCategory;
		this.benVisitID = benVisitID;
		this.visitCode = visitCode;
		this.consultationDate = consultationDate;
		this.bloodGroup = null;

	}

	public BeneficiaryFlowStatus(Long benFlowID, Long benRegID, Long visitCode, Timestamp visitDate, Short benVisitNo,
			String VisitReason, String VisitCategory) {
		this.benFlowID = benFlowID;
		this.beneficiaryRegID = benRegID;
		this.visitCode = visitCode;
		this.benVisitDate = visitDate;
		this.benVisitNo = benVisitNo;
		this.VisitReason = VisitReason;
		this.VisitCategory = VisitCategory;
	}

	public static BeneficiaryFlowStatus getBeneficiaryFlowStatusForLeftPanel(ArrayList<Object[]> objList) {
		BeneficiaryFlowStatus obj = null;
		if (objList != null && objList.size() > 0) {
			for (Object[] objArr : objList) {
				obj = new BeneficiaryFlowStatus((Long) objArr[0], (Long) objArr[1], (Timestamp) objArr[2],
						(String) objArr[3], (String) objArr[4], (Integer) objArr[5], (Short) objArr[6],
						(String) objArr[7], (String) objArr[8], (String) objArr[9], (Long) objArr[10],
						(String) objArr[11], (String) objArr[12], (String) objArr[13], (Long) objArr[14],
						(Timestamp) objArr[15], (Timestamp) objArr[16], (Long) objArr[17], (Timestamp) objArr[18]);
			}
		}
		return obj;
	}

	public static ArrayList<BeneficiaryFlowStatus> getBeneficiaryPastVisitHistory(ArrayList<Object[]> objList) {
		ArrayList<BeneficiaryFlowStatus> visitHistoryList = new ArrayList<>();
		BeneficiaryFlowStatus obj = null;
		if (objList != null && objList.size() > 0) {
			for (Object[] objArr : objList) {
				obj = new BeneficiaryFlowStatus((Long) objArr[0], (Long) objArr[1], (Long) objArr[2],
						(Timestamp) objArr[3], (Short) objArr[4], (String) objArr[5], (String) objArr[6]);
				visitHistoryList.add(obj);
			}
		}
		return visitHistoryList;
	}

	public Integer getConsultantID() {
		return consultantID;
	}

	public void setConsultantID(Integer consultantID) {
		this.consultantID = consultantID;
	}

	public String getConsultantName() {
		return consultantName;
	}

	public void setConsultantName(String consultantName) {
		this.consultantName = consultantName;
	}

	public Timestamp getBenVisitDate() {
		return benVisitDate;
	}

	public void setBenVisitDate(Timestamp benVisitDate) {
		this.benVisitDate = benVisitDate;
	}

	public Timestamp getConsultationDate() {
		return consultationDate;
	}

	public void setConsultationDate(Timestamp consultationDate) {
		this.consultationDate = consultationDate;
	}

	public Integer getVisitSession() {
		return visitSession;
	}

	public void setVisitSession(Integer visitSession) {
		this.visitSession = visitSession;
	}

	public Integer getServicePointID() {
		return servicePointID;
	}

	public void setServicePointID(Integer servicePointID) {
		this.servicePointID = servicePointID;
	}

	public Integer getDistrictID() {
		return districtID;
	}

	public void setDistrictID(Integer districtID) {
		this.districtID = districtID;
	}

	public Integer getVillageID() {
		return villageID;
	}

	public void setVillageID(Integer villageID) {
		this.villageID = villageID;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public String getVanNo() {
		return vanNo;
	}

	public void setVanNo(String vanNo) {
		this.vanNo = vanNo;
	}

	public Integer getProviderServiceMapID() {
		return providerServiceMapId;
	}

	public void setProviderServiceMapID(Integer providerServiceMapId) {
		this.providerServiceMapId = providerServiceMapId;
	}

	public Timestamp getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Timestamp registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Integer getProviderServiceMapId() {
		return providerServiceMapId;
	}

	public void setProviderServiceMapId(Integer providerServiceMapId) {
		this.providerServiceMapId = providerServiceMapId;
	}

	public String getServicePointName() {
		return servicePointName;
	}

	public void setServicePointName(String servicePointName) {
		this.servicePointName = servicePointName;
	}

	public Boolean getPassToNurse() {
		return passToNurse;
	}

	public void setPassToNurse(Boolean passToNurse) {
		this.passToNurse = passToNurse;
	}

	public GenderMaster getM_gender() {
		return m_gender;
	}

	public void setM_gender(GenderMaster m_gender) {
		this.m_gender = m_gender;
	}

	public Integer getAgeVal() {
		return ageVal;
	}

	public void setAgeVal(Integer ageVal) {
		this.ageVal = ageVal;
	}

	public Timestamp getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Timestamp serviceDate) {
		this.serviceDate = serviceDate;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Long getBenFlowID() {
		return benFlowID;
	}

	public void setBenFlowID(Long benFlowID) {
		this.benFlowID = benFlowID;
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

	public Long getBenVisitCode() {
		return visitCode;
	}

	public void setBenVisitCode(Long benVisitCode) {
		this.visitCode = benVisitCode;
	}

	public String getVisitReason() {
		return VisitReason;
	}

	public void setVisitReason(String visitReason) {
		VisitReason = visitReason;
	}

	public String getVisitCategory() {
		return VisitCategory;
	}

	public void setVisitCategory(String visitCategory) {
		VisitCategory = visitCategory;
	}

	public Short getBenVisitNo() {
		return benVisitNo;
	}

	public void setBenVisitNo(Short benVisitNo) {
		this.benVisitNo = benVisitNo;
	}

	public Short getNurseFlag() {
		return nurseFlag;
	}

	public void setNurseFlag(Short nurseFlag) {
		this.nurseFlag = nurseFlag;
	}

	public Short getDoctorFlag() {
		return doctorFlag;
	}

	public void setDoctorFlag(Short doctorFlag) {
		this.doctorFlag = doctorFlag;
	}

	public Short getPharmacist_flag() {
		return pharmacist_flag;
	}

	public void setPharmacist_flag(Short pharmacist_flag) {
		this.pharmacist_flag = pharmacist_flag;
	}

	public Short getLab_technician_flag() {
		return lab_technician_flag;
	}

	public void setLab_technician_flag(Short lab_technician_flag) {
		this.lab_technician_flag = lab_technician_flag;
	}

	public Short getRadiologist_flag() {
		return radiologist_flag;
	}

	public void setRadiologist_flag(Short radiologist_flag) {
		this.radiologist_flag = radiologist_flag;
	}

	public Short getOncologist_flag() {
		return oncologist_flag;
	}

	public void setOncologist_flag(Short oncologist_flag) {
		this.oncologist_flag = oncologist_flag;
	}

	public Short getSpecialist_flag() {
		return specialist_flag;
	}

	public void setSpecialist_flag(Short specialist_flag) {
		this.specialist_flag = specialist_flag;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public Timestamp getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Timestamp visitDate) {
		this.visitDate = visitDate;
	}

	public String getModified_by() {
		return modified_by;
	}

	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}

	public Timestamp getModified_date() {
		return modified_date;
	}

	public void setModified_date(Timestamp modified_date) {
		this.modified_date = modified_date;
	}

	public String getBenName() {
		return benName;
	}

	public void setBenName(String benName) {
		this.benName = benName;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Integer getBen_age_val() {
		return ben_age_val;
	}

	public void setBen_age_val(Integer ben_age_val) {
		this.ben_age_val = ben_age_val;
	}

	public Timestamp getdOB() {
		return dOB;
	}

	public void setdOB(Timestamp dOB) {
		this.dOB = dOB;
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

	public String getPreferredPhoneNum() {
		return preferredPhoneNum;
	}

	public void setPreferredPhoneNum(String preferredPhoneNum) {
		this.preferredPhoneNum = preferredPhoneNum;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getSpouseName() {
		return spouseName;
	}

	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public Long getBeneficiaryID() {
		return beneficiaryID;
	}

	public void setBeneficiaryID(Long beneficiaryID) {
		this.beneficiaryID = beneficiaryID;
	}

	public Short getLabIteration() {
		return labIteration;
	}

	public void setLabIteration(Short labIteration) {
		this.labIteration = labIteration;
	}

	public I_bendemographics getI_bendemographics() {
		return i_bendemographics;
	}

	public void setI_bendemographics(I_bendemographics i_bendemographics) {
		this.i_bendemographics = i_bendemographics;
	}

	public List<BenPhoneMaps> getBenPhoneMaps() {
		return benPhoneMaps;
	}

	public void setBenPhoneMaps(List<BenPhoneMaps> benPhoneMaps) {
		this.benPhoneMaps = benPhoneMaps;
	}

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

	public String getBenImage() {
		return benImage;
	}

	public void setBenImage(String benImage) {
		this.benImage = benImage;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public Short gettC_SpecialistLabFlag() {
		return tC_SpecialistLabFlag;
	}

	public void settC_SpecialistLabFlag(Short tC_SpecialistLabFlag) {
		this.tC_SpecialistLabFlag = tC_SpecialistLabFlag;
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

	public String getProcessed() {
		return processed;
	}

	public void setProcessed(String processed) {
		this.processed = processed;
	}

	public Integer gettCSpecialistUserID() {
		return tCSpecialistUserID;
	}

	public void settCSpecialistUserID(Integer tCSpecialistUserID) {
		this.tCSpecialistUserID = tCSpecialistUserID;
	}

	public Timestamp gettCRequestDate() {
		return tCRequestDate;
	}

	public void settCRequestDate(Timestamp tCRequestDate) {
		this.tCRequestDate = tCRequestDate;
	}

	public Boolean getBenArrivedFlag() {
		return benArrivedFlag;
	}

	public void setBenArrivedFlag(Boolean benArrivedFlag) {
		this.benArrivedFlag = benArrivedFlag;
	}

	public MasterVan getMasterVan() {
		return masterVan;
	}

	public void setMasterVan(MasterVan masterVan) {
		this.masterVan = masterVan;
	}

	public Boolean getIsMobile() {
		return isMobile;
	}

	public void setIsMobile(Boolean isMobile) {
		this.isMobile = isMobile;
	}

	public Long getReferredVisitCode() {
		return referredVisitCode;
	}

	public void setReferredVisitCode(Long referredVisitCode) {
		this.referredVisitCode = referredVisitCode;
	}

	public Long getReferred_visit_id() {
		return referred_visit_id;
	}

	public void setReferred_visit_id(Long referred_visit_id) {
		this.referred_visit_id = referred_visit_id;
	}

	public String getSubVisitCategory() {
		return subVisitCategory;
	}

	public void setSubVisitCategory(String subVisitCategory) {
		this.subVisitCategory = subVisitCategory;
	}
	
	
	
	

}
