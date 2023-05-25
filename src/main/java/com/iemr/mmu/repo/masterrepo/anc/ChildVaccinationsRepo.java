package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.ChildVaccinations;

@Repository
@RestResource(exported = false)
public interface ChildVaccinationsRepo extends CrudRepository<ChildVaccinations, Short> {

	@Query("select vaccinationID, vaccinationTime, vaccineName,sctCode,sctTerm from ChildVaccinations where deleted = false order by vaccineName")
	public ArrayList<Object[]> getChildVaccinations();

	List<ChildVaccinations> findByDeletedOrderByVaccinationTime(Boolean deleted);
}
