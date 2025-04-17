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
package com.iemr.hwc.controller.uptsu;

import jakarta.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemr.hwc.data.uptsu.Referred104Details;
import com.iemr.hwc.service.uptsu.UptsuService;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.v3.oas.annotations.Operation;



@RequestMapping(value = "/uptsu", headers = "Authorization", consumes = "application/json", produces = "application/json")
@RestController

public class UPTechnicalSupportUnitController {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	@Autowired
	private UptsuService uptsuService;

	
	@Operation(summary = "Fetch 104 action master data")
	@GetMapping(value = "/get/action-master", produces = MediaType.APPLICATION_JSON)
	public String getActionMaster() {

		OutputResponse response = new OutputResponse();
		try {
			response.setResponse(uptsuService.getActionMaster());
		} catch (Exception e) {
			response.setError(5000, e.getLocalizedMessage());
		}

		return response.toString();
	}

	
	@Operation(summary = "Fetch 104 work list data")
	@GetMapping("/getWorklistByVanID/{vanId}")
	public String getWolklist104Data(@PathVariable Integer vanId) {
		OutputResponse response = new OutputResponse();
		String resp = null;
		logger.info("Entered into getWorklist method with vanId : " + vanId);
		try {
			resp = uptsuService.getWorklist(vanId);
			if (null != resp) {
				response.setResponse(resp.toString());
			}
		} catch (Exception e) {
			logger.error("error while getting getWolklist104Data {} - " + e.getMessage());
			response.setError(5000, e.getMessage());
		}
		return response.toString();
	}

	
	@Operation(summary = "Save 104 referred details")
	@PostMapping("/submit/closevisit")
	public String saveReferred104Details(@RequestBody String request) {
		ObjectMapper mapper = new ObjectMapper();
		OutputResponse response = new OutputResponse();
		Referred104Details requestObj = null;
		try {
			requestObj = mapper.readValue(request, Referred104Details.class);
			Referred104Details data = uptsuService.saveReferredDetails(requestObj);
			if (null != data && !ObjectUtils.isEmpty(data)) {
				response.setResponse("Data saved successfully");
			}
		} catch (Exception e) {
			logger.error("error while saving saveReferred104Details {} - " + e.getMessage());
			response.setError(5000, e.getMessage());
		}
		return response.toString();
	}
}
