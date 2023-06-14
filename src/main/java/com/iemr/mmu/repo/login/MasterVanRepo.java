/*
* AMRIT – Accessible Medical Records via Integrated Technology 
* Integrated EHR (Electronic Health Records) Solution 
*
* Copyright (C) "Piramal Swasthya Management and Research Institute" 
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
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
