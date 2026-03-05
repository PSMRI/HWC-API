package com.iemr.hwc.utils.exception;

import com.iemr.hwc.utils.response.OutputResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Calendar;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IEMRException.class)
    public ResponseEntity<OutputResponse> handleIEMRException(IEMRException ex, WebRequest request) {
        logger.error("IEMRException: {}", ex.getMessage());
        OutputResponse response = new OutputResponse();
        response.setError(ex.getStatusCode(), ex.getMessage(), "FAILURE");
        return new ResponseEntity<>(response, ex.getHttpStatus());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<OutputResponse> handleIllegalArgumentException(IllegalArgumentException ex,
            WebRequest request) {
        logger.error("IllegalArgumentException: {}", ex.getMessage());
        OutputResponse response = new OutputResponse();
        response.setError(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), "BAD_REQUEST");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<OutputResponse> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("Unhandled Exception: ", ex);
        OutputResponse response = new OutputResponse();
        response.setError(ex);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (response.getStatusCode() == 404) {
            status = HttpStatus.NOT_FOUND;
        } else if (response.getStatusCode() == 400) {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(response, status);
    }
}
