package com.iemr.hwc.controller.choApp;

import com.iemr.hwc.data.choApp.OutreachActivity;
import com.iemr.hwc.data.choApp.UserActivityLogs;
import com.iemr.hwc.data.doctor.PrescriptionTemplates;
import com.iemr.hwc.utils.request.SyncSearchRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CHOAppSyncControllerTest {

    CHOAppSyncController cHOAppSyncController = new CHOAppSyncController();

    @Test
    void testBeneficiaryRegistrationSyncToServer() {
        ResponseEntity<String> result = cHOAppSyncController.beneficiaryRegistrationSyncToServer("comingReq", "Authorization");
        Assertions.assertEquals(new ResponseEntity<String>("body", null, 0), result);
    }

    @Test
    void testBeneficiarySyncToAppLocal() {
        ResponseEntity<String> result = cHOAppSyncController.beneficiarySyncToAppLocal(new SyncSearchRequest(), "Authorization");
        Assertions.assertEquals(new ResponseEntity<String>("body", null, 0), result);
    }

    @Test
    void testBeneficiarySyncToAppLocalCount() {
        ResponseEntity<String> result = cHOAppSyncController.beneficiarySyncToAppLocalCount(new SyncSearchRequest(), "Authorization");
        Assertions.assertEquals(new ResponseEntity<String>("body", null, 0), result);
    }

    @Test
    void testFlowStatusesSyncToAppLocalCount() {
        ResponseEntity<String> result = cHOAppSyncController.flowStatusesSyncToAppLocalCount(new SyncSearchRequest(), "Authorization");
        Assertions.assertEquals(new ResponseEntity<String>("body", null, 0), result);
    }

    @Test
    void testFlowStatusesSyncToAppLocal() {
        ResponseEntity<String> result = cHOAppSyncController.flowStatusesSyncToAppLocal(new SyncSearchRequest(), "Authorization");
        Assertions.assertEquals(new ResponseEntity<String>("body", null, 0), result);
    }

    @Test
    void testUserActivityLogsSyncToServer() {
        ResponseEntity<String> result = cHOAppSyncController.userActivityLogsSyncToServer(List.of(new UserActivityLogs()), "Authorization");
        Assertions.assertEquals(new ResponseEntity<String>("body", null, 0), result);
    }

    @Test
    void testBeneficiaryNurseFormDataGeneralOPDSyncToAppLocal() {
        ResponseEntity<String> result = cHOAppSyncController.beneficiaryNurseFormDataGeneralOPDSyncToAppLocal("comingRequest", "Authorization");
        Assertions.assertEquals(new ResponseEntity<String>("body", null, 0), result);
    }

    @Test
    void testBeneficiaryNurseFormDataGeneralOPDSyncToServer() {
        ResponseEntity<String> result = cHOAppSyncController.beneficiaryNurseFormDataGeneralOPDSyncToServer("comingRequest", "Authorization");
        Assertions.assertEquals(new ResponseEntity<String>("body", null, 0), result);
    }

    @Test
    void testPrescriptionTemplatesToServer() {
        ResponseEntity<String> result = cHOAppSyncController.prescriptionTemplatesToServer(List.of(new PrescriptionTemplates()), "Authorization");
        Assertions.assertEquals(new ResponseEntity<String>("body", null, 0), result);
    }

    @Test
    void testPrescriptionTemplatesToApp() {
        ResponseEntity<String> result = cHOAppSyncController.prescriptionTemplatesToApp(Integer.valueOf(0), "Authorization");
        Assertions.assertEquals(new ResponseEntity<String>("body", null, 0), result);
    }

    @Test
    void testDeleteTemplate() {
        ResponseEntity<String> result = cHOAppSyncController.deleteTemplate(Integer.valueOf(0), Integer.valueOf(0), "Authorization");
        Assertions.assertEquals(new ResponseEntity<String>("body", null, 0), result);
    }

    @Test
    void testCreateNewOutreachActivity() {
        ResponseEntity<String> result = cHOAppSyncController.createNewOutreachActivity(new OutreachActivity(0, Integer.valueOf(0), "userName", "activityName", "eventDescription", Integer.valueOf(0), new Timestamp(0, 0, 0, 0, 0, 0, 0), new Timestamp(0, 0, 0, 0, 0, 0, 0), new Timestamp(0, 0, 0, 0, 0, 0, 0), new byte[]{(byte) 0}, new byte[]{(byte) 0}, "img1", "img2", Double.valueOf(0), Double.valueOf(0), "img1Address", "img2Address", Double.valueOf(0), Double.valueOf(0), Boolean.TRUE), "Authorization");
        Assertions.assertEquals(new ResponseEntity<String>("body", null, 0), result);
    }

    @Test
    void testGetActivitiesByUser() {
        ResponseEntity<String> result = cHOAppSyncController.getActivitiesByUser(Integer.valueOf(0), "Authorization");
        Assertions.assertEquals(new ResponseEntity<String>("body", null, 0), result);
    }

    @Test
    void testGetActivityByIdr() {
        ResponseEntity<String> result = cHOAppSyncController.getActivityByIdr(Integer.valueOf(0), "Authorization");
        Assertions.assertEquals(new ResponseEntity<String>("body", null, 0), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme