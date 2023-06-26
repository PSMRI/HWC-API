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
package com.iemr.hwc.repo.report;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.hwc.data.report.BenChiefComplaintReport;

@Repository
@RestResource(exported = false)
public interface BenChiefComplaintReportRepo extends CrudRepository<BenChiefComplaintReport, Long> {

	@Query(value = "call db_reporting.SP_ChiefComplaintReport(:startDate, :toDate,:ppID)", nativeQuery = true)
	List<Object[]> getcmreport(@Param("startDate")Date fromDate,@Param("toDate") Date toDate,@Param("ppID") Integer parkingPlaceID);
	
	@Query(value = "call db_reporting.SP_Consultation(:startDate, :toDate,:ppID)", nativeQuery = true)
	List<Object[]> getConsultationReport(@Param("startDate")Date fromDate,@Param("toDate") Date toDate,@Param("ppID") Integer parkingPlaceID);

	@Query(value = "call db_reporting.SP_TotalConsultation(:startDate, :toDate,:ppID)", nativeQuery = true)
	List<Object[]> getTotalConsultationReport(@Param("startDate")Date fromDate,@Param("toDate") Date toDate,@Param("ppID") Integer parkingPlaceID);

	@Query(value = "call db_reporting.SP_TMMonthlyReport(:startDate, :toDate,:ppID,:vanID)", nativeQuery = true)
	List<Object[]> getMonthlyReport(@Param("startDate")Date fromDate,@Param("toDate") Date toDate,@Param("ppID") Integer parkingPlaceID,@Param("vanID") Integer vanID);

	@Query(value = "call db_reporting.SP_TMDailyReport(:startDate,:ppID)", nativeQuery = true)
	List<Object[]> getDailyReport(@Param("startDate")Date fromDate, @Param("ppID")Integer ppid);

	
}
