/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.service.generalOPD;

import java.sql.Timestamp;
import java.text.ParseException;
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
import com.iemr.mmu.data.anc.BenAdherence;
import com.iemr.mmu.data.anc.BenAllergyHistory;
import com.iemr.mmu.data.anc.BenChildDevelopmentHistory;
import com.iemr.mmu.data.anc.BenFamilyHistory;
import com.iemr.mmu.data.anc.BenMedHistory;
import com.iemr.mmu.data.anc.BenMenstrualDetails;
import com.iemr.mmu.data.anc.BenPersonalHabit;
import com.iemr.mmu.data.anc.ChildFeedingDetails;
import com.iemr.mmu.data.anc.OralExamination;
import com.iemr.mmu.data.anc.PerinatalHistory;
import com.iemr.mmu.data.anc.PhyGeneralExamination;
import com.iemr.mmu.data.anc.PhyHeadToToeExamination;
import com.iemr.mmu.data.anc.SysCardiovascularExamination;
import com.iemr.mmu.data.anc.SysCentralNervousExamination;
import com.iemr.mmu.data.anc.SysGastrointestinalExamination;
import com.iemr.mmu.data.anc.SysGenitourinarySystemExamination;
import com.iemr.mmu.data.anc.SysMusculoskeletalSystemExamination;
import com.iemr.mmu.data.anc.SysRespiratoryExamination;
import com.iemr.mmu.data.anc.WrapperAncFindings;
import com.iemr.mmu.data.anc.WrapperBenInvestigationANC;
import com.iemr.mmu.data.anc.WrapperChildOptionalVaccineDetail;
import com.iemr.mmu.data.anc.WrapperComorbidCondDetails;
import com.iemr.mmu.data.anc.WrapperFemaleObstetricHistory;
import com.iemr.mmu.data.anc.WrapperImmunizationHistory;
import com.iemr.mmu.data.anc.WrapperMedicationHistory;
import com.iemr.mmu.data.nurse.BenAnthropometryDetail;
import com.iemr.mmu.data.nurse.BenPhysicalVitalDetail;
import com.iemr.mmu.data.nurse.BeneficiaryVisitDetail;
import com.iemr.mmu.data.nurse.CommonUtilityClass;
import com.iemr.mmu.data.quickConsultation.BenChiefComplaint;
import com.iemr.mmu.data.quickConsultation.PrescribedDrugDetail;
import com.iemr.mmu.data.quickConsultation.PrescriptionDetail;
import com.iemr.mmu.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.mmu.repo.nurse.BenVisitDetailRepo;
import com.iemr.mmu.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.mmu.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.mmu.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.mmu.service.common.transaction.CommonServiceImpl;
import com.iemr.mmu.service.family_planning.FamilyPlanningService;
import com.iemr.mmu.service.labtechnician.LabTechnicianServiceImpl;
import com.iemr.mmu.service.tele_consultation.SMSGatewayServiceImpl;
import com.iemr.mmu.utils.exception.IEMRException;
import com.iemr.mmu.utils.mapper.InputMapper;

/***
 * 
 * @author NE298657
 *
 */
@Service
public class GeneralOPDServiceImpl implements GeneralOPDService {
	@Autowired
	private CommonNurseServiceImpl commonNurseServiceImpl;
	@Autowired
	private CommonDoctorServiceImpl commonDoctorServiceImpl;
	@Autowired
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;
	@Autowired
	private GeneralOPDDoctorServiceImpl generalOPDDoctorServiceImpl;
	@Autowired
	private LabTechnicianServiceImpl labTechnicianServiceImpl;
	@Autowired
	private CommonServiceImpl commonServiceImpl;
	@Autowired
	private SMSGatewayServiceImpl sMSGatewayServiceImpl;

	@Autowired
	private FamilyPlanningService familyPlanningService;
	@Autowired
	private BenVisitDetailRepo benVisitDetailRepo;

	/// --------------- start of saving nurse data ------------------------
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String saveNurseData(JsonObject requestOBJ, String Authorization) throws Exception {
		Long historySaveSuccessFlag = null;
		Long vitalSaveSuccessFlag = null;
		Long examtnSaveSuccessFlag = null;
		Long saveSuccessFlag = null;
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
				if (requestOBJ.has("historyDetails") && !requestOBJ.get("historyDetails").isJsonNull())
					historySaveSuccessFlag = saveBenGeneralOPDHistoryDetails(
							requestOBJ.getAsJsonObject("historyDetails"), benVisitID, benVisitCode);

				// call method to save vital data
				if (requestOBJ.has("vitalDetails") && !requestOBJ.get("vitalDetails").isJsonNull())
					vitalSaveSuccessFlag = saveBenVitalDetails(requestOBJ.getAsJsonObject("vitalDetails"), benVisitID,
							benVisitCode);

				// call method to save examination data
				if (requestOBJ.has("examinationDetails") && !requestOBJ.get("examinationDetails").isJsonNull())
					examtnSaveSuccessFlag = saveBenExaminationDetails(requestOBJ.getAsJsonObject("examinationDetails"),
							benVisitID, benVisitCode);
			} else {
				throw new RuntimeException("Error occurred while creating beneficiary visit");
			}

