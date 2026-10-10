package com.iemr.hwc.controller.earDiagnosisAssessment;

import com.iemr.hwc.data.earDiagnosis.EarDiagnosisAssessmentDTO;
import com.iemr.hwc.service.earDiagnosisAssessment.EarDiagnosisAssessmentService;
import com.iemr.hwc.utils.JwtUtil;
import com.iemr.hwc.utils.response.OutputResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/earDiagnosis")
public class EarDiagnosisAssessmentController {

    @Autowired
    private EarDiagnosisAssessmentService service;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "Save all ear diagnosis assessment data")
    @RequestMapping(value = {"/saveAll"}, method = {RequestMethod.POST})
    public ResponseEntity<Map<String, Object>>  saveAllEarDiagnosis(
            @RequestBody List<EarDiagnosisAssessmentDTO> dtos,
            @RequestHeader(value = "jwtToken") String token) {

        Map<String, Object> response = new HashMap<>();

        try {

            if (dtos != null && !dtos.isEmpty()) {
                if (token != null) {
                    Integer userId = jwtUtil.extractUserId(token);
                    List<EarDiagnosisAssessmentDTO> savedData = service.saveAll(dtos, userId);
                    if (savedData != null) {
                        response.put("status", "Success");
                        response.put("statusCode", 200);
                        response.put("data", savedData);

                    }


                }


            } else {
                response.put("message", "Invalid/NULL request body");
                response.put("statusCode", 5000);
            }

        } catch (Exception e) {
            response.put("message", "Error in saving ear diagnosis data : " + e);
            response.put("statusCode", 5000);
        }

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all ear diagnosis assessment data")
    @RequestMapping(value = {"/getAll"}, method = {RequestMethod.POST})
    public ResponseEntity<Map<String, Object>> getAllEarDiagnosis(
            @RequestHeader(value = "jwttoken") String jwttoken) {

        Map<String, Object> response = new HashMap<>();

        try {

            if (jwttoken != null) {

                List<EarDiagnosisAssessmentDTO> data = service.getAll(jwtUtil.extractUserId(jwttoken));
                if (!data.isEmpty()) {
                    response.put("status", "Success");
                    response.put("statusCode", 200);
                    response.put("data", data);
                } else {
                    response.put("statusCode", 5000);
                    response.put("message", "No records found");

                }


            } else {
                response.put("status", HttpStatus.UNAUTHORIZED);
                response.put("statusCode", 401);
            }

        } catch (Exception e) {
            response.put("status", "Error: " + e.getMessage());
            response.put("statusCode", 5000);
        }

        return ResponseEntity.ok(response);
    }
}