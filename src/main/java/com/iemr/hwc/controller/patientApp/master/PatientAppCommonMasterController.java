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
package com.iemr.hwc.controller.patientApp.master;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.hwc.controller.common.master.CommonMasterController;
import com.iemr.hwc.service.patientApp.master.CommonPatientAppMasterService;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping(value = "/master", headers = "Authorization")

public class PatientAppCommonMasterController {

	private Logger logger = LoggerFactory.getLogger(CommonMasterController.class);
	@Autowired
	private CommonPatientAppMasterService commonPatientAppMasterService;

	/**
	 * 
	 * @param visitCategoryID
	 * @return nurse master data for the provided visitCategoryID
	 * 
	 */
	@ApiOperation(value = "Master data API for patient app", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/patientApp/chiefComplaintsMaster/{visitCategoryID}/{providerServiceMapID}/{gender}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public String patientAppChiefComplaintsMasterData(@PathVariable("visitCategoryID") Integer visitCategoryID,
			@PathVariable("providerServiceMapID") Integer providerServiceMapID, @PathVariable("gender") String gender) {
		logger.info("Nurse master Data for categoryID:" + visitCategoryID + " and providerServiceMapID:"
				+ providerServiceMapID);
		;
		OutputResponse response = new OutputResponse();
		response.setResponse(
				commonPatientAppMasterService.getChiefComplaintsMaster(visitCategoryID, providerServiceMapID, gender));
		logger.info("Nurse master Data for categoryID:" + response.toString());
		return response.toString();
	}

	@ApiOperation(value = "Master data API for patient app", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/patientApp/covidMaster/{visitCategoryID}/{providerServiceMapID}/{gender}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public String patientAppCovidMasterData(@PathVariable("visitCategoryID") Integer visitCategoryID,
			@PathVariable("providerServiceMapID") Integer providerServiceMapID, @PathVariable("gender") String gender) {
		logger.info("Nurse master Data for categoryID:" + visitCategoryID + " and providerServiceMapID:"
				+ providerServiceMapID);
		;
		OutputResponse response = new OutputResponse();
		response.setResponse(
				commonPatientAppMasterService.getCovidMaster(visitCategoryID, providerServiceMapID, gender));
		logger.info("Nurse master Data for categoryID:" + response.toString());
		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "Save covid  data patient app", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/covidScreeningDataPatientApp" }, method = { RequestMethod.POST })
	public String saveBenCovidDoctorDataPatientApp(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for Covid data saving - patient APP :" + requestObj);

			String s = commonPatientAppMasterService.saveCovidScreeningData(requestObj);

			response.setResponse(s);

		} catch (Exception e) {
			logger.error("Error while saving Covid data - patient APP :" + e);
			response.setError(5000, "Unable to save data. " + e.getMessage());
		}
		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "Save chief-complaints  data patient app", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/chiefComplaintsDataPatientApp" }, method = { RequestMethod.POST })
	public String saveBenChiefComplaintsDataPatientApp(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for chief-Complaints data saving - patient APP :" + requestObj);

			String s = commonPatientAppMasterService.savechiefComplaintsData(requestObj);

			response.setResponse(s);

		} catch (Exception e) {
			logger.error("Error while saving chief-Complaints data - patient APP :" + e);
			response.setError(5000, "Unable to save data. " + e.getMessage());
		}
		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "Save tele-consultation slot data patient app", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/tcSlotDetailsDataPatientApp" }, method = { RequestMethod.POST })
	public String saveTCSlotDataPatientApp(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for TC slot data saving - patient APP :" + requestObj);

			Integer i = commonPatientAppMasterService.bookTCSlotData(requestObj, Authorization);
			if (i > 0)
				response.setResponse("Teleconsultation slot booked successfully");
			else
				response.setError(5000, "error in slot booking");

		} catch (Exception e) {
			logger.error("Error while booking TC slot - patient APP :" + e);
			response.setError(5000, "error in slot booking : " + e.getMessage());
		}
		return response.toString();
	}

	@ApiOperation(value = "Master data  for patient", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = {
			"/patientApp/details/{stateID}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public String patientAppMasterData(@PathVariable("stateID") Integer stateID) {
		logger.info("master Data for beneficiary:");

		OutputResponse response = new OutputResponse();
		response.setResponse(commonPatientAppMasterService.getMaster(stateID));
		logger.info("Nurse master Data for beneficiary:" + response.toString());
		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "Get patient episode data for specialist - patient app", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/get/getPatientEpisodeData" }, method = { RequestMethod.POST })
	public String getPatientEpisodeDataMobileApp(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for patient episode data for diagnosis - patient APP :" + requestObj);

			String s = commonPatientAppMasterService.getPatientEpisodeData(requestObj);
			if (s != null)
				response.setResponse(s);
			else
				response.setError(5000, "error in getting beneficiary episode data");

		} catch (Exception e) {
			logger.error("error in getting beneficiary episode data - patient APP :" + e);
			response.setError(5000, "error in getting beneficiary episode data : " + e.getMessage());
		}
		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "Get patient booked slot data - patient app", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/get/getPatientBookedSlotDetails" }, method = { RequestMethod.POST })
	public String getPatientBookedSlotDetails(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for patient booked slot data - patient APP :" + requestObj);

			String s = commonPatientAppMasterService.getPatientBookedSlots(requestObj);
			if (s != null)
				response.setResponse(s);
			else
				response.setError(5000, "error in getting beneficiary booked slot data");

		} catch (Exception e) {
			logger.error("error in getting beneficiary booked slot data - patient APP :" + e);
			response.setError(5000, "error in getting beneficiary booked slot data : " + e.getMessage());
		}
		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "save specialist diagnosis data - patient app", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/saveSpecialistDiagnosisData" }, method = { RequestMethod.POST })
	public String saveSpecialistDiagnosisData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for specialist diagnosis data - patient APP :" + requestObj);

			Long pID = commonPatientAppMasterService.saveSpecialistDiagnosisData(requestObj);
			if (pID != null)
				response.setResponse("success");
			else
				response.setError(5000, "error in saving diagnosis data");

		} catch (Exception e) {
			logger.error("error in saving specialist diagnosis data - patient APP :" + e);
			response.setError(5000, "error in saving specialist diagnosis data : " + e.getMessage());
		}
		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "Get specialist diagnosis data - patient app", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/getSpecialistDiagnosisData" }, method = { RequestMethod.POST })
	public String getSpecialistDiagnosisData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for getting specialist diagnosis data - patient APP :" + requestObj);

			String s = commonPatientAppMasterService.getSpecialistDiagnosisData(requestObj);
			if (s != null)
				response.setResponse(s);
			else
				response.setError(5000, "error in getting diagnosis data");

		} catch (Exception e) {
			logger.error("error in getting specialist diagnosis data - patient APP :" + e);
			response.setError(5000, "error in getting specialist diagnosis data : " + e.getMessage());
		}
		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "Get last 3 episode data of the patient - patient app", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/get/getPatientsEpisodes" }, method = { RequestMethod.POST })
	public String getPatientsLast_3_Episode(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for getPatientsLast_3_Episode data - patient APP :" + requestObj);

			String s = commonPatientAppMasterService.getPatientsLast_3_Episode(requestObj);
			if (s != null)
				response.setResponse(s);
			else
				response.setError(5000, "error in getPatientsLast_3_Episode data");

		} catch (Exception e) {
			logger.error("error in getPatientsLast_3_Episode data - patient APP :" + e);
			response.setError(5000, "error in getPatientsLast_3_Episode data : " + e.getMessage());
		}
		return response.toString();
	}

}
