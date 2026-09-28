package com.iemr.hwc.utils.sessionobject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.iemr.hwc.utils.redis.RedisSessionException;
import com.iemr.hwc.utils.redis.RedisStorage;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("SessionObject")
class SessionObjectTest {

	@Mock
	private RedisStorage objectStore;

	private SessionObject sessionObject;

	@BeforeEach
	void setUp() {
		sessionObject = new SessionObject();
		sessionObject.setObjectStore(objectStore);
	}

	@Test
	@DisplayName("reads a session out of the store")
	void readsASession() throws RedisSessionException {
		when(objectStore.getObject(eq("key"), anyBoolean(), anyInt())).thenReturn("{\"userName\":\"nurse1\"}");

		assertThat(sessionObject.getSessionObject("key")).isEqualTo("{\"userName\":\"nurse1\"}");
	}

	@Test
	@DisplayName("passes a read failure on to the caller")
	void reportsAReadFailure() throws RedisSessionException {
		when(objectStore.getObject(eq("key"), anyBoolean(), anyInt()))
				.thenThrow(new RedisSessionException("session gone"));

		assertThatThrownBy(() -> sessionObject.getSessionObject("key")).isInstanceOf(RedisSessionException.class);
	}

	@Test
	@DisplayName("writes a session to the store")
	void writesASession() throws RedisSessionException {
		when(objectStore.setObject(eq("key"), any(), anyInt())).thenReturn("key");

		assertThat(sessionObject.setSessionObject("key", "{}")).isEqualTo("key");

		verify(objectStore).setObject(eq("key"), eq("{}"), anyInt());
	}

	@Test
	@DisplayName("updates the session and indexes it under the username so a concurrent login can find it")
	void indexesTheSessionByUsername() throws RedisSessionException {
		when(objectStore.updateObject(any(), any(), anyBoolean(), anyInt())).thenReturn("key");

		sessionObject.updateSessionObject("key", "{\"userName\":\"  Nurse1  \"}");

		verify(objectStore).updateObject(eq("nurse1"), eq("key"), anyBoolean(), anyInt());
		verify(objectStore).updateObject(eq("key"), eq("{\"userName\":\"  Nurse1  \"}"), anyBoolean(), anyInt());
	}

	@Test
	@DisplayName("still updates the session when the value carries no username")
	void updatesASessionWithoutAUsername() throws RedisSessionException {
		when(objectStore.updateObject(any(), any(), anyBoolean(), anyInt())).thenReturn("key");

		assertThat(sessionObject.updateSessionObject("key", "{\"other\":1}")).isEqualTo("key");

		verify(objectStore, never()).updateObject(eq("nurse1"), any(), anyBoolean(), anyInt());
	}

	@Test
	@DisplayName("still updates the session when the value is not JSON at all")
	void updatesASessionWithAMalformedValue() throws RedisSessionException {
		when(objectStore.updateObject(any(), any(), anyBoolean(), anyInt())).thenReturn("key");

		assertThat(sessionObject.updateSessionObject("key", "not-json")).isEqualTo("key");
	}

	@Test
	@DisplayName("deleting a session is accepted without reaching the store")
	void deleteIsANoOp() {
		assertThatCode(() -> sessionObject.deleteSessionObject("key")).doesNotThrowAnyException();
	}
}
