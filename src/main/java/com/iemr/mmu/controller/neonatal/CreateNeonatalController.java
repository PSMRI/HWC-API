/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.controller.neonatal;

import java.sql.SQLException;

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
import com.iemr.mmu.service.neonatal.NeonatalService;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping(value = "/neonatal-infant-services", headers = "Authorization")
public class CreateNeonatalController {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private NeonatalService neonatalService;

	/**
	 * @Objective Save neonatal-infant data for nurse.
	 * @param requestObj
	 * @return success or failure response with visit code
	 */
	@CrossOrigin
	@ApiOperation(value = "Save neonatal-infant nurse data", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/nurseData" }, method = { RequestMethod.POST })
	public String saveBenNeoNatalAndInfantNurseData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for neonatal-infant nurse data saving :" + requestObj);

			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			if (jsnOBJ != null) {
				String genOPDRes = neonatalService.saveNurseData(jsnOBJ, Authorization);
				response.setResponse(genOPDRes);

			} else {
				response.setResponse("Invalid request");
			}
		} catch (SQLException e) {
			logger.error("Error in nurse data saving :" + e);
			response.setError(5000, "Unable to save data : " + e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("Error in nurse data saving :" + e);
			response.setError(5000, "Unable to save data : " + e.getLocalizedMessage());
		}
		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "save NNI doctor data", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "save-neo-natal-doctor-data" }, method = { RequestMethod.POST })
	public String saveNNIDoctorData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for NNI doctor data saving :" + requestObj);

			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();
			if (jsnOBJ != null) {
				int i = neonatalService.saveDoctorDataNNI(jsnOBJ, Authorization);
				if (i == 1)
					response.setResponse("Data saved successfully");
			} else {

				response.setError(5000, "Invalid request object : NULL");
			}

		} catch (Exception e) {
			logger.error("Error while saving doctor data:" + e);
			response.setError(5000, "Unable to save data. " + e.getMessage());
		}
		return response.toString();
	}

}
