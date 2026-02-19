package com.iemr.hwc.service.health;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class HealthService {

    private static final Logger logger = LoggerFactory.getLogger(HealthService.class);
    private static final String STATUS_KEY = "status";
    private static final String DB_HEALTH_CHECK_QUERY = "SELECT 1 as health_check";
    private static final String DB_VERSION_QUERY = "SELECT VERSION()";
    private static final String STATUS_UP = "UP";
    private static final String STATUS_DOWN = "DOWN";
    private static final String UNKNOWN_VALUE = "unknown";
    private static final int REDIS_TIMEOUT_SECONDS = 3;
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    private final DataSource dataSource;
    private final RedisTemplate<String, Object> redisTemplate;
    private final String dbUrl;
    private final String redisHost;
    private final int redisPort;

    public HealthService(DataSource dataSource,
                        @Autowired(required = false) RedisTemplate<String, Object> redisTemplate,
                        @Value("${spring.datasource.url:unknown}") String dbUrl,
                        @Value("${spring.redis.host:localhost}") String redisHost,
                        @Value("${spring.redis.port:6379}") int redisPort) {
        this.dataSource = dataSource;
        this.redisTemplate = redisTemplate;
        this.dbUrl = dbUrl;
        this.redisHost = redisHost;
        this.redisPort = redisPort;
    }

    public Map<String, Object> checkHealth() {
        Map<String, Object> healthStatus = new LinkedHashMap<>();
        Map<String, Object> components = new LinkedHashMap<>();
        boolean overallHealth = true;

        Map<String, Object> mysqlStatus = checkMySQLHealth();
        components.put("mysql", mysqlStatus);
        if (!isHealthy(mysqlStatus)) {
            overallHealth = false;
        }

        if (redisTemplate != null) {
            Map<String, Object> redisStatus = checkRedisHealth();
            components.put("redis", redisStatus);
            if (!isHealthy(redisStatus)) {
                overallHealth = false;
            }
        }

        healthStatus.put(STATUS_KEY, overallHealth ? STATUS_UP : STATUS_DOWN);
        healthStatus.put("timestamp", Instant.now().toString());
        healthStatus.put("components", components);
        logger.info("Health check completed - Overall status: {}", overallHealth ? STATUS_UP : STATUS_DOWN);

        return healthStatus;
    }



    private Map<String, Object> checkMySQLHealth() {
        Map<String, Object> status = new LinkedHashMap<>();
        
        status.put("type", "MySQL");

        return performHealthCheck("MySQL", status, () -> {
            try {
                try (Connection connection = dataSource.getConnection()) {
                    if (connection.isValid(2)) {
                        try (PreparedStatement stmt = connection.prepareStatement(DB_HEALTH_CHECK_QUERY)) {
                            stmt.setQueryTimeout(3);
                            try (ResultSet rs = stmt.executeQuery()) {
                                if (rs.next() && rs.getInt(1) == 1) {
                                    return new HealthCheckResult(true, null, null);
                                }
                            }
                        }
                    }
                    return new HealthCheckResult(false, null, "Connection validation failed");
                }
            } catch (Exception e) {
                throw new IllegalStateException("Failed to perform MySQL health check", e);
            }
        });
    }

    private Map<String, Object> checkRedisHealth() {
        Map<String, Object> status = new LinkedHashMap<>();
        
        status.put("type", "Redis");

        return performHealthCheck("Redis", status, () -> {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(
                () -> redisTemplate.execute((RedisCallback<String>) connection -> connection.ping()),
                executorService);
            try {
                String pong = future.get(REDIS_TIMEOUT_SECONDS, TimeUnit.SECONDS);
                
                if ("PONG".equals(pong)) {
                    return new HealthCheckResult(true, null, null);
                }
                return new HealthCheckResult(false, null, "Ping returned unexpected response");
            } catch (TimeoutException e) {
                future.cancel(true);
                return new HealthCheckResult(false, null, "Redis ping timed out after " + REDIS_TIMEOUT_SECONDS + " seconds");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return new HealthCheckResult(false, null, "Redis health check was interrupted");
            } catch (Exception e) {
                throw new IllegalStateException("Redis health check failed", e);
            }
        });
    }

    private Map<String, Object> performHealthCheck(String componentName,
                                                    Map<String, Object> status,
                                                    Supplier<HealthCheckResult> checker) {
        long startTime = System.currentTimeMillis();
        
        try {
            HealthCheckResult result = checker.get();
            long responseTime = System.currentTimeMillis() - startTime;
            
            status.put("responseTimeMs", responseTime);

            if (result.isHealthy) {
                logger.debug("{} health check: UP ({}ms)", componentName, responseTime);
                status.put(STATUS_KEY, STATUS_UP);
                if (result.version != null) {
                    status.put("version", result.version);
                }
            } else {
                String safeError = result.error != null ? result.error : "Health check failed";
                logger.warn("{} health check failed: {}", componentName, safeError);
                status.put(STATUS_KEY, STATUS_DOWN);
                status.put("error", safeError);
            }
            
            return status;
            
        } catch (Exception e) {
            long responseTime = System.currentTimeMillis() - startTime;
            
            logger.error("{} health check failed", componentName, e);
            
            status.put(STATUS_KEY, STATUS_DOWN);
            status.put("responseTimeMs", responseTime);
            status.put("error", "Health check failed with an unexpected error");
            
            return status;
        }
    }

    private boolean isHealthy(Map<String, Object> componentStatus) {
        return STATUS_UP.equals(componentStatus.get(STATUS_KEY));
    }


    private static class HealthCheckResult {
        final boolean isHealthy;
        final String version;
        final String error;

        HealthCheckResult(boolean isHealthy, String version, String error) {
            this.isHealthy = isHealthy;
            this.version = version;
            this.error = error;
        }
    }
}
