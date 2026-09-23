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
package com.iemr.hwc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.response.OutputResponse;

/**
 * Catches any exception that escapes individual controller try-catch blocks,
 * ensuring the API never returns an unstructured 500 or a misleading 200.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(IEMRException.class)
	public ResponseEntity<String> handleIEMRException(IEMRException e) {
		logger.error("IEMRException: {}", e.getMessage());
		OutputResponse response = new OutputResponse();
		response.setError(OutputResponse.USERID_FAILURE, e.getMessage());
		return response.toStringWithHttpStatus();
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(Exception e) {
		logger.error("Unhandled exception: {}", e.getMessage(), e);
		OutputResponse response = new OutputResponse();
		response.setError(OutputResponse.GENERIC_FAILURE, "An unexpected error occurred");
		return response.toStringWithHttpStatus();
	}
}
