/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.service.common.master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.iemr.mmu.data.masterdata.nurse.CancerDiseaseType;
import com.iemr.mmu.data.masterdata.nurse.CancerPersonalHabitType;
import com.iemr.mmu.data.masterdata.nurse.FamilyMemberType;
import com.iemr.mmu.data.masterdata.nurse.VisitCategory;
import com.iemr.mmu.data.masterdata.nurse.VisitReason;
import com.iemr.mmu.repo.masterrepo.nurse.CancerDiseaseMasterRepo;
import com.iemr.mmu.repo.masterrepo.nurse.CancerPersonalHabitMasterRepo;
import com.iemr.mmu.repo.masterrepo.nurse.FamilyMemberMasterRepo;
import com.iemr.mmu.repo.masterrepo.nurse.VisitCategoryMasterRepo;
import com.iemr.mmu.repo.masterrepo.nurse.VisitReasonMasterRepo;

@Service
public class NurseMasterDataServiceImpl implements NurseMasterDataService {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private CancerDiseaseMasterRepo cancerDiseaseMasterRepo;

	@Autowired
	private CancerPersonalHabitMasterRepo cancerPersonalHabitMasterRepo;

	@Autowired
	private FamilyMemberMasterRepo familyMemberMasterRepo;

	@Autowired
	private VisitCategoryMasterRepo visitCategoryMasterRepo;

	@Autowired
	private VisitReasonMasterRepo visitReasonMasterRepo;

	public String GetVisitReasonAndCategories() {
		Map<String, Object> resMap = new HashMap<String, Object>();
		ArrayList<Object[]> visitCategories = visitCategoryMasterRepo.getVisitCategoryMaster();
		ArrayList<Object[]> visitReasons = visitReasonMasterRepo.getVisitReasonMaster();

		resMap.put("visitCategories", VisitCategory.getVisitCategoryMasterData(visitCategories));
		resMap.put("visitReasons", VisitReason.getVisitReasonMasterData(visitReasons));

		return new Gson().toJson(resMap);
	}

	public String getCancerScreeningMasterDataForNurse() {
		Map<String, Object> resMap = new HashMap<String, Object>();
		ArrayList<Object[]> DiseaseTypes = cancerDiseaseMasterRepo.getCancerDiseaseMaster();
		ArrayList<Object[]> tobaccoUseStatus = cancerPersonalHabitMasterRepo
				.getCancerPersonalHabitTypeMaster("Tobacco Use Status");
		ArrayList<Object[]> typeOfTobaccoProducts = cancerPersonalHabitMasterRepo
				.getCancerPersonalHabitTypeMaster("Type of Tobacco Product");
		ArrayList<Object[]> alcoholUseStatus = cancerPersonalHabitMasterRepo
				.getCancerPersonalHabitTypeMaster("Alcohol Usage");
		ArrayList<Object[]> frequencyOfAlcoholIntake = cancerPersonalHabitMasterRepo
				.getCancerPersonalHabitTypeMaster("Frequency of Alcohol Intake");
		ArrayList<Object[]> dietTypes = cancerPersonalHabitMasterRepo.getCancerPersonalHabitTypeMaster("Dietary Type ");
		ArrayList<Object[]> oilConsumed = cancerPersonalHabitMasterRepo
				.getCancerPersonalHabitTypeMaster("Oil Consumed");
		ArrayList<Object[]> physicalActivityType = cancerPersonalHabitMasterRepo
				.getCancerPersonalHabitTypeMaster("Physical Activity Type ");

		ArrayList<Object[]> familyMemberTypes = familyMemberMasterRepo.getFamilyMemberTypeMaster();
		// Later remove these 2 from here, These moved to GetVisitReasonAndCategories
		// function
		ArrayList<Object[]> visitCategories = visitCategoryMasterRepo.getVisitCategoryMaster();
		ArrayList<Object[]> visitReasons = visitReasonMasterRepo.getVisitReasonMaster();

		try {
			resMap.put("CancerDiseaseType", CancerDiseaseType.getCancerDiseaseTypeMasterData(DiseaseTypes));
			resMap.put("tobaccoUseStatus",
					CancerPersonalHabitType.getCancerPersonalHabitTypeMasterData(tobaccoUseStatus));
			resMap.put("typeOfTobaccoProducts",
					CancerPersonalHabitType.getCancerPersonalHabitTypeMasterData(typeOfTobaccoProducts));
			resMap.put("alcoholUseStatus",
					CancerPersonalHabitType.getCancerPersonalHabitTypeMasterData(alcoholUseStatus));
			resMap.put("frequencyOfAlcoholIntake",
					CancerPersonalHabitType.getCancerPersonalHabitTypeMasterData(frequencyOfAlcoholIntake));
			resMap.put("dietTypes", CancerPersonalHabitType.getCancerPersonalHabitTypeMasterData(dietTypes));
			resMap.put("oilConsumed", CancerPersonalHabitType.getCancerPersonalHabitTypeMasterData(oilConsumed));
			resMap.put("physicalActivityType",
					CancerPersonalHabitType.getCancerPersonalHabitTypeMasterData(physicalActivityType));

			resMap.put("familyMemberTypes", FamilyMemberType.getFamilyMemberTypeMasterData(familyMemberTypes));

			// Later remove these 2 from here, These moved to GetVisitReasonAndCategories
			// function
			resMap.put("visitCategories", VisitCategory.getVisitCategoryMasterData(visitCategories));
			resMap.put("visitReasons", VisitReason.getVisitReasonMasterData(visitReasons));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}

		// System.out.println(new Gson().toJson(resMap));
		return new Gson().toJson(resMap);

	}

}
