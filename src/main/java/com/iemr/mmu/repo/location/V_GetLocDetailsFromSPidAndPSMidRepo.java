package com.iemr.mmu.repo.location;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.location.V_GetLocDetailsFromSPidAndPSMid;

@Repository
@RestResource(exported = false)
public interface V_GetLocDetailsFromSPidAndPSMidRepo extends CrudRepository<V_GetLocDetailsFromSPidAndPSMid, Long> {

	@Query(" SELECT parkingPlaceID, parkingPlaceName, blockID, blockName, districtID, districtName, zoneID, "
			+ " zoneName, stateID, stateName "
			+ " FROM  V_GetLocDetailsFromSPidAndPSMid "
			+ " WHERE servicepointid =:spID AND spproviderservicemapid =:spPSMID AND ppproviderservicemapid =:ppPSMID AND zdmproviderservicemapid =:zdmPSMID ")
	ArrayList<Object[]> findByServicepointidAndSpproviderservicemapidAndPpproviderservicemapidAndZdmproviderservicemapid(
			@Param("spID") Integer spID, @Param("spPSMID") Integer spPSMID, @Param("ppPSMID") Integer ppPSMID,
			@Param("zdmPSMID") Integer zdmPSMID);
}
