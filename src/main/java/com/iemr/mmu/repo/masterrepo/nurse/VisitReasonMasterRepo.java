package com.iemr.mmu.repo.masterrepo.nurse;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.nurse.VisitReason;
@Repository
@RestResource(exported = false)
public interface VisitReasonMasterRepo extends CrudRepository<VisitReason, Long>{

	@Query("select visitReasonID, visitReason from VisitReason where deleted = false order by visitReason ")
	public ArrayList<Object[]> getVisitReasonMaster();
	
}
