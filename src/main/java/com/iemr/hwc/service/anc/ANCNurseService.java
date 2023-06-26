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
package com.iemr.hwc.service.anc;

import java.text.ParseException;
import java.util.List;

import com.iemr.hwc.data.anc.ANCCareDetails;
import com.iemr.hwc.data.anc.ANCWomenVaccineDetail;
import com.iemr.hwc.data.anc.BenAdherence;
import com.iemr.hwc.data.anc.SysObstetricExamination;
import com.iemr.hwc.data.anc.WrapperAncImmunization;
import com.iemr.hwc.data.anc.WrapperBenInvestigationANC;

public interface ANCNurseService {

	Long saveBeneficiaryANCDetails(ANCCareDetails ancCareDetails);

	Long saveANCWomenVaccineDetails(List<ANCWomenVaccineDetail> ancWomenVaccineDetails);

	// int saveBenAdherenceDetails(BenAdherence benAdherence);

	// int saveBenChiefComplaints(List<BenChiefComplaint>
	// benChiefComplaintList);

	// Long saveBenInvestigation(WrapperBenInvestigationANC
	// wrapperBenInvestigationANC);

	Long saveBenAncCareDetails(ANCCareDetails ancCareDetailsOBJ) throws ParseException;

	Long saveAncImmunizationDetails(WrapperAncImmunization wrapperAncImmunizationOBJ) throws ParseException;

	// Long savePhyHeadToToeExamination(PhyHeadToToeExamination
	// headToToeExamination);

	// Long saveSysCardiovascularExamination(SysCardiovascularExamination
	// cardiovascularExamination);

	// Long saveSysCentralNervousExamination(SysCentralNervousExamination
	// centralNervousExamination);

	// Long saveSysGastrointestinalExamination(SysGastrointestinalExamination
	// gastrointestinalExamination);

	// Long
	// saveSysGenitourinarySystemExamination(SysGenitourinarySystemExamination
	// genitourinarySystemExamination);

	// Long
	// saveSysMusculoskeletalSystemExamination(SysMusculoskeletalSystemExamination
	// musculoskeletalSystemExamination);

	Long saveSysObstetricExamination(SysObstetricExamination obstetricExamination);

	// Long saveSysRespiratoryExamination(SysRespiratoryExamination
	// respiratoryExamination);

	// String getANCExaminationDetailsData(Long benRegID, Long benVisitID);

	// String getBenAdherence(Long beneficiaryRegID, Long benVisitID);

	// String getBenChiefComplaints(Long beneficiaryRegID, Long benVisitID);

	// String getLabTestOrders(Long beneficiaryRegID, Long benVisitID);

	// Integer saveAncDocFindings(WrapperAncFindings wrapperAncFindings);

	// Integer saveBenANCPrescription(List<PrescribedDrugDetail>
	// prescribedDrugDetailList);

	// Long saveBenANCPastHistory(BenMedHistory benMedHistory);

	// Long saveBenANCComorbidConditions(WrapperComorbidCondDetails
	// wrapperComorbidCondDetails);

	// Long saveBenANCMedicationHistory(WrapperMedicationHistory
	// wrapperMedicationHistory);

	// Integer saveBenANCMenstrualHistory(BenMenstrualDetails
	// benMenstrualDetails);

	// Long saveFemaleObstetricHistory(WrapperFemaleObstetricHistory
	// wrapperFemaleObstetricHistory);

	// Long savePerinatalHistory(PerinatalHistory perinatalHistory);

	// Long saveChildOptionalVaccineDetail(WrapperChildOptionalVaccineDetail
	// wrapperChildVaccineDetail);

	// Long saveChildDevelopmentHistory(BenChildDevelopmentHistory
	// benChildDevelopmentHistory);

	// Integer saveANCPersonalHistory(BenPersonalHabit benPersonalHabit);

	// Long saveANCAllergyHistory(BenAllergyHistory benAllergyHistory);

	// Long saveANCBenFamilyHistory(BenFamilyHistory benFamilyHistory);

	// Long saveChildFeedingHistory(ChildFeedingDetails childFeedingDetails);

