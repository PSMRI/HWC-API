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
package com.iemr.mmu.repo.nurse.ncdscreening;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.ncdScreening.DiabetesScreening;

/**
 * 
 * @author SH20094090 17-08-2022
 */

@Repository
public interface DiabetesScreeningRepo extends CrudRepository<DiabetesScreening, Long> {
//
//	@Query("SELECT obj FROM Diabetes obj WHERE obj.beneficiaryRegId = :beneficiaryRegId AND "
//			+ "obj.beneficiaryVisitId = :beneficiaryVisitId")
//	DiabetesScreening fetchBeneficiaryDiabetesDetails(@Param("beneficiaryRegId") Long beneficiaryRegId,
//			@Param("beneficiaryVisitId") Long beneficiaryVisitId);

	public DiabetesScreening findByBeneficiaryRegIdAndVisitcode(Long beneficiaryRegId, Long visitCode);

	@Transactional
	@Modifying
	@Query(" UPDATE DiabetesScreening SET confirmed=:confirmed WHERE beneficiaryRegId = :beneficiaryRegId AND visitcode = :visitcode ")
	public int updateDiabetesScreeningConfirmedStatus(@Param("beneficiaryRegId") Long beneficiaryRegId,
			@Param("visitcode") Long visitcode, @Param("confirmed") Boolean confirmed);
}
