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
package com.iemr.hwc.service.common.transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
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
import com.google.gson.JsonObject;
import com.iemr.hwc.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.hwc.data.neonatal.ImmunizationServices;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.tele_consultation.TCRequestModel;
import com.iemr.hwc.data.tele_consultation.TcSpecialistSlotBookingRequestOBJ;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.neonatal.ImmunizationServicesRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.provider.ProviderServiceMappingRepo;
import com.iemr.hwc.service.adolescent.AdolescentAndChildCareService;
import com.iemr.hwc.service.anc.ANCServiceImpl;
import com.iemr.hwc.service.anc.Utility;
import com.iemr.hwc.service.cancerScreening.CSNurseServiceImpl;
import com.iemr.hwc.service.cancerScreening.CSServiceImpl;
import com.iemr.hwc.service.covid19.Covid19ServiceImpl;
import com.iemr.hwc.service.family_planning.FamilyPlanningService;
import com.iemr.hwc.service.generalOPD.GeneralOPDServiceImpl;
import com.iemr.hwc.service.ncdCare.NCDCareServiceImpl;
import com.iemr.hwc.service.ncdscreening.NCDScreeningServiceImpl;
import com.iemr.hwc.service.neonatal.NeonatalService;
import com.iemr.hwc.service.pnc.PNCServiceImpl;
import com.iemr.hwc.service.quickConsultation.QuickConsultationServiceImpl;
import com.iemr.hwc.service.tele_consultation.TeleConsultationServiceImpl;
import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.mapper.InputMapper;

@Service
@PropertySource("classpath:application.properties")
public class CommonServiceImpl implements CommonService {

	@Value("${openkmDocUrl}")
	private String openkmDocUrl;

	@Autowired
	private Covid19ServiceImpl covid19ServiceImpl;

	private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;
	private ANCServiceImpl ancServiceImpl;
	private PNCServiceImpl pncServiceImpl;
	private GeneralOPDServiceImpl generalOPDServiceImpl;
	private NCDCareServiceImpl ncdCareServiceImpl;
	private QuickConsultationServiceImpl quickConsultationServiceImpl;
	private CommonNurseServiceImpl commonNurseServiceImpl;
	private CSNurseServiceImpl cSNurseServiceImpl;
	private CSServiceImpl csServiceImpl;
	private NCDScreeningServiceImpl ncdScreeningServiceImpl;
	@Autowired
	private CommonDoctorServiceImpl commonDoctorServiceImpl;
	@Autowired
	private TeleConsultationServiceImpl teleConsultationServiceImpl;
	@Autowired
	private ProviderServiceMappingRepo providerServiceMappingRepo;

	@Autowired
	private BenVisitDetailRepo benVisitDetailRepo; 

	@Autowired
	public void setCsServiceImpl(CSServiceImpl csServiceImpl) {
		this.csServiceImpl = csServiceImpl;
	}

	@Autowired
	public void setcSNurseServiceImpl(CSNurseServiceImpl cSNurseServiceImpl) {
		this.cSNurseServiceImpl = cSNurseServiceImpl;
	}

	@Autowired
	public void setCommonNurseServiceImpl(CommonNurseServiceImpl commonNurseServiceImpl) {
		this.commonNurseServiceImpl = commonNurseServiceImpl;
	}

	@Autowired
	public void setQuickConsultationServiceImpl(QuickConsultationServiceImpl quickConsultationServiceImpl) {
		this.quickConsultationServiceImpl = quickConsultationServiceImpl;
	}

	@Autowired
	public void setNcdCareServiceImpl(NCDCareServiceImpl ncdCareServiceImpl) {
		this.ncdCareServiceImpl = ncdCareServiceImpl;
	}

	@Autowired
	public void setGeneralOPDServiceImpl(GeneralOPDServiceImpl generalOPDServiceImpl) {
		this.generalOPDServiceImpl = generalOPDServiceImpl;
	}

	@Autowired
	public void setPncServiceImpl(PNCServiceImpl pncServiceImpl) {
		this.pncServiceImpl = pncServiceImpl;
	}

	@Autowired
	public void setAncServiceImpl(ANCServiceImpl ancServiceImpl) {
		this.ancServiceImpl = ancServiceImpl;
	}

