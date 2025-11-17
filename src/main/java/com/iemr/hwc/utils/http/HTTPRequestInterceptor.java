/*
* AMRIT – Accessible Medical Records via Integrated Technology 
* Integrated EHR (Electronic Health Records) Solution 
*
* Copyright (C) "Piramal Swasthya Management and Research Institute" 
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.iemr.hwc.utils.http;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

import com.iemr.hwc.utils.response.OutputResponse;
import com.iemr.hwc.utils.sessionobject.SessionObject;
import com.iemr.hwc.utils.validator.Validator;

@Component
public class HTTPRequestInterceptor implements HandlerInterceptor {
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Value("${cors.allowed-origins}")
	private String allowedOrigins;
	
	@Autowired
	private SessionObject sessionObject;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		logger.info("http interceptor - pre Handle");
		boolean status = true;

		if (request.getRequestURI().toLowerCase().contains("swagger-ui"))
			return status;

		String authorization = null;
		String preAuth = request.getHeader("Authorization");
		if(null != preAuth && preAuth.contains("Bearer "))
			authorization=preAuth.replace("Bearer ", "");
		else
			authorization = preAuth;
		if (authorization == null || authorization.isEmpty()) {
	        logger.info("Authorization header is null or empty. Skipping HTTPRequestInterceptor.");
	        return true; // Allow the request to proceed without validation
	    }
		if (!request.getMethod().equalsIgnoreCase("OPTIONS")) {
			try {
				String[] requestURIParts = request.getRequestURI().split("/");
				String requestAPI = requestURIParts[requestURIParts.length - 1];
				switch (requestAPI) {
				case "swagger-ui.html":
					break;
				case "index.html":
					break;
				case "swagger-initializer.js":
					break;
				case "swagger-config":
					break;
				case "ui":
					break;
				case "swagger-resources":
					break;
				case "api-docs":
					break;

				case "error":
					status = false;
					break;
				default:
					logger.debug("RequestURI::" + request.getRequestURI() + " || Authorization ::" + authorization);
					if (authorization == null)
						throw new Exception(
								"Authorization key is NULL, please pass valid session key to proceed further. ");
					String userRespFromRedis = sessionObject.getSessionObject(authorization);
					if (userRespFromRedis == null)
						throw new Exception("invalid Authorization key, please pass a valid key to proceed further. ");
					break;
				}
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage());

				OutputResponse output = new OutputResponse();
				output.setError(e);
				response.getOutputStream().print(output.toString());
				response.setContentType(MediaType.APPLICATION_JSON);
				response.setContentLength(output.toString().length());
				String origin = request.getHeader("Origin");
				if (origin != null && isOriginAllowed(origin)) {
					response.setHeader("Access-Control-Allow-Origin", origin);
					response.setHeader("Access-Control-Allow-Credentials", "true");
				} else if (origin != null) {
					logger.warn("CORS headers NOT added for error response | Unauthorized origin: {}", origin);
				}
				status = false;
			}
		}

		return status;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView model)
			throws Exception {
		try {
			logger.debug("In postHandle we are Intercepting the Request");
			String authorization = null;
			String postAuth = request.getHeader("Authorization");
			if(null != postAuth && postAuth.contains("Bearer "))
				authorization=postAuth.replace("Bearer ", "");
			else
				authorization = postAuth;
			logger.debug("RequestURI::" + request.getRequestURI() + " || Authorization ::" + authorization);
			if (authorization != null && !authorization.isEmpty()) {
				sessionObject.updateSessionObject(authorization, sessionObject.getSessionObject(authorization));
			}
		} catch (Exception e) {
			logger.debug("postHandle failed with error " + e.getMessage());
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3)
			throws Exception {
		logger.info("http interceptor - after completion");

	}

	/**
	 * Check if the given origin is allowed based on configured allowedOrigins.
	 * Uses the same logic as JwtUserIdValidationFilter for consistency.
	 * 
	 * @param origin The origin to validate
	 * @return true if origin is allowed, false otherwise
	 */
	private boolean isOriginAllowed(String origin) {
		if (origin == null || allowedOrigins == null || allowedOrigins.trim().isEmpty()) {
			return false;
		}

		return Arrays.stream(allowedOrigins.split(","))
				.map(String::trim)
				.anyMatch(pattern -> {
					String regex = pattern
							.replace(".", "\\.")
							.replace("*", ".*");
					return origin.matches(regex);
				});
	}

}
