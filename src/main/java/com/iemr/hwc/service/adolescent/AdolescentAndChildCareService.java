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
package com.iemr.hwc.service.adolescent;

import java.sql.SQLException;
import java.text.ParseException;

import com.google.gson.JsonObject;
import com.iemr.hwc.utils.exception.IEMRException;

public interface AdolescentAndChildCareService {

	public String saveNurseData(JsonObject requestOBJ, String Authorization) throws SQLException, Exception;

	public String getBenVisitDetailsFrmNurseCAC(Long benRegID, Long visitCode) throws Exception;

	public String getBirthAndImmuniHistory(Long benRegID, Long visitCode) throws IEMRException;

	public String getBeneficiaryVitalDetails(Long beneficiaryRegID, Long visitCode) throws Exception;

	public String getBeneficiaryImmunizationServiceDetails(Long beneficiaryRegID, Long visitCode) throws Exception;

	public int updateBenVitalDetailsCAC(JsonObject vitalDetailsOBJ) throws Exception;

	public int updateBenHistoryDetails(JsonObject historyOBJ) throws IEMRException, ParseException;

	public int updateBenImmunServiceDetailsCAC(JsonObject immunServiceOBJ) throws Exception;

	public int saveDoctorDataCAC(JsonObject requestOBJ, String Authorization) throws Exception;

	public String getBenCaseRecordFromDoctorCAC(Long benRegID, Long visitCode) throws IEMRException;

	public Long updateDoctorDataCAC(JsonObject requestOBJ, String Authorization) throws Exception;

	public String getNurseDataCAC(Long benRegID, Long visitCode) throws Exception;

}
