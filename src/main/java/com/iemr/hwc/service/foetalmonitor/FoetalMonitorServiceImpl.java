/*
* AMRIT – Accessible Medical Records via Integrated Technology 
* Integrated EHR (Electronic Health Records) Solution 
*
* Copyright (C) "Piramal Swasthya Management and Research Institute" 
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.iemr.hwc.service.foetalmonitor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.data.foetalmonitor.FoetalMonitor;
import com.iemr.hwc.data.foetalmonitor.FoetalMonitorData;
import com.iemr.hwc.data.foetalmonitor.FoetalMonitorDeviceID;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.foetalmonitor.FoetalMonitorDeviceIDRepo;
import com.iemr.hwc.repo.foetalmonitor.FoetalMonitorRepo;
import com.iemr.hwc.utils.config.ConfigProperties;
import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.http.HttpUtils;

@Service
@PropertySource("classpath:application.properties")
public class FoetalMonitorServiceImpl implements FoetalMonitorService {

	@Value("${foetalMonitorFilePath}")
	private String foetalMonitorFilePath;

	@Value("${fetosenseAPIKey}")
	private String foetalMonitorAPIKey;


	static HttpURLConnection con;

	private static HttpUtils httpUtils = new HttpUtils();
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private FoetalMonitorRepo foetalMonitorRepo;

	@Autowired
	private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;

	@Autowired
	private FoetalMonitorDeviceIDRepo foetalMonitorDeviceIDRepo;

	/***
	 * @author DU20091017 update the feto-sense data in the DB
	 * @throws IOException
	 * @throws DocumentException
	 */
	@Override
	public int updateFoetalMonitorData(FoetalMonitor foetalMonitorDataOutside) throws IEMRException {

		try {
			logger.info("start test reult data into DB : " + foetalMonitorDataOutside.toString());
			foetalMonitorDataOutside.setAccelerationsListDB(foetalMonitorDataOutside.getAccelerationsList().toString());
			foetalMonitorDataOutside.setDecelerationsListDB(foetalMonitorDataOutside.getAccelerationsList().toString());
			foetalMonitorDataOutside.setMovementEntriesDB(foetalMonitorDataOutside.getMovementEntries().toString());
			foetalMonitorDataOutside.setAutoFetalMovementDB(foetalMonitorDataOutside.getAutoFetalMovement().toString());
			foetalMonitorDataOutside.setFoetalMonitorMotherID(foetalMonitorDataOutside.getMother().get("cmMotherId"));
			foetalMonitorDataOutside.setFoetalMonitorPartnerID(foetalMonitorDataOutside.getMother().get("partnerId"));
			foetalMonitorDataOutside.setPartnerName(foetalMonitorDataOutside.getMother().get("partnerName"));

			// fetching data from the db
			FoetalMonitor foetalMonitorFetchDataDB = foetalMonitorRepo.getFoetalMonitorDetails(foetalMonitorDataOutside.getFoetalMonitorID());
			if (foetalMonitorFetchDataDB == null || foetalMonitorFetchDataDB.getFoetalMonitorID() == null)
				throw new IEMRException("Invalid partnerFetosenseID");

			foetalMonitorDataOutside.setBeneficiaryID(foetalMonitorFetchDataDB.getBeneficiaryID());
			foetalMonitorDataOutside.setBeneficiaryRegID(foetalMonitorFetchDataDB.getBeneficiaryRegID());

			if (foetalMonitorFetchDataDB.getVisitCode() != null)
				foetalMonitorDataOutside.setVisitCode(foetalMonitorFetchDataDB.getVisitCode());
			foetalMonitorDataOutside.setTestTime(foetalMonitorFetchDataDB.getTestTime());
			foetalMonitorDataOutside.setMotherLMPDate(foetalMonitorFetchDataDB.getMotherLMPDate());
			foetalMonitorDataOutside.setMotherName(foetalMonitorFetchDataDB.getMotherName());
			foetalMonitorDataOutside.setFoetalMonitorTestId(foetalMonitorFetchDataDB.getFoetalMonitorTestId());
			foetalMonitorDataOutside.setProviderServiceMapID(foetalMonitorFetchDataDB.getProviderServiceMapID());
			foetalMonitorDataOutside.setBenFlowID(foetalMonitorFetchDataDB.getBenFlowID());
			foetalMonitorDataOutside.setVanID(foetalMonitorFetchDataDB.getVanID());
			foetalMonitorDataOutside.setTestName(foetalMonitorFetchDataDB.getTestName());
			foetalMonitorDataOutside.setCreatedBy(foetalMonitorFetchDataDB.getCreatedBy());

			foetalMonitorDataOutside.setResultState(true);
			foetalMonitorDataOutside.setDeleted(foetalMonitorFetchDataDB.getDeleted());

			// need to write the code for changing the report path data to base 64 and save
			// it in DB

			String filePath = generatePDF(foetalMonitorDataOutside.getReportPath());
			foetalMonitorDataOutside.setaMRITFilePath(filePath);

			// saving the feto sense response to DB
			foetalMonitorDataOutside = foetalMonitorRepo.save(foetalMonitorDataOutside);

			int flagUpdate = 0;

			// updating lab technician flag to 3 from 2 as we got the response from feto
			// sense
			if (foetalMonitorDataOutside != null && foetalMonitorDataOutside.getFoetalMonitorID() > 0) {

				// need to check how many records are there with benflowID
				short lab_technician_flag = 3;
				ArrayList<FoetalMonitor> foetalMonitorDataOnBenFlowID = foetalMonitorRepo
						.getFoetalMonitorDetailsByFlowId(foetalMonitorFetchDataDB.getBenFlowID());
				if (foetalMonitorDataOnBenFlowID.size() > 0) {
					// if any of the record is not updated then marking lab flag as 2 - not able to
					// open in doctor screen.
					for (FoetalMonitor data : foetalMonitorDataOnBenFlowID) {
						if (data != null && !data.getResultState()) {
							lab_technician_flag = 2;
						}
					}
				}
				flagUpdate = beneficiaryFlowStatusRepo.updateLabTechnicianFlag(lab_technician_flag,
						foetalMonitorFetchDataDB.getBenFlowID());
				if (flagUpdate > 0) {
					logger.info("End test reult data into DB ");
					return 1;
				} else
					throw new IEMRException("Error in updating the lab technician flag");
			} else
				throw new IEMRException("Error in updating fetosense data");

		} catch (Exception e) {
			throw new IEMRException("Error in updating fetosense data" + e.getMessage());
		}

	}

	// generate report file in file storage
	private String generatePDF(String filePath) throws IEMRException {
		String filePathLocal = "";
		Long timeStamp = System.currentTimeMillis();
		try {

			URL url = new URL(filePath);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setDoInput(true);
			filePathLocal = foetalMonitorFilePath + "/" + timeStamp.toString() + ".pdf";
			Path path = Paths.get(filePathLocal);
			Files.copy(con.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			con.disconnect();
		}

		return filePathLocal;
	}

	// generate report file in file storage
	@Override
	public String readPDFANDGetBase64(String filePath) throws IEMRException, IOException, FileNotFoundException {
		// FileInputStream file = new FileInputStream(filePath);
		byte[] byteArray = Files.readAllBytes(Paths.get(filePath));
		return Base64.getEncoder().encodeToString(byteArray);
	}

	/***
	 * sends the details to foetal monitor.
	 */
	@Override
	// @Transactional(rollbackFor = Exception.class)
	public String sendFoetalMonitorTestDetails(FoetalMonitor request, String auth) throws Exception {

		logger.info("start fetosense test request : " + request.getTestName());
		try {
			Long benID = foetalMonitorRepo.getBenID(request.getBeneficiaryRegID());
			request.setBeneficiaryID(benID);
			// Saving Foetal Monitor Data in Amrit DB
			request = foetalMonitorRepo.save(request);

			if (request != null && request.getFoetalMonitorID() > 0) {

				FoetalMonitorData foetalMonitorTestDetails = new FoetalMonitorData();
				foetalMonitorTestDetails.setPartnerFoetalMonitorId(request.getFoetalMonitorID());

				// send benid in place of benregid to foetal monitor
				foetalMonitorTestDetails.setBeneficiaryRegID(benID);

				String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
				SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT);

				foetalMonitorTestDetails.setMotherLMPDate(sdf.format(request.getMotherLMPDate()));
				foetalMonitorTestDetails.setMotherName(request.getMotherName());
				foetalMonitorTestDetails.setTestName(request.getTestName());

				// checking whether device ID is mapped or not
				FoetalMonitorDeviceID deviceIDForVanID = foetalMonitorDeviceIDRepo.getDeviceIDForVanID(request.getVanID());

				if (deviceIDForVanID != null && deviceIDForVanID.getDeviceID() != null) {
					foetalMonitorTestDetails.setDeviceID(deviceIDForVanID.getDeviceID());
				} else
					throw new RuntimeException("Spoke is not mapped with Fetosense deviceID");

				JsonParser parser = new JsonParser();
				ResponseEntity<String> result = null;

				HashMap<String, Object> header = new HashMap<>();
				header.put("apiKey", foetalMonitorAPIKey);

				String requestObj = new Gson().toJson(foetalMonitorTestDetails).toString();

				logger.info("calling foetal monitor API with request OBJ : " + requestObj);
				// Invoking Foetal monitor API - Sending mother data and test details to foetal monitor
				result = httpUtils.postWithResponseEntity(
						ConfigProperties.getPropertyByName("fetosense-api-url-ANCTestDetails"), requestObj, header);
				logger.info("Foetal monitor register mother API response : " + result.toString());

				// check foetal monitor API response code
				if (result != null && Integer.parseInt(result.getStatusCode().toString()) == 200) {
					JsonObject responseObj = (JsonObject) parser.parse(result.getBody());
					String responseData = responseObj.get("message").getAsString();
					if (responseData != null) {
						logger.info("end foetal monitor test request: " + request.getTestName());
						return "Patient details sent to foetal monitor device successfully. Please select patient name on device and start the test";
					} else {
						throw new RuntimeException("Foetal monitor register mother API is giving response as null");
					}
				} else {
					throw new RuntimeException(
							"Error while registering mother in foetal monitor, foetal monitor response status code : "
									+ Integer.parseInt(result.getStatusCode().toString()));
				}

			} else
				throw new RuntimeException("Unable to generate foetal monitor id in TM");

		}
		/**
		 * @author SH20094090
		 * @purpose To get response body in case of exception
		 */
		catch (HttpClientErrorException e) {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(e.getResponseBodyAsString());
			jsnOBJ = jsnElmnt.getAsJsonObject();
			if (request != null && request.getPartnerFoetalMonitorId() != null && request.getPartnerFoetalMonitorId() > 0) {
				// String response = e.getres;
				logger.info("Foetal monitor test request transaction roll-backed");
				request.setDeleted(true);
				foetalMonitorRepo.save(request);
			}
			if (jsnOBJ.get("status") != null && jsnOBJ.get("message") != null)
				throw new Exception(
						"Unable to raise test request, error is : " + (jsnOBJ.get("message").getAsString()));
			else
				throw new Exception("Unable to raise test request, error is :  " + e.getMessage());

		} catch (Exception e) {
			// if record is created, and not raised in foetal monitor device, soft delete it
			if (request != null && request.getPartnerFoetalMonitorId() != null && request.getPartnerFoetalMonitorId() > 0) {
				logger.info("Foetal monitor test request transaction roll-backed");
				request.setDeleted(true);
				foetalMonitorRepo.save(request);
			}
			throw new Exception("Unable to raise test request, error is :  " + e.getMessage());
		}

	}

	@Override
	public String getFoetalMonitorDetails(Long benFlowID) throws IEMRException {

		try {
			Map<String, Object> resMap = new HashMap<>();
			ArrayList<FoetalMonitor> foetalMonitorData = foetalMonitorRepo.getFoetalMonitorDetailsByFlowId(benFlowID);

			List<FoetalMonitor> foetalMonitorList = new ArrayList<FoetalMonitor>();
			for (FoetalMonitor data : foetalMonitorData) {
				FoetalMonitor listData = new FoetalMonitor(data.getFoetalMonitorID(), data.getBeneficiaryRegID(),
						data.getBenFlowID(), data.getVisitCode(), data.getFoetalMonitorTestId(), data.getResultState());
				foetalMonitorList.add(listData);
			}
			resMap.put("benFetosenseData", foetalMonitorList);
			return new Gson().toJson(resMap);
		} catch (Exception e) {
			throw new IEMRException("Error in fetching foetal monitor details " + e.getMessage());
		}

	}

}
