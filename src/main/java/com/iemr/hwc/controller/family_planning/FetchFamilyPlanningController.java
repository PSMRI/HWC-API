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
package com.iemr.hwc.controller.family_planning;

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

import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.service.family_planning.FamilyPlanningService;
import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping(value = "/family-planning", headers = "Authorization")
public class FetchFamilyPlanningController {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private FamilyPlanningService familyPlanningService;

	/**
	 * @Objective Fetching beneficiary visit details enterted by nurse.
	 * @param benRegID and benVisitID
	 * @return visit details in JSON format
	 */

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Visit details from Nurse family-planning", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenVisitDetails-Nurse-FamilyPlanning" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenVisitDetailsFrmNurseFamilyPlanning(
			@ApiParam(value = "{\"benRegID\":\"Long\", \"visitCode\":\"Long\"}") @RequestBody CommonUtilityClass commonUtilityClass) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for FamilyPlanning visit data fetching :" + commonUtilityClass.toString());
		try {
			if (commonUtilityClass != null && commonUtilityClass.getBenRegID() != null
					&& commonUtilityClass.getVisitCode() != null) {
				String s = familyPlanningService.getBenVisitDetailsFrmNurseFP(commonUtilityClass.getBenRegID(),
						commonUtilityClass.getVisitCode());
				if (s != null)
					response.setResponse(s);
				else
					throw new IEMRException("No record found");
			} else
				throw new IEMRException("Invalid request - Beneficiary and visit info required");
			// call service to fetch data
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary visit data : " + e.getLocalizedMessage());
			logger.error("Error while getting beneficiary visit data:" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary vital details entered by nurse.
	 * @param benRegID and benVisitID
	 * @return vital details in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary vital details from Nurse Family-Planning", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenVitalDetailsFrmNurseFamilyPlanning" }, method = { RequestMethod.POST })
	public String getBenVitalDetailsFrmNurseFamilyPlanning(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody CommonUtilityClass commonUtilityClass) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for FamilyPlanning vital data fetching :" + commonUtilityClass.toString());
		try {
			if (commonUtilityClass.getBenRegID() != null && commonUtilityClass.getVisitCode() != null) {
				String s = familyPlanningService.getBeneficiaryVitalDetailsFP(commonUtilityClass.getBenRegID(),
						commonUtilityClass.getVisitCode());
				if (s != null)
					response.setResponse(s);
				else
					throw new IEMRException("No record found");
			} else
				throw new IEMRException("Invalid request - Beneficiary and visit info required");
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary vital data : " + e.getLocalizedMessage());
			logger.error("Error while getting beneficiary vital data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary FamilyPlanning details entered by nurse.
	 * @param benRegID and benVisitID
	 * @return FamilyPlanning details in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary FamilyPlanning details from Nurse", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenFPDetailsFrmNurseFamilyPlanning" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenFPDetailsFrmNurseFamilyPlanning(
			@ApiParam(value = "{\"benRegID\":\"Long\", \"visitCode\":\"Long\"}") @RequestBody CommonUtilityClass commonUtilityClass) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for FamilyPlanning details data fetching :" + commonUtilityClass.toString());
		try {
			String s = familyPlanningService.getBeneficiaryFPDetailsFP(commonUtilityClass.getBenRegID(),
					commonUtilityClass.getVisitCode());
			if (s != null)
				response.setResponse(s);
			else
				throw new IEMRException("No record found");
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary FamilyPlanning data " + e.getLocalizedMessage());
			logger.error("Error while getting beneficiary FamilyPlanning data:" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary doctor details.
	 * @param comingRequest
	 * @return doctor details in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Doctor Entered Details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenCaseRecordFromDoctor" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenCaseRecordFromDoctor(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenCaseRecordFromDoctor FP request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (null != obj && obj.length() > 1 && obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = familyPlanningService.getBenCaseRecordFromDoctorFP(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenCaseRecordFromDoctor FP  response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary doctor data : " + e.getLocalizedMessage());
			logger.error("Error in getBenCaseRecordFromDoctor FP :" + e);
		}
		return response.toString();
	}

}