	@Autowired
	public void setBeneficiaryFlowStatusRepo(BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo) {
		this.beneficiaryFlowStatusRepo = beneficiaryFlowStatusRepo;
	}

	@Autowired
	public void setNcdScreeningServiceImpl(NCDScreeningServiceImpl ncdScreeningServiceImpl) {
		this.ncdScreeningServiceImpl = ncdScreeningServiceImpl;
	}

	@Autowired
	private FamilyPlanningService familyPlanningService;

	@Autowired
	private ImmunizationServicesRepo immunizationServicesRepo;

	public String getCaseSheetPrintDataForBeneficiary(BeneficiaryFlowStatus benFlowOBJ, String Authorization)
			throws Exception {
		String visitCategory = benFlowOBJ.getVisitCategory();
		String caseSheetData = null;

		if (null != visitCategory && visitCategory.length() > 0) {
			switch (visitCategory) {
			case "ANC": {
				caseSheetData = getANC_PrintData(benFlowOBJ);
			}
				break;
			case "PNC": {
				caseSheetData = getPNC_PrintData(benFlowOBJ);
			}
				break;
			case "General OPD": {
				caseSheetData = getGenOPD_PrintData(benFlowOBJ);
			}
				break;
			case "NCD care": {
				caseSheetData = getNCDcare_PrintData(benFlowOBJ);
			}
				break;
			case "General OPD (QC)": {
				caseSheetData = getQC_PrintData(benFlowOBJ);
			}
				break;
			case "Cancer Screening": {
				caseSheetData = getCancerScreening_PrintData(benFlowOBJ);
			}
				break;
			case "COVID-19 Screening": {
				caseSheetData = getCovid19_PrintData(benFlowOBJ);
			}
				break;
			case "NCD screening": {
				caseSheetData = getNCDScreening_PrintData(benFlowOBJ);
			}
				break;
			case "FP & Contraceptive Services": {
				caseSheetData = getFP_PrintData(benFlowOBJ);
			}
				break;
			case "Neonatal and Infant Health Care Services": {
				caseSheetData = getNNI_PrintData(benFlowOBJ);
			}
				break;
			case "Childhood & Adolescent Healthcare Services": {
				caseSheetData = getCAC_PrintData(benFlowOBJ);
			}
				break;
			default: {
				caseSheetData = "Invalid VisitCategory";
			}
			}
		}
		return caseSheetData;
	}

	private String getANC_PrintData(BeneficiaryFlowStatus benFlowOBJ) throws IEMRException {
		Map<String, Object> caseSheetData = new HashMap<>();

		caseSheetData.put("nurseData",
				ancServiceImpl.getBenANCNurseData(benFlowOBJ.getBeneficiaryRegID(), benFlowOBJ.getBenVisitCode()));

		caseSheetData.put("doctorData", ancServiceImpl.getBenCaseRecordFromDoctorANC(benFlowOBJ.getBeneficiaryRegID(),
				benFlowOBJ.getBenVisitCode()));

		caseSheetData.put("BeneficiaryData",
				getBenDetails(benFlowOBJ.getBenFlowID(), benFlowOBJ.getBeneficiaryRegID()));

		caseSheetData.put("serviceID", 9);

		return caseSheetData.toString();
	}

	private String getCancerScreening_PrintData(BeneficiaryFlowStatus benFlowOBJ) {
		Map<String, Object> caseSheetData = new HashMap<>();

		caseSheetData.put("nurseData", csServiceImpl.getBenNurseDataForCaseSheet(benFlowOBJ.getBeneficiaryRegID(),
				benFlowOBJ.getBenVisitCode()));
		caseSheetData.put("doctorData", csServiceImpl.getBenCaseRecordFromDoctorCS(benFlowOBJ.getBeneficiaryRegID(),
				benFlowOBJ.getBenVisitCode()));

		caseSheetData.put("BeneficiaryData",
				getBenDetails(benFlowOBJ.getBenFlowID(), benFlowOBJ.getBeneficiaryRegID()));

		caseSheetData.put("ImageAnnotatedData",
				new Gson().toJson(cSNurseServiceImpl.getCancerExaminationImageAnnotationCasesheet(
						benFlowOBJ.getBeneficiaryRegID(), benFlowOBJ.getBenVisitCode())));

		caseSheetData.put("serviceID", 9);

		return caseSheetData.toString();
	}

