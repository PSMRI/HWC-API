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

import javax.persistence.Column;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.data.quickConsultation.BenChiefComplaint;
import com.iemr.mmu.data.snomedct.SCTDescription;

public class WrapperAncFindings {
	private Long beneficiaryRegID;
	private Long benVisitID;
	private Long visitCode;
	private Integer providerServiceMapID;
	private String createdBy;
	private String modifiedBy;
	private String clinicalObservation;
	private String clinicalObservationSctCode;
	private String otherSymptoms;
	// newly added field on 07-08-2018
	@Expose
	@Column(name = "OtherSymptoms_SCTCode")
	private String otherSymptomsSCTCode;

	@Expose
	@Column(name = "OtherSymptoms_SCTTerm")
	private String otherSymptomsSCTTerm;

	private String significantFindings;
	private String significantFindingsSctCode;
	private ArrayList<BenChiefComplaint> complaints;
	// private ArrayList<BenChiefComplaint> chiefComplaints;
	private Boolean isForHistory;

	private Integer vanID;
	private Integer parkingPlaceID;

	// DE40034072 - For clinical observations
	private ArrayList<SCTDescription> clinicalObservationsList;

	// DE40034072 - For significant findings
	private ArrayList<SCTDescription> significantFindingsList;

	public WrapperAncFindings(Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID, String createdBy,
			String clinicalObservation, String otherSymptoms, String significantFindings,
			ArrayList<BenChiefComplaint> complaints) {
		super();
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.createdBy = createdBy;
		this.clinicalObservation = clinicalObservation;
		this.otherSymptoms = otherSymptoms;
		this.significantFindings = significantFindings;
		this.complaints = complaints;
	}

	public String getClinicalObservationSctCode() {
		return clinicalObservationSctCode;
	}

	public void setClinicalObservationSctCode(String clinicalObservationSctCode) {
		this.clinicalObservationSctCode = clinicalObservationSctCode;
	}

	public String getSignificantFindingsSctCode() {
		return significantFindingsSctCode;
	}

