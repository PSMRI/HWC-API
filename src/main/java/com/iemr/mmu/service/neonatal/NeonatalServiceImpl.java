/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.service.neonatal;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.iemr.mmu.data.anc.WrapperAncFindings;
import com.iemr.mmu.data.anc.WrapperBenInvestigationANC;
import com.iemr.mmu.data.anc.WrapperImmunizationHistory;
import com.iemr.mmu.data.neonatal.FollowUpForImmunization;
import com.iemr.mmu.data.neonatal.ImmunizationServices;
import com.iemr.mmu.data.neonatal.InfantBirthDetails;
import com.iemr.mmu.data.neonatal.Vaccines;
import com.iemr.mmu.data.nurse.BenAnthropometryDetail;
import com.iemr.mmu.data.nurse.BenPhysicalVitalDetail;
import com.iemr.mmu.data.nurse.BeneficiaryVisitDetail;
import com.iemr.mmu.data.nurse.CommonUtilityClass;
import com.iemr.mmu.data.quickConsultation.BenChiefComplaint;
import com.iemr.mmu.data.quickConsultation.PrescribedDrugDetail;
import com.iemr.mmu.data.quickConsultation.PrescriptionDetail;
import com.iemr.mmu.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.mmu.repo.neonatal.FollowUpForImmunizationRepo;
import com.iemr.mmu.repo.neonatal.ImmunizationServicesRepo;
import com.iemr.mmu.repo.neonatal.InfantBirthDetailsRepo;
import com.iemr.mmu.repo.nurse.BenVisitDetailRepo;
import com.iemr.mmu.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.mmu.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.mmu.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.mmu.service.common.transaction.CommonServiceImpl;
import com.iemr.mmu.service.generalOPD.GeneralOPDDoctorServiceImpl;
import com.iemr.mmu.service.labtechnician.LabTechnicianServiceImpl;
import com.iemr.mmu.service.tele_consultation.SMSGatewayServiceImpl;
import com.iemr.mmu.utils.exception.IEMRException;
import com.iemr.mmu.utils.mapper.InputMapper;

@Service
public class NeonatalServiceImpl implements NeonatalService {

