package com.iemr.hwc.service.dataSyncLayerCentral;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
class DataSyncRepositoryCentralTest {
    @Mock
    private DataSource dataSource;

    @InjectMocks
    private DataSyncRepositoryCentral dataSyncRepositoryCentral;

    @Test
    void testCheckRecordIsAlreadyPresentOrNot() {
        dataSyncRepositoryCentral.checkRecordIsAlreadyPresentOrNot("Schema Name", "Table Name", "Van Serial No", "Van ID",
                "Van Auto Inc Column Name");
    }

    @Test
    void testSyncDataToCentralDB() {
        // Arrange, Act and Assert
        assertEquals(0, dataSyncRepositoryCentral.syncDataToCentralDB("Query", new ArrayList<>()).length);
    }

    @Test
    void testGetMasterDataFromTable() throws Exception {
        dataSyncRepositoryCentral.getMasterDataFromTable("Schema", "Table", "Column Names", "Master Type",
                mock(Timestamp.class), 1, 1);
    }
}
