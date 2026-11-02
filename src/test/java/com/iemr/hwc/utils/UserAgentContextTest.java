package com.iemr.hwc.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UserAgentContext")
class UserAgentContextTest {

	@AfterEach
	void clear() {
		UserAgentContext.clear();
	}

	@Test
	@DisplayName("hands back the user agent set on this thread")
	void holdsTheUserAgent() {
		UserAgentContext.setUserAgent("okhttp/4.9.0");

		assertThat(UserAgentContext.getUserAgent()).isEqualTo("okhttp/4.9.0");
	}

	@Test
	@DisplayName("reads as null before anything is set")
	void startsEmpty() {
		assertThat(UserAgentContext.getUserAgent()).isNull();
	}

	@Test
	@DisplayName("clear removes the value so a pooled thread cannot inherit it")
	void clearRemovesTheValue() {
		UserAgentContext.setUserAgent("okhttp/4.9.0");

		UserAgentContext.clear();

		assertThat(UserAgentContext.getUserAgent()).isNull();
	}

	@Test
	@DisplayName("one thread does not see the user agent of another")
	void isPerThread() throws Exception {
		UserAgentContext.setUserAgent("this-thread");
		ExecutorService executor = Executors.newSingleThreadExecutor();
		try {
			Callable<String> readOnOtherThread = UserAgentContext::getUserAgent;
			Future<String> other = executor.submit(readOnOtherThread);

			assertThat(other.get()).isNull();
			assertThat(UserAgentContext.getUserAgent()).isEqualTo("this-thread");
		} finally {
			executor.shutdownNow();
		}
	}
}