	@Autowired
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;
	@Autowired
	private CommonServiceImpl commonServiceImpl;
	@Autowired
	private CommonNurseServiceImpl commonNurseServiceImpl;
	@Autowired
	private SMSGatewayServiceImpl sMSGatewayServiceImpl;
	@Autowired
	private InfantBirthDetailsRepo infantBirthDetailsRepo;
	@Autowired
	private ImmunizationServicesRepo immunizationServicesRepo;
	@Autowired
	private BenVisitDetailRepo benVisitDetailRepo;
	@Autowired
	private CommonDoctorServiceImpl commonDoctorServiceImpl;
	@Autowired
	private FollowUpForImmunizationRepo followUpForImmunizationRepo;
	@Autowired
	private GeneralOPDDoctorServiceImpl generalOPDDoctorServiceImpl;
	@Autowired
	private LabTechnicianServiceImpl labTechnicianServiceImpl;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String saveNurseData(JsonObject requestOBJ, String Authorization) throws SQLException, Exception {

		Map<String, String> responseMap = new HashMap<String, String>();
		Long benVisitCode = null;
		TeleconsultationRequestOBJ tcRequestOBJ = null;

		if (requestOBJ != null && requestOBJ.has("visitDetails") && !requestOBJ.get("visitDetails").isJsonNull()) {

			CommonUtilityClass nurseUtilityClass = InputMapper.gson().fromJson(requestOBJ, CommonUtilityClass.class);
			// Call method to save visit details data
			Map<String, Long> visitIdAndCodeMap = saveBenVisitDetails(requestOBJ.getAsJsonObject("visitDetails"),
					nurseUtilityClass);

			Long benVisitID = null;

			if (visitIdAndCodeMap != null && visitIdAndCodeMap.size() > 0 && visitIdAndCodeMap.containsKey("visitID")
					&& visitIdAndCodeMap.containsKey("visitCode")) {
				benVisitID = visitIdAndCodeMap.get("visitID");
				benVisitCode = visitIdAndCodeMap.get("visitCode");

				nurseUtilityClass.setVisitCode(benVisitCode);
				nurseUtilityClass.setBenVisitID(benVisitID);
			}

			JsonObject tmpOBJ = requestOBJ.getAsJsonObject("visitDetails").getAsJsonObject("visitDetails");
			// Getting benflowID for ben status update
			Long benFlowID = null;
			// Above if block code replaced by below line
			benFlowID = nurseUtilityClass.getBenFlowID();

			if (benVisitID != null && benVisitID > 0) {
				// create tc request
				tcRequestOBJ = commonServiceImpl.createTcRequest(requestOBJ, nurseUtilityClass, Authorization);
				// call method to save History data
				if (requestOBJ.has("immunizationHistory") && !requestOBJ.get("immunizationHistory").isJsonNull())
					saveBenNeoNatalHistoryDetails(requestOBJ, benVisitID, benVisitCode);

				// call method to save vital data
				if (requestOBJ.has("vitalDetails") && !requestOBJ.get("vitalDetails").isJsonNull())
					saveBenVitalDetails(requestOBJ.getAsJsonObject("vitalDetails"), benVisitID, benVisitCode);

				// infant birth details save
				if (requestOBJ.has("infantBirthDetails") && !requestOBJ.get("infantBirthDetails").isJsonNull()) {
					InfantBirthDetails infantBirthDetails = InputMapper.gson()
							.fromJson(requestOBJ.get("infantBirthDetails"), InfantBirthDetails.class);

					if (infantBirthDetails != null) {
						infantBirthDetails.setVisitCode(benVisitCode);

						if (infantBirthDetails.getCongenitalAnomaliesList() != null
								&& infantBirthDetails.getCongenitalAnomaliesList().length > 0) {
							StringBuffer sb = new StringBuffer();
							for (String s : infantBirthDetails.getCongenitalAnomaliesList()) {
								sb.append(s).append("||");
							}
							if (sb.length() >= 2)
								infantBirthDetails.setCongenitalAnomalies(sb.substring(0, sb.length() - 2));
						}
						infantBirthDetailsRepo.save(infantBirthDetails);
					}
				}

				// immunization service
				if (requestOBJ.has("immunizationServices") && !requestOBJ.get("immunizationServices").isJsonNull()) {
					ImmunizationServices is = InputMapper.gson().fromJson(requestOBJ.get("immunizationServices"),
							ImmunizationServices.class);

					List<ImmunizationServices> isList = new ArrayList<ImmunizationServices>();
					ImmunizationServices obj;
					if (is != null && is.getVaccines() != null && is.getVaccines().size() > 0) {
						// for each vaccine, create a new db obj
						for (Vaccines v : is.getVaccines()) {
							if (v.getVaccineName() != null) {
								obj = new ImmunizationServices();

								obj.setBeneficiaryRegID(is.getBeneficiaryRegID());
								obj.setVanID(is.getVanID());
								obj.setProviderServiceMapID(is.getProviderServiceMapID());
								obj.setParkingPlaceID(is.getParkingPlaceID());
								obj.setCreatedBy(is.getCreatedBy());

								obj.setDateOfVisit(is.getDateOfVisit());
								obj.setImmunizationServicesType(is.getImmunizationServicesType());
								obj.setImmunizationServicesTypeID(is.getImmunizationServicesTypeID());
								obj.setCurrentImmunizationService(is.getCurrentImmunizationService());
								obj.setCurrentImmunizationServiceID(is.getCurrentImmunizationServiceID());

								obj.setVaccineName(v.getVaccineName());
								obj.setVaccineDose(v.getVaccineDose());
								obj.setRoute(v.getRoute());
								obj.setBatchNo(v.getBatchNo());
								obj.setSiteOfInjection(v.getSiteOfInjection());

								obj.setVisitCode(benVisitCode);

								isList.add(obj);
							}
						}

						if (isList.size() > 0)
							immunizationServicesRepo.save(isList);
					}
				}

				/**
				 * We have to write new code to update ben status flow new logic
				 */
				int i = updateBenStatusFlagAfterNurseSaveSuccess(tmpOBJ, benVisitID, benFlowID, benVisitCode,
						nurseUtilityClass.getVanID(), tcRequestOBJ);

				if (i > 0 && tcRequestOBJ != null && tcRequestOBJ.getWalkIn() == false) {
					int k = sMSGatewayServiceImpl.smsSenderGateway("schedule", nurseUtilityClass.getBeneficiaryRegID(),
							tcRequestOBJ.getSpecializationID(), tcRequestOBJ.getTmRequestID(), null,
							nurseUtilityClass.getCreatedBy(),
							tcRequestOBJ.getAllocationDate() != null ? String.valueOf(tcRequestOBJ.getAllocationDate())
									: "",
							null, Authorization);
				}

			}

			if (benVisitCode != null)
				responseMap.put("visitCode", benVisitCode.toString());
			responseMap.put("response", "Data saved successfully");
		} else {
			throw new RuntimeException("Invalid request, visit details is missing");
		}

		return new Gson().toJson(responseMap);
	}

