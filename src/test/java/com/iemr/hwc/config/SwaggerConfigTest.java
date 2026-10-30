package com.iemr.hwc.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.test.util.ReflectionTestUtils;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;

@DisplayName("SwaggerConfig")
class SwaggerConfigTest {

	private static SwaggerConfig configWithVersion(String version) {
		SwaggerConfig config = new SwaggerConfig();
		ReflectionTestUtils.setField(config, "apiVersion", version);
		return config;
	}

	@Test
	@DisplayName("describes the API with the configured version and the three environment servers")
	void describesTheApi() {
		MockEnvironment environment = new MockEnvironment().withProperty("api.dev.url", "https://dev.example.org")
				.withProperty("api.uat.url", "https://uat.example.org")
				.withProperty("api.demo.url", "https://demo.example.org");

		OpenAPI openApi = configWithVersion("3.8.0").customOpenAPI(environment);

		assertThat(openApi.getInfo().getTitle()).isEqualTo("HWC API");
		assertThat(openApi.getInfo().getVersion()).isEqualTo("3.8.0");
		assertThat(openApi.getServers()).extracting("url").containsExactly("https://dev.example.org",
				"https://uat.example.org", "https://demo.example.org");
		assertThat(openApi.getServers()).extracting("description").containsExactly("Dev", "UAT", "Demo");
	}

	@Test
	@DisplayName("declares bearer authentication as the security scheme")
	void declaresBearerAuthentication() {
		OpenAPI openApi = configWithVersion("3.8.0").customOpenAPI(new MockEnvironment());

		SecurityScheme scheme = openApi.getComponents().getSecuritySchemes().get("my security");
		assertThat(scheme.getType()).isEqualTo(SecurityScheme.Type.HTTP);
		assertThat(scheme.getScheme()).isEqualTo("bearer");
		assertThat(openApi.getSecurity()).hasSize(1);
	}

	@Test
	@DisplayName("falls back to the local server when no environment URLs are configured")
	void fallsBackToTheLocalServer() {
		OpenAPI openApi = configWithVersion("3.8.0").customOpenAPI(new MockEnvironment());

		assertThat(openApi.getServers()).extracting("url").containsOnly("http://localhost:9090");
	}
}
