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

public class WrapperAncImmunization {
	private Long beneficiaryRegID;
	private Long benVisitID;
	private Long visitCode;
	private Integer providerServiceMapID;
	private String createdBy;
	private Long tT1ID;
	private String tT_1Status;
	private String dateReceivedForTT_1;
	private String facilityNameOfTT_1;
	private Long tT2ID;
	private String tT_2Status;
	private String dateReceivedForTT_2;
	private String facilityNameOfTT_2;
	private Long tT3ID;
	private String tT_3Status;
	private String dateReceivedForTT_3;
	private String facilityNameOfTT_3;
	private String modifiedBy;

	private Integer vanID;
	private Integer parkingPlaceID;

	public WrapperAncImmunization() {
	}

	public WrapperAncImmunization(Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID,
			String createdBy, String tT_1Status, String dateReceivedForTT_1, String facilityNameOfTT_1,
			String tT_2Status, String dateReceivedForTT_2, String facilityNameOfTT_2, String tT_3Status,
			String dateReceivedForTT_3, String facilityNameOfTT_3) {
		super();
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.createdBy = createdBy;
		this.tT_1Status = tT_1Status;
		this.dateReceivedForTT_1 = dateReceivedForTT_1;
		this.facilityNameOfTT_1 = facilityNameOfTT_1;
		this.tT_2Status = tT_2Status;
		this.dateReceivedForTT_2 = dateReceivedForTT_2;
		this.facilityNameOfTT_2 = facilityNameOfTT_2;
		this.tT_3Status = tT_3Status;
		this.dateReceivedForTT_3 = dateReceivedForTT_3;
		this.facilityNameOfTT_3 = facilityNameOfTT_3;
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

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String gettT_1Status() {
		return tT_1Status;
	}

	public void settT_1Status(String tT_1Status) {
		this.tT_1Status = tT_1Status;
	}

	public String getDateReceivedForTT_1() {
		return dateReceivedForTT_1;
	}

	public void setDateReceivedForTT_1(String dateReceivedForTT_1) {
		this.dateReceivedForTT_1 = dateReceivedForTT_1;
	}

	public String getFacilityNameOfTT_1() {
		return facilityNameOfTT_1;
	}

	public void setFacilityNameOfTT_1(String facilityNameOfTT_1) {
		this.facilityNameOfTT_1 = facilityNameOfTT_1;
	}

	public String gettT_2Status() {
		return tT_2Status;
	}

	public void settT_2Status(String tT_2Status) {
		this.tT_2Status = tT_2Status;
	}

	public String getDateReceivedForTT_2() {
		return dateReceivedForTT_2;
	}

	public void setDateReceivedForTT_2(String dateReceivedForTT_2) {
		this.dateReceivedForTT_2 = dateReceivedForTT_2;
	}

	public String getFacilityNameOfTT_2() {
		return facilityNameOfTT_2;
	}

	public void setFacilityNameOfTT_2(String facilityNameOfTT_2) {
		this.facilityNameOfTT_2 = facilityNameOfTT_2;
	}

	public String gettT_3Status() {
		return tT_3Status;
	}

	public void settT_3Status(String tT_3Status) {
		this.tT_3Status = tT_3Status;
	}

	public String getDateReceivedForTT_3() {
		return dateReceivedForTT_3;
	}

	public void setDateReceivedForTT_3(String dateReceivedForTT_3) {
		this.dateReceivedForTT_3 = dateReceivedForTT_3;
	}

	public String getFacilityNameOfTT_3() {
		return facilityNameOfTT_3;
	}

	public void setFacilityNameOfTT_3(String facilityNameOfTT_3) {
		this.facilityNameOfTT_3 = facilityNameOfTT_3;
	}

	public Long gettT1ID() {
		return tT1ID;
	}

	public void settT1ID(Long tT1ID) {
		this.tT1ID = tT1ID;
	}

	public Long gettT2ID() {
		return tT2ID;
	}

	public void settT2ID(Long tT2ID) {
		this.tT2ID = tT2ID;
	}

	public Long gettT3ID() {
		return tT3ID;
	}

	public void settT3ID(Long tT3ID) {
		this.tT3ID = tT3ID;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}
