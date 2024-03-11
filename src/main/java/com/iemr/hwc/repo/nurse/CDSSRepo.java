package com.iemr.hwc.repo.nurse;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.hwc.data.nurse.CDSS;

@Repository
@RestResource(exported = false)
public interface CDSSRepo extends CrudRepository<CDSS, Long> {
	
	CDSS findByBeneficiaryRegIDAndVisitCode(Long beneficiaryRegID, Long visitCode);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM CDSS WHERE visitCode=:visitCode ")
	public int deleteVisitDetails(@Param("visitCode") Long visitCode);
	

}
