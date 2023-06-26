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
package com.iemr.hwc.repo.nurse.ncdscreening;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.hwc.data.ncdScreening.NCDScreening;

@Repository
@RestResource(exported = false)
public interface NCDScreeningRepo extends CrudRepository<NCDScreening, Long> {

	@Query(" SELECT ba FROM NCDScreening ba WHERE ba.beneficiaryRegID = :benRegID AND ba.visitCode = :visitCode ")
	public NCDScreening getNCDScreeningDetails(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Transactional
	@Modifying
	@Query("update NCDScreening set ncdScreeningConditionID=:ncdScreeningConditionID, "
			+ " screeningCondition=:screeningCondition, ncdScreeningReasonID=:ncdScreeningReasonID, "
			+ " reasonForScreening=:reasonForScreening,"
			+ " nextScreeningDateDB=:nextScreeningDate, actionForScreenPositive=:actionForScreenPositive, "
			+ " isScreeningComplete=:isScreeningComplete, "
			+ " isBPPrescribed = :isBPPrescribed, isBloodGlucosePrescribed = :isBloodGlucosePrescribed, "
			+ " modifiedBy=:modifiedBy where visitCode=:visitCode AND beneficiaryRegID=:beneficiaryRegID")
	public int updateNCDScreeningDetails(@Param("ncdScreeningConditionID") String ncdScreeningConditionID,
			@Param("screeningCondition") String screeningCondition,
			@Param("ncdScreeningReasonID") Integer ncdScreeningReasonID,
			@Param("reasonForScreening") String reasonForScreening,
			@Param("nextScreeningDate") Timestamp nextScreeningDate,
			@Param("actionForScreenPositive") String actionForScreenPositive,
			@Param("isScreeningComplete") Boolean isScreeningComplete, @Param("modifiedBy") String modifiedBy,
			@Param("beneficiaryRegID") Long beneficiaryRegID, @Param("visitCode") Long visitCode,
			@Param("isBPPrescribed") Boolean isBPPrescribed,
			@Param("isBloodGlucosePrescribed") Boolean isBloodGlucosePrescribed);

}
