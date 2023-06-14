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
package com.iemr.mmu.service.family_planning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iemr.mmu.data.labModule.ProcedureData;
import com.iemr.mmu.repo.doctor.ChiefComplaintMasterRepo;
import com.iemr.mmu.repo.labModule.ProcedureRepo;
import com.iemr.mmu.repo.masterrepo.AgeUnitRepo;
import com.iemr.mmu.repo.masterrepo.GenderMasterRepo;
import com.iemr.mmu.repo.masterrepo.nurse.FPCounselledOnRepo;
import com.iemr.mmu.repo.masterrepo.nurse.FPMethodFollowupRepo;
import com.iemr.mmu.repo.masterrepo.nurse.FPSideEffectsRepo;
import com.iemr.mmu.repo.masterrepo.nurse.FertilityStatusRepo;
import com.iemr.mmu.repo.masterrepo.nurse.IUCDinsertiondonebyRepo;
import com.iemr.mmu.repo.masterrepo.nurse.TypeofIUCDinsertedRepo;
import com.iemr.mmu.utils.exception.IEMRException;

@Service
public class FamilyPlanningMasterServiceImpl implements FamilyPlanningMasterService {

	private static final String female = null;
	@Autowired
	private FPMethodFollowupRepo fPMethodFollowupRepo;
	@Autowired
	private FPSideEffectsRepo fPSideEffectsRepo;
	@Autowired
	private FertilityStatusRepo fertilityStatusRepo;
	@Autowired
	private TypeofIUCDinsertedRepo typeofIUCDinsertedRepo;
	@Autowired
	private IUCDinsertiondonebyRepo iUCDinsertiondonebyRepo;
	@Autowired
	private FPCounselledOnRepo fPCounselledOnRepo;
	@Autowired
	private ChiefComplaintMasterRepo chiefComplaintMasterRepo;
	@Autowired
	private AgeUnitRepo ageUnitRepo;
	@Autowired
	private GenderMasterRepo genderMasterRepo;
	@Autowired
	private ProcedureRepo procedureRepo;

	public String getMasterDataFP(Integer visitCategoryID, int psmID, String gender) throws IEMRException {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

		Map<String, Object> resMap = new HashMap<>();

		resMap.put("m_FPSideEffects", fPSideEffectsRepo.findByDeletedOrderByName(false));
		resMap.put("m_FertilityStatus", fertilityStatusRepo.findByDeletedOrderByName(false));
		resMap.put("m_TypeofIUCDinserted", typeofIUCDinsertedRepo.findByDeletedOrderByName(false));
		resMap.put("m_IUCDinsertiondoneby", iUCDinsertiondonebyRepo.findByDeletedOrderByName(false));
		resMap.put("m_FPCounselledOn", fPCounselledOnRepo.findByDeletedOrderByName(false));
		resMap.put("m_ageunits", ageUnitRepo.findByDeletedOrderByName(false));
		resMap.put("m_gender", genderMasterRepo.findByDeletedOrderByGenderName(false));

		ArrayList<Object[]> procedures = procedureRepo.getProcedureMasterData(psmID, gender);
		resMap.put("procedures", ProcedureData.getProcedures(procedures));

		resMap.put("chiefComplaintMaster", chiefComplaintMasterRepo.findByDeletedOrderByChiefComplaint(false));

		if(gender != null && (gender.equalsIgnoreCase("female") || gender.equalsIgnoreCase("male")))
			resMap.put("m_fpmethodfollowup", fPMethodFollowupRepo.findAllNameByGender(gender));
		else if(gender != null)
			resMap.put("m_fpmethodfollowup", fPMethodFollowupRepo.findByDeletedOrderByName(false));
			
		
			

		return gson.toJson(resMap);
	}
	
	
}
