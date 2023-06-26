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
package com.iemr.hwc.repo.nurse.ncdscreening;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.hwc.data.ncdScreening.HypertensionScreening;

/**
 * 
 * @author SH20094090 17-08-2022
 */

@Repository
public interface HypertensionScreeningRepo extends CrudRepository<HypertensionScreening, Long> {
//	@Query("SELECT obj FROM Hypertension obj WHERE obj.beneficiaryRegId = :beneficiaryRegId AND "
//			+ "obj.beneficiaryVisitId = :beneficiaryVisitId")
//	HypertensionScreening fetchBeneficiaryHypertensionDetails(@Param("beneficiaryRegId") Long beneficiaryRegId,
//			@Param("beneficiaryVisitId") Long beneficiaryVisitId);

	@Query(value = " CALL db_iemr.Pr_ConfirmatoryScreening(:beneficiaryRegId) " + " ", nativeQuery = true)
	public List<Object[]> fetchConfirmedScreening(@Param("beneficiaryRegId") Long beneficiaryRegId);

	public HypertensionScreening findByBeneficiaryRegIdAndVisitcode(Long beneficiaryRegId, Long visitcode);

	@Transactional
	@Modifying
	@Query(" UPDATE HypertensionScreening SET confirmed=:confirmed WHERE beneficiaryRegId = :beneficiaryRegId AND visitcode = :visitcode ")
	public int updateHypertensionScreeningConfirmedStatus(@Param("beneficiaryRegId") Long beneficiaryRegId,
			@Param("visitcode") Long visitcode, @Param("confirmed") Boolean confirmed);

}
