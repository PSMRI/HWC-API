package com.iemr.hwc.repo.choApp;

import com.iemr.hwc.data.choApp.OutreachActivity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;


@Repository
public interface OutreachActivityRepo extends CrudRepository<OutreachActivity, Integer> {

    @Query(" SELECT o.activityId, o.activityName, o.eventDescription," +
            "o.noOfParticipants, o.activityDate, o.userId, o.userName FROM OutreachActivity o WHERE o.userId = :userId AND o.deleted = false ")
    ArrayList<Object[]> getActivitiesByUserID(@Param("userId") Integer userId);
}
