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

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.hwc.data.anc.PhyHeadToToeExamination;

@Repository
@RestResource(exported = false)
public interface PhyHeadToToeExaminationRepo extends CrudRepository<PhyHeadToToeExamination, Long> {

	@Query(" SELECT u FROM PhyHeadToToeExamination u WHERE u.beneficiaryRegID = :benRegID AND u.visitCode = :visitCode")
	public PhyHeadToToeExamination getPhyHeadToToeExaminationData(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Query("SELECT processed from PhyHeadToToeExamination where beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public String getBenHeadToToeExaminationStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Transactional
	@Modifying
	@Query("update PhyHeadToToeExamination set headtoToeExam=:headtoToeExam, head=:head, eyes=:eyes, ears=:ears, nose=:nose, oralCavity=:oralCavity,"
			+ "throat=:throat, breastAndNipples=:breastAndNipples, trunk=:trunk, upperLimbs =:upperLimbs, lowerLimbs=:lowerLimbs, "
			+ "skin=:skin, hair=:hair,"
			+ "nails=:nails, modifiedBy=:modifiedBy, processed=:processed, nipples=:nipples "
			+ " where beneficiaryRegID=:benRegID and visitCode = :visitCode ")
	public int updatePhyHeadToToeExamination(@Param("headtoToeExam") String headtoToeExam, @Param("head") String head,
			@Param("eyes") String eyes, @Param("ears") String ears, @Param("nose") String nose,
			@Param("oralCavity") String oralCavity, @Param("throat") String throat,
			@Param("breastAndNipples") String breastAndNipples, @Param("trunk") String trunk,
			@Param("upperLimbs") String upperLimbs, @Param("lowerLimbs") String lowerLimbs, @Param("skin") String skin,
			@Param("hair") String hair, @Param("nails") String nails, @Param("modifiedBy") String modifiedBy,
			@Param("processed") String processed, @Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode,
			@Param("nipples") String nipples);
}
