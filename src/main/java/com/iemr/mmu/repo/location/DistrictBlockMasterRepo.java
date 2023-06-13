/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.location;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.location.DistrictBlock;

@Repository
@RestResource(exported = false)
public interface DistrictBlockMasterRepo extends CrudRepository<DistrictBlock, Integer> {
	@Query(" SELECT blockID, blockName FROM DistrictBlock WHERE districtID = :districtID AND deleted != 1 ")
	public ArrayList<Object[]> getDistrictBlockMaster(@Param("districtID") Integer districtID);

}
