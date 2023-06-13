/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.nurse;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.nurse.CancerPersonalHabitType;
@Repository
@RestResource(exported = false)
public interface CancerPersonalHabitMasterRepo extends CrudRepository<CancerPersonalHabitType, Long>{

	@Query("select cancerPersonalHabitID, habitType, habitValue from CancerPersonalHabitType where deleted = false and habitType=:habitType "
			+ "order by habitValue ")
	public ArrayList<Object[]> getCancerPersonalHabitTypeMaster(@Param("habitType") String habitType);
	
}
