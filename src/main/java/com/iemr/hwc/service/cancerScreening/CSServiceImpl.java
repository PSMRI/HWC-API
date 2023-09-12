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
package com.iemr.hwc.service.cancerScreening;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.iemr.hwc.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.hwc.data.doctor.CancerAbdominalExamination;
import com.iemr.hwc.data.doctor.CancerBreastExamination;
import com.iemr.hwc.data.doctor.CancerDiagnosis;
import com.iemr.hwc.data.doctor.CancerGynecologicalExamination;
import com.iemr.hwc.data.doctor.CancerOralExamination;
import com.iemr.hwc.data.doctor.WrapperCancerExamImgAnotasn;
import com.iemr.hwc.data.doctor.WrapperCancerSymptoms;
import com.iemr.hwc.data.nurse.BenCancerVitalDetail;
import com.iemr.hwc.data.nurse.BenFamilyCancerHistory;
import com.iemr.hwc.data.nurse.BenObstetricCancerHistory;
import com.iemr.hwc.data.nurse.BenPersonalCancerDietHistory;
import com.iemr.hwc.data.nurse.BenPersonalCancerHistory;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.tele_consultation.TCRequestModel;
import com.iemr.hwc.data.tele_consultation.TcSpecialistSlotBookingRequestOBJ;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.registrar.RegistrarRepoBenData;
import com.iemr.hwc.repo.tc_consultation.TCRequestModelRepo;
import com.iemr.hwc.service.anc.Utility;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonServiceImpl;
import com.iemr.hwc.service.tele_consultation.SMSGatewayServiceImpl;
import com.iemr.hwc.service.tele_consultation.TeleConsultationServiceImpl;
import com.iemr.hwc.utils.mapper.InputMapper;

@Service
public class CSServiceImpl implements CSService {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Value("${registrarQuickSearchByIdUrl}")
	private String registrarQuickSearchByIdUrl;

	private CSNurseServiceImpl cSNurseServiceImpl;
	private CSDoctorServiceImpl cSDoctorServiceImpl;

	private CSOncologistServiceImpl csOncologistServiceImpl;

	private CommonNurseServiceImpl commonNurseServiceImpl;
	private CSCarestreamServiceImpl cSCarestreamServiceImpl;
	private RegistrarRepoBenData registrarRepoBenData;
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;
	private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;
	@Autowired
	private TeleConsultationServiceImpl teleConsultationServiceImpl;
	@Autowired
	private CommonDoctorServiceImpl commonDoctorServiceImpl;
	@Autowired
	private CommonServiceImpl commonServiceImpl;
	@Autowired
	private TCRequestModelRepo tCRequestModelRepo;

	@Autowired
	public void setBeneficiaryFlowStatusRepo(BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo) {
		this.beneficiaryFlowStatusRepo = beneficiaryFlowStatusRepo;
	}

	@Autowired
	public void setCommonBenStatusFlowServiceImpl(CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl) {
		this.commonBenStatusFlowServiceImpl = commonBenStatusFlowServiceImpl;
	}

	@Autowired
	public void setRegistrarRepoBenData(RegistrarRepoBenData registrarRepoBenData) {
		this.registrarRepoBenData = registrarRepoBenData;
	}

	@Autowired
	public void setcSCarestreamServiceImpl(CSCarestreamServiceImpl cSCarestreamServiceImpl) {
		this.cSCarestreamServiceImpl = cSCarestreamServiceImpl;
	}

	@Autowired
	public void setCommonNurseServiceImpl(CommonNurseServiceImpl commonNurseServiceImpl) {
		this.commonNurseServiceImpl = commonNurseServiceImpl;
	}

	@Autowired
	public void setcSDoctorServiceImpl(CSDoctorServiceImpl cSDoctorServiceImpl) {
		this.cSDoctorServiceImpl = cSDoctorServiceImpl;
	}

	@Autowired
	public void setcSNurseServiceImpl(CSNurseServiceImpl cSNurseServiceImpl) {
		this.cSNurseServiceImpl = cSNurseServiceImpl;
	}

	@Autowired
	public void setCsOncologistServiceImpl(CSOncologistServiceImpl csOncologistServiceImpl) {
		this.csOncologistServiceImpl = csOncologistServiceImpl;
	}

	@Autowired
	private SMSGatewayServiceImpl sMSGatewayServiceImpl;

	// ----------Save/Create (Nurse)--------------------------------------

	/***
	 * 
	 * @param requestOBJ
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public String saveCancerScreeningNurseData(JsonObject requestOBJ, String Authorization) throws Exception {
		Long nurseDataSuccessFlag = null;
		TeleconsultationRequestOBJ tcRequestOBJ = null;
		Long benVisitCode = null;
		// check if visit details data is not null
		if (requestOBJ != null && requestOBJ.has("visitDetails") && !requestOBJ.get("visitDetails").isJsonNull()) {
			// Call method to save visit details data

			CommonUtilityClass nurseUtilityClass = InputMapper.gson().fromJson(requestOBJ, CommonUtilityClass.class);
			BeneficiaryVisitDetail benVisitDetailsOBJ = InputMapper.gson().fromJson(requestOBJ.get("visitDetails"),
					BeneficiaryVisitDetail.class);

			Map<String, Long> visitIdAndCodeMap = saveBenVisitDetails(benVisitDetailsOBJ, nurseUtilityClass);

			Long benVisitID = null;

			if (visitIdAndCodeMap != null && visitIdAndCodeMap.size() > 0 && visitIdAndCodeMap.containsKey("visitID")
					&& visitIdAndCodeMap.containsKey("visitCode")) {
				benVisitID = visitIdAndCodeMap.get("visitID");
				benVisitCode = visitIdAndCodeMap.get("visitCode");

				nurseUtilityClass.setVisitCode(benVisitCode);
				nurseUtilityClass.setBenVisitID(benVisitID);
			}else {
				Map<String, String> responseMap = new HashMap<String, String>();
				responseMap.put("response", "Data already saved");
				return new Gson().toJson(responseMap);
			}

			// Getting benflowID for ben status update
			Long benFlowID = null;
			// if (requestOBJ.has("benFlowID")) {
			// benFlowID = requestOBJ.get("benFlowID").getAsLong();
			// }

			// Above if block code replaced by below line
			benFlowID = nurseUtilityClass.getBenFlowID();

			// check if visit details data saved successfully
			if (benVisitID != null && benVisitID > 0) {

				Boolean isReferedToMammogram = null;
				// check if referred to mammogram is true or false
				if (requestOBJ.has("examinationDetails") && requestOBJ.get("examinationDetails") != null
						&& !requestOBJ.get("examinationDetails").isJsonNull()) {
					JsonObject examination = requestOBJ.getAsJsonObject("examinationDetails");
					if (examination.has("breastDetails") && examination.get("breastDetails") != null
							&& !examination.get("breastDetails").isJsonNull()) {
						JsonObject breastExamination = examination.getAsJsonObject("breastDetails");
						if (breastExamination.has("referredToMammogram")
								&& breastExamination.get("referredToMammogram") != null
								&& !breastExamination.get("referredToMammogram").isJsonNull()) {
							isReferedToMammogram = breastExamination.get("referredToMammogram").getAsBoolean();
						}

					}

				}

				// check if doctor visit required ??
				Boolean docVisitReq = false;
				if (requestOBJ.has("sendToDoctorWorklist") && requestOBJ.get("sendToDoctorWorklist") != null
						&& !requestOBJ.get("sendToDoctorWorklist").isJsonNull()) {
					docVisitReq = requestOBJ.get("sendToDoctorWorklist").getAsBoolean();
				}

				// create TC request
				tcRequestOBJ = commonServiceImpl.createTcRequest(requestOBJ, nurseUtilityClass, Authorization);

				// call method to save history data
				Long historySaveSuccessFlag = saveBenHistoryDetails(requestOBJ, benVisitID, benVisitCode);
				// call method to save Examination data
				Long examinationSuccessFlag = saveBenExaminationDetails(requestOBJ, benVisitID, Authorization,
						benVisitCode, benFlowID);
				// call method to save vitals data
				Long vitalSaveSuccessFlag = saveBenVitalsDetails(requestOBJ, benVisitID, benVisitCode);

				if ((historySaveSuccessFlag != null && historySaveSuccessFlag > 0)
						&& (examinationSuccessFlag != null && examinationSuccessFlag > 0)
						&& (vitalSaveSuccessFlag != null && vitalSaveSuccessFlag > 0)) {

					// Integer i = commonNurseServiceImpl.updateBeneficiaryStatus('N',
					// getBenRegID(requestOBJ));

					nurseDataSuccessFlag = examinationSuccessFlag;

					/**
					 * We have to write new code to update ben status flow new logic
					 */
					int j = updateBenStatusFlagAfterNurseSaveSuccess(benVisitDetailsOBJ, benVisitID, benFlowID,
							isReferedToMammogram, docVisitReq, benVisitCode, nurseUtilityClass.getVanID(),
							tcRequestOBJ);

