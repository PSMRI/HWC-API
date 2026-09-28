package com.iemr.hwc.utils.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.redis.RedisSessionException;
import com.iemr.hwc.utils.sessionobject.SessionObject;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Validator")
class ValidatorTest {

	@Mock
	private SessionObject session;

	private Validator validator;

	@BeforeEach
	void setUp() {
		validator = new Validator();
		validator.setSessionObject(session);
	}

	private static JSONObject loginResponse(String ip) {
		return new JSONObject().put("loginIPAddress", ip).put("userName", "nurse1");
	}

	@Test
	@DisplayName("creates a session for a first login and reports success")
	void createsASessionForAFirstLogin() throws Exception {
		when(session.getSessionObject("key")).thenReturn(null);

		JSONObject updated = validator.updateCacheObj(loginResponse("10.0.0.1"), "key", "ipKey");

		assertThat(updated.getString("sessionStatus")).isEqualTo("login success");
		assertThat(updated.getString("key")).isEqualTo("key");
		verify(session).setSessionObject(eq("key"), any());
	}

	@Test
	@DisplayName("keeps the session when the same IP logs in again")
	void keepsTheSessionForTheSameIp() throws Exception {
		when(session.getSessionObject("key")).thenReturn(loginResponse("10.0.0.1").toString());

		JSONObject updated = validator.updateCacheObj(loginResponse("10.0.0.1"), "key", "ipKey");

		assertThat(updated.getString("sessionStatus")).isEqualTo("login success");
		verify(session).setSessionObject(eq("key"), any());
	}

	@Test
	@DisplayName("reports the other address and discards the response when the user is logged in elsewhere")
	void reportsALoginFromAnotherAddress() throws Exception {
		when(session.getSessionObject("key")).thenReturn(loginResponse("10.0.0.9").toString());

		JSONObject updated = validator.updateCacheObj(loginResponse("10.0.0.1"), "key", "ipKey");

		assertThat(updated.getString("sessionStatus")).contains("logged in from 10.0.0.9");
		assertThat(updated.has("userName")).as("the response of a rejected login should not be handed back")
				.isFalse();
		verify(session, never()).setSessionObject(any(), any());
	}

	@Test
	@DisplayName("treats an unreachable session store as a first login")
	void treatsAStoreFailureAsAFirstLogin() throws Exception {
		when(session.getSessionObject("key")).thenThrow(new RedisSessionException("session gone"));

		JSONObject updated = validator.updateCacheObj(loginResponse("10.0.0.1"), "key", "ipKey");

		assertThat(updated.getString("sessionStatus")).isEqualTo("login success");
	}

	@Test
	@DisplayName("leaves the session status at the failure value when the session cannot be written")
	void reportsAFailedWrite() throws Exception {
		when(session.getSessionObject("key")).thenReturn(null);
		when(session.setSessionObject(any(), any())).thenThrow(new RedisSessionException("write failed"));

		JSONObject updated = validator.updateCacheObj(loginResponse("10.0.0.1"), "key", "ipKey");

		assertThat(updated.getString("sessionStatus")).isEqualTo("session creation failed");
	}

	@Test
	@DisplayName("reads a session straight out of the store")
	void readsASession() throws Exception {
		when(session.getSessionObject("key")).thenReturn("{\"a\":1}");

		assertThat(validator.getSessionObject("key")).isEqualTo("{\"a\":1}");
	}

	@Test
	@DisplayName("accepts a login key that has a live session")
	void acceptsALiveKey() throws Exception {
		when(session.getSessionObject("key")).thenReturn(loginResponse("10.0.0.1").toString());

		assertThatCode(() -> validator.checkKeyExists("key", "10.0.0.1")).doesNotThrowAnyException();
	}

	@Test
	@DisplayName("accepts a live session even from a different address, since IP validation is off")
	void ignoresTheAddressWhileIpValidationIsOff() throws Exception {
		when(session.getSessionObject("key")).thenReturn(loginResponse("10.0.0.9").toString());

		assertThatCode(() -> validator.checkKeyExists("key", "10.0.0.1")).doesNotThrowAnyException();
	}

	@Test
	@DisplayName("rejects a login key with no session behind it")
	void rejectsAnExpiredKey() throws Exception {
		when(session.getSessionObject("key")).thenThrow(new RedisSessionException("session gone"));

		assertThatThrownBy(() -> validator.checkKeyExists("key", "10.0.0.1")).isInstanceOf(IEMRException.class)
				.hasMessageContaining("Invalid login key or session is expired");
	}

	@Test
	@DisplayName("rejects a login key whose session is not readable as JSON")
	void rejectsAMalformedSession() throws Exception {
		when(session.getSessionObject("key")).thenReturn("not-json");

		assertThatThrownBy(() -> validator.checkKeyExists("key", "10.0.0.1")).isInstanceOf(IEMRException.class);
	}
}
