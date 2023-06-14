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
package com.iemr.mmu.service.common.master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iemr.mmu.data.doctor.ChiefComplaintMaster;
import com.iemr.mmu.data.labModule.ProcedureData;
import com.iemr.mmu.data.masterdata.anc.DiseaseType;
import com.iemr.mmu.data.masterdata.anc.PersonalHabitType;
import com.iemr.mmu.data.masterdata.ncdscreening.BloodGlucoseType;
import com.iemr.mmu.data.masterdata.ncdscreening.CBAC;
import com.iemr.mmu.data.masterdata.ncdscreening.CBACResponse;
import com.iemr.mmu.data.masterdata.ncdscreening.CervicalLymph;
import com.iemr.mmu.data.masterdata.ncdscreening.InpectionofBreasts;
import com.iemr.mmu.data.masterdata.ncdscreening.MouthOpening;
import com.iemr.mmu.data.masterdata.ncdscreening.OralCavityFinding;
import com.iemr.mmu.data.masterdata.ncdscreening.PalpationOfBreasts;
import com.iemr.mmu.data.masterdata.ncdscreening.PalpationOfLymph;
import com.iemr.mmu.data.masterdata.ncdscreening.PalpationOfOralCavity;
import com.iemr.mmu.data.masterdata.ncdscreening.ScreeningDiseases;
import com.iemr.mmu.data.masterdata.ncdscreening.TemperomandibularJoin;
import com.iemr.mmu.data.masterdata.ncdscreening.VisualExamination;
import com.iemr.mmu.data.masterdata.nurse.FamilyMemberType;
import com.iemr.mmu.repo.doctor.ChiefComplaintMasterRepo;
import com.iemr.mmu.repo.doctor.LabTestMasterRepo;
import com.iemr.mmu.repo.labModule.ProcedureRepo;
import com.iemr.mmu.repo.masterrepo.anc.AllergicReactionTypesRepo;
import com.iemr.mmu.repo.masterrepo.anc.DiseaseTypeRepo;
import com.iemr.mmu.repo.masterrepo.anc.PersonalHabitTypeRepo;
import com.iemr.mmu.repo.masterrepo.ncdScreening.BPAndDiabeticStatusRepo;
import com.iemr.mmu.repo.masterrepo.ncdScreening.BloodGlucoseMasterRepo;
import com.iemr.mmu.repo.masterrepo.ncdScreening.CBACRepo;
import com.iemr.mmu.repo.masterrepo.ncdScreening.CervicalLympRepo;
import com.iemr.mmu.repo.masterrepo.ncdScreening.IDRS_ScreenQuestionsRepo;
import com.iemr.mmu.repo.masterrepo.ncdScreening.InpectionBreastsRepo;
import com.iemr.mmu.repo.masterrepo.ncdScreening.MouthOpeningRepo;
import com.iemr.mmu.repo.masterrepo.ncdScreening.NCDScreeningConditionRepo;
import com.iemr.mmu.repo.masterrepo.ncdScreening.NCDScreeningReasonRepo;
import com.iemr.mmu.repo.masterrepo.ncdScreening.OralCavityFindingRepo;
import com.iemr.mmu.repo.masterrepo.ncdScreening.PalpationBreastsRepo;
import com.iemr.mmu.repo.masterrepo.ncdScreening.PalpationOfLymphRepo;
import com.iemr.mmu.repo.masterrepo.ncdScreening.PalpationOralCavityRepo;
import com.iemr.mmu.repo.masterrepo.ncdScreening.PhysicalActivityRepo;
import com.iemr.mmu.repo.masterrepo.ncdScreening.ScreeningDiseaseMasterRepo;
import com.iemr.mmu.repo.masterrepo.ncdScreening.TemperomandibularRepo;
import com.iemr.mmu.repo.masterrepo.ncdScreening.VisualExamRepo;
import com.iemr.mmu.repo.masterrepo.nurse.FamilyMemberMasterRepo;

