/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.controller.labtechnician;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.mmu.service.labtechnician.LabTechnicianServiceImpl;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;

/***
 * 
 * @author NE298657
 * @Objective Saving lab test results given by LabTechnician
 *
 */

@RestController
@CrossOrigin
@RequestMapping(value = "/labTechnician", headers = "Authorization")
public class LabtechnicianCreateController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	private LabTechnicianServiceImpl labTechnicianServiceImpl;
	
	@Autowired
	public void setLabTechnicianServiceImpl(LabTechnicianServiceImpl labTechnicianServiceImpl) {
		this.labTechnicianServiceImpl = labTechnicianServiceImpl;
	}
	
	/**
	 * @Objective Save lab test results given by LabTechnician
	 * @param JSON requestObj 
	 * @return success or failure response
	 */
	@CrossOrigin
	@ApiOperation(value = "Save Lab Test Result Entered by LabTechnician..", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/LabTestResult" }, method = { RequestMethod.POST })
	public String saveLabTestResult(@RequestBody String requestObj) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for Lab Test Result saving :" + requestObj);

			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			if (jsnOBJ != null) {
				Integer labResultSaveRes = labTechnicianServiceImpl.saveLabTestResult(jsnOBJ);
				if (null != labResultSaveRes && labResultSaveRes > 0) {
					response.setResponse("Data saved successfully");
				} else {
					response.setResponse("Unable to save data");
				}

			} else {
				response.setResponse("Invalid request");
			}
		} catch (Exception e) {
			logger.error("Error while saving lab test results  :" + e);
			response.setError(5000, "Unable to save data");
		}
		return response.toString();
	}
}
