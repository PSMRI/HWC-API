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
package com.iemr.hwc.controller.labtechnician;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.service.labtechnician.LabTechnicianServiceImpl;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.v3.oas.annotations.Operation;

/***
 * 
 * @Objective Saving lab test results given by LabTechnician
 *
 */

@RestController

@RequestMapping(value = "/labTechnician", headers = "Authorization", consumes = "application/json", produces = "application/json")
public class LabTechnicianController {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private LabTechnicianServiceImpl labTechnicianServiceImpl;

	@Autowired
	public void setLabTechnicianServiceImpl(LabTechnicianServiceImpl labTechnicianServiceImpl) {
		this.labTechnicianServiceImpl = labTechnicianServiceImpl;
	}

	/**
	 * @Objective Save lab test results given by LabTechnician
	 * @param JSON requestObj
	 * @return success or failure response
	 */

	@Operation(summary = "Save lab test result")
	@PostMapping(value = { "/save/LabTestResult" })
	public String saveLabTestResult(@RequestBody String requestObj) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for Lab Test Result saving :" + requestObj);

			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			if (jsnOBJ != null) {
				Integer labResultSaveRes = labTechnicianServiceImpl.saveLabTestResult(jsnOBJ);
				if (null != labResultSaveRes && labResultSaveRes > 0) {
					response.setResponse("Data saved successfully");
				} else {
					response.setResponse("Unable to save data");
				}

			} else {
				response.setResponse("Invalid request");
			}
		} catch (Exception e) {
			logger.error("Error while saving lab test results  :" + e);
			response.setError(5000, "Unable to save data");
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary lab tests prescribed by doctor.
	 * @param requestOBJ
	 * @return lab tests prescribed by doctor
	 */

	@Operation(summary = "Get beneficiary lab test prescription")
	@PostMapping(value = { "/get/prescribedProceduresList" })
	public String getBeneficiaryPrescribedProcedure(@RequestBody String requestOBJ) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request obj to fetch lab tests :" + requestOBJ);
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestOBJ);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			if (jsnOBJ != null && !jsnOBJ.isJsonNull() && jsnOBJ.has("beneficiaryRegID") && jsnOBJ.has("visitCode")) {

				String s = labTechnicianServiceImpl.getBenePrescribedProcedureDetails(
						jsnOBJ.get("beneficiaryRegID").getAsLong(), jsnOBJ.get("visitCode").getAsLong());
				if (s != null)
					response.setResponse(s);
				else
					response.setError(5000, "Error in prescribed procedure details");
			} else {
				response.setError(5000, "Invalid request");
			}
		} catch (Exception e) {
			logger.error("Error while getting prescribed procedure data:" + e);
			response.setError(5000, "Error while getting prescribed procedure data");
		}
		return response.toString();
	}

	// API for getting lab result based on beneficiaryRegID and visitCode

	@Operation(summary = "Get lab test result for a beneficiary visit")
	@PostMapping(value = { "/get/labResultForVisitcode" })
	public String getLabResultForVisitCode(@RequestBody String requestOBJ) {
		OutputResponse response = new OutputResponse();
		try {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestOBJ);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			if (jsnOBJ != null && !jsnOBJ.isJsonNull() && jsnOBJ.has("beneficiaryRegID") && jsnOBJ.has("visitCode")) {
				String s = labTechnicianServiceImpl.getLabResultForVisitcode(jsnOBJ.get("beneficiaryRegID").getAsLong(),
						jsnOBJ.get("visitCode").getAsLong());

				if (s != null)
					response.setResponse(s);
				else
					response.setError(5000, "Error while getting lab report");
			} else
				response.setError(5000, "Invalid request");
		} catch (Exception e) {
			logger.error("Error while getting lab result for requested data:" + requestOBJ);
			response.setError(5000, "Error while getting lab report");
		}
		return response.toString();
	}

	@Operation(summary = "Get procedure component mapped master data")
	@PostMapping(value = { "/get/fetchProcCompMapMasterData" })
	public String getProcedureComponentMappedMasterData(@RequestBody String requestOBJ) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request obj to fetch procedure component mapped master data ");
			JsonObject jsnOBJ = new JsonObject();
			JsonElement jsnElement = JsonParser.parseString(requestOBJ);
			jsnOBJ = jsnElement.getAsJsonObject();

			if (jsnOBJ != null && !jsnOBJ.isJsonNull() && jsnOBJ.has("providerServiceMapID")) {

				String s = labTechnicianServiceImpl
						.getProcedureComponentMappedMasterData(jsnOBJ.get("providerServiceMapID").getAsLong());
				if (s != null)
					response.setResponse(s);
				else
					response.setError(5000, "Error in fetching procedure component mapped master data");
			} else {
				response.setError(5000, "Invalid request");
			}
		} catch (Exception e) {
			logger.error("Error while fetching procedure component mapped master data:" + e);
			response.setError(5000, "Error while fetching procedure component mapped master data");
		}
		return response.toString();
	}

}
