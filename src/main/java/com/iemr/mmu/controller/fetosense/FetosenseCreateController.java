/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.controller.fetosense;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.mmu.data.fetosense.Fetosense;
import com.iemr.mmu.service.fetosense.FetosenseService;
import com.iemr.mmu.service.fetosense.FetosenseServiceImpl;
import com.iemr.mmu.utils.mapper.InputMapper;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author DE40034072
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/fetosense", headers = "Authorization")
public class FetosenseCreateController {
	@Autowired
	private FetosenseService fetosenseService;
	@Autowired
	private FetosenseServiceImpl fetosenseServiceImpl;

	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	/**
	 * 
	 * @Objective Transfer Mother Data and NST/CTG Test Details to Fetosense.
	 * @param JSON requestObj
	 * @return success or failure response
	 */

	@CrossOrigin
	@ApiOperation(value = "Provides the mother data and prescribed test details to fetosense")
	@RequestMapping(value = "/sendMotherTestDetailsToFetosense", method = RequestMethod.POST, headers = "Authorization")
	public ResponseEntity<String> sendANCMotherTestDetailsToFetosense(
			@ApiParam("{\"beneficiaryRegID\":\"Long\",\"benFlowID\":\"Long\",\"testTime\":\"Timestamp\",\"motherLMPDate\":\"Timestamp\",\"motherName\":\"String\",\"fetosenseTestId\":\"Long\",\"testName\":\"String\",\"ProviderServiceMapID\":\"Integer\",\"createdBy\":\"String\"}") @RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String authorization) {
		// logger.info("Request Object for transfering mother data to fetosense" +
		// requestObj);
		OutputResponse output = new OutputResponse();

		try {

			if (requestObj != null) {

				Fetosense fetosenseRequest = InputMapper.gson().fromJson(requestObj, Fetosense.class);

				String response = fetosenseService.sendFetosenseTestDetails(fetosenseRequest, authorization);

				output.setResponse(response);
			} else {
				logger.error("send ANC Mother TestDetails To Fetosense : Invalid request");
				output.setError(404, "Invalid request");
			}
		} catch (Exception e) {
			logger.error("send ANC Mother TestDetails To Fetosense failed with error " + e.getMessage());
			output.setError(5000, e.getMessage());
		}
		return output.toStringWithHttpStatus();
	}

	/***
	 * 
	 * @param requestObj
	 * @param authorization
	 * @return
	 */
	@CrossOrigin
	@ApiOperation(value = "Register Mother")
	@RequestMapping(value = "/registerMother", method = RequestMethod.POST, headers = "Authorization")
	public String saveMother(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String authorization) {

		OutputResponse output = new OutputResponse();

		try {
			output.setResponse("Test in progress");
//			output.setResponse(fetosenseServiceImpl.generatePDF(
//					"https://us-central1-fetosense-v2.cloudfunctions.net/getGraph?apiKey=AWP2x7gH2BKtHXcBIUcB&userId=xrJNj3ZC1prquXa5Kzda&testId=WsHlvDn4wr0cACShfhG5&highlight=false&interpretations=true&comments=true"));
		} catch (Exception e) {

			output.setError(e);
		}

		return output.toString();
	}

}
