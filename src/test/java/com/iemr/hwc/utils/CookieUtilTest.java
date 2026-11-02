package com.iemr.hwc.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@DisplayName("CookieUtil")
class CookieUtilTest {

	private final CookieUtil cookieUtil = new CookieUtil();

	private static HttpServletRequest requestWith(Cookie... cookies) {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getCookies()).thenReturn(cookies);
		return request;
	}

	@Test
	@DisplayName("returns the value of the cookie asked for")
	void findsTheNamedCookie() {
		HttpServletRequest request = requestWith(new Cookie("other", "x"), new Cookie("Jwttoken", "token-value"));

		assertThat(cookieUtil.getCookieValue(request, "Jwttoken")).contains("token-value");
	}

	@Test
	@DisplayName("returns empty when the request carries no cookie of that name")
	void missingCookieIsEmpty() {
		HttpServletRequest request = requestWith(new Cookie("other", "x"));

		assertThat(cookieUtil.getCookieValue(request, "Jwttoken")).isEmpty();
	}

	@Test
	@DisplayName("returns empty when the request carries no cookies at all")
	void noCookiesIsEmpty() {
		assertThat(cookieUtil.getCookieValue(requestWith((Cookie[]) null), "Jwttoken")).isEqualTo(Optional.empty());
	}

	@Test
	@DisplayName("reads the JWT out of the Jwttoken cookie")
	void readsTheJwtCookie() {
		HttpServletRequest request = requestWith(new Cookie("Jwttoken", "signed-token"));

		assertThat(CookieUtil.getJwtTokenFromCookie(request)).isEqualTo("signed-token");
	}

	@Test
	@DisplayName("returns null for a JWT lookup when there are no cookies")
	void jwtLookupWithoutCookiesIsNull() {
		assertThat(CookieUtil.getJwtTokenFromCookie(requestWith((Cookie[]) null))).isNull();
	}

	@Test
	@DisplayName("returns null for a JWT lookup when the Jwttoken cookie is absent")
	void jwtLookupWithoutTheTokenCookieIsNull() {
		assertThat(CookieUtil.getJwtTokenFromCookie(requestWith(new Cookie("session", "s")))).isNull();
	}

	@Test
	@DisplayName("takes the first Jwttoken cookie when the request repeats it")
	void takesTheFirstJwtCookie() {
		HttpServletRequest request = requestWith(new Cookie("Jwttoken", "first"), new Cookie("Jwttoken", "second"));

		assertThat(CookieUtil.getJwtTokenFromCookie(request)).isEqualTo("first");
	}
}
