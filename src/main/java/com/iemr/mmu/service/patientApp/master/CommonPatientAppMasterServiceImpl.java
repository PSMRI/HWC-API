/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.service.patientApp.master;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.mmu.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.mmu.data.covid19.Covid19BenFeedback;
import com.iemr.mmu.data.doctor.ChiefComplaintMaster;
import com.iemr.mmu.data.nurse.BeneficiaryVisitDetail;
import com.iemr.mmu.data.nurse.CommonUtilityClass;
import com.iemr.mmu.data.patientApp.ChiefComplaintsPatientAPP;
import com.iemr.mmu.data.quickBlox.Quickblox;
import com.iemr.mmu.data.quickConsultation.BenChiefComplaint;
import com.iemr.mmu.data.quickConsultation.PrescriptionDetail;
import com.iemr.mmu.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.mmu.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.mmu.repo.doctor.ChiefComplaintMasterRepo;
import com.iemr.mmu.repo.masterrepo.covid19.CovidContactHistoryMasterRepo;
import com.iemr.mmu.repo.masterrepo.covid19.CovidRecommnedationMasterRepo;
import com.iemr.mmu.repo.masterrepo.covid19.CovidSymptomsMasterRepo;
import com.iemr.mmu.repo.nurse.BenVisitDetailRepo;
import com.iemr.mmu.repo.quickBlox.QuickBloxRepo;
import com.iemr.mmu.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.mmu.service.common.transaction.CommonServiceImpl;
import com.iemr.mmu.service.covid19.Covid19ServiceImpl;
import com.iemr.mmu.service.generalOPD.GeneralOPDDoctorServiceImpl;
import com.iemr.mmu.utils.mapper.InputMapper;

@Service
@PropertySource("classpath:application.properties")
public class CommonPatientAppMasterServiceImpl implements CommonPatientAppMasterService {

	@Value("${servicePointID}")
	private Integer servicePointID;
	@Value("${parkingPlaceID}")
	private Integer parkingPlaceID;
	@Value("${providerServiceMapID}")
	private Integer providerServiceMapID;
	@Value("${vanID}")
	private Integer vanID;
	@Value("${serviceID}")
	private Integer serviceID;
	@Value("${providerID}")
	private Integer providerID;
	@Value("${appId}")
    private Integer appId;
    @Value("${authKey}")
    private String authKey;
    @Value("${authSecret}")
    private String authSecret;
    @Value("${scheduling-slotsize}")
    private Integer schedulingSlotSize;
	@Autowired
	private CovidSymptomsMasterRepo covidSymptomsMasterRepo;
	@Autowired
	private CovidContactHistoryMasterRepo covidContactHistoryMasterRepo;
	@Autowired
	private CovidRecommnedationMasterRepo covidRecommnedationMasterRepo;
	@Autowired
	private ChiefComplaintMasterRepo chiefComplaintMasterRepo;
	@Autowired
	private CommonNurseServiceImpl commonNurseServiceImpl;
	@Autowired
	private Covid19ServiceImpl covid19ServiceImpl;
	@Autowired
	private BenVisitDetailRepo benVisitDetailRepo;
	@Autowired
	private CommonServiceImpl commonServiceImpl;
	@Autowired
	private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;
	@Autowired
	private GeneralOPDDoctorServiceImpl generalOPDDoctorServiceImpl;
	@Autowired
	private QuickBloxRepo quickBloxRepo;

