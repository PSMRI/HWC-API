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
package com.iemr.hwc.service.family_planning;

import java.sql.Timestamp;
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
import com.iemr.hwc.data.anc.BenAdherence;
import com.iemr.hwc.data.anc.WrapperAncFindings;
import com.iemr.hwc.data.anc.WrapperBenInvestigationANC;
import com.iemr.hwc.data.family_planning.FPDispenseDetails;
import com.iemr.hwc.data.family_planning.FPIecCounsellingDetails;
import com.iemr.hwc.data.family_planning.FamilyPlanningReproductive;
import com.iemr.hwc.data.nurse.BenAnthropometryDetail;
import com.iemr.hwc.data.nurse.BenPhysicalVitalDetail;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.nurse.CDSS;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.quickConsultation.BenChiefComplaint;
import com.iemr.hwc.data.quickConsultation.PrescribedDrugDetail;
import com.iemr.hwc.data.quickConsultation.PrescriptionDetail;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.repo.family_planning.FPDispenseDetailsRepo;
import com.iemr.hwc.repo.family_planning.FPIecCounsellingDetailsRepo;
import com.iemr.hwc.repo.family_planning.FamilyPlanningReproductiveRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.nurse.CDSSRepo;
import com.iemr.hwc.repo.nurse.anc.BenAdherenceRepo;
import com.iemr.hwc.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonServiceImpl;
import com.iemr.hwc.service.generalOPD.GeneralOPDDoctorServiceImpl;
import com.iemr.hwc.service.labtechnician.LabTechnicianServiceImpl;
import com.iemr.hwc.service.tele_consultation.SMSGatewayServiceImpl;
import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.mapper.InputMapper;

@Service
public class FamilyPlanningServiceImpl implements FamilyPlanningService {

	@Autowired
	private CommonNurseServiceImpl commonNurseServiceImpl;

	@Autowired
	private CommonServiceImpl commonServiceImpl;

	@Autowired
	private FamilyPlanningReproductiveRepo familyPlanningReproductiveRepo;

	@Autowired
	private FPIecCounsellingDetailsRepo fPIecCounsellingDetailsRepo;

	@Autowired
	private FPDispenseDetailsRepo fPDispenseDetailsRepo;

	@Autowired
	private CommonDoctorServiceImpl commonDoctorServiceImpl;

	@Autowired
	private SMSGatewayServiceImpl sMSGatewayServiceImpl;

	@Autowired
	private LabTechnicianServiceImpl labTechnicianServiceImpl;

	@Autowired
	private GeneralOPDDoctorServiceImpl generalOPDDoctorServiceImpl;

	@Autowired
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;
	
	private BenVisitDetailRepo benVisitDetailRepo;
	@Autowired
	private BenChiefComplaintRepo benChiefComplaintRepo;
	@Autowired
	private BenAdherenceRepo benAdherenceRepo;

	@Autowired
	private CDSSRepo cdssRepo;

