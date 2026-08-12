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
package com.iemr.hwc.utils.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.BeanPropertyBindingResult;

class GlobalExceptionHandlerTest {

	private GlobalExceptionHandler handler;

	@BeforeEach
	void setUp() {
		handler = new GlobalExceptionHandler();
	}

	@Test
	void classIsAnnotatedSoSpringWillRegisterIt() {
		assertNotNull(GlobalExceptionHandler.class.getAnnotation(RestControllerAdvice.class),
				"@RestControllerAdvice is required for Spring to pick the handler up via component scan.");
	}

	@Test
	void iemrExceptionMapsToHttp401WithGenericBody() {
		ResponseEntity<String> response = handler.handleIEMRException(new IEMRException("password rejected"));
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertFalse(response.getStatusCode().is2xxSuccessful(),
				"Pre-fix behaviour returned HTTP 200; the regression we must guard against.");
	}

	@Test
	void tmExceptionMapsToHttp400() {
		ResponseEntity<String> response = handler.handleTMException(new TMException("ben-id mismatch"));
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	void httpMessageNotReadableMapsToHttp400AndDoesNotEchoRawBody() {
		String sensitiveBody = "{\"aadhaarNo\":\"123412341234\"";
		HttpMessageNotReadableException ex = new HttpMessageNotReadableException(
				"unreadable", new MockHttpInputMessage(sensitiveBody.getBytes()));
		ResponseEntity<String> response = handler.handleHttpMessageNotReadable(ex);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		String body = response.getBody();
		assertNotNull(body);
		assertFalse(body.contains("123412341234"),
				"Sensitive bytes from the failed parse must not be reflected back to the caller.");
	}

	@Test
	void missingParameterMapsToHttp400AndNamesTheParameter() {
		ResponseEntity<String> response = handler.handleMissingParameter(
				new MissingServletRequestParameterException("benRegID", "Long"));
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody() != null && response.getBody().contains("benRegID"));
	}

	@Test
	void validationFailureMapsToHttp400() throws NoSuchMethodException {
		Object target = new Object();
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(target, "target");
		MethodParameter parameter = new MethodParameter(
				GlobalExceptionHandlerTest.class.getDeclaredMethod("validationDummy", String.class), 0);
		MethodArgumentNotValidException ex = new MethodArgumentNotValidException(parameter, bindingResult);
		ResponseEntity<String> response = handler.handleValidation(ex);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	void methodNotSupportedMapsToHttp400() {
		ResponseEntity<String> response = handler.handleMethodNotSupported(
				new HttpRequestMethodNotSupportedException("DELETE"));
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	void uncaughtExceptionMapsToHttp500AndHidesInternalDetails() {
		ResponseEntity<String> response = handler.handleAny(new RuntimeException("npe at line 42 with patientId=999"));
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		String body = response.getBody();
		assertNotNull(body);
		assertFalse(body.contains("patientId=999"),
				"Raw exception messages can carry PII; the body must use the generic fallback.");
		assertFalse(body.contains("npe at line 42"),
				"Stack-trace-flavoured detail must not be echoed to API consumers.");
	}

	@SuppressWarnings("unused")
	private void validationDummy(String ignored) {
		// reflective target for MethodArgumentNotValidException construction
	}
}
