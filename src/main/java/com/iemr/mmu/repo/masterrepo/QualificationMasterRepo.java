package com.iemr.mmu.repo.masterrepo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.registrar.QualificationMaster;

@Repository
@RestResource(exported = false)
public interface QualificationMasterRepo extends CrudRepository<QualificationMaster, Short> {
	@Query("select educationID, educationType from QualificationMaster where  deleted = false "
			+ " and educationID > 1 order by educationType ")
	public ArrayList<Object[]> getQualificationMaster();
}
