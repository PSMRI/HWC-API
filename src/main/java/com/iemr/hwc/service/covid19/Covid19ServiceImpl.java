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
package com.iemr.hwc.service.covid19;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.iemr.hwc.data.anc.BenAllergyHistory;
import com.iemr.hwc.data.anc.BenChildDevelopmentHistory;
import com.iemr.hwc.data.anc.BenFamilyHistory;
import com.iemr.hwc.data.anc.BenMedHistory;
import com.iemr.hwc.data.anc.BenMenstrualDetails;
import com.iemr.hwc.data.anc.BenPersonalHabit;
import com.iemr.hwc.data.anc.ChildFeedingDetails;
import com.iemr.hwc.data.anc.PerinatalHistory;
import com.iemr.hwc.data.anc.WrapperAncFindings;
import com.iemr.hwc.data.anc.WrapperBenInvestigationANC;
import com.iemr.hwc.data.anc.WrapperChildOptionalVaccineDetail;
import com.iemr.hwc.data.anc.WrapperComorbidCondDetails;
import com.iemr.hwc.data.anc.WrapperFemaleObstetricHistory;
import com.iemr.hwc.data.anc.WrapperImmunizationHistory;
import com.iemr.hwc.data.anc.WrapperMedicationHistory;
import com.iemr.hwc.data.covid19.Covid19BenFeedback;
import com.iemr.hwc.data.nurse.BenAnthropometryDetail;
import com.iemr.hwc.data.nurse.BenPhysicalVitalDetail;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.nurse.CDSS;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.quickConsultation.PrescribedDrugDetail;
import com.iemr.hwc.data.quickConsultation.PrescriptionDetail;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.nurse.CDSSRepo;
import com.iemr.hwc.repo.nurse.anc.BenAdherenceRepo;
import com.iemr.hwc.repo.nurse.covid19.Covid19BenFeedbackRepo;
import com.iemr.hwc.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonServiceImpl;
import com.iemr.hwc.service.labtechnician.LabTechnicianServiceImpl;
import com.iemr.hwc.service.tele_consultation.SMSGatewayServiceImpl;
import com.iemr.hwc.service.tele_consultation.TeleConsultationServiceImpl;
import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.mapper.InputMapper;

@Service

public class Covid19ServiceImpl implements Covid19Service {
	@Autowired
	private CommonNurseServiceImpl commonNurseServiceImpl;
	@Autowired
	private CommonDoctorServiceImpl commonDoctorServiceImpl;
	@Autowired
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;
	@Autowired
	private LabTechnicianServiceImpl labTechnicianServiceImpl;
	@Autowired
	private CommonServiceImpl commonServiceImpl;
	@Autowired
	private TeleConsultationServiceImpl teleConsultationServiceImpl;
	@Autowired
	private SMSGatewayServiceImpl sMSGatewayServiceImpl;

	@Autowired
	private Covid19BenFeedbackRepo covid19BenFeedbackRepo;
	@Autowired
	private PrescriptionDetailRepo prescriptionDetailRepo;

	@Autowired
	private BenVisitDetailRepo benVisitDetailRepo;

	@Autowired
	private CDSSRepo cdssRepo;

	@Override
	public String saveCovid19NurseData(JsonObject requestOBJ, String Authorization) throws Exception {
		Long saveSuccessFlag = null;
		TeleconsultationRequestOBJ tcRequestOBJ = null;
		// check if visit details data is not null
		Long benVisitCode = null;
		if (requestOBJ != null && requestOBJ.has("visitDetails") && !requestOBJ.get("visitDetails").isJsonNull()) {
			CommonUtilityClass nurseUtilityClass = InputMapper.gson().fromJson(requestOBJ, CommonUtilityClass.class);
			// Call method to save visit details data
			Map<String, Long> visitIdAndCodeMap = saveBenVisitDetails(requestOBJ.getAsJsonObject("visitDetails"),
					nurseUtilityClass);

			// 07-06-2018 visit code
			Long benVisitID = null;

			if (visitIdAndCodeMap != null && visitIdAndCodeMap.size() > 0 && visitIdAndCodeMap.containsKey("visitID")
					&& visitIdAndCodeMap.containsKey("visitCode")) {
				benVisitID = visitIdAndCodeMap.get("visitID");
				benVisitCode = visitIdAndCodeMap.get("visitCode");

				nurseUtilityClass.setVisitCode(benVisitCode);
				nurseUtilityClass.setBenVisitID(benVisitID);
			} else {
				Map<String, String> responseMap = new HashMap<String, String>();
				responseMap.put("response", "Data already saved");
				return new Gson().toJson(responseMap);
			}

			// check if visit details data saved successfully
			Long historySaveSuccessFlag = null;
			Long vitalSaveSuccessFlag = null;
			Integer covidSaveSuccessFlag = null;
			Integer i = null;

			JsonObject tmpOBJ = requestOBJ.getAsJsonObject("visitDetails").getAsJsonObject("visitDetails");
			// Getting benflowID for ben status update
			Long benFlowID = null;

			// Above if block code replaced by below line
			benFlowID = nurseUtilityClass.getBenFlowID();

			if (benVisitID != null && benVisitID > 0) {
				// save "covid" screening related feedback from beneficiary
				Covid19BenFeedback covid19BenFeedbackOBJ = InputMapper.gson().fromJson(
						requestOBJ.getAsJsonObject("visitDetails").get("covidDetails"), Covid19BenFeedback.class);
				if (covid19BenFeedbackOBJ != null) {
					covid19BenFeedbackOBJ.setVisitCode(benVisitCode);
					covidSaveSuccessFlag = saveCovidDetails(covid19BenFeedbackOBJ);
				} else
					covidSaveSuccessFlag = 1;

				tcRequestOBJ = commonServiceImpl.createTcRequest(requestOBJ, nurseUtilityClass, Authorization);
				// call method to save History data
				historySaveSuccessFlag = saveBenCovid19HistoryDetails(requestOBJ.getAsJsonObject("historyDetails"),
						benVisitID, benVisitCode);
				// call method to save Vital data
				vitalSaveSuccessFlag = saveBenCovid19VitalDetails(requestOBJ.getAsJsonObject("vitalDetails"),
						benVisitID, benVisitCode);

				// i = commonNurseServiceImpl.updateBeneficiaryStatus('N',
				// tmpOBJ.get("beneficiaryRegID").getAsLong());
			} else {
				throw new RuntimeException("Error occurred while creating beneficiary visit");
			}
			if ((null != historySaveSuccessFlag && historySaveSuccessFlag > 0)
					&& (null != vitalSaveSuccessFlag && vitalSaveSuccessFlag > 0) && (covidSaveSuccessFlag != null)) {

				/**
				 * We have to write new code to update ben status flow new logic
				 */
				int J = updateBenFlowNurseAfterNurseActivityANC(tmpOBJ, benVisitID, benFlowID, benVisitCode,
						nurseUtilityClass.getVanID(), tcRequestOBJ);

				if (J > 0)
					saveSuccessFlag = historySaveSuccessFlag;
				else
					throw new RuntimeException("Error occurred while saving data. Beneficiary status update failed");

				if (J > 0 && tcRequestOBJ != null && tcRequestOBJ.getWalkIn() == false) {
					int k = sMSGatewayServiceImpl.smsSenderGateway("schedule", nurseUtilityClass.getBeneficiaryRegID(),
							tcRequestOBJ.getSpecializationID(), tcRequestOBJ.getTmRequestID(), null,
							nurseUtilityClass.getCreatedBy(),
							tcRequestOBJ.getAllocationDate() != null ? String.valueOf(tcRequestOBJ.getAllocationDate())
									: "",
							null, Authorization);
				}

			} else {
				throw new RuntimeException("Error occurred while saving data");
			}
		} else {
			throw new Exception("Invalid input");
		}

		Map<String, String> responseMap = new HashMap<String, String>();
		if (benVisitCode != null) {
			responseMap.put("visitCode", benVisitCode.toString());
		}
		if (null != saveSuccessFlag && saveSuccessFlag > 0) {
			responseMap.put("response", "Data saved successfully");
		} else {
			responseMap.put("response", "Unable to save data");
		}
		return new Gson().toJson(responseMap);
	}

	public void deleteVisitDetails(JsonObject requestOBJ) throws Exception {
		if (requestOBJ != null && requestOBJ.has("visitDetails") && !requestOBJ.get("visitDetails").isJsonNull()) {

			CommonUtilityClass nurseUtilityClass = InputMapper.gson().fromJson(requestOBJ, CommonUtilityClass.class);

			Long visitCode = benVisitDetailRepo.getVisitCode(nurseUtilityClass.getBeneficiaryRegID(),
					nurseUtilityClass.getProviderServiceMapID());

			if (visitCode != null) {
				cdssRepo.deleteVisitDetails(visitCode);
				covid19BenFeedbackRepo.deleteVisitDetails(visitCode);
				benVisitDetailRepo.deleteVisitDetails(visitCode);
			}

		}

	}

