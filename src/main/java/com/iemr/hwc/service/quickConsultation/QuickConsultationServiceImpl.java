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
package com.iemr.hwc.service.quickConsultation;

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
import com.iemr.hwc.data.nurse.BenAnthropometryDetail;
import com.iemr.hwc.data.nurse.BenPhysicalVitalDetail;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.quickConsultation.BenChiefComplaint;
import com.iemr.hwc.data.quickConsultation.BenClinicalObservations;
import com.iemr.hwc.data.quickConsultation.ExternalLabTestOrder;
import com.iemr.hwc.data.quickConsultation.PrescribedDrugDetail;
import com.iemr.hwc.data.quickConsultation.PrescriptionDetail;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.repo.nurse.BenPhysicalVitalRepo;
import com.iemr.hwc.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.hwc.repo.quickConsultation.BenClinicalObservationsRepo;
import com.iemr.hwc.repo.quickConsultation.ExternalTestOrderRepo;
import com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonServiceImpl;
import com.iemr.hwc.service.generalOPD.GeneralOPDDoctorServiceImpl;
import com.iemr.hwc.service.labtechnician.LabTechnicianServiceImpl;
import com.iemr.hwc.service.tele_consultation.SMSGatewayServiceImpl;
import com.iemr.hwc.service.tele_consultation.TeleConsultationServiceImpl;
import com.iemr.hwc.utils.mapper.InputMapper;

@Service
public class QuickConsultationServiceImpl implements QuickConsultationService {
	@Autowired
	private BenChiefComplaintRepo benChiefComplaintRepo;
	@Autowired
	private BenClinicalObservationsRepo benClinicalObservationsRepo;
	@Autowired
	private PrescriptionDetailRepo prescriptionDetailRepo;
	@Autowired
	private ExternalTestOrderRepo externalTestOrderRepo;
	@Autowired
	private CommonNurseServiceImpl commonNurseServiceImpl;
	@Autowired
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;
	@Autowired
	private LabTechnicianServiceImpl labTechnicianServiceImpl;
	@Autowired
	private CommonDoctorServiceImpl commonDoctorServiceImpl;
	@Autowired
	private GeneralOPDDoctorServiceImpl generalOPDDoctorServiceImpl;
	@Autowired
	private CommonServiceImpl commonServiceImpl;
	@Autowired
	private TeleConsultationServiceImpl teleConsultationServiceImpl;
	@Autowired
	private SMSGatewayServiceImpl sMSGatewayServiceImpl;
	@Autowired
	private BenPhysicalVitalRepo benPhysicalVitalRepo;

	@Override
	public Long saveBeneficiaryChiefComplaint(JsonObject caseSheet) {
		ArrayList<BenChiefComplaint> benChiefComplaints = BenChiefComplaint.getBenChiefComplaintList(caseSheet);
		Long returnOBJ = null;
		if (benChiefComplaints != null && benChiefComplaints.size() > 0) {
			List<BenChiefComplaint> chiefComplaints = (List<BenChiefComplaint>) benChiefComplaintRepo
					.save(benChiefComplaints);

			if (benChiefComplaints.size() == chiefComplaints.size()) {
				returnOBJ = new Long(1);
			}
		} else {
			returnOBJ = new Long(1);
		}

		return returnOBJ;
	}

	@Override
	public Long saveBeneficiaryClinicalObservations(JsonObject caseSheet) throws Exception {

		BenClinicalObservations benClinicalObservations = InputMapper.gson().fromJson(caseSheet,
				BenClinicalObservations.class);

		// get snomedCT code for symptoms
		if (benClinicalObservations != null) {
			String[] snomedCTcodeArr = commonDoctorServiceImpl
					.getSnomedCTcode(benClinicalObservations.getOtherSymptoms());
			if (snomedCTcodeArr != null && snomedCTcodeArr.length > 1) {
				benClinicalObservations.setOtherSymptomsSCTCode(snomedCTcodeArr[0]);
				benClinicalObservations.setOtherSymptomsSCTTerm(snomedCTcodeArr[1]);
			}
		}
		BenClinicalObservations benClinicalObservation = benClinicalObservationsRepo.save(benClinicalObservations);
		if (null != benClinicalObservation && benClinicalObservation.getClinicalObservationID() > 0) {
			return benClinicalObservation.getClinicalObservationID();
		}
		return null;
	}

