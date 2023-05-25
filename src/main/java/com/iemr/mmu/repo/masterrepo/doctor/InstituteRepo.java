package com.iemr.mmu.repo.masterrepo.doctor;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.institution.Institute;

@Repository
@RestResource(exported = false)
public interface InstituteRepo extends CrudRepository<Institute, Long> {
	@Query("SELECT institutionID, institutionName FROM Institute WHERE providerServiceMapID = :psmID AND deleted != 1 order by institutionName")
	public ArrayList<Object[]> getInstituteDetails(@Param("psmID") Integer psmID);

}
