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
package com.iemr.hwc.controller.ncdscreening;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.service.ncdscreening.NCDSCreeningDoctorService;
import com.iemr.hwc.service.ncdscreening.NCDScreeningService;
import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @Objective Saving NCD Screening nurse data.
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/NCD", headers = "Authorization")
public class NCDScreeningController {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private NCDScreeningService ncdScreeningService;

	@Autowired
	private NCDSCreeningDoctorService ncdSCreeningDoctorService;

	/**
	 * @Objective Save NCD Screening data for nurse.
	 * @param JSON requestObj
	 * @return success or failure response
	 * @throws Exception 
	 */
	@CrossOrigin
	@ApiOperation(value = "Save NCD screening beneficiary data collected by nurse", consumes = "application/json", produces = "application/json")

	@RequestMapping(value = { "/save/nurseData" }, method = { RequestMethod.POST })
	public String saveBeneficiaryNCDScreeningDetails(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) throws Exception {

		logger.info("Request object for NCD Screening nurse data saving :" + requestObj);
		OutputResponse response = new OutputResponse();

		JsonObject jsonObject = new JsonObject();
		JsonParser jsonParser = new JsonParser();

		try {
			JsonElement jsonElement = jsonParser.parse(requestObj);
			jsonObject = jsonElement.getAsJsonObject();

			if (jsonObject != null) {
				String r = ncdScreeningService.saveNCDScreeningNurseData(jsonObject, Authorization);
				response.setResponse(r);
			} else {
				response.setError(5000, "Invalid request");
			}
		} catch (Exception e) {
			response.setError(5000, "Unable to save data");
			if (e.getMessage().equalsIgnoreCase("Error while booking slot.")) {
				JsonObject jsnOBJ = new JsonObject();
				JsonParser jsnParser = new JsonParser();
				JsonElement jsnElmnt = jsnParser.parse(requestObj);
				jsnOBJ = jsnElmnt.getAsJsonObject();
				ncdScreeningService.deleteVisitDetails(jsnOBJ);
				response.setError(5000, "Already booked slot, Please choose another slot");
			} else {
				response.setError(5000, "Unable to save data");
			}
		}
		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "Save NCD screening beneficiary data collected by doctor", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/doctorData" }, method = { RequestMethod.POST })
	public String saveBenNCDScreeningDoctorData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for NCD Screening doctor data saving :" + requestObj);

			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			if (jsnOBJ != null) {
				Long ncdCareRes = ncdScreeningService.saveDoctorData(jsnOBJ, Authorization);
				if (null != ncdCareRes && ncdCareRes > 0) {
					response.setResponse("Data saved successfully");
				} else {
					response.setResponse("Unable to save data");
				}

			} else {
				response.setResponse("Invalid request");
			}
		} catch (Exception e) {
			logger.error("Error while saving NCD Screening doctor data :" + e);
			if (e.getMessage().equalsIgnoreCase("Error while booking slot."))
				response.setError(5000, "Already booked slot, Please choose another slot");
			else
				response.setError(5000, "Unable to save data. " + e.getMessage());
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching NCD Screening nurse data.
	 * @param benRegID and benVisitID
	 * @return NCD Screening nurse data in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get NCD screening beneficiary visit details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/get/nurseData" }, method = { RequestMethod.POST })
	public String getNCDScreenigDetails(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {

		OutputResponse response = new OutputResponse();
		logger.info("Request obj to fetch nurse data :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.length() > 1) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String screeningDetails = ncdScreeningService.getNCDScreeningDetails(benRegID, visitCode);
				response.setResponse(screeningDetails);
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("NCD Screening nurse data fetch response :" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting NCD Screening data");
			logger.error("Error while getting NCD Screening data :" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get NCD screening visit count for beneficiary register id", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getNcdScreeningVisitCount/{beneficiaryRegID}" }, method = RequestMethod.GET)
	public String getNcdScreeningVisitCount(@PathVariable("beneficiaryRegID") Long beneficiaryRegID) {
		OutputResponse response = new OutputResponse();
		try {
			if (beneficiaryRegID != null) {
				String s = ncdScreeningService.getNcdScreeningVisitCnt(beneficiaryRegID);
				if (s != null)
					response.setResponse(s);
				else
					response.setError(5000, "Error while getting NCD screening Visit Count");

			} else {
				response.setError(5000, "Invalid request");
			}
		} catch (Exception e) {
			response.setError(5000, "Error while getting NCD screening Visit Count");
			logger.error("Error while getting NCD screening Visit Count" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary doctor details.
	 * @param comingRequest
	 * @return visit details in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get NCD screening beneficiary case record and referral", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenCaseRecordFromDoctorNCDScreening" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenCaseRecordFromDoctorNCDCare(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for NCD Screening doctor data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (null != obj && obj.length() > 1 && obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = ncdScreeningService.getBenCaseRecordFromDoctorNCDScreening(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid request");
				response.setError(5000, "Invalid request");
			}
			logger.info("NCD Screening doctor data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary doctor data");
			logger.error("Error while getting beneficiary doctor data :" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get NCD screening beneficiary visit details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenVisitDetailsFrmNurseNCDScreening" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenVisitDetailsFrmNurseGOPD(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request obj to fetch NCD screening visit details :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.length() > 1) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = ncdScreeningService.getBenVisitDetailsFrmNurseNCDScreening(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenVisitDetailsFrmNurseNCDScreening response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary visit data");
			logger.error("Error in getBenVisitDetailsFrmNurseNCDScreening:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get NCD screening beneficiary general OPD history", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenHistoryDetails" }, method = { RequestMethod.POST })

	public String getBenHistoryDetails(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenHistoryDetails request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String s = ncdScreeningService.getBenHistoryDetails(benRegID, visitCode);
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenHistoryDetails response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary history data");
			logger.error("Error in getBenHistoryDetails:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get NCD screening beneficiary vitals from general OPD nurse", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenVitalDetailsFrmNurse" }, method = { RequestMethod.POST })
	public String getBenVitalDetailsFrmNurse(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenVitalDetailsFrmNurse request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = ncdScreeningService.getBeneficiaryVitalDetails(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenVitalDetailsFrmNurse response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary vital data");
			logger.error("Error in getBenVitalDetailsFrmNurse:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get NCD screening IDRS details from general OPD nurse", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenIdrsDetailsFrmNurse" }, method = { RequestMethod.POST })
	public String getBenIdrsDetailsFrmNurse(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenIdrsDetailsFrmNurse request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = ncdScreeningService.getBenIdrsDetailsFrmNurse(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenIdrsDetailsFrmNurse response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary Idrs data");
			logger.error("Error in getBenIdrsDetailsFrmNurse:" + e);

		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get confirmed NCD diseases", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/fetchConfirmedScreeningDisease" }, method = { RequestMethod.POST })
	public String fetchConfirmedScreeningDisease(
			@ApiParam(value = "{\"beneficiaryRegId\":\"Long\"}") @RequestBody CommonUtilityClass commonUtilityClass) {
		OutputResponse response = new OutputResponse();
		try {
			if (commonUtilityClass.getBeneficiaryRegId() == null)
				throw new IEMRException("invalid/NULL beneficiary and/or visit details");
			String res = ncdScreeningService.fetchConfirmedScreeningDisease(commonUtilityClass.getBeneficiaryRegId());
			response.setResponse(res);
		} catch (Exception e) {
			response.setError(5000, "Error while getting Confirmed NCD diseases");
			logger.error("Error in fetchConfirmedScreeningDisease:" + e);
		}
		return response.toString();
	}

	// get NCD screening data - dia, htn, oral, breast, cervical
	@CrossOrigin()
	@ApiOperation(value = "Get NCD screening beneficiary data - dia, htn, oral, breast, cervical", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/get/ncd/screening/data" }, method = { RequestMethod.POST })
	public String getNCDScreeningData(
			@ApiParam(value = "{\"beneficiaryRegId\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody CommonUtilityClass commonUtilityClass) {

		OutputResponse response = new OutputResponse();
		try {

			if (commonUtilityClass.getBeneficiaryRegId() == null || commonUtilityClass.getVisitCode() == null)
				throw new IEMRException("invalid/NULL beneficiary and/or visit details");
			String screeningDetails = ncdScreeningService.getNCDScreeningData(commonUtilityClass.getBeneficiaryRegId(),
					commonUtilityClass.getVisitCode());
			response.setResponse(screeningDetails);

		} catch (Exception e) {
			response.setError(5000, "Error while getting captured NCD Screening data ");
			logger.error("Error while getting captured NCD Screening data :" + e);
		}
		return response.toStringWithSerializeNulls();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get NCD screening beneficiary CBAC details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getCbacDetails" }, method = { RequestMethod.POST })
	public String getCbacDetails(
			@ApiParam(value = "{\"beneficiaryRegId\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody CommonUtilityClass commonUtilityClass) {

		OutputResponse response = new OutputResponse();
		try {
			if (commonUtilityClass.getBeneficiaryRegId() == null || commonUtilityClass.getVisitCode() == null)
				throw new IEMRException("invalid/NULL beneficiary and/or visit details");
			String screeningDetails = ncdScreeningService.getCbacData(commonUtilityClass.getBeneficiaryRegId(),
					commonUtilityClass.getVisitCode());
			response.setResponse(screeningDetails);
		} catch (Exception e) {
			response.setError(5000, "Error while getting captured CBAC data ");
			logger.error("Error while getting captured CBAC data :" + e);
		}
		return response.toStringWithSerializeNulls();
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Replace NCD Screening Data entered by Nurse
	 * 
	 *            NOT using as of now
	 */
	@CrossOrigin
	@ApiOperation(value = "Update NCD screening beneficiary details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/nurseData" }, method = { RequestMethod.POST })
	public String updateBeneficiaryNCDScreeningDetails(@RequestBody String requestObj) {

		logger.info("Update NCDScreening Details request:" + requestObj);
		OutputResponse response = new OutputResponse();
		JsonObject jsonObject = new JsonObject();
		JsonParser jsonParser = new JsonParser();

		try {
			JsonElement jsonElement = jsonParser.parse(requestObj);
			jsonObject = jsonElement.getAsJsonObject();

			if (jsonObject != null) {
				Integer r = ncdScreeningService.updateNurseNCDScreeningDetails(jsonObject);
				if (r != null && r == 1) {
					response.setResponse("Data updated successfully");
				} else {
					response.setError(5000, "Unable to modify data");
				}
			} else {
				response.setError(5000, "Invalid request");
			}
		} catch (Exception e) {
			response.setError(5000, "Unable to modify data :" + e.getLocalizedMessage());
			logger.error("Error in updating beneficiary NCD screening data: " + e);
		}
		logger.info("Update NCDScreening Details response:" + response);
		return response.toString();
	}

	/*
	 * Updating the history WDF requirement
	 */
	@CrossOrigin
	@ApiOperation(value = "Update NCD screening beneficiary history", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/historyScreen" }, method = { RequestMethod.POST })
	public String updateHistoryNurse(@RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for history data updating :" + requestObj);

		JsonObject jsnOBJ = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElmnt = jsnParser.parse(requestObj);
		jsnOBJ = jsnElmnt.getAsJsonObject();

		try {
			int result = ncdScreeningService.UpdateNCDScreeningHistory(jsnOBJ);
			if (result > 0) {
				response.setResponse("Data updated successfully");
			} else {
				response.setError(500, "Unable to modify data");
			}
			logger.info("History data update Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Unable to modify data : " + e.getLocalizedMessage());
			logger.error("Error while updating history data :" + e);
		}

		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "Update NCD screening beneficiary vitals", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/vitalScreen" }, method = { RequestMethod.POST })
	public String updateVitalNurse(@RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for vital data updating :" + requestObj);

		JsonObject jsnOBJ = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElmnt = jsnParser.parse(requestObj);
		jsnOBJ = jsnElmnt.getAsJsonObject();

		try {
			int result = ncdScreeningService.updateBenVitalDetails(jsnOBJ);
			if (result > 0) {
				response.setResponse("Data updated successfully");
			} else {
				response.setError(500, "Unable to modify data");
			}
			logger.info("Vital data update Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Unable to modify data : " + e.getLocalizedMessage());
			logger.error("Error while updating vital data :" + e);
		}

		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "Update NCD screening beneficiary history", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/idrsScreen" }, method = { RequestMethod.POST })
	public String updateIDRSScreen(@RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for history data updating :" + requestObj);

		JsonObject jsnOBJ = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElmnt = jsnParser.parse(requestObj);
		jsnOBJ = jsnElmnt.getAsJsonObject();

		try {
			Long result = ncdScreeningService.UpdateIDRSScreen(jsnOBJ);
			if (result != null && result > 0) {
				response.setResponse("Data updated successfully");
			} else {
				response.setError(500, "Unable to modify data");
			}
			logger.info("IDRS data update Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Unable to modify data : " + e.getLocalizedMessage());
			logger.error("Error while updating history data :" + e);
		}

		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "Update NCD screening beneficiary case record and referral", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/doctorData" }, method = { RequestMethod.POST })
	public String updateDoctorData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for doctor data update :" + requestObj);

		try {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			int i = ncdSCreeningDoctorService.updateDoctorData(jsnOBJ, Authorization);
			if (i > 0)
				response.setResponse("Data updated successfully");
			else
				response.setError(5000, "Error in data update");
		} catch (Exception e) {
			response.setError(5000, "Unable to modify data : " + e.getLocalizedMessage());
			if (e.getMessage().equalsIgnoreCase("Error while booking slot."))
				response.setError(5000, "Already booked slot, Please choose another slot");
			else
				logger.error("Error while updating doctor data : " + e);
		}

		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "Update NCD screening beneficiary data", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/ncd/screening/data" }, method = { RequestMethod.POST })
	public String updateNCDScreeningData(@RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();

		JsonObject jsonObject = new JsonObject();
		JsonParser jsonParser = new JsonParser();
		try {
			JsonElement jsonElement = jsonParser.parse(requestObj);
			jsonObject = jsonElement.getAsJsonObject();

			if (jsonObject != null) {
				String r = ncdScreeningService.updateNCDScreeningData(jsonObject);
				response.setResponse(r);

			} else {
				response.setError(5000, "Invalid request");
			}

		} catch (Exception e) {
			response.setError(5000, "Unable to modify data : " + e.getLocalizedMessage());
			logger.error("Error in updating beneficiary NCD screening data : " + e);
		}
		return response.toString();
	}

}
