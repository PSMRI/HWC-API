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
package com.iemr.mmu.repo.nurse;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.mmu.data.nurse.BenPersonalCancerDietHistory;

@Repository
@RestResource(exported = false)
public interface BenPersonalCancerDietHistoryRepo extends CrudRepository<BenPersonalCancerDietHistory, Long> {
	@Query("SELECT bpdh from BenPersonalCancerDietHistory bpdh  WHERE bpdh.beneficiaryRegID = :benRegID AND bpdh.deleted = false "
			+ "AND bpdh.visitCode = :visitCode")
	public BenPersonalCancerDietHistory getBenPersonaDietHistory(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Transactional
	@Modifying
	@Query("update BenPersonalCancerDietHistory set providerServiceMapID=:providerServiceMapID, dietType=:dietType, fruitConsumptionDays=:fruitConsumptionDays, "
			+ "fruitQuantityPerDay=:fruitQuantityPerDay, vegetableConsumptionDays=:vegetableConsumptionDays, vegetableQuantityPerDay=:vegetableQuantityPerDay, "
			+ " intakeOfOutsidePreparedMeal=:intakeOfOutsidePreparedMeal, typeOfOilConsumed=:typeOfOilConsumed, physicalActivityType=:physicalActivityType,"
			+ " ssRadiationExposure=:ssRadiationExposure, isThyroidDisorder=:isThyroidDisorder,"
			+ " modifiedBy=:modifiedBy, processed=:processed where "
			+ "  beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public int updateBenPersonalCancerDietHistory(@Param("providerServiceMapID") Integer providerServiceMapID,
			@Param("dietType") String dietType, @Param("fruitConsumptionDays") Integer fruitConsumptionDays,
			@Param("fruitQuantityPerDay") Integer fruitQuantityPerDay,
			@Param("vegetableConsumptionDays") Integer vegetableConsumptionDays,
			@Param("vegetableQuantityPerDay") Integer vegetableQuantityPerDay,
			@Param("intakeOfOutsidePreparedMeal") Integer intakeOfOutsidePreparedMeal,
			@Param("typeOfOilConsumed") String typeOfOilConsumed,
			@Param("physicalActivityType") String physicalActivityType,
			@Param("ssRadiationExposure") Boolean ssRadiationExposure,
			@Param("isThyroidDisorder") Boolean isThyroidDisorder, @Param("modifiedBy") String modifiedBy,
			@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode,
			@Param("processed") String processed);

	// @Query("SELECT bpdh from BenPersonalCancerDietHistory bpdh WHERE
	// bpdh.beneficiaryRegID = :benRegID AND bpdh.benVisitID = :benVisitID"
	// + " AND DATE(createdDate) = :createdDate")
	// public BenPersonalCancerDietHistory
	// getBenPersonaDietHistory(@Param("benRegID") Long benRegID,
	// @Param("benVisitID") Long benVisitID, @Param("createdDate") Date
	// createdDate);

	@Query(" SELECT dietType, fruitConsumptionDays, fruitQuantityPerDay, vegetableConsumptionDays, vegetableQuantityPerDay, "
			+ "intakeOfOutsidePreparedMeal, typeOfOilConsumed, physicalActivityType, ssRadiationExposure, isThyroidDisorder, Date(createdDate) "
			+ "from BenPersonalCancerDietHistory bph WHERE bph.beneficiaryRegID = :benRegID and (dietType is not null or fruitConsumptionDays is not null "
			+ "or fruitQuantityPerDay is not null or vegetableConsumptionDays is not null or vegetableQuantityPerDay is not null or intakeOfOutsidePreparedMeal is not null "
			+ "or typeOfOilConsumed <> '' or physicalActivityType is not null or ssRadiationExposure is not null or isThyroidDisorder is not null) order by createdDate desc")
	public ArrayList<Object[]> getBenPersonaDietHistory(@Param("benRegID") Long benRegID);

	@Query("SELECT processed from BenPersonalCancerDietHistory where beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public String getPersonalCancerDietHistoryStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

}
