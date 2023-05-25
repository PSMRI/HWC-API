package com.iemr.mmu.repo.location;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.location.States;

@Repository
@RestResource(exported = false)
public interface StateMasterRepo extends CrudRepository<States, Integer> {
	@Query(" SELECT stateID, stateName FROM States WHERE deleted != 1 ")
	public ArrayList<Object[]> getStateMaster();
}
