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
package com.iemr.hwc.repo.login;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.hwc.data.login.UserParkingplaceMapping;

@Repository
@RestResource(exported = false)
public interface UserParkingplaceMappingRepo extends CrudRepository<UserParkingplaceMapping, Integer> {
	@Query("SELECT x.parkingPlaceID,p.stateID,s.stateName,p.districtID,d.districtName,p.districtBlockID,b.blockName from UserParkingplaceMapping x "
			+ " INNER JOIN x.m_parkingplace p" + " INNER JOIN p.state s" + " INNER JOIN p.m_district d"
			+ " INNER JOIN p.districtBlock b" + " WHERE x.userID = :userID and x.deleted = 0 ")
	public List<Object[]> getUserParkingPlce(@Param("userID") Integer userID);

	public UserParkingplaceMapping findOneByUserIDAndProviderServiceMapIdAndDeleted(Integer userID, Integer providerServiceMapId,
			Integer deleted);

//	@Query("SELECT u from UserParkingplaceMapping u WHERE u.userID= :userID and u.providerServiceMapId =:providerServiceMapId and u.deleted =:deleted")
//	public List<Object[]> getUserParkingPlce1(@Param("userID") Integer userID,@Param("providerServiceMapId") Integer providerServiceMapId,@Param("deleted") Integer deleted );
//	

	// public UserParkingplaceMapping getUserParkingPlce1(Integer userid, Integer
	// providerServiceMapId);
}
