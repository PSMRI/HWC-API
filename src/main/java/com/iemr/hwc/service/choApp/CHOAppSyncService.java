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
package com.iemr.hwc.service.choApp;

import com.iemr.hwc.data.choApp.OutreachActivity;
import com.iemr.hwc.data.choApp.UserActivityLogs;
import com.iemr.hwc.data.doctor.PrescriptionTemplates;
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

    ResponseEntity<String> savePrescriptionTemplatesToServer(List<PrescriptionTemplates> templateList, String authorization);

    ResponseEntity<String> savePrescriptionTemplatesToApp(Integer userID, String authorization);

    ResponseEntity<String> deletePrescriptionTemplates(Integer userID, Integer tempID);

    ResponseEntity<String> countFlowRecordsByVillageIDAndLastModifiedDate(SyncSearchRequest villageIDAndLastSyncDate, String authorization);

    ResponseEntity<String> countBeneficiaryByVillageIDAndLastModifiedDate(SyncSearchRequest villageIDAndLastSyncDate, String authorization);

    ResponseEntity<String> createNewOutreachActivity(OutreachActivity activity, String authorization);

    ResponseEntity<String> getActivitiesByUser(Integer userId, String authorization);

    ResponseEntity<String> getActivityById(Integer activityId, String authorization);
}