	// Prescription for ANC...
	/* @Deprecated */
	public Long saveBenPrescriptionForANC(PrescriptionDetail prescription) {
		Long r = null;
		PrescriptionDetail prescriptionRS = prescriptionDetailRepo.save(prescription);
		if (prescriptionRS != null && prescriptionRS.getPrescriptionID() > 0) {
			r = prescriptionRS.getPrescriptionID();
		}
		return r;
	}

	// now not required
	// @Deprecated
	// @Override
	// public Long saveBeneficiaryPrescribedDrugDetail(JsonObject caseSheet, Long
	// prescriptionID,
	// CommonUtilityClass commonUtilityClass) {
	// Long prescribedDrugSuccessFlag = null;
	// ArrayList<PrescribedDrugDetail> prescriptionDetails = PrescribedDrugDetail
	// .getBenPrescribedDrugDetailList(caseSheet, prescriptionID,
	// commonUtilityClass);
	//
	// /*
	// * List<PrescribedDrugDetail> prescribedDrugs = (List<PrescribedDrugDetail>)
	// * prescribedDrugDetailRepo .save(prescriptionDetails);
	// *
	// * if (null != prescribedDrugs && prescribedDrugs.size() > 0) { for
	// * (PrescribedDrugDetail prescribedDrug : prescribedDrugs) { return
	// * prescribedDrug.getPrescribedDrugID(); } }
	// */
	//
	// Integer r =
	// commonNurseServiceImpl.saveBenPrescribedDrugsList(prescriptionDetails);
	// if (r > 0 && r != null) {
	// prescribedDrugSuccessFlag = new Long(r);
	// }
	//
	// return prescribedDrugSuccessFlag;
	// }

	@Override
	public Long saveBeneficiaryExternalLabTestOrderDetails(JsonObject caseSheet) {

		ExternalLabTestOrder externalLabTestOrder = ExternalLabTestOrder.getExternalLabTestOrderList(caseSheet);
		ExternalLabTestOrder externalTestOrder = externalTestOrderRepo.save(externalLabTestOrder);

		if (null != externalTestOrder && externalTestOrder.getExternalTestOrderID() > 0) {
			return externalTestOrder.getExternalTestOrderID();
		}
		return null;
	}