@Service
public class NCDScreeningMasterServiceImpl implements NCDScreeningMasterService {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private NCDScreeningConditionRepo ncdScreeningConditionRepo;
	private NCDScreeningReasonRepo ncdScreeningReasonRepo;
	private BPAndDiabeticStatusRepo bpAndDiabeticStatusRepo;
	private LabTestMasterRepo labTestMasterRepo;
	private ChiefComplaintMasterRepo chiefComplaintMasterRepo;
	private ProcedureRepo procedureRepo;
	@Autowired
	private CBACRepo cBACRepo;
	@Autowired
	private ScreeningDiseaseMasterRepo screeningDiseaseMasterRepo;
	@Autowired
	private BloodGlucoseMasterRepo bloodGlucoseMasterRepo;
	@Autowired
	private CervicalLympRepo cervicalLympRepo;
	@Autowired
	private InpectionBreastsRepo inpectionBreastsRepo;

	@Autowired
	private MouthOpeningRepo mouthOpeningRepo;

	@Autowired
	OralCavityFindingRepo oralCavityFindingRepo;

	@Autowired
	PalpationBreastsRepo palpationBreastsRepo;

	@Autowired
	PalpationOfLymphRepo palpationOfLymphRepo;

	@Autowired
	PalpationOralCavityRepo palpationOralCavityRepo;

	@Autowired
	private TemperomandibularRepo temperomandibularRepo;

	@Autowired
	private VisualExamRepo visualExamRepo;
	@Autowired
	private IDRS_ScreenQuestionsRepo iDRS_ScreenQuestionsRepo;
	@Autowired
	private PhysicalActivityRepo physicalActivityRepo;
	@Autowired
	private DiseaseTypeRepo diseaseTypeRepo;
	@Autowired
	private FamilyMemberMasterRepo familyMemberMasterRepo;
	@Autowired
	private PersonalHabitTypeRepo personalHabitTypeRepo;
	@Autowired
	private AllergicReactionTypesRepo allergicReactionTypesRepo;

	@Autowired
	public void setNcdScreeningConditionRepo(NCDScreeningConditionRepo ncdScreeningConditionRepo) {
		this.ncdScreeningConditionRepo = ncdScreeningConditionRepo;
	}

	@Autowired
	public void setNcdScreeningReasonRepo(NCDScreeningReasonRepo ncdScreeningReasonRepo) {
		this.ncdScreeningReasonRepo = ncdScreeningReasonRepo;
	}

	@Autowired
	public void setBpAndDiabeticStatusRepo(BPAndDiabeticStatusRepo bpAndDiabeticStatusRepo) {
		this.bpAndDiabeticStatusRepo = bpAndDiabeticStatusRepo;
	}

	@Autowired
	public void setLabTestMasterRepo(LabTestMasterRepo labTestMasterRepo) {
		this.labTestMasterRepo = labTestMasterRepo;
	}

	@Autowired
	public void setChiefComplaintMasterRepo(ChiefComplaintMasterRepo chiefComplaintMasterRepo) {
		this.chiefComplaintMasterRepo = chiefComplaintMasterRepo;
	}

	@Autowired
	public void setProcedureRepo(ProcedureRepo procedureRepo) {
		this.procedureRepo = procedureRepo;
	}