	@Override
	public String saveNurseDataFP(JsonObject requestOBJ, String Authorization) throws Exception {

		// logic to parse and save data in DB
		Map<String, String> responseMap = new HashMap<String, String>();
		if (requestOBJ != null && requestOBJ.has("visitDetails") && !requestOBJ.get("visitDetails").isJsonNull()) {
			CommonUtilityClass nurseUtilityClass = InputMapper.gson().fromJson(requestOBJ, CommonUtilityClass.class);

			JsonObject tmpOBJ = requestOBJ.getAsJsonObject("visitDetails").getAsJsonObject("visitDetails");
			// save visit details and generate visit-code
			Map<String, Long> visitIdAndCodeMap = saveBenVisitDetails(requestOBJ.getAsJsonObject("visitDetails"),
					nurseUtilityClass);

			if (visitIdAndCodeMap != null && visitIdAndCodeMap.size() > 0 && visitIdAndCodeMap.containsKey("visitID")
					&& visitIdAndCodeMap.containsKey("visitCode")) {
				nurseUtilityClass.setVisitCode(visitIdAndCodeMap.get("visitCode"));
				nurseUtilityClass.setBenVisitID(visitIdAndCodeMap.get("visitID"));

				// create tc request
				TeleconsultationRequestOBJ tcRequestOBJ = commonServiceImpl.createTcRequest(requestOBJ,
						nurseUtilityClass, Authorization);

				// call method to save Vital data
				saveBenANCVitalDetails(requestOBJ.getAsJsonObject("vitalDetails"), nurseUtilityClass.getBenVisitID(),
						nurseUtilityClass.getVisitCode());

				// save family Planning & Reproductive Details
				if (requestOBJ.has("familyPlanningReproductiveDetails")
						&& !requestOBJ.get("familyPlanningReproductiveDetails").isJsonNull()) {
					FamilyPlanningReproductive familyPlanningReproductive = InputMapper.gson().fromJson(
							requestOBJ.getAsJsonObject("familyPlanningReproductiveDetails"),
							FamilyPlanningReproductive.class);

					if (familyPlanningReproductive != null) {
						familyPlanningReproductive.setBeneficiaryRegID(nurseUtilityClass.getBeneficiaryRegID());
						familyPlanningReproductive.setVisitCode(nurseUtilityClass.getVisitCode());
						familyPlanningReproductive.setBenVisitID(nurseUtilityClass.getBenVisitID());
						familyPlanningReproductive.setCreatedBy(nurseUtilityClass.getCreatedBy());
						familyPlanningReproductive.setVanID(nurseUtilityClass.getVanID());
						familyPlanningReproductive.setParkingPlaceID(nurseUtilityClass.getParkingPlaceID());
						familyPlanningReproductive.setProviderServiceMapID(nurseUtilityClass.getProviderServiceMapID());

						if (familyPlanningReproductive.getCurrentlyUsingFpMethod() != null
								&& familyPlanningReproductive.getCurrentlyUsingFpMethod().length > 0) {
							StringBuffer sb = new StringBuffer();
							for (String s : familyPlanningReproductive.getCurrentlyUsingFpMethod()) {
								sb.append(s).append("||");
							}
							if (sb.length() >= 2)
								familyPlanningReproductive
										.setCurrentlyUsingFpMethodDB(sb.substring(0, sb.length() - 2));
						}

						// save using repo
						familyPlanningReproductiveRepo.save(familyPlanningReproductive);

					}
				}

				// save family Planning & Reproductive Details
				if (requestOBJ.has("iecAndCounsellingDetails")
						&& !requestOBJ.get("iecAndCounsellingDetails").isJsonNull()) {
					FPIecCounsellingDetails fPIecCounsellingDetails = InputMapper.gson().fromJson(
							requestOBJ.getAsJsonObject("iecAndCounsellingDetails"), FPIecCounsellingDetails.class);

					if (fPIecCounsellingDetails != null) {
						fPIecCounsellingDetails.setBeneficiaryRegID(nurseUtilityClass.getBeneficiaryRegID());
						fPIecCounsellingDetails.setVisitCode(nurseUtilityClass.getVisitCode());
						fPIecCounsellingDetails.setBenVisitID(nurseUtilityClass.getBenVisitID());
						fPIecCounsellingDetails.setCreatedBy(nurseUtilityClass.getCreatedBy());
						fPIecCounsellingDetails.setVanID(nurseUtilityClass.getVanID());
						fPIecCounsellingDetails.setParkingPlaceID(nurseUtilityClass.getParkingPlaceID());
						fPIecCounsellingDetails.setProviderServiceMapID(nurseUtilityClass.getProviderServiceMapID());

						if (fPIecCounsellingDetails.getCounselledOn() != null
								&& fPIecCounsellingDetails.getCounselledOn().length > 0) {
							StringBuffer sb = new StringBuffer();
							for (String s : fPIecCounsellingDetails.getCounselledOn()) {
								sb.append(s).append("||");
							}
							if (sb.length() >= 2)
								fPIecCounsellingDetails.setCounselledOnDB(sb.substring(0, sb.length() - 2));
						}

						if (fPIecCounsellingDetails.getTypeOfContraceptiveOpted() != null
								&& fPIecCounsellingDetails.getTypeOfContraceptiveOpted().length > 0) {
							StringBuffer sb = new StringBuffer();
							for (String s : fPIecCounsellingDetails.getTypeOfContraceptiveOpted()) {
								sb.append(s).append("||");
							}
							if (sb.length() >= 2)
								fPIecCounsellingDetails.setTypeOfContraceptiveOptedDB(sb.substring(0, sb.length() - 2));
						}

						// save using repo
						fPIecCounsellingDetailsRepo.save(fPIecCounsellingDetails);

					}

				}

				// save family Planning & Reproductive Details
				if (requestOBJ.has("dispensationDetails") && !requestOBJ.get("dispensationDetails").isJsonNull()) {
					FPDispenseDetails fPDispenseDetails = InputMapper.gson()
							.fromJson(requestOBJ.getAsJsonObject("dispensationDetails"), FPDispenseDetails.class);

					if (fPDispenseDetails != null) {
						fPDispenseDetails.setBeneficiaryRegID(nurseUtilityClass.getBeneficiaryRegID());
						fPDispenseDetails.setVisitCode(nurseUtilityClass.getVisitCode());
						fPDispenseDetails.setBenVisitID(nurseUtilityClass.getBenVisitID());
						fPDispenseDetails.setCreatedBy(nurseUtilityClass.getCreatedBy());
						fPDispenseDetails.setVanID(nurseUtilityClass.getVanID());
						fPDispenseDetails.setParkingPlaceID(nurseUtilityClass.getParkingPlaceID());
						fPDispenseDetails.setProviderServiceMapID(nurseUtilityClass.getProviderServiceMapID());

						if (fPDispenseDetails.getTypeOfContraceptivePrescribed() != null
								&& fPDispenseDetails.getTypeOfContraceptivePrescribed().length > 0) {
							StringBuffer sb = new StringBuffer();
							for (String s : fPDispenseDetails.getTypeOfContraceptivePrescribed()) {
								sb.append(s).append("||");
							}
							if (sb.length() >= 2)
								fPDispenseDetails.setTypeOfContraceptivePrescribedDB(sb.substring(0, sb.length() - 2));
						}

						// save using repo
						fPDispenseDetailsRepo.save(fPDispenseDetails);
					}
				}

				int i = updateBenStatusFlagAfterNurseSaveSuccess(tmpOBJ, nurseUtilityClass.getBenVisitID(),
						nurseUtilityClass.getBenFlowID(), nurseUtilityClass.getVisitCode(),
						nurseUtilityClass.getVanID(), tcRequestOBJ);

				if (i > 0 && tcRequestOBJ != null && tcRequestOBJ.getWalkIn() == false) {
					int k = sMSGatewayServiceImpl.smsSenderGateway("schedule", nurseUtilityClass.getBeneficiaryRegID(),
							tcRequestOBJ.getSpecializationID(), tcRequestOBJ.getTmRequestID(), null,
							nurseUtilityClass.getCreatedBy(),
							tcRequestOBJ.getAllocationDate() != null ? String.valueOf(tcRequestOBJ.getAllocationDate())
									: "",
							null, Authorization);
				}

			} else
				throw new RuntimeException("Error occurred while creating beneficiary visit");

			if (nurseUtilityClass.getVisitCode() != null) {
				responseMap.put("visitCode", nurseUtilityClass.getVisitCode().toString());
				responseMap.put("response", "Data saved successfully");
			}

		} else
			throw new IEMRException("Invalid request object - Visit details is not valid - NULL");

		return new Gson().toJson(responseMap);
	}
	
