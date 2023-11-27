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
package com.iemr.hwc.repo.quickConsultation;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.hwc.data.quickConsultation.LabTestOrderDetail;

@Repository
@RestResource(exported = false)
public interface LabTestOrderDetailRepo extends CrudRepository<LabTestOrderDetail, Long>{

	@Query(" SELECT beneficiaryRegID, benVisitID, providerServiceMapID,	procedureID, procedureName, "
			+ "testingRequirements, visitCode  from LabTestOrderDetail ba WHERE ba.beneficiaryRegID = :benRegID "
			+ "AND ba.visitCode= :visitCode")
	public ArrayList<Object[]> getLabTestOrderDetails(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	@Query(" SELECT ba  from LabTestOrderDetail ba WHERE ba.beneficiaryRegID = :benRegID "
			+ "AND ba.visitCode= :visitCode")
	public ArrayList<LabTestOrderDetail> getLabTestOrderDetails2(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	@Modifying
	@Transactional
	@Query(" Delete from LabTestOrderDetail WHERE beneficiaryRegID = :benRegID AND benVisitID = :benVisitID ")
	public int deleteExistingLabTestOrderDetail(@Param("benRegID") Long benRegID, @Param("benVisitID") Long benVisitID);
	
	@Modifying
	@Transactional
	@Query(" DELETE FROM LabTestOrderDetail WHERE visitCode=:visitCode ")
	public int deleteVisitDetails(@Param("visitCode") Long visitCode);
	
}
