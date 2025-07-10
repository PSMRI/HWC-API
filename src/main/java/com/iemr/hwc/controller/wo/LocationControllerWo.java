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
package com.iemr.hwc.controller.wo;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.controller.common.master.CommonMasterController;
import com.iemr.hwc.service.location.LocationServiceImpl;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.v3.oas.annotations.Operation;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "wo/location")
public class LocationControllerWo {
	private OutputResponse response;
	private Logger logger = LoggerFactory.getLogger(CommonMasterController.class);

	private LocationServiceImpl locationServiceImpl;

	@Autowired
	public void setLocationServiceImpl(LocationServiceImpl locationServiceImpl) {
		this.locationServiceImpl = locationServiceImpl;
	}

	@Operation(summary = "Get district master from state id")
	@GetMapping(value = "/get/districtMaster/{stateID}/wo")
	public String getDistrictMaster(@PathVariable("stateID") Integer stateID) {
		logger.info("get District master ...");
		response = new OutputResponse();
		String s = locationServiceImpl.getDistrictList(stateID);
		if (s != null) {
			response.setResponse(s);
		} else {
			response.setError(5000, "Error while getting districts");
		}
		logger.info("districtMaster" + response.toString());
		return response.toString();
	}

	@Operation(summary = "Get village master from village id")
	@GetMapping(value = "/get/LocationMaster/{villageID}/wo")
	public String getLocationMaster(@PathVariable("villageID") Integer villageID) {
		logger.info("get Location master ...");
		response = new OutputResponse();
		String s = locationServiceImpl.getLocationMasterData(villageID);
		if (s != null) {
			response.setResponse(s);
		} else {
			response.setError(5000, "Error while getting data");
		}
		logger.info("villageMaster" + response.toString());
		return response.toString();
	}

	@Operation(summary = "Get block master from district id")
	@GetMapping(value = "/get/districtBlockMaster/{districtID}/wo")
	public String getDistrictBlockMaster(@PathVariable("districtID") Integer districtID) {
		logger.info("get District Block master districtID ..." + districtID);
		response = new OutputResponse();
		String s = locationServiceImpl.getDistrictBlockList(districtID);
		if (s != null) {
			response.setResponse(s);
		} else {
			response.setError(5000, "Error while getting district blocks");
		}
		logger.info("districtBlockMaster" + response.toString());
		return response.toString();
	}

	@Operation(summary = "Get village master from block id")
	@GetMapping(value = "/get/villageMasterFromBlockID/{blockID}/wo")
	public String getVillageMaster(@PathVariable("blockID") Integer blockID) {
		logger.info("get District Block master districtID ..." + blockID);
		response = new OutputResponse();
		String s = locationServiceImpl.getVillageMasterFromBlockID(blockID);
		if (s != null) {
			response.setResponse(s);
		} else {
			response.setError(5000, "Error while getting villages");
		}
		logger.info("village master" + response.toString());
		return response.toString();
	}

	@Operation(summary = "Get village from district id")
	@GetMapping(value = "/get/villageFromDistrictID/{districtID}/wo")
	public String getVillageByDistrictID(@PathVariable("districtID") Integer districtID) {
		logger.info("get village by districtID ..." + districtID);
		response = new OutputResponse();
		String s = locationServiceImpl.getVillageListByDistrictID(districtID);
		if (s != null) {
			response.setResponse(s);
		} else {
			response.setError(5000, "Error while getting villages");
		}
		logger.info("village master" + response.toString());
		return response.toString();
	}

	/***
	 * based on servicepoint id and provider service map id get other location
	 * details.
	 *
	 * @param comingRequest
	 * @return
	 */
	@Operation(summary = "Get location details from service point id and provider service map id")
	@PostMapping(value = "/getLocDetailsBasedOnSpIDAndPsmID/wo", produces = { "application/json" })
	public String getLocDetailsBasedOnSpIDAndPsmIDNew(@RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj != null && obj.has("vanID") && obj.has("spPSMID") && obj.get("vanID") != null
					&& obj.get("spPSMID") != null) {
				String s = locationServiceImpl.getLocDetailsNew(obj.getInt("vanID"), obj.getInt("spPSMID"), obj);

				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(5000, "Error while getting location data");
		}
		return response.toString();
	}

	@Operation(summary = "Update health and wellness center coordinates")
	@PostMapping(value = { "/update/villageCoordinates" })
	public String updateGeolocationVillage(@RequestBody String requestObj) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for Geolocation update :" + requestObj);

			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			JsonElement jElmtLatitude = jsnOBJ.get("latitude");
			JsonElement jElmtLongitude = jsnOBJ.get("longitude");
			JsonElement jElmtDistrictBranchID = jsnOBJ.get("districtBranchID");
			JsonElement jElmtAddress = jsnOBJ.get("address");

			if (jsnOBJ != null && jElmtLatitude != null && jElmtLongitude != null && jElmtDistrictBranchID != null
					&& jElmtAddress != null) {
				int responseUpdate = locationServiceImpl.updateGeolocationByDistrictBranchID(
						jElmtLatitude.getAsDouble(), jElmtLongitude.getAsDouble(), jElmtDistrictBranchID.getAsInt(),
						jElmtAddress.getAsString());
				if (responseUpdate == 1) {
					response.setResponse("Successfully updated");
				} else if (responseUpdate == 101) {
					response.setResponse("Geolocation for this village already set");
				}
			} else {
				response.setError(400, "Invalid request");
			}
		} catch (Exception e) {
			logger.error("Error in updating geolocation :" + e);
			response.setError(5000, "Unable to update data");
		}
		return response.toString();
	}

	@Operation(summary = "Get outreach programs based on state id")
	@GetMapping(value = "/outreachMaster/{stateID}/wo")
	public ResponseEntity<String> getOutreachMasterForState(@PathVariable("stateID") Integer stateID) {
		logger.info("get Outreach programs for state with Id ..." + stateID);

		response = new OutputResponse();
		HttpStatus statusCode = HttpStatus.OK;
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", "application/json");

		try {
			String resList = locationServiceImpl.getOutreachProgramsList(stateID);
			response.setResponse(resList);
		} catch (Exception e) {
			logger.error("Error while fetching outreach list for stateId" + stateID);
			response.setError(500, "Unable to fetch outreach list for stateId" + stateID + "Exception - " + e);
			statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(response.toStringWithSerializeNulls(), headers, statusCode);
	}
}