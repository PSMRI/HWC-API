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
package com.iemr.hwc.service.masterVillage;

import com.google.gson.Gson;
import com.iemr.hwc.data.location.*;
import com.iemr.hwc.data.login.Users;
import com.iemr.hwc.data.login.UsersMasterVillage;
import com.iemr.hwc.repo.location.*;
import com.iemr.hwc.repo.login.UserLoginRepo;
import com.iemr.hwc.repo.login.UserMasterVillageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class MasterVillageServiceImpl implements MasterVillageService {

    @Autowired
    private UserLoginRepo userLoginRepo;

    @Autowired
    private UserMasterVillageRepo userMasterVillageRepo;

    @Autowired
    private DistrictBranchMasterRepo districtBranchMasterRepo;

    public String setMasterVillage(Long userID, Integer villageID){
        String response = "";
        UsersMasterVillage usersMasterVillage = userMasterVillageRepo.getByUserID(userID);
        if(usersMasterVillage==null){
            DistrictBranchMapping districtBranchMapping = districtBranchMasterRepo.findByDistrictBranchID(villageID);
            Users userBD = userLoginRepo.getUserByUserID(userID);
            if(userBD!=null) {
                if(districtBranchMapping!=null){
                    UsersMasterVillage usersMasterVillage1 = new UsersMasterVillage();
                    usersMasterVillage1.setUser(userBD);
                    usersMasterVillage1.setMasterVillage(districtBranchMapping);
                    usersMasterVillage1.setActive(true);
                    usersMasterVillage1.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                    usersMasterVillage1.setLastModDate(new Timestamp(System.currentTimeMillis()));
                    usersMasterVillage1 = userMasterVillageRepo.save(usersMasterVillage1);
                    if(usersMasterVillage1!=null){
                        response = new Gson().toJson(usersMasterVillage1.getMasterVillage());
                    }
                    else{
                        response = "not_ok";
                    }
                }
                else{
                    response = "villageID_not_exist";
                }
            }
            else{
                response = "userID_not_exist";
            }
        }
        else{
            response =  new Gson().toJson(usersMasterVillage.getMasterVillage());

        }

        return response;
    }

    public UsersMasterVillage getMasterVillage(Long userID){
        UsersMasterVillage response = null;
        UsersMasterVillage user = userMasterVillageRepo.getByUserID(userID);
        if(user!=null){
            return user;
        }

        return response;
    }
}