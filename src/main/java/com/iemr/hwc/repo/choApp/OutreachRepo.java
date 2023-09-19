package com.iemr.hwc.repo.choApp;

import com.iemr.hwc.data.choApp.Outreach;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface OutreachRepo extends CrudRepository<Outreach, Integer> {

    @Query(" SELECT o FROM Outreach o WHERE o.stateID = :stateID AND o.deleted = false ")
    List<Outreach> getOutreachListByStateID(@Param("stateID") Integer stateID);
}
