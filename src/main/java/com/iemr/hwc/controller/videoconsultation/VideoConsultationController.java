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
package com.iemr.hwc.controller.videoconsultation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.hwc.service.videoconsultation.VideoConsultationService;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/videoConsultation", headers = "Authorization", consumes = "application/json", produces = "application/json")
public class VideoConsultationController {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private VideoConsultationService videoConsultationService;

	@CrossOrigin()
	@Operation(summary = "Login to video consultation service")
	@GetMapping(value = "/login/{userID}", headers = "Authorization", produces = { "application/json" })
	public String login(@PathVariable("userID") Long userID) {

		OutputResponse response = new OutputResponse();

		try {

			String createdData = videoConsultationService.login(userID);

			response.setResponse(createdData.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		return response.toString();

	}

	@CrossOrigin()
	@Operation(summary = "Call user for video consultation")
	@GetMapping(value = "/call/{fromUserID}/{touserID}", headers = "Authorization", produces = { "application/json" })
	public String call(@PathVariable("fromUserID") Long fromUserID, @PathVariable("toUserID") Long toUserID) {

		OutputResponse response = new OutputResponse();

		try {

			String createdData = videoConsultationService.callUser(fromUserID, toUserID);

			response.setResponse(createdData.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		return response.toString();

	}

	@CrossOrigin()
	@Operation(summary = "Video consultatation for users by passing type")
	@GetMapping(value = "/call/{fromUserID}/{toUserID}/{type}", headers = "Authorization", produces = {
			"application/json" })
	public String callSwymedAndJitsi(@PathVariable("fromUserID") Long fromUserID,
			@PathVariable("toUserID") Long toUserID, @PathVariable("type") String Type) {

		OutputResponse response = new OutputResponse();

		try {
			String createdData = null;
			if (Type.equalsIgnoreCase("Swymed")) {
				createdData = videoConsultationService.callUser(fromUserID, toUserID);
			} else {
				createdData = videoConsultationService.callUserjitsi(fromUserID, toUserID);
			}

			response.setResponse(createdData.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		return response.toString();

	}

	@CrossOrigin()
	@Operation(summary = "Call van through Swymed")
	@GetMapping(value = "/callvan/{fromUserID}/{vanID}", headers = "Authorization", produces = { "application/json" })
	public String callvan(@PathVariable("fromUserID") Long fromUserID, @PathVariable("vanID") Integer vanID) {

		OutputResponse response = new OutputResponse();

		try {

			String createdData = videoConsultationService.callVan(fromUserID, vanID);

			response.setResponse(createdData.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		return response.toString();

	}

	@CrossOrigin()
	@Operation(summary = "Call van through Swymed by passing type")
	@GetMapping(value = "/callvan/{fromUserID}/{vanID}/{type}", headers = "Authorization", produces = {
			"application/json" })
	public String callVanSwymedAndJitsi(@PathVariable("fromUserID") Long fromUserID,
			@PathVariable("vanID") Integer vanID, @PathVariable("type") String type) {

		OutputResponse response = new OutputResponse();

		try {

			String createdData = null;
			if (type.equalsIgnoreCase("Swymed")) {
				createdData = videoConsultationService.callVan(fromUserID, vanID);
			} else {
				createdData = videoConsultationService.callVanJitsi(fromUserID, vanID);
			}

			response.setResponse(createdData.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		return response.toString();

	}

	@CrossOrigin()
	@Operation(summary = "Logout of video consultation service")
	@GetMapping(value = "/logout", headers = "Authorization", produces = { "application/json" })
	public String logout() {

		OutputResponse response = new OutputResponse();

		try {

			String createdData = videoConsultationService.logout();

			response.setResponse(createdData.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		return response.toString();

	}

}
