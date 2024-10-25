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

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.iemr.hwc.data.anc.BenAdherence;
import com.iemr.hwc.data.anc.BenAllergyHistory;
import com.iemr.hwc.data.anc.BenChildDevelopmentHistory;
import com.iemr.hwc.data.anc.BenFamilyHistory;
import com.iemr.hwc.data.anc.BenMedHistory;
import com.iemr.hwc.data.anc.BenMedicationHistory;
import com.iemr.hwc.data.anc.BenMenstrualDetails;
import com.iemr.hwc.data.anc.BenPersonalHabit;
import com.iemr.hwc.data.anc.BencomrbidityCondDetails;
import com.iemr.hwc.data.anc.ChildFeedingDetails;
import com.iemr.hwc.data.anc.ChildOptionalVaccineDetail;
import com.iemr.hwc.data.anc.ChildVaccineDetail1;
import com.iemr.hwc.data.anc.FemaleObstetricHistory;
import com.iemr.hwc.data.anc.OralExamination;
import com.iemr.hwc.data.anc.PerinatalHistory;
import com.iemr.hwc.data.anc.PhyGeneralExamination;
import com.iemr.hwc.data.anc.PhyHeadToToeExamination;
import com.iemr.hwc.data.anc.SysCardiovascularExamination;
import com.iemr.hwc.data.anc.SysCentralNervousExamination;
import com.iemr.hwc.data.anc.SysGastrointestinalExamination;
import com.iemr.hwc.data.anc.SysGenitourinarySystemExamination;
import com.iemr.hwc.data.anc.SysMusculoskeletalSystemExamination;
import com.iemr.hwc.data.anc.SysRespiratoryExamination;
import com.iemr.hwc.data.anc.WrapperBenInvestigationANC;
import com.iemr.hwc.data.anc.WrapperChildOptionalVaccineDetail;
import com.iemr.hwc.data.anc.WrapperComorbidCondDetails;
import com.iemr.hwc.data.anc.WrapperFemaleObstetricHistory;
import com.iemr.hwc.data.anc.WrapperImmunizationHistory;
import com.iemr.hwc.data.anc.WrapperMedicationHistory;
import com.iemr.hwc.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.hwc.data.bmi.BmiCalculation;
import com.iemr.hwc.data.doctor.BenReferDetails;
import com.iemr.hwc.data.doctor.ProviderSpecificRequest;
import com.iemr.hwc.data.ncdScreening.IDRSData;
import com.iemr.hwc.data.ncdScreening.PhysicalActivityType;
import com.iemr.hwc.data.nurse.BenAnthropometryDetail;
import com.iemr.hwc.data.nurse.BenCancerVitalDetail;
import com.iemr.hwc.data.nurse.BenPhysicalVitalDetail;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.nurse.CDSS;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.quickConsultation.BenChiefComplaint;
import com.iemr.hwc.data.quickConsultation.LabTestOrderDetail;
import com.iemr.hwc.data.quickConsultation.PrescribedDrugDetail;
import com.iemr.hwc.data.quickConsultation.PrescriptionDetail;
import com.iemr.hwc.data.registrar.WrapperRegWorklist;
import com.iemr.hwc.data.snomedct.SCTDescription;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.bmiCalculation.BMICalculationRepo;
import com.iemr.hwc.repo.doctor.BenReferDetailsRepo;
import com.iemr.hwc.repo.nurse.BenAnthropometryRepo;
import com.iemr.hwc.repo.nurse.BenCancerVitalDetailRepo;
import com.iemr.hwc.repo.nurse.BenPhysicalVitalRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.nurse.CDSSRepo;
import com.iemr.hwc.repo.nurse.anc.BenAdherenceRepo;
import com.iemr.hwc.repo.nurse.anc.BenAllergyHistoryRepo;
import com.iemr.hwc.repo.nurse.anc.BenChildDevelopmentHistoryRepo;
import com.iemr.hwc.repo.nurse.anc.BenFamilyHistoryRepo;
import com.iemr.hwc.repo.nurse.anc.BenMedHistoryRepo;
import com.iemr.hwc.repo.nurse.anc.BenMedicationHistoryRepo;
import com.iemr.hwc.repo.nurse.anc.BenMenstrualDetailsRepo;
import com.iemr.hwc.repo.nurse.anc.BenPersonalHabitRepo;
import com.iemr.hwc.repo.nurse.anc.BencomrbidityCondRepo;
import com.iemr.hwc.repo.nurse.anc.ChildFeedingDetailsRepo;
import com.iemr.hwc.repo.nurse.anc.ChildOptionalVaccineDetailRepo;
import com.iemr.hwc.repo.nurse.anc.ChildVaccineDetail1Repo;
import com.iemr.hwc.repo.nurse.anc.FemaleObstetricHistoryRepo;
import com.iemr.hwc.repo.nurse.anc.OralExaminationRepo;
import com.iemr.hwc.repo.nurse.anc.PerinatalHistoryRepo;
import com.iemr.hwc.repo.nurse.anc.PhyGeneralExaminationRepo;
import com.iemr.hwc.repo.nurse.anc.PhyHeadToToeExaminationRepo;
import com.iemr.hwc.repo.nurse.anc.SysCardiovascularExaminationRepo;
import com.iemr.hwc.repo.nurse.anc.SysCentralNervousExaminationRepo;
import com.iemr.hwc.repo.nurse.anc.SysGastrointestinalExaminationRepo;
import com.iemr.hwc.repo.nurse.anc.SysGenitourinarySystemExaminationRepo;
import com.iemr.hwc.repo.nurse.anc.SysMusculoskeletalSystemExaminationRepo;
import com.iemr.hwc.repo.nurse.anc.SysRespiratoryExaminationRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.IDRSDataRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.PhysicalActivityTypeRepo;
import com.iemr.hwc.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.hwc.repo.quickConsultation.LabTestOrderDetailRepo;
import com.iemr.hwc.repo.quickConsultation.PrescribedDrugDetailRepo;
import com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.hwc.repo.registrar.RegistrarRepoBenData;
import com.iemr.hwc.repo.registrar.ReistrarRepoBenSearch;
import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.mapper.InputMapper;

@Service
@PropertySource("classpath:application.properties")
public class CommonNurseServiceImpl implements CommonNurseService {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Value("${nurseWL}")
	private Integer nurseWL;
	@Value("${nurseTCWL}")
	private Integer nurseTCWL;
	@Value("${pharmaWL}")
	private Integer pharmaWL;
	@Value("${labWL}")
	private Integer labWL;
	@Value("${radioWL}")
	private Integer radioWL;
	@Value("${oncoWL}")
	private Integer oncoWL;
	@Value("${TMReferredWL}")
	private Integer TMReferredWL;
	
	private BenVisitDetailRepo benVisitDetailRepo;
	
	private BenChiefComplaintRepo benChiefComplaintRepo;
	private BenMedHistoryRepo benMedHistoryRepo;
	private BencomrbidityCondRepo bencomrbidityCondRepo;
	private BenMedicationHistoryRepo benMedicationHistoryRepo;
	private FemaleObstetricHistoryRepo femaleObstetricHistoryRepo;
	private BenMenstrualDetailsRepo benMenstrualDetailsRepo;
	private BenFamilyHistoryRepo benFamilyHistoryRepo;
	private BenPersonalHabitRepo benPersonalHabitRepo;
	private BenAllergyHistoryRepo benAllergyHistoryRepo;
	private ChildOptionalVaccineDetailRepo childOptionalVaccineDetailRepo;
	private ChildVaccineDetail1Repo childVaccineDetail1Repo;
	private BenAnthropometryRepo benAnthropometryRepo;
	private BenPhysicalVitalRepo benPhysicalVitalRepo;
	private PhyGeneralExaminationRepo phyGeneralExaminationRepo;
	private PhyHeadToToeExaminationRepo phyHeadToToeExaminationRepo;
	private SysGastrointestinalExaminationRepo sysGastrointestinalExaminationRepo;
	private SysCardiovascularExaminationRepo sysCardiovascularExaminationRepo;
	private SysRespiratoryExaminationRepo sysRespiratoryExaminationRepo;
	private SysCentralNervousExaminationRepo sysCentralNervousExaminationRepo;
	private SysMusculoskeletalSystemExaminationRepo sysMusculoskeletalSystemExaminationRepo;
	private SysGenitourinarySystemExaminationRepo sysGenitourinarySystemExaminationRepo;
	@Autowired
	private OralExaminationRepo oralExaminationRepo;
	private RegistrarRepoBenData registrarRepoBenData;
	private PrescriptionDetailRepo prescriptionDetailRepo;
	private LabTestOrderDetailRepo labTestOrderDetailRepo;
	private PrescribedDrugDetailRepo prescribedDrugDetailRepo;
	private ReistrarRepoBenSearch reistrarRepoBenSearch;
	private BenAdherenceRepo benAdherenceRepo;
	private BenChildDevelopmentHistoryRepo benChildDevelopmentHistoryRepo;
	private ChildFeedingDetailsRepo childFeedingDetailsRepo;
	private PerinatalHistoryRepo perinatalHistoryRepo;
	private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;
	private PhysicalActivityTypeRepo physicalActivityTypeRepo;
	private IDRSDataRepo iDRSDataRepo;
	private BenCancerVitalDetailRepo benCancerVitalDetailRepo;
	
	private CommonDoctorServiceImpl commonDoctorServiceImpl;
	private BenReferDetailsRepo benReferDetailsRepo;
	@Autowired
	private CDSSRepo cdssRepo;

	@Autowired
	public void setCommonDoctorServiceImpl(CommonDoctorServiceImpl commonDoctorServiceImpl) {
		this.commonDoctorServiceImpl = commonDoctorServiceImpl;
	}

	@Autowired
	public void setBenCancerVitalDetailRepo(BenCancerVitalDetailRepo benCancerVitalDetailRepo) {
		this.benCancerVitalDetailRepo = benCancerVitalDetailRepo;
	}

	@Autowired
	public void setBeneficiaryFlowStatusRepo(BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo) {
		this.beneficiaryFlowStatusRepo = beneficiaryFlowStatusRepo;
	}

	@Autowired
	public void setBenChildDevelopmentHistoryRepo(BenChildDevelopmentHistoryRepo benChildDevelopmentHistoryRepo) {
		this.benChildDevelopmentHistoryRepo = benChildDevelopmentHistoryRepo;
	}

	@Autowired
	public void setChildFeedingDetailsRepo(ChildFeedingDetailsRepo childFeedingDetailsRepo) {
		this.childFeedingDetailsRepo = childFeedingDetailsRepo;
	}

	@Autowired
	public void setPerinatalHistoryRepo(PerinatalHistoryRepo perinatalHistoryRepo) {
		this.perinatalHistoryRepo = perinatalHistoryRepo;
	}

	@Autowired
	public void setBenAdherenceRepo(BenAdherenceRepo benAdherenceRepo) {
		this.benAdherenceRepo = benAdherenceRepo;
	}

	@Autowired
	public void setReistrarRepoBenSearch(ReistrarRepoBenSearch reistrarRepoBenSearch) {
		this.reistrarRepoBenSearch = reistrarRepoBenSearch;
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
	public void setPrescriptionDetailRepo(PrescriptionDetailRepo prescriptionDetailRepo) {
		this.prescriptionDetailRepo = prescriptionDetailRepo;
	}

	@Autowired
	public void setPhyGeneralExaminationRepo(PhyGeneralExaminationRepo phyGeneralExaminationRepo) {
		this.phyGeneralExaminationRepo = phyGeneralExaminationRepo;
	}

	@Autowired
	public void setRegistrarRepoBenData(RegistrarRepoBenData registrarRepoBenData) {
		this.registrarRepoBenData = registrarRepoBenData;
	}

	@Autowired
	public void setSysGenitourinarySystemExaminationRepo(
			SysGenitourinarySystemExaminationRepo sysGenitourinarySystemExaminationRepo) {
		this.sysGenitourinarySystemExaminationRepo = sysGenitourinarySystemExaminationRepo;
	}

	@Autowired
	public void setSysMusculoskeletalSystemExaminationRepo(
			SysMusculoskeletalSystemExaminationRepo sysMusculoskeletalSystemExaminationRepo) {
		this.sysMusculoskeletalSystemExaminationRepo = sysMusculoskeletalSystemExaminationRepo;
	}

	@Autowired
	public void setSysCentralNervousExaminationRepo(SysCentralNervousExaminationRepo sysCentralNervousExaminationRepo) {
		this.sysCentralNervousExaminationRepo = sysCentralNervousExaminationRepo;
	}

	@Autowired
	public void setSysRespiratoryExaminationRepo(SysRespiratoryExaminationRepo sysRespiratoryExaminationRepo) {
		this.sysRespiratoryExaminationRepo = sysRespiratoryExaminationRepo;
	}

	@Autowired
	public void setSysCardiovascularExaminationRepo(SysCardiovascularExaminationRepo sysCardiovascularExaminationRepo) {
		this.sysCardiovascularExaminationRepo = sysCardiovascularExaminationRepo;
	}

	@Autowired
	public void setSysGastrointestinalExaminationRepo(
			SysGastrointestinalExaminationRepo sysGastrointestinalExaminationRepo) {
		this.sysGastrointestinalExaminationRepo = sysGastrointestinalExaminationRepo;
	}

	@Autowired
	public void setPhyHeadToToeExaminationRepo(PhyHeadToToeExaminationRepo phyHeadToToeExaminationRepo) {
		this.phyHeadToToeExaminationRepo = phyHeadToToeExaminationRepo;
	}

	@Autowired
	public void setBenAnthropometryRepo(BenAnthropometryRepo benAnthropometryRepo) {
		this.benAnthropometryRepo = benAnthropometryRepo;
	}

	@Autowired
	public void setBenPhysicalVitalRepo(BenPhysicalVitalRepo benPhysicalVitalRepo) {
		this.benPhysicalVitalRepo = benPhysicalVitalRepo;
	}

	@Autowired
	public void setChildVaccineDetail1Repo(ChildVaccineDetail1Repo childVaccineDetail1Repo) {
		this.childVaccineDetail1Repo = childVaccineDetail1Repo;
	}

	@Autowired
	public void setChildOptionalVaccineDetailRepo(ChildOptionalVaccineDetailRepo childOptionalVaccineDetailRepo) {
		this.childOptionalVaccineDetailRepo = childOptionalVaccineDetailRepo;
	}

	@Autowired
	public void setBenAllergyHistoryRepo(BenAllergyHistoryRepo benAllergyHistoryRepo) {
		this.benAllergyHistoryRepo = benAllergyHistoryRepo;
	}

	@Autowired
	public void setBenPersonalHabitRepo(BenPersonalHabitRepo benPersonalHabitRepo) {
		this.benPersonalHabitRepo = benPersonalHabitRepo;
	}

	@Autowired
	public void setBenFamilyHistoryRepo(BenFamilyHistoryRepo benFamilyHistoryRepo) {
		this.benFamilyHistoryRepo = benFamilyHistoryRepo;
	}

	@Autowired
	public void setBenMenstrualDetailsRepo(BenMenstrualDetailsRepo benMenstrualDetailsRepo) {
		this.benMenstrualDetailsRepo = benMenstrualDetailsRepo;
	}

	@Autowired
	public void setFemaleObstetricHistoryRepo(FemaleObstetricHistoryRepo femaleObstetricHistoryRepo) {
		this.femaleObstetricHistoryRepo = femaleObstetricHistoryRepo;
	}

	@Autowired
	public void setBenMedicationHistoryRepo(BenMedicationHistoryRepo benMedicationHistoryRepo) {
		this.benMedicationHistoryRepo = benMedicationHistoryRepo;
	}

	@Autowired
	public void setBencomrbidityCondRepo(BencomrbidityCondRepo bencomrbidityCondRepo) {
		this.bencomrbidityCondRepo = bencomrbidityCondRepo;
	}

	@Autowired
	public void setBenMedHistoryRepo(BenMedHistoryRepo benMedHistoryRepo) {
		this.benMedHistoryRepo = benMedHistoryRepo;
	}

	@Autowired
	public void setBenChiefComplaintRepo(BenChiefComplaintRepo benChiefComplaintRepo) {
		this.benChiefComplaintRepo = benChiefComplaintRepo;
	}

	@Autowired
	public void setPhysicalActivityTypeRepo(PhysicalActivityTypeRepo physicalActivityTypeRepo) {
		this.physicalActivityTypeRepo = physicalActivityTypeRepo;
	}

	@Autowired
	public void setIDRSDataRepo(IDRSDataRepo iDRSDataRepo) {
		this.iDRSDataRepo = iDRSDataRepo;
	}

	@Autowired
	public void setBenVisitDetailRepo(BenVisitDetailRepo benVisitDetailRepo) {
		this.benVisitDetailRepo = benVisitDetailRepo;
	}

	@Autowired
	private IDRSDataRepo iDrsDataRepo;
	@Autowired
	private PhysicalActivityTypeRepo physicalActivityaRepo;

	@Autowired
	private BMICalculationRepo bmiCalculationRepo;

	public Integer updateBeneficiaryStatus(Character c, Long benRegID) {
		Integer i = registrarRepoBenData.updateBenFlowStatus(c, benRegID);
		return i;
	}

	/**
	 * Save beneficiary visit details data and return beneficiary visit ID.
	 * 
	 * @param beneficiaryVisitDetail
	 * @return
	 */
	@Override
	public Long saveBeneficiaryVisitDetails(BeneficiaryVisitDetail beneficiaryVisitDetail) {
		BeneficiaryVisitDetail response = null;

		// get the total no of visit for the beneficiary (visit count)
		Short benVisitCount = benVisitDetailRepo
				.getVisitCountForBeneficiary(beneficiaryVisitDetail.getBeneficiaryRegID());

		if (benVisitCount != null && benVisitCount >= 0) {
			benVisitCount = (short) (benVisitCount + 1);
		} else {
			benVisitCount = 1;
		}
		beneficiaryVisitDetail.setVisitNo(benVisitCount);

		// file id, comma seperated
		Integer[] docIdArr = beneficiaryVisitDetail.getFileIDs();
		StringBuilder sb = new StringBuilder();
		if (docIdArr != null && docIdArr.length > 0) {
			for (Integer i : docIdArr) {
				sb.append(i + ",");
			}
		}
		beneficiaryVisitDetail.setReportFilePath(sb.toString());

		if (beneficiaryVisitDetail.getFollowUpForFpMethod() != null
				&& beneficiaryVisitDetail.getFollowUpForFpMethod().length > 0) {
			StringBuffer sb1 = new StringBuffer();
			for (String s : beneficiaryVisitDetail.getFollowUpForFpMethod()) {
				sb1.append(s).append("||");
			}
			if (sb1.length() >= 2)
				beneficiaryVisitDetail.setFpMethodFollowup(sb1.substring(0, sb1.length() - 2));

		}

		if (beneficiaryVisitDetail.getSideEffects() != null && beneficiaryVisitDetail.getSideEffects().length > 0) {
			StringBuffer sb1 = new StringBuffer();
			for (String s : beneficiaryVisitDetail.getSideEffects()) {
				sb1.append(s).append("||");
			}
			if (sb1.length() >= 2)
				beneficiaryVisitDetail.setFpSideeffects(sb1.substring(0, sb1.length() - 2));

		}

		response = benVisitDetailRepo.save(beneficiaryVisitDetail);

		if (response != null) {

			// Long visitCode = updateVisitCode(response, 10);
			return response.getBenVisitID();
		} else
			return null;

	}

	@Override
	public Long saveBeneficiaryVisitDetails(BeneficiaryVisitDetail beneficiaryVisitDetail, Integer sessionId) {
		BeneficiaryVisitDetail response = null;

		// get the total no of visit for the beneficiary (visit count)
		Short benVisitCount = benVisitDetailRepo
				.getVisitCountForBeneficiary(beneficiaryVisitDetail.getBeneficiaryRegID());

		if (benVisitCount != null && benVisitCount >= 0) {
			benVisitCount = (short) (benVisitCount + 1);
		} else {
			benVisitCount = 1;
		}
		beneficiaryVisitDetail.setVisitNo(benVisitCount);

		// file id, comma seperated
		Integer[] docIdArr = beneficiaryVisitDetail.getFileIDs();
		StringBuilder sb = new StringBuilder();
		if (docIdArr != null && docIdArr.length > 0) {
			for (Integer i : docIdArr) {
				sb.append(i + ",");
			}
		}
		beneficiaryVisitDetail.setReportFilePath(sb.toString());

		if (beneficiaryVisitDetail.getFollowUpForFpMethod() != null
				&& beneficiaryVisitDetail.getFollowUpForFpMethod().length > 0) {
			StringBuffer sb1 = new StringBuffer();
			for (String s : beneficiaryVisitDetail.getFollowUpForFpMethod()) {
				sb1.append(s).append("||");
			}
			if (sb1.length() >= 2)
				beneficiaryVisitDetail.setFpMethodFollowup(sb1.substring(0, sb1.length() - 2));

		}

		if (beneficiaryVisitDetail.getSideEffects() != null && beneficiaryVisitDetail.getSideEffects().length > 0) {
			StringBuffer sb1 = new StringBuffer();
			for (String s : beneficiaryVisitDetail.getSideEffects()) {
				sb1.append(s).append("||");
			}
			if (sb1.length() >= 2)
				beneficiaryVisitDetail.setFpSideeffects(sb1.substring(0, sb1.length() - 2));

		}

		response = benVisitDetailRepo.save(beneficiaryVisitDetail);

		if (response != null) {
			Long benVisitId = response.getBenVisitID();
			Integer vanId = response.getVanID();
			Integer sessionIdObj = sessionId;
			Long visitCode = generateVisitCode(benVisitId, vanId, sessionIdObj);
			CDSS cdss = new CDSS();
			cdss.setVisitCode(visitCode);
			cdss.setBenVisitID(benVisitId);
			if (beneficiaryVisitDetail.getBeneficiaryRegID() != null)
				cdss.setBeneficiaryRegID(beneficiaryVisitDetail.getBeneficiaryRegID());
			if (beneficiaryVisitDetail.getProviderServiceMapID() != null)
				cdss.setProviderServiceMapID(beneficiaryVisitDetail.getProviderServiceMapID());
			if (beneficiaryVisitDetail.getCreatedBy() != null)
				cdss.setCreatedBy(beneficiaryVisitDetail.getCreatedBy());
			if (beneficiaryVisitDetail.getVanID() != null)
				cdss.setVanID(beneficiaryVisitDetail.getVanID());
			if (beneficiaryVisitDetail.getParkingPlaceID() != null)
				cdss.setParkingPlaceID(beneficiaryVisitDetail.getParkingPlaceID());
			if (beneficiaryVisitDetail.getPresentChiefComplaint() != null)
				cdss.setPresentChiefComplaint(beneficiaryVisitDetail.getPresentChiefComplaint());
			if (beneficiaryVisitDetail.getPresentChiefComplaintID() != null)
				cdss.setPresentChiefComplaintID(beneficiaryVisitDetail.getPresentChiefComplaintID());
			if (beneficiaryVisitDetail.getSelectedDiagnosis() != null)
				cdss.setSelectedDiagnosis(beneficiaryVisitDetail.getSelectedDiagnosis());
			if (beneficiaryVisitDetail.getSelectedDiagnosisID() != null)
				cdss.setSelectedDiagnosisID(beneficiaryVisitDetail.getSelectedDiagnosisID());
			if (beneficiaryVisitDetail.getRecommendedAction() != null)
				cdss.setRecommendedAction(beneficiaryVisitDetail.getRecommendedAction());
			if (beneficiaryVisitDetail.getRecommendedActionPc() != null)
				cdss.setRecommendedActionPc(beneficiaryVisitDetail.getRecommendedActionPc());
			if (beneficiaryVisitDetail.getRemarks() != null)
				cdss.setRemarks(beneficiaryVisitDetail.getRemarks());
			if (beneficiaryVisitDetail.getRemarksPc() != null)
				cdss.setRemarksPc(beneficiaryVisitDetail.getRemarksPc());
			if (beneficiaryVisitDetail.getAlgorithm() != null)
				cdss.setAlgorithm(beneficiaryVisitDetail.getAlgorithm());
			if (beneficiaryVisitDetail.getAlgorithmPc() != null)
				cdss.setAlgorithmPc(beneficiaryVisitDetail.getAlgorithmPc());
			if (beneficiaryVisitDetail.getDiseaseSummary() != null)
				cdss.setDiseaseSummary(beneficiaryVisitDetail.getDiseaseSummary());
			if (beneficiaryVisitDetail.getDiseaseSummaryID() != null)
				cdss.setDiseaseSummaryID(beneficiaryVisitDetail.getDiseaseSummaryID());
			if (beneficiaryVisitDetail.getAction() != null)
				cdss.setAction(beneficiaryVisitDetail.getAction());
			if (beneficiaryVisitDetail.getActionPc() != null)
				cdss.setActionPc(beneficiaryVisitDetail.getActionPc());
			if (beneficiaryVisitDetail.getActionId() != null)
				cdss.setActionId(beneficiaryVisitDetail.getActionId());
			if (beneficiaryVisitDetail.getActionIdPc() != null)
				cdss.setActionIdPc(beneficiaryVisitDetail.getActionIdPc());
			if (beneficiaryVisitDetail.getInformationGiven() != null)
				cdss.setInformationGiven(beneficiaryVisitDetail.getInformationGiven());

			cdssRepo.save(cdss);
			// Long visitCode = updateVisitCode(response, 10);
			return response.getBenVisitID();
		} else
			return null;

	}

	public int getMaxCurrentdate(Long beneficiaryRegID, String visitreason, String visitcategory) throws IEMRException {
		String maxDate = benVisitDetailRepo.getMaxCreatedDate(beneficiaryRegID, visitreason, visitcategory);

		int i = 0;
		if (maxDate != null) {
			try {
				DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String maxdateTrim = maxDate.substring(0, maxDate.indexOf("."));
				java.util.Date d = timeFormat.parse(maxdateTrim);
				Calendar cal = Calendar.getInstance();
				Calendar cal1 = Calendar.getInstance();
				cal.setTime(d);
				cal.add(Calendar.MINUTE, 10);
				i = cal.compareTo(cal1);

			} catch (ParseException e) {
				throw new IEMRException("Error while parseing created date :" + e.getMessage());
			}
		}
		return i;
	}

	public Long generateVisitCode(Long visitID, Integer vanID, Integer sessionID) {
		String visitCode = "";

		// van & session ID
		String vanIDString = "";
		int vanIdLength = (int) (Math.log10(vanID) + 1);

		for (int i = 0; i < 5 - vanIdLength; i++) {
			vanIDString += "0";
		}
		vanIDString += vanID;
		String visitIDString = "";
		int visitIdLength = (int) (Math.log10(visitID) + 1);
		for (int i = 0; i < 8 - visitIdLength; i++) {
			visitIDString += "0";
		}
		visitIDString += visitID;

		// Generating VISIT CODE
		// visitCode += sessionID + dayString + monthString + vanIDString +
		// visitIDString;
		// changed logic 14 digit visit code, removed day & month
		visitCode += sessionID + vanIDString + visitIDString;

		int i = benVisitDetailRepo.updateVisitCode(Long.valueOf(visitCode), visitID);
		if (i > 0)
			return Long.valueOf(visitCode);
		else
			return Long.valueOf(0);
	}

	@Deprecated
	public int updateVisitCodeInVisitDetailsTable(Long visitCode, Long visitID) {
		int i = benVisitDetailRepo.updateVisitCode(visitCode, visitID);
		return i;
	}

	/**
	 * Neeraj have created this for getting visit count of patient
	 * 
	 * @return
	 */
	public Short getBenVisitCount(Long benRegID) {
		Short benVisitCount = benVisitDetailRepo.getVisitCountForBeneficiary(benRegID);

		if (benVisitCount != null && benVisitCount >= 0) {
			benVisitCount = (short) (benVisitCount + 1);
		} else {
			benVisitCount = 1;
		}

		return benVisitCount;
	}

	public int updateBeneficiaryVisitDetails(BeneficiaryVisitDetail beneficiaryVisitDetail) {
		int response = 0;
		try {
			response = benVisitDetailRepo.updateBeneficiaryVisitDetail(beneficiaryVisitDetail.getVisitReasonID(),
					beneficiaryVisitDetail.getVisitReason(), beneficiaryVisitDetail.getVisitCategoryID(),
					beneficiaryVisitDetail.getVisitCategory(), beneficiaryVisitDetail.getPregnancyStatus(),
					beneficiaryVisitDetail.getrCHID(), beneficiaryVisitDetail.getHealthFacilityType(),
					beneficiaryVisitDetail.getHealthFacilityLocation(), beneficiaryVisitDetail.getModifiedBy(),
					beneficiaryVisitDetail.getBenVisitID());

		} catch (Exception e) {
			logger.error(e.getMessage());

		}
		return response;

	}

	public BeneficiaryVisitDetail getCSVisitDetails(Long benRegID, Long visitCode) {
		BeneficiaryVisitDetail benVisitDetailsOBJ = new BeneficiaryVisitDetail();
		benVisitDetailsOBJ = benVisitDetailRepo.getVisitDetails(benRegID, visitCode);

		// file id array from string
		Integer fileIds[];
		if (benVisitDetailsOBJ != null && benVisitDetailsOBJ.getReportFilePath() != null) {
			String fileIdsTemp[] = benVisitDetailsOBJ.getReportFilePath().split(",");
			fileIds = new Integer[fileIdsTemp.length];
			int pointer = 0;
			for (String str : fileIdsTemp) {
				if (str != null && str.trim().length() > 0) {
					fileIds[pointer] = Integer.parseInt(str.trim());
					pointer++;
				}
			}
		} else {
			fileIds = new Integer[0];
		}

		benVisitDetailsOBJ.setFileIDs(fileIds);

//		if (benVisitDetailsOBJ1 != null)
//			benVisitDetailsOBJ1.setFileIDs(fileIds);

		if (benVisitDetailsOBJ.getFpMethodFollowup() != null) {
			String[] fpMethodArr = benVisitDetailsOBJ.getFpMethodFollowup().split("\\|\\|");
			if (fpMethodArr != null && fpMethodArr.length > 0)
				benVisitDetailsOBJ.setFollowUpForFpMethod(fpMethodArr);
		}
		if (benVisitDetailsOBJ.getFpSideeffects() != null) {
			String[] fpSideEffectArr = benVisitDetailsOBJ.getFpSideeffects().split("\\|\\|");
			if (fpSideEffectArr != null && fpSideEffectArr.length > 0)
				benVisitDetailsOBJ.setSideEffects(fpSideEffectArr);
		}

		return benVisitDetailsOBJ;
	}

	public CDSS getCdssDetails(Long benRegID, Long visitCode) {
		CDSS cdssObj = new CDSS();
		cdssObj = cdssRepo.findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode);
		return cdssObj;
	}

