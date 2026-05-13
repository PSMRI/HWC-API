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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.iemr.hwc.utils.response.OutputResponse;

/**
 * Centralised exception translation for HWC-API REST controllers. Every
 * uncaught exception is converted into an {@link OutputResponse} body with a
 * correct HTTP status code instead of the legacy behaviour of returning HTTP
 * 200 with an error embedded in the body. Exception messages are deliberately
 * not echoed into log messages because they may contain values originating
 * from beneficiary payloads; the stack trace is still captured by SLF4J for
 * server-side diagnosis.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	private static final String GENERIC_BAD_REQUEST = "Invalid request";
	private static final String GENERIC_SERVER_ERROR = "Unexpected server error";

	@ExceptionHandler(IEMRException.class)
	public ResponseEntity<String> handleIEMRException(IEMRException ex) {
		LOGGER.error("IEMRException raised at controller boundary", ex);
		OutputResponse response = new OutputResponse();
		response.setError(ex);
		return response.toStringWithHttpStatus();
	}

	@ExceptionHandler(TMException.class)
	public ResponseEntity<String> handleTMException(TMException ex) {
		LOGGER.error("TMException raised at controller boundary", ex);
		OutputResponse response = new OutputResponse();
		response.setError(ex);
		return response.toStringWithHttpStatus();
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
		LOGGER.error("Malformed request body received", ex);
		OutputResponse response = new OutputResponse();
		response.setError(OutputResponse.BAD_REQUEST, GENERIC_BAD_REQUEST);
		return response.toStringWithHttpStatus();
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<String> handleMissingParameter(MissingServletRequestParameterException ex) {
		LOGGER.error("Missing required request parameter", ex);
		OutputResponse response = new OutputResponse();
		response.setError(OutputResponse.BAD_REQUEST, "Missing required parameter: " + ex.getParameterName());
		return response.toStringWithHttpStatus();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleValidation(MethodArgumentNotValidException ex) {
		LOGGER.error("Request payload failed validation", ex);
		OutputResponse response = new OutputResponse();
		response.setError(OutputResponse.BAD_REQUEST, "Request payload failed validation");
		return response.toStringWithHttpStatus();
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<String> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
		LOGGER.error("Unsupported HTTP method", ex);
		OutputResponse response = new OutputResponse();
		response.setError(OutputResponse.BAD_REQUEST, "HTTP method not supported");
		return response.toStringWithHttpStatus();
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleAny(Exception ex) {
		LOGGER.error("Unhandled exception at controller boundary", ex);
		OutputResponse response = new OutputResponse();
		response.setError(OutputResponse.GENERIC_FAILURE, GENERIC_SERVER_ERROR);
		return response.toStringWithHttpStatus();
	}
}