	/**
	 * 
	 * @param requestOBJ
	 * @return success or failure flag for visitDetails data saving
	 */
	public Map<String, Long> saveBenVisitDetails(JsonObject visitDetailsOBJ, CommonUtilityClass nurseUtilityClass)
			throws Exception {
		Map<String, Long> visitIdAndCodeMap = new HashMap<>();
		Long benVisitID = null;
		int adherenceSuccessFlag = 0;
		int investigationSuccessFlag = 0;
		if (visitDetailsOBJ != null && visitDetailsOBJ.has("visitDetails")
				&& !visitDetailsOBJ.get("visitDetails").isJsonNull()) {

			BeneficiaryVisitDetail benVisitDetailsOBJ = InputMapper.gson().fromJson(visitDetailsOBJ.get("visitDetails"),
					BeneficiaryVisitDetail.class);
			int i = commonNurseServiceImpl.getMaxCurrentdate(benVisitDetailsOBJ.getBeneficiaryRegID(),
					benVisitDetailsOBJ.getVisitReason(), benVisitDetailsOBJ.getVisitCategory());
			if (i < 1) {
				benVisitID = commonNurseServiceImpl.saveBeneficiaryVisitDetails(benVisitDetailsOBJ);

				// 11-06-2018 visit code
				Long benVisitCode = commonNurseServiceImpl.generateVisitCode(benVisitID, nurseUtilityClass.getVanID(),
						nurseUtilityClass.getSessionID());
				if (benVisitID != null && benVisitID > 0 && benVisitCode != null && benVisitCode > 0) {
					if (visitDetailsOBJ.has("cdss") && !visitDetailsOBJ.get("cdss").isJsonNull()) {
						JsonObject cdssObj = visitDetailsOBJ.getAsJsonObject("cdss");
						CDSS cdss = InputMapper.gson().fromJson(cdssObj, CDSS.class);
						cdss.setBenVisitID(benVisitID);
						cdss.setVisitCode(benVisitCode);

						if (cdssObj.has("presentChiefComplaintDb")) {
							JsonObject presentCheifComplaintObj = cdssObj.getAsJsonObject("presentChiefComplaintDb");

							if (presentCheifComplaintObj.get("selectedDiagnosisID") != null
									&& !presentCheifComplaintObj.get("selectedDiagnosisID").isJsonNull())
								cdss.setSelectedDiagnosisID(
										presentCheifComplaintObj.get("selectedDiagnosisID").getAsString());
							if (presentCheifComplaintObj.get("selectedDiagnosis") != null
									&& !presentCheifComplaintObj.get("selectedDiagnosis").isJsonNull())
								cdss.setSelectedDiagnosis(
										presentCheifComplaintObj.get("selectedDiagnosis").getAsString());
							if (presentCheifComplaintObj.get("presentChiefComplaint") != null
									&& !presentCheifComplaintObj.get("presentChiefComplaint").isJsonNull())
								cdss.setPresentChiefComplaint(
										presentCheifComplaintObj.get("presentChiefComplaint").getAsString());
							if (presentCheifComplaintObj.get("presentChiefComplaintID") != null
									&& !presentCheifComplaintObj.get("presentChiefComplaintID").isJsonNull())
								cdss.setPresentChiefComplaintID(
										presentCheifComplaintObj.get("presentChiefComplaintID").getAsString());
							if (presentCheifComplaintObj.get("algorithmPc") != null
									&& !presentCheifComplaintObj.get("algorithmPc").isJsonNull())
								cdss.setAlgorithmPc(presentCheifComplaintObj.get("algorithmPc").getAsString());
							if (presentCheifComplaintObj.get("recommendedActionPc") != null
									&& !presentCheifComplaintObj.get("recommendedActionPc").isJsonNull())
								cdss.setRecommendedActionPc(
										presentCheifComplaintObj.get("recommendedActionPc").getAsString());
							if (presentCheifComplaintObj.get("remarksPc") != null
									&& !presentCheifComplaintObj.get("remarksPc").isJsonNull())
								cdss.setRemarksPc(presentCheifComplaintObj.get("remarksPc").getAsString());
							if (presentCheifComplaintObj.get("actionPc") != null
									&& !presentCheifComplaintObj.get("actionPc").isJsonNull())
								cdss.setActionPc(presentCheifComplaintObj.get("actionPc").getAsString());
							if (presentCheifComplaintObj.get("actionIdPc") != null
									&& !presentCheifComplaintObj.get("actionIdPc").isJsonNull())
								cdss.setActionIdPc(presentCheifComplaintObj.get("actionIdPc").getAsInt());
						}

						if (cdssObj.has("diseaseSummaryDb")) {
							JsonObject diseaseSummaryObj = cdssObj.getAsJsonObject("diseaseSummaryDb");
							if (diseaseSummaryObj.get("diseaseSummary") != null
									&& !diseaseSummaryObj.get("diseaseSummary").isJsonNull())
								cdss.setDiseaseSummary(diseaseSummaryObj.get("diseaseSummary").getAsString());
							if (diseaseSummaryObj.get("diseaseSummaryID") != null
									&& !diseaseSummaryObj.get("diseaseSummaryID").isJsonNull())
								cdss.setDiseaseSummaryID(diseaseSummaryObj.get("diseaseSummaryID").getAsInt());
							if (diseaseSummaryObj.get("algorithm") != null
									&& !diseaseSummaryObj.get("algorithm").isJsonNull())
								cdss.setAlgorithm(diseaseSummaryObj.get("algorithm").getAsString());
							if (diseaseSummaryObj.get("recommendedAction") != null
									&& !diseaseSummaryObj.get("recommendedAction").isJsonNull())
								cdss.setRecommendedAction(diseaseSummaryObj.get("recommendedAction").getAsString());
							if (diseaseSummaryObj.get("remarks") != null
									&& !diseaseSummaryObj.get("remarks").isJsonNull())
								cdss.setRemarks(diseaseSummaryObj.get("remarks").getAsString());
							if (diseaseSummaryObj.get("action") != null
									&& !diseaseSummaryObj.get("action").isJsonNull())
								cdss.setAction(diseaseSummaryObj.get("action").getAsString());
							if (diseaseSummaryObj.get("actionId") != null
									&& !diseaseSummaryObj.get("actionId").isJsonNull())
								cdss.setActionId(diseaseSummaryObj.get("actionId").getAsInt());
							if (diseaseSummaryObj.get("informationGiven") != null
									&& !diseaseSummaryObj.get("informationGiven").isJsonNull())
								cdss.setInformationGiven(diseaseSummaryObj.get("informationGiven").getAsString());

						}

						commonNurseServiceImpl.saveCdssDetails(cdss);
					}

				}
				visitIdAndCodeMap.put("visitID", benVisitID);
				visitIdAndCodeMap.put("visitCode", benVisitCode);
			}
		}
		return visitIdAndCodeMap;
	}

