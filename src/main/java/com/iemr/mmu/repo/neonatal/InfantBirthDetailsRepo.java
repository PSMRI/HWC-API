package com.iemr.mmu.repo.neonatal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.neonatal.InfantBirthDetails;

@Repository
public interface InfantBirthDetailsRepo extends CrudRepository<InfantBirthDetails, Long> {

	InfantBirthDetails findByBeneficiaryRegIDAndVisitCode(Long benRegId, Long visitCode);
}
