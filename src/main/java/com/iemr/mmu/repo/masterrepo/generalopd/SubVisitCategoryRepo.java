/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.generalopd;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.generalopd.SubVisitCategory;

@Repository
@RestResource(exported = false)
public interface SubVisitCategoryRepo extends CrudRepository<SubVisitCategory, Integer> {
	List<SubVisitCategory> findByDeletedOrderByName(Boolean deleted);

}
