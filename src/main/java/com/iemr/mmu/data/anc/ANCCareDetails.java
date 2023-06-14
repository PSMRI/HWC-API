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

@Entity
@Table(name = "t_anccare")
public class ANCCareDetails {

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
	@Column(name = "VisitNo")
	private Short visitNo;

	@Expose
	@Column(name = "ComolaintType")
	private String comolaintType;

	@Expose
	@Column(name = "Duration")
	private String duration;

	@Expose
	@Column(name = "Description")
	private String description;

	@Expose
	@Column(name = "LastMenstrualPeriod_LMP")
	private Date lastMenstrualPeriod_LMP;

	@Expose
	@Column(name = "GestationalAgeOrPeriodofAmenorrhea_POA")
	private Short gestationalAgeOrPeriodofAmenorrhea_POA;

	@Expose
	@Column(name = "TrimesterNumber")
	private Short trimesterNumber;

	@Expose
	@Column(name = "ExpectedDateofDelivery")
	private Date expectedDateofDelivery;

	@Expose
	@Column(name = "PrimiGravida")
	private Boolean primiGravida;

	@Expose
	@Column(name = "Gravida_G")
	private Short gravida_G;

	@Expose
	@Column(name = "TermDeliveries_T")
	private Short termDeliveries_T;

	@Expose
	@Column(name = "PretermDeliveries_P")
	private Short pretermDeliveries_P;

	@Expose
	@Column(name = "Abortions_A")
	private Short abortions_A;

	@Expose
	@Column(name = "Livebirths_L")
	private Short livebirths_L;

	@Expose
	@Column(name = "BloodGroup")
	private String bloodGroup;

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

	@Transient
	private String lmpDate;

	@Transient
	private String expDelDt;

	@Expose
	@Column(name = "VanSerialNo")
	private Long vanSerialNo;

	@Expose
	@Column(name = "vanID")
	private Integer vanID;

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
	@Column(name = "stillBirth")
	private Integer stillBirth;

	@Expose
	@Column(name = "Para_P")
	private Short para;

	public Short getPara() {
		return para;
	}

	public void setPara(Short para) {
		this.para = para;
	}

	public ANCCareDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ANCCareDetails(Long iD, Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID, Long visitCode,
			Short visitNo, String comolaintType, String duration, String description, Date lastMenstrualPeriod_LMP,
			Short gestationalAgeOrPeriodofAmenorrhea_POA, Short trimesterNumber, Date expectedDateofDelivery,
			Boolean primiGravida, Short gravida_G, Short termDeliveries_T, Short pretermDeliveries_P, Short abortions_A,
			Short livebirths_L, String bloodGroup, Integer stillBirth) {
		super();
		ID = iD;
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.visitCode = visitCode;
		this.visitNo = visitNo;
		this.comolaintType = comolaintType;
		this.duration = duration;
		this.description = description;
		this.lastMenstrualPeriod_LMP = lastMenstrualPeriod_LMP;
		this.gestationalAgeOrPeriodofAmenorrhea_POA = gestationalAgeOrPeriodofAmenorrhea_POA;
		this.trimesterNumber = trimesterNumber;
		this.expectedDateofDelivery = expectedDateofDelivery;
		this.primiGravida = primiGravida;
		this.gravida_G = gravida_G;
		this.termDeliveries_T = termDeliveries_T;
		this.pretermDeliveries_P = pretermDeliveries_P;
		this.abortions_A = abortions_A;
		this.livebirths_L = livebirths_L;
		this.bloodGroup = bloodGroup;
		this.stillBirth = stillBirth;
	}

	public ANCCareDetails(Long iD, Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID, Long visitCode,
			Short visitNo, String comolaintType, String duration, String description, Date lastMenstrualPeriod_LMP,
			Short gestationalAgeOrPeriodofAmenorrhea_POA, Short trimesterNumber, Date expectedDateofDelivery,
			Boolean primiGravida, Short gravida_G, Short termDeliveries_T, Short pretermDeliveries_P, Short abortions_A,
			Short livebirths_L, String bloodGroup, Integer stillBirth, Short para) {
		super();
		ID = iD;
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.visitCode = visitCode;
		this.visitNo = visitNo;
		this.comolaintType = comolaintType;
		this.duration = duration;
		this.description = description;
		this.lastMenstrualPeriod_LMP = lastMenstrualPeriod_LMP;
		this.gestationalAgeOrPeriodofAmenorrhea_POA = gestationalAgeOrPeriodofAmenorrhea_POA;
		this.trimesterNumber = trimesterNumber;
		this.expectedDateofDelivery = expectedDateofDelivery;
		this.primiGravida = primiGravida;
		this.gravida_G = gravida_G;
		this.termDeliveries_T = termDeliveries_T;
		this.pretermDeliveries_P = pretermDeliveries_P;
		this.abortions_A = abortions_A;
		this.livebirths_L = livebirths_L;
		this.bloodGroup = bloodGroup;
		this.stillBirth = stillBirth;
		this.para = para;
	}