	/**
	 * 
	 * @param requestOBJ
	 * @return success or failure flag for visitDetails data saving
	 */
	public Long saveBenCovid19HistoryDetails(JsonObject ncdCareHistoryOBJ, Long benVisitID, Long benVisitCode)
			throws Exception {
		Long pastHistorySuccessFlag = null;
		Long comrbidSuccessFlag = null;
		Long medicationSuccessFlag = null;
		Long obstetricSuccessFlag = null;
		Integer menstrualHistorySuccessFlag = null;
		Long familyHistorySuccessFlag = null;
		Integer personalHistorySuccessFlag = null;
		Long allergyHistorySuccessFlag = null;
		Long childVaccineSuccessFlag = null;
		Long immunizationSuccessFlag = null;
		Long developmentHistorySuccessFlag = null;
		Long childFeedingSuccessFlag = null;
		Long perinatalHistorySuccessFlag = null;

		// Save past History
		if (ncdCareHistoryOBJ != null && ncdCareHistoryOBJ.has("pastHistory")
				&& !ncdCareHistoryOBJ.get("pastHistory").isJsonNull()) {
			BenMedHistory benMedHistory = InputMapper.gson().fromJson(ncdCareHistoryOBJ.get("pastHistory"),
					BenMedHistory.class);
			if (null != benMedHistory) {
				benMedHistory.setBenVisitID(benVisitID);
				benMedHistory.setVisitCode(benVisitCode);
				pastHistorySuccessFlag = commonNurseServiceImpl.saveBenPastHistory(benMedHistory);
			}

		} else {
			pastHistorySuccessFlag = new Long(1);
		}

		// Save Comorbidity/concurrent Conditions
		if (ncdCareHistoryOBJ != null && ncdCareHistoryOBJ.has("comorbidConditions")
				&& !ncdCareHistoryOBJ.get("comorbidConditions").isJsonNull()) {
			WrapperComorbidCondDetails wrapperComorbidCondDetails = InputMapper.gson()
					.fromJson(ncdCareHistoryOBJ.get("comorbidConditions"), WrapperComorbidCondDetails.class);
			if (null != wrapperComorbidCondDetails) {
				wrapperComorbidCondDetails.setBenVisitID(benVisitID);
				wrapperComorbidCondDetails.setVisitCode(benVisitCode);
				comrbidSuccessFlag = commonNurseServiceImpl.saveBenComorbidConditions(wrapperComorbidCondDetails);
			}
		} else {
			comrbidSuccessFlag = new Long(1);
		}

		// Save Medication History
		if (ncdCareHistoryOBJ != null && ncdCareHistoryOBJ.has("medicationHistory")
				&& !ncdCareHistoryOBJ.get("medicationHistory").isJsonNull()) {
			WrapperMedicationHistory wrapperMedicationHistory = InputMapper.gson()
					.fromJson(ncdCareHistoryOBJ.get("medicationHistory"), WrapperMedicationHistory.class);
			if (null != wrapperMedicationHistory
					&& wrapperMedicationHistory.getBenMedicationHistoryDetails().size() > 0) {
				wrapperMedicationHistory.setBenVisitID(benVisitID);
				wrapperMedicationHistory.setVisitCode(benVisitCode);
				medicationSuccessFlag = commonNurseServiceImpl.saveBenMedicationHistory(wrapperMedicationHistory);
			} else {
				medicationSuccessFlag = new Long(1);
			}

		} else {
			medicationSuccessFlag = new Long(1);
		}

		// Save Past Obstetric History
		if (ncdCareHistoryOBJ != null && ncdCareHistoryOBJ.has("femaleObstetricHistory")
				&& !ncdCareHistoryOBJ.get("femaleObstetricHistory").isJsonNull()) {
			WrapperFemaleObstetricHistory wrapperFemaleObstetricHistory = InputMapper.gson()
					.fromJson(ncdCareHistoryOBJ.get("femaleObstetricHistory"), WrapperFemaleObstetricHistory.class);

			if (wrapperFemaleObstetricHistory != null) {
				wrapperFemaleObstetricHistory.setBenVisitID(benVisitID);
				wrapperFemaleObstetricHistory.setVisitCode(benVisitCode);
				obstetricSuccessFlag = commonNurseServiceImpl.saveFemaleObstetricHistory(wrapperFemaleObstetricHistory);
			} else {
				// Female Obstetric Details not provided.
			}

		} else {
			obstetricSuccessFlag = new Long(1);
		}

		// Save Menstrual History
		if (ncdCareHistoryOBJ != null && ncdCareHistoryOBJ.has("menstrualHistory")
				&& !ncdCareHistoryOBJ.get("menstrualHistory").isJsonNull()) {
			BenMenstrualDetails menstrualDetails = InputMapper.gson()
					.fromJson(ncdCareHistoryOBJ.get("menstrualHistory"), BenMenstrualDetails.class);
			if (null != menstrualDetails) {
				menstrualDetails.setBenVisitID(benVisitID);
				menstrualDetails.setVisitCode(benVisitCode);
				menstrualHistorySuccessFlag = commonNurseServiceImpl.saveBenMenstrualHistory(menstrualDetails);
			}

		} else {
			menstrualHistorySuccessFlag = 1;
		}

		// Save Family History
		if (ncdCareHistoryOBJ != null && ncdCareHistoryOBJ.has("familyHistory")
				&& !ncdCareHistoryOBJ.get("familyHistory").isJsonNull()) {
			BenFamilyHistory benFamilyHistory = InputMapper.gson().fromJson(ncdCareHistoryOBJ.get("familyHistory"),
					BenFamilyHistory.class);
			if (null != benFamilyHistory) {
				benFamilyHistory.setBenVisitID(benVisitID);
				benFamilyHistory.setVisitCode(benVisitCode);
				familyHistorySuccessFlag = commonNurseServiceImpl.saveBenFamilyHistory(benFamilyHistory);
			}
		} else {
			familyHistorySuccessFlag = new Long(1);
		}

		// Save Personal History
		if (ncdCareHistoryOBJ != null && ncdCareHistoryOBJ.has("personalHistory")
				&& !ncdCareHistoryOBJ.get("personalHistory").isJsonNull()) {
			// Save Ben Personal Habits..
			BenPersonalHabit personalHabit = InputMapper.gson().fromJson(ncdCareHistoryOBJ.get("personalHistory"),
					BenPersonalHabit.class);
			if (null != personalHabit) {
				personalHabit.setBenVisitID(benVisitID);
				personalHabit.setVisitCode(benVisitCode);
				personalHistorySuccessFlag = commonNurseServiceImpl.savePersonalHistory(personalHabit);
			}

			BenAllergyHistory benAllergyHistory = InputMapper.gson().fromJson(ncdCareHistoryOBJ.get("personalHistory"),
					BenAllergyHistory.class);
			if (null != benAllergyHistory) {
				benAllergyHistory.setBenVisitID(benVisitID);
				benAllergyHistory.setVisitCode(benVisitCode);
				allergyHistorySuccessFlag = commonNurseServiceImpl.saveAllergyHistory(benAllergyHistory);
			}

		} else {
			personalHistorySuccessFlag = 1;
			allergyHistorySuccessFlag = new Long(1);
		}

		// Save Other/Optional Vaccines History
		if (ncdCareHistoryOBJ != null && ncdCareHistoryOBJ.has("childVaccineDetails")
				&& !ncdCareHistoryOBJ.get("childVaccineDetails").isJsonNull()) {
			WrapperChildOptionalVaccineDetail wrapperChildVaccineDetail = InputMapper.gson()
					.fromJson(ncdCareHistoryOBJ.get("childVaccineDetails"), WrapperChildOptionalVaccineDetail.class);
			if (null != wrapperChildVaccineDetail) {
				wrapperChildVaccineDetail.setBenVisitID(benVisitID);
				wrapperChildVaccineDetail.setVisitCode(benVisitCode);
				childVaccineSuccessFlag = commonNurseServiceImpl
						.saveChildOptionalVaccineDetail(wrapperChildVaccineDetail);
			} else {
				// Child Optional Vaccine Detail not provided.
			}

		} else {
			childVaccineSuccessFlag = new Long(1);
		}

		// Save Immunization History
		if (ncdCareHistoryOBJ != null && ncdCareHistoryOBJ.has("immunizationHistory")
				&& !ncdCareHistoryOBJ.get("immunizationHistory").isJsonNull()) {
			WrapperImmunizationHistory wrapperImmunizationHistory = InputMapper.gson()
					.fromJson(ncdCareHistoryOBJ.get("immunizationHistory"), WrapperImmunizationHistory.class);
			if (null != wrapperImmunizationHistory) {
				wrapperImmunizationHistory.setBenVisitID(benVisitID);
				wrapperImmunizationHistory.setVisitCode(benVisitCode);
				immunizationSuccessFlag = commonNurseServiceImpl.saveImmunizationHistory(wrapperImmunizationHistory);
			} else {

				// ImmunizationList Data not Available
			}

		} else {
			immunizationSuccessFlag = new Long(1);
		}

		// Save Development History
		if (ncdCareHistoryOBJ != null && ncdCareHistoryOBJ.has("developmentHistory")
				&& !ncdCareHistoryOBJ.get("developmentHistory").isJsonNull()) {
			BenChildDevelopmentHistory benChildDevelopmentHistory = InputMapper.gson()
					.fromJson(ncdCareHistoryOBJ.get("developmentHistory"), BenChildDevelopmentHistory.class);

			if (null != benChildDevelopmentHistory) {
				benChildDevelopmentHistory.setBenVisitID(benVisitID);
				benChildDevelopmentHistory.setVisitCode(benVisitCode);
				developmentHistorySuccessFlag = commonNurseServiceImpl
						.saveChildDevelopmentHistory(benChildDevelopmentHistory);
			}

		} else {
			developmentHistorySuccessFlag = new Long(1);
		}

		// Save Feeding History
		if (ncdCareHistoryOBJ != null && ncdCareHistoryOBJ.has("feedingHistory")
				&& !ncdCareHistoryOBJ.get("feedingHistory").isJsonNull()) {
			ChildFeedingDetails childFeedingDetails = InputMapper.gson()
					.fromJson(ncdCareHistoryOBJ.get("feedingHistory"), ChildFeedingDetails.class);

			if (null != childFeedingDetails) {
				childFeedingDetails.setBenVisitID(benVisitID);
				childFeedingDetails.setVisitCode(benVisitCode);
				childFeedingSuccessFlag = commonNurseServiceImpl.saveChildFeedingHistory(childFeedingDetails);
			}

		}
		{
			childFeedingSuccessFlag = new Long(1);
		}

		// Save Perinatal Histroy
		if (ncdCareHistoryOBJ != null && ncdCareHistoryOBJ.has("perinatalHistroy")
				&& !ncdCareHistoryOBJ.get("perinatalHistroy").isJsonNull()) {
			PerinatalHistory perinatalHistory = InputMapper.gson().fromJson(ncdCareHistoryOBJ.get("perinatalHistroy"),
					PerinatalHistory.class);

			if (null != perinatalHistory) {
				perinatalHistory.setBenVisitID(benVisitID);
				perinatalHistory.setVisitCode(benVisitCode);
				perinatalHistorySuccessFlag = commonNurseServiceImpl.savePerinatalHistory(perinatalHistory);
			}

		}
		{
			perinatalHistorySuccessFlag = new Long(1);
		}

		Long historySaveSucccessFlag = null;

		if ((null != pastHistorySuccessFlag && pastHistorySuccessFlag > 0)
				&& (null != comrbidSuccessFlag && comrbidSuccessFlag > 0)
				&& (null != medicationSuccessFlag && medicationSuccessFlag > 0)
				&& (null != obstetricSuccessFlag && obstetricSuccessFlag > 0)
				&& (null != menstrualHistorySuccessFlag && menstrualHistorySuccessFlag > 0)
				&& (null != familyHistorySuccessFlag && familyHistorySuccessFlag > 0)
				&& (null != personalHistorySuccessFlag && personalHistorySuccessFlag > 0)
				&& (null != allergyHistorySuccessFlag && allergyHistorySuccessFlag > 0)
				&& (null != childVaccineSuccessFlag && childVaccineSuccessFlag > 0)
				&& (null != immunizationSuccessFlag && immunizationSuccessFlag > 0)
				&& (null != developmentHistorySuccessFlag && developmentHistorySuccessFlag > 0)
				&& (null != childFeedingSuccessFlag && childFeedingSuccessFlag > 0)
				&& (null != perinatalHistorySuccessFlag && perinatalHistorySuccessFlag > 0)) {

			historySaveSucccessFlag = pastHistorySuccessFlag;
		}
		return historySaveSucccessFlag;
	}