	@Override
	public String getChiefComplaintsMaster(Integer visitCategoryID, Integer providerServiceMapID, String gender) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		ArrayList<Object[]> ccList = chiefComplaintMasterRepo.getChiefComplaintMaster();
		resMap.put("chiefComplaintMaster", ChiefComplaintMaster.getChiefComplaintMasters(ccList));
		return new Gson().toJson(resMap);
	}

	@Override
	public String getCovidMaster(Integer visitCategoryID, Integer providerServiceMapID, String gender) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("covidSymptomsMaster", covidSymptomsMasterRepo.findByDeleted(false));
		resMap.put("covidContactHistoryMaster", covidContactHistoryMasterRepo.findByDeleted(false));
		resMap.put("covidRecommendationMaster", covidRecommnedationMasterRepo.findByDeleted(false));
		return new Gson().toJson(resMap);
	}

	@Override
    public String getMaster(Integer stateID) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("servicePointID", servicePointID);
        resMap.put("parkingPlaceID", parkingPlaceID);
        resMap.put("vanID", vanID);
        resMap.put("providerServiceMapID", providerServiceMapID);
        resMap.put("serviceID", serviceID);
        resMap.put("providerID", providerID);
        resMap.put("appId",appId);
        resMap.put("authKey",authKey);
        resMap.put("authSecret",authSecret);
        resMap.put("schedulingSlotSize", schedulingSlotSize);
        return new Gson().toJson(resMap);
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String saveCovidScreeningData(String requestObj) throws Exception {
		Map<String, Long> responseOBJ = new HashMap<>();

		JSONObject jsonOBJ = new JSONObject(requestObj);

		CommonUtilityClass nurseUtilityClass = InputMapper.gson().fromJson(requestObj, CommonUtilityClass.class);

		BeneficiaryVisitDetail benDetailsOBJ = InputMapper.gson().fromJson(requestObj, BeneficiaryVisitDetail.class);
		benDetailsOBJ.setVisitReason("New Chief Complaint");
		benDetailsOBJ.setVisitCategory("COVID-19 Screening");

		if (benDetailsOBJ != null && benDetailsOBJ.getVanID() != null && benDetailsOBJ.getBeneficiaryRegID() != null
				&& benDetailsOBJ.getProviderServiceMapID() != null) {
			Long visitID = commonNurseServiceImpl.saveBeneficiaryVisitDetails(benDetailsOBJ);
			Long visitCode = null;
			if (visitID != null) {
				visitCode = commonNurseServiceImpl.generateVisitCode(visitID, nurseUtilityClass.getVanID(), 3);
				if (visitCode != null) {
					Covid19BenFeedback covid19BenFeedbackOBJ = InputMapper.gson()
							.fromJson(jsonOBJ.getJSONObject("covidDetails").toString(), Covid19BenFeedback.class);

					if (covid19BenFeedbackOBJ != null) {
						covid19BenFeedbackOBJ.setVisitCode(visitCode);
						Integer i = covid19ServiceImpl.saveCovidDetails(covid19BenFeedbackOBJ);
					} else
						throw new RuntimeException("error in saving covid screening details");

					responseOBJ.put("visitCode", visitCode);
				} else
					throw new RuntimeException(" Error in episode creation - visit code");
			} else
				throw new RuntimeException(" Error in episode creation");

		} else
			throw new RuntimeException("Error in episode creation. Missing Beneficiary registration details");

		return new Gson().toJson(responseOBJ);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String savechiefComplaintsData(String requestObj) throws Exception {
		Map<String, Long> responseOBJ = new HashMap<>();

		JSONObject jsonOBJ = new JSONObject(requestObj);

		CommonUtilityClass nurseUtilityClass = InputMapper.gson().fromJson(requestObj, CommonUtilityClass.class);
		Long visitCode = null;
		if (nurseUtilityClass != null && nurseUtilityClass.getBeneficiaryRegID() != null
				&& nurseUtilityClass.getVanID() != null && nurseUtilityClass.getProviderServiceMapID() != null
				&& nurseUtilityClass.getCreatedBy() != null) {
			ChiefComplaintsPatientAPP[] benChiefComplaintArray = InputMapper.gson().fromJson(
					jsonOBJ.getJSONObject("chiefComplaints").getJSONArray("pastIllness").toString(),
					ChiefComplaintsPatientAPP[].class);

			List<ChiefComplaintsPatientAPP> benChiefComplaintList = Arrays.asList(benChiefComplaintArray);

			BenChiefComplaint chiefComplaintOBJ;
			List<BenChiefComplaint> chiefComplaintsList = new ArrayList<>();

			if (nurseUtilityClass.getIsCovidFlowDone() != null && nurseUtilityClass.getIsCovidFlowDone()
					&& nurseUtilityClass.getVisitCode() != null) {
				visitCode = nurseUtilityClass.getVisitCode();
			} else {
				BeneficiaryVisitDetail benDetailsOBJ = InputMapper.gson().fromJson(requestObj,
						BeneficiaryVisitDetail.class);
				benDetailsOBJ.setVisitReason("New Chief Complaint");
				benDetailsOBJ.setVisitCategory("General OPD");

				Long visitID = commonNurseServiceImpl.saveBeneficiaryVisitDetails(benDetailsOBJ);

				if (visitID != null) {
					visitCode = commonNurseServiceImpl.generateVisitCode(visitID, nurseUtilityClass.getVanID(), 3);
					if (visitCode != null) {
					} else
						throw new RuntimeException(" Error in episode creation - visit code");
				} else
					throw new RuntimeException(" Error in episode creation");
			}

			for (ChiefComplaintsPatientAPP temp : benChiefComplaintList) {
				chiefComplaintOBJ = new BenChiefComplaint();
				if (temp.getIllnessTypeID() != null)
					chiefComplaintOBJ.setChiefComplaintID(temp.getIllnessTypeID());
				if (temp.getIllnessType() != null)
					chiefComplaintOBJ.setChiefComplaint(temp.getIllnessType());
				if (temp.getTimePeriodAgo() != null)
					chiefComplaintOBJ.setDuration(temp.getTimePeriodAgo());
				if (temp.getTimePeriodUnit() != null)
					chiefComplaintOBJ.setUnitOfDuration(temp.getTimePeriodUnit());

				chiefComplaintOBJ.setBeneficiaryRegID(nurseUtilityClass.getBeneficiaryRegID());

				chiefComplaintOBJ.setVanID(nurseUtilityClass.getVanID());
				chiefComplaintOBJ.setProviderServiceMapID(nurseUtilityClass.getProviderServiceMapID());
				chiefComplaintOBJ.setCreatedBy(nurseUtilityClass.getCreatedBy());
				chiefComplaintOBJ.setParkingPlaceID(nurseUtilityClass.getParkingPlaceID());

				chiefComplaintOBJ.setVisitCode(visitCode);

				chiefComplaintsList.add(chiefComplaintOBJ);
			}

			commonNurseServiceImpl.saveBenChiefComplaints(chiefComplaintsList);
		} else
			throw new RuntimeException(" Error in saving chief complaints");

		if (visitCode != null)
			responseOBJ.put("visitCode", visitCode);
		return new Gson().toJson(responseOBJ);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer bookTCSlotData(String requestObj, String Authorization) throws Exception {
		Integer response = 0;

		JsonObject jsnOBJ = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElmnt = jsnParser.parse(requestObj);
		jsnOBJ = jsnElmnt.getAsJsonObject();

		CommonUtilityClass nurseUtilityClass = InputMapper.gson().fromJson(requestObj, CommonUtilityClass.class);

		if (nurseUtilityClass != null && nurseUtilityClass.getVisitCode() != null
				&& nurseUtilityClass.getBeneficiaryRegID() != null && nurseUtilityClass.getCreatedBy() != null) {

			BeneficiaryVisitDetail obj = benVisitDetailRepo.getVisitDetails(nurseUtilityClass.getBeneficiaryRegID(),
					nurseUtilityClass.getVisitCode());

			if (obj != null && obj.getBenVisitID() != null) {
				nurseUtilityClass.setBenVisitID(obj.getBenVisitID());
				TeleconsultationRequestOBJ tcRequestOBJ = commonServiceImpl.createTcRequest(jsnOBJ, nurseUtilityClass,
						Authorization);

				if (tcRequestOBJ != null && tcRequestOBJ.getTmRequestID() != null) {
					int i = creataAndUpdateBeneficairyFlowStatus(tcRequestOBJ, obj, nurseUtilityClass);
					if (i > 0) {
						response = 1;
					} else
						throw new RuntimeException("Error in slot booking and flow");
				} else
					throw new RuntimeException("Error in slot booking.");
			} else
				throw new RuntimeException("Error in slot booking. Missing Beneficiary visit details");
		} else
			throw new RuntimeException("Error in slot booking. Missing Beneficiary registration details");

		return response;
	}

	private Integer creataAndUpdateBeneficairyFlowStatus(TeleconsultationRequestOBJ tcRequestOBJ,
			BeneficiaryVisitDetail obj, CommonUtilityClass nurseUtilityClass) throws Exception {
		BeneficiaryFlowStatus benFlowOBJ = new BeneficiaryFlowStatus();
//
//		Quickblox qb;
//		if (tcRequestOBJ.getUserID() != null)
//		qb = quickBloxRepo.getQuickbloxIds(tcRequestOBJ.getUserID());
//		else
//			throw new RuntimeException("Quickblox user mapping not avaialble");

//		//for quickblox start

//		Map<String, Long> quickBlox = new HashMap<String, Long>();
//		quickBlox.put("specialistUserID", Long.valueOf(tcRequestOBJ.getUserID()));
//		RestTemplate restTemplate = new RestTemplate();
//		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
//		headers.add("Content-Type", "application/json");
//		headers.add("AUTHORIZATION", Authorization);
//		HttpEntity<Object> request = new HttpEntity<Object>(quickBlox.toString(), headers);
//		ResponseEntity<String> response = restTemplate.exchange(getQuickbloxIds, HttpMethod.POST, request,
//				String.class);
//
//		String specialistBenQuickbloxID = "";
//		if (response.getStatusCodeValue() == 200 && response.hasBody()) {
//			JsonObject jsnOBJ = new JsonObject();
//			JsonObject check = new JsonObject();
//			JsonParser jsnParser = new JsonParser();
//			JsonElement jsnElmnt = jsnParser.parse(response.getBody());
//			jsnOBJ = jsnElmnt.getAsJsonObject();
//			if (jsnOBJ.has("statusCode") && jsnOBJ.get("statusCode").getAsInt() == 200)
//				check = jsnOBJ.getAsJsonObject("data");
//			specialistBenQuickbloxID = check.getAsJsonObject("quickbloxIds").get("specialistBenQuickbloxID")
//					.getAsString();
//		}
		// benFlowOBJ.setBenQuickbloxID(Long.parseLong(specialistBenQuickbloxID));

		// for quickblox end

//		if (qb != null && qb.getSpecialistBenQuickbloxID() != null)
//			benFlowOBJ.setBenQuickbloxID(qb.getSpecialistBenQuickbloxID());

		benFlowOBJ.setBeneficiaryRegID(obj.getBeneficiaryRegID());
		benFlowOBJ.setBenVisitID(obj.getBenVisitID());
		benFlowOBJ.setVisitCode(obj.getVisitCode());
		benFlowOBJ.setVisitReason(obj.getVisitReason());
		benFlowOBJ.setVisitCategory(obj.getVisitCategory());
		benFlowOBJ.setBenVisitNo(obj.getVisitNo());
		benFlowOBJ.setBenName(nurseUtilityClass.getFirstName()
				.concat(nurseUtilityClass.getLastName() != null ? (" " + nurseUtilityClass.getLastName()) : ""));
		benFlowOBJ.setAge(nurseUtilityClass.getAge() + " " + nurseUtilityClass.getAgeUnits());
		benFlowOBJ.setBen_age_val(nurseUtilityClass.getAge());
		benFlowOBJ.setGenderID(nurseUtilityClass.getGenderID());
		benFlowOBJ.setGenderName(nurseUtilityClass.getGenderName());
		benFlowOBJ.setPreferredPhoneNum(nurseUtilityClass.getCreatedBy());
		benFlowOBJ.setAgeVal(nurseUtilityClass.getAge());
		benFlowOBJ.setCreatedBy(nurseUtilityClass.getCreatedBy());
		benFlowOBJ.setBenVisitDate(new Timestamp(System.currentTimeMillis()));
		benFlowOBJ.setBeneficiaryID(nurseUtilityClass.getBeneficiaryID());
		benFlowOBJ.setDistrictID(nurseUtilityClass.getDistrictID());
		benFlowOBJ.setDistrictName(nurseUtilityClass.getDistrictName());
		benFlowOBJ.setVillageID(nurseUtilityClass.getVillageId());
		benFlowOBJ.setVillageName(nurseUtilityClass.getVillageName());
		benFlowOBJ.setProviderServiceMapId(nurseUtilityClass.getProviderServiceMapID());
		benFlowOBJ.setVanID(nurseUtilityClass.getVanID());
		benFlowOBJ.setParkingPlaceID(nurseUtilityClass.getParkingPlaceID());

		benFlowOBJ.settCSpecialistUserID(tcRequestOBJ.getUserID());
		benFlowOBJ.settCRequestDate(tcRequestOBJ.getAllocationDate());

		benFlowOBJ.setNurseFlag((short) 0);
		benFlowOBJ.setDoctorFlag((short) 0);
		benFlowOBJ.setPharmacist_flag((short) 0);
		benFlowOBJ.setLab_technician_flag((short) 0);
		benFlowOBJ.setRadiologist_flag((short) 0);
		benFlowOBJ.setOncologist_flag((short) 0);
		benFlowOBJ.setSpecialist_flag((short) 1);

		BeneficiaryFlowStatus resultSet = beneficiaryFlowStatusRepo.save(benFlowOBJ);
		if (resultSet != null && resultSet.getBenFlowID() > 0)
			return 1;
		else
			return 0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String getPatientEpisodeData(String requestObj) throws Exception {
		Map<String, Object> responseMap = new HashMap<>();
		CommonUtilityClass nurseUtilityClass = InputMapper.gson().fromJson(requestObj, CommonUtilityClass.class);

		if (nurseUtilityClass != null && nurseUtilityClass.getVisitCode() != null
				&& nurseUtilityClass.getBeneficiaryRegID() != null) {
			String benEpisodeDataMap = covid19ServiceImpl.getBenVisitDetailsFrmNurseCovid19(
					nurseUtilityClass.getBeneficiaryRegID(), nurseUtilityClass.getVisitCode());

			String chiefComplaints = commonNurseServiceImpl
					.getBenChiefComplaints(nurseUtilityClass.getBeneficiaryRegID(), nurseUtilityClass.getVisitCode());

			JsonObject covidDetailsMap = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(benEpisodeDataMap);
			covidDetailsMap = jsnElmnt.getAsJsonObject();

			JsonArray chiefComplaintsList = new JsonArray();
			JsonParser jsnParser1 = new JsonParser();
			JsonElement jsnElmnt1 = jsnParser1.parse(chiefComplaints);
			chiefComplaintsList = jsnElmnt1.getAsJsonArray();

			if (covidDetailsMap != null)
				responseMap.put("covidDetails", covidDetailsMap);
			if (chiefComplaintsList != null)
				responseMap.put("chiefComplaints", chiefComplaintsList);

		} else
			throw new RuntimeException("Error in getting Beneficiary Details");

		return new Gson().toJson(responseMap);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String getPatientBookedSlots(String requestObj) throws Exception {
		Map<String, Object> responseMap = new HashMap<>();
		CommonUtilityClass nurseUtilityClass = InputMapper.gson().fromJson(requestObj, CommonUtilityClass.class);

		if (nurseUtilityClass != null && nurseUtilityClass.getBeneficiaryRegID() != null
				&& nurseUtilityClass.getCreatedBy() != null) {

			ArrayList<BeneficiaryFlowStatus> objLIst = beneficiaryFlowStatusRepo
					.getBenSlotDetails(nurseUtilityClass.getCreatedBy());

			if (objLIst.size() > 0) {
				Boolean isAnySlotForthisPatient = false;
				responseMap.put("isAnyActiveSlot", true);
				Map<String, Object> objMap = new HashMap<>();

				int pointer = 1;
				for (BeneficiaryFlowStatus b : objLIst) {
					// call quickblox repo for password
					Quickblox qb = quickBloxRepo.getQuickbloxIds(b.gettCSpecialistUserID());

					if (b.getBeneficiaryRegID().equals(nurseUtilityClass.getBeneficiaryRegID())) {
						isAnySlotForthisPatient = true;
						objMap.put("benID", b.getBeneficiaryID());
						objMap.put("benName", b.getBenName());
						objMap.put("age", b.getAge());
						objMap.put("gender", b.getGenderName());
						objMap.put("tcDate", b.gettCRequestDate());
						objMap.put("benQuickbloxID", qb.getSpecialistBenQuickbloxID());
						objMap.put("benQuickbloxPass", qb.getSpecialistBenQuickBloxPass());
						responseMap.put("patientDetails", objMap);
						break;
					} else {
						if (pointer == objLIst.size()) {
							objMap.put("benID", b.getBeneficiaryID());
							objMap.put("benName", b.getBenName());
							objMap.put("age", b.getAge());
							objMap.put("gender", b.getGenderName());
							objMap.put("tcDate", b.gettCRequestDate());
							objMap.put("benQuickbloxID", qb.getSpecialistBenQuickbloxID());
							objMap.put("benQuickbloxPass", qb.getSpecialistBenQuickBloxPass());

							responseMap.put("patientDetails", objMap);
						}
					}
					pointer++;
				}
				responseMap.put("isAnyActiveSlotForSameBen", isAnySlotForthisPatient);
			} else
				responseMap.put("isAnyActiveSlot", false);

		} else
			throw new RuntimeException("invalid request. beneficiary details are missing");

		return new Gson().toJson(responseMap);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long saveSpecialistDiagnosisData(String requestObj) throws Exception {
		Long response = null;
		CommonUtilityClass nurseUtilityClass = InputMapper.gson().fromJson(requestObj, CommonUtilityClass.class);

		PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
		if (nurseUtilityClass != null && nurseUtilityClass.getBeneficiaryRegID() != null
				&& nurseUtilityClass.getVisitCode() != null && nurseUtilityClass.getVanID() != null
				&& nurseUtilityClass.getCreatedBy() != null) {
			prescriptionDetail.setBeneficiaryRegID(nurseUtilityClass.getBeneficiaryRegID());
			prescriptionDetail.setVisitCode(nurseUtilityClass.getVisitCode());
			prescriptionDetail.setVanID(nurseUtilityClass.getVanID());
			prescriptionDetail.setProviderServiceMapID(nurseUtilityClass.getProviderServiceMapID());
			if (nurseUtilityClass.getParkingPlaceID() != null)
				prescriptionDetail.setParkingPlaceID(nurseUtilityClass.getParkingPlaceID());
			if (nurseUtilityClass.getSpecialistDiagnosis() != null)
				prescriptionDetail.setInstruction(nurseUtilityClass.getSpecialistDiagnosis());

			prescriptionDetail.setCreatedBy(nurseUtilityClass.getCreatedBy());

			Long pID = commonNurseServiceImpl.saveBenPrescription(prescriptionDetail);
			if (pID != null && pID > 0) {
				int i = beneficiaryFlowStatusRepo.updateBenFlowStatusAfterSpecialistMobileAPP(
						nurseUtilityClass.getVisitCode(), nurseUtilityClass.getBeneficiaryRegID(), (short) 9);

				if (i > 0)
					response = pID;
				else
					throw new RuntimeException("error in saving diagnosis data - flow status change");
			} else
				throw new RuntimeException("error in saving diagnosis data");
		} else
			throw new RuntimeException("invalid request. beneficiary details are missing");

		return response;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String getSpecialistDiagnosisData(String requestObj) throws Exception {
		CommonUtilityClass nurseUtilityClass = InputMapper.gson().fromJson(requestObj, CommonUtilityClass.class);

		if (nurseUtilityClass != null && nurseUtilityClass.getBeneficiaryRegID() != null
				&& nurseUtilityClass.getVisitCode() != null) {
			String diagnosis = generalOPDDoctorServiceImpl.getGeneralOPDDiagnosisDetails(
					nurseUtilityClass.getBeneficiaryRegID(), nurseUtilityClass.getVisitCode());
			if (diagnosis != null)
				return diagnosis;
			else
				throw new RuntimeException("error in getting diagnosis data");
		} else
			throw new RuntimeException("invalid request. beneficiary details are missing");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String getPatientsLast_3_Episode(String requestObj) throws Exception {
		CommonUtilityClass nurseUtilityClass = InputMapper.gson().fromJson(requestObj, CommonUtilityClass.class);

		if (nurseUtilityClass != null && nurseUtilityClass.getBeneficiaryRegID() != null) {
			ArrayList<BeneficiaryFlowStatus> resultSet = beneficiaryFlowStatusRepo
					.getPatientLat_3_Episode(nurseUtilityClass.getBeneficiaryRegID());

			if (resultSet != null)
				return new Gson().toJson(resultSet);
			else
				throw new RuntimeException("error in getting patient last 3 episode data data");
		} else
			throw new RuntimeException("invalid request. beneficiary details is/are missing");
	}
}
