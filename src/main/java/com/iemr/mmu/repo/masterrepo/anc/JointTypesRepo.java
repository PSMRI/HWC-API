/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.JointTypes;

@Repository
@RestResource(exported = false)
public interface JointTypesRepo extends CrudRepository<JointTypes, Short>{
	
	@Query("select jointID, jointType, jointTypeDesc from JointTypes where deleted = false order by jointType")
	public ArrayList<Object[]> getJointTypes();
}
