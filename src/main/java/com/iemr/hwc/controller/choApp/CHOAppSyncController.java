/*
 * AMRIT – Accessible Medical Records via Integrated Technology
 * Integrated EHR (Electronic Health Records) Solution
 *
 * Copyright (C) "Piramal Swasthya Management and Research Institute"
 *
 * This file is part of AMRIT.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.iemr.hwc.controller.choApp;

import com.iemr.hwc.data.choApp.OutreachActivity;
import com.iemr.hwc.data.choApp.UserActivityLogs;
import com.iemr.hwc.data.doctor.PrescriptionTemplates;
import com.iemr.hwc.service.choApp.CHOAppSyncService;
import com.iemr.hwc.utils.request.SyncSearchRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
                                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.registerCHOAPPBeneficiary(comingReq, Authorization);
    }

    // beneficiary registration sync from AMRIT server to CHO app with identity new
    @ApiOperation(value = "Sync beneficiaries from AMRIT server to CHO App", consumes = "application/json", produces = "application/json")
    @RequestMapping(value = { "/beneficiariesToApp" }, method = { RequestMethod.POST })
    public ResponseEntity<String> beneficiarySyncToAppLocal(@RequestBody SyncSearchRequest villageIDAndLastSyncDate,
                                                                      @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.getBeneficiaryByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, Authorization);
    }

    // Get count of beneficiary to sync from AMRIT server to CHO app with identity new
    @ApiOperation(value = "Returns count of beneficiaries to be synced from AMRIT server to CHO App", consumes = "application/json", produces = "application/json")
    @RequestMapping(value = { "/beneficiariesToAppCount" }, method = { RequestMethod.POST })
    public ResponseEntity<String> beneficiarySyncToAppLocalCount(@RequestBody SyncSearchRequest villageIDAndLastSyncDate,
                                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.countBeneficiaryByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, Authorization);
    }

    //Count of beneficiary flow status records to sync from AMRIT server to CHO app
    @ApiOperation(value = "Returns count of beneficiaries flow status records to be synced", consumes = "application/json", produces = "application/json")
    @RequestMapping(value = { "/benFlowStatusRecordsCount" }, method = {
            RequestMethod.POST })
    public ResponseEntity<String> flowStatusesSyncToAppLocalCount(@RequestBody SyncSearchRequest villageIDAndLastSyncDate,
                                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.countFlowRecordsByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, Authorization);
    }

    // beneficiary flow status records sync from AMRIT server to CHO app
    @ApiOperation(value = "Sync beneficiaries flow status records ", consumes = "application/json", produces = "application/json")
    @RequestMapping(value = { "/benFlowStatusRecordsToApp" }, method = {
            RequestMethod.POST })
    public ResponseEntity<String> flowStatusesSyncToAppLocal(@RequestBody SyncSearchRequest villageIDAndLastSyncDate,
                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

            return choappSyncService.getFlowRecordsByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, Authorization);
    }

    // Login logout logs sync from CHO app to AMRIT server
    @ApiOperation(value = "Sync user activity logs to AMRIT server", consumes = "application/json", produces = "application/json")
    @RequestMapping(value = { "/userActivityLogsToServer" }, method = { RequestMethod.POST })
    public ResponseEntity<String> userActivityLogsSyncToServer(@RequestBody List<UserActivityLogs> logsList,
                                                                      @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.saveUserActivityLogs(logsList, Authorization);
    }

    // beneficiary nurse-form data(visit details,vitals,chief complaints,history,examinations) sync from AMRIT server to CHO app
    @ApiOperation(value = "Sync beneficiaries nurse-form data from AMRIT server to CHO App", consumes = "application/json", produces = "application/json")
    @RequestMapping(value = { "/beneficiaryGeneralOPDNurseFormDataToApp" }, method = { RequestMethod.POST })
    public ResponseEntity<String> beneficiaryNurseFormDataGeneralOPDSyncToAppLocal(
            @ApiParam(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.getBeneficiaryNurseFormDataGeneralOPD(comingRequest, Authorization);
    }

    // beneficiary nurse-form data(visit details,vitals,chief complaints,history,examinations) save from CHO app to server
    @ApiOperation(value = "Save beneficiaries nurse-form data CHO App to AMRIT server", consumes = "application/json", produces = "application/json")
    @RequestMapping(value = { "/generalOPDNurseFormDataToServer" }, method = { RequestMethod.POST })
    public ResponseEntity<String> beneficiaryNurseFormDataGeneralOPDSyncToServer(@RequestBody String comingRequest,
                                                                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.saveBeneficiaryNurseFormDataGeneralOPD(comingRequest, Authorization);
    }

    // Upload Prescription templates from CHO app to AMRIT server
    @ApiOperation(value = "Save prescription templates to AMRIT server", consumes = "application/json", produces = "application/json")
    @RequestMapping(value = { "/prescriptionTemplatesToServer" }, method = { RequestMethod.POST })
    public ResponseEntity<String> prescriptionTemplatesToServer(@RequestBody List<PrescriptionTemplates> templateList,
                                                               @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.savePrescriptionTemplatesToServer(templateList, Authorization);
    }

    // Fetch Prescription templates for a doctor from AMRIT server
    @ApiOperation(value = "Fetch prescription templates for doctor to AMRIT server", consumes = "application/json", produces = "application/json")
    @RequestMapping(value = { "/{userID}/prescriptionTemplatesDataToApp" }, method = { RequestMethod.GET })
    public ResponseEntity<String> prescriptionTemplatesToApp(@PathVariable Integer userID,
                                                                @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.savePrescriptionTemplatesToApp(userID, Authorization);
    }

    // Delete Prescription templates for a doctor from AMRIT server
    @ApiOperation(value = "Delete prescription templates for doctor to AMRIT server", consumes = "application/json", produces = "application/json")
    @RequestMapping(value = { "/{userID}/prescriptionTemplates/{tempID}/delete" }, method = { RequestMethod.DELETE })
    public ResponseEntity<String> deleteTemplate(@PathVariable Integer userID,
                                                             @PathVariable Integer tempID,
                                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.deletePrescriptionTemplates(userID, tempID);
    }

    // save new Outreach activity event
    @ApiOperation(value = "Create new event for outreach activity", consumes = "application/json", produces = "application/json")
    @RequestMapping(value = { "/activity/create" }, method = { RequestMethod.POST })
    public ResponseEntity<String> createNewOutreachActivity(@RequestBody OutreachActivity activity,
                                                               @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.createNewOutreachActivity(activity, Authorization);
    }

    // Get all outreach activities by user
    @ApiOperation(value = "Get list of all outreach activities by user", consumes = "application/json", produces = "application/json")
    @RequestMapping(value = { "/activity/{userId}/getAllByUser" }, method = { RequestMethod.GET })
    public ResponseEntity<String> getActivitiesByUser(@PathVariable Integer userId,
                                                               @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.getActivitiesByUser(userId, Authorization);
    }

    // Get an outreach activity by activityId
    @ApiOperation(value = "Get an outreach activities by activityId", consumes = "application/json", produces = "application/json")
    @RequestMapping(value = { "/activity/{activityId}/getById" }, method = { RequestMethod.GET })
    public ResponseEntity<String> getActivityByIdr(@PathVariable Integer activityId,
                                                      @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.getActivityById(activityId, Authorization);
    }
}