package com.iemr.hwc.controller.painSymptom;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iemr.hwc.data.painSymptom.PainSymptomAssessmentDTO;
import com.iemr.hwc.data.painSymptom.PainSymptomData;
import com.iemr.hwc.service.painSymptomAssessment.PainSymptomAssessmentService;
import com.iemr.hwc.service.painSymptomAssessment.PainSymptomAssessmentServiceImpl;
import com.iemr.hwc.utils.JwtUtil;
import com.iemr.hwc.utils.response.OutputResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/painSymptom")
public class PainSymptomController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PainSymptomAssessmentService painSymptomAssessmentService;

    @PostMapping("/saveAll")
    public ResponseEntity<Map<String, Object>> saveAll(
            @RequestBody List<PainSymptomAssessmentDTO> request,
            @RequestHeader("jwtToken") String token) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (request != null && !request.isEmpty()) {

                painSymptomAssessmentService.saveAll(request,token);

                response.put("status", "Success");
                response.put("statusCode", 200);
                response.put("message", "Saved Successfully");

            } else {
                response.put("statusCode", 5000);
                response.put("message", "Invalid request found");
            }

        } catch (Exception e) {
            response.put("statusCode", 5000);
            response.put("message", e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/getAll")
    public ResponseEntity<Map<String, Object>> getAll(
            @RequestHeader("jwttoken") String token) {

        Map<String, Object> response = new HashMap<>();

        try {
            Integer userId = jwtUtil.extractUserId(token);

            if (userId != null) {

                List<PainSymptomAssessmentDTO> res =
                        painSymptomAssessmentService.getPain(userId);

                if (res != null && !res.isEmpty()) {
                    response.put("status", "Success");
                    response.put("statusCode", 200);
                    response.put("data", res);
                } else {
                    response.put("statusCode", 5000);
                    response.put("message", "No record found");
                }

            } else {
                response.put("statusCode", 401);
                response.put("message", "Invalid token");
            }

        } catch (Exception e) {
            response.put("statusCode", 5000);
            response.put("message", e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

}
