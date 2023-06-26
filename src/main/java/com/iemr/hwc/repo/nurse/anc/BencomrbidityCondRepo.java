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
package com.iemr.hwc.repo.nurse.anc;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.hwc.data.anc.BencomrbidityCondDetails;

@Repository
@RestResource(exported = false)
public interface BencomrbidityCondRepo extends CrudRepository<BencomrbidityCondDetails, Long> {

	@Query("select Date(createdDate), comorbidCondition , otherComorbidCondition, Date(year) "
			+ "from BencomrbidityCondDetails a where a.beneficiaryRegID = :beneficiaryRegID AND a.comorbidCondition is not null "
			+ "AND a.isForHistory = true AND a.deleted = false ORDER BY a.createdDate DESC ")
	public ArrayList<Object[]> getBencomrbidityCondDetails(@Param("beneficiaryRegID") Long beneficiaryRegID);

	@Query(" SELECT beneficiaryRegID, benVisitID, providerServiceMapID, comorbidConditionID, comorbidCondition, year, otherComorbidCondition, "
			+ " isForHistory, createdDate, visitCode  FROM BencomrbidityCondDetails "
			+ " WHERE beneficiaryRegID = :benRegID AND deleted = false AND visitCode= :visitCode")
	public ArrayList<Object[]> getBencomrbidityCondDetails(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Modifying
	@Transactional
	@Query(" update BencomrbidityCondDetails set deleted=true, processed=:processed WHERE ID = :ID")
	public int deleteExistingBenComrbidityCondDetails(@Param("ID") Long ID, @Param("processed") String processed);

	@Query("SELECT ID, processed from BencomrbidityCondDetails where beneficiaryRegID=:benRegID AND visitCode = :visitCode AND deleted=false")
	public ArrayList<Object[]> getBenComrbidityCondHistoryStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Query("SELECT ID FROM BencomrbidityCondDetails where beneficiaryRegID=:benRegID AND "
			+ " (comorbidConditionID IN (2, 3, 5, 6)) AND deleted is false")
	public ArrayList<Long> getHRPStatus(@Param("benRegID") Long benRegID);

}
