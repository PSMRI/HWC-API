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
package com.iemr.mmu.data.anc;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.annotation.sqlInjectionSafe.SQLInjectionSafe;
import com.iemr.mmu.service.anc.Utility;

@Entity
@Table(name = "t_BenMedHistory")
public class BenMedHistory {

	@Id
	@GeneratedValue
	@Expose
	@Column(name = "BenMedHistoryID")
	private Long benMedHistoryID;
	
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
	@Column(name = "YearofIllness")
	private Timestamp yearofIllness;

	@Expose
	@Column(name = "IllnessTypeID")
	private Integer illnessTypeID;

	@Expose
	@Column(name = "IllnessType")
	private @SQLInjectionSafe String illnessType;

	@Expose
	@Column(name = "OtherIllnessType")
	private @SQLInjectionSafe String otherIllnessType;

	@Expose
	@Column(name = "SurgeryID")
	private Integer surgeryID;

	@Expose
	@Column(name = "SurgeryType")
	private @SQLInjectionSafe String surgeryType;

	@Expose
	@Column(name = "YearofSurgery")
	private Timestamp yearofSurgery;

	@Expose
	@Column(name = "OtherSurgeryType")
	private @SQLInjectionSafe String otherSurgeryType;

	@Expose
	@Column(name = "DrugComplianceID")
	private Short drugComplianceID;

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

	public BenMedHistory() {
	}

	@Transient
	private Date Year_Of_Illness;
	@Transient
	private Date Year_Of_Surgery;
	@Transient
	private @SQLInjectionSafe String Illness_Type;
	@Transient
	private @SQLInjectionSafe String Surgery_Type;
	@Transient
	private @SQLInjectionSafe String Other_Illness_Type;
	@Transient
	private @SQLInjectionSafe String Other_Surgery_Type;

	@Transient
	private Date captureDate;

	public BenMedHistory(Date createdDate, String illnessType, String otherIllnessType, Date yearOfIllnessTmp,
			String surgeryType, String otherSurgeryType, Date yearOfSurgeryTmp) {
		this.captureDate = createdDate;
		this.Illness_Type = illnessType;
		this.Other_Illness_Type = otherIllnessType;
		this.Year_Of_Illness = yearOfIllnessTmp;
		this.Surgery_Type = surgeryType;
		this.Other_Surgery_Type = otherSurgeryType;
		this.Year_Of_Surgery = yearOfSurgeryTmp;

	}

	public BenMedHistory(Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID) {
		super();
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
	}