	public int saveBenChiefComplaints(List<BenChiefComplaint> benChiefComplaintList) {
		int r = 0;
		List<BenChiefComplaint> benChiefComplaintListNew = new ArrayList<>();
		for (BenChiefComplaint obj : benChiefComplaintList) {
			if (obj.getChiefComplaintID() != null)
				benChiefComplaintListNew.add(obj);
		}
		if (benChiefComplaintListNew.size() > 0) {
			List<BenChiefComplaint> benChiefComplaintResultList = (List<BenChiefComplaint>) benChiefComplaintRepo
					.saveAll(benChiefComplaintListNew);
			if (benChiefComplaintListNew.size() == benChiefComplaintResultList.size())
				r = 1;
		} else {
			r = 1;
		}
		return r;
	}

	public Long saveBenPastHistory(BenMedHistory benMedHistory) {
		Long pastHistorySuccessFlag = null;
		ArrayList<BenMedHistory> benMedHistoryList = benMedHistory.getBenPastHistory();
		if (null != benMedHistoryList && benMedHistoryList.size() > 0) {
			ArrayList<BenMedHistory> res = (ArrayList<BenMedHistory>) benMedHistoryRepo.saveAll(benMedHistoryList);
			if (benMedHistoryList.size() == res.size()) {
				pastHistorySuccessFlag = new Long(1);
			}
		} else {
			pastHistorySuccessFlag = new Long(1);
		}
		return pastHistorySuccessFlag;
	}

	public Long saveBenComorbidConditions(WrapperComorbidCondDetails wrapperComorbidCondDetails) {
		Long comrbidSuccessFlag = null;
		ArrayList<BencomrbidityCondDetails> bencomrbidityCondDetailsList = wrapperComorbidCondDetails
				.getComrbidityConds();
		if (bencomrbidityCondDetailsList.size() > 0) {
			ArrayList<BencomrbidityCondDetails> res = (ArrayList<BencomrbidityCondDetails>) bencomrbidityCondRepo
					.saveAll(bencomrbidityCondDetailsList);
			if (bencomrbidityCondDetailsList.size() == res.size()) {
				comrbidSuccessFlag = res.get(0).getID();
			}
		} else {
			comrbidSuccessFlag = new Long(1);
		}
		return comrbidSuccessFlag;
	}

	public Long saveBenMedicationHistory(WrapperMedicationHistory wrapperMedicationHistory) {
		Long medicationSuccessFlag = null;
		ArrayList<BenMedicationHistory> benMedicationHistoryList = wrapperMedicationHistory
				.getBenMedicationHistoryDetails();
		if (benMedicationHistoryList.size() > 0) {
			ArrayList<BenMedicationHistory> res = (ArrayList<BenMedicationHistory>) benMedicationHistoryRepo
					.saveAll(benMedicationHistoryList);
			if (benMedicationHistoryList.size() == res.size()) {
				medicationSuccessFlag = res.get(0).getID();
			}
		} else {
			medicationSuccessFlag = new Long(1);
		}
		return medicationSuccessFlag;
	}

	public Long saveFemaleObstetricHistory(WrapperFemaleObstetricHistory wrapperFemaleObstetricHistory) {
		Long obstetricSuccessFlag = null;

		ArrayList<FemaleObstetricHistory> femaleObstetricHistorylist = getFemaleObstetricHistoryObj(
				wrapperFemaleObstetricHistory.getFemaleObstetricHistoryDetails());

		if (femaleObstetricHistorylist != null && femaleObstetricHistorylist.size() > 0) {

			ArrayList<FemaleObstetricHistory> res = (ArrayList<FemaleObstetricHistory>) femaleObstetricHistoryRepo
					.saveAll(femaleObstetricHistorylist);
			if (femaleObstetricHistorylist.size() == res.size()) {
				obstetricSuccessFlag = new Long(1);
			}
		} else {
			obstetricSuccessFlag = new Long(1);
		}
		return obstetricSuccessFlag;
	}

	private ArrayList<FemaleObstetricHistory> getFemaleObstetricHistoryObj(
			ArrayList<FemaleObstetricHistory> femaleObstetricHistorylist) {
		StringBuilder pregComplicationID;
		StringBuilder pregComplicationName;
		StringBuilder deliComplicationID;
		StringBuilder deliComplicationName;
		StringBuilder postpartumComplicationID;
		StringBuilder postpartumComplicationName;

		StringBuilder postAbortionComp;
		StringBuilder postAbortionCompValues;

//		StringBuilder typeOfAbortionID;
//		StringBuilder typeOfAbortionValue;
//
//		StringBuilder serviceFacilityID;
//		StringBuilder serviceFacilityValue;

		// iterate through pregnancy complication
		for (FemaleObstetricHistory obj : femaleObstetricHistorylist) {
			pregComplicationID = new StringBuilder();
			pregComplicationName = new StringBuilder();
			deliComplicationID = new StringBuilder();
			deliComplicationName = new StringBuilder();
			postpartumComplicationID = new StringBuilder();
			postpartumComplicationName = new StringBuilder();
			postAbortionComp = new StringBuilder();
			postAbortionCompValues = new StringBuilder();

			// check for pregnancy complication
			if (obj.getPregComplicationList() != null && obj.getPregComplicationList().size() > 0) {
				for (int i = 0; i < obj.getPregComplicationList().size(); i++) {
					if (i == (obj.getPregComplicationList().size() - 1)) {
						pregComplicationID.append(obj.getPregComplicationList().get(i).get("pregComplicationID"));
						pregComplicationName.append(obj.getPregComplicationList().get(i).get("pregComplicationType"));
					} else {
						pregComplicationID.append(obj.getPregComplicationList().get(i).get("pregComplicationID"))
								.append(",");
						pregComplicationName.append(obj.getPregComplicationList().get(i).get("pregComplicationType"))
								.append(",");
					}
				}
			}
			// check for delivery complication
			if (obj.getDeliveryComplicationList() != null && obj.getDeliveryComplicationList().size() > 0) {
				for (int i = 0; i < obj.getDeliveryComplicationList().size(); i++) {
					if (i == (obj.getDeliveryComplicationList().size() - 1)) {
						deliComplicationID
								.append(obj.getDeliveryComplicationList().get(i).get("deliveryComplicationID"));
						deliComplicationName
								.append(obj.getDeliveryComplicationList().get(i).get("deliveryComplicationType"));
					} else {
						deliComplicationID
								.append(obj.getDeliveryComplicationList().get(i).get("deliveryComplicationID"))
								.append(",");
						deliComplicationName
								.append(obj.getDeliveryComplicationList().get(i).get("deliveryComplicationType"))
								.append(",");
					}
				}
			}

			// check for postpartum complication
			if (obj.getPostpartumComplicationList() != null && obj.getPostpartumComplicationList().size() > 0) {
				for (int i = 0; i < obj.getPostpartumComplicationList().size(); i++) {
					if (i == (obj.getPostpartumComplicationList().size() - 1)) {
						postpartumComplicationID
								.append(obj.getPostpartumComplicationList().get(i).get("postpartumComplicationID"));
						postpartumComplicationName
								.append(obj.getPostpartumComplicationList().get(i).get("postpartumComplicationType"));
					} else {
						postpartumComplicationID
								.append(obj.getPostpartumComplicationList().get(i).get("postpartumComplicationID"))
								.append(",");
						postpartumComplicationName
								.append(obj.getPostpartumComplicationList().get(i).get("postpartumComplicationType"))
								.append(",");
					}
				}
			}

			if (obj.getPostAbortionComplication() != null && obj.getPostAbortionComplication().size() > 0) {
				int pointer = 1;
				for (Map<String, Object> postAbortionCompMap : obj.getPostAbortionComplication()) {
					if (pointer == obj.getPostAbortionComplication().size()) {
						postAbortionComp.append(
								String.valueOf(((Double) postAbortionCompMap.get("complicationID")).intValue()));
						postAbortionCompValues
								.append(String.valueOf((String) postAbortionCompMap.get("complicationValue")));

					} else {
						postAbortionComp.append(
								String.valueOf(((Double) postAbortionCompMap.get("complicationID")).intValue()) + ",");
						postAbortionCompValues
								.append(String.valueOf((String) postAbortionCompMap.get("complicationValue")) + ",");
					}
					pointer++;
				}

				obj.setPostAbortionComplication_db(postAbortionComp.toString());
				obj.setPostAbortionComplicationsValues(postAbortionCompValues.toString());
			}

			if (obj.getAbortionType() != null && obj.getAbortionType().containsKey("complicationID")
					&& obj.getAbortionType().containsKey("complicationValue")) {
				obj.setAbortionTypeID(((Double) obj.getAbortionType().get("complicationID")).intValue());
				obj.setTypeOfAbortionValue((String) obj.getAbortionType().get("complicationValue"));
			}

			if (obj.getTypeofFacility() != null && obj.getTypeofFacility().containsKey("serviceFacilityID")
					&& obj.getTypeofFacility().containsKey("facilityName")) {
				obj.setTypeofFacilityID(((Double) obj.getTypeofFacility().get("serviceFacilityID")).intValue());
				obj.setServiceFacilityValue((String) obj.getTypeofFacility().get("facilityName"));
			}

			// set pregnancy complication ID & Name (comma "," seperated)
			obj.setPregComplicationID(pregComplicationID.toString());
			obj.setPregComplicationType(pregComplicationName.toString());
			// set delivery complication ID & Name (comma "," seperated)
			obj.setDeliveryComplicationID(deliComplicationID.toString());
			obj.setDeliveryComplicationType(deliComplicationName.toString());
			// set postpartum complication ID & Name (comma "," seperated)
			obj.setPostpartumComplicationID(postpartumComplicationID.toString());
			obj.setPostpartumComplicationType(postpartumComplicationName.toString());
		}

		return femaleObstetricHistorylist;
	}

	public Integer saveBenMenstrualHistory(BenMenstrualDetails benMenstrualDetails) {
		Integer menstrualHistorySuccessFlag = null;

		ArrayList<Map<String, Object>> menstrualProblemList = benMenstrualDetails.getMenstrualProblemList();
		if (menstrualProblemList != null && menstrualProblemList.size() > 0) {
			StringBuilder problemID = new StringBuilder();
			StringBuilder problemName = new StringBuilder();

			for (int i = 0; i < menstrualProblemList.size(); i++) {
				if (i == (menstrualProblemList.size() - 1)) {
					problemID.append(menstrualProblemList.get(i).get("menstrualProblemID"));
					problemName.append(menstrualProblemList.get(i).get("problemName"));
				} else {
					problemID.append(menstrualProblemList.get(i).get("menstrualProblemID")).append(",");
					problemName.append(menstrualProblemList.get(i).get("problemName")).append(",");
				}
			}
			benMenstrualDetails.setMenstrualProblemID(problemID.toString());
			benMenstrualDetails.setProblemName(problemName.toString());
		}

		BenMenstrualDetails res = benMenstrualDetailsRepo.save(benMenstrualDetails);
		if (null != res && res.getBenMenstrualID() > 0) {
			menstrualHistorySuccessFlag = res.getBenMenstrualID();
		}
		return menstrualHistorySuccessFlag;
	}

	public Long saveBenFamilyHistory(BenFamilyHistory benFamilyHistory) {
		Long familyHistorySuccessFlag = null;

		ArrayList<BenFamilyHistory> familyHistoryList = benFamilyHistory.getBenFamilyHistory();
		if (familyHistoryList.size() > 0) {
			ArrayList<BenFamilyHistory> res = (ArrayList<BenFamilyHistory>) benFamilyHistoryRepo
					.saveAll(familyHistoryList);
			if (familyHistoryList.size() == res.size()) {
				familyHistorySuccessFlag = new Long(1);
			}
		} else {
			familyHistorySuccessFlag = new Long(1);
		}
		return familyHistorySuccessFlag;
	}

	public Integer savePersonalHistory(BenPersonalHabit benPersonalHabit) {
		Integer personalHistorySuccessFlag = null;

		ArrayList<BenPersonalHabit> personalHabits = benPersonalHabit.getPersonalHistory();
		if (personalHabits.size() > 0) {
			ArrayList<BenPersonalHabit> res = (ArrayList<BenPersonalHabit>) benPersonalHabitRepo.saveAll(personalHabits);
			if (personalHabits.size() == res.size()) {
				personalHistorySuccessFlag = 1;
			}
		} else {
			personalHistorySuccessFlag = 1;
		}
		return personalHistorySuccessFlag;
	}

	public Long saveAllergyHistory(BenAllergyHistory benAllergyHistory) {
		Long allergyHistorySuccessFlag = null;

		ArrayList<BenAllergyHistory> allergyList = benAllergyHistory.getBenAllergicHistory();
		if (allergyList.size() > 0) {
			ArrayList<BenAllergyHistory> res = (ArrayList<BenAllergyHistory>) benAllergyHistoryRepo.saveAll(allergyList);
			if (allergyList.size() == res.size()) {
				allergyHistorySuccessFlag = new Long(1);
			}
		} else {
			allergyHistorySuccessFlag = new Long(1);
		}
		return allergyHistorySuccessFlag;
	}

	public Long saveChildOptionalVaccineDetail(WrapperChildOptionalVaccineDetail wrapperChildVaccineDetail) {
		Long childVaccineSuccessFlag = null;
		ArrayList<ChildOptionalVaccineDetail> childOptionalVaccineDetails = wrapperChildVaccineDetail
				.getChildOptionalVaccineDetails();
		if (childOptionalVaccineDetails.size() > 0) {
			ArrayList<ChildOptionalVaccineDetail> res = (ArrayList<ChildOptionalVaccineDetail>) childOptionalVaccineDetailRepo
					.saveAll(childOptionalVaccineDetails);
			if (childOptionalVaccineDetails.size() == res.size()) {
				childVaccineSuccessFlag = new Long(1);
			}
		} else {
			childVaccineSuccessFlag = new Long(1);
		}
		return childVaccineSuccessFlag;
	}

	public Long saveImmunizationHistory(WrapperImmunizationHistory wrapperImmunizationHistory) {
		Long immunizationSuccessFlag = (long) 1;

		ArrayList<ChildVaccineDetail1> childVaccineDetails = wrapperImmunizationHistory.getBenChildVaccineDetails();

		if (childVaccineDetails != null && childVaccineDetails.size() > 0)
			childVaccineDetails = (ArrayList<ChildVaccineDetail1>) childVaccineDetail1Repo.saveAll(childVaccineDetails);

		return immunizationSuccessFlag;
	}

	public Long saveBeneficiaryPhysicalAnthropometryDetails(BenAnthropometryDetail benAnthropometryDetail) {
		BenAnthropometryDetail response = benAnthropometryRepo.save(benAnthropometryDetail);
		if (response != null)
			return response.getID();
		else
			return null;
	}

	public Long saveIDRS(IDRSData idrsDetail) {
		IDRSData response = iDrsDataRepo.save(idrsDetail);
		if (response != null)
			return response.getId();
		else
			return null;
	}

	public Long savePhysicalActivity(PhysicalActivityType physicalActivityDetail) {
		PhysicalActivityType response = physicalActivityaRepo.save(physicalActivityDetail);
		if (response != null)
			return response.getpAID();
		else
			return null;
	}

	public Long saveBenFamilyHistoryNCDScreening(BenFamilyHistory benFamilyHistory) {
		Long familyHistorySuccessFlag = null;

		ArrayList<BenFamilyHistory> familyHistoryList = benFamilyHistory.getBenFamilyHist();
		if (familyHistoryList.size() > 0) {
			ArrayList<BenFamilyHistory> res = (ArrayList<BenFamilyHistory>) benFamilyHistoryRepo
					.saveAll(familyHistoryList);
			if (familyHistoryList.size() == res.size()) {
				familyHistorySuccessFlag = new Long(1);
			}
		} else {
			familyHistorySuccessFlag = new Long(1);
		}
		return familyHistorySuccessFlag;
	}

