package com.iemr.mmu.repo.syncActivity_syncLayer;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.syncActivity_syncLayer.SyncUtilityClass;

@Repository
@RestResource(exported = false)
public interface SyncUtilityClassRepo extends CrudRepository<SyncUtilityClass, Integer> {
	List<SyncUtilityClass> findBySyncTableGroupIDAndDeletedOrderBySyncTableDetailID(Integer groupID, Boolean deleted);
}
