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

import com.iemr.hwc.data.anc.SysMusculoskeletalSystemExamination;

@Repository
@RestResource(exported = false)
public interface SysMusculoskeletalSystemExaminationRepo
		extends CrudRepository<SysMusculoskeletalSystemExamination, Long> {
	
	@Query(" SELECT u FROM SysMusculoskeletalSystemExamination u WHERE u.beneficiaryRegID = :benRegID AND u.visitCode = :visitCode")
	public SysMusculoskeletalSystemExamination getSysMusculoskeletalSystemExamination(@Param("benRegID") Long benRegID,
			 @Param("visitCode") Long  visitCode);
	

	@Query("SELECT processed from SysMusculoskeletalSystemExamination where beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public String getBenMusculoskeletalSystemExaminationStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	
	@Transactional
	@Modifying
	@Query("update SysMusculoskeletalSystemExamination set joint_TypeOfJoint=:joint_TypeOfJoint, joint_Laterality=:joint_Laterality, "
			+ "joint_Abnormality=:joint_Abnormality, upperLimb_Laterality=:upperLimb_Laterality, upperLimb_Abnormality=:upperLimb_Abnormality,"
			+ " lowerLimb_Laterality =:lowerLimb_Laterality, lowerLimb_Abnormality=:lowerLimb_Abnormality, chestWall=:chestWall, spine =:spine, "
			+ "modifiedBy=:modifiedBy, processed=:processed "
			+ "where beneficiaryRegID=:benRegID and visitCode = :visitCode ")
	public int updateSysMusculoskeletalSystemExamination(@Param("joint_TypeOfJoint") String joint_TypeOfJoint,
			@Param("joint_Laterality") String joint_Laterality,
			@Param("joint_Abnormality") String joint_Abnormality,
			@Param("upperLimb_Laterality") String upperLimb_Laterality,
			@Param("upperLimb_Abnormality") String upperLimb_Abnormality,
			@Param("lowerLimb_Laterality") String lowerLimb_Laterality,
			@Param("lowerLimb_Abnormality") String lowerLimb_Abnormality,
			@Param("chestWall") String chestWall,
			@Param("spine") String spine,
			@Param("modifiedBy") String modifiedBy,
			@Param("processed") String processed,
			@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	
	
}
