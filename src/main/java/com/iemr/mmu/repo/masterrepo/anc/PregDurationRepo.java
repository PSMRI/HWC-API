/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.PregDuration;

@Repository
@RestResource(exported = false)
public interface PregDurationRepo extends CrudRepository<PregDuration, Short>{
	
	@Query("select pregDurationID, durationType from PregDuration where deleted = false order by durationType")
	public ArrayList<Object[]> getPregDurationTypes();
}
