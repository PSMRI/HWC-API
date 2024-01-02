package com.iemr.hwc.utils.exception;

public class VideoConsultationException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message = null;

	public VideoConsultationException(String message, Throwable cause) {
		super(message);
		this.message = message;
		super.setStackTrace(cause.getStackTrace());
	}

	public VideoConsultationException(String message) {
		super(message);
		this.message = message;
	}

	public String toString() {
		return this.message;
	}

	public String getMessage() {
		return this.message;
	}
}
