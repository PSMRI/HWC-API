/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.generalopd;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.generalopd.FoodIntoleranceStatus;

@Repository
@RestResource(exported = false)
public interface FoodIntoleranceStatusRepo extends CrudRepository<FoodIntoleranceStatus, Integer> {
	List<FoodIntoleranceStatus> findByDeletedOrderByIntoleranceStatus(Boolean deleted);
}
