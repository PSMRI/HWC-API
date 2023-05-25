package com.iemr.mmu.repo.neonatal;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.neonatal.ImmunizationServiceRouteMaster;

@Repository
@RestResource(exported = false)
public interface ImmunizationServiceRouteMasterRepo extends CrudRepository<ImmunizationServiceRouteMaster, Integer> {

	List<ImmunizationServiceRouteMaster> findByDeletedOrderByRoute(Boolean deleted);

	List<ImmunizationServiceRouteMaster> findByVaccinationIDAndDeletedOrderByRoute(Integer vID, Boolean deleted);

}
