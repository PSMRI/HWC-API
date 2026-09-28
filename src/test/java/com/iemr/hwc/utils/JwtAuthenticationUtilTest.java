package com.iemr.hwc.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import com.iemr.hwc.data.login.Users;
import com.iemr.hwc.repo.login.UserLoginRepo;
import com.iemr.hwc.utils.exception.IEMRException;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("JwtAuthenticationUtil")
class JwtAuthenticationUtilTest {

	@Mock
	private CookieUtil cookieUtil;
	@Mock
	private JwtUtil jwtUtil;
	@Mock
	private RedisTemplate<String, Object> redisTemplate;
	@Mock
	private ValueOperations<String, Object> valueOperations;
	@Mock
	private UserLoginRepo userLoginRepo;
	@Mock
	private HttpServletRequest request;

	private JwtAuthenticationUtil authenticationUtil;

	@BeforeEach
	void setUp() {
		authenticationUtil = new JwtAuthenticationUtil(cookieUtil, jwtUtil);
		ReflectionTestUtils.setField(authenticationUtil, "redisTemplate", redisTemplate);
		ReflectionTestUtils.setField(authenticationUtil, "userLoginRepo", userLoginRepo);
		when(redisTemplate.opsForValue()).thenReturn(valueOperations);
	}

	private static Claims claimsWith(String subject, String userId) {
		Claims claims = mock(Claims.class);
		when(claims.getSubject()).thenReturn(subject);
		when(claims.get("userId", String.class)).thenReturn(userId);
		return claims;
	}

	@Test
	@DisplayName("answers with the username carried by a valid cookie token")
	void returnsTheUsernameOfAValidToken() {
		when(cookieUtil.getCookieValue(request, "Jwttoken")).thenReturn(Optional.of("token"));
		Claims claims = claimsWith("nurse1", "42");
		when(jwtUtil.validateToken("token")).thenReturn(claims);

		ResponseEntity<String> response = authenticationUtil.validateJwtToken(request);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo("nurse1");
	}

	@Test
	@DisplayName("answers 401 when the request carries no JWT cookie")
	void rejectsARequestWithoutAToken() {
		when(cookieUtil.getCookieValue(request, "Jwttoken")).thenReturn(Optional.empty());

		ResponseEntity<String> response = authenticationUtil.validateJwtToken(request);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
		assertThat(response.getBody()).contains("JWT Token is not set");
	}

	@Test
	@DisplayName("answers 401 when the token does not validate")
	void rejectsAnInvalidToken() {
		when(cookieUtil.getCookieValue(request, "Jwttoken")).thenReturn(Optional.of("token"));
		when(jwtUtil.validateToken("token")).thenReturn(null);

		ResponseEntity<String> response = authenticationUtil.validateJwtToken(request);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
		assertThat(response.getBody()).contains("Invalid JWT Token");
	}

	@Test
	@DisplayName("answers 401 when the token carries no username")
	void rejectsATokenWithoutASubject() {
		when(cookieUtil.getCookieValue(request, "Jwttoken")).thenReturn(Optional.of("token"));
		Claims claims = claimsWith(null, "42");
		when(jwtUtil.validateToken("token")).thenReturn(claims);

		ResponseEntity<String> response = authenticationUtil.validateJwtToken(request);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
		assertThat(response.getBody()).contains("Username is missing");

		Claims blankSubject = claimsWith("", "42");
		when(jwtUtil.validateToken("token")).thenReturn(blankSubject);
		assertThat(authenticationUtil.validateJwtToken(request).getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	@DisplayName("accepts a token whose user is already cached in Redis, without touching the database")
	void acceptsACachedUser() throws IEMRException {
		Claims claims = claimsWith("nurse1", "42");
		when(jwtUtil.validateToken("token")).thenReturn(claims);
		when(valueOperations.get("user_42")).thenReturn(new Users());

		assertThat(authenticationUtil.validateUserIdAndJwtToken("token")).isTrue();

		verify(userLoginRepo, never()).getUserByUserID(anyLong());
	}

	@Test
	@DisplayName("falls back to the database and caches the user when Redis has no entry")
	void cachesAUserLoadedFromTheDatabase() throws IEMRException {
		Users user = new Users();
		user.setUserID(42L);
		user.setUserName("nurse1");
		Claims claims = claimsWith("nurse1", "42");
		when(jwtUtil.validateToken("token")).thenReturn(claims);
		when(valueOperations.get("user_42")).thenReturn(null);
		when(userLoginRepo.getUserByUserID(42L)).thenReturn(user);

		assertThat(authenticationUtil.validateUserIdAndJwtToken("token")).isTrue();

		verify(valueOperations).set(eq("user_42"), any(Users.class), eq(30L), eq(TimeUnit.MINUTES));
	}

	@Test
	@DisplayName("rejects a token whose user exists in neither Redis nor the database")
	void rejectsAnUnknownUser() {
		Claims claims = claimsWith("nurse1", "42");
		when(jwtUtil.validateToken("token")).thenReturn(claims);
		when(valueOperations.get("user_42")).thenReturn(null);
		when(userLoginRepo.getUserByUserID(42L)).thenReturn(null);

		assertThatThrownBy(() -> authenticationUtil.validateUserIdAndJwtToken("token"))
				.isInstanceOf(IEMRException.class).hasMessageContaining("Invalid User ID");
	}

	@Test
	@DisplayName("rejects a token that does not validate")
	void rejectsATokenThatDoesNotValidate() {
		when(jwtUtil.validateToken("token")).thenReturn(null);

		assertThatThrownBy(() -> authenticationUtil.validateUserIdAndJwtToken("token"))
				.isInstanceOf(IEMRException.class).hasMessageContaining("Invalid JWT token");
	}

	@Test
	@DisplayName("rejects a token whose user id is not a number")
	void rejectsANonNumericUserId() {
		Claims claims = claimsWith("nurse1", "not-a-number");
		when(jwtUtil.validateToken("token")).thenReturn(claims);
		when(valueOperations.get("user_not-a-number")).thenReturn(null);

		assertThatThrownBy(() -> authenticationUtil.validateUserIdAndJwtToken("token"))
				.isInstanceOf(IEMRException.class);
	}
}
