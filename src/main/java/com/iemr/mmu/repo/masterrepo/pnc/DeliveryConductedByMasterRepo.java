package com.iemr.mmu.repo.masterrepo.pnc;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.pnc.DeliveryConductedByMaster;

@Repository
public interface DeliveryConductedByMasterRepo extends CrudRepository<DeliveryConductedByMaster, Integer> {

	List<DeliveryConductedByMaster> findByDeleted(Boolean deleted);

}
