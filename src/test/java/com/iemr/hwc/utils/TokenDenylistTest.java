package com.iemr.hwc.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("TokenDenylist")
class TokenDenylistTest {

	@Mock
	private RedisTemplate<String, Object> redisTemplate;
	@Mock
	private ValueOperations<String, Object> valueOperations;
	@InjectMocks
	private TokenDenylist tokenDenylist;

	@BeforeEach
	void stubRedis() {
		when(redisTemplate.opsForValue()).thenReturn(valueOperations);
	}

	@Test
	@DisplayName("stores the jti under the denylist prefix for the lifetime of the token")
	void storesTheJti() {
		tokenDenylist.addTokenToDenylist("jti-1", 60_000L);

		verify(valueOperations).set(eq("denied_jti-1"), any(), eq(60_000L), eq(TimeUnit.MILLISECONDS));
	}

	@ParameterizedTest
	@NullSource
	@ValueSource(strings = { "", "   " })
	@DisplayName("ignores a blank jti rather than writing an empty key")
	void ignoresABlankJti(String jti) {
		tokenDenylist.addTokenToDenylist(jti, 60_000L);

		verifyNoInteractions(valueOperations);
	}

	@Test
	@DisplayName("rejects an expiry that would denylist the token forever")
	void rejectsANonPositiveExpiry() {
		assertThatThrownBy(() -> tokenDenylist.addTokenToDenylist("jti-1", 0L))
				.isInstanceOf(IllegalArgumentException.class).hasMessageContaining("positive");
		assertThatThrownBy(() -> tokenDenylist.addTokenToDenylist("jti-1", null))
				.isInstanceOf(IllegalArgumentException.class);
		verify(valueOperations, never()).set(any(), any(), anyLong(), any());
	}

	@Test
	@DisplayName("surfaces a Redis write failure to the caller")
	void reportsAFailedWrite() {
		doThrow(new IllegalStateException("redis down")).when(valueOperations).set(any(), any(), anyLong(), any());

		assertThatThrownBy(() -> tokenDenylist.addTokenToDenylist("jti-1", 60_000L))
				.isInstanceOf(RuntimeException.class).hasMessageContaining("Failed to denylist token");
	}

	@Test
	@DisplayName("reports a token as denylisted when the key is present")
	void reportsADenylistedToken() {
		when(redisTemplate.hasKey("denied_jti-1")).thenReturn(true);

		assertThat(tokenDenylist.isTokenDenylisted("jti-1")).isTrue();
	}

	@Test
	@DisplayName("reports a token as usable when the key is absent")
	void reportsAnAbsentToken() {
		when(redisTemplate.hasKey("denied_jti-1")).thenReturn(false);

		assertThat(tokenDenylist.isTokenDenylisted("jti-1")).isFalse();
	}

	@ParameterizedTest
	@NullSource
	@ValueSource(strings = { "", "   " })
	@DisplayName("treats a blank jti as not denylisted without asking Redis")
	void blankJtiIsNotDenylisted(String jti) {
		assertThat(tokenDenylist.isTokenDenylisted(jti)).isFalse();

		verify(redisTemplate, never()).hasKey(any());
	}

	@Test
	@DisplayName("lets requests through when Redis cannot be reached, rather than blocking every caller")
	void redisFailureDoesNotBlockRequests() {
		when(redisTemplate.hasKey(any())).thenThrow(new IllegalStateException("redis down"));

		assertThat(tokenDenylist.isTokenDenylisted("jti-1")).isFalse();
	}
}
