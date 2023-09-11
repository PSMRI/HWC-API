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
package com.iemr.hwc.service.registrar;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.ws.rs.core.MediaType;

import com.iemr.hwc.data.login.Users;
import com.iemr.hwc.data.registrar.*;
import com.iemr.hwc.repo.login.UserLoginRepo;
import com.iemr.hwc.repo.registrar.*;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.iemr.hwc.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.utils.mapper.InputMapper;
import com.iemr.hwc.utils.response.OutputResponse;

/***
 * 
 * @author NE298657
 *
 */
@Service
@PropertySource("classpath:application.properties")
public class RegistrarServiceImpl implements RegistrarService {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Value("${registrationUrl}")
	private String registrationUrl;

	@Value("${registrarQuickSearchByIdUrl}")
	private String registrarQuickSearchByIdUrl;

	@Value("${registrarQuickSearchByPhoneNoUrl}")
	private String registrarQuickSearchByPhoneNoUrl;

	@Value("${beneficiaryEditUrl}")
	private String beneficiaryEditUrl;

	@Value("${registrarAdvanceSearchUrl}")
	private String registrarAdvanceSearchUrl;

	@Value("${syncSearchByLocation}")
	private String syncSearchByLocation;

	private RegistrarRepoBenData registrarRepoBenData;
	private RegistrarRepoBenDemoData registrarRepoBenDemoData;
	private RegistrarRepoBenPhoneMapData registrarRepoBenPhoneMapData;
	private RegistrarRepoBenGovIdMapping registrarRepoBenGovIdMapping;
	private ReistrarRepoBenSearch reistrarRepoBenSearch;
	private BeneficiaryDemographicAdditionalRepo beneficiaryDemographicAdditionalRepo;
	private RegistrarRepoBeneficiaryDetails registrarRepoBeneficiaryDetails;
	private BeneficiaryImageRepo beneficiaryImageRepo;
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;

	@Autowired
	private UserLoginRepo userRepo;

	@Autowired
	private UserBiometricsRepo userBiometricsRepo;

	@Autowired
	public void setCommonBenStatusFlowServiceImpl(CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl) {
		this.commonBenStatusFlowServiceImpl = commonBenStatusFlowServiceImpl;
	}

	@Autowired
	public void setBeneficiaryImageRepo(BeneficiaryImageRepo beneficiaryImageRepo) {
		this.beneficiaryImageRepo = beneficiaryImageRepo;
	}

	@Autowired
	public void setBeneficiaryDemographicAdditionalRepo(
			BeneficiaryDemographicAdditionalRepo beneficiaryDemographicAdditionalRepo) {
		this.beneficiaryDemographicAdditionalRepo = beneficiaryDemographicAdditionalRepo;
	}

	@Autowired
	public void setRegistrarRepoBenData(RegistrarRepoBenData registrarRepoBenData) {
		this.registrarRepoBenData = registrarRepoBenData;
	}

	@Autowired
	public void setRegistrarRepoBenDemoData(RegistrarRepoBenDemoData registrarRepoBenDemoData) {
		this.registrarRepoBenDemoData = registrarRepoBenDemoData;
	}

	@Autowired
	public void setRegistrarRepoBenPhoneMapData(RegistrarRepoBenPhoneMapData registrarRepoBenPhoneMapData) {
		this.registrarRepoBenPhoneMapData = registrarRepoBenPhoneMapData;
	}

	@Autowired
	public void setRegistrarRepoBenGovIdMapping(RegistrarRepoBenGovIdMapping registrarRepoBenGovIdMapping) {
		this.registrarRepoBenGovIdMapping = registrarRepoBenGovIdMapping;
	}

	@Autowired
	public void setReistrarRepoAdvanceBenSearch(ReistrarRepoBenSearch reistrarRepoBenSearch) {
		this.reistrarRepoBenSearch = reistrarRepoBenSearch;
	}

	@Autowired
	public void setRegistrarRepoBeneficiaryDetails(RegistrarRepoBeneficiaryDetails registrarRepoBeneficiaryDetails) {
		this.registrarRepoBeneficiaryDetails = registrarRepoBeneficiaryDetails;
	}

	@Override
	public BeneficiaryData createBeneficiary(JsonObject benD) {
		Long benRegID = null;
		// Call repository for saving data in
		// Table: i_beneficairy
		// Persistence Class: BeneficiaryData
		BeneficiaryData benData = registrarRepoBenData.save(getBenOBJ(benD));
		return benData;
	}