			if ((null != historySaveSuccessFlag && historySaveSuccessFlag > 0)
					&& (null != vitalSaveSuccessFlag && vitalSaveSuccessFlag > 0)
					&& (null != examtnSaveSuccessFlag && examtnSaveSuccessFlag > 0)) {

				/**
				 * We have to write new code to update ben status flow new logic
				 */

				int i = updateBenStatusFlagAfterNurseSaveSuccess(tmpOBJ, benVisitID, benFlowID, benVisitCode,
						nurseUtilityClass.getVanID(), tcRequestOBJ);

				// if (i > 0)
				saveSuccessFlag = historySaveSuccessFlag;
//				else
//					throw new RuntimeException("Error occurred while saving data. Beneficiary status update failed");

				if (i > 0 && tcRequestOBJ != null && tcRequestOBJ.getWalkIn() == false) {
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

	@Override
	public Map<String, Long> saveBenVisitDetails(JsonObject visitDetailsOBJ, CommonUtilityClass nurseUtilityClass)
			throws Exception {
		Map<String, Long> visitIdAndCodeMap = new HashMap<>();
		Long benVisitID = null;
		if (visitDetailsOBJ != null && visitDetailsOBJ.has("visitDetails")
				&& !visitDetailsOBJ.get("visitDetails").isJsonNull()) {
			// Save Beneficiary visit details
			BeneficiaryVisitDetail benVisitDetailsOBJ = InputMapper.gson().fromJson(visitDetailsOBJ.get("visitDetails"),
					BeneficiaryVisitDetail.class);
			benVisitID = commonNurseServiceImpl.saveBeneficiaryVisitDetails(benVisitDetailsOBJ);

			// 07-06-2018 visit code
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

				if (visitDetailsOBJ.has("adherence") && !visitDetailsOBJ.get("adherence").isJsonNull()) {
					// Save Ben Adherence
					BenAdherence benAdherence = InputMapper.gson().fromJson(visitDetailsOBJ.get("adherence"),
							BenAdherence.class);
					benAdherence.setBenVisitID(benVisitID);
					benAdherence.setVisitCode(benVisitCode);
					commonNurseServiceImpl.saveBenAdherenceDetails(benAdherence);
				}
			}
			visitIdAndCodeMap.put("visitID", benVisitID);
			visitIdAndCodeMap.put("visitCode", benVisitCode);
		}
		return visitIdAndCodeMap;
	}

	@Override
	public Long saveBenGeneralOPDHistoryDetails(JsonObject generalOPDHistoryOBJ, Long benVisitID, Long benVisitCode)
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
		if (generalOPDHistoryOBJ != null && generalOPDHistoryOBJ.has("pastHistory")
				&& !generalOPDHistoryOBJ.get("pastHistory").isJsonNull()) {
			BenMedHistory benMedHistory = InputMapper.gson().fromJson(generalOPDHistoryOBJ.get("pastHistory"),
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
		if (generalOPDHistoryOBJ != null && generalOPDHistoryOBJ.has("comorbidConditions")
				&& !generalOPDHistoryOBJ.get("comorbidConditions").isJsonNull()) {
			WrapperComorbidCondDetails wrapperComorbidCondDetails = InputMapper.gson()
					.fromJson(generalOPDHistoryOBJ.get("comorbidConditions"), WrapperComorbidCondDetails.class);
			if (null != wrapperComorbidCondDetails) {
				wrapperComorbidCondDetails.setBenVisitID(benVisitID);
				wrapperComorbidCondDetails.setVisitCode(benVisitCode);
				comrbidSuccessFlag = commonNurseServiceImpl.saveBenComorbidConditions(wrapperComorbidCondDetails);
			}
		} else {
			comrbidSuccessFlag = new Long(1);
		}

		// Save Medication History
		if (generalOPDHistoryOBJ != null && generalOPDHistoryOBJ.has("medicationHistory")
				&& !generalOPDHistoryOBJ.get("medicationHistory").isJsonNull()) {
			WrapperMedicationHistory wrapperMedicationHistory = InputMapper.gson()
					.fromJson(generalOPDHistoryOBJ.get("medicationHistory"), WrapperMedicationHistory.class);
			if (null != wrapperMedicationHistory) {
				wrapperMedicationHistory.setBenVisitID(benVisitID);
				wrapperMedicationHistory.setVisitCode(benVisitCode);
				medicationSuccessFlag = commonNurseServiceImpl.saveBenMedicationHistory(wrapperMedicationHistory);
			}

		} else {
			medicationSuccessFlag = new Long(1);
		}

		// Save Past Obstetric History
		if (generalOPDHistoryOBJ != null && generalOPDHistoryOBJ.has("femaleObstetricHistory")
				&& !generalOPDHistoryOBJ.get("femaleObstetricHistory").isJsonNull()) {
			WrapperFemaleObstetricHistory wrapperFemaleObstetricHistory = InputMapper.gson()
					.fromJson(generalOPDHistoryOBJ.get("femaleObstetricHistory"), WrapperFemaleObstetricHistory.class);

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
		if (generalOPDHistoryOBJ != null && generalOPDHistoryOBJ.has("menstrualHistory")
				&& !generalOPDHistoryOBJ.get("menstrualHistory").isJsonNull()) {
			BenMenstrualDetails menstrualDetails = InputMapper.gson()
					.fromJson(generalOPDHistoryOBJ.get("menstrualHistory"), BenMenstrualDetails.class);
			if (null != menstrualDetails) {
				menstrualDetails.setBenVisitID(benVisitID);
				menstrualDetails.setVisitCode(benVisitCode);
				menstrualHistorySuccessFlag = commonNurseServiceImpl.saveBenMenstrualHistory(menstrualDetails);
			}

		} else {
			menstrualHistorySuccessFlag = 1;
		}

		// Save Family History
		if (generalOPDHistoryOBJ != null && generalOPDHistoryOBJ.has("familyHistory")
				&& !generalOPDHistoryOBJ.get("familyHistory").isJsonNull()) {
			BenFamilyHistory benFamilyHistory = InputMapper.gson().fromJson(generalOPDHistoryOBJ.get("familyHistory"),
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
		if (generalOPDHistoryOBJ != null && generalOPDHistoryOBJ.has("personalHistory")
				&& !generalOPDHistoryOBJ.get("personalHistory").isJsonNull()) {
			// Save Ben Personal Habits..
			BenPersonalHabit personalHabit = InputMapper.gson().fromJson(generalOPDHistoryOBJ.get("personalHistory"),
					BenPersonalHabit.class);
			if (null != personalHabit) {
				personalHabit.setBenVisitID(benVisitID);
				personalHabit.setVisitCode(benVisitCode);
				personalHistorySuccessFlag = commonNurseServiceImpl.savePersonalHistory(personalHabit);
			}

			BenAllergyHistory benAllergyHistory = InputMapper.gson()
					.fromJson(generalOPDHistoryOBJ.get("personalHistory"), BenAllergyHistory.class);
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
		if (generalOPDHistoryOBJ != null && generalOPDHistoryOBJ.has("childVaccineDetails")
				&& !generalOPDHistoryOBJ.get("childVaccineDetails").isJsonNull()) {
			WrapperChildOptionalVaccineDetail wrapperChildVaccineDetail = InputMapper.gson()
					.fromJson(generalOPDHistoryOBJ.get("childVaccineDetails"), WrapperChildOptionalVaccineDetail.class);
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
		if (generalOPDHistoryOBJ != null && generalOPDHistoryOBJ.has("immunizationHistory")
				&& !generalOPDHistoryOBJ.get("immunizationHistory").isJsonNull()) {
			WrapperImmunizationHistory wrapperImmunizationHistory = InputMapper.gson()
					.fromJson(generalOPDHistoryOBJ.get("immunizationHistory"), WrapperImmunizationHistory.class);
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
		if (generalOPDHistoryOBJ != null && generalOPDHistoryOBJ.has("developmentHistory")
				&& !generalOPDHistoryOBJ.get("developmentHistory").isJsonNull()) {
			BenChildDevelopmentHistory benChildDevelopmentHistory = InputMapper.gson()
					.fromJson(generalOPDHistoryOBJ.get("developmentHistory"), BenChildDevelopmentHistory.class);

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
		if (generalOPDHistoryOBJ != null && generalOPDHistoryOBJ.has("feedingHistory")
				&& !generalOPDHistoryOBJ.get("feedingHistory").isJsonNull()) {
			ChildFeedingDetails childFeedingDetails = InputMapper.gson()
					.fromJson(generalOPDHistoryOBJ.get("feedingHistory"), ChildFeedingDetails.class);

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
		if (generalOPDHistoryOBJ != null && generalOPDHistoryOBJ.has("perinatalHistroy")
				&& !generalOPDHistoryOBJ.get("perinatalHistroy").isJsonNull()) {
			PerinatalHistory perinatalHistory = InputMapper.gson()
					.fromJson(generalOPDHistoryOBJ.get("perinatalHistroy"), PerinatalHistory.class);

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

	@Override
	public Long saveBenVitalDetails(JsonObject vitalDetailsOBJ, Long benVisitID, Long benVisitCode) throws Exception {
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

	@Override
	public Long saveBenExaminationDetails(JsonObject examinationDetailsOBJ, Long benVisitID, Long benVisitCode)
			throws Exception {

		// Save General Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("generalExamination")
				&& !examinationDetailsOBJ.get("generalExamination").isJsonNull()) {
			PhyGeneralExamination generalExamination = InputMapper.gson()
					.fromJson(examinationDetailsOBJ.get("generalExamination"), PhyGeneralExamination.class);
			if (null != generalExamination) {
				generalExamination.setBenVisitID(benVisitID);
				generalExamination.setVisitCode(benVisitCode);
				commonNurseServiceImpl.savePhyGeneralExamination(generalExamination);
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
				commonNurseServiceImpl.savePhyHeadToToeExamination(headToToeExamination);
			}

		}

		// Save Gastro Intestinal Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("gastroIntestinalExamination")
				&& !examinationDetailsOBJ.get("gastroIntestinalExamination").isJsonNull()) {
			SysGastrointestinalExamination gastrointestinalExamination = InputMapper.gson().fromJson(
					examinationDetailsOBJ.get("gastroIntestinalExamination"), SysGastrointestinalExamination.class);
			if (null != gastrointestinalExamination) {
				gastrointestinalExamination.setBenVisitID(benVisitID);
				gastrointestinalExamination.setVisitCode(benVisitCode);
				commonNurseServiceImpl.saveSysGastrointestinalExamination(gastrointestinalExamination);

			}
		}

		// Save cardioVascular Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("cardioVascularExamination")
				&& !examinationDetailsOBJ.get("cardioVascularExamination").isJsonNull()) {
			SysCardiovascularExamination cardiovascularExamination = InputMapper.gson().fromJson(
					examinationDetailsOBJ.get("cardioVascularExamination"), SysCardiovascularExamination.class);
			if (null != cardiovascularExamination) {
				cardiovascularExamination.setBenVisitID(benVisitID);
				cardiovascularExamination.setVisitCode(benVisitCode);
				commonNurseServiceImpl.saveSysCardiovascularExamination(cardiovascularExamination);

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
				commonNurseServiceImpl.saveSysRespiratoryExamination(sysRespiratoryExamination);
			}
		}

		// Save Central Nervous System Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("centralNervousSystemExamination")
				&& !examinationDetailsOBJ.get("centralNervousSystemExamination").isJsonNull()) {
			SysCentralNervousExamination sysCentralNervousExamination = InputMapper.gson().fromJson(
					examinationDetailsOBJ.get("centralNervousSystemExamination"), SysCentralNervousExamination.class);
			if (null != sysCentralNervousExamination) {
				sysCentralNervousExamination.setBenVisitID(benVisitID);
				sysCentralNervousExamination.setVisitCode(benVisitCode);
				commonNurseServiceImpl.saveSysCentralNervousExamination(sysCentralNervousExamination);
			}
		}

		// Save Musculoskeletal System Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("musculoskeletalSystemExamination")
				&& !examinationDetailsOBJ.get("musculoskeletalSystemExamination").isJsonNull()) {
			SysMusculoskeletalSystemExamination sysMusculoskeletalSystemExamination = InputMapper.gson().fromJson(
					examinationDetailsOBJ.get("musculoskeletalSystemExamination"),
					SysMusculoskeletalSystemExamination.class);
			if (null != sysMusculoskeletalSystemExamination) {
				sysMusculoskeletalSystemExamination.setBenVisitID(benVisitID);
				sysMusculoskeletalSystemExamination.setVisitCode(benVisitCode);
				commonNurseServiceImpl.saveSysMusculoskeletalSystemExamination(sysMusculoskeletalSystemExamination);

			}
		}

		// Save Genito Urinary System Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("genitoUrinarySystemExamination")
				&& !examinationDetailsOBJ.get("genitoUrinarySystemExamination").isJsonNull()) {
			SysGenitourinarySystemExamination sysGenitourinarySystemExamination = InputMapper.gson().fromJson(
					examinationDetailsOBJ.get("genitoUrinarySystemExamination"),
					SysGenitourinarySystemExamination.class);
			if (null != sysGenitourinarySystemExamination) {
				sysGenitourinarySystemExamination.setBenVisitID(benVisitID);
				sysGenitourinarySystemExamination.setVisitCode(benVisitCode);
				commonNurseServiceImpl.saveSysGenitourinarySystemExamination(sysGenitourinarySystemExamination);

			}
		}

		// save oral examination
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("oralDetails")
				&& !examinationDetailsOBJ.get("oralDetails").isJsonNull()) {

			OralExamination oralDetails = InputMapper.gson().fromJson(examinationDetailsOBJ.get("oralDetails"),
					OralExamination.class);
			if (null != oralDetails) {

				oralDetails.setBenVisitID(benVisitID);
				oralDetails.setVisitCode(benVisitCode);
				commonNurseServiceImpl.saveOralExamination(oralDetails);

			}

		}

		return (long) 1;

	}

	/**
	 * @param JsonObject
	 * @return saveSuccessFlag
	 * 
	 */
	/// --------------- start of saving doctor data ------------------------
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long saveDoctorData(JsonObject requestOBJ, String Authorization) throws Exception {
		Long saveSuccessFlag = null;
		Long prescriptionID = null;
		Long investigationSuccessFlag = null;
		Integer findingSuccessFlag = null;
		Integer prescriptionSuccessFlag = null;
		Long referSaveSuccessFlag = null;
		// Integer subVisitCategorySaveSuccessFlag = null;
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

			if (commonUtilityClass != null && commonUtilityClass.getSubVisitCategory() != null) {
				benVisitDetailRepo.updateSubVisitCategory(commonUtilityClass.getSubVisitCategory(),
						commonUtilityClass.getVisitCode());

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
						.saveBenReferDetails(requestOBJ.get("refer").getAsJsonObject(), false);
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
					saveSuccessFlag = investigationSuccessFlag;
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

	/// --------------- Start of Fetching GeneralOPD Nurse Data ----------------
	public String getBenVisitDetailsFrmNurseGOPD(Long benRegID, Long visitCode) {
		Map<String, Object> resMap = new HashMap<>();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

		BeneficiaryVisitDetail visitDetail = commonNurseServiceImpl.getCSVisitDetails(benRegID, visitCode);

		resMap.put("GOPDNurseVisitDetail", gson.toJson(visitDetail));

		resMap.put("BenAdherence", commonNurseServiceImpl.getBenAdherence(benRegID, visitCode));

		resMap.put("BenChiefComplaints", commonNurseServiceImpl.getBenChiefComplaints(benRegID, visitCode));

		return resMap.toString();
	}

	public String getBenHistoryDetails(Long benRegID, Long visitCode) throws IEMRException {
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

	public String getBeneficiaryVitalDetails(Long beneficiaryRegID, Long benVisitID) {
		Map<String, Object> resMap = new HashMap<>();

		resMap.put("benAnthropometryDetail",
				commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, benVisitID));
		resMap.put("benPhysicalVitalDetail",
				commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, benVisitID));

		return resMap.toString();
	}

	public String getExaminationDetailsData(Long benRegID, Long visitCode) throws IEMRException {
		Map<String, Object> examinationDetailsMap = new HashMap<String, Object>();

		examinationDetailsMap.put("generalExamination",
				commonNurseServiceImpl.getGeneralExaminationData(benRegID, visitCode));
		examinationDetailsMap.put("headToToeExamination",
				commonNurseServiceImpl.getHeadToToeExaminationData(benRegID, visitCode));
		examinationDetailsMap.put("gastrointestinalExamination",
				commonNurseServiceImpl.getSysGastrointestinalExamination(benRegID, visitCode));
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
		// oral examination
		examinationDetailsMap.put("oralDetails", commonNurseServiceImpl.getOralExamination(benRegID, visitCode));

		return new Gson().toJson(examinationDetailsMap);
	}

	/// --------------- END of Fetching GeneralOPD Nurse Data ----------------

	/// --------------- start of updating GeneralOPD Nurse Data ----------------
	@Transactional(rollbackFor = Exception.class)
	public int UpdateVisitDetails(JsonObject jsnOBJ) throws Exception {

		int chiefCompltUpdateRes = 0;

		if (jsnOBJ != null && jsnOBJ.has("visitDetails") && !jsnOBJ.get("visitDetails").isJsonNull()) {

			if (jsnOBJ.has("chiefComplaints") && !jsnOBJ.get("chiefComplaints").isJsonNull()) {
				// Update Ben Chief Complaints
				BenChiefComplaint[] benChiefComplaintArray = InputMapper.gson().fromJson(jsnOBJ.get("chiefComplaints"),
						BenChiefComplaint[].class);

				List<BenChiefComplaint> benChiefComplaintList = Arrays.asList(benChiefComplaintArray);

				chiefCompltUpdateRes = commonNurseServiceImpl.updateBenChiefComplaints(benChiefComplaintList);
			}
		}
		return chiefCompltUpdateRes;
	}

	/**
	 * 
	 * @param requestOBJ
	 * @return success or failure flag for General OPD History updating by Doctor
	 * @throws ParseException
	 */
	@Transactional(rollbackFor = Exception.class)
	public int updateBenHistoryDetails(JsonObject historyOBJ) throws IEMRException, ParseException {

		// Update Past History
		if (historyOBJ != null && historyOBJ.has("pastHistory") && !historyOBJ.get("pastHistory").isJsonNull()) {
			BenMedHistory benMedHistory = InputMapper.gson().fromJson(historyOBJ.get("pastHistory"),
					BenMedHistory.class);
			commonNurseServiceImpl.updateBenPastHistoryDetails(benMedHistory);

		}

		// Update Comorbidity/concurrent Conditions
		if (historyOBJ != null && historyOBJ.has("comorbidConditions")
				&& !historyOBJ.get("comorbidConditions").isJsonNull()) {
			WrapperComorbidCondDetails wrapperComorbidCondDetails = InputMapper.gson()
					.fromJson(historyOBJ.get("comorbidConditions"), WrapperComorbidCondDetails.class);
			commonNurseServiceImpl.updateBenComorbidConditions(wrapperComorbidCondDetails);
		}

		// Update Medication History
		if (historyOBJ != null && historyOBJ.has("medicationHistory")
				&& !historyOBJ.get("medicationHistory").isJsonNull()) {
			WrapperMedicationHistory wrapperMedicationHistory = InputMapper.gson()
					.fromJson(historyOBJ.get("medicationHistory"), WrapperMedicationHistory.class);
			commonNurseServiceImpl.updateBenMedicationHistory(wrapperMedicationHistory);
		}
		// Update Personal History
		if (historyOBJ != null && historyOBJ.has("personalHistory")
				&& !historyOBJ.get("personalHistory").isJsonNull()) {
			// Update Ben Personal Habits..
			BenPersonalHabit personalHabit = InputMapper.gson().fromJson(historyOBJ.get("personalHistory"),
					BenPersonalHabit.class);

			commonNurseServiceImpl.updateBenPersonalHistory(personalHabit);

			// Update Ben Allergy History..
			BenAllergyHistory benAllergyHistory = InputMapper.gson().fromJson(historyOBJ.get("personalHistory"),
					BenAllergyHistory.class);
			commonNurseServiceImpl.updateBenAllergicHistory(benAllergyHistory);

		}

		// Update Family History
		if (historyOBJ != null && historyOBJ.has("familyHistory") && !historyOBJ.get("familyHistory").isJsonNull()) {
			BenFamilyHistory benFamilyHistory = InputMapper.gson().fromJson(historyOBJ.get("familyHistory"),
					BenFamilyHistory.class);
			commonNurseServiceImpl.updateBenFamilyHistory(benFamilyHistory);
		}

		// Update Menstrual History
		if (historyOBJ != null && historyOBJ.has("menstrualHistory")
				&& !historyOBJ.get("menstrualHistory").isJsonNull()) {
			BenMenstrualDetails menstrualDetails = InputMapper.gson().fromJson(historyOBJ.get("menstrualHistory"),
					BenMenstrualDetails.class);
			commonNurseServiceImpl.updateMenstrualHistory(menstrualDetails);
		}

		// Update Past Obstetric History
		if (historyOBJ != null && historyOBJ.has("femaleObstetricHistory")
				&& !historyOBJ.get("femaleObstetricHistory").isJsonNull()) {
			WrapperFemaleObstetricHistory wrapperFemaleObstetricHistory = InputMapper.gson()
					.fromJson(historyOBJ.get("femaleObstetricHistory"), WrapperFemaleObstetricHistory.class);

			commonNurseServiceImpl.updatePastObstetricHistory(wrapperFemaleObstetricHistory);
		}

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

		// Update Other/Optional Vaccines History
		if (historyOBJ != null && historyOBJ.has("childVaccineDetails")
				&& !historyOBJ.get("childVaccineDetails").isJsonNull()) {
			WrapperChildOptionalVaccineDetail wrapperChildVaccineDetail = InputMapper.gson()
					.fromJson(historyOBJ.get("childVaccineDetails"), WrapperChildOptionalVaccineDetail.class);
			commonNurseServiceImpl.updateChildOptionalVaccineDetail(wrapperChildVaccineDetail);
		}

		// Update ChildFeeding History
		if (historyOBJ != null && historyOBJ.has("feedingHistory") && !historyOBJ.get("feedingHistory").isJsonNull()) {
			ChildFeedingDetails childFeedingDetails = InputMapper.gson().fromJson(historyOBJ.get("feedingHistory"),
					ChildFeedingDetails.class);

			if (null != childFeedingDetails) {
				if (childFeedingDetails.getTypeOfFoodIntolerances() != null
						&& childFeedingDetails.getTypeOfFoodIntolerances().length > 0) {
					StringBuffer sb = new StringBuffer();
					for (String foodIntolerence : childFeedingDetails.getTypeOfFoodIntolerances()) {
						sb.append(foodIntolerence).append("||");
					}

					childFeedingDetails.setTypeofFoodIntolerance(sb.toString());
				}

				commonNurseServiceImpl.updateChildFeedingHistory(childFeedingDetails);
			}

		}

		// Update Perinatal History
		if (historyOBJ != null && historyOBJ.has("perinatalHistroy")
				&& !historyOBJ.get("perinatalHistroy").isJsonNull()) {
			PerinatalHistory perinatalHistory = InputMapper.gson().fromJson(historyOBJ.get("perinatalHistroy"),
					PerinatalHistory.class);

			if (null != perinatalHistory) {
				commonNurseServiceImpl.updatePerinatalHistory(perinatalHistory);
			}

		}
		// Update Development History
		if (historyOBJ != null && historyOBJ.has("developmentHistory")
				&& !historyOBJ.get("developmentHistory").isJsonNull()) {
			BenChildDevelopmentHistory benChildDevelopmentHistory = InputMapper.gson()
					.fromJson(historyOBJ.get("developmentHistory"), BenChildDevelopmentHistory.class);

			if (null != benChildDevelopmentHistory) {
				commonNurseServiceImpl.updateChildDevelopmentHistory(benChildDevelopmentHistory);
			}

		}

		return 1;
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

	/**
	 * 
	 * @param requestOBJ
	 * @return success or failure flag for Examinationm data updating
	 */
	@Transactional(rollbackFor = Exception.class)
	public int updateBenExaminationDetails(JsonObject examinationDetailsOBJ) throws Exception {

		// update General Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("generalExamination")
				&& !examinationDetailsOBJ.get("generalExamination").isJsonNull()) {
			PhyGeneralExamination generalExamination = InputMapper.gson()
					.fromJson(examinationDetailsOBJ.get("generalExamination"), PhyGeneralExamination.class);
			commonNurseServiceImpl.updatePhyGeneralExamination(generalExamination);
		}

		// update Head to toe Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("headToToeExamination")
				&& !examinationDetailsOBJ.get("headToToeExamination").isJsonNull()) {
			PhyHeadToToeExamination headToToeExamination = InputMapper.gson()
					.fromJson(examinationDetailsOBJ.get("headToToeExamination"), PhyHeadToToeExamination.class);
			commonNurseServiceImpl.updatePhyHeadToToeExamination(headToToeExamination);
		}

		// update Gastro Intestinal Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("gastroIntestinalExamination")
				&& !examinationDetailsOBJ.get("gastroIntestinalExamination").isJsonNull()) {
			SysGastrointestinalExamination gastrointestinalExamination = InputMapper.gson().fromJson(
					examinationDetailsOBJ.get("gastroIntestinalExamination"), SysGastrointestinalExamination.class);
			commonNurseServiceImpl.updateSysGastrointestinalExamination(gastrointestinalExamination);
		}

		// update Cardio Vascular Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("cardioVascularExamination")
				&& !examinationDetailsOBJ.get("cardioVascularExamination").isJsonNull()) {
			SysCardiovascularExamination cardiovascularExamination = InputMapper.gson().fromJson(
					examinationDetailsOBJ.get("cardioVascularExamination"), SysCardiovascularExamination.class);
			commonNurseServiceImpl.updateSysCardiovascularExamination(cardiovascularExamination);
		}

		// update Respiratory Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("respiratorySystemExamination")
				&& !examinationDetailsOBJ.get("respiratorySystemExamination").isJsonNull()) {
			SysRespiratoryExamination sysRespiratoryExamination = InputMapper.gson().fromJson(
					examinationDetailsOBJ.get("respiratorySystemExamination"), SysRespiratoryExamination.class);
			commonNurseServiceImpl.updateSysRespiratoryExamination(sysRespiratoryExamination);
		}

		// update Central Nervous Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("centralNervousSystemExamination")
				&& !examinationDetailsOBJ.get("centralNervousSystemExamination").isJsonNull()) {
			SysCentralNervousExamination sysCentralNervousExamination = InputMapper.gson().fromJson(
					examinationDetailsOBJ.get("centralNervousSystemExamination"), SysCentralNervousExamination.class);
			commonNurseServiceImpl.updateSysCentralNervousExamination(sysCentralNervousExamination);
		}

		// update Muskeloskeletal Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("musculoskeletalSystemExamination")
				&& !examinationDetailsOBJ.get("musculoskeletalSystemExamination").isJsonNull()) {
			SysMusculoskeletalSystemExamination sysMusculoskeletalSystemExamination = InputMapper.gson().fromJson(
					examinationDetailsOBJ.get("musculoskeletalSystemExamination"),
					SysMusculoskeletalSystemExamination.class);
			commonNurseServiceImpl.updateSysMusculoskeletalSystemExamination(sysMusculoskeletalSystemExamination);
		}

		// update Genito Urinary Examination Details
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("genitoUrinarySystemExamination")
				&& !examinationDetailsOBJ.get("genitoUrinarySystemExamination").isJsonNull()) {
			SysGenitourinarySystemExamination sysGenitourinarySystemExamination = InputMapper.gson().fromJson(
					examinationDetailsOBJ.get("genitoUrinarySystemExamination"),
					SysGenitourinarySystemExamination.class);
			commonNurseServiceImpl.updateSysGenitourinarySystemExamination(sysGenitourinarySystemExamination);
		}

		// update oral examination
		if (examinationDetailsOBJ != null && examinationDetailsOBJ.has("oralDetails")
				&& !examinationDetailsOBJ.get("oralDetails").isJsonNull()) {
			OralExamination oralExamination = InputMapper.gson().fromJson(examinationDetailsOBJ.get("oralDetails"),
					OralExamination.class);

			if (oralExamination != null && oralExamination.getPreMalignantLesionTypeList() != null
					&& oralExamination.getPreMalignantLesionTypeList().length > 0) {
				StringBuffer sb = new StringBuffer();
				for (String s : oralExamination.getPreMalignantLesionTypeList()) {
					sb.append(s).append("||");
				}
				sb.substring(0, sb.length() - 2);
				oralExamination.setPreMalignantLesionType(sb.toString());
			}
			commonNurseServiceImpl.updateOralExamination(oralExamination);
		}

		return 1;
	}

