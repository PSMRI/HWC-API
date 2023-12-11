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
import com.iemr.hwc.service.pnc.PNCServiceImpl;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @Objective Saving PNC nurse and doctor data
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/PNC", headers = "Authorization")
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

	@CrossOrigin
	@ApiOperation(value = "Save PNC nurse data", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/nurseData" }, method = { RequestMethod.POST })
	public String saveBenPNCNurseData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) throws Exception {
		OutputResponse response = new OutputResponse();

		if (null != requestObj) {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			try {
				logger.info("Request object for PNC nurse data saving :" + requestObj);

				if (jsnOBJ != null) {
					String ancRes = pncServiceImpl.savePNCNurseData(jsnOBJ, Authorization);
					response.setResponse(ancRes);
				} else {
					response.setError(5000, "Invalid request");
				}

			} catch (Exception e) {
				logger.error("Error while saving nurse data :" + e.getMessage());
				pncServiceImpl.deleteVisitDetails(jsnOBJ);
				response.setError(5000, e.getMessage());
			}
		}
		return response.toString();
	}

	/**
	 * @Objective Saving PNC doctor data
	 * @param requestObj
	 * @return success or failure response
	 */

	@CrossOrigin
	@ApiOperation(value = "Save PNC doctor data", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/doctorData" }, method = { RequestMethod.POST })
	public String saveBenPNCDoctorData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for PNC doctor data saving :" + requestObj);

			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();
			if (jsnOBJ != null) {
				Long r = pncServiceImpl.savePNCDoctorData(jsnOBJ, Authorization);
				if (r != null && r > 0) {
					response.setResponse("Data saved successfully");
				} else {
					response.setError(5000, "Unable to save data");
				}
			} else {
				response.setError(5000, "Invalid request");
			}

		} catch (Exception e) {
			logger.error("Error while saving doctor data :" + e.getMessage());
			response.setError(5000, e.getMessage());
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching Beneficiary visit data entered by nurse
	 * @param comingRequest
	 * @return visit details in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get PNC beneficiary visit details from nurse", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenVisitDetailsFrmNursePNC" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenVisitDetailsFrmNursePNC(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("PNC visit data fetch request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.length() > 1) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = pncServiceImpl.getBenVisitDetailsFrmNursePNC(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid request");
				response.setError(5000, "Invalid request");
			}
			logger.info("PNC visit data fetch response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary visit data");
			logger.error("Error while getting beneficiary visit data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching Beneficiary PNC Care data entered by nurse
	 * @param comingRequest
	 * @return PNC Care data in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get PNC beneficiary details from nurse", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenPNCDetailsFrmNursePNC" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenPNCDetailsFrmNursePNC(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("PNC Care data fetch request:" + comingRequest);
		try {
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
			}
			logger.info("PNC Care data fetch response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary PNC Care data");
			logger.error("Error while getting beneficiary PNC Care data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching Beneficiary history data entered by nurse
	 * @param comingRequest
	 * @return history data in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get PNC bneficiary history from nurse", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenHistoryDetails" }, method = { RequestMethod.POST })

	public String getBenHistoryDetails(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("History data fetch request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String s = pncServiceImpl.getBenHistoryDetails(benRegID, visitCode);
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("History data fetch response :" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary history data");
			logger.error("Error while getting beneficiary history data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching Beneficiary vital data entered by nurse
	 * @param comingRequest
	 * @return vital data in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get PNC beneficiary vitals from nurse", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenVitalDetailsFrmNurse" }, method = { RequestMethod.POST })
	public String getBenVitalDetailsFrmNurse(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("vital data fetch request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = pncServiceImpl.getBeneficiaryVitalDetails(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid request");
				response.setError(5000, "Invalid request");
			}
			logger.info("Vital data fetch response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary vital data");
			logger.error("Error while getting beneficiary vital data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching Beneficiary examination data entered by nurse
	 * @param comingRequest
	 * @return examination data in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get PNC beneficiary examination details from nurse", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenExaminationDetailsPNC" }, method = { RequestMethod.POST })

	public String getBenExaminationDetailsPNC(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("PNC examination data fetch request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String s = pncServiceImpl.getPNCExaminationDetailsData(benRegID, visitCode);
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("Examination data fetch response :" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary examination data");
			logger.error("Error while getting beneficiary examination data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching Beneficiary doctor data
	 * @param comingRequest
	 * @return doctor data in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get PNC beneficiary case record", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenCaseRecordFromDoctorPNC" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenCaseRecordFromDoctorPNC(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("PNC doctor data fetch request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (null != obj && obj.length() > 1 && obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = pncServiceImpl.getBenCaseRecordFromDoctorPNC(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid request");
				response.setError(5000, "Invalid request");
			}
			logger.info("Doctor data fetch response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary doctor data");
			logger.error("Error while getting beneficiary doctor data:" + e);
		}
		return response.toString();
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Replace PNC Care Data entered by Nurse with the details entered by
	 *            Doctor
	 */

	@CrossOrigin
	@ApiOperation(value = "Update PNC doctor data", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/PNCScreen" }, method = { RequestMethod.POST })
	public String updatePNCCareNurse(@RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();
		logger.info("PNC Care data update request:" + requestObj);

		JsonObject jsnOBJ = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElmnt = jsnParser.parse(requestObj);
		jsnOBJ = jsnElmnt.getAsJsonObject();

		try {
			int result = pncServiceImpl.updateBenPNCDetails(jsnOBJ);
			if (result > 0) {
				response.setResponse("Data updated successfully");
			} else {
				response.setError(500, "Unable to modify data");
			}
			logger.info("PNC Care data update response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Unable to modify data");
			logger.error("Error while updating PNC Care :" + e);
		}

		return response.toString();
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Replace PNC History Data entered by Nurse with the details entered
	 *            by Doctor
	 */

	@CrossOrigin
	@ApiOperation(value = "Update PNC beneficiary history", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/historyScreen" }, method = { RequestMethod.POST })
	public String updateHistoryNurse(@RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();
		logger.info("History data update request:" + requestObj);

		JsonObject jsnOBJ = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElmnt = jsnParser.parse(requestObj);
		jsnOBJ = jsnElmnt.getAsJsonObject();

		try {
			int result = pncServiceImpl.updateBenHistoryDetails(jsnOBJ);
			if (result > 0) {
				response.setResponse("Data updated successfully");
			} else {
				response.setError(500, "Unable to modify data");
			}
			logger.info("History data update response:" + response);
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
	 * @objective Replace PNC Vital Data entered by Nurse with the details entered
	 *            by Doctor
	 */

	@CrossOrigin
	@ApiOperation(value = "Update PNC beneficiary vitals", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/vitalScreen" }, method = { RequestMethod.POST })
	public String updateVitalNurse(@RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();
		logger.info("Vital data update request:" + requestObj);

		JsonObject jsnOBJ = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElmnt = jsnParser.parse(requestObj);
		jsnOBJ = jsnElmnt.getAsJsonObject();

		try {
			int result = pncServiceImpl.updateBenVitalDetails(jsnOBJ);
			if (result > 0) {
				response.setResponse("Data updated successfully");
			} else {
				response.setError(500, "Unable to modify data");
			}
			logger.info("Vital data update response:" + response);
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
	 * @objective Replace PNC Examination Data entered by Nurse with the details
	 *            entered by Doctor
	 */

	@CrossOrigin
	@ApiOperation(value = "Update PNC examination data", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/examinationScreen" }, method = { RequestMethod.POST })
	public String updateGeneralOPDExaminationNurse(@RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();
		logger.info("Examination data update request:" + requestObj);

		JsonObject jsnOBJ = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElmnt = jsnParser.parse(requestObj);
		jsnOBJ = jsnElmnt.getAsJsonObject();

		try {
			int result = pncServiceImpl.updateBenExaminationDetails(jsnOBJ);
			if (result > 0) {
				response.setResponse("Data updated successfully");
			} else {
				response.setError(500, "Unable to modify data");
			}
			logger.info("Examination data update response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Unable to modify data");
			logger.error("Error while updating examination data :" + e);
		}

		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "Update PNC doctor data", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/doctorData" }, method = { RequestMethod.POST })
	public String updatePNCDoctorData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {

		OutputResponse response = new OutputResponse();
		logger.info("Doctor data update request:" + requestObj);

		JsonObject jsnOBJ = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElmnt = jsnParser.parse(requestObj);
		jsnOBJ = jsnElmnt.getAsJsonObject();

		try {
			Long result = pncServiceImpl.updatePNCDoctorData(jsnOBJ, Authorization);
			if (null != result && result > 0) {
				response.setResponse("Data updated successfully");
			} else {
				response.setError(500, "Unable to modify data");
			}
			logger.info("Doctor data update response:" + response);
		} catch (Exception e) {
			logger.error("Unable to modify data. " + e.getMessage());
			response.setError(5000, e.getMessage());
		}

		return response.toString();
	}
}
