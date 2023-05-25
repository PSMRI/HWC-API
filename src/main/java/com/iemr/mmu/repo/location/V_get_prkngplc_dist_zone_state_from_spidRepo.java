package com.iemr.mmu.repo.location;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.location.V_get_prkngplc_dist_zone_state_from_spid;

@Repository
@RestResource(exported = false)
public interface V_get_prkngplc_dist_zone_state_from_spidRepo
		extends CrudRepository<V_get_prkngplc_dist_zone_state_from_spid, Long> {

	@Query(" SELECT Distinct parkingPlaceID, parkingPlaceName, districtID, districtName,"
			+ " zoneID, zoneName, stateID, stateName " + " FROM V_get_prkngplc_dist_zone_state_from_spid "
			+ " WHERE servicePointID =:spID AND providerServiceMapID =:psmID ")
	ArrayList<Object[]> getDefaultLocDetails(@Param("spID") Integer spID, @Param("psmID") Integer psmID);

}