	private String getGenOPD_PrintData(BeneficiaryFlowStatus benFlowOBJ) throws Exception {
		Map<String, Object> caseSheetData = new HashMap<>();

		caseSheetData.put("nurseData", generalOPDServiceImpl.getBenGeneralOPDNurseData(benFlowOBJ.getBeneficiaryRegID(),
				benFlowOBJ.getBenVisitCode()));

		caseSheetData.put("doctorData", generalOPDServiceImpl
				.getBenCaseRecordFromDoctorGeneralOPD(benFlowOBJ.getBeneficiaryRegID(), benFlowOBJ.getBenVisitCode()));

		caseSheetData.put("BeneficiaryData",
				getBenDetails(benFlowOBJ.getBenFlowID(), benFlowOBJ.getBeneficiaryRegID()));

		caseSheetData.put("serviceID", 9);

		return caseSheetData.toString();
	}

	private String getFP_PrintData(BeneficiaryFlowStatus benFlowOBJ) throws Exception {
		Map<String, Object> caseSheetData = new HashMap<>();

		caseSheetData.put("nurseData",
				familyPlanningService.getNurseDataFP(benFlowOBJ.getBeneficiaryRegID(), benFlowOBJ.getBenVisitCode()));

		caseSheetData.put("doctorData", familyPlanningService
				.getBenCaseRecordFromDoctorFP(benFlowOBJ.getBeneficiaryRegID(), benFlowOBJ.getBenVisitCode()));

		caseSheetData.put("BeneficiaryData",
				getBenDetails(benFlowOBJ.getBenFlowID(), benFlowOBJ.getBeneficiaryRegID()));

		caseSheetData.put("serviceID", 9);

		return caseSheetData.toString();
	}

	@Autowired
	private NeonatalService neonatalService;

	private String getNNI_PrintData(BeneficiaryFlowStatus benFlowOBJ) throws Exception {
		Map<String, Object> caseSheetData = new HashMap<>();

		caseSheetData.put("nurseData",
				neonatalService.getNurseDataNNI(benFlowOBJ.getBeneficiaryRegID(), benFlowOBJ.getBenVisitCode()));

		caseSheetData.put("doctorData", neonatalService.getBenCaseRecordFromDoctorNNI(benFlowOBJ.getBeneficiaryRegID(),
				benFlowOBJ.getBenVisitCode()));

		caseSheetData.put("BeneficiaryData",
				getBenDetails(benFlowOBJ.getBenFlowID(), benFlowOBJ.getBeneficiaryRegID()));

		caseSheetData.put("serviceID", 9);

		return caseSheetData.toString();
	}

	@Autowired
	private AdolescentAndChildCareService adolescentAndChildCareService;

	private String getCAC_PrintData(BeneficiaryFlowStatus benFlowOBJ) throws Exception {
		Map<String, Object> caseSheetData = new HashMap<>();

		caseSheetData.put("nurseData", adolescentAndChildCareService.getNurseDataCAC(benFlowOBJ.getBeneficiaryRegID(),
				benFlowOBJ.getBenVisitCode()));

		caseSheetData.put("doctorData", adolescentAndChildCareService
				.getBenCaseRecordFromDoctorCAC(benFlowOBJ.getBeneficiaryRegID(), benFlowOBJ.getBenVisitCode()));

		caseSheetData.put("BeneficiaryData",
				getBenDetails(benFlowOBJ.getBenFlowID(), benFlowOBJ.getBeneficiaryRegID()));

		caseSheetData.put("serviceID", 9);

		return caseSheetData.toString();
	}

	private String getNCDcare_PrintData(BeneficiaryFlowStatus benFlowOBJ) throws IEMRException {
		Map<String, Object> caseSheetData = new HashMap<>();

		caseSheetData.put("nurseData", ncdCareServiceImpl.getBenNCDCareNurseData(benFlowOBJ.getBeneficiaryRegID(),
				benFlowOBJ.getBenVisitCode()));

		caseSheetData.put("doctorData", ncdCareServiceImpl
				.getBenCaseRecordFromDoctorNCDCare(benFlowOBJ.getBeneficiaryRegID(), benFlowOBJ.getBenVisitCode()));

		caseSheetData.put("BeneficiaryData",
				getBenDetails(benFlowOBJ.getBenFlowID(), benFlowOBJ.getBeneficiaryRegID()));

		caseSheetData.put("serviceID", 9);

		return caseSheetData.toString();
	}

