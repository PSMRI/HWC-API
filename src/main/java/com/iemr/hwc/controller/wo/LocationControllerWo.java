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

import com.iemr.hwc.controller.common.master.CommonMasterController;
import com.iemr.hwc.service.location.LocationServiceImpl;
import com.iemr.hwc.utils.response.OutputResponse;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
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

//	@ApiOperation(value = "Get country master for beneficiary", consumes = "application/json", produces = "application/json")
//	@RequestMapping(value = "/get/countryMaster", method = RequestMethod.GET)
//	public String getCountryMaster() {
//		logger.info("get country master ...");
//		response = new OutputResponse();
//		String s = locationServiceImpl.getCountryList();
//		if (s != null)
//			response.setResponse(s);
//		else
//			response.setError(5000, "Error while getting country");
//		return response.toString();
//	}
//
//	@ApiOperation(value = "Get country city master for beneficiary", consumes = "application/json", produces = "application/json")
//	@RequestMapping(value = "/get/countryCityMaster/{countryID}/wo", method = RequestMethod.GET)
//	public String getCountryCityMaster(@PathVariable("countryID") Integer countryID) {
//		logger.info("get country citymaster ...");
//		response = new OutputResponse();
//		String s = locationServiceImpl.getCountryCityList(countryID);
//		if (s != null)
//			response.setResponse(s);
//		else
//			response.setError(5000, "Error while getting country city");
//		return response.toString();
//	}
//
//	@ApiOperation(value = "Get state master for beneficiary", consumes = "application/json", produces = "application/json")
//	@RequestMapping(value = "/get/stateMaster", method = RequestMethod.GET)
//	public String getStateMaster() {
//		logger.info("get state master ...");
//		response = new OutputResponse();
//		String s = locationServiceImpl.getStateList();
//		if (s != null)
//			response.setResponse(s);
//		else
//			response.setError(5000, "Error while getting states");
//		logger.info("stateMaster" + response.toString());
//		return response.toString();
//	}

	@ApiOperation(value = "Get district master from state id", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/get/districtMaster/{stateID}/wo", method = RequestMethod.GET)
	public String getDistrictMaster(@PathVariable("stateID") Integer stateID) {
		logger.info("get District master ...");
		response = new OutputResponse();
		String s = locationServiceImpl.getDistrictList(stateID);
		if (s != null)
			response.setResponse(s);
		else
			response.setError(5000, "Error while getting districts");
		logger.info("districtMaster" + response.toString());
		return response.toString();
	}

	@ApiOperation(value = "Get village master from village id", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/get/LocationMaster/{villageID}/wo", method = RequestMethod.GET)
	public String getLocationMaster(@PathVariable("villageID") Integer villageID) {
		logger.info("get Location master ...");
		response = new OutputResponse();
		String s = locationServiceImpl.getLocationMasterData(villageID);
		if (s != null)
			response.setResponse(s);
		else
			response.setError(5000, "Error while getting data");
		logger.info("villageMaster" + response.toString());
		return response.toString();
	}

	@ApiOperation(value = "Get block master from district id", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/get/districtBlockMaster/{districtID}/wo", method = RequestMethod.GET)
	public String getDistrictBlockMaster(@PathVariable("districtID") Integer districtID) {
		logger.info("get District Block master districtID ..." + districtID);
		response = new OutputResponse();
		String s = locationServiceImpl.getDistrictBlockList(districtID);
		if (s != null)
			response.setResponse(s);
		else
			response.setError(5000, "Error while getting district blocks");
		logger.info("districtBlockMaster" + response.toString());
		return response.toString();
	}

	@ApiOperation(value = "Get village master from block id", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/get/villageMasterFromBlockID/{blockID}/wo", method = RequestMethod.GET)
	public String getVillageMaster(@PathVariable("blockID") Integer blockID) {
		logger.info("get District Block master districtID ..." + blockID);
		response = new OutputResponse();
		String s = locationServiceImpl.getVillageMasterFromBlockID(blockID);
		if (s != null)
			response.setResponse(s);
		else
			response.setError(5000, "Error while getting villages");
		logger.info("village master" + response.toString());
		return response.toString();
	}

	@ApiOperation(value = "Get village from district id", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/get/villageFromDistrictID/{districtID}/wo", method = RequestMethod.GET)
	public String getVillageByDistrictID(@PathVariable("districtID") Integer districtID) {
		logger.info("get village by districtID ..." + districtID);
		response = new OutputResponse();
		String s = locationServiceImpl.getVillageListByDistrictID(districtID);
		if (s != null)
			response.setResponse(s);
		else
			response.setError(5000, "Error while getting villages");
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
	@CrossOrigin()
	@ApiOperation(value = "Get location details from service point id and provider service map id", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/getLocDetailsBasedOnSpIDAndPsmID", method = { RequestMethod.POST }, produces = {
			"application/json" })
	public String getLocDetailsBasedOnSpIDAndPsmIDNew(@RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj != null && obj.has("vanID") && obj.has("spPSMID") && obj.get("vanID") != null
					&& obj.get("spPSMID") != null) {
				String s = locationServiceImpl.getLocDetailsNew(obj.getInt("vanID"), obj.getInt("spPSMID"));

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
}