	public BenMedHistory(Timestamp yearofIllness, Integer illnessTypeID, String illnessType, String otherIllnessType,
			Integer surgeryID, String surgeryType, Timestamp yearofSurgery, String otherSurgeryType,
			Timestamp createdDate, Long visitCode) {
		super();
		this.yearofIllness = yearofIllness;
		this.illnessTypeID = illnessTypeID;
		this.illnessType = illnessType;
		this.otherIllnessType = otherIllnessType;
		this.surgeryID = surgeryID;
		this.surgeryType = surgeryType;
		this.yearofSurgery = yearofSurgery;
		this.otherSurgeryType = otherSurgeryType;
		this.createdDate = createdDate;
		this.visitCode = visitCode;
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

	public Timestamp getYearofIllness() {
		return yearofIllness;
	}

	public void setYearofIllness(Timestamp yearofIllness) {
		this.yearofIllness = yearofIllness;
	}

	public Integer getIllnessTypeID() {
		return illnessTypeID;
	}

	public void setIllnessTypeID(Integer illnessTypeID) {
		this.illnessTypeID = illnessTypeID;
	}

	public String getIllnessType() {
		return illnessType;
	}

	public void setIllnessType(String illnessType) {
		this.illnessType = illnessType;
	}

	public Integer getSurgeryID() {
		return surgeryID;
	}

	public void setSurgeryID(Integer surgeryID) {
		this.surgeryID = surgeryID;
	}

	public String getSurgeryType() {
		return surgeryType;
	}

	public void setSurgeryType(String surgeryType) {
		this.surgeryType = surgeryType;
	}

	public Timestamp getYearofSurgery() {
		return yearofSurgery;
	}

	public void setYearofSurgery(Timestamp yearofSurgery) {
		this.yearofSurgery = yearofSurgery;
	}

	public Short getDrugComplianceID() {
		return drugComplianceID;
	}

	public void setDrugComplianceID(Short drugComplianceID) {
		this.drugComplianceID = drugComplianceID;
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

	public Long getBenMedHistoryID() {
		return benMedHistoryID;
	}

	public String getOtherIllnessType() {
		return otherIllnessType;
	}

	public void setOtherIllnessType(String otherIllnessType) {
		this.otherIllnessType = otherIllnessType;
	}

	public String getOtherSurgeryType() {
		return otherSurgeryType;
	}

	public void setOtherSurgeryType(String otherSurgeryType) {
		this.otherSurgeryType = otherSurgeryType;
	}

	@Transient
	@Expose
	private ArrayList<Map<String, Object>> pastIllness;

	@Transient
	@Expose
	private ArrayList<Map<String, Object>> pastSurgery;

	public ArrayList<Map<String, Object>> getPastIllness() {
		return pastIllness;
	}

	public void setPastIllness(ArrayList<Map<String, Object>> pastIllness) {
		this.pastIllness = pastIllness;
	}

	public ArrayList<Map<String, Object>> getPastSurgery() {
		return pastSurgery;
	}

	public void setPastSurgery(ArrayList<Map<String, Object>> pastSurgery) {
		this.pastSurgery = pastSurgery;
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

	public ArrayList<BenMedHistory> getBenPastHistory() {
		int maxMedHistorySize = 0;
		if (pastIllness.size() > pastSurgery.size()) {
			maxMedHistorySize = pastIllness.size();
		} else {
			maxMedHistorySize = pastSurgery.size();
		}
		ArrayList<BenMedHistory> medHistoryList = new ArrayList<BenMedHistory>();
		for (int i = 0; i < maxMedHistorySize; i++) {
			BenMedHistory benMedHistory = new BenMedHistory();

			benMedHistory.setBeneficiaryRegID(beneficiaryRegID);
			benMedHistory.setBenVisitID(benVisitID);
			benMedHistory.setVisitCode(visitCode);
			benMedHistory.setProviderServiceMapID(providerServiceMapID);
			benMedHistory.setVanID(vanID);
			benMedHistory.setParkingPlaceID(parkingPlaceID);
			benMedHistory.setCreatedBy(createdBy);

			if (null != pastIllness && pastIllness.size() > i) {
				Map<String, Object> illness = (Map<String, Object>) pastIllness.get(i);

				if (null != illness) {
					if (null != illness.get("illnessTypeID")) {
						benMedHistory.setIllnessTypeID(Integer.parseInt(illness.get("illnessTypeID").toString()));
					}
					if (null != illness.get("illnessType")) {
						benMedHistory.setIllnessType(illness.get("illnessType").toString());
					}
					if (null != illness.get("otherIllnessType")) {
						benMedHistory.setOtherIllnessType(illness.get("otherIllnessType").toString());
					}
				}

				String timePeriodUnit = (String) illness.get("timePeriodUnit");
				Integer timePeriodAgo = 0;
				if (null != illness.get("timePeriodAgo")) {
					timePeriodAgo = Integer.parseInt(illness.get("timePeriodAgo").toString());
				}
				benMedHistory.setYearofIllness(Utility.convertToDateFormat(timePeriodUnit, timePeriodAgo));
			}
			if (null != pastSurgery && pastSurgery.size() > i) {
				String surgeryTimePeriodUnit = null;
				Integer surgeryTimePeriodAgo = 0;

				Map<String, Object> surgery = (Map<String, Object>) pastSurgery.get(i);
				if (null != surgery) {
					if (null != surgery.get("surgeryID")) {
						benMedHistory.setSurgeryID(Integer.parseInt(surgery.get("surgeryID").toString()));
					}
					if (null != surgery.get("surgeryType")) {
						benMedHistory.setSurgeryType(surgery.get("surgeryType").toString());
					}

					if (null != surgery.get("otherSurgeryType")) {
						benMedHistory.setOtherSurgeryType(surgery.get("otherSurgeryType").toString());
					}

					surgeryTimePeriodUnit = (String) surgery.get("timePeriodUnit");

					if (null != surgery.get("timePeriodAgo")) {
						surgeryTimePeriodAgo = Integer.parseInt(surgery.get("timePeriodAgo").toString());
					}
				}

//				String surgeryTimePeriodUnit = (String) surgery.get("timePeriodUnit");
//				Integer surgeryTimePeriodAgo = 0;
//				if (null != surgery.get("timePeriodAgo")) {
//					surgeryTimePeriodAgo = Integer.parseInt(surgery.get("timePeriodAgo").toString());
//				}
				benMedHistory
						.setYearofSurgery(Utility.convertToDateFormat(surgeryTimePeriodUnit, surgeryTimePeriodAgo));
			}
			if (benMedHistory.getIllnessTypeID() != null || benMedHistory.getSurgeryID() != null)
				medHistoryList.add(benMedHistory);
		}

		return medHistoryList;
	}

	public BenMedHistory getBenPastHistory(ArrayList<Object[]> pastHistory) {

		BenMedHistory benHistory = null;
		if (null != pastHistory && pastHistory.size() > 0) {
			Object[] obj1 = pastHistory.get(0);
			benHistory = new BenMedHistory((Long) obj1[0], (Long) obj1[1], (Integer) obj1[2]);

			ArrayList<Map<String, Object>> pastIllness = new ArrayList<Map<String, Object>>();
			ArrayList<Map<String, Object>> pastSurgery = new ArrayList<Map<String, Object>>();
			for (Object[] obj : pastHistory) {
				BenMedHistory benMedHistory = new BenMedHistory((Timestamp) obj[3], (Integer) obj[4], (String) obj[5],
						(String) obj[6], (Integer) obj[7], (String) obj[8], (Timestamp) obj[9], (String) obj[10],
						(Timestamp) obj1[11], (Long) obj[12]);

				Map<String, Object> illness = new HashMap<String, Object>();
				Map<String, Object> timePeriod = null;
				if (null != benMedHistory.getIllnessTypeID() && benMedHistory.getIllnessTypeID() > 0) {
					illness.put("illnessTypeID", benMedHistory.getIllnessTypeID());
					illness.put("illnessType", benMedHistory.getIllnessType());
					illness.put("otherIllnessType", benMedHistory.getOtherIllnessType());

					timePeriod = Utility.convertTimeToWords(benMedHistory.getYearofIllness(),
							benMedHistory.getCreatedDate());

					illness.put("timePeriodAgo", timePeriod.get("timePeriodAgo"));
					illness.put("timePeriodUnit", timePeriod.get("timePeriodUnit"));
					pastIllness.add(illness);
				}

				if (null != benMedHistory.getSurgeryID() && benMedHistory.getSurgeryID() > 0) {
					Map<String, Object> surgery = new HashMap<String, Object>();

					surgery.put("surgeryID", benMedHistory.getSurgeryID());
					surgery.put("surgeryType", benMedHistory.getSurgeryType());
					surgery.put("otherSurgeryType", benMedHistory.getOtherSurgeryType());

					timePeriod = Utility.convertTimeToWords(benMedHistory.getYearofSurgery(),
							benMedHistory.getCreatedDate());

					surgery.put("timePeriodAgo", timePeriod.get("timePeriodAgo"));
					surgery.put("timePeriodUnit", timePeriod.get("timePeriodUnit"));

					pastSurgery.add(surgery);
				}
			}
			benHistory.setPastIllness(pastIllness);
			benHistory.setPastSurgery(pastSurgery);
		}
		return benHistory;

	}

	public void setBenMedHistoryID(Long benMedHistoryID) {
		this.benMedHistoryID = benMedHistoryID;
	}

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public Date getYear_Of_Illness() {
		return Year_Of_Illness;
	}

	public void setYear_Of_Illness(Date year_Of_Illness) {
		Year_Of_Illness = year_Of_Illness;
	}

	public Date getYear_Of_Surgery() {
		return Year_Of_Surgery;
	}

	public void setYear_Of_Surgery(Date year_Of_Surgery) {
		Year_Of_Surgery = year_Of_Surgery;
	}

	public String getIllness_Type() {
		return Illness_Type;
	}

	public void setIllness_Type(String illness_Type) {
		Illness_Type = illness_Type;
	}

	public String getSurgery_Type() {
		return Surgery_Type;
	}

	public void setSurgery_Type(String surgery_Type) {
		Surgery_Type = surgery_Type;
	}

	public String getOther_Illness_Type() {
		return Other_Illness_Type;
	}

	public void setOther_Illness_Type(String other_Illness_Type) {
		Other_Illness_Type = other_Illness_Type;
	}

	public String getOther_Surgery_Type() {
		return Other_Surgery_Type;
	}

	public void setOther_Surgery_Type(String other_Surgery_Type) {
		Other_Surgery_Type = other_Surgery_Type;
	}

	public Date getCaptureDate() {
		return captureDate;
	}

	public void setCaptureDate(Date captureDate) {
		this.captureDate = captureDate;
	}

}
