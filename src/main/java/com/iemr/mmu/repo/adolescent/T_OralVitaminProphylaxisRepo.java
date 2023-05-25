package com.iemr.mmu.repo.adolescent;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.adolescent.T_OralVitaminProphylaxis;

@Repository
public interface T_OralVitaminProphylaxisRepo extends CrudRepository<T_OralVitaminProphylaxis, Long> {

	List<T_OralVitaminProphylaxis> findByBeneficiaryRegIDAndVisitCodeAndDeleted(Long regId, Long visitCode,
			Boolean deleted);
}
