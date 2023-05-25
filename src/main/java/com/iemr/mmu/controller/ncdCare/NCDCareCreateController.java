package com.iemr.mmu.controller.ncdCare;

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
import com.iemr.mmu.service.ncdCare.NCDCareServiceImpl;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author NA874500
 * @Objective Saving NCD Care data for Nurse and Doctor.
 * @Date 21-3-2018
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/NCDCare", headers = "Authorization")
public class NCDCareCreateController {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private NCDCareServiceImpl ncdCareServiceImpl;

	@Autowired
	public void setNcdCareServiceImpl(NCDCareServiceImpl ncdCareServiceImpl) {
		this.ncdCareServiceImpl = ncdCareServiceImpl;
	}

	/**
	 * @Objective Save NCD Care data for nurse.
	 * @param JSON
	 *            requestObj
	 * @return success or failure response
	 */
	@CrossOrigin
	@ApiOperation(value = "Save NCD Care nurse data..", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/nurseData" }, method = { RequestMethod.POST })
	public String saveBenNCDCareNurseData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for NCD Care nurse data saving :" + requestObj);

			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			if (jsnOBJ != null) {
				String ncdCareRes = ncdCareServiceImpl.saveNCDCareNurseData(jsnOBJ, Authorization);
//				if (null != ncdCareRes && ncdCareRes > 0) {
//					response.setResponse("Data saved successfully");
//				} else {
//					response.setResponse("Unable to save data");
//				}
				response.setResponse(ncdCareRes);
			} else {
				response.setError(5000, "Invalid Request !!!");
			}

		} catch (Exception e) {
			logger.error("Error while saving NCD Care nurse data :" + e);
			response.setError(5000, "Unable to save data");
		}
		return response.toString();
	}

	/**
	 * @Objective Save NCD Care data for doctor.
	 * @param JSON
	 *            requestObj
	 * @return success or failure response
	 */
	@CrossOrigin
	@ApiOperation(value = "Save NCD Care doctor data..", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/doctorData" }, method = { RequestMethod.POST })
	public String saveBenNCDCareDoctorData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for NCD Care doctor data saving :" + requestObj);

			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(requestObj);
			jsnOBJ = jsnElmnt.getAsJsonObject();

			if (jsnOBJ != null) {
				Long ncdCareRes = ncdCareServiceImpl.saveDoctorData(jsnOBJ, Authorization);
				if (null != ncdCareRes && ncdCareRes > 0) {
					response.setResponse("Data saved successfully");
				} else {
					response.setResponse("Unable to save data");
				}

			} else {
				response.setResponse("Invalid request");
			}
		} catch (Exception e) {
			logger.error("Error while saving NCD Care doctor data :" + e);
			response.setError(5000, "Unable to save data. " + e.getMessage());
		}
		return response.toString();
	}
}
