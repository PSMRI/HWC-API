/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.registrar.AgeUnit;

@Repository
@RestResource(exported = false)
public interface AgeUnitRepo extends CrudRepository<AgeUnit, Integer> {
	
    @Query("select obj from AgeUnit obj where obj.deleted = false")
	public ArrayList<AgeUnit> getAgeUnit();
    
    ArrayList<AgeUnit> findByDeletedOrderByName(Boolean deleted);
    
    
	
	

	

}
