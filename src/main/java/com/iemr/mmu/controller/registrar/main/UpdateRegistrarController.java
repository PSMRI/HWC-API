package com.iemr.mmu.controller.registrar.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.iemr.mmu.data.registrar.WrapperBeneficiaryRegistration;
import com.iemr.mmu.service.common.master.RegistrarServiceMasterDataImpl;
import com.iemr.mmu.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.mmu.service.registrar.RegistrarServiceImpl;
import com.iemr.mmu.utils.mapper.InputMapper;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping(value = "/registrar", headers = "Authorization")
/** Objective: Performs Update Beneficiary Details */
public class UpdateRegistrarController {
	private Logger logger = LoggerFactory.getLogger(UpdateRegistrarController.class);
	private InputMapper inputMapper = new InputMapper();
	private RegistrarServiceImpl registrarServiceImpl;
	private RegistrarServiceMasterDataImpl registrarServiceMasterDataImpl;
	private CommonNurseServiceImpl commonNurseServiceImpl;

	@Autowired
	public void setRegistrarServiceImpl(RegistrarServiceImpl registrarServiceImpl) {
		this.registrarServiceImpl = registrarServiceImpl;
	}

	@Autowired
	public void setRegistrarServiceMasterDataImpl(RegistrarServiceMasterDataImpl registrarServiceMasterDataImpl) {
		this.registrarServiceMasterDataImpl = registrarServiceMasterDataImpl;
	}

	@Autowired
	public void setCommonNurseServiceImpl(CommonNurseServiceImpl commonNurseServiceImpl) {
		this.commonNurseServiceImpl = commonNurseServiceImpl;
	}

	// Registrar Beneficiary Registration API .....
	@CrossOrigin()
	@ApiOperation(value = "Update Registered Beneficiary Data", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/BeneficiaryDetails" }, method = { RequestMethod.POST })
	public String updateBeneficiary(
			@ApiParam(value = "{\"benD\": {\"beneficiaryRegID\": \"Long\", \"firstName\": \"String\", \"lastName\": \"String\", \"gender\": \"Short\","
					+ "\"dob\": \"Timestamp\", \"maritalStatus\": \"Short\", \"fatherName\": \"String\", \"motherName\": \"String\","
					+ "\"husbandName\": \"String\", \"image\": \"String\", \"aadharNo\": \"String\", \"income\": \"Short\", "
					+ "\"literacyStatus\": \"String\", \"educationQualification\": \"Short\", \"occupation\": \"Short\", \"phoneNo\": \"String\","
					+ "\"emailID\": \"Integer\", \"bankName\": \"String\", \"branchName\": \"String\", \"IFSCCode\": \"String\", \"accountNumber\": \"String\","
					+ "\"community\": \"Short\", \"religion\": \"Short\", \"blockID\": \"Integer\", \"blockName\": \"String\", \"habitation\": \"String\", "
					+ "\"villageID\": \"Integer\", \"villageName\": \"String\", \"districtID\": \"Integer\", \"districtName\": \"String\", \"stateID\": \"Integer\", "
					+ "\"stateName\": \"String\", \"govID\": [{\"benGovMapID\": \"Long\", \"type\": \"String\",\"value\": \"String\"},"
					+ "{\"type\": \"String\",\"value\": \"String\"}], \"ageAtMarriage\": \"Integer\", \"createdBy\": \"String\", "
					+ "\"servicePointID\": \"Integer\", \"govtIdentityNo\": \"Integer\", \"govtIdentityTypeID\": \"Integer\", \"modifiedBy\": \"String\"}}") @RequestBody String comingRequest) {

		OutputResponse response = new OutputResponse();
		try {

			// JsonObject responseOBJ = new JsonObject();
			WrapperBeneficiaryRegistration wrapperBeneficiaryRegistrationOBJ = InputMapper.gson()
					.fromJson(comingRequest, WrapperBeneficiaryRegistration.class);
			logger.info("updateBeneficiary request:" + comingRequest);
			JsonObject benD = wrapperBeneficiaryRegistrationOBJ.getBenD();

			if (benD == null || benD.isJsonNull() || !benD.has("beneficiaryRegID")) {
				response.setError(0, "Data Not Sufficient...");
			} else {
				int benData = registrarServiceImpl.updateBeneficiary(benD);
				if (benData != 0 && !benD.get("beneficiaryRegID").isJsonNull()) {
					Long benRegID = benD.get("beneficiaryRegID").getAsLong();
					int benDemoUpdateRes = registrarServiceImpl.updateBeneficiaryDemographic(benD, benRegID);
					int benPhonMapUpdateRes = registrarServiceImpl.updateBeneficiaryPhoneMapping(benD, benRegID);

					int benGovIdMapUpdateRes = registrarServiceImpl.updateBenGovIdMapping(benD, benRegID);

					int benbenDemoOtherUpdateRes = registrarServiceImpl.updateBeneficiaryDemographicAdditional(benD,
							benRegID);

					int benImageUpdateRes = registrarServiceImpl.updateBeneficiaryImage(benD, benRegID);

					if (benRegID >= 0 && benDemoUpdateRes >= 0 && benPhonMapUpdateRes >= 0
							&& benbenDemoOtherUpdateRes >= 0 && benImageUpdateRes >= 0) {
						Integer i = commonNurseServiceImpl.updateBeneficiaryStatus('R', benRegID);
						response.setResponse("Beneficiary Details updated successfully!!!");

					} else {
						// i_beneficiary, I_bendemographics and m_benphonemap
						// roll-back
						response.setError(500, "Something Went-Wrong");
					}
				} else {
					// i_beneficiary roll-back
					response.setError(500, "Something Went-Wrong");
				}
			}
			logger.info("updateBeneficiary response:" + response);
		} catch (Exception e) {
			logger.error("Error in updateBeneficiary :" + e);
			response.setError(e);
		}
		return response.toString();
	}

	// revisit to nurse by searching and submitting new
	@ApiOperation(value = "registrar will submit a beneficiary to nurse for revisit", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/create/BenReVisitToNurse" }, method = { RequestMethod.POST })
	public String createReVisitForBenToNurse(@RequestBody String requestOBJ) {
		OutputResponse response = new OutputResponse();
		try {
			int i = registrarServiceImpl.searchAndSubmitBeneficiaryToNurse(requestOBJ);
			if (i > 0) {
				if (i == 1)
					response.setResponse("Beneficiary moved to nurse worklist");
				else
					response.setError(5000, "Beneficiary already present in nurse worklist");
			} else {
				response.setError(5000, "Error while moving beneficiary to nurse worklist");
			}
		} catch (Exception e) {
			logger.error("Error while creating re-visit " + e);
			response.setError(5000, "Error while moving beneficiary to nurse worklist");
		}
		return response.toString();
	}

	@ApiOperation(value = "ben edit, save or submit", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/BeneficiaryUpdate" }, method = { RequestMethod.POST })
	public String beneficiaryUpdate(@RequestBody String requestOBJ,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		Integer s = null;
		try {
			s = registrarServiceImpl.updateBeneficiary(requestOBJ, Authorization);
			if (s != null) {
				if (s == 1)
					response.setResponse("Beneficiary details updated successfully");
				else
					response.setResponse(
							"Beneficiary details updated successfully but already present in nurse work list");
			} else {
				response.setError(5000, "Error while updating beneficiary details");
			}
		} catch (Exception e) {
			response.setError(5000, "Error in beneficiary details update");
		}
		return response.toString();
	}

}
