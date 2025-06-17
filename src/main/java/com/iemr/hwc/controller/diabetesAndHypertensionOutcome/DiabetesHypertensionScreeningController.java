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
package com.iemr.hwc.controller.diabetesAndHypertensionOutcome;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.hwc.service.ncdscreening.DiabetesAndHypertensionOutcomeService;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/screeningOutcome", headers = "Authorization", consumes = "application/json", produces = "application/json")
public class DiabetesHypertensionScreeningController {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	@Autowired
	private DiabetesAndHypertensionOutcomeService diabetesAndHypertensionOutcomeService;

	@Operation(summary = "Evaluate hypertension screening outcome")
	@PostMapping(value = { "/hypertension" })
	public String getHypertensionOutcome(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object getting hypertension screening outcome :" + comingRequest);
		try {
			String res = diabetesAndHypertensionOutcomeService.getHypertensionOutcome(comingRequest);
			response.setResponse(res);
		} catch (Exception e) {
			response.setError(5000, "Error while getting hypertension screening outcome");
			logger.error("Error while getting hypertension screening outcome ", e);
		}
		return response.toString();
	}

	@Operation(summary = "Evaluate diabetes screening outcome")
	@PostMapping(value = { "/diabetes" })
	public String getDiabetesOutcome(
			@Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object getting diabetes screening outcome :" + comingRequest);
		try {
			String res = diabetesAndHypertensionOutcomeService.getDiabetesOutcome(comingRequest);
			response.setResponse(res);
		} catch (Exception e) {
			response.setError(5000, "Error while getting diabetes screening outcome");
			logger.error("Error while getting diabetes screening outcome ", e);
		}
		return response.toString();
	}
}
