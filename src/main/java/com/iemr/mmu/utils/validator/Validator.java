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
package com.iemr.mmu.utils.validator;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.iemr.mmu.utils.exception.IEMRException;
import com.iemr.mmu.utils.redis.RedisSessionException;
import com.iemr.mmu.utils.sessionobject.SessionObject;

@Service
public class Validator {
	// private static SessionObject session;

	private SessionObject session;
	private static Boolean enableIPValidation = false;

	@Autowired(required = true)
	@Required
	public void setSessionObject(SessionObject sessionObject) {
		this.session = sessionObject;
	}

	// private static void setSessionObject() {
	// if (session == null) {
	// session = new SessionObject();
	// }
	// }

	private Logger logger = LoggerFactory.getLogger(Validator.class);

	public JSONObject updateCacheObj(JSONObject responseObj, String key, String ipKey) {
		try {
			Boolean loggedFromDifferentIP = false;
			String loginKey = key;
			String status = "login failed";
			try {
				responseObj.put("sessionStatus", "session creation failed");
				String sessionData = session.getSessionObject(key);
				if (sessionData != null && sessionData.trim().length() > 0) {
					JSONObject sessionObj = new JSONObject(sessionData);
					if (!sessionObj.getString("loginIPAddress").equals(responseObj.getString("loginIPAddress"))) {
						loggedFromDifferentIP = true;
						status = "login success, but user logged in from " + sessionObj.getString("loginIPAddress");
					}
				}
			} catch (RedisSessionException e) {
				logger.error("Session validation failed with exception", e);
			}
			if (!loggedFromDifferentIP) {
				status = "login success";
				session.setSessionObject(key, responseObj.toString());
			} else {
				responseObj = new JSONObject();
			}
			responseObj.put("key", loginKey);
			responseObj.put("sessionStatus", status);
		} catch (RedisSessionException | JSONException e) {
			logger.error("Session validation failed with exception", e);
		}
		return responseObj;
	}

	public String getSessionObject(String key) throws RedisSessionException {
		return session.getSessionObject(key);
	}

	public void checkKeyExists(String loginKey, String ipAddress) throws IEMRException
	{
		try
		{
			String sessionString = session.getSessionObject(loginKey);
			JSONObject sessionObj = new JSONObject(sessionString);
			if (enableIPValidation)
			{
				if (!sessionObj.getString("loginIPAddress").equals(ipAddress))
				{
					logger.error(
							"Logged in IP : " + sessionObj.getString("loginIPAddress") + "\tRequest IP : " + ipAddress);
					throw new Exception();
				} 
			}
		} catch (Exception e)
		{
			throw new IEMRException("Invalid login key or session is expired");
		}
	}
}