	/// --------------- start of saving doctor data ------------------------
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int saveDoctorDataNNI(JsonObject requestOBJ, String Authorization) throws Exception {
		int saveSuccessFlag = 1;
		Long prescriptionID = null;

		// Integer tcRequestStatusFlag = null;

		if (requestOBJ != null) {
			TeleconsultationRequestOBJ tcRequestOBJ = null;
			CommonUtilityClass commonUtilityClass = InputMapper.gson().fromJson(requestOBJ, CommonUtilityClass.class);

			// create TC request if applicable
			tcRequestOBJ = commonServiceImpl.createTcRequest(requestOBJ, commonUtilityClass, Authorization);

			JsonArray testList = null;
			JsonArray drugList = null;

			Boolean isTestPrescribed = false;
			Boolean isMedicinePrescribed = false;

			// checking if test is prescribed
			if (requestOBJ.has("investigation") && !requestOBJ.get("investigation").isJsonNull()
					&& requestOBJ.get("investigation") != null) {
				testList = requestOBJ.getAsJsonObject("investigation").getAsJsonArray("laboratoryList");
				if (testList != null && !testList.isJsonNull() && testList.size() > 0)
					isTestPrescribed = true;
			}
			// checking if medicine is prescribed
			if (requestOBJ.has("prescription") && !requestOBJ.get("prescription").isJsonNull()
					&& requestOBJ.get("prescription") != null) {
				drugList = requestOBJ.getAsJsonArray("prescription");
				if (drugList != null && !drugList.isJsonNull() && drugList.size() > 0) {
					isMedicinePrescribed = true;
				}
			}

			// save findings
			if (requestOBJ.has("findings") && !requestOBJ.get("findings").isJsonNull()) {
				WrapperAncFindings wrapperAncFindings = InputMapper.gson().fromJson(requestOBJ.get("findings"),
						WrapperAncFindings.class);
				commonDoctorServiceImpl.saveDocFindings(wrapperAncFindings);

			}

			// creating prescription object
			PrescriptionDetail prescriptionDetail = new PrescriptionDetail();

			if (requestOBJ.has("diagnosis") && !requestOBJ.get("diagnosis").isJsonNull()) {
				JsonObject diagnosisObj = requestOBJ.getAsJsonObject("diagnosis");

				prescriptionDetail = InputMapper.gson().fromJson(diagnosisObj, PrescriptionDetail.class);
			} else {
			}

			WrapperBenInvestigationANC wrapperBenInvestigationANC = InputMapper.gson()
					.fromJson(requestOBJ.get("investigation"), WrapperBenInvestigationANC.class);
			// Save Prescription
			prescriptionDetail.setExternalInvestigation(wrapperBenInvestigationANC.getExternalInvestigations());
			if (commonUtilityClass.getTreatmentsOnSideEffects() != null)
				prescriptionDetail.setTreatmentsOnSideEffects(commonUtilityClass.getTreatmentsOnSideEffects());

			if (requestOBJ.has("counsellingProvidedList") && !requestOBJ.get("counsellingProvidedList").isJsonNull()
					&& requestOBJ.get("counsellingProvidedList") != null) {
				PrescriptionDetail tempPrescription = InputMapper.gson().fromJson(requestOBJ, PrescriptionDetail.class);
				if (tempPrescription != null && tempPrescription.getCounsellingProvidedList() != null
						&& tempPrescription.getCounsellingProvidedList().length > 0) {
					StringBuffer sb = new StringBuffer();
					for (String s : tempPrescription.getCounsellingProvidedList()) {
						sb.append(s).append("||");
					}
					if (sb.length() >= 2)
						prescriptionDetail.setCounsellingProvided(sb.substring(0, sb.length() - 2));

				}

			}
			prescriptionID = commonNurseServiceImpl.saveBenPrescription(prescriptionDetail);

			// save prescribed lab test
			if (isTestPrescribed) {
				if (wrapperBenInvestigationANC != null) {
					wrapperBenInvestigationANC.setPrescriptionID(prescriptionID);
					commonNurseServiceImpl.saveBenInvestigation(wrapperBenInvestigationANC);
				}
			}
			// save prescribed medicine
			if (isMedicinePrescribed) {
				PrescribedDrugDetail[] prescribedDrugDetail = InputMapper.gson()
						.fromJson(requestOBJ.get("prescription"), PrescribedDrugDetail[].class);

				List<PrescribedDrugDetail> prescribedDrugDetailList = Arrays.asList(prescribedDrugDetail);

				if (prescribedDrugDetailList.size() > 0) {
					for (PrescribedDrugDetail tmpObj : prescribedDrugDetailList) {
						tmpObj.setPrescriptionID(prescriptionID);
						tmpObj.setBeneficiaryRegID(commonUtilityClass.getBeneficiaryRegID());
						tmpObj.setBenVisitID(commonUtilityClass.getBenVisitID());
						tmpObj.setVisitCode(commonUtilityClass.getVisitCode());
						tmpObj.setProviderServiceMapID(commonUtilityClass.getProviderServiceMapID());
					}

					commonNurseServiceImpl.saveBenPrescribedDrugsList(prescribedDrugDetailList);
				}
			}

			// save followUpForImmunization
			if (requestOBJ.has("followUpForImmunization") && !requestOBJ.get("followUpForImmunization").isJsonNull()
					&& requestOBJ.get("followUpForImmunization") != null) {
				FollowUpForImmunization followUpForImmunization = InputMapper.gson()
						.fromJson(requestOBJ.get("followUpForImmunization"), FollowUpForImmunization.class);

				if (followUpForImmunization != null)
					followUpForImmunizationRepo.save(followUpForImmunization);
			}

			// check if all requested data saved properly
			if (prescriptionID != null && prescriptionID > 0) {
				// call method to update beneficiary flow table
				if (prescriptionID != null) {
					commonUtilityClass.setPrescriptionID(prescriptionID);
					commonUtilityClass.setVisitCategoryID(6);
					commonUtilityClass.setAuthorization(Authorization);

				}
				int i = commonDoctorServiceImpl.updateBenFlowtableAfterDocDataSave(commonUtilityClass, isTestPrescribed,
						isMedicinePrescribed, tcRequestOBJ);

				if (i > 0)
					saveSuccessFlag = 1;
				else
					throw new RuntimeException("Error occurred while saving data. Beneficiary status update failed");

				if (i > 0 && tcRequestOBJ != null && tcRequestOBJ.getWalkIn() == false) {
					int k = sMSGatewayServiceImpl.smsSenderGateway("schedule", commonUtilityClass.getBeneficiaryRegID(),
							tcRequestOBJ.getSpecializationID(), tcRequestOBJ.getTmRequestID(), null,
							commonUtilityClass.getCreatedBy(),
							tcRequestOBJ.getAllocationDate() != null ? String.valueOf(tcRequestOBJ.getAllocationDate())
									: "",
							null, Authorization);
				}

			} else {
				throw new RuntimeException();
			}
		} else {
			// request OBJ is null.
		}
		return saveSuccessFlag;
	}
	/// ------------------- END of saving doctor data ------------------------

