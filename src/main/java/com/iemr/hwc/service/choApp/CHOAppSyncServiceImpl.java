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
package com.iemr.hwc.service.choApp;

import com.google.gson.*;
import com.iemr.hwc.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.hwc.data.choApp.OutreachActivity;
import com.iemr.hwc.data.choApp.UserActivityLogs;
import com.iemr.hwc.data.doctor.PrescriptionTemplates;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.quickConsultation.BenChiefComplaint;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.choApp.OutreachActivityRepo;
import com.iemr.hwc.repo.choApp.UserActivityLogsRepo;
import com.iemr.hwc.repo.doctor.PrescriptionTemplatesRepo;
import com.iemr.hwc.repo.nurse.BenAnthropometryRepo;
import com.iemr.hwc.repo.nurse.BenPhysicalVitalRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.generalOPD.GeneralOPDServiceImpl;
import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.request.SyncSearchRequest;
import com.iemr.hwc.utils.response.OutputResponse;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.*;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;
import java.util.*;

@Service
@PropertySource("classpath:application.properties")
public class CHOAppSyncServiceImpl implements CHOAppSyncService {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Value("${registrationUrl}")
    private String registrationUrl;

    @Value("${syncSearchByLocation}")
    private String syncSearchByLocation;

    @Value("${getBenCountToSync}")
    private String getBenCountToSync;

    private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;

    private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;

    private UserActivityLogsRepo userActivityLogsRepo;

    private CommonNurseServiceImpl commonNurseServiceImpl;

    private BenAnthropometryRepo benAnthropometryRepo;

    private BenPhysicalVitalRepo benPhysicalVitalRepo;

    private BenChiefComplaintRepo benChiefComplaintRepo;

    private GeneralOPDServiceImpl generalOPDServiceImpl;

    private BenVisitDetailRepo benVisitDetailRepo;

    private PrescriptionTemplatesRepo prescriptionTemplatesRepo;

    private OutreachActivityRepo outreachActivityRepo;

    @Autowired
    public void setOutreachActivityRepo(OutreachActivityRepo outreachActivityRepo){
        this.outreachActivityRepo = outreachActivityRepo;
    }

    @Autowired
    public void setPrescriptionTemplatesRepo(PrescriptionTemplatesRepo prescriptionTemplatesRepo){
        this.prescriptionTemplatesRepo = prescriptionTemplatesRepo;
    }

    @Autowired
    public void setCommonBenStatusFlowServiceImpl(CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl) {
        this.commonBenStatusFlowServiceImpl = commonBenStatusFlowServiceImpl;
    }

    @Autowired
    public void setBeneficiaryFlowStatusRepo(BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo) {
        this.beneficiaryFlowStatusRepo = beneficiaryFlowStatusRepo;
    }

    @Autowired
    public void setUserActivityLogsRepo(UserActivityLogsRepo userActivityLogsRepo){
        this.userActivityLogsRepo = userActivityLogsRepo;
    }

    @Autowired
    public void setCommonNurseServiceImpl(CommonNurseServiceImpl commonNurseServiceImpl){
        this.commonNurseServiceImpl = commonNurseServiceImpl;
    }

    @Autowired
    public void setBenAnthropometryRepo(BenAnthropometryRepo benAnthropometryRepo){
        this.benAnthropometryRepo = benAnthropometryRepo;
    }

    @Autowired
    public void setBenPhysicalVitalRepo(BenPhysicalVitalRepo benPhysicalVitalRepo){
        this.benPhysicalVitalRepo = benPhysicalVitalRepo;
    }

    @Autowired
    public void setBenChiefComplaintRepo(BenChiefComplaintRepo benChiefComplaintRepo){
        this.benChiefComplaintRepo = benChiefComplaintRepo;
    }

    @Autowired
    public void setGeneralOPDServiceImpl(GeneralOPDServiceImpl generalOPDServiceImpl){
        this.generalOPDServiceImpl = generalOPDServiceImpl;
    }

