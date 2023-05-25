package com.iemr.mmu.controller.cancerscreening;

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
import com.iemr.mmu.service.cancerScreening.CSServiceImpl;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author NE298657
 * @Objective Saving Cancer screening data for Nurse and Doctor both.
 * @Date 15-01-2018
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/CS-cancerScreening", headers = "Authorization")
public class CancerScreeningCreateController {
	private Logger logger = LoggerFactory.getLogger(CancerScreeningCreateController.class);

	private CSServiceImpl cSServiceImpl;

	@Autowired
	public void setCancerScreeningServiceImpl(CSServiceImpl cSServiceImpl) {
		this.cSServiceImpl = cSServiceImpl;
	}

	/**
	 * @Objective Save Cancer Screening data for nurse.
	 * @param JSON
	 *            requestObj
	 * @return success or failure response
	 */

	@CrossOrigin
	@ApiOperation(value = "Save cancer screening nurse data..", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/nurseData" }, method = { RequestMethod.POST })
	public String saveBenCancerScreeningNurseData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for CS nurse data saving :" + requestObj);

			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			if (jsnOBJ != null) {
				String nurseDataSaveSuccessFlag = cSServiceImpl.saveCancerScreeningNurseData(jsnOBJ, Authorization);
				response.setResponse(nurseDataSaveSuccessFlag);
				//if (nurseDataSaveSuccessFlag != null && nurseDataSaveSuccessFlag > 0) {
//					if (nurseDataSaveSuccessFlag == 1)
//						response.setResponse("Data saved successfully");
//					else if (nurseDataSaveSuccessFlag == 2)
//						response.setResponse("Data saved and MAMMOGRAM order created successfully");
//					else
//						response.setError(9999,
//								"Data saved successfully but 'error in MAMMOGRAM order creation';please contact administrator");
//				} else {
//					response.setError(5000, "Unable to save data");
//				}
			} else {
				response.setError(5000, "Invalid request");
			}

		} catch (Exception e) {
			logger.error("Error while saving beneficiary nurse data :" + e);
			response.setError(5000, "Unable to save data");
		}
		return response.toString();
	}

	/**
	 * @Objective Save Cancer Screening data for doctor.
	 * @param JSON
	 *            requestObj
	 * @return success or failure response
	 */

	@CrossOrigin
	@ApiOperation(value = "Save cancer screening doctor data..", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/doctorData" }, method = { RequestMethod.POST })
	public String saveBenCancerScreeningDoctorData(@RequestBody String requestObj,
			@RequestHeader String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for CS doctor data saving :" + requestObj);

			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			if (jsnOBJ != null) {
				Long csDocDataSaveSuccessFlag = cSServiceImpl.saveCancerScreeningDoctorData(jsnOBJ, Authorization);
				if (csDocDataSaveSuccessFlag != null && csDocDataSaveSuccessFlag > 0) {
					response.setResponse("Data saved successfully");
				} else {
					response.setError(5000, "Unable to save data");
				}
			} else {
				response.setError(5000, "Invalid request");
			}

		} catch (Exception e) {
			logger.error("Error while saving beneficiary doctor data :" + e);
			response.setError(5000, "Unable to save data");
		}
		return response.toString();
	}
}
