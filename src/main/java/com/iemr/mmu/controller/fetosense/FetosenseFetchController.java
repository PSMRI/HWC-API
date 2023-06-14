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
package com.iemr.mmu.controller.fetosense;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.mmu.data.fetosense.Fetosense;
import com.iemr.mmu.service.fetosense.FetosenseService;
import com.iemr.mmu.utils.exception.IEMRException;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping(value = "/fetosense")
public class FetosenseFetchController {

	@Autowired
	private FetosenseService fetosenseService;

	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	/***
	 * @author DU20091017
	 * @param benFlowID
	 * @return
	 * @throws Exception
	 */
	@CrossOrigin
	@ApiOperation(value = "Provides the fetosense details")
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
	@ApiOperation(value = "Fetch fetosense pdf report base64 string")
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
}
