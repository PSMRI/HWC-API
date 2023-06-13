/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.ncdCare;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.ncdcare.NCDCareType;

@Repository
@RestResource(exported = false)
public interface NCDCareTypeRepo extends CrudRepository<NCDCareType, Integer>
{

	@Query("select ncdCareTypeID, ncdCareType from NCDCareType where deleted = false order by ncdCareType")
	public ArrayList<Object[]> getNCDCareTypes();
	
}
