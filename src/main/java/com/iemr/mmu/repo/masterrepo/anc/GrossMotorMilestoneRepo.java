package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.GrossMotorMilestone;

@Repository
@RestResource(exported = false)
public interface GrossMotorMilestoneRepo extends CrudRepository<GrossMotorMilestone, Short>{

	@Query("select gMMilestoneID, gMMilestone, gMMilestoneDesc from GrossMotorMilestone where deleted = false order by gMMilestone")
	public ArrayList<Object[]> getGrossMotorMilestones();
	
}
