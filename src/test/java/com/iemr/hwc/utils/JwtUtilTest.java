package com.iemr.hwc.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

import com.iemr.hwc.utils.exception.IEMRException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("JwtUtil")
class JwtUtilTest {

	private static final String SECRET = "a-test-signing-secret-of-at-least-32-bytes";
	private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

	@Mock
	private TokenDenylist tokenDenylist;

	private JwtUtil jwtUtil;

	@BeforeEach
	void setUp() {
		jwtUtil = new JwtUtil();
		ReflectionTestUtils.setField(jwtUtil, "SECRET_KEY", SECRET);
		ReflectionTestUtils.setField(jwtUtil, "tokenDenylist", tokenDenylist);
	}

	private static String token(String subject, Map<String, Object> claims, String jti) {
		var builder = Jwts.builder().subject(subject).claims(claims)
				.expiration(new Date(System.currentTimeMillis() + 600_000));
		if (jti != null) {
			builder.id(jti);
		}
		return builder.signWith(KEY).compact();
	}

	@Test
	@DisplayName("accepts a token signed with the configured secret")
	void validatesAWellSignedToken() {
		String jwt = token("nurse1", Map.of("userId", "42"), null);

		Claims claims = jwtUtil.validateToken(jwt);

		assertThat(claims).isNotNull();
		assertThat(claims.getSubject()).isEqualTo("nurse1");
		assertThat(claims.get("userId", String.class)).isEqualTo("42");
	}

	@Test
	@DisplayName("rejects a token signed with a different secret")
	void rejectsAForeignSignature() {
		String foreign = Jwts.builder().subject("nurse1")
				.signWith(Keys.hmacShaKeyFor("a-completely-different-secret-32b".getBytes())).compact();

		assertThat(jwtUtil.validateToken(foreign)).isNull();
	}

	@Test
	@DisplayName("rejects a token that has expired")
	void rejectsAnExpiredToken() {
		String expired = Jwts.builder().subject("nurse1").expiration(new Date(System.currentTimeMillis() - 1000))
				.signWith(KEY).compact();

		assertThat(jwtUtil.validateToken(expired)).isNull();
	}

	@Test
	@DisplayName("rejects text that is not a token at all")
	void rejectsGarbage() {
		assertThat(jwtUtil.validateToken("not-a-token")).isNull();
		assertThat(jwtUtil.validateToken("")).isNull();
		assertThat(jwtUtil.validateToken(null)).isNull();
	}

	@Test
	@DisplayName("rejects a valid token whose id has been denylisted at logout")
	void rejectsADenylistedToken() {
		when(tokenDenylist.isTokenDenylisted("jti-1")).thenReturn(true);
		String jwt = token("nurse1", Map.of("userId", "42"), "jti-1");

		assertThat(jwtUtil.validateToken(jwt)).isNull();
	}

	@Test
	@DisplayName("accepts a token whose id is not denylisted")
	void acceptsATokenNotOnTheDenylist() {
		when(tokenDenylist.isTokenDenylisted("jti-1")).thenReturn(false);
		String jwt = token("nurse1", Map.of("userId", "42"), "jti-1");

		assertThat(jwtUtil.validateToken(jwt)).isNotNull();
	}

	@Test
	@DisplayName("refuses to work when no signing secret is configured")
	void requiresASecret() {
		ReflectionTestUtils.setField(jwtUtil, "SECRET_KEY", null);

		assertThatThrownBy(() -> jwtUtil.extractUsername("anything")).isInstanceOf(IllegalStateException.class)
				.hasMessageContaining("JWT secret key is not set");

		ReflectionTestUtils.setField(jwtUtil, "SECRET_KEY", "");
		assertThatThrownBy(() -> jwtUtil.extractUsername("anything")).isInstanceOf(IllegalStateException.class);
	}

	@Test
	@DisplayName("reads the username out of a token")
	void extractsTheUsername() {
		String jwt = token("nurse1", Map.of("userId", "42"), null);

		assertThat(jwtUtil.extractUsername(jwt)).isEqualTo("nurse1");
	}

	@Test
	@DisplayName("reads an arbitrary claim through a resolver")
	void extractsAClaim() {
		String jwt = token("nurse1", Map.of("userId", "42"), null);

		java.util.function.Function<Claims, String> userId = claims -> claims.get("userId", String.class);

		assertThat(jwtUtil.extractClaim(jwt, userId)).isEqualTo("42");
	}

	@Test
	@DisplayName("reads the numeric user id out of a token")
	void extractsTheUserId() throws IEMRException {
		String jwt = token("nurse1", Map.of("userId", "42"), null);

		assertThat(jwtUtil.extractUserId(jwt)).isEqualTo(Integer.valueOf(42));
	}

	@Test
	@DisplayName("reports an invalid token when asked for its user id")
	void userIdOfAnInvalidTokenFails() {
		assertThatThrownBy(() -> jwtUtil.extractUserId("not-a-token")).isInstanceOf(IEMRException.class)
				.hasMessageContaining("Validation error");
	}

	@Test
	@DisplayName("reports a token that carries no user id")
	void userIdMissingFromTheTokenFails() {
		String jwt = token("nurse1", Map.of(), null);

		assertThatThrownBy(() -> jwtUtil.extractUserId(jwt)).isInstanceOf(IEMRException.class);
	}
}