	@Override
	public String quickConsultNurseDataInsert(JsonObject jsnOBJ, String Authorization) throws Exception {
		Integer returnOBJ = 0;
		TeleconsultationRequestOBJ tcRequestOBJ = null;
		Long benVisitCode = null;
		if (jsnOBJ != null && jsnOBJ.has("visitDetails") && !jsnOBJ.get("visitDetails").isJsonNull()) {

			CommonUtilityClass nurseUtilityClass = InputMapper.gson().fromJson(jsnOBJ, CommonUtilityClass.class);

			BeneficiaryVisitDetail benVisitDetailsOBJ = InputMapper.gson().fromJson(jsnOBJ.get("visitDetails"),
					BeneficiaryVisitDetail.class);
			int i=commonNurseServiceImpl.getMaxCurrentdate(benVisitDetailsOBJ.getBeneficiaryRegID(),benVisitDetailsOBJ.getVisitReason(),benVisitDetailsOBJ.getVisitCategory());
			if(i<1) {
			Long benVisitID = commonNurseServiceImpl.saveBeneficiaryVisitDetails(benVisitDetailsOBJ);

			// 11-06-2018 visit code
			benVisitCode = commonNurseServiceImpl.generateVisitCode(benVisitID, nurseUtilityClass.getVanID(),
					nurseUtilityClass.getSessionID());

			// Getting benflowID for ben status update
			Long benFlowID = null;
			// if (jsnOBJ.has("benFlowID")) {
			// benFlowID = jsnOBJ.get("benFlowID").getAsLong();
			// }

			// Above if block code replaced by below line
			benFlowID = nurseUtilityClass.getBenFlowID();

			if (benVisitID != null && benVisitID > 0) {

				nurseUtilityClass.setVisitCode(benVisitCode);
				nurseUtilityClass.setBenVisitID(benVisitID);

				tcRequestOBJ = commonServiceImpl.createTcRequest(jsnOBJ, nurseUtilityClass, Authorization);

				BenAnthropometryDetail benAnthropometryDetail = InputMapper.gson().fromJson(jsnOBJ.get("vitalsDetails"),
						BenAnthropometryDetail.class);
				benAnthropometryDetail.setBenVisitID(benVisitID);
				benAnthropometryDetail.setVisitCode(benVisitCode);
				Long benAnthropometryID = commonNurseServiceImpl
						.saveBeneficiaryPhysicalAnthropometryDetails(benAnthropometryDetail);
				BenPhysicalVitalDetail benPhysicalVitalDetail = InputMapper.gson().fromJson(jsnOBJ.get("vitalsDetails"),
						BenPhysicalVitalDetail.class);
				benPhysicalVitalDetail.setBenVisitID(benVisitID);
				benPhysicalVitalDetail.setVisitCode(benVisitCode);
				Long benPhysicalVitalID = commonNurseServiceImpl
						.saveBeneficiaryPhysicalVitalDetails(benPhysicalVitalDetail);
				if (benAnthropometryID != null && benAnthropometryID > 0 && benPhysicalVitalID != null
						&& benPhysicalVitalID > 0) {
					// Integer i = commonNurseServiceImpl.updateBeneficiaryStatus('N',
					// benVisitDetailsOBJ.getBeneficiaryRegID());

					returnOBJ = 1;
					/**
					 * We have to write new code to update ben status flow new logic
					 */

					int j = updateBenStatusFlagAfterNurseSaveSuccess(benVisitDetailsOBJ, benVisitID, benFlowID,
							benVisitCode, nurseUtilityClass.getVanID(), tcRequestOBJ);

					if (j > 0)
						returnOBJ = 1;
					else
						throw new RuntimeException(
								"Error occurred while saving data. Beneficiary status update failed");

					if (j > 0 && tcRequestOBJ != null && tcRequestOBJ.getWalkIn() == false) {
						int k = sMSGatewayServiceImpl.smsSenderGateway("schedule",
								nurseUtilityClass.getBeneficiaryRegID(), tcRequestOBJ.getSpecializationID(),
								tcRequestOBJ.getTmRequestID(), null, nurseUtilityClass.getCreatedBy(),
								tcRequestOBJ.getAllocationDate() != null
										? String.valueOf(tcRequestOBJ.getAllocationDate())
										: "",
								null, Authorization);
					}

				} else {
					throw new RuntimeException("Error occurred while saving data");
				}
			} else {
				throw new RuntimeException("Error occurred while creating beneficiary visit");
			}
		} else {
				Map<String, String> responseMap = new HashMap<String, String>();
				responseMap.put("response", "Data already saved");
				return new Gson().toJson(responseMap);
			}
		} else {
			throw new Exception("Invalid input");
		}
		Map<String, String> responseMap = new HashMap<String, String>();
		if (benVisitCode != null) {
			responseMap.put("visitCode", benVisitCode.toString());
		}
		if (null != returnOBJ && returnOBJ > 0) {
			responseMap.put("response", "Data saved successfully");
		} else {
			responseMap.put("response", "Unable to save data");
		}
		return new Gson().toJson(responseMap);
		// return returnOBJ;
	}

	// method for updating ben flow status flag for nurse
	private int updateBenStatusFlagAfterNurseSaveSuccess(BeneficiaryVisitDetail benVisitDetailsOBJ, Long benVisitID,
			Long benFlowID, Long benVisitCode, Integer vanID, TeleconsultationRequestOBJ tcRequestOBJ) {
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
				benVisitDetailsOBJ.getBeneficiaryRegID(), benVisitID, benVisitDetailsOBJ.getVisitReason(),
				benVisitDetailsOBJ.getVisitCategory(), nurseFlag, docFlag, labIteration, (short) 0, (short) 0,
				benVisitCode, vanID, specialistFlag, tcDate, tcSpecialistUserID);

