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
package com.iemr.hwc.service.anc;

import com.google.gson.JsonObject;
import com.iemr.hwc.data.anc.HrpStatusAndReasonsRequestModel;
import com.iemr.hwc.data.anc.SysObstetricExamination;
import com.iemr.hwc.utils.exception.IEMRException;

public interface ANCService {

	public String saveANCNurseData(JsonObject requestOBJ, String Authorization) throws Exception;
	
	public void deleteVisitDetails(JsonObject requestOBJ) throws Exception;

	// int UpdateANCVisitDetails(JsonObject jsnOBJ) throws Exception;

	String getBenVisitDetailsFrmNurseANC(Long benRegID, Long visitCode);

	String getBenANCHistoryDetails(Long benRegID, Long visitCode);

	String getANCExaminationDetailsData(Long benRegID, Long visitCode);

	String getBeneficiaryVitalDetails(Long beneficiaryRegID, Long visitCode);

	String getBenANCDetailsFrmNurseANC(Long benRegID, Long visitCode) throws IEMRException;

	String getHRPStatus(HrpStatusAndReasonsRequestModel hrpStatusAndReasonsRequestModel) throws Exception;

	public SysObstetricExamination getHRPInformation(Long beneficiaryRegId) throws IEMRException;

	public Long saveANCDoctorData(JsonObject jsnOBJ, String authorization) throws Exception;

	public String getBenCaseRecordFromDoctorANC(Long benRegID, Long visitCode);

	public int updateBenANCDetails(JsonObject jsnOBJ) throws Exception;

	public int updateBenANCHistoryDetails(JsonObject jsnOBJ) throws Exception;

	public int updateBenANCVitalDetails(JsonObject jsnOBJ) throws Exception;

	public int updateBenANCExaminationDetails(JsonObject jsnOBJ) throws Exception;

	public Long updateANCDoctorData(JsonObject jsnOBJ, String authorization) throws Exception;

}
