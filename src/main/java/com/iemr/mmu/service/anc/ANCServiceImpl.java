/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.service.anc;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.iemr.mmu.data.anc.ANCCareDetails;
import com.iemr.mmu.data.anc.ANCDiagnosis;
import com.iemr.mmu.data.anc.BenAdherence;
import com.iemr.mmu.data.anc.BenAllergyHistory;
import com.iemr.mmu.data.anc.BenFamilyHistory;
import com.iemr.mmu.data.anc.BenMedHistory;
import com.iemr.mmu.data.anc.BenMenstrualDetails;
import com.iemr.mmu.data.anc.BenPersonalHabit;
import com.iemr.mmu.data.anc.ComplicationDuringPregnancyHRP;
import com.iemr.mmu.data.anc.DeliveryComplicationHRP;
import com.iemr.mmu.data.anc.HrpStatusAndReasonsRequestModel;
import com.iemr.mmu.data.anc.PastObstetricHostoryHRP;
import com.iemr.mmu.data.anc.PhyGeneralExamination;
import com.iemr.mmu.data.anc.PhyHeadToToeExamination;
import com.iemr.mmu.data.anc.SysCardiovascularExamination;
import com.iemr.mmu.data.anc.SysCentralNervousExamination;
import com.iemr.mmu.data.anc.SysGenitourinarySystemExamination;
import com.iemr.mmu.data.anc.SysMusculoskeletalSystemExamination;
import com.iemr.mmu.data.anc.SysObstetricExamination;
import com.iemr.mmu.data.anc.SysRespiratoryExamination;
import com.iemr.mmu.data.anc.WrapperAncFindings;
import com.iemr.mmu.data.anc.WrapperAncImmunization;
import com.iemr.mmu.data.anc.WrapperBenInvestigationANC;
import com.iemr.mmu.data.anc.WrapperChildOptionalVaccineDetail;
import com.iemr.mmu.data.anc.WrapperComorbidCondDetails;
import com.iemr.mmu.data.anc.WrapperFemaleObstetricHistory;
import com.iemr.mmu.data.anc.WrapperImmunizationHistory;
import com.iemr.mmu.data.anc.WrapperMedicationHistory;
import com.iemr.mmu.data.fetosense.Fetosense;
import com.iemr.mmu.data.nurse.BenAnthropometryDetail;
import com.iemr.mmu.data.nurse.BenPhysicalVitalDetail;
import com.iemr.mmu.data.nurse.BeneficiaryVisitDetail;
import com.iemr.mmu.data.nurse.CommonUtilityClass;
import com.iemr.mmu.data.quickConsultation.BenChiefComplaint;
import com.iemr.mmu.data.quickConsultation.PrescribedDrugDetail;
import com.iemr.mmu.data.quickConsultation.PrescriptionDetail;
import com.iemr.mmu.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.mmu.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.mmu.repo.fetosense.FetosenseRepo;
import com.iemr.mmu.repo.nurse.BenAnthropometryRepo;
import com.iemr.mmu.repo.nurse.anc.ANCCareRepo;
import com.iemr.mmu.repo.nurse.anc.ANCDiagnosisRepo;
import com.iemr.mmu.repo.nurse.anc.BenMedHistoryRepo;
import com.iemr.mmu.repo.nurse.anc.BenMenstrualDetailsRepo;
import com.iemr.mmu.repo.nurse.anc.BencomrbidityCondRepo;
import com.iemr.mmu.repo.nurse.anc.FemaleObstetricHistoryRepo;
import com.iemr.mmu.repo.nurse.anc.SysObstetricExaminationRepo;
import com.iemr.mmu.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.mmu.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.mmu.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.mmu.service.common.transaction.CommonServiceImpl;
import com.iemr.mmu.service.labtechnician.LabTechnicianServiceImpl;
import com.iemr.mmu.service.tele_consultation.SMSGatewayServiceImpl;
import com.iemr.mmu.service.tele_consultation.TeleConsultationServiceImpl;
import com.iemr.mmu.utils.exception.IEMRException;
import com.iemr.mmu.utils.mapper.InputMapper;

@Service
public class ANCServiceImpl implements ANCService {

	private ANCNurseServiceImpl ancNurseServiceImpl;
	// private NurseServiceImpl nurseServiceImpl;
	private ANCDoctorServiceImpl ancDoctorServiceImpl;
	private CommonNurseServiceImpl commonNurseServiceImpl;
	private CommonDoctorServiceImpl commonDoctorServiceImpl;
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;
	private LabTechnicianServiceImpl labTechnicianServiceImpl;
	@Autowired
	private TeleConsultationServiceImpl teleConsultationServiceImpl;
	@Autowired
	private CommonServiceImpl commonServiceImpl;
	@Autowired
	private ANCCareRepo ancCareRepo;
	@Autowired
	private FemaleObstetricHistoryRepo femaleObstetricHistoryRepo;
	@Autowired
	private ANCDiagnosisRepo aNCDiagnosisRepo;
	@Autowired
	private FetosenseRepo fetosenseRepo;

	@Autowired
	private BenMenstrualDetailsRepo benMenstrualDetailsRepo;

	@Autowired
	private SysObstetricExaminationRepo sysObstetricExaminationRepo;

	@Autowired
	public void setLabTechnicianServiceImpl(LabTechnicianServiceImpl labTechnicianServiceImpl) {
		this.labTechnicianServiceImpl = labTechnicianServiceImpl;
	}

	@Autowired
	public void setCommonBenStatusFlowServiceImpl(CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl) {
		this.commonBenStatusFlowServiceImpl = commonBenStatusFlowServiceImpl;
	}

	@Autowired
	public void setCommonDoctorServiceImpl(CommonDoctorServiceImpl commonDoctorServiceImpl) {
		this.commonDoctorServiceImpl = commonDoctorServiceImpl;
	}

	@Autowired
	public void setCommonNurseServiceImpl(CommonNurseServiceImpl commonNurseServiceImpl) {
		this.commonNurseServiceImpl = commonNurseServiceImpl;
	}

	@Autowired
	public void setANCDoctorServiceImpl(ANCDoctorServiceImpl ancDoctorServiceImpl) {
		this.ancDoctorServiceImpl = ancDoctorServiceImpl;
	}

	@Autowired
	public void setAncNurseServiceImpl(ANCNurseServiceImpl ancNurseServiceImpl) {
		this.ancNurseServiceImpl = ancNurseServiceImpl;
	}

