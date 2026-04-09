package com.iemr.hwc.controller.maternalHealth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iemr.hwc.data.anc.ANCVisitDTO;
import com.iemr.hwc.data.childRegistration.ChildRegisterDTO;
import com.iemr.hwc.data.infantRegistration.InfantRegisterDTO;
import com.iemr.hwc.data.pregnantWomen.PregnantWomanDTO;
import com.iemr.hwc.data.requestDTO.GetBenRequestHandler;
import com.iemr.hwc.service.childRegistration.ChildService;
import com.iemr.hwc.service.infantRegistraion.InfantService;
import com.iemr.hwc.service.maternalHelathService.MaternalHealthService;
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
@RequestMapping(value = "/maternal", consumes = "application/json", produces = "application/json")
public class MaternalHealthController {

    private final Logger logger = LoggerFactory.getLogger(MaternalHealthController.class);


    @Autowired
    private MaternalHealthService maternalHealthService;

    @Autowired
    private JwtUtil jwtUtil;


    @Operation(summary = "save anc visit details")
    @RequestMapping(value = { "/ancVisit/saveAll" }, method = { RequestMethod.POST })
    public String saveANCVisit(@RequestBody List<ANCVisitDTO> ancVisitDTOs,
                               @RequestHeader(value = "JwtToken") String token) {
        OutputResponse response = new OutputResponse();
        try {
            if (ancVisitDTOs.size() != 0) {
                logger.info("Saving ANC visits with timestamp : " + new Timestamp(System.currentTimeMillis()));
                if(token!=null){
                    String s = maternalHealthService.saveANCVisit(ancVisitDTOs,jwtUtil.extractUserId(token));

                    if (s != null)
                        response.setResponse(s);
                    else
                        response.setError(5000, "Saving anc data to db failed");
                }

            } else
                response.setError(5000, "Invalid/NULL request obj");
        } catch (Exception e) {
            logger.error("Error in save ANC visit details : ",e);

            response.setError(5000, "Error in save ANC visit details : " + e);
        }
        return response.toString();
    }

    @Operation(summary = "get anc visit details")
    @RequestMapping(value = { "/ancVisit/getAll" }, method = { RequestMethod.POST })
    public String getANCVisitDetails(@RequestHeader(value = "jwttoken") String jwtToken) {
        OutputResponse response = new OutputResponse();
        try {
            if (jwtToken != null) {
                List<ANCVisitDTO> result = maternalHealthService.getANCVisits(jwtUtil.extractUsername(jwtToken));
                Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy h:mm:ss a").create();
                String s = gson.toJson(result);
                if (s != null)
                    response.setResponse(s);
                else
                    response.setError(5000, "No record found");
            } else
                response.setError(5000, "Invalid/NULL request obj");
        } catch (Exception e) {
            logger.error("Error in Anc visit get data : " + e);
            response.setError(5000, "Error in Anc visit get data : " + e);
        }
        return response.toString();
    }


    @Operation(summary = "save pregnant woman registration details")
    @RequestMapping(value = { "/pregnantWoman/saveAll" }, method = { RequestMethod.POST })
    public String savePregnantWomanRegistrations(@RequestBody List<PregnantWomanDTO> pregnantWomanDTOs,
                                                 @RequestHeader(value = "Authorization") String Authorization) {
        OutputResponse response = new OutputResponse();
        try {
            if (pregnantWomanDTOs.size() != 0) {
                logger.info(
                        "Saving pregnant woman details with timestamp : " + new Timestamp(System.currentTimeMillis()));
                String s = maternalHealthService.registerPregnantWoman(pregnantWomanDTOs);
                if (s != null)
                    response.setResponse(s);
                else
                    response.setError(5000, "Saving pwr data to db failed");
            } else
                response.setError(5000, "Invalid/NULL request obj");
        } catch (Exception e) {
            logger.error("Error in save pregnant woman register details : " + e);
            response.setError(5000, "Error in save pregnant woman register details : " + e);
        }
        return response.toString();
    }

    @Operation(summary = "get List of pregnant woman registration details")
    @RequestMapping(value = { "/pregnantWoman/getAll" }, method = { RequestMethod.POST })
    public String getPregnantWomanList(@RequestHeader(value = "jwttoken") String jwtToken) {
        OutputResponse response = new OutputResponse();
        try {
            if (jwtToken != null) {

                List<PregnantWomanDTO> result = maternalHealthService.getPregnantWoman(jwtUtil.extractUsername(jwtToken));
                Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy h:mm:ss a").create();
                String s = gson.toJson(result);
                if (s != null)
                    response.setResponse(s);
                else
                    response.setError(5000, "No record found");
            } else
                response.setError(5000, "Invalid/NULL request obj");
        } catch (Exception e) {
            logger.error("Error in pregnant woman get data : " + e);
            response.setError(5000, "Error in pregnant woman get data : " + e);
        }
        return response.toString();

    }

}
