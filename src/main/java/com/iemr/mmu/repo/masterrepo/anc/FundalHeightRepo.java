package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.FundalHeight;

@Repository
@RestResource(exported = false)
public interface FundalHeightRepo extends CrudRepository<FundalHeight, Short>{

	@Query("select fundalHeightID, fundalHeight, fundalHeightDesc from FundalHeight where deleted = false order by fundalHeight")
	public ArrayList<Object[]> getFundalHeights();
	
}
