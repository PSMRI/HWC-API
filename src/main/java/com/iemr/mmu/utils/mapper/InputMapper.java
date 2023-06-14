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
package com.iemr.mmu.utils.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.ExclusionStrategy;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.iemr.mmu.utils.exception.IEMRException;

/**
 * @author VI314759
 *
 */
/**
 * @author VI314759
 *
 */
@Service
public class InputMapper {
	static GsonBuilder builder;
	ExclusionStrategy strategy;
	Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	// @Autowired
	// ConfigProperties configProperties;

	// @Autowired
	// SessionObject sessionObject;

	// public void setSessionObject(SessionObject sessionObject)
	// {
	// this.sessionObject = sessionObject;
	// }

	public InputMapper() {
		if (builder == null) {
			builder = new GsonBuilder();
			builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
			// builder.addDeserializationExclusionStrategy(strategy)
		}
	}

	/**
	 * @return
	 */
	public static InputMapper gson() {
		return new InputMapper();
	}

	/**
	 * @param json
	 * @param classOfT
	 * @return
	 * @throws IEMRException
	 */
	public <T> T fromJson(String json, Class<T> classOfT) throws IEMRException {
		return builder.create().fromJson(json, classOfT);
	}

	public <T> T fromJson(JsonElement json, Class<T> classOfT) throws IEMRException {
		return builder.create().fromJson(json, classOfT);
	}

	/**
	 * @param json
	 * @throws IEMRException
	 *             This function will check for the following things and throws
	 *             login exception if any one fails 1. Get the session details
	 *             from the redis 2. IP address in request and the logged in IP
	 *             address should be same.
	 */
	// private void valildate(String json) throws IEMRException
	// {
	// Object obj = new JsonParser().parse(json);
	// try
	// {
	// if (obj instanceof JsonObject)
	// {
	// JSONObject reqObj = new JSONObject(json);
	// String key = reqObj.getString("key");
	// // Validator.checkKeyExists(key);
	// } else if (obj instanceof JsonObject)
	// {
	// // to do
	// } else
	// {
	// // throw new IEMRException("Invalid login key");
	// logger.info("Invalid login key");
	// }
	// } catch (Exception e)
	// {
	// // throw new IEMRException("Invalid login key");
	// logger.info("Invalid login key");
	// }
	// }
}