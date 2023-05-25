package com.iemr.mmu.controller.registrar.master;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.mmu.service.common.master.RegistrarServiceMasterDataImpl;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping(value = "/registrar", headers = "Authorization")
/** Objective: Get Registration related master Data */
public class RegistrarMasterController {

	private Logger logger = LoggerFactory.getLogger(RegistrarMasterController.class);
	private RegistrarServiceMasterDataImpl registrarServiceMasterDataImpl;

	@Autowired
	public void setRegistrarServiceMasterDataImpl(RegistrarServiceMasterDataImpl registrarServiceMasterDataImpl) {
		this.registrarServiceMasterDataImpl = registrarServiceMasterDataImpl;
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Master Data for Registrar", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/registrarMasterData" }, method = { RequestMethod.POST })
	public String masterDataForRegistration(
			@ApiParam(value = "{\"spID\": \"Integer\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		logger.info("masterDataForRegistration request :" + comingRequest);
		try {

			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("spID")) {
				if (obj.getInt("spID") > 0) {
					response.setResponse(registrarServiceMasterDataImpl.getRegMasterData());
				} else {
					response.setError(5000, "Invalid service point");
				}
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("masterDataForRegistration response :" + response);

		} catch (Exception e) {
			logger.error("Error in masterDataForRegistration :" + e);
			response.setError(5000, "Error while getting master data for registration");
		}
		return response.toString();
	}
}
