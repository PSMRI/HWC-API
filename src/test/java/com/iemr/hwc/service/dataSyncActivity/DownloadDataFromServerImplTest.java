package com.iemr.hwc.service.dataSyncActivity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.iemr.hwc.data.syncActivity_syncLayer.TempVan;
import com.iemr.hwc.repo.syncActivity_syncLayer.SyncDownloadMasterRepo;
import com.iemr.hwc.repo.syncActivity_syncLayer.TempVanRepo;

@ExtendWith(MockitoExtension.class)
class DownloadDataFromServerImplTest {
    @Mock
    private DataSyncRepository dataSyncRepository;

    @InjectMocks
    private DownloadDataFromServerImpl downloadDataFromServerImpl;

    @Mock
    private SyncDownloadMasterRepo syncDownloadMasterRepo;

    @Mock
    private TempVanRepo tempVanRepo;

    @Test
    void testDownloadMasterDataFromServer() throws Exception {
        // Arrange, Act and Assert
        assertEquals("inProgress", downloadDataFromServerImpl.downloadMasterDataFromServer("JaneDoe", 1, 1));
        assertEquals("inProgress",
                downloadDataFromServerImpl.downloadMasterDataFromServer(" Master download started ", 1, 1));
    }

    @Test
    void testGetVanDetailsForMasterDownload() throws Exception {
        // Arrange
        when(tempVanRepo.getVanID()).thenReturn(new ArrayList<>());

        // Act and Assert
        assertThrows(Exception.class, () -> downloadDataFromServerImpl.getVanDetailsForMasterDownload());
        verify(tempVanRepo).getVanID();
    }

    @Test
    void testGetVanDetailsForMasterDownload2() throws Exception {
        // Arrange
        TempVan tempVan = new TempVan();
        tempVan.setTempVanID(1);
        tempVan.setVanID(1);
        tempVan.setVehicalNo("There are more than 1 van available. Kindly contact the administrator.");

        ArrayList<TempVan> tempVanList = new ArrayList<>();
        tempVanList.add(tempVan);
        when(tempVanRepo.getVanID()).thenReturn(tempVanList);

        // Act
        String actualVanDetailsForMasterDownload = downloadDataFromServerImpl.getVanDetailsForMasterDownload();

        // Assert
        verify(tempVanRepo).getVanID();
        assertEquals("{\"tempVanID\":1,\"vanID\":1,\"vehicalNo\":\"There are more than 1 van available. Kindly contact the"
                + " administrator.\"}", actualVanDetailsForMasterDownload);
    }

    @Test
    void testGetVanDetailsForMasterDownload3() throws Exception {
        // Arrange
        TempVan tempVan = new TempVan();
        tempVan.setTempVanID(1);
        tempVan.setVanID(1);
        tempVan.setVehicalNo("There are more than 1 van available. Kindly contact the administrator.");

        ArrayList<TempVan> tempVanList = new ArrayList<>();
        tempVanList.addAll(new ArrayList<>());
        tempVanList.add(tempVan);
        when(tempVanRepo.getVanID()).thenReturn(tempVanList);

        // Act
        String actualVanDetailsForMasterDownload = downloadDataFromServerImpl.getVanDetailsForMasterDownload();

        // Assert
        verify(tempVanRepo).getVanID();
        assertEquals("{\"tempVanID\":1,\"vanID\":1,\"vehicalNo\":\"There are more than 1 van available. Kindly contact the"
                + " administrator.\"}", actualVanDetailsForMasterDownload);
    }

    @Test
    void testGetDownloadStatus() {
        // Arrange and Act
        Map<String, Object> actualDownloadStatus = downloadDataFromServerImpl.getDownloadStatus();

        // Assert
        assertEquals(3, actualDownloadStatus.size());
        assertTrue(actualDownloadStatus.containsKey("failedMasterCount"));
        assertTrue(actualDownloadStatus.containsKey("failedMasters"));
        assertTrue(actualDownloadStatus.containsKey("percentage"));
    }
}