	// method for updating ben flow status flag for nurse
	private int updateBenStatusFlagAfterNurseSaveSuccess(JsonObject tmpOBJ, Long benVisitID, Long benFlowID,
			Long benVisitCode, Integer vanID, TeleconsultationRequestOBJ tcRequestOBJ) {
		short nurseFlag = (short) 9;
		short docFlag = (short) 1;
		short labIteration = (short) 0;

		short specialistFlag = (short) 0;
		Timestamp tcDate = null;
		Integer tcSpecialistUserID = null;

		if (tcRequestOBJ != null && tcRequestOBJ.getUserID() != null && tcRequestOBJ.getAllocationDate() != null) {
			specialistFlag = (short) 1;
			tcDate = tcRequestOBJ.getAllocationDate();
			tcSpecialistUserID = tcRequestOBJ.getUserID();
		} else
			specialistFlag = (short) 0;

		int i = commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(benFlowID,
				tmpOBJ.get("beneficiaryRegID").getAsLong(), benVisitID, tmpOBJ.get("visitReason").getAsString(),
				tmpOBJ.get("visitCategory").getAsString(), nurseFlag, docFlag, labIteration, (short) 0, (short) 0,
				benVisitCode, vanID, specialistFlag, tcDate, tcSpecialistUserID);

		return i;
	}

	private Map<String, Long> saveBenVisitDetails(JsonObject visitDetailsOBJ, CommonUtilityClass nurseUtilityClass)
			throws Exception {
		Map<String, Long> visitIdAndCodeMap = new HashMap<>();
		Long benVisitID = null;
		if (visitDetailsOBJ != null && visitDetailsOBJ.has("visitDetails")
				&& !visitDetailsOBJ.get("visitDetails").isJsonNull()) {
			// Save Beneficiary visit details
			BeneficiaryVisitDetail benVisitDetailsOBJ = InputMapper.gson().fromJson(visitDetailsOBJ.get("visitDetails"),
					BeneficiaryVisitDetail.class);
			benVisitID = commonNurseServiceImpl.saveBeneficiaryVisitDetails(benVisitDetailsOBJ);

			// generate visit code
			Long benVisitCode = commonNurseServiceImpl.generateVisitCode(benVisitID, nurseUtilityClass.getVanID(),
					nurseUtilityClass.getSessionID());

			if (benVisitID != null && benVisitID > 0 && benVisitCode != null && benVisitCode > 0) {
				if (visitDetailsOBJ.has("chiefComplaints") && !visitDetailsOBJ.get("chiefComplaints").isJsonNull()) {
					BenChiefComplaint[] benChiefComplaintArray = InputMapper.gson()
							.fromJson(visitDetailsOBJ.get("chiefComplaints"), BenChiefComplaint[].class);

					List<BenChiefComplaint> benChiefComplaintList = Arrays.asList(benChiefComplaintArray);
					if (null != benChiefComplaintList && benChiefComplaintList.size() > 0) {
						for (BenChiefComplaint benChiefComplaint : benChiefComplaintList) {
							benChiefComplaint.setBenVisitID(benVisitID);
							benChiefComplaint.setVisitCode(benVisitCode);
						}
					}
					// Save Beneficiary Chief Complaints
					commonNurseServiceImpl.saveBenChiefComplaints(benChiefComplaintList);
				}
			}
			visitIdAndCodeMap.put("visitID", benVisitID);
			visitIdAndCodeMap.put("visitCode", benVisitCode);
		}
		return visitIdAndCodeMap;
	}

	private Long saveBenNeoNatalHistoryDetails(JsonObject generalOPDHistoryOBJ, Long benVisitID, Long benVisitCode)
			throws Exception {

		// Save Immunization History
		if (generalOPDHistoryOBJ != null && generalOPDHistoryOBJ.has("immunizationHistory")
				&& !generalOPDHistoryOBJ.get("immunizationHistory").isJsonNull()) {
			WrapperImmunizationHistory wrapperImmunizationHistory = InputMapper.gson()
					.fromJson(generalOPDHistoryOBJ.get("immunizationHistory"), WrapperImmunizationHistory.class);
			if (null != wrapperImmunizationHistory) {
				wrapperImmunizationHistory.setBenVisitID(benVisitID);
				wrapperImmunizationHistory.setVisitCode(benVisitCode);
				commonNurseServiceImpl.saveImmunizationHistory(wrapperImmunizationHistory);
			}
		}

		return (long) 1;
	}

