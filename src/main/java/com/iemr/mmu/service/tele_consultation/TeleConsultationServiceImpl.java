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
package com.iemr.mmu.service.tele_consultation;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.mmu.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.mmu.data.nurse.CommonUtilityClass;
import com.iemr.mmu.data.tele_consultation.TCRequestModel;
import com.iemr.mmu.data.tele_consultation.TcSpecialistSlotBookingRequestOBJ;
import com.iemr.mmu.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.mmu.data.tele_consultation.TeleconsultationStats;
import com.iemr.mmu.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.mmu.repo.tc_consultation.TCRequestModelRepo;
import com.iemr.mmu.repo.tc_consultation.TeleconsultationStatsRepo;
import com.iemr.mmu.service.common.transaction.CommonServiceImpl;
import com.iemr.mmu.utils.mapper.InputMapper;
import com.iemr.mmu.utils.mapper.OutputMapper;

@Service
@PropertySource("classpath:application.properties")
public class TeleConsultationServiceImpl implements TeleConsultationService {
	@Value("${tcSpecialistSlotCancel}")
	private String tcSpecialistSlotCancel;

	@Autowired
	private TCRequestModelRepo tCRequestModelRepo;
	@Autowired
	private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;
	@Autowired
	private CommonServiceImpl commonServiceImpl;
	@Autowired
	private SMSGatewayServiceImpl sMSGatewayServiceImpl;
	@Autowired
	private TeleconsultationStatsRepo teleconsultationStatsRepo;

	public Long createTCRequest(TCRequestModel tCRequestModel) {
		TCRequestModel tCRequestModelRS = tCRequestModelRepo.save(tCRequestModel);
		if (tCRequestModelRS != null && tCRequestModelRS.gettMRequestID() > 0)
			return tCRequestModelRS.gettMRequestID();
		else
			return Long.valueOf(0);
	}

	@Transactional(rollbackFor = Exception.class)
	public int updateBeneficiaryArrivalStatus(String requestOBJ) throws Exception {
		int resultFlag = 0;
		JsonObject requestJson = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElement = jsnParser.parse(requestOBJ);
		requestJson = jsnElement.getAsJsonObject();

		if (requestJson.has("benflowID") && requestJson.has("benRegID") && requestJson.has("visitCode")
				&& requestJson.has("modifiedBy") && requestJson.has("status") && requestJson.has("userID")
				&& !requestJson.get("benflowID").isJsonNull() && !requestJson.get("benRegID").isJsonNull()
				&& !requestJson.get("visitCode").isJsonNull() && !requestJson.get("status").isJsonNull()
				&& !requestJson.get("modifiedBy").isJsonNull() && !requestJson.get("userID").isJsonNull()) {

			String statusStr;
			Boolean status = requestJson.get("status").getAsBoolean();
			if (status)
				statusStr = "A";
			else
				statusStr = "N";

			int i = beneficiaryFlowStatusRepo.updateBeneficiaryArrivalStatus(requestJson.get("benflowID").getAsLong(),
					requestJson.get("benRegID").getAsLong(), requestJson.get("visitCode").getAsLong(),
					requestJson.get("status").getAsBoolean(), requestJson.get("modifiedBy").getAsString(),
					requestJson.get("userID").getAsInt());

			if (i > 0) {
				int j = tCRequestModelRepo.updateBeneficiaryStatus(requestJson.get("benRegID").getAsLong(),
						requestJson.get("visitCode").getAsLong(), statusStr,
						requestJson.get("modifiedBy").getAsString(), requestJson.get("userID").getAsInt(), false);

				if (j > 0)
					resultFlag = 1;
				else
					throw new RuntimeException(
							"Beneficiary arrival status update failed.There is no active Tele-consultation session");
			} else
				throw new RuntimeException(
						"Beneficiary arrival status update failed.There is no active Tele-consultation session for this visit");
		} else {
			throw new RuntimeException("Invalid request");
		}

		return resultFlag;
	}