	/**
	 * 
	 * @param requestOBJ
	 * @return success or failure flag for visitDetails data saving
	 */
	public Long saveBenCovid19VitalDetails(JsonObject vitalDetailsOBJ, Long benVisitID, Long benVisitCode)
			throws Exception {
		Long vitalSuccessFlag = null;
		Long anthropometrySuccessFlag = null;
		Long phyVitalSuccessFlag = null;
		// Save Physical Anthropometry && Physical Vital Details
		if (vitalDetailsOBJ != null) {
			BenAnthropometryDetail benAnthropometryDetail = InputMapper.gson().fromJson(vitalDetailsOBJ,
					BenAnthropometryDetail.class);
			BenPhysicalVitalDetail benPhysicalVitalDetail = InputMapper.gson().fromJson(vitalDetailsOBJ,
					BenPhysicalVitalDetail.class);

			if (null != benAnthropometryDetail) {
				benAnthropometryDetail.setBenVisitID(benVisitID);
				benAnthropometryDetail.setVisitCode(benVisitCode);
				anthropometrySuccessFlag = commonNurseServiceImpl
						.saveBeneficiaryPhysicalAnthropometryDetails(benAnthropometryDetail);
			}
			if (null != benPhysicalVitalDetail) {
				benPhysicalVitalDetail.setBenVisitID(benVisitID);
				benPhysicalVitalDetail.setVisitCode(benVisitCode);
				phyVitalSuccessFlag = commonNurseServiceImpl
						.saveBeneficiaryPhysicalVitalDetails(benPhysicalVitalDetail);
			}

			if (anthropometrySuccessFlag != null && anthropometrySuccessFlag > 0 && phyVitalSuccessFlag != null
					&& phyVitalSuccessFlag > 0) {
				vitalSuccessFlag = anthropometrySuccessFlag;
			}
		}

		return vitalSuccessFlag;
	}

	// method for updating ben flow status flag for nurse
	private int updateBenFlowNurseAfterNurseActivityANC(JsonObject tmpOBJ, Long benVisitID, Long benFlowID,
			Long benVisitCode, Integer vanID, TeleconsultationRequestOBJ tcRequestOBJ) {
		short nurseFlag;
		short docFlag;
		short labIteration;

		short specialistFlag = (short) 0;
		Timestamp tcDate = null;
		Integer tcSpecialistUserID = null;

		nurseFlag = (short) 9;
		docFlag = (short) 1;
		labIteration = (short) 0;

		if (tcRequestOBJ != null && tcRequestOBJ.getUserID() != null && tcRequestOBJ.getAllocationDate() != null) {
			specialistFlag = (short) 1;
			tcDate = tcRequestOBJ.getAllocationDate();
			tcSpecialistUserID = tcRequestOBJ.getUserID();
		} else
			specialistFlag = (short) 0;

		int rs = commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(benFlowID,
				tmpOBJ.get("beneficiaryRegID").getAsLong(), benVisitID, tmpOBJ.get("visitReason").getAsString(),
				tmpOBJ.get("visitCategory").getAsString(), nurseFlag, docFlag, labIteration, (short) 0, (short) 0,
				benVisitCode, vanID, specialistFlag, tcDate, tcSpecialistUserID);

		return rs;
	}

	public String getBenVisitDetailsFrmNurseCovid19(Long benRegID, Long visitCode) {
		Map<String, Object> resMap = new HashMap<>();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

		BeneficiaryVisitDetail visitDetail = commonNurseServiceImpl.getCSVisitDetails(benRegID, visitCode);
		Covid19BenFeedback covid19BenFeedback = getCovidDetails(benRegID, visitCode);

		resMap.put("covid19NurseVisitDetail", gson.toJson(visitDetail));
		resMap.put("covidDetails", new Gson().toJson(covid19BenFeedback));
		resMap.put("Cdss", commonNurseServiceImpl.getBenCdss(benRegID, visitCode));

		// resMap.put("BenAdherence", commonNurseServiceImpl.getBenAdherence(benRegID,
		// visitCode));
		//
		// resMap.put("Investigation", commonNurseServiceImpl.getLabTestOrders(benRegID,
		// visitCode));

		return resMap.toString();
	}

