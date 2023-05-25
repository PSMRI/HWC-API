package com.iemr.mmu.repo.doctor;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.doctor.DrugFormMaster;
@Repository
@RestResource(exported = false)
public interface DrugFormMasterRepo extends CrudRepository<DrugFormMaster, Integer> {
	
	@Query("SELECT drugFormID, drugForm FROM DrugFormMaster c where c.deleted != 1 order by drugForm")
	public  ArrayList<Object[]> getDrugFormMaster();
	
}
