/*
* AMRIT – Accessible Medical Records via Integrated Technology 
* Integrated EHR (Electronic Health Records) Solution 
*
* Copyright (C) "Piramal Swasthya Management and Research Institute" 
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
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
	private RestTemplate rest;
	private HttpHeaders headers;
	private HttpStatus status;

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
		HttpHeaders headers = new HttpHeaders();
		if (header.containsKey(HttpHeaders.AUTHORIZATION)) {
			headers.add(HttpHeaders.AUTHORIZATION, header.get(HttpHeaders.AUTHORIZATION).toString());
		}
		HttpEntity<String> requestEntity = new HttpEntity<String>(data, headers);
		ResponseEntity<String> responseEntity = rest.exchange(uri, HttpMethod.POST, requestEntity, String.class);
		setStatus(responseEntity.getStatusCode());
		return responseEntity.getBody();
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
		HttpEntity<String> requestEntity = new HttpEntity<String>(data, headers);
		ResponseEntity<String> responseEntity = rest.exchange(uri, HttpMethod.POST, requestEntity, String.class);
		return responseEntity;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}