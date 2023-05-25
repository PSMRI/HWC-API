package com.iemr.mmu.repo.masterrepo.nurse;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.nurse.FPMethodFollowup;

@Repository
@RestResource(exported = false)
public interface FPMethodFollowupRepo extends CrudRepository<FPMethodFollowup, Integer> {

	List<FPMethodFollowup> findByDeletedOrderByName(Boolean deleted);
	
	@Query("SELECT p FROM FPMethodFollowup p WHERE ( p.gender =:gender or p.gender is null ) AND p.deleted = false")
	List<FPMethodFollowup> findAllNameByGender(@Param ("gender") String gender);
	
}
