/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.registrar.ReligionMaster;

@Repository
@RestResource(exported = false)
public interface ReligionMasterRepo extends CrudRepository<ReligionMaster, Short> {
	@Query("select religionID, religionType from ReligionMaster where deleted = false order by religionType ")
	public ArrayList<Object[]> getReligionMaster();
}
