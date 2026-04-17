package com.iemr.hwc.controller.noseDiagnosis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iemr.hwc.data.noiseDiagnosis.NoseDiagnosisAssessmentDTO;
import com.iemr.hwc.service.noiseDignosisAssessgment.NoseDiagnosisAssessmentService;
import com.iemr.hwc.utils.JwtUtil;
import com.iemr.hwc.utils.response.OutputResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/nose", headers = "Authorization", consumes = "application/json", produces = "application/json")
public class NoseDiagnosisAssessmentController {

    @Autowired
    private NoseDiagnosisAssessmentService service;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/saveAll")
    public String saveAll(@RequestBody List<NoseDiagnosisAssessmentDTO> dtos,
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
            response.setError(5000, "Error in save: " + e.getMessage());
        }

        return response.toString();
    }

    @PostMapping("/getAll")
    public String getAll(@RequestHeader("jwttoken") String token) {

        OutputResponse response = new OutputResponse();

        try {
            String user = jwtUtil.extractUsername(token);

            List<NoseDiagnosisAssessmentDTO> list = service.getAll(user);

            Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy h:mm:ss a").create();
            response.setResponse(gson.toJson(list));

        } catch (Exception e) {
            response.setError(5000, "Error in get: " + e.getMessage());
        }

        return response.toString();
    }
}