	@Override
	public Long createBeneficiaryDemographic(JsonObject benD, Long benRegID) {
		Long tmpBenDemoID = null;
		// Call repository for saving data in
		// Table: I_bendemographics
		// Persistence Class: BeneficiaryDemographicData
		BeneficiaryDemographicData benDemoData = registrarRepoBenDemoData.save(getBenDemoOBJ(benD, benRegID));
		if (benDemoData != null) {
			tmpBenDemoID = benDemoData.getBenDemographicsID();
		}
		return tmpBenDemoID;
	}

	@Override
	public Long createBeneficiaryDemographicAdditional(JsonObject benD, Long benRegID) {
		Long tmpBenDemoAddID = null;
		BeneficiaryDemographicAdditional beneficiaryDemographicAdditional = beneficiaryDemographicAdditionalRepo
				.save(getBeneficiaryDemographicAdditional(benD, benRegID));
		if (beneficiaryDemographicAdditional != null) {
			tmpBenDemoAddID = beneficiaryDemographicAdditional.getBenDemoAdditionalID();
		}
		return tmpBenDemoAddID;
	}

	@Override
	public Long createBeneficiaryImage(JsonObject benD, Long benRegID) {
		Long tmpBenImageID = null;
		BeneficiaryImage beneficiaryImage = new BeneficiaryImage();
		beneficiaryImage.setBeneficiaryRegID(benRegID);
		if (!benD.get("image").isJsonNull())
			beneficiaryImage.setBenImage(benD.get("image").getAsString());
		if (!benD.get("createdBy").isJsonNull())
			beneficiaryImage.setCreatedBy(benD.get("createdBy").getAsString());

		BeneficiaryImage benImage = beneficiaryImageRepo.save(beneficiaryImage);
		if (benImage != null) {
			tmpBenImageID = benImage.getBeneficiaryRegID();
		}
		return tmpBenImageID;
	}

	private BeneficiaryDemographicAdditional getBeneficiaryDemographicAdditional(JsonObject benD, Long benRegID) {
		BeneficiaryDemographicAdditional benDemoAd = new BeneficiaryDemographicAdditional();
		benDemoAd.setBeneficiaryRegID(benRegID);

		if (benD.has("literacyStatus") && !benD.get("literacyStatus").isJsonNull()) {
			benDemoAd.setLiteracyStatus(benD.get("literacyStatus").getAsString());
		}

		if (benD.has("motherName") && !benD.get("motherName").isJsonNull()) {
			benDemoAd.setMotherName(benD.get("motherName").getAsString());
		}
		if (benD.has("emailID") && !benD.get("emailID").isJsonNull()) {
			benDemoAd.setEmailID(benD.get("emailID").getAsString());
		}
		if (benD.has("bankName") && !benD.get("bankName").isJsonNull()) {
			benDemoAd.setBankName(benD.get("bankName").getAsString());
		}
		if (benD.has("branchName") && !benD.get("branchName").isJsonNull()) {
			benDemoAd.setBranchName(benD.get("branchName").getAsString());
		}
		if (benD.has("IFSCCode") && !benD.get("IFSCCode").isJsonNull()) {
			benDemoAd.setiFSCCode(benD.get("IFSCCode").getAsString());
		}
		if (benD.has("accountNumber") && !benD.get("accountNumber").isJsonNull()) {
			benDemoAd.setAccountNo(benD.get("accountNumber").getAsString());
		}
		if (benD.has("createdBy") && !benD.get("createdBy").isJsonNull())
			benDemoAd.setCreatedBy(benD.get("createdBy").getAsString());

		if (benD.has("ageAtMarriage") && !benD.get("ageAtMarriage").isJsonNull()) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

			java.util.Date parsedDate;
			int ageAtMarriage = benD.get("ageAtMarriage").getAsInt();
			int currentAge = benD.get("age").getAsInt();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, -(currentAge - ageAtMarriage));
			cal.set(Calendar.MONTH, 1);
			cal.set(Calendar.DAY_OF_YEAR, 1);

