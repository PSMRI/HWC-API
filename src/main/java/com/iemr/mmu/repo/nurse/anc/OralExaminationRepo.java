/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.nurse.anc;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.anc.OralExamination;

@Repository
@RestResource(exported = false)
public interface OralExaminationRepo extends CrudRepository<OralExamination, Long> {

	public OralExamination findByBeneficiaryRegIDAndVisitCodeAndDeleted(Long benRegId, Long visitCode, Boolean deleted);

}