	private String getPNC_PrintData(BeneficiaryFlowStatus benFlowOBJ) throws IEMRException {
		Map<String, Object> caseSheetData = new HashMap<>();

		caseSheetData.put("nurseData",
				pncServiceImpl.getBenPNCNurseData(benFlowOBJ.getBeneficiaryRegID(), benFlowOBJ.getBenVisitCode()));

		caseSheetData.put("doctorData", pncServiceImpl.getBenCaseRecordFromDoctorPNC(benFlowOBJ.getBeneficiaryRegID(),
				benFlowOBJ.getBenVisitCode()));

		caseSheetData.put("BeneficiaryData",
				getBenDetails(benFlowOBJ.getBenFlowID(), benFlowOBJ.getBeneficiaryRegID()));

		caseSheetData.put("serviceID", 9);

		return caseSheetData.toString();
	}

	private String getQC_PrintData(BeneficiaryFlowStatus benFlowOBJ) {
		Map<String, Object> caseSheetData = new HashMap<>();

		caseSheetData.put("nurseData", quickConsultationServiceImpl
				.getBenQuickConsultNurseData(benFlowOBJ.getBeneficiaryRegID(), benFlowOBJ.getBenVisitCode()));

		caseSheetData.put("doctorData", quickConsultationServiceImpl.getBenCaseRecordFromDoctorQuickConsult(
				benFlowOBJ.getBeneficiaryRegID(), benFlowOBJ.getBenVisitCode()));

		caseSheetData.put("BeneficiaryData",
				getBenDetails(benFlowOBJ.getBenFlowID(), benFlowOBJ.getBeneficiaryRegID()));

		caseSheetData.put("serviceID", 9);

		return caseSheetData.toString();
	}

	private String getCovid19_PrintData(BeneficiaryFlowStatus benFlowOBJ) throws IEMRException {
		Map<String, Object> caseSheetData = new HashMap<>();

		caseSheetData.put("nurseData", covid19ServiceImpl.getBenCovidNurseData(benFlowOBJ.getBeneficiaryRegID(),
				benFlowOBJ.getBenVisitCode()));

		caseSheetData.put("doctorData", covid19ServiceImpl
				.getBenCaseRecordFromDoctorCovid19(benFlowOBJ.getBeneficiaryRegID(), benFlowOBJ.getBenVisitCode()));

		caseSheetData.put("BeneficiaryData",
				getBenDetails(benFlowOBJ.getBenFlowID(), benFlowOBJ.getBeneficiaryRegID()));

		caseSheetData.put("serviceID", 9);

		return caseSheetData.toString();
	}

	private String getNCDScreening_PrintData(BeneficiaryFlowStatus benFlowOBJ) throws IEMRException {
		Map<String, Object> caseSheetData = new HashMap<>();

		caseSheetData.put("nurseData", ncdScreeningServiceImpl
				.getBenNCDScreeningNurseData(benFlowOBJ.getBeneficiaryRegID(), benFlowOBJ.getBenVisitCode()));

		caseSheetData.put("doctorData", ncdScreeningServiceImpl.getBenCaseRecordFromDoctorNCDScreening(
				benFlowOBJ.getBeneficiaryRegID(), benFlowOBJ.getBenVisitCode()));

		caseSheetData.put("BeneficiaryData",
				getBenDetails(benFlowOBJ.getBenFlowID(), benFlowOBJ.getBeneficiaryRegID()));

		caseSheetData.put("serviceID", 9);

		return caseSheetData.toString();
	}

