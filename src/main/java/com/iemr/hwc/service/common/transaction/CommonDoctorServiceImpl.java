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

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.data.anc.WrapperAncFindings;
import com.iemr.hwc.data.anc.WrapperBenInvestigationANC;
import com.iemr.hwc.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.hwc.data.doctor.BenReferDetails;
import com.iemr.hwc.data.foetalmonitor.FoetalMonitor;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.quickConsultation.BenChiefComplaint;
import com.iemr.hwc.data.quickConsultation.BenClinicalObservations;
import com.iemr.hwc.data.quickConsultation.LabTestOrderDetail;
import com.iemr.hwc.data.quickConsultation.PrescribedDrugDetail;
import com.iemr.hwc.data.registrar.WrapperRegWorklist;
import com.iemr.hwc.data.snomedct.SCTDescription;
import com.iemr.hwc.data.tele_consultation.TcSpecialistSlotBookingRequestOBJ;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.data.tele_consultation.TeleconsultationStats;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.doctor.BenReferDetailsRepo;
import com.iemr.hwc.repo.doctor.DocWorkListRepo;
import com.iemr.hwc.repo.foetalmonitor.FoetalMonitorRepo;
import com.iemr.hwc.repo.nurse.ncdcare.NCDCareDiagnosisRepo;
import com.iemr.hwc.repo.nurse.pnc.PNCDiagnosisRepo;
import com.iemr.hwc.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.hwc.repo.quickConsultation.BenClinicalObservationsRepo;
import com.iemr.hwc.repo.quickConsultation.LabTestOrderDetailRepo;
import com.iemr.hwc.repo.quickConsultation.PrescribedDrugDetailRepo;
import com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.hwc.repo.tc_consultation.TCRequestModelRepo;
import com.iemr.hwc.repo.tc_consultation.TeleconsultationStatsRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.snomedct.SnomedServiceImpl;
import com.iemr.hwc.service.tele_consultation.SMSGatewayServiceImpl;
import com.iemr.hwc.utils.CookieUtil;
import com.iemr.hwc.utils.RestTemplateUtil;
import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.mapper.InputMapper;
import com.iemr.hwc.utils.mapper.OutputMapper;

import jakarta.servlet.http.HttpServletRequest;

@Service
@PropertySource("classpath:application.properties")
public class CommonDoctorServiceImpl {

	@Value("${tcSpecialistSlotBook}")
	private String tcSpecialistSlotBook;

	@Value("${docWL}")
	private Integer docWL;
	@Value("${tcSpeclistWL}")
	private Integer tcSpeclistWL;

	private BenClinicalObservationsRepo benClinicalObservationsRepo;
	private BenChiefComplaintRepo benChiefComplaintRepo;
	private DocWorkListRepo docWorkListRepo;
	private BenReferDetailsRepo benReferDetailsRepo;
	private LabTestOrderDetailRepo labTestOrderDetailRepo;
	private PrescribedDrugDetailRepo prescribedDrugDetailRepo;

	private SnomedServiceImpl snomedServiceImpl;

	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;

	private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;
	@Autowired
	private TCRequestModelRepo tCRequestModelRepo;
	@Autowired
	private PNCDiagnosisRepo pNCDiagnosisRepo;
	@Autowired
	private PrescriptionDetailRepo prescriptionDetailRepo;
	@Autowired
	private NCDCareDiagnosisRepo NCDCareDiagnosisRepo;
	@Autowired
	private SMSGatewayServiceImpl sMSGatewayServiceImpl;
	@Autowired
	private FoetalMonitorRepo foetalMonitorRepo;
	@Autowired
	private CookieUtil cookieUtil;

	@Autowired
	public void setSnomedServiceImpl(SnomedServiceImpl snomedServiceImpl) {
		this.snomedServiceImpl = snomedServiceImpl;
	}

	@Autowired
	public void setCommonBenStatusFlowServiceImpl(CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl) {
		this.commonBenStatusFlowServiceImpl = commonBenStatusFlowServiceImpl;
	}

	@Autowired
	public void setBeneficiaryFlowStatusRepo(BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo) {
		this.beneficiaryFlowStatusRepo = beneficiaryFlowStatusRepo;
	}

	@Autowired
	public void setPrescribedDrugDetailRepo(PrescribedDrugDetailRepo prescribedDrugDetailRepo) {
		this.prescribedDrugDetailRepo = prescribedDrugDetailRepo;
	}

	@Autowired
	public void setLabTestOrderDetailRepo(LabTestOrderDetailRepo labTestOrderDetailRepo) {
		this.labTestOrderDetailRepo = labTestOrderDetailRepo;
	}

	@Autowired
	public void setBenReferDetailsRepo(BenReferDetailsRepo benReferDetailsRepo) {
		this.benReferDetailsRepo = benReferDetailsRepo;
	}

	@Autowired
	public void setDocWorkListRepo(DocWorkListRepo docWorkListRepo) {
		this.docWorkListRepo = docWorkListRepo;
	}

	@Autowired
	public void setBenChiefComplaintRepo(BenChiefComplaintRepo benChiefComplaintRepo) {
		this.benChiefComplaintRepo = benChiefComplaintRepo;
	}

	@Autowired
	public void setBenClinicalObservationsRepo(BenClinicalObservationsRepo benClinicalObservationsRepo) {
		this.benClinicalObservationsRepo = benClinicalObservationsRepo;
	}

	@Autowired
	private TeleconsultationStatsRepo teleconsultationStatsRepo;
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	public Integer saveFindings(JsonObject obj) throws Exception {
		int i = 0;
		BenClinicalObservations clinicalObservations = InputMapper.gson().fromJson(obj, BenClinicalObservations.class);
		BenClinicalObservations benClinicalObservationsRS = benClinicalObservationsRepo.save(clinicalObservations);
		if (benClinicalObservationsRS != null) {
			i = 1;
		}

		return i;

	}

