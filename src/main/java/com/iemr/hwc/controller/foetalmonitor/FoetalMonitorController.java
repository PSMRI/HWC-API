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
package com.iemr.hwc.controller.foetalmonitor;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.hwc.data.foetalmonitor.FoetalMonitor;
import com.iemr.hwc.service.foetalmonitor.FoetalMonitorService;
import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.mapper.InputMapper;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping(value = "/foetalMonitor", headers = "Authorization")
public class FoetalMonitorController {
	@Autowired
	private FoetalMonitorService foetalMonitorService;
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	/**
	 * 
	 * @Objective Transfer Mother Data and NST/CTG Test Details to foetal monitor.
	 * @param JSON requestObj
	 * @return success or failure response
	 */

	@CrossOrigin
	@ApiOperation(value = "Send the mother data and prescribed test details to foetal monitor")
	@RequestMapping(value = "/sendMotherTestDetailsTofoetalMonitor", method = RequestMethod.POST, headers = "Authorization")
	public ResponseEntity<String> sendANCMotherTestDetailsToFoetalMonitor(
			@ApiParam("{\"beneficiaryRegID\":\"Long\",\"benFlowID\":\"Long\",\"testTime\":\"Timestamp\",\"motherLMPDate\":\"Timestamp\",\"motherName\":\"String\",\"fetosenseTestId\":\"Long\",\"testName\":\"String\",\"ProviderServiceMapID\":\"Integer\",\"createdBy\":\"String\"}") @RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String authorization) {
		OutputResponse output = new OutputResponse();

		try {

			if (requestObj != null) {

				FoetalMonitor foetalMonitorRequest = InputMapper.gson().fromJson(requestObj, FoetalMonitor.class);

				String response = foetalMonitorService.sendFoetalMonitorTestDetails(foetalMonitorRequest, authorization);

				output.setResponse(response);
			} else {
				logger.error("send ANC Mother TestDetails To Foetal Monitor : Invalid request");
				output.setError(404, "Invalid request");
			}
		} catch (Exception e) {
			logger.error("send ANC Mother TestDetails To Foetal Monitor failed with error " + e.getMessage());
			output.setError(5000, e.getMessage());
		}
		return output.toStringWithHttpStatus();
	}

	/***
	 * 
	 * @param requestObj
	 * @param authorization
	 * @return
	 */
	@CrossOrigin
	@ApiOperation(value = "Foetal monitor device status check")
	@RequestMapping(value = "/registerMother", method = RequestMethod.POST, headers = "Authorization")
	public String saveMother(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String authorization) {

		OutputResponse output = new OutputResponse();

		try {
			output.setResponse("Test in progress");
		} catch (Exception e) {

			output.setError(e);
		}

		return output.toString();
	}

	/***
	 * @param benFlowID
	 * @return
	 * @throws Exception
	 */
	@CrossOrigin
	@ApiOperation(value = "Get the foetal monitor details")
	@RequestMapping(value = "/fetch/foetalMonitorDetails/{benFlowID}", method = RequestMethod.GET, headers = "Authorization")
	public String getFoetalMonitorDetails(@ApiParam("{\"benFlowID\":\"Long\"}") @PathVariable("benFlowID") Long benFlowID) {

		logger.info("Request Object for getting Foetal Monitor data - " + benFlowID);
		OutputResponse output = new OutputResponse();
		try {
			String response = foetalMonitorService.getFoetalMonitorDetails(benFlowID);
			if (response != null)
				output.setResponse(response);
			else
				output.setError(5000, "Error in fetching the details");
		} catch (IEMRException e) {
			logger.error("Get foetal monitor details failed with error " + e.getMessage(), e);
			output.setError(5000, e.getMessage());
		}

		return output.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "Fetch foetal monitor pdf report (Base64 format)")
	@RequestMapping(value = "/fetch/reportGraphBase64", method = RequestMethod.POST, headers = "Authorization")
	public ResponseEntity<String> getFoetalMonitorDetails(
			@ApiParam("{\"reportFilePath\":\"String\"}") @RequestBody FoetalMonitor foetalMonitorOBJ) {

		logger.info("Request Object for getting foetal monitor test report file - " + foetalMonitorOBJ.toString());
		OutputResponse output = new OutputResponse();
		try {
			String response = foetalMonitorService.readPDFANDGetBase64(foetalMonitorOBJ.getaMRITFilePath());
			if (response != null)
				output.setResponse(response);
			else
				output.setError(5000, "Error in fetching the details");
		} catch (FileNotFoundException fnf) {
			logger.error("Get Foetal Monitor Details failed with error " + fnf.getMessage());
			output.setError(5000, "File not found : " + fnf.getMessage());
		} catch (IOException io) {
			logger.error("Get Foetal Monitor Details failed with error " + io.getMessage());
			output.setError(5000, "File not found : " + io.getMessage());
		} catch (IEMRException e) {
			logger.error("Get Foetal Monitor Details failed with error " + e.getMessage(), e);
			output.setError(5000, e.getMessage());
		}

		return output.toStringWithHttpStatus();
	}

	/***
	 * @param requestObj
	 * @param Authorization
	 * @return
	 * @throws IOException
	 */
	@CrossOrigin
	@ApiOperation(value = "Update foetal monitor data", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/update/foetalMonitorData", method = { RequestMethod.POST })
	public ResponseEntity<String> updateFoetalMonitorData(
			@ApiParam("\r\n" + "{\r\n" + "\"testId\":\"String\", \r\n" + "\"deviceId\":\"String\", \r\n"
					+ "\"testDoneAt\":\"String\", \r\n" + "\"lengthOfTest\": \"Integer\", \r\n"
					+ "\"basalHeartRate\": \"Integer\", \r\n" + "\"accelerationsList\":\"List of object\", \r\n"
					+ "\"decelerationsList\":\"List of object\", \r\n" + "\"shortTermVariationBpm\":\"String\",\r\n"
					+ "\"shortTermVariationMilli\":\"Integer\", \r\n" + "\"longTermVariation\":\"Integer\", \r\n"
					+ "\"movementEntries\":\"List\", \r\n" + "\"autoFetalMovement\":\"List\", \r\n"
					+ "\"reportPath\":\"String\"\r\n" + "\"mother\":{\r\n" + "\"cmMotherId\": \"String\", \r\n"
					+ "\"partnerMotherId\":\"String\", \r\n" + "\"partnerFetosenseID\":\"Long\",\r\n"
					+ "\"partnerId\":\"String\", \r\n" + "\"partnerName\":\"String\", \r\n"
					+ "\"motherName\":\"String\", \r\n" + "}\r\n" + "}") @RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		logger.info("Foetal Monitor API test result data  :" + requestObj);
		try {
			if (requestObj != null) {
				FoetalMonitor foetalMonitorData = InputMapper.gson().fromJson(requestObj, FoetalMonitor.class);
				int responseValue = foetalMonitorService.updateFoetalMonitorData(foetalMonitorData);
				if (responseValue == 1)
					response.setResponse("Data updated successfully");
			} else {
				response.setError(404, "Invalid request");
				logger.error("Invalid request");
			}
		} catch (IEMRException e) {
			response.setError(5000, e.getMessage());
			logger.error("Error while updating Foetal Monitor data :" + e);
		}
		return response.toStringWithHttpStatus();
	}

}