	public String getBenGeneralOPDNurseData(Long benRegID, Long visitCode) throws Exception {
		Map<String, Object> resMap = new HashMap<>();

		resMap.put("history", getBenHistoryDetails(benRegID, visitCode));
		resMap.put("examination", getExaminationDetailsData(benRegID, visitCode));

		resMap.put("vitals", getBeneficiaryVitalDetails(benRegID, visitCode));

		return resMap.toString();
	}

	public String getBenCaseRecordFromDoctorGeneralOPD(Long benRegID, Long visitCode) throws IEMRException {
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

		resMap.put("Refer", commonDoctorServiceImpl.getReferralDetails(benRegID, visitCode, false));

		resMap.put("LabReport",
				new Gson().toJson(labTechnicianServiceImpl.getLabResultDataForBen(benRegID, visitCode)));

		resMap.put("GraphData", new Gson().toJson(commonNurseServiceImpl.getGraphicalTrendData(benRegID, "genOPD")));

		resMap.put("ArchivedVisitcodeForLabResult",
				labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(benRegID, visitCode));

		return resMap.toString();
	}

	// update doctor data
	@Transactional(rollbackFor = Exception.class)
	public Long updateGeneralOPDDoctorData(JsonObject requestOBJ, String Authorization) throws Exception {
		Long updateSuccessFlag = null;
		Long prescriptionID = null;
		Long investigationSuccessFlag = null;
		Integer findingSuccessFlag = null;
		Integer diagnosisSuccessFlag = null;
		Integer prescriptionSuccessFlag = null;
		Long referSaveSuccessFlag = null;
		// Integer tcRequestStatusFlag = null;

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

			if (commonUtilityClass != null && commonUtilityClass.getSubVisitCategory() != null) {
				benVisitDetailRepo.updateSubVisitCategory(commonUtilityClass.getSubVisitCategory(),
						commonUtilityClass.getVisitCode());

			}

			// update findings
			if (requestOBJ.has("findings") && !requestOBJ.get("findings").isJsonNull()) {

				WrapperAncFindings wrapperAncFindings = InputMapper.gson().fromJson(requestOBJ.get("findings"),
						WrapperAncFindings.class);
				findingSuccessFlag = commonDoctorServiceImpl.updateDocFindings(wrapperAncFindings);

			} else {
				findingSuccessFlag = 1;
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
				prescriptionID = prescriptionDetail.getPrescriptionID();
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

			// update prescription
			int p = commonNurseServiceImpl.updatePrescription(prescriptionDetail);

			if (p > 0)
				diagnosisSuccessFlag = 1;

			// save prescribed lab test
			if (isTestPrescribed == true) {
				WrapperBenInvestigationANC wrapperBenInvestigationANC = InputMapper.gson()
						.fromJson(requestOBJ.get("investigation"), WrapperBenInvestigationANC.class);

				if (wrapperBenInvestigationANC != null) {
					wrapperBenInvestigationANC.setPrescriptionID(prescriptionID);
					investigationSuccessFlag = commonNurseServiceImpl.saveBenInvestigation(wrapperBenInvestigationANC);
				}
			} else {
				investigationSuccessFlag = new Long(1);
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
					if (r > 0 && r != null) {
						prescriptionSuccessFlag = r;
					}

				} else {
					prescriptionSuccessFlag = 1;
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

			// check if all requested data updated/saved properly
			if ((findingSuccessFlag != null && findingSuccessFlag > 0)
					&& (diagnosisSuccessFlag != null && diagnosisSuccessFlag > 0)
					&& (investigationSuccessFlag != null && investigationSuccessFlag > 0)
					&& (prescriptionSuccessFlag != null && prescriptionSuccessFlag > 0)
					&& (referSaveSuccessFlag != null && referSaveSuccessFlag > 0)) {

				// call method to update beneficiary flow table
				if (prescriptionID != null) {
					commonUtilityClass.setPrescriptionID(prescriptionID);
					commonUtilityClass.setVisitCategoryID(6);
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

}
