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
package com.iemr.hwc.repo.tc_consultation;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.hwc.data.tele_consultation.TeleconsultationStats;

@Repository
@RestResource(exported = false)
public interface TeleconsultationStatsRepo extends CrudRepository<TeleconsultationStats, Long> {

	@Query(" SELECT COUNT(tMStatsID)  FROM TeleconsultationStats t"
			+ " WHERE t.beneficiaryRegID = :benRegID AND t.visitCode = :visitCode and t.deleted = false")
	public int checkTCRecordCount(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Query(nativeQuery = true, value = " SELECT * from t_tmstats t "
			+ " WHERE t.beneficiaryRegID = :benRegID AND t.visitCode = :visitCode and t.deleted = false "
			+ " ORDER BY t.tMStatsID DESC LIMIT 1 ")
	public TeleconsultationStats getLatestStartTime(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

}
