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

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.service.generalOPD.GeneralOPDServiceImpl;
import com.iemr.hwc.utils.response.OutputResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import io.swagger.v3.oas.annotations.Operation;

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
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			try {
				if (jsnOBJ != null) {
					String genOPDRes = generalOPDServiceImpl.saveNurseData(jsnOBJ, Authorization);
					response.setResponse(genOPDRes);
				} else {
					response.setError(OutputResponse.BAD_REQUEST, "Invalid request");
				}
			} catch (Exception e) {
				logger.error("Error in nurse data saving: {}", e.getMessage());
				generalOPDServiceImpl.deleteVisitDetails(jsnOBJ);
				response.setError(5000, e.getMessage());
			}
		}
		return response.toStringWithHttpStatus();
	}

	/**
	 * @Objective Save General OPD data for doctor.
	 * @param requestObj
	 * @return success or failure response
	 */
	@Operation(summary = "Save general OPD data collected by doctor")
	@PostMapping(value = { "/save/doctorData" })
	public ResponseEntity<String> saveBenGenOPDDoctorData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			if (jsnOBJ != null) {
				Long genOPDRes = generalOPDServiceImpl.saveDoctorData(jsnOBJ, Authorization);
				if (null != genOPDRes && genOPDRes > 0) {
					List<Long> prescribedDrugIDs = new ArrayList<>();
					if (jsnOBJ.has("savedDrugIDs") && !jsnOBJ.get("savedDrugIDs").isJsonNull()) {
						JsonArray drugIDsArray = jsnOBJ.getAsJsonArray("savedDrugIDs");
						for (int j = 0; j < drugIDsArray.size(); j++) {
							prescribedDrugIDs.add(drugIDsArray.get(j).getAsLong());
						}
					}
					Map<String, Object> responseData = new HashMap<>();
					responseData.put("message", "Data saved successfully");
					responseData.put("prescribedDrugIDs", prescribedDrugIDs);
					response.setResponse(new Gson().toJson(responseData));
				} else {
					response.setError(OutputResponse.GENERIC_FAILURE, "Unable to save data");
				}
			} else {
				response.setError(OutputResponse.BAD_REQUEST, "Invalid request");
			}
		} catch (Exception e) {
			logger.error("Error in doctor data saving: {}", e.getMessage());
			response.setError(5000, e.getMessage());
		}
		return response.toStringWithHttpStatus();
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
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.length() > 1) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");
				String res = generalOPDServiceImpl.getBenVisitDetailsFrmNurseGOPD(benRegID, visitCode);
				response.setResponse(res);
			} else {
				response.setError(OutputResponse.BAD_REQUEST, "Invalid request");
			}
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary visit data");
			logger.error("Error in getBenVisitDetailsFrmNurseGOPD: {}", e.getMessage());
		}
		return response.toStringWithHttpStatus();
	}

	/**
	 * @Objective Fetching beneficiary history details entered by nurse.
	 * @param comingRequest
	 * @return history details in JSON format
	 */
	@Operation(summary = "Get general OPD beneficiary history")
	@PostMapping(value = { "/getBenHistoryDetails" })
	public ResponseEntity<String> getBenHistoryDetails(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");
				String s = generalOPDServiceImpl.getBenHistoryDetails(benRegID, visitCode);
				response.setResponse(s);
			} else {
				response.setError(OutputResponse.BAD_REQUEST, "Invalid request");
			}
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary history data");
			logger.error("Error in getBenHistoryDetails: {}", e.getMessage());
		}
		return response.toStringWithHttpStatus();
	}

	/**
	 * @Objective Fetching beneficiary vital details entered by nurse.
	 * @param comingRequest
	 * @return vital details in JSON format
	 */
	@Operation(summary = "Get general OPD beneficiary vitals")
	@PostMapping(value = { "/getBenVitalDetailsFrmNurse" })
	public ResponseEntity<String> getBenVitalDetailsFrmNurse(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");
				String res = generalOPDServiceImpl.getBeneficiaryVitalDetails(benRegID, visitCode);
				response.setResponse(res);
			} else {
				response.setError(OutputResponse.BAD_REQUEST, "Invalid request");
			}
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary vital data");
			logger.error("Error in getBenVitalDetailsFrmNurse: {}", e.getMessage());
		}
		return response.toStringWithHttpStatus();
	}

	/**
	 * @Objective Fetching beneficiary examination details entered by nurse.
	 * @param comingRequest
	 * @return examination details in JSON format
	 */
	@Operation(summary = "Get general OPD beneficiary examination details")
	@PostMapping(value = { "/getBenExaminationDetails" })
	public ResponseEntity<String> getBenExaminationDetails(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");
				String s = generalOPDServiceImpl.getExaminationDetailsData(benRegID, visitCode);
				response.setResponse(s);
			} else {
				response.setError(OutputResponse.BAD_REQUEST, "Invalid request");
			}
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary examination data");
			logger.error("Error in getBenExaminationDetails: {}", e.getMessage());
		}
		return response.toStringWithHttpStatus();
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
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (null != obj && obj.length() > 1 && obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");
				String res = generalOPDServiceImpl.getBenCaseRecordFromDoctorGeneralOPD(benRegID, visitCode);
				response.setResponse(res);
			} else {
				response.setError(OutputResponse.BAD_REQUEST, "Invalid request");
			}
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary doctor data");
			logger.error("Error in getBenCaseRecordFromDoctorGeneralOPD: {}", e.getMessage());
		}
		return response.toStringWithHttpStatus();
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
	public ResponseEntity<String> updateHistoryNurse(@RequestBody String requestObj) {
		OutputResponse response = new OutputResponse();
		JsonObject jsnOBJ = new JsonParser().parse(requestObj).getAsJsonObject();
		try {
			int result = generalOPDServiceImpl.updateBenHistoryDetails(jsnOBJ);
			if (result > 0) {
				response.setResponse("Data updated successfully");
			} else {
				response.setError(OutputResponse.GENERIC_FAILURE, "Unable to modify data");
			}
		} catch (Exception e) {
			response.setError(5000, "Unable to modify data");
			logger.error("Error while updating history data: {}", e.getMessage());
		}
		return response.toStringWithHttpStatus();
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
	public ResponseEntity<String> updateVitalNurse(@RequestBody String requestObj) {
		OutputResponse response = new OutputResponse();
		JsonObject jsnOBJ = new JsonParser().parse(requestObj).getAsJsonObject();
		try {
			int result = generalOPDServiceImpl.updateBenVitalDetails(jsnOBJ);
			if (result > 0) {
				response.setResponse("Data updated successfully");
			} else {
				response.setError(OutputResponse.GENERIC_FAILURE, "Unable to modify data");
			}
		} catch (Exception e) {
			response.setError(5000, "Unable to modify data");
			logger.error("Error while updating vital data: {}", e.getMessage());
		}
		return response.toStringWithHttpStatus();
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
	public ResponseEntity<String> updateGeneralOPDExaminationNurse(@RequestBody String requestObj) {
		OutputResponse response = new OutputResponse();
		JsonObject jsnOBJ = new JsonParser().parse(requestObj).getAsJsonObject();
		try {
			int result = generalOPDServiceImpl.updateBenExaminationDetails(jsnOBJ);
			if (result > 0) {
				response.setResponse("Data updated successfully");
			} else {
				response.setError(OutputResponse.GENERIC_FAILURE, "Unable to modify data");
			}
		} catch (Exception e) {
			response.setError(5000, "Unable to modify data");
			logger.error("Error while updating examination data: {}", e.getMessage());
		}
		return response.toStringWithHttpStatus();
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
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		JsonObject jsnOBJ = new JsonParser().parse(requestObj).getAsJsonObject();
		try {
			Long result = generalOPDServiceImpl.updateGeneralOPDDoctorData(jsnOBJ, Authorization);
			if (null != result && result > 0) {
				List<Long> prescribedDrugIDs = new ArrayList<>();
				if (jsnOBJ.has("savedDrugIDs") && !jsnOBJ.get("savedDrugIDs").isJsonNull()) {
					JsonArray drugIDsArray = jsnOBJ.getAsJsonArray("savedDrugIDs");
					for (int j = 0; j < drugIDsArray.size(); j++) {
						prescribedDrugIDs.add(drugIDsArray.get(j).getAsLong());
					}
				}
				Map<String, Object> responseData = new HashMap<>();
				responseData.put("message", "Data updated successfully");
				responseData.put("prescribedDrugIDs", prescribedDrugIDs);
				response.setResponse(new Gson().toJson(responseData));
			} else {
				response.setError(OutputResponse.GENERIC_FAILURE, "Unable to modify data");
			}
		} catch (Exception e) {
			logger.error("Unable to modify doctor data: {}", e.getMessage());
			response.setError(5000, e.getMessage());
		}
		return response.toStringWithHttpStatus();
	}

}
