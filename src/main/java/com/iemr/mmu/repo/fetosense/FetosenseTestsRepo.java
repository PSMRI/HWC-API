package com.iemr.mmu.repo.fetosense;


import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.fetosense.FetosenseTestMaster;


@Repository
@RestResource(exported = false)
public interface FetosenseTestsRepo extends CrudRepository<FetosenseTestMaster, Integer> {
	
	@Query("SELECT f.fetosenseTestId, f.testName FROM FetosenseTestMaster f WHERE f.providerServiceMapID = :providerServiceMapID AND f.deleted = false")
	public ArrayList<Object[]> getFetosenseTestsDetails(@Param("providerServiceMapID") Integer providerServiceMapID);
	



}

