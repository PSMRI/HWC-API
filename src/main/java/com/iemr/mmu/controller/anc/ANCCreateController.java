/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.controller.anc;

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
import com.iemr.mmu.service.anc.ANCServiceImpl;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author NA874500
 * @Objective Saving ANC data for Nurse and Doctor.
 * @Date 19-01-2018
 *
 */


@CrossOrigin
@RestController
@RequestMapping(value = "/ANC", headers = "Authorization")
public class ANCCreateController {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private ANCServiceImpl ancServiceImpl;

	@Autowired
	public void setAncServiceImpl(ANCServiceImpl ancServiceImpl) {
		this.ancServiceImpl = ancServiceImpl;
	}

	/**
	 * @Objective Save ANC data for nurse.
	 * @param JSON
	 *            requestObj
	 * @return success or failure response
	 */

	@CrossOrigin
	@ApiOperation(value = "Save ANC nurse data..", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/nurseData" }, method = { RequestMethod.POST })
	public String saveBenANCNurseData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			
			logger.info("Request object for ANC nurse data saving :" + requestObj);

			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			if (jsnOBJ != null) {
				String ancRes = ancServiceImpl.saveANCNurseData(jsnOBJ, Authorization);
//				if (null != ancRes && ancRes > 0) {
//					response.setResponse("Data saved successfully");
//				} else {
//					response.setError(5000, "Unable to save data");
//				}
				response.setResponse(ancRes);
			} else {
				response.setError(5000, "Invalid request");
			}

		} catch (Exception e) {
			logger.error("Error while saving nurse data:" + e);
			response.setError(5000, "Unable to save data");
		}
		return response.toString();
	}

	/**
	 * @Objective Save ANC data for doctor.
	 * @param JSON
	 *            requestObj
	 * @return success or failure response
	 */
	@CrossOrigin
	@ApiOperation(value = "Save ANC doctor data..", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/doctorData" }, method = { RequestMethod.POST })
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
				Long r = ancServiceImpl.saveANCDoctorData(jsnOBJ, Authorization);
				if (r != null && r > 0) {
					response.setResponse("Data saved successfully");
				} else {
					// something went wrong
					response.setError(5000, "Unable to save data");
				}
			} else {
				// data is null
				response.setError(5000, "Invalid request");
			}

		} catch (Exception e) {
			logger.error("Error while saving doctor data:" + e);
			response.setError(5000, "Unable to save data. " + e.getMessage());
		}
		return response.toString();
	}

}
