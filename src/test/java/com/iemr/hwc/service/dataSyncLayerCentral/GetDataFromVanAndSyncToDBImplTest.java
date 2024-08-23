package com.iemr.hwc.service.dataSyncLayerCentral;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.iemr.hwc.data.syncActivity_syncLayer.SyncUploadDataDigester;

@ExtendWith(MockitoExtension.class)
class GetDataFromVanAndSyncToDBImplTest {
    @Mock
    private DataSyncRepositoryCentral dataSyncRepositoryCentral;

    @InjectMocks
    private GetDataFromVanAndSyncToDBImpl getDataFromVanAndSyncToDBImpl;

    @Test
    void testSyncDataToServer() throws Exception {
        getDataFromVanAndSyncToDBImpl.syncDataToServer("Request OBJ", "JaneDoe");
    }

    @Test
    void testUpdate_M_BeneficiaryRegIdMapping_for_provisioned_benID() {
        // Arrange
        SyncUploadDataDigester syncUploadDataDigester = new SyncUploadDataDigester();
        syncUploadDataDigester.setSchemaName("Schema Name");
        syncUploadDataDigester.setServerColumns("Server Columns");
        syncUploadDataDigester.setSyncData(new ArrayList<>());
        syncUploadDataDigester.setSyncedBy("Synced By");
        syncUploadDataDigester.setTableName("Table Name");

        // Act and Assert
        assertEquals("data sync passed",
                getDataFromVanAndSyncToDBImpl.update_M_BeneficiaryRegIdMapping_for_provisioned_benID(syncUploadDataDigester));
    }

    @Test
    void testUpdate_M_BeneficiaryRegIdMapping_for_provisioned_benID2() {
        // Arrange
        SyncUploadDataDigester syncUploadDataDigester = mock(SyncUploadDataDigester.class);
        when(syncUploadDataDigester.getSchemaName()).thenReturn("Schema Name");
        when(syncUploadDataDigester.getTableName()).thenReturn("Table Name");
        when(syncUploadDataDigester.getSyncData()).thenReturn(new ArrayList<>());
        doNothing().when(syncUploadDataDigester).setSchemaName(Mockito.<String>any());
        doNothing().when(syncUploadDataDigester).setServerColumns(Mockito.<String>any());
        doNothing().when(syncUploadDataDigester).setSyncData(Mockito.<List<Map<String, Object>>>any());
        doNothing().when(syncUploadDataDigester).setSyncedBy(Mockito.<String>any());
        doNothing().when(syncUploadDataDigester).setTableName(Mockito.<String>any());
        syncUploadDataDigester.setSchemaName("Schema Name");
        syncUploadDataDigester.setServerColumns("Server Columns");
        syncUploadDataDigester.setSyncData(new ArrayList<>());
        syncUploadDataDigester.setSyncedBy("Synced By");
        syncUploadDataDigester.setTableName("Table Name");

        // Act
        String actualUpdate_M_BeneficiaryRegIdMapping_for_provisioned_benIDResult = getDataFromVanAndSyncToDBImpl
                .update_M_BeneficiaryRegIdMapping_for_provisioned_benID(syncUploadDataDigester);

        // Assert
        verify(syncUploadDataDigester).getSchemaName();
        verify(syncUploadDataDigester).getSyncData();
        verify(syncUploadDataDigester).getTableName();
        verify(syncUploadDataDigester).setSchemaName(eq("Schema Name"));
        verify(syncUploadDataDigester).setServerColumns(eq("Server Columns"));
        verify(syncUploadDataDigester).setSyncData(isA(List.class));
        verify(syncUploadDataDigester).setSyncedBy(eq("Synced By"));
        verify(syncUploadDataDigester).setTableName(eq("Table Name"));
        assertEquals("data sync passed", actualUpdate_M_BeneficiaryRegIdMapping_for_provisioned_benIDResult);
    }

