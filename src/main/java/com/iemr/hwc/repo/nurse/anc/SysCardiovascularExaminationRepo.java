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

import com.iemr.hwc.data.anc.SysCardiovascularExamination;

@Repository
@RestResource(exported = false)
public interface SysCardiovascularExaminationRepo extends CrudRepository<SysCardiovascularExamination, Long> {
	
	@Query(" SELECT u FROM SysCardiovascularExamination u WHERE u.beneficiaryRegID = :benRegID AND u.visitCode = :visitCode")
	public SysCardiovascularExamination getSysCardiovascularExaminationData(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	

	@Query("SELECT processed from SysCardiovascularExamination where beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public String getBenCardiovascularExaminationStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	
	@Transactional
	@Modifying
	@Query("update SysCardiovascularExamination set jugularVenousPulse_JVP=:jugularVenousPulse_JVP, apexbeatLocation=:apexbeatLocation, apexbeatType=:apexbeatType, "
			+ "firstHeartSound_S1=:firstHeartSound_S1, secondHeartSound_S2=:secondHeartSound_S2, additionalHeartSounds =:additionalHeartSounds, "
			+ "murmurs=:murmurs, pericardialRub=:pericardialRub, modifiedBy=:modifiedBy, processed=:processed "
			+ "where beneficiaryRegID=:benRegID and visitCode = :visitCode ")
	public int updateSysCardiovascularExamination(@Param("jugularVenousPulse_JVP") String jugularVenousPulse_JVP,
			@Param("apexbeatLocation") String apexbeatLocation,
			@Param("apexbeatType") String apexbeatType,
			@Param("firstHeartSound_S1") String firstHeartSound_S1,
			@Param("secondHeartSound_S2") String secondHeartSound_S2,
			@Param("additionalHeartSounds") String additionalHeartSounds,
			@Param("murmurs") String murmurs,
			@Param("pericardialRub") String pericardialRub,
			@Param("modifiedBy") String modifiedBy,
			@Param("processed") String processed,
			@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
}
