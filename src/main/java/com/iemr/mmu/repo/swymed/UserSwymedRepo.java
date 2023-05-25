package com.iemr.mmu.repo.swymed;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.swymed.UserSwymed;

@Repository
@RestResource(exported = false)
public interface UserSwymedRepo extends CrudRepository<UserSwymed, Long> {

	@Query("select new UserSwymed(us,user.UserName) from UserSwymed us join us.user user where  us.userID=:userID")
	UserSwymed findOneMap(@Param("userID")Long userid);
	
	

}