	public Integer saveDocFindings(WrapperAncFindings wrapperAncFindings) {
		int i = 0;
		int clinicalObservationFlag = 0;
		int chiefComFlag = 0;

		// save clinical observation
		BenClinicalObservations benClinicalObservationsRS = benClinicalObservationsRepo
				.save(getBenClinicalObservations(wrapperAncFindings));
		if (benClinicalObservationsRS != null) {
			clinicalObservationFlag = 1;
		}

		// get chief complaints Object to save
		ArrayList<BenChiefComplaint> tmpBenCHiefComplaints = wrapperAncFindings.getComplaints();
		ArrayList<BenChiefComplaint> tmpBenCHiefComplaintsTMP = new ArrayList<>();
		// filter out valid chief complaints
		if (tmpBenCHiefComplaints.size() > 0) {
			for (BenChiefComplaint benChiefComplaint : tmpBenCHiefComplaints) {
				if (benChiefComplaint.getChiefComplaint() != null) {
					benChiefComplaint.setBeneficiaryRegID(wrapperAncFindings.getBeneficiaryRegID());
					benChiefComplaint.setBenVisitID(wrapperAncFindings.getBenVisitID());
					benChiefComplaint.setVisitCode(wrapperAncFindings.getVisitCode());
					benChiefComplaint.setProviderServiceMapID(wrapperAncFindings.getProviderServiceMapID());
					benChiefComplaint.setCreatedBy(wrapperAncFindings.getCreatedBy());
					benChiefComplaint.setVanID(wrapperAncFindings.getVanID());
					benChiefComplaint.setParkingPlaceID(wrapperAncFindings.getParkingPlaceID());

					tmpBenCHiefComplaintsTMP.add(benChiefComplaint);
				}
			}

		}
		// if valid chief complaints is present than save to DB
		if (tmpBenCHiefComplaintsTMP.size() > 0) {
			ArrayList<BenChiefComplaint> benChiefComplaintListRS = (ArrayList<BenChiefComplaint>) benChiefComplaintRepo
					.saveAll(tmpBenCHiefComplaintsTMP);
			if (tmpBenCHiefComplaintsTMP.size() == benChiefComplaintListRS.size()) {
				chiefComFlag = 1;
			}
		} else {
			chiefComFlag = 1;
		}

		// check if both clinical observation & chief complaints both saved successfully
		if (clinicalObservationFlag > 0 && chiefComFlag > 0)
			i = 1;

		return i;
	}

	// get comma separated snomedCT code for give string comma seperated
	public String[] getSnomedCTcode(String requestString) {
		String[] returnARR = new String[2];
		String snomedCTidVal = "";
		String snomedCTtermVal = "";

		if (requestString != null && requestString.length() > 0) {
			String[] symptomArr = requestString.split(",");

			int pointer = 0;
			for (String s : symptomArr) {
				SCTDescription obj = snomedServiceImpl.findSnomedCTRecordFromTerm(s.trim());
				if (obj != null) {
					if (pointer == symptomArr.length - 1) {
						snomedCTidVal += obj.getConceptID();
						snomedCTtermVal += obj.getTerm();
					} else {
						snomedCTidVal += obj.getConceptID() + ",";
						snomedCTtermVal += obj.getTerm() + ",";
					}
				} else {
					if (pointer == symptomArr.length - 1) {
						snomedCTidVal += "N/A";
						snomedCTtermVal += "N/A";
					} else {
						snomedCTidVal += "N/A" + ",";
						snomedCTtermVal += "N/A" + ",";
					}

				}
				pointer++;
			}

		}
		returnARR[0] = snomedCTidVal;
		returnARR[1] = snomedCTtermVal;

		return returnARR;
	}

	private BenClinicalObservations getBenClinicalObservations(WrapperAncFindings wrapperAncFindings) {
		// snomedCT integration started on : 06-08-2018
		String symptoms = wrapperAncFindings.getOtherSymptoms();
		String[] responseString = getSnomedCTcode(symptoms);
		// end of snomedCT integration

		BenClinicalObservations benClinicalObservations = new BenClinicalObservations();
		benClinicalObservations.setBeneficiaryRegID(wrapperAncFindings.getBeneficiaryRegID());
		benClinicalObservations.setBenVisitID(wrapperAncFindings.getBenVisitID());
		benClinicalObservations.setVisitCode(wrapperAncFindings.getVisitCode());
		benClinicalObservations.setProviderServiceMapID(wrapperAncFindings.getProviderServiceMapID());
		benClinicalObservations.setVanID(wrapperAncFindings.getVanID());
		benClinicalObservations.setParkingPlaceID(wrapperAncFindings.getParkingPlaceID());
		benClinicalObservations.setCreatedBy(wrapperAncFindings.getCreatedBy());
		// benClinicalObservations.setClinicalObservation(wrapperAncFindings.getClinicalObservation());
		benClinicalObservations.setOtherSymptoms(wrapperAncFindings.getOtherSymptoms());
		// benClinicalObservations.setSignificantFindings(wrapperAncFindings.getSignificantFindings());
		benClinicalObservations.setIsForHistory(wrapperAncFindings.getIsForHistory());
		benClinicalObservations.setModifiedBy(wrapperAncFindings.getModifiedBy());
		if (responseString != null && responseString.length > 1) {
			benClinicalObservations.setOtherSymptomsSCTCode(responseString[0]);
			benClinicalObservations.setOtherSymptomsSCTTerm(responseString[1]);
		}

		if (wrapperAncFindings != null && wrapperAncFindings.getClinicalObservationsList() != null
				&& wrapperAncFindings.getClinicalObservationsList().size() > 0) {

			StringBuilder clinicalTerm = new StringBuilder();
			StringBuilder clinicalConceptID = new StringBuilder();

			for (SCTDescription obj : wrapperAncFindings.getClinicalObservationsList()) {
				if (obj.getTerm() != null)
					clinicalTerm.append(obj.getTerm()).append("||");
				if (obj.getConceptID() != null)
					clinicalConceptID.append(obj.getConceptID()).append("||");

			}
			if (clinicalTerm.length() > 1)
				benClinicalObservations.setClinicalObservation(clinicalTerm.substring(0, clinicalTerm.length() - 2));
			if (clinicalConceptID.length() > 1)
				benClinicalObservations
						.setClinicalObservationSctcode(clinicalConceptID.substring(0, clinicalConceptID.length() - 2));

		}

		if (wrapperAncFindings != null && wrapperAncFindings.getSignificantFindingsList() != null
				&& wrapperAncFindings.getSignificantFindingsList().size() > 0) {
			StringBuilder significantTerm = new StringBuilder();
			StringBuilder significantConceptID = new StringBuilder();

			for (SCTDescription obj : wrapperAncFindings.getSignificantFindingsList()) {
				if (obj.getTerm() != null)
					significantTerm.append(obj.getTerm()).append("||");
				if (obj.getConceptID() != null)
					significantConceptID.append(obj.getConceptID()).append("||");

			}

			if (significantTerm.length() > 1)
				benClinicalObservations
						.setSignificantFindings(significantTerm.substring(0, significantTerm.length() - 2));
			if (significantConceptID.length() > 1)
				benClinicalObservations.setSignificantFindingsSctcode(
						significantConceptID.substring(0, significantConceptID.length() - 2));

		}

		return benClinicalObservations;
	}

