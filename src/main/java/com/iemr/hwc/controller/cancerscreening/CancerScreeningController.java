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
package com.iemr.hwc.controller.cancerscreening;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.data.doctor.CancerDiagnosis;
import com.iemr.hwc.data.nurse.BenCancerVitalDetail;
import com.iemr.hwc.service.cancerScreening.CSServiceImpl;
import com.iemr.hwc.utils.mapper.InputMapper;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.v3.oas.annotations.Operation;

/**
 * 
 * @Objective Saving Cancer screening data for Nurse and Doctor both.
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/CS-cancerScreening", headers = "Authorization", consumes = "application/json", produces = "application/json")
public class CancerScreeningController {
	private Logger logger = LoggerFactory.getLogger(CancerScreeningController.class);
	private OutputResponse response;
	private CSServiceImpl cSServiceImpl;

	@Autowired
	public void setCancerScreeningServiceImpl(CSServiceImpl cSServiceImpl) {
		this.cSServiceImpl = cSServiceImpl;
	}

	/**
	 * @Objective Save Cancer Screening data for nurse.
	 * @param JSON requestObj
	 * @return success or failure response
	 * @throws Exception
	 */

	@CrossOrigin
	@Operation(summary = "Save cancer screening data collected by nurse")
	@PostMapping(value = { "/save/nurseData" })
	public String saveBenCancerScreeningNurseData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) throws Exception {
		OutputResponse response = new OutputResponse();

		if (null != requestObj) {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			try {
				logger.info("Request object for CS nurse data saving :" + requestObj);

				if (jsnOBJ != null) {
					String nurseDataSaveSuccessFlag = cSServiceImpl.saveCancerScreeningNurseData(jsnOBJ, Authorization);
					response.setResponse(nurseDataSaveSuccessFlag);
				} else {
					response.setError(5000, "Invalid request");
				}
			} catch (Exception e) {
				logger.error("Error while saving beneficiary nurse data :" + e.getMessage());
				cSServiceImpl.deleteVisitDetails(jsnOBJ);
				response.setError(5000, e.getMessage());
			}
		}
		return response.toString();
	}

	/**
	 * @Objective Save Cancer Screening data for doctor.
	 * @param JSON requestObj
	 * @return success or failure response
	 */

	@CrossOrigin
	@Operation(summary = "Update cancer screening data by the doctor")
	@PostMapping(value = { "/save/doctorData" })
	public String saveBenCancerScreeningDoctorData(@RequestBody String requestObj,
			@RequestHeader String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for CS doctor data saving :" + requestObj);

			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			if (jsnOBJ != null) {
				Long csDocDataSaveSuccessFlag = cSServiceImpl.saveCancerScreeningDoctorData(jsnOBJ, Authorization);
				if (csDocDataSaveSuccessFlag != null && csDocDataSaveSuccessFlag > 0) {
					response.setResponse("Data saved successfully");
				} else {
					response.setError(5000, "Unable to save data");
				}
			} else {
				response.setError(5000, "Invalid request");
			}

		} catch (Exception e) {
			logger.error("Error while saving beneficiary doctor data :" + e.getMessage());
			response.setError(5000, e.getMessage());
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary visit details enterted by nurse.
	 * @param benRegID and benVisitID
	 * @return visit details in JSON format
	 */

	@CrossOrigin()
	@Operation(summary = "Get beneficiary visit details")
	@PostMapping(value = { "/getBenDataFrmNurseToDocVisitDetailsScreen" })
	public String getBenDataFrmNurseScrnToDocScrnVisitDetails(
			@Param(value = "{\"benRegID\":\"Long\", \"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for CS visit data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.length() > 1) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");
				String s = cSServiceImpl.getBenDataFrmNurseToDocVisitDetailsScreen(benRegID, visitCode);
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("CS visit data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary visit data");
			logger.error("Error while getting beneficiary visit data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary history details enterted by nurse.
	 * @param benRegID and benVisitID
	 * @return history details in JSON format
	 */

	@CrossOrigin()
	@Operation(summary = "Get beneficiary cancer history")
	@PostMapping(value = { "/getBenDataFrmNurseToDocHistoryScreen" })
	public String getBenDataFrmNurseScrnToDocScrnHistory(
			@Param(value = "{\"benRegID\":\"Long\", \"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for CS history data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.length() > 1) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");
				String s = cSServiceImpl.getBenDataFrmNurseToDocHistoryScreen(benRegID, visitCode);
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("CS history data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary history data");
			logger.error("Error while getting beneficiary history data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary vital details enterted by nurse.
	 * @param benRegID and benVisitID
	 * @return vital details in JSON format
	 */

	@CrossOrigin()
	@Operation(summary = "Get beneficiary vitals")
	@PostMapping(value = { "/getBenDataFrmNurseToDocVitalScreen" })
	public String getBenDataFrmNurseScrnToDocScrnVital(
			@Param(value = "{\"benRegID\":\"Long\", \"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for CS vital data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.length() > 1) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String s = cSServiceImpl.getBenDataFrmNurseToDocVitalScreen(benRegID, visitCode);
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("CS vital data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary vital data");
			logger.error("Error while getting beneficiary vital data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary examination details enterted by nurse.
	 * @param benRegID and benVisitID
	 * @return examination details in JSON format
	 */

	@CrossOrigin()
	@Operation(summary = "Get beneficiary examination details")
	@PostMapping(value = { "/getBenDataFrmNurseToDocExaminationScreen" })
	public String getBenDataFrmNurseScrnToDocScrnExamination(
			@Param(value = "{\"benRegID\":\"Long\", \"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for CS examination data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.length() > 1) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");
				String s = cSServiceImpl.getBenDataFrmNurseToDocExaminationScreen(benRegID, visitCode);
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("CS examination data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary examination data");
			logger.error("Error while getting beneficiary examination data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetch beneficiary previous family history details enterted by
	 *            nurse.
	 * @param benRegID
	 * @return previous family history details in JSON format
	 */

	@CrossOrigin()
	@Operation(summary = "Get beneficiary family history")
	@PostMapping(value = { "/getBenCancerFamilyHistory" })
	public String getBenCancerFamilyHistory(
			@Param(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for CS family history data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = cSServiceImpl.getBenFamilyHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("CS family history data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary family history data");
			logger.error("Error while getting beneficiary family history data:" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetch beneficiary previous personal history details enterted by
	 *            nurse.
	 * @param benRegID
	 * @return previous personal history details in JSON format
	 */

	@CrossOrigin()
	@Operation(summary = "Get beneficiary personal history")
	@PostMapping(value = { "/getBenCancerPersonalHistory" })
	public String getBenCancerPersonalHistory(
			@Param(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for CS personal history data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = cSServiceImpl.getBenPersonalHistoryData(benRegID);
				response.setResponse(s);
			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("CS personal history data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary personal history data");
			logger.error("Error while getting beneficiary personal history data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetch beneficiary previous personal diet history details enterted
	 *            by nurse.
	 * @param benRegID
	 * @return previous personal history details in JSON format
	 */

	@CrossOrigin()
	@Operation(summary = "Get beneficiary personal diet history")
	@PostMapping(value = { "/getBenCancerPersonalDietHistory" })
	public String getBenCancerPersonalDietHistory(
			@Param(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for CS personal diet history data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = cSServiceImpl.getBenPersonalDietHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("CS personal diet history data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary personal diet history data");
			logger.error("Error while getting beneficiary personal diet history data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetch beneficiary previous obstetric history details entered by
	 *            nurse.
	 * @param benRegID
	 * @return previous obstetric history details in JSON format
	 */

	@CrossOrigin()
	@Operation(summary = "Get beneficiary obstetric history")
	@PostMapping(value = { "/getBenCancerObstetricHistory" })
	public String getBenCancerObstetricHistory(
			@Param(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for CS obstetric history data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = cSServiceImpl.getBenObstetricHistoryData(benRegID);
				response.setResponse(s);
			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("CS obstetric history data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary obstetric history data");
			logger.error("Error while getting beneficiary obstetric history data:" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetch beneficiary diagnosis details entered by doctor.
	 * @param benRegID
	 * @return doctor details in JSON format
	 */

	@CrossOrigin()
	@Operation(summary = "Get beneficiary case record and referral details")
	@PostMapping(value = { "/getBenCaseRecordFromDoctorCS" })
	@Transactional(rollbackFor = Exception.class)
	public String getBenCaseRecordFromDoctorCS(
			@Param(value = "{\"benRegID\":\"Long\", \"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for CS doctor data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (null != obj && obj.length() > 1 && obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");
				String res = cSServiceImpl.getBenCaseRecordFromDoctorCS(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("CS doctor data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary doctor data");
			logger.error("Error while getting beneficiary doctor data :" + e);
		}
		return response.toString();
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Replace Cancer Screening History Details entered by Nurse with the
	 *            details entered by Doctor
	 */

	@CrossOrigin
	@Operation(summary = "Update cancer screening history")
	@PostMapping(value = { "/update/historyScreen" })
	public String updateCSHistoryNurse(
			@Param(value = "{\"historyDetails\": {\"familyHistory\":{\"diseases\": [{\"beneficiaryRegID\":\"Long\", \"benVisitID\":\"Long\", "
					+ "\"providerServiceMapID\":\"Integer\", \"cancerDiseaseType\":\"String\", \"otherDiseaseType\":\"String\", \"familyMemberList\":\"List\", "
					+ "\"createdBy\":\"String\"}]}, \"personalHistory\":{\"beneficiaryRegID\":\"Long\",\"benVisitID\":\"Long\", \"providerServiceMapID\":\"Integer\", "
					+ "\"tobaccoUse\":\"String\", \"startAge_year\":\"Integer\", \"endAge_year\":\"Integer\", \"typeOfTobaccoProductList\":\"List\", "
					+ "\"quantityPerDay\":\"Integer\", \"isFilteredCigaerette\":\"Boolean\", \"isCigaretteExposure\":\"Boolean\", \"isBetelNutChewing\":\"Boolean\", "
					+ "\"durationOfBetelQuid\":\"Integer\", \"alcoholUse\":\"String\", \"ssAlcoholUsed\":\"Boolean\", \"frequencyOfAlcoholUsed\":\"String\", "
					+ "\"dietType\":\"String\", \"intakeOfOutsidePreparedMeal\":\"Integer\", \"fruitConsumptionDays\":\"Integer\", \"fruitQuantityPerDay\":\"Integer\", "
					+ "\"vegetableConsumptionDays\":\"Integer\", \"vegetableQuantityPerDay\":\"Integer\", \"typeOfOilConsumedList\":\"List\","
					+ "\"physicalActivityType\":\"String\", \"ssRadiationExposure\":\"Boolean\", \"isThyroidDisorder\":\"Boolean\", \"createdBy\":\"String\"}"
					+ "\"pastObstetricHistory\":{\"beneficiaryRegID\":\"Long\", \"benVisitID\":\"Long\", \"providerServiceMapID\":\"Integer\", "
					+ "\"pregnancyStatus\":\"String\", \"isUrinePregTest\":\"Boolean\", \"pregnant_No\":\"String\",\"noOfLivingChild\":\"Integer\","
					+ "\"isAbortion\":\"Boolean\",\"isOralContraceptiveUsed\":\"Boolean\", \"isHormoneReplacementTherapy\":\"Boolean\",\"menarche_Age\":\"Integer\", "
					+ "\"isMenstrualCycleRegular\":\"Boolean\",\"menstrualCycleLength\":\"Integer\", \"menstrualFlowDuration\":\"Integer\", "
					+ "\"menstrualFlowType\":\"String\", \"isDysmenorrhea\":\"Boolean\", \"isInterMenstrualBleeding\":\"Boolean\", "
					+ "\"menopauseAge\":\"Integer\", \"isPostMenopauseBleeding\":\"Boolean\", \"createdBy\":\"String\"}}}") @RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for CS history data updating :" + requestObj);
		JsonObject jsnOBJ = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElmnt = jsnParser.parse(requestObj);
		jsnOBJ = jsnElmnt.getAsJsonObject();
		try {
			int result = cSServiceImpl.UpdateCSHistoryNurseData(jsnOBJ);
			if (result > 0) {
				response.setResponse("Data updated successfully");
			} else {
				response.setError(500, "Unable to modify data");
			}
			logger.info("CS history data update Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Unable to modify data");
			logger.error("Error while updating beneficiary history data :" + e);
		}

		return response.toString();
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Replace Cancer Screening Vital Details entered by Nurse with the
	 *            details entered by Doctor
	 */

	@CrossOrigin
	@Operation(summary = "Update beneficiary vitals")
	@PostMapping(value = { "/update/vitalScreen" })
	public String upodateBenVitalDetail(
			@Param(value = "{\"ID\": \"Long\", \"beneficiaryRegID\":\"Long\",\"benVisitID\":\"Long\","
					+ "\"weight_Kg\":\"Double\", \"height_cm\":\"Double\", \"waistCircumference_cm\":\"Double\", \"bloodGlucose_Fasting\":\"Short\","
					+ "\"bloodGlucose_Random\":\"Short\", \"bloodGlucose_2HrPostPrandial\":\"Short\", \"systolicBP_1stReading\":\"Short\", "
					+ "\"diastolicBP_1stReading\":\"Short\", \"systolicBP_2ndReading\":\"Short\", \"diastolicBP_2ndReading\":\"Short\", "
					+ "\"systolicBP_3rdReading\":\"Short\", \"diastolicBP_3rdReading\":\"Short\","
					+ " \"hbA1C\":\"Short\",\"hemoglobin\":\"Short\",\"modifiedBy\":\"String\"}") @RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for CS vital data updating :" + requestObj);
		try {
			BenCancerVitalDetail benCancerVitalDetail = InputMapper.gson().fromJson(requestObj,
					BenCancerVitalDetail.class);
			int responseObj = cSServiceImpl.updateBenVitalDetail(benCancerVitalDetail);
			if (responseObj > 0) {
				response.setResponse("Data updated successfully");
			} else {
				response.setError(500, "Unable to modify data");
			}
			logger.info("CS vital data update Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Unable to modify data");
			logger.error("Error while updating beneficiary vital data :" + e);
		}
		return response.toString();
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Replace Cancer Screening examination Details entered by Nurse with
	 *            the details entered by Doctor
	 */

	@CrossOrigin
	@Operation(summary = "Update beneficiary examination details")
	@PostMapping(value = { "/update/examinationScreen" })
	public String upodateBenExaminationDetail(@RequestBody String requestObj) {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for CS examination data updating :" + requestObj);
		JsonObject jsnOBJ = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElmnt = jsnParser.parse(requestObj);
		jsnOBJ = jsnElmnt.getAsJsonObject();
		try {
			int responseObj = cSServiceImpl.updateBenExaminationDetail(jsnOBJ);
			if (responseObj > 0) {
				response.setResponse("Data updated successfully");
			} else {
				response.setError(500, "Unable to modify data");
			}
			logger.info("CS examination data update Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Unable to modify data");
			logger.error("Error while updating beneficiary examination data :" + e);
		}
		return response.toString();
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective update Cancer Diagnosis Details By Oncologist
	 * 
	 */

	@CrossOrigin
	@Operation(summary = "Update cancer diagnosis details by Oncologist")
	@PostMapping(value = { "/update/examinationScreen/diagnosis" })
	public String updateCancerDiagnosisDetailsByOncologist(
			@Param(value = "{\"beneficiaryRegID\":\"Long\", \"benVisitID\":\"Long\", \"visitCode\":\"Long\", "
					+ "\"provisionalDiagnosisOncologist\":\"String\", \"modifiedBy\":\"string\"}") @RequestBody String requestObj) {

		response = new OutputResponse();
		logger.info("Request object for CS diagnosis data updating :" + requestObj);
		try {
			CancerDiagnosis cancerDiagnosis = InputMapper.gson().fromJson(requestObj, CancerDiagnosis.class);
			int result = cSServiceImpl.updateCancerDiagnosisDetailsByOncologist(cancerDiagnosis);
			if (result > 0) {
				response.setResponse("Data updated successfully");
			} else {
				response.setError(5000, "Unable to modify data");
			}
			logger.info("CS diagnosis data update Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Unable to modify data");
			logger.error("Error while updating beneficiary diagnosis data :" + e);
		}
		return response.toString();
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Replace doctor data for the doctor next visit
	 * 
	 */

	@CrossOrigin
	@Operation(summary = "Update cancer screening data")
	@PostMapping(value = { "/update/doctorData" })
	public String updateCancerScreeningDoctorData(@RequestBody String requestObj) {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for CS doctor data updating :" + requestObj);
		JsonObject jsnOBJ = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElmnt = jsnParser.parse(requestObj);
		jsnOBJ = jsnElmnt.getAsJsonObject();
		try {
			int result = cSServiceImpl.updateCancerScreeningDoctorData(jsnOBJ);
			if (result > 0) {
				response.setResponse("Data updated successfully");
			} else {
				response.setError(500, "Unable to modify data");
			}
			logger.info("CS doctor data update Response:" + response);
		} catch (Exception e) {
			logger.error("Error while updating beneficiary data. " + e.getMessage());
			response.setError(5000, e.getMessage());
		}
		return response.toString();
	}
}
