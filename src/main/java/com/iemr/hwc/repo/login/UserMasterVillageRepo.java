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

import com.iemr.hwc.data.login.UsersMasterVillage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
@RestResource(exported = false)
public interface UserMasterVillageRepo extends CrudRepository<UsersMasterVillage, Long> {
    @Query(" SELECT u FROM UsersMasterVillage u WHERE u.user.userID = :userID AND u.masterVillage.districtBranchID = :districtBranchID AND u.user.deleted = false AND u.masterVillage.deleted = false ")
    public UsersMasterVillage getByUserIDAndVillageID(@Param("userID") Long userID, @Param("districtBranchID") Integer districtBranchID);

    @Query(" SELECT u FROM UsersMasterVillage u WHERE u.user.userID = :userID AND u.user.deleted = false AND u.active = true ")
    public UsersMasterVillage getByUserID(@Param("userID") Long userID);

}