	private String getBenDetails(Long benFlowID, Long benRegID) {
		BeneficiaryFlowStatus obj = beneficiaryFlowStatusRepo.getBenDetailsForLeftSidePanel(benFlowID);
		if(obj != null && obj.getVisitCode() != null) {
		BeneficiaryVisitDetail visitDetail = benVisitDetailRepo.getSubVisitCategory(benRegID, obj.getVisitCode());
		
		if (visitDetail != null && visitDetail.getSubVisitCategory() != null)
			obj.setSubVisitCategory(visitDetail.getSubVisitCategory());
		}

		// BeneficiaryFlowStatus obj =
		// BeneficiaryFlowStatus.getBeneficiaryFlowStatusForLeftPanel(tmpOBJ);
		// Integer tcspID = beneficiaryFlowStatusRepo.getTCspecialistID(benRegID,
		// benFlowID);
//		if (tcspID != null && tcspID > 0) {
//			obj.settCSpecialistUserID(tcspID);
//		}
					
		return new Gson().toJson(obj);
	}

	// ------- Fetch beneficiary all past history data ------------------
	public String getBenPastHistoryData(Long beneficiaryRegID) {
		return commonNurseServiceImpl.fetchBenPastMedicalHistory(beneficiaryRegID);
	}
	/// ------- End of Fetch beneficiary all past history data ----------

	// ------- Fetch beneficiary all Comorbid conditions history data
	// ------------------
	public String getComorbidHistoryData(Long beneficiaryRegID) {
		return commonNurseServiceImpl.fetchBenComorbidityHistory(beneficiaryRegID);
	}
	/// ------- End of Fetch beneficiary all Comorbid conditions history data
	/// --------

	// ------- Fetch beneficiary all Medication history data -----------
	public String getMedicationHistoryData(Long beneficiaryRegID) {
		return commonNurseServiceImpl.fetchBenPersonalMedicationHistory(beneficiaryRegID);
	}
	/// ------- End of Fetch beneficiary all Medication history data --

	// ------- Fetch beneficiary all Personal Tobacco history data
	// ---------------
	public String getPersonalTobaccoHistoryData(Long beneficiaryRegID) {
		return commonNurseServiceImpl.fetchBenPersonalTobaccoHistory(beneficiaryRegID);
	}
	/// ------- End of Fetch beneficiary all Personal Tobacco history data------

	// ------- Fetch beneficiary all Personal Alcohol history data
	// ---------------
	public String getPersonalAlcoholHistoryData(Long beneficiaryRegID) {
		return commonNurseServiceImpl.fetchBenPersonalAlcoholHistory(beneficiaryRegID);
	}
	/// ------- End of Fetch beneficiary all Personal Alcohol history data
	/// ------

	// ------- Fetch beneficiary all Personal Allergy history data
	// ---------------
	public String getPersonalAllergyHistoryData(Long beneficiaryRegID) {
		return commonNurseServiceImpl.fetchBenPersonalAllergyHistory(beneficiaryRegID);
	}
	/// ------- End of Fetch beneficiary all Personal Allergy history data------

	// ------- Fetch beneficiary all Family history data ---------------
	public String getFamilyHistoryData(Long beneficiaryRegID) {
		return commonNurseServiceImpl.fetchBenPersonalFamilyHistory(beneficiaryRegID);
	}
	/// ------- End of Fetch beneficiary all Family history data ------

	public String getProviderSpecificData(String request) throws IEMRException {
		return commonNurseServiceImpl.fetchProviderSpecificdata(request);
	}

	// ------- Fetch beneficiary all Physical history data ---------------
	public String getBenPhysicalHistory(Long beneficiaryRegID) {
		return commonNurseServiceImpl.fetchBenPhysicalHistory(beneficiaryRegID);
	}
	/// ------- End of Fetch beneficiary all Physical history data ------

	// ------- Fetch beneficiary all Menstrual history data -----------
	public String getMenstrualHistoryData(Long beneficiaryRegID) {
		return commonNurseServiceImpl.fetchBenMenstrualHistory(beneficiaryRegID);
	}
	/// ------- End of Fetch beneficiary all Menstrual history data --

	// ------- Fetch beneficiary all past obstetric history data ---------------
	public String getObstetricHistoryData(Long beneficiaryRegID) {
		return commonNurseServiceImpl.fetchBenPastObstetricHistory(beneficiaryRegID);
	}
	/// ------- End of Fetch beneficiary all past obstetric history data ------