	public Long saveBeneficiaryPhysicalVitalDetails(BenPhysicalVitalDetail benPhysicalVitalDetail) {
		// ArrayList<Short> averageSystolicList = new ArrayList<>();
		// ArrayList<Short> averageDiastolicList = new ArrayList<>();

		short sysBP = 0;
		short dysBP = 0;
		int j = 0;

		if (benPhysicalVitalDetail.getSystolicBP_1stReading() != null
				&& benPhysicalVitalDetail.getDiastolicBP_1stReading() != null) {
			sysBP = (short) (sysBP + benPhysicalVitalDetail.getSystolicBP_1stReading());
			dysBP = (short) (dysBP + benPhysicalVitalDetail.getDiastolicBP_1stReading());
			j++;
		}
		if (benPhysicalVitalDetail.getSystolicBP_2ndReading() != null
				&& benPhysicalVitalDetail.getDiastolicBP_2ndReading() != null) {
			sysBP = (short) (sysBP + benPhysicalVitalDetail.getSystolicBP_2ndReading());
			dysBP = (short) (dysBP + benPhysicalVitalDetail.getDiastolicBP_2ndReading());
			j++;
		}
		if (benPhysicalVitalDetail.getSystolicBP_3rdReading() != null
				&& benPhysicalVitalDetail.getDiastolicBP_3rdReading() != null) {
			sysBP = (short) (sysBP + benPhysicalVitalDetail.getSystolicBP_3rdReading());
			dysBP = (short) (dysBP + benPhysicalVitalDetail.getDiastolicBP_3rdReading());
			j++;
		}

		if (j > 0) {
			benPhysicalVitalDetail.setAverageSystolicBP((short) (sysBP / j));
			benPhysicalVitalDetail.setAverageDiastolicBP((short) (dysBP / j));
		}
		/**
		 * 
		 * 
		 * Short systolicBP_1stReading =
		 * benPhysicalVitalDetail.getSystolicBP_1stReading(); Short
		 * diastolicBP_1stReading = benPhysicalVitalDetail.getDiastolicBP_1stReading();
		 * if (systolicBP_1stReading != null && diastolicBP_1stReading != null) {
		 * averageSystolicList.add(systolicBP_1stReading);
		 * averageDiastolicList.add(diastolicBP_1stReading); }
		 * 
		 * Short systolicBP_2ndReading =
		 * benPhysicalVitalDetail.getSystolicBP_2ndReading(); Short
		 * diastolicBP_2ndReading = benPhysicalVitalDetail.getDiastolicBP_2ndReading();
		 * if (systolicBP_2ndReading != null && diastolicBP_2ndReading != null) {
		 * averageSystolicList.add(systolicBP_2ndReading);
		 * averageDiastolicList.add(diastolicBP_2ndReading); }
		 * 
		 * Short systolicBP_3rdReading =
		 * benPhysicalVitalDetail.getSystolicBP_3rdReading(); Short
		 * diastolicBP_3rdReading = benPhysicalVitalDetail.getDiastolicBP_3rdReading();
		 * if (systolicBP_3rdReading != null && diastolicBP_3rdReading != null) {
		 * averageSystolicList.add(systolicBP_3rdReading);
		 * averageDiastolicList.add(diastolicBP_3rdReading); }
		 * 
		 * Short averageSystolic = (short) ((averageSystolicList.stream().mapToInt(i ->
		 * i.shortValue()).sum()) / averageSystolicList.size()); Short averageDiastolic
		 * = (short) ((averageDiastolicList.stream().mapToInt(i ->
		 * i.shortValue()).sum()) / averageDiastolicList.size());
		 * 
		 * benPhysicalVitalDetail.setAverageSystolicBP(averageSystolic);
		 * benPhysicalVitalDetail.setAverageDiastolicBP(averageDiastolic);
		 * 
		 */

		BenPhysicalVitalDetail response = benPhysicalVitalRepo.save(benPhysicalVitalDetail);
		if (response != null)
			return response.getID();
		else
			return null;
	}

	public String getBeneficiaryPhysicalAnthropometryDetails(Long beneficiaryRegID, Long visitCode) {
		BenAnthropometryDetail benAnthropometryDetail = benAnthropometryRepo.getBenAnthropometryDetail(beneficiaryRegID,
				visitCode);
		return new Gson().toJson(benAnthropometryDetail);
	}

