/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.controller.neonatal;

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
import com.iemr.mmu.utils.exception.IEMRException;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping(value = "/neonatal-infantServices", headers = "Authorization")
public class UpdateNeonatalController {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private NeonatalService neonatalService;

	@CrossOrigin
	@ApiOperation(value = "update NNI Vital Data in Doctor screen", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/vitalScreen" }, method = { RequestMethod.POST })
	public String updateVitalNurseNNI(@RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for NNI Vital data updating :" + requestObj);

		try {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			int i = neonatalService.updateBenVitalDetailsNNI(jsnOBJ);
			if (i == 1)
				response.setResponse("Data updated successfully");
			else
				response.setError(500, "Unable to modify data, please contact administrator");

			logger.info("NNI vital data update Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Unable to modify data : " + e.getLocalizedMessage());
			logger.error("Error while updating beneficiary vital data :" + e);
		}

		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "update NNI Data in Doctor screen", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/BirthAndImmunizationHistoryScreen" }, method = { RequestMethod.POST })
	public String updateBirthAndImmunizationHistoryNurse(@RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for NNI data updating :" + requestObj);

		try {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			int i = neonatalService.updateBenHistoryDetails(jsnOBJ);

			if (i > 0)
				response.setResponse("data updated successfully");
			else
				throw new IEMRException("error in updating data. please contact administrator");

		} catch (Exception e) {
			response.setError(5000, "Unable to modify data : " + e.getLocalizedMessage());
			logger.error("Error while updating beneficiary NNI data :" + e);
		}

		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "update NNI Data in Doctor screen", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/ImmunizationServicesScreen" }, method = { RequestMethod.POST })
	public String updateImmunizationServicesNurse(@RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for NNI data updating :" + requestObj);

		try {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			int i = neonatalService.updateBenImmunServiceDetailsNNI(jsnOBJ);

			if (i > 0)
				response.setResponse("data updated successfully");
			else
				throw new IEMRException("error in updating data, please contact administrator");

		} catch (Exception e) {
			response.setError(5000, "Unable to modify data : " + e.getLocalizedMessage());
			logger.error("Error while updating beneficiary NNI data :" + e);
		}

		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "update NNI Doctor Data", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/doctorData" }, method = { RequestMethod.POST })
	public String updateNNIDoctorData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for doctor data updating :" + requestObj);
		try {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			Long result = neonatalService.updateDoctorDataNNI(jsnOBJ, Authorization);

			if (null != result && result > 0) {
				response.setResponse("Data updated successfully");
			} else
				throw new IEMRException("error in updating data - NULL");

			logger.info("Doctor data update response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Unable to modify data. " + e.getLocalizedMessage());
			logger.error("Error while updating NNI  doctor data:" + e);
		}

		return response.toString();
	}

}
