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
package com.iemr.hwc.service.adolescent;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
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
import com.iemr.hwc.data.adolescent.T_OralVitaminProphylaxis;
import com.iemr.hwc.data.anc.WrapperAncFindings;
import com.iemr.hwc.data.anc.WrapperBenInvestigationANC;
import com.iemr.hwc.data.anc.WrapperImmunizationHistory;
import com.iemr.hwc.data.neonatal.FollowUpForImmunization;
import com.iemr.hwc.data.neonatal.ImmunizationServices;
import com.iemr.hwc.data.neonatal.InfantBirthDetails;
import com.iemr.hwc.data.neonatal.Vaccines;
import com.iemr.hwc.data.nurse.BenAnthropometryDetail;
import com.iemr.hwc.data.nurse.BenPhysicalVitalDetail;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.nurse.CDSS;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.quickConsultation.BenChiefComplaint;
import com.iemr.hwc.data.quickConsultation.PrescribedDrugDetail;
import com.iemr.hwc.data.quickConsultation.PrescriptionDetail;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.repo.adolescent.T_OralVitaminProphylaxisRepo;
import com.iemr.hwc.repo.neonatal.FollowUpForImmunizationRepo;
import com.iemr.hwc.repo.neonatal.ImmunizationServicesRepo;
import com.iemr.hwc.repo.neonatal.InfantBirthDetailsRepo;
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
public class AdolescentAndChildCareServiceImpl implements AdolescentAndChildCareService {
	@Autowired
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;
	@Autowired
	private CommonServiceImpl commonServiceImpl;
	@Autowired
	private CommonNurseServiceImpl commonNurseServiceImpl;
	@Autowired
	private SMSGatewayServiceImpl sMSGatewayServiceImpl;
	@Autowired
	private InfantBirthDetailsRepo infantBirthDetailsRepo;
	@Autowired
	private ImmunizationServicesRepo immunizationServicesRepo;
	@Autowired
	private BenVisitDetailRepo benVisitDetailRepo;
	@Autowired
	private CommonDoctorServiceImpl commonDoctorServiceImpl;
	@Autowired
	private FollowUpForImmunizationRepo followUpForImmunizationRepo;
	@Autowired
	private GeneralOPDDoctorServiceImpl generalOPDDoctorServiceImpl;
	@Autowired
	private LabTechnicianServiceImpl labTechnicianServiceImpl;
	@Autowired
	private T_OralVitaminProphylaxisRepo oralVitaminProphylaxisRepo;
	@Autowired
	private BenChiefComplaintRepo benChiefComplaintRepo;
	@Autowired
	private BenAdherenceRepo benAdherenceRepo;

	@Autowired
	private CDSSRepo cdssRepo;

