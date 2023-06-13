/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.controller.quickconsult;

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

import com.iemr.mmu.service.quickConsultation.QuickConsultationServiceImpl;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author NA874500
 * @Objective Fetching general OPD quick consult data for Nurse and Doctor both.
 * @Date 08-02-2018
 * 
 */

@CrossOrigin
@RestController
@RequestMapping(value = "/genOPD-QC-quickConsult", headers = "Authorization")
public class QuickConsultFetchController {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	private QuickConsultationServiceImpl quickConsultationServiceImpl;

	@Autowired
	public void setQuickConsultationServiceImpl(QuickConsultationServiceImpl quickConsultationServiceImpl) {
		this.quickConsultationServiceImpl = quickConsultationServiceImpl;
	}

	/**
	 * @Objective Fething beneficiary visit data filled by Nurse for Doctor screen...
	 * @param benRegID and benVisitID
	 * @return visit details in JSON format
	 */
	
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Visit details from Nurse screen", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenDataFrmNurseToDocVisitDetailsScreen" }, method = { RequestMethod.POST })
	public String getBenDataFrmNurseScrnToDocScrnVisitDetails(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		logger.info("Quick consult visit data fetch request :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.length() > 1) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String s = quickConsultationServiceImpl.getBenDataFrmNurseToDocVisitDetailsScreen(benRegID, visitCode);
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("Quick consult visit data fetch response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting visit data");
			logger.error("Error while fetching quick consult visit data:" + e);
		}
		return response.toString();
	}
	
	/**
	 * @Objective Fething beneficiary vital data filled by Nurse for Doctor screen...
	 * @param benRegID and benVisitID
	 * @return visit details in JSON format
	 */
	
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary vital details from Nurse GeneralOPD", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenVitalDetailsFrmNurse" }, method = { RequestMethod.POST })
	public String getBenVitalDetailsFrmNurse(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Quick consult vital data fetch request :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = quickConsultationServiceImpl.getBeneficiaryVitalDetails(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid request");
				response.setError(5000, "Invalid request");
			}
			logger.info("Quick consult vital data fetch response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting vital data");
			logger.error("Error while fetching quick consult vital data:" + e);
		}
		return response.toString();
	}
	
	/**
	 * @Objective Fething beneficiary doctor data
	 * @param benRegID and benVisitID
	 * @return visit details in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Doctor Entered Details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenCaseRecordFromDoctorQuickConsult" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenCaseRecordFromDoctorQuickConsult(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Quick consult doctor data fetch request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (null != obj && obj.length() > 1 && obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = quickConsultationServiceImpl.getBenCaseRecordFromDoctorQuickConsult(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid request");
				response.setError(5000, "Invalid request");
			}
			logger.info("Quick consult doctor data fetch response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting doctor data");
			logger.error("Error while fetching quick consult doctor data:" + e);
		}
		return response.toString();
	}
}
