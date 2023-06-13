/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.controller.common.main;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.mmu.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.mmu.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.mmu.utils.mapper.InputMapper;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping(value = "/common", headers = "Authorization")
/** Objective: Performs updating Beneficiary status flag */
public class UpdateCommonController {
	private Logger logger = LoggerFactory.getLogger(UpdateCommonController.class);
	private InputMapper inputMapper = new InputMapper();
	private CommonNurseServiceImpl commonNurseServiceImpl;
	@Autowired
	private CommonDoctorServiceImpl commonDoctorServiceImpl;

	@Autowired
	public void setCommonNurseServiceImpl(CommonNurseServiceImpl commonNurseServiceImpl) {
		this.commonNurseServiceImpl = commonNurseServiceImpl;
	}

	// this functionality are moved to registrar update controller.
	// 16-04-2018, Neeraj kumar
	@CrossOrigin
	@ApiOperation(value = "update Beneficiary Status Flag", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/benDetailsAndSubmitToNurse" }, method = { RequestMethod.POST })
	public String saveBeneficiaryVisitDetail(
			@ApiParam(value = "{\"beneficiaryRegID\": \"Long\"}") @RequestBody String comingRequest) {

		OutputResponse response = new OutputResponse();
		inputMapper = new InputMapper();
		logger.info("benDetailsAndSubmitToNurse request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("beneficiaryRegID")) {
				if (obj.getLong("beneficiaryRegID") > 0) {

					Integer i = commonNurseServiceImpl.updateBeneficiaryStatus('R', obj.getLong("beneficiaryRegID"));
					if (i != null && i > 0) {
						response.setResponse("Beneficiary Successfully Submitted to Nurse Work-List.");
					} else {
						response.setError(500, "Something went Wrong please try after Some Time !!!");
					}

				} else {
					response.setError(500, "Beneficiary Registration ID is Not valid !!!");
				}
			} else {
				response.setError(500, "Beneficiary Registration ID is Not valid !!!");
			}
			logger.info("benDetailsAndSubmitToNurse response:" + response);
		} catch (Exception e) {
			response.setError(e);
			logger.error("Error in benDetailsAndSubmitToNurse:" + e);
		}

		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "Extend Redis Session for 30 mins", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/extend/redisSession" }, method = { RequestMethod.POST })
	public String extendRedisSession() {
		OutputResponse response = new OutputResponse();
		try {
			// temp code later have to validate the user also.
			response.setResponse("Session extended for 30 mins");
		} catch (Exception e) {
			logger.error("Error while extending running session");
		}
		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "Soft delete prescribed medicine", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/doctor/delete/prescribedMedicine" }, method = { RequestMethod.POST })
	public String deletePrescribedMedicine(@RequestBody String requestOBJ) {
		OutputResponse response = new OutputResponse();
		try {
			if (requestOBJ != null) {
				JSONObject obj = new JSONObject(requestOBJ);
				String s = commonDoctorServiceImpl.deletePrescribedMedicine(obj);
				if (s != null)
					response.setResponse(s);
				else
					response.setError(5000, "error while deleting record");
			} else {

			}
		} catch (Exception e) {
			logger.error("Error while deleting prescribed medicine");
			response.setError(e);
		}
		return response.toString();
	}
}
