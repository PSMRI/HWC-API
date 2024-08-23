package com.iemr.hwc.service.dataSyncActivity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.iemr.hwc.data.syncActivity_syncLayer.DataSyncGroups;
import com.iemr.hwc.data.syncActivity_syncLayer.SyncUtilityClass;
import com.iemr.hwc.repo.syncActivity_syncLayer.DataSyncGroupsRepo;

@ExtendWith(MockitoExtension.class)
class UploadDataToServerImplTest {
    @Mock
    private DataSyncGroupsRepo dataSyncGroupsRepo;

    @Mock
    private DataSyncRepository dataSyncRepository;

    @InjectMocks
    private UploadDataToServerImpl uploadDataToServerImpl;

    @Test
    void testGetDataToSyncToServer() throws Exception {
        // Arrange
        when(dataSyncRepository.getVanAndServerColumnList(Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        String actualDataToSyncToServer = uploadDataToServerImpl.getDataToSyncToServer(1, "User", "JaneDoe");

        // Assert
        verify(dataSyncRepository).getVanAndServerColumnList(isA(Integer.class));
        assertNull(actualDataToSyncToServer);
    }

    @Test
    void testGetDataToSyncToServer2() throws Exception {
        // Arrange
        SyncUtilityClass syncUtilityClass = new SyncUtilityClass();
        syncUtilityClass.setDataClassName("Data Class Name");
        syncUtilityClass.setDeleted(true);
        syncUtilityClass.setIsMaster(true);
        syncUtilityClass.setSchemaName("Schema Name");
        syncUtilityClass.setServerColumnName("Server Column Name");
        syncUtilityClass.setSyncTableDetailID(1);
        syncUtilityClass.setSyncTableGroupID(1);
        syncUtilityClass.setTableName("Table Name");
        syncUtilityClass.setVanColumnName("Van Column Name");

        ArrayList<SyncUtilityClass> syncUtilityClassList = new ArrayList<>();
        syncUtilityClassList.add(syncUtilityClass);
        when(dataSyncRepository.getDataForGivenSchemaAndTable(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(dataSyncRepository.getVanAndServerColumnList(Mockito.<Integer>any())).thenReturn(syncUtilityClassList);

        // Act
        String actualDataToSyncToServer = uploadDataToServerImpl.getDataToSyncToServer(1, "User", "JaneDoe");

        // Assert
        verify(dataSyncRepository).getDataForGivenSchemaAndTable(eq("Schema Name"), eq("Table Name"),
                eq("Van Column Name"));
        verify(dataSyncRepository).getVanAndServerColumnList(isA(Integer.class));
        assertEquals("Data successfully synced", actualDataToSyncToServer);
    }

    @Test
    void testSyncIntercepter() throws Exception {
        // Arrange
        when(dataSyncRepository.getVanAndServerColumnList(Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        String actualSyncIntercepterResult = uploadDataToServerImpl.syncIntercepter(1, "User", "JaneDoe");

        // Assert
        verify(dataSyncRepository).getVanAndServerColumnList(isA(Integer.class));
        assertNull(actualSyncIntercepterResult);
    }

    @Test
    void testSyncIntercepter2() throws Exception {
        // Arrange
        SyncUtilityClass syncUtilityClass = new SyncUtilityClass();
        syncUtilityClass.setDataClassName("Data Class Name");
        syncUtilityClass.setDeleted(true);
        syncUtilityClass.setIsMaster(true);
        syncUtilityClass.setSchemaName("Schema Name");
        syncUtilityClass.setServerColumnName("Server Column Name");
        syncUtilityClass.setSyncTableDetailID(1);
        syncUtilityClass.setSyncTableGroupID(1);
        syncUtilityClass.setTableName("Table Name");
        syncUtilityClass.setVanColumnName("Van Column Name");

        ArrayList<SyncUtilityClass> syncUtilityClassList = new ArrayList<>();
        syncUtilityClassList.add(syncUtilityClass);
        when(dataSyncRepository.getDataForGivenSchemaAndTable(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(dataSyncRepository.getVanAndServerColumnList(Mockito.<Integer>any())).thenReturn(syncUtilityClassList);

        // Act
        String actualSyncIntercepterResult = uploadDataToServerImpl.syncIntercepter(1, "User", "JaneDoe");

        // Assert
        verify(dataSyncRepository).getDataForGivenSchemaAndTable(eq("Schema Name"), eq("Table Name"),
                eq("Van Column Name"));
        verify(dataSyncRepository).getVanAndServerColumnList(isA(Integer.class));
        assertEquals("Data successfully synced", actualSyncIntercepterResult);
    }

    @Test
    void testSyncIntercepter3() throws Exception {
        // Arrange
        SyncUtilityClass syncUtilityClass = new SyncUtilityClass();
        syncUtilityClass.setDataClassName("Data Class Name");
        syncUtilityClass.setDeleted(true);
        syncUtilityClass.setIsMaster(true);
        syncUtilityClass.setSchemaName("Schema Name");
        syncUtilityClass.setServerColumnName("Server Column Name");
        syncUtilityClass.setSyncTableDetailID(1);
        syncUtilityClass.setSyncTableGroupID(1);
        syncUtilityClass.setTableName("Table Name");
        syncUtilityClass.setVanColumnName("Van Column Name");

        ArrayList<SyncUtilityClass> syncUtilityClassList = new ArrayList<>();
        syncUtilityClassList.add(syncUtilityClass);
        when(dataSyncRepository.getDataForGivenSchemaAndTable(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenThrow(new Exception("Data successfully synced"));
        when(dataSyncRepository.getVanAndServerColumnList(Mockito.<Integer>any())).thenReturn(syncUtilityClassList);

        // Act and Assert
        assertThrows(Exception.class, () -> uploadDataToServerImpl.syncIntercepter(1, "User", "JaneDoe"));
        verify(dataSyncRepository).getDataForGivenSchemaAndTable(eq("Schema Name"), eq("Table Name"),
                eq("Van Column Name"));
        verify(dataSyncRepository).getVanAndServerColumnList(isA(Integer.class));
    }

    @Test
    void testSyncDataToServer() throws Exception {
        uploadDataToServerImpl.syncDataToServer("Schema Name", "Table Name", "Van Auto Inc Column Name", "Server Columns",
                new ArrayList<>(), "User", "JaneDoe");
    }

    @Test
    void testGetVanSerialNoListForSyncedData() throws Exception {
        uploadDataToServerImpl.getVanSerialNoListForSyncedData("Van Auto Inc Column Name", new ArrayList<>());
    }

    @Test
    void testGetDataSyncGroupDetails() {
        // Arrange
        when(dataSyncGroupsRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        // Act
        String actualDataSyncGroupDetails = uploadDataToServerImpl.getDataSyncGroupDetails();

        // Assert
        verify(dataSyncGroupsRepo).findByDeleted(isA(Boolean.class));
        assertEquals("[]", actualDataSyncGroupDetails);
    }

    @Test
    void testGetDataSyncGroupDetails2() {
        // Arrange
        ArrayList<DataSyncGroups> dataSyncGroupsList = new ArrayList<>();
        dataSyncGroupsList.add(new DataSyncGroups());
        when(dataSyncGroupsRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(dataSyncGroupsList);

        // Act
        String actualDataSyncGroupDetails = uploadDataToServerImpl.getDataSyncGroupDetails();

        // Assert
        verify(dataSyncGroupsRepo).findByDeleted(isA(Boolean.class));
        assertEquals("[{}]", actualDataSyncGroupDetails);
    }

    @Test
    void testGetDataSyncGroupDetails3() {
        // Arrange
        ArrayList<DataSyncGroups> dataSyncGroupsList = new ArrayList<>();
        dataSyncGroupsList.add(new DataSyncGroups());
        dataSyncGroupsList.add(new DataSyncGroups());
        when(dataSyncGroupsRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(dataSyncGroupsList);

        // Act
        String actualDataSyncGroupDetails = uploadDataToServerImpl.getDataSyncGroupDetails();

        // Assert
        verify(dataSyncGroupsRepo).findByDeleted(isA(Boolean.class));
        assertEquals("[{},{}]", actualDataSyncGroupDetails);
    }

    @Test
    void testGetDataSyncGroupDetails4() {
        // Arrange
        ArrayList<DataSyncGroups> dataSyncGroupsList = new ArrayList<>();
        dataSyncGroupsList.add(null);
        when(dataSyncGroupsRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(dataSyncGroupsList);

        // Act
        String actualDataSyncGroupDetails = uploadDataToServerImpl.getDataSyncGroupDetails();

        // Assert
        verify(dataSyncGroupsRepo).findByDeleted(isA(Boolean.class));
        assertEquals("[null]", actualDataSyncGroupDetails);
    }
}
