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
import org.springframework.stereotype.Repository;

import com.iemr.hwc.data.login.MasterVan;

@Repository
public interface FacilityLoginRepo extends CrudRepository<MasterVan, Integer> {

	@Query(value = "SELECT DISTINCT usrm.FacilityID, f.FacilityName, "
			+ "f.StateID, COALESCE(s.StateName,'') AS stateName, "
			+ "f.DistrictID, COALESCE(d.DistrictName,'') AS districtName, "
			+ "f.BlockID, COALESCE(b.BlockName,'') AS blockName "
			+ "FROM m_UserServiceRoleMapping usrm "
			+ "JOIN m_facility f ON usrm.FacilityID = f.FacilityID "
			+ "LEFT JOIN m_state s ON s.StateID = f.StateID "
			+ "LEFT JOIN m_district d ON d.DistrictID = f.DistrictID "
			+ "LEFT JOIN m_districtblock b ON b.BlockID = f.BlockID "
			+ "WHERE usrm.UserID = :userID AND usrm.ProviderServiceMapID = :providerServiceMapID "
			+ "AND usrm.Deleted = false AND usrm.FacilityID IS NOT NULL "
			+ "AND f.Deleted = false LIMIT 1", nativeQuery = true)
	Object[] getUserFacilityDetails(@Param("userID") Integer userID,
			@Param("providerServiceMapID") Integer providerServiceMapID);
}
