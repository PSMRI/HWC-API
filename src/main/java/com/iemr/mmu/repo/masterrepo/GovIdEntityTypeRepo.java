/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.registrar.GovIdEntityType;

@Repository
@RestResource(exported = false)
public interface GovIdEntityTypeRepo extends CrudRepository<GovIdEntityType, Short> {
	@Query(" select govtIdentityTypeID, identityType from GovIdEntityType where  deleted = false "
			+ " and isGovtID = true  order by identityType ")
	public ArrayList<Object[]> getGovIdEntityMaster();
	
	@Query(" select govtIdentityTypeID, identityType from GovIdEntityType where  deleted = false  "
			+ " and isGovtID = false order by identityType ")
	public ArrayList<Object[]> getOtherGovIdEntityMaster();
}
