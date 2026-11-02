package com.iemr.hwc.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@DisplayName("CorsConfig")
class CorsConfigTest {

	private static CorsRegistration registrationOn(CorsRegistry registry) {
		CorsRegistration registration = mock(CorsRegistration.class, invocation -> {
			// The registry API is a builder, so every call has to hand the builder back.
			return invocation.getMethod().getReturnType() == CorsRegistration.class ? invocation.getMock() : null;
		});
		when(registry.addMapping(any())).thenReturn(registration);
		return registration;
	}

	@Test
	@DisplayName("registers the configured origins for every path")
	void registersTheConfiguredOrigins() {
		CorsConfig config = new CorsConfig();
		ReflectionTestUtils.setField(config, "allowedOrigins", "https://a.example.org, https://b.example.org");
		CorsRegistry registry = mock(CorsRegistry.class);
		CorsRegistration registration = registrationOn(registry);

		config.addCorsMappings(registry);

		verify(registry).addMapping("/**");
		ArgumentCaptor<String[]> origins = ArgumentCaptor.forClass(String[].class);
		verify(registration).allowedOriginPatterns(origins.capture());
		assertThat(origins.getValue()).containsExactly("https://a.example.org", "https://b.example.org");
		verify(registration).allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
		verify(registration).allowCredentials(true);
		verify(registration).maxAge(3600);
	}

	@Test
	@DisplayName("registers a single origin without a trailing empty pattern")
	void registersASingleOrigin() {
		CorsConfig config = new CorsConfig();
		ReflectionTestUtils.setField(config, "allowedOrigins", "https://only.example.org");
		CorsRegistry registry = mock(CorsRegistry.class);
		CorsRegistration registration = registrationOn(registry);

		config.addCorsMappings(registry);

		ArgumentCaptor<String[]> origins = ArgumentCaptor.forClass(String[].class);
		verify(registration).allowedOriginPatterns(origins.capture());
		assertThat(origins.getValue()).containsExactly("https://only.example.org");
	}
}
