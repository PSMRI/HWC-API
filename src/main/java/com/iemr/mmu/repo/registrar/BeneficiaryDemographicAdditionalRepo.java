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
package com.iemr.mmu.repo.registrar;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.nurse.BeneficiaryVisitDetail;
import com.iemr.mmu.data.registrar.BeneficiaryDemographicAdditional;

@Repository
@RestResource(exported = false)
public interface BeneficiaryDemographicAdditionalRepo extends CrudRepository<BeneficiaryDemographicAdditional, Long> {

	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryDemographicAdditional set literacyStatus = :literacyStatus, motherName =:motherName, emailID = :emailID, bankName = :bankName,"
			+ " branchName = :branchName, iFSCCode = :iFSCCode, accountNo = :accountNo, modifiedBy = :modifiedBy where "
			+ " beneficiaryRegID = :beneficiaryRegID ")
	public Integer updateBeneficiaryDemographicAdditional(@Param("literacyStatus") String literacyStatus,
			@Param("motherName") String motherName,
			@Param("emailID") String emailID,
			@Param("bankName") String bankName,
			@Param("branchName") String branchName,
			@Param("iFSCCode") String iFSCCode,
			@Param("accountNo") String accountNo,
			@Param("modifiedBy") String modifiedBy,
			@Param("beneficiaryRegID") Long beneficiaryRegID);
	
	@Query(" SELECT bvd from BeneficiaryDemographicAdditional bvd WHERE bvd.beneficiaryRegID = :benRegID")
	public BeneficiaryDemographicAdditional getBeneficiaryDemographicAdditional(@Param("benRegID") Long benRegID);

}
