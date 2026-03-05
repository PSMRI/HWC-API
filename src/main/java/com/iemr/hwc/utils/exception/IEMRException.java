package com.iemr.hwc.utils.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public class IEMRException extends RuntimeException {
	private final int statusCode;
	private final HttpStatus httpStatus;

	public IEMRException(String message) {
		super(message);
		this.statusCode = 5000;
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public IEMRException(String message, int statusCode, HttpStatus httpStatus) {
		super(message);
		this.statusCode = statusCode;
		this.httpStatus = httpStatus;
	}

	public IEMRException(String message, Throwable cause) {
		super(message, cause);
		this.statusCode = 5000;
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}
}
