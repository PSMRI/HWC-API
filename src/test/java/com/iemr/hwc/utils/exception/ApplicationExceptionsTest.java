package com.iemr.hwc.utils.exception;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * The three application exceptions share a shape: they carry a message, optionally wrap a
 * cause, and print as their own class name followed by the message.
 */
@DisplayName("application exceptions")
class ApplicationExceptionsTest {

	static Stream<Arguments> exceptions() {
		return Stream.of(Arguments.of(new IEMRException("went wrong")), Arguments.of(new TMException("went wrong")),
				Arguments.of(new VideoConsultationException("went wrong")));
	}

	static Stream<Arguments> wrappingExceptions() {
		Throwable cause = new IllegalStateException("root cause");
		return Stream.of(Arguments.of(new IEMRException("went wrong", cause), cause),
				Arguments.of(new TMException("went wrong", cause), cause),
				Arguments.of(new VideoConsultationException("went wrong", cause), cause));
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("exceptions")
	@DisplayName("carries the message it was raised with")
	void carriesItsMessage(Exception thrown) {
		assertThat(thrown.getMessage()).contains("went wrong");
		assertThat(thrown).isInstanceOf(Exception.class);
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("exceptions")
	@DisplayName("prints as its message alone, so a response built from it carries no class name")
	void printsItsMessageAlone(Exception thrown) {
		assertThat(thrown.toString()).isEqualTo("went wrong");
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("wrappingExceptions")
	@DisplayName("adopts the stack trace of the failure it wraps, rather than chaining it as a cause")
	void adoptsTheStackTraceOfItsCause(Exception thrown, Throwable cause) {
		assertThat(thrown.getMessage()).isEqualTo("went wrong");
		assertThat(thrown.getStackTrace()).isEqualTo(cause.getStackTrace());
		// The two-argument constructor calls setStackTrace rather than super(message, cause),
		// so getCause stays null and only the trace points back at the original failure.
		assertThat(thrown.getCause()).isNull();
	}
}
