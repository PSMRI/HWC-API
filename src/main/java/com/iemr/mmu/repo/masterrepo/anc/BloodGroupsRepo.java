/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.BloodGroups;

@Repository
@RestResource(exported = false)
public interface BloodGroupsRepo extends CrudRepository<BloodGroups, Integer> {

	@Query("select bloodGroupID, bloodGroup, bloodGroupDesc from BloodGroups where deleted = false order by bloodGroup")
	public ArrayList<Object[]> getBloodGroups();

	List<BloodGroups> findByDeleted(Boolean deleted);
}
