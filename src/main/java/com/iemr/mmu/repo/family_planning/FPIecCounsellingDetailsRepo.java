/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.family_planning;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.family_planning.FPIecCounsellingDetails;

@Repository
public interface FPIecCounsellingDetailsRepo extends CrudRepository<FPIecCounsellingDetails, Long> {
	public FPIecCounsellingDetails findOneByBeneficiaryRegIDAndVisitCode(Long regId, Long visitCode);

	public List<FPIecCounsellingDetails> findByBeneficiaryRegIDOrderByIdDesc(Long regId);
}
