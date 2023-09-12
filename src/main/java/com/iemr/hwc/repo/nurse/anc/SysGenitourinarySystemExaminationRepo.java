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

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.hwc.data.anc.SysGenitourinarySystemExamination;

@Repository
@RestResource(exported = false)
public interface SysGenitourinarySystemExaminationRepo extends CrudRepository<SysGenitourinarySystemExamination, Long> {
	
	@Query(" SELECT u FROM SysGenitourinarySystemExamination u WHERE u.beneficiaryRegID = :benRegID AND u.visitCode = :visitCode ")
	public SysGenitourinarySystemExamination getSysGenitourinarySystemExaminationData(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	

	@Query("SELECT processed from SysGenitourinarySystemExamination where beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public String getBenGenitourinarySystemExaminationStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	
	@Transactional
	@Modifying
	@Query("update SysGenitourinarySystemExamination set renalAngle=:renalAngle, suprapubicRegion=:suprapubicRegion, "
			+ "externalGenitalia=:externalGenitalia, modifiedBy=:modifiedBy, processed=:processed "
			+ "where beneficiaryRegID=:benRegID and visitCode = :visitCode ")
	public int updateSysGenitourinarySystemExamination(@Param("renalAngle") String renalAngle,
			@Param("suprapubicRegion") String suprapubicRegion,
			@Param("externalGenitalia") String externalGenitalia,
			@Param("modifiedBy") String modifiedBy,
			@Param("processed") String processed,
			@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
}
