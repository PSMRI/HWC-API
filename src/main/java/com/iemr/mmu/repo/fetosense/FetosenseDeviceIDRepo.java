package com.iemr.mmu.repo.fetosense;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.fetosense.FetosenseDeviceID;

@Repository
@RestResource(exported = false)
public interface FetosenseDeviceIDRepo extends CrudRepository<FetosenseDeviceID, Integer>  {
	
	@Query("SELECT f FROM FetosenseDeviceID f WHERE f.vanID = :vanID AND f.deactivated = false ")
	public FetosenseDeviceID getDeviceIDForVanID(@Param("vanID") Integer vanID);
	
}
