package com.iemr.hwc.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.http.AuthorizationHeaderRequestWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("JwtUserIdValidationFilter")
class JwtUserIdValidationFilterTest {

	private static final String ALLOWED = "https://hwc.example.org, https://*.trusted.example.org";

	@Mock
	private JwtAuthenticationUtil jwtAuthenticationUtil;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private FilterChain chain;

	private JwtUserIdValidationFilter filter(String allowedOrigins) {
		return new JwtUserIdValidationFilter(jwtAuthenticationUtil, allowedOrigins);
	}

	private void requestTo(String method, String uri, String origin) {
		when(request.getMethod()).thenReturn(method);
		when(request.getRequestURI()).thenReturn(uri);
		when(request.getContextPath()).thenReturn("");
		when(request.getHeader("Origin")).thenReturn(origin);
	}

	@Test
	@DisplayName("answers a preflight from an allowed origin with the CORS headers and does not run the chain")
	void answersPreflight() throws Exception {
		requestTo("OPTIONS", "/ANC/save/nurseData", "https://hwc.example.org");

		filter(ALLOWED).doFilter(request, response, chain);

		// A preflight passes through both the general CORS block and the OPTIONS block, so
		// these headers are written twice.
		verify(response, atLeastOnce()).setHeader("Access-Control-Allow-Origin", "https://hwc.example.org");
		verify(response, atLeastOnce()).setHeader("Access-Control-Allow-Credentials", "true");
		verify(response).setStatus(HttpServletResponse.SC_OK);
		verify(chain, never()).doFilter(any(), any());
	}

	@Test
	@DisplayName("blocks a preflight that carries no Origin header")
	void blocksPreflightWithoutOrigin() throws Exception {
		requestTo("OPTIONS", "/ANC/save/nurseData", null);

		filter(ALLOWED).doFilter(request, response, chain);

		verify(response).sendError(HttpServletResponse.SC_FORBIDDEN, "OPTIONS request requires Origin header");
		verify(chain, never()).doFilter(any(), any());
	}

	@Test
	@DisplayName("blocks a preflight from an origin that is not configured")
	void blocksPreflightFromForeignOrigin() throws Exception {
		requestTo("OPTIONS", "/ANC/save/nurseData", "https://attacker.example.net");

		filter(ALLOWED).doFilter(request, response, chain);

		verify(response).sendError(HttpServletResponse.SC_FORBIDDEN, "Origin not allowed");
		verify(chain, never()).doFilter(any(), any());
	}

	@Test
	@DisplayName("blocks a normal request from an origin that is not configured")
	void blocksRequestFromForeignOrigin() throws Exception {
		requestTo("POST", "/ANC/save/nurseData", "https://attacker.example.net");

		filter(ALLOWED).doFilter(request, response, chain);

		verify(response).sendError(HttpServletResponse.SC_FORBIDDEN, "Origin not allowed");
		verify(chain, never()).doFilter(any(), any());
	}

	@Test
	@DisplayName("accepts an origin matched by a wildcard pattern")
	void acceptsAWildcardOrigin() throws Exception {
		requestTo("OPTIONS", "/ANC/save/nurseData", "https://team.trusted.example.org");

		filter(ALLOWED).doFilter(request, response, chain);

		verify(response).setStatus(HttpServletResponse.SC_OK);
		verify(response, never()).sendError(eq(HttpServletResponse.SC_FORBIDDEN), anyString());
	}

	@Test
	@DisplayName("blocks a preflight when no origins are configured at all")
	void blocksPreflightWhenNothingIsConfigured() throws Exception {
		requestTo("OPTIONS", "/ANC/save/nurseData", "https://hwc.example.org");

		filter("  ").doFilter(request, response, chain);

		verify(response).sendError(HttpServletResponse.SC_FORBIDDEN, "Origin not allowed");
	}

	@Test
	@DisplayName("lets the login endpoint through without a token")
	void skipsTheLoginEndpoint() throws Exception {
		requestTo("POST", "/user/userAuthenticate", null);

		filter(ALLOWED).doFilter(request, response, chain);

		verify(chain).doFilter(request, response);
		verify(response, never()).sendError(anyInt(), anyString());
	}

	@Test
	@DisplayName("lets the other public endpoints through without a token")
	void skipsThePublicEndpoints() throws Exception {
		for (String path : new String[] { "/user/logOutUserFromConcurrentSession", "/swagger-ui/index.html",
				"/v3/api-docs", "/user/refreshToken", "/public/anything", "/health", "/version" }) {
			FilterChain perPath = org.mockito.Mockito.mock(FilterChain.class);
			HttpServletResponse perPathResponse = org.mockito.Mockito.mock(HttpServletResponse.class);
			requestTo("POST", path, null);

			filter(ALLOWED).doFilter(request, perPathResponse, perPath);

			verify(perPath).doFilter(request, perPathResponse);
			verify(perPathResponse, never()).sendError(anyInt(), anyString());
		}
	}