	@Autowired
	private SMSGatewayServiceImpl sMSGatewayServiceImpl;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String saveANCNurseData(JsonObject requestOBJ, String Authorization) throws Exception {
		// String vr = "";
		// String vc = "";
		Long saveSuccessFlag = null;
		TeleconsultationRequestOBJ tcRequestOBJ = null;
		Long benVisitCode = null;
		// check if visit details data is not null
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

			Long ancSaveSuccessFlag = null;
			Long historySaveSuccessFlag = null;
			Long vitalSaveSuccessFlag = null;
			Long examtnSaveSuccessFlag = null;

			// code moved from inner code
			JsonObject tmpOBJ = requestOBJ.getAsJsonObject("visitDetails").getAsJsonObject("visitDetails");

			// Getting benflowID for ben status update
			Long benFlowID = null;

			// Above if block code replaced by below line
			benFlowID = nurseUtilityClass.getBenFlowID();

			if (benVisitID != null && benVisitID > 0) {
				// create tc request
				tcRequestOBJ = commonServiceImpl.createTcRequest(requestOBJ, nurseUtilityClass, Authorization);
				// call method to save ANC data
				ancSaveSuccessFlag = saveBenANCDetails(requestOBJ.getAsJsonObject("ancDetails"), benVisitID,
						benVisitCode);
				// call method to save History data
				historySaveSuccessFlag = saveBenANCHistoryDetails(requestOBJ.getAsJsonObject("historyDetails"),
						benVisitID, benVisitCode);
				// call method to save Vital data
				vitalSaveSuccessFlag = saveBenANCVitalDetails(requestOBJ.getAsJsonObject("vitalDetails"), benVisitID,
						benVisitCode);
				// call method to save Examination data
				examtnSaveSuccessFlag = saveBenANCExaminationDetails(requestOBJ.getAsJsonObject("examinationDetails"),
						benVisitID, benVisitCode);
			} else {
				throw new RuntimeException("Error occurred while creating beneficiary visit");
			}
			if ((null != ancSaveSuccessFlag && ancSaveSuccessFlag > 0)
					&& (null != historySaveSuccessFlag && historySaveSuccessFlag > 0)
					&& (null != vitalSaveSuccessFlag && vitalSaveSuccessFlag > 0)
					&& (null != examtnSaveSuccessFlag && examtnSaveSuccessFlag > 0)) {
				/**
				 * We have to write new code to update ben status flow new logic
				 */

				int j = updateBenFlowNurseAfterNurseActivityANC(
						requestOBJ.getAsJsonObject("visitDetails").getAsJsonObject("investigation"), tmpOBJ, benVisitID,
						benFlowID, benVisitCode, nurseUtilityClass.getVanID(), tcRequestOBJ);

				if (j > 0)
					saveSuccessFlag = ancSaveSuccessFlag;
				else
					throw new RuntimeException("Error occurred while saving data. Beneficiary status update failed");

				if (j > 0 && tcRequestOBJ != null && tcRequestOBJ.getWalkIn() == false) {
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

	private int updateBenFlowNurseAfterNurseActivityANC(JsonObject investigationDataCheck, JsonObject tmpOBJ,
			Long benVisitID, Long benFlowID, Long visitCode, Integer vanID, TeleconsultationRequestOBJ tcRequestOBJ) {
		short nurseFlag;
		short docFlag;
		short labIteration;
		short specialistFlag = (short) 0;
		Timestamp tcDate = null;
		Integer tcSpecialistUserID = null;

		// for feto sense
		short labTechnicianFlag = (short) 0;

		if (!investigationDataCheck.isJsonNull() && !investigationDataCheck.get("laboratoryList").isJsonNull()
				&& investigationDataCheck.getAsJsonArray("laboratoryList").size() > 0) {

			// ben will transfer to lab and doc both
			nurseFlag = (short) 2;
			docFlag = (short) 0;
			labIteration = (short) 1;
		} else {
			// ben will transfer doc only
			nurseFlag = (short) 9;
			docFlag = (short) 1;
			labIteration = (short) 0;
		}

		if (tcRequestOBJ != null && tcRequestOBJ.getUserID() != null && tcRequestOBJ.getAllocationDate() != null) {
			specialistFlag = (short) 1;
			tcDate = tcRequestOBJ.getAllocationDate();
			tcSpecialistUserID = tcRequestOBJ.getUserID();
		} else
			specialistFlag = (short) 0;

		ArrayList<Fetosense> fetosenseData = fetosenseRepo.getFetosenseDetailsByFlowId(benFlowID);
		if (fetosenseData.size() > 0) {
			// updating visitcode in fetosense table;
			fetosenseRepo.updateVisitCode(visitCode, benFlowID);

			labTechnicianFlag = 3;
			for (Fetosense data : fetosenseData) {
				if (data != null && !data.getResultState()) {
					labTechnicianFlag = 2;
				}
			}
		}
		int rs = commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivityANC(benFlowID,
				tmpOBJ.get("beneficiaryRegID").getAsLong(), benVisitID, tmpOBJ.get("visitReason").getAsString(),
				tmpOBJ.get("visitCategory").getAsString(), nurseFlag, docFlag, labIteration, (short) 0, (short) 0,
				visitCode, vanID, specialistFlag, tcDate, tcSpecialistUserID, labTechnicianFlag);

		return rs;
	}

	@Transactional(rollbackFor = Exception.class)
	public Long saveANCDoctorData(JsonObject requestOBJ, String Authorization) throws Exception {
		Long saveSuccessFlag = null;
		Long prescriptionID = null;
		Long investigationSuccessFlag = null;
		Integer findingSuccessFlag = null;
		Long diagnosisSuccessFlag = null;
		Integer prescriptionSuccessFlag = null;
		Long referSaveSuccessFlag = null;
		Integer tcRequestStatusFlag = null;

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
			
			// creating prescription object
			WrapperBenInvestigationANC wrapperBenInvestigationANC = InputMapper.gson()
					.fromJson(requestOBJ.get("investigation"), WrapperBenInvestigationANC.class);

			String instruction = null;
			if (requestOBJ.has("diagnosis") && !requestOBJ.get("diagnosis").isJsonNull()
					&& requestOBJ.get("diagnosis").getAsJsonObject().has("specialistDiagnosis")
					&& !requestOBJ.get("diagnosis").getAsJsonObject().get("specialistDiagnosis").isJsonNull()) {
				instruction = requestOBJ.get("diagnosis").getAsJsonObject().get("specialistDiagnosis").getAsString();
			}
			
			String prescription_counsellingProvided = null;
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
						tempPrescription.setCounsellingProvided(sb.substring(0, sb.length() - 2));
					prescription_counsellingProvided = tempPrescription.getCounsellingProvided();
					
				}
			}

			prescriptionID = commonNurseServiceImpl.savePrescriptionDetailsAndGetPrescriptionID(
					wrapperBenInvestigationANC.getBeneficiaryRegID(), wrapperBenInvestigationANC.getBenVisitID(),
					wrapperBenInvestigationANC.getProviderServiceMapID(), wrapperBenInvestigationANC.getCreatedBy(),
					wrapperBenInvestigationANC.getExternalInvestigations(), wrapperBenInvestigationANC.getVisitCode(),
					wrapperBenInvestigationANC.getVanID(), wrapperBenInvestigationANC.getParkingPlaceID(), instruction, prescription_counsellingProvided);

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
				Integer r = commonNurseServiceImpl.saveBenPrescribedDrugsList(prescribedDrugDetailList);
				if (r > 0 && r != null) {
					prescriptionSuccessFlag = r;
				}
			} else {
				prescriptionSuccessFlag = 1;
			}

			// save diagnosis
			if (requestOBJ.has("diagnosis") && !requestOBJ.get("diagnosis").isJsonNull()) {
				diagnosisSuccessFlag = ancDoctorServiceImpl
						.saveBenANCDiagnosis(requestOBJ.get("diagnosis").getAsJsonObject(), prescriptionID);
			} else {
				diagnosisSuccessFlag = new Long(1);
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
					&& (diagnosisSuccessFlag != null && diagnosisSuccessFlag > 0)
					&& (investigationSuccessFlag != null && investigationSuccessFlag > 0)
					&& (prescriptionSuccessFlag != null && prescriptionSuccessFlag > 0)
					&& (referSaveSuccessFlag != null && referSaveSuccessFlag > 0)) {

				// call method to update beneficiary flow table
				if (prescriptionID != null) {
					commonUtilityClass.setPrescriptionID(prescriptionID);
					commonUtilityClass.setVisitCategoryID(4);
					commonUtilityClass.setAuthorization(Authorization);

				}
				int i = commonDoctorServiceImpl.updateBenFlowtableAfterDocDataSave(commonUtilityClass, isTestPrescribed,
						isMedicinePrescribed, tcRequestOBJ);

				if (i > 0)
					saveSuccessFlag = diagnosisSuccessFlag;
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
		} else

		{
			// request OBJ is null.
		}
		return saveSuccessFlag;
	}

	/// ------------------- END of saving doctor data ------------------------

	/**
	 * 
	 * @param requestOBJ
	 * @return success or failure flag for visitDetails data saving
	 */

