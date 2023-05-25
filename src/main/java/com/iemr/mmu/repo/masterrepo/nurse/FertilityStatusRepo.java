package com.iemr.mmu.repo.masterrepo.nurse;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.nurse.FertilityStatus;

@Repository
@RestResource(exported = false)
public interface FertilityStatusRepo extends CrudRepository<FertilityStatus, Integer> {
	List<FertilityStatus> findByDeletedOrderByName(Boolean deleted);
}
