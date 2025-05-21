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
package com.iemr.hwc.data.quickConsultation;

import java.sql.Timestamp;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.iemr.hwc.annotation.sqlInjectionSafe.SQLInjectionSafe;

@Entity
@Table(name = "t_benchiefcomplaint")
public class BenChiefComplaint {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	@Column(name = "ID")
	private Long benChiefComplaintID;
	
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
	@Column(name = "ChiefComplaintID")
	private Integer chiefComplaintID;
	@Expose
	@Column(name = "ChiefComplaint")
	private @SQLInjectionSafe String chiefComplaint;

	@Expose
	@Column(name = "SCTCode")
	private @SQLInjectionSafe String conceptID;

	@Expose
	@Column(name = "Duration")
	private Integer duration;
	@Expose
	@Column(name = "UnitOfDuration")
	private @SQLInjectionSafe String unitOfDuration;
	@Expose
	@Column(name = "Description")
	private @SQLInjectionSafe String description;

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
	@Column(name = "VanID")
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

	public BenChiefComplaint() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BenChiefComplaint(Long benChiefComplaintID, Long beneficiaryRegID, Long benVisitID,
			Integer providerServiceMapID, Integer chiefComplaintID, String chiefComplaint, Integer duration,
			String unitOfDuration, String description, Long visitCode, String conceptID) {
		super();
		this.benChiefComplaintID = benChiefComplaintID;
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.visitCode = visitCode;
		this.providerServiceMapID = providerServiceMapID;
		this.chiefComplaintID = chiefComplaintID;
		this.chiefComplaint = chiefComplaint;
		this.duration = duration;
		this.unitOfDuration = unitOfDuration;
		this.description = description;
		this.conceptID = conceptID;
	}

	public static ArrayList<BenChiefComplaint> getBenChiefComplaints(ArrayList<Object[]> resList) {
		ArrayList<BenChiefComplaint> resArray = new ArrayList<BenChiefComplaint>();
		BenChiefComplaint cOBJ = null;
		for (Object[] obj : resList) {
			cOBJ = new BenChiefComplaint((Long) obj[0], (Long) obj[1], (Long) obj[2], (Integer) obj[3],
					(Integer) obj[4], (String) obj[5], (Integer) obj[6], (String) obj[7], (String) obj[8],
					(Long) obj[9], (String) obj[10]);
			resArray.add(cOBJ);
		}
		return resArray;
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

	public Integer getChiefComplaintID() {
		return chiefComplaintID;
	}

	public void setChiefComplaintID(Integer chiefComplaintID) {
		this.chiefComplaintID = chiefComplaintID;
	}

	public String getChiefComplaint() {
		return chiefComplaint;
	}

	public void setChiefComplaint(String chiefComplaint) {
		this.chiefComplaint = chiefComplaint;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getUnitOfDuration() {
		return unitOfDuration;
	}

	public void setUnitOfDuration(String unitOfDuration) {
		this.unitOfDuration = unitOfDuration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Long getBenChiefComplaintID() {
		return benChiefComplaintID;
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

	public void setBenChiefComplaintID(Long benChiefComplaintID) {
		this.benChiefComplaintID = benChiefComplaintID;
	}

	public String getConceptID() {
		return conceptID;
	}

	public void setConceptID(String conceptID) {
		this.conceptID = conceptID;
	}
private static final Logger logger = LoggerFactory.getLogger(BenChiefComplaint.class);


	public static ArrayList<BenChiefComplaint> getBenChiefComplaintList(JsonObject emrgCasesheet) {


		ArrayList<BenChiefComplaint> resArray = new ArrayList<>();
		BenChiefComplaint benChiefComplaint = null;
		
		if (emrgCasesheet.has("chiefComplaintList") && !emrgCasesheet.get("chiefComplaintList").isJsonNull()
				&& emrgCasesheet.get("chiefComplaintList").isJsonArray()) {
			for (JsonElement csobj : emrgCasesheet.getAsJsonArray("chiefComplaintList")) {
				benChiefComplaint = new BenChiefComplaint();

				// if (emrgCasesheet.has("benVisitID") && !emrgCasesheet.get("benVisitID").isJsonNull())
				// 	benChiefComplaint.setBenVisitID(emrgCasesheet.get("benVisitID").getAsLong());

				// if (emrgCasesheet.has("visitCode") && !emrgCasesheet.get("visitCode").isJsonNull())
				// 	benChiefComplaint.setVisitCode(emrgCasesheet.get("visitCode").getAsLong());

				if (emrgCasesheet.has("beneficiaryRegID") && !emrgCasesheet.get("beneficiaryRegID").isJsonNull())
					benChiefComplaint.setBeneficiaryRegID(emrgCasesheet.get("beneficiaryRegID").getAsLong());

				if (emrgCasesheet.has("providerServiceMapID")
						&& !emrgCasesheet.get("providerServiceMapID").isJsonNull())
					benChiefComplaint.setProviderServiceMapID(emrgCasesheet.get("providerServiceMapID").getAsInt());

				JsonObject obj = csobj.getAsJsonObject();
				
logger.info("Visit id="+obj.get("benVisitID"));
logger.info("Visit code="+obj.get("visitCode"));

				if (obj.has("benVisitID") && !obj.get("benVisitID").isJsonNull())
            		benChiefComplaint.setBenVisitID(obj.get("benVisitID").getAsLong());

        		if (obj.has("visitCode") && !obj.get("visitCode").isJsonNull())
            		benChiefComplaint.setVisitCode(obj.get("visitCode").getAsLong());

				if (obj.has("chiefComplaintID") && !obj.get("chiefComplaintID").isJsonNull())
					benChiefComplaint.setChiefComplaintID(obj.get("chiefComplaintID").getAsInt());

				if (obj.has("chiefComplaint") && !obj.get("chiefComplaint").isJsonNull())
					benChiefComplaint.setChiefComplaint(obj.get("chiefComplaint").getAsString());

				if (obj.has("duration") && !obj.get("duration").isJsonNull())
					benChiefComplaint.setDuration(obj.get("duration").getAsInt());

				if (obj.has("unitOfDuration") && !obj.get("unitOfDuration").isJsonNull())
					benChiefComplaint.setUnitOfDuration(obj.get("unitOfDuration").getAsString());

				if (obj.has("description") && !obj.get("description").isJsonNull())
					benChiefComplaint.setDescription(obj.get("description").getAsString());

				if (obj.has("conceptID") && !obj.get("conceptID").isJsonNull())
					benChiefComplaint.setConceptID(obj.get("conceptID").getAsString());

				if (obj.has("vanID") && !obj.get("vanID").isJsonNull())
					benChiefComplaint.setVanID(obj.get("vanID").getAsInt());

				if (obj.has("parkingPlaceID") && !obj.get("parkingPlaceID").isJsonNull())
					benChiefComplaint.setParkingPlaceID(obj.get("parkingPlaceID").getAsInt());

				/*
				 * if (emrgCasesheet.has("description") &&
				 * !emrgCasesheet.get("description").isJsonNull())
				 * benChiefComplaint.setDescription(emrgCasesheet.get("description").getAsString
				 * ());
				 */

				if (emrgCasesheet.has("createdBy") && !emrgCasesheet.get("createdBy").isJsonNull())
					benChiefComplaint.setCreatedBy(emrgCasesheet.get("createdBy").getAsString());

				resArray.add(benChiefComplaint);
			}
		}

		return resArray;
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

}
