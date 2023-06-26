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
package com.iemr.hwc.repo.nurse;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.hwc.data.nurse.BenFamilyCancerHistory;

@Repository
@RestResource(exported = false)
public interface BenFamilyCancerHistoryRepo extends CrudRepository<BenFamilyCancerHistory, Long> {

	@Transactional
	@Modifying
	@Query("update BenFamilyCancerHistory set cancerDiseaseType=:cancerDiseaseType, familyMember=:familyMember, modifiedBy=:modifiedBy where iD=:iD "
			+ "AND beneficiaryRegID=:benRegID AND benVisitID = :benVisitID")
	public int updateBenFamilyCancerHistory(@Param("cancerDiseaseType") String cancerDiseaseType,
			@Param("familyMember") String familyMember, @Param("modifiedBy") String modifiedBy, @Param("iD") Long iD,
			@Param("benRegID") Long benRegID, @Param("benVisitID") Long benVisitID);

	@Query(" SELECT bfh from BenFamilyCancerHistory bfh WHERE bfh.beneficiaryRegID = :benRegID AND bfh.deleted = false AND bfh.visitCode = :visitCode")
	public List<BenFamilyCancerHistory> getBenFamilyHistory(@Param("benRegID") Long benRegID,
			 @Param("visitCode") Long visitCode);

	// @Query(" SELECT bfh from BenFamilyCancerHistory bfh WHERE
	// bfh.beneficiaryRegID = :benRegID AND bfh.benVisitID = :benVisitID "
	// + " AND DATE(createdDate) = :createdDate AND bfh.deleted = false")
	// public List<BenFamilyCancerHistory>
	// getBenFamilyHistory(@Param("benRegID") Long benRegID,
	// @Param("benVisitID") Long benVisitID, @Param("createdDate") Date
	// createdDate);

	@Modifying
	@Transactional
	@Query("update BenFamilyCancerHistory set deleted=true, processed=:processed WHERE ID = :ID")
	public int deleteExistingFamilyRecord(@Param("ID") Long ID, @Param("processed") String processed);

	@Query(" SELECT cancerDiseaseType, familyMember, Date(createdDate) FROM BenFamilyCancerHistory "
			+ " WHERE beneficiaryRegID = :benRegID AND cancerDiseaseType IS NOT NULL "
			+ " AND familyMember IS NOT NULL AND deleted = false ORDER BY createdDate DESC  ")
	public ArrayList<Object[]> getBenCancerFamilyHistory(@Param("benRegID") Long benRegID);

	@Query("SELECT ID, processed from BenFamilyCancerHistory where beneficiaryRegID=:benRegID AND visitCode = :visitCode AND deleted=false")
	public ArrayList<Object[]> getFamilyCancerHistoryStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

}
