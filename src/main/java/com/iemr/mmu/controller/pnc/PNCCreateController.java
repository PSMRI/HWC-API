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
 * @Objective Saving PNC nurse and doctor data
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/PNC", headers = "Authorization")
public class PNCCreateController {
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
	 */

	@CrossOrigin
	@ApiOperation(value = "Save PNC nurse data..", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/nurseData" }, method = { RequestMethod.POST })
	public String saveBenPNCNurseData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for PNC nurse data saving :" + requestObj);

			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			if (jsnOBJ != null) {
				String ancRes = pncServiceImpl.savePNCNurseData(jsnOBJ, Authorization);
				response.setResponse(ancRes);
//				if (null != ancRes && ancRes > 0) {
//					response.setResponse("Data saved successfully");
//				} else {
//					response.setResponse("Unable to save data");
//				}

			} else {
				response.setError(5000, "Invalid request");
			}

		} catch (Exception e) {
			logger.error("Error while saving nurse data :" + e);
			response.setError(5000, "Unable to save data");
		}
		return response.toString();
	}

	/**
	 * @Objective Saving PNC doctor data
	 * @param requestObj
	 * @return success or failure response
	 */

	@CrossOrigin
	@ApiOperation(value = "Save PNC doctor data..", consumes = "application/json", produces = "application/json")
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
					// soething went wrong
					response.setError(5000, "Unable to save data");
				}
			} else {
				// data is null
				response.setError(5000, "Invalid request");
			}

		} catch (Exception e) {
			logger.error("Error while saving doctor data :" + e);
			response.setError(5000, "Unable to save data. " + e.getMessage());
		}
		return response.toString();
	}

}
