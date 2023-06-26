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
package com.iemr.hwc.controller.family_planning;

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
import com.iemr.hwc.service.family_planning.FamilyPlanningService;
import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping(value = "/family-planning", headers = "Authorization")
public class UpdateFamilyPlanningController {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private FamilyPlanningService familyPlanningService;

	@CrossOrigin
	@ApiOperation(value = "update FamilyPlanning Data in Doctor screen", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/FamilyPlanningScreen" }, method = { RequestMethod.POST })
	public String updateFamilyPlanningNurse(@RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for FamilyPlanning data updating :" + requestObj);

		try {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			String s = familyPlanningService.updateFPDataFP(jsnOBJ);

			if (s != null)
				response.setResponse(s);
			else
				throw new IEMRException("error in updating data - NULL");

		} catch (Exception e) {
			response.setError(5000, "Unable to modify data : " + e.getLocalizedMessage());
			logger.error("Error while updating beneficiary FamilyPlanning data :" + e);
		}

		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "update FamilyPlanning Vital Data in Doctor screen", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/vitalScreen" }, method = { RequestMethod.POST })
	public String updateVitalNurseFamilyPlanning(@RequestBody String requestObj) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for FamilyPlanning Vital data updating :" + requestObj);

		try {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			int i = familyPlanningService.updateBenVitalDetailsFP(jsnOBJ);
			if (i == 1)
				response.setResponse("Data updated successfully");
			else
				response.setError(500, "Unable to modify data, please contact administrator");

			logger.info("FamilyPlanning vital data update Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Unable to modify data : " + e.getLocalizedMessage());
			logger.error("Error while updating beneficiary vital data :" + e);
		}

		return response.toString();
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Update FamilyPlanning doctor data for the doctor next visit
	 */
	@CrossOrigin
	@ApiOperation(value = "update FamilyPlanning Doctor Data", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/doctorData" }, method = { RequestMethod.POST })
	public String updateFamilyPlanningDoctorData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {

		OutputResponse response = new OutputResponse();
		logger.info("Request object for doctor data updating :" + requestObj);
		try {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			Long result = familyPlanningService.updateDoctorDataFP(jsnOBJ, Authorization);

			if (null != result && result > 0) {
				response.setResponse("Data updated successfully");
			} else
				throw new IEMRException("error in updating data - NULL");

			logger.info("Doctor data update response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Unable to modify data. " + e.getLocalizedMessage());
			logger.error("Error while updating FamilyPlanning  doctor data:" + e);
		}

		return response.toString();
	}

}
