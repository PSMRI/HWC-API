/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.videoConsultation;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.videoConsultation.VideoConsultationUser;

@Repository
@RestResource(exported = false)
public interface VideoConsultationUserRepo extends CrudRepository<VideoConsultationUser, Long> {

	@Query("select new VideoConsultationUser(us,user.UserName) from VideoConsultationUser us join us.user user where  us.userID=:userID")
	VideoConsultationUser findOneMap(@Param("userID") Long userid);

}
