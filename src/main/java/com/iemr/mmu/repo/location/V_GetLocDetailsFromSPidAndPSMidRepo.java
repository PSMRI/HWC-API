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
