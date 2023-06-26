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
package com.iemr.hwc.controller.registrar.master;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping({ "/registrar" })
/**
 * Objective: Performs Beneficiary Registration, Update, QuickSearch,
 * AdvancedSearch
 */
public class RegistrarController {

	/*
	 * private Logger logger =
	 * LoggerFactory.getLogger(RegistrarController.class); private InputMapper
	 * inputMapper = new InputMapper(); private RegistrarServiceImpl
	 * registrarServiceImpl; private RegistrarServiceMasterDataImpl
	 * registrarServiceMasterDataImpl; private NurseServiceImpl
	 * nurseServiceImpl;
	 * 
	 * @Autowired public void setRegistrarServiceImpl(RegistrarServiceImpl
	 * registrarServiceImpl) { this.registrarServiceImpl = registrarServiceImpl;
	 * }
	 * 
	 * @Autowired public void
	 * setRegistrarServiceMasterDataImpl(RegistrarServiceMasterDataImpl
	 * registrarServiceMasterDataImpl) { this.registrarServiceMasterDataImpl =
	 * registrarServiceMasterDataImpl; }
	 * 
	 * @Autowired public void setNurseServiceImpl(NurseServiceImpl
	 * nurseServiceImpl) { this.nurseServiceImpl = nurseServiceImpl; }
	 * 
	 * // Registrar Work List API .....
	 * 
	 * @CrossOrigin()
	 * 
	 * @ApiOperation(value = "Get Registrar workList Data", consumes =
	 * "application/json", produces = "application/json")
	 * 
	 * @RequestMapping(value = { "/registrarWorkListData" }, method = {
	 * RequestMethod.POST }) public String getRegistrarWorkList(@ApiParam(value
	 * = "{\"spID\": \"Integer\"}") @RequestBody String comingRequest) throws
	 * JSONException { OutputResponse response = new OutputResponse();
	 * logger.info("getRegistrarWorkList request:" + comingRequest); try {
	 * 
	 * JSONObject obj = new JSONObject(comingRequest);
	 * 
	 * // wrapperRegWorklistArray = //
	 * this.registrarServiceImpl.getRegWorkList(obj.getInt("spID"));
	 * response.setResponse(this.registrarServiceImpl.getRegWorkList(obj.getInt(
	 * "spID"))); logger.info("getRegistrarWorkList response:" + response); }
	 * catch (Exception e) { response.setError(e);
	 * logger.error("Error in getRegistrarWorkList:" + e); } return
	 * response.toString(); }
	 * 
	 * // Registrar Beneficiary Registration API .....
	 * 
	 * @CrossOrigin()
	 * 
	 * @ApiOperation(value = "Register a new Beneficiary", consumes =
	 * "application/json", produces = "application/json")
	 * 
	 * @RequestMapping(value = { "/registrarBeneficaryRegistration" }, method =
	 * { RequestMethod.POST }) public String createBeneficiary(
	 * 
	 * @ApiParam(value =
	 * "{\"benD\":{\"firstName\": \"String\", \"lastName\": \"String\", \"gender\": \"Short\","
	 * +
	 * "\"dob\": \"Timestamp\", \"maritalStatus\": \"Short\", \"fatherName\": \"String\", \"motherName\": \"String\","
	 * +
	 * "\"husbandName\": \"String\", \"image\": \"String\", \"aadharNo\": \"String\", \"income\": \"Short\", "
	 * +
	 * "\"literacyStatus\": \"String\", \"educationQualification\": \"Short\", \"occupation\": \"Short\", \"phoneNo\": \"String\","
	 * +
	 * "\"emailID\": \"Integer\", \"bankName\": \"String\", \"branchName\": \"String\", \"IFSCCode\": \"String\", \"accountNumber\": \"String\","
	 * +
	 * "\"community\": \"Short\", \"religion\": \"Short\", \"blockID\": \"Integer\", \"blockName\": \"String\","
	 * +
	 * "\"habitation\": \"String\", \"villageID\": \"Integer\", \"villageName\": \"String\", \"districtID\": \"Integer\","
	 * +
	 * "\"districtName\": \"String\", \"stateID\": \"Integer\", \"stateName\": \"String\", \"countryID\": \"Integer\","
	 * +
	 * "\"govID\": [{\"type\": \"String\",\"value\": \"String\"}], \"ageAtMarriage\": \"Integer\", \"createdBy\": \"String\", "
	 * + "\"servicePointID\": \"Integer\"}}") @RequestBody String comingRequest)
	 * {
	 * 
	 * OutputResponse response = new OutputResponse(); try {
	 * 
	 * // JsonObject responseOBJ = new JsonObject();
	 * WrapperBeneficiaryRegistration wrapperBeneficiaryRegistrationOBJ =
	 * InputMapper.gson() .fromJson(comingRequest,
	 * WrapperBeneficiaryRegistration.class);
	 * logger.info("createBeneficiary request:" + comingRequest); JsonObject
	 * benD = wrapperBeneficiaryRegistrationOBJ.getBenD();
	 * 
	 * if (benD == null || benD.isJsonNull()) { response.setError(0,
	 * "Data Not Sufficient..."); } else { BeneficiaryData benData =
	 * registrarServiceImpl.createBeneficiary(benD); if (benData != null) { Long
	 * benRegID = benData.getBeneficiaryRegID(); Long benDemoID =
	 * registrarServiceImpl.createBeneficiaryDemographic(benD, benRegID); Long
	 * benPhonMapID = registrarServiceImpl.createBeneficiaryPhoneMapping(benD,
	 * benRegID);
	 * 
	 * int benGovIdMapID = registrarServiceImpl.createBenGovIdMapping(benD,
	 * benRegID);
	 * 
	 * Long benbenDemoOtherID =
	 * registrarServiceImpl.createBeneficiaryDemographicAdditional(benD,
	 * benRegID);
	 * 
	 * Long benImageID = registrarServiceImpl.createBeneficiaryImage(benD,
	 * benRegID);
	 * 
	 * if (benRegID > 0 && benDemoID > 0 && benPhonMapID > 0 &&
	 * benbenDemoOtherID > 0 && benImageID > 0) { Integer i =
	 * nurseServiceImpl.updateBeneficiaryStatus('R', benRegID); if
	 * (benData.getBeneficiaryID() != null) {
	 * response.setResponse(benData.getBeneficiaryID()); } else { //
	 * i_beneficiary, I_bendemographics and // m_benphonemap // roll-back
	 * response.
	 * setResponse("Registration Done But BeneficiaryID Not Generated!!!"); } }
	 * else { // i_beneficiary, I_bendemographics and m_benphonemap // roll-back
	 * response.setError(500, "Something Went-Wrong"); } } else { //
	 * i_beneficiary roll-back response.setError(500, "Something Went-Wrong"); }
	 * } logger.info("createBeneficiary response:" + response); } catch
	 * (Exception e) { logger.error("Error in createBeneficiary :" + e);
	 * response.setError(e); } return response.toString(); }
	 * 
	 * // Registrar Quick search .....
	 * 
	 * @CrossOrigin()
	 * 
	 * @ApiOperation(value = "Search for the Beneficiary by BeneficiaryID",
	 * consumes = "application/json", produces = "application/json")
	 * 
	 * @RequestMapping(value = { "/quickSearch" }, method = { RequestMethod.POST
	 * }) public String quickSearchBeneficiary(
	 * 
	 * @ApiParam(value = "{\"benID\": \"String\"}") @RequestBody String
	 * comingRequest) { OutputResponse response = new OutputResponse();
	 * logger.info("quickSearchBeneficiary request:" + comingRequest); try {
	 * 
	 * JSONObject obj = new JSONObject(comingRequest);
	 * 
	 * // wrapperRegWorklistArray = //
	 * registrarServiceImpl.getQuickSearchBenData(obj.getString("benID"));
	 * response.setResponse(registrarServiceImpl.getQuickSearchBenData(obj.
	 * getString("benID"))); logger.info("quickSearchBeneficiary response:" +
	 * response); } catch (Exception e) {
	 * logger.error("Error in quickSearchBeneficiary :" + e);
	 * response.setError(e); } return response.toString(); }
	 * 
	 * // Registrar Advance search .....
	 * 
	 * @CrossOrigin()
	 * 
	 * @ApiOperation(value =
	 * "Search for the Beneficiary based on provided data", consumes =
	 * "application/json", produces = "application/json")
	 * 
	 * @RequestMapping(value = { "/advanceSearch" }, method = {
	 * RequestMethod.POST }) public String advanceSearch(
	 * 
	 * @ApiParam(value =
	 * "{\"firstName\": \"String\", \"lastName\": \"String\", \"phoneNo\": \"String\","
	 * +
	 * "\"beneficiaryID\": \"String\", \"stateID\": \"Integer\", \"districtID\": \"Integer\", \"aadharNo\": \"String\"},"
	 * + " \"govtIdentityNo\": \"String\"}") @RequestBody String comingRequest)
	 * { OutputResponse response = new OutputResponse();
	 * logger.info("advanceSearch request :" + comingRequest); try { //
	 * JSONObject obj = new JSONObject(comingRequest); V_BenAdvanceSearch
	 * v_BenAdvanceSearch = inputMapper.gson().fromJson(comingRequest,
	 * V_BenAdvanceSearch.class);
	 * response.setResponse(registrarServiceImpl.getAdvanceSearchBenData(
	 * v_BenAdvanceSearch)); logger.info("advanceSearch response:" + response);
	 * } catch (Exception e) { logger.error("Error in advanceSearch :" + e);
	 * response.setError(e); }
	 * 
	 * return response.toString(); }
	 * 
	 * @CrossOrigin()
	 * 
	 * @ApiOperation(value =
	 * "Get Beneficiary Details of given beneficiaryRegID", consumes =
	 * "application/json", produces = "application/json")
	 * 
	 * @RequestMapping(value = { "/get/benDetailsByRegID" }, method = {
	 * RequestMethod.POST }) public String getBenDetailsByRegID(
	 * 
	 * @ApiParam(value = "{\"beneficiaryRegID\": \"Long\"}") @RequestBody String
	 * comingRequest) { OutputResponse response = new OutputResponse();
	 * logger.info("getBenDetailsByRegID request :" + comingRequest); try {
	 * 
	 * JSONObject obj = new JSONObject(comingRequest); if
	 * (obj.has("beneficiaryRegID")) { if (obj.getLong("beneficiaryRegID") > 0)
	 * {
	 * 
	 * String beneficiaryData = registrarServiceMasterDataImpl
	 * .getBenDetailsByRegID(obj.getLong("beneficiaryRegID"));
	 * 
	 * response.setResponse(beneficiaryData); } else { response.setError(500,
	 * "Please pass beneficiaryRegID"); } } else { response.setError(500,
	 * "Bad Request... beneficiaryRegID is not there in request"); }
	 * logger.info("getBenDetailsByRegID response :" + response); } catch
	 * (Exception e) { logger.error("Error in getBenDetailsByRegID :" + e);
	 * response.setError(e); } return response.toString(); }
	 * 
	 * @CrossOrigin
	 * 
	 * @ApiOperation(value = "update Beneficiary Status Flag", consumes =
	 * "application/json", produces = "application/json")
	 * 
	 * @RequestMapping(value = { "/update/benDetailsAndSubmitToNurse" }, method
	 * = { RequestMethod.POST }) public String saveBeneficiaryVisitDetail(
	 * 
	 * @ApiParam(value = "{\"beneficiaryRegID\": \"Long\"}") @RequestBody String
	 * comingRequest) {
	 * 
	 * OutputResponse response = new OutputResponse(); inputMapper = new
	 * InputMapper(); logger.info("benDetailsAndSubmitToNurse request:" +
	 * comingRequest); try { JSONObject obj = new JSONObject(comingRequest); if
	 * (obj.has("beneficiaryRegID")) { if (obj.getLong("beneficiaryRegID") > 0)
	 * {
	 * 
	 * Integer i = nurseServiceImpl.updateBeneficiaryStatus('R',
	 * obj.getLong("beneficiaryRegID")); if (i != null && i > 0) { response.
	 * setResponse("Beneficiary Successfully Submitted to Nurse Work-List."); }
	 * else { response.setError(500,
	 * "Something went Wrong please try after Some Time !!!"); }
	 * 
	 * } else { response.setError(500,
	 * "Beneficiary Registration ID is Not valid !!!"); } } else {
	 * response.setError(500, "Beneficiary Registration ID is Not valid !!!"); }
	 * logger.info("benDetailsAndSubmitToNurse response:" + response); } catch
	 * (Exception e) { response.setError(e);
	 * logger.error("Error in benDetailsAndSubmitToNurse:" + e); }
	 * 
	 * return response.toString(); }
	 * 
	 * @CrossOrigin()
	 * 
	 * @ApiOperation(value = "Get Beneficiary Details", consumes =
	 * "application/json", produces = "application/json")
	 * 
	 * @RequestMapping(value = { "/get/beneficiaryDetails" }, method = {
	 * RequestMethod.POST }) public String getBeneficiaryDetails(
	 * 
	 * @ApiParam(value = "{\"beneficiaryRegID\": \"Long\"}") @RequestBody String
	 * requestObj) { OutputResponse response = new OutputResponse();
	 * logger.info("getBeneficiaryDetails request :" + requestObj); try {
	 * 
	 * JSONObject obj = new JSONObject(requestObj); if
	 * (obj.has("beneficiaryRegID")) { if (obj.getLong("beneficiaryRegID") > 0)
	 * {
	 * 
	 * String beneficiaryData = registrarServiceImpl
	 * .getBeneficiaryDetails(obj.getLong("beneficiaryRegID")); if
	 * (beneficiaryData != null) { response.setResponse(beneficiaryData); } else
	 * { Map<String, String> noDataMap = new HashMap<>();
	 * response.setResponse(new Gson().toJson(noDataMap)); }
	 * 
	 * } else { response.setError(500, "Please pass beneficiaryRegID"); } } else
	 * { response.setError(500,
	 * "Bad Request... beneficiaryRegID is not there in request"); }
	 * logger.info("getBeneficiaryDetails response :" + response); } catch
	 * (Exception e) { logger.error("Error in getBeneficiaryDetails :" + e);
	 * response.setError(e); } return response.toString(); }
	 * 
	 * @CrossOrigin()
	 * 
	 * @ApiOperation(value = "Get Beneficiary Image", consumes =
	 * "application/json", produces = "application/json")
	 * 
	 * @RequestMapping(value = { "/get/beneficiaryImage" }, method = {
	 * RequestMethod.POST }) public String getBeneficiaryImage(
	 * 
	 * @ApiParam(value = "{\"beneficiaryRegID\": \"Long\"}") @RequestBody String
	 * requestObj) { OutputResponse response = new OutputResponse();
	 * logger.info("getBeneficiaryImage request :" + requestObj); try {
	 * JSONObject obj = new JSONObject(requestObj); if
	 * (obj.has("beneficiaryRegID")) { if (obj.getLong("beneficiaryRegID") > 0)
	 * { String beneficiaryData =
	 * registrarServiceImpl.getBenImage(obj.getLong("beneficiaryRegID"));
	 * 
	 * response.setResponse(beneficiaryData); } else { response.setError(500,
	 * "Please pass beneficiaryRegID"); } } else { response.setError(500,
	 * "Bad Request... beneficiaryRegID is not there in request"); }
	 * logger.info("getBeneficiaryDetails response :" + response); } catch
	 * (Exception e) {
	 * 
	 * } return response.toString(); }
	 * 
	 * // Registrar Beneficiary Registration API .....
	 * 
	 * @CrossOrigin()
	 * 
	 * @ApiOperation(value = "Update Registered Beneficiary Data", consumes =
	 * "application/json", produces = "application/json")
	 * 
	 * @RequestMapping(value = { "/update/BeneficiaryDetails" }, method = {
	 * RequestMethod.POST }) public String updateBeneficiary(
	 * 
	 * @ApiParam(value =
	 * "{\"benD\": {\"beneficiaryRegID\": \"Long\", \"firstName\": \"String\", \"lastName\": \"String\", \"gender\": \"Short\","
	 * +
	 * "\"dob\": \"Timestamp\", \"maritalStatus\": \"Short\", \"fatherName\": \"String\", \"motherName\": \"String\","
	 * +
	 * "\"husbandName\": \"String\", \"image\": \"String\", \"aadharNo\": \"String\", \"income\": \"Short\", "
	 * +
	 * "\"literacyStatus\": \"String\", \"educationQualification\": \"Short\", \"occupation\": \"Short\", \"phoneNo\": \"String\","
	 * +
	 * "\"emailID\": \"Integer\", \"bankName\": \"String\", \"branchName\": \"String\", \"IFSCCode\": \"String\", \"accountNumber\": \"String\","
	 * +
	 * "\"community\": \"Short\", \"religion\": \"Short\", \"blockID\": \"Integer\", \"blockName\": \"String\", \"habitation\": \"String\", "
	 * +
	 * "\"villageID\": \"Integer\", \"villageName\": \"String\", \"districtID\": \"Integer\", \"districtName\": \"String\", \"stateID\": \"Integer\", "
	 * +
	 * "\"stateName\": \"String\", \"govID\": [{\"benGovMapID\": \"Long\", \"type\": \"String\",\"value\": \"String\"},"
	 * +
	 * "{\"type\": \"String\",\"value\": \"String\"}], \"ageAtMarriage\": \"Integer\", \"createdBy\": \"String\", "
	 * +
	 * "\"servicePointID\": \"Integer\", \"govtIdentityNo\": \"Integer\", \"govtIdentityTypeID\": \"Integer\", \"modifiedBy\": \"String\"}}"
	 * ) @RequestBody String comingRequest) {
	 * 
	 * OutputResponse response = new OutputResponse(); try {
	 * 
	 * // JsonObject responseOBJ = new JsonObject();
	 * WrapperBeneficiaryRegistration wrapperBeneficiaryRegistrationOBJ =
	 * InputMapper.gson() .fromJson(comingRequest,
	 * WrapperBeneficiaryRegistration.class);
	 * logger.info("updateBeneficiary request:" + comingRequest); JsonObject
	 * benD = wrapperBeneficiaryRegistrationOBJ.getBenD();
	 * 
	 * if (benD == null || benD.isJsonNull() || !benD.has("beneficiaryRegID")) {
	 * response.setError(0, "Data Not Sufficient..."); } else { int benData =
	 * registrarServiceImpl.updateBeneficiary(benD); if (benData != 0 &&
	 * !benD.get("beneficiaryRegID").isJsonNull()) { Long benRegID =
	 * benD.get("beneficiaryRegID").getAsLong(); int benDemoUpdateRes =
	 * registrarServiceImpl.updateBeneficiaryDemographic(benD, benRegID); int
	 * benPhonMapUpdateRes =
	 * registrarServiceImpl.updateBeneficiaryPhoneMapping(benD, benRegID);
	 * 
	 * int benGovIdMapUpdateRes =
	 * registrarServiceImpl.updateBenGovIdMapping(benD, benRegID);
	 * 
	 * int benbenDemoOtherUpdateRes =
	 * registrarServiceImpl.updateBeneficiaryDemographicAdditional(benD,
	 * benRegID);
	 * 
	 * int benImageUpdateRes = registrarServiceImpl.updateBeneficiaryImage(benD,
	 * benRegID);
	 * 
	 * if (benRegID >= 0 && benDemoUpdateRes >= 0 && benPhonMapUpdateRes >= 0 &&
	 * benbenDemoOtherUpdateRes >= 0 && benImageUpdateRes >= 0) { Integer i =
	 * nurseServiceImpl.updateBeneficiaryStatus('R', benRegID);
	 * response.setResponse("Beneficiary Details updated successfully!!!");
	 * 
	 * } else { // i_beneficiary, I_bendemographics and m_benphonemap //
	 * roll-back response.setError(500, "Something Went-Wrong"); } } else { //
	 * i_beneficiary roll-back response.setError(500, "Something Went-Wrong"); }
	 * } logger.info("updateBeneficiary response:" + response); } catch
	 * (Exception e) { logger.error("Error in updateBeneficiary :" + e);
	 * response.setError(e); } return response.toString(); }
	 */

}
