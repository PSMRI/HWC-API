package com.iemr.mmu.repo.masterrepo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iemr.mmu.data.masterdata.registrar.RelationshipMaster;

public interface RelationshipMasterRepo extends CrudRepository<RelationshipMaster, Integer> {
	
	@Query("select r from RelationshipMaster r where r.deleted is false")
	ArrayList<RelationshipMaster> getRelationshipMaster();
	
	

}
