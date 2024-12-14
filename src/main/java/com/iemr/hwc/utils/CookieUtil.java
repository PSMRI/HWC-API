package com.iemr.hwc.utils;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class CookieUtil {

	public Optional<String> getCookieValue(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookieName.equals(cookie.getName())) {
					return Optional.of(cookie.getValue());
				}
			}
		}
		return Optional.empty();
	}

	public String getJwtTokenFromCookie(HttpServletRequest request) {
		return Arrays.stream(request.getCookies()).filter(cookie -> "Jwttoken".equals(cookie.getName()))
				.map(Cookie::getValue).findFirst().orElse(null);
	}
}
