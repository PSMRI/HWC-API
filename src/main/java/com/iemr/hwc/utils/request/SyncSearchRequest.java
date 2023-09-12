package com.iemr.hwc.utils.request;

import lombok.Data;
import java.util.List;

@Data
public class SyncSearchRequest {
    private String lastSyncDate;
    private Long lastModifiedDate;
    private List<Integer> villageID;
}
