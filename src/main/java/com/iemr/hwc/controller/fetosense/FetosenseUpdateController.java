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

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

/**
 * 
 * @author DU20091017
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/fetosense", headers = "Authorization")
public class FetosenseUpdateController {

	@Autowired
	private FetosenseService fetosenseService;

	private Logger logger = LoggerFactory.getLogger(FetosenseUpdateController.class);

	/***
	 * @author DU20091017
	 * @param requestObj
	 * @param Authorization
	 * @return
	 * @throws IOException
	 */
	@CrossOrigin
	@ApiOperation(value = "update fetosense Data", consumes = "application/json", produces = "application/json")
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
//
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
