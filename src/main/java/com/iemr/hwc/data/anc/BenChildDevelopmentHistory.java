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
package com.iemr.hwc.data.anc;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.hwc.annotation.sqlInjectionSafe.SQLInjectionSafe;

@Entity
@Table(name = "t_DevelopmentHistory")
public class BenChildDevelopmentHistory {

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
	@Column(name = "GrossMotorMilestone")
	private @SQLInjectionSafe String grossMotorMilestone;

	@Expose
	@Column(name = "IsGMMAttained")
	private Boolean isGrossMotorMilestones;

	@Expose
	@Column(name = "FineMotorMilestone")
	private @SQLInjectionSafe String fineMotorMilestone;

	@Expose
	@Column(name = "IsFMMAttained")
	private Boolean isFineMotorMilestones;

	@Expose
	@Column(name = "SocialMilestone")
	private @SQLInjectionSafe String socialMilestone;

	@Expose
	@Column(name = "IsSMAttained")
	private Boolean isSocialMilestones;

	@Expose
	@Column(name = "LanguageMilestone")
	private @SQLInjectionSafe String languageMilestone;

	@Expose
	@Column(name = "IsLMAttained")
	private Boolean isLanguageMilestones;

	@Expose
	@Column(name = "DevelopmentProblem")
	private @SQLInjectionSafe String developmentProblem;

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

	@Transient
	private List<String> grossMotorMilestones;

	@Transient
	private List<String> fineMotorMilestones;

	@Transient
	private List<String> socialMilestones;

	@Transient
	private List<String> languageMilestones;

	@Transient
	private List<String> developmentProblems;

	@Transient
	private Date captureDate;

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

	public String getGrossMotorMilestone() {
		return grossMotorMilestone;
	}

	public void setGrossMotorMilestone(String grossMotorMilestone) {
		this.grossMotorMilestone = grossMotorMilestone;
	}

	public String getFineMotorMilestone() {
		return fineMotorMilestone;
	}

	public void setFineMotorMilestone(String fineMotorMilestone) {
		this.fineMotorMilestone = fineMotorMilestone;
	}

	public String getSocialMilestone() {
		return socialMilestone;
	}

	public void setSocialMilestone(String socialMilestone) {
		this.socialMilestone = socialMilestone;
	}

	public String getLanguageMilestone() {
		return languageMilestone;
	}

	public void setLanguageMilestone(String languageMilestone) {
		this.languageMilestone = languageMilestone;
	}

	public Boolean getIsGrossMotorMilestones() {
		return isGrossMotorMilestones;
	}

	public void setIsGrossMotorMilestones(Boolean isGrossMotorMilestones) {
		this.isGrossMotorMilestones = isGrossMotorMilestones;
	}

	public Boolean getIsFineMotorMilestones() {
		return isFineMotorMilestones;
	}

	public void setIsFineMotorMilestones(Boolean isFineMotorMilestones) {
		this.isFineMotorMilestones = isFineMotorMilestones;
	}

	public Boolean getIsSocialMilestones() {
		return isSocialMilestones;
	}

	public void setIsSocialMilestones(Boolean isSocialMilestones) {
		this.isSocialMilestones = isSocialMilestones;
	}

	public Boolean getIsLanguageMilestones() {
		return isLanguageMilestones;
	}

