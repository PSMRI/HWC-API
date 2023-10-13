package com.iemr.hwc.service.choApp;

import com.iemr.hwc.data.choApp.UserActivityLogs;
import com.iemr.hwc.utils.request.SyncSearchRequest;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface CHOAppSyncService {
    ResponseEntity<String> registerCHOAPPBeneficiary(String comingReq, String authorization);

    ResponseEntity<String> getBeneficiaryByVillageIDAndLastModifiedDate(SyncSearchRequest villageIDAndLastSyncDate, String authorization);

    ResponseEntity<String> getFlowRecordsByVillageIDAndLastModifiedDate(SyncSearchRequest villageIDAndLastSyncDate, String authorization);

    ResponseEntity<String> saveUserActivityLogs(List<UserActivityLogs> logsList, String authorization);

    ResponseEntity<String> getBeneficiaryNurseFormDataGeneralOPD(String comingRequest, String authorization);

    ResponseEntity<String> saveBeneficiaryNurseFormDataGeneralOPD(String comingRequest, String authorization);
}
