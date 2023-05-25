package com.iemr.mmu.repo.doctor;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.doctor.DrugDoseMaster;

@Repository
@RestResource(exported = false)
public interface DrugDoseMasterRepo extends CrudRepository<DrugDoseMaster, Integer> {
	
	@Query("SELECT drugDoseID, drugDose, itemFormID FROM DrugDoseMaster c where c.deleted != 1 order by drugDose")
	public  ArrayList<Object[]> getDrugDoseMaster();
	
}
