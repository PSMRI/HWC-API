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
package com.iemr.hwc.controller.fetosense;

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

import com.iemr.hwc.data.fetosense.Fetosense;
import com.iemr.hwc.service.fetosense.FetosenseService;
import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.mapper.InputMapper;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping(value = "/fetosense", headers = "Authorization")
public class FetosenseController {
	@Autowired
	private FetosenseService fetosenseService;
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	/**
	 * 
	 * @Objective Transfer Mother Data and NST/CTG Test Details to Fetosense.
	 * @param JSON requestObj
	 * @return success or failure response
	 */

	@CrossOrigin
	@ApiOperation(value = "Send the mother data and prescribed test details to fetosense")
	@RequestMapping(value = "/sendMotherTestDetailsToFetosense", method = RequestMethod.POST, headers = "Authorization")
	public ResponseEntity<String> sendANCMotherTestDetailsToFetosense(
			@ApiParam("{\"beneficiaryRegID\":\"Long\",\"benFlowID\":\"Long\",\"testTime\":\"Timestamp\",\"motherLMPDate\":\"Timestamp\",\"motherName\":\"String\",\"fetosenseTestId\":\"Long\",\"testName\":\"String\",\"ProviderServiceMapID\":\"Integer\",\"createdBy\":\"String\"}") @RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String authorization) {
		OutputResponse output = new OutputResponse();

		try {

			if (requestObj != null) {

				Fetosense fetosenseRequest = InputMapper.gson().fromJson(requestObj, Fetosense.class);

				String response = fetosenseService.sendFetosenseTestDetails(fetosenseRequest, authorization);

				output.setResponse(response);
			} else {
				logger.error("send ANC Mother TestDetails To Fetosense : Invalid request");
				output.setError(404, "Invalid request");
			}
		} catch (Exception e) {
			logger.error("send ANC Mother TestDetails To Fetosense failed with error " + e.getMessage());
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
	@ApiOperation(value = "Fetosense device status check")
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
	@ApiOperation(value = "Get the fetosense details")
	@RequestMapping(value = "/fetch/fetosenseDetails/{benFlowID}", method = RequestMethod.GET, headers = "Authorization")
	public String getFetosenseDetails(@ApiParam("{\"benFlowID\":\"Long\"}") @PathVariable("benFlowID") Long benFlowID) {

		logger.info("Request Object for getting fetosense data - " + benFlowID);
		OutputResponse output = new OutputResponse();
		try {
			String response = fetosenseService.getFetosenseDetails(benFlowID);
			if (response != null)
				output.setResponse(response);
			else
				output.setError(5000, "Error in fetching the details");
		} catch (IEMRException e) {
			logger.error("getFetosenseDetails failed with error " + e.getMessage(), e);
			output.setError(5000, e.getMessage());
		}

		return output.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "Fetch fetosense pdf report (Base64 format)")
	@RequestMapping(value = "/fetch/reportGraphBase64", method = RequestMethod.POST, headers = "Authorization")
	public ResponseEntity<String> getFetosenseDetails(
			@ApiParam("{\"reportFilePath\":\"String\"}") @RequestBody Fetosense fetosenseOBJ) {

		logger.info("Request Object for getting fetosense test report file - " + fetosenseOBJ.toString());
		OutputResponse output = new OutputResponse();
		try {
			String response = fetosenseService.readPDFANDGetBase64(fetosenseOBJ.getaMRITFilePath());
			if (response != null)
				output.setResponse(response);
			else
				output.setError(5000, "Error in fetching the details");
		} catch (FileNotFoundException fnf) {
			logger.error("getFetosenseDetails failed with error " + fnf.getMessage());
			output.setError(5000, "File not found : " + fnf.getMessage());
		} catch (IOException io) {
			logger.error("getFetosenseDetails failed with error " + io.getMessage());
			output.setError(5000, "File not found : " + io.getMessage());
		} catch (IEMRException e) {
			logger.error("getFetosenseDetails failed with error " + e.getMessage(), e);
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
	@ApiOperation(value = "Update fetosense data", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/update/fetosenseData", method = { RequestMethod.POST })
	public ResponseEntity<String> updateFetosenseData(
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
		logger.info("Fetosense API test result data  :" + requestObj);
		try {
			if (requestObj != null) {
				Fetosense fetosenseData = InputMapper.gson().fromJson(requestObj, Fetosense.class);
				int responseValue = fetosenseService.updateFetosenseData(fetosenseData);
				if (responseValue == 1)
					response.setResponse("Data updated successfully");
			} else {
				response.setError(404, "Invalid request");
				logger.error("Invalid request");
			}
		} catch (IEMRException e) {
			response.setError(5000, e.getMessage());
			logger.error("Error while updating fetosense data :" + e);
		}
		return response.toStringWithHttpStatus();
	}

}
