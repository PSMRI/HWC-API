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
package com.iemr.hwc.utils.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.exception.TMException;

class OutputResponseTest {

	@Test
	void badRequestConstantIsRfcCompliant() {
		assertEquals(400, OutputResponse.BAD_REQUEST,
				"BAD_REQUEST must be 400 per RFC 9110; the legacy value 404 conflated bad-request and not-found.");
	}

	@Test
	void notFoundConstantPreservesPreviousMagicNumber() {
		assertEquals(404, OutputResponse.NOT_FOUND,
				"Existing callers using the literal 404 for not-found semantics must continue to resolve to NOT_FOUND.");
	}

	@Test
	void swymedAndTmExceptionCodesAreDistinct() {
		assertNotEquals(OutputResponse.SWYMED_EXCEPTION, OutputResponse.TM_EXCEPTION,
				"SWYMED_EXCEPTION and TM_EXCEPTION must be distinguishable so clients can route on them.");
	}

	@Test
	void successResponseMapsToHttp200() {
		OutputResponse response = new OutputResponse();
		response.setResponse("ok");
		ResponseEntity<String> entity = response.toStringWithHttpStatus();
		assertEquals(HttpStatus.OK, entity.getStatusCode());
	}

	@Test
	void badRequestStatusCodeMapsToHttp400() {
		OutputResponse response = new OutputResponse();
		response.setError(OutputResponse.BAD_REQUEST, "bad payload");
		ResponseEntity<String> entity = response.toStringWithHttpStatus();
		assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());
	}

	@Test
	void notFoundStatusCodeMapsToHttp404() {
		OutputResponse response = new OutputResponse();
		response.setError(OutputResponse.NOT_FOUND, "record missing");
		ResponseEntity<String> entity = response.toStringWithHttpStatus();
		assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
	}

	@Test
	void legacy404MagicNumberStillMapsToHttp404() {
		OutputResponse response = new OutputResponse();
		response.setError(404, "village not found");
		ResponseEntity<String> entity = response.toStringWithHttpStatus();
		assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode(),
				"Existing controllers call setError(404, ...) directly for not-found semantics.");
	}

	@Test
	void userIdFailureMapsToHttp401() {
		OutputResponse response = new OutputResponse();
		response.setError(OutputResponse.USERID_FAILURE, "auth failed");
		assertEquals(HttpStatus.UNAUTHORIZED, response.toStringWithHttpStatus().getStatusCode());
	}

	@Test
	void privilegeFailureMapsToHttp403() {
		OutputResponse response = new OutputResponse();
		response.setError(OutputResponse.PREVILAGE_FAILURE, "forbidden");
		assertEquals(HttpStatus.FORBIDDEN, response.toStringWithHttpStatus().getStatusCode());
	}

	@Test
	void environmentExceptionMapsToHttp503() {
		OutputResponse response = new OutputResponse();
		response.setError(OutputResponse.ENVIRONMENT_EXCEPTION, "db down");
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.toStringWithHttpStatus().getStatusCode());
	}

	@Test
	void swymedExceptionMapsToHttp502() {
		OutputResponse response = new OutputResponse();
		response.setError(OutputResponse.SWYMED_EXCEPTION, "upstream failed");
		assertEquals(HttpStatus.BAD_GATEWAY, response.toStringWithHttpStatus().getStatusCode());
	}

	@Test
	void tmExceptionMapsToHttp400() {
		OutputResponse response = new OutputResponse();
		response.setError(OutputResponse.TM_EXCEPTION, "invalid input");
		assertEquals(HttpStatus.BAD_REQUEST, response.toStringWithHttpStatus().getStatusCode(),
				"TMException semantically means 'Invalid input' per OutputResponse.setError(Throwable).");
	}

	@Test
	void genericFailureMapsToHttp500() {
		OutputResponse response = new OutputResponse();
		response.setError(OutputResponse.GENERIC_FAILURE, "boom");
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.toStringWithHttpStatus().getStatusCode());
	}

	@Test
	void codeExceptionMapsToHttp500() {
		OutputResponse response = new OutputResponse();
		response.setError(OutputResponse.CODE_EXCEPTION, "npe");
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.toStringWithHttpStatus().getStatusCode());
	}

	@Test
	void parseExceptionMapsToHttp400() {
		OutputResponse response = new OutputResponse();
		response.setError(OutputResponse.PARSE_EXCEPTION, "bad json");
		assertEquals(HttpStatus.BAD_REQUEST, response.toStringWithHttpStatus().getStatusCode());
	}

	@Test
	void unknownStatusCodeFallsBackToHttp500() {
		OutputResponse response = new OutputResponse();
		response.setError(9999, "mystery");
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.toStringWithHttpStatus().getStatusCode(),
				"Unknown internal codes should default to 500, not 503 — they signal server bugs.");
	}

	@Test
	void setErrorWithIemrExceptionRoutesToUnauthorizedHttpStatus() {
		OutputResponse response = new OutputResponse();
		response.setError(new IEMRException("bad credentials"));
		assertEquals(OutputResponse.USERID_FAILURE, response.getStatusCode());
		assertEquals(HttpStatus.UNAUTHORIZED, response.toStringWithHttpStatus().getStatusCode());
	}

	@Test
	void setErrorWithTmExceptionRoutesToBadRequestHttpStatus() {
		OutputResponse response = new OutputResponse();
		response.setError(new TMException("invalid payload"));
		assertEquals(OutputResponse.TM_EXCEPTION, response.getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST, response.toStringWithHttpStatus().getStatusCode());
	}

	@Test
	void responseBodyShapeIsPreservedAcrossHttpStatusEnvelope() {
		OutputResponse response = new OutputResponse();
		response.setError(OutputResponse.BAD_REQUEST, "bad payload");
		String body = response.toStringWithHttpStatus().getBody();
		assertTrue(body != null && body.contains("\"statusCode\":400"),
				"Body still carries the OutputResponse JSON envelope so existing UI parsers keep working.");
	}
}
