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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public String getRegistrarWorkList(@Param(value = "{\"spID\": \"Integer\"}") @RequestBody String comingRequest)
			throws JSONException {
		OutputResponse response = new OutputResponse();
		logger.info("getRegistrarWorkList request:" + comingRequest);
		try {

			JSONObject obj = new JSONObject(comingRequest);
			response.setResponse(this.registrarServiceImpl.getRegWorkList(obj.getInt("spID")));
			logger.info("getRegistrarWorkList response:" + response);
		} catch (Exception e) {
			response.setError(e);
			logger.error("Error in getRegistrarWorkList:" + e);
		}
		return response.toString();
	}

	// Registrar Quick search .....

	@Operation(summary = "Search for beneficiary based on beneficiary id")
	@PostMapping(value = { "/quickSearch" })
	public String quickSearchBeneficiary(@Param(value = "{\"benID\": \"String\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		logger.info("quickSearchBeneficiary request:" + comingRequest);
		try {

			JSONObject obj = new JSONObject(comingRequest);
			response.setResponse(registrarServiceImpl.getQuickSearchBenData(obj.getString("benID")));
			logger.info("quickSearchBeneficiary response:" + response);
		} catch (Exception e) {
			logger.error("Error in quickSearchBeneficiary :" + e);
			response.setError(e);
		}
		return response.toString();
	}

	// Registrar Advance search .....

	@Operation(summary = "Search for beneficiary based on provided data")
	@PostMapping(value = { "/advanceSearch" })
	public String advanceSearch(
			@Param(value = "{\"firstName\": \"String\", \"lastName\": \"String\", \"phoneNo\": \"String\","
					+ "\"beneficiaryID\": \"String\", \"stateID\": \"Integer\", \"districtID\": \"Integer\", \"aadharNo\": \"String\"},"
					+ " \"govtIdentityNo\": \"String\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		logger.info("advanceSearch request :" + comingRequest);
		try {
			V_BenAdvanceSearch v_BenAdvanceSearch = inputMapper.gson().fromJson(comingRequest,
					V_BenAdvanceSearch.class);
			response.setResponse(registrarServiceImpl.getAdvanceSearchBenData(v_BenAdvanceSearch));
			logger.info("advanceSearch response:" + response);
		} catch (Exception e) {
			logger.error("Error in advanceSearch :" + e);
			response.setError(e);
		}

		return response.toString();
	}

	// API for left side ben data

	@Operation(summary = "Get beneficiary details based on beneficiary register id")
	@PostMapping(value = { "/get/benDetailsByRegID" })
	public String getBenDetailsByRegID(
			@Param(value = "{\"beneficiaryRegID\": \"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		logger.info("getBenDetailsByRegID request :" + comingRequest);
		try {

			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("beneficiaryRegID")) {
				if (obj.getLong("beneficiaryRegID") > 0) {

					String beneficiaryData = registrarServiceMasterDataImpl
							.getBenDetailsByRegID(obj.getLong("beneficiaryRegID"));

					response.setResponse(beneficiaryData);
				} else {
					response.setError(500, "Please pass beneficiaryRegID");
				}
			} else {
				response.setError(500, "Bad Request... beneficiaryRegID is not there in request");
			}
			logger.info("getBenDetailsByRegID response :" + response);
		} catch (Exception e) {
			logger.error("Error in getBenDetailsByRegID :" + e);
			response.setError(e);
		}
		return response.toString();
	}

	@Operation(summary = "Get beneficiary details")
	@PostMapping(value = { "/get/beneficiaryDetails" })
	public String getBeneficiaryDetails(
			@Param(value = "{\"beneficiaryRegID\": \"Long\"}") @RequestBody String requestObj) {
		OutputResponse response = new OutputResponse();
		logger.info("getBeneficiaryDetails request :" + requestObj);
		try {

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
					response.setError(500, "Please pass beneficiaryRegID");
				}
			} else {
				response.setError(500, "Bad Request... beneficiaryRegID is not there in request");
			}
			logger.info("getBeneficiaryDetails response :" + response);
		} catch (Exception e) {
			logger.error("Error in getBeneficiaryDetails :" + e);
			response.setError(e);
		}
		return response.toString();
	}

	@Operation(summary = "Get beneficiary image")
	@PostMapping(value = { "/get/beneficiaryImage" })
	public String getBeneficiaryImage(
			@Param(value = "{\"beneficiaryRegID\": \"Long\"}") @RequestBody String requestObj) {
		OutputResponse response = new OutputResponse();
		logger.info("getBeneficiaryImage request :" + requestObj);
		try {
			JSONObject obj = new JSONObject(requestObj);
			if (obj.has("beneficiaryRegID")) {
				if (obj.getLong("beneficiaryRegID") > 0) {
					String beneficiaryData = registrarServiceImpl.getBenImage(obj.getLong("beneficiaryRegID"));

					response.setResponse(beneficiaryData);
				} else {
					response.setError(500, "Please pass beneficiaryRegID");
				}
			} else {
				response.setError(500, "Bad Request... beneficiaryRegID is not there in request");
			}
			logger.info("getBeneficiaryDetails response :" + response);
		} catch (Exception e) {

		}
		return response.toString();
	}

	// beneficiary quick search new integrated with common and identity

	@Operation(summary = "Search beneficiary based on beneficiary id or beneficiary phone number")
	@PostMapping(value = { "/quickSearchNew" })
	public String quickSearchNew(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		String searchList = null;
		OutputResponse response = new OutputResponse();
		try {
			searchList = registrarServiceImpl.beneficiaryQuickSearch(requestObj, Authorization);
			if (searchList == null) {
				response.setError(5000, "Invalid request");
				return response.toString();
			} else {
				return searchList;
			}
		} catch (Exception e) {
			logger.error("Error in Quick Search" + e);
			response.setError(5000, "Error while searching beneficiary");
			return response.toString();
		}

	}

	// beneficiary Advance search new integrated with common and identity

	@Operation(summary = "Beneficiary advance search integrated with common and identity API")
	@PostMapping(value = { "/advanceSearchNew" })
	public String advanceSearchNew(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		String searchList = null;
		OutputResponse response = new OutputResponse();
		try {
			searchList = registrarServiceImpl.beneficiaryAdvanceSearch(requestObj, Authorization);
			if (searchList == null) {
				response.setError(5000, "Invalid request");
				return response.toString();
			} else {
				return searchList;
			}
		} catch (Exception e) {
			logger.error("Error in Quick Search" + e);
			response.setError(5000, "Error while searching beneficiary");
			return response.toString();
		}

	}

	@Operation(summary = "Beneficiary advance search")
	@PostMapping(value = { "/benAdvanceSearchNew" })
	public String benAdvanceSearchNew(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		String searchList = null;
		OutputResponse response = new OutputResponse();
		try {
			searchList = registrarServiceImpl.beneficiaryAdvanceSearch(requestObj, Authorization);
			if (searchList == null) {
				response.setError(5000, "Invalid request");
				return response.toString();
			} else {
				return searchList;
			}
		} catch (Exception e) {
			logger.error("Error in Quick Search" + e);
			response.setError(5000, "Error while searching beneficiary");
			return response.toString();
		}

	}

	// Get Beneficiary Details for left side panel of given beneficiaryRegID new

	@Operation(summary = "Get beneficiary details for side panel")
	@PostMapping(value = { "/get/benDetailsByRegIDForLeftPanelNew" })
	public String getBenDetailsForLeftSidePanelByRegID(
			@Param(value = "{\"beneficiaryRegID\": \"Long\"}") @RequestBody String comingRequest,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		logger.info("getBenDetailsByRegID request :" + comingRequest);
		try {

			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("beneficiaryRegID") && obj.has("benFlowID")) {
				if (obj.getLong("beneficiaryRegID") > 0 && obj.getLong("benFlowID") > 0) {

					String beneficiaryData = registrarServiceMasterDataImpl.getBenDetailsForLeftSideByRegIDNew(
							obj.getLong("beneficiaryRegID"), obj.getLong("benFlowID"), Authorization, comingRequest);

					response.setResponse(beneficiaryData);
				} else {
					response.setError(500, "Invalid Beneficiary ID");
				}
			} else {
				response.setError(500, "Invalid request");
			}
			logger.info("getBenDetailsByRegID response :" + response);
		} catch (Exception e) {
			logger.error("Error in getBenDetailsByRegID :" + e);
			response.setError(5000, "Error while getting beneficiary details");
		}
		return response.toString();
	}

	// new api for ben image

	@Operation(summary = "Get beneficiary image")
	@PostMapping(value = { "/getBenImage" })
	public String getBenImage(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			String returnOBJ = registrarServiceMasterDataImpl.getBenImageFromIdentityAPI(Authorization, requestObj);
			return returnOBJ;
		} catch (Exception e) {
			logger.error("Error ben image fetch" + e);
			response.setError(5000, "Error while getting beneficiary image");
			return response.toString();
		}

	}

	// Registrar Beneficiary Registration API .....

	@Operation(summary = "Register a new beneficiary")
	@PostMapping(value = { "/registrarBeneficaryRegistration" })
	public String createBeneficiary(
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
			@RequestHeader(value = "Authorization") String Authorization) {

		OutputResponse response = new OutputResponse();
		try {
			WrapperBeneficiaryRegistration wrapperBeneficiaryRegistrationOBJ = InputMapper.gson()
					.fromJson(comingRequest, WrapperBeneficiaryRegistration.class);
			logger.info("createBeneficiary request:" + comingRequest);
			JsonObject benD = wrapperBeneficiaryRegistrationOBJ.getBenD();

			if (benD == null || benD.isJsonNull()) {
				response.setError(0, "Invalid input data");
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
					}
				} else {
					response.setError(500, "Something Went-Wrong");
				}
			}
			logger.info("createBeneficiary response:" + response);
		} catch (Exception e) {
			logger.error("Error in createBeneficiary :" + e);
			response.setError(e);
		}
		return response.toString();
	}

	// beneficiary registration with common and identity new

	@Operation(summary = "Register a new beneficiary new API")
	@PostMapping(value = { "/registrarBeneficaryRegistrationNew" })
	public String registrarBeneficaryRegistrationNew(@RequestBody String comingReq,
			@RequestHeader(value = "Authorization") String Authorization) {
		String s;
		OutputResponse response = new OutputResponse();
		try {
			s = registrarServiceImpl.registerBeneficiary(comingReq, Authorization);
			return s;
		} catch (Exception e) {
			logger.error("Error in registration" + e);
			response.setError(5000, "Error in registration; please contact administrator");
			return response.toString();
		}

	}

	// Registrar Beneficiary Registration API .....

	@Operation(summary = "Update registered beneficiary data")
	@PostMapping(value = { "/update/BeneficiaryDetails" })
	public String updateBeneficiary(
			@Param(value = "{\"benD\": {\"beneficiaryRegID\": \"Long\", \"firstName\": \"String\", \"lastName\": \"String\", \"gender\": \"Short\","
					+ "\"dob\": \"Timestamp\", \"maritalStatus\": \"Short\", \"fatherName\": \"String\", \"motherName\": \"String\","
					+ "\"husbandName\": \"String\", \"image\": \"String\", \"aadharNo\": \"String\", \"income\": \"Short\", "
					+ "\"literacyStatus\": \"String\", \"educationQualification\": \"Short\", \"occupation\": \"Short\", \"phoneNo\": \"String\","
					+ "\"emailID\": \"Integer\", \"bankName\": \"String\", \"branchName\": \"String\", \"IFSCCode\": \"String\", \"accountNumber\": \"String\","
					+ "\"community\": \"Short\", \"religion\": \"Short\", \"blockID\": \"Integer\", \"blockName\": \"String\", \"habitation\": \"String\", "
					+ "\"villageID\": \"Integer\", \"villageName\": \"String\", \"districtID\": \"Integer\", \"districtName\": \"String\", \"stateID\": \"Integer\", "
					+ "\"stateName\": \"String\", \"govID\": [{\"benGovMapID\": \"Long\", \"type\": \"String\",\"value\": \"String\"},"
					+ "{\"type\": \"String\",\"value\": \"String\"}], \"ageAtMarriage\": \"Integer\", \"createdBy\": \"String\", "
					+ "\"servicePointID\": \"Integer\", \"govtIdentityNo\": \"Integer\", \"govtIdentityTypeID\": \"Integer\", \"modifiedBy\": \"String\"}}") @RequestBody String comingRequest) {

		OutputResponse response = new OutputResponse();
		try {
			WrapperBeneficiaryRegistration wrapperBeneficiaryRegistrationOBJ = InputMapper.gson()
					.fromJson(comingRequest, WrapperBeneficiaryRegistration.class);
			logger.info("updateBeneficiary request:" + comingRequest);
			JsonObject benD = wrapperBeneficiaryRegistrationOBJ.getBenD();

			if (benD == null || benD.isJsonNull() || !benD.has("beneficiaryRegID")) {
				response.setError(0, "Data Not Sufficient...");
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
					}
				} else {
					response.setError(500, "Something Went-Wrong");
				}
			}
			logger.info("updateBeneficiary response:" + response);
		} catch (Exception e) {
			logger.error("Error in updateBeneficiary :" + e);
			response.setError(e);
		}
		return response.toString();
	}

	// revisit to nurse by searching and submitting new
	@Operation(summary = "Search and submit beneficiary to nurse for revisit")
	@PostMapping(value = { "/create/BenReVisitToNurse" })
	public String createReVisitForBenToNurse(@RequestBody String requestOBJ) {
		OutputResponse response = new OutputResponse();
		try {
			int i = registrarServiceImpl.searchAndSubmitBeneficiaryToNurse(requestOBJ);
			if (i > 0) {
				if (i == 1)
					response.setResponse("Beneficiary moved to nurse worklist");
				else
					response.setError(5000, "Beneficiary already present in nurse worklist");
			} else {
				response.setError(5000, "Error while moving beneficiary to nurse worklist");
			}
		} catch (Exception e) {
			logger.error("Error while creating re-visit " + e);
			response.setError(5000, "Error while moving beneficiary to nurse worklist");
		}
		return response.toString();
	}

	@Operation(summary = "Update registered beneficiary details")
	@PostMapping(value = { "/update/BeneficiaryUpdate" })
	public String beneficiaryUpdate(@RequestBody String requestOBJ,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		Integer s = null;
		try {
			s = registrarServiceImpl.updateBeneficiary(requestOBJ, Authorization);
			if (s != null) {
				if (s == 1)
					response.setResponse("Beneficiary details updated successfully");
				else
					response.setResponse(
							"Beneficiary details updated successfully but already present in nurse work list");
			} else {
				response.setError(5000, "Error while updating beneficiary details");
			}
		} catch (Exception e) {
			response.setError(5000, "Error in beneficiary details update");
		}
		return response.toString();
	}

}
