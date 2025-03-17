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
package com.iemr.hwc.service.cancerScreening;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.iemr.hwc.utils.CookieUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CSCarestreamServiceImpl implements CSCarestreamService {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Value("${carestreamOrderCreateURL}")
	private String carestreamOrderCreateURL;
	@Autowired
	private CookieUtil cookieUtil;

	@Override
	public int createMamographyRequest(ArrayList<Object[]> benDataForCareStream, long benRegID, long benVisitID,
			String Authorization) {
		int responseData = 0;
		RestTemplate restTemplate = new RestTemplate();
		HttpServletRequest requestHeader = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String jwtTokenFromCookie = cookieUtil.getJwtTokenFromCookie(requestHeader);
		try {
			// HttpHeaders headers = new HttpHeaders();
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
			headers.add("Content-Type", "application/json");
			headers.add("AUTHORIZATION", Authorization);
			headers.add("Cookie", "Jwttoken=" + jwtTokenFromCookie);
			String requestOBJ = getOrderCreationRequestOBJ(benDataForCareStream, benRegID, benVisitID);

			HttpEntity<Object> request = new HttpEntity<Object>(requestOBJ, headers);
			// System.out.println("hello");
			ResponseEntity<String> response = restTemplate.exchange(carestreamOrderCreateURL, HttpMethod.POST, request,
					String.class);
			if (response != null) {
				JSONObject obj = new JSONObject(response.getBody());
				if (obj.getInt("statusCode") == 200) {
					responseData = 1;
				}
			}
			// System.out.println("hello");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return responseData;
	}

	private String getOrderCreationRequestOBJ(ArrayList<Object[]> benDataForCareStream, long benRegID, long benVisitID)
			throws Exception {
		Map<String, Object> requestOBJ = new HashMap<>();
		if (benDataForCareStream != null && benDataForCareStream.size() > 0) {
			Object[] objArr = benDataForCareStream.get(0);

			Date dob = (Date) objArr[1];
			String dobStr = dob.toString();
			dobStr = dobStr.replaceAll("-", "");
			short genderID = (short) objArr[2];
			String gender = "";

			if (genderID == 1)
				gender = "M";
			else if (genderID == 2)
				gender = "F";
			else
				gender = "T";

			String[] arr = String.valueOf(objArr[0]).split(" ", 2);

			if (arr != null && arr.length > 0)
				requestOBJ.put("firstName", arr[0]);

			if (arr != null && arr.length >= 2) {
				requestOBJ.put("LastName", arr[1]);
			}
			requestOBJ.put("patientID", benRegID);
			requestOBJ.put("acc", benVisitID);
			requestOBJ.put("gender", gender);
			requestOBJ.put("dob", dobStr);
			// System.out.println("hellooo.......");
		}
		return new Gson().toJson(requestOBJ);
	}
}
