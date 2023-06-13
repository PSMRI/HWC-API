/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.BirthComplication;

@Repository
@RestResource(exported = false)
public interface BirthComplicationRepo extends CrudRepository<BirthComplication, Short>{
	
	@Query("select birthComplicationID, name, birthComplicationDesc from BirthComplication where deleted = false order by name")
	public ArrayList<Object[]> getBirthComplicationTypes();
}
