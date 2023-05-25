package com.iemr.mmu.repo.masterrepo.nurse;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.nurse.VisitCategory;
@Repository
@RestResource(exported = false)
public interface VisitCategoryMasterRepo extends CrudRepository<VisitCategory, Long>{

	@Query("select visitCategoryID, visitCategory from VisitCategory where deleted = false order by visitCategory ")
	public ArrayList<Object[]> getVisitCategoryMaster();
	
}