			// System.out.println(cal.getTime());

			Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
			// System.out.println(timestamp);
			benDemoAd.setMarrigeDate(timestamp);

		}

		// Following values will get only in update request
		if (benD.has("benDemoAdditionalID") && !benD.get("benDemoAdditionalID").isJsonNull()) {
			benDemoAd.setBenDemoAdditionalID(benD.get("benDemoAdditionalID").getAsLong());
		}
		if (benD.has("modifiedBy") && !benD.get("modifiedBy").isJsonNull()) {
			benDemoAd.setModifiedBy(benD.get("modifiedBy").getAsString());
		}
		return benDemoAd;
	}

	@Override
	public Long createBeneficiaryPhoneMapping(JsonObject benD, Long benRegID) {
		Long tmpBenPhonMapID = null;
		// Call repository for saving data in
		// Table: m_benphonemap
		// Persistence Class: BeneficiaryPhoneMapping
		BeneficiaryPhoneMapping benPhoneMap = registrarRepoBenPhoneMapData.save(getBenPhoneOBJ(benD, benRegID));
		if (benPhoneMap != null) {
			tmpBenPhonMapID = benPhoneMap.getBenPhMapID();
		}
		return tmpBenPhonMapID;
	}

	public int createBenGovIdMapping(JsonObject benD, Long benRegID) {
		Long tempBenGovMapID = null;
		// Call repository for saving Data to table m_bengovidmap and
		// Persistence Class = BenGovIdMapping
		// System.out.println("hello");
		ArrayList<BenGovIdMapping> benGovIDMap = (ArrayList<BenGovIdMapping>) registrarRepoBenGovIdMapping
				.save(BenGovIdMapping.getBenGovIdMappingOBJList(benD, benRegID));
		// System.out.println("hello");
		return benGovIDMap.size();
	}

	@Override
	public String getRegWorkList(int i) {
		// Call repository for fetching data from
		// Table: i_beneficiary, I_bendemographics, m_benphonemap
		// Persistence Class: BeneficiaryData, BeneficiaryDemographicData,
		// ...................BeneficiaryPhoneMapping
		List<Object[]> resList = registrarRepoBenData.getRegistrarWorkList(i);
		// System.out.println("helloo.....");
		return WrapperRegWorklist.getRegistrarWorkList(resList);
	}

	@Override
	public String getQuickSearchBenData(String benID) {
		// List<Object[]> resList = registrarRepoBenData.getQuickSearch(benID);
		List<Object[]> resList = reistrarRepoBenSearch.getQuickSearch(benID);
		// System.out.println("hello...");
		return WrapperRegWorklist.getRegistrarWorkList(resList);
	}

	public String getAdvanceSearchBenData(V_BenAdvanceSearch v_BenAdvanceSearch) {
		String result = "";
		try {
			String benID = "%%";
			String benFirstName = "";
			String benLastName = "";
			String fatherName = "";
			String phoneNo = "%%";
			String aadharNo = "%%";
			String govIDNo = "%%";
			String stateID = "%%";
			String districtID = "%%";

			if (null != v_BenAdvanceSearch.getBeneficiaryID() && !v_BenAdvanceSearch.getBeneficiaryID().isEmpty()) {
				benID = v_BenAdvanceSearch.getBeneficiaryID();
			}
			if (null != v_BenAdvanceSearch.getFirstName() && !v_BenAdvanceSearch.getFirstName().isEmpty()) {
				benFirstName = v_BenAdvanceSearch.getFirstName();
			}
			if (null != v_BenAdvanceSearch.getLastName() && !v_BenAdvanceSearch.getLastName().isEmpty()) {
				benLastName = v_BenAdvanceSearch.getLastName();
			}
			if (null != v_BenAdvanceSearch.getFatherName() && !v_BenAdvanceSearch.getFatherName().isEmpty()) {
				fatherName = v_BenAdvanceSearch.getFatherName();
			}
			if (null != v_BenAdvanceSearch.getPhoneNo() && !v_BenAdvanceSearch.getPhoneNo().isEmpty()) {
				phoneNo = v_BenAdvanceSearch.getPhoneNo();
			}
			if (null != v_BenAdvanceSearch.getAadharNo() && !v_BenAdvanceSearch.getAadharNo().isEmpty()) {
				aadharNo = v_BenAdvanceSearch.getAadharNo();
			}
			if (null != v_BenAdvanceSearch.getGovtIdentityNo() && !v_BenAdvanceSearch.getGovtIdentityNo().isEmpty()) {
				govIDNo = v_BenAdvanceSearch.getGovtIdentityNo();
			}
			if (null != v_BenAdvanceSearch.getStateID()) {
				stateID = v_BenAdvanceSearch.getStateID() + "";
			}
			if (null != v_BenAdvanceSearch.getDistrictID()) {
				districtID = v_BenAdvanceSearch.getDistrictID() + "";
			}
			/*
			 * reistrarRepoBenSearch.getAdvanceBenSearchList(benID, benFirstName,
			 * benLastName, benGenderID, fatherName, phoneNo, aadharNo, govIDNo,
			 * districtID);
			 */
			ArrayList<Object[]> resList = reistrarRepoBenSearch.getAdvanceBenSearchList(benID, benFirstName,
					benLastName, phoneNo, aadharNo, govIDNo, stateID, districtID);

			result = WrapperRegWorklist.getRegistrarWorkList(resList);
			// System.out.println(resList);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	public BeneficiaryData getBenOBJ(JsonObject benD) {
		// Initializing BeneficiaryData Class Object...

		BeneficiaryData benData = new BeneficiaryData();
		if (benD.has("firstName") && !benD.get("firstName").isJsonNull())
			benData.setFirstName(benD.get("firstName").getAsString());
		if (benD.has("lastName") && !benD.get("lastName").isJsonNull())
			benData.setLastName(benD.get("lastName").getAsString());
		if (benD.has("gender") && !benD.get("gender").isJsonNull())
			benData.setGenderID(benD.get("gender").getAsShort());
		if (benD.has("dob") && !benD.get("dob").isJsonNull()) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
			java.util.Date parsedDate;
			try {
				parsedDate = dateFormat.parse(benD.get("dob").getAsString());
				Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
				benData.setDob(timestamp);
				// System.out.println("hello");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}

		}
		if (benD.has("maritalStatus") && !benD.get("maritalStatus").isJsonNull())
			benData.setMaritalStatusID(benD.get("maritalStatus").getAsShort());
		if (benD.has("createdBy") && !benD.get("createdBy").isJsonNull())
			benData.setCreatedBy(benD.get("createdBy").getAsString());
		if (benD.has("fatherName") && !benD.get("fatherName").isJsonNull())
			benData.setFatherName(benD.get("fatherName").getAsString());
		if (benD.has("husbandName")) {
			if (!benD.get("husbandName").isJsonNull())
				benData.setSpouseName(benD.get("husbandName").getAsString());
		}

		if (benD.has("aadharNo") && !benD.get("aadharNo").isJsonNull())
			benData.setAadharNo(benD.get("aadharNo").getAsString());
		// System.out.println(benData);
		// Following values will get only in update request
		if (benD.has("beneficiaryRegID")) {
			if (!benD.get("beneficiaryRegID").isJsonNull()) {
				benData.setBeneficiaryRegID(benD.get("beneficiaryRegID").getAsLong());
			}
		}
		if (benD.has("modifiedBy")) {
			if (!benD.get("modifiedBy").isJsonNull()) {
				benData.setModifiedBy(benD.get("modifiedBy").getAsString());
			}
		}
		// System.out.println(benData);
		return benData;
	}

	public BeneficiaryDemographicData getBenDemoOBJ(JsonObject benD, Long benRegID) {
		// Initializing BeneficiaryDemographicData Class Object...
		BeneficiaryDemographicData benDemoData = new BeneficiaryDemographicData();
		benDemoData.setBeneficiaryRegID(benRegID);
		if (benD.has("countryID") && !benD.get("countryID").isJsonNull())
			benDemoData.setCountryID(benD.get("countryID").getAsInt());
		if (benD.has("stateID") && !benD.get("stateID").isJsonNull())
			benDemoData.setStateID(benD.get("stateID").getAsInt());
		if (benD.has("districtID") && !benD.get("districtID").isJsonNull())
			benDemoData.setDistrictID(benD.get("districtID").getAsInt());
		if (benD.has("blockID") && !benD.get("blockID").isJsonNull())
			benDemoData.setBlockID(benD.get("blockID").getAsInt());
		if (benD.has("servicePointID") && !benD.get("servicePointID").isJsonNull())
			benDemoData.setServicePointID(benD.get("servicePointID").getAsInt());
		if (benD.has("villageID") && !benD.get("villageID").isJsonNull())
			benDemoData.setDistrictBranchID(benD.get("villageID").getAsInt());

		if (benD.has("createdBy") && !benD.get("createdBy").isJsonNull())
			benDemoData.setCreatedBy(benD.get("createdBy").getAsString());

		if (benD.has("community") && !benD.get("community").isJsonNull())
			benDemoData.setCommunityID(benD.get("community").getAsShort());
		if (benD.has("religion") && !benD.get("religion").isJsonNull())
			benDemoData.setReligionID(benD.get("religion").getAsShort());
		if (benD.has("occupation") && !benD.get("occupation").isJsonNull())
			benDemoData.setOccupationID(benD.get("occupation").getAsShort());
		if (benD.has("educationQualification") && !benD.get("educationQualification").isJsonNull())
			benDemoData.setEducationID(benD.get("educationQualification").getAsShort());
		if (benD.has("income") && !benD.get("income").isJsonNull())
			benDemoData.setIncomeStatusID(benD.get("income").getAsShort());
		// System.out.println(benDemoData);
		// Following values will get only in update request
		if (benD.has("benDemographicsID") && !benD.get("benDemographicsID").isJsonNull())
			benDemoData.setBenDemographicsID(benD.get("benDemographicsID").getAsLong());
		if (benD.has("modifiedBy") && !benD.get("modifiedBy").isJsonNull())
			benDemoData.setModifiedBy(benD.get("modifiedBy").getAsString());
		// System.out.println(benDemoData);
		return benDemoData;
	}

	public BeneficiaryPhoneMapping getBenPhoneOBJ(JsonObject benD, Long benRegID) {
		// Initializing BeneficiaryPhoneMapping Class Object...
		BeneficiaryPhoneMapping benPhoneMap = new BeneficiaryPhoneMapping();
		benPhoneMap.setBenificiaryRegID(benRegID);
		if (benD.has("phoneNo") && !benD.get("phoneNo").isJsonNull())
			benPhoneMap.setPhoneNo(benD.get("phoneNo").getAsString());
		if (benD.has("createdBy") && !benD.get("createdBy").isJsonNull())
			benPhoneMap.setCreatedBy(benD.get("createdBy").getAsString());
		// System.out.println(benPhoneMap);
		// Following values will get only in update request
		if (benD.has("benPhMapID") && !benD.get("benPhMapID").isJsonNull())
			benPhoneMap.setBenPhMapID(benD.get("benPhMapID").getAsLong());
		if (benD.has("modifiedBy") && !benD.get("modifiedBy").isJsonNull())
			benPhoneMap.setModifiedBy(benD.get("modifiedBy").getAsString());
		// System.out.println(benPhoneMap);
		return benPhoneMap;
	}

	@Override
	public String getBeneficiaryDetails(Long beneficiaryRegID) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.serializeNulls();
		Gson gson = gsonBuilder.create();
		List<Object[]> resList = registrarRepoBeneficiaryDetails.getBeneficiaryDetails(beneficiaryRegID);

		// System.out.println("hello");

		if (resList != null && resList.size() > 0) {

			ArrayList<Map<String, Object>> govIdList = new ArrayList<>();
			ArrayList<Map<String, Object>> otherGovIdList = new ArrayList<>();
			Map<String, Object> govIDMap;
			Map<String, Object> otherGovIDMap;
			Object[] objarr = resList.get(0);
			// System.out.println("helooo");
			for (Object[] arrayObj : resList) {
				if (arrayObj[26] != null) {
					if ((Boolean) arrayObj[26] == true) {
						govIDMap = new HashMap<>();

						govIDMap.put("type", arrayObj[24]);
						govIDMap.put("value", arrayObj[25]);
						govIDMap.put("isGovType", arrayObj[26]);
						govIdList.add(govIDMap);
					} else {
						otherGovIDMap = new HashMap<>();

						otherGovIDMap.put("type", arrayObj[24]);
						otherGovIDMap.put("value", arrayObj[25]);
						otherGovIDMap.put("isGovType", arrayObj[26]);
						otherGovIdList.add(otherGovIDMap);
					}
				}

			}
			String s = beneficiaryImageRepo.getBenImage(beneficiaryRegID);
			FetchBeneficiaryDetails fetchBeneficiaryDetailsOBJ = FetchBeneficiaryDetails.getBeneficiaryDetails(objarr,
					govIdList, s, otherGovIdList);
			// System.out.println("helooo");
			return gson.toJson(fetchBeneficiaryDetailsOBJ);
		} else {
			// System.out.println("helooo");
			return null;
		}

		// System.out.println("helooo");

		// ArrayList<FetchBeneficiaryDetails> beneficiaryDetails =
		// FetchBeneficiaryDetails.getBeneficiaryDetails(resList);

	}

	@Override
	public String getBenImage(Long benRegID) {
		Map<String, String> benImageMap = new HashMap<String, String>();
		String s = beneficiaryImageRepo.getBenImage(benRegID);
		if (s != null)
			benImageMap.put("benImage", s);

		return new Gson().toJson(benImageMap);
	}

	@Override
	public int updateBeneficiary(JsonObject benD) {
		Long benRegID = null;
		// Call repository for updating data in
		// Table: i_beneficairy
		// Persistence Class: BeneficiaryData
		BeneficiaryData beneficiaryData = getBenOBJ(benD);
		int response = registrarRepoBenData.updateBeneficiaryData(beneficiaryData.getFirstName(),
				beneficiaryData.getLastName(), beneficiaryData.getGenderID(), beneficiaryData.getDob(),
				beneficiaryData.getMaritalStatusID(), beneficiaryData.getFatherName(), beneficiaryData.getSpouseName(),
				beneficiaryData.getAadharNo(), beneficiaryData.getModifiedBy(), beneficiaryData.getBeneficiaryRegID());
		return response;
	}

	@Override
	public int updateBeneficiaryDemographic(JsonObject benD, Long benRegID) {
		Long tmpBenDemoID = null;
		// Call repository for updating data in
		// Table: I_bendemographics
		// Persistence Class: BeneficiaryDemographicData
		BeneficiaryDemographicData benDemographicData = getBenDemoOBJ(benD, benRegID);
		Integer benDemoData = registrarRepoBenDemoData.updateBendemographicData(benDemographicData.getCountryID(),
				benDemographicData.getStateID(), benDemographicData.getDistrictID(), benDemographicData.getAreaID(),
				benDemographicData.getServicePointID(), benDemographicData.getDistrictBranchID(),
				benDemographicData.getCommunityID(), benDemographicData.getReligionID(),
				benDemographicData.getOccupationID(), benDemographicData.getEducationID(),
				benDemographicData.getIncomeStatusID(), benDemographicData.getModifiedBy(),
				benDemographicData.getBeneficiaryRegID());

		return benDemoData;
	}

	@Override
	public int updateBeneficiaryPhoneMapping(JsonObject benD, Long benRegID) {
		Long tmpBenPhonMapID = null;
		// Call repository for updating data in
		// Table: m_benphonemap
		// Persistence Class: BeneficiaryPhoneMapping
		BeneficiaryPhoneMapping benPhoneMap = getBenPhoneOBJ(benD, benRegID);
		Integer benPhoneMapRes = registrarRepoBenPhoneMapData.updateBenPhoneMap(benPhoneMap.getPhoneNo(),
				benPhoneMap.getModifiedBy(), benPhoneMap.getBenificiaryRegID());

		return benPhoneMapRes;
	}

	@Override
	public int updateBenGovIdMapping(JsonObject benD, Long benRegID) {
		Long tempBenGovMapID = null;
		// Call repository for Delete Data from table m_bengovidmap and save
		// Data to table m_bengovidmap
		// Persistence Class = BenGovIdMapping
		ArrayList<BenGovIdMapping> benGovIDMap = BenGovIdMapping.getBenGovIdMappingOBJList(benD, benRegID);
		// List IDsToDelete = new ArrayList();
		// for (BenGovIdMapping benGovID : benGovIDMap) {
		// if (null != benGovID.getID()) {
		// // delete
		// //registrarRepoBenGovIdMapping.delete(benGovID);
		// IDsToDelete.add(benGovID);
		// benGovIDMap.remove(benGovIDMap.indexOf(benGovID));
		// }
		// }

		int x = registrarRepoBenGovIdMapping.deletePreviousGovMapID(benRegID);
		ArrayList<BenGovIdMapping> benGovIDMaps = (ArrayList<BenGovIdMapping>) registrarRepoBenGovIdMapping
				.save(benGovIDMap);

		return benGovIDMaps.size();
	}

	@Override
	public int updateBeneficiaryDemographicAdditional(JsonObject benD, Long benRegID) {
		Long tmpBenDemoAddID = null;

		BeneficiaryDemographicAdditional beneficiaryDemographicAdditional = getBeneficiaryDemographicAdditional(benD,
				benRegID);
		int res = 0;
		BeneficiaryDemographicAdditional demographicAdditionalData = beneficiaryDemographicAdditionalRepo
				.getBeneficiaryDemographicAdditional(benRegID);
		if (null != demographicAdditionalData) {
			res = beneficiaryDemographicAdditionalRepo.updateBeneficiaryDemographicAdditional(
					beneficiaryDemographicAdditional.getLiteracyStatus(),
					beneficiaryDemographicAdditional.getMotherName(), beneficiaryDemographicAdditional.getEmailID(),
					beneficiaryDemographicAdditional.getBankName(), beneficiaryDemographicAdditional.getBranchName(),
					beneficiaryDemographicAdditional.getiFSCCode(), beneficiaryDemographicAdditional.getAccountNo(),
					beneficiaryDemographicAdditional.getModifiedBy(),
					beneficiaryDemographicAdditional.getBeneficiaryRegID());
		} else {
			BeneficiaryDemographicAdditional data = beneficiaryDemographicAdditionalRepo
					.save(beneficiaryDemographicAdditional);
			if (data.getBenDemoAdditionalID() > 0) {
				res = 1;
			}
		}

		return res;
	}

	@Override
	public int updateBeneficiaryImage(JsonObject benD, Long benRegID) {
		Long tmpBenImageID = null;
		BeneficiaryImage beneficiaryImage = new BeneficiaryImage();
		beneficiaryImage.setBeneficiaryRegID(benRegID);
		Integer response = 0;
		if (benD.has("image") && !benD.get("image").isJsonNull()) {
			beneficiaryImage.setBenImage(benD.get("image").getAsString());
			if (benD.has("createdBy") && !benD.get("createdBy").isJsonNull())
				beneficiaryImage.setCreatedBy(benD.get("createdBy").getAsString());

			// Following values will get only in update request
			if (benD.has("benImageID") && !benD.get("benImageID").isJsonNull()) {
				beneficiaryImage.setBenImageID(benD.get("benImageID").getAsLong());
			}
			if (benD.has("modifiedBy") && !benD.get("modifiedBy").isJsonNull()) {
				beneficiaryImage.setModifiedBy(benD.get("modifiedBy").getAsString());
			}

			Long tempBenRegID = beneficiaryImageRepo.findBenImage(benRegID);
			if (null != tempBenRegID) {
				response = beneficiaryImageRepo.updateBeneficiaryImage(beneficiaryImage.getBenImage(),
						beneficiaryImage.getModifiedBy(), beneficiaryImage.getBeneficiaryRegID());
			} else {
				BeneficiaryImage data = beneficiaryImageRepo.save(beneficiaryImage);
				if (data.getBenImageID() > 0) {
					response = 1;
				}
			}

		} else {
			// Nothing failed, No need to update if their is no image value sent
			response = 1;
		}

		return response;
	}

	public BeneficiaryData getBeneficiaryPersonalDetails(Long benRegID) {
		List<Objects[]> beneficiaryDemographicData = registrarRepoBenDemoData.getBeneficiaryDemographicData(benRegID);

		List<Object[]> benDetailsList = registrarRepoBenData.getBenDetailsByRegID(benRegID);
		BeneficiaryData benDetails = null;
		if (benDetailsList.size() > 0) {
			benDetails = BeneficiaryData.getBeneficiaryPersonalData(benDetailsList).get(0);
		}
		if (benDetails != null) {
			if (benDetails.getGenderID() != null) {
				if (benDetails.getGenderID() == 1) {
					benDetails.setGenderName("Male");
				} else if (benDetails.getGenderID() == 2) {
					benDetails.setGenderName("Female");
				} else {
					if (benDetails.getGenderID() == 3) {
						benDetails.setGenderName("Transgender");
					}
				}
			}
		}
		if (null != beneficiaryDemographicData && benDetails != null) {
			for (Object[] obj : beneficiaryDemographicData) {
				benDetails.setServicePointName((String) obj[2]);
			}
		}
		return benDetails;
	}

	// New beneficiary registration with common and identity
	public String registerBeneficiary(String comingRequest, String Authorization) throws Exception {

		OutputResponse response1 = new OutputResponse();
		Long beneficiaryRegID = null;
		Long beneficiaryID = null;

		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", MediaType.APPLICATION_JSON + ";charset=utf-8");
		// headers.add("Content-Type", MediaType.APPLICATION_JSON);
		headers.add("AUTHORIZATION", Authorization);
		HttpEntity<Object> request = new HttpEntity<Object>(comingRequest, headers);
		ResponseEntity<String> response = restTemplate.exchange(registrationUrl, HttpMethod.POST, request,
				String.class);
		if (response.getStatusCodeValue() == 200 & response.hasBody()) {
			String responseStr = response.getBody();
			JSONObject responseOBJ = new JSONObject(responseStr);
			beneficiaryRegID = responseOBJ.getJSONObject("data").getLong("beneficiaryRegID");
			beneficiaryID = responseOBJ.getJSONObject("data").getLong("beneficiaryID");
			// System.out.println("hello");

			BeneficiaryFlowStatus obj = InputMapper.gson().fromJson(comingRequest, BeneficiaryFlowStatus.class);
			if (obj != null && obj.getIsMobile() != null && obj.getIsMobile()) {
				response1.setResponse("Beneficiary successfully registered. Beneficiary ID is : " + beneficiaryID);
			} else {
				int i = commonBenStatusFlowServiceImpl.createBenFlowRecord(comingRequest, beneficiaryRegID,
						beneficiaryID);

				if (i > 0) {
					if (i == 1)
						response1.setResponse(
								"Beneficiary successfully registered. Beneficiary ID is : " + beneficiaryID);
				} else {
					response1.setError(5000, "Error in registration; please contact administrator");
					// log error that beneficiaryID generated but flow part is not
					// done successfully.
				}
			}
		} else {
			// log error that registration failed.
		}
		return response1.toString();
	}

	// beneficiary advance search new integrated with common and identity
	public String getBeneficiaryByBlockIDAndLastModDate(String villageID, Date lastModifDate, String Authorization) {
		String returnOBJ = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			JSONObject obj = new JSONObject();
			obj.put("villageID", Integer.parseInt(villageID));
			obj.put("lastModifiedDate", lastModifDate.getTime());

			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
			headers.add("Content-Type", "application/json");
			HttpEntity<String> request = new HttpEntity<String>(obj.toString(), headers);
			ResponseEntity<String> response = restTemplate.exchange(syncSearchByLocation, HttpMethod.POST, request,
					String.class);

			if (response.hasBody()){
				returnOBJ = response.getBody();
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}

		return returnOBJ;

	}

	// New beneficiary update api
	public Integer updateBeneficiary(String comingRequest, String Authorization) throws Exception {
		Integer returnOBJ = null;
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		// headers.add("Content-Type", "application/json");
		headers.add("Content-Type", MediaType.APPLICATION_JSON + ";charset=utf-8");
		headers.add("AUTHORIZATION", Authorization);
		HttpEntity<Object> request = new HttpEntity<Object>(comingRequest, headers);
		ResponseEntity<String> response = restTemplate.exchange(beneficiaryEditUrl, HttpMethod.POST, request,
				String.class);

		if (response.getStatusCodeValue() == 200) {
			BeneficiaryFlowStatus obj = InputMapper.gson().fromJson(comingRequest, BeneficiaryFlowStatus.class);
			if (obj.getPassToNurse() != null && obj.getPassToNurse() == true)
				returnOBJ = searchAndSubmitBeneficiaryToNurse(comingRequest);
			else
				returnOBJ = 1;
		}
		return returnOBJ;
	}

	// beneficiary quick search new integrated with common and identity
	public String beneficiaryQuickSearch(String requestObj, String Authorization) {
		String returnOBJ = null;
		RestTemplate restTemplate = new RestTemplate();
		JSONObject obj = new JSONObject(requestObj);
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", "application/json");
		headers.add("AUTHORIZATION", Authorization);
		if ((obj.has("beneficiaryID") && !obj.isNull("beneficiaryID"))
				|| (obj.has("HealthID") && !obj.isNull("HealthID"))
				|| (obj.has("HealthIDNumber") && !obj.isNull("HealthIDNumber"))
				|| (obj.has("familyId") && !obj.isNull("familyId"))
				|| (obj.has("identity") && !obj.isNull("identity"))) {
			HttpEntity<Object> request = new HttpEntity<Object>(requestObj, headers);
			ResponseEntity<String> response = restTemplate.exchange(registrarQuickSearchByIdUrl, HttpMethod.POST,
					request, String.class);
			if (response.hasBody())
				returnOBJ = response.getBody();

		} else {
			if (obj.has("phoneNo") && !obj.isNull("phoneNo")) {
				HttpEntity<Object> request = new HttpEntity<Object>(requestObj, headers);
				ResponseEntity<String> response = restTemplate.exchange(registrarQuickSearchByPhoneNoUrl,
						HttpMethod.POST, request, String.class);
				if (response.hasBody())
					returnOBJ = response.getBody();
			} else {
			}
		}
		return returnOBJ;
	}

	// beneficiary advance search new integrated with common and identity
	public String beneficiaryAdvanceSearch(String requestObj, String Authorization) {
		String returnOBJ = null;
		RestTemplate restTemplate = new RestTemplate();
		JSONObject obj = new JSONObject(requestObj);
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", "application/json");
		headers.add("AUTHORIZATION", Authorization);
		HttpEntity<Object> request = new HttpEntity<Object>(requestObj, headers);
		ResponseEntity<String> response = restTemplate.exchange(registrarAdvanceSearchUrl, HttpMethod.POST, request,
				String.class);

		if (response.hasBody())
			returnOBJ = response.getBody();

		return returnOBJ;

	}

	public int searchAndSubmitBeneficiaryToNurse(String requestOBJ) throws Exception {
		int i = commonBenStatusFlowServiceImpl.createBenFlowRecord(requestOBJ, null, null);
		return i;
	}

	public String saveFingerprints(FingerPrintDTO comingRequest) {
		String response = "";
		Users user = userRepo.getUserByUsername(comingRequest.getUserName());
		if(user !=null){
			UserBiometricsMapping userBiometricsMapping = new UserBiometricsMapping();
			userBiometricsMapping.setUserID(user.getUserID());
			userBiometricsMapping.setFirstName(user.getFirstName());
			userBiometricsMapping.setLastName(user.getLastName());
			userBiometricsMapping.setUserName(user.getUserName());
			userBiometricsMapping.setCreatedBy(user.getUserName());
			userBiometricsMapping.setRightThumb(comingRequest.getRightThumb());
			userBiometricsMapping.setRightIndexFinger(comingRequest.getRightIndexFinger());
			userBiometricsMapping.setLeftThumb(comingRequest.getLeftThumb());
			userBiometricsMapping.setLeftIndexFinger(comingRequest.getLeftIndexFinger());

			UserBiometricsMapping resp = userBiometricsRepo.save(userBiometricsMapping);
			if(resp !=null){
				response = "ok";
			}
			else{
				response = "ko";
			}
		}
		else{
			response = "wrong username";
		}

		return response;
	}

	public UserBiometricsMapping getFingerprintsByUserID(Long userID) {
		UserBiometricsMapping user = userBiometricsRepo.getFingerprintsByUserID(userID);
		return user;
	}
}
