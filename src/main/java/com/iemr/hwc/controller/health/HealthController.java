package com.iemr.hwc.controller.health;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.iemr.hwc.service.health.HealthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller for infrastructure health checks.
 * Provides endpoints to verify the health status of backend services and dependencies.
 */
@RestController
@RequestMapping("/health")
@Tag(name = "Health Check", description = "APIs for checking infrastructure health status")
public class HealthController {

    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);

    @Autowired
    private HealthService healthService;

    /**
     * Check the health status of all infrastructure components.
     * 
     * @return Health status map with component details
     */
    @GetMapping
    @Operation(summary = "Check infrastructure health", 
               description = "Returns the health status of MySQL, Redis, and other configured services")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "All services are healthy or partial services are down"),
        @ApiResponse(responseCode = "503", description = "Critical services are unavailable")
    })
    public ResponseEntity<Map<String, Object>> checkHealth() {
        logger.info("Health check endpoint called");
        
        try {
            Map<String, Object> healthStatus = healthService.checkHealth();
            String overallStatus = (String) healthStatus.get("status");
            
            // Return 200 if overall status is UP, 503 if DOWN
            HttpStatus httpStatus = "UP".equals(overallStatus) ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE;
            
            logger.debug("Health check completed with status: {}", overallStatus);
            return new ResponseEntity<>(healthStatus, httpStatus);
            
        } catch (Exception e) {
            logger.error("Unexpected error during health check", e);
            
            // Return sanitized error response
            Map<String, Object> errorResponse = Map.of(
                "status", "DOWN",
                "error", "Health check service unavailable",
                "timestamp", System.currentTimeMillis()
            );
            
            return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
