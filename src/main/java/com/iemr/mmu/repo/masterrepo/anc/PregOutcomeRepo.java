package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.PregOutcome;

@Repository
@RestResource(exported = false)
public interface PregOutcomeRepo extends CrudRepository<PregOutcome, Short>{
	
	@Query("select pregOutcomeID, pregOutcome from PregOutcome where deleted = false order by pregOutcome")
	public ArrayList<Object[]> getPregOutcomes();
	
}
