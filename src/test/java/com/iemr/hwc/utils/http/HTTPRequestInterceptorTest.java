package com.iemr.hwc.utils.http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

import com.iemr.hwc.utils.redis.RedisSessionException;
import com.iemr.hwc.utils.sessionobject.SessionObject;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("HTTPRequestInterceptor")
class HTTPRequestInterceptorTest {

	private static final String ALLOWED = "https://hwc.example.org";

	@Mock
	private SessionObject sessionObject;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;

	private HTTPRequestInterceptor interceptor;
	private ByteArrayOutputStream written;

	@BeforeEach
	void setUp() throws IOException {
		interceptor = new HTTPRequestInterceptor();
		ReflectionTestUtils.setField(interceptor, "sessionObject", sessionObject);
		ReflectionTestUtils.setField(interceptor, "allowedOrigins", ALLOWED);
		written = new ByteArrayOutputStream();
		when(response.getOutputStream()).thenReturn(new ServletOutputStream() {
			@Override
			public boolean isReady() {
				return true;
			}

			@Override
			public void setWriteListener(WriteListener writeListener) {
				// nothing to notify in a unit test
			}

			@Override
			public void write(int b) {
				written.write(b);
			}
		});
	}

	private void requestTo(String method, String uri, String authorization) {
		when(request.getMethod()).thenReturn(method);
		when(request.getRequestURI()).thenReturn(uri);
		when(request.getHeader("Authorization")).thenReturn(authorization);
	}

	@Test
	@DisplayName("lets a request with a live session through")
	void acceptsALiveSession() throws Exception {
		requestTo("POST", "/ANC/save/nurseData", "Bearer session-key");
		when(sessionObject.getSessionObject("session-key")).thenReturn("{\"userName\":\"nurse1\"}");

		assertThat(interceptor.preHandle(request, response, new Object())).isTrue();

		verify(sessionObject).getSessionObject("session-key");
	}

	@Test
	@DisplayName("reads the session key with or without the Bearer prefix")
	void acceptsABareSessionKey() throws Exception {
		requestTo("POST", "/ANC/save/nurseData", "session-key");
		when(sessionObject.getSessionObject("session-key")).thenReturn("{\"userName\":\"nurse1\"}");

		assertThat(interceptor.preHandle(request, response, new Object())).isTrue();
	}

	@Test
	@DisplayName("lets the Swagger UI through untouched")
	void skipsSwaggerUi() throws Exception {
		when(request.getRequestURI()).thenReturn("/swagger-ui/index.html");

		assertThat(interceptor.preHandle(request, response, new Object())).isTrue();

		verifyNoInteractions(sessionObject);
	}

	@Test
	@DisplayName("lets a request without an Authorization header through for the endpoint to decide")
	void skipsARequestWithoutAuthorization() throws Exception {
		requestTo("POST", "/ANC/save/nurseData", null);

		assertThat(interceptor.preHandle(request, response, new Object())).isTrue();

		verifyNoInteractions(sessionObject);
	}

	@Test
	@DisplayName("lets a request with an empty Authorization header through")
	void skipsAnEmptyAuthorization() throws Exception {
		requestTo("POST", "/ANC/save/nurseData", "");

		assertThat(interceptor.preHandle(request, response, new Object())).isTrue();

		verifyNoInteractions(sessionObject);
	}

	@Test
	@DisplayName("lets a preflight through without checking the session")
	void skipsPreflight() throws Exception {
		requestTo("OPTIONS", "/ANC/save/nurseData", "Bearer session-key");

		assertThat(interceptor.preHandle(request, response, new Object())).isTrue();

		verifyNoInteractions(sessionObject);
	}

	@Test
	@DisplayName("lets the documentation endpoints through with a session key present")
	void skipsTheDocumentationEndpoints() throws Exception {
		for (String uri : new String[] { "/x/swagger-ui.html", "/x/index.html", "/x/swagger-initializer.js",
				"/x/swagger-config", "/x/ui", "/x/swagger-resources", "/x/api-docs" }) {
			requestTo("GET", uri, "Bearer session-key");

			assertThat(interceptor.preHandle(request, response, new Object())).as("%s", uri).isTrue();
		}
		verifyNoInteractions(sessionObject);
	}

