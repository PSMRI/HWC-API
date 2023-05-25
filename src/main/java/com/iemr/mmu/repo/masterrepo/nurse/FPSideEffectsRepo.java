package com.iemr.mmu.repo.masterrepo.nurse;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.nurse.FPSideEffects;

@Repository
@RestResource(exported = false)
public interface FPSideEffectsRepo extends CrudRepository<FPSideEffects, Integer> {
	List<FPSideEffects> findByDeletedOrderByName(Boolean deleted);
}
