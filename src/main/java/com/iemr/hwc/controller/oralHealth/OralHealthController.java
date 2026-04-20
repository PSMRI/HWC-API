package com.iemr.hwc.controller.oralHealth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iemr.hwc.data.oralHealth.OralHealthDTO;
import com.iemr.hwc.service.oralHealth.OralHealthService;
import com.iemr.hwc.utils.JwtUtil;
import com.iemr.hwc.utils.response.OutputResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping(value = "/oralHealth", headers = "Authorization", consumes = "application/json", produces = "application/json")
public class OralHealthController {

    @Autowired
    private OralHealthService service;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "save oral health details")
    @PostMapping("/saveAll")
    public String saveAll(@RequestBody List<OralHealthDTO> dtos,
                          @RequestHeader("Authorization") String token) {

        OutputResponse response = new OutputResponse();

        try {
            if (dtos != null && !dtos.isEmpty()) {

                String user = jwtUtil.extractUsername(token);

                String res = service.saveAll(dtos, user);
                response.setResponse(res);

            } else {
                response.setError(5000, "Invalid request");
            }

        } catch (Exception e) {
            response.setError(5000, "Error in save oral health: " + e.getMessage());
        }

        return response.toString();
    }

    @Operation(summary = "get oral health details")
    @PostMapping("/getAll")
    public String getAll(@RequestHeader("jwttoken") String token) {

        OutputResponse response = new OutputResponse();

        try {
            String user = jwtUtil.extractUsername(token);

            List<OralHealthDTO> list = service.getAll(user);

            Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy h:mm:ss a").create();
            response.setResponse(gson.toJson(list));

        } catch (Exception e) {
            response.setError(5000, "Error in get oral health: " + e.getMessage());
        }

        return response.toString();
    }
}