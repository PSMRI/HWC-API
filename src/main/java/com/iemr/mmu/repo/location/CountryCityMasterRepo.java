/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.location;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.location.Country;
import com.iemr.mmu.data.location.CountryCityMaster;

@Repository
@RestResource(exported = false)
public interface CountryCityMasterRepo extends CrudRepository<CountryCityMaster, Integer> {
	
	
	@Query("select c from CountryCityMaster c where countryID = :countryID and deleted = :deleted order by cityName ")
	ArrayList<CountryCityMaster> findByCountryIDAndDeleted(@Param("countryID") int countryID, @Param("deleted") boolean deleted);
}

