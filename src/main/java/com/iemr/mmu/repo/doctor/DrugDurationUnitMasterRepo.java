/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.doctor;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.doctor.DrugDurationUnitMaster;
@Repository
@RestResource(exported = false)
public interface DrugDurationUnitMasterRepo extends CrudRepository<DrugDurationUnitMaster, Integer> {
	
	@Query("SELECT drugDurationID, drugDuration FROM DrugDurationUnitMaster c where c.deleted != 1 order by drugDuration")
	public  ArrayList<Object[]> getDrugDurationUnitMaster();
	
}
