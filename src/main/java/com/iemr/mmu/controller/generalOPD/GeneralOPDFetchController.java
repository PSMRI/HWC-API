package com.iemr.mmu.controller.generalOPD;

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

import com.iemr.mmu.service.generalOPD.GeneralOPDServiceImpl;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/***
 * 
 * @author NE298657
 * @Objective Fetching General OPD data for Nurse and Doctor.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/generalOPD", headers = "Authorization")
public class GeneralOPDFetchController {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private GeneralOPDServiceImpl generalOPDServiceImpl;

	@Autowired
	public void setGeneralOPDServiceImpl(GeneralOPDServiceImpl generalOPDServiceImpl) {
		this.generalOPDServiceImpl = generalOPDServiceImpl;
	}

	/**
	 * @Objective Fetching beneficiary visit details enterted by nurse.
	 * @param comingRequest
	 * @return visit details in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Visit details from Nurse General OPD", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenVisitDetailsFrmNurseGOPD" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenVisitDetailsFrmNurseGOPD(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request obj to fetch General OPD visit details :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.length() > 1) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = generalOPDServiceImpl.getBenVisitDetailsFrmNurseGOPD(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenDataFrmNurseScrnToDocScrnVisitDetails response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary visit data");
			logger.error("Error in getBenDataFrmNurseScrnToDocScrnVisitDetails:" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary history details enterted by nurse.
	 * @param comingRequest
	 * @return history details in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary General OPD History details from Nurse to Doctor ", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenHistoryDetails" }, method = { RequestMethod.POST })

	public String getBenHistoryDetails(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenHistoryDetails request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String s = generalOPDServiceImpl.getBenHistoryDetails(benRegID, visitCode);
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenHistoryDetails response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary history data");
			logger.error("Error in getBenHistoryDetails:" + e);
		}
		return response.toString();
	}
	
	/**
	 * @Objective Fetching beneficiary vital details enterted by nurse.
	 * @param comingRequest
	 * @return vital details in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary vital details from Nurse GeneralOPD", consumes = "application/json", produces = "application/json")
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

				String res = generalOPDServiceImpl.getBeneficiaryVitalDetails(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenVitalDetailsFrmNurse response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary vital data");
			logger.error("Error in getBenVitalDetailsFrmNurse:" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary examination details enterted by nurse.
	 * @param comingRequest
	 * @return examination details in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary General OPD Examination details from Nurse to Doctor ", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenExaminationDetails" }, method = { RequestMethod.POST })

	public String getBenExaminationDetails(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenExaminationDetails request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String s = generalOPDServiceImpl.getExaminationDetailsData(benRegID, visitCode);
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenExaminationDetails response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary examination data");
			logger.error("Error in getBenExaminationDetails:" + e);
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
	@RequestMapping(value = { "/getBenCaseRecordFromDoctorGeneralOPD" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenCaseRecordFromDoctorGeneralOPD(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenCaseRecordFromDoctorGeneralOPD request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (null != obj && obj.length() > 1 && obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String res = generalOPDServiceImpl.getBenCaseRecordFromDoctorGeneralOPD(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenCaseRecordFromDoctorGeneralOPD response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary doctor data");
			logger.error("Error in getBenCaseRecordFromDoctorGeneralOPD:" + e);
		}
		return response.toString();
	}
}