	public String getBenCdssDetails(Long beneficiaryRegID, Long visitCode) {
		CDSS cdss = cdssRepo.findByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode);
		return new Gson().toJson(cdss);
	}

	public String getBeneficiaryPhysicalVitalDetails(Long beneficiaryRegID, Long visitCode) {
		BenPhysicalVitalDetail benPhysicalVitalDetail = benPhysicalVitalRepo.getBenPhysicalVitalDetail(beneficiaryRegID,
				visitCode);
		return new Gson().toJson(benPhysicalVitalDetail);
	}

	public int updateANCAnthropometryDetails(BenAnthropometryDetail anthropometryDetail) throws IEMRException {
		Integer r = 0;
		if (null != anthropometryDetail) {

			String processed = benAnthropometryRepo.getBenAnthropometryStatus(anthropometryDetail.getBeneficiaryRegID(),
					anthropometryDetail.getVisitCode());
			if (null != processed && !processed.equals("N")) {
				processed = "U";
			} else {
				processed = "N";
			}

			// anthropometryDetail.setModifiedBy(anthropometryDetail.getCreatedBy());
			r = benAnthropometryRepo.updateANCCareDetails(anthropometryDetail.getWeight_Kg(),
					anthropometryDetail.getHeight_cm(), anthropometryDetail.getbMI(),
					anthropometryDetail.getHeadCircumference_cm(),
					anthropometryDetail.getMidUpperArmCircumference_MUAC_cm(),
					anthropometryDetail.getHipCircumference_cm(), anthropometryDetail.getWaistCircumference_cm(),
					anthropometryDetail.getWaistHipRatio(), anthropometryDetail.getModifiedBy(), processed,
					anthropometryDetail.getBeneficiaryRegID(), anthropometryDetail.getVisitCode());
		}
		return r;
	}

	public int updateANCPhysicalVitalDetails(BenPhysicalVitalDetail physicalVitalDetail) throws IEMRException {
		Integer r = 0;
		if (null != physicalVitalDetail) {

			String processed = benPhysicalVitalRepo.getBenPhysicalVitalStatus(physicalVitalDetail.getBeneficiaryRegID(),
					physicalVitalDetail.getVisitCode());
			if (null != processed && !processed.equals("N")) {
				processed = "U";
			} else {
				processed = "N";
			}

			physicalVitalDetail.setAverageSystolicBP(physicalVitalDetail.getSystolicBP_1stReading());
			physicalVitalDetail.setAverageDiastolicBP(physicalVitalDetail.getDiastolicBP_1stReading());
			r = benPhysicalVitalRepo.updatePhysicalVitalDetails(physicalVitalDetail.getTemperature(),
					physicalVitalDetail.getPulseRate(), physicalVitalDetail.getRespiratoryRate(),
					physicalVitalDetail.getSystolicBP_1stReading(), physicalVitalDetail.getDiastolicBP_1stReading(),
					physicalVitalDetail.getSystolicBP_2ndReading(), physicalVitalDetail.getDiastolicBP_2ndReading(),
					physicalVitalDetail.getSystolicBP_3rdReading(), physicalVitalDetail.getDiastolicBP_3rdReading(),
					physicalVitalDetail.getAverageSystolicBP(), physicalVitalDetail.getAverageDiastolicBP(),
					physicalVitalDetail.getBloodPressureStatusID(), physicalVitalDetail.getBloodPressureStatus(),
					physicalVitalDetail.getBloodGlucose_Fasting(), physicalVitalDetail.getBloodGlucose_Random(),
					physicalVitalDetail.getBloodGlucose_2hr_PP(), physicalVitalDetail.getBloodGlucose_NotSpecified(),
					physicalVitalDetail.getDiabeticStatusID(), physicalVitalDetail.getDiabeticStatus(),
					physicalVitalDetail.getCapillaryRefillTime(), physicalVitalDetail.getModifiedBy(), processed,
					physicalVitalDetail.getRbsTestResult(), physicalVitalDetail.getRbsTestRemarks(),
					physicalVitalDetail.getsPO2(), physicalVitalDetail.getBeneficiaryRegID(),
					physicalVitalDetail.getVisitCode(), physicalVitalDetail.getHemoglobin());

		}
		return r;
	}

	public Long savePhyGeneralExamination(PhyGeneralExamination generalExamination) throws IEMRException {
		// Long generalExaminationID = null;
		String TypeOfDangerSigns = "";
		if (null != generalExamination.getTypeOfDangerSigns() && generalExamination.getTypeOfDangerSigns().size() > 0) {
			for (String TypeOfDangerSign : generalExamination.getTypeOfDangerSigns()) {
				TypeOfDangerSigns += TypeOfDangerSign + ",";
			}
			generalExamination.setTypeOfDangerSign(TypeOfDangerSigns);
		}

		generalExamination = phyGeneralExaminationRepo.save(generalExamination);
		if (generalExamination != null && generalExamination.getID() != null)
			return generalExamination.getID();

		else
			throw new IEMRException("DB-Error in saving General Examination");
	}

	public Long savePhyHeadToToeExamination(PhyHeadToToeExamination headToToeExamination) throws IEMRException {
		// Long examinationID = null;
		headToToeExamination = phyHeadToToeExaminationRepo.save(headToToeExamination);

		if (headToToeExamination != null && headToToeExamination.getID() > 0)
			return headToToeExamination.getID();
		else
			throw new IEMRException("DB-Error in saving Head To Toe Examination");
	}

	public Long saveSysGastrointestinalExamination(SysGastrointestinalExamination gastrointestinalExamination)
			throws IEMRException {
		// Long examinationID = null;
		gastrointestinalExamination = sysGastrointestinalExaminationRepo.save(gastrointestinalExamination);
		if (gastrointestinalExamination != null && gastrointestinalExamination.getID() > 0)
			return gastrointestinalExamination.getID();
		else
			throw new IEMRException("DB-Error in saving Gastrointestinal Examination");
	}

	public Long saveSysCardiovascularExamination(SysCardiovascularExamination cardiovascularExamination)
			throws IEMRException {
		// Long examinationID = null;
		cardiovascularExamination = sysCardiovascularExaminationRepo.save(cardiovascularExamination);

		if (cardiovascularExamination != null && cardiovascularExamination.getID() > 0)
			return cardiovascularExamination.getID();
		else
			throw new IEMRException("DB-Error in saving Cardiovascular Examination");
	}

	public Long saveSysRespiratoryExamination(SysRespiratoryExamination respiratoryExamination) throws IEMRException {
		// TODO Auto-generated method stub
		// Long r = null;
		respiratoryExamination = sysRespiratoryExaminationRepo.save(respiratoryExamination);
		if (respiratoryExamination != null && respiratoryExamination.getID() > 0)
			return respiratoryExamination.getID();
		else
			throw new IEMRException("DB-Error in saving Respiratory Examination");
	}

	public Long saveSysCentralNervousExamination(SysCentralNervousExamination centralNervousExamination)
			throws IEMRException {
		// TODO Auto-generated method stub
		// Long r = null;
		centralNervousExamination = sysCentralNervousExaminationRepo.save(centralNervousExamination);
		if (centralNervousExamination != null && centralNervousExamination.getID() != null) {
			return centralNervousExamination.getID();
		} else
			throw new IEMRException("DB-Error in saving Central Nervous Examination");

	}

	public Long saveSysMusculoskeletalSystemExamination(
			SysMusculoskeletalSystemExamination musculoskeletalSystemExamination) throws IEMRException {
		// TODO Auto-generated method stub
		// Long r = null;
		musculoskeletalSystemExamination = sysMusculoskeletalSystemExaminationRepo
				.save(musculoskeletalSystemExamination);
		if (musculoskeletalSystemExamination != null && musculoskeletalSystemExamination.getID() != null) {
			return musculoskeletalSystemExamination.getID();
		} else
			throw new IEMRException("DB-Error in saving Musculoskeletal System Examination");
	}

	public Long saveSysGenitourinarySystemExamination(SysGenitourinarySystemExamination genitourinarySystemExamination)
			throws IEMRException {
		// TODO Auto-generated method stub
		// Long r = null;
		genitourinarySystemExamination = sysGenitourinarySystemExaminationRepo.save(genitourinarySystemExamination);

		if (genitourinarySystemExamination.getID() != null && genitourinarySystemExamination.getID() > 0)
			return genitourinarySystemExamination.getID();

		else
			throw new IEMRException("DB-Error in saving Genitourinary System Examination");
	}

	public String fetchBenPastMedicalHistory(Long benRegID) {
		Map<String, Object> resMap = new HashMap<>();
		ArrayList<Object[]> benPastHistoryDataArray = benMedHistoryRepo.getBenPastHistory(benRegID);
		ArrayList<BenMedHistory> benMedHistoryArrayList = new ArrayList<>();
		if (benPastHistoryDataArray != null && benPastHistoryDataArray.size() > 0) {
			BenMedHistory benMedHistory;
			for (Object[] obj : benPastHistoryDataArray) {
				benMedHistory = new BenMedHistory((Date) obj[0], (String) obj[1], (String) obj[2], (Date) obj[3],
						(String) obj[4], (String) obj[5], (Date) obj[6]);
				benMedHistoryArrayList.add(benMedHistory);
			}
		}

		Map<String, String> columnMap = new HashMap<>();
		List<Map<String, String>> columns = new ArrayList<Map<String, String>>();

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Date of Capture");
		columnMap.put("keyName", "captureDate");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "");
		columnMap.put("keyName", "");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Illness Type");
		columnMap.put("keyName", "Illness_Type");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Other Illness Type");
		columnMap.put("keyName", "Other_Illness_Type");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Year of Illness");
		columnMap.put("keyName", "Year_Of_Illness");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Surgery Type");
		columnMap.put("keyName", "Surgery_Type");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Other Surgery Type");
		columnMap.put("keyName", "Other_Surgery_Type");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Year of Surgery");
		columnMap.put("keyName", "Year_Of_Surgery");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "");
		columnMap.put("keyName", "");
		columns.add(columnMap);

		resMap.put("columns", columns);
		resMap.put("data", benMedHistoryArrayList);

		return new Gson().toJson(resMap);

	}

	public String fetchBenPersonalTobaccoHistory(Long beneficiaryRegID) {
		ArrayList<Object[]> benPersonalHabits = (ArrayList<Object[]>) benPersonalHabitRepo
				.getBenPersonalTobaccoHabitDetail(beneficiaryRegID);

		Map<String, Object> response = new HashMap<String, Object>();
		List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
		Map<String, Object> column = new HashMap<String, Object>();

		column = new HashMap<>();
		column.put("columnName", "Date of Capture");
		column.put("keyName", "captureDate");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Dietary Type");
		column.put("keyName", "dietaryType");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Physical Activity Type");
		column.put("keyName", "physicalActivityType");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Tobacco Use Status");
		column.put("keyName", "tobaccoUseStatus");
		columns.add(column);

		/*
		 * column = new HashMap<String, Object>(); column.put("columnName",
		 * "Tobacco Use Type ID"); column.put("keyName", "tobaccoUseTypeID");
		 * columns.add(column);
		 */

		column = new HashMap<String, Object>();
		column.put("columnName", "Tobacco Use Type");
		column.put("keyName", "tobaccoUseType");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Other Tobacco Use Type");
		column.put("keyName", "otherTobaccoUseType");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Number Per Day");
		column.put("keyName", "numberperDay");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Number Per Week");
		column.put("keyName", "numberperWeek");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Tobacco Use Start Date");
		column.put("keyName", "tobacco_use_duration");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Risky Sexual Practices Status");
		column.put("keyName", "riskySexualPracticeStatus");
		columns.add(column);

		ArrayList<BenPersonalHabit> personalHabits = new ArrayList<BenPersonalHabit>();
		if (null != benPersonalHabits) {
			for (Object[] obj : benPersonalHabits) {

				BenPersonalHabit benPersonalHabit = new BenPersonalHabit((Date) obj[0], (String) obj[1],
						(String) obj[2], (String) obj[3], (String) obj[4], (String) obj[5], (Short) obj[6],
						(Date) obj[7], (Character) obj[8], (Short) obj[9]);

				personalHabits.add(benPersonalHabit);
			}
		}

		response.put("columns", columns);
		response.put("data", personalHabits);
		return new Gson().toJson(response);

	}

	public String fetchBenPersonalAlcoholHistory(Long beneficiaryRegID) {
		ArrayList<Object[]> benPersonalHabits = (ArrayList<Object[]>) benPersonalHabitRepo
				.getBenPersonalAlcoholHabitDetail(beneficiaryRegID);

		Map<String, Object> response = new HashMap<String, Object>();
		List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
		Map<String, Object> column = new HashMap<String, Object>();

		column = new HashMap<>();
		column.put("columnName", "Date of Capture");
		column.put("keyName", "captureDate");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Dietary Type");
		column.put("keyName", "dietaryType");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Physical Activity Type");
		column.put("keyName", "physicalActivityType");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Alcohol Intake Status");
		column.put("keyName", "alcoholIntakeStatus");
		columns.add(column);

		/*
		 * column = new HashMap<String, Object>(); column.put("columnName",
		 * "Alcohol Type ID"); column.put("keyName", "alcoholTypeID");
		 * columns.add(column);
		 */
		column = new HashMap<String, Object>();
		column.put("columnName", "Alcohol Type");
		column.put("keyName", "alcoholType");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Other Alcohol Type");
		column.put("keyName", "otherAlcoholType");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Alcohol Intake Frequency");
		column.put("keyName", "alcoholIntakeFrequency");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Avg Alcohol Consumption");
		column.put("keyName", "avgAlcoholConsumption");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Alcohol Use Started Date");
		column.put("keyName", "alcohol_use_duration");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Risky Sexual Practices Status");
		column.put("keyName", "riskySexualPracticeStatus");
		columns.add(column);

		ArrayList<BenPersonalHabit> personalHabits = new ArrayList<BenPersonalHabit>();
		if (null != benPersonalHabits) {
			for (Object[] obj : benPersonalHabits) {
				BenPersonalHabit benPersonalHabit = new BenPersonalHabit((Date) obj[0], (String) obj[1],
						(String) obj[2], (String) obj[3], (String) obj[4], (String) obj[5], (String) obj[6],
						(String) obj[7], (Date) obj[8], (Character) obj[9]);
				personalHabits.add(benPersonalHabit);
			}
		}

		response.put("columns", columns);
		response.put("data", personalHabits);
		return new Gson().toJson(response);

	}

	public String fetchBenPersonalAllergyHistory(Long beneficiaryRegID) {
		ArrayList<Object[]> benPersonalHabits = (ArrayList<Object[]>) benAllergyHistoryRepo
				.getBenPersonalAllergyDetail(beneficiaryRegID);

		Map<String, Object> response = new HashMap<String, Object>();
		List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
		Map<String, Object> column = new HashMap<String, Object>();

		column = new HashMap<>();
		column.put("columnName", "Date of Capture");
		column.put("keyName", "captureDate");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Allergy Status");
		column.put("keyName", "allergyStatus");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Allergy Type");
		column.put("keyName", "allergyType");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Allergy Name");
		column.put("keyName", "allergyName");
		columns.add(column);

		/*
		 * column = new HashMap<String, Object>(); column.put("columnName",
		 * "Allergic Reaction Type ID"); column.put("keyName",
		 * "allergicReactionTypeID"); columns.add(column);
		 */

		column = new HashMap<String, Object>();
		column.put("columnName", "Allergic Reaction Type");
		column.put("keyName", "allergicReactionType");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Other Allergic Reaction");
		column.put("keyName", "otherAllergicReaction");
		columns.add(column);

		ArrayList<BenAllergyHistory> personalHabits = new ArrayList<BenAllergyHistory>();
		if (null != benPersonalHabits) {
			for (Object[] obj : benPersonalHabits) {

				BenAllergyHistory benPersonalHabit = new BenAllergyHistory((Date) obj[0], (String) obj[1],
						(String) obj[2], (String) obj[3], (String) obj[4], (String) obj[5], (String) obj[6]);
				personalHabits.add(benPersonalHabit);
			}

		}

		response.put("columns", columns);
		response.put("data", personalHabits);
		return new Gson().toJson(response);

	}

	public String fetchBenPersonalMedicationHistory(Long beneficiaryRegID) {
		ArrayList<Object[]> beMedicationHistory = benMedicationHistoryRepo
				.getBenMedicationHistoryDetail(beneficiaryRegID);

		Map<String, Object> response = new HashMap<String, Object>();
		List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
		Map<String, Object> column = new HashMap<String, Object>();

		column = new HashMap<>();
		column.put("columnName", "Date of Capture");
		column.put("keyName", "captureDate");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Current Medication");
		column.put("keyName", "currentMedication");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Date");
		column.put("keyName", "medication_year");
		columns.add(column);

		ArrayList<BenMedicationHistory> medicationHistory = new ArrayList<BenMedicationHistory>();
		if (null != beMedicationHistory) {
			for (Object[] obj : beMedicationHistory) {
				BenMedicationHistory history = new BenMedicationHistory((Date) obj[0], (String) obj[1], (Date) obj[2]);
				medicationHistory.add(history);
			}

		}

		response.put("columns", columns);
		response.put("data", medicationHistory);
		return new Gson().toJson(response);

	}

	public String fetchBenPersonalFamilyHistory(Long beneficiaryRegID) {
		ArrayList<Object[]> benFamilyHistory = benFamilyHistoryRepo.getBenFamilyHistoryDetail(beneficiaryRegID);

		Map<String, Object> response = new HashMap<String, Object>();
		List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
		Map<String, Object> column = new HashMap<String, Object>();

		column = new HashMap<>();
		column.put("columnName", "Date of Capture");
		column.put("keyName", "captureDate");
		columns.add(column);

		/*
		 * column = new HashMap<String, Object>(); column.put("columnName",
		 * "Disease Type ID"); column.put("keyName", "diseaseTypeID");
		 * columns.add(column);
		 */

		column = new HashMap<String, Object>();
		column.put("columnName", "Disease Type");
		column.put("keyName", "diseaseType");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Other Disease Type");
		column.put("keyName", "otherDiseaseType");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Family Member");
		column.put("keyName", "familyMember");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Is Genetic Disorder");
		column.put("keyName", "IsGeneticDisorder");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Genetic Disorder");
		column.put("keyName", "geneticDisorder");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Is Consanguineous Marrige");
		column.put("keyName", "IsConsanguineousMarrige");
		columns.add(column);

		ArrayList<BenFamilyHistory> familyHistory = new ArrayList<BenFamilyHistory>();
		if (null != benFamilyHistory) {
			for (Object[] obj : benFamilyHistory) {
				BenFamilyHistory history = new BenFamilyHistory((Date) obj[0], (String) obj[1], (String) obj[2],
						(String) obj[3], (Boolean) obj[4], (String) obj[5], (Boolean) obj[6]);
				familyHistory.add(history);
			}

		}

		response.put("columns", columns);
		response.put("data", familyHistory);
		return new Gson().toJson(response);

	}

	public String fetchBenPhysicalHistory(Long beneficiaryRegID) {
		ArrayList<Object[]> benPhysicalHistory = physicalActivityTypeRepo.getBenPhysicalHistoryDetail(beneficiaryRegID);

		Map<String, Object> response = new HashMap<String, Object>();
		List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
		Map<String, Object> column = new HashMap<String, Object>();

		column = new HashMap<>();
		column.put("columnName", "Date of Capture");
		column.put("keyName", "captureDate");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Activity Type");
		column.put("keyName", "activityType");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Physical Activity Type");
		column.put("keyName", "physicalActivityType");
		columns.add(column);

		ArrayList<PhysicalActivityType> familyHistory = new ArrayList<PhysicalActivityType>();
		if (null != benPhysicalHistory) {
			for (Object[] obj : benPhysicalHistory) {
				PhysicalActivityType phyhistory = new PhysicalActivityType((Date) obj[0], (String) obj[1],
						(String) obj[2]);
				familyHistory.add(phyhistory);
			}
		}

		response.put("columns", columns);
		response.put("data", familyHistory);
		return new Gson().toJson(response);

	}

	public String fetchBenMenstrualHistory(Long beneficiaryRegID) {
		ArrayList<Object[]> benMenstrualDetails = benMenstrualDetailsRepo.getBenMenstrualDetail(beneficiaryRegID);

		Map<String, Object> response = new HashMap<String, Object>();
		List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
		Map<String, Object> column = new HashMap<String, Object>();

		column = new HashMap<>();
		column.put("columnName", "Date of Capture");
		column.put("keyName", "captureDate");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Regularity");
		column.put("keyName", "regularity");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Cycle Length");
		column.put("keyName", "cycleLength");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Blood Flow Duration");
		column.put("keyName", "bloodFlowDuration");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Problem Name");
		column.put("keyName", "problemName");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "LMPDate");
		column.put("keyName", "lmp_date");
		columns.add(column);

		ArrayList<BenMenstrualDetails> menstrualDetails = new ArrayList<BenMenstrualDetails>();
		if (null != benMenstrualDetails) {
			for (Object[] obj : benMenstrualDetails) {

				BenMenstrualDetails history = new BenMenstrualDetails((Date) obj[0], (String) obj[1], (String) obj[2],
						(String) obj[3], (String) obj[4], (Date) obj[5]);
				menstrualDetails.add(history);
			}

		}

		response.put("columns", columns);
		response.put("data", menstrualDetails);
		return new Gson().toJson(response);

	}

	public String fetchBenPastObstetricHistory(Long beneficiaryRegID) {
		ArrayList<Object[]> femaleObstetricHistory = femaleObstetricHistoryRepo
				.getBenFemaleObstetricHistoryDetail(beneficiaryRegID);

		Map<String, Object> response = new HashMap<String, Object>();
		List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
		Map<String, Object> column = new HashMap<String, Object>();

		column = new HashMap<>();
		column.put("columnName", "Date of Capture");
		column.put("keyName", "captureDate");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Preg Order");
		column.put("keyName", "pregOrder");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Preg Complication Type");
		column.put("keyName", "pregComplicationType");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Other Preg Complication");
		column.put("keyName", "otherPregComplication");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Duration Type");
		column.put("keyName", "durationType");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Delivery Type");
		column.put("keyName", "deliveryType");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Delivery Place");
		column.put("keyName", "deliveryPlace");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Other Delivery Place");
		column.put("keyName", "otherDeliveryPlace");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Delivery Complication Type");
		column.put("keyName", "deliveryComplicationType");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Other Delivery Complication");
		column.put("keyName", "otherDeliveryComplication");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Preg Outcome");
		column.put("keyName", "pregOutcome");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Postpartum Complication Type");
		column.put("keyName", "postpartumComplicationType");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Other Postpartum CompType");
		column.put("keyName", "otherPostpartumCompType");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Post Natal Complication");
		column.put("keyName", "postNatalComplication");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Other Post Natal Complication");
		column.put("keyName", "otherPostNatalComplication");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Congenital Anomalies");
		column.put("keyName", "congenitalAnomalies");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "New Born Complication");
		column.put("keyName", "newBornComplication");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Other New Born Complication");
		column.put("keyName", "otherNewBornComplication");
		columns.add(column);

		ArrayList<FemaleObstetricHistory> femaleObstetricDetails = new ArrayList<FemaleObstetricHistory>();
		if (null != femaleObstetricHistory) {
			for (Object[] obj : femaleObstetricHistory) {

				FemaleObstetricHistory history = new FemaleObstetricHistory((Date) obj[0], (Short) obj[1],
						(String) obj[2], (String) obj[3], (String) obj[4], (String) obj[5], (String) obj[6],
						(String) obj[7], (String) obj[8], (String) obj[9], (String) obj[10], (String) obj[11],
						(String) obj[12], (String) obj[13], (String) obj[14], (String) obj[15], (String) obj[16],
						(String) obj[17]);
				femaleObstetricDetails.add(history);
			}

		}

		response.put("columns", columns);
		response.put("data", femaleObstetricDetails);
		return new Gson().toJson(response);

	}

	public String fetchBenComorbidityHistory(Long beneficiaryRegID) {
		ArrayList<Object[]> bencomrbidityCondDetails = bencomrbidityCondRepo
				.getBencomrbidityCondDetails(beneficiaryRegID);

		Map<String, Object> response = new HashMap<String, Object>();
		List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
		Map<String, Object> column = new HashMap<String, Object>();

		column = new HashMap<>();
		column.put("columnName", "Date of Capture");
		column.put("keyName", "captureDate");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Comorbid Condition");
		column.put("keyName", "comorbidCondition");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Other Comorbid Condition");
		column.put("keyName", "otherComorbidCondition");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Date");
		column.put("keyName", "date");
		columns.add(column);

		ArrayList<BencomrbidityCondDetails> bencomrbidityConds = new ArrayList<BencomrbidityCondDetails>();
		if (null != bencomrbidityCondDetails) {
			for (Object[] obj : bencomrbidityCondDetails) {

				BencomrbidityCondDetails history = new BencomrbidityCondDetails((Date) obj[0], (String) obj[1],
						(String) obj[2], (Date) obj[3]);
				bencomrbidityConds.add(history);
			}

		}

		response.put("columns", columns);
		response.put("data", bencomrbidityConds);
		return new Gson().toJson(response);

	}

	public String fetchBenImmunizationHistory(Long beneficiaryRegID) {
		ArrayList<Object[]> childVaccineDetail = childVaccineDetail1Repo.getBenChildVaccineDetails(beneficiaryRegID);

		Map<String, Object> response = new HashMap<String, Object>();
		List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
		Map<String, Object> column = new HashMap<String, Object>();

		column = new HashMap<>();
		column.put("columnName", "Date of Capture");
		column.put("keyName", "captureDate");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Default Receiving Age");
		column.put("keyName", "defaultReceivingAge");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Vaccine Name");
		column.put("keyName", "vaccineName");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Status");
		column.put("keyName", "status");
		columns.add(column);

		ArrayList<ChildVaccineDetail1> childVaccineDetails = new ArrayList<ChildVaccineDetail1>();
		if (null != childVaccineDetail) {
			for (Object[] obj : childVaccineDetail) {
				ChildVaccineDetail1 history = new ChildVaccineDetail1((Date) obj[0], (String) obj[1], (String) obj[2],
						(Boolean) obj[3]);
				childVaccineDetails.add(history);
			}
		}

		response.put("columns", columns);
		response.put("data", childVaccineDetails);
		return new Gson().toJson(response);

	}

	public String fetchBenOptionalVaccineHistory(Long beneficiaryRegID) {
		ArrayList<Object[]> childOptionalVaccineDetail = childOptionalVaccineDetailRepo
				.getBenOptionalVaccineDetail(beneficiaryRegID);

		Map<String, Object> response = new HashMap<String, Object>();
		List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
		Map<String, Object> column = new HashMap<String, Object>();

		column = new HashMap<>();
		column.put("columnName", "Date of Capture");
		column.put("keyName", "captureDate");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Default Receiving Age");
		column.put("keyName", "defaultReceivingAge");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Vaccine Name");
		column.put("keyName", "vaccineName");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Other Vaccine Name");
		column.put("keyName", "otherVaccineName");
		columns.add(column);

		/** Later we will enable these two if needed **/
		/*
		 * column = new HashMap<String, Object>(); column.put("columnName", "Status");
		 * column.put("keyName", "status"); columns.add(column);
		 * 
		 * column = new HashMap<String, Object>(); column.put("columnName",
		 * "Received Date"); column.put("keyName", "receivedDate"); columns.add(column);
		 */

		column = new HashMap<String, Object>();
		column.put("columnName", "Actual Receiving Age");
		column.put("keyName", "actualReceivingAge");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Age Unit");
		column.put("keyName", "ageUnit");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Received Facility Name");
		column.put("keyName", "receivedFacilityName");
		columns.add(column);

		ArrayList<ChildOptionalVaccineDetail> childOptionalVaccineDetails = new ArrayList<ChildOptionalVaccineDetail>();
		if (null != childOptionalVaccineDetail) {
			for (Object[] obj : childOptionalVaccineDetail) {
				ChildOptionalVaccineDetail history = new ChildOptionalVaccineDetail((Date) obj[0], (String) obj[1],
						(String) obj[2], (String) obj[3], (String) obj[4], (Timestamp) obj[5], (String) obj[6],
						(String) obj[7], (String) obj[8]);
				childOptionalVaccineDetails.add(history);
			}
		}

		response.put("columns", columns);
		response.put("data", childOptionalVaccineDetails);
		return new Gson().toJson(response);

	}

	public String getBenChiefComplaints(Long beneficiaryRegID, Long visitCode) {
		ArrayList<Object[]> resList = benChiefComplaintRepo.getBenChiefComplaints(beneficiaryRegID, visitCode);
		ArrayList<BenChiefComplaint> benChiefComplaints = BenChiefComplaint.getBenChiefComplaints(resList);
		return new Gson().toJson(benChiefComplaints);
	}

	public String getBenCdss(Long beneficiaryRegID, Long visitCode) {
		Map<String, Object> cdssData = new HashMap<>();
		CDSS cdss = cdssRepo.findByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode);

		if (cdss != null) {
			Map<String, Object> presentChiefComplaint = new HashMap<>();
			Map<String, Object> diseaseSummary = new HashMap<>();

			presentChiefComplaint.put("selectedDiagnosisID", cdss.getSelectedDiagnosisID());
			presentChiefComplaint.put("selectedDiagnosis", cdss.getSelectedDiagnosis());
			presentChiefComplaint.put("presentChiefComplaint", cdss.getPresentChiefComplaint());
			presentChiefComplaint.put("presentChiefComplaintID", cdss.getPresentChiefComplaintID());
			presentChiefComplaint.put("algorithmPc", cdss.getAlgorithmPc());
			presentChiefComplaint.put("recommendedActionPc", cdss.getRecommendedActionPc());
			presentChiefComplaint.put("remarksPc", cdss.getRemarksPc());
			presentChiefComplaint.put("actionPc", cdss.getActionPc());
			presentChiefComplaint.put("actionIdPc", cdss.getActionIdPc());
			diseaseSummary.put("diseaseSummaryID", cdss.getDiseaseSummaryID());
			diseaseSummary.put("diseaseSummary", cdss.getDiseaseSummary());
			diseaseSummary.put("algorithm", cdss.getAlgorithm());
			diseaseSummary.put("recommendedAction", cdss.getRecommendedAction());
			diseaseSummary.put("remarks", cdss.getRemarks());
			diseaseSummary.put("action", cdss.getAction());
			diseaseSummary.put("actionId", cdss.getActionId());
			diseaseSummary.put("informationGiven", cdss.getInformationGiven());

			cdssData.put("presentChiefComplaint", presentChiefComplaint);
			cdssData.put("diseaseSummary", diseaseSummary);
			cdssData.put("vanID", cdss.getVanID());
			cdssData.put("parkingPlaceID", cdss.getParkingPlaceID());
			cdssData.put("providerServiceMapID", cdss.getProviderServiceMapID());
			cdssData.put("beneficiaryRegID", cdss.getBeneficiaryRegID());
			cdssData.put("benVisitID", cdss.getBenVisitID());
			cdssData.put("createdBy", cdss.getCreatedBy());
		}
		return new Gson().toJson(cdssData);
	}

	public BenFamilyHistory getFamilyHistoryDetail(Long beneficiaryRegID, Long visitCode) {
//		BenFamilyHistory familyHistory = benFamilyHistoryRepo.getBenFamilyHistoryDetails(beneficiaryRegID, visitCode);
//		
//
//		return new Gson().toJson(familyHistory);
		ArrayList<Object[]> familyHistory = benFamilyHistoryRepo.getBenFamilyHisDetail(beneficiaryRegID, visitCode);
		BenFamilyHistory familyHistoryDetails = BenFamilyHistory.getBenFamilyHist(familyHistory);

		return familyHistoryDetails;

	}

	public PhysicalActivityType getPhysicalActivityType(Long beneficiaryRegID, Long visitCode) {
		PhysicalActivityType phyHistory = physicalActivityTypeRepo.getBenPhysicalHistoryDetails(beneficiaryRegID,
				visitCode);
		return phyHistory;

	}

	public IDRSData getBeneficiaryIdrsDetails(Long beneficiaryRegID, Long visitCode) {

		ArrayList<Object[]> idrsDetails = iDRSDataRepo.getBenIdrsDetail(beneficiaryRegID, visitCode);
		IDRSData idrsDetail = IDRSData.getIDRSData(idrsDetails);
		return idrsDetail;
	}

	public BenMedHistory getPastHistoryData(Long beneficiaryRegID, Long visitCode) {
		ArrayList<Object[]> pastHistory = benMedHistoryRepo.getBenPastHistory(beneficiaryRegID, visitCode);

		BenMedHistory medHistory = new BenMedHistory();
		BenMedHistory benMedHistory = medHistory.getBenPastHistory(pastHistory);
		return benMedHistory;
	}

	public WrapperComorbidCondDetails getComorbidityConditionsHistory(Long beneficiaryRegID, Long visitCode) {
		ArrayList<Object[]> comrbidityConds = bencomrbidityCondRepo.getBencomrbidityCondDetails(beneficiaryRegID,
				visitCode);

		WrapperComorbidCondDetails comrbidityCondDetails = WrapperComorbidCondDetails
				.getComorbidityDetails(comrbidityConds);
		return comrbidityCondDetails;
	}

	public WrapperMedicationHistory getMedicationHistory(Long beneficiaryRegID, Long visitCode) {
		ArrayList<Object[]> medicationHistory = benMedicationHistoryRepo.getBenMedicationHistoryDetail(beneficiaryRegID,
				visitCode);

		WrapperMedicationHistory wrapperMedicationHistory = WrapperMedicationHistory
				.getMedicationHistoryDetails(medicationHistory);
		return wrapperMedicationHistory;
	}

	public BenPersonalHabit getPersonalHistory(Long beneficiaryRegID, Long visitCode) {
		ArrayList<Object[]> personalDetails = benPersonalHabitRepo.getBenPersonalHabitDetail(beneficiaryRegID,
				visitCode);

		ArrayList<Object[]> allergyDetails = benAllergyHistoryRepo.getBenPersonalAllergyDetail(beneficiaryRegID,
				visitCode);

		BenPersonalHabit personalHabits = BenPersonalHabit.getPersonalDetails(personalDetails);
		if (null == personalHabits) {
			personalHabits = new BenPersonalHabit();
		}
		ArrayList<BenAllergyHistory> allergyList = BenAllergyHistory.getBenAllergicHistory(allergyDetails);
		if (null != allergyList && allergyList.size() > 0) {
			personalHabits.setAllergyStatus(allergyList.get(0).getAllergyStatus());
			personalHabits.setAllergicList(allergyList);
		}

		return personalHabits;
	}

	public BenFamilyHistory getFamilyHistory(Long beneficiaryRegID, Long visitCode) {
		ArrayList<Object[]> familyHistory = benFamilyHistoryRepo.getBenFamilyHistoryDetail(beneficiaryRegID, visitCode);
		BenFamilyHistory familyHistoryDetails = BenFamilyHistory.getBenFamilyHistory(familyHistory);

		return familyHistoryDetails;
	}

	public BenMenstrualDetails getMenstrualHistory(Long beneficiaryRegID, Long visitCode) {
		ArrayList<Object[]> menstrualHistory = benMenstrualDetailsRepo.getBenMenstrualDetail(beneficiaryRegID,
				visitCode);
		BenMenstrualDetails menstrualHistoryDetails = BenMenstrualDetails.getBenMenstrualDetails(menstrualHistory);
		if (menstrualHistoryDetails != null) {
			// CRs changes, 30-10-2018
			String problemID = menstrualHistoryDetails.getMenstrualProblemID();
			String problemName = menstrualHistoryDetails.getProblemName();

			if (problemID != null && problemName != null) {
				String[] problemIdArr = problemID.split(",");
				String[] problemNameArr = problemName.split(",");
				ArrayList<Map<String, Object>> menstrualProblemList = new ArrayList<>();
				Map<String, Object> menstrualProblemMap = null;

				if (problemIdArr.length == problemNameArr.length) {
					for (int i = 0; i < problemIdArr.length; i++) {
						menstrualProblemMap = new HashMap<String, Object>();
						menstrualProblemMap.put("menstrualProblemID", problemIdArr[i]);
						menstrualProblemMap.put("problemName", problemNameArr[i]);
						menstrualProblemList.add(menstrualProblemMap);
					}
				}
				menstrualHistoryDetails.setMenstrualProblemList(menstrualProblemList);
			}
		}

		return menstrualHistoryDetails;
	}

	public WrapperFemaleObstetricHistory getFemaleObstetricHistory(Long beneficiaryRegID, Long visitCode) {
		ArrayList<Object[]> femaleObstetricHistory = femaleObstetricHistoryRepo
				.getBenFemaleObstetricHistoryDetail(beneficiaryRegID, visitCode);
		WrapperFemaleObstetricHistory femaleObstetricHistoryDetails = WrapperFemaleObstetricHistory
				.getFemaleObstetricHistory(femaleObstetricHistory);
		femaleObstetricHistoryDetails = getWrapperFemaleObstetricHistory(femaleObstetricHistoryDetails);

		return femaleObstetricHistoryDetails;
	}

	private WrapperFemaleObstetricHistory getWrapperFemaleObstetricHistory(
			WrapperFemaleObstetricHistory femaleObstetricHistoryDetails) {
		// s
		if (femaleObstetricHistoryDetails != null
				&& femaleObstetricHistoryDetails.getFemaleObstetricHistoryList() != null
				&& femaleObstetricHistoryDetails.getFemaleObstetricHistoryList().size() > 0) {

			String[] pregComplicationIDArr;
			String[] pregComplicationNameArr;
			String[] deliComplicationIDArr;
			String[] deliComplicationNameArr;
			String[] popaComplicationIDArr;
			String[] popaComplicationNameArr;

			ArrayList<Map<String, Object>> pregList;
			ArrayList<Map<String, Object>> deliList;
			ArrayList<Map<String, Object>> popaList;
			Map<String, Object> preMap;
			Map<String, Object> delMap;
			Map<String, Object> posMap;

			for (FemaleObstetricHistory obj : femaleObstetricHistoryDetails.getFemaleObstetricHistoryList()) {

				pregComplicationIDArr = null;
				pregComplicationNameArr = null;
				deliComplicationIDArr = null;
				deliComplicationNameArr = null;
				popaComplicationIDArr = null;
				popaComplicationNameArr = null;

				pregList = new ArrayList<>();
				deliList = new ArrayList<>();
				popaList = new ArrayList<>();

				if (obj.getPregComplicationID() != null)
					pregComplicationIDArr = obj.getPregComplicationID().split(",");
				if (obj.getPregComplicationType() != null)
					pregComplicationNameArr = obj.getPregComplicationType().split(",");
				if (obj.getPregComplicationType() != null)
					deliComplicationIDArr = obj.getDeliveryComplicationID().split(",");
				if (obj.getPregComplicationType() != null)
					deliComplicationNameArr = obj.getDeliveryComplicationType().split(",");
				if (obj.getPregComplicationType() != null)
					popaComplicationIDArr = obj.getPostpartumComplicationID().split(",");
				if (obj.getPregComplicationType() != null)
					popaComplicationNameArr = obj.getPostpartumComplicationType().split(",");

				if (pregComplicationIDArr != null && pregComplicationIDArr.length > 0 && pregComplicationNameArr != null
						&& pregComplicationNameArr.length > 0
						&& pregComplicationIDArr.length == pregComplicationNameArr.length) {

					for (int i = 0; i < pregComplicationIDArr.length; i++) {
						preMap = new HashMap<>();
						preMap.put("pregComplicationID", pregComplicationIDArr[i]);
						preMap.put("pregComplicationType", pregComplicationNameArr[i]);

						pregList.add(preMap);
					}

				}
				if (deliComplicationIDArr != null && deliComplicationIDArr.length > 0 && deliComplicationNameArr != null
						&& deliComplicationNameArr.length > 0
						&& deliComplicationIDArr.length == deliComplicationNameArr.length) {

					for (int i = 0; i < deliComplicationIDArr.length; i++) {
						delMap = new HashMap<>();
						delMap.put("deliveryComplicationID", deliComplicationIDArr[i]);
						delMap.put("deliveryComplicationType", deliComplicationNameArr[i]);

						deliList.add(delMap);
					}

				}
				if (popaComplicationIDArr != null && popaComplicationIDArr.length > 0 && popaComplicationNameArr != null
						&& popaComplicationNameArr.length > 0
						&& popaComplicationIDArr.length == popaComplicationNameArr.length) {

					for (int i = 0; i < popaComplicationIDArr.length; i++) {
						posMap = new HashMap<>();
						posMap.put("postpartumComplicationID", popaComplicationIDArr[i]);
						posMap.put("postpartumComplicationType", popaComplicationNameArr[i]);

						popaList.add(posMap);
					}

				}

				if (obj.getPostAbortionComplication_db() != null) {
					ArrayList<Map<String, Object>> postAborCompList = new ArrayList<>();
					Map<String, Object> postAborCompMap;

					String[] idArr = obj.getPostAbortionComplication_db().split(",");
					String[] valArr = obj.getPostAbortionComplicationsValues().split(",");

					for (int i = 0; i < idArr.length; i++) {
						postAborCompMap = new HashMap<>();
						postAborCompMap.put("complicationID", idArr[i]);
						postAborCompMap.put("complicationValue", valArr[i]);

						postAborCompList.add(postAborCompMap);
					}

					obj.setPostAbortionComplication(postAborCompList);
				}

				if (obj.getAbortionTypeID() != null && obj.getTypeOfAbortionValue() != null) {
					Map<String, Object> aborTypeMap = new HashMap<>();
					aborTypeMap.put("complicationID", obj.getAbortionTypeID());
					aborTypeMap.put("complicationValue", obj.getTypeOfAbortionValue());

					obj.setAbortionType(aborTypeMap);
				}

				if (obj.getServiceFacilityID() != null && obj.getServiceFacilityValue() != null) {
					Map<String, Object> serFacilityMap = new HashMap<>();
					serFacilityMap.put("serviceFacilityID", obj.getAbortionTypeID());
					serFacilityMap.put("facilityName", obj.getTypeOfAbortionValue());

					obj.setTypeofFacility(serFacilityMap);
				}

				obj.setPregComplicationList(pregList);
				obj.setDeliveryComplicationList(deliList);
				obj.setPostpartumComplicationList(popaList);

			}

		}
		// e
		return femaleObstetricHistoryDetails;
	}

	public WrapperChildOptionalVaccineDetail getChildOptionalVaccineHistory(Long beneficiaryRegID, Long visitCode) {
		ArrayList<Object[]> childOptionalVaccineDetail = childOptionalVaccineDetailRepo
				.getBenOptionalVaccineDetail(beneficiaryRegID, visitCode);
		WrapperChildOptionalVaccineDetail childOptionalVaccineDetails = WrapperChildOptionalVaccineDetail
				.getChildOptionalVaccineDetail(childOptionalVaccineDetail);

		return childOptionalVaccineDetails;
	}

	public WrapperImmunizationHistory getImmunizationHistory(Long beneficiaryRegID, Long visitCode) {
		ArrayList<Object[]> childVaccineDetail;
		if (visitCode == null)
			childVaccineDetail = childVaccineDetail1Repo.getBenChildVaccineDetailsByRegID(beneficiaryRegID);
		else
			childVaccineDetail = childVaccineDetail1Repo.getBenChildVaccineDetails(beneficiaryRegID, visitCode);
		WrapperImmunizationHistory childVaccineDetails = WrapperImmunizationHistory
				.getChildVaccineDetail(childVaccineDetail);

		return childVaccineDetails;
	}

	public PhyGeneralExamination getGeneralExaminationData(Long benRegID, Long visitCode) {
		PhyGeneralExamination phyGeneralExaminationData = phyGeneralExaminationRepo
				.getPhyGeneralExaminationData(benRegID, visitCode);
		if (null != phyGeneralExaminationData) {
			String dSign = phyGeneralExaminationData.getTypeOfDangerSign();
			if (dSign != null && dSign.length() > 0) {
				String[] typeDangerSignArr = dSign.split(",");
				if (typeDangerSignArr != null && typeDangerSignArr.length > 0) {
					ArrayList<String> typeOfDangerSigns = new ArrayList<>();
					for (String typeDangerSign : typeDangerSignArr) {
						typeOfDangerSigns.add(typeDangerSign);
					}
					phyGeneralExaminationData.setTypeOfDangerSigns(typeOfDangerSigns);
				}
			} else {
				ArrayList<String> typeOfDangerSignsTmp = new ArrayList<>();
				phyGeneralExaminationData.setTypeOfDangerSigns(typeOfDangerSignsTmp);
			}

		}
		return phyGeneralExaminationData;

	}

	public PhyHeadToToeExamination getHeadToToeExaminationData(Long benRegID, Long visitCode) {
		PhyHeadToToeExamination phyHeadToToeExaminationData = phyHeadToToeExaminationRepo
				.getPhyHeadToToeExaminationData(benRegID, visitCode);

		return phyHeadToToeExaminationData;

	}

	public SysGastrointestinalExamination getSysGastrointestinalExamination(Long benRegID, Long visitCode) {
		SysGastrointestinalExamination sysGastrointestinalExaminationData = sysGastrointestinalExaminationRepo
				.getSSysGastrointestinalExamination(benRegID, visitCode);

		return sysGastrointestinalExaminationData;
	}

	public SysCardiovascularExamination getCardiovascularExamination(Long benRegID, Long visitCode) {
		SysCardiovascularExamination sysCardiovascularExaminationData = sysCardiovascularExaminationRepo
				.getSysCardiovascularExaminationData(benRegID, visitCode);

		return sysCardiovascularExaminationData;
	}

	public SysRespiratoryExamination getRespiratoryExamination(Long benRegID, Long visitCode) {
		SysRespiratoryExamination sysRespiratoryExaminationData = sysRespiratoryExaminationRepo
				.getSysRespiratoryExaminationData(benRegID, visitCode);

		return sysRespiratoryExaminationData;
	}

	public SysCentralNervousExamination getSysCentralNervousExamination(Long benRegID, Long visitCode) {
		SysCentralNervousExamination sysCentralNervousExaminationData = sysCentralNervousExaminationRepo
				.getSysCentralNervousExaminationData(benRegID, visitCode);

		return sysCentralNervousExaminationData;
	}

	public SysMusculoskeletalSystemExamination getMusculoskeletalExamination(Long benRegID, Long visitCode) {
		SysMusculoskeletalSystemExamination sysMusculoskeletalSystemExaminationData = sysMusculoskeletalSystemExaminationRepo
				.getSysMusculoskeletalSystemExamination(benRegID, visitCode);

		return sysMusculoskeletalSystemExaminationData;
	}

	public SysGenitourinarySystemExamination getGenitourinaryExamination(Long benRegID, Long visitCode) {
		SysGenitourinarySystemExamination sysGenitourinarySystemExaminationData = sysGenitourinarySystemExaminationRepo
				.getSysGenitourinarySystemExaminationData(benRegID, visitCode);

		return sysGenitourinarySystemExaminationData;
	}

	public OralExamination getOralExamination(Long benRegID, Long visitCode) throws IEMRException {
		OralExamination oralExamination = oralExaminationRepo.findByBeneficiaryRegIDAndVisitCodeAndDeleted(benRegID,
				visitCode, false);
		if (oralExamination != null && oralExamination.getPreMalignantLesionType() != null)
			oralExamination.setPreMalignantLesionTypeList(oralExamination.getPreMalignantLesionType().split("\\|\\|"));

		return oralExamination;
	}

	//// get service over
