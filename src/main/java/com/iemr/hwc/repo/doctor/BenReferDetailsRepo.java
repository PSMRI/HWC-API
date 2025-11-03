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
package com.iemr.hwc.repo.doctor;

import java.sql.Timestamp;
import java.util.ArrayList;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.hwc.data.doctor.BenReferDetails;

@Repository
@RestResource(exported = false)
public interface BenReferDetailsRepo extends CrudRepository<BenReferDetails, Long> {

//	@Query(" SELECT benReferID, beneficiaryRegID, benVisitID, providerServiceMapID, referredToInstituteID, "
//			+ "referredToInstituteName, serviceID, serviceName, visitCode, revisitDate , referralReason, "
//			+ " createdDate, otherReferredToInstituteName, otherReferralReason "
//			+ "from BenReferDetails ba WHERE ba.beneficiaryRegID = :benRegID AND ba.visitCode = :visitCode AND ba.deleted = false")
//	public ArrayList<Object[]> getBenReferDetails(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	public ArrayList<BenReferDetails> findByBeneficiaryRegIDAndVisitCode(Long benRegId, Long visitCode);

	@Query(" SELECT ba "
			+ "from BenReferDetails ba WHERE ba.beneficiaryRegID = :benRegID AND ba.visitCode = :visitCode AND ba.deleted = false")
	public ArrayList<BenReferDetails> getBenReferDetails2(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	/*
	 * @Query("SELECT processed from BenReferDetails where beneficiaryRegID=:benRegID AND benVisitID = :benVisitID"
	 * ) public String getBenReferDetailsStatus(@Param("benRegID") Long benRegID,
	 * 
	 * @Param("benVisitID") Long benVisitID);
	 */

	@Query("SELECT benReferID, processed from BenReferDetails where beneficiaryRegID=:benRegID AND visitCode = :visitCode AND deleted=false")
	public ArrayList<Object[]> getBenReferDetailsStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Modifying
	@Transactional
	@Query(" Update BenReferDetails  set referredToInstituteID=:referredToInstituteID, "
			+ "referredToInstituteName=:referredToInstituteName,revisitDate=:revisitDate,referralReason=:referralReason, processed=:processed "
			+ "WHERE benReferID =:benReferID")
	public int updateReferredInstituteName(@Param("referredToInstituteID") Integer referredToInstituteID,
			@Param("referredToInstituteName") String referredToInstituteName,
			@Param("revisitDate") Timestamp revisitDate, @Param("referralReason") String referralReason,
			@Param("benReferID") Long benReferID, @Param("processed") String processed);

	@Query("SELECT ba FROM BenReferDetails ba WHERE ba.createdBy = :createdBy AND ba.deleted = false ORDER BY ba.createdDate DESC")
	public ArrayList<BenReferDetails> getBenReferDetailsByCreatedBy(@Param("createdBy") String createdBy);
}
