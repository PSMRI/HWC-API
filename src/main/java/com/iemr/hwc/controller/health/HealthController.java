package com.iemr.hwc.controller.health;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.hwc.service.health.HealthService;

@RestController
public class HealthController {
    
    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);

    @Autowired
    private HealthService healthService;

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        logger.debug("Health check endpoint called");
        Map<String, Object> healthStatus = healthService.checkHealth();
        return ResponseEntity.ok(healthStatus);
    }
}