// -----------------------------------------
	public int updateBenChiefComplaints(List<BenChiefComplaint> benChiefComplaintList) {
		int r = 0;
		if (null != benChiefComplaintList && benChiefComplaintList.size() > 0) {
			benChiefComplaintRepo.deleteExistingBenChiefComplaints(benChiefComplaintList.get(0).getBeneficiaryRegID(),
					benChiefComplaintList.get(0).getVisitCode());

			List<BenChiefComplaint> benChiefComplaintResultList = (List<BenChiefComplaint>) benChiefComplaintRepo
					.saveAll(benChiefComplaintList);

			if (benChiefComplaintResultList != null && benChiefComplaintResultList.size() > 0) {
				r = benChiefComplaintResultList.size();
			}
		}
		return r;
	}

	public int updateBenPastHistoryDetails(BenMedHistory benMedHistory) throws ParseException, IEMRException {
		Integer r = 0;
		int delRes = 0;
		if (null != benMedHistory) {
			// Delete Existing past History of beneficiary before inserting
			// updated history
			ArrayList<Object[]> benMedHistoryStatuses = benMedHistoryRepo
					.getBenMedHistoryStatus(benMedHistory.getBeneficiaryRegID(), benMedHistory.getVisitCode());

			for (Object[] obj : benMedHistoryStatuses) {
				String processed = (String) obj[1];
				if (null != processed && !"N".equals(processed)) {
					processed = "U";
				} else {
					processed = "N";
				}
				delRes = benMedHistoryRepo.deleteExistingBenMedHistory((Long) obj[0], processed);

			}

			ArrayList<BenMedHistory> benMedHistoryList = benMedHistory.getBenPastHistory();
			if (null != benMedHistoryList && benMedHistoryList.size() > 0) {
				ArrayList<BenMedHistory> res = (ArrayList<BenMedHistory>) benMedHistoryRepo.saveAll(benMedHistoryList);
				if (benMedHistoryList.size() == res.size()) {
					r = 1;
				}
			} else {
				r = 1;
			}
		}
		return r;
	}

	public int updateBenComorbidConditions(WrapperComorbidCondDetails wrapperComorbidCondDetails) throws IEMRException {
		int r = 0;
		int delRes = 0;
		if (null != wrapperComorbidCondDetails) {

			ArrayList<Object[]> benComorbidCondHistoryStatuses = bencomrbidityCondRepo
					.getBenComrbidityCondHistoryStatus(wrapperComorbidCondDetails.getBeneficiaryRegID(),
							wrapperComorbidCondDetails.getVisitCode());

			for (Object[] obj : benComorbidCondHistoryStatuses) {
				String processed = (String) obj[1];
				if (null != processed && !"N".equals(processed)) {
					processed = "U";
				} else {
					processed = "N";
				}
				delRes = bencomrbidityCondRepo.deleteExistingBenComrbidityCondDetails((Long) obj[0], processed);

			}

			ArrayList<BencomrbidityCondDetails> bencomrbidityCondDetailsList = wrapperComorbidCondDetails
					.getComrbidityConds();
			if (null != bencomrbidityCondDetailsList && bencomrbidityCondDetailsList.size() > 0) {
				ArrayList<BencomrbidityCondDetails> res = (ArrayList<BencomrbidityCondDetails>) bencomrbidityCondRepo
						.saveAll(bencomrbidityCondDetailsList);
				if (bencomrbidityCondDetailsList.size() == res.size()) {
					r = 1;
				}
			} else {
				r = 1;
			}
		}
		return r;
	}

	public int updateBenMedicationHistory(WrapperMedicationHistory wrapperMedicationHistory) throws IEMRException {
		Integer r = 0;
		int delRes = 0;
		if (null != wrapperMedicationHistory) {

			ArrayList<Object[]> benMedicationHistoryStatuses = benMedicationHistoryRepo.getBenMedicationHistoryStatus(
					wrapperMedicationHistory.getBeneficiaryRegID(), wrapperMedicationHistory.getVisitCode());

			for (Object[] obj : benMedicationHistoryStatuses) {
				String processed = (String) obj[1];
				if (null != processed && !"N".equals(processed)) {
					processed = "U";
				} else {
					processed = "N";
				}
				delRes = benMedicationHistoryRepo.deleteExistingBenMedicationHistory((Long) obj[0], processed);

			}

			ArrayList<BenMedicationHistory> benMedicationHistoryList = wrapperMedicationHistory
					.getBenMedicationHistoryDetails();
			if (null != benMedicationHistoryList && benMedicationHistoryList.size() > 0) {
				ArrayList<BenMedicationHistory> res = (ArrayList<BenMedicationHistory>) benMedicationHistoryRepo
						.saveAll(benMedicationHistoryList);
				if (benMedicationHistoryList.size() == res.size()) {
					r = 1;
				}
			} else {
				r = 1;
			}
		}
		return r;
	}

	public int updateBenPersonalHistory(BenPersonalHabit benPersonalHabit) throws IEMRException {
		Integer r = 0;
		int delRes = 0;
		if (null != benPersonalHabit) {

			ArrayList<Object[]> benPersonalHistoryStatuses = benPersonalHabitRepo.getBenPersonalHistoryStatus(
					benPersonalHabit.getBeneficiaryRegID(), benPersonalHabit.getVisitCode());

			for (Object[] obj : benPersonalHistoryStatuses) {
				String processed = (String) obj[1];
				if (null != processed && !"N".equals(processed)) {
					processed = "U";
				} else {
					processed = "N";
				}
				delRes = benPersonalHabitRepo.deleteExistingBenPersonalHistory((Integer) obj[0], processed);

			}

			ArrayList<BenPersonalHabit> personalHabits = benPersonalHabit.getPersonalHistory();
			if (null != personalHabits && personalHabits.size() > 0) {
				ArrayList<BenPersonalHabit> res = (ArrayList<BenPersonalHabit>) benPersonalHabitRepo
						.saveAll(personalHabits);
				if (personalHabits.size() > 0) {
					r = 1;
				}
			} else {
				r = 1;
			}
		}
		return r;
	}

	public int updateBenAllergicHistory(BenAllergyHistory benAllergyHistory) throws IEMRException {
		Integer r = 0;
		int delRes = 0;

		if (null != benAllergyHistory) {

			ArrayList<Object[]> benAllergyHistoryStatuses = benAllergyHistoryRepo.getBenAllergyHistoryStatus(
					benAllergyHistory.getBeneficiaryRegID(), benAllergyHistory.getVisitCode());

			for (Object[] obj : benAllergyHistoryStatuses) {
				String processed = (String) obj[1];
				if (null != processed && !"N".equals(processed)) {
					processed = "U";
				} else {
					processed = "N";
				}
				delRes = benAllergyHistoryRepo.deleteExistingBenAllergyHistory((Long) obj[0], processed);
			}

			ArrayList<BenAllergyHistory> allergyList = benAllergyHistory.getBenAllergicHistory();
			if (null != allergyList && allergyList.size() > 0) {
				ArrayList<BenAllergyHistory> res = (ArrayList<BenAllergyHistory>) benAllergyHistoryRepo
						.saveAll(allergyList);
				if (allergyList.size() == res.size()) {
					r = 1;
				}
			} else {
				r = 1;
			}
		}
		return r;
	}

	public int updateBenFamilyHistory(BenFamilyHistory benFamilyHistory) throws IEMRException {
		Integer r = 0;
		int delRes = 0;
		if (null != benFamilyHistory) {

			ArrayList<Object[]> benFamilyHistoryStatuses = benFamilyHistoryRepo
					.getBenFamilyHistoryStatus(benFamilyHistory.getBeneficiaryRegID(), benFamilyHistory.getVisitCode());

			for (Object[] obj : benFamilyHistoryStatuses) {
				String processed = (String) obj[1];
				if (null != processed && !"N".equals(processed)) {
					processed = "U";
				} else {
					processed = "N";
				}
				delRes = benFamilyHistoryRepo.deleteExistingBenFamilyHistory((Long) obj[0], processed);
			}

			ArrayList<BenFamilyHistory> familyHistoryList = benFamilyHistory.getBenFamilyHistory();
			if (null != familyHistoryList && familyHistoryList.size() > 0) {
				ArrayList<BenFamilyHistory> res = (ArrayList<BenFamilyHistory>) benFamilyHistoryRepo
						.saveAll(familyHistoryList);
				if (familyHistoryList.size() == res.size()) {
					r = 1;
				}
			} else {
				r = 1;
			}
		}
		return r;
	}

	public int updateMenstrualHistory(BenMenstrualDetails benMenstrualDetails) throws IEMRException {
		int response = 0;
		int recordsAvailable = 0;
		if (null != benMenstrualDetails) {
			String processed = benMenstrualDetailsRepo.getBenMenstrualDetailStatus(
					benMenstrualDetails.getBeneficiaryRegID(), benMenstrualDetails.getVisitCode());
			if (processed != null) {
				recordsAvailable = 1;
			}
			if (null != processed && !"N".equals(processed)) {
				processed = "U";
			} else {
				processed = "N";
			}

			ArrayList<Map<String, Object>> menstrualProblemList = benMenstrualDetails.getMenstrualProblemList();
			if (menstrualProblemList != null && menstrualProblemList.size() > 0) {
				StringBuilder problemID = new StringBuilder();
				StringBuilder problemName = new StringBuilder();

				for (int i = 0; i < menstrualProblemList.size(); i++) {
					if (i == (menstrualProblemList.size() - 1)) {
						problemID.append(menstrualProblemList.get(i).get("menstrualProblemID"));
						problemName.append(menstrualProblemList.get(i).get("problemName"));
					} else {
						problemID.append(menstrualProblemList.get(i).get("menstrualProblemID")).append(",");
						problemName.append(menstrualProblemList.get(i).get("problemName")).append(",");
					}
				}
				benMenstrualDetails.setMenstrualProblemID(problemID.toString());
				benMenstrualDetails.setProblemName(problemName.toString());
			}

			if (recordsAvailable > 0) {
				response = benMenstrualDetailsRepo.updateMenstrualDetails(
						benMenstrualDetails.getMenstrualCycleStatusID(), benMenstrualDetails.getMenstrualCycleStatus(),
						benMenstrualDetails.getRegularity(), benMenstrualDetails.getMenstrualCyclelengthID(),
						benMenstrualDetails.getCycleLength(), benMenstrualDetails.getMenstrualFlowDurationID(),
						benMenstrualDetails.getBloodFlowDuration(), benMenstrualDetails.getMenstrualProblemID(),
						benMenstrualDetails.getProblemName(), benMenstrualDetails.getlMPDate(),
						benMenstrualDetails.getModifiedBy(), processed, benMenstrualDetails.getBeneficiaryRegID(),
						benMenstrualDetails.getVisitCode());
			} else {
				benMenstrualDetails.setCreatedBy(benMenstrualDetails.getModifiedBy());
				BenMenstrualDetails menstrualDetails = benMenstrualDetailsRepo.save(benMenstrualDetails);
				if (null != menstrualDetails && menstrualDetails.getBenMenstrualID() > 0) {
					response = 1;
				}
			}
		}
		return response;
	}

	public int updatePastObstetricHistory(WrapperFemaleObstetricHistory wrapperFemaleObstetricHistory)
			throws IEMRException {
		Integer r = 0;
		int delRes = 0;
		if (null != wrapperFemaleObstetricHistory) {
			ArrayList<Object[]> benObstetricHistoryStatuses = femaleObstetricHistoryRepo.getBenObstetricHistoryStatus(
					wrapperFemaleObstetricHistory.getBeneficiaryRegID(), wrapperFemaleObstetricHistory.getVisitCode());

			for (Object[] obj : benObstetricHistoryStatuses) {
				String processed = (String) obj[1];
				if (null != processed && !"N".equals(processed)) {
					processed = "U";
				} else {
					processed = "N";
				}
				delRes = femaleObstetricHistoryRepo.deleteExistingObstetricHistory((Long) obj[0], processed);
			}

			ArrayList<FemaleObstetricHistory> femaleObstetricHistorylist = getFemaleObstetricHistoryObj(
					wrapperFemaleObstetricHistory.getFemaleObstetricHistoryDetails());

			ArrayList<FemaleObstetricHistory> res = (ArrayList<FemaleObstetricHistory>) femaleObstetricHistoryRepo
					.saveAll(femaleObstetricHistorylist);

			if (femaleObstetricHistorylist.size() == res.size()) {
				r = 1;
			}
		}
		return r;
	}

	public int updateChildOptionalVaccineDetail(WrapperChildOptionalVaccineDetail wrapperChildOptionalVaccineDetail)
			throws IEMRException {
		Integer r = 0;
		int delRes = 0;
		if (null != wrapperChildOptionalVaccineDetail) {

			ArrayList<Object[]> benChildOptionalVaccineHistoryStatuses = childOptionalVaccineDetailRepo
					.getBenChildOptionalVaccineHistoryStatus(wrapperChildOptionalVaccineDetail.getBeneficiaryRegID(),
							wrapperChildOptionalVaccineDetail.getVisitCode());

			for (Object[] obj : benChildOptionalVaccineHistoryStatuses) {
				String processed = (String) obj[1];
				if (null != processed && !"N".equals(processed)) {
					processed = "U";
				} else {
					processed = "N";
				}
				delRes = childOptionalVaccineDetailRepo.deleteExistingChildOptionalVaccineDetail((Long) obj[0],
						processed);
			}

			ArrayList<ChildOptionalVaccineDetail> childOptionalVaccineDetails = wrapperChildOptionalVaccineDetail
					.getChildOptionalVaccineDetails();
			if (null != childOptionalVaccineDetails && childOptionalVaccineDetails.size() > 0) {
				ArrayList<ChildOptionalVaccineDetail> res = (ArrayList<ChildOptionalVaccineDetail>) childOptionalVaccineDetailRepo
						.saveAll(childOptionalVaccineDetails);
				if (childOptionalVaccineDetails.size() == res.size()) {
					r = 1;
				}
			} else {
				r = 1;
			}
		}
		return r;
	}

	public int updateChildImmunizationDetail(WrapperImmunizationHistory wrapperImmunizationHistory)
			throws IEMRException {
		Integer r = 0;

		ArrayList<ChildVaccineDetail1> childVaccineDetails = wrapperImmunizationHistory.getBenChildVaccineDetails();

		if (null != childVaccineDetails) {
			String processed = "N";
			ChildVaccineDetail1 childVaccine = childVaccineDetails.get(0);
			ArrayList<Object[]> childVaccineStatuses = childVaccineDetail1Repo
					.getBenChildVaccineDetailStatus(childVaccine.getBeneficiaryRegID(), childVaccine.getVisitCode());

			Map<String, String> vaccineStatuses = new HashMap<String, String>();

			for (Object[] obj : childVaccineStatuses) {
				vaccineStatuses.put((String) obj[0] + "-" + (String) obj[1], (String) obj[2]);
			}

			for (ChildVaccineDetail1 childVaccineDetail : childVaccineDetails) {

				processed = vaccineStatuses
						.get(childVaccineDetail.getDefaultReceivingAge() + "-" + childVaccineDetail.getVaccineName());
				if (null != processed && !processed.equals("N")) {
					processed = "U";
				} else {
					processed = "N";
				}
				r = childVaccineDetail1Repo.updateChildANCImmunization(childVaccineDetail.getStatus(),
						childVaccineDetail.getModifiedBy(), processed, childVaccineDetail.getBeneficiaryRegID(),
						childVaccineDetail.getVisitCode(), childVaccineDetail.getDefaultReceivingAge(),
						childVaccineDetail.getVaccineName(), childVaccineDetail.getSctCode(),
						childVaccineDetail.getSctTerm(), childVaccineDetail.getVaccinationReceivedAt());

				if (r == 0) {
					if (childVaccineDetail.getCreatedBy() == null && childVaccineDetail.getModifiedBy() != null)
						childVaccineDetail.setCreatedBy(childVaccineDetail.getModifiedBy());
					childVaccineDetail1Repo.save(childVaccineDetail);

				}
			}
		}
		return r;
	}

	public int updatePhyGeneralExamination(PhyGeneralExamination generalExamination) throws IEMRException {
		int response = 0;
		String TypeOfDangerSigns = "";

		if (null != generalExamination) {

			String processed = phyGeneralExaminationRepo.getBenGeneralExaminationStatus(
					generalExamination.getBeneficiaryRegID(), generalExamination.getVisitCode());
			if (null != processed && !"N".equals(processed)) {
				processed = "U";
			} else {
				processed = "N";
			}

			if (null != generalExamination.getTypeOfDangerSigns()
					&& generalExamination.getTypeOfDangerSigns().size() > 0) {
				for (String TypeOfDangerSign : generalExamination.getTypeOfDangerSigns()) {
					TypeOfDangerSigns += TypeOfDangerSign + ",";
				}
				generalExamination.setTypeOfDangerSign(TypeOfDangerSigns);
			}

			response = phyGeneralExaminationRepo.updatePhyGeneralExamination(generalExamination.getConsciousness(),
					generalExamination.getCoherence(), generalExamination.getCooperation(),
					generalExamination.getComfortness(), generalExamination.getBuiltAndAppearance(),
					generalExamination.getGait(), generalExamination.getDangerSigns(),
					generalExamination.getTypeOfDangerSign(), generalExamination.getPallor(),
					generalExamination.getJaundice(), generalExamination.getCyanosis(),
					generalExamination.getClubbing(), generalExamination.getLymphadenopathy(),
					generalExamination.getLymphnodesInvolved(), generalExamination.getTypeOfLymphadenopathy(),
					generalExamination.getEdema(), generalExamination.getExtentOfEdema(),
					generalExamination.getEdemaType(), generalExamination.getQuickening(),
					generalExamination.getFoetalMovements(), generalExamination.getModifiedBy(), processed,
					generalExamination.getBeneficiaryRegID(), generalExamination.getVisitCode());
		}
		return response;
	}

	public int updatePhyHeadToToeExamination(PhyHeadToToeExamination headToToeExamination) throws IEMRException {
		int response = 0;
		if (null != headToToeExamination) {
			String processed = phyHeadToToeExaminationRepo.getBenHeadToToeExaminationStatus(
					headToToeExamination.getBeneficiaryRegID(), headToToeExamination.getVisitCode());
			if (null != processed && !"N".equals(processed)) {
				processed = "U";
			} else {
				processed = "N";
			}
			response = phyHeadToToeExaminationRepo.updatePhyHeadToToeExamination(
					headToToeExamination.getHeadtoToeExam(), headToToeExamination.getHead(),
					headToToeExamination.getEyes(), headToToeExamination.getEars(), headToToeExamination.getNose(),
					headToToeExamination.getOralCavity(), headToToeExamination.getThroat(),
					headToToeExamination.getBreastAndNipples(), headToToeExamination.getTrunk(),
					headToToeExamination.getUpperLimbs(), headToToeExamination.getLowerLimbs(),
					headToToeExamination.getSkin(), headToToeExamination.getHair(), headToToeExamination.getNails(),
					headToToeExamination.getModifiedBy(), processed, headToToeExamination.getBeneficiaryRegID(),
					headToToeExamination.getVisitCode(), headToToeExamination.getNipples());
		}
		return response;
	}

	public int updateSysCardiovascularExamination(SysCardiovascularExamination cardiovascular) throws IEMRException {
		int response = 0;
		if (null != cardiovascular) {
			String processed = sysCardiovascularExaminationRepo.getBenCardiovascularExaminationStatus(
					cardiovascular.getBeneficiaryRegID(), cardiovascular.getVisitCode());
			if (null != processed && !processed.equals("N")) {
				processed = "U";
			}
			response = sysCardiovascularExaminationRepo.updateSysCardiovascularExamination(
					cardiovascular.getJugularVenousPulse_JVP(), cardiovascular.getApexbeatLocation(),
					cardiovascular.getApexbeatType(), cardiovascular.getFirstHeartSound_S1(),
					cardiovascular.getSecondHeartSound_S2(), cardiovascular.getAdditionalHeartSounds(),
					cardiovascular.getMurmurs(), cardiovascular.getPericardialRub(), cardiovascular.getModifiedBy(),
					processed, cardiovascular.getBeneficiaryRegID(), cardiovascular.getVisitCode());
		}
		return response;
	}

	public int updateSysRespiratoryExamination(SysRespiratoryExamination respiratory) throws IEMRException {
		int r = 0;
		if (null != respiratory) {
			String processed = sysRespiratoryExaminationRepo
					.getBenRespiratoryExaminationStatus(respiratory.getBeneficiaryRegID(), respiratory.getVisitCode());
			if (null != processed && !"N".equals(processed)) {
				processed = "U";
			} else {
				processed = "N";
			}
			r = sysRespiratoryExaminationRepo.updateSysRespiratoryExamination(respiratory.getTrachea(),
					respiratory.getInspection(), respiratory.getSignsOfRespiratoryDistress(),
					respiratory.getPalpation(), respiratory.getAuscultation(), respiratory.getAuscultation_Stridor(),
					respiratory.getAuscultation_BreathSounds(), respiratory.getAuscultation_Crepitations(),
					respiratory.getAuscultation_Wheezing(), respiratory.getAuscultation_PleuralRub(),
					respiratory.getAuscultation_ConductedSounds(), respiratory.getPercussion(),
					respiratory.getModifiedBy(), processed, respiratory.getBeneficiaryRegID(),
					respiratory.getVisitCode());
		}
		return r;
	}

	public int updateSysCentralNervousExamination(SysCentralNervousExamination centralNervous) throws IEMRException {
		int r = 0;
		if (null != centralNervous) {
			String processed = sysCentralNervousExaminationRepo.getBenCentralNervousExaminationStatus(
					centralNervous.getBeneficiaryRegID(), centralNervous.getVisitCode());
			if (null != processed && !"N".equals(processed)) {
				processed = "U";
			} else {
				processed = "N";
			}
			r = sysCentralNervousExaminationRepo.updateSysCentralNervousExamination(centralNervous.getHandedness(),
					centralNervous.getCranialNervesExamination(), centralNervous.getMotorSystem(),
					centralNervous.getSensorySystem(), centralNervous.getAutonomicSystem(),
					centralNervous.getCerebellarSigns(), centralNervous.getSignsOfMeningealIrritation(),
					centralNervous.getSkull(), centralNervous.getModifiedBy(), processed,
					centralNervous.getBeneficiaryRegID(), centralNervous.getVisitCode());
		}

		return r;
	}

	public int updateSysMusculoskeletalSystemExamination(SysMusculoskeletalSystemExamination musculoskeletalSystem)
			throws IEMRException {
		int r = 0;
		if (null != musculoskeletalSystem) {
			String processed = sysMusculoskeletalSystemExaminationRepo.getBenMusculoskeletalSystemExaminationStatus(
					musculoskeletalSystem.getBeneficiaryRegID(), musculoskeletalSystem.getVisitCode());
			if (null != processed && !"N".equals(processed)) {
				processed = "U";
			} else {
				processed = "N";
			}
			r = sysMusculoskeletalSystemExaminationRepo.updateSysMusculoskeletalSystemExamination(
					musculoskeletalSystem.getJoint_TypeOfJoint(), musculoskeletalSystem.getJoint_Laterality(),
					musculoskeletalSystem.getJoint_Abnormality(), musculoskeletalSystem.getUpperLimb_Laterality(),
					musculoskeletalSystem.getUpperLimb_Abnormality(), musculoskeletalSystem.getLowerLimb_Laterality(),
					musculoskeletalSystem.getLowerLimb_Abnormality(), musculoskeletalSystem.getChestWall(),
					musculoskeletalSystem.getSpine(), musculoskeletalSystem.getModifiedBy(), processed,
					musculoskeletalSystem.getBeneficiaryRegID(), musculoskeletalSystem.getVisitCode());
		}
		return r;
	}

	public int updateSysGenitourinarySystemExamination(SysGenitourinarySystemExamination genitourinarySystem)
			throws IEMRException {
		int r = 0;
		if (null != genitourinarySystem) {
			String processed = sysGenitourinarySystemExaminationRepo.getBenGenitourinarySystemExaminationStatus(
					genitourinarySystem.getBeneficiaryRegID(), genitourinarySystem.getVisitCode());
			if (null != processed && !"N".equals(processed)) {
				processed = "U";
			} else {
				processed = "N";
			}
			r = sysGenitourinarySystemExaminationRepo.updateSysGenitourinarySystemExamination(
					genitourinarySystem.getRenalAngle(), genitourinarySystem.getSuprapubicRegion(),
					genitourinarySystem.getExternalGenitalia(), genitourinarySystem.getModifiedBy(), processed,
					genitourinarySystem.getBeneficiaryRegID(), genitourinarySystem.getVisitCode());
		}
		return r;
	}

	// update oral examination
	public int updateOralExamination(OralExamination oralExamination) throws IEMRException {
		OralExamination oralExaminationRS = oralExaminationRepo.findByBeneficiaryRegIDAndVisitCodeAndDeleted(
				oralExamination.getBeneficiaryRegID(), oralExamination.getVisitCode(), false);
		if (oralExaminationRS != null) {
			oralExaminationRS.setProcessed("U");

			oralExaminationRS.setLimitedMouthOpening(oralExamination.getLimitedMouthOpening());
			oralExaminationRS.setPremalignantLesions(oralExamination.getPremalignantLesions());
			oralExaminationRS.setPreMalignantLesionType(oralExamination.getPreMalignantLesionType());
			oralExaminationRS.setProlongedIrritation(oralExamination.getProlongedIrritation());
			oralExaminationRS.setChronicBurningSensation(oralExamination.getChronicBurningSensation());
			oralExaminationRS.setObservation(oralExamination.getObservation());

			oralExaminationRS.setModifiedBy(oralExamination.getModifiedBy());

			oralExaminationRepo.save(oralExaminationRS);
		} else {
			oralExamination.setCreatedBy(oralExamination.getModifiedBy());
			oralExaminationRepo.save(oralExamination);
		}

		return 1;
	}

	public Long savePrescriptionDetailsAndGetPrescriptionID(Long benRegID, Long benVisitID, Integer psmID,
			String createdBy, String externalInvestigation, Long benVisitCode, Integer vanID, Integer parkingPlaceID,
			String instruction, String prescription_counsellingProvided,
			ArrayList<SCTDescription> provisionalDiagnosisList) {
		PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
		prescriptionDetail.setBeneficiaryRegID(benRegID);
		prescriptionDetail.setBenVisitID(benVisitID);
		prescriptionDetail.setVisitCode(benVisitCode);
		prescriptionDetail.setProviderServiceMapID(psmID);
		prescriptionDetail.setCreatedBy(createdBy);
		prescriptionDetail.setExternalInvestigation(externalInvestigation);
		prescriptionDetail.setVanID(vanID);
		prescriptionDetail.setParkingPlaceID(parkingPlaceID);

		if (instruction != null)
			prescriptionDetail.setInstruction(instruction);

		if (prescription_counsellingProvided != null)
			prescriptionDetail.setCounsellingProvided(prescription_counsellingProvided);
		if (provisionalDiagnosisList != null)
			prescriptionDetail.setProvisionalDiagnosisList(provisionalDiagnosisList);

		Long prescriptionID = saveBenPrescription(prescriptionDetail);
		return prescriptionID;
	}

	// prescription for covid diagnosis
	public Long savePrescriptionCovid(Long benRegID, Long benVisitID, Integer psmID, String createdBy,
			String externalInvestigation, Long benVisitCode, Integer vanID, Integer parkingPlaceID, String instruction,
			String doctorDiagnosis, String counsellingProvided) {
		PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
		prescriptionDetail.setBeneficiaryRegID(benRegID);
		prescriptionDetail.setBenVisitID(benVisitID);
		prescriptionDetail.setVisitCode(benVisitCode);
		prescriptionDetail.setProviderServiceMapID(psmID);
		prescriptionDetail.setCreatedBy(createdBy);
		prescriptionDetail.setExternalInvestigation(externalInvestigation);
		prescriptionDetail.setVanID(vanID);
		prescriptionDetail.setParkingPlaceID(parkingPlaceID);

		if (instruction != null)
			prescriptionDetail.setInstruction(instruction);

		if (doctorDiagnosis != null)
			prescriptionDetail.setDiagnosisProvided(doctorDiagnosis);

		prescriptionDetail.setCounsellingProvided(counsellingProvided);

		Long prescriptionID = saveBenPrescription(prescriptionDetail);

		return prescriptionID;
	}

	public Long saveBeneficiaryPrescription(JsonObject caseSheet) throws Exception {

		PrescriptionDetail prescriptionDetail = InputMapper.gson().fromJson(caseSheet, PrescriptionDetail.class);

		// String[] snomedCTArr =
		// commonDoctorServiceImpl.getSnomedCTcode(prescriptionDetail.getDiagnosisProvided());
		// if (snomedCTArr != null && snomedCTArr.length > 1) {
		// prescriptionDetail.setDiagnosisProvided_SCTCode(snomedCTArr[0]);
		// prescriptionDetail.setDiagnosisProvided_SCTTerm(snomedCTArr[1]);
		// }
		// prescriptionDetail.setPrescriptionID(null);
		return saveBenPrescription(prescriptionDetail);
	}

	public Long saveBenPrescription(PrescriptionDetail prescription) {
		Long r = null;
		prescription.setPrescriptionID(null);

		StringBuilder pdTerm = new StringBuilder();
		StringBuilder pdConceptID = new StringBuilder();

		if (prescription != null && prescription.getProvisionalDiagnosisList() != null
				&& prescription.getProvisionalDiagnosisList().size() > 0) {
			for (SCTDescription obj : prescription.getProvisionalDiagnosisList()) {
				if (obj.getTerm() != null) {
					if(pdTerm.toString().isEmpty()){
						pdTerm.append(obj.getTerm());
						if(null != obj.getConceptID()) {
							pdConceptID.append(obj.getConceptID());
						}else {
							pdConceptID.append("N/A");
						}
					}else{
						if(obj.getTerm() != null){
							pdTerm.append("  ||  ").append(obj.getTerm());
						}
						if (obj.getConceptID() != null){
							pdConceptID.append("  ||  ").append(obj.getConceptID());
						}else{
							pdConceptID.append("  ||  ").append("N/A");
						}
					}
				}
			}
			prescription.setDiagnosisProvided(pdTerm.toString());
			prescription.setDiagnosisProvided_SCTCode(pdConceptID.toString());
		}

		PrescriptionDetail prescriptionRS = prescriptionDetailRepo.save(prescription);
		if (prescriptionRS != null && prescriptionRS.getPrescriptionID() > 0) {
			r = prescriptionRS.getPrescriptionID();
		}
		return r;
	}

	public int updatePrescription(PrescriptionDetail prescription) {
		int i = 0;

		// SnomedCT new code
		StringBuilder pdTerm = new StringBuilder();
		StringBuilder pdConceptID = new StringBuilder();

		if (prescription != null && prescription.getProvisionalDiagnosisList() != null
				&& prescription.getProvisionalDiagnosisList().size() > 0) {
			int pointer = 1;
			for (SCTDescription obj : prescription.getProvisionalDiagnosisList()) {
				if (obj.getTerm() != null) {
					if (pointer == prescription.getProvisionalDiagnosisList().size()) {
						pdTerm.append(obj.getTerm());
						if (obj.getConceptID() != null)
							pdConceptID.append(obj.getConceptID());
						else
							pdConceptID.append("N/A");
					} else {
						pdTerm.append(obj.getTerm()).append("  ||  ");
						if (obj.getConceptID() != null)
							pdConceptID.append(obj.getConceptID()).append("  ||  ");
						else
							pdConceptID.append("N/A").append("  ||  ");
					}

				}
				pointer++;
			}
			prescription.setDiagnosisProvided(pdTerm.toString());
			prescription.setDiagnosisProvided_SCTCode(pdConceptID.toString());
			// prescription.setDiagnosisProvided_SCTTerm(pdTerm.toString());

		}

//		String processed = prescriptionDetailRepo.getGeneralOPDDiagnosisStatus(prescription.getBeneficiaryRegID(),
//				prescription.getVisitCode(), prescription.getPrescriptionID());
//		if (null != processed && !processed.equals("N")) {
//			processed = "U";
//		}

		String processed;
		PrescriptionDetail pDetails = prescriptionDetailRepo.getGeneralOPDDiagnosisStatus(
				prescription.getBeneficiaryRegID(), prescription.getVisitCode(), prescription.getPrescriptionID());
		if (null != pDetails && !pDetails.getProcessed().equals("N")) {
			processed = "U";
		} else {
			if (pDetails != null)
				processed = pDetails.getProcessed();
			else
				processed = "N";
		}

		prescription.setProcessed(processed);
//		if (pDetails.getInstruction() != null) {
//			prescription.setInstruction(pDetails.getInstruction());}
//		
		if (pDetails != null && prescription.getInstruction() == null && pDetails.getInstruction() != null) {
			prescription.setInstruction(pDetails.getInstruction());
		}
		if (pDetails != null && prescription.getDiagnosisProvided() == null
				&& pDetails.getDiagnosisProvided() != null) {
			prescription.setDiagnosisProvided(pDetails.getDiagnosisProvided());
		}

		PrescriptionDetail resultSet = prescriptionDetailRepo.save(prescription);
		if (resultSet != null && resultSet.getPrescriptionID() > 0)
			i = 1;

		return i;
	}

	public Long saveBeneficiaryLabTestOrderDetails(JsonObject caseSheet, Long prescriptionID) {
		Long returnOBJ = null;
		ArrayList<LabTestOrderDetail> labTestOrderDetails = LabTestOrderDetail.getLabTestOrderDetailList(caseSheet,
				prescriptionID);

		if (labTestOrderDetails != null && labTestOrderDetails.size() > 0) {
			List<LabTestOrderDetail> labTestOrders = (List<LabTestOrderDetail>) labTestOrderDetailRepo
					.saveAll(labTestOrderDetails);
			if (labTestOrderDetails.size() == labTestOrders.size()) {
				returnOBJ = new Long(1);
			}
		} else {
			returnOBJ = new Long(1);
		}

		return returnOBJ;
	}

	public Integer saveBenPrescribedDrugsList(List<PrescribedDrugDetail> prescribedDrugDetailList) {
		Integer r = 0;

		if (prescribedDrugDetailList.size() > 0) {
			for (PrescribedDrugDetail obj : prescribedDrugDetailList) {
				if (obj.getFormName().equalsIgnoreCase("Tablet") || obj.getFormName().equalsIgnoreCase("Capsule")) {
					int qtyPrescribed = calculateQtyPrescribed(obj.getFormName(), obj.getDose(), obj.getFrequency(),
							obj.getDuration(), obj.getUnit());

					obj.setQtyPrescribed(qtyPrescribed);
				}
			}
			List<PrescribedDrugDetail> prescribedDrugDetailListRS = (List<PrescribedDrugDetail>) prescribedDrugDetailRepo
					.saveAll(prescribedDrugDetailList);
			if (prescribedDrugDetailList.size() == prescribedDrugDetailListRS.size()) {
				r = prescribedDrugDetailListRS.size();
			}
		} else {
			r = 1;
		}
		return r;
	}

	private int calculateQtyPrescribed(String form, String dose, String frequency, String duration,
			String durationUnit) {
		int qtyPrescribed = 0;

		if (form != null && dose != null && frequency != null && duration != null && durationUnit != null) {
			double qtyInOneDay = getQtyForOneDay(form, dose, frequency);

			if (frequency.equalsIgnoreCase("Single Dose") || frequency.equalsIgnoreCase("Stat Dose")) {
				qtyPrescribed = (int) Math.ceil(qtyInOneDay);
			} else {
				if (durationUnit.equalsIgnoreCase("Day(s)"))
					qtyPrescribed = (int) Math.ceil(Integer.parseInt(duration) * qtyInOneDay);
				else if (durationUnit.equalsIgnoreCase("Week(s)"))
					qtyPrescribed = (int) Math.ceil(Integer.parseInt(duration) * 7 * qtyInOneDay);
				else if (durationUnit.equalsIgnoreCase("Month(s)"))
					qtyPrescribed = (int) Math.ceil(Integer.parseInt(duration) * 30 * qtyInOneDay);
			}
		}

		return qtyPrescribed;

	}

	private double getQtyForOneDay(String form, String dose, String frequency) {
		double qtyInOneDay = 0;
		if (form != null && dose != null && frequency != null) {
			if (frequency.equalsIgnoreCase("Once Daily(OD)")) {
				if (form.equalsIgnoreCase("Tablet")) {
					if (dose.equalsIgnoreCase("Half Tab")) {
						qtyInOneDay = .5;
					} else {
						if (dose.equalsIgnoreCase("One Tab")) {
							qtyInOneDay = 1;
						} else {
							if (dose.equalsIgnoreCase("One & Half Tab")) {
								qtyInOneDay = 1.5;
							} else {
								if (dose.equalsIgnoreCase("Two Tabs")) {
									qtyInOneDay = 2;
								}
							}
						}

					}
				} else {
					if (form.equalsIgnoreCase("Capsule")) {
						qtyInOneDay = 1;
					}
				}
			} else {
				if (frequency.equalsIgnoreCase("Twice Daily(BD)")) {
					if (form.equalsIgnoreCase("Tablet")) {
						if (dose.equalsIgnoreCase("Half Tab")) {
							qtyInOneDay = 1;
						} else {
							if (dose.equalsIgnoreCase("One Tab")) {
								qtyInOneDay = 2;
							} else {
								if (dose.equalsIgnoreCase("One & Half Tab")) {
									qtyInOneDay = 3;
								} else {
									if (dose.equalsIgnoreCase("Two Tabs")) {
										qtyInOneDay = 4;
									}
								}
							}

						}
					} else {
						if (form.equalsIgnoreCase("Capsule")) {
							qtyInOneDay = 2;
						}
					}
				} else {
					if (frequency.equalsIgnoreCase("Thrice Daily (TID)")) {
						if (form.equalsIgnoreCase("Tablet")) {
							if (dose.equalsIgnoreCase("Half Tab")) {
								qtyInOneDay = 1.5;
							} else {
								if (dose.equalsIgnoreCase("One Tab")) {
									qtyInOneDay = 3;
								} else {
									if (dose.equalsIgnoreCase("One & Half Tab")) {
										qtyInOneDay = 4.5;
									} else {
										if (dose.equalsIgnoreCase("Two Tabs")) {
											qtyInOneDay = 6;
										}
									}
								}

							}
						} else {
							if (form.equalsIgnoreCase("Capsule")) {
								qtyInOneDay = 3;
							}
						}
					} else {
						if (frequency.equalsIgnoreCase("Four Times in a Day (QID)")) {
							if (form.equalsIgnoreCase("Tablet")) {
								if (dose.equalsIgnoreCase("Half Tab")) {
									qtyInOneDay = 2;
								} else {
									if (dose.equalsIgnoreCase("One Tab")) {
										qtyInOneDay = 4;
									} else {
										if (dose.equalsIgnoreCase("One & Half Tab")) {
											qtyInOneDay = 6;
										} else {
											if (dose.equalsIgnoreCase("Two Tabs")) {
												qtyInOneDay = 8;
											}
										}
									}

								}
							} else {
								if (form.equalsIgnoreCase("Capsule")) {
									qtyInOneDay = 4;
								}
							}
						} else {
							if (frequency.equalsIgnoreCase("Single Dose") || frequency.equalsIgnoreCase("Stat Dose")) {
								if (form.equalsIgnoreCase("Tablet")) {
									if (dose.equalsIgnoreCase("Half Tab")) {
										qtyInOneDay = .5;
									} else {
										if (dose.equalsIgnoreCase("One Tab")) {
											qtyInOneDay = 1;
										} else {
											if (dose.equalsIgnoreCase("One & Half Tab")) {
												qtyInOneDay = 1.5;
											} else {
												if (dose.equalsIgnoreCase("Two Tabs")) {
													qtyInOneDay = 2;
												}
											}
										}

									}
								} else {
									if (form.equalsIgnoreCase("Capsule")) {
										qtyInOneDay = 1;
									}
								}
							} else {
								if (frequency.equalsIgnoreCase("Once in a Week")) {
									if (form.equalsIgnoreCase("Tablet")) {
										if (dose.equalsIgnoreCase("Half Tab")) {
											qtyInOneDay = .07142;
										} else {
											if (dose.equalsIgnoreCase("One Tab")) {
												qtyInOneDay = .14285;
											} else {
												if (dose.equalsIgnoreCase("One & Half Tab")) {
													qtyInOneDay = .21428;
												} else {
													if (dose.equalsIgnoreCase("Two Tabs")) {
														qtyInOneDay = .28571;
													}
												}
											}

										}
									} else {
										if (form.equalsIgnoreCase("Capsule")) {
											qtyInOneDay = .15;
										}
									}
								} else {
									if (frequency.equalsIgnoreCase("SOS")) {
										if (form.equalsIgnoreCase("Tablet")) {
											if (dose.equalsIgnoreCase("Half Tab")) {
												qtyInOneDay = .5;
											} else {
												if (dose.equalsIgnoreCase("One Tab")) {
													qtyInOneDay = 1;
												} else {
													if (dose.equalsIgnoreCase("One & Half Tab")) {
														qtyInOneDay = 1.5;
													} else {
														if (dose.equalsIgnoreCase("Two Tabs")) {
															qtyInOneDay = 2;
														}
													}
												}

											}
										} else {
											if (form.equalsIgnoreCase("Capsule")) {
												qtyInOneDay = 1;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return qtyInOneDay;
	}

	public int saveBenInvestigationDetails(WrapperBenInvestigationANC wrapperBenInvestigationANC) {
		Long investigationSuccessFlag = null;
		int res = 0;
		if (wrapperBenInvestigationANC != null) {
			Long prescriptionID = savePrescriptionDetailsAndGetPrescriptionID(
					wrapperBenInvestigationANC.getBeneficiaryRegID(), wrapperBenInvestigationANC.getBenVisitID(),
					wrapperBenInvestigationANC.getProviderServiceMapID(), wrapperBenInvestigationANC.getCreatedBy(),
					wrapperBenInvestigationANC.getExternalInvestigations(), wrapperBenInvestigationANC.getVisitCode(),
					wrapperBenInvestigationANC.getVanID(), wrapperBenInvestigationANC.getParkingPlaceID(), null, null,
					null);

			wrapperBenInvestigationANC.setPrescriptionID(prescriptionID);
			investigationSuccessFlag = saveBenInvestigation(wrapperBenInvestigationANC);
			if (investigationSuccessFlag != null && investigationSuccessFlag > 0) {
				// Investigation data saved successfully.
				res = 1;
			} else {
				// Something went wrong !!!
			}
		} else {
			// Invalid Data..
		}
		return res;
	}

	public Long saveBenInvestigation(WrapperBenInvestigationANC wrapperBenInvestigationANC) {
		Long r = null;

		ArrayList<LabTestOrderDetail> LabTestOrderDetailList = new ArrayList<>();
		ArrayList<LabTestOrderDetail> investigationList = wrapperBenInvestigationANC.getLaboratoryList();
		if (investigationList != null && investigationList.size() > 0) {

			for (LabTestOrderDetail testData : investigationList) {

				testData.setPrescriptionID(wrapperBenInvestigationANC.getPrescriptionID());
				testData.setBeneficiaryRegID(wrapperBenInvestigationANC.getBeneficiaryRegID());
				testData.setBenVisitID(wrapperBenInvestigationANC.getBenVisitID());
				testData.setVisitCode(wrapperBenInvestigationANC.getVisitCode());
				testData.setProviderServiceMapID(wrapperBenInvestigationANC.getProviderServiceMapID());
				testData.setCreatedBy(wrapperBenInvestigationANC.getCreatedBy());
				testData.setVanID(wrapperBenInvestigationANC.getVanID());
				testData.setParkingPlaceID(wrapperBenInvestigationANC.getParkingPlaceID());

				LabTestOrderDetailList.add(testData);
			}
			ArrayList<LabTestOrderDetail> LabTestOrderDetailListRS = (ArrayList<LabTestOrderDetail>) labTestOrderDetailRepo
					.saveAll(LabTestOrderDetailList);

			if (LabTestOrderDetailListRS.size() == investigationList.size()) {
				r = new Long(1);
			}

		} else {
			r = new Long(1);
		}

		return r;

	}

	public String updateBenVisitStatusFlag(Long benVisitID, String c) {
		return updateBenStatus(benVisitID, c);
	}

	public String updateBenStatus(Long benVisitID, String c) {
		Map<String, String> resMap = new HashMap<>();
		Integer i = benVisitDetailRepo.updateBenFlowStatus(c, benVisitID);
		if (i != null && i > 0) {
			resMap.put("status", "Updated Successfully");
		}
		return new Gson().toJson(resMap);
	}

	public String getNurseWorkList() {
		List<Object[]> nurseWorkListData = reistrarRepoBenSearch.getNurseWorkList();
		// System.out.println("hello");
		return WrapperRegWorklist.getRegistrarWorkList(nurseWorkListData);
	}

	// New Nurse worklist.... 26-03-2018
	public String getNurseWorkListNew(Integer providerServiceMapId, Integer vanID) {
		Calendar cal = Calendar.getInstance();
		if (nurseWL != null && nurseWL > 0 && nurseWL <= 30)
			cal.add(Calendar.DAY_OF_YEAR, -nurseWL);
		else
			cal.add(Calendar.DAY_OF_YEAR, -7);
		long sevenDaysAgo = cal.getTimeInMillis();

		ArrayList<BeneficiaryFlowStatus> obj = beneficiaryFlowStatusRepo.getNurseWorklistNew(providerServiceMapId,
				vanID, new Timestamp(sevenDaysAgo));

		return new Gson().toJson(obj);
	}

	// nurse worklist TC schedule (current-date) new ... 05-02-2019
	public String getNurseWorkListTcCurrentDate(Integer providerServiceMapId, Integer vanID) {
		Calendar cal = Calendar.getInstance();
		if (nurseTCWL != null && nurseTCWL > 0 && nurseTCWL <= 30)
			cal.add(Calendar.DAY_OF_YEAR, -nurseTCWL);
		else
			cal.add(Calendar.DAY_OF_YEAR, -7);
		long sevenDaysAgo = cal.getTimeInMillis();

		ArrayList<BeneficiaryFlowStatus> obj = beneficiaryFlowStatusRepo
				.getNurseWorklistCurrentDate(providerServiceMapId, new Timestamp(sevenDaysAgo), vanID);

		return new Gson().toJson(obj);
	}

	// nurse worklist TC schedule (future-date) new ... 05-02-2019
	public String getNurseWorkListTcFutureDate(Integer providerServiceMapId, Integer vanID) {
		ArrayList<BeneficiaryFlowStatus> obj = beneficiaryFlowStatusRepo
				.getNurseWorklistFutureDate(providerServiceMapId, vanID);

		return new Gson().toJson(obj);
	}

	// New Lab worklist.... 26-03-2018
	public String getLabWorkListNew(Integer providerServiceMapId, Integer vanID) {
		Calendar cal = Calendar.getInstance();
		if (labWL != null && labWL > 0 && labWL <= 30)
			cal.add(Calendar.DAY_OF_YEAR, -labWL);
		else
			cal.add(Calendar.DAY_OF_YEAR, -7);
		long sevenDaysAgo = cal.getTimeInMillis();

		ArrayList<BeneficiaryFlowStatus> obj = beneficiaryFlowStatusRepo.getLabWorklistNew(providerServiceMapId,
				new Timestamp(sevenDaysAgo), vanID);

		return new Gson().toJson(obj);
	}

	// New radiologist worklist.... 26-03-2018
	public String getRadiologistWorkListNew(Integer providerServiceMapId, Integer vanID) {
		Calendar cal = Calendar.getInstance();
		if (radioWL != null && radioWL > 0 && radioWL <= 30)
			cal.add(Calendar.DAY_OF_YEAR, -radioWL);
		else
			cal.add(Calendar.DAY_OF_YEAR, -7);
		long sevenDaysAgo = cal.getTimeInMillis();

		ArrayList<BeneficiaryFlowStatus> obj = beneficiaryFlowStatusRepo.getRadiologistWorkListNew(providerServiceMapId,
				new Timestamp(sevenDaysAgo), vanID);

		return new Gson().toJson(obj);
	}

	// New oncologist worklist.... 26-03-2018
	public String getOncologistWorkListNew(Integer providerServiceMapId, Integer vanID) {

		Calendar cal = Calendar.getInstance();
		if (oncoWL != null && oncoWL > 0 && oncoWL <= 30)
			cal.add(Calendar.DAY_OF_YEAR, -oncoWL);
		else
			cal.add(Calendar.DAY_OF_YEAR, -7);
		long sevenDaysAgo = cal.getTimeInMillis();

		ArrayList<BeneficiaryFlowStatus> obj = beneficiaryFlowStatusRepo.getOncologistWorkListNew(providerServiceMapId,
				new Timestamp(sevenDaysAgo), vanID);

		return new Gson().toJson(obj);
	}

	// New pharma worklist.... 26-03-2018
	public String getPharmaWorkListNew(Integer providerServiceMapId, Integer vanID) {

		Calendar cal = Calendar.getInstance();
		if (pharmaWL != null && pharmaWL > 0 && pharmaWL <= 30)
			cal.add(Calendar.DAY_OF_YEAR, -pharmaWL);
		else
			cal.add(Calendar.DAY_OF_YEAR, -7);
		long sevenDaysAgo = cal.getTimeInMillis();

		ArrayList<BeneficiaryFlowStatus> obj = beneficiaryFlowStatusRepo.getPharmaWorkListNew(providerServiceMapId,
				new Timestamp(sevenDaysAgo), vanID);

		return new Gson().toJson(obj);
	}

	public int saveBenAdherenceDetails(BenAdherence benAdherence) throws IEMRException {
		int r = 0;
		BenAdherence benAdherenceOBJ = benAdherenceRepo.save(benAdherence);
		if (benAdherenceOBJ != null) {
			r = 1;
		}
		return r;
	}

	public Long saveCdssDetails(CDSS cdss) throws IEMRException {
		cdss = cdssRepo.save(cdss);

		if (cdss != null && cdss.getId() > 0)
			return cdss.getId();
		else
			throw new IEMRException("DB-Error in saving cdss details");

	}

	public Long saveChildDevelopmentHistory(BenChildDevelopmentHistory benChildDevelopmentHistory)
			throws IEMRException {
		Long developmentSuccessFlag = null;

		BenChildDevelopmentHistory childDevelopmentHistory = getDevelopmentHistoryDBOBJ(benChildDevelopmentHistory);
		BenChildDevelopmentHistory res = benChildDevelopmentHistoryRepo.save(childDevelopmentHistory);
		if (null != res && res.getID() > 0) {
			developmentSuccessFlag = res.getID();
		}
		return developmentSuccessFlag;
	}

	public Long saveChildFeedingHistory(ChildFeedingDetails childFeedingDetails) {
		Long feedingSuccessFlag = null;
//		ChildFeedingDetails childFeedingHistory = ChildFeedingDetails.getChildFeedingHistory(childFeedingDetails);

		// typeofFoodIntolerance list to string
		if (childFeedingDetails != null && childFeedingDetails.getTypeOfFoodIntolerances() != null
				&& childFeedingDetails.getTypeOfFoodIntolerances().length > 0) {

			StringBuffer sb = new StringBuffer();

			for (String foodIntolerence : childFeedingDetails.getTypeOfFoodIntolerances()) {
				sb.append(foodIntolerence).append("||");
			}

			if (sb.length() >= 2) {
				String fi = sb.substring(0, (sb.length() - 2));
				childFeedingDetails.setTypeofFoodIntolerance(fi);
			}
		}

		ChildFeedingDetails res = childFeedingDetailsRepo.save(childFeedingDetails);
		if (null != res && res.getID() > 0) {
			feedingSuccessFlag = res.getID();
		}
		return feedingSuccessFlag;
	}

	public Long savePerinatalHistory(PerinatalHistory perinatalHistory) {
		Long perinatalSuccessFlag = null;

		PerinatalHistory res = perinatalHistoryRepo.save(perinatalHistory);
		if (null != res && res.getID() > 0) {
			perinatalSuccessFlag = res.getID();
		}
		return perinatalSuccessFlag;
	}

	public String getBenAdherence(Long beneficiaryRegID, Long visitCode) {
		ArrayList<Object[]> resList = benAdherenceRepo.getBenAdherence(beneficiaryRegID, visitCode);
		BenAdherence benAdherences = BenAdherence.getBenAdherences(resList);
		return new Gson().toJson(benAdherences);
	}

	public String getLabTestOrders(Long beneficiaryRegID, Long visitCode) {
		ArrayList<Object[]> resList = labTestOrderDetailRepo.getLabTestOrderDetails(beneficiaryRegID, visitCode);
		WrapperBenInvestigationANC labTestOrderDetails = LabTestOrderDetail.getLabTestOrderDetails(resList);
		return new Gson().toJson(labTestOrderDetails);
	}

	public BenChildDevelopmentHistory getDevelopmentHistory(Long beneficiaryRegID, Long visitCode)
			throws IEMRException {

		BenChildDevelopmentHistory childDevelopmentHistoryRS = benChildDevelopmentHistoryRepo
				.getBenDevelopmentHistory(beneficiaryRegID, visitCode);

		if (childDevelopmentHistoryRS != null) {

			if (childDevelopmentHistoryRS.getGrossMotorMilestone() != null)
				childDevelopmentHistoryRS.setGrossMotorMilestones(
						Arrays.asList(childDevelopmentHistoryRS.getGrossMotorMilestone().split(",")));

			if (childDevelopmentHistoryRS.getFineMotorMilestone() != null)
				childDevelopmentHistoryRS.setFineMotorMilestones(
						Arrays.asList(childDevelopmentHistoryRS.getFineMotorMilestone().split(",")));

			if (childDevelopmentHistoryRS.getSocialMilestone() != null)
				childDevelopmentHistoryRS
						.setSocialMilestones(Arrays.asList(childDevelopmentHistoryRS.getSocialMilestone().split(",")));

			if (childDevelopmentHistoryRS.getLanguageMilestone() != null)
				childDevelopmentHistoryRS.setLanguageMilestones(
						Arrays.asList(childDevelopmentHistoryRS.getLanguageMilestone().split(",")));

			if (childDevelopmentHistoryRS.getDevelopmentProblem() != null)
				childDevelopmentHistoryRS.setDevelopmentProblems(
						Arrays.asList(childDevelopmentHistoryRS.getDevelopmentProblem().split(",")));
		}

		return childDevelopmentHistoryRS;
	}

	public PerinatalHistory getPerinatalHistory(Long beneficiaryRegID, Long visitCode) throws IEMRException {

		PerinatalHistory perinatalHistoryDetails = perinatalHistoryRepo.getBenPerinatalHistory(beneficiaryRegID,
				visitCode);
		return perinatalHistoryDetails;
	}

	public ChildFeedingDetails getFeedingHistory(Long beneficiaryRegID, Long visitCode) throws IEMRException {

		ChildFeedingDetails feedingHistoryDetails = childFeedingDetailsRepo.getBenFeedingHistory(beneficiaryRegID,
				visitCode);

		if (feedingHistoryDetails != null && feedingHistoryDetails.getTypeofFoodIntolerance() != null)
			feedingHistoryDetails
					.setTypeOfFoodIntolerances(feedingHistoryDetails.getTypeofFoodIntolerance().split("\\|\\|"));

		return feedingHistoryDetails;
	}

	public String fetchBenPerinatalHistory(Long beneficiaryRegID) {
		ArrayList<Object[]> perinatalHistoryDetail = perinatalHistoryRepo.getBenPerinatalDetail(beneficiaryRegID);

		Map<String, Object> response = new HashMap<String, Object>();
		List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
		Map<String, Object> column = new HashMap<String, Object>();

		column = new HashMap<>();
		column.put("columnName", "Date of Capture");
		column.put("keyName", "captureDate");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Place of Delivery");
		column.put("keyName", "placeOfDelivery");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Other Place of Delivery");
		column.put("keyName", "otherPlaceOfDelivery");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Type of Delivery");
		column.put("keyName", "typeOfDelivery");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Complication at Birth");
		column.put("keyName", "complicationAtBirth");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Other Complication at Birth");
		column.put("keyName", "otherComplicationAtBirth");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Gestation");
		column.put("keyName", "gestation");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Birth Weight(g)");
		column.put("keyName", "birthWeight_gram");
		columns.add(column);

		ArrayList<PerinatalHistory> PerinatalHistoryDetails = new ArrayList<PerinatalHistory>();
		if (null != perinatalHistoryDetail) {
			for (Object[] obj : perinatalHistoryDetail) {
				PerinatalHistory history = new PerinatalHistory((Date) obj[0], (String) obj[1], (String) obj[2],
						(String) obj[3], (String) obj[4], (String) obj[5], (String) obj[6], (Double) obj[7]);
				PerinatalHistoryDetails.add(history);
			}
		}

		response.put("columns", columns);
		response.put("data", PerinatalHistoryDetails);
		return new Gson().toJson(response);

	}

	public String fetchBenFeedingHistory(Long beneficiaryRegID) {
		ArrayList<Object[]> feedingHistoryDetail = childFeedingDetailsRepo.getBenFeedingHistoryDetail(beneficiaryRegID);

		Map<String, Object> response = new HashMap<String, Object>();
		List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
		Map<String, Object> column = new HashMap<String, Object>();

		column = new HashMap<>();
		column.put("columnName", "Date of Capture");
		column.put("keyName", "captureDate");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Child ID");
		column.put("keyName", "childID");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Beneficiary Mother ID");
		column.put("keyName", "benMotherID");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Type of Feed");
		column.put("keyName", "typeOfFeed");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Comp Feed Start Age");
		column.put("keyName", "compFeedStartAge");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "No of Comp Feed Per Day");
		column.put("keyName", "noOfCompFeedPerDay");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Food Intolerance Status");
		column.put("keyName", "foodIntoleranceStatus");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Type of Food Intolerance");
		column.put("keyName", "typeofFoodIntolerance");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Other Food Intolerance");
		column.put("keyName", "otherFoodIntolerance");
		columns.add(column);

		ArrayList<ChildFeedingDetails> feedingHistoryDetails = new ArrayList<ChildFeedingDetails>();
		if (null != feedingHistoryDetail) {
			for (Object[] obj : feedingHistoryDetail) {
				ChildFeedingDetails history = new ChildFeedingDetails((Date) obj[0], (Long) obj[1], (Long) obj[2],
						(String) obj[3], (String) obj[4], (String) obj[5], (String) obj[6], (String) obj[7],
						(String) obj[8]);
				feedingHistoryDetails.add(history);
			}
		}

		response.put("columns", columns);
		response.put("data", feedingHistoryDetails);
		return new Gson().toJson(response);

	}

	public String fetchBenDevelopmentHistory(Long beneficiaryRegID) {
		ArrayList<Object[]> developmentHistoryDetail = benChildDevelopmentHistoryRepo
				.getBenDevelopmentHistoryDetail(beneficiaryRegID);

		Map<String, Object> response = new HashMap<String, Object>();
		List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
		Map<String, Object> column = new HashMap<String, Object>();

		column = new HashMap<>();
		column.put("columnName", "Date of Capture");
		column.put("keyName", "captureDate");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Gross Motor Milestone");
		column.put("keyName", "grossMotorMilestone");
		columns.add(column);

		/*
		 * column = new HashMap<>(); column.put("columnName", "Is GMM Attained");
		 * column.put("keyName", "isGMMAttained"); columns.add(column);
		 */

		column = new HashMap<>();
		column.put("columnName", "Fine Motor Milestone");
		column.put("keyName", "fineMotorMilestone");
		columns.add(column);

		/*
		 * column = new HashMap<>(); column.put("columnName", "Is FMM Attained");
		 * column.put("keyName", "isFMMAttained"); columns.add(column);
		 */
		column = new HashMap<>();
		column.put("columnName", "Social Milestone");
		column.put("keyName", "socialMilestone");
		columns.add(column);

		/*
		 * column = new HashMap<>(); column.put("columnName", "Is SM Attained");
		 * column.put("keyName", "isSMAttained"); columns.add(column);
		 */

		column = new HashMap<>();
		column.put("columnName", "Language Milestone");
		column.put("keyName", "languageMilestone");
		columns.add(column);

		/*
		 * column = new HashMap<>(); column.put("columnName", "Is LM Attained");
		 * column.put("keyName", "isLMAttained"); columns.add(column);
		 */

		column = new HashMap<>();
		column.put("columnName", "Development Problem");
		column.put("keyName", "developmentProblem");
		columns.add(column);

		ArrayList<BenChildDevelopmentHistory> developmentHistoryDetails = new ArrayList<BenChildDevelopmentHistory>();
		if (null != developmentHistoryDetail) {
			for (Object[] obj : developmentHistoryDetail) {

				BenChildDevelopmentHistory history = new BenChildDevelopmentHistory((Date) obj[0], (String) obj[1],
						(Boolean) obj[2], (String) obj[3], (Boolean) obj[4], (String) obj[5], (Boolean) obj[6],
						(String) obj[7], (Boolean) obj[8], (String) obj[9]);
				developmentHistoryDetails.add(history);
			}
		}

		response.put("columns", columns);
		response.put("data", developmentHistoryDetails);
		return new Gson().toJson(response);

	}

	public int updateChildFeedingHistory(ChildFeedingDetails childFeedingDetails) throws IEMRException {
		int response = 0;
		int recordsAvailable = 0;
		if (null != childFeedingDetails) {
			String processed = childFeedingDetailsRepo.getBenChildFeedingDetailStatus(
					childFeedingDetails.getBeneficiaryRegID(), childFeedingDetails.getVisitCode());
			if (null != processed) {
				recordsAvailable = 1;
			}
			if (null != processed && !"N".equals(processed)) {
				processed = "U";
			} else {
				processed = "N";
			}
			if (recordsAvailable > 0) {
				response = childFeedingDetailsRepo.updateFeedingDetails(childFeedingDetails.getChildID(),
						childFeedingDetails.getBenMotherID(), childFeedingDetails.getTypeOfFeed(),
						childFeedingDetails.getCompFeedStartAge(), childFeedingDetails.getNoOfCompFeedPerDay(),
						childFeedingDetails.getFoodIntoleranceStatus(), childFeedingDetails.getTypeofFoodIntolerance(),
						childFeedingDetails.getModifiedBy(), processed, childFeedingDetails.getBeneficiaryRegID(),
						childFeedingDetails.getVisitCode(), childFeedingDetails.getOtherFoodIntolerance());
			} else {
				childFeedingDetails.setCreatedBy(childFeedingDetails.getModifiedBy());
				childFeedingDetails = childFeedingDetailsRepo.save(childFeedingDetails);
				if (null != childFeedingDetails && childFeedingDetails.getID() > 0) {
					response = 1;
				}
			}
		}
		return response;
	}

	public int updatePerinatalHistory(PerinatalHistory perinatalHistory) throws IEMRException {
		int response = 0;
		int recordsAvailable = 0;
		if (null != perinatalHistory) {
			String processed = perinatalHistoryRepo.getPerinatalHistoryStatus(perinatalHistory.getBeneficiaryRegID(),
					perinatalHistory.getVisitCode());
			if (null != processed) {
				recordsAvailable = 1;
			}
			if (null != processed && !"N".equals(processed)) {
				processed = "U";
			} else {
				processed = "N";
			}
			if (recordsAvailable > 0) {
				response = perinatalHistoryRepo.updatePerinatalDetails(perinatalHistory.getDeliveryPlaceID(),
						perinatalHistory.getPlaceOfDelivery(), perinatalHistory.getOtherPlaceOfDelivery(),
						perinatalHistory.getDeliveryTypeID(), perinatalHistory.getTypeOfDelivery(),
						perinatalHistory.getComplicationAtBirthID(), perinatalHistory.getComplicationAtBirth(),
						perinatalHistory.getOtherComplicationAtBirth(), perinatalHistory.getGestation(),
						perinatalHistory.getBirthWeightG(), perinatalHistory.getModifiedBy(), processed,
						perinatalHistory.getBeneficiaryRegID(), perinatalHistory.getVisitCode());
			} else {
				perinatalHistory.setCreatedBy(perinatalHistory.getModifiedBy());
				perinatalHistory = perinatalHistoryRepo.save(perinatalHistory);
				if (null != perinatalHistory && perinatalHistory.getID() > 0) {
					response = 1;
				}

			}
		}
		return response;
	}

	public int updateChildDevelopmentHistory(BenChildDevelopmentHistory childDevelopmentDetails) throws IEMRException {
		int response = 0;
		int recordsAvailable = 0;
		if (null != childDevelopmentDetails) {
			String processed = benChildDevelopmentHistoryRepo.getDevelopmentHistoryStatus(
					childDevelopmentDetails.getBeneficiaryRegID(), childDevelopmentDetails.getVisitCode());
			if (null != processed) {
				recordsAvailable = 1;
			}
			if (null != processed && !"N".equals(processed)) {
				processed = "U";
			} else {
				processed = "N";
			}
			childDevelopmentDetails = getDevelopmentHistoryDBOBJ(childDevelopmentDetails);
			if (recordsAvailable > 0) {
				response = benChildDevelopmentHistoryRepo.updateBenChildDevelopmentHistory(
						childDevelopmentDetails.getGrossMotorMilestone(),
						childDevelopmentDetails.getIsGrossMotorMilestones(),
						childDevelopmentDetails.getFineMotorMilestone(),
						childDevelopmentDetails.getIsFineMotorMilestones(),
						childDevelopmentDetails.getSocialMilestone(), childDevelopmentDetails.getIsSocialMilestones(),
						childDevelopmentDetails.getLanguageMilestone(),
						childDevelopmentDetails.getIsLanguageMilestones(),
						childDevelopmentDetails.getDevelopmentProblem(), childDevelopmentDetails.getModifiedBy(),
						processed, childDevelopmentDetails.getBeneficiaryRegID(),
						childDevelopmentDetails.getVisitCode());
			} else {
				childDevelopmentDetails.setCreatedBy(childDevelopmentDetails.getModifiedBy());
				BenChildDevelopmentHistory developmentDetails = benChildDevelopmentHistoryRepo
						.save(childDevelopmentDetails);
				if (null != developmentDetails && developmentDetails.getID() > 0) {
					response = 1;
				}
			}
		}
		return response;
	}

	// for above function
	private BenChildDevelopmentHistory getDevelopmentHistoryDBOBJ(BenChildDevelopmentHistory benChildDevelopmentHistory)
			throws IEMRException {

		if (benChildDevelopmentHistory != null) {
			StringBuffer grossMotorMilestone = new StringBuffer();
			if (benChildDevelopmentHistory.getGrossMotorMilestones() != null
					&& benChildDevelopmentHistory.getGrossMotorMilestones().size() > 0) {
				for (String gmm : benChildDevelopmentHistory.getGrossMotorMilestones()) {
					grossMotorMilestone.append(gmm).append(",");
				}
				benChildDevelopmentHistory.setGrossMotorMilestone(grossMotorMilestone.toString());
			}

			StringBuffer fineMotorMilestone = new StringBuffer();
			if (null != benChildDevelopmentHistory.getFineMotorMilestones()
					&& benChildDevelopmentHistory.getFineMotorMilestones().size() > 0) {
				for (String fmm : benChildDevelopmentHistory.getFineMotorMilestones()) {
					fineMotorMilestone.append(fmm).append(",");
				}
				benChildDevelopmentHistory.setFineMotorMilestone(fineMotorMilestone.toString());
			}

			StringBuffer socialMilestone = new StringBuffer();
			if (null != benChildDevelopmentHistory.getSocialMilestones()
					&& benChildDevelopmentHistory.getSocialMilestones().size() > 0) {
				for (String sm : benChildDevelopmentHistory.getSocialMilestones()) {
					socialMilestone.append(sm).append(",");
				}
				benChildDevelopmentHistory.setSocialMilestone(socialMilestone.toString());
			}

			StringBuffer languageMilestone = new StringBuffer();
			if (null != benChildDevelopmentHistory.getLanguageMilestones()
					&& benChildDevelopmentHistory.getLanguageMilestones().size() > 0) {
				for (String lm : benChildDevelopmentHistory.getLanguageMilestones()) {
					languageMilestone.append(lm).append(",");
				}
				benChildDevelopmentHistory.setLanguageMilestone(languageMilestone.toString());
			}

			StringBuffer developmentProblem = new StringBuffer();
			if (null != benChildDevelopmentHistory.getDevelopmentProblems()
					&& benChildDevelopmentHistory.getDevelopmentProblems().size() > 0) {
				for (String dp : benChildDevelopmentHistory.getDevelopmentProblems()) {
					developmentProblem.append(dp).append(",");
				}
				benChildDevelopmentHistory.setDevelopmentProblem(developmentProblem.toString());
			}

		}
		return benChildDevelopmentHistory;
	}

	public int updateSysGastrointestinalExamination(SysGastrointestinalExamination gastrointestinalExamination)
			throws IEMRException {
		int response = 0;
		if (null != gastrointestinalExamination) {
			String processed = sysGastrointestinalExaminationRepo.getBenGastrointestinalExaminationStatus(
					gastrointestinalExamination.getBeneficiaryRegID(), gastrointestinalExamination.getVisitCode());
			if (null != processed && !"N".equals(processed)) {
				processed = "U";
			} else {
				processed = "N";
			}
			response = sysGastrointestinalExaminationRepo.updateSysGastrointestinalExamination(
					gastrointestinalExamination.getInspection(), gastrointestinalExamination.getPalpation(),
					gastrointestinalExamination.getPalpation_AbdomenTexture(),
					gastrointestinalExamination.getPalpation_Liver(), gastrointestinalExamination.getPalpation_Spleen(),
					gastrointestinalExamination.getPalpation_Tenderness(),
					gastrointestinalExamination.getPalpation_LocationOfTenderness(),
					gastrointestinalExamination.getPercussion(), gastrointestinalExamination.getAuscultation(),
					gastrointestinalExamination.getAnalRegion(), gastrointestinalExamination.getModifiedBy(), processed,
					gastrointestinalExamination.getBeneficiaryRegID(), gastrointestinalExamination.getVisitCode());
		}
		return response;
	}

	public Map<String, Object> getGraphicalTrendData(Long benRegID, String visitCategory) {
		Map<String, Object> returnOBJ = new HashMap<>();

		ArrayList<Object[]> benLastSixVisitDetails = benVisitDetailRepo.getLastSixVisitDetailsForBeneficiary(benRegID, visitCategory);

		ArrayList<Long> benVisitCodeListCancer = new ArrayList<>();
		ArrayList<Long> benVisitCodeListOther = new ArrayList<>();

		BigInteger a;
		String vc;
		if (benLastSixVisitDetails != null && benLastSixVisitDetails.size() > 0) {
			for (Object[] objArr : benLastSixVisitDetails) {
				vc = (String) objArr[1];
				if (vc != null && vc.equalsIgnoreCase("Cancer Screening")) {
					a = BigInteger.valueOf((long) objArr[2]);
					benVisitCodeListCancer.add(a.longValue());
				} else {
					a = BigInteger.valueOf((long) objArr[2]);
					benVisitCodeListOther.add(a.longValue());
				}
			}
		}

		ArrayList<Object[]> benAnthro = new ArrayList<>();
		ArrayList<Object[]> benVital = new ArrayList<>();
		ArrayList<BenCancerVitalDetail> benCancerVital = new ArrayList<>();

		if (benVisitCodeListCancer.size() > 0) {
			benCancerVital = benCancerVitalDetailRepo.getBenCancerVitalDetailForGraph(benVisitCodeListCancer);
		}

		if (benVisitCodeListOther.size() > 0) {
			benAnthro = benAnthropometryRepo.getBenAnthropometryDetailForGraphtrends(benVisitCodeListOther);
			benVital = benPhysicalVitalRepo.getBenPhysicalVitalDetailForGraphTrends(benVisitCodeListOther);
		}

		ArrayList<Map<String, Object>> weightList = new ArrayList<>();
		ArrayList<Map<String, Object>> bpList = new ArrayList<>();
		ArrayList<Map<String, Object>> bgList = new ArrayList<>();

		Map<String, Object> weightOBJ;
		Map<String, Object> bpObj;
		Map<String, Object> bgOBJ;

		if ((benAnthro != null && benAnthro.size() > 0) || (benCancerVital != null && benCancerVital.size() > 0)) {
			for (Object[] objArr : benAnthro) {
				weightOBJ = new HashMap<>();
				weightOBJ.put("weight", objArr[0]);
				weightOBJ.put("date", objArr[1]);

				weightList.add(weightOBJ);
			}

			for (BenCancerVitalDetail obj : benCancerVital) {
				weightOBJ = new HashMap<>();
				weightOBJ.put("weight", obj.getWeight_Kg());
				weightOBJ.put("date", Date.valueOf(obj.getCreatedDate().toLocalDateTime().toLocalDate()));

				weightList.add(weightOBJ);
			}
		}

		if ((benVital != null && benVital.size() > 0) || (benCancerVital != null && benCancerVital.size() > 0)) {
			for (Object[] objArr : benVital) {
				if (objArr[0] != null && (Short) objArr[0] > 0 && objArr[1] != null && (Short) objArr[1] > 0) {
					bpObj = new HashMap<>();
					bpObj.put("avgSysBP", objArr[0]);
					bpObj.put("avgDysBP", objArr[1]);
					bpObj.put("date", objArr[5]);

					bpList.add(bpObj);
				}

				if (objArr[2] != null || objArr[3] != null || objArr[4] != null) {
					bgOBJ = new HashMap<>();
					bgOBJ.put("bg_fasting", objArr[2]);
					bgOBJ.put("bg_random", objArr[3]);
					bgOBJ.put("bg_2hr_pp", objArr[4]);
					bgOBJ.put("date", objArr[5]);

					bgList.add(bgOBJ);
				}
			}

			for (BenCancerVitalDetail obj : benCancerVital) {
				if (obj.getSystolicBP_1stReading() != null || obj.getSystolicBP_2ndReading() != null
						|| obj.getSystolicBP_3rdReading() != null || obj.getDiastolicBP_1stReading() != null
						|| obj.getDiastolicBP_2ndReading() != null || obj.getDiastolicBP_3rdReading() != null) {
					short sysBP1 = (obj.getSystolicBP_1stReading() != null && obj.getSystolicBP_1stReading() > 0)
							? obj.getSystolicBP_1stReading()
							: 0;

					short sysBP2 = (obj.getSystolicBP_2ndReading() != null && obj.getSystolicBP_2ndReading() > 0)
							? obj.getSystolicBP_2ndReading()
							: 0;

					short sysBP3 = (obj.getSystolicBP_3rdReading() != null && obj.getSystolicBP_3rdReading() > 0)
							? obj.getSystolicBP_3rdReading()
							: 0;

					short dysBP1 = (obj.getDiastolicBP_1stReading() != null && obj.getDiastolicBP_1stReading() > 0)
							? obj.getDiastolicBP_1stReading()
							: 0;

					short dysBP2 = (obj.getDiastolicBP_2ndReading() != null && obj.getDiastolicBP_2ndReading() > 0)
							? obj.getDiastolicBP_2ndReading()
							: 0;

					short dysBP3 = (obj.getDiastolicBP_3rdReading() != null && obj.getDiastolicBP_3rdReading() > 0)
							? obj.getDiastolicBP_3rdReading()
							: 0;

					bpObj = new HashMap<>();
					bpObj.put("avgSysBP", ((sysBP1 + sysBP2 + sysBP3) / 3));
					bpObj.put("avgDysBP", ((dysBP1 + dysBP2 + dysBP3) / 3));
					bpObj.put("date", Date.valueOf(obj.getCreatedDate().toLocalDateTime().toLocalDate()));

					bpList.add(bpObj);
				}

				if (obj.getBloodGlucose_Fasting() != null || obj.getBloodGlucose_Random() != null
						|| obj.getBloodGlucose_2HrPostPrandial() != null) {
					bgOBJ = new HashMap<>();
					bgOBJ.put("bg_fasting", obj.getBloodGlucose_Fasting());
					bgOBJ.put("bg_random", obj.getBloodGlucose_Random());
					bgOBJ.put("bg_2hr_pp", obj.getBloodGlucose_2HrPostPrandial());
					bgOBJ.put("date", Date.valueOf(obj.getCreatedDate().toLocalDateTime().toLocalDate()));

					bgList.add(bgOBJ);
				}

			}

		}

		returnOBJ.put("weightList", weightList);
		returnOBJ.put("bpList", bpList);
		returnOBJ.put("bgList", bgList);

		return returnOBJ;
	}

	public int updateBenFamilyHistoryNCDScreening(BenFamilyHistory benFamilyHistory) {
		// TODO Auto-generated method stub
		int familyHistorySuccessFlag = 0;

		ArrayList<BenFamilyHistory> familyHistoryList = benFamilyHistory.getBenFamilyHist();
		if (familyHistoryList.size() > 0) {
			ArrayList<BenFamilyHistory> res = (ArrayList<BenFamilyHistory>) benFamilyHistoryRepo
					.saveAll(familyHistoryList);
			if (familyHistoryList.size() == res.size()) {
				familyHistorySuccessFlag = 1;
			}
		} else {
			familyHistorySuccessFlag = 0;
		}
		return familyHistorySuccessFlag;
	}

	public int updateBenPhysicalActivityHistoryNCDScreening(PhysicalActivityType physicalActivityType) {
		// TODO Auto-generated method stub
		int pysicalActivityHistorySuccessFlag = 0;

//		ArrayList<BenFamilyHistory> familyHistoryList = benFamilyHistory.getBenFamilyHistory();
//		if (familyHistoryList.size() > 0) {
//			ArrayList<BenFamilyHistory> res = (ArrayList<BenFamilyHistory>) benFamilyHistoryRepo
//					.save(familyHistoryList);
//			if (familyHistoryList.size() == res.size()) {
//				familyHistorySuccessFlag = 1;
//			}
		if (physicalActivityType.getID() != null)
			physicalActivityType.setProcessed("U");
		else
			physicalActivityType.setProcessed("N");
		physicalActivityType.setDeleted(false);
		PhysicalActivityType physicalActivityTypeRes = physicalActivityTypeRepo.save(physicalActivityType);
		if (physicalActivityTypeRes != null) {
			pysicalActivityHistorySuccessFlag = 1;
		} else {
			pysicalActivityHistorySuccessFlag = 0;
		}
		return pysicalActivityHistorySuccessFlag;
	}

	// get Ben Symptomatic Questionnaire Details
	@Override
	public String getBenSymptomaticData(Long benRegID) throws Exception {
		Map<String, Object> responseMap = new HashMap<>();
		ArrayList<Map<String, Object>> ansList = new ArrayList<>();
		// get date before 3 month
		java.util.Date referenceDate = new java.util.Date();
		Calendar c = Calendar.getInstance();
		c.setTime(referenceDate);
		c.add(Calendar.MONTH, -3);
		Timestamp t = new Timestamp(c.getTimeInMillis());

		Map<String, Object> questionAnsMap;
		String confirmedDisease = null;
		String suspectedDisease = null;
		ArrayList<IDRSData> resultSet = iDRSDataRepo.getBenIdrsDetailsLast_3_Month(benRegID, t);

		if (resultSet != null && resultSet.size() > 0) {
			Long visitCode = null;
			int pointer = 0;
			for (IDRSData i : resultSet) {

				if (visitCode == null || i.getVisitCode().equals(visitCode)) {
					questionAnsMap = new HashMap<>();
					visitCode = i.getVisitCode();
					questionAnsMap.put("qID", i.getIdrsQuestionID());
					questionAnsMap.put("qANS", i.getAnswer());

					if (pointer == 0) {
						suspectedDisease = i.getSuspectedDisease();
						confirmedDisease = i.getConfirmedDisease();
					}
					ansList.add(questionAnsMap);
					pointer++;
				} else
					break;
			}

		}

		// is diabetic check for ben
		Integer i = iDRSDataRepo.isDiabeticCheck(benRegID);
		Integer epilepsy = iDRSDataRepo.isEpilepsyCheck(benRegID);
		Integer vision = iDRSDataRepo.isDefectiveVisionCheck(benRegID);
		Integer hypertension = iDRSDataRepo.isHypertensionCheck(benRegID);
		responseMap.put("questionariesData", ansList);
		if (i != null && i > 0)
			responseMap.put("isDiabetic", true);
		else
			responseMap.put("isDiabetic", false);
		if (epilepsy != null && epilepsy > 0)
			responseMap.put("isEpilepsy", true);
		else
			responseMap.put("isEpilepsy", false);
		if (vision != null && vision > 0)
			responseMap.put("isDefectiveVision", true);
		else
			responseMap.put("isDefectiveVision", false);
		if (hypertension != null && hypertension > 0)
			responseMap.put("isHypertension", true);
		else
			responseMap.put("isHypertension", false);
		if (suspectedDisease != null)
			responseMap.put("suspectedDisease", suspectedDisease);

		if (confirmedDisease != null)
			responseMap.put("confirmedDisease", confirmedDisease);

		return new Gson().toJson(responseMap);
	}

//	@Override
//	public String getBenPreviousDiabetesData(Long benRegID) throws Exception {
//		Map<String, Object> response = new HashMap<String, Object>();
//
//		ArrayList<IDRSData> resultSet = new ArrayList<>();
//		ArrayList<Object[]> resultSet1 = new ArrayList<>();
//		Map<String, String> column;
//		ArrayList<Map<String, String>> columns = new ArrayList<>();
//
//		column = new HashMap<>();
//		column.put("columnName", "Date of Capture");
//		column.put("keyName", "createdDate");
//		columns.add(column);
//
//		column = new HashMap<>();
//		column.put("columnName", "Visit Code");
//		column.put("keyName", "visitCode");
//		columns.add(column);
//
//		column = new HashMap<>();
//		column.put("columnName", "Question");
//		column.put("keyName", "question");
//		columns.add(column);
//
//		column = new HashMap<>();
//		column.put("columnName", "Answer");
//		column.put("keyName", "answer");
//		columns.add(column);
//		IDRSData idrs=new IDRSData();
//		resultSet1 = iDRSDataRepo.getBenPreviousDiabetesDetails(benRegID);
//        if(resultSet1 !=null && resultSet1.size()>0)
//        {
//        	for(Object[] obj:resultSet1)
//        	{
//        		idrs=new IDRSData(((BigInteger) obj[0]).longValue(),(Timestamp)obj[1],(String)obj[2],(String)obj[3],((BigInteger) obj[4]).longValue(),((Integer) obj[5])
//        				,(String)obj[6]);
//        		resultSet.add(idrs);
//        	}
//        }
//		response.put("columns", columns);
//		response.put("data", resultSet);
//		return new Gson().toJson(response);
//	}
	@Override
	public String getBenPreviousDiabetesData(Long benRegID) throws Exception {

		Map<String, Object> response = new HashMap<String, Object>();

		ArrayList<IDRSData> resultSet = new ArrayList<>();

		Map<String, String> column;
		ArrayList<Map<String, String>> columns = new ArrayList<>();

		column = new HashMap<>();
		column.put("columnName", "Date of Capture");
		column.put("keyName", "createdDate");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Visit Code");
		column.put("keyName", "visitCode");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Question");
		column.put("keyName", "question");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Answer");
		column.put("keyName", "answer");
		columns.add(column);

		resultSet = iDRSDataRepo.getBenPreviousDiabetesDetails(benRegID);

		response.put("columns", columns);
		response.put("data", resultSet);
		return new Gson().toJson(response);
	}

	// New Nurse worklist coming from MMU.... 16-02-2021
	public String getMmuNurseWorkListNew(Integer providerServiceMapId, Integer vanID) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -TMReferredWL);
		long startTime = cal.getTimeInMillis();
		ArrayList<BeneficiaryFlowStatus> obj = beneficiaryFlowStatusRepo.getMmuNurseWorklistNew(providerServiceMapId,
				vanID, new Timestamp(startTime));

		return new Gson().toJson(obj);
	}

	@Override
	public String getBenPreviousReferralData(Long benRegID) throws Exception {

		Map<String, Object> response = new HashMap<String, Object>();

		ArrayList<Object[]> resultSet = new ArrayList<>();
		ArrayList<IDRSData> resultSet1 = new ArrayList<>();
		IDRSData idrs = new IDRSData();
		Map<String, String> column;
		ArrayList<Map<String, String>> columns = new ArrayList<>();

		column = new HashMap<>();
		column.put("columnName", "Date of Referral");
		column.put("keyName", "createdDate");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Visit Code");
		column.put("keyName", "visitCode");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Suspected Diseases");
		column.put("keyName", "suspectedDisease");
		columns.add(column);

		resultSet = iDRSDataRepo.getBenPreviousReferredDetails(benRegID);

		if (resultSet != null) {
			for (Object[] obj : resultSet) {
				idrs = new IDRSData(((BigInteger) obj[0]).longValue(), (Timestamp) obj[1], (String) obj[2]);
				resultSet1.add(idrs);
			}
		}
		response.put("data", resultSet1);
		response.put("columns", columns);
		return new Gson().toJson(response);
	}

	/**
	 * Author SH20094090
	 * 
	 * @throws IEMRException
	 */
	public String fetchProviderSpecificdata(String request) throws IEMRException {
		String res = "";
		try {
			ProviderSpecificRequest detail = InputMapper.gson().fromJson(request, ProviderSpecificRequest.class);
			switch (detail.getFetchMMUDataFor().toLowerCase()) {
			case "prescription":
				res = getPrescriptionData(detail);
				break;
			case "investigation":
				res = getInvestigationData(detail);
				break;
			case "referral":
				res = getReferralData(detail);
				break;
			default:
				res = "Invalid master param to fetch data";
			}
		} catch (Exception e) {
			throw new IEMRException(e.getMessage());
		}
		return res;
	}

	private String getReferralData(ProviderSpecificRequest request) throws IEMRException {
		Map<String, Object> response = new HashMap<String, Object>();
		List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
		Map<String, Object> column = new HashMap<String, Object>();
		Map<String, String> value = new HashMap<String, String>();
		column = new HashMap<>();
		column.put("columnName", "Higher Healthcare Centre");
		column.put("keyName", "referredToInstituteName");
		columns.add(column);
		column = new HashMap<>();
		column.put("columnName", "Additional Services");
		column.put("keyName", "refrredToAdditionalServiceList");
		columns.add(column);
		column = new HashMap<>();
		column.put("columnName", "Referral Reason");
		column.put("keyName", "referralReason");
		columns.add(column);
		column = new HashMap<>();
		column.put("columnName", "Revisit Date");
		column.put("keyName", "revisitDate");
		columns.add(column);
		column = new HashMap<>();
		column.put("columnName", "Date of Referral");
		column.put("keyName", "createdDate");
		columns.add(column);
		response.put("columns", columns);
		try {
			// ArrayList<BenReferDetails> resList =
			// benReferDetailsRepo.getBenReferDetails2(request.getBenRegID(),
			// request.getVisitCode());
//			ArrayList<Object[]> resList = benReferDetailsRepo.getBenReferDetails(request.getBenRegID(),
//					request.getVisitCode());
//			BenReferDetails referDetails = BenReferDetails.getBenReferDetails(resList);
//			value.put("data",
//					commonDoctorServiceImpl.getReferralDetails(request.getBenRegID(), request.getVisitCode()));
			// values.add(value);

			ArrayList<BenReferDetails> resList = benReferDetailsRepo
					.findByBeneficiaryRegIDAndVisitCode(request.getBenRegID(), request.getVisitCode());
			if (resList != null && resList.size() > 0)
				response.put("data", resList.get(resList.size() - 1));

		} catch (Exception e) {
			throw new IEMRException("Error while fetching Referral data");
		}
		return new Gson().toJson(response);
	}

	private String getPrescriptionData(ProviderSpecificRequest request) throws IEMRException {
		Map<String, Object> response = new HashMap<String, Object>();
		List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
		Map<String, Object> column = new HashMap<String, Object>();
		column = new HashMap<>();
		column.put("columnName", "Drug Name");
		column.put("keyName", "drugName");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Strength");
		column.put("keyName", "drugStrength");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Duration");
		column.put("keyName", "duration");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Unit of duration");
		column.put("keyName", "unit");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Form");
		column.put("keyName", "formName");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Frequency");
		column.put("keyName", "frequency");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Quantity Prescribed");
		column.put("keyName", "qtyPrescribed");
		columns.add(column);

		column = new HashMap<String, Object>();
		column.put("columnName", "Prescribed Date");
		column.put("keyName", "createdDate");
		columns.add(column);

		response.put("columns", columns);
		try {
			// ArrayList<PrescribedDrugDetail> resList =
			// prescribedDrugDetailRepo.getBenPrescribedDrugDetails2(request.getBenRegID(),
			// request.getVisitCode());
			ArrayList<Object[]> resList = prescribedDrugDetailRepo.getBenPrescribedDrugDetails(request.getBenRegID(),
					request.getVisitCode());

			ArrayList<PrescribedDrugDetail> prescribedDrugs = PrescribedDrugDetail.getprescribedDrugs(resList);
			response.put("data", prescribedDrugs);
		} catch (Exception e) {
			throw new IEMRException("Error while fetching prescription data");
		}
		return new Gson().toJson(response);
	}

	String getInvestigationData(ProviderSpecificRequest request) throws IEMRException {
		Map<String, Object> response = new HashMap<String, Object>();
		List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
		Map<String, Object> column = new HashMap<String, Object>();
		column = new HashMap<>();
		column.put("columnName", "Test Name");
		column.put("keyName", "procedureName");
		columns.add(column);

		response.put("columns", columns);
		try {
//			ArrayList<LabTestOrderDetail> labTestOrders = labTestOrderDetailRepo.getLabTestOrderDetails2(request.getBenRegID(), request.getVisitCode());
			ArrayList<Object[]> labTestOrders = labTestOrderDetailRepo.getLabTestOrderDetails(request.getBenRegID(),
					request.getVisitCode());
			WrapperBenInvestigationANC labTestOrdersList = LabTestOrderDetail.getLabTestOrderDetails(labTestOrders);

			// Checking RBS Test is already performed or not
			int rbsStatus = 0;
			if (labTestOrders.size() > 0) {

				for (LabTestOrderDetail objTestDetails : labTestOrdersList.getLaboratoryList()) {
					if (objTestDetails.getProcedureName().equalsIgnoreCase("RBS Test")) {

						rbsStatus = 1;
						break;
					}

				}

			}

			BenPhysicalVitalDetail benVitalDetailList = new BenPhysicalVitalDetail();
			if (rbsStatus == 0) {
				// Fetching RBS Test Result from Vitals
				benVitalDetailList = benPhysicalVitalRepo.getBenPhysicalVitalDetail(request.getBenRegID(),
						request.getVisitCode());
				if (benVitalDetailList != null && !Objects.equals(benVitalDetailList.getRbsTestResult(), null)) {
					if (labTestOrders.size() > 0) {

						LabTestOrderDetail rbsTestDetails = new LabTestOrderDetail("RBS Test");
						labTestOrdersList.getLaboratoryList().add(rbsTestDetails);
					} else {

						labTestOrdersList = LabTestOrderDetail.getRBSTestOrderDetailsFromVitals(benVitalDetailList);

					}
				}

			}

			response.put("data", labTestOrdersList);
		} catch (Exception e) {
			throw new IEMRException("Error while fetching Investigation data");
		}
		return new Gson().toJson(response);
	}

	@Override
	public String calculateBMIStatus(String request) throws IEMRException {
		String result = "";
		Map<String, String> map = new HashMap<String, String>();
		try {
			BmiCalculation bmiMap = InputMapper.gson().fromJson(request, BmiCalculation.class);
			if (bmiMap != null && bmiMap.getYearMonth() != null && bmiMap.getGender() != null
					&& bmiMap.getBmi() != 0.0d) {
				String[] ar = bmiMap.getYearMonth().split(" ");
				if (ar != null && ar.length > 0) {
					Integer years = Integer.valueOf(ar[0]);
					Integer months = Integer.valueOf(ar[3]);
					Integer totalMonths = (years * 12) + months;
					BmiCalculation calc = bmiCalculationRepo.getBMIDetails(totalMonths, bmiMap.getGender());
					if (calc == null)
						throw new IEMRException("No data found for this category");
					if (calc != null && bmiMap.getBmi() >= calc.getN1SD() && bmiMap.getBmi() < calc.getP1SD())
						result = "Normal";
					else if (calc != null && bmiMap.getBmi() >= calc.getN2SD() && bmiMap.getBmi() < calc.getN1SD())
						result = "Mild malnourished";
					else if (calc != null && bmiMap.getBmi() >= calc.getN3SD() && bmiMap.getBmi() < calc.getN2SD())
						result = "Moderately Malnourished";
					else if (calc != null && bmiMap.getBmi() < calc.getN3SD())
						result = "Severely Malnourished";
					else if (calc != null && bmiMap.getBmi() >= calc.getP1SD() && bmiMap.getBmi() < calc.getP2SD())
						result = "Overweight";
					else if (calc != null && bmiMap.getBmi() >= calc.getP2SD() && bmiMap.getBmi() < calc.getP3SD())
						result = "Obese";
					else if (calc != null && bmiMap.getBmi() >= calc.getP3SD())
						result = "Severely Obese";
				}
			}
		} catch (Exception e) {
			throw new IEMRException("Error while calculating BMI status:" + e.getMessage());
		}
		if (result != null) {
			map.put("bmiStatus", result);
			return new Gson().toJson(map);
		} else
			throw new IEMRException("Error while calculating BMI status");
	}

	public Long saveOralExamination(OralExamination oralDetails) throws IEMRException {

		if (oralDetails != null) {
			oralDetails = getOralExaminationDetails(oralDetails);

			oralDetails = oralExaminationRepo.save(oralDetails);
			if (oralDetails != null && oralDetails.getID() > 0)
				return oralDetails.getID();
			else
				throw new IEMRException("error in saving OralExamination to DB");
		} else
			throw new IEMRException("OralExamination is NULL");

	}

	public OralExamination getOralExaminationDetails(OralExamination oralExamination) throws IEMRException {
		if (oralExamination != null) {

			if (oralExamination.getPreMalignantLesionTypeList() != null
					&& oralExamination.getPreMalignantLesionTypeList().length > 0) {

				StringBuffer sb = new StringBuffer();

				for (String preMalignantLesionType : oralExamination.getPreMalignantLesionTypeList()) {
					sb.append(preMalignantLesionType).append("||");
				}

				if (sb.length() >= 2) {
					sb.substring(0, (sb.length() - 2));

					oralExamination.setPreMalignantLesionType(sb.toString());
				}
			}

		}
		return oralExamination;

	}

}