	@Override
	public void deleteVisitDetails(JsonObject requestOBJ) throws Exception {
		if (requestOBJ != null && requestOBJ.has("visitDetails") && !requestOBJ.get("visitDetails").isJsonNull()) {

			CommonUtilityClass nurseUtilityClass = InputMapper.gson().fromJson(requestOBJ, CommonUtilityClass.class);

			Long visitCode = benVisitDetailRepo.getVisitCode(nurseUtilityClass.getBeneficiaryRegID(),
					nurseUtilityClass.getProviderServiceMapID());

			if (visitCode != null) {
				benChiefComplaintRepo.deleteVisitDetails(visitCode);
				benAdherenceRepo.deleteVisitDetails(visitCode);
				cdssRepo.deleteVisitDetails(visitCode);
				benVisitDetailRepo.deleteVisitDetails(visitCode);
			}

		}

	}

	// method for updating ben flow status flag for nurse
	private int updateBenStatusFlagAfterNurseSaveSuccess(JsonObject tmpOBJ, Long benVisitID, Long benFlowID,
			Long benVisitCode, Integer vanID, TeleconsultationRequestOBJ tcRequestOBJ) {
		short nurseFlag = (short) 9;
		short docFlag = (short) 1;
		short labIteration = (short) 0;

		short specialistFlag = (short) 0;
		Timestamp tcDate = null;
		Integer tcSpecialistUserID = null;

		if (tcRequestOBJ != null && tcRequestOBJ.getUserID() != null && tcRequestOBJ.getAllocationDate() != null) {
			specialistFlag = (short) 1;
			tcDate = tcRequestOBJ.getAllocationDate();
			tcSpecialistUserID = tcRequestOBJ.getUserID();
		} else
			specialistFlag = (short) 0;

		int i = commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(benFlowID,
				tmpOBJ.get("beneficiaryRegID").getAsLong(), benVisitID, tmpOBJ.get("visitReason").getAsString(),
				tmpOBJ.get("visitCategory").getAsString(), nurseFlag, docFlag, labIteration, (short) 0, (short) 0,
				benVisitCode, vanID, specialistFlag, tcDate, tcSpecialistUserID);

		return i;
	}

	/**
	 * 
	 * @param requestOBJ
	 * @return success or failure flag for visitDetails data saving
	 */

