/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.neonatal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.neonatal.FollowUpForImmunization;

@Repository
public interface FollowUpForImmunizationRepo extends CrudRepository<FollowUpForImmunization, Long> {

	FollowUpForImmunization findByBeneficiaryRegIDAndVisitCode(long benRegId, long visitCode);
}
