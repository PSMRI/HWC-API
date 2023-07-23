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
package com.iemr.hwc.service.fetosense;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
import com.iemr.hwc.data.fetosense.Fetosense;
import com.iemr.hwc.data.fetosense.FetosenseData;
import com.iemr.hwc.data.fetosense.FetosenseDeviceID;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.fetosense.FetosenseDeviceIDRepo;
import com.iemr.hwc.repo.fetosense.FetosenseRepo;
import com.iemr.hwc.utils.config.ConfigProperties;
import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.http.HttpUtils;

@Service
@PropertySource("classpath:application.properties")
public class FetosenseServiceImpl implements FetosenseService {

	@Value("${fotesenseFilePath}")
	private String fotesenseFilePath;

	@Value("${fetosenseAPIKey}")
	private String fetosenseAPIKey;

	@Value("${fetosenseReportPath}")
	private String fetosenseReportPath;

	static HttpURLConnection con;

	private static HttpUtils httpUtils = new HttpUtils();
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private FetosenseRepo fetosenseRepo;

	@Autowired
	private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;

	@Autowired
	private FetosenseDeviceIDRepo fetosenseDeviceIDRepo;

	/***
	 * @author DU20091017 update the feto-sense data in the DB
	 * @throws IOException
	 * @throws DocumentException
	 */
	@Override
	public int updateFetosenseData(Fetosense fetosenseDataOutside) throws IEMRException {

		try {
			logger.info("start test reult data into DB : " + fetosenseDataOutside.toString());
			fetosenseDataOutside.setAccelerationsListDB(fetosenseDataOutside.getAccelerationsList().toString());
			fetosenseDataOutside.setDecelerationsListDB(fetosenseDataOutside.getAccelerationsList().toString());
			fetosenseDataOutside.setMovementEntriesDB(fetosenseDataOutside.getMovementEntries().toString());
			fetosenseDataOutside.setAutoFetalMovementDB(fetosenseDataOutside.getAutoFetalMovement().toString());
			fetosenseDataOutside.setFetosenseMotherID(fetosenseDataOutside.getMother().get("cmMotherId"));
			fetosenseDataOutside.setFetosensePartnerID(fetosenseDataOutside.getMother().get("partnerId"));
			fetosenseDataOutside.setPartnerName(fetosenseDataOutside.getMother().get("partnerName"));

			// fetching data from the db
			Fetosense fetosenseFetchDataDB = fetosenseRepo.getFetosenseDetails(fetosenseDataOutside.getFetosenseID());
			if (fetosenseFetchDataDB == null || fetosenseFetchDataDB.getFetosenseID() == null)
				throw new IEMRException("Invalid partnerFetosenseID");

			fetosenseDataOutside.setBeneficiaryID(fetosenseFetchDataDB.getBeneficiaryID());
			fetosenseDataOutside.setBeneficiaryRegID(fetosenseFetchDataDB.getBeneficiaryRegID());

			// setting the values from the DB response
			// fetosenseDataOutside.setBeneficiaryRegID(fetosenseFetchDataDB.getBeneficiaryRegID());
			if (fetosenseFetchDataDB.getVisitCode() != null)
				fetosenseDataOutside.setVisitCode(fetosenseFetchDataDB.getVisitCode());
			fetosenseDataOutside.setTestTime(fetosenseFetchDataDB.getTestTime());
			fetosenseDataOutside.setMotherLMPDate(fetosenseFetchDataDB.getMotherLMPDate());
			fetosenseDataOutside.setMotherName(fetosenseFetchDataDB.getMotherName());
			fetosenseDataOutside.setFetosenseTestId(fetosenseFetchDataDB.getFetosenseTestId());
			fetosenseDataOutside.setProviderServiceMapID(fetosenseFetchDataDB.getProviderServiceMapID());
			fetosenseDataOutside.setBenFlowID(fetosenseFetchDataDB.getBenFlowID());
			fetosenseDataOutside.setVanID(fetosenseFetchDataDB.getVanID());
			fetosenseDataOutside.setTestName(fetosenseFetchDataDB.getTestName());
			fetosenseDataOutside.setCreatedBy(fetosenseFetchDataDB.getCreatedBy());

			fetosenseDataOutside.setResultState(true);
			fetosenseDataOutside.setDeleted(fetosenseFetchDataDB.getDeleted());

			// need to write the code for changing the report path data to base 64 and save
			// it in DB

			String filePath = generatePDF(fetosenseDataOutside.getReportPath());
			fetosenseDataOutside.setaMRITFilePath(filePath);

			// saving the feto sense response to DB
			fetosenseDataOutside = fetosenseRepo.save(fetosenseDataOutside);

			int flagUpdate = 0;

			// updating lab technician flag to 3 from 2 as we got the response from feto
			// sense
			if (fetosenseDataOutside != null && fetosenseDataOutside.getFetosenseID() > 0) {

				// need to check how many records are there with benflowID
				short lab_technician_flag = 3;
				ArrayList<Fetosense> fetosenseDataOnBenFlowID = fetosenseRepo
						.getFetosenseDetailsByFlowId(fetosenseFetchDataDB.getBenFlowID());
				if (fetosenseDataOnBenFlowID.size() > 0) {
					// if any of the record is not updated then marking lab flag as 2 - not able to
					// open in doctor screen.
					for (Fetosense data : fetosenseDataOnBenFlowID) {
						if (data != null && !data.getResultState()) {
							lab_technician_flag = 2;
						}
					}
				}
				flagUpdate = beneficiaryFlowStatusRepo.updateLabTechnicianFlag(lab_technician_flag,
						fetosenseFetchDataDB.getBenFlowID());
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
        if (filePath.startsWith(fetosenseReportPath)) {
            URL url = new URL(filePath);

            // Check if the URL is from an allowed domain
            String host = url.getHost();
            if (!fetosenseReportPath.contains(host)) {
                throw new IllegalArgumentException("The URL is not from an allowed domain");
            }

            filePathLocal = fotesenseFilePath + "/" + timeStamp.toString() + ".pdf";

            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpGet httpGet = new HttpGet(filePath);
                HttpResponse httpResponse = httpClient.execute(httpGet);

                // Check if the request was successful (status code 200)
                if (httpResponse.getStatusLine().getStatusCode() != 200) {
                    throw new RuntimeException("Failed to download PDF. HTTP status code: " + httpResponse.getStatusLine().getStatusCode());
                }

                try (InputStream inputStream = httpResponse.getEntity().getContent();
                     FileOutputStream outputStream = new FileOutputStream(filePathLocal)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
            }
        }

    } catch (IOException e) {
        throw new RuntimeException(e.getMessage());
    }

    return filePathLocal;
}

	// generate report file in file storage
	@Override
	public String readPDFANDGetBase64(String filePath) throws IEMRException, IOException, FileNotFoundException {
//		FileInputStream file = new FileInputStream(filePath);
		byte[] byteArray = Files.readAllBytes(Paths.get(filePath));
		return Base64.getEncoder().encodeToString(byteArray);
	}

	/***
	 * sends the details to fetosense.
	 */
	@Override
	// @Transactional(rollbackFor = Exception.class)
	public String sendFetosenseTestDetails(Fetosense request, String auth) throws Exception {

		// Fetosense response = null;
		logger.info("start fetosense test request : " + request.getTestName());
		try {
			Long benID = fetosenseRepo.getBenID(request.getBeneficiaryRegID());
			request.setBeneficiaryID(benID);
			// Saving Fetosense Data in Amrit DB
			request = fetosenseRepo.save(request);

			if (request != null && request.getFetosenseID() > 0) {

				FetosenseData fetosenseTestDetails = new FetosenseData();
				fetosenseTestDetails.setPartnerFetosenseID(request.getFetosenseID());

				// send benid in place of benregid to fetosense
				fetosenseTestDetails.setBeneficiaryRegID(benID);

				String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
				SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT);

				fetosenseTestDetails.setMotherLMPDate(sdf.format(request.getMotherLMPDate()));
				fetosenseTestDetails.setMotherName(request.getMotherName());
				fetosenseTestDetails.setTestName(request.getTestName());

				// checking whether device ID is mapped or not
				FetosenseDeviceID deviceIDForVanID = fetosenseDeviceIDRepo.getDeviceIDForVanID(request.getVanID());

				if (deviceIDForVanID != null && deviceIDForVanID.getDeviceID() != null) {
					fetosenseTestDetails.setDeviceID(deviceIDForVanID.getDeviceID());
				} else
					throw new RuntimeException("Spoke is not mapped with Fetosense deviceID");

				JsonParser parser = new JsonParser();
				ResponseEntity<String> result = null;

				HashMap<String, Object> header = new HashMap<>();
				header.put("apiKey", fetosenseAPIKey);

				String requestObj = new Gson().toJson(fetosenseTestDetails).toString();

				logger.info("calling fetosense API with request OBJ : " + requestObj);
				// Invoking Fetosense API - Sending mother data and test details to fetosense
				result = httpUtils.postWithResponseEntity(
						ConfigProperties.getPropertyByName("fetosense-api-url-ANCTestDetails"), requestObj, header);
				logger.info("Fetosense register mother API response : " + result.toString());

				// check fetosense API response code
				if (result != null && Integer.parseInt(result.getStatusCode().toString()) == 200) {
					JsonObject responseObj = (JsonObject) parser.parse(result.getBody());
					String responseData = responseObj.get("message").getAsString();
					if (responseData != null) {
						logger.info("end fetosense test request: " + request.getTestName());
						return "Patient details sent to fetosense device successfully. Please select patient name on device and start the test";
					} else {
						throw new RuntimeException("fetosense register mother API is giving response as null");
					}
				} else {
					throw new RuntimeException(
							"Error while registering mother in fetosense, fetosense response status code : "
									+ Integer.parseInt(result.getStatusCode().toString()));
				}

			} else
				throw new RuntimeException("Unable to generate fetosense id in TM");

		} 
		/**
		 * @author SH20094090
		 * @purpose To get response body in case of exception
		 */
		catch(HttpClientErrorException e)
		{
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(e.getResponseBodyAsString());
			jsnOBJ = jsnElmnt.getAsJsonObject();
			if (request != null && request.getPartnerFetosenseId() != null && request.getPartnerFetosenseId() > 0) {
				 //String response = e.getres;
				logger.info("fetosense test request transaction roll-backed");
				request.setDeleted(true);
				fetosenseRepo.save(request);
			}
			if(jsnOBJ.get("status") !=null && jsnOBJ.get("message") !=null)
//			throw new Exception("Unable to raise test request, error is : " + ("status code "+(jsnOBJ.get("status").getAsString())
//					+","+(jsnOBJ.get("message").getAsString())));
				throw new Exception("Unable to raise test request, error is : " + (jsnOBJ.get("message").getAsString()));
			else
				throw new Exception("Unable to raise test request, error is :  " + e.getMessage());
			
		}
		catch (Exception e) {
			// if record is created, and not raised in fetosense device, soft delete it
			if (request != null && request.getPartnerFetosenseId() != null && request.getPartnerFetosenseId() > 0) {
				 //String response = e.getres;
				logger.info("fetosense test request transaction roll-backed");
				request.setDeleted(true);
				fetosenseRepo.save(request);
			}
			throw new Exception("Unable to raise test request, error is :  " + e.getMessage());
		}

	}

	@Override
	public String getFetosenseDetails(Long benFlowID) throws IEMRException {

		try {
			Map<String, Object> resMap = new HashMap<>();
			ArrayList<Fetosense> fetosenseData = fetosenseRepo.getFetosenseDetailsByFlowId(benFlowID);

			List<Fetosense> fetosenseList = new ArrayList<Fetosense>();
			for (Fetosense data : fetosenseData) {
				Fetosense listData = new Fetosense(data.getFetosenseID(), data.getBeneficiaryRegID(),
						data.getBenFlowID(), data.getVisitCode(), data.getFetosenseTestId(), data.getResultState());
				fetosenseList.add(listData);
			}
			resMap.put("benFetosenseData", fetosenseList);
			return new Gson().toJson(resMap);
		} catch (Exception e) {
			throw new IEMRException("Error in fetching fetosense details " + e.getMessage());
		}

	}

}
