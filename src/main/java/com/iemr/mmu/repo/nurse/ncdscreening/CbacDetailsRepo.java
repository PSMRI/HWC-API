/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.nurse.ncdscreening;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.ncdScreening.CbacDetails;

@Repository
public interface CbacDetailsRepo extends CrudRepository<CbacDetails, Long> {
	
	public CbacDetails findByBeneficiaryRegIdAndVisitCode(Long beneficiaryRegId, Long visitCode);

}