    @Autowired
    public void setBenVisitDetailRepo(BenVisitDetailRepo benVisitDetailRepo){
        this.benVisitDetailRepo = benVisitDetailRepo;
    }

    public ResponseEntity<String> registerCHOAPPBeneficiary(String comingRequest, String Authorization){

        OutputResponse outputResponse = new OutputResponse();
        JsonObject responseObj = new JsonObject();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Long beneficiaryRegID = null;
        Long beneficiaryID = null;
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Content-Type", MediaType.APPLICATION_JSON + ";charset=utf-8");
        headers.add("AUTHORIZATION", Authorization);

        HttpEntity<Object> registrationRequest = new HttpEntity<Object>(comingRequest, headers);

        try {
            ResponseEntity<String> registrationResponse = restTemplate.exchange(registrationUrl, HttpMethod.POST, registrationRequest,
                    String.class);

                String registrationResponseStr = registrationResponse.getBody();
                JSONObject registrationResponseObj = new JSONObject(registrationResponseStr);

                if (registrationResponseObj.getInt("statusCode") == 200) {

                    beneficiaryRegID = registrationResponseObj.getJSONObject("data").getLong("beneficiaryRegID");
                    beneficiaryID = registrationResponseObj.getJSONObject("data").getLong("beneficiaryID");

                    int i = commonBenStatusFlowServiceImpl.createBenFlowRecord(comingRequest, beneficiaryRegID, beneficiaryID);

                    if (i > 0) {
                        if (i == 1) {
                            responseObj.addProperty("beneficiaryID", beneficiaryID);
                            responseObj.addProperty("beneficiaryRegID", beneficiaryRegID);
                            outputResponse.setResponse(responseObj.toString());
                            status = HttpStatus.OK;
                        }
                    } else {
                        logger.error("Couldn't create a new benFlowStatus record for the registered beneficiary");
                        outputResponse.setError(500, "Beneficiary creation successful but couldn't create new flow status for it.");
                        status = HttpStatus.INTERNAL_SERVER_ERROR;
                    }

                } else {
                    logger.error("Error encountered in Common-API service while registering beneficiary. "
                            + registrationResponseObj.getString("status"));
                    outputResponse.setError(registrationResponseObj.getInt("statusCode"), "Error encountered in Common-API service while registering beneficiary. "
                            + registrationResponseObj.getString("status"));
                }

        } catch(ResourceAccessException e){
            logger.error("Error establishing connection with Common-API service. " + e);
            outputResponse.setError(503, "Error establishing connection with Common-API service. ");
            status = HttpStatus.SERVICE_UNAVAILABLE;
        } catch(RestClientResponseException e){
            logger.error("Error encountered in Common-API service while registering beneficiary. " + e);
            outputResponse.setError(e.getRawStatusCode(), "Error encountered in Common-API service while registering beneficiary. " + e);
            status = HttpStatus.valueOf(e.getRawStatusCode());
        } catch (JSONException e){
            logger.error("Encountered JSON exception " + e);
            outputResponse.setError(502, "Error registering the beneficiary.Encountered JSON exception " + e);
            status = HttpStatus.BAD_GATEWAY;
        } catch (Exception e){
            logger.error("Encountered exception " + e);
            outputResponse.setError(500, "Error registering the beneficiary.Encountered exception " + e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        headers.remove("AUTHORIZATION");

        return new ResponseEntity<> (outputResponse.toString(),headers,status);
    }

    public ResponseEntity<String> getBeneficiaryByVillageIDAndLastModifiedDate(SyncSearchRequest villageIDAndLastSyncDate, String Authorization) {

        OutputResponse outputResponse = new OutputResponse();
        HttpStatus statusCode = HttpStatus.OK;

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("AUTHORIZATION", Authorization);

        RestTemplate restTemplate = new RestTemplate();

        try {

            if(villageIDAndLastSyncDate.getVillageID() !=null && !villageIDAndLastSyncDate.getVillageID().isEmpty()
                    && villageIDAndLastSyncDate.getLastSyncDate() != null) {

                DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
                DateTime dt = formatter.parseDateTime(villageIDAndLastSyncDate.getLastSyncDate());

                villageIDAndLastSyncDate.setLastModifiedDate(dt.toDate().getTime());

                String identityRequestString = new GsonBuilder().create().toJson(villageIDAndLastSyncDate);
                HttpEntity<String> request = new HttpEntity<>(identityRequestString, headers);
                ResponseEntity<String> response = restTemplate.exchange(syncSearchByLocation, HttpMethod.POST, request,
                        String.class);

                if (response.hasBody()) {

                    JSONObject responseJSON = new JSONObject(response.getBody());
                    JSONArray jsonArray = new JSONArray(responseJSON.getJSONObject("response").getString("data"));

                    outputResponse.setResponse(jsonArray.toString());
                }
            }else{
                logger.error("Unable to search beneficiaries to sync based on villageIDs and lastSyncDate. Incomplete request body - Either villageIDs or lastSyncDate missing.");
                outputResponse.setError(400,"Bad request. Incomplete request body - Either villageIDs or lastSyncDate missing.");
                statusCode = HttpStatus.BAD_REQUEST;
            }
        }
        catch(ResourceAccessException e){
            logger.error("Error establishing connection with Identity service. " + e);
            outputResponse.setError(503, "Error establishing connection with Identity service. ");
            statusCode = HttpStatus.SERVICE_UNAVAILABLE;
        } catch(RestClientResponseException e){
            logger.error("Error encountered in Identity service while searching beneficiary based on villageIDs and lastSyncDate " + e);
            outputResponse.setError(e.getRawStatusCode(), "Error encountered in Identity service while searching beneficiary based on villageIDs and lastSyncDate " + e);
            statusCode = HttpStatus.valueOf(e.getRawStatusCode());
        } catch (JSONException e){
            logger.error("Encountered JSON exception while parsing response from Identity service " + e);
            outputResponse.setError(502, "Encountered JSON exception while parsing response from Identity service " + e);
            statusCode = HttpStatus.BAD_GATEWAY;
        } catch (Exception e){
            logger.error("Encountered exception " + e);
            outputResponse.setError(500, "Error searching beneficiaries to sync. Exception " + e);
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        headers.remove("AUTHORIZATION");

        return new ResponseEntity<>(outputResponse.toStringWithSerializeNulls(),headers,statusCode);

    }

    public ResponseEntity<String> countBeneficiaryByVillageIDAndLastModifiedDate(SyncSearchRequest villageIDAndLastSyncDate, String Authorization) {

        OutputResponse outputResponse = new OutputResponse();
        HttpStatus statusCode = HttpStatus.OK;

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("AUTHORIZATION", Authorization);

        RestTemplate restTemplate = new RestTemplate();

        try {

            if(villageIDAndLastSyncDate.getVillageID() !=null && !villageIDAndLastSyncDate.getVillageID().isEmpty()
                    && villageIDAndLastSyncDate.getLastSyncDate() != null) {

                DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
                DateTime dt = formatter.parseDateTime(villageIDAndLastSyncDate.getLastSyncDate());

                villageIDAndLastSyncDate.setLastModifiedDate(dt.toDate().getTime());

                String identityRequestString = new GsonBuilder().create().toJson(villageIDAndLastSyncDate);
                HttpEntity<String> request = new HttpEntity<>(identityRequestString, headers);
                ResponseEntity<String> response = restTemplate.exchange(getBenCountToSync, HttpMethod.POST, request,
                        String.class);

                if (response.hasBody()) {

                    JSONObject responseJSON = new JSONObject(response.getBody());
                    String count = responseJSON.getJSONObject("response").getString("data");

                    outputResponse.setResponse(count);
                }
            }else{
                logger.error("Unable to get count of beneficiaries to sync based on villageIDs and lastSyncDate. Incomplete request body - Either villageIDs or lastSyncDate missing.");
                outputResponse.setError(400,"Bad request. Incomplete request body - Either villageIDs or lastSyncDate missing.");
                statusCode = HttpStatus.BAD_REQUEST;
            }
        }
        catch(ResourceAccessException e){
            logger.error("Error establishing connection with Identity service. " + e);
            outputResponse.setError(503, "Error establishing connection with Identity service. ");
            statusCode = HttpStatus.SERVICE_UNAVAILABLE;
        } catch(RestClientResponseException e){
            logger.error("Error encountered in Identity service while getting count of beneficiary based on villageIDs and lastSyncDate " + e);
            outputResponse.setError(e.getRawStatusCode(), "Error encountered in Identity service while getting count of beneficiary based on villageIDs and lastSyncDate " + e);
            statusCode = HttpStatus.valueOf(e.getRawStatusCode());
        } catch (JSONException e){
            logger.error("Encountered JSON exception while parsing response from Identity service " + e);
            outputResponse.setError(502, "Encountered JSON exception while parsing response from Identity service " + e);
            statusCode = HttpStatus.BAD_GATEWAY;
        } catch (Exception e){
            logger.error("Encountered exception " + e);
            outputResponse.setError(500, "Error getting count of beneficiaries to sync. Exception " + e);
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        headers.remove("AUTHORIZATION");

        return new ResponseEntity<>(outputResponse.toStringWithSerializeNulls(),headers,statusCode);

    }

    @Override
    public ResponseEntity<String> countFlowRecordsByVillageIDAndLastModifiedDate(SyncSearchRequest villageIDAndLastSyncDate, String Authorization) {

        HttpStatus statusCode = HttpStatus.OK;
        OutputResponse outputResponse = new OutputResponse();
        Long benFlowCount;

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");

        try {
            if (villageIDAndLastSyncDate.getVillageID() !=null && !villageIDAndLastSyncDate.getVillageID().isEmpty()
                    && villageIDAndLastSyncDate.getLastSyncDate() != null) {
                DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
                DateTime dt = formatter.parseDateTime(villageIDAndLastSyncDate.getLastSyncDate());

                benFlowCount = beneficiaryFlowStatusRepo.getFlowRecordsCount(villageIDAndLastSyncDate.getVillageID(),
                        new Timestamp(dt.toDate().getTime()));
                outputResponse.setResponse(String.valueOf(benFlowCount));
            }else{
                logger.error("Unable to search beneficiaries to sync based on villageIDs and lastSyncDate. Incomplete request body - Either villageIDs or lastSyncDate missing.");
                outputResponse.setError(400,"Bad request. Incomplete request body - Either villageIDs or lastSyncDate missing.");
                statusCode = HttpStatus.BAD_REQUEST;
            }
        } catch (IllegalArgumentException e){
            logger.error("Encountered exception. " + e);
            outputResponse.setError(400, "Encountered exception. Exception " + e);
            statusCode = HttpStatus.BAD_REQUEST;
        } catch (Exception e){
            logger.error("Encountered exception while fetching ben flow status records to sync " + e);
            outputResponse.setError(500, "Error fetching ben flow status records to sync . Exception " + e);
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(outputResponse.toStringWithSerializeNulls(),headers,statusCode);

    }

    @Override
    public ResponseEntity<String> getFlowRecordsByVillageIDAndLastModifiedDate(SyncSearchRequest villageIDAndLastSyncDate, String Authorization) {

        HttpStatus statusCode = HttpStatus.OK;
        OutputResponse outputResponse = new OutputResponse();
        ArrayList<BeneficiaryFlowStatus> benFlowList;

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");

        try {
            if (villageIDAndLastSyncDate.getVillageID() !=null && !villageIDAndLastSyncDate.getVillageID().isEmpty()
                    && villageIDAndLastSyncDate.getLastSyncDate() != null) {
                DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
                DateTime dt = formatter.parseDateTime(villageIDAndLastSyncDate.getLastSyncDate());

                benFlowList = beneficiaryFlowStatusRepo.getFlowRecordsToSync(villageIDAndLastSyncDate.getVillageID(),
                        new Timestamp(dt.toDate().getTime()));
                outputResponse.setResponse(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create().toJson(benFlowList));
            }else{
                logger.error("Unable to search beneficiaries to sync based on villageIDs and lastSyncDate. Incomplete request body - Either villageIDs or lastSyncDate missing.");
                outputResponse.setError(400,"Bad request. Incomplete request body - Either villageIDs or lastSyncDate missing.");
                statusCode = HttpStatus.BAD_REQUEST;
            }
        } catch (IllegalArgumentException e){
            logger.error("Encountered exception. " + e);
            outputResponse.setError(400, "Encountered exception. Exception " + e);
            statusCode = HttpStatus.BAD_REQUEST;
        } catch (Exception e){
            logger.error("Encountered exception while fetching ben flow status records to sync " + e);
            outputResponse.setError(500, "Error fetching ben flow status records to sync . Exception " + e);
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(outputResponse.toStringWithSerializeNulls(),headers,statusCode);

    }

    @Override
    public ResponseEntity<String> saveUserActivityLogs(List<UserActivityLogs> logsList, String authorization) {

        OutputResponse outputResponse = new OutputResponse();
        HttpStatus statusCode = HttpStatus.OK;

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");

        try{
            for (UserActivityLogs log : logsList){
                if(log.getUserImage() != null) {
                    byte[] imageByte = Base64.getDecoder().decode(log.getUserImage());
                    log.setImageData(imageByte);
                }
            }
        List<UserActivityLogs> savedList = (List<UserActivityLogs>) userActivityLogsRepo.save(logsList);

        if (savedList.size() == logsList.size()){
            outputResponse.setResponse("Data saved successfully");
        }else {
            throw new Exception();
        }

        } catch (IEMRException | DataIntegrityViolationException |
                JsonSyntaxException | NumberFormatException e){
            logger.error("Encountered exception EITHER due to incorrect payload syntax OR" +
                    " because of missing userId. " + e);
            outputResponse.setError(400,"Encountered exception EITHER due to incorrect payload syntax OR " +
                    "because of missing userId. Please check the payload. " + e);
            statusCode = HttpStatus.BAD_REQUEST;
        } catch (Exception e){
            logger.error("Encountered exception while saving UserActivityLogs. " + e);
            outputResponse.setError(500,"Encountered exception while saving UserActivityLogs. " + e);
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(outputResponse.toString(),headers,statusCode);
    }

    @Override
    public ResponseEntity<String> getBeneficiaryNurseFormDataGeneralOPD(String comingRequest, String authorization) {

        OutputResponse outputResponse = new OutputResponse();
        HttpStatus statusCode = HttpStatus.OK;

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");

        try{
            JSONObject obj = new JSONObject(comingRequest);
            Long benRegID = obj.getLong("benRegID");
            Long visitCode = obj.getLong("visitCode");

            Map<String, Object> response = new HashMap<>();

            Gson gson = new GsonBuilder().serializeNulls().excludeFieldsWithoutExposeAnnotation().create();

            //Fetching visitDetails, chief complaints
            response.put("GOPDNurseVisitDetail", commonNurseServiceImpl.getCSVisitDetails(benRegID, visitCode));

            ArrayList<Object[]> resList = benChiefComplaintRepo.getBenChiefComplaints(benRegID, visitCode);
            ArrayList<BenChiefComplaint> benChiefComplaints = BenChiefComplaint.getBenChiefComplaints(resList);
            response.put("BenChiefComplaints", benChiefComplaints);

            //Fetching vitals
            response.put("benAnthropometryDetail", benAnthropometryRepo.getBenAnthropometryDetail(benRegID, visitCode));
            response.put("benPhysicalVitalDetail", benPhysicalVitalRepo.getBenPhysicalVitalDetail(benRegID, visitCode));

            outputResponse.setResponse(gson.toJson(response));

        } catch (JSONException e) {
            logger.error("Encountered exception EITHER due to incorrect payload syntax OR" +
                    " because of missing benRegID/visitCode " + e);
            outputResponse.setError(400, "Encountered exception EITHER due to incorrect payload syntax OR " +
                    "because of missing benRegID/visitCode. Please check the payload. " + e);
            statusCode = HttpStatus.BAD_REQUEST;
        }catch (NullPointerException e){
            logger.error("No ben visit detail record found. Exception encountered while setting setting variable to a null object " + e);
            outputResponse.setError(404,"No ben visit detail record found. Please make sure the Ids in payload are correct. " + e);
            statusCode = HttpStatus.NOT_FOUND;
        } catch (Exception e){
            logger.error("Encountered exception while fetching nurse data(visit details,vitals,chief complaints,history,examinations)" +
                    " for beneficiary. " + e);
            outputResponse.setError(500,"Encountered exception while fetching nurse data" +
                    "(visit details,vitals,chief complaints,history,examinations) for beneficiary. " + e);
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(outputResponse.toStringWithSerializeNulls(),headers,statusCode);
    }

    @Override
    public ResponseEntity<String> saveBeneficiaryNurseFormDataGeneralOPD(String requestObj, String authorization) {

        OutputResponse outputResponse = new OutputResponse();
        HttpStatus statusCode = HttpStatus.OK;

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");

        try {

            JsonObject jsnOBJ;
            JsonParser jsnParser = new JsonParser();
            JsonElement jsonElement = jsnParser.parse(requestObj);
            jsnOBJ = jsonElement.getAsJsonObject();

            if (jsnOBJ != null) {
                String genOPDRes = generalOPDServiceImpl.saveNurseData(jsnOBJ, authorization);

                JsonObject responseObject;
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(genOPDRes);
                responseObject = element.getAsJsonObject();

                Gson gson = new Gson();

                if(responseObject.has("visitCode") && responseObject.get("visitCode")!=null){
                    BeneficiaryVisitDetail visitDetail= benVisitDetailRepo.findByVisitCode(responseObject.get("visitCode").getAsLong());
                    JsonElement ele = gson.toJsonTree(visitDetail.getBenVisitID());
                    responseObject.add("visitID",ele);
                    outputResponse.setResponse(gson.toJson(responseObject));
                }
                else {
                    outputResponse.setResponse(genOPDRes);
                }
            } else {
                logger.error("Invalid request object " + requestObj);
                outputResponse.setError(400," Bad Request. Invalid request payload");
                statusCode = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception e) {
            logger.error("Error in nurse data saving :" + e);
            outputResponse.setError(500, "Unable to save data " + e);
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(outputResponse.toString(),headers,statusCode);
    }

    @Override
    public ResponseEntity<String> savePrescriptionTemplatesToServer(List<PrescriptionTemplates> templateList, String authorization) {
        OutputResponse outputResponse = new OutputResponse();
        HttpStatus statusCode = HttpStatus.OK;

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");

        try{
            List<PrescriptionTemplates> savedList = (List<PrescriptionTemplates>) prescriptionTemplatesRepo.save(templateList);

            if (savedList.size() == templateList.size()){
                outputResponse.setResponse("Data saved successfully");
            }else {
                throw new Exception();
            }

        } catch (IEMRException | DataIntegrityViolationException |
                 JsonSyntaxException | NumberFormatException e){
            logger.error("Encountered exception EITHER due to incorrect payload syntax OR" +
                    " because of missing userId. " + e);
            outputResponse.setError(400,"Encountered exception EITHER due to incorrect payload syntax OR " +
                    "because of missing userId. Please check the payload. " + e);
            statusCode = HttpStatus.BAD_REQUEST;
        } catch (Exception e){
            logger.error("Encountered exception while saving Prescription templates. " + e);
            outputResponse.setError(500,"Encountered exception while saving Prescription templates. " + e);
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(outputResponse.toString(),headers,statusCode);
    }

    @Override
    public ResponseEntity<String> savePrescriptionTemplatesToApp(Integer userID, String authorization) {
        OutputResponse outputResponse = new OutputResponse();
        HttpStatus statusCode = HttpStatus.OK;

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");

        List<PrescriptionTemplates> templateList =  prescriptionTemplatesRepo.getPrescriptionTemplatesByUserID(userID);

        outputResponse.setResponse(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create().toJson(templateList));

        return new ResponseEntity<>(outputResponse.toStringWithSerializeNulls(),headers,statusCode);
    }

    @Override
    public ResponseEntity<String> deletePrescriptionTemplates(Integer userID, Integer tempID) {
        OutputResponse outputResponse = new OutputResponse();
        HttpStatus statusCode = HttpStatus.OK;

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");

        prescriptionTemplatesRepo.deletePrescriptionTemplatesByUserIDAndTempID(userID, tempID);

        outputResponse.setResponse("Successfully deleted");

        return new ResponseEntity<>(outputResponse.toString(),headers,statusCode);
    }


    @Override
    public ResponseEntity<String> createNewOutreachActivity(OutreachActivity activity, String authorization) {
        OutputResponse outputResponse = new OutputResponse();
        HttpStatus statusCode = HttpStatus.OK;

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");

        try{
            if(activity.getImg1() != null) {
                byte[] imageByte1 = Base64.getDecoder().decode(activity.getImg1());
                activity.setImg1Data(imageByte1);
            }

            if(activity.getImg2() != null) {
                byte[] imageByte2 = Base64.getDecoder().decode(activity.getImg2());
                activity.setImg2Data(imageByte2);
            }

            outreachActivityRepo.save(activity);

            outputResponse.setResponse("Data saved successfully");

        } catch (DataIntegrityViolationException |
                 JsonSyntaxException | NumberFormatException e){
            logger.error("Encountered exception EITHER due to incorrect payload syntax OR" +
                    " because of missing userId. " + e);
            outputResponse.setError(400,"Encountered exception EITHER due to incorrect payload syntax OR " +
                    "because of missing userId. Please check the payload. " + e);
            statusCode = HttpStatus.BAD_REQUEST;
        } catch (Exception e){
            logger.error("Encountered exception while saving outreach activity. " + e);
            outputResponse.setError(500,"Encountered exception while saving outreach activity. " + e);
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(outputResponse.toString(),headers,statusCode);
    }

    @Override
    public ResponseEntity<String> getActivitiesByUser(Integer userId, String authorization) {
        OutputResponse outputResponse = new OutputResponse();
        HttpStatus statusCode = HttpStatus.OK;

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");

        ArrayList<Object[]> activitiesObj =  outreachActivityRepo.getActivitiesByUserID(userId);

        ArrayList<OutreachActivity> activities = OutreachActivity.getActivitiesForUser(activitiesObj);

        outputResponse.setResponse(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create().toJson(activities));

        return new ResponseEntity<>(outputResponse.toStringWithSerializeNulls(),headers,statusCode);
    }

    @Override
    public ResponseEntity<String> getActivityById(Integer activityId, String authorization) {
        OutputResponse outputResponse = new OutputResponse();
        HttpStatus statusCode = HttpStatus.OK;

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");

        OutreachActivity activity =  outreachActivityRepo.findOne(activityId);

        if (activity != null && activity.getImg1Data() != null){
            String img1 = Base64.getEncoder().encodeToString(activity.getImg1Data());
            activity.setImg1(img1);
        }

        if (activity != null && activity.getImg2Data() != null){
            String img2 = Base64.getEncoder().encodeToString(activity.getImg2Data());
            activity.setImg2(img2);
        }

        outputResponse.setResponse(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create().toJson(activity));

        return new ResponseEntity<>(outputResponse.toStringWithSerializeNulls(),headers,statusCode);
    }
}