	private ArrayList<BenChiefComplaint> getBenChiefComplaint(WrapperAncFindings wrapperAncFindings) {
		ArrayList<BenChiefComplaint> benChiefComplaintList = new ArrayList<>();
		BenChiefComplaint benChiefComplaint;
		if (wrapperAncFindings != null && wrapperAncFindings.getComplaints() != null
				&& wrapperAncFindings.getComplaints().size() > 0) {
			for (BenChiefComplaint complaintsDetails : wrapperAncFindings.getComplaints()) {
				benChiefComplaint = new BenChiefComplaint();
				benChiefComplaint.setBeneficiaryRegID(wrapperAncFindings.getBeneficiaryRegID());
				benChiefComplaint.setBenVisitID(wrapperAncFindings.getBenVisitID());
				benChiefComplaint.setProviderServiceMapID(wrapperAncFindings.getProviderServiceMapID());
				benChiefComplaint.setCreatedBy(wrapperAncFindings.getCreatedBy());

				if (null != complaintsDetails.getChiefComplaintID()) {

					benChiefComplaint.setChiefComplaintID(complaintsDetails.getChiefComplaintID());
				}
				if (null != complaintsDetails.getChiefComplaint())
					benChiefComplaint.setChiefComplaint(complaintsDetails.getChiefComplaint());
				if (null != complaintsDetails.getDuration())
					benChiefComplaint.setDuration(complaintsDetails.getDuration());
				if (null != complaintsDetails.getUnitOfDuration())
					benChiefComplaint.setUnitOfDuration(complaintsDetails.getUnitOfDuration());
				if (null != complaintsDetails.getDescription())
					benChiefComplaint.setDescription(complaintsDetails.getDescription());

				benChiefComplaintList.add(benChiefComplaint);
			}
		}
		return benChiefComplaintList;
	}

	public String getDocWorkList() {
		List<Object[]> docWorkListData = docWorkListRepo.getDocWorkList();
		return WrapperRegWorklist.getDocWorkListData(docWorkListData);
	}

	// New doc work-list service
	public String getDocWorkListNew(Integer providerServiceMapId, Integer serviceID, Integer vanID) {

		Calendar cal = Calendar.getInstance();
		if (docWL != null && docWL > 0 && docWL <= 30)
			cal.add(Calendar.DAY_OF_YEAR, -docWL);
		else
			cal.add(Calendar.DAY_OF_YEAR, -10);
		long cutoffTime = cal.getTimeInMillis();

		ArrayList<BeneficiaryFlowStatus> docWorkList = new ArrayList<>();
		// MMU doc work-list
		if (serviceID != null && serviceID == 2) {
			docWorkList = beneficiaryFlowStatusRepo.getDocWorkListNew(providerServiceMapId);
		}
		// TC doc work-list
		else if (serviceID != null && serviceID == 9) {
			docWorkList = beneficiaryFlowStatusRepo.getDocWorkListNewTC(providerServiceMapId,
					new Timestamp(cutoffTime), vanID);
			for (BeneficiaryFlowStatus beneficiaryFlowStatus : docWorkList) {
				Boolean isHighrisk = beneficiaryFlowStatusRepo.getIsHighrisk(beneficiaryFlowStatus.getBeneficiaryID());
				if(null != isHighrisk)
					beneficiaryFlowStatus.setIs_high_risk(isHighrisk);
			}
		}

		return new Gson().toJson(docWorkList);
	}

	// New doc work-list service (Future scheduled beneficiary for TM)
	public String getDocWorkListNewFutureScheduledForTM(Integer providerServiceMapId, Integer serviceID,
			Integer vanID) {

		ArrayList<BeneficiaryFlowStatus> docWorkListFutureScheduled = new ArrayList<>();
		if (serviceID != null && serviceID == 9) {
			docWorkListFutureScheduled = beneficiaryFlowStatusRepo
					.getDocWorkListNewFutureScheduledTC(providerServiceMapId, vanID);
		}
		return new Gson().toJson(docWorkListFutureScheduled);
	}