	public static ANCCareDetails getANCCareDetails(ArrayList<Object[]> resList) {
		// ArrayList<ANCCareDetails> resArray = new ArrayList<ANCCareDetails>();
		ANCCareDetails cOBJ = null;
		for (Object[] obj : resList) {
			cOBJ = new ANCCareDetails((Long) obj[0], (Long) obj[1], (Long) obj[2], (Integer) obj[3], (Long) obj[4],
					(Short) obj[5], (String) obj[6], (String) obj[7], (String) obj[8], (Date) obj[9], (Short) obj[10],
					(Short) obj[11], (Date) obj[12], (Boolean) obj[13], (Short) obj[14], (Short) obj[15],
					(Short) obj[16], (Short) obj[17], (Short) obj[18], (String) obj[19], (Integer) obj[20]);

			Date Ldate = (Date) obj[9];
			if (null != Ldate) {
				cOBJ.setLmpDate(Ldate.toString());
			}

			Date EDate = (Date) obj[12];
			if (null != EDate) {
				cOBJ.setExpDelDt(EDate.toString());
			}
		}
		return cOBJ;
	}

	public static ANCCareDetails getANCCareDetails(ArrayList<Object[]> resList, int temp) {
		// ArrayList<ANCCareDetails> resArray = new ArrayList<ANCCareDetails>();
		ANCCareDetails cOBJ = null;
		for (Object[] obj : resList) {
			cOBJ = new ANCCareDetails((Long) obj[0], (Long) obj[1], (Long) obj[2], (Integer) obj[3], (Long) obj[4],
					(Short) obj[5], (String) obj[6], (String) obj[7], (String) obj[8], (Date) obj[9], (Short) obj[10],
					(Short) obj[11], (Date) obj[12], (Boolean) obj[13], (Short) obj[14], (Short) obj[15],
					(Short) obj[16], (Short) obj[17], (Short) obj[18], (String) obj[19], (Integer) obj[20],
					(Short) obj[21]);

			Date Ldate = (Date) obj[9];
			if (null != Ldate) {
				cOBJ.setLmpDate(Ldate.toString());
			}

			Date EDate = (Date) obj[12];
			if (null != EDate) {
				cOBJ.setExpDelDt(EDate.toString());
			}
		}
		return cOBJ;
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

	public Short getVisitNo() {
		return visitNo;
	}

	public void setVisitNo(Short visitNo) {
		this.visitNo = visitNo;
	}

	public String getComolaintType() {
		return comolaintType;
	}

	public void setComolaintType(String comolaintType) {
		this.comolaintType = comolaintType;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getLastMenstrualPeriod_LMP() {
		return lastMenstrualPeriod_LMP;
	}

	public void setLastMenstrualPeriod_LMP(Date lastMenstrualPeriod_LMP) {
		this.lastMenstrualPeriod_LMP = lastMenstrualPeriod_LMP;
	}

	public Short getGestationalAgeOrPeriodofAmenorrhea_POA() {
		return gestationalAgeOrPeriodofAmenorrhea_POA;
	}

	public void setGestationalAgeOrPeriodofAmenorrhea_POA(Short gestationalAgeOrPeriodofAmenorrhea_POA) {
		this.gestationalAgeOrPeriodofAmenorrhea_POA = gestationalAgeOrPeriodofAmenorrhea_POA;
	}

	public Short getTrimesterNumber() {
		return trimesterNumber;
	}

	public void setTrimesterNumber(Short trimesterNumber) {
		this.trimesterNumber = trimesterNumber;
	}

	public Date getExpectedDateofDelivery() {
		return expectedDateofDelivery;
	}

	public void setExpectedDateofDelivery(Date expectedDateofDelivery) {
		this.expectedDateofDelivery = expectedDateofDelivery;
	}

	public Boolean getPrimiGravida() {
		return primiGravida;
	}

	public void setPrimiGravida(Boolean primiGravida) {
		this.primiGravida = primiGravida;
	}

	public Short getGravida_G() {
		return gravida_G;
	}

	public void setGravida_G(Short gravida_G) {
		this.gravida_G = gravida_G;
	}

	public Short getTermDeliveries_T() {
		return termDeliveries_T;
	}

	public void setTermDeliveries_T(Short termDeliveries_T) {
		this.termDeliveries_T = termDeliveries_T;
	}

	public Short getPretermDeliveries_P() {
		return pretermDeliveries_P;
	}

	public void setPretermDeliveries_P(Short pretermDeliveries_P) {
		this.pretermDeliveries_P = pretermDeliveries_P;
	}

	public Short getAbortions_A() {
		return abortions_A;
	}

	public void setAbortions_A(Short abortions_A) {
		this.abortions_A = abortions_A;
	}

	public Short getLivebirths_L() {
		return livebirths_L;
	}

	public void setLivebirths_L(Short livebirths_L) {
		this.livebirths_L = livebirths_L;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
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

	public String getLmpDate() {
		return lmpDate;
	}

	public void setLmpDate(String lmpDate) {
		this.lmpDate = lmpDate;
	}

	public String getExpDelDt() {
		return expDelDt;
	}

	public void setExpDelDt(String expDelDt) {
		this.expDelDt = expDelDt;
	}

	public Long getVanSerialNo() {
		return vanSerialNo;
	}

	public void setVanSerialNo(Long vanSerialNo) {
		this.vanSerialNo = vanSerialNo;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
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

	public Integer getStillBirth() {
		return stillBirth;
	}

	public void setStillBirth(Integer stillBirth) {
		this.stillBirth = stillBirth;
	}

}
