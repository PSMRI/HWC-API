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
package com.iemr.hwc.controller.generalOPD;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.service.generalOPD.GeneralOPDServiceImpl;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/***
 * 
 * @Objective Saving General OPD data for Nurse and Doctor.
 *
 */

@RestController
@RequestMapping(value = "/generalOPD", headers = "Authorization", consumes = "application/json", produces = "application/json")
public class GeneralOPDController {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private GeneralOPDServiceImpl generalOPDServiceImpl;

	@Autowired
	public void setGeneralOPDServiceImpl(GeneralOPDServiceImpl generalOPDServiceImpl) {
		this.generalOPDServiceImpl = generalOPDServiceImpl;
	}

	/**
	 * @Objective Save General OPD data for nurse.
	 * @param requestObj
	 * @return success or failure response
	 * @throws Exception
	 */
	@Operation(summary = "Save general OPD data collected by nurse")
	@PostMapping(value = { "/save/nurseData" })
	public ResponseEntity<String> saveBenGenOPDNurseData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) throws Exception {
		OutputResponse response = new OutputResponse();
		if (null != requestObj) {
			JsonObject jsnOBJ = JsonParser.parseString(requestObj).getAsJsonObject();
			logger.info("Request object for GeneralOPD nurse data saving :" + requestObj);
			String genOPDRes = generalOPDServiceImpl.saveNurseData(jsnOBJ, Authorization);
			response.setResponse(genOPDRes);
		} else {
			response.setResponse("Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * @Objective Save General OPD data for doctor.
	 * @param requestObj
	 * @return success or failure response
	 */
	@Operation(summary = "Save general OPD data collected by doctor")
	@PostMapping(value = { "/save/doctorData" })
	public ResponseEntity<String> saveBenGenOPDDoctorData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for GeneralOPD doctor data saving :" + requestObj);
		JsonObject jsnOBJ = JsonParser.parseString(requestObj).getAsJsonObject();
		if (jsnOBJ != null) {
			Long genOPDRes = generalOPDServiceImpl.saveDoctorData(jsnOBJ, Authorization);
			if (null != genOPDRes && genOPDRes > 0) {
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
	 * @Objective Fetching beneficiary visit details entered by nurse.
	 * @param comingRequest
	 * @return visit details in JSON format
	 */
	@Operation(summary = "Get general OPD beneficiary visit details")
	@PostMapping(value = { "/getBenVisitDetailsFrmNurseGOPD" })
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<String> getBenVisitDetailsFrmNurseGOPD(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest)
			throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("Request obj to fetch General OPD visit details :" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.length() > 1 && obj.has("benRegID") && obj.has("visitCode")) {
			Long benRegID = obj.getLong("benRegID");
			Long visitCode = obj.getLong("visitCode");
			String res = generalOPDServiceImpl.getBenVisitDetailsFrmNurseGOPD(benRegID, visitCode);
			response.setResponse(res);
		} else {
			logger.info("Invalid Request Data.");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("getBenDataFrmNurseScrnToDocScrnVisitDetails response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * @Objective Fetching beneficiary history details entered by nurse.
	 * @param comingRequest
	 * @return history details in JSON format
	 */
	@Operation(summary = "Get general OPD beneficiary history")
	@PostMapping(value = { "/getBenHistoryDetails" })
	public ResponseEntity<String> getBenHistoryDetails(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest)
			throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("getBenHistoryDetails request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("benRegID") && obj.has("visitCode")) {
			Long benRegID = obj.getLong("benRegID");
			Long visitCode = obj.getLong("visitCode");
			String s = generalOPDServiceImpl.getBenHistoryDetails(benRegID, visitCode);
			response.setResponse(s);
		} else {
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("getBenHistoryDetails response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * @Objective Fetching beneficiary vital details entered by nurse.
	 * @param comingRequest
	 * @return vital details in JSON format
	 */
	@Operation(summary = "Get general OPD beneficiary vitals")
	@PostMapping(value = { "/getBenVitalDetailsFrmNurse" })
	public ResponseEntity<String> getBenVitalDetailsFrmNurse(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest)
			throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("getBenVitalDetailsFrmNurse request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("benRegID") && obj.has("visitCode")) {
			Long benRegID = obj.getLong("benRegID");
			Long visitCode = obj.getLong("visitCode");
			String res = generalOPDServiceImpl.getBeneficiaryVitalDetails(benRegID, visitCode);
			response.setResponse(res);
		} else {
			logger.info("Invalid Request Data.");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("getBenVitalDetailsFrmNurse response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * @Objective Fetching beneficiary examination details entered by nurse.
	 * @param comingRequest
	 * @return examination details in JSON format
	 */
	@Operation(summary = "Get general OPD beneficiary examination details")
	@PostMapping(value = { "/getBenExaminationDetails" })
	public ResponseEntity<String> getBenExaminationDetails(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest)
			throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("getBenExaminationDetails request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("benRegID") && obj.has("visitCode")) {
			Long benRegID = obj.getLong("benRegID");
			Long visitCode = obj.getLong("visitCode");
			String s = generalOPDServiceImpl.getExaminationDetailsData(benRegID, visitCode);
			response.setResponse(s);
		} else {
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("getBenExaminationDetails response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * @Objective Fetching beneficiary doctor details.
	 * @param comingRequest
	 * @return doctor details in JSON format
	 */
	@Operation(summary = "Get general OPD beneficiary case record and referral")
	@PostMapping(value = { "/getBenCaseRecordFromDoctorGeneralOPD" })
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<String> getBenCaseRecordFromDoctorGeneralOPD(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest)
			throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("getBenCaseRecordFromDoctorGeneralOPD request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (null != obj && obj.length() > 1 && obj.has("benRegID") && obj.has("visitCode")) {
			Long benRegID = obj.getLong("benRegID");
			Long visitCode = obj.getLong("visitCode");
			String res = generalOPDServiceImpl.getBenCaseRecordFromDoctorGeneralOPD(benRegID, visitCode);
			response.setResponse(res);
		} else {
			logger.info("Invalid Request Data.");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("getBenCaseRecordFromDoctorGeneralOPD response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Replace General OPD History Data entered by Nurse with the details
	 *            entered by Doctor
	 */
	@Operation(summary = "Update beneficiary history")
	@PostMapping(value = { "/update/historyScreen" })
	public ResponseEntity<String> updateHistoryNurse(@RequestBody String requestObj) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for history data updating :" + requestObj);
		JsonObject jsnOBJ = JsonParser.parseString(requestObj).getAsJsonObject();
		int result = generalOPDServiceImpl.updateBenHistoryDetails(jsnOBJ);
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
	 * @objective Replace General OPD Vital Data entered by Nurse with the details
	 *            entered by Doctor
	 */
	@Operation(summary = "Update general OPD beneficiary vitals")
	@PostMapping(value = { "/update/vitalScreen" })
	public ResponseEntity<String> updateVitalNurse(@RequestBody String requestObj) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for vital data updating :" + requestObj);
		JsonObject jsnOBJ = JsonParser.parseString(requestObj).getAsJsonObject();
		int result = generalOPDServiceImpl.updateBenVitalDetails(jsnOBJ);
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
	 * @objective Replace General OPD Examination Data entered by Nurse with the
	 *            details entered by Doctor
	 */
	@Operation(summary = "Update general OPD beneficiary examination data")
	@PostMapping(value = { "/update/examinationScreen" })
	public ResponseEntity<String> updateGeneralOPDExaminationNurse(@RequestBody String requestObj) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for examination data updating :" + requestObj);
		JsonObject jsnOBJ = JsonParser.parseString(requestObj).getAsJsonObject();
		int result = generalOPDServiceImpl.updateBenExaminationDetails(jsnOBJ);
		if (result > 0) {
			response.setResponse("Data updated successfully");
		} else {
			response.setError(500, "Unable to modify data");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		}
		logger.info("Examination data update response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Replace General OPD doctor data for the doctor next visit
	 */
	@Operation(summary = "Update general OPD beneficiary case record and referral")
	@PostMapping(value = { "/update/doctorData" })
	public ResponseEntity<String> updateGeneralOPDDoctorData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for doctor data updating :" + requestObj);
		JsonObject jsnOBJ = JsonParser.parseString(requestObj).getAsJsonObject();
		Long result = generalOPDServiceImpl.updateGeneralOPDDoctorData(jsnOBJ, Authorization);
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
