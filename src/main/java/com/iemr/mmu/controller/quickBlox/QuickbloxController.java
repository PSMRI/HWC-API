package com.iemr.mmu.controller.quickBlox;

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
import com.iemr.mmu.service.patientApp.master.CommonPatientAppMasterService;
import com.iemr.mmu.service.quickBlox.QuickbloxService;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping(value = "/quickblox", headers = "Authorization")

public class QuickbloxController {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	@Autowired
	private QuickbloxService quickbloxService;
	@CrossOrigin
	@ApiOperation(value = "Get Quickblox Ids", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getquickbloxIds" }, method = { RequestMethod.POST })
	public String getquickbloxIds(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for quickblox get ids :" + requestObj);
			response.setResponse(
					quickbloxService.getQuickbloxIds(requestObj));
			return response.toString();
//			JsonObject jsnOBJ = new JsonObject();
//			JsonParser jsnParser = new JsonParser();
//			JsonElement jsnElmnt = jsnParser.parse(requestObj);
//			jsnOBJ = jsnElmnt.getAsJsonObject();
//
//			if (jsnOBJ != null) {
//				Long ncdCareRes = quickbloxService.getQuickbloxIds(requestObj);
//				if (null != ncdCareRes && ncdCareRes > 0) {
//					response.setResponse("Data saved successfully");
//				} else {
//					response.setResponse("Unable to save data");
//				}
//
//			} else {
//				response.setError(5000, "Invalid Request !!!");
//			}

		} catch (Exception e) {
			logger.error("Error while getting quickblox Ids :" + e);
			response.setError(5000, "Error while getting quickblox Ids");
		}
		return response.toString();
	}
	


}
