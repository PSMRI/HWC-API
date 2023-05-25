package com.iemr.mmu.repo.location;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.location.V_getVanLocDetails;

@Repository
@RestResource(exported = false)
public interface V_getVanLocDetailsRepo extends CrudRepository<V_getVanLocDetails, Integer> {
	@Query(" SELECT distinct t.stateID, t.parkingPlaceID, t.districtID, t.districtName "
			+ " FROM V_getVanLocDetails t WHERE t.vanID = :vanID ")
	ArrayList<Object[]> getVanDetails(@Param("vanID") Integer vanID);

}
