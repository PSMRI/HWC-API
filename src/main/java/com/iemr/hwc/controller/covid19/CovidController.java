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
package com.iemr.hwc.controller.covid19;

import java.util.HashMap;
import java.util.Map;

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
import com.iemr.hwc.service.covid19.Covid19ServiceImpl;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.v3.oas.annotations.Operation;

/**
 * 
 * @Objective Saving NCD Care data for Nurse and Doctor.
 */
@RestController
@RequestMapping(value = "/pandemic/covid", headers = "Authorization", consumes = "application/json", produces = "application/json")
public class CovidController {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private Covid19ServiceImpl covid19ServiceImpl;

	@Operation(summary = "Save COVID nurse data")
	@PostMapping(value = { "/save/nurseData" })
	public String saveBenNCDCareNurseData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) throws Exception {
		OutputResponse response = new OutputResponse();

		if (null != requestObj) {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			try {
				logger.info("Request object for COVID 19 nurse data saving :" + requestObj);

				if (jsnOBJ != null) {
					String ncdCareRes = covid19ServiceImpl.saveCovid19NurseData(jsnOBJ, Authorization);
					response.setResponse(ncdCareRes);
				} else {
					response.setError(5000, "Invalid Request !!!");
				}

			} catch (Exception e) {
				logger.error("Error while saving Pandemic nurse data :" + e.getMessage());
				covid19ServiceImpl.deleteVisitDetails(jsnOBJ);
				response.setError(5000, e.getMessage());
			}
		}
		return response.toString();
	}

	/**
	 * @Objective Save Covid data for doctor.
	 * @param JSON requestObj
	 * @return success or failure response
	 */
	@Operation(summary = "Save COVID doctor data")
	@PostMapping(value = { "/save/doctorData" })
	public String saveBenCovidDoctorData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for Covid doctor data saving :" + requestObj);

			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			if (jsnOBJ != null) {
				Long ncdCareRes = covid19ServiceImpl.saveDoctorData(jsnOBJ, Authorization);
				if (null != ncdCareRes && ncdCareRes > 0) {
					response.setResponse("Data saved successfully");
				} else {
					response.setResponse("Unable to save data");
				}

			} else {
				response.setResponse("Invalid request");
			}
		} catch (Exception e) {
			logger.error("Error while saving Covid doctor data :" + e.getMessage());
			response.setError(5000, e.getMessage());
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary visit details entered by nurse.
	 * @param comingRequest
	 * @return visit details in JSON format
	 */
	@Operation(summary = "Get COVID beneficiary visit details")
	@PostMapping(value = { "/getBenVisitDetailsFrmNurseCovid" })
	@Transactional(rollbackFor = Exception.class)
	public String getBenVisitDetailsFrmNurseCovid19(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for Covid 19 visit data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.length() > 1) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = covid19ServiceImpl.getBenVisitDetailsFrmNurseCovid19(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid request");
				response.setError(5000, "Invalid request");
			}
			logger.info("Covid 19 visit data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary visit data");
			logger.error("Error while getting beneficiary visit data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary history details entered by nurse.
	 * @param comingRequest
	 * @return visit details in JSON format
	 */
	@Operation(summary = "Get COVID beneficiary history")
	@PostMapping(value = { "/getBenCovid19HistoryDetails" })

	public String getBenCovid19HistoryDetails(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for NCD Care history data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String s = covid19ServiceImpl.getBenCovid19HistoryDetails(benRegID, visitCode);
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("NCD Care history data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary history data");
			logger.error("Error while getting beneficiary history data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary vital details entered by nurse.
	 * @param comingRequest
	 * @return visit details in JSON format
	 */
	@Operation(summary = "Get COVID beneficiary vitals")
	@PostMapping(value = { "/getBenVitalDetailsFrmNurseCovid" })
	public String getBenVitalDetailsFrmNurseNCDCare(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for Covid 19 vital data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = covid19ServiceImpl.getBeneficiaryVitalDetails(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid request");
				response.setError(5000, "Invalid request");
			}
			logger.info("Covid 19 vital data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary vital data");
			logger.error("Error while getting beneficiary vital data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary doctor details.
	 * @param comingRequest
	 * @return visit details in JSON format
	 */
	@Operation(summary = "Get COVID beneficiary case-record and referral details")
	@PostMapping(value = { "/getBenCaseRecordFromDoctorCovid" })
	@Transactional(rollbackFor = Exception.class)
	public String getBenCaseRecordFromDoctorCovid19(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for Covid 19 doctor data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (null != obj && obj.length() > 1 && obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = covid19ServiceImpl.getBenCaseRecordFromDoctorCovid19(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid request");
				response.setError(5000, "Invalid request");
			}
			logger.info("Covid 19 doctor data fetching Response:" + response);
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
	 * @objective Replace COVID History Data entered by Nurse with the details
	 *            entered by doctor
	 */
	@Operation(summary = "Update COVID beneficiary history")
	@PostMapping(value = { "/update/historyScreen" })
	public String updateHistoryNurse(@RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for history data updating :" + requestObj);

		JsonObject jsnOBJ = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElmnt = jsnParser.parse(requestObj);
		jsnOBJ = jsnElmnt.getAsJsonObject();

		try {
			int result = covid19ServiceImpl.updateBenHistoryDetails(jsnOBJ);
			if (result > 0) {
				Map<String, Integer> resMap = new HashMap<String, Integer>();
				resMap.put("result", result);
				response.setResponse("Data updated successfully");
			} else {
				response.setError(500, "Unable to modify data");
			}
			logger.info("History data update Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Unable to modify data");
			logger.error("Error while updating history data :" + e);
		}

		return response.toString();
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Replace NCD Care Vital Data entered by Nurse with the details
	 *            entered by doctor
	 */
	@Operation(summary = "Update COVID beneficiary vitals")
	@PostMapping(value = { "/update/vitalScreen" })
	public String updateVitalNurse(@RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for vital data updating :" + requestObj);

		JsonObject jsnOBJ = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElmnt = jsnParser.parse(requestObj);
		jsnOBJ = jsnElmnt.getAsJsonObject();

		try {
			int result = covid19ServiceImpl.updateBenVitalDetails(jsnOBJ);
			if (result > 0) {
				response.setResponse("Data updated successfully");
			} else {
				response.setError(500, "Unable to modify data");
			}
			logger.info("Vital data update Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Unable to modify data");
			logger.error("Error while updating vital data :" + e);
		}

		return response.toString();
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Replace COVID 19 doctor data for the doctor next visit
	 */
	@Operation(summary = "Update COVID beneficiary case-record and referral details")
	@PostMapping(value = { "/update/doctorData" })
	public String updateCovid19DoctorData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for doctor data updating :" + requestObj);

		JsonObject jsnOBJ = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElmnt = jsnParser.parse(requestObj);
		jsnOBJ = jsnElmnt.getAsJsonObject();

		try {
			Long result = covid19ServiceImpl.updateCovid19DoctorData(jsnOBJ, Authorization);
			if (null != result && result > 0) {
				response.setResponse("Data updated successfully");
			} else {
				response.setError(500, "Unable to modify data");
			}
			logger.info("Doctor data update Response:" + response);
		} catch (Exception e) {
			logger.error("Unable to modify data. " + e.getMessage());
			response.setError(5000, e.getMessage());
		}

		return response.toString();
	}
}