	public void setSignificantFindingsSctCode(String significantFindingsSctCode) {
		this.significantFindingsSctCode = significantFindingsSctCode;
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

	public String getOtherSymptomsSCTCode() {
		return otherSymptomsSCTCode;
	}

	public void setOtherSymptomsSCTCode(String otherSymptomsSCTCode) {
		this.otherSymptomsSCTCode = otherSymptomsSCTCode;
	}

	public String getOtherSymptomsSCTTerm() {
		return otherSymptomsSCTTerm;
	}

	public void setOtherSymptomsSCTTerm(String otherSymptomsSCTTerm) {
		this.otherSymptomsSCTTerm = otherSymptomsSCTTerm;
	}

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getClinicalObservation() {
		return clinicalObservation;
	}

	public void setClinicalObservation(String clinicalObservation) {
		this.clinicalObservation = clinicalObservation;
	}

	public String getOtherSymptoms() {
		return otherSymptoms;
	}

	public void setOtherSymptoms(String otherSymptoms) {
		this.otherSymptoms = otherSymptoms;
	}

	public String getSignificantFindings() {
		return significantFindings;
	}

	public void setSignificantFindings(String significantFindings) {
		this.significantFindings = significantFindings;
	}

	public ArrayList<BenChiefComplaint> getComplaints() {
		return complaints;
	}

	public void setComplaints(ArrayList<BenChiefComplaint> complaints) {
		this.complaints = complaints;
	}

	public Boolean getIsForHistory() {
		return isForHistory;
	}

	public void setIsForHistory(Boolean isForHistory) {
		this.isForHistory = isForHistory;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public ArrayList<SCTDescription> getClinicalObservationsList() {
		return clinicalObservationsList;
	}

	public void setClinicalObservationsList(ArrayList<SCTDescription> clinicalObservationsList) {
		this.clinicalObservationsList = clinicalObservationsList;
	}

	public ArrayList<SCTDescription> getSignificantFindingsList() {
		return significantFindingsList;
	}

	public void setSignificantFindingsList(ArrayList<SCTDescription> significantFindingsList) {
		this.significantFindingsList = significantFindingsList;
	}

	public WrapperAncFindings(Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID,
			String clinicalObservation, String otherSymptoms, String significantFindings,
			ArrayList<BenChiefComplaint> chiefComplaints, Boolean isForHistory, Long visitCode,
			String clinicalObservationSctCode, String significantFindingsSctCode) {
		super();
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.visitCode = visitCode;
		this.providerServiceMapID = providerServiceMapID;
		this.clinicalObservation = clinicalObservation;
		this.clinicalObservationSctCode = clinicalObservationSctCode;
		this.otherSymptoms = otherSymptoms;
		this.significantFindings = significantFindings;
		this.significantFindingsSctCode = significantFindingsSctCode;
		this.complaints = chiefComplaints;
		this.isForHistory = isForHistory;
	}

	public static WrapperAncFindings getFindingsData(ArrayList<Object[]> clinicalObservationsList,
			ArrayList<Object[]> chiefComplaintsList) {
		WrapperAncFindings cOBJ = null;

		ArrayList<BenChiefComplaint> chiefcmplts = BenChiefComplaint.getBenChiefComplaints(chiefComplaintsList);
		if (null != clinicalObservationsList && clinicalObservationsList.size() > 0) {
			for (Object[] obj : clinicalObservationsList) {
				cOBJ = new WrapperAncFindings((Long) obj[0], (Long) obj[1], (Integer) obj[2], (String) obj[3],
						(String) obj[4], (String) obj[5], chiefcmplts, (Boolean) obj[6], (Long) obj[7], (String) obj[8],
						(String) obj[9]);

			}
			ArrayList<SCTDescription> clinicalObsList = getClinicalObservationList(cOBJ.getClinicalObservation(),
					cOBJ.getClinicalObservationSctCode());
			ArrayList<SCTDescription> significantFindingsList = getSignificantFindingsList(
					cOBJ.getSignificantFindings(), cOBJ.getSignificantFindingsSctCode());
			if (clinicalObsList != null && clinicalObsList.size() > 0)
				cOBJ.setClinicalObservationsList(clinicalObsList);
			if (significantFindingsList != null && significantFindingsList.size() > 0)
				cOBJ.setSignificantFindingsList(significantFindingsList);
		} else if (null != chiefcmplts && chiefcmplts.size() > 0) {
			BenChiefComplaint cmplint = chiefcmplts.get(0);
			cOBJ = new WrapperAncFindings(cmplint.getBeneficiaryRegID(), cmplint.getBenVisitID(),
					cmplint.getProviderServiceMapID(), null, null, null, chiefcmplts, null, cmplint.getVisitCode(),
					null, null);
		}
		return cOBJ;
	}

	private static ArrayList<SCTDescription> getClinicalObservationList(String clinicalObservation,
			String clinicalObservationSctCode) {
		ArrayList<SCTDescription> clinicalList = new ArrayList<SCTDescription>();
		if (clinicalObservation != null && clinicalObservationSctCode != null) {
			String[] clinicalObs = clinicalObservation.split("\\|\\|");
			String[] clinicalSct = clinicalObservationSctCode.split("\\|\\|");
			SCTDescription sct = null;
			for (int i = 0; i < clinicalObs.length; i++) {
				sct = new SCTDescription(clinicalSct[i],clinicalObs[i]);
				clinicalList.add(sct);
			}

		}
		return clinicalList;
	}

	private static ArrayList<SCTDescription> getSignificantFindingsList(String significantFindings,
			String significantFindingsCode) {
		ArrayList<SCTDescription> significantList = new ArrayList<SCTDescription>();
		if (significantFindings != null && significantFindingsCode != null) {
			String[] significantObs = significantFindings.split("\\|\\|");
			String[] significantSct = significantFindingsCode.split("\\|\\|");
			SCTDescription sct = null;
			for (int i = 0; i < significantObs.length; i++) {
				sct = new SCTDescription(significantSct[i],significantObs[i]);
				significantList.add(sct);
			}
		}
		return significantList;
	}

}
