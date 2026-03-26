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
package com.iemr.hwc.repo.location;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.hwc.data.location.Districts;

@Repository
@RestResource(exported = false)
public interface DistrictMasterRepo extends CrudRepository<Districts, Integer> {
	@Query(" SELECT d.districtID, d.districtName,d.govtLGDStateID,d.govtLGDDistrictID FROM Districts d " + " WHERE d.stateID = :stateID AND d.deleted != true ")
	public ArrayList<Object[]> getDistrictMaster(@Param("stateID") Integer stateID);

	@Query(value = "SELECT f.StateID, f.DistrictID, d.DistrictName, f.BlockID, b.BlockName "
			+ "FROM m_facility f "
			+ "LEFT JOIN m_district d ON f.DistrictID = d.DistrictID "
			+ "LEFT JOIN m_districtblock b ON f.BlockID = b.BlockID "
			+ "WHERE f.FacilityID = :facilityID AND f.Deleted = false", nativeQuery = true)
	public Object[] getFacilityLocation(@Param("facilityID") Integer facilityID);

}
