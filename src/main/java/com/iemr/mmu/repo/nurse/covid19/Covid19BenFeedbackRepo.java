/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.nurse.covid19;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.covid19.Covid19BenFeedback;

@Repository
@RestResource(exported = false)
public interface Covid19BenFeedbackRepo extends CrudRepository<Covid19BenFeedback, Long> {
	Covid19BenFeedback findByBeneficiaryRegIDAndVisitCode(Long benRegID, Long visitCode);
}
