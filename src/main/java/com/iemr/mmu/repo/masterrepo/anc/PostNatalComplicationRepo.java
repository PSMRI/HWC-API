/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.PostNatalComplication;

@Repository
@RestResource(exported = false)
public interface PostNatalComplicationRepo extends CrudRepository<PostNatalComplication, Short>{
	
	@Query("select postNatalComplicationID, postNatalComplication from PostNatalComplication where deleted = false order by postNatalComplication ")
	public ArrayList<Object[]> getPostNatalComplications();
	
}
