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
package com.iemr.hwc.controller.pnc;

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
import com.iemr.hwc.service.pnc.PNCServiceImpl;
import com.iemr.hwc.utils.response.OutputResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;

/**
 * 
 * @Objective Saving PNC nurse and doctor data
 *
 */
@RestController
@RequestMapping(value = "/PNC", headers = "Authorization", consumes = "application/json", produces = "application/json")
public class PostnatalCareController {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	private PNCServiceImpl pncServiceImpl;

	@Autowired
	public void setPncServiceImpl(PNCServiceImpl pncServiceImpl) {
		this.pncServiceImpl = pncServiceImpl;
	}

	/**
	 * @Objective Saving PNC nurse data
	 * @param requestObj
	 * @return success or failure response
	 * @throws Exception
	 */
	@Operation(summary = "Save PNC nurse data")
	@PostMapping(value = { "/save/nurseData" })
	public ResponseEntity<String> saveBenPNCNurseData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) throws Exception {
		OutputResponse response = new OutputResponse();
		if (null != requestObj) {
			JsonObject jsnOBJ = JsonParser.parseString(requestObj).getAsJsonObject();
			logger.info("Request object for PNC nurse data saving :" + requestObj);
			String ancRes = pncServiceImpl.savePNCNurseData(jsnOBJ, Authorization);
			response.setResponse(ancRes);
		} else {
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * @Objective Saving PNC doctor data
	 * @param requestObj
	 * @return success or failure response
	 */
	@Operation(summary = "Save PNC doctor data")
	@PostMapping(value = { "/save/doctorData" })
	public ResponseEntity<String> saveBenPNCDoctorData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for PNC doctor data saving :" + requestObj);
		JsonObject jsnOBJ = JsonParser.parseString(requestObj).getAsJsonObject();
		if (jsnOBJ != null) {
			Long r = pncServiceImpl.savePNCDoctorData(jsnOBJ, Authorization);
			if (r != null && r > 0) {
				response.setResponse("Data saved successfully");
			} else {
				response.setError(5000, "Unable to save data");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
			}
		} else {
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * @Objective Fetching Beneficiary visit data entered by nurse
	 * @param comingRequest
	 * @return visit details in JSON format
	 */
	@Operation(summary = "Get PNC beneficiary visit details from nurse")
	@PostMapping(value = { "/getBenVisitDetailsFrmNursePNC" })
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<String> getBenVisitDetailsFrmNursePNC(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest)
			throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("PNC visit data fetch request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.length() > 1 && obj.has("benRegID") && obj.has("visitCode")) {
			Long benRegID = obj.getLong("benRegID");
			Long visitCode = obj.getLong("visitCode");
			String res = pncServiceImpl.getBenVisitDetailsFrmNursePNC(benRegID, visitCode);
			response.setResponse(res);
		} else {
			logger.info("Invalid request");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("PNC visit data fetch response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * @Objective Fetching Beneficiary PNC Care data entered by nurse
	 * @param comingRequest
	 * @return PNC Care data in JSON format
	 */
	@Operation(summary = "Get PNC beneficiary details from nurse")
	@PostMapping(value = { "/getBenPNCDetailsFrmNursePNC" })
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<String> getBenPNCDetailsFrmNursePNC(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest)
			throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("PNC Care data fetch request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		Long visitCode = null;
		if (obj.has("benRegID")) {
			Long benRegID = obj.getLong("benRegID");
			if (obj.has("visitCode") && !obj.isNull("visitCode") && obj.get("visitCode") != null)
				visitCode = obj.getLong("visitCode");
			String res = pncServiceImpl.getBenPNCDetailsFrmNursePNC(benRegID, visitCode);
			response.setResponse(res);
		} else {
			logger.info("Invalid request");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("PNC Care data fetch response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * @Objective Fetching Beneficiary history data entered by nurse
	 * @param comingRequest
	 * @return history data in JSON format
	 */
	@Operation(summary = "Get PNC bneficiary history from nurse")
	@PostMapping(value = { "/getBenHistoryDetails" })
	public ResponseEntity<String> getBenHistoryDetails(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest)
			throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("History data fetch request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("benRegID") && obj.has("visitCode")) {
			Long benRegID = obj.getLong("benRegID");
			Long visitCode = obj.getLong("visitCode");
			String s = pncServiceImpl.getBenHistoryDetails(benRegID, visitCode);
			response.setResponse(s);
		} else {
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("History data fetch response :" + response);
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * @Objective Fetching Beneficiary vital data entered by nurse
	 * @param comingRequest
	 * @return vital data in JSON format
	 */
	@Operation(summary = "Get PNC beneficiary vitals from nurse")
	@PostMapping(value = { "/getBenVitalDetailsFrmNurse" })
	public ResponseEntity<String> getBenVitalDetailsFrmNurse(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest)
			throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("vital data fetch request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("benRegID") && obj.has("visitCode")) {
			Long benRegID = obj.getLong("benRegID");
			Long visitCode = obj.getLong("visitCode");
			String res = pncServiceImpl.getBeneficiaryVitalDetails(benRegID, visitCode);
			response.setResponse(res);
		} else {
			logger.info("Invalid request");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("Vital data fetch response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * @Objective Fetching Beneficiary examination data entered by nurse
	 * @param comingRequest
	 * @return examination data in JSON format
	 */
	@Operation(summary = "Get PNC beneficiary examination details from nurse")
	@PostMapping(value = { "/getBenExaminationDetailsPNC" })
	public ResponseEntity<String> getBenExaminationDetailsPNC(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest)
			throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("PNC examination data fetch request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("benRegID") && obj.has("visitCode")) {
			Long benRegID = obj.getLong("benRegID");
			Long visitCode = obj.getLong("visitCode");
			String s = pncServiceImpl.getPNCExaminationDetailsData(benRegID, visitCode);
			response.setResponse(s);
		} else {
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("Examination data fetch response :" + response);
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * @Objective Fetching Beneficiary doctor data
	 * @param comingRequest
	 * @return doctor data in JSON format
	 */
	@Operation(summary = "Get PNC beneficiary case record")
	@PostMapping(value = { "/getBenCaseRecordFromDoctorPNC" })
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<String> getBenCaseRecordFromDoctorPNC(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest)
			throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("PNC doctor data fetch request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (null != obj && obj.length() > 1 && obj.has("benRegID") && obj.has("visitCode")) {
			Long benRegID = obj.getLong("benRegID");
			Long visitCode = obj.getLong("visitCode");
			String res = pncServiceImpl.getBenCaseRecordFromDoctorPNC(benRegID, visitCode);
			response.setResponse(res);
		} else {
			logger.info("Invalid request");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("Doctor data fetch response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Replace PNC Care Data entered by Nurse with the details entered by
	 *            Doctor
	 */
	@Operation(summary = "Update PNC doctor data")
	@PostMapping(value = { "/update/PNCScreen" })
	public ResponseEntity<String> updatePNCCareNurse(@RequestBody String requestObj) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("PNC Care data update request:" + requestObj);
		JsonObject jsnOBJ = JsonParser.parseString(requestObj).getAsJsonObject();
		int result = pncServiceImpl.updateBenPNCDetails(jsnOBJ);
		if (result > 0) {
			response.setResponse("Data updated successfully");
		} else {
			response.setError(500, "Unable to modify data");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		}
		logger.info("PNC Care data update response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Replace PNC History Data entered by Nurse with the details entered
	 *            by Doctor
	 */
	@Operation(summary = "Update PNC beneficiary history")
	@PostMapping(value = { "/update/historyScreen" })
	public ResponseEntity<String> updateHistoryNurse(@RequestBody String requestObj) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("History data update request:" + requestObj);
		JsonObject jsnOBJ = JsonParser.parseString(requestObj).getAsJsonObject();
		int result = pncServiceImpl.updateBenHistoryDetails(jsnOBJ);
		if (result > 0) {
			response.setResponse("Data updated successfully");
		} else {
			response.setError(500, "Unable to modify data");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		}
		logger.info("History data update response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Replace PNC Vital Data entered by Nurse with the details entered
	 *            by Doctor
	 */
	@Operation(summary = "Update PNC beneficiary vitals")
	@PostMapping(value = { "/update/vitalScreen" })
	public ResponseEntity<String> updateVitalNurse(@RequestBody String requestObj) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("Vital data update request:" + requestObj);
		JsonObject jsnOBJ = JsonParser.parseString(requestObj).getAsJsonObject();
		int result = pncServiceImpl.updateBenVitalDetails(jsnOBJ);
		if (result > 0) {
			response.setResponse("Data updated successfully");
		} else {
			response.setError(500, "Unable to modify data");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		}
		logger.info("Vital data update response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Replace PNC Examination Data entered by Nurse with the details
	 *            entered by Doctor
	 */
	@Operation(summary = "Update PNC examination data")
	@PostMapping(value = { "/update/examinationScreen" })
	public ResponseEntity<String> updateGeneralOPDExaminationNurse(@RequestBody String requestObj) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("Examination data update request:" + requestObj);
		JsonObject jsnOBJ = JsonParser.parseString(requestObj).getAsJsonObject();
		int result = pncServiceImpl.updateBenExaminationDetails(jsnOBJ);
		if (result > 0) {
			response.setResponse("Data updated successfully");
		} else {
			response.setError(500, "Unable to modify data");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		}
		logger.info("Examination data update response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	@Operation(summary = "Update PNC doctor data")
	@PostMapping(value = { "/update/doctorData" })
	public ResponseEntity<String> updatePNCDoctorData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("Doctor data update request:" + requestObj);
		JsonObject jsnOBJ = JsonParser.parseString(requestObj).getAsJsonObject();
		Long result = pncServiceImpl.updatePNCDoctorData(jsnOBJ, Authorization);
		if (null != result && result > 0) {
			response.setResponse("Data updated successfully");
		} else {
			response.setError(500, "Unable to modify data");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		}
		logger.info("Doctor data update response:" + response);
		return ResponseEntity.ok(response.toString());
	}
}
