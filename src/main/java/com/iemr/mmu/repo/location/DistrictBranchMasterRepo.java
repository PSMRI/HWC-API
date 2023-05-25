package com.iemr.mmu.repo.location;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.location.DistrictBranchMapping;

@Repository
@RestResource(exported = false)
public interface DistrictBranchMasterRepo extends CrudRepository<DistrictBranchMapping, Integer> {

	@Query(" SELECT districtBranchID, villageName FROM DistrictBranchMapping WHERE blockID = :blockID  AND deleted != 1 ")
	public ArrayList<Object[]> findByBlockID(@Param("blockID") Integer blockID);
	

}
