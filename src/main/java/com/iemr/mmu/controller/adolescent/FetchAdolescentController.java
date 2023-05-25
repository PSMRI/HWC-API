package com.iemr.mmu.controller.adolescent;

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

import com.iemr.mmu.data.nurse.CommonUtilityClass;
import com.iemr.mmu.service.adolescent.AdolescentAndChildCareService;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/***
 * 
 * @author IN40068837
 *
 */

@CrossOrigin
@RestController
@RequestMapping(value = "/child-adolescent-care", headers = "Authorization")
public class FetchAdolescentController {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private AdolescentAndChildCareService adolescentAndChildCareService;

	/**
	 * @Objective Fetching beneficiary visit details entered by nurse.
	 * @param comingRequest
	 * @return visit details in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Visit details from Nurse CAC", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenVisitDetailsFrmNurseCAC" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenVisitDetailsFrmNurseCAC(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request obj to fetch CAC visit details :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.length() > 1) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = adolescentAndChildCareService.getBenVisitDetailsFrmNurseCAC(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request : missing visit information");
			}
			logger.info("getBenVisitDetailsFrmNurseCAC response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary visit data : " + e.getLocalizedMessage());
			logger.error("Error in getBenVisitDetailsFrmNurseCAC:" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary history details entered by nurse.
	 * @param comingRequest
	 * @return history details in JSON format
	 */

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary CAC History details from Nurse to Doctor ", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenHistoryDetails" }, method = { RequestMethod.POST })

	public String getBenHistoryDetails(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody CommonUtilityClass commonUtilityClass) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenHistoryDetails request:" + commonUtilityClass.toString());
		try {

			if (commonUtilityClass.getBenRegID() != null) {

				String s = adolescentAndChildCareService.getBirthAndImmuniHistory(commonUtilityClass.getBenRegID(),
						commonUtilityClass.getVisitCode());
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request : missing ben information");
			}
			logger.info("getBenHistoryDetails response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary history data : " + e.getLocalizedMessage());
			logger.error("Error in getBenHistoryDetails:" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary vital details entered by nurse.
	 * @param comingRequest
	 * @return vital details in JSON format
	 */

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary vital details from Nurse CAC", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenVitalDetailsFrmNurse" }, method = { RequestMethod.POST })
	public String getBenVitalDetailsFrmNurse(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenVitalDetailsFrmNurse request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = adolescentAndChildCareService.getBeneficiaryVitalDetails(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request : visit information missing");
			}
			logger.info("getBenVitalDetailsFrmNurse response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary vital data : " + e.getLocalizedMessage());
			logger.error("Error in getBenVitalDetailsFrmNurse:" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary immunization service entered by nurse.
	 * @param { "benRegID": "274685", "benVisitID": "171431", "visitCode":
	 *          "30022000171431" }
	 * @return immunization service details in JSON format
	 */

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary CAC immunization service details from Nurse to Doctor ", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenImmunizationServiceDetails" }, method = { RequestMethod.POST })

	public String getBenImmunizationServiceDetails(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody CommonUtilityClass commonUtilityClass) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenImmunizationServiceDetails request:" + commonUtilityClass.toString());
		try {

			if (commonUtilityClass.getBenRegID() != null && commonUtilityClass.getVisitCode() != null) {

				String s = adolescentAndChildCareService.getBeneficiaryImmunizationServiceDetails(
						commonUtilityClass.getBenRegID(), commonUtilityClass.getVisitCode());
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request : missing ben information");
			}
			logger.info("getBenImmunizationServiceDetails response:" + response);
		} catch (Exception e) {
			response.setError(5000,
					"Error while getting beneficiary immunization service data : " + e.getLocalizedMessage());
			logger.error("Error in getBenImmunizationServiceDetails:" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary doctor details.
	 * @param comingRequest
	 * @return doctor details in JSON format
	 */

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Doctor Entered Details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenCaseRecordFromDoctor" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenCaseRecordFromDoctor(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenCaseRecordFromDoctor CAC request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (null != obj && obj.length() > 1 && obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				 String res = adolescentAndChildCareService.getBenCaseRecordFromDoctorCAC(benRegID,
				 visitCode);
				 response.setResponse(res);
			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenCaseRecordFromDoctor CAC  response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary doctor data : " + e.getLocalizedMessage());
			logger.error("Error in getBenCaseRecordFromDoctor CAC :" + e);
		}
		return response.toString();
	}
}
