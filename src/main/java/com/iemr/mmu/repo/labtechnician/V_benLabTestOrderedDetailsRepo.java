package com.iemr.mmu.repo.labtechnician;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.labtechnician.V_benLabTestOrderedDetails;

@Repository
@RestResource(exported = false)
public interface V_benLabTestOrderedDetailsRepo extends CrudRepository<V_benLabTestOrderedDetails, Long> {
	ArrayList<V_benLabTestOrderedDetails> findDistinctByBeneficiaryRegIDAndVisitCodeAndProcedureTypeAndProcedureIDNotInOrderByProcedureIDAscTestComponentIDAscResultValueAsc(
			Long benRegID, Long visitCode, String procedureType, ArrayList<Integer> ids);
}
