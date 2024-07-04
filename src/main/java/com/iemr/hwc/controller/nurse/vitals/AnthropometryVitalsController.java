package com.iemr.hwc.controller.nurse.vitals;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.iemr.hwc.service.nurse.vitals.AnthropometryVitalsService;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@CrossOrigin
@RequestMapping(value = "/anthropometryVitals", headers = "Authorization", consumes = "application/json", produces = "application/json")
public class AnthropometryVitalsController {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	@Autowired
	private AnthropometryVitalsService anthropometryVitalsService;
	
	//Auto-patching height in anthropometry details
		@CrossOrigin()
		@Operation(summary = "Get beneficiary height details")
		@PostMapping(value = { "/getBenHeightDetailsFrmNurse" })
		public String getBenHeightDetailsFrmNurse(
				@Param(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
			OutputResponse response = new OutputResponse();

			logger.info("Request object for beneficiary height data fetching :" + comingRequest);
			try {
				JSONObject obj = new JSONObject(comingRequest);
				if (obj.has("benRegID")) {
					Long benRegID = obj.getLong("benRegID");

					String res = anthropometryVitalsService.getBeneficiaryHeightDetails(benRegID);
					response.setResponse(res);
				} else {
					logger.info("Invalid request");
					response.setError(5000, "Invalid request");
				}
				logger.info("Beneficiary height data fetching Response:" + response);
			} catch (Exception e) {
				response.setError(5000, "Error while getting beneficiary height data");
				logger.error("Error while getting beneficiary height data :" + e);
			}
			return response.toString();
		}
	
	
}
