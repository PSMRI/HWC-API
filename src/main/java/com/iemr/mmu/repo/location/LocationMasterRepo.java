package com.iemr.mmu.repo.location;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.location.DistrictBranchMapping;


@Repository
@RestResource(exported = false)
public interface LocationMasterRepo extends CrudRepository<DistrictBranchMapping, Integer> {
	@Query(value="call Pr_Locationdetails(:villageID)", nativeQuery=true)
	public List<Objects[]> getLocationMaster(@Param("villageID") Integer villageID);
	

}