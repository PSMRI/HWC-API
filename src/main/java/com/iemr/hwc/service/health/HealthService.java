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

/**
 * Service to check the health of infrastructure components (MySQL, Redis).
 * Returns detailed health status with response times and version information.
 */
@Service
public class HealthService {

    private static final Logger logger = LoggerFactory.getLogger(HealthService.class);
    private static final String DB_HEALTH_CHECK_QUERY = "SELECT 1 as health_check";
    private static final String DB_VERSION_QUERY = "SELECT VERSION()";
    private static final String STATUS_UP = "UP";
    private static final String STATUS_DOWN = "DOWN";
    private static final String UNKNOWN_VALUE = "unknown";

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

    /**
     * Check the health of all configured infrastructure components.
     * @param includeDetails if true, includes sensitive infrastructure details (host, port, database, version);
     *                       if false, returns only component status for unauthenticated users
     */
    public Map<String, Object> checkHealth(boolean includeDetails) {
        Map<String, Object> healthStatus = new LinkedHashMap<>();
        Map<String, Object> components = new LinkedHashMap<>();
        boolean overallHealth = true;

        // Check MySQL connectivity
        Map<String, Object> mysqlStatus = checkMySQLHealth(includeDetails);
        components.put("mysql", mysqlStatus);
        if (!isHealthy(mysqlStatus)) {
            overallHealth = false;
        }

        // Check Redis connectivity if configured
        if (redisTemplate != null) {
            Map<String, Object> redisStatus = checkRedisHealth(includeDetails);
            components.put("redis", redisStatus);
            if (!isHealthy(redisStatus)) {
                overallHealth = false;
            }
        }

        healthStatus.put("status", overallHealth ? STATUS_UP : STATUS_DOWN);
        healthStatus.put("timestamp", Instant.now().toString());
        healthStatus.put("components", components);
        logger.info("Health check completed - Overall status: {}", overallHealth ? STATUS_UP : STATUS_DOWN);

        return healthStatus;
    }

    /**
     * Check the health of all configured infrastructure components (default includes details for backward compatibility).
     */
    public Map<String, Object> checkHealth() {
        return checkHealth(true);
    }

    /**
     * Check MySQL database connectivity and retrieve version information.
     */
    private Map<String, Object> checkMySQLHealth(boolean includeDetails) {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("type", "MySQL");
        
        // Only include sensitive infrastructure details if requested (authenticated user)
        if (includeDetails) {
            details.put("host", extractHost(dbUrl));
            details.put("port", extractPort(dbUrl));
            details.put("database", extractDatabaseName(dbUrl));
        }

        return performHealthCheck("MySQL", details, includeDetails, () -> {
            try {
                try (Connection connection = dataSource.getConnection()) {
                    if (connection.isValid(2)) {
                        try (PreparedStatement stmt = connection.prepareStatement(DB_HEALTH_CHECK_QUERY)) {
                            stmt.setQueryTimeout(3);
                            try (ResultSet rs = stmt.executeQuery()) {
                                if (rs.next() && rs.getInt(1) == 1) {
                                    String version = includeDetails ? getMySQLVersion(connection) : null;
                                    return new HealthCheckResult(true, version, null);
                                }
                            }
                        }
                    }
                    return new HealthCheckResult(false, null, "Connection validation failed");
                }
            } catch (Exception e) {
                logger.error("MySQL health check error", e);
                throw new IllegalStateException("Failed to perform MySQL health check", e);
            }
        });
    }

    /**
     * Check Redis connectivity and retrieve version information.
     */
    private Map<String, Object> checkRedisHealth(boolean includeDetails) {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("type", "Redis");
        
        // Only include sensitive infrastructure details if requested (authenticated user)
        if (includeDetails) {
            details.put("host", redisHost);
            details.put("port", redisPort);
        }

        return performHealthCheck("Redis", details, includeDetails, () -> {
            String pong = redisTemplate.execute((RedisCallback<String>) connection ->
                connection.ping()
            );
            if ("PONG".equals(pong)) {
                String version = includeDetails ? getRedisVersion() : null;
                return new HealthCheckResult(true, version, null);
            }
            return new HealthCheckResult(false, null, "Ping returned unexpected response");
        });
    }

