/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.DiseaseType;

@Repository
@RestResource(exported = false)
public interface DiseaseTypeRepo extends CrudRepository<DiseaseType, Short> {

	@Query(" SELECT diseaseTypeID, diseaseType, snomedCode, snomedTerm FROM DiseaseType WHERE deleted = false "
			+ " ORDER BY diseaseType")
	public ArrayList<Object[]> getDiseaseTypes();
}
