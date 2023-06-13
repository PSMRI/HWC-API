/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.controller.family_planning;

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
import com.iemr.mmu.service.family_planning.FamilyPlanningService;
import com.iemr.mmu.utils.exception.IEMRException;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping(value = "/family-planning", headers = "Authorization")
public class FetchFamilyPlanningController {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private FamilyPlanningService familyPlanningService;

	/**
	 * @Objective Fetching beneficiary visit details enterted by nurse.
	 * @param benRegID and benVisitID
	 * @return visit details in JSON format
	 */

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Visit details from Nurse family-planning", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenVisitDetails-Nurse-FamilyPlanning" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenVisitDetailsFrmNurseFamilyPlanning(
			@ApiParam(value = "{\"benRegID\":\"Long\", \"visitCode\":\"Long\"}") @RequestBody CommonUtilityClass commonUtilityClass) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for FamilyPlanning visit data fetching :" + commonUtilityClass.toString());
		try {
			if (commonUtilityClass != null && commonUtilityClass.getBenRegID() != null
					&& commonUtilityClass.getVisitCode() != null) {
				String s = familyPlanningService.getBenVisitDetailsFrmNurseFP(commonUtilityClass.getBenRegID(),
						commonUtilityClass.getVisitCode());
				if (s != null)
					response.setResponse(s);
				else
					throw new IEMRException("No record found");
			} else
				throw new IEMRException("Invalid request - Beneficiary and visit info required");
			// call service to fetch data
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary visit data : " + e.getLocalizedMessage());
			logger.error("Error while getting beneficiary visit data:" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary vital details entered by nurse.
	 * @param benRegID and benVisitID
	 * @return vital details in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary vital details from Nurse Family-Planning", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenVitalDetailsFrmNurseFamilyPlanning" }, method = { RequestMethod.POST })
	public String getBenVitalDetailsFrmNurseFamilyPlanning(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody CommonUtilityClass commonUtilityClass) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for FamilyPlanning vital data fetching :" + commonUtilityClass.toString());
		try {
			if (commonUtilityClass.getBenRegID() != null && commonUtilityClass.getVisitCode() != null) {
				String s = familyPlanningService.getBeneficiaryVitalDetailsFP(commonUtilityClass.getBenRegID(),
						commonUtilityClass.getVisitCode());
				if (s != null)
					response.setResponse(s);
				else
					throw new IEMRException("No record found");
			} else
				throw new IEMRException("Invalid request - Beneficiary and visit info required");
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary vital data : " + e.getLocalizedMessage());
			logger.error("Error while getting beneficiary vital data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary FamilyPlanning details entered by nurse.
	 * @param benRegID and benVisitID
	 * @return FamilyPlanning details in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary FamilyPlanning details from Nurse", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenFPDetailsFrmNurseFamilyPlanning" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenFPDetailsFrmNurseFamilyPlanning(
			@ApiParam(value = "{\"benRegID\":\"Long\", \"visitCode\":\"Long\"}") @RequestBody CommonUtilityClass commonUtilityClass) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for FamilyPlanning details data fetching :" + commonUtilityClass.toString());
		try {
			String s = familyPlanningService.getBeneficiaryFPDetailsFP(commonUtilityClass.getBenRegID(),
					commonUtilityClass.getVisitCode());
			if (s != null)
				response.setResponse(s);
			else
				throw new IEMRException("No record found");
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary FamilyPlanning data " + e.getLocalizedMessage());
			logger.error("Error while getting beneficiary FamilyPlanning data:" + e);
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

		logger.info("getBenCaseRecordFromDoctor FP request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (null != obj && obj.length() > 1 && obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = familyPlanningService.getBenCaseRecordFromDoctorFP(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenCaseRecordFromDoctor FP  response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary doctor data : " + e.getLocalizedMessage());
			logger.error("Error in getBenCaseRecordFromDoctor FP :" + e);
		}
		return response.toString();
	}

}
