package com.iemr.mmu.repo.syncActivity_syncLayer;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.syncActivity_syncLayer.TempVan;

@Repository
@RestResource(exported = false)
public interface TempVanRepo extends CrudRepository<TempVan, Integer> {
	@Query(" SELECT v FROM TempVan v")
	public ArrayList<TempVan> getVanID();

}