	public void setIsLanguageMilestones(Boolean isLanguageMilestones) {
		this.isLanguageMilestones = isLanguageMilestones;
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

	public String getDevelopmentProblem() {
		return developmentProblem;
	}

	public void setDevelopmentProblem(String developmentProblem) {
		this.developmentProblem = developmentProblem;
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

	public List<String> getGrossMotorMilestones() {
		return grossMotorMilestones;
	}

	public void setGrossMotorMilestones(List<String> grossMotorMilestones) {
		this.grossMotorMilestones = grossMotorMilestones;
	}

	public List<String> getFineMotorMilestones() {
		return fineMotorMilestones;
	}

	public void setFineMotorMilestones(List<String> fineMotorMilestones) {
		this.fineMotorMilestones = fineMotorMilestones;
	}

	public List<String> getSocialMilestones() {
		return socialMilestones;
	}

	public void setSocialMilestones(List<String> socialMilestones) {
		this.socialMilestones = socialMilestones;
	}

	public List<String> getLanguageMilestones() {
		return languageMilestones;
	}

	public void setLanguageMilestones(List<String> languageMilestones) {
		this.languageMilestones = languageMilestones;
	}

	public List<String> getDevelopmentProblems() {
		return developmentProblems;
	}

	public void setDevelopmentProblems(List<String> developmentProblems) {
		this.developmentProblems = developmentProblems;
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

//	public static BenChildDevelopmentHistory getDevelopmentHistory(BenChildDevelopmentHistory benChildDevelopmentHistory){
//		
//		List<String> grossMotorMilestones = benChildDevelopmentHistory.getGrossMotorMilestones();
//		String grossMotorMilestone = "";
//		if(null != grossMotorMilestones && grossMotorMilestones.size()>0){
//			for(String gmm :grossMotorMilestones){
//				grossMotorMilestone += gmm +",";
//			}
//		}
//		benChildDevelopmentHistory.setGrossMotorMilestone(grossMotorMilestone);
//		
//		List<String> fineMotorMilestones = benChildDevelopmentHistory.getFineMotorMilestones();
//		String fineMotorMilestone = "";
//		if(null != fineMotorMilestones && fineMotorMilestones.size()>0){
//			for(String fmm :fineMotorMilestones){
//				fineMotorMilestone += fmm +",";
//			}
//		}
//		benChildDevelopmentHistory.setFineMotorMilestone(fineMotorMilestone);
//		
//		List<String> socialMilestones = benChildDevelopmentHistory.getSocialMilestones();
//		String socialMilestone = "";
//		if(null != socialMilestones && socialMilestones.size()>0){
//			for(String sm :socialMilestones){
//				socialMilestone += sm +",";
//			}
//		}
//		benChildDevelopmentHistory.setSocialMilestone(socialMilestone);
//		
//		List<String> languageMilestones = benChildDevelopmentHistory.getLanguageMilestones();
//		String languageMilestone = "";
//		if(null != languageMilestones && languageMilestones.size()>0){
//			for(String lm :languageMilestones){
//				languageMilestone += lm +",";
//			}
//		}
//		benChildDevelopmentHistory.setLanguageMilestone(languageMilestone);
//		
//		List<String> developmentProblems = benChildDevelopmentHistory.getDevelopmentProblems();
//		String developmentProblem = "";
//		if(null != developmentProblems && developmentProblems.size()>0){
//			for(String dp :developmentProblems){
//				developmentProblem += dp +",";
//			}
//		}
//		benChildDevelopmentHistory.setDevelopmentProblem(developmentProblem);
//		return benChildDevelopmentHistory;
//	}

	public BenChildDevelopmentHistory(Date createdDate, String grossMotorMilestone, Boolean isGrossMotorMilestones,
			String fineMotorMilestone, Boolean isFineMotorMilestones, String socialMilestone,
			Boolean isSocialMilestones, String languageMilestone, Boolean isLanguageMilestones,
			String developmentProblem) {
		super();
		this.captureDate = createdDate;
		this.grossMotorMilestone = grossMotorMilestone;
		this.isGrossMotorMilestones = isGrossMotorMilestones;
		this.fineMotorMilestone = fineMotorMilestone;
		this.isFineMotorMilestones = isFineMotorMilestones;
		this.socialMilestone = socialMilestone;
		this.isSocialMilestones = isSocialMilestones;
		this.languageMilestone = languageMilestone;
		this.isLanguageMilestones = isLanguageMilestones;
		this.developmentProblem = developmentProblem;
	}

	public BenChildDevelopmentHistory(Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID,
			String grossMotorMilestone, Boolean isGrossMotorMilestones, String fineMotorMilestone,
			Boolean isFineMotorMilestones, String socialMilestone, Boolean isSocialMilestones, String languageMilestone,
			Boolean isLanguageMilestones, String developmentProblem, Long visitCode) {
		super();
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.grossMotorMilestone = grossMotorMilestone;
		this.isGrossMotorMilestones = isGrossMotorMilestones;
		this.fineMotorMilestone = fineMotorMilestone;
		this.isFineMotorMilestones = isFineMotorMilestones;
		this.socialMilestone = socialMilestone;
		this.isSocialMilestones = isSocialMilestones;
		this.languageMilestone = languageMilestone;
		this.isLanguageMilestones = isLanguageMilestones;
		this.developmentProblem = developmentProblem;
		this.visitCode = visitCode;
	}

	public static BenChildDevelopmentHistory getBenChildDevelopmentDetails(
			ArrayList<Object[]> developmentHistoryDetails) {
		BenChildDevelopmentHistory developmentDetails = null;
		if (null != developmentHistoryDetails && developmentHistoryDetails.size() > 0) {
			for (Object[] obj : developmentHistoryDetails) {
				developmentDetails = new BenChildDevelopmentHistory((Long) obj[0], (Long) obj[1], (Integer) obj[2],
						(String) obj[3], (Boolean) obj[4], (String) obj[5], (Boolean) obj[6], (String) obj[7],
						(Boolean) obj[8], (String) obj[9], (Boolean) obj[10], (String) obj[11], (Long) obj[12]);

				String grossMotorMilestones = developmentDetails.getGrossMotorMilestone();
				developmentDetails.setGrossMotorMilestones(Arrays.asList(grossMotorMilestones.split(",")));

				String fineMotorMilestones = developmentDetails.getFineMotorMilestone();
				developmentDetails.setFineMotorMilestones(Arrays.asList(fineMotorMilestones.split(",")));

				String socialMilestones = developmentDetails.getSocialMilestone();
				developmentDetails.setSocialMilestones(Arrays.asList(socialMilestones.split(",")));

				String languageMilestones = developmentDetails.getLanguageMilestone();
				developmentDetails.setLanguageMilestones(Arrays.asList(languageMilestones.split(",")));

				String developmentProblems = developmentDetails.getDevelopmentProblem();
				developmentDetails.setDevelopmentProblems(Arrays.asList(developmentProblems.split(",")));
			}
		}
		return developmentDetails;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public BenChildDevelopmentHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

}
