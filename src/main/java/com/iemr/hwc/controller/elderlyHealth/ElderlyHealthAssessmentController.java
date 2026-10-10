package com.iemr.hwc.controller.elderlyHealth;

import com.iemr.hwc.data.elderlyHealth.ElderlyHealthAssessmentDTO;
import com.iemr.hwc.service.elderlyHealth.ElderlyHealthAssessmentService;
import com.iemr.hwc.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/elderlyHealth")
public class ElderlyHealthAssessmentController {

    @Autowired
    private ElderlyHealthAssessmentService service;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/saveAll")
    public ResponseEntity<Map<String, Object>> saveAll(
            @RequestBody List<ElderlyHealthAssessmentDTO> dtos,
            @RequestHeader("jwtToken") String token) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (dtos != null && !dtos.isEmpty()) {
                Integer userId = jwtUtil.extractUserId(token);
                service.saveAll(dtos, userId);

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
                List<ElderlyHealthAssessmentDTO> res = service.getAll(userId);

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
