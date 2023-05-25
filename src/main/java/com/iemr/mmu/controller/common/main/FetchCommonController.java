package com.iemr.mmu.controller.common.main;

import javax.ws.rs.core.MediaType;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.mmu.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.mmu.data.nurse.CommonUtilityClass;
import com.iemr.mmu.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.mmu.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.mmu.service.common.transaction.CommonServiceImpl;
import com.iemr.mmu.utils.mapper.InputMapper;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping(value = "/common", headers = "Authorization")
public class FetchCommonController {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	private CommonDoctorServiceImpl commonDoctorServiceImpl;
	private CommonNurseServiceImpl commonNurseServiceImpl;
	private CommonServiceImpl commonServiceImpl;

	@Autowired
	public void setCommonServiceImpl(CommonServiceImpl commonServiceImpl) {
		this.commonServiceImpl = commonServiceImpl;
	}

	@Autowired
	public void setCommonDoctorServiceImpl(CommonDoctorServiceImpl commonDoctorServiceImpl) {
		this.commonDoctorServiceImpl = commonDoctorServiceImpl;
	}

	@Autowired
	public void setCommonNurseServiceImpl(CommonNurseServiceImpl commonNurseServiceImpl) {
		this.commonNurseServiceImpl = commonNurseServiceImpl;
	}

	@Deprecated
	@CrossOrigin()
	@ApiOperation(value = "provides doctor worklist", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getDocWorklist" }, method = { RequestMethod.GET })
	public String getDocWorkList() {
		OutputResponse response = new OutputResponse();
		try {
			String s = commonDoctorServiceImpl.getDocWorkList();
			response.setResponse(s);
		} catch (Exception e) {
			logger.error("Error in getDocWorkList:" + e);
			response.setError(e);
		}
		return response.toString();
	}

	// doc worklist new
	@CrossOrigin()
	@ApiOperation(value = "provides doctor worklist", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getDocWorklistNew/{providerServiceMapID}/{serviceID}/{vanID}" }, method = {
			RequestMethod.GET })
	public String getDocWorkListNew(@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("serviceID") Integer serviceID, @PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		try {
			if (providerServiceMapID != null && serviceID != null) {
				String s = commonDoctorServiceImpl.getDocWorkListNew(providerServiceMapID, serviceID, vanID);
				if (s != null)
					response.setResponse(s);
			} else {
				logger.error("Invalid request, either ProviderServiceMapID or ServiceID is invalid. PSMID = "
						+ providerServiceMapID + " SID = " + serviceID);
				response.setError(5000, "Invalid request, either ProviderServiceMapID or ServiceID is invalid");
			}

		} catch (Exception e) {
			logger.error("Error in getDocWorkList:" + e);
			response.setError(5000, "Error while getting doctor worklist");
		}
		return response.toString();
	}

	// doc worklist new (TM future scheduled beneficiary)
	@CrossOrigin()
	@ApiOperation(value = "provides doctor worklist future scheduled for TM", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = {
			"/getDocWorkListNewFutureScheduledForTM/{providerServiceMapID}/{serviceID}/{vanID}" }, method = {
					RequestMethod.GET })
	public String getDocWorkListNewFutureScheduledForTM(
			@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("serviceID") Integer serviceID, @PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		try {
			if (providerServiceMapID != null && serviceID != null) {
				String s = commonDoctorServiceImpl.getDocWorkListNewFutureScheduledForTM(providerServiceMapID,
						serviceID, vanID);
				if (s != null)
					response.setResponse(s);
			} else {
				logger.error("Invalid request, either ProviderServiceMapID or ServiceID is invalid. PSMID = "
						+ providerServiceMapID + " SID = " + serviceID);
				response.setError(5000, "Invalid request, either ProviderServiceMapID or ServiceID is invalid");
			}

		} catch (Exception e) {
			logger.error("Error in getDocWorkListFutureScheduledbeneficiary:" + e);
			response.setError(5000, "Error while getting doctor worklist for future scheduled beneficiay");
		}
		return response.toString();
	}

