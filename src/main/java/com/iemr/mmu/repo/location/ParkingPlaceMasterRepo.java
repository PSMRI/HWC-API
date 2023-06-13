/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.location;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.login.ParkingPlace;

@Repository
@RestResource(exported = false)
public interface ParkingPlaceMasterRepo extends CrudRepository<ParkingPlace, Integer> {
	@Query(" SELECT parkingPlaceID, parkingPlaceName FROM ParkingPlace WHERE providerServiceMapID = :providerServiceMapID AND deleted != 1 ")
	public ArrayList<Object[]> getParkingPlaceMaster(@Param("providerServiceMapID") Integer providerServiceMapID);

}