	@Override
	public String saveNurseData(JsonObject requestOBJ, String Authorization) throws SQLException, Exception {

		Map<String, String> responseMap = new HashMap<String, String>();
		Long benVisitCode = null;
		TeleconsultationRequestOBJ tcRequestOBJ = null;

		if (requestOBJ != null && requestOBJ.has("visitDetails") && !requestOBJ.get("visitDetails").isJsonNull()) {

			CommonUtilityClass nurseUtilityClass = InputMapper.gson().fromJson(requestOBJ, CommonUtilityClass.class);
			// Call method to save visit details data
			Map<String, Long> visitIdAndCodeMap = saveBenVisitDetails(requestOBJ.getAsJsonObject("visitDetails"),
					nurseUtilityClass);

			Long benVisitID = null;

			if (visitIdAndCodeMap != null && visitIdAndCodeMap.size() > 0 && visitIdAndCodeMap.containsKey("visitID")
					&& visitIdAndCodeMap.containsKey("visitCode")) {
				benVisitID = visitIdAndCodeMap.get("visitID");
				benVisitCode = visitIdAndCodeMap.get("visitCode");

				nurseUtilityClass.setVisitCode(benVisitCode);
				nurseUtilityClass.setBenVisitID(benVisitID);
			}

			JsonObject tmpOBJ = requestOBJ.getAsJsonObject("visitDetails").getAsJsonObject("visitDetails");
			// Getting benflowID for ben status update
			Long benFlowID = null;
			// Above if block code replaced by below line
			benFlowID = nurseUtilityClass.getBenFlowID();

			if (benVisitID != null && benVisitID > 0) {
				// create tc request
				tcRequestOBJ = commonServiceImpl.createTcRequest(requestOBJ, nurseUtilityClass, Authorization);
				// call method to save History data
				if (requestOBJ.has("immunizationHistory") && !requestOBJ.get("immunizationHistory").isJsonNull())
					saveBenNeoNatalHistoryDetails(requestOBJ, benVisitID, benVisitCode);

				// call method to save vital data
				if (requestOBJ.has("vitalDetails") && !requestOBJ.get("vitalDetails").isJsonNull())
					saveBenVitalDetails(requestOBJ.getAsJsonObject("vitalDetails"), benVisitID, benVisitCode);

				// infant birth details save
				if (requestOBJ.has("infantBirthDetails") && !requestOBJ.get("infantBirthDetails").isJsonNull()) {
					InfantBirthDetails infantBirthDetails = InputMapper.gson()
							.fromJson(requestOBJ.get("infantBirthDetails"), InfantBirthDetails.class);

					if (infantBirthDetails != null) {
						infantBirthDetails.setVisitCode(benVisitCode);

						infantBirthDetailsRepo.save(infantBirthDetails);
					}
				}

				// immunization service
				if (requestOBJ.has("immunizationServices") && !requestOBJ.get("immunizationServices").isJsonNull()) {
					ImmunizationServices is = InputMapper.gson().fromJson(requestOBJ.get("immunizationServices"),
							ImmunizationServices.class);

					List<ImmunizationServices> isList = new ArrayList<ImmunizationServices>();
					ImmunizationServices obj;
					if (is != null && is.getVaccines() != null && is.getVaccines().size() > 0) {
						// for each vaccine, create a new db obj
						for (Vaccines v : is.getVaccines()) {
							if (v.getVaccineName() != null) {
								obj = new ImmunizationServices();

								obj.setBeneficiaryRegID(is.getBeneficiaryRegID());
								obj.setVanID(is.getVanID());
								obj.setProviderServiceMapID(is.getProviderServiceMapID());
								obj.setParkingPlaceID(is.getParkingPlaceID());
								obj.setCreatedBy(is.getCreatedBy());

								obj.setDateOfVisit(is.getDateOfVisit());
								obj.setImmunizationServicesType(is.getImmunizationServicesType());
								obj.setImmunizationServicesTypeID(is.getImmunizationServicesTypeID());
								obj.setCurrentImmunizationService(is.getCurrentImmunizationService());
								obj.setCurrentImmunizationServiceID(is.getCurrentImmunizationServiceID());

								obj.setVaccineName(v.getVaccineName());
								obj.setVaccineDose(v.getVaccineDose());
								obj.setRoute(v.getRoute());
								obj.setBatchNo(v.getBatchNo());
								obj.setSiteOfInjection(v.getSiteOfInjection());

								obj.setVisitCode(benVisitCode);

								isList.add(obj);
							}
						}

						if (isList.size() > 0)
							immunizationServicesRepo.saveAll(isList);
					}
				}

				// Oral-Vitamin-Prophylaxis data save
				if (requestOBJ.has("oralVitaminAProphylaxis")
						&& !requestOBJ.get("oralVitaminAProphylaxis").isJsonNull()) {
					T_OralVitaminProphylaxis oralVitaminProphylaxis = InputMapper.gson()
							.fromJson(requestOBJ.get("oralVitaminAProphylaxis"), T_OralVitaminProphylaxis.class);

					if (oralVitaminProphylaxis != null) {
						oralVitaminProphylaxis.setVisitCode(benVisitCode);

						oralVitaminProphylaxisRepo.save(oralVitaminProphylaxis);
					}
				}

				/**
				 * We have to write new code to update ben status flow new logic
				 */
				int i = updateBenStatusFlagAfterNurseSaveSuccess(tmpOBJ, benVisitID, benFlowID, benVisitCode,
						nurseUtilityClass.getVanID(), tcRequestOBJ);

				if (i > 0 && tcRequestOBJ != null && tcRequestOBJ.getWalkIn() == false) {
					int k = sMSGatewayServiceImpl.smsSenderGateway("schedule", nurseUtilityClass.getBeneficiaryRegID(),
							tcRequestOBJ.getSpecializationID(), tcRequestOBJ.getTmRequestID(), null,
							nurseUtilityClass.getCreatedBy(),
							tcRequestOBJ.getAllocationDate() != null ? String.valueOf(tcRequestOBJ.getAllocationDate())
									: "",
							null, Authorization);
				}

			}

			if (benVisitCode != null)
				responseMap.put("visitCode", benVisitCode.toString());
			responseMap.put("response", "Data saved successfully");
		} else {
			throw new RuntimeException("Invalid request, visit details is missing");
		}

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

	private Map<String, Long> saveBenVisitDetails(JsonObject visitDetailsOBJ, CommonUtilityClass nurseUtilityClass)
			throws Exception {
		Map<String, Long> visitIdAndCodeMap = new HashMap<>();
		Long benVisitID = null;
		if (visitDetailsOBJ != null && visitDetailsOBJ.has("visitDetails")
				&& !visitDetailsOBJ.get("visitDetails").isJsonNull()) {
			// Save Beneficiary visit details
			BeneficiaryVisitDetail benVisitDetailsOBJ = InputMapper.gson().fromJson(visitDetailsOBJ.get("visitDetails"),
					BeneficiaryVisitDetail.class);
			benVisitID = commonNurseServiceImpl.saveBeneficiaryVisitDetails(benVisitDetailsOBJ);

			// generate visit code
			Long benVisitCode = commonNurseServiceImpl.generateVisitCode(benVisitID, nurseUtilityClass.getVanID(),
					nurseUtilityClass.getSessionID());

			if (benVisitID != null && benVisitID > 0 && benVisitCode != null && benVisitCode > 0) {
				if (visitDetailsOBJ.has("chiefComplaints") && !visitDetailsOBJ.get("chiefComplaints").isJsonNull()) {
					BenChiefComplaint[] benChiefComplaintArray = InputMapper.gson()
							.fromJson(visitDetailsOBJ.get("chiefComplaints"), BenChiefComplaint[].class);

					List<BenChiefComplaint> benChiefComplaintList = Arrays.asList(benChiefComplaintArray);
					if (null != benChiefComplaintList && benChiefComplaintList.size() > 0) {
						for (BenChiefComplaint benChiefComplaint : benChiefComplaintList) {
							benChiefComplaint.setBenVisitID(benVisitID);
							benChiefComplaint.setVisitCode(benVisitCode);
						}
					}
					// Save Beneficiary Chief Complaints
					commonNurseServiceImpl.saveBenChiefComplaints(benChiefComplaintList);
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

	private Long saveBenNeoNatalHistoryDetails(JsonObject generalOPDHistoryOBJ, Long benVisitID, Long benVisitCode)
			throws Exception {

		// Save Immunization History
		if (generalOPDHistoryOBJ != null && generalOPDHistoryOBJ.has("immunizationHistory")
				&& !generalOPDHistoryOBJ.get("immunizationHistory").isJsonNull()) {
			WrapperImmunizationHistory wrapperImmunizationHistory = InputMapper.gson()
					.fromJson(generalOPDHistoryOBJ.get("immunizationHistory"), WrapperImmunizationHistory.class);
			if (null != wrapperImmunizationHistory) {
				wrapperImmunizationHistory.setBenVisitID(benVisitID);
				wrapperImmunizationHistory.setVisitCode(benVisitCode);
				commonNurseServiceImpl.saveImmunizationHistory(wrapperImmunizationHistory);
			}
		}

		return (long) 1;
	}

	private Long saveBenVitalDetails(JsonObject vitalDetailsOBJ, Long benVisitID, Long benVisitCode) throws Exception {
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
		} else {
			vitalSuccessFlag = new Long(1);
		}

		return vitalSuccessFlag;
	}

	/// --------------- start of saving doctor data ------------------------
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int saveDoctorDataCAC(JsonObject requestOBJ, String Authorization) throws Exception {
		int saveSuccessFlag = 1;
		Long prescriptionID = null;

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
				commonDoctorServiceImpl.saveDocFindings(wrapperAncFindings);

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
				PrescriptionDetail tempPrescription = InputMapper.gson().fromJson(requestOBJ, PrescriptionDetail.class);
				if (tempPrescription != null && tempPrescription.getCounsellingProvidedList() != null
						&& tempPrescription.getCounsellingProvidedList().length > 0) {
					StringBuffer sb = new StringBuffer();
					for (String s : tempPrescription.getCounsellingProvidedList()) {
						sb.append(s).append("||");
					}
					if (sb.length() >= 2)
						prescriptionDetail.setCounsellingProvided(sb.substring(0, sb.length() - 2));

				}

			}
			prescriptionID = commonNurseServiceImpl.saveBenPrescription(prescriptionDetail);

			// save prescribed lab test
			if (isTestPrescribed) {
				if (wrapperBenInvestigationANC != null) {
					wrapperBenInvestigationANC.setPrescriptionID(prescriptionID);
					commonNurseServiceImpl.saveBenInvestigation(wrapperBenInvestigationANC);
				}
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

					commonNurseServiceImpl.saveBenPrescribedDrugsList(prescribedDrugDetailList);
				}
			}

			// save followUpForImmunization
			if (requestOBJ.has("followUpForImmunization") && !requestOBJ.get("followUpForImmunization").isJsonNull()
					&& requestOBJ.get("followUpForImmunization") != null) {
				FollowUpForImmunization followUpForImmunization = InputMapper.gson()
						.fromJson(requestOBJ.get("followUpForImmunization"), FollowUpForImmunization.class);

				if (followUpForImmunization != null)
					followUpForImmunizationRepo.save(followUpForImmunization);
			}

			// check if all requested data saved properly
			if (prescriptionID != null && prescriptionID > 0) {
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

	/// ************* fetch Nurse data ******************

	// fetch visit details
	@Override
	public String getBenVisitDetailsFrmNurseCAC(Long benRegID, Long visitCode) throws Exception {
		Map<String, Object> resMap = new HashMap<>();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

		BeneficiaryVisitDetail visitDetail = commonNurseServiceImpl.getCSVisitDetails(benRegID, visitCode);

		resMap.put("cacNurseVisitDetail", gson.toJson(visitDetail));

		resMap.put("BenChiefComplaints", commonNurseServiceImpl.getBenChiefComplaints(benRegID, visitCode));

		resMap.put("Cdss", commonNurseServiceImpl.getBenCdss(benRegID, visitCode));

		return resMap.toString();
	}

	// fetch history & born-birth details
	@Override
	public String getBirthAndImmuniHistory(Long benRegID, Long visitCode) throws IEMRException {
		Map<String, Object> HistoryDetailsMap = new HashMap<String, Object>();

		if (visitCode == null) {
			visitCode = benVisitDetailRepo.getLatestVisitCodeForGivenVC(benRegID,
					"Childhood & Adolescent Healthcare Services");

			if (visitCode == null)
				throw new IEMRException("no previous information found. please capture latest information");
		}

		HistoryDetailsMap.put("immunizationHistory",
				commonNurseServiceImpl.getImmunizationHistory(benRegID, visitCode));

		InfantBirthDetails ibObj = infantBirthDetailsRepo.findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode);

		HistoryDetailsMap.put("infantBirthDetails", ibObj);

		return new Gson().toJson(HistoryDetailsMap);
	}

	@Override
	public String getBeneficiaryVitalDetails(Long beneficiaryRegID, Long visitCode) throws Exception {
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
	public String getBeneficiaryImmunizationServiceDetails(Long beneficiaryRegID, Long visitCode) throws Exception {
		Map<String, Object> resMap = new HashMap<>();
		Vaccines vaccines;
		List<Vaccines> vaccineList = new ArrayList<>();

		List<ImmunizationServices> rsList = immunizationServicesRepo
				.findByBeneficiaryRegIDAndVisitCodeAndDeleted(beneficiaryRegID, visitCode, false);

		if (rsList != null && rsList.size() > 0) {
			ImmunizationServices output = rsList.get(0);
			for (ImmunizationServices is : rsList) {
				if (is.getVaccineName() != null) {
					vaccines = new Vaccines();
					vaccines.setVaccineName(is.getVaccineName());
					if (is.getVaccineDose() != null)
						vaccines.setVaccineDose(is.getVaccineDose());
					if (is.getBatchNo() != null)
						vaccines.setBatchNo(is.getBatchNo());
					if (is.getRoute() != null)
						vaccines.setRoute(is.getRoute());
					if (is.getSiteOfInjection() != null)
						vaccines.setSiteOfInjection(is.getSiteOfInjection());

					vaccineList.add(vaccines);
				}
			}
			output.setVaccines(vaccineList);
			resMap.put("immunizationServices", new Gson().toJson(output));
		}

		// fetch and put oral Vitamin-A Prophylaxis
		List<T_OralVitaminProphylaxis> resultList = oralVitaminProphylaxisRepo
				.findByBeneficiaryRegIDAndVisitCodeAndDeleted(beneficiaryRegID, visitCode, false);
		if (resultList != null && resultList.size() > 0)
			resMap.put("oralVitaminAProphylaxis", new Gson().toJson(resultList.get(resultList.size() - 1)));

		return resMap.toString();
	}

	// fetch case-record
	@Override
	public String getBenCaseRecordFromDoctorCAC(Long benRegID, Long visitCode) throws IEMRException {
		Map<String, Object> resMap = new HashMap<>();

		resMap.put("findings", commonDoctorServiceImpl.getFindingsDetails(benRegID, visitCode));

		String diagnosis_prescription = generalOPDDoctorServiceImpl.getGeneralOPDDiagnosisDetails(benRegID, visitCode);
		resMap.put("diagnosis", diagnosis_prescription);

		if (diagnosis_prescription != null) {

			PrescriptionDetail pd = new Gson().fromJson(diagnosis_prescription, PrescriptionDetail.class);
			if (pd != null && pd.getCounsellingProvided() != null) {
				resMap.put("counsellingProvidedList", new Gson().toJson(pd.getCounsellingProvided().split("\\|\\|")));
			}
		}

		resMap.put("investigation", commonDoctorServiceImpl.getInvestigationDetails(benRegID, visitCode));

		resMap.put("prescription", commonDoctorServiceImpl.getPrescribedDrugs(benRegID, visitCode));

		resMap.put("LabReport",
				new Gson().toJson(labTechnicianServiceImpl.getLabResultDataForBen(benRegID, visitCode)));

		resMap.put("GraphData", new Gson().toJson(commonNurseServiceImpl.getGraphicalTrendData(benRegID, "genOPD")));

		resMap.put("ArchivedVisitcodeForLabResult",
				labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(benRegID, visitCode));

		FollowUpForImmunization fuI = followUpForImmunizationRepo.findByBeneficiaryRegIDAndVisitCode(benRegID,
				visitCode);
		if (fuI != null)
			resMap.put("followUpForImmunization", new Gson().toJson(fuI));

		return resMap.toString();
	}

	@Override
	public String getNurseDataCAC(Long benRegID, Long visitCode) throws Exception {
		HashMap<String, Object> resMap = new HashMap<>();

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		BeneficiaryVisitDetail visitDetail = commonNurseServiceImpl.getCSVisitDetails(benRegID, visitCode);

		resMap.put("adolescentNurseVisitDetail", gson.toJson(visitDetail));
		resMap.put("BenChiefComplaints", commonNurseServiceImpl.getBenChiefComplaints(benRegID, visitCode));

		resMap.put("history", getBirthAndImmuniHistory(benRegID, visitCode));
		resMap.put("vitals", getBeneficiaryVitalDetails(benRegID, visitCode));
		resMap.put("immunizationServices", getBeneficiaryImmunizationServiceDetails(benRegID, visitCode));
		resMap.put("cdss", getBeneficiaryCdssDetails(benRegID, visitCode));

		return resMap.toString();
	}

	// update vitals & anthro
	/**
	 * 
	 * @param requestOBJ
	 * @return success or failure flag for vitals data updating
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateBenVitalDetailsCAC(JsonObject vitalDetailsOBJ) throws Exception {

		// update Physical Anthropometry && Physical Vital Details
		if (vitalDetailsOBJ != null) {
			BenAnthropometryDetail benAnthropometryDetail = InputMapper.gson().fromJson(vitalDetailsOBJ,
					BenAnthropometryDetail.class);
			BenPhysicalVitalDetail benPhysicalVitalDetail = InputMapper.gson().fromJson(vitalDetailsOBJ,
					BenPhysicalVitalDetail.class);

			commonNurseServiceImpl.updateANCAnthropometryDetails(benAnthropometryDetail);
			commonNurseServiceImpl.updateANCPhysicalVitalDetails(benPhysicalVitalDetail);

		}

		return 1;
	}

	/**
	 * 
	 * @param requestOBJ
	 * @return success or failure flag for General OPD History updating by Doctor
	 * @throws ParseException
	 */
	@Transactional(rollbackFor = Exception.class)
	public int updateBenHistoryDetails(JsonObject historyOBJ) throws IEMRException, ParseException {

		// update immunization history
		if (historyOBJ != null && historyOBJ.has("immunizationHistory")
				&& !historyOBJ.get("immunizationHistory").isJsonNull()) {

			JsonObject immunizationHistory = historyOBJ.getAsJsonObject("immunizationHistory");
			if (immunizationHistory.get("immunizationList") != null
					&& immunizationHistory.getAsJsonArray("immunizationList").size() > 0) {
				WrapperImmunizationHistory wrapperImmunizationHistory = InputMapper.gson()
						.fromJson(historyOBJ.get("immunizationHistory"), WrapperImmunizationHistory.class);
				commonNurseServiceImpl.updateChildImmunizationDetail(wrapperImmunizationHistory);
			}
		}

		// update infant birth details
		// infant birth details save
		if (historyOBJ.has("infantBirthDetails") && !historyOBJ.get("infantBirthDetails").isJsonNull()) {
			InfantBirthDetails infantBirthDetails = InputMapper.gson().fromJson(historyOBJ.get("infantBirthDetails"),
					InfantBirthDetails.class);

			if (infantBirthDetails != null) {
				if (infantBirthDetails.getId() == null) {
					infantBirthDetails.setCreatedBy(infantBirthDetails.getModifiedBy());
					infantBirthDetails.setProcessed("N");
				} else
					infantBirthDetails.setProcessed("P");

				infantBirthDetailsRepo.save(infantBirthDetails);
			}
		}

		return 1;
	}

	/// update immunization service data
	/**
	 * 
	 * @param requestOBJ
	 * @return success or failure flag for immunization data updating
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateBenImmunServiceDetailsCAC(JsonObject immunServiceOBJ) throws Exception {

		// immunization service
		if (immunServiceOBJ.has("immunizationServices") && !immunServiceOBJ.get("immunizationServices").isJsonNull()) {
			ImmunizationServices is = InputMapper.gson().fromJson(immunServiceOBJ.get("immunizationServices"),
					ImmunizationServices.class);

			if (is != null && is.getBeneficiaryRegID() != null && is.getVisitCode() != null)
				immunizationServicesRepo.updateDeletedFlag(true, is.getBeneficiaryRegID(), is.getVisitCode());

			List<ImmunizationServices> isList = new ArrayList<ImmunizationServices>();
			ImmunizationServices obj;
			if (is != null && is.getVaccines() != null && is.getVaccines().size() > 0) {
				// for each vaccine, create a new db obj
				for (Vaccines v : is.getVaccines()) {
					if (v.getVaccineName() != null) {
						obj = new ImmunizationServices();

						obj.setBeneficiaryRegID(is.getBeneficiaryRegID());
						obj.setVanID(is.getVanID());
						obj.setProviderServiceMapID(is.getProviderServiceMapID());
						obj.setParkingPlaceID(is.getParkingPlaceID());

						if (is.getModifiedBy() != null) {
							obj.setModifiedBy(is.getModifiedBy());
							obj.setCreatedBy(is.getModifiedBy());
							obj.setProcessed("N");
						}

						if (is.getCreatedBy() != null)
							obj.setCreatedBy(is.getCreatedBy());

						obj.setDateOfVisit(is.getDateOfVisit());
						obj.setImmunizationServicesType(is.getImmunizationServicesType());
						obj.setImmunizationServicesTypeID(is.getImmunizationServicesTypeID());
						obj.setCurrentImmunizationService(is.getCurrentImmunizationService());
						obj.setCurrentImmunizationServiceID(is.getCurrentImmunizationServiceID());

						obj.setVaccineName(v.getVaccineName());
						obj.setVaccineDose(v.getVaccineDose());
						obj.setRoute(v.getRoute());
						obj.setBatchNo(v.getBatchNo());
						obj.setSiteOfInjection(v.getSiteOfInjection());

						obj.setVisitCode(is.getVisitCode());

						isList.add(obj);
					}
				}

				if (isList.size() > 0)
					immunizationServicesRepo.saveAll(isList);
			}
		}

		// Oral-Vitamin-Prophylaxis data update
		if (immunServiceOBJ.has("oralVitaminAProphylaxis")
				&& !immunServiceOBJ.get("oralVitaminAProphylaxis").isJsonNull()) {
			T_OralVitaminProphylaxis oralVitaminProphylaxis = InputMapper.gson()
					.fromJson(immunServiceOBJ.get("oralVitaminAProphylaxis"), T_OralVitaminProphylaxis.class);

			if (oralVitaminProphylaxis != null) {
				if (oralVitaminProphylaxis.getId() == null) {
					oralVitaminProphylaxis.setCreatedBy(oralVitaminProphylaxis.getModifiedBy());
				}

				oralVitaminProphylaxisRepo.save(oralVitaminProphylaxis);
			}
		}

		return 1;
	}

	// doc update
	// update doctor data
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long updateDoctorDataCAC(JsonObject requestOBJ, String Authorization) throws Exception {
		Long updateSuccessFlag = null;
		Long prescriptionID = null;

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
				commonDoctorServiceImpl.updateDocFindings(wrapperAncFindings);

			}

			// creating prescription OBJ
			PrescriptionDetail prescriptionDetail = null;
			if (requestOBJ.has("diagnosis") && !requestOBJ.get("diagnosis").isJsonNull()) {
				prescriptionDetail = InputMapper.gson().fromJson(requestOBJ.get("diagnosis"), PrescriptionDetail.class);
			}

			// generating WrapperBenInvestigationANC OBJ
			WrapperBenInvestigationANC wrapperBenInvestigationANCTMP = InputMapper.gson()
					.fromJson(requestOBJ.get("investigation"), WrapperBenInvestigationANC.class);

			if (prescriptionDetail != null) {
				prescriptionDetail.setExternalInvestigation(wrapperBenInvestigationANCTMP.getExternalInvestigations());
				prescriptionID = prescriptionDetail.getPrescriptionID();
			}

			if (commonUtilityClass.getTreatmentsOnSideEffects() != null)
				prescriptionDetail.setTreatmentsOnSideEffects(commonUtilityClass.getTreatmentsOnSideEffects());

			if (requestOBJ.has("counsellingProvidedList") && !requestOBJ.get("counsellingProvidedList").isJsonNull()
					&& requestOBJ.get("counsellingProvidedList") != null) {

				PrescriptionDetail tempPrescription = InputMapper.gson().fromJson(requestOBJ, PrescriptionDetail.class);

				if (tempPrescription != null && tempPrescription.getCounsellingProvidedList() != null
						&& tempPrescription.getCounsellingProvidedList().length > 0) {
					StringBuffer sb = new StringBuffer();
					for (String s : tempPrescription.getCounsellingProvidedList()) {
						sb.append(s).append("||");
					}
					if (sb.length() >= 2)
						prescriptionDetail.setCounsellingProvided(sb.substring(0, sb.length() - 2));

				} else
					prescriptionDetail.setCounsellingProvided("");

			}

			// update prescription
			int p = commonNurseServiceImpl.updatePrescription(prescriptionDetail);

			// update followUpForImmunization
			if (requestOBJ.has("followUpForImmunization") && !requestOBJ.get("followUpForImmunization").isJsonNull()
					&& requestOBJ.get("followUpForImmunization") != null) {
				FollowUpForImmunization followUpForImmunization = InputMapper.gson()
						.fromJson(requestOBJ.get("followUpForImmunization"), FollowUpForImmunization.class);

				if (followUpForImmunization != null) {
					if (followUpForImmunization.getProcessed() == null)
						followUpForImmunization.setProcessed("N");
					if (followUpForImmunization.getId() == null && followUpForImmunization.getCreatedBy() == null
							&& followUpForImmunization.getModifiedBy() != null) {
						followUpForImmunization.setCreatedBy(followUpForImmunization.getModifiedBy());
					}

					followUpForImmunizationRepo.save(followUpForImmunization);
				}
			}

			// save prescribed lab test
			if (isTestPrescribed == true) {
				WrapperBenInvestigationANC wrapperBenInvestigationANC = InputMapper.gson()
						.fromJson(requestOBJ.get("investigation"), WrapperBenInvestigationANC.class);

				if (wrapperBenInvestigationANC != null) {
					wrapperBenInvestigationANC.setPrescriptionID(prescriptionID);
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
						tmpObj.setPrescriptionID(prescriptionID);
						tmpObj.setBeneficiaryRegID(commonUtilityClass.getBeneficiaryRegID());
						tmpObj.setBenVisitID(commonUtilityClass.getBenVisitID());
						tmpObj.setVisitCode(commonUtilityClass.getVisitCode());
						tmpObj.setProviderServiceMapID(commonUtilityClass.getProviderServiceMapID());
					}
					Integer r = commonNurseServiceImpl.saveBenPrescribedDrugsList(prescribedDrugDetailList);

				}

			}
			// call method to update beneficiary flow table
			if (prescriptionID != null)
				commonUtilityClass.setPrescriptionID(prescriptionID);

			commonUtilityClass.setVisitCategoryID(6);
			commonUtilityClass.setAuthorization(Authorization);

			int i = commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(commonUtilityClass, isTestPrescribed,
					isMedicinePrescribed, tcRequestOBJ);

			if (i > 0)
				updateSuccessFlag = (long) 1;
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

		}
		return updateSuccessFlag;
	}

}