	@Deprecated
	@CrossOrigin()
	@ApiOperation(value = "Get Nurse worklist", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getNurseWorklist" }, method = { RequestMethod.GET })
	public String getNurseWorkList(@PathVariable("visitCategoryID") Integer visitCategoryID) {
		OutputResponse response = new OutputResponse();
		try {
			String s = commonNurseServiceImpl.getNurseWorkList();
			response.setResponse(s);
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("Error in getNurseWorklist:" + e);
			response.setError(e);
		}
		return response.toString();
	}

	// nurse worklist new
	@CrossOrigin()
	@ApiOperation(value = "Get Nurse worklist new", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getNurseWorklistNew/{providerServiceMapID}/{serviceID}/{vanID}" }, method = {
			RequestMethod.GET })
	public String getNurseWorkListNew(@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		try {
			String s = commonNurseServiceImpl.getNurseWorkListNew(providerServiceMapID, vanID);
			if (s != null)
				response.setResponse(s);
			else
				response.setError(5000, "Error while getting nurse worklist");
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("Error in getNurseWorklist:" + e);
			response.setError(5000, "Error while getting nurse worklist");
		}
		return response.toString();
	}

	// nurse worklist TC schedule (current-date) new
	@CrossOrigin()
	@ApiOperation(value = "Get Nurse worklist new", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getNurseWorkListTcCurrentDate/{providerServiceMapID}/{serviceID}/{vanID}" }, method = {
			RequestMethod.GET })
	public String getNurseWorkListTcCurrentDateNew(@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		try {
			String s = commonNurseServiceImpl.getNurseWorkListTcCurrentDate(providerServiceMapID, vanID);
			if (s != null)
				response.setResponse(s);
			else
				response.setError(5000, "Error while getting nurse worklist");
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("Error in getNurseWorklist:" + e);
			response.setError(5000, "Error while getting nurse worklist");
		}
		return response.toString();
	}

	// nurse worklist TC schedule (future-date) new
	@CrossOrigin()
	@ApiOperation(value = "Get Nurse worklist new", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getNurseWorkListTcFutureDate/{providerServiceMapID}/{serviceID}/{vanID}" }, method = {
			RequestMethod.GET })
	public String getNurseWorkListTcFutureDateNew(@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		try {
			String s = commonNurseServiceImpl.getNurseWorkListTcFutureDate(providerServiceMapID, vanID);
			if (s != null)
				response.setResponse(s);
			else
				response.setError(5000, "Error while getting nurse worklist");
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("Error in getNurseWorklist:" + e);
			response.setError(5000, "Error while getting nurse worklist");
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Doctor Entered Previous significant Findings", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getDoctorPreviousSignificantFindings" }, method = { RequestMethod.POST })
	public String getDoctorPreviousSignificantFindings(
			@ApiParam(value = "{\"beneficiaryRegID\": \"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj != null && obj.has("beneficiaryRegID") && obj.get("beneficiaryRegID") != null) {
				Long beneficiaryRegID = obj.getLong("beneficiaryRegID");
				String s = commonDoctorServiceImpl.fetchBenPreviousSignificantFindings(beneficiaryRegID);
				if (s != null)
					response.setResponse(s);
				else
					response.setError(5000, "Error while getting previous significant findings");
			} else {
				response.setError(5000, "Invalid data!");
			}
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("Error while fetching previous significant findings" + e);
			response.setError(5000, "Error while getting previous significant findings");
		}
		return response.toString();
	}

	// Get Lab technician worklist new
	@CrossOrigin()
	@ApiOperation(value = "Get Lab technician worklist new", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getLabWorklistNew/{providerServiceMapID}/{serviceID}/{vanID}" }, method = {
			RequestMethod.GET })
	public String getLabWorkListNew(@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		try {
			String s = commonNurseServiceImpl.getLabWorkListNew(providerServiceMapID, vanID);
			if (s != null)
				response.setResponse(s);
			else
				response.setError(5000, "Error while getting lab technician worklist");
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("Error in getLabWorklist:" + e);
			response.setError(5000, "Error while getting lab technician worklist");
		}
		return response.toString();
	}

	// Get radiologist worklist new
	@CrossOrigin()
	@ApiOperation(value = "Get radiologist worklist new", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getRadiologist-worklist-New/{providerServiceMapID}/{serviceID}/{vanID}" }, method = {
			RequestMethod.GET })
	public String getRadiologistWorklistNew(@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		try {
			String s = commonNurseServiceImpl.getRadiologistWorkListNew(providerServiceMapID, vanID);
			if (s != null)
				response.setResponse(s);
			else
				response.setError(5000, "Error while getting radiologist worklist");
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("Error in getLabWorklist:" + e);
			response.setError(5000, "Error while getting radiologist worklist");
		}
		return response.toString();
	}

	// Get oncologist worklist new
	@CrossOrigin()
	@ApiOperation(value = "Get oncologist worklist new", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getOncologist-worklist-New/{providerServiceMapID}/{serviceID}/{vanID}" }, method = {
			RequestMethod.GET })
	public String getOncologistWorklistNew(@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		try {
			String s = commonNurseServiceImpl.getOncologistWorkListNew(providerServiceMapID, vanID);
			if (s != null)
				response.setResponse(s);
			else
				response.setError(5000, "Error while getting oncologist worklist");
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("Error in getLabWorklist:" + e);
			response.setError(5000, "Error while getting oncologist worklist");
		}
		return response.toString();
	}

	// Get pharma worklist new
	@CrossOrigin()
	@ApiOperation(value = "Get pharma worklist new", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getPharma-worklist-New/{providerServiceMapID}/{serviceID}/{vanID}" }, method = {
			RequestMethod.GET })
	public String getPharmaWorklistNew(@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		try {
			String s = commonNurseServiceImpl.getPharmaWorkListNew(providerServiceMapID, vanID);
			if (s != null)
				response.setResponse(s);
			else
				response.setError(5000, "Error while getting pharma worklist");
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("Error in getLabWorklist:" + e);
			response.setError(5000, "Error while getting pharma worklist");
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "get case-sheet print data for beneficiary.", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/get/Case-sheet/printData" }, method = { RequestMethod.POST })
	public String getCasesheetPrintData(@RequestBody String comingReq,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			if (comingReq != null) {
				BeneficiaryFlowStatus obj = InputMapper.gson().fromJson(comingReq, BeneficiaryFlowStatus.class);
				String casesheetData = commonServiceImpl.getCaseSheetPrintDataForBeneficiary(obj, Authorization);
				response.setResponse(casesheetData);
			} else
				response.setError(5000, "Invalid request");
		} catch (Exception e) {
			logger.error("" + e);
		}

		return response.toString();
	}

	// Start of Fetch Previous Medical History...
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Past History", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenPastHistory" }, method = { RequestMethod.POST })
	public String getBenPastHistory(@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenPastHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getBenPastHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenPastHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting illness and surgery history");
			logger.error("Error in getBenPastHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Tobacco History", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenTobaccoHistory" }, method = { RequestMethod.POST })
	public String getBenTobaccoHistory(@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenTobaccoHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getPersonalTobaccoHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenTobaccoHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting tobacco history");
			logger.error("Error in getBenTobaccoHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Alcohol History", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenAlcoholHistory" }, method = { RequestMethod.POST })
	public String getBenAlcoholHistory(@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenAlcoholHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getPersonalAlcoholHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenAlcoholHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting alcohol history");
			logger.error("Error in getBenAlcoholHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Allergy History", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenAllergyHistory" }, method = { RequestMethod.POST })
	public String getBenANCAllergyHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenAllergyHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getPersonalAllergyHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenAllergyHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting allergy history");
			logger.error("Error in getBenAllergyHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Medication History", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenMedicationHistory" }, method = { RequestMethod.POST })
	public String getBenMedicationHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenMedicationHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getMedicationHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenMedicationHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting medication history");
			logger.error("Error in getBenMedicationHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Family History", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenFamilyHistory" }, method = { RequestMethod.POST })
	public String getBenFamilyHistory(@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenFamilyHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getFamilyHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenFamilyHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting family history");
			logger.error("Error in getBenFamilyHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Menstrual History", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenMenstrualHistory" }, method = { RequestMethod.POST })
	public String getBenMenstrualHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenMenstrualHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getMenstrualHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenMenstrualHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting menstrual history");
			logger.error("Error in getBenMenstrualHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary past Obstetric History", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenPastObstetricHistory" }, method = { RequestMethod.POST })
	public String getBenPastObstetricHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenPastObstetricHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getObstetricHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenPastObstetricHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting obstetric history");
			logger.error("Error in getBenPastObstetricHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Comorbidity Condition Details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenComorbidityConditionHistory" }, method = { RequestMethod.POST })
	public String getBenANCComorbidityConditionHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenComorbidityConditionHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getComorbidHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenComorbidityConditionHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting comodbidity history");
			logger.error("Error in getBenComorbidityConditionHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Optional Vaccine Details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenOptionalVaccineHistory" }, method = { RequestMethod.POST })
	public String getBenOptionalVaccineHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenOptionalVaccineHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getChildVaccineHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenOptionalVaccineHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting optional vaccination history");
			logger.error("Error in getBenOptionalVaccineHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Child Vaccine(Immunization) Details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenChildVaccineHistory" }, method = { RequestMethod.POST })
	public String getBenImmunizationHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenImmunizationHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getImmunizationHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenImmunizationHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting child vaccine(immunization) history");
			logger.error("Error in getBenImmunizationHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Perinatal History Details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenPerinatalHistory" }, method = { RequestMethod.POST })
	public String getBenPerinatalHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenPerinatalHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getBenPerinatalHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenPerinatalHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting perinatal history");
			logger.error("Error in getBenPerinatalHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Child Feeding History Details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenFeedingHistory" }, method = { RequestMethod.POST })
	public String getBenFeedingHistory(@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenFeedingHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getBenFeedingHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenFeedingHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting child feeding history");
			logger.error("Error in getBenFeedingHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Child Development History Details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenDevelopmentHistory" }, method = { RequestMethod.POST })
	public String getBenDevelopmentHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenDevelopmentHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getBenDevelopmentHistoryData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenDevelopmentHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting child development history");
			logger.error("Error in getBenDevelopmentHistory:" + e);
		}
		return response.toString();
	}

	// End of Fetch Previous History...

	/***
	 * fetch ben previous visit details for history case-record(Platform).
	 * 08-08-2018
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get casesheet History of Beneficiary", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBeneficiaryCaseSheetHistory" }, method = { RequestMethod.POST })
	public String getBeneficiaryCaseSheetHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for fetching beneficiary previous visit history :" + comingRequest);
		try {
			String responseData = commonServiceImpl.getBenPreviousVisitDataForCaseRecord(comingRequest);
			if (responseData != null)
				response.setResponse(responseData);
			else
				response.setError(5000, "Error while fetching beneficiary previous visit history details");
		} catch (Exception e) {
			response.setError(5000, "Error while fetching beneficiary previous visit history details");
			logger.error("Error while fetching beneficiary previous visit history :" + e);
		}
		return response.toString();
	}

	// TC specialist worklist new
	@CrossOrigin()
	@ApiOperation(value = "TCSpecialist", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getTCSpecialistWorklist/{providerServiceMapID}/{serviceID}/{userID}" }, method = {
			RequestMethod.GET })
	public String getTCSpecialistWorkListNew(@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("userID") Integer userID, @PathVariable("serviceID") Integer serviceID) {
		OutputResponse response = new OutputResponse();
		try {
			if (providerServiceMapID != null && userID != null) {
				String s = commonDoctorServiceImpl.getTCSpecialistWorkListNewForTM(providerServiceMapID, userID,
						serviceID);
				if (s != null)
					response.setResponse(s);
			} else {
				logger.error("Invalid request, either ProviderServiceMapID or userID is invalid. PSMID = "
						+ providerServiceMapID + " SID = " + userID);
				response.setError(5000, "Invalid request, either ProviderServiceMapID or userID is invalid");
			}

		} catch (Exception e) {
			logger.error("Error in getTC_SpecialistWorkList:" + e);
			response.setError(5000, "Error while getting TC specialist worklist");
		}
		return response.toString();
	}

	// TC specialist worklist new, patient App, 14-08-2020
	@CrossOrigin()
	@ApiOperation(value = "TCSpecialist", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = {
			"/getTCSpecialistWorklistPatientApp/{providerServiceMapID}/{serviceID}/{userID}/{vanID}" }, method = {
					RequestMethod.GET })
	public String getTCSpecialistWorkListNewPatientApp(
			@PathVariable("providerServiceMapID") Integer providerServiceMapID, @PathVariable("userID") Integer userID,
			@PathVariable("serviceID") Integer serviceID, @PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		try {
			if (providerServiceMapID != null && userID != null) {
				String s = commonDoctorServiceImpl.getTCSpecialistWorkListNewForTMPatientApp(providerServiceMapID,
						userID, serviceID, vanID);
				if (s != null)
					response.setResponse(s);
			} else {
				logger.error("Invalid request, either ProviderServiceMapID or userID is invalid. PSMID = "
						+ providerServiceMapID + " SID = " + userID);
				response.setError(5000, "Invalid request, either ProviderServiceMapID or userID is invalid");
			}

		} catch (Exception e) {
			logger.error("Error in getTC_SpecialistWorkList:" + e);
			response.setError(5000, "Error while getting TC specialist worklist");
		}
		return response.toString();
	}

	// TC specialist worklist new future scheduled
	@CrossOrigin()
	@ApiOperation(value = "TCSpecialist future scheduled", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = {
			"/getTCSpecialistWorklistFutureScheduled/{providerServiceMapID}/{serviceID}/{userID}" }, method = {
					RequestMethod.GET })
	public String getTCSpecialistWorklistFutureScheduled(
			@PathVariable("providerServiceMapID") Integer providerServiceMapID, @PathVariable("userID") Integer userID,
			@PathVariable("serviceID") Integer serviceID) {
		OutputResponse response = new OutputResponse();
		try {
			if (providerServiceMapID != null && userID != null) {
				String s = commonDoctorServiceImpl.getTCSpecialistWorkListNewFutureScheduledForTM(providerServiceMapID,
						userID, serviceID);
				if (s != null)
					response.setResponse(s);
			} else {
				logger.error("Invalid request, either ProviderServiceMapID or userID is invalid. PSMID = "
						+ providerServiceMapID + " UserID = " + userID);
				response.setError(5000, "Invalid request, either ProviderServiceMapID or userID is invalid");
			}

		} catch (Exception e) {
			logger.error("Error in getTC_SpecialistWorkList future scheduled:" + e);
			response.setError(5000, "Error while getting TC specialist future scheduled worklist");
		}
		return response.toString();
	}

	// openkm file download
	@CrossOrigin
	@ApiOperation(value = "API will add file sent as string in KM.")
	@RequestMapping(value = "/getKMFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON, headers = "Authorization")
	public String getKMFile(@ApiParam(value = "{}") @RequestBody String request,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		logger.info("add file request is " + request);
		try {
			String s = commonServiceImpl.getOpenKMDocURL(request, Authorization);
			if (s != null)
				response.setResponse(s);
			else {
				logger.error("file not available for request : " + request);
				response.setError(5000, "file not available, please contact administrator");
			}
		} catch (Exception e) {
			logger.error("Error while getting file download url : " + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Physical History", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenPhysicalHistory" }, method = { RequestMethod.POST })
	public String getBenPhysicalHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenPhysicalHistory request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getBenPhysicalHistory(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("getBenPhysicalHistory response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting Physical history");
			logger.error("Error in getBenPhysicalHistory:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary Symptomatic questionnaire answer details", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenSymptomaticQuestionnaireDetails" }, method = { RequestMethod.POST })
	public String getBenSymptomaticQuestionnaireDetails(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Get Beneficiary Symptomatic questionnaire answer details request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getBenSymptomaticQuestionnaireDetailsData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("Get Beneficiary Symptomatic questionnaire answer details response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting details");
			logger.error("Error in Get Beneficiary Symptomatic questionnaire answer details:" + e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary previous Diabetes history", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenPreviousDiabetesHistoryDetails" }, method = { RequestMethod.POST })
	public String getBenPreviousDiabetesHistoryDetails(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Get Beneficiary previous Diabetes history request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getBenPreviousDiabetesData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("Get Beneficiary previous Diabetes history response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting details");
			logger.error("Error in Get Beneficiary previous Diabetes history:" + e);
		}
		return response.toString();
	}

	// nurse worklist coming from MMU application
	@CrossOrigin()
	@ApiOperation(value = "Get MMU Nurse worklist new", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getMmuNurseWorklistNew/{providerServiceMapID}/{serviceID}/{vanID}" }, method = {
			RequestMethod.GET })
	public String getMmuNurseWorklistNew(@PathVariable("providerServiceMapID") Integer providerServiceMapID,
			@PathVariable("vanID") Integer vanID) {
		OutputResponse response = new OutputResponse();
		try {
			String s = commonNurseServiceImpl.getMmuNurseWorkListNew(providerServiceMapID, vanID);
			if (s != null)
				response.setResponse(s);
			else
				response.setError(5000, "Error while getting MMU Nurse Worklist");
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("Error in getNurseWorklist:" + e);
			response.setError(5000, "Error while getting MMU Nurse Worklist");
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary previous Referral history", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenPreviousReferralHistoryDetails" }, method = { RequestMethod.POST })
	public String getBenPreviousReferralHistoryDetails(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("Get Beneficiary previous Referral history request:" + comingRequest);
		try {
			JSONObject obj = new JSONObject(comingRequest);
			if (obj.has("benRegID")) {
				Long benRegID = obj.getLong("benRegID");
				String s = commonServiceImpl.getBenPreviousReferralData(benRegID);
				response.setResponse(s);

			} else {
				logger.info("Invalid Request Data.");
				response.setError(5000, "Invalid request");
			}
			logger.info("Get Beneficiary previous Referral history response:" + response);
		} catch (Exception e) {
			response.setError(5000, "Error while getting details");
			logger.error("Error in Get Beneficiary previous Referral history:" + e);
		}
		return response.toString();
	}

	/**
	 * Author SH20094090
	 * 
	 * @param comingRequest
	 * @return ProviderSpecificMasterData
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get Provider Specific Data", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getProviderSpecificData" }, method = { RequestMethod.POST })
	public String getProviderSpecificData(
			@ApiParam(value = "{\"benvisitID\":\"Long\",\"benvisitCode\":\"Long\",\"fetchMMUDataFor\":\"String\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("getProviderSpecificData request:" + comingRequest);
		try {
			String s = commonServiceImpl.getProviderSpecificData(comingRequest);
			response.setResponse(s);
			logger.info("getProviderSpecificData response:" + response);
		} catch (Exception e) {
			response.setError(5000, e.getMessage());
			logger.error("Error in getProviderSpecificData:" + e);
		}
		return response.toString();
	}

	/**
	 * Author SH20094090
	 * 
	 * @param comingRequest
	 * @return ProviderSpecificMasterData
	 */
	@CrossOrigin()
	@ApiOperation(value = "Calculate Beneficiary BMI Status", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/calculateBMIStatus" }, method = { RequestMethod.POST })
	public String calculateBMIStatus(
			@ApiParam(value = "{\"bmi\":\"double\",\"yearMonth\":\"String\",\"gender\":\"String\"}") @RequestBody String comingRequest) {
		OutputResponse response = new OutputResponse();

		logger.info("calculateBMIStatus request:" + comingRequest);
		try {
			String s = commonNurseServiceImpl.calculateBMIStatus(comingRequest);
			response.setResponse(s);
			logger.info("calculateBMIStatus response:" + response);
		} catch (Exception e) {
			response.setError(5000, e.getMessage());
			logger.error("Error in calculateBMIStatus:" + e.getMessage());
		}
		return response.toString();
	}

	/**
	 * @Objective Fetching beneficiary immunization service history.
	 * @param { "benRegID": "274685" }
	 * @return immunization service details in JSON format
	 */
	@CrossOrigin()
	@ApiOperation(value = "Get Beneficiary immunization service details history ", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/getBenImmunizationServiceHistory" }, method = { RequestMethod.POST })

	public String getBenImmunizationServiceHistory(
			@ApiParam(value = "{\"benRegID\":\"Long\"}") @RequestBody CommonUtilityClass commonUtilityClass) {
		OutputResponse response = new OutputResponse();

		logger.info("getBenImmunizationServiceDetails history request:" + commonUtilityClass.toString());
		try {

			if (commonUtilityClass.getBenRegID() != null) {

				String s = commonServiceImpl.getBenImmunizationServiceHistory(commonUtilityClass.getBenRegID());
				response.setResponse(s);
			} else {
				response.setError(5000, "Invalid request : missing ben information");
			}
			logger.info("getBenImmunizationServiceDetails history response:" + response);
		} catch (Exception e) {
			response.setError(5000,
					"Error while getting beneficiary immunization service hostory data : " + e.getLocalizedMessage());
			logger.error("Error in getBenImmunizationServiceDetails history:" + e);
		}
		return response.toString();
	}

}
