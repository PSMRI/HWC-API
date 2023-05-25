package com.iemr.mmu.controller.pnc;

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
import com.iemr.mmu.service.pnc.PNCServiceImpl;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author NA874500
 * @Objective Updating PNC nurse and doctor data
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/PNC", headers = "Authorization")
public class PNCUpdateController {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	private PNCServiceImpl pncServiceImpl;

	@Autowired
	public void setPncServiceImpl(PNCServiceImpl pncServiceImpl) {
		this.pncServiceImpl = pncServiceImpl;
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Replace PNC Care Data entered by Nurse with the details entered by
	 *            Doctor
	 */

	@CrossOrigin
	@ApiOperation(value = "update PNC care Data in Doctor screen", consumes = "application/json", produces = "application/json")
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
	@ApiOperation(value = "update History Data in Doctor screen", consumes = "application/json", produces = "application/json")
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
	@ApiOperation(value = "update PNC Vital Data in Doctor screen", consumes = "application/json", produces = "application/json")
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
	@ApiOperation(value = "update PNC Examination Data in Doctor screen", consumes = "application/json", produces = "application/json")
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
	@ApiOperation(value = "update PNC Doctor Data", consumes = "application/json", produces = "application/json")
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
			response.setError(5000, "Unable to modify data. " + e.getMessage());
			logger.error("Error while updating doctor data :" + e);
		}

		return response.toString();
	}
}
