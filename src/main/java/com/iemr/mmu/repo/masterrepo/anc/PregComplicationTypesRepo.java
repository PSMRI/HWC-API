package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.PregComplicationTypes;

@Repository
@RestResource(exported = false)
public interface PregComplicationTypesRepo extends CrudRepository<PregComplicationTypes, Integer>{
	
	@Query("select pregComplicationID, pregComplicationType from PregComplicationTypes where "
			+ " deleted = false order by pregComplicationType")
	public ArrayList<Object[]> getPregComplicationTypes();
}
