/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.neonatal;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.neonatal.ImmunizationServiceVaccinationMaster;

@Repository
@RestResource(exported = false)
public interface ImmunizationServiceVaccinationMasterRepo
		extends CrudRepository<ImmunizationServiceVaccinationMaster, Integer> {

	List<ImmunizationServiceVaccinationMaster> findByDeleted(Boolean deleted);

	List<ImmunizationServiceVaccinationMaster> findByCurrentImmunizationServiceIdAndDeletedAndVisitCategoryID(
			Integer cisid, Boolean deleted, Integer vcid);

}
