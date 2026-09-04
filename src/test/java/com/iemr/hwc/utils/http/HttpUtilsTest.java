package com.iemr.hwc.utils.http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("HttpUtils")
class HttpUtilsTest {

	private static final String URI = "http://localhost:9090/api";

	@Mock
	private RestTemplate rest;

	private HttpUtils httpUtils;

	@BeforeEach
	void setUp() {
		httpUtils = new HttpUtils();
		ReflectionTestUtils.setField(httpUtils, "rest", rest);
		when(rest.exchange(any(String.class), any(HttpMethod.class), any(), eq(String.class)))
				.thenReturn(new ResponseEntity<>("{\"ok\":true}", HttpStatus.OK));
	}

	@SuppressWarnings("unchecked")
	private HttpEntity<String> capturedRequest() {
		ArgumentCaptor<HttpEntity<String>> request = ArgumentCaptor.forClass(HttpEntity.class);
		org.mockito.Mockito.verify(rest).exchange(eq(URI), any(HttpMethod.class), request.capture(),
				eq(String.class));
		return request.getValue();
	}

	@Test
	@DisplayName("a plain GET returns the body and records the status")
	void performsAPlainGet() {
		assertThat(httpUtils.get(URI)).isEqualTo("{\"ok\":true}");
		assertThat(httpUtils.getStatus()).isEqualTo(HttpStatus.OK);
	}

	@Test
	@DisplayName("a GET forwards the Authorization and Content-Type it is given")
	void forwardsTheGivenHeaders() {
		HashMap<String, Object> header = new HashMap<>();
		header.put(HttpHeaders.AUTHORIZATION, "Bearer token");
		header.put(HttpHeaders.CONTENT_TYPE, "application/xml");

		assertThat(httpUtils.get(URI, header)).isEqualTo("{\"ok\":true}");

		HttpHeaders sent = capturedRequest().getHeaders();
		assertThat(sent.getFirst(HttpHeaders.AUTHORIZATION)).isEqualTo("Bearer token");
		assertThat(sent.getFirst(HttpHeaders.CONTENT_TYPE)).isEqualTo("application/xml");
	}

	@Test
	@DisplayName("a GET defaults the Content-Type to JSON when none is given")
	void defaultsTheContentTypeToJson() {
		httpUtils.get(URI, new HashMap<>());

		assertThat(capturedRequest().getHeaders().getFirst("Content-Type")).isEqualTo("application/json");
	}

	@Test
	@DisplayName("a plain POST sends the body and records the status")
	void performsAPlainPost() {
		assertThat(httpUtils.post(URI, "{\"benRegID\":42}")).isEqualTo("{\"ok\":true}");

		assertThat(capturedRequest().getBody()).isEqualTo("{\"benRegID\":42}");
		assertThat(httpUtils.getStatus()).isEqualTo(HttpStatus.OK);
	}

	@Test
	@DisplayName("a POST forwards the Authorization it is given")
	void postForwardsAuthorization() {
		HashMap<String, Object> header = new HashMap<>();
		header.put(HttpHeaders.AUTHORIZATION, "Bearer token");

		assertThat(httpUtils.post(URI, "{}", header)).isEqualTo("{\"ok\":true}");

		assertThat(capturedRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION)).isEqualTo("Bearer token");
	}

	@Test
	@DisplayName("a POST without an Authorization header sends none")
	void postWithoutAuthorization() {
		httpUtils.post(URI, "{}", new HashMap<>());

		assertThat(capturedRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION)).isNull();
	}

	@Test
	@DisplayName("the response-entity POST hands the whole response back and forwards the API key")
	void postWithResponseEntityForwardsTheApiKey() {
		HashMap<String, Object> header = new HashMap<>();
		header.put(HttpHeaders.AUTHORIZATION, "Bearer token");
		header.put("apiKey", "an-api-key");

		ResponseEntity<String> response = httpUtils.postWithResponseEntity(URI, "{}", header);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo("{\"ok\":true}");
		HttpHeaders sent = capturedRequest().getHeaders();
		assertThat(sent.getFirst("apiKey")).isEqualTo("an-api-key");
		assertThat(sent.getFirst("Content-Type")).isEqualTo("application/json");
	}

	@Test
	@DisplayName("the response-entity POST always sets a JSON content type, even with no headers given")
	void postWithResponseEntitySetsJson() {
		httpUtils.postWithResponseEntity(URI, "{}", new HashMap<>());

		assertThat(capturedRequest().getHeaders().getFirst("Content-Type")).isEqualTo("application/json");
	}

	@Test
	@DisplayName("the recorded status can be set directly")
	void statusIsSettable() {
		httpUtils.setStatus(HttpStatus.SERVICE_UNAVAILABLE);

		assertThat(httpUtils.getStatus()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
	}
}
