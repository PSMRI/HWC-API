package com.iemr.hwc.service.CHOApp;

import com.iemr.hwc.utils.request.SyncSearchRequest;
import org.springframework.http.ResponseEntity;

public interface CHOAppSyncService {
    ResponseEntity<String> registerCHOAPPBeneficiary(String comingReq, String authorization);

    ResponseEntity<String> getBeneficiaryByVillageIDAndLastModifiedDate(SyncSearchRequest villageIDAndLastSyncDate, String authorization);
}
