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

import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.iemr.mmu.utils.exception.IEMRException;

@Service
public interface FamilyPlanningService {

	// save
	public String saveNurseDataFP(JsonObject requestOBJ, String Authorization) throws Exception;

	public int saveDoctorDataFP(JsonObject requestOBJ, String Authorization) throws Exception;

	// get
	public String getBenVisitDetailsFrmNurseFP(Long benRegID, Long visitCode) throws Exception;

	public String getBeneficiaryVitalDetailsFP(Long beneficiaryRegID, Long visitCode) throws Exception;

	public String getBeneficiaryFPDetailsFP(Long beneficiaryRegID, Long visitCode) throws Exception;

	public String getNurseDataFP(Long benRegID, Long visitCode) throws Exception;

	public String getBenCaseRecordFromDoctorFP(Long benRegID, Long visitCode) throws IEMRException;

	// update
	public String updateFPDataFP(JsonObject requestOBJ) throws IEMRException;

	public int updateBenVitalDetailsFP(JsonObject vitalDetailsOBJ) throws Exception;

	public Long updateDoctorDataFP(JsonObject requestOBJ, String Authorization) throws Exception;

}
