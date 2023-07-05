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
package com.iemr.hwc.controller.familyPlanning;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.service.family_planning.FamilyPlanningService;
import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping(value = "/family-planning", headers = "Authorization")
public class FamilyPlanningController {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private FamilyPlanningService familyPlanningService;

	@ApiOperation(value = "Save family planning data collected by nurse", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/save-family-planning-nurse-data", method = RequestMethod.POST)
	public String saveFamilyPlanningNuseData(@ApiParam(value = "{\r\n" + "  \"visitDetails\": {\r\n"
			+ "    \"visitDetails\": {\r\n" + "      \"beneficiaryRegID\": \"274661\",\r\n"
			+ "      \"providerServiceMapID\": \"1261\",\r\n" + "      \"visitNo\": null,\r\n"
			+ "      \"visitReason\": \"Follow Up\",\r\n"
			+ "      \"visitCategory\": \"FP & Contraceptive Services\",\r\n"
			+ "      \"pregnancyStatus\": \"Yes\",\r\n" + "      \"IdrsOrCbac\": null,\r\n"
			+ "      \"rCHID\": \"23\",\r\n" + "      \"healthFacilityType\": null,\r\n"
			+ "      \"healthFacilityLocation\": null,\r\n" + "      \"reportFilePath\": null,\r\n"
			+ "      \"followUpForFpMethod\": [\r\n" + "        \"Condoms (Nirodh)\",\r\n" + "        \"IUCD 375\",\r\n"
			+ "        \"Tubectomy (Female Sterilization)\",\r\n" + "        \"Other\"\r\n" + "      ],\r\n"
			+ "      \"otherFollowUpForFpMethod\": \"other follow FP\",\r\n" + "      \"sideEffects\": [\r\n"
			+ "        \"Irregular menstrual bleeding\",\r\n" + "        \"Delayed periods\",\r\n"
			+ "        \"Other\"\r\n" + "      ],\r\n" + "      \"otherSideEffects\": \"other effect\",\r\n"
			+ "      \"createdBy\": \"testtm\",\r\n" + "      \"vanID\": 220,\r\n"
			+ "      \"parkingPlaceID\": 246,\r\n" + "      \"fileIDs\": null\r\n" + "    },\r\n"
			+ "    \"chiefComplaints\": [\r\n" + "      {\r\n" + "        \"beneficiaryRegID\": \"274661\",\r\n"
			+ "        \"benVisitID\": null,\r\n" + "        \"providerServiceMapID\": \"1261\",\r\n"
			+ "        \"conceptID\": \"386661006\",\r\n" + "        \"chiefComplaint\": \"Fever\",\r\n"
			+ "        \"chiefComplaintID\": 167,\r\n" + "        \"duration\": \"1\",\r\n"
			+ "        \"unitOfDuration\": \"Hours\",\r\n" + "        \"description\": null,\r\n"
			+ "        \"createdBy\": \"testtm\",\r\n" + "        \"vanID\": 220,\r\n"
			+ "        \"parkingPlaceID\": 246\r\n" + "      }\r\n" + "    ]\r\n" + "  },\r\n"
			+ "  \"vitalDetails\": {\r\n" + "    \"beneficiaryRegID\": \"274661\",\r\n"
			+ "    \"benVisitID\": null,\r\n" + "    \"providerServiceMapID\": \"1261\",\r\n"
			+ "    \"weight_Kg\": \"55\",\r\n" + "    \"height_cm\": \"156\",\r\n"
			+ "    \"waistCircumference_cm\": null,\r\n" + "    \"hipCircumference_cm\": null,\r\n"
			+ "    \"bMI\": 22.6,\r\n" + "    \"waistHipRatio\": null,\r\n" + "    \"headCircumference_cm\": null,\r\n"
			+ "    \"midUpperArmCircumference_MUAC_cm\": null,\r\n" + "    \"temperature\": \"91\",\r\n"
			+ "    \"pulseRate\": \"77\",\r\n" + "    \"sPO2\": null,\r\n" + "    \"systolicBP_1stReading\": null,\r\n"
			+ "    \"diastolicBP_1stReading\": null,\r\n" + "    \"bloodGlucose_Fasting\": null,\r\n"
			+ "    \"bloodGlucose_Random\": null,\r\n" + "    \"bloodGlucose_2hr_PP\": null,\r\n"
			+ "    \"respiratoryRate\": null,\r\n" + "    \"rbsTestResult\": null,\r\n"
			+ "    \"rbsTestRemarks\": null,\r\n" + "    \"rbsCheckBox\": true,\r\n" + "    \"hemoglobin\": null,\r\n"
			+ "    \"createdBy\": \"testtm\",\r\n" + "    \"vanID\": 220,\r\n" + "    \"parkingPlaceID\": 246\r\n"
			+ "  },\r\n" + "  \"familyPlanningReproductiveDetails\": {\r\n" + "    \"fertilityStatusID\": 1,\r\n"
			+ "    \"fertilityStatus\": \"Fertile\",\r\n" + "    \"parity\": 1,\r\n"
			+ "    \"totalNoOfChildrenBorn\": 5,\r\n" + "    \"totalNoOfChildrenBornFemale\": 2,\r\n"
			+ "    \"totalNoOfChildrenBornMale\": 3,\r\n" + "    \"numberOfLiveChildren\": 4,\r\n"
			+ "    \"numberOfLiveChildrenFemale\": 2,\r\n" + "    \"numberOfLiveChildrenMale\": 1,\r\n"
			+ "    \"ageOfYoungestChild\": 2,\r\n" + "    \"unitOfAge\": \"years\",\r\n"
			+ "    \"youngestChildGender\": \"Others\",\r\n" + "    \"otherYoungestChildGender\": \"Transgender\",\r\n"
			+ "    \"currentlyUsingFpMethod\": [\r\n" + "      \"Condoms (Nirodh)\",\r\n" + "      \"IUCD 375\",\r\n"
			+ "      \"Tubectomy (Female Sterilization)\",\r\n" + "      \"Other\"\r\n" + "    ],\r\n"
			+ "    \"otherCurrentlyUsingFpMethod\": \"other FP\",\r\n"
			+ "    \"dateOfSterilization\": \"2021-10-07T00:00:00.000Z\",\r\n"
			+ "    \"placeOfSterilization\": \"Hospital\",\r\n" + "    \"dosesTaken\": 5,\r\n"
			+ "    \"dateOfLastDoseTaken\": \"2021-10-07T00:00:00.000Z\"\r\n" + "  },\r\n"
			+ "  \"iecAndCounsellingDetails\": {\r\n" + "    \"counselledOn\": [\r\n"
			+ "      \"Healthy Healthy Timing & Spacing of Pregnancy (HTSP)\",\r\n"
			+ "      \"Medical Eligibility for Family Planning Method\",\r\n" + "      \"Others\"\r\n" + "    ],\r\n"
			+ "    \"otherCounselledOn\": \"Others\",\r\n" + "    \"typeOfContraceptiveOpted\": [\r\n"
			+ "      \"IUCD 380A\",\r\n" + "      \"IUCD 375\",\r\n" + "      \"Others\"\r\n" + "    ],\r\n"
			+ "    \"otherTypeOfContraceptiveOpted\": \"Others\"\r\n" + "  },\r\n" + "  \"dispensationDetails\": {\r\n"
			+ "    \"typeOfContraceptivePrescribed\": [\r\n" + "      \"Centchroman Pills (Chhaya)\",\r\n"
			+ "      \"Progestin Only Pills (POPs)\",\r\n" + "      \"Others\"\r\n" + "    ],\r\n"
			+ "    \"otherTypeOfContraceptivePrescribed\": \"Other\",\r\n" + "    \"dosesTaken\": \"4\",\r\n"
			+ "    \"dateOfLastDoseTaken\": \"1998-10-07T00:00:00.000Z\",\r\n" + "    \"qtyPrescribed\": \"3\",\r\n"
			+ "    \"nextVisitForRefill\": \"1998-10-07T00:00:00.000Z\",\r\n"
			+ "    \"typeOfIUCDInsertedId\": \"12\",\r\n" + "    \"typeOfIUCDInserted\": \"IUCD CU 380A\",\r\n"
			+ "    \"dateOfIUCDInsertion\": \"1998-10-07T00:00:00.000Z\",\r\n"
			+ "    \"iucdInsertionDoneBy\": \"ANM\"\r\n" + "  },\r\n" + "  \"benFlowID\": \"26963\",\r\n"
			+ "  \"beneficiaryID\": \"235049275761\",\r\n" + "  \"sessionID\": \"3\",\r\n"
			+ "  \"parkingPlaceID\": 246,\r\n" + "  \"vanID\": 220,\r\n" + "  \"serviceID\": \"4\",\r\n"
			+ "  \"createdBy\": \"testtm\",\r\n" + "  \"tcRequest\": null,\r\n"
			+ "  \"beneficiaryRegID\": \"274661\",\r\n" + "  \"providerServiceMapID\": \"1261\"\r\n" + "}\r\n"
			+ "") @RequestBody String requestOBJ, @RequestHeader(value = "Authorization") String Authorization) {

		OutputResponse response = new OutputResponse();
		try {

			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestOBJ);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			if (jsnOBJ != null) {

				String outcome = familyPlanningService.saveNurseDataFP(jsnOBJ, Authorization);
				// set response once data save successfully
				response.setResponse(outcome);
			} else
				throw new IEMRException("Invalid request object / NULL");
		} catch (Exception e) {
			logger.error("error in saving family-planning nurse data : " + e.getLocalizedMessage());
			response.setError(5000, "error in saving family-planning nurse data : " + e.getLocalizedMessage());
		}

		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "Save family planning data collected by doctor", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "save-family-planning-doctor-data" }, method = { RequestMethod.POST })
	public String saveFamilyPlanningDoctorData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for FamilyPlanning doctor data saving :" + requestObj);

			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();
			if (jsnOBJ != null) {
				int i = familyPlanningService.saveDoctorDataFP(jsnOBJ, Authorization);
				if (i == 1)
					response.setResponse("Data saved successfully");
			} else {

				response.setError(5000, "Invalid request object : NULL");
			}

		} catch (Exception e) {
			logger.error("Error while saving doctor data:" + e);
			response.setError(5000, "Unable to save data. " + e.getMessage());
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary visit details enterted by nurse.
	 * @param benRegID and benVisitID
	 * @return visit details in JSON format
	 */

	@CrossOrigin()
	@ApiOperation(value = "Get family planning beneficiary visit details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenVisitDetails-Nurse-FamilyPlanning" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenVisitDetailsFrmNurseFamilyPlanning(
			@ApiParam(value = "{\"benRegID\":\"Long\", \"visitCode\":\"Long\"}") @RequestBody CommonUtilityClass commonUtilityClass) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for FamilyPlanning visit data fetching :" + commonUtilityClass.toString());
		try {
			if (commonUtilityClass != null && commonUtilityClass.getBenRegID() != null
					&& commonUtilityClass.getVisitCode() != null) {
				String s = familyPlanningService.getBenVisitDetailsFrmNurseFP(commonUtilityClass.getBenRegID(),
						commonUtilityClass.getVisitCode());
				if (s != null)
					response.setResponse(s);
				else
					throw new IEMRException("No record found");
			} else
				throw new IEMRException("Invalid request - Beneficiary and visit info required");
			// call service to fetch data
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary visit data : " + e.getLocalizedMessage());
			logger.error("Error while getting beneficiary visit data:" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary vital details entered by nurse.
	 * @param benRegID and benVisitID
	 * @return vital details in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get family planning beneficiary vitals", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenVitalDetailsFrmNurseFamilyPlanning" }, method = { RequestMethod.POST })
	public String getBenVitalDetailsFrmNurseFamilyPlanning(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody CommonUtilityClass commonUtilityClass) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for FamilyPlanning vital data fetching :" + commonUtilityClass.toString());
		try {
			if (commonUtilityClass.getBenRegID() != null && commonUtilityClass.getVisitCode() != null) {
				String s = familyPlanningService.getBeneficiaryVitalDetailsFP(commonUtilityClass.getBenRegID(),
						commonUtilityClass.getVisitCode());
				if (s != null)
					response.setResponse(s);
				else
					throw new IEMRException("No record found");
			} else
				throw new IEMRException("Invalid request - Beneficiary and visit info required");
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary vital data : " + e.getLocalizedMessage());
			logger.error("Error while getting beneficiary vital data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary FamilyPlanning details entered by nurse.
	 * @param benRegID and benVisitID
	 * @return FamilyPlanning details in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get family planning beneficiary details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenFPDetailsFrmNurseFamilyPlanning" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenFPDetailsFrmNurseFamilyPlanning(
			@ApiParam(value = "{\"benRegID\":\"Long\", \"visitCode\":\"Long\"}") @RequestBody CommonUtilityClass commonUtilityClass) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for FamilyPlanning details data fetching :" + commonUtilityClass.toString());
		try {
			String s = familyPlanningService.getBeneficiaryFPDetailsFP(commonUtilityClass.getBenRegID(),
					commonUtilityClass.getVisitCode());
			if (s != null)
				response.setResponse(s);
			else
				throw new IEMRException("No record found");
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary FamilyPlanning data " + e.getLocalizedMessage());
			logger.error("Error while getting beneficiary FamilyPlanning data:" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary doctor details.
	 * @param comingRequest
	 * @return doctor details in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get family planning beneficiary case record", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenCaseRecordFromDoctor" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenCaseRecordFromDoctor(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenCaseRecordFromDoctor FP request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (null != obj && obj.length() > 1 && obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = familyPlanningService.getBenCaseRecordFromDoctorFP(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenCaseRecordFromDoctor FP  response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary doctor data : " + e.getLocalizedMessage());
			logger.error("Error in getBenCaseRecordFromDoctor FP :" + e);
		}
		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "Update family planning data", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/FamilyPlanningScreen" }, method = { RequestMethod.POST })
	public String updateFamilyPlanningNurse(@RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for FamilyPlanning data updating :" + requestObj);

		try {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			String s = familyPlanningService.updateFPDataFP(jsnOBJ);

			if (s != null)
				response.setResponse(s);
			else
				throw new IEMRException("error in updating data - NULL");

		} catch (Exception e) {
			response.setError(5000, "Unable to modify data : " + e.getLocalizedMessage());
			logger.error("Error while updating beneficiary FamilyPlanning data :" + e);
		}

		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "Update family planning beneficiary vitals", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/vitalScreen" }, method = { RequestMethod.POST })
	public String updateVitalNurseFamilyPlanning(@RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for FamilyPlanning Vital data updating :" + requestObj);

		try {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			int i = familyPlanningService.updateBenVitalDetailsFP(jsnOBJ);
			if (i == 1)
				response.setResponse("Data updated successfully");
			else
				response.setError(500, "Unable to modify data, please contact administrator");

			logger.info("FamilyPlanning vital data update Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Unable to modify data : " + e.getLocalizedMessage());
			logger.error("Error while updating beneficiary vital data :" + e);
		}

		return response.toString();
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Update FamilyPlanning doctor data for the doctor next visit
	 */
	@CrossOrigin
	@ApiOperation(value = "Update family planning data", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/doctorData" }, method = { RequestMethod.POST })
	public String updateFamilyPlanningDoctorData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for doctor data updating :" + requestObj);
		try {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			Long result = familyPlanningService.updateDoctorDataFP(jsnOBJ, Authorization);

			if (null != result && result > 0) {
				response.setResponse("Data updated successfully");
			} else
				throw new IEMRException("error in updating data - NULL");

			logger.info("Doctor data update response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Unable to modify data. " + e.getLocalizedMessage());
			logger.error("Error while updating FamilyPlanning  doctor data:" + e);
		}

		return response.toString();
	}

}
