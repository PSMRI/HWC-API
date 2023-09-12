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
package com.iemr.hwc.repo.labModule;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.hwc.data.labModule.LabResultEntry;

@Repository
@RestResource(exported = false)
public interface LabResultEntryRepo extends CrudRepository<LabResultEntry, Long> {
	@Query("SELECT procedureID FROM LabResultEntry WHERE beneficiaryRegID = :benRegID AND "
			+ " benVisitID = :benVisitID ")
	ArrayList<Integer> findProcedureListByBeneficiaryRegIDAndBenVisitID(@Param("benRegID") Long benRegID,
			@Param("benVisitID") Long benVisitID);

	ArrayList<LabResultEntry> findByBeneficiaryRegIDAndVisitCodeOrderByProcedureIDAsc(Long benRegID, Long visitCode);

	@Query(nativeQuery = true, value = "SELECT DISTINCT visitCode, Date(createdDate) FROM t_lab_testresult "
			+ " WHERE beneficiaryRegID = :benRegID AND visitCode != :visitCode AND visitCode IS NOT NULL "
			+ " GROUP BY visitCode " + " ORDER BY createdDate DESC LIMIT 3 ")
	ArrayList<Object[]> getLast_3_visitForLabTestDone(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

}
