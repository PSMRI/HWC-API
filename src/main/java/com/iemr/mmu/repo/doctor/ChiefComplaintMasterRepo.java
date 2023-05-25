package com.iemr.mmu.repo.doctor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.doctor.ChiefComplaintMaster;

@Repository
@RestResource(exported = false)
public interface ChiefComplaintMasterRepo extends CrudRepository<ChiefComplaintMaster, Integer> {

	@Query("SELECT chiefComplaintID, chiefComplaint, chiefComplaintDesc FROM ChiefComplaintMaster c where c.deleted != 1 order by chiefComplaint")
	public ArrayList<Object[]> getChiefComplaintMaster();

	List<ChiefComplaintMaster> findByDeletedOrderByChiefComplaint(Boolean deleted);

}