		return i;
	}

	@Override
	public Integer quickConsultDoctorDataInsert(JsonObject quickConsultDoctorOBJ, String Authorization)
			throws Exception {
		Integer returnOBJ = 0;
		Integer prescriptionSuccessFlag = null;
		Integer investigationSuccessFlag = null;
		Integer vitalsRBSTestFlag = null;
		// Integer tcRequestStatusFlag = null;

		TeleconsultationRequestOBJ tcRequestOBJ = null;
		// TcSpecialistSlotBookingRequestOBJ tcSpecialistSlotBookingRequestOBJ = null;
		CommonUtilityClass commonUtilityClass = InputMapper.gson().fromJson(quickConsultDoctorOBJ,
				CommonUtilityClass.class);

		tcRequestOBJ = commonServiceImpl.createTcRequest(quickConsultDoctorOBJ, commonUtilityClass, Authorization);

		Long benChiefComplaintID = saveBeneficiaryChiefComplaint(quickConsultDoctorOBJ);
		Long clinicalObservationID = saveBeneficiaryClinicalObservations(quickConsultDoctorOBJ);
		
		// generate prescription
		Long prescriptionID = commonNurseServiceImpl.saveBeneficiaryPrescription(quickConsultDoctorOBJ);

		Boolean isTestPrescribed = false;
		Boolean isMedicinePrescribed = false;

		JsonArray testList = quickConsultDoctorOBJ.getAsJsonArray("labTestOrders");
		if (testList != null && !testList.isJsonNull() && testList.size() > 0)
			isTestPrescribed = true;

		JsonArray drugList = quickConsultDoctorOBJ.getAsJsonArray("prescription");
		if (drugList != null && !drugList.isJsonNull() && drugList.size() > 0)
			isMedicinePrescribed = true;

		// save prescribed medicine
		if (isMedicinePrescribed) {

			PrescribedDrugDetail[] prescribedDrugDetail = InputMapper.gson()
					.fromJson(quickConsultDoctorOBJ.get("prescription"), PrescribedDrugDetail[].class);
			List<PrescribedDrugDetail> prescribedDrugDetailList = Arrays.asList(prescribedDrugDetail);

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
		
		// save prescribed lab test
		if (isTestPrescribed) {
			Long i = commonNurseServiceImpl.saveBeneficiaryLabTestOrderDetails(quickConsultDoctorOBJ, prescriptionID);
			if (i != null && i > 0)
				investigationSuccessFlag = 1;
		} else {
			investigationSuccessFlag = 1;
		}

		// Updating Vitals RBS Test Result
		BenPhysicalVitalDetail physicalVitalDet = InputMapper.gson().fromJson(quickConsultDoctorOBJ,
				BenPhysicalVitalDetail.class);
		int r = 0;
		if (quickConsultDoctorOBJ.has("rbsTestResult") || quickConsultDoctorOBJ.has("rbsTestRemarks")) {

			r = benPhysicalVitalRepo.updatePhysicalVitalDetailsQCDoctor(physicalVitalDet.getRbsTestResult(),
					physicalVitalDet.getRbsTestRemarks(), physicalVitalDet.getBeneficiaryRegID(),
					physicalVitalDet.getVisitCode());
			if (r > 0) {
				vitalsRBSTestFlag = 1;
			}
		} else {
			vitalsRBSTestFlag = 1;
		}

		// check if all data updated successfully
		if ((null != benChiefComplaintID && benChiefComplaintID > 0)
				&& (null != clinicalObservationID && clinicalObservationID > 0)
				&& (prescriptionID != null && prescriptionID > 0)
				&& (prescriptionSuccessFlag != null && prescriptionSuccessFlag > 0)
				&& (investigationSuccessFlag != null && investigationSuccessFlag > 0)
				&& (vitalsRBSTestFlag != null && vitalsRBSTestFlag > 0)) {

			// call method to update beneficiary flow table
			if (prescriptionID != null) {
				commonUtilityClass.setPrescriptionID(prescriptionID);
				commonUtilityClass.setVisitCategoryID(7);
				commonUtilityClass.setAuthorization(Authorization);

			}
			// call method to update beneficiary flow table
			int i = commonDoctorServiceImpl.updateBenFlowtableAfterDocDataSave(commonUtilityClass, isTestPrescribed,
					isMedicinePrescribed, tcRequestOBJ);

			if (i > 0)
				returnOBJ = 1;
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
		return returnOBJ;
	}

	// ------- Start Fetch (Nurse data to Doctor screen) ----------------
	public String getBenDataFrmNurseToDocVisitDetailsScreen(Long benRegID, Long visitCode) {
		Map<String, Object> resMap = new HashMap<>();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

		BeneficiaryVisitDetail benVisitDetailsOBJ = commonNurseServiceImpl.getCSVisitDetails(benRegID, visitCode);

		if (null != benVisitDetailsOBJ) {

			resMap.put("benVisitDetails", benVisitDetailsOBJ);
		}

		return gson.toJson(resMap);
	}

	public String getBeneficiaryVitalDetails(Long beneficiaryRegID, Long visitCode) {
		Map<String, Object> resMap = new HashMap<>();

		resMap.put("benAnthropometryDetail",
				commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, visitCode));
		resMap.put("benPhysicalVitalDetail",
				commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode));

		return resMap.toString();
	}

	public String getBenQuickConsultNurseData(Long benRegID, Long visitCode) {

		Map<String, Object> resMap = new HashMap<>();

		resMap.put("vitals", getBeneficiaryVitalDetails(benRegID, visitCode));

		return resMap.toString();
	}
	// ------- END of Fetch (Nurse data to Doctor screen) ----------------

	public String getBenCaseRecordFromDoctorQuickConsult(Long benRegID, Long visitCode) {
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("findings", commonDoctorServiceImpl.getFindingsDetails(benRegID, visitCode));
		resMap.put("diagnosis", generalOPDDoctorServiceImpl.getGeneralOPDDiagnosisDetails(benRegID, visitCode));
		resMap.put("investigation", commonDoctorServiceImpl.getInvestigationDetails(benRegID, visitCode));
		resMap.put("prescription", commonDoctorServiceImpl.getPrescribedDrugs(benRegID, visitCode));
		resMap.put("LabReport",
				new Gson().toJson(labTechnicianServiceImpl.getLabResultDataForBen(benRegID, visitCode)));
		resMap.put("ArchivedVisitcodeForLabResult",
				labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(benRegID, visitCode));

		// resList.add(commonDoctorServiceImpl.getFindingsDetails(benRegID, visitCode));
		// resList.add(commonDoctorServiceImpl.getInvestigationDetails(benRegID,
		// visitCode));
		// resList.add(commonDoctorServiceImpl.getPrescribedDrugs(benRegID, visitCode));
		// resList.add(new
		// Gson().toJson(labTechnicianServiceImpl.getLabResultDataForBen(benRegID,
		// visitCode)));

		return resMap.toString();
	}

	@Transactional(rollbackFor = Exception.class)
	public Long updateGeneralOPDQCDoctorData(JsonObject quickConsultDoctorOBJ, String Authorization) throws Exception {
		Long updateSuccessFlag = null;
		Long prescriptionID = null;
		Long prescribedDrugSuccessFlag = null;
		Long labTestOrderSuccessFlag = null;
		Long vitalsRBSTestFlag = null;

		Integer tcRequestStatusFlag = null;

		TeleconsultationRequestOBJ tcRequestOBJ = null;
		// TcSpecialistSlotBookingRequestOBJ tcSpecialistSlotBookingRequestOBJ = null;
		CommonUtilityClass commonUtilityClass = InputMapper.gson().fromJson(quickConsultDoctorOBJ,
				CommonUtilityClass.class);

		tcRequestOBJ = commonServiceImpl.createTcRequest(quickConsultDoctorOBJ, commonUtilityClass, Authorization);

		Long benChiefComplaintID = saveBeneficiaryChiefComplaint(quickConsultDoctorOBJ);
		Integer clinicalObservationID = updateBeneficiaryClinicalObservations(quickConsultDoctorOBJ);

		// generate prescription OBJ
		PrescriptionDetail prescriptionDetail = InputMapper.gson().fromJson(quickConsultDoctorOBJ,
				PrescriptionDetail.class);

		if (prescriptionDetail != null) {
			int p = commonNurseServiceImpl.updatePrescription(prescriptionDetail);
			prescriptionID = prescriptionDetail.getPrescriptionID();
		}

		JsonArray testList = null;
		JsonArray drugList = null;

		Boolean isTestPrescribed = false;
		Boolean isMedicinePrescribed = false;

		// checking if test is prescribed
		if (quickConsultDoctorOBJ.has("labTestOrders")) {
			testList = quickConsultDoctorOBJ.getAsJsonArray("labTestOrders");
			if (testList != null && !testList.isJsonNull() && testList.size() > 0)
				isTestPrescribed = true;
		}
		// checking if medicine is prescribed
		if (quickConsultDoctorOBJ.has("prescription") && !quickConsultDoctorOBJ.get("prescription").isJsonNull()
				&& quickConsultDoctorOBJ.get("prescription") != null) {
			drugList = quickConsultDoctorOBJ.getAsJsonArray("prescription");
			if (drugList != null && !drugList.isJsonNull() && drugList.size() > 0) {
				isMedicinePrescribed = true;
			}
		}

		// update prescribed medicine
		if (isMedicinePrescribed == true) {
			PrescribedDrugDetail[] prescribedDrugDetail = InputMapper.gson()
					.fromJson(quickConsultDoctorOBJ.get("prescription"), PrescribedDrugDetail[].class);
			List<PrescribedDrugDetail> prescribedDrugDetailList = Arrays.asList(prescribedDrugDetail);

			for (PrescribedDrugDetail tmpObj : prescribedDrugDetailList) {
				tmpObj.setPrescriptionID(prescriptionID);
				tmpObj.setBeneficiaryRegID(commonUtilityClass.getBeneficiaryRegID());
				tmpObj.setBenVisitID(commonUtilityClass.getBenVisitID());
				tmpObj.setVisitCode(commonUtilityClass.getVisitCode());
				tmpObj.setProviderServiceMapID(commonUtilityClass.getProviderServiceMapID());
			}
			Integer r = commonNurseServiceImpl.saveBenPrescribedDrugsList(prescribedDrugDetailList);
			if (r > 0 && r != null) {
				prescribedDrugSuccessFlag = new Long(1);
			}

		} else {
			prescribedDrugSuccessFlag = new Long(1);
		}

		// update prescribed lab test
		if (isTestPrescribed == true) {
			labTestOrderSuccessFlag = commonNurseServiceImpl.saveBeneficiaryLabTestOrderDetails(quickConsultDoctorOBJ,
					prescriptionID);
		} else {
			labTestOrderSuccessFlag = new Long(1);
		}

		// Updating Vitals RBS Test Result
		BenPhysicalVitalDetail physicalVitalDet = InputMapper.gson().fromJson(quickConsultDoctorOBJ,
				BenPhysicalVitalDetail.class);
		int r = 0;
		if (quickConsultDoctorOBJ.has("rbsTestResult") || quickConsultDoctorOBJ.has("rbsTestRemarks")) {

			r = benPhysicalVitalRepo.updatePhysicalVitalDetailsQCDoctor(physicalVitalDet.getRbsTestResult(),
					physicalVitalDet.getRbsTestRemarks(), physicalVitalDet.getBeneficiaryRegID(),
					physicalVitalDet.getVisitCode());
			if (r > 0) {
				vitalsRBSTestFlag = new Long(1);
			}
		} else {
			vitalsRBSTestFlag = new Long(1);
		}

		if ((null != benChiefComplaintID && benChiefComplaintID > 0)
				&& (null != clinicalObservationID && clinicalObservationID > 0)
				&& (null != prescribedDrugSuccessFlag && prescribedDrugSuccessFlag > 0)
				&& (null != labTestOrderSuccessFlag && labTestOrderSuccessFlag > 0)
				&& (null != vitalsRBSTestFlag && vitalsRBSTestFlag > 0)) {

			// call method to update beneficiary flow table

			if (prescriptionID != null) {
				commonUtilityClass.setPrescriptionID(prescriptionID);
				commonUtilityClass.setVisitCategoryID(7);
				commonUtilityClass.setAuthorization(Authorization);

			}
			int i = commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(commonUtilityClass, isTestPrescribed,
					isMedicinePrescribed, tcRequestOBJ);

			if (i > 0)
				updateSuccessFlag = benChiefComplaintID;
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

		return updateSuccessFlag;
	}

	@Override
	public Integer updateBeneficiaryClinicalObservations(JsonObject caseSheet) throws Exception {
		Integer r = 0;
		BenClinicalObservations benClinicalObservations = InputMapper.gson().fromJson(caseSheet,
				BenClinicalObservations.class);
		// get snomedCT code for symptoms
		if (benClinicalObservations != null) {
			String[] snomedCTcodeArr = commonDoctorServiceImpl
					.getSnomedCTcode(benClinicalObservations.getOtherSymptoms());
			if (snomedCTcodeArr != null && snomedCTcodeArr.length > 1) {
				benClinicalObservations.setOtherSymptomsSCTCode(snomedCTcodeArr[0]);
				benClinicalObservations.setOtherSymptomsSCTTerm(snomedCTcodeArr[1]);
			}
		}

		r = commonDoctorServiceImpl.updateBenClinicalObservations(benClinicalObservations);
		return r;
	}

}
