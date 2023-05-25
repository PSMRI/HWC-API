package com.iemr.mmu.controller.generalOPD;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.mmu.service.generalOPD.GeneralOPDServiceImpl;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;

/***
 * 
 * @author NE298657
 * @Objective Saving General OPD data for Nurse and Doctor.
 *
 */

@RestController
@CrossOrigin
@RequestMapping(value = "/generalOPD", headers = "Authorization")
public class GeneralOPDCreateController {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private GeneralOPDServiceImpl generalOPDServiceImpl;

	@Autowired
	public void setGeneralOPDServiceImpl(GeneralOPDServiceImpl generalOPDServiceImpl) {
		this.generalOPDServiceImpl = generalOPDServiceImpl;
	}

	/**
	 * @Objective Save General OPD data for nurse.
	 * @param requestObj
	 * @return success or failure response
	 */
	@CrossOrigin
	@ApiOperation(value = "Save General OPD nurse data..", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/nurseData" }, method = { RequestMethod.POST })
	public String saveBenGenOPDNurseData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for GeneralOPD nurse data saving :" + requestObj);

			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			if (jsnOBJ != null) {
				String genOPDRes = generalOPDServiceImpl.saveNurseData(jsnOBJ, Authorization);
				response.setResponse(genOPDRes);
//				if (null != genOPDRes && genOPDRes > 0) {
//					response.setResponse("Data saved successfully and visit code is "+genOPDRes);
//				} else {
//					response.setResponse("Unable to save data");
//				}

			} else {
				response.setResponse("Invalid request");
			}
		} catch (Exception e) {
			logger.error("Error in nurse data saving :" + e);
			response.setError(5000, "Unable to save data");
		}
		return response.toString();
	}

	/**
	 * @Objective Save General OPD data for doctor.
	 * @param requestObj
	 * @return success or failure response
	 */
	@CrossOrigin
	@ApiOperation(value = "Save General OPD doctor data..", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/doctorData" }, method = { RequestMethod.POST })
	public String saveBenGenOPDDoctorData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for GeneralOPD doctor data saving :" + requestObj);

			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			if (jsnOBJ != null) {
				Long genOPDRes = generalOPDServiceImpl.saveDoctorData(jsnOBJ, Authorization);
				if (null != genOPDRes && genOPDRes > 0) {
					response.setResponse("Data saved successfully");
				} else {
					response.setResponse("Unable to save data");
				}

			} else {
				response.setResponse("Invalid request");
			}
		} catch (Exception e) {
			logger.error("Error in doctor data saving :" + e);
			response.setError(5000, "Unable to save data. " + e.getMessage());
		}
		return response.toString();
	}
}
