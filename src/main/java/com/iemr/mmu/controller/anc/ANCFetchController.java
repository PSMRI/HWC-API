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
package com.iemr.mmu.controller.anc;

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

import com.google.gson.Gson;
import com.iemr.mmu.data.anc.HrpStatusAndReasonsRequestModel;
import com.iemr.mmu.data.anc.SysObstetricExamination;
import com.iemr.mmu.data.nurse.CommonUtilityClass;
import com.iemr.mmu.service.anc.ANCService;
import com.iemr.mmu.service.anc.ANCServiceImpl;
import com.iemr.mmu.utils.exception.IEMRException;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author NA874500
 * @Objective Fetch ANC Nurse data.
 * @Date 19-01-2018
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/ANC", headers = "Authorization")
public class ANCFetchController {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private ANCServiceImpl ancServiceImpl;

	@Autowired
	public void setAncServiceImpl(ANCServiceImpl ancServiceImpl) {
		this.ancServiceImpl = ancServiceImpl;
	}

	/**
	 * @Objective Fetching beneficiary visit details enterted by nurse.
	 * @param benRegID and benVisitID
	 * @return visit details in JSON format
	 */

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Visit details from Nurse ANC", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenVisitDetailsFrmNurseANC" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenVisitDetailsFrmNurseANC(
			@ApiParam(value = "{\"benRegID\":\"Long\", \"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for ANC visit data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.length() > 1) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");
				String res = ancServiceImpl.getBenVisitDetailsFrmNurseANC(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid request");
				response.setError(5000, "Invalid request");
			}
			logger.info("ANC visit data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary visit data");
			logger.error("Error while getting beneficiary visit data:" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary anc care details enterted by nurse.
	 * @param benRegID and benVisitID
	 * @return anc care details in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary ANC Care details from Nurse ANC", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenANCDetailsFrmNurseANC" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenANCDetailsFrmNurseANC(
			@ApiParam(value = "{\"benRegID\":\"Long\", \"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for ANC Care data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			Long visitCode = null;
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");

				if (obj.has("visitCode") && !obj.isNull("visitCode") && obj.get("visitCode") != null)
					visitCode = obj.getLong("visitCode");

				String res = ancServiceImpl.getBenANCDetailsFrmNurseANC(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid request");
				response.setError(5000, "Invalid request, Beneficiary information is NULL");
			}
			logger.info("ANC Care data fetching response :" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary ANC care data");
			logger.error("Error while getting beneficiary ANC care data:" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary history details enterted by nurse.
	 * @param benRegID and benVisitID
	 * @return history details in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary ANC History details from Nurse to Doctor ", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenANCHistoryDetails" }, method = { RequestMethod.POST })

	public String getBenANCHistoryDetails(
			@ApiParam(value = "{\"benRegID\":\"Long\", \"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for ANC history data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String s = ancServiceImpl.getBenANCHistoryDetails(benRegID, visitCode);
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("ANC history data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary history data");
			logger.error("Error while getting beneficiary history data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary vital details enterted by nurse.
	 * @param benRegID and benVisitID
	 * @return vital details in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary ANC vital details from Nurse ANC", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenANCVitalDetailsFrmNurseANC" }, method = { RequestMethod.POST })
	public String getBenANCVitalDetailsFrmNurseANC(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for ANC vital data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = ancServiceImpl.getBeneficiaryVitalDetails(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid request");
				response.setError(5000, "Invalid request");
			}
			logger.info("ANC vital data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary vital data");
			logger.error("Error while getting beneficiary vital data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary examination details enterted by nurse.
	 * @param benRegID and benVisitID
	 * @return examination details in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary ANC Examination details from Nurse to Doctor ", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenExaminationDetailsANC" }, method = { RequestMethod.POST })

	public String getBenExaminationDetailsANC(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for ANC examination data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String s = ancServiceImpl.getANCExaminationDetailsData(benRegID, visitCode);
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("ANC examination data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary examination data");
			logger.error("Error while getting beneficiary examination data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary details enterted by doctor.
	 * @param benRegID and benVisitID
	 * @return doctor entered details in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Doctor Entered Details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenCaseRecordFromDoctorANC" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenCaseRecordFromDoctorANC(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for doctor data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (null != obj && obj.length() > 1 && obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = ancServiceImpl.getBenCaseRecordFromDoctorANC(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid request");
				response.setError(5000, "Invalid request");
			}
			logger.info("ANC doctor data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary doctor data");
			logger.error("Error while getting beneficiary doctor data :" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Check HRP- (High Risk Pregnancy) status for ANC beneficiary", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getHRPStatus" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getHRPStatus(@RequestBody HrpStatusAndReasonsRequestModel hrpStatusAndReasonsRequestModel) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for doctor data fetching :" + hrpStatusAndReasonsRequestModel.toString());
		try {

			String res = ancServiceImpl.getHRPStatus(hrpStatusAndReasonsRequestModel);
			response.setResponse(res);

		} catch (Exception e) {
			response.setError(5000, "error in getting HRP status");
			logger.error("error in getting HRP status : " + e);
		}
		return response.toString();
	}

	// changes for get HRP information - follow-up, can be used at other places also
	@Autowired
	private ANCService aNCService;

	@CrossOrigin()
	@ApiOperation(value = "get HRP information (status and reason) for HRP from obstetric history "
			+ " - latest visit available in data store", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getHrpInformation" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getHrpInformation(
			@ApiParam(value = "{\"beneficiaryRegId\":\"Long\"}") @RequestBody CommonUtilityClass commonUtilityClass) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for getHrpInformation :" + commonUtilityClass.toString());
		try {

			SysObstetricExamination sysObstetricExamination = aNCService
					.getHRPInformation(commonUtilityClass.getBeneficiaryRegId());
			if (sysObstetricExamination != null)
				response.setResponse(new Gson().toJson(sysObstetricExamination));
			else
				response.setResponse("no information found");
		} catch (IEMRException e) {
			response.setError(5000, "error in getting HRP information : " + e.getLocalizedMessage());
			logger.error("error in getting HRP information : " + e);
		}
		return response.toString();
	}

}
