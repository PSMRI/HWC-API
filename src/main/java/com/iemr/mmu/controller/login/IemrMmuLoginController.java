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
package com.iemr.mmu.controller.login;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.mmu.controller.registrar.master.RegistrarController;
import com.iemr.mmu.service.login.IemrMmuLoginServiceImpl;
import com.iemr.mmu.utils.mapper.InputMapper;
import com.iemr.mmu.utils.response.OutputResponse;

@RequestMapping(value = "/user", headers = "Authorization")
@RestController
public class IemrMmuLoginController {

	private Logger logger = LoggerFactory.getLogger(RegistrarController.class);
	private InputMapper inputMapper = new InputMapper();

	private IemrMmuLoginServiceImpl iemrMmuLoginServiceImpl;

	@Autowired
	public void setIemrMmuLoginServiceImpl(IemrMmuLoginServiceImpl iemrMmuLoginServiceImpl) {
		this.iemrMmuLoginServiceImpl = iemrMmuLoginServiceImpl;
	}

	@CrossOrigin()
	@RequestMapping(value = "/getUserServicePointVanDetails", method = { RequestMethod.POST }, produces = {
			"application/json" })
	public String getUserServicePointVanDetails(@RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		try {

			JSONObject obj = new JSONObject(comingRequest);
			logger.info("getUserServicePointVanDetails request " + comingRequest);
			String responseData = iemrMmuLoginServiceImpl.getUserServicePointVanDetails(obj.getInt("userID"));
			response.setResponse(responseData);
		} catch (Exception e) {
			// e.printStackTrace();
			response.setError(5000, "Error while getting service points and van data");
			logger.error("get User SP and van details failed with " + e.getMessage(), e);

		}
		logger.info("getUserServicePointVanDetails response " + response.toString());
		return response.toString();
	}

	@CrossOrigin()
	@RequestMapping(value = "/getServicepointVillages", method = { RequestMethod.POST }, produces = {
			"application/json" })
	public String getServicepointVillages(@RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		try {

			JSONObject obj = new JSONObject(comingRequest);
			logger.info("getServicepointVillages request " + comingRequest);
			String responseData = iemrMmuLoginServiceImpl.getServicepointVillages(obj.getInt("servicePointID"));
			response.setResponse(responseData);
		} catch (Exception e) {
			// e.printStackTrace();
			response.setError(5000, "Error while getting service points and villages");
			logger.error("get villages with servicepoint failed with " + e.getMessage(), e);

		}
		logger.info("getServicepointVillages response " + response.toString());
		return response.toString();
	}

	@CrossOrigin()
	@RequestMapping(value = "/getUserVanSpDetails", method = { RequestMethod.POST }, produces = { "application/json" })
	public String getUserVanSpDetails(@RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		try {

			JSONObject obj = new JSONObject(comingRequest);
			logger.info("getServicepointVillages request " + comingRequest);
			if (obj.has("userID") && obj.has("providerServiceMapID")) {
				String responseData = iemrMmuLoginServiceImpl.getUserVanSpDetails(obj.getInt("userID"),
						obj.getInt("providerServiceMapID"));
				response.setResponse(responseData);
			} else {
				response.setError(5000, "Invalid request");
			}
		} catch (Exception e) {
			// e.printStackTrace();
			response.setError(5000, "Error while getting van and service points data");
			logger.error("getUserVanSpDetails failed with " + e.getMessage(), e);

		}
		logger.info("getUserVanSpDetails response " + response.toString());
		return response.toString();
	}
	
	/* created by = DU20091017 */
	
	@CrossOrigin()
	@RequestMapping(value = "/getUserSpokeDetails/{psmId}", method = { RequestMethod.GET }, produces = { "application/json" })
	public String getUserSpokeDetails(@PathVariable ("psmId") Integer psmId) {
		OutputResponse response = new OutputResponse();
		try {
			if (psmId != null)
				response.setResponse(iemrMmuLoginServiceImpl.getUserSpokeDetails(psmId));
			else
				response.setError(5000, "Invalid request");
		} catch (Exception e) {
			logger.info("Error occurred while fetching van master is  : " + e);
			response.setError(5000, "Error occurred while fetching van master is  : " + e);
			;
		}
		return response.toString();
	}
}
