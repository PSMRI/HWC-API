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
package com.iemr.hwc.service.tele_consultation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.data.quickConsultation.PrescribedDrugDetail;
import com.iemr.hwc.data.tele_consultation.SmsRequestOBJ;
import com.iemr.hwc.repo.tc_consultation.TCRequestModelRepo;

@Service
@PropertySource("classpath:application.properties")
public class SMSGatewayServiceImpl implements SMSGatewayService {
	@Value("${sendSMSUrl}")
	private String sendSMSUrl;
	@Value("${schedule}")
	private String schedule;
	@Value("${prescription}")
	private String prescription;
	@Value("${cancel}")
	private String cancel;
	@Value("${reSchedule}")
	private String reSchedule;

	@Autowired
	private TCRequestModelRepo tCRequestModelRepo;
	@Autowired
	RestTemplate restTemplate;
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Override
	public int smsSenderGateway(String smsType, Long benRegID, Integer specializationID, Long tMRequestID,
			Long tMRequestCancelID, String createdBy, String tcDate, String tcPreviousDate, String Authorization) {

		int returnOBJ = 0;
		try
		{
		String requestOBJ = createSMSRequest(smsType, benRegID, specializationID, tMRequestID, tMRequestCancelID,
				createdBy, tcDate, tcPreviousDate);

		if (requestOBJ != null) {
			String smsStatus = sendSMS(requestOBJ, Authorization);
			if (smsStatus != null) {
				JsonObject jsnOBJ = new JsonObject();
				JsonParser jsnParser = new JsonParser();
				JsonElement jsnElmnt = jsnParser.parse(smsStatus);
				jsnOBJ = jsnElmnt.getAsJsonObject();
				if (jsnOBJ != null && jsnOBJ.get("statusCode").getAsInt() == 200)
					returnOBJ = 1;
			}
			// System.out.println("hello");
		}
		}
		catch(Exception e)
		{
			logger.info("Exception during sending "+smsType+" SMS " + e.getMessage());
		}
		return returnOBJ;

	}

	@Override
	public int smsSenderGateway2(String smsType, List<PrescribedDrugDetail> object, String Authorization, Long benregID,
			String createdBy, List<Object> diagnosis) {

		int returnOBJ = 0;
		Integer smsTypeID = 0;
		SmsRequestOBJ obj;
		String requestOBJ = null;
		ArrayList<SmsRequestOBJ> objList = null;
		if (smsType.equalsIgnoreCase("prescription")) {
			smsTypeID = tCRequestModelRepo.getSMSTypeID(prescription);
		}
		if (smsTypeID != null && smsTypeID != 0) {
			obj = new SmsRequestOBJ();
			obj.setObj(object);
			obj.setDiagnosis(diagnosis);
			obj.setBeneficiaryRegID(benregID);
			obj.setCreatedBy(createdBy);
			ArrayList<Integer> smsTemplateID = tCRequestModelRepo.getSMSTemplateID(smsTypeID);
			if (smsTemplateID != null && smsTemplateID.size() == 1) {
				obj.setSmsTemplateID(smsTemplateID.get(0));
				objList = new ArrayList<SmsRequestOBJ>();
				objList.add(obj);
			} else {
				objList = null;
				logger.info("Multiple SMS template created for same sms type");
			}
		}
		if (objList != null && objList.size() > 0)
			requestOBJ = new Gson().toJson(objList);
		if (requestOBJ != null) {
			String smsStatus = sendSMS(requestOBJ, Authorization);
			if (smsStatus != null) {
				JsonObject jsnOBJ = new JsonObject();
				JsonParser jsnParser = new JsonParser();
				JsonElement jsnElmnt = jsnParser.parse(smsStatus);
				jsnOBJ = jsnElmnt.getAsJsonObject();
				if (jsnOBJ != null && jsnOBJ.get("statusCode").getAsInt() == 200)
					returnOBJ = 1;
			}
		}

		return returnOBJ;

	}

	@Override
	public String createSMSRequest(String smsType, Long benRegID, Integer specializationID, Long tMRequestID,
			Long tMRequestCancelID, String createdBy, String tcDate, String tcPreviousDate) {

		SmsRequestOBJ obj = null;
		ArrayList<SmsRequestOBJ> objList = new ArrayList<>();

		int smsTypeID;

		switch (smsType) {
		case "schedule":
			smsTypeID = tCRequestModelRepo.getSMSTypeID(schedule);
			break;
		case "cancel":
			smsTypeID = tCRequestModelRepo.getSMSTypeID(cancel);
			break;
		case "reSchedule":
			smsTypeID = tCRequestModelRepo.getSMSTypeID(reSchedule);
			break;
		default:
			smsTypeID = 0;
		}

		if (smsTypeID != 0) {
			obj = new SmsRequestOBJ();
			ArrayList<Integer> smsTemplateID = tCRequestModelRepo.getSMSTemplateID(smsTypeID);
			if (smsTemplateID != null && smsTemplateID.size() == 1)
				obj.setSmsTemplateID(smsTemplateID.get(0));
			else {
				obj.setSmsTemplateID(null);
				logger.info("Multiple SMS template created for same sms type");
			}
			obj.setBeneficiaryRegID(benRegID);
			obj.setSpecializationID(specializationID);
			obj.setSmsType(smsType);
			obj.setCreatedBy(createdBy);
			obj.setTcDate(tcDate);
			obj.setTcPreviousDate(tcPreviousDate);

			objList.add(obj);
		}
		if (obj != null && obj.getSmsTemplateID() != null)
			return new Gson().toJson(objList);
		else
			return null;
	}

	@Override
	public String sendSMS(String request, String Authorization) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("AUTHORIZATION", Authorization);

		HttpEntity<Object> requestOBJ = new HttpEntity<Object>(request, headers);

		return restTemplate.exchange(sendSMSUrl, HttpMethod.POST, requestOBJ, String.class).getBody();
	}
}
