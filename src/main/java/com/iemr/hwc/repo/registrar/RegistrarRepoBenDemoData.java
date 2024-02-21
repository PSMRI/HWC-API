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
package com.iemr.hwc.repo.registrar;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.registrar.BeneficiaryDemographicData;
@Repository
@RestResource(exported = false)
public interface RegistrarRepoBenDemoData extends CrudRepository<BeneficiaryDemographicData, Long> {
	
	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryDemographicData set countryID = :countryID,stateID = :stateID,districtID = :districtID,areaID = :areaID,servicePointID = :servicePointID,"
			+ " districtBranchID = :districtBranchID,communityID = :communityID,religionID = :religionID,occupationID = :occupationID, educationID = :educationID,"
			+ " incomeStatusID = :incomeStatusID, modifiedBy = :modifiedBy where beneficiaryRegID = :beneficiaryRegID ")
	public Integer updateBendemographicData(@Param("countryID") Integer countryID,
			@Param("stateID") Integer stateID,
			@Param("districtID") Integer districtID,
			@Param("areaID") Integer areaID,
			@Param("servicePointID") Integer servicePointID,
			@Param("districtBranchID") Integer districtBranchID,
			@Param("communityID") Short communityID,
			@Param("religionID") Short religionID,
			@Param("occupationID") Short occupationID,
			@Param("educationID") Short educationID,
			@Param("incomeStatusID") Short incomeStatusID,
			@Param("modifiedBy") String modifiedBy,
			@Param("beneficiaryRegID") Long beneficiaryRegID);
	
	
	
	@Query(" SELECT bd.beneficiaryRegID,bd.servicePointID,s.servicePointName from BeneficiaryDemographicData bd "
			+ "INNER JOIN bd.servicePoint s "
			+ "WHERE bd.beneficiaryRegID = :beneficiaryRegID")
	public List<Objects[]> getBeneficiaryDemographicData(@Param("beneficiaryRegID") Long beneficiaryRegID);
	
//	countryID
//	stateID
//	districtID
//	areaID
//	servicePointID
//	districtBranchID
//	communityID
//	religionID
//	occupationID
//	educationID
//	incomeStatusID

}
