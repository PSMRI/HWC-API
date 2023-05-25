package com.iemr.mmu.repo.neonatal;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.neonatal.BirthDoseVaccinesMasterData;
import com.iemr.mmu.data.neonatal.CounsellingProvidedMasterData;

@Repository
@RestResource(exported = false)
public interface CounsellingProvidedMasterRepo extends CrudRepository<CounsellingProvidedMasterData, Integer> {
	
	List<CounsellingProvidedMasterData> findByDeletedAndVisitCategoryIDOrderByName(Boolean deleted, Integer vcid);

}
