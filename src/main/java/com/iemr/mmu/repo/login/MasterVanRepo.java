/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.login;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.login.MasterVan;

@Repository
@RestResource(exported = false)
public interface MasterVanRepo extends CrudRepository<MasterVan, Integer> {
	@Query("Select mv.vanID, mv.vehicalNo from MasterVan mv WHERE mv.deleted != 1 and mv.parkingPlaceID in :parkingPlaceList ")
	public List<Object[]> getUserVanDatails(@Param("parkingPlaceList") Set<Integer> parkingPlaceList);

	@Query("Select mv.facilityID from MasterVan mv WHERE mv.vanID = :vanID ")
	public Integer getFacilityID(@Param("vanID") Integer vanID);

	@Query("Select mv.swymedEmailID from MasterVan mv WHERE mv.vanID = :vanID ")
	public String getSpokeEmail(@Param("vanID") Integer vanID);
	
	/* created by = DU20091017 */
	
	@Query("Select mv.vanID, mv.vehicalNo from MasterVan mv WHERE mv.providerServiceMapID = :providerServiceMapID ")
	public ArrayList<Object[]> getVanMaster(@Param("providerServiceMapID") Integer providerServiceMapID);
}
