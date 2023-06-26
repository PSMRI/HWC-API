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
package com.iemr.hwc.controller.ncdscreening;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.hwc.data.ncdScreening.CbacDetails;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.service.ncdscreening.NCDScreeningServiceImpl;
import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author NA874500
 * @Objective Fetching NCD Screening nurse data.
 * @Date 24-01-2018
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/NCD", headers = "Authorization")
public class NCDFetchController {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private NCDScreeningServiceImpl ncdScreeningServiceImpl;

	@Autowired
	public void setNcdScreeningServiceImpl(NCDScreeningServiceImpl ncdScreeningServiceImpl) {
		this.ncdScreeningServiceImpl = ncdScreeningServiceImpl;
	}

	/**
	 * @Objective Fetching NCD Screening nurse data.
	 * @param benRegID and benVisitID
	 * @return NCD Screening nurse data in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get NCD Screening Visit Details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/get/nurseData" }, method = { RequestMethod.POST })
	public String getNCDScreenigDetails(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {

		OutputResponse response = new OutputResponse();
		logger.info("Request obj to fetch nurse data :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.length() > 1) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String screeningDetails = ncdScreeningServiceImpl.getNCDScreeningDetails(benRegID, visitCode);
				response.setResponse(screeningDetails);
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("NCD Screening nurse data fetch response :" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting NCD Screening data");
			logger.error("Error while getting NCD Screening data :" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "get ncd screening visit count for beneficiaryRegID", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getNcdScreeningVisitCount/{beneficiaryRegID}" }, method = RequestMethod.GET)
	public String getNcdScreeningVisitCount(@PathVariable("beneficiaryRegID") Long beneficiaryRegID) {
		OutputResponse response = new OutputResponse();
		try {
			if (beneficiaryRegID != null) {
				String s = ncdScreeningServiceImpl.getNcdScreeningVisitCnt(beneficiaryRegID);
				if (s != null)
					response.setResponse(s);
				else
					response.setError(5000, "Error while getting NCD screening Visit Count");

			} else {
				response.setError(5000, "Invalid request");
			}
		} catch (Exception e) {
			response.setError(5000, "Error while getting NCD screening Visit Count");
			logger.error("Error while getting NCD screening Visit Count" + e);
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
	@RequestMapping(value = { "/getBenCaseRecordFromDoctorNCDScreening" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenCaseRecordFromDoctorNCDCare(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for NCD Screening doctor data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (null != obj && obj.length() > 1 && obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = ncdScreeningServiceImpl.getBenCaseRecordFromDoctorNCDScreening(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid request");
				response.setError(5000, "Invalid request");
			}
			logger.info("NCD Screening doctor data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary doctor data");
			logger.error("Error while getting beneficiary doctor data :" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Visit details from Nurse NCD Screening", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenVisitDetailsFrmNurseNCDScreening" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenVisitDetailsFrmNurseGOPD(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request obj to fetch NCD screening visit details :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.length() > 1) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = ncdScreeningServiceImpl.getBenVisitDetailsFrmNurseNCDScreening(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenVisitDetailsFrmNurseNCDScreening response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary visit data");
			logger.error("Error in getBenVisitDetailsFrmNurseNCDScreening:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary General OPD History details from Nurse to Doctor ", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenHistoryDetails" }, method = { RequestMethod.POST })

	public String getBenHistoryDetails(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenHistoryDetails request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String s = ncdScreeningServiceImpl.getBenHistoryDetails(benRegID, visitCode);
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenHistoryDetails response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary history data");
			logger.error("Error in getBenHistoryDetails:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary vital details from Nurse GeneralOPD", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenVitalDetailsFrmNurse" }, method = { RequestMethod.POST })
	public String getBenVitalDetailsFrmNurse(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenVitalDetailsFrmNurse request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = ncdScreeningServiceImpl.getBeneficiaryVitalDetails(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenVitalDetailsFrmNurse response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary vital data");
			logger.error("Error in getBenVitalDetailsFrmNurse:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary vital details from Nurse GeneralOPD", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenIdrsDetailsFrmNurse" }, method = { RequestMethod.POST })
	public String getBenIdrsDetailsFrmNurse(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenIdrsDetailsFrmNurse request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = ncdScreeningServiceImpl.getBenIdrsDetailsFrmNurse(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenIdrsDetailsFrmNurse response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary Idrs data");
			logger.error("Error in getBenIdrsDetailsFrmNurse:" + e);

		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Confirmed NCD diseases", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/fetchConfirmedScreeningDisease" }, method = { RequestMethod.POST })
	public String fetchConfirmedScreeningDisease(
			@ApiParam(value = "{\"beneficiaryRegId\":\"Long\"}") @RequestBody CommonUtilityClass commonUtilityClass) {
		OutputResponse response = new OutputResponse();
		try {
			if (commonUtilityClass.getBeneficiaryRegId() == null)
				throw new IEMRException("invalid/NULL beneficiary and/or visit details");
				String res = ncdScreeningServiceImpl.fetchConfirmedScreeningDisease(commonUtilityClass.getBeneficiaryRegId());
				response.setResponse(res);
		} catch (Exception e) {
			response.setError(5000, "Error while getting Confirmed NCD diseases");
			logger.error("Error in fetchConfirmedScreeningDisease:" + e);
		}
		return response.toString();
	}

	// 13-09-2022, get NCD screening data - dia, htn, oral, breast, cervical
	@CrossOrigin()
	@ApiOperation(value = "get NCD screening data - dia, htn, oral, breast, cervical", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/get/ncd/screening/data" }, method = { RequestMethod.POST })
	public String getNCDScreeningData(
			@ApiParam(value = "{\"beneficiaryRegId\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody CommonUtilityClass commonUtilityClass) {

		OutputResponse response = new OutputResponse();
		// logger.info("Request obj to fetch nurse data :" + comingRequest);
		try {

			if (commonUtilityClass.getBeneficiaryRegId() == null || commonUtilityClass.getVisitCode() == null)
				throw new IEMRException("invalid/NULL beneficiary and/or visit details");
			String screeningDetails = ncdScreeningServiceImpl
					.getNCDScreeningData(commonUtilityClass.getBeneficiaryRegId(), commonUtilityClass.getVisitCode());
			response.setResponse(screeningDetails);

		} catch (Exception e) {
			response.setError(5000, "Error while getting captured NCD Screening data ");
			logger.error("Error while getting captured NCD Screening data :" + e);
		}
		return response.toStringWithSerializeNulls();
	}
	
	@CrossOrigin()
	@ApiOperation(value = "get CBAC details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getCbacDetails" }, method = { RequestMethod.POST })
	public String getCbacDetails(
			@ApiParam(value = "{\"beneficiaryRegId\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody CommonUtilityClass commonUtilityClass) {

		OutputResponse response = new OutputResponse();
		try {
			if (commonUtilityClass.getBeneficiaryRegId() == null || commonUtilityClass.getVisitCode() == null)
				throw new IEMRException("invalid/NULL beneficiary and/or visit details");
			String screeningDetails = ncdScreeningServiceImpl
					.getCbacData(commonUtilityClass.getBeneficiaryRegId(), commonUtilityClass.getVisitCode());
			response.setResponse(screeningDetails);
		} catch (Exception e) {
			response.setError(5000, "Error while getting captured CBAC data ");
			logger.error("Error while getting captured CBAC data :" + e);
		}
		return response.toStringWithSerializeNulls();
	}
	
}
