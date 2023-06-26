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

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.hwc.data.anc.BenChildDevelopmentHistory;

@Repository
@RestResource(exported = false)
public interface BenChildDevelopmentHistoryRepo extends CrudRepository<BenChildDevelopmentHistory, Long> {

	@Query("select Date(createdDate), grossMotorMilestone, isGrossMotorMilestones, fineMotorMilestone, isFineMotorMilestones, "
			+ "socialMilestone, isSocialMilestones, languageMilestone, isLanguageMilestones, developmentProblem "
			+ "from BenChildDevelopmentHistory a where a.beneficiaryRegID = :beneficiaryRegID "
			+ "AND deleted = false ORDER BY createdDate DESC ")
	public ArrayList<Object[]> getBenDevelopmentHistoryDetail(@Param("beneficiaryRegID") Long beneficiaryRegID);

	@Query("select beneficiaryRegID, benVisitID, providerServiceMapID, grossMotorMilestone, isGrossMotorMilestones, fineMotorMilestone, "
			+ "isFineMotorMilestones, socialMilestone, isSocialMilestones, languageMilestone, isLanguageMilestones, developmentProblem, visitCode  "
			+ "from BenChildDevelopmentHistory a where a.beneficiaryRegID = :beneficiaryRegID and a.visitCode = :visitCode AND deleted=false")

	public ArrayList<Object[]> getBenDevelopmentDetails(@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode);

	// new for above, fetch list obj class obj instead of arrList, 11-10-2022
	@Query("select t from BenChildDevelopmentHistory t where t.beneficiaryRegID = :beneficiaryRegID "
			+ " and t.visitCode = :visitCode AND t.deleted=false")
	public BenChildDevelopmentHistory getBenDevelopmentHistory(@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode);

	@Query("SELECT processed from BenChildDevelopmentHistory where beneficiaryRegID=:benRegID AND visitCode = :visitCode AND deleted = false")
	public String getDevelopmentHistoryStatus(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Transactional
	@Modifying
	@Query("update BenChildDevelopmentHistory set grossMotorMilestone=:grossMotorMilestone, isGrossMotorMilestones=:isGrossMotorMilestones, "
			+ "fineMotorMilestone=:fineMotorMilestone, isFineMotorMilestones=:isFineMotorMilestones,"
			+ " socialMilestone=:socialMilestone, isSocialMilestones=:isSocialMilestones, languageMilestone=:languageMilestone, "
			+ "isLanguageMilestones=:isLanguageMilestones, developmentProblem=:developmentProblem, "
			+ "  modifiedBy=:modifiedBy, processed=:processed where "
			+ "beneficiaryRegID=:beneficiaryRegID AND visitCode = :visitCode")
	public int updateBenChildDevelopmentHistory(@Param("grossMotorMilestone") String grossMotorMilestone,
			@Param("isGrossMotorMilestones") Boolean isGrossMotorMilestones,
			@Param("fineMotorMilestone") String fineMotorMilestone, @Param("isFineMotorMilestones") Boolean isFineMotorMilestones,
			@Param("socialMilestone") String socialMilestone, @Param("isSocialMilestones") Boolean isSocialMilestones,
			@Param("languageMilestone") String languageMilestone, @Param("isLanguageMilestones") Boolean isLanguageMilestones,
			@Param("developmentProblem") String developmentProblem, @Param("modifiedBy") String modifiedBy,
			@Param("processed") String processed, @Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode);

}
