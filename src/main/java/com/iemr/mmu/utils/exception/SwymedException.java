package com.iemr.mmu.utils.exception;

public class SwymedException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message = null;

	public SwymedException(String message, Throwable cause) {
		super(message);
		this.message = message;
		super.setStackTrace(cause.getStackTrace());
	}

	public SwymedException(String message) {
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
