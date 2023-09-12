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

import com.iemr.hwc.data.anc.BenFamilyHistory;

@Repository
@RestResource(exported = false)
public interface BenFamilyHistoryRepo extends CrudRepository<BenFamilyHistory, Long>{
	
	@Query("select Date(createdDate), familyMember, diseaseType, otherDiseaseType, isGeneticDisorder, geneticDisorder, isConsanguineousMarrige "
			+ "from BenFamilyHistory a where a.beneficiaryRegID = :beneficiaryRegID  AND deleted = false "
			+ "order by createdDate DESC")
	public ArrayList<Object[]> getBenFamilyHistoryDetail(@Param("beneficiaryRegID") Long beneficiaryRegID);
	
	@Query(" SELECT beneficiaryRegID, benVisitID, providerServiceMapID, familyMember, diseaseTypeID, diseaseType, otherDiseaseType, "
			+ "isGeneticDisorder, geneticDisorder, isConsanguineousMarrige, visitCode, snomedCode, snomedTerm  FROM BenFamilyHistory "
			+ " WHERE beneficiaryRegID = :benRegID AND deleted = false AND visitCode = :visitCode")
	public ArrayList<Object[]> getBenFamilyHistoryDetail(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);
	
	
	@Modifying
	@Transactional
	@Query(" Update BenFamilyHistory set deleted=true, processed=:processed WHERE ID = :ID")
		public int deleteExistingBenFamilyHistory(@Param("ID") Long ID, @Param("processed") String processed);
	
	@Query("SELECT ID, processed from BenFamilyHistory where beneficiaryRegID=:benRegID AND visitCode = :visitCode AND deleted=false")
	public ArrayList<Object[]> getBenFamilyHistoryStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	
	@Query(" SELECT ID,beneficiaryRegID, benVisitID, providerServiceMapID, familyMember, diseaseTypeID, diseaseType, otherDiseaseType, "
			+ "isGeneticDisorder, geneticDisorder, isConsanguineousMarrige, visitCode, snomedCode, snomedTerm  FROM BenFamilyHistory "
			+ " WHERE beneficiaryRegID = :benRegID AND deleted = false AND visitCode = :visitCode")
	public ArrayList<Object[]> getBenFamilyHisDetail(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);
	
}
