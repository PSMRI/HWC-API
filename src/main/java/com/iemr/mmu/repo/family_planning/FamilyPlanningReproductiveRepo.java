/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.family_planning;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.family_planning.FamilyPlanningReproductive;

@Repository
public interface FamilyPlanningReproductiveRepo extends CrudRepository<FamilyPlanningReproductive, Long> {
	public FamilyPlanningReproductive findOneByBeneficiaryRegIDAndVisitCode(Long regId, Long visitCode);

	public List<FamilyPlanningReproductive> findByBeneficiaryRegIDOrderByIdDesc(Long regId);

}
