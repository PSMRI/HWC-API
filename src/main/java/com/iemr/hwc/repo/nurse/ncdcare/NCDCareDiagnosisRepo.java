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
package com.iemr.hwc.repo.nurse.ncdcare;

import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.hwc.data.ncdcare.NCDCareDiagnosis;

@Repository
@RestResource(exported = false)
public interface NCDCareDiagnosisRepo extends CrudRepository<NCDCareDiagnosis, Long> {

	@Query(" SELECT beneficiaryRegID, benVisitID, providerServiceMapID, prescriptionID, "
			+ " ncdScreeningCondition, ncdComplication, ncdCareType, visitCode, ncdScreeningConditionOther, "
			+ " createdBy, createdDate "
			+ " from NCDCareDiagnosis ba " + " WHERE ba.beneficiaryRegID = :benRegID"
			+ " AND ba.visitCode = :visitCode AND ba.deleted = false " + " ORDER BY createdDate desc")
	public ArrayList<Object[]> getNCDCareDiagnosisDetails(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Query("SELECT processed from NCDCareDiagnosis where beneficiaryRegID=:benRegID AND visitCode = :visitCode "
			+ " AND prescriptionID =:prescriptionID ")
	public String getNCDCareDiagnosisStatus(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode,
			@Param("prescriptionID") Long prescriptionID);

	@Transactional
	@Modifying
	@Query("update NCDCareDiagnosis set ncdScreeningCondition=:ncdScreeningCondition, ncdComplication=:ncdComplication, "
			+ "ncdCareType=:ncdCareType, modifiedBy=:modifiedBy, processed=:processed, "
			+ " ncdScreeningConditionOther = :ncdScreeningConditionOther "
			+ "where visitCode=:visitCode AND beneficiaryRegID=:beneficiaryRegID AND prescriptionID =:prescriptionID ")
	public int updateNCDCareDiagnosis(@Param("ncdScreeningCondition") String ncdScreeningCondition,
			@Param("ncdComplication") String ncdComplication, @Param("ncdCareType") String ncdCareType,
			@Param("modifiedBy") String modifiedBy, @Param("processed") String processed,
			@Param("beneficiaryRegID") Long beneficiaryRegID, @Param("visitCode") Long visitCode,
			@Param("prescriptionID") Long prescriptionID,
			@Param("ncdScreeningConditionOther") String ncdScreeningConditionOther);

	@Query("SELECT ncdScreeningCondition from NCDCareDiagnosis where visitCode = :visitCode "
			+ " AND prescriptionID =:prescriptionID AND (deleted <> true OR deleted IS NULL)")
	public List<Object> getNCDcondition(@Param("visitCode") Long visitCode,
			@Param("prescriptionID") Long prescriptionID);
}
