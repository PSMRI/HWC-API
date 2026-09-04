package com.iemr.hwc.service.health;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * The health endpoint answers a load balancer, so what matters is that it always answers,
 * and that it answers DOWN when a dependency is unreachable rather than hanging or
 * failing. Each dependency is reported on its own so an operator can see which one broke.
 */
@DisplayName("HealthService")
class HealthServiceTest {

	private HealthService healthService;

	@AfterEach
	void shutdown() {
		if (healthService != null) {
			healthService.shutdown();
		}
	}

	private static DataSource reachableDatabase() throws SQLException {
		DataSource dataSource = mock(DataSource.class);
		Connection connection = mock(Connection.class);
		PreparedStatement statement = mock(PreparedStatement.class);
		ResultSet resultSet = mock(ResultSet.class);
		when(dataSource.getConnection()).thenReturn(connection);
		when(connection.prepareStatement(anyString())).thenReturn(statement);
		when(statement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(true);
		return dataSource;
	}

	private static DataSource unreachableDatabase() throws SQLException {
		DataSource dataSource = mock(DataSource.class);
		when(dataSource.getConnection()).thenThrow(new SQLException("connection refused"));
		return dataSource;
	}

	@SuppressWarnings("unchecked")
	private static RedisTemplate<String, Object> respondingRedis(String reply) {
		RedisTemplate<String, Object> redisTemplate = mock(RedisTemplate.class);
		when(redisTemplate.execute(any(RedisCallback.class))).thenReturn(reply);
		return redisTemplate;
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Object> componentsOf(Map<String, Object> response, String component) {
		Map<String, Map<String, Object>> components = (Map<String, Map<String, Object>>) response.get("components");
		return components.get(component);
	}

	@Test
	@DisplayName("reports UP when both the database and Redis answer")
	void reportsUpWhenEverythingAnswers() throws Exception {
		healthService = new HealthService(reachableDatabase(), respondingRedis("PONG"));

		Map<String, Object> response = healthService.checkHealth();

		assertThat(response.get("status")).isEqualTo("UP");
		assertThat(response.get("timestamp")).isNotNull();
		assertThat(componentsOf(response, "mysql").get("status")).isEqualTo("UP");
		assertThat(componentsOf(response, "redis").get("status")).isEqualTo("UP");
		assertThat(componentsOf(response, "mysql")).containsKey("responseTimeMs");
	}

	@Test
	@DisplayName("reports DOWN and names the database when it cannot be reached")
	void reportsDownWhenTheDatabaseIsUnreachable() throws Exception {
		healthService = new HealthService(unreachableDatabase(), respondingRedis("PONG"));

		Map<String, Object> response = healthService.checkHealth();

		assertThat(response.get("status")).isEqualTo("DOWN");
		assertThat(componentsOf(response, "mysql").get("status")).isEqualTo("DOWN");
		assertThat(componentsOf(response, "mysql").get("error")).isEqualTo("MySQL connection failed");
		assertThat(componentsOf(response, "redis").get("status")).isEqualTo("UP");
	}

	@Test
	@DisplayName("reports DOWN when the database answers nothing to the health query")
	void reportsDownWhenTheQueryReturnsNothing() throws Exception {
		DataSource dataSource = mock(DataSource.class);
		Connection connection = mock(Connection.class);
		PreparedStatement statement = mock(PreparedStatement.class);
		ResultSet resultSet = mock(ResultSet.class);
		when(dataSource.getConnection()).thenReturn(connection);
		when(connection.prepareStatement(anyString())).thenReturn(statement);
		when(statement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(false);
		healthService = new HealthService(dataSource, respondingRedis("PONG"));

		Map<String, Object> response = healthService.checkHealth();

		assertThat(response.get("status")).isEqualTo("DOWN");
		assertThat(componentsOf(response, "mysql").get("error")).isEqualTo("No result from health check query");
	}

	@Test
	@DisplayName("reports DOWN and names Redis when it answers something other than PONG")
	void reportsDownWhenRedisDoesNotPong() throws Exception {
		healthService = new HealthService(reachableDatabase(), respondingRedis("nothing"));

		Map<String, Object> response = healthService.checkHealth();

		assertThat(response.get("status")).isEqualTo("DOWN");
		assertThat(componentsOf(response, "redis").get("error")).isEqualTo("Redis PING failed");
	}

	@SuppressWarnings("unchecked")
	@Test
	@DisplayName("reports DOWN and names Redis when it cannot be reached")
	void reportsDownWhenRedisIsUnreachable() throws Exception {
		RedisTemplate<String, Object> redisTemplate = mock(RedisTemplate.class);
		when(redisTemplate.execute(any(RedisCallback.class)))
				.thenThrow(new IllegalStateException("connection refused"));
		healthService = new HealthService(reachableDatabase(), redisTemplate);

		Map<String, Object> response = healthService.checkHealth();

		assertThat(response.get("status")).isEqualTo("DOWN");
		assertThat(componentsOf(response, "redis").get("error")).isEqualTo("Redis connection failed");
	}

	@Test
	@DisplayName("treats an unconfigured Redis as healthy and says it was skipped")
	void treatsAnUnconfiguredRedisAsHealthy() throws Exception {
		healthService = new HealthService(reachableDatabase(), null);

		Map<String, Object> response = healthService.checkHealth();

		assertThat(response.get("status")).isEqualTo("UP");
		assertThat(componentsOf(response, "redis").get("status")).isEqualTo("UP");
		assertThat(componentsOf(response, "redis").get("message")).asString().contains("not configured");
	}

	@Test
	@DisplayName("still answers, with both components reported, once the checks have been shut down")
	void stillAnswersAfterShutdown() throws Exception {
		healthService = new HealthService(reachableDatabase(), respondingRedis("PONG"));

		healthService.shutdown();
		Map<String, Object> response = healthService.checkHealth();

		assertThat(response).containsKey("components");
		assertThat(componentsOf(response, "mysql")).containsKey("status");
		assertThat(componentsOf(response, "redis")).containsKey("status");
	}

	@Test
	@DisplayName("shutting down twice is harmless")
	void shuttingDownTwiceIsHarmless() throws Exception {
		healthService = new HealthService(reachableDatabase(), respondingRedis("PONG"));

		healthService.shutdown();

		assertThatCode(() -> healthService.shutdown()).doesNotThrowAnyException();
	}
}
