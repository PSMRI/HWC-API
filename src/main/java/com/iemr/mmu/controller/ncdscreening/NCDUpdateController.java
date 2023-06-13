/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.controller.ncdscreening;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.mmu.service.ncdscreening.NCDSCreeningDoctorService;
import com.iemr.mmu.service.ncdscreening.NCDScreeningService;
import com.iemr.mmu.service.ncdscreening.NCDScreeningServiceImpl;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author NA874500
 * @Objective Updating NCD Screening nurse data.
 * @Date 24-01-2018
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/NCD", headers = "Authorization")
public class NCDUpdateController {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private NCDScreeningServiceImpl ncdScreeningServiceImpl;

	@Autowired
	private NCDSCreeningDoctorService ncdSCreeningDoctorService;

	@Autowired
	private NCDScreeningService ncdScreeningService;

	@Autowired
	public void setNcdScreeningServiceImpl(NCDScreeningServiceImpl ncdScreeningServiceImpl) {
		this.ncdScreeningServiceImpl = ncdScreeningServiceImpl;
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
	@ApiOperation(value = "update Beneficiary NCD Screening Detail", consumes = "application/json", produces = "application/json")
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
				Integer r = ncdScreeningServiceImpl.updateNurseNCDScreeningDetails(jsonObject);
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
	 * Updating the history WDF requirement 9-12-2020
	 */
	@CrossOrigin
	@ApiOperation(value = "update History Data in Doctor screen", consumes = "application/json", produces = "application/json")
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
	@ApiOperation(value = "update NCD Screening Vital Data in Doctor screen", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/vitalScreen" }, method = { RequestMethod.POST })
	public String updateVitalNurse(@RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for vital data updating :" + requestObj);

		JsonObject jsnOBJ = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElmnt = jsnParser.parse(requestObj);
		jsnOBJ = jsnElmnt.getAsJsonObject();

		try {
			int result = ncdScreeningServiceImpl.updateBenVitalDetails(jsnOBJ);
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

	// Shubham Shekhar,11-12-2020,WDF
	@CrossOrigin
	@ApiOperation(value = "update History Data in Doctor screen", consumes = "application/json", produces = "application/json")
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

	// 14-12-2020
	@CrossOrigin
	@ApiOperation(value = "update Doctor Data", consumes = "application/json", produces = "application/json")
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
			logger.error("Error while updating doctor data : " + e);
		}

		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "update Beneficiary NCD Screening data", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/ncd/screening/data" }, method = { RequestMethod.POST })
	public String updateNCDScreeningData(@RequestBody String requestObj) {

		// logger.info("Update NCDScreening Details request:" + requestObj);
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
		// logger.info("Update NCDScreening Details response:" + response);
		return response.toString();
	}

}
