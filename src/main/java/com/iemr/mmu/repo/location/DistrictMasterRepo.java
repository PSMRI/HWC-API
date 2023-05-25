package com.iemr.mmu.repo.location;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.location.Districts;

@Repository
@RestResource(exported = false)
public interface DistrictMasterRepo extends CrudRepository<Districts, Integer> {
	@Query(" SELECT d.districtID, d.districtName FROM Districts d " + " WHERE d.stateID = :stateID AND d.deleted != 1 ")
	public ArrayList<Object[]> getDistrictMaster(@Param("stateID") Integer stateID);

}
