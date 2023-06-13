/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.utils.exception;

public class IEMRException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message = null;

	public IEMRException(String message, Throwable cause) {
		super(message);
		this.message = message;
		super.setStackTrace(cause.getStackTrace());
	}

	public IEMRException(String message) {
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
