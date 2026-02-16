package com.iemr.hwc.service.health;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
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
    private static final String DB_HEALTH_CHECK_QUERY = "SELECT 1 as health_check";
    private static final String DB_VERSION_QUERY = "SELECT VERSION()";

    @Autowired
    private DataSource dataSource;

    @Autowired(required = false)
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${spring.datasource.url:unknown}")
    private String dbUrl;

    @Value("${spring.redis.host:localhost}")
    private String redisHost;

    @Value("${spring.redis.port:6379}")
    private int redisPort;

    public Map<String, Object> checkHealth() {
        Map<String, Object> healthStatus = new LinkedHashMap<>();
        Map<String, Object> components = new LinkedHashMap<>();
        boolean overallHealth = true;

        // Check MySQL connectivity
        Map<String, Object> mysqlStatus = checkMySQLHealth();
        components.put("mysql", mysqlStatus);
        if (!isHealthy(mysqlStatus)) {
            overallHealth = false;
        }

        // Check Redis connectivity if configured
        if (redisTemplate != null) {
            Map<String, Object> redisStatus = checkRedisHealth();
            components.put("redis", redisStatus);
            if (!isHealthy(redisStatus)) {
                overallHealth = false;
            }
        }

        healthStatus.put("status", overallHealth ? "UP" : "DOWN");
        healthStatus.put("timestamp", Instant.now().toString());
        healthStatus.put("components", components);

        logger.info("Health check completed - Overall status: {}", overallHealth ? "UP" : "DOWN");
        return healthStatus;
    }

    private Map<String, Object> checkMySQLHealth() {
        Map<String, Object> details = new LinkedHashMap<>();
        
        details.put("type", "MySQL");
        details.put("host", extractHost(dbUrl));
        details.put("port", extractPort(dbUrl));
        details.put("database", extractDatabaseName(dbUrl));

        return performHealthCheck("MySQL", details, () -> {
            try {
                try (Connection connection = dataSource.getConnection()) {
                    if (connection.isValid(2)) {
                        try (PreparedStatement stmt = connection.prepareStatement(DB_HEALTH_CHECK_QUERY)) {
                            stmt.setQueryTimeout(3);
                            try (ResultSet rs = stmt.executeQuery()) {
                                if (rs.next() && rs.getInt(1) == 1) {
                                    String version = getMySQLVersion(connection);
                                    return new HealthCheckResult(true, version, null);
                                }
                            }
                        }
                    }
                    return new HealthCheckResult(false, null, "Connection validation failed");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Map<String, Object> checkRedisHealth() {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("type", "Redis");
        details.put("host", redisHost);
        details.put("port", redisPort);

        return performHealthCheck("Redis", details, () -> {
            String pong = redisTemplate.execute((RedisCallback<String>) connection ->
                connection.ping()
            );
            if ("PONG".equals(pong)) {
                String version = getRedisVersion();
                return new HealthCheckResult(true, version, null);
            }
            return new HealthCheckResult(false, null, "Ping returned unexpected response");
        });
    }

    /**
     * Common health check execution pattern to reduce code duplication.
     */
    private Map<String, Object> performHealthCheck(String componentName, 
                                                    Map<String, Object> details,
                                                    Supplier<HealthCheckResult> checker) {
        Map<String, Object> status = new LinkedHashMap<>();
        long startTime = System.currentTimeMillis();

        try {
            HealthCheckResult result = checker.get();
            long responseTime = System.currentTimeMillis() - startTime;

            if (result.isHealthy) {
                logger.debug("{} health check: UP ({}ms)", componentName, responseTime);
                status.put("status", "UP");
                details.put("responseTimeMs", responseTime);
                if (result.version != null) {
                    details.put("version", result.version);
                }
            } else {
                logger.warn("{} health check: {}", componentName, result.error);
                status.put("status", "DOWN");
                details.put("error", result.error);
            }

            status.put("details", details);
            return status;
        } catch (Exception e) {
            logger.error("{} health check failed: {}", componentName, e.getMessage());
            long responseTime = System.currentTimeMillis() - startTime;
            status.put("status", "DOWN");
            details.put("error", "Service unavailable");
            details.put("responseTimeMs", responseTime);
            status.put("details", details);
            return status;
        }
    }

    private boolean isHealthy(Map<String, Object> componentStatus) {
        return "UP".equals(componentStatus.get("status"));
    }

    private String getMySQLVersion(Connection connection) {
        try (PreparedStatement stmt = connection.prepareStatement(DB_VERSION_QUERY);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            logger.debug("Could not retrieve MySQL version: {}", e.getMessage());
        }
        return null;
    }

    private String getRedisVersion() {
        try {
            Properties info = redisTemplate.execute((RedisCallback<Properties>) connection ->
                connection.serverCommands().info("server")
            );
            if (info != null && info.containsKey("redis_version")) {
                return info.getProperty("redis_version");
            }
        } catch (Exception e) {
            logger.debug("Could not retrieve Redis version: {}", e.getMessage());
        }
        return null;
    }

    private String extractHost(String jdbcUrl) {
        if (jdbcUrl == null || "unknown".equals(jdbcUrl)) {
            return "unknown";
        }
        try {
            String withoutPrefix = jdbcUrl.replaceFirst("jdbc:mysql://", "");
            int slashIndex = withoutPrefix.indexOf('/');
            String hostPort = slashIndex > 0 
                ? withoutPrefix.substring(0, slashIndex) 
                : withoutPrefix;
            int colonIndex = hostPort.indexOf(':');
            return colonIndex > 0 ? hostPort.substring(0, colonIndex) : hostPort;
        } catch (Exception e) {
            logger.debug("Could not extract host from URL: {}", e.getMessage());
        }
        return "unknown";
    }

    private String extractPort(String jdbcUrl) {
        if (jdbcUrl == null || "unknown".equals(jdbcUrl)) {
            return "unknown";
        }
        try {
            String withoutPrefix = jdbcUrl.replaceFirst("jdbc:mysql://", "");
            int slashIndex = withoutPrefix.indexOf('/');
            String hostPort = slashIndex > 0 
                ? withoutPrefix.substring(0, slashIndex) 
                : withoutPrefix;
            int colonIndex = hostPort.indexOf(':');
            return colonIndex > 0 ? hostPort.substring(colonIndex + 1) : "3306";
        } catch (Exception e) {
            logger.debug("Could not extract port from URL: {}", e.getMessage());
        }
        return "3306";
    }

    private String extractDatabaseName(String jdbcUrl) {
        if (jdbcUrl == null || "unknown".equals(jdbcUrl)) {
            return "unknown";
        }
        try {
            int lastSlash = jdbcUrl.lastIndexOf('/');
            if (lastSlash >= 0 && lastSlash < jdbcUrl.length() - 1) {
                String afterSlash = jdbcUrl.substring(lastSlash + 1);
                int queryStart = afterSlash.indexOf('?');
                if (queryStart > 0) {
                    return afterSlash.substring(0, queryStart);
                }
                return afterSlash;
            }
        } catch (Exception e) {
            logger.debug("Could not extract database name: {}", e.getMessage());
        }
        return "unknown";
    }

    /**
     * Internal class to hold health check results.
     */
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