package com.iemr.hwc.controller.deliveryOutCome;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iemr.hwc.data.anc.ANCVisitDTO;
import com.iemr.hwc.data.childRegistration.ChildRegisterDTO;
import com.iemr.hwc.data.deliveryOutcome.DeliveryOutcomeDTO;
import com.iemr.hwc.data.infantRegistration.InfantRegisterDTO;
import com.iemr.hwc.data.pregnantWomen.PregnantWomanDTO;
import com.iemr.hwc.data.requestDTO.GetBenRequestHandler;
import com.iemr.hwc.service.childRegistration.ChildService;
import com.iemr.hwc.service.deliveryOutCome.DeliveryOutcomeService;
import com.iemr.hwc.service.infantRegistraion.InfantService;
import com.iemr.hwc.service.maternalHelathService.MaternalHealthService;
import com.iemr.hwc.utils.JwtUtil;
import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.response.OutputResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
@RestController
@RequestMapping(value = "/deliveryOutcome", consumes = "application/json", produces = "application/json")
public class DeliveryOutComeController {

    private final Logger logger = LoggerFactory.getLogger(DeliveryOutComeController.class);


    @Autowired
    private DeliveryOutcomeService deliveryOutcomeService;

    @Autowired
    private MaternalHealthService maternalHealthService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private InfantService infantService;

    @Autowired
    private ChildService childService;


    @Operation(summary = "save Delivery Outcome details")
    @RequestMapping(value = { "/saveAll" }, method = { RequestMethod.POST })
    public String saveDeliveryOutcome(@RequestBody List<DeliveryOutcomeDTO> deliveryOutcomeDTOS,
                                      @RequestHeader(value = "Authorization") String Authorization, HttpServletRequest request) throws IEMRException {


        OutputResponse response = new OutputResponse();
        try {
            if (deliveryOutcomeDTOS.size() != 0) {
                logger.info("Saving delivery outcomes with timestamp : " + new Timestamp(System.currentTimeMillis()));
                String s = deliveryOutcomeService.registerDeliveryOutcome(deliveryOutcomeDTOS);
                if (s != null)
                    response.setResponse(s);
                else
                    response.setError(5000, "Saving delivery outcome to db failed");
            } else
                response.setError(5000, "Invalid/NULL request obj");
        } catch (Exception e) {
            logger.error("Error in save delivery outcome details : " + e);
            response.setError(5000, "Error in save delivery outcome details : " + e);
        }
        return response.toString();

    }

    @Operation(summary = "get Delivery Outcome details")
    @RequestMapping(value = { "/getAll" }, method = { RequestMethod.POST })
    public String getDeliveryOutcome(@RequestHeader(value = "jwttoken") String jwttoken) {
        OutputResponse response = new OutputResponse();
        try {
            if (jwttoken != null) {

                List<DeliveryOutcomeDTO> result = deliveryOutcomeService.getDeliveryOutcome(jwtUtil.extractUsername(jwttoken));
                Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy h:mm:ss a").create();
                String s = gson.toJson(result);
                if (result != null && !result.isEmpty()) {
                    response.setResponse(gson.toJson(result));
                }else
                    response.setError(5000, "No record found");
            } else
                response.setError(5000, "Invalid/NULL request obj");
        } catch (Exception e) {
            logger.error("Error in delivery outcomes get data : " + e);
            response.setError(5000, "Error in delivery outcomes get data : " + e);
        }
        return response.toString();
    }
}
