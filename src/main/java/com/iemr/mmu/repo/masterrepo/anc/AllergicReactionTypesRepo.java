/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.AllergicReactionTypes;
import com.iemr.mmu.data.masterdata.pnc.DeliveryConductedByMaster;
@Repository
@RestResource(exported = false)
public interface AllergicReactionTypesRepo extends CrudRepository<AllergicReactionTypes, Short>{
	
	@Query("select allergicReactionTypeID, name, allergicReactionTypeDesc from AllergicReactionTypes where deleted = false order by name")
	public ArrayList<Object[]> getAllergicReactionTypes();
	
	
	List<AllergicReactionTypes> findByDeleted(Boolean deleted);
}
