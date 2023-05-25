package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.NewBornComplication;

@Repository
@RestResource(exported = false)
public interface NewBornComplicationRepo extends CrudRepository<NewBornComplication, Short>{

	@Query("select newBornComplicationID, newBornComplication from NewBornComplication where deleted = false"
			+ " order by newBornComplication")
	public ArrayList<Object[]> getNewBornComplications();
}
