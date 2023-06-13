/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.pnc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.pnc.NewbornHealthStatus;

@Repository
@RestResource(exported = false)
public interface NewbornHealthStatusRepo extends CrudRepository<NewbornHealthStatus, Integer>
{
	@Query("select newBornHealthStatusID, newBornHealthStatus, newBornHealthStatusDesc from NewbornHealthStatus where deleted = false "
			+ "order by newBornHealthStatus")
	public ArrayList<Object[]> getnewBornHealthStatuses();
}
