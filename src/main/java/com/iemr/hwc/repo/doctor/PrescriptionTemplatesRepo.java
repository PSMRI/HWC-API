package com.iemr.hwc.repo.doctor;

import com.iemr.hwc.data.doctor.PrescriptionTemplates;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PrescriptionTemplatesRepo extends CrudRepository<PrescriptionTemplates,Integer> {

    @Query(" SELECT t FROM PrescriptionTemplates t WHERE t.userID = :userId")
    List<PrescriptionTemplates> getPrescriptionTemplatesByUserID(@Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query(" DELETE FROM PrescriptionTemplates t WHERE t.userID = :userID AND t.tempID = :tempID")
    void deletePrescriptionTemplatesByUserIDAndTempID(@Param("userID") Integer userID, @Param("tempID") Integer tempID);
}
