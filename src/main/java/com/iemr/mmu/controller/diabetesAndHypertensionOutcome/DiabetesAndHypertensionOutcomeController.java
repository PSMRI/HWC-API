package com.iemr.mmu.controller.diabetesAndHypertensionOutcome;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.mmu.service.ncdscreening.DiabetesAndHypertensionOutcomeService;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping(value = "/screeningOutcome", headers = "Authorization")
public class DiabetesAndHypertensionOutcomeController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	@Autowired
	private DiabetesAndHypertensionOutcomeService diabetesAndHypertensionOutcomeService;
	@CrossOrigin()
	@ApiOperation(value = "Get Hypertension Screening Outcome", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/hypertension" }, method = { RequestMethod.POST })
	public String getHypertensionOutcome(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object getting hypertension screening outcome :" + comingRequest);
		try {
				String res = diabetesAndHypertensionOutcomeService.getHypertensionOutcome(comingRequest);
				response.setResponse(res);
		} catch (Exception e) {
			response.setError(5000, "Error while getting hypertension screening outcome");
			logger.error("Error while getting hypertension screening outcome :" + e);
		}
		return response.toString();
	}
	
	@CrossOrigin()
	@ApiOperation(value = "Get Diabetes Screening Outcome", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/diabetes" }, method = { RequestMethod.POST })
	public String getDiabetesOutcome(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object getting diabetes screening outcome :" + comingRequest);
		try {
				String res = diabetesAndHypertensionOutcomeService.getDiabetesOutcome(comingRequest);
				response.setResponse(res);
		} catch (Exception e) {
			response.setError(5000, "Error while getting diabetes screening outcome");
			logger.error("Error while getting diabetes screening outcome :" + e);
		}
		return response.toString();
	}
}
