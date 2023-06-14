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
package com.iemr.mmu.controller.location;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.mmu.controller.common.master.CommonMasterController;
import com.iemr.mmu.service.location.LocationServiceImpl;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping(value = "/location", headers = "Authorization")
public class LocationController {
	private OutputResponse response;
	private Logger logger = LoggerFactory.getLogger(CommonMasterController.class);

	private LocationServiceImpl locationServiceImpl;

	@Autowired
	public void setLocationServiceImpl(LocationServiceImpl locationServiceImpl) {
		this.locationServiceImpl = locationServiceImpl;
	}

	@ApiOperation(value = "country master for beneficiary", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/get/countryMaster", method = RequestMethod.GET)
	public String getCountryMaster() {
		logger.info("get country master ...");
		response = new OutputResponse();
		String s = locationServiceImpl.getCountryList();
		if (s != null)
			response.setResponse(s);
		else
			response.setError(5000, "Error while getting country");
		// logger.info("country list" + response.toString());
		return response.toString();
	}

	@ApiOperation(value = "country city master for beneficiary", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/get/countryCityMaster/{countryID}/", method = RequestMethod.GET)
	public String getCountryCityMaster(@PathVariable("countryID") Integer countryID) {
		logger.info("get country citymaster ...");
		response = new OutputResponse();
		String s = locationServiceImpl.getCountryCityList(countryID);
		if (s != null)
			response.setResponse(s);
		else
			response.setError(5000, "Error while getting country city");
		// logger.info("country list" + response.toString());
		return response.toString();
	}

	@ApiOperation(value = "State master for beneficiary", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/get/stateMaster", method = RequestMethod.GET)
	public String getStateMaster() {
		logger.info("get state master ...");
		response = new OutputResponse();
		String s = locationServiceImpl.getStateList();
		if (s != null)
			response.setResponse(s);
		else
			response.setError(5000, "Error while getting states");
		logger.info("stateMaster" + response.toString());
		return response.toString();
	}

	@Deprecated
	@ApiOperation(value = "Zone master for beneficiary", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/get/zoneMaster/{providerServiceMapID}/", method = RequestMethod.GET)
	public String getZoneMaster(@PathVariable("providerServiceMapID") Integer providerServiceMapID) {
		logger.info("get Zone master ...");
		response = new OutputResponse();
		String s = locationServiceImpl.getZoneList(providerServiceMapID);
		if (s != null)
			response.setResponse(s);
		else
			response.setError(5000, "Error while getting zones");
		logger.info("zoneMaster" + response.toString());
		return response.toString();
	}

	@ApiOperation(value = "District master from stateID", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/get/districtMaster/{stateID}", method = RequestMethod.GET)
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
	
	@ApiOperation(value = "village master from villageID", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/get/LocationMaster/{villageID}", method = RequestMethod.GET)
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

	@ApiOperation(value = "District Block mast", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/get/districtBlockMaster/{districtID}", method = RequestMethod.GET)
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

	@ApiOperation(value = "Village master from block id", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/get/villageMasterFromBlockID/{blockID}", method = RequestMethod.GET)
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

	@Deprecated
	@ApiOperation(value = "Parking Place master for beneficiary", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/get/parkingPlaceMaster/{providerServiceMapID}", method = RequestMethod.GET)
	public String getParkingPlaceMaster(@PathVariable("providerServiceMapID") Integer providerServiceMapID) {
		logger.info("get District Block master providerServiceMapID ..." + providerServiceMapID);
		response = new OutputResponse();
		String s = locationServiceImpl.getParkingPlaceList(providerServiceMapID);
		if (s != null)
			response.setResponse(s);
		else
			response.setError(5000, "No Parking Place Master Data Available !!!");
		logger.info("parkingPlaceMaster" + response.toString());
		return response.toString();
	}

	@Deprecated
	@ApiOperation(value = "Service Point master for beneficiary", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/get/servicePointMaster/{parkingPlaceID}", method = RequestMethod.GET)
	public String getServicePointMaster(@PathVariable("parkingPlaceID") Integer parkingPlaceID) {
		logger.info("get Servicepoint master parkingPlaceID ..." + parkingPlaceID);
		response = new OutputResponse();
		String s = locationServiceImpl.getServicePointPlaceList(parkingPlaceID);
		if (s != null)
			response.setResponse(s);
		else
			response.setError(5000, "No servicePoint Master Data Available !!!");
		logger.info("servicePointMaster" + response.toString());
		return response.toString();
	}

	/***
	 * old, 11-10-2018 based on servicepoint id and provider service map id get
	 * other location details.
	 * 
	 * @param comingRequest
	 * @return
	 */
	@Deprecated
	@CrossOrigin()
	@RequestMapping(value = "/getLocDetailsBasedOnSpIDAndPsmID_old", method = { RequestMethod.POST }, produces = {
			"application/json" })
	public String getLocDetailsBasedOnSpIDAndPsmID(@RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj != null && obj.has("spID") && obj.has("spPSMID") && obj.get("spID") != null
					&& obj.get("spPSMID") != null) {
				String s = locationServiceImpl.getLocDetails(obj.getInt("spID"), obj.getInt("spPSMID"));

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

	/***
	 * New, 11-10-2018 based on servicepoint id and provider service map id get
	 * other location details.
	 * 
	 * @param comingRequest
	 * @return
	 */
	@CrossOrigin()
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
