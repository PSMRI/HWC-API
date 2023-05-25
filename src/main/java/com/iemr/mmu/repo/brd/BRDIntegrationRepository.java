package com.iemr.mmu.repo.brd;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.uptsu.Referred104Details;

@Repository
public interface BRDIntegrationRepository extends CrudRepository<Referred104Details, Integer>{
	
	@Query(value = "call Pr_104IntegrationBhayaData(:startDate,:endDate)", nativeQuery = true)
	ArrayList<Object[]> getData(@Param("startDate")String startDate,@Param("endDate")String endDate);

}
