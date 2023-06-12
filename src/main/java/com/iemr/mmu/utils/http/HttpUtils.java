package com.iemr.mmu.utils.http;

import java.util.HashMap;

import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpUtils {
	public static final String AUTHORIZATION = "Authorization";
	// @Autowired
	private RestTemplate rest;
	// @Autowired
	private HttpHeaders headers;
	// @Autowired
	private HttpStatus status;

	// @Autowired(required = true)
	// @Qualifier("hibernateCriteriaBuilder")
	public HttpUtils() {
		if (rest == null) {
			rest = new RestTemplate();
			headers = new HttpHeaders();
		}
	}

	public String get(String uri) {
		String body;
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<String> responseEntity = rest.exchange(uri, HttpMethod.GET, requestEntity, String.class);
		setStatus(responseEntity.getStatusCode());
		body = responseEntity.getBody();
		return body;
	}

	public String get(String uri, HashMap<String, Object> header) {
		String body;
		HttpHeaders headers = new HttpHeaders();
		if (header.containsKey(HttpHeaders.AUTHORIZATION)) {
			headers.add(HttpHeaders.AUTHORIZATION, header.get(HttpHeaders.AUTHORIZATION).toString());
		}
		if (header.containsKey(HttpHeaders.CONTENT_TYPE)) {
			headers.add(HttpHeaders.CONTENT_TYPE, header.get(HttpHeaders.CONTENT_TYPE).toString());
		} else {
			headers.add("Content-Type", MediaType.APPLICATION_JSON);
		}
		HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
		ResponseEntity<String> responseEntity = rest.exchange(uri, HttpMethod.GET, requestEntity, String.class);
		setStatus(responseEntity.getStatusCode());
		body = responseEntity.getBody();
		return body;
	}

	public String post(String uri, String json) {
		String body;
		HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> responseEntity = rest.exchange(uri, HttpMethod.POST, requestEntity, String.class);
		setStatus(responseEntity.getStatusCode());
		body = responseEntity.getBody();
		return body;
	}

	public String post(String uri, String data, HashMap<String, Object> header) {
		String body;
		HttpHeaders headers = new HttpHeaders();
		if (header.containsKey(HttpHeaders.AUTHORIZATION)) {
			headers.add(HttpHeaders.AUTHORIZATION, header.get(HttpHeaders.AUTHORIZATION).toString());
		}
		// headers.add("Content-Type", MediaType.APPLICATION_JSON);
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		HttpEntity<String> requestEntity;
		requestEntity = new HttpEntity<String>(data, headers);
		responseEntity = rest.exchange(uri, HttpMethod.POST, requestEntity, String.class);
		setStatus(responseEntity.getStatusCode());
		body = responseEntity.getBody();
		return body;
	}

	public ResponseEntity<String> postWithResponseEntity(String uri, String data, HashMap<String, Object> header) {
		HttpHeaders headers = new HttpHeaders();
		if (header.containsKey(HttpHeaders.AUTHORIZATION)) {
			headers.add(HttpHeaders.AUTHORIZATION, header.get(HttpHeaders.AUTHORIZATION).toString());
		}
		// for fetosense api
		if (header.containsKey("apiKey")) {
			headers.add("apiKey", header.get("apiKey").toString());
		}
		headers.add("Content-Type", MediaType.APPLICATION_JSON);
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		HttpEntity<String> requestEntity;
		requestEntity = new HttpEntity<String>(data, headers);
		responseEntity = rest.exchange(uri, HttpMethod.POST, requestEntity, String.class);
		return responseEntity;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}