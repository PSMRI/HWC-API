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
package com.iemr.hwc.service.quickConsultation;

import com.google.gson.JsonObject;

public interface QuickConsultationService {

	public Long saveBeneficiaryChiefComplaint(JsonObject benChiefComplaint);

	public Long saveBeneficiaryClinicalObservations(JsonObject benClinicalObservations) throws Exception;

	// public Long saveBeneficiaryPrescription(JsonObject prescriptionDetail) throws
	// Exception;

	// public Long saveBeneficiaryPrescribedDrugDetail(JsonObject
	// prescribedDrugDetail, Long prescriptionID,
	// CommonUtilityClass commonUtilityClass);

	// public Long saveBeneficiaryLabTestOrderDetails(JsonObject labTestOrderDetail,
	// Long prescriptionID);

	public Long saveBeneficiaryExternalLabTestOrderDetails(JsonObject externalLabTestOrderDetail);

	public String quickConsultNurseDataInsert(JsonObject jsnOBJ, String Authorization) throws Exception;

	public Integer quickConsultDoctorDataInsert(JsonObject quickConsultDoctorOBJ, String Authorization)
			throws Exception;

	Integer updateBeneficiaryClinicalObservations(JsonObject caseSheet) throws Exception;
	
	public void deleteVisitDetails(JsonObject requestOBJ) throws Exception;
}
