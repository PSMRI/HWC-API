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

import com.iemr.hwc.data.anc.SysGastrointestinalExamination;

@Repository
@RestResource(exported = false)
public interface SysGastrointestinalExaminationRepo extends CrudRepository<SysGastrointestinalExamination, Long> {
	
	@Query(" SELECT u FROM SysGastrointestinalExamination u WHERE u.beneficiaryRegID = :benRegID AND u.visitCode = :visitCode")
	public SysGastrointestinalExamination getSSysGastrointestinalExamination(@Param("benRegID") Long benRegID,
			 @Param("visitCode") Long visitCode);
	

	@Query("SELECT processed from SysGastrointestinalExamination where beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public String getBenGastrointestinalExaminationStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	
	@Transactional
	@Modifying
	@Query("update SysGastrointestinalExamination set inspection=:inspection, palpation=:palpation, palpation_AbdomenTexture=:palpation_AbdomenTexture, "
			+ "palpation_Liver=:palpation_Liver, palpation_Spleen=:palpation_Spleen, palpation_Tenderness =:palpation_Tenderness, "
			+ "palpation_LocationOfTenderness=:palpation_LocationOfTenderness, percussion=:percussion, auscultation=:auscultation, analRegion=:analRegion,"
			+ " modifiedBy=:modifiedBy, processed=:processed where beneficiaryRegID=:benRegID and visitCode = :visitCode ")
	public int updateSysGastrointestinalExamination(@Param("inspection") String inspection,
			@Param("palpation") String palpation,
			@Param("palpation_AbdomenTexture") String palpation_AbdomenTexture,
			@Param("palpation_Liver") String palpation_Liver,
			@Param("palpation_Spleen") String palpation_Spleen,
			@Param("palpation_Tenderness") String palpation_Tenderness,
			@Param("palpation_LocationOfTenderness") String palpation_LocationOfTenderness,
			@Param("percussion") String percussion,
			@Param("auscultation") String auscultation,
			@Param("analRegion") String analRegion,
			@Param("modifiedBy") String modifiedBy,
			@Param("processed") String processed,
			@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
}
