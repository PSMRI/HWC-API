/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.covid19;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.covid19.CovidRecommendationMaster;

@Repository
@RestResource(exported = false)
public interface CovidRecommnedationMasterRepo extends CrudRepository<CovidRecommendationMaster, Integer> {
	ArrayList<CovidRecommendationMaster> findByDeleted(Boolean deleted);
}
