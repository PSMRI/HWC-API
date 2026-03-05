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
package com.iemr.hwc.controller.registrar.main;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iemr.hwc.data.registrar.BeneficiaryData;
import com.iemr.hwc.data.registrar.V_BenAdvanceSearch;
import com.iemr.hwc.data.registrar.WrapperBeneficiaryRegistration;
import com.iemr.hwc.service.common.master.RegistrarServiceMasterDataImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.nurse.NurseServiceImpl;
import com.iemr.hwc.service.registrar.RegistrarServiceImpl;
import com.iemr.hwc.utils.mapper.InputMapper;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/registrar", headers = "Authorization", consumes = "application/json", produces = "application/json")
/**
 * Objective: Performs QuickSearch, AdvancedSearch and fetching Beneficiary
 * Details
 */
public class RegistrarController {
	private Logger logger = LoggerFactory.getLogger(RegistrarController.class);
	private InputMapper inputMapper = new InputMapper();
	private RegistrarServiceImpl registrarServiceImpl;
	private RegistrarServiceMasterDataImpl registrarServiceMasterDataImpl;
	private NurseServiceImpl nurseServiceImpl;
	private CommonNurseServiceImpl commonNurseServiceImpl;

	@Autowired
	public void setRegistrarServiceImpl(RegistrarServiceImpl registrarServiceImpl) {
		this.registrarServiceImpl = registrarServiceImpl;
	}

	@Autowired
	public void setRegistrarServiceMasterDataImpl(RegistrarServiceMasterDataImpl registrarServiceMasterDataImpl) {
		this.registrarServiceMasterDataImpl = registrarServiceMasterDataImpl;
	}

	@Autowired
	public void setNurseServiceImpl(NurseServiceImpl nurseServiceImpl) {
		this.nurseServiceImpl = nurseServiceImpl;
	}

