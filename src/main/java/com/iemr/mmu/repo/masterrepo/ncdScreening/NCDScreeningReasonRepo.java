package com.iemr.mmu.repo.masterrepo.ncdScreening;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.ncdscreening.NCDScreeningReason;

@Repository
@RestResource(exported = false)
public interface NCDScreeningReasonRepo extends CrudRepository<NCDScreeningReason, Long>{

	@Query("select ncdScreeningReasonID, ncdScreeningReason from NCDScreeningReason where deleted = false order by ncdScreeningReason")
	public ArrayList<Object[]> getNCDScreeningReasons();
}
