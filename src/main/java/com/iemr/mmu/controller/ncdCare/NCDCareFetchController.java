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
package com.iemr.mmu.controller.ncdCare;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.mmu.service.ncdCare.NCDCareServiceImpl;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author NA874500
 * @Objective Fetching NCD Care data of Nurse and Doctor.
 * @Date 21-3-2018
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/NCDCare", headers = "Authorization")
public class NCDCareFetchController
{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private NCDCareServiceImpl ncdCareServiceImpl;
	
	@Autowired
	public void setNcdCareServiceImpl(NCDCareServiceImpl ncdCareServiceImpl)
	{
		this.ncdCareServiceImpl = ncdCareServiceImpl;
	}

	/**
	 * @Objective Fetching beneficiary visit details enterted by nurse.
	 * @param comingRequest
	 * @return visit details in JSON format
	 */
	
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Visit details from Nurse NCD Care", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenVisitDetailsFrmNurseNCDCare" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenVisitDetailsFrmNurseNCDCare(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for NCD Care visit data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.length() > 1) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = ncdCareServiceImpl.getBenVisitDetailsFrmNurseNCDCare(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid request");
				response.setError(5000, "Invalid request");
			}
			logger.info("NCD Care visit data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary visit data");
			logger.error("Error while getting beneficiary visit data :" + e);
		}
		return response.toString();
	}
	
	/**
	 * @Objective Fetching beneficiary history details enterted by nurse.
	 * @param comingRequest
	 * @return visit details in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary NCD Care History details from Nurse to Doctor ", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenNCDCareHistoryDetails" }, method = { RequestMethod.POST })

	public String getBenNCDCareHistoryDetails(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for NCD Care history data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String s = ncdCareServiceImpl.getBenNCDCareHistoryDetails(benRegID, visitCode);
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("NCD Care history data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary history data");
			logger.error("Error while getting beneficiary history data :" + e);
		}
		return response.toString();
	}
	
	/**
	 * @Objective Fetching beneficiary vital details enterted by nurse.
	 * @param comingRequest
	 * @return visit details in JSON format
	 */
	
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary NCD Care vital details from Nurse NCD Care", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenVitalDetailsFrmNurseNCDCare" }, method = { RequestMethod.POST })
	public String getBenVitalDetailsFrmNurseNCDCare(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for NCD Care vital data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = ncdCareServiceImpl.getBeneficiaryVitalDetails(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid request");
				response.setError(5000, "Invalid request");
			}
			logger.info("NCD Care vital data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary vital data");
			logger.error("Error while getting beneficiary vital data :" + e);
		}
		return response.toString();
	}
	
	/**
	 * @Objective Fetching beneficiary doctor details.
	 * @param comingRequest
	 * @return visit details in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Doctor Entered Details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenCaseRecordFromDoctorNCDCare" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenCaseRecordFromDoctorNCDCare(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for NCD Care doctor data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (null != obj && obj.length() > 1 && obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = ncdCareServiceImpl.getBenCaseRecordFromDoctorNCDCare(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid request");
				response.setError(5000, "Invalid request");
			}
			logger.info("NCD Care doctor data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary doctor data");
			logger.error("Error while getting beneficiary doctor data :" + e);
		}
		return response.toString();
	}
}
