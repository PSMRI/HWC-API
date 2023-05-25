package com.iemr.mmu.repo.syncActivity_syncLayer;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.mmu.data.syncActivity_syncLayer.SyncDownloadMaster;

@Repository
@RestResource(exported = false)
public interface SyncDownloadMasterRepo extends CrudRepository<SyncDownloadMaster, Integer> {
	@Query(" SELECT u FROM SyncDownloadMaster u WHERE u.deleted is false ")
	ArrayList<SyncDownloadMaster> getDownloadTables();

	@Transactional
	@Modifying
	@Query(" UPDATE SyncDownloadMaster u SET u.lastDownloadDate = Date(Now()) WHERE u.downloadMasterTableID =:ID ")
	int updateTableSyncDownloadMasterForLastDownloadDate(@Param("ID") Integer ID);
}