	@Test
	@DisplayName("stops a request routed to the error endpoint")
	void stopsTheErrorEndpoint() throws Exception {
		requestTo("GET", "/error", "Bearer session-key");

		assertThat(interceptor.preHandle(request, response, new Object())).isFalse();
	}

	@Test
	@DisplayName("stops a request whose session has expired and writes the error out")
	void stopsAnExpiredSession() throws Exception {
		requestTo("POST", "/ANC/save/nurseData", "Bearer session-key");
		when(sessionObject.getSessionObject("session-key")).thenThrow(new RedisSessionException("session gone"));

		assertThat(interceptor.preHandle(request, response, new Object())).isFalse();

		assertThat(written.toString()).contains("statusCode");
	}

	@Test
	@DisplayName("stops a request whose session key is unknown")
	void stopsAnUnknownSessionKey() throws Exception {
		requestTo("POST", "/ANC/save/nurseData", "Bearer session-key");
		when(sessionObject.getSessionObject("session-key")).thenReturn(null);

		assertThat(interceptor.preHandle(request, response, new Object())).isFalse();

		assertThat(written.toString()).contains("statusCode");
	}

	@Test
	@DisplayName("adds the CORS headers to an error response for an allowed origin")
	void addsCorsHeadersToAnErrorResponse() throws Exception {
		requestTo("POST", "/ANC/save/nurseData", "Bearer session-key");
		when(request.getHeader("Origin")).thenReturn(ALLOWED);
		when(sessionObject.getSessionObject("session-key")).thenReturn(null);

		interceptor.preHandle(request, response, new Object());

		verify(response).setHeader("Access-Control-Allow-Origin", ALLOWED);
		verify(response).setHeader("Access-Control-Allow-Credentials", "true");
	}

	@Test
	@DisplayName("withholds the CORS headers from an error response for a foreign origin")
	void withholdsCorsHeadersFromAForeignOrigin() throws Exception {
		requestTo("POST", "/ANC/save/nurseData", "Bearer session-key");
		when(request.getHeader("Origin")).thenReturn("https://attacker.example.net");
		when(sessionObject.getSessionObject("session-key")).thenReturn(null);

		interceptor.preHandle(request, response, new Object());

		verify(response, never()).setHeader(eq("Access-Control-Allow-Origin"), anyString());
	}

	@Test
	@DisplayName("refreshes the session after the request has been handled")
	void refreshesTheSessionAfterwards() throws Exception {
		when(request.getRequestURI()).thenReturn("/ANC/save/nurseData");
		when(request.getHeader("Authorization")).thenReturn("Bearer session-key");
		when(sessionObject.getSessionObject("session-key")).thenReturn("{\"userName\":\"nurse1\"}");

		interceptor.postHandle(request, response, new Object(), null);

		verify(sessionObject).updateSessionObject("session-key", "{\"userName\":\"nurse1\"}");
	}

	@Test
	@DisplayName("skips the refresh when the request carried no Authorization header")
	void skipsTheRefreshWithoutAuthorization() throws Exception {
		when(request.getRequestURI()).thenReturn("/ANC/save/nurseData");
		when(request.getHeader("Authorization")).thenReturn(null);

		interceptor.postHandle(request, response, new Object(), null);

		verify(sessionObject, never()).updateSessionObject(any(), any());
	}

	@Test
	@DisplayName("swallows a refresh failure so it cannot fail an already-handled request")
	void swallowsARefreshFailure() throws Exception {
		when(request.getRequestURI()).thenReturn("/ANC/save/nurseData");
		when(request.getHeader("Authorization")).thenReturn("Bearer session-key");
		when(sessionObject.getSessionObject("session-key")).thenThrow(new RedisSessionException("session gone"));

		assertThatCode(() -> interceptor.postHandle(request, response, new Object(), null))
				.doesNotThrowAnyException();
	}

	@Test
	@DisplayName("afterCompletion has nothing left to do")
	void afterCompletionIsANoOp() {
		assertThatCode(() -> interceptor.afterCompletion(request, response, new Object(), null))
				.doesNotThrowAnyException();
	}
}
