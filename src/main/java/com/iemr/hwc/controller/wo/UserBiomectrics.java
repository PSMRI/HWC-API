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
package com.iemr.hwc.controller.wo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.iemr.hwc.controller.common.master.CommonMasterController;
import com.iemr.hwc.data.registrar.FingerPrintDTO;
import com.iemr.hwc.data.registrar.UserBiometricsMapping;
import com.iemr.hwc.service.registrar.RegistrarServiceImpl;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "wo/user_biometrics")
public class UserBiomectrics {
	// private OutputResponse response;
	private Logger logger = LoggerFactory.getLogger(CommonMasterController.class);

	@Autowired
	private RegistrarServiceImpl registrarService;

	@Operation(summary = "add fingerprint for a given username")
	@PostMapping(value = "add/fingerprint/wo", produces = { "application/json" })
	public String addFingerPrints(@RequestBody FingerPrintDTO comingRequest) {
		OutputResponse response = new OutputResponse();
		try {
			if (comingRequest != null && comingRequest.getUserName() != null) {
				String resp = registrarService.saveFingerprints(comingRequest);
				if (resp != null && resp.equals("ok")) {
					response.setResponse(resp);
				} else if (resp != null && resp.equals("ko")) {
					response.setError(500, "Error adding fingerprints");
				}
			} else {
				response.setError(400, "Invalid request");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(500, "Error while adding fingerprints data");
		}
		return response.toString();
	}

	@Operation(summary = "Get fingerprint by username")
	@GetMapping(value = "/get/fingerprint/{userID}/wo")
	public String getFingerprintsByUsername(@PathVariable("userID") Long userID) {
		logger.info("Get fingerprint by username ..." + userID);
		OutputResponse response = new OutputResponse();
		UserBiometricsMapping user = registrarService.getFingerprintsByUserID(userID);
		if (user != null) {
			Gson gson = new Gson();
			UserBiometricsMapping userBiometricsMapping = new UserBiometricsMapping();
			userBiometricsMapping.setUserID(user.getUserID());
			userBiometricsMapping.setFirstName(user.getFirstName());
			userBiometricsMapping.setLastName(user.getLastName());
			userBiometricsMapping.setUserName(user.getUserName());
			userBiometricsMapping.setCreatedBy(user.getUserName());
			userBiometricsMapping.setRightThumb(user.getRightThumb());
			userBiometricsMapping.setRightIndexFinger(user.getRightIndexFinger());
			userBiometricsMapping.setLeftThumb(user.getLeftThumb());
			userBiometricsMapping.setLeftIndexFinger(user.getLeftIndexFinger());

			response.setResponse(gson.toJson(userBiometricsMapping));
		} else {
			response.setError(404, "User with userID: " + userID + " not found");
		}
		logger.info("Get fingerprint " + response.toString());
		return response.toString();
	}
}