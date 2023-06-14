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

import java.util.ArrayList;

import com.iemr.mmu.data.quickConsultation.LabTestOrderDetail;

public class WrapperBenInvestigationANC {
	private Long beneficiaryRegID;
	private Long benVisitID;
	private Long visitCode;
	private Integer providerServiceMapID;
	private Long prescriptionID;
	private String externalInvestigations;
	private String createdBy;

	private Integer vanID;
	private Integer parkingPlaceID;

	private ArrayList<LabTestOrderDetail> laboratoryList;

	public WrapperBenInvestigationANC() {
	}

	public WrapperBenInvestigationANC(Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID,
			Long prescriptionID, String createdBy, ArrayList<LabTestOrderDetail> laboratoryList) {
		super();
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.prescriptionID = prescriptionID;
		this.createdBy = createdBy;
		this.laboratoryList = laboratoryList;
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

	public Long getPrescriptionID() {
		return prescriptionID;
	}

	public void setPrescriptionID(Long prescriptionID) {
		this.prescriptionID = prescriptionID;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public ArrayList<LabTestOrderDetail> getLaboratoryList() {
		return laboratoryList;
	}

	public void setLaboratoryList(ArrayList<LabTestOrderDetail> laboratoryList) {
		this.laboratoryList = laboratoryList;
	}

	public String getExternalInvestigations() {
		return externalInvestigations;
	}

	public void setExternalInvestigations(String externalInvestigations) {
		this.externalInvestigations = externalInvestigations;
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

	/*
	 * public static WrapperBenInvestigationANC
	 * getInvestigations(ArrayList<Object[]> investigationList) {
	 * WrapperBenInvestigationANC cOBJ = null;
	 * 
	 * WrapperBenInvestigationANC labTestOrdersList =
	 * LabTestOrderDetail.getLabTestOrderDetails(investigationList); if(null !=
	 * labTestOrdersList && labTestOrdersList.size()>0){ for (LabTestOrderDetail
	 * order : labTestOrdersList) {
	 * 
	 * 
	 * } } return cOBJ; }
	 */

}
