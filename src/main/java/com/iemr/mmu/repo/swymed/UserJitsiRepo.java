package com.iemr.mmu.repo.swymed;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.swymed.UserJitsi;

@Repository
@RestResource(exported = false)
public interface UserJitsiRepo extends CrudRepository<UserJitsi, Integer> {
	
	

	@Query("select new UserJitsi(us.jitsiUserName,us.jitsiPassword) from UserJitsi us where us.userID=:userID")
	UserJitsi findOneJitsiMap(@Param("userID") Long fromuserid);
	
	@Query("select us from UserJitsi us where us.vanID=:vanID")
	UserJitsi findOneJitsiMapVan(@Param("vanID") Integer vanid);

}
