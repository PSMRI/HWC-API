package com.iemr.hwc.utils.request;

import lombok.Data;

@Data
public class SyncSearchRequest {
    private String lastSyncDate;
    private Long lastModifiedDate;
    private Integer villageID;
}