	private Covid19BenFeedback getCovidDetails(Long benRegID, Long visitCode) {
		Covid19BenFeedback obj = covid19BenFeedbackRepo.findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode);
		if (obj != null) {
			if (obj.getSymptoms_db() != null) {
				String[] symptomsArr = obj.getSymptoms_db().split("\\|\\|");
				if (symptomsArr != null)
					obj.setSymptoms(symptomsArr);
			}

			if (obj.getTravelType() != null) {
				String[] treavelTypeArr = obj.getTravelType().split("\\|\\|");
				if (treavelTypeArr != null)
					obj.setTravelList(treavelTypeArr);
			}
			if (obj.getcOVID19_contact_history() != null) {
				String[] contactHistoryArr = obj.getcOVID19_contact_history().split("\\|\\|");
				if (contactHistoryArr != null)
					obj.setContactStatus(contactHistoryArr);
			}

			if (obj.getRecommendation_db() != null) {
				ArrayList<String[]> recommendationList = new ArrayList<>();
				String[] recommendationArr = obj.getRecommendation_db().split("\\|\\|");
				if (recommendationArr != null) {
					recommendationList.add(recommendationArr);
					obj.setRecommendation((recommendationList));
				}
			}

			if (obj.getSuspectedStatus() != null) {
				if (obj.getSuspectedStatus())
					obj.setSuspectedStatusUI("YES");
				else
					obj.setSuspectedStatusUI("NO");
			}

		}
		return obj;
	}

	public String getBenCovid19HistoryDetails(Long benRegID, Long visitCode) throws IEMRException {
		Map<String, Object> HistoryDetailsMap = new HashMap<String, Object>();

		HistoryDetailsMap.put("PastHistory", commonNurseServiceImpl.getPastHistoryData(benRegID, visitCode));
		HistoryDetailsMap.put("ComorbidityConditions",
				commonNurseServiceImpl.getComorbidityConditionsHistory(benRegID, visitCode));
		HistoryDetailsMap.put("MedicationHistory", commonNurseServiceImpl.getMedicationHistory(benRegID, visitCode));
		HistoryDetailsMap.put("PersonalHistory", commonNurseServiceImpl.getPersonalHistory(benRegID, visitCode));
		HistoryDetailsMap.put("FamilyHistory", commonNurseServiceImpl.getFamilyHistory(benRegID, visitCode));
		HistoryDetailsMap.put("MenstrualHistory", commonNurseServiceImpl.getMenstrualHistory(benRegID, visitCode));
		HistoryDetailsMap.put("FemaleObstetricHistory",
				commonNurseServiceImpl.getFemaleObstetricHistory(benRegID, visitCode));
		HistoryDetailsMap.put("ImmunizationHistory",
				commonNurseServiceImpl.getImmunizationHistory(benRegID, visitCode));
		HistoryDetailsMap.put("childOptionalVaccineHistory",
				commonNurseServiceImpl.getChildOptionalVaccineHistory(benRegID, visitCode));
		HistoryDetailsMap.put("DevelopmentHistory", commonNurseServiceImpl.getDevelopmentHistory(benRegID, visitCode));
		HistoryDetailsMap.put("PerinatalHistory", commonNurseServiceImpl.getPerinatalHistory(benRegID, visitCode));
		HistoryDetailsMap.put("FeedingHistory", commonNurseServiceImpl.getFeedingHistory(benRegID, visitCode));

		return new Gson().toJson(HistoryDetailsMap);
	}

	public String getBeneficiaryVitalDetails(Long beneficiaryRegID, Long visitCode) {
		Map<String, Object> resMap = new HashMap<>();

		resMap.put("benAnthropometryDetail",
				commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, visitCode));
		resMap.put("benPhysicalVitalDetail",
				commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode));

		return resMap.toString();
	}

	public String getBeneficiaryCdssDetails(Long beneficiaryRegID, Long benVisitID) {
		Map<String, Object> resMap = new HashMap<>();

		resMap.put("presentChiefComplaint", commonNurseServiceImpl.getBenCdssDetails(beneficiaryRegID, benVisitID));
		resMap.put("diseaseSummary", commonNurseServiceImpl.getBenCdssDetails(beneficiaryRegID, benVisitID));

		return resMap.toString();
	}

	public Integer saveCovidDetails(Covid19BenFeedback covid19BenFeedbackOBJ) {

		if (covid19BenFeedbackOBJ != null && covid19BenFeedbackOBJ.getSymptoms() != null
				&& covid19BenFeedbackOBJ.getSymptoms().length > 0) {
			StringBuffer sb = new StringBuffer("");
			int pointer = 1;
			for (String s : covid19BenFeedbackOBJ.getSymptoms()) {
				if (pointer == covid19BenFeedbackOBJ.getSymptoms().length)
					sb.append(s);
				else
					sb.append(s + "||");

				pointer++;
			}
			covid19BenFeedbackOBJ.setSymptoms_db(sb.toString());
		}
		if (covid19BenFeedbackOBJ != null && covid19BenFeedbackOBJ.getContactStatus() != null
				&& covid19BenFeedbackOBJ.getContactStatus().length > 0) {
			StringBuffer sb = new StringBuffer("");
			int pointer = 1;
			for (String s : covid19BenFeedbackOBJ.getContactStatus()) {
				if (pointer == covid19BenFeedbackOBJ.getContactStatus().length)
					sb.append(s);
				else
					sb.append(s + "||");

				pointer++;
			}
			covid19BenFeedbackOBJ.setcOVID19_contact_history(sb.toString());
		}
		if (covid19BenFeedbackOBJ != null && covid19BenFeedbackOBJ.getTravelList() != null
				&& covid19BenFeedbackOBJ.getTravelList().length > 0) {
			StringBuffer sb = new StringBuffer("");
			int pointer = 1;
			for (String s : covid19BenFeedbackOBJ.getTravelList()) {
				if (pointer == covid19BenFeedbackOBJ.getTravelList().length)
					sb.append(s);
				else
					sb.append(s + "||");

				pointer++;
			}
			covid19BenFeedbackOBJ.setTravelType(sb.toString());
		}
		if (covid19BenFeedbackOBJ != null && covid19BenFeedbackOBJ.getRecommendation() != null
				&& covid19BenFeedbackOBJ.getRecommendation().size() > 0) {
			StringBuffer sb = new StringBuffer("");

			int pointer = 1;

			for (String s : covid19BenFeedbackOBJ.getRecommendation().get(0)) {
				if (pointer == covid19BenFeedbackOBJ.getRecommendation().get(0).length)
					sb.append(s);
				else
					sb.append(s + "||");

				pointer++;
			}

			covid19BenFeedbackOBJ.setRecommendation_db(sb.toString());
		}

		if (covid19BenFeedbackOBJ.getSuspectedStatusUI().equalsIgnoreCase("YES"))
			covid19BenFeedbackOBJ.setSuspectedStatus(true);
		else if (covid19BenFeedbackOBJ.getSuspectedStatusUI().equalsIgnoreCase("NO"))
			covid19BenFeedbackOBJ.setSuspectedStatus(false);

		Covid19BenFeedback resultSetObj = covid19BenFeedbackRepo.save(covid19BenFeedbackOBJ);
		if (resultSetObj != null && resultSetObj.getcOVID19ID() > 0)
			return 1;
		else
			return null;
	}

	/**
	 * Update Services
	 */

	/**
	 * 
	 * @param requestOBJ
	 * @return success or failure flag for General OPD History updating by Doctor
	 */
	@Transactional(rollbackFor = Exception.class)
	public int updateBenHistoryDetails(JsonObject historyOBJ) throws Exception {
		int pastHistorySuccessFlag = 0;
		int comrbidSuccessFlag = 0;
		int medicationSuccessFlag = 0;
		int personalHistorySuccessFlag = 0;
		int allergyHistorySuccessFlag = 0;
		int familyHistorySuccessFlag = 0;
		int menstrualHistorySuccessFlag = 0;
		int obstetricSuccessFlag = 0;
		int childVaccineSuccessFlag = 0;
		int childFeedingSuccessFlag = 0;
		int perinatalHistorySuccessFlag = 0;
		int developmentHistorySuccessFlag = 0;
		int immunizationSuccessFlag = 0;

		// Update Past History
		if (historyOBJ != null && historyOBJ.has("pastHistory") && !historyOBJ.get("pastHistory").isJsonNull()) {
			BenMedHistory benMedHistory = InputMapper.gson().fromJson(historyOBJ.get("pastHistory"),
					BenMedHistory.class);
			pastHistorySuccessFlag = commonNurseServiceImpl.updateBenPastHistoryDetails(benMedHistory);

		} else {
			pastHistorySuccessFlag = 1;
		}

		// Update Comorbidity/concurrent Conditions
		if (historyOBJ != null && historyOBJ.has("comorbidConditions")
				&& !historyOBJ.get("comorbidConditions").isJsonNull()) {
			WrapperComorbidCondDetails wrapperComorbidCondDetails = InputMapper.gson()
					.fromJson(historyOBJ.get("comorbidConditions"), WrapperComorbidCondDetails.class);
			comrbidSuccessFlag = commonNurseServiceImpl.updateBenComorbidConditions(wrapperComorbidCondDetails);
		} else {
			comrbidSuccessFlag = 1;
		}

		// Update Medication History
		if (historyOBJ != null && historyOBJ.has("medicationHistory")
				&& !historyOBJ.get("medicationHistory").isJsonNull()) {
			WrapperMedicationHistory wrapperMedicationHistory = InputMapper.gson()
					.fromJson(historyOBJ.get("medicationHistory"), WrapperMedicationHistory.class);
			medicationSuccessFlag = commonNurseServiceImpl.updateBenMedicationHistory(wrapperMedicationHistory);
		} else {
			medicationSuccessFlag = 1;
		}
		// Update Personal History
		if (historyOBJ != null && historyOBJ.has("personalHistory")
				&& !historyOBJ.get("personalHistory").isJsonNull()) {
			// Update Ben Personal Habits..
			BenPersonalHabit personalHabit = InputMapper.gson().fromJson(historyOBJ.get("personalHistory"),
					BenPersonalHabit.class);

			personalHistorySuccessFlag = commonNurseServiceImpl.updateBenPersonalHistory(personalHabit);

			// Update Ben Allergy History..
			BenAllergyHistory benAllergyHistory = InputMapper.gson().fromJson(historyOBJ.get("personalHistory"),
					BenAllergyHistory.class);
			allergyHistorySuccessFlag = commonNurseServiceImpl.updateBenAllergicHistory(benAllergyHistory);

		} else {
			allergyHistorySuccessFlag = 1;
			personalHistorySuccessFlag = 1;
		}

		// Update Family History
		if (historyOBJ != null && historyOBJ.has("familyHistory") && !historyOBJ.get("familyHistory").isJsonNull()) {
			BenFamilyHistory benFamilyHistory = InputMapper.gson().fromJson(historyOBJ.get("familyHistory"),
					BenFamilyHistory.class);
			familyHistorySuccessFlag = commonNurseServiceImpl.updateBenFamilyHistory(benFamilyHistory);
		} else {
			familyHistorySuccessFlag = 1;
		}

		// Update Menstrual History
		if (historyOBJ != null && historyOBJ.has("menstrualHistory")
				&& !historyOBJ.get("menstrualHistory").isJsonNull()) {
			BenMenstrualDetails menstrualDetails = InputMapper.gson().fromJson(historyOBJ.get("menstrualHistory"),
					BenMenstrualDetails.class);
			menstrualHistorySuccessFlag = commonNurseServiceImpl.updateMenstrualHistory(menstrualDetails);
		} else {
			menstrualHistorySuccessFlag = 1;
		}

		// Update Past Obstetric History
		if (historyOBJ != null && historyOBJ.has("femaleObstetricHistory")
				&& !historyOBJ.get("femaleObstetricHistory").isJsonNull()) {
			WrapperFemaleObstetricHistory wrapperFemaleObstetricHistory = InputMapper.gson()
					.fromJson(historyOBJ.get("femaleObstetricHistory"), WrapperFemaleObstetricHistory.class);

			obstetricSuccessFlag = commonNurseServiceImpl.updatePastObstetricHistory(wrapperFemaleObstetricHistory);
		} else {
			obstetricSuccessFlag = 1;
		}

		if (historyOBJ != null && historyOBJ.has("immunizationHistory")
				&& !historyOBJ.get("immunizationHistory").isJsonNull()) {

			JsonObject immunizationHistory = historyOBJ.getAsJsonObject("immunizationHistory");
			if (immunizationHistory.get("immunizationList") != null
					&& immunizationHistory.getAsJsonArray("immunizationList").size() > 0) {
				WrapperImmunizationHistory wrapperImmunizationHistory = InputMapper.gson()
						.fromJson(historyOBJ.get("immunizationHistory"), WrapperImmunizationHistory.class);
				immunizationSuccessFlag = commonNurseServiceImpl
						.updateChildImmunizationDetail(wrapperImmunizationHistory);
			} else {
				immunizationSuccessFlag = 1;
			}
		} else {
			immunizationSuccessFlag = 1;
		}

		// Update Other/Optional Vaccines History
		if (historyOBJ != null && historyOBJ.has("childVaccineDetails")
				&& !historyOBJ.get("childVaccineDetails").isJsonNull()) {
			WrapperChildOptionalVaccineDetail wrapperChildVaccineDetail = InputMapper.gson()
					.fromJson(historyOBJ.get("childVaccineDetails"), WrapperChildOptionalVaccineDetail.class);
			childVaccineSuccessFlag = commonNurseServiceImpl
					.updateChildOptionalVaccineDetail(wrapperChildVaccineDetail);
		} else {
			childVaccineSuccessFlag = 1;
		}

		// Update ChildFeeding History
		if (historyOBJ != null && historyOBJ.has("feedingHistory") && !historyOBJ.get("feedingHistory").isJsonNull()) {
			ChildFeedingDetails childFeedingDetails = InputMapper.gson().fromJson(historyOBJ.get("feedingHistory"),
					ChildFeedingDetails.class);

			if (null != childFeedingDetails) {
				childFeedingSuccessFlag = commonNurseServiceImpl.updateChildFeedingHistory(childFeedingDetails);
			}

		} else {
			childFeedingSuccessFlag = 1;
		}

		// Update Perinatal History
		if (historyOBJ != null && historyOBJ.has("perinatalHistroy")
				&& !historyOBJ.get("perinatalHistroy").isJsonNull()) {
			PerinatalHistory perinatalHistory = InputMapper.gson().fromJson(historyOBJ.get("perinatalHistroy"),
					PerinatalHistory.class);

			if (null != perinatalHistory) {
				perinatalHistorySuccessFlag = commonNurseServiceImpl.updatePerinatalHistory(perinatalHistory);
			}

		} else {
			perinatalHistorySuccessFlag = 1;
		}

		// Update Development History
		if (historyOBJ != null && historyOBJ.has("developmentHistory")
				&& !historyOBJ.get("developmentHistory").isJsonNull()) {
			BenChildDevelopmentHistory benChildDevelopmentHistory = InputMapper.gson()
					.fromJson(historyOBJ.get("developmentHistory"), BenChildDevelopmentHistory.class);

			if (null != benChildDevelopmentHistory) {
				developmentHistorySuccessFlag = commonNurseServiceImpl
						.updateChildDevelopmentHistory(benChildDevelopmentHistory);
			}

		} else {
			developmentHistorySuccessFlag = 1;
		}

		int historyUpdateSuccessFlag = 0;

		if (pastHistorySuccessFlag > 0 && comrbidSuccessFlag > 0 && medicationSuccessFlag > 0
				&& allergyHistorySuccessFlag > 0 && familyHistorySuccessFlag > 0 && obstetricSuccessFlag > 0
				&& childVaccineSuccessFlag > 0 && personalHistorySuccessFlag > 0 && menstrualHistorySuccessFlag > 0
				&& immunizationSuccessFlag > 0 && childFeedingSuccessFlag > 0 && perinatalHistorySuccessFlag > 0
				&& developmentHistorySuccessFlag > 0) {

			historyUpdateSuccessFlag = pastHistorySuccessFlag;
		}
		return historyUpdateSuccessFlag;
	}

	/**
	 * 
	 * @param requestOBJ
	 * @return success or failure flag for vitals data updating
	 */
	@Transactional(rollbackFor = Exception.class)
	public int updateBenVitalDetails(JsonObject vitalDetailsOBJ) throws Exception {
		int vitalSuccessFlag = 0;
		int anthropometrySuccessFlag = 0;
		int phyVitalSuccessFlag = 0;
		// Save Physical Anthropometry && Physical Vital Details
		if (vitalDetailsOBJ != null) {
			BenAnthropometryDetail benAnthropometryDetail = InputMapper.gson().fromJson(vitalDetailsOBJ,
					BenAnthropometryDetail.class);
			BenPhysicalVitalDetail benPhysicalVitalDetail = InputMapper.gson().fromJson(vitalDetailsOBJ,
					BenPhysicalVitalDetail.class);

			anthropometrySuccessFlag = commonNurseServiceImpl.updateANCAnthropometryDetails(benAnthropometryDetail);
			phyVitalSuccessFlag = commonNurseServiceImpl.updateANCPhysicalVitalDetails(benPhysicalVitalDetail);

			if (anthropometrySuccessFlag > 0 && phyVitalSuccessFlag > 0) {
				vitalSuccessFlag = anthropometrySuccessFlag;
			}
		} else {
			vitalSuccessFlag = 1;
		}

		return vitalSuccessFlag;
	}

	// get nurse data
	public String getBenCovidNurseData(Long benRegID, Long visitCode) throws IEMRException {
		Map<String, Object> resMap = new HashMap<>();

		resMap.put("covidDetails", new Gson().toJson(getCovidDetails(benRegID, visitCode)));

		resMap.put("vitals", getBeneficiaryVitalDetails(benRegID, visitCode));

		resMap.put("history", getBenCovid19HistoryDetails(benRegID, visitCode));

		resMap.put("cdss", getBeneficiaryCdssDetails(benRegID, visitCode));

		return resMap.toString();
	}

	/// --------------- start of saving doctor data ------------------------
	@Transactional(rollbackFor = Exception.class)
	public Long saveDoctorData(JsonObject requestOBJ, String Authorization) throws Exception {
		Long saveSuccessFlag = null;
		Long prescriptionID = null;
		Long investigationSuccessFlag = null;
		Integer findingSuccessFlag = null;
		Integer prescriptionSuccessFlag = null;
		// Long diagnosisSuccessFlag = null;
		Long referSaveSuccessFlag = null;
		Integer tcRequestStatusFlag = null;

		Boolean doctorSignatureFlag = false;
		if (requestOBJ.has("doctorSignatureFlag")
				&& !requestOBJ.get("doctorSignatureFlag").isJsonNull()) {
			doctorSignatureFlag = requestOBJ.get("doctorSignatureFlag").getAsBoolean();
		}

		if (requestOBJ != null) {
			TeleconsultationRequestOBJ tcRequestOBJ = null;
			// TcSpecialistSlotBookingRequestOBJ tcSpecialistSlotBookingRequestOBJ = null;
			CommonUtilityClass commonUtilityClass = InputMapper.gson().fromJson(requestOBJ, CommonUtilityClass.class);

			tcRequestOBJ = commonServiceImpl.createTcRequest(requestOBJ, commonUtilityClass, Authorization);

			JsonArray testList = null;
			JsonArray drugList = null;

			Boolean isTestPrescribed = false;
			Boolean isMedicinePrescribed = false;

			// checking if test is prescribed
			if (requestOBJ.has("investigation") && !requestOBJ.get("investigation").isJsonNull()
					&& requestOBJ.get("investigation") != null) {
				testList = requestOBJ.getAsJsonObject("investigation").getAsJsonArray("laboratoryList");
				if (testList != null && !testList.isJsonNull() && testList.size() > 0)
					isTestPrescribed = true;
			}

			// checking if medicine is prescribed
			if (requestOBJ.has("prescription") && !requestOBJ.get("prescription").isJsonNull()
					&& requestOBJ.get("prescription") != null) {
				drugList = requestOBJ.getAsJsonArray("prescription");
				if (drugList != null && !drugList.isJsonNull() && drugList.size() > 0) {
					isMedicinePrescribed = true;
				}
			}

			// save findings
			if (requestOBJ.has("findings") && !requestOBJ.get("findings").isJsonNull()) {
				WrapperAncFindings wrapperAncFindings = InputMapper.gson().fromJson(requestOBJ.get("findings"),
						WrapperAncFindings.class);
				findingSuccessFlag = commonDoctorServiceImpl.saveDocFindings(wrapperAncFindings);

			} else {
				findingSuccessFlag = 1;
			}

			String instruction = null;
			if (requestOBJ.has("diagnosis") && !requestOBJ.get("diagnosis").isJsonNull()
					&& requestOBJ.get("diagnosis").getAsJsonObject().has("specialistDiagnosis")
					&& !requestOBJ.get("diagnosis").getAsJsonObject().get("specialistDiagnosis").isJsonNull()) {
				instruction = requestOBJ.get("diagnosis").getAsJsonObject().get("specialistDiagnosis").getAsString();
			}

			String doctorDiagnosis = null;
			if (requestOBJ.has("diagnosis") && !requestOBJ.get("diagnosis").isJsonNull()
					&& requestOBJ.get("diagnosis").getAsJsonObject().has("doctorDiagnosis")
					&& !requestOBJ.get("diagnosis").getAsJsonObject().get("doctorDiagnosis").isJsonNull()) {
				doctorDiagnosis = requestOBJ.get("diagnosis").getAsJsonObject().get("doctorDiagnosis").getAsString();
			}

			String prescription_counsellingProvided = null;
			if (requestOBJ.has("counsellingProvidedList") && !requestOBJ.get("counsellingProvidedList").isJsonNull()
					&& requestOBJ.get("counsellingProvidedList") != null) {
				prescription_counsellingProvided = requestOBJ.getAsJsonObject().get("counsellingProvidedList")
						.getAsString();
			}

			// generate prescription
			WrapperBenInvestigationANC wrapperBenInvestigationANC = InputMapper.gson()
					.fromJson(requestOBJ.get("investigation"), WrapperBenInvestigationANC.class);
			prescriptionID = commonNurseServiceImpl.savePrescriptionCovid(
					wrapperBenInvestigationANC.getBeneficiaryRegID(), wrapperBenInvestigationANC.getBenVisitID(),
					wrapperBenInvestigationANC.getProviderServiceMapID(), wrapperBenInvestigationANC.getCreatedBy(),
					wrapperBenInvestigationANC.getExternalInvestigations(), wrapperBenInvestigationANC.getVisitCode(),
					wrapperBenInvestigationANC.getVanID(), wrapperBenInvestigationANC.getParkingPlaceID(), instruction,
					doctorDiagnosis, prescription_counsellingProvided);

			// save diagnosis
			// not required in covid diagnosis
			// if (requestOBJ.has("diagnosis") && !requestOBJ.get("diagnosis").isJsonNull())
			// {
			// NCDCareDiagnosis ncdDiagnosis =
			// InputMapper.gson().fromJson(requestOBJ.get("diagnosis"),
			// NCDCareDiagnosis.class);
			// ncdDiagnosis.setPrescriptionID(prescriptionID);
			// diagnosisSuccessFlag =
			// ncdCareDoctorServiceImpl.saveNCDDiagnosisData(ncdDiagnosis);
			//
			// } else {
			// diagnosisSuccessFlag = new Long(1);
			// }

			// save prescribed lab test
			if (isTestPrescribed) {
				wrapperBenInvestigationANC.setPrescriptionID(prescriptionID);
				investigationSuccessFlag = commonNurseServiceImpl.saveBenInvestigation(wrapperBenInvestigationANC);
			} else {
				investigationSuccessFlag = new Long(1);
			}

			// save prescribed medicine
			if (isMedicinePrescribed) {
				PrescribedDrugDetail[] prescribedDrugDetail = InputMapper.gson()
						.fromJson(requestOBJ.get("prescription"), PrescribedDrugDetail[].class);
				List<PrescribedDrugDetail> prescribedDrugDetailList = Arrays.asList(prescribedDrugDetail);

				for (PrescribedDrugDetail tmpObj : prescribedDrugDetailList) {
					tmpObj.setPrescriptionID(prescriptionID);
					tmpObj.setBeneficiaryRegID(commonUtilityClass.getBeneficiaryRegID());
					tmpObj.setBenVisitID(commonUtilityClass.getBenVisitID());
					tmpObj.setVisitCode(commonUtilityClass.getVisitCode());
					tmpObj.setProviderServiceMapID(commonUtilityClass.getProviderServiceMapID());
				}
				Map<String, Object> drugSaveResult = commonNurseServiceImpl
						.saveBenPrescribedDrugsList(prescribedDrugDetailList);
				Integer r = (Integer) drugSaveResult.get("count");
				List<Long> prescribedDrugIDs = (List<Long>) drugSaveResult.get("prescribedDrugIDs");

				// Store IDs in JsonObject
				if (prescribedDrugIDs != null && !prescribedDrugIDs.isEmpty()) {
					Gson gson = new Gson();
					requestOBJ.add("savedDrugIDs", gson.toJsonTree(prescribedDrugIDs));
				}

				if (r > 0 && r != null) {
					prescriptionSuccessFlag = r;
				}

			} else {
				prescriptionSuccessFlag = 1;
			}

			// save referral details
			if (requestOBJ.has("refer") && !requestOBJ.get("refer").isJsonNull()) {
				referSaveSuccessFlag = commonDoctorServiceImpl
						.saveBenReferDetails(requestOBJ.get("refer").getAsJsonObject(), false);
			} else {
				referSaveSuccessFlag = new Long(1);
			}

			// check if all requested data saved properly
			if ((findingSuccessFlag != null && findingSuccessFlag > 0)
					&& (investigationSuccessFlag != null && investigationSuccessFlag > 0)
					&& (prescriptionSuccessFlag != null && prescriptionSuccessFlag > 0)
					&& (referSaveSuccessFlag != null && referSaveSuccessFlag > 0)) {

				// call method to update beneficiary flow table
				if (prescriptionID != null) {
					commonUtilityClass.setPrescriptionID(prescriptionID);
					commonUtilityClass.setVisitCategoryID(8);
					commonUtilityClass.setAuthorization(Authorization);

				}
				int i = commonDoctorServiceImpl.updateBenFlowtableAfterDocDataSave(commonUtilityClass, isTestPrescribed,
						isMedicinePrescribed, tcRequestOBJ, doctorSignatureFlag);

				if (i > 0)
					saveSuccessFlag = referSaveSuccessFlag;
				else
					throw new RuntimeException("Error occurred while saving data. Beneficiary status update failed");

				if (i > 0 && tcRequestOBJ != null && tcRequestOBJ.getWalkIn() == false) {
					int k = sMSGatewayServiceImpl.smsSenderGateway("schedule", commonUtilityClass.getBeneficiaryRegID(),
							tcRequestOBJ.getSpecializationID(), tcRequestOBJ.getTmRequestID(), null,
							commonUtilityClass.getCreatedBy(),
							tcRequestOBJ.getAllocationDate() != null ? String.valueOf(tcRequestOBJ.getAllocationDate())
									: "",
							null, Authorization);
				}

			} else {
				throw new RuntimeException();
			}
		} else {
			// request OBJ is null.
		}
		return saveSuccessFlag;
	}
	/// --------------- END of saving doctor data ------------------------

	public String getBenCaseRecordFromDoctorCovid19(Long benRegID, Long visitCode) {
		Map<String, Object> resMap = new HashMap<>();

		resMap.put("findings", commonDoctorServiceImpl.getFindingsDetails(benRegID, visitCode));

		// resMap.put("diagnosis",
		// ncdCareDoctorServiceImpl.getNCDCareDiagnosisDetails(benRegID, visitCode));

		String diagnosis_prescription = getCovidDiagnosisData(benRegID, visitCode);
		resMap.put("diagnosis", diagnosis_prescription);
		if (diagnosis_prescription != null) {

			PrescriptionDetail pd = new Gson().fromJson(diagnosis_prescription, PrescriptionDetail.class);
			if (pd != null && pd.getCounsellingProvided() != null) {
				resMap.put("counsellingProvidedList", new Gson().toJson(pd.getCounsellingProvided()));
			}
		}

		resMap.put("investigation", commonDoctorServiceImpl.getInvestigationDetails(benRegID, visitCode));

		resMap.put("prescription", commonDoctorServiceImpl.getPrescribedDrugs(benRegID, visitCode));

		resMap.put("Refer", commonDoctorServiceImpl.getReferralDetails(benRegID, visitCode, false));

		resMap.put("LabReport",
				new Gson().toJson(labTechnicianServiceImpl.getLabResultDataForBen(benRegID, visitCode)));

		resMap.put("GraphData", new Gson().toJson(commonNurseServiceImpl.getGraphicalTrendData(benRegID, "ncdCare")));

		resMap.put("ArchivedVisitcodeForLabResult",
				labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(benRegID, visitCode));

		return resMap.toString();
	}

	private String getCovidDiagnosisData(Long benRegID, Long visitCode) {
		Map<String, Object> diagnosisMap = new HashMap<>();
		ArrayList<PrescriptionDetail> obj = prescriptionDetailRepo
				.findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(benRegID, visitCode);
		if (obj != null && obj.size() > 0) {
			diagnosisMap.put("doctorDiagnonsis", obj.get(0).getDiagnosisProvided());
			diagnosisMap.put("specialistDiagnosis", obj.get(0).getInstruction());
			diagnosisMap.put("externalInvestigation", obj.get(0).getExternalInvestigation());
			diagnosisMap.put("prescriptionID", obj.get(0).getPrescriptionID());
			diagnosisMap.put("beneficiaryRegID", obj.get(0).getBeneficiaryRegID());
			diagnosisMap.put("visitCode", obj.get(0).getVisitCode());
			diagnosisMap.put("vanID", obj.get(0).getVanID());
			diagnosisMap.put("providerServiceMapID", obj.get(0).getProviderServiceMapID());
			diagnosisMap.put("parkingPlaceID", obj.get(0).getParkingPlaceID());
			diagnosisMap.put("createdBy", obj.get(0).getCreatedBy());
			diagnosisMap.put("counsellingProvided", obj.get(0).getCounsellingProvided());

		} else {
			diagnosisMap.put("doctorDiagnonsis", null);
			diagnosisMap.put("specialistDiagnosis", null);
			diagnosisMap.put("externalInvestigation", null);
			diagnosisMap.put("prescriptionID", null);
			diagnosisMap.put("beneficiaryRegID", null);
			diagnosisMap.put("visitCode", null);
			diagnosisMap.put("vanID", null);
			diagnosisMap.put("providerServiceMapID", null);
			diagnosisMap.put("parkingPlaceID", null);
			diagnosisMap.put("createdBy", null);
			diagnosisMap.put("counsellingProvided", null);
		}
		return new Gson().toJson(diagnosisMap);
	}

	@Transactional(rollbackFor = Exception.class)
	public Long updateCovid19DoctorData(JsonObject requestOBJ, String Authorization) throws Exception {
		Long updateSuccessFlag = null;
		Long prescriptionID = null;
		Long investigationSuccessFlag = null;
		Integer findingSuccessFlag = null;
		Integer prescriptionSuccessFlag = null;
		Long referSaveSuccessFlag = null;

		Boolean doctorSignatureFlag = false;
		if (requestOBJ.has("doctorSignatureFlag")
				&& !requestOBJ.get("doctorSignatureFlag").isJsonNull()) {
			doctorSignatureFlag = requestOBJ.get("doctorSignatureFlag").getAsBoolean();
		}

		if (requestOBJ != null) {
			TeleconsultationRequestOBJ tcRequestOBJ = null;
			// TcSpecialistSlotBookingRequestOBJ tcSpecialistSlotBookingRequestOBJ = null;
			CommonUtilityClass commonUtilityClass = InputMapper.gson().fromJson(requestOBJ, CommonUtilityClass.class);

			tcRequestOBJ = commonServiceImpl.createTcRequest(requestOBJ, commonUtilityClass, Authorization);

			JsonArray testList = null;
			JsonArray drugList = null;

			Boolean isTestPrescribed = false;
			Boolean isMedicinePrescribed = false;

			// checking if test is prescribed
			if (requestOBJ.has("investigation") && !requestOBJ.get("investigation").isJsonNull()
					&& requestOBJ.get("investigation") != null) {
				testList = requestOBJ.getAsJsonObject("investigation").getAsJsonArray("laboratoryList");
				if (testList != null && !testList.isJsonNull() && testList.size() > 0)
					isTestPrescribed = true;
			}

			// checking if medicine is prescribed
			if (requestOBJ.has("prescription") && !requestOBJ.get("prescription").isJsonNull()
					&& requestOBJ.get("prescription") != null) {
				drugList = requestOBJ.getAsJsonArray("prescription");
				if (drugList != null && !drugList.isJsonNull() && drugList.size() > 0) {
					isMedicinePrescribed = true;
				}
			}

			if (requestOBJ.has("findings") && !requestOBJ.get("findings").isJsonNull()) {

				WrapperAncFindings wrapperAncFindings = InputMapper.gson().fromJson(requestOBJ.get("findings"),
						WrapperAncFindings.class);
				findingSuccessFlag = commonDoctorServiceImpl.updateDocFindings(wrapperAncFindings);

			} else {
				findingSuccessFlag = 1;
			}

			// generate WrapperBenInvestigationANC OBJ
			WrapperBenInvestigationANC wrapperBenInvestigationANC = InputMapper.gson()
					.fromJson(requestOBJ.get("investigation"), WrapperBenInvestigationANC.class);

			// generate prescription OBJ & diagnosis OBJ
			PrescriptionDetail prescriptionDetail = null;

			String instruction = null;
			if (requestOBJ.has("diagnosis") && !requestOBJ.get("diagnosis").isJsonNull()
					&& requestOBJ.get("diagnosis").getAsJsonObject().has("specialistDiagnosis")
					&& !requestOBJ.get("diagnosis").getAsJsonObject().get("specialistDiagnosis").isJsonNull()) {
				instruction = requestOBJ.get("diagnosis").getAsJsonObject().get("specialistDiagnosis").getAsString();
			}

			String doctorDiagnosis = null;
			if (requestOBJ.has("diagnosis") && !requestOBJ.get("diagnosis").isJsonNull()
					&& requestOBJ.get("diagnosis").getAsJsonObject().has("doctorDiagnosis")
					&& !requestOBJ.get("diagnosis").getAsJsonObject().get("doctorDiagnosis").isJsonNull()) {
				doctorDiagnosis = requestOBJ.get("diagnosis").getAsJsonObject().get("doctorDiagnosis").getAsString();
			}

			if (requestOBJ.has("diagnosis") && !requestOBJ.get("diagnosis").isJsonNull()) {
				prescriptionDetail = InputMapper.gson().fromJson(requestOBJ.get("diagnosis"), PrescriptionDetail.class);
				prescriptionDetail.setExternalInvestigation(wrapperBenInvestigationANC.getExternalInvestigations());
				prescriptionID = prescriptionDetail.getPrescriptionID();

				if (commonUtilityClass.getIsSpecialist() && instruction != null)
					prescriptionDetail.setInstruction(instruction);

				if (!commonUtilityClass.getIsSpecialist() && doctorDiagnosis != null)
					prescriptionDetail.setDiagnosisProvided(doctorDiagnosis);
			}

			if (requestOBJ.has("counsellingProvidedList") && !requestOBJ.get("counsellingProvidedList").isJsonNull()
					&& requestOBJ.get("counsellingProvidedList") != null) {
				prescriptionDetail.setCounsellingProvided(
						requestOBJ.getAsJsonObject().get("counsellingProvidedList").getAsString());
			} else
				prescriptionDetail.setCounsellingProvided("");

			// update prescription
			if (prescriptionDetail != null) {
				int p = commonNurseServiceImpl.updatePrescription(prescriptionDetail);
			}

			// update prescribed lab test
			if (isTestPrescribed) {
				wrapperBenInvestigationANC.setPrescriptionID(prescriptionID);
				investigationSuccessFlag = commonNurseServiceImpl.saveBenInvestigation(wrapperBenInvestigationANC);
			} else {
				investigationSuccessFlag = new Long(1);
			}

			// update prescribed medicine
			if (isMedicinePrescribed) {
				PrescribedDrugDetail[] prescribedDrugDetail = InputMapper.gson()
						.fromJson(requestOBJ.get("prescription"), PrescribedDrugDetail[].class);
				List<PrescribedDrugDetail> prescribedDrugDetailList = Arrays.asList(prescribedDrugDetail);

				for (PrescribedDrugDetail tmpObj : prescribedDrugDetailList) {
					tmpObj.setPrescriptionID(prescriptionID);
					tmpObj.setBeneficiaryRegID(commonUtilityClass.getBeneficiaryRegID());
					tmpObj.setBenVisitID(commonUtilityClass.getBenVisitID());
					tmpObj.setVisitCode(commonUtilityClass.getVisitCode());
					tmpObj.setProviderServiceMapID(commonUtilityClass.getProviderServiceMapID());
				}
				Map<String, Object> drugSaveResult = commonNurseServiceImpl
						.saveBenPrescribedDrugsList(prescribedDrugDetailList);
				Integer r = (Integer) drugSaveResult.get("count");
				List<Long> prescribedDrugIDs = (List<Long>) drugSaveResult.get("prescribedDrugIDs");

				// Store IDs in JsonObject
				if (prescribedDrugIDs != null && !prescribedDrugIDs.isEmpty()) {
					Gson gson = new Gson();
					requestOBJ.add("savedDrugIDs", gson.toJsonTree(prescribedDrugIDs));
				}
				if (r > 0 && r != null) {
					prescriptionSuccessFlag = r;
				}
			} else {
				prescriptionSuccessFlag = 1;
			}

			// update referral
			if (requestOBJ.has("refer") && !requestOBJ.get("refer").isJsonNull()) {
				referSaveSuccessFlag = commonDoctorServiceImpl
						.updateBenReferDetails(requestOBJ.get("refer").getAsJsonObject(), false);
			} else {
				referSaveSuccessFlag = new Long(1);
			}

			// check if all data updated successfully
			if ((findingSuccessFlag != null && findingSuccessFlag > 0)
					&& (investigationSuccessFlag != null && investigationSuccessFlag > 0)
					&& (prescriptionSuccessFlag != null && prescriptionSuccessFlag > 0)
					&& (referSaveSuccessFlag != null && referSaveSuccessFlag > 0)) {

				// call method to update beneficiary flow table
				if (prescriptionID != null) {
					commonUtilityClass.setPrescriptionID(prescriptionID);
					commonUtilityClass.setVisitCategoryID(8);
					commonUtilityClass.setAuthorization(Authorization);

				}
				int i = commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(commonUtilityClass,
						isTestPrescribed, isMedicinePrescribed, tcRequestOBJ, doctorSignatureFlag);

				if (i > 0)
					updateSuccessFlag = investigationSuccessFlag;
				else
					throw new RuntimeException("Error occurred while saving data. Beneficiary status update failed");

				if (i > 0 && tcRequestOBJ != null && tcRequestOBJ.getWalkIn() == false) {
					int k = sMSGatewayServiceImpl.smsSenderGateway("schedule", commonUtilityClass.getBeneficiaryRegID(),
							tcRequestOBJ.getSpecializationID(), tcRequestOBJ.getTmRequestID(), null,
							commonUtilityClass.getCreatedBy(),
							tcRequestOBJ.getAllocationDate() != null ? String.valueOf(tcRequestOBJ.getAllocationDate())
									: "",
							null, Authorization);
				}

			} else {
				throw new RuntimeException();
			}
		} else {
			// request OBJ is null.
		}
		return updateSuccessFlag;
	}

}
