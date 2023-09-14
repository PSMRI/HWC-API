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

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.hwc.data.location.DistrictBranchMapping;

import javax.transaction.Transactional;

@Repository
@RestResource(exported = false)
public interface DistrictBranchMasterRepo extends CrudRepository<DistrictBranchMapping, Integer> {

	@Query(" SELECT districtBranchID, villageName,govtLGDSubDistrictID,govtLGDVillageID, latitude, longitude, active, address FROM DistrictBranchMapping WHERE blockID = :blockID  AND deleted != 1 ")
	public ArrayList<Object[]> findByBlockID(@Param("blockID") Integer blockID);

	@Query(" SELECT d FROM DistrictBranchMapping d WHERE d.districtBranchID = :districtBranchID  AND d.deleted = false ")
	public DistrictBranchMapping findAllByDistrictBranchID(@Param("districtBranchID") Integer districtBranchID);

	@Transactional
	@Modifying
	@Query("update DistrictBranchMapping u set u.latitude = :latitude, u.longitude = :longitude, u.active = :active, u.address = :address where u.districtBranchID = :districtBranchID")
	int updateGeolocationByDistrictBranchID(@Param("latitude") Double latitude, @Param("longitude") Double longitude, @Param("active") Boolean active, @Param("address") String address,
											@Param("districtBranchID") Integer districtBranchID);

	@Transactional
	@Modifying
	@Query("update DistrictBranchMapping u set u.active = :active where u.districtBranchID = :districtBranchID")
	int updateActiceStatus(@Param("active") boolean active, @Param("districtBranchID") Integer districtBranchID);

	@Query(" SELECT d FROM DistrictBranchMapping d WHERE d.districtBranchID = :districtBranchID  AND d.deleted != 1 ")
	public DistrictBranchMapping findByDistrictBranchID(@Param("districtBranchID") Integer districtBranchID);
}
