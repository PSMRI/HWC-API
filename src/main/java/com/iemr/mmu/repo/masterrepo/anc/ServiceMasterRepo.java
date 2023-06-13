/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.ServiceMaster;

@Repository
@RestResource(exported = false)
public interface ServiceMasterRepo extends CrudRepository<ServiceMaster, Short>{

	@Query("select serviceID, serviceName, serviceDesc from ServiceMaster where deleted = false order by serviceName")
	public ArrayList<Object[]> getAdditionalServices();
	
}