	// New TC specialist work-list service
	public String getTCSpecialistWorkListNewForTMPatientApp(Integer providerServiceMapId, Integer userID,
			Integer serviceID, Integer vanID) {
		Calendar cal = Calendar.getInstance();
		if (tcSpeclistWL != null && tcSpeclistWL > 0 && tcSpeclistWL <= 30)
			cal.add(Calendar.DAY_OF_YEAR, -tcSpeclistWL);
		else
			cal.add(Calendar.DAY_OF_YEAR, -7);
		long sevenDaysAgo = cal.getTimeInMillis();

		ArrayList<BeneficiaryFlowStatus> tcSpecialistWorkList = new ArrayList<>();
		if (serviceID != null && serviceID == 9) {
			tcSpecialistWorkList = beneficiaryFlowStatusRepo.getTCSpecialistWorkListNewPatientApp(providerServiceMapId,
					userID, new Timestamp(sevenDaysAgo), vanID);
		}
		return new Gson().toJson(tcSpecialistWorkList);
	}

	// New TC specialist work-list service, patient App, 14-08-2020
	public String getTCSpecialistWorkListNewForTM(Integer providerServiceMapId, Integer userID, Integer serviceID) {
		Calendar cal = Calendar.getInstance();
		if (tcSpeclistWL != null && tcSpeclistWL > 0 && tcSpeclistWL <= 30)
			cal.add(Calendar.DAY_OF_YEAR, -tcSpeclistWL);
		else
			cal.add(Calendar.DAY_OF_YEAR, -7);
		long sevenDaysAgo = cal.getTimeInMillis();

		ArrayList<BeneficiaryFlowStatus> tcSpecialistWorkList = new ArrayList<>();
		if (serviceID != null && serviceID == 9) {
			tcSpecialistWorkList = beneficiaryFlowStatusRepo.getTCSpecialistWorkListNew(providerServiceMapId, userID,
					new Timestamp(sevenDaysAgo));
		}
		return new Gson().toJson(tcSpecialistWorkList);
	}

	// New TC specialist work-list service (Future scheduled beneficiary for TM)
	public String getTCSpecialistWorkListNewFutureScheduledForTM(Integer providerServiceMapId, Integer userID,
			Integer serviceID) {

		ArrayList<BeneficiaryFlowStatus> tcSpecialistWorkListFutureScheduled = new ArrayList<>();
		if (serviceID != null && serviceID == 9) {
			tcSpecialistWorkListFutureScheduled = beneficiaryFlowStatusRepo
					.getTCSpecialistWorkListNewFutureScheduled(providerServiceMapId, userID);
		}
		return new Gson().toJson(tcSpecialistWorkListFutureScheduled);
	}

	public String fetchBenPreviousSignificantFindings(Long beneficiaryRegID) {
		ArrayList<Object[]> previousSignificantFindings = (ArrayList<Object[]>) benClinicalObservationsRepo
				.getPreviousSignificantFindings(beneficiaryRegID);

		Map<String, Object> response = new HashMap<String, Object>();

		ArrayList<BenClinicalObservations> clinicalObservations = new ArrayList<BenClinicalObservations>();
		if (null != clinicalObservations) {
			for (Object[] obj : previousSignificantFindings) {
				BenClinicalObservations clinicalObservation = new BenClinicalObservations((String) obj[1],
						(Date) obj[0]);
				clinicalObservations.add(clinicalObservation);
			}
		}

		response.put("findings", clinicalObservations);
		return new Gson().toJson(response);

	}

	public Long saveBenReferDetails(JsonObject obj, Boolean rrList) throws IEMRException {
		Long ID = (long) 1;
		BenReferDetails referDetails = InputMapper.gson().fromJson(obj, BenReferDetails.class);
		CommonUtilityClass cuc = InputMapper.gson().fromJson(obj, CommonUtilityClass.class);

		// referral reason list
		if (referDetails != null) {
			if (rrList != null && rrList) {
				if (referDetails.getReferralReasonList() != null && referDetails.getReferralReasonList().length > 0) {
					StringBuffer sb = new StringBuffer();
					for (String rr : referDetails.getReferralReasonList()) {
						sb.append(rr).append("||");
					}
					if (sb.length() >= 2)
						referDetails.setReferralReason(sb.substring(0, (sb.length() - 2)));
				} else
					referDetails.setReferralReason("");
			}
			// additional service referral list
			if (referDetails.getRefrredToAdditionalServiceList() != null
					&& referDetails.getRefrredToAdditionalServiceList().length > 0) {
				StringBuffer sb = new StringBuffer();
				for (String ras : referDetails.getRefrredToAdditionalServiceList()) {
					sb.append(ras).append("||");
				}
				if (sb.length() >= 2)
					referDetails.setServiceName(sb.substring(0, (sb.length() - 2)));

			}

			benReferDetailsRepo.save(referDetails);
		}

		return ID;
	}

	public String getBenReferDetailsByCreatedBy(String createdBy) {
		ArrayList<BenReferDetails> resList = benReferDetailsRepo.getBenReferDetailsByCreatedBy(createdBy);

		// Process referral reason and service lists for each record
		if (resList != null && resList.size() > 0) {
			for (BenReferDetails referDetails : resList) {
				if (referDetails != null && referDetails.getReferralReason() != null) {
					referDetails.setReferralReasonList(referDetails.getReferralReason().split("\\|\\|"));
				}
				if (referDetails != null && referDetails.getServiceName() != null) {
					referDetails.setRefrredToAdditionalServiceList(referDetails.getServiceName().split("\\|\\|"));
				}
				referDetails.setBenId(referDetails.getBeneficiaryRegID());

			}
		}

		// Configure date format for JSON output
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("MMM d, yyyy h:mm:ss a");
		return gsonBuilder.create().toJson(resList);
	}