	private Long saveBenVitalDetails(JsonObject vitalDetailsOBJ, Long benVisitID, Long benVisitCode) throws Exception {
		Long vitalSuccessFlag = null;
		Long anthropometrySuccessFlag = null;
		Long phyVitalSuccessFlag = null;
		// Save Physical Anthropometry && Physical Vital Details
		if (vitalDetailsOBJ != null) {
			BenAnthropometryDetail benAnthropometryDetail = InputMapper.gson().fromJson(vitalDetailsOBJ,
					BenAnthropometryDetail.class);
			BenPhysicalVitalDetail benPhysicalVitalDetail = InputMapper.gson().fromJson(vitalDetailsOBJ,
					BenPhysicalVitalDetail.class);

			if (null != benAnthropometryDetail) {
				benAnthropometryDetail.setBenVisitID(benVisitID);
				benAnthropometryDetail.setVisitCode(benVisitCode);
				anthropometrySuccessFlag = commonNurseServiceImpl
						.saveBeneficiaryPhysicalAnthropometryDetails(benAnthropometryDetail);
			}
			if (null != benPhysicalVitalDetail) {
				benPhysicalVitalDetail.setBenVisitID(benVisitID);
				benPhysicalVitalDetail.setVisitCode(benVisitCode);
				phyVitalSuccessFlag = commonNurseServiceImpl
						.saveBeneficiaryPhysicalVitalDetails(benPhysicalVitalDetail);
			}

			if (anthropometrySuccessFlag != null && anthropometrySuccessFlag > 0 && phyVitalSuccessFlag != null
					&& phyVitalSuccessFlag > 0) {
				vitalSuccessFlag = anthropometrySuccessFlag;
			}
		} else {
			vitalSuccessFlag = new Long(1);
		}

		return vitalSuccessFlag;
	}

	// fetch nurse data ....................
	/// --------------- Start of Fetching GeneralOPD Nurse Data ----------------
	@Override
	public String getBenVisitDetailsFrmNurseNNI(Long benRegID, Long visitCode) throws Exception {
		Map<String, Object> resMap = new HashMap<>();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

		BeneficiaryVisitDetail visitDetail = commonNurseServiceImpl.getCSVisitDetails(benRegID, visitCode);

		resMap.put("neonatalNurseVisitDetail", gson.toJson(visitDetail));

		resMap.put("BenChiefComplaints", commonNurseServiceImpl.getBenChiefComplaints(benRegID, visitCode));

		return resMap.toString();
	}

	@Override
	public String getBeneficiaryVitalDetails(Long beneficiaryRegID, Long visitCode) throws Exception {
		Map<String, Object> resMap = new HashMap<>();

		resMap.put("benAnthropometryDetail",
				commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, visitCode));
		resMap.put("benPhysicalVitalDetail",
				commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode));

		return resMap.toString();
	}

	@Override
	public String getBirthAndImmuniHistory(Long benRegID, Long visitCode) throws IEMRException {
		Map<String, Object> HistoryDetailsMap = new HashMap<String, Object>();

		if (visitCode == null) {
			visitCode = benVisitDetailRepo.getLatestVisitCodeForGivenVC(benRegID,
					"Neonatal and infant health care services");

			if (visitCode == null)
				throw new IEMRException("no previous information found. please capture latest information");
		}

		HistoryDetailsMap.put("immunizationHistory", commonNurseServiceImpl.getImmunizationHistory(benRegID, visitCode));

		InfantBirthDetails ibObj = infantBirthDetailsRepo.findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode);
		if (ibObj != null && ibObj.getId() != null && ibObj.getId() > 0) {
			if (ibObj.getCongenitalAnomalies() != null && ibObj.getCongenitalAnomalies().length() > 0) {
				String[] caArr = ibObj.getCongenitalAnomalies().split("\\|\\|");
				if (caArr != null)
					ibObj.setCongenitalAnomaliesList(caArr);
			}
		}

		HistoryDetailsMap.put("infantBirthDetails", ibObj);

		return new Gson().toJson(HistoryDetailsMap);
	}

	@Override
	public String getBeneficiaryImmunizationServiceDetails(Long beneficiaryRegID, Long visitCode) throws Exception {
		Map<String, Object> resMap = new HashMap<>();
		Vaccines vaccines;
		List<Vaccines> vaccineList = new ArrayList<>();

		List<ImmunizationServices> rsList = immunizationServicesRepo
				.findByBeneficiaryRegIDAndVisitCodeAndDeleted(beneficiaryRegID, visitCode, false);

		if (rsList != null && rsList.size() > 0) {
			ImmunizationServices output = rsList.get(0);
			for (ImmunizationServices is : rsList) {
				if (is.getVaccineName() != null) {
					vaccines = new Vaccines();
					vaccines.setVaccineName(is.getVaccineName());
					if (is.getVaccineDose() != null)
						vaccines.setVaccineDose(is.getVaccineDose());
					if (is.getBatchNo() != null)
						vaccines.setBatchNo(is.getBatchNo());
					if (is.getRoute() != null)
						vaccines.setRoute(is.getRoute());
					if (is.getSiteOfInjection() != null)
						vaccines.setSiteOfInjection(is.getSiteOfInjection());

					vaccineList.add(vaccines);
				}
			}
			output.setVaccines(vaccineList);
			resMap.put("immunizationServices", new Gson().toJson(output));
		}
		// correct

		return resMap.toString();
	}