	@Transactional(rollbackFor = Exception.class)
	public int updateBeneficiaryStatusToCancelTCRequest(String requestOBJ, String Authorization) throws Exception {
		int resultFlag = 0;
		JsonObject requestJson = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElement = jsnParser.parse(requestOBJ);
		requestJson = jsnElement.getAsJsonObject();

		if (requestJson.has("benflowID") && requestJson.has("benRegID") && requestJson.has("visitCode")
				&& requestJson.has("modifiedBy") && requestJson.has("userID")
				&& !requestJson.get("benflowID").isJsonNull() && !requestJson.get("benRegID").isJsonNull()
				&& !requestJson.get("visitCode").isJsonNull() && !requestJson.get("modifiedBy").isJsonNull()
				&& !requestJson.get("userID").isJsonNull()) {

			TCRequestModel tCRequestModelRS = tCRequestModelRepo.getSpecializationID(
					requestJson.get("benRegID").getAsLong(), requestJson.get("visitCode").getAsLong(),
					requestJson.get("userID").getAsInt());

			Timestamp tcDate = tCRequestModelRS.getRequestDate();

			int i = cancelSlotForTCCancel(requestJson.get("userID").getAsInt(), requestJson.get("benRegID").getAsLong(),
					requestJson.get("visitCode").getAsLong(), Authorization);

			int j = beneficiaryFlowStatusRepo.updateBeneficiaryStatusToCancelRequest(
					requestJson.get("benflowID").getAsLong(), requestJson.get("benRegID").getAsLong(),
					requestJson.get("visitCode").getAsLong(), requestJson.get("modifiedBy").getAsString(),
					requestJson.get("userID").getAsInt());

			int k = tCRequestModelRepo.updateBeneficiaryStatusCancel(requestJson.get("benRegID").getAsLong(),
					requestJson.get("visitCode").getAsLong(), "C", requestJson.get("modifiedBy").getAsString(),
					requestJson.get("userID").getAsInt(), true);

			if (i > 0 && j > 0 && k > 0) {
				resultFlag = 1;

				int l = sMSGatewayServiceImpl.smsSenderGateway("cancel", requestJson.get("benRegID").getAsLong(),
						tCRequestModelRS.getSpecializationID(), null, null, requestJson.get("modifiedBy").getAsString(),
						null, tcDate != null ? String.valueOf(tcDate) : "DD-MM-YYYY", Authorization);
			} else
				throw new RuntimeException(
						"Teleconsultation cancel request failed.There is no active Tele-consultation session for this visit");

		} else
			throw new RuntimeException("Invalid request");

		return resultFlag;
	}

	public int cancelSlotForTCCancel(int userID, long benRegID, long visitCode, String Authorization) throws Exception {
		String fromTime = "";
		Long duration = null;
		int returnOBJ = 0;
		Set<String> statusSet = new HashSet<>();

		statusSet.add("N");
		statusSet.add("A");
		statusSet.add("O");

		ArrayList<TCRequestModel> resultSetList = tCRequestModelRepo.getTcDetailsList(benRegID, visitCode, userID,
				statusSet);

		TcSpecialistSlotBookingRequestOBJ obj = new TcSpecialistSlotBookingRequestOBJ();

		if (resultSetList != null && resultSetList.size() > 0) {
			obj.setUserID(userID);
			obj.setDate(resultSetList.get(0).getRequestDate());

			if (resultSetList.get(0).getRequestDate() != null)
				// String s = resultSetList.get(0).getRequestDate().toString().split(" ")[1];
				fromTime = resultSetList.get(0).getRequestDate().toString().split(" ")[1];

			obj.setFromTime(fromTime);

			duration = resultSetList.get(0).getDuration_minute();
			obj.setDuration(duration);
			obj.setToTime(calculateToDate(fromTime, duration));

			String requestOBJ = OutputMapper.gson().toJson(obj);

			RestTemplate restTemplate = new RestTemplate();
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
			headers.add("Content-Type", "application/json");
			headers.add("AUTHORIZATION", Authorization);
			HttpEntity<Object> request = new HttpEntity<Object>(requestOBJ, headers);
			ResponseEntity<String> response = restTemplate.exchange(tcSpecialistSlotCancel, HttpMethod.POST, request,
					String.class);

			if (response.getStatusCodeValue() == 200 && response.hasBody()) {
				JsonObject jsnOBJ = new JsonObject();
				JsonParser jsnParser = new JsonParser();
				JsonElement jsnElmnt = jsnParser.parse(response.getBody());
				jsnOBJ = jsnElmnt.getAsJsonObject();
				if (jsnOBJ.has("statusCode") && jsnOBJ.get("statusCode").getAsInt() == 200)
					returnOBJ = 1;
			}
		} else {
			returnOBJ = 1;
		}
		return returnOBJ;
	}

	private String calculateToDate(String fromTime, Long duration) {
		String toTime = "";

		if (fromTime != null && duration != null) {
			LocalTime fTime = LocalTime.parse(fromTime);
			LocalTime tTime = fTime.plusMinutes(duration);

			toTime = tTime.toString();
		}

		return toTime;
	}