    /**
     * Common health check execution pattern with security improvements:
     * - Consistent failure payloads
     * - Sanitized error messages (no raw exceptions to callers)
     * - Always includes responseTimeMs and errorType
     * - Full exceptions logged internally only
     */
    private Map<String, Object> performHealthCheck(String componentName,
                                                    Map<String, Object> details,
                                                    boolean includeDetails,
                                                    Supplier<HealthCheckResult> checker) {
        Map<String, Object> status = new LinkedHashMap<>();
        long startTime = System.currentTimeMillis();
        
        try {
            HealthCheckResult result = checker.get();
            long responseTime = System.currentTimeMillis() - startTime;
            
            // Always populate response time
            details.put("responseTimeMs", responseTime);

            if (result.isHealthy) {
                logger.debug("{} health check: UP ({}ms)", componentName, responseTime);
                status.put("status", STATUS_UP);
                if (result.version != null) {
                    details.put("version", result.version);
                }
            } else {
                // Sanitize error message - do not expose internal details
                String safeError = result.error != null ? result.error : "Health check failed";
                logger.warn("{} health check failed: {}", componentName, safeError);
                status.put("status", STATUS_DOWN);
                details.put("error", safeError);
                details.put("errorType", "CheckFailed");
            }
            
            status.put("details", details);
            return status;
            
        } catch (Exception e) {
            long responseTime = System.currentTimeMillis() - startTime;
            
            // Log full exception internally for debugging
            logger.error("{} health check failed", componentName, e);
            
            // Return sanitized error to caller - never expose exception class names
            status.put("status", STATUS_DOWN);
            details.put("responseTimeMs", responseTime);
            details.put("error", "Health check failed");
            details.put("errorType", "InternalError");
            status.put("details", details);
            
            return status;
        }
    }

    /**
     * Check if a component status indicates healthy state.
     */
    private boolean isHealthy(Map<String, Object> componentStatus) {
        return STATUS_UP.equals(componentStatus.get("status"));
    }

    /**
     * Retrieve MySQL version information.
     */
    private String getMySQLVersion(Connection connection) {
        try (PreparedStatement stmt = connection.prepareStatement(DB_VERSION_QUERY);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            logger.debug("Could not retrieve MySQL version", e);
        }
        return null;
    }

    /**
     * Retrieve Redis version information.
     */
    private String getRedisVersion() {
        try {
            Properties info = redisTemplate.execute((RedisCallback<Properties>) connection ->
                connection.serverCommands().info("server")
            );
            if (info != null && info.containsKey("redis_version")) {
                return info.getProperty("redis_version");
            }
        } catch (Exception e) {
            logger.debug("Could not retrieve Redis version", e);
        }
        return null;
    }

    /**
     * Extract hostname from JDBC URL.
     */
    private String extractHost(String jdbcUrl) {
        if (jdbcUrl == null || UNKNOWN_VALUE.equals(jdbcUrl)) {
            return UNKNOWN_VALUE;
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
            logger.debug("Could not extract host from URL", e);
        }
        return "unknown";
    }

    /**
     * Extract port number from JDBC URL.
     */
    private String extractPort(String jdbcUrl) {
        if (jdbcUrl == null || UNKNOWN_VALUE.equals(jdbcUrl)) {
            return UNKNOWN_VALUE;
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
            logger.debug("Could not extract port from URL", e);
        }
        return "3306";
    }

    /**
     * Extract database name from JDBC URL.
     */
    private String extractDatabaseName(String jdbcUrl) {
        if (jdbcUrl == null || UNKNOWN_VALUE.equals(jdbcUrl)) {
            return UNKNOWN_VALUE;
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
            logger.debug("Could not extract database name from URL", e);
        }
        return "unknown";
    }

    /**
     * Internal class to hold health check results.
     * Encapsulates the outcome of a health check with optional version and error details.
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
