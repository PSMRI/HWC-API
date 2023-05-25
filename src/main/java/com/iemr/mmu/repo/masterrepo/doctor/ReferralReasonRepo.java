package com.iemr.mmu.repo.masterrepo.doctor;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.doctor.ReferralReason;

@Repository
@RestResource(exported = false)
public interface ReferralReasonRepo extends CrudRepository<ReferralReason, Integer> {
	
	List<ReferralReason> findByDeletedAndVisitCategoryIdOrderByName(Boolean deleted, Integer vcid);

}