	// ------- Fetch beneficiary all Immunization history data ---------------
	public String getImmunizationHistoryData(Long beneficiaryRegID) {
		return commonNurseServiceImpl.fetchBenImmunizationHistory(beneficiaryRegID);
	}
	/// ------- End of Fetch beneficiary all Immunization history data ------

	// ------- Fetch beneficiary all Child Vaccine history data ---------------
	public String getChildVaccineHistoryData(Long beneficiaryRegID) {
		return commonNurseServiceImpl.fetchBenOptionalVaccineHistory(beneficiaryRegID);
	}
	/// ------- End of Fetch beneficiary all Child Vaccine history data ------

	// ------- Fetch beneficiary all Perinatal history data ---------------
	public String getBenPerinatalHistoryData(Long beneficiaryRegID) {
		return commonNurseServiceImpl.fetchBenPerinatalHistory(beneficiaryRegID);
	}
	/// ------- End of Fetch beneficiary all Perinatal history data ------

	// ------- Fetch beneficiary all Feeding history data ---------------
	public String getBenFeedingHistoryData(Long beneficiaryRegID) {
		return commonNurseServiceImpl.fetchBenFeedingHistory(beneficiaryRegID);
	}
	/// ------- End of Fetch beneficiary all Feeding history data ------

	// ------- Fetch beneficiary all Development history data ---------------
	public String getBenDevelopmentHistoryData(Long beneficiaryRegID) {
		return commonNurseServiceImpl.fetchBenDevelopmentHistory(beneficiaryRegID);
	}

	// -- Fetch beneficiary previous visit details for case-record ---
	/**
	 * date : 08-09-2018
	 * 
	 * @throws IEMRException
	 */
	public String getBenPreviousVisitDataForCaseRecord(String comingRequest) throws IEMRException {
		CommonUtilityClass obj = InputMapper.gson().fromJson(comingRequest, CommonUtilityClass.class);
		// List<Short> flagList = new ArrayList<>();
		// flagList.add((short) 9);
		// flagList.add((short) 3);
		List<Integer> psmIDList = providerServiceMappingRepo.getProviderServiceMapIdForServiceID((short) 9);
		psmIDList.add(0);
		ArrayList<Object[]> resultList = beneficiaryFlowStatusRepo.getBenPreviousHistory(obj.getBeneficiaryRegID(),
				psmIDList);

		return new Gson().toJson(BeneficiaryFlowStatus.getBeneficiaryPastVisitHistory(resultList));
	}

	// end of Fetch beneficiary previous visit details for case-record

