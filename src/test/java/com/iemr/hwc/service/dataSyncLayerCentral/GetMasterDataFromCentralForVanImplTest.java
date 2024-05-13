package com.iemr.hwc.service.dataSyncLayerCentral;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.iemr.hwc.data.syncActivity_syncLayer.SyncDownloadMaster;

@ExtendWith(MockitoExtension.class)
class GetMasterDataFromCentralForVanImplTest {
    @Mock
    private DataSyncRepositoryCentral dataSyncRepositoryCentral;

    @InjectMocks
    private GetMasterDataFromCentralForVanImpl getMasterDataFromCentralForVanImpl;

    @Test
    void testGetMasterDataForVan() throws Exception {
        // Arrange
        when(dataSyncRepositoryCentral.getMasterDataFromTable(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Timestamp>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        SyncDownloadMaster obj = new SyncDownloadMaster();
        obj.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        obj.setCreatedDate(mock(Timestamp.class));
        obj.setDeleted(true);
        obj.setDownloadMasterTableID(1);
        obj.setLastDownloadDate(mock(Timestamp.class));
        obj.setLastModDate(mock(Timestamp.class));
        obj.setMasterType("Master Type");
        obj.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        obj.setProcessed("Processed");
        obj.setProviderServiceMapID(1);
        obj.setSchemaName("Schema Name");
        obj.setServerColumnName("Server Column Name");
        obj.setTableName("Table Name");
        obj.setVanColumnName("Van Column Name");
        obj.setVanID(1);

        // Act
        String actualMasterDataForVan = getMasterDataFromCentralForVanImpl.getMasterDataForVan(obj);

        // Assert
        verify(dataSyncRepositoryCentral).getMasterDataFromTable(eq("Schema Name"), eq("Table Name"),
                eq("Server Column Name"), eq("Master Type"), isA(Timestamp.class), isA(Integer.class), isA(Integer.class));
        assertEquals("[]", actualMasterDataForVan);
    }

    @Test
    void testGetMasterDataForVan2() throws Exception {
        // Arrange
        ArrayList<Map<String, Object>> mapList = new ArrayList<>();
        mapList.add(new HashMap<>());
        when(dataSyncRepositoryCentral.getMasterDataFromTable(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Timestamp>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any())).thenReturn(mapList);

        SyncDownloadMaster obj = new SyncDownloadMaster();
        obj.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        obj.setCreatedDate(mock(Timestamp.class));
        obj.setDeleted(true);
        obj.setDownloadMasterTableID(1);
        obj.setLastDownloadDate(mock(Timestamp.class));
        obj.setLastModDate(mock(Timestamp.class));
        obj.setMasterType("Master Type");
        obj.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        obj.setProcessed("Processed");
        obj.setProviderServiceMapID(1);
        obj.setSchemaName("Schema Name");
        obj.setServerColumnName("Server Column Name");
        obj.setTableName("Table Name");
        obj.setVanColumnName("Van Column Name");
        obj.setVanID(1);

        // Act
        String actualMasterDataForVan = getMasterDataFromCentralForVanImpl.getMasterDataForVan(obj);

        // Assert
        verify(dataSyncRepositoryCentral).getMasterDataFromTable(eq("Schema Name"), eq("Table Name"),
                eq("Server Column Name"), eq("Master Type"), isA(Timestamp.class), isA(Integer.class), isA(Integer.class));
        assertEquals("[{}]", actualMasterDataForVan);
    }

    @Test
    void testGetMasterDataForVan3() throws Exception {
        // Arrange
        ArrayList<Map<String, Object>> mapList = new ArrayList<>();
        mapList.add(new HashMap<>());
        mapList.add(new HashMap<>());
        when(dataSyncRepositoryCentral.getMasterDataFromTable(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Timestamp>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any())).thenReturn(mapList);

        SyncDownloadMaster obj = new SyncDownloadMaster();
        obj.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        obj.setCreatedDate(mock(Timestamp.class));
        obj.setDeleted(true);
        obj.setDownloadMasterTableID(1);
        obj.setLastDownloadDate(mock(Timestamp.class));
        obj.setLastModDate(mock(Timestamp.class));
        obj.setMasterType("Master Type");
        obj.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        obj.setProcessed("Processed");
        obj.setProviderServiceMapID(1);
        obj.setSchemaName("Schema Name");
        obj.setServerColumnName("Server Column Name");
        obj.setTableName("Table Name");
        obj.setVanColumnName("Van Column Name");
        obj.setVanID(1);

        // Act
        String actualMasterDataForVan = getMasterDataFromCentralForVanImpl.getMasterDataForVan(obj);

        // Assert
        verify(dataSyncRepositoryCentral).getMasterDataFromTable(eq("Schema Name"), eq("Table Name"),
                eq("Server Column Name"), eq("Master Type"), isA(Timestamp.class), isA(Integer.class), isA(Integer.class));
        assertEquals("[{},{}]", actualMasterDataForVan);
    }

    @Test
    void testGetMasterDataForVan4() throws Exception {
        // Arrange
        SyncDownloadMaster obj = mock(SyncDownloadMaster.class);
        when(obj.getSchemaName()).thenReturn(null);
        doNothing().when(obj).setCreatedBy(Mockito.<String>any());
        doNothing().when(obj).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(obj).setDeleted(Mockito.<Boolean>any());
        doNothing().when(obj).setDownloadMasterTableID(Mockito.<Integer>any());
        doNothing().when(obj).setLastDownloadDate(Mockito.<Timestamp>any());
        doNothing().when(obj).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(obj).setMasterType(Mockito.<String>any());
        doNothing().when(obj).setModifiedBy(Mockito.<String>any());
        doNothing().when(obj).setProcessed(Mockito.<String>any());
        doNothing().when(obj).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(obj).setSchemaName(Mockito.<String>any());
        doNothing().when(obj).setServerColumnName(Mockito.<String>any());
        doNothing().when(obj).setTableName(Mockito.<String>any());
        doNothing().when(obj).setVanColumnName(Mockito.<String>any());
        doNothing().when(obj).setVanID(Mockito.<Integer>any());
        obj.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        obj.setCreatedDate(mock(Timestamp.class));
        obj.setDeleted(true);
        obj.setDownloadMasterTableID(1);
        obj.setLastDownloadDate(mock(Timestamp.class));
        obj.setLastModDate(mock(Timestamp.class));
        obj.setMasterType("Master Type");
        obj.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        obj.setProcessed("Processed");
        obj.setProviderServiceMapID(1);
        obj.setSchemaName("Schema Name");
        obj.setServerColumnName("Server Column Name");
        obj.setTableName("Table Name");
        obj.setVanColumnName("Van Column Name");
        obj.setVanID(1);

        // Act
        String actualMasterDataForVan = getMasterDataFromCentralForVanImpl.getMasterDataForVan(obj);

        // Assert
        verify(obj).getSchemaName();
        verify(obj).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(obj).setCreatedDate(isA(Timestamp.class));
        verify(obj).setDeleted(isA(Boolean.class));
        verify(obj).setDownloadMasterTableID(isA(Integer.class));
        verify(obj).setLastDownloadDate(isA(Timestamp.class));
        verify(obj).setLastModDate(isA(Timestamp.class));
        verify(obj).setMasterType(eq("Master Type"));
        verify(obj).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(obj).setProcessed(eq("Processed"));
        verify(obj).setProviderServiceMapID(isA(Integer.class));
        verify(obj).setSchemaName(eq("Schema Name"));
        verify(obj).setServerColumnName(eq("Server Column Name"));
        verify(obj).setTableName(eq("Table Name"));
        verify(obj).setVanColumnName(eq("Van Column Name"));
        verify(obj).setVanID(isA(Integer.class));
        assertNull(actualMasterDataForVan);
    }
}
