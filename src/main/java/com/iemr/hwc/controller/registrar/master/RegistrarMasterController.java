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
package com.iemr.hwc.controller.registrar.master;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.hwc.service.common.master.RegistrarServiceMasterDataImpl;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.v3.oas.annotations.Operation;




@CrossOrigin
@RestController
@RequestMapping(value = "/registrar", headers = "Authorization", consumes = "application/json", produces = "application/json")
/** Objective: Get Registration related master Data */
public class RegistrarMasterController {

	private Logger logger = LoggerFactory.getLogger(RegistrarMasterController.class);
	private RegistrarServiceMasterDataImpl registrarServiceMasterDataImpl;

	@Autowired
	public void setRegistrarServiceMasterDataImpl(RegistrarServiceMasterDataImpl registrarServiceMasterDataImpl) {
		this.registrarServiceMasterDataImpl = registrarServiceMasterDataImpl;
	}

	@CrossOrigin()
	@Operation(summary = "Get master data for registrar")
	@PostMapping(value = { "/registrarMasterData" })
	public String masterDataForRegistration(
			@Param(value = "{\"spID\": \"Integer\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		logger.info("masterDataForRegistration request :" + comingRequest);
		try {

			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("spID")) {
				if (obj.getInt("spID") > 0) {
					response.setResponse(registrarServiceMasterDataImpl.getRegMasterData());
				} else {
					response.setError(5000, "Invalid service point");
				}
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("masterDataForRegistration response :" + response);

		} catch (Exception e) {
			logger.error("Error in masterDataForRegistration :" + e);
			response.setError(5000, "Error while getting master data for registration");
		}
		return response.toString();
	}
}
