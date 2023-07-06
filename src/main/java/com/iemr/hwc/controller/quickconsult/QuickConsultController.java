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
package com.iemr.hwc.controller.quickconsult;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.data.quickConsultation.WrapperQuickConsultation;
import com.iemr.hwc.service.quickConsultation.QuickConsultationServiceImpl;
import com.iemr.hwc.utils.mapper.InputMapper;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @Objective Saving general OPD quick consult data for Nurse and Doctor both.
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/genOPD-QC-quickConsult", headers = "Authorization")
public class QuickConsultController {
	private Logger logger = LoggerFactory.getLogger(QuickConsultController.class);
	private QuickConsultationServiceImpl quickConsultationServiceImpl;

	@Autowired
	public void setQuickConsultationServiceImpl(QuickConsultationServiceImpl quickConsultationServiceImpl) {
		this.quickConsultationServiceImpl = quickConsultationServiceImpl;
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective first data will be pushed to BenVisitDetails Table and benVisitID
	 *            will be generated and then this benVisitID will be patched with
	 *            Beneficiary Vital and Anthropometry Detail Object and pushed to
	 *            Database table
	 */
	@CrossOrigin
	@ApiOperation(value = "Save quick consult nurse data", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/nurseData" }, method = { RequestMethod.POST })
	public String saveBenQuickConsultDataNurse(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		logger.info("Quick consult nurse data save request : " + requestObj);
		try {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			if (jsnOBJ != null) {
				String r = quickConsultationServiceImpl.quickConsultNurseDataInsert(jsnOBJ, Authorization);
				response.setResponse(r);
			} else {
				response.setError(5000, "Invalid request");
			}
		} catch (Exception e) {
			logger.error("Error while saving quick consult nurse data: " + e);
			response.setError(5000, "Unable to save data");
		}
		return response.toString();
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Save beneficiary data for doctor quick consult - QC.
	 */

	@CrossOrigin
	@ApiOperation(value = "Save quick consult details for doctor", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/doctorData" }, method = { RequestMethod.POST })
	public String saveQuickConsultationDetail(
			@ApiParam(value = "{\"quickConsultation\":{\"beneficiaryRegID\":\"Long\",\"providerServiceMapID\": \"Integer\", \"benVisitID\":\"Long\", \"benChiefComplaint\":[{\"chiefComplaintID\":\"Integer\", "
					+ "\"chiefComplaint\":\"String\", \"duration\":\"Integer\", \"unitOfDuration\":\"String\"}], \"description\":\"String\""
					+ "\"clinicalObservation\":\"String\", \"externalInvestigation\":\"String\", \"diagnosisProvided\":\"String\", \"instruction\":\"String\", \"remarks\":\"String\","
					+ "\"prescribedDrugs\":[{\"drugForm\":\"String\", \"drugTradeOrBrandName\":\"String\", \"genericDrugName\":\"String\", \"drugStrength\":\"String\", "
					+ "\"dose\":\"String\", \"route\":\"String\", \"frequency\":\"String\", \"drugDuration\":\"String\", \"relationToFood\":\"String\", "
					+ "\"specialInstruction\":\"String\"}], \"labTestOrders\":[{\"testID\":\"String\", \"testName\":\"String\", \"testingRequirements\":\"String\","
					+ " \"isRadiologyImaging\":\"String\", \"createdBy\":\"String\"}, {\"testID\":\"Integer\", \"testName\":\"String\", "
					+ "\"testingRequirements\":\"String\", \"isRadiologyImaging\":\"Boolean\"}],"
					+ "\"createdBy\":\"String\"}}") @RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {

		OutputResponse response = new OutputResponse();
		logger.info("Quick consult doctor data save request:" + requestObj);

		try {
			WrapperQuickConsultation wrapperQuickConsultation = InputMapper.gson().fromJson(requestObj,
					WrapperQuickConsultation.class);

			JsonObject quickConsultDoctorOBJ = wrapperQuickConsultation.getQuickConsultation();
			Integer i = quickConsultationServiceImpl.quickConsultDoctorDataInsert(quickConsultDoctorOBJ, Authorization);

			if (i != null && i > 0) {
				response.setResponse("Data saved successfully");
			} else {
				response.setError(5000, "Unable to save data");
			}
			logger.info("Quick consult doctor data save response:" + response);
		} catch (Exception e) {
			logger.error("Error while saving quick consult doctor data:" + e);
			response.setError(5000, "Unable to save data. " + e.getMessage());
		}

		return response.toString();
	}

	/**
	 * @Objective Fething beneficiary visit data filled by Nurse for Doctor
	 *            screen...
	 * @param benRegID and benVisitID
	 * @return visit details in JSON format
	 */

	@CrossOrigin()
	@ApiOperation(value = "Get quick consult beneficiary visit details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenDataFrmNurseToDocVisitDetailsScreen" }, method = { RequestMethod.POST })
	public String getBenDataFrmNurseScrnToDocScrnVisitDetails(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		logger.info("Quick consult visit data fetch request :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.length() > 1) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String s = quickConsultationServiceImpl.getBenDataFrmNurseToDocVisitDetailsScreen(benRegID, visitCode);
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("Quick consult visit data fetch response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting visit data");
			logger.error("Error while fetching quick consult visit data:" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fething beneficiary vital data filled by Nurse for Doctor
	 *            screen...
	 * @param benRegID and benVisitID
	 * @return visit details in JSON format
	 */

	@CrossOrigin()
	@ApiOperation(value = "Get quick consult beneficiary vital details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenVitalDetailsFrmNurse" }, method = { RequestMethod.POST })
	public String getBenVitalDetailsFrmNurse(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Quick consult vital data fetch request :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = quickConsultationServiceImpl.getBeneficiaryVitalDetails(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid request");
				response.setError(5000, "Invalid request");
			}
			logger.info("Quick consult vital data fetch response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting vital data");
			logger.error("Error while fetching quick consult vital data:" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary doctor data
	 * @param benRegID and benVisitID
	 * @return visit details in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get quick consult beneficiary case record", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenCaseRecordFromDoctorQuickConsult" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenCaseRecordFromDoctorQuickConsult(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Quick consult doctor data fetch request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (null != obj && obj.length() > 1 && obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = quickConsultationServiceImpl.getBenCaseRecordFromDoctorQuickConsult(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid request");
				response.setError(5000, "Invalid request");
			}
			logger.info("Quick consult doctor data fetch response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting doctor data");
			logger.error("Error while fetching quick consult doctor data:" + e);
		}
		return response.toString();
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Replace General OPD doctor data for the doctor next visit
	 * 
	 */

	@CrossOrigin
	@ApiOperation(value = "Update quick consult doctor data", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/doctorData" }, method = { RequestMethod.POST })
	public String updateGeneralOPDQCDoctorData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {

		OutputResponse response = new OutputResponse();
		logger.info("Quick consult doctor data update request:" + requestObj);

		JsonObject jsnOBJ = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElmnt = jsnParser.parse(requestObj);
		jsnOBJ = jsnElmnt.getAsJsonObject();

		try {
			WrapperQuickConsultation wrapperQuickConsultation = InputMapper.gson().fromJson(requestObj,
					WrapperQuickConsultation.class);

			JsonObject quickConsultDoctorOBJ = wrapperQuickConsultation.getQuickConsultation();

			Long result = quickConsultationServiceImpl.updateGeneralOPDQCDoctorData(quickConsultDoctorOBJ,
					Authorization);
			if (null != result && result > 0) {
				response.setResponse("Data updated successfully");
			} else {
				response.setError(500, "Unable to modify data");
			}
			logger.info("Quick consult doctor data update response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Unable to modify data");
			logger.error("Error while updating quick consult doctor data :" + e);
		}

		return response.toString();
	}

}
