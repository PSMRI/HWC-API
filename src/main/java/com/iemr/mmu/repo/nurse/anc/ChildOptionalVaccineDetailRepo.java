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
package com.iemr.mmu.repo.nurse.anc;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.anc.ChildOptionalVaccineDetail;

@Repository
@RestResource(exported = false)
public interface ChildOptionalVaccineDetailRepo extends CrudRepository<ChildOptionalVaccineDetail, Long> {

	@Query("select Date(createdDate), defaultReceivingAge, vaccineName, otherVaccineName, status, receivedDate, actualReceivingAge, receivedFacilityName, ageUnit "
			+ "from ChildOptionalVaccineDetail a where a.beneficiaryRegID = :beneficiaryRegID AND vaccineName is not null "
			+ "AND deleted = false ORDER BY createdDate DESC ")
	public ArrayList<Object[]> getBenOptionalVaccineDetail(@Param("beneficiaryRegID") Long beneficiaryRegID);

	@Query("select beneficiaryRegID, benVisitID, providerServiceMapID, defaultReceivingAge, vaccineName, otherVaccineName,"
			+ " status, receivedDate, actualReceivingAge, "
			+ "receivedFacilityName, visitCode, sctCode, sctTerm, ageUnitID, ageUnit "
			+ " from ChildOptionalVaccineDetail a where a.beneficiaryRegID = :beneficiaryRegID "
			+ "AND deleted = false AND a.visitCode = :visitCode")
	public ArrayList<Object[]> getBenOptionalVaccineDetail(@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode);

	@Modifying
	@Transactional
	@Query(" Update ChildOptionalVaccineDetail set deleted=true, processed=:processed WHERE ID = :ID")
	public int deleteExistingChildOptionalVaccineDetail(@Param("ID") Long ID, @Param("processed") String processed);

	@Query("SELECT ID, processed from ChildOptionalVaccineDetail where beneficiaryRegID=:benRegID AND visitCode = :visitCode AND deleted=false")
	public ArrayList<Object[]> getBenChildOptionalVaccineHistoryStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

}
