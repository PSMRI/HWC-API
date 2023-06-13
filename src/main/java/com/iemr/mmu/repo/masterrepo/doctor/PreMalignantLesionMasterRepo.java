/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.doctor;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.doctor.PreMalignantLesion;
@Repository
@RestResource(exported = false)
public interface PreMalignantLesionMasterRepo extends CrudRepository<PreMalignantLesion, Long>{

	@Query("select preMalignantLesionID, preMalignantLesionType from PreMalignantLesion where deleted = false "
			+ "order by preMalignantLesionType")
	public ArrayList<Object[]> getPreMalignantLesionMaster();
	
}
