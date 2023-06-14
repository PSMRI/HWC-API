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
package com.iemr.mmu.repo.quickConsultation;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.quickConsultation.BenChiefComplaint;

@Repository
@RestResource(exported = false)
public interface BenChiefComplaintRepo extends CrudRepository<BenChiefComplaint, Long> {

	@Query(" SELECT benChiefComplaintID, beneficiaryRegID, benVisitID, providerServiceMapID, chiefComplaintID, chiefComplaint, "
			+ "duration, unitOfDuration, description, visitCode,conceptID "
			+ "from BenChiefComplaint ba WHERE ba.beneficiaryRegID = :benRegID "
			+ " AND ba.deleted = false AND ba.chiefComplaintID is not null AND ba.visitCode = :visitCode")
	public ArrayList<Object[]> getBenChiefComplaints(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Modifying
	@Transactional
	@Query(" Delete from BenChiefComplaint WHERE beneficiaryRegID = :benRegID AND visitCode = :visitCode")
	public int deleteExistingBenChiefComplaints(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

}