	public String getFindingsDetails(Long beneficiaryRegID, Long visitCode) {
		ArrayList<Object[]> clinicalObservationsList = benClinicalObservationsRepo.getFindingsData(beneficiaryRegID,
				visitCode);
		ArrayList<Object[]> chiefComplaintsList = benChiefComplaintRepo.getBenChiefComplaints(beneficiaryRegID,
				visitCode);

		WrapperAncFindings findings = WrapperAncFindings.getFindingsData(clinicalObservationsList, chiefComplaintsList);
		return new Gson().toJson(findings);
	}

	public String getInvestigationDetails(Long beneficiaryRegID, Long visitCode) {
		ArrayList<Object[]> labTestOrders = labTestOrderDetailRepo.getLabTestOrderDetails(beneficiaryRegID, visitCode);
		WrapperBenInvestigationANC labTestOrdersList = LabTestOrderDetail.getLabTestOrderDetails(labTestOrders);

		return new Gson().toJson(labTestOrdersList);
	}

	public WrapperBenInvestigationANC getInvestigationDetailsWrapper(Long beneficiaryRegID, Long visitCode) {
		ArrayList<Object[]> labTestOrders = labTestOrderDetailRepo.getLabTestOrderDetails(beneficiaryRegID, visitCode);
		WrapperBenInvestigationANC labTestOrdersList = LabTestOrderDetail.getLabTestOrderDetails(labTestOrders);

		return labTestOrdersList;
	}

	public String getPrescribedDrugs(Long beneficiaryRegID, Long visitCode) {
		ArrayList<Object[]> resList = prescribedDrugDetailRepo.getBenPrescribedDrugDetails(beneficiaryRegID, visitCode);

		ArrayList<PrescribedDrugDetail> prescribedDrugs = PrescribedDrugDetail.getprescribedDrugs(resList);

		return new Gson().toJson(prescribedDrugs);
	}

	public String getReferralDetails(Long beneficiaryRegID, Long visitCode, Boolean rrList) {
		ArrayList<BenReferDetails> resList = benReferDetailsRepo.findByBeneficiaryRegIDAndVisitCode(beneficiaryRegID,
				visitCode);

		BenReferDetails referDetails;
		if (resList != null && resList.size() > 0) {
			referDetails = resList.get(resList.size() - 1);
			if (referDetails != null && referDetails.getReferralReason() != null)
				referDetails.setReferralReasonList(referDetails.getReferralReason().split("\\|\\|"));

			if (referDetails != null && referDetails.getServiceName() != null)
				referDetails.setRefrredToAdditionalServiceList(referDetails.getServiceName().split("\\|\\|"));

			if (rrList == null || !rrList)
				referDetails.setReferralReasonList(new String[0]);

			return new Gson().toJson(referDetails);

		}
		return new Gson().toJson(new BenReferDetails());
	}

	public Integer updateDocFindings(WrapperAncFindings wrapperAncFindings) {
		int clinObsrvtnsRes = 0;
		int chiefCmpltsRes = 0;
		int updateFindingsRes = 0;

		BenClinicalObservations benClinicalObservations = getBenClinicalObservations(wrapperAncFindings);
		clinObsrvtnsRes = updateBenClinicalObservations(benClinicalObservations);

		ArrayList<BenChiefComplaint> tmpBenCHiefComplaints = wrapperAncFindings.getComplaints();
		ArrayList<BenChiefComplaint> tmpBenCHiefComplaintsTMP = new ArrayList<>();
		if (tmpBenCHiefComplaints.size() > 0) {
			for (BenChiefComplaint benChiefComplaint : tmpBenCHiefComplaints) {
				if (benChiefComplaint.getChiefComplaintID() != null) {
					benChiefComplaint.setBeneficiaryRegID(wrapperAncFindings.getBeneficiaryRegID());
					benChiefComplaint.setBenVisitID(wrapperAncFindings.getBenVisitID());
					benChiefComplaint.setVisitCode(wrapperAncFindings.getVisitCode());
					benChiefComplaint.setProviderServiceMapID(wrapperAncFindings.getProviderServiceMapID());
					benChiefComplaint.setCreatedBy(wrapperAncFindings.getCreatedBy());

					tmpBenCHiefComplaintsTMP.add(benChiefComplaint);
				}
			}
			chiefCmpltsRes = updateDoctorBenChiefComplaints(tmpBenCHiefComplaintsTMP);

		} else {
			chiefCmpltsRes = 1;
		}
		if (clinObsrvtnsRes > 0 && chiefCmpltsRes > 0) {
			updateFindingsRes = 1;

		}
		return updateFindingsRes;
	}

	public int updateDoctorBenChiefComplaints(List<BenChiefComplaint> benChiefComplaintList) {
		int r = 0;
		if (null != benChiefComplaintList && benChiefComplaintList.size() > 0) {

			List<BenChiefComplaint> benChiefComplaintResultList = (List<BenChiefComplaint>) benChiefComplaintRepo
					.saveAll(benChiefComplaintList);

			if (benChiefComplaintResultList != null && benChiefComplaintResultList.size() > 0) {
				r = benChiefComplaintResultList.size();
			}
		} else {
			r = 1;
		}
		return r;
	}