					if (j > 0)
						nurseDataSuccessFlag = examinationSuccessFlag;
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

				}

			} else {
				throw new RuntimeException("Error occurred while creating beneficiary visit");
			}

		} else {
			throw new Exception("Invalid input");
		}
		Map<String, String> responseMap = new HashMap<String, String>();
		if (benVisitCode != null) {
			responseMap.put("visitCode", benVisitCode.toString());
		}
		if (null != nurseDataSuccessFlag && nurseDataSuccessFlag > 0) {
			if (nurseDataSuccessFlag == 1)
				responseMap.put("response", "Data saved successfully");
			else if (nurseDataSuccessFlag == 2)
				responseMap.put("response", "Data saved and MAMMOGRAM order created successfully");
			else
				responseMap.put("response",
						"Data saved successfully but 'error in MAMMOGRAM order creation';please contact administrator");
		} else {
			responseMap.put("response", "Unable to save data");
		}
		return new Gson().toJson(responseMap);
	}

	// method for updating ben flow status flag for nurse
	private int updateBenStatusFlagAfterNurseSaveSuccess(BeneficiaryVisitDetail benVisitDetailsOBJ, Long benVisitID,
			Long benFlowID, Boolean isReferedToMammogram, Boolean docVisitReq, Long benVisitCode, Integer vanID,
			TeleconsultationRequestOBJ tcRequestOBJ) {
		short nurseFlag = (short) 9;
		short docFlag = (short) 0;
		short labIteration = (short) 0;
		short radiologistFlag = (short) 0;
		short oncologistFlag = (short) 0;

		short specialistFlag = (short) 0;
		Timestamp tcDate = null;
		Integer tcSpecialistUserID = null;

		if (isReferedToMammogram != null) {
			if (isReferedToMammogram == true)
				radiologistFlag = (short) 1;

		}

		if (docVisitReq == true)
			docFlag = (short) 1;
		else
			oncologistFlag = (short) 1;

		if (tcRequestOBJ != null && tcRequestOBJ.getUserID() != null && tcRequestOBJ.getAllocationDate() != null) {
			specialistFlag = (short) 1;
			tcDate = tcRequestOBJ.getAllocationDate();
			tcSpecialistUserID = tcRequestOBJ.getUserID();
		} else
			specialistFlag = (short) 0;

		int i = commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(benFlowID,
				benVisitDetailsOBJ.getBeneficiaryRegID(), benVisitID, benVisitDetailsOBJ.getVisitReason(),
				benVisitDetailsOBJ.getVisitCategory(), nurseFlag, docFlag, labIteration, radiologistFlag,
				oncologistFlag, benVisitCode, vanID, specialistFlag, tcDate, tcSpecialistUserID);

		return i;
	}

	private Long getBenRegID(JsonObject requestOBJ) throws Exception {
		BeneficiaryVisitDetail benVisitDetailsOBJ = InputMapper.gson().fromJson(requestOBJ.get("visitDetails"),
				BeneficiaryVisitDetail.class);
		return benVisitDetailsOBJ.getBeneficiaryRegID();
	}

	/**
	 * 
	 * @param requestOBJ
	 * @return success or failure flag for visitDetails data saving
	 */
	public Map<String, Long> saveBenVisitDetails(BeneficiaryVisitDetail benVisitDetailsOBJ,
			CommonUtilityClass nurseUtilityClass) throws Exception {
		Map<String, Long> visitIdAndCodeMap = new HashMap<>();
		int i=commonNurseServiceImpl.getMaxCurrentdate(benVisitDetailsOBJ.getBeneficiaryRegID(),benVisitDetailsOBJ.getVisitReason(),benVisitDetailsOBJ.getVisitCategory());
		if(i<1) {
		Long benVisitID = commonNurseServiceImpl.saveBeneficiaryVisitDetails(benVisitDetailsOBJ);

		// 11-06-2018 visit code
		Long benVisitCode = commonNurseServiceImpl.generateVisitCode(benVisitID, nurseUtilityClass.getVanID(),
				nurseUtilityClass.getSessionID());

		visitIdAndCodeMap.put("visitID", benVisitID);
		visitIdAndCodeMap.put("visitCode", benVisitCode);
		}
		return visitIdAndCodeMap;
	}

	/**
	 * 
	 * @param requestOBJ
	 * @param benVisitID
	 * @return success or failure flag for history data saving
	 * @throws Exception
	 */
	public Long saveBenHistoryDetails(JsonObject requestOBJ, Long benVisitID, Long benVisitCode) throws Exception {
		Integer benFamilyHistoryDataSavingSuccessFlag = null;
		Long benPersonalHistorySaveSuccessFlag = null;
		Long benPersonalDietHistorySaveSuccessFlag = null;
		Long benObstetricSaveSuccessFlag = null;
		Long csHistorySaveRes = null;

		if (requestOBJ != null && requestOBJ.has("historyDetails") && !requestOBJ.get("historyDetails").isJsonNull()) {
			JsonObject historyOBJ = requestOBJ.getAsJsonObject("historyDetails");

			if (historyOBJ != null && historyOBJ.has("familyHistory")
					&& !historyOBJ.get("familyHistory").isJsonNull()) {

				JsonObject familyHistoryOBJ = historyOBJ.getAsJsonObject("familyHistory");
				if (familyHistoryOBJ != null && familyHistoryOBJ.has("diseases")
						&& !familyHistoryOBJ.get("diseases").isJsonNull()) {
					BenFamilyCancerHistory[] benFamilyCancerHistoryArray = InputMapper.gson().fromJson(
							familyHistoryOBJ.get("diseases").getAsJsonArray(), BenFamilyCancerHistory[].class);
					List<BenFamilyCancerHistory> benFamilyCancerHistoryList = Arrays
							.asList(benFamilyCancerHistoryArray);
					if (benFamilyCancerHistoryArray != null && benFamilyCancerHistoryList.size() > 0) {
						for (BenFamilyCancerHistory obj : benFamilyCancerHistoryArray) {
							obj.setBenVisitID(benVisitID);
							obj.setVisitCode(benVisitCode);
						}
						benFamilyHistoryDataSavingSuccessFlag = cSNurseServiceImpl
								.saveBenFamilyCancerHistory(benFamilyCancerHistoryList);

					} else {
						// Failed to store family History..
					}
				} else {
					// familyHistoryOBJ is null or diseases are not
					// there in familyHistoryOBJ or null
					benFamilyHistoryDataSavingSuccessFlag = 1;
				}
			} else {
				// history Obj is null or family history history is null
				// or not there in history obj !!!
				benFamilyHistoryDataSavingSuccessFlag = 1;
			}
			if (historyOBJ != null && historyOBJ.has("personalHistory")
					&& !historyOBJ.get("personalHistory").isJsonNull()) {
				BenPersonalCancerHistory benPersonalCancerHistory = InputMapper.gson()
						.fromJson(historyOBJ.get("personalHistory"), BenPersonalCancerHistory.class);

				BenPersonalCancerDietHistory benPersonalCancerDietHistory = InputMapper.gson()
						.fromJson(historyOBJ.get("personalHistory"), BenPersonalCancerDietHistory.class);

				if (benPersonalCancerHistory != null && benPersonalCancerDietHistory != null) {

					benPersonalCancerHistory.setBenVisitID(benVisitID);
					benPersonalCancerHistory.setVisitCode(benVisitCode);
					benPersonalCancerDietHistory.setBenVisitID(benVisitID);
					benPersonalCancerDietHistory.setVisitCode(benVisitCode);

					benPersonalHistorySaveSuccessFlag = cSNurseServiceImpl
							.saveBenPersonalCancerHistory(benPersonalCancerHistory);
					benPersonalDietHistorySaveSuccessFlag = cSNurseServiceImpl
							.saveBenPersonalCancerDietHistory(benPersonalCancerDietHistory);
				} else {
					// Failed to store Personal History..
				}
			} else {
				// ben personal history data not there !!!
				benPersonalHistorySaveSuccessFlag = new Long(1);
				benPersonalDietHistorySaveSuccessFlag = new Long(1);
			}
			if (historyOBJ != null && historyOBJ.has("pastObstetricHistory")
					&& !historyOBJ.get("pastObstetricHistory").isJsonNull()) {
				BenObstetricCancerHistory benObstetricCancerHistory = InputMapper.gson()
						.fromJson(historyOBJ.get("pastObstetricHistory"), BenObstetricCancerHistory.class);

				benObstetricCancerHistory.setBenVisitID(benVisitID);
				benObstetricCancerHistory.setVisitCode(benVisitCode);
				benObstetricSaveSuccessFlag = cSNurseServiceImpl
						.saveBenObstetricCancerHistory(benObstetricCancerHistory);
			} else {
				// ben obstetric history data not there !!!
				benObstetricSaveSuccessFlag = new Long(1);
			}

			if (benFamilyHistoryDataSavingSuccessFlag > 0
					&& (null != benPersonalHistorySaveSuccessFlag && benPersonalHistorySaveSuccessFlag > 0)
					&& (null != benPersonalDietHistorySaveSuccessFlag && benPersonalDietHistorySaveSuccessFlag > 0)
					&& (null != benObstetricSaveSuccessFlag && benObstetricSaveSuccessFlag > 0)) {
				csHistorySaveRes = benPersonalHistorySaveSuccessFlag;
			}

		} else {
			// History Object is not there in request object !!!
			csHistorySaveRes = new Long(1);
		}

		return csHistorySaveRes;
	}

	public Long saveBenFamilyHistoryDetails() {
		return null;
	}

	/**
	 * 
	 * @param requestOBJ
	 * @param benVisitID
	 * @return success or failure flag for vitals data saving
	 * @throws Exception
	 */
	public Long saveBenVitalsDetails(JsonObject requestOBJ, Long benVisitID, Long benVisitCode) throws Exception {
		Long benVitalSaveSuccessFlag = null;
		if (requestOBJ != null && requestOBJ.has("vitalsDetails") && !requestOBJ.get("vitalsDetails").isJsonNull()) {
			BenCancerVitalDetail benCancerVitalDetail = InputMapper.gson().fromJson(requestOBJ.get("vitalsDetails"),
					BenCancerVitalDetail.class);
			if (benCancerVitalDetail != null) {
				benCancerVitalDetail.setBenVisitID(benVisitID);
				benCancerVitalDetail.setVisitCode(benVisitCode);
				benVitalSaveSuccessFlag = cSNurseServiceImpl.saveBenVitalDetail(benCancerVitalDetail);
			}
		} else {
			// ben vitals data is not there !!!
			benVitalSaveSuccessFlag = new Long(1);
		}
		return benVitalSaveSuccessFlag;
	}

	/// -------End of Save/Create (Nurse)-----------------------------------

	// -------Update (Nurse data from Doctor screen)----------------------
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int UpdateCSHistoryNurseData(JsonObject jsnOBJ) throws Exception {

		int familyCURes = 0;
		int pastObstetricCURes = 0;
		int personalCURes = 0;
		int personalDietURes = 0;
		int historyUpdateRes = 0;

		if (null != jsnOBJ && jsnOBJ.has("familyHistory") && !jsnOBJ.get("familyHistory").isJsonNull()) {
			BenFamilyCancerHistory[] benFamilyCancerHistoryArray = InputMapper.gson()
					.fromJson(jsnOBJ.get("familyHistory"), BenFamilyCancerHistory[].class);
			List<BenFamilyCancerHistory> benFamilyCancerHistoryList = Arrays.asList(benFamilyCancerHistoryArray);

			if (benFamilyCancerHistoryList != null && benFamilyCancerHistoryList.size() > 0) {
				familyCURes = cSNurseServiceImpl.updateBeneficiaryFamilyCancerHistory(benFamilyCancerHistoryList);
			} else {
				familyCURes = 1;
			}
		} else {
			familyCURes = 1;
		}

		if (null != jsnOBJ && jsnOBJ.has("pastObstetricHistory") && !jsnOBJ.get("pastObstetricHistory").isJsonNull()) {

			BenObstetricCancerHistory benObstetricCancerHistory = InputMapper.gson()
					.fromJson(jsnOBJ.get("pastObstetricHistory"), BenObstetricCancerHistory.class);
			pastObstetricCURes = cSNurseServiceImpl.updateBenObstetricCancerHistory(benObstetricCancerHistory);
		} else {
			pastObstetricCURes = 1;
		}

		if (null != jsnOBJ && jsnOBJ.has("personalHistory") && !jsnOBJ.get("personalHistory").isJsonNull()) {

			BenPersonalCancerHistory benPersonalCancerHistory = InputMapper.gson()
					.fromJson(jsnOBJ.get("personalHistory"), BenPersonalCancerHistory.class);

			BenPersonalCancerDietHistory benPersonalCancerDietHistory = InputMapper.gson()
					.fromJson(jsnOBJ.get("personalHistory"), BenPersonalCancerDietHistory.class);

			personalCURes = cSNurseServiceImpl.updateBenPersonalCancerHistory(benPersonalCancerHistory);

			personalDietURes = cSNurseServiceImpl.updateBenPersonalCancerDietHistory(benPersonalCancerDietHistory);

		} else {
			personalCURes = 1;
			personalDietURes = 1;
		}

		if (familyCURes > 0 && pastObstetricCURes > 0 && personalCURes > 0 && personalDietURes > 0) {
			historyUpdateRes = 1;
		} else {
			// TODO Rollback the succeeded transactions
		}

		return historyUpdateRes;
	}

	@Transactional(rollbackFor = Exception.class)
	public int updateBenExaminationDetail(JsonObject jsnOBJ) throws Exception {
		int examinationUpdateRes = 0;
		int signSympSuccessFlag = 0;
		int lymphNodeSuccessFlag = 0;
		int oralDetailsSuccessFlag = 0;
		int breastExmnSuccessFlag = 0;
		int abdominalExmnSuccessFlag = 0;
		int gynecologicalExmnSuccessFlag = 0;
		int imgCoordinatesSuccessFlag = 0;

		if (null != jsnOBJ && jsnOBJ.has("signsDetails") && !jsnOBJ.get("signsDetails").isJsonNull()) {

			JsonObject signsDetailsOBJ = jsnOBJ.getAsJsonObject("signsDetails");

			WrapperCancerSymptoms wrapperCancerSymptoms = InputMapper.gson().fromJson(signsDetailsOBJ,
					WrapperCancerSymptoms.class);

			if (null != wrapperCancerSymptoms.getCancerSignAndSymptoms()) {
				int ID = cSNurseServiceImpl
						.updateSignAndSymptomsExaminationDetails(wrapperCancerSymptoms.getCancerSignAndSymptoms());
				if (ID > 0) {
					// SignAndSymptoms Details stored successfully..
					signSympSuccessFlag = ID;
				} else {
					//
				}
			} else {
				// signsDetails not available..
				signSympSuccessFlag = 1;
			}

			if (null != wrapperCancerSymptoms.getCancerLymphNodeDetails()) {
				int ID = cSNurseServiceImpl.updateLymphNodeExaminationDetails(wrapperCancerSymptoms);
				if (ID > 0) {
					// LymphNode Details stored successfully..
					lymphNodeSuccessFlag = ID;
				} else {

				} // Failed to store LymphNode Details..
			} else {
				// lymphNode not available..
				lymphNodeSuccessFlag = 1;
			}

		} else {
			signSympSuccessFlag = 1;
			lymphNodeSuccessFlag = 1;
		}

		if (jsnOBJ != null && jsnOBJ.has("oralDetails") && !jsnOBJ.get("oralDetails").isJsonNull()) {

			CancerOralExamination cancerOralExamination = InputMapper.gson().fromJson(jsnOBJ.get("oralDetails"),
					CancerOralExamination.class);
			int ID = cSNurseServiceImpl.updateCancerOralDetails(cancerOralExamination);
			if (ID > 0) {
				// oralDetails stored successfully...
				oralDetailsSuccessFlag = ID;
			} else {
				// Failed to store oralDetails..
			}

		} else {
			// oralDetails not available..
			oralDetailsSuccessFlag = 1;
		}

		if (jsnOBJ != null && jsnOBJ.has("breastDetails") && !jsnOBJ.get("breastDetails").isJsonNull()) {

			CancerBreastExamination cancerBreastExamination = InputMapper.gson().fromJson(jsnOBJ.get("breastDetails"),
					CancerBreastExamination.class);
			int ID = cSNurseServiceImpl.updateCancerBreastDetails(cancerBreastExamination);
			if (ID > 0) {
				// breastDetails stored successfully...
				breastExmnSuccessFlag = ID;
			} else {
				// Failed to store breastDetails..
			}

		} else {
			// breastDetails not available..
			breastExmnSuccessFlag = 1;
		}

		if (jsnOBJ != null && jsnOBJ.has("abdominalDetails") && !jsnOBJ.get("abdominalDetails").isJsonNull()) {

			CancerAbdominalExamination cancerAbdominalExamination = InputMapper.gson()
					.fromJson(jsnOBJ.get("abdominalDetails"), CancerAbdominalExamination.class);
			int ID = cSNurseServiceImpl.updateCancerAbdominalExaminationDetails(cancerAbdominalExamination);

			if (ID > 0) {
				// abdominalDetails stored successfully...
				abdominalExmnSuccessFlag = ID;
			} else {
				// Failed to store abdominalDetails..
			}

		} else {
			// abdominalDetails not available..
			abdominalExmnSuccessFlag = 1;
		}

		if (jsnOBJ != null && jsnOBJ.has("gynecologicalDetails") && !jsnOBJ.get("gynecologicalDetails").isJsonNull()) {

			CancerGynecologicalExamination cancerGynecologicalExamination = InputMapper.gson()
					.fromJson(jsnOBJ.get("gynecologicalDetails"), CancerGynecologicalExamination.class);

			int ID = cSNurseServiceImpl.updateCancerGynecologicalExaminationDetails(cancerGynecologicalExamination);
			if (ID > 0) {
				// gynecologicalDetails stored successfully...
				gynecologicalExmnSuccessFlag = ID;
			} else {
				// Failed to store gynecologicalDetails..
			}

		} else {
			// gynecologicalDetails not available..
			gynecologicalExmnSuccessFlag = 1;
		}

		if (jsnOBJ != null && jsnOBJ.has("imageCoordinates") && !jsnOBJ.get("imageCoordinates").isJsonNull()) {

			WrapperCancerExamImgAnotasn[] wrapperCancerExamImgAnotasn = InputMapper.gson()
					.fromJson(jsnOBJ.get("imageCoordinates"), WrapperCancerExamImgAnotasn[].class);

			Long visitCode = null;
			if (jsnOBJ.has("visitCode") && !jsnOBJ.get("visitCode").isJsonNull()) {
				visitCode = jsnOBJ.get("visitCode").getAsLong();
			}
			List<WrapperCancerExamImgAnotasn> wrapperCancerExamImgAnotasnList = Arrays
					.asList(wrapperCancerExamImgAnotasn);
			if (null != wrapperCancerExamImgAnotasnList) {
				int r = cSNurseServiceImpl.updateCancerExamImgAnotasnDetails(cSNurseServiceImpl
						.getCancerExaminationImageAnnotationList(wrapperCancerExamImgAnotasnList, visitCode));
				if (r > 0) {
					// imageCoordinates stored successfully...
					imgCoordinatesSuccessFlag = r;
				}
			} else {
				imgCoordinatesSuccessFlag = 1;
			}
		} else {
			// imageCoordinates not available..
			imgCoordinatesSuccessFlag = 1;
		}

		if (signSympSuccessFlag > 0 && lymphNodeSuccessFlag > 0 && oralDetailsSuccessFlag > 0
				&& breastExmnSuccessFlag > 0 && abdominalExmnSuccessFlag > 0 && gynecologicalExmnSuccessFlag > 0
				&& imgCoordinatesSuccessFlag > 0) {
			examinationUpdateRes = 1;
		} else {
			// TODO Rollback the succeeded transactions
		}

		return examinationUpdateRes;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updateBenVitalDetail(BenCancerVitalDetail benCancerVitalDetail) {
		return cSNurseServiceImpl.updateBenVitalDetail(benCancerVitalDetail);
	}

	/// ------- End of Update (Nurse data from Doctor screen)-----------

	// ------- Fetch (Nurse data to Doctor screen) ----------------
	public String getBenDataFrmNurseToDocVisitDetailsScreen(Long benRegID, Long visitCode) {
		Map<String, Object> resMap = new HashMap<>();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

		BeneficiaryVisitDetail benVisitDetailsOBJ = commonNurseServiceImpl.getCSVisitDetails(benRegID, visitCode);

		if (null != benVisitDetailsOBJ) {

			resMap.put("benVisitDetails", benVisitDetailsOBJ);
		}

		return gson.toJson(resMap);
	}

	public String getBenDataFrmNurseToDocHistoryScreen(Long benRegID, Long visitCode) {
		Map<String, Object> resMap = new HashMap<>();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.serializeNulls();
		Gson gson = gsonBuilder.create();

		resMap.put("benFamilyHistory", cSNurseServiceImpl.getBenFamilyHisData(benRegID, visitCode));

		resMap.put("benObstetricHistory", cSNurseServiceImpl.getBenObstetricDetailsData(benRegID, visitCode));

		resMap.put("benPersonalHistory", cSNurseServiceImpl.getBenPersonalCancerHistoryData(benRegID, visitCode));

		resMap.put("benPersonalDietHistory",
				cSNurseServiceImpl.getBenPersonalCancerDietHistoryData(benRegID, visitCode));

		return gson.toJson(resMap);
	}

	public String getBenDataFrmNurseToDocVitalScreen(Long benRegID, Long visitCode) {
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("benVitalDetails", cSNurseServiceImpl.getBenCancerVitalDetailData(benRegID, visitCode));
		resMap.put("GraphData", commonNurseServiceImpl.getGraphicalTrendData(benRegID, "cancer screening"));
		return new Gson().toJson(resMap);
	}

	public String getBenDataFrmNurseToDocExaminationScreen(Long benRegID, Long visitCode) {
		Map<String, Object> resMap = new HashMap<>();
		// TODO

		resMap.put("abdominalExamination",
				cSNurseServiceImpl.getBenCancerAbdominalExaminationData(benRegID, visitCode));

		resMap.put("breastExamination", cSNurseServiceImpl.getBenCancerBreastExaminationData(benRegID, visitCode));

		resMap.put("gynecologicalExamination",
				cSNurseServiceImpl.getBenCancerGynecologicalExaminationData(benRegID, visitCode));

		resMap.put("signsAndSymptoms", cSNurseServiceImpl.getBenCancerSignAndSymptomsData(benRegID, visitCode));

		resMap.put("BenCancerLymphNodeDetails",
				cSNurseServiceImpl.getBenCancerLymphNodeDetailsData(benRegID, visitCode));

		resMap.put("oralExamination", cSNurseServiceImpl.getBenCancerOralExaminationData(benRegID, visitCode));

		resMap.put("imageCoordinates",
				cSNurseServiceImpl.getCancerExaminationImageAnnotationCasesheet(benRegID, visitCode));

		return new Gson().toJson(resMap);
	}

	/// -------End of Fetch (Nurse data to Doctor screen) -------

	// -------Create/save (Doctor)---------------------------
	@Transactional(rollbackFor = Exception.class)
	public Long saveCancerScreeningDoctorData(JsonObject requestOBJ, String Authorization) throws Exception {
		Long docDataSuccessFlag = null;
		Long tcRequestStatusFlag = null;

		if (requestOBJ != null && requestOBJ.has("diagnosis") && !requestOBJ.get("diagnosis").isJsonNull()) {

			TeleconsultationRequestOBJ tcRequestOBJ = null;
			TcSpecialistSlotBookingRequestOBJ tcSpecialistSlotBookingRequestOBJ = null;

			CommonUtilityClass commonUtilityClass = InputMapper.gson().fromJson(requestOBJ.get("diagnosis"),
					CommonUtilityClass.class);

			if (commonUtilityClass != null && commonUtilityClass.getServiceID() != null
					&& commonUtilityClass.getServiceID() == 4 && requestOBJ != null && requestOBJ.has("tcRequest")
					&& requestOBJ.get("tcRequest") != null) {

				tcRequestOBJ = InputMapper.gson().fromJson(requestOBJ.get("tcRequest"),
						TeleconsultationRequestOBJ.class);
				// create TC request
				if (tcRequestOBJ != null && tcRequestOBJ.getUserID() != null && tcRequestOBJ.getUserID() > 0
						&& tcRequestOBJ.getAllocationDate() != null) {

					tcRequestOBJ.setAllocationDate(Utility.combineDateAndTimeToDateTime(
							tcRequestOBJ.getAllocationDate().toString(), tcRequestOBJ.getFromTime()));

					// tc request model
					TCRequestModel tRequestModel = InputMapper.gson().fromJson(requestOBJ.get("diagnosis"),
							TCRequestModel.class);

					tRequestModel.setUserID(tcRequestOBJ.getUserID());
					tRequestModel.setSpecializationID(tcRequestOBJ.getSpecializationID());
					tRequestModel.setRequestDate(tcRequestOBJ.getAllocationDate());
					tRequestModel
							.setDuration_minute(Utility.timeDiff(tcRequestOBJ.getFromTime(), tcRequestOBJ.getToTime()));
					tRequestModel.setVanID(commonUtilityClass.getVanID());

					// tc speciaist slot booking model
					tcSpecialistSlotBookingRequestOBJ = new TcSpecialistSlotBookingRequestOBJ();

					tcSpecialistSlotBookingRequestOBJ.setUserID(tRequestModel.getUserID());
					tcSpecialistSlotBookingRequestOBJ.setDate(tRequestModel.getRequestDate());
					tcSpecialistSlotBookingRequestOBJ.setFromTime(tcRequestOBJ.getFromTime());
					tcSpecialistSlotBookingRequestOBJ.setToTime(tcRequestOBJ.getToTime());
					tcSpecialistSlotBookingRequestOBJ.setCreatedBy(commonUtilityClass.getCreatedBy());
					tcSpecialistSlotBookingRequestOBJ.setModifiedBy(commonUtilityClass.getCreatedBy());

					int j = commonDoctorServiceImpl.callTmForSpecialistSlotBook(tcSpecialistSlotBookingRequestOBJ,
							Authorization);

					if (j > 0) {
						tcRequestStatusFlag = teleConsultationServiceImpl.createTCRequest(tRequestModel);
						tcRequestOBJ.setTmRequestID(tcRequestStatusFlag);
					} else
						throw new RuntimeException("Error while booking slot.");
				}
			}

			Long diagnosisSuccessFlag = saveBenDiagnosisDetails(requestOBJ);

			if (diagnosisSuccessFlag != null && diagnosisSuccessFlag > 0) {

				Long tmpBenFlowID = null;
				Long tmpbeneficiaryRegID = null;
				Long tmpBeneficiaryID = null;
				Long tmpBenVisitID = null;
				short tcSpecialistFlag = (short) 0;
				int tcUserID = 0;
				Timestamp tcDate = null;

				tmpBenFlowID = commonUtilityClass.getBenFlowID();
				tmpbeneficiaryRegID = commonUtilityClass.getBeneficiaryRegID();
				tmpBeneficiaryID = commonUtilityClass.getBeneficiaryID();
				tmpBenVisitID = commonUtilityClass.getBenVisitID();

				short docFlag = (short) 1;
				short pharmaFalg = (short) 0;
				short oncologistFlag = (short) 1;

				if (commonUtilityClass != null && commonUtilityClass.getIsSpecialist() != null
						&& commonUtilityClass.getIsSpecialist() == true) {
					tcSpecialistFlag = (short) 9;
				} else {
					docFlag = (short) 9;
				}

				if (tcRequestOBJ != null && tcRequestOBJ.getUserID() != null && tcRequestOBJ.getUserID() > 0
						&& tcRequestOBJ.getAllocationDate() != null) {
					tcSpecialistFlag = (short) 1;
					tcUserID = tcRequestOBJ.getUserID();
					tcDate = tcRequestOBJ.getAllocationDate();

				}

				int l1 = 0;
				int l2 = 0;

				if (commonUtilityClass != null && commonUtilityClass.getIsSpecialist() != null
						&& commonUtilityClass.getIsSpecialist() == true) {
					l1 = commonBenStatusFlowServiceImpl.updateBenFlowAfterDocDataFromSpecialist(tmpBenFlowID,
							tmpbeneficiaryRegID, tmpBeneficiaryID, tmpBenVisitID, docFlag, pharmaFalg, oncologistFlag,
							tcSpecialistFlag, (short) 0);

					if (tcSpecialistFlag == 9) {
						int l = tCRequestModelRepo.updateStatusIfConsultationCompleted(
								commonUtilityClass.getBeneficiaryRegID(), commonUtilityClass.getVisitCode(), "D");
					}
				} else {
					l2 = commonBenStatusFlowServiceImpl.updateBenFlowAfterDocData(tmpBenFlowID, tmpbeneficiaryRegID,
							tmpBeneficiaryID, tmpBenVisitID, docFlag, pharmaFalg, oncologistFlag, tcSpecialistFlag,
							tcUserID, tcDate, (short) 0);
				}

				if (l1 > 0 || l2 > 0)
					docDataSuccessFlag = diagnosisSuccessFlag;
				else
					throw new RuntimeException("Error occurred while saving data. Beneficiary status update failed");

				if (docDataSuccessFlag > 0 && tcRequestOBJ != null && tcRequestOBJ.getWalkIn() == false) {
					int k = sMSGatewayServiceImpl.smsSenderGateway("schedule", commonUtilityClass.getBeneficiaryRegID(),
							tcRequestOBJ.getSpecializationID(), tcRequestOBJ.getTmRequestID(), null,
							commonUtilityClass.getCreatedBy(),
							tcRequestOBJ.getAllocationDate() != null ? String.valueOf(tcRequestOBJ.getAllocationDate())
									: "",
							null, Authorization);
				}

			}
		} else {
			// NO input available..
		}
		return docDataSuccessFlag;

	}

	private Long getBenVisitID(JsonObject requestOBJ) throws Exception {
		Long benVisitID = null;
		if (requestOBJ != null && requestOBJ.has("diagnosis") && !requestOBJ.get("diagnosis").isJsonNull()) {

			JsonObject tmpOBJ = requestOBJ.get("diagnosis").getAsJsonObject();
			if (tmpOBJ != null && tmpOBJ.has("benVisitID") && !tmpOBJ.get("benVisitID").isJsonNull()) {
				benVisitID = tmpOBJ.get("benVisitID").getAsLong();
			}
		}

		return benVisitID;
	}

	/**
	 * 
	 * @param requestOBJ
	 * @return success or failure flag for Examination data saving
	 */

	public Long saveBenExaminationDetails(JsonObject requestOBJ, Long benVisitID, String Authorization,
			Long benVisitCode, Long benFlowID) throws Exception {
		Long signSympSuccessFlag = null;
		Long lymphNodeSuccessFlag = null;
		Long oralDetailsSuccessFlag = null;
		Long breastExmnSuccessFlag = null;
		Long abdominalExmnSuccessFlag = null;
		Long gynecologicalExmnSuccessFlag = null;
		Long imgCoordinatesSuccessFlag = null;

		Long exmnSuccessFlag = null;

		if (requestOBJ != null && requestOBJ.has("examinationDetails")
				&& !requestOBJ.get("examinationDetails").isJsonNull()) {
			JsonObject examinationOBJ = requestOBJ.getAsJsonObject("examinationDetails");
			if (examinationOBJ != null && examinationOBJ.has("signsDetails")
					&& !examinationOBJ.get("signsDetails").isJsonNull()) {

				JsonObject signsDetailsOBJ = examinationOBJ.getAsJsonObject("signsDetails");

				WrapperCancerSymptoms wrapperCancerSymptoms = InputMapper.gson().fromJson(signsDetailsOBJ,
						WrapperCancerSymptoms.class);

				if (null != wrapperCancerSymptoms.getCancerSignAndSymptoms()) {
					Long ID = cSNurseServiceImpl.saveCancerSignAndSymptomsData(
							wrapperCancerSymptoms.getCancerSignAndSymptoms(), benVisitID, benVisitCode);
					if (ID > 0 && ID != null) {
						// SignAndSymptoms Details stored successfully..
						signSympSuccessFlag = ID;
					} else {
						// Failed to store signsDetails..
					}
				} else {
					// signsDetails not available..
					signSympSuccessFlag = new Long(1);
				}

				if (null != wrapperCancerSymptoms.getCancerLymphNodeDetails()
						&& wrapperCancerSymptoms.getCancerLymphNodeDetails().size() > 0) {
					Long ID = cSNurseServiceImpl.saveLymphNodeDetails(wrapperCancerSymptoms.getCancerLymphNodeDetails(),
							benVisitID, benVisitCode);
					if (null != ID && ID > 0) {
						// LymphNode Details stored successfully..
						lymphNodeSuccessFlag = ID;
					} else {

					} // Failed to store LymphNode Details..
				} else {
					// lymphNode not available..
					lymphNodeSuccessFlag = new Long(1);
				}

			} else {
				signSympSuccessFlag = new Long(1);
				lymphNodeSuccessFlag = new Long(1);
			}

			if (examinationOBJ != null && examinationOBJ.has("oralDetails")
					&& !examinationOBJ.get("oralDetails").isJsonNull()) {

				CancerOralExamination cancerOralExamination = InputMapper.gson()
						.fromJson(examinationOBJ.get("oralDetails"), CancerOralExamination.class);
				cancerOralExamination.setBenVisitID(benVisitID);
				cancerOralExamination.setVisitCode(benVisitCode);
				Long ID = cSNurseServiceImpl.saveCancerOralExaminationData(cancerOralExamination);
				if (ID != null && ID > 0) {
					// oralDetails stored successfully...
					oralDetailsSuccessFlag = ID;
				} else {
					// Failed to store oralDetails..
				}

			} else {
				// oralDetails not available..
				oralDetailsSuccessFlag = new Long(1);
			}

			if (examinationOBJ != null && examinationOBJ.has("breastDetails")
					&& !examinationOBJ.get("breastDetails").isJsonNull()) {

				CancerBreastExamination cancerBreastExamination = InputMapper.gson()
						.fromJson(examinationOBJ.get("breastDetails"), CancerBreastExamination.class);
				cancerBreastExamination.setBenVisitID(benVisitID);
				cancerBreastExamination.setVisitCode(benVisitCode);
				Long ID = cSNurseServiceImpl.saveCancerBreastExaminationData(cancerBreastExamination);
				if (ID != null && ID > 0) {
					// breastDetails stored successfully...

					// New code for care stream... 16-03-2018
					if (cancerBreastExamination.getReferredToMammogram() != null
							&& cancerBreastExamination.getReferredToMammogram() == true) {

						int r = createCareStreamOrder(cancerBreastExamination.getBeneficiaryRegID(),
								cancerBreastExamination.getBenVisitID(), Authorization, benFlowID);
						if (r > 0) {
							breastExmnSuccessFlag = Long.valueOf(2);
						} else {
							breastExmnSuccessFlag = Long.valueOf(3);
						}
					} else {
						breastExmnSuccessFlag = Long.valueOf(1);
					}
					// End of New code for care stream... 16-03-2018
				} else {
					// Failed to store breastDetails..
				}

			} else {
				// breastDetails not available..
				breastExmnSuccessFlag = new Long(1);
			}

			if (examinationOBJ != null && examinationOBJ.has("abdominalDetails")
					&& !examinationOBJ.get("abdominalDetails").isJsonNull()) {

				CancerAbdominalExamination cancerAbdominalExamination = InputMapper.gson()
						.fromJson(examinationOBJ.get("abdominalDetails"), CancerAbdominalExamination.class);
				cancerAbdominalExamination.setBenVisitID(benVisitID);
				cancerAbdominalExamination.setVisitCode(benVisitCode);
				Long ID = cSNurseServiceImpl.saveCancerAbdominalExaminationData(cancerAbdominalExamination);
				if (ID != null && ID > 0) {
					// abdominalDetails stored successfully...
					abdominalExmnSuccessFlag = ID;
				} else {
					// Failed to store abdominalDetails..
				}

			} else {
				// abdominalDetails not available..
				abdominalExmnSuccessFlag = new Long(1);
			}

			if (examinationOBJ != null && examinationOBJ.has("gynecologicalDetails")
					&& !examinationOBJ.get("gynecologicalDetails").isJsonNull()) {

				CancerGynecologicalExamination cancerGynecologicalExamination = InputMapper.gson()
						.fromJson(examinationOBJ.get("gynecologicalDetails"), CancerGynecologicalExamination.class);
				cancerGynecologicalExamination.setBenVisitID(benVisitID);
				cancerGynecologicalExamination.setVisitCode(benVisitCode);
				Long ID = cSNurseServiceImpl.saveCancerGynecologicalExaminationData(cancerGynecologicalExamination);
				if (ID != null && ID > 0) {
					// gynecologicalDetails stored successfully...
					gynecologicalExmnSuccessFlag = ID;
				} else {
					// Failed to store gynecologicalDetails..
				}

			} else {
				// gynecologicalDetails not available..
				gynecologicalExmnSuccessFlag = new Long(1);
			}

			if (examinationOBJ != null && examinationOBJ.has("imageCoordinates")
					&& !examinationOBJ.get("imageCoordinates").isJsonNull()) {

				WrapperCancerExamImgAnotasn[] wrapperCancerExamImgAnotasn = InputMapper.gson()
						.fromJson(examinationOBJ.get("imageCoordinates"), WrapperCancerExamImgAnotasn[].class);

				List<WrapperCancerExamImgAnotasn> wrapperCancerExamImgAnotasnList = Arrays
						.asList(wrapperCancerExamImgAnotasn);
				if (null != wrapperCancerExamImgAnotasnList && wrapperCancerExamImgAnotasnList.size() > 0) {
					Long r = cSNurseServiceImpl.saveDocExaminationImageAnnotation(wrapperCancerExamImgAnotasnList,
							benVisitID, benVisitCode);
					if (r != null && r > 0) {
						// imageCoordinates stored successfully...
						imgCoordinatesSuccessFlag = r;
					} else {
						// Failed to store image coordinates..
					}
				} else {
					imgCoordinatesSuccessFlag = new Long(1);
				}
			} else {
				// imageCoordinates not available..
				imgCoordinatesSuccessFlag = new Long(1);
			}

			if (null != signSympSuccessFlag && signSympSuccessFlag > 0 && null != lymphNodeSuccessFlag
					&& lymphNodeSuccessFlag > 0 && null != oralDetailsSuccessFlag && oralDetailsSuccessFlag > 0
					&& null != breastExmnSuccessFlag && breastExmnSuccessFlag > 0 && null != abdominalExmnSuccessFlag
					&& abdominalExmnSuccessFlag > 0 && null != gynecologicalExmnSuccessFlag
					&& gynecologicalExmnSuccessFlag > 0 && null != imgCoordinatesSuccessFlag
					&& imgCoordinatesSuccessFlag > 0) {

				exmnSuccessFlag = breastExmnSuccessFlag;

			} else {
				// TODO Rollback
			}

		} else {
			exmnSuccessFlag = new Long(1);
		}

		return exmnSuccessFlag;
	}

	/**
	 * 
	 * @param requestOBJ
	 * @return success or failure flag for Diagnosis data saving
	 */
	public Long saveBenDiagnosisDetails(JsonObject requestOBJ) throws Exception {

		Long diagnosisSuccessFlag = null;
		if (requestOBJ != null && requestOBJ.has("diagnosis") && !requestOBJ.get("diagnosis").isJsonNull()) {
			CancerDiagnosis cancerDiagnosis = InputMapper.gson().fromJson(requestOBJ.get("diagnosis"),
					CancerDiagnosis.class);
			Long ID = cSDoctorServiceImpl.saveCancerDiagnosisData(cancerDiagnosis);
			if (ID != null && ID > 0) {
				// diagnosis details stored successfully...
				diagnosisSuccessFlag = ID;
			} else {
				// Failed to store diagnosis details..
			}
		} else {
			// diagnosis details not available..
			diagnosisSuccessFlag = new Long(1);
		}

		return diagnosisSuccessFlag;
	}

	/// ------- End of Create/save (Doctor)---------------------------

	// -------Fetch Case-sheet data ----------------------------------
	@Deprecated
	public String getCancerCasesheetData(JSONObject obj, String Authorization) throws Exception {
		String caseSheetData = null;
		if (obj.length() > 1) {
			Long benRegID = null;
			Long benVisitID = null;
			Long benFlowID = null;
			Long visitCode = null;
			try {
				benRegID = obj.getLong("benRegID");
				benVisitID = obj.getLong("benVisitID");
				benFlowID = obj.getLong("benFlowID");
				visitCode = obj.getLong("visitCode");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}

			caseSheetData = getBenDataForCaseSheet(benFlowID, benRegID, visitCode, Authorization);

		} else {

		}

		return caseSheetData;
	}

	@Deprecated
	public String getBenDataForCaseSheet(Long benFlowID, Long benRegID, Long visitCode, String Authorization)
			throws Exception {

		Map<String, Object> caseSheetData = cSNurseServiceImpl.getBenNurseDataForCaseSheet(benRegID, visitCode);
		caseSheetData.putAll(cSDoctorServiceImpl.getBenDoctorEnteredDataForCaseSheet(benRegID, visitCode));

		caseSheetData.put("BeneficiaryData", getBenDetails(benFlowID, benRegID));
		caseSheetData.put("ImageAnnotatedData",
				cSNurseServiceImpl.getCancerExaminationImageAnnotationCasesheet(benRegID, visitCode));

		return new Gson().toJson(caseSheetData);
	}

	public String getBenNurseDataForCaseSheet(Long benRegID, Long visitCode) {
		Map<String, Object> resMap = new HashMap<>();

		resMap.put("benVisitDetail", cSNurseServiceImpl.getBeneficiaryVisitDetails(benRegID, visitCode));

		resMap.put("familyDiseaseHistory", cSNurseServiceImpl.getBenFamilyHisData(benRegID, visitCode));

		resMap.put("patientObstetricHistory", cSNurseServiceImpl.getBenObstetricDetailsData(benRegID, visitCode));

		resMap.put("patientPersonalHistory", cSNurseServiceImpl.getBenPersonalCancerHistoryData(benRegID, visitCode));

		resMap.put("benPersonalDietHistory",
				cSNurseServiceImpl.getBenPersonalCancerDietHistoryData(benRegID, visitCode));

		resMap.put("currentVitals", cSNurseServiceImpl.getBenCancerVitalDetailData(benRegID, visitCode));

		resMap.put("abdominalExamination",
				cSNurseServiceImpl.getBenCancerAbdominalExaminationData(benRegID, visitCode));

		resMap.put("breastExamination", cSNurseServiceImpl.getBenCancerBreastExaminationData(benRegID, visitCode));

		resMap.put("gynecologicalExamination",
				cSNurseServiceImpl.getBenCancerGynecologicalExaminationData(benRegID, visitCode));

		resMap.put("signsAndSymptoms", cSNurseServiceImpl.getBenCancerSignAndSymptomsData(benRegID, visitCode));

		resMap.put("BenCancerLymphNodeDetails",
				cSNurseServiceImpl.getBenCancerLymphNodeDetailsData(benRegID, visitCode));

		resMap.put("oralExamination", cSNurseServiceImpl.getBenCancerOralExaminationData(benRegID, visitCode));

		return new Gson().toJson(resMap);
	}

	// Same method we have copied to commonServices, in future we can remove
	// this from here..
	private BeneficiaryFlowStatus getBenDetails(Long benFlowID, Long benRegID) {
		BeneficiaryFlowStatus obj = beneficiaryFlowStatusRepo.getBenDetailsForLeftSidePanel(benFlowID);
		// BeneficiaryFlowStatus obj =
		// BeneficiaryFlowStatus.getBeneficiaryFlowStatusForLeftPanel(tmpOBJ);
		return obj;
	}

	/// -------End of Fetch Case-sheet data ----------------------------------

	// -------Fetch beneficiary all past family history data------------------
	public String getBenFamilyHistoryData(Long beneficiaryRegID) {
		return cSNurseServiceImpl.getBenCancerFamilyHistory(beneficiaryRegID);
	}
	/// ------- End of Fetch beneficiary all past family history data----------

	// -------Fetch beneficiary all past personal history data------------------
	public String getBenPersonalHistoryData(Long beneficiaryRegID) {
		return cSNurseServiceImpl.getBenCancerPersonalHistory(beneficiaryRegID);
	}
	/// ------- End of Fetch beneficiary all past personal history data--------

	// -------Fetch beneficiary all past personal diet history data-----------
	public String getBenPersonalDietHistoryData(Long beneficiaryRegID) {
		return cSNurseServiceImpl.getBenCancerPersonalDietHistory(beneficiaryRegID);
	}
	/// ------- End of Fetch beneficiary all past personal diet history data--

	// -------Fetch beneficiary all past obstetric history data---------------
	public String getBenObstetricHistoryData(Long beneficiaryRegID) {
		return cSNurseServiceImpl.getBenCancerObstetricHistory(beneficiaryRegID);
	}
	/// ------- End of Fetch beneficiary all past obstetric history data------

	@Override
	public int updateCancerDiagnosisDetailsByOncologist(CancerDiagnosis cancerDiagnosis) {
		return csOncologistServiceImpl.updateCancerDiagnosisDetailsByOncologist(cancerDiagnosis);

	}

	private int createCareStreamOrder(long benRegID, long benVisitID, String Authorization, Long benFlowID) {
//		ArrayList<Object[]> benDataForCareStream = registrarRepoBenData.getBenDataForCareStream(benRegID);
		ArrayList<Object[]> benDataForCareStream = beneficiaryFlowStatusRepo.getBenDataForCareStream(benFlowID);

		int r = cSCarestreamServiceImpl.createMamographyRequest(benDataForCareStream, benRegID, benVisitID,
				Authorization);
		return r;
	}

	@Deprecated
	public String getBenDoctorDiagnosisData(Long benRegID, Long visitCode) {
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("benDiagnosisDetails", cSDoctorServiceImpl.getBenCancerDiagnosisData(benRegID, visitCode));
		return new Gson().toJson(resMap);
	}

	// Fetch CS Doctor Details START....
	public String getBenCaseRecordFromDoctorCS(Long benRegID, Long visitCode) {
		Map<String, Object> resMap = new HashMap<>();

		resMap.put("diagnosis", cSDoctorServiceImpl.getBenCancerDiagnosisData(benRegID, visitCode));
		return new Gson().toJson(resMap);
	}
	// Fetch CS Doctor Details END....

	@Transactional(rollbackFor = Exception.class)
	public int updateCancerScreeningDoctorData(JsonObject requestOBJ) throws Exception {
		int updateSuccessFlag = 0;
		int diagnosisSuccessFlag = 0;
		if (requestOBJ != null) {
			if (requestOBJ.has("diagnosis") && !requestOBJ.get("diagnosis").isJsonNull()) {
				CancerDiagnosis cancerDiagnosis = InputMapper.gson().fromJson(requestOBJ.get("diagnosis"),
						CancerDiagnosis.class);

				CommonUtilityClass commonUtilityClass = InputMapper.gson().fromJson(requestOBJ.get("diagnosis"),
						CommonUtilityClass.class);

				diagnosisSuccessFlag = cSDoctorServiceImpl.updateCancerDiagnosisDetailsByDoctor(cancerDiagnosis);
				if (diagnosisSuccessFlag > 0) {
					int i = beneficiaryFlowStatusRepo.updateBenFlowAfterTCSpcialistDoneForCanceScreening(
							commonUtilityClass.getBenFlowID(), commonUtilityClass.getBeneficiaryRegID(),
							commonUtilityClass.getVisitCode());
					if (i > 0) {
						updateSuccessFlag = 1;
						int l = tCRequestModelRepo.updateStatusIfConsultationCompleted(
								commonUtilityClass.getBeneficiaryRegID(), commonUtilityClass.getVisitCode(), "D");

					} else
						throw new RuntimeException("Error while updating beneficiary flow status.");
				} else
					throw new RuntimeException("Error while saving data.");
			} else {
				throw new RuntimeException("Invalid request.");
			}
		} else {
			throw new RuntimeException("Invalid request as it is null.");
		}
		return updateSuccessFlag;
	}

}
