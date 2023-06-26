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

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.hwc.data.doctor.CancerExaminationImageAnnotation;

@Repository
@RestResource(exported = false)
public interface CancerExaminationImageAnnotationRepo extends CrudRepository<CancerExaminationImageAnnotation, Long> {

	@Query(" SELECT t FROM CancerExaminationImageAnnotation t  "
			+ "  WHERE t.beneficiaryRegID =:benRegID AND deleted = false AND t.visitCode = :visitCode ORDER BY t.cancerImageID  ")
	public List<CancerExaminationImageAnnotation> getCancerExaminationImageAnnotationList(
			@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Query("SELECT ID, processed from CancerExaminationImageAnnotation where beneficiaryRegID=:benRegID "
			+ "  AND visitCode = :visitCode AND deleted = false AND cancerImageID IN (:imgIDList)")
	public ArrayList<Object[]> getCancerExaminationImageAnnotationDetailsStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode, @Param("imgIDList") List<Integer> imgIDList);

	@Modifying
	@Transactional
	@Query("update CancerExaminationImageAnnotation set deleted=true, processed=:processed WHERE ID = :ID")
	public int deleteExistingImageAnnotationDetails(@Param("ID") Long ID, @Param("processed") String processed);

}
