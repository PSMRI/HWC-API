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
package com.iemr.hwc.controller.teleconsultation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.service.tele_consultation.TeleConsultationServiceImpl;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/tc", headers = "Authorization", consumes = "application/json", produces = "application/json")
public class TeleConsultationController {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private TeleConsultationServiceImpl teleConsultationServiceImpl;

	
	@Operation(summary = "Update beneficiary arrival status based on request")
	@PostMapping(value = { "/update/benArrivalStatus" })
	public String benArrivalStatusUpdater(@RequestBody String requestOBJ) {
		OutputResponse response = new OutputResponse();
		try {
			if (requestOBJ != null) {
				int i = teleConsultationServiceImpl.updateBeneficiaryArrivalStatus(requestOBJ);
				if (i > 0)
					response.setResponse("Beneficiary arrival status updated successfully.");
				else
					response.setError(5000, "Error in updating beneficiary arrival status.");
			} else
				response.setError(5000, "Invalid request");
		} catch (Exception e) {
			logger.error("error while updating beneficiary arrival status = " + e);
			response.setError(5000, "Error while updating beneficiary arrival status." + e);
		}
		return response.toString();
	}

	
	@Operation(summary = "Update beneficiary status based on request")
	@PostMapping(value = { "/cancel/benTCRequest" })
	public String updateBeneficiaryStatusToCancelTCRequest(@RequestBody String requestOBJ,
			@RequestHeader String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			if (requestOBJ != null) {
				int i = teleConsultationServiceImpl.updateBeneficiaryStatusToCancelTCRequest(requestOBJ, Authorization);
				if (i > 0)
					response.setResponse("Beneficiary TC request cancelled successfully.");
				else
					response.setError(5000, "Teleconsultation cancel request failed.");
			} else
				response.setError(5000, "Invalid request");
		} catch (Exception e) {
			logger.error("error while updating beneficiary status for Teleconsultation cancel request = " + e);
			response.setError(5000,
					"Error while updating beneficiary status for Teleconsultation cancel request = " + e);
		}
		return response.toString();
	}

	
	@Operation(summary = "Check if specialist can proceed with beneficiary")
	@PostMapping(value = { "/check/benTCRequestStatus" })
	public String checkBeneficiaryStatusToProceedWithSpecialist(@RequestBody String requestOBJ) {
		OutputResponse response = new OutputResponse();
		try {
			if (requestOBJ != null) {
				int i = teleConsultationServiceImpl.checkBeneficiaryStatusForSpecialistTransaction(requestOBJ);
				if (i > 0)
					response.setResponse("Specialist can proceed with beneficiary TM session.");
				else
					response.setError(5000, "Issue while fetching beneficiary status.");
			} else
				response.setError(5000, "Invalid request");
		} catch (Exception e) {
			logger.error("Issue while fetching beneficiary status =  " + e);
			response.setError(5000, "Issue while fetching beneficiary status = " + e);
		}
		return response.toString();
	}

	
	@Operation(summary = "Create teleconsultation request from worklist")
	@PostMapping(value = { "/create/benTCRequestWithVisitCode" })
	public String createTCRequestForBeneficiary(@RequestBody String requestOBJ, @RequestHeader String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			if (requestOBJ != null) {
				JsonObject jsnOBJ = new JsonObject();
				JsonParser jsnParser = new JsonParser();
				JsonElement jsnElmnt = jsnParser.parse(requestOBJ);
				jsnOBJ = jsnElmnt.getAsJsonObject();

				int i = teleConsultationServiceImpl.createTCRequestFromWorkList(jsnOBJ, Authorization);
				if (i > 0)
					response.setResponse("Teleconsultation request created successfully.");
				else
					response.setError(5000, "Issue while creating Teleconsultation request.");
			} else
				response.setError(5000, "Invalid request");
		} catch (Exception e) {
			logger.error("Issue while creating Teleconsultation request =  " + e);
			response.setError(5000, "Issue while creating Teleconsultation request = " + e);
		}
		return response.toString();
	}

	// TC request List
	
	@Operation(summary = "Get teleconsultation request list for a specialist")
	@PostMapping(value = { "/getTCRequestList" })
	public String getTCSpecialistWorkListNew(@RequestBody String requestOBJ) {
		OutputResponse response = new OutputResponse();
		try {
			if (requestOBJ != null) {
				JsonObject jsnOBJ = new JsonObject();
				JsonParser jsnParser = new JsonParser();
				JsonElement jsnElmnt = jsnParser.parse(requestOBJ);
				jsnOBJ = jsnElmnt.getAsJsonObject();

				String s = teleConsultationServiceImpl.getTCRequestListBySpecialistIdAndDate(
						jsnOBJ.get("psmID").getAsInt(), jsnOBJ.get("userID").getAsInt(),
						jsnOBJ.get("date").getAsString());
				if (s != null)
					response.setResponse(s);
			} else {
				logger.error("Invalid request, either ProviderServiceMapID or userID or reqDate is invalid");
				response.setError(5000,
						"Invalid request, either ProviderServiceMapID or UserID or RequestDate is invalid");
			}

		} catch (Exception e) {
			logger.error("Error in TC requestList" + e);
			response.setError(5000, "Error while getting TC requestList");
		}
		return response.toString();
	}

	
	@Operation(summary = "Update first consultation start time")
	@PostMapping(value = { "/startconsultation" })
	public String startconsultation(@RequestBody String requestOBJ) {
		OutputResponse response = new OutputResponse();
		try {
			if (requestOBJ != null) {
				JsonObject jsnOBJ = new JsonObject();
				JsonParser jsnParser = new JsonParser();
				JsonElement jsnElmnt = jsnParser.parse(requestOBJ);
				jsnOBJ = jsnElmnt.getAsJsonObject();

				Integer s = teleConsultationServiceImpl.startconsultation(jsnOBJ.get("benRegID").getAsLong(),
						jsnOBJ.get("visitCode").getAsLong());
				if (s != null)
					response.setResponse(s.toString());
			} else {
				logger.error("Invalid request, either ProviderServiceMapID or userID or reqDate is invalid");
				response.setError(5000,
						"Invalid request, either ProviderServiceMapID or UserID or RequestDate is invalid");
			}

		} catch (Exception e) {
			logger.error("Error in TC requestList" + e);
			response.setError(5000, "Error while getting TC requestList");
		}
		return response.toString();
	}
}
