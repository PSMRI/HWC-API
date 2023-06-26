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

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.hwc.data.doctor.CancerLymphNodeDetails;
import com.iemr.hwc.data.doctor.CancerOralExamination;
@Repository
@RestResource(exported = false)
public interface CancerOralExaminationRepo extends CrudRepository<CancerOralExamination, Long> {
	
	@Query(" SELECT c from CancerOralExamination c WHERE c.beneficiaryRegID = :benRegID "
			+ " AND c.deleted = false AND c.visitCode = :visitCode")
	public CancerOralExamination getBenCancerOralExaminationDetails(@Param("benRegID") Long benRegID,
	@Param("visitCode") Long  visitCode);
	
	@Query("SELECT processed from CancerOralExamination where beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public String getCancerOralExaminationStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	
	@Transactional
	@Modifying
	@Query("update CancerOralExamination set providerServiceMapID=:providerServiceMapID, limitedMouthOpening=:limitedMouthOpening, "
			+ "premalignantLesions=:premalignantLesions, preMalignantLesionType=:preMalignantLesionType, "
			+ "prolongedIrritation=:prolongedIrritation, chronicBurningSensation=:chronicBurningSensation, "
			+ "observation =:observation, modifiedBy=:modifiedBy, processed=:processed where "
			+ " beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public int updateCancerOralExaminationDetails(@Param("providerServiceMapID") Integer providerServiceMapID,
			@Param("limitedMouthOpening") String limitedMouthOpening, 
			@Param("premalignantLesions") Boolean premalignantLesions,
			@Param("preMalignantLesionType") String preMalignantLesionType, 
			@Param("prolongedIrritation") Boolean prolongedIrritation,
			@Param("chronicBurningSensation") Boolean chronicBurningSensation, 
			@Param("observation") String observation,
			@Param("modifiedBy") String modifiedBy,
			@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode,
			@Param("processed") String processed);
}
