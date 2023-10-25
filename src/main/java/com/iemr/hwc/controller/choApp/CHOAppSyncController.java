package com.iemr.hwc.controller.choApp;

import com.iemr.hwc.data.choApp.UserActivityLogs;
import com.iemr.hwc.data.doctor.PrescriptionTemplates;
import com.iemr.hwc.service.choApp.CHOAppSyncService;
import com.iemr.hwc.utils.request.SyncSearchRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    @RequestMapping(value = { "/beneficiariesToApp" }, method = { RequestMethod.POST })
    public ResponseEntity<String> beneficiarySyncToAppLocal(@RequestBody SyncSearchRequest villageIDAndLastSyncDate,
                                                                      @RequestHeader(value = "Authorization") String Authorization) {

        return choappSyncService.getBeneficiaryByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, Authorization);
    }

    // beneficiary flow status records sync from AMRIT server to CHO app
    @ApiOperation(value = "Sync beneficiaries flow status records ", consumes = "application/json", produces = "application/json")
    @RequestMapping(value = { "/benFlowStatusRecordsToApp" }, method = {
            RequestMethod.POST })
    public ResponseEntity<String> flowStatusesSyncToAppLocal(@RequestBody SyncSearchRequest villageIDAndLastSyncDate,
                                             @RequestHeader(value = "Authorization") String Authorization) {

            return choappSyncService.getFlowRecordsByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, Authorization);
    }

    // Login logout logs sync from CHO app to AMRIT server
    @ApiOperation(value = "Sync user activity logs to AMRIT server", consumes = "application/json", produces = "application/json")
    @RequestMapping(value = { "/userActivityLogsToServer" }, method = { RequestMethod.POST })
    public ResponseEntity<String> userActivityLogsSyncToServer(@RequestBody List<UserActivityLogs> logsList,
                                                                      @RequestHeader(value = "Authorization") String Authorization) {

        return choappSyncService.saveUserActivityLogs(logsList, Authorization);
    }

    // beneficiary nurse-form data(visit details,vitals,chief complaints,history,examinations) sync from AMRIT server to CHO app
    @ApiOperation(value = "Sync beneficiaries nurse-form data from AMRIT server to CHO App", consumes = "application/json", produces = "application/json")
    @RequestMapping(value = { "/beneficiaryGeneralOPDNurseFormDataToApp" }, method = { RequestMethod.POST })
    public ResponseEntity<String> beneficiaryNurseFormDataGeneralOPDSyncToAppLocal(
            @ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest,
            @RequestHeader(value = "Authorization") String Authorization) {

        return choappSyncService.getBeneficiaryNurseFormDataGeneralOPD(comingRequest, Authorization);
    }

    // beneficiary nurse-form data(visit details,vitals,chief complaints,history,examinations) save from CHO app to server
    @ApiOperation(value = "Save beneficiaries nurse-form data CHO App to AMRIT server", consumes = "application/json", produces = "application/json")
    @RequestMapping(value = { "/generalOPDNurseFormDataToServer" }, method = { RequestMethod.POST })
    public ResponseEntity<String> beneficiaryNurseFormDataGeneralOPDSyncToServer(@RequestBody String comingRequest,
                                                                                 @RequestHeader(value = "Authorization") String Authorization) {

        return choappSyncService.saveBeneficiaryNurseFormDataGeneralOPD(comingRequest, Authorization);
    }

    // Upload Prescription templates from CHO app to AMRIT server
    @ApiOperation(value = "Save prescription templates to AMRIT server", consumes = "application/json", produces = "application/json")
    @RequestMapping(value = { "/prescriptionTemplatesToServer" }, method = { RequestMethod.POST })
    public ResponseEntity<String> prescriptionTemplatesToServer(@RequestBody List<PrescriptionTemplates> templateList,
                                                               @RequestHeader(value = "Authorization") String Authorization) {

        return choappSyncService.savePrescriptionTemplatesToServer(templateList, Authorization);
    }

    // Fetch Prescription templates for a doctor from AMRIT server
    @ApiOperation(value = "Fetch prescription templates for doctor to AMRIT server", consumes = "application/json", produces = "application/json")
    @RequestMapping(value = { "/{userID}/prescriptionTemplatesDataToApp" }, method = { RequestMethod.GET })
    public ResponseEntity<String> prescriptionTemplatesToApp(@PathVariable Integer userID,
                                                                @RequestHeader(value = "Authorization") String Authorization) {

        return choappSyncService.savePrescriptionTemplatesToApp(userID, Authorization);
    }

}