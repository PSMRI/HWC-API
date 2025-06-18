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

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/sync", headers = "Authorization", consumes = "application/json", produces = "application/json")
/**
 * Objective: Performs beneficiaries and ben flow status sync to AMRIT server
 * from CHO app and vice-versa.
 * Details
 */
public class CHOAppSyncController {
    private CHOAppSyncService choappSyncService;

    @Autowired
    public void setCHOAppSyncService(CHOAppSyncService choappSyncService) {
        this.choappSyncService = choappSyncService;
    }

    // beneficiary registration sync from CHO app to AMRIT server with common and
    // identity new
    @Operation(summary = "Sync new beneficiaries to AMRIT server")
    @PostMapping(value = { "/beneficiariesToServer" })
    public ResponseEntity<String> beneficiaryRegistrationSyncToServer(@RequestBody String comingReq,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.registerCHOAPPBeneficiary(comingReq, Authorization);
    }

    // beneficiary registration sync from AMRIT server to CHO app with identity new
    @Operation(summary = "Sync beneficiaries from AMRIT server to CHO App")
    @PostMapping(value = { "/beneficiariesToApp" })
    public ResponseEntity<String> beneficiarySyncToAppLocal(@RequestBody SyncSearchRequest villageIDAndLastSyncDate,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.getBeneficiaryByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, Authorization);
    }

    // Get count of beneficiary to sync from AMRIT server to CHO app with identity
    // new
    @Operation(summary = "Returns count of beneficiaries to be synced from AMRIT server to CHO App")
    @PostMapping(value = { "/beneficiariesToAppCount" })
    public ResponseEntity<String> beneficiarySyncToAppLocalCount(
            @RequestBody SyncSearchRequest villageIDAndLastSyncDate,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.countBeneficiaryByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate,
                Authorization);
    }

    // Count of beneficiary flow status records to sync from AMRIT server to CHO app
    @Operation(summary = "Returns count of beneficiaries flow status records to be synced")
    @PostMapping(value = { "/benFlowStatusRecordsCount" })
    public ResponseEntity<String> flowStatusesSyncToAppLocalCount(
            @RequestBody SyncSearchRequest villageIDAndLastSyncDate,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.countFlowRecordsByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate,
                Authorization);
    }

    // beneficiary flow status records sync from AMRIT server to CHO app
    @Operation(summary = "Sync beneficiaries flow status records ")
    @PostMapping(value = { "/benFlowStatusRecordsToApp" })
    public ResponseEntity<String> flowStatusesSyncToAppLocal(@RequestBody SyncSearchRequest villageIDAndLastSyncDate,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.getFlowRecordsByVillageIDAndLastModifiedDate(villageIDAndLastSyncDate, Authorization);
    }

    // Login logout logs sync from CHO app to AMRIT server
    @Operation(summary = "Sync user activity logs to AMRIT server")
    @PostMapping(value = { "/userActivityLogsToServer" })
    public ResponseEntity<String> userActivityLogsSyncToServer(@RequestBody List<UserActivityLogs> logsList,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.saveUserActivityLogs(logsList, Authorization);
    }

    // beneficiary nurse-form data(visit details,vitals,chief
    // complaints,history,examinations) sync from AMRIT server to CHO app
    @Operation(summary = "Sync beneficiaries nurse-form data from AMRIT server to CHO App")
    @PostMapping(value = { "/beneficiaryGeneralOPDNurseFormDataToApp" })
    public ResponseEntity<String> beneficiaryNurseFormDataGeneralOPDSyncToAppLocal(
            @Param(value = "{\"benRegID\":\"Long\",\"visitCode\":\"Long\"}") @RequestBody String comingRequest,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.getBeneficiaryNurseFormDataGeneralOPD(comingRequest, Authorization);
    }

    // beneficiary nurse-form data(visit details,vitals,chief
    // complaints,history,examinations) save from CHO app to server
    @Operation(summary = "Save beneficiaries nurse-form data CHO App to AMRIT server")
    @PostMapping(value = { "/generalOPDNurseFormDataToServer" })
    public ResponseEntity<String> beneficiaryNurseFormDataGeneralOPDSyncToServer(@RequestBody String comingRequest,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.saveBeneficiaryNurseFormDataGeneralOPD(comingRequest, Authorization);
    }

    // Upload Prescription templates from CHO app to AMRIT server
    @Operation(summary = "Save prescription templates to AMRIT server")
    @PostMapping(value = { "/prescriptionTemplatesToServer" })
    public ResponseEntity<String> prescriptionTemplatesToServer(@RequestBody List<PrescriptionTemplates> templateList,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.savePrescriptionTemplatesToServer(templateList, Authorization);
    }

    // Fetch Prescription templates for a doctor from AMRIT server
    @Operation(summary = "Fetch prescription templates for doctor to AMRIT server")
    @GetMapping(value = { "/{userID}/prescriptionTemplatesDataToApp" })
    public ResponseEntity<String> prescriptionTemplatesToApp(@PathVariable Integer userID,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.savePrescriptionTemplatesToApp(userID, Authorization);
    }

    // Delete Prescription templates for a doctor from AMRIT server
    @Operation(summary = "Delete prescription templates for doctor to AMRIT server")
    @DeleteMapping(value = { "/{userID}/prescriptionTemplates/{tempID}/delete" })
    public ResponseEntity<String> deleteTemplate(@PathVariable Integer userID,
            @PathVariable Integer tempID,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.deletePrescriptionTemplates(userID, tempID);
    }

    // save new Outreach activity event
    @Operation(summary = "Create new event for outreach activity")
    @PostMapping(value = { "/activity/create" })
    public ResponseEntity<String> createNewOutreachActivity(@RequestBody OutreachActivity activity,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.createNewOutreachActivity(activity, Authorization);
    }

    // Get all outreach activities by user
    @Operation(summary = "Get list of all outreach activities by user")
    @GetMapping(value = { "/activity/{userId}/getAllByUser" })
    public ResponseEntity<String> getActivitiesByUser(@PathVariable Integer userId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.getActivitiesByUser(userId, Authorization);
    }

    // Get an outreach activity by activityId
    @Operation(summary = "Get an outreach activities by activityId")
    @GetMapping(value = { "/activity/{activityId}/getById" })
    public ResponseEntity<String> getActivityByIdr(@PathVariable Integer activityId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {

        return choappSyncService.getActivityById(activityId, Authorization);
    }
}