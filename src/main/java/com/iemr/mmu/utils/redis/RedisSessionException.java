/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.utils.redis;

import com.iemr.mmu.utils.exception.IEMRException;

public class RedisSessionException extends IEMRException {
	public RedisSessionException(String message, Throwable cause) {
		super(message, cause);
	}

	public RedisSessionException(String message) {
		super(message);
	}
}
