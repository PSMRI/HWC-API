package com.iemr.hwc.utils.redis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("RedisStorage")
class RedisStorageTest {

	private static final String KEY = "session-key";

	@Mock
	private LettuceConnectionFactory connectionFactory;
	@Mock
	private RedisConnection connection;

	private RedisStorage storage;

	@BeforeEach
	void setUp() {
		storage = new RedisStorage();
		ReflectionTestUtils.setField(storage, "connection", connectionFactory);
		when(connectionFactory.getConnection()).thenReturn(connection);
	}

	private void keyHolds(String value) {
		when(connection.get(KEY.getBytes())).thenReturn(value == null ? null : value.getBytes(StandardCharsets.UTF_8));
	}

	@Test
	@DisplayName("writes a session when the key is not taken yet")
	void writesANewSession() throws RedisSessionException {
		keyHolds(null);

		assertThat(storage.setObject(KEY, "{\"userName\":\"nurse1\"}", 900)).isEqualTo(KEY);

		verify(connection).set(eq(KEY.getBytes()), eq("{\"userName\":\"nurse1\"}".getBytes()),
				eq(Expiration.seconds(900)), eq(SetOption.UPSERT));
	}

	@Test
	@DisplayName("leaves an existing session in place rather than overwriting it")
	void doesNotOverwriteAnExistingSession() throws RedisSessionException {
		keyHolds("{\"userName\":\"already-here\"}");

		assertThat(storage.setObject(KEY, "{\"userName\":\"nurse1\"}", 900)).isEqualTo(KEY);

		verify(connection, never()).set(any(), any(), any(), any());
	}

	@Test
	@DisplayName("writes a session when the stored value is empty")
	void writesOverAnEmptyValue() throws RedisSessionException {
		keyHolds("");

		storage.setObject(KEY, "{\"userName\":\"nurse1\"}", 900);

		verify(connection).set(any(), any(), any(), any());
	}

	@Test
	@DisplayName("reads a session back and extends its expiry")
	void readsAndExtendsASession() throws RedisSessionException {
		keyHolds("{\"userName\":\"nurse1\"}");

		assertThat(storage.getObject(KEY, true, 900)).isEqualTo("{\"userName\":\"nurse1\"}");

		verify(connection).expire(KEY.getBytes(), 900);
	}

	@Test
	@DisplayName("reports a missing session rather than returning null")
	void reportsAMissingSession() {
		keyHolds(null);

		assertThatThrownBy(() -> storage.getObject(KEY, true, 900)).isInstanceOf(RedisSessionException.class)
				.hasMessageContaining("Unable to fetch session object");
	}

	@Test
	@DisplayName("reports a blank session as missing")
	void reportsABlankSessionAsMissing() {
		keyHolds("   ");

		assertThatThrownBy(() -> storage.getObject(KEY, true, 900)).isInstanceOf(RedisSessionException.class);
	}

	@Test
	@DisplayName("updates a session that exists and refreshes its expiry")
	void updatesAnExistingSession() throws RedisSessionException {
		keyHolds("{\"userName\":\"nurse1\"}");

		assertThat(storage.updateObject(KEY, "{\"userName\":\"nurse2\"}", true, 900)).isEqualTo(KEY);

		verify(connection).set(eq(KEY.getBytes()), eq("{\"userName\":\"nurse2\"}".getBytes()),
				eq(Expiration.seconds(900)), eq(SetOption.UPSERT));
	}

	@Test
	@DisplayName("refuses to update a session that is not there")
	void refusesToUpdateAMissingSession() {
		keyHolds(null);

		assertThatThrownBy(() -> storage.updateObject(KEY, "{}", true, 900))
				.isInstanceOf(RedisSessionException.class).hasMessageContaining("Unable to fetch session object");
	}

	@Test
	@DisplayName("deletes a session and reports how many keys went")
	void deletesASession() throws RedisSessionException {
		when(connection.del(KEY.getBytes())).thenReturn(1L);

		assertThat(storage.deleteObject(KEY)).isEqualTo(1L);
	}

	@Test
	@DisplayName("reports zero when there was nothing to delete")
	void deletesNothing() throws RedisSessionException {
		when(connection.del(KEY.getBytes())).thenReturn(0L);

		assertThat(storage.deleteObject(KEY)).isZero();
	}
}
