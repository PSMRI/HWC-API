package com.iemr.mmu.repo.masterrepo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.registrar.LiteracyStatus;

@Repository
@RestResource(exported = false)
public interface LiteracyStatusRepo extends CrudRepository<LiteracyStatus, Integer> {
	
	@Query("select l from LiteracyStatus l  where l.deleted = false")
	public ArrayList<LiteracyStatus> getLiteracyStatus();

}
