package com.iemr.hwc.utils.redis;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RedisSessionException")
class RedisSessionExceptionTest {

	@Test
	@DisplayName("carries the message it was raised with")
	void carriesItsMessage() {
		RedisSessionException thrown = new RedisSessionException("session gone");

		assertThat(thrown).hasMessage("session gone").isInstanceOf(Exception.class);
	}
}
