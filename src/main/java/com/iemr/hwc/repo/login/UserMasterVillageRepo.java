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

    @Query(" SELECT u FROM UsersMasterVillage u WHERE u.user.userID = :userID AND u.user.deleted = false ")
    public UsersMasterVillage getByUserID(@Param("userID") Long userID);

}