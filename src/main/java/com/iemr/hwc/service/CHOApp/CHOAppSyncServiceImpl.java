package com.iemr.hwc.service.CHOApp;

import com.google.gson.*;
import com.iemr.hwc.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.utils.request.SyncSearchRequest;
import com.iemr.hwc.utils.response.OutputResponse;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
import java.util.ArrayList;

@Service
@PropertySource("classpath:application.properties")
public class CHOAppSyncServiceImpl implements CHOAppSyncService {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Value("${registrationUrl}")
    private String registrationUrl;

    @Value("${syncSearchByLocation}")
    private String syncSearchByLocation;

    private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;

    private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;

    @Autowired
    public void setCommonBenStatusFlowServiceImpl(CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl) {
        this.commonBenStatusFlowServiceImpl = commonBenStatusFlowServiceImpl;
    }

    @Autowired
    public void setBeneficiaryFlowStatusRepo(BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo) {
        this.beneficiaryFlowStatusRepo = beneficiaryFlowStatusRepo;
    }

    public ResponseEntity<String> registerCHOAPPBeneficiary(String comingRequest, String Authorization){

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
                            status = HttpStatus.OK;
                        }
                    } else {
                        logger.error("Couldn't create a new benFlowStatus record for the registered beneficiary");
                        responseObj.addProperty("error", "Beneficiary creation successful but couldn't create new flow status for it.");
                        status = HttpStatus.INTERNAL_SERVER_ERROR;
                    }

                } else {
                    logger.error("Error encountered in Common-API service while registering beneficiary. "
                            + registrationResponseObj.getString("status"));
                    responseObj.addProperty("error", "Error encountered in Common-API service while registering beneficiary. "
                            + registrationResponseObj.getString("status"));
                }

        } catch(ResourceAccessException e){
            logger.error("Error establishing connection with Common-API service. " + e);
            responseObj.addProperty("error", "Error establishing connection with Common-API service. ");
            status = HttpStatus.SERVICE_UNAVAILABLE;
        } catch(RestClientResponseException e){
            logger.error("Error encountered in Common-API service while registering beneficiary. " + e);
            responseObj.addProperty("error", "Error encountered in Common-API service while registering beneficiary. " + e);
            status = HttpStatus.valueOf(e.getRawStatusCode());
        } catch (JSONException e){
            logger.error("Encountered JSON exception " + e);
            responseObj.addProperty("error", "Error registering the beneficiary.Encountered JSON exception " + e);
            status = HttpStatus.BAD_GATEWAY;
        } catch (Exception e){
            logger.error("Encountered exception " + e);
            responseObj.addProperty("error", "Error registering the beneficiary.Encountered exception " + e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<> (responseObj.toString(),headers,status);
    }

    public ResponseEntity<String> getBeneficiaryByVillageIDAndLastModifiedDate(SyncSearchRequest villageIDAndLastSyncDate, String Authorization) {

        OutputResponse outputResponse = new OutputResponse();
        HttpStatus statusCode = HttpStatus.OK;

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("AUTHORIZATION", Authorization);

        RestTemplate restTemplate = new RestTemplate();

        try {

            if(villageIDAndLastSyncDate.getVillageID() !=null && villageIDAndLastSyncDate.getLastSyncDate() != null) {

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
                logger.error("Unable to search beneficiaries to sync based on villageID and lastSyncDate. Incomplete request body - Either villageID or lastSyncDate missing.");
                outputResponse.setError(400,"Bad request. Incomplete request body - Either villageID or lastSyncDate missing.");
                statusCode = HttpStatus.BAD_REQUEST;
            }
        }
        catch(ResourceAccessException e){
            logger.error("Error establishing connection with Identity service. " + e);
            outputResponse.setError(503, "Error establishing connection with Identity service. ");
            statusCode = HttpStatus.SERVICE_UNAVAILABLE;
        } catch(RestClientResponseException e){
            logger.error("Error encountered in Identity service while searching beneficiary based on villageID and lastSyncDate " + e);
            outputResponse.setError(e.getRawStatusCode(), "Error encountered in Identity service while searching beneficiary based on villageID and lastSyncDate " + e);
            statusCode = HttpStatus.valueOf(e.getRawStatusCode());
        } catch (JSONException e){
            logger.error("Encountered JSON exception while parsing response from Identity service " + e);
            outputResponse.setError(400, "Encountered JSON exception while parsing response from Identity service " + e);
            statusCode = HttpStatus.BAD_GATEWAY;
        } catch (Exception e){
            logger.error("Encountered exception " + e);
            outputResponse.setError(500, "Error searching beneficiaries to sync. Exception " + e);
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(outputResponse.toString(),headers,statusCode);

    }

    @Override
    public ResponseEntity<String> getFlowRecordsByVillageIDAndLastModifiedDate(SyncSearchRequest villageIDAndLastSyncDate, String Authorization) {

        HttpStatus statusCode = HttpStatus.OK;
        OutputResponse response = new OutputResponse();
        ArrayList<BeneficiaryFlowStatus> benFlowList;

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("AUTHORIZATION", Authorization);

        try {
            if (villageIDAndLastSyncDate.getVillageID() !=null && villageIDAndLastSyncDate.getLastSyncDate() != null) {
                DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
                DateTime dt = formatter.parseDateTime(villageIDAndLastSyncDate.getLastSyncDate());

                benFlowList = beneficiaryFlowStatusRepo.getFlowRecordsToSync(villageIDAndLastSyncDate.getVillageID(),
                        new Timestamp(dt.toDate().getTime()));
                response.setResponse(new Gson().toJson(benFlowList));
            }else{
                logger.error("Unable to search beneficiaries to sync based on villageID and lastSyncDate. Incomplete request body - Either villageID or lastSyncDate missing.");
                response.setError(400,"Bad request. Incomplete request body - Either villageID or lastSyncDate missing.");
                statusCode = HttpStatus.BAD_REQUEST;
            }
        } catch (IllegalArgumentException e){
            logger.error("Encountered exception. " + e);
            response.setError(400, "Encountered exception. Exception " + e);
            statusCode = HttpStatus.BAD_REQUEST;
        } catch (Exception e){
            logger.error("Encountered exception while fetching ben flow status records to sync " + e);
            response.setError(500, "Error fetching ben flow status records to sync . Exception " + e);
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(response.toString(),headers,statusCode);

    }
}