	public Map<String, Long> saveBenVisitDetails(JsonObject visitDetailsOBJ, CommonUtilityClass nurseUtilityClass)
			throws Exception {
		Map<String, Long> visitIdAndCodeMap = new HashMap<>();
		Long benVisitID = null;
		int investigationSuccessFlag = 0;
		int adherenceSuccessFlag = 0;
		int chiefComplaintsSuccessFlag = 0;
		if (visitDetailsOBJ != null && visitDetailsOBJ.has("visitDetails")
				&& !visitDetailsOBJ.get("visitDetails").isJsonNull()) {

			CommonUtilityClass commonUtilityClass = InputMapper.gson().fromJson(visitDetailsOBJ,
					CommonUtilityClass.class);

			BeneficiaryVisitDetail benVisitDetailsOBJ = InputMapper.gson().fromJson(visitDetailsOBJ.get("visitDetails"),
					BeneficiaryVisitDetail.class);

			// benVisitDetailsOBJ.setVanID(commonUtilityClass.getVanID());
			// benVisitDetailsOBJ.setParkingPlaceID(commonUtilityClass.getParkingPlaceID());

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
					chiefComplaintsSuccessFlag = commonNurseServiceImpl.saveBenChiefComplaints(benChiefComplaintList);
				}
				if (visitDetailsOBJ.has("adherence") && !visitDetailsOBJ.get("adherence").isJsonNull()) {
					// Save Ben Adherence
					BenAdherence benAdherence = InputMapper.gson().fromJson(visitDetailsOBJ.get("adherence"),
							BenAdherence.class);
					benAdherence.setBenVisitID(benVisitID);
					benAdherence.setVisitCode(benVisitCode);
					adherenceSuccessFlag = commonNurseServiceImpl.saveBenAdherenceDetails(benAdherence);
				}
				if (visitDetailsOBJ.has("investigation") && !visitDetailsOBJ.get("investigation").isJsonNull()
						&& visitDetailsOBJ.getAsJsonObject("investigation").get("laboratoryList") != null
						&& !visitDetailsOBJ.getAsJsonObject("investigation").get("laboratoryList").isJsonNull()
						&& visitDetailsOBJ.getAsJsonObject("investigation").getAsJsonArray("laboratoryList")
								.size() > 0) {
					// Save Ben Investigations
					WrapperBenInvestigationANC wrapperBenInvestigationANC = InputMapper.gson()
							.fromJson(visitDetailsOBJ.get("investigation"), WrapperBenInvestigationANC.class);

					if (wrapperBenInvestigationANC != null) {
						wrapperBenInvestigationANC.setBenVisitID(benVisitID);
						wrapperBenInvestigationANC.setVisitCode(benVisitCode);
						investigationSuccessFlag = commonNurseServiceImpl
								.saveBenInvestigationDetails(wrapperBenInvestigationANC);

					} else {
						// Invalid Data..
					}

				} else {
					investigationSuccessFlag = 1;
				}
				if (adherenceSuccessFlag > 0 && chiefComplaintsSuccessFlag > 0 && investigationSuccessFlag > 0) {
					// Adherence, ChiefComplaints and Investigation Details
					// stored successfully.
				}

				// TODO Save Ben Upload Files
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
	 * @throws ParseException
	 */
	public Long saveBenANCDetails(JsonObject ancDetailsOBJ, Long benVisitID, Long benVisitCode) throws Exception {
		Long ancSuccessFlag = null;
		Long ancCareSuccessFlag = null;
		Long ancImunizationSuccessFlag = null;
		if (ancDetailsOBJ != null && ancDetailsOBJ.has("ancObstetricDetails")
				&& !ancDetailsOBJ.get("ancObstetricDetails").isJsonNull()) {
			// Save Ben ANC Care Details
			ANCCareDetails ancCareDetailsOBJ = InputMapper.gson().fromJson(ancDetailsOBJ.get("ancObstetricDetails"),
					ANCCareDetails.class);
			if (null != ancCareDetailsOBJ) {
				ancCareDetailsOBJ.setBenVisitID(benVisitID);
				ancCareDetailsOBJ.setVisitCode(benVisitCode);
				ancCareSuccessFlag = ancNurseServiceImpl.saveBenAncCareDetails(ancCareDetailsOBJ);
			}
		}
		if (ancDetailsOBJ != null && ancDetailsOBJ.has("ancImmunization")
				&& !ancDetailsOBJ.get("ancImmunization").isJsonNull()) {
			WrapperAncImmunization wrapperAncImmunizationOBJ = InputMapper.gson()
					.fromJson(ancDetailsOBJ.get("ancImmunization"), WrapperAncImmunization.class);
			if (null != wrapperAncImmunizationOBJ) {
				wrapperAncImmunizationOBJ.setBenVisitID(benVisitID);
				wrapperAncImmunizationOBJ.setVisitCode(benVisitCode);
				ancImunizationSuccessFlag = ancNurseServiceImpl.saveAncImmunizationDetails(wrapperAncImmunizationOBJ);
			}

		}
		if ((null != ancCareSuccessFlag && ancCareSuccessFlag > 0)
				&& (null != ancImunizationSuccessFlag && ancImunizationSuccessFlag > 0)) {
			ancSuccessFlag = ancCareSuccessFlag;
		}

		return ancSuccessFlag;
	}

	/**
	 * 
	 * @param requestOBJ
	 * @return success or failure flag for history data saving
	 */
	public Long saveBenANCHistoryDetails(JsonObject ancHistoryOBJ, Long benVisitID, Long benVisitCode)
			throws Exception {
		Long pastHistorySuccessFlag = null;
		Long comrbidSuccessFlag = null;
		Long medicationSuccessFlag = null;
		int personalHistorySuccessFlag = 0;
		Long allergyHistorySuccessFlag = null;
		Long familyHistorySuccessFlag = null;
		int menstrualHistorySuccessFlag = 0;
		Long obstetricSuccessFlag = null;
		Long immunizationSuccessFlag = null;
		Long childVaccineSuccessFlag = null;

		// Save Past History
		if (ancHistoryOBJ != null && ancHistoryOBJ.has("pastHistory")
				&& !ancHistoryOBJ.get("pastHistory").isJsonNull()) {
			BenMedHistory benMedHistory = InputMapper.gson().fromJson(ancHistoryOBJ.get("pastHistory"),
					BenMedHistory.class);
			if (null != benMedHistory) {
				benMedHistory.setBenVisitID(benVisitID);
				benMedHistory.setVisitCode(benVisitCode);
				pastHistorySuccessFlag = commonNurseServiceImpl.saveBenPastHistory(benMedHistory);
			}

		}

		// Save Comorbidity/concurrent Conditions
		if (ancHistoryOBJ != null && ancHistoryOBJ.has("comorbidConditions")
				&& !ancHistoryOBJ.get("comorbidConditions").isJsonNull()) {
			WrapperComorbidCondDetails wrapperComorbidCondDetails = InputMapper.gson()
					.fromJson(ancHistoryOBJ.get("comorbidConditions"), WrapperComorbidCondDetails.class);
			if (null != wrapperComorbidCondDetails) {
				wrapperComorbidCondDetails.setBenVisitID(benVisitID);
				wrapperComorbidCondDetails.setVisitCode(benVisitCode);
				comrbidSuccessFlag = commonNurseServiceImpl.saveBenComorbidConditions(wrapperComorbidCondDetails);
			}
		}

		// Save Medication History
		if (ancHistoryOBJ != null && ancHistoryOBJ.has("medicationHistory")
				&& !ancHistoryOBJ.get("medicationHistory").isJsonNull()) {
			WrapperMedicationHistory wrapperMedicationHistory = InputMapper.gson()
					.fromJson(ancHistoryOBJ.get("medicationHistory"), WrapperMedicationHistory.class);
			if (null != wrapperMedicationHistory) {
				wrapperMedicationHistory.setBenVisitID(benVisitID);
				wrapperMedicationHistory.setVisitCode(benVisitCode);
				medicationSuccessFlag = commonNurseServiceImpl.saveBenMedicationHistory(wrapperMedicationHistory);

				// medicationSuccessFlag =
				// ancNurseServiceImpl.saveBenANCMedicationHistory(wrapperMedicationHistory);
			}

		}
		// Save Personal History
		if (ancHistoryOBJ != null && ancHistoryOBJ.has("personalHistory")
				&& !ancHistoryOBJ.get("personalHistory").isJsonNull()) {
			// Save Ben Personal Habits..
			BenPersonalHabit personalHabit = InputMapper.gson().fromJson(ancHistoryOBJ.get("personalHistory"),
					BenPersonalHabit.class);
			if (null != personalHabit) {
				personalHabit.setBenVisitID(benVisitID);
				personalHabit.setVisitCode(benVisitCode);

				personalHistorySuccessFlag = commonNurseServiceImpl.savePersonalHistory(personalHabit);
				// personalHistorySuccessFlag =
				// ancNurseServiceImpl.saveANCPersonalHistory(personalHabit);
			}

			BenAllergyHistory benAllergyHistory = InputMapper.gson().fromJson(ancHistoryOBJ.get("personalHistory"),
					BenAllergyHistory.class);
			if (null != benAllergyHistory) {
				benAllergyHistory.setBenVisitID(benVisitID);
				benAllergyHistory.setVisitCode(benVisitCode);

				allergyHistorySuccessFlag = commonNurseServiceImpl.saveAllergyHistory(benAllergyHistory);
				// allergyHistorySuccessFlag =
				// ancNurseServiceImpl.saveANCAllergyHistory(benAllergyHistory);
			}

		}

		// Save Family History
		if (ancHistoryOBJ != null && ancHistoryOBJ.has("familyHistory")
				&& !ancHistoryOBJ.get("familyHistory").isJsonNull()) {
			BenFamilyHistory benFamilyHistory = InputMapper.gson().fromJson(ancHistoryOBJ.get("familyHistory"),
					BenFamilyHistory.class);
			if (null != benFamilyHistory) {
				benFamilyHistory.setBenVisitID(benVisitID);
				benFamilyHistory.setVisitCode(benVisitCode);
				familyHistorySuccessFlag = commonNurseServiceImpl.saveBenFamilyHistory(benFamilyHistory);
				// familyHistorySuccessFlag =
				// ancNurseServiceImpl.saveANCBenFamilyHistory(benFamilyHistory);
			}
		}

		// Save Menstrual History
		if (ancHistoryOBJ != null && ancHistoryOBJ.has("menstrualHistory")
				&& !ancHistoryOBJ.get("menstrualHistory").isJsonNull()) {
			BenMenstrualDetails menstrualDetails = InputMapper.gson().fromJson(ancHistoryOBJ.get("menstrualHistory"),
					BenMenstrualDetails.class);
			if (null != menstrualDetails) {
				menstrualDetails.setBenVisitID(benVisitID);
				menstrualDetails.setVisitCode(benVisitCode);
				menstrualHistorySuccessFlag = commonNurseServiceImpl.saveBenMenstrualHistory(menstrualDetails);
				// menstrualHistorySuccessFlag =
				// ancNurseServiceImpl.saveBenANCMenstrualHistory(menstrualDetails);
			}

		}

		// Save Past Obstetric History
		if (ancHistoryOBJ != null && ancHistoryOBJ.has("femaleObstetricHistory")
				&& !ancHistoryOBJ.get("femaleObstetricHistory").isJsonNull()) {
			WrapperFemaleObstetricHistory wrapperFemaleObstetricHistory = InputMapper.gson()
					.fromJson(ancHistoryOBJ.get("femaleObstetricHistory"), WrapperFemaleObstetricHistory.class);

			if (wrapperFemaleObstetricHistory != null) {
				wrapperFemaleObstetricHistory.setBenVisitID(benVisitID);
				wrapperFemaleObstetricHistory.setVisitCode(benVisitCode);
				obstetricSuccessFlag = commonNurseServiceImpl.saveFemaleObstetricHistory(wrapperFemaleObstetricHistory);
				// obstetricSuccessFlag =
				// ancNurseServiceImpl.saveFemaleObstetricHistory(wrapperFemaleObstetricHistory);
			} else {
				// Female Obstetric Details not provided.
			}

		} else {
			obstetricSuccessFlag = new Long(1);
		}

		/** For Female above 12 and below 16 years.. **/
		// Save Immunization History
		if (ancHistoryOBJ != null && ancHistoryOBJ.has("immunizationHistory")
				&& !ancHistoryOBJ.get("immunizationHistory").isJsonNull()) {
			WrapperImmunizationHistory wrapperImmunizationHistory = InputMapper.gson()
					.fromJson(ancHistoryOBJ.get("immunizationHistory"), WrapperImmunizationHistory.class);
			if (null != wrapperImmunizationHistory) {
				wrapperImmunizationHistory.setBenVisitID(benVisitID);
				wrapperImmunizationHistory.setVisitCode(benVisitCode);
				immunizationSuccessFlag = commonNurseServiceImpl.saveImmunizationHistory(wrapperImmunizationHistory);
				// immunizationSuccessFlag =
				// ancNurseServiceImpl.saveANCImmunizationHistory(wrapperImmunizationHistory);
			} else {

				// ImmunizationList Data not Available
			}

		} else {
			immunizationSuccessFlag = new Long(1);
		}

		// Save Other/Optional Vaccines History
		if (ancHistoryOBJ != null && ancHistoryOBJ.has("childVaccineDetails")
				&& !ancHistoryOBJ.get("childVaccineDetails").isJsonNull()) {
			WrapperChildOptionalVaccineDetail wrapperChildVaccineDetail = InputMapper.gson()
					.fromJson(ancHistoryOBJ.get("childVaccineDetails"), WrapperChildOptionalVaccineDetail.class);
			if (null != wrapperChildVaccineDetail) {
				wrapperChildVaccineDetail.setBenVisitID(benVisitID);
				wrapperChildVaccineDetail.setVisitCode(benVisitCode);
				childVaccineSuccessFlag = commonNurseServiceImpl
						.saveChildOptionalVaccineDetail(wrapperChildVaccineDetail);
				// childVaccineSuccessFlag =
				// ancNurseServiceImpl.saveChildOptionalVaccineDetail(wrapperChildVaccineDetail);
			} else {
				// Child Optional Vaccine Detail not provided.
			}

		} else {
			childVaccineSuccessFlag = new Long(1);
		}

		Long historySuccessFlag = null;

		if ((null != pastHistorySuccessFlag && pastHistorySuccessFlag > 0)
				&& (null != comrbidSuccessFlag && comrbidSuccessFlag > 0)
				&& (null != medicationSuccessFlag && medicationSuccessFlag > 0)
				&& (null != allergyHistorySuccessFlag && allergyHistorySuccessFlag > 0)
				&& (null != familyHistorySuccessFlag && familyHistorySuccessFlag > 0)
				&& (null != obstetricSuccessFlag && obstetricSuccessFlag > 0)
				&& (null != immunizationSuccessFlag && immunizationSuccessFlag > 0)
				&& (null != childVaccineSuccessFlag && childVaccineSuccessFlag > 0) && personalHistorySuccessFlag > 0
				&& menstrualHistorySuccessFlag > 0) {
			historySuccessFlag = pastHistorySuccessFlag;
		}
		return historySuccessFlag;
	}

	/**
	 * 
	 * @param requestOBJ
	 * @return success or failure flag for visitDetails data saving
	 */
	public Long saveBenANCVitalDetails(JsonObject vitalDetailsOBJ, Long benVisitID, Long benVisitCode)
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

	/**
	 * 
	 * @param requestOBJ
	 * @return success or failure flag for visitDetails data saving
	 */
	public Long saveBenANCExaminationDetails(JsonObject examinationDetailsOBJ, Long benVisitID, Long benVisitCode)
			throws Exception {

		Long exmnSuccessFlag = null;

		Long genExmnSuccessFlag = null;
		Long headToToeExmnSuccessFlag = null;
		// Long gastroIntsExmnSuccessFlag = null;
		Long cardiExmnSuccessFlag = null;
		Long respiratoryExmnSuccessFlag = null;
		Long centralNrvsExmnSuccessFlag = null;
		Long muskelstlExmnSuccessFlag = null;
		Long genitorinaryExmnSuccessFlag = null;
		Long obstetricExmnSuccessFlag = null;

		// Save General Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("generalExamination")
				&& !examinationDetailsOBJ.get("generalExamination").isJsonNull()) {
			PhyGeneralExamination generalExamination = InputMapper.gson()
					.fromJson(examinationDetailsOBJ.get("generalExamination"), PhyGeneralExamination.class);
			if (null != generalExamination) {
				generalExamination.setBenVisitID(benVisitID);
				generalExamination.setVisitCode(benVisitCode);
				genExmnSuccessFlag = commonNurseServiceImpl.savePhyGeneralExamination(generalExamination);
				// genExmnSuccessFlag =
				// ancNurseServiceImpl.savePhyGeneralExamination(generalExamination);
			}

		}

		// Save Head to toe Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("headToToeExamination")
				&& !examinationDetailsOBJ.get("headToToeExamination").isJsonNull()) {
			PhyHeadToToeExamination headToToeExamination = InputMapper.gson()
					.fromJson(examinationDetailsOBJ.get("headToToeExamination"), PhyHeadToToeExamination.class);
			if (null != headToToeExamination) {
				headToToeExamination.setBenVisitID(benVisitID);
				headToToeExamination.setVisitCode(benVisitCode);
				headToToeExmnSuccessFlag = commonNurseServiceImpl.savePhyHeadToToeExamination(headToToeExamination);
				// headToToeExmnSuccessFlag =
				// ancNurseServiceImpl.savePhyHeadToToeExamination(headToToeExamination);
			}

		}
		// Save Gastro Intestinal Examination Details
		/**
		 * Removed from anc. Only applicable for generalOPD. date: 07-02-2018
		 */

