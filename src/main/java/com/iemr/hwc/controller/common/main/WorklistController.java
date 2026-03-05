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
package com.iemr.hwc.controller.common.main;

import jakarta.ws.rs.core.MediaType;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.hwc.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonServiceImpl;
import com.iemr.hwc.utils.mapper.InputMapper;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/common", headers = "Authorization", consumes = "application/json", produces = "application/json")
public class WorklistController {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	private CommonDoctorServiceImpl commonDoctorServiceImpl;
	private CommonNurseServiceImpl commonNurseServiceImpl;
	private CommonServiceImpl commonServiceImpl;

	@Autowired
	public void setCommonServiceImpl(CommonServiceImpl commonServiceImpl) {
		this.commonServiceImpl = commonServiceImpl;
	}

	@Autowired
	public void setCommonDoctorServiceImpl(CommonDoctorServiceImpl commonDoctorServiceImpl) {
		this.commonDoctorServiceImpl = commonDoctorServiceImpl;
	}

	@Autowired
	public void setCommonNurseServiceImpl(CommonNurseServiceImpl commonNurseServiceImpl) {
		this.commonNurseServiceImpl = commonNurseServiceImpl;
	}

	@Operation(summary = "Get doctor worklist")
	@GetMapping(value = { "/getDocWorklistNew/{providerServiceMapID}/{serviceID}/{vanID}" })
	public ResponseEntity<String> getDocWorkListNew(@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("serviceID") Integer serviceID, @PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		if (providerServiceMapID != null && serviceID != null) {
			String s = commonDoctorServiceImpl.getDocWorkListNew(providerServiceMapID, serviceID, vanID);
			if (s != null)
				response.setResponse(s);
		} else {
			logger.error("Invalid request, either ProviderServiceMapID or ServiceID is invalid. PSMID = "
					+ providerServiceMapID + " SID = " + serviceID);
			response.setError(5000, "Invalid request, either ProviderServiceMapID or ServiceID is invalid");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}

	// doc worklist new (TM future scheduled beneficiary)
	@Operation(summary = "Get doctor future worklist scheduled for telemedicine")
	@GetMapping(value = { "/getDocWorkListNewFutureScheduledForTM/{providerServiceMapID}/{serviceID}/{vanID}" })
	public ResponseEntity<String> getDocWorkListNewFutureScheduledForTM(
			@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("serviceID") Integer serviceID, @PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		if (providerServiceMapID != null && serviceID != null) {
			String s = commonDoctorServiceImpl.getDocWorkListNewFutureScheduledForTM(providerServiceMapID,
					serviceID, vanID);
			if (s != null)
				response.setResponse(s);
		} else {
			logger.error("Invalid request, either ProviderServiceMapID or ServiceID is invalid. PSMID = "
					+ providerServiceMapID + " SID = " + serviceID);
			response.setError(5000, "Invalid request, either ProviderServiceMapID or ServiceID is invalid");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}

	// nurse worklist new
	@Operation(summary = "Get nurse worklist")
	@GetMapping(value = { "/getNurseWorklistNew/{providerServiceMapID}/{serviceID}/{vanID}" })
	public ResponseEntity<String> getNurseWorkListNew(
			@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		String s = commonNurseServiceImpl.getNurseWorkListNew(providerServiceMapID, vanID);
		if (s != null)
			response.setResponse(s);
		else {
			response.setError(5000, "Error while getting nurse worklist");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}

	// nurse worklist TC schedule (current-date) new
	@Operation(summary = "Get worklist for teleconsultation for the current date")
	@GetMapping(value = { "/getNurseWorkListTcCurrentDate/{providerServiceMapID}/{serviceID}/{vanID}" })
	public ResponseEntity<String> getNurseWorkListTcCurrentDateNew(
			@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		String s = commonNurseServiceImpl.getNurseWorkListTcCurrentDate(providerServiceMapID, vanID);
		if (s != null)
			response.setResponse(s);
		else {
			response.setError(5000, "Error while getting nurse worklist");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}

	// nurse worklist TC schedule (future-date) new
	@Operation(summary = "Get worklist for teleconsultation for the future date")
	@GetMapping(value = { "/getNurseWorkListTcFutureDate/{providerServiceMapID}/{serviceID}/{vanID}" })
	public ResponseEntity<String> getNurseWorkListTcFutureDateNew(
			@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		String s = commonNurseServiceImpl.getNurseWorkListTcFutureDate(providerServiceMapID, vanID);
		if (s != null)
			response.setResponse(s);
		else {
			response.setError(5000, "Error while getting nurse worklist");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}

	@Operation(summary = "Get previous significant findings")
	@PostMapping(value = { "/getDoctorPreviousSignificantFindings" })
	public ResponseEntity<String> getDoctorPreviousSignificantFindings(
			@Param(value = "{\"beneficiaryRegID\": \"Long\"}") @RequestBody String comingRequest) throws Exception {
		OutputResponse response = new OutputResponse();
		JSONObject obj = new JSONObject(comingRequest);
		if (obj != null && obj.has("beneficiaryRegID") && !obj.isNull("beneficiaryRegID")) {
			Long beneficiaryRegID = obj.getLong("beneficiaryRegID");
			String s = commonDoctorServiceImpl.fetchBenPreviousSignificantFindings(beneficiaryRegID);
			if (s != null)
				response.setResponse(s);
			else {
				response.setError(5000, "Error while getting previous significant findings");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
			}
		} else {
			response.setError(5000, "Invalid data!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}

	// Get Lab technician worklist new
	@Operation(summary = "Get lab technician worklist")
	@GetMapping(value = { "/getLabWorklistNew/{providerServiceMapID}/{serviceID}/{vanID}" })
	public ResponseEntity<String> getLabWorkListNew(@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		String s = commonNurseServiceImpl.getLabWorkListNew(providerServiceMapID, vanID);
		if (s != null)
			response.setResponse(s);
		else {
			response.setError(5000, "Error while getting lab technician worklist");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}

	// Get radiologist worklist new
	@Operation(summary = "Get radiologist worklist")
	@PostMapping(value = { "/getRadiologist-worklist-New/{providerServiceMapID}/{serviceID}/{vanID}" })
	public ResponseEntity<String> getRadiologistWorklistNew(
			@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		String s = commonNurseServiceImpl.getRadiologistWorkListNew(providerServiceMapID, vanID);
		if (s != null)
			response.setResponse(s);
		else {
			response.setError(5000, "Error while getting radiologist worklist");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}

	// Get oncologist worklist new
	@Operation(summary = "Get oncologist worklist")
	@PostMapping(value = { "/getOncologist-worklist-New/{providerServiceMapID}/{serviceID}/{vanID}" })
	public ResponseEntity<String> getOncologistWorklistNew(
			@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		String s = commonNurseServiceImpl.getOncologistWorkListNew(providerServiceMapID, vanID);
		if (s != null)
			response.setResponse(s);
		else {
			response.setError(5000, "Error while getting oncologist worklist");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}

	// Get pharma worklist new
	@Operation(summary = "Get pharmacist worklist")
	@GetMapping(value = { "/getPharma-worklist-New/{providerServiceMapID}/{serviceID}/{vanID}" })
	public ResponseEntity<String> getPharmaWorklistNew(
			@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		String s = commonNurseServiceImpl.getPharmaWorkListNew(providerServiceMapID, vanID);
		if (s != null)
			response.setResponse(s);
		else {
			response.setError(5000, "Error while getting pharma worklist");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}

	@Operation(summary = "Print case sheet of beneficiary.")
	@PostMapping(value = { "/get/Case-sheet/printData" })
	public ResponseEntity<String> getCasesheetPrintData(@RequestBody String comingReq,
			@RequestHeader(value = "Authorization") String Authorization) throws Exception {
		OutputResponse response = new OutputResponse();
		if (comingReq != null) {
			BeneficiaryFlowStatus obj = InputMapper.gson().fromJson(comingReq, BeneficiaryFlowStatus.class);
			String casesheetData = commonServiceImpl.getCaseSheetPrintDataForBeneficiary(obj, Authorization);
			response.setResponse(casesheetData);
		} else {
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}

	// Start of Fetch Previous Medical History...
	@Operation(summary = "Get beneficiary history")
	@PostMapping(value = { "/getBenPastHistory" })
	public ResponseEntity<String> getBenPastHistory(
			@Param(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("getBenPastHistory request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("benRegID") && !obj.isNull("benRegID")) {
			Long benRegID = obj.getLong("benRegID");
			String s = commonServiceImpl.getBenPastHistoryData(benRegID);
			response.setResponse(s);
		} else {
			logger.info("Invalid Request Data.");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("getBenPastHistory response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	@Operation(summary = "Get beneficiary tobacco consumption history")
	@PostMapping(value = { "/getBenTobaccoHistory" })
	public ResponseEntity<String> getBenTobaccoHistory(
			@Param(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("getBenTobaccoHistory request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("benRegID") && !obj.isNull("benRegID")) {
			Long benRegID = obj.getLong("benRegID");
			String s = commonServiceImpl.getPersonalTobaccoHistoryData(benRegID);
			response.setResponse(s);
		} else {
			logger.info("Invalid Request Data.");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("getBenTobaccoHistory response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	@Operation(summary = "Get beneficiary alcohol consumption history")
	@PostMapping(value = { "/getBenAlcoholHistory" })
	public ResponseEntity<String> getBenAlcoholHistory(
			@Param(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("getBenAlcoholHistory request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("benRegID") && !obj.isNull("benRegID")) {
			Long benRegID = obj.getLong("benRegID");
			String s = commonServiceImpl.getPersonalAlcoholHistoryData(benRegID);
			response.setResponse(s);
		} else {
			logger.info("Invalid Request Data.");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("getBenAlcoholHistory response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	@Operation(summary = "Get beneficiary allergy history")
	@PostMapping(value = { "/getBenAllergyHistory" })
	public ResponseEntity<String> getBenANCAllergyHistory(
			@Param(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("getBenAllergyHistory request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("benRegID") && !obj.isNull("benRegID")) {
			Long benRegID = obj.getLong("benRegID");
			String s = commonServiceImpl.getPersonalAllergyHistoryData(benRegID);
			response.setResponse(s);
		} else {
			logger.info("Invalid Request Data.");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("getBenAllergyHistory response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	@Operation(summary = "Get beneficiary referral details by created by")
	@PostMapping(value = { "/getBenReferDetailsByCreatedBy" }, headers = "Authorization")
	public ResponseEntity<String> getBenReferDetailsByCreatedBy(
			@Param(value = "{\"createdBy\":\"String\"}") @RequestBody String comingRequest,
			@RequestHeader(value = "Authorization") String Authorization) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("getBenReferDetailsByCreatedBy request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("createdBy") && !obj.isNull("createdBy")) {
			String createdBy = obj.getString("createdBy");
			String s = commonDoctorServiceImpl.getBenReferDetailsByCreatedBy(createdBy);
			response.setResponse(s);
		} else {
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}

	@Operation(summary = "Get beneficiary medication history")
	@PostMapping(value = { "/getBenMedicationHistory" })
	public String getBenMedicationHistory(@Param(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenMedicationHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getMedicationHistoryData(benRegID);
				response.setResponse(s);
			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenMedicationHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting medication history");
			logger.error("Error in getBenMedicationHistory:" + e);
		}
		return response.toString();
	}

	@Operation(summary = "Get beneficiary family history")
	@PostMapping(value = { "/getBenFamilyHistory" })
	public ResponseEntity<String> getBenFamilyHistory(
			@Param(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("getBenFamilyHistory request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("benRegID") && !obj.isNull("benRegID")) {
			Long benRegID = obj.getLong("benRegID");
			String s = commonServiceImpl.getFamilyHistoryData(benRegID);
			response.setResponse(s);
		} else {
			logger.info("Invalid Request Data.");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("getBenFamilyHistory response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	@Operation(summary = "Get beneficiary menstrual history")
	@PostMapping(value = { "/getBenMenstrualHistory" })
	public ResponseEntity<String> getBenMenstrualHistory(
			@Param(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("getBenMenstrualHistory request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("benRegID") && !obj.isNull("benRegID")) {
			Long benRegID = obj.getLong("benRegID");
			String s = commonServiceImpl.getMenstrualHistoryData(benRegID);
			response.setResponse(s);
		} else {
			logger.info("Invalid Request Data.");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("getBenMenstrualHistory response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	@Operation(summary = "Get beneficiary obstetrics history")
	@PostMapping(value = { "/getBenPastObstetricHistory" })
	public ResponseEntity<String> getBenPastObstetricHistory(
			@Param(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("getBenPastObstetricHistory request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("benRegID") && !obj.isNull("benRegID")) {
			Long benRegID = obj.getLong("benRegID");
			String s = commonServiceImpl.getObstetricHistoryData(benRegID);
			response.setResponse(s);
		} else {
			logger.info("Invalid Request Data.");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("getBenPastObstetricHistory response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	@Operation(summary = "Get beneficiary comorbidity condition details")
	@PostMapping(value = { "/getBenComorbidityConditionHistory" })
	public ResponseEntity<String> getBenANCComorbidityConditionHistory(
			@Param(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("getBenComorbidityConditionHistory request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("benRegID") && !obj.isNull("benRegID")) {
			Long benRegID = obj.getLong("benRegID");
			String s = commonServiceImpl.getComorbidHistoryData(benRegID);
			response.setResponse(s);
		} else {
			logger.info("Invalid Request Data.");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("getBenComorbidityConditionHistory response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	@Operation(summary = "Get beneficiary optional vaccine details")
	@PostMapping(value = { "/getBenOptionalVaccineHistory" })
	public ResponseEntity<String> getBenOptionalVaccineHistory(
			@Param(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("getBenOptionalVaccineHistory request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("benRegID") && !obj.isNull("benRegID")) {
			Long benRegID = obj.getLong("benRegID");
			String s = commonServiceImpl.getChildVaccineHistoryData(benRegID);
			response.setResponse(s);
		} else {
			logger.info("Invalid Request Data.");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("getBenOptionalVaccineHistory response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	@Operation(summary = "Get child beneficiary vaccine details")
	@PostMapping(value = { "/getBenChildVaccineHistory" })
	public ResponseEntity<String> getBenImmunizationHistory(
			@Param(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("getBenImmunizationHistory request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("benRegID") && !obj.isNull("benRegID")) {
			Long benRegID = obj.getLong("benRegID");
			String s = commonServiceImpl.getImmunizationHistoryData(benRegID);
			response.setResponse(s);
		} else {
			logger.info("Invalid Request Data.");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("getBenImmunizationHistory response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	@Operation(summary = "Get beneficiary perinatal history")
	@PostMapping(value = { "/getBenPerinatalHistory" })
	public ResponseEntity<String> getBenPerinatalHistory(
			@Param(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("getBenPerinatalHistory request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("benRegID") && !obj.isNull("benRegID")) {
			Long benRegID = obj.getLong("benRegID");
			String s = commonServiceImpl.getBenPerinatalHistoryData(benRegID);
			response.setResponse(s);
		} else {
			logger.info("Invalid Request Data.");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("getBenPerinatalHistory response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	@Operation(summary = "Get child beneficiary feeding history")
	@PostMapping(value = { "/getBenFeedingHistory" })
	public ResponseEntity<String> getBenFeedingHistory(
			@Param(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("getBenFeedingHistory request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("benRegID") && !obj.isNull("benRegID")) {
			Long benRegID = obj.getLong("benRegID");
			String s = commonServiceImpl.getBenFeedingHistoryData(benRegID);
			response.setResponse(s);
		} else {
			logger.info("Invalid Request Data.");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("getBenFeedingHistory response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	@Operation(summary = "Get child beneficiary development history")
	@PostMapping(value = { "/getBenDevelopmentHistory" })
	public ResponseEntity<String> getBenDevelopmentHistory(
			@Param(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("getBenDevelopmentHistory request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("benRegID") && !obj.isNull("benRegID")) {
			Long benRegID = obj.getLong("benRegID");
			String s = commonServiceImpl.getBenDevelopmentHistoryData(benRegID);
			response.setResponse(s);
		} else {
			logger.info("Invalid Request Data.");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("getBenDevelopmentHistory response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	// End of Fetch Previous History...

	/***
	 * fetch ben previous visit details for history case-record(Platform).
	 * 08-08-2018
	 */
	@Operation(summary = "Get beneficiary casesheet history")
	@PostMapping(value = { "/getBeneficiaryCaseSheetHistory" })
	public ResponseEntity<String> getBeneficiaryCaseSheetHistory(
			@Param(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for fetching beneficiary previous visit history :" + comingRequest);
		String responseData = commonServiceImpl.getBenPreviousVisitDataForCaseRecord(comingRequest);
		if (responseData != null)
			response.setResponse(responseData);
		else {
			response.setError(5000, "Error while fetching beneficiary previous visit history details");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}

	// TC specialist worklist new
	@Operation(summary = "Get teleconsultation specialist worklist")
	@GetMapping(value = { "/getTCSpecialistWorklist/{providerServiceMapID}/{serviceID}/{userID}" })
	public ResponseEntity<String> getTCSpecialistWorkListNew(
			@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("userID") Integer userID, @PathVariable("serviceID") Integer serviceID) {
		OutputResponse response = new OutputResponse();
		if (providerServiceMapID != null && userID != null) {
			String s = commonDoctorServiceImpl.getTCSpecialistWorkListNewForTM(providerServiceMapID, userID,
					serviceID);
			if (s != null)
				response.setResponse(s);
		} else {
			logger.error("Invalid request, either ProviderServiceMapID or userID is invalid. PSMID = "
					+ providerServiceMapID + " SID = " + userID);
			response.setError(5000, "Invalid request, either ProviderServiceMapID or userID is invalid");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}

	// TC specialist worklist new, patient App, 14-08-2020
	@Operation(summary = "Get teleconsultation specialist worklist for patient app")
	@GetMapping(value = { "/getTCSpecialistWorklistPatientApp/{providerServiceMapID}/{serviceID}/{userID}/{vanID}" })
	public ResponseEntity<String> getTCSpecialistWorkListNewPatientApp(
			@PathVariable("providerServiceMapID") Integer providerServiceMapID, @PathVariable("userID") Integer userID,
			@PathVariable("serviceID") Integer serviceID, @PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		if (providerServiceMapID != null && userID != null) {
			String s = commonDoctorServiceImpl.getTCSpecialistWorkListNewForTMPatientApp(providerServiceMapID,
					userID, serviceID, vanID);
			if (s != null)
				response.setResponse(s);
		} else {
			logger.error("Invalid request, either ProviderServiceMapID or userID is invalid. PSMID = "
					+ providerServiceMapID + " SID = " + userID);
			response.setError(5000, "Invalid request, either ProviderServiceMapID or userID is invalid");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}

	// TC specialist worklist new future scheduled
	@Operation(summary = "Get teleconsultation specialist future scheduled")
	@GetMapping(value = {
			"/getTCSpecialistWorklistFutureScheduled/{providerServiceMapID}/{serviceID}/{userID}" })
	public ResponseEntity<String> getTCSpecialistWorklistFutureScheduled(
			@PathVariable("providerServiceMapID") Integer providerServiceMapID, @PathVariable("userID") Integer userID,
			@PathVariable("serviceID") Integer serviceID) {
		OutputResponse response = new OutputResponse();
		if (providerServiceMapID != null && userID != null) {
			String s = commonDoctorServiceImpl.getTCSpecialistWorkListNewFutureScheduledForTM(providerServiceMapID,
					userID, serviceID);
			if (s != null)
				response.setResponse(s);
		} else {
			logger.error("Invalid request, either ProviderServiceMapID or userID is invalid. PSMID = "
					+ providerServiceMapID + " UserID = " + userID);
			response.setError(5000, "Invalid request, either ProviderServiceMapID or userID is invalid");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}

	// openkm file download
	@Operation(summary = "Add file as string to openKM.")
	@PostMapping(value = "/getKMFile", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON, headers = "Authorization")
	public ResponseEntity<String> getKMFile(@Param(value = "{}") @RequestBody String request,
			@RequestHeader(value = "Authorization") String Authorization) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("add file request is " + request);
		String s = commonServiceImpl.getOpenKMDocURL(request, Authorization);
		if (s != null)
			response.setResponse(s);
		else {
			logger.error("file not available for request : " + request);
			response.setError(5000, "file not available, please contact administrator");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}

	@Operation(summary = "Get beneficiary physical history")
	@PostMapping(value = { "/getBenPhysicalHistory" })
	public ResponseEntity<String> getBenPhysicalHistory(
			@Param(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("getBenPhysicalHistory request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("benRegID") && !obj.isNull("benRegID")) {
			Long benRegID = obj.getLong("benRegID");
			String s = commonServiceImpl.getBenPhysicalHistory(benRegID);
			response.setResponse(s);
		} else {
			logger.info("Invalid Request Data.");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("getBenPhysicalHistory response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	@Operation(summary = "Get beneficiary symptomatic questionnaire answer details")
	@PostMapping(value = { "/getBenSymptomaticQuestionnaireDetails" })
	public ResponseEntity<String> getBenSymptomaticQuestionnaireDetails(
			@Param(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("Get Beneficiary Symptomatic questionnaire answer details request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("benRegID") && !obj.isNull("benRegID")) {
			Long benRegID = obj.getLong("benRegID");
			String s = commonServiceImpl.getBenSymptomaticQuestionnaireDetailsData(benRegID);
			response.setResponse(s);
		} else {
			logger.info("Invalid Request Data.");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("Get Beneficiary Symptomatic questionnaire answer details response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	@Operation(summary = "Get beneficiary previous biabetes history")
	@PostMapping(value = { "/getBenPreviousDiabetesHistoryDetails" })
	public ResponseEntity<String> getBenPreviousDiabetesHistoryDetails(
			@Param(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) throws Exception {
		OutputResponse response = new OutputResponse();

		logger.info("Get Beneficiary previous Diabetes history request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("benRegID") && !obj.isNull("benRegID")) {
			Long benRegID = obj.getLong("benRegID");
			String s = commonServiceImpl.getBenPreviousDiabetesData(benRegID);
			response.setResponse(s);
		} else {
			logger.info("Invalid Request Data.");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("Get Beneficiary previous Diabetes history response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	// nurse worklist coming from MMU application
	@Operation(summary = "Get mmu nurse worklist")
	@GetMapping(value = { "/getMmuNurseWorklistNew/{providerServiceMapID}/{vanID}" })
	public ResponseEntity<String> getMmuNurseWorklistNew(
			@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("vanID") Integer vanID) throws Exception {
		OutputResponse response = new OutputResponse();
		String s = commonNurseServiceImpl.getMmuNurseWorkListNew(providerServiceMapID, vanID);
		if (s != null)
			response.setResponse(s);
		else {
			response.setError(5000, "Error while getting MMU Nurse Worklist");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}

	@Operation(summary = "Get beneficiary previous Referral history")
	@PostMapping(value = { "/getBenPreviousReferralHistoryDetails" })
	public ResponseEntity<String> getBenPreviousReferralHistoryDetails(
			@Param(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) throws Exception {
		OutputResponse response = new OutputResponse();

		logger.info("Get Beneficiary previous Referral history request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("benRegID") && !obj.isNull("benRegID")) {
			Long benRegID = obj.getLong("benRegID");
			String s = commonServiceImpl.getBenPreviousReferralData(benRegID);
			response.setResponse(s);
		} else {
			logger.info("Invalid Request Data.");
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("Get Beneficiary previous Referral history response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * Author SH20094090
	 * 
	 * @param comingRequest
	 * @return ProviderSpecificMasterData
	 */
	@Operation(summary = "Get provider specific data")
	@PostMapping(value = { "/getProviderSpecificData" })
	public ResponseEntity<String> getProviderSpecificData(
			@Param(value = "{\"benvisitID\":\"Long\",\"benvisitCode\":\"Long\",\"fetchMMUDataFor\":\"String\"}") @RequestBody String comingRequest)
			throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("getProviderSpecificData request:" + comingRequest);
		String s = commonServiceImpl.getProviderSpecificData(comingRequest);
		response.setResponse(s);
		logger.info("getProviderSpecificData response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * Author SH20094090
	 * 
	 * @param comingRequest
	 * @return ProviderSpecificMasterData
	 */
	@Operation(summary = "Calculate beneficiary BMI status")
	@PostMapping(value = { "/calculateBMIStatus" })
	public ResponseEntity<String> calculateBMIStatus(
			@Param(value = "{\"bmi\":\"double\",\"yearMonth\":\"String\",\"gender\":\"String\"}") @RequestBody String comingRequest)
			throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("calculateBMIStatus request:" + comingRequest);
		String s = commonNurseServiceImpl.calculateBMIStatus(comingRequest);
		response.setResponse(s);
		logger.info("calculateBMIStatus response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	/**
	 * @Objective Fetching beneficiary immunization service history.
	 * @param { "benRegID": "274685" }
	 * @return immunization service details in JSON format
	 */
	@Operation(summary = "Get beneficiary immunization history ")
	@PostMapping(value = { "/getBenImmunizationServiceHistory" })
	public ResponseEntity<String> getBenImmunizationServiceHistory(
			@Param(value = "{\"benRegID\":\"Long\"}") @RequestBody CommonUtilityClass commonUtilityClass)
			throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("getBenImmunizationServiceDetails history request:" + commonUtilityClass.toString());
		if (commonUtilityClass.getBenRegID() != null) {
			String s = commonServiceImpl.getBenImmunizationServiceHistory(commonUtilityClass.getBenRegID());
			response.setResponse(s);
		} else {
			response.setError(5000, "Invalid request : missing ben information");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("getBenImmunizationServiceDetails history response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	// this functionality are moved to registrar update controller.
	// 16-04-2018, Neeraj kumar
	@Operation(summary = "Update beneficiary status flag")
	@PostMapping(value = { "/update/benDetailsAndSubmitToNurse" })
	public ResponseEntity<String> saveBeneficiaryVisitDetail(
			@Param(value = "{\"beneficiaryRegID\": \"Long\"}") @RequestBody String comingRequest) throws Exception {

		OutputResponse response = new OutputResponse();
		logger.info("benDetailsAndSubmitToNurse request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("beneficiaryRegID") && !obj.isNull("beneficiaryRegID")) {
			if (obj.getLong("beneficiaryRegID") > 0) {

				Integer i = commonNurseServiceImpl.updateBeneficiaryStatus('R', obj.getLong("beneficiaryRegID"));
				if (i != null && i > 0) {
					response.setResponse("Beneficiary Successfully Submitted to Nurse Work-List.");
				} else {
					response.setError(500, "Something went Wrong please try after Some Time !!!");
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
				}
			} else {
				response.setError(400, "Beneficiary Registration ID is Not valid !!!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
			}
		} else {
			response.setError(400, "Beneficiary Registration ID is Not valid !!!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("benDetailsAndSubmitToNurse response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	@Operation(summary = "Extend redis session for 30 mins")
	@PostMapping(value = { "/extend/redisSession" })
	public ResponseEntity<String> extendRedisSession() {
		OutputResponse response = new OutputResponse();
		// temp code later have to validate the user also.
		response.setResponse("Session extended for 30 mins");
		return ResponseEntity.ok(response.toString());
	}

	@Operation(summary = "Soft delete prescribed medicine")
	@PostMapping(value = { "/doctor/delete/prescribedMedicine" })
	public ResponseEntity<String> deletePrescribedMedicine(@RequestBody String requestOBJ) throws Exception {
		OutputResponse response = new OutputResponse();
		if (requestOBJ != null) {
			JSONObject obj = new JSONObject(requestOBJ);
			String s = commonDoctorServiceImpl.deletePrescribedMedicine(obj);
			if (s != null)
				response.setResponse(s);
			else {
				response.setError(5000, "error while deleting record");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
			}
		} else {
			response.setError(400, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}
}