    @Test
    void testUpdate_M_BeneficiaryRegIdMapping_for_provisioned_benID3() {
        // Arrange
        ArrayList<Map<String, Object>> mapList = new ArrayList<>();
        mapList.add(new HashMap<>());
        SyncUploadDataDigester syncUploadDataDigester = mock(SyncUploadDataDigester.class);
        when(syncUploadDataDigester.getSchemaName()).thenReturn("Schema Name");
        when(syncUploadDataDigester.getTableName()).thenReturn("Table Name");
        when(syncUploadDataDigester.getSyncData()).thenReturn(mapList);
        doNothing().when(syncUploadDataDigester).setSchemaName(Mockito.<String>any());
        doNothing().when(syncUploadDataDigester).setServerColumns(Mockito.<String>any());
        doNothing().when(syncUploadDataDigester).setSyncData(Mockito.<List<Map<String, Object>>>any());
        doNothing().when(syncUploadDataDigester).setSyncedBy(Mockito.<String>any());
        doNothing().when(syncUploadDataDigester).setTableName(Mockito.<String>any());
        syncUploadDataDigester.setSchemaName("Schema Name");
        syncUploadDataDigester.setServerColumns("Server Columns");
        syncUploadDataDigester.setSyncData(new ArrayList<>());
        syncUploadDataDigester.setSyncedBy("Synced By");
        syncUploadDataDigester.setTableName("Table Name");

        // Act
        String actualUpdate_M_BeneficiaryRegIdMapping_for_provisioned_benIDResult = getDataFromVanAndSyncToDBImpl
                .update_M_BeneficiaryRegIdMapping_for_provisioned_benID(syncUploadDataDigester);

        // Assert
        verify(syncUploadDataDigester).getSchemaName();
        verify(syncUploadDataDigester).getSyncData();
        verify(syncUploadDataDigester).getTableName();
        verify(syncUploadDataDigester).setSchemaName(eq("Schema Name"));
        verify(syncUploadDataDigester).setServerColumns(eq("Server Columns"));
        verify(syncUploadDataDigester).setSyncData(isA(List.class));
        verify(syncUploadDataDigester).setSyncedBy(eq("Synced By"));
        verify(syncUploadDataDigester).setTableName(eq("Table Name"));
        assertEquals("data sync passed", actualUpdate_M_BeneficiaryRegIdMapping_for_provisioned_benIDResult);
    }

    @Test
    void testGetQueryToInsertDataToServerDB() {
        // Arrange, Act and Assert
        assertEquals(" INSERT INTO Schema Name.Table Name( Server Columns) VALUES (  ?  ) ",
                getDataFromVanAndSyncToDBImpl.getQueryToInsertDataToServerDB("Schema Name", "Table Name", "Server Columns"));
        assertEquals(" INSERT INTO Schema Name.Table Name( ,) VALUES (  ) ",
                getDataFromVanAndSyncToDBImpl.getQueryToInsertDataToServerDB("Schema Name", "Table Name", ","));
        assertEquals(" INSERT INTO Schema Name.Table Name( null) VALUES (  ) ",
                getDataFromVanAndSyncToDBImpl.getQueryToInsertDataToServerDB("Schema Name", "Table Name", null));
    }

    @Test
    void testGetQueryToUpdateDataToServerDB() {
        // Arrange, Act and Assert
        assertEquals(" UPDATE  Schema Name.Table Name SET Server Columns= ? WHERE VanSerialNo = ? AND VanID = ? ",
                getDataFromVanAndSyncToDBImpl.getQueryToUpdateDataToServerDB("Schema Name", "Table Name", "Server Columns"));
        assertEquals(" UPDATE  Schema Name.Table Name SET  WHERE VanSerialNo = ? AND VanID = ? ",
                getDataFromVanAndSyncToDBImpl.getQueryToUpdateDataToServerDB("Schema Name", "Table Name", ","));
        assertEquals(" UPDATE  Schema Name.Table Name SET  WHERE VanSerialNo = ? AND VanID = ? ",
                getDataFromVanAndSyncToDBImpl.getQueryToUpdateDataToServerDB("Schema Name", "Table Name", null));
    }
}