	@Override
	public List<Object[]> getNCDScreeningConditions() {
		List<Object[]> ncdScreeningConditions = null;
		try {
			ncdScreeningConditions = ncdScreeningConditionRepo.getNCDScreeningConditions();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ncdScreeningConditions;
	}

	@Override
	public List<Object[]> getNCDScreeningReasons() {
		List<Object[]> ncdScreeningReasons = null;
		try {
			ncdScreeningReasons = ncdScreeningReasonRepo.getNCDScreeningReasons();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ncdScreeningReasons;
	}

	@Override
	public List<Object[]> getBPAndDiabeticStatus(Boolean isBPStatus) {
		List<Object[]> bpAndDiabeticStatus = null;
		try {
			bpAndDiabeticStatus = bpAndDiabeticStatusRepo.getBPAndDiabeticStatus(isBPStatus);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return bpAndDiabeticStatus;
	}

	@Override
	public ArrayList<Object[]> getNCDTest() {
		ArrayList<Object[]> labTests = null;
		try {
			labTests = labTestMasterRepo.getTestsBYVisitCategory("NCD Screening");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return labTests;
	}

	@Override
	public List<Object[]> getChiefComplaintMaster() {
		List<Object[]> ccList = null;
		try {
			ccList = chiefComplaintMasterRepo.getChiefComplaintMaster();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ccList;
	}

	@Override
	public String getNCDScreeningMasterData(Integer visitCategoryID, Integer providerServiceMapID, String gender) {
		Map<String, Object> resMap = new HashMap<String, Object>();

		resMap.put("chiefComplaintMaster",
				ChiefComplaintMaster.getChiefComplaintMasters((ArrayList<Object[]>) getChiefComplaintMaster()));

		ArrayList<Object[]> DiseaseTypes = diseaseTypeRepo.getDiseaseTypes();
		resMap.put("DiseaseTypes", DiseaseType.getDiseaseTypes(DiseaseTypes));

		ArrayList<Object[]> familyMemberTypes = familyMemberMasterRepo.getFamilyMemberTypeMaster();
		resMap.put("familyMemberTypes", FamilyMemberType.getFamilyMemberTypeMasterData(familyMemberTypes));

		
		ArrayList<ScreeningDiseases> sd = screeningDiseaseMasterRepo.getScreeningDiseases();
		resMap.put("screeningCondition", sd);
		
		ArrayList<BloodGlucoseType> bg = bloodGlucoseMasterRepo.getBloodGlucoseType();
		resMap.put("bloodGlucoseType", bg);
	
		
		
		

		
		resMap.put("IDRSQuestions", iDRS_ScreenQuestionsRepo.findByDeleted(false));
		List<CBAC> cbacData = cBACRepo.getCBAC(gender.toLowerCase());
		if (cbacData != null && cbacData.size() > 0)
			resMap.put("CBACQuestions", getCBACDataOrdered(cbacData));
		Map<String, Object> oralCancerMap = new HashMap<String, Object>();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		ArrayList<OralCavityFinding> oralCavity = oralCavityFindingRepo.getOralCavityMasters();
		oralCancerMap.put("oralCavity", oralCavity);
		ArrayList<MouthOpening> mouhtOpening = mouthOpeningRepo.getMouthOpeningMasters();
		oralCancerMap.put("mouthOpening", mouhtOpening);
		ArrayList<PalpationOfOralCavity> palpationOralCavity = palpationOralCavityRepo.getPalpationOralCavityMasters();
		oralCancerMap.put("palpationOralCavity", palpationOralCavity);
		ArrayList<TemperomandibularJoin> temporomandibularJoin = temperomandibularRepo
				.getTemperomandibularJoinMasters();
		oralCancerMap.put("temporomandibularJoin", temporomandibularJoin);
		List<CervicalLymph> cervicalLymphNode = cervicalLympRepo.getCervicalScreening();
		oralCancerMap.put("cervicalLymphNode", cervicalLymphNode);

		Map<String, Object> cervicalCancerMap = new HashMap<String, Object>();
		ArrayList<VisualExamination> visualExamination = visualExamRepo.getVisualExaminationMasters();
		cervicalCancerMap.put("visualExamination", visualExamination);

		ArrayList<InpectionofBreasts> inspectionOfBreasts = inpectionBreastsRepo.getBreastInspectionMasters();
		ArrayList<PalpationOfBreasts> palpationOfBreasts = palpationBreastsRepo.getPalpationBreastsMasters();
		ArrayList<PalpationOfLymph> palpationLymphNodes = palpationOfLymphRepo.getPalpationLymphMasters();
		
		ArrayList<Object[]> tobaccoUseStatus = personalHabitTypeRepo.getPersonalHabitTypeMaster("Tobacco Use Status");
		ArrayList<Object[]> typeOfTobaccoProducts = personalHabitTypeRepo
				.getPersonalHabitTypeMaster("Type of Tobacco Use");
		ArrayList<Object[]> alcoholUseStatus = personalHabitTypeRepo
				.getPersonalHabitTypeMaster("Alcohol Intake Status");
		ArrayList<Object[]> typeOfAlcoholProducts = personalHabitTypeRepo.getPersonalHabitTypeMaster("Type of Alcohol");
		ArrayList<Object[]> frequencyOfAlcoholIntake = personalHabitTypeRepo
				.getPersonalHabitTypeMaster("Frequency of Alcohol Intake");
		ArrayList<Object[]> quantityOfAlcoholIntake = personalHabitTypeRepo
				.getPersonalHabitTypeMaster("Average Quantity of Alcohol consumption");
		
		HashMap<String, Object> breastCancerMap = new HashMap<String, Object>();
		breastCancerMap.put("inspectionOfBreasts", inspectionOfBreasts);
		breastCancerMap.put("palpationOfBreasts", palpationOfBreasts);
		breastCancerMap.put("palpationLymphNodes", palpationLymphNodes);
		resMap.put("oralCancer", oralCancerMap);
		resMap.put("breastCancer", breastCancerMap);
		resMap.put("cervicalCancer", cervicalCancerMap);
		resMap.put("physicalActivity", physicalActivityRepo.findByDeleted(false));
		ArrayList<Object[]> procedures = procedureRepo.getProcedureMasterData(providerServiceMapID, gender);
		resMap.put("procedures", ProcedureData.getProcedures(procedures));
		
		resMap.put("tobaccoUseStatus", PersonalHabitType.getPersonalHabitTypeMasterData(tobaccoUseStatus));
		resMap.put("typeOfTobaccoProducts", PersonalHabitType.getPersonalHabitTypeMasterData(typeOfTobaccoProducts));
		resMap.put("alcoholUseStatus", PersonalHabitType.getPersonalHabitTypeMasterData(alcoholUseStatus));
		resMap.put("typeOfAlcoholProducts", PersonalHabitType.getPersonalHabitTypeMasterData(typeOfAlcoholProducts));
		resMap.put("frequencyOfAlcoholIntake",
				PersonalHabitType.getPersonalHabitTypeMasterData(frequencyOfAlcoholIntake));
		resMap.put("quantityOfAlcoholIntake",
				PersonalHabitType.getPersonalHabitTypeMasterData(quantityOfAlcoholIntake));
		resMap.put("AllergicReactionTypes", allergicReactionTypesRepo.findByDeleted(false));
		
		return gson.toJson(resMap);
	}

	public List<CBACResponse> getCBACDataOrdered(List<CBAC> cbacList) {
		List<CBACResponse> testListReturn = new ArrayList<CBACResponse>();
		Integer preQuestion = null;
		List<HashMap<String, String>> optionsList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map;
		int pointer = 0;
		CBACResponse cbacRes = new CBACResponse();
		try {
			for (CBAC t : cbacList) {
				if (preQuestion == null || preQuestion == t.getQuestionid()) {
					preQuestion = t.getQuestionid();
					map = new HashMap<String, String>();
					if (t.getRange() != null && t.getCbac_Score() != null) {
						map.put("option", t.getRange());
						map.put("score", String.valueOf(t.getCbac_Score()));
						optionsList.add(map);
					}
					if ((pointer + 1) == cbacList.size()
							|| t.getQuestionid() != cbacList.get(pointer + 1).getQuestionid()) {
						cbacRes = new CBACResponse();
						cbacRes.setQuestion(t.getName());
						cbacRes.setQuestionId(preQuestion);
						cbacRes.setSection(t.getSection());
						cbacRes.setQuestionType(t.getQuestiontype());
						cbacRes.setOptions(optionsList);
						testListReturn.add(cbacRes);
					}
					pointer++;

				} else {
					optionsList = new ArrayList<HashMap<String, String>>();
					preQuestion = t.getQuestionid();
					map = new HashMap<String, String>();
					if (t.getRange() != null && t.getCbac_Score() != null) {
						map.put("option", t.getRange());
						map.put("score", String.valueOf(t.getCbac_Score()));
						optionsList.add(map);
					}

					if ((pointer + 1) == cbacList.size()
							|| t.getQuestionid() != cbacList.get(pointer + 1).getQuestionid()) {
						cbacRes = new CBACResponse();
						cbacRes.setQuestion(t.getName());
						cbacRes.setQuestionId(preQuestion);
						cbacRes.setSection(t.getSection());
						cbacRes.setQuestionType(t.getQuestiontype());
						if (optionsList != null && optionsList.size() > 0)
							cbacRes.setOptions(optionsList);
						testListReturn.add(cbacRes);
					}
					pointer++;
				}

			}
		} catch (Exception e) {
			logger.info("Error while ordering CBAC data:" + e.getLocalizedMessage());
		}
		return testListReturn;
	}
}
