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
package com.iemr.hwc.controller.adolescent;

import java.sql.SQLException;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.service.adolescent.AdolescentAndChildCareService;
import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/child-adolescent-care", headers = "Authorization")
public class ChildhoodAdolescenceController {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private AdolescentAndChildCareService adolescentAndChildCareService;

	/**
	 * @Objective Save child-adolescent-care data for nurse.
	 * @param requestObj
	 * @return success or failure response with visit code
	 * @throws Exception
	 */

	@Operation(summary = "Save child adolescent care (CAC) nurse data")
	@PostMapping(value = { "/save/nurseData" })
	public String saveBenNurseDataCAC(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) throws Exception {
		OutputResponse response = new OutputResponse();
		if (null != requestObj) {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();
			try {
				logger.info("Request object for child-adolescent-care nurse data saving :" + requestObj);

				if (jsnOBJ != null) {
					String genOPDRes = adolescentAndChildCareService.saveNurseData(jsnOBJ, Authorization);
					response.setResponse(genOPDRes);

				} else {
					response.setResponse("Invalid request");
				}
			} catch (SQLException e) {
				logger.error("Error in nurse data saving :" + e);
				response.setError(5000, "Unable to save data : " + e.getLocalizedMessage());
			} catch (Exception e) {
				logger.error("Error in nurse data saving :" + e.getMessage());
				adolescentAndChildCareService.deleteVisitDetails(jsnOBJ);
				response.setError(5000, e.getMessage());
			}
		}
		return response.toString();
	}

