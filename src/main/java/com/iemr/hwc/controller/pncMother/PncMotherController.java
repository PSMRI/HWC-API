package com.iemr.hwc.controller.pncMother;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iemr.hwc.data.pncMother.PNCVisitDTO;
import com.iemr.hwc.service.maternalHelathService.MaternalHealthService;
import com.iemr.hwc.utils.JwtUtil;
import com.iemr.hwc.utils.response.OutputResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping(value = "/pnc", headers = "Authorization", consumes = "application/json", produces = "application/json")
public class PncMotherController {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private MaternalHealthService maternalHealthService;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "save pnc visit details")
    @RequestMapping(value = { "/saveAll" }, method = { RequestMethod.POST })
    public String savePNCVisit(@RequestBody List<PNCVisitDTO> pncVisitDTOs,
                               @RequestHeader(value = "Authorization") String Authorization) {
        OutputResponse response = new OutputResponse();
        try {
            if (pncVisitDTOs.size() != 0) {
                logger.info("Saving PNC visits with timestamp : " + new Timestamp(System.currentTimeMillis()));
                String s = maternalHealthService.savePNCVisit(pncVisitDTOs);
                if (s != null)
                    response.setResponse(s);
                else
                    response.setError(5000, "Saving pnc to db failed");
            } else
                response.setError(5000, "Invalid/NULL request obj");
        } catch (Exception e) {
            logger.error("Error in save PNC visit details : " + e);
            response.setError(5000, "Error in save PNC visit details : " + e);
        }
        return response.toString();
    }

    @Operation(summary = "get pnc visit details")
    @RequestMapping(value = { "/getAll" }, method = { RequestMethod.POST })
    public String getPNCVisitDetails(@RequestHeader(value = "jwttoken") String token) {
        OutputResponse response = new OutputResponse();
        try {
            if (token != null) {

                List<PNCVisitDTO> result = maternalHealthService.getPNCVisits(jwtUtil.extractUsername(token));
                Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy h:mm:ss a").create();
                String s = gson.toJson(result);
                if (s != null)
                    response.setResponse(s);
                else
                    response.setError(5000, "No record found");
            } else
                response.setError(5000, "Invalid/NULL request obj");
        } catch (Exception e) {
            logger.error("Error in Pnc visit get data : " + e);
            response.setError(5000, "Error in Pnc visit get data : " + e);
        }
        return response.toString();
    }
}
