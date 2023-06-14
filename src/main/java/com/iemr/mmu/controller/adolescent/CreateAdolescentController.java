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
package com.iemr.mmu.controller.adolescent;

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
import com.iemr.mmu.service.adolescent.AdolescentAndChildCareService;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping(value = "/child-adolescent-care", headers = "Authorization")
public class CreateAdolescentController {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private AdolescentAndChildCareService adolescentAndChildCareService;

	/**
	 * @Objective Save child-adolescent-care data for nurse.
	 * @param requestObj
	 * @return success or failure response with visit code
	 */
	@CrossOrigin
	@ApiOperation(value = "Save child-adolescent-care nurse data", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/nurseData" }, method = { RequestMethod.POST })
	public String saveBenNurseDataCAC(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for child-adolescent-care nurse data saving :" + requestObj);

			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			JsonObject jsnOBJ = jsnElmnt.getAsJsonObject();

			if (jsnOBJ != null) {
				String genOPDRes = adolescentAndChildCareService.saveNurseData(jsnOBJ, Authorization);
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
	@ApiOperation(value = "save child-adolescent-care doctor data", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "save/doctorData" }, method = { RequestMethod.POST })
	public String saveDoctorDataCAC(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for child-adolescent-care doctor data saving :" + requestObj);

			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			JsonObject jsnOBJ = jsnElmnt.getAsJsonObject();
			if (jsnOBJ != null) {
				 int i = adolescentAndChildCareService.saveDoctorDataCAC(jsnOBJ, Authorization);
				
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