	public int updateBenClinicalObservations(BenClinicalObservations benClinicalObservations) {
		Integer r = 0;
		int recordsAvailable = 0;
		if (null != benClinicalObservations) {
			String processed = benClinicalObservationsRepo.getBenClinicalObservationStatus(
					benClinicalObservations.getBeneficiaryRegID(), benClinicalObservations.getVisitCode());

			if (null != processed) {
				recordsAvailable = 1;
			}

			if (null != processed && !processed.equals("N")) {
				processed = "U";
			} else {
				processed = "N";
			}

			if (recordsAvailable > 0) {
				r = benClinicalObservationsRepo.updateBenClinicalObservations(
						benClinicalObservations.getClinicalObservation(), benClinicalObservations.getOtherSymptoms(),
						benClinicalObservations.getOtherSymptomsSCTCode(),
						benClinicalObservations.getOtherSymptomsSCTTerm(),
						benClinicalObservations.getSignificantFindings(), benClinicalObservations.getIsForHistory(),
						benClinicalObservations.getCreatedBy(), processed,
						benClinicalObservations.getBeneficiaryRegID(), benClinicalObservations.getVisitCode(),
						benClinicalObservations.getClinicalObservationSctcode(),
						benClinicalObservations.getSignificantFindingsSctcode());
			} else {
				BenClinicalObservations observationsRes = benClinicalObservationsRepo.save(benClinicalObservations);
				if (null != observationsRes && observationsRes.getClinicalObservationID() > 0) {
					r = 1;
				}
			}
		}
		return r;
	}

	public Long updateBenReferDetails(JsonObject referObj, Boolean rrList) throws IEMRException {
		Long ID = (long) 1;

		BenReferDetails referDetails = InputMapper.gson().fromJson(referObj, BenReferDetails.class);

		// referral reason list
		if (referDetails != null) {
			if (rrList != null && rrList) {
				if (referDetails.getReferralReasonList() != null && referDetails.getReferralReasonList().length > 0) {
					StringBuffer sb = new StringBuffer();
					for (String rr : referDetails.getReferralReasonList()) {
						sb.append(rr).append("||");
					}
					if (sb.length() >= 2)
						referDetails.setReferralReason(sb.substring(0, (sb.length() - 2)));
				} else
					referDetails.setReferralReason("");
			}
			// additional service referral list
			if (referDetails.getRefrredToAdditionalServiceList() != null
					&& referDetails.getRefrredToAdditionalServiceList().length > 0) {
				StringBuffer sb = new StringBuffer();
				for (String ras : referDetails.getRefrredToAdditionalServiceList()) {
					sb.append(ras).append("||");
				}
				if (sb.length() >= 2)
					referDetails.setServiceName(sb.substring(0, (sb.length() - 2)));

			}
			benReferDetailsRepo.save(referDetails);
		}

		return ID;
	}

	/**
	 * 
	 * 
	 * @param commonUtilityClass
	 * @param testList
	 * @param drugList
	 * @return
	 * @throws IEMRException
	 */
	/// ------Start of beneficiary flow table after doctor data save-------------

	public int updateBenFlowtableAfterDocDataSave(CommonUtilityClass commonUtilityClass, Boolean isTestPrescribed,
			Boolean isMedicinePrescribed, TeleconsultationRequestOBJ tcRequestOBJ) throws IEMRException {
		short pharmaFalg;
		short docFlag = (short) 1;
		short tcSpecialistFlag = (short) 0;

		short labTechnicianFlag = (short) 0;
		int tcUserID = 0;
		Timestamp tcDate = null;

		Long tmpBenFlowID = commonUtilityClass.getBenFlowID();
		Long tmpBeneficiaryID = commonUtilityClass.getBeneficiaryID();
		Long tmpBenVisitID = commonUtilityClass.getBenVisitID();
		Long tmpbeneficiaryRegID = commonUtilityClass.getBeneficiaryRegID();

		if (commonUtilityClass != null && commonUtilityClass.getVisitCategoryID() != null
				&& commonUtilityClass.getVisitCategoryID() == 9) {
			ArrayList<FoetalMonitor> foetalMonitorData = foetalMonitorRepo
					.getFoetalMonitorDetailsByFlowId(tmpBenFlowID);
			if (foetalMonitorData.size() > 0) {
				labTechnicianFlag = 3;
				for (FoetalMonitor data : foetalMonitorData) {
					if (data != null && !data.getResultState()) {
						labTechnicianFlag = 2;
					}
					if (data != null && data.getVisitCode() == null) {
						foetalMonitorRepo.updateVisitCode(commonUtilityClass.getVisitCode(), tmpBenFlowID);
					}
				}
			}
		}

		// check if TC specialist or doctor
		if (commonUtilityClass != null && commonUtilityClass.getIsSpecialist() != null
				&& commonUtilityClass.getIsSpecialist() == true) {
			// checking if test is prescribed
			if (isTestPrescribed) {
				tcSpecialistFlag = (short) 2;
			} else {
				if (labTechnicianFlag == 3)
					labTechnicianFlag = 9;
				tcSpecialistFlag = (short) 9;
			}

		} else {
			// checking if test is prescribed
			if (isTestPrescribed) {
				docFlag = (short) 2;
			} else {
				docFlag = (short) 9;
				if (labTechnicianFlag == 3)
					labTechnicianFlag = 9;
			}
		}

		// checking if medicine is prescribed
		if (isMedicinePrescribed) {
			pharmaFalg = (short) 1;
		} else {
			pharmaFalg = (short) 0;
		}

		if (tcRequestOBJ != null && tcRequestOBJ.getUserID() != null && tcRequestOBJ.getUserID() > 0
				&& tcRequestOBJ.getAllocationDate() != null) {
			tcSpecialistFlag = (short) 1;
			tcUserID = tcRequestOBJ.getUserID();
			tcDate = tcRequestOBJ.getAllocationDate();

		}

		int i = 0;

		if (commonUtilityClass != null && commonUtilityClass.getIsSpecialist() != null
				&& commonUtilityClass.getIsSpecialist() == true) {
			i = commonBenStatusFlowServiceImpl.updateBenFlowAfterDocDataFromSpecialist(tmpBenFlowID,
					tmpbeneficiaryRegID, tmpBeneficiaryID, tmpBenVisitID, docFlag, pharmaFalg, (short) 0,
					tcSpecialistFlag, labTechnicianFlag);
			if (tcSpecialistFlag == 9) {
				int l = tCRequestModelRepo.updateStatusIfConsultationCompleted(commonUtilityClass.getBeneficiaryRegID(),
						commonUtilityClass.getVisitCode(), "D");
			}

			// check if consultation start time is there and update the end time
			TeleconsultationStats teleconsultationStats = teleconsultationStatsRepo
					.getLatestStartTime(commonUtilityClass.getBeneficiaryRegID(), commonUtilityClass.getVisitCode());

			// if consultation end time is not available, update it else create a new entry
			if (teleconsultationStats != null) {
				if (teleconsultationStats.getEndTime() == null) {
					teleconsultationStats.setEndTime(new Timestamp(System.currentTimeMillis()));
				} else {
					teleconsultationStats.settMStatsID(null);
					teleconsultationStats.setEndTime(new Timestamp(System.currentTimeMillis()));
				}

				teleconsultationStatsRepo.save(teleconsultationStats);
			}

		} else
			i = commonBenStatusFlowServiceImpl.updateBenFlowAfterDocData(tmpBenFlowID, tmpbeneficiaryRegID,
					tmpBeneficiaryID, tmpBenVisitID, docFlag, pharmaFalg, (short) 0, tcSpecialistFlag, tcUserID, tcDate,
					labTechnicianFlag);
		if (commonUtilityClass.getIsSpecialist() == true) {
			if (tcSpecialistFlag == 9) {
				if (commonUtilityClass.getPrescriptionID() != null) {
					try {
						createTMPrescriptionSms(commonUtilityClass);
					} catch (Exception e) {
						logger.info("Error while sending TM prescription SMS :" + e.getMessage());
					}
				}

			}

		} else if (commonUtilityClass.getIsSpecialist() == false) {
			if (docFlag == 9 && tcRequestOBJ == null) {
				if (commonUtilityClass.getPrescriptionID() != null) {
					try {
						createTMPrescriptionSms(commonUtilityClass);
					} catch (Exception e) {
						logger.info("Error while sending TM prescription SMS :" + e.getMessage());
					}
				}
			}
		}
		return i;
	}