	/***
	 * 
	 * @param requestOBJ
	 * @param commonUtilityClass
	 * @param Authorization
	 * @return
	 * @throws Exception
	 * @date 05-02-2019
	 */
	// create Teleconsultation request. Common for all module
	public TeleconsultationRequestOBJ createTcRequest(JsonObject requestOBJ, CommonUtilityClass commonUtilityClass,
			String Authorization) throws Exception {

		TeleconsultationRequestOBJ tcRequestOBJ = null;
		Long tcRequestStatusFlag = null;
		TcSpecialistSlotBookingRequestOBJ tcSpecialistSlotBookingRequestOBJ = null;
		//
		// if (commonUtilityClass != null && commonUtilityClass.getServiceID() != null
		// && commonUtilityClass.getServiceID() == 4 && requestOBJ != null &&
		// requestOBJ.has("tcRequest")
		// && requestOBJ.get("tcRequest") != null) {
		if (commonUtilityClass != null && requestOBJ != null && requestOBJ.has("tcRequest")
				&& requestOBJ.get("tcRequest") != null) {
			tcRequestOBJ = InputMapper.gson().fromJson(requestOBJ.get("tcRequest"), TeleconsultationRequestOBJ.class);

			// create TC request
			if (tcRequestOBJ != null && tcRequestOBJ.getUserID() != null && tcRequestOBJ.getUserID() > 0
					&& tcRequestOBJ.getAllocationDate() != null) {

				tcRequestOBJ.setAllocationDate(Utility.combineDateAndTimeToDateTime(
						tcRequestOBJ.getAllocationDate().toString(), tcRequestOBJ.getFromTime()));

				// tc request model
				TCRequestModel tRequestModel = InputMapper.gson().fromJson(requestOBJ, TCRequestModel.class);

				tRequestModel.setBeneficiaryRegID(commonUtilityClass.getBeneficiaryRegID());
				tRequestModel.setProviderServiceMapID(commonUtilityClass.getProviderServiceMapID());
				tRequestModel.setVisitCode(commonUtilityClass.getVisitCode());
				if (commonUtilityClass.getBenVisitID() != null)
					tRequestModel.setBenVisitID(commonUtilityClass.getBenVisitID());

				tRequestModel.setUserID(tcRequestOBJ.getUserID());
				tRequestModel.setSpecializationID(tcRequestOBJ.getSpecializationID());
				tRequestModel.setRequestDate(tcRequestOBJ.getAllocationDate());
				tRequestModel
						.setDuration_minute(Utility.timeDiff(tcRequestOBJ.getFromTime(), tcRequestOBJ.getToTime()));
				tRequestModel.setVanID(commonUtilityClass.getVanID());

				// tc speciaist slot booking model
				tcSpecialistSlotBookingRequestOBJ = new TcSpecialistSlotBookingRequestOBJ();
				tcSpecialistSlotBookingRequestOBJ.setUserID(tRequestModel.getUserID());
				tcSpecialistSlotBookingRequestOBJ.setDate(tRequestModel.getRequestDate());
				tcSpecialistSlotBookingRequestOBJ.setFromTime(tcRequestOBJ.getFromTime());
				tcSpecialistSlotBookingRequestOBJ.setToTime(tcRequestOBJ.getToTime());
				tcSpecialistSlotBookingRequestOBJ.setCreatedBy(commonUtilityClass.getCreatedBy());
				tcSpecialistSlotBookingRequestOBJ.setModifiedBy(commonUtilityClass.getCreatedBy());

				int j = commonDoctorServiceImpl.callTmForSpecialistSlotBook(tcSpecialistSlotBookingRequestOBJ,
						Authorization);
				if (j > 0) {
					tcRequestStatusFlag = teleConsultationServiceImpl.createTCRequest(tRequestModel);
					if (tcRequestStatusFlag != null && tcRequestStatusFlag > 0) {
						tcRequestOBJ.setTmRequestID(tcRequestStatusFlag);
					} else {
						throw new RuntimeException("Error while creating TC request.");
					}
				} else
					throw new RuntimeException("Already Booked Slot, Please choose another slot");

			}
		}

		return tcRequestOBJ;
	}

	// end

	public String getOpenKMDocURL(String requestOBJ, String Authorization) {
		RestTemplate restTemplate = new RestTemplate();
		String fileUUID = null;
		JSONObject obj = new JSONObject(requestOBJ);
		if (obj.has("fileID")) {
			fileUUID = benVisitDetailRepo.getFileUUID(obj.getInt("fileID"));

			if (fileUUID != null) {
				Map<String, Object> requestBody = new HashMap<>();
				requestBody.put("fileUID", fileUUID);

				MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
				headers.add("Content-Type", "application/json");
				headers.add("AUTHORIZATION", Authorization);
				HttpEntity<Object> request = new HttpEntity<Object>(requestBody, headers);
				ResponseEntity<String> response = restTemplate.exchange(openkmDocUrl, HttpMethod.POST, request,
						String.class);
				return response.getBody();
			} else
				return null;
		} else
			return null;

	}

	@Override
	public String getBenSymptomaticQuestionnaireDetailsData(Long beneficiaryRegID) throws Exception {
		return commonNurseServiceImpl.getBenSymptomaticData(beneficiaryRegID);

	}

	@Override
	public String getBenPreviousDiabetesData(Long beneficiaryRegID) throws Exception {
		return commonNurseServiceImpl.getBenPreviousDiabetesData(beneficiaryRegID);

	}

	@Override
	public String getBenPreviousReferralData(Long beneficiaryRegID) throws Exception {
		return commonNurseServiceImpl.getBenPreviousReferralData(beneficiaryRegID);

	}

	@Override
	public String getBenImmunizationServiceHistory(Long beneficiaryRegID) throws Exception {

		List<ImmunizationServices> rsList = immunizationServicesRepo.findByBeneficiaryRegIDAndDeleted(beneficiaryRegID,
				false);

		return new Gson().toJson(rsList);
	}

}
