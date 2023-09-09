package com.iemr.hwc.controller.CHOApp;

import com.iemr.hwc.service.CHOApp.CHOAppSyncService;
import com.iemr.hwc.utils.request.SyncSearchRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/sync", headers = "Authorization")
/**
 * Objective: Performs beneficiaries and ben flow status sync to AMRIT server from CHO app and vice-versa.
 * Details
 */
public class CHOAppSyncController {
    private CHOAppSyncService choappSyncService;

    @Autowired
    public void setCHOAppSyncService(CHOAppSyncService choappSyncService) {
        this.choappSyncService = choappSyncService;
    }

    // beneficiary registration sync from CHO app to AMRIT server with common and identity new
    @ApiOperation(value = "Sync new beneficiaries to AMRIT server", consumes = "application/json", produces = "application/json")
    @RequestMapping(value = { "/beneficiariesToServer" }, method = { RequestMethod.POST })
    public ResponseEntity<String> beneficiaryRegistrationSyncToServer(@RequestBody String comingReq,
                                                              @RequestHeader(value = "Authorization") String Authorization) {

        return choappSyncService.registerCHOAPPBeneficiary(comingReq, Authorization);
    }

    // beneficiary registration sync from AMRIT server to CHO app with identity new
    @ApiOperation(value = "Sync beneficiaries from AMRIT server to CHO App", consumes = "application/json", produces = "application/json")
    @RequestMapping(value = { "/beneficiariesToLocal" }, method = { RequestMethod.GET })
    public ResponseEntity<String> beneficiarySyncToAppLocal(@RequestBody SyncSearchRequest villageIDAndLastSyncDate,
                                                                      @RequestHeader(value = "Authorization") String Authorization) {

        return choappSyncService.getBeneficiaryByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, Authorization);
    }

}