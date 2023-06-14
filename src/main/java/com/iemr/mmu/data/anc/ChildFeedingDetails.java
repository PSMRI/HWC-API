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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.annotation.sqlInjectionSafe.SQLInjectionSafe;

@Entity
@Table(name = "t_childfeedinghistory")
public class ChildFeedingDetails {

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
	@Column(name = "ChildID")
	private Long childID;

	@Expose
	@Column(name = "BenMotherID")
	private Long benMotherID;

	@Expose
	@Column(name = "TypeOfFeed")
	private @SQLInjectionSafe String typeOfFeed;

	@Expose
	@Column(name = "CompFeedStartAge")
	private @SQLInjectionSafe String compFeedStartAge;

	@Expose
	@Column(name = "NoOfCompFeedPerDay")
	private @SQLInjectionSafe String noOfCompFeedPerDay;

	@Expose
	@Column(name = "FoodIntoleranceStatus")
	private @SQLInjectionSafe String foodIntoleranceStatus;

	@Expose
	@Column(name = "TypeofFoodIntolerance")
	private @SQLInjectionSafe String typeofFoodIntolerance;

	@Expose
	@Column(name = "TypeofFoodIntoleranceId")
	private Long typeofFoodIntoleranceID;
	@Expose
	@Column(name = "Others")
	private String otherFoodIntolerance;

	@Expose
	@Transient
	private String[] typeOfFoodIntolerances;

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

	public Long getChildID() {
		return childID;
	}

	public void setChildID(Long childID) {
		this.childID = childID;
	}

	public Long getBenMotherID() {
		return benMotherID;
	}

	public void setBenMotherID(Long benMotherID) {
		this.benMotherID = benMotherID;
	}

	public String getTypeOfFeed() {
		return typeOfFeed;
	}

	public void setTypeOfFeed(String typeOfFeed) {
		this.typeOfFeed = typeOfFeed;
	}

	public String getCompFeedStartAge() {
		return compFeedStartAge;
	}

	public void setCompFeedStartAge(String compFeedStartAge) {
		this.compFeedStartAge = compFeedStartAge;
	}

	public String getNoOfCompFeedPerDay() {
		return noOfCompFeedPerDay;
	}

	public void setNoOfCompFeedPerDay(String noOfCompFeedPerDay) {
		this.noOfCompFeedPerDay = noOfCompFeedPerDay;
	}

	public String getFoodIntoleranceStatus() {
		return foodIntoleranceStatus;
	}

	public void setFoodIntoleranceStatus(String foodIntoleranceStatus) {
		this.foodIntoleranceStatus = foodIntoleranceStatus;
	}

	public String getTypeofFoodIntolerance() {
		return typeofFoodIntolerance;
	}

	public void setTypeofFoodIntolerance(String typeofFoodIntolerance) {
		this.typeofFoodIntolerance = typeofFoodIntolerance;
	}

	public String[] getTypeOfFoodIntolerances() {
		return typeOfFoodIntolerances;
	}

	public void setTypeOfFoodIntolerances(String[] typeOfFoodIntolerances) {
		this.typeOfFoodIntolerances = typeOfFoodIntolerances;
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

	public Long getTypeofFoodIntoleranceID() {
		return typeofFoodIntoleranceID;
	}

	public void setTypeofFoodIntoleranceID(Long typeofFoodIntoleranceID) {
		this.typeofFoodIntoleranceID = typeofFoodIntoleranceID;
	}

	public String getOtherFoodIntolerance() {
		return otherFoodIntolerance;
	}

	public void setOtherFoodIntolerance(String otherFoodIntolerance) {
		this.otherFoodIntolerance = otherFoodIntolerance;
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

//	public static ChildFeedingDetails getChildFeedingHistory(ChildFeedingDetails childFeedingDetails){
//		
//		ArrayList<Map<String, String>> typeofFoodIntolerancelist = childFeedingDetails.getTypeofFoodIntolerancelist();
//		if(null != typeofFoodIntolerancelist && typeofFoodIntolerancelist.size()>0){
//			for (int i = 0; i <  typeofFoodIntolerancelist.size(); i++){
//				
//					Map<String, String> childFeedingDetail = typeofFoodIntolerancelist.get(i);
//					childFeedingDetails.setTypeofFoodIntoleranceID(Long.parseLong(childFeedingDetail.get("foodID")));
//					childFeedingDetails.setTypeofFoodIntolerance(childFeedingDetail.get("foodType"));			
//			}
//		}
//		return childFeedingDetails;
//		
//	}

	public ChildFeedingDetails(Date createdDate, Long childID, Long benMotherID, String typeOfFeed,
			String compFeedStartAge, String noOfCompFeedPerDay, String foodIntoleranceStatus,
			String typeofFoodIntolerance, String otherFoodIntolerance) {
		super();
		this.captureDate = createdDate;
		this.childID = childID;
		this.benMotherID = benMotherID;
		this.typeOfFeed = typeOfFeed;
		this.compFeedStartAge = compFeedStartAge;
		this.noOfCompFeedPerDay = noOfCompFeedPerDay;
		this.foodIntoleranceStatus = foodIntoleranceStatus;
		this.typeofFoodIntolerance = typeofFoodIntolerance;
		this.otherFoodIntolerance = otherFoodIntolerance;
	}

	public ChildFeedingDetails(Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID, Long childID,
			Long benMotherID, String typeOfFeed, String compFeedStartAge, String noOfCompFeedPerDay,
			String foodIntoleranceStatus, String typeofFoodIntolerance, Long visitCode) {
		super();
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.childID = childID;
		this.benMotherID = benMotherID;
		this.typeOfFeed = typeOfFeed;
		this.compFeedStartAge = compFeedStartAge;
		this.noOfCompFeedPerDay = noOfCompFeedPerDay;
		this.foodIntoleranceStatus = foodIntoleranceStatus;
		this.typeofFoodIntolerance = typeofFoodIntolerance;
		this.visitCode = visitCode;
//		this.typeofFoodIntoleranceID = typeofFoodIntoleranceID;
//		this.otherFoodIntolerance = otherFoodIntolerance;
	}

	public static ChildFeedingDetails getBenFeedingDetails(ArrayList<Object[]> feedingHistoryDetails) {
		ChildFeedingDetails feedingDetails = null;
		if (null != feedingHistoryDetails && feedingHistoryDetails.size() > 0) {
			for (Object[] obj : feedingHistoryDetails) {
				feedingDetails = new ChildFeedingDetails((Long) obj[0], (Long) obj[1], (Integer) obj[2], (Long) obj[3],
						(Long) obj[4], (String) obj[5], (String) obj[6], (String) obj[7], (String) obj[8],
						(String) obj[9], (Long) obj[10]);
			}
		}
		return feedingDetails;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public ChildFeedingDetails() {
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
