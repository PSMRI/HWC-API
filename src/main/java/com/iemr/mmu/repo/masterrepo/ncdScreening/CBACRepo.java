package com.iemr.mmu.repo.masterrepo.ncdScreening;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.ncdscreening.CBAC;
@Repository
public interface CBACRepo extends CrudRepository<CBAC, Integer> {
	
	@Query("select c from CBAC c  where c.deleted = false AND (c.gender =:gender OR c.gender is null) order by questionId")
	public ArrayList<CBAC> getCBAC(@Param("gender") String gender);

}
