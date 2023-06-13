/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.report;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.report.BenChiefComplaintReport;

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
