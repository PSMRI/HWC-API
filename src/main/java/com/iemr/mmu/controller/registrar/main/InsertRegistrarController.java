/* LicenseInfo : Copyright © 2023 Piramal */ 
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
import com.iemr.mmu.data.registrar.BeneficiaryData;
import com.iemr.mmu.data.registrar.WrapperBeneficiaryRegistration;
import com.iemr.mmu.service.common.master.RegistrarServiceMasterDataImpl;
import com.iemr.mmu.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.mmu.service.registrar.RegistrarServiceImpl;
import com.iemr.mmu.utils.mapper.InputMapper;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/***
 * 
 * @author NE298657
 *
 */

@CrossOrigin
@RestController
@RequestMapping(value = "/registrar", headers = "Authorization")
/** Objective: Performs Beneficiary Registration */
public class InsertRegistrarController {
	private Logger logger = LoggerFactory.getLogger(InsertRegistrarController.class);
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
	@ApiOperation(value = "Register a new Beneficiary", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/registrarBeneficaryRegistration" }, method = { RequestMethod.POST })
	public String createBeneficiary(
			@ApiParam(value = "{\"benD\":{\"firstName\": \"String\", \"lastName\": \"String\", \"gender\": \"Short\","
					+ "\"dob\": \"Timestamp\", \"maritalStatus\": \"Short\", \"fatherName\": \"String\", \"motherName\": \"String\","
					+ "\"husbandName\": \"String\", \"image\": \"String\", \"aadharNo\": \"String\", \"income\": \"Short\", "
					+ "\"literacyStatus\": \"String\", \"educationQualification\": \"Short\", \"occupation\": \"Short\", \"phoneNo\": \"String\","
					+ "\"emailID\": \"Integer\", \"bankName\": \"String\", \"branchName\": \"String\", \"IFSCCode\": \"String\", \"accountNumber\": \"String\","
					+ "\"community\": \"Short\", \"religion\": \"Short\", \"blockID\": \"Integer\", \"blockName\": \"String\","
					+ "\"habitation\": \"String\", \"villageID\": \"Integer\", \"villageName\": \"String\", \"districtID\": \"Integer\","
					+ "\"districtName\": \"String\", \"stateID\": \"Integer\", \"stateName\": \"String\", \"countryID\": \"Integer\","
					+ "\"govID\": [{\"type\": \"String\",\"value\": \"String\"}], \"ageAtMarriage\": \"Integer\", \"createdBy\": \"String\", "
					+ "\"servicePointID\": \"Integer\"}}") @RequestBody String comingRequest,
			@RequestHeader(value = "Authorization") String Authorization) {

		OutputResponse response = new OutputResponse();
		try {

			// New code 23-03-2018
			// String s =
			// registrarServiceImpl.registerBeneficiary(comingRequest,
			// Authorization);
			// end of New code 23-03-2018

			// JsonObject responseOBJ = new JsonObject();
			WrapperBeneficiaryRegistration wrapperBeneficiaryRegistrationOBJ = InputMapper.gson()
					.fromJson(comingRequest, WrapperBeneficiaryRegistration.class);
			logger.info("createBeneficiary request:" + comingRequest);
			JsonObject benD = wrapperBeneficiaryRegistrationOBJ.getBenD();

			if (benD == null || benD.isJsonNull()) {
				response.setError(0, "Invalid input data");
			} else {
				BeneficiaryData benData = registrarServiceImpl.createBeneficiary(benD);

				if (benData != null) {
					Long benRegID = benData.getBeneficiaryRegID();
					Long benDemoID = registrarServiceImpl.createBeneficiaryDemographic(benD, benRegID);
					Long benPhonMapID = registrarServiceImpl.createBeneficiaryPhoneMapping(benD, benRegID);

					int benGovIdMapID = registrarServiceImpl.createBenGovIdMapping(benD, benRegID);

					Long benbenDemoOtherID = registrarServiceImpl.createBeneficiaryDemographicAdditional(benD,
							benRegID);

					Long benImageID = registrarServiceImpl.createBeneficiaryImage(benD, benRegID);

					if (benRegID > 0 && benDemoID > 0 && benPhonMapID > 0 && benbenDemoOtherID > 0 && benImageID > 0) {
						Integer i = commonNurseServiceImpl.updateBeneficiaryStatus('R', benRegID);
						if (benData.getBeneficiaryID() != null) {
							response.setResponse(benData.getBeneficiaryID());
						} else {
							// i_beneficiary, I_bendemographics and
							// m_benphonemap
							// roll-back

							// 1.1
							response.setResponse("Registration Done. Beneficiary ID is : " + benRegID);
							// Temp code
							// Long r =
							// registrarServiceImpl.updateBenFlowStatusFlag(benData,
							// benD.get("phoneNo").getAsString());
							//
							// if (r != null && r > 0) {
							// response.setResponse("Registration Done.
							// Beneficiary ID is : " + benRegID);
							// } else {
							// }
							// end of Temp code[replace by 1.1]
						}
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
			logger.info("createBeneficiary response:" + response);
		} catch (Exception e) {
			logger.error("Error in createBeneficiary :" + e);
			response.setError(e);
		}
		return response.toString();
	}

	// beneficiary registration with common and identity new
	@CrossOrigin()
	@ApiOperation(value = "Register a new Beneficiary new API", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/registrarBeneficaryRegistrationNew" }, method = { RequestMethod.POST })
	public String registrarBeneficaryRegistrationNew(@RequestBody String comingReq,
			@RequestHeader(value = "Authorization") String Authorization) {
		String s;
		OutputResponse response = new OutputResponse();
		try {
			s = registrarServiceImpl.registerBeneficiary(comingReq, Authorization);
			return s;
		} catch (Exception e) {
			logger.error("Error in registration" + e);
			response.setError(5000, "Error in registration; please contact administrator");
			return response.toString();
		}

	}

}
