package com.iemr.hwc.utils.http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.servlet.http.HttpServletRequest;

@DisplayName("AuthorizationHeaderRequestWrapper")
class AuthorizationHeaderRequestWrapperTest {

	private static HttpServletRequest requestWith(String... headerNames) {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getHeaderNames()).thenReturn(Collections.enumeration(List.of(headerNames)));
		when(request.getHeader("Content-Type")).thenReturn("application/json");
		when(request.getHeaders("Content-Type")).thenReturn(Collections.enumeration(List.of("application/json")));
		return request;
	}

	@Test
	@DisplayName("answers the Authorization header with the value it was wrapped with")
	void overridesTheAuthorizationHeader() {
		AuthorizationHeaderRequestWrapper wrapper = new AuthorizationHeaderRequestWrapper(
				requestWith("Content-Type"), "replacement-token");

		assertThat(wrapper.getHeader("Authorization")).isEqualTo("replacement-token");
		assertThat(wrapper.getHeader("authorization")).isEqualTo("replacement-token");
	}

	@Test
	@DisplayName("passes any other header through to the wrapped request")
	void passesOtherHeadersThrough() {
		AuthorizationHeaderRequestWrapper wrapper = new AuthorizationHeaderRequestWrapper(
				requestWith("Content-Type"), "replacement-token");

		assertThat(wrapper.getHeader("Content-Type")).isEqualTo("application/json");
		assertThat(Collections.list(wrapper.getHeaders("Content-Type"))).containsExactly("application/json");
	}

	@Test
	@DisplayName("answers the multi-value Authorization lookup with the single wrapped value")
	void overridesTheMultiValueLookup() {
		AuthorizationHeaderRequestWrapper wrapper = new AuthorizationHeaderRequestWrapper(
				requestWith("Content-Type"), "replacement-token");

		assertThat(Collections.list(wrapper.getHeaders("Authorization"))).containsExactly("replacement-token");
	}

	@Test
	@DisplayName("adds Authorization to the header names when the request did not carry it")
	void addsAuthorizationToTheHeaderNames() {
		AuthorizationHeaderRequestWrapper wrapper = new AuthorizationHeaderRequestWrapper(
				requestWith("Content-Type"), "replacement-token");

		assertThat(Collections.list(wrapper.getHeaderNames())).containsExactly("Content-Type", "Authorization");
	}

	@Test
	@DisplayName("does not repeat Authorization when the request already carried it")
	void doesNotRepeatAuthorization() {
		AuthorizationHeaderRequestWrapper wrapper = new AuthorizationHeaderRequestWrapper(
				requestWith("Authorization", "Content-Type"), "replacement-token");

		assertThat(Collections.list(wrapper.getHeaderNames())).containsExactly("Authorization", "Content-Type");
	}
}
