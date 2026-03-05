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
package com.iemr.hwc.controller.ncdCare;

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

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.iemr.hwc.service.ncdCare.NCDCareServiceImpl;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.v3.oas.annotations.Operation;

/**
 * 
 * @Objective Saving NCD Care data for Nurse and Doctor.
 */
@RestController
@RequestMapping(value = "/NCDCare", headers = "Authorization", consumes = "application/json", produces = "application/json")
public class NCDCareController {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private NCDCareServiceImpl ncdCareServiceImpl;

	/**
	 * @Objective Save NCD Care data for nurse.
	 * @param JSON requestObj
	 * @return success or failure response
	 * @throws Exception
	 */
	@Operation(summary = "Save NCD care data collected by nurse")
	@PostMapping(value = { "/save/nurseData" })
	public ResponseEntity<String> saveBenNCDCareNurseData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) throws Exception {
		OutputResponse response = new OutputResponse();
		if (null != requestObj) {
			JsonObject jsnOBJ = JsonParser.parseString(requestObj).getAsJsonObject();
			logger.info("Request object for NCD Care nurse data saving :" + requestObj);
			String ncdCareRes = ncdCareServiceImpl.saveNCDCareNurseData(jsnOBJ, Authorization);
			response.setResponse(ncdCareRes);
		} else {
			response.setError(5000, "Invalid Request !!!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * @Objective Save NCD Care data for doctor.
	 * @param JSON requestObj
	 * @return success or failure response
	 */
	@Operation(summary = "Save NCD care beneficiary case record and referral")
	@PostMapping(value = { "/save/doctorData" })
	public ResponseEntity<String> saveBenNCDCareDoctorData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for NCD Care doctor data saving :" + requestObj);
		JsonObject jsnOBJ = JsonParser.parseString(requestObj).getAsJsonObject();
		if (jsnOBJ != null) {
			Long ncdCareRes = ncdCareServiceImpl.saveDoctorData(jsnOBJ, Authorization);
			if (null != ncdCareRes && ncdCareRes > 0) {
				response.setResponse("Data saved successfully");
			} else {
				response.setResponse("Unable to save data");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
			}
		} else {
			response.setResponse("Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * @Objective Fetching beneficiary visit details enterted by nurse.
	 * @param comingRequest
	 * @return visit details in JSON format
	 */
	@Operation(summary = "Get NCD care beneficiary visit details")
	@PostMapping(value = { "/getBenVisitDetailsFrmNurseNCDCare" })
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<String> getBenVisitDetailsFrmNurseNCDCare(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest)
			throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for NCD Care visit data fetching :" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.length() > 1 && obj.has("benRegID") && obj.has("visitCode")) {
			Long benRegID = obj.getLong("benRegID");
			Long visitCode = obj.getLong("visitCode");
			String res = ncdCareServiceImpl.getBenVisitDetailsFrmNurseNCDCare(benRegID, visitCode);
			response.setResponse(res);
		} else {
			logger.info("Invalid request");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("NCD Care visit data fetching Response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * @Objective Fetching beneficiary history details enterted by nurse.
	 * @param comingRequest
	 * @return visit details in JSON format
	 */
	@Operation(summary = "Get NCD Care beneficiary history")
	@PostMapping(value = { "/getBenNCDCareHistoryDetails" })
	public ResponseEntity<String> getBenNCDCareHistoryDetails(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest)
			throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for NCD Care history data fetching :" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("benRegID") && obj.has("visitCode")) {
			Long benRegID = obj.getLong("benRegID");
			Long visitCode = obj.getLong("visitCode");
			String s = ncdCareServiceImpl.getBenNCDCareHistoryDetails(benRegID, visitCode);
			response.setResponse(s);
		} else {
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("NCD Care history data fetching Response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * @Objective Fetching beneficiary vital details enterted by nurse.
	 * @param comingRequest
	 * @return visit details in JSON format
	 */
	@Operation(summary = "Get NCD Care beneficiary vitals")
	@PostMapping(value = { "/getBenVitalDetailsFrmNurseNCDCare" })
	public ResponseEntity<String> getBenVitalDetailsFrmNurseNCDCare(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest)
			throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for NCD Care vital data fetching :" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("benRegID") && obj.has("visitCode")) {
			Long benRegID = obj.getLong("benRegID");
			Long visitCode = obj.getLong("visitCode");
			String res = ncdCareServiceImpl.getBeneficiaryVitalDetails(benRegID, visitCode);
			response.setResponse(res);
		} else {
			logger.info("Invalid request");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("NCD Care vital data fetching Response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * @Objective Fetching beneficiary doctor details.
	 * @param comingRequest
	 * @return visit details in JSON format
	 */
	@Operation(summary = "Get NCD care beneficiary case record and referral")
	@PostMapping(value = { "/getBenCaseRecordFromDoctorNCDCare" })
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<String> getBenCaseRecordFromDoctorNCDCare(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest)
			throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for NCD Care doctor data fetching :" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (null != obj && obj.length() > 1 && obj.has("benRegID") && obj.has("visitCode")) {
			Long benRegID = obj.getLong("benRegID");
			Long visitCode = obj.getLong("visitCode");
			String res = ncdCareServiceImpl.getBenCaseRecordFromDoctorNCDCare(benRegID, visitCode);
			response.setResponse(res);
		} else {
			logger.info("Invalid request");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("NCD Care doctor data fetching Response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Replace NCD Care History Data entered by Nurse with the details
	 *            entered by Doctor
	 */
	@Operation(summary = "Update NCD care beneficiary history")
	@PostMapping(value = { "/update/historyScreen" })
	public ResponseEntity<String> updateHistoryNurse(@RequestBody String requestObj) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for history data updating :" + requestObj);
		JsonObject jsnOBJ = JsonParser.parseString(requestObj).getAsJsonObject();
		int result = ncdCareServiceImpl.updateBenHistoryDetails(jsnOBJ);
		if (result > 0) {
			response.setResponse("Data updated successfully");
		} else {
			response.setError(500, "Unable to modify data");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		}
		logger.info("History data update Response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Replace NCD Care Vital Data entered by Nurse with the details
	 *            entered by Doctor
	 */
	@Operation(summary = "Update NCD care beneficiary vitals")
	@PostMapping(value = { "/update/vitalScreen" })
	public ResponseEntity<String> updateVitalNurse(@RequestBody String requestObj) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for vital data updating :" + requestObj);
		JsonObject jsnOBJ = JsonParser.parseString(requestObj).getAsJsonObject();
		int result = ncdCareServiceImpl.updateBenVitalDetails(jsnOBJ);
		if (result > 0) {
			response.setResponse("Data updated successfully");
		} else {
			response.setError(500, "Unable to modify data");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		}
		logger.info("Vital data update Response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Replace NCD Care doctor data for the doctor next visit
	 */
	@Operation(summary = "Update NCD care beneficiary case record and referral")
	@PostMapping(value = { "/update/doctorData" })
	public ResponseEntity<String> updateNCDCareDoctorData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for doctor data updating :" + requestObj);
		JsonObject jsnOBJ = JsonParser.parseString(requestObj).getAsJsonObject();
		Long result = ncdCareServiceImpl.updateNCDCareDoctorData(jsnOBJ, Authorization);
		if (null != result && result > 0) {
			response.setResponse("Data updated successfully");
		} else {
			response.setError(500, "Unable to modify data");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		}
		logger.info("Doctor data update Response:" + response);
		return ResponseEntity.ok(response.toString());
	}

}