	/**
	 * 
	 * 
	 * 
	 * 
	 * @param commonUtilityClass
	 * @param isTestPrescribed
	 * @param isMedicinePrescribed
	 * @return
	 */
	public int updateBenFlowtableAfterDocDataUpdate(CommonUtilityClass commonUtilityClass, Boolean isTestPrescribed,
			Boolean isMedicinePrescribed, TeleconsultationRequestOBJ tcRequestOBJ) throws Exception {
		int i = 0;
		short pharmaFalg;
		short docFlag = (short) 0;
		short tcSpecialistFlag = (short) 0;
		int tcUserID = 0;
		Timestamp tcDate = null;

		short labTechnicianFlag = (short) 0;

		Long tmpBenFlowID = commonUtilityClass.getBenFlowID();
		Long tmpBeneficiaryID = commonUtilityClass.getBeneficiaryID();
		Long tmpBenVisitID = commonUtilityClass.getBenVisitID();
		Long tmpbeneficiaryRegID = commonUtilityClass.getBeneficiaryRegID();

		if (commonUtilityClass != null && commonUtilityClass.getVisitCategoryID() != null
				&& commonUtilityClass.getVisitCategoryID() == 9) {
			ArrayList<FoetalMonitor> foetalMonitorData = foetalMonitorRepo
					.getFoetalMonitorDetailsByFlowId(tmpBenFlowID);
			if (foetalMonitorData.size() > 0) {
				labTechnicianFlag = 3;
				for (FoetalMonitor data : foetalMonitorData) {
					if (data != null && !data.getResultState()) {
						labTechnicianFlag = 2;
					}
					if (data != null && data.getVisitCode() == null) {
						foetalMonitorRepo.updateVisitCode(commonUtilityClass.getVisitCode(), tmpBenFlowID);
					}
				}
			}
		}

		if (commonUtilityClass.getIsSpecialist() != null && commonUtilityClass.getIsSpecialist() == true) {
			if (isTestPrescribed)
				tcSpecialistFlag = (short) 2;
			else {
				tcSpecialistFlag = (short) 9;
				if (labTechnicianFlag == 3)
					labTechnicianFlag = 9;
			}

			if (isMedicinePrescribed)
				pharmaFalg = (short) 1;
			else
				pharmaFalg = (short) 0;

			i = commonBenStatusFlowServiceImpl.updateBenFlowAfterDocDataUpdateTCSpecialist(tmpBenFlowID,
					tmpbeneficiaryRegID, tmpBeneficiaryID, tmpBenVisitID, docFlag, pharmaFalg, (short) 0,
					tcSpecialistFlag, tcUserID, tcDate, labTechnicianFlag);

			if (tcSpecialistFlag == 9) {
				int l = tCRequestModelRepo.updateStatusIfConsultationCompleted(commonUtilityClass.getBeneficiaryRegID(),
						commonUtilityClass.getVisitCode(), "D");
			}

			// check if consultation start time is there and update the end time
			TeleconsultationStats teleconsultationStats = teleconsultationStatsRepo
					.getLatestStartTime(commonUtilityClass.getBeneficiaryRegID(), commonUtilityClass.getVisitCode());

			// if consultation end time is not available, update it else create a new entry
			if (teleconsultationStats != null) {
				if (teleconsultationStats.getEndTime() == null) {
					teleconsultationStats.setEndTime(new Timestamp(System.currentTimeMillis()));
				} else {
					teleconsultationStats.settMStatsID(null);
					teleconsultationStats.setEndTime(new Timestamp(System.currentTimeMillis()));
				}

				teleconsultationStatsRepo.save(teleconsultationStats);
			}

		} else {

			if (isTestPrescribed)
				docFlag = (short) 2;
			else {
				docFlag = (short) 9;
				if (labTechnicianFlag == 3)
					labTechnicianFlag = 9;
			}

			if (isMedicinePrescribed)
				pharmaFalg = (short) 1;
			else
				pharmaFalg = (short) 0;

			if (tcRequestOBJ != null && tcRequestOBJ.getUserID() != null && tcRequestOBJ.getUserID() > 0
					&& tcRequestOBJ.getAllocationDate() != null) {
				tcSpecialistFlag = (short) 1;
				tcUserID = tcRequestOBJ.getUserID();
				tcDate = tcRequestOBJ.getAllocationDate();
			}

			i = commonBenStatusFlowServiceImpl.updateBenFlowAfterDocDataUpdate(tmpBenFlowID, tmpbeneficiaryRegID,
					tmpBeneficiaryID, tmpBenVisitID, docFlag, pharmaFalg, (short) 0, tcSpecialistFlag, tcUserID, tcDate,
					labTechnicianFlag);

		}

		if (commonUtilityClass.getIsSpecialist() == true) {
			if (tcSpecialistFlag == 9) {
				if (commonUtilityClass.getPrescriptionID() != null)
					createTMPrescriptionSms(commonUtilityClass);
			}
		} else if (commonUtilityClass.getIsSpecialist() == false) {
			if (docFlag == 9 && tcRequestOBJ == null) {
				if (commonUtilityClass.getPrescriptionID() != null)
					createTMPrescriptionSms(commonUtilityClass);
			}
		}
		return i;
	}

