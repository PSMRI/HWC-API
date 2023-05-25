package com.iemr.mmu.repo.family_planning;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.family_planning.FPDispenseDetails;

@Repository
public interface FPDispenseDetailsRepo extends CrudRepository<FPDispenseDetails, Long> {
	public FPDispenseDetails findOneByBeneficiaryRegIDAndVisitCode(Long regId, Long visitCode);

	public List<FPDispenseDetails> findByBeneficiaryRegIDOrderByIdDesc(Long regId);
}
