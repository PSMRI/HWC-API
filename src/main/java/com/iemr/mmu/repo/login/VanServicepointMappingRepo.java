/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.login;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.login.VanServicepointMapping;

@Repository
@RestResource(exported = false)
public interface VanServicepointMappingRepo extends CrudRepository<VanServicepointMapping, Integer> {

	@Query("select s.servicePointID, s.servicePointName, v.vanSession "
			+ " from VanServicepointMapping v inner join v.masterServicePoint s "
			+ " where s.parkingPlaceID in :parkingPlaceList and v.deleted != 1 and s.deleted != 1 ")
	public List<Object[]> getuserSpSessionDetails(@Param("parkingPlaceList") Set<Integer> parkingPlaceList);
}
