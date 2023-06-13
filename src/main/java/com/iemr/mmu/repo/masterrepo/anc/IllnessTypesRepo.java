/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.IllnessTypes;

@Repository
@RestResource(exported = false)
public interface IllnessTypesRepo extends CrudRepository<IllnessTypes, Integer> {

	@Query(" SELECT illnessID, illnessType FROM IllnessTypes WHERE deleted = false "
			+ " AND (visitCategoryID = :visitCategoryID OR visitCategoryID is null) ORDER BY illnessType ")
	public ArrayList<Object[]> getIllnessTypes(@Param("visitCategoryID") Integer visitCategoryID);
}
