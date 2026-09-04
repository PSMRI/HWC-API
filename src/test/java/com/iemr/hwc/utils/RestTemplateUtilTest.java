package com.iemr.hwc.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.Cookie;

@DisplayName("RestTemplateUtil")
class RestTemplateUtilTest {

	@AfterEach
	void clearRequestContext() {
		RequestContextHolder.resetRequestAttributes();
		UserAgentContext.clear();
	}

	private static void bindRequest(MockHttpServletRequest request) {
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	}

	@Test
	@DisplayName("builds a JSON request with the Authorization header when there is no request in scope")
	void buildsARequestOutsideAWebRequest() {
		HttpEntity<Object> entity = RestTemplateUtil.createRequestEntity("{\"a\":1}", "Bearer token");

		assertThat(entity.getBody()).isEqualTo("{\"a\":1}");
		assertThat(entity.getHeaders().getFirst(HttpHeaders.AUTHORIZATION)).isEqualTo("Bearer token");
		assertThat(entity.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)).isEqualTo("application/json;charset=utf-8");
		assertThat(entity.getHeaders().getFirst(HttpHeaders.COOKIE)).isNull();
	}

	@Test
	@DisplayName("forwards the caller's JWT header on to the downstream service")
	void forwardsTheJwtHeader() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader(Constants.JWT_TOKEN, "header-token");
		bindRequest(request);

		HttpEntity<Object> entity = RestTemplateUtil.createRequestEntity("{}", "Bearer token");

		assertThat(entity.getHeaders().getFirst(Constants.JWT_TOKEN)).isEqualTo("header-token");
	}

	@Test
	@DisplayName("forwards the caller's JWT cookie on as a cookie header")
	void forwardsTheJwtCookie() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setCookies(new Cookie("Jwttoken", "cookie-token"));
		bindRequest(request);

		HttpEntity<Object> entity = RestTemplateUtil.createRequestEntity("{}", "Bearer token");

		assertThat(entity.getHeaders().getFirst(HttpHeaders.COOKIE)).isEqualTo("Jwttoken=cookie-token");
	}

	@Test
	@DisplayName("forwards the mobile client's user agent so the downstream service can recognise it")
	void forwardsTheUserAgent() {
		bindRequest(new MockHttpServletRequest());
		UserAgentContext.setUserAgent("okhttp/4.9.0");

		HttpEntity<Object> entity = RestTemplateUtil.createRequestEntity("{}", "Bearer token");

		assertThat(entity.getHeaders().getFirst(HttpHeaders.USER_AGENT)).isEqualTo("okhttp/4.9.0");
	}

	@Test
	@DisplayName("sends no user agent when the thread carries none")
	void sendsNoUserAgentWhenThereIsNone() {
		bindRequest(new MockHttpServletRequest());

		HttpEntity<Object> entity = RestTemplateUtil.createRequestEntity("{}", "Bearer token");

		assertThat(entity.getHeaders().getFirst(HttpHeaders.USER_AGENT)).isNull();
	}

	@Test
	@DisplayName("sends the Authorization header even when the request carries no token of its own")
	void alwaysSendsAuthorization() {
		bindRequest(new MockHttpServletRequest());

		HttpEntity<Object> entity = RestTemplateUtil.createRequestEntity("{}", "Bearer token");

		assertThat(entity.getHeaders().getFirst(HttpHeaders.AUTHORIZATION)).isEqualTo("Bearer token");
		assertThat(entity.getHeaders().getFirst(Constants.JWT_TOKEN)).isNull();
	}
}
