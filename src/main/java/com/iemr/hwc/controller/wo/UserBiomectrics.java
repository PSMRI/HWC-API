package com.iemr.hwc.controller.wo;

import com.google.gson.Gson;
import com.iemr.hwc.controller.common.master.CommonMasterController;
import com.iemr.hwc.data.registrar.FingerPrintDTO;
import com.iemr.hwc.data.registrar.UserBiometricsMapping;
import com.iemr.hwc.service.location.LocationServiceImpl;
import com.iemr.hwc.service.registrar.RegistrarServiceImpl;
import com.iemr.hwc.utils.response.OutputResponse;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "wo/user_biometrics")
public class UserBiomectrics {
    //    private OutputResponse response;
    private Logger logger = LoggerFactory.getLogger(CommonMasterController.class);

    @Autowired
    private RegistrarServiceImpl registrarService;
    @CrossOrigin()
    @ApiOperation(value = "add fingerprint for a given username", consumes = "application/json", produces = "application/json")
    @RequestMapping(value = "add/fingerprint/wo", method = { RequestMethod.POST }, produces = {
            "application/json" })
    public String addFingerPrints(@RequestBody FingerPrintDTO comingRequest) {
        OutputResponse response = new OutputResponse();
        try {
            if (comingRequest != null && comingRequest.getUserName() != null) {
                String resp = registrarService.saveFingerprints(comingRequest);
                if(resp !=null && resp.equals("ok")){
                    response.setResponse(resp);
                }
                else if(resp !=null && resp.equals("ko")){
                    response.setError(500, "Error adding fingerprints");
                }
            } else {
                response.setError(400, "Invalid request");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            response.setError(500, "Error while adding fingerprints data");
        }
        return response.toString();
    }

    @ApiOperation(value = "Get fingerprint by username", consumes = "application/json", produces = "application/json")
    @RequestMapping(value = "/get/fingerprint/{userID}/wo", method = RequestMethod.GET)
    public String getFingerprintsByUsername(@PathVariable("userID") Long userID) {
        logger.info("Get fingerprint by username ..." + userID);
        OutputResponse response = new OutputResponse();
        UserBiometricsMapping user = registrarService.getFingerprintsByUserID(userID);
        if (user != null){
            Gson gson = new Gson();
            UserBiometricsMapping userBiometricsMapping = new UserBiometricsMapping();
            userBiometricsMapping.setUserID(user.getUserID());
            userBiometricsMapping.setFirstName(user.getFirstName());
            userBiometricsMapping.setLastName(user.getLastName());
            userBiometricsMapping.setUserName(user.getUserName());
            userBiometricsMapping.setCreatedBy(user.getUserName());
            userBiometricsMapping.setRightThumb(user.getRightThumb());
            userBiometricsMapping.setRightIndexFinger(user.getRightIndexFinger());
            userBiometricsMapping.setLeftThumb(user.getLeftThumb());
            userBiometricsMapping.setLeftIndexFinger(user.getLeftIndexFinger());

            response.setResponse(gson.toJson(userBiometricsMapping));
        }
        else{
            response.setError(404, "User with userID: "+userID+" not found");
        }
        logger.info("Get fingerprint " + response.toString());
        return response.toString();
    }
}