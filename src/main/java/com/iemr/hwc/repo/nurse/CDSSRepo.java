package com.iemr.hwc.repo.nurse;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.hwc.data.nurse.CDSS;

@Repository
public interface CDSSRepo extends CrudRepository<CDSS, Long> {
	
	CDSS findByBeneficiaryRegIDAndVisitCode(Long beneficiaryRegID, Long visitCode);
	

}
