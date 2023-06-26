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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.data.doctor.CancerDiagnosis;
import com.iemr.hwc.data.nurse.BenCancerVitalDetail;
import com.iemr.hwc.service.cancerScreening.CSServiceImpl;
import com.iemr.hwc.utils.mapper.InputMapper;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author NA874500
 * @Objective Update Cancer screening data for Nurse.
 * @Date 17-01-2018
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/CS-cancerScreening", headers = "Authorization")
public class CancerScreeningUpdateController {
	private InputMapper inputMapper = new InputMapper();
	private OutputResponse response;
	private Logger logger = LoggerFactory.getLogger(CancerScreeningUpdateController.class);
	private CSServiceImpl cSServiceImpl;

	@Autowired
	public void setCancerScreeningServiceImpl(CSServiceImpl cSServiceImpl) {
		this.cSServiceImpl = cSServiceImpl;
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Replace Cancer Screening History Details entered by Nurse with the
	 *            details entered by Doctor
	 */

	@CrossOrigin
	@ApiOperation(value = "update Cancer Screening History Nurse Data in Doctor screen", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/historyScreen" }, method = { RequestMethod.POST })
	public String updateCSHistoryNurse(
			@ApiParam(value = "{\"historyDetails\": {\"familyHistory\":{\"diseases\": [{\"beneficiaryRegID\":\"Long\", \"benVisitID\":\"Long\", "
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
		inputMapper = new InputMapper();
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
	@ApiOperation(value = "update Ben Vital Detail", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/vitalScreen" }, method = { RequestMethod.POST })
	public String upodateBenVitalDetail(
			@ApiParam(value = "{\"ID\": \"Long\", \"beneficiaryRegID\":\"Long\",\"benVisitID\":\"Long\","
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
	@ApiOperation(value = "update Ben Examination Detail", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/examinationScreen" }, method = { RequestMethod.POST })
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
	@ApiOperation(value = "update Cancer Diagnosis Details By Oncologist", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/examinationScreen/diagnosis" }, method = { RequestMethod.POST })
	public String updateCancerDiagnosisDetailsByOncologist(
			@ApiParam(value = "{\"beneficiaryRegID\":\"Long\", \"benVisitID\":\"Long\", \"visitCode\":\"Long\", "
					+ "\"provisionalDiagnosisOncologist\":\"String\", \"modifiedBy\":\"string\"}") @RequestBody String requestObj) {

		response = new OutputResponse();
		inputMapper = new InputMapper();
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
	@ApiOperation(value = "update CancerScreening Doctor Data", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/doctorData" }, method = { RequestMethod.POST })
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
			response.setError(5000, "Error while updating beneficiary data. " + e);
			logger.error("Error while updating beneficiary doctor data :" + e);
		}

		return response.toString();
	}
}