	@Operation(summary = "Save child adolescent care doctor data")
	@PostMapping(value = { "save/doctorData" })
	public String saveDoctorDataCAC(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for child-adolescent-care doctor data saving :" + requestObj);

			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			JsonObject jsnOBJ = jsnElmnt.getAsJsonObject();
			if (jsnOBJ != null) {
				int i = adolescentAndChildCareService.saveDoctorDataCAC(jsnOBJ, Authorization);

				if (i == 1)
					response.setResponse("Data saved successfully");
			} else {

				response.setError(5000, "Invalid request object : NULL");
			}

		} catch (Exception e) {
			logger.error("Error while saving doctor data:" + e.getMessage());
			response.setError(5000, e.getMessage());
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary visit details entered by nurse.
	 * @param comingRequest
	 * @return visit details in JSON format
	 */

	@Operation(summary = "Get beneficiary visit details from nurse for child adolescent care")
	@PostMapping(value = { "/getBenVisitDetailsFrmNurseCAC" })
	@Transactional(rollbackFor = Exception.class)
	public String getBenVisitDetailsFrmNurseCAC(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request obj to fetch CAC visit details :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.length() > 1) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = adolescentAndChildCareService.getBenVisitDetailsFrmNurseCAC(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request : missing visit information");
			}
			logger.info("getBenVisitDetailsFrmNurseCAC response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary visit data : " + e.getLocalizedMessage());
			logger.error("Error in getBenVisitDetailsFrmNurseCAC:" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary history details entered by nurse.
	 * @param comingRequest
	 * @return history details in JSON format
	 */

	@Operation(summary = "Get child adolescent care beneficiary history")
	@PostMapping(value = { "/getBenHistoryDetails" })

	public String getBenHistoryDetails(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody CommonUtilityClass commonUtilityClass) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenHistoryDetails request:" + commonUtilityClass.toString());
		try {

			if (commonUtilityClass.getBenRegID() != null) {

				String s = adolescentAndChildCareService.getBirthAndImmuniHistory(commonUtilityClass.getBenRegID(),
						commonUtilityClass.getVisitCode());
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request : missing ben information");
			}
			logger.info("getBenHistoryDetails response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary history data : " + e.getLocalizedMessage());
			logger.error("Error in getBenHistoryDetails:" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary vital details entered by nurse.
	 * @param comingRequest
	 * @return vital details in JSON format
	 */

	@Operation(summary = "Get child adolescent care beneficiary vitals from nurse")
	@PostMapping(value = { "/getBenVitalDetailsFrmNurse" })
	public String getBenVitalDetailsFrmNurse(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenVitalDetailsFrmNurse request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = adolescentAndChildCareService.getBeneficiaryVitalDetails(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request : visit information missing");
			}
			logger.info("getBenVitalDetailsFrmNurse response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary vital data : " + e.getLocalizedMessage());
			logger.error("Error in getBenVitalDetailsFrmNurse:" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary immunization service entered by nurse.
	 * @param { "benRegID": "274685", "benVisitID": "171431", "visitCode":
	 *          "30022000171431" }
	 * @return immunization service details in JSON format
	 */

	@Operation(summary = "Get child adolescent care beneficiary immunization details")
	@PostMapping(value = { "/getBenImmunizationServiceDetails" })

	public String getBenImmunizationServiceDetails(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody CommonUtilityClass commonUtilityClass) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenImmunizationServiceDetails request:" + commonUtilityClass.toString());
		try {

			if (commonUtilityClass.getBenRegID() != null && commonUtilityClass.getVisitCode() != null) {

				String s = adolescentAndChildCareService.getBeneficiaryImmunizationServiceDetails(
						commonUtilityClass.getBenRegID(), commonUtilityClass.getVisitCode());
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request : missing ben information");
			}
			logger.info("getBenImmunizationServiceDetails response:" + response);
		} catch (Exception e) {
			response.setError(5000,
					"Error while getting beneficiary immunization service data : " + e.getLocalizedMessage());
			logger.error("Error in getBenImmunizationServiceDetails:" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary doctor details.
	 * @param comingRequest
	 * @return doctor details in JSON format
	 */

	@Operation(summary = "Get child adolescent care beneficiary details entered by doctor")
	@PostMapping(value = { "/getBenCaseRecordFromDoctor" })
	@Transactional(rollbackFor = Exception.class)
	public String getBenCaseRecordFromDoctor(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenCaseRecordFromDoctor CAC request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (null != obj && obj.length() > 1 && obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = adolescentAndChildCareService.getBenCaseRecordFromDoctorCAC(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenCaseRecordFromDoctor CAC  response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary doctor data : " + e.getLocalizedMessage());
			logger.error("Error in getBenCaseRecordFromDoctor CAC :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Updating beneficiary vital details entered by nurse.
	 * @param comingRequest
	 * @return vital details in JSON format
	 */

	@Operation(summary = "Update child adolescent care beneficiary vitals")
	@PostMapping(value = { "/update/vitalScreen" })
	public String updateVitalNurseCAC(@RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for CAC Vital data updating :" + requestObj);

		try {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			int i = adolescentAndChildCareService.updateBenVitalDetailsCAC(jsnOBJ);
			if (i == 1)
				response.setResponse("Data updated successfully");
			else
				response.setError(500, "Unable to modify data, please contact administrator");

			logger.info("CAC vital data update Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Unable to modify data : " + e.getLocalizedMessage());
			logger.error("Error while updating beneficiary vital data :" + e);
		}

		return response.toString();
	}

	/**
	 * @Objective Updating beneficiary history details entered by nurse.
	 * @param comingRequest
	 * @return history details in JSON format
	 */

	@Operation(summary = "Update birth and immunization history")
	@PostMapping(value = { "/update/BirthAndImmunizationHistoryScreen" })
	public String updateBirthAndImmunizationHistoryNurse(@RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for CAC data updating :" + requestObj);

		try {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			int i = adolescentAndChildCareService.updateBenHistoryDetails(jsnOBJ);

			if (i > 0)
				response.setResponse("data updated successfully");
			else
				throw new IEMRException("error in updating data. please contact administrator");

		} catch (Exception e) {
			response.setError(5000, "Unable to modify data : " + e.getLocalizedMessage());
			logger.error("Error while updating beneficiary CAC data :" + e);
		}

		return response.toString();
	}

	/**
	 * @Objective Updating beneficiary immunization service entered by nurse.
	 * @param { "benRegID": "274685", "benVisitID": "171431", "visitCode":
	 *          "30022000171431" }
	 * @return immunization service details in JSON format
	 */

	@Operation(summary = "Update immunization service data")
	@PostMapping(value = { "/update/ImmunizationServicesScreen" })
	public String updateImmunizationServicesNurse(@RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for CAC data updating :" + requestObj);

		try {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			int i = adolescentAndChildCareService.updateBenImmunServiceDetailsCAC(jsnOBJ);

			if (i > 0)
				response.setResponse("data updated successfully");
			else
				throw new IEMRException("error in updating data, please contact administrator");

		} catch (Exception e) {
			response.setError(5000, "Unable to modify data : " + e.getLocalizedMessage());
			logger.error("Error while updating beneficiary CAC data :" + e);
		}

		return response.toString();
	}

	/**
	 * @Objective Updating beneficiary doctor details.
	 * @param comingRequest
	 * @return doctor details in JSON format
	 */

	@Operation(summary = "Update child adolescent care doctor data")
	@PostMapping(value = { "/update/doctorData" })
	public String updateCACDoctorData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for doctor data updating :" + requestObj);
		try {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			Long result = adolescentAndChildCareService.updateDoctorDataCAC(jsnOBJ, Authorization);

			if (null != result && result > 0) {
				response.setResponse("Data updated successfully");
			} else
				throw new IEMRException("error in updating data - NULL");

			logger.info("Doctor data update response:" + response);
		} catch (Exception e) {
			logger.error("Unable to modify data. " + e.getLocalizedMessage());
			response.setError(5000, e.getMessage());
		}

		return response.toString();
	}

}