//	@Override
//	public String getBenImmunizationServiceHistory(Long beneficiaryRegID) throws Exception {
//
//		List<ImmunizationServices> rsList = immunizationServicesRepo.findByBeneficiaryRegIDAndDeleted(beneficiaryRegID,
//				false);
//
//		return new Gson().toJson(rsList);
//	}

	@Override
	public String getBenCaseRecordFromDoctorNNI(Long benRegID, Long visitCode) throws IEMRException {
		Map<String, Object> resMap = new HashMap<>();

		resMap.put("findings", commonDoctorServiceImpl.getFindingsDetails(benRegID, visitCode));

		String diagnosis_prescription = generalOPDDoctorServiceImpl.getGeneralOPDDiagnosisDetails(benRegID, visitCode);
		resMap.put("diagnosis", diagnosis_prescription);

		if (diagnosis_prescription != null) {

			PrescriptionDetail pd = new Gson().fromJson(diagnosis_prescription, PrescriptionDetail.class);
			if (pd != null && pd.getCounsellingProvided() != null) {
				resMap.put("counsellingProvidedList", new Gson().toJson(pd.getCounsellingProvided().split("\\|\\|")));
			}
		}

		resMap.put("investigation", commonDoctorServiceImpl.getInvestigationDetails(benRegID, visitCode));

		resMap.put("prescription", commonDoctorServiceImpl.getPrescribedDrugs(benRegID, visitCode));

		resMap.put("LabReport",
				new Gson().toJson(labTechnicianServiceImpl.getLabResultDataForBen(benRegID, visitCode)));

		resMap.put("GraphData", new Gson().toJson(commonNurseServiceImpl.getGraphicalTrendData(benRegID, "genOPD")));

		resMap.put("ArchivedVisitcodeForLabResult",
				labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(benRegID, visitCode));

		FollowUpForImmunization fuI = followUpForImmunizationRepo.findByBeneficiaryRegIDAndVisitCode(benRegID,
				visitCode);
		if (fuI != null)
			resMap.put("followUpForImmunization", new Gson().toJson(fuI));

		return resMap.toString();
	}

	@Override
	public String getNurseDataNNI(Long benRegID, Long visitCode) throws Exception {
		HashMap<String, Object> resMap = new HashMap<>();

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		BeneficiaryVisitDetail visitDetail = commonNurseServiceImpl.getCSVisitDetails(benRegID, visitCode);

		resMap.put("neonatalNurseVisitDetail", gson.toJson(visitDetail));
		resMap.put("BenChiefComplaints", commonNurseServiceImpl.getBenChiefComplaints(benRegID, visitCode));

		resMap.put("history", getBirthAndImmuniHistory(benRegID, visitCode));
		resMap.put("vitals", getBeneficiaryVitalDetails(benRegID, visitCode));
		resMap.put("immunizationServices", getBeneficiaryImmunizationServiceDetails(benRegID, visitCode));

		return resMap.toString();
	}

