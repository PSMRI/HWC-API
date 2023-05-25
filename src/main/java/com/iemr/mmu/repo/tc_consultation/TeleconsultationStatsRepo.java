package com.iemr.mmu.repo.tc_consultation;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.tele_consultation.TeleconsultationStats;

@Repository
@RestResource(exported = false)
public interface TeleconsultationStatsRepo extends CrudRepository<TeleconsultationStats, Long> {

	@Query(" SELECT COUNT(tMStatsID)  FROM TeleconsultationStats t"
			+ " WHERE t.beneficiaryRegID = :benRegID AND t.visitCode = :visitCode and t.deleted is false")
	public int checkTCRecordCount(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Query(nativeQuery = true, value = " SELECT * from t_tmstats t "
			+ " WHERE t.beneficiaryRegID = :benRegID AND t.visitCode = :visitCode and t.deleted is false "
			+ " ORDER BY t.tMStatsID DESC LIMIT 1 ")
	public TeleconsultationStats getLatestStartTime(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

}
