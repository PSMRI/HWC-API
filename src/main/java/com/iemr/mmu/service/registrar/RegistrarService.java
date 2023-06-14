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
package com.iemr.mmu.service.registrar;

import com.google.gson.JsonObject;
import com.iemr.mmu.data.registrar.BeneficiaryData;

public interface RegistrarService {
	public String getRegWorkList(int servicePointID);

	public BeneficiaryData createBeneficiary(JsonObject benD);

	public Long createBeneficiaryDemographic(JsonObject benD, Long benRegID);

	public Long createBeneficiaryPhoneMapping(JsonObject benD, Long benRegID);

	public Long createBeneficiaryDemographicAdditional(JsonObject benD, Long benRegID);

	public Long createBeneficiaryImage(JsonObject benD, Long benRegID);

	public String getQuickSearchBenData(String benID);

	public String getBenImage(Long benRegID);

	public String getBeneficiaryDetails(Long benRegID);

	int updateBeneficiary(JsonObject benD);

	int updateBeneficiaryDemographic(JsonObject benD, Long benRegID);

	int updateBeneficiaryPhoneMapping(JsonObject benD, Long benRegID);

	int updateBenGovIdMapping(JsonObject benD, Long benRegID);

	int updateBeneficiaryDemographicAdditional(JsonObject benD, Long benRegID);

	int updateBeneficiaryImage(JsonObject benD, Long benRegID);

}