	private Map<String, Long> saveBenVisitDetails(JsonObject visitDetailsOBJ, CommonUtilityClass nurseUtilityClass)
			throws IEMRException {
		Map<String, Long> visitIdAndCodeMap = new HashMap<>();
		Long benVisitID = null;

		if (visitDetailsOBJ != null && visitDetailsOBJ.has("visitDetails")
				&& !visitDetailsOBJ.get("visitDetails").isJsonNull()) {

			CommonUtilityClass commonUtilityClass = InputMapper.gson().fromJson(visitDetailsOBJ,
					CommonUtilityClass.class);

			BeneficiaryVisitDetail benVisitDetailsOBJ = InputMapper.gson().fromJson(visitDetailsOBJ.get("visitDetails"),
					BeneficiaryVisitDetail.class);

			benVisitID = commonNurseServiceImpl.saveBeneficiaryVisitDetails(benVisitDetailsOBJ);

			// 07-06-2018 visit code
			Long benVisitCode = commonNurseServiceImpl.generateVisitCode(benVisitID, nurseUtilityClass.getVanID(),
					nurseUtilityClass.getSessionID());

			if (benVisitID != null && benVisitID > 0 && benVisitCode != null && benVisitCode > 0) {
				if (visitDetailsOBJ.has("chiefComplaints") && !visitDetailsOBJ.get("chiefComplaints").isJsonNull()) {
					// Save Ben Chief Complaints
					BenChiefComplaint[] benChiefComplaintArray = InputMapper.gson()
							.fromJson(visitDetailsOBJ.get("chiefComplaints"), BenChiefComplaint[].class);

					List<BenChiefComplaint> benChiefComplaintList = Arrays.asList(benChiefComplaintArray);
					if (null != benChiefComplaintList && benChiefComplaintList.size() > 0) {
						for (BenChiefComplaint benChiefComplaint : benChiefComplaintList) {
							benChiefComplaint.setBenVisitID(benVisitID);
							benChiefComplaint.setVisitCode(benVisitCode);
						}
					}
					commonNurseServiceImpl.saveBenChiefComplaints(benChiefComplaintList);
				}
				if (visitDetailsOBJ.has("adherence") && !visitDetailsOBJ.get("adherence").isJsonNull()) {
					// Save Ben Adherence
					BenAdherence benAdherence = InputMapper.gson().fromJson(visitDetailsOBJ.get("adherence"),
							BenAdherence.class);
					benAdherence.setBenVisitID(benVisitID);
					benAdherence.setVisitCode(benVisitCode);
					commonNurseServiceImpl.saveBenAdherenceDetails(benAdherence);
				}

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
							cdss.setSelectedDiagnosis(presentCheifComplaintObj.get("selectedDiagnosis").getAsString());
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
						if (diseaseSummaryObj.get("remarks") != null && !diseaseSummaryObj.get("remarks").isJsonNull())
							cdss.setRemarks(diseaseSummaryObj.get("remarks").getAsString());
						if (diseaseSummaryObj.get("action") != null && !diseaseSummaryObj.get("action").isJsonNull())
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

		return visitIdAndCodeMap;
	}

	/**
	 * 
	 * @param requestOBJ
	 * @return success or failure flag for visitDetails data saving
	 */
	private Long saveBenANCVitalDetails(JsonObject vitalDetailsOBJ, Long benVisitID, Long benVisitCode)
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

	@Override
	public String getBenVisitDetailsFrmNurseFP(Long benRegID, Long visitCode) throws Exception {
		Map<String, Object> resMap = new HashMap<>();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		BeneficiaryVisitDetail visitDetail = commonNurseServiceImpl.getCSVisitDetails(benRegID, visitCode);

		resMap.put("FP_NurseVisitDetail", gson.toJson(visitDetail));

		resMap.put("BenAdherence", commonNurseServiceImpl.getBenAdherence(benRegID, visitCode));

		resMap.put("BenChiefComplaints", commonNurseServiceImpl.getBenChiefComplaints(benRegID, visitCode));

		resMap.put("Cdss", commonNurseServiceImpl.getBenCdss(benRegID, visitCode));

		return resMap.toString();
	}

	@Override
	public String getBeneficiaryVitalDetailsFP(Long beneficiaryRegID, Long visitCode) throws Exception {
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

	@Override
	public String getBeneficiaryFPDetailsFP(Long beneficiaryRegID, Long visitCode) throws Exception {
		Map<String, Object> resMap = new HashMap<>();

		FamilyPlanningReproductive fpr = new FamilyPlanningReproductive();
		FPIecCounsellingDetails fpc = new FPIecCounsellingDetails();
		FPDispenseDetails fpd = new FPDispenseDetails();

		// followed-up case
		if (visitCode == null) {
			List<FamilyPlanningReproductive> fprList = familyPlanningReproductiveRepo
					.findByBeneficiaryRegIDOrderByIdDesc(beneficiaryRegID);
			if (fprList != null && fprList.size() > 0)
				fpr = fprList.get(0);

			List<FPIecCounsellingDetails> fpcList = fPIecCounsellingDetailsRepo
					.findByBeneficiaryRegIDOrderByIdDesc(beneficiaryRegID);
			if (fpcList != null && fpcList.size() > 0)
				fpc = fpcList.get(0);

			List<FPDispenseDetails> fpdList = fPDispenseDetailsRepo
					.findByBeneficiaryRegIDOrderByIdDesc(beneficiaryRegID);
			if (fpdList != null && fpdList.size() > 0)
				fpd = fpdList.get(0);
		} else {
			fpr = familyPlanningReproductiveRepo.findOneByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode);
			fpc = fPIecCounsellingDetailsRepo.findOneByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode);
			fpd = fPDispenseDetailsRepo.findOneByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode);
		}

		if (fpr != null && fpr.getCurrentlyUsingFpMethodDB() != null) {
			fpr.setCurrentlyUsingFpMethod(fpr.getCurrentlyUsingFpMethodDB().split("\\|\\|"));
		}

		if (fpc != null) {
			if (fpc.getCounselledOnDB() != null)
				fpc.setCounselledOn(fpc.getCounselledOnDB().split("\\|\\|"));

			if (fpc.getTypeOfContraceptiveOptedDB() != null)
				fpc.setTypeOfContraceptiveOpted(fpc.getTypeOfContraceptiveOptedDB().split("\\|\\|"));
		}

		if (fpd != null && fpd.getTypeOfContraceptivePrescribedDB() != null)
			fpd.setTypeOfContraceptivePrescribed(fpd.getTypeOfContraceptivePrescribedDB().split("\\|\\|"));

		resMap.put("familyPlanningReproductiveDetails", fpr);
		resMap.put("iecAndCounsellingDetails", fpc);
		resMap.put("dispensationDetails", fpd);

		return new Gson().toJson(resMap);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String updateFPDataFP(JsonObject requestOBJ) throws IEMRException {
		if (requestOBJ != null) {

			// update family Planning & Reproductive Details
			if (requestOBJ.has("familyPlanningReproductiveDetails")
					&& !requestOBJ.get("familyPlanningReproductiveDetails").isJsonNull()) {
				FamilyPlanningReproductive familyPlanningReproductive = InputMapper.gson().fromJson(
						requestOBJ.getAsJsonObject("familyPlanningReproductiveDetails"),
						FamilyPlanningReproductive.class);
				if (familyPlanningReproductive != null) {
					if (familyPlanningReproductive.getCurrentlyUsingFpMethod() != null
							&& familyPlanningReproductive.getCurrentlyUsingFpMethod().length > 0) {
						StringBuffer sb = new StringBuffer();
						for (String s : familyPlanningReproductive.getCurrentlyUsingFpMethod()) {
							sb.append(s).append("||");
						}
						if (sb.length() >= 2)
							familyPlanningReproductive.setCurrentlyUsingFpMethodDB(sb.substring(0, sb.length() - 2));
					}
					// save using repo
					familyPlanningReproductiveRepo.save(familyPlanningReproductive);

				}
			}

			// update iec And Counselling Details
			if (requestOBJ.has("iecAndCounsellingDetails")
					&& !requestOBJ.get("iecAndCounsellingDetails").isJsonNull()) {
				FPIecCounsellingDetails fPIecCounsellingDetails = InputMapper.gson().fromJson(
						requestOBJ.getAsJsonObject("iecAndCounsellingDetails"), FPIecCounsellingDetails.class);
				if (fPIecCounsellingDetails != null) {
					if (fPIecCounsellingDetails.getCounselledOn() != null
							&& fPIecCounsellingDetails.getCounselledOn().length > 0) {
						StringBuffer sb = new StringBuffer();
						for (String s : fPIecCounsellingDetails.getCounselledOn()) {
							sb.append(s).append("||");
						}
						if (sb.length() >= 2)
							fPIecCounsellingDetails.setCounselledOnDB(sb.substring(0, sb.length() - 2));
					}
					if (fPIecCounsellingDetails.getTypeOfContraceptiveOpted() != null
							&& fPIecCounsellingDetails.getTypeOfContraceptiveOpted().length > 0) {
						StringBuffer sb = new StringBuffer();
						for (String s : fPIecCounsellingDetails.getTypeOfContraceptiveOpted()) {
							sb.append(s).append("||");
						}
						if (sb.length() >= 2)
							fPIecCounsellingDetails.setTypeOfContraceptiveOptedDB(sb.substring(0, sb.length() - 2));
					}
					// save using repo
					fPIecCounsellingDetailsRepo.save(fPIecCounsellingDetails);
				}
			}

			// update dispensation Details
			if (requestOBJ.has("dispensationDetails") && !requestOBJ.get("dispensationDetails").isJsonNull()) {
				FPDispenseDetails fPDispenseDetails = InputMapper.gson()
						.fromJson(requestOBJ.getAsJsonObject("dispensationDetails"), FPDispenseDetails.class);
				if (fPDispenseDetails != null) {
					if (fPDispenseDetails.getTypeOfContraceptivePrescribed() != null
							&& fPDispenseDetails.getTypeOfContraceptivePrescribed().length > 0) {
						StringBuffer sb = new StringBuffer();
						for (String s : fPDispenseDetails.getTypeOfContraceptivePrescribed()) {
							sb.append(s).append("||");
						}
						if (sb.length() >= 2)
							fPDispenseDetails.setTypeOfContraceptivePrescribedDB(sb.substring(0, sb.length() - 2));
					}
					// save using repo
					fPDispenseDetailsRepo.save(fPDispenseDetails);
				}
			}
		} else
			throw new IEMRException("Invalid request - NULL");

		return "Data updated successfully";
	}

	/**
	 * 
	 * @param requestOBJ
	 * @return success or failure flag for vitals data updating
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateBenVitalDetailsFP(JsonObject vitalDetailsOBJ) throws Exception {

		// update Physical Anthropometry && Physical Vital Details
		if (vitalDetailsOBJ != null) {
			BenAnthropometryDetail benAnthropometryDetail = InputMapper.gson().fromJson(vitalDetailsOBJ,
					BenAnthropometryDetail.class);
			BenPhysicalVitalDetail benPhysicalVitalDetail = InputMapper.gson().fromJson(vitalDetailsOBJ,
					BenPhysicalVitalDetail.class);

			int i = commonNurseServiceImpl.updateANCAnthropometryDetails(benAnthropometryDetail);
			int j = commonNurseServiceImpl.updateANCPhysicalVitalDetails(benPhysicalVitalDetail);

		}

		return 1;
	}

	// update doctor data
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long updateDoctorDataFP(JsonObject requestOBJ, String Authorization) throws Exception {

		if (requestOBJ != null) {
			TeleconsultationRequestOBJ tcRequestOBJ = null;
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

			// update findings
			if (requestOBJ.has("findings") && !requestOBJ.get("findings").isJsonNull()) {
				WrapperAncFindings wrapperAncFindings = InputMapper.gson().fromJson(requestOBJ.get("findings"),
						WrapperAncFindings.class);

				commonDoctorServiceImpl.saveDocFindings(wrapperAncFindings);

			}

			// creating prescription OBJ
			PrescriptionDetail prescriptionDetail = null;
			if (requestOBJ.has("diagnosis") && !requestOBJ.get("diagnosis").isJsonNull()) {
				// JsonObject diagnosisObj = requestOBJ.getAsJsonObject("diagnosis");
				prescriptionDetail = InputMapper.gson().fromJson(requestOBJ.get("diagnosis"), PrescriptionDetail.class);
			}

			// generating WrapperBenInvestigationANC OBJ
			WrapperBenInvestigationANC wrapperBenInvestigationANCTMP = InputMapper.gson()
					.fromJson(requestOBJ.get("investigation"), WrapperBenInvestigationANC.class);

			if (prescriptionDetail != null) {
				prescriptionDetail.setExternalInvestigation(wrapperBenInvestigationANCTMP.getExternalInvestigations());
				if (commonUtilityClass.getTreatmentsOnSideEffects() != null)
					prescriptionDetail.setTreatmentsOnSideEffects(commonUtilityClass.getTreatmentsOnSideEffects());

				commonUtilityClass.setPrescriptionID(prescriptionDetail.getPrescriptionID());
			}

			if (requestOBJ.has("counsellingProvidedList") && !requestOBJ.get("counsellingProvidedList").isJsonNull()
					&& requestOBJ.get("counsellingProvidedList") != null) {
				prescriptionDetail.setCounsellingProvided(
						requestOBJ.getAsJsonObject().get("counsellingProvidedList").getAsString());
			} else
				prescriptionDetail.setCounsellingProvided("");

			// update prescription
			int p = commonNurseServiceImpl.updatePrescription(prescriptionDetail);

			// save prescribed lab test
			if (isTestPrescribed == true) {
				WrapperBenInvestigationANC wrapperBenInvestigationANC = InputMapper.gson()
						.fromJson(requestOBJ.get("investigation"), WrapperBenInvestigationANC.class);

				if (wrapperBenInvestigationANC != null) {
					wrapperBenInvestigationANC.setPrescriptionID(commonUtilityClass.getPrescriptionID());
					commonNurseServiceImpl.saveBenInvestigation(wrapperBenInvestigationANC);
				}
			}

			// save prescribed medicine
			if (isMedicinePrescribed == true) {
				PrescribedDrugDetail[] prescribedDrugDetail = InputMapper.gson()
						.fromJson(requestOBJ.get("prescription"), PrescribedDrugDetail[].class);
				List<PrescribedDrugDetail> prescribedDrugDetailList = Arrays.asList(prescribedDrugDetail);

				if (prescribedDrugDetailList.size() > 0) {
					for (PrescribedDrugDetail tmpObj : prescribedDrugDetailList) {
						tmpObj.setPrescriptionID(commonUtilityClass.getPrescriptionID());
						tmpObj.setBeneficiaryRegID(commonUtilityClass.getBeneficiaryRegID());
						tmpObj.setBenVisitID(commonUtilityClass.getBenVisitID());
						tmpObj.setVisitCode(commonUtilityClass.getVisitCode());
						tmpObj.setProviderServiceMapID(commonUtilityClass.getProviderServiceMapID());
					}
					Integer r = commonNurseServiceImpl.saveBenPrescribedDrugsList(prescribedDrugDetailList);

				}

			}

			// update referral
			if (requestOBJ.has("refer") && !requestOBJ.get("refer").isJsonNull()) {
				commonDoctorServiceImpl.updateBenReferDetails(requestOBJ.get("refer").getAsJsonObject(), true);
			}

			// check if all requested data updated/saved properly

			// call method to update beneficiary flow table

			commonUtilityClass.setVisitCategoryID(9);
			commonUtilityClass.setAuthorization(Authorization);

			int i = commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(commonUtilityClass, isTestPrescribed,
					isMedicinePrescribed, tcRequestOBJ);

			if (i > 0) {
				if (tcRequestOBJ != null && tcRequestOBJ.getWalkIn() == false) {
					int k = sMSGatewayServiceImpl.smsSenderGateway("schedule", commonUtilityClass.getBeneficiaryRegID(),
							tcRequestOBJ.getSpecializationID(), tcRequestOBJ.getTmRequestID(), null,
							commonUtilityClass.getCreatedBy(),
							tcRequestOBJ.getAllocationDate() != null ? String.valueOf(tcRequestOBJ.getAllocationDate())
									: "",
							null, Authorization);
				}
			} else
				throw new RuntimeException("Error occurred while saving data. Beneficiary status update failed");

		} else
			throw new IEMRException("invalid request  - NULL");

		return (long) 1;

	}

	/// --------------- start of saving doctor data ------------------------
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int saveDoctorDataFP(JsonObject requestOBJ, String Authorization) throws Exception {
		int saveSuccessFlag = 1;
		Long prescriptionID = null;
		Long investigationSuccessFlag = null;
		Integer findingSuccessFlag = null;
		Integer prescriptionSuccessFlag = null;
		Long referSaveSuccessFlag = null;

		// Integer tcRequestStatusFlag = null;

		if (requestOBJ != null) {

			TeleconsultationRequestOBJ tcRequestOBJ = null;
			CommonUtilityClass commonUtilityClass = InputMapper.gson().fromJson(requestOBJ, CommonUtilityClass.class);

			// create TC request if applicable
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

			// creating prescription object
			PrescriptionDetail prescriptionDetail = new PrescriptionDetail();

			if (requestOBJ.has("diagnosis") && !requestOBJ.get("diagnosis").isJsonNull()) {
				JsonObject diagnosisObj = requestOBJ.getAsJsonObject("diagnosis");

				prescriptionDetail = InputMapper.gson().fromJson(diagnosisObj, PrescriptionDetail.class);
			} else {
			}

			WrapperBenInvestigationANC wrapperBenInvestigationANC = InputMapper.gson()
					.fromJson(requestOBJ.get("investigation"), WrapperBenInvestigationANC.class);
			// Save Prescription
			prescriptionDetail.setExternalInvestigation(wrapperBenInvestigationANC.getExternalInvestigations());
			if (commonUtilityClass.getTreatmentsOnSideEffects() != null)
				prescriptionDetail.setTreatmentsOnSideEffects(commonUtilityClass.getTreatmentsOnSideEffects());

			if (requestOBJ.has("counsellingProvidedList") && !requestOBJ.get("counsellingProvidedList").isJsonNull()
					&& requestOBJ.get("counsellingProvidedList") != null) {
				prescriptionDetail.setCounsellingProvided(
						requestOBJ.getAsJsonObject().get("counsellingProvidedList").getAsString());
			}

			prescriptionID = commonNurseServiceImpl.saveBenPrescription(prescriptionDetail);

			// save prescribed lab test
			if (isTestPrescribed) {
				if (wrapperBenInvestigationANC != null) {
					wrapperBenInvestigationANC.setPrescriptionID(prescriptionID);
					investigationSuccessFlag = commonNurseServiceImpl.saveBenInvestigation(wrapperBenInvestigationANC);
				}
			} else {
				investigationSuccessFlag = new Long(1);
			}

			// save prescribed medicine
			if (isMedicinePrescribed) {
				PrescribedDrugDetail[] prescribedDrugDetail = InputMapper.gson()
						.fromJson(requestOBJ.get("prescription"), PrescribedDrugDetail[].class);

				List<PrescribedDrugDetail> prescribedDrugDetailList = Arrays.asList(prescribedDrugDetail);

				if (prescribedDrugDetailList.size() > 0) {
					for (PrescribedDrugDetail tmpObj : prescribedDrugDetailList) {
						tmpObj.setPrescriptionID(prescriptionID);
						tmpObj.setBeneficiaryRegID(commonUtilityClass.getBeneficiaryRegID());
						tmpObj.setBenVisitID(commonUtilityClass.getBenVisitID());
						tmpObj.setVisitCode(commonUtilityClass.getVisitCode());
						tmpObj.setProviderServiceMapID(commonUtilityClass.getProviderServiceMapID());
					}

					Integer r = commonNurseServiceImpl.saveBenPrescribedDrugsList(prescribedDrugDetailList);
					if (r > 0 && r != null) {
						prescriptionSuccessFlag = r;
					}

				} else {
					prescriptionSuccessFlag = 1;
				}
			} else {
				prescriptionSuccessFlag = 1;
			}

			// save referral details
			if (requestOBJ.has("refer") && !requestOBJ.get("refer").isJsonNull()) {
				referSaveSuccessFlag = commonDoctorServiceImpl
						.saveBenReferDetails(requestOBJ.get("refer").getAsJsonObject(), true);
			} else {
				referSaveSuccessFlag = new Long(1);
			}

			// check if all requested data saved properly
			if ((findingSuccessFlag != null && findingSuccessFlag > 0) && (prescriptionID != null && prescriptionID > 0)
					&& (investigationSuccessFlag != null && investigationSuccessFlag > 0)
					&& (prescriptionSuccessFlag != null && prescriptionSuccessFlag > 0)
					&& (referSaveSuccessFlag != null && referSaveSuccessFlag > 0)) {

				// call method to update beneficiary flow table
				if (prescriptionID != null) {
					commonUtilityClass.setPrescriptionID(prescriptionID);
					commonUtilityClass.setVisitCategoryID(6);
					commonUtilityClass.setAuthorization(Authorization);

				}
				int i = commonDoctorServiceImpl.updateBenFlowtableAfterDocDataSave(commonUtilityClass, isTestPrescribed,
						isMedicinePrescribed, tcRequestOBJ);

				if (i > 0)
					saveSuccessFlag = 1;
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
	/// ------------------- END of saving doctor data ------------------------

	@Override
	public String getBenCaseRecordFromDoctorFP(Long benRegID, Long visitCode) throws IEMRException {
		Map<String, Object> resMap = new HashMap<>();

		resMap.put("findings", commonDoctorServiceImpl.getFindingsDetails(benRegID, visitCode));

		String diagnosis_prescription = generalOPDDoctorServiceImpl.getGeneralOPDDiagnosisDetails(benRegID, visitCode);
		resMap.put("diagnosis", diagnosis_prescription);

		if (diagnosis_prescription != null) {

			PrescriptionDetail pd = new Gson().fromJson(diagnosis_prescription, PrescriptionDetail.class);
			if (pd != null && pd.getTreatmentsOnSideEffects() != null)
				resMap.put("treatmentsOnSideEffects", new Gson().toJson(pd.getTreatmentsOnSideEffects()));
			if (pd != null && pd.getCounsellingProvided() != null)
				resMap.put("counsellingProvidedList", new Gson().toJson(pd.getCounsellingProvided()));
		}

		resMap.put("investigation", commonDoctorServiceImpl.getInvestigationDetails(benRegID, visitCode));

		resMap.put("prescription", commonDoctorServiceImpl.getPrescribedDrugs(benRegID, visitCode));

		resMap.put("Refer", commonDoctorServiceImpl.getReferralDetails(benRegID, visitCode, true));

		resMap.put("LabReport",
				new Gson().toJson(labTechnicianServiceImpl.getLabResultDataForBen(benRegID, visitCode)));

		resMap.put("GraphData", new Gson().toJson(commonNurseServiceImpl.getGraphicalTrendData(benRegID, "genOPD")));

		resMap.put("ArchivedVisitcodeForLabResult",
				labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(benRegID, visitCode));

		return resMap.toString();
	}

	@Override
	public String getNurseDataFP(Long benRegID, Long visitCode) throws Exception {
		HashMap<String, Object> resMap = new HashMap<>();

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		BeneficiaryVisitDetail visitDetail = commonNurseServiceImpl.getCSVisitDetails(benRegID, visitCode);

		resMap.put("fpNurseVisitData", gson.toJson(visitDetail));

		resMap.put("fpData", getBeneficiaryFPDetailsFP(benRegID, visitCode));
		resMap.put("vitals", getBeneficiaryVitalDetailsFP(benRegID, visitCode));
		resMap.put("cdss", getBeneficiaryCdssDetails(benRegID, visitCode));

		return resMap.toString();
	}

}
