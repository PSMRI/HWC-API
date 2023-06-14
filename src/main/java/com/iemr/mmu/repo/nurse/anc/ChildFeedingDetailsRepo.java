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
package com.iemr.mmu.repo.nurse.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.mmu.data.anc.ChildFeedingDetails;

@Repository
@RestResource(exported = false)
public interface ChildFeedingDetailsRepo extends CrudRepository<ChildFeedingDetails, Long> {

	@Query("select Date(createdDate), childID, benMotherID, typeOfFeed, compFeedStartAge, noOfCompFeedPerDay, foodIntoleranceStatus,"
			+ " typeofFoodIntolerance, otherFoodIntolerance "
			+ "from ChildFeedingDetails a where a.beneficiaryRegID = :beneficiaryRegID AND (typeOfFeed is not null OR "
			+ "compFeedStartAge is not null OR foodIntoleranceStatus is not null)"
			+ "AND deleted = false ORDER BY createdDate DESC ")
	public ArrayList<Object[]> getBenFeedingHistoryDetail(@Param("beneficiaryRegID") Long beneficiaryRegID);

	@Query("select beneficiaryRegID, benVisitID, providerServiceMapID, childID, benMotherID, typeOfFeed, compFeedStartAge, "
			+ "noOfCompFeedPerDay, foodIntoleranceStatus, typeofFoodIntolerance, visitCode "
			+ "from ChildFeedingDetails a where a.beneficiaryRegID = :beneficiaryRegID and a.visitCode = :visitCode AND deleted = false")
	public ArrayList<Object[]> getBenFeedingDetails(@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode);

	// enhancement of above, returning object of class, 11-10-2022
	@Query("SELECT t FROM ChildFeedingDetails t WHERE t.beneficiaryRegID = :beneficiaryRegID and t.visitCode = :visitCode AND t.deleted = false")
	public ChildFeedingDetails getBenFeedingHistory(@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode);

	@Query("SELECT processed from ChildFeedingDetails where beneficiaryRegID=:benRegID AND visitCode = :visitCode AND deleted = false")
	public String getBenChildFeedingDetailStatus(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Transactional
	@Modifying
	@Query("update ChildFeedingDetails set childID=:childID, benMotherID=:benMotherID, "
			+ "typeOfFeed=:typeOfFeed, compFeedStartAge=:compFeedStartAge,"
			+ " noOfCompFeedPerDay=:noOfCompFeedPerDay, foodIntoleranceStatus=:foodIntoleranceStatus,"
			+ " typeofFoodIntolerance=:typeofFoodIntolerance, modifiedBy=:modifiedBy, processed=:processed, "
			+ " otherFoodIntolerance=:otherFoodIntolerance where "
			+ "beneficiaryRegID=:beneficiaryRegID AND visitCode = :visitCode")
	public int updateFeedingDetails(@Param("childID") Long childID, @Param("benMotherID") Long benMotherID,
			@Param("typeOfFeed") String typeOfFeed, @Param("compFeedStartAge") String compFeedStartAge,
			@Param("noOfCompFeedPerDay") String noOfCompFeedPerDay,
			@Param("foodIntoleranceStatus") String foodIntoleranceStatus,
			@Param("typeofFoodIntolerance") String typeofFoodIntolerance, @Param("modifiedBy") String modifiedBy,
			@Param("processed") String processed, @Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode, @Param("otherFoodIntolerance") String otherFoodIntolerance);

}
