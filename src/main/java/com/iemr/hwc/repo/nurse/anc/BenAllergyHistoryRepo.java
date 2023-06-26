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

import com.iemr.hwc.data.anc.BenAllergyHistory;

@Repository
@RestResource(exported = false)
public interface BenAllergyHistoryRepo extends CrudRepository<BenAllergyHistory, Long> {

	@Query("select Date(createdDate), allergyStatus, allergyType, allergyName, allergicReactionType, otherAllergicReaction, remarks "
			+ " from BenAllergyHistory a where a.beneficiaryRegID = :beneficiaryRegID AND allergyStatus is not null and deleted = false "
			+ "order by createdDate DESC")
	public ArrayList<Object[]> getBenPersonalAllergyDetail(@Param("beneficiaryRegID") Long beneficiaryRegID);

	@Query(" SELECT beneficiaryRegID, benVisitID, providerServiceMapID, allergyStatus, allergyType, allergyName, allergicReactionTypeID, "
			+ "allergicReactionType, otherAllergicReaction, remarks, visitCode, snomedCode, snomedTerm  FROM BenAllergyHistory "
			+ " WHERE beneficiaryRegID = :benRegID AND deleted = false AND visitCode = :visitCode")
	public ArrayList<Object[]> getBenPersonalAllergyDetail(@Param("benRegID") Long benRegID,
			 @Param("visitCode") Long visitCode);

	@Modifying
	@Transactional
	@Query("Update BenAllergyHistory set deleted=true, processed=:processed WHERE ID = :ID")
	public int deleteExistingBenAllergyHistory(@Param("ID") Long ID, @Param("processed") String processed);
	
	@Query("SELECT ID, processed from BenAllergyHistory where beneficiaryRegID=:benRegID AND visitCode = :visitCode AND deleted=false")
	public ArrayList<Object[]> getBenAllergyHistoryStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
}