	@Test
	@DisplayName("passes a request with a valid JWT cookie down the chain behind an Authorization wrapper")
	void acceptsAValidCookieToken() throws Exception {
		requestTo("POST", "/ANC/save/nurseData", "https://hwc.example.org");
		when(request.getCookies()).thenReturn(new Cookie[] { new Cookie("Jwttoken", "token") });
		when(jwtAuthenticationUtil.validateUserIdAndJwtToken("token")).thenReturn(true);

		filter(ALLOWED).doFilter(request, response, chain);

		ArgumentCaptor<ServletRequest> forwarded = ArgumentCaptor.forClass(ServletRequest.class);
		verify(chain).doFilter(forwarded.capture(), eq(response));
		assertThat(forwarded.getValue()).isInstanceOf(AuthorizationHeaderRequestWrapper.class);
	}

	@Test
	@DisplayName("passes a request with a valid JWT header down the chain")
	void acceptsAValidHeaderToken() throws Exception {
		requestTo("POST", "/ANC/save/nurseData", "https://hwc.example.org");
		when(request.getHeader(Constants.JWT_TOKEN)).thenReturn("header-token");
		when(jwtAuthenticationUtil.validateUserIdAndJwtToken("header-token")).thenReturn(true);

		filter(ALLOWED).doFilter(request, response, chain);

		verify(chain).doFilter(any(AuthorizationHeaderRequestWrapper.class), eq(response));
	}

	@Test
	@DisplayName("clears a userId cookie that a client tries to smuggle in")
	void clearsTheUserIdCookie() throws Exception {
		requestTo("POST", "/ANC/save/nurseData", "https://hwc.example.org");
		when(request.getCookies()).thenReturn(new Cookie[] { new Cookie("userId", "42") });
		when(request.getHeader("Authorization")).thenReturn("Bearer x");
		when(request.getHeader(Constants.USER_AGENT)).thenReturn("okhttp/4.9.0");

		filter(ALLOWED).doFilter(request, response, chain);

		ArgumentCaptor<Cookie> cleared = ArgumentCaptor.forClass(Cookie.class);
		verify(response).addCookie(cleared.capture());
		assertThat(cleared.getValue().getName()).isEqualTo("userId");
		assertThat(cleared.getValue().getMaxAge()).isZero();
		assertThat(cleared.getValue().isHttpOnly()).isTrue();
		assertThat(cleared.getValue().getSecure()).isTrue();
	}

	@Test
	@DisplayName("lets a mobile client through on its Authorization header alone, and clears the thread afterwards")
	void acceptsAMobileClient() throws Exception {
		requestTo("POST", "/ANC/save/nurseData", "https://hwc.example.org");
		when(request.getHeader(Constants.USER_AGENT)).thenReturn("okhttp/4.9.0");
		when(request.getHeader("Authorization")).thenReturn("Bearer mobile-token");

		filter(ALLOWED).doFilter(request, response, chain);

		verify(chain).doFilter(request, response);
		assertThat(UserAgentContext.getUserAgent()).as("the request-scoped user agent must not leak to the next"
				+ " request handled by this thread").isNull();
	}

	@Test
	@DisplayName("rejects a browser request that carries no token")
	void rejectsARequestWithoutAToken() throws Exception {
		requestTo("POST", "/ANC/save/nurseData", "https://hwc.example.org");

		filter(ALLOWED).doFilter(request, response, chain);

		verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid or missing token");
		verify(chain, never()).doFilter(any(), any());
	}

	@Test
	@DisplayName("rejects a mobile request whose user agent is not a known client")
	void rejectsAnUnknownUserAgent() throws Exception {
		requestTo("POST", "/ANC/save/nurseData", "https://hwc.example.org");
		when(request.getHeader(Constants.USER_AGENT)).thenReturn("curl/8.0");
		when(request.getHeader("Authorization")).thenReturn("Bearer x");

		filter(ALLOWED).doFilter(request, response, chain);

		verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid or missing token");
	}

	@Test
	@DisplayName("reports a validation failure as unauthorized rather than letting it escape")
	void reportsAValidationFailure() throws Exception {
		requestTo("POST", "/ANC/save/nurseData", "https://hwc.example.org");
		when(request.getCookies()).thenReturn(new Cookie[] { new Cookie("Jwttoken", "token") });
		when(jwtAuthenticationUtil.validateUserIdAndJwtToken("token"))
				.thenThrow(new IEMRException("Validation error: Invalid User ID."));

		filter(ALLOWED).doFilter(request, response, chain);

		verify(response).sendError(eq(HttpServletResponse.SC_UNAUTHORIZED),
				org.mockito.ArgumentMatchers.contains("Authorization error"));
		verify(chain, never()).doFilter(any(), any());
	}

	@Test
	@DisplayName("adds no CORS headers to a request that carries no Origin")
	void addsNoCorsHeadersWithoutAnOrigin() throws Exception {
		requestTo("POST", "/ANC/save/nurseData", null);

		filter(ALLOWED).doFilter(request, response, chain);

		verify(response, never()).setHeader(eq("Access-Control-Allow-Origin"), anyString());
	}
}
