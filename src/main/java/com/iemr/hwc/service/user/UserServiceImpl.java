package com.iemr.hwc.service.user;

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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserLoginRepo userLoginRepo;

    @Autowired
    private UserMasterVillageRepo userMasterVillageRepo;

    @Autowired
    private DistrictBranchMasterRepo districtBranchMasterRepo;

    public String setMasterVillage(Long userID, Integer villageID){
        String response = "";
        UsersMasterVillage usersMasterVillage = userMasterVillageRepo.getByUserIDAndVillageID(userID, villageID);
        if(usersMasterVillage==null || (usersMasterVillage!=null && usersMasterVillage.getActive()==false)){
            DistrictBranchMapping districtBranchMapping = districtBranchMasterRepo.findByDistrictBranchID(villageID);
            Users userBD = userLoginRepo.getUserByUserID(userID);
            if(userBD!=null) {
                if(districtBranchMapping!=null){
                    UsersMasterVillage user = new UsersMasterVillage();
                    user.setUser(userBD);
                    user.setMasterVillage(districtBranchMapping);
                    user.setActive(true);
                    user.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                    user.setLastModDate(new Timestamp(System.currentTimeMillis()));
                    user = userMasterVillageRepo.save(user);
                    if(user!=null){
                        response = "ok";
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
            response = "already_have_master_village";

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