package com.iemr.hwc.repo.choApp;

import com.iemr.hwc.data.choApp.UserActivityLogs;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActivityLogsRepo extends CrudRepository<UserActivityLogs,Integer> {
}