		// Save Cardio Vascular Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("cardioVascularExamination")
				&& !examinationDetailsOBJ.get("cardioVascularExamination").isJsonNull()) {
			SysCardiovascularExamination cardiovascularExamination = InputMapper.gson().fromJson(
					examinationDetailsOBJ.get("cardioVascularExamination"), SysCardiovascularExamination.class);
			if (null != cardiovascularExamination) {
				cardiovascularExamination.setBenVisitID(benVisitID);
				cardiovascularExamination.setVisitCode(benVisitCode);
				cardiExmnSuccessFlag = commonNurseServiceImpl
						.saveSysCardiovascularExamination(cardiovascularExamination);
				// cardiExmnSuccessFlag =
				// ancNurseServiceImpl.saveSysCardiovascularExamination(cardiovascularExamination);

			}
		}

		// Save Respiratory Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("respiratorySystemExamination")
				&& !examinationDetailsOBJ.get("respiratorySystemExamination").isJsonNull()) {
			SysRespiratoryExamination sysRespiratoryExamination = InputMapper.gson().fromJson(
					examinationDetailsOBJ.get("respiratorySystemExamination"), SysRespiratoryExamination.class);
			if (null != sysRespiratoryExamination) {
				sysRespiratoryExamination.setBenVisitID(benVisitID);
				sysRespiratoryExamination.setVisitCode(benVisitCode);
				respiratoryExmnSuccessFlag = commonNurseServiceImpl
						.saveSysRespiratoryExamination(sysRespiratoryExamination);
				// respiratoryExmnSuccessFlag = ancNurseServiceImpl
				// .saveSysRespiratoryExamination(sysRespiratoryExamination);
			}
		}

		// Save Central Nervous Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("centralNervousSystemExamination")
				&& !examinationDetailsOBJ.get("centralNervousSystemExamination").isJsonNull()) {
			SysCentralNervousExamination sysCentralNervousExamination = InputMapper.gson().fromJson(
					examinationDetailsOBJ.get("centralNervousSystemExamination"), SysCentralNervousExamination.class);
			if (null != sysCentralNervousExamination) {
				sysCentralNervousExamination.setBenVisitID(benVisitID);
				sysCentralNervousExamination.setVisitCode(benVisitCode);
				centralNrvsExmnSuccessFlag = commonNurseServiceImpl
						.saveSysCentralNervousExamination(sysCentralNervousExamination);
				// centralNrvsExmnSuccessFlag = ancNurseServiceImpl
				// .saveSysCentralNervousExamination(sysCentralNervousExamination);
			}
		}

		// Save Muskeloskeletal Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("musculoskeletalSystemExamination")
				&& !examinationDetailsOBJ.get("musculoskeletalSystemExamination").isJsonNull()) {
			SysMusculoskeletalSystemExamination sysMusculoskeletalSystemExamination = InputMapper.gson().fromJson(
					examinationDetailsOBJ.get("musculoskeletalSystemExamination"),
					SysMusculoskeletalSystemExamination.class);
			if (null != sysMusculoskeletalSystemExamination) {
				sysMusculoskeletalSystemExamination.setBenVisitID(benVisitID);
				sysMusculoskeletalSystemExamination.setVisitCode(benVisitCode);
				muskelstlExmnSuccessFlag = commonNurseServiceImpl
						.saveSysMusculoskeletalSystemExamination(sysMusculoskeletalSystemExamination);
				// muskelstlExmnSuccessFlag = ancNurseServiceImpl
				// .saveSysMusculoskeletalSystemExamination(sysMusculoskeletalSystemExamination);

			}
		}

		// Save Genito Urinary Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("genitoUrinarySystemExamination")
				&& !examinationDetailsOBJ.get("genitoUrinarySystemExamination").isJsonNull()) {
			SysGenitourinarySystemExamination sysGenitourinarySystemExamination = InputMapper.gson().fromJson(
					examinationDetailsOBJ.get("genitoUrinarySystemExamination"),
					SysGenitourinarySystemExamination.class);
			if (null != sysGenitourinarySystemExamination) {
				sysGenitourinarySystemExamination.setBenVisitID(benVisitID);
				sysGenitourinarySystemExamination.setVisitCode(benVisitCode);
				genitorinaryExmnSuccessFlag = commonNurseServiceImpl
						.saveSysGenitourinarySystemExamination(sysGenitourinarySystemExamination);
				// genitorinaryExmnSuccessFlag = ancNurseServiceImpl
				// .saveSysGenitourinarySystemExamination(sysGenitourinarySystemExamination);

			}
		}

		// Save Obstetric Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("obstetricExamination")
				&& !examinationDetailsOBJ.get("obstetricExamination").isJsonNull()) {
			SysObstetricExamination sysObstetricExamination = InputMapper.gson()
					.fromJson(examinationDetailsOBJ.get("obstetricExamination"), SysObstetricExamination.class);
			if (null != sysObstetricExamination) {
				sysObstetricExamination.setBenVisitID(benVisitID);
				sysObstetricExamination.setVisitCode(benVisitCode);

				StringBuffer sb = new StringBuffer();
				if (sysObstetricExamination.getReasonsForHRP() != null) {
					for (String reasonForHrp : sysObstetricExamination.getReasonsForHRP()) {
						if (reasonForHrp != null)
							sb.append(reasonForHrp).append("||");
					}
					sysObstetricExamination
							.setReasonsForHRPDB(sb.length() > 0 ? sb.toString().substring(0, sb.length() - 2) : null);
				}

				obstetricExmnSuccessFlag = ancNurseServiceImpl.saveSysObstetricExamination(sysObstetricExamination);

			}
		}

		if ((null != genExmnSuccessFlag && genExmnSuccessFlag > 0)
				&& (null != headToToeExmnSuccessFlag && headToToeExmnSuccessFlag > 0)
				// && (null != gastroIntsExmnSuccessFlag &&
				// gastroIntsExmnSuccessFlag > 0)
				&& (null != cardiExmnSuccessFlag && cardiExmnSuccessFlag > 0)
				&& (null != respiratoryExmnSuccessFlag && respiratoryExmnSuccessFlag > 0)
				&& (null != centralNrvsExmnSuccessFlag && centralNrvsExmnSuccessFlag > 0)
				&& (null != muskelstlExmnSuccessFlag && muskelstlExmnSuccessFlag > 0)
				&& (null != genitorinaryExmnSuccessFlag && genitorinaryExmnSuccessFlag > 0)
				&& (null != obstetricExmnSuccessFlag && obstetricExmnSuccessFlag > 0)) {
			exmnSuccessFlag = genExmnSuccessFlag;
		}
		return exmnSuccessFlag;
	}

	// ----------Fetch ANC (Nurse) --------------------------------------

	@Override
	public String getBenVisitDetailsFrmNurseANC(Long benRegID, Long visitCode) {
		Map<String, Object> resMap = new HashMap<>();

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

		BeneficiaryVisitDetail visitDetail = commonNurseServiceImpl.getCSVisitDetails(benRegID, visitCode);

		resMap.put("ANCNurseVisitDetail", gson.toJson(visitDetail));

		resMap.put("BenAdherence", commonNurseServiceImpl.getBenAdherence(benRegID, visitCode));

		resMap.put("BenChiefComplaints", commonNurseServiceImpl.getBenChiefComplaints(benRegID, visitCode));
		// resMap.put("BenChiefComplaints",
		// ancNurseServiceImpl.getBenChiefComplaints(benRegID, benVisitID));

		resMap.put("Investigation", commonNurseServiceImpl.getLabTestOrders(benRegID, visitCode));

		return resMap.toString();
	}

	@Override
	public String getBenANCDetailsFrmNurseANC(Long benRegID, Long visitCode) throws IEMRException {
		Map<String, Object> resMap = new HashMap<>();

		// check the latest visit code for ANC visit, if any
		if (visitCode == null && benRegID != null) {
			visitCode = beneficiaryFlowStatusRepo.getLatestVisitCode(benRegID, "ANC");
			if (visitCode == null)
				return resMap.toString();

		}

		resMap.put("ANCCareDetail", ancNurseServiceImpl.getANCCareDetails(benRegID, visitCode));

		resMap.put("ANCWomenVaccineDetails", ancNurseServiceImpl.getANCWomenVaccineDetails(benRegID, visitCode));

		return resMap.toString();
	}

	@Override
	public String getBenANCHistoryDetails(Long benRegID, Long visitCode) {
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

		return new Gson().toJson(HistoryDetailsMap);
	}

	@Override
	public String getBeneficiaryVitalDetails(Long beneficiaryRegID, Long visitCode) {
		Map<String, Object> resMap = new HashMap<>();

		resMap.put("benAnthropometryDetail",
				commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, visitCode));
		resMap.put("benPhysicalVitalDetail",
				commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode));

		return resMap.toString();
	}

	@Override
	public String getANCExaminationDetailsData(Long benRegID, Long visitCode) {
		Map<String, Object> examinationDetailsMap = new HashMap<String, Object>();

		examinationDetailsMap.put("generalExamination",
				commonNurseServiceImpl.getGeneralExaminationData(benRegID, visitCode));
		examinationDetailsMap.put("headToToeExamination",
				commonNurseServiceImpl.getHeadToToeExaminationData(benRegID, visitCode));

		/* Only for General OPD, Can remove from here */
		/*
		 * examinationDetailsMap.put("gastrointestinalExamination",
		 * ancNurseServiceImpl.getSysGastrointestinalExamination(benRegID, benVisitID));
		 */
		examinationDetailsMap.put("cardiovascularExamination",
				commonNurseServiceImpl.getCardiovascularExamination(benRegID, visitCode));
		examinationDetailsMap.put("respiratoryExamination",
				commonNurseServiceImpl.getRespiratoryExamination(benRegID, visitCode));
		examinationDetailsMap.put("centralNervousExamination",
				commonNurseServiceImpl.getSysCentralNervousExamination(benRegID, visitCode));
		examinationDetailsMap.put("musculoskeletalExamination",
				commonNurseServiceImpl.getMusculoskeletalExamination(benRegID, visitCode));
		examinationDetailsMap.put("genitourinaryExamination",
				commonNurseServiceImpl.getGenitourinaryExamination(benRegID, visitCode));
		examinationDetailsMap.put("obstetricExamination",
				ancNurseServiceImpl.getSysObstetricExamination(benRegID, visitCode));

		return new Gson().toJson(examinationDetailsMap);
	}

	// ---------- ENd of Fetch ANC (Nurse)--------------------------------------

	/*
	 * // ------- Fetch beneficiary all past history data ------------------ public
	 * String getANCPastHistoryData(Long beneficiaryRegID) { return
	 * commonNurseServiceImpl.fetchBenPastMedicalHistory(beneficiaryRegID); } ///
	 * ------- End of Fetch beneficiary all past history data ----------
	 * 
	 * // ------- Fetch beneficiary all Comorbid conditions history data //
	 * ------------------ public String getANCComorbidHistoryData(Long
	 * beneficiaryRegID) { return
	 * commonNurseServiceImpl.fetchBenComorbidityHistory(beneficiaryRegID); } ///
	 * ------- End of Fetch beneficiary all Comorbid conditions history data ///
	 * --------
	 * 
	 * // ------- Fetch beneficiary all Medication history data ----------- public
	 * String getANCMedicationHistoryData(Long beneficiaryRegID) { return
	 * commonNurseServiceImpl.fetchBenPersonalMedicationHistory(beneficiaryRegID); }
	 * /// ------- End of Fetch beneficiary all Medication history data --
	 * 
	 * // ------- Fetch beneficiary all Personal Tobacco history data //
	 * --------------- public String getANCPersonalTobaccoHistoryData(Long
	 * beneficiaryRegID) { return
	 * commonNurseServiceImpl.fetchBenPersonalTobaccoHistory(beneficiaryRegID); }
	 * /// ------- End of Fetch beneficiary all Personal Tobacco history data------
	 * 
	 * // ------- Fetch beneficiary all Personal Alcohol history data //
	 * --------------- public String getANCPersonalAlcoholHistoryData(Long
	 * beneficiaryRegID) { return
	 * commonNurseServiceImpl.fetchBenPersonalAlcoholHistory(beneficiaryRegID); }
	 * /// ------- End of Fetch beneficiary all Personal Alcohol history data ///
	 * ------
	 * 
	 * // ------- Fetch beneficiary all Personal Allergy history data //
	 * --------------- public String getANCPersonalAllergyHistoryData(Long
	 * beneficiaryRegID) { return
	 * commonNurseServiceImpl.fetchBenPersonalAllergyHistory(beneficiaryRegID); }
	 * /// ------- End of Fetch beneficiary all Personal Allergy history data------
	 * 
	 * // ------- Fetch beneficiary all Family history data --------------- public
	 * String getANCFamilyHistoryData(Long beneficiaryRegID) { return
	 * commonNurseServiceImpl.fetchBenPersonalFamilyHistory(beneficiaryRegID); } ///
	 * ------- End of Fetch beneficiary all Family history data ------
	 * 
	 * // ------- Fetch beneficiary all Menstrual history data ----------- public
	 * String getANCMenstrualHistoryData(Long beneficiaryRegID) { return
	 * commonNurseServiceImpl.fetchBenMenstrualHistory(beneficiaryRegID); } ///
	 * ------- End of Fetch beneficiary all Menstrual history data --
	 * 
	 * // ------- Fetch beneficiary all past obstetric history data ---------------
	 * public String getANCObstetricHistoryData(Long beneficiaryRegID) { return
	 * commonNurseServiceImpl.fetchBenPastObstetricHistory(beneficiaryRegID); } ///
	 * ------- End of Fetch beneficiary all past obstetric history data ------
	 * 
	 * // ------- Fetch beneficiary all Immunization history data ---------------
	 * public String getANCImmunizationHistoryData(Long beneficiaryRegID) { return
	 * commonNurseServiceImpl.fetchBenImmunizationHistory(beneficiaryRegID); } ///
	 * ------- End of Fetch beneficiary all Immunization history data ------
	 * 
	 * // ------- Fetch beneficiary all Child Vaccine history data ---------------
	 * public String getANCChildVaccineHistoryData(Long beneficiaryRegID) { return
	 * commonNurseServiceImpl.fetchBenOptionalVaccineHistory(beneficiaryRegID); }
	 * /// ------- End of Fetch beneficiary all Child Vaccine history data ------
	 */
	// -------Update (Nurse data from Doctor screen)----------------------

	/***
	 * 
	 * @param ancDetailsOBJ
	 * @param benVisitID
	 * @return success or failure flag for ANC update
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public int updateBenANCDetails(JsonObject ancDetailsOBJ) throws Exception {
		int ancSuccessFlag = 0;
		int ancCareSuccessFlag = 0;
		int ancImunizationSuccessFlag = 0;
		if (ancDetailsOBJ != null && ancDetailsOBJ.has("ancObstetricDetails")
				&& !ancDetailsOBJ.get("ancObstetricDetails").isJsonNull()) {
			// Update Ben ANC Care Details
			ANCCareDetails ancCareDetailsOBJ = InputMapper.gson().fromJson(ancDetailsOBJ.get("ancObstetricDetails"),
					ANCCareDetails.class);
			ancCareSuccessFlag = ancNurseServiceImpl.updateBenAncCareDetails(ancCareDetailsOBJ);

			if (ancCareDetailsOBJ.getLmpDate() != null && !ancCareDetailsOBJ.getLmpDate().isEmpty()
					&& ancCareDetailsOBJ.getLmpDate().length() >= 10) {
				String lmpDate = ancCareDetailsOBJ.getLmpDate().split("T")[0];
				new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(lmpDate).getTime());
				int i = benMenstrualDetailsRepo.updateLmpDate(
						new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(lmpDate).getTime()),
						ancCareDetailsOBJ.getModifiedBy(), ancCareDetailsOBJ.getBeneficiaryRegID(),
						ancCareDetailsOBJ.getVisitCode());
			}
		}
		if (ancDetailsOBJ != null && ancDetailsOBJ.has("ancImmunization")
				&& !ancDetailsOBJ.get("ancImmunization").isJsonNull()) {
			WrapperAncImmunization wrapperAncImmunizationOBJ = InputMapper.gson()
					.fromJson(ancDetailsOBJ.get("ancImmunization"), WrapperAncImmunization.class);
			ancImunizationSuccessFlag = ancNurseServiceImpl.updateBenAncImmunizationDetails(wrapperAncImmunizationOBJ);

		}
		if (ancCareSuccessFlag > 0 && ancImunizationSuccessFlag > 0) {
			ancSuccessFlag = ancImunizationSuccessFlag;
		}

		return ancSuccessFlag;
	}

	/**
	 * 
	 * @param requestOBJ
	 * @return success or failure flag for ANC History updating by Doctor
	 */
	@Transactional(rollbackFor = Exception.class)
	public int updateBenANCHistoryDetails(JsonObject ancHistoryOBJ) throws Exception {
		int pastHistorySuccessFlag = 0;
		int comrbidSuccessFlag = 0;
		int medicationSuccessFlag = 0;
		int personalHistorySuccessFlag = 0;
		int allergyHistorySuccessFlag = 0;
		int familyHistorySuccessFlag = 0;
		int menstrualHistorySuccessFlag = 0;
		int obstetricSuccessFlag = 0;
		int immunizationSuccessFlag = 0;
		int childVaccineSuccessFlag = 0;

		// Update Past History
		if (ancHistoryOBJ != null && ancHistoryOBJ.has("pastHistory")
				&& !ancHistoryOBJ.get("pastHistory").isJsonNull()) {
			BenMedHistory benMedHistory = InputMapper.gson().fromJson(ancHistoryOBJ.get("pastHistory"),
					BenMedHistory.class);
			pastHistorySuccessFlag = commonNurseServiceImpl.updateBenPastHistoryDetails(benMedHistory);

		} else {
			pastHistorySuccessFlag = 1;
		}

		// Update Comorbidity/concurrent Conditions
		if (ancHistoryOBJ != null && ancHistoryOBJ.has("comorbidConditions")
				&& !ancHistoryOBJ.get("comorbidConditions").isJsonNull()) {
			WrapperComorbidCondDetails wrapperComorbidCondDetails = InputMapper.gson()
					.fromJson(ancHistoryOBJ.get("comorbidConditions"), WrapperComorbidCondDetails.class);
			comrbidSuccessFlag = commonNurseServiceImpl.updateBenComorbidConditions(wrapperComorbidCondDetails);
		} else {
			comrbidSuccessFlag = 1;
		}

		// Update Medication History
		if (ancHistoryOBJ != null && ancHistoryOBJ.has("medicationHistory")
				&& !ancHistoryOBJ.get("medicationHistory").isJsonNull()) {
			WrapperMedicationHistory wrapperMedicationHistory = InputMapper.gson()
					.fromJson(ancHistoryOBJ.get("medicationHistory"), WrapperMedicationHistory.class);
			medicationSuccessFlag = commonNurseServiceImpl.updateBenMedicationHistory(wrapperMedicationHistory);
		} else {
			medicationSuccessFlag = 1;
		}
		// Update Personal History
		if (ancHistoryOBJ != null && ancHistoryOBJ.has("personalHistory")
				&& !ancHistoryOBJ.get("personalHistory").isJsonNull()) {
			// Update Ben Personal Habits..
			BenPersonalHabit personalHabit = InputMapper.gson().fromJson(ancHistoryOBJ.get("personalHistory"),
					BenPersonalHabit.class);

			personalHistorySuccessFlag = commonNurseServiceImpl.updateBenPersonalHistory(personalHabit);

			// Update Ben Allergy History..
			BenAllergyHistory benAllergyHistory = InputMapper.gson().fromJson(ancHistoryOBJ.get("personalHistory"),
					BenAllergyHistory.class);
			allergyHistorySuccessFlag = commonNurseServiceImpl.updateBenAllergicHistory(benAllergyHistory);

		} else {
			personalHistorySuccessFlag = 1;
			allergyHistorySuccessFlag = 1;
		}

		// Update Family History
		if (ancHistoryOBJ != null && ancHistoryOBJ.has("familyHistory")
				&& !ancHistoryOBJ.get("familyHistory").isJsonNull()) {
			BenFamilyHistory benFamilyHistory = InputMapper.gson().fromJson(ancHistoryOBJ.get("familyHistory"),
					BenFamilyHistory.class);
			familyHistorySuccessFlag = commonNurseServiceImpl.updateBenFamilyHistory(benFamilyHistory);
		} else {
			familyHistorySuccessFlag = 1;
		}

		// Update Menstrual History
		if (ancHistoryOBJ != null && ancHistoryOBJ.has("menstrualHistory")
				&& !ancHistoryOBJ.get("menstrualHistory").isJsonNull()) {
			BenMenstrualDetails menstrualDetails = InputMapper.gson().fromJson(ancHistoryOBJ.get("menstrualHistory"),
					BenMenstrualDetails.class);
			menstrualHistorySuccessFlag = commonNurseServiceImpl.updateMenstrualHistory(menstrualDetails);
		} else {
			menstrualHistorySuccessFlag = 1;
		}

		// Update Past Obstetric History
		if (ancHistoryOBJ != null && ancHistoryOBJ.has("femaleObstetricHistory")
				&& !ancHistoryOBJ.get("femaleObstetricHistory").isJsonNull()) {
			WrapperFemaleObstetricHistory wrapperFemaleObstetricHistory = InputMapper.gson()
					.fromJson(ancHistoryOBJ.get("femaleObstetricHistory"), WrapperFemaleObstetricHistory.class);

			obstetricSuccessFlag = commonNurseServiceImpl.updatePastObstetricHistory(wrapperFemaleObstetricHistory);
		} else {
			obstetricSuccessFlag = 1;
		}

		/** For Female above 12 yrs old and below 16 years old.. **/
		// Update Immunization History
		if (ancHistoryOBJ != null && ancHistoryOBJ.has("immunizationHistory")
				&& !ancHistoryOBJ.get("immunizationHistory").isJsonNull()) {

			JsonObject immunizationHistory = ancHistoryOBJ.getAsJsonObject("immunizationHistory");
			if (immunizationHistory.get("immunizationList") != null
					&& immunizationHistory.getAsJsonArray("immunizationList").size() > 0) {
				WrapperImmunizationHistory wrapperImmunizationHistory = InputMapper.gson()
						.fromJson(ancHistoryOBJ.get("immunizationHistory"), WrapperImmunizationHistory.class);
				immunizationSuccessFlag = commonNurseServiceImpl
						.updateChildImmunizationDetail(wrapperImmunizationHistory);
			} else {
				immunizationSuccessFlag = 1;
			}
		} else {
			immunizationSuccessFlag = 1;
		}

		// Update Other/Optional Vaccines History
		if (ancHistoryOBJ != null && ancHistoryOBJ.has("childVaccineDetails")
				&& !ancHistoryOBJ.get("childVaccineDetails").isJsonNull()) {
			WrapperChildOptionalVaccineDetail wrapperChildVaccineDetail = InputMapper.gson()
					.fromJson(ancHistoryOBJ.get("childVaccineDetails"), WrapperChildOptionalVaccineDetail.class);
			childVaccineSuccessFlag = commonNurseServiceImpl
					.updateChildOptionalVaccineDetail(wrapperChildVaccineDetail);
		} else {
			childVaccineSuccessFlag = 1;
		}

		int historyUpdateSuccessFlag = 0;

		if (pastHistorySuccessFlag > 0 && comrbidSuccessFlag > 0 && medicationSuccessFlag > 0
				&& allergyHistorySuccessFlag > 0 && familyHistorySuccessFlag > 0 && obstetricSuccessFlag > 0
				&& immunizationSuccessFlag > 0 && childVaccineSuccessFlag > 0 && personalHistorySuccessFlag > 0
				&& menstrualHistorySuccessFlag > 0) {

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
	public int updateBenANCVitalDetails(JsonObject vitalDetailsOBJ) throws Exception {
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
		}

		return vitalSuccessFlag;
	}

	/**
	 * 
	 * @param requestOBJ
	 * @return success or failure flag for Examinationm data updating
	 */
	@Transactional(rollbackFor = Exception.class)
	public int updateBenANCExaminationDetails(JsonObject examinationDetailsOBJ) throws Exception {

		int exmnSuccessFlag = 0;

		int genExmnSuccessFlag = 0;
		int headToToeExmnSuccessFlag = 0;
		// int gastroIntsExmnSuccessFlag = 0;
		int cardiExmnSuccessFlag = 0;
		int respiratoryExmnSuccessFlag = 0;
		int centralNrvsExmnSuccessFlag = 0;
		int muskelstlExmnSuccessFlag = 0;
		int genitorinaryExmnSuccessFlag = 0;
		int obstetricExmnSuccessFlag = 0;

		// Save General Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("generalExamination")
				&& !examinationDetailsOBJ.get("generalExamination").isJsonNull()) {
			PhyGeneralExamination generalExamination = InputMapper.gson()
					.fromJson(examinationDetailsOBJ.get("generalExamination"), PhyGeneralExamination.class);
			genExmnSuccessFlag = commonNurseServiceImpl.updatePhyGeneralExamination(generalExamination);
		}

		// Save Head to toe Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("headToToeExamination")
				&& !examinationDetailsOBJ.get("headToToeExamination").isJsonNull()) {
			PhyHeadToToeExamination headToToeExamination = InputMapper.gson()
					.fromJson(examinationDetailsOBJ.get("headToToeExamination"), PhyHeadToToeExamination.class);
			headToToeExmnSuccessFlag = commonNurseServiceImpl.updatePhyHeadToToeExamination(headToToeExamination);
		}

		// Save Cardio Vascular Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("cardioVascularExamination")
				&& !examinationDetailsOBJ.get("cardioVascularExamination").isJsonNull()) {
			SysCardiovascularExamination cardiovascularExamination = InputMapper.gson().fromJson(
					examinationDetailsOBJ.get("cardioVascularExamination"), SysCardiovascularExamination.class);
			cardiExmnSuccessFlag = commonNurseServiceImpl.updateSysCardiovascularExamination(cardiovascularExamination);
		}

		// Save Respiratory Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("respiratorySystemExamination")
				&& !examinationDetailsOBJ.get("respiratorySystemExamination").isJsonNull()) {
			SysRespiratoryExamination sysRespiratoryExamination = InputMapper.gson().fromJson(
					examinationDetailsOBJ.get("respiratorySystemExamination"), SysRespiratoryExamination.class);
			respiratoryExmnSuccessFlag = commonNurseServiceImpl
					.updateSysRespiratoryExamination(sysRespiratoryExamination);
		}

		// Save Central Nervous Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("centralNervousSystemExamination")
				&& !examinationDetailsOBJ.get("centralNervousSystemExamination").isJsonNull()) {
			SysCentralNervousExamination sysCentralNervousExamination = InputMapper.gson().fromJson(
					examinationDetailsOBJ.get("centralNervousSystemExamination"), SysCentralNervousExamination.class);
			centralNrvsExmnSuccessFlag = commonNurseServiceImpl
					.updateSysCentralNervousExamination(sysCentralNervousExamination);
		}

		// Save Muskeloskeletal Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("musculoskeletalSystemExamination")
				&& !examinationDetailsOBJ.get("musculoskeletalSystemExamination").isJsonNull()) {
			SysMusculoskeletalSystemExamination sysMusculoskeletalSystemExamination = InputMapper.gson().fromJson(
					examinationDetailsOBJ.get("musculoskeletalSystemExamination"),
					SysMusculoskeletalSystemExamination.class);
			muskelstlExmnSuccessFlag = commonNurseServiceImpl
					.updateSysMusculoskeletalSystemExamination(sysMusculoskeletalSystemExamination);
		}

		// Save Genito Urinary Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("genitoUrinarySystemExamination")
				&& !examinationDetailsOBJ.get("genitoUrinarySystemExamination").isJsonNull()) {
			SysGenitourinarySystemExamination sysGenitourinarySystemExamination = InputMapper.gson().fromJson(
					examinationDetailsOBJ.get("genitoUrinarySystemExamination"),
					SysGenitourinarySystemExamination.class);
			genitorinaryExmnSuccessFlag = commonNurseServiceImpl
					.updateSysGenitourinarySystemExamination(sysGenitourinarySystemExamination);
		}

		// Save Obstetric Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("obstetricExamination")
				&& !examinationDetailsOBJ.get("obstetricExamination").isJsonNull()) {
			SysObstetricExamination sysObstetricExamination = InputMapper.gson()
					.fromJson(examinationDetailsOBJ.get("obstetricExamination"), SysObstetricExamination.class);

			StringBuffer sb = new StringBuffer();
			if (sysObstetricExamination.getReasonsForHRP() != null) {
				for (String reasonForHrp : sysObstetricExamination.getReasonsForHRP()) {
					if (reasonForHrp != null)
						sb.append(reasonForHrp).append("||");
				}
				sysObstetricExamination
						.setReasonsForHRPDB(sb.length() > 0 ? sb.toString().substring(0, sb.length() - 2) : null);
			}

			obstetricExmnSuccessFlag = ancNurseServiceImpl.updateSysObstetricExamination(sysObstetricExamination);
		}