/// *****************  update nurse data *****************************

	// update vitals & anthro
	/**
	 * 
	 * @param requestOBJ
	 * @return success or failure flag for vitals data updating
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateBenVitalDetailsNNI(JsonObject vitalDetailsOBJ) throws Exception {

		// update Physical Anthropometry && Physical Vital Details
		if (vitalDetailsOBJ != null) {
			BenAnthropometryDetail benAnthropometryDetail = InputMapper.gson().fromJson(vitalDetailsOBJ,
					BenAnthropometryDetail.class);
			BenPhysicalVitalDetail benPhysicalVitalDetail = InputMapper.gson().fromJson(vitalDetailsOBJ,
					BenPhysicalVitalDetail.class);

			commonNurseServiceImpl.updateANCAnthropometryDetails(benAnthropometryDetail);
			commonNurseServiceImpl.updateANCPhysicalVitalDetails(benPhysicalVitalDetail);

		}

		return 1;
	}

	/**
	 * 
	 * @param requestOBJ
	 * @return success or failure flag for General OPD History updating by Doctor
	 * @throws ParseException
	 */
	@Transactional(rollbackFor = Exception.class)
	public int updateBenHistoryDetails(JsonObject historyOBJ) throws IEMRException, ParseException {

		// update immunization history
		if (historyOBJ != null && historyOBJ.has("immunizationHistory")
				&& !historyOBJ.get("immunizationHistory").isJsonNull()) {

			JsonObject immunizationHistory = historyOBJ.getAsJsonObject("immunizationHistory");
			if (immunizationHistory.get("immunizationList") != null
					&& immunizationHistory.getAsJsonArray("immunizationList").size() > 0) {
				WrapperImmunizationHistory wrapperImmunizationHistory = InputMapper.gson()
						.fromJson(historyOBJ.get("immunizationHistory"), WrapperImmunizationHistory.class);
				commonNurseServiceImpl.updateChildImmunizationDetail(wrapperImmunizationHistory);
			}
		}

		// update infant birth details
		// infant birth details save
		if (historyOBJ.has("infantBirthDetails") && !historyOBJ.get("infantBirthDetails").isJsonNull()) {
			InfantBirthDetails infantBirthDetails = InputMapper.gson().fromJson(historyOBJ.get("infantBirthDetails"),
					InfantBirthDetails.class);

			if (infantBirthDetails != null) {
				if (infantBirthDetails.getId() == null) {
					infantBirthDetails.setCreatedBy(infantBirthDetails.getModifiedBy());
					infantBirthDetails.setProcessed("N");
				} else
					infantBirthDetails.setProcessed("P");
				if (infantBirthDetails.getCongenitalAnomaliesList() != null
						&& infantBirthDetails.getCongenitalAnomaliesList().length > 0) {
					StringBuffer sb = new StringBuffer();
					for (String s : infantBirthDetails.getCongenitalAnomaliesList()) {
						sb.append(s).append("||");
					}
					if (sb.length() >= 2)
						infantBirthDetails.setCongenitalAnomalies(sb.substring(0, sb.length() - 2));
				} else
					infantBirthDetails.setCongenitalAnomalies("");

				infantBirthDetailsRepo.save(infantBirthDetails);
			}
		}

		return 1;
	}

	/// update immunization service data
	/**
	 * 
	 * @param requestOBJ
	 * @return success or failure flag for immunization data updating
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateBenImmunServiceDetailsNNI(JsonObject immunServiceOBJ) throws Exception {

		// immunization service
		if (immunServiceOBJ.has("immunizationServices") && !immunServiceOBJ.get("immunizationServices").isJsonNull()) {
			ImmunizationServices is = InputMapper.gson().fromJson(immunServiceOBJ.get("immunizationServices"),
					ImmunizationServices.class);

			if (is != null && is.getBeneficiaryRegID() != null && is.getVisitCode() != null)
				immunizationServicesRepo.updateDeletedFlag(true, is.getBeneficiaryRegID(), is.getVisitCode());

			List<ImmunizationServices> isList = new ArrayList<ImmunizationServices>();
			ImmunizationServices obj;
			if (is != null && is.getVaccines() != null && is.getVaccines().size() > 0) {
				// for each vaccine, create a new db obj
				for (Vaccines v : is.getVaccines()) {
					if (v.getVaccineName() != null) {
						obj = new ImmunizationServices();

						obj.setBeneficiaryRegID(is.getBeneficiaryRegID());
						obj.setVanID(is.getVanID());
						obj.setProviderServiceMapID(is.getProviderServiceMapID());
						obj.setParkingPlaceID(is.getParkingPlaceID());

						if (is.getModifiedBy() != null) {
							obj.setModifiedBy(is.getModifiedBy());
							obj.setCreatedBy(is.getModifiedBy());
							obj.setProcessed("N");
						}

						if (is.getCreatedBy() != null)
							obj.setCreatedBy(is.getCreatedBy());

						obj.setDateOfVisit(is.getDateOfVisit());
						obj.setImmunizationServicesType(is.getImmunizationServicesType());
						obj.setImmunizationServicesTypeID(is.getImmunizationServicesTypeID());
						obj.setCurrentImmunizationService(is.getCurrentImmunizationService());
						obj.setCurrentImmunizationServiceID(is.getCurrentImmunizationServiceID());

						obj.setVaccineName(v.getVaccineName());
						obj.setVaccineDose(v.getVaccineDose());
						obj.setRoute(v.getRoute());
						obj.setBatchNo(v.getBatchNo());
						obj.setSiteOfInjection(v.getSiteOfInjection());

						obj.setVisitCode(is.getVisitCode());

						isList.add(obj);
					}
				}

				if (isList.size() > 0)
					immunizationServicesRepo.save(isList);
			}
		}

		return 1;
	}

	// doc update
	// update doctor data
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long updateDoctorDataNNI(JsonObject requestOBJ, String Authorization) throws Exception {
		Long updateSuccessFlag = null;
		Long prescriptionID = null;

		if (requestOBJ != null) {
			TeleconsultationRequestOBJ tcRequestOBJ = null;

			CommonUtilityClass commonUtilityClass = InputMapper.gson().fromJson(requestOBJ, CommonUtilityClass.class);

			tcRequestOBJ = commonServiceImpl.createTcRequest(requestOBJ, commonUtilityClass, Authorization);

			JsonArray testList = null;
			JsonArray drugList = null;

			Boolean isTestPrescribed = false;
			Boolean isMedicinePrescribed = false;

			// checking if test is prescribed
			if (requestOBJ.has("investigation") && !requestOBJ.get("investigation").isJsonNull()
					&& requestOBJ.get("investigation") != null) {
				testList = requestOBJ.getAsJsonObject("investigation").getAsJsonArray("laboratoryList");
				if (testList != null && !testList.isJsonNull() && testList.size() > 0)
					isTestPrescribed = true;
			}

			// checking if medicine is prescribed
			if (requestOBJ.has("prescription") && !requestOBJ.get("prescription").isJsonNull()
					&& requestOBJ.get("prescription") != null) {
				drugList = requestOBJ.getAsJsonArray("prescription");
				if (drugList != null && !drugList.isJsonNull() && drugList.size() > 0) {
					isMedicinePrescribed = true;
				}
			}

			// update findings
			if (requestOBJ.has("findings") && !requestOBJ.get("findings").isJsonNull()) {

				WrapperAncFindings wrapperAncFindings = InputMapper.gson().fromJson(requestOBJ.get("findings"),
						WrapperAncFindings.class);
				commonDoctorServiceImpl.updateDocFindings(wrapperAncFindings);

			}

			// creating prescription OBJ
			PrescriptionDetail prescriptionDetail = null;
			if (requestOBJ.has("diagnosis") && !requestOBJ.get("diagnosis").isJsonNull()) {
				prescriptionDetail = InputMapper.gson().fromJson(requestOBJ.get("diagnosis"), PrescriptionDetail.class);
			}

			// generating WrapperBenInvestigationANC OBJ
			WrapperBenInvestigationANC wrapperBenInvestigationANCTMP = InputMapper.gson()
					.fromJson(requestOBJ.get("investigation"), WrapperBenInvestigationANC.class);

			if (prescriptionDetail != null) {
				prescriptionDetail.setExternalInvestigation(wrapperBenInvestigationANCTMP.getExternalInvestigations());
				prescriptionID = prescriptionDetail.getPrescriptionID();
			}

			if (commonUtilityClass.getTreatmentsOnSideEffects() != null)
				prescriptionDetail.setTreatmentsOnSideEffects(commonUtilityClass.getTreatmentsOnSideEffects());

			if (requestOBJ.has("counsellingProvidedList") && !requestOBJ.get("counsellingProvidedList").isJsonNull()
					&& requestOBJ.get("counsellingProvidedList") != null) {

				PrescriptionDetail tempPrescription = InputMapper.gson().fromJson(requestOBJ, PrescriptionDetail.class);

				if (tempPrescription != null && tempPrescription.getCounsellingProvidedList() != null
						&& tempPrescription.getCounsellingProvidedList().length > 0) {
					StringBuffer sb = new StringBuffer();
					for (String s : tempPrescription.getCounsellingProvidedList()) {
						sb.append(s).append("||");
					}
					if (sb.length() >= 2)
						prescriptionDetail.setCounsellingProvided(sb.substring(0, sb.length() - 2));

				} else
					prescriptionDetail.setCounsellingProvided("");

			}

			// update prescription
			int p = commonNurseServiceImpl.updatePrescription(prescriptionDetail);

			// update followUpForImmunization
			if (requestOBJ.has("followUpForImmunization") && !requestOBJ.get("followUpForImmunization").isJsonNull()
					&& requestOBJ.get("followUpForImmunization") != null) {
				FollowUpForImmunization followUpForImmunization = InputMapper.gson()
						.fromJson(requestOBJ.get("followUpForImmunization"), FollowUpForImmunization.class);

				if (followUpForImmunization != null) {
					if (followUpForImmunization.getProcessed() == null)
						followUpForImmunization.setProcessed("N");
					if (followUpForImmunization.getId() == null && followUpForImmunization.getCreatedBy() == null
							&& followUpForImmunization.getModifiedBy() != null) {
						followUpForImmunization.setCreatedBy(followUpForImmunization.getModifiedBy());
					}

					followUpForImmunizationRepo.save(followUpForImmunization);
				}
			}

			// save prescribed lab test
			if (isTestPrescribed == true) {
				WrapperBenInvestigationANC wrapperBenInvestigationANC = InputMapper.gson()
						.fromJson(requestOBJ.get("investigation"), WrapperBenInvestigationANC.class);

				if (wrapperBenInvestigationANC != null) {
					wrapperBenInvestigationANC.setPrescriptionID(prescriptionID);
					commonNurseServiceImpl.saveBenInvestigation(wrapperBenInvestigationANC);
				}
			}

			// save prescribed medicine
			if (isMedicinePrescribed == true) {
				PrescribedDrugDetail[] prescribedDrugDetail = InputMapper.gson()
						.fromJson(requestOBJ.get("prescription"), PrescribedDrugDetail[].class);
				List<PrescribedDrugDetail> prescribedDrugDetailList = Arrays.asList(prescribedDrugDetail);

				if (prescribedDrugDetailList.size() > 0) {
					for (PrescribedDrugDetail tmpObj : prescribedDrugDetailList) {
						tmpObj.setPrescriptionID(prescriptionID);
						tmpObj.setBeneficiaryRegID(commonUtilityClass.getBeneficiaryRegID());
						tmpObj.setBenVisitID(commonUtilityClass.getBenVisitID());
						tmpObj.setVisitCode(commonUtilityClass.getVisitCode());
						tmpObj.setProviderServiceMapID(commonUtilityClass.getProviderServiceMapID());
					}
					Integer r = commonNurseServiceImpl.saveBenPrescribedDrugsList(prescribedDrugDetailList);

				}

			}

			// call method to update beneficiary flow table
			if (prescriptionID != null)
				commonUtilityClass.setPrescriptionID(prescriptionID);

			commonUtilityClass.setVisitCategoryID(6);
			commonUtilityClass.setAuthorization(Authorization);

			int i = commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(commonUtilityClass, isTestPrescribed,
					isMedicinePrescribed, tcRequestOBJ);

			if (i > 0)
				updateSuccessFlag = (long) 1;
			else
				throw new RuntimeException("Error occurred while saving data. Beneficiary status update failed");

			if (i > 0 && tcRequestOBJ != null && tcRequestOBJ.getWalkIn() == false) {
				int k = sMSGatewayServiceImpl.smsSenderGateway("schedule", commonUtilityClass.getBeneficiaryRegID(),
						tcRequestOBJ.getSpecializationID(), tcRequestOBJ.getTmRequestID(), null,
						commonUtilityClass.getCreatedBy(),
						tcRequestOBJ.getAllocationDate() != null ? String.valueOf(tcRequestOBJ.getAllocationDate())
								: "",
						null, Authorization);
			}

		}
		return updateSuccessFlag;
	}

}
