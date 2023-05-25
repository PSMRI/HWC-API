package com.iemr.mmu.controller.cancerscreening;

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

import com.iemr.mmu.service.cancerScreening.CSServiceImpl;
import com.iemr.mmu.utils.mapper.InputMapper;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author NA874500
 * @Objective Fetch Cancer screening nurse data.
 * @Date 18-01-2018
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/CS-cancerScreening", headers = "Authorization")
public class CancerScreeningFetchController {

	private InputMapper inputMapper;
	private Logger logger = LoggerFactory.getLogger(CancerScreeningFetchController.class);

	private CSServiceImpl cSServiceImpl;

	@Autowired
	public void setCancerScreeningServiceImpl(CSServiceImpl cSServiceImpl) {
		this.cSServiceImpl = cSServiceImpl;
	}

	/**
	 * @Objective Fetching beneficiary visit details enterted by nurse.
	 * @param benRegID
	 *            and benVisitID
	 * @return visit details in JSON format
	 */

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Visit details from Nurse screen", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenDataFrmNurseToDocVisitDetailsScreen" }, method = { RequestMethod.POST })
	public String getBenDataFrmNurseScrnToDocScrnVisitDetails(
			@ApiParam(value = "{\"benRegID\":\"Long\", \"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for CS visit data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.length() > 1) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");
				String s = cSServiceImpl.getBenDataFrmNurseToDocVisitDetailsScreen(benRegID, visitCode);
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("CS visit data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary visit data");
			logger.error("Error while getting beneficiary visit data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary history details enterted by nurse.
	 * @param benRegID
	 *            and benVisitID
	 * @return history details in JSON format
	 */

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Cancer History details from Nurse screen", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenDataFrmNurseToDocHistoryScreen" }, method = { RequestMethod.POST })
	public String getBenDataFrmNurseScrnToDocScrnHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\", \"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for CS history data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.length() > 1) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");
				String s = cSServiceImpl.getBenDataFrmNurseToDocHistoryScreen(benRegID, visitCode);
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("CS history data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary history data");
			logger.error("Error while getting beneficiary history data :" + e);
		}
		// System.out.println(response.toString());
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary vital details enterted by nurse.
	 * @param benRegID
	 *            and benVisitID
	 * @return vital details in JSON format
	 */

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Vital details from Nurse screen", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenDataFrmNurseToDocVitalScreen" }, method = { RequestMethod.POST })
	public String getBenDataFrmNurseScrnToDocScrnVital(
			@ApiParam(value = "{\"benRegID\":\"Long\", \"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for CS vital data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.length() > 1) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String s = cSServiceImpl.getBenDataFrmNurseToDocVitalScreen(benRegID, visitCode);
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("CS vital data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary vital data");
			logger.error("Error while getting beneficiary vital data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary examination details enterted by nurse.
	 * @param benRegID
	 *            and benVisitID
	 * @return examination details in JSON format
	 */

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Examination details from Nurse screen", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenDataFrmNurseToDocExaminationScreen" }, method = { RequestMethod.POST })
	public String getBenDataFrmNurseScrnToDocScrnExamination(
			@ApiParam(value = "{\"benRegID\":\"Long\", \"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for CS examination data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.length() > 1) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");
				String s = cSServiceImpl.getBenDataFrmNurseToDocExaminationScreen(benRegID, visitCode);
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("CS examination data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary examination data");
			logger.error("Error while getting beneficiary examination data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetch beneficiary previous family history details enterted by
	 *            nurse.
	 * @param benRegID
	 * @return previous family history details in JSON format
	 */

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Cancer Family History", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenCancerFamilyHistory" }, method = { RequestMethod.POST })
	public String getBenCancerFamilyHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for CS family history data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = cSServiceImpl.getBenFamilyHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("CS family history data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary family history data");
			logger.error("Error while getting beneficiary family history data:" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetch beneficiary previous personal history details enterted by
	 *            nurse.
	 * @param benRegID
	 * @return previous personal history details in JSON format
	 */

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Cancer Personal History", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenCancerPersonalHistory" }, method = { RequestMethod.POST })
	public String getBenCancerPersonalHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for CS personal history data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = cSServiceImpl.getBenPersonalHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("CS personal history data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary personal history data");
			logger.error("Error while getting beneficiary personal history data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetch beneficiary previous personal diet history details enterted
	 *            by nurse.
	 * @param benRegID
	 * @return previous personal history details in JSON format
	 */

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Cancer Personal Diet History", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenCancerPersonalDietHistory" }, method = { RequestMethod.POST })
	public String getBenCancerPersonalDietHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for CS personal diet history data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = cSServiceImpl.getBenPersonalDietHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("CS personal diet history data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary personal diet history data");
			logger.error("Error while getting beneficiary personal diet history data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetch beneficiary previous obstetric history details enterted by
	 *            nurse.
	 * @param benRegID
	 * @return previous obstetric history details in JSON format
	 */

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Cancer Obstetric History", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenCancerObstetricHistory" }, method = { RequestMethod.POST })
	public String getBenCancerObstetricHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for CS obstetric history data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = cSServiceImpl.getBenObstetricHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("CS obstetric history data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary obstetric history data");
			logger.error("Error while getting beneficiary obstetric history data:" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetch beneficiary diagnosis details enterted by doctor.
	 * @param benRegID
	 * @return diagnosis details in JSON format
	 */

	@Deprecated
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Diagnosis details from Doctor screen", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenDataFrmDoctorDiagnosisScreen" }, method = { RequestMethod.POST })
	public String getBenDataFrmDoctorDiagnosisScreen(
			@ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for CS diagnosis data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (null != obj && obj.length() > 1 && obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");

				String s = cSServiceImpl.getBenDoctorDiagnosisData(benRegID, visitCode);
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request");
			}
			logger.info("CS diagnosis data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary diagnosis data");
			logger.error("Error while getting beneficiary diagnosis data :" + e);
		}
		return response.toString();
	}

	/**
	 * @Objective Fetch beneficiary diagnosis details enterted by doctor.
	 * @param benRegID
	 * @return doctor details in JSON format
	 */

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Doctor Entered Details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenCaseRecordFromDoctorCS" }, method = { RequestMethod.POST })
	@Transactional(rollbackFor = Exception.class)
	public String getBenCaseRecordFromDoctorCS(
			@ApiParam(value = "{\"benRegID\":\"Long\", \"visitCode\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Request object for CS doctor data fetching :" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (null != obj && obj.length() > 1 && obj.has("benRegID") && obj.has("visitCode")) {
				Long benRegID = obj.getLong("benRegID");
				Long visitCode = obj.getLong("visitCode");
				String res = cSServiceImpl.getBenCaseRecordFromDoctorCS(benRegID, visitCode);
				response.setResponse(res);
			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("CS doctor data fetching Response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting beneficiary doctor data");
			logger.error("Error while getting beneficiary doctor data :" + e);
		}
		return response.toString();
	}

}
