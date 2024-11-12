package com.iemr.hwc.controller.dataSyncActivity;

import com.iemr.hwc.service.dataSyncActivity.DownloadDataFromServerImpl;
import com.iemr.hwc.service.dataSyncActivity.UploadDataToServerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StartSyncActivityTest {

    @Mock
    UploadDataToServerImpl uploadDataToServerImpl;
    @Mock
    DownloadDataFromServerImpl downloadDataFromServerImpl;
    @InjectMocks
    StartSyncActivity startSyncActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDataSyncToServer() throws Exception {
        when(uploadDataToServerImpl.getDataToSyncToServer(anyInt(), anyString(), anyString())).thenReturn("getDataToSyncToServerResponse");

        String result = startSyncActivity.dataSyncToServer("requestOBJ", "Authorization", "ServerAuthorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetSyncGroupDetails() {
        when(uploadDataToServerImpl.getDataSyncGroupDetails()).thenReturn("getDataSyncGroupDetailsResponse");

        String result = startSyncActivity.getSyncGroupDetails();
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testStartMasterDownload() throws Exception {
        when(downloadDataFromServerImpl.downloadMasterDataFromServer(anyString(), anyInt(), anyInt())).thenReturn("downloadMasterDataFromServerResponse");

        String result = startSyncActivity.startMasterDownload("requestOBJ", "Authorization", "ServerAuthorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testCheckMastersDownloadProgress() {
        when(downloadDataFromServerImpl.getDownloadStatus()).thenReturn(Map.of("getDownloadStatusResponse", "getDownloadStatusResponse"));

        String result = startSyncActivity.checkMastersDownloadProgress();
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetVanDetailsForMasterDownload() throws Exception {
        when(downloadDataFromServerImpl.getVanDetailsForMasterDownload()).thenReturn("getVanDetailsForMasterDownloadResponse");

        String result = startSyncActivity.getVanDetailsForMasterDownload();
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

