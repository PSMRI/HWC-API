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
package com.iemr.hwc.controller.anc;

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

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.data.anc.HrpStatusAndReasonsRequestModel;
import com.iemr.hwc.data.anc.SysObstetricExamination;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.service.anc.ANCService;
import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.v3.oas.annotations.Operation;

/**
 * 
 * @Objective Saving ANC data for Nurse and Doctor.
 *
 */

@CrossOrigin
@RestController
@RequestMapping(value = "/ANC", headers = "Authorization", consumes = "application/json", produces = "application/json")
public class AntenatalCareController {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private ANCService ancService;

	/**
	 * @Objective Save ANC data for nurse.
	 * @param JSON requestObj
	 * @return success or failure response
	 * @throws Exception
	 */

	@CrossOrigin
	@Operation(summary = "Save ANC nurse data")
	@PostMapping(value = { "/save/nurseData" })
	public String saveBenANCNurseData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) throws Exception {
		OutputResponse response = new OutputResponse();

		if (null != requestObj) {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			try {

				logger.info("Request object for ANC nurse data saving :" + requestObj);

				if (jsnOBJ != null) {
					String ancRes = ancService.saveANCNurseData(jsnOBJ, Authorization);
					response.setResponse(ancRes);
				} else {
					response.setError(5000, "Invalid request");
				}

			} catch (Exception e) {
				logger.error("Error while saving nurse data:" + e.getMessage());
				ancService.deleteVisitDetails(jsnOBJ);
				response.setError(5000, e.getMessage());
			}
		}
		return response.toString();
	}

	/**
	 * @Objective Save ANC data for doctor.
	 * @param JSON requestObj
	 * @return success or failure response
	 */
	@CrossOrigin
	@Operation(summary = "Save ANC doctor data")
	@PostMapping(value = { "/save/doctorData" })
	public String saveBenANCDoctorData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for ANC doctor data saving :" + requestObj);

			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();
			if (jsnOBJ != null) {
				Long r = ancService.saveANCDoctorData(jsnOBJ, Authorization);
				if (r != null && r > 0) {
					response.setResponse("Data saved successfully");
				} else {
					response.setError(5000, "Unable to save data");
				}
			} else {
				response.setError(5000, "Invalid request");
			}

		} catch (Exception e) {
			logger.error("Error while saving doctor data:" + e.getMessage());
			response.setError(5000, e.getMessage());
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary visit details entered by nurse.
	 * @param benRegID and benVisitID
	 * @return visit details in JSON format
	 */

	@CrossOrigin()
	@Operation(summary = "Get ANC beneficiary visit details from nurse")
	@PostMapping(value = { "/getBenVisitDetailsFrmNurseANC" })
	@Transactional(rollbackFor = Exception.class)
	public String getBenVisitDetailsFrmNurseANC(
			@Param(value = "{\"benRegID\":\"Long\", \"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for ANC visit data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.length() > 1) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");
				String res = ancService.getBenVisitDetailsFrmNurseANC(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid request");
				response.setError(5000, "Invalid request");
			}
			logger.info("ANC visit data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary visit data");
			logger.error("Error while getting beneficiary visit data:" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary anc care details entered by nurse.
	 * @param benRegID and benVisitID
	 * @return anc care details in JSON format
	 */
	@CrossOrigin()
	@Operation(summary = "Get ANC beneficiary details from nurse")
	@PostMapping(value = { "/getBenANCDetailsFrmNurseANC" })
	@Transactional(rollbackFor = Exception.class)
	public String getBenANCDetailsFrmNurseANC(
			@Param(value = "{\"benRegID\":\"Long\", \"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for ANC Care data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			Long visitCode = null;
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");

				if (obj.has("visitCode") && !obj.isNull("visitCode") && obj.get("visitCode") != null)
					visitCode = obj.getLong("visitCode");

				String res = ancService.getBenANCDetailsFrmNurseANC(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid request");
				response.setError(5000, "Invalid request, Beneficiary information is NULL");
			}
			logger.info("ANC Care data fetching response :" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary ANC care data");
			logger.error("Error while getting beneficiary ANC care data:" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary history details entered by nurse.
	 * @param benRegID and benVisitID
	 * @return history details in JSON format
	 */
	@CrossOrigin()
	@Operation(summary = "Get ANC beneficiary history from nurse")
	@PostMapping(value = { "/getBenANCHistoryDetails" })

	public String getBenANCHistoryDetails(
			@Param(value = "{\"benRegID\":\"Long\", \"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for ANC history data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String s = ancService.getBenANCHistoryDetails(benRegID, visitCode);
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("ANC history data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary history data");
			logger.error("Error while getting beneficiary history data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary vital details entered by nurse.
	 * @param benRegID and benVisitID
	 * @return vital details in JSON format
	 */
	@CrossOrigin()
	@Operation(summary = "Get ANC beneficiary vitals from nurse")
	@PostMapping(value = { "/getBenANCVitalDetailsFrmNurseANC" })
	public String getBenANCVitalDetailsFrmNurseANC(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for ANC vital data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = ancService.getBeneficiaryVitalDetails(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid request");
				response.setError(5000, "Invalid request");
			}
			logger.info("ANC vital data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary vital data");
			logger.error("Error while getting beneficiary vital data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary examination details entered by nurse.
	 * @param benRegID and benVisitID
	 * @return examination details in JSON format
	 */
	@CrossOrigin()
	@Operation(summary = "Get ANC beneficiary examination details from nurse")
	@PostMapping(value = { "/getBenExaminationDetailsANC" })

	public String getBenExaminationDetailsANC(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for ANC examination data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String s = ancService.getANCExaminationDetailsData(benRegID, visitCode);
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("ANC examination data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary examination data");
			logger.error("Error while getting beneficiary examination data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary details entered by doctor.
	 * @param benRegID and benVisitID
	 * @return doctor entered details in JSON format
	 */
	@CrossOrigin()
	@Operation(summary = "Get ANC beneficiary case record")
	@PostMapping(value = { "/getBenCaseRecordFromDoctorANC" })
	@Transactional(rollbackFor = Exception.class)
	public String getBenCaseRecordFromDoctorANC(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for doctor data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (null != obj && obj.length() > 1 && obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = ancService.getBenCaseRecordFromDoctorANC(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid request");
				response.setError(5000, "Invalid request");
			}
			logger.info("ANC doctor data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary doctor data");
			logger.error("Error while getting beneficiary doctor data :" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@Operation(summary = "Check high risk pregnancy status for ANC beneficiary")
	@PostMapping(value = { "/getHRPStatus" })
	@Transactional(rollbackFor = Exception.class)
	public String getHRPStatus(@RequestBody HrpStatusAndReasonsRequestModel hrpStatusAndReasonsRequestModel) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for doctor data fetching :" + hrpStatusAndReasonsRequestModel.toString());
		try {

			String res = ancService.getHRPStatus(hrpStatusAndReasonsRequestModel);
			response.setResponse(res);

		} catch (Exception e) {
			response.setError(5000, "error in getting HRP status");
			logger.error("error in getting HRP status : " + e);
		}
		return response.toString();
	}

	// changes for get HRP information - follow-up, can be used at other places also
	@Autowired
	private ANCService aNCService;

	@CrossOrigin()
	@Operation(summary = "Get high risk pregnancy information (status and reason) from obstetric history "
			+ "as per the latest visit")
	@PostMapping(value = { "/getHrpInformation" })
	@Transactional(rollbackFor = Exception.class)
	public String getHrpInformation(
			@Param(value = "{\"beneficiaryRegId\":\"Long\"}") @RequestBody CommonUtilityClass commonUtilityClass) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for getHrpInformation :" + commonUtilityClass.toString());
		try {

			SysObstetricExamination sysObstetricExamination = aNCService
					.getHRPInformation(commonUtilityClass.getBeneficiaryRegId());
			if (sysObstetricExamination != null)
				response.setResponse(new Gson().toJson(sysObstetricExamination));
			else
				response.setResponse("no information found");
		} catch (IEMRException e) {
			response.setError(5000, "error in getting HRP information : " + e.getLocalizedMessage());
			logger.error("error in getting HRP information : " + e);
		}
		return response.toString();
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Replace ANC Care Data entered by Nurse with the details entered by
	 *            Doctor
	 */

	@CrossOrigin
	@Operation(summary = "Update ANC beneficiary data")
	@PostMapping(value = { "/update/ANCScreen" })
	public String updateANCCareNurse(@RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for ANC Care data updating :" + requestObj);

		JsonObject jsnOBJ = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElmnt = jsnParser.parse(requestObj);
		jsnOBJ = jsnElmnt.getAsJsonObject();

		try {
			int result = ancService.updateBenANCDetails(jsnOBJ);
			if (result > 0) {
				response.setResponse("Data updated successfully");
			} else {
				response.setError(500, "Unable to modify data");
			}
			logger.info("ANC Care data update Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Unable to modify data");
			logger.error("Error while updating beneficiary ANC care data :" + e);
		}

		return response.toString();
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Replace ANC History Data entered by Nurse with the details entered
	 *            by Doctor
	 */

	@CrossOrigin
	@Operation(summary = "Update ANC beneficiary history")
	@PostMapping(value = { "/update/historyScreen" })
	public String updateANCHistoryNurse(@RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for ANC history data updating :" + requestObj);

		JsonObject jsnOBJ = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElmnt = jsnParser.parse(requestObj);
		jsnOBJ = jsnElmnt.getAsJsonObject();

		try {
			int result = ancService.updateBenANCHistoryDetails(jsnOBJ);
			if (result > 0) {
				response.setResponse("Data updated successfully");
			} else {
				response.setError(500, "Unable to modify data");
			}
			logger.info("ANC history data update Response:" + response);
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
	 * @objective Replace ANC Vital Data entered by Nurse with the details entered
	 *            by Doctor
	 */

	@CrossOrigin
	@Operation(summary = "Update ANC beneficiary vitals")
	@PostMapping(value = { "/update/vitalScreen" })
	public String updateANCVitalNurse(@RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for ANC Vital data updating :" + requestObj);

		JsonObject jsnOBJ = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElmnt = jsnParser.parse(requestObj);
		jsnOBJ = jsnElmnt.getAsJsonObject();

		try {
			int result = ancService.updateBenANCVitalDetails(jsnOBJ);
			if (result > 0) {
				response.setResponse("Data updated successfully");
			} else {
				response.setError(500, "Unable to modify data");
			}
			logger.info("ANC vital data update Response:" + response);
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
	 * @objective Replace ANC History Data entered by Nurse with the details entered
	 *            by Doctor
	 */

	@CrossOrigin
	@Operation(summary = "Update ANC examination data")
	@PostMapping(value = { "/update/examinationScreen" })
	public String updateANCExaminationNurse(@RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for ANC examination data updating :" + requestObj);

		JsonObject jsnOBJ = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElmnt = jsnParser.parse(requestObj);
		jsnOBJ = jsnElmnt.getAsJsonObject();

		try {
			int result = ancService.updateBenANCExaminationDetails(jsnOBJ);
			if (result > 0) {
				response.setResponse("Data updated successfully");
			} else {
				response.setError(500, "Unable to modify data");
			}
			logger.info("ANC examination data update Response:" + response);
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
	 * @objective Replace ANC doctor data for the doctor next visit
	 * 
	 */
	@CrossOrigin
	@Operation(summary = "Update ANC doctor data")
	@PostMapping(value = { "/update/doctorData" })
	public String updateANCDoctorData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for ANC doctor data updating :" + requestObj);

		JsonObject jsnOBJ = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElmnt = jsnParser.parse(requestObj);
		jsnOBJ = jsnElmnt.getAsJsonObject();

		try {
			Long result = ancService.updateANCDoctorData(jsnOBJ, Authorization);
			if (null != result && result > 0) {
				response.setResponse("Data updated successfully");
			} else {
				response.setError(500, "Unable to modify data");
			}
			logger.info("ANC doctor data update Response:" + response);
		} catch (Exception e) {
			logger.error("Unable to modify data. " + e.getMessage());
			response.setError(5000, e.getMessage());
		}

		return response.toString();
	}

}
