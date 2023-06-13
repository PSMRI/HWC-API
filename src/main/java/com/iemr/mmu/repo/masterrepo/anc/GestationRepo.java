/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.Gestation;

@Repository
@RestResource(exported = false)
public interface GestationRepo extends CrudRepository<Gestation, Short> {

	@Query("select gestationID, name, gestationDesc from Gestation where deleted = false order by name")
	public ArrayList<Object[]> getGestationTypes();

	List<Gestation> findByDeletedOrderByName(Boolean deleted);

}
