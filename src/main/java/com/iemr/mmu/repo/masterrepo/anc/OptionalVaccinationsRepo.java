package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.OptionalVaccinations;

@Repository
@RestResource(exported = false)
public interface OptionalVaccinationsRepo extends CrudRepository<OptionalVaccinations, Short>{
	
	@Query("select vaccineID, vaccineName, sctCode, sctTerm from OptionalVaccinations where deleted = false"
			+ " order by vaccineName")
	public ArrayList<Object[]> getOptionalVaccinations();
}
