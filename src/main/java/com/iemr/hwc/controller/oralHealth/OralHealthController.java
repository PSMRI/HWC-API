package com.iemr.hwc.controller.oralHealth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iemr.hwc.data.oralHealth.OralHealthDTO;
import com.iemr.hwc.data.oralHealth.OralHealthData;
import com.iemr.hwc.service.oralHealth.OralHealthService;
import com.iemr.hwc.utils.JwtUtil;
import com.iemr.hwc.utils.response.OutputResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/oralHealth", headers = "Authorization", consumes = "application/json", produces = "application/json")
public class OralHealthController {

    @Autowired
    private OralHealthService service;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/saveAll")
    public ResponseEntity<Map<String, Object>> saveAll(
            @RequestBody List<OralHealthDTO> dtos,
            @RequestHeader("jwtToken") String token) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (dtos != null && !dtos.isEmpty()) {

                Integer  userId = jwtUtil.extractUserId(token);
                String  userName = jwtUtil.extractUsername(token);

                List<OralHealthData> saved = service.saveAll(dtos, userId,userName);

                response.put("status", "Success");
                response.put("statusCode", 200);
                response.put("data", saved);

            } else {
                response.put("statusCode", 5000);
                response.put("message", "Invalid request");
            }

        } catch (Exception e) {
            response.put("statusCode", 5000);
            response.put("message", "Error: " + e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/getAll")
    public ResponseEntity<Map<String, Object>> getAll(
            @RequestHeader("jwtToken") String token) {

        Map<String, Object> response = new HashMap<>();

        try {
            Integer userId = jwtUtil.extractUserId(token);

            List<OralHealthDTO> list = service.getAll(userId);

            if (!list.isEmpty()) {
                response.put("status", "Success");
                response.put("statusCode", 200);
                response.put("data", list);
            } else {
                response.put("statusCode", 5000);
                response.put("message", "No records found");
            }

        } catch (Exception e) {
            response.put("statusCode", 5000);
            response.put("message", "Error: " + e.getMessage());
        }

        return ResponseEntity.ok(response);
    }
}