	// Registrar Work List API .....
	@Operation(summary = "Get registrar worklist data")
	@PostMapping(value = { "/registrarWorkListData" })
	public ResponseEntity<String> getRegistrarWorkList(
			@Param(value = "{\"spID\": \"Integer\"}") @RequestBody String comingRequest)
			throws JSONException {
		OutputResponse response = new OutputResponse();
		logger.info("getRegistrarWorkList request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		response.setResponse(this.registrarServiceImpl.getRegWorkList(obj.getInt("spID")));
		logger.info("getRegistrarWorkList response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	// Registrar Quick search .....
	@Operation(summary = "Search for beneficiary based on beneficiary id")
	@PostMapping(value = { "/quickSearch" })
	public ResponseEntity<String> quickSearchBeneficiary(
			@Param(value = "{\"benID\": \"String\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		logger.info("quickSearchBeneficiary request:" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		response.setResponse(registrarServiceImpl.getQuickSearchBenData(obj.getString("benID")));
		logger.info("quickSearchBeneficiary response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	// Registrar Advance search .....
	@Operation(summary = "Search for beneficiary based on provided data")
	@PostMapping(value = { "/advanceSearch" })
	public ResponseEntity<String> advanceSearch(
			@Param(value = "{\"firstName\": \"String\", \"lastName\": \"String\", \"phoneNo\": \"String\","
					+ "\"beneficiaryID\": \"String\", \"stateID\": \"Integer\", \"districtID\": \"Integer\", \"aadharNo\": \"String\"},"
					+ " \"govtIdentityNo\": \"String\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		logger.info("advanceSearch request :" + comingRequest);
		V_BenAdvanceSearch v_BenAdvanceSearch = inputMapper.gson().fromJson(comingRequest,
				V_BenAdvanceSearch.class);
		response.setResponse(registrarServiceImpl.getAdvanceSearchBenData(v_BenAdvanceSearch));
		logger.info("advanceSearch response:" + response);

		return ResponseEntity.ok(response.toString());
	}

	// API for left side ben data
	@Operation(summary = "Get beneficiary details based on beneficiary register id")
	@PostMapping(value = { "/get/benDetailsByRegID" })
	public ResponseEntity<String> getBenDetailsByRegID(
			@Param(value = "{\"beneficiaryRegID\": \"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		logger.info("getBenDetailsByRegID request :" + comingRequest);

		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("beneficiaryRegID")) {
			if (obj.getLong("beneficiaryRegID") > 0) {

				String beneficiaryData = registrarServiceMasterDataImpl
						.getBenDetailsByRegID(obj.getLong("beneficiaryRegID"));

				response.setResponse(beneficiaryData);
			} else {
				throw new IllegalArgumentException("Please pass beneficiaryRegID");
			}
		} else {
			throw new IllegalArgumentException("Bad Request... beneficiaryRegID is not there in request");
		}
		logger.info("getBenDetailsByRegID response :" + response);
		return ResponseEntity.ok(response.toString());
	}

	@Operation(summary = "Get beneficiary details")
	@PostMapping(value = { "/get/beneficiaryDetails" })
	public ResponseEntity<String> getBeneficiaryDetails(
			@Param(value = "{\"beneficiaryRegID\": \"Long\"}") @RequestBody String requestObj) {
		OutputResponse response = new OutputResponse();
		logger.info("getBeneficiaryDetails request :" + requestObj);

		JSONObject obj = new JSONObject(requestObj);
		if (obj.has("beneficiaryRegID")) {
			if (obj.getLong("beneficiaryRegID") > 0) {

				String beneficiaryData = registrarServiceImpl
						.getBeneficiaryDetails(obj.getLong("beneficiaryRegID"));
				if (beneficiaryData != null) {
					response.setResponse(beneficiaryData);
				} else {
					Map<String, String> noDataMap = new HashMap<>();
					response.setResponse(new Gson().toJson(noDataMap));
				}

			} else {
				throw new IllegalArgumentException("Please pass beneficiaryRegID");
			}
		} else {
			throw new IllegalArgumentException("Bad Request... beneficiaryRegID is not there in request");
		}
		logger.info("getBeneficiaryDetails response :" + response);
		return ResponseEntity.ok(response.toString());
	}

	@Operation(summary = "Get beneficiary image")
	@PostMapping(value = { "/get/beneficiaryImage" })
	public ResponseEntity<String> getBeneficiaryImage(
			@Param(value = "{\"beneficiaryRegID\": \"Long\"}") @RequestBody String requestObj) {
		OutputResponse response = new OutputResponse();
		logger.info("getBeneficiaryImage request :" + requestObj);

		JSONObject obj = new JSONObject(requestObj);
		if (obj.has("beneficiaryRegID")) {
			if (obj.getLong("beneficiaryRegID") > 0) {
				String beneficiaryData = registrarServiceImpl.getBenImage(obj.getLong("beneficiaryRegID"));

				response.setResponse(beneficiaryData);
			} else {
				throw new IllegalArgumentException("Please pass beneficiaryRegID");
			}
		} else {
			throw new IllegalArgumentException("Bad Request... beneficiaryRegID is not there in request");
		}
		logger.info("getBeneficiaryDetails response :" + response);
		return ResponseEntity.ok(response.toString());
	}

	// beneficiary quick search new integrated with common and identity
	@Operation(summary = "Search beneficiary based on beneficiary id or beneficiary phone number")
	@PostMapping(value = { "/quickSearchNew" })
	public ResponseEntity<String> quickSearchNew(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		String searchList = null;
		OutputResponse response = new OutputResponse();
		searchList = registrarServiceImpl.beneficiaryQuickSearch(requestObj, Authorization);
		if (searchList == null) {
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		} else {
			return ResponseEntity.ok(searchList);
		}

	}

	// beneficiary Advance search new integrated with common and identity
	@Operation(summary = "Beneficiary advance search integrated with common and identity API")
	@PostMapping(value = { "/advanceSearchNew" })
	public ResponseEntity<String> advanceSearchNew(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		String searchList = null;
		OutputResponse response = new OutputResponse();
		searchList = registrarServiceImpl.beneficiaryAdvanceSearch(requestObj, Authorization);
		if (searchList == null) {
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		} else {
			return ResponseEntity.ok(searchList);
		}

	}

	@Operation(summary = "Beneficiary advance search")
	@PostMapping(value = { "/benAdvanceSearchNew" })
	public ResponseEntity<String> benAdvanceSearchNew(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) throws Exception {
		OutputResponse response = new OutputResponse();
		String searchList = registrarServiceImpl.beneficiaryAdvanceSearch(requestObj, Authorization);
		if (searchList == null) {
			response.setError(5000, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		} else {
			return ResponseEntity.ok(searchList);
		}
	}

	// Get Beneficiary Details for left side panel of given beneficiaryRegID new
	@Operation(summary = "Get beneficiary details for side panel")
	@PostMapping(value = { "/get/benDetailsByRegIDForLeftPanelNew" })
	public ResponseEntity<String> getBenDetailsForLeftSidePanelByRegID(
			@Param(value = "{\"beneficiaryRegID\": \"Long\"}") @RequestBody String comingRequest,
			@RequestHeader(value = "Authorization") String Authorization) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("getBenDetailsByRegID request :" + comingRequest);
		JSONObject obj = new JSONObject(comingRequest);
		if (obj.has("beneficiaryRegID") && obj.has("benFlowID")) {
			if (obj.getLong("beneficiaryRegID") > 0 && obj.getLong("benFlowID") > 0) {
				String beneficiaryData = registrarServiceMasterDataImpl.getBenDetailsForLeftSideByRegIDNew(
						obj.getLong("beneficiaryRegID"), obj.getLong("benFlowID"), Authorization, comingRequest);
				response.setResponse(beneficiaryData);
			} else {
				response.setError(500, "Invalid Beneficiary ID");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
			}
		} else {
			response.setError(500, "Invalid request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
		logger.info("getBenDetailsByRegID response :" + response);
		return ResponseEntity.ok(response.toString());
	}

	// new api for ben image
	@Operation(summary = "Get beneficiary image")
	@PostMapping(value = { "/getBenImage" })
	public ResponseEntity<String> getBenImage(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) throws Exception {
		String returnOBJ = registrarServiceMasterDataImpl.getBenImageFromIdentityAPI(Authorization, requestObj);
		return ResponseEntity.ok(returnOBJ);
	}

	// Registrar Beneficiary Registration API .....
	@Operation(summary = "Register a new beneficiary")
	@PostMapping(value = { "/registrarBeneficaryRegistration" })
	public ResponseEntity<String> createBeneficiary(
			@Param(value = "{\"benD\":{\"firstName\": \"String\", \"lastName\": \"String\", \"gender\": \"Short\","
					+ "\"dob\": \"Timestamp\", \"maritalStatus\": \"Short\", \"fatherName\": \"String\", \"motherName\": \"String\","
					+ "\"husbandName\": \"String\", \"image\": \"String\", \"aadharNo\": \"String\", \"income\": \"Short\", "
					+ "\"literacyStatus\": \"String\", \"educationQualification\": \"Short\", \"occupation\": \"Short\", \"phoneNo\": \"String\","
					+ "\"emailID\": \"Integer\", \"bankName\": \"String\", \"branchName\": \"String\", \"IFSCCode\": \"String\", \"accountNumber\": \"String\","
					+ "\"community\": \"Short\", \"religion\": \"Short\", \"blockID\": \"Integer\", \"blockName\": \"String\","
					+ "\"habitation\": \"String\", \"villageID\": \"Integer\", \"villageName\": \"String\", \"districtID\": \"Integer\","
					+ "\"districtName\": \"String\", \"stateID\": \"Integer\", \"stateName\": \"String\", \"countryID\": \"Integer\","
					+ "\"govID\": [{\"type\": \"String\",\"value\": \"String\"}], \"ageAtMarriage\": \"Integer\", \"createdBy\": \"String\", "
					+ "\"servicePointID\": \"Integer\"}}") @RequestBody String comingRequest,
			@RequestHeader(value = "Authorization") String Authorization) throws Exception {

		OutputResponse response = new OutputResponse();
		WrapperBeneficiaryRegistration wrapperBeneficiaryRegistrationOBJ = InputMapper.gson()
				.fromJson(comingRequest, WrapperBeneficiaryRegistration.class);
		logger.info("createBeneficiary request:" + comingRequest);
		JsonObject benD = wrapperBeneficiaryRegistrationOBJ.getBenD();

		if (benD == null || benD.isJsonNull()) {
			response.setError(0, "Invalid input data");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		} else {
			BeneficiaryData benData = registrarServiceImpl.createBeneficiary(benD);

			if (benData != null) {
				Long benRegID = benData.getBeneficiaryRegID();
				Long benDemoID = registrarServiceImpl.createBeneficiaryDemographic(benD, benRegID);
				Long benPhonMapID = registrarServiceImpl.createBeneficiaryPhoneMapping(benD, benRegID);

				int benGovIdMapID = registrarServiceImpl.createBenGovIdMapping(benD, benRegID);

				Long benbenDemoOtherID = registrarServiceImpl.createBeneficiaryDemographicAdditional(benD,
						benRegID);

				Long benImageID = registrarServiceImpl.createBeneficiaryImage(benD, benRegID);

				if (benRegID > 0 && benDemoID > 0 && benPhonMapID > 0 && benbenDemoOtherID > 0 && benImageID > 0) {
					Integer i = commonNurseServiceImpl.updateBeneficiaryStatus('R', benRegID);
					if (benData.getBeneficiaryID() != null) {
						response.setResponse(benData.getBeneficiaryID());
					} else {
						response.setResponse("Registration Done. Beneficiary ID is : " + benRegID);
					}
				} else {
					response.setError(500, "Something Went-Wrong");
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
				}
			} else {
				response.setError(500, "Something Went-Wrong");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
			}
		}
		logger.info("createBeneficiary response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	// beneficiary registration with common and identity new
	@Operation(summary = "Register a new beneficiary new API")
	@PostMapping(value = { "/registrarBeneficaryRegistrationNew" })
	public ResponseEntity<String> registrarBeneficaryRegistrationNew(@RequestBody String comingReq,
			@RequestHeader(value = "Authorization") String Authorization) throws Exception {
		String s = registrarServiceImpl.registerBeneficiary(comingReq, Authorization);
		return ResponseEntity.ok(s);
	}

	// Registrar Beneficiary Registration API .....
	@Operation(summary = "Update registered beneficiary data")
	@PostMapping(value = { "/update/BeneficiaryDetails" })
	public ResponseEntity<String> updateBeneficiary(
			@Param(value = "{\"benD\": {\"beneficiaryRegID\": \"Long\", \"firstName\": \"String\", \"lastName\": \"String\", \"gender\": \"Short\","
					+ "\"dob\": \"Timestamp\", \"maritalStatus\": \"Short\", \"fatherName\": \"String\", \"motherName\": \"String\","
					+ "\"husbandName\": \"String\", \"image\": \"String\", \"aadharNo\": \"String\", \"income\": \"Short\", "
					+ "\"literacyStatus\": \"String\", \"educationQualification\": \"Short\", \"occupation\": \"Short\", \"phoneNo\": \"String\","
					+ "\"emailID\": \"Integer\", \"bankName\": \"String\", \"branchName\": \"String\", \"IFSCCode\": \"String\", \"accountNumber\": \"String\","
					+ "\"community\": \"Short\", \"religion\": \"Short\", \"blockID\": \"Integer\", \"blockName\": \"String\", \"habitation\": \"String\", "
					+ "\"villageID\": \"Integer\", \"villageName\": \"String\", \"districtID\": \"Integer\", \"districtName\": \"String\", \"stateID\": \"Integer\", "
					+ "\"stateName\": \"String\", \"govID\": [{\"benGovMapID\": \"Long\", \"type\": \"String\",\"value\": \"String\"},"
					+ "{\"type\": \"String\",\"value\": \"String\"}], \"ageAtMarriage\": \"Integer\", \"createdBy\": \"String\", "
					+ "\"servicePointID\": \"Integer\", \"govtIdentityNo\": \"Integer\", \"govtIdentityTypeID\": \"Integer\", \"modifiedBy\": \"String\"}}") @RequestBody String comingRequest)
			throws Exception {

		OutputResponse response = new OutputResponse();
		WrapperBeneficiaryRegistration wrapperBeneficiaryRegistrationOBJ = InputMapper.gson()
				.fromJson(comingRequest, WrapperBeneficiaryRegistration.class);
		logger.info("updateBeneficiary request:" + comingRequest);
		JsonObject benD = wrapperBeneficiaryRegistrationOBJ.getBenD();

		if (benD == null || benD.isJsonNull() || !benD.has("beneficiaryRegID")) {
			response.setError(0, "Data Not Sufficient...");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		} else {
			int benData = registrarServiceImpl.updateBeneficiary(benD);
			if (benData != 0 && !benD.get("beneficiaryRegID").isJsonNull()) {
				Long benRegID = benD.get("beneficiaryRegID").getAsLong();
				int benDemoUpdateRes = registrarServiceImpl.updateBeneficiaryDemographic(benD, benRegID);
				int benPhonMapUpdateRes = registrarServiceImpl.updateBeneficiaryPhoneMapping(benD, benRegID);

				int benGovIdMapUpdateRes = registrarServiceImpl.updateBenGovIdMapping(benD, benRegID);

				int benbenDemoOtherUpdateRes = registrarServiceImpl.updateBeneficiaryDemographicAdditional(benD,
						benRegID);

				int benImageUpdateRes = registrarServiceImpl.updateBeneficiaryImage(benD, benRegID);

				if (benRegID >= 0 && benDemoUpdateRes >= 0 && benPhonMapUpdateRes >= 0
						&& benbenDemoOtherUpdateRes >= 0 && benImageUpdateRes >= 0) {
					Integer i = commonNurseServiceImpl.updateBeneficiaryStatus('R', benRegID);
					response.setResponse("Beneficiary Details updated successfully!!!");

				} else {
					response.setError(500, "Something Went-Wrong");
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
				}
			} else {
				response.setError(500, "Something Went-Wrong");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
			}
		}
		logger.info("updateBeneficiary response:" + response);
		return ResponseEntity.ok(response.toString());
	}

	// revisit to nurse by searching and submitting new
	@Operation(summary = "Search and submit beneficiary to nurse for revisit")
	@PostMapping(value = { "/create/BenReVisitToNurse" })
	public ResponseEntity<String> createReVisitForBenToNurse(@RequestBody String requestOBJ) throws Exception {
		OutputResponse response = new OutputResponse();
		int i = registrarServiceImpl.searchAndSubmitBeneficiaryToNurse(requestOBJ);
		if (i > 0) {
			if (i == 1)
				response.setResponse("Beneficiary moved to nurse worklist");
			else
				response.setError(5000, "Beneficiary already present in nurse worklist");
		} else {
			response.setError(5000, "Error while moving beneficiary to nurse worklist");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}

	@Operation(summary = "Update registered beneficiary details")
	@PostMapping(value = { "/update/BeneficiaryUpdate" })
	public ResponseEntity<String> beneficiaryUpdate(@RequestBody String requestOBJ,
			@RequestHeader(value = "Authorization") String Authorization) throws Exception {
		OutputResponse response = new OutputResponse();
		Integer s = registrarServiceImpl.updateBeneficiary(requestOBJ, Authorization);
		if (s != null) {
			if (s == 1)
				response.setResponse("Beneficiary details updated successfully");
			else
				response.setResponse(
						"Beneficiary details updated successfully but already present in nurse work list");
		} else {
			response.setError(5000, "Error while updating beneficiary details");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		}
		return ResponseEntity.ok(response.toString());
	}

}
