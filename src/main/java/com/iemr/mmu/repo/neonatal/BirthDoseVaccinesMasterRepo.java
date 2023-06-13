/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.neonatal;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.neonatal.BirthDoseVaccinesMasterData;

@Repository
@RestResource(exported = false)
public interface BirthDoseVaccinesMasterRepo extends CrudRepository<BirthDoseVaccinesMasterData, Integer> {
	
	List<BirthDoseVaccinesMasterData> findByDeletedOrderByName(Boolean deleted);

}