	@Transactional(rollbackFor = Exception.class)
	public int checkBeneficiaryStatusForSpecialistTransaction(String requestOBJ) throws Exception {
		int returnOBJ = 0;
		JsonObject requestJson = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElement = jsnParser.parse(requestOBJ);
		requestJson = jsnElement.getAsJsonObject();

		if (requestJson.has("benflowID") && requestJson.has("benRegID") && requestJson.has("visitCode")
				&& requestJson.has("userID") && !requestJson.get("benflowID").isJsonNull()
				&& !requestJson.get("benRegID").isJsonNull() && !requestJson.get("visitCode").isJsonNull()
				&& !requestJson.get("userID").isJsonNull()) {

			ArrayList<BeneficiaryFlowStatus> resultSet = beneficiaryFlowStatusRepo.checkBeneficiaryArrivalStatus(
					requestJson.get("benflowID").getAsLong(), requestJson.get("benRegID").getAsLong(),
					requestJson.get("visitCode").getAsLong(), requestJson.get("userID").getAsInt());

			if (resultSet != null && resultSet.size() == 1) {
				BeneficiaryFlowStatus obj1 = resultSet.get(0);
				if (obj1.getBenArrivedFlag()) {
					ArrayList<TCRequestModel> tCRequestModelOBJ = tCRequestModelRepo.checkBenTcStatus(
							requestJson.get("benRegID").getAsLong(), requestJson.get("visitCode").getAsLong(),
							requestJson.get("userID").getAsInt());
					if (tCRequestModelOBJ != null && tCRequestModelOBJ.size() > 0) {
						returnOBJ = 1;
					} else {
						throw new RuntimeException("Sorry, Beneficiary has not any active Teleconsultation session.");
					}
				} else {
					throw new RuntimeException("Sorry, Beneficiary has not arrived at TM spoke/center");
				}
			} else {
				throw new RuntimeException(
						"No record or multiple record found in DB. Kindly contact the administrator");
			}
		} else {
			throw new RuntimeException("Invalid request.");
		}
		return returnOBJ;
	}

	@Transactional(rollbackFor = Exception.class)
	public int createTCRequestFromWorkList(JsonObject tcRequestOBJ, String Authorization) throws Exception {
		int returnOBJ = 0;

		CommonUtilityClass commonUtilityClass = InputMapper.gson().fromJson(tcRequestOBJ, CommonUtilityClass.class);
		TeleconsultationRequestOBJ tcRequest = null;

		tcRequest = commonServiceImpl.createTcRequest(tcRequestOBJ, commonUtilityClass, Authorization);
		if (tcRequest != null && tcRequest.getUserID() != null && tcRequest.getAllocationDate() != null) {
			returnOBJ = 1;
			TCRequestModel tRequestModel = InputMapper.gson().fromJson(tcRequestOBJ, TCRequestModel.class);
			tRequestModel.setUserID(tcRequest.getUserID());
			tRequestModel.setRequestDate(tcRequest.getAllocationDate());

			int i = beneficiaryFlowStatusRepo.updateFlagAfterTcRequestCreatedFromWorklist(
					commonUtilityClass.getBenFlowID(), commonUtilityClass.getBeneficiaryRegID(),
					commonUtilityClass.getVisitCode(), tRequestModel.getUserID(), tRequestModel.getRequestDate());
			if (i > 0) {
				if (tcRequest != null && tcRequest.getWalkIn() == false) {
					int l = sMSGatewayServiceImpl.smsSenderGateway("schedule", commonUtilityClass.getBeneficiaryRegID(),
							tcRequest.getSpecializationID(), null, null, commonUtilityClass.getCreatedBy(),
							tcRequest.getAllocationDate() != null ? String.valueOf(tcRequest.getAllocationDate())
									: "DD-MM-YYYY",
							null, Authorization);
				}
				returnOBJ = 1;
			} else
				throw new RuntimeException(
						"Error while updating beneficiary flow status. Kindly contact your administrator");
		} else {
			throw new RuntimeException("Error while creating TC request. Kindly contact your administrator");
		}
		return returnOBJ;
	}

	public String getTCRequestListBySpecialistIdAndDate(Integer providerServiceMapID, Integer userID, String reqDate)
			throws Exception {
		LocalDate datePart = LocalDate.parse(reqDate.split("T")[0]);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Date d = new SimpleDateFormat("yyyy-MM-dd").parse(datePart.format(format));
		Timestamp t = new Timestamp(d.getTime());
		ArrayList<BeneficiaryFlowStatus> tcList = beneficiaryFlowStatusRepo.getTCRequestList(providerServiceMapID,
				userID, t);
		return new Gson().toJson(tcList);
	}

	public Integer startconsultation(Long benRegID, Long visitCode) {
		// check tm stats
		TeleconsultationStats teleconsultationStats = new TeleconsultationStats();
		teleconsultationStats.setBeneficiaryRegID(benRegID);
		teleconsultationStats.setVisitCode(visitCode);
		teleconsultationStats.setStartTime(new Timestamp(System.currentTimeMillis()));
		teleconsultationStats.setCreatedBy("Report");

		teleconsultationStatsRepo.save(teleconsultationStats);

		// TODO Auto-generated method stub
		return tCRequestModelRepo.updateStartConsultationTime(benRegID, visitCode);
	}

}
