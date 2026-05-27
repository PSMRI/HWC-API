package com.iemr.hwc.controller.infantRegistration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iemr.hwc.data.infantRegistration.InfantRegisterDTO;
import com.iemr.hwc.data.requestDTO.GetBenRequestHandler;
import com.iemr.hwc.service.infantRegistraion.InfantService;
import com.iemr.hwc.utils.JwtUtil;
import com.iemr.hwc.utils.response.OutputResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping(value = "/infant", consumes = "application/json", produces = "application/json")
public class InfantRegistrationController {

    private final Logger logger = LoggerFactory.getLogger(InfantRegistrationController.class);


    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private InfantService infantService;




    @Operation(summary = "save Infant registration details")
    @RequestMapping(value = { "/saveAll" }, method = { RequestMethod.POST })
    public String saveInfantList(@RequestBody List<InfantRegisterDTO> infantRegisterDTOs,
                                 @RequestHeader(value = "Authorization") String Authorization) {
        OutputResponse response = new OutputResponse();
        try {
            if (infantRegisterDTOs.size() != 0) {
                logger.info("Saving infant Register with timestamp : " + new Timestamp(System.currentTimeMillis()));
                String s = infantService.registerInfant(infantRegisterDTOs);
                if (s != null)
                    response.setResponse(s);
                else
                    response.setError(5000, "Saving infant register data to db failed");
            } else
                response.setError(5000, "Invalid/NULL request obj");
        } catch (Exception e) {
            logger.error("Error in save infant register details : " + e);
            response.setError(5000, "Error in save infant register details : " + e);
        }
        return response.toString();
    }

    @Operation(summary = "get infant registration details")
    @RequestMapping(value = { "/getAll" }, method = { RequestMethod.POST })
    public String getInfantList(@RequestHeader(value = "jwttoken") String jwttoken) {
        OutputResponse response = new OutputResponse();
        try {
            if (jwttoken != null) {
                logger.info("request object with timestamp : " + new Timestamp(System.currentTimeMillis()) + " "
                        + jwttoken);
                List<InfantRegisterDTO> result = infantService.getInfantDetails(jwtUtil.extractUsername(jwttoken));
                Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy h:mm:ss a").create();
                String s = gson.toJson(result);
                if (s != null)
                    response.setResponse(s);
                else
                    response.setError(5000, "No record found");
            } else
                response.setError(5000, "Invalid/NULL request obj");
        } catch (Exception e) {
            logger.error("Error in infant register get data : " + e);
            response.setError(5000, "Error in infant register get data : " + e);
        }
        return response.toString();
    }



}