	// Long saveANCImmunizationHistory(WrapperImmunizationHistory
	// wrapperImmunizationHistory);

	// public String fetchBenPastMedicalHistory(Long benRegID) throws Exception;

	// public String fetchBenPersonalTobaccoHistory(Long beneficiaryRegID);

	// public String fetchBenPersonalAlcoholHistory(Long beneficiaryRegID);

	// public String fetchBenPersonalAllergyHistory(Long beneficiaryRegID);

	// public String fetchBenPersonalMedicationHistory(Long beneficiaryRegID);

	// public String fetchBenPersonalFamilyHistory(Long beneficiaryRegID);

	// public String fetchBenMenstrualHistory(Long beneficiaryRegID);

	// public String fetchBenPastObstetricHistory(Long beneficiaryRegID);

	// public String fetchBenComorbidityHistory(Long beneficiaryRegID);

	// public String fetchBenOptionalVaccineHistory(Long beneficiaryRegID);

	// public String getBenANCHistoryDetails(Long benRegID, Long benVisitID);

	public int updateBenAdherenceDetails(BenAdherence benAdherence);

	// public int updateBenChiefComplaints(List<BenChiefComplaint>
	// benChiefComplaintList);

	//public Long updateBenInvestigation(WrapperBenInvestigationANC wrapperBenInvestigationANC);

	public int updateBenAncCareDetails(ANCCareDetails ancCareDetailsOBJ) throws ParseException;

	public int updateBenAncImmunizationDetails(WrapperAncImmunization wrapperAncImmunization) throws ParseException;

	// public int updateBenAncPastHistoryDetails(BenMedHistory benMedHistory)
	// throws ParseException;

	// public int updateBenANCComorbidConditions(WrapperComorbidCondDetails
	// wrapperComorbidCondDetails);

	// public int updateBenANCMedicationHistory(WrapperMedicationHistory
	// wrapperMedicationHistory);

	// public int updateBenANCPersonalHistory(BenPersonalHabit
	// benPersonalHabit);

	// public int updateBenANCAllergicHistory(BenAllergyHistory
	// benAllergyHistory);

	// public int updateBenANCFamilyHistory(BenFamilyHistory benFamilyHistory);

	// public int
	// updateChildOptionalVaccineDetail(WrapperChildOptionalVaccineDetail
	// wrapperChildOptionalVaccineDetail);

	// public int updateANCAnthropometryDetails(BenAnthropometryDetail
	// anthropometryDetail );

	// public int updateANCPhysicalVitalDetails(BenPhysicalVitalDetail
	// physicalVitalDetail );

	// public int updateANCChildImmunizationDetail(WrapperImmunizationHistory
	// wrapperImmunizationHistory);

	// public String fetchBenImmunizationHistory(Long beneficiaryRegID);

	// public int
	// updateSysGastrointestinalExamination(SysGastrointestinalExamination
	// gastrointestinalExamination);

	// public int
	// updateSysCardiovascularExamination(SysCardiovascularExamination
	// cardiovascularExamination);

	// public int updateSysRespiratoryExamination(SysRespiratoryExamination
	// respiratoryExamination);

	// public int
	// updateSysCentralNervousExamination(SysCentralNervousExamination
	// centralNervousExamination);

	// public int
	// updateSysMusculoskeletalSystemExamination(SysMusculoskeletalSystemExamination
	// musculoskeletalSystemExamination);

	// public int
	// updateSysGenitourinarySystemExamination(SysGenitourinarySystemExamination
	// genitourinarySystemExamination);

	public int updateSysObstetricExamination(SysObstetricExamination obstetricExamination);

	String getANCCareDetails(Long beneficiaryRegID, Long visitCode);

	String getANCWomenVaccineDetails(Long beneficiaryRegID, Long visitCode);

	// public int updatePhyHeadToToeExamination(PhyHeadToToeExamination
	// headToToeExamination);

	// public int updateANCMenstrualHistory(BenMenstrualDetails
	// benMenstrualDetails);

	// public int updateANCPastObstetricHistory(WrapperFemaleObstetricHistory
	// wrapperFemaleObstetricHistory);

}
