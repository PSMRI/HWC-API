/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.controller.quickconsult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.mmu.data.quickConsultation.WrapperQuickConsultation;
import com.iemr.mmu.service.quickConsultation.QuickConsultationServiceImpl;
import com.iemr.mmu.service.tele_consultation.SMSGatewayServiceImpl;
import com.iemr.mmu.utils.mapper.InputMapper;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author NE298657
 * @Objective Saving general OPD quick consult data for Nurse and Doctor both.
 * @Date 12-01-2018
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/genOPD-QC-quickConsult", headers = "Authorization")
public class QuickConsultCreateController {
	private Logger logger = LoggerFactory.getLogger(QuickConsultCreateController.class);
	private QuickConsultationServiceImpl quickConsultationServiceImpl;

	@Autowired
	public void setQuickConsultationServiceImpl(QuickConsultationServiceImpl quickConsultationServiceImpl) {
		this.quickConsultationServiceImpl = quickConsultationServiceImpl;
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective first data will be pushed to BenVisitDetails Table and benVisitID
	 *            will be generated and then this benVisitID will be patched with
	 *            Beneficiary Vital and Anthropometry Detail Object and pushed to
	 *            Database table
	 */
	@CrossOrigin
	@ApiOperation(value = "Save quick consult nurse data (QC)..", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/nurseData" }, method = { RequestMethod.POST })
	public String saveBenQuickConsultDataNurse(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		logger.info("Quick consult nurse data save request : " + requestObj);
		try {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			if (jsnOBJ != null) {
				String r = quickConsultationServiceImpl.quickConsultNurseDataInsert(jsnOBJ, Authorization);
				response.setResponse(r);
//				if (r == 1) {
//					response.setResponse("Data saved successfully");
//				} else {
//					// Handle error and required msg...
//					response.setError(500, "Unable to save data");
//				}
			} else {
				response.setError(5000, "Invalid request");
			}
		} catch (Exception e) {
			logger.error("Error while saving quick consult nurse data: " + e);
			response.setError(5000, "Unable to save data");
		}
		return response.toString();
	}

	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Save beneficiary data for doctor quick consult - QC.
	 */

	@CrossOrigin
	@ApiOperation(value = "save Quick Consultation Detail for doctor", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/doctorData" }, method = { RequestMethod.POST })
	public String saveQuickConsultationDetail(
			@ApiParam(value = "{\"quickConsultation\":{\"beneficiaryRegID\":\"Long\",\"providerServiceMapID\": \"Integer\", \"benVisitID\":\"Long\", \"benChiefComplaint\":[{\"chiefComplaintID\":\"Integer\", "
					+ "\"chiefComplaint\":\"String\", \"duration\":\"Integer\", \"unitOfDuration\":\"String\"}], \"description\":\"String\""
					+ "\"clinicalObservation\":\"String\", \"externalInvestigation\":\"String\", \"diagnosisProvided\":\"String\", \"instruction\":\"String\", \"remarks\":\"String\","
					+ "\"prescribedDrugs\":[{\"drugForm\":\"String\", \"drugTradeOrBrandName\":\"String\", \"genericDrugName\":\"String\", \"drugStrength\":\"String\", "
					+ "\"dose\":\"String\", \"route\":\"String\", \"frequency\":\"String\", \"drugDuration\":\"String\", \"relationToFood\":\"String\", "
					+ "\"specialInstruction\":\"String\"}], \"labTestOrders\":[{\"testID\":\"String\", \"testName\":\"String\", \"testingRequirements\":\"String\","
					+ " \"isRadiologyImaging\":\"String\", \"createdBy\":\"String\"}, {\"testID\":\"Integer\", \"testName\":\"String\", "
					+ "\"testingRequirements\":\"String\", \"isRadiologyImaging\":\"Boolean\"}],"
					+ "\"createdBy\":\"String\"}}") @RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {

		OutputResponse response = new OutputResponse();
		logger.info("Quick consult doctor data save request:" + requestObj);

		try {
			WrapperQuickConsultation wrapperQuickConsultation = InputMapper.gson().fromJson(requestObj,
					WrapperQuickConsultation.class);

			JsonObject quickConsultDoctorOBJ = wrapperQuickConsultation.getQuickConsultation();
			Integer i = quickConsultationServiceImpl.quickConsultDoctorDataInsert(quickConsultDoctorOBJ, Authorization);

			if (i != null && i > 0) {
				response.setResponse("Data saved successfully");
			} else {
				response.setError(5000, "Unable to save data");
			}
			logger.info("Quick consult doctor data save response:" + response);
		} catch (Exception e) {
			logger.error("Error while saving quick consult doctor data:" + e);
			response.setError(5000, "Unable to save data. " + e.getMessage());
		}

		return response.toString();
	}
}
