/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.registrar.GenderMaster;

@Repository
@RestResource(exported = false)
public interface GenderMasterRepo extends CrudRepository<GenderMaster, Short> {
	@Query("select genderID, genderName from GenderMaster where deleted = false order by genderName ")
	public ArrayList<Object[]> getGenderMaster();
	
	ArrayList<GenderMaster> findByDeletedOrderByGenderName(Boolean deleted);
}
