package com.iemr.mmu.repo.masterrepo.doctor;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.doctor.ItemFormMaster;

@Repository
@RestResource(exported = false)
public interface ItemFormMasterRepo extends CrudRepository<ItemFormMaster, Integer> {
	
	@Query("SELECT itemFormID, itemFormName FROM ItemFormMaster WHERE deleted is false")
	public ArrayList<Object[]> getItemFormMaster();

}