//		if (genExmnSuccessFlag > 0 && headToToeExmnSuccessFlag > 0 && cardiExmnSuccessFlag > 0
//				&& respiratoryExmnSuccessFlag > 0 && centralNrvsExmnSuccessFlag > 0 && muskelstlExmnSuccessFlag > 0
//				&& genitorinaryExmnSuccessFlag > 0 && obstetricExmnSuccessFlag > 0) {
//			exmnSuccessFlag = genExmnSuccessFlag;
//		}
		return 1;
	}

	public String getBenANCNurseData(Long benRegID, Long visitCode) throws IEMRException {
		Map<String, Object> resMap = new HashMap<>();

		resMap.put("anc", getBenANCDetailsFrmNurseANC(benRegID, visitCode));

		resMap.put("history", getBenANCHistoryDetails(benRegID, visitCode));

		resMap.put("vitals", getBeneficiaryVitalDetails(benRegID, visitCode));

		resMap.put("examination", getANCExaminationDetailsData(benRegID, visitCode));

		return resMap.toString();
	}

	// get ANC doctor data for update
	public String getBenCaseRecordFromDoctorANC(Long benRegID, Long visitCode) {
		Map<String, Object> resMap = new HashMap<>();

		resMap.put("findings", commonDoctorServiceImpl.getFindingsDetails(benRegID, visitCode));

		String diagnosis_prescription = ancDoctorServiceImpl.getANCDiagnosisDetails(benRegID, visitCode);
		resMap.put("diagnosis", diagnosis_prescription);
		if (diagnosis_prescription != null) {

			PrescriptionDetail pd = new Gson().fromJson(diagnosis_prescription, PrescriptionDetail.class);
			if (pd != null && pd.getCounsellingProvided() != null) {
				resMap.put("counsellingProvidedList", new Gson().toJson(pd.getCounsellingProvided().split("\\|\\|")));
			}
		}

		resMap.put("investigation", commonDoctorServiceImpl.getInvestigationDetails(benRegID, visitCode));

		resMap.put("prescription", commonDoctorServiceImpl.getPrescribedDrugs(benRegID, visitCode));

		resMap.put("Refer", commonDoctorServiceImpl.getReferralDetails(benRegID, visitCode, false));

		resMap.put("fetosenseData", commonDoctorServiceImpl.getFetosenseData(benRegID, visitCode));

		resMap.put("LabReport",
				new Gson().toJson(labTechnicianServiceImpl.getLabResultDataForBen(benRegID, visitCode)));

		resMap.put("GraphData", new Gson().toJson(commonNurseServiceImpl.getGraphicalTrendData(benRegID, "anc")));

		resMap.put("ArchivedVisitcodeForLabResult",
				labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(benRegID, visitCode));

		return resMap.toString();
	}

	@Transactional(rollbackFor = Exception.class)
	public Long updateANCDoctorData(JsonObject requestOBJ, String Authorization) throws Exception {
		Long updateSuccessFlag = null;
		Long prescriptionID = null;
		Long investigationSuccessFlag = null;
		Integer findingSuccessFlag = null;
		Integer diagnosisSuccessFlag = null;
		Integer prescriptionSuccessFlag = null;
		Long referSaveSuccessFlag = null;
		Integer tcRequestStatusFlag = null;

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
				findingSuccessFlag = commonDoctorServiceImpl.updateDocFindings(wrapperAncFindings);
			} else {
				findingSuccessFlag = 1;
			}

			// Generate ANC wrapper class OBJ
			WrapperBenInvestigationANC wrapperBenInvestigationANC = InputMapper.gson()
					.fromJson(requestOBJ.get("investigation"), WrapperBenInvestigationANC.class);

			// generate prescription OBJ & diagnosis OBJ
			ANCDiagnosis ancDiagnosis = null;
			PrescriptionDetail prescriptionDetail = null;
			if (requestOBJ.has("diagnosis") && !requestOBJ.get("diagnosis").isJsonNull()) {
				// diagnosis OBJ
				ancDiagnosis = InputMapper.gson().fromJson(requestOBJ.get("diagnosis"), ANCDiagnosis.class);

				// prescription OBJ
				prescriptionDetail = InputMapper.gson().fromJson(requestOBJ.get("diagnosis"), PrescriptionDetail.class);
				if (ancDiagnosis != null && ancDiagnosis.getSpecialistDiagnosis() != null)
					prescriptionDetail.setInstruction(ancDiagnosis.getSpecialistDiagnosis());
				prescriptionDetail.setExternalInvestigation(wrapperBenInvestigationANC.getExternalInvestigations());
			}
			
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

			if (prescriptionDetail != null) {
				prescriptionID = prescriptionDetail.getPrescriptionID();
				commonNurseServiceImpl.updatePrescription(prescriptionDetail);
			}

			// update diagnosis
			if (ancDiagnosis != null) {
				diagnosisSuccessFlag = ancDoctorServiceImpl.updateBenANCDiagnosis(ancDiagnosis);
			} else {
				diagnosisSuccessFlag = 1;
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
				Integer r = commonNurseServiceImpl.saveBenPrescribedDrugsList(prescribedDrugDetailList);
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
					&& (diagnosisSuccessFlag != null && diagnosisSuccessFlag > 0)
					&& (investigationSuccessFlag != null && investigationSuccessFlag > 0)
					&& (prescriptionSuccessFlag != null && prescriptionSuccessFlag > 0)
					&& (referSaveSuccessFlag != null && referSaveSuccessFlag > 0)) {

				// call method to update beneficiary flow table
				if (prescriptionID != null) {
					commonUtilityClass.setPrescriptionID(prescriptionID);
					commonUtilityClass.setVisitCategoryID(4);
					commonUtilityClass.setAuthorization(Authorization);

				}
				int i = commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(commonUtilityClass,
						isTestPrescribed, isMedicinePrescribed, tcRequestOBJ);
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

	@Autowired
	private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;
	@Autowired
	private BenAnthropometryRepo benAnthropometryRepo;
	@Autowired
	private BenMedHistoryRepo benMedHistoryRepo;
	@Autowired
	private BencomrbidityCondRepo bencomrbidityCondRepo;

	// check for HRP identification, all visits will be considered
	@Override
	public String getHRPStatus(HrpStatusAndReasonsRequestModel hrpStatusAndReasonsRequestModel) throws Exception {
		Boolean isHRP = false;
		Set<String> reasonForHrp = new HashSet();
		Map<String, Object> responseMap = new HashMap<>();

		// check comorbidity condition
		if (hrpStatusAndReasonsRequestModel.getComorbidConditions() != null
				&& hrpStatusAndReasonsRequestModel.getComorbidConditions().length > 0) {
			for (String comorbidity : hrpStatusAndReasonsRequestModel.getComorbidConditions()) {
				if (comorbidity.equalsIgnoreCase("Pregnancy induced hypertension")
						|| comorbidity.equalsIgnoreCase("pre-eclampsia")
						|| comorbidity.equalsIgnoreCase("pre-eclampsic toxemia")
						|| comorbidity.equalsIgnoreCase("Syphilis") || comorbidity.equalsIgnoreCase("HIV positive")
						|| comorbidity.equalsIgnoreCase("Gestational Diabetes Mellitus")
						|| comorbidity.equalsIgnoreCase("Hypothyroidism"))

					reasonForHrp.add(comorbidity);

			}
		}

		// check past illness
		if (hrpStatusAndReasonsRequestModel.getPastIllness() != null
				&& hrpStatusAndReasonsRequestModel.getPastIllness().length > 0) {
			for (String pastIllness : hrpStatusAndReasonsRequestModel.getPastIllness()) {
				if (pastIllness.length() > 0 && !pastIllness.equalsIgnoreCase("None"))
					reasonForHrp.add(pastIllness);

			}
		}

		// age, Young Primi Elderly Gravida
		if (hrpStatusAndReasonsRequestModel.getBenificiaryAge() != null) {
			if (hrpStatusAndReasonsRequestModel.getBenificiaryAge() < 20)
				reasonForHrp.add("Young Primi");
			else if (hrpStatusAndReasonsRequestModel.getBenificiaryAge() > 35)
				reasonForHrp.add("Elderly Gravida");
		}

		// blood group
		if (hrpStatusAndReasonsRequestModel.getBloodGroupType() != null
				&& hrpStatusAndReasonsRequestModel.getBloodGroupType().toLowerCase().contains("-ve")) {
			reasonForHrp.add("Rh negative");
		}

		// Anthropometry, height
		if (hrpStatusAndReasonsRequestModel.getBeneficiaryHeight() != null
				&& hrpStatusAndReasonsRequestModel.getBeneficiaryHeight() < 140)
			reasonForHrp.add("Short height woman");

		// vitals, heamolglobin
		if (hrpStatusAndReasonsRequestModel.getHemoglobin() != null
				&& hrpStatusAndReasonsRequestModel.getHemoglobin() < 7)
			reasonForHrp.add("Hemoglobin is less than 7");

		// obstetric examination
		if (hrpStatusAndReasonsRequestModel.getMalPresentation() != null
				&& hrpStatusAndReasonsRequestModel.getMalPresentation())
			reasonForHrp.add("Mal Presentation");
		if (hrpStatusAndReasonsRequestModel.getLowLyingPlacenta() != null
				&& hrpStatusAndReasonsRequestModel.getLowLyingPlacenta())
			reasonForHrp.add("Low Lying Placenta");
		if (hrpStatusAndReasonsRequestModel.getVertebralDeformity() != null
				&& hrpStatusAndReasonsRequestModel.getVertebralDeformity())
			reasonForHrp.add("Vertebral Deformity");

		// past obstetric history, preg complication
		// System.out.println(new
		// Gson().toJson(hrpStatusAndReasonsRequestModel.getPastObstetric()));
		if (hrpStatusAndReasonsRequestModel.getPastObstetric() != null
				&& hrpStatusAndReasonsRequestModel.getPastObstetric().size() > 0) {
			for (PastObstetricHostoryHRP obj : hrpStatusAndReasonsRequestModel.getPastObstetric()) {
				// complication during pregnancy
				if (obj.getComplicationDuringPregnancy() != null && obj.getComplicationDuringPregnancy().size() > 0) {
					for (ComplicationDuringPregnancyHRP complicationDuringPregnancyHRP : obj
							.getComplicationDuringPregnancy()) {
						if (complicationDuringPregnancyHRP.getPregComplicationType().equalsIgnoreCase("Twins")
								|| complicationDuringPregnancyHRP.getPregComplicationType()
										.equalsIgnoreCase("Multiple pregnancy"))
							reasonForHrp.add(complicationDuringPregnancyHRP.getPregComplicationType());

					}
				}

				// complication during delivery
				if (obj.getDeliveryComplication() != null && obj.getDeliveryComplication().size() > 0) {
					for (DeliveryComplicationHRP deliveryComplicationHRP : obj.getDeliveryComplication()) {
						if (deliveryComplicationHRP.getDeliveryComplicationType()
								.equalsIgnoreCase("Obstructed Delivery"))
							reasonForHrp.add(deliveryComplicationHRP.getDeliveryComplicationType());

					}
				}

				// congenitalAnomalies
				if (obj.getCongenitalAnomalies() != null && obj.getCongenitalAnomalies().length() > 0)
					reasonForHrp.add("congenital Anomalies of new born");

				// durationOfPregnancy
				if (obj.getDurationOfPregnancy() != null && obj.getDurationOfPregnancy().getDurationType() != null
						&& obj.getDurationOfPregnancy().getDurationType().equalsIgnoreCase("preterm"))
					reasonForHrp.add("previous preterm pregnancy");

				// Type of delivery
				if (obj.getTypeOfDelivery() != null && obj.getTypeOfDelivery().getDeliveryType() != null
						&& obj.getTypeOfDelivery().getDeliveryType().equalsIgnoreCase("Cesarean Section (LSCS)"))
					reasonForHrp.add(obj.getTypeOfDelivery().getDeliveryType());

			}
		}

		if (reasonForHrp.size() > 0)
			isHRP = true;

		responseMap.put("isHRP", isHRP);
		responseMap.put("reasonForHrp", reasonForHrp);
		return new Gson().toJson(responseMap);
	}

	@Override
	public SysObstetricExamination getHRPInformation(Long beneficiaryRegId) throws IEMRException {
		SysObstetricExamination sysObstetricExamination = sysObstetricExaminationRepo
				.findFirstByBeneficiaryRegIDOrderByIDDesc(beneficiaryRegId);
		if (sysObstetricExamination != null && sysObstetricExamination.getReasonsForHRPDB() != null) {
			String[] reasonForHrp = sysObstetricExamination.getReasonsForHRPDB().split("\\|\\|");
			if (reasonForHrp != null && reasonForHrp.length > 0)
				sysObstetricExamination.setReasonsForHRP(reasonForHrp);
		}
		return sysObstetricExamination;
	}
}
