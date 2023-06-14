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
package com.iemr.mmu.repo.neonatal;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.mmu.data.neonatal.ImmunizationServices;

@Repository
public interface ImmunizationServicesRepo extends CrudRepository<ImmunizationServices, Long> {
	List<ImmunizationServices> findByBeneficiaryRegIDAndVisitCodeAndDeleted(long benRegId, long visitCode,
			boolean deleted);

	List<ImmunizationServices> findByBeneficiaryRegIDAndDeleted(long benRegId, boolean deleted);

	@Transactional
	@Modifying
	@Query(" UPDATE ImmunizationServices t SET t.deleted=:deleted WHERE t.beneficiaryRegID=:beneficiaryRegID"
			+ " AND t.visitCode=:visitCode ")
	public int updateDeletedFlag(@Param("deleted") Boolean deleted, @Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode);
}
