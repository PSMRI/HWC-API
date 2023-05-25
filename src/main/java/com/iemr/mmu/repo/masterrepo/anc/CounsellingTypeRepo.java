package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.CounsellingType;

@Repository
@RestResource(exported = false)
public interface CounsellingTypeRepo extends CrudRepository<CounsellingType, Short>{

	@Query("select counsellingTypeID, counsellingType, counsellingTypeDesc from CounsellingType where deleted = false order by counsellingType")
	public ArrayList<Object[]> getCounsellingTypes();
	
}
