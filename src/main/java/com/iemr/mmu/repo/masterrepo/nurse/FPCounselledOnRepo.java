/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.nurse;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.nurse.FPCounselledOn;

@Repository
@RestResource(exported = false)
public interface FPCounselledOnRepo extends CrudRepository<FPCounselledOn, Integer> {
	List<FPCounselledOn> findByDeletedOrderByName(Boolean deleted);
}