	public String deletePrescribedMedicine(JSONObject obj) {
		int i = 0;
		if (obj != null && obj.has("id")) {
			i = prescribedDrugDetailRepo.deletePrescribedmedicine(obj.getLong("id"));
		} else {

		}
		if (i > 0)
			return "record deleted successfully";
		else
			return null;
	}

	public int callTmForSpecialistSlotBook(TcSpecialistSlotBookingRequestOBJ tcSpecialistSlotBookingRequestOBJ,
			String Authorization) {
		int successFlag = 0;
		String requestOBJ = OutputMapper.gson().toJson(tcSpecialistSlotBookingRequestOBJ);

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<Object> request = RestTemplateUtil.createRequestEntity(requestOBJ, Authorization);
		ResponseEntity<String> response = restTemplate.exchange(tcSpecialistSlotBook, HttpMethod.POST, request,
				String.class);

		if (response.getStatusCodeValue() == 200 && response.hasBody()) {
			JsonObject jsnOBJ = new JsonObject();
			JsonParser jsnParser = new JsonParser();
			JsonElement jsnElmnt = jsnParser.parse(response.getBody());
			jsnOBJ = jsnElmnt.getAsJsonObject();
			if (jsnOBJ.has("statusCode") && jsnOBJ.get("statusCode").getAsInt() == 200)
				successFlag = 1;
		}
		return successFlag;
	}

	public void createTMPrescriptionSms(CommonUtilityClass commonUtilityClass) throws IEMRException {
		List<Object> diagnosis = null;
		List<PrescribedDrugDetail> prescriptionDetails = null;
		int k = 0;
		prescriptionDetails = prescribedDrugDetailRepo.getPrescriptionDetails(commonUtilityClass.getPrescriptionID());
		if (prescriptionDetails != null && prescriptionDetails.size() > 0) {
			try {

				if (commonUtilityClass.getVisitCategoryID() == 6 || commonUtilityClass.getVisitCategoryID() == 7
						|| commonUtilityClass.getVisitCategoryID() == 8) {
					diagnosis = prescriptionDetailRepo.getProvisionalDiagnosis(commonUtilityClass.getVisitCode(),
							commonUtilityClass.getPrescriptionID());// add visit code too
				} else if (commonUtilityClass.getVisitCategoryID() == 3) {
					diagnosis = NCDCareDiagnosisRepo.getNCDcondition(commonUtilityClass.getVisitCode(),
							commonUtilityClass.getPrescriptionID());// add visit code too
				} else if (commonUtilityClass.getVisitCategoryID() == 5) {
					diagnosis = pNCDiagnosisRepo.getProvisionalDiagnosis(commonUtilityClass.getVisitCode(),
							commonUtilityClass.getPrescriptionID());
				}
			} catch (Exception e) {
				logger.info("Exception during fetching diagnosis and precription detail " + e.getMessage());
			}

			try {
				if (prescriptionDetails != null)
					k = sMSGatewayServiceImpl.smsSenderGateway2("prescription", prescriptionDetails,
							commonUtilityClass.getAuthorization(), commonUtilityClass.getBeneficiaryRegID(),
							commonUtilityClass.getCreatedBy(), diagnosis);
			} catch (Exception e) {
				logger.info("Exception during sending TM prescription SMS " + e.getMessage());
			}
		}
		if (k != 0)
			logger.info("SMS sent for TM Prescription");
		else
			logger.info("SMS not sent for TM Prescription");
	}

	public String getFoetalMonitorData(Long beneFiciaryRegID, Long visitCode) {

		ArrayList<FoetalMonitor> foetalMonitorData = foetalMonitorRepo
				.getFoetalMonitorDetailsForCaseRecord(beneFiciaryRegID, visitCode);

		return new Gson().toJson(foetalMonitorData);

	}
}
