/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.ComplicationTypes;

@Repository
@RestResource(exported = false)
public interface ComplicationTypesRepo extends CrudRepository<ComplicationTypes, Short>{

	@Query("select complicationID, complicationValue from ComplicationTypes where deleted = false and "
			+ "complicationType=:complicationType order by complicationValue")
	public ArrayList<Object[]> getComplicationTypes(@Param("complicationType") String complicationType);
	
}
