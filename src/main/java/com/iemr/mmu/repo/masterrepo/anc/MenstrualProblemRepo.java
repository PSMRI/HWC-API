package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.MenstrualProblem;

@Repository
@RestResource(exported = false)
public interface MenstrualProblemRepo extends CrudRepository<MenstrualProblem, Short>{
	
	@Query("select menstrualProblemID, problemName, menstrualProblemDesc from MenstrualProblem where deleted = false order by name")
	public ArrayList<Object[]> getMenstrualProblems();
}
